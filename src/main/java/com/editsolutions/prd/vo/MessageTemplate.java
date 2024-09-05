package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class MessageTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long messageTemplatePK;
	private String messageTypeCT;
	private String name;
	private String messageText;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;

	public MessageTemplate() {
	}

	public Long getMessageTemplatePK() {
		return messageTemplatePK;
	}

	public void setMessageTemplatePK(Long messageTemplatePK) {
		this.messageTemplatePK = messageTemplatePK;
	}

	public String getMessageTypeCT() {
		return messageTypeCT;
	}

	public void setMessageTypeCT(String messageTypeCT) {
		this.messageTypeCT = messageTypeCT;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

}
