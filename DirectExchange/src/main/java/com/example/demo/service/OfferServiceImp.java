package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonConstants;
import com.example.demo.dao.BankRepository;
import com.example.demo.dao.OfferRepository;
import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.BankAccount;
import com.example.demo.dto.Offer;
import com.example.demo.dto.Transactions;
import com.example.demo.dto.User;
import com.example.demo.exception.InvalidRequestException;

@Service
@Transactional
public class OfferServiceImp implements IOfferService{

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Autowired
	private IUserService userService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Override
	public Offer add(Offer offer) throws InvalidRequestException{
		User user = offer.getUser();
		if(!user.getIsVerified()) {
			throw new InvalidRequestException("User is not Verified.");
		}
	
		offer.setDestinationAmount(offer.getAmount() * offer.getExchangeRate());
		if(!offer.getIsCounterOffer()) {
			if(offer.getDestinationCurrency().equals(offer.getSourceCurrency())) {
				throw new InvalidRequestException(" Source and destination Currency should not be same.");	
			}
		} else {
			Optional<Offer> temp = offerRepository.findByIdAndStatusAndExpirationDateAfter(offer.getParentOffer().getId(), CommonConstants.OFFER_OPEN, LocalDateTime.now());
			if(!temp.isPresent()) {
				throw new InvalidRequestException("InValid Request. Offer you want to counter no longer exists.");
			}
			if(!temp.get().getIsCounterOfferAllowed()) {
				throw new InvalidRequestException("InValid Request. Counter Offer is not allowed for this offer.");
			}
			offer.setExpirationDate(offer.getExpirationDate());
			offer.setSourceCountry(temp.get().getSourceCountry());
			offer.setDestinationCountry(temp.get().getDestinationCountry());
			offer.setSourceCurrency(temp.get().getSourceCurrency());
			offer.setDestinationCurrency(temp.get().getDestinationCurrency());
			offer.setIsSplitOfferAllowed(false);
			offer.setIsCounterOfferAllowed(false);
			double lowerLimit = temp.get().getAmount() * 0.90;
			double upperLimit = temp.get().getAmount()  * 1.10; 
			if(offer.getAmount() < lowerLimit || offer.getAmount() > upperLimit) {
				throw new InvalidRequestException("InValid Request for counter offer. Amount exceeds 10% lower or upper bound");						
			}
			
			if(offer.getHasMatchingOffer()) {
				Optional<Offer> counterModeOffer = offerRepository.findByIdAndStatusAndExpirationDateAfter(offer.getMatchingOffer().getId(), CommonConstants.OFFER_OPEN, LocalDateTime.now());
				if(!counterModeOffer.isPresent()) {
					throw new InvalidRequestException("InValid Request. Offer you want to counter no longer exists.");
				}
				
				counterModeOffer.get().setStatus(CommonConstants.OFFER_COUNTERMADE);
				offerRepository.save(counterModeOffer.get());				
			}
		}
		if(!checkDestinationBankAccountExists(user, offer.getSourceCurrency(), "Send")) {
			throw new InvalidRequestException("User does not have source account with required permission");		
		}
		
		if(!checkDestinationBankAccountExists(user, offer.getDestinationCurrency(), "Receive")) {
			throw new InvalidRequestException("User does not have destination account with required permission");			
		}
		return offerRepository.save(offer);
	}

	@Override
	public Offer findOne(Long id) throws Exception {
		Optional<Offer> offer= offerRepository.findById(id);
		if(!offer.isPresent()) {
			throw new InvalidRequestException("Offer not present with given id");		
		 }
		return offer.get();
	}

	@Override
	public List<Offer> findAll() throws Exception{
		List<Offer> allOffers = offerRepository.findByStatusAndIsCounterOfferAndExpirationDateAfter(CommonConstants.OFFER_OPEN, false, LocalDateTime.now());
		return allOffers;
	}
	
	@Override
	public List<Offer> findAllWithFiltering(String sourceCurrency, Double amount, String destinationCurrency, Double destinationAmount,long userId, int page, int size) throws Exception{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Offer> criteriaQuery = criteriaBuilder.createQuery(Offer.class);
		Root<Offer> offerRoot = criteriaQuery.from(Offer.class);
		List<Predicate> predicateList = new ArrayList<>();
	
//		CriteriaQuery<Offer> select = criteriaQuery.select(offerRoot);
		if(sourceCurrency != null && !sourceCurrency.isEmpty()) {
			Predicate predicatesourceCurrency = criteriaBuilder.equal(offerRoot.get("sourceCurrency"), sourceCurrency);	
			predicateList.add(predicatesourceCurrency);
		}
		if(destinationCurrency != null && !destinationCurrency.isEmpty()) {
			Predicate predicatedestinationCurrency = criteriaBuilder.equal(offerRoot.get("destinationCurrency"), destinationCurrency);
			predicateList.add(predicatedestinationCurrency);
		}
		
		if(destinationAmount != null) {
			Predicate predicatedestinationAmount = criteriaBuilder.equal(offerRoot.get("destinationAmount"), destinationAmount);
			predicateList.add(predicatedestinationAmount);
		}
		if(amount != null) {
			Predicate predicateamount = criteriaBuilder.equal(offerRoot.get("amount"), amount);
			predicateList.add(predicateamount);
		}
		//findByStatusAndIsCounterOfferAndExpirationDateAfter
		predicateList.add(criteriaBuilder.equal(offerRoot.get("status"), CommonConstants.OFFER_OPEN));
		predicateList.add(criteriaBuilder.equal(offerRoot.get("isCounterOffer"), false));
		predicateList.add(criteriaBuilder.notEqual(offerRoot.get("user"), userId));
		predicateList.add(criteriaBuilder.greaterThan(offerRoot.get("expirationDate"), LocalDateTime.now()));		
		criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
		TypedQuery<Offer> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page-1)*size);
		query.setMaxResults(size);
		List<Offer> items = query.getResultList();
		//List<Offer> items =  entityManager.createQuery(criteriaQuery).getResultList();
		//System.out.print(items);
		return items;
	}
	
	
	@Override
	public List<Offer> findAllWithoutUserOffer(Long userId) {
		List<Offer> allOffers = offerRepository.findByStatusAndIsCounterOfferAndExpirationDateAfterAndUser_IdNot(CommonConstants.OFFER_OPEN, false, LocalDateTime.now(), userId);
		
		if(allOffers.size()>0)
		{
			for(int i=0;i<allOffers.size();i++)
			{
				userService.setUserReputation(allOffers.get(i).getUser().getId());
			}
		}
		return allOffers;
	}
	
	@Override
	public Offer update(Offer offer) throws Exception {
		User user = offer.getUser();
		Optional<Offer> offerToBeUpdated = offerRepository.findByIdAndUserAndExpirationDateAfterAndStatus(offer.getId(), user,  LocalDateTime.now(),CommonConstants.OFFER_OPEN);
		if(!offerToBeUpdated.isPresent()) {
			throw new InvalidRequestException("Invalid Request.Either this offer not exists or is no longer valid for update");	
		}
		if(offerToBeUpdated.get().getIsCounterOffer()) {
			throw new InvalidRequestException("Counter offer can not be updated.");
		}
		copyNonNullProperties(offer, offerToBeUpdated.get());
		offer = offerToBeUpdated.get();
		if(offer.getDestinationCurrency().equals(offer.getSourceCurrency())) {
			throw new InvalidRequestException(" Source and destination Currency should not be same.");	
		}
		if(!checkDestinationBankAccountExists(user, offer.getSourceCurrency(), "Send")) {
			throw new InvalidRequestException("User does not have source account with required permission");		
		}
		
		if(!checkDestinationBankAccountExists(user, offer.getDestinationCurrency(), "Receive")) {
			throw new InvalidRequestException("User does not have destination account with required permission");			
		}
		return offerRepository.save(offer);
	}

	@Override
	public boolean acceptOfferFromBrosePage(Long id,User user) throws Exception{
		Optional<Offer> acceptedOfferOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(id, CommonConstants.OFFER_OPEN , LocalDateTime.now());
		if(!acceptedOfferOptional.isPresent()) {
			throw new InvalidRequestException("Offer does not exists or is no longer in Open state.");				
		}
		
		Offer acceptedOffer = acceptedOfferOptional.get();
		
		if(!checkDestinationBankAccountExists(user, acceptedOffer.getDestinationCurrency(), "Send")) {
			throw new InvalidRequestException("User does not have source account with required permission");		
		}
		
		if(!checkDestinationBankAccountExists(user, acceptedOffer.getSourceCurrency(), "Receive")) {
			throw new InvalidRequestException("User does not have destination account with required permission");			
		}
		
		if(acceptedOffer.getUser().equals(user)) {
			throw new InvalidRequestException(" Offer Creator and acceptor cannot be the same person");				
		}
		saveTransaction(acceptedOffer, user);
		
		deleteAllCounterOffer(acceptedOffer);
		acceptedOffer.setStatus(CommonConstants.OFFER_INTRANSACTION);
		acceptedOffer.setOfferAcceptor(user);
		offerRepository.save(acceptedOffer);
		return true;
	}
	
	@Override
	public boolean acceptCounterOfferFromBrosePage(Long offerId) throws Exception{
		
		Optional<Offer> acceptedCounterOfferOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(offerId, CommonConstants.OFFER_OPEN , LocalDateTime.now());
		if(!acceptedCounterOfferOptional.isPresent()) {
			throw new InvalidRequestException("Offer does not exists.");				
		}
		Offer acceptedCounterOffer = acceptedCounterOfferOptional.get();
		if(acceptedCounterOffer.getIsSplitOfferAllowed()) {
			List<Long> l = new ArrayList<>();
			l.add(acceptedCounterOffer.getMatchingOffer().getId());
			l.add(offerId);
			l.add(acceptedCounterOffer.getSplitMatchedOffer().getId());
			if(acceptedCounterOffer.getSpitType()) {
				return acceptSplitOfferFromMyOfferBPlusC(l);
			}else {
				return acceptSplitOfferFromMyOfferCMinusB(l);
			}
		}
		if(acceptedCounterOffer.getUser().equals(acceptedCounterOffer.getParentOffer().getUser())) {
			throw new InvalidRequestException("Offer Creator and acceptor cannot be the same person");				
		}
				
		List<Offer> counterOffers = findCounterOffers(acceptedCounterOffer.getParentOffer().getId()); 
		for(Offer counterOffer:counterOffers) {
			if(counterOffer.getHasMatchingOffer()) {
				Optional<Offer> matchedOffer = offerRepository.findByIdAndStatusAndExpirationDateAfter(counterOffer.getMatchingOffer().getId(), CommonConstants.OFFER_COUNTERMADE, LocalDateTime.now());
				if(counterOffer.getId() == acceptedCounterOffer.getId())
					matchedOffer.get().setStatus(CommonConstants.OFFER_EXPIRED);
				else
					matchedOffer.get().setStatus(CommonConstants.OFFER_OPEN);
				offerRepository.save(matchedOffer.get());
			}
			if(counterOffer.getId() == acceptedCounterOffer.getId()) continue;
			counterOffer.setStatus(CommonConstants.OFFER_EXPIRED);
			offerRepository.save(counterOffer);
		}

		Transactions t1 = new Transactions(
				acceptedCounterOffer.getParentOffer().getUser(),
				acceptedCounterOffer.getUser(),
				acceptedCounterOffer.getAmount(),
				acceptedCounterOffer.getAmount()*0.95*acceptedCounterOffer.getExchangeRate(),
				acceptedCounterOffer.getSourceCurrency(),
				acceptedCounterOffer.getDestinationCurrency(),
				acceptedCounterOffer.getAmount()*0.05,
				acceptedCounterOffer);
		Transactions t2 = new Transactions(
				acceptedCounterOffer.getUser(),
				acceptedCounterOffer.getParentOffer().getUser(),
				acceptedCounterOffer.getAmount()*acceptedCounterOffer.getExchangeRate(), 
				acceptedCounterOffer.getAmount()*0.95,
				acceptedCounterOffer.getDestinationCurrency(),
				acceptedCounterOffer.getSourceCurrency(),
				acceptedCounterOffer.getAmount()*acceptedCounterOffer.getExchangeRate()*0.05,
				acceptedCounterOffer);
		t1.setExpirationDate(null);
		t2.setExpirationDate(null);

		transactionsRepository.save(t1);
		transactionsRepository.save(t2);
		acceptedCounterOffer.setStatus(CommonConstants.OFFER_INTRANSACTION);
		acceptedCounterOffer.setOfferAcceptor(acceptedCounterOffer.getParentOffer().getUser());
		offerRepository.save(acceptedCounterOffer);
		return true;
	}
	
	
	@Override
	public boolean acceptOfferFromMyOffer(Long myOfferId, Long acceptedOfferId) throws Exception {
		Optional<Offer> myOfferOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(myOfferId, CommonConstants.OFFER_OPEN , LocalDateTime.now());
		Optional<Offer> acceptedOfferOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(acceptedOfferId, CommonConstants.OFFER_OPEN , LocalDateTime.now());
		if(!myOfferOptional.isPresent()) {
			throw new InvalidRequestException("InValid Offer.");
		}
		if(!acceptedOfferOptional.isPresent()) {
			throw new InvalidRequestException("InValid Offer request");
		}	
		Offer myOffer = myOfferOptional.get();
		Offer acceptedOffer = acceptedOfferOptional.get();
		deleteAllCounterOffer(myOffer);
		deleteAllCounterOffer(acceptedOffer);
		myOffer.setStatus(CommonConstants.OFFER_EXPIRED);
		acceptedOffer.setStatus(CommonConstants.OFFER_INTRANSACTION);
		acceptedOffer.setOfferAcceptor(myOffer.getUser());
		offerRepository.save(myOffer);
		offerRepository.save(acceptedOffer);
		saveTransaction(acceptedOffer, myOffer.getUser());
		return true;
	}
	
	/*
	 *  A = $1000 , RS 70000
	 *  B = Rs 35000 intoExchange  $500
	 *  C = Rs 35000 intoexchange  $500
	 * */
	@Override
	public boolean acceptSplitOfferFromMyOfferBPlusC(List<Long> offers) throws Exception {
		Optional<Offer> acceptedOfferOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(offers.get(0), CommonConstants.OFFER_OPEN , LocalDateTime.now());
		Optional<Offer> offerBOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(offers.get(1), CommonConstants.OFFER_OPEN , LocalDateTime.now());
		Optional<Offer> offerCOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(offers.get(2), CommonConstants.OFFER_OPEN , LocalDateTime.now());
		
		Offer acceptedOffer = acceptedOfferOptional.get();
		Offer offerB = offerBOptional.get();
		Offer offerC = offerCOptional.get();
		double acceptSendingAmount = offerB.getAmount() * offerB.getExchangeRate() + offerC.getAmount() * offerC.getExchangeRate();
		double acceptReceivingAmount = offerB.getAmount() * 0.95 + offerC.getAmount() * 0.95;
		return doSPlitOffer(acceptSendingAmount, acceptReceivingAmount, acceptedOffer, offerB, offerC);
	}
	/*
	 *  A = C.destination - B.amount, (C.amount - B.destinationAmount) * 0.50
	 *  B = Rs 35000 ,  $500
	 *  C = $1000  , Rs 70,000
	 * 
	 * */
	@Override
	public boolean acceptSplitOfferFromMyOfferCMinusB(List<Long> offers) throws Exception {
		Optional<Offer> acceptedOfferOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(offers.get(0), CommonConstants.OFFER_OPEN , LocalDateTime.now());
		Optional<Offer> offerBOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(offers.get(1), CommonConstants.OFFER_OPEN , LocalDateTime.now());
		Optional<Offer> offerCOptional = offerRepository.findByIdAndStatusAndExpirationDateAfter(offers.get(2), CommonConstants.OFFER_OPEN , LocalDateTime.now());
		
		Offer acceptedOffer = acceptedOfferOptional.get();
		Offer offerB = null;
		Offer offerC = null;
		if(acceptedOffer.getSourceCurrency() == offerBOptional.get().getSourceCurrency()) {
			 offerB = offerBOptional.get();
			 offerC = offerCOptional.get();
		}else {
			 offerB = offerCOptional.get();
			 offerC = offerBOptional.get();
		}
		double acceptSendingAmount = offerC.getDestinationAmount() - offerB.getAmount();
		double acceptReceivingAmount = (offerC.getAmount() - offerB.getDestinationAmount()) * 0.95;
		return doSPlitOffer(acceptSendingAmount, acceptReceivingAmount, acceptedOffer, offerB, offerC);
	}

	public boolean doSPlitOffer(double acceptSendingAmount,double acceptReceivingAmount,Offer acceptedOffer,Offer offerB, Offer offerC) throws Exception {
		Transactions t1 = new Transactions(
				acceptedOffer.getUser(),
				offerB.getUser(), 
				acceptSendingAmount,
				acceptReceivingAmount,
				acceptedOffer.getSourceCurrency(),
				acceptedOffer.getDestinationCurrency(),
				acceptSendingAmount*0.05 ,
				acceptedOffer);
		t1.setExpirationDate(null);
		transactionsRepository.save(t1);
		saveTransaction(offerB,acceptedOffer);
		saveTransaction(offerC,acceptedOffer);
		deleteAllCounterOffer(acceptedOffer);
		deleteAllCounterOffer(offerB);
		deleteAllCounterOffer(offerC);
		offerB.setStatus(CommonConstants.OFFER_EXPIRED);
		offerC.setStatus(CommonConstants.OFFER_EXPIRED);
		acceptedOffer.setStatus(CommonConstants.OFFER_INTRANSACTION);
		acceptedOffer.setOfferAcceptor(offerB.getUser());
		offerRepository.save(acceptedOffer);
		offerRepository.save(offerB);
		offerRepository.save(offerC);
		return true;
	}
	
	public void saveTransaction(Offer currOffer,Offer acceptedOffer) {
		Transactions t1 = new Transactions(
				currOffer.getUser(),
				acceptedOffer.getUser(), 
				currOffer.getAmount(),
				currOffer.getDestinationAmount()*0.95,
				currOffer.getSourceCurrency(),
				currOffer.getDestinationCurrency(),
				currOffer.getAmount()*0.05 ,
				acceptedOffer);
		t1.setExpirationDate(null);
		transactionsRepository.save(t1);
		
	}
	
	public void saveTransaction(Offer acceptedOffer, User user) {
		Transactions t1 = new Transactions(
				acceptedOffer.getUser(),
				user,
				acceptedOffer.getAmount(),
				acceptedOffer.getAmount()*0.95*acceptedOffer.getExchangeRate(),
				acceptedOffer.getSourceCurrency(),
				acceptedOffer.getDestinationCurrency(),
				acceptedOffer.getAmount()*0.05 ,
				acceptedOffer);
		Transactions t2 = new Transactions(
				user,
				acceptedOffer.getUser(),
				acceptedOffer.getDestinationAmount(), 
				acceptedOffer.getAmount()*0.95,
				acceptedOffer.getDestinationCurrency(),
				acceptedOffer.getSourceCurrency(),
				acceptedOffer.getDestinationAmount()*0.05,
				acceptedOffer);
		t1.setExpirationDate(null);
		t2.setExpirationDate(null);
		transactionsRepository.save(t1);
		transactionsRepository.save(t2);
		
		userService.setUserReputation(acceptedOffer.getUser().getId());
		userService.setUserReputation(user.getId());
	}
	@Override
	public void delete(Long id) throws Exception {
		Optional<Offer> offerOptional = offerRepository.findByIdAndStatus(id, CommonConstants.OFFER_OPEN);
		if(!offerOptional.isPresent()) {
			throw new InvalidRequestException("Offer with given id does not exists.");		
		}
		Offer offerToBeDeleted = offerOptional.get();
		if(offerToBeDeleted.getIsCounterOffer()) {
			deleteCounterOffer(offerToBeDeleted);
		}else {
			deleteAllCounterOffer(offerToBeDeleted);	
			offerToBeDeleted.setStatus(CommonConstants.OFFER_EXPIRED);
			offerRepository.save(offerToBeDeleted);
		}
	}
	
	private void deleteCounterOffer(Offer offer) {
		if(offer.getHasMatchingOffer()) {
			Optional<Offer> matchedOffer = offerRepository.findByIdAndStatusAndExpirationDateAfter(offer.getMatchingOffer().getId(), CommonConstants.OFFER_COUNTERMADE, LocalDateTime.now());		
			matchedOffer.get().setStatus(CommonConstants.OFFER_OPEN);
			offerRepository.save(matchedOffer.get());
		}
		offer.setStatus(CommonConstants.OFFER_EXPIRED);
		offerRepository.save(offer);
	}
	private void deleteAllCounterOffer(Offer offerToBeDeleted) throws Exception {
		List<Offer> counterOffers = findCounterOffers(offerToBeDeleted.getId()); 
		for(Offer counterOffer:counterOffers) {
			deleteCounterOffer(counterOffer);
		}
	}

	@Override
	public List<Offer> findOffersForUser(Long userId) throws Exception{
		return offerRepository.findByUser_IdAndIsCounterOfferAndExpirationDateAfterOrderByStatusAsc(userId, false, LocalDateTime.now());
	}
	
	@Override
	public List<Offer> findCounterOffers(Long id, Long userId) throws Exception{
		 return offerRepository.findByParentOffer_IdAndStatusAndExpirationDateAfterAndUser_IdNot(id, CommonConstants.OFFER_OPEN, LocalDateTime.now(), userId);	
	}
	
	public List<Offer> findCounterOffers(Long id) throws Exception{
		 return offerRepository.findByParentOffer_IdAndStatusAndExpirationDateAfter(id, CommonConstants.OFFER_OPEN, LocalDateTime.now());	
	}

	@Override
	public HashMap<String, Object> getMatchingOffer(Long id, Long userId) throws Exception {
		Optional<Offer> offerOptional = offerRepository.findByIdAndIsCounterOfferAndStatusAndExpirationDateAfter(id,false, CommonConstants.OFFER_OPEN, LocalDateTime.now());
		if(!offerOptional.isPresent()) {
			throw new InvalidRequestException("Offer with given id does not exists.");
		}
		Offer offer = offerOptional.get();
		HashMap<String,Object> map = new HashMap<>();
		
		double amount = offer.getAmount() * offer.getExchangeRate();
		List<Offer> exactMatch = offerRepository.findBySourceCurrencyAndDestinationCurrencyAndDestinationAmountAndStatusAndIsCounterOfferAndUser_IdNotAndExpirationDateAfter(offer.getDestinationCurrency(), offer.getSourceCurrency(), offer.getAmount() , CommonConstants.OFFER_OPEN, false,userId, LocalDateTime.now());
		map.put("exactMath", exactMatch);
		List<Offer> rangeMatch = offerRepository.findBySourceCurrencyAndDestinationCurrencyAndStatusAndDestinationAmountBetweenAndIsCounterOfferAndDestinationAmountNotAndUser_IdNotAndExpirationDateAfterOrderByAmountAsc(offer.getDestinationCurrency(), offer.getSourceCurrency(), CommonConstants.OFFER_OPEN, offer.getAmount()*0.90, offer.getAmount() *1.10, false, offer.getAmount(), userId, LocalDateTime.now());
		map.put("rangeMath", rangeMatch);
		if(offer.getIsSplitOfferAllowed()) {
			List<Offer> forSplitMatch = offerRepository.findBySourceCurrencyAndDestinationCurrencyAndStatusAndIsCounterOfferAndUser_IdNotAndExpirationDateAfterOrderByDestinationAmountAsc(offer.getDestinationCurrency(), offer.getSourceCurrency(), CommonConstants.OFFER_OPEN, false, userId, LocalDateTime.now());
			User user = new User();
			user.setId(userId);
			List<Offer> listOfC = offerRepository.getListOfC(user,offer.getDestinationCurrency(), offer.getSourceCurrency(), amount, CommonConstants.OFFER_OPEN, 0);
			List<Offer> listOfB = offerRepository.findBySourceCurrencyAndDestinationCurrencyAndStatusAndIsCounterOfferAndExpirationDateAfterAndIdNot(offer.getSourceCurrency(), offer.getDestinationCurrency(),CommonConstants.OFFER_OPEN, false, LocalDateTime.now(),id);
			List<List<Offer>> aEqualsBPlusC = new ArrayList<>();
			checkForAEqualsBPlusC(offer.getDestinationAmount(), forSplitMatch, aEqualsBPlusC);
			map.put("aEqualsBPlusC", aEqualsBPlusC);	
			List<List<Offer>> cMinusBEqulsA = new ArrayList<>();
			checkForCMinusBEqualsA(offer.getDestinationAmount(),listOfC,  listOfB, cMinusBEqulsA);
			map.put("cMinusBEqulsA", cMinusBEqulsA);	
		}
		
		if(offer.getIsCounterOfferAllowed()) {
			map.put("counterOffer", findCounterOffers(id, userId));
		}
		return map;
	}


	private void checkForCMinusBEqualsA(double amount, List<Offer> listOfC, List<Offer> listOfB,
			List<List<Offer>> splitMathes) {
		int size = listOfC.size();
		for(int i=0;i<size;i++) {
			for(int j=0; j<listOfB.size(); j++) {
				double sum = listOfC.get(i).getAmount() - listOfB.get(j).getDestinationAmount();
				if(sum >= amount * 0.90 && sum <= amount * 1.10) {
					List<Offer> temp= new ArrayList<>();
					temp.add(listOfC.get(i));
					temp.add(listOfB.get(j));
					splitMathes.add(temp);
				}
			}
		}
	}

	private void checkForAEqualsBPlusC(double amount, List<Offer> forSplitMatch, List<List<Offer>> splitMathes) {
		int size = forSplitMatch.size();
		for(int i=0;i< size-1;i++) {
			int j= size-1;
			while(i<j) {
				double sum = forSplitMatch.get(i).getAmount() + forSplitMatch.get(j).getAmount();
				if(sum >= amount * 0.90 && sum <= amount * 1.10) {
					List<Offer> temp= new ArrayList<>();
					temp.add(forSplitMatch.get(i));
					temp.add(forSplitMatch.get(j));
					splitMathes.add(temp);
				}else {
					break;
				}
				j--;
			}
		}		
	}
	
	public boolean checkDestinationBankAccountExists(User user, String destimationCurrency, String type){
		Set<BankAccount> destinationAccounts= bankRepository.findByUserAndPrimaryCurrency(user, destimationCurrency);
		if(destinationAccounts.isEmpty()) return false;
		Iterator<BankAccount> itr = destinationAccounts.iterator();
		     while(itr.hasNext()){
		        BankAccount temp = itr.next();
		        if(temp.getSupportMethod().equals("Both") || temp.getSupportMethod().equals(type)) return true;
		}
		return false;
	}
	
	
	public static void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	    	if(pd.getName().equals("counterOffer")) continue;
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
}
