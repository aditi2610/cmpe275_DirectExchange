package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.User;
import com.example.demo.exception.InvalidRequestException;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
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
			user=new User(nickName,email,password);
			userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
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

}
