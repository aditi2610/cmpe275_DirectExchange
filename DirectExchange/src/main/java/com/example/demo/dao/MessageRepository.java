package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
