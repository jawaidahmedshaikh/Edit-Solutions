package com.editsolutions.prd.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.CaseSetupServiceImpl;
import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.service.GroupSetupService;
import com.editsolutions.prd.service.GroupSetupServiceImpl;
import com.editsolutions.prd.service.NotificationService;
import com.editsolutions.prd.service.NotificationServiceImpl;
import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.CaseSetup;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.GroupSetup;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.PRDSettings;

import edit.services.db.hibernate.SessionHelper;

public class GroupSetupDao extends Dao<GroupSetup> {

	public GroupSetupDao() {
        super(GroupSetup.class);
    }
	
	public GroupSetup getGroupSetup(Serializable id) throws DAOException {
		return get(id);
	}
	
	public boolean groupExists(String groupNumber) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
        Criteria c = session.createCriteria(GroupSetup.class);
        c.add(Restrictions.eq("groupNumber", groupNumber));
        List<GroupSetup> groupSetups = findAll(c);
        if (groupSetups.size() > 0) {
        	return true;
        }
        return false;
	}
	
	public List<GroupSetup> getGroupSetups(Long caseSetupPK) throws DAOException{
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
        Criteria c = session.createCriteria(GroupSetup.class);
        c.add(Restrictions.eq("caseContractPk", caseSetupPK));
        List<GroupSetup> groupSetups = findAll(c);
       	return groupSetups;
	}

	public GroupSetup getGroupSetupByNumber(String groupNumber) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
        Criteria c = session.createCriteria(GroupSetup.class);
        c.add(Restrictions.eq("groupNumber", groupNumber));
        //c.add(Restrictions.isNotNull("prdSettings"));
        
        // the adding of Groups to VENUS for the Bill Portal site is causing a problem with duplicate groups in the vw_PRD_Group view. 
        // Need to pick one to use based on which is has a related PRD_Setup. Not good, will need to figure this out at some point. 2019-09-30
        List<GroupSetup> groupSetups = findAll(c);
        GroupSetup groupSetup = null;
        Iterator <GroupSetup> it = groupSetups.iterator();
        while (it.hasNext()) {
        	groupSetup = it.next();
            if (groupSetups.size() > 1) {
            	if ((groupSetup!= null) && (groupSetup.getPrdSettings() != null)) {
   	              	CaseSetup caseSetup = new CaseSetupServiceImpl().getCaseSetupFromPK(groupSetup.getCaseContractPk());
   	              	caseSetup.setGroupSetups(getGroupSetups(caseSetup.getCaseContractPk()));
   	              	groupSetup.setCaseSetup(caseSetup);
            	    return groupSetup;	
            	}
            }
        	
        }
        if (groupSetup != null) {
   	        CaseSetup caseSetup = new CaseSetupServiceImpl().getCaseSetupFromPK(groupSetup.getCaseContractPk());
  	        caseSetup.setGroupSetups(getGroupSetups(caseSetup.getCaseContractPk()));
            groupSetup.setCaseSetup(caseSetup);
        }
        return groupSetup;
	}

	/*
	public GroupSetup getGroupSetupByNumber(String groupNumber) {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
        Criteria c = session.createCriteria(GroupSetup.class);
        c.add(Restrictions.eq("groupNumber", groupNumber));
        List<GroupSetup> groupSetups = findAll(c);
        if (groupSetups.size() > 0) {
        	return groupSetups.get(0);
        }
        return null;
	}
	*/

	public List<GroupSetup> getGroupSetupsBySearchString(String s) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(GroupSetup.class);
        if (s.length() == 3) {
            c.add(Restrictions.eq("groupNumber", s));
        } else {
        	c.add(Restrictions.ilike("groupName", "%" + s + "%"));
        }
		return findAll(c);
	}
	
	public GroupSetup createGroupSetup(GroupSetup groupSetup) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_Group " 
				+ "(GroupNumber, Case_ContractGroupFK, CaseNumber, EffectiveDate, " +
				   "TerminationDate, RequirementNotifyDayCT, CaseTypeCT, DomicileStateCT, " +
				   "GroupTrustStateCT, ClientRolePK, ReferenceID, ClientDetailPK, " +
				   "GroupName, AddressLine1, AddressLine2, AddressLine3, AddressLine4, " +
				   "City, StateCT, ZipCode, ChangeEffectiveDate, SystemCT, DeptLocCode, " +
				   "DeptLocName, AddDate, AddUser, ModDate, ModUser) " +
				   " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
				   "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, groupSetup.getGroupNumber());
			preparedStatement.setLong(2, groupSetup.getCaseContractPk());
			preparedStatement.setString(3, groupSetup.getCaseNumber());
			preparedStatement.setDate(4, groupSetup.getEffectiveDate());
			preparedStatement.setDate(5, groupSetup.getTerminationDate());
			preparedStatement.setString(6, groupSetup.getRequirementNotifyDayCT());
			preparedStatement.setString(7, groupSetup.getCaseTypeCT());
			preparedStatement.setString(8, groupSetup.getDomicileStateCT());
			preparedStatement.setString(9, groupSetup.getGroupTrustStateCT());
			preparedStatement.setLong(10, 0L);
			preparedStatement.setString(11, groupSetup.getReferenceID());
			preparedStatement.setLong(12, 0L);
			preparedStatement.setString(13, groupSetup.getGroupName());
			preparedStatement.setString(14, groupSetup.getAddressLine1());
			preparedStatement.setString(15, groupSetup.getAddressLine2());
			preparedStatement.setString(16, groupSetup.getAddressLine3());
			preparedStatement.setString(17, groupSetup.getAddressLine4());
			preparedStatement.setString(18, groupSetup.getCity());
			preparedStatement.setString(19, groupSetup.getStateCT());
			preparedStatement.setString(20, groupSetup.getZipCode());
			preparedStatement.setDate(21, groupSetup.getChangeEffectiveDate());
			preparedStatement.setString(22, groupSetup.getSystemCT());
			preparedStatement.setString(23, "");
			preparedStatement.setString(24, "");
			preparedStatement.setDate(25, groupSetup.getAddDate());
			preparedStatement.setString(26, groupSetup.getAddUser());
			preparedStatement.setDate(27, groupSetup.getModDate());
			preparedStatement.setString(28, groupSetup.getModUser());
			preparedStatement.executeUpdate();
	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	groupSetup.setGroupContractPk(generatedKeys.getLong(1));
	            }
	            else {
	                throw new SQLException("Creating case failed, no ID obtained.");
	            }
	        }

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return groupSetup;
	}
	
	
	public GroupSetup updateGroupSetup(GroupSetup groupSetup)  throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_Group" +
				" set GroupNumber = ?, CaseNumber = ?, EffectiveDate = ?, TerminationDate = ?, " +
				" RequirementNotifyDayCT = ?, CaseTypeCT = ?, DomicileStateCT = ?, GroupTrustStateCT = ?, " +
				" ClientRolePK = ?, ReferenceID = ?, ClientDetailPK = ?, GroupName = ?, AddressLine1 = ?, " + 
				" AddressLine2 = ?, AddressLine3 = ?, AddressLine4 = ?, City = ?, StateCT = ?, " + 
				" ZipCode = ?, ChangeEffectiveDate = ?, SystemCT = ? " +
				" where Group_ContractGroupPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, groupSetup.getGroupNumber());
			preparedStatement.setString(2, groupSetup.getCaseNumber());
			preparedStatement.setDate(3, groupSetup.getEffectiveDate());
			preparedStatement.setDate(4, groupSetup.getTerminationDate());
			preparedStatement.setString(5, groupSetup.getRequirementNotifyDayCT());
			preparedStatement.setString(6, groupSetup.getCaseTypeCT());
			preparedStatement.setString(7, groupSetup.getDomicileStateCT());
			preparedStatement.setString(8, groupSetup.getGroupTrustStateCT());
			if (groupSetup.getClientRolePK() != null) {
			    preparedStatement.setLong(9, groupSetup.getClientRolePK());
			} else {
			    preparedStatement.setLong(9, 0L);
			}
			preparedStatement.setString(10, groupSetup.getReferenceID());
			if (groupSetup.getClientDetailPK() != null) {
			    preparedStatement.setLong(11, groupSetup.getClientDetailPK());
			} else {
			    preparedStatement.setLong(11, 0L);
			}
			preparedStatement.setString(12, groupSetup.getGroupName());
			preparedStatement.setString(13, groupSetup.getAddressLine1());
			preparedStatement.setString(14, groupSetup.getAddressLine2());
			preparedStatement.setString(15, groupSetup.getAddressLine3());
			preparedStatement.setString(16, groupSetup.getAddressLine4());
			preparedStatement.setString(17, groupSetup.getCity());
			preparedStatement.setString(18, groupSetup.getStateCT());
			preparedStatement.setString(19, groupSetup.getZipCode());
			preparedStatement.setDate(20, groupSetup.getChangeEffectiveDate());
			preparedStatement.setString(21, groupSetup.getSystemCT());
			preparedStatement.setLong(22, groupSetup.getGroupContractPk());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return groupSetup;
	}
	
	public void deleteGroupSetup(GroupSetup groupSetup) throws DAOException {
		List<GroupSetup> groupSetups = new ArrayList<>();
		groupSetups.add(groupSetup);
		deleteGroupSetups(groupSetups);
	}

	public void deleteGroupSetups(List<GroupSetup> groupSetups) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_Group " + " where Group_ContractGroupPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			Iterator<GroupSetup> it = groupSetups.iterator();
			while (it.hasNext()) {
				GroupSetup groupSetup = it.next();
			    if (groupSetup.getPrdSettings() != null) {
			    	PRDSettingsService prdService = new PRDSettingsServiceImpl();
			    	prdService.deletePRDSettings(groupSetup.getPrdSettings());
			    }
				preparedStatement.setLong(1, groupSetup.getGroupContractPk());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		
	}
	

}
