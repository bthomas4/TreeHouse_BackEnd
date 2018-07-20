package com.claim.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.claim.repository.PersonTreeHouseRepository;
import com.claim.repository.TreeHouseRepository;
import com.claim.entity.Person;
import com.claim.entity.PersonTreeHouse;
import com.claim.entity.TreeHouse;

@Service
public class TreeHouseService {

	@Autowired
	TreeHouseRepository treeHouseRepository;
	
	@Autowired
	PersonTreeHouseRepository personTreeHouseRespository;

	
/************* Create a new TreeHouse ***************/

	//Create a new TreeHouse
	public void createTreeHouse(String treeHouseName, String userEmail) {
		
		//Create the new TreeHouse
		TreeHouse tree = new TreeHouse(treeHouseName);
		this.treeHouseRepository.save(tree);
		
		//Get the treeID and create new PersonTreeHouse
		int treeID = tree.getTreeHouseID();
		PersonTreeHouse personTree = new PersonTreeHouse(userEmail, treeID);
		this.personTreeHouseRespository.save(personTree);
	}
	
	
/************* Find all people in a given tree ***************/
	
	//Find and return Person email List for a tree
//	public List<String> getAllPersonEmails(int treeID) {
	
		//This needs to return personEmail and genID
//		List<String> allPersons = this.personTreeHouseRepository.NeedToWriteThisQueryMethod(treeID)
//		return allEmails;
//	}
	
	
/************* Find a TreeHouse by ID ***************/
	
	//Find a TreeHouse by TreeHouseID
	public void findTreeHouse(TreeHouse tree) {
		this.treeHouseRepository.findOne(tree.getTreeHouseID());
	}
	
}
