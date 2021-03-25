package com.example.demo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CounterOffer")
public class CounterOffer extends Offer{

//	
//	@ManyToOne(cascade={CascadeType.ALL})
//	@JoinColumn(name="manager_id")
//	private Offer offer;
//
//	@OneToMany(mappedBy="manager")
//	private Set<Offer> parentOffer = new HashSet<>();
		
}
