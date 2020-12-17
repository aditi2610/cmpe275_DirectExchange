package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.controller.Utility;
import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.Transactions;
import com.example.demo.dto.User;
import com.example.demo.exception.InvalidRequestException;

import net.bytebuddy.utility.RandomString;
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Override
	public ResponseEntity<?> addUser(String nickName, String email, String password) throws InvalidRequestException {
		// TODO Auto-generated method stub
		System.out.println(nickName+" "+email+" "+password);
		User user=userRepository.findByEmail(email);
		if(user==null)
		{
			User user1=userRepository.findByNickName(nickName);
			if(user1==null)
			{
				System.out.println("the new user getting created"+nickName+" "+email);
				
				
				
			user=new User(nickName,email,password);
			
			String randomCode = RandomString.make(64);
			user.setVerificationCode(randomCode);
			userRepository.save(user);
			System.out.println("the new user getting saved"+nickName+" "+email);
			return new ResponseEntity<>(user, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", "User Already registered with this nick name" ),HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", "User Already registered with this email" ),HttpStatus.BAD_REQUEST);
		}
		
	}
	@Override
	public ResponseEntity<?> loginUser(String email, String password,HttpSession session) throws InvalidRequestException {
		// TODO Auto-generated method stub
		
		User user=userRepository.findByEmailAndPassword(email,password);
		if(user!=null)
		{
			if(user.getIsVerified())
		{
			return new ResponseEntity<>(user,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", "User not yet verified " ),HttpStatus.UNAUTHORIZED);
		}
		}
		else
		{
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "404", "INVALID CREDENTIALS " ),HttpStatus.NOT_FOUND);
		}
		
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
	@Override
	public void sendVerificationEmail(String nickName, String email, String verificationCode, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		String subject = "Please Verify your Email";
		String senderName = "Direct Exchange Team";
		String mailContent = "<p> Dear " +nickName + " ,</p><br>";
		mailContent+= "<p>Please click on link below to verify your email address</p> <br>";
		
		String verifyUrl = siteUrl + "/verify/"+ verificationCode;
		mailContent += "<h3><a href=\""+ verifyUrl +"\">VERIFY</a></h3>";
		mailContent += "<br><p> Thank you <br>Direct Exchange Team</p>";
		MimeMessage message = javaMailSender.createMimeMessage();
		System.out.println("mail sending");
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("mangsaditi26@gmail.com",senderName);
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		javaMailSender.send(message);
		System.out.println("mail sent");
		
	}
	@Override
	public void setUserReputation(Long id) {
		// TODO Auto-generated method stub
		Optional<User> user=userRepository.findById(id);
		User userActual=user.get();
		List<Transactions> transList= transactionsRepository.findBySender_Id(id);
		int numberOfTransactions=0;
		int numberOfAtFault=0;
		int rating=0;
		double f=0.0;
		if(transList!=null)
		{
			numberOfTransactions=transList.size();
			
			for(int i=0;i<transList.size();i++)
			{
				if(transList.get(i).getExpirationDate().isBefore(LocalDateTime.now())&&transList.get(i).getStatus()!=1)
				{
					numberOfAtFault++;
				}
			}
			//((1- (# of at-fault transactions) / (# of entered transactions)) * 4) + 1
			f=(double)numberOfAtFault/numberOfTransactions;
			rating=(int)Math.round(((1.0-f)*4.0)+1.0);
			System.out.println(id+" "+numberOfTransactions+" "+numberOfAtFault+" rating"+rating);
			userActual.setRating(rating);
			userRepository.save(userActual);

		}
		
	}
	
	
	@Override
	public ResponseEntity<?> loginUsingOAuth(String email, String nickName, HttpServletRequest request) throws InvalidRequestException, UnsupportedEncodingException, MessagingException {
		// TODO Auto-generated method stub
		
		User user=userRepository.findByEmail(email);
		System.out.println(user);
		
		if(user == null) {
			
			ResponseEntity<?> res =addUser(nickName, email, "");
			String siteUrl = Utility.getSiteUrl(request);
			String verificationCode = res.getBody().toString();
			sendVerificationEmail(nickName, email,verificationCode, siteUrl);
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", "User not yet verified " ),HttpStatus.UNAUTHORIZED);
		}
		else if(user!=null)
		{
			if(user.getIsVerified())
		{
			return new ResponseEntity<>(user,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", "User not yet verified " ),HttpStatus.UNAUTHORIZED);
		}
		}
		else
		{
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "404", "INVALID CREDENTIALS " ),HttpStatus.NOT_FOUND);
		}
		
		
	}
	

}
