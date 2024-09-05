package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;
	private Timestamp dateSent;
	private Date dueDate;
	private Long notificationPK;
	private Long originalNotificationPK;
	private Long dataHeaderFK;
	private Long prdSetupFK;
	private String emailTo;
	private String emailCC;
	private String emailBCC;
	private String ftpAddress;
	private String ftpUsername;
	private String ftpPassword;
	private String exportDirectory;
	private String messageSubject;
	private String messageText;
	private boolean hasAttachment;
	private String fileAttachmentName;
	private byte[] fileAttachment;
	private String statusCT;
	private Date addDate;
	private String addUser;
	private Date modDate;
	private String modUser;

	public Notification() {
	}

	public Long getNotificationPK() {
		return notificationPK;
	}

	public void setNotificationPK(Long notificationPK) {
		this.notificationPK = notificationPK;
	}

	public Long getOriginalNotificationPK() {
		return originalNotificationPK;
	}

	public void setOriginalNotificationPK(Long originalNotificationPK) {
		this.originalNotificationPK = originalNotificationPK;
	}

	public Long getDataHeaderFK() {
		return dataHeaderFK;
	}

	public void setDataHeaderFK(Long dataHeaderFK) {
		this.dataHeaderFK = dataHeaderFK;
	}

	public Long getPrdSetupFK() {
		return prdSetupFK;
	}

	public void setPrdSetupFK(Long prdSetupFK) {
		this.prdSetupFK = prdSetupFK;
	}

	public Timestamp getDateSent() {
		return dateSent;
	}

	public void setDateSent(Timestamp dateSent) {
		this.dateSent = dateSent;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailCC() {
		return emailCC;
	}

	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}

	public String getEmailBCC() {
		return emailBCC;
	}

	public void setEmailBCC(String emailBCC) {
		this.emailBCC = emailBCC;
	}

	public String getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(String ftpAddress) {
		this.ftpAddress = ftpAddress;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getExportDirectory() {
		return exportDirectory;
	}

	public void setExportDirectory(String exportDirectory) {
		this.exportDirectory = exportDirectory;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
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

	public boolean getHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public String getFileAttachmentName() {
		return fileAttachmentName;
	}

	public byte[] getFileAttachment() {
		return fileAttachment;
	}

	public void setFileAttachment(byte[] fileAttachment) {
		this.fileAttachment = fileAttachment;
	}

	public void setFileAttachmentName(String fileAttachmentName) {
		this.fileAttachmentName = fileAttachmentName;
	}

	public String getStatusCT() {
		return statusCT;
	}

	public void setStatusCT(String statusCT) {
		this.statusCT = statusCT;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
