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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Boolean isCounterOffer;
    
	@ManyToOne(targetEntity = Offer.class)
    private Offer parentOffer;
    
    @ManyToOne(targetEntity = Offer.class)
    private Offer matchingOffer;
    
    @JsonProperty
    @Column(name="hasCounterParent", columnDefinition = "boolean default false")
    private Boolean hasMatchingOffer = false;
    
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "user", referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	@OneToOne
	private User offerAcceptor;
	
	@JsonProperty
	@Column(name="isSplitMatch", columnDefinition = "boolean default false")
	private Boolean isSplitMatch = false;
	    
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "splitMatchUser", referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User splitMatchedUser;
		
	
	@OneToMany(mappedBy = "offer")
	@JsonIgnore
	private Set<Transactions> transaction;
	
	public User getOfferAcceptor() {
		return offerAcceptor;
	}

	public void setOfferAcceptor(User offerAcceptor) {
		this.offerAcceptor = offerAcceptor;
	}

	public boolean isSplitMatch() {
		return isSplitMatch;
	}

	public void setSplitMatch(boolean isSplitMatch) {
		this.isSplitMatch = isSplitMatch;
	}

	public User getSplitMatchedUser() {
		return splitMatchedUser;
	}

	public void setSplitMatchedUser(User splitMatchedUser) {
		this.splitMatchedUser = splitMatchedUser;
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

	public Boolean isCounterOfferAllowed() {
		return isCounterOfferAllowed;
	}

	public void setCounterOfferAllowed(Boolean isCounterOfferAllowed) {
		this.isCounterOfferAllowed = isCounterOfferAllowed;
	}

	public Boolean isSplitOfferAllowed() {
		return isSplitOfferAllowed;
	}

	public void setSplitOfferAllowed(Boolean isSplitOfferAllowed) {
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

	public Boolean isCounterOffer() {
		return isCounterOffer;
	}

	public void setCounterOffer(Boolean isCounterOffer) {
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
