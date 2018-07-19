package com.claim.dto;

public class InviteUserToTreeHouse {

	//Attributes
	int selectedTreeHouseId;
	String inviteeFirstName;
	String inviteeLastName;
	String inviteeEmail;
	
	
	//Constructor
	public InviteUserToTreeHouse() {}

	
	//Getters and Setters
	public int getSelectedTreeHouseId() {
		return selectedTreeHouseId;
	}


	public void setSelectedTreeHouseId(int selectedTreeHouseId) {
		this.selectedTreeHouseId = selectedTreeHouseId;
	}


	public String getInviteeFirstName() {
		return inviteeFirstName;
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


	public String getInviteeEmail() {
		return inviteeEmail;
	}


	public void setInviteeEmail(String inviteeEmail) {
		this.inviteeEmail = inviteeEmail;
	}
	
}
