package com.editsolutions.prd.service;


import com.editsolutions.prd.vo.Message;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.util.MessageBuilderFactory;


public class MessageServiceImpl implements MessageService {

	public MessageServiceImpl() {
		super();
	}

	@Override
	public Message buildMessage(Message message, String groupNumber) throws DAOException {
		return MessageBuilderFactory.buildMessage(message, groupNumber);
	}

}
