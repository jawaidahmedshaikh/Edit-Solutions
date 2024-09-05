package com.editsolutions.prd.temp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.editsolutions.prd.service.CaseSetupService;
import com.editsolutions.prd.service.CaseSetupServiceImpl;
import com.editsolutions.prd.service.GroupSetupService;
import com.editsolutions.prd.service.GroupSetupServiceImpl;
import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.vo.CaseSetup;
import com.editsolutions.prd.vo.GroupSetup;
import com.editsolutions.prd.vo.PRDSettings;

import edit.services.db.hibernate.SessionHelper;

public class LoadGroups {

	public static void load() {
		CaseSetupService caseService = new CaseSetupServiceImpl();
		GroupSetupService groupSetupService = new GroupSetupServiceImpl();
		PRDSettingsService prdSettingsService = new PRDSettingsServiceImpl();

		Connection connection = SessionHelper.getSession(
				SessionHelper.EDITSOLUTIONS).connection();
		String sql = "select * from GROUP_DATA ";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				//System.out.println(rs.getString("Case Number"));
				CaseSetup caseSetup = caseService.getCaseSetupByCaseNumber(rs
						.getString("Case Number").trim());
				if (caseSetup != null) {

					GroupSetup groupSetup = new GroupSetup();
					if (caseSetup.getCaseContractPk() == null) {
						//System.out.println(caseSetup.getCaseContractPk());
					} else {
						groupSetup.setCaseContractPk(caseSetup
								.getCaseContractPk());
					}

					try {
						groupSetup.setAddressLine1(rs
								.getString("Address Line 1"));
					} catch (Exception e) {
					}
					try {
						groupSetup.setAddressLine2(rs
								.getString("Address Line 2"));
					} catch (Exception e) {
					}
					try {
						groupSetup.setAddressLine3(rs
								.getString("Address Line 3"));
					} catch (Exception e) {
					}
					try {
						groupSetup.setAddressLine4(rs
								.getString("Address Line 4"));
					} catch (Exception e) {
					}
					try {
						groupSetup.setStateCT(rs.getString("State"));
					} catch (Exception e) {
					}
					try {
						groupSetup.setZipCode(rs.getString("Zip"));
					} catch (Exception e) {
					}

					//System.out.println("City: " + rs.getString(8));
					groupSetup.setCity(rs.getString(8));
					groupSetup.setGroupNumber(rs.getString("Group Number"));
					groupSetup.setCaseNumber(caseSetup.getCaseNumber());

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.sql.Date effectiveDate = null;
					java.sql.Date terminationDate = null;
					try {
						effectiveDate = new java.sql.Date(sdf.parse(
								"2016-05-19").getTime());
						terminationDate = new java.sql.Date(sdf.parse(
								"9999-12-31").getTime());
					} catch (ParseException e) {
					}
					groupSetup.setEffectiveDate(effectiveDate);
					groupSetup.setTerminationDate(terminationDate);
					groupSetup.setReferenceID(groupSetup.getGroupNumber());
					groupSetup.setGroupName(rs.getString("Group Name"));
					groupSetup.setRequirementNotifyDayCT("Friday");
					groupSetup.setSystemCT("MF");

					// save group
					groupSetup = groupSetupService.saveGroupSetup(groupSetup);
					PRDSettings prdSettings = new PRDSettings();
					String sql2 = "select * from PRD_IMPORT_DATA where [Group Number] = ? ";
					PreparedStatement preparedStatement2 = connection
							.prepareStatement(sql2);
					preparedStatement2
							.setString(1, groupSetup.getGroupNumber());
					ResultSet rs2 = preparedStatement2.executeQuery();
					if (rs2.next()) {

						prdSettings.setContractCaseFK(caseSetup
								.getCaseContractPk());
						prdSettings.setContractGroupFK(groupSetup.getGroupContractPk());
						prdSettings.setSummaryCT(rs2.getString("Summary"));
						prdSettings.setSortOptionCT(rs2.getString("Sort"));
						prdSettings.setRepName(rs2.getString("Rep Name"));
						prdSettings.setRepPhoneNumber(rs2
								.getString("Rep Phone"));
						String summaryType;
						if (prdSettings.getSummaryCT().equals("EEID")) {
							prdSettings.setFileTemplateFK(5000000000000L);
							summaryType = "Standard EEID";
						} else {
							prdSettings.setFileTemplateFK(5000000000001L);
							summaryType = "Standard Product";
						}
						prdSettings.setTypeCT("Standard");
						prdSettings.setSubjectTemplateFK(5000000000000L);
						prdSettings.setMessageTemplateFK(5000000000001L);
						prdSettings.setHoldForReview(true);
						prdSettings.setReportTypeCT("Changes");
						prdSettings.setFirstDeductionLeadDays(0);

						java.sql.Date currentDateThru = null;
						java.sql.Date firstDeductionDate = null;
						java.sql.Date nextPRDExtractDate = null;
						java.sql.Date nextPRDDueDate = null;
						java.sql.Date lastPRDExtractDate = null;
						try {
							currentDateThru = new java.sql.Date(sdf.parse(
									"2016-06-30").getTime());
							firstDeductionDate = new java.sql.Date(sdf.parse(
									"2016-05-19").getTime());
							nextPRDExtractDate = new java.sql.Date(sdf.parse(
									"2016-05-19").getTime());
							nextPRDDueDate = new java.sql.Date(sdf.parse(
									"2016-05-20").getTime());
							lastPRDExtractDate = new java.sql.Date(sdf.parse(
									"2016-05-12").getTime());
						} catch (ParseException e) {
						}
						prdSettings.setCurrentDateThru(currentDateThru);
						prdSettings.setFirstDeductionDate(firstDeductionDate);
						prdSettings.setNextPRDExtractDate(nextPRDExtractDate);
						prdSettings.setNextPRDDueDate(nextPRDDueDate);
						prdSettings.setLastPRDExtractDate(lastPRDExtractDate);

						prdSettings.setDeliveryMethodCT("Email");
						prdSettings.setEffectiveDate(effectiveDate);
						prdSettings.setTerminationDate(terminationDate);
						prdSettings.setEmailSentFrom("test@visfin.com");
						prdSettings.setSetupName(groupSetup.getCaseNumber()
								+ "-" + groupSetup.getGroupNumber() + " "
								+ summaryType);
						prdSettings.setStatusCT("Active");

						prdSettingsService.savePRDSettings(prdSettings);

					}
				}

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

}
