package com.example.demo.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.dto.User;
import com.example.demo.service.IUserService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public User createUser(String firstName, String lastName, String email, String password) {
		
		User user =new User(firstName, lastName, email, password);
		System.out.println("going to save Users now!");
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
	
		userRepository.save(user);
//		sendVerificationEmail(user);
		return user;
		
//		return new ResponseEntity<>("User account has been successfully created. Please verify your email address.",HttpStatus.CREATED);
	}
	public void sendVerificationEmail(User user, String siteUrl) 
			throws UnsupportedEncodingException, MessagingException {
		String subject = "Please Verify your Email";
		String senderName = "Direct Exchange Team";
		String mailContent = "<p> Dear " + user.getFirstName()+ " " +user.getLastName()+ " ,</p><br>";
		mailContent+= "<p>Please click on link below to verify your email address</p> <br>";
		
		String verifyUrl = siteUrl + "/verify/"+ user.getVerificationCode();
		mailContent += "<h3><a href=\""+ verifyUrl +"\">VERIFY</a></h3>";
		mailContent += "<br><p> Thank you <br>Direct Exchange Team</p>";
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("mangsaditi26@gmail.com",senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		javaMailSender.send(message);
		
	}
	@Override
	public boolean verify(String verificationCode) {
		System.out.println("INside verify Method");
		User user = userRepository.findByVerificationCode(verificationCode);
		if(user == null || user.getIsVerified() ) {
			return false;
		}
		else {
			System.out.println("have udpated evreything");
			user.setIsVerified(true);
			userRepository.save(user);
			return true;
		}
	}

}
