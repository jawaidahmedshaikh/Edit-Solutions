package com.selman.calcfocus.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;

public class CalcFocusLoggingService {
	
	/*
	[MEVValuesPK] [bigint] IDENTITY(1,1) NOT NULL,
	[BatchId] [varchar](50) NULL,
	[ExtractDate] [date] NULL,
	[ContractNumber] [varchar](15) NOT NULL,
	[Type] [varchar](50) NULL,
	[EOPInterestAdjustment] [money] NULL,
	[EndAV] [money] NULL,
	[AddDate] [date] NOT NULL
	*/

	public static String writeMevValues(final Connection connection, 
			final String batchId,
			final Date extractDate, 
			final String type, 
			final String plan,
			final String contractNumber,
			final Double beginningReserve,
			final Double netPremiums,
			final Double eopInterestAdjustment,
			final Double expenseCharges,
			final Double costOfInsurance,
			final Double nonDeath,
			final Double death,
			final Double endAv) throws SQLException {

		final String sql = "INSERT INTO MEVValues (BatchId, ExtractDate, Type, [Plan], ContractNumber,  " + 
			"beginningReserve, netPremiums, eopInterestAdjustment, expenseCharges, costOfInsurance, " + 
				"nonDeath, death, endAv, AddDate) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(sql);
			if (batchId == null) {
				preparedStatement.setNull(1, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(1, batchId);
			}
			if (extractDate == null) {
				preparedStatement.setNull(2, java.sql.Types.DATE);
			} else {
				preparedStatement.setDate(2, extractDate);
			}
			if (type == null) {
				preparedStatement.setNull(3, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(3, type);
			}
			if (plan == null) {
				preparedStatement.setNull(4, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(4, plan);
			}
			if (contractNumber == null) {
				preparedStatement.setNull(5, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(5, contractNumber);
			}
			if (beginningReserve == null) {
				preparedStatement.setNull(6, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(6, beginningReserve);
			}
			if (netPremiums == null) {
				preparedStatement.setNull(7, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(7, netPremiums);
			}
			if (eopInterestAdjustment == null) {
				preparedStatement.setNull(8, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(8, eopInterestAdjustment);
			}
			if (expenseCharges == null) {
				preparedStatement.setNull(9, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(9, expenseCharges);
			}
			if (costOfInsurance == null) {
				preparedStatement.setNull(10, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(10, costOfInsurance);
			}
			if (nonDeath == null) {
				preparedStatement.setNull(11, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(11, nonDeath);
			}
			if (death == null) {
				preparedStatement.setNull(12, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(12, death);
			}
			if (endAv == null) {
				preparedStatement.setNull(13, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setDouble(13, endAv);
			}
	        long millis=System.currentTimeMillis();  
			preparedStatement.setDate(14, new java.sql.Date(millis));

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Logging error: " + e.toString());
		}
		return null;

	}
	public static String writeLogEntry(final Connection connection, final String contractNumber,
			final String companyCode, final Long segmemtFK, final String segmentStatusCT, final String requestXML,
			final String responseXML, final String addUser, final int isSuccess) throws SQLException {

		final String sql = "INSERT INTO CalcFocusLog (ContractNumber, CompanyCode, SegmentFK, " + 
			"SegmentStatusCT, RequestXML, ResponseXML, AddUser, Success) values (?,?,?,?,?,?,?,?)";

		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(sql);
			if (contractNumber == null) {
				preparedStatement.setNull(1, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(1, contractNumber);
			}
			if (companyCode == null) {
				preparedStatement.setNull(2, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(2, companyCode);
			}
			if (segmemtFK == null) {
				preparedStatement.setNull(3, java.sql.Types.BIGINT);
			} else {
				preparedStatement.setLong(3, segmemtFK);
			}
			if (segmentStatusCT == null) {
				preparedStatement.setNull(4, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(4, segmentStatusCT);
			}
			if (requestXML == null) {
				preparedStatement.setNull(5, java.sql.Types.VARCHAR);
			} else {
				preparedStatement.setString(5, requestXML);
			}

			if (responseXML == null) {
				preparedStatement.setNull(6, java.sql.Types.VARCHAR);
			} else {
				if (responseXML.length() <= 8000) {
					preparedStatement.setString(6, responseXML);
				} else {
					preparedStatement.setString(6, "Response too large to store.");
				}
			}

			preparedStatement.setString(7, addUser);
			preparedStatement.setInt(8, isSuccess);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Logging error: " + e.toString());
		}
		return null;
	}
}