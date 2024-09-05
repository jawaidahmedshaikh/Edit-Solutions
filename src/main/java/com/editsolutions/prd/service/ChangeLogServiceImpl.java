package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.dao.ChangeLogDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.ChangeLog;

public class ChangeLogServiceImpl {
	private ChangeLogDao changeLogDao;

	public ChangeLogServiceImpl() {
		super();
		changeLogDao = new ChangeLogDao();
	}

	public List<ChangeLog> getChangeLogs(String pkSetupPK) throws DAOException {
		List<ChangeLog> list = changeLogDao.getPayrollDeductionDataList(pkSetupPK);
		return list;
	}

}
