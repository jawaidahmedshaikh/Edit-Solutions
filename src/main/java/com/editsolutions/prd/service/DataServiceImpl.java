package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.dao.DataDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.PayrollDeductionData;
import com.editsolutions.prd.vo.PRDSettings;

public class DataServiceImpl implements DataService {
	private DataDao payrollDeductionDataDao;

	public DataServiceImpl() {
		super();
		payrollDeductionDataDao = new DataDao();
	}
	
	public List<PayrollDeductionData> getPayrollDeductionDataHistoryList(PRDSettings p, String id)
			throws DAOException {
		List<PayrollDeductionData> list = payrollDeductionDataDao.getPayrollDeductionDataHistoryList(p, id);
		return list;
	}
	
	
	public void createReviewFiles(List<DataHeader> dataHeaders) throws DAOException {
		payrollDeductionDataDao.createReviewFiles(dataHeaders);
	}
	

	public List<PayrollDeductionData> getPayrollDeductionDataList(String s, boolean all)
			throws DAOException {
		List<PayrollDeductionData> list = payrollDeductionDataDao.getPayrollDeductionDataList(s, all);
		return list;
	}

	public PayrollDeductionData getPayrollDeductionData(String s) throws DAOException {
		return payrollDeductionDataDao.get(s);
	}
	
	public PayrollDeductionData savePayrollDeductionData(String key, String value) throws DAOException {
		return payrollDeductionDataDao.savePayrollDeductionData(key, value);
    }

	public PayrollDeductionData insertPayrollDeductionData(PayrollDeductionData payrollDeductionData) throws DAOException {
		return payrollDeductionDataDao.insertPayrollDeductionData(payrollDeductionData);
    }
	
	public void deletePayrollDeductionData(String recordFK) throws DAOException {
		payrollDeductionDataDao.deletePayrollDeductionData(recordFK);
	}

	public void deletePayrollDeductionDatas(String[] recordPKs) throws DAOException {
		payrollDeductionDataDao.deletePayrollDeductionDatas(recordPKs);
	}

	public void deletePayrollDeductionDataFromDataHeader(DataHeader dataHeader) throws DAOException {
        payrollDeductionDataDao.deletePayrollDeductionDataFromDataHeader(dataHeader);
	}
	
	public List<Object[]> getPayrollDeductionDataForPRD(Long prdSettingsPK, Long dataHeaderPK) throws DAOException {
		return payrollDeductionDataDao.getPayrollDeductionDataForPRD(prdSettingsPK, dataHeaderPK);
	}

	public List<Object[]> getPayrollDeductionTableForPRD(PRDSettings prdSettings, DataHeader dataHeader) throws DAOException {
		return payrollDeductionDataDao.getPayrollDeductionTableForPRD(prdSettings, dataHeader);
	}
	public String getCoverageTier(String ownerSSN)  throws DAOException {
		return payrollDeductionDataDao.getCoverageTier(ownerSSN);
	}

}
