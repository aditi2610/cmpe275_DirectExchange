package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.InvalidRequestException;
import com.example.demo.dto.Offer;

@Service
public interface OfferService {

	public Offer add(Offer offer) throws Exception;

	public Offer update(Offer offer) throws Exception;

	public void delete(Long id) throws Exception;

	public boolean acceptOffer(Set<Offer> offers) throws InvalidRequestException, Exception;

	public Offer findOne(Long id) throws Exception;

	public List<Offer> findOffersForUser(Long userId) throws Exception;

	public List<Offer> findAll() throws Exception;

	public HashMap<String, Object> getMatchingOffer(Long id, Long userId) throws Exception;

	public List<Offer> findCounterOffers(Long id, Long userId) throws Exception;
}
