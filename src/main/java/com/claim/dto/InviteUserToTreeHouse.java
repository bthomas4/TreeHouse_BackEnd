package com.claim.dto;

import com.claim.entity.Message;

public class InviteUserToTreeHouse {

	//Attributes
	String inviteeFirstName;
	String inviteeLastName;
	Message message;
	
	
	//Constructor
	public InviteUserToTreeHouse() {}

	
	//Getters and Setters
	public String getInviteeFirstName() {
		return inviteeFirstName;
	}


	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}


	public void setInviteeFirstName(String inviteeFirstName) {
		this.inviteeFirstName = inviteeFirstName;
	}


	public String getInviteeLastName() {
		return inviteeLastName;
	}


	public void setInviteeLastName(String inviteeLastName) {
		this.inviteeLastName = inviteeLastName;
	}
	
}
