package com.claim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message {
	
	//maybe a boolean to see delete message after submit
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="messageID")
	//Message counter
	int messageID;
	
	@Column(name="sender")
	//Sender's email
	String sender;
	
	@Column(name="receiver")
	//Receiver's email
	String receiver;
	
	@Column(name="subject")
	String subject;
	
	@Column(name="body")
	String body;

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
