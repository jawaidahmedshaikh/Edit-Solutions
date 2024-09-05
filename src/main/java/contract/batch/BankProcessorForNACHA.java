/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.batch;

import batch.CashBatchFileMaker;
import batch.business.*;
import contract.Segment;
import contract.Bucket;
import contract.ContractClient;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.*;
import edit.services.db.hibernate.*;
import edit.services.config.*;
import edit.services.logging.*;
import event.*;
import event.financial.contract.trx.*;
import event.financial.group.trx.*;
import fission.utility.*;
import group.ContractGroup;
import role.*;
import client.*;
import billing.*;
import engine.*;
import businesscalendar.*;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import logging.*;
import org.apache.logging.log4j.Logger;

/**
 * Back end job to produce a fixed length flat file for Bank EFT processing.
 * This job looks at the pending Bills and Suspense that are a source of EFT and
 * formats the qualifying data to the ouput formats.
 */
public class BankProcessorForNACHA implements Serializable {

	public File eftNACHAFile;
	private BufferedWriter bufferedWriter;
	private CashBatchFileMaker cashBatchFile;

	private String bankRoutingNumber;

	private String formattedBatchSeqNumber;
	private Integer batchSequenceNumber = 0;

	private String detailSeqNumber;
	private Integer batchDetailCount = 0;
	private Integer totalDetailCount = 0;

	private EDITBigDecimal batchTotalAmount = new EDITBigDecimal();
	private EDITBigDecimal batchTotalRoutingNumber;// = new EDITBigDecimal(NACHADetail.CHUBB_ROUTING_NUMBER);
	private EDITBigDecimal sumOfAllRoutingNumbers;
	private EDITBigDecimal sumOfAllDebitTotals;
	private EDITBigDecimal sumOfAllCreditTotals;
	private Integer sumOfAllRecords = 0;
	private String createCashBatchFile = "false";
	EDITDate draftDateED;

	public static final String PROCESS_TYPE_BILL = "Billing";
	public static final String PROCESS_TYPE_TRX = "Transaction";
	public static final String PROCESS_TYPE_CHECK = "Check";
    private static final String SELMAN_TIN = "1362136262";

	public BankProcessorForNACHA() {

	}

	/**
	 * From the parameters selected - start the batch stats and execute the job.
	 * When the job is finshed the batch stats will indicate the end. During
	 * processing the number of records processed or errored will display in the
	 * stats.
	 * 
	 * @param processDate
	 * @param companyName
	 */
	public void createBankForNACHA(String companyName, String processDate, String isIndividual,
			String createCashBatchFile) throws Exception {
		EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
				.tagBatchStart(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA, "Bank Extract EFT");

		this.createCashBatchFile = createCashBatchFile;

		EDITDate processDateED = new EDITDate(processDate);
		BusinessDay bestBusinessDay = new BusinessCalendar().findNextBusinessDay(processDateED, 2);
		draftDateED = bestBusinessDay.getBusinessDate();

		try {
			if (isIndividual.equals("true")) {
				bankJobForNACHA(processDate, companyName);
			} else {
				bankJobForNACHAOneBillSchedulePerGroup(processDate, companyName);
			}
		} catch (Exception e) {
			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
					.updateFailure();

			System.out.println(e);

			e.printStackTrace();

			LogEvent logEvent = new LogEvent("Bank EFT Extracts Errored", e);

			Logger logger = Logging.getLogger(Logging.BATCH_JOB);

			logger.error(logEvent);

			// Log error to database
//            EDITMap columnInfo = new EDITMap("ContractNumber", "N/A");
//            columnInfo.put("ProcessDate", processDate);
//
//            Log.logToDatabase(Log.BANK, "Bank EFT Extracts Errored: " + e.getMessage(), columnInfo);
		} finally {
			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
					.tagBatchStop();
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
		}
	}

	/**
	 * Access the productStructure(s) requested, a company name of 'All' can be
	 * selected. If it is all product type product structures will be used in the
	 * processing. For the billing, contract suspense and check suspense processing
	 * each will be done for the company(s) selected. Accumulation of amounts,
	 * routing numbers and record counts of the detail will produce summary totals
	 * at the batch and file levels.
	 *
	 * The first record output is the FileHeader, which requires the "Bank" trust
	 * type on the ClientDetail.
	 *
	 * Each new company will require a batch header, which requires the"Client
	 * Company" trust type on the ClienDetail. The qualifying data will be used to
	 * created the detail. The amounts and routingNumbers wi be accummulated for the
	 * Batch Control processing. At the end of the company detail the Batch Control
	 * record is output. It will contain the total amounts and routing numbers of
	 * the batch.
	 *
	 * At the end of all the processing, the last record writtem is the File
	 * Control. It will contain the total amounts of all the batches and the total
	 * records written to the file.
	 * 
	 * @param processDate
	 * @param companyName
	 * @throws Exception
	 */
	private void bankJobForNACHA(String processDate, String companyName) {

		try {
			sumOfAllRoutingNumbers = new EDITBigDecimal();
			sumOfAllDebitTotals = new EDITBigDecimal();
			sumOfAllCreditTotals = new EDITBigDecimal();
			sumOfAllRecords = 0;

			Company[] companies = null;
			if (companyName.equalsIgnoreCase("All")) {
				companies = Company.find_All_CompaniesForProductType();
			} else {
				Company company = Company.findBy_CompanyName(companyName);
				List<Company> companyList = new ArrayList<>();
				companyList.add(company);
				companies = (Company[]) companyList.toArray(new Company[companyList.size()]);
			}
			// Create file and the file header
			if (createCashBatchFile.equals("true")) {
				cashBatchFile = new CashBatchFileMaker(draftDateED);
			}

			// Process billSchedules for each company selected
			for (int i = 0; i < companies.length; i++) {
				ClientDetail clientCompanyDetail = ClientDetail
						.findByTrustType_CompanyFK(ClientDetail.TRUSTTYPECT_CLIENTCOMPANY, companies[i].getCompanyPK());

				if (clientCompanyDetail != null) {

					String companyPrefix = companies[i].getPolicyNumberPrefix();

					batchTotalAmount = new EDITBigDecimal();
					batchTotalRoutingNumber = new EDITBigDecimal();

					setEFTNACHAFile(companies[i].getCompanyName());
					getFileHeader();

					Segment[] segments = Segment.findNachaSegments(processDate, companyPrefix);
					if ((segments != null) && (segments.length > 0)) {
						NACHABatchHeader nachaBatchHeader = createBatchHeader(draftDateED, companies[i],
								clientCompanyDetail);
						batchDetailCount = 0;

						if (createCashBatchFile.equals("true")) {
							processBillSchedule(segments, companies[i], processDate, cashBatchFile);
						} else {
							processBillSchedule(segments, companies[i], processDate);
						}
//						processClientCompanyDetail(nachaBatchHeader, PROCESS_TYPE_BILL);
						endBatchProcess(SELMAN_TIN, PROCESS_TYPE_BILL);
						totalDetailCount = totalDetailCount + batchDetailCount;

					}
				}
				processFileControl();
			}

			if (createCashBatchFile.equals("true")) {
				cashBatchFile.createTrailerFinishFile(draftDateED);
			}

			// File control is the last record now close the writer
			bufferedWriter.close();
			bufferedWriter = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Access the productStructure(s) requested, a company name of 'All' can be
	 * selected. If it is all product type product structures will be used in the
	 * processing. For the billing, contract suspense and check suspense processing
	 * each will be done for the company(s) selected. Accumulation of amounts,
	 * routing numbers and record counts of the detail will produce summary totals
	 * at the batch and file levels.
	 *
	 * The first record output is the FileHeader, which requires the "Bank" trust
	 * type on the ClientDetail.
	 *
	 * Each new company will require a batch header, which requires the"Client
	 * Company" trust type on the ClienDetail. The qualifying data will be used to
	 * created the detail. The amounts and routingNumbers will be accummulated for
	 * the Batch Control processing. At the end of the company detail the Batch
	 * Control record is output. It will contain the total amounts and routing
	 * numbers of the batch.
	 *
	 * At the end of all the processing, the last record writtem is the File
	 * Control. It will contain the total amounts of all the batches and the total
	 * records written to the file.
	 * 
	 * @param processDate
	 * @param companyName
	 * @throws Exception
	 */
	private void bankJobForNACHAOneBillSchedulePerGroup(String processDate, String companyName) {
		BillSchedule billSchedule = null;

		try {
			sumOfAllRoutingNumbers = new EDITBigDecimal();
			sumOfAllDebitTotals = new EDITBigDecimal();
			sumOfAllCreditTotals = new EDITBigDecimal();
			sumOfAllRecords = 0;

			Company[] companies = null;
			if (companyName.equalsIgnoreCase("All")) {
				companies = Company.find_All_CompaniesForProductType();
			} else {
				Company company = Company.findBy_CompanyName(companyName);
				List<Company> companyList = new ArrayList<>();
				companyList.add(company);
				companies = (Company[]) companyList.toArray(new Company[companyList.size()]);
			}
			// Create file and the file header
			setEFTNACHAFile("LISTBILL");
			getFileHeader();
			if (createCashBatchFile.equals("true")) {
				cashBatchFile = new CashBatchFileMaker(draftDateED);
			}

			// Process billSchedules for each company selected
			for (int i = 0; i < companies.length; i++) {
				ClientDetail clientCompanyDetail = ClientDetail
						.findByTrustType_CompanyFK(ClientDetail.TRUSTTYPECT_CLIENTCOMPANY, companies[i].getCompanyPK());

				if (clientCompanyDetail != null) {

					String billCompanyName = companies[i].getCompanyName();

					batchTotalAmount = new EDITBigDecimal();
					batchTotalRoutingNumber = new EDITBigDecimal();

					BillSchedule[] billSchedules = BillSchedule
							.findByExtractDate_Role_Source_For_Group_EFT(new EDITDate(processDate), billCompanyName);
					if (billSchedules != null) {
						for (int j = 0; j < billSchedules.length; j++) {
							batchDetailCount = 0;
							NACHABatchHeader nachaBatchHeader = createBatchHeader(new EDITDate(processDate),
									companies[i], clientCompanyDetail);
							billSchedule = billSchedules[j];
							billSchedule.setSegments(new HashSet<Segment>(
									Arrays.asList(Segment.findBy_BillScheduleFK_2(billSchedule.getBillSchedulePK()))));

							if (createCashBatchFile.equals("true")) {
								processBillScheduleByGroup(billSchedule, companies[i], processDate, cashBatchFile);
							} else {
								processBillScheduleByGroup(billSchedule, companies[i], processDate);
							}
							// processClientCompanyDetail(nachaBatchHeader, PROCESS_TYPE_BILL);

							endBatchProcess(nachaBatchHeader.getEmployerId(), PROCESS_TYPE_BILL);
							totalDetailCount = totalDetailCount + batchDetailCount;

						}
					}
				}
				processFileControl();

				if (createCashBatchFile.equals("true")) {
					cashBatchFile.createTrailerFinishFile(draftDateED);
				}
			}

			// File control is the last record now close the writer
			bufferedWriter.close();
			bufferedWriter = null;

		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();
		} finally {
			SessionHelper.clearSessions();
		}
	}

	private void logErrorToDatabase(Exception e, BillSchedule billSchedule, Company company, Segment segment,
			String message, String processDate) {
		String payorName = "";

		String contractNumber = "";
		if (message == null) {
			message = "Bank Job Errored: " + e.getMessage();
		}
		if (segment != null) {
			ContractClient payorContractClient = segment.getPayorContractClient();
			ClientRole clientRole = payorContractClient.getClientRole();
			contractNumber = segment.getContractNumber();
			payorName = clientRole.getClientDetail().getPrettyName();
		}

		EDITMap columnInfo = null;
		if ((company == null) || (company.getCompanyName() == null)) {
			columnInfo = new EDITMap("CompanyName", "Missing");

		} else {
			columnInfo = new EDITMap("CompanyName", company.getCompanyName());
		}
		columnInfo.put("ContractNumber", contractNumber);

		if ((billSchedule != null) && (billSchedule.getNextBillDueDate() != null)) {
			columnInfo.put("DueDate", billSchedule.getNextBillDueDate());
		} else {
			columnInfo.put("DueDate", "Missing");
		}
		columnInfo.put("PayorName", payorName);
		columnInfo.put("ProcessDate", processDate);

		logging.Log.logToDatabase(logging.Log.BANK, message, columnInfo);
	}

	// using cashbatch file to create suspense records...
	/*
	 * private Suspense[] getContractSuspenseForNACHA(Long productStructurePK) {
	 * Suspense[] contractSuspenses =
	 * Suspense.findContractSuspenseForBankEFT(productStructurePK);
	 * 
	 * List suspenses = new ArrayList(); if (contractSuspenses != null) { for (int i
	 * = 0; i < contractSuspenses.length; i++) { Segment segment =
	 * contractSuspenses[i].getInSuspense().getEDITTrxHistory().getEDITTrx().
	 * getClientSetup() .getContractSetup().getSegment();
	 * 
	 * ClientRole clientRole = segment.getContractClient().getClientRole(); if
	 * (clientRole.getPreferenceFK() != null && clientRole.getPreferenceFK() != 0) {
	 * suspenses.add(contractSuspenses[i]); } else { Preference preference =
	 * Preference.findByClientDetailPK_EFTPrimary_PreferenceType(
	 * clientRole.getClientDetailFK(), "Disbursement"); if (preference != null) {
	 * suspenses.add(contractSuspenses[i]); } } } }
	 * 
	 * return (Suspense[]) suspenses.toArray(new Suspense[suspenses.size()]); }
	 */

	/*
	 * private void processBillSchedule(Segment[] segments, Company company, String
	 * processDate, CashBatchFileMaker cashBatchFile) throws Exception {
	 * 
	 * try {
	 * 
	 * for (int n = 0; n < segments.length; n++) {
	 * System.out.println("Contract Number: " + segments[n].getContractNumber()); if
	 * (segments[n].getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_ACTIVE) ||
	 * segments[n].getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_SUBMITTED) ||
	 * segments[n].getSegmentStatusCT().equals(Segment.
	 * SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM)) {
	 * 
	 * try { detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);
	 * EDITBigDecimal billedAmount = getBillAmount(
	 * SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
	 * segments[n].getSegmentPK()); ClientDetail clientDetail =
	 * getClientDetailForBillData(segments[n]); NACHADetail nachaDetail = new
	 * NACHADetail(detailSeqNumber, billedAmount, clientDetail, PROCESS_TYPE_BILL);
	 * 
	 * nachaDetail.createDetail(); if (cashBatchFile != null) { ContractGroup
	 * contractGroup = ContractGroup
	 * .findBy_ContractGroupPK(segments[n].getContractGroupFK());
	 * cashBatchFile.createPayments("D", contractGroup.getContractGroupNumber(),
	 * segments[n].getContractNumber(), new EDITDate(), billedAmount, "NACHA",
	 * "999"); }
	 * 
	 * writeToBufferedWriter(nachaDetail.getFileData().toString());
	 * 
	 * accumlationsForDetail(nachaDetail);
	 * 
	 * batchDetailCount++; EditServiceLocator.getSingleton().getBatchAgent()
	 * .getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA).updateSuccess(); } catch
	 * (Exception ex) { EditServiceLocator.getSingleton().getBatchAgent()
	 * .getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA).updateFailure();
	 * System.out.println("Failed record: " + segments[n].getContractNumber()); } }
	 * }
	 * 
	 * } catch (Exception e) { System.out.println(e);
	 * 
	 * e.printStackTrace();
	 * 
	 * String message = " Processing BillSchedules Errored";
	 * 
	 * // logErrorToDatabase(e, billSchedule, company, segment, message,
	 * processDate);
	 * 
	 * }
	 * 
	 * }
	 */

	private void processBillSchedule(Segment[] segments, Company company, String processDate) throws Exception {
		processBillSchedule(segments, company, processDate, null);
	}

	private void processBillSchedule(Segment[] segments, Company company, String processDate,
			CashBatchFileMaker cashBatchFile) throws Exception {

		Segment segment = null;
		BillSchedule billSchedule = null;
		NACHADetail nachaDetail = null;

		for (int n = 0; n < segments.length; n++) {
			segment = segments[n];

			try {
				ClientDetail clientDetail = getClientDetailForBillData(segment);
				Preference preference = Preference.findByClientDetailPK_DisbursementAndPreferenceType(
						clientDetail.getClientDetailPK(), "EFT", "billing");
				Set<Preference> preferences = new HashSet<>();
				preferences.add(preference);
				clientDetail.setPreferences(preferences);

				detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);
				EDITBigDecimal billedAmount = getBillAmount(
						SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(), segment.getSegmentPK());

				nachaDetail = new NACHADetail(segment, detailSeqNumber, billedAmount, clientDetail, PROCESS_TYPE_BILL);

				billSchedule = BillSchedule.findBy_BillSchedulePK(segment.getBillScheduleFK());

				nachaDetail.createDetail();
				if (cashBatchFile != null) {
					ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(segment.getContractGroupFK());
					cashBatchFile.createPayments("D", contractGroup.getContractGroupNumber(),
							segment.getContractNumber(), billSchedule.getNextBillDueDate(), billedAmount, "NACHA",
							"999");
				}

				System.out.println(">" + nachaDetail.getFileData().toString() + "<");
				writeToBufferedWriter(nachaDetail.getFileData().toString());
				accumlationsForDetail(nachaDetail);
				batchDetailCount++;

				EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
						.updateSuccess();
				System.out.println(segment.getContractNumber());
			} catch (Exception ex) {
				EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
						.updateFailure();
				System.out.println("Failed record: " + segment.getContractNumber());
				String message = " EFT Errored: " + segment.getContractNumber();

				if ((nachaDetail != null) && (nachaDetail.getMessage() != null)) {
					message = message + ": " + nachaDetail.getMessage();

				}
				logErrorToDatabase(ex, billSchedule, company, segment, message, processDate);

				continue;
			}

		}
	}

	/*
	 * private void processBillSchedule(Segment[] segments, Company company, String
	 * processDate) throws Exception { processBillSchedule(segments, company,
	 * processDate, null); }
	 */

	private void processBillScheduleByGroup(BillSchedule billSchedule, Company company, String processDate)
			throws Exception {
		processBillScheduleByGroup(billSchedule, company, processDate, null);
	}

	private void processBillScheduleByGroup(BillSchedule billSchedule, Company company, String processDate,
			CashBatchFileMaker cashBatchFile) throws Exception {

		if (billSchedule.getBillGroup() == null) {
			System.out.println("null BillGroup: billSchedulePK: " + billSchedule.getBillSchedulePK());
//			return;
		}
//		System.out.println("begin batch: " + billSchedule.getBillSchedulePK());

		Segment segment = null;
		try {

//				Bill bill = billSchedule.getBillGroup().getBill();

			// Set segments = billSchedule.getSegments();
			Segment[] segments = Segment.findBy_BillScheduleFK(billSchedule.getBillSchedulePK());
			// System.out.println("billSchedulePK: " + billSchedule.getBillSchedulePK());
			// for (Iterator iterator = segments.iterator(); iterator.hasNext();) {
			for (int n = 0; n < segments.length; n++) {
				// segment = (Segment) iterator.next();
				segment = segments[n];
				/*
				 * if (segment.getContractNumber().equals("VC00342344")) {
				 * System.out.println("Contract Number: " + segment.getContractNumber()); } if
				 * (segment.getContractNumber().equals("VC00331230")) {
				 * System.out.println("Contract Number: " + segment.getContractNumber()); }
				 */

				// System.out.println("SegmentPK: " + segment.getSegmentPK());
				if (segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_ACTIVE)
						|| segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_SUBMITTED)
						|| segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM)) {

					try {
						detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);
						EDITBigDecimal billedAmount = getBillAmount(
								SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection(),
								segment.getSegmentPK());

						ClientDetail clientDetail = getClientDetailForBillData(segment);
						Preference preference = Preference.findByClientDetailPK_DisbursementAndPreferenceType(
								clientDetail.getClientDetailPK(), "EFT", "billing");
						Set<Preference> preferences = new HashSet<>();
						preferences.add(preference);
						clientDetail.setPreferences(preferences);

						System.out.println("PROCESSING: " + segment.getContractNumber());
						NACHADetail nachaDetail = new NACHADetail(segment, detailSeqNumber, billedAmount, clientDetail,
								PROCESS_TYPE_BILL);

						nachaDetail.createDetail();
						if (cashBatchFile != null) {
							ContractGroup contractGroup = ContractGroup
									.findBy_ContractGroupPK(segment.getContractGroupFK());
							// System.out.println("GroupNumber: " + contractGroup.getContractGroupNumber());
							cashBatchFile.createPayments("D", contractGroup.getContractGroupNumber(),
									segment.getContractNumber(), draftDateED, billedAmount, "NACHA", "999");
						}

						writeToBufferedWriter(nachaDetail.getFileData().toString());

						accumlationsForDetail(nachaDetail);

						batchDetailCount++;
						EditServiceLocator.getSingleton().getBatchAgent()
								.getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA).updateSuccess();
					} catch (Exception ex) {
						System.out.println("Failed record: " + segment.getContractNumber());
						System.out.println(ex.toString());
						EditServiceLocator.getSingleton().getBatchAgent()
								.getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA).updateFailure();
						String message = " Processing BillSchedules Errored";
						logErrorToDatabase(ex, billSchedule, company, segment, message, processDate);
					}

				}
			}
			System.out.println("end batch");
		}

		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			String message = " Processing BillSchedules Errored";
			logErrorToDatabase(e, billSchedule, company, segment, message, processDate);

		}

	}

	private void processBillSchedulesByGroup(Long contractGroupPK, Company company, String processDate)
			throws SQLException {
		String companyPrefix = company.getPolicyNumberPrefix();
		Segment[] segments = Segment.findBy_ContractGroupFKForNachaFile(contractGroupPK, processDate, companyPrefix);
		for (int i = 0; i < segments.length; i++) {
			try {
				detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);
				BillSchedule billSchedule = BillSchedule.findBy_SegmentPK(segments[i].getSegmentPK());
				Bill bill = billSchedule.getBillGroup().getBill();
				EDITBigDecimal billedAmount = bill.getBilledAmount();
				ClientDetail clientDetail = getClientDetailForBillData(segments[i]);
				NACHADetail nachaDetail = new NACHADetail(segments[i],detailSeqNumber, billedAmount, clientDetail,
						PROCESS_TYPE_BILL);
				nachaDetail.createDetail();
				writeToBufferedWriter(nachaDetail.getFileData().toString());
				accumlationsForDetail(nachaDetail);
				batchDetailCount++;

				EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
						.updateSuccess();
			} catch (Exception ex) {
				EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
						.updateFailure();
				System.out.println("Failed record: " + segments[i].getContractNumber());
			}
		}

	}

	/**
	 * Get the Bill.BilledAmount and client information to produce the billing
	 * detail. Accumulate the billAmount and routingNumber for the batchControl
	 * requirements for totals. Create a premium trx for the bill processed using
	 * the NextBillDueDate. - should all be in the future.
	 * 
	 * @param billSchedules
	 * @param processDateED
	 */
	private void processBillSchedules(BillSchedule[] billSchedules, Company company, String processDate)
			throws Exception {
		BillSchedule billSchedule = null;
		Segment segment = null;

		try {
			for (int i = 0; i < billSchedules.length; i++) {
				billSchedule = billSchedules[i];

				Bill bill = billSchedules[i].getBillGroup().getBill();

				Set segments = billSchedules[i].getSegments();

				for (Iterator iterator = segments.iterator(); iterator.hasNext();) {
					segment = (Segment) iterator.next();
					if (segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_ACTIVE)
							|| segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_SUBMITTED)
							|| segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM)) {
						batchDetailCount++;

						detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);

						ClientDetail clientDetail = getClientDetailForBillData(segment);

						NACHADetail nachaDetail = new NACHADetail(segment, detailSeqNumber, bill.getBilledAmount(), clientDetail,
								PROCESS_TYPE_BILL);

						nachaDetail.createDetail();

						writeToBufferedWriter(nachaDetail.getFileData().toString());

						accumlationsForDetail(nachaDetail);

						EditServiceLocator.getSingleton().getBatchAgent()
								.getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA).updateSuccess();
					}
				}
			}
		}

		catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			String message = " Processing BillSchedules Errored";

			logErrorToDatabase(e, billSchedule, company, segment, message, processDate);

		}

	}

	/**
	 * Get the ClientDetail and the SuspenseAmount to crate the detail for Contract
	 * Suspense processing. The amount and rounting number will be accumulated for
	 * the Batch Control processing. The suspense MaintenanceInd must be updated in
	 * order to marke this record as processed.
	 * 
	 * @param contractSuspenses
	 */
	private void processContractSuspense(Suspense[] contractSuspenses) throws Exception {
		for (int i = 0; i < contractSuspenses.length; i++) {
			batchDetailCount++;
			detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);

			Suspense contractSuspense = contractSuspenses[i];

			ClientDetail clientDetail = getClientForContractSuspense(contractSuspense);
			NACHADetail nachaDetail = new NACHADetail(new Segment(), detailSeqNumber, contractSuspense.getSuspenseAmount(),
					clientDetail, PROCESS_TYPE_TRX);

			nachaDetail.createDetail();

			writeToBufferedWriter(nachaDetail.getFileData().toString());

			accumlationsForDetail(nachaDetail);

			updateSuspense(contractSuspense);

			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
					.updateSuccess();
		}
	}

	/**
	 * Get the ClientDetail and the SuspenseAmount to crate the detail for Check
	 * Suspense processing. The amount and rounting number will be accumulated for
	 * the Batch Control processing. The suspense MaintenanceInd must be updated in
	 * order to marke this record as processed.
	 * 
	 * @param checkSuspenses
	 */
	private void processCheckSuspense(Suspense[] checkSuspenses) throws Exception

	{
		for (int i = 0; i < checkSuspenses.length; i++) {
			batchDetailCount++;
			detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);

			Suspense checkSuspense = checkSuspenses[i];
			ClientDetail clientDetail = getClientDetailForChecks(checkSuspense);

			NACHADetail nachaDetail = new NACHADetail(new Segment(), detailSeqNumber, checkSuspense.getSuspenseAmount(), clientDetail,
					PROCESS_TYPE_CHECK);

			nachaDetail.createDetail();

			writeToBufferedWriter(nachaDetail.getFileData().toString());

			accumlationsForDetail(nachaDetail);

			updateSuspense(checkSuspense);

			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_BANK_FOR_NACHA)
					.updateSuccess();
		}
	}

	/**
	 * The BillSchedule was accessed with the Client data - now get it
	 * 
	 * @param segment
	 * @return
	 */
	private ClientDetail getClientDetailForBillData(Segment segment) {
		ContractClient contractClient = ContractClient.findBy_SegmentPK_And_RoleTypeCT(segment.getSegmentPK(),
				"POR")[0];
		// ContractClient contractClient = segment.getContractClient();
		// System.out.println("ContractClientPK: " +
		// contractClient.getContractClientPK());
		ClientDetail clientDetail = ClientDetail.findBy_Segment_RoleType(segment, "POR");

		// ClientRole clientRole =
		// ClientRole.findByPK(contractClient.getClientRoleFK());

		// ClientRole clientRole = contractClient.getClientRole();
		// System.out.println("ClientRolePK: " + clientRole.getClientRolePK());

		// ClientDetail clientDetail = clientRole.getClientDetail();
		// System.out.println("ClientDetailPK: " + clientDetail.getClientDetailPK());

		return clientDetail;
	}

	/**
	 * Each bill schedule processed must have a Premium Trx created, that will
	 * process on the NextBillDueDate
	 * 
	 * @param billSchedule
	 * @param segment
	 */
	private void createPYTrx(BillSchedule billSchedule, Company company, String processDate) throws Exception {
		Segment segment = null;
		try {
			Set segments = billSchedule.getSegments();
			for (Iterator iterator = segments.iterator(); iterator.hasNext();) {
				segment = (Segment) iterator.next();
				if (segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_ACTIVE)
						|| segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_SUBMITTED)
						|| segment.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM)) {
					buildAndSavePYTrx(billSchedule, segment);
				} else {
					// do nothing
					System.out.println("Death Status");
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			String message = "Errored while Creating Premium Trx";

			logErrorToDatabase(e, billSchedule, company, segment, message, processDate);
		}
	}

	/**
	 * Build the transaction
	 * 
	 * @param billSchedule
	 * @param segment
	 * @throws Exception
	 */
	private void buildAndSavePYTrx(BillSchedule billSchedule, Segment segment) throws Exception {
		ContractEvent contractEvent = new ContractEvent();

		GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

		EDITDate effectiveDate = billSchedule.getLastBillDueDate();
		int taxYear = effectiveDate.getYear();
//        BusinessDay bestBusinessDay = new BusinessCalendar().findNextBusinessDay(effectiveDate, 2);
//
//        int taxYear = bestBusinessDay.getBusinessDate().getYear();

//        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(EDITTrx.TRANSACTIONTYPECT_PREMIUM, bestBusinessDay.getBusinessDate(), taxYear, "System");
		EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(EDITTrx.TRANSACTIONTYPECT_PREMIUM, effectiveDate, taxYear,
				"System");
		Bill bill = billSchedule.getBillGroup().getBill();
		groupSetupVO.getContractSetupVO(0).setPolicyAmount(bill.getBilledAmount().getBigDecimal());

		try {
			new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_COMMIT,
					segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * The Suspense was accessed with the Client data - now get it.
	 * 
	 * @param contractSuspense
	 * @return
	 */
	private ClientDetail getClientForContractSuspense(Suspense contractSuspense) {
		EDITTrxHistory editTrxHistory = contractSuspense.getInSuspense().getEDITTrxHistory();

		ContractSetup contractSetup = editTrxHistory.getEDITTrx().getClientSetup().getContractSetup();

		ClientDetail clientDetail = contractSetup.getClientSetup().getClientRole().getClientDetail();

		return clientDetail;
	}

	/**
	 * The Suspense was accessed with the Client data - now get it.
	 * 
	 * @param checkSuspense
	 * @return
	 */
	private ClientDetail getClientDetailForChecks(Suspense checkSuspense) {
		EDITTrxHistory editTrxHistory = checkSuspense.getInSuspense().getEDITTrxHistory();

		ClientRole clientRole = editTrxHistory.getCommissionHistory().getPlacedAgent().getClientRole();

		ClientDetail clientDetail = clientRole.getClientDetail();

		return clientDetail;
	}

	/**
	 * BatchHeader create - ClientCompany being process is used
	 * 
	 * @param processDate
	 * @param company
	 * @return
	 */
	private NACHABatchHeader createBatchHeader(EDITDate processDate, Company company,
			ClientDetail clientCompanyDetail) {
		batchSequenceNumber++;
		formattedBatchSeqNumber = Util.padWithZero(batchSequenceNumber.toString(), 7);

		NACHABatchHeader nachaBatchHeader = new NACHABatchHeader(company.getCompanyPK(), clientCompanyDetail,
				formattedBatchSeqNumber, bankRoutingNumber, draftDateED);
		nachaBatchHeader.createBatchHeader();

		writeToBufferedWriter(nachaBatchHeader.getFileData().toString());

		sumOfAllRecords++;

		return nachaBatchHeader;
	}

	/**
	 * Each batch needs a ClientCompany Detail record before the BatchControl
	 * contains the same batch total for the ClientCompany preference.
	 * 
	 * @param nachaBatchHeader
	 * @param processType
	 */
	private void processClientCompanyDetail(NACHABatchHeader nachaBatchHeader, String processType) {
		ClientDetail clientDetail = nachaBatchHeader.getClientDetail();

		batchDetailCount++;
		detailSeqNumber = Util.padWithZero(batchDetailCount.toString(), 7);

		NACHADetail nachaDetail = new NACHADetail(new Segment(), detailSeqNumber, batchTotalAmount, clientDetail, processType);
		nachaDetail.createClientCompanyDetail();
//		accumlationsForDetail(nachaDetail);
		batchTotalRoutingNumber = batchTotalRoutingNumber.addEditBigDecimal(nachaDetail.getRoutingNumber());

		writeToBufferedWriter(nachaDetail.getFileData().toString());
	}

	/**
	 * BatchControl create - Totals of the batch just processed, sum these total to
	 * the grand total level.
	 * 
	 * @param employerId
	 */
	private void endBatchProcess(String employerId, String processType) {
// batchTotalRoutingNumber = batchTotalRoutingNumber.addEditBigDecimal(new EDITBigDecimal(NACHADetail.CHUBB_ROUTING_NUMBER));

		NACHABatchControl nachaBatchControl = new NACHABatchControl(formattedBatchSeqNumber, detailSeqNumber,
				bankRoutingNumber, batchTotalRoutingNumber, batchTotalAmount, employerId, processType);

		nachaBatchControl.createBatchControl();

		writeToBufferedWriter(nachaBatchControl.getFileData().toString());

		sumOfAllCreditTotals = sumOfAllCreditTotals.addEditBigDecimal(batchTotalAmount);

		sumOfAllDebitTotals = sumOfAllDebitTotals.addEditBigDecimal(batchTotalAmount);

		sumOfAllRoutingNumbers = sumOfAllRoutingNumbers.addEditBigDecimal(batchTotalRoutingNumber);
//		sumOfAllRoutingNumbers = sumOfAllRoutingNumbers.addEditBigDecimal(new EDITBigDecimal(bankRoutingNumber));

		// add in the detail count of the records just processed
		sumOfAllRecords = sumOfAllRecords + batchDetailCount;
		// add in the count of the Batch Control record
		sumOfAllRecords++;
		batchTotalAmount = new EDITBigDecimal();
		batchTotalRoutingNumber = new EDITBigDecimal();
	}

	/**
	 * Create File Header - this uses the Bank Client Information
	 */
	private void getFileHeader() {
		NACHAFileHeader nachaFileHeader = new NACHAFileHeader();

		bankRoutingNumber = nachaFileHeader.getRoutingNumber();

		writeToBufferedWriter(nachaFileHeader.getFileData().toString());

		sumOfAllRecords++;
	}

	/**
	 * Create File Control - the last record on the output file - grand totals of
	 * detail
	 */
	private void processFileControl() {
		checkForEmptyFile();

		NACHAFileControl nachaFileControl = new NACHAFileControl(formattedBatchSeqNumber, sumOfAllRoutingNumbers,
				sumOfAllDebitTotals, sumOfAllCreditTotals, sumOfAllRecords, totalDetailCount);

		nachaFileControl.createFileControl();

		batchDetailCount = 0;

		writeToBufferedWriter(nachaFileControl.getFileData().toString());

	}

	private void checkForEmptyFile() {
		if (formattedBatchSeqNumber == null) {
			formattedBatchSeqNumber = "000000000";
		}

		if (sumOfAllRoutingNumbers == null) {
			sumOfAllRoutingNumbers = new EDITBigDecimal();
		}

		if (sumOfAllDebitTotals == null) {
			sumOfAllDebitTotals = new EDITBigDecimal();
		}

		if (sumOfAllCreditTotals == null) {
			sumOfAllCreditTotals = new EDITBigDecimal();
		}
	}

	/**
	 * Suspense update to marked the records as processed
	 * 
	 * @param suspense
	 */
	public void updateSuspense(Suspense suspense) throws Exception {

		if (suspense.getRemovalReason() != null && suspense.getRemovalReason().equalsIgnoreCase("Refund")) {
			suspense.setMaintenanceInd("R");
			suspense.setDateAppliedRemoved(new EDITDate());
		} else {
			suspense.setMaintenanceInd("D");
		}

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
			suspense.hSave();

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITContractException(e.getMessage());
		}

	}

	private void updateBillSchedule(BillSchedule billSchedule, Company company, String processDate) throws Exception {
//        String billMode = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("BILLFREQUENCY", billSchedule.getBillingModeCT());

//        EDITDate nextBillDueDate = billSchedule.getNextBillDueDate().addMode(billMode);
//        BusinessDay businessDay = new BusinessCalendar().findPreviousBusinessDay(nextBillDueDate, 2);
//
//        billSchedule.setNextBillDueDate(nextBillDueDate);
//        billSchedule.setNextBillExtractDate(businessDay.getBusinessDate());

		Bill bill = billSchedule.getBillGroup().getBill();

		bill.setPaidAmount(bill.getBilledAmount());

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.saveOrUpdate(bill, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			String message = "Failed to update BillSchedule";

			logErrorToDatabase(e, billSchedule, company, null, message, processDate);

			throw new EDITContractException(e.getMessage());
		}
	}

	/**
	 * Accumulate the detail amount and routingNumber for the batch totals
	 * 
	 * @param nachaDetail
	 */
	private void accumlationsForDetail(NACHADetail nachaDetail) {
		// Accumulations
		// System.out.println(nachaDetail.getRoutingNumber());
		if ((nachaDetail == null) || (nachaDetail.getAmount() == null)) {
			System.out.println("natchaDetail.amount is null");
		}

		batchTotalAmount = batchTotalAmount.addEditBigDecimal(nachaDetail.getAmount());

		if ((nachaDetail == null) || (nachaDetail.getRoutingNumber() == null)) {
			System.out.println("natchaDetail.routingNumber is null");
		}
		batchTotalRoutingNumber = batchTotalRoutingNumber.addEditBigDecimal(nachaDetail.getRoutingNumber());
	}

//*************************** Utility methods  *************************************************

	/**
	 * Define the Output File and the buffered writer
	 * 
	 * @throws Exception
	 */
	private void setEFTNACHAFile(String companyName) throws Exception {
		EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

		eftNACHAFile = new File(
				export1.getDirectory() + "VC_NACHA_EFT_" + draftDateED.getYYYYMMDDDateNoDelimiter() + ".txt");

		bufferedWriter = new BufferedWriter(new FileWriter(eftNACHAFile));

	}

	/**
	 * Writing to the file through the buffered writer
	 * 
	 * @param data
	 */
	private void writeToBufferedWriter(String data) {
		try {
			bufferedWriter.write(data);

			bufferedWriter.newLine();

			bufferedWriter.flush();
		} catch (IOException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	public static EDITBigDecimal getBillAmount(Connection connection, Long pk) throws SQLException {

		// String sql = "SELECT sum(billAmount) from vwPremiumDue where segmentPK = " +
		// pk + " ";

//		String sql = "SELECT    sum(billAmount), sum(billAmountOverride) from vwPremiumDue where segmentPK = " + pk + " ";
		String sql = "SELECT    billAmount, billAmountOverride from vwPremiumDue where recordNumber = 1 and segmentPK = "
				+ pk + " ";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		EDITBigDecimal billAmount = new EDITBigDecimal();
		EDITBigDecimal billAmountOverride = new EDITBigDecimal();

		if (resultSet.next()) {
			billAmount = new EDITBigDecimal(resultSet.getBigDecimal(1));
			billAmountOverride = new EDITBigDecimal(resultSet.getBigDecimal(2));
		}

		if (billAmountOverride.isGT(new EDITBigDecimal("0.00"))) {
			return billAmountOverride;
		} else {
			return billAmount;
		}

	}
}
