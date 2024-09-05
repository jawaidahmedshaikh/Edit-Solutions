package com.editsolutions.prd.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.dao.DeductionScheduleDayDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DeductionScheduleDay;
import com.editsolutions.prd.vo.PRDSettings;


public class DeductionScheduleDayServiceImpl implements DeductionScheduleDayService {
	
	private DeductionScheduleDayDao deductionScheduleDayDao;
	
	public DeductionScheduleDayServiceImpl() {
		super();
		deductionScheduleDayDao = new DeductionScheduleDayDao();
	}

	@Override
	public List<DeductionScheduleDay> getDeductionSchedule(PRDSettings prdSettings)
			throws DAOException {
		return deductionScheduleDayDao.getDeductionSchedule(prdSettings);
	}
	
	public DeductionScheduleDay getNextDeductionScheduleDay(PRDSettings prdSettings, Date deductionDate) throws DAOException {
		return deductionScheduleDayDao.getNextDeductionScheduleDay(prdSettings, deductionDate);
	}

	
	public List<DeductionScheduleDay> saveDeductionSchedule(PRDSettings prdSettings, List<DeductionScheduleDay> deductionScheduleDays) throws DAOException {
		return deductionScheduleDayDao.saveDeductionSchedule(prdSettings, deductionScheduleDays);
	}

	@Override
	public List<DeductionScheduleDay> createDeductionSchedule(PRDSettings prdSettings,
			List<DeductionScheduleDay> deductionSchedules) throws DAOException {
		return deductionScheduleDayDao.createDeductionSchedule(prdSettings, deductionSchedules);
	}

	@Override
	public DeductionScheduleDay addDay(DeductionScheduleDay deductionScheduleDay)
			throws DAOException {
		return deductionScheduleDayDao.addDay(deductionScheduleDay);
	}

	@Override
	public DeductionScheduleDay updateDay(DeductionScheduleDay deductionScheduleDay)
			throws DAOException {
		return deductionScheduleDayDao.updateDay(deductionScheduleDay);
	}

	@Override
	public void deleteDay(DeductionScheduleDay deductionScheduleDay)
			throws DAOException {
		deductionScheduleDayDao.deleteDay(deductionScheduleDay);
		
	}

	@Override
	public void deleteDeductionSchedule(PRDSettings prdSettings, Connection connection)
			throws DAOException {
		deductionScheduleDayDao.deleteDeductionSchedule(prdSettings, connection);
		
	}
	
	public DeductionScheduleDay getFirstDeductionScheduleDay(PRDSettings prdSettings) throws DAOException {
		return deductionScheduleDayDao.getFirstDeductionScheduleDay(prdSettings);
	}
	
	/*
	public List<DeductionScheduleDay> createMonthlySchedule(PRDSettings prdSettings, int dayOfMonth) throws DAOException {
		deductionScheduleDayDao.createMonthlySchedule(prdSettings, dayOfMonth);
	}
	*/
	

}
