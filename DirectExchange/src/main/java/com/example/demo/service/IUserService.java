package com.example.demo.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.example.demo.dto.User;

@Service
public interface IUserService {
	

	User createUser (String firstName, String lastName,String email ,String password); 
	public void sendVerificationEmail(User user, String siteUrl)
			throws UnsupportedEncodingException, MessagingException;
	public boolean verify(String verificaitonCode);

}
