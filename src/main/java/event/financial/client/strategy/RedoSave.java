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

import contract.*;

import contract.dm.dao.DAOFactory;


import edit.common.ChangeProcessor;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITCRUDException;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.*;

import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.VOClass;
import edit.services.db.hibernate.SessionHelper;

import engine.Company;
import engine.Fee;

import engine.sp.SPOutput;
import engine.sp.custom.document.GroupSetupDocument;

import event.*;

import event.dm.dao.OverdueChargeDAO;

import event.financial.client.trx.ClientTrx;

import fission.utility.DOMUtil;
import fission.utility.DateTimeUtil;
import fission.utility.Util;

import java.math.BigDecimal;

import java.util.*;


import org.dom4j.Document;
import org.dom4j.Element;


public class RedoSave
{

    private String cycleDate;
    private int executionMode;
    private SegmentHistory segmentHistory;

    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    public void NaturalRedoSave()
    {

    }

    public ClientStrategy[] doUpdates(SPOutput spOutput, GroupSetupVO groupSetupvo, Object praseDocument, EDITTrxVO editTrxVO, String cycleDate, long productStructurePK) throws EDITEventException
    {
        this.cycleDate = cycleDate;
        this.executionMode = executionMode;

        SegmentVO baseSegmentVO = null;
        SegmentVO currentSegment = null;
        List<SegmentVO> segmentVOs = new ArrayList<SegmentVO>();
        PremiumDueVO premiumDueVO = null;
        EDITTrxHistoryVO editTrxHistoryVO = null;

        GroupSetupVO groupSetupVO = groupSetupvo;
        VOObject voObject = null;
        List bucketHistories = new ArrayList();
        List buckets = new ArrayList();
        WithholdingHistoryVO withholdingHistoryVO = null;
        List chargeHistory = new ArrayList();
        FinancialHistoryVO financialHistoryVO = null;
        List commissionablePremiumHistories = new ArrayList();
        List overdueChargesList = new ArrayList();
        List overdueChargesSettledList = new ArrayList();
        List commissionHistories = new ArrayList();
        List reinsuranceHistories = new ArrayList();
        List invHistoriesToSave = new ArrayList();
        List segmentHistories = new ArrayList();
        List commissionPhase = new ArrayList();
        InvestmentVO investmentVO = null;
        
        List clientStrategyArray = null;
        ClientStrategy[] clientStrategy = null;

        boolean premiumDueFound = false;
        boolean commissionPhaseFound = false;

        CRUD crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);
        //The trx components created in scripts will be stored here
        TreeMap editTrxComponents = new TreeMap();
        FilteredRequirementVO filteredRequirementVO = null;
        List<LifeVO> lifeVOs = new ArrayList<LifeVO>();
        PayoutVO payoutVO = null;

        // SL -- Clear hibernate sessions before starting crud transaction, so that hibernate will not have stale information.
        // If we do not clear the hibernate sessions the method checkForOutSuspense() that is calling event.saveSuspenseForPendingAmount()
        // has hibernate transaction and is updating the database with stale information. (When commit is issued via hibernate
        // all the entities in the session are updated to the database.) We should not have this problem when we completely
        // switch to Hibernate.
        SessionHelper.clearSessions();

        VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

        try
        {
            crud.startTransaction();

            for (int i = 0; i < voObjects.length; i++)
            {
                boolean persistCurrentVO = true;

                voObject = voObjects[i];

                String currentTableName = VOClass.getTableName(voObject.getClass());

                if (currentTableName.equalsIgnoreCase("BucketHistory") ||
                    currentTableName.equalsIgnoreCase("BucketChargeHistory") )
                {
                }
                else if (currentTableName.equalsIgnoreCase("SegmentHistory"))
                {
                    segmentHistories.add(voObject);
                }
                else if (currentTableName.equalsIgnoreCase("Bucket"))
                {
                    buckets.add(voObject);
                }
                else if(currentTableName.equalsIgnoreCase("AnnualizedSubBucket"))
                {
                    BucketVO lastBucketVO = (BucketVO) buckets.get(buckets.size() - 1);
                    lastBucketVO.addAnnualizedSubBucketVO((AnnualizedSubBucketVO) voObject);
                    buckets.set(buckets.size() - 1, lastBucketVO);
                }
                else if (currentTableName.equalsIgnoreCase("WithholdingHistory"))
                {
                    withholdingHistoryVO = (WithholdingHistoryVO) voObject;
                }
                else if (currentTableName.equalsIgnoreCase("ChargeHistory"))
                {
                    chargeHistory.add(voObject);
                }
                else if (currentTableName.equalsIgnoreCase("CommissionHistory"))
                {
                    commissionHistories.add(voObject);
                }
                else if (currentTableName.equalsIgnoreCase("ReinsuranceHistory"))
                {
                    reinsuranceHistories.add(voObject);
                }
                else if (currentTableName.equalsIgnoreCase("CommissionInvestmentHistory"))
                {
                    CommissionHistoryVO lastCommissionHistoryVO = (CommissionHistoryVO) commissionHistories.get(commissionHistories.size() - 1);
                    lastCommissionHistoryVO.addCommissionInvestmentHistoryVO((CommissionInvestmentHistoryVO) voObject);
                    commissionHistories.set(commissionHistories.size() - 1, lastCommissionHistoryVO);
                }
                else if (currentTableName.equalsIgnoreCase("FinancialHistory"))
                {
                    financialHistoryVO = (FinancialHistoryVO) voObject;
                    if (financialHistoryVO.getTaxableIndicator() == null)
                    {
                        financialHistoryVO.setTaxableIndicator("Y");
                    }
                }
                else if (currentTableName.equalsIgnoreCase("CommissionablePremiumHistory"))
                {
                	commissionablePremiumHistories.add(voObject);
                }
                else if (currentTableName.equalsIgnoreCase("EDITTrxHistory"))
                {
                    editTrxHistoryVO = (EDITTrxHistoryVO) voObject;
                }
                else if (currentTableName.equalsIgnoreCase("InvestmentHistory"))
                {
                    InvestmentHistoryVO investmentHistoryVO = (InvestmentHistoryVO) voObject;
                    invHistoriesToSave.add(investmentHistoryVO);
                }
                else if (currentTableName.equalsIgnoreCase("Investment"))
                {
                    investmentVO = (InvestmentVO) voObject;
                    if (investmentVO.getInvestmentPK() == 0)
                    {
                        persistCurrentVO = false;
                    }
                }
                else if (currentTableName.equalsIgnoreCase("OverdueCharge"))
                {
                    overdueChargesList.add(voObject);
                }
                else if (currentTableName.equalsIgnoreCase("OverdueChargeSettled"))
                {
                    overdueChargesSettledList.add(voObject);
                }
                //capture each groupSetup created in a table for processing later
                else if (currentTableName.equalsIgnoreCase("GroupSetup"))
                {
                    editTrxComponents.put(i + "", voObject);
                }
                else if (currentTableName.equalsIgnoreCase("AgentSnapshotDetail"))
                {
                    AgentSnapshotDetailVO asdVO = (AgentSnapshotDetailVO) voObject;
                    AgentSnapshotVO agentSnapshotVO = (AgentSnapshotVO) AgentSnapshot.findBy_PK(new Long(asdVO.getAgentSnapshotPK())).getVO();
                    agentSnapshotVO.setAdvanceAmount(asdVO.getAdvanceAmount());
                    agentSnapshotVO.setAdvanceRecovery(asdVO.getAdvanceRecovery());
                    voObject = agentSnapshotVO;

                    crud.createOrUpdateVOInDBRecursively(voObject, false);
                }
                else if (currentTableName.equalsIgnoreCase("ContractSetup") ||
                        (currentTableName.equalsIgnoreCase("ClientSetup"))  ||
                        (currentTableName.equalsIgnoreCase("EDITTrx"))      ||
                        (currentTableName.equalsIgnoreCase("OutSuspense"))  ||
                        (currentTableName.equalsIgnoreCase("RedoDoc")))
                {
                    ; //no action do not update database
                }
                else if (currentTableName.equalsIgnoreCase("PremiumDue")) // We assume that the SPOutputs are ordered and that SegmentVO (Base) has been found.
                {
                    premiumDueVO = (PremiumDueVO) voObject;
                    premiumDueFound = true;
                }
                else if (currentTableName.equalsIgnoreCase("CommissionPhase")) // We assume that the SPOutputs are ordered, and that the PremiumDueVO has already been established.
                {
                    if (premiumDueFound)
                    {
                        commissionPhase.add(voObject);
                        commissionPhaseFound = true;
                    }
                }
                else if (currentTableName.equalsIgnoreCase("InsertRequirement"))
                {
                    NaturalSave naturalSave = new NaturalSave();
                    filteredRequirementVO = naturalSave.createContractRequirement((InsertRequirementVO)voObject, baseSegmentVO);
                    voObject = filteredRequirementVO;
                }
                else if (currentTableName.equalsIgnoreCase("Life"))
                {
                    // lifeVO = (LifeVO) voObjects[i];
                    lifeVOs.add((LifeVO) voObject);
                    persistCurrentVO = false;
                }
                else if (currentTableName.equalsIgnoreCase("Payout"))
                {
                    payoutVO = (PayoutVO) voObjects[i];
                    persistCurrentVO = false;
                }
                else if (currentTableName.equalsIgnoreCase("SegmentSecondary"))
                {
                	persistCurrentVO = true;
                }
                else
                {
                    if (currentTableName.equalsIgnoreCase("Segment"))
                    {
                        currentSegment = (SegmentVO) voObject;

                        Segment segment = new Segment(currentSegment);
                        
                        segmentVOs.add(currentSegment);
                        
                        if (currentSegment.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_TRADITIONAL_LIFE) ||
                        	currentSegment.getSegmentFK() == 0L) 
                        {
                        	baseSegmentVO = currentSegment;
                        }

                        // verify if segment status is changed....
                        if (segment.isSegmentStatusChanged())
                        {
                            // generate change history record
                            new ChangeProcessor().generateChangeHistoryForSegmentStatusChange(currentSegment, editTrxVO.getOperator(), editTrxVO.getEffectiveDate());
                        }

                        // Talked to Tom and Rob, both said that ... we should be updating the segment after reapply process
                        // Not sure why we configured it to not update the SegmentVO.
                        // persistCurrentVO = false;
                    }

                    if (persistCurrentVO)
                    {
                        crud.createOrUpdateVOInDBRecursively(voObject, false);
                    }
                }
            }    //end of for loop
            crud.commitTransaction();
            crud.startTransaction();

            if (editTrxHistoryVO == null)
            {
                editTrxHistoryVO = editTrxVO.getEDITTrxHistoryVO(0);
            }

           //segmentHistory is created by code as of Sept 07
            String newStatus = null;
            Long segmentPKLong = new Long(groupSetupVO.getContractSetupVO(0).getSegmentFK());
            if (baseSegmentVO == null)
            {
                Segment segment = Segment.findByPK(segmentPKLong);
                newStatus = segment.getSegmentStatusCT();
                
                baseSegmentVO = (SegmentVO) segment.getVO();
            }
            else
            {
                newStatus = baseSegmentVO.getSegmentStatusCT();
                segmentPKLong = new Long(baseSegmentVO.getSegmentPK());
            }
            
            int baseIndex = -1;
            boolean matchFound = false;

            //Save Segments After History created
            if (lifeVOs.size() > 0 && segmentVOs.size() > 0)
            {
            	for (int x = 0; x < lifeVOs.size(); x++) 
            	{
            		for (int y = 0; y < segmentVOs.size(); y++)
            		{
            			matchFound = false;
            			
            			if (segmentVOs.get(y).getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_TRADITIONAL_LIFE) ||
            				segmentVOs.get(y).getSegmentFK() == 0L)
            			{
            				baseIndex = y;
            			}
            			
            			if (Long.compare(lifeVOs.get(x).getSegmentFK(), segmentVOs.get(y).getSegmentPK()) == 0) {
            				segmentVOs.get(y).addLifeVO(lifeVOs.get(x));
            				matchFound = true;
            			}
            		}
            		
            		if (!matchFound)
            		{
                        crud.createOrUpdateVOInDBRecursively(lifeVOs.get(x), false);
            		}
            	}
            	            	
            }
            else if (lifeVOs.size() > 0 && segmentVOs.size() == 0)
            {
            	for (LifeVO lifeVO : lifeVOs)
            	{
                    crud.createOrUpdateVOInDBRecursively(lifeVO, false);
            	}
            }

            if (payoutVO != null && segmentVOs.size() > 0)
            {
            	if (baseIndex > 0)
            	{
            		segmentVOs.get(baseIndex).addPayoutVO(payoutVO);
            	} 
            	else 
            	{
                    crud.createOrUpdateVOInDBRecursively(payoutVO, false);
            	}
            }
            else if (payoutVO != null)
            {
                crud.createOrUpdateVOInDBRecursively(payoutVO, false);
            }

            if (segmentVOs.size() > 0)
            {
            	for (SegmentVO segmentVO : segmentVOs) 
            	{
            		crud.createOrUpdateVOInDBRecursively(segmentVO, false);
            	}
            }
            //end of segmentHistory creation process now add it to the other history


            if (bucketHistoryExists(voObjects))
            {
                captureBucketHistories(bucketHistories, (GroupSetupDocument)praseDocument);
            }

            Hashtable interimAcctBuckets = populateEDITTrxHistoryFields(editTrxHistoryVO, editTrxVO,
                                                                    bucketHistories, withholdingHistoryVO,
                                                                    chargeHistory, commissionHistories,
                                                                    reinsuranceHistories, financialHistoryVO, commissionablePremiumHistories,
                                                                    invHistoriesToSave, investmentVO, buckets, segmentHistories, crud);

            crud.createOrUpdateVOInDBRecursively(editTrxHistoryVO, false);

            if (!editTrxComponents.isEmpty())
            {
                NaturalSave naturalSave = new NaturalSave();
                clientStrategy = naturalSave.saveSpawnedEDITTrx(editTrxComponents, voObjects, productStructurePK, crud, executionMode, cycleDate);
            }

            //  If the PremiumDue exists, update it before the commissionPhase
            if (premiumDueFound && commissionPhaseFound)
            {
                long segmentPK = 0;
                if (new Long(baseSegmentVO.getSegmentFK()) == null)
                {
                    segmentPK = baseSegmentVO.getSegmentPK();
                }
                else
                {
                    segmentPK = baseSegmentVO.getSegmentFK();
                }

                updateCurrentPremiumDue(segmentPK, premiumDueVO.getEffectiveDate(), crud, editTrxVO.getEDITTrxPK(), commissionPhase);

                if (premiumDueVO.getPremiumDuePK() == 0)
                {
                    for (int i = 0; i < commissionPhase.size(); i++)
                    {
                        premiumDueVO.addCommissionPhaseVO((CommissionPhaseVO)commissionPhase.get(i));
                    }

                    crud.createOrUpdateVOInDBRecursively(premiumDueVO);
                }
            }

            String transactionType = editTrxVO.getTransactionTypeCT();

            if (editTrxVO.getPendingStatus().equalsIgnoreCase("B") ||
                editTrxVO.getPendingStatus().equalsIgnoreCase("S"))
            {
                editTrxVO.setPendingStatus("L");
                resetProcessDateTime(editTrxVO, crud);
            }
            else if (editTrxVO.getPendingStatus().equalsIgnoreCase("F"))
            {
                editTrxVO.setPendingStatus("H");
                resetProcessDateTime(editTrxVO, crud);
            }
            else
            {
                if (transactionType.equalsIgnoreCase("HFTA") ||
                    transactionType.equalsIgnoreCase("HFTP") ||
                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT) ||
                    transactionType.equalsIgnoreCase("MD"))
                {
                    if (baseSegmentVO == null)
                    {
                        Segment segmentDoc = ((GroupSetupDocument) praseDocument).getGroupSetup().getContractSetup().getSegment();
                        baseSegmentVO = (SegmentVO)segmentDoc.getVO();
                    }

                    //check UnitValues table for forward price.  If found, pending status should be set to "H"
                    //If backward price found, pending status should be set to "L"
                    boolean forwardPriceFound = lookForForwardPrice(editTrxVO, baseSegmentVO, invHistoriesToSave);
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
                		baseSegmentVO.getSegmentStatusCT().equalsIgnoreCase("FSHedgeFundPend")) ||
                         (transactionType.equalsIgnoreCase("DE") &&
                        baseSegmentVO.getSegmentStatusCT().equalsIgnoreCase("DeathHedgeFundPend")))
                {
                    editTrxVO.setPendingStatus("L");
                }
                else
                {
//                    InvestmentVO[] allInvestmentVOs = redoDocVO.getBaseSegmentVO().getSegmentVO().getInvestmentVO();
//
//                    boolean allHedgeForwardPricesFound = checkHedgeFundsForForwardPrice(allInvestmentVOs, editTrxVO.getEffectiveDate());
//
//                    if (allHedgeForwardPricesFound)
//                    {
                        editTrxVO.setPendingStatus("H");
//                    }
//                    else
//                    {
//                        editTrxVO.setPendingStatus("L");
//                    }
//
//                    resetProcessDateTime(editTrxVO, crud);
                }
            }

            processOverdueChargeRecords(editTrxVO, overdueChargesList, overdueChargesSettledList, crud);

            crud.createOrUpdateVOInDB(editTrxVO);

            SuspenseVO suspenseVO = null;

            if (transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("TF"))
//            if (transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("TF") || transactionType.equalsIgnoreCase("RN"))
            {
                Segment segmentDoc = ((GroupSetupDocument) praseDocument).getGroupSetup().getContractSetup().getSegment();
                baseSegmentVO = (SegmentVO)segmentDoc.getVO();

                TrxGeneration trxGeneration = new TrxGeneration();
                trxGeneration.generateRenewalTrx(editTrxVO, groupSetupVO, baseSegmentVO, buckets, crud);
            }

            if (transactionType.equalsIgnoreCase("PE"))
            {
                NaturalSave naturalSave = new NaturalSave();

                if (baseSegmentVO == null)
            {
                    Segment segmentDoc = ((GroupSetupDocument) praseDocument).getGroupSetup().getContractSetup().getSegment();
                    baseSegmentVO = (SegmentVO)segmentDoc.getVO();
                }

                boolean generateTrx = naturalSave.determineIfRebalTrxNeeded(baseSegmentVO.getSegmentPK(), crud);

                long productKey = baseSegmentVO.getProductStructureFK();

                if (generateTrx)
                {
                    //todo - read edittrx for pr trx eff date, segmentpk, pendingstatus = 'p'

                    EDITTrxVO[] editTrxVOs = new event.dm.dao.FastDAO().findEDITTrxByEffectiveDateAndTrxTypeCT(editTrxVO.getEffectiveDate(), "PR", baseSegmentVO.getSegmentPK(), crud);
                    if (editTrxVOs != null)
                    {
                        for (int i = 0; i < editTrxVOs.length; i++)
                        {
                            if (editTrxVOs[i].getPendingStatus().equalsIgnoreCase("P") &&
                                editTrxVOs[i].getStatus().equalsIgnoreCase("A"))
                            {
                                generateTrx = false;
                                break;
                            }
                        }
                    }
                }

                if (generateTrx)
                {
                    List voInclusionList = new ArrayList();
                    voInclusionList.add(InvestmentVO.class);
                    voInclusionList.add(InvestmentAllocationVO.class);
                    voInclusionList.add(BucketVO.class);

                    crud.retrieveVOFromDBRecursively(baseSegmentVO, voInclusionList, true);

                    boolean modifyEffDate = false;

                    TrxGeneration trxGeneration = new TrxGeneration();
                    EDITTrxVO newEditTrxVO = trxGeneration.generateRebalanceTrx(editTrxVO, groupSetupVO, productKey, crud, baseSegmentVO, modifyEffDate);

                    ClientTrx naturalTrx = new ClientTrx(newEditTrxVO, editTrxVO.getOperator());
                    naturalTrx.setExecutionMode(executionMode);
                    naturalTrx.setCycleDate(cycleDate);
                    clientStrategyArray = new ArrayList();
                    clientStrategyArray.add(new Natural(naturalTrx, "natural"));
                    clientStrategy = (ClientStrategy[]) clientStrategyArray.toArray(new ClientStrategy[clientStrategyArray.size()]);
                }
            }

            crud.commitTransaction();

            if ((transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                 transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT)) &&
                editTrxVO.getPendingStatus().equalsIgnoreCase("H"))
            {
                CRUD crud2 = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

                try
                {
                    createDivisionFeeOffset(editTrxVO, baseSegmentVO.getContractNumber(), crud2);
                }
                catch (Exception e)
                {
                    System.out.println(e);

                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                    throw e;
                }
                finally
                {
                    if (crud2 != null)
                        crud2.close();

                    crud2 = null;
                }
            }

            if (interimAcctBuckets.size() > 0)
            {
                updateHFTrxBucketFK(editTrxVO.getEDITTrxPK(), interimAcctBuckets);
            }
        }
        catch (Exception e) // RuntimeException(s) need to be considered as well.
        {
            EDITEventException editEventException = new EDITEventException(e.getMessage());

            System.out.println(e);

            e.printStackTrace();

            try
            {
                crud.rollbackTransaction();
            }
            catch (EDITCRUDException e1)
            {
                System.out.println(e1);

                e1.printStackTrace();

                EDITEventException editEventException2 = new EDITEventException(e.getMessage());

                editEventException = editEventException2;
            }

            throw editEventException;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return clientStrategy;
    }

    public void updateCurrentPremiumDue(long segmentPK, String effectiveDate, CRUD crud, long editTrxPK, List commissionPhase)
    {
        PremiumDueVO[] premiumDueVOs = new contract.dm.dao.FastDAO().findPremiumDueBySegmentPK(segmentPK, new EDITDate(effectiveDate), crud);

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

    /**
     * Locates all BucketHistories, and BucketChargeHistories of the specified Document and adds them to the
     * specified BucketHistories List. BucketChargeHistories are added as children to their parent BucketHistory.
     * @param bucketHistories
     * @param document
     */
    private List captureBucketHistories(List bucketHistories, Document document)
    {
        GroupSetupDocument groupSetupDocument = (GroupSetupDocument)document;

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

        return bucketHistories;
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


    private void updateHFTrxBucketFK(long editTrxPK, Hashtable interimAcctBuckets) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(InvestmentAllocationOverrideVO.class);

        EDITTrxVO[] editTrxVO = event.dm.dao.DAOFactory.getEDITTrxDAO().findSpawnedTrx(editTrxPK);
        if (editTrxVO != null && editTrxVO.length > 0)
        {
            event.business.Event eventComponent = new event.component.EventComponent();

            for (int i = 0; i < editTrxVO.length; i++)
            {
                EDITTrxVO spawnedEditTrxVO = eventComponent.composeEDITTrxVOByEDITTrxPK(editTrxVO[i].getEDITTrxPK(), voInclusionList);

                InvestmentAllocationOverrideVO[] invAllocOverrideVOs = ((ContractSetupVO) spawnedEditTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class)).getInvestmentAllocationOverrideVO();

                long interimAcctBucketFK = getInterimAccountBucketFK(invAllocOverrideVOs, interimAcctBuckets);

                for (int j = 0; j < invAllocOverrideVOs.length; j++)
                {
                    if (invAllocOverrideVOs[j].getBucketFK() > 0)
                    {
                        invAllocOverrideVOs[j].setBucketFK(interimAcctBucketFK);

                        InvestmentAllocationOverride invAllocOvrd = new InvestmentAllocationOverride(invAllocOverrideVOs[j]);

                        invAllocOvrd.saveNonRecursively();
                    }
                }
            }
        }
    }

    private SuspenseVO createSuspense(SegmentVO segmentVO, EDITTrxHistoryVO editTrxHistoryVO,
                                      EDITTrxVO editTrxVO, GroupSetupVO groupSetupVO)
    {
        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);

        if (segmentVO == null)
        {
            segmentVO = DAOFactory.getSegmentDAO().findBySegmentPK(contractSetupVO.getSegmentFK(), false, new ArrayList())[0];
        }

        String userDefNumber = segmentVO.getContractNumber();
        String effectiveDate = editTrxVO.getEffectiveDate();
        FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();
        // double suspenseAmount = financialHistoryVO[0].getCheckAmount();
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/29/2004
        EDITBigDecimal suspenseAmount = new EDITBigDecimal(financialHistoryVO[0].getCheckAmount());

        InSuspenseVO inSuspenseVO = new InSuspenseVO();
        // inSuspenseVO.setAmount(suspenseAmount);
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/29/2004
        inSuspenseVO.setAmount(suspenseAmount.getBigDecimal());
        inSuspenseVO.setEDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
        inSuspenseVO.setInSuspensePK(0);
        inSuspenseVO.setSuspenseFK(0);

        SuspenseVO suspenseVO = new SuspenseVO();
        suspenseVO.setAccountingPendingInd("N");
        suspenseVO.setDirectionCT("Apply");
        suspenseVO.setEffectiveDate(effectiveDate);
        suspenseVO.setMaintenanceInd("M");
        suspenseVO.setMaintDateTime(editTrxVO.getMaintDateTime());
        suspenseVO.setOperator(editTrxVO.getOperator());
        suspenseVO.addInSuspenseVO(inSuspenseVO);
        String processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate().getFormattedDate();
        suspenseVO.setProcessDate(processDate);
        suspenseVO.setSuspenseAmount(suspenseAmount.getBigDecimal());
        suspenseVO.setSuspensePK(0);
        suspenseVO.setUserDefNumber(userDefNumber);
        suspenseVO.setSuspenseType("Contract");
        Company company = Company.findByProductStructurePK(new Long(segmentVO.getProductStructureFK()));
        suspenseVO.setCompanyFK(company.getCompanyPK().longValue());

        return suspenseVO;
    }

    /**
     * REDO transaction process is now started though document activation and the use of hibernate. Therefore as this document wa
     * built and copies to the undo history were made using hibernate primary keys have been assigned. The setting the
     * EDITTrxHistoryPK must take place in all the children is case they were created new by the script.
     * @param editTrxHistoryVO
     * @param editTrxVO
     * @param bucketHistory
     * @param withholdingHistoryVO
     * @param chargeHistory
     * @param commissionHistories
     * @param reinsuranceHistories
     * @param financialHistoryVO
     * @param invHistoriesToSave
     * @param buckets
     * @param segmentHistories
     * @param crud
     * @return
     * @throws EDITEventException
     */
    private Hashtable populateEDITTrxHistoryFields(EDITTrxHistoryVO editTrxHistoryVO, EDITTrxVO editTrxVO,
                                                   List bucketHistory, WithholdingHistoryVO withholdingHistoryVO,
                                                   List chargeHistory, List commissionHistories,
                                                   List reinsuranceHistories, FinancialHistoryVO financialHistoryVO, List commissionablePremiumHistories,
                                                   List invHistoriesToSave, InvestmentVO investmentVO, 
                                                   List buckets, List segmentHistories, CRUD crud) throws EDITEventException
    {
     
 //       Processing a pending redo transaction was generating the redo EDITTrxHistory, but it not associating it to the redo EDITTrx.
//        This simple code fix will associate EDITTrxHistory with its parent EDITTrx and show these redo transactions in history.
          	editTrxHistoryVO.setEDITTrxFK(editTrxVO.getEDITTrxPK());
        
  //     Hibernate has set this already
 //       editTrxHistoryVO.setProcessDate(currentDate);
//        editTrxHistoryVO.setProcessTime(EDITDate.getCurrentTime());

        EDITDate currentDate = new EDITDate();
        editTrxHistoryVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());

        String transactionType = editTrxVO.getTransactionTypeCT();
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

        editTrxHistoryVO.setCorrespondenceTypeCT("Confirm");

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

        //if (transactionType.equals("PO") || transactionType.equals("WI") || transactionType.equals("FS") || transactionType.equals("LS")
        //                                 || transactionType.equals(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN))
        //{
        //    editTrxHistoryVO.setControlNumber(CRUD.getNextAvailableKey() + "");
        //}

        long investmentPK = -1;
        if (investmentVO != null) {
            investmentPK = crud.createOrUpdateVOInDB(investmentVO);
        }
        
        Hashtable interimAcctBuckets = new Hashtable();

        if (bucketHistory != null && bucketHistory.size() > 0)
        {
            interimAcctBuckets = updateBucketsAndHistory(buckets, editTrxVO, editTrxHistoryVO, bucketHistory, investmentVO, crud);
        }

        if (withholdingHistoryVO != null)
        {
            editTrxHistoryVO.removeAllWithholdingHistoryVO();
            withholdingHistoryVO.setWithholdingHistoryPK(0);
            editTrxHistoryVO.addWithholdingHistoryVO(withholdingHistoryVO);
        }

        if (chargeHistory != null  && chargeHistory.size() > 0)
        {
            editTrxHistoryVO.removeAllChargeHistoryVO();
            for (int i = 0; i < chargeHistory.size(); i++)
            {
                ChargeHistoryVO chargeHistoryVO = (ChargeHistoryVO)chargeHistory.get(i);
                editTrxHistoryVO.addChargeHistoryVO(chargeHistoryVO);

            }
        }

        if (commissionHistories.size() > 0)
        {
            editTrxHistoryVO.removeAllCommissionHistoryVO();
            for (int i = 0; i < commissionHistories.size(); i++)
            {
                CommissionHistoryVO commissionHistoryVO = (CommissionHistoryVO) commissionHistories.get(i);
                editTrxHistoryVO.addCommissionHistoryVO(commissionHistoryVO);
            }
        }

        if (reinsuranceHistories.size() > 0)
        {
            editTrxHistoryVO.removeAllReinsuranceHistoryVO();
            for (int i = 0; i < reinsuranceHistories.size(); i++)
            {
                ReinsuranceHistoryVO reinsuranceHistoryVO = (ReinsuranceHistoryVO) reinsuranceHistories.get(i);
                editTrxHistoryVO.addReinsuranceHistoryVO(reinsuranceHistoryVO);
            }
        }

        if (financialHistoryVO != null)
        {
            editTrxHistoryVO.removeAllFinancialHistoryVO();
            editTrxHistoryVO.addFinancialHistoryVO(financialHistoryVO);
        }
        
        if (commissionablePremiumHistories != null)
        {
            editTrxHistoryVO.removeAllCommissionablePremiumHistoryVO();
            for (int i = 0; i < commissionablePremiumHistories.size(); i++)
            {
            	CommissionablePremiumHistoryVO commissionablePremiumHistoryVO = (CommissionablePremiumHistoryVO) commissionablePremiumHistories.get(i);
                editTrxHistoryVO.addCommissionablePremiumHistoryVO(commissionablePremiumHistoryVO);
            }
        }

        if (segmentHistories.size() > 0)
        {
            editTrxHistoryVO.removeAllSegmentHistoryVO();
            for (int i = 0; i < segmentHistories.size(); i++)
            {
                editTrxHistoryVO.addSegmentHistoryVO((SegmentHistoryVO) segmentHistories.get(i));
            }
        }
        
        if (invHistoriesToSave.size() > 0)
        {
            if (editTrxVO.getPendingStatus().equalsIgnoreCase("F") &&
                editTrxVO.getPendingStatus().equalsIgnoreCase("B"))
            {
                for (int i = 0; i < invHistoriesToSave.size(); i++)
                {
                    editTrxHistoryVO.addInvestmentHistoryVO((InvestmentHistoryVO) invHistoriesToSave.get(i));
                }
            }
            else
            {
                editTrxHistoryVO.removeAllInvestmentHistoryVO();
                for (int i = 0; i < invHistoriesToSave.size(); i++)
                {
                     InvestmentHistoryVO investmentHistoryVO = (InvestmentHistoryVO) invHistoriesToSave.get(i);
                     if (investmentPK >= 0 && investmentHistoryVO.getInvestmentFK() == 0) {
                    	 investmentHistoryVO.setInvestmentFK(investmentPK);
                     }
                     editTrxHistoryVO.addInvestmentHistoryVO(investmentHistoryVO);
                }
            }
        }
        
        EDITTrx editTrx = new EDITTrx(editTrxVO);
        if (editTrx.isRemovalTransaction())
        {
            SuspenseVO[] suspenseVOs = event.dm.dao.DAOFactory.getSuspenseDAO().findBy_EDITTrxFK(editTrxVO.getReapplyEDITTrxFK(), crud);
            if (suspenseVOs != null)
            {
                for (int i = 0; i < suspenseVOs.length; i++)
                {
                    //todo create or update the existing insuspense
                    if (suspenseVOs[i].getMaintenanceInd().equalsIgnoreCase("U"))
                    {
                        suspenseVOs[i].setMaintenanceInd(Suspense.MAINTENANCE_IND_M);
                        suspenseVOs[i].setSuspensePK(0);
                        long suspensePK = crud.createOrUpdateVOInDB(suspenseVOs[i]);

                        InSuspenseVO inSuspenseVO = (InSuspenseVO)suspenseVOs[0].getInSuspenseVO(0);
                        inSuspenseVO.setInSuspensePK(0);
                        inSuspenseVO.setEDITTrxHistoryFK(0);
                        inSuspenseVO.setSuspenseFK(suspensePK);

                        editTrxHistoryVO.removeAllInSuspenseVO();
                        editTrxHistoryVO.addInSuspenseVO(inSuspenseVO);
                        break;
                    }
                }
            }
        }

        String accountingPeriod = DateTimeUtil.buildAccountingPeriod(new EDITDate(cycleDate));

        editTrxVO.setAccountingPeriod(accountingPeriod);
        editTrxVO.setOriginalAccountingPeriod(accountingPeriod);

        return interimAcctBuckets;
    }

    private Hashtable updateBucketsAndHistory(List buckets,
                                         EDITTrxVO editTrxVO,
                                         EDITTrxHistoryVO editTrxHistoryVO,
                                         List bucketHistories,
                                         InvestmentVO investmentVO,
                                         CRUD crud) throws EDITEventException
    {
        Hashtable interimAcctBuckets = new Hashtable();

        String bucketSourceCT = null;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_TRANSFER_AMT))
        {
            String originatingTrxType = EDITTrx.getOriginatingTrxType(editTrxVO.getOriginatingTrxFK());
            if (originatingTrxType != null && (originatingTrxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_TRANSFER) ||
                    originatingTrxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN)))
            {
                bucketSourceCT = Bucket.BUCKETSOURCECT_TRANSFER;
            }
        }

        try
        {

            for (int i = 0; i < buckets.size(); i++)
            {
                BucketVO bucketVO = (BucketVO) buckets.get(i);
                long bucketKey = bucketVO.getBucketPK();

                if (bucketSourceCT != null && bucketVO.getBucketPK() == 0)
                {
                    bucketVO.setBucketSourceCT(bucketSourceCT);
                }

                if (investmentVO != null && bucketVO.getInvestmentFK() == 0) {
                	bucketVO.setInvestmentFK(investmentVO.getInvestmentPK());
                	investmentVO.addBucketVO(bucketVO);
                }
                
                bucketKey = crud.createOrUpdateVOInDB(bucketVO);

                AnnualizedSubBucketVO[] annSubBucketVOs = bucketVO.getAnnualizedSubBucketVO();
                if (annSubBucketVOs != null)
                {
                    for (int j = 0; j < annSubBucketVOs.length; j++)
                    {
                        annSubBucketVOs[j].setBucketFK(bucketKey);
                        crud.createOrUpdateVOInDB(annSubBucketVOs[j]);
                    }
                }

                //the order of buckets and history should be the same
                BucketHistoryVO bucketHistoryVO = (BucketHistoryVO) bucketHistories.get(i);
                bucketHistoryVO.setBucketFK(bucketKey);
                if ((bucketHistoryVO.getInterimAccountIndicator() != null &&
                     bucketHistoryVO.getInterimAccountIndicator().equalsIgnoreCase("Y")) ||
                    (bucketHistoryVO.getHoldingAccountIndicator() != null &&
                     bucketHistoryVO.getHoldingAccountIndicator().equalsIgnoreCase("Y")))
                {
                    interimAcctBuckets.put(bucketHistoryVO.getHedgeFundInvestmentFK() + "", bucketKey + "");
                }
                
            }

            BucketHistoryVO[] existingBucketHistories = editTrxHistoryVO.getBucketHistoryVO();

            editTrxHistoryVO.removeAllBucketHistoryVO();

            for (int j = 0; j < bucketHistories.size(); j++)
            {
                BucketHistoryVO bucketHistoryVO = (BucketHistoryVO) bucketHistories.get(j);

                editTrxHistoryVO.addBucketHistoryVO(bucketHistoryVO);
            }

            boolean bucketHistoryFound = false;

            for (int j = 0; j < existingBucketHistories.length; j++)
            {
                bucketHistoryFound = false;

                for (int k = 0; k < bucketHistories.size(); k++)
                {
                    if (((BucketHistoryVO) bucketHistories.get(k)).getBucketHistoryPK() == existingBucketHistories[j].getBucketHistoryPK())
                    {
                        bucketHistoryFound = true;
                        break;
                    }
                }

                if (!bucketHistoryFound)
                {
                    editTrxHistoryVO.addBucketHistoryVO(existingBucketHistories[j]);
                }
            }
        }
        catch (Exception e)
        {
            EDITEventException editEventException = new EDITEventException(e.getMessage());

            System.out.println(e);

            e.printStackTrace();

            try
            {
                crud.rollbackTransaction();
            }
            catch (EDITCRUDException e1)
            {
                System.out.println(e1);

                e1.printStackTrace();

                EDITEventException editEventException2 = new EDITEventException(e.getMessage());

                editEventException = editEventException2;
            }

            throw editEventException;
        }
        finally
        {
            return interimAcctBuckets;
        }
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

        if (transactionTypeCT.equalsIgnoreCase("MD") || transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION))
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
     * Search UnitValue table for forward price (based on the effective date of the transaction)
     * @param editTrxVO
     * @param baseSegmentVO
     * @return
     * @throws Exception
     */
    private boolean lookForForwardPrice(EDITTrxVO editTrxVO, SegmentVO baseSegmentVO, List investmentHistories) throws Exception
    {
        boolean forwardPriceFound = false;
        boolean hedgeFundFound = false;

        EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(UnitValuesVO.class);

        String transactionType = editTrxVO.getTransactionTypeCT();
        boolean notAllPricesSet = true;

        if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT))
        {
            for (int i = 0; i < investmentHistories.size(); i++)
            {
                InvestmentHistoryVO investmentHistoryVO = (InvestmentHistoryVO)investmentHistories.get(i);
                  if (investmentHistoryVO.getFinalPriceStatus() == null)
                  {
                      notAllPricesSet = false;
                  }
            }

            if (!notAllPricesSet)
            {
                forwardPriceFound = false;
            }
            else
            {
                forwardPriceFound = true;
            }
        }
        else
        {
            if (!editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MODALDEDUCTION))
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
                contract.business.Lookup contractLookup = new contract.component.LookupComponent();

                boolean allForwardPricesFoundForHF = true;
                InvestmentVO[] investmentVOs = contractLookup.composeInvestmentVOBySegmentPK(baseSegmentVO.getSegmentPK(), new ArrayList());
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
        }

        return forwardPriceFound;
    }

    /**
     * Search UnitValue table for forward price on all hedge funds associated with this transaction
     * (based on the effective date of the transaction)
     * @param investmentVOs - all investments included in this transaction
     * @param trxEffDate - effective date of this transaction
     * @return
     * @throws Exception
     */
    private boolean checkHedgeFundsForForwardPrice(InvestmentVO[] investmentVOs, String trxEffDate) throws Exception
    {
        boolean forwardPricesFound = true;

        EDITDate edTrxEffDate = new EDITDate(trxEffDate);

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(UnitValuesVO.class);

        for (int i = 0; i < investmentVOs.length; i++)
        {
            FundVO fundVO = engineLookup.composeFundVOByFilteredFundPK(investmentVOs[i].getFilteredFundFK(), voInclusionList);
            if (fundVO.getFundType().equalsIgnoreCase("Hedge"))
            {
                FilteredFundVO[] filteredFundVOs = fundVO.getFilteredFundVO();
                for (int j = 0; j < filteredFundVOs.length; j++)
                {
                    UnitValuesVO[] unitValuesVOs = filteredFundVOs[j].getUnitValuesVO();
                    for (int k = 0; k < unitValuesVOs.length; k++)
                    {
                        EDITDate edUVEffDate = new EDITDate(unitValuesVOs[k].getEffectiveDate());
                        if (edUVEffDate.before(edTrxEffDate))
                        {
                            forwardPricesFound = false;
                            k = unitValuesVOs.length;
                            j = filteredFundVOs.length;
                        }
                    }
                }
            }
        }

        return forwardPricesFound;
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
            EDITDateTime currentDateTime = new EDITDateTime();

            editTrxHistory.setProcessDateTime(currentDateTime);
            crud.createOrUpdateVOInDB(editTrxHistory.getVO());
        }
    }

    private long getInterimAccountBucketFK(InvestmentAllocationOverrideVO[] invAllocOvrdVOs, Hashtable interimAccountBuckets)
    {
        long interimAcctBucketFK = 0;

        Enumeration hfInvestmentFKs = null;

        boolean bucketFKFound = false;

        for (int i = 0; i < invAllocOvrdVOs.length; i++)
        {
            hfInvestmentFKs = interimAccountBuckets.keys();

            while (hfInvestmentFKs.hasMoreElements())
            {
                String hfInvestmentFK = (String) hfInvestmentFKs.nextElement();

                if (Long.parseLong(hfInvestmentFK) == invAllocOvrdVOs[i].getInvestmentFK())
                {
                    interimAcctBucketFK = Long.parseLong((String) interimAccountBuckets.get(hfInvestmentFK));

                    bucketFKFound = true;
                }
            }

            if (bucketFKFound)
            {
                break;
            }
        }

        return interimAcctBucketFK;
    }
}
