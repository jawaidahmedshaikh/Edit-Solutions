package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.MessageTemplateDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.MessageTemplate;

public class MessageTemplateServiceImpl implements MessageTemplateService {
	private MessageTemplateDao messageTemplateDao;

	public MessageTemplateServiceImpl() {
		super();
		messageTemplateDao = new MessageTemplateDao();
	}

	public MessageTemplate saveMessageTemplate(MessageTemplate messageTemplate) throws DAOException {
		if (messageTemplate.getMessageTemplatePK() == null) {
		    return messageTemplateDao.createMessageTemplate(messageTemplate);
		} else {
		    return messageTemplateDao.updateMessageTemplate(messageTemplate);
		}
	}
	public void deleteMessageTemplate(MessageTemplate messageTemplate) throws DAOException {
        messageTemplateDao.deleteMessageTemplate(messageTemplate);
	}
	
	public List <MessageTemplate> getTemplates(String templateType) throws DAOException {
		return messageTemplateDao.getTemplates(templateType);
	}

	@Override
	public List<MessageTemplate> getSubjectTemplates() throws DAOException {
		return messageTemplateDao.getSubjectTemplates();
	}

	@Override
	public List<MessageTemplate> getMessageTemplates() throws DAOException {
		return messageTemplateDao.getMessageTemplates();
	}

	@Override
	public List<MessageTemplate> getAllTemplates() throws DAOException {
		return messageTemplateDao.getAllTemplates();
	}
	
	
	public MessageTemplate getTemplate(Long messageTemplatePK) throws DAOException {
		return messageTemplateDao.getTemplate(messageTemplatePK);
	}

}
