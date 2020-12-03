package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public User findByNickName(String nickName);
	
	public User findByEmailAndPassword(String email,String password);

	 public User findByVerificationCode(String verificationCode);
}
