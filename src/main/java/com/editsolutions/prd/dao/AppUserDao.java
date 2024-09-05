package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.AppSetting;

import java.util.HashMap;

import edit.portal.common.session.UserSession;
import edit.services.db.hibernate.SessionHelper;

public class AppUserDao extends Dao<UserSession> {

	public AppUserDao() {
        super(UserSession.class);
    }
	
	public UserSession getUserSession() {
		//Session session = SessionHelper.getSession
		return null;
	}
	
}
