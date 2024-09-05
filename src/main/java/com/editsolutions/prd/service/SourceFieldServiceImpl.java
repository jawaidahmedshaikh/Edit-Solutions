package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.SourceFieldDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.SourceField;
import com.editsolutions.prd.vo.PRDSettings;

public class SourceFieldServiceImpl implements SourceFieldService {
	private SourceFieldDao sourceFieldDao;

	public SourceFieldServiceImpl() {
		super();
		sourceFieldDao = new SourceFieldDao();
	}

	public List<SourceField> getSourceFields(String fileTemplateTypeCT) throws DAOException {
		return sourceFieldDao.getSourceFields(fileTemplateTypeCT);

	}


}
