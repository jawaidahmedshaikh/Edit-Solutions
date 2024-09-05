package group;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import edit.services.db.hibernate.HibernateEntity;
import group.BatchContractImportFile.Views;

/**
 * Represents information about a single NB contract occurring
 * in a batch file.
 */
public class BatchContractImportRecord extends HibernateEntity implements Comparable<BatchContractImportRecord> {

	@Id
	@JsonView(BatchContractImportFile.Views.Full.class)
	private Long batchContractImportRecordPk;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private Long batchContractImportFileFK;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private Integer recordSequence; 
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private String label;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private String status;
	
	@JsonView(BatchContractImportFile.Views.Full.class)
	private String message;
	
	/** The set of log entries associated with this record */
	@JsonView(BatchContractImportFile.Views.Full.class) 
	private SortedSet<BatchContractImportRecordLog> batchContractImportRecordLogs = new TreeSet<BatchContractImportRecordLog>();
	
	private BatchContractImportFile batchContractImportFile;

	public Long getBatchContractImportRecordPk() {
		return batchContractImportRecordPk;
	}

	public void setBatchContractImportRecordPk(Long batchContractImportRecordPk) {
		this.batchContractImportRecordPk = batchContractImportRecordPk;
	}

	public Long getBatchContractImportFileFK() {
		return batchContractImportFileFK;
	}

	public void setBatchContractImportFileFK(Long batchContractImportFileFK) {
		this.batchContractImportFileFK = batchContractImportFileFK;
	}

	public Integer getRecordSequence() {
		return recordSequence;
	}

	public void setRecordSequence(Integer recordSequence) {
		this.recordSequence = recordSequence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BatchContractImportFile getBatchContractImportFile() {
		return batchContractImportFile;
	}

	public void setBatchContractImportFile(BatchContractImportFile batchContractImportFile) {
		this.batchContractImportFile = batchContractImportFile;
	}

	public Set<BatchContractImportRecordLog> getBatchContractImportRecordLogs() {
		return batchContractImportRecordLogs;
	}

	public void setBatchContractImportRecordLogs(
			SortedSet<BatchContractImportRecordLog> batchContractImportRecordLogs) {
		this.batchContractImportRecordLogs = batchContractImportRecordLogs;
	}

	@Override
	public int compareTo(BatchContractImportRecord o) {
		return this.recordSequence.compareTo(o.recordSequence);
	} 
	
}
