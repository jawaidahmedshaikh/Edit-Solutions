package com.editsolutions.prd.service;

import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.dao.GroupSetupDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.CaseSetup;
import com.editsolutions.prd.vo.GroupSetup;

public class GroupSetupServiceImpl implements GroupSetupService {
	private GroupSetupDao groupSetupDao;

	public GroupSetupServiceImpl() {
		super();
		groupSetupDao = new GroupSetupDao();
	}

	public boolean groupExists(String groupNumber) throws DAOException {
		return groupSetupDao.groupExists(groupNumber);
	}

	public List<GroupSetup> getGroupSetupsBySearchString(String s)
			throws DAOException {
		return groupSetupDao.getGroupSetupsBySearchString(s);
	}
	
	public List<GroupSetup> getGroupSetups(Long caseSetupPK) throws DAOException {
		return groupSetupDao.getGroupSetups(caseSetupPK);
	}
	public GroupSetup getGroupSetupByNumberForContacts(String groupNumber) throws DAOException {
		return groupSetupDao.getGroupSetupByNumber(groupNumber);
	}
	
	public GroupSetup getGroupSetupByNumber(String groupNumber) throws DAOException {
		return groupSetupDao.getGroupSetupByNumber(groupNumber);
	}

	public GroupSetup getGroupSetup(Serializable id) throws DAOException {
		return groupSetupDao.get(id);
	}

	public GroupSetup saveGroupSetup(GroupSetup groupSetup) throws DAOException {
		if (groupSetup.getGroupContractPk() == null) {
		    return groupSetupDao.createGroupSetup(groupSetup);
		} else {
		    return groupSetupDao.updateGroupSetup(groupSetup);
		}
	}
	
	public void deleteGroupSetups(List<GroupSetup> groupSetups) throws DAOException {
		groupSetupDao.deleteGroupSetups(groupSetups);
	}

	public void deleteGroupSetup(GroupSetup groupSetup) throws DAOException {
		groupSetupDao.deleteGroupSetup(groupSetup);
	}
	

}
