package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

public class Transformation implements Serializable {

	private static final long serialVersionUID = 1L;
	private long transformationPK;
	private String name;
	private String description;
	private String sql;
	private String whereClause;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;

	public Transformation() {
	}

	public long getTransformationPK() {
		return transformationPK;
	}

	public void setTransformationPK(long transformationPK) {
		this.transformationPK = transformationPK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
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
