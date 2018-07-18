package com.claim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="treeHouse")
public class TreeHouse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tree_Id")
	int treeHouseID;
	
	@Column(name="tree_Name")
	String treeHouseName;
	
	@Transient
	//Email of Persons in tree
	//New table or 1 to Many?
	Set<String> allPersonsInTree = new HashSet<>();
		
	//eventually need to add an array for scrap book pictures
	
	
	//Constructor
	public TreeHouse(String treeHouseName) {
		this.treeHouseName = treeHouseName;
	}

	
	//To String
	@Override
	public String toString() {
		return "TreeHouse [treeHouseID=" + treeHouseID + ", treeHouseName=" + treeHouseName + ", allPersonsInTree="
				+ allPersonsInTree + "]";
	}

	
	//Getters and Setters
	public int getTreeHouseID() {
		return treeHouseID;
	}

	public void setTreeHouseID(int treeHouseID) {
		this.treeHouseID = treeHouseID;
	}

	public String getTreeHouseName() {
		return treeHouseName;
	}

	public void setTreeHouseName(String treeHouseName) {
		this.treeHouseName = treeHouseName;
	}

	public Set<String> getAllPersonsInTree() {
		return allPersonsInTree;
	}

	public void setAllPersonsInTree(Set<String> allPersonsInTree) {
		this.allPersonsInTree = allPersonsInTree;
	}
	
}
