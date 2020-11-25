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

@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private long id;
	
	@Column(name = "Email", nullable= false , unique = true, updatable = false)
	private String email;
	
	@Column(name= "Name", nullable = false, unique = true)
	private String name;
	
	@Column(name= "Password", nullable = false)
	private String password;
	
	@Column(name= "IsVerified", nullable = false)
	private String isVerified;

	@OneToMany(mappedBy = "user")
	private Set<BankAccount> accounts; 
	
	@OneToMany(mappedBy = "user")
	private Set<Offer> offers; 
	
	@OneToMany(mappedBy = "sender")
	private Set<Message> serderMsg; 
	
	@OneToMany(mappedBy = "receiver")
	private Set<Message> receiverMsg; 
	
	
	@OneToMany(mappedBy = "user1")
	private Set<Transactions> user1Transections; 
	
	@OneToMany(mappedBy = "user2")
	private Set<Transactions> user2Transections; 
	
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

	public long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<BankAccount> accounts) {
		this.accounts = accounts;
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
        eb.append(email, that.email);
        return eb.isEquals();
    }
	
}
