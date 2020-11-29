package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.OfferRepository;
import com.example.demo.dto.Offer;
import com.example.demo.dto.User;

@Service
public class OfferServiceImp implements OfferService{

	@Autowired
	private OfferRepository offerRepository;
	
	
	
	@Override
	public Offer add(Offer offer) {
		User user = offer.getUser();
		
	//	return offerRepository.save(offer);
	return null;
	}

}
