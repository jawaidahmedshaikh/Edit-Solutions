package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.NotificationService;
import com.editsolutions.prd.service.NotificationServiceImpl;
import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.MessageTemplate;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SetupContact;

import edit.services.db.hibernate.SessionHelper;

public class SetupContactDao extends Dao<SetupContact> {

	public SetupContactDao() {
		super(SetupContact.class);
	}

	public List<SetupContact> getSetupContacts(PRDSettings prdSettings)
			throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);

		Criteria c = session.createCriteria(SetupContact.class);
		c.add(Restrictions.eq("setupFK", prdSettings.getPrdSetupPK()));
		@SuppressWarnings("unchecked")
		List<SetupContact> setupContacts = findAll(c);
		return setupContacts;
	}

	public SetupContact createSetupContact(SetupContact setupContact)
			throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "insert into PRD_SetupContact"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, setupContact.getSetupFK());
			preparedStatement.setString(2, setupContact.getName());
			preparedStatement.setString(3, setupContact.getEmail());
			preparedStatement.setString(4, setupContact.getRecipientTypeCT());
			preparedStatement.setDate(5, null);
			preparedStatement.setString(6, null);
			preparedStatement.setDate(7, null);
			preparedStatement.setString(8, null);

			preparedStatement.executeUpdate();
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					setupContact.setSetupContactPK(generatedKeys.getLong(1));
				} else {
					throw new SQLException(
							"Creating setupContact failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		updateNotificationContacts(setupContact);
		return setupContact;
	}

	public SetupContact updateSetupContact(SetupContact setupContact)
			throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "update PRD_SetupContact"
				+ " set ContactName = ?, ContactEmail = ?, RecipientTypeCT = ?  "
				+ " where PRD_SetupContactPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, setupContact.getName());
			preparedStatement.setString(2, setupContact.getEmail());
			preparedStatement.setString(3, setupContact.getRecipientTypeCT());
			preparedStatement.setLong(4, setupContact.getSetupContactPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		updateNotificationContacts(setupContact);
		return setupContact;
	}

	private void updateNotificationContacts(SetupContact setupContact) {
		PRDSettingsService prdService = new PRDSettingsServiceImpl();
		PRDSettings prdSettings = prdService.getPRDSettings(setupContact
				.getSetupFK());
		NotificationService nService = new NotificationServiceImpl();
		List<Notification> notifications = nService.getPendingNotifications(prdSettings.getPrdSetupPK());

		Iterator<Notification> notIterator = notifications.iterator();
		while (notIterator.hasNext()) {
			Notification notification = notIterator.next();

			List<SetupContact> setupContacts = getSetupContacts(prdSettings);
			Iterator<SetupContact> it = setupContacts.iterator();
			notification.setEmailTo(null);
			notification.setEmailCC(null);
			notification.setEmailBCC(null);
			while (it.hasNext()) {
				SetupContact sc = it.next();
				switch (sc.getRecipientTypeCT().toUpperCase()) {
				case "TO":
					if (notification.getEmailTo() != null) {
						notification.setEmailTo(notification.getEmailTo()
								+ "; " + sc.getEmail());
					} else {
						notification.setEmailTo(sc.getEmail());
					}
					break;
				case "CC":
					if (notification.getEmailCC() != null) {
						notification.setEmailCC(notification.getEmailCC()
								+ "; " + sc.getEmail());
					} else {
						notification.setEmailCC(sc.getEmail());
					}
					break;
				case "BCC":
					if (notification.getEmailBCC() != null) {
						notification.setEmailBCC(notification.getEmailBCC()
								+ "; " + sc.getEmail());
					} else {
						notification.setEmailBCC(sc.getEmail());
					}
					break;
				}
			}
			nService.saveNotification(notification);
		}
	}

	public void deleteSetupContact(SetupContact setupContact)
			throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_SetupContact"
				+ " where PRD_SetupContactPK = ?";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setLong(1, setupContact.getSetupContactPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		updateNotificationContacts(setupContact);
	}

}
