package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	private Offers offer1;
	
	private Offers offer2;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Offers getOffer1() {
		return offer1;
	}

	public void setOffer1(Offers offer1) {
		this.offer1 = offer1;
	}

	public Offers getOffer2() {
		return offer2;
	}

	public void setOffer2(Offers offer2) {
		this.offer2 = offer2;
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
