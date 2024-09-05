package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.MessageTemplateField;

public interface MessageTemplateFieldService {
	
	public List<MessageTemplateField> getAllMessageTemplateFields()  throws DAOException;
	public MessageTemplateField getMessageTemplateField(String tableName, String fieldName) throws DAOException;
	public MessageTemplateField saveMessageTemplateField(MessageTemplateField messageTemplateField) throws DAOException;
	public void deleteMessageTemplateField(MessageTemplateField messageTemplateField) throws DAOException;

}
