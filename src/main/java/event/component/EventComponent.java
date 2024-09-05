/*
 * User: gfrosti
 * Date: Jul 15, 2003
 * Time: 1:52:40 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.component;

import edit.common.*;
import edit.common.exceptions.EDITEventException;
import edit.common.exceptions.EDITValidationException;
import edit.common.vo.*;
import edit.portal.exceptions.PortalEditingException;
import edit.services.component.AbstractComponent;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.ElementLockManager;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import event.*;
import event.batch.ActivityFileInterfaceProcessor;
import event.batch.ControlsAndBalancesProcessor;
import event.batch.HedgeFundNotificationProcessor;
import event.batch.UnitValueUpdateProcessor;
import event.business.Event;
import event.dm.composer.*;
import event.dm.dao.DAOFactory;
import event.dm.dao.FastDAO;
import event.dm.dao.OverdueChargeDAO;
import event.financial.client.trx.ClientTrx;
import event.financial.contract.trx.ContractEvent;
import event.financial.group.trx.GroupTrx;
import fission.utility.Util;
import logging.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import contract.*;

import edit.common.exceptions.EDITLockException;
import engine.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


public class EventComponent extends AbstractComponent implements Event {
    /**
     * Creates a transaction group via GroupSetup. The GroupSetupVO may be for a GROUP, LIST, or INDIVIDUAL group. As
     * such, it is possible that this GroupSetupVO will contain 1 or more ContractSetupVOs to represent the grouping.
     *
     * @param groupSetupVO
     * @param editTrxVO
     * @param processName
     * @param optionCode
     * @param productStructurePK
     * @return true if successfully saved, false otherwise
     * @throws EDITEventException
     */
    public boolean saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName,
                                  String optionCode, long productStructurePK) throws Exception {
        return new GroupTrx().saveGroupSetup(groupSetupVO, editTrxVO, processName, optionCode, productStructurePK);
    }

    public void saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, String processName,
                               String optionCode, long productStructurePK, int notificationDays) throws Exception {
        new GroupTrx().saveGroupSetup(groupSetupVO, editTrxVO, processName, optionCode, productStructurePK);
    }

    public boolean saveGroupSetup(GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, SegmentVO segmentVO, String processName,
                                  String optionCode, long productStructurePK, String ignoreEditWarnings, DepositsVO[] depositsVOs)
            throws EDITEventException, PortalEditingException, EDITValidationException {
        return new GroupTrx().saveGroupSetup(groupSetupVO, editTrxVO, segmentVO, processName, optionCode, productStructurePK, ignoreEditWarnings, depositsVOs);
    }

    public void saveCommissionGroupSetup(GroupSetupVO groupSetupVO) throws Exception {
        new GroupTrx().saveCommissionGroupSetup(groupSetupVO);
    }

    public long reverseClientTrx(long editTrxPK, String operator, String reversalReasonCode) throws Exception {
        return new ClientTrx(editTrxPK, operator).reverse(reversalReasonCode);
    }

    public long batchReverseClientTrx(long editTrxPK, String operator, String reversalReasonCode, List<Long> pksToReverse, boolean isSubmitReversalChain, EDITTrxVO earliestPYToRedo) throws Exception {
        return new ClientTrx(editTrxPK, operator).batchReverse(reversalReasonCode, pksToReverse, isSubmitReversalChain, earliestPYToRedo);
    }
    
    /**
     * A Non-sufficient fund Reversal of a Premium trx,
     * 1)Access the Suspense for the contract and the negative premium trx amount
     * 2)If the suspense not found, error generated
     * 3)If the suspense is found, access and update the deposit with the suspensePK
     * and update the SuspenseAmount to zero.
     * 4)No Apply Suspense will be created in the current reversal processing.
     *
     * @param editTrxPK
     * @param operator
     * @param reversalReasonCode
     * @param contractNumber
     * @return
     * @throws Exception
     */
    public String reverseNSFClientTrx(long editTrxPK, String operator, String reversalReasonCode, String contractNumber) throws Exception {
        String message = "";

        EDITTrx pyEdittrx = EDITTrx.findBy_PK(new Long(editTrxPK));
        EDITBigDecimal suspenseAmount = pyEdittrx.getTrxAmount().multiplyEditBigDecimal("-1");
        Suspense suspense = Suspense.findNSFEntry(contractNumber, suspenseAmount);

        if (suspense != null) {
            try {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                Deposits deposit = Deposits.findByEDITTrxFK(new Long(editTrxPK));

                if (deposit != null) {
                    deposit.setSuspense(suspense);

                    deposit.hSave();
                }

                suspense.setSuspenseAmount(new EDITBigDecimal("0"));

                suspense.hSave();

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

                SessionHelper.clearSessions();

                new ClientTrx(editTrxPK, operator).reverse(reversalReasonCode);
            } catch (Exception e) {
                SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
                e.printStackTrace();
                throw new EDITEventException(e.getMessage());
            }
        } else {
            message = "NSF Reversal Not Allowed - NSF Suspense Not Found";
        }

        return message;
    }

    public void processCommissionChecks(String processDate) throws Exception {
        GroupTrx groupTrx = new GroupTrx();

        groupTrx.processCommissionChecks(processDate);
    }

    public long createOrUpdateVO(Object voObject, boolean recursively) throws Exception {
        return super.createOrUpdateVO(voObject, ConnectionFactory.EDITSOLUTIONS_POOL, recursively);
    }

    public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception {
        return super.deleteVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false, null);
    }

    public void commitContract(SegmentVO segmentVO,
                               String operator,
                               String lastDayOfMonthInd,
                               String incomeMaturityAgeString,
                               String suppressPolicyPages) throws Exception {
        ContractEvent contractEvent = new ContractEvent();

        try {
            // Map the segmentVO to the entity to make it hibernate aware
            // This allows the segment to be saved using hibernate.
            Segment segment = (Segment) SessionHelper.map(segmentVO, SessionHelper.EDITSOLUTIONS);
            segmentVO.setSegmentPK(segment.getSegmentPK().longValue());
            // This makes sure we don't lose all of the "fluffy" stuff attached to the SegmentVO.
            segment.setVO(segmentVO);


            contractEvent.commitContract(segment, operator, lastDayOfMonthInd, incomeMaturityAgeString, suppressPolicyPages);

        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();

            throw e;
        } finally {
            SessionHelper.clearSessions();
        }
    }

    public GroupSetupVO composeGroupSetupVOByEDITTrxPK(long editTrxPK, List voInclusionList) throws Exception {
        return new VOComposer().composeGroupSetupVOByEDITTrxPK(editTrxPK, voInclusionList);
    }

    public EDITTrxVO composeEDITTrxVOByEDITTrxPK(long editTrxPK, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOByEDITTrxPK(editTrxPK, voInclusionList);
    }

    public EDITTrxVO[] composeEDITTrxVOBySegmentPK(long segmentPK, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOBySegmentPK(segmentPK, voInclusionList);
    }

    /**
     * Composes all EDITTrx records where the SegmentFK (on the contractSetup record) matches the segmentPK parameter
     * value, and whose transaction is in the transactionTypes value(s) parameter
     *
     * @param segmentPK
     * @param transactionTypes
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOBySegmentPKAndTrxType(segmentPK, transactionTypes, voInclusionList);
    }

    /**
     * Composes all Pending EDITTrx records where the SegmentFK (on the contractSetup record) matches the segmentPK parameter
     * value, and whose transaction is in the transactionTypes value(s) parameter
     *
     * @param segmentPK
     * @param transactionTypes
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composePendingEDITTrxVOBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes, List voInclusionList) throws Exception {
        return new VOComposer().composePendingEDITTrxVOBySegmentPKAndTrxType(segmentPK, transactionTypes, voInclusionList);
    }

    public EDITTrxVO[] composeEDITTrxVOForStatement(long segmentPK,
                                                    String startingEffDate,
                                                    String endingEffDate,
                                                    int drivingTrxPriority,
                                                    List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOForStatement(segmentPK,
                startingEffDate,
                endingEffDate,
                drivingTrxPriority,
                voInclusionList);
    }

    public EDITTrxVO[] composeEDITTrxVOBySegmentPKs_AND_PendingStatus(List segmentPKList, String[] pendingStatus, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOBySegmentPKs_AND_PendingStatus(segmentPKList, pendingStatus, voInclusionList);
    }

    /**
     * Finds a range of EDITTrxVOs given a staring point, fetch size, and direction. Assumes sort by EffectiveDate, TrxPriority ascending.
     *
     * @param startingEDITTrxPK If 0, it assumes that natural starting point by sort criteria.
     * @param fetchSize
     * @param scrollDirection   scrollDirection > 0 for forward scrolling, scrollDirection < 0 for backward scrolling.
     * @return
     */
    public EDITTrxVO[] composeEDITTrxVOByRange_AND_PendingStatus(long startingEDITTrxPK,
                                                                 String[] pendingStatus,
                                                                 int fetchSize,
                                                                 int scrollDirection,
                                                                 List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOByRange(startingEDITTrxPK, pendingStatus, fetchSize, scrollDirection, voInclusionList);
    }

    /**
     * Finds all EDITTrxVOs with the specified pendingStatus
     *
     * @param pendingStatus   value of pending status for trx
     * @param voInclusionList associated VOs to be included
     * @return All EDITTrxVOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatus(String pendingStatus, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOByPendingStatus(pendingStatus, voInclusionList);
    }
    
    /**
     * Finds all EDITTrxVOs with the specified pendingStatus and transactionType
     *
     * @param pendingStatus   value of pending status for trx
     * @param transactionType value of transactionTypeCT for trx
     * @param voInclusionList associated VOs to be included
     * @return All EDITTrxVOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatusAndTransactionType(long segmentPK, String pendingStatus, String transactionType, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOByPendingStatusAndTransactionType(segmentPK, pendingStatus, transactionType, voInclusionList);
    }

    /**
     * Finds all EDITTrxVOs with the specified pendingStatus, sorted by Segment and Trx Effective Date
     *
     * @param pendingStatus   value of pending status for trx
     * @param voInclusionList associated VOs to be included
     * @return All EDITTrxVOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatusSortedBySegmentAndEffDate(String pendingStatus, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOByPendingStatusSortBySegmentAndEffDate(pendingStatus, voInclusionList);
    }

    /**
     * Finds all EDITTrxHistoryVOs with given segmentPK
     *
     * @param segmentPK       segment the history is associated with
     * @param voInclusionList associated VOs to be included
     * @return All EDITTrxHistoryVOs for the segment
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryVOBySegmentPK(long segmentPK, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryVOBySegmentPK(segmentPK, voInclusionList);
    }

    public void updateIssueEDITTrxVO(long segmentPK, List voInclusionList) throws Exception {
        EDITTrxVO editTrxVO = new VOComposer().composeIssueEDITTrxVO(segmentPK, voInclusionList);
        if (editTrxVO != null) {
            editTrxVO.setStatus("R");

            EDITTrxCorrespondenceVO[] editTrxCorrVOs = editTrxVO.getEDITTrxCorrespondenceVO();
            if (editTrxCorrVOs != null) {
                for (int c = 0; c < editTrxCorrVOs.length; c++) {
                    editTrxCorrVOs[c].setStatus("H");
                }
            }

            EDITTrx editTrx = new EDITTrx(editTrxVO);
            editTrx.save();
        }
    }

    public void deletePendingTrxBySegmentPK(List segmentPKList, List voInclusionList) throws Exception {
        EDITTrxVO[] editTrxVOs = new VOComposer().composeEDITTrxVOBySegmentPKs_AND_PendingStatus(segmentPKList,
                new String[]{"P", "M", "X"},
                voInclusionList);
        if (editTrxVOs != null) {
            for (int e = 0; e < editTrxVOs.length; e++) {
                EDITTrx editTrx = new EDITTrx(editTrxVOs[e]);
                editTrx.delete();
            }
        }
    }

    public void deletePendingTrxBySegmentPK_AndNotTransactionType(List segmentPKList, String[] transactionTypes, List voInclusionList) throws Exception {
        EDITTrxVO[] editTrxVOs = new VOComposer().composeEDITTrxVOBySegmentPKs_AND_PendingStatus_AND_NotTransactionType(segmentPKList,
                new String[]{"P", "M", "X"},
                transactionTypes,
                voInclusionList);
        if (editTrxVOs != null) {
            for (int e = 0; e < editTrxVOs.length; e++) {
                EDITTrx editTrx = new EDITTrx(editTrxVOs[e]);
                editTrx.delete();
            }
        }
    }
    
    public int deleteClientTrx(long editTrxPK, String operator) throws Exception {
        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(OutSuspenseVO.class);

        EDITTrxVO editTrxVO = new VOComposer().composeEDITTrxVOByEDITTrxPK(editTrxPK, voInclusionList);

        ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
        if (contractSetupVO.getOutSuspenseVOCount() > 0) {
            resetSuspense(contractSetupVO.getOutSuspenseVO());
        }

        if (editTrxVO.getOriginatingTrxFK() > 0) {
            resetNotificationAmountReceived(editTrxVO);
        }

        return new ClientTrx(editTrxPK, operator).delete();
    }

    public void resetSuspense(OutSuspenseVO[] outSuspenseVOs) throws Exception {
        for (int i = 0; i < outSuspenseVOs.length; i++) {
            Suspense suspense = Suspense.findByPK(new Long(outSuspenseVOs[i].getSuspenseFK()));
            suspense.setPendingSuspenseAmount(new EDITBigDecimal());

            saveSuspenseForPendingAmount(suspense);
        }
    }
    
    public void replaceSuspense(OutSuspenseVO[] outSuspenseVOs, String operator) {
    	
    	CRUD crud = null;
    	
    	try {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

	        for (int i = 0; i < outSuspenseVOs.length; i++) {
	            Suspense originalSuspense = Suspense.findByPK(new Long(outSuspenseVOs[i].getSuspenseFK()));
	            
	            if (originalSuspense != null ) {
		            // create new suspense record
		            Suspense newSuspense = new Suspense();
		            newSuspense.setUserDefNumber(originalSuspense.getUserDefNumber());
		            newSuspense.setOriginalContractNumber(originalSuspense.getUserDefNumber());
		            newSuspense.setEffectiveDate(originalSuspense.getEffectiveDate());
		            newSuspense.setMaintDateTime(new EDITDateTime());
		            newSuspense.setOperator(operator);
		            newSuspense.setProcessDate(originalSuspense.getProcessDate());
		            newSuspense.setSuspenseType(originalSuspense.getSuspenseType());
		            newSuspense.setPremiumTypeCT(originalSuspense.getPremiumTypeCT());
		            newSuspense.setAccountingPendingInd(originalSuspense.getAccountingPendingInd()); 
		            newSuspense.setStatus("N");
		            newSuspense.setTaxYear(originalSuspense.getTaxYear());
		            newSuspense.setContractPlacedFrom("Individual");
		            newSuspense.setMaintenanceInd("M");
		            newSuspense.setSuspenseAmount(originalSuspense.getOriginalAmount());
		            newSuspense.setOriginalAmount(originalSuspense.getOriginalAmount());
		            newSuspense.setPendingSuspenseAmount(new EDITBigDecimal("0"));
		            newSuspense.setDirectionCT("Apply");
		            newSuspense.setCompanyFK(originalSuspense.getCompanyFK());
		        	            
		            SessionHelper.saveOrUpdate(newSuspense, SessionHelper.EDITSOLUTIONS);
		            
		            // Delete deposit(s) associated with original suspense record
		            Deposits[] deposits = Deposits.findBySuspenseFK(originalSuspense.getSuspensePK());
		            if (deposits != null) {
		                for (int d = 0; d < deposits.length; d++) {
		                    SessionHelper.delete(deposits[d], SessionHelper.EDITSOLUTIONS);
		                }
		            }
		            
		            originalSuspense.setSuspenseAmount(new EDITBigDecimal("0"));
		            originalSuspense.setPendingSuspenseAmount(new EDITBigDecimal("0"));
		            originalSuspense.setMaintenanceInd("A");
		            
		            SessionHelper.saveOrUpdate(originalSuspense, SessionHelper.EDITSOLUTIONS);		            
	            }
	        }
	        
	        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            SessionHelper.clearSessions();
            
        } catch (Exception e) {
    		e.printStackTrace();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase("Create New Suspense Entry/Delete Deposit Errored", e);
            
        	throw e;
        }
    }

    public void resetNotificationAmountReceived(EDITTrxVO editTrxVO) throws Exception {
        EDITTrxVO origEditTrxVO = new VOComposer().composeEDITTrxVOByEDITTrxPK(editTrxVO.getOriginatingTrxFK(), new ArrayList());

        if (origEditTrxVO != null) {
            EDITBigDecimal notificationAmountReceived = new EDITBigDecimal(origEditTrxVO.getNotificationAmountReceived());

            if (notificationAmountReceived.isGT("0")) {
                notificationAmountReceived = notificationAmountReceived.subtractEditBigDecimal(editTrxVO.getTrxAmount());

                origEditTrxVO.setNotificationAmountReceived(Util.roundToNearestCent(notificationAmountReceived).getBigDecimal());

                new EDITTrx(origEditTrxVO).saveNonRecursively();
            }
        }
    }

    public void deleteCommissionAdjustment(long commissionHistoryPK) throws Exception {
        GroupSetup groupSetup = null;
        GroupSetupVO[] groupSetupVO = GroupSetup.findGroupSetupByCommissionHistoryPK(commissionHistoryPK);
        if (groupSetupVO != null) {
            groupSetup = new GroupSetup(groupSetupVO[0]);
            groupSetup.delete();
        }
    }

    public void deleteSuspense(long suspensePK) throws Exception {
        new Suspense(suspensePK).delete();
    }

    public int deleteWithholdingOverride(long withholdingOverridePK) throws Exception {
        return new WithholdingOverride(withholdingOverridePK).delete();
    }

    public int deleteInvestmentAllocationOverride(long invAllocOvrdPK) throws Exception {
        return new InvestmentAllocationOverride(invAllocOvrdPK).delete();
    }

//    public boolean isAccountingPending(long segmentPK, String processDate) throws Exception
//    {
//        return new FastDAO().findBySegmentPK_AND_ProcessDateLTE(segmentPK, processDate);
//    }

//    public EDITTrxHistoryVO[] composeEDITTrxHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate, List voInclusionList) throws Exception
//    {
//        return new VOComposer().composeEDITTrxHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(accountPendingStatus, processDate, voInclusionList);
//    }
//
//    public CommissionHistoryVO[] composeCommissionHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate, List voInclusionList) throws Exception
//    {
//        return new VOComposer().composeCommissionHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(accountPendingStatus, processDate, voInclusionList);
//    }

    public SuspenseVO[] composeAccountingPendingSuspenseVO(String processDate, List voInclusionList) throws Exception {
        return new VOComposer().composeAccountingPendingSuspenseVO(processDate, voInclusionList);
    }

    public EDITTrxHistoryVO composeEDITTrxHistoryVOByPK(long editTrxHistoryPK, List voInclusionList) {
        return new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryPK);
    }

    /**
     * Composes all edit trx history records for the given segment and from/to dates
     *
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
                                                                   List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryBySegmentAndDates(segmentPK, fromDate, toDate, transactionType, voInclusionList);
    }

    public TransactionPriorityVO composeTransactionPriorityVOByPK(long transactionPriorityPK, List voInclusionList) throws Exception {
        return new TransactionPriorityComposer(voInclusionList).compose(transactionPriorityPK);
    }

    /**
     * Composes the TransactionPriority record where the TransactionTypeCT matches the transactionTypeCT parameter
     * value
     *
     * @param transactionTypeCT
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public TransactionPriorityVO composeTransactionPriorityVOByTransactionType(String transactionTypeCT, List voInclusionList) throws Exception {
        return new TransactionPriorityComposer(voInclusionList).compose(transactionTypeCT);
    }

    public SuspenseVO[] composeSuspenseVO(List voInclusionList) throws Exception {
        return new VOComposer().composeSuspenseVO(voInclusionList);
    }

    public SuspenseVO[] composeSuspenseVOsToDelete(List voInclusionList) throws Exception {
        return new VOComposer().composeSuspenseVOsToDelete(voInclusionList);
    }

    public SuspenseVO[] composeCashBatchSuspenseVO(List voInclusionList) throws Exception {
        return new VOComposer().composeCashBatchSuspenseVO(voInclusionList);
    }

    public SuspenseVO composeSuspenseVO(long suspensePK, List voInclusionList) throws Exception {
        return new VOComposer().composeSuspenseVO(suspensePK, voInclusionList);
    }

    public SuspenseVO[] composeSuspenseVO(String contractNumber, List voInclusionList) throws Exception {
        return new VOComposer().composeSuspenseVO(contractNumber, voInclusionList);
    }

    public SuspenseVO[] composeSuspenseVOByUserDefNumber(String userDefNumber, List voInclusionList) throws Exception {
        return new VOComposer().composeSuspenseVOByUserDefNumber(userDefNumber, voInclusionList);
    }

    public SuspenseVO[] composeSuspenseVOByDirection(String direction, List voInclusionList) throws Exception {

        return new VOComposer().composeSuspenseVOByDirection(direction, voInclusionList);
    }

    public CashBatchContractVO[] composeCashBatchContractByCBContractPK(long cashBatchContractPK) throws Exception {
        return new VOComposer().composeCashBatchContractByCBContractPK(cashBatchContractPK);
    }

    public void saveEditTrxChanges(EDITTrxVO[] editTrxVOs, String operator, long segmentPK) throws Exception {
        new EDITTrx(editTrxVOs, operator, segmentPK).saveHistory();
    }

    /**
     * Saves the EDITTrx record non-recursively (no children will be saved)
     *
     * @param editTrxVO
     * @throws Exception
     */
    public void saveEDITTrxVONonRecursively(EDITTrxVO editTrxVO) throws Exception {
        new EDITTrx(editTrxVO).saveNonRecursively();
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateStatus(String updateStatus, List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByUpdateStatus(updateStatus, voInclusionList);
    }

    public CommissionHistoryVO[] composeCommHistVOByPlacedAgentPKAndUpdateStatus(long placedAgentPK, String[] updateStatus, List voInclusionList) throws Exception {
        return new VOComposer().composeCommHistVOByPlacedAgentPKAndUpdateStatus(placedAgentPK, updateStatus, voInclusionList);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPKExcludingUpdateStatus(long[] placedAgentPKs,
                                                                                                String[] updateStatuses,
                                                                                                List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByPlacedAgentPKExcludingUpdateStatus(placedAgentPKs,
                updateStatuses,
                voInclusionList);
    }

    //    public CommissionHistoryVO[] composeCommHistoryByPlacedAgentTransactionFromToDates(long[] placedAgentPKs,
    //                                                                                        String transactionType,
    //                                                                                         String fromDate,
    //                                                                                          String toDate,
    //                                                                                           List voInclusionList) throws Exception
    //    {
    //        return new VOComposer().composeCommHistoryByPlacedAgentTransactionFromToDates(placedAgentPKs,
    //                                                                                       transactionType,
    //                                                                                        fromDate,
    //                                                                                         toDate,
    //                                                                                          voInclusionList);
    //    }

    public CommissionHistoryVO[] composeCommHistoryByPlacedAgentTransTypeDatesAndPolicy(long[] placedAgentPKs,
                                                                                        String transactionType,
                                                                                        EDITDate fromDate,
                                                                                        EDITDate toDate,
                                                                                        String contractNumber,
                                                                                        List voInclusionList) throws Exception {
        return new VOComposer().
                composeCommHistoryByPlacedAgentTransTypeDatesAndPolicy(
                        placedAgentPKs,
                        transactionType,
                        fromDate,
                        toDate,
                        contractNumber,
                        voInclusionList);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPKAndCommissionType(long[] placedAgentPKs,
                                                                                            String commissionType,
                                                                                            List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByPlacedAgentPKAndCommissionType(placedAgentPKs,
                commissionType,
                voInclusionList);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPK(long placedAgentPK,
                                                                           List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByPlacedAgentPK(placedAgentPK,
                voInclusionList);
    }

    /**
     * Compose the CommissionHistoryVO for the given PlacedAgent and Segment foreign keys
     *
     * @param agentSnapshotPK
     * @param segmentFK
     * @param voInclusionList
     * @param trxTypeCTs
     * @return
     * @throws Exception
     */
    public CommissionHistoryVO[] composeCommissionHistoryVOByAgentSnapshotPK_SegmentFK(long agentSnapshotPK,
                                                                                       long segmentFK, String[] trxTypeCTs,
                                                                                       List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByAgentSnapshotPK_SegmentFK(agentSnapshotPK,
                segmentFK,
                trxTypeCTs,
                voInclusionList);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(String updateDateTime, long[] placedAgentPKs, String[] statuses, List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(updateDateTime, placedAgentPKs, statuses, voInclusionList);
    }

    public CommissionHistoryVO[] composeCommHistByUpdateDateTimeGTPlacedAgentPKBonusAmtAndStatus(String updateDateTime, long[] placedAgentPKs, String[] statuses, List voInclusionList) throws Exception {
        return new VOComposer().composeCommHistByUpdateDateTimeGTPlacedAgentPKBonusAmtAndStatus(updateDateTime, placedAgentPKs, statuses, voInclusionList);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeLTPlacedAgentPK(long[] placedAgentPKs, String updateDateTime, List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByUpdateDateTimeLTPlacedAgentPK(placedAgentPKs, updateDateTime, voInclusionList);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTLTPlacedAgentPK(long[] placedAgentPKs, String currentUpdateDateTime, String priorUpdateDateTime, List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByUpdateDateTimeGTLTPlacedAgentPK(placedAgentPKs, currentUpdateDateTime, priorUpdateDateTime, voInclusionList);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTELTEPlacedAgentPK(long[] placedAgentPKs, String currentUpdateDateTime, String priorUpdateDateTime, List voInclusionList) throws Exception {
        return new VOComposer().composeCommissionHistoryVOByUpdateDateTimeGTELTEPlacedAgentPK(placedAgentPKs, currentUpdateDateTime, priorUpdateDateTime, voInclusionList);
    }

    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByEDITTrxPK(long editTrxPK, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxCorrespondenceVOByEDITTrxPK(editTrxPK, voInclusionList);
    }

    public String processCorrespondence(ProductStructureVO[] productStructureVOs) throws Exception {
        return null;//new CorrespondenceProcessor().createCorrespondenceExtracts(productStructureVOs);
    }

    public CommissionHistoryVO composeCommissionHistoryVOByCommissionHistoryPK(long commissionHistoryPK, List voInclusionList) throws Exception {
        return new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryPK);
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryBySegmentFKAndCycleDate(long[] segmentFKs, String fromDate, String toDate, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryByDates(segmentFKs, fromDate, toDate, voInclusionList);
//        return new EDITTrxHistoryComposer(voInclusionList).compose(fromDate, toDate);
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDatesForCompanyStructure(long companyStructureFK, String fromDate, String toDate, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryByDatesForCompanyStructure(companyStructureFK, fromDate, toDate, voInclusionList);
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByProcessDateTrxType(long segmentPK,
                                                                        String fromDate,
                                                                        String toDate,
                                                                        String[] trxType,
                                                                        List voInclusionList)
            throws Exception {
        return new VOComposer().composeEDITTrxHistoryByDatesTrxType(segmentPK,
                fromDate,
                toDate,
                trxType,
                voInclusionList);
    }

    public CommissionHistoryVO composeCommissionHistoryVO(CommissionHistoryVO commissionHistoryVO, List voInclusionList) throws Exception {
        new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO);

        return commissionHistoryVO;
    }

    public long saveSuspense(SuspenseVO suspenseVO) throws Exception {
        CRUD crud = null;

        long suspensePK = 0;

        List voExclusionList = null;

        voExclusionList = new ArrayList();

        try {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            voExclusionList.add(EDITTrxHistoryVO.class);
            voExclusionList.add(EDITTrxVO.class);
            voExclusionList.add(ContractSetupVO.class);
            voExclusionList.add(ClientSetupVO.class);

            suspensePK = crud.createOrUpdateVOInDBRecursively(suspenseVO, voExclusionList);

        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();
            throw e;
        } finally {
            if (crud != null) crud.close();

            crud = null;
        }

        return suspensePK;
    }

    public long saveSuspenseNonRecursively(SuspenseVO suspenseVO) throws Exception {
        CRUD crud = null;

        long suspensePK = 0;

        try {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            suspensePK = crud.createOrUpdateVOInDB(suspenseVO);

        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();
            throw e;
        } finally {
            if (crud != null) crud.close();

            crud = null;
        }

        return suspensePK;
    }

    public long saveCashBatchContract(CashBatchContractVO cashBatchContractVO) throws Exception {
        CRUD crud = null;

        long cashBatchContractPK = 0;

        try {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            cashBatchContractPK = crud.createOrUpdateVOInDB(cashBatchContractVO);

        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();
            throw e;
        } finally {
            if (crud != null) crud.close();

            crud = null;
        }

        return cashBatchContractPK;
    }

    public void cleanSuspenseAfterAccounting() throws Exception {
        CRUD crud = null;

        try {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            SuspenseVO[] suspenseVOs = composeSuspenseVOsToDelete(new ArrayList());

            if (suspenseVOs != null) {
                for (int s = 0; s < suspenseVOs.length; s++) {
                    crud.deleteVOFromDBRecursively(SuspenseVO.class, suspenseVOs[s].getSuspensePK());
                }
            }

            //Now get all CashBatches that are released and not accounting pending to see if it should be marked as completed
            int updateContractCount = completeCashBatchContractUpdate();
            System.out.println("CashBatchContracts Updates: " + updateContractCount);

//            CashBatchContract[] cashBatchContracts = CashBatchContract.findReleasedByAccountingPendingIndicator("N");
//            if (cashBatchContracts != null) {
//                boolean suspenseFound = false;
//                for (int i = 0; i < cashBatchContracts.length; i++) {
//                    suspenseFound = false;
//                    Set suspenses = cashBatchContracts[i].getSuspenses();
//                    Iterator it = suspenses.iterator();
//                    while (it.hasNext()) {
//                        Suspense suspense = (Suspense) it.next();
//                        if (suspense.getSuspenseAmount().isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) &&
//                                suspense.getMaintenanceInd().equalsIgnoreCase("M")) {
//                            suspenseFound = true;
//                        }
//                    }
//
//                    if (!suspenseFound) {
//                        completeCashBatchContract(cashBatchContracts[i]);
//                    }
//                }
//            }
        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();
            throw e;
        } finally {
            if (crud != null) crud.close();

            crud = null;

            SessionHelper.clearSessions();
        }
    }

    public QuoteVO performInforceQuote(String quoteDate, String quoteTypeCT, long segmentPK, String operator) throws Exception {
        Quote quote = new Quote();

        quote.getInforceQuote(quoteDate, quoteTypeCT, segmentPK, operator);

        return (QuoteVO) quote.getVO();
    }

    public LoanPayoffQuoteVO performLoanPayoffQuote(String quoteDate, long segmentPK) throws Exception {
        LoanPayoffQuote loanPayoffQuote = new LoanPayoffQuote();

        loanPayoffQuote.getLoanPayoffQuote(quoteDate, segmentPK);

        return (LoanPayoffQuoteVO) loanPayoffQuote.getVO();
    }

    public QuoteVO performNewBusinessQuote(QuoteVO quoteVOIn) throws Exception {
        Quote quote = new Quote();

        quote.getNewBusinessQuote(quoteVOIn);

        return (QuoteVO) quote.getVO();
    }

    /**
     * Perfoms new business quote with input parameters in quoteVO.
     * This method should be used from outside our system i.e. when we are trying to get quote using services.
     *
     * @param quoteVO
     * @return
     * @throws Exception
     */
    public QuoteVO getNewBusinessQuote(QuoteVO quoteVO) throws Exception {
        return new QuoteImpl().performNewBusinessQuote(quoteVO);
    }

    public EDITTrxHistoryVO composeEDITTrxHistoryVOClosestToQuoteDate(String quoteDate, long segmentPK, List voInclusionList) throws Exception {
        return new EDITTrxHistoryComposer(voInclusionList).compose(quoteDate, segmentPK);
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOForTaxableIncomeCheckTotals(String lastStatementDateTime,
                                                                                       long placedAgentPK,
                                                                                       List voInclusionList)
            throws Exception {
        return new VOComposer().composeCommissionHistoryVOForTaxableIncomeCheckTotals(lastStatementDateTime,
                placedAgentPK,
                voInclusionList);
    }

    public String reverseNewBusinessSuspense(long suspensePK, String operator) throws Exception {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findBySuspensePK(suspensePK);

        String returnValue = null;

        if (suspenseVO != null) {
            CRUD crud = null;

            try {
                if (suspenseVO[0].getStatus().equalsIgnoreCase("R")) {
                    return "Suspense Record Has Already Been Reversed";
                } else {
                    suspenseVO[0].setStatus("R");
                    suspenseVO[0].setSuspenseType("Individual");
                    if (suspenseVO[0].getAccountingPendingInd().equalsIgnoreCase("Y")) {
                        suspenseVO[0].setAccountingPendingInd("N");
                    } else {
                        suspenseVO[0].setAccountingPendingInd("Y");
                    }
                    suspenseVO[0].setOperator(operator);
                    suspenseVO[0].setMaintDateTime(new EDITDateTime().getFormattedDateTime());

                    crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

                    crud.createOrUpdateVOInDB(suspenseVO[0]);

                    returnValue = null;
                }
            } catch (Exception e) {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw e;
            } finally {
                if (crud != null) crud.close();
            }
        } else {
            returnValue = "Suspense Record Not Found";
        }

        return returnValue;
    }

//    public long[] findEDITTrxHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingInd, String processDate) throws Exception
//    {
//        return FastDAO.findEDITTrxHistoryPKsByAccountPendingStatus_AND_ProcessDateLTE(accountPendingInd, processDate);
//    }

//    public long[] findCommissionHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingInd, String processDate) throws Exception
//    {
//        return FastDAO.findCommissionHistoryPKsByByAccountPendingStatus_AND_ProcessDateLTE(accountPendingInd, processDate);
//    }

    public long[] findAccountingPendingSuspenseEntries(String processDate) throws Exception {
        return new FastDAO().findAccountingPendingSuspenseEntries(processDate);
    }

    public long[] findAllSuspensePKs() throws Exception {
        return new FastDAO().findAllSuspensePKs();
    }

    public long[] findCommissionHistoryPKsByUpdateStatus(String updateStatus) throws Exception {
        return new FastDAO().findCommissionHistoryPKsByUpdateStatus(updateStatus);
    }

    /**
     * Finds Suspense with the given userDefNumber
     *
     * @param userDefNumber
     * @return array of SuspenseVO objects found
     * @throws Exception
     */
    public SuspenseVO[] findSuspenseByUserDefNumber(String userDefNumber) throws Exception {
        return DAOFactory.getSuspenseDAO().findByUserDefNumber(userDefNumber);
    }

    public ElementLockVO lockElement(long cashBatchContractPK, String username) throws EDITLockException {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.lockElement(cashBatchContractPK, username);
    }

    public int unlockElement(long lockTablePK) {
        ElementLockManager elementLockManager = new ElementLockManager();

        return elementLockManager.unlockElement(lockTablePK);
    }

    /**
     * Execute the GAAP Premium Reserves Extract for the slected parameters of start date, end date and product structure.
     *
     * @param startDate
     * @param endDate
     * @param productStructureVOs
     * @throws Exception
     */
    public String runGAAPPremiumExtract(String startDate, String endDate, ProductStructureVO[] productStructureVOs) throws Exception {
        return null;//return new GAAPPremiumExtractProcessor().createPremiumReservesExtract(startDate, endDate, productStructureVOs);
    }

    /**
     * Creates the Tax Extract
     *
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
                                String fileType) throws Exception {
        return null;
    }

    /**
     * Execute the Controls And Balances Interface for the slected parameters of companyName, and cycleDate.
     *
     * @param companyName
     * @param cycleDate
     * @throws Exception
     */
    public String runControlsAndBalancesReport(String companyName, String cycleDate) throws Exception {
        ControlsAndBalancesProcessor controlsAndBalancesProcessor = new ControlsAndBalancesProcessor();
        controlsAndBalancesProcessor.setControlsAndBalancesInformation(companyName, cycleDate);

        return controlsAndBalancesProcessor.createControlsAndBalancesReport();
    }

    public BucketHistoryVO[] composeBucketHistoryByBucketFK(long bucketFK, List voInclusionList) throws Exception {
        return new VOComposer().composeBucketHistoryByBucketFK(bucketFK, voInclusionList);
    }

    public long getNextAvailableKey() {
        return CRUD.getNextAvailableKey();
    }

    public long saveCommissionHistoryAdjustment(CommissionHistoryVO commissionHistoryVO) throws Exception {
        CRUD crud = null;

        long commissionHistoryPK = 0;

        try {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            commissionHistoryPK = crud.createOrUpdateVOInDB(commissionHistoryVO);

        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();
            throw e;
        } finally {
            if (crud != null) crud.close();

            crud = null;
        }

        return commissionHistoryPK;
    }

    /**
     * Search for updated Unit Value entries (where updateStatus = "H"), and update the pendingStatus on the
     * appropriate transactions/contracts with "B" or "F"
     *
     * @return
     * @throws Exception
     */
    public boolean unitValueUpdate() throws Exception {
        return new UnitValueUpdateProcessor().updateEDITTrxForUnitValues();
    }

    /**
     * Generates report containing all Hedge Fund Notifications for the parameter date specified (corr date <= param date)
     * and updates the correspondence record and the EDITTrx record with the correct notification amount (calculated in PRASE)
     *
     * @param notifyCorrDate
     * @return
     * @throws Exception
     */
    public boolean runHedgeFundNotification(String notifyCorrDate) throws Exception {
        return new HedgeFundNotificationProcessor().runHedgeFundNotification(notifyCorrDate);
    }

    /**
     * Imports new unit values from the flat file specified in the EDITServicesConfig.xml file
     *
     * @throws Exception
     */
    public String importUnitValues(String uvImportMonth, String uvImportDay, String uvImportYear) throws Exception {
        return null;
    }

    /**
     * Creates the Activity Download File for the given cycle date
     *
     * @param cycleDate
     * @throws Exception
     */
    public void createActivityFile(String cycleDate) throws Exception {
        new ActivityFileInterfaceProcessor().createTransactionActivityFile(cycleDate);
    }

    /**
     * Retrieves all EDITTrx for the given segment, with pending status equal to the status(es) passed in and an effective date
     * equal to or greater than the driving effective date.
     *
     * @param segmentFK
     * @param pendingStatus
     * @param drivingEffDate
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOBySegmentPendingStatusAndEffDate(long segmentFK,
                                                                        String[] pendingStatus,
                                                                        String drivingEffDate,
                                                                        List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOBySegmentPendingStatusAndEffDate(segmentFK, pendingStatus, drivingEffDate, voInclusionList);
    }

    /**
     * Retrieves all EDITTrxCorrespondence records with a correspondence date <= the param date specified, whose
     * correspondenceType equals the correspondence type specified.
     *
     * @param notifyCorrDate
     * @param correspondenceType
     * @param voInclusionList
     * @return
     */
    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByCorrTypeAndDate(String notifyCorrDate,
                                                                                     String[] correspondenceTypes,
                                                                                     List voInclusionList) {
        return new VOComposer().composeEDITTrxCorrespondenceVOByCorrTypeAndDate(notifyCorrDate, correspondenceTypes, voInclusionList);
    }

    /**
     * Retrieves all EDITTrxCorrespondece records with the specified Transaction Correspondence PK
     *
     * @param trxCorrespondencePK
     * @return
     */
    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByTrxCorrPK(long trxCorrPK) throws Exception {
        return new VOComposer().composeEDITTrxCorrespondenceVOByTrxCorrPK(trxCorrPK);
    }


    /**
     * Retrieves all suspense records created for the specified hedge fund (filteredFundFK)
     *
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public SuspenseVO[] composeSuspenseVOByFilteredFundFK(long filteredFundFK, List voInclusionList) throws Exception {
        return new VOComposer().composeSuspenseVOByFilteredFundFK(filteredFundFK, voInclusionList);
    }

    /**
     * Composess all EDITTrx records where transactionType = 'HFTA' or 'HFTP' and whose InvestmentAllocationOverride
     * points to an Investment record whose FilteredFundFK matches the filteredFundFK parameter value
     *
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEditTrxVOByTrxTypeAndFilteredFundFK(long filteredFundFK, List voInclusionList) throws Exception {
        return new VOComposer().composeEditTrxVOByTrxTypeAndFilteredFundFK(filteredFundFK, voInclusionList);
    }

    /**
     * Retrieves all BucketHistory records for the given investment and editTrxHistory keys
     *
     * @param investmentFK
     * @param editTrxHistoryPK
     * @return
     * @throws Exception
     */
    public BucketHistoryVO[] composeBucketHistoryByInvestmentAndEditTrxHistory(long investmentFK,
                                                                               long editTrxHistoryPK,
                                                                               List voInclusionList) throws Exception {
        return new VOComposer().composeBucketHistoryByInvestmentAndEditTrxHistory(investmentFK, editTrxHistoryPK, voInclusionList);
    }

    /**
     * Retrieves all InvestmentHistory records for the given investment and editTrxHistory keys
     *
     * @param investmentFK
     * @param editTrxHistoryPK
     * @return
     * @throws Exception
     */
    public InvestmentHistoryVO[] findInvestmentHistoryByInvestmentAndEditTrxHistory(long investmentFK,
                                                                                    long editTrxHistoryPK) throws Exception {
        return new FastDAO().findInvestmentHistoryByInvestmentAndEditTrxHistory(investmentFK, editTrxHistoryPK);
    }

    /**
     * Saves the EDITTrxCorrespondence record to the database
     *
     * @param editTrxCorrespondenceVO
     * @return
     * @throws Exception
     */
    public long saveEDITTrxCorrespondence(EDITTrxCorrespondenceVO editTrxCorrespondenceVO) throws Exception {
        CRUD crud = null;

        long editTrxCorrespondencePK = 0;

        try {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            editTrxCorrespondencePK = crud.createOrUpdateVOInDB(editTrxCorrespondenceVO);

        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();
            throw e;
        } finally {
            if (crud != null) crud.close();

            crud = null;
        }

        return editTrxCorrespondencePK;
    }

    public EDITTrxVO composeOriginatingEDITTrxVOByOriginatingEditTrxFK(long originatingTrxFK, List voInclusionList) throws Exception {
        return new VOComposer().composeOriginatingEDITTrxVOByOriginatingEditTrxFK(originatingTrxFK, voInclusionList);
    }

    /**
     * Finder.
     *
     * @param reinsuranceHistoryPK
     * @return
     */
    public EDITTrxVO findEDITTrxVOBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK) {
        EDITTrxVO editTrxVO = null;

        EDITTrx editTrx = EDITTrx.findBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        if (editTrx != null) {
            editTrxVO = editTrx.getAsVO();
        }

        return editTrxVO;
    }

    /**
     * Retrieves the total gross amount of all withdrawals for the specified segment processed for the specified tax year
     *
     * @param taxYear
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getWithdrawalGrossAmountTaxYearToDate(int taxYear, long segmentFK) throws Exception {
        return new FastDAO().getWithdrawalsTaxYearToDate(taxYear, segmentFK);
    }

    /**
     * Retrieves the total gross amount of all RMD (RW) transactions for the specified segment processed
     * for the specified tax year.
     *
     * @param taxYear
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getRMDGrossAmountTaxYearToDate(int taxYear, long segmentFK) throws Exception {
        return new FastDAO().getRmdsTaxYearToDate(taxYear, segmentFK);
    }

    /**
     * Retrieves the AccumulatedValue from the previous Calendar Year End Transaction (if it exists)
     *
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public EDITBigDecimal getPriorCYAccumulatedValue(long segmentFK) throws Exception {
        return new FastDAO().getPriorCYAccumulatedValue(segmentFK);
    }

    /**
     * Retrieves any existing RW transactions for the specified segment
     *
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public GroupSetupVO[] findRMDTransaction(long segmentFK, String trxType, List voInclusionList) throws Exception {
        return new VOComposer().composeGroupSetupVOsForRMDTransactions(segmentFK, trxType, voInclusionList);
    }

    /**
     * finder method for retrieving overdue charge remaining
     *
     * @param editTrxFK
     * @return
     */
    public OverdueChargeVO[] findOverdueChargeByEDITTrxFK(long editTrxFK) {
        return new OverdueChargeDAO().findByEDITTrxFK(editTrxFK);
    }

    /**
     * Checks the given investments for their final price (unit value)
     *
     * @param investmentVOs
     * @return
     * @throws Exception
     */
    public boolean checkForForwardPrices(InvestmentVO[] investmentVOs, String effectiveDate) throws Exception {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        boolean allForwardPricesFound = true;
        boolean investmentHasValue = false;

        for (int i = 0; i < investmentVOs.length; i++) {
            investmentHasValue = false;

            BucketVO[] bucketVO = investmentVOs[i].getBucketVO();
            for (int j = 0; j < bucketVO.length; j++) {
                if (new EDITBigDecimal(bucketVO[j].getCumUnits()).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)) {
                    investmentHasValue = true;
                    break;
                }
            }

            if (investmentHasValue) {
                FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundByPK(investmentVOs[i].getFilteredFundFK());

                UnitValuesVO[] unitValuesVO = engine.dm.dao.DAOFactory.getUnitValuesDAO().
                        findUnitValuesByFilteredFundIdDate(filteredFundVO[0].getFilteredFundPK(),
                                effectiveDate,
                                "Forward");

                if (unitValuesVO == null || unitValuesVO.length == 0) {
                    allForwardPricesFound = false;
                }
            }
        }

        return allForwardPricesFound;
    }

    /**
     * Checks the given investments for their final price (unit value) and charge code FK.
     *
     * @param investmentVOs
     * @param effectiveDate
     * @return
     * @throws Exception
     */
    public boolean checkForForwardPricesWithChargeCodes(InvestmentVO[] investmentVOs,
                                                        String effectiveDate) throws Exception {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        boolean allForwardPricesFound = true;

        for (int i = 0; i < investmentVOs.length; i++) {
            long chargeCodeFK = investmentVOs[i].getChargeCodeFK();

            FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundByPK(investmentVOs[i].getFilteredFundFK());

            UnitValuesVO[] unitValuesVO = engine.dm.dao.DAOFactory.getUnitValuesDAO().
                    findUnitValuesByFilteredFundIdDate(filteredFundVO[0].getFilteredFundPK(),
                            effectiveDate,
                            "Forward");

            if (unitValuesVO == null || unitValuesVO.length == 0) {
                allForwardPricesFound = false;
            } else {
                // now see if all of them returned have charge code set to chargeCodeFK
                for (int j = 0; j < unitValuesVO.length; j++) {
                    if (unitValuesVO[j].getChargeCodeFK() != chargeCodeFK) {
                        allForwardPricesFound = false;
                        break;
                    }
                }
            }
        }

        return allForwardPricesFound;

    }


    /**
     * To import cash clearance values.
     *
     * @param importDate
     * @return
     */
    public String importCashClearanceValues(String importDate) {
        return null;//new CashClearanceImportProcessor().importCashClearanceValues(importDate);
    }

    /**
     * Composes all editTrxHistoryVOs for the given dates using the given dateType (effective/process date)
     * that affected the given filtered fund.
     *
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
                                                                   List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryByDate_And_Fund(startDate, endDate, dateType, filteredFundFK, voInclusionList);
    }

    /**
     * Composes all editTrxHistoryVOs for the given dates using the given dateType (effective/process date)
     * that affected the given filtered fund.
     *
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
                                                                   long chargeCodeFK,
                                                                   List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryByDate_And_Fund(startDate, endDate, dateType, filteredFundFK, chargeCodeFK, voInclusionList);
    }

    /**
     * Composes all editTrxHistoryVOs for the given dates using the given dateType (effective/process date)
     * that affected the given filtered fund.
     *
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
                                                                     List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryByDateSegmentFund(startDate, endDate, dateType, filteredFundFK, chargeCodeFK, segmentFKs, voInclusionList);
    }


    /**
     * Finds all EDITTrxVO that match the specified transactionType and pending status that fall between
     * the specified dates.
     *
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
                                                                                    String pendingStatus) throws Exception {
        return DAOFactory.getEDITTrxDAO().findBySegment_TransactionTypeCT_EffectiveDate_AND_PendingStatus(segmentPK,
                transactionTypeCT,
                fromDate,
                toDate,
                pendingStatus);
    }

    /**
     * Save the transactionPriorityVO passed in
     *
     * @param transactionPriorityVO
     */
    public void saveTransactionPriority(TransactionPriorityVO transactionPriorityVO) throws Exception {
        TransactionPriority transactionPriority = new TransactionPriority(transactionPriorityVO);

        transactionPriority.save();
    }

    /**
     * Delete the TransactionPriority record using its PK.
     *
     * @param transactionPriorityPK
     * @throws Exception
     */
    public void deleteTransactionPriority(long transactionPriorityPK) throws Exception {
        TransactionPriority transactionPriority = TransactionPriority.findByPK(transactionPriorityPK);

        try {
            transactionPriority.delete();
        } catch (Throwable throwable) {
            System.out.println(throwable);

            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
    }

    public InvestmentAllocationOverrideVO[] getInvestmentAllocationOvrds(long originatingTrxFK) throws Exception {
        InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOs = DAOFactory.getInvestmentAllocationOverrideDAO().findByEDITTrxPK(originatingTrxFK);
        List investmentAllocVOOut = new ArrayList();

        if (investmentAllocationOverrideVOs != null) {
            List voInclusionList = new ArrayList();
            voInclusionList.add(InvestmentVO.class);
            voInclusionList.add(InvestmentAllocationVO.class);

            for (int i = 0; i < investmentAllocationOverrideVOs.length; i++) {
                InvestmentAllocationOverrideComposer investmentAllocationOverrideComposer = new InvestmentAllocationOverrideComposer(voInclusionList);
                InvestmentAllocationOverrideVO investmentAllocationOvrdVO = investmentAllocationOverrideComposer.compose(investmentAllocationOverrideVOs[i]);
                investmentAllocVOOut.add(investmentAllocationOvrdVO);
            }

            return (InvestmentAllocationOverrideVO[]) investmentAllocVOOut.toArray(new InvestmentAllocationOverrideVO[investmentAllocVOOut.size()]);
        }

        return investmentAllocationOverrideVOs;
    }

    /**
     * Retrieves all ClientSetupVOs associated with the given contractClientFK
     *
     * @param contractClientFK
     * @return
     * @throws Exception
     */
    public ClientSetupVO[] composeClientSetupByContractClientFK(long contractClientFK) throws Exception {
        return new VOComposer().composeClientSetupByContractClientFK(contractClientFK, new ArrayList());
    }

    /**
     * Retrieves all ContractSetupVOs associated with the given segmentFK
     *
     * @param segmentFK
     * @return
     * @throws Exception
     */
    public ContractSetupVO[] composeContractSetupBySegmentFK(long segmentFK) throws Exception {
        return new VOComposer().composeContractSetupBySegmentFK(segmentFK, new ArrayList());
    }

    /**
     * Update CashBatchContract after processing accounting
     *
     * @param cashBatchContract    The CashBatchContract record to be updated
     * @param finalUpdateIndicator A flag indicating the last CashBatchContract record - will clear hibernate session
     */
    public void updateCashBatchContract(CashBatchContract cashBatchContract, String finalUpdateIndicator) {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try {
            SessionHelper.saveOrUpdate(cashBatchContract, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }
    }

    @Override
    public int updateCashBatchContractAccountPendingIndicator(long primaryKey, String status) {
        try (Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL)) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE EDIT_SOLUTIONS.dbo.CashBatchContract" +
                    " SET AccountingPendingIndicator= ? WHERE CashBatchContractPK = ?")) {
                ps.setString(1, status);
                ps.setLong(2, primaryKey);
                return ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Update the CashBatchContract Record
     *
     * @param cashBatchContract The CashBatchContract record to be deleted.
     */
    public void updateCashBatchContract(CashBatchContract cashBatchContract) {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try {
            SessionHelper.saveOrUpdate(cashBatchContract, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }
    }

    /**
     * Updates any CashBatchContract records with a RealseIndicator of 'R', AccountPendingIndicator of 'N' and has a
     * foreign Suspense record with an amount >0 and MaintenanceInd of 'M'
     *
     * @return
     */
    public int completeCashBatchContractUpdate() {
        String sql = "UPDATE CashBatchContract set ReleaseIndicator='C'\n "
                + "where CashBatchContractPK IN\n "
                + "(select CashBatchContractFK from Suspense s where s.SuspenseAmount <=0 and s.MaintenanceInd != 'M')\n "
                + "and AccountingPendingIndicator = 'N' AND ReleaseIndicator='R'";

        Session session = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS);
        SQLQuery query = session.createSQLQuery(sql);
        int recordCount = query.executeUpdate();
        session.close();
        return recordCount;

    }

    /**
     * Mark the CashBatchContract Record as being Complete
     *
     * @param cashBatchContract The CashBatchContract record to be deleted.
     * @deprecated
     */
    public void completeCashBatchContract(CashBatchContract cashBatchContract) {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try {
            cashBatchContract.setReleaseIndicator(CashBatchContract.RELEASE_INDICATOR_COMPLETE);
            SessionHelper.saveOrUpdate(cashBatchContract, SessionHelper.EDITSOLUTIONS);
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
            Log.logGeneralExceptionToDatabase(null, e);
        }
    }

    /**
     * Save the given Suspense records with the update PendingSuspenseAmount
     *
     * @param suspense
     */
    public String saveSuspenseForPendingAmount(Suspense suspense) {
        String responseMessage = "Suspense Entry Save was Successful";

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try {
            SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);

            Deposits[] deposits = Deposits.findBySuspenseFK(suspense.getSuspensePK());
            if (deposits != null) {
                for (int i = 0; i < deposits.length; i++) {
                    SessionHelper.delete(deposits[i], SessionHelper.EDITSOLUTIONS);
                }
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

            SessionHelper.clearSessions();
        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Suspense Entry Save Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        } finally {
            return responseMessage;
        }
    }

    /**
     * Save the given Suspense records with the update PendingSuspenseAmount (set after trx save)
     *
     * @param suspense
     */
    public String saveSuspenseForTransaction(Suspense suspense) {
        String responseMessage = "Suspense Entry Save was Successful";

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try {
            SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Suspense Entry Save Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        } finally {
            SessionHelper.clearSessions();

            return responseMessage;
        }
    }

    /**
     * Delete the given Set of OutSuspense records
     *
     * @param outSuspenseSet
     * @return
     */
    public String deleteOutSuspense(Set outSuspenseSet) {
        String responseMessage = "Out Suspense Entries Successfully Deleted";

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try {
            Iterator it = outSuspenseSet.iterator();
            while (it.hasNext()) {
                OutSuspense outSuspense = (OutSuspense) it.next();
                SessionHelper.delete(outSuspense, SessionHelper.EDITSOLUTIONS);
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            responseMessage = "Out Suspense Entry Deletion Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        } finally {
            return responseMessage;
        }
    }

    public int deleteChange(long chargePK) throws Exception {
        Charge charge = new Charge();
        charge.setChargePK(new Long(chargePK));
        int deleteCount = charge.delete();
        return deleteCount;
    }

    public String saveQuoteReversal(Long editTrxPK, Long segmentPK, String operator, String reversalReasonCode) {
        return null;
    }

    /**
     * New Business Event for deleting transactions that are in pending status.  Deletion of the Issue
     * Trx requires deleting of the other transactions that were created during the issue process. This deletion
     * process will by[ass deleing the Submit and Billing Change transactions.
     *
     * @param editTrx
     * @param segment
     * @return
     */
    public String deleteTransaction(EDITTrx editTrx, Segment segment) {
        String responseMessage = "Transaction successfully deleted";

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        //Deleting the Issue transaction requires deleing the pending transactions generated during the issue process
        EDITTrx[] editTrxs = null;
        if (editTrx.getTransactionTypeCT().equalsIgnoreCase("IS")) {
            editTrxs = EDITTrx.findBy_SegmentPK_PendingStatus(segment.getSegmentPK(), EDITTrx.PENDINGSTATUS_PENDING);
        }
        try {
            editTrx.hDelete();

            if (editTrxs != null) {
                for (int i = 0; i < editTrxs.length; i++) {
                    EDITTrx trx = editTrxs[i];
                    if (!trx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT) &&
                            !trx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE)) {
                        trx.hDelete();
                    }
                }
            }

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        } catch (Exception e) {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            responseMessage = "Transaction delete errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);

        } finally {
            SessionHelper.clearSessions();
        }

        return responseMessage;

    }

    public void buildTransactionsFromQuote(String transactionType, QuoteVO quoteVO, Long segmentPK, EDITDate quoteDate, String operator, String notTakenOverrideInd) {
        EDITDate effectiveDate = null;
        String processName = null;
        effectiveDate = quoteDate;
        if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_NOTTAKEN)) {
            processName = "NotTaken";
        } else {
            processName = "FullSurrender";

            Segment segment = Segment.findByPK(segmentPK);
            String status = segment.getSegmentStatusCT();

            Life life = Life.findBy_SegmentPK(segmentPK.longValue());
            EDITDate paidToDate = life.getPaidToDate();

            // removed per WO 20818 so effectiveDate is not backdated
            /*if (paidToDate != null)
            {
                if (quoteDate.afterOREqual(paidToDate) && !status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP))
                {
                    effectiveDate = paidToDate;
                }
            }*/
        }

        int taxYear = effectiveDate.getYear();

        Segment segment = Segment.findByPK(segmentPK);
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[]{segment});

        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try {
            EDITBigDecimal totalRefundAmount = new Suspense().updatePendingSuspenseAmount(segment.getContractNumber());

            if (totalRefundAmount.isGT("0")) {
                ChargeVO chargeVO = new ChargeVO();
                chargeVO.setChargeAmount(totalRefundAmount.getBigDecimal());
                chargeVO.setChargeTypeCT(Charge.CHARGE_TYPE_SUSPENSE_REFUND);
                groupSetupVO.addChargeVO(chargeVO);
            }

            EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);

            editTrx.setNotTakenOverrideInd(notTakenOverrideInd);

            new GroupTrx().saveGroupSetup(groupSetupVO, (EDITTrxVO) editTrx.getAsVO(), processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    public String processTransfer(List suspenseTransferRows, Suspense suspense, String reasonCode, String operator) {
        String responseMessage = "Transfers Completed Successfully";

        Long cashBatchContractPK = suspense.getCashBatchContractFK();

        try {
            //Update the original selection as voided
            suspense.setVoidDefaults(suspense);
            suspense.save();
            Long companyFK = suspense.getCompanyFK();

            //Create new "Apply" suspense for each of the table entries and a premium trx
            for (int i = 0; i < suspenseTransferRows.size(); i++) {
                StringTokenizer tokens = new StringTokenizer((String) suspenseTransferRows.get(i), "_");

                String rowId = tokens.nextToken();
                String contractNumber = tokens.nextToken();
                String transferAmount = tokens.nextToken();
                EDITBigDecimal amount = new EDITBigDecimal(transferAmount);
                Suspense applySuspense = new Suspense().createApplySuspense(amount, contractNumber, operator, reasonCode, companyFK);

                applySuspense.save();
                Segment segment = Segment.findByContractNumber(applySuspense.getUserDefNumber());
            	if (suspense.getPremiumTypeCT().equals(Suspense.PREMIUMTYPECT_LOAN_REPAYMENT)) {
            		//Added for UL products - DE - 2021-04-14
                    EDITTrx.createLoanPaymentForSuspense(suspense, cashBatchContractPK, operator , segment);
            	} else {
                    EDITTrx.createPremiumForSuspense(suspense, cashBatchContractPK , operator, segment);
            	}
            }
        } catch (Exception e) {
            responseMessage = "Suspense Entry Transfer Errored - See Logging For Error Information";

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(responseMessage, e);
        }

        return responseMessage;
    }

    /**
     * Composes all edit trx history records for the given segment and from/to dates
     *
     * @param segmentPK
     * @param fromDate
     * @param toDate
     * @param transactionType
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByEffectiveDateForAccting(long segmentPK,
                                                                             String fromDate,
                                                                             String toDate,
                                                                             String transactionType,
                                                                             List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxHistoryBySegmentAndDatesForAccting(segmentPK, fromDate, toDate, transactionType, voInclusionList);
    }

    public EDITTrxVO[] composeEDITTrxVOBySegmentPK_AND_PendingStatus(long segmentPK, String[] pendingStatus, List voInclusionList) throws Exception {
        return new VOComposer().composeEDITTrxVOBySegmentPK_AND_PendingStatus(segmentPK, pendingStatus, voInclusionList);
    }
}
