package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Offer;
import com.example.demo.dto.User;
import com.example.demo.exception.InvalidRequestException;

@Service
public interface IOfferService {

	public Offer add(Offer offer) throws Exception;

	public Offer update(Offer offer) throws Exception;

	public void delete(Long id) throws Exception;

	public Offer findOne(Long id) throws Exception;

	public List<Offer> findOffersForUser(Long userId) throws Exception;

	public List<Offer> findAll() throws Exception;

	public HashMap<String, Object> getMatchingOffer(Long id, Long userId) throws Exception;

	public List<Offer> findCounterOffers(Long id, Long userId) throws Exception;

	public List<Offer> findAllWithoutUserOffer(Long userId);

	public boolean acceptOfferFromBrosePage(Long id, User user) throws Exception;

	public boolean acceptCounterOfferFromBrosePage(Long offerId) throws Exception;

	public boolean acceptOfferFromMyOffer(Long offerId1, Long offerId2);
}
