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

import edit.services.db.hibernate.SessionHelper;

public class AppSettingDao extends Dao<AppSetting> {

	public AppSettingDao() {
        super(AppSetting.class);
    }
	
	public HashMap<String, String> getAppSettingsHash() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(AppSetting.class);
		cr.addOrder(Order.desc("displayOrder"));
		@SuppressWarnings("unchecked")
		List<AppSetting> list = cr.list();
		Iterator<AppSetting> it = list.iterator();
		HashMap<String, String> hm = new HashMap<>();
		while (it.hasNext()) {
			AppSetting as = it.next();
            hm.put(as.getName(), as.getValue());
		}
		return hm;
	}

	public List<AppSetting> getAppSettings() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(AppSetting.class);
		cr.addOrder(Order.desc("displayOrder"));
		@SuppressWarnings("unchecked")
		List<AppSetting> list = cr.list();
		return list;
	}
	
	public AppSetting createAppSetting(AppSetting appSetting) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_AppSetting" 
					+ " values (?, ?, ?, ?, ?, ?, ?, ?) ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, appSetting.getName());
				preparedStatement.setString(2, appSetting.getValue());
				preparedStatement.setString(3, appSetting.getType());
				preparedStatement.setString(4, appSetting.getDescription());
				preparedStatement.setInt(5, appSetting.getDisplayOrder());
				preparedStatement.setDate(6, null);
				preparedStatement.setString(7, null);
				preparedStatement.setDate(8, null);
				preparedStatement.setString(9, null);

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	appSetting.setAppSettingPK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new SQLException("Creating appSetting failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				System.out.println(e.toString());
			    throw new DAOException(e);
			}
		return appSetting;
	}
	
	public AppSetting updateAppSetting(AppSetting appSetting) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "update PRD_AppSettings" 
					+ " set Name = ?, Value = ?, Type = ?, Description = ?, DisplayOrder = ?  "
					+ " where PRD_AppSettingsPK = ?";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, appSetting.getName());
				preparedStatement.setString(2, appSetting.getValue());
				preparedStatement.setString(3, appSetting.getType());
				preparedStatement.setString(4, appSetting.getDescription());
				preparedStatement.setInt(5, appSetting.getDisplayOrder());
				preparedStatement.setLong(6, appSetting.getAppSettingPK());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.toString());
			    throw new DAOException(e);
			}
		return appSetting;
	}
	
	public void deleteAppSetting(AppSetting appSetting) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_AppSetting" 
				+ " where PRD_AppSettingPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, appSetting.getAppSettingPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
    }	

}
