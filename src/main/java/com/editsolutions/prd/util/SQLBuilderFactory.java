package com.editsolutions.prd.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.editsolutions.prd.service.MessageTemplateFieldService;
import com.editsolutions.prd.service.MessageTemplateFieldServiceImpl;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.FileTemplateField;
import com.editsolutions.prd.vo.Message;
import com.editsolutions.prd.vo.MessageTemplateField;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SourceField;
import com.editsolutions.prd.vo.Transformation;

import edit.services.db.hibernate.SessionHelper;
import edu.emory.mathcs.backport.java.util.Collections;
import electric.util.holder.booleanInOut;

public class SQLBuilderFactory {

	public static String PRODUCT_QUERY_OLD = "with dataset as ("
			+ "SELECT      AccountNumber, MARKET_CODE, DeductionDate, "
			+ "SUM(NEW_DEDUCTION_AMOUNT) AS NEW_DEDUCTION_AMOUNT, "
			// + "EMPLOYEE_ID, PRODUCT_TYPE, PHL_ProductCode "
			+ "EMPLOYEE_ID, PRODUCT_TYPE "
			+ "FROM  vw_PRD_Import "
			+ "where DeductionDate = ? "
			+ "and         MARKET_CODE = ? "
			+ " and PRODUCT_TYPE != ? " // from exclusions 
			+ "GROUP BY "
			+ "EMPLOYEE_ID, PRODUCT_TYPE, AccountNumber, MARKET_CODE, DeductionDate "
			// + "EMPLOYEE_ID, PRODUCT_TYPE,PHL_ProductCode, AccountNumber, MARKET_CODE, DeductionDate "
			+ ") select      a.*, " + "(     select      top 1 EMPLOYEE_NAME "
			+ "from  vw_PRD_Import " + "where EMPLOYEE_ID = a.EMPLOYEE_ID "
			+ " and MARKET_CODE = ? "
			+ " order by EMPLOYEE_NAME " + ") as EMPLOYEE_NAME "
			+ "from  dataset a ";
	
	public static String PRODUCT_QUERY = "with dataset as (" 
		      + "SELECT      AccountNumber, MARKET_CODE, DeductionDate, " 
              + "SUM(NEW_DEDUCTION_AMOUNT) AS NEW_DEDUCTION_AMOUNT,  "
              + "EMPLOYEE_ID, PRODUCT_TYPE " 
              + "FROM  vw_PRD_Import  "
              + "where DeductionDate = ? " 
              + "and         MARKET_CODE = ? " 
              + "GROUP BY  "
              + "EMPLOYEE_ID, PRODUCT_TYPE, AccountNumber, MARKET_CODE, DeductionDate  "
              + ") "
              + "select      a.* "
              + ",(    select      top 1 EMPLOYEE_NAME "
              + "from  vw_PRD_Import "
              + "where EMPLOYEE_ID = a.EMPLOYEE_ID " 
              + "and         MARKET_CODE = ? " 
              + "order by EMPLOYEE_NAME, EMPLOYEE_LNAME, EMPLOYEE_FNAME " 
              + ") as EMPLOYEE_NAME "
              + ",(    select      top 1 EMPLOYEE_LNAME "
              + "from  vw_PRD_Import "
              + "where EMPLOYEE_ID = a.EMPLOYEE_ID " 
              + "and         MARKET_CODE = ? " 
              + "order by EMPLOYEE_NAME, EMPLOYEE_LNAME, EMPLOYEE_FNAME " 
              + ") as EMPLOYEE_LNAME "
              + ",(    select      top 1 EMPLOYEE_FNAME "
              + "from  vw_PRD_Import "
              + "where EMPLOYEE_ID = a.EMPLOYEE_ID " 
              + "and         MARKET_CODE = ? " 
              + "order by EMPLOYEE_NAME, EMPLOYEE_LNAME, EMPLOYEE_FNAME "
              + ") as EMPLOYEE_FNAME "
              + "from  dataset a";


	public static String POLICY_QUERY = "SELECT  * "
			+ " from  vw_PRD_Import " + " where DeductionDate = ?  "
			+ " and         MARKET_CODE = ? "
			+ " and PRODUCT_TYPE != ? "; // from exclusions 

	public static String EID_QUERY = "SELECT      AccountNumber, MARKET_CODE, DeductionDate, "
			+ " SUM(NEW_DEDUCTION_AMOUNT) AS NEW_DEDUCTION_AMOUNT, EMPLOYEE_ID, "
			+ " (     select      top 1 EMPLOYEE_NAME "
			+ " from  vw_PRD_Import "
			+ " where EMPLOYEE_ID = a.EMPLOYEE_ID "
			+ " and MARKET_CODE = ? "
			+ " order by EMPLOYEE_NAME  "
			+ " ) as EMPLOYEE_NAME "
			+ " FROM  vw_PRD_Import a "
			+ " where DeductionDate = ?  "
			+ " and         MARKET_CODE = ? "
			+ " and PRODUCT_TYPE != ? " // from exclusions 
			+ " GROUP BY "
			+ " EMPLOYEE_ID, AccountNumber, MARKET_CODE, DeductionDate "
			+ " ORDER BY EMPLOYEE_NAME";
	
	public static String ALL_QUERY = "SELECT * FROM vw_PRD_Import "
			                        + " where DeductionDate = ?  "
			                       // + " and PRODUCT_TYPE != ? " // from exclusions 
			             			+ " and         MARKET_CODE = ? "; 

	public static String getEeidSQLForDynamicQuery(PRDSettings prdSettings) {
		StringBuilder sql = new StringBuilder("select ");
		FileTemplate fileTemplate = prdSettings.getFileTemplate();
		List<FileTemplateField> fileTemplateFields = fileTemplate
				.getFileTemplateFields();
		Collections.sort(fileTemplateFields);
		Iterator<FileTemplateField> it = fileTemplateFields.iterator();
		int numberOfFields = fileTemplateFields.size();
		int fieldCount = 0;
		boolean marketCodeNeeded = true;
		boolean deductionDateNeeded = true;
		boolean employeeIdNeeded = true;
		boolean ssnRequested = false;
		while (it.hasNext()) {
			FileTemplateField fileTemplateField = it.next();
			SourceField sourceField = fileTemplateField.getSourceField();
			if (sourceField.getOdsFieldName() != null) {
				if (sourceField.getOdsFieldName().equals("NEW_DEDUCTION_AMOUNT")) {
				    sql.append("SUM(NEW_DEDUCTION_AMOUNT) AS NEW_DEDUCTION_AMOUNT");
				} else {
				    sql.append(sourceField.getOdsFieldName());
				}
				if (sourceField.getOdsFieldName().equals("MARKET_CODE")) {
					marketCodeNeeded = false;
				}
				// for those strange PRDs that don't have a DeductionDate
				if (sourceField.getOdsFieldName().equals("DeductionDate")) {
				    deductionDateNeeded = false;	
				}
				if (sourceField.getOdsFieldName().equals("EMPLOYEE_ID")) {
				    employeeIdNeeded = false;	
				}
				if (sourceField.getOdsFieldName().equals("SSN")) {
				    ssnRequested = true;	
				}
				if (fieldCount < (numberOfFields - 1)) {
					sql.append(", ");
				} else {
					if (marketCodeNeeded) {
						sql.append(", MARKET_CODE"); // always need market code
					}
					if (deductionDateNeeded) {
						sql.append(", DeductionDate"); // always need deductionDate 
					}
					if (employeeIdNeeded) {
						sql.append(", EMPLOYEE_ID"); // always need employeeID 
					}
					sql.append(" ");
				}
			} else {
				// if last field check for comma
				if (fieldCount == (numberOfFields - 1)) {
				    // remove last comma and space
					sql = new StringBuilder(sql.toString().trim());
					sql = new StringBuilder(sql.substring(0, sql.length() - 1));
					sql.append(" ");
				}
			}
			fieldCount++;
		}
		sql.append("from vw_PRD_Import ");
		sql.append("where DeductionDate = ? ");
		sql.append("and MARKET_CODE = ? ");
		sql.append("and PRODUCT_TYPE != ? ");
		String firstGroupBy = "";
		if (prdSettings.getSummaryCT().toUpperCase().equals("POLNBR")) {
            firstGroupBy = "POLICY_NUMBER"; 
		} else if (prdSettings.getSummaryCT().equals("Product")) {
            firstGroupBy = "PRODUCT_TYPE"; 
		} else {
			if (ssnRequested) {
                firstGroupBy = "SSN"; 
			} else { //(prdSettings.getSummaryCT().equals("EEID")) {
                firstGroupBy = "EMPLOYEE_ID"; 
		    }
		}
		sql.append("group by ");
		sql.append(firstGroupBy);

		it = fileTemplateFields.iterator();
		while (it.hasNext()) {
			FileTemplateField fileTemplateField = it.next();
			SourceField sourceField = fileTemplateField.getSourceField();
			if ((sourceField.getOdsFieldName() != null)
					&& !sourceField.getOdsFieldName().equals(firstGroupBy) &&
					   !sourceField.getOdsFieldName().equals("NEW_DEDUCTION_AMOUNT")) {
				sql.append(", ");
				sql.append(sourceField.getOdsFieldName());
			}
		}
		
		if (marketCodeNeeded) {
		    sql.append(", MARKET_CODE" ); // always need market code
		}
		if (deductionDateNeeded) {
		    sql.append(", DeductionDate" ); // always need DeductionDate 
		}
		if (ssnRequested) {
		    sql.append(", EMPLOYEE_ID" ); // always need DeductionDate 
		}

		return sql.toString();
	}

	public static String testTransformation(
			MessageTemplateField messageTemplateField,
			Transformation transformation, String groupNumber) {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		Statement statement;
		ResultSet resultSet;
		String fieldString = transformation.getSql().replaceFirst("\\[X\\]",
				messageTemplateField.getFieldName());
		String sql = "SELECT " + fieldString + " FROM "
				+ messageTemplateField.getTableName()
				+ " WHERE GroupNumber = '" + groupNumber + "'";

		if (messageTemplateField.getTableName().equals("vw_PRD_Setup")) {
			fieldString = transformation.getSql().replaceFirst("\\[X\\]",
					"p." + messageTemplateField.getFieldName());
			sql = "SELECT "
					+ fieldString
					+ "  FROM "
					+ messageTemplateField.getTableName()
					+ " p, vw_PRD_Group g "
					+ " WHERE p.Group_ContractGroupFK = g.Group_ContractGroupPK and g.groupNumber = '"
					+ groupNumber + "'";
		}

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e.toString());
		}
		return null;
	}

	public static Message buildSQL(Message message, String groupNumber)
			throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		Statement statement;
		ResultSet resultSet;

		MessageTemplateFieldService messageTemplateFieldService = new MessageTemplateFieldServiceImpl();
		String preMessage = message.getPreMessage();
		String postMessage = message.getPreMessage();
		Pattern pattern = Pattern.compile("\\{(.*?)\\}");
		Matcher matcher = pattern.matcher(preMessage);
		List<String> tableField = new ArrayList<>();
		List<String> toBeReplaced = new ArrayList<>();
		while (matcher.find()) {
			tableField.add(matcher.group(1));
			toBeReplaced.add("\\{" + matcher.group(1) + "\\}");
		}
		for (int i = 0; i < tableField.size(); i++) {
			String beforeSplit = tableField.get(i);
			String[] tableFieldPair = beforeSplit.split("\\.");

			MessageTemplateField messageTemplateField = messageTemplateFieldService
					.getMessageTemplateField(tableFieldPair[0],
							tableFieldPair[1]);

			String sql;
			if (tableFieldPair[0].equals("vw_PRD_Group")) {
				if (messageTemplateField.getTransformation() != null) {
					tableFieldPair[1] = messageTemplateField
							.getTransformation().getSql()
							.replaceFirst("\\[X\\]", tableFieldPair[1]);
				}
				// else {
				sql = "SELECT " + tableFieldPair[1] + " FROM "
						+ tableFieldPair[0] + " WHERE groupNumber = '"
						+ groupNumber + "'";
				// }
			} else {
				if (messageTemplateField.getTransformation() != null) {
					tableFieldPair[1] = messageTemplateField
							.getTransformation().getSql()
							.replaceFirst("\\[X\\]", "p." + tableFieldPair[1]);
					sql = "SELECT "
							+ tableFieldPair[1]
							+ "  FROM "
							+ tableFieldPair[0]
							+ " p, vw_PRD_Group g "
							+ " WHERE p.Group_ContractGroupFK = g.Group_ContractGroupPK and g.groupNumber = '"
							+ groupNumber + "'";

				} else {
					sql = "SELECT p."
							+ tableFieldPair[1]
							+ "  FROM "
							+ tableFieldPair[0]
							+ " p, vw_PRD_Group g "
							+ " WHERE p.Group_ContractGroupFK = g.Group_ContractGroupPK and g.groupNumber = '"
							+ groupNumber + "'";
				}
			}
			// System.out.println(sql);

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					postMessage = postMessage.replaceFirst(toBeReplaced.get(i),
							resultSet.getString(1));
				}
			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}

		}
		message.setPostMessage(postMessage);

		return message;

	}

}