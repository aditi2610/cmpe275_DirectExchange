package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.service.IBankAccountService;
import com.example.demo.service.IExchangeRateService;



@CrossOrigin
@RestController
public class ControllerClass {

	@Autowired
	private IBankAccountService BankAccountService;
	@Autowired
	private IExchangeRateService ExchangeService;

	
//Adding New Bank Account
@RequestMapping(value="bankaccount" , method = RequestMethod.POST)	
public ResponseEntity<?> addBankAccount(
		@RequestParam(value="bankName",required=false) String bankName,
		@RequestParam(value="country",required=false) String country,
		@RequestParam(value="accountNumber",required=false) String accountNumber,
		@RequestParam(value="ownerName",required=false) String ownerName,
		@RequestParam(value="ownerAddress",required=false) String ownerAddress,
		@RequestParam(value="primaryCurrency",required=false) String primaryCurrency,
		@RequestParam(value="supportMethod",required=false) String supportMethod,
		@RequestParam(value="userId",required=false) Long userId
		
		) 
{ 

	ResponseEntity<?> res = null;
	try {
System.out.println(bankName+" "+"country "+country+accountNumber+ownerName+ownerAddress+primaryCurrency+supportMethod+userId);
		res = BankAccountService.addBankAccount(bankName, country, accountNumber, ownerName, ownerAddress,  primaryCurrency,supportMethod, userId);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return res;
}
//Getting All Bank Accounts for user

	@RequestMapping(value="bankaccount/{userId}" , method = RequestMethod.GET)	
	public ResponseEntity<?> getPlayer(@PathVariable("userId") Long userId)

	{ 
		ResponseEntity<?> res = null;
		try {
			res = BankAccountService.getAccounts(userId);
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		return res;
	}
	
	//Adding All exchange rates
	
	@RequestMapping(value="exchangerate" , method = RequestMethod.POST)	
	public ResponseEntity<?> addExchangeRate(
			@RequestParam(value="country",required=false) String country,
			@RequestParam(value="currencyCode",required=false) String currencyCode,
			@RequestParam(value="rate",required=false) Double rate
			
			
			) 
	{ 

		ResponseEntity<?> res = null;
		try {
			res = ExchangeService.addExchangeRate(country,currencyCode, rate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	//Getting All Exchange rates

		@RequestMapping(value="exchangerate" , method = RequestMethod.GET)	
		public ResponseEntity<?> getExchangeRates()

		{ 
			ResponseEntity<?> res = null;
			try {
				res = ExchangeService.getExchangeRates();
			} catch (Exception e) {
				e.printStackTrace();
				
			}

			return res;
		}

}