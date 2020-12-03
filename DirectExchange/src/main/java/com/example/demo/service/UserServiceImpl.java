package com.example.demo.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.User;
import com.example.demo.exception.InvalidRequestException;

import net.bytebuddy.utility.RandomString;
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
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
	

}
