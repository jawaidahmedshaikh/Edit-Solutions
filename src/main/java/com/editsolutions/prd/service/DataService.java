package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.PayrollDeductionData;
import com.editsolutions.prd.vo.PRDHistory;

public interface DataService {
	
	public List<PayrollDeductionData> getPayrollDeductionDataList(String s, boolean all) throws DAOException;
	public List<PayrollDeductionData> getPayrollDeductionDataHistoryList(PRDSettings p, String id) throws DAOException;
	public PayrollDeductionData savePayrollDeductionData(String key, String value) throws DAOException;
	public PayrollDeductionData insertPayrollDeductionData(PayrollDeductionData payrollDeductionData) throws DAOException;
	public void deletePayrollDeductionData(String recordFK) throws DAOException;
	public void deletePayrollDeductionDatas(String[] recordFKs) throws DAOException;
	public void deletePayrollDeductionDataFromDataHeader(DataHeader dataHeader) throws DAOException;
	public List<Object[]> getPayrollDeductionDataForPRD(Long prdSettingsPK, Long dataHeaderFK) throws DAOException;
	public List<Object[]> getPayrollDeductionTableForPRD(PRDSettings prdSettings, DataHeader dataHeader) throws DAOException;
	public String getCoverageTier(String ownerSSN) throws DAOException;
	public void createReviewFiles(List<DataHeader> dataHeaders) throws DAOException; 

}
