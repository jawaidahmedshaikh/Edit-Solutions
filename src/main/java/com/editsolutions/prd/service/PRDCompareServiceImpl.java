package com.editsolutions.prd.service;


import java.util.Date;




import java.util.List;

import com.editsolutions.prd.dao.PRDCompareDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.PRDSettings;

import edit.common.EDITDate;

public class PRDCompareServiceImpl implements PRDCompareService {
	
	private PRDCompareDao prdCompareDao;
	
	public PRDCompareServiceImpl() {
		super();
		prdCompareDao = new PRDCompareDao();
	}
	
	/*
	public  void runCompare(Date date) {
	    prdCompareDao.runCompare(date);
	}
	*/
	
	public void createTestTextExtract(FileTemplate fileTemplate, String groupNumber) throws DAOException {
		prdCompareDao.createTestTextExtract(fileTemplate, groupNumber);
	}

	public void releaseAllPRDs(List<DataHeader> dataHeaders) throws DAOException {
        prdCompareDao.releaseAllPRDs(dataHeaders);		
	}

	public void manuallyReleasePRD(PRDSettings prdSettings, DataHeader dataHeader) throws DAOException {
		prdCompareDao.manuallyReleasePRD(prdSettings, dataHeader);
	}

	public void releasePRD(PRDSettings prdSettings, DataHeader dataHeader) throws DAOException {
		prdCompareDao.releasePRD(prdSettings, dataHeader, true, false);
	}

	public void releasePRDIgnoreTolerances(PRDSettings prdSettings, DataHeader dataHeader) throws DAOException {
		prdCompareDao.releasePRD(prdSettings, dataHeader, true, true);
	}

	public  void runPRDCompare(EDITDate date, String groupNumber) throws DAOException {
	    prdCompareDao.runPRDCompare(date, groupNumber);
	}

	public  void runPRDCompare(EDITDate date) throws DAOException {
	    prdCompareDao.runPRDCompare(date);
	}

	public  void runPRDCompareExtract(EDITDate date, String extractType) throws DAOException {
	    prdCompareDao.runPRDCompareExtract(date, extractType);
	}


	@Override
	public void compareRecords(PRDSettings prdSettings, List<PRDSettings> prdSettingsList, boolean isTest) throws DAOException {
	    prdCompareDao.createDataRecords(prdSettings, prdSettingsList, isTest);
	}
	
	public void importPRDRecordsFromODS(Date date) throws DAOException {
		prdCompareDao.importPRDRecordsFromODS(date);
	}

}
