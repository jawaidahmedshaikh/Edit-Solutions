/*
 * User: gfrosti
 * Date: May 24, 2005
 * Time: 10:29:27 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package batch.component;

import accounting.ap.*;
import agent.*;
import batch.business.*;
import client.component.*;
import contract.batch.*;
import group.PayrollDeductionSchedule;
import edit.services.*;
import edit.services.db.hibernate.*;
import edit.services.config.*;
import edit.services.logging.*;
import edit.common.*;
import edit.common.exceptions.EDITCaseException;
import event.batch.*;
import event.financial.group.trx.*;
import event.*;
import logging.*;
import reinsurance.*;
import reporting.batch.*;
import role.*;
import staging.*;

import java.lang.reflect.*;
import java.util.*;
import java.io.*;

import com.editsolutions.prd.service.PRDCompareServiceImpl;

import billing.*;
import billing.BillGroup;
import billing.BillSchedule;
import client.Preference;
import fission.utility.*;



public class BatchComponent implements BatchComponentMBean
{
    /**
     * @see Batch#importUnitValues(String,String,String,String)
     */
    public String importUnitValues(String importDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(UnitValueImportProcessor.class, "importUnitValues", new Class[] { String.class }, new Object[] { importDate }, Batch.BATCH_JOB_IMPORT_UNIT_VALUES, callMethod);

        return batchStatus;
    }
    
    /**
     * @see Batch#updateUnitValues()
     */
    public String unitValueUpdate(String username, String password, String callMethod)
    {
        String batchStatus = dispatch(UnitValueUpdateProcessor.class, "updateEDITTrxForUnitValues", new Class[] { }, new Object[] { }, Batch.BATCH_JOB_UPDATE_UNIT_VALUES, callMethod);

        return batchStatus;        
    }
    
    /**
     * @see Batch#runHedgeFundNotification()
     */
    public String runHedgeFundNotification(String notificationDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(HedgeFundNotificationProcessor.class, "runHedgeFundNotification", new Class[] { String.class }, new Object[] { notificationDate }, Batch.BATCH_JOB_RUN_HEDGEFUND_NOTIFICATION, callMethod);

        return batchStatus;                
    }

    /**
     * @see Batch#processDailyBatch(String,String,String,String,String,String)
     * @param processDate
     * @param companyName
     * @param operator
     * @param username
     * @param password
     */
    public String processDailyBatch(String processDate, String companyName, String operator, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(GroupTrx.class, "executeBatch", new Class[] { String.class, String.class, String.class }, new Object[] { processDate, companyName, operator }, Batch.BATCH_JOB_RUN_DAILY_BATCH, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createBankExtracts(String,String,String,String,String)
     * @param companyName
     * @param contractId
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createBankExtracts(String companyName, String contractId, String outputFileType, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(BankProcessor.class, "createBankExtracts", new Class[] { String.class, String.class, String.class }, new Object[] { companyName, contractId, outputFileType }, Batch.BATCH_JOB_CREATE_BANK_EXTRACTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createAccountingExtract_XML(String,String,String,String,String)
     * @param processDate
     * @param suppressExtract
     * @param outputFileType (will be Flat or XML)
     * @param username
     * @param password
     * @return
     */
    public String createAccountingExtract_XML(String processDate, String suppressExtract, String outputFileType, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(AccountingProcessor.class, "createAccountingExtract_XML", new Class[] { String.class, String.class, String.class }, new Object[] { processDate, suppressExtract, outputFileType }, Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createCorrespondenceExtracts(String,String,String,String)
     * @param companyName
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createCorrespondenceExtracts(String companyName, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(CorrespondenceProcessor.class, "createCorrespondenceExtracts", new Class[] { String.class }, new Object[] { companyName }, Batch.BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createEquityIndexHedgeExtract(String,String,String,String)
     * @param companyStructure
     * @param runDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createEquityIndexHedgeExtract(String companyStructure, String runDate, String createSubBucketInd, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(EquityIndexHedgeProcessor.class, "createEquityIndexHedgeExtract", new Class[] { String.class, String.class, String.class }, new Object[] { companyStructure, runDate, createSubBucketInd }, Batch.BATCH_JOB_CREATE_EQUITY_INDEX_HEDGE_EXTRACTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createEquityIndexHedgeExtract(String,String,String,String)
     * @param startDate
     * @param endDate
     * @param companyStructure
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createPremiumReservesExtract(String startDate, String endDate, String companyStructure, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(GAAPPremiumExtractProcessor.class, "createPremiumReservesExtract", new Class[] { String.class, String.class, String.class }, new Object[] { startDate, endDate, companyStructure }, Batch.BATCH_JOB_CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createTransactionActivityFile(String,String,String,String)
     * @param cycleDate
     * @param username
     * @param password
     * @param callMethod
     */
    public String createTransactionActivityFile(String cycleDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ActivityFileInterfaceProcessor.class, "createTransactionActivityFile", new Class[] { String.class }, new Object[] { cycleDate }, Batch.BATCH_JOB_CREATE_TRANSACTION_ACTIVITY_FILE, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#importCashClearanceValues(String,String,String,String)
     * @param importDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String importCashClearanceValues(String importDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(CashClearanceImportProcessor.class, "importCashClearanceValues", new Class[] { String.class }, new Object[] { importDate }, Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#updateAgentCommissions(String,String,String,String)
     * @param companyName
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String updateAgentCommissions(String companyName, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(CommissionController.class, "updateAgentCommissions", new Class[] { String.class }, new Object[] { companyName }, Batch.BATCH_JOB_UPDATE_AGENT_COMMISSIONS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#setupAgentCommissionChecksCK(String,String,String,String,String)
     * @param paymentMode
     * @param forceOutMinBal (indicator yes/no)
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     */
    public String setupAgentCommissionChecksCK(String paymentModeCT, String forceOutMinBal, String operator, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(CheckController.class, "setupAgentCommissionChecks", new Class[] { String.class, String.class, String.class, String.class }, new Object[] { Preference.DISBURSEMENT_SOURCE_PAPER, paymentModeCT, forceOutMinBal, operator }, Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_CK, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#setupAgentCommissionChecksCK(String,String,String,String,String)
     * @param paymentMode
     * @param forceOutMinBal (indicator yes/no)
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     */
    public String setupAgentCommissionChecksEFT(String paymentModeCT, String forceOutMinBal, String operator, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(CheckController.class, "setupAgentCommissionChecks", new Class[] { String.class, String.class, String.class, String.class }, new Object[] { "EFT", paymentModeCT, forceOutMinBal, operator }, Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_EFT, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#processCommissionChecks(String,String,String,String)
     * @param cycleDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String processCommissionChecks(String cycleDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(GroupTrx.class, "processCommissionChecks", new Class[] { String.class }, new Object[] { cycleDate }, Batch.BATCH_JOB_PROCESS_COMMISSION_CHECKS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#generateCommissionStatements(String,String,String,String,String)
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
                                               String callMethod)
    {
        String batchStatus = dispatch(CommissionStatement.class, "generateCommissionStatements", new Class[] { String.class, String.class, String.class, String.class }, new Object[] { contractCodeCT, paymentModeCT, processDate, outputFileType }, Batch.BATCH_JOB_GENERATE_COMMISSION_STATEMENTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createValuationExtracts(String,String,String,String,String)
     * @param companyName
     * @param valuationDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createValuationExtracts(String companyName, String valuationDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReportsProcessor.class, "createValuationExtracts", new Class[] { String.class, String.class }, new Object[] { companyName, valuationDate }, Batch.BATCH_JOB_CREATE_VALUATION_EXTRACTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createTaxReservesExtract(String,String,String,String,String,String,String,String,String)
     * @param startDate
     * @param endDate
     * @param companyStructure
     * @param taxReportType
     * @param taxYear
     * @param fileType
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String createTaxReservesExtract(String startDate, String endDate, String companyStructure, String taxReportType, String taxYear, String fileType, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(TaxExtractProcessor.class, "createTaxReservesExtract", new Class[] { String.class, String.class, String.class, String.class, String.class, String.class }, new Object[] { startDate, endDate, companyStructure, taxReportType, taxYear, fileType }, Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#createAccountingExtract_FLAT(String,String,String,String)
     * @param accountingPeriod
     * @param username
     * @param password
     * @param callMethod
     */
    public String createAccountingExtract_FLAT(String accountingPeriod, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReportsProcessor.class, "createAccountingExtract_FLAT", new Class[] { String.class }, new Object[] { accountingPeriod }, Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_FLAT, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#closeAccounting(String,String,String,String,String)
     * @param marketingPackageName
     * @param accountingPeriod
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String closeAccounting(String marketingPackageName, String accountingPeriod, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReportsProcessor.class, "closeAccounting", new Class[] { String.class, String.class }, new Object[] { marketingPackageName, accountingPeriod }, Batch.BATCH_JOB_CLOSE_ACCOUNTING, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#ofacCheckOnAllClients(String,String,String)
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String ofacCheckOnAllClients(String username, String password, String callMethod)
    {
        String batchStatus = dispatch(EditOFACCheck.class, "ofacCheckOnAllClients", new Class[] {  }, new Object[] {  }, Batch.BATCH_JOB_CHECK_OFAC_FOR_ALL_CLIENTS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#updateReinsuranceBalances(String,String,String,String,String)
     * @param companyName
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     */
    public String updateReinsuranceBalances(String companyName, String operator, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReinsuranceProcess.class, "updateReinsuranceBalances", new Class[] {String.class, String.class  }, new Object[] { companyName, operator  }, Batch.BATCH_JOB_UPDATE_REINSURANCE_BALANCES, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#setupReinsuranceChecks(String,String,String,String,String)
     * @param companyName
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     */
    public String setupReinsuranceChecks(String companyName, String operator, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReinsuranceProcess.class, "setupReinsuranceChecks", new Class[] {String.class, String.class  }, new Object[] { companyName, operator  }, Batch.BATCH_JOB_SETUP_REINSURANCE_CHECKS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#generateRmdNotifications(String,String,String,String,String,String)
     * @param companyStructure
     * @param startRmdAnnualDate
     * @param endRmdAnnualDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String generateRmdNotifications(String companyStructure, String startRmdAnnualDate, String endRmdAnnualDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(RMDNotificationProcessor.class, "generateRmdNotifications", new Class[] {String.class, String.class, String.class  }, new Object[] { companyStructure, startRmdAnnualDate, endRmdAnnualDate}, Batch.BATCH_JOB_GENERATE_RMD_NOTIFICATIONS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#processCoiReplenishment(String,String,String,String,String)
     * @param companyStructure
     * @param runDate
     * @param contractNumber
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String processCoiReplenishment(String companyStructure, String runDate, String contractNumber, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(COIReplenishmentProcessor.class, "processCoiReplenishment", new Class[] {String.class, String.class, String.class}, new Object[] {companyStructure, runDate, contractNumber}, Batch.BATCH_JOB_PROCESS_COI_REPLENISHMENT, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#stageTables(String,String,String,String)
     * @param processDate
     * @param username
     * @param password
     * @param callMethod
     */
    public String stageTables(String processDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(Staging.class, "stageTables", new Class[] {String.class}, new Object[] { processDate}, Batch.BATCH_JOB_STAGE_TABLES, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#updateAgentBonuses(String,String,String)
     * @param username
     * @param password
     * @param callMethod
     */
    public String updateAgentBonuses(String username, String password, String callMethod)
    {
        String batchStatus = dispatch(BonusProgram.class, "updateAgentBonuses", new Class[] {}, new Object[] {}, Batch.BATCH_JOB_UPDATE_AGENT_BONUSES, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#processAgentBonusChecks(String,String,String,String,String,String)
     * @param processDate
     * @param frequencyCT
     * @param operator
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String processAgentBonusChecks(String processDate, String frequencyCT, String operator, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ParticipatingAgent.class, "processAgentBonusChecks", new Class[] {String.class, String.class, String.class}, new Object[] { processDate, frequencyCT, operator}, Batch.BATCH_JOB_PROCESS_AGENT_BONUS_CHECKS, callMethod);

        return batchStatus;
    }

    /**
     * @see Batch#generateBonusCommissionStatements(String,String,String,String,String)
     * @param contractCodeCT
     * @param processDate
     * @param username
     * @param password
     * @param callMethod
     * @return
     */
    public String generateBonusCommissionStatements(String contractCodeCT, String processDate, String mode, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ParticipatingAgent.class, "generateBonusCommissionStatements", new Class[] {String.class, String.class, String.class}, new Object[] { contractCodeCT, processDate, mode}, Batch.BATCH_JOB_GENERATE_BONUS_COMMISSION_STATEMENTS, callMethod);

        return batchStatus;
    }

    public String processReinsuranceChecks(String cycleDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(GroupTrx.class, "executeReinsuranceCheckProcessing", new Class[] { String.class }, new Object[] { cycleDate }, Batch.BATCH_JOB_PROCESS_REINSURANCE_CHECKS, callMethod);

        return batchStatus;
    }

    public String runAlphaExtract(String paramDate,  String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReportsProcessor.class, "runAlphaExtract", new Class[] {EDITDate.class}, new Object[] {new EDITDate(paramDate)}, Batch.BATCH_JOB_RUN_ALPHA_EXTRACT, callMethod);

        return batchStatus;
    }

    public String runPendingRequirementsExtract(String extractDate,  String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReportsProcessor.class, "runPendingRequirementsExtract", new Class[] {EDITDate.class}, new Object[] {new EDITDate(extractDate)}, Batch.BATCH_JOB_RUN_PENDING_REQUIREMENTS_EXTRACT, callMethod);

        return batchStatus;
    }

    /**
     *
     * @param selectedMarketingPackage
     * @param selectedFunds
     * @param selectedDateType
     * @param startDate
     * @param endDate
     */
    public String runFundActivityExport(String selectedMarketingPackage, String selectedFunds, String selectedDateType, String startDate, String endDate, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ActivityFileExportProcessor.class, "exportFundActivity", new Class[] {String.class, String.class, String.class, String.class, String.class}, new Object[]{selectedMarketingPackage, selectedFunds, selectedDateType, startDate, endDate}, Batch.BATCH_JOB_RUN_FUN_ACTIVITY_REPORT, callMethod);

        return batchStatus;        
    }

	public String createPayoutTrxExtract(EDITDate fromDate, EDITDate toDate, String dateOption, String callMethod)
    {
//        String batchStatus = dispatch(BankProcessor.class, "createBankExtracts", new Class[] { String.class, String.class, String.class }, new Object[] { companyName, contractId, outputFileType }, Batch.BATCH_JOB_CREATE_BANK_EXTRACTS, callMethod);
        String batchStatus = dispatch(ReportsProcessor.class, "createPayoutTrxExtract", new Class[] { EDITDate.class, EDITDate.class, String.class }, new Object[] { fromDate, toDate, dateOption }, Batch.BATCH_JOB_CREATE_PAYOUT_TRX_EXTRACT, callMethod);

        return batchStatus;

    }

    /**
     * @see Batch#importCashBatch(String, String)
     * @param importDate
     * @param operator
     * @return
     */
    public String importCashBatch(String importDate, String operator, String filename,  String username, String password, String callMethod)
    {
        String batchStatus = dispatch(CashBatchProcessor.class, "importCashBatch", new Class[] {String.class, String.class, String.class}, new Object[]{importDate, operator, filename}, Batch.BATCH_JOB_IMPORT_CASH_BATCH, callMethod);

        return batchStatus;
    }


    /**************** Helper Methods ******************/
    /**
     * Determines if the callMethod is sychronous or asynchronous.
     * @param callMethod
     * @return true if the callMethod is asynchronous
     */
    private boolean isAsynchronous(String callMethod)
    {
        boolean isAsynchronous = false;

        if (callMethod.equals(Batch.ASYNCHRONOUS))
        {
            isAsynchronous = true;
        }

        return isAsynchronous;
    }

    /**
     * Uses reflection to execute the specified method and params. If the callMethod is asynchronous, the method
     * is invoked in a new thread.
     * @param targetClass
     @param targetMethod
     @param paramTypes
     @param paramValues
     @param batchJob
     @param callMethod
     */
    private String dispatch(final Class targetClass, final String targetMethod, final Class[] paramTypes, final Object[] paramValues, String batchJob, String callMethod)
    {
        String batchStatus = null;

        try
        {
            final Method method = targetClass.getMethod(targetMethod, paramTypes);

            final Object targetObj = targetClass.newInstance();
            
            EditServiceLocator.getSingleton().getBatchAgent().clearBatchStat(batchJob);

            if (isAsynchronous(callMethod))
            {
                new Thread()
                    {
                        public void run()
                        {
                            try
                            {
                                method.invoke(targetObj, paramValues);
                            }
                            catch (Exception e)
                            {
                                System.out.println(e);

                                e.printStackTrace();
                                
                                logError(e);
                            }
                        }
                    }.start();
            }
            else
            {
                method.invoke(targetObj, paramValues);

                batchStatus = EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(batchJob).getBatchStatus();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
        }

        return batchStatus;
    }

    /**
     * Log any errors that weren't already caught as General Exceptions
     * @param e
     */
    private void logError(Exception e)
    {
        Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

        //  Log to database
        Log.logGeneralExceptionToDatabase(null, e);
    }

    /**
     *
     * @param extractDate
     */
    public String runPRDCompare(String extractDate,  String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(PRDCompareServiceImpl.class, "runPRDCompare", new Class[] { EDITDate.class }, new Object[] { new EDITDate(extractDate) }, Batch.BATCH_JOB_RUN_PRD_COMPARE, callMethod);

        return batchStatus;
    }

    public String runAnnualStatements(String extractDate, String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(AnnualStatementProcessor.class, "runAnnualStatements", 
        		new Class[] { EDITDate.class }, new Object[] {new EDITDate(extractDate) }, 
        		Batch.BATCH_JOB_ANNUAL_STATEMENTS, callMethod);

        return batchStatus;
    }

    public String runMonthlyValuation(String extractDate, String isAsync, String isYev, String mevContractNumber, String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(MonthlyValuationProcessor.class, "runMonthlyValuation", 
        		new Class[] { EDITDate.class, String.class, String.class, String.class }, 
        		new Object[] {new EDITDate(extractDate), isAsync, isYev, mevContractNumber },
        		Batch.BATCH_JOB_MONTHLY_VALUATION, callMethod);

        return batchStatus;
    }

    public String runPolicyPages(String extractDate, String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(PolicyPagesProcessor.class, "runPolicyPages", 
        		new Class[] { EDITDate.class }, new Object[] {new EDITDate(extractDate) }, 
        		Batch.BATCH_JOB_POLICY_PAGES, callMethod);

        return batchStatus;
    }


    /**
     *
     * @param extractDate
     */
    public String runPRDCompareExtract(String extractDate, String extractType,  String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(PRDCompareServiceImpl.class, "runPRDCompareExtract", new Class[] { EDITDate.class, String.class }, new Object[] { new EDITDate(extractDate), extractType }, Batch.BATCH_JOB_RUN_PRD_COMPARE, callMethod);

        return batchStatus;
    }

    /**
     *
     * @param extractDate
     */
    public String createPRDExtract(String extractDate,  String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(PayrollDeductionSchedule.class, "createPRDExtract", new Class[] { EDITDate.class }, new Object[] { new EDITDate(extractDate) }, Batch.BATCH_JOB_CREATE_PRD_EXTRACT, callMethod);

        return batchStatus;
    }

    /**
     *
     * @param extractDate
     */
    public String createListBillExtract(String extractDate,  String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(BillSchedule.class, "createListBillExtract", new Class[] { EDITDate.class }, new Object[] { new EDITDate(extractDate) }, Batch.BATCH_JOB_CREATE_BILL_EXTRACT, callMethod);

        return batchStatus;
    }

    public String createClientAccountingExtract(String extractDate, String companyName,  String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(AccountingStaging.class, "stageTables", new Class[] { String.class, EDITDate.class, String.class }, new Object[] { "ClientAccounting", new EDITDate(extractDate), companyName }, Batch.BATCH_JOB_CREATE_CLIENT_ACCOUNTING_EXTRACT, callMethod);

        return batchStatus;
    }

    public String runDataWarehouse(String extractDate, String companyName, String caseNumber, String groupNumber,  String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReportsProcessor.class, "runDataWarehouse", new Class[] {EDITDate.class, String.class, String.class, String.class}, new Object[] {new EDITDate(extractDate), companyName, caseNumber, groupNumber}, Batch.BATCH_JOB_RUN_DATAWAREHOUSE, callMethod);

        return batchStatus;
    }

    public String runWorksheet(String batchId, String username, String password, String callMethod)
    {
        String batchStatus = dispatch(ReportsProcessor.class, "runWorksheet", new Class[] {String.class}, new Object[] {batchId}, Batch.BATCH_JOB_RUN_WORKSHEET, callMethod);

        return batchStatus;
    }

    public String runCheckNumberImport(String paramDate, String companyName,  String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(CheckNumberImportProcessor.class, "runCheckNumberImport", new Class[] {EDITDate.class, String.class}, new Object[] {new EDITDate(paramDate), companyName}, Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT, callMethod);

        return batchStatus;
    }

    public String runManualAccountingImport(String paramDate,  String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(ManualAccountingImportProcessor.class, "runManualAccountingImport", new Class[] {EDITDate.class}, new Object[] {new EDITDate(paramDate)}, Batch.BATCH_JOB_RUN_MANUAL_ACCOUNTING_IMPORT, callMethod);

        return batchStatus;
    }

    public String createBankForNACHA(String companyName, String processDate, String isIndividual, String createCashBatchFile, String username, String password, String callMethod) throws EDITCaseException
    {
        String batchStatus = dispatch(BankProcessorForNACHA.class, "createBankForNACHA", new Class[] { String.class, String.class, String.class, String.class }, new Object[] { companyName, processDate, isIndividual, createCashBatchFile }, Batch.BATCH_JOB_RUN_BANK_FOR_NACHA, callMethod);

        return batchStatus;
    }

    /**
     * Processes the last line of the cash batch import file.  The last line contains summary information.
     * The total suspense amount in the summary should equal the total of all suspense amounts that were processed
     * in the preceding lines.  If they are not equal, a warning message is placed in the batch log.
     *
     * @param lastLine                      last line of the import file which contains the summary information to be read
     * @param cashBatchContract             CashBatchContract object which contains the total suspense amount processed
     * @param operator                      current operator (or user)
     * @param fileName                      filename of the import file.  Used as information for the warning message
     */
    private void processLastLineCashBatchImport(String lastLine, CashBatchContract cashBatchContract, String operator, String fileName)
    {
        final int SUSPENSE_AMOUNT_BEGIN = 22;      final int SUSPENSE_AMOUNT_LENGTH = 9;

        String totalSuspenseAmountString = lastLine.substring(SUSPENSE_AMOUNT_BEGIN, SUSPENSE_AMOUNT_BEGIN + SUSPENSE_AMOUNT_LENGTH).trim();

        EDITBigDecimal totalSuspenseAmount = new EDITBigDecimal(totalSuspenseAmountString, 2);
        totalSuspenseAmount = totalSuspenseAmount.divideEditBigDecimal(new EDITBigDecimal("100.00"));   // divide by 100 to get currency (ex. 3468 in file is 34.68)

        if ( ! cashBatchContract.getAmount().isEQ(totalSuspenseAmount))
        {
            //  The summary's total suspense amount does not equal the total of all suspense amounts processed
            //  Put a warning message in the batch log
            EDITMap columnInfo = new EDITMap();
            columnInfo.put("Operator", operator);
            columnInfo.put("ProcessDate", new EDITDate().getFormattedDate());

            String message = "Cash Batch Import: The total suspense amount in the summary is not equal to the total of all suspense amounts processed.  File: " + fileName;
            Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);
        }
    }
}
