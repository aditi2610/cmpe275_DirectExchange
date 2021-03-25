package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CurrencyRatesRepository;
import com.example.demo.dto.CurrencyRates;
import com.example.demo.exception.InvalidRequestException;

@Service
public class ExchangeRateServiceImpl implements IExchangeRateService {

	@Autowired
	private CurrencyRatesRepository currencyRepository;
	
	@Override
	public ResponseEntity<?> addExchangeRate(String country, String currencyCode, double rate) throws InvalidRequestException {
		// TODO Auto-generated method stub
		CurrencyRates currencyRates=new CurrencyRates(country, currencyCode, rate);
		currencyRepository.save(currencyRates);
		return new ResponseEntity<>(currencyRates,HttpStatus.CREATED);
		
	}

	@Override
	public ResponseEntity<?> getExchangeRates() throws InvalidRequestException {
		// TODO Auto-generated method stub
		List<CurrencyRates> cr=new ArrayList<CurrencyRates>();
		cr=currencyRepository.findAll();
		return new ResponseEntity<>(cr,HttpStatus.OK);
	}

}
