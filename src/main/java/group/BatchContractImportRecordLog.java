package group;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import edit.common.EDITDateTime;
import edit.services.db.hibernate.HibernateEntity;
import group.BatchContractImportFile.Views;

/**
 * Represents information about a single NB contract occurring
 * in a batch file.
 */
public class BatchContractImportRecordLog extends HibernateEntity implements Comparable<BatchContractImportRecordLog> {

	@Id
	@JsonView(BatchContractImportFile.Views.Full.class)
	private Long batchContractImportRecordLogPK;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private Long batchContractImportRecordFK;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private String status;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	@JsonSerialize(using=ToStringSerializer.class)
	private EDITDateTime creationTime;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private String message;
	
	private BatchContractImportRecord batchContractImportRecord;

	public Long getBatchContractImportRecordLogPK() {
		return batchContractImportRecordLogPK;
	}

	public void setBatchContractImportRecordLogPK(
			Long batchContractImportRecordLogPK) {
		this.batchContractImportRecordLogPK = batchContractImportRecordLogPK;
	}

	public Long getBatchContractImportRecordFK() {
		return batchContractImportRecordFK;
	}

	public void setBatchContractImportRecordFK(Long batchContractImportRecordFK) {
		this.batchContractImportRecordFK = batchContractImportRecordFK;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EDITDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(EDITDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BatchContractImportRecord getBatchContractImportRecord() {
		return batchContractImportRecord;
	}

	public void setBatchContractImportRecord(
			BatchContractImportRecord batchContractImportRecord) {
		this.batchContractImportRecord = batchContractImportRecord;
	}

	@Override
	public int compareTo(BatchContractImportRecordLog o) {
		if(this.batchContractImportRecordLogPK == null || o.batchContractImportRecordLogPK == null) {
			return Integer.compare(System.identityHashCode(this), System.identityHashCode(o));
		} else if(this.batchContractImportRecordLogPK != null || o.batchContractImportRecordLogPK != null) {
			return this.batchContractImportRecordLogPK.compareTo(o.batchContractImportRecordLogPK);
		} else {
			int compareResult = Long.compare(this.creationTime.getTimeInMilliseconds(), o.creationTime.getTimeInMilliseconds());
			if(compareResult == 0) {
				compareResult = Integer.compare(System.identityHashCode(this), System.identityHashCode(o));
			}
			return compareResult;
		}
	}		
}
