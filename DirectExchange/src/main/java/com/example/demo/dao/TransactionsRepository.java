package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long>{

}
