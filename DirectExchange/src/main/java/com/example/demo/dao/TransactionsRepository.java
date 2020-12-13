package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Offer;
import com.example.demo.dto.Report;
import com.example.demo.dto.Result;
import com.example.demo.dto.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>{
	
	Optional<Transactions> findByIdAndExpirationDateAfterAndStatus(Long id, LocalDateTime now, int status);

	List<Transactions> findByOfferAndStatus(Offer offer, int status);

	List<Transactions> findBySender_Id(Long userId);
	
	@Query(value= "Select * from transactions where status = 1 and sender_id = :senderId and creation_date >= date_Add(now(), interval -12 month)", nativeQuery = true)
	public List<Transactions> findByTransactionHistory(Long senderId);

	
	

//	@Query(value= "select new Result( SUM(t.sending_amount/c.rate) as convertedUSD , SUM(servie_fee) as totalFee, count(DISTINCT t.offer) as totalOffers, t.status as status) from transactions t inner join currency_rates c on c.currency_code  = t.source_currency where t.creation_date >= date_Add(now(), interval -12 month) group by t.status", nativeQuery = true)
//	List<Result> generateReport();
	

	@Query(value= "select SUM(t.sending_amount/c.rate) as convertedUSD , SUM(servie_fee) as totalFee, count(DISTINCT t.offer) as totalOffers, t.status as status from transactions t inner join currency_rates c on c.currency_code  = t.source_currency where t.creation_date >= date_Add(now(), interval -12 month) group by t.status", nativeQuery = true)
	List<Object[]> generateReport();
	
	
	@Query(value= "Select sum(t.sending_amount/(select rate from currency_rates where currency_code = t.source_currency)) as sendingAmount, sum(t.receving_amount/(select rate from currency_rates where currency_code = t.destination_currency)) as recevingAmount, sum(t.servie_fee/c.rate) as serviceFee from transactions t inner join currency_rates c on c.currency_code = t.source_currency where t.sender_id = :senderId and t.status = 1 and t.creation_date >= date_Add(now(), interval -12 month)", nativeQuery = true)
	public List<Object[]> findByTransactionHistoryStats(Long senderId);
	
	
	}
