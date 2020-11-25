package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BankAccount")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private long bankId;
	
	@Column(name= "BankName", nullable = false)
	private String bankName;
	
	@Column(name= "Country", nullable = false)
	private String country;
	
	@Column(name= "AccountNumber", nullable = false)
	private String accountNumber;
	
	@Column(name= "OwnerName", nullable = false)
	private String ownerName;
	
	@Column(name= "OwnerAddress", nullable = false)
	private String ownerAddress;
	
	@Column(name= "SupportMethod", nullable = false)
	private String supportMethod;

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public String getSupportMethod() {
		return supportMethod;
	}

	public void setSupportMethod(String supportMethod) {
		this.supportMethod = supportMethod;
	}
	
}
