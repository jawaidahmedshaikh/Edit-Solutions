package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.TransformationDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.util.MessageBuilderFactory;
import com.editsolutions.prd.vo.MessageTemplateField;
import com.editsolutions.prd.vo.Transformation;

public class TransformationServiceImpl implements TransformationService {
	private TransformationDao transformationDao;

	public TransformationServiceImpl() {
		super();
		transformationDao = new TransformationDao();
	}

	@Override
	public List<Transformation> getAllTransformations() throws DAOException {
		List<Transformation> list = transformationDao.findAll();
		return list;
	}

	@Override
	public String testTransformation(MessageTemplateField messageTemplateField,
			Transformation transformation, String groupNumber) throws DAOException {
	    return MessageBuilderFactory.testTransformation(messageTemplateField, transformation, groupNumber);	
	}
	
	public Transformation saveTransformation(Transformation transformation) throws DAOException {
		return transformationDao.save(transformation);
	}

	@Override
	public String testSectionTransformation(Transformation transformation, String value) throws DAOException {
	    return MessageBuilderFactory.testSectionTransformation(transformation, value);	
	}

}
