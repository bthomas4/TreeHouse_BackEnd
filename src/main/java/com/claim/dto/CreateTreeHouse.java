package com.claim.dto;

import com.claim.entity.Person;

public class CreateTreeHouse {

	//Attributes
	Person user;
	String treeHouseName;
	
	//Constructor
	public CreateTreeHouse() {}

	//Getters and setters
	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public String getTreeHouseName() {
		return treeHouseName;
	}

	public void setTreeHouseName(String treeHouseName) {
		this.treeHouseName = treeHouseName;
	}
	
}
