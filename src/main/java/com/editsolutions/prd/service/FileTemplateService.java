package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.FileTemplate;;

public interface FileTemplateService {
	
	public FileTemplate saveFileTemplate(FileTemplate fileTempate) throws DAOException;
	public void deleteFileTemplate(FileTemplate fileTempate) throws DAOException;
	public List<FileTemplate> getFileTemplates() throws DAOException;
	public List<FileTemplate> getFileTemplates(String fileTemplateTypeCT) throws DAOException;
	public List<FileTemplate> getHeaderTemplates() throws DAOException;
	public List<FileTemplate> getFooterTemplates() throws DAOException;
	public FileTemplate getTemplate(Long fileTemplatePK) throws DAOException;

}
