package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.FileTemplateField;

public interface FileTemplateFieldService {
	
	public List<FileTemplateField> getFileTemplateFieldTemplates(String fileTemplateTypeCT) throws DAOException;
	public FileTemplateField saveFileTemplateField(FileTemplateField fileTemplateField) throws DAOException;
	public void deleteFileTemplateField(FileTemplateField fileTempateField) throws DAOException;
	public void deleteFileTemplateFields(Long fileTemplateFK) throws DAOException;
	public List<FileTemplateField> getFileTemplateFields() throws DAOException;
	public FileTemplateField getFileTemplateField(Long fileTemplateFieldPK) throws DAOException;

}
