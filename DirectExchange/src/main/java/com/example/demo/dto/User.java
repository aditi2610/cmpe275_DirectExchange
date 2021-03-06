package com.example.demo.dto;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.IUserService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
//property = "id")
@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private Long id;
	
	@Column(name = "Email", nullable= false , unique = true, updatable = false)
	private String email;
	
	@Column(name= "NickName", nullable = true, unique = true)	
	private String nickName;
	
	@Column(name= "First_Name", nullable = true, unique = true)	
	private String firstName;	
		
	@Column(name= "Last_Name", nullable = true, unique = true)	
	private String lastName;	
		
	@Column(name= "Password", nullable = false)
	private String password;
	
	//0 for N/A, 1 ,2,3,4,5 rating
	@Column(name="rating", columnDefinition = "int default 0")
	private int rating = 0;
	
	@JsonProperty
	@Column(name= "IsVerified", nullable = false)
	private boolean isVerified;

	@OneToMany(mappedBy = "user")
	private Set<BankAccount> accounts; 

	@OneToMany(mappedBy = "user")
	private Set<Offer> offers; 

	@OneToMany(mappedBy = "sender")
	private Set<Message> serderMsg; 

	@OneToMany(mappedBy = "receiver")
	private Set<Message> receiverMsg; 
	
	@OneToMany(mappedBy = "offerAcceptor")
	private Set<Offer> acceptedOffer;

	@OneToMany(mappedBy = "sender")
	private Set<Transactions> senderTransections; 

	@Column(name= "verification_code", updatable = false)
	private String verificationCode;
	
	@OneToMany(mappedBy = "receiver")
	private Set<Transactions> receiverTransections; 
	
	public Set<Transactions> getSenderTransections() {
		return senderTransections;
	}

	public User()
	{
		
	}
	public User(String nickName, String email,String password) {	
	 this.nickName = nickName;	
	 this.email = email;	
	 this.password = password;	
	 this.isVerified = false;
	}	
//
//	public Offer getAcceptedOffer() {
//		return acceptedOffer;
//	}
//
//	public void setAcceptedOffer(Offer acceptedOffer) {
//		this.acceptedOffer = acceptedOffer;
//	}
	
	public String getFirstName() {	
		return firstName;	
	}	
	public Set<Offer> getAcceptedOffer() {
		return acceptedOffer;
	}
	public void setAcceptedOffer(Set<Offer> acceptedOffer) {
		this.acceptedOffer = acceptedOffer;
	}
	public void setFirstName(String firstName) {	
		this.firstName = firstName;	
	}	
	public String getLastName() {	
		return lastName;	
	}	
	public void setLastName(String lastName) {	
		this.lastName = lastName;	
	}	

	public void setSenderTransections(Set<Transactions> senderTransections) {
		this.senderTransections = senderTransections;
	}

	@JsonIgnoreProperties({"user"})
	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Set<Message> getSerderMsg() {
		return serderMsg;
	}

	public void setSerderMsg(Set<Message> serderMsg) {
		this.serderMsg = serderMsg;
	}

	public Set<Message> getReceiverMsg() {
		return receiverMsg;
	}

	public void setReceiverMsg(Set<Message> receiverMsg) {
		this.receiverMsg = receiverMsg;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getIsVerified() {	
		return isVerified;	
	}	
	public void setIsVerified(Boolean isVerified) {	
		this.isVerified = isVerified;	
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getRating() {
		
		return rating;
	}
	public void setRating(int rating) {
		
		this.rating = rating;
	}
	@JsonIgnoreProperties({"accounts"})
	public Set<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<BankAccount> accounts) {
		this.accounts = accounts;
	}


	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(id);
		hcb.append(email);
		return hcb.toHashCode();
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User that = (User) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(id, that.id);
        return eb.isEquals();
	}
	
	@Override
	public String toString(){
		return this.verificationCode;
	}
	
}
