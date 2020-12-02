package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.exception.InvalidRequestException;

public interface IExchangeRateService {

	ResponseEntity<?> addExchangeRate(String country, String currencyCode, double rate) throws InvalidRequestException; 
	ResponseEntity<?> getExchangeRates()throws InvalidRequestException; 
}
