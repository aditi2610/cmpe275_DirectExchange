package com.example.demo.dto;

public class Result {

	
	private Double convertedUSD;
	private Double totalFee;
	private Long totalOffers;
	private int status;
	
	 public Double getConvertedUSD() {
		return convertedUSD;
	}

	public void setConvertedUSD(Double convertedUSD) {
		this.convertedUSD = convertedUSD;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public long getTotalOffers() {
		return totalOffers;
	}

	public void setTotalOffers(long totalOffers) {
		this.totalOffers = totalOffers;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public Result( double cUSD, double tfee, long toffers, int status ) {
		this.convertedUSD = cUSD;
		this.totalFee = tfee;
		this.totalOffers = toffers;
		this.status = status;
	}

}
