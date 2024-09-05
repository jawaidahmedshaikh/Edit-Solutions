package com.editsolutions.prd.service;

import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.dao.CaseSetupDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.CaseSetup;

import electric.util.holder.booleanInOut;


public class CaseSetupServiceImpl implements CaseSetupService {

	private CaseSetupDao caseSetupDao;
	
	public CaseSetupServiceImpl() {
		super();
		caseSetupDao = new CaseSetupDao();
	}
	
	public boolean caseExists(String caseNumber) throws DAOException {
		return caseSetupDao.caseExists(caseNumber);
	}
	
	public List<CaseSetup> getCaseSetupsWithCorrespondence() throws DAOException {
		return caseSetupDao.getCaseSetupsWithCorrespondence();
	}

	public CaseSetup getCaseSetupByCaseNumber(String caseNumber) throws DAOException {
		return caseSetupDao.getCaseSetupByCaseNumber(caseNumber);
	}
	
	public CaseSetup getCaseSetupByGroupNumber(String groupNumber) throws DAOException {
		return caseSetupDao.getCaseSetupByGroupNumber(groupNumber);
	}

	public CaseSetup getCaseSetup(String s) throws DAOException {
		return caseSetupDao.getCaseSetup(s);
	}
	
	public CaseSetup getCaseSetupFromPK(Long pk) throws DAOException {
		return caseSetupDao.getCaseSetupFromPK(pk);
	}

	public List<CaseSetup> getCaseSetupsByRepName(String repName) throws DAOException {
		return caseSetupDao.getCaseSetupsByRepName(repName);
	}

	public List<CaseSetup> getCaseSetupsBySearchString(String s, boolean byName) throws DAOException {
		return caseSetupDao.getCaseSetupsBySearchString(s, byName);
	}

	public CaseSetup get(Serializable id) throws DAOException {
		return caseSetupDao.get(id);
	}

	public CaseSetup saveCaseSetup(CaseSetup caseSetup) throws DAOException {
		if (caseSetup.getCaseContractPk() == null) {
  		    return caseSetupDao.createCaseSetup(caseSetup);
		} else {
			return caseSetupDao.updateCaseSetup(caseSetup);
		}
	}
	
	public void deleteCaseSetup(CaseSetup caseSetup) throws DAOException {
		caseSetupDao.deleteCaseSetup(caseSetup);	
	}
	

}
