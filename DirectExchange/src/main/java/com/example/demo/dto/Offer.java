package com.example.demo.dto;

import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
//property = "id")
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
	private Double amount;
	
	@Column(name= "destinationAmount")
	private Double destinationAmount;
	
	@Column(name= "DestinationCountry", nullable = false)
	private String destinationCountry;
	
	@Column(name= "DestinationCurrency", nullable = false)
	private String destinationCurrency;
	
	@Column(name= "ExchangeRate", nullable = false)
	private Double exchangeRate;
	
	@Column(name= "ExpirationDate", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL 5 MINUTE")
	private LocalDateTime expirationDate;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false)
	private LocalDateTime creationDate = LocalDateTime.now();
	
	@JsonProperty
	@Column(name= "isCounterOfferAllowed", nullable = false)
	private Boolean isCounterOfferAllowed = true;
	
	@JsonProperty
	@Column(name= "isSplitOfferAllowed", nullable = false)
	private Boolean isSplitOfferAllowed = true;

	//0 for inActive, 1 for Active , 2  for Fulfilled
	@Column(name="status", columnDefinition = "integer default 0")
	private int status = 0;
	
	@JsonProperty
    @Column(name="isCounterOffer")
    private Boolean isCounterOffer = false;
    
	@ManyToOne(targetEntity = Offer.class)
    private Offer parentOffer;
    
    @ManyToOne(targetEntity = Offer.class)
    private Offer matchingOffer;
    
    @JsonProperty
    @Column(name="hasCounterParent", columnDefinition = "boolean default false")
    private Boolean hasMatchingOffer = false;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user", referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User offerAcceptor;
	
	@JsonProperty
	@Column(name="isSplitMatch", columnDefinition = "boolean default false")
	private Boolean isSplitMatch = false;
	    
	 @ManyToOne(targetEntity = Offer.class)
	private Offer splitMatchedOffer;
		
	@JsonProperty
	@Column(name="splitType", columnDefinition = "boolean default false")
	private Boolean spitType;
	
	@OneToMany(mappedBy = "offer")
	private Set<Transactions> transaction;
	
	public Boolean getSpitType() {
		return spitType;
	}

	public void setSpitType(Boolean spitType) {
		this.spitType = spitType;
	}

	@JsonIgnoreProperties({"accounts", "offers", "senderTransections", "receiverTransections","acceptedOffer"})
	public User getOfferAcceptor() {
		return offerAcceptor;
	}

	public void setOfferAcceptor(User offerAcceptor) {
		this.offerAcceptor = offerAcceptor;
	}

	public Offer getSplitMatchedOffer() {
		return splitMatchedOffer;
	}

	public void setSplitMatchedOffer(Offer splitMatchedOffer) {
		this.splitMatchedOffer = splitMatchedOffer;
	}

	public Double getDestinationAmount() {
		return destinationAmount;
	}

	public void setDestinationAmount(Double destinationAmount) {
		this.destinationAmount = destinationAmount;
	}

	public Offer getMatchingOffer() {
		return matchingOffer;
	}

	public void setMatchingOffer(Offer matchingOffer) {
		this.matchingOffer = matchingOffer;
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

	@JsonIgnoreProperties({"accounts", "offers", "senderTransections", "receiverTransections","acceptedOffer"})
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		if(expirationDate == null) {
			this.expirationDate =LocalDateTime.now().plusMinutes(5);
		}else 
			this.expirationDate = expirationDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
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

	public Boolean getIsCounterOfferAllowed() {
		return isCounterOfferAllowed;
	}

	public void setIsCounterOfferAllowed(Boolean isCounterOfferAllowed) {
		this.isCounterOfferAllowed = isCounterOfferAllowed;
	}

	public Boolean getIsSplitOfferAllowed() {
		return isSplitOfferAllowed;
	}

	public void setIsSplitOfferAllowed(Boolean isSplitOfferAllowed) {
		this.isSplitOfferAllowed = isSplitOfferAllowed;
	}

	public Boolean getIsCounterOffer() {
		return isCounterOffer;
	}

	public void setIsCounterOffer(Boolean isCounterOffer) {
		this.isCounterOffer = isCounterOffer;
	}

	public Boolean getHasMatchingOffer() {
		return hasMatchingOffer;
	}

	public void setHasMatchingOffer(Boolean hasMatchingOffer) {
		this.hasMatchingOffer = hasMatchingOffer;
	}

	public Boolean getIsSplitMatch() {
		return isSplitMatch;
	}

	public void setIsSplitMatch(Boolean isSplitMatch) {
		this.isSplitMatch = isSplitMatch;
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
	
    public String toString() {
    	return this.id+" "+this.sourceCurrency+" "+this.destinationCurrency+"\n";
    }
}
