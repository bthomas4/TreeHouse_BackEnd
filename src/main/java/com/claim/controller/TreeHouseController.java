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
import com.claim.entity.Person;
import com.claim.entity.PersonTreeHouse;
import com.claim.entity.TreeHouse;
import com.claim.service.PersonService;
import com.claim.service.TreeHouseService;

@CrossOrigin
@RestController
public class TreeHouseController {

	@Autowired
	TreeHouseService treeHouseService;
	
	@Autowired
	PersonService personService;

	
	
/************* Create and Save and new TH ***************/
	//Create new TreeHouse
	@RequestMapping(value="/createNewTreeHouse", 
		consumes=MediaType.APPLICATION_JSON_VALUE,
		produces=MediaType.APPLICATION_JSON_VALUE,
		method=RequestMethod.POST)
	
	private ResponseEntity<TreeHouse> createNewTreeHouse(@RequestBody CreateTreeHouse treeHouse) {	
		return new ResponseEntity<>(treeHouseService.createTreeHouse(treeHouse.getTreeHouseName(), treeHouse.getUser().getEmail()), HttpStatus.OK);		
	}

	
	
/************* Search for trees a User belongs to ***************/
	@RequestMapping(value="/searchForTrees",
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.GET)
	
	@ResponseBody
	private ResponseEntity<List<TreeHouse>> searchForTrees(String userEmail) {
		
		//Get and return list of trees (treeID and treeName) a user belongs to
		List<TreeHouse> allTrees = treeHouseService.searchForTrees(userEmail);
		return new ResponseEntity<>(allTrees, HttpStatus.OK);
	}
	
	
	
/************* Get all Users that belong to a Tree ***************/
	//Get users from a given TreeHouse
	@RequestMapping(value="/getAllTreeMembers",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.POST)
	
	@ResponseBody
	private ResponseEntity<ArrayList<ArrayList<Person>>> getAllTreeMembers(@RequestBody TreeHouse tree) {

		//Get a List of email and genID for the tree
		ArrayList<PersonTreeHouse> allUsers = (ArrayList<PersonTreeHouse>) treeHouseService.getEmailsAndIDs(tree.getTreeHouseID());
		
		//Create 2d array to be loaded and returned
		ArrayList<ArrayList<Person>> entireTree = new ArrayList<>();
		
		//Find max Array size
		int maxGen = 0;
		for (PersonTreeHouse user : allUsers) {
			if (user.getGenerationID() > maxGen) {
				maxGen = user.getGenerationID(); }
		}
		
		//Sort users within the 2d array entireTree
		for (int i = 0; i <= maxGen; i++) {

			//Create array to load and push to entireTree
			ArrayList<Person> genToAdd = new ArrayList<>();
			
			for (PersonTreeHouse user : allUsers) {
				
				//See if genID matches array index
				if(user.getGenerationID() == i) {
					
					//Create person object from email
					Person personToAdd = this.personService.findPerson(user.getPersonEmail());
					
					//Add person to that generation
					genToAdd.add(personToAdd);
				}
			}
			entireTree.add(genToAdd);
		}
		return new ResponseEntity<>(entireTree, HttpStatus.OK);
	}
	
}
