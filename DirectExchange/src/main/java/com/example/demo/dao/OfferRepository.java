package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.Offer;
import com.example.demo.dto.User;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	//public Page<Offer> findAll(String sourceCurrency, double amount, String destinationCurrency, double destinationAmount, Pageable pageable);
	
	public Optional<Offer> findByIdAndUserAndStatus(Long offerId, User user, int active);

	
	public List<Offer> findByStatusAndIsCounterOffer(int status, boolean isCounterOffer);
	
	public Optional<Offer> findByIdAndStatus(Long id, int i);
	
	public Optional<Offer> findByIdAndStatusAndExpirationDateAfter(Long id, int status, LocalDateTime now);
	
	public List<Offer> findByUser_IdOrderByStatusAsc(Long userId);
	
	@Query(value="Select * from offer where user != :user and source_currency = :sourceCurrency and destination_currency= :destinationCurrency and destination_amount > :amount and status = :status and is_counter_offer = :isCounterOffer and expiration_date > CURRENT_DATE", nativeQuery = true)
	public List<Offer> getListOfC(User user,String sourceCurrency, String destinationCurrency, double amount, int status, int isCounterOffer);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndStatusAndIsCounterOfferAndExpirationDateAfterAndIdNot(
			String sourceCurrency, String destinationCurrency, int status, boolean isCounterOffer, LocalDateTime now,long userId);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndStatusAndIsCounterOfferAndUser_IdNotAndExpirationDateAfterOrderByDestinationAmountAsc(
			String destinationCurrency, String sourceCurrency, int status, boolean isCounterOffer, Long userId,
			LocalDateTime now);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndDestinationAmountAndStatusAndIsCounterOfferAndUser_IdNotAndExpirationDateAfter(
			String destinationCurrency, String sourceCurrency, Double amount, int status, boolean isCounterOffer, Long userId,
			LocalDateTime now);

	public List<Offer> findBySourceCurrencyAndDestinationCurrencyAndStatusAndDestinationAmountBetweenAndIsCounterOfferAndDestinationAmountNotAndUser_IdNotAndExpirationDateAfterOrderByAmountAsc(
			String destinationCurrency, String sourceCurrency, int status, double lowerBound, double upperBound, boolean isCounterOffer,
			double amount, Long userId, LocalDateTime now);

	public List<Offer> findByParentOffer_IdAndStatusAndExpirationDateAfterAndUser_IdNot(Long id, int status,LocalDateTime now, Long userId);

	public List<Offer> findByStatusAndIsCounterOfferAndExpirationDateAfter(int status, boolean isCounterOffer,LocalDateTime now);

	public Optional<Offer> findByIdAndUserAndExpirationDateAfterAndStatus(Long id, User user, LocalDateTime now,int status);

	public List<Offer> findByParentOffer_IdAndStatusAndExpirationDateAfter(Long id, int status, LocalDateTime now);

	public List<Offer> findByStatusAndIsCounterOfferAndExpirationDateAfterAndUser_IdNot(int status, boolean b,LocalDateTime now, Long userId);

	public List<Offer> findByUser_IdAndIsCounterOfferAndExpirationDateAfterOrderByStatusAsc(Long userId, boolean b,
			LocalDateTime now);

	public Optional<Offer> findByIdAndIsCounterOfferAndStatusAndExpirationDateAfter(Long id, boolean b, int oFFER_OPEN,
			LocalDateTime now);
}
