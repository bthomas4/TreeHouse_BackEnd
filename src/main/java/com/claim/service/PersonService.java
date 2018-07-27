package com.claim.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	public Set<Person> findChildren (String personEmail) {
		List<Person> momKids = findChildrenByMother(personEmail);
		List<Person> dadKids = findChildrenByFather(personEmail);
		Set<Person> allChildren = new HashSet<>();
		allChildren.addAll(momKids);
		allChildren.addAll(dadKids);		
		return allChildren;
	}
	
	public List<Person> findChildrenByMother(String motherEmail) {
		return this.personRepository.findChildrenByMother(motherEmail);
	}	

	public List<Person> findChildrenByFather(String fatherEmail) {
		return this.personRepository.findChildrenByFather(fatherEmail);
	}	

}
