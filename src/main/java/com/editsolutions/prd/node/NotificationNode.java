package com.editsolutions.prd.node;

import java.io.Serializable;
import java.util.Date;

import electric.util.holder.booleanInOut;

public class NotificationNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long notificationPK;
	private Long originalNotificationPK;
	private Long prdSetupFK;
	private String messageSubject;
	private String messageText;
	private String emailTo;
	private String emailCC;
	private String emailBCC;
	private Date dateSent;
	private Date dueDate;
	private String notifyStatus;
	private String fileAttachmentName;
	private Boolean hasAttachment;

	public Long getNotificationPK() {
		return notificationPK;
	}

	public void setNotificationPK(Long notificationPK) {
		this.notificationPK = notificationPK;
	}
	
	

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public Long getOriginalNotificationPK() {
		return originalNotificationPK;
	}

	public void setOriginalNotificationPK(Long originalNotificationPK) {
		this.originalNotificationPK = originalNotificationPK;
	}

	public Long getPrdSetupFK() {
		return prdSetupFK;
	}

	public void setPrdSetupFK(Long prdSetupFK) {
		this.prdSetupFK = prdSetupFK;
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

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public String getFileAttachmentName() {
		return fileAttachmentName;
	}

	public void setFileAttachmentName(String fileAttachmentName) {
		this.fileAttachmentName = fileAttachmentName;
	}

	public Boolean getHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(Boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	

}
