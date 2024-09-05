package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.FileTemplateFieldDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.FileTemplateField;

public class FileTemplateFieldServiceImpl implements FileTemplateFieldService {
	private FileTemplateFieldDao fileTemplateFieldDao;

	public FileTemplateFieldServiceImpl() {
		super();
		fileTemplateFieldDao = new FileTemplateFieldDao();
	}
	
	public List<FileTemplateField> getFileTemplateFieldTemplates(String fileTemplateTypeCT) throws DAOException {
		return fileTemplateFieldDao.getFileTemplateFieldTemplates(fileTemplateTypeCT);
	}

	public List<FileTemplateField> saveFileTemplateFields(List<FileTemplateField> fileTemplateFields) throws DAOException {
		return fileTemplateFieldDao.saveFileTemplateFields(fileTemplateFields);

	}

	public FileTemplateField saveFileTemplateField(FileTemplateField fileTemplateField) throws DAOException {
		if (fileTemplateField.getFileTemplateFieldPK() == null) {
		    return fileTemplateFieldDao.createFileTemplateField(fileTemplateField);
		} else {
		    return fileTemplateFieldDao.updateFileTemplateField(fileTemplateField);
		}
	}

	public void deleteFileTemplateField(FileTemplateField fileTemplateField) throws DAOException {
        fileTemplateFieldDao.deleteFileTemplateField(fileTemplateField);
	}

	public void deleteFileTemplateFields(Long fileTemplateFK) throws DAOException {
        fileTemplateFieldDao.deleteFileTemplateFields(fileTemplateFK);
	}
	
	public List <FileTemplateField> getFileTemplateFields() throws DAOException {
		return fileTemplateFieldDao.getFileTemplateFields();
	}

	
	public FileTemplateField getFileTemplateField(Long fileTemplateFieldPK) throws DAOException {
		return fileTemplateFieldDao.getFileTemplateField(fileTemplateFieldPK);
	}

}
