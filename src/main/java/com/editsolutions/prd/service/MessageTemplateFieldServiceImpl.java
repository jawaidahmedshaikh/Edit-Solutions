package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.MessageTemplateFieldDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.MessageTemplateField;

public class MessageTemplateFieldServiceImpl implements MessageTemplateFieldService {
	private MessageTemplateFieldDao messageTemplateFieldDao;

	public MessageTemplateFieldServiceImpl() {
		super();
		messageTemplateFieldDao = new MessageTemplateFieldDao();
	}

	@Override
	public List<MessageTemplateField> getAllMessageTemplateFields() throws DAOException {
		List<MessageTemplateField> list = messageTemplateFieldDao.getAllMessageTemplatesField();
		return list;
	}

	@Override
	public MessageTemplateField getMessageTemplateField(String tableName, String fieldName) throws DAOException {
		return messageTemplateFieldDao.getMessageTemplatesField(tableName, fieldName);
	}
	
	@Override
	public MessageTemplateField saveMessageTemplateField(MessageTemplateField messageTemplateField) throws DAOException {
	    if (messageTemplateField.getMessageTemplateFieldPK() == null) {
	        return messageTemplateFieldDao.createMessageTemplateField(messageTemplateField);
	    } else {
	        return messageTemplateFieldDao.updateMessageTemplateField(messageTemplateField);
	    }
		
	}

	@Override
	public void deleteMessageTemplateField(MessageTemplateField messageTemplateField) throws DAOException {
        messageTemplateFieldDao.delete(messageTemplateField);
	}

}
