package com.claim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.claim.repository.TreeHouseRepository;
import com.claim.entity.Person;
import com.claim.entity.TreeHouse;

@Service
public class TreeHouseService {

	@Autowired
	TreeHouseRepository treeHouseRepository;
	
	public void createTreeHouse(String treeHouseName) {
		TreeHouse tree = new TreeHouse(treeHouseName);
		this.treeHouseRepository.save(tree);
	}
	
	public void findTreeHouse(TreeHouse tree) {
		this.treeHouseRepository.findOne(tree.getTreeHouseID());
	}
	
}
