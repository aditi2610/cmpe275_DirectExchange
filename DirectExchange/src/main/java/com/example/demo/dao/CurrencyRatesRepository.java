package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.dto.CurrencyRates;

@Repository
public interface CurrencyRatesRepository extends JpaRepository<CurrencyRates, Long> {

}
