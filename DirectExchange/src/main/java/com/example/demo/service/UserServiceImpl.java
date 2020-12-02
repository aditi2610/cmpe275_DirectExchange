package com.example.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.Exception.InvalidRequestException;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.User;


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

}
