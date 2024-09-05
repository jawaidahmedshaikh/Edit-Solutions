package com.selman.calcfocus.correspondence;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.datatype.DatatypeConfigurationException;

import com.selman.calcfocus.correspondence.builder.CalcFocusCorrespondenceRequestBuilder;
import com.selman.calcfocus.correspondence.builder.PlanCode;
import com.selman.calcfocus.correspondence.builder.PolicyAndBaseCoverageValues;
import com.selman.calcfocus.correspondence.db.JdbcConnection;
import com.selman.calcfocus.correspondence.db.Query;
import com.selman.calcfocus.request.GenderBlend;
import com.selman.calcfocus.request.Policy;
import com.selman.calcfocus.response.CalculateResponse;
import com.selman.calcfocus.service.CalcFocusLoggingService;
import com.selman.calcfocus.service.CalculateRequestService;
import com.selman.calcfocus.util.BadDataException;
import com.selman.calcfocus.util.CalcFocusUtils;

public class CreateDocuments {

	public static void main(String[] args) {

		Connection connection = null;
		Connection engineConnection = null;
		Connection esConnection = null;
		System.out.println("Setting variables...");
		String dbURL = null;
		String user = null; 
		String pass = null; 
		if (args.length == 3) {
		    dbURL= args[0];
		    user = args[1];
		    pass = args[2];
		}
		
		

		try {
			// config = configs.properties(new File("cfda.properties"));

//			String dbURL = config.getString("database.host");
//			String user = config.getString("database.user");
//			String pass = config.getString("database.pass");

//			String dbURL = "jdbc:jtds:sqlserver://PC-B6KKY23:1433;instance=SQL2014;databaseName=";
			//String dbURL = "jdbc:jtds:sqlserver://172.16.68.101:1433;databaseName=";
//			String user = "asears";
//			String pass = "Welcome1!";
			if ((dbURL == null) || (dbURL.trim().isEmpty())) {
//			    dbURL = "jdbc:jtds:sqlserver://P12-DB:1433;databaseName=";
//			    user = "PRODVenusUserID";
//			    pass = "PRODPassword1";
				dbURL = "jdbc:jtds:sqlserver://172.16.68.101:1433;databaseName=";
				user = "asears";
				pass = "Welcome1!";
//				dbURL = "jdbc:jtds:sqlserver://PC-B6KKY23:1433;instance=SQL2008R2;databaseName=";
//				user = "venus";
//				pass = "venus";
			}
			// String dbURL = "jdbc:jtds:sqlserver://t04-app:1433;databaseName=";
			//dbURL = "jdbc:jtds:sqlserver://t03-app:1433;databaseName=";
//			String user = "venus";
//			String pass = "venus";
			//user = "sa";
			//pass = "TestPassword1";

			PolicyAndBaseCoverageValues policyValues = new PolicyAndBaseCoverageValues();
			policyValues.setCFoutputInstruction("Illustration");
			policyValues.setCFprocessType("IllustrateLife");
			policyValues.setReportType("PDF");
			policyValues.setReportName("Ledger");
			policyValues.setResultsWithReport(Boolean.TRUE);

			connection = JdbcConnection.getConnection(dbURL, user, pass, "STAGING");
			esConnection = JdbcConnection.getConnection(dbURL, user, pass, "EDIT_SOLUTIONS");
			engineConnection = JdbcConnection.getConnection(dbURL, user, pass, "ENGINE");

			long[] baseSegmentPks = Query.getCorrepondencePKs(connection, "PolPage");
			for (int i = 0; i < baseSegmentPks.length; i++) {
				try {
					Query.buildBaseCoverage(connection, baseSegmentPks[i], policyValues);
					System.out.println(policyValues.getContractNumber());
					policyValues.setSegmentStatus(CalcFocusUtils.getCFStatus(policyValues.getSegmentStatus()));
					System.out.println(policyValues.getSegmentStatus());
					Query.setRoleAddressValues(connection, baseSegmentPks[i], policyValues);
					policyValues.setRiderCoverageValues(Query.buildRiderCoverage(connection, baseSegmentPks[i]));
					PlanCode planCode = CalcFocusUtils.getCalcFocusPlanCode(engineConnection,
							policyValues.getRatedGenderCT(), policyValues.getUnderwritingClass(), policyValues.getOptionCodeCT(),
							policyValues.getGroupPlan(),  policyValues.getCompanyName());
					policyValues.setProductCode(planCode.getCalcFocusProductCode());

					CalcFocusCorrespondenceRequestBuilder builder;
					;
					builder = new CalcFocusCorrespondenceRequestBuilder(policyValues);
					Object response = CalculateRequestService.calcFocusRequest(esConnection, builder.getReq());
					if (response instanceof CalculateResponse) {
						System.out.println("Success");
					} else {
						System.out.println("Failure");
					}
				} catch (BadDataException bda) {
					Policy policy = bda.getPolicy();
					CalcFocusLoggingService.writeLogEntry(esConnection, policy.getPolicyNumber(), policy.getCompanyCode(),
							Long.valueOf(Long.parseLong(policy.getPolicyGUID())),
							policy.getPolicyAdminStatus().toString(), bda.getMessage(), null, "calcFocus", 0);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
				if (esConnection != null && !esConnection.isClosed()) {
					esConnection.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}

}
