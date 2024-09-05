package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;
import com.editsolutions.prd.vo.SourceField;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.PayrollDeductionData;
import com.editsolutions.prd.vo.SetupContact;

import edit.services.db.hibernate.SessionHelper;
import electric.soap.rpc.Return;

public class SourceFieldDao extends Dao<SourceField> {

	public SourceFieldDao() {
        super(SourceField.class);
    }

	
	@SuppressWarnings("unchecked")
	public List<SourceField> getSourceFields(String fileTemplateTypeCT) throws DAOException {
		
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		//SQLQuery query = session
		//		.createSQLQuery("select * from PRD_SourceField ");
        Criteria c = session.createCriteria(SourceField.class);
        c.add(Restrictions.eq("fileTemplateTypeCT", fileTemplateTypeCT));
		List<SourceField> sourceFields = findAll(c);
		session.close();
		return sourceFields;
	}

	public SourceField createSourceField(PRDSettings prdSettings, SourceField sourceField) throws DAOException {
		/*
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_SourceField (PRD_SetupFK, DateSent, DueDate, EmailTo, EmailCC, EmailBCC, " 
					+ "FTPAddress, MessageSubject, MessageText, FileAttachment, FileAttachmentName, HasAttachment, "
					+ "StatusCT) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1, sourceField.getPrdSetupFK());
				preparedStatement.setDate(2, sourceField.getDateSent());
				preparedStatement.setDate(3, sourceField.getDueDate());
				preparedStatement.setString(4, sourceField.getEmailTo());
				preparedStatement.setString(5, sourceField.getEmailCC());
				preparedStatement.setString(6, sourceField.getEmailBCC());
				preparedStatement.setString(7, sourceField.getFtpAddress());
				preparedStatement.setString(8, sourceField.getMessageSubject());
				preparedStatement.setString(9, sourceField.getMessageText());
				preparedStatement.setBytes(10, sourceField.getFileAttachment());
				preparedStatement.setString(11, sourceField.getFileAttachmentName());
				preparedStatement.setBoolean(12, sourceField.getHasAttachment());
				preparedStatement.setString(13, "P");

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	sourceField.setSourceFieldPK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new DAOException("Creating sourceField failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return sourceField;
		*/
		return null;
	}
	

}
