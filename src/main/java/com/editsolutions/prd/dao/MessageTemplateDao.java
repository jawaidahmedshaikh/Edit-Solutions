package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.MessageTemplate;

import edit.services.db.hibernate.SessionHelper;

public class MessageTemplateDao extends Dao<MessageTemplate> {

	public MessageTemplateDao() {
        super(MessageTemplate.class);
    }
	
	public MessageTemplate createMessageTemplate(MessageTemplate messageTemplate) throws DAOException{
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_MessageTemplate" 
					+ " values (?, ?, ?, ?, ?, ?, ?) ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, messageTemplate.getMessageTypeCT());
				preparedStatement.setString(2, messageTemplate.getName());
				preparedStatement.setString(3, messageTemplate.getMessageText());
				preparedStatement.setDate(4, null);
				preparedStatement.setString(5, null);
				preparedStatement.setDate(6, null);
				preparedStatement.setString(7, null);

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	messageTemplate.setMessageTemplatePK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new DAOException("Creating messageTemplate failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return messageTemplate;
	}
	
	public MessageTemplate updateMessageTemplate(MessageTemplate messageTemplate) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "update PRD_MessageTemplate" 
					+ " set MessageTypeCT = ?, MessageName = ?, MessageText = ?  "
					+ " where PRD_MessageTemplatePK = ?";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, messageTemplate.getMessageTypeCT());
				preparedStatement.setString(2, messageTemplate.getName());
				preparedStatement.setString(3, messageTemplate.getMessageText());
				preparedStatement.setLong(4, messageTemplate.getMessageTemplatePK());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return messageTemplate;
	}
	
	public void deleteMessageTemplate(MessageTemplate messageTemplate) throws DAOException{
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_MessageTemplate" 
				+ " where PRD_MessageTemplatePK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, messageTemplate.getMessageTemplatePK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
    }	
	
	public MessageTemplate getTemplate(Long messageTemplatePK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(MessageTemplate.class);
		cr.add(Restrictions.eq("messageTemplatePK", messageTemplatePK));
		return (MessageTemplate) cr.uniqueResult();
	}

	public List<MessageTemplate> getTemplates(String templateType) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(MessageTemplate.class);
		cr.add(Restrictions.eq("messageTypeCT", templateType));
		@SuppressWarnings("unchecked")
		List<MessageTemplate> list = cr.list();
		return list;
	}

	public List<MessageTemplate> getSubjectTemplates() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(MessageTemplate.class);
		cr.add(Restrictions.eq("messageTypeCT", "Subject"));
		@SuppressWarnings("unchecked")
		List<MessageTemplate> list = cr.list();
		return list;
	}

	public List<MessageTemplate> getMessageTemplates() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		Criteria cr = session.createCriteria(MessageTemplate.class);
		cr.add(Restrictions.eq("messageTypeCT", "Message"));
		@SuppressWarnings("unchecked")
		List<MessageTemplate> list = cr.list();
		return list;
	}

	public List<MessageTemplate> getAllTemplates() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		Criteria cr = session.createCriteria(MessageTemplate.class);
		@SuppressWarnings("unchecked")
		List<MessageTemplate> list = cr.list();
		return list;
	}

			
}
