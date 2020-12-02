package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.exception.InvalidRequestException;

public interface IUserService {

	ResponseEntity<?> addUser(String nickName, String email, String password) throws InvalidRequestException;

}
