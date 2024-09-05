package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.dao.RepDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Rep;

public class RepServiceImpl {
	private RepDao repDao;

	public RepServiceImpl() {
		super();
		repDao = new RepDao();
	}

	public List<Rep> getReps() throws DAOException {
		List<Rep> list = repDao.getRepList();
		return list;
	}

}
