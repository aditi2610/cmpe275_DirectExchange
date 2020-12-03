package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Transactions;

@Service
public interface ITransactionsService {

	List<Transactions> getAllTransaction(Long userId);

	boolean changeTransactionStatus(Long id) throws Exception;

}
