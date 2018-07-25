package com.claim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="message")
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="messageID", nullable=false)
	//Message counter
	long messageID;
	
	@Column(name="subject", nullable=false)
	//Relation or Invitation
	String subject;
	
	@Column(name="tree_id", nullable=false)
	int treeID;
	
	@Column(name="receiver", nullable=false)
	//Receiver's email
	String receiver;
	
	@Column(name="sender", nullable=false)
	//Sender's email
	String sender;	
	
	@Column(name="receiver_relation_to_sender")
	//Father, Mother, Child, Spouse
	String receiverRelationToSender;
	
	@Column(name="sender_relation_to_receiver")
	//Father, Mother, Child, Spouse	
	String senderRelationToReceiver;
	
	@Transient
	//Create Person obj to send back
	Person senderPerson;
	
	@Transient
	//Create Person obj to send back
	TreeHouse treeHouse;
	
	//Constructor
	public Message() {}


	//Getters and Setters
	public long getMessageID() {
		return messageID;
	}

	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getTreeID() {
		return treeID;
	}

	public void setTreeID(int treeID) {
		this.treeID = treeID;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiverRelationToSender() {
		return receiverRelationToSender;
	}

	public void setReceiverRelationToSender(String receiverRelationToSender) {
		this.receiverRelationToSender = receiverRelationToSender;
	}

	public String getSenderRelationToReceiver() {
		return senderRelationToReceiver;
	}

	public void setSenderRelationToReceiver(String senderRelationToReceiver) {
		this.senderRelationToReceiver = senderRelationToReceiver;
	}

	public Person getSenderPerson() {
		return senderPerson;
	}

	public void setSenderPerson(Person senderPerson) {
		this.senderPerson = senderPerson;
	}

	public TreeHouse getTreeHouse() {
		return treeHouse;
	}

	public void setTreeHouse(TreeHouse treeHouse) {
		this.treeHouse = treeHouse;
	}
	
}
