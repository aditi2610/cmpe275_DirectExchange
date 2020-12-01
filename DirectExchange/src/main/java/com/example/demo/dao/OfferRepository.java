package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Offer;
import com.example.demo.dto.User;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	public Optional<Offer> findByIdAndUserAndStatus(Long offerId, User user, int active);

	public List<Offer> findByParentOfferAndStatus(Offer offer, int status);
	
	public List<Offer> findByStatusAndIsCounterOffer(int status, boolean isCounterOffer);
	
	public Optional<Offer> findByIdAndStatus(Long id, int i);
	
	public List<Offer> findByParentOffer_IdAndStatus(Long id, int status);

	public List<Offer> findByUser_IdOrderByStatusAsc(Long userId);
	

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndAmountAndStatusAndIsCounterOffer(
			String destinationCurrency, String sourceCurrency, double amount, int status, boolean isCounterOffer);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndStatusAndAmountBetweenAndIsCounterOfferOrderByAmountAsc(
			String destinationCurrency, String sourceCurrency, int status, double lowerBound, double upperBound, boolean isCounterOffer);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndStatusAndAmountBetweenAndIsCounterOfferAndAmountNotOrderByAmountAsc(String destinationCurrency, String sourceCurrency, int status, double lowerBound, double upperBound, boolean isCounterOffer, double amount);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndStatusAndIsCounterOfferOrderByAmountAsc(String destinationCurrency, String sourceCurrency, int status, boolean isCounterOffer);

	//public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndAmountGratherThanAndStatusAndIsCounterOfferOrderByAmountAsc(String destinationCurrency, String sourceCurrency, double amount, int status, boolean isCounterOffer);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndStatusAndIsCounterOffer(String sourceCurrency, String destinationCurrency, int status, boolean isCounterOffer);
}
