package com.editsolutions.prd.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.dao.PRDSettingsDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.PRDSettings;


public class PRDSettingsServiceImpl implements PRDSettingsService {
	
	private PRDSettingsDao prdSettingsDao;
	
	public PRDSettingsServiceImpl() {
		super();
		prdSettingsDao = new PRDSettingsDao();
	}

	@Override
	public List<PRDSettings> getPRDSettingsByLastExtractDate(Date extractDate) throws DAOException {
	    return prdSettingsDao.getPRDSettingsByLastExtractDate(extractDate);
	}
	
	public PRDSettings getByGroupSetupPK(Serializable id) throws DAOException {
	    return prdSettingsDao.getByGroupSetupPK(id);	
	}

	@Override
	public List<PRDSettings> getPRDSettingsByLastExtractDate(Date extractDate, String prdType) throws DAOException {
	    return prdSettingsDao.getPRDSettingsByLastExtractDate(extractDate, prdType);
	}

	@Override
	public List<PRDSettings> getPRDSettingsByExtractDate(Date extractDate) throws DAOException {
	    return prdSettingsDao.getPRDSettingsByExtractDate(extractDate);
	}

	@Override
	public List<PRDSettings> getPRDSettingsByExtractDate(Date extractDate, String prdType) throws DAOException {
	    return prdSettingsDao.getPRDSettingsByExtractDate(extractDate, prdType);
	}

	@Override
	public List<String> getSenderEmails() throws DAOException {
	    return prdSettingsDao.getSenderEmails();	
	}

	@Override
	public PRDSettings getPRDSettings(Serializable id) throws DAOException {
	    return prdSettingsDao.get(id);
	}

	@Override
	public void deletePRDSettings(PRDSettings prdSettings) throws DAOException {
		prdSettingsDao.deletePRDSettings(prdSettings);
    }
	
	@Override
	public PRDSettings savePRDSettings(PRDSettings prdSettings) throws DAOException {
		if (prdSettings.getPrdSetupPK() == null) {
  		    return prdSettingsDao.savePRDSettings(prdSettings);
		} else {
  		    return prdSettingsDao.updatePRDSettings(prdSettings);
		}
	}
	
	public PRDSettings updateESPRDExtractDate(PRDSettings prdSettings, Date extractDate) throws DAOException {
		return prdSettingsDao.updateESPRDExtractDate(prdSettings, extractDate);
	}
	
	public PRDSettings saveESPRDSettings(String caseSetupPK, String groupSetupPK) throws DAOException {
		return prdSettingsDao.saveESPRDSettings(Long.parseLong(caseSetupPK), Long.parseLong(groupSetupPK));
	}

	@Override
	public PRDSettings updateExtractDate(PRDSettings prdSettings, Date extractDate) throws DAOException {
  		    return prdSettingsDao.updatePRDExtractDate(prdSettings, extractDate);
	}

	public boolean exportDirectoryExists(PRDSettings prdSettings) {
	    return prdSettingsDao.exportDirectoryExists(prdSettings);	
	}

	@Override
	public List<PRDSettings> getOddBallPRDs() throws DAOException {
	    return prdSettingsDao.getOddBallPRDs();
	}

}
