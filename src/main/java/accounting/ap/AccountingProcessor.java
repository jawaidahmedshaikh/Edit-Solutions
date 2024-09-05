/*
 * User: dlataill
 * Date: Jan 23, 2002
 * Time: 7:58:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package accounting.ap;

import accounting.dm.dao.DAOFactory;
import accounting.dm.dao.AccountingDetailDAO;
import accounting.utility.*;
import accounting.interfaces.AccountingInterfaceCmd;
import batch.business.*;
import client.*;
import contract.*;
import contract.dm.composer.*;
import contract.dm.composer.VOComposer;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.*;
import edit.services.config.*;
import edit.services.db.*;
import edit.services.db.hibernate.*;
import edit.services.logging.*;
import engine.*;
import engine.business.*;
import engine.component.*;
import event.*;
import event.business.*;
import event.component.*;
import event.dm.composer.*;
import event.dm.dao.*;
import fission.utility.*;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import logging.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;

import reinsurance.*;
import agent.*;
import role.ClientRole;
import staging.AccountingStaging;


public class AccountingProcessor implements Serializable {
    public static final String ACCOUNTING_EXCEPTION_FILE = "accountingExceptions.txt";
    private static final String[] ELEMENT_NAMES = {Element.GROSS_AMOUNT, Element.NET_AMOUNT, Element.STATE_WITHHOLDING, Element.FEDERAL_WITHHOLDING, Element.CITY_WITHHOLDING, Element.CHECK_AMOUNT, Element.FREE_AMOUNT, Element.SUSPENSE, Element.FUND_DOLLARS, Element.GAIN, Element.LOSS, Element.COUNTY_WITHHOLDING, Element.BONUS_AMOUNT,
            Element.BUCKET_LIAB_NEG, Element.BUCKET_LIAB_POS, Element.INTEREST_PROCEEDS, Element.LOAN_PRINCIPAL_DOLLARS, Element.LOAN_INTEREST_DOLLARS, Element.LOAN_INTEREST_LIABILITY, Element.UNEARNED_INTEREST_CREDIT, Element.OVER_SHORT_AMOUNT};
    private static final String[] ELEMENT_NAMES_UL = {Element.GROSS_AMOUNT, Element.NET_AMOUNT, Element.CHECK_AMOUNT, Element.INTEREST_PROCEEDS, Element.LOAN_PRINCIPAL_DOLLARS, 
    		Element.LOAN_INTEREST_DOLLARS};
    private static final String[] COMMISSION_ELEMENT_NAMES = {Element.CHECK_AMOUNT, Element.COMMISSION_AMOUNT, Element.ADA_AMOUNT, Element.EXPENSE_AMOUNT};
    private static final String[] BONUS_COMM_ELEMENT_NAMES = {Element.CHECK_AMOUNT, Element.COMMISSION_AMOUNT, Element.BONUS_COMMISSION_AMOUNT, Element.EXCESS_BONUS_COMMISSION_AMOUNT};
    private static final String[] REINSURANCE_ELEMENT_NAMES = {Element.MODAL_PREMIUM_AMOUNT};
    private static final String OUTPUT_FILE_TYPE_XML = "XML";
    private static final String OUTPUT_FILE_TYPE_FLAT = "Flat";
    private static final String OUTPUT_FILE_TYPE_STAGING = "Staging";
    private static final String GAIN_LOSS_FEE = "GainLoss";
    private static final String DFCASH_TRX = "DFCASH";
    private static final String DFACC_TRX = "DFACC";
    private static final String staging_event_type = "GeneralLedger";
    private static final Logger logger = LogManager.getLogger("AccountingProcessor.class");
    public static File exportFile = null;
    private static EDITDateTime stagingDate = null;
    private String accountingProcessDate = null;
    private boolean suppressExtract = true;
    private Map productStructureVOCache = new HashMap();
    private StringBuffer fileData = null;
    private AccountingInterfaceCmd accountingInterfaceCmd = null;
    private String outputFileType = null;
    
    ThreadPoolExecutor executorService;
    int runningThreadCount = 0;
    ConcurrentLinkedQueue<Long> sleepingThreads = new ConcurrentLinkedQueue<Long>();
    
    /**
     * use this for specialized lookup of charge code
     */
    private Calculator calculatorComponent = new CalculatorComponent();
    private engine.business.Lookup engineLookup = new engine.component.LookupComponent();

    public AccountingProcessor() throws Exception {
        super();

        init();
    }

    public AccountingProcessor(String accountingProcessDate, boolean suppressExtract, EDITDateTime stagingDate, String outputFileType, File exportFile) {
        super();
        this.accountingProcessDate = accountingProcessDate;
        this.suppressExtract = suppressExtract;
        AccountingProcessor.stagingDate = stagingDate;
        this.outputFileType = outputFileType;
        AccountingProcessor.exportFile = exportFile;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private final void init() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Start: " + sf.format(new Date(System.currentTimeMillis())));
        ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().findAllProductTypeStructureVOs(false, null);

        for (int i = 0; i < productStructureVOs.length; i++) {
            productStructureVOCache.put(productStructureVOs[i].getProductStructurePK(), productStructureVOs[i]);
        }
        System.out.println("End: " + sf.format(new Date(System.currentTimeMillis())));
    }

    private List<long[]> splitPKArray(int threadCount, long[] pkArray) {
        List<long[]> pkArrayList = new ArrayList<>();
        if (pkArray == null || pkArray.length == 0)
            return pkArrayList;
        if (pkArray.length == 1) {
            pkArrayList.add(pkArray);
            return pkArrayList;
        }

        int finalArraySize = 0;
        int chunkSize = (int) Math.ceil(pkArray.length / (double) threadCount);
        for (int x = 1; x <= threadCount; x++) {
            if (x == threadCount)
                pkArrayList.add(Arrays.copyOfRange(pkArray, chunkSize * (x - 1), pkArray.length));
            else if (chunkSize > 0 && x <= threadCount)
                pkArrayList.add(Arrays.copyOfRange(pkArray, chunkSize * (x - 1), chunkSize * x));

            finalArraySize += pkArrayList.get(x - 1).length;
        }

        return pkArrayList;
    }

    private List<CashBatchContract[]> splitCashBatchContractsArray(int threadCount, CashBatchContract[] pkArray) {
        List<CashBatchContract[]> pkArrayList = new ArrayList<>(threadCount);
        if (pkArray == null || pkArray.length == 0)
            return pkArrayList;
        if (pkArray.length == 1) {
            pkArrayList.add(pkArray);
            return pkArrayList;
        }

        int finalArraySize = 0;
        int chunkSize = (int) Math.ceil(pkArray.length / (double) threadCount);

        for (int x = 1; x <= threadCount; x++) {
            if (x == threadCount)
                pkArrayList.add(Arrays.copyOfRange(pkArray, chunkSize * (x - 1), pkArray.length));
            else if (chunkSize > 0 && x <= threadCount)
                pkArrayList.add(Arrays.copyOfRange(pkArray, chunkSize * (x - 1), chunkSize * x));

            finalArraySize += pkArrayList.get(x - 1).length;
        }
//        System.out.println("combined arraylist length: "+ finalArraySize);
        return pkArrayList;
    }

    /**
     * There are three phases to this Extract, in all cases the records used must have AccountPendingInd = "Y" and
     * satisfy the processDate requested. An xml output is produced reporting on the activity.
     * 1) EDITTrxHistory records
     * 2) CommissionHistory records
     * 3) Suspense Records
     *
     * @param accountingProcessDate
     * @throws Exception
     */
    public void createAccountingExtract_XML(final String accountingProcessDate, final String suppressExtract, final String outputFileType) throws Exception {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).tagBatchStart(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML, "Accounting Extract");
        List<Future> futureList = new ArrayList<>();
        
    	int threadCount = (Runtime.getRuntime().availableProcessors() > 1) ? (Runtime.getRuntime().availableProcessors() - 1) : 1;
        
        // maxAccountingThreads set in venus-version.properties file
    	String maxAccountingThreads = System.getProperty("maxAccountingThreads");
    	if (maxAccountingThreads != null) {
		    int maxThreads = Integer.parseInt(maxAccountingThreads);
		    if (threadCount > maxThreads) {
		    	threadCount = maxThreads;
		    }
    	}
    	
    	this.runningThreadCount = threadCount;
	    
        this.executorService = new ThreadPoolExecutor(threadCount, threadCount,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        
        boolean exportHeaderWritten = false;
        this.accountingProcessDate = accountingProcessDate;
        this.suppressExtract = Boolean.getBoolean(suppressExtract); // This will always be false (check the Javadoc on this method - GF.
        this.stagingDate = new EDITDateTime(accountingProcessDate + EDITDateTime.DATE_TIME_DELIMITER + new EDITDateTime().getFormattedTime());
        this.outputFileType = outputFileType;

        if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
            fileData = new StringBuffer();
            accountingInterfaceCmd = new AccountingInterfaceCmd();
        }
        
        try {
            final EDITDateTime edt = this.stagingDate;
            final File expFile = this.exportFile;
            
            // Run History Accounting First
            long[] editTrxHistoryPKs = getEDITTrxHistoryPKs(accountingProcessDate);
        	final boolean suppressExt = Boolean.getBoolean(suppressExtract);

            if (editTrxHistoryPKs != null) {
            	final int editTrxHistoryPKsCount = editTrxHistoryPKs.length;
                exportFile = getExportFile();
                if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                    insertStartAccounting(exportFile);
                    exportHeaderWritten = true;
                }

    	        for (int i = 0; i < editTrxHistoryPKsCount; i++) {

    				final long editTrxHistoryPK = editTrxHistoryPKs[i];
    				final int index = i;

                    futureList.add(executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            	runEDITTrxHistoryAccounting(editTrxHistoryPK, index, editTrxHistoryPKsCount);
                            }  catch (GenericJDBCException e) {
                        		pauseDamagedThread();
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error(e);
                            }
                        }
                    }));
                }
            }

//            if (gainLossByFund.size() > 0)
//            {
//                createDFACCForGainLoss();
//            }

            long[] commissionHistoryPKs = getCommissionHistoryPKs(accountingProcessDate);
            long[] bonusCommissionHistoryPKs = getBonusCommissionHistoryPKs(accountingProcessDate);

            if (commissionHistoryPKs != null || bonusCommissionHistoryPKs != null) {
                if (!exportHeaderWritten && outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                    exportFile = getExportFile();
                    insertStartAccounting(exportFile);
                    exportHeaderWritten = true;
                }

                if (commissionHistoryPKs != null && commissionHistoryPKs.length > 0) {
                    futureList.addAll(startCommissionHistoryThreads(commissionHistoryPKs, executorService));
                }
                if (bonusCommissionHistoryPKs != null && bonusCommissionHistoryPKs.length > 0) {
                    futureList.addAll(startBonusCommissionHistoryThreads(bonusCommissionHistoryPKs, executorService));
                }
            }

            long[] reinsuranceHistoryPKs = getReinsuranceHistoryPKs();

            if (reinsuranceHistoryPKs != null) {
                if (!exportHeaderWritten && outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                    exportFile = getExportFile();
                    insertStartAccounting(exportFile);
                    exportHeaderWritten = true;
                }
                
            	final int reinsuranceHistoryPKsCount = reinsuranceHistoryPKs.length;

                for (int i = 0; i < reinsuranceHistoryPKsCount; i++) {

    				final long reinsuranceHistoryPK = reinsuranceHistoryPKs[i];
    				final int index = i;

                    futureList.add(executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                runReinsuranceAccounting(reinsuranceHistoryPK, index, reinsuranceHistoryPKsCount);
                            } catch (GenericJDBCException e) {
                        		pauseDamagedThread();
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error(e);
                            }
                        }
                    }));
                }
            }

            long[] suspensePKs = getAccountingPendingSuspensePKs(accountingProcessDate);
            CashBatchContract[] cashBatchContracts = getAccountingPendingCashBatchContracts();

            if (suspensePKs != null || cashBatchContracts != null) {
                if (!exportHeaderWritten && outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                    exportFile = getExportFile();
                    insertStartAccounting(exportFile);
                    exportHeaderWritten = true;
                }

                if (cashBatchContracts != null && cashBatchContracts.length > 0) {
                	futureList.addAll(runCashBatchContractsThreads(cashBatchContracts, executorService));
                }
                if (suspensePKs != null && suspensePKs.length > 0) {
                	futureList.addAll(runSuspenseAccountingThreads(suspensePKs, executorService));
                }
            }

            /* Engine.Fee has no records... removing this step
             * futureList.add(
                    executorService.submit(new Runnable() {
                                               @Override
                                               public void run() {
                                                   try {
                                                       runFeeAccounting(accountingProcessDate);
                                                   } catch (Exception e) {
                                                       e.printStackTrace();
                                                   }
                                               }
                                           }
                    ));*/

//            if (exportHeaderWritten)
//            {
//                insertEndAccounting(exportFile);
//            }
            /*futureList.add(
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            updateManualJournalEntries(accountingProcessDate);
                        }
                    }));*/

            // Old versions of suspense are in memory and updates are being overlayed with this old data.
            boolean threadsComplete = false;
			while (!threadsComplete) {

				Iterator<Future> futuresIt = futureList.iterator();
				int nullCount = 0;
				while (futuresIt.hasNext()) {
					try {
						if (futuresIt.next().get() == null) {
							nullCount++;
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
				if (nullCount == (futureList.size())) {
					threadsComplete = true;
				}
			}
			
            executorService.shutdown();
            System.out.println("Awaiting Termination!");
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            System.out.println("All threads terminated!");
            
            event.business.Event eventComponent = new event.component.EventComponent();
            System.out.println("Cleaning Suspense");
            eventComponent.cleanSuspenseAfterAccounting();
            System.out.println("Suspense cleaned.");
            
            SessionHelper.clearSessions();

            System.out.println("Accounting batch complete. ");
        
        } catch (Exception e) {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateFailure();

            System.out.println(e);
            e.printStackTrace();
            throw e;
        } finally {
            if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                accountingInterfaceCmd.exportExtract(fileData, "SEGGL.prn");
            }
            if (exportHeaderWritten) {
                insertEndAccounting(exportFile);
            }

            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).tagBatchStop();
        }
    }
    
    private void pauseDamagedThread() {
    	try {    		
        	while(getTasksLeft() > 2) {
            	
            	if (sleepingThreads != null && !sleepingThreads.contains(Thread.currentThread().getId())) {
            		sleepingThreads.add(Thread.currentThread().getId());
            	}
            	
            	// System.out.println("********************** Thread ID: " + Thread.currentThread().getId());
            	System.out.println("Sleeping Threads: " + sleepingThreads.toString());
            	// System.out.println(sleepingThreads.size() + " vs " + runningThreadCount);
            	
            	if (runningThreadCount > 0 && sleepingThreads.size() == runningThreadCount) {
            		// job appears to be hung ... all threads are sleeping ... set message
                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
                			"Job Appears to Be Hung - Restart Tomcat & Accounting Job");
            	}
            	
            	// sleep for five minutes
            	Thread.currentThread().sleep(300000);
			}

        	// sleep for another minute to ensure other threads complete the work before returning
			Thread.currentThread().sleep(60000);

		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
    }
    
    private int getTasksLeft() {
    	
    	if (this.executorService != null && !this.executorService.isShutdown() && !this.executorService.isTerminated()) {
	    	System.out.println(
	                String.format("[sleepingThreadMonitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
	                    this.executorService.getPoolSize(),
	                    this.executorService.getCorePoolSize(),
	                    this.executorService.getActiveCount(),
	                    this.executorService.getCompletedTaskCount(),
	                    this.executorService.getTaskCount(),
	                    this.executorService.isShutdown(),
	                    this.executorService.isTerminated())); 
	    	
	    	return (int) (this.executorService.getTaskCount() - this.executorService.getCompletedTaskCount());
    
    	} else {
    		return 0;
    	}
    }

    private List<Future> runSuspenseAccountingThreads(long[] suspensePKs, ExecutorService executorService) {

        List<Future> futures = new ArrayList<>();
        final int suspensePKsCount = suspensePKs.length;

        System.out.println("Processing Suspense Accounting");

        for (int i = 0; i < suspensePKsCount; i++) {
        	
        	final long suspensePK = suspensePKs[i];
			final int index = i;
			
            futures.add(executorService.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        runSuspenseAccounting(suspensePK, index, suspensePKsCount);
                    } catch (GenericJDBCException e) {
                		pauseDamagedThread();
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e);
                    }
                }
            }));

        }
        return futures;
    }

    private List<Future> runCashBatchContractsThreads(CashBatchContract[] cashBatchContracts, ExecutorService executorService) {

        List<Future> futures = new ArrayList<>();
        final Event eventComponent = new EventComponent();
        final int totalContracts = cashBatchContracts.length;
        
        System.out.println("Processing Cash Batch Contract Accounting");

        for (int i = 0; i < totalContracts; i++) {
        	final CashBatchContract cashBatchContract = cashBatchContracts[i];
			final int index = i;
			
            futures.add(executorService.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        runCashBatchContracts(cashBatchContract, eventComponent, index, totalContracts);
                    } catch (GenericJDBCException e) {
                		pauseDamagedThread();
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e);
                    }
                }
            }));

        }
        return futures;
    }

    private List<Future> startCommissionHistoryThreads(long[] commissionHistoryPKs, ExecutorService executorService) {

        List<Future> futures = new ArrayList<>();
    	final int commissionHistoryPKsCount = commissionHistoryPKs.length;

        System.out.println("Processing Commission History Accounting");

        for (int i = 0; i < commissionHistoryPKsCount; i++) {

        	final long commissionHistoryPK = commissionHistoryPKs[i];
			final int index = i;

            futures.add(executorService.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        runCommissionHistoryAccounting(commissionHistoryPK, index, commissionHistoryPKsCount);
                    } catch (GenericJDBCException e) {
                		pauseDamagedThread();
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e);
                    }
                }
            }));

        }
        
        return futures;
    }

    private List<Future> startBonusCommissionHistoryThreads(long[] bonusCommissionHistoryPKs, ExecutorService executorService) {

        List<Future> futures = new ArrayList<>();
        final int bonusCommissionHistoryPKsCount = bonusCommissionHistoryPKs.length;
        
        System.out.println("Processing Bonus Commission History Accounting");

    	for (int i = 0; i < bonusCommissionHistoryPKsCount; i++) {

			final long bonusCommissionHistoryPK = bonusCommissionHistoryPKs[i];
			final int index = i;
			
            futures.add(executorService.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        runBonusCommissionAccounting(bonusCommissionHistoryPK, index, bonusCommissionHistoryPKsCount);
                    } catch (GenericJDBCException e) {
                		pauseDamagedThread();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));

        }
        return futures;
    }

    //TODO: This method seems to do quite a lot. Probably a good place to find inefficiency.
    //TODO: Switching to blockingdeque and running 2 threads one from the beginning, one from the end.


    private void updateAccountingDetailNotFundElement(List accountingDetail, ElementVO elementVO, SegmentVO segmentVO, EDITTrxHistoryVO editTrxHistoryVO, 
    		EDITTrxVO editTrxVO, ContractClientVO contractClientVO, int payoutCertainDuration, GroupSetupVO groupSetupVO, String memoCode, 
    		ElementStructureVO elementStructureVO, Map drivingCompanyInfo, Element currentElement, EDITBigDecimal elementAmount, 
    		EDITBigDecimal debits, EDITBigDecimal credits) throws Exception {
    	
        AccountEffectVO[] accountEffectVOs;
        if ((elementAmount.doubleValue() != 0) && !currentElement.isFundElement()) {
            accountEffectVOs = processNonFundLevelElement(currentElement.getName(), elementAmount, elementVO, elementStructureVO, 
            		editTrxVO.getEffectiveDate(), drivingCompanyInfo, editTrxVO.getStatus(), debits, credits);

            if (accountEffectVOs != null) {
                ChargeCodeVO chargeCodeVO = null;
                updateAccountingDetail(accountEffectVOs, currentElement, editTrxVO,
                        editTrxHistoryVO, segmentVO, memoCode, drivingCompanyInfo,
                        contractClientVO, accountingDetail, chargeCodeVO,
                        groupSetupVO.getDistributionCodeCT(), payoutCertainDuration);
            }
        }
    }

    //TODO: Called 65k times for 248 accounts processed
    private void calculateIfFundElement(List accountingDetail, ElementVO elementVO, SegmentVO segmentVO, EDITTrxHistoryVO editTrxHistoryVO, EDITTrxVO editTrxVO, ContractClientVO contractClientVO,
                                        int payoutCertainDuration, GroupSetupVO groupSetupVO, String memoCode, ElementStructureVO elementStructureVO, 
                                        Map drivingCompanyInfo, Element currentElement, EDITBigDecimal debits, EDITBigDecimal credits) throws Exception {
        AccountEffectVO[] accountEffectVOs;
        if (currentElement.isFundElement()) {
            if (currentElement.isGainLoss()) {
                InvestmentHistoryVO[] investmentHistoryVOs = editTrxHistoryVO.getInvestmentHistoryVO();
                for (int k = 0; k < investmentHistoryVOs.length; k++) {
                    accountEffectVOs = processFundLevelElement(currentElement, elementVO, elementStructureVO,
                            editTrxVO.getEffectiveDate(), drivingCompanyInfo, null,
                            investmentHistoryVOs[k], editTrxVO.getStatus(), editTrxHistoryVO, editTrxVO,
                            debits, credits);

                    if (accountEffectVOs != null) {
                        ChargeCodeVO chargeCodeVO = getHistoricalChargeCode(investmentHistoryVOs[k]);

                        updateAccountingDetail(accountEffectVOs, currentElement, editTrxVO,
                                editTrxHistoryVO, segmentVO, memoCode, drivingCompanyInfo,
                                contractClientVO, accountingDetail, chargeCodeVO,
                                groupSetupVO.getDistributionCodeCT(), payoutCertainDuration);
                    }
                }
            } else {
                BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVO.getBucketHistoryVO();

                for (int k = 0; k < bucketHistoryVOs.length; k++) {
                    accountEffectVOs = processFundLevelElement(currentElement, elementVO, elementStructureVO,
                            editTrxVO.getEffectiveDate(), drivingCompanyInfo, bucketHistoryVOs[k],
                            null, editTrxVO.getStatus(), editTrxHistoryVO, editTrxVO, debits, credits);

                    if (accountEffectVOs != null) {
                        ChargeCodeVO chargeCodeVO =
                                getHistoricalChargeCode(
                                        bucketHistoryVOs[k], editTrxHistoryVO);

                        updateAccountingDetail(accountEffectVOs, currentElement, editTrxVO,
                                editTrxHistoryVO, segmentVO, memoCode, drivingCompanyInfo,
                                contractClientVO, accountingDetail, chargeCodeVO,
                                groupSetupVO.getDistributionCodeCT(), payoutCertainDuration);
                    }
                }
            }
        }
    }

    private void updateAccountingDetail(AccountEffectVO[] accountEffectVOs,
                                        Element currentElement,
                                        EDITTrxVO editTrxVO, // editTrx trxAmount is 102.60
                                        EDITTrxHistoryVO editTrxHistoryVO,
                                        SegmentVO segmentVO,
                                        String memoCode,
                                        Map drivingCompanyInfo,
                                        ContractClientVO contractClientVO,
                                        List<AccountingDetailVO> accountingDetail,
                                        ChargeCodeVO chargeCodeVO,
                                        String distributionCodeOverride,
                                        int certainDuration) throws Exception {
        if (accountEffectVOs != null) // accountEffect is monthly payment amount - 111.15
        {
            String fundNumber = "";

            if (currentElement.isFundElement()) {
                fundNumber = currentElement.getFilteredFundVO().getFundNumber();
            }
            String chargeCode = null;
            if (chargeCodeVO != null) {
                chargeCode = chargeCodeVO.getChargeCode();
            }

            String transactionCode = editTrxVO.getTransactionTypeCT();
            String effectiveDate = editTrxVO.getEffectiveDate();

            //            String processDate = editTrxHistoryVO.getProcessDate();
            EDITDate processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();
            FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();
            ClientSetupVO clientSetupVO = null;
            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK)) {
                clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
            }

            for (int k = 0; k < accountEffectVOs.length; k++) {
                AccountingDetailVO accountingDetailVO =
                        buildAccountDetailVO(fundNumber,
                                segmentVO,
                                effectiveDate,
                                processDate.getFormattedDate(),
                                memoCode,
                                transactionCode,
                                editTrxVO.getStatus(),
                                drivingCompanyInfo,
                                accountEffectVOs[k],
                                contractClientVO,
                                null,
                                null,
                                editTrxVO.getAccountingPeriod(),
                                editTrxVO.getEDITTrxPK(),
                                chargeCode,
                                distributionCodeOverride,
                                financialHistoryVO[0].getDistributionCodeCT(),
                                clientSetupVO,
                                null,
                                certainDuration,
                                editTrxVO.getOperator(),
                                editTrxVO.getTrxAmount());

                accountingDetail.add(accountingDetailVO);

                if (!suppressExtract) {
                    if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                        exportAccounting(accountingDetailVO);
                    } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                        accountingInterfaceCmd.setAccountingInformation(segmentVO, accountEffectVOs[k],
                                (String) drivingCompanyInfo.get("companyName"),
                                (String) drivingCompanyInfo.get("businessContract"),
                                transactionCode, memoCode, effectiveDate,
                                processDate.getFormattedDate(), fileData);
                        accountingInterfaceCmd.exec();
                    }
                }
            }
        }
    }


    private void runCommissionHistoryAccounting(long commissionHistoryPK, int index, int totalPKs) throws Exception {
        
    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
    			"Step 2 of 6 - Executing CommissionHistory Accounting - CommissionHistoryPK " + (index+1) + " out of " + totalPKs);
    	
    	List accountingDetail = new ArrayList();

        ElementVO elementVO = null;
        SegmentVO segmentVO = null;
        CommissionHistoryVO commissionHistoryVO = null;
        String trxEffectiveDate = null;
        
        try {
	        
        	commissionHistoryVO = composeCommissionHistory(commissionHistoryPK);
	        EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) commissionHistoryVO.getParentVO(EDITTrxHistoryVO.class);
	        EDITTrxVO editTrxVO = getEDITTrxVO(editTrxHistoryVO);
	
	        trxEffectiveDate = editTrxVO.getEffectiveDate();
       
            EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
            EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

            // Build ElementVO
            String process = "Commission";
            String event = "*";
            String eventType = commissionHistoryVO.getCommissionTypeCT();

            elementVO = buildElementVO(process, event, eventType, null);

            FinancialHistoryVO[] financialHistoryVOs = editTrxHistoryVO.getFinancialHistoryVO();
            GroupSetupVO groupSetupVO = getGroupSetupVO(editTrxHistoryVO);

            //ClientDetailVO clientDetailVO = (ClientDetailVO) commissionHistoryVO.getParentVO(PlacedAgentVO.class).getParentVO(AgentContractVO.class).getParentVO(AgentVO.class).getParentVO(ClientRoleVO.class).getParentVO(ClientDetailVO.class);
            Long clientRoleFK = ((PlacedAgentVO) commissionHistoryVO.getParentVO(PlacedAgentVO.class)).getClientRoleFK();
            ClientRole clientRole = new ClientRole(clientRoleFK);
            ClientDetailVO clientDetailVO = (ClientDetailVO) clientRole.get_ClientDetail().getVO();


            FinancialHistoryVO financialHistoryVO = null;
            String distributionCode = null;

            if (financialHistoryVOs != null && financialHistoryVOs.length > 0) {
                financialHistoryVO = financialHistoryVOs[0];
                distributionCode = financialHistoryVO.getDistributionCodeCT();
            }

            segmentVO = getSegmentVO(editTrxHistoryVO);

            ProductStructureVO productStructureVO = null;
            int certainDuration = 0;
            String qualNonQual = "*";
            int payoutCertainDuration = 0;

            if (segmentVO != null) {
                productStructureVO = getProductStructureVO(segmentVO.getProductStructureFK());
                qualNonQual = Util.initString(segmentVO.getQualNonQualCT(), "*");

                if (segmentVO.getEffectiveDate() == null) {
                    certainDuration = 1;
                } else {
                    certainDuration = calculateDuration(segmentVO.getEffectiveDate(), editTrxVO.getEffectiveDate());
                }

                if (segmentVO.getPayoutVOCount() > 0) {
                    payoutCertainDuration = segmentVO.getPayoutVO(0).getCertainDuration();
                }
            }

            // Build ElementStructureVO
            String memoCode = Util.initString(groupSetupVO.getMemoCode(), "");

            ElementStructureVO elementStructureVO = buildElementStructureVO(memoCode, certainDuration, qualNonQual);
/*
                // Build ElementVO
                String process = "Commission";
                String event = "*";
                String eventType = commissionHistoryVO.getCommissionTypeCT();

                    elementVO = buildElementVO(process, event, eventType, null);
*/
            // Build drivingCompanyInfo
            Map<String, String> drivingCompanyInfo;

            if (productStructureVO != null) {
                drivingCompanyInfo = buildDrivingProductInfo(productStructureVO);
            } else {
                drivingCompanyInfo = new HashMap<>();

                drivingCompanyInfo.put("companyName", "Commission");
                drivingCompanyInfo.put("marketingPackage", "*");
                drivingCompanyInfo.put("groupProduct", "*");
                drivingCompanyInfo.put("area", "*");
                drivingCompanyInfo.put("businessContract", "*");
            }

            for (int j = 0; j < COMMISSION_ELEMENT_NAMES.length; j++) {
                elementVO.setElementName(COMMISSION_ELEMENT_NAMES[j]);

                Element currentElement = new Element(COMMISSION_ELEMENT_NAMES[j]);
                EDITBigDecimal elementAmount = new EDITBigDecimal(String.valueOf(currentElement.getCommissionAmount(commissionHistoryVO, editTrxVO, financialHistoryVO)));

                AccountEffectVO[] accountEffectVOs = null;

                elementStructureVO.setCertainPeriod(certainDuration);

                if (elementAmount.doubleValue() != 0) {
                    accountEffectVOs = processNonFundLevelElement(currentElement.getName(), elementAmount, elementVO, elementStructureVO, 
                    		editTrxVO.getEffectiveDate(), drivingCompanyInfo, editTrxVO.getStatus(), debits, credits);
                }

                if (accountEffectVOs != null) {
                    String fundNumber = "";
                    String effectiveDate = editTrxVO.getEffectiveDate();
                    EDITDate processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();

                    for (int k = 0; k < accountEffectVOs.length; k++) {
                        AccountingDetailVO accountingDetailVO =
                                buildCommissionAccountDetailVO(fundNumber,
                                        segmentVO,
                                        effectiveDate,
                                        processDate.getFormattedDate(),
                                        memoCode,
                                        "Commission",
                                        editTrxVO.getStatus(),
                                        drivingCompanyInfo,
                                        accountEffectVOs[k],
                                        clientDetailVO,
                                        editTrxVO.getAccountingPeriod(),
                                        editTrxVO.getEDITTrxPK(),
                                        groupSetupVO.getDistributionCodeCT(),
                                        distributionCode,
                                        payoutCertainDuration,
                                        commissionHistoryVO.getPlacedAgentFK(),
                                        editTrxVO.getOperator());

                        accountingDetail.add(accountingDetailVO);

                        if (!suppressExtract) {
                            if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                                exportAccounting(accountingDetailVO);
                            } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                                accountingInterfaceCmd.setAccountingInformation(segmentVO, accountEffectVOs[k],
                                        (String) drivingCompanyInfo.get("ProductName"),
                                        (String) drivingCompanyInfo.get("BusinessContract"),
                                        "Commission", memoCode, effectiveDate,
                                        processDate.getFormattedDate(), fileData);
                                accountingInterfaceCmd.exec();
                            }
                        }
                    }
                }

            }

            debitsEqCredits(accountingDetail, debits, credits);

            saveAccountingDetail(accountingDetail);

            commissionHistoryVO.setAccountingPendingStatus("N");

            // Update the EDITTrxHistory
            new event.component.EventComponent().createOrUpdateVO(commissionHistoryVO, false);

            if (accountingDetail != null && accountingDetail.size() > 0) {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateSuccess();
            }

        } catch (GenericJDBCException e) {
    		e.printStackTrace();
    		
    		// do not try to log error via SessionHelper while using boneCP... will throw another exception

    		if (accountingDetail != null) {
    			saveAccountingDetail(accountingDetail);
    		}

            if (commissionHistoryVO != null) {
	            commissionHistoryVO.setAccountingPendingStatus("F");
	            new event.component.EventComponent().createOrUpdateVO(commissionHistoryVO, false);
            }

            // throw the exception to pause the damaged thread
            throw e;
            
        } catch (Exception e) {
            e.printStackTrace();
            logError(e, segmentVO, elementVO, trxEffectiveDate, null, commissionHistoryVO.getPlacedAgentFK());

            saveAccountingDetail(accountingDetail);

            commissionHistoryVO.setAccountingPendingStatus("F");

            // Update CommissionHistory
            new event.component.EventComponent().createOrUpdateVO(commissionHistoryVO, false);
            
        } finally {
            try {
	            if (accountingDetail.size() > 0) {
	            	Segment segment = null;
	                if (segmentVO != null) {
	                	segment = new Segment(segmentVO);
	                }
	                
	                AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
	                accountingStaging.stageTables(accountingDetail, segment);
	            }
	            
            } catch(GenericJDBCException e) {
        		e.printStackTrace();

        		if (commissionHistoryVO != null) {
    	            commissionHistoryVO.setAccountingPendingStatus("F");
    	            new event.component.EventComponent().createOrUpdateVO(commissionHistoryVO, false);
                }
        		
                // throw the exception to pause the damaged thread
                throw e;
            }
        }
            
        SessionHelper.clearSessions();
    }

    private void runBonusCommissionAccounting(long bonusCommissionHistoryPK, int index, int totalPKs) throws Exception {
        
    	Segment segment = null;
        SegmentVO segmentVO = null;
        ElementVO elementVO = null;
        BonusCommissionHistory bonusCommissionHistory = null;
        long placedAgentPK = 0;
        String trxEffectiveDate = null;
        
        List<AccountingDetailVO> accountingDetail = new ArrayList<>();

    	try {
    		
	    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
	    			"Step 3 of 6 - Executing Bonus Commission History Accounting - BonusCommissionHistoryPK " + (index+1) + " out of " + totalPKs);

	        bonusCommissionHistory = BonusCommissionHistory.findByPK_UsingHibernate(bonusCommissionHistoryPK);
	        CommissionHistory commissionHistory = bonusCommissionHistory.getCommissionHistory();
	        EDITTrxHistory editTrxHistory = commissionHistory.getEDITTrxHistory();
	        EDITTrx editTrx = editTrxHistory.getEDITTrx();
	
	        if (commissionHistory != null) {
	            placedAgentPK = commissionHistory.getPlacedAgentFK();
	        }
	
	        trxEffectiveDate = editTrx.getEffectiveDate().getFormattedDate();
        
            EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
            EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

            FinancialHistory financialHistory = editTrxHistory.getFinancialHistory();
            GroupSetup groupSetup = getGroupSetup(editTrx);
            ClientDetail clientDetail = getClientDetail(commissionHistory);

            segment = getSegment(editTrx);

            ProductStructure productStructure = null;
            int certainDuration = 0;
            String qualNonQual = "*";
            int payoutCertainDuration = 0;

            if (segment != null) {
//                        segmentVO = (SegmentVO) segment.getVO();
                productStructure = getProductStructure(segment.getProductStructureFK());
                qualNonQual = Util.initString(segment.getQualNonQualCT(), "*");

                if (segment.getEffectiveDate() == null) {
                    certainDuration = 1;
                } else {
                    certainDuration = calculateDuration(segment.getEffectiveDate().getFormattedDate(), editTrx.getEffectiveDate().getFormattedDate());
                }

                Payout payout = segment.getPayout();

                if (payout != null) {
                    payoutCertainDuration = payout.getCertainDuration();
                }
            }

            // Build ElementStructureVO
            String memoCode = Util.initString(groupSetup.getMemoCode(), "");

            ElementStructureVO elementStructureVO = buildElementStructureVO(memoCode, certainDuration, qualNonQual);

            // Build ElementVO
            String process = "Commission";
            String event = "*";
            String eventType = commissionHistory.getCommissionTypeCT();

            elementVO = buildElementVO(process, event, eventType, null);

            // Build drivingCompanyInfo
            Map drivingCompanyInfo;

            drivingCompanyInfo = buildDrivingProductInfo(productStructure);


            for (int j = 0; j < BONUS_COMM_ELEMENT_NAMES.length; j++) {
                elementVO.setElementName(BONUS_COMM_ELEMENT_NAMES[j]);

                Element currentElement = new Element(BONUS_COMM_ELEMENT_NAMES[j]);
                EDITBigDecimal elementAmount = currentElement.getCommissionAmount((CommissionHistoryVO) commissionHistory.getVO(), editTrx.getAsVO(), (FinancialHistoryVO) financialHistory.getVO());

                AccountEffectVO[] accountEffectVOs = null;

                elementStructureVO.setCertainPeriod(certainDuration);

                if (elementAmount.doubleValue() != 0) {
                    accountEffectVOs = processNonFundLevelElement(currentElement.getName(), elementAmount, elementVO, elementStructureVO, 
                    		editTrx.getEffectiveDate().getFormattedDate(), drivingCompanyInfo, editTrx.getStatus(), debits, credits);
                }

                if (accountEffectVOs != null) {
                    String fundNumber = "";
                    String effectiveDate = editTrx.getEffectiveDate().getFormattedDate();
                    EDITDate processDate = new EDITDateTime(editTrxHistory.getProcessDateTime().getFormattedDateTime()).getEDITDate();
                    ClientDetailVO clientDetailVO = (ClientDetailVO) clientDetail.getVO();

                    for (int k = 0; k < accountEffectVOs.length; k++) {
                        AccountingDetailVO accountingDetailVO =
                                buildCommissionAccountDetailVO(fundNumber,
                                        (SegmentVO) segment.getVO(),
                                        effectiveDate,
                                        processDate.getFormattedDate(),
                                        memoCode,
                                        "Commission",
                                        editTrx.getStatus(),
                                        drivingCompanyInfo,
                                        accountEffectVOs[k],
                                        clientDetailVO,
                                        editTrx.getAccountingPeriod(),
                                        editTrx.getEDITTrxPK(),
                                        groupSetup.getDistributionCodeCT(),
                                        financialHistory.getDistributionCodeCT(),
                                        payoutCertainDuration,
                                        commissionHistory.getPlacedAgentFK(),
                                        editTrx.getOperator());

                        accountingDetail.add(accountingDetailVO);

                        if (!suppressExtract) {
                            if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                                exportAccounting(accountingDetailVO);
                            } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                                accountingInterfaceCmd.setAccountingInformation((SegmentVO) segment.getVO(), accountEffectVOs[k],
                                        (String) drivingCompanyInfo.get("CompanyName"),
                                        (String) drivingCompanyInfo.get("BusinessContract"),
                                        "Commission", memoCode, effectiveDate,
                                        processDate.getFormattedDate(), fileData);
                                accountingInterfaceCmd.exec();
                            }
                        }
                    }
                }

            }

            debitsEqCredits(accountingDetail, debits, credits);

            saveAccountingDetail(accountingDetail);

            bonusCommissionHistory.setAccountingPendingStatus("N");

            // Update BonusCommissionHistory
            bonusCommissionHistory.hSave();

    	} catch (GenericJDBCException e) {
    		e.printStackTrace();
    		
    		// do not try to log error via SessionHelper while using boneCP... will throw another exception

    		if (accountingDetail != null) {
    			saveAccountingDetail(accountingDetail);
    		}

    		if (bonusCommissionHistory != null) {
	            bonusCommissionHistory.setAccountingPendingStatus("F");
	            bonusCommissionHistory.hSave();
    		}
            
            // throw the exception to pause the damaged thread
            throw e;
            
        } catch (Exception e) {
            e.printStackTrace();
            logError(e, segmentVO, elementVO, trxEffectiveDate, null, placedAgentPK);

            saveAccountingDetail(accountingDetail);

            bonusCommissionHistory.setAccountingPendingStatus("F");

            // Update CommissionHistory
            bonusCommissionHistory.hSave();
        } finally {

            if (accountingDetail.size() > 0) {
                AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                accountingStaging.stageTables(accountingDetail, segment);
            }
        }
    }

    private void debitsEqCredits(List<AccountingDetailVO> accountingDetail, EDITBigDecimal debits, EDITBigDecimal credits) {
        if (debits.isEQ(credits)) {
            for (int v = 0; v < accountingDetail.size(); v++) {
                AccountingDetailVO accountingDetailVO = accountingDetail.get(v);
                accountingDetailVO.setOutOfBalanceInd("N");
            }
        } else {
            for (int v = 0; v < accountingDetail.size(); v++) {
                AccountingDetailVO accountingDetailVO = accountingDetail.get(v);
                accountingDetailVO.setOutOfBalanceInd("Y");
            }
        }
    }

    private void runReinsuranceAccounting(long reinsuranceHistoryPK, int index, int totalPKs) throws Exception {
        
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
    			"Step 4 of 6 - Executing Reinsurance Accounting - ReinsuranceHistoryPKs " + (index+1) + " out of " + totalPKs);

        List<AccountingDetailVO> accountingDetail = new ArrayList<>();

        PrintWriter writer = null;
        ElementVO elementVO = null;
        SegmentVO segmentVO = null;
        ReinsuranceHistoryVO reinsuranceHistoryVO = null;
        String trxEffectiveDate = null;
        
        try {
            reinsuranceHistoryVO = composeReinsuranceHistory(reinsuranceHistoryPK);
            EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) reinsuranceHistoryVO.getParentVO(EDITTrxHistoryVO.class);
            EDITTrxVO editTrxVO = getEDITTrxVO(editTrxHistoryVO);
            trxEffectiveDate = editTrxVO.getEffectiveDate();

            EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
            EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

            GroupSetupVO groupSetupVO = getGroupSetupVO(editTrxHistoryVO);
            ContractClientVO contractClientVO = (ContractClientVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractClientVO.class);

            segmentVO = getSegmentVO(editTrxHistoryVO);

            ProductStructureVO productStructureVO = null;
            int certainDuration = 0;
            String qualNonQual = "*";

            if (segmentVO != null) {
                productStructureVO = getProductStructureVO(segmentVO.getProductStructureFK());
                qualNonQual = Util.initString(segmentVO.getQualNonQualCT(), "*");

                if (segmentVO.getEffectiveDate() == null) {
                    certainDuration = 1;
                } else {
                    certainDuration = calculateDuration(segmentVO.getEffectiveDate(), editTrxVO.getEffectiveDate());
                }
            }

            // Build ElementStructureVO
            String memoCode = Util.initString(groupSetupVO.getMemoCode(), "");

            ElementStructureVO elementStructureVO = buildElementStructureVO(memoCode, certainDuration, qualNonQual);

            // Build ElementVO
            String process = "Reinsurance";
            String event = "*";
            String eventType = "*";

            elementVO = buildElementVO(process, event, eventType, null);

            // Build drivingCompanyInfo
            Map<String, String> drivingCompanyInfo;

            drivingCompanyInfo = buildDrivingProductInfo(productStructureVO);


            for (int j = 0; j < REINSURANCE_ELEMENT_NAMES.length; j++) {
                elementVO.setElementName(REINSURANCE_ELEMENT_NAMES[j]);

                Element currentElement = new Element(REINSURANCE_ELEMENT_NAMES[j]);
                EDITBigDecimal elementAmount = new EDITBigDecimal(String.valueOf(currentElement.getReinsuranceAmount(reinsuranceHistoryVO)));

                AccountEffectVO[] accountEffectVOs = null;

                elementStructureVO.setCertainPeriod(certainDuration);

                if (elementAmount.doubleValue() != 0) {
                    accountEffectVOs = processNonFundLevelElement(currentElement.getName(), elementAmount, elementVO, elementStructureVO, 
                    		editTrxVO.getEffectiveDate(), drivingCompanyInfo, editTrxVO.getStatus(), debits, credits);
                }

                if (accountEffectVOs != null) {
                    String fundNumber = "";
                    String effectiveDate = editTrxVO.getEffectiveDate();
                    EDITDate processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();

                    FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();

                    for (int k = 0; k < accountEffectVOs.length; k++) {
                        AccountingDetailVO accountingDetailVO =
                                buildAccountDetailVO(fundNumber,
                                        segmentVO,
                                        effectiveDate,
                                        processDate.getFormattedDate(),
                                        memoCode,
                                        "Reinsurance",
                                        editTrxVO.getStatus(),
                                        drivingCompanyInfo,
                                        accountEffectVOs[k],
                                        contractClientVO,
                                        segmentVO.getContractNumber(),
                                        segmentVO.getContractNumber(),
                                        editTrxVO.getAccountingPeriod(),
                                        editTrxVO.getEDITTrxPK(),
                                        "",
                                        groupSetupVO.getDistributionCodeCT(),
                                        financialHistoryVO[0].getDistributionCodeCT(),
                                        null,
                                        reinsuranceHistoryVO, 0,
                                        editTrxVO.getOperator(),
                                        editTrxVO.getTrxAmount());

                        accountingDetail.add(accountingDetailVO);

                        if (!suppressExtract) {
                            if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                                exportAccounting(accountingDetailVO);
                            } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                                accountingInterfaceCmd.setAccountingInformation(segmentVO, accountEffectVOs[k],
                                        drivingCompanyInfo.get("CompanyName"),
                                        drivingCompanyInfo.get("BusinessContract"),
                                        "Reinsurance", memoCode, effectiveDate,
                                        processDate.getFormattedDate(), fileData);
                                accountingInterfaceCmd.exec();
                            }
                        }
                    }
                }

            }

            debitsEqCredits(accountingDetail, debits, credits);

            saveAccountingDetail(accountingDetail);

            reinsuranceHistoryVO.setAccountingPendingStatus("N");

            // Update the EDITTrxHistory
            new event.component.EventComponent().createOrUpdateVO(reinsuranceHistoryVO, false);

            if (accountingDetail != null && accountingDetail.size() > 0) {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateSuccess();
            }
            
        } catch (GenericJDBCException e) {
    		e.printStackTrace();
    		
    		// do not try to log error via SessionHelper while using boneCP... will throw another exception

    		if (accountingDetail != null) {
    			saveAccountingDetail(accountingDetail);
    		}

    		if (reinsuranceHistoryVO != null) {
    			reinsuranceHistoryVO.setAccountingPendingStatus("F");
            	new event.component.EventComponent().createOrUpdateVO(reinsuranceHistoryVO, false);
    		}
            
            // throw the exception to pause the damaged thread
            throw e;
            
        } catch (Exception e) {
            e.printStackTrace();
            logError(e, segmentVO, elementVO, trxEffectiveDate, null, 0);

            saveAccountingDetail(accountingDetail);

            reinsuranceHistoryVO.setAccountingPendingStatus("F");

            // Update ReinsuranceHistory
            new event.component.EventComponent().createOrUpdateVO(reinsuranceHistoryVO, false);
        } finally {
            if (writer != null) {
                writer.close();
            }
            
            if (accountingDetail.size() > 0) {
            	Segment segment = null;
                if (segmentVO != null) {
                	segment = new Segment(segmentVO);
                }
                
                AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                accountingStaging.stageTables(accountingDetail, segment);
            }
        }

        SessionHelper.clearSessions();
    }

    private void runCashBatchContracts(CashBatchContract cashBatchContract, Event eventComponent, int index, int totalContracts) throws Exception {
        
    	if (cashBatchContract != null) {
    		
            String process = "Suspense";
            String event;
            String elementName = "OrigAmount";

            String memoCode = "";

            SegmentVO segmentVO = null;

            Map<String, String> drivingCompanyInfo;

            String finalUpdateIndicator = "N";
            	
        	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
        			"Step 5 of 6 - Executing Cash Batch Contract Accounting - CashBatch " + (index+1) + " out of " + totalContracts);
            
            String eventType = "Batch";

            /*cashBatchCount += 1;
            if (cashBatchCount == cashBatchContracts.length) {
                finalUpdateIndicator = "Y";
            }*/

            if (cashBatchContract.getReleaseIndicator().equalsIgnoreCase("V")) {
                event = "Delete";
            } else {
                event = "*";
            }

            List accountingDetail = new ArrayList();
            ElementVO elementVO = null;

            String effectiveDate = cashBatchContract.getCreationDate().getFormattedDate();
            String accountingPeriod = effectiveDate.substring(0, 7);
            EDITBigDecimal batchAmount = cashBatchContract.getAmount();

            try {
                EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
                EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

                String batchId = cashBatchContract.getBatchID();

                int certainDuration = 0;
                String qualNonQual = "*";

                drivingCompanyInfo = new HashMap<>();

                drivingCompanyInfo.put("companyName", "*");
                drivingCompanyInfo.put("marketingPackage", "*");
                drivingCompanyInfo.put("groupProduct", "*");
                drivingCompanyInfo.put("area", "*");
                drivingCompanyInfo.put("businessContract", "*");
                
                String suspenseType = getCashBatchContractSuspenseType(cashBatchContract.getCashBatchContractPK());

                if (suspenseType != null) {
                    // All Suspenses belonging to a CashBatchContract will have same SuspenseType...
                    // checking for at least one Suspense is enough.
                    if (Suspense.SUSPENSETYPE_REDEMPTION.equals(suspenseType)) {
                        eventType = Suspense.SUSPENSETYPE_REDEMPTION;
                    }
                }

                elementVO = buildElementVO(process, event, eventType, elementName);

                ElementStructureVO elementStructureVO = buildElementStructureVO(memoCode, certainDuration, qualNonQual);

                AccountEffectVO[] accountEffectVOs = processSuspenseElement(elementName, batchAmount, elementVO, elementStructureVO, effectiveDate, 
                		drivingCompanyInfo, debits, credits);

                if (accountEffectVOs != null) {
                    for (int j = 0; j < accountEffectVOs.length; j++) {
                        AccountingDetailVO accountingDetailVO =
                                buildAccountDetailVO("",
                                        segmentVO,
                                        effectiveDate,
                                        effectiveDate,
                                        memoCode,
                                        process,
                                        "",
                                        drivingCompanyInfo,
                                        accountEffectVOs[j],
                                        null,
                                        batchId,
                                        batchId,
                                        accountingPeriod,
                                        0,
                                        null, null, null, null, null, 0, null, accountEffectVOs[j].getAccountAmount());

                        accountingDetail.add(accountingDetailVO);

                        if (!suppressExtract) {
                            if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                                exportAccounting(accountingDetailVO);
                            } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                                accountingInterfaceCmd.setAccountingInformation(segmentVO, accountEffectVOs[j],
                                        (String) drivingCompanyInfo.get("CompanyName"),
                                        (String) drivingCompanyInfo.get("BusinessContract"),
                                        process, memoCode, effectiveDate,
                                        effectiveDate, fileData);
                                accountingInterfaceCmd.exec();
                            }
                        }
                    }
                }


                debitsEqCredits(accountingDetail, debits, credits);

                saveAccountingDetail(accountingDetail);

//                    cashBatchContracts[i].setAccountingPendingIndicator("N");
                eventComponent.updateCashBatchContractAccountPendingIndicator(cashBatchContract.getCashBatchContractPK(), "N");

                // Update CashBatchContract
//                    eventComponent.updateCashBatchContract(cashBatchContracts[i], finalUpdateIndicator);

                if (accountingDetail != null && accountingDetail.size() > 0) {
                    AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                    accountingStaging.stageTables(accountingDetail, null);
                }

                if (accountingDetail != null && accountingDetail.size() > 0) {
                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateSuccess();
                }

                //clear memory for each occurrence of the for loop
                accountingDetail = null;

            } catch (GenericJDBCException e) {
        		e.printStackTrace();
        		
        		// do not try to log error via SessionHelper while using boneCP... will throw another exception
        		
        		if (accountingDetail != null) {
        			saveAccountingDetail(accountingDetail);
        		}

        		if (cashBatchContract != null) {
	                cashBatchContract.setAccountingPendingIndicator("F");
	
	                // Update CashBatch (finalUpdateIndicator doesn't appear to be used)
	                eventComponent.updateCashBatchContract(cashBatchContract, finalUpdateIndicator);
        		}

                // throw the exception to pause the damaged thread
                throw e;
                
            } catch (Exception e) {
                e.printStackTrace();
                logError(e, segmentVO, elementVO, effectiveDate, null, 0);

                saveAccountingDetail(accountingDetail);

                cashBatchContract.setAccountingPendingIndicator("F");

                // Update CashBatch (finalUpdateIndicator doesn't appear to be used)
                eventComponent.updateCashBatchContract(cashBatchContract, finalUpdateIndicator);
                
            } finally {

                if (accountingDetail != null && accountingDetail.size() > 0) {
                    AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                    accountingStaging.stageTables(accountingDetail, null);
                }
            }
        }

        SessionHelper.clearSessions();
    }

    private void runSuspenseAccounting(long suspensePK, int index, int totalPKs) throws Exception {

    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
    			"Step 6 of 6 - Executing Suspense Accounting - SuspensePK " + (index+1) + " out of " + totalPKs);
    	
        List<AccountingDetailVO> accountingDetail = new ArrayList<>();

        SegmentVO segmentVO = null;
        PayoutVO payoutVO = null;
        ElementVO elementVO = null;

        SuspenseVO suspenseVO = composeAccountingPendingSuspense(suspensePK);

        String status = suspenseVO.getMaintenanceInd();
        String suspenseType = Suspense.SUSPENSETYPE_CONTRACT;

        if (suspenseVO.getFilteredFundFK() > 0) {
            suspenseType = suspenseVO.getSuspenseType();
        }

        String memoCode = suspenseVO.getMemoCode();

        if (memoCode == null) {
            memoCode = "";
        }

        String contractPlacedFrom = suspenseVO.getContractPlacedFrom();

        if (contractPlacedFrom == null) {
            contractPlacedFrom = "*";
        }

        String process = "Suspense";
        String effectiveDate = suspenseVO.getEffectiveDate();
        String event = contractPlacedFrom;
        String eventType = suspenseType;
        String elementName = "OrigAmount";

        EDITBigDecimal originalAmount = new EDITBigDecimal(String.valueOf(suspenseVO.getOriginalAmount()));

        if (originalAmount.doubleValue() > 0) {
            EDITDate processDate = new EDITDate(suspenseVO.getProcessDate());
            String accountingPeriod = DateTimeUtil.buildAccountingPeriod(processDate.getFormattedYear(), processDate.getFormattedMonth());

            //Process InSuspense
            InSuspenseVO[] inSuspenseVO = suspenseVO.getInSuspenseVO();

            if ((inSuspenseVO != null) && (inSuspenseVO.length > 0)) {
                try {
                    EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) inSuspenseVO[0].getParentVO(EDITTrxHistoryVO.class);
                    EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                    ContractClientVO contractClientVO = getContractClientVO(editTrxHistoryVO);
                    segmentVO = getSegmentVO(editTrxHistoryVO);
                    if (segmentVO.getPayoutVOCount() > 0) {
                        payoutVO = segmentVO.getPayoutVO(0);
                    }

                    ProductStructureVO productStructureVO = null;

                    if (segmentVO != null) {
                        productStructureVO = getProductStructureVO(segmentVO.getProductStructureFK());
                    }

                    EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
                    EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

                    String transactionCode = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());
                    if (transactionCode == null) {
                        transactionCode = CodeTableWrapper.getSingleton().getCodeByCodeTableNameAndCodeDesc("DEATHTRXTYPE", editTrxVO.getTransactionTypeCT());
                    }

                    Map drivingCompanyInfo = getDrivingCompanyInfoMap(productStructureVO);

                    elementVO = buildElementVO(process, event, eventType, elementName);

                    ElementStructureVO elementStructureVO;

                    if (segmentVO != null) {
                        int certainDuration = 0;

                        if (payoutVO != null) {
                            certainDuration = payoutVO.getCertainDuration();
                        }

                        elementStructureVO = buildElementStructureVO(memoCode, certainDuration, segmentVO.getQualNonQualCT());
                    } else {
                        elementStructureVO = buildElementStructureVO(memoCode, 0, "*");
                    }

                    AccountEffectVO[] accountEffectVOs = processSuspenseElement(elementName, originalAmount, elementVO, elementStructureVO, 
                    		editTrxVO.getEffectiveDate(), drivingCompanyInfo, debits, credits);

                    if (accountEffectVOs != null) {
                        for (int j = 0; j < accountEffectVOs.length; j++) {
                            String chargeCode = null;
                            AccountingDetailVO accountingDetailVO =
                                    buildAccountDetailVO("",
                                            segmentVO,
                                            effectiveDate,
                                            processDate.getFormattedDate(),
                                            memoCode,
                                            transactionCode,
                                            "",
                                            drivingCompanyInfo,
                                            accountEffectVOs[j],
                                            contractClientVO,
                                            suspenseVO.getUserDefNumber(),
                                            suspenseVO.getOriginalContractNumber(),
                                            accountingPeriod,
                                            editTrxVO.getEDITTrxPK(),
                                            chargeCode,
                                            null, null, null, null, 0, suspenseVO.getOperator(),
                                            editTrxVO.getTrxAmount());

                            accountingDetail.add(accountingDetailVO);

                            if (!suppressExtract) {
                                if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                                    exportAccounting(accountingDetailVO);
                                } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                                    accountingInterfaceCmd.setAccountingInformation(segmentVO, accountEffectVOs[j],
                                            (String) drivingCompanyInfo.get("CompanyName"),
                                            (String) drivingCompanyInfo.get("BusinessContract"),
                                            transactionCode, memoCode, effectiveDate,
                                            processDate.getFormattedDate(), fileData);
                                    accountingInterfaceCmd.exec();
                                }
                            }
                        }
                    }

                    debitsEqCredits(accountingDetail, debits, credits);

                    saveAccountingDetail(accountingDetail);

                    suspenseVO.setAccountingPendingInd("N");

                    if ((status != null) && (status.equalsIgnoreCase("U") || status.equalsIgnoreCase("A"))) {
                        suspenseVO.setMaintenanceInd("R");
                    }

                    // Commented out for Equitrust Issue #241
                    //                        double suspenseAmount = suspenseVO.getSuspenseAmount();
                    //                        double newSuspenseAmount = suspenseAmount - inSuspenseVO[0].getAmount();
                    //                        if (newSuspenseAmount == 0)
                    //                        {
                    //                            suspenseVO.setMaintenanceInd("D");
                    //                        }
                    //                        else
                    //                        {
                    //                            suspenseVO.setSuspenseAmount(newSuspenseAmount);
                    //                        }
                    // Update Suspense
                    new event.component.EventComponent().createOrUpdateVO(suspenseVO, false);

                    if (accountingDetail != null && accountingDetail.size() > 0) {
                    	Segment segment = null;
                        if (segmentVO != null) {
                        	segment = new Segment(segmentVO);
                        }
                        
                        AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                        accountingStaging.stageTables(accountingDetail, segment);
                    }

                    if (accountingDetail != null && accountingDetail.size() > 0) {
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateSuccess();
                    }

                    //clear memory for each occurrence of the for loop
                    accountingDetail = null;
                
                } catch (GenericJDBCException e) {
            		e.printStackTrace();
            		
            		// do not try to log error via SessionHelper while using boneCP... will throw another exception

            		if (accountingDetail != null) {
            			saveAccountingDetail(accountingDetail);
            		}

                    if (suspenseVO != null) {
	                    suspenseVO.setAccountingPendingInd("F");
	
	                    if (status != null) {
	                        if (status.equalsIgnoreCase("U") || status.equalsIgnoreCase("A")) {
	                            suspenseVO.setMaintenanceInd("R");
	                        }
	                    }
	
	                    if (inSuspenseVO != null && inSuspenseVO.length > 0) {
		                    EDITBigDecimal suspenseAmount = new EDITBigDecimal(String.valueOf(suspenseVO.getSuspenseAmount()));
		
		                    EDITBigDecimal newSuspenseAmount = suspenseAmount.subtractEditBigDecimal(inSuspenseVO[0].getAmount());
		
		                    if (newSuspenseAmount.doubleValue() == 0) {
		                        suspenseVO.setMaintenanceInd("R");
		                    } else {
		                        suspenseVO.setSuspenseAmount(newSuspenseAmount.getBigDecimal());
		                    }
	                    }
	
	                    // Update Suspense
	                    new event.component.EventComponent().createOrUpdateVO(suspenseVO, false);
                    }

                    // throw the exception to pause the damaged thread
                    throw e;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    logError(e, segmentVO, elementVO, effectiveDate, null, 0);

                    saveAccountingDetail(accountingDetail);

                    suspenseVO.setAccountingPendingInd("N");

                    if (status != null) {
                        if (status.equalsIgnoreCase("U") || status.equalsIgnoreCase("A")) {
                            suspenseVO.setMaintenanceInd("R");
                        }
                    }

                    EDITBigDecimal suspenseAmount = new EDITBigDecimal(String.valueOf(suspenseVO.getSuspenseAmount()));

                    EDITBigDecimal newSuspenseAmount = suspenseAmount.subtractEditBigDecimal(inSuspenseVO[0].getAmount());

                    if (newSuspenseAmount.doubleValue() == 0) {
                        suspenseVO.setMaintenanceInd("R");
                    } else {
                        suspenseVO.setSuspenseAmount(newSuspenseAmount.getBigDecimal());
                    }

                    // Update Suspense
                    new event.component.EventComponent().createOrUpdateVO(suspenseVO, false);
                    
                } finally {

                	Segment segment = null;
                    if (segmentVO != null) {
                    	segment = new Segment(segmentVO);
                    }
                    
                    if (accountingDetail != null && accountingDetail.size() > 0) {
                        AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                        accountingStaging.stageTables(accountingDetail, segment);
                    }
                }
            } else {
                try {
                    EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
                    EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

                    String userDefNumber = suspenseVO.getUserDefNumber();

                    if (userDefNumber != null) {
                        segmentVO = getSegmentVOByContractNumber(userDefNumber);
                    }

                    ProductStructureVO productStructureVO = null;
                    int certainDuration = 0;
                    String qualNonQual = "*";

                    if (segmentVO != null) {
                        productStructureVO = getProductStructureVO(segmentVO.getProductStructureFK());
                        if (payoutVO != null) {
                            certainDuration = payoutVO.getCertainDuration();
                        }
                        qualNonQual = Util.initString(segmentVO.getQualNonQualCT(), "*");
                    }

                    Map drivingCompanyInfo;

                    drivingCompanyInfo = getDrivingCompanyInfoMap(productStructureVO);

                    elementVO = buildElementVO(process, event, eventType, elementName);

                    ElementStructureVO elementStructureVO;
                    elementStructureVO = buildElementStructureVO(memoCode, certainDuration, qualNonQual);

                    AccountEffectVO[] accountEffectVOs = processSuspenseElement(elementName, originalAmount, elementVO, elementStructureVO, effectiveDate, 
                    		drivingCompanyInfo, debits, credits);

                    if (accountEffectVOs != null) {
                        for (int j = 0; j < accountEffectVOs.length; j++) {
                            AccountingDetailVO accountingDetailVO = // hit
                                    buildAccountDetailVO("",
                                            segmentVO,
                                            effectiveDate,
                                            processDate.getFormattedDate(),
                                            memoCode,
                                            process,
                                            "",
                                            drivingCompanyInfo,
                                            accountEffectVOs[j],
                                            null,
                                            suspenseVO.getUserDefNumber(),
                                            suspenseVO.getOriginalContractNumber(),
                                            accountingPeriod,
                                            0,
                                            null, null, null, null, null, 0, suspenseVO.getOperator(),
                                            accountEffectVOs[j].getAccountAmount());
//                                                                 suspenseVO.getSuspenseAmount());

                            accountingDetail.add(accountingDetailVO);

                            if (!suppressExtract) {
                                if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                                    exportAccounting(accountingDetailVO);
                                } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                                    accountingInterfaceCmd.setAccountingInformation(segmentVO, accountEffectVOs[j],
                                            (String) drivingCompanyInfo.get("ProductName"),
                                            (String) drivingCompanyInfo.get("BusinessContract"),
                                            process, memoCode, effectiveDate,
                                            processDate.getFormattedDate(), fileData);
                                    accountingInterfaceCmd.exec();
                                }
                            }
                        }
                    }

                    debitsEqCredits(accountingDetail, debits, credits);

                    saveAccountingDetail(accountingDetail);

                    suspenseVO.setAccountingPendingInd("N");

                    if ((new EDITBigDecimal(suspenseVO.getSuspenseAmount()).doubleValue() == 0) && suspenseVO.getSuspenseType().equalsIgnoreCase("Batch")) {
                        suspenseVO.setMaintenanceInd("A");
                    }

                    // Update Suspense
                    new event.component.EventComponent().createOrUpdateVO(suspenseVO, false);

                    if (accountingDetail != null && accountingDetail.size() > 0) {
                    	Segment segment = null;
                        if (segmentVO != null) {
                        	segment = new Segment(segmentVO);
                        }
                        
                        AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                        accountingStaging.stageTables(accountingDetail, segment);
                    }

                    if (accountingDetail != null && accountingDetail.size() > 0) {
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateSuccess();
                    }
                    
                    //clear memory for each occurrence of the for loop
                    accountingDetail = null;
                
                } catch (GenericJDBCException e) {
            		e.printStackTrace();
            		
            		// do not try to log error via SessionHelper while using boneCP... will throw another exception

            		if (accountingDetail != null) {
            			saveAccountingDetail(accountingDetail);
            		}

                    if (suspenseVO != null) {
	                    suspenseVO.setAccountingPendingInd("F");
	
	                    if ((new EDITBigDecimal(suspenseVO.getSuspenseAmount()).doubleValue() == 0) && suspenseVO.getSuspenseType().equalsIgnoreCase("Batch")) {
	                        suspenseVO.setMaintenanceInd("A");
	                    }
	
	                    // Update Suspense
	                    new event.component.EventComponent().createOrUpdateVO(suspenseVO, false);
                    }
            		
                    // throw the exception to pause the damaged thread
                    throw e;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    logError(e, segmentVO, elementVO, effectiveDate, suspenseVO.getUserDefNumber(), 0);

                    saveAccountingDetail(accountingDetail);

                    suspenseVO.setAccountingPendingInd("F");

                    if ((new EDITBigDecimal(suspenseVO.getSuspenseAmount()).doubleValue() == 0) && suspenseVO.getSuspenseType().equalsIgnoreCase("Batch")) {
                        suspenseVO.setMaintenanceInd("A");
                    }

                    // Update Suspense
                    new event.component.EventComponent().createOrUpdateVO(suspenseVO, false);
                    
                } finally {

                	try {
	                    if (accountingDetail != null && accountingDetail.size() > 0) {
	                    	Segment segment = null;
	                        if (segmentVO != null) {
	                        	segment = new Segment(segmentVO);
	                        }
	                        
	                        AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
	                        accountingStaging.stageTables(accountingDetail, segment);
	                    }
                    
                	} catch(GenericJDBCException e) {
                		e.printStackTrace();

                		if (suspenseVO != null) {
	                        suspenseVO.setAccountingPendingInd("F");
	                        new event.component.EventComponent().createOrUpdateVO(suspenseVO, false);
                		}
                		
                        // throw the exception to pause the damaged thread
                        throw e;
                    }
                }
            }
        }
    
        SessionHelper.clearSessions();
    }

    private Map getDrivingCompanyInfoMap(ProductStructureVO productStructureVO) {
        Map drivingCompanyInfo;
        if (productStructureVO != null) {
            drivingCompanyInfo = buildDrivingProductInfo(productStructureVO);
        } else {
            drivingCompanyInfo = new HashMap();

            drivingCompanyInfo.put("companyName", "*");
            drivingCompanyInfo.put("marketingPackage", "*");
            drivingCompanyInfo.put("groupProduct", "*");
            drivingCompanyInfo.put("area", "*");
            drivingCompanyInfo.put("businessContract", "*");
        }
        return drivingCompanyInfo;
    }

    private void runFeeAccounting(String accountingProcessDate) throws Exception {

    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
    			"Step 7 of 8 - Executing Fee Accounting");
    	
    	PrintWriter writer = null;

        long start = System.currentTimeMillis();
        FeeVO[] feeVOs = getFees(accountingProcessDate);
        System.out.println("Fee accounting start.");
        if (feeVOs != null) {
            for (int i = 0; i < feeVOs.length; i++) {
                ElementVO elementVO = null;

                List accountingDetail = new ArrayList<AccountingDetailVO>();

                FeeDescriptionVO feeDescriptionVO = (FeeDescriptionVO) feeVOs[i].getParentVO(FeeDescriptionVO.class);
                FilteredFundVO filteredFundVO = (FilteredFundVO) feeVOs[i].getParentVO(FilteredFundVO.class);

                String process = feeVOs[i].getTransactionTypeCT();
                String effectiveDate = feeVOs[i].getEffectiveDate();
                String event = "*";
                String eventType = Util.initString(feeVOs[i].getToFromInd(), "*");
                String elementName = "";
                if (feeDescriptionVO != null) {
                    elementName = feeDescriptionVO.getFeeTypeCT();
                }
                String memoCode = "";

                EDITBigDecimal amount = new EDITBigDecimal(String.valueOf(feeVOs[i].getTrxAmount()));

                try {
                    if (amount.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) ||
                            amount.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)) {
                        String processDateTime = feeVOs[i].getProcessDateTime();
                        String accountingPeriod = feeVOs[i].getAccountingPeriod();

                        EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
                        EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

                        ProductFilteredFundStructureVO[] cffsVO = filteredFundVO.getProductFilteredFundStructureVO();

                        if (cffsVO != null && cffsVO.length > 0) {
                            ProductStructureVO productStructureVO = (ProductStructureVO) cffsVO[0].getParentVO(ProductStructureVO.class);

                            Map drivingCompanyInfo = getDrivingCompanyInfoMap(productStructureVO);

                            elementVO = buildElementVO(process, event, eventType, elementName);

                            //for GainLoss fee - need to determine whether it should be accounted for as
                            //a gain or a loss
                            if (elementName.equalsIgnoreCase(GAIN_LOSS_FEE)) {
                                if ((amount.isGT("0") && process.equalsIgnoreCase(DFACC_TRX)) ||
                                        (amount.isLT("0") && process.equalsIgnoreCase(DFCASH_TRX))) {
                                    elementName = Element.GAIN;
                                } else if ((amount.isLT("0") && process.equalsIgnoreCase(DFACC_TRX)) ||
                                        (amount.isGT("0") && process.equalsIgnoreCase(DFCASH_TRX))) {
                                    elementName = Element.LOSS;
                                }
                            }

                            elementVO.setElementName(elementName);

                            ElementStructureVO elementStructureVO;
                            elementStructureVO = buildElementStructureVO(memoCode, 0, "*");

                            EDITDate accountingProcessDateAsEDITDate = new EDITDate(accountingProcessDate);

                            EDITDate processDate = new EDITDateTime(processDateTime).getEDITDate();

                            if (!accountingProcessDateAsEDITDate.equals(processDate)) {
                                accountingPeriod = accountingProcessDateAsEDITDate.getFormattedYear() + "/" + accountingProcessDateAsEDITDate.getFormattedMonth();
                            }

                            AccountEffectVO[] accountEffectVOs = processSuspenseElement(elementName, amount, elementVO, elementStructureVO, effectiveDate, 
                            		drivingCompanyInfo, debits, credits);

                            if (accountEffectVOs != null) {
                                for (int k = 0; k < accountEffectVOs.length; k++) {
                                    AccountingDetailVO accountingDetailVO =
                                            buildAccountDetailVO(filteredFundVO.getFundNumber(),
                                                    null,
                                                    effectiveDate,
                                                    processDate.getFormattedDate(),
                                                    memoCode,
                                                    process,
                                                    feeVOs[i].getStatusCT(),
                                                    drivingCompanyInfo,
                                                    accountEffectVOs[k],
                                                    null,
                                                    feeVOs[i].getContractNumber(),
                                                    feeVOs[i].getContractNumber(),
                                                    accountingPeriod,
                                                    0,
                                                    null, null, null, null, null, 0, feeVOs[i].getOperator(),
                                                    accountEffectVOs[k].getAccountAmount());

                                    accountingDetail.add(accountingDetailVO);

                                    if (!suppressExtract) {
                                        if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML)) {
                                            exportAccounting(accountingDetailVO);
                                        } else if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_FLAT)) {
                                            accountingInterfaceCmd.setAccountingInformation(null, accountEffectVOs[k],
                                                    (String) drivingCompanyInfo.get("CompanyName"),
                                                    (String) drivingCompanyInfo.get("BusinessContract"),
                                                    process, memoCode, effectiveDate,
                                                    processDateTime.substring(0, 10), fileData);
                                            accountingInterfaceCmd.exec();
                                        }
                                    }
                                }
                            }

                            debitsEqCredits(accountingDetail, debits, credits);

                            saveAccountingDetail(accountingDetail);

                            // Depending on ProcessDate and ProcessDateTime comparison the AccountingPeriod may change ...
                            feeVOs[i].setAccountingPeriod(accountingPeriod);

                            feeVOs[i].setAccountingPendingStatus("N");

                            // Update Fee
                            new engine.component.CalculatorComponent().createOrUpdateVO(feeVOs[i], false);

                        }
                    }

                    if (accountingDetail != null && accountingDetail.size() > 0) {
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logError(e, null, elementVO, effectiveDate, filteredFundVO.getFundNumber(), 0);

                    saveAccountingDetail(accountingDetail);

                    feeVOs[i].setAccountingPendingStatus("N");

                    // Update Fee
                    new engine.component.CalculatorComponent().createOrUpdateVO(feeVOs[i], false);
                } finally {
                    if (writer != null) {
                        writer.close();
                    }

                    if (accountingDetail.size() > 0) {
                        AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
                        accountingStaging.stageTables(accountingDetail, null);
                    }
                }


            }
            long stop = System.currentTimeMillis();
            System.out.println("Processed " + feeVOs.length +
                    " fee records in " + (stop - start) + " milliseconds");
        }
    }

    private void logError(Exception e, SegmentVO segmentVO, ElementVO elementVO, String effectiveDate, String fundNumber, long placedAgentPK) {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateFailure();

        LogEvent logEvent = new LogEvent(e.getMessage(), e);

        if (segmentVO != null) {
            logEvent.addToContext("ContractFundNumber", segmentVO.getContractNumber());
        } else if (fundNumber != null) {
            logEvent.addToContext("ContractFundNumber", fundNumber);
        } else {
            logEvent = new LogEvent(new Date().toString() + ":" + " Accounting Failed Trying to process Accounting on " + elementVO.getProcess() + ", Element = " + elementVO.getElementName(), e);
        }

        logEvent.addToContext("EffectiveDate", effectiveDate);

        logEvent.addToContext("ProcessDate", new EDITDate().getFormattedDate());

        logEvent.addToContext("Element", elementVO.getElementName());
        
        logEvent.addToContext("Process", elementVO.getProcess());

        String agentNumber = "";
        if (placedAgentPK != 0) {
            PlacedAgent placedAgent = PlacedAgent.findBy_PK_V2(placedAgentPK);
            AgentContract agentContract = placedAgent.getAgentContract();
            agentNumber = agentContract.getAgent().getAgentNumber();
            logEvent.addToContext("AgentNumber", agentNumber);
        }

        Logging.getLogger(Logging.ACCOUNTING).error(logEvent);

        logToDatabase(e, segmentVO, elementVO, effectiveDate, agentNumber, fundNumber);
    }

    private void logToDatabase(Exception e, SegmentVO segmentVO, ElementVO elementVO, String effectiveDate, String agentNumber, String fundNumber) {
        String message = e.getMessage();

        EDITMap columnInfo = new EDITMap();

        EDITDate processDate = new EDITDate();

        if (message == null) {
        	if (e instanceof java.lang.NullPointerException && e.getStackTrace() != null && e.getStackTrace().length > 0) {
	    		String fileName = e.getStackTrace()[0].getFileName();
	    		String methodName = e.getStackTrace()[0].getMethodName();
	    		int lineNumber = e.getStackTrace()[0].getLineNumber();
	    		
	    		message = "Accounting NullPointerException at File: " + fileName + ", Method: " + methodName + ", Line: " + lineNumber +
    					" For Element: " + elementVO.getProcess() + "/" + elementVO.getElementName();
        	} else {       
        		message = "Accounting Failed Trying to Process Accounting on " + elementVO.getProcess() + ", Element = " + elementVO.getElementName();
        	}
        }

        if (segmentVO != null) {
            columnInfo.put("ContractFundNumber", segmentVO.getContractNumber());
            columnInfo.put("ContractNumber", segmentVO.getContractNumber());
        } else if (fundNumber != null) {
            columnInfo.put("ContractFundNumber", fundNumber);
        }

        columnInfo.put("AgentNumber", agentNumber);
        columnInfo.put("ProcessDate", processDate.getFormattedDate());
        columnInfo.put("Process", elementVO.getProcess());
        columnInfo.put("Element", elementVO.getElementName());

        Log.logToDatabase(Log.ACCOUNTING, message, columnInfo);
    }

    private ProductStructureVO getProductStructureVO(long productStructurePK) {

        return (ProductStructureVO) productStructureVOCache.get(new Long(productStructurePK));
    }

    /**
     * Updates the AccountingPendingStatus to 'N' for the all the Accounting Details with AccountingPendingStatus of 'Y'
     * and whose Processing Date is earlier than given date.
     *
     * @param processDate
     */
    private void updateManualJournalEntries(String processDate) {
    	EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
    			"Step 7 of 7 - Executing Update Manual Journal Entries");
    	
        new AccountingDetailDAO().setAccountPendingStatusLTEDate("N", processDate);
    }

    private ProductStructure getProductStructure(long productStructurePK) {

        return ProductStructure.findByPK(productStructurePK);
    }

    private long[] getEDITTrxHistoryPKs(String accountingProcessDate) throws Exception {

        return new FastDAO().findEDITTrxHistoryPKsForAccounting("Y", accountingProcessDate);
    }


    private EDITTrxHistoryVO[] composeHistoryForPolYearEndAcctg(long segmentPK, String effectiveDate) throws Exception {
        List<Class> voInclusionList = new ArrayList<>();
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(EDITTrxVO.class);

        EDITDate fromDate = (new EDITDate(effectiveDate).subtractYears(1));

        return new EventComponent().composeEDITTrxHistoryByEffectiveDateForAccting(segmentPK, fromDate.getFormattedDate(), effectiveDate, "PE", voInclusionList);
    }

    private EDITTrxVO getEDITTrxVO(EDITTrxHistoryVO editTrxHistoryVO) {

        return (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
    }

    private long[] getCommissionHistoryPKs(String accountingProcessDate) throws Exception {
        return new FastDAO().findCommissionHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE("Y", accountingProcessDate);
    }

    private long[] getBonusCommissionHistoryPKs(String accountingProcessDate) throws Exception {
        return new FastDAO().findBonusCommissionHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE("Y", accountingProcessDate);
    }

    private EDITTrxHistoryVO composeEDITTrxHistoryVO(long editTrxHistoryPK) throws Exception {
        List<Class> voInclusionList = new ArrayList<>();
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(ChargeHistoryVO.class);
        voInclusionList.add(WithholdingHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(GroupSetupVO.class);
        voInclusionList.add(OutSuspenseVO.class);
        voInclusionList.add(InvestmentHistoryVO.class);

        return new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryPK);
    }

    private void runEDITTrxHistoryAccounting(long editTrxHistoryPK, int index, int totalPKs) throws Exception {

        // Run Non-Suspense Element First
    	EDITTrxHistoryVO editTrxHistoryVO = null;
    	List<AccountingDetailVO> accountingDetail = new ArrayList<AccountingDetailVO>();
        ElementVO elementVO = null;
        SegmentVO segmentVO = null;
        String trxEffectiveDate = null;
        
    	try { 
    		
    		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).setBatchMessage(
        			"Step 1 of 6 - Executing EditTrxHistory Accounting - EDITTrxHistory " + (index+1) + " out of " + totalPKs);
            
            editTrxHistoryVO = composeEDITTrxHistoryVO(editTrxHistoryPK);
            EDITTrxVO editTrxVO = getEDITTrxVO(editTrxHistoryVO);
            FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();

            trxEffectiveDate = editTrxVO.getEffectiveDate();
            boolean isHedgeFundTrx = checkForHedgeFundTrx(editTrxVO.getTransactionTypeCT());
            		
            if (!isHedgeFundTrx) {
                calculateGainLoss(editTrxHistoryVO);
            }

            EDITBigDecimal debits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
            EDITBigDecimal credits = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

            if (!editTrxVO.getTransactionTypeCT().equalsIgnoreCase("BCK") && !editTrxVO.getTransactionTypeCT().equalsIgnoreCase("CK")) {
                
            	ContractClientVO contractClientVO = getContractClientVO(editTrxHistoryVO);
                segmentVO = getSegmentVO(editTrxHistoryVO);

                int certainDuration = 0;
                String qualNonQual = "*";
                DepositsVO[] depositsVOs = null;
                ProductStructureVO productStructureVO = null;
                int payoutCertainDuration = 0;

                if (segmentVO != null) {
                    depositsVOs = segmentVO.getDepositsVO();

                    if (segmentVO.getQualNonQualCT() != null) {
                        qualNonQual = segmentVO.getQualNonQualCT();
                    }

                    String effectiveDate = segmentVO.getEffectiveDate();

                    if (effectiveDate == null) {
                        certainDuration = 1;
                    } else {
                        if (financialHistoryVO.length > 0 && financialHistoryVO[0].getDuration() > 0) {
                            certainDuration = financialHistoryVO[0].getDuration();
                        } else {
                            certainDuration = calculateDuration(segmentVO.getEffectiveDate(), editTrxVO.getEffectiveDate());
                        }
                    }

                    productStructureVO = getProductStructureVO(segmentVO.getProductStructureFK());

                    if (segmentVO.getPayoutVOCount() > 0) {
                        payoutCertainDuration = segmentVO.getPayoutVO(0).getCertainDuration();
                    }
                }

                GroupSetupVO groupSetupVO = getGroupSetupVO(editTrxHistoryVO);
                ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
                OutSuspenseVO[] outSuspenseVO = contractSetupVO.getOutSuspenseVO();

                // Build ElementStructureVO
                String memoCode = Util.initString(groupSetupVO.getMemoCode(), "");
                // Update memoCode if a NSF reversal
                if (editTrxVO.getReversalReasonCodeCT() != null && editTrxVO.getReversalReasonCodeCT().equalsIgnoreCase("NSF")) {
                	memoCode = editTrxVO.getReversalReasonCodeCT();
                }
                
                ElementStructureVO elementStructureVO = buildElementStructureVO(memoCode, certainDuration, qualNonQual);

                // Build ElementVO
                String process = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());
                if (process == null) {
                    process = CodeTableWrapper.getSingleton().getCodeByCodeTableNameAndCodeDesc("DEATHTRXTYPE", editTrxVO.getTransactionTypeCT());
                }

                String event = editTrxVO.getStatus();
                String eventType = "*";

                if ((depositsVOs != null) && (depositsVOs.length > 0)) {
                    if (depositsVOs[0].getDepositTypeCT() != null) {
                        eventType = depositsVOs[0].getDepositTypeCT();
                    }
                }

                elementVO = buildElementVO(process, event, eventType, null);

                // Build drivingCompanyInfo
                Map<String, String> drivingCompanyInfo;

                if (productStructureVO != null) {
                    drivingCompanyInfo = buildDrivingProductInfo(productStructureVO);
                } else {
                    drivingCompanyInfo = new HashMap<>();

                    drivingCompanyInfo.put("productName", "*");
                    drivingCompanyInfo.put("marketingPackage", "*");
                    drivingCompanyInfo.put("groupProduct", "*");
                    drivingCompanyInfo.put("area", "*");
                    drivingCompanyInfo.put("businessContract", "*");
                }

                boolean isULPolicy = false;
                if (segmentVO != null && segmentVO.getSegmentNameCT() != null && segmentVO.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_UL)) {
                	isULPolicy = true;
                }
                
                String[] elementNames; 
                if (isULPolicy) {
                	elementNames = ELEMENT_NAMES_UL;
                } else {
                	elementNames = ELEMENT_NAMES;
                }
                
                for (int j = 0; j < elementNames.length; j++) {
                    if (!elementNames[j].equalsIgnoreCase("Suspense")) {
                        elementVO.setElementName(elementNames[j]);

                        Element currentElement = new Element(elementNames[j]);

                        CommissionablePremiumHistory[] commPremHistories = null;

                        if (elementNames[j].equalsIgnoreCase(Element.GROSS_AMOUNT)) {
                            commPremHistories = CommissionablePremiumHistory.findBy_EDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
                        }

                        if (commPremHistories == null) {
                            EDITBigDecimal elementAmountFrmVO = currentElement.getAmount(editTrxHistoryVO);
                            EDITBigDecimal elementAmount = new EDITBigDecimal(Util.roundToNearestCent(elementAmountFrmVO).getBigDecimal());
                            calculateIfFundElement(accountingDetail, elementVO, segmentVO, editTrxHistoryVO, editTrxVO, contractClientVO, 
                            		payoutCertainDuration, groupSetupVO, memoCode, elementStructureVO, drivingCompanyInfo, currentElement,
                            		debits, credits);
                            updateAccountingDetailNotFundElement(accountingDetail, elementVO, segmentVO, editTrxHistoryVO, editTrxVO, contractClientVO, 
                            		payoutCertainDuration, groupSetupVO, memoCode, elementStructureVO, drivingCompanyInfo, currentElement, elementAmount,
                            		debits, credits);

                        } else {

                        	EDITBigDecimal cphSum = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
                        	for (CommissionablePremiumHistory cph : commPremHistories) {
                                cphSum = cphSum.addEditBigDecimal(cph.getCommissionablePremium());
                        	}
                        	
                        	if (cphSum.isGT(new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))) {
                        	
                                for (int k = 0; k < commPremHistories.length; k++) {

                                    certainDuration = commPremHistories[k].getDuration();

                                    elementStructureVO = buildElementStructureVO(memoCode, certainDuration, qualNonQual);

                                    EDITBigDecimal elementAmountFrmVO = commPremHistories[k].getCommissionablePremium();

                                    EDITBigDecimal elementAmount = new EDITBigDecimal(Util.roundToNearestCent(elementAmountFrmVO).getBigDecimal());

                                    AccountEffectVO[] accountEffectVOs = null;

                                    if (elementAmount.doubleValue() != 0) {
                                        accountEffectVOs = processNonFundLevelElement(currentElement.getName(), elementAmount, elementVO, 
                                        		elementStructureVO, editTrxVO.getEffectiveDate(), drivingCompanyInfo, editTrxVO.getStatus(),
                                        		debits, credits);
                                    }

                                    if (accountEffectVOs != null) {
                                        ChargeCodeVO chargeCodeVO = null;
                                        updateAccountingDetail(accountEffectVOs, currentElement, editTrxVO,
                                                editTrxHistoryVO, segmentVO, memoCode, drivingCompanyInfo,
                                                contractClientVO, accountingDetail, chargeCodeVO,
                                                groupSetupVO.getDistributionCodeCT(), payoutCertainDuration);
                                    }
                                }
                        	} else {
                                AccountEffectVO[] accountEffectVOs = null;

                        		// use financialHistory.grossAmount
                        		if (financialHistoryVO.length > 0 && financialHistoryVO[0].getDuration() > 0) {
                                    accountEffectVOs = processNonFundLevelElement(currentElement.getName(), new EDITBigDecimal(financialHistoryVO[0].getGrossAmount()), 
                                    		elementVO, elementStructureVO, editTrxVO.getEffectiveDate(), drivingCompanyInfo, editTrxVO.getStatus(),
                                    		debits, credits);
                                }
                        		
                        		if (accountEffectVOs != null) {
                                    ChargeCodeVO chargeCodeVO = null;
                                    updateAccountingDetail(accountEffectVOs, currentElement, editTrxVO,
                                            editTrxHistoryVO, segmentVO, memoCode, drivingCompanyInfo,
                                            contractClientVO, accountingDetail, chargeCodeVO,
                                            groupSetupVO.getDistributionCodeCT(), payoutCertainDuration);
                                }
                        	}
                        }
                    } else {
//                            System.out.println("outSuspense: "+i);
                        //Suspense Element accounting (OutSuspense)
                        if (outSuspenseVO != null) {
                            EDITBigDecimal suspenseAmount = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
                            elementVO.setElementName(elementNames[j]);

                            Element currentElement = new Element(elementNames[j]);

                            for (int o = 0; o < outSuspenseVO.length; o++) {
                                suspenseAmount = suspenseAmount.addEditBigDecimal(outSuspenseVO[o].getAmount());
                            }

                            AccountEffectVO[] accountEffectVOs = null;

                            if (suspenseAmount.doubleValue() > 0) {
                                accountEffectVOs = processNonFundLevelElement(currentElement.getName(), suspenseAmount, elementVO, elementStructureVO, 
                                		editTrxVO.getEffectiveDate(), drivingCompanyInfo, editTrxVO.getStatus(), debits, credits);
                            }

                            if (accountEffectVOs != null) {
                                ChargeCodeVO chargeCodeVO = null;
                                updateAccountingDetail(accountEffectVOs, currentElement, editTrxVO,
                                        editTrxHistoryVO, segmentVO, memoCode, drivingCompanyInfo,
                                        contractClientVO, accountingDetail, chargeCodeVO,
                                        groupSetupVO.getDistributionCodeCT(), payoutCertainDuration);
                            }

                            //clear memory
                        }
                    }
                }

                for (int j = 0; j < Element.chargeTypes.size(); j++) {
                    if (!isULPolicy || (isULPolicy && (
                    		((String)Element.chargeTypes.get(j)).equalsIgnoreCase("PremDueUnpaid") || 
                    		((String)Element.chargeTypes.get(j)).equalsIgnoreCase("SuspenseRefund") ||
                    		((String)Element.chargeTypes.get(j)).equalsIgnoreCase("RefundPremium") ||
                        	((String)Element.chargeTypes.get(j)).equalsIgnoreCase("OrigAmount") ||
                        	((String)Element.chargeTypes.get(j)).equalsIgnoreCase("AdminFee") ))) {

                        elementVO.setElementName((String) Element.chargeTypes.get(j));

                        Element currentElement = new Element((String) Element.chargeTypes.get(j));

                        EDITBigDecimal elementAmountFrmVO = currentElement.getAmount(editTrxHistoryVO);
                        EDITBigDecimal elementAmount = new EDITBigDecimal(Util.roundToNearestCent(elementAmountFrmVO).getBigDecimal());

                        AccountEffectVO[] accountEffectVOs;

                        calculateIfFundElement(accountingDetail, elementVO, segmentVO, editTrxHistoryVO, editTrxVO, contractClientVO, payoutCertainDuration, 
                        		groupSetupVO, memoCode, elementStructureVO, drivingCompanyInfo, currentElement, debits, credits);

                        updateAccountingDetailNotFundElement(accountingDetail, elementVO, segmentVO, editTrxHistoryVO, editTrxVO, contractClientVO, 
                        		payoutCertainDuration, groupSetupVO, memoCode, elementStructureVO, drivingCompanyInfo, currentElement, elementAmount,
                        		debits, credits);

                        //clear memory
                    }
                }

                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PE")) {
                    processPEFundLevelElements(segmentVO,
                            editTrxVO,
                            editTrxHistoryVO,
                            drivingCompanyInfo,
                            elementVO,
                            elementStructureVO,
                            contractClientVO,
                            accountingDetail,
                            groupSetupVO, 
                            debits, 
                            credits);
                }

                debitsEqCredits(accountingDetail, debits, credits);
                saveAccountingDetail(accountingDetail);
                editTrxHistoryVO.setAccountingPendingStatus("N");
                // Update the EDITTrxHistory
                new event.component.EventComponent().createOrUpdateVO(editTrxHistoryVO, false);

                if (accountingDetail != null && accountingDetail.size() > 0) {
                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_XML).updateSuccess();
                }
            }
    	} catch (GenericJDBCException e) {
    		e.printStackTrace();
    		
    		// do not try to log error via SessionHelper while using boneCP... will throw another exception

    		if (accountingDetail != null) {
	            for (int v = 0; v < accountingDetail.size(); v++) {
	                AccountingDetailVO accountingDetailVO = (AccountingDetailVO) accountingDetail.get(v);
	                accountingDetailVO.setOutOfBalanceInd("Y");
	            }
	
	            saveAccountingDetail(accountingDetail);
    		}
            
            if (editTrxHistoryVO != null) {
	            editTrxHistoryVO.setAccountingPendingStatus("F");
		        new event.component.EventComponent().createOrUpdateVO(editTrxHistoryVO, false);
            }
            
            // throw the exception to pause the damaged thread
            throw e;
            
        } catch (Exception e) {
            e.printStackTrace();
            logError(e, segmentVO, elementVO, trxEffectiveDate, null, 0);

            for (int v = 0; v < accountingDetail.size(); v++) {
                AccountingDetailVO accountingDetailVO = (AccountingDetailVO) accountingDetail.get(v);
                accountingDetailVO.setOutOfBalanceInd("Y");
            }

            saveAccountingDetail(accountingDetail);
            editTrxHistoryVO.setAccountingPendingStatus("F");

            // Update the EDITTrxHistory
            new event.component.EventComponent().createOrUpdateVO(editTrxHistoryVO, false);
        } finally {
            try {
	            if (accountingDetail.size() > 0) {
	            	Segment segment = null;
	                if (segmentVO != null) {
	                	segment = new Segment(segmentVO);
	                }
	                
	                AccountingStaging accountingStaging = new AccountingStaging(staging_event_type, stagingDate);
	                accountingStaging.stageTables(accountingDetail, segment);
	            }
	            
	            SessionHelper.clearSessions();
            
            } catch(GenericJDBCException e) {
        		e.printStackTrace();

        		if (editTrxHistoryVO != null) {
    	            editTrxHistoryVO.setAccountingPendingStatus("F");
    		        new event.component.EventComponent().createOrUpdateVO(editTrxHistoryVO, false);
                }
        		
                // throw the exception to pause the damaged thread
                throw e;
            }
        }
    }

    private CommissionHistoryVO composeCommissionHistory(long commissionHistoryPK) throws Exception {
        List<Class> voInclusionList = new ArrayList<>();
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientDetailVO.class);
        voInclusionList.add(ClientAddressVO.class);
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(GroupSetupVO.class);

        return new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryPK);
    }


    private ReinsuranceHistoryVO composeReinsuranceHistory(long reinsuranceHistoryPK) throws Exception {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractClientVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(GroupSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(ReinsurerVO.class);
        voInclusionList.add(ContractTreatyVO.class);
        voInclusionList.add(TreatyVO.class);
        voInclusionList.add(TreatyGroupVO.class);

        return new ReinsuranceHistoryComposer(voInclusionList).compose(reinsuranceHistoryPK);
    }

    private long[] getReinsuranceHistoryPKs() throws Exception {

        return new FastDAO().findReinsuranceHistoryPKsByByAccountPendingStatus("Y");
    }

    private long[] getAccountingPendingSuspensePKs(String accountingProcessDate) throws Exception {

        return new FastDAO().findAccountingPendingSuspenseEntries(accountingProcessDate);
    }

    private CashBatchContract[] getAccountingPendingCashBatchContracts() throws Exception {

        return CashBatchContract.findByAccountingPendingIndicator("Y");
    }
    
    private String getCashBatchContractSuspenseType(long cashBatchContractPK) throws Exception {

        return event.dm.dao.DAOFactory.getCashBatchContractDAO().getCashBatchContractSuspenseType(cashBatchContractPK);
    }

    private SuspenseVO composeAccountingPendingSuspense(long suspensePK) throws Exception {
        List voInclusionList = new ArrayList();
        voInclusionList.add(InSuspenseVO.class);
        voInclusionList.add(OutSuspenseVO.class);
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(PayoutVO.class);

        return new event.dm.composer.VOComposer().composeSuspenseVO(suspensePK, voInclusionList);
    }

    private Map<String, String> buildDrivingProductInfo(ProductStructureVO productStructureVO) {
        Map<String, String> drivingCompanyInfo = new HashMap<>();

        if (productStructureVO != null) {

            Company company = Company.findByPK(productStructureVO.getCompanyFK());
            drivingCompanyInfo.put("companyName", company.getCompanyName());
            drivingCompanyInfo.put("marketingPackage", productStructureVO.getMarketingPackageName());
            drivingCompanyInfo.put("groupProduct", productStructureVO.getGroupProductName());
            drivingCompanyInfo.put("area", productStructureVO.getAreaName());
            drivingCompanyInfo.put("businessContract", productStructureVO.getBusinessContractName());
        } else {
            drivingCompanyInfo.put("companyName", "Commission");
            drivingCompanyInfo.put("marketingPackage", "*");
            drivingCompanyInfo.put("groupProduct", "*");
            drivingCompanyInfo.put("area", "*");
            drivingCompanyInfo.put("businessContract", "*");
        }

        return drivingCompanyInfo;
    }

    private Map<String, String> buildDrivingProductInfo(ProductStructure productStructure) {
        Map<String, String> drivingCompanyInfo = new HashMap<>();

        if (productStructure != null) {
            Company company = Company.findByPK(productStructure.getCompanyFK());
            drivingCompanyInfo.put("companyName", company.getCompanyName());
            drivingCompanyInfo.put("marketingPackage", productStructure.getMarketingPackageName());
            drivingCompanyInfo.put("groupProduct", productStructure.getGroupProductName());
            drivingCompanyInfo.put("area", productStructure.getAreaName());
            drivingCompanyInfo.put("businessContract", productStructure.getBusinessContractName());
        } else {
            drivingCompanyInfo.put("companyName", "Commission");
            drivingCompanyInfo.put("marketingPackage", "*");
            drivingCompanyInfo.put("groupProduct", "*");
            drivingCompanyInfo.put("area", "*");
            drivingCompanyInfo.put("businessContract", "*");
        }

        return drivingCompanyInfo;
    }

    private SegmentVO getSegmentVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception {
        long segmentPK = ((ContractSetupVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class).getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class)).getSegmentFK();

        if (segmentPK == 0) {
            return null;
        } else {
            List<Class> voInclusionList = new ArrayList<>();
            voInclusionList.add(SegmentVO.class);
            voInclusionList.add(PayoutVO.class);
            voInclusionList.add(DepositsVO.class);

            return new VOComposer().composeSegmentVO(segmentPK, voInclusionList);
        }
    }

    private Segment getSegment(EDITTrx editTrx) throws Exception {
        return editTrx.getClientSetup().getContractSetup().getSegment();
    }

    private SegmentVO getSegmentVOByContractNumber(String contractNumber) throws Exception {
        List voInclusionList = new ArrayList();
        voInclusionList.add(PayoutVO.class);

        return new VOComposer().composeSegmentVO(contractNumber, voInclusionList);
    }

    private GroupSetupVO getGroupSetupVO(EDITTrxHistoryVO editTrxHistoryVO) {

        return ((GroupSetupVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class).getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(GroupSetupVO.class));
    }

    private GroupSetup getGroupSetup(EDITTrx editTrx) {
        return editTrx.getClientSetup().getContractSetup().getGroupSetup();
    }

    private ClientDetail getClientDetail(CommissionHistory commissionHistory) {
        return commissionHistory.getPlacedAgent().getClientRole().getClientDetail();
    }

    private AccountEffectVO[] processFundLevelElement(Element element,
                                                      ElementVO elementVO,
                                                      ElementStructureVO elementStructureVO,
                                                      String effectiveDate,
                                                      Map drivingCompanyInfo,
                                                      BucketHistoryVO bucketHistoryVO,
                                                      InvestmentHistoryVO investmentHistoryVO,
                                                      String statusInd,
                                                      EDITTrxHistoryVO editTrxHistoryVO,
                                                      EDITTrxVO editTrxVO, 
                                                      EDITBigDecimal debits,
                                                      EDITBigDecimal credits) throws Exception {
        AccountEffectVO[] accountEffectVOs = null;

        //SRAMAM 09/2004 DOUBLE2DECIMAL
        //double faHistDollars = 0;
        EDITBigDecimal faHistDollars = element.getHistoryAmount(bucketHistoryVO, investmentHistoryVO);

        //if (faHistDollars == 0)
        if (faHistDollars.isEQ("0")) {
            return accountEffectVOs;
        } else {
            ElementVO[] candidateElementVOs;
            AccountVO[] accountVOs;
            ElementStructureVO bestMatchElementStructureVO;

            List<AccountEffectVO> accountEffectVOVector = new ArrayList<>();

            candidateElementVOs = getCandidateElementVOs(element.getName(), effectiveDate);

            long investmentFKFromBucket;

            if (bucketHistoryVO == null) {
                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_CAPITALIZATION)) {
                    setEventTypeForLoanCap(elementVO, editTrxVO);
                } else {
                    elementVO.setEventType(investmentHistoryVO.getToFromStatus());
                }

                investmentFKFromBucket = investmentHistoryVO.getInvestmentFK();
            } else {
                setEventTypeFromBucketHistory(elementVO, bucketHistoryVO, editTrxVO);

                BucketVO bucketVO = getBucketVO(bucketHistoryVO);
                investmentFKFromBucket = bucketVO.getInvestmentFK();
            }

            // We can't use the filtered fund from the investment because
            // there is more than one investment that can have a filtered fund associated.
            // If we used that and then the chargecode from the bucket, they
            // sometimes will not match for the KeyMatcher because the bucket's
            // charge code did not go with the investmenthistory's charge code.
            //
            // We need to set these three items now for the
            // KeyMatcher to have the remaining things to
            // match on:
            //  elementStructureVO.setChargeCodeFK(x);  // 0 or otherwise
            //  element.setFilteredFundVO(filteredFundVO);
            //  elementStructureVO.setFundFK(fundFK);
            // First use the bucket to get its investment
            // and then match that investment number against
            // the chargecodes' investment.  If no match
            // then no charge code.
            Map chargeCodeVOsByInvestmentPK = getMapOfChargeCodeVOsByInvestmentPK(editTrxHistoryVO);

            ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCodeVOsByInvestmentPK.get(investmentFKFromBucket);

            long chargeCodeFK;

            if (chargeCodeVO != null) {
                // a charge code was being used
                chargeCodeFK = chargeCodeVO.getChargeCodePK();

                long filteredFundFK = chargeCodeVO.getFilteredFundFK();

                FilteredFundVO filteredFundVO = engine.dm.dao.DAOFactory.getFilteredFundDAO().findByFilteredFundPK(filteredFundFK)[0];

                long fundFK = filteredFundVO.getFundFK();


                elementStructureVO.setChargeCodeFK(chargeCodeFK);
                element.setFilteredFundVO(filteredFundVO);
                elementStructureVO.setFundFK(fundFK);
            } else {
                // no charge code
                InvestmentVO[] investmentVOs = contract.dm.dao.DAOFactory.getInvestmentDAO().findByInvestmentPK(investmentFKFromBucket, false, null);

                InvestmentVO investmentVO = investmentVOs[0];

                long investmentFilteredFundFK = investmentVO.getFilteredFundFK();

                FilteredFundVO filteredFundVO = engine.dm.dao.DAOFactory.getFilteredFundDAO().findByFilteredFundPK(investmentFilteredFundFK)[0];

                long fundFK = filteredFundVO.getFundFK();

                elementStructureVO.setChargeCodeFK(0);
                element.setFilteredFundVO(filteredFundVO);
                elementStructureVO.setFundFK(fundFK);
            }

            bestMatchElementStructureVO = KeyMatcher.getBestMatch(elementVO, drivingCompanyInfo, elementStructureVO, candidateElementVOs, productStructureVOCache);

            String suppressInd = bestMatchElementStructureVO.getSuppressAccountingInd();

            if (!suppressInd.equalsIgnoreCase("Y")) {
                accountVOs = DAOFactory.getAccountDAO().findAccountsByElementStructureId(bestMatchElementStructureVO.getElementStructurePK());

                String switchEffect = bestMatchElementStructureVO.getSwitchEffectInd();

                String effect;

                for (int k = 0; k < accountVOs.length; k++) {
                    effect = accountVOs[k].getEffect();

                    if (switchEffect.equalsIgnoreCase("Y") && (statusInd.equalsIgnoreCase("R") || statusInd.equalsIgnoreCase("U"))) {
                        if (effect.equalsIgnoreCase("Credit")) {
                            effect = "Debit";
                        } else {
                            effect = "Credit";
                        }
                    }

                    if (element.getName().equalsIgnoreCase(Element.OVER_SHORT_AMOUNT)) {
                        if (element.getHistoryAmount(bucketHistoryVO, investmentHistoryVO).isLT("0")) {
                            faHistDollars = element.getHistoryAmount(bucketHistoryVO, investmentHistoryVO);
                            faHistDollars = faHistDollars.negate();
                            if (effect.equalsIgnoreCase("Credit")) {
                                effect = "Debit";
                            } else {
                                effect = "Credit";
                            }
                        }
                    }

                    boolean gain = false;
                    boolean loss = false;
                    boolean negLiab = false;
                    boolean posLiab = false;

                    if (element.getName().equalsIgnoreCase("Gain")) {
                        if (faHistDollars.isGT("0")) {
                            gain = true;
                        }
                    } else if (element.getName().equalsIgnoreCase("Loss")) {
                        if (faHistDollars.isLT("0")) {
                            faHistDollars = faHistDollars.negate();
                            loss = true;
                        }
                    }

                    if (element.getName().equalsIgnoreCase(Element.BUCKET_LIAB_NEG)) {
                        if (faHistDollars.isLT("0")) {
                            faHistDollars = faHistDollars.negate();
                            negLiab = true;
                        }
                    } else if (element.getName().equalsIgnoreCase(Element.BUCKET_LIAB_POS)) {
                        if (faHistDollars.isGT("0")) {
                            posLiab = true;
                        }
                    }
//                    else
//                    {
//                        faHistDollars = element.getHistoryAmount(bucketHistoryVO, investmentHistoryVO);
//                    }

                    if ((element.getName().equalsIgnoreCase(Element.GAIN) && gain) ||
                            (element.getName().equalsIgnoreCase(Element.LOSS) && loss) ||
                            (element.getName().equalsIgnoreCase(Element.BUCKET_LIAB_NEG) && negLiab) ||
                            (element.getName().equalsIgnoreCase(Element.BUCKET_LIAB_POS) && posLiab) ||
                            ((!element.getName().equalsIgnoreCase(Element.GAIN)) &&
                                    (!element.getName().equalsIgnoreCase(Element.LOSS)) &&
                                    (!element.getName().equalsIgnoreCase(Element.BUCKET_LIAB_NEG)) &&
                                    (!element.getName().equalsIgnoreCase(Element.BUCKET_LIAB_POS)))) {
                        AccountEffectVO singleEffect = new AccountEffectVO();
                        singleEffect.setAccountNumber(accountVOs[k].getAccountNumber());
                        singleEffect.setAccountName(accountVOs[k].getAccountName());
                        singleEffect.setAccountEffect(effect);

                        singleEffect.setAccountAmount(faHistDollars.getBigDecimal());

                        if (effect.equalsIgnoreCase("Debit")) {
                            debits.setValue(debits.addEditBigDecimal(faHistDollars));
                        } else {
                            credits.setValue(credits.addEditBigDecimal(faHistDollars));
                        }

                        accountEffectVOVector.add(singleEffect);
                    }
                }

                if (bucketHistoryVO != null) {
                    // Update BucketHistory
                    new event.component.EventComponent().createOrUpdateVO(bucketHistoryVO, false);
                }
            }
            // end (if supppressAccounting)

            if (accountEffectVOVector != null) {
                accountEffectVOs = new AccountEffectVO[accountEffectVOVector.size()];

                for (int v = 0; v < accountEffectVOVector.size(); v++) {
                    accountEffectVOs[v] = (AccountEffectVO) accountEffectVOVector.get(v);
                }
            }

            return accountEffectVOs;
        }
    }


    private void setEventTypeFromBucketHistory(ElementVO elementVO, BucketHistoryVO bucketHistoryVO, EDITTrxVO editTrxVO) {
        String transactionType = editTrxVO.getTransactionTypeCT();
        String elementName = elementVO.getElementName();

        if ((transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_LOAN) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LAPSE)) &&
                (elementName.equalsIgnoreCase(Element.LOAN_PRINCIPAL_DOLLARS) ||
                        elementName.equalsIgnoreCase(Element.LOAN_INTEREST_DOLLARS) ||
                        elementName.equalsIgnoreCase(Element.UNEARNED_INTEREST_CREDIT) ||
                        elementName.equalsIgnoreCase(Element.OVER_SHORT_AMOUNT))) {
            elementVO.setEventType(bucketHistoryVO.getLoanDollarsToFromStatus());

            if (elementVO.getEventType() == null) {
                elementVO.setEventType(bucketHistoryVO.getToFromStatus());
            }
        } else {
            elementVO.setEventType(bucketHistoryVO.getToFromStatus());
        }
    }

    private void setEventTypeForLoanCap(ElementVO elementVO, EDITTrxVO editTrxVO) {
        String status = editTrxVO.getStatus();

        if (elementVO.getElementName().equalsIgnoreCase(Element.LOAN_PRINCIPAL_DOLLARS)) {
            if (status.equalsIgnoreCase(EDITTrx.STATUS_NATURAL) || status.equalsIgnoreCase(EDITTrx.STATUS_APPLY)) {
                elementVO.setEventType(Element.TO_EVENT_TYPE);
            } else {
                elementVO.setEventType(Element.FROM_EVENT_TYPE);
            }
        } else if (elementVO.getElementName().equalsIgnoreCase(Element.LOAN_INTEREST_DOLLARS)) {
            if (status.equalsIgnoreCase(EDITTrx.STATUS_NATURAL) || status.equalsIgnoreCase(EDITTrx.STATUS_APPLY)) {
                elementVO.setEventType(Element.FROM_EVENT_TYPE);
            } else {
                elementVO.setEventType(Element.TO_EVENT_TYPE);
            }
        }
    }

    private void processPEFundLevelElements(SegmentVO segmentVO,
                                            EDITTrxVO editTrxVO,
                                            EDITTrxHistoryVO editTrxHistoryVO,
                                            Map drivingCompanyInfo,
                                            ElementVO elementVO,
                                            ElementStructureVO elementStructureVO,
                                            ContractClientVO contractClientVO,
                                            List accountingDetail,
                                            GroupSetupVO groupSetupVO,
                                            EDITBigDecimal debits,
                                            EDITBigDecimal credits) throws Exception {
        EDITTrxHistoryVO[] peEditTrxHistoryVOs = composeHistoryForPolYearEndAcctg(segmentVO.getSegmentPK(), editTrxVO.getEffectiveDate());

        Hashtable accumValuesHT = new Hashtable<String, EDITBigDecimal[]>();

        EDITBigDecimal accumBonusInterest;
        EDITBigDecimal accumGuarInterest;
        EDITBigDecimal accumExcessInterest;

        if (peEditTrxHistoryVOs != null) {
	        for (int i = 0; i < peEditTrxHistoryVOs.length; i++) {
	            BucketHistoryVO[] bucketHistoryVOs = peEditTrxHistoryVOs[i].getBucketHistoryVO();
	
	            for (int j = 0; j < bucketHistoryVOs.length; j++) {
	                BucketVO bucketVO = getBucketVO(bucketHistoryVOs[j]);
	
	                String investmentFK = bucketVO.getInvestmentFK() + "";
	
	                if (accumValuesHT.containsKey(investmentFK)) {
	                    EDITBigDecimal[] accumValues = (EDITBigDecimal[]) accumValuesHT.get(investmentFK);
	                    accumBonusInterest = accumValues[0];
	                    accumExcessInterest = accumValues[1];
	                    accumGuarInterest = accumValues[2];
	                } else {
	                    accumBonusInterest = new EDITBigDecimal();
	                    accumExcessInterest = new EDITBigDecimal();
	                    accumGuarInterest = new EDITBigDecimal();
	                }
	
	                accumBonusInterest = accumBonusInterest.addEditBigDecimal(Element.getAccumBonusInterestAmount(bucketHistoryVOs, investmentFK));
	
	                EDITBigDecimal guaranteedInterest = Element.getAccumGuarInterestAmount(bucketHistoryVOs, investmentFK);
	                accumGuarInterest = accumGuarInterest.addEditBigDecimal(guaranteedInterest);
	                accumExcessInterest = accumExcessInterest.addEditBigDecimal(Element.getAccumExcessInterestAmount(bucketHistoryVOs, investmentFK, guaranteedInterest));
	
	                EDITBigDecimal[] accumValues = new EDITBigDecimal[3];
	                accumValues[0] = accumBonusInterest;
	                accumValues[1] = accumExcessInterest;
	                accumValues[2] = accumGuarInterest;
	
	                accumValuesHT.put(investmentFK, accumValues);
	            }
	        }
	
	        processPeElement(Element.ACCUM_BONUS_INTEREST, accumValuesHT,
	                elementVO, elementStructureVO, editTrxVO, editTrxHistoryVO,
	                segmentVO, drivingCompanyInfo, contractClientVO, accountingDetail,
	                groupSetupVO, debits, credits);
	        processPeElement(Element.ACCUM_EXCESS_INTEREST, accumValuesHT,
	                elementVO, elementStructureVO, editTrxVO, editTrxHistoryVO,
	                segmentVO, drivingCompanyInfo, contractClientVO, accountingDetail,
	                groupSetupVO, debits, credits);
	        processPeElement(Element.ACCUM_GUAR_INTEREST, accumValuesHT,
	                elementVO, elementStructureVO, editTrxVO, editTrxHistoryVO,
	                segmentVO, drivingCompanyInfo, contractClientVO, accountingDetail,
	                groupSetupVO, debits, credits);
        }
    }

    private void processPeElement(String elementName, Hashtable<String, EDITBigDecimal[]> accumValuesHT, ElementVO elementVO,
                                  ElementStructureVO elementStructureVO, EDITTrxVO editTrxVO,
                                  EDITTrxHistoryVO editTrxHistoryVO, SegmentVO segmentVO,
                                  Map drivingCompanyInfo, ContractClientVO contractClientVO, List accountingDetail,
                                  GroupSetupVO groupSetupVO, 
                                  EDITBigDecimal debits,
                                  EDITBigDecimal credits) throws Exception {
        Element element = new Element(elementName);

        AccountVO[] accountVOs;
        ElementStructureVO bestMatchElementStructureVO;

        List<AccountEffectVO> accountEffectVOVector;

        ElementVO[] candidateElementVOs = getCandidateElementVOs(elementName, editTrxVO.getEffectiveDate());

        Enumeration accumValuesKeys = accumValuesHT.keys();

        while (accumValuesKeys.hasMoreElements()) {
            accountEffectVOVector = new ArrayList<>();

            long investmentFK = Long.parseLong((String) accumValuesKeys.nextElement());

            EDITBigDecimal elementAmount = new EDITBigDecimal();

            EDITBigDecimal[] accumValues = accumValuesHT.get(investmentFK + "");

            switch (elementName) {
                case Element.ACCUM_BONUS_INTEREST:
                    elementAmount = accumValues[0];
                    break;
                case Element.ACCUM_EXCESS_INTEREST:
                    elementAmount = accumValues[1];
                    break;
                default:
                    elementAmount = accumValues[2];
                    break;
            }

            if (elementAmount.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) || elementAmount.isLT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)) {
                InvestmentVO[] investmentVO = new contract.component.LookupComponent().getInvestmentByPK(investmentFK);

                FilteredFundVO[] filteredFundVO = new engine.component.LookupComponent().findFilteredFundByPK(investmentVO[0].getFilteredFundFK(), false, null);

                element.setFilteredFundVO(filteredFundVO[0]);

                elementStructureVO.setFundFK(filteredFundVO[0].getFundFK());

                bestMatchElementStructureVO = KeyMatcher.getBestMatch(elementVO, drivingCompanyInfo, elementStructureVO, candidateElementVOs, productStructureVOCache);

                String suppressInd = bestMatchElementStructureVO.getSuppressAccountingInd();

                if (!suppressInd.equalsIgnoreCase("Y")) {
                    accountVOs = DAOFactory.getAccountDAO().findAccountsByElementStructureId(bestMatchElementStructureVO.getElementStructurePK());

                    String switchEffect = bestMatchElementStructureVO.getSwitchEffectInd();

                    if (accountEffectVOVector == null) {
                        accountEffectVOVector = new ArrayList();
                    }

                    String effect;

                    for (int k = 0; k < accountVOs.length; k++) {
                        effect = accountVOs[k].getEffect();

                        if (switchEffect.equalsIgnoreCase("Y") && (editTrxVO.getStatus().equalsIgnoreCase("R") || editTrxVO.getStatus().equalsIgnoreCase("U"))) {
                            if (effect.equalsIgnoreCase("Credit")) {
                                effect = "Debit";
                            } else {
                                effect = "Credit";
                            }
                        }

                        AccountEffectVO singleEffect = new AccountEffectVO();
                        singleEffect.setAccountNumber(accountVOs[k].getAccountNumber());
                        singleEffect.setAccountName(accountVOs[k].getAccountName());
                        singleEffect.setAccountEffect(effect);
                        singleEffect.setAccountAmount(elementAmount.getBigDecimal());

                        if (effect.equalsIgnoreCase("Debit")) {
                            debits.setValue(debits.addEditBigDecimal(elementAmount));
                        } else {
                            credits.setValue(credits.addEditBigDecimal(elementAmount));
                        }

                        accountEffectVOVector.add(singleEffect);
                    }
                }
                // end (if supppressAccounting)

                int payoutCertainDuration = 0;
                if (accountEffectVOVector != null) {
                    ChargeCodeVO chargeCodeVO = null;
                    AccountEffectVO[] accountEffectVOs = accountEffectVOVector.toArray(new AccountEffectVO[accountEffectVOVector.size()]);
                    updateAccountingDetail(accountEffectVOs, element, editTrxVO,
                            editTrxHistoryVO, segmentVO, elementStructureVO.getMemoCode(),
                            drivingCompanyInfo, contractClientVO, accountingDetail, chargeCodeVO,
                            groupSetupVO.getDistributionCodeCT(), payoutCertainDuration);
                }
            }
            // end (if elementAmount > 0)
        }
        // end while
    }

    private AccountEffectVO[] processNonFundLevelElement(String elementName, EDITBigDecimal elementAmount,
                                                         ElementVO elementVO, ElementStructureVO elementStructureVO,
                                                         String effectiveDate, Map drivingCompanyInfo, String statusInd,
                                                         EDITBigDecimal debits, EDITBigDecimal credits) throws Exception {
        ElementVO[] candidateElementVOs = getCandidateElementVOs(elementName, effectiveDate);

        ElementStructureVO bestMatchElementStructureVO = KeyMatcher.getBestMatch(elementVO, drivingCompanyInfo, elementStructureVO, candidateElementVOs, productStructureVOCache);

        AccountEffectVO[] accountEffectVOs = null;

        if (bestMatchElementStructureVO != null) {
            String suppressInd = bestMatchElementStructureVO.getSuppressAccountingInd();

            if (!suppressInd.equalsIgnoreCase("Y")) {
                AccountVO[] accountVOs = bestMatchElementStructureVO.getAccountVO();

                String switchEffect = bestMatchElementStructureVO.getSwitchEffectInd();

                accountEffectVOs = new AccountEffectVO[accountVOs.length];

                String effect;

                for (int k = 0; k < accountVOs.length; k++) {
                    effect = accountVOs[k].getEffect();

                    if ((switchEffect.equalsIgnoreCase("Y") && (statusInd.equalsIgnoreCase("R") || statusInd.equalsIgnoreCase("U"))) || //SRAMAM 09/2004 DOUBLE2DECIMAL
                            //(elementVO.getElementName().equalsIgnoreCase("MVA") && elementAmount.intValue() < 0))
                            (elementVO.getElementName().equalsIgnoreCase("MVA") && elementAmount.isLT("0"))) {
                        if (effect.equalsIgnoreCase("Credit")) {
                            effect = "Debit";
                        } else {
                            effect = "Credit";
                        }
                    }

                    AccountEffectVO singleEffect = new AccountEffectVO();
                    singleEffect.setAccountNumber(accountVOs[k].getAccountNumber());
                    singleEffect.setAccountName(accountVOs[k].getAccountName());
                    singleEffect.setAccountEffect(effect);
                    singleEffect.setAccountAmount(elementAmount.getBigDecimal());

                    if (effect.equalsIgnoreCase("Debit")) {
                        debits.setValue(debits.addEditBigDecimal(elementAmount));
                    } else {
                        credits.setValue(credits.addEditBigDecimal(elementAmount));
                    }

                    accountEffectVOs[k] = singleEffect;
                }
            }
        } else {
        	String name = Util.initString(elementName, "");
        	String process = "";
        	String event = "";
        	String company = "";
        	
        	if (elementVO != null) {
	        	process = Util.initString(elementVO.getProcess(), "");
	        	event = Util.initString(elementVO.getEvent(), "");
        	}
        	
        	if (drivingCompanyInfo != null) {
        		company = Util.initString((String)drivingCompanyInfo.get("companyName"), "");
        	}
        	
            throw new Exception("NonFundLevelElement could not find Best Match For Element: " + name + ", Process: " + process + ", Event: " + event + ", " + company);
        }

        return accountEffectVOs;
    }

    private ElementVO[] getCandidateElementVOs(String elementName, String effectiveDate) throws Exception {
        ElementVO[] candidateElementVOs;
        candidateElementVOs = DAOFactory.getElementDAO().findElementsByElementNameAndEffDate(elementName, effectiveDate);

        if (candidateElementVOs != null) {
            for (int i = 0; i < candidateElementVOs.length; i++) {
                long elementId = candidateElementVOs[i].getElementPK();

                ElementCompanyRelationVO[] elementCompanyRelationVOs = DAOFactory.getElementCompanyRelationDAO().findRelationsByElementId(elementId);

                if (elementCompanyRelationVOs != null) {
                    candidateElementVOs[i].setElementCompanyRelationVO(elementCompanyRelationVOs);
                }
            }
        } else {
            throw new EDITException("Error: Could not find Elements where ElementName = " + elementName + " and EffectiveDate <= " + effectiveDate);
        }
        return candidateElementVOs;
    }


    private AccountEffectVO[] processSuspenseElement(String elementName,
            EDITBigDecimal amount, ElementVO elementVO, ElementStructureVO elementStructureVO, String effectiveDate, 
            Map drivingCompanyInfo, EDITBigDecimal debits, EDITBigDecimal credits) throws Exception {
        // ElementVO[] candidateElements = getCandidateElementVOs(elementName, effectiveDate);
        AccountVO[] accountsVO;
        ElementStructureVO bestMatch;

        AccountEffectVO[] accountEffectVOs = null;

        ElementVO[] candidateElements = DAOFactory.getElementDAO().findElementsByElementNameAndEffDate(elementName, effectiveDate);

        if (candidateElements != null) {

            bestMatch = KeyMatcher.getBestMatch(elementVO, drivingCompanyInfo, elementStructureVO, candidateElements, productStructureVOCache);

            if (bestMatch != null) {
                String suppressInd = bestMatch.getSuppressAccountingInd();

                if (!suppressInd.equalsIgnoreCase("Y")) {
                    accountsVO = bestMatch.getAccountVO();

                    if (accountsVO == null) {
                        System.out.println("accountsVO is null");
                    } else {
                        accountEffectVOs = new AccountEffectVO[accountsVO.length];

                        String effect;

                        for (int k = 0; k < accountsVO.length; k++) {
                            effect = accountsVO[k].getEffect();

                            AccountEffectVO singleEffect = new AccountEffectVO();
                            singleEffect.setAccountNumber(accountsVO[k].getAccountNumber());
                            singleEffect.setAccountName(accountsVO[k].getAccountName());
                            singleEffect.setAccountEffect(effect);
                            singleEffect.setAccountAmount(amount.getBigDecimal());

                            if (effect.equalsIgnoreCase("Debit")) {
                                debits.setValue(debits.addEditBigDecimal(amount));
                            } else {
                                credits.setValue(credits.addEditBigDecimal(amount));
                            }

                            accountEffectVOs[k] = singleEffect;
                        }
                    }
                }
            } else {
                throw new Exception("Best Match Not Found For Suspense Element Process");
            }
        } else {
            throw new Exception("No Candidate Elements Found For Suspense Element Process");
        }

        return accountEffectVOs;
    }

    private ElementVO buildElementVO(String process, String event, String eventType, String elementName) {
        ElementVO elementVO = new ElementVO();

        elementVO.setProcess(process);
        elementVO.setEvent(event);
        elementVO.setEventType(eventType);
        elementVO.setElementName(elementName);

        return elementVO;
    }

    private ElementStructureVO buildElementStructureVO(String memoCode, int certainPeriod, String qualNonQual) {
        ElementStructureVO elementStructureVO = new ElementStructureVO();

        elementStructureVO.setMemoCode(memoCode);
        elementStructureVO.setCertainPeriod(certainPeriod);
        elementStructureVO.setQualNonQualCT(qualNonQual);

        return elementStructureVO;
    }

    private AccountingDetailVO buildAccountDetailVO(String fundNumber,
                                                    SegmentVO segmentVO,
                                                    String effectiveDate,
                                                    String processDate,
                                                    String memoCode,
                                                    String transactionCode,
                                                    String reversalInd,
                                                    Map drivingCompanyInfo,
                                                    AccountEffectVO accountEffectVO,
                                                    ContractClientVO contractClientVO,
                                                    String userDefNumber,
                                                    String origContractNumber,
                                                    String accountingPeriod,
                                                    long editTrxFK,
                                                    String chargeCode,
                                                    String distributionCodeOverride,
                                                    String distributionCode,
                                                    ClientSetupVO clientSetupVO,
                                                    ReinsuranceHistoryVO reinsuranceHistoryVO,
                                                    int certainDuration,
                                                    String operator,
                                                    BigDecimal amount)
            throws Exception {


        AccountingDetailVO accountingDetailVO = new AccountingDetailVO();

        accountingDetailVO.setCompanyName((String) drivingCompanyInfo.get("companyName"));
        accountingDetailVO.setMarketingPackageName((String) drivingCompanyInfo.get("marketingPackage"));
        accountingDetailVO.setBusinessContractName((String) drivingCompanyInfo.get("businessContract"));

        String contractNumber = "";
        String qualNonQualCT = "";
        String optionCodeCT = "";
        String qualifiedTypeCT = "";

        if (segmentVO != null) {
            contractNumber = segmentVO.getContractNumber();
            qualNonQualCT = segmentVO.getQualNonQualCT();
            optionCodeCT = segmentVO.getOptionCodeCT();
            qualifiedTypeCT = segmentVO.getQualifiedTypeCT();
        } else if (userDefNumber != null) {
            contractNumber = userDefNumber;
        }

        accountingDetailVO.setContractNumber(contractNumber);
        accountingDetailVO.setQualNonQualCT(qualNonQualCT);
        accountingDetailVO.setOptionCodeCT(optionCodeCT);
        accountingDetailVO.setQualifiedTypeCT(qualifiedTypeCT);

        accountingDetailVO.setTransactionCode(transactionCode);
        accountingDetailVO.setReversalInd(reversalInd);

        String stateCT = "";

        ContractClient[] ownerContractClients = null;
        if (segmentVO != null) {
        	Segment segment = new Segment(segmentVO.getSegmentPK());
        	
        	if (segment != null) {
        		ownerContractClients = segment.getActiveContractClients(ClientRole.ROLETYPECT_OWNER);
        	}
        }
        
        if (segmentVO != null && segmentVO.getPremiumTaxSitusOverrideCT() != null) {
            stateCT = segmentVO.getPremiumTaxSitusOverrideCT();
        } else if (ownerContractClients != null) {
            for (int i = 0; i < ownerContractClients.length; i++) {
            	ClientAddress ownclientAddress = ownerContractClients[i].getClientDetail().getPrimaryAddress();
            	try {
                	stateCT = ownclientAddress.getStateCT();
            	} catch (Exception e) {
            		throw new Exception ("Owner is Missing State in Address Record - ClientAddressPK: " + ownclientAddress.getClientAddressPK());
            	}
            }
        }

        accountingDetailVO.setStateCodeCT(stateCT);
        accountingDetailVO.setMemoCode(memoCode);
        accountingDetailVO.setEffectiveDate(effectiveDate);

        accountingDetailVO.setProcessDate(processDate);

        accountingDetailVO.setAccountNumber(accountEffectVO.getAccountNumber());
        accountingDetailVO.setAccountName(accountEffectVO.getAccountName());
        accountingDetailVO.setAmount(Util.roundToNearestCent(new EDITBigDecimal(accountEffectVO.getAccountAmount())).getBigDecimal());
        accountingDetailVO.setDebitCreditInd(accountEffectVO.getAccountEffect());

        accountingDetailVO.setFundNumber(fundNumber);
        accountingDetailVO.setAccountingPendingStatus("N");
        accountingDetailVO.setOriginalContractNumber(origContractNumber);
        accountingDetailVO.setAccountingProcessDate(accountingProcessDate);
        accountingDetailVO.setAccountingPeriod(accountingPeriod);

        if (editTrxFK > 0) {
            accountingDetailVO.setEDITTrxFK(editTrxFK);
        }

        accountingDetailVO.setChargeCode(chargeCode);

        if (distributionCodeOverride != null) {
            accountingDetailVO.setDistributionCodeCT(distributionCodeOverride);
        } else {
            if (distributionCode == null) {
                distributionCode = CodeTableWrapper.getSingleton().getCodeByCodeTableNameAndCodeDesc("DISTRIBUTIONCODE", "Normal Distribution");
            }

            accountingDetailVO.setDistributionCodeCT(distributionCode);
        }

        String reinsurerNumber = null;
        String treatyGroupNumber = null;

        if (clientSetupVO != null) {
            Treaty treaty = Treaty.findBy_TreatyPK(clientSetupVO.getTreatyFK());
            TreatyGroup treatyGroup = TreatyGroup.findBy_TreatyGroupPK(((TreatyVO) treaty.getVO()).getTreatyGroupFK());
            Reinsurer reinsurer = Reinsurer.findBy_ReinsurerPK(((TreatyVO) treaty.getVO()).getReinsurerFK());

        } else if (reinsuranceHistoryVO != null) {
            TreatyVO treatyVO = (TreatyVO) reinsuranceHistoryVO.getParentVO(ContractTreatyVO.class).getParentVO(TreatyVO.class);
            TreatyGroupVO treatyGroupVO = (TreatyGroupVO) treatyVO.getParentVO(TreatyGroupVO.class);
            ReinsurerVO reinsurerVO = (ReinsurerVO) treatyVO.getParentVO(ReinsurerVO.class);

        }

        accountingDetailVO.setCertainDuration(certainDuration);
        accountingDetailVO.setOperator(operator);
        if (contractClientVO != null) {
            accountingDetailVO.setContractClientFK(contractClientVO.getContractClientPK());
        }

        return accountingDetailVO;
    }

    private AccountingDetailVO buildCommissionAccountDetailVO(String fundNumber,
                                                              SegmentVO segmentVO,
                                                              String effectiveDate,
                                                              String processDate,
                                                              String memoCode,
                                                              String transactionCode,
                                                              String reversalInd,
                                                              Map drivingCompanyInfo,
                                                              AccountEffectVO accountEffectVO,
                                                              ClientDetailVO clientDetailVO,
                                                              String accountingPeriod,
                                                              long editTrxFK,
                                                              String distributionCodeOverride,
                                                              String distributionCode,
                                                              int certainDuration,
                                                              long placedAgentFK,
                                                              String operator)
            throws Exception {
        ClientAddressVO clientAddressVO = null;

        if (clientDetailVO != null) {
            clientAddressVO = getPrimaryClientAddressVO(clientDetailVO);
        }

        AccountingDetailVO accountingDetailVO = new AccountingDetailVO();

        accountingDetailVO.setCompanyName((String) drivingCompanyInfo.get("companyName"));
        accountingDetailVO.setMarketingPackageName((String) drivingCompanyInfo.get("marketingPackage"));
        accountingDetailVO.setBusinessContractName((String) drivingCompanyInfo.get("businessContract"));

        String contractNumber = "";
        String qualNonQualCT = "";
        String optionCodeCT = "";
        String qualifiedTypeCT = "";

        if (segmentVO != null) {
            contractNumber = segmentVO.getContractNumber();
            qualNonQualCT = segmentVO.getQualNonQualCT();
            optionCodeCT = segmentVO.getOptionCodeCT();
            qualifiedTypeCT = segmentVO.getQualifiedTypeCT();
        }

        accountingDetailVO.setContractNumber(contractNumber);
        accountingDetailVO.setQualNonQualCT(qualNonQualCT);
        accountingDetailVO.setOptionCodeCT(optionCodeCT);
        accountingDetailVO.setQualifiedTypeCT(qualifiedTypeCT);

        accountingDetailVO.setTransactionCode(transactionCode);
        accountingDetailVO.setReversalInd(reversalInd);

        if (clientAddressVO != null) {
            accountingDetailVO.setStateCodeCT(clientAddressVO.getStateCT());
        } else {
            accountingDetailVO.setStateCodeCT(" ");
        }

        accountingDetailVO.setMemoCode(memoCode);
        accountingDetailVO.setEffectiveDate(effectiveDate);

        accountingDetailVO.setProcessDate(processDate);

        accountingDetailVO.setAccountNumber(accountEffectVO.getAccountNumber());
        accountingDetailVO.setAccountName(accountEffectVO.getAccountName());
        accountingDetailVO.setAmount(Util.roundToNearestCent(accountEffectVO.getAccountAmount()).getBigDecimal());
        accountingDetailVO.setDebitCreditInd(accountEffectVO.getAccountEffect());

        accountingDetailVO.setFundNumber(fundNumber);
        accountingDetailVO.setAccountingPendingStatus("N");
        accountingDetailVO.setAccountingProcessDate(accountingProcessDate);
        accountingDetailVO.setAccountingPeriod(accountingPeriod);
        accountingDetailVO.setEDITTrxFK(editTrxFK);

        if (distributionCodeOverride != null) {
            accountingDetailVO.setDistributionCodeCT(distributionCodeOverride);
        } else {
            if (distributionCode != null) {
                accountingDetailVO.setDistributionCodeCT(distributionCode);
            } else {
                accountingDetailVO.setDistributionCodeCT("7_NormalDistrib");
            }
        }

        accountingDetailVO.setCertainDuration(certainDuration);
        accountingDetailVO.setPlacedAgentFK(placedAgentFK);
        accountingDetailVO.setOperator(operator);

        return accountingDetailVO;
    }


    private ClientAddressVO[] getPrimaryClientAddressVO(ContractClientVO contractClientVO) throws Exception {
        ClientRoleVO clientRoleVO = role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(contractClientVO.getClientRoleFK(), false, null)[0];

        return client.dm.dao.DAOFactory.getClientAddressDAO().findByClientDetailPK_AND_AddressTypeCT(clientRoleVO.getClientDetailFK(), "PrimaryAddress", false, null);
    }

    private ClientAddressVO getPrimaryClientAddressVO(ClientDetailVO clientDetailVO) throws Exception {
        ClientAddressVO[] addressVOs = clientDetailVO.getClientAddressVO();
        ClientAddressVO clientAddressVO = null;

        if (addressVOs != null) {
            for (int a = 0; a < addressVOs.length; a++) {
                if (addressVOs[a].getAddressTypeCT().equalsIgnoreCase("PrimaryAddress")) {
                    clientAddressVO = addressVOs[a];

                    break;
                }
            }
        }

        return clientAddressVO;
    }

    private ContractClientVO getContractClientVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception {
        List voInclusionList = new ArrayList();
        voInclusionList.add(ContractClientVO.class);

        return new VOComposer().composeContractClientVO((ClientSetupVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class).getParentVO(ClientSetupVO.class), voInclusionList);
    }

    private FeeVO[] getFees(String accountingProcessDate) throws Exception {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(FeeDescriptionVO.class);
        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(ProductFilteredFundStructureVO.class);
        voInclusionList.add(ProductStructureVO.class);

        FeeVO[] feeVOs = engineLookup.composeAllFeesByAccountingPendingStatus_Date(voInclusionList, accountingProcessDate);

        if (feeVOs == null) {
            feeVOs = new FeeVO[0];
        }

        return feeVOs;
    }

    private void saveAccountingDetail(List accountingDetail) throws Exception {
        CRUD crud = null;
        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
            for (int i = 0; i < accountingDetail.size(); i++) {
                crud.createOrUpdateVOInDB(accountingDetail.get(i));
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        } finally {
            if (crud != null)
                crud.close();
        }
    }

    private void calculateGainLoss(EDITTrxHistoryVO editTrxHistoryVO) throws Exception {
        
        Hashtable gainLossByFund = new Hashtable();

    	EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);

        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());

        //        EDITDate processDate = new EDITDate(editTrxHistoryVO.getProcessDate());
        EDITDate processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();

        if (effectiveDate.before(processDate)) {
            InvestmentHistoryVO[] investmentHistoryVO = editTrxHistoryVO.getInvestmentHistoryVO();

            if (investmentHistoryVO != null) {
                engine.business.Lookup calcLookup = new engine.component.LookupComponent();

                for (int i = 0; i < investmentHistoryVO.length; i++) {
                    EDITBigDecimal trxUnits = new EDITBigDecimal(investmentHistoryVO[i].getInvestmentUnits());

                    if (!trxUnits.isEQ("0")) {
                        InvestmentVO investmentVO = contract.dm.dao.DAOFactory.getInvestmentDAO().findByInvestmentPK(investmentHistoryVO[i].getInvestmentFK(), false, null)[0];

                        FilteredFundVO[] filteredFundVO = calcLookup.findFilteredFundByPK(investmentVO.getFilteredFundFK(), false, null);

                        // Get the unit values for the filtered fund -- possibly
                        // only those for the historical charge code used.
                        UnitValuesVO[] unitValuesVO = getChargeCodeUnitValues(filteredFundVO[0], editTrxHistoryVO);

                        String pricingDirection = filteredFundVO[0].getPricingDirection();

                        TreeMap unitValuesTM = new TreeMap();

                        if (unitValuesVO != null) {
                            for (int t = 0; t < unitValuesVO.length; t++) {
                                unitValuesTM.put(unitValuesVO[t].getEffectiveDate(), unitValuesVO[t]);
                            }
                        }

                        Iterator it = unitValuesTM.values().iterator();

                        boolean effUVFound = false;
                        boolean procUVFound = false;

                        EDITBigDecimal effectiveValue = new EDITBigDecimal();

                        EDITBigDecimal processValue = new EDITBigDecimal();

                        EDITBigDecimal prevUnitValue = new EDITBigDecimal();

                        while (it.hasNext()) {
                            UnitValuesVO uvVO = (UnitValuesVO) it.next();

                            String uvEffDate = uvVO.getEffectiveDate();

                            edit.common.EDITDate cedUVEff = new edit.common.EDITDate(uvEffDate);

                            if ((cedUVEff.after(effectiveDate) || cedUVEff.equals(effectiveDate)) && !effUVFound) {
                                if (pricingDirection.equalsIgnoreCase("Forward")) {
                                    effectiveValue = trxUnits.multiplyEditBigDecimal(new EDITBigDecimal(uvVO.getUnitValue() + ""));
                                } else {
                                    if (cedUVEff.after(effectiveDate)) {
                                        effectiveValue = trxUnits.multiplyEditBigDecimal(prevUnitValue);
                                    } else {
                                        effectiveValue = trxUnits.multiplyEditBigDecimal(uvVO.getUnitValue());
                                    }
                                }

                                effUVFound = true;
                            }

                            if ((cedUVEff.after(processDate) || cedUVEff.equals(processDate)) && !procUVFound) {
                                processValue = trxUnits.multiplyEditBigDecimal(uvVO.getUnitValue());
                                procUVFound = true;
                            }

                            if (!effUVFound) {
                                prevUnitValue = new EDITBigDecimal(uvVO.getUnitValue());
                            }
                        }

                        EDITBigDecimal gainLoss = effectiveValue.subtractEditBigDecimal(processValue);

                        investmentHistoryVO[i].setGainLoss(gainLoss.getBigDecimal());

                        if (!gainLoss.isEQ("0")) {
                            String filteredFundFK = filteredFundVO[0].getFilteredFundPK() + "";
                            String chargeCodeFK = investmentHistoryVO[i].getChargeCodeFK() + "";
                            String key = filteredFundFK + "_" + chargeCodeFK;

                            if (gainLossByFund.containsKey(key)) {
                                EDITBigDecimal fundGainLoss = (EDITBigDecimal) gainLossByFund.get(key);
                                fundGainLoss = fundGainLoss.addEditBigDecimal(gainLoss);
                                gainLossByFund.put(key, fundGainLoss);
                            } else {
                                gainLossByFund.put(key, gainLoss);
                            }
                        }

                        new event.component.EventComponent().createOrUpdateVO(investmentHistoryVO[i], false);
                    }
                }
            }
        }
    }

    /**
     * For a given filtered fund and EDITTrxHistory, return back the
     * UnitValueVO's.  If the filtered fund is not using ChargeCodes,
     * then this will just return back all of the unit values.  If the
     * filtered fund is using change codes, then it will filter out
     * the list of all unit values so that they match the charge code
     * that was used in in the EDITTrxHsitory's investment history's
     * change code.
     *
     * @param filteredFundVO
     * @param editTrxHistoryVO
     * @return
     */
    private UnitValuesVO[] getChargeCodeUnitValues(FilteredFundVO filteredFundVO, EDITTrxHistoryVO editTrxHistoryVO) {
        long filteredFundPK = filteredFundVO.getFilteredFundPK();
        UnitValuesVO[] unitValuesVOs = engine.dm.dao.DAOFactory.getUnitValuesDAO().findUnitValuesByFilteredFundId(filteredFundPK);

        if (!"Y".equals(filteredFundVO.getChargeCodeIndicator())) {
            return unitValuesVOs; // all done
        }

        // GET EDITTRXHISTORY'S INVESTMENTHISTORIES
        InvestmentHistory[] investmentHistories = InvestmentHistory.findAllByEDITTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());

        ChargeCodeVO targetChargeCodeVO = null;

        long targetFilteredFundFK = filteredFundVO.getFilteredFundPK();

        for (int i = 0; i < investmentHistories.length; i++) {
            InvestmentHistory investmentHistory = investmentHistories[i];

            InvestmentHistoryVO investmentHistoryVO = (InvestmentHistoryVO) investmentHistory.getVO();

            long chargeCodeFK = investmentHistoryVO.getChargeCodeFK();

            if (chargeCodeFK == 0) {
                continue;
            }

            ChargeCodeVO chargeCodeVO = calculatorComponent.findChargeCodeBy_ChargeCodePK(chargeCodeFK);

            if (targetFilteredFundFK == chargeCodeVO.getFilteredFundFK()) {
                // found it.  This charge code was used
                targetChargeCodeVO = chargeCodeVO;

                break;
            }
        }

        if (targetChargeCodeVO == null) {
            return unitValuesVOs;
        }

        // NOW HAVE THE CHARGE CODE - FILTER THE UNIT VALUES FOR IT
        long chargeCodePK = targetChargeCodeVO.getChargeCodePK();

        List aList = new ArrayList();

        for (int i = 0; i < unitValuesVOs.length; i++) {
            UnitValuesVO unitValuesVO = unitValuesVOs[i];

            if (unitValuesVO.getChargeCodeFK() == chargeCodePK) {
                aList.add(unitValuesVO);
            }
        }

        return (UnitValuesVO[]) aList.toArray(new UnitValuesVO[aList.size()]);
    }

    /**
     * Get the BucketVO from a BucketHistoryVO.  This tries to use the
     * getParent method but if the Bucket is not present, then it returns
     * it from the database.
     *
     * @param bucketHistoryVO
     * @return
     */
    private BucketVO getBucketVO(BucketHistoryVO bucketHistoryVO) {
        try {
            BucketVO bucketVO = (BucketVO) bucketHistoryVO.getParentVO(BucketVO.class);

            if (bucketVO == null) {
                // not present in the BucketHistory - go get it
                long bucketPK = bucketHistoryVO.getBucketFK();
                List voInclusionList = new ArrayList();
                bucketVO = new BucketComposer(voInclusionList).compose(bucketPK);
            }

            return bucketVO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("problem getting BucketVO for PK = " + bucketHistoryVO.getBucketFK());
        }
    }

    /**
     * For a given editTrxHistoryVO, return Map of all of the ChargeCodeVOs
     * for its investment histories, each keyed by investmentPK.
     * This is a specialized lookup so not moved elsewhere.
     *
     * @param editTrxHistoryVO
     * @return Map of ChargeCodeVO's keyed by the investment PKs (Long)
     */
    private Map getMapOfChargeCodeVOsByInvestmentPK(EDITTrxHistoryVO editTrxHistoryVO) {
        long editTrxHistoryPK = editTrxHistoryVO.getEDITTrxHistoryPK();

        InvestmentHistory[] investmentHistories = InvestmentHistory.findAllByEDITTrxHistoryPK(editTrxHistoryPK);

        Map chargeCodeVOsByInvestmentPK = new HashMap();

        if (investmentHistories == null) {
            return chargeCodeVOsByInvestmentPK;
        }

        for (int i = 0; i < investmentHistories.length; i++) {
            InvestmentHistory investmentHistory = investmentHistories[i];
            InvestmentHistoryVO investmentHistoryVO = (InvestmentHistoryVO) investmentHistory.getVO();

            long chargeCodeFK = investmentHistoryVO.getChargeCodeFK();
            long investmentFK = investmentHistoryVO.getInvestmentFK();

            if (chargeCodeFK == 0) {
                continue;
            }

            ChargeCodeVO chargeCodeVO = calculatorComponent.findChargeCodeBy_ChargeCodePK(chargeCodeFK);

            chargeCodeVOsByInvestmentPK.put(investmentFK, chargeCodeVO);
        }

        return chargeCodeVOsByInvestmentPK;
    }

    /**
     * For a given buckethistory, get the ChargeCodeVO associated with it.
     * Its parent bucket has the investmentPK and we match that against
     * the chargecodes referenced in the edittrxhistory.
     *
     * @param bucketHistoryVO
     * @param editTrxHistoryVO
     * @return
     */
    private ChargeCodeVO getHistoricalChargeCode(BucketHistoryVO bucketHistoryVO, EDITTrxHistoryVO editTrxHistoryVO) {
        Map chargeCodeVOsByInvestmentPK = getMapOfChargeCodeVOsByInvestmentPK(editTrxHistoryVO);

        BucketVO bucketVO = getBucketVO(bucketHistoryVO);

        long investmentFKFromBucket = bucketVO.getInvestmentFK();

        return (ChargeCodeVO) chargeCodeVOsByInvestmentPK.get(new Long(investmentFKFromBucket));
    }

    /**
     * For a given investmenthistory, get the ChargeCodeVO associated with it.
     *
     * @param investmentHistoryVO
     * @return
     */
    private ChargeCodeVO getHistoricalChargeCode(InvestmentHistoryVO investmentHistoryVO) {
        return calculatorComponent.findChargeCodeBy_ChargeCodePK(investmentHistoryVO.getChargeCodeFK());
    }

    private int calculateDuration(String contractEffDate, String trxEffDate) {
        EDITDate sed = new EDITDate(contractEffDate);
        EDITDate ted = new EDITDate(trxEffDate);
        String duration = ((sed.getElapsedDays(ted) / 365) + 1) + "";
        int index = duration.indexOf(".");

        if (index > 0) {
            duration = duration.substring(0, index);
        }

        return Integer.parseInt(duration);
    }

    private int calculateDuration(EDITDate contractEffDate, EDITDate trxEffDate) {
        String duration = ((contractEffDate.getElapsedDays(trxEffDate) / 365) + 1) + "";
        int index = duration.indexOf(".");

        if (index > 0) {
            duration = duration.substring(0, index);
        }

        return Integer.parseInt(duration);
    }

    private File getExportFile() {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        return new File(export1.getDirectory() + "SEGGL_" + System.currentTimeMillis() + ".xml");
    }

    private void exportAccounting(AccountingDetailVO accountingDetailVO) throws Exception {
        String parsedXML = roundDollarFields(accountingDetailVO);

        parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);

        appendToFile(exportFile, parsedXML);
    }

    private String roundDollarFields(AccountingDetailVO accountingDetailVO) throws Exception {
        String[] fieldNames = setupFieldNamesForRounding();

        return Util.roundDollarTextFields(accountingDetailVO, fieldNames);
    }

    private void appendToFile(File exportFile, String data) throws Exception {
        ThreadSafeFileWriter safeFileWriter = ThreadSafeFileWriter.getInstance();
        safeFileWriter.writeToFile(exportFile.getPath(), data);


//        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));
//        bw.write(data);
//        bw.flush();
//        bw.close();
    }

    private void insertStartAccounting(File exportFile) throws Exception {
        appendToFile(exportFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        appendToFile(exportFile, "<AccountingExtractVO>\n");
    }

    private void insertEndAccounting(File exportFile) throws Exception {
        appendToFile(exportFile, "\n</AccountingExtractVO>");
    }

    private String[] setupFieldNamesForRounding() {
        List fieldNames = new ArrayList();

        fieldNames.add("AccountingDetailVO.Amount");

        return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }

    private void createDFACCForGainLoss(Hashtable gainLossByFund) {
        Set keys = gainLossByFund.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            long filteredFundFK = Long.parseLong(key.substring(0, key.indexOf("_")));
            String chargeCodeFK = key.substring(key.indexOf("_") + 1);

            FeeDescriptionVO feeDescriptionVO = engineLookup.findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(filteredFundFK, GAIN_LOSS_FEE);

            long feeDescriptionPK = 0;

            if (feeDescriptionVO != null) {
                feeDescriptionPK = feeDescriptionVO.getFeeDescriptionPK();
            }

            EDITBigDecimal gainLoss = (EDITBigDecimal) gainLossByFund.get(key);

            FeeVO feeVO = new FeeVO();
            feeVO.setFeePK(0);
            feeVO.setFilteredFundFK(filteredFundFK);
            feeVO.setFeeDescriptionFK(feeDescriptionPK);
            feeVO.setEffectiveDate(accountingProcessDate);
            feeVO.setProcessDateTime(accountingProcessDate);
            feeVO.setOriginalProcessDate(null);
            feeVO.setReleaseInd("Y");
            feeVO.setReleaseDate(accountingProcessDate);
            feeVO.setStatusCT("N");
            feeVO.setAccountingPendingStatus("Y");
            feeVO.setTrxAmount(gainLoss.getBigDecimal());
            feeVO.setTransactionTypeCT("DFACC");
            feeVO.setContractNumber(null);
            feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            feeVO.setOperator("System");
            feeVO.setChargeCodeFK(Long.parseLong(chargeCodeFK));

            Fee fee = new Fee(feeVO);

            try {
                fee.save();
            } catch (EDITEngineException e) {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES).updateFailure();

                System.out.println(e);

                e.printStackTrace();
            }
        }
    }

    private boolean checkForHedgeFundTrx(String transactionTypeCT) {
        boolean isHedgeFundTrx = false;

        for (int i = 0; i < EDITTrx.hedgeFundTrxTypes.length; i++) {
            if (transactionTypeCT.equalsIgnoreCase(EDITTrx.hedgeFundTrxTypes[i])) {
                isHedgeFundTrx = true;
                break;
            }
        }

        return isHedgeFundTrx;
    }
}
