package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.FileTemplateFieldService;
import com.editsolutions.prd.service.FileTemplateFieldServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.FileTemplateField;

import edit.services.db.hibernate.SessionHelper;
import electric.util.holder.intInOut;

public class FileTemplateDao extends Dao<FileTemplate> {

	public FileTemplateDao() {
        super(FileTemplate.class);
    }
	
	public FileTemplate createFileTemplate(FileTemplate fileTemplate) throws DAOException{
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_FileTemplate (FileTemplateName, FileTemplateDescription, OutputTypeCT, DelimiterCT, IncludeTitles, FileTemplateTypeCT) " 
					+ " values (?, ?, ?, ?, ?, ?)";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, fileTemplate.getName());
				preparedStatement.setString(2, fileTemplate.getDescription());
				preparedStatement.setString(3, fileTemplate.getOutputTypeCT());
				preparedStatement.setString(4, fileTemplate.getDelimiterCT());
				preparedStatement.setBoolean(5, fileTemplate.isIncludeTitles());
				preparedStatement.setString(6, fileTemplate.getFileTemplateTypeCT());
				//preparedStatement.setDate(5, null);
				//preparedStatement.setString(6, null);
				//preparedStatement.setDate(7, null);
				//preparedStatement.setString(8, null);

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	fileTemplate.setFileTemplatePK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new DAOException("Creating fileTemplate failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				throw new DAOException(e);
			}
		return fileTemplate;
	}
	
	public FileTemplate updateFileTemplate(FileTemplate fileTemplate) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "update PRD_FileTemplate" 
					+ " set FileTemplateName = ?, FileTemplateDescription = ?, OutputTypeCT = ?,  "
					+ " DelimiterCT = ?, IncludeTitles = ?, FileTemplateTypeCT = ? "
					+ " where PRD_FileTemplatePK = ?";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, fileTemplate.getName());
				preparedStatement.setString(2, fileTemplate.getDescription());
				preparedStatement.setString(3, fileTemplate.getOutputTypeCT());
				preparedStatement.setString(4, fileTemplate.getDelimiterCT());
				preparedStatement.setBoolean(5, fileTemplate.isIncludeTitles());
				preparedStatement.setString(6, fileTemplate.getFileTemplateTypeCT());
				preparedStatement.setLong(7, fileTemplate.getFileTemplatePK());

				preparedStatement.executeUpdate();
                
				List<FileTemplateField> fileTemplateFields = fileTemplate.getFileTemplateFields();
				Iterator<FileTemplateField> it = fileTemplateFields.iterator();
				FileTemplateFieldService fileTemplateFieldService = new FileTemplateFieldServiceImpl();
				//fileTemplateFieldService.deleteFileTemplateFields(fileTemplate.getFileTemplatePK());
				//int fieldOrder = 0;
				while (it.hasNext()) {
					FileTemplateField field = it.next();
				//	field.setFileTemplateFieldPK(null);
					if (field.getFileTemplateFK() == null) {
					    field.setFileTemplateFK(fileTemplate.getFileTemplatePK());
					}
					if (field.getSourceFieldFK() == null) {
					    field.setSourceFieldFK(field.getSourceField().getSourceFieldPK());
					}
				//	fieldOrder++;
				//	field.setFieldOrder(fieldOrder);
				    fileTemplateFieldService.saveFileTemplateField(field);	
				}
			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return fileTemplate;
	}
	
	public void deleteFileTemplate(FileTemplate fileTemplate) throws DAOException{
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		PreparedStatement preparedStatement;
		try {
			FileTemplateFieldService fileTemplateFieldService = new FileTemplateFieldServiceImpl();
			fileTemplateFieldService.deleteFileTemplateFields(fileTemplate.getFileTemplatePK());
		    //if ((fileTemplate.getFileTemplateFields() != null) && (fileTemplate.getFileTemplateFields().size() > 0)) {
/*		        String fieldSql = "delete PRD_FileTemplateField" 
				    + " where PRD_FileTemplateFK = ?";
			    preparedStatement = connection.prepareStatement(fieldSql);
			    preparedStatement.setLong(1, fileTemplate.getFileTemplatePK());
			    preparedStatement.executeUpdate();
			    */
		   // }
		    String sql = "delete PRD_FileTemplate" 
				       + " where PRD_FileTemplatePK = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, fileTemplate.getFileTemplatePK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
    }	
	
	public FileTemplate getTemplate(Long fileTemplatePK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(FileTemplate.class);
		cr.add(Restrictions.eq("fileTemplatePK", fileTemplatePK));
		return (FileTemplate) cr.uniqueResult();
	}

	public List<FileTemplate> getHeaderTemplates() throws DAOException {
		return getFileTemplates("Header");
	}

	public List<FileTemplate> getFileTemplates() throws DAOException {
		return getFileTemplates("Records");
	}

	public List<FileTemplate> getFooterTemplates() throws DAOException {
		return getFileTemplates("Footer");
	}


	public List<FileTemplate> getFileTemplates(String fileTemplateType) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(FileTemplate.class);
		cr.add(Restrictions.eq("fileTemplateTypeCT", fileTemplateType));
		cr.addOrder(Order.asc("name"));
		@SuppressWarnings("unchecked")
		List<FileTemplate> list = cr.list();
		return list;
	}

}
