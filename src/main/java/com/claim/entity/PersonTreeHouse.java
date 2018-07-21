package com.claim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="person_tree_house")
public class PersonTreeHouse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="person_tree_id", nullable=false)
	Long personTreeId;
	
	@Column(name="person_email", nullable=false)
	String personEmail;
	
	@Column(name="tree_houseid", nullable=false)
	int treeHouseID;
	
	@Column(name="generationid", nullable=false)
	int generationID;

	
	//Constructors
	public PersonTreeHouse() {}
	
	public PersonTreeHouse(String personEmail, int treeHouseId) {
		this.personEmail = personEmail;
		this.treeHouseID = treeHouseId;
		this.generationID = 0;
	}
	
	
	//Getters and Setters
	public Long getPersonTreeId() {
		return personTreeId;
	}	
	
	public void setPersonTreeId(Long personTreeId) {
		this.personTreeId = personTreeId;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public int getTreeHouseID() {
		return treeHouseID;
	}

	public void setTreeHouseID(int treeHouseID) {
		this.treeHouseID = treeHouseID;
	}

	public int getGenerationID() {
		return generationID;
	}

	public void setGenerationID(int generationID) {
		this.generationID = generationID;
	}
	
}
