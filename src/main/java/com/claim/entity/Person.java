package com.claim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person {
	
	@Id
	@Column(name="email")
	String email;
	
	@Column(name="firstName")
	String firstName;
	
	@Column(name="lastName")
	String lastName;
	
	@Column(name="password")
	String password;

	//These store Person's email
	@Column(name="father")
	String father;

	@Column(name="mother")
	String mother;

	@Column(name="spouse")
	String spouse;
	
	@Column
	//Person description
	String summary;
	
	@Column
	//path to profile pic
	String path;
	
	
	//Constructors
	public Person() {
	}
	
	public Person(String firstName, String lastName, String email, String password) {		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	

	//Getters and Setters
	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getMother() {
		return mother;
	}

	public void setMother(String mother) {
		this.mother = mother;
	}

	public String getSpouse() {
		return spouse;
	}

	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
