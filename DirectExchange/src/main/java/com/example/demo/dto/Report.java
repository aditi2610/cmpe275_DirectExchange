package com.example.demo.dto;

import java.time.LocalDateTime;

public class Report {



	private Double servieFee;
	private Double sendingAmount;


	private Double recevingAmount;

	private String sourceCurrency;

	private String destinationCurrency;
	
	private LocalDateTime creationDate;
	
	private Double exchangeRate;
	
	private double totalSendingAmount;
	private double totalReceivingAmount;
	private double totalServiceFee;
	
	public Report() {}
	
	public Report(double sendingAm, double recAmount, double ser){
		this.totalSendingAmount = sendingAm;
		this.totalReceivingAmount= recAmount;
		this.totalServiceFee = ser;
	}
	

	public double getTotalSendingAmount() {
		return totalSendingAmount;
	}

	public void setTotalSendingAmount(double totalSendingAmount) {
		this.totalSendingAmount = totalSendingAmount;
	}

	public double getTotalReceivingAmount() {
		return totalReceivingAmount;
	}

	public void setTotalReceivingAmount(double totalReceivingAmount) {
		this.totalReceivingAmount = totalReceivingAmount;
	}

	public double getTotalServiceFee() {
		return totalServiceFee;
	}

	public void setTotalServiceFee(double totalServiceFee) {
		this.totalServiceFee = totalServiceFee;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}


	public Double getServieFee() {
		return servieFee;
	}

	public void setServieFee(Double servieFee) {
		this.servieFee = servieFee;
	}

	public Double getSendingAmount() {
		return sendingAmount;
	}

	public void setSendingAmount(Double sendingAmount) {
		this.sendingAmount = sendingAmount;
	}

	public Double getRecevingAmount() {
		return recevingAmount;
	}

	public void setRecevingAmount(Double recevingAmount) {
		this.recevingAmount = recevingAmount;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency(String destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return destinationCurrency + " "  + recevingAmount + " " + sourceCurrency + " " + sendingAmount+ " " + exchangeRate;
	}
	
	
	
}
