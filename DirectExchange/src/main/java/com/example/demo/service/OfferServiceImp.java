package com.example.demo.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonConstants;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.dao.BankRepository;
import com.example.demo.dao.OfferRepository;
import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.BankAccount;
import com.example.demo.dto.Offer;
import com.example.demo.dto.Transactions;
import com.example.demo.dto.User;

@Service
@Transactional
public class OfferServiceImp implements OfferService{

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private TransactionsRepository transactionsRepository;

	@Override
	public Offer add(Offer offer) throws InvalidRequestException{
		User user = offer.getUser();
		if(!user.getIsVerified()) {
			throw new InvalidRequestException("User is not Verified.");
		}
	
		if(offer.getDestinationCurrency().equals(offer.getSourceCurrency())) {
			throw new InvalidRequestException(" Source and destination Currency should not be same.");	
		}
		if(!checkDestinationBankAccountExists(user, offer.getSourceCurrency(), "Send")) {
			throw new InvalidRequestException("User does not have source account with required permission");		
		}
		
		if(!checkDestinationBankAccountExists(user, offer.getDestinationCurrency(), "Receive")) {
			throw new InvalidRequestException("User does not have destination account with required permission");			
		}
		
		if(offer.isCounterOffer()) {
			Optional<Offer> temp = offerRepository.findById(offer.getMatchingOffer().getId());
			if(!temp.isPresent()) {
				throw new InvalidRequestException("InValid Request. Matching offer mismatch.");
			}
			if(!offer.getSourceCurrency().equals(temp.get().getDestinationCurrency()) || !offer.getDestinationCurrency().equals(temp.get().getSourceCurrency())) {
				throw new InvalidRequestException("InValid Request for counter offer. Source and Destination currency does not match");				
			}
			double lowerLimit = temp.get().getAmount() * offer.getExchangeRate() * 0.90;
			double upperLimit = temp.get().getAmount() * offer.getExchangeRate() * 1.10; 
			if(offer.getAmount() < lowerLimit || offer.getAmount() > upperLimit) {
				throw new InvalidRequestException("InValid Request for counter offer. Amount exceeds 10% lower or upper bound");						
			}
			if(offer.isHasMatchingOffer()) {
				temp.get().setStatus(CommonConstants.OFFER_INACTIVE);
				offerRepository.save(temp.get());				
			}
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
		List<Offer> allOffers = offerRepository.findByStatusAndIsCounterOffer(CommonConstants.OFFER_ACTIVE, false);
		return allOffers;
	}
	
	@Override
	public Offer update(Offer offer) throws Exception {
		User user = offer.getUser();
		Optional<Offer> offerToBeUpdated = offerRepository.findByIdAndUserAndStatus(offer.getId(), user, CommonConstants.OFFER_ACTIVE);
		if(!offerToBeUpdated.isPresent()) {
			throw new InvalidRequestException("Invalid Request.Either this offer not exists or is no longer valid for update");	
		}
		if(offerToBeUpdated.get().isCounterOffer()) {
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
	public Offer delete(Long id) throws Exception {
		return delete(id,true);
	}
	
	public Offer delete(Long id, boolean flag) throws Exception {
		Optional<Offer> offerOptional = offerRepository.findByIdAndStatus(id, 1);
		if(!offerOptional.isPresent()) {
			throw new InvalidRequestException("Offer with given id does not exists.");		
		}
		Offer offerToBeDeleted = offerOptional.get();
		if(offerToBeDeleted.isCounterOffer() && offerToBeDeleted.isHasMatchingOffer()) {
			Optional<Offer> parentOffer = offerRepository.findById(offerToBeDeleted.getMatchingOffer().getId());
			if(!parentOffer.isPresent()) {
				throw new InvalidRequestException(" Cound not find parent offer.");		
			}
			if(flag) 
				parentOffer.get().setStatus(CommonConstants.OFFER_ACTIVE);
			else
				parentOffer.get().setStatus(CommonConstants.OFFER_INACTIVE);
			offerRepository.save(parentOffer.get());
		}
		offerToBeDeleted.setStatus(CommonConstants.OFFER_INACTIVE);
		return offerRepository.save(offerToBeDeleted);
	}
	
	@Override
	public List<Offer> findOffersForUser(Long userId) throws Exception{
		return offerRepository.findByUser_IdOrderByStatusAsc(userId);
	}
	
	//Can have Transaction Object.
	@Override
	public boolean acceptOffer(Set<Offer> offers) throws Exception {
		
		Iterator<Offer> itr = offers.iterator();
	     while(itr.hasNext()) {
	    	 Offer offer = itr.next();
	    	 Optional<Offer> offerOptional = offerRepository.findByIdAndStatus(offer.getId(), CommonConstants.OFFER_ACTIVE);
	    	 if(!offerOptional.isPresent()) {
	    			throw new InvalidRequestException(" Offer is not valid.");		
	    	 }
	   		 offer = offerOptional.get();
	   		 List<Offer> counterOffers = offerRepository.findByParentOfferAndStatus(offer, CommonConstants.OFFER_ACTIVE);
	   		 for(Offer temp : counterOffers) {
	   			 try {
					delete(temp.getId(), false);
				} catch (Exception e) {
					throw e;
				}
	   		 }
	    	 Transactions tran = new Transactions();
		     tran.setDestinationCurrency(offer.getDestinationCurrency());
		     tran.setSourceCurrency(offer.getSourceCurrency());
		     tran.setOffer(offer);
		     tran.setSender(offer.getUser());
		     tran.setSendingAmount(offer.getAmount());
		     tran.setRecevingAmount(offer.getAmount() * offer.getExchangeRate() * 0.95);
		     tran.setStatus(false);
		     transactionsRepository.save(tran);
		     offer.setStatus(CommonConstants.OFFER_FULFILLED);
		     //offer.setStatus(2);
		     offerRepository.save(offer);	
	     }
	     
		return false;
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
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}

}
