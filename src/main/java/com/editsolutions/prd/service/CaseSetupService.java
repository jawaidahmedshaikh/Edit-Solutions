package com.editsolutions.prd.service;

import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.CaseSetup;

import electric.util.holder.booleanInOut;

public interface CaseSetupService {
	
	public List<CaseSetup> getCaseSetupsBySearchString(String s, boolean byName) throws DAOException;
	public List<CaseSetup> getCaseSetupsWithCorrespondence() throws DAOException;
	public List<CaseSetup> getCaseSetupsByRepName(String repName) throws DAOException;
	public CaseSetup getCaseSetup(String s) throws DAOException;
	public CaseSetup get(Serializable id) throws DAOException;
	public CaseSetup saveCaseSetup(CaseSetup caseSetup) throws DAOException;
	public boolean caseExists(String caseNumber) throws DAOException;
	public CaseSetup getCaseSetupByGroupNumber(String groupNumber) throws DAOException;
	public CaseSetup getCaseSetupByCaseNumber(String caseNumber) throws DAOException;
	public CaseSetup getCaseSetupFromPK(Long pk) throws DAOException;
	public void deleteCaseSetup(CaseSetup caseSetup) throws DAOException;

}
