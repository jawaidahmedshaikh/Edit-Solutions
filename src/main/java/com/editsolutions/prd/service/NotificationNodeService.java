package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.node.NotificationNode;

public interface NotificationNodeService {
	
	public List<NotificationNode> getNotificationNodes(Long prdSetupPK) throws DAOException;

}
