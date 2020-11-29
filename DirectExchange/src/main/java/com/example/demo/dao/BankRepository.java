package com.example.demo.dao;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.BankAccount;
import com.example.demo.dto.User;


@Repository
public interface BankRepository extends JpaRepository<BankAccount, Long> {
	
	public Set<BankAccount> findByUserAndPrimaryCurrency(User userId, String currency);
	public Set<BankAccount> findByUser(User actualUser);
}
