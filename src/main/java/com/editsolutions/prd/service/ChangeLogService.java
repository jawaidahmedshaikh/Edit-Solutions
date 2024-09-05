package com.editsolutions.prd.service;

import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.ChangeLog;;

public interface ChangeLogService {
	
	public List<ChangeLog> getChangeLogs(String prdSetupPK)  throws DAOException;

}
