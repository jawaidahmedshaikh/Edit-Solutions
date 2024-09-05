/*
 * User: dlataill
 * Date: Oct 21, 2004
 * Time: 10:48:42 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.batch;

import edit.common.vo.*;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITDateTime;
import edit.services.db.CRUD;
import edit.services.config.ServicesConfig;
import edit.services.logging.Logging;
import edit.services.*;

import java.io.Serializable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Enumeration;
import java.math.BigDecimal;

import event.InvestmentAllocationOverride;
import event.EDITTrxCorrespondence;
import event.dm.dao.DAOFactory;
import event.financial.group.trx.GroupTrx;
import contract.Investment;
import contract.InvestmentAllocation;
import fission.utility.Util;
import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import logging.LogEvent;
import org.apache.logging.log4j.Logger;
import engine.*;
import batch.business.*;

public class UnitValueUpdateProcessor implements Serializable
{
    private static final event.business.Event eventComponent = new event.component.EventComponent();
    private static final String PRICE_CONFIRM_FILE_NAME = "PriceConfirm";
    private boolean hftpFinalPrice = false;
    private String nullValue = null;
    private String exportFile;

    public UnitValueUpdateProcessor()
    {
        super();

        exportFile = getExportFile();
    }

    public boolean updateEDITTrxForUnitValues()
    {
        EditServiceLocator.getSingleton().getBatchAgent()
                          .getBatchStat(Batch.BATCH_JOB_UPDATE_UNIT_VALUES).tagBatchStart(Batch.BATCH_JOB_UPDATE_UNIT_VALUES,
            "Update UnitValues");


        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        boolean updateSuccessful = true;

        try
        {
            String[] segmentStatusArray = new String[] {"FSHedgeFundPend", "DeathHedgeFundPend"};

            List segVOInclusionList = new ArrayList();
            segVOInclusionList.add(InvestmentVO.class);
            segVOInclusionList.add(InvestmentAllocationVO.class);
            segVOInclusionList.add(BucketVO.class);

            SegmentVO[] segmentVOs = contractLookup.composeSegmentVOByStatus(segmentStatusArray, segVOInclusionList);

            UnitValuesVO[] unitValuesVOs = engineLookup.getUnitValuesByUpdateStatus("Hedge");

            List voInclusionList = new ArrayList();
            voInclusionList.add(ClientSetupVO.class);
            voInclusionList.add(ContractSetupVO.class);
            voInclusionList.add(GroupSetupVO.class);
            voInclusionList.add(InvestmentAllocationOverrideVO.class);
            voInclusionList.add(InvestmentVO.class);
            voInclusionList.add(EDITTrxHistoryVO.class);
            voInclusionList.add(InvestmentHistoryVO.class);

            Hashtable unitValuesHT = new Hashtable();

            if (unitValuesVOs != null)
            {
                engine.business.Calculator engineComponent = new engine.component.CalculatorComponent();

                for (int i = 0; i < unitValuesVOs.length; i++)
                {
                    if (segmentVOs != null)
                    {
                        updateSuccessful = processSegmentsForUnitValueUpdate(segmentVOs,
                                                                             unitValuesVOs[i].getFilteredFundFK(),
                                                                             unitValuesVOs[i].getEffectiveDate());
                    }

                    unitValuesVOs[i].setUpdateStatus(nullValue);
                    engineComponent.createOrUpdateVONonRecursive(unitValuesVOs[i]);
                    unitValuesHT.put(unitValuesVOs[i].getFilteredFundFK() + "_" + unitValuesVOs[i].getChargeCodeFK(), unitValuesVOs[i]);

                    FilteredFund filteredFund = FilteredFund.findByPK(new Long(unitValuesVOs[i].getFilteredFundFK()));

                    exportToFile(unitValuesVOs[i], filteredFund);

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_UNIT_VALUES).updateSuccess();
                }
            }

            EDITTrxVO[] editTrxVOs = eventComponent.composeEDITTrxVOByPendingStatusSortedBySegmentAndEffDate("L", voInclusionList);

            if (editTrxVOs != null)
            {
                updateSuccessful = processTransactionsForUnitValueUpdate(editTrxVOs, unitValuesHT, updateSuccessful);
            }

            voInclusionList.clear();
            voInclusionList.add(ClientSetupVO.class);
            voInclusionList.add(ContractSetupVO.class);
            voInclusionList.add(SegmentVO.class);
            voInclusionList.add(InvestmentVO.class);
            voInclusionList.add(BucketVO.class);

            editTrxVOs = eventComponent.composeEDITTrxVOByPendingStatus("M", voInclusionList);

            if (editTrxVOs != null)
            {
                updateSuccessful = processTransactionsForUnitValueUpdate(editTrxVOs, updateSuccessful);
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_UNIT_VALUES).updateFailure();

            updateSuccessful = false;

            LogEvent logEvent = new LogEvent("Unit Value Update Failed", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
         finally
        {
            EditServiceLocator.getSingleton().getBatchAgent()
                              .getBatchStat(Batch.BATCH_JOB_UPDATE_UNIT_VALUES).tagBatchStop();
        }
        return updateSuccessful;
    }
    
    private boolean processSegmentsForUnitValueUpdate(SegmentVO[] segmentVOs,
                                                      long filteredFundFK,
                                                      String effectiveDate) throws Exception
    {
        EDITDate edUVEffDate = new EDITDate(effectiveDate);

        boolean updateSuccessful = true;

        for (int i = 0; i < segmentVOs.length; i++)
        {
            try
            {
                InvestmentVO[] investmentVOs = segmentVOs[i].getInvestmentVO();
                for (int j = 0; j < investmentVOs.length; j++)
                {
                    if (investmentVOs[j].getFilteredFundFK() == filteredFundFK)
                    {
                        EDITBigDecimal fundValue = getFundValue(investmentVOs[j].getBucketVO());

                        if (fundValue.isGT(new EDITBigDecimal()))
                        {
                            EDITTrxVO editTrxVO = getEditTrxVO(segmentVOs[i]);

                            if (editTrxVO != null)
                            {
                                EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());

                                //create an HFTP for the fund(s) on the FS or DE transaction
                                //whose final price has been received.
                                if (edUVEffDate.afterOREqual(edTrxEffDate))
                                {
                                    generateHFTP(investmentVOs[j], editTrxVO, segmentVOs[i]);
                                    editTrxVO.setPendingStatus("H");

                                    eventComponent.saveEDITTrxVONonRecursively(editTrxVO);
                                }
                            }
                        }

                        j = investmentVOs.length;
                    }
                }
            }
            catch (Exception e)
            {
                LogEvent logEvent = new LogEvent("Unit Value Update Error on Contract " + segmentVOs[i].getContractNumber(), e);

                Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                logger.error(logEvent);

                updateSuccessful = false;
            }
        }

        return updateSuccessful;
    }

    private boolean processTransactionsForUnitValueUpdate(EDITTrxVO[] editTrxVOs, Hashtable unitValuesHT, boolean updateSuccessful) throws Exception
    {
        String[] pendingStatus = new String[] {"H", "L"};
        String previousSegment = "";
        String drivingEffDate = "";
        String segmentFK = "";
        long previousEDITTrxPK = 0;
        long currentEDITTrxPK = 0;

        for (int i = 0; i < editTrxVOs.length; i++)
        {
            try
            {
                currentEDITTrxPK = editTrxVOs[i].getEDITTrxPK();
                ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVOs[i].getParentVO(ClientSetupVO.class);

                ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

                InvestmentAllocationOverrideVO[] invAllocOvrdVOs = contractSetupVO.getInvestmentAllocationOverrideVO();

                segmentFK = contractSetupVO.getSegmentFK() + "";
                if (previousSegment.equals(""))
                {
                    previousSegment = segmentFK;
                    previousEDITTrxPK = currentEDITTrxPK;
                }

                if (!segmentFK.equals(previousSegment) && !drivingEffDate.equals(""))
                {
                    EDITTrxVO[] subsequentTrxs =
                            eventComponent.composeEDITTrxVOBySegmentPendingStatusAndEffDate(Long.parseLong(previousSegment),
                                                                                             pendingStatus,
                                                                                              drivingEffDate,
                                                                                               new ArrayList());
                    if (subsequentTrxs != null)
                    {
                        if (hftpFinalPrice)
                        {
                            checkForHFReceipt(subsequentTrxs,
                                               editTrxVOs[i].getEDITTrxPK(),
                                                editTrxVOs[i].getTrxAmount());
                            subsequentTrxs =
                                    eventComponent.composeEDITTrxVOBySegmentPendingStatusAndEffDate(Long.parseLong(previousSegment),
                                                                                                     pendingStatus,
                                                                                                      drivingEffDate,
                                                                                                       new ArrayList());
                        }

                        updateSuccessful = updateSubsequentTransactions(subsequentTrxs, previousEDITTrxPK, updateSuccessful);
                    }

                    drivingEffDate = "";
                    hftpFinalPrice = false;
                }

                previousSegment = segmentFK;
                previousEDITTrxPK = currentEDITTrxPK;

                drivingEffDate = loopThroughInvestmentOverrides(editTrxVOs[i], invAllocOvrdVOs, unitValuesHT, drivingEffDate);
            }
            catch (Exception e)
            {
                LogEvent logEvent = new LogEvent("Unit Value Update Error on Transaction " + editTrxVOs[i].getTransactionTypeCT(), e);

                Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                logger.error(logEvent);

                updateSuccessful = false;
            }
        }

        if (!drivingEffDate.equals(""))
        {
            ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVOs[editTrxVOs.length - 1].
                                                           getParentVO(ClientSetupVO.class);
            
            ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

            segmentFK = contractSetupVO.getSegmentFK() + "";

            EDITTrxVO[] subsequentTrxs =
                    eventComponent.composeEDITTrxVOBySegmentPendingStatusAndEffDate(Long.parseLong(segmentFK),
                                                                                     pendingStatus,
                                                                                      drivingEffDate,
                                                                                       new ArrayList());
            if (subsequentTrxs != null)
            {
                if (hftpFinalPrice)
                {
                    checkForHFReceipt(subsequentTrxs,
                                       editTrxVOs[editTrxVOs.length - 1].getEDITTrxPK(),
                                        editTrxVOs[editTrxVOs.length - 1].getTrxAmount());
                    subsequentTrxs =
                            eventComponent.composeEDITTrxVOBySegmentPendingStatusAndEffDate(Long.parseLong(segmentFK),
                                                                                             pendingStatus,
                                                                                              drivingEffDate,
                                                                                               new ArrayList());
                }

                updateSuccessful = updateSubsequentTransactions(subsequentTrxs, currentEDITTrxPK, updateSuccessful);
            }
        }

        drivingEffDate = "";

        return updateSuccessful;
    }

    private boolean processTransactionsForUnitValueUpdate(EDITTrxVO[] editTrxVOs, boolean updateSuccessful) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        for (int i = 0; i < editTrxVOs.length; i++)
        {
            ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVOs[i].
                    getParentVO(ClientSetupVO.class);

            ContractSetupVO contractSetupVO =
                    (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

            SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);

            InvestmentVO[] allInvestmentVOs = segmentVO.getInvestmentVO();

            try
            {
                boolean allForwardPricesFound = false;

                if ("TU".equals(editTrxVOs[i].getTransactionTypeCT()))
                {
                    boolean missing = UnitValues.areUnitValuesMissingForInvestmentsWithChargeCodes(
                                    allInvestmentVOs,
                                    editTrxVOs[i].getEffectiveDate());

                    if (! missing)
                    {
                        allForwardPricesFound = true;
                    }

                }
                else
                {
                    allForwardPricesFound =
                            eventComponent.checkForForwardPrices(
                                    allInvestmentVOs,
                                    editTrxVOs[i].getEffectiveDate());

                }


                if (allForwardPricesFound)
                {
                    editTrxVOs[i].setPendingStatus("P");

                    eventComponent.saveEDITTrxVONonRecursively(editTrxVOs[i]);
                }
            }
            catch(Exception e)
            {
                LogEvent logEvent = new LogEvent("Unit Value Update Error on Transaction " + editTrxVOs[i].getTransactionTypeCT(), e);

                Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                logger.error(logEvent);

                updateSuccessful = false;
            }
        }

        return updateSuccessful;
    }

    private EDITBigDecimal getFundValue(BucketVO[] bucketVOs) throws Exception
    {
        EDITBigDecimal fundValue = new EDITBigDecimal();

        if (bucketVOs != null)
        {
            for (int l = 0; l < bucketVOs.length; l++)
            {
                fundValue = fundValue.addEditBigDecimal(new EDITBigDecimal(bucketVOs[l].getCumUnits()));
            }
        }

        return fundValue;
    }

    private EDITTrxVO getEditTrxVO(SegmentVO segmentVO) throws Exception
    {
        EDITTrxVO[] editTrxVOs = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(GroupSetupVO.class);

        if (segmentVO.getSegmentStatusCT().equalsIgnoreCase("FSHedgeFundPend"))
        {
            editTrxVOs = eventComponent.
                          composeEDITTrxVOBySegmentPKAndTrxType(segmentVO.getSegmentPK(),
                                                                new String[] {"FS"},
                                                                voInclusionList);
        }
        else if (segmentVO.getSegmentStatusCT().equalsIgnoreCase("DeathHedgeFundPend"))
        {
            editTrxVOs = eventComponent.
                          composeEDITTrxVOBySegmentPKAndTrxType(segmentVO.getSegmentPK(),
                                                                new String[] {"DE"},
                                                                voInclusionList);
        }

        if (editTrxVOs != null && editTrxVOs.length > 0)
        {
            return editTrxVOs[0];
        }
        else
        {
            return null;
        }
    }

    private boolean updateSubsequentTransactions(EDITTrxVO[] subsequentTrxs, long drivingEDITTrxPK, boolean updateSuccessful) throws Exception
    {
        for (int j = 0; j < subsequentTrxs.length; j++)
        {
            try
            {
                if (subsequentTrxs[j].getEDITTrxPK() != drivingEDITTrxPK)
                {
                    if (subsequentTrxs[j].getPendingStatus().equalsIgnoreCase("H"))
                    {
                        subsequentTrxs[j].setPendingStatus("C");
                    }
                    else
                    {
                        subsequentTrxs[j].setPendingStatus("S");
                    }

                    eventComponent.saveEDITTrxVONonRecursively(subsequentTrxs[j]);
                }
            }
            catch(Exception e)
            {
                LogEvent logEvent = new LogEvent("Unit Value Update Error on Transaction " + subsequentTrxs[j].getTransactionTypeCT(), e);

                Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                logger.error(logEvent);

                updateSuccessful = false;
            }
        }

        return updateSuccessful;

    }

    private String loopThroughInvestmentOverrides(EDITTrxVO editTrxVO,
                                                   InvestmentAllocationOverrideVO[] invAllocOvrdVOs,
                                                    Hashtable unitValuesHT,
                                                     String drivingEffDate) throws Exception
    {
        EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        for (int i = 0; i < invAllocOvrdVOs.length; i++)
        {
            InvestmentVO investmentVO = (InvestmentVO) invAllocOvrdVOs[i].getParentVO(InvestmentVO.class);
            String filteredFundFK = investmentVO.getFilteredFundFK() + "";
            String chargeCodeFK = "0";

            InvestmentHistoryVO[] investmentHistoryVOs = editTrxVO.getEDITTrxHistoryVO(0).getInvestmentHistoryVO();
            for (int j = 0; j < investmentHistoryVOs.length; j++)
            {
                if (investmentHistoryVOs[j].getInvestmentFK() == investmentVO.getInvestmentPK())
                {
                    chargeCodeFK = investmentHistoryVOs[j].getChargeCodeFK() + "";
                }
            }

            Enumeration uvHTKeys = unitValuesHT.keys();

            while (uvHTKeys.hasMoreElements())
            {
                String key = (String) uvHTKeys.nextElement();
                String uvFilteredFundFK = key.substring(0, key.indexOf("_"));
                String uvChargeCodeFK = key.substring(key.indexOf("_") + 1);

                if (uvFilteredFundFK.equals(filteredFundFK) &&
                    uvChargeCodeFK.equals(chargeCodeFK))
                {
                    UnitValuesVO unitValuesVO = (UnitValuesVO) unitValuesHT.get(key);
                    EDITDate edUVEffDate = new EDITDate(unitValuesVO.getEffectiveDate());

                    if (edUVEffDate.before(edTrxEffDate))
                    {
                        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("HFTA"))
                        {
                            editTrxVO.setPendingStatus("B");

                            if (drivingEffDate.equals(""))
                            {
                                drivingEffDate = editTrxVO.getEffectiveDate();
                            }

                            eventComponent.saveEDITTrxVONonRecursively(editTrxVO);
                        }
                    }
                    else if (edUVEffDate.afterOREqual(edTrxEffDate))
                    {
                        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("HFTA") ||
                            editTrxVO.getTransactionTypeCT().equalsIgnoreCase("HFTP"))
                        {
                            editTrxVO.setPendingStatus("F");

                            if (drivingEffDate.equals(""))
                            {
                                drivingEffDate = editTrxVO.getEffectiveDate();
                                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("HFTP"))
                                {
                                    hftpFinalPrice = true;
                                    EDITBigDecimal units = new EDITBigDecimal();
                                    for (int j = 0; j < investmentHistoryVOs.length; j++)
                                    {
                                        if (investmentHistoryVOs[j].getInvestmentFK() == investmentVO.getInvestmentPK())
                                        {
                                            units = new EDITBigDecimal(investmentHistoryVOs[j].getInvestmentUnits());
                                        }
                                    }

                                    EDITBigDecimal finalPrice = new EDITBigDecimal();
                                    finalPrice = units.multiplyEditBigDecimal(unitValuesVO.getUnitValue());

                                    EDITBigDecimal additionalNotifyAmt = finalPrice.subtractEditBigDecimal(editTrxVO.getNotificationAmount());
                                    additionalNotifyAmt = Util.roundToNearestCent(additionalNotifyAmt);
                                    createEDITTrxCorrespondence(additionalNotifyAmt, editTrxVO);

                                    editTrxVO.setTrxAmount(Util.roundToNearestCent(finalPrice).getBigDecimal());
                                }
                            }

                            eventComponent.saveEDITTrxVONonRecursively(editTrxVO);
                        }
                    }
                } // end if
            }// end while
        } // end for

        return drivingEffDate;
    }

    private void checkForHFReceipt(EDITTrxVO[] subsequentTrxs,
                                    long originatingEditTrxPK,
                                     BigDecimal newTrxAmount) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        TransactionPriorityVO trxPriorityVO = null;

        String hfReceiptDate = null;
        int hfReceiptTrxPriority = 0;

        for (int i = 0; i < subsequentTrxs.length; i++)
        {
            if (subsequentTrxs[i].getOriginatingTrxFK() == originatingEditTrxPK &&
                subsequentTrxs[i].getTransferTypeCT().equalsIgnoreCase("HFReceipt"))
            {
                hfReceiptDate = subsequentTrxs[i].getEffectiveDate();
                trxPriorityVO = eventComponent.composeTransactionPriorityVOByTransactionType(subsequentTrxs[i].getTransactionTypeCT(), new ArrayList());
                hfReceiptTrxPriority = trxPriorityVO.getPriority();
                i = subsequentTrxs.length;
            }
        }

        if (hfReceiptDate != null)
        {
            EDITDate edHFReceiptDate = new EDITDate(hfReceiptDate);
            EDITDate edTrxEffDate = null;
            for (int i = subsequentTrxs.length - 1; i > -1; i--)
            {
                trxPriorityVO = eventComponent.composeTransactionPriorityVOByTransactionType(subsequentTrxs[i].getTransactionTypeCT(), new ArrayList());
                edTrxEffDate = new EDITDate(subsequentTrxs[i].getEffectiveDate());
                if (edTrxEffDate.after(edHFReceiptDate) ||
                    (edTrxEffDate.equals(edHFReceiptDate) && trxPriorityVO.getPriority() >= hfReceiptTrxPriority))
                {
                    long suspenseFK = eventComponent.reverseClientTrx(subsequentTrxs[i].getEDITTrxPK(), "System", null);
                    generateNewTrx(subsequentTrxs[i].getEDITTrxPK(), suspenseFK, newTrxAmount, originatingEditTrxPK);
                }
            }
        }
    }

    private void generateNewTrx(long oldEditTrxPK,
                                 long suspenseFK,
                                  BigDecimal newTrxAmount,
                                   long originatingEditTrxPK) throws Exception
    {
        List editTrxVOInclusionList = setEditTrxVOInclusionList();

        EDITTrxVO oldEditTrxVO = eventComponent.composeEDITTrxVOByEDITTrxPK(oldEditTrxPK, editTrxVOInclusionList);
        ClientSetupVO oldClientSetupVO = (ClientSetupVO) oldEditTrxVO.getParentVO(ClientSetupVO.class);
        ContractSetupVO oldContractSetupVO = (ContractSetupVO) oldClientSetupVO.getParentVO(ContractSetupVO.class);
        SegmentVO segmentVO = (SegmentVO) oldContractSetupVO.getParentVO(SegmentVO.class);
        InvestmentAllocationOverrideVO[] oldInvAllocOvrdVOs = oldContractSetupVO.getInvestmentAllocationOverrideVO();

        EDITBigDecimal edNewTrxAmount = new EDITBigDecimal(newTrxAmount);

        //generate new HFTA with new trxAmount && proper funds
        GroupSetupVO groupSetupVO = createNewGroupSetupVO(oldEditTrxVO);
        ContractSetupVO contractSetupVO = createNewContractSetupVO(oldContractSetupVO, edNewTrxAmount);
        ClientSetupVO clientSetupVO = createNewClientSetupVO(oldClientSetupVO);
        SuspenseVO suspenseVO = checkForSuspense(suspenseFK, contractSetupVO, edNewTrxAmount, oldEditTrxVO.getTrxAmount());

        removeClassesFromVOInclusionList(editTrxVOInclusionList);

        if (oldEditTrxVO.getOriginatingTrxFK() > 0)
        {
            EDITTrxVO originatingTrx = eventComponent.composeOriginatingEDITTrxVOByOriginatingEditTrxFK(oldEditTrxVO.getOriginatingTrxFK(), editTrxVOInclusionList);
            if (originatingTrx != null)
            {
                createNewInvAllocOvrdVOs(contractSetupVO, originatingTrx, oldInvAllocOvrdVOs,
                                          editTrxVOInclusionList, edNewTrxAmount);
            }
        }

        EDITTrxVO newHFTEditTrxVO = createNewEditTrxVO(oldEditTrxVO, contractSetupVO, originatingEditTrxPK);

        clientSetupVO.addEDITTrxVO(newHFTEditTrxVO);
        contractSetupVO.addClientSetupVO(clientSetupVO);
        groupSetupVO.addContractSetupVO(contractSetupVO);

        GroupTrx groupTrx = new GroupTrx();

        groupTrx.saveGroupSetup(groupSetupVO,
                                newHFTEditTrxVO,
                                newHFTEditTrxVO.getTransactionTypeCT(),
                                segmentVO.getOptionCodeCT(),
                                segmentVO.getProductStructureFK(),
                                0,
                                null);

        if (suspenseVO != null)
        {
            EDITBigDecimal pendingSuspenseAmount = new EDITBigDecimal(suspenseVO.getPendingSuspenseAmount());
            pendingSuspenseAmount = pendingSuspenseAmount.addEditBigDecimal(newTrxAmount);

            suspenseVO.setPendingSuspenseAmount(Util.roundToNearestCent(pendingSuspenseAmount).getBigDecimal());

            eventComponent.saveSuspenseNonRecursively(suspenseVO);
        }
    }

    private GroupSetupVO createNewGroupSetupVO(EDITTrxVO oldEditTrxVO)
    {
        GroupSetupVO oldGroupSetupVO = (GroupSetupVO) oldEditTrxVO.getParentVO(ClientSetupVO.class).
                                                                    getParentVO(ContractSetupVO.class).
                                                                     getParentVO(GroupSetupVO.class);
        GroupSetupVO newGroupSetupVO = new GroupSetupVO();
        newGroupSetupVO = oldGroupSetupVO;
        newGroupSetupVO.setGroupSetupPK(0);
        if (newGroupSetupVO.getScheduledEventVOCount() > 0)
        {
            ScheduledEventVO[] oldSchedEventVO = oldGroupSetupVO.getScheduledEventVO();

            for (int i = 0; i < oldSchedEventVO.length; i++)
            {
                oldSchedEventVO[i].setScheduledEventPK(0);
                oldSchedEventVO[i].setStartDate(oldEditTrxVO.getEffectiveDate());
                oldSchedEventVO[i].setStopDate(oldEditTrxVO.getEffectiveDate());
                oldSchedEventVO[i].setGroupSetupFK(0);
            }
        }
        
        return newGroupSetupVO;        
    }
    
    private ContractSetupVO createNewContractSetupVO(ContractSetupVO oldContractSetupVO, EDITBigDecimal edNewTrxAmount)
    {
        ContractSetupVO newContractSetupVO = new ContractSetupVO();
        newContractSetupVO = oldContractSetupVO;
        newContractSetupVO.removeAllInvestmentAllocationOverrideVO();
        newContractSetupVO.setContractSetupPK(0);
        newContractSetupVO.setGroupSetupFK(0);
        newContractSetupVO.setPolicyAmount(Util.roundToNearestCent(edNewTrxAmount).getBigDecimal());
        
        return newContractSetupVO;
    }
    
    private ClientSetupVO createNewClientSetupVO(ClientSetupVO oldClientSetupVO)
    {
        ClientSetupVO newClientSetupVO = new ClientSetupVO();
        newClientSetupVO = oldClientSetupVO;
        newClientSetupVO.setClientSetupPK(0);
        newClientSetupVO.setContractSetupFK(0);
        newClientSetupVO.removeAllEDITTrxVO();
        if (newClientSetupVO.getContractClientAllocationOvrdVOCount() > 0)
        {
            ContractClientAllocationOvrdVO[] oldContractClientAllocOvrdVO = oldClientSetupVO.getContractClientAllocationOvrdVO();
            
            for (int i = 0; i < oldContractClientAllocOvrdVO.length; i++)
            {
                oldContractClientAllocOvrdVO[i].setContractClientAllocationOvrdPK(0);
                oldContractClientAllocOvrdVO[i].setClientSetupFK(0);
            }
        }

        return newClientSetupVO;
    }

    private SuspenseVO checkForSuspense(long suspenseFK,
                                         ContractSetupVO contractSetupVO,
                                          EDITBigDecimal edNewTrxAmount,
                                           BigDecimal oldEditTrxAmt) throws Exception
    {
        SuspenseVO suspenseVO = null;
        
        if (suspenseFK > 0)
        {
            suspenseVO = eventComponent.composeSuspenseVO(suspenseFK, new ArrayList());

            edNewTrxAmount = edNewTrxAmount.subtractEditBigDecimal(oldEditTrxAmt);

            OutSuspenseVO outSuspenseVO = createOutSuspenseVO(edNewTrxAmount.getBigDecimal(), suspenseVO);
            contractSetupVO.addOutSuspenseVO(outSuspenseVO);
        }
        
        return suspenseVO;
    }
    
    private void createNewInvAllocOvrdVOs(ContractSetupVO contractSetupVO,
                                           EDITTrxVO originatingTrx,
                                            InvestmentAllocationOverrideVO[] oldInvAllocOvrdVOs,
                                             List editTrxVOInclusionList,
                                              EDITBigDecimal edNewTrxAmount) throws Exception
    {
        if (originatingTrx.getTransactionTypeCT().equalsIgnoreCase("HFTA") ||
            originatingTrx.getTransactionTypeCT().equalsIgnoreCase("HFTP"))
        {
            contractSetupVO.removeAllInvestmentAllocationOverrideVO();

            if (oldInvAllocOvrdVOs != null)
            {
                for (int i = 0; i < oldInvAllocOvrdVOs.length; i++)
                {
                    if (oldInvAllocOvrdVOs[i].getToFromStatus().equalsIgnoreCase("F"))
                    {
                        InvestmentAllocationOverrideVO newHFTInvAllocOvrdVO = new InvestmentAllocationOverrideVO();
                        newHFTInvAllocOvrdVO = oldInvAllocOvrdVOs[i];
                        newHFTInvAllocOvrdVO.setInvestmentAllocationOverridePK(0);
                        newHFTInvAllocOvrdVO.setContractSetupFK(0);

                        contractSetupVO.addInvestmentAllocationOverrideVO(newHFTInvAllocOvrdVO);
                    }
                }
            }

            //need to go back to trx that generated the HFTP/HFTA transactions to get original "To" fund(s)
            originatingTrx = eventComponent.composeOriginatingEDITTrxVOByOriginatingEditTrxFK(originatingTrx.getOriginatingTrxFK(), editTrxVOInclusionList);
            InvestmentAllocationOverrideVO[] hftInvAllocOvrdVOs =
               ((ContractSetupVO) originatingTrx.getParentVO(ClientSetupVO.class).
                                                  getParentVO(ContractSetupVO.class)).
                                                   getInvestmentAllocationOverrideVO();

            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

            List investmentVOInclusionList = new ArrayList();
            investmentVOInclusionList.add(InvestmentAllocationVO.class);

            for (int i = 0; i < hftInvAllocOvrdVOs.length; i++)
            {
                if (hftInvAllocOvrdVOs[i].getToFromStatus().equalsIgnoreCase("T"))
                {
                    Investment hftInvestment = new Investment(contractLookup.composeInvestmentVOByPK(hftInvAllocOvrdVOs[i].getInvestmentFK(), investmentVOInclusionList));
                    InvestmentAllocation investmentAllocation = new InvestmentAllocation(hftInvestment.getInvestmentAllocationVOs());
                    BigDecimal invAlloc = investmentAllocation.getAllocationByPK(hftInvAllocOvrdVOs[i].getInvestmentAllocationFK());
                    EDITBigDecimal fundDollars = new EDITBigDecimal();
                    fundDollars = edNewTrxAmount.multiplyEditBigDecimal(invAlloc);
                    long hftInvAllocPK = investmentAllocation.getPKForAllocationDollars(fundDollars);
                    if (hftInvAllocPK == 0)
                    {
                        hftInvAllocPK = createNewInvestmentAllocation(hftInvestment, Util.roundToNearestCent(fundDollars).getBigDecimal(), "Dollars");
                    }

                    InvestmentAllocationOverrideVO newHFTInvAllocOvrdVO = new InvestmentAllocationOverrideVO();
                    newHFTInvAllocOvrdVO.setInvestmentAllocationOverridePK(0);
                    newHFTInvAllocOvrdVO.setContractSetupFK(0);
                    newHFTInvAllocOvrdVO.setInvestmentFK(hftInvAllocOvrdVOs[i].getInvestmentFK());
                    newHFTInvAllocOvrdVO.setInvestmentAllocationFK(hftInvAllocPK);
                    newHFTInvAllocOvrdVO.setHFStatus("A");
                    newHFTInvAllocOvrdVO.setHFIAIndicator("N");
                    newHFTInvAllocOvrdVO.setHoldingAccountIndicator("N");
                    newHFTInvAllocOvrdVO.setToFromStatus("T");

                    contractSetupVO.addInvestmentAllocationOverrideVO(newHFTInvAllocOvrdVO);
                }
            }
        }
        else
        {
            if (oldInvAllocOvrdVOs != null)
            {
                for (int i = 0; i < oldInvAllocOvrdVOs.length; i++)
                {
                    oldInvAllocOvrdVOs[i].setInvestmentAllocationOverridePK(0);
                    oldInvAllocOvrdVOs[i].setContractSetupFK(0);
                    contractSetupVO.addInvestmentAllocationOverrideVO(oldInvAllocOvrdVOs[i]);
                }
            }
        }
    }
    
    private EDITTrxVO createNewEditTrxVO(EDITTrxVO oldEditTrxVO,
                                          ContractSetupVO contractSetupVO,
                                           long originatingEditTrxPK)
    {
        EDITTrxVO newHFTEditTrxVO = new EDITTrxVO();
        newHFTEditTrxVO.setEDITTrxPK(0);
        newHFTEditTrxVO.setClientSetupFK(0);
        newHFTEditTrxVO.setEffectiveDate(oldEditTrxVO.getEffectiveDate());
        newHFTEditTrxVO.setStatus("N");
        newHFTEditTrxVO.setPendingStatus("P");
        newHFTEditTrxVO.setSequenceNumber(1);
        newHFTEditTrxVO.setTransactionTypeCT(oldEditTrxVO.getTransactionTypeCT());
        newHFTEditTrxVO.setTaxYear(Integer.parseInt(oldEditTrxVO.getEffectiveDate().substring(0, 4)));
        if (oldEditTrxVO.getTransferTypeCT() != null &&
            (oldEditTrxVO.getTransferTypeCT().equalsIgnoreCase("HFReceipt") &&
             oldEditTrxVO.getOriginatingTrxFK() == originatingEditTrxPK))
        {
            newHFTEditTrxVO.setTrxAmount(contractSetupVO.getPolicyAmount());
        }
        else
        {
            newHFTEditTrxVO.setTrxAmount(oldEditTrxVO.getTrxAmount());
        }

        newHFTEditTrxVO.setOriginatingTrxFK(oldEditTrxVO.getOriginatingTrxFK());
        newHFTEditTrxVO.setNoCorrespondenceInd("Y");
        newHFTEditTrxVO.setNoAccountingInd("N");
        newHFTEditTrxVO.setNoCommissionInd("N");
        newHFTEditTrxVO.setZeroLoadInd("N");
        newHFTEditTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        newHFTEditTrxVO.setOperator("System");
        newHFTEditTrxVO.setTransferTypeCT(oldEditTrxVO.getTransferTypeCT());
        newHFTEditTrxVO.setPremiumDueCreatedIndicator("N");

        return newHFTEditTrxVO;
    }

    private OutSuspenseVO createOutSuspenseVO(BigDecimal trxAmount,
                                               SuspenseVO suspenseVO) throws Exception
    {
        OutSuspenseVO outSuspenseVO = new OutSuspenseVO();
        outSuspenseVO.setOutSuspensePK(0);
        outSuspenseVO.setSuspenseFK(suspenseVO.getSuspensePK());
        outSuspenseVO.setContractSetupFK(0);
        outSuspenseVO.setAmount(Util.roundToNearestCent(new EDITBigDecimal(trxAmount)).getBigDecimal());

        return outSuspenseVO;
    }

    private void createEDITTrxCorrespondence(EDITBigDecimal additionalNotificationAmount, EDITTrxVO editTrxVO) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(TransactionCorrespondenceVO.class);

        TransactionPriorityVO trxPriorityVO = eventComponent.composeTransactionPriorityVOByTransactionType("HFTP", voInclusionList);
        TransactionCorrespondenceVO[] trxCorrVO = trxPriorityVO.getTransactionCorrespondenceVO();
        long transactionCorrespondencePK = 0;

        for (int i = 0; i < trxCorrVO.length; i++)
        {
            if (trxCorrVO[i].getCorrespondenceTypeCT().equalsIgnoreCase("HedgeFundFinal"))
            {
                transactionCorrespondencePK = trxCorrVO[i].getTransactionCorrespondencePK();
            }
        }

        EDITTrxCorrespondenceVO editTrxCorrVO = new EDITTrxCorrespondenceVO();
        editTrxCorrVO.setEDITTrxCorrespondencePK(0);
        editTrxCorrVO.setEDITTrxFK(editTrxVO.getEDITTrxPK());
        editTrxCorrVO.setCorrespondenceDate(new EDITDate().getFormattedDate());
        editTrxCorrVO.setNotificationAmount(additionalNotificationAmount.getBigDecimal());
        editTrxCorrVO.setTransactionCorrespondenceFK(transactionCorrespondencePK);
        EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence(editTrxCorrVO);

        editTrxCorrespondence.save();
    }

    private void generateHFTP(InvestmentVO investmentVO, EDITTrxVO editTrxVO, SegmentVO segmentVO) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        Investment investment = new Investment(investmentVO);

        InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());

        EDITDate edOrigEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);

        FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundByPK(investmentVO.getFilteredFundFK());

        int notificationDays = 0;
        String notificationMode = "";
        String notificationDaysType = "";
        EDITDate hftpEffectiveDate = new EDITDate();

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FS"))
        {
            notificationDays = filteredFundVO[0].getFullSurrenderDays();
            notificationMode = filteredFundVO[0].getFullSurrenderModeCT();
            notificationDaysType = filteredFundVO[0].getFullSurrenderDaysTypeCT();
        }
        else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("DE"))
        {
            notificationDays = filteredFundVO[0].getDeathDays();
            notificationMode = filteredFundVO[0].getDeathModeCT();
            notificationDaysType = filteredFundVO[0].getDeathDaysTypeCT();
        }

        if (notificationDaysType != null && notificationDaysType.equalsIgnoreCase("Business"))
        {
            BusinessCalendar businessCalendar = new BusinessCalendar();
            BusinessDay businessDay = businessCalendar.findNextBusinessDay(edOrigEffDate, notificationDays);
            edOrigEffDate = businessDay.getBusinessDate();
        }
        else
        {
            edOrigEffDate = edOrigEffDate.addDays(notificationDays);
        }

        if (notificationMode.equalsIgnoreCase("Monthly"))
        {
            hftpEffectiveDate = edOrigEffDate.getEndOfMonthDate();
        }
        else if(notificationMode.equalsIgnoreCase("Annual"))
        {
            hftpEffectiveDate = edOrigEffDate.getEndOfYearDate();
        }
        else
        {
            hftpEffectiveDate = edOrigEffDate.getEndOfModeDate(notificationMode);
        }

        //CheckForExisting HFTP for this investment/fund
        boolean generateHFTP = checkForExistingHFTP(investmentVO.getInvestmentPK(), segmentVO.getSegmentPK(), hftpEffectiveDate.getFormattedDate());

        if (generateHFTP)
        {
            GroupSetupVO hftpGroupSetupVO = createNewGroupSetupVO(editTrxVO);

            ContractSetupVO hftpContractSetupVO = new ContractSetupVO();
            hftpContractSetupVO.setContractSetupPK(0);
            hftpContractSetupVO.setGroupSetupFK(0);
            hftpContractSetupVO.setSegmentFK(investmentVO.getSegmentFK());

            //create the override for the HF
            long hfInvAllocPK = investmentAllocation.getPKForAllocationPercent(new BigDecimal(1));
            if (hfInvAllocPK == 0)
            {
                hfInvAllocPK = createNewInvestmentAllocation(investment, new BigDecimal(1), "Percent");
            }

            InvestmentAllocationOverrideVO invAllocOvrdVO2 = new InvestmentAllocationOverrideVO();
            InvestmentAllocationOverrideVO invAllocOvrdVO1 = new InvestmentAllocationOverrideVO();

            //create the override for the Hedge Fund
            invAllocOvrdVO2.setInvestmentAllocationOverridePK(0);
            invAllocOvrdVO2.setContractSetupFK(0);
            invAllocOvrdVO2.setInvestmentFK(investmentVO.getInvestmentPK());
            invAllocOvrdVO2.setInvestmentAllocationFK(hfInvAllocPK);
            invAllocOvrdVO2.setHFStatus("A");
            invAllocOvrdVO2.setHFIAIndicator("N");
            invAllocOvrdVO2.setHoldingAccountIndicator("N");
            invAllocOvrdVO2.setToFromStatus("F");
            hftpContractSetupVO.addInvestmentAllocationOverrideVO(invAllocOvrdVO2);

            //create the override for the HA
            long holdingAccountFK = filteredFundVO[0].getHoldingAccountFK();

            FilteredFundVO[] haFilteredFundVOs = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(segmentVO.getProductStructureFK(), holdingAccountFK, new ArrayList());
            if (haFilteredFundVOs != null && haFilteredFundVOs.length > 0)
            {
                invAllocOvrdVO1 = setupHoldingAccount(haFilteredFundVOs[0],
                                                      segmentVO.getInvestmentVO(),
                                                      new BigDecimal(1),
                                                      investmentVO.getSegmentFK());
            }

            hftpContractSetupVO.addInvestmentAllocationOverrideVO(invAllocOvrdVO1);

            clientSetupVO = createNewClientSetupVO(clientSetupVO);

            EDITTrxVO hftpEDITTrxVO = new EDITTrxVO();
            hftpEDITTrxVO.setEDITTrxPK(0);
            hftpEDITTrxVO.setClientSetupFK(0);
            hftpEDITTrxVO.setEffectiveDate(hftpEffectiveDate.getFormattedDate());
            hftpEDITTrxVO.setStatus("N");
            hftpEDITTrxVO.setPendingStatus("P");
            hftpEDITTrxVO.setSequenceNumber(1);
            hftpEDITTrxVO.setTaxYear(hftpEffectiveDate.getYear());
            hftpEDITTrxVO.setTransactionTypeCT("HFTP");
            hftpEDITTrxVO.setOriginatingTrxFK(editTrxVO.getEDITTrxPK());
            hftpEDITTrxVO.setNoAccountingInd("N");
            hftpEDITTrxVO.setNoCommissionInd("N");
            hftpEDITTrxVO.setZeroLoadInd("N");
            hftpEDITTrxVO.setNoCorrespondenceInd("N");
            hftpEDITTrxVO.setPremiumDueCreatedIndicator("N");

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FS"))
            {
                hftpEDITTrxVO.setTransferTypeCT("RedemptionRemoval");
            }
            else
            {
                hftpEDITTrxVO.setTransferTypeCT("RedemptionDeath");
            }

            clientSetupVO.addEDITTrxVO(hftpEDITTrxVO);

            hftpContractSetupVO.addClientSetupVO(clientSetupVO);

            hftpGroupSetupVO.setContractSetupVO(0, hftpContractSetupVO);

            GroupTrx groupTrx = new GroupTrx();

            groupTrx.saveGroupSetup(hftpGroupSetupVO,
                                    hftpEDITTrxVO,
                                    "HFTP",
                                    segmentVO.getOptionCodeCT(),
                                    segmentVO.getProductStructureFK(),
                                    notificationDays,
                                    notificationDaysType);
        }
    }

    private boolean checkForExistingHFTP(long investmentPK, long segmentPK, String hftpEffectiveDate) throws Exception
    {
        event.dm.composer.VOComposer voComposer = new event.dm.composer.VOComposer();

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(InvestmentAllocationOverrideVO.class);

        boolean generateHFTP = true;

        EDITTrxVO[] existingHFTPTrx = DAOFactory.getEDITTrxDAO().findByEffectiveDateAndTrxTypeCT(hftpEffectiveDate, "HFTP", segmentPK);

        if (existingHFTPTrx != null)
        {
            for (int i = 0; i < existingHFTPTrx.length; i++)
            {
                EDITTrxVO editTrxVO = voComposer.composeEDITTrxVOByEDITTrxPK(existingHFTPTrx[i].getEDITTrxPK(), voInclusionList);
                ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
                InvestmentAllocationOverrideVO[] iaOvrdVOs = contractSetupVO.getInvestmentAllocationOverrideVO();
                if (iaOvrdVOs != null)
                {
                    for (int j = 0; j < iaOvrdVOs.length; j++)
                    {
                        if (iaOvrdVOs[j].getInvestmentFK() == investmentPK)
                        {
                            generateHFTP = false;
                        }
                    }
                }
            }
        }

        return generateHFTP;
    }

    private long createNewInvestmentAllocation(Investment investment,
                                                BigDecimal allocation,
                                                 String allocationType) throws Exception
    {
        InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getPK(),
                                                                              allocation,
                                                                               "O", allocationType);

        investmentAllocation.save();
        return investmentAllocation.getNewInvestmentAllocationPK();
    }

    private InvestmentAllocationOverrideVO setupHoldingAccount(FilteredFundVO filteredFundVO,
                                                               InvestmentVO[] investmentVOs,
                                                               BigDecimal haAllocation,
                                                               long segmentFK) throws Exception
    {
        boolean haFound = false;

        InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();

        for (int i = 0; i < investmentVOs.length; i++)
        {
            Investment investment = new Investment(investmentVOs[i]);

            if (investment.getFilteredFundFK().longValue() == filteredFundVO.getFilteredFundPK())
            {
                InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());

                long investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(new EDITBigDecimal(haAllocation));

                if (investmentAllocationPK > 0)
                {
                    InvestmentAllocationOverride investmentAllocationOverride =
                            new InvestmentAllocationOverride(0, investment.getPK(),
                                                              investmentAllocationPK, "T", "A", "N", "Y");
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    haFound = true;
                }
                else
                {
                    investmentAllocation = new InvestmentAllocation(investment.getPK(), haAllocation, "O", "Percent");

                    investmentAllocation.save();
                    long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

                    InvestmentAllocationOverride investmentAllocationOverride =
                            new InvestmentAllocationOverride(0, investment.getPK(),
                                                              newInvestmentAllocationPK, "T", "A", "N", "Y");
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    haFound = true;
                }
            }
        }

        if (!haFound)
        {
            Investment investment = new Investment(segmentFK, filteredFundVO.getFilteredFundPK());

            investment.save();
            long newInvestmentPK = investment.getNewInvestmentPK();

            InvestmentAllocation investmentAllocation = new InvestmentAllocation(newInvestmentPK,
                                                                                 haAllocation,
                                                                                 "O",
                                                                                 "Percent");

            investmentAllocation.save();
            long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

            InvestmentAllocationOverride investmentAllocationOverride =
                    new InvestmentAllocationOverride(0, newInvestmentPK,
                                                      newInvestmentAllocationPK, "T", "A", "N", "Y");
            investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
        }

        return investmentAllocationOverrideVO;
    }

    private void exportToFile(UnitValuesVO unitValueVO, FilteredFund filteredFund)
    {
        Fund fund = filteredFund.getFund();

        ChargeCode chargeCode = ChargeCode.findByPK(unitValueVO.getChargeCodeFK());

        String fundNumber = filteredFund.getFundNumber().trim();
        String chargeCodeColumn = chargeCode == null ? "" : chargeCode.getChargeCode().trim();
        String fundName = fund.getName().trim();
        String effectiveDate = unitValueVO.getEffectiveDate();
        EDITBigDecimal nav1 = new EDITBigDecimal(unitValueVO.getNetAssetValue1());
        EDITBigDecimal nav2 = new EDITBigDecimal(unitValueVO.getNetAssetValue2());
        EDITBigDecimal unitValueColumn = new EDITBigDecimal(unitValueVO.getUnitValue());

        String[] fieldValues = new String[] {fundNumber, chargeCodeColumn, fundName, effectiveDate, nav1.toString(), nav2.toString(), unitValueColumn.toString()};

        String fileData = Util.concatenateStrings(fieldValues, ",") + "\n";

        appendToFile(fileData);
    }

    /**
     * Set up the export file
     * @return  fileName
     */
    private String getExportFile()
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String currentDate = new EDITDate().getFormattedDate();

        currentDate = currentDate.replaceAll(EDITDate.DATE_DELIMITER, "");

        long currentTimeInMillis = CRUD.getNextAvailableKey();

        String exportFile = export1.getDirectory() + PRICE_CONFIRM_FILE_NAME + currentDate + "_" + currentTimeInMillis + ".txt";

        return exportFile;
    }

    /**
     * Append data to export file.
     * @param data
     */
    private void appendToFile(String data)
    {
        BufferedWriter bw = null;

        try
        {
            bw = new BufferedWriter(new FileWriter(exportFile, true));

            bw.write(data);

            bw.flush();

        }
        catch (IOException e)
        {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                bw.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }
    }

    private List setEditTrxVOInclusionList()
    {
        List editTrxVOInclusionList = new ArrayList();
        editTrxVOInclusionList.add(ClientSetupVO.class);
        editTrxVOInclusionList.add(WithholdingOverrideVO.class);
        editTrxVOInclusionList.add(ContractClientAllocationOvrdVO.class);
        editTrxVOInclusionList.add(ContractSetupVO.class);
        editTrxVOInclusionList.add(GroupSetupVO.class);
        editTrxVOInclusionList.add(ScheduledEventVO.class);
        editTrxVOInclusionList.add(ChargeVO.class);
        editTrxVOInclusionList.add(SegmentVO.class);
        editTrxVOInclusionList.add(InvestmentAllocationOverrideVO.class);

        return editTrxVOInclusionList;
    }

    private void removeClassesFromVOInclusionList(List editTrxVOInclusionList)
    {
        editTrxVOInclusionList.remove(SegmentVO.class);
        editTrxVOInclusionList.remove(GroupSetupVO.class);
        editTrxVOInclusionList.remove(WithholdingOverrideVO.class);
        editTrxVOInclusionList.remove(ChargeVO.class);
        editTrxVOInclusionList.remove(ScheduledEventVO.class);
        editTrxVOInclusionList.remove(ContractClientAllocationOvrdVO.class);
    }

}
