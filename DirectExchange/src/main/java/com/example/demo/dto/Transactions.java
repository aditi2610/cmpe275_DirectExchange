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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author shahh
 *
 */
@Entity
@Table(name= "Transactions")
public class Transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private long id;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User sender;

//	@ManyToOne( fetch = FetchType.LAZY)
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	private User receiver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="offer", referencedColumnName ="id")
	private Offer offer;
	
	@Column(name="sendingAmount")
	private double sendingAmount;

	@Column(name="recevingAmount")
	private double recevingAmount;
	
	@Column(name="sourceCurrency")
	private String sourceCurrency;
	
	@Column(name="destinationCurrency")
	private String destinationCurrency;
	
	//0 for inProgress, 1 successful
	@Column(name="status")
	private boolean status;
	
	
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

//	public User getReceiver() {
//		return receiver;
//	}
//
//	public void setReceiver(User receiver) {
//		this.receiver = receiver;
//	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getSendingAmount() {
		return sendingAmount;
	}

	public void setSendingAmount(double sendingAmount) {
		this.sendingAmount = sendingAmount;
	}

	public double getRecevingAmount() {
		return recevingAmount;
	}

	public void setRecevingAmount(double recevingAmount) {
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
        if (!(obj instanceof Transactions)) {
            return false;
        }
        Transactions that = (Transactions) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(id, that.id);
        return eb.isEquals();
    }
}
