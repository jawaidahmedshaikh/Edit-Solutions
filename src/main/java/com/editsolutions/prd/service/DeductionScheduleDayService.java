package com.editsolutions.prd.service;


import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DeductionScheduleDay;
import com.editsolutions.prd.vo.PRDSettings;

import electric.util.holder.intInOut;

public interface DeductionScheduleDayService {
	
	public List<DeductionScheduleDay> getDeductionSchedule(PRDSettings prdSettings) throws DAOException;
	public List<DeductionScheduleDay> createDeductionSchedule(PRDSettings prdSettings, List<DeductionScheduleDay> deductionSchedules) throws DAOException;
	public DeductionScheduleDay addDay(DeductionScheduleDay deductionScheduleDay) throws DAOException ;
	public DeductionScheduleDay updateDay(DeductionScheduleDay deductionScheduleDay) throws DAOException;
	public DeductionScheduleDay getNextDeductionScheduleDay(PRDSettings prdSettings, Date deductionDate) throws DAOException;
	public void deleteDay(DeductionScheduleDay deductionScheduleDay) throws DAOException;
	public List<DeductionScheduleDay> saveDeductionSchedule(PRDSettings prdSettings, List<DeductionScheduleDay> deductionScheduleDays) throws DAOException ;
	public void deleteDeductionSchedule(PRDSettings prdSettings, Connection connection) throws DAOException; 
	public DeductionScheduleDay getFirstDeductionScheduleDay(PRDSettings prdSettings) throws DAOException;
	/*
	public List<DeductionScheduleDay> createMonthlySchedule(PRDSettings prdSettings, int dayOfWeek) throws DAOException;
	*/
}
