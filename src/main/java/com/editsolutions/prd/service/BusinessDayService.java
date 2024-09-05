package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.BusinessDay;

public interface BusinessDayService {
	
	public List<BusinessDay> getBusinessDays(String year) throws DAOException;

}
