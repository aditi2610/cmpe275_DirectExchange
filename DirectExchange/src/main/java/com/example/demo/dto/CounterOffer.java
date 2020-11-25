package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CounterOffer")
public class CounterOffer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private long counterOfferId;
	
	@Column(name= "SourceCountry", nullable = false)
	private String sourceCountry;
	
	public long getCounterOfferId() {
		return counterOfferId;
	}

	public void setCounterOfferId(long counterOfferId) {
		this.counterOfferId = counterOfferId;
	}

	public String getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency(String destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name= "SourceCurrency", nullable = false)
	private String sourceCurrency;
	
	@Column(name= "Amount", nullable = false)
	private double amount;
	
	@Column(name= "DestinationCountry", nullable = false)
	private String destinationCountry;
	
	@Column(name= "DestinationCurrency", nullable = false)
	private String destinationCurrency;
	
	@Column(name= "ExchangeRate", nullable = false)
	private double exchangeRate;
	
	@Column(name= "ExpirationDate", nullable = false)
	private String expirationDate;
	
	
}
