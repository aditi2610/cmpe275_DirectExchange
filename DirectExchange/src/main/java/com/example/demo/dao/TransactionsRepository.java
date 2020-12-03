package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Offer;
import com.example.demo.dto.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>{
	
	Optional<Transactions> findByIdAndExpirationDateAfterAndStatus(Long id, LocalDateTime now, int status);

	List<Transactions> findByOfferAndStatus(Offer offer, int status);

	List<Transactions> findBySender_Id(Long userId);
}
