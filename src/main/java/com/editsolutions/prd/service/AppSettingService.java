package com.editsolutions.prd.service;


import java.util.HashMap;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.AppSetting;

public interface AppSettingService {
	
	public List<AppSetting> getAppSettings() throws DAOException;
	public HashMap<String, String> getAppSettingsHash() throws DAOException;
	public AppSetting saveAppSetting(AppSetting appSetting) throws DAOException;
	public void deleteAppSetting(AppSetting appSetting) throws DAOException;

}
