/*
 * User: gfrosti
 * Date: Jul 1, 2003
 * Time: 3:40:29 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.business;

import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.services.component.ICRUD;
import edit.portal.exceptions.PortalEditingException;

import java.util.List;
import java.util.Set;

import event.*;
import contract.*;


public interface Event extends ICRUD
{
    public boolean saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName,
                                String optionCode , long productStructureFK) throws Exception;

    public void saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName,
                                String optionCode , long productStructureFK, int notificationDays) throws Exception;

    public boolean saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, SegmentVO segmentVO, String processName,
                                String optionCode , long productStructureFK, String ignoreEditWarnings, DepositsVO[] depositsVOs)
                                                                throws EDITEventException, PortalEditingException, EDITValidationException;

    public void saveCommissionGroupSetup(GroupSetupVO groupSetupVO) throws Exception;

    public long reverseClientTrx(long editTrxPK, String operator, String reversalReasonCode) throws Exception;

    public long batchReverseClientTrx(long editTrxPK, String operator, String reversalReasonCode, List<Long> pksToReverse, boolean isSubmitReversalChain, EDITTrxVO earliestPYToRedo) throws Exception;

    public String reverseNSFClientTrx(long editTrxPK, String operator, String reversalReasonCode, String contractNumber) throws Exception;

    public void processCommissionChecks(String processDate) throws Exception;

    public void commitContract(SegmentVO segmentVO,
                                String operator,
                                 String lastDayOfMonthInd,
                                  String incomeMaturityDate,
                                   String suppressPolicyPages) throws Exception;

    public int deleteClientTrx(long editTrxPK, String operator) throws Exception;
    
    public void resetSuspense(OutSuspenseVO[] outSuspenseVOs) throws Exception;
    
    public void replaceSuspense(OutSuspenseVO[] outSuspenseVOs, String operator);

    public void resetNotificationAmountReceived(EDITTrxVO editTrxVO) throws Exception;

    public void deleteCommissionAdjustment(long commissionHistoryPK) throws Exception;

    public void deleteSuspense(long suspensePK) throws Exception;

    public int deleteWithholdingOverride(long withholdingOverridePK) throws Exception;

    public int deleteInvestmentAllocationOverride(long invAllocOvrdPK) throws Exception;

//    public boolean isAccountingPending(long segmentPK, String processDate) throws Exception;

    /* ************************************** Composers ********************************* */

    public GroupSetupVO composeGroupSetupVOByEDITTrxPK(long editTrxPK, List voInclusionList) throws Exception;

    public EDITTrxVO composeEDITTrxVOByEDITTrxPK(long editTrxPK, List voInclusionList) throws Exception;

    public EDITTrxVO[] composeEDITTrxVOBySegmentPK(long segmentPK, List voInclusionList) throws Exception;

    /**
     * Composes all EDITTrx records where the SegmentFK (on the contractSetup record) matches the segmentPK parameter
     * value, and whose transaction is in the transactionTypes value(s) parameter
     * @param segmentPK
     * @param transactionTypes
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes, List voInclusionList) throws Exception;

    /**
     * Composes all Pending EDITTrx records where the SegmentFK (on the contractSetup record) matches the segmentPK parameter
     * value, and whose transaction is in the transactionTypes value(s) parameter
     * @param segmentPK
     * @param transactionTypes
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composePendingEDITTrxVOBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes, List voInclusionList) throws Exception;

    public EDITTrxVO[] composeEDITTrxVOForStatement(long segmentPK,
                                                     String startingEffDate,
                                                      String endingEffDate,
                                                       int drivingTrxPriority,
                                                        List voInclusionList) throws Exception;

    public EDITTrxVO[] composeEDITTrxVOBySegmentPKs_AND_PendingStatus(List segmentPKList, String[] pendingStatus, List voInclusionList) throws Exception;

    public EDITTrxVO[] composeEDITTrxVOByRange_AND_PendingStatus(long startingEDITTrxPK,
                                                                 String[] pendingStatus,
                                                                 int fetchSize,
                                                                 int scrollDirection,
                                                                 List voInclusionList) throws Exception;

    /**
     * Finds all EDITTrxVOs with the specified pendingStatus
     * @param pendingStatus     value of pending status for trx
     * @param voInclusionList   associated VOs to be included
     * @return All pending EDITTrxVOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatus(String pendingStatus, List voInclusionList) throws Exception;

    /**
     * Finds all EDITTrxVOs with the specified pendingStatus and transactionType
     * @param pendingStatus     value of pending status for trx
     * @param transactionType	value of transactionType for trx
     * @param voInclusionList   associated VOs to be included
     * @return All pending EDITTrxVOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatusAndTransactionType(long segmentPK, String pendingStatus, String transactionType, List voInclusionList) throws Exception;

    /**
     * Finds all EDITTrxVOs with the specified pendingStatus, sorted by Segment and Trx Effective Date
     * @param pendingStatus     value of pending status for trx
     * @param voInclusionList   associated VOs to be included
     * @return All pending EDITTrxVOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatusSortedBySegmentAndEffDate(String pendingStatus, List voInclusionList) throws Exception;

    /**
     * Finds all EDITTrxHistoryVOs with given segmentPK
     * @param segmentPK         segment the history is associated with
     * @param voInclusionList   associated VOs to be included
     * @return All EDITTrxHistoryVOs for the segment
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryVOBySegmentPK(long segmentPK, List voInclusionList) throws Exception;

    public void updateIssueEDITTrxVO(long segmentPK, List voInclusionList) throws Exception;

    public void deletePendingTrxBySegmentPK(List segmentPKList, List voInclusionList) throws Exception;
    
    public void deletePendingTrxBySegmentPK_AndNotTransactionType(List segmentPKList, String[] strings,
			List voInclusionList) throws Exception;

//    public EDITTrxHistoryVO[] composeEDITTrxHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate, List voInclusionList) throws Exception;

//    public CommissionHistoryVO[] composeCommissionHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate, List voInclusionList) throws Exception;

    public EDITTrxHistoryVO composeEDITTrxHistoryVOByPK(long editTrxHistoryPK, List voInclusionList);

    /**
     * Composes all edit trx history records for the given segment and from/to dates
     * @param segmentPK
     * @param fromDate
     * @param toDate
     * @param transactionType
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByEffectiveDate(long segmentPK,
                                                                   String fromDate,
                                                                   String toDate,
                                                                   String transactionType,
                                                                   List voInclusionList) throws Exception;

    public TransactionPriorityVO composeTransactionPriorityVOByPK(long transactionPriorityPK, List voInclusionList) throws Exception;

    /**
     * Composes the TransactionPriority record where the TransactionTypeCT matches the transactionTypeCT parameter
     * value
     * @param transactionTypeCT
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public TransactionPriorityVO composeTransactionPriorityVOByTransactionType(String transactionTypeCT, List voInclusionList) throws Exception;

    public SuspenseVO[] composeAccountingPendingSuspenseVO(String processDate, List voInclusionList) throws Exception;

    public SuspenseVO[] composeSuspenseVO(List voInclusionList) throws Exception;

    public SuspenseVO[] composeCashBatchSuspenseVO(List voInclusionList) throws Exception;

    public SuspenseVO composeSuspenseVO(long suspensePK, List voInclusionList) throws Exception;

    public SuspenseVO[] composeSuspenseVO(String contractNumber, List voInclusionList) throws Exception;

    public SuspenseVO[] composeSuspenseVOByUserDefNumber(String userDefNumber, List voInclusionList) throws Exception;

    public SuspenseVO[] composeSuspenseVOByDirection(String direction, List voInclusionList) throws Exception;

    public CashBatchContractVO[] composeCashBatchContractByCBContractPK(long cashBatchContractPK) throws Exception;

    public void saveEditTrxChanges(EDITTrxVO[] editTrxVOs, String operator, long segmentPK) throws Exception;

    /**
     * Saves the EDITTrx record non-recursively (no children will be saved)
     * @param editTrxVO
     * @throws Exception
     */
    public void saveEDITTrxVONonRecursively(EDITTrxVO editTrxVO) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateStatus(String updateStatus, List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommHistVOByPlacedAgentPKAndUpdateStatus(long placedAgentPK, String[] updateStatus, List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPKExcludingUpdateStatus(long[] placedAgentPKs,
                                                                                                 String[] updateStatuses,
                                                                                                  List voInclusionList) throws Exception;
    
    //    public CommissionHistoryVO[] composeCommHistoryByPlacedAgentTransactionFromToDates(long[] placedAgentPKs,
    //                                                                                        String transactionType,
    //                                                                                         String fromDate,
    //                                                                                          String toDate,
    //                                                                                           List voInclusionList) throws Exception;


    public CommissionHistoryVO[] composeCommHistoryByPlacedAgentTransTypeDatesAndPolicy(
                                    long[] placedAgentPKs,
                                    String filterTransaction,
                                    EDITDate fromDate,
                                    EDITDate toDate,
                                    String contractNumber,
                                    List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPKAndCommissionType(long[] placedAgentPKs,
                                                                                             String commissionType,
                                                                                              List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPK(long placedAgentPK, List voInclusionList) throws Exception;

    /**
     * Compose the CommissionHistoryVO for the given PlacedAgent and Segment keys.
     * @param placedAgentPK
     * @param segmentFK
     * @param trxTypeCTs
     * @param voInclusionList
     * @return
     * @throws Exception 
     */
    public CommissionHistoryVO[] composeCommissionHistoryVOByAgentSnapshotPK_SegmentFK(long placedAgentPK,
                                                                                     long segmentFK, String[] trxTypeCTs,
                                                                                     List voInclusionList) throws Exception;

//    public CommissionHistoryVO[] composeCommissionHistoryVOByProcessDateGT_AND_PlacedAgentPK(String processDate, long placedAgentPK, List voInclusionList) throws Exception;

    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByEDITTrxPK(long editTrxPK, List voInclusionList) throws Exception;

    public String processCorrespondence(ProductStructureVO[] productStructureVOs) throws Exception;

    public CommissionHistoryVO composeCommissionHistoryVOByCommissionHistoryPK(long commissionHistoryPK, List voInclusionList) throws Exception;

    public CommissionHistoryVO composeCommissionHistoryVO(CommissionHistoryVO commissionHistoryVO, List voInclusionList) throws Exception;

    public EDITTrxHistoryVO[] composeEDITTrxHistoryBySegmentFKAndCycleDate(long[] segmentFK, String fromDate, String toDate, List voInclusionList) throws Exception;

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDatesForCompanyStructure(long companyStructureFK, String fromDate, String toDate, List voInclusionList) throws Exception;

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByProcessDateTrxType(long segmentPK,
                                                                         String fromDate,
                                                                          String toDate,
                                                                           String[] trxType,
                                                                            List voInclusionList)
                                                                       throws Exception;


    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(String updateDateTime, long[] placedAgentPKs, String[] statuses, List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommHistByUpdateDateTimeGTPlacedAgentPKBonusAmtAndStatus(String updateDateTime, long[] placedAgentPKs, String[] statuses, List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeLTPlacedAgentPK(long[] placedAgentPKs, String updateDateTime, List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTLTPlacedAgentPK(long[] placedAgentPKs, String currentUpdateDateTime, String priorUpdateDateTime, List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTELTEPlacedAgentPK(long[] placedAgentPKs, String currentUpdateDateTime, String priorUpdateDateTime, List voInclusionList) throws Exception;

    public long saveSuspense(SuspenseVO suspenseVO) throws Exception;

    public long saveSuspenseNonRecursively(SuspenseVO suspenseVO) throws Exception;

    public long saveCashBatchContract(CashBatchContractVO cashBatchContractVO) throws Exception;

    public void cleanSuspenseAfterAccounting() throws Exception;

    public QuoteVO performInforceQuote(String quoteDate, String quoteTypeCT, long segmentPK, String operator) throws Exception;

    public LoanPayoffQuoteVO performLoanPayoffQuote(String quoteDate, long segmentPK) throws Exception;

    public QuoteVO performNewBusinessQuote(QuoteVO quoteVO) throws Exception;

    public QuoteVO getNewBusinessQuote(QuoteVO quoteVO) throws Exception;

    public EDITTrxHistoryVO composeEDITTrxHistoryVOClosestToQuoteDate(String quoteDate, long segmentPK, List voInclusionList) throws Exception;

    public CommissionHistoryVO[] composeCommissionHistoryVOForTaxableIncomeCheckTotals(String lastStatementDateTime,
                                                                                        long placedAgentPK,
                                                                                         List voInclusionList)
                                                                                   throws Exception;

    public String reverseNewBusinessSuspense(long suspensePK, String operator) throws Exception;

    /**
     * From EDITTrxHistory table, get all the keys of the records with AccountPendingInd = "Y" and the date requested.
     * @param accountPendingInd
     * @param processDate
     * @return  array of EDITTrxHistory keys
     * @throws Exception
     */
//    public long[] findEDITTrxHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingInd, String processDate) throws Exception;

    /**
     *  From CommissionHistory table, get all the keys of the records with AccountPendingInd = "Y" and the date requested.
     * @param accountPendingInd
     * @param processDate
     * @return  array of CommissionHistory keys
     * @throws Exception
     */
//    public long[] findCommissionHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingInd, String processDate) throws Exception;

    /**
     *  From Suspense table, get all the keys of the records with AccountPendingInd = "Y" and the date requested.
     * @param processDate
     * @return array of Suspense keys
     * @throws Exception
     */
    public long[] findAccountingPendingSuspenseEntries(String processDate) throws Exception;
    /**
     * From all the InSuspense table records get all the SuspenseFKs
     * @return
     * @throws Exception
     */
    public long[] findAllSuspensePKs() throws Exception;

    /**
     * Get the CommissionHistory keys where the UpdateStatus in the record equals "U"
     * @param updateStatus
     * @return
     * @throws Exception
     */
    public long[] findCommissionHistoryPKsByUpdateStatus(String updateStatus) throws Exception;

    /**
     * Finds Suspense with the given userDefNumber
     * @param userDefNumber
     * @return array of SuspenseVO objects found
     * @throws Exception
     */
    public SuspenseVO[] findSuspenseByUserDefNumber(String userDefNumber) throws Exception;

    public ElementLockVO lockElement(long cashBatchContractPK, String userName) throws EDITLockException;

    public int unlockElement(long lockTablePK);

    public String runGAAPPremiumExtract(String startDate, String endDate, ProductStructureVO[] productStructureVOs) throws Exception;

    /**
     * Creates the Tax Extract
     * @param startDate
     * @param endDate
     * @param productStructureVOs
     * @param taxRptType
     * @param taxYear
     * @return
     * @throws Exception
     */
    public String runTaxExtract(String startDate,
                                String endDate,
                                ProductStructureVO[] productStructureVOs,
                                String taxRptType,
                                String taxYear,
                                String fileType) throws Exception;

    /**
     * Generates the Controls And Balances Report
     * @param companyName
     * @param cycleDate
     * @return
     * @throws Exception
     */
    public String runControlsAndBalancesReport(String companyName, String cycleDate) throws Exception;

	public BucketHistoryVO[] composeBucketHistoryByBucketFK(long bucketFK, List VOInclusionList) throws Exception;

    public long getNextAvailableKey();

    public long saveCommissionHistoryAdjustment(CommissionHistoryVO commissionHistoryVO) throws Exception;

//    /**
//     * Search for updated Unit Value entries (where updateStatus = "H"), and update the pendingStatus on the
//     * appropriate transactions/contracts with "B" or "F"
//     * @return
//     * @throws Exception
//     */
//    public boolean unitValueUpdate() throws Exception;
//
//    /**
//     * Generates report containing all Hedge Fund Notifications for the parameter date specified (corr date <= param date)
//     * and updates the correspondence record and the EDITTrx record with the correct notification amount (calculated in PRASE)
//     * @param notifyCorrDate
//     * @return
//     * @throws Exception
//     */
//    public boolean runHedgeFundNotification(String notifyCorrDate) throws Exception;

    /**
     * Imports new unit values from the flat file specified in the EDITServicesConfig.xml file for the given mmddyy param
     * @param uvImportMonth
     * @param uvImportDay
     * @param uvImportYear
     * @throws Exception
     */ 
    public String importUnitValues(String uvImportMonth, String uvImportDay, String uvImportYear) throws Exception;

    /**
     * Creates the Activity Download File for the given cycle date
     * @param cycleDate
     * @throws Exception
     */
    public void createActivityFile(String cycleDate) throws Exception;

    /**
     * Retrieves all EDITTrx for the given segment, with pending status equal to the status(es) passed in and an effective date
     * equal to or greater than the driving effective date.
     * @param segmentFK
     * @param pendingStatus
     * @param drivingEffDate
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOBySegmentPendingStatusAndEffDate(long segmentFK,
                                                                         String[] pendingStatus,
                                                                          String drivingEffDate,
                                                                           List voInclusionList) throws Exception;

    /**
     * Retrieves all EDITTrxCorrespondence records with a correspondence date <= the param date specified, whose
     * correspondenceType equals the correspondence type specified.
     * @param notifyCorrDate
     * @param correspondenceType
     * @param voInclusionList
     * @return
     */
    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByCorrTypeAndDate(String notifyCorrDate,
                                                                                     String[] correspondenceTypes,
                                                                                     List voInclusionList);

    /**
     * Retrieves all EDITTrxCorrespondece records with the specified Transaction Correspondence PK
     * @param trxCorrespondencePK
     * @return
     */
    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByTrxCorrPK(long trxCorrespondencePK) throws Exception;
    

    /**
     * Retrieves all suspense records created for the specified hedge fund (filteredFundFK)
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public SuspenseVO[] composeSuspenseVOByFilteredFundFK(long filteredFundFK, List voInclusionList) throws Exception;

    /**
     * Retrieves all EDITTrx records that affect the specified hedge fund (filteredFundFK)
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEditTrxVOByTrxTypeAndFilteredFundFK(long filteredFundFK, List voInclusionList) throws Exception;

    /**
     * Retrieves all BucketHistory records for the given investment and editTrxHistory keys
     * @param investmentFK
     * @param editTrxHistoryPK
     * @return
     * @throws Exception
     */
    public BucketHistoryVO[] composeBucketHistoryByInvestmentAndEditTrxHistory(long investmentFK,
                                                                                long editTrxHistoryPK,
                                                                                 List voInclusionList) throws Exception;

    /**
     * Retrieves all InvestmentHistory records for the given investment and editTrxHistory keys
     * @param investmentFK
     * @param editTrxHistoryPK
     * @return
     * @throws Exception
     */
    public InvestmentHistoryVO[] findInvestmentHistoryByInvestmentAndEditTrxHistory(long investmentFK,
                                                                                     long editTrxHistoryPK) throws Exception;

    /**
     * Saves the EDITTrxCorrespondence record to the database
     * @param editTrxCorrespondenceVO
     * @return
     * @throws Exception
     */
    public long saveEDITTrxCorrespondence(EDITTrxCorrespondenceVO editTrxCorrespondenceVO) throws Exception;

    public EDITTrxVO composeOriginatingEDITTrxVOByOriginatingEditTrxFK(long originatingTrxFK, List voInclusionList) throws Exception;

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public EDITTrxVO findEDITTrxVOBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK);

    /**
     * Retrieves the total gross amount of all withdrawals for the specified segment processed for the specified tax year
     * @param taxYear
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getWithdrawalGrossAmountTaxYearToDate(int taxYear, long segmentFK) throws Exception;

    /**
     * Retrieves the total gross amount of all RMD (RW) transactions for the specified segment processed
     * for the specified tax year.
     * @param taxYear
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getRMDGrossAmountTaxYearToDate(int taxYear, long segmentFK) throws Exception;

    /**
     * Retrieves the AccumulatedValue from the previous Calendar Year End Transaction (if it exists)
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getPriorCYAccumulatedValue(long segmentFK) throws Exception;

    /**
     * Retrieves any existing RW transactions for the specified segment
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public GroupSetupVO[] findRMDTransaction(long segmentFK, String trxType, List voInclusionList) throws Exception;

	/**
     * finder method for retrieving overdue charge remaining
     * @param editTrxFK
     * @return
     */
    public OverdueChargeVO[] findOverdueChargeByEDITTrxFK(long editTrxFK);

    /**
     * Checks the given investments for their final price (unit value)
     * @param investmentVOs
     * @return
     * @throws Exception
     */
    public boolean checkForForwardPrices(InvestmentVO[] investmentVOs,
                                         String effectiveDate) throws Exception;
    
    
    /**
     * Checks the given investments for their final price (unit value)
     * @param investmentVOs
     * @return
     * @throws Exception
     */
    public boolean checkForForwardPricesWithChargeCodes(InvestmentVO[] investmentVOs, 
                                         String effectiveDate) throws Exception;

    /**
     * To import cash clearance values.
     * @param importDate
     * @return
     */
    public String importCashClearanceValues(String importDate);

    /**
     * Composes all editTrxHistoryVOs for the given dates using the given dateType (effective/process date)
     * that affected the given filtered fund.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDate_And_Fund(String startDate,
                                                                   String endDate,
                                                                   String dateType,
                                                                   long filteredFundFK,
                                                                   List voInclusionList) throws Exception;

    /**
     * Composes all editTrxHistoryVOs for the given dates using the given dateType (effective/process date)
     * that affected the given filtered fund.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param chargeCodeFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDate_And_Fund(String startDate,
                                                                    String endDate,
                                                                    String dateType,
                                                                    long filteredFundFK,
                                                                    long chargeCodeFK,
                                                                    List voInclusionList) throws Exception;

    /**
     * Composes all editTrxHistoryVOs for the given dates using the given dateType (effective/process date)
     * that affected the given filtered fund.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param chargeCodeFK
     * @param segmentFKs
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDateSegmentFund(String startDate,
                                                                     String endDate,
                                                                     String dateType,
                                                                     long filteredFundFK,
                                                                     long chargeCodeFK,
                                                                     long[] segmentFKs,
                                                                     List voInclusionList) throws Exception;

    /**
     * Finds all EDITTrxVO that match the specified transactionType and pending status that fall between
     * the specified dates.
     * @param segmentPK
     * @param transactionTypeCT
     * @param fromDate
     * @param toDate
     * @param pendingStatus
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] findEDITTrxBySegment_TrxType_EffectiveDate_AND_PendingStatus(long segmentPK,
                                                                                    String transactionTypeCT,
                                                                                    String fromDate,
                                                                                    String toDate,
                                                                                    String pendingStatus) throws Exception;

    /**
     * Save the transactionPriorityVO passed in
     * @param transactionPriorityVO
     */
    public void saveTransactionPriority(TransactionPriorityVO transactionPriorityVO)  throws Exception;

    /**
     * Delete the TransactionPriority record using its PK.
     * @param transactionPriorityPK
     * @throws Exception
     */
    public void deleteTransactionPriority(long transactionPriorityPK) throws Exception;

    public InvestmentAllocationOverrideVO[] getInvestmentAllocationOvrds(long originatingTrxFK) throws Exception;

    /**
     * Retrieves all ClientSetupVOs associated with the given contractClientFK
     * @param contractClientFK
     * @return
     * @throws Exception
     */
    public ClientSetupVO[] composeClientSetupByContractClientFK(long contractClientFK) throws Exception;

    /**
     * Retrieves all ContractSetupVOs associated with the given segmentFK
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public ContractSetupVO[] composeContractSetupBySegmentFK(long segmentFK) throws Exception;

    /**
     * Update CashBatchContract after processing accounting
     * @param cashBatchContract     The CashBatchContract record to be updated
     * @param finalUpdateIndicator  A flag indicating the last CashBatchContract record - will clear hibernate session
     */
    public void updateCashBatchContract(CashBatchContract cashBatchContract, String finalUpdateIndicator);

    int updateCashBatchContractAccountPendingIndicator(long primaryKey, String status);

    /**
     * Mark the CashBatchContract Record as Completed
     * @param cashBatchContract
     */
    public void completeCashBatchContract(CashBatchContract cashBatchContract);

    /**
     * Save the given Suspense records with the update PendingSuspenseAmount
     * @param suspense
     */
    public String saveSuspenseForPendingAmount(Suspense suspense);

    /**
     * Save the given Suspense records with the update PendingSuspenseAmount (set after trx save)
     * @param suspense
     */
    public String saveSuspenseForTransaction(Suspense suspense);

    /**
     * Delete the given Set of OutSuspense records
     * @param outSuspenseSet
     * @return
     */
    public String deleteOutSuspense(Set outSuspenseSet);

    public int deleteChange(long chargePK) throws Exception;

    public String saveQuoteReversal(Long editTrxPK, Long segmentPK, String operator, String reversalReasonCode);

    public String deleteTransaction(EDITTrx editTrx, Segment segment);

    public void buildTransactionsFromQuote(String transactionType, QuoteVO quoteVO, Long segmentPK, EDITDate quoteDate, String operator, String notTakenOverrideInd);

    public String processTransfer(List suspenseTransferRows, Suspense suspense, String reasonCode, String operator);

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByEffectiveDateForAccting(long segmentPK,
                                                                             String fromDate,
                                                                             String toDate,
                                                                             String transactionType,
                                                                             List voInclusionList) throws Exception;
                                                                             
    public EDITTrxVO[] composeEDITTrxVOBySegmentPK_AND_PendingStatus(long segmentPK, String[] pendingStatus, List voInclusionList) throws Exception;
	                                                                            
}
