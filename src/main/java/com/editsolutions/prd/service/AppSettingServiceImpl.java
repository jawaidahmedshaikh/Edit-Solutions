package com.editsolutions.prd.service;


import java.util.HashMap;
import java.util.List;

import com.editsolutions.prd.dao.AppSettingDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.AppSetting;

public class AppSettingServiceImpl implements AppSettingService {
	private AppSettingDao appSettingDao;

	public AppSettingServiceImpl() {
		super();
		appSettingDao = new AppSettingDao();
	}
	
	public List<AppSetting> getAppSettings() throws DAOException {
		return appSettingDao.getAppSettings();
	}
	
	public HashMap<String, String> getAppSettingsHash() throws DAOException {
		return appSettingDao.getAppSettingsHash();
	}

	public AppSetting saveAppSetting(AppSetting appSetting) throws DAOException {
		if (appSetting.getAppSettingPK() == null) {
		    return appSettingDao.createAppSetting(appSetting);
		} else {
		    return appSettingDao.updateAppSetting(appSetting);
		}
	}
	public void deleteAppSetting(AppSetting appSetting) throws DAOException {
        appSettingDao.deleteAppSetting(appSetting);
	}

}