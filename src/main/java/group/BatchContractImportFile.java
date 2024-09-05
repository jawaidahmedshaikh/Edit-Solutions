package group;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import edit.common.EDITDateTime;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

public class BatchContractImportFile extends HibernateEntity {
	
	public static class Views {
	    public static class Condensed { }
	    public static class Full { }
	}
	
    public final static String STATUS_PENDING = "PENDING";
    public final static String STATUS_SUCCESS = "SUCCESS";
    public final static String STATUS_ERROR = "ERROR";
    public final static String STATUS_WARNING = "WARNING";
	
	public static String DATABASE = SessionHelper.EDITSOLUTIONS;
	
	/** Auto-generated primary key representing a unique imported record */
	@Id
	@JsonView({Views.Full.class, Views.Condensed.class})
	private Long batchContractImportFilePk;
	
	/** Hash of the imported content used to identify repeated imports of the same file */
	@JsonView({Views.Condensed.class, Views.Full.class})
	private String fileMD5Hash;
	
	/** Time at which the import record was first created */
	@JsonSerialize(using=ToStringSerializer.class)
	@JsonView({Views.Condensed.class, Views.Full.class})
	private EDITDateTime creationTime;
	
	/** Status code of the import process */
	@JsonView({Views.Condensed.class, Views.Full.class})
	private String status;
	
	/** Name of the original uploaded file */
	@JsonView({Views.Condensed.class, Views.Full.class})
	private String sourceFileName;
	
	/** Time at which the import was completed */
	@JsonSerialize(using=ToStringSerializer.class)
	@JsonView({Views.Condensed.class, Views.Full.class})
	private EDITDateTime completedTime;
	
	/** Time at which the import record was last changed */
	@JsonSerialize(using=ToStringSerializer.class)
	@JsonView({Views.Condensed.class, Views.Full.class})
	private EDITDateTime modifiedTime;
	
	/** Error or other informational message related to the import */
	@JsonView({Views.Condensed.class, Views.Full.class})
	private String message;
	
	/** Foreign key to the batch contract setup to import into */
	@JsonView({Views.Condensed.class, Views.Full.class})
	private Long batchContractSetupFk;
	
	/** Operator making the request */
	@JsonView({Views.Condensed.class, Views.Full.class})
	private String operator;
	
	/** Number of contracts in the import file */
	@JsonView({Views.Condensed.class, Views.Full.class})
	private Integer totalRecords;
	
	/** URL at which a success report can be retrieved */
	@JsonView(Views.Full.class)
	private String successReportUrl;
	  
	/** URL at which an error report can be retrieved */
	@JsonView(Views.Full.class)
	private String errorReportUrl;
	  
	/** URL at which a warning report can be retrieved */
	@JsonView(Views.Full.class)
	private String warningReportUrl;
	
	/** The set of records associated with this import file */
	@JsonView(Views.Full.class)
	private SortedSet<BatchContractImportRecord> batchContractImportRecords = new TreeSet<BatchContractImportRecord>();
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Long getBatchContractImportFilePk() {
		return batchContractImportFilePk;
	}
	
	public void setBatchContractImportFilePk(Long batchContractImportFilePk) {
		this.batchContractImportFilePk = batchContractImportFilePk;
	}
	public String getFileMD5Hash() {
		return fileMD5Hash;
	}
	public void setFileMD5Hash(String fileMD5Hash) {
		this.fileMD5Hash = fileMD5Hash;
	}
	public EDITDateTime getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(EDITDateTime creationTime) {
		this.creationTime = creationTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	public EDITDateTime getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(EDITDateTime completedTime) {
		this.completedTime = completedTime;
	}
	public EDITDateTime getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(EDITDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Set<BatchContractImportRecord> getBatchContractImportRecords() {
		return batchContractImportRecords;
	}
	public void setBatchContractImportRecords(
			SortedSet<BatchContractImportRecord> batchContractImportRecords) {
		this.batchContractImportRecords = batchContractImportRecords;
	}
	public Long getBatchContractSetupFk() {
		return batchContractSetupFk;
	}
	public void setBatchContractSetupFk(Long batchContractSetupFk) {
		this.batchContractSetupFk = batchContractSetupFk;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getSuccessReportUrl() {
		return successReportUrl;
	}
	public void setSuccessReportUrl(String successReportUrl) {
		this.successReportUrl = successReportUrl;
	}
	public String getErrorReportUrl() {
		return errorReportUrl;
	}
	public void setErrorReportUrl(String errorReportUrl) {
		this.errorReportUrl = errorReportUrl;
	}
	public String getWarningReportUrl() {
		return warningReportUrl;
	}
	public void setWarningReportUrl(String warningReportUrl) {
		this.warningReportUrl = warningReportUrl;
	}	
}
