package com.editsolutions.prd.service;


import java.io.Serializable;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Notification;
import com.editsolutions.prd.vo.PRDSettings;

public interface NotificationService {
	
	public Notification get(Serializable id) throws DAOException;
	public Notification getNotification(Serializable id) throws DAOException;
	public List<Notification> getPendingNotifications(Serializable id) throws DAOException;
	public Notification saveNotification(Notification Notification) throws DAOException;
	public Notification updateNotification(Notification notification) throws DAOException; 
	public List<Notification> getNotifications(PRDSettings prdSettings) throws DAOException;
	public void deleteNotifications(List<Notification> notifications) throws DAOException; 

}
