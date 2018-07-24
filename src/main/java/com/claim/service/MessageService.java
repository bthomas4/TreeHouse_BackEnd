package com.claim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.claim.repository.MessageRepository;
import com.claim.entity.Message;
import java.util.ArrayList;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;
	
	public void saveMessage(Message message) {
		this.messageRepository.save(message);
	}
	
	public void deleteMessage(int id) {
		this.messageRepository.delete((long)id);
	}
	
	public ArrayList<Message> getMessages(String userEmail) {
		return (ArrayList<Message>) this.messageRepository.getMessages(userEmail);
	}
	
}
