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
	
	
	//eventually need to add an array for scrap book pictures
	
	
	//Constructor
	public TreeHouse(String treeHouseName) {
		this.treeHouseName = treeHouseName;
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

}
