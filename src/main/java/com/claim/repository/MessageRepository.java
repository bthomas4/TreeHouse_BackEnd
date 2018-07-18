package com.claim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claim.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	//SQL queries go here if not yet defined

}