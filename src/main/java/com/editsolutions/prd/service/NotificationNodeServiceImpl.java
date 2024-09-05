package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.NotificationNodeDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.node.NotificationNode;

public class NotificationNodeServiceImpl implements NotificationNodeService {
	private NotificationNodeDao notificationNodeDao;

	public NotificationNodeServiceImpl() {
		super();
		notificationNodeDao = new NotificationNodeDao();
	}

	
	public void resendNotification(NotificationNode notificationNode) throws DAOException {
		notificationNodeDao.resendNotification(notificationNode);
	}

	public void unreleaseNotification(NotificationNode notificationNode) throws DAOException {
		notificationNodeDao.unreleaseNotification(notificationNode);
	}

	public List<NotificationNode> getNotificationNodes(Long prdSetupPK) throws DAOException {
		return notificationNodeDao.getNotificationNodes(prdSetupPK);
	}

}
