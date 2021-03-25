package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface IBankAccountService {
	
		ResponseEntity<?> addBankAccount(String bankName, String country,String accountNumber,String ownerName, String ownerAddress, String primaryCurrency, int supportMethod,Long userId); 

		ResponseEntity<?> getBankAccount(Long id); 

		ResponseEntity<?> updateBankAccount(String bankName, String country,String accountNumber,String ownerName, String ownerAddress, String primaryCurrency, String supportMethod,Long userId);

		ResponseEntity<?> removeBankAccount(Long id); 
	
}
