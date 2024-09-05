package com.editsolutions.prd.service;


import java.util.HashMap;
import java.util.List;

import com.editsolutions.prd.dao.AppSettingDao;
import com.editsolutions.prd.dao.AppUserDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.AppSetting;

import edit.portal.common.session.UserSession;

public class AppUserServiceImpl implements AppUserService {
	private AppUserDao appUserDao;

	public AppUserServiceImpl() {
		super();
		appUserDao = new AppUserDao();
	}

	@Override
	public UserSession getUserSession() {
		return appUserDao.getUserSession();
	}
	
}