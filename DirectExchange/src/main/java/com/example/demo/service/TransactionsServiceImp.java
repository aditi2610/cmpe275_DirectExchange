package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonConstants;
import com.example.demo.dao.OfferRepository;
import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.Transactions;

@Service
public class TransactionsServiceImp implements ITransactionsService {

	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Autowired
	private OfferRepository offerRepository;

	@Override
	public List<Transactions> getAllTransaction(Long userId) {
		
		return transactionsRepository.findBySender_IdAndExpirationDateAfterAndStatus(userId, LocalDateTime.now(), CommonConstants.TRANSACTION_OPEN);
	}

	@Override
	public boolean changeTransactionStatus(Long id) throws Exception {
		Optional<Transactions> transOptional = transactionsRepository.findByIdAndExpirationDateAfterAndStatus(id, LocalDateTime.now(), CommonConstants.TRANSACTION_OPEN);
		if(!transOptional.isPresent()) {
			throw new Exception("InValid Request. Transaction with given id no longer exists.");
		}
		transOptional.get().setStatus(CommonConstants.TRANSACTION_SUCCESS);
		transactionsRepository.save(transOptional.get());
		List<Transactions> transList= transactionsRepository.findByOfferAndStatus(transOptional.get().getOffer(), CommonConstants.TRANSACTION_OPEN);
		if(transList.isEmpty()) {
			transOptional.get().getOffer().setStatus(CommonConstants.OFFER_FULFILLED);
			offerRepository.save(transOptional.get().getOffer());
			return true;
		}
		return false;
	}
}
