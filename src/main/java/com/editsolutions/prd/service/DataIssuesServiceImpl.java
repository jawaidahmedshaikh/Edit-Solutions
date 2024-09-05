package com.editsolutions.prd.service;

import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.dao.DataIssuesDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;


public class DataIssuesServiceImpl implements DataIssuesService {
	
	private DataIssuesDao dataIssuesDao;
	
	public DataIssuesServiceImpl() {
		super();
		dataIssuesDao = new DataIssuesDao();
	}
	
	public List<DataIssue> getUnresolvedDataIssues(Serializable dataHeaderPK) throws DAOException {
		return dataIssuesDao.getUnresolvedDataIssues(dataHeaderPK);
		
	}
	
	public boolean deleteDataIssueFromDataFK(Long dataFK) throws DAOException {
		return dataIssuesDao.deleteDataIssueFromDataFK(dataFK);
	}
	
	public void updateDataIssues(List<DataIssue> dataIssues) throws DAOException {
		dataIssuesDao.updateDataIssues(dataIssues);
	}
	
	public void deleteDataIssues(List<DataIssue> dataIssues) throws DAOException {
		dataIssuesDao.deleteDataIssues(dataIssues);
	}

	public void deleteDataIssuesFromDataHeader(DataHeader dataHeader) throws DAOException {
        dataIssuesDao.deleteDataIssuesFromDataHeader(dataHeader);
	}

	public DataIssue updateDataIssue(DataIssue dataIssue) throws DAOException {
		return dataIssuesDao.updateDataIssue(dataIssue);
	}

	@Override
	public void deleteDataIssue(String dataIssuePK) throws DAOException {
		dataIssuesDao.deleteDataIssue(dataIssuePK);
	}

	@Override
	public List<DataIssue> getDataIssues() throws DAOException {
	    return dataIssuesDao.findAll();
	}

	@Override
	public DataIssue getDataIssue(String id) throws DAOException{
	    return dataIssuesDao.get(Long.parseLong(id));
	}
	
	@Override
	public DataIssue saveDataIssue(DataIssue dataIssue) throws DAOException {
		if (dataIssue.getDataIssuePK() == null) {
  		    return dataIssuesDao.insertDataIssue(dataIssue);
		} else {
  		    return dataIssuesDao.updateDataIssue(dataIssue);
		}
	}

}
