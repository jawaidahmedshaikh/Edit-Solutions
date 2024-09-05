/*
 * User: cgleason
 * Date: Sep 16, 2003
 * Time: 2:54:38 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package event.financial.client.strategy;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import contract.*;
import contract.dm.composer.VOComposer;
import edit.common.ChangeProcessor;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITCRUDException;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.AgentSnapshotDetailVO;
import edit.common.vo.AgentSnapshotVO;
import edit.common.vo.AgentVO;
import edit.common.vo.AnnualPremiumVO;
import edit.common.vo.AnnualizedSubBucketVO;
import edit.common.vo.AreaValueVO;
import edit.common.vo.BucketChargeHistoryVO;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.BucketVO;
import edit.common.vo.ChargeHistoryVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.CommissionInvestmentHistoryVO;
import edit.common.vo.CommissionPhaseVO;
import edit.common.vo.CommissionablePremiumHistoryVO;
import edit.common.vo.ContractClientAllocationOvrdVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.ContractRequirementVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITTrxCorrespondenceVO;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FeeDescriptionVO;
import edit.common.vo.FeeVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FilteredRequirementVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.FundVO;
import edit.common.vo.GroupSetupVO;
import edit.common.vo.InSuspenseVO;
import edit.common.vo.InsertRequirementVO;
import edit.common.vo.InvestmentAllocationOverrideVO;
import edit.common.vo.InvestmentAllocationVO;
import edit.common.vo.InvestmentHistoryVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.OutSuspenseVO;
import edit.common.vo.OverdueChargeSettledVO;
import edit.common.vo.OverdueChargeVO;
import edit.common.vo.PremiumDueVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.ReinsuranceHistoryVO;
import edit.common.vo.SegmentHistoryVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.SuspenseVO;
import edit.common.vo.UnitValuesVO;
import edit.common.vo.VOObject;
import edit.common.vo.WithholdingHistoryVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.VOClass;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import engine.Area;
import engine.AreaValue;
import engine.Company;
import engine.Fee;
import engine.FilteredFund;
import engine.sp.SPOutput;
import event.BucketHistory;
import event.ClientSetup;
import event.CommissionHistory;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.GroupSetup;
import event.InvestmentAllocationOverride;
import event.InvestmentHistory;
import event.Suspense;
import event.business.Event;
import event.component.EventComponent;
import event.dm.composer.EDITTrxComposer;
import event.dm.dao.DAOFactory;
import event.dm.dao.OverdueChargeDAO;
import event.financial.client.trx.ClientTrx;
import event.financial.group.strategy.SaveGroup;
import fission.utility.*;
import group.ContractGroupInvestment;

import java.math.BigDecimal;
import java.util.*;

import logging.Log;
import logging.LogEvent;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import role.business.Role;
import role.component.RoleComponent;
import role.dm.composer.ClientRoleComposer;


public class NaturalSave
{
    private String cycleDate;
    private int executionMode;

    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    public NaturalSave()
    {

    }

    /**
     * Getter.
     * @return
     */
    public String getCycleDate()
    {
        return cycleDate;
    }

    /**
     * Setter.
     * @param cycleDate
     */
    public void setCycleDate(String cycleDate)
    {
        this.cycleDate = cycleDate;
    }

    /**
     * Getter.
     * @return
     */
    public int getExecutionMode()
    {
        return executionMode;
    }

    /**
     * Setter.
     * @param executionMode
     */
    public void setExecutionMode(int executionMode)
    {
        this.executionMode = executionMode;
    }

    public ClientStrategy[] doUpdates(SPOutput spOutput, EDITTrxVO editTrxVO, String cycleDate, GroupSetupVO groupSetupVO,
                                      SegmentVO baseSegmentVO, int executionMode) throws EDITEventException
    {
    	/*if (baseSegmentVO.getContractNumber().equals("VC00005064")) {
    		System.out.println("Stop");
    	}*/
        this.cycleDate = cycleDate;
        this.executionMode = executionMode;

        //special bucket processing needed for new Buckets to be tagged to history of buckets
        //temporary solution - for now;
        SegmentVO segmentVO = null;
        SegmentVO inSegmentVO = baseSegmentVO;
        EDITTrxHistoryVO editTrxHistoryVO = null;
        List buckets = new ArrayList();
        List bucketHistories = new ArrayList();
        List invHistoriesToSave = new ArrayList();
        List overdueChargesList = new ArrayList();
        List overdueChargesSettledList = new ArrayList();
        List commissionHistories = new ArrayList();
        List segmentHistories = new ArrayList();
        AnnualPremiumVO annualPremiumVO = null;
        PremiumDueVO premiumDueVO = null;
        CommissionPhaseVO commissionPhaseVO = null;

        Hashtable invHistHT = new Hashtable();
        List clientStrategyArray = null;
        ClientStrategy[] clientStrategy = null;

        // SL -- Clear hibernate sessions before starting crud transaction, so that hibernate will not have stale information.
        // If we do not clear the hibernate sessions the method checkForOutSuspense() that is calling event.saveSuspenseForPendingAmount()
        // has hibernate transaction and is updating the database with stale information. (When commit is issued via hibernate
        // all the entities in the session are updated to the database.) We should not have this problem when we completely
        // switch to Hibernate.
        SessionHelper.clearSessions();

        VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

        //  If find Investment in output list, want to save any Buckets and BucketHistories as children of the Investment
        //  The boolean investmentFound means that the Buckets in the loop should not be added to the regular Bucket
        //  processing because they are children of the new Investment and have been saved as such.
        boolean investmentFound = false;
        boolean newPremiumDueFound = false;
        boolean existingCommissionPhaseFound = false;
        boolean contractClientFound = false;

        //The trx components created in scripts will be stored here
        TreeMap editTrxComponents = new TreeMap();
        boolean editTrxUpdated = false;
        EDITTrxVO outputEDITTrxVO = null;

        CRUD crud1 = null;

        try
        {
        	crud1 = CRUDFactory.getSingleton().getCRUD(POOLNAME);
            
            for (int i = 0; i < voObjects.length; i++)
            {
                VOObject voObject = voObjects[i];

                String currentTableName = VOClass.getTableName(voObject.getClass());

                if (currentTableName.equalsIgnoreCase("InvestmentHistory"))
                {
                    InvestmentHistoryVO invHistVO = (InvestmentHistoryVO) voObject;

                    if (new EDITBigDecimal(invHistVO.getInvestmentUnits()).isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) &&
                        new EDITBigDecimal(invHistVO.getInvestmentDollars()).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) &&
                        isForHedgeFund(invHistVO, baseSegmentVO))
                    {
                        invHistHT.put(invHistVO.getInvestmentFK() + "", invHistVO);
                    }
                }
            }

            boolean moneyLocked = false;

            if (invHistHT.size() > 0)
            {
                moneyLocked = checkHFTEligibility(invHistHT, groupSetupVO, editTrxVO, spOutput.getDocument("InvestmentAllocationOverrideDocVO"));
            }

            if (!moneyLocked)
            {
                crud1.startTransaction();

                for (int i = 0; i < voObjects.length; i++)
                {
                    boolean persistCurrentVO = true;

                    VOObject voObject = voObjects[i];

                    String currentTableName = VOClass.getTableName(voObject.getClass());

                    if (currentTableName.equalsIgnoreCase("Segment"))
                    {
                        segmentVO = (SegmentVO) voObject;

                        Segment segment = new Segment(segmentVO);

                        // verify if segment status is changed....
                        if (segment.isSegmentStatusChanged())
                        {
                            // generate change history record
                            new ChangeProcessor().generateChangeHistoryForSegmentStatusChange(segmentVO, editTrxVO.getOperator(), editTrxVO.getEffectiveDate());
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("EDITTrxHistory"))
                    {
                        editTrxHistoryVO = (EDITTrxHistoryVO) voObject;
                        populateEDITTrxHistoryFields(editTrxHistoryVO, editTrxVO);
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("Bucket"))
                    {
                        if (! investmentFound)
                        {
                            buckets.add(voObject);
                        }

                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equals("BucketHistory"))
                    {
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equals("BucketChargeHistory"))
                    {
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("AnnualizedSubBucket"))
                    {
                        BucketVO lastBucketVO = (BucketVO) buckets.get(buckets.size() - 1);
                        lastBucketVO.addAnnualizedSubBucketVO((AnnualizedSubBucketVO) voObject);
                        buckets.set(buckets.size() - 1, lastBucketVO);
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("Investment"))
                    {
                        //New investments(no PK) will be updated using the document later on
                        InvestmentVO investmentVO = (InvestmentVO) voObject;
                        if (investmentVO.getInvestmentPK() == 0)
                        {
                            investmentFound = true;
                            persistCurrentVO = false;
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("WithholdingHistory"))
                    {
                        if (editTrxHistoryVO == null)
                        {
                            persistCurrentVO = true;
                        }
                        else
                        {
                            editTrxHistoryVO.addWithholdingHistoryVO((WithholdingHistoryVO) voObject);
                            persistCurrentVO = false;
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("ChargeHistory"))
                    {
                        if (editTrxHistoryVO == null)
                        {
                            persistCurrentVO = true;
                        }
                        else
                        {
                            editTrxHistoryVO.addChargeHistoryVO((ChargeHistoryVO) voObject);
                            persistCurrentVO = false;
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("CommissionHistory"))
                    {
                        commissionHistories.add((CommissionHistoryVO) voObject);
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("CommissionInvestmentHistory"))
                    {
                        CommissionHistoryVO lastCommissionHistoryVO = (CommissionHistoryVO) commissionHistories.get(commissionHistories.size() - 1);
                        lastCommissionHistoryVO.addCommissionInvestmentHistoryVO((CommissionInvestmentHistoryVO) voObject);
                        commissionHistories.set(commissionHistories.size() - 1, lastCommissionHistoryVO);
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("FinancialHistory"))
                    {
                        FinancialHistoryVO financialHistoryVO = (FinancialHistoryVO) voObject;

                        if (financialHistoryVO.getTaxableIndicator() == null)
                        {
                            financialHistoryVO.setTaxableIndicator("Y");
                        }

                        voObject = financialHistoryVO;

                        if (editTrxHistoryVO == null)
                        {
                            persistCurrentVO = true;
                        }
                        else
                        {
                            editTrxHistoryVO.addFinancialHistoryVO(financialHistoryVO);
                            persistCurrentVO = false;
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("CommissionablePremiumHistory"))
                    {
                        CommissionablePremiumHistoryVO commPremHistoryVO = (CommissionablePremiumHistoryVO) voObject;

                        if (editTrxHistoryVO == null)
                        {
                            persistCurrentVO = true;
                        }
                        else
                        {
                            editTrxHistoryVO.addCommissionablePremiumHistoryVO(commPremHistoryVO);
                            persistCurrentVO = false;
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("ReinsuranceHistory"))
                    {
                        ReinsuranceHistoryVO reinsuranceHistoryVO = (ReinsuranceHistoryVO) voObject;
                        reinsuranceHistoryVO.setOperator(editTrxVO.getOperator());
                        reinsuranceHistoryVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
                        reinsuranceHistoryVO.setAccountingPendingStatus("Y");

                        if (editTrxHistoryVO == null)
                        {
                            persistCurrentVO = true;
                        }
                        else
                        {
                            editTrxHistoryVO.addReinsuranceHistoryVO(reinsuranceHistoryVO);
                            persistCurrentVO = false;
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("InvestmentHistory"))
                    {
                        InvestmentHistoryVO invHistVO = (InvestmentHistoryVO) voObject;

                        if (new EDITBigDecimal(invHistVO.getInvestmentUnits()).isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) &&
                            new EDITBigDecimal(invHistVO.getInvestmentDollars()).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) &&
                            isForHedgeFund(invHistVO, baseSegmentVO))
                            // isForHedgeFund() makes sure it is a hedge fund.
                            // Keep this test last.  Most of the time,
                            // the first test will be false so won't need to bother
                            // with isForHedgeFund().
                        {
    //                        invHistHT.put(invHistVO.getInvestmentFK() + "", invHistVO);
                        }
                        else
                        {
                            if (! investmentFound)
                            {
                                invHistoriesToSave.add(invHistVO);
                            }
                        }
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("OverdueCharge"))
                    {
                        overdueChargesList.add(voObject);
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("OverdueChargeSettled"))
                    {
                        overdueChargesSettledList.add(voObject);
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("SegmentHistory"))
                    {
                        SegmentHistoryVO segmentHistoryVO = (SegmentHistoryVO) voObject;

                        if (editTrxHistoryVO == null)
                        {
                            persistCurrentVO = true;
                        }
                        else
                        {
                            editTrxHistoryVO.addSegmentHistoryVO(segmentHistoryVO);
                            persistCurrentVO = false;
                        }
                    }
                    else if (currentTableName.equalsIgnoreCase("SegmentSecondary"))
                    {
                    	persistCurrentVO = true;
                    }
                    else if (currentTableName.equalsIgnoreCase("NaturalDoc"))
                    {
                        persistCurrentVO = false;
                    }
                    //capture each groupSetup created in a table for processing later
                    else if (currentTableName.equalsIgnoreCase("GroupSetup"))
                    {
                        persistCurrentVO = false;
                        editTrxComponents.put(i + "", voObject);
                    }
                    else if (currentTableName.equalsIgnoreCase("ContractSetup"))
                    {
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("InvestmentAllocationOverride"))
                    {
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("ClientSetup"))
                    {
                        persistCurrentVO = false;
                    }
                     else if (currentTableName.equalsIgnoreCase("EDITTrx"))
                    {
                        if (!editTrxUpdated)
                        {
                            outputEDITTrxVO = (EDITTrxVO)voObject;
                            if (outputEDITTrxVO.getEDITTrxPK() == editTrxVO.getEDITTrxPK())
                            {
                                editTrxVO = outputEDITTrxVO;
                                editTrxUpdated = true;
                            }
                        }

                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("OutSuspense"))
                    {
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("PremiumDue"))
                    {
                    	premiumDueVO = (PremiumDueVO) voObject;
                    	
                    	if (premiumDueVO.getPremiumDuePK() == 0) {
                    		newPremiumDueFound = true;
	                        persistCurrentVO = false;
                    	} else {
                    		// save the updated PD
	                        persistCurrentVO = true;
                    	}
                    }
                    else if (currentTableName.equalsIgnoreCase("CommissionPhase"))
                    {
                        persistCurrentVO = false;
                                            	
                    	commissionPhaseVO = (CommissionPhaseVO) voObject;
                    	
                    	if (commissionPhaseVO.getCommissionPhasePK() != 0) {
                        	existingCommissionPhaseFound = true;
                    	} 
                    }
                    else if (currentTableName.equalsIgnoreCase("AgentSnapshotDetail"))
                    {
                        AgentSnapshotDetailVO asdVO = (AgentSnapshotDetailVO) voObject;
                        AgentSnapshotVO agentSnapshotVO = (AgentSnapshotVO) AgentSnapshot.findBy_PK(new Long(asdVO.getAgentSnapshotPK())).getVO();
                        agentSnapshotVO.setAdvanceAmount(asdVO.getAdvanceAmount());
                        agentSnapshotVO.setAdvanceRecovery(asdVO.getAdvanceRecovery());
                        voObject = agentSnapshotVO;
                    }
                    else if (currentTableName.equalsIgnoreCase("ContractClient"))
                    {
                        contractClientFound = true;
                        persistCurrentVO = false;
                    }
                    else if (currentTableName.equalsIgnoreCase("SegmentDoc"))
                    {

                        persistCurrentVO = false;
                    }

                    if (persistCurrentVO)
                    {
                        crud1.createOrUpdateVOInDBRecursively(voObject, false);
                    }
                }           // End for loop

                if (bucketHistoryExists(voObjects))
                {
                    captureBucketHistories(bucketHistories, spOutput.getDocument("GroupSetupDocVO"));
                    buckets = captureBucketWithHistories(buckets, bucketHistories);
                }

                if (editTrxHistoryVO != null)
                {
                    if (invHistoriesToSave.size() > 0)
                    {
                        editTrxHistoryVO.setInvestmentHistoryVO((InvestmentHistoryVO[]) invHistoriesToSave.toArray(new InvestmentHistoryVO[invHistoriesToSave.size()]));
                    }
                    for (int i = 0; i < commissionHistories.size(); i++)
                    {
                        editTrxHistoryVO.addCommissionHistoryVO((CommissionHistoryVO) commissionHistories.get(i));
                    }
                }
                else
                {
                    if (invHistoriesToSave.size() > 0)
                    {
                        updateInvestmentHistory(invHistoriesToSave, crud1);
                    }

                    if (commissionHistories.size() > 0)
                    {
                        updateCommissionHistory(commissionHistories, crud1);
                    }
                }

                Hashtable interimAcctBuckets = null;

                interimAcctBuckets = updateBucketsAndHistory(buckets, editTrxHistoryVO, bucketHistories, editTrxVO, crud1, baseSegmentVO);

                if (investmentFound)
                {
                    updateInvestmentsWithBucketsAndBucketHistory(spOutput.getDocument("InvestmentDocVO"), crud1, editTrxHistoryVO.getEDITTrxHistoryPK(), bucketHistories);
                }

                //  If the PremiumDue exists, it is expected to be a new one
                if (newPremiumDueFound)
                {
                    updatePremiumDue(spOutput.getDocument("SegmentDocVO"), crud1, baseSegmentVO.getSegmentPK(), editTrxVO.getEDITTrxPK());
                }

                //  If the CommissionPhase exists but the PremiumDue doesn't, the CommissionPhase was changed
                //  and we want to persist it
                if (existingCommissionPhaseFound && !editTrxVO.getTransactionTypeCT().equals("FI"))
                {
                    updateCommissionPhase(spOutput.getDocument("SegmentDocVO"), crud1);
                }

                if (contractClientFound)
                {
                    updateContractClient(spOutput.getDocument("ClientDocVO"), crud1);
                }

                if (!editTrxComponents.isEmpty())
                {
                    ClientStrategy[] clientStrategyForNewTrx = saveSpawnedEDITTrx(editTrxComponents, voObjects, baseSegmentVO.getProductStructureFK(), crud1, executionMode, cycleDate);
                    clientStrategy = (ClientStrategy[])Util.joinArrays(clientStrategy, clientStrategyForNewTrx, ClientStrategy.class);
                }

                String transactionType = editTrxVO.getTransactionTypeCT();

                if (editTrxVO.getPendingStatus().equalsIgnoreCase("B") ||
                        editTrxVO.getPendingStatus().equalsIgnoreCase("S"))
                {
                    editTrxVO.setPendingStatus("L");
                    resetProcessDateTime(editTrxVO, crud1);
                }
                else if (editTrxVO.getPendingStatus().equalsIgnoreCase("F"))
                {
                    editTrxVO.setPendingStatus("H");
                    resetProcessDateTime(editTrxVO, crud1);
                }
                else
                {
                    if (transactionType.equalsIgnoreCase("HFTA") ||
                        transactionType.equalsIgnoreCase("HFTP") ||
                        transactionType.equalsIgnoreCase("MD") ||
                        transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                        transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT))
                    {
                        //check UnitValues table for forward price.  If found, pending status should be set to "H"
                        //If backward price found, pending status should be set to "L"
                        boolean forwardPriceFound = lookForForwardPrice(editTrxVO, baseSegmentVO);
                        if (forwardPriceFound)
                        {
                            editTrxVO.setPendingStatus("H");
                        }
                        else
                        {
                            editTrxVO.setPendingStatus("L");
                        }
                    }
                    else if ((transactionType.equalsIgnoreCase("FS") &&
                            segmentVO.getSegmentStatusCT().equalsIgnoreCase("FSHedgeFundPend")) ||
                            (transactionType.equalsIgnoreCase("DE") &&
                            segmentVO.getSegmentStatusCT().equalsIgnoreCase("DeathHedgeFundPend")))
                    {
                        editTrxVO.setPendingStatus("L");
                    }
                    else
                    {
                            editTrxVO.setPendingStatus("H");
                    }
                }

                processOverdueChargeRecords(editTrxVO, overdueChargesList, overdueChargesSettledList, crud1);

                SuspenseVO suspenseVO = null;

                crud1.createOrUpdateVOInDB(editTrxVO);

                EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = editTrxVO.getEDITTrxCorrespondenceVO();
                for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
                {
                    crud1.createOrUpdateVOInDB(editTrxCorrespondenceVO[i]);
                }

                if (segmentVO == null)
                {
                    segmentVO = inSegmentVO;
                }

                if (transactionType.equals("PO") || transactionType.equals("FS") ||
                    transactionType.equals("WI") || transactionType.equals("NT") ||
                    transactionType.equals("SW") || transactionType.equals("HREM") ||
                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_NBREFUND) ||
                    transactionType.equals("LS") || transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
                    transactionType.equals("HDTH") || transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_LOAN) ||
                    transactionType.equals("RW") || transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) ||
                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_CLAIM_PAYOUT) ||
                    (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM) &&
                     segmentVO.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PUTERM)) ||
                    (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_POLICYYEAREND) &&
                     segmentVO.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PUTERM)))
                {
                    suspenseVO = createSuspense(segmentVO, editTrxHistoryVO, editTrxVO, groupSetupVO);

                    if (suspenseVO != null)
                    {
                        crud1.createOrUpdateVOInDBRecursively(suspenseVO, false);
                    }
                }

                if (segmentVO == null)
                {
                    segmentVO = inSegmentVO;
                }


                try
                {
                    //Suspense entries need updating for full termination transactions
                    if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER) ||
                        transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_NOTTAKEN))
                    {
                        new Suspense().updateSuspenseAmount_PendingSuspenseAmount(segmentVO.getContractNumber(), transactionType, crud1);
                    }

                    if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_CLAIM_PAYOUT))
                    {
                        boolean validClaimPayoutTrx = determineUseOfClaimPayout(editTrxVO, segmentVO, crud1);
                        if (validClaimPayoutTrx)
                        {
                            new Suspense().updateSuspenseAmount_PendingSuspenseAmount(segmentVO.getContractNumber(), transactionType, crud1);
                        }
                    }
                }
                catch (Exception e)
                {
                      System.out.println(e);
                      e.printStackTrace();
                      new RuntimeException(e);
                }

                if (new Long(segmentVO.getSegmentFK()) == null)
                {
                    EDITTrx.checkForTerminatingTransaction(editTrxVO, segmentVO, crud1);
                }

         		String trxToBeSpawned = findSpawnedTransaction(transactionType, segmentVO);
                if (trxToBeSpawned != null)
                {
                     TrxGeneration trxGeneration = new TrxGeneration();
                    clientStrategy = trxGeneration.generateSpawnedTransaction(trxToBeSpawned, editTrxVO, groupSetupVO, baseSegmentVO, buckets, crud1, clientStrategy, clientStrategyArray);
                }
                else
                {
                    if (transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("TF") || transactionType.equalsIgnoreCase("RN"))
                    {
                        EDITDate edCycleDate = new EDITDate(cycleDate);
                        boolean addToStrategyChain = true;
                        if (executionMode == ClientTrx.REALTIME_MODE)
                        {
                            addToStrategyChain = false;
                        }

                        boolean modifyLookup = false;
                            TrxGeneration trxGeneration = new TrxGeneration();
                        EDITTrxVO[] newEditTrxVOs = trxGeneration.generateRenewalTrx(editTrxVO, groupSetupVO, baseSegmentVO, buckets, crud1, modifyLookup);

                        clientStrategyArray = new ArrayList();

                        for (int i = 0; i < newEditTrxVOs.length; i++)
                        {
                            if (new EDITDate(newEditTrxVOs[i].getEffectiveDate()).before(edCycleDate) ||
                                (addToStrategyChain && new EDITDate(newEditTrxVOs[i].getEffectiveDate()).equals(edCycleDate)))
                            {
                                ClientTrx naturalTrx = new ClientTrx(newEditTrxVOs[i], editTrxVO.getOperator());
                                naturalTrx.setCycleDate(cycleDate);
                                clientStrategyArray.add(new Natural(naturalTrx, "natural"));
                                clientStrategy = (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);
                        }
                    }
                }

                if (transactionType.equalsIgnoreCase("PE"))
                {
                    boolean generateTrx = determineIfRebalTrxNeeded(baseSegmentVO.getSegmentPK(), crud1);

                        long productKey = segmentVO.getProductStructureFK();

                    if (generateTrx)
                    {
                        List voInclusionList = new ArrayList();
                        voInclusionList.add(InvestmentVO.class);
                        voInclusionList.add(InvestmentAllocationVO.class);
                        voInclusionList.add(BucketVO.class);

                        crud1.retrieveVOFromDBRecursively(baseSegmentVO, voInclusionList, true);

                            boolean modifyEffDate = false;

                        TrxGeneration trxGeneration = new TrxGeneration();
                            EDITTrxVO newEditTrxVO = trxGeneration.generateRebalanceTrx(editTrxVO, groupSetupVO, productKey, crud1, baseSegmentVO, modifyEffDate);

                        ClientTrx naturalTrx = new ClientTrx(newEditTrxVO, editTrxVO.getOperator());
                        clientStrategyArray = new ArrayList();
                        clientStrategyArray.add(new Natural(naturalTrx, "natural"));
                            clientStrategy = (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);

                        }
                    }
                }


                if (transactionType.equals("PY") ||
                    transactionType.equals(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
                    transactionType.equals("HFTA") ||
                    transactionType.equals("HFTP") ||
                    transactionType.equals("HDTH"))
                {
                    EDITDateTime processDateTime = null;
                    if (editTrxHistoryVO == null)
                    {
                        processDateTime = new EDITDateTime();
                    }
                    else
                    {
                        processDateTime = new EDITDateTime(editTrxHistoryVO.getProcessDateTime());
                    }

                    checkForOutSuspense(groupSetupVO, crud1, processDateTime.getFormattedDateTime());
                }

                crud1.commitTransaction();
                
                // for charge codes - wait until after commit since other trans may be generated
                ClientStrategy[] clientStrategiesFromTUTrans = null;
                if (transactionType.equalsIgnoreCase("PY"))
                {
                    if ("Y".equalsIgnoreCase(segmentVO.getChargeCodeStatus()))
                    {

                        TrxGeneration trxGeneration = new TrxGeneration();
                        ClientSetupVO clientSetupVO = groupSetupVO.getContractSetupVO(0).getClientSetupVO(0);
                        clientStrategiesFromTUTrans = trxGeneration.createTransferUnitsTrx(new EDITDate(editTrxVO.getEffectiveDate()), editTrxVO.getOperator(), baseSegmentVO, editTrxVO.getEDITTrxPK(), clientSetupVO.getClientRoleFK(), clientSetupVO.getContractClientFK());
                        clientStrategy = (ClientStrategy[])Util.joinArrays(clientStrategy, clientStrategiesFromTUTrans, ClientStrategy.class);

                    }
                }

                if ((transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                     transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT)) &&
                    editTrxVO.getPendingStatus().equalsIgnoreCase("H"))
                {
                    CRUD crud2 = null;

                    try
                    {
                        crud2 = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

                        createDivisionFeeOffset(editTrxVO, segmentVO.getContractNumber(), crud2);
                    }
                    finally
                    {
                        if (crud2 != null)
                        {
                            crud2.close();
                        }

                        crud2 = null;
                    }
                }

                if (invHistHT.size() > 0)
                {
                    ClientStrategy[] HFTclientStrategy = generateHFT(invHistHT, editTrxVO, groupSetupVO, interimAcctBuckets, spOutput.getDocument("InvestmentAllocationOverrideDocVO"));
                    clientStrategy = (ClientStrategy[])Util.joinArrays(clientStrategy, HFTclientStrategy, ClientStrategy.class);
                }

                // Additional logic required for Not_Taken reversal transactions.
                // Query for all CommissionHistory records that have UpdateStatus ='L' and set them to 'D'
                // 'D' = Just mark it uniquely for delete. Do not physically delete the record.
                if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_NOTTAKEN))
                {
                    Segment segment = new Segment(segmentVO);

                    segment.updateCommissionHistoryRecords(crud1, CommissionHistory.UPDATESTATUS_L);
                }

               //generate the value transfer if sls trx
                if (transactionType.equals("SLS"))
                {
                    TrxGeneration trxGeneration = new TrxGeneration();
                    EDITTrxVO VTEditTrxVO = trxGeneration.createVTTransaction(segmentVO, editTrxVO, buckets, editTrxHistoryVO);

                    ClientTrx naturalTrx = new ClientTrx(VTEditTrxVO, editTrxVO.getOperator());
                    clientStrategyArray = new ArrayList();
                    clientStrategyArray.add(new Natural(naturalTrx, "natural"));
                    ClientStrategy[] VTclientStrategy = (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);
                    clientStrategy = (ClientStrategy[])Util.joinArrays(clientStrategy, VTclientStrategy, ClientStrategy.class);
                }


                //  If the segment belongs to a case, update the PolicyGroupInvestment's accumulated units to reflect the change caused by this transaction
                Segment segment = new Segment(segmentVO);
                if (segment.belongsToACase())
                {
                    updateContractGroupInvestment(segmentVO, editTrxVO, editTrxHistoryVO);
            }
            }
            else
            {
                throw new Exception("Requested Amount Exceeds The Unlocked Value");
            }

        }
        catch (Exception e)
        {
            EDITEventException editEventException = new EDITEventException(e.getMessage());

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            try
            {
                crud1.rollbackTransaction();
            }
            catch (EDITCRUDException e1)
            {
                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                Log.logGeneralExceptionToDatabase(null, e);

                EDITEventException editEventException2 = new EDITEventException(e.getMessage());

                editEventException = editEventException2;
            }

            throw editEventException;
        }
        finally
        {
            if (crud1 != null)
                crud1.close();

            crud1 = null;
        }

        return clientStrategy;
    }
    
    private boolean determineUseOfClaimPayout(EDITTrxVO editTrxVO, SegmentVO segmentVO, CRUD crud1)
    {
        boolean validClaimPayoutTrx = false;

        EDITTrxVO[] rclEditTrxVOs = new event.dm.dao.FastDAO().findMaxEffectiveDate(segmentVO.getSegmentPK(), new EDITDate(editTrxVO.getEffectiveDate()), "RCL", crud1);

        if (rclEditTrxVOs != null)
        {
            EDITTrxVO[] editTrxVOs = new event.dm.dao.FastDAO().findClaimPayoutTrx(segmentVO.getSegmentPK(), new EDITDate(rclEditTrxVOs[0].getEffectiveDate()), crud1);

            if (editTrxVOs != null)
            {
                EDITDate currentTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());
                EDITDate cpoEffDate = new EDITDate(editTrxVOs[0].getEffectiveDate());
                if (currentTrxEffDate.equals(cpoEffDate))
                {
                    validClaimPayoutTrx = true;
                }
            }
        }

        return validClaimPayoutTrx;
    }

    private void createDivisionFeeOffset(EDITTrxVO editTrxVO, String contractNumber, CRUD crud) throws EDITCRUDException
    {
        EDITTrxHistory editTrxHistory = EDITTrxHistory.findBy_EDITTrxFK(new Long(editTrxVO.getEDITTrxPK()));

        InvestmentHistory[] investmentHistoriesWithFinalPrice = editTrxHistory.getInvestmentHistoriesWithFinalPrice();

        String accountingPeriod = editTrxHistory.getProcessDateTime().toString().substring(0, 7);

        engine.business.Lookup lookupComponent = new engine.component.LookupComponent();

        FeeDescriptionVO feeDescriptionVO = null;

        crud.startTransaction();

        for (int i = 0; i < investmentHistoriesWithFinalPrice.length; i++)
        {
            Investment investment = Investment.findByPK(investmentHistoriesWithFinalPrice[i].getInvestmentFK());
            feeDescriptionVO = lookupComponent.
                    findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(investment.getFilteredFundFK(), "Transfer");

            FeeVO feeVO = new FeeVO();
            feeVO.setFilteredFundFK(investment.getFilteredFundFK());
            feeVO.setTransactionTypeCT(Fee.DIVISION_FEE_OFFSET_TRX_TYPE);
            if (feeDescriptionVO != null)
            {
                feeVO.setFeeDescriptionFK(feeDescriptionVO.getFeeDescriptionPK());
            }

            feeVO.setEffectiveDate(editTrxVO.getEffectiveDate());
            feeVO.setProcessDateTime(editTrxHistory.getProcessDateTime().toString());
            feeVO.setAccountingPeriod(accountingPeriod);
            feeVO.setTrxAmount(investmentHistoriesWithFinalPrice[i].getInvestmentDollars().getBigDecimal());
            feeVO.setUnits(investmentHistoriesWithFinalPrice[i].getInvestmentUnits().getBigDecimal());
            feeVO.setContractNumber(contractNumber);
            feeVO.setChargeCodeFK(investmentHistoriesWithFinalPrice[i].getChargeCodeFK().longValue());
            feeVO.setToFromInd(investmentHistoriesWithFinalPrice[i].getToFromStatus());
            feeVO.setReleaseInd("N");
            feeVO.setStatusCT("N");
            feeVO.setAccountingPendingStatus("Y");
            feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            feeVO.setOperator("System");

            crud.createOrUpdateVOInDB(feeVO);
        }

        crud.commitTransaction();
    }

    private void setIntoBucketList(BucketHistoryVO bucketHistoryVO, List buckets)
    {
        boolean bucketMatch = false;
        for (int i = 0; i < buckets.size(); i++)
        {
            BucketVO bucketVO = (BucketVO)buckets.get(i);
            if (bucketVO.getBucketPK() == bucketHistoryVO.getBucketFK())
            {
                bucketVO.addBucketHistoryVO(bucketHistoryVO);
                bucketMatch = true;
            }
        }

        if (!bucketMatch)
        {
            BucketVO lastBucketVO = (BucketVO) buckets.get(buckets.size() - 1);
            lastBucketVO.addBucketHistoryVO((BucketHistoryVO) bucketHistoryVO);
            buckets.set(buckets.size() - 1, lastBucketVO);
        }
    }

    private String findSpawnedTransaction(String transactionType, SegmentVO segmentVO)
    {
        String spawnedTransaction = null;

        long productStructurePK = segmentVO.getProductStructureFK();
        String areaCT = segmentVO.getIssueStateCT();
        String grouping = "SPAWNINGTRANSACTION";

        EDITDate effectiveDate = new EDITDate(segmentVO.getEffectiveDate());
        String field = transactionType;

        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, "*");

        AreaValue areaValue = area.getAreaValue(field);

        if (areaValue != null)
        {
            AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

            spawnedTransaction = areaValueVO.getAreaValue();
        }

        return spawnedTransaction;
    }


    /**
     * Locates all BucketHistories, and BucketChargeHistories of the specified Document and adds them to the
     * specified BucketHistories List. BucketChargeHistories are added as children to their parent BucketHistory.
     * @param bucketHistories
     * @param document
     */
    private void captureBucketHistories(List bucketHistories, Document document)
    {
        List bucketHistoryElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO.EDITTrxHistoryVO.BucketHistoryVO", document);

        for (int i = 0; i < bucketHistoryElements.size(); i++)
        {
            Element bucketHistoryElement = (Element) bucketHistoryElements.get(i);

            BucketHistoryVO bucketHistoryVO = (BucketHistoryVO) DOMUtil.mapElementToVO(bucketHistoryElement);

            List bucketChargetHistories = DOMUtil.getChildren("BucketChargeHistoryVO", bucketHistoryElement);

            for (int j = 0; j < bucketChargetHistories.size(); j++)
            {
                Element bucketChargeHistoryElement = (Element) bucketChargetHistories.get(j);

                BucketChargeHistoryVO bucketChargeHistoryVO = (BucketChargeHistoryVO) DOMUtil.mapElementToVO(bucketChargeHistoryElement);

                bucketHistoryVO.addBucketChargeHistoryVO(bucketChargeHistoryVO);
            }

            bucketHistories.add(bucketHistoryVO);
        }
    }

    /**
     * Multiple transactions can be created in scripts.  Each tables of the transaction is a separate output.  Using the outputs
     * create a fluffy GroupSetupVO for each one.  Only if the trx is backdated, create it as a client strategy to add this new
     * transaction to the strategy chain.
     * @param editTrxComponents
     * @param voObjects
     * @param crud
     * @return
     * @throws EDITDeleteException 
     */
    public ClientStrategy[] saveSpawnedEDITTrx(TreeMap editTrxComponents, VOObject[] voObjects, long productStructurePK, CRUD crud, int executionMode, String cycleDate) throws EDITDeleteException
    {
        GroupSetupVO groupSetupVO = null;
        ContractSetupVO contractSetupVO = null;
        ClientSetupVO clientSetupVO = null;
        EDITTrxVO editTrxVO = null;
        OutSuspenseVO outSuspenseVO = null;
        List investmentAllocOvrd = new ArrayList();
        Set keys = editTrxComponents.keySet();
        ClientStrategy[] clientStrategies = null;
        ClientStrategy[] firstClientStrategies = null;
        ClientStrategy[] secondClientStrategies = null;

        for (Iterator iterator = keys.iterator(); iterator.hasNext();)
        {
            int occurrance = (int)Integer.parseInt(iterator.next().toString());

            groupSetupVO = (GroupSetupVO)voObjects[occurrance];
            occurrance++;
            for (int i = occurrance; i < voObjects.length; i++)
            {
                VOObject voObject = voObjects[i];
                if (voObject instanceof ContractSetupVO)
                {
                    contractSetupVO = (ContractSetupVO) voObjects[i];
                }
                else if (voObject instanceof InvestmentAllocationOverrideVO)
                {
                    investmentAllocOvrd.add((InvestmentAllocationOverrideVO) voObjects[i]);
                }
                else if (voObject instanceof ClientSetupVO)
                {
                    clientSetupVO = (ClientSetupVO) voObjects[i];
                }
                else if (voObject instanceof  EDITTrxVO)
                {
                    editTrxVO = (EDITTrxVO) voObjects[i];
                }
                else if (voObject instanceof OutSuspenseVO)
                {
                    outSuspenseVO = (OutSuspenseVO) voObjects[i];
                }
                else if (voObject instanceof GroupSetupVO)
                {
                    clientSetupVO.addEDITTrxVO(editTrxVO);
                    contractSetupVO.addClientSetupVO(clientSetupVO);
                    groupSetupVO.addContractSetupVO(contractSetupVO);

                    if (!investmentAllocOvrd.isEmpty())
                    {
                        contractSetupVO.setInvestmentAllocationOverrideVO((InvestmentAllocationOverrideVO[])investmentAllocOvrd.toArray(new InvestmentAllocationOverrideVO[investmentAllocOvrd.size()]));
                    }

                    if (outSuspenseVO != null)
                    {
                        contractSetupVO.addOutSuspenseVO(outSuspenseVO);
                    }

                    firstClientStrategies = saveTransaction(groupSetupVO, productStructurePK, crud, executionMode, cycleDate);

                    if (clientStrategies != null)
                    {
                        clientStrategies = (ClientStrategy[])Util.joinArrays(clientStrategies, firstClientStrategies, ClientStrategy.class);
                    }
                    else
                    {
                        clientStrategies = firstClientStrategies;
                    }

                    contractSetupVO = null;
                    groupSetupVO = null;
                    clientSetupVO = null;
                    editTrxVO = null;
                    outSuspenseVO = null;
                    investmentAllocOvrd = new ArrayList();
                    break;
                }
            }

            if (groupSetupVO != null && contractSetupVO != null && clientSetupVO != null && editTrxVO != null)
            {
                clientSetupVO.addEDITTrxVO(editTrxVO);
                contractSetupVO.addClientSetupVO(clientSetupVO);
                groupSetupVO.addContractSetupVO(contractSetupVO);

                if (!investmentAllocOvrd.isEmpty())
                {
                    contractSetupVO.setInvestmentAllocationOverrideVO((InvestmentAllocationOverrideVO[])investmentAllocOvrd.toArray(new InvestmentAllocationOverrideVO[investmentAllocOvrd.size()]));
                }

                if (outSuspenseVO != null)
                {
                    contractSetupVO.addOutSuspenseVO(outSuspenseVO);
                }

                secondClientStrategies = saveTransaction(groupSetupVO, productStructurePK, crud, executionMode, cycleDate);
                if (clientStrategies != null)
                {
                    clientStrategies = (ClientStrategy[])Util.joinArrays(clientStrategies, secondClientStrategies, ClientStrategy.class);
                }
                else
                {
                    clientStrategies = secondClientStrategies;
                }
            }
        }

        return clientStrategies;
    }

    private ClientStrategy[] saveTransaction(GroupSetupVO groupSetupVO, long productStructurePK, CRUD crud, int executionMode, String cycleDate) throws EDITDeleteException
    {
        List clientStrategyArray = new ArrayList();

        SaveGroup saveGroup = new SaveGroup(groupSetupVO);
        ClientTrx[] clientTrxes = saveGroup.save(crud, executionMode, cycleDate);

        for (int i = 0; i < clientTrxes.length; i++)
        {
            if (ClientTrx.isBackdated(clientTrxes[i], productStructurePK) ||
                (executionMode == ClientTrx.BATCH_MODE &&
                 new EDITDate(clientTrxes[i].getEDITTrxVO().getEffectiveDate()).equals(new EDITDate(cycleDate))))
            {
                clientTrxes[i].setExecutionMode(executionMode);
                clientTrxes[i].setCycleDate(cycleDate);
                clientStrategyArray.add(new Natural(clientTrxes[i], "natural"));
            }
        }

        ClientStrategy[] clientStrategies = null;
        if (clientStrategyArray.size() > 0)
        {
            clientStrategies = (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);
        }

        return clientStrategies;
    }

    /**
     * True if there is any BucketHistoryVO in the specified set of VOObjects.
     * @param voObjects
     * @return
     */
    private boolean bucketHistoryExists(VOObject[] voObjects)
    {
        boolean bucketHistoryExists = false;

        for (int i = 0; i < voObjects.length; i++)
        {
            VOObject voObject = voObjects[i];

            if (voObject instanceof BucketHistoryVO)
            {
                bucketHistoryExists = true;

                break;
            }
        }

        return bucketHistoryExists;
    }

    /**
     * Checks that an investment history is tied to a Fund that is of
     * type Hedge fund.
     * @param invHistVO
     * @return boolean - true if the investment history is tied to a Fund type Hedge.
     * @throws Exception
     */
    private boolean isForHedgeFund(InvestmentHistoryVO invHistVO, SegmentVO segmentVO)
    throws Exception
    {
        boolean hedgeFundStatus = false;
        long investmentPK = invHistVO.getInvestmentFK();

        long segmentPK = segmentVO.getSegmentPK();
        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

        for (int i = 0; i < investmentVOs.length; i++)
        {
            if (investmentVOs[i].getInvestmentPK() == invHistVO.getInvestmentFK())
            {
                long filteredFundPK = investmentVOs[i].getFilteredFundFK();

                FundVO[] fundVOs = engine.dm.dao.DAOFactory.getFundDAO().findFundByFilteredFundFK(filteredFundPK, false, null);
                String fundType = fundVOs[0].getFundType();
                hedgeFundStatus = ("Hedge".equalsIgnoreCase(fundType));
                break;
            }
        }

        return hedgeFundStatus;
    }

    /**
     * Check to see if the HFT can be generated - the amount of the transaction must be less than or equal to
     * the unlocked value of the "from" fund - Eligibility check is only required on "from" funds.
     * @param invHistHT
     * @param groupSetupVO
     * @param editTrxVO
     * @return
     * @throws Exception
     */
    private boolean checkHFTEligibility(Hashtable invHistHT, GroupSetupVO groupSetupVO, EDITTrxVO editTrxVO, Document document) throws Exception
    {
        boolean moneyLocked = false;

        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

        Enumeration invHistEnum = invHistHT.keys();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        while (invHistEnum.hasMoreElements())
        {
            long investmentKey = Long.parseLong((String) invHistEnum.nextElement());

            InvestmentHistoryVO invHistVO = (InvestmentHistoryVO) invHistHT.get(investmentKey + "");

            if (invHistVO.getToFromStatus().equalsIgnoreCase("F"))
            {
                Investment investment = Investment.findByPK(new Long(invHistVO.getInvestmentFK()));
                Long chargeCodeFK = investment.getChargeCodeFK();
                if (chargeCodeFK ==  null)
                {
                    chargeCodeFK = new Long(0);
                }
                FilteredFund filteredFund = FilteredFund.findByPK(investment.getFilteredFundFK());

                UnitValuesVO[] unitValues = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFund.getFilteredFundPK().longValue(), editTrxVO.getEffectiveDate(), "Hedge", chargeCodeFK.longValue());

                EDITBigDecimal unitValue = new EDITBigDecimal(unitValues[0].getUnitValue());

                for (int i = 0; i < contractSetupVO.length; i++)
                {
                    EDITDate hftEffectiveDate = getHFTEffectiveDate(editTrxVO, filteredFund, invHistVO);
                    EDITBigDecimal unlockedInvValue = getUnlockedInvValue(investment, filteredFund, hftEffectiveDate, unitValue);
                    EDITBigDecimal transactionAmount = new EDITBigDecimal(invHistVO.getInvestmentDollars());

                    boolean generateHFTP = false;

                    if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") ||
                        editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
                    {
                        generateHFTP = checkForHFTPGeneration(contractSetupVO[0], editTrxVO, invHistVO.getInvestmentFK(), document);
                    }

                    if (generateHFTP)
                    {
                        transactionAmount = getTransactionAmount(investment, filteredFund, contractSetupVO[i].getInvestmentAllocationOverrideVO(), unitValue);
                    }

                    if (!transactionAmount.isLTE(unlockedInvValue))
                    {
                        moneyLocked = true;
                    }
                }
            }
        }

        return moneyLocked;
    }

    /**
     * Calculate the effective date of the HFT that is to be generated from the given editTrx
     * @param editTrxVO
     * @param filteredFund
     * @param invHistVO
     * @return
     */
    private EDITDate getHFTEffectiveDate(EDITTrxVO editTrxVO, FilteredFund filteredFund, InvestmentHistoryVO invHistVO)
    {
        EDITDate edOrigEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        int notificationDays = 0;
        String notificationMode = null;
        String notificationDaysType = null;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") &&
            invHistVO.getToFromStatus().equalsIgnoreCase("F"))
        {
            notificationDays = filteredFund.getTransferDays();
            notificationMode = filteredFund.getTransferModeCT();
            notificationDaysType = filteredFund.getTransferDaysTypeCT();
        }
        else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
        {
            notificationDays = filteredFund.getSeriesTransferDays();
            notificationMode = filteredFund.getSeriesTransferModeCT();
            notificationDaysType = filteredFund.getSeriesTransferDaysTypeCT();
        }
        else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("WI") ||
                 editTrxVO.getTransactionTypeCT().equalsIgnoreCase("SW"))
        {
            notificationDays = filteredFund.getWithdrawalDays();
            notificationMode = filteredFund.getWithdrawalModeCT();
            notificationDaysType = filteredFund.getWithdrawalDaysTypeCT();
        }
        else
        {
            notificationDays = filteredFund.getSubscriptionNotificationDays();
            notificationMode = filteredFund.getSubscriptionModeCT();
            notificationDaysType = filteredFund.getSubscriptionDaysTypeCT();
        }

        String hftEffectiveDate = calculateHftEffectiveDate(edOrigEffDate, notificationDays, notificationMode, notificationDaysType);

        return new EDITDate(hftEffectiveDate);
    }

    /**
     * Calculate the total investment value that is unlocked as of the hft effective date
     * @param investment
     * @param filteredFund
     * @param hftEffectiveDate
     * @return
     */
    private EDITBigDecimal getUnlockedInvValue(Investment investment, FilteredFund filteredFund, EDITDate hftEffectiveDate, EDITBigDecimal unitValue)
    {
        EDITBigDecimal unlockedInvValue = new EDITBigDecimal();

        Set buckets = investment.getBuckets();

        if (!buckets.isEmpty())
        {
            Iterator it = buckets.iterator();

            while (it.hasNext())
            {
                Bucket bucket = (Bucket) it.next();

                EDITDate lockupEndDate = bucket.getLockupEndDate();

                if (lockupEndDate == null || (hftEffectiveDate.after(lockupEndDate) || hftEffectiveDate.equals(lockupEndDate)) )
                {
                    unlockedInvValue = unlockedInvValue.addEditBigDecimal(bucket.getCumUnits().multiplyEditBigDecimal(unitValue));
                }
            }
        }

        unlockedInvValue = unlockedInvValue.round(2);

        return unlockedInvValue;
    }

    /**
     * Calculate the HFT transaction amount using the given investment, fund, and override information
     * @param investment
     * @param filteredFund
     * @param invAllocOvrdVOs
     * @return
     */
    private EDITBigDecimal getTransactionAmount(Investment investment, FilteredFund filteredFund, InvestmentAllocationOverrideVO[] invAllocOvrdVOs, EDITBigDecimal unitValue)
    {
        EDITBigDecimal transactionAmount = new EDITBigDecimal();

        Set buckets = investment.getBuckets();

        if (!buckets.isEmpty())
        {
            Iterator it = buckets.iterator();

            //Loop through the buckets to get the total investment value using the last known unit value.
            while (it.hasNext())
            {
                Bucket bucket = (Bucket) it.next();

                transactionAmount = transactionAmount.addEditBigDecimal(bucket.getCumUnits().multiplyEditBigDecimal(unitValue));
            }
        }

        //Now loop through the investment allocation overrides to find the allocation percent for the "from" fund
        for (int i = 0; i < invAllocOvrdVOs.length; i++)
        {
            if (invAllocOvrdVOs[i].getInvestmentFK() == investment.getInvestmentPK().longValue())
            {
                InvestmentAllocation investmentAllocation = InvestmentAllocation.findByPK(new Long(invAllocOvrdVOs[i].getInvestmentAllocationFK()));

                transactionAmount = transactionAmount.multiplyEditBigDecimal(investmentAllocation.getAllocationPercent());
            }
        }

        transactionAmount = transactionAmount.round(2);

        return transactionAmount;
    }

    private boolean lookForForwardPrice(EDITTrxVO editTrxVO, SegmentVO baseSegmentVO) throws Exception
    {
        boolean forwardPriceFound = false;
        boolean hedgeFundFound = false;

        EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        List voInclusionListForEDITTrx = new ArrayList();
        voInclusionListForEDITTrx.add(ClientSetupVO.class);
        voInclusionListForEDITTrx.add(ContractSetupVO.class);
        voInclusionListForEDITTrx.add(InvestmentAllocationOverrideVO.class);
        
        new EDITTrxComposer(voInclusionListForEDITTrx).compose(editTrxVO);

        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(UnitValuesVO.class);

        if (!editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MODALDEDUCTION) &&
            !editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) &&
            !editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT))
        {
            ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).
                                                                 getParentVO(ContractSetupVO.class);

            InvestmentAllocationOverrideVO[] invAllocOvrdVOs = contractSetupVO.getInvestmentAllocationOverrideVO();

            for (int i = 0; i < invAllocOvrdVOs.length; i++)
            {
                InvestmentVO[] investmentVOs = baseSegmentVO.getInvestmentVO();
                long filteredFundFK = 0;
                for (int j = 0; j < investmentVOs.length; j++)
                {
                    if (investmentVOs[j].getInvestmentPK() == invAllocOvrdVOs[i].getInvestmentFK())
                    {
                        filteredFundFK = investmentVOs[j].getFilteredFundFK();
                        j = investmentVOs.length;
                    }
                }

                FundVO fundVO = engineLookup.composeFundVOByFilteredFundPK(filteredFundFK, voInclusionList);
                if (fundVO.getFundType().equalsIgnoreCase("Hedge"))
                {
                    hedgeFundFound = true;
                    FilteredFundVO[] filteredFundVOs = fundVO.getFilteredFundVO();
                    for (int j = 0; j < filteredFundVOs.length; j++)
                    {
                        UnitValuesVO[] unitValuesVOs = filteredFundVOs[j].getUnitValuesVO();
                        for (int k = 0; k < unitValuesVOs.length; k++)
                        {
                            EDITDate edUVEffDate = new EDITDate(unitValuesVOs[k].getEffectiveDate());
                            if (edUVEffDate.afterOREqual(edTrxEffDate))
                            {
                                forwardPriceFound = true;
                                k = unitValuesVOs.length;
                                j = filteredFundVOs.length;
                                i = invAllocOvrdVOs.length;
                            }
                        }
                    }
                }
            }

            if (!hedgeFundFound)
            {
                forwardPriceFound = true;
            }
        }
        else
        {
            boolean allForwardPricesFoundForHF = true;
            InvestmentVO[] investmentVOs = baseSegmentVO.getInvestmentVO();
            long filteredFundFK = 0;
            for (int i = 0; i < investmentVOs.length; i++)
            {
                filteredFundFK = investmentVOs[i].getFilteredFundFK();

                FundVO fundVO = engineLookup.composeFundVOByFilteredFundPK(filteredFundFK, voInclusionList);
                if (fundVO.getFundType().equalsIgnoreCase("Hedge"))
                {
                    forwardPriceFound = false;

                    FilteredFundVO[] filteredFundVOs = fundVO.getFilteredFundVO();
                    for (int j = 0; j < filteredFundVOs.length; j++)
                    {
                        UnitValuesVO[] unitValuesVOs = filteredFundVOs[j].getUnitValuesVO();
                        for (int k = 0; k < unitValuesVOs.length; k++)
                        {
                            EDITDate edUVEffDate = new EDITDate(unitValuesVOs[k].getEffectiveDate());
                            if (edUVEffDate.afterOREqual(edTrxEffDate))
                            {
                                forwardPriceFound = true;
                                k = unitValuesVOs.length;
                                j = filteredFundVOs.length;
                            }
                        }
                    }

                    if (!forwardPriceFound)
                    {
                        allForwardPricesFoundForHF = false;
                    }
                }
            }

            if (!allForwardPricesFoundForHF)
            {
                forwardPriceFound = false;
            }
            else
            {
                forwardPriceFound = true;
            }
        }

        return forwardPriceFound;
    }

    private void updateInvestmentHistory(List invHistoriesToSave,
                                         CRUD crud)
    {
        for (int i = 0; i < invHistoriesToSave.size(); i++)
        {
            crud.createOrUpdateVOInDB((InvestmentHistoryVO) invHistoriesToSave.get(i));
        }
    }

    private void updateCommissionHistory(List commissionHistories,
                                         CRUD crud)
    {
        for (int i = 0; i < commissionHistories.size(); i++)
        {
            crud.createOrUpdateVOInDB((CommissionHistoryVO) commissionHistories.get(i));
        }
    }

    private Hashtable updateBucketsAndHistory(List buckets,
                                              EDITTrxHistoryVO editTrxHistoryVO,
                                              List bucketHistories,
                                              EDITTrxVO editTrxVO,
                                              CRUD crud,
                                              SegmentVO baseSegmentVO) throws EDITEventException
    {
        Hashtable interimAcctBuckets = new Hashtable();

        String bucketSourceCT = null;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_TRANSFER_AMT) ||
                editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_TRANSFER_PCT))
        {
            String originatingTrxType = EDITTrx.getOriginatingTrxType(editTrxVO.getOriginatingTrxFK());
            if (originatingTrxType != null && (originatingTrxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_TRANSFER) ||
                    originatingTrxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN)))
            {
                bucketSourceCT = Bucket.BUCKETSOURCECT_TRANSFER;
            }
        }

        //if any buckets created during script processing, update it now to the db and insert its true key into its
        //bucket history record.
        if (buckets == null || buckets.size() == 0)
        {
            if (editTrxHistoryVO == null)
            {
                editTrxHistoryVO = new EDITTrxHistoryVO();
                populateEDITTrxHistoryFields(editTrxHistoryVO, editTrxVO);

                setPreviousChargeCode(editTrxHistoryVO, baseSegmentVO);
            }

            saveEditTrxHistories(editTrxHistoryVO, editTrxVO, crud);
        }
        else
        {
            try
            {
                for (int i = 0; i < buckets.size(); i++)
                {
                    BucketVO bucketVO = (BucketVO) buckets.get(i);
                    if (bucketSourceCT != null && bucketVO.getBucketPK() == 0)
                    {
                        bucketVO.setBucketSourceCT(bucketSourceCT);
                    }
                    long bucketKey = crud.createOrUpdateVOInDB(bucketVO);
                    AnnualizedSubBucketVO[] annSubBucketVOs = bucketVO.getAnnualizedSubBucketVO();
                    if (annSubBucketVOs != null)
                    {
                        for (int j = 0; j < annSubBucketVOs.length; j++)
                        {
                            annSubBucketVOs[j].setBucketFK(bucketKey);
                            crud.createOrUpdateVOInDB(annSubBucketVOs[j]);
                        }
                    }

                    //the order of buckets and history should be the same - changed as of 09-20-07
                    //BucketHistoryVO is now in the bucketvo already
 //                    BucketHistoryVO bucketHistoryVO = (BucketHistoryVO) bucketHistories.get(i);
                    BucketHistoryVO[] bucketHistoryVOs = (BucketHistoryVO[]) bucketVO.getBucketHistoryVO();
                    for (int k =0; k < bucketHistoryVOs.length; k++)
                    {
                        BucketHistoryVO bucketHistoryVO = bucketHistoryVOs[k];
                    if (bucketHistoryVO.getBucketFK() == 0)
                    {
                        bucketHistoryVO.setBucketFK(bucketKey);
                        if ((bucketHistoryVO.getInterimAccountIndicator() != null &&
                            bucketHistoryVO.getInterimAccountIndicator().equalsIgnoreCase("Y")) ||
                            (bucketHistoryVO.getHoldingAccountIndicator() != null &&
                             bucketHistoryVO.getHoldingAccountIndicator().equalsIgnoreCase("Y")))
                        {
                            interimAcctBuckets.put(bucketHistoryVO.getHedgeFundInvestmentFK() + "", bucketKey + "");
                        }
                    }

                    if (editTrxHistoryVO != null)
                    {
                        editTrxHistoryVO.addBucketHistoryVO(bucketHistoryVO);
                    }
                    else
                    {
                        crud.createOrUpdateVOInDBRecursively(bucketHistoryVO);
                    }
                }
            }

//                for (int j = 0; j < bucketHistories.size(); j++)
//                {
//                    BucketHistoryVO bucketHistoryVO = (BucketHistoryVO) bucketHistories.get(j);
//
//                    if (editTrxHistoryVO != null)
//                    {
//                        editTrxHistoryVO.addBucketHistoryVO(bucketHistoryVO);
//                    }
//                    else
//                    {
//                        crud.createOrUpdateVOInDBRecursively(bucketHistoryVO);
//                    }
//                }
            }
            catch (Exception e)
            {
                System.out.println(e);
                
                e.printStackTrace();
                
                EDITEventException editEventException = new EDITEventException(e.getMessage());

                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                Log.logGeneralExceptionToDatabase(null, e);

                try
                {
                    crud.rollbackTransaction();
                }
                catch (EDITCRUDException e1)
                {
                    Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                    Log.logGeneralExceptionToDatabase(null, e);

                    EDITEventException editEventException2 = new EDITEventException(e.getMessage());

                    editEventException = editEventException2;
                }

                throw editEventException;
            }

            if (editTrxHistoryVO != null)
            {
                saveEditTrxHistories(editTrxHistoryVO, editTrxVO, crud);
            }
        }

        return interimAcctBuckets;
    }


        /**
     * Set the investment histories' previous charge code
     * @param editTrxHistoryVO
     */
    private void setPreviousChargeCode(EDITTrxHistoryVO editTrxHistoryVO, SegmentVO baseSegmentVO)
    {

        InvestmentVO[] investmentVOs = baseSegmentVO.getInvestmentVO();
        InvestmentHistoryVO[] investmentHistoryVOs = editTrxHistoryVO.getInvestmentHistoryVO();

        // make sure that the investments and investment histories match up
        if (    investmentVOs == null
            ||  investmentHistoryVOs == null
            ||  (investmentVOs.length != investmentHistoryVOs.length) )
        {
            return;
        }


        for (int i = 0; i < investmentHistoryVOs.length; i++)
        {
            InvestmentHistoryVO investmentHistoryVO = investmentHistoryVOs[i];
            InvestmentVO investmentVO = investmentVOs[i];

            long chargeCodeFK = investmentVO.getChargeCodeFK();
            if (chargeCodeFK != 0)
            {
                investmentHistoryVO.setChargeCodeFK(chargeCodeFK);
            }
        }
    }

    void saveEditTrxHistories(EDITTrxHistoryVO editTrxHistoryVO, EDITTrxVO editTrxVO, CRUD crud) throws EDITEventException
    {
        try
        {
            CommissionHistoryVO[] commissionHistoryVO = editTrxHistoryVO.getCommissionHistoryVO();

            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                commissionHistoryVO[i].setAccountingPendingStatus("Y");
                commissionHistoryVO[i].setMaintDateTime(new EDITDateTime().getFormattedDateTime());
                commissionHistoryVO[i].setOperator(editTrxVO.getOperator());
//                commissionHistoryVO[i].setBonusUpdateDateTime(commissionHistoryVO[i].getMaintDateTime());

                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("CK"))
                {
                    commissionHistoryVO[i].setUpdateDateTime(commissionHistoryVO[i].getMaintDateTime());

                    Role role = new RoleComponent();

                    role.updateLastCheckDateTime(commissionHistoryVO[i].getPlacedAgentFK(), new EDITDateTime(commissionHistoryVO[i].getMaintDateTime()));
                }
            }

//            ReinsuranceHistoryVO[] reinsuranceHistoryVOs = editTrxHistoryVO.getReinsuranceHistoryVO();
//
//            for (int i = 0; i < reinsuranceHistoryVOs.length; i++)
//            {
//                ReinsuranceHistoryVO reinsuranceHistoryVO = reinsuranceHistoryVOs[i];
//
//                reinsuranceHistoryVO.setUpdateDateTime(EDITDate.getCurrentDateTime());
//            }

            crud.createOrUpdateVOInDBRecursively(editTrxHistoryVO, false);
        }
        catch (Exception e)
        {
            EDITEventException editEventException = new EDITEventException(e.getMessage());

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            try
            {
                crud.rollbackTransaction();
            }
            catch (EDITCRUDException e1)
            {
                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

                Log.logGeneralExceptionToDatabase(null, e);

                EDITEventException editEventException2 = new EDITEventException(e.getMessage());

                editEventException = editEventException2;
            }

            throw editEventException;
        }
    }

    private void checkForOutSuspense(GroupSetupVO groupSetupVO, CRUD crud, String processDateTime)
    {
        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

        OutSuspenseVO[] outSuspenseVO = DAOFactory.getOutSuspenseDAO().findByContractSetupPK(contractSetupVO[0].getContractSetupPK(), crud);

        if (outSuspenseVO != null)
        {
            //This is necessary to prevent intermitant deadlocks trying to save the Suspense at the end odf this method
            SessionHelper.clearSessions();

            for (int o = 0; o < outSuspenseVO.length; o++)
            {
                Suspense suspense = Suspense.findByPK(new Long(outSuspenseVO[o].getSuspenseFK()));

                if (suspense != null)
                {
                    EDITBigDecimal suspenseAmount = suspense.getSuspenseAmount();
                    EDITBigDecimal outSuspenseAmount = new EDITBigDecimal(outSuspenseVO[o].getAmount());
                    EDITBigDecimal result = suspenseAmount.subtractEditBigDecimal(outSuspenseAmount);

                    suspense.setSuspenseAmount(result);
                    suspense.setProcessDate(new EDITDate());

                    EDITBigDecimal pendingSuspenseAmount = suspense.getPendingSuspenseAmount();

                    if (pendingSuspenseAmount.isGT("0"))
                    {
                        EDITBigDecimal pendingResult = pendingSuspenseAmount.subtractEditBigDecimal(outSuspenseAmount);
                        suspense.setPendingSuspenseAmount(pendingResult);
                    }

                    if (result.isEQ("0"))
                    {
                        suspense.setMaintenanceInd("A");
                        suspense.setDateAppliedRemoved(new EDITDate(processDateTime.substring(0, 10)));
                    }

                    Event eventComponent = new EventComponent();
                    eventComponent.saveSuspenseForTransaction(suspense);
                }
            }
        }
    }

    private SuspenseVO createSuspense(SegmentVO segmentVO, EDITTrxHistoryVO editTrxHistoryVO,
                                      EDITTrxVO editTrxVO, GroupSetupVO groupSetupVO)
    {
        String userDefNumber = segmentVO.getContractNumber();
        String effectiveDate = editTrxVO.getEffectiveDate();
        FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();
        if (financialHistoryVO[0].getCheckAmount().doubleValue() > 0)
        {
            Company company = Company.findByProductStructurePK(new Long(segmentVO.getProductStructureFK()));

            EDITBigDecimal suspenseAmount = new EDITBigDecimal(financialHistoryVO[0].getCheckAmount());

            InSuspenseVO inSuspenseVO = new InSuspenseVO();
            inSuspenseVO.setAmount(suspenseAmount.getBigDecimal());
            inSuspenseVO.setEDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
            inSuspenseVO.setInSuspensePK(0);
            inSuspenseVO.setSuspenseFK(0);

            SuspenseVO suspenseVO = new SuspenseVO();
            suspenseVO.setAccountingPendingInd("N");
            suspenseVO.setMaintenanceInd("M");
            suspenseVO.setDirectionCT("Remove");
            suspenseVO.setEffectiveDate(effectiveDate);
            suspenseVO.setMaintDateTime(editTrxVO.getMaintDateTime());
            suspenseVO.setOperator(editTrxVO.getOperator());
            suspenseVO.addInSuspenseVO(inSuspenseVO);

            EDITDateTime processDateTime = new EDITDateTime(editTrxHistoryVO.getProcessDateTime());

            EDITDate processDate = null;
            if (processDateTime != null)
            {
                processDate = processDateTime.getEDITDate();
            }
            else
            {
                processDate = new EDITDate();
            }
            suspenseVO.setProcessDate(processDate.getFormattedDate());
            suspenseVO.setSuspenseAmount(suspenseAmount.getBigDecimal());
            suspenseVO.setOriginalAmount(suspenseAmount.getBigDecimal());
            suspenseVO.setSuspensePK(0);
            suspenseVO.setUserDefNumber(userDefNumber);
            suspenseVO.setSuspenseType("Contract");
            suspenseVO.setCompanyFK(company.getCompanyPK().longValue());

            if (groupSetupVO.getWithdrawalTypeCT() != null)
            {
                if (groupSetupVO.getWithdrawalTypeCT().equalsIgnoreCase(GroupSetup.WITHDRAWALTYPECT_SUPPCONTRACT))
                {

                    suspenseVO.setUserDefNumber(editTrxVO.getNewPolicyNumber());
                    suspenseVO.setDirectionCT(Suspense.DIRECTIONCT_APPLY);
                    suspenseVO.setPremiumTypeCT(Suspense.PREMIUMTYPECT_ISSUE);
                }
            }

            return suspenseVO;
        }
        else
        {
            return null;
        }
    }

    void populateEDITTrxHistoryFields(EDITTrxHistoryVO editTrxHistoryVO, EDITTrxVO editTrxVO)
    {
        EDITDate currentDate = new EDITDate();
        EDITDateTime currentDateTime = new EDITDateTime();

        editTrxHistoryVO.setProcessDateTime(currentDateTime.getFormattedDateTime());
        editTrxHistoryVO.setOriginalProcessDateTime(currentDateTime.getFormattedDateTime());

        ClientSetupVO clientSetupVO = null;
        
        if (editTrxVO.getParentVOs() == null) // apparently clientSetupVO is not supplied depending on the calling client
        {
          clientSetupVO = (ClientSetupVO)(new ClientSetup(editTrxVO.getClientSetupFK()).getVO());
        }
        else if (editTrxVO.getParentVO(ClientSetupVO.class) != null)
        {
          clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
        }

        String corrAddressTypeCT = null;
        if (clientSetupVO != null)
        {
            long clientRoleFK = clientSetupVO.getClientRoleFK();
            long contractClientFK = clientSetupVO.getContractClientFK();

            String disbAddressTypeCT = null;
            corrAddressTypeCT = null;

            if (contractClientFK == 0)
            {
                List voInclusionList = new ArrayList();
                voInclusionList.add(AgentVO.class);
                ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(clientRoleFK);
                if (clientRoleVO != null)
                {
                    AgentVO agentVO = (AgentVO) clientRoleVO.getParentVO(AgentVO.class);
                    if (agentVO != null)
                    {
                        disbAddressTypeCT = agentVO.getDisbursementAddressTypeCT();
                        corrAddressTypeCT = agentVO.getCorrespondenceAddressTypeCT();
                    }
                }
            }
            else
            {
                ContractClientVO contractClientVO = new VOComposer().composeContractClientVO(clientSetupVO, new ArrayList());
                if (contractClientVO != null)
                {
                    disbAddressTypeCT = contractClientVO.getDisbursementAddressTypeCT();
                    corrAddressTypeCT = contractClientVO.getCorrespondenceAddressTypeCT();
                }
            }

            editTrxHistoryVO.setAddressTypeCT(disbAddressTypeCT);
        }

        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = new event.dm.composer.VOComposer().composeEDITTrxCorrespondenceVOByEDITTrxPK(editTrxVO.getEDITTrxPK(), new ArrayList());
        if (editTrxCorrespondenceVO != null)
        {
            for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
            {
                editTrxCorrespondenceVO[i].setAddressTypeCT(corrAddressTypeCT);
            }

            editTrxVO.setEDITTrxCorrespondenceVO(editTrxCorrespondenceVO);
        }

        String transactionType = editTrxVO.getTransactionTypeCT();
        if (editTrxVO.getNoAccountingInd() != null)
        {
            if (editTrxVO.getNoAccountingInd().equals("Y") ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ADDRESS_CHANGE) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) ||
            	transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE_DEDUCTION_AMT) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATHPENDING) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FACEINCREASE) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ISSUE) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LAPSE_PENDING) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_BILL) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MATURITY) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLYINTEREST) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_RMDCORRESPONDENCE) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_RIDER_CLAIM) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_STATEMENT) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT))
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

        if (executionMode == ClientTrx.REALTIME_MODE)
        {
            editTrxHistoryVO.setCycleDate(currentDate.getFormattedDate());
            editTrxHistoryVO.setRealTimeInd("Y");
            editTrxHistoryVO.setOriginalCycleDate(currentDate.getFormattedDate());
        }
        else
        {
            editTrxHistoryVO.setCycleDate(cycleDate);
            editTrxHistoryVO.setRealTimeInd("N");
            editTrxHistoryVO.setOriginalCycleDate(cycleDate);
        }

        // String transactionType = editTrxVO.getTransactionTypeCT();

        if (transactionType.equals("PO") || transactionType.equals("WI") || transactionType.equals("FS") || transactionType.equals("LS")
                                         || transactionType.equals(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN))
        {
            editTrxHistoryVO.setControlNumber(CRUD.getNextAvailableKey() + "");
        }

        if (editTrxVO.getAccountingPeriod() == null)
        {
            String accountingPeriod = DateTimeUtil.buildAccountingPeriod(new EDITDate(cycleDate));

            editTrxVO.setAccountingPeriod(accountingPeriod);
            editTrxVO.setOriginalAccountingPeriod(accountingPeriod);
        }
    }

    public boolean determineIfRebalTrxNeeded(long segmentPK, CRUD crud)
    {
        boolean generateTrx = false;

        InvestmentVO[] investmentVOs = new contract.dm.dao.FastDAO().findInvestmentBy_SegmentPK(segmentPK, crud);

        List voInclusionList = new ArrayList();
        voInclusionList.add(BucketVO.class);

        if (investmentVOs != null)
        {
            for (int i = 0; i < investmentVOs.length; i++)
            {
                crud.retrieveVOFromDBRecursively(investmentVOs[i], voInclusionList, true);
            }

            BucketVO[] bucketVOs = null;
            for (int i = 0; i < investmentVOs.length; i++)
            {
                bucketVOs = investmentVOs[i].getBucketVO();
                for (int j = 0; j < bucketVOs.length; j++)
                {
                    EDITBigDecimal rebalanceAmount = new EDITBigDecimal(bucketVOs[j].getRebalanceAmount());
                    if (rebalanceAmount.isGT("0"))
                    {
                        generateTrx = true;
                        break;
                    }
                }
            }
        }

        return generateTrx;
    }

    /**
     * Generates an HFTA transaction for each hedge fund affected by the originating transaction
     * @param invHistHT
     * @param editTrxVO
     * @param groupSetupVO
     * @throws Exception
     */
    private ClientStrategy[] generateHFT(Hashtable invHistHT,
                                         EDITTrxVO editTrxVO,
                                         GroupSetupVO groupSetupVO,
                                         Hashtable interimAcctBuckets,
                                         Document document) throws Exception
    {
        List segmentVOInclusionList = new ArrayList();
        segmentVOInclusionList.add(InvestmentVO.class);
        segmentVOInclusionList.add(InvestmentAllocationVO.class);

        List clientStrategyArray = new ArrayList();
        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

        Enumeration invHistEnum = invHistHT.keys();

        while (invHistEnum.hasMoreElements())
        {
            long investmentKey = Long.parseLong((String) invHistEnum.nextElement());

            InvestmentHistoryVO invHistVO = (InvestmentHistoryVO) invHistHT.get(investmentKey + "");

            for (int i = 0; i < contractSetupVO.length; i++)
            {
                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
                {
                    boolean generateHFSP = checkForHFTPGeneration(contractSetupVO[i], editTrxVO, invHistVO.getInvestmentFK(), document);

                    String hfsTrxType = null;

                    if (generateHFSP)
                    {
                        hfsTrxType = EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT;
                    }
                    else
                    {
                        hfsTrxType = EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT;
                    }

                    generateHFSTrx(groupSetupVO,
                                   contractSetupVO[i],
                                   investmentKey,
                                   invHistVO,
                                   editTrxVO,
                                   clientStrategyArray,
                                   hfsTrxType,
                                   document);
                }
                else
                {
                    boolean generateHFTP = false;

                    if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") ||
                        editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") ||
                        editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FT") ||
                        (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) &&
                        (editTrxVO.getTrxAmount().longValue() == 0 && editTrxVO.getTrxPercent().longValue() == 0)))
                    {
                    generateHFTP = checkForHFTPGeneration(contractSetupVO[i], editTrxVO, invHistVO.getInvestmentFK(), document);
                    }

                    if (generateHFTP)
                    {
                        generateHFTPTrx(groupSetupVO,
                                        contractSetupVO[i],
                                        investmentKey,
                                        invHistVO,
                                        editTrxVO,
                                        interimAcctBuckets,
                                    clientStrategyArray,
                                    document);
                    }
                    else
                    {
                        generateHFTATrx(groupSetupVO,
                                        contractSetupVO[i],
                                        investmentKey,
                                        invHistVO,
                                        editTrxVO,
                                        interimAcctBuckets,
                                    clientStrategyArray,
                                    document);
                    }
                }
            }
        }

        return (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);
    }

    private boolean checkForHFTPGeneration(ContractSetupVO contractSetupVO,
                                           EDITTrxVO editTrxVO,
                                           long investmentFK,
                                           Document document) throws Exception
    {
        boolean generateHFTP = false;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
        {
            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

//            InvestmentAllocationOverrideVO[] iaoVOs = contractSetupVO.getInvestmentAllocationOverrideVO();
            List invAllocOverrideElements = DOMUtil.getElements("InvestmentAllocationOverrideDocVO.InvestmentAllocationOverrideVO", document);

            for (int i = 0; i < invAllocOverrideElements.size(); i++)
            {
                Element invAllocOverrideElement = (Element) invAllocOverrideElements.get(i);

                InvestmentAllocationOverrideVO invAllocOvrdVO = (InvestmentAllocationOverrideVO) DOMUtil.mapElementToVO(invAllocOverrideElement);

            List voInclusionList = new ArrayList();
            voInclusionList.add(InvestmentAllocationVO.class);
            voInclusionList.add(FilteredFundVO.class);
            voInclusionList.add(FundVO.class);

//            for (int i = 0; i < iaoVOs.length; i++)
//            {
                InvestmentVO investmentVO = contractLookup.composeInvestmentVOByPK(invAllocOvrdVO.getInvestmentFK(), voInclusionList);
                //match the investmentPK to the investmentFK passed in parameters
                if (investmentVO.getInvestmentPK() == investmentFK)
                {
                    FundVO fundVO = (FundVO) investmentVO.getParentVO(FilteredFundVO.class).getParentVO(FundVO.class);

                    String fundType = fundVO.getFundType();
                    if (fundType.equalsIgnoreCase("Hedge"))
                    {
                        InvestmentAllocationVO[] invAllocVOs = investmentVO.getInvestmentAllocationVO();

                        for (int j = 0; j < invAllocVOs.length; j++)
                        {
                            if (invAllocVOs[j].getInvestmentAllocationPK() == invAllocOvrdVO.getInvestmentAllocationFK())
                            {
                                if (new EDITBigDecimal(invAllocVOs[j].getAllocationPercent()).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                                {
                                    generateHFTP = true;
                                    j = invAllocVOs.length;
                                    i = invAllocOverrideElements.size();
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            String hfTransferType = getHFTransferType(contractSetupVO, editTrxVO.getTransactionTypeCT());
            if (hfTransferType.equalsIgnoreCase("HFTP"))
            {
                generateHFTP = true;
            }
        }

        return generateHFTP;
    }

    private String getHFTransferType(ContractSetupVO contractSetupVO, String transactionType)
    {
        Segment segment = new Segment(contractSetupVO.getSegmentFK());
        long productStructurePK = ((SegmentVO) segment.getVO()).getProductStructureFK();
        String areaCT = ((SegmentVO) segment.getVO()).getIssueStateCT();
        String qualifierCT = "*";
        String grouping = null;
        if (transactionType.equalsIgnoreCase("FT"))
        {
            grouping = "FREELOOKPROCESS";
        }
        else
        {
            grouping = "TRANSACTION";
        }

        EDITDate effectiveDate = new EDITDate(((SegmentVO) segment.getVO()).getEffectiveDate());
        String field = "HFTRANSFERTYPE";

        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        String hfTransferType = "HFTA";

        if (area != null)
        {
            AreaValue areaValue = area.getAreaValue(field);

            if (areaValue != null)
            {
                AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

                hfTransferType = areaValueVO.getAreaValue();
            }
        }

        return hfTransferType;
    }

    private void generateHFTPTrx(GroupSetupVO groupSetupVO,
                                 ContractSetupVO contractSetupVO,
                                 long investmentKey,
                                 InvestmentHistoryVO invHistVO,
                                 EDITTrxVO editTrxVO,
                                 Hashtable interimAcctBuckets,
                                 List clientStrategyArray,
                                 Document document) throws Exception
    {
        EDITTrxVO hftpEDITTrxVO = null;
        EDITDate edOrigEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        SegmentVO segmentVO = getSegment(contractSetupVO.getSegmentFK());
        Investment hfInvestment = getInvestment(investmentKey);

        long interimAcctBucketFK = getInterimAccountBucketFK(hfInvestment, interimAcctBuckets);

        InvestmentAllocation investmentAllocation = new InvestmentAllocation(hfInvestment.getInvestmentAllocationVOs());
        FilteredFundVO filteredFundVO = (FilteredFundVO) hfInvestment.getVO().getParentVO(FilteredFundVO.class);

        int notificationDays = 0;
        String notificationMode = null;
        String notificationDaysType = null;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") &&
            invHistVO.getToFromStatus().equalsIgnoreCase("F"))
        {
            notificationDays = filteredFundVO.getTransferDays();
            notificationMode = filteredFundVO.getTransferModeCT();
            notificationDaysType = filteredFundVO.getTransferDaysTypeCT();
        }
        else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("WI") ||
                 editTrxVO.getTransactionTypeCT().equalsIgnoreCase("SW"))
        {
            notificationDays = filteredFundVO.getWithdrawalDays();
            notificationMode = filteredFundVO.getWithdrawalModeCT();
            notificationMode = filteredFundVO.getWithdrawalDaysTypeCT();
        }
        else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
        {
            notificationDays = filteredFundVO.getLoanDays();
            notificationMode = filteredFundVO.getLoanModeCT();
            notificationDaysType = filteredFundVO.getLoanDaysTypeCT();
        }
        else
        {
            notificationDays = filteredFundVO.getSubscriptionNotificationDays();
            notificationMode = filteredFundVO.getSubscriptionModeCT();
            notificationDaysType = filteredFundVO.getSubscriptionDaysTypeCT();
        }

        String hftpEffectiveDate = calculateHftEffectiveDate(edOrigEffDate, notificationDays, notificationMode, notificationDaysType);

        List invAllocOverrideElements = DOMUtil.getElements("InvestmentAllocationOverrideDocVO.InvestmentAllocationOverrideVO", document);

        InvestmentAllocationOverrideVO[] invAllocOverrideVOs = new InvestmentAllocationOverrideVO[invAllocOverrideElements.size()];

        for (int i = 0; i < invAllocOverrideElements.size(); i++)
        {
            Element invAllocOverrideElement = (Element) invAllocOverrideElements.get(i);

            invAllocOverrideVOs[i] = (InvestmentAllocationOverrideVO) DOMUtil.mapElementToVO(invAllocOverrideElement);
        }

        InvestmentAllocationVO investmentAllocationVO = null;

        for (int i = 0; i < invAllocOverrideVOs.length; i++)
        {
            if (invAllocOverrideVOs[i].getInvestmentFK() == investmentKey)
            {
                investmentAllocationVO = (InvestmentAllocationVO) new InvestmentAllocation(invAllocOverrideVOs[i].getInvestmentAllocationFK()).getVO();
            }
        }

        GroupSetupVO hftpGroupSetupVO = createNewGroupSetupVO(groupSetupVO);
        ContractSetupVO hftpContractSetupVO = createNewContractSetupVO(segmentVO.getSegmentPK());
        hftpGroupSetupVO.addContractSetupVO(hftpContractSetupVO);

        //create the override for the hedge fund
        InvestmentAllocationOverrideVO hfInvAllocOvrdVO = new InvestmentAllocationOverrideVO();
        hfInvAllocOvrdVO.setInvestmentAllocationOverridePK(0);
        hfInvAllocOvrdVO.setContractSetupFK(0);
        hfInvAllocOvrdVO.setInvestmentFK(invHistVO.getInvestmentFK());
        long investmentAllocationPK = investmentAllocation.getPKForAllocationPercent(new BigDecimal(1));
        if (investmentAllocationPK == 0)
        {
            investmentAllocation = new InvestmentAllocation(invHistVO.getInvestmentFK(), new BigDecimal(1), "O", "Percent");

            investmentAllocation.save();
            investmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();
        }

        hfInvAllocOvrdVO.setInvestmentAllocationFK(investmentAllocationPK);
        hfInvAllocOvrdVO.setHFStatus("A");
        hfInvAllocOvrdVO.setHFIAIndicator("N");
        hfInvAllocOvrdVO.setHoldingAccountIndicator("N");
        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FT") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
        {
            hfInvAllocOvrdVO.setToFromStatus("T");
        }
        else
        {
            hfInvAllocOvrdVO.setToFromStatus(invHistVO.getToFromStatus());
        }

        hftpContractSetupVO.addInvestmentAllocationOverrideVO(hfInvAllocOvrdVO);

        FilteredFundVO[] filteredFundVOs = null;

        String holdingAccountIndicator = "N";
        String interimAccountIndicator = "N";

        if ((editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") ||
             editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FT") ||
             editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT)) ||
             (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") &&
              invHistVO.getToFromStatus().equalsIgnoreCase("T")))
        {
            //create the override for the interim account (PY trx only)
            ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(segmentVO.getProductStructureFK(), false, new ArrayList());

            filteredFundVOs = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(segmentVO.getProductStructureFK(), productStructureVO[0].getHedgeFundInterimAccountFK(), new ArrayList());
            interimAccountIndicator = "Y";
        }
        else
        {
            //create the override for the holding account(if not PY trx)
            filteredFundVOs = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(segmentVO.getProductStructureFK(), filteredFundVO.getHoldingAccountFK(),
                                                                                           new ArrayList());
            holdingAccountIndicator = "Y";
        }

        if (filteredFundVOs != null && filteredFundVOs.length > 0)
        {
            InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

            InvestmentAllocationOverrideVO invAllocOvrdVO = setupHoldingOrInterimAccount(filteredFundVOs[0],
                                                                                         investmentVOs,
                                                                                         new BigDecimal(1),
                                                                                         "Percent",
                                                                                         segmentVO.getSegmentPK(),
                                                                                         interimAccountIndicator,
                                                                                         holdingAccountIndicator);

            if ((editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") ||
                 editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FT") ||
                 editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT)) ||
                (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") &&
                 invHistVO.getToFromStatus().equalsIgnoreCase("T")))
            {
                invAllocOvrdVO.setToFromStatus("F");
                invAllocOvrdVO.setBucketFK(interimAcctBucketFK);
            }

            hftpContractSetupVO.addInvestmentAllocationOverrideVO(invAllocOvrdVO);
        }

        ClientSetupVO[] clientSetupVOs = contractSetupVO.getClientSetupVO();
        for (int j = 0; j < clientSetupVOs.length; j++)
        {
            clientSetupVOs[j].removeAllEDITTrxVO();

            clientSetupVOs[j].setClientSetupPK(0);
            clientSetupVOs[j].setContractSetupFK(0);

            ContractClientAllocationOvrdVO[] contractClientAllocOvrdVOs = clientSetupVOs[j].getContractClientAllocationOvrdVO();
            for (int k = 0; k < contractClientAllocOvrdVOs.length; k++)
            {
                contractClientAllocOvrdVOs[k].setContractClientAllocationFK(0);
                contractClientAllocOvrdVOs[k].setClientSetupFK(0);
            }

            hftpEDITTrxVO = new EDITTrxVO();
            hftpEDITTrxVO.setEDITTrxPK(0);
            hftpEDITTrxVO.setClientSetupFK(0);
            hftpEDITTrxVO.setEffectiveDate(hftpEffectiveDate);
            hftpEDITTrxVO.setStatus("N");
            hftpEDITTrxVO.setPendingStatus("P");
            hftpEDITTrxVO.setSequenceNumber(1);
            hftpEDITTrxVO.setTaxYear(Integer.parseInt(hftpEffectiveDate.substring(0, 4)));
            hftpEDITTrxVO.setTrxAmount(new BigDecimal(0));
            hftpEDITTrxVO.setNotificationAmount(new BigDecimal(0));
            hftpEDITTrxVO.setTransactionTypeCT("HFTP");
            hftpEDITTrxVO.setOriginatingTrxFK(editTrxVO.getEDITTrxPK());
            hftpEDITTrxVO.setNoCorrespondenceInd("N");
            hftpEDITTrxVO.setNoAccountingInd("N");
            hftpEDITTrxVO.setNoCommissionInd("N");
            hftpEDITTrxVO.setZeroLoadInd("N");
            hftpEDITTrxVO.setPremiumDueCreatedIndicator("N");
            hftpEDITTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            hftpEDITTrxVO.setOperator("System");
            if ((editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") ||
                 editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FT") ||
                 editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT)) ||
                (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TF") &&
                 invHistVO.getToFromStatus().equalsIgnoreCase("T")))
            {
                hftpEDITTrxVO.setTransferTypeCT("Subscription");
            }
            else
            {
                hftpEDITTrxVO.setTransferTypeCT("RedemptionRemoval");
            }

            clientSetupVOs[j].addEDITTrxVO(hftpEDITTrxVO);
            hftpContractSetupVO.addClientSetupVO(clientSetupVOs[j]);

            SaveGroup saveGroup = new SaveGroup(hftpGroupSetupVO, hftpEDITTrxVO, "HFTP", segmentVO.getOptionCodeCT());

            saveGroup.build();

            saveGroup.save(notificationDays, notificationDaysType);

            GroupSetupVO savedGroupSetupVO = saveGroup.getGroupSetupVO();
            ClientSetupVO clientSetupVO = savedGroupSetupVO.getContractSetupVO()[0].getClientSetupVO(0);
            hftpEDITTrxVO = clientSetupVO.getEDITTrxVO(0);

            ClientTrx clientTrx = new ClientTrx(hftpEDITTrxVO, editTrxVO.getOperator());
            clientTrx.setExecutionMode(executionMode);
            clientTrx.setCycleDate(cycleDate);
            clientStrategyArray.add(new Natural(clientTrx, "natural"));
        }
    }

    private void generateHFSTrx(GroupSetupVO groupSetupVO,
                                ContractSetupVO contractSetupVO,
                                long investmentKey,
                                InvestmentHistoryVO invHistVO,
                                EDITTrxVO editTrxVO,
                                List clientStrategyArray,
                                String hfsTrxType,
                                Document document) throws Exception
    {
        EDITTrxVO hfsEDITTrxVO = null;
        EDITDate edOrigEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        Investment hfInvestment = getInvestment(investmentKey);

        SegmentVO segmentVO = getSegment(contractSetupVO.getSegmentFK());

        FilteredFundVO filteredFundVO = (FilteredFundVO) hfInvestment.getVO().getParentVO(FilteredFundVO.class);

        int notificationDays = filteredFundVO.getSeriesTransferDays();
        String notificationMode = filteredFundVO.getSeriesTransferModeCT();
        String notificationDaysType = filteredFundVO.getSeriesTransferDaysTypeCT();

        String hfsEffectiveDate = calculateHftEffectiveDate(edOrigEffDate, notificationDays, notificationMode, notificationDaysType);

        GroupSetupVO hfsGroupSetupVO = createNewGroupSetupVO(groupSetupVO);
        ContractSetupVO hfsContractSetupVO = createNewContractSetupVO(segmentVO.getSegmentPK());
        hfsGroupSetupVO.addContractSetupVO(hfsContractSetupVO);

        List invAllocOvrdElements = DOMUtil.getElements("InvestmentAllocationOverrideDocVO.InvestmentAllocationOverrideVO", document);
        
        for (int i = 0; i < invAllocOvrdElements.size(); i++)
        {
            Element invAllocOvrdElement = (Element) invAllocOvrdElements.get(i);
            InvestmentAllocationOverrideVO invAllocOvrdVO = (InvestmentAllocationOverrideVO) DOMUtil.mapElementToVO(invAllocOvrdElement);        

            InvestmentAllocationOverrideVO hfInvAllocOvrdVO = new InvestmentAllocationOverrideVO();
            hfInvAllocOvrdVO.setInvestmentAllocationOverridePK(0);
            hfInvAllocOvrdVO.setContractSetupFK(0);
            hfInvAllocOvrdVO.setInvestmentFK(invAllocOvrdVO.getInvestmentFK());
            hfInvAllocOvrdVO.setInvestmentAllocationFK(invAllocOvrdVO.getInvestmentAllocationFK());
            hfInvAllocOvrdVO.setHFStatus("A");
            hfInvAllocOvrdVO.setHFIAIndicator("N");
            hfInvAllocOvrdVO.setHoldingAccountIndicator("N");
            hfInvAllocOvrdVO.setToFromStatus(invAllocOvrdVO.getToFromStatus());

            hfsContractSetupVO.addInvestmentAllocationOverrideVO(hfInvAllocOvrdVO);
        }

        ClientSetupVO[] clientSetupVOs = contractSetupVO.getClientSetupVO();
        for (int j = 0; j < clientSetupVOs.length; j++)
        {
            clientSetupVOs[j].removeAllEDITTrxVO();

            clientSetupVOs[j].setClientSetupPK(0);
            clientSetupVOs[j].setContractSetupFK(0);

            ContractClientAllocationOvrdVO[] contractClientAllocOvrdVOs = clientSetupVOs[j].getContractClientAllocationOvrdVO();
            for (int k = 0; k < contractClientAllocOvrdVOs.length; k++)
            {
                contractClientAllocOvrdVOs[k].setContractClientAllocationFK(0);
                contractClientAllocOvrdVOs[k].setClientSetupFK(0);
            }

            hfsEDITTrxVO = new EDITTrxVO();
            hfsEDITTrxVO.setEDITTrxPK(0);
            hfsEDITTrxVO.setClientSetupFK(0);
            hfsEDITTrxVO.setEffectiveDate(hfsEffectiveDate);
            hfsEDITTrxVO.setStatus("N");
            hfsEDITTrxVO.setPendingStatus("P");
            hfsEDITTrxVO.setSequenceNumber(1);
            hfsEDITTrxVO.setTaxYear(Integer.parseInt(hfsEffectiveDate.substring(0, 4)));
            if (hfsTrxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT))
            {
                hfsEDITTrxVO.setTrxAmount(new BigDecimal(0));
                hfsEDITTrxVO.setNotificationAmount(new BigDecimal(0));
            }
            else
            {
                hfsEDITTrxVO.setTrxAmount(invHistVO.getInvestmentDollars());
                hfsEDITTrxVO.setNotificationAmount(invHistVO.getInvestmentDollars());
            }
            hfsEDITTrxVO.setTransactionTypeCT(hfsTrxType);
            hfsEDITTrxVO.setOriginatingTrxFK(editTrxVO.getEDITTrxPK());
            hfsEDITTrxVO.setNoCorrespondenceInd("N");
            hfsEDITTrxVO.setNoAccountingInd("N");
            hfsEDITTrxVO.setNoCommissionInd("N");
            hfsEDITTrxVO.setZeroLoadInd("N");
            hfsEDITTrxVO.setPremiumDueCreatedIndicator("N");

            hfsEDITTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            hfsEDITTrxVO.setOperator("System");
            hfsEDITTrxVO.setTransferTypeCT(EDITTrx.TRANSFERTYPE_SERIES_TRANSFER);

            clientSetupVOs[j].addEDITTrxVO(hfsEDITTrxVO);
            hfsContractSetupVO.addClientSetupVO(clientSetupVOs[j]);

            SaveGroup saveGroup = new SaveGroup(hfsGroupSetupVO, hfsEDITTrxVO, "HFSP", segmentVO.getOptionCodeCT());

            saveGroup.build();

            saveGroup.save(notificationDays, notificationDaysType);

            GroupSetupVO savedGroupSetupVO = saveGroup.getGroupSetupVO();
            ClientSetupVO clientSetupVO = savedGroupSetupVO.getContractSetupVO()[0].getClientSetupVO(0);
            hfsEDITTrxVO = clientSetupVO.getEDITTrxVO(0);

            ClientTrx clientTrx = new ClientTrx(hfsEDITTrxVO, editTrxVO.getOperator());
            clientStrategyArray.add(new Natural(clientTrx, "natural"));
        }
    }

    private void generateHFTATrx(GroupSetupVO groupSetupVO,
                                 ContractSetupVO contractSetupVO,
                                 long investmentKey,
                                 InvestmentHistoryVO invHistVO,
                                 EDITTrxVO editTrxVO,
                                 Hashtable interimAcctBuckets,
                                 List clientStrategyArray,
                                 Document document) throws Exception
    {
        EDITTrxVO hftaEDITTrxVO = null;

        EDITDate edOrigEffDate = new EDITDate(editTrxVO.getEffectiveDate());
        String transactionType = editTrxVO.getTransactionTypeCT();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        SegmentVO segmentVO = getSegment(contractSetupVO.getSegmentFK());

        String toFromStatus = invHistVO.getToFromStatus();

        InvestmentAllocationOverrideVO hfiaInvAllocOvrdVO = null;

        List invAllocOverrideElements = DOMUtil.getElements("InvestmentAllocationOverrideDocVO.InvestmentAllocationOverrideVO", document);

        if (invAllocOverrideElements.size() > 0)
        {
            InvestmentAllocationOverrideVO[] invAllocOverrideVOs = new InvestmentAllocationOverrideVO[invAllocOverrideElements.size()];
            for (int i = 0; i < invAllocOverrideElements.size(); i++)
        {
                Element invAllocOverrideElement = (Element) invAllocOverrideElements.get(i);

                invAllocOverrideVOs[i] = (InvestmentAllocationOverrideVO) DOMUtil.mapElementToVO(invAllocOverrideElement);
            }

            InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(invAllocOverrideVOs);

            hfiaInvAllocOvrdVO = investmentAllocationOverride.getHFIAOverride();
        }

        ClientSetupVO[] clientSetupVOs = contractSetupVO.getClientSetupVO();

        Investment hfInvestment = getInvestment(investmentKey);

        long interimAcctBucketFK = getInterimAccountBucketFK(hfInvestment, interimAcctBuckets);

        InvestmentAllocation investmentAllocation = new InvestmentAllocation(hfInvestment.getInvestmentAllocationVOs());
        FilteredFundVO filteredFundVO = (FilteredFundVO) hfInvestment.getVO().getParentVO(FilteredFundVO.class);

        int notificationDays = 0;
        String notificationMode = "";
        String notificationDaysType = "";
        if (filteredFundVO != null && toFromStatus.equalsIgnoreCase("T"))
        {
            notificationDays = filteredFundVO.getSubscriptionNotificationDays();
            notificationMode = filteredFundVO.getSubscriptionModeCT();
            notificationDaysType = filteredFundVO.getSubscriptionDaysTypeCT();
        }
        else
        {
            if (transactionType.equalsIgnoreCase("WI") ||
                transactionType.equalsIgnoreCase("SW"))
            {
                notificationDays = filteredFundVO.getWithdrawalDays();
                notificationMode = filteredFundVO.getWithdrawalModeCT();
                notificationDaysType = filteredFundVO.getWithdrawalDaysTypeCT();
            }
            else if (transactionType.equalsIgnoreCase("TF"))
            {
                notificationDays = filteredFundVO.getTransferDays();
                notificationMode = filteredFundVO.getTransferModeCT();
                notificationDaysType = filteredFundVO.getTransferDaysTypeCT();
            }
            else if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
            {
                notificationDays = filteredFundVO.getLoanDays();
                notificationMode = filteredFundVO.getLoanModeCT();
                notificationDaysType = filteredFundVO.getLoanDaysTypeCT();
            }
        }

        String hftaEffectiveDate = calculateHftEffectiveDate(edOrigEffDate, notificationDays, notificationMode, notificationDaysType);

        GroupSetupVO hftaGroupSetupVO = createNewGroupSetupVO(groupSetupVO);

        ContractSetupVO hftaContractSetupVO = createNewContractSetupVO(segmentVO.getSegmentPK());
        hftaContractSetupVO.setPolicyAmount(invHistVO.getInvestmentDollars());

        //create the override for the HF
        long hfInvAllocPK = investmentAllocation.getPKForAllocationDollars(new EDITBigDecimal(invHistVO.getInvestmentDollars()));
        if (hfInvAllocPK == 0)
        {
            hfInvAllocPK = createNewInvestmentAllocation(hfInvestment, invHistVO.getInvestmentDollars());
        }

        InvestmentAllocationOverrideVO invAllocOvrdVO2 = new InvestmentAllocationOverrideVO();
        InvestmentAllocationOverrideVO invAllocOvrdVO1 = new InvestmentAllocationOverrideVO();

        //create the override for the Hedge Fund
        invAllocOvrdVO2 = createHedgeFundOverride(investmentKey, hfInvAllocPK, toFromStatus);
        hftaContractSetupVO.addInvestmentAllocationOverrideVO(invAllocOvrdVO2);

        if (toFromStatus.equalsIgnoreCase("T"))
        {
            //create the override for the HFIA
            Investment hfiaInvestment = getInvestment(hfiaInvAllocOvrdVO.getInvestmentFK());
            InvestmentAllocation hfiaInvestmentAllocation = new InvestmentAllocation(hfiaInvestment.getInvestmentAllocationVOs());
            long hfiaInvAllocPK = hfiaInvestmentAllocation.getPKForAllocationDollars(new EDITBigDecimal(invHistVO.getInvestmentDollars()));
            if (hfiaInvAllocPK == 0)
            {
                hfiaInvAllocPK = createNewInvestmentAllocation(hfiaInvestment, invHistVO.getInvestmentDollars());
            }

            invAllocOvrdVO1.setInvestmentAllocationOverridePK(0);
            invAllocOvrdVO1.setContractSetupFK(0);
            invAllocOvrdVO1.setInvestmentFK(hfiaInvestment.getPK());
            invAllocOvrdVO1.setInvestmentAllocationFK(hfiaInvAllocPK);
            invAllocOvrdVO1.setHFStatus("A");
            invAllocOvrdVO1.setHFIAIndicator("Y");
            invAllocOvrdVO1.setHoldingAccountIndicator("N");
            invAllocOvrdVO1.setToFromStatus("F");
        }
        else
        {
            //create the override for the HA
            FilteredFundVO[] haFilteredFundVOs =
                    engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(segmentVO.getProductStructureFK(),
                                                                                 filteredFundVO.getHoldingAccountFK(),
                                                                                 new ArrayList());
            if (haFilteredFundVOs != null && haFilteredFundVOs.length > 0)
            {
                invAllocOvrdVO1 = setupHoldingOrInterimAccount(haFilteredFundVOs[0],
                                                       segmentVO.getInvestmentVO(),
                                                        invHistVO.getInvestmentDollars(),
                                                         "Dollars",
                                                          segmentVO.getSegmentPK(),
                                                           "N", "Y");
            }
        }

        invAllocOvrdVO1.setBucketFK(interimAcctBucketFK);
        hftaContractSetupVO.addInvestmentAllocationOverrideVO(invAllocOvrdVO1);

        hftaContractSetupVO.setClientSetupVO(clientSetupVOs);
        ClientSetupVO[] hftaClientSetupVOs = hftaContractSetupVO.getClientSetupVO();
        for (int j = 0; j < hftaClientSetupVOs.length; j++)
        {
            hftaClientSetupVOs[j].removeAllEDITTrxVO();

            hftaClientSetupVOs[j].setClientSetupPK(0);
            hftaClientSetupVOs[j].setContractSetupFK(0);

            ContractClientAllocationOvrdVO[] contractClientAllocOvrdVOs = hftaClientSetupVOs[j].getContractClientAllocationOvrdVO();
            for (int k = 0; k < contractClientAllocOvrdVOs.length; k++)
            {
                contractClientAllocOvrdVOs[k].setContractClientAllocationFK(0);
                contractClientAllocOvrdVOs[k].setClientSetupFK(0);
            }

            hftaEDITTrxVO = new EDITTrxVO();
            hftaEDITTrxVO.setEDITTrxPK(0);
            hftaEDITTrxVO.setClientSetupFK(0);
            hftaEDITTrxVO.setEffectiveDate(hftaEffectiveDate);
            hftaEDITTrxVO.setStatus("N");
            hftaEDITTrxVO.setPendingStatus("P");
            hftaEDITTrxVO.setSequenceNumber(1);
            hftaEDITTrxVO.setTaxYear(Integer.parseInt(hftaEffectiveDate.substring(0, 4)));
            hftaEDITTrxVO.setTrxAmount(invHistVO.getInvestmentDollars());
            hftaEDITTrxVO.setNotificationAmount(invHistVO.getInvestmentDollars());
            hftaEDITTrxVO.setTransactionTypeCT("HFTA");
            hftaEDITTrxVO.setOriginatingTrxFK(editTrxVO.getEDITTrxPK());
            hftaEDITTrxVO.setNoCorrespondenceInd("N");
            hftaEDITTrxVO.setNoAccountingInd("N");
            hftaEDITTrxVO.setNoCommissionInd("N");
            hftaEDITTrxVO.setZeroLoadInd("N");
            hftaEDITTrxVO.setPremiumDueCreatedIndicator("N");
            hftaEDITTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            hftaEDITTrxVO.setOperator("System");
            if (toFromStatus.equalsIgnoreCase("T"))
            {
                hftaEDITTrxVO.setTransferTypeCT("Subscription");
            }
            else if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
            {
                hftaEDITTrxVO.setTransferTypeCT("RedemptionLoan");
            }
            else if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
            {
                hftaEDITTrxVO.setTransferTypeCT("SubscriptionLoanRepy");
            }
            else
            {
                hftaEDITTrxVO.setTransferTypeCT("RedemptionRemoval");
            }

            hftaClientSetupVOs[j].addEDITTrxVO(hftaEDITTrxVO);

            hftaGroupSetupVO.addContractSetupVO(hftaContractSetupVO);

//            GroupTrx groupTrx = new GroupTrx();

            SaveGroup saveGroup = new SaveGroup(hftaGroupSetupVO, hftaEDITTrxVO, "HFTA", segmentVO.getOptionCodeCT());

            saveGroup.build();

            saveGroup.save(notificationDays, notificationDaysType);
//            groupTrx.saveGroupSetup(hftaGroupSetupVO,
//                                    hftaEDITTrxVO,
//                                    "HFTA",
//                                    segmentVO.getOptionCodeCT(),
//                                    segmentVO.getProductStructureFK(),
//                                    notificationDays,
//                                    notificationDaysType);


            GroupSetupVO savedGroupSetupVO = saveGroup.getGroupSetupVO();
            ClientSetupVO clientSetupVO = savedGroupSetupVO.getContractSetupVO()[0].getClientSetupVO(0);
            hftaEDITTrxVO = clientSetupVO.getEDITTrxVO(0);
            ClientTrx clientTrx = new ClientTrx(hftaEDITTrxVO, editTrxVO.getOperator());
            clientTrx.setExecutionMode(executionMode);
            clientTrx.setCycleDate(cycleDate);
            clientStrategyArray.add(new Natural(clientTrx, "natural"));
        }
    }

    private long getInterimAccountBucketFK(Investment hfInvestment, Hashtable interimAccountBuckets)
    {
        long interimAcctBucketFK = 0;

        long hfInvestmentPK = ((InvestmentVO) hfInvestment.getVO()).getInvestmentPK();

        Enumeration hfInvestmentFKs = interimAccountBuckets.keys();

        while (hfInvestmentFKs.hasMoreElements())
        {
            String hfInvestmentFK = (String) hfInvestmentFKs.nextElement();

            if (Long.parseLong(hfInvestmentFK) == hfInvestmentPK)
            {
                interimAcctBucketFK = Long.parseLong((String) interimAccountBuckets.get(hfInvestmentFK));
            }
        }

        return interimAcctBucketFK;
    }

    private SegmentVO getSegment(long segmentFK) throws Exception
    {
        List segmentVOInclusionList = new ArrayList();
        segmentVOInclusionList.add(InvestmentVO.class);
        segmentVOInclusionList.add(InvestmentAllocationVO.class);

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        return contractLookup.composeSegmentVO(segmentFK, segmentVOInclusionList);
    }

    private Investment getInvestment(long investmentKey) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(FilteredFundVO.class);

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        return new Investment(contractLookup.composeInvestmentVOByPK(investmentKey, voInclusionList));
    }

    private GroupSetupVO createNewGroupSetupVO(GroupSetupVO groupSetupVO)
    {
        GroupSetupVO hftGroupSetupVO = new GroupSetupVO();
        hftGroupSetupVO = groupSetupVO;
        hftGroupSetupVO.setGroupSetupPK(0);
        hftGroupSetupVO.removeAllContractSetupVO();

        return hftGroupSetupVO;
    }

    private ContractSetupVO createNewContractSetupVO(long segmentFK)
    {
        ContractSetupVO hftContractSetupVO = new ContractSetupVO();
        hftContractSetupVO.setContractSetupPK(0);
        hftContractSetupVO.setGroupSetupFK(0);
        hftContractSetupVO.setSegmentFK(segmentFK);

        return hftContractSetupVO;
    }

    /**
     * Calculates the effective date for the HFTA transaction using the driving transaction's effective date, and the
     * notificationDays and notificationMode from the filteredFund table.
     * @param edOrigEffDate
     * @param notificationDays
     * @param notificationMode
     * @param notificationDaysType
     * @return
     */
    private String calculateHftEffectiveDate(EDITDate edOrigEffDate, int notificationDays, String notificationMode, String notificationDaysType)
    {
        EDITDate hftEffectiveDate = null;

        BusinessCalendar businessCalendar = new BusinessCalendar();

        if (notificationDays > 0)
        {
            if (notificationDaysType != null && notificationDaysType.equalsIgnoreCase("Business"))
            {
                BusinessDay businessDay = businessCalendar.findNextBusinessDay(edOrigEffDate, notificationDays);
                edOrigEffDate = businessDay.getBusinessDate();
            }
            else
            {
                edOrigEffDate.addDays(notificationDays);
            }

        }

        if (notificationMode.equalsIgnoreCase("Monthly"))
        {
            hftEffectiveDate = edOrigEffDate.getEndOfMonthDate();
        }
        else if(notificationMode.equalsIgnoreCase("Annual"))
        {
            hftEffectiveDate = new EDITDate(edOrigEffDate.getFormattedYear(), EDITDate.DEFAULT_MAX_MONTH, EDITDate.DEFAULT_MAX_DAY);
        }
        else
        {
            hftEffectiveDate = edOrigEffDate.getEndOfModeDate(notificationMode);
        }

        BusinessDay businessDay = businessCalendar.getBestBusinessDay(new EDITDate(hftEffectiveDate));
        hftEffectiveDate = businessDay.getBusinessDate();

        return hftEffectiveDate.getFormattedDate();
    }

    /**
     * Creates the InvestmentAllocationOverride record for the hedge fund
     * @param investmentPK
     * @param investmentAllocationPK
     * @param toFromStatus
     * @return
     */
    private InvestmentAllocationOverrideVO createHedgeFundOverride(long investmentPK, long investmentAllocationPK, String toFromStatus)
    {
        InvestmentAllocationOverrideVO invAllocOvrdVO = new InvestmentAllocationOverrideVO();
        invAllocOvrdVO.setInvestmentAllocationOverridePK(0);
        invAllocOvrdVO.setContractSetupFK(0);
        invAllocOvrdVO.setInvestmentFK(investmentPK);
        invAllocOvrdVO.setInvestmentAllocationFK(investmentAllocationPK);
        invAllocOvrdVO.setHFStatus("A");
        invAllocOvrdVO.setHFIAIndicator("N");
        invAllocOvrdVO.setHoldingAccountIndicator("N");
        invAllocOvrdVO.setToFromStatus(toFromStatus);

        return invAllocOvrdVO;
    }

    /**
     * Creates a new InvestmentAllocation for the InvestmentAllocationOverride record
     * @param investment
     * @param allocationDollars
     * @return
     * @throws Exception
     */
    private long createNewInvestmentAllocation(Investment investment, BigDecimal allocationDollars) throws Exception
    {
        InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getPK(), allocationDollars, "O", "Dollars");

        investmentAllocation.save();
        return investmentAllocation.getNewInvestmentAllocationPK();
    }

    /**
     * Adds an Investment record to the specified contract for the specified filtered fund
     * (The specified fund will either be the holding account or the interim account)
     * @param filteredFundVO
     * @param investmentVOs
     * @param haAllocation
     * @param segmentFK
     * @return
     * @throws Exception
     */
    private InvestmentAllocationOverrideVO setupHoldingOrInterimAccount(FilteredFundVO filteredFundVO,
                                                                        InvestmentVO[] investmentVOs,
                                                                        BigDecimal haAllocation,
                                                                        String allocationType,
                                                                        long segmentFK,
                                                                        String interimAccountIndicator,
                                                                        String holdingAccountIndicator) throws Exception
    {
        boolean fundFound = false;

        InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();

        for (int i = 0; i < investmentVOs.length; i++)
        {
            Investment investment = new Investment(investmentVOs[i]);

            if (investment.getFilteredFundFK().longValue() == filteredFundVO.getFilteredFundPK())
            {
                InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());

                long investmentAllocationPK = 0;
                if (allocationType.equalsIgnoreCase("Dollars"))
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(new EDITBigDecimal(haAllocation));
                }
                else
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationPercent(haAllocation);
                }

                if (investmentAllocationPK > 0)
                {
                    InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(0,
                                                                                                                 investment.getPK(),
                                                                                                                 investmentAllocationPK,
                                                                                                                 "T", "A",
                                                                                                                 interimAccountIndicator,
                                                                                                                 holdingAccountIndicator);
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    fundFound = true;
                }
                else
                {
                    investmentAllocation = new InvestmentAllocation(investment.getPK(), haAllocation, "O", allocationType);

                    investmentAllocation.save();
                    long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

                    InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(0,
                                                                                                                 investment.getPK(),
                                                                                                                 newInvestmentAllocationPK,
                                                                                                                 "T", "A",
                                                                                                                 interimAccountIndicator,
                                                                                                                 holdingAccountIndicator);
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    fundFound = true;
                }
            }
        }

        if (!fundFound)
        {
            Investment investment = new Investment(segmentFK, filteredFundVO.getFilteredFundPK());

            investment.save();
            long newInvestmentPK = investment.getNewInvestmentPK();

            InvestmentAllocation investmentAllocation = new InvestmentAllocation(newInvestmentPK, haAllocation, "O", allocationType);

            investmentAllocation.save();
            long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

            InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(0,
                                                                                                         newInvestmentPK,
                                                                                                         newInvestmentAllocationPK,
                                                                                                         "T", "A",
                                                                                                         interimAccountIndicator,
                                                                                                         holdingAccountIndicator);
            investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
        }

        return investmentAllocationOverrideVO;
    }

    /**
      * updates OverdueCharges
      * @param editTrxVO
      * @param overdueChargeList
      * @param crud
      */
     public void processOverdueChargeRecords(EDITTrxVO editTrxVO, List overdueChargeList, List overdueChargesSettledList, CRUD crud)
     {
        OverdueChargeVO overdueChargeVO = null;
        OverdueChargeSettledVO overdueChargeSettledVO = null;

        String transactionTypeCT = editTrxVO.getTransactionTypeCT();

        if (transactionTypeCT.equalsIgnoreCase("MD")  || transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION))
        {
            for (int i = 0; i < overdueChargeList.size(); i++)
            {
                overdueChargeVO = (OverdueChargeVO) overdueChargeList.get(i);

                overdueChargeVO.setEDITTrxFK(editTrxVO.getEDITTrxPK());

                crud.createOrUpdateVOInDB(overdueChargeVO);

                // update the pending status of transaction to 'O' since this MD transaction has overdue records
                editTrxVO.setPendingStatus("O");

                crud.createOrUpdateVOInDB(editTrxVO);
            }
        }
        else if (transactionTypeCT.equalsIgnoreCase("PY") || transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
        {
            for (int i = 0; i < overdueChargesSettledList.size(); i++)
            {
                overdueChargeSettledVO = (OverdueChargeSettledVO) overdueChargesSettledList.get(i);

                crud.createOrUpdateVOInDB(overdueChargeSettledVO);

                OverdueChargeVO[] overdueChargeVOs = new OverdueChargeDAO().findByPK(overdueChargeSettledVO.getOverdueChargeFK());

                EDITBigDecimal overdueAdmin = new EDITBigDecimal(overdueChargeVOs[0].getOverdueAdmin());
                EDITBigDecimal overdueCoi = new EDITBigDecimal(overdueChargeVOs[0].getOverdueCoi());
                EDITBigDecimal overdueExpense = new EDITBigDecimal(overdueChargeVOs[0].getOverdueExpense());

                overdueAdmin = overdueAdmin.subtractEditBigDecimal(Util.initBigDecimal(overdueChargeSettledVO, "SettledAdmin", new BigDecimal("0")));
                overdueCoi = overdueCoi.subtractEditBigDecimal(Util.initBigDecimal(overdueChargeSettledVO, "SettledCoi", new BigDecimal("0")));
                overdueExpense = overdueExpense.subtractEditBigDecimal(Util.initBigDecimal(overdueChargeSettledVO, "SettledExpense", new BigDecimal("0")));

                long mdEDITTrxPK = overdueChargeVOs[0].getEDITTrxFK();

                EDITTrxVO mdEDITTrxVO = (EDITTrxVO) crud.retrieveVOFromDB(EDITTrxVO.class, mdEDITTrxPK);

                // check if all the overdues are zero
                if (overdueAdmin.isEQ("0") && overdueCoi.isEQ("0") && overdueExpense.isEQ("0"))
                {
                    // if all overdues are zero update the pending status of the transaction that created
                    // these overdues record (MD transaction creates the overdues) to 'H'
                    String pendingStatus = mdEDITTrxVO.getPendingStatus();

                    // to make sure that the pending status is 'O'
                    if (pendingStatus.equalsIgnoreCase("O") )
                    {
                        mdEDITTrxVO.setPendingStatus("H");
                    }

                    // update the MD transaction record
                    crud.createOrUpdateVOInDB(mdEDITTrxVO);
                }
            }
        }
     }

    /**
     * Update the ProcessDateTime on EDITTrxHistory - used for repriced transactions.
     * @param editTrxVO
     * @param crud
     */
    private void resetProcessDateTime(EDITTrxVO editTrxVO, CRUD crud)
    {
        EDITTrxHistory editTrxHistory = EDITTrxHistory.findBy_EDITTrxPK(editTrxVO.getEDITTrxPK());
        if (editTrxHistory != null)
        {
            String currentDateTime = new EDITDateTime().getFormattedDateTime();
            editTrxHistory.setProcessDateTime(new EDITDateTime(currentDateTime));
            crud.createOrUpdateVOInDB(editTrxHistory.getVO());
        }
    }

    /**
     * Gets the investments and their buckets and bucketHistories from the document and creates the VOs with the proper
     * relationships. Then persists the fluffy investments.
     *
     * @param document
     * @throws EDITDeleteException 
     */
    private void updateInvestmentsWithBucketsAndBucketHistory(Document document, CRUD crud, 
    		long editTrxHistoryPK, List<BucketHistoryVO> bucketHistoryVOs) throws EDITDeleteException
    {
        List investmentElements = DOMUtil.getElements("InvestmentDocVO.InvestmentVO", document);

        for (int i = 0; i < investmentElements.size(); i++)
        {
            Element investmentElement = (Element) investmentElements.get(i);

            InvestmentVO investmentVO = (InvestmentVO) DOMUtil.mapElementToVO(investmentElement);

            if (investmentVO.getInvestmentPK() == 0)
            {
                List buckets = DOMUtil.getChildren("BucketVO", investmentElement);

                for (int j = 0; j < buckets.size(); j++)
                {
                    Element bucketElement = (Element) buckets.get(j);

                    BucketVO bucketVO = (BucketVO) DOMUtil.mapElementToVO(bucketElement);

                    List bucketHistories = DOMUtil.getChildren("BucketHistoryVO", bucketElement);

                    if (bucketHistories.size() < 1) {
                    	for (BucketHistoryVO bucketHistoryVO : bucketHistoryVOs) {
                    		if (bucketHistoryVO.getBucketFK() == bucketVO.getBucketPK()) {
                    			bucketHistories.add(bucketHistoryVO);
                    		}
                    	}
                    }
                    
                    for (int k = 0; k < bucketHistories.size(); k++)
                    {
                    	BucketHistoryVO bucketHistoryVO = null;
                    	
                    	if (bucketHistories.get(k) instanceof BucketHistoryVO) {
                    		bucketHistoryVO = (BucketHistoryVO) bucketHistories.get(k);
                    	} else {
                    		Element bucketHistoryElement = (Element) bucketHistories.get(k);
                        	bucketHistoryVO = (BucketHistoryVO) DOMUtil.mapElementToVO(bucketHistoryElement);
                    	}
                    	
                    	if (bucketHistoryVO != null) {
                    		bucketHistoryVO.setEDITTrxHistoryFK(editTrxHistoryPK);
                    		bucketVO.addBucketHistoryVO(bucketHistoryVO);
                    	}
                    }

                    investmentVO.addBucketVO(bucketVO);
                }

                List investmentHistories = DOMUtil.getChildren("InvestmentHistoryVO", investmentElement);

                for (int j = 0; j < investmentHistories.size(); j++)
                {
                    Element investmentHistoryElement = (Element) investmentHistories.get(j);

                    InvestmentHistoryVO investmentHistoryVO = (InvestmentHistoryVO) DOMUtil.mapElementToVO(investmentHistoryElement);

                    investmentHistoryVO.setEDITTrxHistoryFK(editTrxHistoryPK);

                    investmentVO.addInvestmentHistoryVO(investmentHistoryVO);
                }

                crud.createOrUpdateVOInDBRecursively(investmentVO);
            }
        }
    }

    private void updateContractGroupInvestment(SegmentVO segmentVO, EDITTrxVO editTrxVO, EDITTrxHistoryVO editTrxHistoryVO)
    {
        Segment segment = new Segment(segmentVO);
        EDITTrx editTrx = new EDITTrx(editTrxVO);
        EDITTrxHistory editTrxHistory = new EDITTrxHistory(editTrxHistoryVO);

        Set bucketHistories = editTrxHistory.getBucketHistories();

        for (Iterator iterator = bucketHistories.iterator(); iterator.hasNext();)
        {
            BucketHistory bucketHistory = (BucketHistory) iterator.next();

            Bucket bucket = Bucket.findByPK(bucketHistory.getBucketFK());

            // Get Investment from db
            Investment investment = Investment.findByPK(bucket.getInvestmentFK());

            Long chargeCodeFK = investment.getChargeCodeFK();
            Long filteredFundFK = investment.getFilteredFundFK();

            EDITDate effectiveDate = editTrx.getEffectiveDate();
            EDITDate processDate = editTrxHistory.getCycleDate();
    //        EDITDate processDate = editTrxHistory.getProcessDateTime().getEDITDate();

            EDITBigDecimal bucketHistoryCumUnits = bucketHistory.getCumUnits();

            ContractGroupInvestment contractGroupInvestment =
                    ContractGroupInvestment.findBy_ContractGroupFK_FilteredFundFK_ChargeCodeFK_EffectiveDate_ProcessDate(
                            segment.getContractGroupFK(), filteredFundFK, chargeCodeFK, effectiveDate, processDate);

            if (contractGroupInvestment != null)
            {
                //  An entry already exists in the PolicyGroupInvestment table, increase or decrease its accumulated units appropriately
                if (bucketHistory.getToFromStatus().equalsIgnoreCase("T"))
                {
                    //  Moving money "to" this fund, therefore add the bucketHistory cum units to contractGroupInvestment's accumulated units
                    contractGroupInvestment.increaseAccumulatedUnits(bucketHistoryCumUnits);
                }
                else
                {
                    //  Moving money "from" this fund, therefore subtract the bucketHistory cum units to contractGroupInvestment's accumulated units
                    contractGroupInvestment.decreaseAccumulatedUnits(bucketHistoryCumUnits);
                }
            }
            else
            {
                // An entry does not already exist, add it
                contractGroupInvestment.setContractGroupFK(segment.getContractGroupFK());
                contractGroupInvestment.setFilteredFundFK(filteredFundFK);
                contractGroupInvestment.setChargeCodeFK(chargeCodeFK);
                contractGroupInvestment.setEffectiveDate(effectiveDate);
                contractGroupInvestment.setProcessDate(processDate);
                contractGroupInvestment.setAccumulatedUnits(bucketHistoryCumUnits);
            }

            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            contractGroupInvestment.hSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
    }

    /**
     * Gets the premiumDue and its associated commissionPhase(s) from the document and creates the VOs with the proper
     * relationships. Then persists the fluffy investments.
     *
     * NOTE:  This method only saves new PremiumDues.  At this time, the script never changes an existing PremiumDue,
     * it only creates new ones.
     *
     * @param document
     * @throws EDITDeleteException 
     */
    private void updatePremiumDue(Document document, CRUD crud, long segmentPK, long editTrxPK) throws EDITDeleteException
    {
        boolean premiumDueUpdated = false;

        List premiumDueElements = DOMUtil.getElements("SegmentDocVO.SegmentVO.PremiumDueVO", document);

        for (int i = 0; i < premiumDueElements.size(); i++)
        {
            Element premiumDueElement = (Element) premiumDueElements.get(i);

            PremiumDueVO premiumDueVO = (PremiumDueVO) DOMUtil.mapElementToVO(premiumDueElement);

//            if (!premiumDueUpdated)
//            {
                updateCurrentPremiumDue(segmentPK, premiumDueVO.getEffectiveDate(), crud, editTrxPK);
//
//                premiumDueUpdated = true;
//            }


            if (premiumDueVO.getPremiumDuePK() == 0)
            {
                List commissionPhases = DOMUtil.getChildren("CommissionPhaseVO", premiumDueElement);

                for (int j = 0; j < commissionPhases.size(); j++)
                {
                    Element commissionPhaseElement = (Element) commissionPhases.get(j);

                    CommissionPhaseVO commissionPhaseVO = (CommissionPhaseVO) DOMUtil.mapElementToVO(commissionPhaseElement);

                    premiumDueVO.addCommissionPhaseVO(commissionPhaseVO);
                }

                crud.createOrUpdateVOInDBRecursively(premiumDueVO);
            }
        }
    }

    public void updateCurrentPremiumDue(long segmentPK, String effectiveDate, CRUD crud, long editTrxPK)
    {
    	PremiumDueVO[] premiumDueVOs = new contract.dm.dao.FastDAO().findPremiumDueBySegmentPKToReset(segmentPK, editTrxPK, new EDITDate(effectiveDate), crud);
    	
        if (premiumDueVOs != null)
        {
            for (int i = 0; i < premiumDueVOs.length; i++)
            {
                if (premiumDueVOs[i].getEDITTrxFK() != editTrxPK)
                {
                    String pendingExtractIndicator = premiumDueVOs[i].getPendingExtractIndicator();
                    if (pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_A) ||
                        pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_B) ||
                        pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_P))
                    {
                        premiumDueVOs[i].setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_D);
                    }
                    else if (pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_M) ||
                             pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_U))
                    {
                        premiumDueVOs[i].setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_E);
                    }
                    crud.createOrUpdateVOInDB(premiumDueVOs[i]);
                }
            }
        }
    }

    /**
     * Persists a CommissionPhase that already exists.
     * @param document
     * @param crud
     * @throws EDITDeleteException 
     */
    private void updateCommissionPhase(Document document, CRUD crud) throws EDITDeleteException
    {
        List commissionPhaseElements = DOMUtil.getElements("SegmentDocVO.SegmentVO.PremiumDueVO.CommissionPhaseVO", document);

        for (int i = 0; i < commissionPhaseElements.size(); i++)
        {
            Element commissionPhaseElement = (Element) commissionPhaseElements.get(i);

            CommissionPhaseVO commissionPhaseVO = (CommissionPhaseVO) DOMUtil.mapElementToVO(commissionPhaseElement);

            if (commissionPhaseVO.getCommissionPhasePK() != 0)
            {
                crud.createOrUpdateVOInDBRecursively(commissionPhaseVO);
            }
        }
    }

    private void updateContractClient(Document document,  CRUD crud)
    {
        List contractClientElements = DOMUtil.getElements("ClientDocVO.ClientVO.ContractClientVO", document);

        for (int i = 0; i < contractClientElements.size(); i++)
        {
            Element contractClientElement = (Element) contractClientElements.get(i);

            ContractClientVO contractClientVO = (ContractClientVO) DOMUtil.mapElementToVO(contractClientElement);

            crud.createOrUpdateVOInDB(contractClientVO);
        }
    }

    private boolean isRemovingMoney(String transactionTypeCT)
    {
        boolean removingMoney = false;

        return removingMoney;
    }

    public FilteredRequirementVO createContractRequirement(InsertRequirementVO insertRequirementVO, SegmentVO segmentVO)
    {
        ContractRequirement contractRequirement = new ContractRequirement();
        Requirement requirement = Requirement.findBy_RequirementId(insertRequirementVO.getRequirementId());
        Segment segment = new Segment(segmentVO);

        contractRequirement.setContractRequirementPK(new Long(0));
        contractRequirement.associateSegment(segment);
        contractRequirement.setFilteredRequirementFK(new Long(0));
        ContractRequirementVO contractRequirementVO = (ContractRequirementVO)contractRequirement.getVO();
        contractRequirement.calcFollowupDate(requirement.getFollowupDays(), contractRequirementVO);

        FilteredRequirementVO filteredRequirementVO = new FilteredRequirementVO();
        filteredRequirementVO.setFilteredRequirementPK(0);
        filteredRequirementVO.setProductStructureFK(segment.getProductStructureFK());
        filteredRequirementVO.setRequirementFK(requirement.getRequirementPK());
        filteredRequirementVO.addContractRequirementVO(contractRequirementVO);

        return filteredRequirementVO;
    }

    /**
     * Hokey solution to bring together buckets and their history without changing scripts.  The order is not how
     * originally specified one bucket to one history. A bucket can have more the one history and not be consecutive.
     * @param bucketHistories
     * @param document
     */
    private List captureBucketWithHistories(List buckets, List bucketHistories)
    {
        List newBucketList = new ArrayList();
        int saveCount = 0;
        int missedCount = 0;
        for (int i = 0; i < buckets.size(); i++)
        {
            BucketVO bucketVO = (BucketVO) buckets.get(i);
            long bucketPK = bucketVO.getBucketPK();

            for (int j = saveCount; j < bucketHistories.size(); j++)
            {
                BucketHistoryVO bucketHistoryVO = (BucketHistoryVO) bucketHistories.get(j);

                if (bucketPK == bucketHistoryVO.getBucketFK())
                {
                    if (bucketPK != 0)
                    {
                        bucketVO.addBucketHistoryVO(bucketHistoryVO);
                    }
                    else
                    {
                        bucketVO.addBucketHistoryVO(bucketHistoryVO);
                        saveCount = j + 1;
                        break;//break this loop only
                   }
                }
//                else
//                {
//                    missedCount = j;
//                }
//
//                if (saveCount > buckets.size())
//                {
//                    saveCount = missedCount;
//                }
            }

            newBucketList.add(bucketVO);
        }

        return newBucketList;
    }
}


