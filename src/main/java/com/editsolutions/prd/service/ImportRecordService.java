package com.editsolutions.prd.service;

import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PRDSettings;

public interface ImportRecordService {
	
	public ImportRecord getImportRecord(Long recordPK) throws DAOException;
	public void deleteTestRecords()  throws DAOException;
	public List<ImportRecord> getImportRecordList(String groupNumber)  throws DAOException;
	public List<ImportRecord> getImportRecordListByDate(String groupNumber, Date deductionDate) throws DAOException;
	public void importDataRecords(PRDSettings prdSettings) throws DAOException;
	public List<String> getEmployeeIDByGroup(PRDSettings prdSettings) throws DAOException;
	public List<String> getPolicyNumberByGroup(PRDSettings prdSettings) throws DAOException;
	public List<ImportRecord> getImportRecordsByEmployeeID(String employeeID, PRDSettings prdSettings) throws DAOException;
	public List<ImportRecord> getImportRecordsByProduct(ImportRecord importRecord, PRDSettings prdSettings) throws DAOException;
	public ImportRecord saveImportRecord(ImportRecord importRecord) throws DAOException;
	public List<String> getAllProductTypes(PRDSettings prdSettings) throws DAOException;
	public List<ImportRecord> getImportRecordsForProductQuery(PRDSettings prdSettings) throws DAOException;
	public List<ImportRecord> getImportRecordsByPolicyNumber(String policyNumber, PRDSettings prdSettings) throws DAOException;
	public void updateChangeReason(long pk, String changeType)  throws DAOException;
	public void updateImportRecord(ImportRecord importRecord) throws DAOException;


}
