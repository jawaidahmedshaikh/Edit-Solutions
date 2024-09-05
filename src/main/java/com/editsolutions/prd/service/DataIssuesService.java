package com.editsolutions.prd.service;

import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;

public interface DataIssuesService {
	
	public List<DataIssue> getUnresolvedDataIssues(Serializable dataHeaderPK)  throws DAOException;

	public DataIssue getDataIssue(String id) throws DAOException;
	public List<DataIssue>getDataIssues() throws DAOException;
	public DataIssue saveDataIssue(DataIssue dataIssue) throws DAOException;
	public void deleteDataIssue(String dataIssuePK) throws DAOException;
	public void deleteDataIssues(List<DataIssue> dataIssues) throws DAOException;
	public void deleteDataIssuesFromDataHeader(DataHeader dataHeader) throws DAOException;
	public DataIssue updateDataIssue(DataIssue dataIssue) throws DAOException;
	public void updateDataIssues(List<DataIssue> dataIssues) throws DAOException;
	public boolean deleteDataIssueFromDataFK(Long dataFK) throws DAOException;

}
