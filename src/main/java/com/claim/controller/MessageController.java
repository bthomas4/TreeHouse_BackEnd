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
	
	//after someone accepts/denies a request, delete that message from the repo
	
	
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
		int senderGenID = messageService.getGenID(treeID, sender.getEmail());
		int receiverGenID = messageService.getGenID(treeID, receiver.getEmail());
		
		//SENDER
		//If key's value is 0, set it to 1
		if (senderGenID == 0) {
			messageService.updateGenID(1, treeID, sender.getEmail());
		}
		
		
		//RECEIVER
		//If key's value is 0, set it to 1	
		if (receiverGenID == 0) {
			messageService.updateGenID(1, treeID, receiver.getEmail());
		}
				
		
		//RECEIVER
		//Set relations
		switch (message.getSenderRelationToReceiver()) {
		
		case ("Father"):
			//Set Father attribute
			receiver.setFather(sender.getEmail());
			
			//Check if a parent is already set to avoid excessive incrementing
			if (receiver.getMother() == null) {
				
				//Set receiver's genID value for this treeID
				setTreeHouseID(receiver, treeID, findGenIDValue(sender, treeID) + 1);
				
				//Check if Person has children or a spouse
				if (!receiver.getChildren().isEmpty() || receiver.getSpouse() != null) {

					//Update child and spouse's values
					setRecursiveGenIDs(receiver, treeID); }}
			
			break;
			
			
		case ("Mother"):
			//Set Mother attribute
			receiver.setMother(sender.getEmail());
		
			//Check if a parent is already set to avoid excessive incrementing
			if(receiver.getFather() == null) {
				
				//Set receiver's genID value for this treeID
				setTreeHouseID(receiver, treeID, findGenIDValue(sender, treeID) + 1);
				
				//Check if Person has children or a spouse
				if (!receiver.getChildren().isEmpty() || receiver.getSpouse() != null) {

					//Update child and spouse's values
					setRecursiveGenIDs(receiver, treeID); }}
			
			break;
		
			
		case ("Child"):
			//Add child to parent's children set
//			receiver.children.add(sender.getEmail());
		
			//Check to see if any parent relationship has been set
			if (sender.getFather() == null && sender.getMother() == null) {
				
				//Set sender's genID value for this treeID
				setTreeHouseID(sender, treeID, findGenIDValue(receiver, treeID) + 1);
				
				//Check if Person has children or a spouse
				if (!sender.getChildren().isEmpty() || sender.getSpouse() != null) {

					//Update child and spouse's values
					setRecursiveGenIDs(sender, treeID); }}
			
			break;
			
			
		case ("Spouse"):
			//Set Spouse attribute for both
			sender.setSpouse(receiver.getEmail());		
			receiver.setSpouse(sender.getEmail());

			//Set Spouse genID's equal for the selected tree
			if (personToTakeValuesFrom.getEmail().equals(sender.getEmail())) {
				
				//Set receiver's genID value for this treeID equal to sender's
				setTreeHouseID(receiver, treeID, findGenIDValue(sender, treeID)); }
			
			else {
				//Set sender's genID value for this treeID equal to receiver's
				setTreeHouseID(sender, treeID, findGenIDValue(receiver, treeID)); }
			
			break;	
		}
		
		
		//Set SENDER'S relations
		switch (message.getReceiverRelationToSender()) {
		
		case ("Father"):
			//Set Father
			sender.setFather(receiver.getEmail());
			break;
			
		case ("Mother"):
			//Set Father
			sender.setMother(receiver.getEmail());
			break;
			
		case ("Child"):
			//Add child to parent's children
//			sender.children.add(receiver.getEmail());
			break;
		}
	}
	
	
	//Update children and spouse genID's when new relation is established
	public static void setRecursiveGenIDs(Person p0, int treeID) {
		
		//Check for spouse
		if (p0.getSpouse() != null) {

			//Find spouse by email
			Person s0 = loadPersonInformation(p0.getSpouse());
			
			//Set spouse's genID
			setTreeHouseID(s0, treeID, findGenIDValue(p0, treeID)); }
		
		//Loop through children
		for (String e1 : p0.getChildren()) {
			
			//Find child by email
			Person p1 = loadPersonInformation(e1);
			
			//Increment child's genId
			setTreeHouseID(p1, treeID, findGenIDValue(p0, treeID) + 1);
							
			//Check if child has spouse
			if (p1.getSpouse() != null) {
				
				//Find spouse by email
				Person s1 = loadPersonInformation(p1.getSpouse());
				
				//Set spouse's genID
				setTreeHouseID(s1, treeID, findGenIDValue(p1, treeID)); }
			
			//Check if this child has children
			if (!p1.getChildren().isEmpty()) {
				
				//Loop through children
				for (String e2 : p1.getChildren()) {
					
					//Find child by email
					Person p2 = loadPersonInformation(e2);
					
					//Increment child's genId
					setTreeHouseID(p2, treeID, findGenIDValue(p1, treeID) + 1);
					
					//Check if child has spouse
					if (p2.getSpouse() != null) {
					
						//Find spouse by email
						Person s2 = loadPersonInformation(p2.getSpouse());
						
						//Set spouse's genID
						setTreeHouseID(s2, treeID, findGenIDValue(p2, treeID)); }
					
					//Check if the child has children
					if (!p2.getChildren().isEmpty()) {
						
						//Loop through children
						for (String e3 : p2.getChildren()) {
							
							//Find child by email
							Person p3 = loadPersonInformation(e3);
							
							//Increment child's genId
							setTreeHouseID(p3, treeID, findGenIDValue(p2, treeID) + 1);
							
							//Check if child has spouse
							if (p3.getSpouse() != null) {
							
								//Find spouse by email
								Person s3 = loadPersonInformation(p3.getSpouse());
								
								//Set spouse's genID
								setTreeHouseID(s3, treeID, findGenIDValue(p3, treeID)); }
															
							//Check if the child has children
							if (!p3.getChildren().isEmpty()) {
								
								//Loop through children
								for (String e4 : p3.getChildren()) {
									
									//Find child by email
									Person p4 = loadPersonInformation(e4);
									
									//Increment child's genId
									setTreeHouseID(p4, treeID, findGenIDValue(p3, treeID) + 1);
									
									//Check if child has Spouse
									if (p4.getSpouse() != null) {
										
										//Find spouse by email
										Person s4 = loadPersonInformation(p4.getSpouse());
										
										//Set spouse's genID
										setTreeHouseID(s4, treeID, findGenIDValue(p4, treeID));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	}
	
	
}
