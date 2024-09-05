package com.editsolutions.prd.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.MessageTemplate;
import com.editsolutions.prd.vo.MessageTemplateField;

import edit.services.db.hibernate.SessionHelper;

public class MessageTemplateFieldDao extends Dao<MessageTemplateField> {

	public MessageTemplateFieldDao() {
		super(MessageTemplateField.class);
	}

	public List<MessageTemplateField> getAllMessageTemplatesField() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		Criteria c = session.createCriteria(MessageTemplateField.class);
		c.addOrder( Order.asc("fieldName"));
		return findAll(c);
	}

	public MessageTemplateField getMessageTemplatesField(String tableName, String fieldName)  throws DAOException  {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(MessageTemplateField.class);
		cr.add(Restrictions.eq("tableName", tableName));
		cr.add(Restrictions.eq("fieldName", fieldName));
		return (MessageTemplateField)cr.uniqueResult();

	}

	public MessageTemplateField createMessageTemplateField(MessageTemplateField messageTemplateField) throws DAOException  {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_MessageTemplateField" 
				+ " values (?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, messageTemplateField.getTableName());
			preparedStatement.setString(2, messageTemplateField.getFieldName());
			preparedStatement.setString(3, messageTemplateField.getFieldTitle());
			preparedStatement.setLong(4, messageTemplateField.getTransformationFK());
			preparedStatement.setDate(5, null);
			preparedStatement.setString(6, null);
			preparedStatement.setDate(7, null);
			preparedStatement.setString(8, null);

			preparedStatement.executeUpdate();
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					messageTemplateField.setMessageTemplateFieldPK(generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Creating messageTemplateField failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return messageTemplateField;
	}

	public MessageTemplateField updateMessageTemplateField(MessageTemplateField messageTemplateField) throws DAOException  {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_MessageTemplateField" 
				+ " set TableName = ?, FieldName = ?, FieldTitle = ?, PRD_TransformationFK = ?  "
				+ " where PRD_MessageTemplateFieldPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, messageTemplateField.getTableName());
			preparedStatement.setString(2, messageTemplateField.getFieldName());
			preparedStatement.setString(3, messageTemplateField.getFieldTitle());
			if (messageTemplateField.getTransformationFK() != null) {
			    preparedStatement.setLong(4, messageTemplateField.getTransformationFK());
			} else {
			    preparedStatement.setObject(4, null);
			}
			preparedStatement.setLong(5, messageTemplateField.getMessageTemplateFieldPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return messageTemplateField;
	}

	public void deleteMessageTemplateField(MessageTemplateField messageTemplateField)  throws DAOException  {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_MessageTemplateField" 
				+ " where PRD_MessageTemplateFieldPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, messageTemplateField.getMessageTemplateFieldPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
			
}
