package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SQLCriterion;

import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataIssuesService;
import com.editsolutions.prd.service.DataIssuesServiceImpl;
import com.editsolutions.prd.service.DataServiceImpl;
import com.editsolutions.prd.service.DataService;
import com.editsolutions.prd.service.GroupSetupService;
import com.editsolutions.prd.service.GroupSetupServiceImpl;
import com.editsolutions.prd.service.NotificationService;
import com.editsolutions.prd.service.NotificationServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.CaseSetup;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;
import com.editsolutions.prd.vo.GroupSetup;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.PayrollDeductionData;

import edit.services.db.hibernate.SessionHelper;

public class CaseSetupDao extends Dao<CaseSetup> {

	public CaseSetupDao() {
        super(CaseSetup.class);
    }

	public CaseSetup getCaseSetupByCaseNumber(String caseNumber) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "select Case_ContractGroupPK from PRD_Case  " +
		                   "where CaseNumber = ? "; 
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
		    preparedStatement.setString(1, caseNumber);
		    ResultSet rSet = preparedStatement.executeQuery();
		    if(rSet.next()) {
		    	return getCaseSetupFromPK(rSet.getLong(1));
		    } else {
		    	System.out.println("not found: " + caseNumber);
		    }
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return null;
	}
	
	public CaseSetup getCaseSetupByGroupNumber(String groupNumber) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		GroupSetupService groupSetupService = new GroupSetupServiceImpl();
        GroupSetup groupSetup = groupSetupService.getGroupSetupByNumber(groupNumber);
        CaseSetup caseSetup = new CaseSetup();
        if (groupSetup != null) {
        	caseSetup = getCaseSetupFromPK(groupSetup.getCaseContractPk());
        	caseSetup.setGroupSetups(groupSetupService.getGroupSetups(caseSetup.getCaseContractPk()));
        }

        return caseSetup;
	}
	
	public void deleteCaseSetup(CaseSetup caseSetup) throws DAOException {
		
		if (caseSetup.getGroupSetups() != null) { 
		    GroupSetupService gss = new GroupSetupServiceImpl();
		    gss.deleteGroupSetups(caseSetup.getGroupSetups());
		}
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete from PRD_Case " +
		                   "where Case_ContractGroupPK = ? "; 
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
		    preparedStatement.setLong(1, caseSetup.getCaseContractPk());
		    preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
			
		}
	}
	
	public CaseSetup getCaseSetupFromPK(Long pk) throws DAOException {
		List<CaseSetup> caseSetups = new ArrayList<>();
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "select distinct c.* from vw_PRD_Case c " +
		                   "where c.Case_ContractGroupPK = ? "; 
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
		    preparedStatement.setLong(1, pk);
		    ResultSet rSet = preparedStatement.executeQuery();
		    while(rSet.next()) {
		    	CaseSetup caseSetup = new CaseSetup();
		    	caseSetup.setCaseContractPk(rSet.getLong(1));
		    	caseSetup.setCaseNumber(rSet.getString(2));
		    	caseSetup.setEffectiveDate(rSet.getDate(3));
		    	caseSetup.setTerminationDate(rSet.getDate(4));
		    	caseSetup.setRequirementNotifyDayCT(rSet.getString(5));
		    	caseSetup.setCaseTypeCT(rSet.getString(6));
		    	caseSetup.setDomicileStateCT(rSet.getString(7));
		    	caseSetup.setGroupTrustStateCT(rSet.getString(8));
		    	caseSetup.setClientRolePK(rSet.getLong(9));
		    	caseSetup.setReferenceID(rSet.getString(10));
		    	caseSetup.setClientDetailPK(rSet.getLong(11));
		    	caseSetup.setCaseName(rSet.getString(12));
		    	caseSetup.setAddressLine1(rSet.getString(13));
		    	caseSetup.setAddressLine2(rSet.getString(14));
		    	caseSetup.setAddressLine3(rSet.getString(15));
		    	caseSetup.setAddressLine4(rSet.getString(16));
		    	caseSetup.setCity(rSet.getString(17));
		    	caseSetup.setStateCT(rSet.getString(18));
		    	caseSetup.setZipCode(rSet.getString(19));
		    	caseSetup.setChangeEffectiveDate(rSet.getDate(20));
		    	caseSetup.setSystemCT(rSet.getString(21));
		    	caseSetup.setAddDate(rSet.getDate(22));
		    	caseSetup.setAddUser(rSet.getString(23));
		    	caseSetup.setModDate(rSet.getDate(24));
		    	caseSetup.setModUser(rSet.getString(25));
		    	caseSetups.add(caseSetup);
		    }
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return caseSetups.get(0);
	}
	
	
	
	public boolean caseExists(String caseNumber) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
        Criteria c = session.createCriteria(CaseSetup.class);
        c.add(Restrictions.eq("caseNumber", caseNumber));
        List<CaseSetup> caseSetups = findAll(c);
        if (caseSetups.size() > 0) {
        	return true;
        }
        return false;
	}
	
	public CaseSetup getCaseSetup(String s) throws DAOException{
		Long id = Long.parseLong(s);
		return get(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<CaseSetup> getCaseSetupsWithCorrespondence() throws DAOException {
		List<CaseSetup> caseSetups = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		StringBuilder sql = new StringBuilder("select distinct c.* ");
        sql.append("from vw_PRD_Case c, vw_PRD_Group g, vw_PRD_Setup p ");
        sql.append("where p.Case_ContractGroupFK = g.Case_ContractGroupPK ");
        sql.append("and g.Case_ContractGroupPK = c.Case_ContractGroupPK ");
        sql.append("and p.PRD_SetupPK in ("); 
        sql.append("select distinct n.prdSetupFK from PRD_Notifications n) ");

		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()); 
		sqlQuery.addEntity(CaseSetup.class);
		caseSetups = sqlQuery.list();
		
		return caseSetups;
	}
	
	
	public List<CaseSetup> getCaseSetupsByRepName(String repName) throws DAOException {
		List<CaseSetup> caseSetups = new ArrayList<>();
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "select distinct c.* from vw_PRD_Setup p, vw_PRD_Case c " +
		                   "where p.Case_ContractGroupFK = c.Case_ContractGroupPK " +
		                   "and RepName = ? "; 
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
		    preparedStatement.setString(1, repName);
		    ResultSet rSet = preparedStatement.executeQuery();
		    while(rSet.next()) {
		    	caseSetups.add(get(rSet.getLong(1)));
		    }
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return caseSetups;
	}
	
	public List<CaseSetup> getCaseSetupsByRepNameWithoutGroups(String repName) throws DAOException {
		List<CaseSetup> caseSetups = new ArrayList<>();
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "select distinct c.* from vw_PRD_Setup p, vw_PRD_Case c " +
		                   "where p.Case_ContractGroupFK = c.Case_ContractGroupPK " +
		                   "and RepName = ? "; 
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
		    preparedStatement.setString(1, repName);
		    ResultSet rSet = preparedStatement.executeQuery();
		    while(rSet.next()) {
		    	CaseSetup caseSetup = new CaseSetup();
		    	caseSetup.setCaseContractPk(rSet.getLong(1));
		    	caseSetup.setCaseNumber(rSet.getString(2));
		    	caseSetup.setEffectiveDate(rSet.getDate(3));
		    	caseSetup.setTerminationDate(rSet.getDate(4));
		    	caseSetup.setRequirementNotifyDayCT(rSet.getString(5));
		    	caseSetup.setCaseTypeCT(rSet.getString(6));
		    	caseSetup.setDomicileStateCT(rSet.getString(7));
		    	caseSetup.setGroupTrustStateCT(rSet.getString(8));
		    	caseSetup.setClientRolePK(rSet.getLong(9));
		    	caseSetup.setReferenceID(rSet.getString(10));
		    	caseSetup.setClientDetailPK(rSet.getLong(11));
		    	caseSetup.setCaseName(rSet.getString(12));
		    	caseSetup.setAddressLine1(rSet.getString(13));
		    	caseSetup.setAddressLine2(rSet.getString(14));
		    	caseSetup.setAddressLine3(rSet.getString(15));
		    	caseSetup.setAddressLine4(rSet.getString(16));
		    	caseSetup.setCity(rSet.getString(17));
		    	caseSetup.setStateCT(rSet.getString(18));
		    	caseSetup.setZipCode(rSet.getString(19));
		    	caseSetup.setChangeEffectiveDate(rSet.getDate(20));
		    	caseSetup.setSystemCT(rSet.getString(21));
		    	caseSetup.setAddDate(rSet.getDate(22));
		    	caseSetup.setAddUser(rSet.getString(23));
		    	caseSetup.setModDate(rSet.getDate(24));
		    	caseSetup.setModUser(rSet.getString(25));
		    	caseSetups.add(caseSetup);
		    }
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return caseSetups;
	}
	
	
	public List<CaseSetup> getCaseSetupsBySearchString(String s, boolean byName) throws DAOException {
        List<CaseSetup> caseSetups = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
        Criteria csCriteria = session.createCriteria(CaseSetup.class);
        session.evict(caseSetups);
/*
		if (!byName && (s.length() == 3)) {
			GroupSetup groupSetup = new GroupSetup();
			GroupSetupService groupSetupService = new GroupSetupServiceImpl();
			groupSetup = groupSetupService.getGroupSetupByNumber(s);
		    if (groupSetup != null) {
		    	if (groupSetup.getCaseSetup() != null) {
		    		s = groupSetup.getCaseSetup().getCaseNumber(); 
		    	}
		    }
		}  
*/

		if (byName) {
        	if (s.endsWith("*")) {
        		s = s.substring(0, s.length() - 1);
        	    csCriteria.add(Restrictions.ilike("caseName", s + "%", MatchMode.START));
        	} else if (s.startsWith("*")) {
        		s = s.substring(1, s.length());
        	    csCriteria.add(Restrictions.ilike("caseName", "%" + s, MatchMode.END));
        	} else {
        	    csCriteria.add(Restrictions.ilike("caseName", s + "%", MatchMode.START));
        	}
        	csCriteria.addOrder( Order.asc("caseName"));
        } else {
        	if (s.endsWith("*")) {
        		s = s.substring(0, s.length() - 1);
        	    csCriteria.add(Restrictions.ilike("caseNumber", s + "%", MatchMode.START));
        	} else if (s.startsWith("*")) {
        		s = s.substring(1, s.length());
        	    csCriteria.add(Restrictions.ilike("caseNumber", "%" + s, MatchMode.END));
        	} else {
//        	    c.add(Restrictions.eq("caseNumber", s).ignoreCase());
        	    csCriteria.add(Restrictions.ilike("caseNumber", s + "%", MatchMode.START));
        	}
        	csCriteria.addOrder( Order.asc("caseNumber"));
        } 
        caseSetups = findAll(csCriteria);
		return caseSetups;
	}

	public CaseSetup createCaseSetup(CaseSetup caseSetup) throws DAOException {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_Case" 
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, caseSetup.getCaseNumber());
			preparedStatement.setDate(2, caseSetup.getEffectiveDate());
			preparedStatement.setDate(3, caseSetup.getTerminationDate());
			preparedStatement.setString(4, caseSetup.getRequirementNotifyDayCT());
			preparedStatement.setString(5, caseSetup.getCaseTypeCT());
			preparedStatement.setString(6, caseSetup.getDomicileStateCT());
			preparedStatement.setString(7, caseSetup.getGroupTrustStateCT());
			preparedStatement.setLong(8, 0L);
			preparedStatement.setString(9, caseSetup.getReferenceID());
			preparedStatement.setLong(10, 0L);
			preparedStatement.setString(11, caseSetup.getCaseName());
			preparedStatement.setString(12, caseSetup.getAddressLine1());
			preparedStatement.setString(13, caseSetup.getAddressLine2());
			preparedStatement.setString(14, caseSetup.getAddressLine3());
			preparedStatement.setString(15, caseSetup.getAddressLine4());
			preparedStatement.setString(16, caseSetup.getCity());
			preparedStatement.setString(17, caseSetup.getStateCT());
			preparedStatement.setString(18, caseSetup.getZipCode());
			preparedStatement.setDate(19, caseSetup.getChangeEffectiveDate());
			preparedStatement.setString(20, caseSetup.getSystemCT());
			preparedStatement.setDate(21, caseSetup.getAddDate());
			preparedStatement.setString(22, caseSetup.getAddUser());
			preparedStatement.setDate(23, caseSetup.getModDate());
			preparedStatement.setString(24, caseSetup.getModUser());

			preparedStatement.execute();
	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	caseSetup.setCaseContractPk(generatedKeys.getLong(1));
	            }
	            else {
	                throw new SQLException("Creating case failed, no ID obtained.");
	            }
	        }

		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return caseSetup;
	}

	public CaseSetup updateCaseSetup(CaseSetup caseSetup) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_Case" +
				" set CaseNumber = ?, EffectiveDate = ?, TerminationDate = ?, " +
				" RequirementNotifyDayCT = ?, CaseTypeCT = ?, DomicileStateCT = ?, GroupTrustStateCT = ?, " +
				" ClientRolePK = ?, ReferenceID = ?, ClientDetailPK = ?, CaseName = ?, AddressLine1 = ?, " + 
				" AddressLine2 = ?, AddressLine3 = ?, AddressLine4 = ?, City = ?, StateCT = ?, " + 
				" ZipCode = ?, ChangeEffectiveDate = ?, SystemCT = ? " +
				" where Case_ContractGroupPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, caseSetup.getCaseNumber());
			preparedStatement.setDate(2, caseSetup.getEffectiveDate());
			preparedStatement.setDate(3, caseSetup.getTerminationDate());
			preparedStatement.setString(4, caseSetup.getRequirementNotifyDayCT());
			preparedStatement.setString(5, caseSetup.getCaseTypeCT());
			preparedStatement.setString(6, caseSetup.getDomicileStateCT());
			preparedStatement.setString(7, caseSetup.getGroupTrustStateCT());
			if (caseSetup.getClientRolePK() != null) {
			    preparedStatement.setLong(8, caseSetup.getClientRolePK());
			} else {
			    preparedStatement.setLong(8, 0L);
			}
			preparedStatement.setString(9, caseSetup.getReferenceID());
			if (caseSetup.getClientDetailPK() != null) {
			    preparedStatement.setLong(10, caseSetup.getClientDetailPK());
			} else {
			    preparedStatement.setLong(10, 0L);
			}
			preparedStatement.setString(11, caseSetup.getCaseName());
			preparedStatement.setString(12, caseSetup.getAddressLine1());
			preparedStatement.setString(13, caseSetup.getAddressLine2());
			preparedStatement.setString(14, caseSetup.getAddressLine3());
			preparedStatement.setString(15, caseSetup.getAddressLine4());
			preparedStatement.setString(16, caseSetup.getCity());
			preparedStatement.setString(17, caseSetup.getStateCT());
			preparedStatement.setString(18, caseSetup.getZipCode());
			preparedStatement.setDate(19, caseSetup.getChangeEffectiveDate());
			preparedStatement.setString(20, caseSetup.getSystemCT());
			preparedStatement.setLong(21, caseSetup.getCaseContractPk());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return caseSetup;
	}

}
