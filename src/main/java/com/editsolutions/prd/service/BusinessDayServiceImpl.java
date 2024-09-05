package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.BusinessDayDao;
import com.editsolutions.prd.dao.PRDSettingsDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.BusinessDay;

public class BusinessDayServiceImpl implements BusinessDayService {
	private BusinessDayDao businessDayDao;
	
	public BusinessDayServiceImpl() {
		super();
		businessDayDao = new BusinessDayDao();
	}

	public List<BusinessDay> getBusinessDays(String year) throws DAOException {
		return businessDayDao.getBusinessDays(year);
	}

}