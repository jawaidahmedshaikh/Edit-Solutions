package com.editsolutions.prd.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.settings.Settings;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.editsolutions.prd.service.AppSettingService;
import com.editsolutions.prd.service.AppSettingServiceImpl;
import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.service.NotificationService;
import com.editsolutions.prd.service.NotificationServiceImpl;
import com.editsolutions.prd.service.SetupContactService;
import com.editsolutions.prd.service.SetupContactServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.util.PRDFileUtils;
import com.editsolutions.prd.vo.AppSetting;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SetupContact;
import com.editsolutions.prd.node.NotificationNode;

import edit.services.db.hibernate.SessionHelper;

public class NotificationNodeDao extends Dao<NotificationNode> {

	public NotificationNodeDao() {
		super(NotificationNode.class);
	}

	public void unreleaseNotification(NotificationNode notificationNode)
			throws DAOException {
		NotificationService notificationService = new NotificationServiceImpl();
		Notification notification = notificationService.get(notificationNode.getNotificationPK());
		List<Notification> notificationsToDelete = new ArrayList<>();
		notificationsToDelete.add(notification);
		notificationService.deleteNotifications(notificationsToDelete);
		//notification.setStatusCT("V");
		//notificationService.saveNotification(notification);
		DataHeaderService dataHeaderService = new DataHeaderServiceImpl();
		DataHeader dataHeader = dataHeaderService.getDataHeader(notification.getDataHeaderFK());
		dataHeader.setStatusCT("I");
		dataHeaderService.updateDataHeader(dataHeader);
	}

	public void resendNotification(NotificationNode notificationNode)
			throws DAOException {
		NotificationService notificationService = new NotificationServiceImpl();
		Notification notification = notificationService.get(notificationNode
				.getNotificationPK());

		// Get a fresh set of contacts in case a new one was added through UI
		SetupContactService scs = new SetupContactServiceImpl();
		PRDSettings prdSettings = new PRDSettings();
		prdSettings.setPrdSetupPK(notificationNode.getPrdSetupFK());
		List<SetupContact> setupContacts = scs.getSetupContacts(prdSettings);
		Iterator<SetupContact> it = setupContacts.iterator();
		notification.setEmailTo(null);
		notification.setEmailCC(null);
		notification.setEmailBCC(null);
		while (it.hasNext()) {
			SetupContact setupContact = it.next();
			switch (setupContact.getRecipientTypeCT().toUpperCase()) {
			case "TO":
				if (notification.getEmailTo() != null) {
					notification.setEmailTo(notification.getEmailTo() + "; "
							+ setupContact.getEmail());
				} else {
					notification.setEmailTo(setupContact.getEmail());
				}
				break;
			case "CC":
				if (notification.getEmailCC() != null) {
					notification.setEmailCC(notification.getEmailCC() + "; "
							+ setupContact.getEmail());
				} else {
					notification.setEmailCC(setupContact.getEmail());
				}
				break;
			case "BCC":
				if (notification.getEmailBCC() != null) {
					notification.setEmailBCC(notification.getEmailBCC() + "; "
							+ setupContact.getEmail());
				} else {
					notification.setEmailBCC(setupContact.getEmail());
				}
				break;
			}
		}

		notification.setStatusCT("P");
		notification.setDateSent(new java.sql.Timestamp(System.currentTimeMillis()));
		notification
				.setOriginalNotificationPK(notification.getNotificationPK());
		notification.setNotificationPK(null);
		notificationService.saveNotification(notification);
		AppSettingService appSettingsService = new AppSettingServiceImpl();
		HashMap<String, String> asHashMap = appSettingsService.getAppSettingsHash();
		File file = new File(asHashMap.get("PRD_TRIGGERS_DIR") + "\\" + asHashMap.get("PRD_SEND_TRIGGER_FILE"));
		StringBuilder messageSB = new StringBuilder();
		messageSB.append(notification.getEmailTo());
		messageSB.append("\n");
		messageSB.append(notification.getFileAttachmentName());
		try {
			PRDFileUtils.createTriggerFile(file, messageSB.toString());
		} catch (IOException e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<NotificationNode> getNotificationNodes(Long prdSetupPK)
			throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session
				.createSQLQuery("select * from PRD_Notification where PRD_SetupFK = "
						+ Long.toString(prdSetupPK) + " order by DueDate DESC ");
		query.addEntity(NotificationNode.class);
		List<NotificationNode> notifications = query.list();
		session.close();
		return notifications;
	}

}
