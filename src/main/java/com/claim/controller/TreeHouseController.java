package com.claim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.claim.dto.CreateTreeHouse;
import com.claim.entity.Person;
import com.claim.service.PersonService;
import com.claim.service.TreeHouseService;

@CrossOrigin
@RestController
public class TreeHouseController {

/************* PersonService ***************/

	@Autowired
	TreeHouseService treeHouseService;

	
	//Create new TreeHouse
	@RequestMapping(value="/createNewTreeHouse", 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.POST)
	
	private void createNewTreeHouse(@RequestBody CreateTreeHouse treeHouse) {				
			treeHouseService.createTreeHouse(treeHouse.getTreeHouseName());
			//gettreeHouse.person to set rest
			
			//Need to add the Person user to the treeHouse's user array
			//Add this tree to the person's HashMap of TH's and set genID to 0
			System.out.println("TreeHouse created");
	}
	
}
