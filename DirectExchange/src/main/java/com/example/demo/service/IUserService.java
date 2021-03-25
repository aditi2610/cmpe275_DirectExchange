package com.example.demo.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import com.example.demo.exception.InvalidRequestException;

public interface IUserService {

	ResponseEntity<?> addUser(String nickName, String email, String password) throws InvalidRequestException;

	ResponseEntity<?> loginUser(String email, String password, HttpSession session) throws InvalidRequestException;
//	public void sendVerificationEmail(User user, String siteUrl) throws UnsupportedEncodingException, MessagingException;
	public boolean verify(String verificaitonCode);

	void sendVerificationEmail(String nickName, String email, String verificationCode, String siteUrl)throws UnsupportedEncodingException, MessagingException;
	
	void setUserReputation(Long id);
	ResponseEntity<?> loginUsingOAuth(String email, String nickName, HttpServletRequest req) throws InvalidRequestException, UnsupportedEncodingException, MessagingException;
}
