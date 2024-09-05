package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;

import electric.util.holder.booleanInOut;
import electric.util.holder.byteInOut;
import electric.util.holder.longInOut;

public class Note implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long notePK;
	private Long dataHeaderFK;
	private Date noteDate;
	private Date actionDate;
	private String note;
	private String statusCT;
	private PRDSettings prdSettings;

	public Long getNotePK() {
		return notePK;
	}

	public void setNotePK(Long notePK) {
		this.notePK = notePK;
	}

	public Long getDataHeaderFK() {
		return dataHeaderFK;
	}

	public void setDataHeaderFK(Long dataHeaderFK) {
		this.dataHeaderFK = dataHeaderFK;
	}
	
	public PRDSettings getPrdSettings() {
		return prdSettings;
	}

	public void setPrdSettings(PRDSettings prdSettings) {
		this.prdSettings = prdSettings;
	}

	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatusCT() {
		return statusCT;
	}

	public void setStatusCT(String statusCT) {
		this.statusCT = statusCT;
	}

	
}
