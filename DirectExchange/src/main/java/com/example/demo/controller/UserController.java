package com.example.demo.controller;


import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.User;
import com.example.demo.service.IUserService;

@RestController
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createOffer(
			@RequestParam(value="firstName",required=true) String firstName,
			@RequestParam(value="lastName",required=true) String lastName,
			@RequestParam(value="email",required=true) String email,
			@RequestParam(value="password",required=true) String password 
			,HttpServletRequest request
			) throws UnsupportedEncodingException, MessagingException{
		System.out.println("Inside User controller");
		 User user = userService.createUser(firstName, lastName, email, password);
		 String siteUrl = Utility.getSiteUrl(request);
		 userService.sendVerificationEmail(user, siteUrl);
		 return new ResponseEntity<>("User account has been successfully created. Please verify your email address.",HttpStatus.CREATED);
	} 
	
	@RequestMapping(value="verify/{code}" , method = RequestMethod.GET)	
	public ResponseEntity<?> verifyUser(@PathVariable("code") String code)
	{ 
		System.out.println("Inside Verfiy Controller");
		ResponseEntity<?> res = null;
		boolean isAuth = userService.verify(code);
		String pageTitle = isAuth ? "verification Succeded": "Verification Failed";
		System.out.println("Authenticaiton : "+ isAuth);
		return res;
	}

}