package com.claim.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim.repository.MessageRepository;
import com.claim.repository.PersonTreeHouseRepository;
import com.claim.repository.TreeHouseRepository;
import com.claim.entity.PersonTreeHouse;
import com.claim.entity.TreeHouse;

@Service
public class TreeHouseService {

	@Autowired
	TreeHouseRepository treeHouseRepository;
	
	@Autowired
	PersonTreeHouseRepository personTreeHouseRepository;
	
	@Autowired
	MessageRepository messageRepository;

	
/************* Create a new TreeHouse ***************/
	//Create a new TreeHouse
	public TreeHouse createTreeHouse(String treeHouseName, String userEmail) {
		
		//Create the new TreeHouse
		TreeHouse tree = new TreeHouse(treeHouseName);
		this.treeHouseRepository.save(tree);
		
		//Get the treeID and create new PersonTreeHouse
		int treeID = tree.getTreeHouseID();
		PersonTreeHouse personTree = new PersonTreeHouse(userEmail, treeID);
		this.personTreeHouseRepository.save(personTree);
		
		return tree;
	}
	
	
	
/************* Find a Tree by treeID ***************/
	//Find a TreeHouse by TreeHouseID
	public TreeHouse findTreeHouse(int treeID) {
		return this.treeHouseRepository.findOne(treeID);
	}

	
	
/************* Find all tree (IDs and Names) a User belongs to ***************/
	public List<TreeHouse> searchForTrees(String userEmail) {
		
		//Get all treeIDs a person belongs to
		List<Integer> treeIds = (List<Integer>) this.personTreeHouseRepository.getTreeIDs(userEmail);
		
		//Create a List of trees to return
		List<TreeHouse> trees = new ArrayList<>();
		
		//Create and return name for each treeId
		for (int id : treeIds) {
			TreeHouse treeToAdd = new TreeHouse(this.treeHouseRepository.findOne(id).getTreeHouseName(), id);
			trees.add(treeToAdd);
		}
		return trees;
	}
	
	
		
/************* Find all people in a given tree ***************/
	//Find and return all email and genID for a tree
	public ArrayList<PersonTreeHouse> getEmailsAndIDs(int treeID) {
		long newTreeID = (long)treeID;
		ArrayList<PersonTreeHouse> allPersons = (ArrayList<PersonTreeHouse>) this.personTreeHouseRepository.getEmailsAndIDs(newTreeID);
		return allPersons;
	}

	
/************* Accept invitation, add Person to TH ***************/
	//Create and save a new PersonTreeHouse
	public void acceptInvitation(PersonTreeHouse personTreeHouse) {
		if (personTreeHouseRepository.checkForDuplicate(personTreeHouse.getPersonEmail(), personTreeHouse.getTreeHouseID()) == 0) {
			System.out.println(personTreeHouseRepository.checkForDuplicate(personTreeHouse.getPersonEmail(), personTreeHouse.getTreeHouseID()));
			personTreeHouseRepository.save(personTreeHouse);
		}
		else {
		}
	}
	

//Not finished
	
/************* Accept invitation, add Person to TH ***************/
	//Create and save a new PersonTreeHouse
	public void acceptRelationRequest(PersonTreeHouse personTreeHouse) {
		
		//Update genID's for people within a specific tree
		//May need a list of people to iterate through?
	}
	
}
