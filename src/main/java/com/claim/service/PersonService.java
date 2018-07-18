package com.claim.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.claim.entity.Person;
import com.claim.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;
	
	public void savePerson(Person person){
		this.personRepository.save(person);
	}

	public Person findPerson(String email) { 
		return this.personRepository.findOne(email);
	}
	
	public List<Person> getAllPersons() {
		List<Person> allPersons = this.personRepository.findAll();
		return allPersons;
	}
	
	//get all persons from a tree
	
}
