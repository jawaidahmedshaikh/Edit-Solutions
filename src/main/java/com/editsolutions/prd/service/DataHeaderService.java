package com.editsolutions.prd.service;

import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;

public interface DataHeaderService {
	
	public DataHeader getDataHeader(Serializable id) throws DAOException;
	public List<DataHeader> getDataHeaders(String prdSetupFK) throws DAOException;
	public List<DataHeader> getDataHeadersWithIssues(String prdSetupFK) throws DAOException;
	public List<DataHeader> getAllDataHeadersWithIssues() throws DAOException;
	public DataHeader saveDataHeader(DataHeader dataHeader) throws DAOException;
	public int getPRDCount(Long dataHeaderPK) throws DAOException;
	public int getCountByIssueLookupCT(Long dataHeaderPK, String issueLookupCT) throws DAOException;
	public void resolveAllDataIssues(DataHeader dataHeader) throws DAOException;
	public void deleteDataHeaders(List<DataHeader> dataHeaders) throws DAOException;
	public List<DataHeader> getAllDataHeadersOnHold() throws DAOException;
	public DataHeader getDataHeaderWithUnresolvedIssues(Serializable dataHeaderPK) throws DAOException;
	public List<DataHeader> getUnreleasedDataHeaders() throws DAOException;
	public DataHeader updateDataHeader(DataHeader dataHeader) throws DAOException;
	
	public List<DataHeader> getPreviousWeeksUnreleasedDataHeaders() throws DAOException;
	public List<DataHeader> getCurrentWeeksReleasedDataHeaders() throws DAOException;
	public List<DataHeader> getAllUnreleasedDataHeaders() throws DAOException;

}
