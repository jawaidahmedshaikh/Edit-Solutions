package com.editsolutions.prd.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.PRDSettings;

public interface PRDSettingsService {
	
	public PRDSettings getPRDSettings(Serializable id) throws DAOException;
	public List<PRDSettings> getPRDSettingsByExtractDate(Date extractDate, String prdType) throws DAOException;
	public List<PRDSettings> getPRDSettingsByExtractDate(Date extractDate) throws DAOException;
	public List<PRDSettings> getPRDSettingsByLastExtractDate(Date extractDate, String prdType) throws DAOException;
	public List<PRDSettings> getPRDSettingsByLastExtractDate(Date extractDate) throws DAOException;
	public PRDSettings savePRDSettings(PRDSettings prdSettings) throws DAOException;
	public PRDSettings updateExtractDate(PRDSettings prdSettings, Date extractDate) throws DAOException;
	public PRDSettings updateESPRDExtractDate(PRDSettings prdSettings, Date extractDate) throws DAOException; 
	public void deletePRDSettings(PRDSettings prdSettings) throws DAOException;
	public boolean exportDirectoryExists(PRDSettings prdSettings);
	public PRDSettings getByGroupSetupPK(Serializable contractGroupPK) throws DAOException;
	public List<PRDSettings> getOddBallPRDs() throws DAOException;
	public List<String> getSenderEmails() throws DAOException;
	
}
