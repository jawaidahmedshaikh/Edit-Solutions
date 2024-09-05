package com.editsolutions.prd.service;



import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Message;

public interface MessageService {
	
	public Message buildMessage(Message message, String groupNumber)  throws DAOException;

}
