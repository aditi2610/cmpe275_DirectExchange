package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
