package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonConstants;
import com.example.demo.dao.OfferRepository;
import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.Report;
import com.example.demo.dto.Result;
import com.example.demo.dto.Transactions;

@Service
public class TransactionsServiceImp implements ITransactionsService {

	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Autowired
	private OfferRepository offerRepository;

	@Override
	public List<Transactions> getAllTransaction(Long userId) {
		
		return transactionsRepository.findBySender_Id(userId);
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

	@Override
	public List<Report> getTransactionHistory(Long sender_id) {
//		System.out.println("Service IMPL getTransactionHistory");
		List<Transactions> transOptional= transactionsRepository.findByTransactionHistory(sender_id);
		List<Report> reportme = new ArrayList<Report>();
		for(Transactions t : transOptional) {
			Report r = new Report();
			r.setCreationDate(t.getCreationDate());
			r.setDestinationCurrency(t.getDestinationCurrency());
			r.setRecevingAmount( t.getRecevingAmount());
			r.setServieFee(t.getServieFee());
			r.setSourceCurrency(t.getSourceCurrency());
			r.setSendingAmount(t.getSendingAmount());
			r.setExchangeRate(t.getOffer().getExchangeRate());
			reportme.add(r);
		}
		
		return reportme ;
	}

	@Override
	public List<Result> generateReport() {
//		System.out.println("Service IMPL generateReport");
		List<Object[]> transOptional= transactionsRepository.generateReport();
		List<Result> list = new ArrayList<>();
		for(Object[]  object: transOptional) {
			Result r = new Result((Double)object[0], (Double)object[1], ((Number)object[2]).longValue(), (Integer)object[3] );
			list.add(r);
		}
//		System.out.println("lets see");
//		System.out.println(list.toString());
		return list;
	}

	@Override
	public List<Report> getTransactionHistoryStats(Long senderId) {
//		System.out.println("Service IMPL getTransactionHistoryStats");
		List<Object[]> transOptional= transactionsRepository.findByTransactionHistoryStats(senderId);
		List<Report> reportme = new ArrayList<Report>();
		for(Object[]  object: transOptional) {
			Report r = new Report((Double)object[0], (Double)object[1], (Double)object[2] );
			reportme.add(r);
		}
		
		return reportme ;
	}
}
