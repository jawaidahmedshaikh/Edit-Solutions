/*
 * User: sdorman
 * Date: Feb 21, 2008
 * Time: 10:48:21 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.batch;

import edit.services.config.*;
import edit.services.db.hibernate.*;
import edit.services.logging.Logging;
import edit.services.*;
import edit.common.*;

import java.util.*;
import java.io.*;

import event.*;
import billing.*;
import fission.utility.*;
import batch.business.*;
import contract.FilteredProduct;
import engine.Company;
import logging.*;

/**
 * Handles all batch processing associated with cash.
 */
public class CashBatchProcessor {
	/**
	 * 
	 * @param importDate
	 * @param operator
	 * @param username
	 * @param password
	 * @param callMethod
	 * @return
	 */
	public String importCashBatch(String importDate, String operator, String paymentFilename) {

			
		final String FILENAME_EXTENSION = ".txt";

		final int BATCH_REC_TYPE_BEGIN = 0;
		final int BATCH_REC_TYPE_LENGTH = 1;
		final int USERDEF_NUMBER_BEGIN = 1;
		final int USERDEF_NUMBER_LENGTH = 10;
		final int BATCH_ID_BEGIN = 11;
		final int BATCH_ID_LENGTH = 3;
		final int EFFECTIVE_DATE_BEGIN = 14;
		final int EFFECTIVE_DATE_LENGTH = 8;
		final int SUSPENSE_AMOUNT_BEGIN = 22;
		final int SUSPENSE_AMOUNT_LENGTH = 9;
		final int DUE_DATE_BEGIN = 32;
		final int DUE_DATE_LENGTH = 8;
		final int GROUP_NUMBER_BEGIN = 40;
		final int GROUP_NUMBER_LENGTH = 10;
		final int DEPTLOC_BEGIN = 50;
		final int DEPTLOC_LENGTH = 2;

		EditServiceLocator
				.getSingleton()
				.getBatchAgent()
				.getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_BATCH)
				.tagBatchStart(Batch.BATCH_JOB_IMPORT_CASH_BATCH,
						"Cash Batch Import");

		// Get file
	    String filenameBase = "VENUSBatchPayment";
		if ((paymentFilename != null) && !paymentFilename.trim().isEmpty()) {
			filenameBase = paymentFilename;
		}  
		String importPath = ServicesConfig.getUnitValueImport().getDirectory();

		String fileName;;

		String importDateWithoutDelimiters = DateTimeUtil
				.getMonthFromMMDDYYYY(importDate)
				+ DateTimeUtil.getDayFromMMDDYYYY(importDate)
				+ DateTimeUtil.getYearFromMMDDYYYY(importDate);

			
	    fileName = filenameBase + importDateWithoutDelimiters
				+ FILENAME_EXTENSION;

		String absoluteImportFileName = importPath + fileName;

		String[] lines = new String[0];

		Company company = null;
		StringBuilder logMessage = null;
		boolean processedWithErrors = false;

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			CashBatchContract cashBatchContract = null;
			// BillGroup billGroup = null;

			// Read import file
			lines = UtilFile.readFile(absoluteImportFileName);

			if (lines.length > 0) {
				// Each line represents 1 suspense entry. Only the first line is
				// used for the CashBatchContract
				// The last line contains summary information, don't process it.
				for (int i = 0; i < lines.length - 1; i++) {
					String line = lines[i];
					
					String batchRecordType = line.substring(BATCH_REC_TYPE_BEGIN,
							BATCH_REC_TYPE_BEGIN + BATCH_REC_TYPE_LENGTH)
							.trim();

					String userDefNumber = line.substring(USERDEF_NUMBER_BEGIN,
							USERDEF_NUMBER_BEGIN + USERDEF_NUMBER_LENGTH)
							.trim();
					String batchID = line.substring(BATCH_ID_BEGIN,
							BATCH_ID_BEGIN + BATCH_ID_LENGTH).trim();
					String effectiveDateString = line.substring(
							EFFECTIVE_DATE_BEGIN,
							EFFECTIVE_DATE_BEGIN + EFFECTIVE_DATE_LENGTH)
							.trim();
					String suspenseAmountString = line.substring(
							SUSPENSE_AMOUNT_BEGIN,
							SUSPENSE_AMOUNT_BEGIN + SUSPENSE_AMOUNT_LENGTH)
							.trim();
					String dueDateString = line.substring(DUE_DATE_BEGIN,
							DUE_DATE_BEGIN + DUE_DATE_LENGTH).trim();
					String groupNumber = line.substring(GROUP_NUMBER_BEGIN,
							GROUP_NUMBER_BEGIN + GROUP_NUMBER_LENGTH).trim();
					// String deptLocation = line.substring(DEPTLOC_BEGIN,
					// DEPTLOC_BEGIN + DEPTLOC_LENGTH).trim();

					// deck: adding logging message for group/contract problems
					logMessage = new StringBuilder();
					logMessage.append("batchRecordType: ");
					logMessage.append(batchRecordType);
					logMessage.append("userDefNumber: ");
					logMessage.append(userDefNumber);
					logMessage.append(" - ");
					logMessage.append("groupNumber: ");
					logMessage.append(groupNumber);

					EDITDate effectiveDate = DateTimeUtil
							.getEDITDateFromMMDDYYYY(DateTimeUtil
									.insertDateDelimiterForMMDDYYYY(effectiveDateString));
					EDITDate dueDate = DateTimeUtil
							.getEDITDateFromMMDDYYYY(DateTimeUtil
									.insertDateDelimiterForMMDDYYYY(dueDateString));

					EDITBigDecimal suspenseAmount = new EDITBigDecimal(
							suspenseAmountString, 2);
					suspenseAmount = suspenseAmount
							.divideEditBigDecimal(new EDITBigDecimal("100.00")); // divide
																					// by
																					// 100
																					// to
																					// get
																					// currency
																					// (ex.
																					// 3468
																					// in
																					// file
																					// is
																					// 34.68)

					FilteredProduct filteredProduct = FilteredProduct
							.findBy_GroupNumber(groupNumber);
					// Skip records with invalid group numbers:
					if (filteredProduct == null) {

						EDITMap columnInfo = new EDITMap();
						columnInfo.put("UserDefNumber", userDefNumber);
						columnInfo.put("GroupNumber", groupNumber);
						columnInfo.put("ProcessDate",
								new EDITDate().getFormattedDate());
						columnInfo.put("Operator", operator);

						int lineNumber = i + 1;

						String message = "Invalid Group Number - Record at line "
								+ lineNumber
								+ " was not processed in file: "
								+ fileName;
						Log.logToDatabase(Log.CASH_BATCH, message, columnInfo);
						processedWithErrors = true;

						continue;
					}
					company = Company.findByProductStructurePK(filteredProduct
							.getProductStructureFK());

					if (i == 0) {
						// It's the first line, create the CashBatchContract and
						// BillGroup
						cashBatchContract = createCashBatchContract(batchRecordType, batchID,
								dueDate, groupNumber, operator);

						// The next few lines are commented out. We are not
						// dealing with BillGroups for phase 1.
						// Find the associated BillGroup and set its released
						// date
						// billGroup =
						// BillGroup.findByDueDate_GroupNumber(dueDate,
						// groupNumber);
						// billGroup.setReleaseDate(effectiveDate);

					}

					// Create Suspense
					Suspense suspense = createSuspense(batchRecordType, userDefNumber,
							effectiveDate, suspenseAmount, operator, batchID);

					// Update CashBatchContract
					cashBatchContract.addSuspense(suspense);
					cashBatchContract.increaseAmount(suspenseAmount);
					cashBatchContract.increaseTotalBatchItems(1);
				}

				// Process the last line which contains summary info
				String lastLine = lines[lines.length - 1];

				processLastLineCashBatchImport(lastLine, cashBatchContract,
						operator, absoluteImportFileName);

				// Save to db
				SessionHelper.saveOrUpdate(cashBatchContract,
						SessionHelper.EDITSOLUTIONS);

				// The next line is commented out. We are not dealing with
				// BillGroups for phase 1.
				// SessionHelper.saveOrUpdate(billGroup,
				// SessionHelper.EDITSOLUTIONS);

				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

				if (processedWithErrors) {
					EditServiceLocator.getSingleton().getBatchAgent()
							.getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_BATCH)
							.updateFailure();

					return Batch.PROCESSED_WITH_ERRORS;

				} else {
					EditServiceLocator.getSingleton().getBatchAgent()
							.getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_BATCH)
							.updateSuccess();
				}
			}
		} catch (NullPointerException npe) {
			String message = "Cash Batch Import: NullPointerException: "
					+ logMessage + " - " + npe.getMessage();
			// npe.printStackTrace();

			Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
					new LogEvent(npe));
			Log.logGeneralExceptionToDatabase(message, npe);

			EditServiceLocator.getSingleton().getBatchAgent()
					.getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_BATCH)
					.updateFailure();

			return Batch.PROCESSED_WITH_ERRORS;

		} catch (IOException ioException) {
			// ioException.printStackTrace();

			String companyName = "";
			if (company != null) {
				companyName = company.getCompanyName();
			}

			EDITMap columnInfo = new EDITMap();
			columnInfo.put("Operator", operator);
			columnInfo.put("ProcessDate", new EDITDate().getFormattedDate());
			columnInfo.put("CompanyName", companyName);

			String message = "Cash Batch Import: IOException reading file "
					+ fileName + ". " + ioException.getMessage();
			Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);

			EditServiceLocator.getSingleton().getBatchAgent()
					.getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_BATCH)
					.updateSuccess();

			return Batch.PROCESSED_BUT_NO_ELEMENTS_FOUND;

		} catch (Exception e) {
			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			String message = "Cash Batch Import: Error processing suspense. "
					+ e.getMessage();

			System.out.println(e);

			e.printStackTrace();

			String companyName = "";
			if (company != null) {
				companyName = company.getCompanyName();
			}

			EDITMap columnInfo = new EDITMap();
			columnInfo.put("Operator", operator);
			columnInfo.put("ProcessDate", new EDITDate().getFormattedDate());
			columnInfo.put("CompanyName", companyName);

			Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
			Log.logGeneralExceptionToDatabase(message, e);

			EditServiceLocator.getSingleton().getBatchAgent()
					.getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_BATCH)
					.updateFailure();

			return Batch.PROCESSED_WITH_ERRORS;
		} finally {
			EditServiceLocator.getSingleton().getBatchAgent()
					.getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_BATCH)
					.tagBatchStop();

		}

		return Batch.PROCESSED_WITHOUT_ERRORS;
	}

	/**
	 * Processes the last line of the cash batch import file. The last line
	 * contains summary information. The total suspense amount in the summary
	 * should equal the total of all suspense amounts that were processed in the
	 * preceding lines. If they are not equal, a warning message is placed in
	 * the batch log.
	 * 
	 * @param lastLine
	 *            last line of the import file which contains the summary
	 *            information to be read
	 * @param cashBatchContract
	 *            CashBatchContract object which contains the total suspense
	 *            amount processed
	 * @param operator
	 *            current operator (or user)
	 * @param fileName
	 *            filename of the import file. Used as information for the
	 *            warning message
	 */
	private void processLastLineCashBatchImport(String lastLine,
			CashBatchContract cashBatchContract, String operator,
			String fileName) {
		final int SUSPENSE_AMOUNT_BEGIN = 22;
		final int SUSPENSE_AMOUNT_LENGTH = 9;

		String totalSuspenseAmountString = lastLine.substring(
				SUSPENSE_AMOUNT_BEGIN,
				SUSPENSE_AMOUNT_BEGIN + SUSPENSE_AMOUNT_LENGTH).trim();

		EDITBigDecimal totalSuspenseAmount = new EDITBigDecimal(
				totalSuspenseAmountString, 2);
		totalSuspenseAmount = totalSuspenseAmount
				.divideEditBigDecimal(new EDITBigDecimal("100.00")); // divide
																		// by
																		// 100
																		// to
																		// get
																		// currency
																		// (ex.
																		// 3468
																		// in
																		// file
																		// is
																		// 34.68)

		if (!cashBatchContract.getAmount().isEQ(totalSuspenseAmount)) {
			// The summary's total suspense amount does not equal the total of
			// all suspense amounts processed
			// Put a warning message in the batch log
			EDITMap columnInfo = new EDITMap();
			columnInfo.put("Operator", operator);
			columnInfo.put("ProcessDate", new EDITDate().getFormattedDate());

			String message = "Cash Batch Import: The total suspense amount in the summary is not equal to the total of all suspense amounts processed.  File: "
					+ fileName;
			Log.logToDatabase(Log.EXECUTE_TRANSACTION, message, columnInfo);
		}
	}

	/**
	 * Creates a CashBatchContract object with the specified values and some
	 * defaults
	 * 
	 * @param batchID
	 * @param dueDate
	 * @param groupNumber
	 * @param operator
	 * 
	 * @return CashBatchContract object
	 */
	private CashBatchContract createCashBatchContract(String batchRecordType, String batchID,
			EDITDate dueDate, String groupNumber, String operator) {
		CashBatchContract cashBatchContract = new CashBatchContract();

		cashBatchContract.setBatchRecordType(batchRecordType);
		cashBatchContract.setBatchID(batchID);
		cashBatchContract.setDueDate(dueDate);
		cashBatchContract.setGroupNumber(groupNumber);
		cashBatchContract.setCreationDate(new EDITDate());
		cashBatchContract.setCreationOperator(operator);
		cashBatchContract
				.setReleaseIndicator(CashBatchContract.RELEASE_INDICATOR_RELEASE);
		cashBatchContract
				.setAccountingPendingIndicator(CashBatchContract.ACCOUNTING_PENDING_INDICATOR_YES);
		cashBatchContract.setAmount(new EDITBigDecimal());
		cashBatchContract.setTotalBatchItems(0);

		return cashBatchContract;
	}

	/**
	 * Creates a Suspense object with the specified values and some defaults
	 * 
	 * @param userDefNumber
	 * @param effectiveDate
	 * @param suspenseAmount
	 * @param operator
	 * 
	 * @return Suspense object
	 */
	private Suspense createSuspense(String batchRecordType, String userDefNumber,
			EDITDate effectiveDate, EDITBigDecimal suspenseAmount,
			String operator, String batchID) {
		Suspense suspense = new Suspense();

		suspense.setUserDefNumber(userDefNumber);
		suspense.setEffectiveDate(effectiveDate);
		suspense.setSuspenseAmount(suspenseAmount);
		suspense.setOriginalAmount(suspenseAmount);
		suspense.setPendingSuspenseAmount(new EDITBigDecimal());
		if (batchRecordType.equals("L")) {
		    suspense.setPremiumTypeCT(Suspense.PREMIUMTYPECT_LOAN_REPAYMENT);
		} else {
		    suspense.setPremiumTypeCT(Suspense.PREMIUMTYPECT_CASH);
		}
		suspense.setDepositTypeCT(Suspense.DEPOSITTYPECT_CASH);
		suspense.setDirectionCT(Suspense.DIRECTIONCT_APPLY);
		suspense.setAccountingPendingInd(Suspense.ACCOUNTING_PENDING_IND_YES);
		suspense.setMaintenanceInd(Suspense.MAINTENANCE_IND_M);
		suspense.setSuspenseType(Suspense.SUSPENSETYPE_CONTRACT);
		suspense.setTaxYear(effectiveDate.getYear());
		suspense.setOriginalContractNumber(batchID);
		suspense.setContractPlacedFrom(Suspense.CONTRACT_PLACED_FROM_BATCH);
		suspense.setProcessDate(new EDITDate());
		suspense.setOperator(operator);
		suspense.setMaintDateTime(new EDITDateTime());

		return suspense;
	}
}
