package com.editsolutions.prd.service;

import java.util.Date;
import java.util.List;

import com.editsolutions.prd.dao.ODSDataDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PolicyHolder;

public class ODSDataServiceImpl implements ODSDataService {
	private ODSDataDao odsDataDao;

	public ODSDataServiceImpl() {
		super();
		odsDataDao = new ODSDataDao();
	}
	
	public List<ImportRecord> getODSDataRecords(Date extractDate)
			throws DAOException {
		List<ImportRecord> list = odsDataDao.getODSDataRecords(extractDate);
		return list;
	}
	
	public List<PolicyHolder> getPolicyHolder(String recordPK) throws DAOException {
		return odsDataDao.getPolicyHolder(recordPK);
	}


}
