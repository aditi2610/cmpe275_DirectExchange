package com.example.demo.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Offer")
@DynamicUpdate
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private Long id;
	
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
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false)
	private Date creationDate = new Date();
	
	@JsonProperty
	@Column(name= "isCounterOfferAllowed", nullable = false)
	private boolean isCounterOfferAllowed;
	
	@JsonProperty
	@Column(name= "isSplitOfferAllowed", nullable = false)
	private boolean isSplitOfferAllowed;

	//0 for inActive, 1 for Active , 2  for Fulfilled
	@Column(name="status", columnDefinition = "integer default 1")
	private int status = 1;
	
//	@ManyToOne
//	private Offer parentOffer;
//	
	@ManyToOne(targetEntity = Offer.class)
    private Offer parentOffer;
    
    @Column(name="isCounterOffer")
    private boolean isCounterOffer;
    
    @ManyToOne(targetEntity = Offer.class)
    private Offer matchingOffer;
    
    @JsonProperty
    @Column(name="hasCounterParent", columnDefinition = "boolean default false")
    private Boolean hasMatchingOffer = false;
    
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "user", referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	@OneToMany(mappedBy = "offer")
	private Set<Transactions> transaction;
	
	public Offer getMatchingOffer() {
		return matchingOffer;
	}

	public void setMatchingOffer(Offer matchingOffer) {
		this.matchingOffer = matchingOffer;
	}

	public Boolean isHasMatchingOffer() {
		return hasMatchingOffer;
	}

	public void setHasMatchingOffer(Boolean hasMatchingOffer) {
		this.hasMatchingOffer = hasMatchingOffer;
	}

	public long getOfferId() {
		return id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Set<Transactions> getTransaction() {
		return transaction;
	}

	public void setTransaction(Set<Transactions> transaction) {
		this.transaction = transaction;
	}

	public Offer getParentOffer() {
		return parentOffer;
	}

	public void setParentOffer(Offer parentOffer) {
		this.parentOffer = parentOffer;
	}

	public boolean isCounterOffer() {
		return isCounterOffer;
	}

	public void setCounterOffer(boolean isCounterOffer) {
		this.isCounterOffer = isCounterOffer;
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
