package com.editsolutions.prd.service;

import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.dao.DataHeaderDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;


public class DataHeaderServiceImpl implements DataHeaderService {
	
	private DataHeaderDao dataHeaderDao;
	
	public DataHeaderServiceImpl() {
		dataHeaderDao = new DataHeaderDao();
	}

	@Override
	public DataHeader getDataHeader(Serializable id) throws DAOException {
		return dataHeaderDao.get(id);
	}
	
	public List<DataHeader> getCurrentWeeksReleasedDataHeaders() throws DAOException {
		return dataHeaderDao.getCurrentWeeksReleasedDataHeaders();
	}
	
	public List<DataHeader> getPreviousWeeksUnreleasedDataHeaders() throws DAOException {
		return dataHeaderDao.getPreviousWeeksUnreleasedDataHeaders();
	}
	
	public List<DataHeader> getAllUnreleasedDataHeaders() throws DAOException {
		return dataHeaderDao.getAllUnreleasedDataHeaders();
	}

	public DataHeader getDataHeaderWithUnresolvedIssues(Serializable dataHeaderPK)  throws DAOException {
		return dataHeaderDao.getDataHeaderWithUnresolvedIssues(dataHeaderPK);
	}
	
	public List<DataHeader> getUnreleasedDataHeaders()  throws DAOException {
		return dataHeaderDao.getAllUnreleasedDataHeaders();
	}

	@Override
	public DataHeader saveDataHeader(DataHeader dataHeader)  throws DAOException {
		return dataHeaderDao.saveDataHeader(dataHeader);
	}

	@Override
	public DataHeader updateDataHeader(DataHeader dataHeader) throws DAOException {
		return dataHeaderDao.updateDataHeader(dataHeader);
	}

	@Override
	public List<DataHeader> getDataHeaders(String prdSetupFK) throws DAOException {
		Long fk = Long.valueOf(prdSetupFK);
		return dataHeaderDao.getDataHeaders(fk);
	}
	
	public void deleteDataHeaders(List<DataHeader> dataHeaders) throws DAOException {
		dataHeaderDao.deleteDataHeaders(dataHeaders);
	}
	
	public void resolveAllDataIssues(DataHeader dataHeader) throws DAOException {
		dataHeaderDao.resolveAllDataIssues(dataHeader);
	}

	@Override
	public List<DataHeader> getAllDataHeadersWithIssues() throws DAOException {
		return dataHeaderDao.getAllDataHeadersWithIssues();
	}

	public List<DataHeader> getAllDataHeadersOnHold() throws DAOException {
		return dataHeaderDao.getAllDataHeadersOnHold();
	}
	
	@Override
	public int getCountByIssueLookupCT(Long dataHeaderPK, String issueLookupCT) throws DAOException {
		return dataHeaderDao.getCountByIssueLookupCT(dataHeaderPK, issueLookupCT);
	}

	@Override
	public List<DataHeader> getDataHeadersWithIssues(String prdSetupFK) throws DAOException {
		Long fk = Long.valueOf(prdSetupFK);
		return dataHeaderDao.getDataHeadersWithIssues(fk);
	}

	public int getPRDCount(Long dataHeaderPK) throws DAOException {
		return dataHeaderDao.getPRDCount(dataHeaderPK);
	}

}
