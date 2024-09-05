package com.selman.calcfocus.correspondence.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.selman.calcfocus.correspondence.builder.CoverageValues;
import com.selman.calcfocus.correspondence.builder.PartyAddressValues;
import com.selman.calcfocus.correspondence.builder.PlanCode;
import com.selman.calcfocus.correspondence.builder.PolicyAndBaseCoverageValues;
import com.selman.calcfocus.correspondence.builder.RiderCoverageValues;
import com.selman.calcfocus.correspondence.builder.RoleValues;
import com.selman.calcfocus.request.AnnualStatementAdminData;
import com.selman.calcfocus.request.AnnualStatementMonthlySummary;

import contract.Bucket;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import event.BucketHistory;

public class Query {

	public static List<AnnualStatementMonthlySummary> getAnnualStatementMonthlySummary(Connection connection,
			Long annualStatementFK) throws SQLException, DatatypeConfigurationException {
		List<AnnualStatementMonthlySummary> summaries = new ArrayList<>();
		GregorianCalendar calendar = new GregorianCalendar();
		String sql = "SELECT [ANNUALSTATEMENTMONTHLYSUMMARYPK]\r\n" + "		      ,[ANNUALSTATEMENTFK]\r\n"
				+ "		      ,[ENDDATE]\r\n" + "		      ,[ACCUMULATIONVALUEATENDDATE]\r\n"
				+ "		      ,[SUMOFPREMIUMSPAID]\r\n" + "		      ,[SUMOFWITHDRAWALS]\r\n"
				+ "		      ,[INTERESTEARNED]\r\n" + "		      ,[MORTALITYCHARGEANDRIDERCOI]\r\n"
				+ "		      ,[BASECOI]\r\n" + "		      ,[RIDERCOI]\r\n" + "		      ,[SUMOFADMINFEES]\r\n"
				+ "		      ,[INTERESTRATE]\r\n" + "		      ,[SUMOFLOANS]\r\n"
				+ "		      ,[SUMOFLOANREPAYMENTS]\r\n" + "		      ,[SUMOFPREMIUMLOAD]\r\n"
				+ "		  FROM [STAGING].[dbo].[ANNUALSTATEMENTMONTHLYSUMMARY]" + "		  WHERE [ANNUALSTATEMENTFK] = "
				+ annualStatementFK + "         ORDER BY [ENDDATE]";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			AnnualStatementMonthlySummary asms = new AnnualStatementMonthlySummary();
			calendar.setTime(resultSet.getDate("ENDDATE"));
			asms.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			asms.setSumOfPremiumsPaid(resultSet.getDouble("SUMOFPREMIUMSPAID"));
			asms.setSumOfWithdrawals(resultSet.getDouble("SUMOFWITHDRAWALS"));
			asms.setInterestEarned(resultSet.getDouble("INTERESTEARNED"));
			asms.setMortalityChargeAndRiderCOI(resultSet.getDouble("MORTALITYCHARGEANDRIDERCOI"));
			asms.setBaseCOI(resultSet.getDouble("BASECOI"));
			asms.setRiderCOI(resultSet.getDouble("RIDERCOI"));
			asms.setSumOfAdminFees(resultSet.getDouble("SUMOFADMINFEES"));
			asms.setInterestRate(resultSet.getDouble("INTERESTRATE"));
			asms.setSumOfLoans(resultSet.getDouble("SUMOFLOANS"));
			asms.setSumOfLoanRepayments(resultSet.getDouble("SUMOFLOANREPAYMENTS"));
			asms.setSumOfPremiumLoad(resultSet.getDouble("SUMOFPREMIUMLOAD"));
			asms.setPolicyAccumulationValueAtEndDate(resultSet.getDouble("ACCUMULATIONVALUEATENDDATE"));
			summaries.add(asms);
		}

		return summaries;
	}

	public static AnnualStatementAdminData getAnnualStatementAdminData(Connection connection, Long segmentBaseFK) // String
																													// contractNumber)
			throws SQLException, DatatypeConfigurationException {
		AnnualStatementAdminData asad = new AnnualStatementAdminData();
		GregorianCalendar calendar = new GregorianCalendar();
		String sql = "SELECT ANNUALSTATEMENTPK \r\n" + "      , SEGMENTBASEFK \r\n" + "      , STARTDATE \r\n"
				+ "      , ENDDATE \r\n" + "      , ACCUMULATIONVALUEATSTARTDATE \r\n"
				+ "      , LOANEDVALUEATSTARTDATE \r\n" + "      , FACEAMOUNTATSTARTDATE \r\n"
				+ "      , ACCUMULATIONVALUEATENDDATE \r\n" + "      , LOANINTERESTACCRUEDATENDDATE \r\n"
				+ "      , SURRENDERCHARGEATENDDATE \r\n" + "      , SURRENDERVALUEATENDDATE \r\n"
				+ "      , DEATHBENEFITATENDDATE \r\n" + "      , FACEAMOUNTATENDDATE \r\n"
				+ "      , SUMOFPREMIUMSPAID \r\n" + "      , SUMOFLOANS \r\n" + "      , SUMOFLOANREPAYMENTS \r\n"
				+ "      , SUMOFWITHDRAWALS \r\n" + "      , SUMOFRIDERCOI \r\n" + "      , SUMOFCOI \r\n"
				+ "  FROM ANNUALSTATEMENT " + "  WHERE SEGMENTBASEFK = " + segmentBaseFK;

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			calendar.setTime(resultSet.getDate("STARTDATE"));
			asad.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			calendar.setTime(resultSet.getDate("ENDDATE"));
			asad.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			asad.setPolicyLoanedAccountValueAtStartDate(resultSet.getDouble("LOANEDVALUEATSTARTDATE"));
			asad.setFaceAmountAtStartDate(resultSet.getDouble("FACEAMOUNTATSTARTDATE"));
			asad.setPolicyAccumulationValueAtStartDate(resultSet.getDouble("ACCUMULATIONVALUEATSTARTDATE"));
			asad.setPolicyAccumulationValueAtEndDate(resultSet.getDouble("ACCUMULATIONVALUEATENDDATE"));
			asad.setPolicyLoanInterestAccruedAtEndDate(resultSet.getDouble("LOANINTERESTACCRUEDATENDDATE"));
			asad.setPolicySurrenderValueAtEndDate(resultSet.getDouble("SURRENDERVALUEATENDDATE"));
			asad.setSurrenderChargeAtEndDate(resultSet.getDouble("SURRENDERCHARGEATENDDATE"));
			asad.setDeathBenefitAtEndDate(resultSet.getDouble("DEATHBENEFITATENDDATE"));
			asad.setFaceAmountAtEndDate(resultSet.getDouble("FACEAMOUNTATENDDATE"));
			asad.setSumOfPremiumsPaid(resultSet.getDouble("SUMOFPREMIUMSPAID"));
			asad.setSumOfLoans(resultSet.getDouble("SUMOFLOANS"));
			asad.setSumOfLoanRepayments(resultSet.getDouble("SUMOFLOANREPAYMENTS"));
			asad.setSumOfWithdrawals(resultSet.getDouble("SUMOFWITHDRAWALS"));
			asad.setSumOfRiderCOI(resultSet.getDouble("SUMOFRIDERCOI"));
			// asad.setSumOfCOI(resultSet.getDouble("SUMOFCOI"));
			asad.getAnnualStatementMonthlySummary()
					.addAll(Query.getAnnualStatementMonthlySummary(connection, resultSet.getLong("ANNUALSTATEMENTPK")));
		}

		return asad;
	}

	public static long[] getCorrepondencePKs(Connection connection, String corrType) throws SQLException {

		String sql = "SELECT S.SEGMENTBASEPK " + "FROM SEGMENTBASE S "
				+ "WHERE s.STAGINGFK IN ( SELECT STAGINGPK FROM STAGING WHERE CORRESPONDENCETYPE = '" + corrType + "') "
				+ "AND S.OPTIONCODE = 'UL' "
				+ "AND SEGMENTSTATUS != 'Frozen' ";

		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = statement.executeQuery(sql);

		int rowCount = 0;
		if (resultSet.last()) {
			rowCount = resultSet.getRow();
			resultSet.beforeFirst();
		}
		long[] pks = new long[rowCount];
		int i = 0;
		while (resultSet.next()) {
			pks[i] = resultSet.getLong(1);
			i++;
		}

		return pks;

	}

	public static Long[] getRiderPKs(Connection connection, Long segmentBaseFK) throws SQLException {

		String sql = "SELECT S.SEGMENTRIDERPK " + "FROM SEGMENTRIDER S " + "WHERE s.SEGMENTBASEFK = "
				+ Long.toString(segmentBaseFK);

		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = statement.executeQuery(sql);

		int rowCount = 0;
		if (resultSet.last()) {
			rowCount = resultSet.getRow();
			resultSet.beforeFirst();
		}
		Long[] pks = new Long[rowCount];
		int i = 0;
		while (resultSet.next()) {
			pks[i] = resultSet.getLong(1);
			i++;
		}

		return pks;

	}

	public static PolicyAndBaseCoverageValues buildBaseCoverage(Connection connection, long pk,
			PolicyAndBaseCoverageValues values) throws SQLException {

		String sql = "SELECT TOP 1 c.CLASSCODE, S.EFFECTIVEDATE AS SEGEFFECTIVEDATE, S.SEGMENTBASEPK, S.SEGMENTSTATUS, S.ISSUESTATE, "
				+ "S.CONTRACTNUMBER, S.COMPANYNAME, S.ISSUESTATE, S.MASTERCONTRACTNUMBER, "
				+ "S.MASTERCONTRACTEFFECTIVEDATE, S.RATEDGENDERCT, S.FACEAMOUNT, S.AGEATISSUE, "
				+ "S.DEATHBENEFITOPTION, S.ORIGINALUNITS, S.UNITS, S.GROUPPLAN, S.UNDERWRITINGCLASSCT, "
				+ "S.OPTIONCODE, G.CONTRACTGROUPNUMBER, G.CORPORATENAME, "
				+ "B.BILLINGMODE, B.BILLTYPE, B.DEDUCTIONFREQUENCY, "
				+ "P1.EFFECTIVEDATE, P1.BILLAMOUNT, P1.DEDUCTIONAMOUNT " + "FROM SEGMENTBASE S "
				+ "INNER JOIN [GROUP] g on s.GROUPFK = g.GROUPPK "
				+ "INNER JOIN PREMIUMDUE P1 on P1.SEGMENTBASEFK = S.SEGMENTBASEPK "
				+ "INNER JOIN BILLSCHEDULE b on b.BILLSCHEDULEPK = s.BILLSCHEDULEFK "
				+ "INNER JOIN CONTRACTCLIENT c ON c.SEGMENTBASEFK = s.SEGMENTBASEPK "
				+ "WHERE c.ROLE = 'Insured' AND P1.PENDINGEXTRACTINDICATOR != 'R' AND s.SEGMENTBASEPK = " + pk + " "
				+ "AND P1.EFFECTIVEDATE = (SELECT MAX(P2.EFFECTIVEDATE) from PREMIUMDUE P2 "
				+ " WHERE P2.SEGMENTBASEFK = " + pk + " AND P2.PENDINGEXTRACTINDICATOR != 'R' "
				+ " AND P2.EFFECTIVEDATE <= S.EFFECTIVEDATE )" + " ORDER BY P1.PREMIUMDUEPK desc ";

		// segmentVO.getRatedGenderCT(), segmentVO.getUnderwritingClassCT(),
		// segmentVO.getGroupPlan(), vv.getCompany().getCompanyName());
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			values.setEffectiveDate(resultSet.getDate("SEGEFFECTIVEDATE"));
			values.setTobaccoUse(resultSet.getString("CLASSCODE"));
			values.setContractNumber(resultSet.getString("CONTRACTNUMBER"));
			values.setSegmentStatus(resultSet.getString("SEGMENTSTATUS"));
			values.setCompanyName(resultSet.getString("COMPANYNAME"));
			values.setRatedGenderCT(resultSet.getString("RATEDGENDERCT"));
			values.setFaceAmount(resultSet.getDouble("FACEAMOUNT"));
			values.setAgeAtIssue(resultSet.getInt("AGEATISSUE"));
			values.setIssueState(resultSet.getString("ISSUESTATE"));
			values.setDeathBenefitOption(resultSet.getString("DEATHBENEFITOPTION"));
			values.setGroupNumber(resultSet.getString("CONTRACTGROUPNUMBER"));
			values.setCorporateName(resultSet.getString("CORPORATENAME"));
			values.setBillingMode(resultSet.getString("BILLINGMODE"));
			values.setBillType(resultSet.getString("BILLTYPE"));
			values.setDeductionFrequency(resultSet.getString("DEDUCTIONFREQUENCY"));
			values.setUnits(resultSet.getDouble("UNITS"));
			values.setOriginalUnits(resultSet.getDouble("ORIGINALUNITS"));
			values.setGroupPlan(resultSet.getString("GROUPPLAN"));
			values.setOptionCodeCT(resultSet.getString("OPTIONCODE"));
			values.setUnderwritingClass(resultSet.getString("UNDERWRITINGCLASSCT"));
			values.setMasterContractEffectiveDate(resultSet.getDate("MASTERCONTRACTEFFECTIVEDATE"));
			values.setMasterContractNumber(resultSet.getString("MASTERCONTRACTNUMBER"));
			values.setPremiumDueEffectiveDate(resultSet.getDate("EFFECTIVEDATE"));
			values.setBillAmount(resultSet.getDouble("BILLAMOUNT"));
			values.setDeductionAmount(resultSet.getDouble("DEDUCTIONAMOUNT"));
			values.setSegmentBasePK(pk);
		}

		/*
		 * String sql2 =
		 * "SELECT TOP 1 P1.EFFECTIVEDATE, P1.BILLAMOUNT, P1.DEDUCTIONAMOUNT " +
		 * "FROM PREMIUMDUE P1 " + "WHERE P1.SEGMENTBASEFK = " + pk +
		 * " AND P1.PENDINGEXTRACTINDICATOR != 'R' " +
		 * "AND P1.EFFECTIVEDATE = (SELECT MAX(P2.EFFECTIVEDATE) from PREMIUMDUE P2 " +
		 * " WHERE P2.SEGMENTBASEFK = " + pk + " AND P2.PENDINGEXTRACTINDICATOR != 'R' "
		 * + " AND P2.EFFECTIVEDATE <= '" + values.getEffectiveDate() + "')" +
		 * " ORDER BY P1.PREMIUMDUEPK desc ";
		 * 
		 * Statement statement2 = connection.createStatement(); ResultSet resultSet2 =
		 * statement2.executeQuery(sql2); while (resultSet2.next()) {
		 * values.setPremiumDueEffectiveDate(resultSet2.getDate("EFFECTIVEDATE"));
		 * values.setBillAmount(resultSet2.getDouble("BILLAMOUNT"));
		 * values.setDeductionAmount(resultSet2.getDouble("DEDUCTIONAMOUNT")); }
		 */

		return values;
	}

	public static List<RiderCoverageValues> buildRiderCoverage(Connection connection, long pk) throws SQLException {

		List<RiderCoverageValues> riderCoverageValues = new ArrayList<>();

		String sql = "SELECT C.CLASSCODE, S.EFFECTIVEDATE, S.SEGMENTRIDERPK, S.SEGMENTSTATUS, " + "S.AGEATISSUE, S.OPTIONCODE, "
				+ "S.RATEDGENDERCT, S.FACEAMOUNT, S.AGEATISSUE, S.UNDERWRITINGCLASSCT, S.ANNUALPREMIUM " 
				+ "FROM SEGMENTRIDER S "
				+ "INNER JOIN CONTRACTCLIENT c on c.SEGMENTBASEFK = s.SEGMENTBASEFK "
				+ "WHERE (C.ROLE = 'Insured' OR C.ROLE = 'TermInsured') AND s.SEGMENTBASEFK = " + pk;

		// segmentVO.getRatedGenderCT(), segmentVO.getUnderwritingClassCT(),
		// segmentVO.getGroupPlan(), vv.getCompany().getCompanyName());
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			RiderCoverageValues values = new RiderCoverageValues();
			values.setEffectiveDate(resultSet.getDate("EFFECTIVEDATE"));
			values.setFaceAmount(resultSet.getDouble("FACEAMOUNT"));
			values.setAgeAtIssue(resultSet.getInt("AGEATISSUE"));
			values.setUnderwritingClass(resultSet.getString("UNDERWRITINGCLASSCT"));
			values.setSegmentRiderPK(resultSet.getLong("SEGMENTRIDERPK"));
			values.setLegacyPlanCode(resultSet.getString("OPTIONCODE"));
			values.setTobaccoUse(resultSet.getString("CLASSCODE"));
			values.setAnnualPremium(resultSet.getDouble("ANNUALPREMIUM"));
			riderCoverageValues.add(values);
		    Query.setRoleAddressValues(connection, values.getSegmentRiderPK(), values);
		}
		

		return riderCoverageValues;
	}

	public static BucketHistory getBucketHistoryForFunds(Connection connection, long pk, java.sql.Date effectiveDate) throws SQLException {

		BucketHistory bucketHistory = new BucketHistory();

		String sql = "SELECT    top 1 " + "                      BucketHistory.* " + "FROM         Segment INNER JOIN "
				+ "                      ContractSetup ON Segment.SegmentPK = ContractSetup.SegmentFK INNER JOIN "
				+ "                      ClientSetup ON ContractSetup.ContractSetupPK = ClientSetup.ContractSetupFK INNER JOIN "
				+ "                      EDITTrx ON ClientSetup.ClientSetupPK = EDITTrx.ClientSetupFK INNER JOIN "
				+ "                      EDITTrxHistory ON EDITTrx.EDITTrxPK = EDITTrxHistory.EDITTrxFK inner join "
				+ "                      BucketHistory ON EDITTrxHistory.EDITTrxHistoryPK = BucketHistory.EDITTrxHistoryFK "
				+ "where Segment.segmentPK = ? "
				+ "and EDITTrx.EffectiveDate <= ? "
				+ "order by EDITTrx.EffectiveDate desc, EDITTrx.EDITTrxPK desc";

		// segmentVO.getRatedGenderCT(), segmentVO.getUnderwritingClassCT(),
		// segmentVO.getGroupPlan(), vv.getCompany().getCompanyName());
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, pk);
		preparedStatement.setDate(2, effectiveDate);
		resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			bucketHistory.setBucketHistoryPK(resultSet.getLong("BUCKETHISTORYPK"));
			bucketHistory.setCumDollars(new EDITBigDecimal(resultSet.getBigDecimal("CUMDOLLARS")));
			bucketHistory.setCumLoanDollars(new EDITBigDecimal(resultSet.getBigDecimal("CUMLOANDOLLARS")));
			bucketHistory.setLoanInterestDollars(new EDITBigDecimal(resultSet.getBigDecimal("LOANINTERESTDOLLARS")));
			bucketHistory.setLoanPrincipalDollars(new EDITBigDecimal(resultSet.getBigDecimal("LOANPRINCIPALDOLLARS")));
			// remove, since not used. 2023-06-09
//			bucketHistory.setPrevInterestPaidThroughDate(new EDITDate(resultSet.getDate("PREVINTERESTPAIDTHROUGHDATE").getTime()));
			bucketHistory
					.setPreviousLoanCumDollars(new EDITBigDecimal(resultSet.getBigDecimal("PREVIOUSLOANCUMDOLLARS")));
			bucketHistory.setPreviousLoanInterestDollars(
					new EDITBigDecimal(resultSet.getBigDecimal("PREVIOUSLOANINTERESTDOLLARS")));
			bucketHistory.setPreviousValuationDate(new EDITDate(resultSet.getDate("PREVIOUSVALUATIONDATE").getTime()));
			bucketHistory.setEDITTrxHistoryFK(resultSet.getLong("EDITTRXHISTORYFK"));
		}

		return bucketHistory;
	}

	public static Bucket getBucketForFunds(Connection connection, long pk) throws SQLException {

		Bucket bucket = new Bucket();

		String sql = "SELECT    top 1 b.* " + "from Bucket b "
				+ "  inner join Investment i on b.InvestmentFK = i.InvestmentPK "
				+ "  inner join Segment s on s.SegmentPK = i.SegmentFK " + "where s.segmentPK = " + pk + " ";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			bucket.setBucketPK(resultSet.getLong("BUCKETPK"));
			bucket.setAccruedLoanInterest(new EDITBigDecimal(resultSet.getBigDecimal("ACCRUEDLOANINTEREST")));
			bucket.setBucketInterestRate(new EDITBigDecimal(resultSet.getBigDecimal("BUCKETINTERESTRATE")));
			bucket.setCumDollars(new EDITBigDecimal(resultSet.getBigDecimal("CUMDOLLARS")));
			bucket.setInterestPaidThroughDate(new EDITDate(resultSet.getDate("INTERESTPAIDTHROUGHDATE").getTime()));
			bucket.setLastValuationDate(new EDITDate(resultSet.getDate("LASTVALUATIONDATE").getTime()));
			bucket.setInterestPaidThroughDate(new EDITDate(resultSet.getDate("INTERESTPAIDTHROUGHDATE").getTime()));
			bucket.setLoanInterestRate(new EDITBigDecimal(resultSet.getBigDecimal("LOANINTERESTRATE")));

		}

		return bucket;
	}

	public static CoverageValues setRoleAddressValues(Connection connection, Long segmentFK,
			CoverageValues policyValues) throws SQLException {
		
		boolean isBase = true;
		String sql = "SELECT CONTRACTCLIENTPK, [ROLE], LASTNAME, FIRSTNAME, CLASSCODE, GENDER, "
				+ "RELATIONSHIPTOEMPLOYEE, DATEOFBIRTH, ADDRESSLINE1, ADDRESSLINE2, ADDRESSLINE3,"
				+ "  CITY, [STATE], ZIP, SEGMENTBASEFK, SEGMENTRIDERFK " + "FROM CONTRACTCLIENT "; 
		if (policyValues instanceof RiderCoverageValues) {
			sql = sql + "WHERE SEGMENTRIDERFK = ";
			isBase = false;
		} else {
			sql = sql + "WHERE SEGMENTBASEFK = ";
		}
		sql = sql + segmentFK;
		List<RoleValues> roleValues = new ArrayList<>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		PartyAddressValues pa = new PartyAddressValues();
		while (resultSet.next()) {
			RoleValues rv;
			if (isBase) {
			    rv = new RoleValues(resultSet.getLong("CONTRACTCLIENTPK"), 
					resultSet.getLong("SEGMENTBASEFK"),
					resultSet.getString("ROLE"));
			} else {
			    rv = new RoleValues(resultSet.getLong("CONTRACTCLIENTPK"), 
					resultSet.getLong("SEGMENTRIDERFK"),
					resultSet.getString("ROLE"));
			}

			if (rv.getRole().equals("OWN") || rv.getRole().equals("Insured") || rv.getRole().equals("TermInsured")) {
				pa.setContractClientFK(resultSet.getLong("CONTRACTCLIENTPK"));
				pa.setPartyFamilyRelationshipType(resultSet.getString("RELATIONSHIPTOEMPLOYEE"));
				pa.setGender(resultSet.getString("GENDER"));
				pa.setDob(resultSet.getDate("DATEOFBIRTH"));
				pa.setLine1(resultSet.getString("ADDRESSLINE1"));
				pa.setLine2(resultSet.getString("ADDRESSLINE2"));
				pa.setLine3(resultSet.getString("ADDRESSLINE3"));
				pa.setCity(resultSet.getString("CITY"));
				pa.setState(resultSet.getString("STATE"));
				pa.setZip(resultSet.getString("ZIP"));
				pa.setFirstName(resultSet.getString("FIRSTNAME"));
				pa.setLastName(resultSet.getString("LASTNAME"));
				if (isBase) {
				    pa.setSegmentBaseFK(resultSet.getLong("SEGMENTBASEFK"));
				} else {
				    pa.setSegmentBaseFK(resultSet.getLong("SEGMENTRIDERFK"));
				}
				policyValues.setPartyAddressValues(pa);

				// need tobacco use in both Coverage and Party
				// } else if (rv.getRole().equals("Insured")) {
				//policyValues.setTobaccoUse(resultSet.getString("CLASSCODE"));
				//pa.setTobaccoUse(resultSet.getString("CLASSCODE"));
			}
			roleValues.add(rv);

		}
		policyValues.setRoleValues(roleValues);

		return policyValues;
	}

	/*
	 * Sum over the CommissionPhase.ExpectedMonthlyPremium = MonthlyMAP Sum over the
	 * PrevCumExpectedMonthlyPrem = AccumulatedMAP PremiumDue.EffectiveDate =
	 * StartingDate 2018/07/22 Effective Date of the ST transaction = EndingDate
	 * 2021/07/22 Life.MAPEndDate = MAPEndDate 2023-07-22 00:00:00.000
	 * 
	 * ModifiedStartingDate (for AHL) = the later of : {<policy><effectiveDate>
	 * 2018-07-22 00:00:00.000 + 5 month, or StartingDate}
	 * 
	 * ModifiedEndingDate = the earlier of: {EndingDate, or MAPEndDate}
	 * 
	 * MonthCount = months between ModifiedStartingDate and ModifiedEndingDate
	 * 
	 * <sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce> = AccumulatedMAP +
	 * (MonthCount x MonthlyMAP)
	 * 
	 * 1623446548277
	 */
	// PremiumDue.EffectiveDate, ST.EffectiveDate, Life.MAPEndDate,
	// Segment.EffectiveDate
	public static Double getSumOfPremiumToKeepActive(Connection connection, Long premiumDuePk,
			EDITDate premiumDueEffectiveDate, EDITDate editTrxEffectiveDate, EDITDate mapEndDate,
			EDITDate segmentEffectiveDate, EDITDate pointInTimeEffectiveDate) throws SQLException {
		Double value = new Double(0.00);
		java.sql.Date startDate;
		java.sql.Date endDate;
		int monthCount = 0;
		
		if ((segmentEffectiveDate.addMonths(4)).afterOREqual(premiumDueEffectiveDate)) {
			startDate = new java.sql.Date(segmentEffectiveDate.addMonths(4).getTimeInMilliseconds());
		} else {
			startDate = new java.sql.Date(premiumDueEffectiveDate.getTimeInMilliseconds());
		}
		if (pointInTimeEffectiveDate.beforeOREqual(mapEndDate)) {
			endDate = new java.sql.Date(pointInTimeEffectiveDate.getTimeInMilliseconds());
		} else {
			endDate = new java.sql.Date(mapEndDate.getTimeInMilliseconds());
		}
		// monthCount = startDate.
		String sql1 = "SELECT DATEDIFF(month, ?, ?) AS DateDiff";

		PreparedStatement preparedStatement;
		ResultSet resultSet;

		preparedStatement = connection.prepareStatement(sql1);
		preparedStatement.setDate(1, startDate);
		preparedStatement.setDate(2, endDate);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			monthCount = resultSet.getInt(1);
		}

		Double accumulatedMAP = new Double(0.00);
		Double monthlyMAP = new Double(0.00);

		String sql2 = "select SUM(c.ExpectedMonthlyPremium), sum(c.PrevCumExpectedMonthlyPrem) "
				+ "from CommissionPhase c " + "where PremiumDueFK = ?";

		PreparedStatement preparedStatement2;
		ResultSet resultSet2;

		preparedStatement2 = connection.prepareStatement(sql2);
		preparedStatement2.setLong(1, premiumDuePk);
		;
		resultSet2 = preparedStatement2.executeQuery();
		if (resultSet2.next()) {
			monthlyMAP = resultSet2.getDouble(1);
			accumulatedMAP = resultSet2.getDouble(2);
		}

		value = accumulatedMAP + (monthCount * monthlyMAP);
		return value;
	}
}
