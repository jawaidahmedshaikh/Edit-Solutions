package com.editsolutions.prd.service;

import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.dao.PRDStatisticsDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.GroupSetup;
import com.editsolutions.prd.vo.PRDStatistics;

public class PRDStatisticsServiceImpl {
	private PRDStatisticsDao prdStatisticsDao;

	public PRDStatisticsServiceImpl() {
		super();
		prdStatisticsDao = new PRDStatisticsDao();
	}

	public List<PRDStatistics> getPRDStatistics() throws DAOException {
	    return prdStatisticsDao.getPRDStatistics();	
	}

	public PRDStatistics getPRDRunningStatistics() throws DAOException {
	    return prdStatisticsDao.getPRDRunningStatistics();	
	}
	public List<GroupSetup> getGroupsWithNoRecords(Date date) throws DAOException {
		return prdStatisticsDao.getGroupsWithNoRecords(date);
	}

	public List<GroupSetup> getNewPRDGroups(Date date) throws DAOException {
		return prdStatisticsDao.getNewPRDGroups(date);
	}

	public List<Object> getGroupsWithExcessTerminationRecords(Date date, Double percent) throws DAOException {
		return prdStatisticsDao.getGroupsWithExcessTerminationRecords(date, percent);
	}

	public List<Object> getGroupsWithExcessNewRecords(Date prdDate, Double percent) throws DAOException {
		return prdStatisticsDao.getGroupsWithExcessNewRecords(prdDate, percent);
	}

}
