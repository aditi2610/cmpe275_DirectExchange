package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BankRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.BankAccount;

import com.example.demo.dto.User;


@Service
public class BankAccountServiceImpl implements IBankAccountService {
	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public ResponseEntity<?> addBankAccount(String bankName, String country, String accountNumber, String ownerName,
			String ownerAddress, String primaryCurrency, int supportMethod, Long userId) {
		// TODO Auto-generated method stub
		User bankUser=null;
			Optional<User> user = userRepository.findById(userId);
			if(user!=null)
			{
				bankUser = user.get();
			}
			
			
		
		BankAccount bankAccount=new BankAccount(bankName, country, accountNumber, ownerName, ownerAddress, primaryCurrency, supportMethod, bankUser);
		bankRepository.save(bankAccount);
		return new ResponseEntity<>(bankAccount,HttpStatus.CREATED);
	}




	@Override
	public ResponseEntity<?> getBankAccount(Long id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public ResponseEntity<?> updateBankAccount(String bankName, String country, String accountNumber, String ownerName,
			String ownerAddress, String primaryCurrency, String supportMethod, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public ResponseEntity<?> removeBankAccount(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
