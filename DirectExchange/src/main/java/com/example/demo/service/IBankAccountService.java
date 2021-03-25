package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface IBankAccountService {
	
<<<<<<< HEAD
		ResponseEntity<?> addBankAccount(String bankName, String country,String accountNumber,String ownerName, String ownerAddress, String primaryCurrency, String supportMethod,Long userId); 
		
		ResponseEntity<?> getAccounts(Long userId); 
=======
		ResponseEntity<?> addBankAccount(String bankName, String country,String accountNumber,String ownerName, String ownerAddress, String primaryCurrency, int supportMethod,Long userId); 
>>>>>>> 5b1c93263d9dd52a659bf65bdce02ed85a36538f

		ResponseEntity<?> getBankAccount(Long id); 

		ResponseEntity<?> updateBankAccount(String bankName, String country,String accountNumber,String ownerName, String ownerAddress, String primaryCurrency, String supportMethod,Long userId);

		ResponseEntity<?> removeBankAccount(Long id); 
	
}
