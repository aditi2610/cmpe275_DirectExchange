package com.example.demo.controller;

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

@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	private IUserService UserService;
	
	//Adding New User
	@RequestMapping(value="user/register" , method = RequestMethod.POST)	
	public ResponseEntity<?> addUser(
			@RequestParam(value="nickName",required=false) String nickName,
			@RequestParam(value="email",required=false) String email,
			@RequestParam(value="password",required=false) String password)
			
	{ 

		ResponseEntity<?> res = null;
		try {
	System.out.println(nickName);
			res = UserService.addUser(nickName, email, password);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return res;
	}

}
