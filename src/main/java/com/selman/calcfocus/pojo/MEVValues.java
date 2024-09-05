package com.selman.calcfocus.pojo;

import java.util.Date;

public class MEVValues {
	
	/*
	[MEVValuesPK] [bigint] IDENTITY(1,1) NOT NULL,
	[BatchId] [varchar](50) NULL,
	[ExtractDate] [date] NULL,
	[ContractNumber] [varchar](15) NOT NULL,
	[Type] [varchar](50) NULL,
	[EOPInterestAdjustment] [money] NULL,
	[EndAV] [money] NULL,
	[AddDate] [date] NOT NULL
	 */
	
	private long mevValues;
	private String batchId;
	private Date extractDate;
	private String contractNumber;
	private String type;
	private Double eopInterestAdjustment;
	private Double endAv;
	private Date addDate;
	
    	

	public MEVValues() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MEVValues(long mevValues, String batchId, Date extractDate, String contractNumber, String type,
			Double eopInterestAdjustment, Double endAv, Date addDate) {
		super();
		this.mevValues = mevValues;
		this.batchId = batchId;
		this.extractDate = extractDate;
		this.contractNumber = contractNumber;
		this.type = type;
		this.eopInterestAdjustment = eopInterestAdjustment;
		this.endAv = endAv;
		this.addDate = addDate;
	}



	public long getMevValues() {
		return mevValues;
	}



	public void setMevValues(long mevValues) {
		this.mevValues = mevValues;
	}



	public String getBatchId() {
		return batchId;
	}



	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}



	public Date getExtractDate() {
		return extractDate;
	}



	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}



	public String getContractNumber() {
		return contractNumber;
	}



	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Double getEopInterestAdjustment() {
		return eopInterestAdjustment;
	}



	public void setEopInterestAdjustment(Double eopInterestAdjustment) {
		this.eopInterestAdjustment = eopInterestAdjustment;
	}



	public Double getEndAv() {
		return endAv;
	}



	public void setEndAv(Double endAv) {
		this.endAv = endAv;
	}



	public Date getAddDate() {
		return addDate;
	}



	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	

}
