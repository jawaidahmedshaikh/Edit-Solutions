package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.FileTemplateDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.FileTemplate;

public class FileTemplateServiceImpl implements FileTemplateService {
	private FileTemplateDao fileTemplateDao;

	public FileTemplateServiceImpl() {
		super();
		fileTemplateDao = new FileTemplateDao();
	}

	public FileTemplate updateFileTemplate(FileTemplate fileTemplate) throws DAOException {
		    return fileTemplateDao.updateFileTemplate(fileTemplate);
	}

	public FileTemplate saveFileTemplate(FileTemplate fileTemplate) throws DAOException {
		if (fileTemplate.getFileTemplatePK() == null) {
		    return fileTemplateDao.createFileTemplate(fileTemplate);
		} else {
		    return fileTemplateDao.updateFileTemplate(fileTemplate);
		}
	}
	public void deleteFileTemplate(FileTemplate fileTemplate) throws DAOException {
        fileTemplateDao.deleteFileTemplate(fileTemplate);
	}
	
	public List <FileTemplate> getFileTemplates(String fileTemplateTypeCT) throws DAOException {
		return fileTemplateDao.getFileTemplates(fileTemplateTypeCT);
	}
	
	public FileTemplate getTemplate(Long fileTemplatePK) throws DAOException {
		return fileTemplateDao.getTemplate(fileTemplatePK);
	}
	
	public List<FileTemplate> getHeaderTemplates() throws DAOException {
		return fileTemplateDao.getHeaderTemplates();
	}

	public List<FileTemplate> getFileTemplates() throws DAOException {
		return fileTemplateDao.getFileTemplates();
	}

	public List<FileTemplate> getFooterTemplates() throws DAOException {
		return fileTemplateDao.getFooterTemplates();
	}

}
