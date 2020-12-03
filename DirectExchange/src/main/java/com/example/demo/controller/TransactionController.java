package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.dto.Transactions;
import com.example.demo.service.ITransactionsService;

@RestController
public class TransactionController {

	@Autowired
	private ITransactionsService transactionsService;

	
	@RequestMapping(value="transactions/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllTransection(@PathVariable("userId") Long userId){
		List<Transactions> transactionsList;
		try {
			transactionsList = transactionsService.getAllTransaction(userId);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(transactionsList,HttpStatus.OK);
	} 
	
	@RequestMapping(value="transactions/apply/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changeTransactionStatus(@PathVariable("id") Long id){
		boolean flag;
		try {
			 flag = transactionsService.changeTransactionStatus(id);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return flag ? new ResponseEntity<>("Offer Fulfilled",HttpStatus.OK) : new ResponseEntity<>("Paid",HttpStatus.OK);
	} 
}
