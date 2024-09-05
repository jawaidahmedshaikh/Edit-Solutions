package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.MessageTemplateField;
import com.editsolutions.prd.vo.Transformation;

public interface TransformationService {
	
	public List<Transformation> getAllTransformations() throws DAOException;
	public String testSectionTransformation(Transformation transformation, String value) throws DAOException;
	public String testTransformation(MessageTemplateField messageTemplateField, Transformation transformation, String groupNumber) throws DAOException;
	public Transformation saveTransformation(Transformation transformation) throws DAOException;

}
