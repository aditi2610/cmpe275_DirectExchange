package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Offer;

@Service
public interface OfferService {

	public Offer add(Offer offer);
}