package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.CaseSetup;
import com.editsolutions.prd.vo.GroupSetup;

public interface GroupSetupService {
	
	public List<GroupSetup> getGroupSetupsBySearchString(String s) throws DAOException;
//	public GroupSetup createGroupSetup(GroupSetup groupSetup);
//	public GroupSetup updateGroupSetup(GroupSetup groupSetup);
	public GroupSetup saveGroupSetup(GroupSetup groupSetup) throws DAOException;
	public boolean groupExists(String groupNumber) throws DAOException;
	public GroupSetup getGroupSetupByNumber(String groupNumber) throws DAOException;
	public List<GroupSetup> getGroupSetups(Long caseSetupPK) throws DAOException;
	public void deleteGroupSetups(List<GroupSetup> groupSetups) throws DAOException;
	public void deleteGroupSetup(GroupSetup groupSetup) throws DAOException;

}
