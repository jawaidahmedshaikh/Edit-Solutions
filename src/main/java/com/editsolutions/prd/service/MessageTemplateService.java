package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.MessageTemplate;;

public interface MessageTemplateService {
	
	public MessageTemplate saveMessageTemplate(MessageTemplate messageTempate) throws DAOException;
	public void deleteMessageTemplate(MessageTemplate messageTempate) throws DAOException;
	public List<MessageTemplate> getSubjectTemplates() throws DAOException;
	public List<MessageTemplate> getMessageTemplates() throws DAOException;
	public List<MessageTemplate> getAllTemplates() throws DAOException;
	public MessageTemplate getTemplate(Long messageTemplatePK) throws DAOException;

}
