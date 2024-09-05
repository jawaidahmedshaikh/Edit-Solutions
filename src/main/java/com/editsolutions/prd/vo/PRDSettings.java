package com.editsolutions.prd.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Temporal;

public class PRDSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long prdSetupPK;
	Long contractCaseFK;
	Long contractGroupFK;
	Long fileTemplateFK;
	Long headerTemplateFK;
	Long footerTemplateFK;
	String setupName;
	String typeCT;
	String statusCT;
	String deliveryMethodCT;
	Long subjectTemplateFK;
	Long messageTemplateFK;
	Long initialSubjectTemplateFK;
	Long initialMessageTemplateFK;
	boolean holdForReview;
	boolean maskSsn;
	int runningCount;
	Date firstDeductionDate;
	int firstDeductionLeadDays;
	int subsequentDays;
	Date effectiveDate;
	Date terminationDate;
	Date changeEffectiveDate;
	String ftpAddress;
	String ftpUserName;
	String ftpPassword;
	String networkLocation;
	String networkLocationDirectory;
	Date addDate;
	String addUser;
	Date modDate;
	String modUser;
	String systemCT;
	Date currentDateThru;
	Long payrollDeductionScheduleFK;
	String emailSentFrom;
	String summaryCT;
	String sortOptionCT;
	String reportTypeCT;
	String repName;
	String repPhoneNumber;
	Date lastPRDExtractDate;
	Date nextPRDExtractDate;
	Date nextPRDDueDate;
    FileTemplate fileTemplate;
    FileTemplate headerTemplate;
    FileTemplate footerTemplate;
    MessageTemplate messageTemplate;
    MessageTemplate subjectTemplate;
    MessageTemplate initialMessageTemplate;
    MessageTemplate initialSubjectTemplate;
    List<SetupContact> setupContacts;
    List<Notification> notifications;
    List<PRDExclusion> prdExclusions;
    List<PRDSettings> additionalPRDSettings;
	GroupSetup groupSetup;

	public PRDSettings() {
	}
	
	

	public List<PRDSettings> getAdditionalPRDSettings() {
		return additionalPRDSettings;
	}



	public void setAdditionalPRDSettings(List<PRDSettings> additionalPRDSettings) {
		this.additionalPRDSettings = additionalPRDSettings;
	}



	public Date getNextPRDDueDate() {
		return nextPRDDueDate;
	}

	public void setNextPRDDueDate(Date nextPRDDueDate) {
		this.nextPRDDueDate = nextPRDDueDate;
	}

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}

	public String getRepPhoneNumber() {
		return repPhoneNumber;
	}

	public void setRepPhoneNumber(String repPhoneNumber) {
		this.repPhoneNumber = repPhoneNumber;
	}

	public GroupSetup getGroupSetup() {
		return groupSetup;
	}

	public void setGroupSetup(GroupSetup groupSetup) {
		this.groupSetup = groupSetup;
	}

	public FileTemplate getFileTemplate() {
		return fileTemplate;
	}
	
	public FileTemplate getHeaderTemplate() {
		return headerTemplate;
	}



	public void setHeaderTemplate(FileTemplate headerTemplate) {
		this.headerTemplate = headerTemplate;
	}



	public FileTemplate getFooterTemplate() {
		return footerTemplate;
	}



	public void setFooterTemplate(FileTemplate footerTemplate) {
		this.footerTemplate = footerTemplate;
	}



	public void setFileTemplate(FileTemplate fileTemplate) {
		this.fileTemplate = fileTemplate;
	}

	// @Column(name = "LastPRDExtractDate", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getLastPRDExtractDate() {
		return lastPRDExtractDate;
	}

	public void setLastPRDExtractDate(Date lastPRDExtractDate) {
		this.lastPRDExtractDate = lastPRDExtractDate;
	}

	// @Column(name = "NextPRDExtractDate", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getNextPRDExtractDate() {
		return nextPRDExtractDate;
	}

	public void setNextPRDExtractDate(Date nextPRDExtractDate) {
		this.nextPRDExtractDate = nextPRDExtractDate;
	}


	// @Column(name="PayrollDeductionScheduleFK", nullable=false)
	public Long getPayrollDeductionScheduleFK() {
		return payrollDeductionScheduleFK;
	}

	public void setPayrollDeductionScheduleFK(Long payrollDeductionScheduleFK) {
		this.payrollDeductionScheduleFK = payrollDeductionScheduleFK;
	}

	// @Column(name="EmailSentFromCT", nullable=true)
	public String getEmailSentFrom() {
		return emailSentFrom;
	}

	public void setEmailSentFrom(String emailSentFrom) {
		this.emailSentFrom = emailSentFrom;
	}

	// @Column(name="SummaryCT", nullable=true)
	public String getSummaryCT() {
		return summaryCT;
	}

	public void setSummaryCT(String summaryCT) {
		this.summaryCT = summaryCT;
	}

	// @Column(name="SortOptionCT", nullable=true)
	public String getSortOptionCT() {
		return sortOptionCT;
	}

	public void setSortOptionCT(String sortOptionCT) {
		this.sortOptionCT = sortOptionCT;
	}

	// @Column(name="ReportTypeCT", nullable=true)
	public String getReportTypeCT() {
		return reportTypeCT;
	}

	public void setReportTypeCT(String reportTypeCT) {
		this.reportTypeCT = reportTypeCT;
	}

	// @Column(name = "CurrentDateThru", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getCurrentDateThru() {
		return currentDateThru;
	}

	public void setCurrentDateThru(Date currentDateThru) {
		this.currentDateThru = currentDateThru;
	}

	// @Id
	// @GeneratedValue(strategy=IDENTITY)
	// @Column(name="PRD_SetupPK", unique=true, nullable=false)
	public Long getPrdSetupPK() {
		return prdSetupPK;
	}

	public void setPrdSetupPK(Long prdSetupPK) {
		this.prdSetupPK = prdSetupPK;
	}

	// @Column(name="Case_ContractGroupFK", nullable=true)
	public Long getContractCaseFK() {
		return contractCaseFK;
	}

	public void setContractCaseFK(Long contractCaseFK) {
		this.contractCaseFK = contractCaseFK;
	}

	// @Column(name="Group_ContractGroupFK", nullable=true)
	public Long getContractGroupFK() {
		return contractGroupFK;
	}

	public void setContractGroupFK(Long contractGroupFK) {
		this.contractGroupFK = contractGroupFK;
	}

	// @Column(name="PRD_FileTemplateFK", nullable=true)
	public Long getFileTemplateFK() {
		return fileTemplateFK;
	}

	public void setFileTemplateFK(Long fileTemplateFK) {
		this.fileTemplateFK = fileTemplateFK;
	}

	// @Column(name="PRD_SetupName", nullable=true)
	public String getSetupName() {
		return setupName;
	}

	public void setSetupName(String setupName) {
		this.setupName = setupName;
	}

	// @Column(name="PRD_TypeCT", nullable=true)
	public String getTypeCT() {
		return typeCT;
	}

	public void setTypeCT(String typeCT) {
		this.typeCT = typeCT;
	}

	// @Column(name="StatusCT", nullable=true)
	public String getStatusCT() {
		return statusCT;
	}

	public void setStatusCT(String statusCT) {
		this.statusCT = statusCT;
	}

	// @Column(name="DeliveryMethodCT", nullable=true)
	public String getDeliveryMethodCT() {
		return deliveryMethodCT;
	}

	public void setDeliveryMethodCT(String deliveryMethodCT) {
		this.deliveryMethodCT = deliveryMethodCT;
	}

	// @Column(name="PRD_SubjectTemplatePK", nullable=true)
	public Long getSubjectTemplateFK() {
		return subjectTemplateFK;
	}

	public void setSubjectTemplateFK(Long subjectTemplateFK) {
		this.subjectTemplateFK = subjectTemplateFK;
	}

	// @Column(name="PRD_MessageTemplatePK", nullable=true)
	public Long getMessageTemplateFK() {
		return messageTemplateFK;
	}

	public void setMessageTemplateFK(Long messageTemplateFK) {
		this.messageTemplateFK = messageTemplateFK;
	}


	@Temporal(javax.persistence.TemporalType.DATE)
	public Date getFirstDeductionDate() {
		return firstDeductionDate;
	}

	public boolean isHoldForReview() {
		return holdForReview;
	}

	public void setHoldForReview(boolean holdForReview) {
		this.holdForReview = holdForReview;
	}

	public boolean isMaskSsn() {
		return maskSsn;
	}

	public void setMaskSsn(boolean maskSsn) {
		this.maskSsn = maskSsn;
	}
	
	public int getRunningCount() {
		return runningCount;
	}
	
	public void setRunningCount(int runningCount) {
		this.runningCount = runningCount;
	}

	public void setFirstDeductionDate(Date firstDeductionDate) {
		this.firstDeductionDate = firstDeductionDate;
	}

	// @Column(name="FirstDeductionLeadDays", nullable=true)
	public int getFirstDeductionLeadDays() {
		return firstDeductionLeadDays;
	}

	public void setFirstDeductionLeadDays(int firstDeductionLeadDays) {
		this.firstDeductionLeadDays = firstDeductionLeadDays;
	}

	// @Column(name="SubsequentDays", nullable=true)
	public int getSubsequentDays() {
		return subsequentDays;
	}

	public void setSubsequentDays(int subsequentDays) {
		this.subsequentDays = subsequentDays;
	}

	// @Column(name = "EffectiveDate", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	// @Column(name = "TerminationDate", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	// @Column(name = "ChangeEffectiveDate", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getChangeEffectiveDate() {
		return changeEffectiveDate;
	}

	public void setChangeEffectiveDate(Date changeEffectiveDate) {
		this.changeEffectiveDate = changeEffectiveDate;
	}

	// @Column(name="FTPAddress", nullable=true)
	public String getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(String ftpAddress) {
		this.ftpAddress = ftpAddress;
	}

	// @Column(name="FTPUserName", nullable=true)
	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	// @Column(name="FTPPassword", nullable=true)
	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getNetworkLocation() {
		return networkLocation;
	}

	public void setNetworkLocation(String networkLocation) {
		this.networkLocation = networkLocation;
	}

	public String getNetworkLocationDirectory() {
		return networkLocationDirectory;
	}

	public void setNetworkLocationDirectory(String networkLocationDirectory) {
		this.networkLocationDirectory = networkLocationDirectory;
	}

	// @Column(name = "AddDate", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	// @Column(name="AddUser", nullable=true)
	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	// @Column(name = "ModDate", nullable = true)
	// @Temporal(javax.persistence.TemporalType.DATE)
	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	// @Column(name="ModUser", nullable=true)
	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	// @Column(name="SystemCT", nullable=true)
	public String getSystemCT() {
		return systemCT;
	}

	public void setSystemCT(String systemCT) {
		this.systemCT = systemCT;
	}

	public List<SetupContact> getSetupContacts() {
		return setupContacts;
	}

	public void setSetupContacts(List<SetupContact> setupContacts) {
		this.setupContacts = setupContacts;
	}

	public MessageTemplate getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(MessageTemplate messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public MessageTemplate getSubjectTemplate() {
		return subjectTemplate;
	}

	public void setSubjectTemplate(MessageTemplate subjectTemplate) {
		this.subjectTemplate = subjectTemplate;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public Long getInitialSubjectTemplateFK() {
		return initialSubjectTemplateFK;
	}

	public void setInitialSubjectTemplateFK(Long initialSubjectTemplateFK) {
		this.initialSubjectTemplateFK = initialSubjectTemplateFK;
	}

	public Long getInitialMessageTemplateFK() {
		return initialMessageTemplateFK;
	}

	public void setInitialMessageTemplateFK(Long initialMessageTemplateFK) {
		this.initialMessageTemplateFK = initialMessageTemplateFK;
	}

	public MessageTemplate getInitialMessageTemplate() {
		return initialMessageTemplate;
	}

	public void setInitialMessageTemplate(MessageTemplate initialMessageTemplate) {
		this.initialMessageTemplate = initialMessageTemplate;
	}

	public MessageTemplate getInitialSubjectTemplate() {
		return initialSubjectTemplate;
	}

	public void setInitialSubjectTemplate(MessageTemplate initialSubjectTemplate) {
		this.initialSubjectTemplate = initialSubjectTemplate;
	}

	public List<PRDExclusion> getPrdExclusions() {
		return prdExclusions;
	}

	public void setPrdExclusions(List<PRDExclusion> prdExclusions) {
		this.prdExclusions = prdExclusions;
	}



	public Long getHeaderTemplateFK() {
		return headerTemplateFK;
	}



	public void setHeaderTemplateFK(Long headerTemplateFK) {
		this.headerTemplateFK = headerTemplateFK;
	}



	public Long getFooterTemplateFK() {
		return footerTemplateFK;
	}



	public void setFooterTemplateFK(Long footerTemplateFK) {
		this.footerTemplateFK = footerTemplateFK;
	}

	
	
}
