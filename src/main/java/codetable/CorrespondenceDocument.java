/*
 * User: dlataill
 * Date: Aug 5, 2004
 * Time: 12:49:57 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package codetable;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.*;
import edit.services.db.CRUD;
import event.EDITTrx;
import event.EDITTrxCorrespondence;
import event.TransactionPriority;

import java.util.ArrayList;
import java.util.List;

import engine.FilteredFund;
import contract.*;
import contract.dm.composer.VOComposer;
public class CorrespondenceDocument extends NaturalDocument
{
    private String trxType = null;
    private String startingEffDate = null;
    private String endingEffDate = null;
    private String trxEffectiveDate = null;
    private EDITTrxVO[] allEditTrxVOs = null;
    private EDITTrxCorrespondence editTrxCorrespondence = null;

    private Segment segment = null;
    
    public CorrespondenceDocument(String trxType,
                                   String startingEffDate,
                                    String endingEffDate,
                                     EDITTrxVO[] allEditTrxVOs,
                                     EDITTrxCorrespondence editTrxCorrespondence)
    {
        super();
        this.trxType = trxType;
        this.startingEffDate = startingEffDate;
        this.endingEffDate = endingEffDate;
        this.allEditTrxVOs = allEditTrxVOs;
        this.editTrxCorrespondence = editTrxCorrespondence;

        setEDITTrxInclusionList();
        setSegmentInclusionList();
    }

    public CorrespondenceDocument(EDITTrxVO editTrxVO)
    {
        super();
        this.editTrxVO = editTrxVO;

        setNonStmtEDITTrxInclusionList();
        setNonStmtSegmentInclusionList();
    }

    public CorrespondenceDocument(Segment segment, EDITDate stagingDate)
    {
        super();
        this.segment = segment;
        this.trxEffectiveDate = stagingDate.toString();
    }

    /**
     * Build NaturalDocVO for CorrespondenceProcessor (Statements Only)
     */
    public void buildDocumentWithHistory()
    {
        try
        {
//            TransactionPriorityVO[] trxPriorityVO = DAOFactory.getTransactionPriorityDAO().findByTrxType(trxType);
            TransactionPriority drivingTrxTransactionPriority = TransactionPriority.findByTrxType(trxType);
            int drivingTrxPriority = drivingTrxTransactionPriority.getPriority();

            EDITDate sedED = new EDITDate(startingEffDate);
            EDITDate eedED = new EDITDate(endingEffDate);

            for (int i = 0; i < allEditTrxVOs.length; i++)
            {
                editTrxVO = allEditTrxVOs[i];
                String status = editTrxVO.getStatus();
                
                if (!status.equalsIgnoreCase(EDITTrx.STATUS_REVERSAL) && !status.equalsIgnoreCase(EDITTrx.STATUS_UNDO))
                {
	                String trxType = editTrxVO.getTransactionTypeCT();
	                TransactionPriority transactionPriority = TransactionPriority.findByTrxType(trxType);
	                int trxPriority = transactionPriority.getPriority();
	
	                String trxEffDate = editTrxVO.getEffectiveDate();
	                EDITDate tedED = new EDITDate(trxEffDate);
	
	                // if policy is not in first year of duration which is determined using the following method,
	                // use transaction priority comparison when transaction effective date is same as starting date.
	                // other wise that means the policy is in first year of duration then do not compare transaction
	                // priorities when transaction effective date is same as starting date.
	                if (hasTransactionWithSamePriorityForADate(drivingTrxPriority, sedED))
	                {
	                    if ((tedED.after(sedED) && tedED.before(eedED)) ||
	                       ((tedED.equals(sedED) && trxPriority >= drivingTrxPriority)) ||
	                       (tedED.equals(eedED) && trxPriority <= drivingTrxPriority))
	                    {
	                        buildDocumentIncludingHistory();
	                    }
	                }
	                else
	                {
	                    if ((tedED.after(sedED) && tedED.before(eedED)) ||
	                       (tedED.equals(sedED)) ||
	                       (tedED.equals(eedED) && trxPriority <= drivingTrxPriority))
	                    {
	                        buildDocumentIncludingHistory();
	                    }
	                }
                }
            }
        }
        catch (Exception e)
        {
//            Logger logger = Logging.getLogger(Logging.LOGGER_CONTRACT_BATCH);
//
//            LogEntryVO logEntryVO = new LogEntryVO();
//
//            logEntryVO.setMessage("CorrespondenceProcessor Failed - " + e.getMessage());
//            logEntryVO.setHint("NaturalDocVO Was Not Built");
//
//            logger.error(logEntryVO);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Helper method.
     * @throws Exception
     */
    private void buildDocumentIncludingHistory() throws Exception
    {
        // Only build complete natural doc once for the triggering transaction

    	if (new Long(editTrxVO.getEDITTrxPK()).equals(editTrxCorrespondence.getEDITTrxFK())) 
        {
            super.composeEDITTrx();
            super.buildDocument();
            if (super.getInvestmentVO() != null)
            {
                super.getSegmentVO().setInvestmentVO(super.getInvestmentVO());
            }

            SegmentVO segmentVO = super.getSegmentVO();
            SegmentVO[] riderSegments = segmentVO.getSegmentVO();
            segmentVO.removeAllSegmentVO();

            segmentVO = checkForServicingAgent(segmentVO);

            addFilteredFundsWithCorrespondenceAsInvestments(segmentVO);

            BaseSegmentVO baseSegmentVO = new BaseSegmentVO();
            baseSegmentVO.setSegmentVO(super.getSegmentVO());
            baseSegmentVO.addClientVO(super.getClientVO());

            buildCommissionVO(super.getSegmentVO());

            ClientVO baseInsuredClientVO = this.buildInsuredClientVO(segmentVO);

            if (baseInsuredClientVO != null)
            {
                baseSegmentVO.addClientVO(baseInsuredClientVO);
            }

            naturalDocVO.setBaseSegmentVO(baseSegmentVO);

            if (super.getInvestmentAllocationOverrideVO() != null)
            {
                naturalDocVO.setInvestmentAllocationOverrideVO(super.getInvestmentAllocationOverrideVO());
            }

            if (riderSegments != null)
            {
                for (int i = 0; i < riderSegments.length; i++)
                {
                    RiderSegmentVO riderSegmentVO = new RiderSegmentVO();
                    riderSegmentVO.setSegmentVO(riderSegments[i]);

                    ClientVO riderInsuredClientVO = this.buildInsuredClientVO(riderSegments[i]);
                    if (riderInsuredClientVO != null)
                    {
                        riderSegmentVO.addClientVO(riderInsuredClientVO);
                    }

                    CommissionVO[] riderCommissionVO = null;
                    String commissionStatus = editTrxVO.getCommissionStatus();

                    if (commissionStatus != null && commissionStatus.equalsIgnoreCase("P"))
                    {
                        AgentHierarchyVO[] agentHierarchyVO = riderSegments[i].getAgentHierarchyVO();

                        if (agentHierarchyVO != null)
                            riderCommissionVO = getCommissionData(agentHierarchyVO);

                        if (riderCommissionVO != null)
                        {
                            riderSegmentVO.setCommissionVO(riderCommissionVO);
                        }
                    }

                    naturalDocVO.addRiderSegmentVO(riderSegmentVO);
                }
            }
        }
        else
        {
            super.composeEDITTrx();
            naturalDocVO.addGroupSetupVO(groupSetupVO);
        }
    }

    /**
     * Build NaturalDocVO for CorrespondenceProcessor (NonStatements Only)
     */
    public void buildDocumentWithoutHistory()
    {
        try
        {
            editTrxVOInclusionList.add(EDITTrxCorrespondenceVO.class);
            super.composeEDITTrx();
            super.buildDocument();
            super.composeBaseAndRiderSegmentVOs();
            super.associateBillingInfo();
            super.setGroupInfo();

            SegmentVO segmentVO = super.getSegmentVO();

            addFilteredFundsWithCorrespondenceAsInvestments(segmentVO);

            super.checkForTransactionAccumGeneration();
            super.composeOverdueChargesRemaining();

            //buildCommissionVO(super.getSegmentVO());
            if (super.commissionVO != null)
            {
                super.naturalDocVO.getBaseSegmentVO().setCommissionVO(super.commissionVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Build NaturalDocVO for CorrespondenceProcessor (NonStatements Only)
     */
    public void buildDocumentForDataWarehouse()
    {
        try
        {
            GroupSetupVO groupSetupVO = new GroupSetupVO();
            ContractSetupVO contractSetupVO = new ContractSetupVO();
            ClientSetupVO clientSetupVO = new ClientSetupVO();
            EDITTrxVO editTrxVO = new EDITTrxVO();
            editTrxVO.setEffectiveDate(this.trxEffectiveDate);
            clientSetupVO.addEDITTrxVO(editTrxVO);
            contractSetupVO.addClientSetupVO(clientSetupVO);
            groupSetupVO.addContractSetupVO(contractSetupVO);
            naturalDocVO.addGroupSetupVO(groupSetupVO);
            super.composeBaseAndRiderSegmentVOs(segment);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Build the CommissionVO for all agent hierarchies on the given SegmentVO.
     * @param segmentVO
     * @throws Exception
     */
    protected void buildCommissionVO(SegmentVO segmentVO) throws Exception
    {
        AgentHierarchyVO[] agentHierarchyVO = segmentVO.getAgentHierarchyVO();

        if (agentHierarchyVO != null)
        {
            super.commissionVO = getCommissionData(agentHierarchyVO);
        }
    }

    /**
     * Get the CommissionVO data for the given agent hierarchies.
     * @param agentHierarchyVO
     * @return
     * @throws Exception
     */
    private CommissionVO[] getCommissionData(AgentHierarchyVO[] agentHierarchyVO) throws Exception
    {
        List voInclusionList = new ArrayList();

        voInclusionList.add(AgentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(AdditionalCompensationVO.class);
//        voInclusionList.add(AgentLicenseVO.class);
        voInclusionList.add(CommissionProfileVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientRoleFinancialVO.class);

        List commissions = new ArrayList();

        for (int i = 0; i < agentHierarchyVO.length; i++)
        {
            CommissionVO commissionVO = new CommissionVO();

            AgentHierarchyAllocation agentHierarchyAllocation = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(
                    new Long(agentHierarchyVO[i].getAgentHierarchyPK()), new EDITDate(this.editTrxVO.getEffectiveDate()));

            if (agentHierarchyAllocation != null)   // if trx is back dated, may not have an active allocation for that effective date
            {

                EDITBigDecimal allocationPercent = agentHierarchyAllocation.getAllocationPercent();

                //order by hierarchy level
                AgentSnapshotVO[] agentSnapshotVOs = contract.dm.dao.DAOFactory.getAgentSnapshotDAO().findSortedAgentSnapshotVOs(agentHierarchyVO[i].getAgentHierarchyPK());

                AgentSnapshotDetailVO[] agentSnapshotDetailVO = new agent.dm.composer.VOComposer().composeCommissionVO(new EDITTrx(editTrxVO), agentSnapshotVOs, agentHierarchyVO[i].getSegmentFK(), voInclusionList);

                commissionVO.setAllocationPercent( allocationPercent.getBigDecimal() );
                commissionVO.setAgentSnapshotDetailVO(agentSnapshotDetailVO);
                commissionVO.setCommissionPK(i + 1);
                commissions.add(commissionVO);
            }
        }

        return (CommissionVO[]) commissions.toArray(new CommissionVO[commissions.size()]) ;
    }

    private SegmentVO checkForServicingAgent(SegmentVO segmentVO)
    {
//        AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
//        segmentVO.removeAllAgentHierarchyVO();
//        for (int i = 0; i < agentHierarchyVOs.length; i++)
//        {
//            if (agentHierarchyVOs[i].getServicingAgentIndicator() != null &&
//                agentHierarchyVOs[i].getServicingAgentIndicator().equalsIgnoreCase("Y"))
//            {
//                segmentVO.addAgentHierarchyVO(agentHierarchyVOs[i]);
//            }
//        }
//
//        if (segmentVO.getAgentHierarchyVOCount() == 0)
//        {
//            segmentVO.setAgentHierarchyVO(agentHierarchyVOs);
//        }

        return segmentVO;
    }


    /**
     * Adds filtered funds that has 'IncludeInCorrespondence' indicator to 'Y' for given segment's company structure
     * as investments to a given segment. This is requried to process correspondence document. The investments that are
     * added does not get saved to database and are used only in scripts for processing.
     * @param segmentVO segmentVO that you want to add to
     */
    private void addFilteredFundsWithCorrespondenceAsInvestments(SegmentVO segmentVO)
    {
        long productStructurePK = segmentVO.getProductStructureFK();

//        CompanyStructure companyStructure = CompanyStructure.findByPK(new Long(companyStructurePK));

        FilteredFund[] filteredFunds = FilteredFund.findByProductStructure_And_CorrespondenceIndicator(new Long(productStructurePK), FilteredFund.CORRESPONDENCE_INDICATOR_YES);

        Segment segment = Segment.findByPK(new Long(segmentVO.getSegmentPK()));

        for (int i = 0; i < filteredFunds.length; i++)
        {
            FilteredFund filteredFund = filteredFunds[i];

            if (!segment.isFilteredFundAnInvestment(filteredFund))
            {
                InvestmentVO investmentVO = new InvestmentVO();

                investmentVO.setInvestmentPK(CRUD.getNextAvailableKey());
                investmentVO.setFilteredFundFK(filteredFunds[i].getFilteredFundPK().longValue());
                investmentVO.setSegmentFK(segmentVO.getSegmentPK());

                // no need to return segmentVO since objects are passed by reference.
                segmentVO.addInvestmentVO(investmentVO);
            }
        }
    }

    /**
     * Helper method to determine the policy is in first year of duration.
     * This could be determined if we have 'ST' trasnsaction for previous year. If we have more than one 'ST' transaction
     * that means the policy is not in first year of duration.
     * Reason for mentioning 'ST' is correspondence document is driven by 'ST' transaction. But the code does not
     * restrict any where to retrieve only 'ST' transactions but business wise we will have only 'ST' transactions that
     * will have correspondence and 'Include History' parameter set.
     * @param drivingTrxPriority
     * @param startingDate
     * @return
     */
    private boolean hasTransactionWithSamePriorityForADate(int drivingTrxPriority, EDITDate startingDate)
    {
        boolean isExists = false;

        for (int i = 0; i < allEditTrxVOs.length; i++)
        {
            EDITTrxVO editTrxVO = allEditTrxVOs[i];

            TransactionPriority priority = TransactionPriority.findByTrxType(editTrxVO.getTransactionTypeCT());

            if (startingDate.equals(new EDITDate(editTrxVO.getEffectiveDate())))
            {
                if (drivingTrxPriority == priority.getPriority())
                {
                    isExists = true;
                    break;
                }
            }
        }

        return isExists;
    }

    /**
     * Set the Inclusion List for the Segment Composer.
     */
    public void setSegmentInclusionList()
    {
        super.setSegmentInclusionList();
        segmentVOInclusionList.add(SegmentVO.class);
        segmentVOInclusionList.add(PayoutVO.class);
        segmentVOInclusionList.add(AgentHierarchyVO.class);
        segmentVOInclusionList.add(LifeVO.class);
        segmentVOInclusionList.add(DepositsVO.class);
        segmentVOInclusionList.add(PremiumDueVO.class);
        segmentVOInclusionList.add(CommissionPhaseVO.class);
        
    }

    /**
     * Set the Inclusion List for the EDITTrx Composer.
     */
    public void setEDITTrxInclusionList()
    {
        super.setEDITTrxInclusionList();
        editTrxVOInclusionList.add(ContractSetupVO.class);
        editTrxVOInclusionList.add(ChargeVO.class);
        editTrxVOInclusionList.add(GroupSetupVO.class);
        editTrxVOInclusionList.add(ScheduledEventVO.class);
        editTrxVOInclusionList.add(ClientSetupVO.class);
        editTrxVOInclusionList.add(EDITTrxHistoryVO.class);
        editTrxVOInclusionList.add(FinancialHistoryVO.class);
        editTrxVOInclusionList.add(BucketHistoryVO.class);
        editTrxVOInclusionList.add(ChargeHistoryVO.class);
        editTrxVOInclusionList.add(WithholdingHistoryVO.class);
    }

    /**
     * Set the Inclusion List for the Segment Composer.
     */
    public void setNonStmtSegmentInclusionList()
    {
        super.setSegmentInclusionList();
        segmentVOInclusionList.add(SegmentVO.class);
        segmentVOInclusionList.add(PayoutVO.class);
        segmentVOInclusionList.add(LifeVO.class);
        segmentVOInclusionList.add(AgentHierarchyVO.class);
        segmentVOInclusionList.add(DepositsVO.class);
        segmentVOInclusionList.add(InherentRiderVO.class);
//        segmentVOInclusionList.add(PolicyGroupVO.class);
        segmentVOInclusionList.add(RequiredMinDistributionVO.class);
    }

    /**
     * Set the Inclusion List for the EDITTrx Composer.
     */
    public void setNonStmtEDITTrxInclusionList()
    {
        super.setEDITTrxInclusionList();
        editTrxVOInclusionList.add(ContractSetupVO.class);
        editTrxVOInclusionList.add(ChargeVO.class);
        editTrxVOInclusionList.add(GroupSetupVO.class);
        editTrxVOInclusionList.add(ScheduledEventVO.class);
        editTrxVOInclusionList.add(ClientSetupVO.class);
        editTrxVOInclusionList.add(InvestmentAllocationOverrideVO.class);
        if (editTrxVO.getPendingStatus().equalsIgnoreCase("F") ||
            editTrxVO.getPendingStatus().equalsIgnoreCase("B") ||
            editTrxVO.getPendingStatus().equalsIgnoreCase("C"))
        {
            editTrxVOInclusionList.add(EDITTrxHistoryVO.class);
            editTrxVOInclusionList.add(FinancialHistoryVO.class);
            editTrxVOInclusionList.add(ChargeHistoryVO.class);
            editTrxVOInclusionList.add(BucketHistoryVO.class);
            editTrxVOInclusionList.add(BucketChargeHistoryVO.class);
            editTrxVOInclusionList.add(WithholdingVO.class);
            editTrxVOInclusionList.add(InSuspenseVO.class);
            editTrxVOInclusionList.add(CommissionHistoryVO.class);
            editTrxVOInclusionList.add(InvestmentHistoryVO.class);
        }
    }
}
