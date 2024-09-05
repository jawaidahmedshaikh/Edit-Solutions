/*
 * User: gfrosti
 * Date: Aug 12, 2003
 * Time: 2:43:58 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.group.trx;

import agent.Agent;
import batch.business.Batch;
import contract.ContractRequirement;
import contract.Deposits;
import contract.Requirement;
import contract.Segment;
import edit.common.*;
import edit.common.exceptions.EDITEventException;
import edit.common.exceptions.EDITValidationException;
import edit.common.vo.*;
import edit.portal.exceptions.PortalEditingException;
import edit.services.EditServiceLocator;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.HibernateEntityDifferenceInterceptor;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import engine.component.CalculatorComponent;
import engine.sp.SPException;
import engine.sp.SPOutput;
import engine.*;
import event.*;
import event.dm.dao.DAOFactory;
import event.dm.dao.TransactionAccumsFastDAO;
import event.financial.client.trx.ClientTrx;
import event.financial.contract.trx.ContractEvent;
import event.financial.group.strategy.SaveGroup;
import fission.utility.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import logging.Log;
import logging.LogEvent;

import org.apache.logging.log4j.Logger;

import client.*;
import role.ClientRole;
import billing.*;
import group.*;


public class GroupTrx
{
    private static final String[] nonActiveStatuses = new String[] {"Frozen", "NotTaken", "Pending", "Surrendered", "Terminated"};

    private static final  String[] trxPendingStatuses = new String[] { "B", "C", "F", "P", "S" };

    private String debugFile;
    private boolean dubugBatch = Boolean.parseBoolean(System.getProperty("debugBatch"));
    
    public GroupTrx()
    {
    	if (dubugBatch)
    	{
    		debugFile = getExportFile();
    	}
    }

    private void executeRealTime(ContractSetupVO[] contractSetupVO) throws Exception
    {
        // Run RealTime if necessary.
        for (int i = 0; i < contractSetupVO.length; i++)
        {
            new ContractEvent().executeRealTime(contractSetupVO[i]);
        }
    }

    /**
     * The starting point for the 'Batch' process. This includes a series of preprocessing including; pre-processing for
     * BillTrx, pre-processing for LapsePendingTrx, and pre-processing for LapseTrx.
     * @param cycleDate
     * @param companyName
     * @param operator
     */
    public void executeBatch(String cycleDate, String companyName, String operator)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).tagBatchStart(Batch.BATCH_JOB_RUN_DAILY_BATCH, "Batch Transaction");

        try
        {
        	if (dubugBatch)
        	{
	        	System.out.println("**************");
	        	System.out.println("Process Pending Change Histories");
        	}
        	
        	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 1 of 7 - Processing Pending Change Histories");
            HibernateEntityDifferenceInterceptor.getInstance().processPendingChangeHistories(new EDITDate(cycleDate)); 
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error Pre-Processing Future-Dated Non Financial Changes", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

            // Don't rethrow.
        }

        if (dubugBatch)
    	{
	        System.out.println("**************");
	    	System.out.println("PreProcess Requirements");
    	}
        
    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 2 of 7 - PreProcessing Requirements");
        preprocessRequirements(cycleDate);

        try
        {
        	if (dubugBatch)
        	{
	        	System.out.println("**************");
	        	System.out.println("LP Processing");
        	}
        	
        	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 3 of 7 - PreProcessing Batch for Billing Trx - LPs");
            preprocessBatchForBillingTrx(companyName, cycleDate, "LP"); // pre-process for LapsePendingTrx Group
        }
        catch (EDITEventException e)
        {
          System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error Pre-Processing LapsePendingTrx", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

            // Don't rethrow.
        }

        try
        {
        	if (dubugBatch)
        	{
	        	System.out.println("**************");
	        	System.out.println("LA Processing");
        	}
        	
        	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 4 of 7 - PreProcessing Batch for Billing Trx - LAs");
            preprocessBatchForBillingTrx(companyName, cycleDate, "LA"); // pre-process for LapseTrx Group
        }
        catch (EDITEventException e)
        {
          System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error Pre-Processing LapseTrx", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

            // Don't rethrow.
        }

        try
        {
        	if (dubugBatch)
        	{
	        	System.out.println("**************");
	        	System.out.println("PreProcess Batch for Suspense Premiums");
        	}
        	
        	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 5 of 7 - PreProcessing Batch for Suspense Premiums");
            preprocessBatchForSuspensePremiums();
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error Pre-Processing Premiums for Suspenses", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

            // Don't rethrow.
        }

        try
        {
        	if (dubugBatch)
        	{
	        	System.out.println("**************");
	        	System.out.println("PreProcess Batch For Billing BC Creation");
        	}
        	
        	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 6 of 7 - PreProcessing Batch for Billing BC Creation");
            preprocessBatchForBillingBCCreation(cycleDate);
        }
        catch (EDITEventException e)
        {
          System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error Pre-Processing EDITTrxCorrespondence", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

            // Don't rethrow.
        }

        if (dubugBatch)
    	{
	        System.out.println("**************");
	    	System.out.println("Execute NonCheck Trx Batch");
    	}
        
        try {
	    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 7 of 7 - Executing NonCheck Trx Batch");
        	executeNonCheckTrxBatchThreaded(companyName, cycleDate, operator);
		} catch (Exception e) {
			e.printStackTrace();
		}

        if (dubugBatch)
    	{
	        System.out.println("**************");
	    	System.out.println("BATCH COMPLETE!");
    	}
        
    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Batch Processing Complete!");
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).tagBatchStop();
    }

    private void preprocessBatchForBillingBCCreation(String cycleDate)  throws EDITEventException
    {
        try
        {
            EDITDate effectiveDate = new EDITDate(cycleDate);
            
            // we're retrieving a superset of the EDITTrx records that we need                        
            String hql = " select distinct editTrx from EDITTrx editTrx" +
            		" join fetch editTrx.ClientSetup CSetup " +
            		" join fetch CSetup.ContractClient CClient " + 
            		" join fetch CClient.Segment Seg " +
            		" left join fetch Seg.Segment BSeg " +
            		" join fetch Seg.BillSchedule BSched " + 
            		" join Seg.ContractGroup CGroup " + 
            		" join CGroup.PayrollDeductionSchedules PSched " + 
            		" where editTrx.EffectiveDate > :cycleDate" +
            		" and editTrx.PendingStatus = 'P'" +
            		" and editTrx.PremiumDueCreatedIndicator = 'N'" +
            		" and (editTrx.TransactionTypeCT = 'FI'" +
            		" or (editTrx.TransactionTypeCT = 'MA' and Seg.SegmentNameCT <> 'UL')" +
            		" or editTrx.TransactionTypeCT = 'BC'" +
            		" or editTrx.TransactionTypeCT = 'BCDA') " + 
            		" and (editTrx.EffectiveDate <= dateadd(d, 60, PSched.CurrentDateThru) " +
    				"	or  (BSched.NextBillExtractDate <= :cycleDate and editTrx.EffectiveDate <= dateadd(M, 15, BSched.NextBillDueDate) and (BSched.BillingModeCT = 'Annual' or BSched.BillingModeCT = 'SemiAnn')) " +
					"	or  (BSched.NextBillExtractDate <= :cycleDate and editTrx.EffectiveDate <= dateadd(M, 6, BSched.NextBillDueDate) and (BSched.BillingModeCT <> 'Annual' and BSched.BillingModeCT <> 'SemiAnn'))) ";
    				
            
            Map<String, EDITDate> params = new HashMap<String, EDITDate>();
            params.put("cycleDate", effectiveDate);

            List<EDITTrx> results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);
            TreeMap<String, String> contractsProcessed = new TreeMap<String, String>();
            Iterator<EDITTrx> it1 = results.iterator();

            int numberOfResults = results.size();
            int index = 0;
            
            Long segmentPK = null;
           	EDITTrx editTrx = null;

            if (it1 != null)
            {
                boolean contractProcessed = false;
                boolean shouldProcessEDITTrx = false;

                while (it1.hasNext()) 
                {
                	try 
                	{
                		index++;
                		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 6 of 7 - PreProcessing Batch for Billing BC Creation Iteration 1 - " + index + " out of " + numberOfResults);

                		if (dubugBatch)
    					{
	                		System.out.println("****** EditTrx Iteration 1: "+ index + " OUT OF " + numberOfResults);
	                    }
	                    
                	editTrx = it1.next();
                    contractProcessed = false;
                    shouldProcessEDITTrx = false;

                    Segment segment = editTrx.getClientSetup().getContractSetup().getSegment();

                    if (segment.getSegmentFK() != null)
                    {
                        segment = segment.getSegment();
                    }

                    
                    BillSchedule currentBillSchedule = segment.getBillSchedule();

                    if (currentBillSchedule.getNextBillExtractDate().beforeOREqual(effectiveDate))
                    {
                        EDITDate trxDate = currentBillSchedule.calculatedEndOFMonthDate(currentBillSchedule);

                        // if a FACE INCREASE process EDITTrx out 60 plus days.
                        if (editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FACEINCREASE))
                        {
                            trxDate = trxDate.addDays(60);

                            if (editTrx.getEffectiveDate().beforeOREqual(trxDate))
                            {
                                shouldProcessEDITTrx = true;
	                                
	                                if (dubugBatch)
    								{
	                                	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(debugFile, true)))) {
	                                    	out.println("IT 1: " + editTrx.getEDITTrxPK());
	                                	}catch (IOException e) {
	                                		System.out.println("IOException: " + e.getMessage());
                            }
                        }
	                            }
	                        }
                        // if not a FACE INCREASE process EDITTrx if effective date before or equal to trxDate (last day of month)
                        else if (editTrx.getEffectiveDate().beforeOREqual(trxDate))
                        {
                            shouldProcessEDITTrx = true;
	                            
	                            if (dubugBatch)
    							{
	                            	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(debugFile, true)))) {
	                                	out.println("IT 1: " + editTrx.getEDITTrxPK());
	                            	}catch (IOException e) {
	                            		System.out.println("IOException: " + e.getMessage());
	                            	}
	                            }
                        }
                    }

                    if (shouldProcessEDITTrx)
                    {
                        Iterator it2 = contractsProcessed.values().iterator();

                        while (it2.hasNext())
                        {
                            if (((String) it2.next()).equalsIgnoreCase(segment.getContractNumber()))
                            {
                                contractProcessed = true;
                            }
                        }

                        if (!contractProcessed)
                        {
                            contractsProcessed.put(segment.getContractNumber(), segment.getContractNumber());

	                            if (dubugBatch)
    							{
	                            	//System.out.println(editTrx.getEDITTrxPK());
	
	                            	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(debugFile, true)))) {
	                                	out.println("IT 1 Built: " + editTrx.getEDITTrxPK());
	                            	}catch (IOException e) {
	                            		System.out.println("IOException: " + e.getMessage());
	                            	}
								}
								
                            String operator = segment.getOperator();
                            EDITTrx.createBillingChangeTrxGroupSetup(segment, operator, editTrx.getEDITTrxPK(), "Batch", effectiveDate);
                        }
                    }
                }
	                catch (Exception e)
	                {
	                	String message = "PreprocessBatchForBillingBCCreation Part 1 Error On EditTrx #" + editTrx.getEDITTrxPK() + ": " + e.getMessage();

	                	String contractNumber = "N/A";
	                    String contractStatus = "N/A";
	                    String operator = "Batch";
	                    String effDate = "N/A";
	                    String processDate = cycleDate;
	                    String transactionTypeCT = "PreprocessBatchForBillingBCCreation";

	                    EDITMap columnInfo = new EDITMap();
	                    columnInfo.put("ContractNumber", contractNumber);
	                    columnInfo.put("ContractStatus", contractStatus);
	                    columnInfo.put("EffectiveDate", effDate);
	                    columnInfo.put("Operator", operator);
	                    columnInfo.put("ProcessDate", processDate);
	                    columnInfo.put("TransactionTypeCT", transactionTypeCT);

	                    Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);
	                    
	                    System.out.println(message);
	                	
	                    e.printStackTrace();
	                }
                }
            }

            //prd lookup needed, BC trx created for qualifying transactions
//            EDITTrx[] editTrxs = new EDITTrx().findByDate_TrxType_PDCreatedInd_PendStatus(effectiveDate);

            index = 0;

            it1 = results.iterator();
            if (it1 != null)
            {
                boolean contractProcessed = false;
                boolean shouldProcessEDITTrx = false;
                while (it1.hasNext())
                {
                	try
                	{
                		index++;
                		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 6 of 7 - PreProcessing Batch for Billing BC Creation Iteration 2 - " + index + " out of " + numberOfResults);

                		if (dubugBatch)
    					{
	                		System.out.println("****** EditTrx Iteration 2: "+ index + " OUT OF " + numberOfResults);
	                    }
	                    
                	editTrx = it1.next();
                    contractProcessed = false;
                    shouldProcessEDITTrx = false;
                    Segment segment = (Segment) editTrx.getClientSetup().getContractSetup().getSegment();
                    if (segment.getSegmentFK() != null)
                    {
                        segment = segment.getSegment();
                    }

                    ContractGroup contractGroup = segment.getContractGroup();
                    Set<PayrollDeductionSchedule> payrollDeductionSchedules = contractGroup.getPayrollDeductionSchedules();
                    Iterator it = payrollDeductionSchedules.iterator();
                    while (it.hasNext())
                    {
                        PayrollDeductionSchedule pds = (PayrollDeductionSchedule) it.next();
                        if (editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FACEINCREASE))
                        {
                            EDITDate trxDate = pds.getCurrentDateThru().addDays(60);

                            if (editTrx.getEffectiveDate().beforeOREqual(trxDate))
                            {
                                shouldProcessEDITTrx = true;
	                                
	                                if (dubugBatch)
    								{
	                                	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(debugFile, true)))) {
	                                    	out.println("IT 2: " + editTrx.getEDITTrxPK());
	                                	}catch (IOException e) {
	                                		System.out.println("IOException: " + e.getMessage());
	                                	}
	                                }
                            }
                        }
                        else if (editTrx.getEffectiveDate().beforeOREqual(pds.getCurrentDateThru()))
                        {
                            shouldProcessEDITTrx = true;
	                            
	                            if (dubugBatch)
    							{
	                            	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(debugFile, true)))) {
	                              	  out.println("IT 2: " + editTrx.getEDITTrxPK());
	                            	}catch (IOException e) {
	                            		System.out.println("IOException: " + e.getMessage());
                        }
                    }
	                        }
	                    }

                    if (shouldProcessEDITTrx)
                    {
                        Iterator it2 = contractsProcessed.values().iterator();

                        while (it2.hasNext())
                        {
                            if (((String) it2.next()).equalsIgnoreCase(segment.getContractNumber()))
                            {
                                contractProcessed = true;
                            }
                        }

                        if (!contractProcessed)
                        {
                            contractsProcessed.put(segment.getContractNumber(), segment.getContractNumber());

                            segmentPK = new Segment().getSegmentPKWithPendingPRD(segment.getSegmentPK(), effectiveDate);

                            if (segmentPK != null)
                            {
	                            	if (dubugBatch)
    								{	
	                            		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(debugFile, true)))) {
	                                    	out.println("IT 2 Built: " + editTrx.getEDITTrxPK());
	                                	}catch (IOException e) {
	                                		System.out.println("IOException: " + e.getMessage());
	                                	}
	                                }
	                                
                                String operator = segment.getOperator();
                                EDITTrx.createBillingChangeTrxGroupSetup(segment, operator, editTrx.getEDITTrxPK(), "Batch", effectiveDate);
                            }
                        }
                    }
                }
	                catch (Exception e)
	                {
	                	String message = "PreprocessBatchForBillingBCCreation Part 2 Error On EditTrx #" + editTrx.getEDITTrxPK() + ": " + e.getMessage();

	                	String contractNumber = "N/A";
	                    String contractStatus = "N/A";
	                    String operator = "Batch";
	                    String effDate = "N/A";
	                    String processDate = cycleDate;
	                    String transactionTypeCT = "PreprocessBatchForBillingBCCreation";

	                    EDITMap columnInfo = new EDITMap();
	                    columnInfo.put("ContractNumber", contractNumber);
	                    columnInfo.put("ContractStatus", contractStatus);
	                    columnInfo.put("EffectiveDate", effDate);
	                    columnInfo.put("Operator", operator);
	                    columnInfo.put("ProcessDate", processDate);
	                    columnInfo.put("TransactionTypeCT", transactionTypeCT);

	                    Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);
	                    
	                    System.out.println(message);
	                	
	                    e.printStackTrace();
	                }
                }
            }
        }
        catch (Exception e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            System.out.println(e);

            e.printStackTrace();
        }
    }
    
    

    /**
     * Processes Check transactions from CK and EFT disbursement sources.
     * @param cycleDate
     * @throws Exception
     */
    public void processCommissionChecks(String cycleDate) throws Exception
    {
        executeCheckTrxBatch("CK", cycleDate);
    }

    /**
     * Processes Reinsurance Check Transactions 'RCK' for the cycle date.
     * @param cycleDate
     * @throws Exception
     */
    public void executeReinsuranceCheckProcessing(String cycleDate) throws Exception
    {
        executeCheckTrxBatch("RCK", cycleDate);
    }

    private void executeCheckTrxBatch(String transactionTypeCT, String cycleDate) throws Exception
    {
        String jobName = null;
        String jobLabel = null;

        if (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK))
        {
            jobName = Batch.BATCH_JOB_PROCESS_REINSURANCE_CHECKS;
            jobLabel = "Reinsurance Check";
        }
        else
        {
            jobName = Batch.BATCH_JOB_PROCESS_COMMISSION_CHECKS;
            jobLabel = "Commission Check";
        }

        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(jobName).tagBatchStart(jobName, jobLabel);

        try
        {
            EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findByTransactionTypeCT_AND_EffectiveDateLTE_AND_PendingStatus(transactionTypeCT, cycleDate, "P");

            if (editTrxVO != null)
            {
                for (int i = 0; i < editTrxVO.length; i++)
                {
                    ClientTrx clientTrx = null;

                    try
                    {
                        clientTrx = new ClientTrx(editTrxVO[i]);

                        clientTrx.setExecutionMode(ClientTrx.BATCH_MODE);

                        clientTrx.setCycleDate(cycleDate);

                        clientTrx.execute();

                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(jobName).updateSuccess();
                    }
                    catch (EDITEventException e)
                    {
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(jobName).updateFailure();

                        Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                        Log.logGeneralExceptionToDatabase(null, e);

                        logErrorToDatabase(e, clientTrx);

                        // Don't rethrow the error. We want to continue processing.
                    }
                }
            }
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(jobName).tagBatchStop();
        }
    }

    /**
     *
     * @param companyName
     * @param cycleDate
     * @param operator
     */
    private void executeNonCheckTrxBatch(String companyName, String cycleDate, String operator)
    {
    	List<Long> segmentPKs = getSegmentPKsForCompanyName(companyName, cycleDate);

        if (segmentPKs != null && segmentPKs.size() > 0)
        {
        	int segmentPKsCount = segmentPKs.size();
        	
            for (int i = 0; i < segmentPKsCount; i++)
            {
                try
                {
//                    if (segmentPKs[i] == 1193830205165L)
//                    {
//                        System.out.println("now");
//                    }
                	if (dubugBatch)
                	{
                		System.out.println("****** SEGMENTPK "+ (i+1) + " OUT OF " + segmentPKsCount);
                	}
                	
            		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 7 of 7 - Executing NonCheck Trx Batch - " + (i+1) + " out of " + segmentPKsCount);
                    new ContractEvent().executeBatch(segmentPKs.get(i), cycleDate, operator);
                }
                catch (Exception e) // This exception may be too "general", but we don't want batch to die.
                {
                    Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                    Log.logGeneralExceptionToDatabase(null, e);

                    // Don't rethrow.
                }
                finally
                {
                    SessionHelper.clearSessions();
                }
            }
        }
    }
    
    private void executeNonCheckTrxBatchThreaded(String companyName, final String cycleDate, final String operator) throws Exception
    {
    	List<Long> segmentPKs = getSegmentPKsForCompanyName(companyName, cycleDate);

        if (segmentPKs != null)
        {
        	final int segmentPKsCount = segmentPKs.size();
        	int nThreads = (Runtime.getRuntime().availableProcessors() > 1) ? (Runtime.getRuntime().availableProcessors() - 1) : 1;

        	// maxBatchThreads set in venus-version.properties file
    	    int maxThreads = Integer.parseInt(System.getProperty("maxBatchThreads"));
    	    if (nThreads > maxThreads) {
    	    	nThreads = maxThreads;
    	    }

    		ExecutorService executor = Executors.newFixedThreadPool(nThreads);

    		List<Future> futures = new ArrayList<Future>();
    		
    		try {
	            for (int i = 0; i < segmentPKsCount; i++) {
	                try {
	                	
	    				final long segmentPK = segmentPKs.get(i);
	    				final int index = i;
	                	
	                	Future<?> future = executor.submit(new Runnable() {
	    					public void run() {
	    	                    try {
									new ContractEvent().executeBatch(segmentPK, cycleDate, operator, index, segmentPKsCount);
								} catch (Exception e) {
									e.printStackTrace();
									
									Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
									
				                    Log.logGeneralExceptionToDatabase(null, e);
				
				                    // Don't rethrow.
								}
	    					} 
	    				});

	    				futures.add(future);
	                }
	                catch (Exception e) // This exception may be too "general", but we don't want batch to die.
	                {
	                    Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
	
	                    Log.logGeneralExceptionToDatabase(null, e);
	
	                    // Don't rethrow.
	                }
	                finally
	                {
	                    SessionHelper.clearSessions();
	                }
	            }
	            
	            executor.shutdown();
				
				boolean threadsComplete = false;
				while (!threadsComplete) {

					Iterator<Future> futuresIt = futures.iterator();
					int nullCount = 0;
					while (futuresIt.hasNext()) {
						if (futuresIt.next().get() == null) {
							nullCount++;
						}
					}
					if (nullCount == (futures.size())) {
						threadsComplete = true;
					}
				}
				
    		} catch(Exception e) {
    			throw e;
    			
	        } finally {
				
				if (executor != null) {
					executor.shutdownNow();
					executor = null;
				}
			}
        }
    }

    public boolean saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName, String optionCode, long productStructurePK) throws EDITEventException
    {
        boolean saveSuccessful = false;

        try
        {
            SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, processName, optionCode);

            saveGroup.build();

            saveGroup.save();

            saveSuccessful = true;

            checkForTrxGeneration(groupSetupVO, editTrxVO, processName, optionCode, productStructurePK);

            ContractSetupVO contractSetupVO = saveGroup.getContractSetupVO()[0];

            String complexChangeType = Util.initString(contractSetupVO.getComplexChangeTypeCT(), "");

            if (!complexChangeType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_BATCH))
            {
                executeRealTime(saveGroup.getContractSetupVO());
            }
        }
        catch (EDITEventException e)
        {
            //  Just throw it, want to keep the validationVO information
            throw e;
        }
        catch (Exception e)
        {
            throw new EDITEventException(e.getMessage());
        }

        return saveSuccessful;
    }

    /**
     * Only saves groupsetup and edittrx to the database. Does not execute the transaction.
     * @param groupSetupVO
     * @param editTrxVO
     * @param processName
     * @param optionCode
     * @param productStructurePK
     * @return
     * @throws EDITEventException
     */
    public ClientTrx[] saveOnlyGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName, String optionCode, long productStructurePK) throws EDITEventException
    {
        List clientTrx = new ArrayList();

        try
        {
            SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, processName, optionCode);

            saveGroup.build();

            clientTrx.addAll(Arrays.asList(saveGroup.save()));

            clientTrx.addAll(Arrays.asList(checkForTrxGeneration(groupSetupVO, editTrxVO, processName, optionCode, productStructurePK)));
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        
            throw new EDITEventException(e.getMessage());
        }

        return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);
    }

    public ClientTrx[] saveGroupSetupForIssue(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName, String optionCode, long productStructurePK, List suspenseVector, Set deposits) throws Exception
    {
        List clientTrx = new ArrayList();

        ClientTrx[] clientTrxArray = null;

        try
        {
            SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, processName, optionCode);

            saveGroup.build();

            clientTrxArray = saveGroup.save();

            clientTrx.addAll(Arrays.asList(clientTrxArray));

            for (int i = 0; i < clientTrxArray.length; i++)
            {
                long editTrxPK = clientTrxArray[i].getEDITTrxVO().getEDITTrxPK();

                for (int j = 0; j < suspenseVector.size(); j++)
                {
                    Suspense vectorSuspense = (Suspense) suspenseVector.get(j);

                    Suspense suspense = Suspense.findByPK(vectorSuspense.getSuspensePK());

                    suspense.setPendingSuspenseAmount(suspense.getSuspenseAmount());

                    try
                    {
                        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                        SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);
                        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

                        Iterator it = deposits.iterator();

                        while (it.hasNext())
                        {
                            Deposits deposit = (Deposits) it.next();

                            if (deposit.getSuspenseFK().equals(suspense.getSuspensePK()))
                            {
                                deposit.setEDITTrxFK(new Long(editTrxPK));
                                deposit.save();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
                    }
                }
            }

            clientTrx.addAll(Arrays.asList(checkForTrxGeneration(groupSetupVO, editTrxVO, processName, optionCode, productStructurePK)));

            return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);

//            executeRealTime(saveGroup.getContractSetupVO());
        }
        catch (EDITEventException e)
        {
            throw new EDITEventException(e.getMessage());
        }
    }

    public void saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName, String optionCode, long productStructurePK, int notificationDays, String notificationDaysType) throws Exception
    {
        SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, processName, optionCode);

        saveGroup.build();

        saveGroup.save(notificationDays, notificationDaysType);

        checkForTrxGeneration(groupSetupVO, editTrxVO, processName, optionCode, productStructurePK);

        executeRealTime(saveGroup.getContractSetupVO());
    }

    public boolean saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, SegmentVO segmentVO, String processName, String optionCode, long productStructurePK, String ignoreEditWarnings, DepositsVO[] depositsVOs) throws EDITEventException, PortalEditingException, EDITValidationException
    {
        SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, processName, optionCode);

        saveGroup.build();

        try
        {
            // Now re-compose the segment to pick up any investment overrides that might have been added.
            contract.business.Lookup contractLookup = new contract.component.LookupComponent();
            List voExclusionList = new ArrayList();
            voExclusionList.add(ClientRoleVO.class);
            voExclusionList.add(ContractClientAllocationOvrdVO.class);
            voExclusionList.add(NoteReminderVO.class);
            voExclusionList.add(ContractSetupVO.class);
            voExclusionList.add(SegmentBackupVO.class);
            voExclusionList.add(RealTimeActivityVO.class);
            voExclusionList.add(PlacedAgentVO.class);
            voExclusionList.add(CommissionProfileVO.class);
            voExclusionList.add(InvestmentAllocationOverrideVO.class);
            voExclusionList.add(BucketHistoryVO.class);
            segmentVO = contractLookup.getSegmentBySegmentPK(segmentVO.getSegmentPK(), true, voExclusionList)[0];
        }
        catch (Exception e)
        {
            throw new EDITEventException(e.getMessage());
        }

        ClientSetupVO clientSetupVO = saveGroup.getGroupSetupVO().getContractSetupVO(0).getClientSetupVO(0);

        long clientRoleFK = clientSetupVO.getClientRoleFK();

        ClientDetailVO clientDetailVO = getClientDetail(clientRoleFK, segmentVO.getContractClientVO());

        if (!ignoreEditWarnings.equalsIgnoreCase("true"))
        {
            TransactionVO transactionVO = new TransactionVO();
            transactionVO.setGroupSetupVO(groupSetupVO);
            transactionVO.setEDITTrxVO(editTrxVO);
            transactionVO.setSegmentVO(segmentVO);

            if (depositsVOs != null)
            {
                transactionVO.setDepositsVO(depositsVOs);
            }

            if (clientDetailVO != null)
            {
                transactionVO.setClientDetailVO(clientDetailVO);
            }

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") ||
                editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
            {
//                try
//                {
//                    int numberOfTFTrx = checkForTFTrxsInThisPolicyYear(segmentVO.getSegmentPK(),
//                                                                       segmentVO.getIssueDate(),
//                                                                       editTrxVO.getEffectiveDate());
//
//                    transactionVO.setNumberOfTransfers(numberOfTFTrx);
//                }
//                catch(Exception e)
//                {
//                    throw new EDITEventException(e);
//                }
                int numberOfTFTrx = 0;
                TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();
                try
                {
                    numberOfTFTrx = checkForTFTrxsInThisPolicyYear(segmentVO.getSegmentPK(), new EDITDate(segmentVO.getIssueDate()), new EDITDate(editTrxVO.getEffectiveDate()));

                    transactionVO.setNumberOfTransfers(numberOfTFTrx);
                }
                catch (Exception e)
                {
                  System.out.println(e);

                    e.printStackTrace();
                }
            }

            validateTransactions(transactionVO, editTrxVO.getEffectiveDate(), productStructurePK);
        }

        boolean saveSuccessful = false;

        try
        {
            saveGroup.save();

            long editTrxPK = saveGroup.getGroupSetupVO().getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0).getEDITTrxPK();

            if (depositsVOs != null)
            {
                for (int i = 0; i < depositsVOs.length; i++)
                {
                    // To prevent updating other deposits with AmountReceived = 0
                    if (new EDITBigDecimal(depositsVOs[i].getAmountReceived()).isGT("0"))
                    {
                        depositsVOs[i].setEDITTrxFK(editTrxPK);
                        new Deposits(depositsVOs[i]).save();
                    }
                }
            }

            saveSuccessful = true;

            checkForTrxGeneration(groupSetupVO, editTrxVO, processName, optionCode, productStructurePK);

            SessionHelper.clearSessions();
            executeRealTime(saveGroup.getContractSetupVO());
        }
        catch (Exception e)
        {
        	System.out.println(e.getStackTrace());
            if (e.getClass().isInstance(EDITEventException.class))
            {
                throw new EDITEventException(e.getMessage());
            }
            else if (e.getClass().isInstance(PortalEditingException.class))
            {
                throw new PortalEditingException(e.getMessage());
            }
        }
        finally
        {
            return saveSuccessful;
        }
    }

    private int checkForTFTrxsInThisPolicyYear(long segmentPK, EDITDate issueDate, EDITDate trxEffDate) throws Exception
    {
        EDITDate edTFEffDate = new EDITDate(trxEffDate);
        EDITDate edIssueDate = new EDITDate(trxEffDate.getYear(), issueDate.getMonth(), issueDate.getDay());

        EDITDate fromDate = null;
        EDITDate toDate = null;

        if (edIssueDate.after(edTFEffDate) || edIssueDate.equals(edTFEffDate))
        {
            toDate = edIssueDate;
            fromDate = edIssueDate.subtractYears(1);
        }
        else
        {
            fromDate = edIssueDate;
            toDate = edIssueDate.addYears(1);
        }

        event.business.Event eventComponent = new event.component.EventComponent();

        EDITTrxVO[] editTrxVOs = eventComponent.findEDITTrxBySegment_TrxType_EffectiveDate_AND_PendingStatus(segmentPK, "TF", fromDate.getFormattedDate(), toDate.getFormattedDate(), "H");

        int numberOfTFTrx = 0;

        if (editTrxVOs != null)
        {
            numberOfTFTrx = editTrxVOs.length;
        }

        EDITTrxVO[] stfEditTrxVOs = eventComponent.findEDITTrxBySegment_TrxType_EffectiveDate_AND_PendingStatus(segmentPK, "STF", fromDate.getFormattedDate(), toDate.getFormattedDate(), "H");

        if (stfEditTrxVOs != null)
        {
            numberOfTFTrx = numberOfTFTrx + stfEditTrxVOs.length;
        }

        return numberOfTFTrx;
    }

    public void saveCommissionGroupSetup(GroupSetupVO groupSetupVO)
    {
        SaveGroup saveGroup = new SaveGroup(groupSetupVO);

        saveGroup.save();
    }

    /**
     * Finds all ProductStructurePKs that can be associated with the supplied CompanyName. The CompanyName of 'All' is special
     * in that all ProductStructurePKs are retrieved across all CompanyNames.
     * @param companyName
     * @return
     */
    private long[] getProductStructurePKs(String companyName)
    {
        long[] productStructurePKs = null;

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (companyName.equalsIgnoreCase("all"))
        {
            productStructurePKs = engineLookup.getAllProductStructurePKs();
        }
        else
        {
            productStructurePKs = engineLookup.getProductStructurePKsByCompanyName(companyName);
        }

        return productStructurePKs;
    }

    /**
     * Finds the set of all candidate SegmentPKs that are associated with the specified CompanyName. The CompanyName of "All"
     * is special in that all SegmentPKs across all CompanyNames are found.
     * @param companyName
     * @return
     */
    private List<Long> getSegmentPKsForCompanyName(String companyName, String cycleDate)
    {
        long[] productStructurePKs = getProductStructurePKs(companyName);

        contract.dm.dao.FastDAO fastDAO = new contract.dm.dao.FastDAO();
        List<Long> segmentPKs = fastDAO.findAllSegmentPKsForBatchCycle(productStructurePKs, nonActiveStatuses, cycleDate, trxPendingStatuses);

        return segmentPKs;
    }

    /**
     * Finds the set of all candidate Segments that meet certain date requirements for billing for the given
     * CompanyName. The CompanyName of "All" is special in that all Segments across all CompanyNames are found that
     * match the supplied criteria.
     * @param companyName
     * @param cycleDate
     * @param transactionTypeCT
     * @return
     */
    private Segment[] getSegmentsForBillingTrx(String companyName, EDITDate cycleDate, String transactionTypeCT)
    {
        Segment[] segments = null;

        long[] productStructurePKs = getProductStructurePKs(companyName);

        if (transactionTypeCT.equals("LP"))
        {
            segments = Segment.findBy_ProductStructurePKs_LapsePendingDate_UsingCRUD(productStructurePKs, cycleDate);
        }
        else if (transactionTypeCT.equals("LA"))
        {
            segments = Segment.findBy_ProductStructurePKs_LapseDate_UsingCRUD(productStructurePKs, cycleDate);
        }

        return segments;
    }

    private void preprocessRequirements(String cycleDate)
    {
        ContractRequirement[] contractRequirements = ContractRequirement.findBy_Status(ContractRequirement.REQUIREMENTSTATUSCT_OUTSTANDING);
        EDITDate edCycleDate = new EDITDate(cycleDate);

        for (int i = 0; i < contractRequirements.length; i++)
        {
        	if (dubugBatch)
        	{
        		System.out.println("****** Processing Requirement " + (i+1) + " of " + contractRequirements.length);
        	}
        	
    		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage("Step 2 of 7 - PreProcessing Requirements - " + (i+1) + " out of " + contractRequirements.length);

            if (contractRequirements[i].getFollowupDate().beforeOREqual(edCycleDate))
            {
                Requirement[] requirement = Requirement.findBy_FilteredRequirementPK(contractRequirements[i].getFilteredRequirementFK());

                String finalStatus = requirement[0].getFinalStatusCT();

                if (finalStatus != null && finalStatus.equalsIgnoreCase(Requirement.FINAL_STATUS_INCOMPLETE))
                {
                    try
                    {
                        Segment segment = Segment.findByPK(contractRequirements[i].getSegmentFK());

                        if (segment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMITTED) ||
                            segment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND))
                        {
                        	SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                            segment.setWorksheetTypeCT(Segment.WORKSHEETTYPECT_FINAL);
                            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                        	
                            EDITTrx.createBillingChangeTrxGroupSetup(segment, "System", finalStatus, contractRequirements[i].getFollowupDate());
                        }
                    }
                    catch (EDITEventException e)
                    {
                        System.out.println(e);

                        e.printStackTrace();

                        LogEvent logEvent = new LogEvent("Error Pre-Processing Requirements Check", e);

                        Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                        logger.error(logEvent);

                        // Don't rethrow.
                    }
                }
            }
        }
    }

    /**
     * Dynamically builds a type of BillingTrx for every Payor who has a Life.LapsePendingDate <= CycleDate (
     * LapsePendingTrx) or Life.LapseDate <= CycleDate (LapseTrx) CycleDate
     *
     * @param companyName
     * @param cycleDate
     * @throws Exception
     */
    private void preprocessBatchForBillingTrx(String companyName, String cycleDate, String transactionTypeCT) throws EDITEventException
    {
        EDITDate edCycleDate = new EDITDate(cycleDate);

        Segment[] segments = getSegmentsForBillingTrx(companyName, edCycleDate, transactionTypeCT);

        if (segments != null)
        {
            ContractEvent contractEvent = new ContractEvent();

            //  If the segment already has an LA or LP transaction, remove it from the list
            segments = checkForPendingLA_And_LP(segments, transactionTypeCT);

            List segmentList = new ArrayList();

            for (int i = 0; i < segments.length; i++)
            {
            	if (dubugBatch)
            	{
            		System.out.println("Process LA/LP Segment " + (i+1) + " of " + segments.length);
            	}
            	
                if (!segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATH) &&
                    !segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SURRENDERED))
                {
                    segmentList.add(segments[i]);
                }
            }

            segments = (Segment[]) segmentList.toArray(new Segment[segmentList.size()]);

            //  The list of segments may be empty after checking for LA and LP transactions.  Check for an
            //  empty list before continuing.
            if (segments.length > 0)
            {
                contractEvent.createBillingTrxGroupSetup(segments, transactionTypeCT);

//                if (shouldSpawnTrx(transactionTypeCT))
//                {
//                    spawnTrxsAfterBillTrx(segments, cycleDate);
//                }
            }
        }
    }

    /**
     * Pre-process for Suspenses.  Create premiums for those Suspenses whose pendingAmount is equal to zero, suspenseAmount
     * is greater than zero and whose CashBatchContract has been released. Then check the contract Status, if terminated,
     * no premium will be created but a refund suspense will.
     * 
     * @throws EDITEventException
     */
    private void preprocessBatchForSuspensePremiums() throws Exception
    {
        //  Find all released CashBatchContracts
        List<CashBatchContract> cashBatchContracts = Collections.synchronizedList(CashBatchContract.findByReleasedIndicator(
        		CashBatchContract.RELEASE_INDICATOR_RELEASE));

        if (cashBatchContracts != null)
        {
        	final int totalContracts = cashBatchContracts.size();
        	
        	int nThreads = 1; // setting to 1 to avoid non-incrementing sequenceNumbers on PYs created on same contractNumbers
    		
        	// maxBatchThreads set in venus-version.properties file
    		/*int maxThreads = Integer.parseInt(System.getProperty("maxBatchThreads"));
    	    if (nThreads > maxThreads) {
    	    	nThreads = maxThreads;
    	    }*/
    	    
            for (int i = 0; i < cashBatchContracts.size(); i++)
            {
            	final int cbcIndex = i;
                final CashBatchContract cashBatchContract = cashBatchContracts.get(i);

                Suspense[] suspenses = Suspense.findApplyByCashBatchContract_PendingAmountEQZero_SuspenseAmountGTZero(cashBatchContract);

                if (suspenses != null)
                {
                	final int totalSuspense = suspenses.length;
                	
            		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
            		List<Future> futures = new ArrayList<Future>();
                	
            		try {
	                    for (int j = 0; j < totalSuspense; j++)
	                    {
        	                try {
        	                	
        	    		        final Suspense suspense = suspenses[j];
        	    				final int suspenseIndex = j;
        	                	
        	                	Future<?> future = executor.submit(new Runnable() {
        	    					public void run() {
        	    	                    try {
        									processSuspense(suspense, cashBatchContract, cbcIndex, suspenseIndex, totalContracts, totalSuspense);
        								} catch (Exception e) {
        									e.printStackTrace();
        									
        									Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
        									
        				                    Log.logGeneralExceptionToDatabase(null, e);
        				
        				                    // Don't rethrow.
        								}
        	    					} 
        	    				});

        	    				futures.add(future);
        	                	
        	                }
        	                catch (Exception e) // This exception may be too "general", but we don't want batch to die.
        	                {
        	                    Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
        	
        	                    Log.logGeneralExceptionToDatabase(null, e);
        	
        	                    // Don't rethrow.
        	                }
        	                finally
        	                {
        	                    SessionHelper.clearSessions();
        	                }
	            	       
	                    }
	                    
	                    executor.shutdown();
        				
        				boolean threadsComplete = false;
        				while (!threadsComplete) {

        					Iterator<Future> futuresIt = futures.iterator();
        					int nullCount = 0;
        					while (futuresIt.hasNext()) {
        						if (futuresIt.next().get() == null) {
        							nullCount++;
        						}
        					}
        					if (nullCount == (futures.size())) {
        						threadsComplete = true;
        					}
        				}
            		} catch (Exception e) {
            			throw e;
                    } finally {
        				
        				if (executor != null) {
        					executor.shutdownNow();
        					executor = null;
        				}
        			}
                }
            }
        }
        SessionHelper.clearSessions();
    }
    
    private void processSuspense(Suspense suspense, CashBatchContract cashBatchContract, int cbcIndex, int suspenseIndex, int totalContracts, int totalSuspense) throws Exception
    {
    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DAILY_BATCH).setBatchMessage(
    			"Step 5 of 7 (Threaded) - PreProcessing Cash Batch " + (cbcIndex+1) + " of " + totalContracts + " - Suspense " + (suspenseIndex+1) + " of " + totalSuspense);

    	if (dubugBatch)
    	{
    		System.out.println("****** PreProcessing Cash Batch " + (cbcIndex+1) + " of " + totalContracts + " - Suspense " + (suspenseIndex+1) + " of " + totalSuspense);
    	}
    	
        Segment segment = Segment.findByContractNumber(suspense.getUserDefNumber());

        if (segment != null)
        {
        	String status = segment.getSegmentStatusCT();
            String statusType = new Segment().determineSegmentStatusType(segment, suspense.getEffectiveDate());

            if (statusType != null)
            {
                if (statusType.equalsIgnoreCase(Segment.TERMINATED) || status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_FROZEN))
                {
                    if (suspense.getAccountingPendingInd().equalsIgnoreCase("N"))
                    {
                        //original suspense update
                        suspense.setRefundAmount(suspense.getSuspenseAmount());
                        suspense.setContractPlacedFrom(Suspense.CONTRACT_PLACED_FROM_REJECTEDBATCH);
                        suspense.setReasonCodeCT(Suspense.REASON_CODE_TERMINATED);
                        suspense.setMaintenanceInd(Suspense.MAINTENANCE_IND_R);
                    }
                    else
                    {
                        //new refund suspense
                        createRefundSuspense(suspense, segment);
                        suspense.setMaintenanceInd("D");
                    }

                    //initialize amount last since it is needed to create the refund
                    suspense.setSuspenseAmount(new EDITBigDecimal("0"));
                }
                else
                {
                    EDITTrx.createPremiumForSuspense(suspense, cashBatchContract.getCashBatchContractPK(),cashBatchContract.getCreationOperator(), segment);

                    suspense.setPendingSuspenseAmount(suspense.getSuspenseAmount());
                    if (statusType.equalsIgnoreCase(Segment.PENDING))
                    {
                        suspense.setReasonCodeCT(Suspense.REASON_CODE_PENDINGISSUE);
                    }
                }
            }
        }
        else
        {
            suspense.setReasonCodeCT(Suspense.REASON_CODE_NOCONTRACT);
        }
        //original suspense
        suspense.save();
    }
 
    private void createRefundSuspense(Suspense suspense, Segment segment)
    {
        Suspense refundSuspense = new Suspense();
        refundSuspense.setSuspensePK(new Long(0));
        refundSuspense.setSuspenseAmount(suspense.getSuspenseAmount());
        refundSuspense.setOriginalAmount(suspense.getSuspenseAmount());
        refundSuspense.setUserDefNumber(suspense.getUserDefNumber().toUpperCase());
        refundSuspense.setOriginalContractNumber(suspense.getUserDefNumber().toUpperCase());
        refundSuspense.setOperator(suspense.getOperator());
        refundSuspense.setDisbursementSourceCT(suspense.getDisbursementSourceCT());
        refundSuspense.setAddressTypeCT(suspense.getAddressTypeCT());

        refundSuspense.setSuspenseRefundDefaults();

        refundSuspense.setMaintDateTime(new EDITDateTime());
        refundSuspense.setSuspenseType(Suspense.SUSPENSETYPE_CONTRACT);
        refundSuspense.setContractPlacedFrom(Suspense.CONTRACT_PLACED_FROM_REJECTEDBATCH);
        refundSuspense.setRemovalReason(Suspense.REMOVAL_REASON_REJECTEDBATCH);
        refundSuspense.setReasonCodeCT(Suspense.REASON_CODE_TERMINATED);

        ClientDetail clientDetail = ClientDetail.findBy_Segment_RoleType(segment, "POR");
        if (clientDetail == null)
        {
            ClientDetail.findBy_Segment_RoleType(segment, "Payor");
        }


        if (clientDetail != null)
        {
            refundSuspense.setClientDetail(clientDetail);
            refundSuspense.setClientDetailFK(clientDetail.getClientDetailPK());
        }

        Company company = Company.findByProductStructurePK(segment.getProductStructureFK());
        refundSuspense.setCompanyFK(company.getCompanyPK());

        //crud save for now
        refundSuspense.save();
    }

    /**
     * If so configured, transactions will be spawned after the processing of BillTrx.
     * @param segments
     * @param cycleDate
     */
//    private void spawnTrxsAfterBillTrx(Segment[] segments, String cycleDate) throws EDITEventException
//    {
//        SpawnedTransaction[] spawnedTransactions = ServicesConfig.getSpawnedTransactions("BI");
//
//        for (int j = 0; j < segments.length; j++) // For each Segment...
//        {
//            for (int k = 0; k < spawnedTransactions.length; k++) // For each transaction configured to be spawned for this BillTrx...
//            {
//                String spawnedTrxTypeCT = spawnedTransactions[k].getTransactionTypeCT();
//
//                if (spawnedTrxTypeCT.equals("PY")) // Premium
//                {
//                    if (shouldSpawnPremiumTrxAfterBillTrx(segments[j], cycleDate))
//                    {
//                        ContractEvent contractEvent = new ContractEvent();
//
//                        contractEvent.createPremiumTrxGroupSetup(segments[j]);
//                    }
//                }
//            }
//        }
//    }

    /**
     * Check EDITTrx table for pending transactions - if the specified transactionTypeCT (which in this case will be
     * either LA or LP) is found, do NOT generate another one (remove the segment from the list).
     * @param segmentVOs
     * @param transactionTypeCT
     * @return
     * @throws Exception
     */
    private Segment[] checkForPendingLA_And_LP(Segment[] segments, String transactionTypeCT) throws EDITEventException
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List segmentList = new ArrayList();

        boolean transactionFound = false;

        try
        {
            List segmentPKList = new ArrayList();

            List activeSegments = new ArrayList();

            boolean segmentActive = true;

            for (int i = 0; i < segments.length; i++)
            {
                segmentActive = true;

                if (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LAPSE_PENDING) &&
                    (segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSEPENDING) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSE) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_NOT_TAKEN) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_TRANSITION) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATH) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATHPENDING) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_EXTENSION) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_BENEFIT) ||
                     segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PUTERM)))
                {
                    segmentActive = false;
                }
                if (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LAPSE) &&
                     (segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSE) ||
                      segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP) ||
                      segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_NOT_TAKEN) ||
                      segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATH) ||
                      segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATHPENDING) ||
                      segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_EXTENSION) ||
                      segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_BENEFIT) ||
                      segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PUTERM)))
                {
                    segmentActive = false;
                }

                if (segmentActive)
                {
                    activeSegments.add(segments[i]);
                }
            }

            segments = (Segment[]) activeSegments.toArray(new Segment[activeSegments.size()]);

            for (int i = 0; i < segments.length; i++)
            {
            	if (dubugBatch)
            	{
            		System.out.println("****PROCESSING SEGMENT " + (i+1) + " of " + segments.length);
            	}
            	
                transactionFound = false;

                segmentPKList.clear();
                segmentPKList.add(segments[i].getSegmentPK().longValue());

                EDITTrxVO[] editTrxVO = eventComponent.composeEDITTrxVOBySegmentPKs_AND_PendingStatus(segmentPKList, new String[] {"P"}, new ArrayList());
                if (editTrxVO != null)
                {
                    for (int j = 0; j < editTrxVO.length; j++)
                    {
                        if (editTrxVO[j].getTransactionTypeCT().equalsIgnoreCase(transactionTypeCT) ||
                            (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LAPSE_PENDING) &&
                             (segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSEPENDING) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSE) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_NOT_TAKEN) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_TRANSITION) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATH) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATHPENDING))) ||
                            (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LAPSE) &&
                             (segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSE) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP) ||
                              segments[i].getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_NOT_TAKEN))))
                        {
                            transactionFound = true;
                            j = editTrxVO.length;
                        }
                    }
                }

                if (!transactionFound)
                {
                    segmentList.add(segments[i]);
                }
            }
        }
        catch (Exception e)
        {
            throw new EDITEventException(e.getMessage());
        }

        return (Segment[]) segmentList.toArray(new Segment[segmentList.size()]);
    }

    /**
     * Returns true if it has been so configured that a BillTrx will spawn other transactions.
     * @param transactionTypeCT
     * @return
     */
//    private boolean shouldSpawnTrx(String transactionTypeCT)
//    {
//        Object spawnedTransactions = null;
//
//        spawnedTransactions = ServicesConfig.getSpawnedTransactions(transactionTypeCT);
//
//        return (spawnedTransactions != null);
//    }

    /**
     * A PremiumTrx should be created for any Segment that has BillLapse.ExtractDate <= CycleDate and BillLapse.MethodCT is EFT or Credit Card
     * @param segment               A Segment which is known to have at least 1 BillLapse whose ExtractDate <= CycleDate
     * @param cycleDate
     * @return
     */
//    private boolean shouldSpawnPremiumTrxAfterBillTrx(Segment segment, String cycleDate)
//    {
//        String[] methodCTs = { "CreditCard", "EFT" };
//
//        long segmentPK = segment.getSegmentPK().longValue();
//
//        boolean shouldSpawnTrx = new contract.dm.dao.FastDAO().findSegmentHasBillLapseBySegmentPK_AND_ExtractDateLTE_AND_MethodCT_IN(segmentPK, cycleDate, methodCTs);
//
//        return shouldSpawnTrx;
//    }

    private void validateTransactions(TransactionVO transactionVO, String effectiveDate, long productStructurePK) throws PortalEditingException, EDITValidationException
    {
        SPOutput spOutput = null;
        SPOutputVO spOuputVO= null;
        ValidationVO[] validationVOs = null;
        PortalEditingException editingException = null;

        SegmentVO baseSegmentVO = transactionVO.getSegmentVO();
        long segmentPK = baseSegmentVO.getSegmentPK();
        String processName = "TransactionSave";
        Segment segment = new Segment(baseSegmentVO);
        String eventType = segment.setEventTypeForValidationScript();

        try
        {
            spOutput = new CalculatorComponent().processScript("TransactionVO", transactionVO, processName, "*", eventType, effectiveDate, productStructurePK, false);
        }
        catch (SPException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }

        if (spOutput.hasValidationOutputs())
        {
            validationVOs = spOutput.getSPOutputVO().getValidationVO();

            editingException = new PortalEditingException();

            editingException.setValidationVOs(validationVOs);

            throw editingException;
        }
        else if (spOutput.hasValidationOutputs())
        {
            VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

            for (int i = 0; i < voObjects.length; i++)
            {
                VOObject voObject = voObjects[i];

                if (voObject instanceof InsertRequirementVO)
                {
                    InsertRequirementVO insertRequirementVO = (InsertRequirementVO) voObject;

                    String requirementId = insertRequirementVO.getRequirementId();

                    new contract.component.ContractComponent().insertContractRequirement(segmentPK, productStructurePK, requirementId);
                }
            }
        }

        EDITTrxVO editTrxVO = transactionVO.getEDITTrxVO();

        boolean moneyLocked = false;

        GroupSetupVO groupSetupVO = transactionVO.getGroupSetupVO();
        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

        InvestmentVO[] investmentVOs = baseSegmentVO.getInvestmentVO();

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("SW") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase("WI") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
        {
            EDITDate trxEffectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
            EDITDate lockupEndDate = null;

            for (int i = 0; i < contractSetupVO.length; i++)
            {
                InvestmentAllocationOverrideVO[] invAllocOvrdVOs = contractSetupVO[i].getInvestmentAllocationOverrideVO();
                if (invAllocOvrdVOs != null && invAllocOvrdVOs.length > 0)
                {
                    for (int j = 0; j < invAllocOvrdVOs.length; j++)
                    {
                        if (invAllocOvrdVOs[j].getToFromStatus().equalsIgnoreCase("F"))
                        {
                            for (int k = 0; k < investmentVOs.length; k++)
                            {
                                if (investmentVOs[k].getInvestmentPK() == invAllocOvrdVOs[j].getInvestmentFK())
                                {
                                    BucketVO[] bucketVOs = investmentVOs[k].getBucketVO();
                                    if (bucketVOs != null)
                                    {
                                        for (int l = 0; l < bucketVOs.length; l++)
                                        {
                                            if (bucketVOs[l].getLockupEndDate() != null)
                                            {
                                                lockupEndDate = new EDITDate(bucketVOs[l].getLockupEndDate());
                                                if (lockupEndDate.after(trxEffectiveDate) || lockupEndDate.equals(trxEffectiveDate))
                                                {
                                                    moneyLocked = true;
                                                    l = bucketVOs.length;
                                                    k = investmentVOs.length;
                                                    j = invAllocOvrdVOs.length;
                                                    i = contractSetupVO.length;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    for (int j = 0; j < investmentVOs.length; j++)
                    {
                        BucketVO[] bucketVOs = investmentVOs[j].getBucketVO();
                        if (bucketVOs != null)
                        {
                            for (int k = 0; k < bucketVOs.length; k++)
                            {
                                if (bucketVOs[k].getLockupEndDate() != null)
                                {
                                    lockupEndDate = new EDITDate(bucketVOs[k].getLockupEndDate());
                                    if (lockupEndDate.after(trxEffectiveDate) || lockupEndDate.equals(trxEffectiveDate))
                                    {
                                        moneyLocked = true;
                                        k = bucketVOs.length;
                                        j = investmentVOs.length;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (moneyLocked)
        {
            editingException = new PortalEditingException();
            editingException.setAction("MoneyLocked");
            throw editingException;
        }

        boolean bucketsExist = true;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ADJUST_UP) ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ADJUST_DOWN))
        {
            bucketsExist = checkForBucket(contractSetupVO, investmentVOs, editTrxVO.getEffectiveDate());
        }

        if (!bucketsExist)
        {
            editingException = new PortalEditingException();
            editingException.setAction("InvalidBuckets");
            throw editingException;
        }
    }

    /**
     * Method to check for existence of bucket where deposit date is less than or equal to the effective date of the
     * AU or AD transaction (an appropriate bucket must exist for each fund on the transaction)
     * @param contractSetupVO
     * @param investmentVOs
     * @param effectiveDate
     * @return
     */
    private boolean checkForBucket(ContractSetupVO[] contractSetupVO, InvestmentVO[] investmentVOs, String effectiveDate)
    {
        boolean bucketExists = false;
        boolean allBucketsExist = true;

        EDITDate edEffDate = new EDITDate(effectiveDate);

        for (int i = 0; i < contractSetupVO.length; i++)
        {
            InvestmentAllocationOverrideVO[] invAllocOvrdVOs = contractSetupVO[i].getInvestmentAllocationOverrideVO();
            for (int j = 0; j < invAllocOvrdVOs.length; j++)
            {
                bucketExists = false;

                for (int k = 0; k < investmentVOs.length; k++)
                {
                    if (investmentVOs[k].getInvestmentPK() == invAllocOvrdVOs[j].getInvestmentFK())
                    {
                        BucketVO[] bucketVOs = investmentVOs[k].getBucketVO();
                        if (bucketVOs != null)
                        {
                            for (int l = 0; l < bucketVOs.length; l++)
                            {
                                EDITDate edDepositDate = new EDITDate(bucketVOs[l].getDepositDate());

                                if (edDepositDate.before(edEffDate) || edDepositDate.equals(edEffDate))
                                {
                                    bucketExists = true;
                                    break;
                                }
                            }
                        }

                        if (!bucketExists)
                        {
                            allBucketsExist = false;
                            break;
                        }
                    }
                }

                if (!allBucketsExist)
                {
                    break;
                }
            }
        }

        return allBucketsExist;
    }

    /**
     * Withdrawal transactions for Life contracts can generate a Face Decrease, if not suppressed.
     * @param groupSetupVO
     * @param editTrxVO
     * @param processName
     * @param optionCode
     * @param productStructurePK
     */
    private ClientTrx[] checkForTrxGeneration(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName, String optionCode, long productStructurePK)
    {
        List clienTrx = new ArrayList();

        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);

        String suppressDecreaseFaceInd = Util.initString(contractSetupVO.getSuppressDecreaseFaceInd(), "N");

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("WI") && suppressDecreaseFaceInd.equalsIgnoreCase("N"))
        {
            clienTrx.addAll(Arrays.asList(checkForFaceDecreaseGeneration(groupSetupVO, processName, optionCode, productStructurePK)));
        }

        return (ClientTrx[]) clienTrx.toArray(new ClientTrx[clienTrx.size()]);
    }

    /**
     * Get the Life table to determine if the death benefit option is "Level".  Only Life contracts
     * have a Life table.
     * If it is a Face Decrease trx is created and saved.
     * @param groupSetupVO
     * @param editTrxVO
     * @param processName
     * @param optionCode
     * @param productStructurePK
     */
    private ClientTrx[] checkForFaceDecreaseGeneration(GroupSetupVO groupSetupVO, String processName, String optionCode, long productStructurePK)
    {
        List clientTrx = new ArrayList();

        List voInclusionList = new ArrayList();

        voInclusionList.add(LifeVO.class);

        try
        {
            SegmentVO segmentVO = new contract.dm.composer.VOComposer().composeSegmentVO(groupSetupVO.getContractSetupVO(0).getSegmentFK(), voInclusionList);

            if (segmentVO.getSegmentNameCT().equalsIgnoreCase("Life"))
            {
                LifeVO lifeVO = segmentVO.getLifeVO(0);

                if (lifeVO.getDeathBenefitOptionCT().equalsIgnoreCase("Level"))
                {
                    clientTrx.addAll(Arrays.asList(generateFaceDecrease(groupSetupVO, processName, optionCode)));
                }
            }
        }
        catch (Exception e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            throw new RuntimeException(e);
        }

        return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);
    }

    /**
     * Using the WI as a basis, initialization is done for the Face Decrease trx, it is built and saved.
     * @param groupSetupVO
     * @param editTrxVO
     * @param processName
     * @param optionCode
     * @param productStructurePK
     * @throws EDITEventException
     */
    private ClientTrx[] generateFaceDecrease(GroupSetupVO groupSetupVO, String processName, String optionCode) throws EDITEventException
    {
        List clientTrx = new ArrayList();

        EDITTrxVO editTrxVO = groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0);

        long originatingTrxFK = editTrxVO.getEDITTrxPK();

        EDITTrxVO[] fdEditTrxVOs = DAOFactory.getEDITTrxDAO().findSpawnedTrx(originatingTrxFK);
        if (fdEditTrxVOs == null || fdEditTrxVOs.length == 0)
        {
            groupSetupVO.setGroupSetupPK(0);
            groupSetupVO.getContractSetupVO(0).setContractSetupPK(0);
            groupSetupVO.getContractSetupVO(0).setGroupSetupFK(0);
            groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).setClientSetupPK(0);
            groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).setContractSetupFK(0);

            editTrxVO.setEDITTrxPK(0);
            editTrxVO.setClientSetupFK(0);
            editTrxVO.setTransactionTypeCT("FD");
            editTrxVO.setOriginatingTrxFK(originatingTrxFK);
            groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).removeAllEDITTrxVO();
            groupSetupVO.getContractSetupVO(0).removeAllClientSetupVO();

            SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, processName, optionCode);

            saveGroup.build();
            clientTrx.addAll(Arrays.asList(saveGroup.save()));
        }

        return (ClientTrx[]) clientTrx.toArray(new ClientTrx[clientTrx.size()]);
    }

    /**
     * Builds and saves a Check Transaction (a GroupSetup) from the supplied information.
     * @param transactionTypeCT 'RCK' or 'CK'
     * @param clientPK
     * @param clientType
     * @param checkAmount
     * @param operator
     * @param bonusCommissionAmount
     * @param excessBonusCommissionAmount
     * @return the EDITTrx that was built and saved.
     */
    public EDITTrx buildCheckTransactionGroup(String transactionTypeCT, long clientPK, 
                                              int clientType, EDITBigDecimal checkAmount, 
                                              String operator, String effectiveDate, 
                                              EDITBigDecimal bonusCommissionAmount, 
                                              EDITBigDecimal excessBonusCommissionAmount,
                                              String forceOutMinBal)
    {
        GroupSetupVO groupSetupVO = new GroupSetupVO();

        //SRAMAM 09/2004 DOUBLE2DECIMAL
        //groupSetupVO.setGroupAmount(checkAmount);
        groupSetupVO.setGroupAmount(checkAmount.getBigDecimal());
        groupSetupVO.setMemoCode("");

        ContractSetupVO contractSetupVO = new ContractSetupVO();

        contractSetupVO.setPolicyAmount(checkAmount.getBigDecimal());

        ClientSetupVO clientSetupVO = new ClientSetupVO();

        setClientType(clientSetupVO, clientPK, clientType);

        EDITTrxVO editTrxVO = new EDITTrxVO();

        editTrxVO.setEffectiveDate(effectiveDate);
        editTrxVO.setStatus("N");
        editTrxVO.setPendingStatus("P");
        editTrxVO.setSequenceNumber(1);
        editTrxVO.setTrxAmount(checkAmount.getBigDecimal());
        editTrxVO.setBonusCommissionAmount(bonusCommissionAmount.getBigDecimal());
        editTrxVO.setExcessBonusCommissionAmount(excessBonusCommissionAmount.getBigDecimal());
        editTrxVO.setTransactionTypeCT(transactionTypeCT);
        editTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        editTrxVO.setOperator(operator);
        editTrxVO.setTaxYear(new EDITDate().getYear());
        editTrxVO.setTrxIsRescheduledInd("N");
        editTrxVO.setCommissionStatus("N");
        editTrxVO.setLookBackInd("N");
        editTrxVO.setNoAccountingInd("N");
        editTrxVO.setNoCommissionInd("N");
        editTrxVO.setZeroLoadInd("N");
        editTrxVO.setNoCorrespondenceInd("N");
        editTrxVO.setForceoutMinBalInd(forceOutMinBal);
        editTrxVO.setPremiumDueCreatedIndicator("N");

        groupSetupVO.addContractSetupVO(contractSetupVO);
        contractSetupVO.addClientSetupVO(clientSetupVO);
        clientSetupVO.addEDITTrxVO(editTrxVO);

        SaveGroup saveGroup = new SaveGroup(groupSetupVO);

        saveGroup.save();

        return new EDITTrx(editTrxVO);
    }

    /**
     * ClientSetups can represent 1 of several types of clients. This guarantees that the correct client
     * relationship is set.
     * @param clientSetupVO
     * @param clientPK
     * @param clientType
     */
    private void setClientType(ClientSetupVO clientSetupVO, long clientPK, int clientType)
    {
        switch (clientType)
        {
        case ClientSetup.TYPE_CONTRACT_CLIENT:
            clientSetupVO.setContractClientFK(clientPK);

            break;

        case ClientSetup.TYPE_CLIENT_ROLE:
            clientSetupVO.setClientRoleFK(clientPK);

            break;

        case ClientSetup.TYPE_TREATY:
            clientSetupVO.setTreatyFK(clientPK);
        }
    }

    private ClientDetailVO getClientDetail(long clientRoleKey, ContractClientVO[] contractClientVOs)
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();
        role.business.Lookup roleLookup = new role.component.LookupComponent();
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        List voInclusionList = new ArrayList();
        ClientRoleVO clientRoleVO = null;
        ContractClientVO contractClientVO = null;

        if (clientRoleKey == 0)
        {
            contractClientVO = contractLookup.getTheInsuredContractClient(contractClientVOs);

            if (contractClientVO != null)
            {
                clientRoleKey = contractClientVO.getClientRoleFK();
            }
        }

        ClientDetailVO clientDetailVO = null;

        try
        {
            clientRoleVO = roleLookup.composeClientRoleVO(clientRoleKey, voInclusionList);
        }
        catch (Exception e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }

        if (clientRoleVO != null)
        {
            voInclusionList.add(ClientAddressVO.class);
            voInclusionList.add(PreferenceVO.class);
            voInclusionList.add(TaxInformationVO.class);
            voInclusionList.add(TaxProfileVO.class);
            voInclusionList.add(ClientRoleVO.class);

            try
            {
                clientDetailVO = clientLookup.composeClientDetailVO(clientRoleVO.getClientDetailFK(), voInclusionList);
            }
            catch (Exception e)
            {
                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                Log.logGeneralExceptionToDatabase(null, e);
            }
        }

        return clientDetailVO;
    }

    /**
     * Log errors to the database
     * @param e                  exception thrown
     * @param clientTrx          clientTrx that helps to get the agentNumber for logging
     */
    private void logErrorToDatabase(Exception e, ClientTrx clientTrx)
    {
        //  Supposed to be able to get 0 or 1 unique Agents from clientTrx for CK transactions
        Agent agent = (Agent) clientTrx.getClientSetup().getClientRole().getAgent();
        ClientRole clientRole = (ClientRole) clientTrx.getClientSetup().getClientRole();

        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());

        if (agent != null)
        {
            columnInfo.put("AgentNumber", clientRole.getReferenceID());
        }
        else
        {
            columnInfo.put("AgentNumber", "N/A");
        }

        Log.logToDatabase(Log.RUN_AGENT_CK, e.toString(), columnInfo);
    }


    private String getExportFile()
    {
    	EDITDateTime runDate = new EDITDateTime();
        String dateString = runDate.getFormattedDateTime();
        String fileName = "BatchDebugOutput";

        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String exportFile = export1.getDirectory() + fileName + ".txt";

        return exportFile;
    }

}
