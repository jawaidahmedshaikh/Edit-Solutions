package com.editsolutions.prd.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.aop.ThrowsAdvice;

import com.editsolutions.prd.service.BatchDetailService;
import com.editsolutions.prd.service.BatchDetailServiceImpl;
import com.editsolutions.prd.service.BatchService;
import com.editsolutions.prd.service.BatchServiceImpl;
import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.service.DataIssuesService;
import com.editsolutions.prd.service.DataIssuesServiceImpl;
import com.editsolutions.prd.service.FileTemplateFieldService;
import com.editsolutions.prd.service.FileTemplateFieldServiceImpl;
import com.editsolutions.prd.service.FileTemplateService;
import com.editsolutions.prd.service.FileTemplateServiceImpl;
import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.util.OutputUtils;
import com.editsolutions.prd.vo.Batch;
import com.editsolutions.prd.vo.BatchDetail;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.PayrollDeductionData;

import edit.services.db.hibernate.SessionHelper;
import electric.application.tools.NewApp;
import electric.util.holder.booleanInOut;
import electric.util.holder.intInOut;

public class DataDao extends Dao<PayrollDeductionData> {

	public DataDao() {
		super(PayrollDeductionData.class);
	}

	public PayrollDeductionData getPayrollDeductionData(String id)
			throws DAOException {
		return get(Long.valueOf(id));
	}

	public List<PayrollDeductionData> getPayrollDeductionDataList(String s,
			boolean all) throws DAOException {
		String sql;
		if (all) {
			sql = "select b.FieldOrder, a.* from PRD_Data a, PRD_FileTemplateField b "
					+ "where a.PRD_FileTemplateFieldPK = b.PRD_FileTemplateFieldPK "
					+ "and a.PRD_DataHeaderFK = "
					+ Long.valueOf(s)
					+ " "
					+ "order by a.PRD_RecordFK, b.FieldOrder";
		} else {
			sql = "select b.FieldOrder, a.* from PRD_Data a, PRD_FileTemplateField b "
					+ "where a.PRD_FileTemplateFieldPK = b.PRD_FileTemplateFieldPK "
					+ "and a.PRD_RecordFK in ( "
					+ "select      distinct a.PRD_RecordFK "
					+ " from  PRD_Data a, PRD_DataIssues b "
					+ "where b.PRD_DataFK = a.PRD_DataPK  "
					+ "and b.PRD_DataHeaderFK = "
					+ Long.valueOf(s)
					+ ") and a.PRD_DataHeaderFK = "
					+ Long.valueOf(s)
					+ " order by a.PRD_RecordFK, b.FieldOrder ";

		}
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(PayrollDeductionData.class);
		@SuppressWarnings("unchecked")
		List<PayrollDeductionData> prdData = query.list();

		return prdData;
	}

	public List<PayrollDeductionData> getPayrollDeductionDataHistoryList(
			PRDSettings prdSettings, String id) throws DAOException {
		// return
		// getPayrollDeductionDataHistoryList(Long.parseLong(prdSettingsPK),
		// id);
		return getPayrollDeductionDataHistoryList(prdSettings, id, null);

	}

	public List<PayrollDeductionData> getPayrollDeductionDataHistoryList(
			PRDSettings prdSettings, String id, String productType)
			throws DAOException {
		if (productType != null) {
			// productTypeSQL =
		}
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session
				.createSQLQuery("select b.FieldOrder, a.* from PRD_Data a, PRD_FileTemplateField b, PRD_Record r "
						+ "where a.PRD_FileTemplateFieldPK = b.PRD_FileTemplateFieldPK "
						+ " and a.PRD_RecordFK = r.PRD_RecordPK "
						+ "and r.GroupNumber = '"
						+ prdSettings.getGroupSetup().getGroupNumber()
						+ "' "
						+ "and PRD_RecordFK in ("
						+ "select PRD_RecordFK from PRD_Data where FieldValue = '"
						+ id + "') " + "order by a.PRD_RecordFK, b.FieldOrder");

		query.addEntity(PayrollDeductionData.class);
		@SuppressWarnings("unchecked")
		List<PayrollDeductionData> prdData = query.list();
		session.close();
		return prdData;
	}

	public void createReviewFiles(List<DataHeader> dataHeaders)
			throws DAOException {
		String prdDirectory = System.getProperty("PRDDirectory");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(new java.util.Date());
		String productFileName = prdDirectory + "\\" + "NEW_PRD_PRODUCT_FILE_"
				+ dateString + ".xlsx";
		String eeidFileName = prdDirectory + "\\" + "NEW_PRD_EEID_FILE_"
				+ dateString + ".xlsx";
		Iterator<DataHeader> it = dataHeaders.iterator();
		XSSFWorkbook productWorkbook = new XSSFWorkbook();
		XSSFWorkbook eeidWorkbook = new XSSFWorkbook();
		XSSFSheet productSheet = productWorkbook.createSheet();
		XSSFSheet eeidSheet = eeidWorkbook.createSheet();
		CellStyle dateCellStyle = productWorkbook.createCellStyle();
		CreationHelper createHelper = productWorkbook.getCreationHelper();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				"mm-dd-yyyy"));

		int productCount = 0;
		int eeidCount = 0;
		int productColumnCount = 5;
		int eeidColumnCount = 4;

		while (it.hasNext()) {
			DataHeader dh = it.next();
			System.out.println(dh.getPrdSettings().getGroupSetup()
					.getGroupNumber());
			System.out.println(dh.getPrdSettings().getSummaryCT());
			// System.out.println("data count: " +
			// dh.getPayrollDeductionDatas().size());
			if (dh.getPrdSettings().getSummaryCT().equals("Product")) {
				productSheet = OutputUtils.createReviewFile(dh, productSheet,
						dateCellStyle);
				productCount++;
			} else if (dh.getPrdSettings().getSummaryCT().equals("EEID")) {
				eeidSheet = OutputUtils.createReviewFile(dh, eeidSheet,
						dateCellStyle);
				eeidCount++;
			}

		}

		System.out.println("Product Count: " + productCount);
		System.out.println("EEID Count: " + eeidCount);

		for (int c = 0; c < productColumnCount; c++) {
			productSheet.autoSizeColumn(c + 1);
		}

		for (int c = 0; c < eeidColumnCount; c++) {
			eeidSheet.autoSizeColumn(c + 1);
		}

		FileOutputStream productOut;
		FileOutputStream eeidOut;
		try {
			File productFile = new File(productFileName);
			File eeidFile = new File(eeidFileName);
			productOut = new FileOutputStream(productFile);
			eeidOut = new FileOutputStream(eeidFile);
			productWorkbook.write(productOut);
			eeidWorkbook.write(eeidOut);
			productOut.close();
			eeidOut.close();
			productWorkbook.close();
			eeidWorkbook.close();
		} catch (Exception e) {
			throw new DAOException(e.toString());
		}

	}

	// sp_prd_data_GroupByProduct 5000000000143, 'xxx-xx-xxxx'``
	public String getCoverageTier(String ownerSSN) throws DAOException {
		ownerSSN = ownerSSN.replace("-", "");
		String coverageTier = "";
		Session session = SessionHelper.getSession(SessionHelper.ODS);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			cstmt = session.connection().prepareCall(
					"{call sp_PRD_CoverageTier ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			cstmt.setString(1, ownerSSN);
			boolean results = cstmt.execute();
			int rowsAffected = 0;

			// Protects against lack of SET NOCOUNT in stored procedure
			while (results || rowsAffected != -1) {
				if (results) {
					rs = cstmt.getResultSet();
					break;
				} else {
					rowsAffected = cstmt.getUpdateCount();
				}
				results = cstmt.getMoreResults();
			}
			while (rs.next()) {
				coverageTier = rs.getString(1);
			}

		} catch (HibernateException | SQLException e) {
			throw new DAOException(e.toString());
		}
		return coverageTier;

	}

	public List<Object[]> getPayrollDeductionTableForPRD(
			PRDSettings prdSettings, DataHeader dataHeader) throws DAOException {
        BatchDetailService bds = new BatchDetailServiceImpl();
        BatchDetail bd = bds.getBatchDetail(prdSettings);
		List<Object[]> dataList = new ArrayList<>();
		String tableName = null;
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			if (bd == null) {
			    cstmt = session.connection().prepareCall(
					"{call sp_PRD_Data_IssuesOnly ?, ?, ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			    cstmt.setLong(1, prdSettings.getPrdSetupPK());
			    cstmt.setLong(2, dataHeader.getDataHeaderPK());
			    cstmt.setInt(3, 1);
			} else {
			    cstmt = session.connection().prepareCall(
					"{sp_PRD_Data_IssuesOnly_Batch ?, ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			    cstmt.setLong(1, bd.getBatchFK());
			    cstmt.setInt(2, 1);
			}
			boolean results = cstmt.execute();
			int rowsAffected = 0;

			// Protects against lack of SET NOCOUNT in stored procedure
			while (results || rowsAffected != -1) {
				if (results) {
					rs = cstmt.getResultSet();
					break;
				} else {
					rowsAffected = cstmt.getUpdateCount();
				}
				results = cstmt.getMoreResults();
			}
			while (rs.next()) {
				tableName = rs.getString(1);
			}

			// FileTemplateService fts = new FileTemplateServiceImpl();
			FileTemplate fileTemplate = prdSettings.getFileTemplate();
			List<FileTemplateField> fileTemplateFields = fileTemplate
					.getFileTemplateFields();
			// *** query temp table
			Connection connection = SessionHelper.getSession(
					SessionHelper.EDITSOLUTIONS).connection();

			String sqlCount = "select count(*) from " + tableName;
			PreparedStatement preparedStatement = connection
					.prepareStatement(sqlCount);
			ResultSet rsCount = preparedStatement.executeQuery();
			if (rsCount.next()) {
				dataHeader.setPrdCount(rsCount.getInt(1));
			}

			Double deductionTotal = 0.0;
			String sql = "select * from " + tableName;

			preparedStatement = connection
					.prepareStatement(sql);
			ResultSet rs2 = preparedStatement.executeQuery();
			boolean firstTimeThru = true;
			String[] titles = new String[fileTemplateFields.size()];
			while (rs2.next()) {
				Iterator<FileTemplateField> it = fileTemplateFields.iterator();
				int f = 0;
				String[] data = new String[fileTemplateFields.size()];

				while (it.hasNext()) {
					try {
						FileTemplateField fileTemplateField = it.next();


						if (fileTemplate.isIncludeTitles() && firstTimeThru) {
							String title = fileTemplateField.getFieldTitle();
							if (title == null) {
								title = "";
							}
							// For fixed length, make sure field is not longer
							// than specified field length.
							if (fileTemplate.getOutputTypeCT().equals("TXT")) {
								if (title.length() > fileTemplateField
										.getFieldLength()) {
									title = title
											.substring(0, fileTemplateField
													.getFieldLength() - 1);
								}
								// append spaces to reach specified field length
								// for fixed length file
								titles[f] = String.format("%1$-"
										+ fileTemplateField.getFieldLength()
										+ "s", title);
							} else {
								titles[f] = title.trim();
							}
						}

						String value = rs2.getString(fileTemplateField
								.getFieldTitle());
						if (value == null || value.trim().isEmpty()) {
							if (fileTemplateField.getDefaultValue() != null) {
								value = fileTemplateField.getDefaultValue();
							} else {
								value = "";
							}
						}
						// Get sum total of deductions
						if (fileTemplateField.getSourceField().getSqlFieldName().equals("DeductionAmount")) {
						    deductionTotal = deductionTotal + Double.parseDouble(value);
						}
						// For fixed length, make sure field is not longer than
						// specified field length.
						if (fileTemplate.getOutputTypeCT().equals("TXT")) {
							if (value.length() > fileTemplateField
									.getFieldLength()) {
								value = value.substring(0,
										fileTemplateField.getFieldLength() - 1);
							}
							// append spaces to reach specified field length for
							// fixed length file
							data[f] = String.format(
									"%1$-" + fileTemplateField.getFieldLength()
											+ "s", value);
						} else {
							data[f] = value.trim();
						}
						f++;
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
				if (fileTemplate.isIncludeTitles() && firstTimeThru) {
					dataList.add(titles);
					firstTimeThru = false;
				}
				dataList.add(data);
			}
			dataHeader.setDeductionTotal(deductionTotal);
			String dropSQL = "drop table " + tableName;
			preparedStatement = connection.prepareStatement(dropSQL);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return dataList;

	}

	// sp_prd_data_GroupByProduct 5000000000143, 'xxx-xx-xxxx'``
	public List<Object[]> getPayrollDeductionDataForPRD(Long prdSettingsPK,
			Long dataHeaderPK) throws DAOException {

		PRDSettingsService pss = new PRDSettingsServiceImpl();
		PRDSettings prdSettings = pss.getPRDSettings(prdSettingsPK);

		BatchService bs = new BatchServiceImpl();
        BatchDetailService bds = new BatchDetailServiceImpl();
        BatchDetail bd = bds.getBatchDetail(prdSettings);
		List<Object[]> dataList = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			if (bd == null) {
			    cstmt = session.connection().prepareCall(
					"{call sp_PRD_Data_IssuesOnly ?, ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			    cstmt.setLong(1, prdSettingsPK);
			    cstmt.setLong(2, dataHeaderPK);
			} else {
			    cstmt = session.connection().prepareCall(
					"{sp_PRD_Data_IssuesOnly_Batch ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			    cstmt.setLong(1, bd.getBatchFK());
			}
			boolean results = cstmt.execute();
			int rowsAffected = 0;

			// Protects against lack of SET NOCOUNT in stored procedure
			while (results || rowsAffected != -1) {
				if (results) {
					rs = cstmt.getResultSet();
					break;
				} else {
					rowsAffected = cstmt.getUpdateCount();
				}
				results = cstmt.getMoreResults();
			}

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			Object[] titleData = new Object[columns - 4];
			for (int c = 0; c < titleData.length; c++) {
				titleData[c] = metaData.getColumnName(c + 5);
			}
			dataList.add(titleData);

			Object[] data = null;
			while (rs.next()) {
				data = new Object[columns];
				for (int i = 5; i <= columns; i++) {
					Object value = rs.getObject(i);
					data[i - 5] = value;
				}
				dataList.add(data);
			}
			/*
			 * Object[] data = null; while (rs.next()) { data = new
			 * Object[columns - 4]; for (int i = 0; i <= columns; i++) { Object
			 * value = rs.getObject(i+5); data[i] = value; } dataList.add(data);
			 * }
			 */

		} catch (HibernateException | SQLException e) {
			throw new DAOException(e.toString());
		}

		return dataList;

	}

	public PayrollDeductionData insertPayrollDeductionData(
			PayrollDeductionData payrollDeductionData) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "insert into PRD_Data (PRD_DataHeaderFK, PRD_FileTemplateFieldPK, "
				+ "PRD_RecordFK, FieldValue, AddDate, AddUser, ModDate, ModUser) "
				+ " values (?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement
					.setLong(1, payrollDeductionData.getDataHeaderFK());
			preparedStatement.setLong(2,
					payrollDeductionData.getFileTemplateFieldFK());
			preparedStatement.setLong(3, payrollDeductionData.getRecordFK());
			preparedStatement
					.setString(4, payrollDeductionData.getFieldValue());
			preparedStatement.setDate(5, payrollDeductionData.getAddDate());
			preparedStatement.setString(6, payrollDeductionData.getAddUser());
			preparedStatement.setDate(7, payrollDeductionData.getModDate());
			preparedStatement.setString(8, payrollDeductionData.getModUser());
			/*
			 * if (payrollDeductionData.getDataIssueFK() != null) {
			 * preparedStatement.setLong(9,
			 * payrollDeductionData.getDataIssueFK()); } else {
			 * preparedStatement.setNull(9, java.sql.Types.BIGINT); }
			 */
			preparedStatement.executeUpdate();

			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				payrollDeductionData.setDataPK(generatedKeys.getLong(1));
			} else {
				throw new SQLException(
						"Data: Creating case failed, no ID obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return payrollDeductionData;
	}

	public PayrollDeductionData savePayrollDeductionData(String key,
			String value) throws DAOException {
		PayrollDeductionData payrollDeductionData = get(Long.valueOf(key));
		payrollDeductionData.setFieldValue(value);

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String updateSQL = "update PRD_Data "
				+ " set FieldValue = ? where PRD_DataPK = ?";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(updateSQL);
			preparedStatement
					.setString(1, payrollDeductionData.getFieldValue());
			preparedStatement.setLong(2, payrollDeductionData.getDataPK());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}

		return payrollDeductionData;
	}

	public void deletePayrollDeductionDatas(String[] recordFKs) throws DAOException {
		for (int i = 0; i < recordFKs.length; i++) {
		    deletePayrollDeductionData(recordFKs[i]);
		}
	}

	public void deletePayrollDeductionData(String recordFK) throws DAOException {
		DataIssuesService dis = new DataIssuesServiceImpl();

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String selectSql = "select PRD_DataPK from  PRD_Data where PRD_RecordFK = ?";

		String sql = "delete PRD_Data where PRD_RecordFK = ?";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(selectSql);
			preparedStatement.setLong(1, Long.valueOf(recordFK));
			ResultSet rSet = preparedStatement.executeQuery();
			while (rSet.next()) {
				Long dataFK = rSet.getLong(1);
				dis.deleteDataIssueFromDataFK(dataFK);
			}

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, Long.valueOf(recordFK));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}

	}

	public void deletePayrollDeductionDataFromDataHeader(DataHeader dataHeader)
			throws DAOException {
		DataIssuesService dis = new DataIssuesServiceImpl();
		dis.deleteDataIssuesFromDataHeader(dataHeader);

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_Data " + " where PRD_DataHeaderFK = ?";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setLong(1, dataHeader.getDataHeaderPK());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}

	}

	/*
	public void deletePayrollDeductionDatas(List<PayrollDeductionData> prdDatas)
			throws DAOException {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_Data " + " where PRD_DataPK = ?";
		try {
			Iterator<PayrollDeductionData> it = prdDatas.iterator();
			while (it.hasNext()) {
				PayrollDeductionData prdData = it.next();
				PreparedStatement preparedStatement = connection
						.prepareStatement(sql);
				preparedStatement.setLong(1, prdData.getDataPK());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}

	}
	*/

}
