package com.claim.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.claim.entity.PersonTreeHouse;
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
		
	
/************* Create a TreeHouse invitation ***************/
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
	
	
/************* Accept a TreeHouse invitation ***************/
	//User accepts and joins a new TH
	@RequestMapping(value="/acceptInvitation",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.POST)
	
	private void acceptInvitation(@RequestBody PersonTreeHouse personTreeHouse) {
		messageService.acceptInvitation(personTreeHouse);
	}
	
	
/************* Get messages that belong to a user ***************/
	@RequestMapping(value="/getMessagesForUser",
		consumes=MediaType.APPLICATION_JSON_VALUE,
		method=RequestMethod.POST)
	
	@ResponseBody
	private ResponseEntity<ArrayList<Message>> getMessagesForUser(@RequestBody Person user) {
		ArrayList<Message> allMessages = (ArrayList<Message>) messageService.getMessages(user.getEmail());
		for (Message m : allMessages) {
			m.setSenderPerson(personService.findPerson(m.getSender()));
			m.setReceiverPerson(personService.findPerson(m.getReceiver()));
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
	
	
/************* Create a relation request message ***************/
	@RequestMapping(value="/requestRelation",
			method=RequestMethod.POST)

	private void requestRelation(@RequestBody Message message) {		
		//Create and save the new message
		Message newMessage = message;
		messageService.saveMessage(newMessage);
	}
	
	
/************* Accept a relation request message ***************/
	@RequestMapping(value="/acceptRelation",
			method=RequestMethod.POST)

	private void acceptRelation(@RequestBody Message message) {
		
		//Variables to decrease calls
		int treeID = message.getTreeID();
		Person sender = (personService.findPerson(message.getSender()));
		Person receiver = (personService.findPerson(message.getReceiver()));
		String senderEmail = sender.getEmail();
		String receiverEmail = receiver.getEmail();

		//SENDER
		//If key's value is 0, set it to 1
		if (messageService.getGenID(treeID, sender.getEmail()) == 0) {
			messageService.updateGenID(1, treeID, senderEmail);
		}
		
		
		//RECEIVER
		//If key's value is 0, set it to 1	
		if (messageService.getGenID(treeID, receiver.getEmail()) == 0) {
			messageService.updateGenID(1, treeID, receiverEmail);
		}
				
		
		//Sender To Receiver
		//Set relations
		switch (message.getSenderRelationToReceiver()) {
		
		case ("Father"):
			//Set Father attribute
			receiver.setFather(senderEmail);
			
			//Check if a parent is already set to avoid excessive incrementing
			if (receiver.getMother() == null) {
				
				//Set receiver's genID to (sender's genID + 1) for this treeID
				messageService.updateGenID(messageService.getGenID(treeID, senderEmail) + 1, treeID, receiverEmail);
				
				//Check if Person has children or a spouse
				Set<Person> kids = personService.findChildren(receiverEmail);
				if ((!kids.isEmpty()) || receiver.getSpouse() != null) {

					//Update child and spouse's values
					setRecursiveGenIDs(receiver, treeID); }
			}
			
			personService.savePerson(receiver);
			break;
			
			
		case ("Mother"):
			//Set Mother attribute
			receiver.setMother(senderEmail);
		
			//Check if a parent is already set to avoid excessive incrementing
			if(receiver.getFather() == null) {
				
				//Set receiver's genID to (sender's genID + 1) for this treeID
				messageService.updateGenID(messageService.getGenID(treeID, senderEmail) + 1, treeID, receiverEmail);
				
				//Check if Person has children or a spouse
				Set<Person> kids = personService.findChildren(receiverEmail);
				if ((!kids.isEmpty()) || receiver.getSpouse() != null) {
					
					//Update child and spouse's values
					setRecursiveGenIDs(receiver, treeID); }
			}
			
			personService.savePerson(receiver);
			break;
		
			
		case ("Child"):
			//Check to see if any parent relationship has been set
			if (sender.getFather() == null && sender.getMother() == null) {
				
				//Set sender's genID value to (receiver genID + 1) for this treeID
				messageService.updateGenID(messageService.getGenID(treeID, receiverEmail) + 1, treeID, senderEmail);
				
				//Check if Person has children or a spouse
				Set<Person> kids = personService.findChildren(senderEmail);
				if ((!kids.isEmpty()) || sender.getSpouse() != null) {

					//Update child and spouse's values
					setRecursiveGenIDs(sender, treeID); }
			}
			
			break;
			
			
		case ("Spouse"):
			//Set Spouse attribute for both
			sender.setSpouse(receiverEmail);		
			receiver.setSpouse(senderEmail);

			//Set Spouse genID's equal for the selected tree
			if (message.getBiologicalPerson().equals(senderEmail)) {
				
				//Set receiver's genID value equal to sender's
				messageService.updateGenID(messageService.getGenID(treeID, senderEmail), treeID, receiverEmail);
				setRecursiveGenIDs(sender, treeID);
				setRecursiveGenIDs(receiver, treeID); }
			
			else {
				
				//Set sender's genID value equal to receiver's
				messageService.updateGenID(messageService.getGenID(treeID, receiverEmail), treeID, senderEmail);
				setRecursiveGenIDs(receiver, treeID);
				setRecursiveGenIDs(sender, treeID); }
			
			personService.savePerson(sender);
			personService.savePerson(receiver);
			break;	
		}
		
		
		//Receiver to Sender
		//Set Relations
		switch (message.getReceiverRelationToSender()) {
		
		case ("Father"):
			//Set Father
			sender.setFather(receiverEmail);
			personService.savePerson(sender);
			break;
			
		case ("Mother"):
			//Set Father
			sender.setMother(receiverEmail);
			personService.savePerson(sender);
			break;
		}
	}
	
	
	//Update children and spouse genID's when new relation is established
	public void setRecursiveGenIDs(Person p0, int treeID) {
		
		//Check for spouse
		if (p0.getSpouse() != null) {

			//Find spouse by email
			Person s0 = personService.findPerson(p0.getSpouse());
			
			//Set spouse's genID
			messageService.updateGenID(messageService.getGenID(treeID, p0.getEmail()), treeID, s0.getEmail()); }
		
		//Loop through children
		for (Person p1 : personService.findChildren(p0.getEmail())) {
			
			//Increment child's genId
			messageService.updateGenID(messageService.getGenID(treeID, p0.getEmail()) + 1, treeID, p1.getEmail());
							
			//Check if child has spouse
			if (p1.getSpouse() != null) {
				
				//Find spouse by email
				Person s1 = personService.findPerson(p1.getSpouse());
				
				//Set spouse's genID
				messageService.updateGenID(messageService.getGenID(treeID, p1.getEmail()), treeID, s1.getEmail()); }
			
			//Check if this child has children
			Set<Person> p1Kids = personService.findChildren(p1.getEmail());
			if (!p1Kids.isEmpty()) {
				
				//Loop through children
				for (Person p2 : p1Kids) {
					
					//Increment child's genId
					messageService.updateGenID(messageService.getGenID(treeID, p1.getEmail()) + 1, treeID, p2.getEmail());
					
					//Check if child has spouse
					if (p2.getSpouse() != null) {
					
						//Find spouse by email
						Person s2 = personService.findPerson(p2.getSpouse());
						
						//Set spouse's genID
						messageService.updateGenID(messageService.getGenID(treeID, p2.getEmail()), treeID, s2.getEmail()); }
					
					//Check if the child has children
					Set<Person> p2Kids = personService.findChildren(p2.getEmail());
					if (!p2Kids.isEmpty()) {
						
						//Loop through children
						for (Person p3 : p2Kids) {
							
							//Increment child's genId
							messageService.updateGenID(messageService.getGenID(treeID, p2.getEmail()) + 1, treeID, p3.getEmail());
							
							//Check if child has spouse
							if (p3.getSpouse() != null) {
							
								//Find spouse by email
								Person s3 = personService.findPerson(p3.getSpouse());
								
								//Set spouse's genID
								messageService.updateGenID(messageService.getGenID(treeID, p3.getEmail()), treeID, s3.getEmail()); }
															
							//Check if the child has children
							Set<Person> p3Kids = personService.findChildren(p3.getEmail());
							if (!p3Kids.isEmpty()) {
								
								//Loop through children
								for (Person p4 : p3Kids) {
									
									//Increment child's genId
									messageService.updateGenID(messageService.getGenID(treeID, p3.getEmail()) + 1, treeID, p4.getEmail());
									
									//Check if child has Spouse
									if (p4.getSpouse() != null) {
										
										//Find spouse by email
										Person s4 = personService.findPerson(p4.getSpouse());
										
										//Set spouse's genID
										messageService.updateGenID(messageService.getGenID(treeID, p4.getEmail()), treeID, s4.getEmail()); }
									
								}
							}
						}
					}
				}
			}
		}
	}
	
}
