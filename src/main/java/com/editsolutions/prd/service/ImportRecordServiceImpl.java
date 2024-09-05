package com.editsolutions.prd.service;

import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.dao.ImportRecordDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PRDSettings;

public class ImportRecordServiceImpl implements ImportRecordService {
	private ImportRecordDao importRecordDao;

	public ImportRecordServiceImpl() {
		super();
		importRecordDao = new ImportRecordDao();
	}
	
	public ImportRecord getImportRecord(Long recordPK) throws DAOException {
		return importRecordDao.get(recordPK);
	}
	
	public void deleteTestRecords()  throws DAOException {
		importRecordDao.deleteTestRecords();
	}

	public ImportRecord saveImportRecord(ImportRecord importRecord) throws DAOException {
	    return importRecordDao.save(importRecord);	
	}

	public List<ImportRecord> getImportRecordList(String groupNumber) throws DAOException {
		List<ImportRecord> list = importRecordDao.getImportRecordList(groupNumber);
		return list;
	}

	public List<ImportRecord> getImportRecordListByDate(String groupNumber, Date deductionDate) throws DAOException {
		List<ImportRecord> list = importRecordDao.getImportRecordListByDate(groupNumber, deductionDate);
		return list;
	}
	
	public void importDataRecords(PRDSettings prdSettings) throws DAOException {
		importRecordDao.importDataRecords(prdSettings);
	}
	
	public List<String> getPolicyNumberByGroup(PRDSettings prdSettings) throws DAOException {
		return importRecordDao.getPolicyNumberByGroup(prdSettings);
	}

	public List<String> getEmployeeIDByGroup(PRDSettings prdSettings) throws DAOException {
		return importRecordDao.getEmployeeIDByGroup(prdSettings);
	}

	public List<ImportRecord> getImportRecordsByEmployeeID(String employeeID, PRDSettings prdSettings) throws DAOException {
		return importRecordDao.getImportRecordsByEmployeeID(employeeID, prdSettings);
	}

	public List<ImportRecord> getImportRecordsByProduct(ImportRecord importRecord, PRDSettings prdSettings) throws DAOException {
		return importRecordDao.getImportRecordsByProduct(importRecord, prdSettings);
	}

	public List<ImportRecord> getImportRecordsByPolicyNumber(String policyNumber, PRDSettings prdSettings)  throws DAOException {
        return importRecordDao.getImportRecordsByPolicyNumber(policyNumber, prdSettings);
	}
	
	public List<ImportRecord> getImportRecordsForProductQuery(PRDSettings prdSettings) throws DAOException {
		return importRecordDao.getImportRecordsForProductQuery(prdSettings);
	}
	
	public List<String> getAllProductTypes(PRDSettings prdSettings) throws DAOException {
		return importRecordDao.getAllProductTypes(prdSettings);
	}
	public void updateChangeReason(long pk, String changeType)  throws DAOException {
		importRecordDao.updateChangeReason(pk, changeType);
	}
	 
	public void updateImportRecord(ImportRecord importRecord) throws DAOException {
		importRecordDao.update(importRecord);
	}

}
