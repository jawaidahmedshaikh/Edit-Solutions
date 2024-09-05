package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.util.SQLBuilderFactory;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.MessageTemplateField;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SourceField;

import edit.services.db.hibernate.SessionHelper;
import edu.emory.mathcs.backport.java.util.Collections;
import electric.soap.rpc.Return;
import electric.util.holder.intInOut;

public class ImportRecordDao extends Dao<ImportRecord> {

	public ImportRecordDao() {
		super(ImportRecord.class);
	}

	public int getAllODSRecords() throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.ODS)
				.connection();
		String sql = "select count(*) from vw_PRD_Import_PHL";
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return 0;

	}

	public void updateChangeReason(long pk, String changeReason)  throws DAOException  {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_Record set ChangeReason = ? " 
				+ " where PRD_RecordPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, changeReason);
			preparedStatement.setLong(2, pk);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void deleteTestRecords()  throws DAOException  {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_Record" 
				+ " where isTest = 1";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<String> getAllProductTypes(PRDSettings prdSettings) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.ODS)
				.connection();
		String sql = "select distinct PRODUCT_TYPE from vw_PRD_Import "
				+ "where MARKET_CODE = ?";
		List<String> productTypes = new ArrayList<>();
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1, prdSettings.getGroupSetup()
					.getGroupNumber());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				productTypes.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return productTypes;
	}

	public List<ImportRecord> getImportRecordsByProduct(
		ImportRecord importRecord, PRDSettings prdSettings) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(ImportRecord.class);
		if (importRecord != null) {
			cr.add(Restrictions.eq("employeeID", importRecord.getEmployeeID()));
			cr.add(Restrictions.eq("groupNumber", prdSettings.getGroupSetup().getGroupNumber()));
			if (prdSettings.getSystemCT().equals("ES")) {
				cr.add(Restrictions.le(
						"deductionDate",
						new java.sql.Date((DateUtils.addDays(
								prdSettings.getLastPRDExtractDate(), 1))
								.getTime())));
			} else {
				cr.add(Restrictions.le("deductionDate",
						prdSettings.getNextPRDDueDate()));
			}
			cr.add(Restrictions.eq("productType", importRecord.getProductType()));
			cr.addOrder(Order.desc("deductionDate"));
			if ((prdSettings.getReportTypeCT() != null) && prdSettings.getReportTypeCT().equals("Changes")) {
				cr.setMaxResults(2);
			} else {
				cr.setMaxResults(1);
			}
		}
		@SuppressWarnings("unchecked")
		List<ImportRecord> list = cr.list();
		return list;

	}


	public List<ImportRecord> getImportRecordsForProductQuery(
			PRDSettings prdSettings) throws DAOException {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		List<ImportRecord> importRecords = new ArrayList<>();

		String sql = "select AccountNumber, EmployeeID, EmployeeName, sum(DeductionAmount) as 'DeductionAmount', "
				+ "DeductionDate, ProductType from PRD_Record "
				+ "where DeductionDate = ? "
				+ "and GroupNumber = ? "
				+ "group by EmployeeId, ProductType, DeductionAmount, EmployeeName, AccountNumber, DeductionDate";

		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			// preparedStatement.setDate(1, prdSettings.getNextPRDDueDate());
			if (prdSettings.getSystemCT().equals("ES")) {
				preparedStatement.setDate(1,
						prdSettings.getLastPRDExtractDate());
			} else {
				preparedStatement.setDate(1,
						prdSettings.getNextPRDExtractDate());
			}
			preparedStatement.setString(2, prdSettings.getGroupSetup()
					.getGroupNumber());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ImportRecord importRecord = new ImportRecord();
				importRecord.setAccountNumber(rs.getString("AccountNumber"));
				importRecord.setEmployeeID(rs.getString("EmployeeID"));
				importRecord.setEmployeeName(rs.getString("EmployeeName"));
				importRecord
						.setDeductionAmount(rs.getDouble("DeductionAmount"));
				importRecord.setDeductionDate(rs.getDate("DeductionDate"));
				importRecord.setProductType(rs.getString("ProductType"));
				importRecords.add(importRecord);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return importRecords;
	}


	public List<ImportRecord> getImportRecordsByEmployeeID(String employeeID,
			PRDSettings prdSettings)  throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(ImportRecord.class);
		if (employeeID != null) {
			cr.add(Restrictions.eq("employeeID", employeeID));
			if (prdSettings.getSystemCT().equals("ES")) {
				cr.add(Restrictions.le(
						"deductionDate",
						new java.sql.Date((DateUtils.addDays(
								prdSettings.getLastPRDExtractDate(), 1))
								.getTime())));
			} else {
				cr.add(Restrictions.le("deductionDate",
						prdSettings.getNextPRDDueDate()));
			}
			cr.add(Restrictions.eq("groupNumber", prdSettings.getGroupSetup()
					.getGroupNumber()));
			cr.addOrder(Order.desc("deductionDate"));
			if ((prdSettings.getReportTypeCT() != null) && prdSettings.getReportTypeCT().equals("Changes")) {
				cr.setMaxResults(2);
			} else {
				cr.setMaxResults(1);
			}
		}
		@SuppressWarnings("unchecked")
		List<ImportRecord> list = cr.list();
		return list;
	}

	public void importDataRecords(PRDSettings prdSettings) throws DAOException {
		List<ImportRecord> importRecords = getImportRecordListByDate(
				prdSettings.getGroupSetup().getGroupNumber(),
				prdSettings.getNextPRDDueDate());
		DataHeader dataHeader = new DataHeader();
		dataHeader.setPrdDate(prdSettings.getNextPRDDueDate());
		dataHeader.setSetupFK(prdSettings.getPrdSetupPK());
		DataHeaderService dhs = new DataHeaderServiceImpl();
		dhs.saveDataHeader(dataHeader);

	}

	public List<ImportRecord> getImportRecordList(String groupNumber) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(ImportRecord.class);
		if (groupNumber != null) {
			cr.add(Restrictions.eq("groupNumber", groupNumber));
		}
		@SuppressWarnings("unchecked")
		List<ImportRecord> list = cr.list();
		return list;
	}

	public List<ImportRecord> getImportRecordListByDate(String groupNumber,
			Date deductionDate) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(ImportRecord.class);
		if (groupNumber != null) {
			cr.add(Restrictions.eq("groupNumber", groupNumber));
			cr.add(Restrictions.eq("deductionDate", deductionDate));
		}
		@SuppressWarnings("unchecked")
		List<ImportRecord> list = cr.list();
		return list;
	}

	public List<String> getPolicyNumberByGroup(PRDSettings prdSettings) throws DAOException { 

		List<String> policyNumbers = new ArrayList<>();
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "select distinct PolicyNumber from PRD_Record"
				+ " where GroupNumber = ? and DeductionDate = ? ";
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1, prdSettings.getGroupSetup()
					.getGroupNumber());
			if (prdSettings.getSystemCT().equals("ES")) {
				preparedStatement.setDate(
						2,
						new java.sql.Date((prdSettings.getLastPRDExtractDate())
								.getTime()));
			} else {
				preparedStatement.setDate(2,
						prdSettings.getNextPRDExtractDate());
			}
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				policyNumbers.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return policyNumbers;
	}
	
	public List<ImportRecord> getImportRecordsByPolicyNumber(String policyNumber, PRDSettings prdSettings) throws DAOException {
//		String sql = SQLBuilderFactory.getSQLForDynamicQuery(prdSettings);
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(ImportRecord.class);
		if (policyNumber != null) {
			cr.add(Restrictions.eq("policyNumber", policyNumber));
//			if (prdSettings.getSystemCT().equals("ES")) {
				cr.add(Restrictions.le(
						"deductionDate",
						new java.sql.Date((DateUtils.addDays(
								prdSettings.getLastPRDExtractDate(), 1))
								.getTime())));
//			} else {
//				cr.add(Restrictions.le("deductionDate",
//						prdSettings.getNextPRDDueDate()));
//			}
			cr.add(Restrictions.eq("groupNumber", prdSettings.getGroupSetup()
					.getGroupNumber()));
			cr.addOrder(Order.desc("deductionDate"));
			if ((prdSettings.getReportTypeCT() != null) && prdSettings.getReportTypeCT().equals("Changes")) {
				cr.setMaxResults(2);
			} else {
				cr.setMaxResults(1);
			}
		}
		@SuppressWarnings("unchecked")
		List<ImportRecord> list = cr.list();
		return list;
	}


	public List<String> getEmployeeIDByGroup(PRDSettings prdSettings) throws DAOException { 

		List<String> employeeIDs = new ArrayList<>();
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "select distinct employeeID from PRD_Record"
				+ " where GroupNumber = ? and DeductionDate = ? ";
		try {

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1, prdSettings.getGroupSetup()
					.getGroupNumber());
			if (prdSettings.getSystemCT().equals("ES")) {
				preparedStatement.setDate(
						2,
						new java.sql.Date((prdSettings.getLastPRDExtractDate())
								.getTime()));
			} else {
				preparedStatement.setDate(2,
						prdSettings.getNextPRDExtractDate());
			}
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				employeeIDs.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return employeeIDs;
	}

}
