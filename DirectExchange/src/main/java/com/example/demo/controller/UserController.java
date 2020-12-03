package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.service.IUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.User;
import com.example.demo.service.IUserService;
@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	//Adding New User
	@RequestMapping(value="user/register" , method = RequestMethod.POST)	
	public ResponseEntity<?> addUser(
			@RequestParam(value="nickName",required=false) String nickName,
			@RequestParam(value="email",required=false) String email,
			@RequestParam(value="password",required=false) String password, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException
	{ 

		ResponseEntity<?> res = null;
		try {
	System.out.println(nickName);
			res = userService.addUser(nickName, email, password);
			String siteUrl = Utility.getSiteUrl(request);
			String verificationCode = res.getBody().toString();
		 	userService.sendVerificationEmail(nickName, email,verificationCode, siteUrl);
		 	res = new ResponseEntity<>(HttpStatus.CREATED);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return res;
	}

	
	//User Login
	
		@RequestMapping(value="user/login" , method = RequestMethod.POST)	
		public ResponseEntity<?> loginUser(
				@RequestParam(value="email",required=false) String email,
				@RequestParam(value="password",required=false) String password,
				HttpSession session)		
		{ 
			ResponseEntity<?> res = null;
			try {
		System.out.println(email+" "+password);
				res = userService.loginUser(email, password,session);
			
			} catch (InvalidRequestException e) {
				e.printStackTrace();
				return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
			}
			return res;
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
