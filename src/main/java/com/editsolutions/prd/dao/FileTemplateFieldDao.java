package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.SourceFieldService;
import com.editsolutions.prd.service.SourceFieldServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.SourceField;

import edit.services.db.hibernate.SessionHelper;

public class FileTemplateFieldDao extends Dao<FileTemplateField> {

	public FileTemplateFieldDao() {
        super(FileTemplateField.class);
    }
	
	public List<FileTemplateField> getFileTemplateFieldTemplates(String fileTemplateTypeCT) throws DAOException {
		List<FileTemplateField> fileTemplateFields = new ArrayList<>();
		SourceFieldService sourceFieldService = new SourceFieldServiceImpl();
		List<SourceField> sourceFields = sourceFieldService.getSourceFields(fileTemplateTypeCT);
		Iterator<SourceField> it = sourceFields.iterator();
		while (it.hasNext()) {
			SourceField sourceField = it.next();
			FileTemplateField fileTemplateField = new FileTemplateField();
			fileTemplateField.setFieldTitle(sourceField.getFriendlyFieldName());
			fileTemplateField.setSourceFieldFK(sourceField.getSourceFieldPK());
			fileTemplateField.setDoCompare(false); 
			fileTemplateField.setSortBy(false); 
			fileTemplateField.setSourceField(sourceField);
			fileTemplateFields.add(fileTemplateField);
		}
		return fileTemplateFields;
	}
	
	public List<FileTemplateField> saveFileTemplateFields(List<FileTemplateField> fileTemplateFields) throws DAOException {
		Iterator<FileTemplateField> it = fileTemplateFields.iterator();
		while (it.hasNext()) {
		    createFileTemplateField(it.next());	
		}
		return fileTemplateFields;
	}

	public FileTemplateField createFileTemplateField(FileTemplateField fileTemplateField) throws DAOException{
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_FileTemplateField (PRD_FileTemplateFK, PRD_SourceFieldFK, FieldTitle, FieldOrder, " 
					+ "PRD_TransformationFK, FieldLength, PrimaryID, DoCompare, SortBy, DefaultValue) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1, fileTemplateField.getFileTemplateFK());
				preparedStatement.setLong(2, fileTemplateField.getSourceFieldFK());
				preparedStatement.setString(3, fileTemplateField.getFieldTitle());
				preparedStatement.setInt(4, fileTemplateField.getFieldOrder());
				if (fileTemplateField.getTransformationFK() != null) {
				    preparedStatement.setLong(5, fileTemplateField.getTransformationFK());
				} else {
				    preparedStatement.setNull(5, java.sql.Types.BIGINT);
				}
				preparedStatement.setInt(6, fileTemplateField.getFieldLength());
				preparedStatement.setString(7, null);
				preparedStatement.setBoolean(8,fileTemplateField.isDoCompare());
				preparedStatement.setBoolean(9,fileTemplateField.isSortBy());
				preparedStatement.setString(10,fileTemplateField.getDefaultValue());

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	fileTemplateField.setFileTemplateFieldPK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new DAOException("Creating fileTemplateField failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return fileTemplateField;
	}
	
	public FileTemplateField updateFileTemplateField(FileTemplateField fileTemplateField) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "update PRD_FileTemplateField" 
					+ " set PRD_FileTemplateFK = ?, PRD_SourceFieldFK = ?, FieldTitle = ?,  "
					+ " FieldOrder = ?, PRD_TransformationFK = ?, FieldLength = ?,  DoCompare = ?, SortBy = ?, DefaultValue = ? "
					+ " where PRD_FileTemplateFieldPK = ?";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, fileTemplateField.getFileTemplateFK());
				preparedStatement.setLong(2, fileTemplateField.getSourceFieldFK());
				preparedStatement.setString(3, fileTemplateField.getFieldTitle());
				preparedStatement.setInt(4, fileTemplateField.getFieldOrder());
				if (fileTemplateField.getTransformationFK() != null) {
				    preparedStatement.setLong(5, fileTemplateField.getTransformationFK());
				} else {
				    preparedStatement.setNull(5, java.sql.Types.BIGINT);
				}
				preparedStatement.setInt(6, fileTemplateField.getFieldLength());
				preparedStatement.setBoolean(7, fileTemplateField.isDoCompare());
				preparedStatement.setBoolean(8, fileTemplateField.isSortBy());
				preparedStatement.setString(9, fileTemplateField.getDefaultValue());
				preparedStatement.setLong(10, fileTemplateField.getFileTemplateFieldPK());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return fileTemplateField;
	}
	
	public void deleteFileTemplateField(FileTemplateField fileTemplateField) throws DAOException{
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sqlData = "delete PRD_Data " 
		                 + "where PRD_FileTemplateFieldPK = ? ";
		String sql = "delete PRD_FileTemplateField" 
				+ " where PRD_FileTemplateFieldPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlData);
			preparedStatement.setLong(1, fileTemplateField.getFileTemplateFieldPK());

			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, fileTemplateField.getFileTemplateFieldPK());

			preparedStatement.executeUpdate();


		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
    }	

	public void deleteFileTemplateFields(Long fileTemplateFK) throws DAOException{
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_FileTemplateField" 
				+ " where PRD_FileTemplateFK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, fileTemplateFK);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
    }	
	
	
	public FileTemplateField getFileTemplateField(Long fileTemplateFieldPK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(FileTemplateField.class);
		cr.add(Restrictions.eq("fileTemplateFieldPK", fileTemplateFieldPK));
		return (FileTemplateField) cr.uniqueResult();
	}

	public List<FileTemplateField> getFileTemplateFields() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(FileTemplateField.class);
		@SuppressWarnings("unchecked")
		List<FileTemplateField> list = cr.list();
		return list;
	}

}
