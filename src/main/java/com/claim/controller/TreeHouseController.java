package com.claim.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.claim.dto.CreateTreeHouse;
import com.claim.dto.InviteUserToTreeHouse;
import com.claim.dto.SearchForATreeHouse;
import com.claim.entity.Person;
import com.claim.service.PersonService;
import com.claim.service.TreeHouseService;

@CrossOrigin
@RestController
public class TreeHouseController {

	@Autowired
	TreeHouseService treeHouseService;
	
	@Autowired
	PersonService personService;

	
	
/************* Get Users from a TreeHouse ***************/

	//Create new TreeHouse
	@RequestMapping(value="/createNewTreeHouse", 
		consumes=MediaType.APPLICATION_JSON_VALUE,
		method=RequestMethod.POST)
	
	private void createNewTreeHouse(@RequestBody CreateTreeHouse treeHouse) {	
		treeHouseService.createTreeHouse(treeHouse.getTreeHouseName(), treeHouse.getUser().getEmail());		
		System.out.println("TreeHouse created");
	}
	
	
/************* Invite Users to a TreeHouse ***************/
	
	//TreeHouse members invite Users to a TreeHouse
	@RequestMapping(value="/inviteUserToTreeHouse",
		consumes=MediaType.APPLICATION_JSON_VALUE,
		method=RequestMethod.POST)
	
	private void inviteUserToTreeHouse(InviteUserToTreeHouse inviteUserToTreeHouse) {
		//Find User by email 
		Person foundPerson = personService.findPerson(inviteUserToTreeHouse.getInviteeEmail());
		
		//Check the first/last name match the foundPerson
		if (foundPerson.getFirstName().equals(inviteUserToTreeHouse.getInviteeFirstName())
			&& foundPerson.getLastName().equals(inviteUserToTreeHouse.getInviteeLastName())) {
				//Create a new message and append in to the invitee's message array
				//Be sure to pass the TH id
		}
		else {}
		
		//Upon accept of invitation, call another method to add the found user to the given treeID
	}
	
	
/************* Get Users from a TreeHouse ***************/

	//Get users from a given TreeHouse
	@RequestMapping(value="/getAllTreeMembers", 
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.GET)
	
	@ResponseBody
	private ResponseEntity<List<Person>> getAllTreeMembers(int treeID) {
		//This needs to change
		//Needs to return a 2D array
		
		//Create new list of people
		List<Person> allPersons = new ArrayList<>();

		//Get/return an email User List for a treeID
		//List<String> allEmails = treeHouseService.getAllPersonEmails(treeID);
		
		//for (String user : allEmails) {
			//Person personToAdd = this.personService.findPerson(user.getEmail()); 
			//allPersons.add(personToAdd);
		
		return new ResponseEntity<>(allPersons, HttpStatus.OK);
	}
	
	
	
//	//For User trying to invite themselves to an existing TreeHouse
//	//Search for a TreeHouse
//	@RequestMapping(value="/searchForATreeHouse",
//			consumes=MediaType.APPLICATION_JSON_VALUE,
//			produces=MediaType.APPLICATION_JSON_VALUE,
//			method=RequestMethod.GET)
//	
////Not sure what needs to be returned yet
//	private void searchForATreeHouse(SearchForATreeHouse searchForATreeHouse) {
//		//find the Person by email	
//	}
}
