package com.editsolutions.prd.service;

import java.util.Date;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PolicyHolder;

public interface ODSDataService {
	
	public List<ImportRecord> getODSDataRecords(Date extractDate) throws DAOException;
	public List<PolicyHolder> getPolicyHolder(String recordPK) throws DAOException;

}
