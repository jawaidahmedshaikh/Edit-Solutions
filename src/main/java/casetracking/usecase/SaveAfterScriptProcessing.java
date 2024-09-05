/*
 * User: cgleason
 * Date: Jul 21, 2005
 * Time: 3:19:28 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package casetracking.usecase;

import edit.common.vo.*;
import edit.common.*;
import edit.common.exceptions.*;
import edit.services.db.*;
import edit.services.db.hibernate.*;
import event.*;
import event.transaction.*;
import event.financial.client.trx.*;

import java.util.*;

import org.hibernate.*;
import contract.*;


public class SaveAfterScriptProcessing
{
    private EDITDate cycleDate;


    //temporary class until natural save completely integrated with hibernate
    public SaveAfterScriptProcessing()
    {

    }

    public void doUpdates(VOObject[] voObjects, EDITTrx editTrx, EDITDate cycleDate, Segment originalSegment) throws EDITEventException
    {

         // The cache could be quite big by now, and Hibernate may interpret a lot of the element as dirty when they are not and "foolishly" perform a DB update.
        //DO NOT REMOVE
//        SessionHelper.clearSession(SessionHelper.EDITSOLUTIONS);

        this.cycleDate = cycleDate;

        //special bucket processing needed for new Buckets to be tagged to history of buckets
        //temporary solution - for now;
//        EDITTrxHistoryVO editTrxHistoryVO = null;

//        ClientStrategy[] clientStrategy = null;

//        Segment segment = (Segment) SessionHelper.get(Segment.class, new Long(baseSegmentVO.getSegmentPK()), SessionHelper.EDITSOLUTIONS);

//        EDITTrx editTrx = (EDITTrx) SessionHelper.get(EDITTrx.class, new Long(editTrxVO.getEDITTrxPK()), SessionHelper.EDITSOLUTIONS);

//        editTrx.setVO(editTrxVO);
        EDITTrxVO editTrxVO = editTrx.getAsVO();


        Life life = null;
        EDITTrxHistory editTrxHistory = null;
        FinancialHistory financialHistory = null;
        WithholdingHistory withholdingHistory = null;
        InSuspense inSuspense = null;
        List buckets = new ArrayList();
        List bucketHistories = new ArrayList();
        List chargeHistories = new ArrayList();
        List investmentHistories = new ArrayList();

        EDITBigDecimal freeAmount = new EDITBigDecimal();
        EDITBigDecimal initCYAccumValue = new EDITBigDecimal();
        EDITBigDecimal trxAmount = new EDITBigDecimal();

        Suspense suspense = null;
        Segment segment = null;
        SegmentVO segmentVO = null;

        for (int i = 0; i < voObjects.length; i++)
        {
            VOObject voObject = voObjects[i];


            String currentTableName = null;
            if (voObjects[i] instanceof NaturalDocVO )
            {
                currentTableName = "NaturalDocVO";
                NaturalDocVO naturalDocVO = (NaturalDocVO)voObjects[i];
                freeAmount = new EDITBigDecimal(naturalDocVO.getFreeAmount());
                initCYAccumValue = new EDITBigDecimal(naturalDocVO.getInitCYAccumValue());

            }
            else
            {
                currentTableName = VOClass.getTableName(voObject.getClass());
            }

            if (currentTableName.equalsIgnoreCase("Segment"))
            {
                segmentVO = (SegmentVO) voObject;
                segment = (Segment) SessionHelper.get(Segment.class, new Long(segmentVO.getSegmentPK()), SessionHelper.EDITSOLUTIONS);
                segment.setVO(voObject);
 
                // verify if segment status is changed....
                if (segment.isSegmentStatusChanged())
                {
                    // generate change history record
                    new ChangeProcessor().generateChangeHistoryForSegmentStatusChange(segmentVO, editTrxVO.getOperator(), editTrxVO.getEffectiveDate());
                }
            }
            else if (currentTableName.equalsIgnoreCase("EDITTrxHistory"))
            {
                populateEDITTrxHistoryFields((EDITTrxHistoryVO) voObject, editTrxVO);

                // Assuming that editTrxHistories are always new.
                editTrxHistory = new EDITTrxHistory();

                editTrxHistory.setVO(voObject);
            }
            else if (currentTableName.equalsIgnoreCase("Bucket"))
            {
                // Assuming that buckets are new or old
                BucketVO bucketVO = (BucketVO) voObject;

                Bucket bucket = null;

                if (bucketVO.getBucketPK() == 0)
                {
                    bucket = new Bucket();
                }
                else
                {
                    bucket = (Bucket) SessionHelper.get(Bucket.class, new Long(((BucketVO) voObject).getBucketPK()), SessionHelper.EDITSOLUTIONS);
                }

                bucket.setVO((BucketVO) voObject);

                buckets.add(bucket);
            }
            else if (currentTableName.equalsIgnoreCase("BucketHistory"))
            {
                // Assuming that bucketHistories are always new.
                BucketHistory bucketHistory = new BucketHistory();

                bucketHistory.setVO((BucketHistoryVO) voObject);

                bucketHistories.add(bucketHistory);
            }
            else if (currentTableName.equalsIgnoreCase("WithholdingHistory"))
            {
                // Assuming that withholdingHistories are always new.
                withholdingHistory = new WithholdingHistory();

                withholdingHistory.setVO(voObject);
            }
            else if (currentTableName.equalsIgnoreCase("ChargeHistory"))
            {
                // Assuming the chargeHistories are always new.
                ChargeHistory chargeHistory = new ChargeHistory();

                chargeHistory.setVO(voObject);

                chargeHistories.add(chargeHistory);
            }
            else if (currentTableName.equalsIgnoreCase("FinancialHistory"))
            {
                // Assming the financialHistory is always new.
                financialHistory = new FinancialHistory();

                financialHistory.setVO(voObject);
                trxAmount = financialHistory.getGrossAmount();
            }
            else if (currentTableName.equalsIgnoreCase("InvestmentHistory"))
            {
                // Assuming that investmentHistories are always new.
                InvestmentHistory investmentHistory = new InvestmentHistory();

                investmentHistory.setVO(voObject);

                investmentHistories.add(investmentHistory);
            }
            else if (voObject instanceof LifeVO)
            {
                // Assming that lifes are always old.
                life = (Life) SessionHelper.get(Life.class, new Long(((LifeVO) voObject).getLifePK()), SessionHelper.EDITSOLUTIONS);

                life.setVO(voObject);
            }
        }

        updateBucketsAndHistoryHibernate(editTrxHistory, financialHistory, buckets, bucketHistories, chargeHistories, investmentHistories);

        String transactionType = editTrxVO.getTransactionTypeCT();

        editTrx.setPendingStatus("H");

        if (segmentVO == null)
        {
            originalSegment = (Segment) SessionHelper.get(Segment.class, originalSegment.getSegmentPK(), SessionHelper.EDITSOLUTIONS);
            segmentVO = (SegmentVO)originalSegment.getVO();
            segment = originalSegment;
        }
        if (transactionType.equalsIgnoreCase("LS"))
        {
            EDITBigDecimal checkAmount = financialHistory.getCheckAmount();

            suspense = createSuspense(segment, editTrxHistory, editTrx, checkAmount);
        }


// *************************************** BEGIN Trx Updates ******************************
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        // Save in the proper order - parents first, then children.


        editTrxHistory.setEDITTrx(editTrx);

        SessionHelper.saveOrUpdate(editTrxHistory, SessionHelper.EDITSOLUTIONS);

        SessionHelper.saveOrUpdate(editTrx, SessionHelper.EDITSOLUTIONS);

        SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);

        for (Iterator iterator = buckets.iterator(); iterator.hasNext();)
        {
            Bucket bucket = (Bucket) iterator.next();

            SessionHelper.saveOrUpdate(bucket, SessionHelper.EDITSOLUTIONS);
        }

        for (Iterator iterator = bucketHistories.iterator(); iterator.hasNext();)
        {
            BucketHistory bucketHistory = (BucketHistory) iterator.next();

            SessionHelper.saveOrUpdate(bucketHistory, SessionHelper.EDITSOLUTIONS);
        }

        for (Iterator iterator = investmentHistories.iterator(); iterator.hasNext();)
        {
            InvestmentHistory investmentHistory = (InvestmentHistory) iterator.next();

            SessionHelper.saveOrUpdate(investmentHistory, SessionHelper.EDITSOLUTIONS);
        }

        for (Iterator iterator = chargeHistories.iterator(); iterator.hasNext();)
        {
            ChargeHistory chargeHistory = (ChargeHistory) iterator.next();

            SessionHelper.saveOrUpdate(chargeHistory, SessionHelper.EDITSOLUTIONS);
        }

        if (financialHistory != null)
        {
            SessionHelper.saveOrUpdate(financialHistory, SessionHelper.EDITSOLUTIONS);
        }

        if (suspense != null)
        {
            SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);
        }

        if (inSuspense != null)
        {
            SessionHelper.saveOrUpdate(inSuspense, SessionHelper.EDITSOLUTIONS);
        }

        //Casetracking StretchIRa created this new contract that must be updated by the SLS trx
        Segment newSegment = null;
        if (transactionType.equalsIgnoreCase("SLS"))
        {
            newSegment = updateNewContractRMD(editTrx, freeAmount, initCYAccumValue);
            SessionHelper.saveOrUpdate(newSegment, SessionHelper.EDITSOLUTIONS);
        }

        try
        {
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (HibernateException e)
        {
            throw new EDITEventException(e.getMessage());
        }

// *************************************** END Trx Updates ******************************
        //For selected contract statuses, reset any pending editTrx records to 'T'
        if (segment.getSegmentStatusCT().equalsIgnoreCase("Terminated") ||
                segment.getSegmentStatusCT().equalsIgnoreCase("Surrendered") ||
                segment.getSegmentStatusCT().equalsIgnoreCase("NotTaken") ||
                segment.getSegmentStatusCT().equalsIgnoreCase("Death") ||
                segment.getSegmentStatusCT().equalsIgnoreCase("FSHedgeFundPend") ||
                segment.getSegmentStatusCT().equalsIgnoreCase("DeathHedgeFundPend"))
        {
            updatePendingTransactions(segment, "T");
        }

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

// *************************************** Spawn Trx ******************************
        if (transactionType.equalsIgnoreCase("SLS"))
        {
            createVTTransaction(newSegment, segment, editTrx, buckets, bucketHistories, trxAmount);
        }
// *************************************** Spawn Trx ******************************
    }


    void populateEDITTrxHistoryFields(EDITTrxHistoryVO editTrxHistoryVO, EDITTrxVO editTrxVO)
    {
        EDITDate currentDate = new EDITDate();
        EDITDateTime currentDateTime = new EDITDateTime();

        editTrxHistoryVO.setProcessDateTime(currentDateTime.getFormattedDateTime());
        editTrxHistoryVO.setOriginalProcessDateTime(currentDateTime.getFormattedDateTime());

        if (editTrxVO.getNoAccountingInd() != null)
        {
            if (editTrxVO.getNoAccountingInd().equals("Y") || editTrxVO.getTransactionTypeCT().equalsIgnoreCase("RC"))
            {
                editTrxHistoryVO.setAccountingPendingStatus("N");
            }
            else if (editTrxVO.getNoAccountingInd().equals("N"))
            {
                editTrxHistoryVO.setAccountingPendingStatus("Y");
            }
        }
        else
        {
            editTrxHistoryVO.setAccountingPendingStatus("Y");
        }

        editTrxHistoryVO.setCorrespondenceTypeCT("Confirm");
        editTrxHistoryVO.setEDITTrxFK(editTrxVO.getEDITTrxPK());

        if (ClientTrx.REALTIME_MODE == 0)
        {
            editTrxHistoryVO.setCycleDate(currentDate.getFormattedDate());
            editTrxHistoryVO.setRealTimeInd("Y");
        }
        else
        {
            editTrxHistoryVO.setCycleDate(cycleDate.getFormattedDate());
            editTrxHistoryVO.setRealTimeInd("N");
        }

        String transactionType = editTrxVO.getTransactionTypeCT();

        if (transactionType.equals("PO") || transactionType.equals("WI") || (transactionType.equals("FS") || transactionType.equals("LS")
                                         || transactionType.equals(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN)))
        {
            editTrxHistoryVO.setControlNumber(CRUD.getNextAvailableKey() + "");
        }

        if (editTrxVO.getAccountingPeriod() == null)
        {
            editTrxVO.setAccountingPeriod(currentDate.getFormattedYearAndMonth());
            editTrxVO.setOriginalAccountingPeriod(currentDate.getFormattedYearAndMonth());
        }
    }

    private EDITTrxHistory updateBucketsAndHistoryHibernate(EDITTrxHistory editTrxHistory, FinancialHistory financialHistory, List buckets, List bucketHistories, List chargeHistories, List investmentHistories)
    {
        int index = 0;

        // Bucket
        for (Iterator iterator = buckets.iterator(); iterator.hasNext();)
        {
            Bucket bucket = (Bucket) iterator.next();

            if (bucket.getBucketPK() == null) // associate the new Bucket to the Investment
            {
                Investment investment = (Investment) SessionHelper.get(Investment.class, bucket.getInvestmentFK(), SessionHelper.EDITSOLUTIONS);

                // Associate
                bucket.setInvestment(investment);
            }

            // BucketHistory
            BucketHistory bucketHistory = (BucketHistory) bucketHistories.get(index);

            // Associate
            bucketHistory.setBucket(bucket);

            // Associate
            bucketHistory.setEDITTrxHistory(editTrxHistory);

            index++;
        }

        // ChargeHistory
        for (Iterator iterator = chargeHistories.iterator(); iterator.hasNext();)
        {
            ChargeHistory chargeHistory = (ChargeHistory) iterator.next();

            // Associate
            chargeHistory.setEDITTrxHistory(editTrxHistory);
        }

        // FinancialHistory
        if (financialHistory != null)
        {
            // Associate
            financialHistory.setEDITTrxHistory(editTrxHistory);
        }

        // InvestmentHistory
        for (Iterator iterator = investmentHistories.iterator(); iterator.hasNext();)
        {
            // Similar to the the issue for BucketHistory, InvestmentHistories need to wait to be associate with EDITTrxHistory.
            InvestmentHistory investmentHistory = (InvestmentHistory) iterator.next();

            Investment investment = (Investment) SessionHelper.get(Investment.class, investmentHistory.getInvestmentFK(), SessionHelper.EDITSOLUTIONS);

            // Associate
            investmentHistory.setInvestment(investment);

            // Associate
            investmentHistory.setEDITTrxHistory(editTrxHistory);
        }

        return editTrxHistory;
    }

    private Suspense createSuspense(Segment segment, EDITTrxHistory editTrxHistory, EDITTrx editTrx, EDITBigDecimal suspenseAmount)
    {
        EDITDate effectiveDate = editTrx.getEffectiveDate();

        if (suspenseAmount.isGT("0"))
        {
            InSuspense inSuspense = new InSuspense();
            inSuspense.setAmount(suspenseAmount);
            inSuspense.setEDITTrxHistory(editTrxHistory);

            Suspense suspense = new Suspense();
            suspense.setAccountingPendingInd("N");
            suspense.setMaintenanceInd("M");
            suspense.setDirectionCT(Suspense.DIRECTIONCT_REMOVE);
            suspense.setEffectiveDate(effectiveDate);
            suspense.setMaintDateTime(editTrx.getMaintDateTime());
            suspense.setOperator(editTrx.getOperator());
            suspense.addInSuspense(inSuspense);
            String processDateTime = editTrxHistory.getProcessDateTime().toString();
            EDITDate processDate = null;
            if (processDateTime != null)
            {
                processDate = new EDITDateTime(processDateTime).getEDITDate();
            }
            else
            {
                processDate = new EDITDate();
            }
            suspense.setProcessDate(processDate);
            suspense.setSuspenseAmount(suspenseAmount);
            suspense.setOriginalAmount(suspenseAmount);
            suspense.setUserDefNumber(segment.getContractNumber());
            suspense.setSuspenseType("Contract");

            GroupSetup groupSetup = editTrx.getClientSetup().getContractSetup().getGroupSetup();

            String withDrawalTypeCT = groupSetup.getWithdrawalTypeCT();

            if (GroupSetup.WITHDRAWALTYPECT_SUPPCONTRACT.equals(withDrawalTypeCT))
            {
                suspense.setUserDefNumber(editTrx.getNewPolicyNumber());
                suspense.setDirectionCT(Suspense.DIRECTIONCT_APPLY);
                suspense.setPremiumTypeCT(Suspense.PREMIUMTYPECT_ISSUE);
            }

            return suspense;
        }
        else
        {
            return null;
        }
    }

    /**
     * Pending transactions after a death processes remain on the EDITTrx table with a pendingStatus of "T"
     * @param segment
     * @param pendingStatusChangeValue
     */
    //todo - this method is obsolete as written 
    private void updatePendingTransactions(Segment segment, String pendingStatusChangeValue)
    {
        EDITTrx[] editTrxs = EDITTrx.findPendingEditTrx(segment);

        if (editTrxs != null)
        {
            for (int i = 0; i < editTrxs.length; i++)
            {
                EDITTrx editTrx = editTrxs[i];

                ScheduledEvent scheduledEvent = ScheduledEvent.findByEditTrx(editTrx);

                String frequency = "";

                if (scheduledEvent != null)
                {
                    frequency = scheduledEvent.getFrequencyCT();
                }

                boolean trxCanProcess =
                        TransactionProcessor.checkForAllowableTransaction(segment,
                                                                          editTrx.getTransactionTypeCT(),
                                                                          editTrx.getEffectiveDate(),
                                                                          frequency);

                if (!trxCanProcess || (editTrx.getStatus().equalsIgnoreCase("N")))
                {
                    editTrx.setPendingStatus(pendingStatusChangeValue);
                    SessionHelper.saveOrUpdate(editTrx, SessionHelper.EDITSOLUTIONS);
                }
            }
        }
    }

    /**
     *
     * @param segment
     * @param editTrx
     */
    private Segment updateNewContractRMD(EDITTrx editTrx, EDITBigDecimal freeAmount, EDITBigDecimal initCYAccumValue)
    {
        String newContractNumber = editTrx.getNewPolicyNumber();
        Segment newSegment = Segment.findByContractNumber(newContractNumber);
        newSegment.setFreeAmount(freeAmount);
        newSegment.setFreeAmountRemaining(freeAmount);

        RequiredMinDistribution rmd = newSegment.getRequiredMinDistribution();
        if (rmd != null)
        {
            rmd.setInitialCYAccumValue(initCYAccumValue);
            newSegment.addRequiredMinDistribution(rmd);
        }

        return newSegment;
    }


    public void createVTTransaction(Segment newSegment, Segment segment, EDITTrx editTrx, List buckets, List bucketHistories, EDITBigDecimal trxAmount)
    {

        ValueTransferTrx valueTransferTrx = new ValueTransferTrx(newSegment);

        EDITTrx newEditTrx = valueTransferTrx.createValueTransferTrx(editTrx, buckets, bucketHistories, trxAmount);

        GroupSetup groupSetup = newEditTrx.getClientSetup().getContractSetup().getGroupSetup();
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
        SessionHelper.saveOrUpdate(groupSetup, SessionHelper.EDITSOLUTIONS);
        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

        try
        {
            newEditTrx = (EDITTrx)SessionHelper.get(EDITTrx.class, newEditTrx.getEDITTrxPK(), SessionHelper.EDITSOLUTIONS);
            valueTransferTrx.checkForExecutionOfValueTransfer(newEditTrx);
        }
        catch (EDITEventException e)
        {
            System.out.println(e);

            e.printStackTrace();
        }
    }

}
