package com.editsolutions.prd.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;

import staging.Staging;
import batch.business.Batch;

import com.editsolutions.prd.PayrollDeductionUtils;
import com.editsolutions.prd.service.AppSettingServiceImpl;
import com.editsolutions.prd.service.BatchDetailService;
import com.editsolutions.prd.service.BatchDetailServiceImpl;
import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.service.DataIssuesService;
import com.editsolutions.prd.service.DataIssuesServiceImpl;
import com.editsolutions.prd.service.DataService;
import com.editsolutions.prd.service.DataServiceImpl;
import com.editsolutions.prd.service.GroupSetupService;
import com.editsolutions.prd.service.GroupSetupServiceImpl;
import com.editsolutions.prd.service.ImportRecordService;
import com.editsolutions.prd.service.ImportRecordServiceImpl;
import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.temp.LoadGroups;
import com.editsolutions.prd.util.CorrespondenceUtils;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.util.PRDLoggingUtils;
import com.editsolutions.prd.util.SQLBuilderFactory;
import com.editsolutions.prd.vo.AppSetting;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.GroupSetup;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.PayrollDeductionData;
import com.editsolutions.prd.vo.SourceField;
import com.editsolutions.prd.vo.Transformation;

import edit.common.EDITDate;
import edit.services.EditServiceLocator;
import edit.services.db.hibernate.SessionHelper;

public class PRDCompareDao extends Dao<PRDSettings> {

	/*
	 * 1. runPRDCompare(EDITDate date) Kicks off compare process
	 * 
	 * 2. importPRDRecordsFromODS(Date extractDate) Iterate through each current PRD
	 * Settings, get appropriate SQL query and imports data from ODS into
	 * PRD_Records table (Work table). a. Standard-Product b. Standard EEID c.
	 * Non-Standard
	 * 
	 * 3. getPRDRecords(PRDSettings prdSettings, Date extractDate,String sql,
	 * Connection odsConnection, ImportRecordService importRecordService Do query
	 * and insert records into PRD_Records table.
	 * 
	 * 4. createDataRecords(PRDSettings prdSetting) Compares last and previous
	 * PRD_Records based on FileTemplate setup and inserts each field into the
	 * PRD_Data table.
	 * 
	 * 5. releasePRD(PRDSettings prdSettings, DataHeader dataHeader, Boolean
	 * isFinalRelease) If PRD is not on hold and there are no issues, create PRD
	 * file and stage for delivery.
	 */

	private float newTolerance;
	private float zeroTolerance;
	private java.sql.Date lastStandardPrdExtractDate;
	private List<String> standardPrdsToCompare = new ArrayList<>();

	public PRDCompareDao() {
		super(PRDSettings.class);
		HashMap<String, String> appSettings = new AppSettingServiceImpl().getAppSettingsHash();
		//newTolerance = Float.parseFloat(appSettings.get("NEW_RECORD_LIMIT")) * 100;
		//zeroTolerance = Float.parseFloat(appSettings.get("TERMINATION_LIMIT")) * 100;
		newTolerance = Float.parseFloat(".25") * 100;
		zeroTolerance = Float.parseFloat(".25") * 100;

		String lastExtractDate = "2021-01-19";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf1.parse(lastExtractDate);
		} catch (ParseException e) {
			System.out.println("parse exception");
			e.printStackTrace();
		}
		if (date != null) {
			lastStandardPrdExtractDate = new java.sql.Date(date.getTime());
		} else {
			System.out.println("lastStandardPrdExtratDate not populated");
		}

	}

	public void runPRDCompare(EDITDate date) {
		standardPrdsToCompare = getPrdsToBeCompared(new java.sql.Date(date.getTimeInMilliseconds()),
				lastStandardPrdExtractDate);
		importPRDRecordsFromODS(new Date(date.getTimeInMilliseconds()));
	}

	// TODO: Rename
	public void runPRDCompare(EDITDate date, String groupNumber) {
		if (groupNumber == null) {
			importPRDRecordsFromODS(new Date(date.getTimeInMilliseconds()));
		} else {
			importPRDRecordsFromODS(groupNumber);
		}

	}

	public void importPRDRecordsFromODS(final String groupNumber) {
		// add to standardPrdsToCompare so it doesn't get skipped.
		standardPrdsToCompare.add(groupNumber);
		PRDSettingsService prdSettingsService = new PRDSettingsServiceImpl();
		GroupSetupService gss = new GroupSetupServiceImpl();
		GroupSetup groupSetup = gss.getGroupSetupByNumber(groupNumber);
		String tempTableName = openPRDGroupImportTempTable(groupNumber);

		String sql = "select top 1 deductionDate from " + tempTableName + " where market_code = '" + groupNumber
				+ "' order by deductionDate desc ";
		Connection connection = SessionHelper.getSession(SessionHelper.ODS).connection();
		Statement statement;
		ResultSet resultSet;
		Date extractDate = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				extractDate = resultSet.getDate(1);
			}
		} catch (SQLException e) {
			System.out.println("TransformField error: " + e.toString());
			throw new DAOException(e.toString());
		}

		PRDSettings prdSettings = prdSettingsService.getByGroupSetupPK(groupSetup.getGroupContractPk());
		// prdSettings.setReportTypeCT("All");
		// temporarly setting extract dates to the latest date of records in ods
		// prdSettings.setNextPRDExtractDate(new
		// java.sql.Date(extractDate.getTime()));
		// prdSettings.setLastPRDExtractDate(new
		// java.sql.Date(extractDate.getTime()));
		List<PRDSettings> allPRDSettings = new ArrayList<>();
		allPRDSettings.add(prdSettings);

		doWork(allPRDSettings, tempTableName, extractDate, true);

	}

	public void importPRDRecordsFromODS(final Date extractDate) {
		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_COMPARE)
				.tagBatchStart(Batch.BATCH_JOB_RUN_PRD_COMPARE, "PRD Compare");

		PRDSettingsService prdSettingsService = new PRDSettingsServiceImpl();
		BatchDetailService batchDetailService = new BatchDetailServiceImpl();

		// BATCH PRDS
		List<PRDSettings> batchPRDSettings = batchDetailService
				.getBatchPRDSettings(new java.sql.Date(extractDate.getTime()));

		// NON VENUS - Standard - use nextPRDExtractDate
		List<PRDSettings> standardPRDSettings = prdSettingsService
				.getPRDSettingsByExtractDate(new java.sql.Date(extractDate.getTime()), "Standard");

		// VENUS - Standard - use lastPRDExtractDate
		standardPRDSettings.addAll(prdSettingsService
				.getPRDSettingsByLastExtractDate(new java.sql.Date(extractDate.getTime()), "Standard"));

		// NON VENUS - Custom - use nextPRDExtractDate
		List<PRDSettings> customPRDSettings = prdSettingsService
				.getPRDSettingsByExtractDate(new java.sql.Date(extractDate.getTime()), "Custom");

		// VENUS - Custom - use lastPRDExtractDate
		customPRDSettings.addAll(
				prdSettingsService.getPRDSettingsByLastExtractDate(new java.sql.Date(extractDate.getTime()), "Custom"));

		List<PRDSettings> allPRDSettings = new ArrayList<>();
		allPRDSettings.addAll(batchPRDSettings);
		allPRDSettings.addAll(standardPRDSettings);
		allPRDSettings.addAll(customPRDSettings);

		// create Temp table with PRD ODS data for performance
		String tempTableName = openPRDImportTempTable(extractDate);
		doWork(allPRDSettings, tempTableName, extractDate, false);
	}

	@SuppressWarnings("rawtypes")
	private void doWork(List<PRDSettings> allPRDSettings, String tempTableName, final Date extractDate,
			final boolean isTest) {

		int doCount = 0;
		int skipCount = 0;
		String sql = "";

		//int nThreads = (Runtime.getRuntime().availableProcessors() > 1)
		//		? (Runtime.getRuntime().availableProcessors() - 1)
		//		: 1;
		// ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		try {
		//	List<Future> futures = new ArrayList<Future>();

			// iterator through current PRD Settings
			Iterator<PRDSettings> it1 = allPRDSettings.iterator();
			while (it1.hasNext()) {
				PRDSettings prdSettings = it1.next();

				try {
					// Skip and log if not fileTemplate is assigned to the
					// PRDSettings.
					if (prdSettings.getFileTemplateFK() == null) {
						throw new Exception("No FileTemplate Assigned.");
					}

					// this shouldn't happen, but just in case, default to EEID
					if (prdSettings.getSummaryCT() == null) {
						prdSettings.setSummaryCT("EEID");
					}

					if (prdSettings.getTypeCT().equals("Standard")
							// && standardPrdsToCompare.contains(prdSettings.getGroupSetup()
							// .getGroupNumber().trim()))
							|| (prdSettings.getReportTypeCT().equals("All"))
							|| (prdSettings.getTypeCT().equals("Custom"))) {
						System.out.println("PRD is being processed.....");
						System.out.println(prdSettings.getGroupSetup().getGroupNumber() + " "
								+ prdSettings.getReportTypeCT() + " " + prdSettings.getTypeCT());
						doCount++;

						if (prdSettings.getSummaryCT().toUpperCase().equals("POLNBR")) {
							sql = SQLBuilderFactory.ALL_QUERY;
						} else if (!prdSettings.getFileTemplate().getName().startsWith("Standard")) {
							sql = SQLBuilderFactory.getEeidSQLForDynamicQuery(prdSettings);
						} else if (prdSettings.getSummaryCT() == null) {
							prdSettings.setSummaryCT("EEID");
						} else if (prdSettings.getSummaryCT().equals("EEID")) {
							sql = SQLBuilderFactory.EID_QUERY;
						} else { // DEFAULT to PRODUCT
									// (prdSettings.getSummaryCT().equals("Product"))
									// {
							sql = SQLBuilderFactory.PRODUCT_QUERY;
						}

						if (tempTableName != null) {
							sql = sql.replace("vw_PRD_Import", tempTableName);
						}
						final String finalSql = sql;
						final Long prdSettingsPK = prdSettings.getPrdSetupPK();

		//				Future<?> future = executor.submit(new Runnable() {
		//					public void run() {
								getPRDRecords(prdSettingsPK, extractDate, finalSql, isTest);
		//					}
		//				});
		//				futures.add(future);
					} else {
						System.out.println("PRD not being processed. Not in list of groups.");
						System.out.println(prdSettings.getGroupSetup().getGroupNumber() + " "
								+ prdSettings.getReportTypeCT() + " " + prdSettings.getTypeCT());
						skipCount++;
						advancePRDDates(prdSettings.getPrdSetupPK());
					}

				} catch (Exception e) {
					System.out.println("Main PRDCompare loop: " + e.toString());
					PRDLoggingUtils.logToDatabase(e, prdSettings, null, null);
					EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_COMPARE)
							.updateFailure();
					// then continue
				}

			}
			//executor.shutdown();
			//executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
			/*
			 * boolean threadsComplete = false; while (!threadsComplete) {
			 * 
			 * Iterator<Future> futuresIt = futures.iterator(); int nullCount = 0; while
			 * (futuresIt.hasNext()) { try { if (futuresIt.next().get() == null) {
			 * nullCount++; } } catch (InterruptedException | ExecutionException e) {
			 * e.printStackTrace(); } } if (nullCount == (futures.size())) { threadsComplete
			 * = true; } }
			 */
			closePRDImportTempTable(tempTableName);
			System.out.println("doCount: " + doCount);
			System.out.println("skipCount: " + skipCount);
			if (isTest) {
				ImportRecordService importRecordService = new ImportRecordServiceImpl();
				importRecordService.deleteTestRecords();
			} else {

				EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_COMPARE)
						.tagBatchStop();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			//if (executor != null) {
			//	executor.shutdownNow();
			//	executor = null;
			//}
		}

	}

	public List<String> getPrdsToBeCompared(Date nextPRDExtractDate, Date lastPRDExtractDate) {
		List<String> groupsToCompare = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.ODS);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			cstmt = session.connection().prepareCall("{call sp_PRD_GetMarketCodeList  ?, ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cstmt.setDate(1, new java.sql.Date(lastPRDExtractDate.getTime()));
			cstmt.setDate(2, new java.sql.Date(nextPRDExtractDate.getTime()));
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
			System.out.println("Groups to process: ");
			while (rs.next()) {
				//System.out.println(rs.getString(1));

				groupsToCompare.add(rs.getString(1).trim());
			}
		} catch (SQLException e) {
			System.out.println("getPrdsToBeCompared: " + e.toString());
		}

		return groupsToCompare;
	}

	public String openPRDGroupImportTempTable(String groupNumber) {
		String tableName = null;
		Session session = SessionHelper.getSession(SessionHelper.ODS);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			cstmt = session.connection().prepareCall("{call sp_PRD_tempImport_PHL_test_group ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cstmt.setString(1, groupNumber);
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
		} catch (SQLException e) {
			System.out.println("openPRDImportTempTable: " + e.toString());
		}

		return tableName;
	}

	public String openPRDImportTempTable(String groupNumber) {
		String tableName = null;
		Session session = SessionHelper.getSession(SessionHelper.ODS);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			cstmt = session.connection().prepareCall("{call sp_PRD_tempImport_PHL_test ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cstmt.setString(1, groupNumber);
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
		} catch (SQLException e) {
			System.out.println("openPRDImportTempTable: " + e.toString());
		}

		return tableName;
	}

	public String openPRDImportTempTable(Date extractDate) {
		String tableName = null;
		Session session = SessionHelper.getSession(SessionHelper.ODS);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			cstmt = session.connection().prepareCall("{call sp_PRD_tempImport_PHL_test ? }",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cstmt.setDate(1, new java.sql.Date(extractDate.getTime()));
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
		} catch (SQLException e) {
			System.out.println("openPRDImportTempTable: " + e.toString());
		}

		return tableName;
	}

	public void closePRDImportTempTable(String tableName) {
		Connection connection = SessionHelper.getSession(SessionHelper.ODS).connection();

		String dropSQL = "drop table " + tableName;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(dropSQL);
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("closePRDImportTempTable: " + e.toString());
		}

	}

	private void getPRDRecords(Long prdSettingsPK, Date extractDate, String sql, boolean isTest) {

		Session odsSession = SessionHelper.getSession(SessionHelper.ODS);
		final ImportRecordService importRecordService = new ImportRecordServiceImpl();
		final Connection odsConnection = odsSession.connection();
		PRDSettingsService prdSettingsService = new PRDSettingsServiceImpl();
		PRDSettings mainPrdSettings = prdSettingsService.getPRDSettings(prdSettingsPK);
		PreparedStatement preparedStatement;
		List<PRDSettings> prdSettingsList = new ArrayList<>();
		prdSettingsList.add(mainPrdSettings);
		if ((mainPrdSettings.getAdditionalPRDSettings() != null)
				&& (mainPrdSettings.getAdditionalPRDSettings().size() > 0)) {
			prdSettingsList.addAll(mainPrdSettings.getAdditionalPRDSettings());
		}

		Iterator<PRDSettings> it = prdSettingsList.iterator();
		while (it.hasNext()) {
			PRDSettings prdSettings = it.next();

			if (((prdSettings.getStatusCT() != null) && (!prdSettings.getStatusCT().equals("Inactive")))) {

				System.out.println("Group to extract: " + prdSettings.getGroupSetup().getGroupNumber());

				try {
					preparedStatement = odsConnection.prepareStatement(sql);

					if (preparedStatement.getParameterMetaData().getParameterCount() == 2) {
						if (mainPrdSettings.getSystemCT().equals("ES")) {
							if (isTest) {
								preparedStatement.setDate(1, new java.sql.Date(extractDate.getTime()));
							} else {
								preparedStatement.setDate(1, mainPrdSettings.getLastPRDExtractDate());
							}
						} else {
							preparedStatement.setDate(1, new java.sql.Date(extractDate.getTime()));
						}
						preparedStatement.setString(2, prdSettings.getGroupSetup().getGroupNumber());
					} else if ((preparedStatement.getParameterMetaData().getParameterCount() == 5)
							&& mainPrdSettings.getSummaryCT().equals("Product")) {
						if (mainPrdSettings.getSystemCT().equals("ES")) {
							if (isTest) {
								preparedStatement.setDate(1, new java.sql.Date(extractDate.getTime()));
							} else {
								preparedStatement.setDate(1, mainPrdSettings.getLastPRDExtractDate());
							}
							// System.out.println(
							// mainPrdSettings.getLastPRDExtractDate());
						} else {
							preparedStatement.setDate(1, new java.sql.Date(extractDate.getTime()));
							// System.out.println(new
							// java.sql.Date(extractDate.getTime()));
						}
						preparedStatement.setString(2, prdSettings.getGroupSetup().getGroupNumber());
						/*
						 * if ((prdSettings.getPrdExclusions() != null) &&
						 * (prdSettings.getPrdExclusions().size() > 0)) { preparedStatement.setString(3,
						 * prdSettings .getPrdExclusions().iterator().next() .getExclusionCode()); }
						 * else { preparedStatement.setString(3, "XXX"); }
						 */
						preparedStatement.setString(3, prdSettings.getGroupSetup().getGroupNumber());

						preparedStatement.setString(4, prdSettings.getGroupSetup().getGroupNumber());

						preparedStatement.setString(5, prdSettings.getGroupSetup().getGroupNumber());
						// System.out.println(prdSettings.getGroupSetup().getGroupNumber());
					} else if (preparedStatement.getParameterMetaData().getParameterCount() == 3) {
						if (mainPrdSettings.getSystemCT().equals("ES")) {
							if (isTest) {
								preparedStatement.setDate(1, new java.sql.Date(extractDate.getTime()));
							} else {
								preparedStatement.setDate(1, mainPrdSettings.getLastPRDExtractDate());
							}
						} else {
							preparedStatement.setDate(1, new java.sql.Date(extractDate.getTime()));
						}
						preparedStatement.setString(2, prdSettings.getGroupSetup().getGroupNumber());
						if ((prdSettings.getPrdExclusions() != null) && (prdSettings.getPrdExclusions().size() > 0)) {
							preparedStatement.setString(3,
									prdSettings.getPrdExclusions().iterator().next().getExclusionCode());
						} else {
							preparedStatement.setString(3, "XXX");
						}
					} else {
						preparedStatement.setString(1, prdSettings.getGroupSetup().getGroupNumber());
						if (prdSettings.getSystemCT().equals("ES")) {
							if (isTest) {
								preparedStatement.setDate(2, new java.sql.Date(extractDate.getTime()));
							} else {
								preparedStatement.setDate(2, mainPrdSettings.getLastPRDExtractDate());
							}
						} else {
							preparedStatement.setDate(2, new java.sql.Date( // TODO:
																			// relook
																			// at
																			// this.
																			// Should
																			// be
																			// using
																			// nextExtractDate?
									extractDate.getTime()));
						}
						preparedStatement.setString(3, prdSettings.getGroupSetup().getGroupNumber());

						if ((prdSettings.getPrdExclusions() != null) && (prdSettings.getPrdExclusions().size() > 0)) {
							preparedStatement.setString(4,
									prdSettings.getPrdExclusions().iterator().next().getExclusionCode());
						} else {
							preparedStatement.setString(4, "XXX");
						}
					}

					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {

						/*
						 * System.out.println(rs.getMetaData().getColumnName(1));
						 * System.out.println(rs.getMetaData().getColumnName(2));
						 * System.out.println(rs.getMetaData().getColumnName(3));
						 * System.out.println(rs.getMetaData().getColumnName(4));
						 * System.out.println(rs.getMetaData().getColumnName(5));
						 * System.out.println(rs.getMetaData().getColumnName(6));
						 */

						ImportRecord importRecord = new ImportRecord();
						if (isTest) {
							importRecord.setTest(true);
						}
						try {
							importRecord.setAccountNumber(rs.getString("MARKET_CODE"));
						} catch (SQLException e) {
						}
						try {
							importRecord.setGroupNumber(rs.getString("MARKET_CODE"));
						} catch (SQLException e) {
						}
						try {
							importRecord.setDeductionDate(rs.getDate("DeductionDate"));
						} catch (SQLException e) {
						}
						try {
							importRecord.setEmployeeID(rs.getString("EMPLOYEE_ID"));
						} catch (SQLException e) {
						}
						try {
							importRecord.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
							//System.out.println(rs.getString("EMPLOYEE_NAME"));
						} catch (SQLException e) {
						}
						try {
							if (rs.getString("EMPLOYEE_FNAME") != null) {
								importRecord.setEmployeeFirstName(rs.getString("EMPLOYEE_FNAME"));
							} else {
								importRecord.setEmployeeFirstName("");
							}
						} catch (SQLException e) {
						}
						try {
							if (rs.getString("EMPLOYEE_LNAME") != null) {
								importRecord.setEmployeeLastName(rs.getString("EMPLOYEE_LNAME"));
							} else {
								importRecord.setEmployeeLastName("");
							}
						} catch (SQLException e) {
						}
						try {
							importRecord.setDeductionAmount(rs.getDouble("NEW_DEDUCTION_AMOUNT"));
						} catch (SQLException e) {
						}
						try {
							importRecord.setPolicyCoverageEffectiveDate(rs.getDate("PHL_EffectiveDate"));
						} catch (SQLException e) {
						}

						try {
							importRecord.setPolicyCoverageEffectiveDate(rs.getDate("POLICY_COV_EFF_DATE"));
						} catch (SQLException e) {
						}

						try {
							if (rs.getString("SSN") != null) {
								importRecord.setSsn(rs.getString("SSN"));
							} else {
								importRecord.setSsn("");
							}
						} catch (SQLException e) {
						}
						try {
							if (rs.getString("BILLING_FREQUENCY") != null) {
								importRecord.setBillingFrequency(rs.getInt("BILLING_FREQUENCY"));
							}
						} catch (SQLException e) {
						}

						if (prdSettings.getSummaryCT().equals("Product")
								|| prdSettings.getSummaryCT().equals("PolNbr")) {
							try {
								importRecord.setProductType(rs.getString("PRODUCT_TYPE"));
							} catch (SQLException e) {
							}
							// try {
							// importRecord.setPolicyProductCode("TEST"); //
							// .getString("PHL_ProductCode"));
							// .getString("PHL_ProductCode"));
							// } catch (SQLException e) {
							// }

						}
						if (prdSettings.getSummaryCT().equals("PolNbr")
								|| (!prdSettings.getFileTemplate().getName().startsWith("Standard"))) {

							try {
								if (rs.getString("PHL_InsuredFirstName") != null) {
									importRecord.setInsuredFirstName(rs.getString("PHL_InsuredFirstName"));
								} else {
									importRecord.setInsuredFirstName("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_CurrentFaceAmount") != null) {
									importRecord.setCurrentFaceAmount(rs.getDouble("PHL_CurrentFaceAmount"));
								} else {
									importRecord.setCurrentFaceAmount(null);
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("CURRENT_AGE") != null) {
									importRecord.setCurrentAge(rs.getInt("CURRENT_AGE"));
								} else {
									importRecord.setCurrentAge(null);
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_InsuredLastName") != null) {
									importRecord.setInsuredLastName(rs.getString("PHL_InsuredLastName"));
								} else {
									importRecord.setInsuredLastName("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_InsuredMiddle") != null) {
									importRecord.setInsuredMI(rs.getString("PHL_InsuredMiddle"));
								} else {
									importRecord.setInsuredMI("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getDate("PHL_DateOfBirth") != null) {
									importRecord.setInsuredDOB(rs.getDate("PHL_DateOfBirth"));
								} else {
									importRecord.setInsuredDOB(null);
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_BillMode") != null) {
									importRecord.setBillMode(rs.getString("PHL_BillMode"));
								} else {
									importRecord.setBillMode("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_ModePremium") != null) {
									importRecord.setModePremium(rs.getDouble("PHL_ModePremium"));
								} else {
									importRecord.setBillMode("");
								}
							} catch (SQLException e) {
							}
							try {
								importRecord.setIssueAge(rs.getInt("PHL_IssueAge"));
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("NEW_BILLABLE_PREMIUM") != null) {
									importRecord.setBilliblePremium(rs.getDouble("NEW_BILLABLE_PREMIUM"));
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("BILLING_COMPANY") != null) {
									importRecord.setBillingCompany(rs.getString("BILLING_COMPANY"));
								} else {
									importRecord.setBillingCompany("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("POLICY_NUMBER") != null) {
									importRecord.setPolicyNumber(rs.getString("POLICY_NUMBER"));
								} else {
									importRecord.setPolicyNumber("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("SYSTEM") != null) {
									importRecord.setSystem(rs.getString("SYSTEM"));
								} else {
									importRecord.setSystem("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("STATUS") != null) {
									importRecord.setStatus(rs.getString("STATUS"));
								} else {
									importRecord.setStatus("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_PolicyStatus") != null) {
									importRecord.setPolicyStatus(rs.getString("PHL_PolicyStatus"));
								} else {
									importRecord.setPolicyStatus("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_ApplicationDate") != null) {
									importRecord.setApplicationDate(rs.getDate("PHL_ApplicationDate"));
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_TerminationDate") != null) {
									importRecord.setTerminationDate(rs.getDate("PHL_TerminationDate"));
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_ResidentState") != null) {
									importRecord.setState(rs.getString("PHL_ResidentState"));
								} else {
									importRecord.setState("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("INSURED_NAME") != null) {
									importRecord.setInsuredName(rs.getString("INSURED_NAME"));
								} else {
									importRecord.setInsuredName("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("RELATIONSHIP") != null) {
									importRecord.setRelationshipCode(rs.getString("RELATIONSHIP"));
								} else {
									importRecord.setRelationshipCode("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("DEPARTMENT_ID") != null) {
									importRecord.setDepartmentLocation(rs.getString("DEPARTMENT_ID"));
								} else {
									importRecord.setDepartmentLocation("");
								}
							} catch (SQLException e) {
							}

							try {
								if (rs.getString("EMPLOYEE_MI") != null) {
									importRecord.setEmployeeMI(rs.getString("EMPLOYEE_MI"));
								} else {
									importRecord.setEmployeeMI("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("CorporateName") != null) {
									importRecord.setEmployerName(rs.getString("CorporateName"));
								} else {
									importRecord.setEmployerName("");
								}
							} catch (SQLException e) {
							}

							try {
								if (rs.getString("InsuredGender") != null) {
									importRecord.setInsuredGender(rs.getString("InsuredGender"));
								} else {
									importRecord.setInsuredGender("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_Address") != null) {
									importRecord.setStreet1(rs.getString("PHL_Address"));
								} else {
									importRecord.setStreet1("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_Address2") != null) {
									importRecord.setStreet2(rs.getString("PHL_Address2"));
								} else {
									importRecord.setStreet2("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_City") != null) {
									importRecord.setCity(rs.getString("PHL_City"));
								} else {
									importRecord.setCity("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("PHL_Zip") != null) {
									importRecord.setZip(rs.getString("PHL_Zip"));
								} else {
									importRecord.setZip("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("Smoker") != null) {
									importRecord.setSmoker(rs.getString("Smoker"));
								} else {
									importRecord.setSmoker("");
								}
							} catch (SQLException e) {
							}

							try {
								if (rs.getString("BillingForm") != null) {
									importRecord.setBillingForm(rs.getString("BillingForm"));
								} else {
									importRecord.setBillingForm("");
								}
							} catch (SQLException e) {
							}

							try {
								if (rs.getString("AblacInd") != null) {
									importRecord.setAblacInd(rs.getString("AblacInd"));
								} else {
									importRecord.setAblacInd("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("ChangeType") != null) {
									importRecord.setChangeType(rs.getString("ChangeType"));
								} else {
									importRecord.setChangeType("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("LntCode") != null) {
									importRecord.setLntCode(rs.getString("LntCode"));
								} else {
									importRecord.setLntCode("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getString("InstypeShortLabel") != null) {
									importRecord.setInstypeShortLabel(rs.getString("InstypeShortLabel"));
								} else {
									importRecord.setInstypeShortLabel("");
								}
							} catch (SQLException e) {
							}
							try {
								if (rs.getDate("PolicyEffectiveDate") != null) {
									importRecord.setPolicyEffectiveDate(rs.getDate("PolicyEffectiveDate"));
								} else {
									importRecord.setInstypeShortLabel("");
								}
							} catch (SQLException e) {
							}

						}

						// add deductionDate for those PRDs that don't require
						// them
						if (importRecord.getDeductionDate() == null) {
							importRecord.setDeductionDate(new java.sql.Date(extractDate.getTime()));
						}
						// add groupNumber for those PRDs that don't require
						// them
						if (importRecord.getGroupNumber() == null) {
							importRecord.setGroupNumber(prdSettings.getGroupSetup().getGroupNumber());
						}
						importRecord.setFILLER("");
						importRecordService.saveImportRecord(importRecord);
					}

				} catch (SQLException e) {
					System.out.println("getPRDRecords: " + e.toString());
					EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_COMPARE)
							.updateFailure();
				}
			}
		}
		if ((mainPrdSettings.getStatusCT() != null) && (mainPrdSettings.getStatusCT().equals("Active"))) {
			if (isTest) {
				mainPrdSettings.setReportTypeCT("All");
				mainPrdSettings.setNextPRDExtractDate(new java.sql.Date(extractDate.getTime()));
				mainPrdSettings.setLastPRDExtractDate(new java.sql.Date(extractDate.getTime()));
				mainPrdSettings.setNextPRDDueDate(new java.sql.Date(extractDate.getTime()));
				mainPrdSettings.setHoldForReview(false);
			}
			createDataRecords(mainPrdSettings, prdSettingsList, isTest);
			// delete test records so they don't interfere with production PRDs
		} else {
			System.out.println("mainPrdSettings.getStatusCT(): " + mainPrdSettings.getStatusCT());
		}
	}

	public void createDataRecords(PRDSettings mainPrdSettings, List<PRDSettings> prdSettingsList, boolean isTest) {

		boolean isBatch = false;
		DataHeader dataHeader = new DataHeader();
		ImportRecord lastImportRecord = null;
		ImportRecord currentImportRecord = null;
		FileTemplate fileTemplate = mainPrdSettings.getFileTemplate();
		if (fileTemplate == null) {
			// LOG and break
			return;
		}
		if (prdSettingsList.size() > 1) {
			isBatch = true;
		}
		List<FileTemplateField> fileTemplateFields = fileTemplate.getFileTemplateFields();

		List<ImportRecord> importRecords = new ArrayList<>();

		// 1 a
		int batchCount = 0;
		ImportRecordService irs = new ImportRecordServiceImpl();
		Iterator<PRDSettings> it = prdSettingsList.iterator();
		while (it.hasNext()) {
			PRDSettings prdSettings = it.next();

			// increment running count
			PRDSettingsService prdSettingsService = new PRDSettingsServiceImpl();
			prdSettings.setRunningCount(prdSettings.getRunningCount() + 1);
			if (!isTest) {
			    prdSettingsService.savePRDSettings(prdSettings);
			}

			batchCount++;
			if (mainPrdSettings.getSummaryCT().equals("Product")) {
				List<ImportRecord> importRecordsByProduct = irs.getImportRecordsForProductQuery(prdSettings);

				if ((importRecordsByProduct.size() > 0) && (!isBatch || batchCount == 1)) {
					dataHeader = createDataHeader(mainPrdSettings, dataHeader, isTest);
				}

				Iterator<ImportRecord> it1 = importRecordsByProduct.iterator();
				Iterator<ImportRecord> it2 = null;
				while (it1.hasNext()) {
					ImportRecord importRecordByProduct = it1.next();
					importRecords = irs.getImportRecordsByProduct(importRecordByProduct, prdSettings);
					it2 = importRecords.iterator();
					int i = 0;
					while (it2.hasNext()) {
						if (i < 2) {
							if (i == 0) {
								currentImportRecord = it2.next();
							} else {
								lastImportRecord = it2.next();
								// ECK
							}
						} else {
							break;
						}
						i++;
					}

					// if (isDifferent(currentImportRecord, lastImportRecord)) {

					Iterator<FileTemplateField> it4 = fileTemplateFields.iterator();
					HashMap<String, FileTemplateField> fieldHashMap = new HashMap<>();
					while (it4.hasNext()) {
						FileTemplateField fileTemplateField = it4.next();
						fieldHashMap.put(fileTemplateField.getSourceField().getJavaFieldName(), fileTemplateField);
					}
					try {
						compareDataRecords(lastImportRecord, currentImportRecord, fieldHashMap, dataHeader,
								prdSettings);
						lastImportRecord = null;
						currentImportRecord = null;
						EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_COMPARE)
								.updateSuccess();
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}
					// }

				}

			} else if (mainPrdSettings.getSummaryCT().equals("EEID")) {
				List<String> employeeIDs = irs.getEmployeeIDByGroup(prdSettings);
				if ((employeeIDs.size() > 0) && (!isBatch || batchCount == 1)) {
					dataHeader = createDataHeader(mainPrdSettings, dataHeader, isTest);
				}
				// else {
				// advance even if no weekly data for PRDSettings
				// advancePRDDates(prdSettings);
				// }
				Iterator<String> it1 = employeeIDs.iterator();
				Iterator<ImportRecord> it2 = null;
				while (it1.hasNext()) {
					String employeeID = it1.next();
					importRecords = irs.getImportRecordsByEmployeeID(employeeID, prdSettings);
					it2 = importRecords.iterator();
					int i = 0;
					while (it2.hasNext()) {
						if (i < 2) {
							if (i == 0) {
								currentImportRecord = it2.next();
							} else {
								lastImportRecord = it2.next();
							}
						} else {
							break;
						}
						i++;
					}
//					if (isDifferent(currentImportRecord, lastImportRecord)) {

						Iterator<FileTemplateField> it4 = fileTemplateFields.iterator();
						HashMap<String, FileTemplateField> fieldHashMap = new HashMap<>();
						while (it4.hasNext()) {
							FileTemplateField fileTemplateField = it4.next();
							fieldHashMap.put(fileTemplateField.getSourceField().getJavaFieldName(), fileTemplateField);
						}
						try {
							compareDataRecords(lastImportRecord, currentImportRecord, fieldHashMap, dataHeader,
									prdSettings);
							lastImportRecord = null;
							currentImportRecord = null;
							EditServiceLocator.getSingleton().getBatchAgent()
									.getBatchStat(Batch.BATCH_JOB_RUN_PRD_COMPARE).updateSuccess();
						} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
							e.printStackTrace();
						}
//					}

				}
			} else if (mainPrdSettings.getSummaryCT().equals("PolNbr")) {
				List<String> policyNumbers = irs.getPolicyNumberByGroup(prdSettings);

				if ((policyNumbers.size() > 0) && (!isBatch || batchCount == 1)) {
					dataHeader = createDataHeader(mainPrdSettings, dataHeader, isTest);
				}

				Iterator<String> it1 = policyNumbers.iterator();
				Iterator<ImportRecord> it2 = null;
				while (it1.hasNext()) {
					String policyNumber = it1.next();
					importRecords = irs.getImportRecordsByPolicyNumber(policyNumber, prdSettings);
					it2 = importRecords.iterator();
					int i = 0;
					while (it2.hasNext()) {
						if (i < 2) {
							if (i == 0) {
								currentImportRecord = it2.next();
							} else {
								lastImportRecord = it2.next();
							}
						} else {
							break;
						}
						i++;
					}
					Iterator<FileTemplateField> it4 = fileTemplateFields.iterator();
					HashMap<String, FileTemplateField> fieldHashMap = new HashMap<>();
					while (it4.hasNext()) {
						FileTemplateField fileTemplateField = it4.next();
						fieldHashMap.put(fileTemplateField.getSourceField().getJavaFieldName(), fileTemplateField);
					}
					try {
						compareDataRecords(lastImportRecord, currentImportRecord, fieldHashMap, dataHeader,
								prdSettings);
						lastImportRecord = null;
						currentImportRecord = null;
						EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_COMPARE)
								.updateSuccess();
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}

				}

			}
		}

		if (dataHeader.getDataHeaderPK() != null) {
			if (((dataHeader.getDataIssues() != null) && (dataHeader.getDataIssues().size() > 0)) || isTest) {
				dataHeader.setStatusCT(DataHeader.HAS_ISSUES);
				System.out.println("HAS ISSUES");
				if (isTest) {
					dataHeader.setTestPrd(isTest);
				}
		        dataHeader = new DataHeaderServiceImpl().updateDataHeader(dataHeader);

				if (!isTest) {
					releasePRD(mainPrdSettings, dataHeader, false, false);
				}
			} else {
				dataHeader.setStatusCT(DataHeader.NO_CHANGES);
				System.out.println("NO ISSUES");
		        dataHeader = new DataHeaderServiceImpl().updateDataHeader(dataHeader);
			}
		}  else {
			dataHeader.setStatusCT(DataHeader.NO_CHANGES);
			System.out.println("NO ISSUES - PRD_DataHeader not saved.");
		}

		if (!isTest) {
			advancePRDDates(mainPrdSettings.getPrdSetupPK());
		}
	}

	private boolean isDifferent(ImportRecord lastImportRecord, ImportRecord currentImportRecord) {
		lastImportRecord.setDeductionDate(null);
		currentImportRecord.setDeductionDate(null);
		lastImportRecord.setImportRecordPK(null);
		currentImportRecord.setImportRecordPK(null);
		if (lastImportRecord.equals(currentImportRecord)) {
			return false;
		}
		return true;
	}

	public void compareDataRecords(Object oldRecord, Object newRecord, HashMap<String, FileTemplateField> fieldHashMap,
			DataHeader dataHeader, PRDSettings prdSettings)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		// Use newRecord. Old not always available
		BeanMap map = new BeanMap(newRecord);

		PropertyUtilsBean propUtils = new PropertyUtilsBean();
		DataService dataService = new DataServiceImpl();
		DataIssuesService dataIssuesService = new DataIssuesServiceImpl();
		DataIssue dataIssue;
		String changeReason = "";
		String ownerSSN = null;
		boolean changeReasonFieldNeeded = false;
		boolean transactionControlNumberFieldNeeded = false;
		boolean trustmarkChangeReasonFieldNeeded = false;
		boolean coverageTierFieldNeeded = false;
		PayrollDeductionData changeReasonPayrollDeductionData = null;
		PayrollDeductionData transactionControlNumberData = null;
		PayrollDeductionData trustmarkChangeReasonPayrollDeductionData = null;
		PayrollDeductionData coverageTierPayrollDeductionData = null;
		boolean isNewRecord = false;

		for (Object propNameObject : map.keySet()) {
			dataIssue = null;
			String propertyName = (String) propNameObject;
			FileTemplateField ftField = null;

			// This is a 'special' field that is NOT in the record. Only create
			// it if there is a FileTemplateField for 'changeReason'
			if (propertyName.equals("changeReason")) {
				ftField = fieldHashMap.get(propertyName);
				if (ftField != null) {
					changeReasonFieldNeeded = true;
					changeReasonPayrollDeductionData = new PayrollDeductionData();
					changeReasonPayrollDeductionData.setDataHeaderFK(dataHeader.getDataHeaderPK());
					changeReasonPayrollDeductionData.setFileTemplateFieldFK(ftField.getFileTemplateFieldPK());
					changeReasonPayrollDeductionData.setRecordFK(((ImportRecord) newRecord).getImportRecordPK());
					changeReasonPayrollDeductionData.setFileTemplateField(ftField);
					Transformation t = ftField.getTransformation();
				//	if (t != null) {
				//	    changeReasonPayrollDeductionData.getFileTemplateField().setTransformation(ftField.getTransformation());
				//	    String transformedField = transformField(ftField, ((ImportRecord) newRecord).getImportRecordPK());
				//	    changeReasonPayrollDeductionData.setFieldValue(transformedField);
				//	}
					
				}
				/*
				 * } else if (propertyName.equals("transactionControlNumber")) { ftField =
				 * fieldHashMap.get(propertyName); if (ftField != null) {
				 * transactionControlNumberFieldNeeded = true; transactionControlNumberData =
				 * new PayrollDeductionData(); transactionControlNumberData
				 * .setDataHeaderFK(dataHeader.getDataHeaderPK()); transactionControlNumberData
				 * .setFileTemplateFieldFK(ftField .getFileTemplateFieldPK());
				 * transactionControlNumberData .setRecordFK(((ImportRecord) newRecord)
				 * .getImportRecordPK()); }
				 */
			} else if (propertyName.equals("trustmarkChangeReason")) {
				ftField = fieldHashMap.get(propertyName);
				if (ftField != null) {
					trustmarkChangeReasonFieldNeeded = true;
					trustmarkChangeReasonPayrollDeductionData = new PayrollDeductionData();
					trustmarkChangeReasonPayrollDeductionData.setDataHeaderFK(dataHeader.getDataHeaderPK());
					trustmarkChangeReasonPayrollDeductionData.setFileTemplateFieldFK(ftField.getFileTemplateFieldPK());
					trustmarkChangeReasonPayrollDeductionData
							.setRecordFK(((ImportRecord) newRecord).getImportRecordPK());
				}
			} else if (propertyName.equals("coverageTier")) {
				ftField = fieldHashMap.get(propertyName);
				if (ftField != null) {
					coverageTierFieldNeeded = true;
					coverageTierPayrollDeductionData = new PayrollDeductionData();
					coverageTierPayrollDeductionData.setDataHeaderFK(dataHeader.getDataHeaderPK());
					coverageTierPayrollDeductionData.setFileTemplateFieldFK(ftField.getFileTemplateFieldPK());
					coverageTierPayrollDeductionData.setRecordFK(((ImportRecord) newRecord).getImportRecordPK());
				}
			} else if (fieldHashMap.get(propertyName) != null) {
				ftField = fieldHashMap.get(propertyName);
				PayrollDeductionData prdData = new PayrollDeductionData();
				prdData.setDataHeaderFK(dataHeader.getDataHeaderPK());
				prdData.setFileTemplateFieldFK(ftField.getFileTemplateFieldPK());

				// Create Data
				try {
					Object o = propUtils.getProperty(newRecord, propertyName);
					if (o != null) {
						prdData.setFieldValue(propUtils.getProperty(newRecord, propertyName).toString());
						// need ssn for coverageTier
						if (propertyName.equals("ssn")) {
							ownerSSN = prdData.getFieldValue();
						}
					} else {
						prdData.setFieldValue("");
					}
				} catch (Exception e) {
					prdData.setFieldValue(DataIssue.ERROR);
				}
				prdData.setRecordFK(((ImportRecord) newRecord).getImportRecordPK());

				if (oldRecord == null) {
					// only create dataIssue for a field that are being
					// compared. Should only be one DataIssue per DataHeader
					// should change dataHeader.getDataIssues() list to single
					// DataIssue.
					if (!isNewRecord) { // &&
										// dataHeader.getDataIssues().isEmpty())
										// {
						dataIssue = new DataIssue();
						dataIssue.setDataHeaderFK(prdData.getDataHeaderFK());
						dataIssue.setIsResolved(false);
						dataIssue.setIssueLookupCT(DataIssue.NEW);
						dataHeader.getDataIssues().add(dataIssue);
						changeReason = DataIssue.NEW;
						isNewRecord = true;
					}
				} else {
					if (ftField.isDoCompare()) {
						if (fieldHashMap.containsKey(propertyName)) {

							Object property1 = "";
							Object property2 = "";

							if (propUtils.getProperty(oldRecord, propertyName) != null) {
								property1 = propUtils.getProperty(oldRecord, propertyName);
							}
							if (propUtils.getProperty(newRecord, propertyName) != null) {
								property2 = propUtils.getProperty(newRecord, propertyName);
							}
							if (!property1.equals(property2)) {

								// There is a difference
								dataIssue = new DataIssue();
								dataIssue.setDataHeaderFK(prdData.getDataHeaderFK());
								dataIssue.setIsResolved(false);
								if (propertyName.equals("deductionAmount") && ((Double) property2 == 0.0)) {
									dataIssue.setIssueLookupCT(DataIssue.TERMINATED);
									changeReason = DataIssue.TERMINATED;
								} else {
									dataIssue.setIssueLookupCT(DataIssue.CHANGE);
									changeReason = DataIssue.CHANGE;
								}
								dataHeader.getDataIssues().add(dataIssue);
							} else {
								// if reportTypeCT is 'ALL' need to show all
								// records. Create UNCHANGED Issue to force
								// reporting.
								if ((prdSettings.getReportTypeCT() != null)
										&& prdSettings.getReportTypeCT().equals("All")
										&& !prdSettings.getSystemCT().equals("ES")) {
									dataIssue = new DataIssue();
									dataIssue.setDataHeaderFK(prdData.getDataHeaderFK());
									dataIssue.setIsResolved(false);
									dataIssue.setIssueLookupCT(DataIssue.UNCHANGED);
									dataHeader.getDataIssues().add(dataIssue);
								}
							}
						}
					}

				}

				// Attach transformation, if one exists, to field.
				if (ftField.getTransformation() != null) {
					String transformedField = transformField(ftField, ((ImportRecord) newRecord).getImportRecordPK());
					prdData.setFieldValue(transformedField);
				}

				dataService.insertPayrollDeductionData(prdData);
				if ((dataHeader.getDataIssues() != null) || (dataHeader.getDataIssues().size() > 0)) {
					if (dataIssue != null) {
						dataIssue.setDataFK(prdData.getDataPK());
						dataIssue = dataIssuesService.saveDataIssue(dataIssue);
					}
				}
			}
		}
		if (coverageTierFieldNeeded) {
			if (ownerSSN != null) {
				coverageTierPayrollDeductionData.setFieldValue(dataService.getCoverageTier(ownerSSN));
				dataService.insertPayrollDeductionData(coverageTierPayrollDeductionData);
			}
		}

		if (transactionControlNumberFieldNeeded) {
			if (ownerSSN != null) {
				dataService.insertPayrollDeductionData(transactionControlNumberData);
			}
		}

		// I'd like to do this dynamically without hard coding, but don't have
		// time right now.
		if (trustmarkChangeReasonFieldNeeded) {
			String trustmarkChangeReason = changeReason;
			if (changeReason.equals("T")) {
				trustmarkChangeReason = "D";
			} else if (changeReason.equals("N")) {
				trustmarkChangeReason = "A";
			}
			trustmarkChangeReasonPayrollDeductionData.setFieldValue(trustmarkChangeReason);
			dataService.insertPayrollDeductionData(trustmarkChangeReasonPayrollDeductionData);
		}
		if (changeReasonFieldNeeded) {
			changeReasonPayrollDeductionData.setFieldValue(changeReason);
			FileTemplateField f = changeReasonPayrollDeductionData.getFileTemplateField();
			Transformation t = f.getTransformation();
			if (t != null) {
		        changeReasonPayrollDeductionData.getFileTemplateField().setTransformation(t);
		        String transformedField = transformField(f, changeReason);
		        changeReasonPayrollDeductionData.setFieldValue(transformedField);
			}
			//here
			dataService.insertPayrollDeductionData(changeReasonPayrollDeductionData);
		}

	}

	public void releaseAllPRDs(List<DataHeader> dataHeaders) {
		Iterator<DataHeader> it = dataHeaders.iterator();
		while (it.hasNext()) {
			DataHeader dataHeader = it.next();
			PRDSettings prdSettings = dataHeader.getPrdSettings();
			releasePRD(prdSettings, dataHeader, true, false);
		}
	}

	public void createTestTextExtract(FileTemplate fileTemplate, String groupNumber) throws DAOException {
		runPRDCompare(null, groupNumber);
	}

	public void manuallyReleasePRD(PRDSettings prdSettings, DataHeader dataHeader) {
		DataHeaderService dataHeaderService = new DataHeaderServiceImpl();
		// refresh dataHeader
		dataHeader = dataHeaderService.getDataHeaderWithUnresolvedIssues(dataHeader.getDataHeaderPK());
		dataHeader.setStatusCT(DataHeader.RELEASED);
		dataHeaderService.updateDataHeader(dataHeader);
		CorrespondenceUtils.createCorrespondence(prdSettings, dataHeader, "C");
		// advancePRDDates(prdSettings);
		System.out.println("Group: " + prdSettings.getGroupSetup().getGroupNumber());
	}

	public void releasePRD(PRDSettings prdSettings, DataHeader dataHeader, boolean isFinalRelease,
			boolean ignoreTolerances) {

		// get a fresh copy of prdSettings
		// PRDSettingsService prdSettingsService = new PRDSettingsServiceImpl();
		// prdSettings = prdSettingsService.getPRDSettings(prdSettings.getPrdSetupPK());

		DataHeaderService dataHeaderService = new DataHeaderServiceImpl();
		dataHeader = dataHeaderService.getDataHeaderWithUnresolvedIssues(dataHeader.getDataHeaderPK());
//		if (isFinalRelease) {
//			dataHeader.setStatusCT(DataHeader.PENDING);
//		}
		System.out.println("Group: " + prdSettings.getGroupSetup().getGroupNumber());
		// If PRD is not on hold and no issues then do nothing but advance
		// non-VENUS PRD .

		float totalRecords = new Float(dataHeaderService.getPRDCount(dataHeader.getDataHeaderPK()));
		float totalNews = new Float(dataHeaderService.getCountByIssueLookupCT(dataHeader.getDataHeaderPK(), "A"));
		float totalZeros = new Float(dataHeaderService.getCountByIssueLookupCT(dataHeader.getDataHeaderPK(), "T"));
		float newRatio = totalNews / totalRecords * new Float(100.00);
		float zeroRatio = totalZeros / totalRecords * new Float(100.00);
		if (newRatio > newTolerance) {
			prdSettings.setHoldForReview(true);
			if (!ignoreTolerances) {
				isFinalRelease = false;
			}
		}
		if (zeroRatio > zeroTolerance) {
			prdSettings.setHoldForReview(true);
			if (!ignoreTolerances) {
				isFinalRelease = false;
			}
		}

		if ((dataHeader.getUnresolvedDataIssues() == null)
				|| (dataHeader.getUnresolvedDataIssues().size() == 0) && !isFinalRelease) {

			dataHeader.setStatusCT(DataHeader.NO_CHANGES);
			dataHeaderService.updateDataHeader(dataHeader);

		} else {
			if (isFinalRelease || !prdSettings.isHoldForReview()) {
				System.out.println("NOT ON HOLD");
				dataHeader.setStatusCT(DataHeader.RELEASED);
				dataHeaderService.updateDataHeader(dataHeader);
				if (prdSettings.getStatusCT().equals("Active")) {
					System.out.println("creating correspondence.....");
					CorrespondenceUtils.createCorrespondence(prdSettings, dataHeader, "P");
				}
			} else {
				System.out.println("ON HOLD");
			}
		}
		System.out.println("Finished Group: " + prdSettings.getGroupSetup().getGroupNumber());
	}

	private void advancePRDDates(Long prdSettingsPK) {
		boolean isBatch = false;
		PRDSettingsService prdSettingsService = new PRDSettingsServiceImpl();
		PRDSettings prdSettings = prdSettingsService.getPRDSettings(prdSettingsPK);
		if (!prdSettings.getSystemCT().equals("ES")) {

			List<PRDSettings> prdSettingsList = new ArrayList<>();

			// check if batch
			if ((prdSettings.getAdditionalPRDSettings() != null)
					&& (prdSettings.getAdditionalPRDSettings().size() > 0)) {
				prdSettingsList.addAll(prdSettings.getAdditionalPRDSettings());
				isBatch = true;
			}

			if (prdSettings.getTypeCT().equals("Standard")) {
				prdSettings = PayrollDeductionUtils.updatePRDExtractDates(prdSettings,
						DateUtils.addDays(prdSettings.getNextPRDDueDate(), 7));
			} else if (prdSettings.getPrdSetupPK() != null) {
				prdSettings = PayrollDeductionUtils.updatePRDCustomExtractDates(prdSettings);
			}
			prdSettingsService.savePRDSettings(prdSettings);

			if (isBatch) {
				Iterator<PRDSettings> it = prdSettingsList.iterator();
				while (it.hasNext()) {
					PRDSettings prd = it.next();
					prd.setNextPRDDueDate(prdSettings.getNextPRDDueDate());
					prd.setNextPRDExtractDate(prdSettings.getNextPRDExtractDate());
					prd.setLastPRDExtractDate(prdSettings.getLastPRDExtractDate());
					prd.setCurrentDateThru(prdSettings.getCurrentDateThru());
					prdSettingsService.savePRDSettings(prd);
				}
			}
		}
	}

	private DataHeader createDataHeader(PRDSettings prdSettings, DataHeader dataHeader, boolean isTest) {
		DataHeaderService dhs = new DataHeaderServiceImpl();
		if (prdSettings.getSystemCT().equals("ES")) {
			dataHeader
					.setPrdDate(new java.sql.Date(DateUtils.addDays(prdSettings.getLastPRDExtractDate(), 1).getTime()));
		} else {
			dataHeader.setPrdDate(prdSettings.getNextPRDDueDate());
		}
		dataHeader.setSetupFK(prdSettings.getPrdSetupPK());

		if ((prdSettings.getAdditionalPRDSettings() != null) && (prdSettings.getAdditionalPRDSettings().size() > 0)) {
			dataHeader.setComments("Batch: " + prdSettings.getGroupSetup().getGroupNumber() + " ");
			Iterator<PRDSettings> it = prdSettings.getAdditionalPRDSettings().iterator();
			while (it.hasNext()) {
				dataHeader.setComments(dataHeader.getComments() + it.next().getGroupSetup().getGroupNumber() + " ");
			}
		} else {
			if (isTest) {
				dataHeader.setComments(prdSettings.getGroupSetup().getGroupNumber() + "-MANUAL_ALL_PRD");
			} else {
				dataHeader.setComments(
						prdSettings.getGroupSetup().getGroupNumber() + "-" + prdSettings.getFileTemplate().getName());
			}
		}
		dataHeader = dhs.saveDataHeader(dataHeader);
		dataHeader.setDataIssues(new ArrayList<DataIssue>());
		if (prdSettings.isHoldForReview()) {
			dataHeader.setStatusCT(DataHeader.ON_HOLD);
			dataHeader = new DataHeaderServiceImpl().updateDataHeader(dataHeader);
		}
		return dataHeader;
	}

	private String transformField(FileTemplateField fileTemplateField, String value) {
		Transformation transformation = fileTemplateField.getTransformation();
		SourceField sourceField = fileTemplateField.getSourceField();
		String transformSQL = transformation.getSql().replaceAll("\\[X\\]", "'" + value + "'");
		StringBuilder sql = new StringBuilder();
		sql.append("Select ").append(transformSQL).append(" ");
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		Statement statement;
		ResultSet resultSet;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("TransformField error: " + e.toString());
			throw new DAOException(e.toString());
		}
		return value;

	}
	private String transformField(FileTemplateField fileTemplateField, Long recordPK) {
		Transformation transformation = fileTemplateField.getTransformation();
		SourceField sourceField = fileTemplateField.getSourceField();
		String transformSQL = transformation.getSql().replaceAll("\\[X\\]", sourceField.getSqlFieldName());
		StringBuilder sql = new StringBuilder();
		sql.append("Select ").append(transformSQL).append(" ");
		sql.append("from PRD_Record where PRD_RecordPK = ").append(recordPK);
		if ((transformation.getWhereClause() != null) && !transformation.getWhereClause().isEmpty()) {
			sql.append(" AND ").append(transformation.getWhereClause());
		}

		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		Statement statement;
		ResultSet resultSet;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("TransformField error: " + e.toString());
			throw new DAOException(e.toString());
		}
		return sourceField.getSqlFieldName();
	}

	public void runPRDCompareExtract(EDITDate date, String extractType) {
		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_ODS_EXTRACT)
				.tagBatchStart(Batch.BATCH_JOB_RUN_PRD_ODS_EXTRACT, "PRD Compare " + extractType + " Extract: ");
		LoadGroups.load();
		/*
		 * if (extractType.equals("MF")) { runSSISExtract(date, "ODS_PRD_MF_Import"); }
		 * else if (extractType.equals("VENUS")) { runSSISExtract(date,
		 * "ODS_PRD_VENUS_Import"); } else if (extractType.equals("BOTH")) {
		 * runSSISExtract(date, "ODS_PRD_MF_Import"); runSSISExtract(date,
		 * "ODS_PRD_VENUS_Import"); }
		 */
		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_ODS_EXTRACT)
				.tagBatchStop();
	}

	public void runSSISExtract(EDITDate date, String jobToRun) {
		Runtime rt = Runtime.getRuntime();
		String extractDate = date.getFormattedMonth() + "/" + date.getFormattedDay() + "/" + date.getFormattedYear();
		System.out.println("runSSISExtract error: " + extractDate);
		String command = "dtexec /SQL \"" + jobToRun
				+ "\" /SERVER \"localhost\" /Set \"\\Package.Variables[User::CycleDate].Properties[Value]\";"
				+ extractDate;

		Process proc;
		try {
			proc = rt.exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				// System.out.println(s);
				if (s.contains("DTSER_SUCCESS")) {
					EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_ODS_EXTRACT)
							.updateSuccess();
				} else if (s.contains("DTSER_FAILURE")) {
					EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PRD_ODS_EXTRACT)
							.updateFailure();
				}
			}

			// read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}