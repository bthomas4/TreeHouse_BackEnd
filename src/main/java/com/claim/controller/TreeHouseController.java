package com.claim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	 
	//Create new TreeHouse
	@RequestMapping(value="/createNewTreeHouse", 
		consumes=MediaType.APPLICATION_JSON_VALUE,
		method=RequestMethod.POST)
	
	private void createNewTreeHouse(@RequestBody CreateTreeHouse treeHouse) {	
		treeHouseService.createTreeHouse(treeHouse.getTreeHouseName(), treeHouse.getUser().getEmail());		
		System.out.println("TreeHouse created");
	}
	
	
	
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
