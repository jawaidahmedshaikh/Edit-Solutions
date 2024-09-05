package com.editsolutions.prd.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.util.PRDFileUtils;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.PRDSettings;

import edit.services.db.hibernate.SessionHelper;

public class NotificationDao extends Dao<Notification> {

	public NotificationDao() {
        super(Notification.class);
    }

	@SuppressWarnings("unchecked")
	public List<Notification> getPendingNotifications(Serializable id) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(Notification.class);
        c.add(Restrictions.eq("prdSetupFK", id));
        c.add(Restrictions.eq("statusCT", "P"));
        List<Notification> notifications = c.list();
		return notifications;
	}

	public Notification getNotification(Serializable id) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(Notification.class);
        c.add(Restrictions.eq("prdSetupFK", id));
		return get(c);
	}

	public Notification get(Serializable id) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(Notification.class);
        c.add(Restrictions.eq("notificationPK", id));
		return get(c);
	}
	
	@SuppressWarnings("unchecked")
	public List<Notification> getNotifications(PRDSettings prdSettings) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(Notification.class);
        c.add(Restrictions.eq("prdSetupFK", prdSettings.getPrdSetupPK()));
        c.addOrder(Order.desc("dueDate"));
        List<Notification> notifications = c.list();
		return notifications;
	}

	public Notification updateNotification(Notification notification) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "update PRD_Notification set PRD_SetupFK = ?, DateSent = ?, DueDate = ?, EmailTo = ?, EmailCC = ?, EmailBCC = ?, " 
					+ "FTPAddress = ?, MessageSubject = ?, MessageText = ?, FileAttachment = ?, FileAttachmentName = ?, HasAttachment = ?, "
					+ "StatusCT = ?, FTPUsername = ?, FTPPassword = ?, ExportDirectory = ?,  PRD_DataHeaderFK = ?" 
					+ "where PRD_NotificationPK = ? ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, notification.getPrdSetupFK());
				preparedStatement.setTimestamp(2, notification.getDateSent());
				preparedStatement.setDate(3, notification.getDueDate());
				preparedStatement.setString(4, notification.getEmailTo());
				preparedStatement.setString(5, notification.getEmailCC());
				preparedStatement.setString(6, notification.getEmailBCC());
				preparedStatement.setString(7, notification.getFtpAddress());
				preparedStatement.setString(8, notification.getMessageSubject());
				preparedStatement.setString(9, notification.getMessageText());
				preparedStatement.setBytes(10, notification.getFileAttachment());
				if (notification.getFileAttachmentName() != null) {
				    preparedStatement.setString(11, notification.getFileAttachmentName().trim());
				} else {
				    preparedStatement.setString(11, notification.getFileAttachmentName());
				}
				preparedStatement.setBoolean(12, notification.getHasAttachment());
				preparedStatement.setString(13, notification.getStatusCT());
				preparedStatement.setString(14, notification.getFtpUsername());
				preparedStatement.setString(15, notification.getFtpPassword());
				preparedStatement.setString(16, notification.getExportDirectory());
				preparedStatement.setLong(17, notification.getNotificationPK());
				preparedStatement.setLong(18, notification.getDataHeaderFK());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return notification;
	}

	public Notification createNotification(Notification notification) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_Notification (PRD_SetupFK, DateSent, DueDate, EmailTo, EmailCC, EmailBCC, " 
					+ "FTPAddress, MessageSubject, MessageText, FileAttachment, FileAttachmentName, HasAttachment, "
					+ "StatusCT, FTPUsername, FTPPassword, ExportDirectory, PRD_DataHeaderFK) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1, notification.getPrdSetupFK());
				preparedStatement.setTimestamp(2, notification.getDateSent());
				preparedStatement.setDate(3, notification.getDueDate());
				preparedStatement.setString(4, notification.getEmailTo());
				preparedStatement.setString(5, notification.getEmailCC());
				preparedStatement.setString(6, notification.getEmailBCC());
				preparedStatement.setString(7, notification.getFtpAddress());
				preparedStatement.setString(8, notification.getMessageSubject());
				preparedStatement.setString(9, notification.getMessageText());
				preparedStatement.setBytes(10, notification.getFileAttachment());
				if (notification.getFileAttachmentName() != null) {
				    preparedStatement.setString(11, notification.getFileAttachmentName().trim());
				} else {
				    preparedStatement.setString(11, notification.getFileAttachmentName());
				}
				preparedStatement.setBoolean(12, notification.getHasAttachment());
				preparedStatement.setString(13, notification.getStatusCT());
				preparedStatement.setString(14, notification.getFtpUsername());
				preparedStatement.setString(15, notification.getFtpPassword());
				preparedStatement.setString(16, notification.getExportDirectory());
				preparedStatement.setLong(17, notification.getDataHeaderFK());

/*				preparedStatement.setDate(8, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
				preparedStatement.setDate(9, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
				preparedStatement.setString(10, "");
				preparedStatement.setString(11, "");
				preparedStatement.setLong(12, notification.getPrdSetupFK());
				preparedStatement.setBytes(13, notification.getFileAttachment());;
*/

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	notification.setNotificationPK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new DAOException("Creating notification failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return notification;
	}
	
	public void deleteNotifications(List<Notification> notifications) throws DAOException {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_Notification " + " where PRD_NotificationPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			Iterator<Notification> it = notifications.iterator();
			while (it.hasNext()) {
				Notification notification = it.next();
				preparedStatement.setLong(1, notification.getNotificationPK());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}
	


}
