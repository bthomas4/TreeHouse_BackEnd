package com.claim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.claim.repository.MessageRepository;
import com.claim.repository.PersonTreeHouseRepository;
import com.claim.entity.Message;
import com.claim.entity.PersonTreeHouse;

import java.util.ArrayList;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	PersonTreeHouseRepository personTreeHouseRepository;
	
	public void saveMessage(Message message) {
		this.messageRepository.save(message);
	}
	
	public void deleteMessage(int id) {
		this.messageRepository.delete((long)id);
	}
	
	public ArrayList<Message> getMessages(String userEmail) {
		return (ArrayList<Message>) this.messageRepository.getMessages(userEmail);
	}
	
	public void acceptInvitation(PersonTreeHouse personTreeHouse) {
		if (personTreeHouseRepository.checkForDuplicate(personTreeHouse.getPersonEmail(), personTreeHouse.getTreeHouseID()) == 0) {
			personTreeHouseRepository.save(personTreeHouse);
		}
		else {
			System.out.print("duplicate found, user not saved");
		}
	}
	
	public int getGenID(int treeID, String userEmail) {
		return personTreeHouseRepository.getGenID(treeID, userEmail);
	}
	
	public void updateGenID(int value, int treeID, String userEmail) {
		personTreeHouseRepository.updateGenID(value, treeID, userEmail);
	}

}
