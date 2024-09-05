/*
 * 
 * User: cgleason
 * Date: Jan 26, 2007
 * Time: 11:45:30 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package conversion;

import edit.common.vo.*;
import edit.common.*;
import edit.services.db.*;

import java.util.*;
import java.sql.*;

import fission.utility.*;
import engine.dm.dao.*;

public class ConvertContract {
	CRUD crud;
	Connection conn = null;
	CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
	String maxDateDefault = "9999/99/99";

	public ConvertContract(Connection conn, CRUD crud) {
		this.conn = conn;
		this.crud = crud;

		// Use this boolean to Insert rows with Keys
		crud.setRestoringRealTime(true);
	}

	public void convertSegment(HashMap countTable) throws Exception {
		SegmentVO segmentVO = null;

		int countIn = 0;
		int countOut = 0;
		int commitCount = 0;

		countTable.put("PAcountIn", countIn);
		countTable.put("PAcountOut", countOut);
		countTable.put("INcountIn", countIn);
		countTable.put("INcountOut", countOut);
		countTable.put("BUcountOut", countOut);
		countTable.put("CCcountIn", countIn);
		countTable.put("CCcountOut", countOut);
		countTable.put("SScountIn", countIn);
		countTable.put("CCAcountIn", countIn);
		countTable.put("CCAcountOut", countOut);
		countTable.put("WHcountIn", countIn);
		countTable.put("WHcountOut", countOut);
		countTable.put("NRcountIn", countIn);
		countTable.put("NRcountOut", countOut);

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM Segment";

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			if (commitCount == 0) {
				crud.startTransaction();
			}
			countIn++;
			segmentVO = new SegmentVO();
			long segmentPK = new Long(rs.getLong("SegmentPK"));
			segmentVO.setSegmentPK(segmentPK);

            segmentVO.setContractNumber(Util.initString((String)rs.getString("ContractNumber"), null));
            segmentVO.setProductStructureFK(new Long(rs.getLong("ProductStructureFK")));

			String effectiveDate = Util.initString(
					(String) rs.getString("EffectiveDate"), null);
			effectiveDate = CommonDatabaseConversionFunctions
					.convertDate(effectiveDate);
			segmentVO.setEffectiveDate(effectiveDate);

			segmentVO.setAmount(rs.getBigDecimal("Amount"));

			String qualNonQualInd = Util.initString(
					(String) rs.getString("QualNonQualInd"), null);
			if (qualNonQualInd != null) {
				if (qualNonQualInd.equalsIgnoreCase("n")) {
					qualNonQualInd = "NonQualified";
				} else {
					qualNonQualInd = "Qualified";
				}
			}
			segmentVO.setQualNonQualCT(qualNonQualInd);

			segmentVO.setExchangeInd(Util.initString(
					(String) rs.getString("ExchangeInd"), "N"));
			segmentVO.setCostBasis(rs.getBigDecimal("CostBasis"));
			segmentVO.setRecoveredCostBasis(rs
					.getBigDecimal("RecoveredCostBasis"));

			String terminationDate = Util.initString(
					(String) rs.getString("TerminationDate"), null);
			terminationDate = CommonDatabaseConversionFunctions
					.convertDate(terminationDate);
			if (terminationDate == null) {
				terminationDate = EDITDate.DEFAULT_MAX_DATE;
			}
			segmentVO.setTerminationDate(terminationDate);

			String statusChangeDate = Util.initString(
					(String) rs.getString("StatusChangeDate"), null);
			statusChangeDate = CommonDatabaseConversionFunctions
					.convertDate(statusChangeDate);
			segmentVO.setStatusChangeDate(statusChangeDate);

			Integer codeTableKey = new Integer(rs.getInt("NameCT"));
			String segmentName = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			segmentVO.setSegmentNameCT(segmentName);

			codeTableKey = new Integer(rs.getInt("SegmentStatusCT"));
			String segmentStatus = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			segmentVO.setSegmentStatusCT(segmentStatus);

			codeTableKey = new Integer(rs.getInt("OptionCodeCT"));
			String optionCode = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			segmentVO.setOptionCodeCT(optionCode);

			codeTableKey = new Integer(rs.getInt("IssueStateCT"));
			String issueState = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			segmentVO.setIssueStateCT(issueState);

			codeTableKey = new Integer(rs.getInt("QualifiedTypeCT"));
			String qualifiedType = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			segmentVO.setQualifiedTypeCT(qualifiedType);

			String applicationDate = Util.initString(
					(String) rs.getString("ApplicationDate"), null);
			applicationDate = CommonDatabaseConversionFunctions
					.convertDate(applicationDate);
			segmentVO.setApplicationSignedDate(applicationDate);

			String creationDate = Util.initString(
					(String) rs.getString("CreationDate"), null);
			creationDate = CommonDatabaseConversionFunctions
					.convertDate(creationDate);
			segmentVO.setCreationDate(creationDate);

			segmentVO.setCreationOperator(Util.initString(
					(String) rs.getString("CreationOperator"), null));

			segmentVO.setWaiverInEffect("N");
			segmentVO.setWaiveFreeLookIndicator("N");
			segmentVO.setCashWithAppInd("N");

			getPayoutForSegment(segmentVO, countTable);

			getInvestmentsForSegment(segmentVO, countTable);

			getContractClientForSegment(segmentVO, countTable);

			getNoteReminderForSegment(segmentVO, countTable);

			crud.createOrUpdateVOInDBRecursively(segmentVO);
			countOut++;
			commitCount++;

			if (commitCount == 25) {
				crud.commitTransaction();
				commitCount = 0;
			}
		}

		s.close();
		rs.close();

		countTable.put("SEcountIn", countIn);
		countTable.put("SEcountOut", countOut);
	}

	private void getPayoutForSegment(SegmentVO segmentVO, HashMap countTable)
			throws Exception {
		if (segmentVO.getContractNumber().equals("08WS093411")) {
			System.out.println("debug");
		}
		int countIn = (Integer) countTable.get("PAcountIn");
		int countOut = (Integer) countTable.get("PAcountOut");
		PayoutVO payoutVO = null;
		long segmentPK = segmentVO.getSegmentPK();

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM Payout WHERE SegmentFK = " + segmentPK;

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			countIn++;
			String quoteDate = Util.initString(
					(String) rs.getString("QuoteDate"), null);
			quoteDate = CommonDatabaseConversionFunctions
					.convertDate(quoteDate);
			segmentVO.setQuoteDate(quoteDate);

			segmentVO.setCharges(rs.getBigDecimal("Charges"));
			segmentVO.setLoads(rs.getBigDecimal("Loads"));
			segmentVO.setFees(rs.getBigDecimal("Fees"));

			payoutVO = new PayoutVO();
			long payoutPK = new Long(rs.getLong("PayoutPK"));
			payoutVO.setPayoutPK(payoutPK);
			payoutVO.setSegmentFK(segmentPK);

			String paymentStartDate = Util.initString(
					(String) rs.getString("PaymentStartDate"), null);
			paymentStartDate = CommonDatabaseConversionFunctions
					.convertDate(paymentStartDate);
			payoutVO.setPaymentStartDate(paymentStartDate);

			payoutVO.setCertainDuration(rs.getInt("CertainDuration"));
			payoutVO.setPostJune1986Investment(Util.initString(
					(String) rs.getString("PostJune1986Investment"), null));
			payoutVO.setPaymentAmount(rs.getBigDecimal("PaymentAmount"));
			payoutVO.setReducePercent1(rs.getBigDecimal("ReducePercent1"));
			payoutVO.setReducePercent2(rs.getBigDecimal("ReducePercent2"));
			payoutVO.setTotalExpectedReturnAmount(rs
					.getBigDecimal("TotalExpectedReturnAmount"));
			payoutVO.setFinalDistributionAmount(rs
					.getBigDecimal("FinalDistributionAmount"));
			payoutVO.setExclusionRatio(rs.getBigDecimal("ExclusionRatio"));
			payoutVO.setYearlyTaxableBenefit(rs
					.getBigDecimal("YearlyTaxableBenefit"));

			String finalPaymentDate = Util.initString(
					(String) rs.getString("FinalPaymentDate"), null);
			finalPaymentDate = CommonDatabaseConversionFunctions
					.convertDate(finalPaymentDate);
			payoutVO.setFinalPaymentDate(finalPaymentDate);

			String lastCheckDate = Util.initString(
					(String) rs.getString("LastCheckDate"), null);
			lastCheckDate = CommonDatabaseConversionFunctions
					.convertDate(lastCheckDate);
			if (lastCheckDate != null) {
				if (lastCheckDate.equals("2005/08/00")) {
					lastCheckDate = "2005/08/01";
					System.out
							.println("LastCheckDate on Payout set to 2005/08/01 from 2005/08/00 for Payout key = "
									+ payoutPK);
				} else if (lastCheckDate.equals("2005/04/00")) {
					lastCheckDate = "2005/04/01";
					System.out
							.println("LastCheckDate on Payout set to 2005/04/01 from 2005/04/00 for Payout key = "
									+ payoutPK);
				} else if (lastCheckDate.equals("2004/09/00")) {
					lastCheckDate = "2004/09/01";
					System.out
							.println("LastCheckDate on Payout set to 2004/09/01 from 2004/09/00 for Payout key = "
									+ payoutPK);
				} else if (lastCheckDate.equals("2004/07/00")) {
					lastCheckDate = "2004/07/01";
					System.out
							.println("LastCheckDate on Payout set to 2004/07/01 from 2004/07/00 for Payout key = "
									+ payoutPK);
				} else if (lastCheckDate.equals("2006/05/00")) {
					lastCheckDate = "2006/05/01";
					System.out
							.println("LastCheckDate on Payout set to 2006/05/01 from 2006/05/00 for Payout key = "
									+ payoutPK);
				} else if (lastCheckDate.equals("2006/06/00")) {
					lastCheckDate = "2006/06/01";
					System.out
							.println("LastCheckDate on Payout set to 2006/06/01 from 2006/06/00 for Payout key = "
									+ payoutPK);
				}
			}
			payoutVO.setLastCheckDate(lastCheckDate);

			String nextPaymentDate = Util.initString(
					(String) rs.getString("NextPaymentDate"), null);
			nextPaymentDate = CommonDatabaseConversionFunctions
					.convertDate(nextPaymentDate);
			payoutVO.setNextPaymentDate(nextPaymentDate);

			payoutVO.setExclusionAmount(rs.getBigDecimal("ExclusionAmount"));

			String certainPeriodEndDate = Util.initString(
					(String) rs.getString("CertainPeriodEndDate"), null);
			certainPeriodEndDate = CommonDatabaseConversionFunctions
					.convertDate(certainPeriodEndDate);
			payoutVO.setCertainPeriodEndDate(certainPeriodEndDate);

			Integer codeTableKey = new Integer(rs.getInt("PaymentFrequencyCT"));
			String paymentFrequency = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			payoutVO.setPaymentFrequencyCT(paymentFrequency);

			segmentVO.addPayoutVO(payoutVO);
			countOut++;
		}

		s.close();
		rs.close();

		countTable.put("PAcountIn", countIn);
		countTable.put("PAcountOut", countOut);
	}

	private void getInvestmentsForSegment(SegmentVO segmentVO,
			HashMap countTable) throws Exception {
		if (segmentVO.getContractNumber().equals("08WS093411")) {
			System.out.println("debug");
		}
		
		int countIn = (Integer) countTable.get("INcountIn");
		int countOut = (Integer) countTable.get("INcountOut");
		int BUcountOut = (Integer) countTable.get("BUcountOut");

		InvestmentVO investmentVO = null;
		long segmentPK = segmentVO.getSegmentPK();

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM Allocation WHERE SegmentFK = " + segmentPK;

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			countIn++;
			investmentVO = new InvestmentVO();
			long allocationPK = new Long(rs.getLong("AllocationPK"));
			investmentVO.setInvestmentPK(allocationPK);
			investmentVO.setSegmentFK(segmentPK);

			String excessIntCalcDate = Util.initString(
					(String) rs.getString("ExcessInterestCalculationDate"),
					null);
			excessIntCalcDate = CommonDatabaseConversionFunctions
					.convertDate(excessIntCalcDate);
			investmentVO.setExcessInterestCalculationDate(excessIntCalcDate);

			String excessIntPaymentDate = Util.initString(
					(String) rs.getString("ExcessInterestPaymentDate"), null);
			excessIntPaymentDate = CommonDatabaseConversionFunctions
					.convertDate(excessIntPaymentDate);
			investmentVO.setExcessInterestPaymentDate(excessIntPaymentDate);

			investmentVO.setExcessInterest(rs.getBigDecimal("ExcessInterest"));
			investmentVO.setExcessInterestMethod(Util.initString(
					(String) rs.getString("ExcessInterestMethod"), null));

			String excessIntStartDate = Util.initString(
					(String) rs.getString("ExcessInterestStartDate"), null);
			excessIntStartDate = CommonDatabaseConversionFunctions
					.convertDate(excessIntStartDate);
			investmentVO.setExcessInterestStartDate(excessIntStartDate);

			investmentVO.setAssumedInvestmentReturn(rs
					.getBigDecimal("AssumedInvestmentReturn"));

			InvestmentAllocationVO investmentAllocationVO = new InvestmentAllocationVO();
			investmentAllocationVO.setInvestmentAllocationPK(CRUD
					.getNextAvailableKey());
			investmentAllocationVO.setInvestmentFK(allocationPK);
			investmentAllocationVO.setAllocationPercent(rs
					.getBigDecimal("AllocationPct"));
			investmentAllocationVO.setOverrideStatus("P");

			long fundFK = new Long(rs.getLong("FundFK"));
			long filteredFundPK = 0;

			if (fundFK != 0) {
				filteredFundPK = getFilteredFundKey(
						segmentVO.getProductStructureFK(), fundFK);
			}

			investmentVO.setFilteredFundFK(filteredFundPK);
			investmentVO.addInvestmentAllocationVO(investmentAllocationVO);
			countOut++;

			BucketVO bucketVO = new BucketVO();
			bucketVO.setBucketPK(CRUD.getNextAvailableKey());
			bucketVO.setInvestmentFK(allocationPK);
			bucketVO.setCumDollars(rs.getBigDecimal("Dollars"));
			bucketVO.setCumUnits(rs.getBigDecimal("Units"));

			String depositDate = Util.initString(
					(String) rs.getString("DepositDate"), null);
			depositDate = CommonDatabaseConversionFunctions
					.convertDate(depositDate);
			bucketVO.setDepositDate(depositDate);

			String lastValDate = Util.initString(
					(String) rs.getString("LastValuationDate"), null);
			lastValDate = CommonDatabaseConversionFunctions
					.convertDate(lastValDate);
			bucketVO.setLastValuationDate(lastValDate);

			bucketVO.setPayoutDollars(rs.getBigDecimal("PayoutDollars"));
			bucketVO.setPayoutUnits(rs.getBigDecimal("PayoutUnits"));
			investmentVO.addBucketVO(bucketVO);
			BUcountOut++;

			segmentVO.addInvestmentVO(investmentVO);
		}

		s.close();
		rs.close();

		countTable.put("INcountIn", countIn);
		countTable.put("INcountOut", countOut);
		countTable.put("BUcountOut", BUcountOut);
	}


    private void getContractClientForSegment(SegmentVO segmentVO, HashMap countTable) throws Exception
    {
		int countIn = (Integer) countTable.get("CCcountIn");
		int countOut = (Integer) countTable.get("CCcountOut");

		ContractClientVO contractClientVO = null;
		ClientRoleVO clientRoleVO = null;
		long segmentPK = segmentVO.getSegmentPK();

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM ClientRelationShip WHERE SegmentFK = "
				+ segmentPK;

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			countIn++;

			clientRoleVO = new ClientRoleVO();
			clientRoleVO.setClientRolePK(CRUD.getNextAvailableKey());
			clientRoleVO.setClientDetailFK(new Long(rs.getLong("ClientFK")));

			Integer codeTableKey = new Integer(rs.getInt("TypeCT"));
			String type = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			clientRoleVO.setRoleTypeCT(type);

			clientRoleVO.setOverrideStatus("P");

			// save the role to get a key
			long clientRolePK = crud.createOrUpdateVOInDB(clientRoleVO);

			contractClientVO = new ContractClientVO();
			long clientRelationshipPK = new Long(
					rs.getLong("ClientRelationshipPK"));
			contractClientVO.setContractClientPK(clientRelationshipPK);
			contractClientVO.setSegmentFK(segmentPK);
			contractClientVO.setClientRoleFK(clientRolePK);
			contractClientVO.setOverrideStatus("P");

			contractClientVO.setIssueAge(rs.getInt("IssueAge"));

			getContractClientRatings(contractClientVO, countTable);

			getContractClientAllocation(contractClientVO, countTable);

			if (contractClientVO.getEffectiveDate() == null) {
				contractClientVO.setEffectiveDate(segmentVO.getEffectiveDate());
			}
			if (contractClientVO.getTerminationDate() == null) {
				contractClientVO.setTerminationDate(EDITDate.DEFAULT_MAX_DATE);
			}

			getWithholding(contractClientVO, clientRoleVO, segmentPK,
					countTable);

			segmentVO.addContractClientVO(contractClientVO);

			countOut++;
		}

		s.close();
		rs.close();

		countTable.put("CCcountIn", countIn);
		countTable.put("CCcountOut", countOut);
	}

	private void getContractClientRatings(ContractClientVO contractClientVO,
			HashMap countTable) throws Exception {
		int countIn = (Integer) countTable.get("SScountIn");

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM ClientRelSubStandard WHERE ClientRelationshipFK = "
				+ contractClientVO.getContractClientPK();

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			countIn++;
			contractClientVO.setFlatExtra(rs.getBigDecimal("FlatExtraAmount"));
			contractClientVO.setFlatExtraAge(rs.getInt("FlatExtraAge"));
			contractClientVO.setFlatExtraDur(rs.getInt("FlatExtraDur"));
			contractClientVO.setPercentExtra(rs
					.getBigDecimal("FlatExtraPercent"));
			contractClientVO.setPercentExtraAge(rs.getInt("FlatExtraPctAge"));
			contractClientVO.setPercentExtraDur(rs.getInt("FlatExtraPctDur"));

			Integer codeTableKey = new Integer(
					rs.getInt("SubstandardTableRating"));
			String substandardRating = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			contractClientVO.setTableRatingCT(substandardRating);
		}

		s.close();
		rs.close();

		countTable.put("SScountIn", countIn);
	}

	private void getContractClientAllocation(ContractClientVO contractClientVO,
			HashMap countTable) throws Exception {
		int countIn = (Integer) countTable.get("CCAcountIn");
		int countOut = (Integer) countTable.get("CCAcountOut");

		ContractClientAllocationVO contractClientAllocationVO = null;
		long contractClientPK = contractClientVO.getContractClientPK();

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM Payee WHERE ClientRelationshipFK = "
				+ contractClientPK;

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			countIn++;
			String effectiveDate = Util.initString(
					(String) rs.getString("EffectiveDate"), null);
			if (effectiveDate != null && effectiveDate.equals(maxDateDefault)) {
				effectiveDate = EDITDate.DEFAULT_MIN_DATE;
			} else {
				effectiveDate = CommonDatabaseConversionFunctions
						.convertDate(effectiveDate);
			}
			contractClientVO.setEffectiveDate(effectiveDate);

			String terminationDate = Util.initString(
					(String) rs.getString("TerminateDate"), null);
			terminationDate = CommonDatabaseConversionFunctions
					.convertDate(terminationDate);
			if (terminationDate != null) {
				if (terminationDate.equals("200/07/25")) {
					terminationDate = "2009/07/25";
					System.out
							.println("TerminationDate on ContractClient set to 2009/07/25 from 200/07/25 for ContractClient key = "
									+ contractClientPK);
				}
				if (terminationDate.equals("2011/04/00")) {
					terminationDate = "2011/04/01";
					System.out
							.println("TerminationDate on ContractClient set to 2011/04/01 from 2011/04/00 for ContractClient key = "
									+ contractClientPK);
				}
			}

			contractClientVO.setTerminationDate(terminationDate);

			contractClientAllocationVO = new ContractClientAllocationVO();
			contractClientAllocationVO.setContractClientAllocationPK(new Long(
					rs.getLong("PayeePK")));
			contractClientAllocationVO.setContractClientFK(contractClientPK);
			contractClientAllocationVO.setAllocationPercent(rs
					.getBigDecimal("AllocationPct"));
			contractClientAllocationVO.setOverrideStatus("P");

			contractClientVO
					.addContractClientAllocationVO(contractClientAllocationVO);
			countOut++;
		}

		s.close();
		rs.close();

		countTable.put("CCAcountIn", countIn);
		countTable.put("CCAcountOut", countOut);
	}

	private void getWithholding(ContractClientVO contractClientVO,
			ClientRoleVO clientRoleVO, long segmentPK, HashMap countTable)
			throws Exception {
		int countIn = (Integer) countTable.get("WHcountIn");
		int countOut = (Integer) countTable.get("WHcountOut");

		WithholdingVO withholdingVO = null;
		long contractClientPK = contractClientVO.getContractClientPK();
		long clientDetailFK = clientRoleVO.getClientDetailFK();

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM TrxWithholdingOverride "
				+ "INNER JOIN  TrxPayeeOverride ON TrxWithholdingOverride.TrxPayeeOverrideFK = TrxPayeeOverride.TrxPayeeOverridePK "
				+ "INNER JOIN  Transactions ON TrxPayeeOverride.TransactionsFK = Transactions.TransactionsPK "
				+ "WHERE TrxPayeeOverride.ClientFK = "
				+ clientRoleVO.getClientDetailFK()
				+ " AND Transactions.SegmentFK = " + segmentPK;

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			countIn++;
			withholdingVO = new WithholdingVO();
			long withholdingPK = new Long(
					rs.getLong("TrxWithholdingOverridePK"));

			withholdingVO.setWithholdingPK(CRUD.getNextAvailableKey());
			withholdingVO.setFederalWithholdingTypeCT(Util.initString(
					(String) rs.getString("FederalWithholdingInd"), null));
			withholdingVO.setFederalWithholdingAmount(rs
					.getBigDecimal("FederalWithholding"));
			withholdingVO.setFederalWithholdingPercent(rs
					.getBigDecimal("FederalWithholdingPrcnt"));
			withholdingVO.setStateWithholdingTypeCT(Util.initString(
					(String) rs.getString("StateWithholdingInd"), null));
			withholdingVO.setStateWithholdingAmount(rs
					.getBigDecimal("StateWithholding"));
			withholdingVO.setStateWithholdingPercent(rs
					.getBigDecimal("StateWithholdingPrcnt"));
			withholdingVO.setCityWithholdingTypeCT(Util.initString(
					(String) rs.getString("CityWithholdingInd"), null));
			withholdingVO.setCityWithholdingAmount(rs
					.getBigDecimal("CityWithholding"));
			withholdingVO.setCountyWithholdingTypeCT(Util.initString(
					(String) rs.getString("CountyWithholdingInd"), null));
			withholdingVO.setCountyWithholdingAmount(rs
					.getBigDecimal("CountyWithholding"));

			contractClientVO.addWithholdingVO(withholdingVO);
			countOut++;
		}

		s.close();
		rs.close();

		countTable.put("WHcountIn", countIn);
		countTable.put("WHcountOut", countOut);
	}

	private void getNoteReminderForSegment(SegmentVO segmentVO,
			HashMap countTable) throws Exception {
		if (segmentVO.getContractNumber().equals("08WS093411")) {
			System.out.println("debug");
		}
		int countIn = (Integer) countTable.get("NRcountIn");
		int countOut = (Integer) countTable.get("NRcountOut");

		NoteReminderVO noteReminderVO = null;

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM NoteReminder WHERE SegmentFK = "
				+ segmentVO.getSegmentPK();

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			countIn++;
			noteReminderVO = new NoteReminderVO();
			noteReminderVO.setNoteReminderPK(new Long(rs
					.getLong("NoteReminderPK")));
			noteReminderVO.setSegmentFK(segmentVO.getSegmentPK());

			Integer codeTableKey = new Integer(rs.getInt("NoteTypeCT"));
			String noteType = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			noteReminderVO.setNoteTypeCT(noteType);

			noteReminderVO.setNote(Util.initString(
					(String) rs.getString("Note"), null));
			noteReminderVO.setSequence(rs.getInt("Sequence"));
			noteReminderVO.setOperator(Util.initString(
					(String) rs.getString("Operator"), null));

			String maintDateTime = Util.initString(
					(String) rs.getString("MaintDateTime"), null);
			maintDateTime = CommonDatabaseConversionFunctions
					.convertNoteMaintDate(maintDateTime);
			noteReminderVO.setMaintDateTime(maintDateTime);

			codeTableKey = new Integer(rs.getInt("NoteQualifierCT"));
			String notequalifier = CommonDatabaseConversionFunctions
					.getCodeTableValue(codeTableKey);
			noteReminderVO.setNoteQualifierCT(notequalifier);

			segmentVO.addNoteReminderVO(noteReminderVO);

			countOut++;
		}

		s.close();
		rs.close();

		countTable.put("NRcountIn", countIn);
		countTable.put("NRcountOut", countOut);
	}

	public void convertChangeHistory(HashMap countTable) throws Exception {
		int countIn = 0;
		int countOut = 0;
		int commitCount = 0;

		ChangeHistoryVO changeHistoryVO = null;

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM ChangeHistory";

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			if (commitCount == 0) {
				crud.startTransaction();
			}
			countIn++;
			changeHistoryVO = new ChangeHistoryVO();
			long changeHistoryPK = new Long(rs.getLong("ChangeHistoryPK"));
			changeHistoryVO.setChangeHistoryPK(changeHistoryPK);

			changeHistoryVO.setModifiedRecordFK(new Long(rs
					.getLong("ModifiedRecordFK")));
			String tableName = Util.initString(
					(String) rs.getString("TableName"), null);
			String fieldName = Util.initString(
					(String) rs.getString("FieldName"), null);
			changeHistoryVO.setFieldName(fieldName);

			String effectiveDate = Util.initString(
					(String) rs.getString("EffectiveDate"), null);
			effectiveDate = CommonDatabaseConversionFunctions
					.convertDate(effectiveDate);
			changeHistoryVO.setEffectiveDate(effectiveDate);

			String processDate = Util.initString(
					(String) rs.getString("ProcessDate"), null);
			processDate = CommonDatabaseConversionFunctions
					.convertDate(processDate);
			changeHistoryVO.setProcessDate(processDate);

			tableName = checkTableName(tableName, fieldName);
			changeHistoryVO.setTableName(tableName);

			changeHistoryVO.setOperator(Util.initString(
					(String) rs.getString("Operator"), null));

			String maintDateTime = Util.initString(
					(String) rs.getString("MaintDateTime"), null);
			if (maintDateTime.length() == 10) {
				maintDateTime = maintDateTime + " "
						+ EDITDateTime.DEFAULT_MIN_TIME;
			}
			changeHistoryVO.setMaintDateTime(maintDateTime);

			String beforeValue = Util.initString(
					(String) rs.getString("BeforeValue"), null);
			String afterValue = Util.initString(
					(String) rs.getString("AfterValue"), null);

			if (fieldName.endsWith("CT")
					|| fieldName.equalsIgnoreCase("StatusCode")) {
				Integer codeTableKey = null;
				if (beforeValue != null && !beforeValue.equals("")) {
					codeTableKey = new Integer(beforeValue);
					beforeValue = CommonDatabaseConversionFunctions
							.getCodeTableValue(codeTableKey);
				}
				if (afterValue != null && !afterValue.equals("")) {
					codeTableKey = new Integer(afterValue);
					afterValue = CommonDatabaseConversionFunctions
							.getCodeTableValue(codeTableKey);
				}
			}

			changeHistoryVO.setBeforeValue(beforeValue);
			changeHistoryVO.setAfterValue(afterValue);

			crud.createOrUpdateVOInDB(changeHistoryVO);

			countOut++;
			commitCount++;
			if (commitCount == 25) {
				crud.commitTransaction();
				commitCount = 0;
			}
		}

		s.close();
		rs.close();

		countTable.put("CHcountIn", countIn);
		countTable.put("CHcountOut", countOut);
	}

	private String checkTableName(String tableName, String fieldName) {
		String newTableName = tableName;

		if (tableName.equalsIgnoreCase("ClientRelationship")) {
			newTableName = "ContractClient";
		} else if (tableName.equalsIgnoreCase("Payee")) {
			if (fieldName.equalsIgnoreCase("PrintAs2")
					|| fieldName.equalsIgnoreCase("PrintAs")
					|| fieldName.equalsIgnoreCase("DisbursementSourceCT")) {
				newTableName = "Preference";
			} else {
				newTableName = "ContractClientAllocation";
			}
		} else if (tableName.equalsIgnoreCase("Allocation")) {
			newTableName = "Investment";
		} else if (tableName.equalsIgnoreCase("TrxWithholdingOverride")) {
			newTableName = "Withholding";
		} else if (tableName.equalsIgnoreCase("AllocationActivityHistory")) {
			newTableName = "FinancialHistory";
		}

		return newTableName;
	}

	public void convertTransactionPriority(HashMap countTable) throws Exception {
		int countIn = 0;
		int countOut = 0;
		int commitCount = 0;

		TransactionPriorityVO transactionPriorityVO = null;

		Statement s = conn.createStatement();

		String sql = "SELECT * FROM TransactionPriority";

		ResultSet rs = s.executeQuery(sql);

		while (rs.next()) {
			if (commitCount == 0) {
				crud.startTransaction();
			}
			countIn++;
			transactionPriorityVO = new TransactionPriorityVO();
			transactionPriorityVO.setTransactionPriorityPK(new Long(rs
					.getLong("TransactionPriorityPK")));
			transactionPriorityVO.setPriority(rs.getInt("Priority"));

			Integer codeTableKey = new Integer(rs.getInt("TransactionTypeCT"));
			String trxType = null;
			if (codeTableKey == 169) {
				trxType = "DE";
			} else if (codeTableKey == 204) {
				trxType = "DBO";
			} else if (codeTableKey == 210) {
				trxType = "LO";
			} else if (codeTableKey == 212) {
				trxType = "LC";
			} else {
				trxType = CommonDatabaseConversionFunctions
						.getCodeTableValue(codeTableKey);

			}
			transactionPriorityVO.setTransactionTypeCT(trxType);

			crud.createOrUpdateVOInDB(transactionPriorityVO);
			countOut++;

			commitCount++;
			if (commitCount == 100) {
				crud.commitTransaction();
				commitCount = 0;
			}
		}

		s.close();
		rs.close();

		countTable.put("TPcountIn", countIn);
		countTable.put("TPcountOut", countOut);
	}

	private long getFilteredFundKey(long productStructureFK, long fundFK) {
		long filteredFundFK = 0;

		ProductFilteredFundStructureVO[] productFilteredFundStructureVOs = DAOFactory
				.getProductFilteredFundStructureDAO()
				.findByCoStructFKAndFundFK(productStructureFK, fundFK);

		if (productFilteredFundStructureVOs != null) {
			filteredFundFK = productFilteredFundStructureVOs[0]
					.getFilteredFundFK();
		}

		return filteredFundFK;
	}

}
