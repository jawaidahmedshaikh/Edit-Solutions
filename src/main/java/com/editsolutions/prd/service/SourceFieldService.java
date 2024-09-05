package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.SourceField;

public interface SourceFieldService {
	
	public List<SourceField> getSourceFields(String fileTemplateTypeCT) throws DAOException;

}
