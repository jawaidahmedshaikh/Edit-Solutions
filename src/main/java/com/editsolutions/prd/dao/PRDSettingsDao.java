package com.editsolutions.prd.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;

import com.editsolutions.prd.PayrollDeductionUtils;
import com.editsolutions.prd.service.BatchDetailService;
import com.editsolutions.prd.service.BatchDetailServiceImpl;
import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.service.NotificationService;
import com.editsolutions.prd.service.NotificationServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.PRDSettings;

import edit.common.EDITDate;
import edit.services.db.hibernate.SessionHelper;

public class PRDSettingsDao extends Dao<PRDSettings> {

	public PRDSettingsDao() {
        super(PRDSettings.class);
    }
	
	public List<PRDSettings> getPRDSettingsByExtractDate(Date extractDate) throws DAOException {
		return getPRDSettingsByExtractDate(extractDate, "Standard");
	}
	
	private List<PRDSettings> filterOutBatchPRDs(List<PRDSettings> prdSettings) {
		BatchDetailService bds = new BatchDetailServiceImpl();
		List<PRDSettings> filteredList = new ArrayList<>();
		Iterator<PRDSettings> it = prdSettings.iterator();
		while (it.hasNext()) {
			PRDSettings prd = it.next();
			if (!bds.isInBatch(prd)) {
			    filteredList.add(prd);	
			}
		}
	    return filteredList;	
	}

	@SuppressWarnings("unchecked")
	public List<PRDSettings> getPRDSettingsByExtractDate(Date extractDate, String prdType) throws DAOException {
		
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(PRDSettings.class);
        c.add(Restrictions.eq("nextPRDExtractDate", extractDate));
        c.add(Restrictions.eq("typeCT", prdType));
        c.add(Restrictions.ne("systemCT", "ES"));
		return filterOutBatchPRDs(c.list());
	}

	public List<PRDSettings> getPRDSettingsByLastExtractDate(Date extractDate) throws DAOException {
        return getPRDSettingsByLastExtractDate(extractDate, "Standard");
	}

	@SuppressWarnings("unchecked")
	public List<PRDSettings> getPRDSettingsByLastExtractDate(Date extractDate, String prdType) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(PRDSettings.class);
        c.add(Restrictions.eq("lastPRDExtractDate", extractDate));
        c.add(Restrictions.eq("typeCT", prdType));
        c.add(Restrictions.eq("systemCT", "ES"));
		return filterOutBatchPRDs(c.list());
	}
	
	public PRDSettings getByGroupSetupPK(Serializable contractGroupPK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(PRDSettings.class);
        c.add(Restrictions.eq("contractGroupFK", contractGroupPK));
		return get(c);
	}

	/*
	public PRDSettings get(Serializable prdSetupPK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(PRDSettings.class);
        c.add(Restrictions.eq("prdSetupPK", prdSetupPK));
		return get(c);
	}
	*/
	
	public void deletePRDSettings(PRDSettings prdSettings) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_Setup " + " where PRD_SetupPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			if (prdSettings != null) {
			    DataHeaderService dhs = new DataHeaderServiceImpl();
			    List<DataHeader> dataHeaders = dhs.getDataHeaders(Long.toString(prdSettings.getPrdSetupPK())); 
			    if (dataHeaders != null) {
			        dhs.deleteDataHeaders(dataHeaders);
			    }

			    if (prdSettings.getNotifications() != null) {
			    	NotificationService ns = new NotificationServiceImpl();
			    	ns.deleteNotifications(prdSettings.getNotifications());
			    }
				preparedStatement.setLong(1, prdSettings.getPrdSetupPK());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		
	}
	
	public PRDSettings saveESPRDSettings(Long caseSetupPK, Long groupSetupPK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		CallableStatement cstmt = null;
		//ResultSet rs = null;
		try {
			cstmt = session.connection().prepareCall("{call sp_PRD_Setup_Insert ?, ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			cstmt.setLong(1, caseSetupPK);
			cstmt.setLong(2, groupSetupPK);
			boolean results = cstmt.execute();
			int rowsAffected = 0;
			// Protects against lack of SET NOCOUNT in stored procedure
			while (results || rowsAffected != -1) {
				if (results) {
					ResultSet rs = cstmt.getResultSet();
					break;
				} else {
					rowsAffected = cstmt.getUpdateCount();
				}
				results = cstmt.getMoreResults();
			}
		} catch (Exception e) {
             throw new DAOException(e);			
		}
		PRDSettings prdSettings = getByGroupSetupPK(groupSetupPK);
		return prdSettings;
		
	}
	
	public PRDSettings savePRDSettings(PRDSettings prdSettings) throws DAOException {
		prdSettings = PayrollDeductionUtils.calculateAndSetPRDExtractDates(prdSettings);
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_Setup (Case_ContractGroupFK, Group_ContractGroupFK, PayrollDeductionScheduleFK, "
                     + "PRD_FileTemplateFK, PRD_SetupName, PRD_TypeCT, StatusCT, DeliveryMethodCT, PRD_SubjectTemplateFK, "
                     + "PRD_MessageTemplateFK, PRD_InitialSubjectTemplateFK, PRD_InitialMessageTemplateFK, FirstDeductionDate, " 
                     + "FirstDeductionLeadDays, SubsequentDays, "
                     + "EffectiveDate, TerminationDate, CurrentDateThru, ChangeEffectiveDate, NextPRDExtractDate, "
                     + "LastPRDExtractDate, NextPRDDueDate, EmailSentFromCT, SummaryCT, SortOptionCT, ReportTypeCT, "
                     + "FTPAddress, FTPUserName, FTPPassword, RepName, RepPhoneNumber, AddDate, AddUser, "
                     + "ModDate, ModUser, HoldForReview, NetworkLocation, NetworkLocationDirectory, PRD_HeaderTemplateFK, "
                     + "PRD_FooterTemplateFK, MaskSsn, RunningCount) " 
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			//System.out.println(sql);
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, prdSettings.getContractCaseFK());
			preparedStatement.setLong(2, prdSettings.getContractGroupFK());
			if (prdSettings.getPayrollDeductionScheduleFK() != null) {
			    preparedStatement.setLong(3, prdSettings.getPayrollDeductionScheduleFK());
			} else {
			    preparedStatement.setLong(3, 0L);
			}
			if (prdSettings.getFileTemplate() != null) {
			    preparedStatement.setLong(4, prdSettings.getFileTemplateFK());
			} else {
			    preparedStatement.setLong(4, 5000000000000L);
			}
			preparedStatement.setString(5, prdSettings.getSetupName());
			preparedStatement.setString(6, prdSettings.getTypeCT());
			preparedStatement.setString(7, prdSettings.getStatusCT());
			preparedStatement.setString(8, prdSettings.getDeliveryMethodCT());
			if (prdSettings.getSubjectTemplateFK() != null) {
			    preparedStatement.setLong(9, prdSettings.getSubjectTemplateFK());
			} else {
			    preparedStatement.setLong(9, 5000000000000L);
			}
			if (prdSettings.getMessageTemplateFK() != null) {
			    preparedStatement.setLong(10, prdSettings.getMessageTemplateFK());
			} else {
			    preparedStatement.setLong(10, 5000000000001L);
			}
			if (prdSettings.getInitialSubjectTemplateFK() != null) {
			    preparedStatement.setLong(11, prdSettings.getInitialSubjectTemplateFK());
			} else {
			    preparedStatement.setLong(11, 5000000000105L);
			}
			if (prdSettings.getInitialMessageTemplateFK() != null) {
			    preparedStatement.setLong(12, prdSettings.getInitialMessageTemplateFK());
			} else {
			    preparedStatement.setLong(12, 5000000000104L);
			}
			preparedStatement.setDate(13, prdSettings.getFirstDeductionDate());
			preparedStatement.setInt(14, prdSettings.getFirstDeductionLeadDays());
			preparedStatement.setInt(15, prdSettings.getSubsequentDays());
			preparedStatement.setDate(16, prdSettings.getEffectiveDate());
			preparedStatement.setDate(17, prdSettings.getTerminationDate());
			preparedStatement.setDate(18, prdSettings.getCurrentDateThru());
			preparedStatement.setDate(19, prdSettings.getChangeEffectiveDate());
			preparedStatement.setDate(20, prdSettings.getNextPRDExtractDate());
			preparedStatement.setDate(21, prdSettings.getLastPRDExtractDate());
			preparedStatement.setDate(22, prdSettings.getNextPRDDueDate());
//			preparedStatement.setDate(20, null);
			preparedStatement.setString(23, prdSettings.getEmailSentFrom());
			preparedStatement.setString(24, prdSettings.getSummaryCT());
			preparedStatement.setString(25, prdSettings.getSortOptionCT());
			preparedStatement.setString(26, prdSettings.getReportTypeCT());
			preparedStatement.setString(27, prdSettings.getFtpAddress());
			preparedStatement.setString(28, prdSettings.getFtpUserName());
			preparedStatement.setString(29, prdSettings.getFtpPassword());
			preparedStatement.setString(30, prdSettings.getRepName());
			preparedStatement.setString(31, prdSettings.getRepPhoneNumber());
			preparedStatement.setDate(32, prdSettings.getAddDate());
			preparedStatement.setString(33, prdSettings.getAddUser());
			preparedStatement.setDate(34, prdSettings.getModDate());
			preparedStatement.setString(35, prdSettings.getModUser());
			preparedStatement.setBoolean(36, prdSettings.isHoldForReview());
			if ((prdSettings.getNetworkLocation() == null) || prdSettings.getNetworkLocation().trim().isEmpty()) {
			    preparedStatement.setString(37, null);
			    preparedStatement.setString(38, null);
			} else {
			    preparedStatement.setString(37, prdSettings.getNetworkLocation());
			    preparedStatement.setString(38, prdSettings.getNetworkLocationDirectory());
			}
			if (prdSettings.getHeaderTemplateFK() != null) {
			    preparedStatement.setLong(39, prdSettings.getHeaderTemplateFK());
			}  else {
			    preparedStatement.setNull(39, Types.BIGINT);
			}
			if (prdSettings.getFooterTemplateFK() != null) {
			    preparedStatement.setLong(40, prdSettings.getFooterTemplateFK());
			}  else {
			    preparedStatement.setNull(40, Types.BIGINT);
			} 
			preparedStatement.setBoolean(41, prdSettings.isMaskSsn());
			preparedStatement.setInt(42, prdSettings.getRunningCount());

			preparedStatement.execute();
	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	prdSettings.setPrdSetupPK(generatedKeys.getLong(1));
	            }
	            else {
	                throw new DAOException("Creating PRD failed, no ID obtained.");
	            }
	        }

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return prdSettings;
	}


	public PRDSettings updatePRDSettings(PRDSettings prdSettings, Date extractDate) throws DAOException {
		// save if not yet in DB so we have pk
		if (prdSettings == null) {
			prdSettings = savePRDSettings(prdSettings);
		}
		if (extractDate == null) {
		    prdSettings = PayrollDeductionUtils.calculateAndSetPRDExtractDates(prdSettings);
		} else {
		    prdSettings = PayrollDeductionUtils.calculateAndSetPRDExtractDates(prdSettings, extractDate);
		}
		return updatePRDSettings(prdSettings);
	}

	public PRDSettings updatePRDExtractDate(PRDSettings prdSettings, Date extractDate) throws DAOException {
		//PayrollDeductionUtils.calculateAndSetPRDExtractDates(prdSettings, extractDate);
		EDITDate dueDate = PayrollDeductionUtils.getEDITDate(extractDate);
		BusinessDay bd = new BusinessCalendar().findNextBusinessDay(dueDate, 1);
		dueDate = bd.getBusinessDate();
		prdSettings.setNextPRDDueDate(new Date(dueDate.getTimeInMilliseconds()));
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_Setup set " 
		+ "CurrentDateThru = ?, NextPRDExtractDate = ?, LastPRDExtractDate = ?, NextPRDDueDate = ? "
		+ "where PRD_SetupPK = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, prdSettings.getCurrentDateThru());
			preparedStatement.setDate(2, prdSettings.getNextPRDExtractDate());
			preparedStatement.setDate(3, prdSettings.getLastPRDExtractDate());
			preparedStatement.setDate(4, prdSettings.getNextPRDDueDate());
			preparedStatement.setLong(5, prdSettings.getPrdSetupPK());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return prdSettings;
		
		//return updatePRDSettings(prdSettings);
		
	}
	
	public PRDSettings updateESPRDExtractDate(PRDSettings prdSettings, Date extractDate) throws DAOException {
		PayrollDeductionUtils.calculateAndSetPRDExtractDates(prdSettings, extractDate);
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PayrollDeductionSchedule set " 
		+ "CurrentDateThru = ?, NextPRDExtractDate = ?, LastPRDExtractDate = ?, NextPRDDueDate = ? "
		+ "where PayrollDeductionSchedulePK = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, prdSettings.getCurrentDateThru());
			preparedStatement.setDate(2, prdSettings.getNextPRDExtractDate());
			preparedStatement.setDate(3, prdSettings.getLastPRDExtractDate());
			preparedStatement.setDate(4, prdSettings.getNextPRDDueDate());
			preparedStatement.setLong(5, prdSettings.getPayrollDeductionScheduleFK());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return prdSettings;
		//return updatePRDSettings(prdSettings);
		
	}

	public PRDSettings updatePRDSettings(PRDSettings prdSettings) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_Setup " +
				" set Case_ContractGroupFK = ?, Group_ContractGroupFK = ?, PayrollDeductionScheduleFK = ?, PRD_FileTemplateFK = ?, " +
				" PRD_SetupName = ?, PRD_TypeCT = ?, StatusCT = ?, DeliveryMethodCT = ?, PRD_SubjectTemplateFK = ?, " + 
				" PRD_MessageTemplateFK = ?, PRD_InitialSubjectTemplateFK = ?, PRD_InitialMessageTemplateFK = ?, HoldForReview = ?, " +
				" FirstDeductionDate = ?, FirstDeductionLeadDays = ?, " +
				" SubsequentDays = ?, EffectiveDate = ?, TerminationDate = ?, CurrentDateThru = ?, ChangeEffectiveDate = ?, " +
				" NextPRDExtractDate = ?, LastPRDExtractDate = ?, NextPRDDueDate = ?, EmailSentFromCT = ?, " + 
				" SummaryCT = ?, SortOptionCT = ?, ReportTypeCT = ?, FTPAddress = ?, FTPUserName = ?, FTPPassword = ?, " +
				" RepName = ?, RepPhoneNumber = ?, NetworkLocation = ?, NetworkLocationDirectory = ?, " +
				" PRD_HeaderTemplateFK = ?, PRD_FooterTemplateFK = ?, MaskSsn = ? , RunningCount = ? " +
				" where PRD_SetupPK = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, prdSettings.getContractCaseFK());
			preparedStatement.setLong(2, prdSettings.getContractGroupFK());
			if (prdSettings.getPayrollDeductionScheduleFK() != null) {
			    preparedStatement.setLong(3, prdSettings.getPayrollDeductionScheduleFK());
			} else {
			    preparedStatement.setLong(3, 0L);
			}
			if (prdSettings.getFileTemplate() != null) {
			    preparedStatement.setLong(4, prdSettings.getFileTemplateFK());
			} else {
			    preparedStatement.setLong(4, 5000000000000L);
			}
			preparedStatement.setString(5, prdSettings.getSetupName());
			preparedStatement.setString(6, prdSettings.getTypeCT());
			preparedStatement.setString(7, prdSettings.getStatusCT());
			preparedStatement.setString(8, prdSettings.getDeliveryMethodCT());
			if (prdSettings.getSubjectTemplateFK() != null) {
			    preparedStatement.setLong(9, prdSettings.getSubjectTemplateFK());
			} else {
			    preparedStatement.setLong(9, 5000000000000L);
			}
			if (prdSettings.getMessageTemplateFK() != null) {
			    preparedStatement.setLong(10, prdSettings.getMessageTemplateFK());
			} else {
			    preparedStatement.setLong(10, 5000000000001L);
			}
			if (prdSettings.getInitialSubjectTemplateFK() != null) {
			    preparedStatement.setLong(11, prdSettings.getInitialSubjectTemplateFK());
			} else {
			    preparedStatement.setLong(11, 5000000000106L);
			}
			if (prdSettings.getInitialMessageTemplateFK() != null) {
			    preparedStatement.setLong(12, prdSettings.getInitialMessageTemplateFK());
			} else {
			    preparedStatement.setLong(12, 5000000000104L);
			}
			preparedStatement.setBoolean(13, prdSettings.isHoldForReview());
			preparedStatement.setDate(14, prdSettings.getFirstDeductionDate());
			preparedStatement.setInt(15, prdSettings.getFirstDeductionLeadDays());
			preparedStatement.setInt(16, prdSettings.getSubsequentDays());
			preparedStatement.setDate(17, prdSettings.getEffectiveDate());
			preparedStatement.setDate(18, prdSettings.getTerminationDate());
			preparedStatement.setDate(19, prdSettings.getCurrentDateThru());
			preparedStatement.setDate(20, prdSettings.getChangeEffectiveDate());
			preparedStatement.setDate(21, prdSettings.getNextPRDExtractDate());
			preparedStatement.setDate(22, prdSettings.getLastPRDExtractDate());
			preparedStatement.setDate(23, prdSettings.getNextPRDDueDate());
//			preparedStatement.setDate(21, null);
			preparedStatement.setString(24, prdSettings.getEmailSentFrom());
			preparedStatement.setString(25, prdSettings.getSummaryCT());
			preparedStatement.setString(26, prdSettings.getSortOptionCT());
			preparedStatement.setString(27, prdSettings.getReportTypeCT());
			preparedStatement.setString(28, prdSettings.getFtpAddress());
			preparedStatement.setString(29, prdSettings.getFtpUserName());
			preparedStatement.setString(30, prdSettings.getFtpPassword());
			preparedStatement.setString(31, prdSettings.getRepName());
			preparedStatement.setString(32, prdSettings.getRepPhoneNumber());
			if (prdSettings.getNetworkLocationDirectory() == null) {
			    preparedStatement.setString(33, null); 
			} else {
			    preparedStatement.setString(33, prdSettings.getNetworkLocation());
			}
			preparedStatement.setString(34, prdSettings.getNetworkLocationDirectory());

			if (prdSettings.getHeaderTemplateFK() != null) {
			    preparedStatement.setLong(35, prdSettings.getHeaderTemplateFK());
			}  else {
			    preparedStatement.setNull(35, Types.BIGINT);
			} 
			if (prdSettings.getFooterTemplateFK() != null) {
			    preparedStatement.setLong(36, prdSettings.getFooterTemplateFK());
			}  else {
			    preparedStatement.setNull(36, Types.BIGINT);
			} 
			
			preparedStatement.setBoolean(37, prdSettings.isMaskSsn());
			preparedStatement.setInt(38, prdSettings.getRunningCount());
			preparedStatement.setLong(39, prdSettings.getPrdSetupPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return prdSettings;
	}

	public List<String> getSenderEmails() {
		String sql = "select distinct EmailSentFromCT from vw_PRD_Setup order by EmailSentFromCT";
		
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session.createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> emailAddresses = query.list();
		return emailAddresses;
	}
	
	public List<PRDSettings> getOddBallPRDs() {
		String sql = "select * from vw_PRD_Setup where nextPRDExtractDate not in " + 
				     "(select top 1 NextPRDExtractDate from vw_PRD_Setup " +
	                 "where PRD_TypeCT = 'Standard' " +
				     "group by NextPRDExtractDate " +
				     "order by COUNT(NextPRDExtractDate) desc ) order by PRD_TypeCT, NextPRDExtractDate ";
		
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(PRDSettings.class);
		@SuppressWarnings("unchecked")
		List<PRDSettings> prdSettings = query.list();
		return prdSettings;
	}
	
	public boolean exportDirectoryExists(PRDSettings prdSettings) {
		// Network permissions prevent this from working.
		//return PRDFileUtils.directoryExists(prdSettings.getNetworkLocation()  + File.separator + 
		//		prdSettings.getNetworkLocationDirectory());
		return true;
	}
}
