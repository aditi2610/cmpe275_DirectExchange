package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")
@Entity
@Table(name="BankAccount")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private long id;
	
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
	
	@Column(name= "PrimaryCurrency", nullable = false)
	private String primaryCurrency;
	
	// Send , Receive  , Both 
	@Column(name= "SupportMethod", nullable = false)
	private String supportMethod;

	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "user", referencedColumnName = "id")
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	public BankAccount()
	{
		
	}
	public BankAccount(String bankName, String country, String accountNumber, String ownerName,
			String ownerAddress, String primaryCurrency, String supportMethod, User user) {
		// TODO Auto-generated constructor stub
		this.bankName=bankName;
		this.country=country;
		this.accountNumber=accountNumber;
		this.ownerName=ownerName;
		this.ownerAddress=ownerAddress;
		this.primaryCurrency=primaryCurrency;
		this.supportMethod=supportMethod;
		this.user=user;
		
		
	}

	public long getBankId() {
		return id;
	}

	public void setBankId(long id) {
		this.id = id;
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

	public String getPrimaryCurrency() {
		return primaryCurrency;
	}

	public void setPrimaryCurrency(String primaryCurrency) {
		this.primaryCurrency = primaryCurrency;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@JsonIgnoreProperties({"accounts", "offers","acceptedOffer"})
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);
        return hcb.toHashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BankAccount)) {
            return false;
        }
        BankAccount that = (BankAccount) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(id, that.id);
        return eb.isEquals();
    }
	
	
}
