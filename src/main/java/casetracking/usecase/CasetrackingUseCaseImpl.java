/*
 * User: sprasad
 * Date: Apr 8, 2005
 * Time: 2:00:14 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package casetracking.usecase;

import casetracking.CaseRequirement;
import casetracking.CasetrackingLog;
import casetracking.CasetrackingNote;
import casetracking.Quote;
import client.ClientDetail;
import contract.*;
import contract.ChangeHistory;
import edit.common.*;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.*;
import edit.services.db.hibernate.SessionHelper;
import edit.portal.exceptions.*;
import event.*;
import event.financial.contract.trx.*;
import event.transaction.*;
import role.ClientRole;

import java.text.SimpleDateFormat;
import java.util.*;

import agent.*;
import fission.utility.*;
import engine.*;

public class CasetrackingUseCaseImpl implements CasetrackingUseCase {
	private static final String CLIENT_DECEASED = "Deceased";
	private static final String CLIENT_DECEASED_PENDING = "DeceasedPending";
	private static final String DEATH_TRX = "DE";
	private static final String DEATH_PENDING_TRX = "DP";
	private static final String DEATH_PENDING_REVERSAL_TRX = "DPR";
	private static final String DEATH = "Death";
	private static final String DEATH_PENDING = "DeathPending";
	private static final String DEATH_PENDING_REVERSAL = "DeathPendingReversal";
	private static final String BENE_CHANGE_TERMINATION = "BeneChg TerminationDate";
	private static final String BENE_CHANGE_ALLOCATION_PCT = "BeneChg AllocationPct";
	private static final String BENE_ADD = "Beneficiary Add";
	private static final String RIDER_CLAIM = "RCL";

	/**
	 * Security method to access CaseTracking
	 */
	public void accessCaseTracking() {

	}

	/**
	 * Saves new/existing Casetracking Note
	 * 
	 * @param casetrackingNote
	 * @param clientDetailPK
	 */
	public void saveOrUpdateNote(CasetrackingNote casetrackingNote,
			Long clientDetailPK) {
		ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		casetrackingNote.setMaintDateTime(new EDITDateTime());

		clientDetail.addCasetrackingNote(casetrackingNote);

		SessionHelper.saveOrUpdate(casetrackingNote,
				SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		SessionHelper.clearSessions();
	}

	/**
	 * Deletes the existing casetracking Note
	 * 
	 * @param casetrackingNotePK
	 */
	public void deleteNote(Long casetrackingNotePK) {
		CasetrackingNote casetrackingNote = CasetrackingNote
				.findByPK(casetrackingNotePK);

		ClientDetail clientDetail = casetrackingNote.getClientDetail();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		/*
		 * Before deleting the child entity from the database delete
		 * (disassociate from the parent) it from the parent's collection (if
		 * the parent is loaded in hibernate sessions) otherwise hibernate
		 * complains with the following maessage .....
		 * org.hibernate.ObjectDeletedException: deleted object would be
		 * re-saved by cascade (remove deleted object from associations):
		 */
		clientDetail.deleteCasetrackingNote(casetrackingNote);

		SessionHelper.delete(casetrackingNote, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		SessionHelper.clearSessions();
	}

	/**
	 * Set the client status to the appropriate death status and update the
	 * client detail
	 * 
	 * @param clientDetail
	 * @param transactionType
	 * @return
	 */
	public ClientDetail updateClientDeathStatus(ClientDetail clientDetail,
			String transactionType) {
		if (transactionType.equalsIgnoreCase(DEATH_TRX)) {
			if (editDateOfDeath(clientDetail)) {
				clientDetail.setStatusCT(CLIENT_DECEASED);
			}
		} else if (transactionType.equalsIgnoreCase(DEATH_PENDING_TRX)) {
			clientDetail.setStatusCT(CLIENT_DECEASED_PENDING);
		}
		
		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		SessionHelper.saveOrUpdate(clientDetail, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		return clientDetail;
	}

	/**
	 * The date of death must be less than the current date, in order to update
	 * the Client Status on the Client Detail to 'deceased'.
	 * 
	 * @param clientDetail
	 * @return
	 */
	private boolean editDateOfDeath(ClientDetail clientDetail) {
		boolean validDate = false;

		EDITDate currentDate = new EDITDate();
		if (clientDetail.getDateOfDeath().before(currentDate)) {
			validDate = true;
		}

		return validDate;
	}

	/**
	 * Based on the event in the casetrackingLog, process the event selected.
	 * Three events are possible, deathpending, deathPending reversal and death.
	 * Then save the log record.
	 * 
	 * @param casetrackingLog
	 * @param contractClientPK
	 */
	public void deathTransactionProcess(CasetrackingLog casetrackingLog,
			Long contractClientPK, String condtionCT) throws Exception,
			PortalEditingException {
		ContractClient contractClient = ContractClient
				.findByPK(contractClientPK);

		String transactionType = casetrackingLog.getCaseTrackingEvent();
		String operator = casetrackingLog.getOperator();

		ClientDetail clientDetail = casetrackingLog.getClientDetail();
		EDITDate currentDate = new EDITDate();
		//EDITDate effectiveDate = getEffectiveDate(clientDetail, contractClient);
		EDITDate effectiveDate = new EDITDate();
		EDITTrx editTrx = null;

		if (transactionType.equalsIgnoreCase(DEATH_TRX)) {
			casetrackingLog.setCaseTrackingEvent(DEATH);
			try {
				// Save record with editTrx.effectiveDate = currentDate
				editTrx = saveDeathTransaction(operator, currentDate,
						contractClient, condtionCT,
						clientDetail.getDateOfDeath());

			} catch (EDITEventException e) {
				System.out.println(e);

				e.printStackTrace();
			}
		} else if (transactionType.equalsIgnoreCase(DEATH_PENDING_TRX)) {
			casetrackingLog.setCaseTrackingEvent(DEATH_PENDING);
			try {
				editTrx = saveDeathPendingTransaction(operator, effectiveDate,
						contractClient, condtionCT);
			} catch (EDITEventException e) {
				System.out.println(e);

				e.printStackTrace();
			}
		} else if (transactionType.equalsIgnoreCase(DEATH_PENDING_REVERSAL_TRX)) {
			casetrackingLog.setCaseTrackingEvent(DEATH_PENDING_REVERSAL);
			deathPendingReversalProcess(operator, effectiveDate, contractClient);
		}

		setClientData(casetrackingLog, clientDetail);
		casetrackingLog.setEffectiveDate(effectiveDate);
		saveLogEntry(casetrackingLog);

		SessionHelper.clearSessions();

		if (transactionType.equalsIgnoreCase(DEATH_TRX)
				|| transactionType.equalsIgnoreCase(DEATH_PENDING_TRX)) {
			try {
				// DeathTrx deathTrx = new DeathTrx();
				DeathTrx.checkForExecutionOfDeathTrx(editTrx);
			} catch (EDITEventException e) {
				System.out.println(e);

				e.printStackTrace();
				throw (e);
			} catch (PortalEditingException e) {
				System.out.println(e);

				e.printStackTrace();
				throw (e);
			} catch (Exception e) {
				System.out.println(e);

				e.printStackTrace();
				throw (e);
			}
		}

		SessionHelper.clearSessions();
	}

	private EDITDate getEffectiveDate(ClientDetail clientDetail,
			ContractClient contractClient) {
		Segment segment = contractClient.getSegment();
		EDITDate currentDate = new EDITDate();
		EDITDate effectiveDate = null;

		Area area = new Area(segment.getProductStructureFK().longValue(),
				clientDetail.getResidentStateAtDeathCT(), "DEATHRULES",
				currentDate, "*");

		AreaValue areaValue = area.getAreaValue("DEATHDATE");

		// for (int i = 0; i < areaValues.length; i++)
		// {
		if (areaValue != null) {
			if (areaValue.get_AreaValue().equalsIgnoreCase("dateofdeath")) {
				effectiveDate = clientDetail.getDateOfDeath();
			} else if (areaValue.get_AreaValue().equalsIgnoreCase(
					"proofofdeath")) {
				effectiveDate = clientDetail.getProofOfDeathReceivedDate();
			} else if (areaValue.get_AreaValue().equalsIgnoreCase(
					"notificationofdeath")) {
				effectiveDate = clientDetail.getNotificationReceivedDate();
			}
		}
		// }

		if (effectiveDate == null
				|| effectiveDate
						.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE))) {
			effectiveDate = new EDITDate();
		}
		Date dt = new Date();
		effectiveDate = new EDITDate();
		return effectiveDate;
	}

	/**
	 * The CaseTrackingProcess field is updated to the client record as soon as
	 * it is selected
	 * 
	 * @param clientDetail
	 */
	public void updateClientProcess(ClientDetail clientDetail) {
		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		SessionHelper.saveOrUpdate(clientDetail, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * Create a financial transaction DE, for the ContractClient selected Once
	 * saved check for realtime execution
	 * 
	 * @param operator
	 * @param effectiveDate
	 * @param contractClient
	 */
	public EDITTrx saveDeathTransaction(String operator,
			EDITDate effectiveDate, ContractClient contractClient,
			String conditionCT, EDITDate dateOfDeath) throws EDITEventException {
		// create EDITTrx and save it using the parent
		Segment segment = contractClient.getSegment();
		DeathTrx deathTrx = new DeathTrx(segment);
		
		effectiveDate = new EDITDate();
		EDITTrx editTrx = deathTrx.createDeathEDITTrx(operator, effectiveDate,
				contractClient, conditionCT, dateOfDeath);

		GroupSetup groupSetup = editTrx.getClientSetup().getContractSetup()
				.getGroupSetup();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		SessionHelper.saveOrUpdate(groupSetup, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		return editTrx;
		// use the Crud method to process realtime - temporarily deactivated
		// try
		// {
		// deathTrx.checkForExecutionOfDeathTransaction(operator, editTrx,
		// segment);
		// }
		// catch (EDITEventException e)
		// {
		// System.out.println(e);

		// e.printStackTrace();
		// }
	}

	/**
	 * Create a financial transaction DP, for the ContractClient selected Once
	 * saved check for realtime execution
	 * 
	 * @param operator
	 * @param effectiveDate
	 * @param contractClient
	 */
	public EDITTrx saveDeathPendingTransaction(String operator,
			EDITDate effectiveDate, ContractClient contractClient,
			String conditionCT) throws EDITEventException {
		// create EDITTrx and save it using the parent
		Segment segment = contractClient.getSegment();
		DeathTrx deathTrx = new DeathTrx(segment);
		EDITTrx editTrx = deathTrx.createDeathPendingEDITTrx(operator,
				effectiveDate, contractClient, conditionCT);

		GroupSetup groupSetup = editTrx.getClientSetup().getContractSetup()
				.getGroupSetup();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		SessionHelper.saveOrUpdate(groupSetup, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		return editTrx;
	}

	/**
	 * Create a financial transaction RCL, for the Contract selected Once saved
	 * check for realtime execution
	 * 
	 * @param operator
	 * @param effectiveDate
	 * @param segmentPK
	 *            (the PK for the base segment of the selected contract
	 * @param riderName
	 *            (the rider name indicating the type of claim being processed)
	 * @param careType
	 *            (the type of care - for LTC and TI claims)
	 * @param dateOfDeath
	 * @param authorizedSignatureCT
	 */
	public void saveRiderClaimTrx(String operator, EDITDate effectiveDate,
			Long segmentPK, String riderName, String careType,
			EDITDate dateOfDeath, String claimType, String conditionCT,
			String authorizedSignatureCT, EDITBigDecimal amountOverride,
			EDITBigDecimal interestOverride) throws EDITEventException {
		// create EDITTrx and save it using the parent
		Segment segment = Segment.findByPK(segmentPK);
		ContractClient contractClient = segment.getOwnerContractClient();

		EDITTrx.createRiderClaimEDITTrx(segment, operator, effectiveDate,
				contractClient.getContractClientPK(),
				contractClient.getClientRoleFK(), riderName, careType,
				dateOfDeath, claimType, conditionCT, authorizedSignatureCT,
				amountOverride, interestOverride);
	}

	/**
	 * Change the contract status for the contract selected - produce change
	 * history
	 * 
	 * @param operator
	 * @param effectiveDate
	 * @param contractClient
	 */
	public void deathPendingProcess(String operator, EDITDate effectiveDate,
			ContractClient contractClient) {
		Segment segment = contractClient.getSegment();

		segment.setSegmentStatusCT("DeathPending");
		segment.setOperator(operator);

		if (effectiveDate == null
				|| effectiveDate
						.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE))) {
			effectiveDate = new EDITDate();
		}

		segment.setChangeHistoryEffDate(effectiveDate);

		// HIBERNATE will track change on the segment now
		// segment.checkForNonFinancialChanges();

		// Save the segment with the status change
		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * Create and save to db the Overrides needed for the transaction,
	 * ContractClientAllocation override and the Withholding for the LumpSum
	 * transaction being created. Once saved check for realtime execution
	 * 
	 * @param casetrackingLog
	 * @param contractClientKey
	 * @param taxYear
	 * @param amount
	 * @param withholding
	 * @param segment
	 * @param isSuppContract
	 *            Is this Lump Sum transaction going to be created with
	 *            WithdrawalType set to Supplemental Contract
	 */
	public void saveLumpSumTransaction(CasetrackingLog casetrackingLog,
			Long contractClientKey, int taxYear, EDITBigDecimal amountOverride,
			EDITBigDecimal interestOverride, String zeroInterestIndicator,
			Withholding withholding, Charge[] charges, Segment segment,
			boolean isSuppContract, int sequenceNumber)
			throws EDITEventException, PortalEditingException, Exception {
		ContractClient contractClient = ContractClient
				.findByPK(contractClientKey);
		ClientDetail clientDetail = contractClient.getClientRole()
				.getClientDetail();

		EDITDate effectiveDate = casetrackingLog.getEffectiveDate();
		String operator = casetrackingLog.getOperator();
		EDITBigDecimal allocationPct = casetrackingLog.getAllocationPercent();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		// Update the segment with total number of Active Primary Beneficiaries
		update_SaveOriginalSegment(segment);

		LumpSumTrx lumpSumTrx = new LumpSumTrx(segment, contractClient);

		EDITTrx editTrx = lumpSumTrx.createLumpSumTrx(operator, effectiveDate,
				taxYear, amountOverride, interestOverride,
				zeroInterestIndicator, withholding, charges, allocationPct,
				isSuppContract, sequenceNumber);

		editTrx.setNewPolicyNumber(casetrackingLog.getNewContractNumber());

		GroupSetup groupSetup = editTrx.getClientSetup().getContractSetup()
				.getGroupSetup();
		SessionHelper.saveOrUpdate(groupSetup, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		// save log entry for lump sum
		casetrackingLog.setContractNumber(segment.getContractNumber());
		setClientData(casetrackingLog, clientDetail);
		saveLogEntry(casetrackingLog);

		SessionHelper.clearSessions();

		try {
			lumpSumTrx.checkForExecutionOfLumpSumTrx(editTrx);
		} catch (EDITEventException e) {
			if (e.getErrorNumber() == EDITEventException.CONSTANT_NO_DATA_FOUND) {
				// at this point of time only want to throw specific exceptions
				// with above ErrorNumber.
				throw e;
			}
		} catch (PortalEditingException e) {
			System.out.println(e);

			e.printStackTrace();
			throw (e);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();
			throw (e);
		} finally {
			SessionHelper.clearSessions();
		}
		// //save log entry for lump sum
		// casetrackingLog.setContractNumber(segment.getContractNumber());
		// setClientData(casetrackingLog, clientDetail);
		// saveLogEntry(casetrackingLog);

	}

	/**
	 * With the parameters passed in, update the contractClient selected and
	 * create a log reocrd for the event. ChangeHistory is also produced for the
	 * contractClient changes.
	 * 
	 * @param contractClientPK
	 *            selected contractClient to update
	 * @param relationship
	 *            selected value with which to update selected contractClient
	 * @param terminationDate
	 *            selected value with which to update selected contractClient
	 * @param terminationReason
	 *            selected value with which to update selected contractClient
	 * @param casetrackingLog
	 *            logging information
	 * @param splitEqualInd
	 *            allocation Ind
	 */
	public void updateExistingBeneficiary(Long contractClientPK,
			String relationship, EDITDate terminationDate,
			String terminationReason, CasetrackingLog casetrackingLog,
			String splitEqualInd, EDITBigDecimal allocationAmount) {
		ContractClient contractClient = ContractClient
				.findByPK(contractClientPK);

		Segment segment = contractClient.getSegment();
		EDITBigDecimal allocationPercent = casetrackingLog
				.getAllocationPercent();

		// Need the current value of the termination value for log entry
		// creation
		EDITDate priorTerminationDate = contractClient.getTerminationDate();

		contractClient = segment.updateExistingBeneficiary(contractClient,
				relationship, terminationDate, terminationReason,
				casetrackingLog.getOperator(), allocationPercent,
				splitEqualInd, allocationAmount);

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		SessionHelper.saveOrUpdate(contractClient, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		saveCasetrackingLogForExistingBene(contractClient,
				priorTerminationDate, casetrackingLog);

		SessionHelper.clearSessions();
	}

	/**
	 * Populate further the casetracking Log for the ExistingBene event and save
	 * it
	 * 
	 * @param contractClient
	 * @param terminationDate
	 * @param casetrackingLog
	 */
	private void saveCasetrackingLogForExistingBene(
			ContractClient contractClient, EDITDate terminationDate,
			CasetrackingLog casetrackingLog) {
		if (contractClient.getTerminationDate().equals(terminationDate)) {
			casetrackingLog.setCaseTrackingEvent(BENE_CHANGE_ALLOCATION_PCT);
		} else {
			casetrackingLog.setCaseTrackingEvent(BENE_CHANGE_TERMINATION);
		}

		casetrackingLog.setContractNumber(contractClient.getSegment()
				.getContractNumber());
		casetrackingLog.setEffectiveDate(new EDITDate());

		ClientDetail clientDetail = contractClient.getClientDetail();
		setClientData(casetrackingLog, clientDetail);

		saveLogEntry(casetrackingLog);
	}

	/**
	 * With the parameters passed in, create the new ClientRole and
	 * ContractClient for the client selected. This event is tracked by the
	 * CasetrackingLog and ChangeHistory.
	 * 
	 * @param clientDetailPK
	 * @param segmentPK
	 * @param beneRole
	 * @param contractClient
	 * @param casetrackingLog
	 */
	public void createNewBeneficiary(Long clientDetailPK, Long segmentPK,
			String beneRole, ContractClient contractClient,
			CasetrackingLog casetrackingLog, String splitEqualInd,
			EDITBigDecimal allocationAmount) {
		ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);
		Segment segment = Segment.findByPK(segmentPK);

		String operator = casetrackingLog.getOperator();
		EDITBigDecimal allocationPercent = casetrackingLog
				.getAllocationPercent();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		segment.createNewBeneficiary(clientDetail, beneRole, contractClient,
				operator, allocationPercent, splitEqualInd, allocationAmount);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		saveCasetrackingLogForNewBene(clientDetail,
				segment.getContractNumber(), casetrackingLog);

		SessionHelper.clearSessions();
	}

	/**
	 * Populate futher thecasetrackingLog for the New Bene event and save it.
	 * 
	 * @param clientDetail
	 * @param contractNumber
	 * @param casetrackingLog
	 */
	private void saveCasetrackingLogForNewBene(ClientDetail clientDetail,
			String contractNumber, CasetrackingLog casetrackingLog) {
		casetrackingLog.setCaseTrackingEvent(BENE_ADD);

		casetrackingLog.setContractNumber(contractNumber);
		casetrackingLog.setEffectiveDate(new EDITDate());

		setClientData(casetrackingLog, clientDetail);

		saveLogEntry(casetrackingLog);
	}

	/**
	 * The current owner will be terminated - ContractClient termination date is
	 * set to the effectiveDate entered minus one. Then the ContractClient
	 * selected becomes the owner.
	 * 
	 * @param casetrackingLog
	 * @param beneContractClientPK
	 */
	public void processSpousalContinuation(CasetrackingLog casetrackingLog,
			Long beneContractClientPK) {
		EDITDate effectiveDate = casetrackingLog.getEffectiveDate();
		String effDateAsString = effectiveDate.getFormattedDate();

		String operator = casetrackingLog.getOperator();

		ContractClient beneContractClient = ContractClient
				.findByPK(beneContractClientPK);
		beneContractClient.setEffectiveDate(effectiveDate);

		Segment segment = beneContractClient.getSegment();
		segment.setOperator(operator);
		segment.setChangeHistoryEffDate(effectiveDate);
		EDITDate terminationDate = new EDITDate(effDateAsString)
				.subtractDays(1);

		ClientDetail beneClientDetail = beneContractClient.getClientDetail();
		EDITDate beneDateOfBirth = beneClientDetail.getBirthDate();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		// get current owner and determine Natural Status
		ContractClient ownerContractClient = segment.getOwnerContractClient();
		ClientDetail ownerClientDetail = ownerContractClient.getClientRole()
				.getClientDetail();

		boolean annuitantEqual = false;
		ContractClient annContractClient = segment.getAnnuitantContractClient();
		if (annContractClient != null) {
			annuitantEqual = isOwnerAnnEqual(annContractClient,
					ownerContractClient);
		}

		boolean naturalClientStatus = ownerClientDetail.isStatusNatural();

		// the gender must be male or female for this boolean to be true
		// terminate current owner make bene new owner
		if (naturalClientStatus) {
			ownerContractClient.setTerminationDate(terminationDate);
			ownerContractClient
					.setTerminationReasonCT(ContractClient.SPOUSAL_TERMINATION_REASON);

			// The segment entity has been set into this ownerContractClient
			ownerContractClient.setSegment(ownerContractClient.getSegment());
			ownerContractClient.checkForNonFinancialChanges();
			SessionHelper.saveOrUpdate(ownerContractClient,
					SessionHelper.EDITSOLUTIONS);

			// Update the roleType for the new owner
			ClientRole clientRole = beneClientDetail
					.findOrCreateRoles(ClientRole.ROLETYPECT_OWNER);
			beneContractClient.setClientRole(clientRole);
			beneContractClient.setSegment(beneContractClient.getSegment());
			beneContractClient.checkForNonFinancialChanges();
			SessionHelper.saveOrUpdate(beneContractClient,
					SessionHelper.EDITSOLUTIONS);
		}

		// terminate current annuitant, if one exists and same as owner
		if (annContractClient != null && annuitantEqual) {
			annContractClient.setTerminationDate(terminationDate);
			annContractClient
					.setTerminationReasonCT(ContractClient.SPOUSAL_TERMINATION_REASON);

			annContractClient.setSegment(annContractClient.getSegment());
			annContractClient.checkForNonFinancialChanges();
			SessionHelper.saveOrUpdate(annContractClient,
					SessionHelper.EDITSOLUTIONS);

			// create new role of annuitant for the bene when the deceased also
			// was the annuitant
			ClientRole clientRole = beneClientDetail
					.findOrCreateRoles(ClientRole.ROLETYPECT_ANNUITANT);
			ContractClient newContractClient = segment.formatContractClient(
					clientRole, effectiveDate, "P");
			segment.addContractClient(newContractClient);
			newContractClient.checkForNonFinancialChanges();
		} else if (!naturalClientStatus) {
			// for the non-natural entity, the bene becomes the annuitant, for
			// the one that just died
			annContractClient.setTerminationDate(terminationDate);
			annContractClient
					.setTerminationReasonCT(ContractClient.SPOUSAL_TERMINATION_REASON);

			annContractClient.setSegment(annContractClient.getSegment());
			annContractClient.checkForNonFinancialChanges();
			SessionHelper.saveOrUpdate(annContractClient,
					SessionHelper.EDITSOLUTIONS);

			ClientRole clientRole = (ClientRole) beneContractClient
					.getClientRole();
			clientRole.setRoleTypeCT(ClientRole.ROLETYPECT_ANNUITANT);
			clientRole.checkForNonFinancialChanges(segment.getOperator());
			beneContractClient.setSegment(beneContractClient.getSegment());
			SessionHelper.saveOrUpdate(beneContractClient,
					SessionHelper.EDITSOLUTIONS);
		}

		// The segment status must be set back to active
		segment.setSegmentStatusCT(Segment.SEGMENTSTATUSCT_ACTIVE);
		// segment.checkForNonFinancialChanges();
		segment.setCasetrackingOptionCT(casetrackingLog.getCaseTrackingEvent());

		RequiredMinDistribution rmd = segment.getRequiredMinDistribution();
		if (rmd != null) {
			rmd.setDeceasedSeventyAndHalfDate(rmd.getSeventyAndHalfDate());
			rmd.setLifeExpectancyMultipleCT("Uniform");
			if (beneDateOfBirth != null) {
				EDITDate newSeventyDate = calcSeventyHalfDate(beneDateOfBirth);
				rmd.setSeventyAndHalfDate(newSeventyDate);
				rmd.setAnnualDate(newSeventyDate);
			}
			segment.addRequiredMinDistribution(rmd);
		}

		SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		casetrackingLog.setContractNumber(segment.getContractNumber());
		setClientData(casetrackingLog, beneClientDetail);
		saveLogEntry(casetrackingLog);

		SessionHelper.clearSessions();

		// Using CRUD, update the transactions for the original owner to the new
		// owner
		try {
			segment.updateOwnerForPendingTransactions(beneContractClient, null);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();
		}
	}

	public void processRiderClaim(CasetrackingLog casetrackingLog,
			Long segmentPK, String careType, EDITDate dateOfDeath,
			String claimType, String conditionCT, String authorizedSignatureCT,
			EDITBigDecimal amountOverride, EDITBigDecimal interestOverride) {
		EDITDate effectiveDate = casetrackingLog.getEffectiveDate();

		String operator = casetrackingLog.getOperator();

		Segment segment = Segment.findByPK(segmentPK);
		try {
			saveRiderClaimTrx(operator, effectiveDate, segment.getSegmentFK(),
					segment.getOptionCodeCT(), careType, dateOfDeath,
					claimType, conditionCT, authorizedSignatureCT,
					amountOverride, interestOverride);
		} catch (EDITEventException e) {

		}

		casetrackingLog.setContractNumber(segment.getContractNumber());
		saveLogEntry(casetrackingLog);

		SessionHelper.clearSessions();
	}

	private EDITDate calcSeventyHalfDate(EDITDate dateOfBirth) {
		EDITDate newDate = dateOfBirth.getSeventyHalfDate();

		return newDate;
	}

	/**
	 * Determine if the same client is the owner and annuitant
	 * 
	 * @param annContractClient
	 * @param ownerContractClient
	 * @return
	 */
	private boolean isOwnerAnnEqual(ContractClient annContractClient,
			ContractClient ownerContractClient) {
		long annClientDetailPK = annContractClient.getClientRole()
				.getClientDetail().getPK();
		long ownerClientDetailPK = ownerContractClient.getClientRole()
				.getClientDetail().getPK();

		if (annClientDetailPK == ownerClientDetailPK) {
			return true;
		}

		return false;
	}

	/**
	 * Create and save the new Annuity contract for supplemental contract or
	 * open claim
	 * 
	 * @param newSegment
	 *            skeletal segment built from data entry
	 * @param contractClientPK
	 *            selected beneficiary will be the owner of the new contract
	 * @param openClaim
	 *            boolean for selection of this option, in order to create
	 *            transfer trx
	 * @param casetrackingLog
	 *            logging info
	 * @param fundMap
	 *            fund numbers and allocations from data entry
	 */
	public void createNewContract(Segment newSegment, Long contractClientPK,
			boolean openClaim, CasetrackingLog casetrackingLog, Map fundMap)
			throws EDITEventException, PortalEditingException, Exception {
		// Determine if Roles needed here exist
		ContractClient contractClient = ContractClient
				.findByPK(contractClientPK);
		ClientDetail clientDetail = contractClient.getClientDetail();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		newSegment.createAndSaveNewContract(clientDetail, casetrackingLog,
				fundMap);

		// update and save Original Policy
		String operator = newSegment.getOperator();
		Segment segment = contractClient.getSegment();

		segment.setOperator(operator);
		SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		// todo
		// if (openClaim)
		// {
		// setupTranferTrxOnOpenClaim(newSegment, segment);
		// }

		// create a lump sum trx for the original contract
		int taxYear = newSegment.getEffectiveDate().getYear();
		int sequenceNumber = 1;
		saveLumpSumTransaction(casetrackingLog, contractClientPK, taxYear,
				new EDITBigDecimal(), new EDITBigDecimal(), null, null, null,
				segment, true, sequenceNumber);

		// //Log the event
		// casetrackingLog.setContractNumber(segment.getContractNumber());
		// setClientData(casetrackingLog, clientDetail);
		// saveLogEntry(casetrackingLog);

		// SessionHelper.clearSession(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * 
	 * @param newSegment
	 * @param originalSegment
	 */
	public void setupTranferTrxOnOpenClaim(Segment newSegment,
			Segment originalSegment) {
		// todo get original contract investments,
		// Set investments = new HashSet();
		// Set investmentAllocOvrds = new HashSet();
		//
		// //create new Investment and Allocations for the From funds - original
		// Investment[] origInvestments = (Investment[]) investments.toArray(new
		// Investment[investments.size()]);
		//
		// SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		//
		// for (int i = 0; i < origInvestments.length; i++)
		// {
		// Investment origInvestment = origInvestments[i];
		// InvestmentAllocation origInvestmentAllocation =
		// origInvestment.getInvestmentAllocation();
		//
		// InvestmentAllocation investmentAllocation = new
		// InvestmentAllocation();
		// investmentAllocation.setAllocationPercent(origInvestmentAllocation.getAllocationPercent());
		// investmentAllocation.setOverrideStatus("O");
		//
		// Investment investment = new Investment();
		// investment.setSegment(newSegment);
		// investment.setFilteredFundFK(new
		// Long(origInvestment.getFilteredFundFK()));
		// investment.setInvestmentAllocation(investmentAllocation);
		// SessionHelper.saveOrUpdate(investment, SessionHelper.EDITSOLUTIONS);
		//
		// InvestmentAllocationOverride investmentAllocationOverride = new
		// InvestmentAllocationOverride();
		// investmentAllocationOverride.setInvestment(investment);
		// investmentAllocationOverride.setInvestmentAllocation(investment.getInvestmentAllocation());
		// investmentAllocationOverride.setToFromStatus("F");
		// investmentAllocOvrds.add(investmentAllocationOverride);
		// }
		//
		// Set newInvestments = newSegment.getInvestments();
		// Investment[] newSegmentInvestments = (Investment[])
		// newInvestments.toArray(new Investment[newInvestments.size()]);
		//
		// for (int i = 0; i < newSegmentInvestments.length; i++)
		// {
		// Investment newInvestment = newSegmentInvestments[i];
		// InvestmentAllocation newInvestmentAllocation =
		// newInvestment.getInvestmentAllocation();
		//
		// InvestmentAllocation investmentAllocation = new
		// InvestmentAllocation();
		// investmentAllocation.setAllocationPercent(newInvestmentAllocation.getAllocationPercent());
		// investmentAllocation.setOverrideStatus("O");
		//
		// Investment investment = new Investment();
		// investment.setSegment(newSegment);
		// investment.setFilteredFundFK(new
		// Long(newInvestment.getFilteredFundFK()));
		// investment.setInvestmentAllocation(investmentAllocation);
		// SessionHelper.saveOrUpdate(investment, SessionHelper.EDITSOLUTIONS);
		//
		// InvestmentAllocationOverride investmentAllocationOverride = new
		// InvestmentAllocationOverride();
		// investmentAllocationOverride.setInvestment(investment);
		// investmentAllocationOverride.setInvestmentAllocation(investment.getInvestmentAllocation());
		// investmentAllocationOverride.setToFromStatus("T");
		// investmentAllocOvrds.add(investmentAllocationOverride);
		// }
		//
		// SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		// todo format transaction
	}

	/**
	 * Annuitization process - create payout table, payout scheduled event and
	 * transfer trx
	 * 
	 * @param payout
	 * @param contractClientPK
	 * @param originalSegmentPK
	 * @param operator
	 * @param effectiveDate
	 */
	public void updateExistingContract(Payout payout, Long contractClientPK,
			String originalSegmentPK, String operator, EDITDate effectiveDate) {
		// todo - need more BA specifications
		// ContractClient contractClient =
		// (ContractClient)SessionHelper.get(ContractClient.class,
		// contractClientPK, SessionHelper.EDITSOLUTIONS);
		// Segment segment = contractClient.getSegment();
		//
		// Set investmentSet = segment.getInvestments();
		// Investment[] investments = (Investment[])investmentSet.toArray(new
		// Investment[investmentSet.size()]);
		// for (int i = 0; i < investments.length; i++)
		// {
		// investments[i].getInvestmentAllocation().setAllocationPercent(new
		// EDITBigDecimal("0"));
		// // reset into segment?
		// }
		//
		// segment.setPayout(payout);
		// segment.setInvestments(funds);
	}

	/**
	 * Assemble the CaseTrackingQuoteVO and execute the death quote script
	 * 
	 * @param disbursementDate
	 * @param beneAllocationPct
	 * @param segmentPK
	 * @return
	 */
	public CaseTrackingQuoteVO performDeathQuote(EDITDate disbursementDate,
			EDITBigDecimal beneAllocationPct, Long segmentPK) {
		Quote quote = new Quote();

		CaseTrackingQuoteVO caseTrackingQuoteVO = null;

		try {
			caseTrackingQuoteVO = quote.performDeathQuote(disbursementDate,
					beneAllocationPct, segmentPK);
		} catch (EDITEventException e) {
			System.out.println(e);

			e.printStackTrace();
		}

		return caseTrackingQuoteVO;
	}

	/**
	 * Not implemented yet Assemble the CaseTrackingQuoteVO and execute the
	 * payout Quote
	 * 
	 * @param payout
	 * @param segmentPK
	 * @return
	 */
	public CaseTrackingQuoteVO performPayoutQuote(Payout payout,
			Long segmentPK, Long contractClientPK) {
		// Quote quote = new Quote();
		//
		// CaseTrackingQuoteVO caseTrackingQuoteVO = null;
		//
		// ContractClient contractClient =
		// ContractClient.findByPK(contractClientPK);
		// ClientDetail clientDetail = contractClient.getClientDetail();
		//
		// Segment segment = Segment.findByPK(segmentPK);
		// SegmentVO segmentVO = (SegmentVO)segment.getVO();
		// segmentVO.addPayoutVO((PayoutVO)payout.getVO());
		//
		// // CaseTrackingQuoteVO caseTrackingQuoteVO = new
		// CaseTrackingQuoteVO();
		// //
		// caseTrackingQuoteVO.addClientDetailVO((ClientDetailVO)clientDetail.getVO());
		// // caseTrackingQuoteVO.addSegmentVO(segmentVO);
		// try
		// {
		// caseTrackingQuoteVO = quote.performPayoutQuote(payout, segmentPK,
		// clientDetail);
		// }
		// catch (EDITEventException e)
		// {
		// System.out.println(e);

		// e.printStackTrace();
		// }
		//
		// return caseTrackingQuoteVO;
		return null;
	}

	/**
	 * Updates existing CaseRequirement entity.
	 * 
	 * @param caseRequirement
	 */
	public void updateCaseRequirement(CaseRequirement caseRequirement) {
		if (caseRequirement.getFollowupDate() == null) {
			caseRequirement.calculateAndSetFollowupDate();
		}

		caseRequirement.checkRequirementStatusAndUpdateReceivedDate();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		SessionHelper
				.saveOrUpdate(caseRequirement, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * Deletes existing caseRequirement
	 * 
	 * @param caseRequirement
	 */
	public void deleteCaseRequirement(CaseRequirement caseRequirement) {
		ClientDetail clientDetail = caseRequirement.getClientDetail();
		FilteredRequirement filteredRequirement = caseRequirement
				.getFilteredRequirement();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		/*
		 * Before deleting the child entity from the database delete
		 * (disassociate from the parent) it from the parent's collection (if
		 * the parent is loaded in hibernate sessions) otherwise hibernate
		 * complains with the following maessage .....
		 * org.hibernate.ObjectDeletedException: deleted object would be
		 * re-saved by cascade (remove deleted object from associations):
		 */
		clientDetail.deleteCaseRequirement(caseRequirement);
		filteredRequirement.deleteCaseRequirement(caseRequirement);

		SessionHelper.delete(caseRequirement, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * Associates filteredRequirements to clientDetail.
	 * 
	 * @param clientDetail
	 * @param filteredRequirements
	 */
	public void associateNonManualFilteredRequirementsToClient(
			ClientDetail clientDetail,
			FilteredRequirement[] filteredRequirements) {
		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		for (int i = 0; i < filteredRequirements.length; i++) {
			Requirement requirement = filteredRequirements[i].getRequirement();

			if (!requirement.isManualRequirement()) {
				CaseRequirement caseRequirement = new CaseRequirement(
						clientDetail, filteredRequirements[i]);

				SessionHelper.saveOrUpdate(caseRequirement,
						SessionHelper.EDITSOLUTIONS);
			}
		}

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * Reverse the deathPending status back to its prior status. Access
	 * changeHistory for the value of the prior status. Set this value back into
	 * the Segment and save it. A new ChangeHistory will be generated for thsi
	 * event along with a casetrackingLog record.
	 * 
	 * @param operator
	 * @param effectiveDate
	 * @param contractClient
	 */
	public void deathPendingReversalProcess(String operator,
			EDITDate effectiveDate, ContractClient contractClient) {
		Segment segment = contractClient.getSegment();

		ChangeHistory[] changeHistory = ChangeHistory
				.findByModifiedKey_AfterValue(segment.getSegmentPK());

		if (changeHistory != null) {
			segment.setSegmentStatusCT(changeHistory[0].getBeforeValue());
			segment.setOperator(operator);

			if (effectiveDate == null
					|| effectiveDate.equals(new EDITDate(
							EDITDate.DEFAULT_MAX_DATE))) {
				effectiveDate = new EDITDate();
			}

			segment.setChangeHistoryEffDate(effectiveDate);

			// segment.checkForNonFinancialChanges();

			// Save the segment with the status change
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
			SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		}
	}

	/**
	 * Update the CasetrackingLog table with the entry passed in
	 * 
	 * @param casetrackingLog
	 */
	private void saveLogEntry(CasetrackingLog casetrackingLog) {
		casetrackingLog.setProcessDate(new EDITDate());

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		SessionHelper
				.saveOrUpdate(casetrackingLog, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * For every record written to the casetrackingLog, format the client Name
	 * from the appropriate places in the ClientDetail.
	 * 
	 * @param casetrackingLog
	 * @param clientDetail
	 */
	private void setClientData(CasetrackingLog casetrackingLog,
			ClientDetail clientDetail) {
		String name = clientDetail.getCorporateName();

		if (name == null) {
			name = clientDetail.getLastName() + ", "
					+ clientDetail.getFirstName();
		}

		casetrackingLog.setClientName(name);
	}

	/**
	 * Adds manual requirement and associates to product structure and client
	 * 
	 * @param clientDetail
	 * @param filteredRequirement
	 */
	public void associateFilteredRequirementToClient(ClientDetail clientDetail,
			FilteredRequirement filteredRequirement) {
		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		CaseRequirement caseRequirement = new CaseRequirement(clientDetail,
				filteredRequirement);

		SessionHelper
				.saveOrUpdate(caseRequirement, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * StretchIRa process for the old and new contract
	 * 
	 * @param originalSegment
	 * @param beneContractClient
	 * @param casetrackingLog
	 * @param rmd
	 * @param beneficiaries
	 */
	public void processStretchIRA(Segment originalSegment,
			ContractClient beneContractClient, CasetrackingLog casetrackingLog,
			RequiredMinDistribution rmd, Set beneficiaries, String printLine1,
			String printLine2) {
		// This selected bene will become the owner of a new contract this is a
		// stretchIRA.
		ClientDetail clientDetail = beneContractClient.getClientDetail();
		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

		// Clone the original segment creating the newSegment
		Segment newSegment = setupNewSegment(originalSegment, casetrackingLog,
				beneficiaries, beneContractClient, printLine1, printLine2, rmd);

		update_SaveOriginalSegment(originalSegment);

		SessionHelper.saveOrUpdate(newSegment, SessionHelper.EDITSOLUTIONS);
		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		ContractEvent contractEvent = new ContractEvent();
		newSegment = (Segment) SessionHelper.get(Segment.class,
				newSegment.getSegmentPK(), SessionHelper.EDITSOLUTIONS);

		contractEvent.commitContractAutoIssue(newSegment,
				casetrackingLog.getOperator());

		setClientData(casetrackingLog, clientDetail);
		saveLogEntry(casetrackingLog);

		// create a lump sum trx for the original contract
		try {
			saveSuppLumpSumTransaction(casetrackingLog, originalSegment,
					beneContractClient);
		} catch (EDITEventException e) {
			System.out.println(e);

			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();
		}

		SessionHelper.clearSessions();
	}

	private void update_SaveOriginalSegment(Segment originalSegment) {
		if (originalSegment.getTotalActiveBeneficiaries() == 0) {
			String[] roleTypes = new String[] { "PBE", "IBE" };

			ContractClient[] contractClients = ContractClient
					.findBy_SegmentPK_RoleTypeCT_TerminationDateGTE(
							originalSegment.getPK(), roleTypes,
							new EDITDate().getFormattedDate());

			originalSegment.setTotalActiveBeneficiaries(contractClients.length);
			originalSegment.setRemainingBeneficiaries(contractClients.length);
			SessionHelper.saveOrUpdate(originalSegment,
					SessionHelper.EDITSOLUTIONS);
		}

	}

	private void saveSuppLumpSumTransaction(CasetrackingLog casetrackingLog,
			Segment segment, ContractClient beneContractClient)
			throws Exception {
		String operator = casetrackingLog.getOperator();
		EDITBigDecimal allocationPct = casetrackingLog.getAllocationPercent();

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		SuppLumpSumTrx suppLumpSumTrx = new SuppLumpSumTrx(segment);

		EDITTrx editTrx = suppLumpSumTrx.createSuppLumpSumTrx(operator,
				casetrackingLog.getNewContractNumber(), beneContractClient);

		GroupSetup groupSetup = editTrx.getClientSetup().getContractSetup()
				.getGroupSetup();
		SessionHelper.saveOrUpdate(groupSetup, SessionHelper.EDITSOLUTIONS);

		SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

		SessionHelper.clearSessions();

		try {
			suppLumpSumTrx.checkForExecutionOfSuppLumpSum(operator, editTrx);
		} catch (EDITEventException e) {
			System.out.println(e);

			e.printStackTrace();
		}
	}

	/**
	 * Create a new Segment for the benficiary selected - Stretch IRA processing
	 * ADD VOS and children entities at the same time
	 * 
	 * @param origSegment
	 * @param casetrackingLog
	 * @return
	 */
	private Segment setupNewSegment(Segment origSegment,
			CasetrackingLog casetrackingLog, Set beneficiaries,
			ContractClient beneContractClient, String printLine1,
			String printLine2, RequiredMinDistribution rmd) {
		Segment newSegment = (Segment) SessionHelper.shallowCopy(origSegment,
				SessionHelper.EDITSOLUTIONS);

		newSegment.setContractNumber(casetrackingLog.getNewContractNumber());
		newSegment.setSuppOriginalContractNumber(casetrackingLog
				.getContractNumber());
		newSegment.setSegmentStatusCT("IssuePendingPremium");
		newSegment.setQualNonQualCT("Qualified");
		newSegment.setQualifiedTypeCT("StretchIRA");
		newSegment.setFreeAmount(new EDITBigDecimal());
		newSegment.setFreeAmountRemaining(new EDITBigDecimal());
		newSegment.setCasetrackingOptionCT(casetrackingLog
				.getCaseTrackingEvent());
		newSegment.setPrintLine1(printLine1);
		newSegment.setPrintLine2(printLine2);
		newSegment.setProductStructureFK(origSegment.getProductStructureFK());

		newSegment = getChildrenOfOriginalSegment(origSegment, newSegment);

		// Create new roles of Owner and Annuitant for the bene, now the new
		// owner
		ClientDetail beneClientDetail = beneContractClient.getClientDetail();
		ClientRole clientRole = beneClientDetail
				.findOrCreateRoles(ClientRole.ROLETYPECT_OWNER);
		ContractClient newContractClient = newSegment.formatContractClient(
				clientRole, newSegment.getEffectiveDate(), "P");
		newSegment.addContractClient(newContractClient);
		((SegmentVO) newSegment.getVO())
				.addContractClientVO((ContractClientVO) newContractClient
						.getVO());

		clientRole = beneClientDetail
				.findOrCreateRoles(ClientRole.ROLETYPECT_ANNUITANT);
		newContractClient = newSegment.formatContractClient(clientRole,
				newSegment.getEffectiveDate(), "P");
		newSegment.addContractClient(newContractClient);
		((SegmentVO) newSegment.getVO())
				.addContractClientVO((ContractClientVO) newContractClient
						.getVO());

		if (beneficiaries != null) {
			for (Iterator iterator = beneficiaries.iterator(); iterator
					.hasNext();) {
				ContractClient beneficiary = (ContractClient) iterator.next();

				newSegment.addContractClient(beneficiary);
				((SegmentVO) newSegment.getVO())
						.addContractClientVO((ContractClientVO) beneficiary
								.getVO());
			}
		}

		// the owner role gets a role of 'deceased' is natural, otherwise the
		// annuitant get the 'deceased' role
		ContractClient ownerContractClient = origSegment
				.getOwnerContractClient();
		ClientDetail ownerClientDetail = ownerContractClient.getClientRole()
				.getClientDetail();
		EDITDate dateOfDeath = ownerClientDetail.getDateOfDeath();
		boolean naturalClientStatus = ownerClientDetail.isStatusNatural();

		if (naturalClientStatus) {
			clientRole = ownerClientDetail
					.findOrCreateRoles(ClientRole.ROLETYPECT_DECEASED);
			newContractClient = newSegment.formatContractClient(clientRole,
					newSegment.getEffectiveDate(), "P");
			newSegment.addContractClient(newContractClient);
			((SegmentVO) newSegment.getVO())
					.addContractClientVO((ContractClientVO) newContractClient
							.getVO());
		} else {
			ContractClient annContractClient = origSegment
					.getAnnuitantContractClient();
			ClientDetail annClientDetail = annContractClient.getClientRole()
					.getClientDetail();
			clientRole = annClientDetail
					.findOrCreateRoles(ClientRole.ROLETYPECT_DECEASED);
			newContractClient = newSegment.formatContractClient(clientRole,
					newSegment.getEffectiveDate(), "P");
			newSegment.addContractClient(newContractClient);
			((SegmentVO) newSegment.getVO())
					.addContractClientVO((ContractClientVO) newContractClient
							.getVO());
			dateOfDeath = annClientDetail.getDateOfDeath();
		}

		if (rmd != null) {
			RequiredMinDistribution origRMD = origSegment
					.getRequiredMinDistribution();
			if (origRMD != null) {
				EDITDate seventyAndHalfDate = origRMD.getSeventyAndHalfDate();
				EDITDate requiredBeginDate = adjustSeventyAndHalfDate(seventyAndHalfDate);
				if (dateOfDeath.before(requiredBeginDate)) {
					rmd.setLifeExpectancyMultipleCT("Single");
					rmd.setDeceasedSeventyAndHalfDate(seventyAndHalfDate);

					int adjustedYear = getYearForNewSeventyHalfDate(
							seventyAndHalfDate, dateOfDeath);
					EDITDate newDate = new EDITDate(adjustedYear + "",
							EDITDate.DEFAULT_MAX_MONTH,
							EDITDate.DEFAULT_MAX_DAY);

					rmd.setSeventyAndHalfDate(newDate);
					rmd.setAnnualDate(newDate);
				} else {
					rmd.setLifeExpectancyMultipleCT("Uniform");
					rmd.setSeventyAndHalfDate(origRMD.getSeventyAndHalfDate());

					EDITDate annualDate = origRMD.getAnnualDate();

					int annualYear = adjustAnnualYear(annualDate, dateOfDeath);
					EDITDate newAnnualDate = new EDITDate(annualYear,
							annualDate.getMonth(), annualDate.getDay());
					rmd.setAnnualDate(newAnnualDate);
				}

				rmd.setInitialCYAccumValue(new EDITBigDecimal());
				newSegment.addRequiredMinDistribution(rmd);
				((SegmentVO) newSegment.getVO())
						.addRequiredMinDistributionVO((RequiredMinDistributionVO) rmd
								.getVO());
			}
		}

		return newSegment;
	}

	/**
	 * When the originalAnnualDate has the year equal to the dateOfDeath year,
	 * add 1 to the year for the rmd on the new contract
	 * 
	 * @param annualDate
	 * @param dateOfDeath
	 * @return
	 */
	private int adjustAnnualYear(EDITDate annualDate, EDITDate dateOfDeath) {
		int annualYear = annualDate.getYear();

		if (dateOfDeath.getYear() == annualYear) {
			annualYear = annualYear + 1;
		}
		return annualYear;
	}

	/**
	 * RequiredBeginDate calc = month 04, day 01, and year SeventyAndHalfDate
	 * year + 1
	 * 
	 * @param seventyAndHalfDate
	 * @return
	 */
	private EDITDate adjustSeventyAndHalfDate(EDITDate seventyAndHalfDate) {

		int year = seventyAndHalfDate.getYear() + 1;
		String yearPlus = year + "";
		EDITDate requiredBeginDate = new EDITDate(yearPlus, "04", "01");

		return requiredBeginDate;
	}

	private int getYearForNewSeventyHalfDate(EDITDate seventyAndHalfDate,
			EDITDate dateOfDeath) {
		int yearToUse = 0;
		int dodYearAdjusted = dateOfDeath.getYear() + 1;
		int seventyYear = seventyAndHalfDate.getYear();
		if (seventyYear > dodYearAdjusted) {
			yearToUse = seventyAndHalfDate.getYear();
		} else {
			yearToUse = dodYearAdjusted;
		}

		return yearToUse;
	}

	private Segment getChildrenOfOriginalSegment(Segment origSegment,
			Segment newSegment) {
		// Use the original contract agents
		Set agentHierarchies = origSegment.getAgentHierarchies();
		AgentHierarchy[] ahArray = (AgentHierarchy[]) agentHierarchies
				.toArray(new AgentHierarchy[agentHierarchies.size()]);
		for (int i = 0; i < ahArray.length; i++) {
			AgentHierarchy agentHier = (AgentHierarchy) SessionHelper
					.shallowCopy(ahArray[i], SessionHelper.EDITSOLUTIONS);
			agentHier.setAgentFK(ahArray[i].getAgentFK());
			Set agentSnapshots = ahArray[i].getAgentSnapshots();
			AgentSnapshot[] asArray = (AgentSnapshot[]) agentSnapshots
					.toArray(new AgentSnapshot[agentSnapshots.size()]);
			for (int j = 0; j < asArray.length; j++) {
				AgentSnapshot agentSnapshot = (AgentSnapshot) SessionHelper
						.shallowCopy(asArray[j], SessionHelper.EDITSOLUTIONS);
				PlacedAgent placedAgent = (PlacedAgent) SessionHelper.get(
						PlacedAgent.class, asArray[j].getPlacedAgentFK(),
						SessionHelper.EDITSOLUTIONS);
				agentSnapshot.setPlacedAgent(placedAgent);
				((AgentSnapshotVO) agentSnapshot.getVO()).setParentVO(
						PlacedAgentVO.class,
						(PlacedAgentVO) placedAgent.getVO());

				agentHier.addAgentSnapshot(agentSnapshot);
				((AgentHierarchyVO) agentHier.getVO())
						.addAgentSnapshotVO((AgentSnapshotVO) agentSnapshot
								.getVO());
			}

			Set agentHierarchyAllocations = ahArray[i]
					.getAgentHierarchyAllocations();
			AgentHierarchyAllocation[] ahaArray = (AgentHierarchyAllocation[]) agentHierarchyAllocations
					.toArray(new AgentHierarchyAllocation[agentHierarchyAllocations
							.size()]);
			for (int j = 0; j < ahaArray.length; j++) {
				AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) SessionHelper
						.shallowCopy(ahaArray[j], SessionHelper.EDITSOLUTIONS);
				agentHier.addAgentHierarchyAllocation(agentHierarchyAllocation);
				((AgentHierarchyVO) agentHier.getVO())
						.addAgentHierarchyAllocationVO((AgentHierarchyAllocationVO) agentHierarchyAllocation
								.getVO());
			}

			newSegment.addAgentHierarchy(agentHier);
			((SegmentVO) newSegment.getVO())
					.addAgentHierarchyVO((AgentHierarchyVO) agentHier.getVO());
		}

		// use the original contract investments
		Set investments = origSegment.getInvestments();
		Investment[] inArray = (Investment[]) investments
				.toArray(new Investment[investments.size()]);

		for (int i = 0; i < inArray.length; i++) {
			Investment investment = (Investment) SessionHelper.shallowCopy(
					inArray[i], SessionHelper.EDITSOLUTIONS);
			investment.setFilteredFundFK(inArray[i].getFilteredFundFK());
			Set investmentAllocations = inArray[i].getInvestmentAllocations();
			InvestmentAllocation[] inAllocArray = (InvestmentAllocation[]) investmentAllocations
					.toArray(new InvestmentAllocation[investmentAllocations
							.size()]);

			for (int j = 0; j < inAllocArray.length; j++) {
				InvestmentAllocation investmentAllocation = (InvestmentAllocation) SessionHelper
						.shallowCopy(inAllocArray[j],
								SessionHelper.EDITSOLUTIONS);
				investment.addInvestmentAllocation(investmentAllocation);
				((InvestmentVO) investment.getVO())
						.addInvestmentAllocationVO((InvestmentAllocationVO) investmentAllocation
								.getVO());
			}

			newSegment.addInvestment(investment);
			((SegmentVO) newSegment.getVO())
					.addInvestmentVO((InvestmentVO) investment.getVO());
		}

		Payout origPayout = origSegment.getPayout();
		Payout payout = (Payout) SessionHelper.shallowCopy(origPayout,
				SessionHelper.EDITSOLUTIONS);
		newSegment.setPayout(payout);
		((SegmentVO) newSegment.getVO()).addPayoutVO((PayoutVO) payout.getVO());

		return newSegment;
	}
}
