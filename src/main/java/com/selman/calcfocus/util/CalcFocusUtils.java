package com.selman.calcfocus.util;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.selman.calcfocus.request.Fund;
import com.selman.calcfocus.request.FundSubtype;
import com.selman.calcfocus.request.FundType;
import com.selman.calcfocus.request.PointInTime;
import com.selman.calcfocus.request.PolicyAdminStatus;
import com.selman.calcfocus.request.RoleType;
import com.selman.calcfocus.request.YesNo;
import com.selman.calcfocus.response.MonthEndValues;
import com.selman.calcfocus.response.PolicySummary;

import billing.BillSchedule;
import contract.Bucket;
import contract.Segment;
import contract.YearEndValues;

import com.selman.calcfocus.correspondence.builder.PlanCode;
import com.selman.calcfocus.request.AnnualStatementAdminData;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITCaseException;
import edit.services.db.CRUD;
import edit.services.db.hibernate.SessionHelper;
import event.EDITTrx;
import event.FinancialHistory;

//import com.editsolutions.prd.util.DAOException;

//import edit.common.vo.SegmentVO;
//import edit.services.db.hibernate.SessionHelper;

public class CalcFocusUtils {

	public static String getUniqueRequestID(String contractNumber, Long pk, Date now) {
		DateFormat fmt = new SimpleDateFormat("yyyyMMdd-hhmmss.SSS");
		String dateFormatted = fmt.format(now);
		return contractNumber + "-" + Long.toString(pk) + "-" + dateFormatted;
	}
	
	public static String writeYearEndValues(Connection connection, YearEndValues yearEndValues) throws SQLException {

		final String sql = "INSERT INTO YearEndValues  ( [YearEndValuesPK]\r\n" + 
				"      ,[SegmentFK]\r\n" + 
				"      ,[YearEndDate]\r\n" + 
				"      ,[PolicyAdminStatus]\r\n" + 
				"      ,[PaymentMethod]\r\n" + 
				"      ,[PaymentMode]\r\n" + 
				"      ,[DeductionFrequency]\r\n" + 
				"      ,[ULFaceAmount]\r\n" + 
				"      ,[TermRiderCount]\r\n" + 
				"      ,[TermRiderFace]\r\n" + 
				"      ,[AnnualPremium]\r\n" + 
				"      ,[ResidentState]\r\n" + 
				"      ,[AccumulatedValue]\r\n" + 
				"      ,[InterimInterest]\r\n" + 
				"      ,[AddDate]\r\n" + 
				"      ,[AddUser]\r\n" + 
				"      ,[Operator]\r\n" + 
				"      ,[MainDateTime]) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, yearEndValues.getYearEndValuesPK());
			preparedStatement.setLong(2, yearEndValues.getSegmentFK());
			Date extractDate = new Date(yearEndValues.getYearEndDate().getTimeInMilliseconds());
			java.sql.Date yed = new java.sql.Date(extractDate.getTime());
			preparedStatement.setDate(3,  yed);
			if (yearEndValues.getPolicyAdminStatus() != null) {
			    preparedStatement.setString(4, yearEndValues.getPolicyAdminStatus());
			} else {
			    preparedStatement.setNull(4, java.sql.Types.VARCHAR);
				
			}
			if (yearEndValues.getPaymentMethod() != null) {
			    preparedStatement.setString(5, yearEndValues.getPaymentMethod());
			} else {
			    preparedStatement.setNull(5, java.sql.Types.VARCHAR);
			}
				
			if (yearEndValues.getPaymentMode() != null) {
			    preparedStatement.setString(6, yearEndValues.getPaymentMode());
			} else {
			    preparedStatement.setNull(6, java.sql.Types.VARCHAR);
			}
			preparedStatement.setString(7, yearEndValues.getDeductionFrequency());
			preparedStatement.setFloat(8, yearEndValues.getULFaceAmount().getBigDecimal().floatValue());
			preparedStatement.setInt(9, yearEndValues.getTermRiderCount());
			preparedStatement.setFloat(10, yearEndValues.getTermRiderFace().getBigDecimal().floatValue());
			preparedStatement.setFloat(11, yearEndValues.getAnnualPremium().getBigDecimal().floatValue());
			preparedStatement.setString(12, yearEndValues.getResidentState());
			preparedStatement.setFloat(13, yearEndValues.getAccumulatedValue().getBigDecimal().floatValue());
			preparedStatement.setFloat(14, yearEndValues.getInterimInterest().getBigDecimal().floatValue());
			preparedStatement.setTimestamp(15, new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setString(16, "TEST");
			preparedStatement.setString(17, "TEST");
			preparedStatement.setTimestamp(18, new java.sql.Timestamp(new Date().getTime()));

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("YearEndValues: " + e.toString());
		}
		return null;

	}	
	public static void saveYearEndValues(Connection connection, PolicySummary policySummary) throws SQLException  {
		GregorianCalendar calendar = new GregorianCalendar();
		MonthEndValues mevs = policySummary.getMonthEndValuation().getMonthEndValues().get(0);
        YearEndValues yearEndValues = new YearEndValues();
        /* java.util.Date */
        Date date = mevs.getAsOfDate().toGregorianCalendar().getTime();
        EDITDate asOfDate = new EDITDate(date.getTime());
        yearEndValues.setYearEndDate(new EDITDate(asOfDate));
        yearEndValues.setYearEndValuesPK(CRUD.getNextAvailableKey());
        //yearEndValues.setYearEndValuesPK(0L);
        yearEndValues.setAccumulatedValue(new EDITBigDecimal(BigDecimal.valueOf(mevs.getLoanedPlusUnloanedAccountValue())));
        if (mevs.getPaymentMethod() != null) {
            yearEndValues.setPaymentMode(mevs.getPaymentMode().toString());
        } 
        yearEndValues.setInterimInterest(new EDITBigDecimal(BigDecimal.valueOf(mevs.getInterimAccountValueInterest())));
        yearEndValues.setAnnualPremium(new EDITBigDecimal(BigDecimal.valueOf(mevs.getAnnualPremium())));
        if (mevs.getPolicyAdminStatus() != null) {
        	if (mevs.getPolicyAdminStatus().equals("PRE_LAPSE")) {
        		yearEndValues.setPolicyAdminStatus(PolicyAdminStatus.PRE_LAPSE.value());
        	}
            yearEndValues.setPolicyAdminStatus(mevs.getPolicyAdminStatus().toString());
        }
        yearEndValues.setResidentState(mevs.getResidenceState());
        yearEndValues.setTermRiderCount(mevs.getTermRiderLives().intValue());
        yearEndValues.setTermRiderFace(new EDITBigDecimal(BigDecimal.valueOf(mevs.getTermRiderFaceAmount())));
        yearEndValues.setULFaceAmount(new EDITBigDecimal(BigDecimal.valueOf(mevs.getFaceAmount())));
        yearEndValues.setSegmentFK(new Long(policySummary.getGUID()));
        BillSchedule billSchedule = BillSchedule.findBy_SegmentPK(yearEndValues.getSegmentFK());
        yearEndValues.setPaymentMethod(billSchedule.getBillMethodCT());
        yearEndValues.setDeductionFrequency(billSchedule.getDeductionFrequencyCT());
        //yearEndValues.hSave();
        writeYearEndValues(connection, yearEndValues);
        
	}

//	I think the remaining field change we need is that <fund><principal> should be changed to equal the 
//	FinancialHistory.AccumulatedValue from the last MV transaction
//	Less
//	From BucketHistory connected to the last MI transaction, if one exists, and only if the related Bucket.BucketSourceCT = PolicyLoan:

	public static Double getLoanPrincipleRemaining(Connection connection, String contractNumber) {
		String sql = "  select sum(b.LoanPrincipalRemaining) " + "  from Bucket b "
				+ "  inner join Investment i on b.InvestmentFK = i.InvestmentPK "
				+ "  inner join Segment s on s.SegmentPK = i.SegmentFK " + "  where s.ContractNumber = ? "
				+ "  and b.BucketSourceCT = 'PolicyLoan'";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}
	
/*
 Sum over FinancialHistory.GrossAmount, connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW')
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime


Additionally, add to the above the total:
TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW')
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=   EDITTrxHistory.CycleDate

Additionally, subtract from the above the total:
Sum over FinancialHistory.GrossAmount, connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW')
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL
	
 */
	public static Double getReportingPeriodSumOfPremiumsPaid(Connection connection, String contractNumber, Date firstDayOfYear, Date pointInTimeEffectiveDate) {
		String sql = "select \r\n" + 
				"(select SUM(GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				  ((? <= EDITTrx.EffectiveDate and EDITTrx.EffectiveDate <= ?)) \r\n" + 
				"				  ) \r\n" + 
				"	+	-- plus	  \r\n" + 

				
				"ISNULL((select SUM(GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and " + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=   EDITTrxHistory.CycleDate
				"				  EDITTrx.EffectiveDate < ? and " + 
				"				  ? <= EDITTrxHistory.CycleDate " + 
				"				  ),0)	" +
				

				
				"	-	-- minus	  \r\n" + 
				"ISNULL((select SUM(GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and " + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  EDITTrx.EffectiveDate < ? and " + 
				"				  ? <= EDITTrxHistory.CycleDate and " + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null and " + 
				"				  EDITTrxHistory.OriginalProcessDateTime < ? " + 
				"				  ),0)	";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
			
			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(6, new java.sql.Date(firstDayOfYear.getTime()));
			

			preparedStatement.setString(7, contractNumber);
			preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(9, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(10, new java.sql.Date(firstDayOfYear.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}
	/*
	 * 
Sum over (FinancialHistory.GrossAmount-FinancialHistory.NetAmount), connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW')
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime

Additionally, add to the above the total:
Sum over (FinancialHistory.GrossAmount-FinancialHistory.NetAmount), connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW')
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=   EDITTrxHistory.CycleDate

Additionally, subtract from the above the total:
Sum over (FinancialHistory.GrossAmount-FinancialHistory.NetAmount), connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW')
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL


	 */

	public static Double getReportingPeriodSumOfPremiumChargePaid(Connection connection, String contractNumber, Date firstDayOfYear, Date pointInTimeEffectiveDate) {
		String sql = "select \r\n" + 
				"(select SUM(GrossAmount - NetAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  (? <= EDITTrx.EffectiveDate and EDITTrx.EffectiveDate <= ?)  \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				  ) \r\n" + 
				" 	+	-- plus	  \r\n" + 
				
				"ISNULL((select SUM(GrossAmount - NetAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=   EDITTrxHistory.CycleDate
				"				  (EDITTrx.EffectiveDate < ?  and \r\n" + 
				"				  ? <= EDITTrxHistory.CycleDate) " + 
				"				  ),0)	" +
				
				"	-	-- minus	  \r\n" + 
				"ISNULL((select SUM(GrossAmount - NetAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT in ('PY', 'WP', 'WMD', 'PW') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  ? <= EDITTrxHistory.CycleDate and \r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null and " + 
				"				  EDITTrxHistory.OriginalProcessDateTime < ? " +
				"				  ),0)	";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(6, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(7, contractNumber);
			preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(9, new java.sql.Date(firstDayOfYear.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return sum;

	}
	
	/*
	 * 
Sum over ChargeHistory.ChargeAmount where
ChargeHistory.ChargeTypeCT = AdminExp
and as connected via EDITTrxHistory to EDITTrx where
TransactionTypeCT = MV
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime


Additionally, add to the above the total:
Sum over ChargeHistory.ChargeAmount where
ChargeHistory.ChargeTypeCT = AdminExp
and as connected via EDITTrxHistory to EDITTrx where
TransactionTypeCT = MV
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=   EDITTrxHistory.CycleDate

Additionally, subtract from the above the total:
Sum over ChargeHistory.ChargeAmount where
ChargeHistory.ChargeTypeCT = AdminExp
and as connected via EDITTrxHistory to EDITTrx where
TransactionTypeCT = MV
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL

	 */

	public static Double getReportingPeriodSumOfExpenseCharge(Connection connection, String contractNumber, Date firstDayOfYear, Date pointInTimeEffectiveDate) {
		String sql = "select \r\n" + 
				"(select SUM(ChargeHistory.ChargeAmount) from ChargeHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = ChargeHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"                 ChargeHistory.ChargeTypeCT = 'AdminExp' and " +
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MV' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				  ((? <= EDITTrx.EffectiveDate and EDITTrx.EffectiveDate <= ?)) \r\n" + 
				"				  ) \r\n" + 
				
				
				"	+	-- plus	  \r\n" + 
				"ISNULL((select SUM(ChargeHistory.ChargeAmount) from ChargeHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = ChargeHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"                 ChargeHistory.ChargeTypeCT = 'AdminExp' and " +
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MV' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
				"				  EDITTrx.EffectiveDate < ? and " + 
				"				  ? <= EDITTrxHistory.CycleDate " + 
				"				  ),0)	" +
				
				
				"	-	-- minus	  \r\n" + 
				"ISNULL((select SUM(ChargeHistory.ChargeAmount) from ChargeHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = ChargeHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"                 ChargeHistory.ChargeTypeCT = 'AdminExp' and " +
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MV' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  EDITTrx.EffectiveDate < ? and \r\n" + 
				"				  ? <= EDITTrxHistory.CycleDate and \r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null and " + 
				"				  EDITTrxHistory.OriginalProcessDateTime < ? " +
				"				  ),0)	";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(6, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(7, contractNumber);
			preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(9, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(10, new java.sql.Date(firstDayOfYear.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}

	/*
	 *
Sum over ChargeHistory.ChargeAmount where
ChargeHistory.ChargeTypeCT in ('BaseCOI', 'RiderCOI')
and as connected via EDITTrxHistory to EDITTrx where
TransactionTypeCT = MV
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime


Additionally, add to the above the total:
Sum over ChargeHistory.ChargeAmount where
ChargeHistory.ChargeTypeCT in ('BaseCOI', 'RiderCOI')
and as connected via EDITTrxHistory to EDITTrx where
TransactionTypeCT = MV
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy

and  1/1/yyyy <=   EDITTrxHistory.CycleDate
Additionally, subtract from the above the total:
Sum over ChargeHistory.ChargeAmount where
ChargeHistory.ChargeTypeCT in ('BaseCOI', 'RiderCOI')
and as connected via EDITTrxHistory to EDITTrx where
TransactionTypeCT = MV
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL

	 */

	public static Double getReportingPeriodSumOfCOI(Connection connection, String contractNumber, Date firstDayOfYear, Date pointInTimeEffectiveDate) {
		String sql = "select \r\n" + 
				"(select SUM(ChargeHistory.ChargeAmount) from ChargeHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = ChargeHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"                 ChargeHistory.ChargeTypeCT in ('BaseCOI', 'RiderCOI') and " +
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MV' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				  ((? <= EDITTrx.EffectiveDate and EDITTrx.EffectiveDate <= ?))  \r\n" + 
				"				  ) \r\n" + 
				
				"	+	-- plus	  \r\n" + 
				"ISNULL((select SUM(ChargeHistory.ChargeAmount) from ChargeHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = ChargeHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"                 ChargeHistory.ChargeTypeCT in ('BaseCOI', 'RiderCOI') and " +
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
//				"				  EDITTrx.Status = 'R'	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MV' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
				"				  EDITTrx.EffectiveDate < ?  and " + 
				"				  ? <= EDITTrxHistory.CycleDate " +  // should this be here?
				"				  ),0)	" +
				
				
				"	-	-- minus	  \r\n" + 
				"ISNULL((select SUM(ChargeHistory.ChargeAmount) from ChargeHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = ChargeHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"                 ChargeHistory.ChargeTypeCT in ('BaseCOI', 'RiderCOI') and " +
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MV' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  EDITTrx.EffectiveDate < ? and \r\n" + 
				"				  ? <= EDITTrxHistory.CycleDate and \r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null and " + 
				"				  EDITTrxHistory.OriginalProcessDateTime < ? " +
				"				  ),0)	";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
			
			
			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(6, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(7, contractNumber);
			preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(9, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(10, new java.sql.Date(firstDayOfYear.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}
	
	/*
TransactionTypeCT = MI
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime


Additionally, add to the above the total:
Sum over FinancialHistory.GrossAmount, connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT = MI
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=   EDITTrxHistory.CycleDate

Additionally, subtract from the above the total:
Sum over FinancialHistory.GrossAmount, connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT = MI
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL

	 */
	public static Double getReportingPeriodSumOfAVInterest(Connection connection, String contractNumber, Date firstDayOfYear, 
			Date pointInTimeEffectiveDate) {

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		String sql = "select \r\n" + 
				"(select SUM(FinancialHistory.GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MI' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  ( ? <= EDITTrx.EffectiveDate and EDITTrx.EffectiveDate <= ?)\r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				  ) \r\n" + 
				
				
				"	+	-- plus	  \r\n" + 
				"isNULL((select SUM(GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n " + 
				"				  EDITTrx.TransactionTypeCT = 'MI' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
// EDITTrx.EffectiveDate < 1/1/yyyy
// and  1/1/yyyy <=   EDITTrxHistory.CycleDate
				"				  EDITTrx.EffectiveDate < ? and " + 
				"				  ? <= EDITTrxHistory.CycleDate " + 
				"				  ),0) " +
				
				
				"	-	-- minus	  \r\n" + 
				"isNULL((select SUM(GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MI' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  EDITTrx.EffectiveDate < ? and \r\n" + 
				"				  ? <= EDITTrxHistory.CycleDate and \r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null and " + 
				"				  EDITTrxHistory.OriginalProcessDateTime < ? " +
				"				  ),0)	";

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(6, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(7, contractNumber);
			preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(9, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(10, new java.sql.Date(firstDayOfYear.getTime()));


			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}

	/*
	If and only if this policy is terminated,
	And therefore the pointInTime = termination date.
	Add to the above, 
	FinancialHistory.AccumulatedValue, connected to the final termination transaction.
	The final termination transaction will have Status = N or A, PendingStatus = H, 
	be related to OptionCodeCT = UL, and have TransactionTypeCT = DE, LA, FS, or MA
	Minus
	FinancialHistory.AccumulatedValue, connected to the last active MV prior to the final termination.
	(greatest EffectiveDate with Status = N or A, PendingStatus = H )
	Minus
	FinancialHistory.NetAmount of any PY transaction with Status = N or A, PendingStatus = H, and EffectiveDate > the effective date of the last MV.
	Minus
	FinancialHistory.GrossAmount of any WI transaction with Status = N or A, PendingStatus = H, and EffectiveDate > the effective date of the last MV.
	Minus
    FinancialHistory.AccumulatedValue*{[(FinancialHistory.CurrentDeathBenefit + FinancialHistory.GrossAmount)/FinancialHistory.CurrentDeathBenefit] - 1}
    of any CPO transaction with Status = N or A, PendingStatus = H, and EffectiveDate > the effective date of the last MV.
*/
	public static Double getReportingPeriodSumOfAVInterestAddForTerminated(Connection connection, String contractNumber, 
			Date pointInTimeEffectiveDate, Date mvEffectiveDate) {

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		// FinancialHistory.AccumulatedValue, connected to the final termination transaction.
		// The final termination transaction will have Status = N or A, PendingStatus = H, 
		// be related to OptionCodeCT = UL, and have TransactionTypeCT = DE, LA, FS, or MA
		String sql = "select \r\n" + 
				"(select TOP 1 FinancialHistory.AccumulatedValue from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  Segment.OptionCodeCT = 'UL' and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT in ('DE', 'LA', 'FS', 'MA') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  (? <= EDITTrx.EffectiveDate and EDITTrx.EffectiveDate <= ?) \r\n" + 
				"				  ORDER BY EDITTrx.EffectiveDate desc "	 +
				"				  ) \r\n" + 
				
		// FinancialHistory.AccumulatedValue, connected to the last active MV prior to the final termination.
		// (greatest EffectiveDate with Status = N or A, PendingStatus = H )
				"   -	-- minus	  \r\n" + 
				"isNULL((select TOP 1 FinancialHistory.AccumulatedValue from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'MV' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  EDITTrx.EffectiveDate >= ? " + 
				"                 ORDER BY EDITTrx.EffectiveDate desc " +		
				"				  ),0) " +

				

	 	// FinancialHistory.NetAmount of any PY transaction with Status = N or A, PendingStatus = H, 
	 	// and EffectiveDate > the effective date of the last MV.
				"   -	-- minus	  \r\n" + 
				"isNULL((select sum(FinancialHistory.NetAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'PY' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  EDITTrx.EffectiveDate > ? " + 
				"				  ),0) " +

				
		// FinancialHistory.GrossAmount of any WI transaction with Status = N or A, PendingStatus = H, 
		// and EffectiveDate > the effective date of the last MV.
				"   -	-- minus	  \r\n" + 
				"isNULL((select sum(FinancialHistory.GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'WI' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  EDITTrx.EffectiveDate > ? " + 
				"				  ),0) " +
				
				
    	// FinancialHistory.AccumulatedValue*{[(FinancialHistory.CurrentDeathBenefit + FinancialHistory.GrossAmount)/FinancialHistory.CurrentDeathBenefit] - 1}
    	// of any CPO transaction with Status = N or A, PendingStatus = H, and EffectiveDate > the effective date of the last MV.
				
				"   -	-- minus	  \r\n" + 
				"isNULL((select sum((FinancialHistory.CurrentDeathBenefit + FinancialHistory.GrossAmount)/FinancialHistory.currentDeathBenefit - 1) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'CPO' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  EDITTrx.EffectiveDate > ? " + 
				"				  ),0)	";

		try {
			preparedStatement = connection.prepareStatement(sql);


			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(mvEffectiveDate.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(mvEffectiveDate.getTime()));

			preparedStatement.setString(6, contractNumber);
			preparedStatement.setDate(7, new java.sql.Date(mvEffectiveDate.getTime()));

			preparedStatement.setString(8, contractNumber);
			preparedStatement.setDate(9, new java.sql.Date(mvEffectiveDate.getTime()));

			preparedStatement.setString(10, contractNumber);
			preparedStatement.setDate(11, new java.sql.Date(mvEffectiveDate.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}

	
	/*
FinancialHistory.AccumulatedValue, connected to the following EDITtrx, via EDITTrxHistory:
TransactionTypeCT in ('DE')
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime


Additionally, add to the above the total:
FinancialHistory.AccumulatedValue, connected to the following EDITtrx, via EDITTrxHistory:
TransactionTypeCT in ('DE')
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy

and  1/1/yyyy <=   EDITTrxHistory.CycleDate
Additionally, subtract from the above:
TransactionTypeCT in ('DE')
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL

	 */
	public static Double getReportingPeriodReservesReleasedDeath(Connection connection, String contractNumber, Date firstDayOfYear, Date pointInTimeEffectiveDate) {

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		String sql = "select \r\n" + 
				"(select SUM(FinancialHistory.AccumulatedValue) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ?  and  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'DE' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				  (? <= EDITTrx.EffectiveDate and EDITTrx.EffectiveDate <= ?)  \r\n" + 
//				"				  (? <= EDITTrxHistory.CycleDate))\r\n" + 
				"				  ) \r\n" + 
				
				"	+	-- plus	  \r\n" + 
				"ISNULL((select SUM(AccumulatedValue) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status = 'R'	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'DE' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
				"				  EDITTrx.EffectiveDate < ? " + 
//				"				  ? <= EDITTrxHistory.CycleDate " + 
				"				  ),0)	" +
				
				"	-	-- minus	  \r\n" + 
				"ISNULL((select SUM(AccumulatedValue) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  Segment.ContractNumber = ? and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'DE' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  EDITTrx.EffectiveDate < ? and \r\n" + 
				"				  ? <= EDITTrxHistory.CycleDate and \r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null and " + 
				"				  EDITTrxHistory.OriginalProcessDateTime < ? " +
				"				  ),0)	";

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
//			preparedStatement.setDate(4, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(firstDayOfYear.getTime()));
//			preparedStatement.setDate(7, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(6, contractNumber);
			preparedStatement.setDate(7, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(9, new java.sql.Date(firstDayOfYear.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}
	
	/*
Sum over FinancialHistory.GrossAmount, connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT = WI
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime

plus sum over
FinancialHistory.AccumulatedValue, connected to the following EDITtrx, via EDITTrxHistory:
TransactionTypeCT in ('FS', 'LA', 'MA')
OptionCodeCT = 'UL'
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime

plus sum over
FinancialHistory.AccumulatedValue*{[(FinancialHistory.CurrentDeathBenefit + FinancialHistory.GrossAmount)/FinancialHistory.CurrentDeathBenefit] - 1}
connected to the following EDITtrx, via EDITTrxHistory:
TransactionTypeCT in ('CPO')
Status = N or A
PendingStatus = H
1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime

plus sum over
FinancialHistory.GrossAmount, connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT = WI
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=   EDITTrxHistory.CycleDate

plus sum over
FinancialHistory.AccumulatedValue, connected to the following EDITtrx, via EDITTrxHistory:
TransactionTypeCT in ('FS', 'LA', 'MA')
OptionCodeCT = 'UL'
Status = N or A
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=   EDITTrxHistory.CycleDate

minus sum over
FinancialHistory.GrossAmount, connected to the following EDITTrx, via EDITTrxHistory:
TransactionTypeCT = WI
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL

minus sum over
FinancialHistory.AccumulatedValue, connected to the following EDITtrx, via EDITTrxHistory:
TransactionTypeCT in ('FS', 'LA', 'MA')
OptionCodeCT = 'UL'
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL

minus sum over
FinancialHistory.AccumulatedValue*{[(FinancialHistory.CurrentDeathBenefit + FinancialHistory.GrossAmount)/FinancialHistory.CurrentDeathBenefit] - 1}
connected to the following EDITtrx, via EDITTrxHistory:
TransactionTypeCT in ('CPO')
Status = R or U
PendingStatus = H
EDITTrx.EffectiveDate < 1/1/yyyy
and  1/1/yyyy <=  EDITTrxHistory.CycleDate
and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
and  EDITTrxHistory.OriginalProcessDateTime != NULL

	 */
	public static Double getReportingPeriodReservesReleasedOther(Connection connection, String contractNumber, Date firstDayOfYear, Date pointInTimeEffectiveDate) {

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);


		String sql = "SELECT \r\n" + 
		// 1
				"((select ISNULL((\r\n" + 
				"select (FinancialHistory.GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'WI' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				  \r\n" + 
				"				 ( ? <= EDITTrx.EffectiveDate  and  -- firstDayOfYear\r\n" + 
				"                   EDITTrx.EffectiveDate <= ?)  -- pointInTime\r\n" + 
//				"                                                                  OR\r\n" + 
//				"                    (? <= EDITTrxHistory.CycleDate)) -- pointInTime \r\n" + 
				"), 0))\r\n" + 
				"\r\n" + 
				"+\r\n" + 
				"\r\n" + 
				//2
				"(select ISNULL((\r\n" + 
				"select (FinancialHistory.AccumulatedValue) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'  \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT IN ('FS', 'LA', 'MA') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  OptionCodeCT = 'UL' and \r\n" + 
				"				  \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				 ( ? <= EDITTrx.EffectiveDate  and  -- firstDayOfYear\r\n" + 
				"                   EDITTrx.EffectiveDate <= ?)  -- pointInTime\r\n" + 
//				"                                                                  OR\r\n" + 
//				"                    (? <= EDITTrxHistory.CycleDate)) -- pointInTime \r\n" + 
				"), 0)))\r\n" + 
				"\r\n" + 
				"+ \r\n" + 
				"\r\n" + 
				//3
				"(select ISNULL((\r\n" + 
				"select ((FinancialHistory.AccumulatedValue * ((FinancialHistory.CurrentDeathBenefit + FinancialHistory.GrossAmount)/FinancialHistory.CurrentDeathBenefit)) - 1) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'   \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'CPO' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  \r\n" + 
//1/1/yyyy <= EDITTrx.EffectiveDate <= pointInTime
				"				 ( ? <= EDITTrx.EffectiveDate  and  -- firstDayOfYear\r\n" + 
				"                   EDITTrx.EffectiveDate <= ?)  -- pointInTime\r\n" + 
//				"                                                                  OR\r\n" + 
//				"                    (? <= EDITTrxHistory.CycleDate)) -- pointInTime \r\n" + 
				"), 0))\r\n" + 
				"\r\n" + 
				"+\r\n" + 
				"\r\n" + 
//4
				"(select ISNULL((\r\n" + 
				"select (FinancialHistory.GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'   and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'WI' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H'  and \r\n" + 
				"				  \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=   EDITTrxHistory.CycleDate
				"				  EDITTrx.EffectiveDate < ? and " + 
				"				  ? <= EDITTrxHistory.CycleDate " + 
				"), 0))\r\n" + 
				"\r\n" + 
				"+\r\n" + 
				"\r\n" + 
//5
				
				"(select ISNULL((\r\n" + 
				"select (FinancialHistory.AccumulatedValue) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'  and \r\n" + 
				"				  EDITTrx.Status in ('N', 'A')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT IN ('FS', 'LA', 'MA') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  OptionCodeCT = 'UL' and \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=   EDITTrxHistory.CycleDate
				"				  EDITTrx.EffectiveDate < ? and " + 
				"				  ? <= EDITTrxHistory.CycleDate " + 
				"				  \r\n" + 
				"), 0))\r\n" + 
				"\r\n" + 
				"- \r\n" + 
				"\r\n" + 
				
				//6
				"(select ISNULL((\r\n" + 
				"select (FinancialHistory.GrossAmount) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'  and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'WI' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H'  and \r\n" + 
				"				  \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  EDITTrx.EffectiveDate < ?    and  -- firstDayOfYear\r\n" + 
				"                  ? <= EDITTrxHistory.CycleDate and -- firstDayOfYear\r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null " + 
				"                  and EDITTrxHistory.OriginalProcessDateTime < ? --firstDayOfYear\r\n" + 
				"), 0))\r\n" + 
				"\r\n" + 
				"-\r\n" + 
				"\r\n" + 
				//7
				"(select ISNULL((\r\n" + 
				"select (FinancialHistory.AccumulatedValue) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'  and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT IN ('FS', 'LA', 'MA') and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  OptionCodeCT = 'UL' and \r\n" + 
				"				  \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  EDITTrx.EffectiveDate < ?    and  -- firstDayOfYear\r\n" + 
				"                  ? <= EDITTrxHistory.CycleDate and -- firstDayOfYear\r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null " + 
				"                  and EDITTrxHistory.OriginalProcessDateTime < ? --firstDayOfYear\r\n" + 
				"), 0))\r\n" + 
				"\r\n" + 
				"-\r\n" + 
				"\r\n" + 
				//8
				"(select ISNULL((\r\n" + 
				"select (((FinancialHistory.CurrentDeathBenefit + FinancialHistory.GrossAmount)/FinancialHistory.CurrentDeathBenefit) - 1) from FinancialHistory\r\n" + 
				"				  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK\r\n" + 
				"				  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK \r\n" + 
				"				  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK\r\n" + 
				"				  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK \r\n" + 
				"				  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK\r\n" + 
				"				  where \r\n" + 
				"				  ContractNumber = ? and -- like '06U%'  and \r\n" + 
				"				  EDITTrx.Status in ('R','U')	and \r\n" + 
				"				  EDITTrx.TransactionTypeCT = 'CPO' and \r\n" + 
				"				  EDITTrx.PendingStatus = 'H' and \r\n" + 
				"				  \r\n" + 
//EDITTrx.EffectiveDate < 1/1/yyyy
//and  1/1/yyyy <=  EDITTrxHistory.CycleDate
//and  EDITTrxHistory.OriginalProcessDateTime < 1/1/yyyy
//and  EDITTrxHistory.OriginalProcessDateTime != NULL
				"				  EDITTrx.EffectiveDate < ?    and  -- firstDayOfYear\r\n" + 
				"                 ? <= EDITTrxHistory.CycleDate and -- firstDayOfYear \r\n" + 
				"				  EDITTrxHistory.OriginalProcessDateTime is not null " + 
				"                 and EDITTrxHistory.OriginalProcessDateTime < ? -- firstDayOfYear\r\n" + 
				"), 0))";

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
			//preparedStatement.setDate(4, new java.sql.Date(firstDayOfYear.getTime()));
			//preparedStatement.setDate(5, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(4, contractNumber);
			preparedStatement.setDate(5, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(6, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
			//preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			//preparedStatement.setDate(10, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(7, contractNumber);
			preparedStatement.setDate(8, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(9, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
			//preparedStatement.setDate(12, new java.sql.Date(firstDayOfYear.getTime()));
			//preparedStatement.setDate(15, new java.sql.Date(pointInTimeEffectiveDate.getTime()));

			preparedStatement.setString(10, contractNumber);
			preparedStatement.setDate(11, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(12, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(13, contractNumber);
			preparedStatement.setDate(14, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(15, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(16, contractNumber);
			preparedStatement.setDate(17, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(18, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(19, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(20, contractNumber);
			preparedStatement.setDate(21, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(22, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(23, new java.sql.Date(firstDayOfYear.getTime()));

			preparedStatement.setString(24, contractNumber);
			preparedStatement.setDate(25, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(26, new java.sql.Date(firstDayOfYear.getTime()));
			preparedStatement.setDate(27, new java.sql.Date(firstDayOfYear.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}
	
	public static Double getLienPrincipleRemaining(Connection connection, String contractNumber) {
		String sql = "select sum(b.LoanPrincipalRemaining) " + "  from Bucket b "
				+ "  inner join Investment i on b.InvestmentFK = i.InvestmentPK "
				+ "  inner join Segment s on s.SegmentPK = i.SegmentFK " + "  where s.ContractNumber = ? "
				+ "  and b.BucketSourceCT in ('TILien', 'LTCLienCare', 'LTCLienConfinement') ";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double sum = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;

	}

	public static Double getSumOfPremiumPaidTAMRA(Connection connection, String contractNumber, Date beginDate,
			Date pointInTimeEffectiveDate) {
		return CalcFocusUtils.getSumOfPremiumPaidTAMRA(connection, contractNumber, beginDate, null,
				pointInTimeEffectiveDate);
	}

	// (Sum over FinancialHistory.GrossAmount for PY, PW, WP, and WMD trxs)
	// - (Sum over FinancialHistory.GrossAmount for WI trxs)
	// + (Sum over FinancialHistory.TaxableBenefit for WI trx)
	// + (Sum over FinancialHistory.TaxableBenefit for LO trxs)
	// from sevenPayStartDate to earlier of transaction date or sevenPayStartDate +
	// 7 years - 1 day.
	public static Double getSumOfPremiumPaidTAMRA(Connection connection, String contractNumber, Date beginDate,
			Date endDate, Date pointInTimeEffectiveDate) {
		String sql = "  select SUM(CommissionablePremium) " + "  from CommissionablePremiumHistory "
				+ "  inner join EDITTrxHistory ON EDITTrxHistory.EDITTrxHistoryPK = CommissionablePremiumHistory.EDITTrxHistoryFK	"
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK 						"
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK				"
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK  "
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK	"
				+ "  where Segment.ContractNumber = ?		" + "  and Status not in ('r', 'u')		"
				+ "  and EDITTrx.EffectiveDate >= ?	" + "  and EDITTrx.EffectiveDate < ? ";
		String sql2 = "  select SUM(GrossAmount - TaxableBenefit) from FinancialHistory	"
				+ "  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK	"
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK 	"
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK	"
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK 	"
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK	"
				+ " where ContractNumber = ?	" + "  and EDITTrx.Status not in ('r', 'u')		"
				+ "  and EDITTrx.TransactionTypeCT = 'WI'		" + "  and EDITTrx.EffectiveDate >= ?	"
				+ "  and EDITTrx.EffectiveDate < ?";

		PreparedStatement preparedStatement;
		PreparedStatement preparedStatement2;
		ResultSet resultSet;
		ResultSet resultSet2;
		Double sum = new Double(0.00);
		Double sum2 = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);

			preparedStatement.setDate(2, new java.sql.Date(beginDate.getTime()));
			if ((endDate != null) && pointInTimeEffectiveDate.after(endDate)) {
				preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));
			} else {
				preparedStatement.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
			}

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

			preparedStatement2 = connection.prepareStatement(sql2);

			preparedStatement2.setString(1, contractNumber);
			preparedStatement2.setDate(2, new java.sql.Date(beginDate.getTime()));
			if ((endDate != null) && pointInTimeEffectiveDate.after(endDate)) {
				preparedStatement2.setDate(3, new java.sql.Date(endDate.getTime()));
			} else {
				preparedStatement2.setDate(3, new java.sql.Date(pointInTimeEffectiveDate.getTime()));
			}

			resultSet2 = preparedStatement2.executeQuery();
			while (resultSet2.next()) {
				sum2 = resultSet2.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum - sum2;

	}

	public static Double getSumOfPremiumPaidDEFRA(Connection connection, String contractNumber, Date beginDate,
			Date endDate) {
		String sql = "select SUM(CommissionablePremium) from CommissionablePremiumHistory		\r\n"
				+ "  inner join EDITTrxHistory ON EDITTrxHistory.EDITTrxHistoryPK = CommissionablePremiumHistory.EDITTrxHistoryFK		"
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK 		"
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK		"
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK 		"
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK		"
				+ "  where ContractNumber = ? " + "  and Status not in ('r', 'u')		"
				+ "  and EDITTrx.EffectiveDate >= ?	" + "  and EDITTrx.EffectiveDate <= ? ";

		String sql2 = "select SUM(GrossAmount) from FinancialHistory				"
				+ "  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK		"
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK 						"
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK						"
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK 						"
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK						"
				+ "  where ContractNumber = ?								"
				+ "  and EDITTrx.Status not in ('r', 'u')								"
				+ "  and EDITTrx.TransactionTypeCT = 'WI'								"
				+ "  and EDITTrx.EffectiveDate >= ?								" + "  and EDITTrx.EffectiveDate <= ? ";

		PreparedStatement preparedStatement;
		PreparedStatement preparedStatement2;
		ResultSet resultSet;
		ResultSet resultSet2;
		Double sum = new Double(10.00);
		Double sum2 = new Double(5.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(beginDate.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

			preparedStatement2 = connection.prepareStatement(sql2);

			preparedStatement2.setString(1, contractNumber);
			preparedStatement2.setDate(2, new java.sql.Date(beginDate.getTime()));
			preparedStatement2.setDate(3, new java.sql.Date(endDate.getTime()));

			resultSet2 = preparedStatement2.executeQuery();
			while (resultSet2.next()) {
				sum2 = resultSet2.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum - sum2;

	}

	public static Double getSumNoLapsePremiums(Connection connection, String contractNumber, Date beginDate,
			Date endDate) {
		String sql = "  select SUM(CommissionablePremium) from CommissionablePremiumHistory		"
				+ "  inner join EDITTrxHistory ON EDITTrxHistory.EDITTrxHistoryPK = CommissionablePremiumHistory.EDITTrxHistoryFK		"
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK 		"
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK		"
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK 		"
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK		"
				+ "  where ContractNumber = ? " + "  and Status not in ('r', 'u')		"
				+ "  and EDITTrx.EffectiveDate >= ?	" + "  and EDITTrx.EffectiveDate <= ? ";
		String sql2 = "  select SUM(GrossAmount) from FinancialHistory						"
				+ "  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK			"
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK 						"
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK				"
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK 	"
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK						"
				+ "  where ContractNumber = ?				" + "  and EDITTrx.Status not in ('r', 'u')	"
				+ "  and EDITTrx.TransactionTypeCT = 'WI'	" + "  and EDITTrx.EffectiveDate >= ?		"
				+ "  and EDITTrx.EffectiveDate <= ?";
		String sql3 = "  select SUM(GrossAmount) from FinancialHistory "
				+ "  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK "
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK "
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK "
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK "
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK " + "  where ContractNumber = ? "
				+ "  and EDITTrx.Status not in ('r', 'u') " + "  and EDITTrx.TransactionTypeCT = 'LO' "
				+ "  and EDITTrx.EffectiveDate >= ?			" + "  and EDITTrx.EffectiveDate <= ?";
		String sql4 = "  select SUM(GrossAmount) from FinancialHistory "
				+ "  inner join EDITTrxHistory on EDITTrxHistory.EDITTrxHistoryPK = FinancialHistory.EDITTrxHistoryFK "
				+ "  inner join EDITTrx ON EDITTrxHistory.EDITTrxFK = EDITTrx.EDITTrxPK "
				+ "  inner join ClientSetup ON EDITTrx.ClientSetupFK = ClientSetup.ClientSetupPK "
				+ "  inner join ContractSetup ON ClientSetup.ContractSetupFK = ContractSetup.ContractSetupPK "
				+ "  inner join Segment ON ContractSetup.SegmentFK = Segment.SegmentPK " + "  where ContractNumber = ?"
				+ "  and EDITTrx.Status not in ('r', 'u') " + "  and EDITTrx.TransactionTypeCT = 'LR' "
				+ "  and EDITTrx.EffectiveDate >= ?		" + "  and EDITTrx.EffectiveDate <= ?";

		PreparedStatement preparedStatement;
		PreparedStatement preparedStatement2;
		PreparedStatement preparedStatement3;
		PreparedStatement preparedStatement4;
		ResultSet resultSet;
		ResultSet resultSet2;
		ResultSet resultSet3;
		ResultSet resultSet4;
		Double sum = new Double(0.00);
		Double sum2 = new Double(0.00);
		Double sum3 = new Double(0.00);
		Double sum4 = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);
			preparedStatement.setDate(2, new java.sql.Date(beginDate.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sum = resultSet.getDouble(1);
			}

			preparedStatement2 = connection.prepareStatement(sql2);

			preparedStatement2.setString(1, contractNumber);
			preparedStatement2.setDate(2, new java.sql.Date(beginDate.getTime()));
			preparedStatement2.setDate(3, new java.sql.Date(endDate.getTime()));

			resultSet2 = preparedStatement2.executeQuery();
			while (resultSet2.next()) {
				sum2 = resultSet2.getDouble(1);
			}

			preparedStatement3 = connection.prepareStatement(sql3);

			preparedStatement3.setString(1, contractNumber);
			preparedStatement3.setDate(2, new java.sql.Date(beginDate.getTime()));
			preparedStatement3.setDate(3, new java.sql.Date(endDate.getTime()));

			resultSet3 = preparedStatement3.executeQuery();
			while (resultSet3.next()) {
				sum3 = resultSet3.getDouble(1);
			}

			preparedStatement4 = connection.prepareStatement(sql4);

			preparedStatement4.setString(1, contractNumber);
			preparedStatement4.setDate(2, new java.sql.Date(beginDate.getTime()));
			preparedStatement4.setDate(3, new java.sql.Date(endDate.getTime()));

			resultSet4 = preparedStatement4.executeQuery();
			while (resultSet4.next()) {
				sum4 = resultSet4.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ((sum - sum2) - sum3) + sum4;

	}
	/*
	 * public static Double getSumNoLapsePremiums(Connection connection, String
	 * contractNumber, Date beginDate, Date endDate) { CallableStatement cstmt; try
	 * { cstmt =
	 * connection.prepareCall("{? = call fn_SumNoLapsePremiums(?, ?, ?)}");
	 * 
	 * cstmt.registerOutParameter(1, Types.DOUBLE); cstmt.setString(2,
	 * contractNumber); cstmt.setDate(3, new java.sql.Date(beginDate.getTime()));
	 * cstmt.setDate(4, new java.sql.Date(endDate.getTime()));
	 * 
	 * cstmt.execute(); return cstmt.getDouble(1); } catch (SQLException e) {
	 * e.printStackTrace(); } return null;
	 * 
	 * }
	 */

	public static Date addToDate(Date effectiveDate, int years, int days) {
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(effectiveDate);
		if (years > 0) {
			cal.add(Calendar.YEAR, years);
		}
		cal.add(Calendar.DAY_OF_MONTH, days);
		d = cal.getTime();

		return d;
	}

	public static PlanCode getCalcFocusPlanCode(Connection connection, String ratedGender, String underwritingClass,
			String optionCodeCT, String groupPlan, String company) throws DatatypeConfigurationException {
		
		boolean isCtrOrOirOrLT = false;
		if (optionCodeCT.equals("CTR") || optionCodeCT.equals("OIR") || optionCodeCT.equals("LT")) {
		    isCtrOrOirOrLT = true;
		}

		String sql = "SELECT CalcFocusPlanCodePK " + "      ,RatedGender " + "      ,UnderwritingClass "
				+ "      ,GroupPlan " + "      ,CalcFocusProductCode " + "      ,Company " + "      ,OptionCodeCT "
				+ "      ,LegacyPlanCode " + "      ,LegacyValuationType " + "      ,GenderBlend "
				+ " from CalcFocusPlanCode "
				+ "WHERE ((Company = ?) OR (Company = '*')) ";
		if (!isCtrOrOirOrLT && (ratedGender != null)) {
				sql = sql + "AND ((RatedGender = ?) OR (RatedGender = '*')) ";
		} else {
			ratedGender = null;
		}
		if (!isCtrOrOirOrLT && (underwritingClass != null)) {
				sql = sql + "AND ((UnderwritingClass = ?) OR  (UnderwritingClass = '*')) ";
		} else {
			underwritingClass = null;
		}
		if (optionCodeCT != null) {
			sql = sql + "AND ((OptionCodeCT = ?) OR (OptionCodeCT = '*')) ";
		}
		if (!isCtrOrOirOrLT && (groupPlan != null)) {
			sql = sql + "AND ((GroupPlan = ?) OR (GroupPlan = '*')) ";
		} else {
			groupPlan = null;
		}

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		PlanCode planCode = null;

		try {
			int index = 1;
			preparedStatement = connection.prepareStatement(sql);
			System.out.println("1 index:" + index);
			preparedStatement.setString(index, company);
			index++;
			if (ratedGender != null ) {
				preparedStatement.setString(index, ratedGender);
			System.out.println("2 index:" + index);
				index++;
			}
			if (underwritingClass != null) {
				preparedStatement.setString(index, underwritingClass);
			System.out.println("3 index:" + index);
				index++;
			}
			if (optionCodeCT != null) {
				preparedStatement.setString(index, optionCodeCT);
			System.out.println("4 index:" + index);
				index++;
			}
			if (groupPlan != null) {
			System.out.println("5 index:" + index);
				preparedStatement.setString(index, groupPlan);
			}

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				planCode = new PlanCode(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
						resultSet.getString(8), resultSet.getString(9), resultSet.getString(10));
			}
		} catch (SQLException e) {
			System.out.println("PlanCode error: " + e.toString());
			throw new DatatypeConfigurationException("PlanCode query is incorrect: " + e.toString());
		}
		if (planCode == null) {
			System.out.println("PlanCode is null in getPlanCode");
			throw new DatatypeConfigurationException("PlanCode is null");
		}
		return planCode;

	}
	
	// Not always working for some reason.  Rewrote above.
	public static PlanCode getCalcFocusPlanCodeOriginal(Connection connection, String ratedGender, String underwritingClass,
			String optionCodeCT, String groupPlan, String company) throws DatatypeConfigurationException {

		String sql = "SELECT CalcFocusPlanCodePK " + "      ,RatedGender " + "      ,UnderwritingClass "
				+ "      ,GroupPlan " + "      ,CalcFocusProductCode " + "      ,Company " + "      ,OptionCodeCT "
				+ "      ,LegacyPlanCode " + "      ,LegacyValuationType " + "      ,GenderBlend "
				+ " from CalcFocusPlanCode "
				+ "WHERE ((RatedGender = ?) OR (RatedGender is null AND ? is null) OR (RatedGender = '*')) "
				+ "AND ((UnderwritingClass = ?) OR (UnderwritingClass is null AND ? is null) OR (UnderwritingClass = '*')) "
				+ "AND ((OptionCodeCT = ?) OR (OptionCodeCT is null AND ? is null) OR (OptionCodeCT = '*')) "
				+ "AND ((GroupPlan = ?) OR (GroupPlan is null AND ? is null) OR (GroupPlan = '*')) "
				+ "AND ((Company = ?) OR (Company = '*')) ";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		PlanCode planCode = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			if (ratedGender == null) {
				preparedStatement.setNull(1, Types.VARCHAR);
				preparedStatement.setNull(2, Types.VARCHAR);
			} else {
				preparedStatement.setString(1, ratedGender);
				preparedStatement.setString(2, ratedGender);
			}
			if (underwritingClass == null) {
				preparedStatement.setNull(3, Types.VARCHAR);
				preparedStatement.setNull(4, Types.VARCHAR);
			} else {
				preparedStatement.setString(3, underwritingClass);
				preparedStatement.setString(4, underwritingClass);
			}
			if (optionCodeCT == null) {
				preparedStatement.setNull(5, Types.VARCHAR);
				preparedStatement.setNull(6, Types.VARCHAR);
			} else {
				preparedStatement.setString(5, optionCodeCT);
				preparedStatement.setString(6, optionCodeCT);
			}
			if (groupPlan == null) {
				preparedStatement.setNull(7, Types.VARCHAR);
				preparedStatement.setNull(8, Types.VARCHAR);
			} else {
				preparedStatement.setString(7, groupPlan);
				preparedStatement.setString(8, groupPlan);
			}
			preparedStatement.setString(9, company);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				planCode = new PlanCode(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
						resultSet.getString(8), resultSet.getString(9), resultSet.getString(10));
			}
		} catch (SQLException e) {
			System.out.println("PlanCode error: " + e.toString());
			throw new DatatypeConfigurationException("PlanCode query is incorrect: " + e.toString());
		}
		if (planCode == null) {
			System.out.println("PlanCode is null in getPlanCode");
			throw new DatatypeConfigurationException("PlanCode is null");
		}
		return planCode;

	}

	public static String getCFStatus(String status) {
		System.out.println(status);
		String cfStatus = null;
		switch (status) {
		case "ACTIVE":
		case "Active":
		case "DeathPending":
		case "IssuePendingPremium":
		case "ReinstatementNB":
		case "ReinstatementPending":
		case "Reopen":
		case "RestoredBenefit":
		case "RestoredDeathPending":
		case "SubmitPend":
		case "Submitted":
		case "Transition":
			cfStatus = "Active";
			break;
		case "LTC":
		case "Extension":
		case "Waiver":
		case "WaiverMonthlyDed":
		case "PayorPremiumWaiver":
		case "ON_WAIVER":
			cfStatus = "OnWaiver";
			break;
		case "LapsePending":
		case "PreLapse":
		case "PRELAPSE":
		case "PRE_LAPSE":
			cfStatus = "PreLapse";
			break;
		case "Lapse":
			cfStatus = "Lapsed";
			break;
		case "Surrendered":
		case "NotTaken":
		case "Death":
			cfStatus = "Terminated";
		}
		return cfStatus;
	}

	public static String getCFTransactionType(String transactionType, String complexChangeType) {
		String cfTransactionType = null;
		switch (transactionType) {
		case "FI":
			cfTransactionType = "BenefitsIncrease";
			break;
		case "LO":
			cfTransactionType = "Loan";
			break;
		case "LR":
			cfTransactionType = "LoanRepayment";
			break;
		case "WI":
			cfTransactionType = "PartialSurrender";
			break;
		case "LP":
			cfTransactionType = "PreLapse";
			break;
		case "PY":
			cfTransactionType = "Premium";
			break;
		case "WP":
			cfTransactionType = "Premium";
			break;
		case "PW":
			cfTransactionType = "Premium";
			break;
		case "WMD":
			cfTransactionType = "Premium";
			break;
		case "LA":
			cfTransactionType = "Lapse";
			break;
		case "NT":
			cfTransactionType = "PolicyTermination";
			break;
		case "FS":
			cfTransactionType = "PolicyTermination";
			break;
		case "MA":
			cfTransactionType = "Maturity";
			break;
		case "DE":
			// ?
		case "CC":
			if (complexChangeType.equals("RiderChange")) {
			     cfTransactionType = "BenefitsChange";
			} else if (complexChangeType.equals("FaceIncrease")) {
			     cfTransactionType = "BenefitsIncrease";
			} else if (complexChangeType.equals("FaceDecrease")) {
			     cfTransactionType = "BenefitsDecrease";
			} else if (complexChangeType.equals("DBOption")) {
			     cfTransactionType = "DeathBenefitOptionChange";
			} else if (complexChangeType.equals("RiderAdd")) {
			     cfTransactionType = "RiderAdd";
			} else if (complexChangeType.equals("RiderTerm")) {
			     cfTransactionType = "RiderTerminate";
			} else if (complexChangeType.equals("ClassChange")) {
			     cfTransactionType = "UnderwritingChange";
			}
			break;

		}
		return cfTransactionType;
	}
	/*
	 * if and only if Bucket.UnearnedLoanInterest = 0 (i.e. the product charges loan interes in arrears)
	 * = <principal> x BucketHistory.LoanInterestRate x 
     * [days from lastPrinciaplChangeDate to pointInTimeDate] / 365
     * rounded near to 2 decimals.
     * else, set to zero
	 */
	public static Double calculateUnearedLoanInterest(Connection connection, String contractNumber, Double principle, Date pointInTimeDate, Date lastValuationDate) {
		String sql = "  SELECT b.UnearnedLoanInterest, b.LoanInterestRate, b.LastValuationDate " + 
				"  FROM Bucket b " + 
				"  inner join Investment i on i.InvestmentPK = b.InvestmentFK " + 
				"  inner join Segment s on i.SegmentFK = s.segmentPK " + 
				"  where BucketSourceCT = 'PolicyLoan'" + 
				"  and ContractNumber = ? ";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Double unearnedLoanInterest = new Double(0.00);
		Double loanInterestRate = new Double(0.00);

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				 unearnedLoanInterest = resultSet.getDouble(1);
				 loanInterestRate = resultSet.getDouble(2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (unearnedLoanInterest.equals(new Double(0.00))) {
			long difference = pointInTimeDate.getTime() - lastValuationDate.getTime();
		    float daysBetween = (difference / (1000*60*60*24));
		    unearnedLoanInterest = (principle * loanInterestRate * daysBetween) / 365;
		}
		return unearnedLoanInterest;

	}

	public static Date getLastPrincipleDate(Connection connection, String contractNumber, Double principle, Date pointInTimeDate) {
		String sql = "  SELECT b.LastValuationDate " + 
				"  FROM Bucket b " + 
				"  inner join Investment i on i.InvestmentPK = b.InvestmentFK " + 
				"  inner join Segment s on i.SegmentFK = s.segmentPK " + 
				"  where BucketSourceCT = 'PolicyLoan'" + 
				"  and ContractNumber = ? ";

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Date lastValuationDate = new Date();

		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, contractNumber);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				 lastValuationDate = resultSet.getDate(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastValuationDate;

	}
	
	public static Date getMEVSegments(Connection connection) {
		String sql = " SELECT * FROM Segment " + 
				" inner join YearEndValues y on y.SegmentFK = s.SegmentPK " + 
				" WHERE  s.SegmentFK is null " + 
				" and y.YearEndDate = (" + 
				" SELECT CAST(DATEADD(DAY,-1,DATEADD(YEAR,DATEDIFF(YEAR,1,GETDATE()),0)) AS DATE))" +
				" UNION " +
				" SELECT distinct * Segment " + 
				" left outer join YearEndValues y on y.SegmentFK = s.SegmentPK " + 
				" WHERE  s.SegmentFK is null " + 
				" and OptionCodeCT = 'UL' " + 
				" and y.YearEndValuesPK is null " + 
				" and SegmentStatusCT not in ('Lapse', 'Incomplete', 'NotTaken', 'Surrendered', " + 
				" 'Death', 'Frozen', 'Pending', 'Submitted', 'SubmitPend', 'Terminated', 'Withdrawn', " + 
				" 'DeclinedMed', 'Postponed', 'DeclineElig', 'DeclineReq') ";
	

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Date lastValuationDate = new Date();

		try {
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				 lastValuationDate = resultSet.getDate(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastValuationDate;

	}
	
}
