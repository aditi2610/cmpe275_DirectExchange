package com.example.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.example.demo.Exception.InvalidRequestException;

public interface IUserService {

	ResponseEntity<?> addUser(String nickName, String email, String password) throws InvalidRequestException;

	ResponseEntity<?> loginUser(String email, String password, HttpSession session) throws InvalidRequestException;

}
