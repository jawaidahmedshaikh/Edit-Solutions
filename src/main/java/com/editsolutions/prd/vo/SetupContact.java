package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class SetupContact implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long setupContactPK;
	private Long setupFK;
	private String name;
	private String email;
	private String recipientTypeCT;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;

	public SetupContact() {
	}

	public Long getSetupContactPK() {
		return setupContactPK;
	}

	public void setSetupContactPK(Long setupContactPK) {
		this.setupContactPK = setupContactPK;
	}

	public Long getSetupFK() {
		return setupFK;
	}

	public void setSetupFK(Long setupFK) {
		this.setupFK = setupFK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecipientTypeCT() {
		return recipientTypeCT;
	}

	public void setRecipientTypeCT(String recipientTypeCT) {
		this.recipientTypeCT = recipientTypeCT;
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
