/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.batch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import logging.LogEvent;

import org.apache.logging.log4j.Logger;

import role.ClientRole;
import staging.CorrespondenceStaging;
import staging.Staging;
import staging.StagingContext;
import batch.business.Batch;
import client.ClientDetail;
import codetable.component.CodeTableComponent;
import contract.ContractClient;
import contract.Segment;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.vo.ActivitySummaryVO;
import edit.common.vo.BaseSegmentVO;
import edit.common.vo.BucketVO;
import edit.common.vo.CalculatedValuesVO;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.ClientVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.CorrespondenceDetailVO;
import edit.common.vo.EDITExport;
import edit.common.vo.EDITTrxCorrespondenceVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.GIOOptionValueVO;
import edit.common.vo.InvestmentRateVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.NaturalDocVO;
import edit.common.vo.NoteReminderVO;
import edit.common.vo.PreferenceVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.ProjectionSurrenderChargeVO;
import edit.common.vo.ProjectionsVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.TransactionCorrespondenceVO;
import edit.common.vo.VOObject;
import edit.services.EditServiceLocator;
import edit.services.config.ServicesConfig;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.RecursionContext;
import edit.services.db.RecursionListener;
import edit.services.db.VOClass;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import engine.business.Calculator;
import engine.component.CalculatorComponent;
import engine.sp.SPOutput;
import event.BucketHistory;
import event.ChargeHistory;
import event.EDITTrx;
import event.EDITTrxCorrespondence;
import event.EDITTrxHistory;
import event.FinancialHistory;
import event.TransactionCorrespondence;
import event.TransactionPriority;
import event.dm.composer.VOComposer;
import event.dm.dao.DAOFactory;
import fission.utility.Util;
import fission.utility.XMLUtil;

public class CorrespondenceProcessor implements Serializable, RecursionListener {
	private static final String SEPERATOR_FOR_PRIMARY_OWNER_AND_SECONDARY_OWNER = "&";
	private static final long MAX_FILE_SIZE = 50000 * 1024; // 50,000 Meg files
	private static final int NUM_BACKUPS = 10; // Basically limitless
	private boolean produceXML;

	public CorrespondenceProcessor() {
		super();
	}

	public void createCorrespondenceExtracts(String companyName) {
		EditServiceLocator
		.getSingleton()
		.getBatchAgent()
		.getBatchStat(Batch.BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS)
		.tagBatchStart(Batch.BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS,
				"Correspondence Extract");

		ProductStructureVO[] productStructureVOs = null;// getProductStructures(companyName);
		// not even being used
		// right now.

		try {
			processRequestForAllCompanies();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace(); 

			LogEvent logEvent = new LogEvent("Correspondence Errored", e);

			Logger logger = Logging.getLogger(Logging.BATCH_JOB);

			logger.error(logEvent);

		} finally {
			EditServiceLocator
			.getSingleton()
			.getBatchAgent()
			.getBatchStat(
					Batch.BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS)
					.tagBatchStop();
		}
	}

	/**
	 * The set of ProductStructureVOs relating to the specified companyName. All
	 * ProductStructureVOs are returned if the company name is "All".
	 * 
	 * @param companyName
	 * @return
	 */
	private ProductStructureVO[] getProductStructures(String companyName) {
		ProductStructureVO[] productStructureVOs = null;

		engine.business.Lookup engineLookup = new engine.component.LookupComponent();

		if (companyName.equalsIgnoreCase("All")) {
			productStructureVOs = engineLookup.getAllProductStructures();
		} else {
			productStructureVOs = engineLookup
					.getAllProductStructuresByCoName(companyName);
		}

		return productStructureVOs;
	}

	/**
	 * The EDITTrxCorrespondence records that satisfy the requested date and
	 * have a Status = "P", become a part of the correspondence extract. First
	 * the keys are retrieved, then fluffy VOs are built to produce the extract.
	 * 
	 * @throws Exception
	 */
	private void processRequestForAllCompanies() throws Exception {

		produceXML = false; 
		
		final File exportFile = getExportFile();

		if (produceXML)
		{
			insertStartCorrespondence(exportFile);
		}
		
		final EDITDate currentDate = new EDITDate();
		final EDITDateTime stagingDate = new EDITDateTime();
		
		String eventType = "Correspondence";
		String database = SessionHelper.STAGING;
		
		String[] correspondenceTypes = event.dm.dao.DAOFactory.getTransactionCorrespondenceDAO().findAllCorrespondenceTypes();
		
		if(correspondenceTypes != null)
		{
			for (String correspondenceType : correspondenceTypes)
			{
				SessionHelper.beginTransaction(database);
				
				StagingContext stagingContext = new StagingContext(eventType, stagingDate, correspondenceType);
				
				Staging staging = new Staging();
				staging.stage(stagingContext, database);
			}
			
			SessionHelper.commitTransaction(database);
		}		
				
		long[] editTrxCorrespondencePKs = EDITTrxCorrespondence
				.findEDITTrxCorrespondenceByStatus_And_PriorToCorrespondenceDate(
						EDITTrxCorrespondence.STATUS_PENDING, currentDate);

		if (editTrxCorrespondencePKs.length == 0) {
			if (produceXML)
			{
				insertEndCorrespondence(exportFile);
			}
			throw new Exception("No Correspondence Records To Process");
		}

		final CRUD crud = CRUDFactory.getSingleton().getCRUD(
				ConnectionFactory.EDITSOLUTIONS_POOL);
		
		int nThreads = (Runtime.getRuntime().availableProcessors() > 1) ? (Runtime.getRuntime().availableProcessors() - 1) : 1;

		ExecutorService executor = Executors.newFixedThreadPool(nThreads);

		List<Future> futures = new ArrayList<Future>();

		try {
			//Process correspondence with executorService
			final int totalEditTrxCorrespondence = editTrxCorrespondencePKs.length;
			for (int i = 0; i < totalEditTrxCorrespondence; i++) {

				final long editTrxCorrPK = editTrxCorrespondencePKs[i];
				final int index = i;
				
				Future<?> future = executor.submit(new Runnable() {
					public void run() {
						processCorrespondence(editTrxCorrPK, currentDate, stagingDate, crud, exportFile, index, totalEditTrxCorrespondence);
					} 
				});

				futures.add(future);				
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

		} finally {
			if (crud != null) {
				crud.close();
			}

			if (executor != null) {
				executor.shutdownNow();
				executor = null;
			}
		}

		// Update Staging.RecordCount for each Correspondence record		
		Staging staging = null;
		
		List<Staging> corrStagingRecords = Staging.findStagingByEventType("Correspondence");
		Iterator<Staging> stagingIt = corrStagingRecords.iterator();
		
		SessionHelper.beginTransaction(database);
		
		// Set Staging record counts
		while (stagingIt.hasNext()) {
			staging = stagingIt.next();
			long stagingPK = staging.getStagingPK();
			int recordCount = (int) Staging.getCount_AttachedSegmentBaseRecords(stagingPK);
			staging.setRecordCount(recordCount);

			SessionHelper.saveOrUpdate(staging, database);
		}

		SessionHelper.commitTransaction(database);

		SessionHelper.clearSession(database);
		
		if (produceXML)
		{
			insertEndCorrespondence(exportFile);
		}
	}

	private void processCorrespondence (long editTrxCorrespondencePK, EDITDate currentDate, EDITDateTime stagingDate, CRUD crud, File exportFile, int index, int totalTrx) {
		
		EDITTrxCorrespondence editTrxCorrespondence = null;

		TransactionCorrespondence transactionCorrespondence = null;
		
		try {
			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS).setBatchMessage(
	    			"Process Correspondence (Threaded) - EDITTrxCorrespondence " + (index+1) + " of " + totalTrx);

			long saveProductKey = 0;

			editTrxCorrespondence = EDITTrxCorrespondence
					.findByPK(new Long(editTrxCorrespondencePK));

			transactionCorrespondence = TransactionCorrespondence
					.findByPK(editTrxCorrespondence
							.getTransactionCorrespondenceFK());


			boolean includeHistory = getIncludeHistoryIndicator(transactionCorrespondence);

			ActivitySummaryVO activitySummaryVO = new ActivitySummaryVO();

			NaturalDocVO naturalDocVO = getNaturalDocDataPlus(
					editTrxCorrespondence, activitySummaryVO,
					includeHistory);

			boolean shouldProcessCorrespondence = true;

			if (transactionCorrespondence.getCorrespondenceTypeCT()
					.equalsIgnoreCase("LPNotice")) {
				if (!naturalDocVO
						.getBaseSegmentVO()
						.getSegmentVO()
						.getSegmentStatusCT()
						.equalsIgnoreCase(
								Segment.SEGMENTSTATUSCT_LAPSEPENDING)) {
					shouldProcessCorrespondence = false;
				}
			} else if (transactionCorrespondence.getCorrespondenceTypeCT().equalsIgnoreCase("Stmt") && 
					naturalDocVO.getBaseSegmentVO().getSegmentVO().getSegmentStatusCT(
							).equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATHPENDING) &&
					naturalDocVO.getBaseSegmentVO().getSegmentVO().getSegmentNameCT()
							.equalsIgnoreCase(Segment.SEGMENTNAMECT_UL)) {
				
				shouldProcessCorrespondence = false;
			}

			// Keep Confirm and LPNotice separate for the time being per
			// Shawn. 9-10-15
			boolean changeStatusToHistory = true;
			if (transactionCorrespondence.getCorrespondenceTypeCT()
					.equalsIgnoreCase("Confirm")) {
				// transactionCorrespondence.getCorrespondenceTypeCT().equalsIgnoreCase("LPNotice"))
				// {

				if (!naturalDocVO.getBaseSegmentVO().getSegmentVO()
						.getSegmentStatusCT()
						.equalsIgnoreCase("LapsePending")
						&& !naturalDocVO.getBaseSegmentVO()
						.getSegmentVO().getSegmentStatusCT()
						.equalsIgnoreCase("Active")
						&& !naturalDocVO.getBaseSegmentVO()
						.getSegmentVO().getSegmentStatusCT()
						.equalsIgnoreCase("Transition")) {
					shouldProcessCorrespondence = false;
					changeStatusToHistory = false;
				}
			}

			if (shouldProcessCorrespondence) {
				ProductStructureVO productStructureVO = null;

				if (naturalDocVO.getBaseSegmentVO() != null) {
					long productKey = naturalDocVO.getBaseSegmentVO()
							.getSegmentVO().getProductStructureFK();

					if (productKey != saveProductKey) {
						productStructureVO = getProductStructure(productKey);
						saveProductKey = productKey;
					}

					CorrespondenceDetailVO correspondenceDetailVO = new CorrespondenceDetailVO();
					
					correspondenceDetailVO.setCorrespondenceDetailPK(0);
					naturalDocVO.setNaturalDocPK(1);
					activitySummaryVO.setActivitySummaryPK(2);

					correspondenceDetailVO
					.addNaturalDocVO(naturalDocVO);
					correspondenceDetailVO
					.addActivitySummaryVO(activitySummaryVO);
					correspondenceDetailVO
					.setCorrespondenceType(transactionCorrespondence
							.getCorrespondenceTypeCT());
					correspondenceDetailVO
					.addProductStructureVO(productStructureVO);

					// All updates vo's will be in voObjects
					VOObject[] voObjects = null;
					Calculator calcComponent = new CalculatorComponent();

					SPOutput spOutput = calcComponent.processScript(
							"CorrespondenceDetailVO",
							correspondenceDetailVO, "Correspondence",
							"*", "*", currentDate.getFormattedDate(),
							productKey, true);

					voObjects = spOutput.getSPOutputVO().getVOObject();

					// Update into the original VO using recursion
					Map vosByPK = new HashMap();

					if (voObjects != null) {
						for (int j = 0; j < voObjects.length; j++) {
							VOObject voObject = (VOObject) voObjects[j];

							if (voObject instanceof CorrespondenceDetailVO) {
								vosByPK.put("correspondenceDetailVO",
										voObject);
							} else if (voObject instanceof ProjectionsVO
									|| voObject instanceof InvestmentRateVO
									|| voObject instanceof ProjectionSurrenderChargeVO
									|| voObject instanceof BucketVO
									|| voObject instanceof CalculatedValuesVO
									|| voObject instanceof GIOOptionValueVO) {
								; // temp change to process until
								// rework, remove these from
								// recursion update after recursion
							} else if (voObject instanceof EDITTrxCorrespondenceVO) {
								updateCorrespondence(
										(EDITTrxCorrespondenceVO) voObject,
										crud);
							} else {
								VOClass voClass = VOClass
										.getVOClassMetaData(voObject
												.getClass());

								Long pk = (Long) voClass.getPKGetter()
										.getMethod()
										.invoke(voObject, null);

								vosByPK.put(pk.toString(), voObject);
							}
						}
					}

					if (!vosByPK.isEmpty()) {
						RecursionContext recursionContext = new RecursionContext();

						recursionContext
						.addToMemory("vosByPK", vosByPK);

						if (voObjects != null) {
							updateToVOModel(voObjects,
									correspondenceDetailVO);
						}

						updatePreferenceInCorrespondenceDetailVO(correspondenceDetailVO);

						if (produceXML)
						{
							exportCorrespondence(correspondenceDetailVO, exportFile);
						}
						
						boolean isStaged = false; 
						
						CorrespondenceStaging correspondenceStaging = new CorrespondenceStaging(stagingDate);
						isStaged = correspondenceStaging.stageTables(correspondenceDetailVO);
						
						if (isStaged)
						{
							SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
							editTrxCorrespondence.setStatus(EDITTrxCorrespondence.STATUS_HISTORY);
							SessionHelper.saveOrUpdate(editTrxCorrespondence, SessionHelper.EDITSOLUTIONS);
						}
						
						SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
						
						editTrxCorrespondence = null;
						activitySummaryVO = null;
						naturalDocVO = null;
						correspondenceDetailVO = null;
						voObjects = null;

						EditServiceLocator
						.getSingleton()
						.getBatchAgent()
						.getBatchStat(
								Batch.BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS)
								.updateSuccess();
					}
				}
			} else if (changeStatusToHistory) {
				SessionHelper
				.beginTransaction(SessionHelper.EDITSOLUTIONS);
				editTrxCorrespondence
				.setStatus(EDITTrxCorrespondence.STATUS_HISTORY);
				SessionHelper.saveOrUpdate(editTrxCorrespondence,
						SessionHelper.EDITSOLUTIONS);
				SessionHelper
				.commitTransaction(SessionHelper.EDITSOLUTIONS);
			}
		} catch (Exception e) {
			EditServiceLocator
			.getSingleton()
			.getBatchAgent()
			.getBatchStat(
					Batch.BATCH_JOB_CREATE_CORRESPONDENCE_EXTRACTS)
					.updateFailure();

			SessionHelper
			.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
			// exportFile.delete();

			System.out.println(e);

			e.printStackTrace(); 

			// Log error to database
			logErrorToDatabase(e, editTrxCorrespondence,
					transactionCorrespondence);

		}

		SessionHelper.clearSessions();
	}

	private void logErrorToDatabase(Exception e,
			EDITTrxCorrespondence editTrxCorrespondence,
			TransactionCorrespondence transactionCorrespondence) {
		EDITTrx editTrx = EDITTrx
				.findByPK(editTrxCorrespondence.getEDITTrxFK());

		Segment segment = editTrx.getClientSetup().getContractSetup()
				.getSegment();

		EDITMap columnInfo = new EDITMap("ContractNumber",
				segment.getContractNumber());
		columnInfo.put("CorrespondenceType",
				transactionCorrespondence.getCorrespondenceTypeCT());
		columnInfo.put("ProcessDate", new EDITDate().getFormattedDate());

		logging.Log.logToDatabase(logging.Log.CORRESPONDENCE,
				"Correspondence Errored: " + e.getMessage(), columnInfo);
	}

	private void updateCorrespondence(
			EDITTrxCorrespondenceVO editTrxCorrespondenceVO, CRUD crud)
					throws Exception {
		if (!editTrxCorrespondenceVO.getStatus().equalsIgnoreCase("T")) {
			try {
				crud.startTransaction();

				EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence(
						editTrxCorrespondenceVO);

				editTrxCorrespondence.save(crud);

				crud.commitTransaction();
			} catch (Exception e) {
				crud.rollbackTransaction();

				System.out.println(e);

				e.printStackTrace();

				throw e;
			}
		}
	}

	private ProductStructureVO getProductStructure(long productKey)
			throws Exception {
		engine.business.Lookup engineLookup = new engine.component.LookupComponent();

		ProductStructureVO productStructureVO = engineLookup
				.findProductStructureVOByPK(productKey, false, new ArrayList())[0];

		return productStructureVO;
	}

	private NaturalDocVO getNaturalDocDataPlus(
			EDITTrxCorrespondence editTrxCorrespondence,
			ActivitySummaryVO activitySummaryVO, boolean includeHistory)
					throws Exception {

		NaturalDocVO naturalDocVO = null;
		CodeTableComponent codeTableComponent = new CodeTableComponent();

		EDITTrx editTrx = EDITTrx
				.findByPK(editTrxCorrespondence.getEDITTrxFK());

		if (includeHistory) {
			List voInclusionList = new ArrayList();
			voInclusionList.add(EDITTrxVO.class);
			voInclusionList.add(ClientSetupVO.class);
			voInclusionList.add(ContractSetupVO.class);
			// EDITTrxVO editTrxVO = new
			// VOComposer().composeEDITTrxVOByEDITTrxPK(editTrxCorrespondenceVO.getEDITTrxFK(),
			// voInclusionList);
			// long segmentPK = ((ContractSetupVO)
			// (editTrxVO.getParentVO(ClientSetupVO.class)).getParentVO(ContractSetupVO.class)).getSegmentFK();
			Segment segment = editTrx.getClientSetup().getContractSetup()
					.getSegment();

			long segmentPK = segment.getSegmentPK().longValue();
			String trxType = editTrx.getTransactionTypeCT();

			EDITDate endingEffDate = new EDITDate(editTrx.getEffectiveDate());
			EDITDate startingEffDate = endingEffDate.subtractYears(1);
			startingEffDate = startingEffDate.addDays(1); // One day is added so we are not including this same date last year

			EDITTrxVO[] editTrxVOs = new VOComposer()
			.composeEDITTrxVOBySegmentPK(segmentPK, voInclusionList);

			// At this point we need both type of access from db. Because the
			// building of natural document depends on
			// composed edittrxVO. Hibernate deals with all entities. When we
			// rebuild documents using hibernate
			// we do not need composers.
			EDITTrx[] editTrxs = EDITTrx.findBySegment_And_PendingStatus(
					segment, new String[] { "H" });

			populateActivitySummaryVO(editTrxs, activitySummaryVO,
					endingEffDate.getFormattedDate(), trxType);

			if (editTrxVOs.length != 0) {
				naturalDocVO = codeTableComponent.buildNaturalDocWithHistory(
						trxType, startingEffDate.getFormattedDate(),
						endingEffDate.getFormattedDate(), editTrxVOs, editTrxCorrespondence);
			}
		} else {
			EDITTrxVO editTrxVO = DAOFactory.getEDITTrxDAO().findByEDITTrxPK(
					editTrxCorrespondence.getEDITTrxFK().longValue())[0];

			naturalDocVO = codeTableComponent
					.buildNaturalDocWithoutHistory(editTrxVO);
		}

		getAdditionalData(naturalDocVO);

		String corrAddressType = editTrxCorrespondence.getAddressTypeCT();
		if (corrAddressType == null || corrAddressType.equals("")) {
			corrAddressType = "PrimaryAddress";
		}

		if (naturalDocVO.getBaseSegmentVO() != null) {
			filterClientAddressByCTTypeAndTerminationDate(naturalDocVO
					.getBaseSegmentVO().getClientVO(), corrAddressType,
					new EDITDate(), editTrxCorrespondence
					.getEDITTrxCorrespondencePK().longValue());
		}

		return naturalDocVO;
	}

	private void filterClientAddressByCTTypeAndTerminationDate(
			ClientVO[] clientVO, String addressTypeCT, EDITDate currentDate,
			long editTrxCorrespondencePK) throws Exception {
		try {
			for (int i = 0; i < clientVO.length; i++) {
				ClientDetailVO[] clientDetailVO = clientVO[i]
						.getClientDetailVO();

				for (int j = 0; j < clientDetailVO.length; j++) {
					ClientAddressVO[] clientAddressVO = clientDetailVO[j]
							.getClientAddressVO();

					if (clientAddressVO != null) {
						clientDetailVO[j].removeAllClientAddressVO();

						ClientAddressVO clientCorrAddress = null;

						if (addressTypeCT.equalsIgnoreCase("PrimaryAddress")
								|| addressTypeCT
								.equalsIgnoreCase("SecondaryAddress")) {
							clientCorrAddress = checkForSecondaryAddress(
									clientAddressVO, currentDate);
						}

						if (clientCorrAddress == null) {
							for (int k = 0; k < clientAddressVO.length; k++) {
								if (clientAddressVO[k].getAddressTypeCT()
										.equals(addressTypeCT)) {
									if (clientAddressVO[k].getEffectiveDate() != null) {
										EDITDate effectiveDate = new EDITDate(
												clientAddressVO[k]
														.getEffectiveDate());

										EDITDate terminationDate = new EDITDate(
												clientAddressVO[k]
														.getTerminationDate());

										if ((currentDate.after(effectiveDate) || currentDate
												.equals(effectiveDate))
												&& (currentDate
														.before(terminationDate) || currentDate
														.equals(terminationDate))) {
											clientDetailVO[j]
													.addClientAddressVO(clientAddressVO[k]);
											break;
										}
									} else {
										clientDetailVO[j]
												.addClientAddressVO(clientAddressVO[k]);
										break;
									}
								}
							}
						} else {
							clientDetailVO[j]
									.addClientAddressVO(clientCorrAddress);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(
					"Correspondence Address Not Found For EDITTrxCorrespondencePK "
							+ editTrxCorrespondencePK);
		}
	}

	private ClientAddressVO checkForSecondaryAddress(
			ClientAddressVO[] clientAddressVOs, EDITDate currentDate)
					throws Exception {
		ClientAddressVO secondaryAddress = null;
		for (int i = 0; i < clientAddressVOs.length; i++) {
			if (clientAddressVOs[i].getAddressTypeCT().equals(
					"SecondaryAddress")) {
				EDITDate terminationDate = new EDITDate(
						clientAddressVOs[i].getTerminationDate());
				if (clientAddressVOs[i].getEffectiveDate() != null) {
					EDITDate effectiveDate = new EDITDate(
							clientAddressVOs[i].getEffectiveDate());

					if ((currentDate.after(effectiveDate) || currentDate
							.equals(effectiveDate))
							&& (currentDate.before(terminationDate) || currentDate
									.equals(terminationDate))) {
						String startDate = clientAddressVOs[i].getStartDate();
						String stopDate = clientAddressVOs[i].getStopDate();

						if ((currentDate.getFormattedMonth()
								+ EDITDate.DATE_DELIMITER + currentDate
								.getFormattedDay()).compareTo(startDate) >= 0
								&& (currentDate.getFormattedMonth()
										+ EDITDate.DATE_DELIMITER + currentDate
										.getFormattedDay())
										.compareTo(stopDate) <= 0) {
							secondaryAddress = clientAddressVOs[i];
							break;
						}
					}
				}
				// if effective date is null compare with termination date only.
				else {
					if (currentDate.before(terminationDate)
							|| currentDate.equals(terminationDate)) {
						String startDate = clientAddressVOs[i].getStartDate();
						String stopDate = clientAddressVOs[i].getStopDate();

						if ((currentDate.getFormattedMonth()
								+ EDITDate.DATE_DELIMITER + currentDate
								.getFormattedDay()).compareTo(startDate) >= 0
								&& (currentDate.getFormattedMonth()
										+ EDITDate.DATE_DELIMITER + currentDate
										.getFormattedDay())
										.compareTo(stopDate) <= 0) {
							secondaryAddress = clientAddressVOs[i];
							break;
						}
					}
				}
			}
		}

		return secondaryAddress;
	}

	private void populateActivitySummaryVO(EDITTrx[] editTrxs,
			ActivitySummaryVO activitySummaryVO, String endingEffDate,
			String drivingTrxType) throws Exception {
		TransactionPriority transactionPriority = TransactionPriority
				.findByTrxType(drivingTrxType);
		int drivingTrxPriority = transactionPriority.getPriority();
		EDITDate eedED = new EDITDate(endingEffDate);

		EDITBigDecimal totalPayments = new EDITBigDecimal();
		EDITBigDecimal interestEarned = new EDITBigDecimal();
		EDITBigDecimal totalWithdrawals = new EDITBigDecimal();
		EDITBigDecimal totalCharges = new EDITBigDecimal();

		for (int i = 0; i < editTrxs.length; i++) {
			String trxType = editTrxs[i].getTransactionTypeCT();
			transactionPriority = TransactionPriority.findByTrxType(trxType);
			int trxPriority = transactionPriority.getPriority();

			EDITDate trxEffDate = editTrxs[i].getEffectiveDate();

			if (trxEffDate.before(eedED)
					|| (trxEffDate.equals(eedED) && (trxPriority <= drivingTrxPriority))) {
				if (editTrxs[i].getStatus().equalsIgnoreCase("N")
						|| editTrxs[i].getStatus().equalsIgnoreCase("A")) {
					// EDITTrxHistoryVO[] editTrxHistoryVO =
					// DAOFactory.getEDITTrxHistoryDAO().findByEditTrxPK(editTrxPK);
					EDITTrxHistory editTrxHistory = editTrxs[i]
							.getEDITTrxHistory();
					// long editTrxHistoryPK =
					// editTrxHistoryVO[0].getEDITTrxHistoryPK();

					if (editTrxHistory != null) {
						Set chargeHistories = editTrxHistory
								.getChargeHistories();
						Set bucketHistories = editTrxHistory
								.getBucketHistories();
						FinancialHistory financialHistory = editTrxHistory
								.getFinancialHistory();

						if (chargeHistories != null) {
							for (Iterator iterator = chargeHistories.iterator(); iterator
									.hasNext();) {
								ChargeHistory chargeHistory = (ChargeHistory) iterator
										.next();
								EDITBigDecimal chargeAmount = chargeHistory
										.getChargeAmount();
								if (chargeHistory.getChargeTypeCT()
										.equalsIgnoreCase("MVA")) {
									chargeAmount = chargeAmount.negate();
								}
								totalCharges = totalCharges
										.addEditBigDecimal(chargeAmount);
							}
						}

						if (bucketHistories != null) {
							for (Iterator iterator = bucketHistories.iterator(); iterator
									.hasNext();) {
								BucketHistory bucketHistory = (BucketHistory) iterator
										.next();

								interestEarned = interestEarned
										.addEditBigDecimal(bucketHistory
												.getInterestEarnedCurrent());
							}
						}

						if (financialHistory != null) {
							EDITBigDecimal grossAmount = financialHistory
									.getGrossAmount();
							if (trxType.equalsIgnoreCase("PY")) {
								totalPayments = totalPayments
										.addEditBigDecimal(grossAmount);
							} else if (trxType.equalsIgnoreCase("WI")) {
								totalWithdrawals = totalWithdrawals
										.addEditBigDecimal(grossAmount);
							}
						}
					}
				}
			}
		}

		activitySummaryVO.setTotalPayments(totalPayments.getBigDecimal());
		activitySummaryVO.setInterestEarned(interestEarned.getBigDecimal());
		activitySummaryVO.setTotalWithdrawals(totalWithdrawals.getBigDecimal());
		activitySummaryVO.setTotalCharges(totalCharges.getBigDecimal());
	}

	private void getAdditionalData(NaturalDocVO naturalDocVO) throws Exception {
		// need all contract clients for extract
		if (naturalDocVO.getBaseSegmentVO() != null) {
			long segmentPK = naturalDocVO.getBaseSegmentVO().getSegmentVO()
					.getSegmentPK();
			// ContractClientVO[] contractClientVO =
			// contract.dm.dao.DAOFactory.getContractClientDAO().findBySegmentFK(segmentPK,
			// false, new ArrayList());
			Segment segment = Segment.findByPK(new Long(segmentPK));
			Set contractClients = segment.getContractClients();

			List voExclusionList = new ArrayList();
			voExclusionList.add(ClientRoleVO.class);

			for (Iterator iterator = contractClients.iterator(); iterator
					.hasNext();) {
				// ClientRoleVO clientRoleVO =
				// role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(contractClientVOs[i].getClientRoleFK(),
				// false, new ArrayList())[0];
				ContractClient contractClient = (ContractClient) iterator
						.next();
				ClientRole clientRole = contractClient.getClientRole();
				String roleType = clientRole.getRoleTypeCT();
				if (!roleType.equalsIgnoreCase(naturalDocVO.getBaseSegmentVO()
						.getClientVO(0).getRoleTypeCT())) {
					ClientDetailVO clientDetailVO = client.dm.dao.DAOFactory
							.getClientDetailDAO().findByClientPK(
									(clientRole.getClientDetailFK()), true,
									voExclusionList)[0];
					ClientVO clientVO = new ClientVO();
					clientVO.addContractClientVO((ContractClientVO) contractClient
							.getVO());
					clientVO.addClientDetailVO(clientDetailVO);
					clientVO.setRoleTypeCT(roleType);
					naturalDocVO.getBaseSegmentVO().addClientVO(clientVO);
				}
			}

			// add NoteReminders
			NoteReminderVO[] noteReminderVOs = contract.dm.dao.DAOFactory
					.getNoteReminderDAO().findBySegmentPK(segmentPK, false,
							new ArrayList());

			if (noteReminderVOs != null) {
				naturalDocVO.getBaseSegmentVO().getSegmentVO()
				.setNoteReminderVO(noteReminderVOs);
			}

			// Riders already in NaturalDoc - this logic adds the same riders
			// twice anyway
			// add all the riders in the segmentVO
			// SegmentVO[] segmentVOs =
			// contract.dm.dao.DAOFactory.getSegmentDAO().findRidersBySegmentPK(segmentPK);
			//
			// if (segmentVOs != null)
			// {
			// int i = 0;
			//
			// List segments = new ArrayList();
			// RiderSegmentVO[] riderSegmentVOs =
			// naturalDocVO.getRiderSegmentVO();
			// if (riderSegmentVOs != null)
			// {
			// for (i = 0; i < riderSegmentVOs.length; i++)
			// {
			// segments.add(riderSegmentVOs[i].getSegmentVO());
			// }
			// }
			//
			// for (i = 0; i < segmentVOs.length; i++)
			// {
			// segments.add(segmentVOs[i]);
			// }
			//
			// naturalDocVO.removeAllRiderSegmentVO();
			//
			// for (i = 0; i < segments.size(); i++)
			// {
			// SegmentVO riderSegment = (SegmentVO) segments.get(i);
			//
			// RiderSegmentVO riderSegmentVO = new RiderSegmentVO();
			//
			// riderSegmentVO.setRiderSegmentPK(i);
			// riderSegmentVO.setSegmentVO(riderSegment);
			//
			// naturalDocVO.addRiderSegmentVO(riderSegmentVO);
			// }
			// }

			// EDITTrxVO contained data it two places causing problems with xml
			// output
			// ClientSetupVO clientSetupVO =
			// naturalDocVO.getGroupSetupVO(0).getContractSetupVO(0).getClientSetupVO(0);
			// clientSetupVO.removeEDITTrxVO(0);
		}
	}

	/**
	 * Retrieves the IncludeHistoryIndicator for the given
	 * transactionCorrespondence (specified by the transactionCorrespondencePK)
	 * 
	 * @param transactionCorrespondence
	 *            The primary key for the transaction correspondence record
	 * @return
	 * @throws Exception
	 */
	private boolean getIncludeHistoryIndicator(
			TransactionCorrespondence transactionCorrespondence)
					throws Exception {
		boolean includeHistory = false;

		if (transactionCorrespondence.getIncludeHistoryIndicator() != null
				&& transactionCorrespondence.getIncludeHistoryIndicator()
				.equalsIgnoreCase("Y")) {
			includeHistory = true;
		}

		return includeHistory;
	}

	public void currentNode(Object currentNode, Object parentNode,
			RecursionContext recursionContext) {
		Map vosByPK = (Map) recursionContext.getFromMemory("vosByPK");

		VOObject voObject = (VOObject) currentNode;

		VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

		if (voObject instanceof CorrespondenceDetailVO) {
			if (vosByPK.containsKey("correspondenceDetailVO")) {
				VOObject voFromOutput = (VOObject) vosByPK
						.get("correspondenceDetailVO");

				voObject.copyFrom(voFromOutput);

				vosByPK.remove("correspondenceDetailVO");
			}
		} else {
			try {
				Long pk = (Long) voClass.getPKGetter().getMethod()
						.invoke(voObject, null);

				if (vosByPK.containsKey(pk.toString())) {
					VOObject voFromOutput = (VOObject) vosByPK.get(pk
							.toString());

					voObject.copyFrom(voFromOutput);

					vosByPK.remove(pk.toString());
				}
			} catch (Exception e) {
				System.out.println(e);

				e.printStackTrace(); // To change body of catch statement use
				// Options | File Templates.

				throw new RuntimeException(e);
			}
		}

		if (vosByPK.isEmpty()) {
			recursionContext.setShouldContinueRecursion(false);
		}
	}

	/**
	 * Updates the Preference.PrintAs as per the following rules. If
	 * Preference.PrintAs is null If Segment.ContractTypeCT is 'Joint'
	 * Preference.PrintAs = Primary Owner FirstName LastName + Secondary Owner
	 * FirstName LastName Otherwise Preference.PrintAs = Primary Owner FirstName
	 * LastName The length is restricted to 70 characters because the length of
	 * Preference.PrintAs field length is 70. When the Segment.ContractTypeCT is
	 * 'Joint' do not move CorporateName. If the ClientDetail does not have
	 * Preference don't do anything.
	 * 
	 * @param correspondenceDetailVO
	 */
	private void updatePreferenceInCorrespondenceDetailVO(
			CorrespondenceDetailVO correspondenceDetailVO) {
		NaturalDocVO naturalDocVO = correspondenceDetailVO.getNaturalDocVO(0);

		BaseSegmentVO baseSegmentVO = naturalDocVO.getBaseSegmentVO();

		SegmentVO segmentVO = baseSegmentVO.getSegmentVO();

		ClientVO[] clientVO = baseSegmentVO.getClientVO();

		for (int i = 0; i < clientVO.length; i++) {
			if (ClientRole.ROLETYPECT_OWNER.equals(clientVO[i].getRoleTypeCT())) {
				ClientDetailVO clientDetailVO = clientVO[i]
						.getClientDetailVO(0);

				if (clientDetailVO.getPreferenceVO().length > 0) {
					PreferenceVO preferenceVO = clientDetailVO
							.getPreferenceVO(0);

					String printAs = preferenceVO.getPrintAs();

					if (printAs == null) {
						String contractTypeCT = segmentVO.getContractTypeCT();

						if (contractTypeCT != null
								&& contractTypeCT
								.equals(Segment.CONTRACTTYPECT_JOINT)) {
							Segment segment = Segment.findByPK(new Long(
									segmentVO.getSegmentPK()));

							printAs = clientDetailVO.getFirstName() + " "
									+ clientDetailVO.getLastName();

							ContractClient secondaryOwnerCC = segment
									.getSecondaryOwner();

							if (secondaryOwnerCC != null) {
								ClientDetail secondaryOwner = secondaryOwnerCC
										.getClientRole().getClientDetail();

								printAs = printAs
										+ " "
										+ SEPERATOR_FOR_PRIMARY_OWNER_AND_SECONDARY_OWNER
										+ " " + secondaryOwner.getFirstName()
										+ " " + secondaryOwner.getLastName();
							}
						} else {
							if (clientDetailVO.getCorporateName() == null) {
								printAs = clientDetailVO.getFirstName() + " "
										+ clientDetailVO.getLastName();
							} else {
								printAs = clientDetailVO.getCorporateName();
							}
						}

						if (printAs.length() > 70) {
							printAs = printAs.substring(0, 70);
						}

						preferenceVO.setPrintAs(printAs);
					}
				}
			}
		}
	}

	private File getExportFile() {
		EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

		File exportFile = new File(export1.getDirectory() + "SEGCORR_"
				+ System.currentTimeMillis() + ".xml");

		return exportFile;
	}

	private void exportCorrespondence(
			CorrespondenceDetailVO correspondenceDetailVO, File exportFile)
					throws Exception
					// private void exportCorrespondence(CorrespondenceDetailVO
					// correspondenceDetailVO, Logger exportFile) throws Exception
					{
		String parsedXML = roundDollarFields(correspondenceDetailVO);

		parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);

		appendToFile(exportFile, parsedXML);
					}

	private String roundDollarFields(
			CorrespondenceDetailVO correspondenceDetailVO) throws Exception {
		String[] fieldNames = setupFieldNamesForRounding();
		String voToXML = Util.roundDollarTextFields(correspondenceDetailVO,
				fieldNames);

		return voToXML;
	}

	private String[] setupFieldNamesForRounding() {
		List fieldNames = new ArrayList();

		fieldNames.add("GroupSetupVO.GroupAmount");
		fieldNames.add("GroupSetupVO.EmployerContribution");
		fieldNames.add("GroupSetupVO.EmployeeContribution");
		fieldNames.add("SegmentVO.Amount");
		fieldNames.add("SegmentVO.CostBasis");
		fieldNames.add("SegmentVO.RecoveredCostBasis");
		fieldNames.add("SegmentVO.Charges");
		fieldNames.add("SegmentVO.Loads");
		fieldNames.add("SegmentVO.Fees");
		fieldNames.add("SegmentVO.FreeAmountRemaining");
		fieldNames.add("SegmentVO.FreeAmount");

		fieldNames.add("BucketVO.CumDollars");
		fieldNames.add("BucketVO.DepositAmount");
		fieldNames.add("BucketVO.PayoutDollars");
		fieldNames.add("BucketVO.GuarCumValue");
		fieldNames.add("BucketVO.BonusAmount");
		fieldNames.add("BucketVO.RebalanceAmount");
		fieldNames.add("EDITTrxVO.TrxAmount");
		fieldNames.add("AgentSnapshotDetailVO.CommissionOverrideAmount");
		fieldNames.add("ProjectionsVO.Amount");
		fieldNames.add("InvestmentRateVO.MinimumTransferAmount");
		fieldNames.add("ActivitySummaryVO.TotalPayments");
		fieldNames.add("ActivitySummaryVO.InterestEarned");
		fieldNames.add("ActivitySummaryVO.TotalWithdrawals");
		fieldNames.add("ActivitySummaryVO.TotalCharges");

		return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
	}

	// private void appendToFile(Logger corrLogger, String data)
	// {
	// corrLogger.fatal(data);
	//
	// }
	private void appendToFile(File exportFile, String data) throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));

		bw.write(data);

		bw.flush();

		bw.close();
	}

	private void insertStartCorrespondence(File exportFile) throws Exception
	// private void insertStartCorrespondence(Logger exportFile) throws
	// Exception
	{
		appendToFile(exportFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		appendToFile(exportFile, "<CorrespondenceVO>\n");
	}

	private void insertEndCorrespondence(File exportFile) throws Exception
	{
		appendToFile(exportFile, "\n</CorrespondenceVO>");
	}

	private void updateToVOModel(Object[] voObjects,
			CorrespondenceDetailVO correspondenceDetailVO) {
		for (int i = 0; i < voObjects.length; i++) {
			Object voObject = voObjects[i];

			String currentTableName = VOClass.getTableName(voObject.getClass());

			if (currentTableName.equalsIgnoreCase("Projections")) {
				ProjectionsVO projectionsVO = (ProjectionsVO) voObject;
				correspondenceDetailVO.addProjectionsVO(projectionsVO);
			}

			if (currentTableName.equalsIgnoreCase("ProjectionSurrenderCharge")) {
				ProjectionSurrenderChargeVO projectionSurrenderChargeVO = (ProjectionSurrenderChargeVO) voObject;
				correspondenceDetailVO
				.addProjectionSurrenderChargeVO(projectionSurrenderChargeVO);
			}

			if (currentTableName.equalsIgnoreCase("InvestmentRate")) {
				InvestmentRateVO investmentRateVO = (InvestmentRateVO) voObject;
				correspondenceDetailVO.addInvestmentRateVO(investmentRateVO);
			}

			if (currentTableName.equalsIgnoreCase("Bucket")) {
				BucketVO bucketVO = (BucketVO) voObject;
				InvestmentVO[] investmentVOs = correspondenceDetailVO
						.getNaturalDocVO(0).getBaseSegmentVO().getSegmentVO()
						.getInvestmentVO();
				if (investmentVOs != null) {
					for (int j = 0; j < investmentVOs.length; j++) {
						if (investmentVOs[j].getInvestmentPK() == bucketVO
								.getInvestmentFK()) {
							investmentVOs[j].addBucketVO(bucketVO);
						}
					}
				}
			}
			if (currentTableName.equalsIgnoreCase("CalculatedValues")) {
				CalculatedValuesVO calculatedValuesVO = (CalculatedValuesVO) voObject;
				correspondenceDetailVO
				.addCalculatedValuesVO(calculatedValuesVO);
			}

			if (currentTableName.equalsIgnoreCase("GIOOptionValue")) {
				GIOOptionValueVO gioOptionValueVO = (GIOOptionValueVO) voObject;
				correspondenceDetailVO.addGIOOptionValueVO(gioOptionValueVO);
			}
		}
	}

	// private void fooStartCorres()
	// {
	// Logger corrLogger = null;
	//
	// try
	// {
	// corrLogger = getExportLogger();
	//
	//
	// for (int i = 0; i < 100000; i++)
	// {
	// corrLogger.fatal("Foo Message: " + i);
	// }
	//
	// org.apache.log4j.Hierarchy hierarchy = new Hierarchy(corrLogger);
	//
	// hierarchy.shutdown();
	//
	// //corrLogger.close()//
	// }
	// catch(Exception e)
	// {
	// System.out.println(e);
	//
	// e.printStackTrace();
	//
	// // Logging to DB as well?
	// }
	//
	//
	//
	// }

	// private Logger getExportLogger() throws IOException
	// {
	// EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");
	//
	// // Step 1: Build the rolling file appender (archive mechanics)
	// String file = export1.getDirectory() + "SEGCORR_" +
	// System.currentTimeMillis() + ".xml";
	// // String file =
	// "/Perforce\\EditSolutions\\EQDevelopment\\exploded\\logs\\CORRLOGGER.log";
	// // File exportFile = new File(file);
	//
	// Layout layout = new CorrespondenceLayout();
	//
	// RollingFileAppender rollingFileAppender = new RollingFileAppender(layout,
	// file);
	// // rollingFileAppender.setFile(file);
	// rollingFileAppender.setAppend(true);
	// rollingFileAppender.setBufferedIO(true);
	// rollingFileAppender.setMaximumFileSize(MAX_FILE_SIZE);
	// rollingFileAppender.setMaxBackupIndex(NUM_BACKUPS);
	// rollingFileAppender.setImmediateFlush(true);
	// rollingFileAppender.activateOptions();
	//
	// // Step 2 open a Log that uses the rolling appender
	//
	// // Step 3 Use the Log (start logging the Corr entries)
	//
	// // Step 4 Close the Log
	//
	//
	//
	//
	// Logger logger = Logger.getLogger("CORRLOGGER");
	//
	// logger.addAppender(rollingFileAppender);
	//
	// return logger;
	// }

}
