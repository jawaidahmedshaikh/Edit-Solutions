package batch.business;

import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.common.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 20, 2005
 * Time: 4:44:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface  Batch
{
    public static final String SEG_MARKER_BEGIN = "<seg>";
    public static final String SEG_MARKER_END = "</seg>";

    public static final String PROCESSED_WITHOUT_ERRORS = SEG_MARKER_BEGIN + "PROCESSED_WITHOUT_ERRORS" + SEG_MARKER_END;
    public static final String PROCESSED_WITH_ERRORS = SEG_MARKER_BEGIN + "PROCESSED_WITH_ERRORS" + SEG_MARKER_END;
    public static final String PROCESSED_BUT_NO_ELEMENTS_FOUND = SEG_MARKER_BEGIN + "PROCESSED_BUT_NO_ELEMENTS_FOUND" + SEG_MARKER_END;
    public static final String PROCESSING = SEG_MARKER_BEGIN + "PROCESSING" + SEG_MARKER_END;
    public static final String PROCESSING_NO_STATS_AVAILABLE = SEG_MARKER_BEGIN + "PROCESSING_NO_STATS_AVAILABLE" + SEG_MARKER_END;

    public static final String SYNCHRONOUS = "SYNCHRONOUS";
    public static final String ASYNCHRONOUS = "ASYNCHRONOUS";

    public static final String BATCH_JOB_IMPORT_UNIT_VALUES = "IMPORT_UNIT_VALUES";
    public static final String BATCH_JOB_UPDATE_UNIT_VALUES = "UPDATE_UNIT_VALUES";
    public static final String BATCH_JOB_RUN_HEDGEFUND_NOTIFICATION = "RUN_HEDGEFUND_NOTIFICATION";
    public static final String BATCH_JOB_RUN_DAILY_BATCH = "RUN_DAILY_BATCH";
    public static final String BATCH_JOB_CREATE_BANK_EXTRACTS = "CREATE_BANK_EXTRACTS";
    public static final String BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML = "CREATE_ACCOUNTING_EXTRACT_XML";
    public static final String BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS = "CREATE_CORRESPONDENCE_EXTRACTS";
    public static final String BATCH_JOB_CREATE_EQUITY_INDEX_HEDGE_EXTRACTS = "CREATE_EQUITY_INDEX_HEDGE_EXTRACTS";
    public static final String BATCH_JOB_CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS = "CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS";
    public static final String BATCH_JOB_CREATE_TRANSACTION_ACTIVITY_FILE = "TRANSACTION_ACTIVITY_FILE";
    public static final String BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES = "IMPORT_CASH_CLEARANCE_VALUES";
    public static final String BATCH_JOB_UPDATE_AGENT_COMMISSIONS = "UPDATE_AGENT_COMMISSIONS";
    public static final String BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_CK = "SETUP_AGENT_COMMISSION_CHECKS_CK";
    public static final String BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_EFT = "SETUP_AGENT_COMMISSION_CHECKS_EFT";
    public static final String BATCH_JOB_PROCESS_COMMISSION_CHECKS = "PROCESS_COMMISSION_CHECKS";
    public static final String BATCH_JOB_GENERATE_COMMISSION_STATEMENTS = "GENERATE_COMMISSION_STATEMENTS";
    public static final String BATCH_JOB_CREATE_VALUATION_EXTRACTS = "CREATE_VALUATION_EXTRACTS";
    public static final String BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS = "CREATE_TAX_RESERVES_EXTRACTS";
    public static final String BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_FLAT = "CREATE_ACCOUNTING_EXTRACT_FLAT";
    public static final String BATCH_JOB_CLOSE_ACCOUNTING = "CLOSE_ACCOUNTING";
    public static final String BATCH_JOB_CHECK_OFAC_FOR_ALL_CLIENTS = "CHECK_OFAC_FOR_ALL_CLIENTS";
    public static final String BATCH_JOB_UPDATE_REINSURANCE_BALANCES = "UPDATE_REINSURANCE_BALANCES";
    public static final String BATCH_JOB_SETUP_REINSURANCE_CHECKS = "SETUP_REINSURANCE_CHECKS";
    public static final String BATCH_JOB_GENERATE_RMD_NOTIFICATIONS = "GENERATE_RMD_NOTIFICATIONS";
    public static final String BATCH_JOB_PROCESS_COI_REPLENISHMENT = "PROCESS_COI_REPLENISHMENT";
    public static final String BATCH_JOB_STAGE_TABLES = "STAGE_TABLES";
    public static final String BATCH_JOB_UPDATE_AGENT_BONUSES = "UPDATE_AGENT_BONUSES";
    public static final String BATCH_JOB_PROCESS_AGENT_BONUS_CHECKS = "PROCESS_AGENT_BONUS_CHECKS";
    public static final String BATCH_JOB_GENERATE_BONUS_COMMISSION_STATEMENTS = "GENERATE_BONUS_COMMISSION_STATEMENTS";
    public static final String BATCH_JOB_PROCESS_REINSURANCE_CHECKS = "PROCESS_REINSURANCE_CHECKS";
	public static final String BATCH_JOB_CREATE_PAYOUT_TRX_EXTRACT = "CREATE_PAYOUT_TRX_EXTRACT";
    public static final String BATCH_JOB_RUN_ALPHA_EXTRACT = "RUN_ALPHA_EXTRACT";
    public static final String BATCH_JOB_RUN_PENDING_REQUIREMENTS_EXTRACT = "RUN_PENDING_REQUIREMENTS_EXTRACT";
    public static final String BATCH_JOB_CREATE_CLIENT_ACCOUNTING_EXTRACT = "CREATE_CLIENT_ACCOUNTING_EXTRACT";
    public static final String BATCH_JOB_CREATE_BILL_EXTRACT = "CREATE_BILL_EXTRACT";
    public static final String BATCH_JOB_CREATE_PRD_EXTRACT = "CREATE_PRD_EXTRACT";
    public static final String BATCH_JOB_RUN_PRD_COMPARE = "RUN_PRD_COMPARE";
    public static final String BATCH_JOB_MONTHLY_VALUATION = "RUN_MONTHLY_VALUATION";
    public static final String BATCH_JOB_ANNUAL_STATEMENTS = "RUN_ANNUAL_STATEMENTS";
    public static final String BATCH_JOB_POLICY_PAGES = "RUN_POLICY_PAGES";
    public static final String BATCH_JOB_RUN_PRD_ODS_EXTRACT = "RUN_PRD_ODS EXTRACT";
    public static final String BATCH_JOB_RUN_DATAWAREHOUSE = "RUN_DATA_WAREHOUSE";
    public static final String BATCH_JOB_RUN_CHECK_NUMBER_IMPORT = "RUN_CHECK_NUMBER_IMPORT";
    public static final String BATCH_JOB_RUN_MANUAL_ACCOUNTING_IMPORT = "RUN_MANUAL_ACCOUNTING_IMPORT";
    public static final String BATCH_JOB_RUN_FUN_ACTIVITY_REPORT = "RUN_FUN_ACTIVITY_REPORT";
    public static final String BATCH_JOB_IMPORT_CASH_BATCH = "IMPORT_CASH_BATCH";
    public static final String BATCH_JOB_RUN_WORKSHEET = "RUN WORKSHEET";
    public static final String BATCH_JOB_RUN_BANK_FOR_NACHA = "CREATE_BANK_FOR_NACHA" ;

    /**
     * Permits a place-holder when the user does [not] supply values for
     * optional parameters.
     */
    public static final String NULL = "NULL";

    /**
     * @see event.batch.UnitValueImportProcessor#importUnitValues(String)
     * @param username
     * @param password
     * @param callMethod
     * @see Batch.ASYNCHRONOUS callMethod parameter to use for an asynchronous batch request.
     * @see Batch.SYNCHRONOUS callMethod parameter to use for a sychronous batch request.
     * @return Batch.PROCESSED_WITHOUT_ERRORS or Batch.PROCESSED_WITH_ERRORS
     */
    public String importUnitValues(String importDate, String username, String password, String callMethod);
    
    /**
     * Search for updated Unit Value entries (where updateStatus = "H"), and update the pendingStatus on the
     * appropriate transactions/contracts with "B" or "F"
     * @see event.batch.UnitValueUpdateProcessor#updateEDITTrxForUnitValues()
     * @param callMethod
     * @param username
     * @param password
     * @see Batch.ASYNCHRONOUS callMethod parameter to use for an asynchronous batch request.
     * @see Batch.SYNCHRONOUS callMethod parameter to use for a sychronous batch request.
     * @return Batch.PROCESSED_WITHOUT_ERRORS or Batch.PROCESSED_WITH_ERRORS
     */
    public String unitValueUpdate(String username, String password, String callMethod);
    
    /**
     * Generates report containing all Hedge Fund Notifications for the parameter date specified (corr date <= param date)
     * and updates the correspondence record and the EDITTrx record with the correct notification amount (calculated in PRASE)
     * @see event.batch.HedgeFundNotificationProcessor#runHedgeFundNotification()
     * @param callMethod
     * @param username
     * @param password
     * @see Batch.ASYNCHRONOUS callMethod parameter to use for an asynchronous batch request.
     * @see Batch.SYNCHRONOUS callMethod parameter to use for a sychronous batch request.
     * @return Batch.PROCESSED_WITHOUT_ERRORS or Batch.PROCESSED_WITH_ERRORS
     */
    public String runHedgeFundNotification(String notificationDate, String username, String password, String callMethod);   

    /**
     * @see event.financial.group.trx.GroupTrx#executeBatch(String, String, String)
     * @param processDate
     * @param companyName
     * @param operator
     * @param username
     * @param password
     * @return Batch.PROCESSED_WITHOUT_ERRORS or Batch.PROCESSED_WITH_ERRORS
     */
    public String processDailyBatch(String processDate, String companyName, String operator, String username, String password, String callMethod);

    /**
     * @see contract.batch.BankProcessor#createBankExtracts(String, String)
     * @param companyName
     * @param contractId
     * @param outputFileType
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createBankExtracts(String companyName, String contractId, String outputFileType, String username, String password, String callMethod);

    /**
     * @see accounting.ap.AccountingProcessor#createAccountingExtract_XML(String, String)
     * @param processDate
     * @param suppressExtract
     * @param outputfiletype (will be Flat or XML)
     * @param username
     * @param password
     * @return
     */
    public String createAccountingExtract_XML(String processDate, String suppressExtract, String outputFileType, String username, String password, String callMethod);

    /**
     * @see event.batch.CorrespondenceProcessor#createCorrespondenceExtracts(String)
     * @param companyName
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createCorrespondenceExtracts(String companyName, String username, String password, String callMethod);

    /**
     * @see event.batch.EquityIndexHedgeProcessor#createEquityIndexHedgeExtract(String)
     * @param companyStructure
     * @param runDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createEquityIndexHedgeExtract(String companyStructure, String runDate, String createSubBucketInd, String username, String password, String callMethod);

    /**
     * @see event.batch.GAAPPremiumExtractProcessor#createPremiumReservesExtract(String, String, String)
     * @param startDate
     * @param endDate
     * @param companyStructure
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createPremiumReservesExtract(String startDate, String endDate, String companyStructure, String username, String password, String callMethod);

    /**
     * @see event.batch.ActivityFileInterfaceProcessor#createTransactionActivityFile(String)
     * @param cycleDate
     * @param username
     * @param password
     * @param callMethod
     */
    public String createTransactionActivityFile(String cycleDate, String username, String password, String callMethod);

    /**
     * @see event.batch.CashClearanceImportProcessor#importCashClearanceValues(String)
     * @param importDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String importCashClearanceValues(String importDate, String username, String password, String callMethod);

    /**
     * @see agent.CommissionController#updateAgentCommissions(String)
     * @param companyName
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String updateAgentCommissions(String companyName, String username, String password, String callMethod);

    /**
     * @see role.CheckController#setupAgentCommissionChecks(String, String, String)
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String setupAgentCommissionChecksCK(String paymentModeCT, String forceOutMinBal, String operator, String username, String password, String callMethod);

    /**
     * @see role.CheckController#setupAgentCommissionChecks(String, String, String)
     * @param operator
     * @param username
     * @param username
     * @param callMethod
     * @return
     */
    public String setupAgentCommissionChecksEFT(String paymentModeCT, String forceOutMinBal, String operator, String username, String password, String callMethod);

    /**
     * @see event.financial.group.trx.GroupTrx#processCommissionChecks(String)
     * @param cycleDate
     * @param username
     * @param password
     * @param callMethod
     */
    public String processCommissionChecks(String cycleDate, String username, String password, String callMethod);

    /**
     * @see agent.CommissionStatement#generateCommissionStatements(String, String, String)
     * @param contractCodeCT
     * @param processDate
     * @param username
     * @param password
     * @param outputFileType
     * @param callMethod
     * @return
     */
    public String generateCommissionStatements(String contractCodeCT, 
                                               String paymentModeCT,
                                               String processDate,
                                               String outputFileType,
                                               String username,
                                               String password,                                               
                                               String callMethod);

    /**
     * @see reporting.batch.ReportsProcessor#createValuationExtracts(String, String)
     * @param companyName
     * @param valuationDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createValuationExtracts(String companyName, String valuationDate, String username, String password, String callMethod);

    /**
     * @see event.batch.TaxExtractProcessor#createTaxReservesExtract(String, String, String, String, String, String)
     * @param startDate
     * @param endDate
     * @param companyStructure
     * @param taxRptType
     * @param taxYear
     * @param fileType
     * @param username
     * @param password
     * @return
     */
    public String createTaxReservesExtract(String startDate, String endDate, String companyStructure, String taxRptType, String taxYear, String fileType, String username, String password, String callMethod);

    /**
     * @see reporting.batch.ReportsProcessor#createAccountingExtract_FLAT(String)
     * @param accountingPeriod
     * @param username
     * @param password
     * @param callMethod
     */
    public String createAccountingExtract_FLAT(String accountingPeriod, String username, String password, String callMethod);

    /**
     * @see reporting.batch.ReportsProcessor#closeAccounting(String, String)
     * @param marketingPackageName
     * @param accountingPeriod
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String closeAccounting(String marketingPackageName, String accountingPeriod, String username, String password, String callMethod);

    /**
     * @see client.component.EditOFACCheck#ofacCheckOnAllClients()
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String ofacCheckOnAllClients(String username, String password, String callMethod);

    /**
     * @see reinsurance.business.Reinsurance#updateReinsuranceBalances(String, String)
     * @param companyName
     * @param operator
     * @param username
     * @param password
     */
    public String updateReinsuranceBalances(String companyName, String operator, String username, String password, String callMethod);

    /**
     * @see reinsurance.ReinsuranceProcess#setupReinsuranceChecks(String, String)
     * @param companyName
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     */
    public String setupReinsuranceChecks(String companyName, String operator, String username, String password, String callMethod);

    /**
     * @see event.batch.RMDNotificationProcessor#generateRmdNotifications(String, String, String)
     * @param companyStructure
     * @param startRmdAnnualDate
     * @param endRmdAnnualDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String generateRmdNotifications(String companyStructure, String startRmdAnnualDate, String endRmdAnnualDate, String username, String password, String callMethod);

    /**
     * @see event.batch.COIReplenishmentProcessor#processCoiReplenishment(String, String)
     * @param companyStructure
     * @param runDate
     * @param contractNumber
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String processCoiReplenishment(String companyStructure, String runDate, String contractNumber,
                                          String username, String password, String callMethod);

    /**
     * @see staging.Staging#stageTables(String)
     * @param processDate
     * @param username
     * @param password
     * @param callMethod
     */
    public String stageTables(String processDate, String username, String password, String callMethod);

    /**
     * @see agent.BonusProgram#updateAgentBonuses(String)
     * @param username
     * @param password
     * @param callMethod
     */
    public String updateAgentBonuses(String username, String password, String callMethod);

    /**
     * @see agent.ParticipatingAgent#processAgentBonusChecks(String, String)
     * @param processDate
     * @param frequencyCT
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String processAgentBonusChecks(String processDate, String frequencyCT, String operator, String username, String password, String callMethod);

    /**
     * @see agent.ParticipatingAgent#generateBonusCommissionStatements(String, String)
     * @param contractCodeCT
     * @param processDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String generateBonusCommissionStatements(String contractCodeCT, String processDate, String mode, String username, String password, String callMethod);

    /**
     *
     * @param cycleDate
     * @param username
     * @param password
     * @param callMethod
     */
    public String processReinsuranceChecks(String cycleDate, String username, String password, String callMethod);

    /**
     * Run Alpha extract (dumping client information into extract file)
     * @param callMethod
     * @return
     */
    public String runAlphaExtract(String extractDate, String username, String password, String callMethod);

    /**
     * Run Pending Requirements extract (dumping all outstanding contract requirements for the date period specified)
     * @param extractDate
     * @param toDate
     * @param callMethod
     * @return
     */
    public String runPendingRequirementsExtract(String extractDate, String username, String password, String callMethod);

    /**
     *
     * @param selectedMarketingPackage
     * @param selectedFunds
     * @param selectedDateType
     * @param startDate
     * @param endDate
     */
    public String runFundActivityExport(String selectedMarketingPackage, String selectedFunds, String selectedDateType, String startDate, String endDate, String username, String password, String callMethod);

    public String createPRDExtract(String extractDate, String username, String password, String callMethod) throws EDITCaseException;

    public String runPRDCompare(String extractDate, String username, String password, String callMethod) throws EDITCaseException;

    public String runMonthlyValuation(String extractDate, String isAsync, String isYev, String mevContractNumber, String username, String password, String callMethod) throws EDITCaseException;

    public String runAnnualStatements(String extractDate, String username, String password, String callMethod) throws EDITCaseException;

    public String runPolicyPages(String extractDate, String username, String password, String callMethod) throws EDITCaseException;

    public String runPRDCompareExtract(String extractDate, String extractType, String username, String password, String callMethod) throws EDITCaseException;

    public String createListBillExtract(String listBillDate, String username, String password, String callMethod) throws EDITCaseException;

    public String runDataWarehouse(String extractDate, String companyName, String caseNumber, String groupNumber, String username, String password, String callMethod);

    public String runWorksheet(String batchId, String username, String password, String callMethod);
    
    /**
     * Imports data from a file and creates CashBatchContracts and Suspenses
     *
     * @param importDate                    date in the fileName of file to be imported.  The path is assumed to be the
     *                                      UnitValuesImport value in the EDITServicesConfig file
     * @param operator                      user doing the import
     *
     * @return  array of Strings containing response messages 
     */
    public String importCashBatch(String importDate, String operator, String username, String filename, String password, String callMethod);

    public String createClientAccountingExtract(String extractDate, String companyName, String username, String password, String callMethod) throws EDITCaseException;

	public String createPayoutTrxExtract(EDITDate fromDate, EDITDate toDate, String dateOption, String callMethod);

    public String runCheckNumberImport(String paramDate, String companyName, String username, String password, String callMethod) throws EDITCaseException;

    public String runManualAccountingImport(String paramDate, String username, String password, String callMethod) throws EDITCaseException;

    /**
     * EFT JOB will produce direct output for the requested company to their bank
     * @param companyName
     * @param processDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     * @throws EDITCaseException
     */
    public String createBankForNACHA(String companyName, String processDate, String isIndividual, String createCashBatchFile, String username, String password, String callMethod) throws EDITCaseException;
}
