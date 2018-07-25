package com.claim.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.claim.dto.InviteUserToTreeHouse;
import com.claim.entity.Message;
import com.claim.entity.Person;
import com.claim.service.MessageService;
import com.claim.service.PersonService;
import com.claim.service.TreeHouseService;

@CrossOrigin
@RestController
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	TreeHouseService treeHouseService;
	
	//after someone accepts/denies a request, delete that message from the repo
	
	
/************* Invite Users to a TreeHouse ***************/
	//TreeHouse members invite Users to a TreeHouse
	@RequestMapping(value="/inviteUserToTreeHouse",
		consumes=MediaType.APPLICATION_JSON_VALUE,
		method=RequestMethod.POST)
	
	private void inviteUserToTreeHouse(@RequestBody InviteUserToTreeHouse inviteUserToTreeHouse) {
		
		//Find User by email
		Person foundPerson = personService.findPerson(inviteUserToTreeHouse.getMessage().getReceiver());
		
		//Check the first/last name match the foundPerson
		if (foundPerson != null
			&& foundPerson.getFirstName().equals(inviteUserToTreeHouse.getInviteeFirstName())
			&& foundPerson.getLastName().equals(inviteUserToTreeHouse.getInviteeLastName())) {
				
				//Create and save the new message
				Message newMessage = inviteUserToTreeHouse.getMessage();
				messageService.saveMessage(newMessage); }
		
		else {}
	}
	
/************* Invite Users to a TreeHouse ***************/
	@RequestMapping(value="/getMessagesForUser",
		consumes=MediaType.APPLICATION_JSON_VALUE,
		method=RequestMethod.POST)
	
	@ResponseBody
	private ResponseEntity<ArrayList<Message>> getMessagesForUser(@RequestBody Person user) {
		ArrayList<Message> allMessages = (ArrayList<Message>) messageService.getMessages(user.getEmail());
		for (Message m : allMessages) {
			m.setSenderPerson(personService.findPerson(m.getSender()));
			m.setTreeHouse(treeHouseService.findTreeHouse(m.getTreeID()));
		}
		return new ResponseEntity<>(allMessages, HttpStatus.OK);
	}
	
	
/************* Remove a message ***************/
	@RequestMapping(value="/removeMessage",
		method=RequestMethod.GET)
	
	private void removeMessage(int id) {
		messageService.deleteMessage(id);
	}
	
		
	//need method to set relationships on accept and delete the message
	
	
}
