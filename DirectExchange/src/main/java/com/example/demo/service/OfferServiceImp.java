package com.example.demo.service;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.InvalidRequestException;
import com.example.demo.dao.BankRepository;
import com.example.demo.dao.OfferRepository;
import com.example.demo.dto.BankAccount;
import com.example.demo.dto.Offer;
import com.example.demo.dto.User;

@Service
public class OfferServiceImp implements OfferService{

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	
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
	
	@Override
	public Offer add(Offer offer) throws InvalidRequestException{
		User user = offer.getUser();
		if(!user.getIsVerified()) {
			throw new InvalidRequestException("User is not Verified.");
		}
		
		if(!checkDestinationBankAccountExists(user, offer.getSourceCurrency(), "Send")) {
			throw new InvalidRequestException("User does not have source account with required permission");		
		}
		
		if(!checkDestinationBankAccountExists(user, offer.getDestinationCurrency(), "Receive")) {
			throw new InvalidRequestException("User does not have destination account with required permission");			
		}
		return offerRepository.save(offer);
	}

}
