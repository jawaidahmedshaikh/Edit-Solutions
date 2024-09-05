package com.editsolutions.prd.service;


import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.dao.NotificationDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.PRDSettings;

public class NotificationServiceImpl implements NotificationService {
	private NotificationDao notificationDao;

	public NotificationServiceImpl() {
		super();
		notificationDao = new NotificationDao();
	}
	
	public Notification get(Serializable id) throws DAOException {
		return notificationDao.get(id);
	}

	public List<Notification> getPendingNotifications(Serializable id) throws DAOException {
        return notificationDao.getPendingNotifications(id);	
	}
	
	public Notification getNotification(Serializable id) throws DAOException {
        return notificationDao.getNotification(id);	
	}

	public List<Notification> getNotifications(PRDSettings prdSettings) throws DAOException {
		return notificationDao.getNotifications(prdSettings);
	}
	
	public void deleteNotifications(List<Notification> notifications) throws DAOException {
	    notificationDao.deleteNotifications(notifications);	
	}

	public Notification saveNotification(Notification notification) throws DAOException {
		if (notification.getNotificationPK() == null) {
		    return notificationDao.createNotification(notification);
		} else {
		    return notificationDao.updateNotification(notification);
		}
	}
	public Notification updateNotification(Notification notification) throws DAOException  {
		return notificationDao.updateNotification(notification);
	}



}
