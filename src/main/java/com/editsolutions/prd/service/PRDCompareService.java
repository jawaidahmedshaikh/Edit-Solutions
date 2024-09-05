package com.editsolutions.prd.service;


import java.util.Date;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.PRDSettings;

import edit.common.EDITDate;

public interface PRDCompareService {
	
	public void compareRecords(PRDSettings prdSettings, List<PRDSettings> prdSettingsList, boolean isTest) throws DAOException;

	public void importPRDRecordsFromODS(Date date) throws DAOException;
	
	public  void runPRDCompare(EDITDate date, String groupNumber) throws DAOException;

	public  void runPRDCompare(EDITDate date) throws DAOException;
	
	public void releasePRD(PRDSettings prdSettings, DataHeader dataHeader) throws DAOException;

	public void releasePRDIgnoreTolerances(PRDSettings prdSettings, DataHeader dataHeader) throws DAOException;
	
	public void releaseAllPRDs(List<DataHeader> dataHeaders) throws DAOException;

	public void createTestTextExtract(FileTemplate fileTemplate, String groupNumber) throws DAOException;
	/*
	public  void runCompare(Date date);
	*/

}
