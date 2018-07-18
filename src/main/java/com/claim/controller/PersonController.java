package com.claim.controller;

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
import com.claim.entity.Person;
import com.claim.service.PersonService;

@CrossOrigin
@RestController
public class PersonController {
	 
	
/************* PersonService ***************/

	@Autowired
	PersonService personService;
	
	
/************* Login/Sign Up methods ***************/
	
	//Login
	@RequestMapping(value="/login",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.POST)
	
	private ResponseEntity<Person> login(@RequestBody Person user) {
		//Find Person by email
		Person person = personService.findPerson(user.getEmail());
		
		//Check for null value and password match
		if (person!=null && person.getPassword().equals(user.getPassword())) {
			return new ResponseEntity<>(person, HttpStatus.OK); }
		
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
	}
	
	
	//Create new user
	@RequestMapping(value="/createNewPerson", 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.POST)

	private void createNewPerson(@RequestBody Person user) {		
		//See if email address is already in use
		//Make sure all fields have been filled out
		if (personService.findPerson(user.getEmail()) == null 
			&& user.getFirstName() != null
			&& user.getLastName() != null
			&& user.getEmail() != null
			&& user.getPassword() != null) {
				personService.savePerson(user);
				System.out.println("User saved"); }
		
		else {
			} //Send an error message that email already exists
	}	

	
/************* Get Users from a TreeHouse ***************/

	//Get all Users
	@RequestMapping(value="/getAllUsers", 
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.GET)
	
	@ResponseBody
	private ResponseEntity<List<Person>> getAllUsers() {
		List<Person> allUsers = personService.getAllPersons();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	
	
/************* Add a Person to a TreeHouse ***************/
	
	
}