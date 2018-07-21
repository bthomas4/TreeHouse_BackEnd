package com.claim.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.claim.repository.PersonTreeHouseRepository;
import com.claim.repository.TreeHouseRepository;
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
	
	
	
/************* Find a Tree by treeID ***************/
	//Find a TreeHouse by TreeHouseID
	public void findTreeHouse(TreeHouse tree) {
		this.treeHouseRepository.findOne(tree.getTreeHouseID());
	}

	
	
/************* Find all tree (IDs and Names) a User belongs to ***************/
	public List<TreeHouse> searchForTrees(String userEmail) {
		
		//Get all treeIDs a person belongs to
		List<Integer> treeIds = (List<Integer>) this.personTreeHouseRespository.getTreeIDs(userEmail);
		
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
		ArrayList<PersonTreeHouse> allPersons = (ArrayList<PersonTreeHouse>) this.personTreeHouseRespository.getEmailsAndIDs(newTreeID);
		return allPersons;
	}

}
