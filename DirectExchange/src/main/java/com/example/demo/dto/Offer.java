package com.example.demo.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Offer")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private long id;
	
	@Column(name= "SourceCountry", nullable = false)
	private String sourceCountry;
	
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
	private Date expirationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name= "isCounterOfferAllowed", nullable = false)
	private boolean isCounterOfferAllowed;
	
	@Column(name= "isSplitOfferAllowed", nullable = false)
	private boolean isSplitOfferAllowed;

	@Column(name="status")
	private int status;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "user", referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "offer")
	private Transactions transaction;
	
	public long getOfferId() {
		return id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setOfferId(long offerId) {
		this.id = offerId;
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

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isCounterOfferAllowed() {
		return isCounterOfferAllowed;
	}

	public void setCounterOfferAllowed(boolean isCounterOfferAllowed) {
		this.isCounterOfferAllowed = isCounterOfferAllowed;
	}

	public boolean isSplitOfferAllowed() {
		return isSplitOfferAllowed;
	}

	public void setSplitOfferAllowed(boolean isSplitOfferAllowed) {
		this.isSplitOfferAllowed = isSplitOfferAllowed;
	}

	public Transactions getTransaction() {
		return transaction;
	}

	public void setTransaction(Transactions transaction) {
		this.transaction = transaction;
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
        if (!(obj instanceof Offer)) {
            return false;
        }
        Offer that = (Offer) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(id, that.id);
        return eb.isEquals();
    }
	
}
