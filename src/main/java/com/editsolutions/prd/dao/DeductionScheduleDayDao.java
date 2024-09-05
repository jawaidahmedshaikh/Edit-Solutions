package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;








import com.editsolutions.prd.PayrollDeductionUtils;
import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DeductionScheduleDay;
import com.editsolutions.prd.vo.PRDSettings;

import edit.services.db.hibernate.SessionHelper;

public class DeductionScheduleDayDao extends Dao<DeductionScheduleDay> {

	public DeductionScheduleDayDao() {
		super(DeductionScheduleDay.class);
	}
	

	public List<DeductionScheduleDay> saveDeductionSchedule(PRDSettings prdSettings, List<DeductionScheduleDay> deductionScheduleDays) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		
		deleteDeductionSchedule(prdSettings, connection);
		
		String sql = "insert into PRD_DeductionScheduleDay  values(?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement preparedStatement;
		Iterator<DeductionScheduleDay> it = deductionScheduleDays.iterator();
		while(it.hasNext()) {
			DeductionScheduleDay deductionScheduleDay = it.next();
			try {
				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			    preparedStatement.setLong(1, prdSettings.getPrdSetupPK());
			    preparedStatement.setDate(2, deductionScheduleDay.getPrdDueDate());
			    preparedStatement.setString(3, deductionScheduleDay.getPayrollDeductionCodeCT());
			    preparedStatement.setDate(4, null);
			    preparedStatement.setDate(5, null);
			    preparedStatement.setDate(6, null);
			    preparedStatement.setDate(7, null);
				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	deductionScheduleDay.setDeductionScheduleDayPK(generatedKeys.getLong(1));
		            } else {
		                throw new SQLException("Creating DeductionScheduleDay failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				throw new DAOException("Unable to insert deductionScheduleDays: " + e.toString());
			}
			
		}
		
		prdSettings = PayrollDeductionUtils.updatePRDCustomExtractDates(prdSettings);

        //PRDSettingsService pss = new PRDSettingsServiceImpl();
        //pss.savePRDSettings(prdSettings);

        return deductionScheduleDays;
	}
	
	public DeductionScheduleDay getFirstDeductionScheduleDay(PRDSettings prdSettings) {
		return getNextDeductionScheduleDay(prdSettings, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
	}

	public DeductionScheduleDay getNextDeductionScheduleDay(PRDSettings prdSettings, Date deductionDate) {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		 
		 String sql = "select * from PRD_DeductionScheduleDay where " +
		              "PRD_SetupFK = ? " +
		              "and PRD_DueDate = ( " +
		              "select min(dsd.PRD_DueDate) " +
		              "from PRD_DeductionScheduleDay dsd " +
		              "where PRD_SetupFK = ? " +
		              "and dsd.PRD_DueDate > ?) ";

		PreparedStatement preparedStatement;

		try {
				preparedStatement = connection.prepareStatement(sql);
			    preparedStatement.setLong(1, prdSettings.getPrdSetupPK());
			    preparedStatement.setLong(2, prdSettings.getPrdSetupPK());
			    preparedStatement.setDate(3, deductionDate);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					DeductionScheduleDay deductionScheduleDay = new DeductionScheduleDay();
					deductionScheduleDay.setDeductionScheduleDayPK(resultSet
							.getLong("PRD_DeductionScheduleDayPK"));
					deductionScheduleDay.setPrdSetupFK(resultSet
							.getLong("PRD_SetupFK"));
					deductionScheduleDay.setPrdDueDate(resultSet
							.getDate("PRD_DueDate"));
					deductionScheduleDay.setPayrollDeductionCodeCT(resultSet
							.getString("PayrollDeductionCodeCT"));
					deductionScheduleDay.setPayrollDeductionScheduleFK(resultSet
							.getLong("PRD_DeductionScheduleDayPK"));
					deductionScheduleDay.setAddDate(resultSet.getDate("AddDate"));
					deductionScheduleDay.setAddUser(resultSet.getString("AddUser"));
					deductionScheduleDay.setModDate(resultSet.getDate("ModDate"));
					deductionScheduleDay.setModUser(resultSet.getString("ModUser"));
					return deductionScheduleDay;
				}

		} catch (SQLException e) {
			throw new DAOException("Unable to insert deductionScheduleDays: " + e.toString());
		}
		return null;
		
	}

	public List<DeductionScheduleDay> getDeductionSchedule(
			PRDSettings prdSettings) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "select * from vw_PRD_DeductionScheduleDay  "
				+ "where PRD_SetupFK = ?  " + "order by PRD_DueDate ASC";
		PreparedStatement preparedStatement;
		List<DeductionScheduleDay> deductionScheduleDays = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, prdSettings.getPrdSetupPK());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				DeductionScheduleDay deductionScheduleDay = new DeductionScheduleDay();
				deductionScheduleDay.setDeductionScheduleDayPK(resultSet
						.getLong("PRD_DeductionScheduleDayPK"));
				deductionScheduleDay.setPrdSetupFK(resultSet
						.getLong("PRD_SetupFK"));
				deductionScheduleDay.setPrdDueDate(resultSet
						.getDate("PRD_DueDate"));
				deductionScheduleDay.setPayrollDeductionCodeCT(resultSet
						.getString("PayrollDeductionCodeCT"));
				deductionScheduleDay.setPayrollDeductionScheduleFK(resultSet
						.getLong("PayrollDeductionScheduleFK"));
				deductionScheduleDay.setAddDate(resultSet.getDate("AddDate"));
				deductionScheduleDay.setAddUser(resultSet.getString("AddUser"));
				deductionScheduleDay.setModDate(resultSet.getDate("ModDate"));
				deductionScheduleDay.setModUser(resultSet.getString("ModUser"));
				deductionScheduleDays.add(deductionScheduleDay);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DAOException("getDeductionSchedule() failed: " + e.toString());
		}
		return deductionScheduleDays;
	}

	public List<DeductionScheduleDay> createDeductionSchedule(PRDSettings prdSettings,
			List<DeductionScheduleDay> deductionSchedules) throws DAOException {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();

		deleteDeductionSchedule(prdSettings, connection);

		String sql = "insert into PRD_DeductionSchedule (PRD_SetupFK, PRD_DueDate, PayrollDeductionCodeCT) "
				+ " values (?, ?, ?) ";
		Iterator<DeductionScheduleDay> it = deductionSchedules.iterator();
		try {
			while (it.hasNext()) {
				DeductionScheduleDay deductionSchedule = it.next();
				PreparedStatement preparedStatement = connection
						.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1, deductionSchedule.getPrdSetupFK());
				preparedStatement.setDate(2, deductionSchedule.getPrdDueDate());
				preparedStatement.setString(3,
						deductionSchedule.getPayrollDeductionCodeCT());

				preparedStatement.executeUpdate();
				try (ResultSet generatedKeys = preparedStatement
						.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						deductionSchedule
								.setDeductionScheduleDayPK(generatedKeys
										.getLong(1));
					} else {
						throw new DAOException(
								"Creating deductionSchedule failed, no ID obtained.");
					}
				}
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return deductionSchedules;
	}

	public DeductionScheduleDay addDay(DeductionScheduleDay deductionScheduleDay)
			throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "insert into PRD_DeductionSchedule (PRD_SetupFK, PRD_DueDate, PayrollDeductionCodeCT) "
				+ " values (?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, deductionScheduleDay.getPrdSetupFK());
			preparedStatement.setDate(2, deductionScheduleDay.getPrdDueDate());
			preparedStatement.setString(3,
					deductionScheduleDay.getPayrollDeductionCodeCT());

			preparedStatement.executeUpdate();
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					deductionScheduleDay
							.setDeductionScheduleDayPK(generatedKeys.getLong(1));
				} else {
					throw new DAOException(
							"Creating deductionSchedule failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return deductionScheduleDay;
	}

	public DeductionScheduleDay updateDay(
			DeductionScheduleDay deductionScheduleDay) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "update PRD_DeductionScheduleDay set PRD_SetupFK = ?, PRDDueDate = ?, "
				+ "PayrollDeductionCodeCT = ? "
				+ "where PRD_DeductionScheduleDayPK = ?  ";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setLong(1, deductionScheduleDay.getPrdSetupFK());
			preparedStatement.setDate(2, deductionScheduleDay.getPrdDueDate());
			preparedStatement.setString(3,
					deductionScheduleDay.getPayrollDeductionCodeCT());
			preparedStatement.setLong(4,
					deductionScheduleDay.getDeductionScheduleDayPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return deductionScheduleDay;
	}

	public void deleteDeductionSchedule(PRDSettings prdSettings, Connection connection)
			throws DAOException {
		if (connection == null) {
		    connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		}
		String sql = "delete PRD_DeductionScheduleDay  "
				+ "where PRD_SetupFK = ?  ";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setLong(1, prdSettings.getPrdSetupPK());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
	}

	public void deleteDay(DeductionScheduleDay deductionScheduleDay)
			throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_DeductionScheduleDay  "
				+ "where PRD_DeductionScheduleDayPK = ?  ";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setLong(1,
					deductionScheduleDay.getDeductionScheduleDayPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
	}

}
