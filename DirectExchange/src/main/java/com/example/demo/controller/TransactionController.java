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
import com.example.demo.dto.Report;
import com.example.demo.dto.Result;
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
	
	@RequestMapping(value="transactions/history/{senderId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTransactionHistory(@PathVariable("senderId") Long senderId){
//		System.out.println("INside getTransactionHistory");
		List<Report> transactionsList;
		try {
			transactionsList = transactionsService.getTransactionHistory(senderId);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(transactionsList,HttpStatus.OK);
	} 
	
	@RequestMapping(value="transactions/report", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> generateReport(){
//		System.out.println("Inside generate report");
		List<Result> transactionsList;
		try {
			transactionsList =  transactionsService.generateReport();
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(transactionsList,HttpStatus.OK);
	} 
	
	@RequestMapping(value="transactions/historyStats/{senderId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTransactionHistoryStats(@PathVariable("senderId") Long senderId){
//		System.out.println("Inside getTransactionHistoryStats");
		List<Report> transactionsList;
		try {
			transactionsList = transactionsService.getTransactionHistoryStats(senderId);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(transactionsList,HttpStatus.OK);
	} 
	

}
