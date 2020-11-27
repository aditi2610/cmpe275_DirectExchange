package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.service.IBankAccountService;



@RestController
public class ControllerClass {

	@Autowired
	private IBankAccountService BankAccountService;

	
//Adding New Player
@RequestMapping(value="bankaccount" , method = RequestMethod.POST)	
public ResponseEntity<?> addBankAccount(
		@RequestParam(value="bankName",required=false) String bankName,
		@RequestParam(value="country",required=false) String country,
		@RequestParam(value="accountNumber",required=false) String accountNumber,
		@RequestParam(value="ownerName",required=false) String ownerName,
		@RequestParam(value="ownerAddress",required=false) String ownerAddress,
		@RequestParam(value="primaryCurrency",required=false) String primaryCurrency,
		@RequestParam(value="supportMethod",required=false) Integer supportMethod,
		@RequestParam(value="userId",required=false) Long userId
		
		) 
{ 

	ResponseEntity<?> res = null;
	try {

		res = BankAccountService.addBankAccount(bankName, country, accountNumber, ownerName, ownerAddress,  primaryCurrency,supportMethod, userId);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return res;
}
}