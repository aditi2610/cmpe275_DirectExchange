package com.example.demo.dto;

import java.time.LocalDateTime;

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
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User sender;

	@ManyToOne( fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User receiver;

	@Column(name = "servieFee")
	private Double servieFee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="offer", referencedColumnName ="id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Offer offer;
	
	@Column(name="sendingAmount")
	private Double sendingAmount;

	@Column(name="recevingAmount")
	private Double recevingAmount;
	
	@Column(name="sourceCurrency")
	private String sourceCurrency;
	
	@Column(name="destinationCurrency")
	private String destinationCurrency;
	
	//0 for inProgress, 1 successful
	@Column(name="status", columnDefinition = "int default 0")
	private int status = 0;
	
	@Column(name= "ExpirationDate", nullable = false)
	private LocalDateTime expirationDate;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false)
	private LocalDateTime creationDate = LocalDateTime.now();

	


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

	public Transactions(User sender, User reciever, double sendingAmount, double recevingAmount,
			String sourceCurrency, String destinationCurrency, double servieFee, Offer offer) {
		this.sender = sender;
		this.receiver = reciever;
		this.sendingAmount = sendingAmount;
		this.recevingAmount = recevingAmount;
		this.sourceCurrency = sourceCurrency;
		this.destinationCurrency = destinationCurrency;
		this.servieFee = servieFee;
		this.offer = offer;
	}
	
	Transactions(){}
	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Double getServieFee() {
		return servieFee;
	}

	public void setServieFee(Double servieFee) {
		this.servieFee = servieFee;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		if(expirationDate == null) {
			this.expirationDate =LocalDateTime.now().plusMinutes(10);
		}else 
			this.expirationDate = expirationDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
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
