package com.editsolutions.prd.util;

import java.util.Date;

import logging.Log;

import com.editsolutions.prd.vo.DataIssue;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.PayrollDeductionData;

import edit.common.EDITMap;

public class PRDLoggingUtils {

	public static void logToDatabase(Exception e, PRDSettings prdSettings, PayrollDeductionData prdData, DataIssue dataIssue) {

		String message = "";

		EDITMap columnInfo = new EDITMap();

		Date processDate = new Date();
		
		String groupNumber = "";
		if ((prdSettings != null) && (prdSettings.getGroupSetup() != null)) {
		    groupNumber = prdSettings.getGroupSetup().getGroupNumber();	
		}

		if (dataIssue == null) {
		    message = "PRD Compare Error: " + groupNumber + ": " + e.getMessage();
		} else {
		    message = dataIssue.getIssueLookupCT() + ": Exception: " + e.getMessage();
		}
		
		// TODO: format dates

		columnInfo.put("Operator", "");
		columnInfo.put("ProcessDate", processDate.toString());
		if (prdData != null) {
		    columnInfo.put("RecordPK", Long.toString(prdData.getRecordFK()));
		}
		if (prdSettings != null) {
		    columnInfo.put("GroupNumber", prdSettings.getGroupSetup().getGroupNumber());
		    columnInfo.put("DeductionDate", prdSettings.getNextPRDDueDate().toString());
		}

		Log.logToDatabase(Log.PRD_COMPARE, message, columnInfo);
	}

}
