package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.dao.CaseNodeDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.node.CaseNode;



public class CaseNodeServiceImpl {

	private CaseNodeDao caseNodeDao;
	
	public CaseNodeServiceImpl() {
		super();
		caseNodeDao = new CaseNodeDao();
	}
	public List<CaseNode> getCaseNodesWithCorrespondence(String searchString, String searchType) throws DAOException {
		return caseNodeDao.getCaseNodesWithCorrespondence(searchString, searchType);
	}
	

}
