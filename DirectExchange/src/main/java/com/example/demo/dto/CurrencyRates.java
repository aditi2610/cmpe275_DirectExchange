package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CurrencyRates")
public class CurrencyRates {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private long id;
	
	@Column(name="country")
	private String country;
	
	@Column(name="currencyCode")
	private String currencyCode;

	@Column(name="rate")
	private double rate;
	
	public CurrencyRates()
	{
		
	}
	public CurrencyRates(String country, String currencyCode, double rate) {
		// TODO Auto-generated constructor stub
		
		this.country=country;
		this.currencyCode=currencyCode;
		this.rate=rate;
		
		
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}
