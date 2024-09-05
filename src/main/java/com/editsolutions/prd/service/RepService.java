package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Rep;

public interface RepService {
	
	public List<Rep> getRepList()  throws DAOException;

}
