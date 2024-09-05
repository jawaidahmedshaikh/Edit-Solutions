/*
 * User: gfrosti
 * Date: Jul 30, 2003
 * Time: 1:23:32 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package event.financial.client.strategy;

import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.*;
import edit.services.config.ServicesConfig;
import event.dm.dao.*;
import event.dm.composer.EDITTrxComposer;
import event.financial.client.trx.ClientTrx;
import event.*;
import event.business.Event;
import event.common.TransactionPriorityCache;
import event.component.EventComponent;

import java.math.BigDecimal;
import java.util.*;

import billing.BillSchedule;
import contract.dm.composer.VOComposer;
import contract.dm.dao.FastDAO;
import contract.dm.dao.InvestmentDAO;
import contract.AnnualizedSubBucket;
import contract.ContractClient;
import contract.Segment;
import contract.Investment;
import contract.PremiumDue;
import reinsurance.ReinsuranceHistory;
import fission.utility.Util;
import engine.Fee;
import org.apache.logging.log4j.Logger;
import logging.*;
import role.ClientRoleFinancial;
import engine.*;


public class Reversal extends ClientStrategy
{
    private Map fundTypeCache;

    private SegmentVO segmentVO = null;
    private SegmentVO[] riderSegmentVOs = null;

    private boolean quoteInProcess = false;

    public Reversal(ClientTrx clientTrx)
    {
        super(clientTrx);

        this.fundTypeCache = new HashMap();
    }

    public Reversal(ClientTrx clientTrx, String sortStatus)
    {
        super(clientTrx);
        super.setSortStatus(sortStatus);

        this.fundTypeCache = new HashMap();
    }

    public Reversal(ClientTrx clientTrx, boolean quoteInProcess)
    {
        super(clientTrx);

        this.quoteInProcess = quoteInProcess;
        this.fundTypeCache = new HashMap();
    }

    public ClientStrategy[] execute() throws EDITEventException
    {
        CRUD eventCRUD = null;

        CRUD contractCRUD = null;

        ClientStrategy[] clientStrategys = null;

        try
        {
            EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();

            String transactionType = editTrxVO.getTransactionTypeCT();

            ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(super.getClientTrx().getEDITTrxVO().getEDITTrxPK());

            String complexChangeType = null;
            if (contractSetupVO != null && contractSetupVO.length > 0) 
            {
            	complexChangeType = contractSetupVO[0].getComplexChangeTypeCT();
            }
            
            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

            List segmentVOInclusionList = new ArrayList();
            segmentVOInclusionList.add(SegmentVO.class);
            segmentVOInclusionList.add(LifeVO.class);
            segmentVOInclusionList.add(AgentHierarchyVO.class);
            segmentVOInclusionList.add(AgentSnapshotVO.class);

            segmentVO = contractLookup.composeSegmentVO(contractSetupVO[0].getSegmentFK(), segmentVOInclusionList);

            long editTrxPK = editTrxVO.getEDITTrxPK();

            EDITTrxHistoryVO[] editTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findByEditTrxPK(editTrxPK);

            //This check must be done before changing the edittrx PendingStatus ot "H".  The original status must be "H" for this to work correctly
            if ((transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                 transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT)) &&
                editTrxVO.getPendingStatus().equalsIgnoreCase("H"))
            {
                CRUD engineCrud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

                try
                {
                    if (!quoteInProcess)
                    {
                        createDivisionFeeOffset(editTrxVO, segmentVO.getContractNumber(), engineCrud);
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);

                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                    throw e;
                }
                finally
                {
                    if (engineCrud != null)
                        engineCrud.close();

                    engineCrud = null;
                }
            }
            
            eventCRUD = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            // check if trx is a BC with complexChangeType of 'Batch'
            if (transactionType != null && transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) && 
            		complexChangeType != null && complexChangeType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_BATCH)) {
            	// 'Batch' BCs prepare system for another upcoming change... set EditTrx status but make no other changes to db
            	updateEDITTrxVO(eventCRUD);
            	 
            } else {
                contractCRUD = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
            	updateEDITTrxHistoryVO(editTrxHistoryVO[0], Util.initString(editTrxVO.getTransferTypeCT(), ""), eventCRUD, contractCRUD, contractSetupVO[0]);
            }


            // special handling for un-lapse
            if(editTrxVO.getTransactionTypeCT().equalsIgnoreCase("LA"))
            {
            	updatePremiumDueForLapse(editTrxPK, new EDITDate(editTrxVO.getEffectiveDate()));
            } else
            {
            	updatePremiumDue(editTrxVO, segmentVO.getSegmentPK());
            }

            Segment segment = new Segment(segmentVO);

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY"))
            {
                deleteRenewalTrx(editTrxPK);
                updateBillSchedule(segmentVO, editTrxVO, eventCRUD);
            }
            
            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("LA") ||
        		editTrxVO.getTransactionTypeCT().equalsIgnoreCase("DE") ||
        		editTrxVO.getTransactionTypeCT().equalsIgnoreCase("NT") ||
        		editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FS"))
            {
                updateBillScheduleTermination(segmentVO, editTrxVO, eventCRUD);
            }

            CommissionHistoryVO[] commissionHistoryVOs = DAOFactory.getCommissionHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryVO[0].getEDITTrxHistoryPK());

            if (commissionHistoryVOs != null)
            {
                String status = editTrxVO.getStatus();
                createAndSaveReversalCommissionHistories(commissionHistoryVOs, contractSetupVO[0], status, editTrxVO.getTransactionTypeCT(), segment, eventCRUD);
            }

            ReinsuranceHistory[] reinsuranceHistories = ReinsuranceHistory.findBy_EDITTrxHistoryFK(editTrxHistoryVO[0].getEDITTrxHistoryPK());
            if (reinsuranceHistories != null)
            {
                createAndSaveReversalReinsuranceHistories(reinsuranceHistories, eventCRUD);
            }

            //Reset the NotificationAmountReceived on the Originating transaction for HFTA,
            //HDTH, HLOAN, and HREM transactions for REVERSAL transactions only
            if (editTrxVO.getStatus().equalsIgnoreCase("R") &&
                editTrxVO.getOriginatingTrxFK() > 0 &&
                (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_TRANSFER_AMT) ||
                 transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH) ||
                 transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_LOAN) ||
                 transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_REDEMPTION)))
            {
                Event eventComponent = new EventComponent();
                eventComponent.resetNotificationAmountReceived(super.getClientTrx().getEDITTrxVO());
            }

            if (EDITTrx.TRANSACTIONTYPECT_MODALDEDUCTION.equalsIgnoreCase(transactionType))
            {
                FinancialHistoryVO financialHistoryVO = DAOFactory.getFinancialHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryVO[0].getEDITTrxHistoryPK())[0];

                EDITBigDecimal prevChargeDeductAmount = new EDITBigDecimal(financialHistoryVO.getPrevChargeDeductAmount());

                segmentVO.setChargeDeductAmount(prevChargeDeductAmount.getBigDecimal());
            }

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT))
            {
                segment.setSegmentStatusCT(Segment.SEGMENTSTATUSCT_SUBMIT_PEND);
                
                // make sure any pending trx on the contract are set to pendingStatus=D
                terminatePendingTrx(eventCRUD, editTrxVO.getOperator());
            }

            if (eventCRUD != null) {
            	eventCRUD.close();
            	eventCRUD = null;
            }

            // verify if segment status is changed....
            if (segment.isSegmentStatusChanged())
            {
                // generate change history record
                new ChangeProcessor().generateChangeHistoryForSegmentStatusChange(segmentVO, editTrxVO.getOperator(), editTrxVO.getEffectiveDate());

                //  Unterminate any necessary transactions and add them to the array of ClientStrategys to be picked up
                //  by the StrategyChain
                clientStrategys = unterminateTransactions(segment, new EDITTrx(editTrxVO));
            }

            //Clear out ConversionDate for the BillingChange transaction on reversal.  this 'BC' must have complexChangeType = 'CONV'
            if (EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE.equalsIgnoreCase(transactionType))
            {
                segment.setConversionDate(null);
            }

            if (contractCRUD == null) {
            	contractCRUD = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
            }

            contractCRUD.createOrUpdateVOInDB(segmentVO);

            Segment riderSegment = null;

            if (riderSegmentVOs != null)
            {
                for (int i = 0; i < riderSegmentVOs.length; i++)
                {
                    riderSegment = new Segment(riderSegmentVOs[i]);
                    if (riderSegment.isSegmentStatusChanged())
                    {
                        // generate change history record
                        new ChangeProcessor().generateChangeHistoryForSegmentStatusChange(riderSegmentVOs[i], editTrxVO.getOperator(), editTrxVO.getEffectiveDate());
                    }

                    contractCRUD.createOrUpdateVOInDB(riderSegmentVOs[i]);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
            
            throw new EDITEventException(e.getMessage());
        }
        finally
        {
            if (eventCRUD != null) eventCRUD.close();
            eventCRUD = null;

            if (contractCRUD != null) contractCRUD.close();
            contractCRUD = null;
        }

        return clientStrategys;
    }
    
    private void updateBillSchedule(SegmentVO segmentVO, EDITTrxVO editTrxVO, CRUD crud) throws EDITCRUDException {
    	
        BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(segmentVO.getBillScheduleFK());

        // Update using SessionHelper to ensure if multiple requiredPremiumAmount reversals are done as part of this strategy chain, updated value will be selected each time (additions will be cumulative)
        if (billSchedule != null) {
        	String billMethod = billSchedule.getBillMethodCT();
        	if (billMethod != null && !billMethod.equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL)) {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        		billSchedule.setRequiredPremiumAmount(billSchedule.getRequiredPremiumAmount().addEditBigDecimal(editTrxVO.getTrxAmount()));

				SessionHelper.save(billSchedule, SessionHelper.EDITSOLUTIONS);

				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        	}
        }
    }
    
    private void updateBillScheduleTermination(SegmentVO segmentVO, EDITTrxVO editTrxVO, CRUD crud) throws EDITCRUDException {
        BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(segmentVO.getBillScheduleFK());
        if (billSchedule != null) {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
    		billSchedule.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
			SessionHelper.save(billSchedule, SessionHelper.EDITSOLUTIONS);
			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
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
            String toFromStatus = investmentHistoriesWithFinalPrice[i].getToFromStatus();
            if (toFromStatus.equals("T"))
            {
                feeVO.setToFromInd("F");
            }
            else if (toFromStatus.equals("F"))
            {
                feeVO.setToFromInd("T");
            }
            feeVO.setReleaseInd("N");
            feeVO.setStatusCT("N");
            feeVO.setAccountingPendingStatus("Y");
            feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            feeVO.setOperator("System");

            crud.createOrUpdateVOInDB(feeVO);
        }

        crud.commitTransaction();
    }

    protected void updateEDITTrxVO(CRUD crud) throws Exception
    {
    	EDITDate currentDate = new EDITDate();

    	super.getClientTrx().getEDITTrxVO().setStatus("R");
    	super.getClientTrx().getEDITTrxVO().setMaintDateTime(new EDITDateTime().getFormattedDateTime());
    	super.getClientTrx().getEDITTrxVO().setAccountingPeriod(currentDate.getFormattedYearAndMonth());

    	super.getClientTrx().save();

    	if (!quoteInProcess)
    	{
    		//Death trx had other contract pending trxs, pendingStatus changed to "T" now change it back to "P"
    			reverseTerminatedTrxToPending(crud);
    		
    		//Now look for all trx that originated from the trx being reversed.
    		//All PeNDING trx spawned from this trx need to be deleted.
    		EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findSpawnedTrx(super.getClientTrx().getEDITTrxVO().getEDITTrxPK());

    		if (editTrxVO != null)
    		{
    			for (int i = 0; i < editTrxVO.length; i++)
    			{
    				if (editTrxVO[i].getPendingStatus().equalsIgnoreCase("P") ||
    						editTrxVO[i].getPendingStatus().equalsIgnoreCase("M"))
    				{
    					new ClientTrx(editTrxVO[i]).delete();
    				}
    			}
    		}
    		
    		EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(super.getClientTrx().getEDITTrxVO().getEDITTrxPK());
            if (editTrxCorrespondenceVOs != null && editTrxCorrespondenceVOs.length > 0)
            {
	    		for (EDITTrxCorrespondenceVO editTrxCorrespondenceVO : editTrxCorrespondenceVOs)
	             {
	             	if (editTrxCorrespondenceVO.getStatus().equalsIgnoreCase(EDITTrxCorrespondence.STATUS_PENDING))
	             	{
	             		editTrxCorrespondenceVO.setStatus(EDITTrxCorrespondence.STATUS_TERMINATED);
	             		crud.createOrUpdateVOInDB(editTrxCorrespondenceVO);
	             	}
	             }
	    	}
    	}
    }

    private void reverseTerminatedTrxToPending(CRUD crud)
    {
        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findTerminatedTrxForTransaction(segmentVO.getSegmentPK(), this.getClientTrx().getEDITTrxVO().getEDITTrxPK());

        EDITTrxVO editTrxVO = null; 
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = null;
        
        if (editTrxVOs != null)
        {
            for (int i = 0; i < editTrxVOs.length; i++)
            {
                editTrxVO = editTrxVOs[i];
                editTrxVO.setPendingStatus("P");
                editTrxVO.setTerminationTrxFK(0L);
                crud.createOrUpdateVOInDB(editTrxVO);
                
                editTrxCorrespondenceVOs = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());
                
                if (editTrxCorrespondenceVOs != null && editTrxCorrespondenceVOs.length > 0)
                {
	                for (EDITTrxCorrespondenceVO editTrxCorrespondenceVO : editTrxCorrespondenceVOs)
	                {
	                	if (editTrxCorrespondenceVO.getStatus().equalsIgnoreCase(EDITTrxCorrespondence.STATUS_TERMINATED))
	                	{
	                		editTrxCorrespondenceVO.setStatus(EDITTrxCorrespondence.STATUS_PENDING);
	                		crud.createOrUpdateVOInDB(editTrxCorrespondenceVO);
	                	}
	                }
                }
            }
        }
    }
    
    private void terminatePendingTrx(CRUD crud, String operator) throws Exception
    {
    	try {
	        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findAllPendingByContractNumber(segmentVO.getContractNumber());
	        EDITTrxVO editTrxVO = null; 
	        
	        if (editTrxVOs != null)
	        {
	            event.business.Event eventComponent = new event.component.EventComponent();

	            for (int i = 0; i < editTrxVOs.length; i++)
	            {
	                editTrxVO = editTrxVOs[i];

	            	List voInclusionList = new ArrayList();
	                voInclusionList.add(ClientSetupVO.class);
	                voInclusionList.add(ContractSetupVO.class);
	                voInclusionList.add(OutSuspenseVO.class);

	                EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);
	                composer.compose(editTrxVO);

	                ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
	                if (contractSetupVO.getOutSuspenseVOCount() > 0) {
	                	eventComponent.replaceSuspense(contractSetupVO.getOutSuspenseVO(), operator);
	                }
	                
	                editTrxVO.setPendingStatus("D");
	                editTrxVO.setOperator(operator);
	                crud.createOrUpdateVOInDB(editTrxVO);
	            }
	        }
    	} catch (Exception e) {
    		throw e;
    	}
    }

    public void updateEDITTrxHistoryVO(EDITTrxHistoryVO editTrxHistoryVO,
                                        String transferTypeCT,
                                        CRUD eventCRUD,
                                        CRUD contractCRUD,
                                        ContractSetupVO contractSetupVO) throws Exception
    {
        EDITDate currentDate = new EDITDate();

        GroupSetupVO[] groupSetupVO = DAOFactory.getGroupSetupDAO().findByEditTrxPK(super.getClientTrx().getEDITTrxVO().getEDITTrxPK());

        riderSegmentVOs = segmentVO.getSegmentVO();

        updateEDITTrxVO(eventCRUD);

        EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();
        String transactionType = editTrxVO.getTransactionTypeCT();

        BucketHistoryVO[] bucketHistoryVOs = DAOFactory.getBucketHistoryDAO().findByEditTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());

        if (bucketHistoryVOs != null)
        {
            // ascending
            bucketHistoryVOs = (BucketHistoryVO[]) Util.sortObjects(bucketHistoryVOs, new String[] {"getToFromStatus"});

            // descending because we need "T" status to process first then "F" status
            bucketHistoryVOs = (BucketHistoryVO[]) Util.invertObjects(bucketHistoryVOs);

            reverseBucketVOs(eventCRUD, bucketHistoryVOs, transactionType);
        }

        InvestmentHistoryVO[] investmentHistoryVOs = DAOFactory.getInvestmentHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());

        if (investmentHistoryVOs != null)
        {
            updateInvestmentHistoryVO(eventCRUD, investmentHistoryVOs);
        }

        // reset BillScheduleFK if we're reversing a billing change
        if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) && !(this instanceof Undo))
        {
	     	// get the billScheduleFK from the previous segmentHistory
	        long billScheduleFK = 0;
	        
	        EDITTrxVO previousTrx = getBillScheduleEditTrxHistory();
	        
	        if (previousTrx != null) {
	        	
	            EDITTrxHistoryVO[] previousTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findByEditTrxPK(previousTrx.getEDITTrxPK());
	        	
	            if (previousTrxHistoryVO != null) {
	            	SegmentHistoryVO[] previousSegmentHistoryVOs = DAOFactory.getSegmentHistoryDAO().
	            			findByEditTrxHistoryFKAndNonNullBillScheduleFK(previousTrxHistoryVO[0].getEDITTrxHistoryPK());
	            	
	            	if (previousSegmentHistoryVOs != null) {
	            		billScheduleFK = previousSegmentHistoryVOs[0].getBillScheduleFK();
	            		
	            		if (billScheduleFK > 0) {
	                    	segmentVO.setBillScheduleFK(billScheduleFK);
	                    }
	            	}
	            }
	        }
        }

        SegmentHistoryVO[] segmentHistoryVOs = DAOFactory.getSegmentHistoryDAO().findByEditTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
        if (segmentHistoryVOs != null)
        {
            for (int i = 0; i < segmentHistoryVOs.length; i++)
            {
                String prevSegmentStatus = segmentHistoryVOs[i].getPrevSegmentStatus();

                if (segmentHistoryVOs[i].getSegmentFK() == segmentVO.getSegmentPK())
                {
                    segmentVO.setLastAnniversaryDate(segmentHistoryVOs[i].getPrevLastAnniversaryDate());
                    segmentVO.setTerminationDate(segmentHistoryVOs[i].getPriorTerminationDate());
                    segmentVO.setAnnualPremium(segmentHistoryVOs[i].getPrevAnnualPremium());

                    if (segmentVO.getSegmentStatusCT().equalsIgnoreCase("Active") && prevSegmentStatus.equalsIgnoreCase("IssuedPendingReq")) {
                        segmentVO.setSegmentStatusCT("IssuePendingPremium");
                    } else if (segmentVO.getSegmentStatusCT().equalsIgnoreCase("Active") && prevSegmentStatus.equalsIgnoreCase("ActivePendingComm")) {
                        segmentVO.setSegmentStatusCT("Active");
                    } else {
                        segmentVO.setSegmentStatusCT(prevSegmentStatus);
                    }
                    
                    ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();
                    if (contractClientVOs != null && contractClientVOs.length > 0) {
                    	for (ContractClientVO contractClientVO : contractClientVOs) {
                    		contractClientVO.setClassCT(segmentHistoryVOs[i].getPriorRateClass());
                    		contractCRUD.createOrUpdateVOInDB(contractClientVO);
                    	}
                    }
                }

                if (riderSegmentVOs != null)
                {
                    for (int j = 0; j < riderSegmentVOs.length; j++)
                    {
                        if (segmentHistoryVOs[i].getSegmentFK() == riderSegmentVOs[j].getSegmentPK())
                        {
                            if (riderSegmentVOs[j].getSegmentStatusCT().equalsIgnoreCase("Active") && prevSegmentStatus.equalsIgnoreCase("IssuedPendingReq")) {
                                riderSegmentVOs[j].setSegmentStatusCT("IssuePendingPremium");
                            } else if (riderSegmentVOs[j].getSegmentStatusCT().equalsIgnoreCase("Active") && prevSegmentStatus.equalsIgnoreCase("ActivePendingComm")) {
                                riderSegmentVOs[j].setSegmentStatusCT("Active");
                            } else {
                                riderSegmentVOs[j].setSegmentStatusCT(prevSegmentStatus);
                            }
                            
                            riderSegmentVOs[j].setTerminationDate(segmentHistoryVOs[i].getPriorTerminationDate());
                        }
                    }
                }
            }
        }

        FinancialHistoryVO[] financialHistoryVO = DAOFactory.getFinancialHistoryDAO().findByEditTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());

        String contractNumber = segmentVO.getContractNumber();
        
        if (financialHistoryVO != null && financialHistoryVO.length > 0)
        {
            segmentVO.setCostBasis(financialHistoryVO[0].getPriorCostBasis());
            segmentVO.setRecoveredCostBasis(financialHistoryVO[0].getPriorRecoveredCostBasis());
            segmentVO.setSettlementAmount(financialHistoryVO[0].getPriorSettlementAmount());
            segmentVO.setLastSettlementValDate(financialHistoryVO[0].getPriorLastSettlementValDate());
            segmentVO.setTotalActiveBeneficiaries(financialHistoryVO[0].getPriorTotalActiveBeneficiaries());
            segmentVO.setRemainingBeneficiaries(financialHistoryVO[0].getPriorRemainingBeneficiaries());
            
            // make sure this works for IS trx!??
        	if (financialHistoryVO[0].getPrevTotalFaceAmount().compareTo(new BigDecimal(0)) == 1) {
        		segmentVO.setTotalFaceAmount(financialHistoryVO[0].getPrevTotalFaceAmount());
        	}

            if (transactionType.equalsIgnoreCase("PY") &&
                segmentVO.getRequiredMinDistributionVOCount() > 0)
            {
                RequiredMinDistributionVO rmdVO = segmentVO.getRequiredMinDistributionVO(0);
                rmdVO.setInitialCYAccumValue(financialHistoryVO[0].getPriorInitialCYAccumValue());
            }

            reverseLifeTableFields(transactionType, financialHistoryVO[0], contractSetupVO, contractCRUD, segmentHistoryVOs);

            String reversalReasonCode = editTrxVO.getReversalReasonCodeCT();

            if (reversalReasonCode == null || !reversalReasonCode.equalsIgnoreCase(EDITTrx.REVERSAL_REASON_NONSUFFICIENTFUNDS)) {
        	   if (editTrxVO.getStatus().equalsIgnoreCase("R") &&
	               (transactionType.equalsIgnoreCase("PY") ||
	                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
	                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_LOAN) ||
	                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH) ||
	                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_REDEMPTION) ||
	                (transactionType.equalsIgnoreCase("HFTA") && transferTypeCT.equalsIgnoreCase("HFReceipt")))) {
        		   
	                SuspenseVO suspenseVO = new SuspenseVO();
	                suspenseVO.setUserDefNumber(contractNumber);
	                suspenseVO.setOriginalContractNumber(contractNumber);
	                suspenseVO.setEffectiveDate(super.getClientTrx().getEDITTrxVO().getEffectiveDate());
	                suspenseVO.setMaintDateTime(editTrxVO.getMaintDateTime());
	                suspenseVO.setOperator(editTrxVO.getOperator());
	
	                EDITDate processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate();
	                suspenseVO.setProcessDate(processDate.getFormattedDate());
	
	                suspenseVO.setSuspenseType("Contract");
	                if (groupSetupVO[0].getPremiumTypeCT() != null) {
	                    suspenseVO.setPremiumTypeCT(groupSetupVO[0].getPremiumTypeCT());
	                }
	                
	                suspenseVO.setAccountingPendingInd("N");
	                suspenseVO.setStatus("N");
	                suspenseVO.setTaxYear(editTrxVO.getTaxYear());
	                suspenseVO.setContractPlacedFrom("Individual");
	                suspenseVO.setMaintenanceInd("M");
	                suspenseVO.setSuspenseAmount(financialHistoryVO[0].getGrossAmount());
	                suspenseVO.setOriginalAmount(financialHistoryVO[0].getGrossAmount());
	                suspenseVO.setDirectionCT("Apply");
	                financialHistoryVO[0].setDisbursementSourceCT("HoldInSuspense");
	
	                if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_LOAN) ||
	                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH) ||
	                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_REDEMPTION) ||
	                    (transactionType.equalsIgnoreCase("HFTA") && transferTypeCT.equalsIgnoreCase("HFReceipt")))
	                {
	                    long originatingTrxFK = editTrxVO.getOriginatingTrxFK();
	
	                    List voInclusionList = new ArrayList();
	                    voInclusionList.add(EDITTrxHistoryVO.class);
	                    voInclusionList.add(InvestmentHistoryVO.class);
	                    voInclusionList.add(InvestmentVO.class);
	
	                    EDITTrxVO originatingEDITTrxVO = new EDITTrxComposer(voInclusionList).compose(originatingTrxFK);
	                    long filteredFundFK = getFilteredFundFKOfFromInvestment(originatingEDITTrxVO);
	                    suspenseVO.setFilteredFundFK(filteredFundFK);
	
	                    long chargeCodeFK = getChargeCodeFKOfFromInvestment(originatingEDITTrxVO);
	
	                    EDITDate dfcashEffectiveDate = new EDITDate(super.getClientTrx().getEDITTrxVO().getEffectiveDate());
	
	                    EDITBigDecimal dfcashTrxAmount = new EDITBigDecimal(financialHistoryVO[0].getGrossAmount());
	
	                    new EDITTrx(originatingTrxFK, editTrxVO.getEDITTrxPK(), this.getClientTrx().getOperator()).createDFCASHFeeTrx(filteredFundFK, dfcashTrxAmount, dfcashEffectiveDate, chargeCodeFK, true);
	                }
	
					Company company = Company.findByProductStructurePK(new Long(segmentVO.getProductStructureFK()));
	                suspenseVO.setCompanyFK(company.getCompanyPK().longValue());
	
	                suspenseFK = saveSuspenseVO(eventCRUD, suspenseVO);
	
	                InSuspenseVO inSuspenseVO = new InSuspenseVO();
	                inSuspenseVO.setAmount(financialHistoryVO[0].getGrossAmount());
	                inSuspenseVO.setEDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
	                inSuspenseVO.setSuspenseFK(suspenseVO.getSuspensePK());
	
	                saveInSuspenseVO(eventCRUD, inSuspenseVO);
	
	                if (transactionType.equalsIgnoreCase("PY"))
	                {
	                    updateDepositRecords(editTrxVO.getEDITTrxPK(), groupSetupVO[0].getPremiumTypeCT(), suspenseFK, contractCRUD);
	                }
        	   	}
            }

            if (editTrxVO.getStatus().equalsIgnoreCase("R"))
            {
                checkForSuspenseDeletion(editTrxHistoryVO, transactionType, eventCRUD);
            }

            String accountPendingStatus = editTrxHistoryVO.getAccountingPendingStatus();

            if (accountPendingStatus.equals("Y"))
            {
                editTrxHistoryVO.setAccountingPendingStatus("N");
            }
            else if (accountPendingStatus.equals("N") &&
            		!(editTrxVO.getNoAccountingInd().equals("Y") ||
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
        	        	transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT)
            		)) {
            	
                editTrxHistoryVO.setAccountingPendingStatus("Y");
            }

            editTrxHistoryVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
            editTrxHistoryVO.setCycleDate(currentDate.getFormattedDate());

            if (super.getClientTrx().getExecutionMode() == ClientTrx.REALTIME_MODE)
            {
                editTrxHistoryVO.setRealTimeInd("Y");
            }
            else
            {
                editTrxHistoryVO.setCycleDate(super.getClientTrx().getCycleDate());
                editTrxHistoryVO.setRealTimeInd("N");
            }

            eventCRUD.createOrUpdateVOInDB(editTrxHistoryVO);

            if (transactionType.equalsIgnoreCase("PY") ||
                transactionType.equalsIgnoreCase("TF") ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER) ||
                transactionType.equalsIgnoreCase("TU") ||
                transactionType.equalsIgnoreCase("FT") ||
                transactionType.equalsIgnoreCase("HFTA") ||
                transactionType.equalsIgnoreCase("HFTP") ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_AMT) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_SERIES_PCT))
            {
                updateInvestment(editTrxHistoryVO);
            }


            if (!quoteInProcess) {
	            if(transactionType.equalsIgnoreCase("PY") ||
	               transactionType.equalsIgnoreCase("CY")) {
	                deleteSubBucketEntries(editTrxVO.getEDITTrxPK());
	            }
            }

//            if (transactionType.equals("PY") || // PremiumTrx
//                transactionType.equals("BI") || // BillTrx
//                transactionType.equals("LP") || // LapseTrx
//                transactionType.equals("LA"))   // LapsePendingTrx
//            {
//                updateBillLapse(financialHistoryVO[0]);
//            }
        }
    }

    public EDITTrxVO getBillScheduleEditTrxHistory()
    {
    	
    	TransactionPriorityCache transactionPriorityCache = TransactionPriorityCache.getInstance();

        int priority = transactionPriorityCache.getPriority(this.getClientTrx().getEDITTrxVO().getTransactionTypeCT());
    	
        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findPreviousActiveTrx(segmentVO.getSegmentPK(), this.getClientTrx().getEDITTrxVO(), priority);
        EDITTrxVO previousTrx = null;
        
        if (editTrxVOs != null) {
        	previousTrx = editTrxVOs[0];
        }
        
        return previousTrx;
    }

    private void createSuspense()
    {

    }
    
    /**
     * Engages special premium-due handling for reversal of lapse transactions,
     * possibly activating multiple PremiumDue records.
     * @param editTrxFK The primary key of the lapse transaction being reversed
     * @param trxDate The date on which the reversed transaction was originally applied
     * @throws EDITEventException
     */
    public void updatePremiumDueForLapse(long editTrxFK, EDITDate trxDate) throws EDITEventException
    {
        List<PremiumDue> premiumDues = PremiumDue.findAllByEDITTrxFK(new Long(editTrxFK));
        if (premiumDues != null)
        {
            try
            {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

                // first reverse all transactions
                Long segmentPK = null; // all PremiumDues have same SegmentPk
                for(PremiumDue premDue : premiumDues) 
                {
                	premDue.setPendingExtractIndicator("R");
                	segmentPK = premDue.getSegmentFK();
                	SessionHelper.saveOrUpdate(premDue, SessionHelper.EDITSOLUTIONS);
                }
                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                
                // activate the most recently occurring PremiumDue before the transaction date
                // as well as all PremiumDues after the transaction date, ignoring reversals
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                EDITMap params = new EDITMap();

                String hql = " select premiumDue from PremiumDue premiumDue" +
                      " where premiumDue.SegmentFK = :segmentPK" +
                      " and (premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
                      " where premiumDue2.SegmentFK = :segmentPK" +
                      " and premiumDue2.PendingExtractIndicator <> 'R'" +
                      " and premiumDue2.PendingExtractIndicator <> 'T'" +
                      " and premiumDue2.EffectiveDate <= :trxDate) or " +
                      " premiumDue.EffectiveDate > :trxDate)" +
                      " and premiumDue.PendingExtractIndicator <> 'R'" +
                      " and premiumDue.PendingExtractIndicator <> 'T'" +
                      " order by premiumDue.PremiumDuePK desc";

                params.put("segmentPK", segmentPK);
                params.put("trxDate", trxDate);

                List<PremiumDue> pdsToReset = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

                Set<String> sawDates = new HashSet<String>();
                for(PremiumDue pdToReset : pdsToReset) {
                	// keep only the first PremiumDue for a given effective date,
                	// as subsequent records are necessarily older (ordering by PremiumDuePK)
                	if(sawDates.contains(pdToReset.getEffectiveDate().toString()))
                		continue;
                	
                	sawDates.add(pdToReset.getEffectiveDate().toString());
                    if (pdToReset != null)
                    {
                        if (pdToReset.getPendingExtractIndicator().equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_E))
                        {
                            pdToReset.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_U);
                        }
                        else
                        {
                            pdToReset.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_P);
                        }
                        SessionHelper.saveOrUpdate(pdToReset, SessionHelper.EDITSOLUTIONS);
                    }
                }

                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
            catch (Exception e)
            {
                SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

                throw new EDITEventException(e.getMessage());
            }
        }
    }
    
    public void updatePremiumDue(EDITTrxVO editTrxVO, long segmentPK) throws EDITEventException
    {

        List<PremiumDue> premiumDues = null;

    	if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT)) {
    		// get ALL premium dues to set them all to R
            premiumDues = PremiumDue.findAllBySegmentFK(segmentPK);
    	} else {
    		// get premium dues associated with the trx
            premiumDues = PremiumDue.findAllByEDITTrxFK(new Long(editTrxVO.getEDITTrxPK()));
    	}
    	
        if (premiumDues != null && premiumDues.size() > 0)        	
        {
            try
            {
                List<Long> premiumDuePKs = new ArrayList<>();
				
				for(PremiumDue premDue : premiumDues) {
					SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
				
					premDue.setPendingExtractIndicator("R");
					premiumDuePKs.add(premDue.getPremiumDuePK());
					if (premDue.getEDITTrx() != null && premDue.getEDITTrx().getEDITTrxPK() != null && 
							premDue.getEDITTrx().getEDITTrxPK().equals(editTrxVO.getEDITTrxPK())) {
						premDue.getEDITTrx().setStatus(editTrxVO.getStatus());
						premDue.getEDITTrx().setMaintDateTime(new EDITDateTime(editTrxVO.getMaintDateTime()));
						premDue.getEDITTrx().setAccountingPeriod(editTrxVO.getAccountingPeriod());
					}
				
					SessionHelper.saveOrUpdate(premDue, SessionHelper.EDITSOLUTIONS);
				    SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
				}

            	/*if (!editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT)) {

            		PremiumDue pdToReset = PremiumDue.findBySegmentPK_PremiumDueToReset(segmentPK, premiumDuePKs, new EDITDate(editTrxVO.getEffectiveDate()));

                    if (pdToReset != null) {
                    	
                    	SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                	
                        if (pdToReset.getPendingExtractIndicator().equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_E)) {
                            pdToReset.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_U);
                        } else {
                            pdToReset.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_P);
                        }

                        SessionHelper.saveOrUpdate(pdToReset, SessionHelper.EDITSOLUTIONS);
                    	SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                    }
            	}*/

            }
            catch (Exception e)
            {
                SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

                throw new EDITEventException(e.getMessage());
            }
        }
    }

    /**
     * Will delete suspense record(s) for removal transactions only if trx is still accounting pending
     * @param editTrxHistoryVO
     * @param transactionType
     * @throws EDITDeleteException 
     */
    private void checkForSuspenseDeletion(EDITTrxHistoryVO editTrxHistoryVO, String transactionType, CRUD eventCRUD) throws EDITDeleteException
    {
        if ((transactionType.equalsIgnoreCase("DE") ||
             transactionType.equalsIgnoreCase("FS") ||
             transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) ||
             transactionType.equalsIgnoreCase("LS") ||
             transactionType.equalsIgnoreCase("NT") ||
             transactionType.equalsIgnoreCase("PO") ||
             transactionType.equalsIgnoreCase("RW") ||
             transactionType.equalsIgnoreCase("SW") ||
             transactionType.equalsIgnoreCase("WI")) &&
             editTrxHistoryVO.getAccountingPendingStatus().equalsIgnoreCase("Y"))
        {
            event.business.Event eventComponent = new event.component.EventComponent();

            List voInclusionList = new ArrayList();
            voInclusionList.add(InSuspenseVO.class);
            voInclusionList.add(SuspenseVO.class);

            editTrxHistoryVO = eventComponent.composeEDITTrxHistoryVOByPK(editTrxHistoryVO.getEDITTrxHistoryPK(), voInclusionList);

            if (editTrxHistoryVO.getInSuspenseVOCount() > 0)
            {
                InSuspenseVO[] inSuspenseVOs = editTrxHistoryVO.getInSuspenseVO();
                for (int i = 0; i < inSuspenseVOs.length; i++)
                {
                    SuspenseVO suspenseVO = (SuspenseVO) inSuspenseVOs[i].getParentVO(SuspenseVO.class);

                    eventCRUD.deleteVOFromDBRecursively(SuspenseVO.class, suspenseVO.getSuspensePK());
                }
            }
        }
    }

    /**
     * Set the investment pointing back to the original charrge code if there was one.
     * @param editTrxHistoryVO
     * @throws Exception
     */
    private void updateInvestment(EDITTrxHistoryVO editTrxHistoryVO)
            throws Exception
    {
        //InvestmentVO[] investmentVOs = segmentVO[0].getInvestmentVO();
        //Note - Commented because this will not necessarily get all investments.   
        long segmentPK = segmentVO.getSegmentPK();
        InvestmentVO[] investmentVOs = contract.dm.dao.DAOFactory.getInvestmentDAO().
                            findBySegmentPK(segmentPK, false, new ArrayList());

        if (investmentVOs == null)
            return;

        long editTrxHistoryPK = editTrxHistoryVO.getEDITTrxHistoryPK();

        if (editTrxHistoryPK == 0L)
            return;

        for (int i = 0; i < investmentVOs.length; i++)
        {
            long investmentPK = investmentVOs[i].getInvestmentPK();

            if (investmentPK == 0L)
                continue;

            // get the investment histories for investmentPK and editTrxHistoryPK
            event.business.Event event = new event.component.EventComponent();

            InvestmentHistoryVO[] investmentHistories =
                    event.findInvestmentHistoryByInvestmentAndEditTrxHistory(investmentPK, editTrxHistoryPK);

            if (investmentHistories != null)
            {
                //  Question: there can be multiple investmentHistories for each investment.
                //  Which one to use the previousChargeCodeFK from?  Answer from Rob is
                //  the last one.  Find the most recent one.
                int latestInvestmentHistoryIx = -1;
                long latestInvestmentHistoryPK = 0L;
                for (int j = 0; j < investmentHistories.length; j++)
                {
                    InvestmentHistoryVO investmentHistory = investmentHistories[j];
                    long ihPKTemp =  investmentHistory.getInvestmentHistoryPK();
                    if (ihPKTemp > latestInvestmentHistoryPK)
                    {
                        latestInvestmentHistoryPK = ihPKTemp;
                        latestInvestmentHistoryIx = j;
                    }
                }
                // here is the most recent one
                long previousChargeCodeFK =
                        investmentHistories[latestInvestmentHistoryIx].
                            getPreviousChargeCodeFK();

                if (investmentVOs[i].getChargeCodeFK() != previousChargeCodeFK)
                {
                    investmentVOs[i].setChargeCodeFK(previousChargeCodeFK);
                    updateInvestmentInDB(investmentVOs[i]);
                }
            }
        }
    }

    /**
     * Plain database update without touching investment allocations
     * or anything else.
     * @param investmentVO
     */
    private void updateInvestmentInDB(InvestmentVO investmentVO)
    {

        CRUD crud = null;
        try
        {
            //Investment investment = new Investment(investmentVO);
            //investment.save();
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
            crud.createOrUpdateVOInDB(investmentVO);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw new RuntimeException("Reversal: problem saving Investment to database", e);
        }
        finally
        {
            if (crud != null)
                crud.close();
            crud = null;
        }
    }

    private void deleteSubBucketEntries(long editTrxPKey) throws Exception
    {
        AnnualizedSubBucket[] annualizedSubBuckets = AnnualizedSubBucket.findByEDITTrxFK(editTrxPKey);
        if(annualizedSubBuckets != null)
        {
            for ( int i = 0; i < annualizedSubBuckets.length; i++)
            {
                 annualizedSubBuckets[i].delete();
            }
        }
    }

    private long saveSuspenseVO(CRUD crud, SuspenseVO suspenseVO) throws Exception
    {
        return crud.createOrUpdateVOInDB(suspenseVO);
    }

    private void saveInSuspenseVO(CRUD crud, InSuspenseVO inSuspenseVO) throws Exception
    {
        crud.createOrUpdateVOInDB(inSuspenseVO);
    }

    private void updateDepositRecords(long editTrxPK, String premiumType, long suspenseFK, CRUD contractCRUD) throws Exception
    {
        DepositsVO[] depositsVOs = new VOComposer().composeDepositsByEDITTrxPK(editTrxPK);
        if (depositsVOs != null)
        {
            for (int i = 0; i < depositsVOs.length; i++)
            {
                if (premiumType != null && premiumType.equalsIgnoreCase("Issue"))
                {
                    depositsVOs[i].setAnticipatedAmount(depositsVOs[i].getAmountReceived());
                    depositsVOs[i].setSuspenseFK(suspenseFK);
                }
                else
                {
                    depositsVOs[i].setAmountReceived(new EDITBigDecimal().getBigDecimal());
                    depositsVOs[i].setDateReceived(null);
                    depositsVOs[i].setSuspenseFK(0);
                }

                depositsVOs[i].setEDITTrxFK(0);

                contractCRUD.createOrUpdateVOInDB(depositsVOs[i]);
            }
        }
    }
    
    private void reverseBucketVOs(CRUD crud, BucketHistoryVO[] bucketHistoryVOs, String transactionType) throws Exception {
    	
    	if (bucketHistoryVOs != null) {
            for (int i = 0; i < bucketHistoryVOs.length; i++) {
            	
                BucketVO[] bucketVOs = contract.dm.dao.DAOFactory.getBucketDAO().findByBucketPK(bucketHistoryVOs[i].getBucketFK(), false, new ArrayList());

                if (bucketVOs != null && bucketVOs.length > 0) {
                	
                	BucketVO bucketVO = bucketVOs[0];
                	bucketVO.setLastValuationDate(bucketHistoryVOs[i].getPreviousValuationDate());
                	bucketVO.setCumDollars(bucketHistoryVOs[i].getPreviousValue());
                	bucketVO.setGuarCumValue(bucketHistoryVOs[i].getPreviousGuaranteeValue());
                	bucketVO.setRebalanceAmount(bucketHistoryVOs[i].getPriorRebalanceAmount());
                	bucketVO.setPreviousLoanInterestRate(bucketHistoryVOs[i].getPreviousPriorBucketRate());
                	bucketVO.setPriorBucketRate(bucketHistoryVOs[i].getPreviousPriorBucketRate());
                	bucketVO.setLastRenewalDate(bucketHistoryVOs[i].getPreviousLastRenewalDate());
                	bucketVO.setLoanInterestRate(bucketHistoryVOs[i].getPreviousBucketRate());
                	bucketVO.setBucketInterestRate(bucketHistoryVOs[i].getPreviousBucketRate());
                	bucketVO.setLoanCumDollars(bucketHistoryVOs[i].getPreviousLoanCumDollars());
                	bucketVO.setLoanPrincipalRemaining(bucketHistoryVOs[i].getPreviousLoanPrincipalRemaining());
                	bucketVO.setAccruedLoanInterest(bucketHistoryVOs[i].getPreviousLoanInterestDollars());
                	bucketVO.setUnearnedLoanInterest(bucketHistoryVOs[i].getPrevUnearnedLoanInterest());
                	bucketVO.setInterestPaidThroughDate(bucketHistoryVOs[i].getPrevInterestPaidThroughDate());

                    crud.createOrUpdateVOInDB(bucketVO);
                }
            }
        }
    }

    private void updateBucketHistoryVO(CRUD crud, BucketHistoryVO[] bucketHistoryVOs, String transactionType) throws Exception
    {
        if (bucketHistoryVOs != null)
        {
            for (int i = 0; i < bucketHistoryVOs.length; i++)
            {
                String fundType = getFundTypeForBucket(bucketHistoryVOs[i].getBucketFK());

                if (fundType.equals("Fixed") || fundType.equals("MVA") || fundType.equals("EquityIndex"))
                {
                    bucketHistoryVOs[i].setCumDollars( new EDITBigDecimal(bucketHistoryVOs[i].getPreviousValue() + "").getBigDecimal() );
                }
                else if (fundType.equals("Variable") || fundType.equals("Hedge"))
                {
                    bucketHistoryVOs[i].setCumUnits( new EDITBigDecimal(bucketHistoryVOs[i].getPreviousValue() + "").getBigDecimal() );
                }

                String toFromStatus = bucketHistoryVOs[i].getToFromStatus();

                if (toFromStatus.equals("T"))
                {
                    bucketHistoryVOs[i].setToFromStatus("F");
                }
                else if (toFromStatus.equals("F"))
                {
                    bucketHistoryVOs[i].setToFromStatus("T");
                }

                crud.createOrUpdateVOInDB(bucketHistoryVOs[i]);

                updateBucketVO(bucketHistoryVOs[i], fundType, transactionType, toFromStatus, crud);
            }
        }
    }

    public void updateBucketVO(BucketHistoryVO bucketHistoryVO, String fundType, String transactionType,
                               String toFromStatus, CRUD crud) throws Exception
    {
        BucketVO[] bucketVO = contract.dm.dao.DAOFactory.getBucketDAO().findByBucketPK(bucketHistoryVO.getBucketFK(), false, new ArrayList());

        if (fundType.equals("Fixed") || fundType.equals("MVA") || fundType.equals("EquityIndex"))
        {
            bucketVO[0].setCumDollars( new EDITBigDecimal(bucketHistoryVO.getPreviousValue() + "").getBigDecimal() );
            bucketVO[0].setGuarCumValue(bucketHistoryVO.getPreviousGuaranteeValue());
        }
        else if (fundType.equals("Variable") || fundType.equals("Hedge"))
        {
            bucketVO[0].setCumUnits( new EDITBigDecimal(bucketHistoryVO.getPreviousValue() + "").getBigDecimal() );
        }

        EDITBigDecimal dollars = new EDITBigDecimal( bucketHistoryVO.getDollars() );
        EDITBigDecimal bonusAmountFromHistory = new EDITBigDecimal( bucketHistoryVO.getBonusAmount() );

        //Only update the deposit amount for apply transactions - not removals
        if (!transactionType.equalsIgnoreCase("DE") &&
            !transactionType.equalsIgnoreCase("FP") &&
            !transactionType.equalsIgnoreCase("FS") &&
            !transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) &&
            !transactionType.equalsIgnoreCase("LS") &&
            !transactionType.equalsIgnoreCase("PO") &&
            !transactionType.equalsIgnoreCase("RE") &&
            !transactionType.equalsIgnoreCase("WI") &&
            !transactionType.equalsIgnoreCase("MR") &&
            !transactionType.equalsIgnoreCase("MD") &&
            !transactionType.equalsIgnoreCase("NT") &&
            !transactionType.equalsIgnoreCase("PE") &&
            !transactionType.equalsIgnoreCase("TU") &&
            !transactionType.equalsIgnoreCase("TF") &&
            !transactionType.equalsIgnoreCase("PR") &&
            !isLoanTransaction(transactionType)) // and not loan related transctions
        {
            if (!toFromStatus.equals("F"))
            {
                EDITBigDecimal depositAmount = new EDITBigDecimal( bucketVO[0].getDepositAmount() );
                depositAmount = depositAmount.subtractEditBigDecimal(dollars.subtractEditBigDecimal(bonusAmountFromHistory));
                bucketVO[0].setDepositAmount(depositAmount.getBigDecimal());
            }
        }
        // for LC transaction the following additional fields need to be reset.
        else if (isLoanTransaction(transactionType))
        {
            // BucketVO.CumDollars is reset from BucketHistoryVO.PreviousValue based on fund type above
            bucketVO[0].setAccruedLoanInterest(bucketHistoryVO.getPreviousLoanInterestDollars());
            bucketVO[0].setLoanPrincipalRemaining(bucketHistoryVO.getPreviousLoanPrincipalRemaining());
            bucketVO[0].setLoanCumDollars(bucketHistoryVO.getPreviousLoanCumDollars());
            bucketVO[0].setUnearnedLoanInterest(bucketHistoryVO.getPrevUnearnedLoanInterest());
            bucketVO[0].setInterestPaidThroughDate(bucketHistoryVO.getPrevInterestPaidThroughDate());
        }

        EDITBigDecimal bonusAmount = new EDITBigDecimal( bucketVO[0].getBonusAmount() );
        bonusAmount = bonusAmount.subtractEditBigDecimal(bonusAmountFromHistory);
        bucketVO[0].setBonusAmount( bonusAmount.getBigDecimal() );

        bucketVO[0].setRebalanceAmount(bucketHistoryVO.getPriorRebalanceAmount());

        bucketVO[0].setLastValuationDate(bucketHistoryVO.getPreviousValuationDate());

        if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_POLICYYEAREND) || transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_RENEWAL) ||
            transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM))
        {
            EDITBigDecimal previousPriorBucketRate = new EDITBigDecimal(bucketHistoryVO.getPreviousPriorBucketRate());
            if (previousPriorBucketRate != null && !previousPriorBucketRate.isEQ("0"))
            {
                bucketVO[0].setPriorBucketRate(previousPriorBucketRate.getBigDecimal());
            }
            else
            {
                //Log messages if the previous field has not been updated 
                String message = "PriorBucketRate not update - PreviousPriorBucketRate = 0";
                logWarning(message);
            }

            EDITBigDecimal previousBucketRate = new EDITBigDecimal(bucketHistoryVO.getPreviousBucketRate());
            if (previousBucketRate != null && !previousBucketRate.isEQ("0"))
            {
                if (fundType.equals("EquityIndex"))
                {
                    bucketVO[0].setIndexCapRate(previousBucketRate.getBigDecimal());
                }
                else
                {
                    bucketVO[0].setBucketInterestRate(previousBucketRate.getBigDecimal());
                }
            }
            else
            {
                //Log messages if the previous fields have not been updated
                String message = null;
                if (fundType.equals("EquityIndex"))
                {
                    message = "IndexCapRate not updated- PreviousBucketRate = 0";
                }
                else
                {
                    message = "BucketInteresRate not update - PreviousBucketRate = 0";
                }

                logWarning(message);
            }

            if (!transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM))
            {
                if (fundType.equals("MVA") && transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_RENEWAL))
                {
                    bucketVO[0].setRenewalDate(bucketVO[0].getLastRenewalDate());
                    bucketVO[0].setLastRenewalDate(bucketHistoryVO.getPreviousLastRenewalDate());
                }
                else if (!fundType.equals("MVA") && transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_POLICYYEAREND))
                {
                    bucketVO[0].setRenewalDate(bucketVO[0].getLastRenewalDate());
                    bucketVO[0].setLastRenewalDate(bucketHistoryVO.getPreviousLastRenewalDate());
                }
            }
        }

        crud.createOrUpdateVOInDB(bucketVO[0]);
    }

    private void updateInvestmentHistoryVO(CRUD crud, InvestmentHistoryVO[] investmentHistoryVOs)
    {
        for (int i = 0; i < investmentHistoryVOs.length; i++)
        {
            InvestmentHistoryVO investmentHistoryVO = investmentHistoryVOs[i];

            String toFromStatus = investmentHistoryVO.getToFromStatus();

            if (toFromStatus != null)
            {
                if (toFromStatus.equals("T"))
                {
                    investmentHistoryVO.setToFromStatus("F");
                }
                else if (toFromStatus.equals("F"))
                {
                    investmentHistoryVO.setToFromStatus("T");
                }
            }

            crud.createOrUpdateVOInDB(investmentHistoryVO);
        }
    }

    private boolean isLoanTransaction(String transactionType)
    {
        boolean isLoanTransaction = false;

        if (transactionType.equals(EDITTrx.TRANSACTIONTYPECT_LOAN) || transactionType.equals(EDITTrx.TRANSACTIONTYPECT_LOAN_CAPITALIZATION) ||
            transactionType.equals(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) || transactionType.equals(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION) ||
            transactionType.equals(EDITTrx.TRANSACTIONTYPECT_MONTHLYFEE) || transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_LOAN) ||
            transactionType.equals(EDITTrx.TRANSACTIONTYPECT_CLAIM_PAYOUT) || transactionType.equals(EDITTrx.TRANSACTIONTYPECT_PREMIUM_LOAN) ||
            transactionType.equals(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER))
        {
            isLoanTransaction = true;
        }

        return isLoanTransaction;
    }

    public String getFundTypeForBucket(long bucketPK) throws Exception
    {
        long filteredFundFK = new FastDAO().findFilteredFundPKByBucketPK(bucketPK);

        return getFundTypeForFilteredFund(filteredFundFK);
    }

    private String getFundTypeForFilteredFund(long filteredFundFK) throws Exception
    {
        String fundType = (String) fundTypeCache.get(new Long(filteredFundFK));

        if (fundType == null)
        {
            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            FundVO[] fundVO = engineLookup.getFundByFilteredFundFK(filteredFundFK, false, new ArrayList());

            fundType = fundVO[0].getFundType();

            fundTypeCache.put(new Long(filteredFundFK), fundType);
        }

        return fundType;
    }

    private void createAndSaveReversalCommissionHistories(CommissionHistoryVO[] commissionHistoryVOs, ContractSetupVO contractSetupVO, String status, String transactionTypeCT, Segment segment, CRUD crud) throws Exception
    {
        boolean historyStatus;
        for (int i = 0; i < commissionHistoryVOs.length; i++)
        {
            historyStatus = false;
            String accountingPendingStatus = commissionHistoryVOs[i].getAccountingPendingStatus();

            if (accountingPendingStatus == null || accountingPendingStatus.equalsIgnoreCase("Y"))
            {
                commissionHistoryVOs[i].setAccountingPendingStatus("N");
                crud.createOrUpdateVOInDB(commissionHistoryVOs[i]);
                historyStatus = true;
            }

            // Do not create ChargeBack records for held CommissionHistory records i.e. with UpdateStatus = 'L' or 'D'
            // At this point of time Premium transaction will have commission hold records.
            // Update all commission history records hanging of this transaction only.
            if (isCommissionHeldRecord(commissionHistoryVOs[i].getUpdateStatus()))
            {
                if (CommissionHistory.UPDATESTATUS_L.equals(commissionHistoryVOs[i].getUpdateStatus()))
                {
                    commissionHistoryVOs[i].setUpdateStatus(CommissionHistory.UPDATESTATUS_D);
                }
                else if (CommissionHistory.UPDATESTATUS_D.equals(commissionHistoryVOs[i].getUpdateStatus()))
                {
                    commissionHistoryVOs[i].setUpdateStatus(CommissionHistory.UPDATESTATUS_L);
                }
                if ("Y".equals(commissionHistoryVOs[i].getAccountingPendingStatus()))
                {
                    commissionHistoryVOs[i].setAccountingPendingStatus("N");
                }
                else if ("N".equals(commissionHistoryVOs[i].getAccountingPendingStatus()))
                {
                    commissionHistoryVOs[i].setAccountingPendingStatus("Y");
                }
            }
            else
            {
            commissionHistoryVOs[i].setCommissionHistoryPK(0);
            commissionHistoryVOs[i].setUpdateStatus("U");
//            commissionHistoryVOs[i].setUpdateDateTime(EDITDateTime.getCurrentDateTime());

                // status = edittrx status
            if (status.equals("U"))
            {
                if (includeUndoTransactionsInCommissionStatement())
                {
                    commissionHistoryVOs[i].setStatementInd("Y");
                }
                else
                {
                    commissionHistoryVOs[i].setStatementInd("N");
                }
            }
            else
            {
                commissionHistoryVOs[i].setStatementInd("Y");
            }

            String commissionType = commissionHistoryVOs[i].getCommissionTypeCT();

                if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK_REVERSAL);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RENEWAL))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_RNWL_NEG_EARN);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RNWL_NEG_EARN))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_RENEWAL);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK);
                }
                else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK);
                }
                else
                {
                    commissionHistoryVOs[i].setCommissionTypeCT(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK);
                }

                commissionType = commissionHistoryVOs[i].getCommissionTypeCT();

                if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK) ||
                    commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK) ||
                    commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL) ||
                    commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK) ||
                    commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    EDITBigDecimal commAmount = new EDITBigDecimal(commissionHistoryVOs[i].getCommissionAmount());

                    boolean isNewBusinessTrx = false;
                    if (contractSetupVO != null) {
	                    String complexChangeType = contractSetupVO.getComplexChangeTypeCT();
	                    
	                    if (complexChangeType != null && (
	                		complexChangeType.equalsIgnoreCase("Withdrawn") || 
	                		complexChangeType.equalsIgnoreCase("Postponed") ||
	                		complexChangeType.equalsIgnoreCase("Incomplete") ||
	                		complexChangeType.equalsIgnoreCase("DeclineReq") ||
	                		complexChangeType.equalsIgnoreCase("DeclineElig") ||
	                		complexChangeType.equalsIgnoreCase("DeclinedMed"))) {
	                    	
	                    	isNewBusinessTrx = true;
	                    }
                    }

                    SegmentVO segmentVO = (SegmentVO) segment.getVO();
                    AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
                    for (int j = 0; j < agentHierarchyVOs.length; j++)
                    {
                        if (agentHierarchyVOs[j].getAgentSnapshotVOCount() > 0)
                        {
                            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVOs[j].getAgentSnapshotVO();

                            for (int k = 0; k < agentSnapshotVOs.length; k++)
                            {
                                if (agentSnapshotVOs[k].getAgentSnapshotPK() == commissionHistoryVOs[i].getAgentSnapshotFK())
                                {
                                    if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK) ||
                                        commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
                                    {
                                        agentSnapshotVOs[k].setAdvanceAmount(new EDITBigDecimal(agentSnapshotVOs[k].getAdvanceAmount()).subtractEditBigDecimal(commAmount).getBigDecimal());
                                    }
                                    else if (commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL) ||
                                             commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
                                    {
                                    	if (!transactionTypeCT.equalsIgnoreCase("LA") &&
                                    		!transactionTypeCT.equalsIgnoreCase("FS") &&
                                    		!transactionTypeCT.equalsIgnoreCase("DE") &&
                                    		!transactionTypeCT.equalsIgnoreCase("NT") &&
                                    		(!transactionTypeCT.equalsIgnoreCase("BC") || (transactionTypeCT.equalsIgnoreCase("BC") && !isNewBusinessTrx)))
                                    	{
                                    		agentSnapshotVOs[k].setAdvanceAmount(new EDITBigDecimal(agentSnapshotVOs[k].getAdvanceAmount()).addEditBigDecimal(commAmount).getBigDecimal());
                                    	}
                                    }
                                    else
                                    {
                                        agentSnapshotVOs[k].setAdvanceRecovery(new EDITBigDecimal(agentSnapshotVOs[k].getAdvanceRecovery()).subtractEditBigDecimal(commAmount).getBigDecimal());
                                    }

                                    crud.createOrUpdateVOInDB(agentSnapshotVOs[k]);
                                }
                            }
                        }
                    }
            }

            if (!historyStatus)
            {
                commissionHistoryVOs[i].setAccountingPendingStatus("Y");
            }

                EDITBigDecimal debitBalanceAmount = new EDITBigDecimal(commissionHistoryVOs[i].getDebitBalanceAmount());
                if (!debitBalanceAmount.isEQ("0"))
                {
                    EDITBigDecimal commissionAmount = new EDITBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    commissionAmount = commissionAmount.addEditBigDecimal(debitBalanceAmount);
                    commissionHistoryVOs[i].setCommissionAmount(commissionAmount.getBigDecimal());
                    commissionHistoryVOs[i].setDebitBalanceAmount(new EDITBigDecimal().getBigDecimal());
                }

            commissionHistoryVOs[i].setUndoRedoStatus("undo");
            }
            
            
            String commissionType = commissionHistoryVOs[i].getCommissionTypeCT();
            if (!quoteInProcess && !commissionType.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK))
            {
            	crud.createOrUpdateVOInDB(commissionHistoryVOs[i]);
            }
    }

        // Additional process required for Not_Taken and premium reversal transactions.
        // For premium reversals Set UpdateStatus = 'D' (Do not physically delete the record, just mark it uniquely).
        // At this point of time, only premium transactions will have CommissionHistory records with UpdateStatus = 'L'
        // Though premium transactions will have CommissionHistory records with UpdateStatus = 'D'
        // we need not check for that condition because a reversed transaction can not be reversed again.
        // A premium reversed means  we no longer need to keep track of commissions that are put on hold.
        // That is the reason we are marking the records uniquely with 'D'.
        // For Not_Taken reversals Query for all CommissionHistory records that are changed to UpdateStatus ='D'
        // in the Not_Taken natural transaction execution and set them back to UpdateStatus = 'L'
        if (transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_NOTTAKEN))
        {
            segment.updateCommissionHistoryRecords(crud, CommissionHistory.UPDATESTATUS_D);
        }
    }

    /**
     * Determies if CommissionHistory record is Commission hold record.
     * @param updateStatus
     * @return
     */
    private boolean isCommissionHeldRecord(String updateStatus)
    {
        boolean isHeldRecord = false;

        // At this point of time only premium transactions will have commission hold records.
        // In future in case if any other transactions have comiissionhistory hold record, I think the same rules apply.
        if (updateStatus.equals(CommissionHistory.UPDATESTATUS_L) || updateStatus.equals(CommissionHistory.UPDATESTATUS_D))
        {
            isHeldRecord = true;
        }

        return isHeldRecord;
    }

    /**
     * Update existing ReinsuranceHistory (accountingPendingStatus (if necessary) and create a new ReinsuranceHistory
     * record for the undo.
     * @param reinsuranceHistories
     * @param crud
     * @throws Exception
     */
    private void createAndSaveReversalReinsuranceHistories(ReinsuranceHistory[] reinsuranceHistories, CRUD crud) throws Exception
    {
        boolean historyStatus;
        for (int i = 0; i < reinsuranceHistories.length; i++)
        {
            ReinsuranceHistoryVO reinsuranceHistoryVO = (ReinsuranceHistoryVO) reinsuranceHistories[i].getVO();

            historyStatus = false;
            String accountingPendingStatus = reinsuranceHistoryVO.getAccountingPendingStatus();

            if (accountingPendingStatus == null || accountingPendingStatus.equalsIgnoreCase("Y"))
            {
                reinsuranceHistoryVO.setAccountingPendingStatus("N");
                crud.createOrUpdateVOInDB(reinsuranceHistoryVO);
                historyStatus = true;
            }

            reinsuranceHistoryVO.setReinsuranceHistoryPK(0);
            reinsuranceHistoryVO.setUpdateStatus("U");

            String reinsuranceType = reinsuranceHistoryVO.getReinsuranceTypeCT();

            if (reinsuranceType.equalsIgnoreCase("ChargeBack"))
            {
                reinsuranceHistoryVO.setReinsuranceTypeCT("ChargeBackRev");
            }
            else
            {
                reinsuranceHistoryVO.setReinsuranceTypeCT("ChargeBack");
            }

            if (!historyStatus)
            {
                reinsuranceHistoryVO.setAccountingPendingStatus("Y");
            }

            reinsuranceHistoryVO.setUndoRedoStatus("undo");

            if (!quoteInProcess)
            {
            crud.createOrUpdateVOInDB(reinsuranceHistoryVO);
        }
    }
    }

//   /**
//     * Bill (BI), Lapse (LA), LapsePending (LP) and Premium (PY) transactions must restore BillLapse information to its
//     * prior state.
//     * @param financialHistoryVO contains the previous BillLapse information to restore
//     */
//    private void updateBillLapse(FinancialHistoryVO financialHistoryVO) throws Exception
//    {
//        ClientTrx clientTrx = getClientTrx();
//
//        long clientSetupPK = clientTrx.getEDITTrxVO().getClientSetupFK();
//
//        ClientSetupVO clientSetupVO = DAOFactory.getClientSetupDAO().findByClientSetupPK(clientSetupPK)[0];
//
//        long contractClientPK = clientSetupVO.getContractClientFK();
//
//        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
//
//        BillLapseVO[] billLapseVO = contractLookup.composeBillLapseVOByContractClientPK(contractClientPK, null);
//
//        if (billLapseVO != null)
//        {
//            billLapseVO[0].setExtractDate(financialHistoryVO.getPriorExtractDate());
//
//            billLapseVO[0].setDueDate(financialHistoryVO.getPriorDueDate());
//
//            billLapseVO[0].setFixedAmount(financialHistoryVO.getPriorFixedAmount());
//
//            contract.business.Contract contractComponent = new contract.component.ContractComponent();
//
//            contractComponent.saveBillLapse(billLapseVO[0]);
//        }
//    }

    /**
     * Reverse Life table fields
     * @param transactionType
     * @param financialHistoryVO
     * @param contractSetupVO
     * @param crud
     */
    private void reverseLifeTableFields(String transactionType, 
                                        FinancialHistoryVO financialHistoryVO, 
                                        ContractSetupVO contractSetupVO, 
                                        CRUD crud,
                                        SegmentHistoryVO[] segmentHistoryVOs)
    {
        LifeVO[] lifeVOs = contract.dm.dao.DAOFactory.getLifeDAO().findLifeBySegmentFK(segmentVO.getSegmentPK(), false, null);

        LifeVO lifeVO = null;

        if (lifeVOs != null)
        {
            lifeVO = lifeVOs[0];

            if (lifeVO != null)
            {
                reverseGuidelineFields(transactionType, financialHistoryVO, crud, lifeVO, segmentHistoryVOs);
                
                String complexChangeType = null;
                if (contractSetupVO != null) {
                	complexChangeType = contractSetupVO.getComplexChangeTypeCT();
                }
                
                // this may be redundant??
                if (this instanceof Reversal && transactionType != null && transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) && 
                	complexChangeType != null && (complexChangeType.equalsIgnoreCase("BOTH") || complexChangeType.equalsIgnoreCase("CONV") ||
    				complexChangeType.equalsIgnoreCase("CONT"))) {

                	lifeVO.setLapsePendingDate(null);
                    lifeVO.setLapseDate(null);
                }
            }
        }

        if (transactionType.equalsIgnoreCase("CC"))
        {
            reverseComplexChangeType(lifeVO, contractSetupVO, financialHistoryVO, crud);
        }

        if (lifeVO !=null)
        {
            crud.createOrUpdateVOInDB(lifeVO);
        }
    }
    
   /**
     * Reset the guideline values on the Life table using the FinancialHistory table fields
     * @param trxCode
     * @param financialHistoryVO
     * @param crud
    *  @param lifeVO
     */
    private void reverseGuidelineFields(String trxCode,
                                        FinancialHistoryVO financialHistoryVO,
                                        CRUD crud,
                                        LifeVO lifeVO,
                                        SegmentHistoryVO[] segmentHistoryVOs)
    {
            
    	lifeVO.setGuidelineSinglePremium(financialHistoryVO.getPrevGuidelineSinglePremium());
        lifeVO.setGuidelineLevelPremium(financialHistoryVO.getPrevGuidelineLevelPremium());
        lifeVO.setCumGuidelineLevelPremium(financialHistoryVO.getPrevCumGLP());
        lifeVO.setTamra(financialHistoryVO.getPrevTamra());
        lifeVO.setTamraStartDate(financialHistoryVO.getPrevTamraStartDate());
        lifeVO.setMECGuidelineSinglePremium(financialHistoryVO.getPrevMECGuidelineSinglePrem());
        lifeVO.setMECGuidelineLevelPremium(financialHistoryVO.getPrevMECGuidelineLevelPrem());
        lifeVO.setMECStatusCT(financialHistoryVO.getPrevMECStatusCT());
        lifeVO.setMECDate(financialHistoryVO.getPrevMECDate());
        lifeVO.setLapsePendingDate(financialHistoryVO.getPriorLapsePendingDate());
        lifeVO.setLapseDate(financialHistoryVO.getPriorLapseDate());
        lifeVO.setDeathBenefitOptionCT(financialHistoryVO.getPriorDeathBenefitOption());
        lifeVO.setMAPEndDate(financialHistoryVO.getPrevMAPEndDate());
        lifeVO.setTamraInitAdjValue(financialHistoryVO.getPrevTamraInitAdjValue());
        lifeVO.setFaceAmount(financialHistoryVO.getPrevTotalFaceAmount());        
        if (segmentVO.getSegmentNameCT().equalsIgnoreCase("UL")) {
        	lifeVO.setCurrentDeathBenefit(financialHistoryVO.getPrevTotalFaceAmount());       
        }
        
    	if (segmentHistoryVOs != null)
        {
    		for (int i = 0; i < segmentHistoryVOs.length; i++)
    		{
    			if (segmentHistoryVOs[i].getSegmentFK() == lifeVO.getSegmentFK())
    			{
    				if (segmentHistoryVOs[i].getPriorPaidToDate() != null) {
                        lifeVO.setPaidToDate(segmentHistoryVOs[i].getPriorPaidToDate());
                    }
    				
                    lifeVO.setGuarPaidUpTerm(segmentHistoryVOs[i].getGuarPaidUpTerm());
                    lifeVO.setNonGuarPaidUpTerm(segmentHistoryVOs[i].getNonGuarPaidUpTerm());
                    
    				if (segmentHistoryVOs[i].getPrevFaceAmount().compareTo(new BigDecimal(0)) == 1)
    				{
    			        if (!segmentVO.getSegmentNameCT().equalsIgnoreCase("UL")) {
    			        	lifeVO.setCurrentDeathBenefit(segmentHistoryVOs[i].getPrevFaceAmount());
        					lifeVO.setFaceAmount(segmentHistoryVOs[i].getPrevFaceAmount());
    			        }	
    					
    					segmentVO.setAmount(segmentHistoryVOs[i].getPrevFaceAmount());

    					BigDecimal units = segmentHistoryVOs[i].getPrevFaceAmount().divide(new BigDecimal(1000));
    					segmentVO.setUnits(units);
    				}
    			}

    			if (riderSegmentVOs != null)
    			{
    				for (int j = 0; j < riderSegmentVOs.length; j++)
    				{
    					if (segmentHistoryVOs[i].getSegmentFK() == riderSegmentVOs[j].getSegmentPK())
    					{                                	
    						if (segmentHistoryVOs[i].getPrevFaceAmount().compareTo(new BigDecimal(0)) == 1)
    						{
    							riderSegmentVOs[j].setAmount(segmentHistoryVOs[i].getPrevFaceAmount());

    							BigDecimal units = segmentHistoryVOs[i].getPrevFaceAmount().divide(new BigDecimal(1000));
    							riderSegmentVOs[j].setUnits(units);
    						}
    						
    						LifeVO[] riderLifeVO = riderSegmentVOs[j].getLifeVO();

                            if (riderLifeVO != null && riderLifeVO.length > 0)
                            {
                                if (segmentHistoryVOs[i].getSegmentFK() == riderLifeVO[0].getSegmentFK())
                                {
                                	if (segmentHistoryVOs[i].getPrevFaceAmount().compareTo(new BigDecimal(0)) == 1) {
                                		riderLifeVO[0].setFaceAmount(segmentHistoryVOs[i].getPrevFaceAmount());
            						}
                                	
                                    riderLifeVO[0].setGuarPaidUpTerm(segmentHistoryVOs[i].getGuarPaidUpTerm());
            						riderLifeVO[0].setNonGuarPaidUpTerm(segmentHistoryVOs[i].getNonGuarPaidUpTerm());

                                    crud.createOrUpdateVOInDB(riderLifeVO[0]);
                                }
                            }
    					}
    				}
    			}
    		}
        }
        
        if (trxCode.equalsIgnoreCase("FS"))
        {
        	lifeVO.setPaidUpTermDate(null);
        }

        crud.createOrUpdateVOInDB(lifeVO);
    }
    
    /**
     * For the Complex Change trx, also reverse the change for the type specified
     * @param lifeVO
     * @param contractSetupVO
     * @param financialHistoryVO 
     * @param crud
     */
    private void reverseComplexChangeType(LifeVO lifeVO,ContractSetupVO contractSetupVO, FinancialHistoryVO financialHistoryVO, CRUD crud)
    {
        String complexChangeType =contractSetupVO.getComplexChangeTypeCT();
        if (complexChangeType.equalsIgnoreCase("DBOChange") && lifeVO != null)
        {
            lifeVO.setDeathBenefitOptionCT(financialHistoryVO.getPrevComplexChangeValue());
            lifeVO.setPendingDBOChangeInd("Y");
        }
        else if (complexChangeType.equalsIgnoreCase("ClassChange"))
        {
            ContractClientVO contractClientVO = getInsuredContractClient();
            contractClientVO.setClassCT(financialHistoryVO.getPrevComplexChangeValue());
            contractClientVO.setPendingClassChangeInd("Y");
            crud.createOrUpdateVOInDB(contractClientVO);

        }
        else if (complexChangeType.equalsIgnoreCase("TableRatingChange"))
        {
            ContractClientVO contractClientVO = getInsuredContractClient();
            contractClientVO.setTableRatingCT(financialHistoryVO.getPrevComplexChangeValue());
            contractClientVO.setPendingClassChangeInd("Y");
            crud.createOrUpdateVOInDB(contractClientVO);
        }
        else if (complexChangeType.equalsIgnoreCase("ROTHConversion"))
        {
            segmentVO.setCostBasis(financialHistoryVO.getPriorCostBasis());
            segmentVO.setROTHConvInd("N");
            segmentVO.setQualifiedTypeCT("IRA");
        }
    }

    /**
     * Get the ContractClient that will be updated with a previous value
     * @return
     */
//    private ContractClientVO getInsuredContractClient()
//    {
//        ContractClient contractClient = new ContractClient();
//        ContractClientVO[] contractClientVOs = contract.dm.dao.DAOFactory.getContractClientDAO().findBySegmentFK(segmentVO.getSegmentPK(), false, null);
//        ContractClientVO contractClientVO = contractClient.getInsuredContractClient(contractClientVOs);
//        return contractClientVO;
//    }

    /**
     * Returns the FilteredFundFK for InvestmentHistory.ToFromStatus = 'F'
     * @param originatingEDITTrxVO
     * @return
     */
    private long getFilteredFundFKOfFromInvestment(EDITTrxVO originatingEDITTrxVO)
    {
        long filteredFundFK = 0;

        InvestmentHistoryVO[] investmentHistoryVOs = originatingEDITTrxVO.getEDITTrxHistoryVO(0).getInvestmentHistoryVO();

        for (int i = 0; i < investmentHistoryVOs.length; i++)
        {
            InvestmentHistoryVO investmentHistoryVO = investmentHistoryVOs[i];

            if (investmentHistoryVO.getToFromStatus().equals("F"))
            {
                InvestmentVO investmentVO = (InvestmentVO) investmentHistoryVO.getParentVO(InvestmentVO.class);

                filteredFundFK = investmentVO.getFilteredFundFK();

                break;
            }
        }

        return filteredFundFK;
    }

    /**
     * Returns the FilteredFundFK for InvestmentHistory.ToFromStatus = 'F'
     * @param originatingEDITTrxVO
     * @return
     */
    private long getChargeCodeFKOfFromInvestment(EDITTrxVO originatingEDITTrxVO)
    {
        long chargeCodeFK = 0;

        InvestmentHistoryVO[] investmentHistoryVOs = originatingEDITTrxVO.getEDITTrxHistoryVO(0).getInvestmentHistoryVO();

        for (int i = 0; i < investmentHistoryVOs.length; i++)
        {
            InvestmentHistoryVO investmentHistoryVO = investmentHistoryVOs[i];

            if (investmentHistoryVO.getToFromStatus().equals("F"))
            {
                InvestmentVO investmentVO = (InvestmentVO) investmentHistoryVO.getParentVO(InvestmentVO.class);

                chargeCodeFK = investmentVO.getChargeCodeFK();

                break;
            }
        }

        return chargeCodeFK;
    }

    /**
     * Unterminate transactions and add them to the list of clientStrategys
     *
     * @param segment
     * @param editTrx
     *
     * @return
     */
    private ClientStrategy[] unterminateTransactions(Segment segment, EDITTrx editTrx)
    {
        List clientStrategys = new ArrayList();
        ClientStrategy[] clientStrategyArray = null;

        if (segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_OVERLOAN))
        {
            List clientStrategyList = unterminateTransactionsBasedOnType(segment, editTrx, EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN);
            if (clientStrategyList.size() > 0)
            {
                clientStrategys.add(clientStrategyList);
            }
        }
        else if (segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_OVERDUE))
        {
            List clientStrategyList = unterminateTransactionsBasedOnType(segment, editTrx, EDITTrx.TRANSACTIONTYPECT_LAPSE_PENDING);
            if (clientStrategyList.size() > 0)
            {
                clientStrategys.add(clientStrategyList);
            }
        }
        else if (segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSEPENDING))
        {
            List clientStrategyList = unterminateTransactionsBasedOnType(segment, editTrx, EDITTrx.TRANSACTIONTYPECT_LAPSE);
            if (clientStrategyList.size() > 0)
            {
                clientStrategys.add(clientStrategyList);
            }
        }

        if (clientStrategys.size() >  0)
        {
            clientStrategyArray = (ClientStrategy[]) clientStrategys.toArray(new ClientStrategy[clientStrategys.size()]);
        }

        return  clientStrategyArray;
    }

    /**
     * "Unterminates" transactions for a specified transactionType that meet certain criteria
     * 1.  If the transactionType's priority is less than the reversal's priority
     * 2.  If the terminated transaction's effectiveDate is >= this reversal's effective date
     * <P>
     * Unterminates by changing the terminated status to pending.  A ClientStrategy is built for the unterminated
     * transaction and returned in the list so it can be added to the StrategyChain.
     *
     * @param segment
     * @param editTrx
     *
     * @return
     */
    private List unterminateTransactionsBasedOnType(Segment segment, EDITTrx editTrx, String transactionType)
    {
        List clientStrategys = new ArrayList();

        String thisTrxsTransactionTypeCT = editTrx.getTransactionTypeCT();
        EDITDate thisTrxsEffectiveDate = editTrx.getEffectiveDate();
        long thisTrxsPK = editTrx.getEDITTrxPK();

        if (TransactionPriority.priorityIsLessThan(transactionType, thisTrxsTransactionTypeCT))
        {
            EDITTrx[] terminatedEDITTrxs = EDITTrx.findBy_EffectiveDate_GTorEqual_TransactionType_PendingStatus(thisTrxsEffectiveDate,
                transactionType, EDITTrx.PENDINGSTATUS_TERMINATED, segment.getSegmentPK());

            for (int i = 0; i < terminatedEDITTrxs.length; i++)
            {
            	if (terminatedEDITTrxs[i].getTerminationTrxFK() == thisTrxsPK) 
            	{
	                terminatedEDITTrxs[i].unterminate();
	
	                unterminateEDITTrxCorrespondence(terminatedEDITTrxs[i].getEDITTrxPK());
	
	                ClientStrategy clientStrategy = buildClientStrategy(terminatedEDITTrxs[i]);
	
	                clientStrategys.add(clientStrategy);
            	}
            }
        }

        return clientStrategys;
    }

    /**
     * Returns true if the IncludeUndoTransactionsInCommissionStatementIndicator is set to 'Y' in configuration file.
     * @return
     */
    private boolean includeUndoTransactionsInCommissionStatement()
    {
        boolean includeUndoTransactionsInCommissionStatement = false;

        String includeUndoTransactionsInCommissionStatementIndicator = ServicesConfig.getIncludeUndoTransactionsInCommissionStatementsIndicator();

        if (includeUndoTransactionsInCommissionStatementIndicator != null &&
                includeUndoTransactionsInCommissionStatementIndicator.equals("Y"))
        {
            includeUndoTransactionsInCommissionStatement = true;
        }

        return includeUndoTransactionsInCommissionStatement;
    }

    /**
     * Creates a ClientStrategy object using the specified editTrx
     * @param editTrx
     * @return
     */
    private ClientStrategy buildClientStrategy(EDITTrx editTrx)
    {
        ClientTrx clientTrx = new ClientTrx(editTrx.getAsVO());

        ClientStrategy clientStrategy = new Reversal(clientTrx);

        return clientStrategy;
    }

    /**
     * Unterminates all the editTrxCorrespondences for a given editTrx
     *
     * @param editTrx
     */
    private void unterminateEDITTrxCorrespondence(long editTrxPK)
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxPK);
        
        for (int i=0; i < editTrxCorrespondenceVOs.length; i++)
        {
            EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence(editTrxCorrespondenceVOs[i]);

            editTrxCorrespondence.unterminate();
        }
    }
    
    /**
     * Resets terminated editTrxCorrespondences upon reversal
     *
     * @param editTrx
     */
    private void resetTerminatedEDITTrxCorrespondence(long editTrxPK)
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxPK);
        
        for (int i=0; i < editTrxCorrespondenceVOs.length; i++)
        {
        	
            EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence(editTrxCorrespondenceVOs[i]);
            
            if (editTrxCorrespondence.getStatus().equalsIgnoreCase(EDITTrxCorrespondence.STATUS_TERMINATED))
            {
            	editTrxCorrespondence.unterminate();
            	//crud.createOrUpdateVOInDB(editTrxVO);
            }
        }
    }

	/**
     * Get the ContractClient that will be updated with a previous value
     * @return
     */
    private ContractClientVO getInsuredContractClient()
	{
        ContractClientVO[] contractClientVOs = contract.dm.dao.DAOFactory.getContractClientDAO().findBySegmentFK(segmentVO.getSegmentPK(), false, null);

        ContractClient[] contractClients = convertContractClientVOs(contractClientVOs);

        ContractClient contractClient = ContractClient.getInsuredContractClient(contractClients);

        return (ContractClientVO) contractClient.getVO();
    }

    // The following method is temporary until we fully go to Hibernate
    private ContractClient[] convertContractClientVOs(ContractClientVO[] contractClientVOs)
    {
        List contractClients = new ArrayList();

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            ContractClient contractClient = new ContractClient(contractClientVOs[i]);

            contractClients.add(contractClient);
        }

        return (ContractClient[]) contractClients.toArray(new ContractClient[contractClients.size()]);
    }

    private void logWarning(String message)
    {
        EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();
        String operator = editTrxVO.getOperator();
        String contractNumber = "None Found!";


        if (segmentVO != null)
        {
            contractNumber = segmentVO.getContractNumber();
        }

        Logger logger = Logging.getLogger(Logging.VALIDATE_TRANSACTION_SAVE);

        LogEvent logEvent = new LogEvent(message);

        logEvent.setContextName(Logging.VALIDATE_TRANSACTION_SAVE);
        logEvent.addToContext("TransactionTypeCT", editTrxVO.getTransactionTypeCT());
        logEvent.addToContext("ContractNumber", contractNumber);
        logEvent.addToContext("EffectiveDate", editTrxVO.getEffectiveDate());
        logEvent.addToContext("Severity", "Warning");
        logEvent.addToContext("Operator", operator);

        logger.error(logEvent);
    }
}
