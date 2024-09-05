/*
 * User: gfrosti
 * Date: Aug 4, 2003
 * Time: 2:07:19 PM

 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

import role.dm.composer.ClientRoleComposer;

public class SegmentComposer extends Composer
{
    private List voInclusionList;
    private NoteReminderVO[] noteReminderVO;
    private PayoutVO[] payoutVO;
    private LifeVO[] lifeVO;
    private AgentHierarchyVO[] agentHierarchyVO;
    private AgentSnapshotVO[] agentSnapshotVO;
    private DepositsVO[] depositsVO;
    private InvestmentVO[] investmentVO;
    private InvestmentAllocationVO[] investmentAllocationVO;
    private InherentRiderVO[] inherentRiderVO;
    private ChangeHistoryVO[] changeHistoryVO;
    private ContractRequirementVO[] contractRequirementVO;
    private RequiredMinDistributionVO[] rmdVO;
    private PremiumDueVO[] premiumDueVO;
    private ValueAtIssueVO[] valueAtIssueVO;
    private CRUD crud;

    public SegmentComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    /**
     * Builds a SegmentVO with specified child, listed in the voInclusionList.
     * @param segmentPK
     * @return
     */
    public SegmentVO compose(long segmentPK)
    {
        SegmentVO segmentVO = null;

        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            segmentVO = (SegmentVO) crud.retrieveVOFromDB(SegmentVO.class, segmentPK);

            compose(segmentVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return segmentVO;
    }

    /**
     * For the request segmentPK and childred, assemble a SegmentVO
     * @param segmentVO
     * @throws Exception
     */
    public void compose(SegmentVO segmentVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

//            if (voInclusionList.contains(SegmentVO.class)) appendSegmentVO(segmentVO);

            if (voInclusionList.contains(SegmentVO.class)) appendRiders(segmentVO);

            composeForBaseAndRiders(segmentVO);

//            if (voInclusionList.contains(NoteReminderVO.class)) appendNoteReminderVO(segmentVO);
//
//            if (voInclusionList.contains(PayoutVO.class)) appendPayoutVO(segmentVO);
//
//            if (voInclusionList.contains(LifeVO.class)) appendLifeVO(segmentVO);
//
//            if (voInclusionList.contains(AgentHierarchyVO.class)) appendAgentHierarchyVO(segmentVO);
//
//            if (voInclusionList.contains(DepositsVO.class)) appendDepositsVO(segmentVO);
//
//            if (voInclusionList.contains(InvestmentVO.class)) appendInvestmentVO(segmentVO);
//
//            if (voInclusionList.contains(ContractClientVO.class)) appendContractClientVO(segmentVO);
//
//            if (voInclusionList.contains(InherentRiderVO.class)) appendInherentRiderVO(segmentVO);
//
//            if (voInclusionList.contains(ChangeHistoryVO.class)) appendChangeHistoryVO(segmentVO);
//
//            if (voInclusionList.contains(ContractRequirementVO.class)) appendContractRequirementVO(segmentVO);
//
//            if (voInclusionList.contains(MasterVO.class)) associateMasterVO(segmentVO);
//
//            if (voInclusionList.contains(RequiredMinDistributionVO.class)) appendRequiredMinDistributionVO(segmentVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    /**
     * Common compose for the base and all riders.  Set the global variable to null.
     * @param segmentVO
     */
    public void composeForBaseAndRiders(SegmentVO segmentVO)
    {
        try
        {
            if (voInclusionList.contains(NoteReminderVO.class)) appendNoteReminderVO(segmentVO);

            if (voInclusionList.contains(PayoutVO.class)) appendPayoutVO(segmentVO);

            if (voInclusionList.contains(LifeVO.class)) appendLifeVO(segmentVO);

            if (voInclusionList.contains(AgentHierarchyVO.class)) appendAgentHierarchyVO(segmentVO);

            if (voInclusionList.contains(DepositsVO.class)) appendDepositsVO(segmentVO);

            if (voInclusionList.contains(InvestmentVO.class)) appendInvestmentVO(segmentVO);

            if (voInclusionList.contains(ContractClientVO.class)) appendContractClientVO(segmentVO);

            if (voInclusionList.contains(InherentRiderVO.class)) appendInherentRiderVO(segmentVO);

            if (voInclusionList.contains(ChangeHistoryVO.class)) appendChangeHistoryVO(segmentVO);

            if (voInclusionList.contains(ContractRequirementVO.class)) appendContractRequirementVO(segmentVO);

//            if (voInclusionList.contains(MasterVO.class)) associateMasterVO(segmentVO);

            if (voInclusionList.contains(BillScheduleVO.class)) associateBillScheduleVO(segmentVO);

            if (voInclusionList.contains(RequiredMinDistributionVO.class)) appendRequiredMinDistributionVO(segmentVO);

            if (voInclusionList.contains(PremiumDueVO.class)) appendPremiumDueVO(segmentVO);

            if (voInclusionList.contains(ValueAtIssueVO.class)) appendValueAtIssueVO(segmentVO);

            noteReminderVO = null;
            payoutVO = null;
            lifeVO = null;
            agentHierarchyVO = null;
            agentSnapshotVO = null;
            depositsVO = null;
            investmentVO = null;
            investmentAllocationVO = null;
            inherentRiderVO = null;
            changeHistoryVO = null;
            contractRequirementVO = null;
            rmdVO = null;
            premiumDueVO = null;
            valueAtIssueVO = null;
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void substituteNoteReminderVO(long segmentPK, NoteReminderVO[] noteReminderVO)
    {
        this.noteReminderVO = noteReminderVO;
    }

    public void substitutePayoutVO(long segmentPK, PayoutVO[] payoutVO)
    {
        this.payoutVO = payoutVO;
    }

    public void substituteAgentHierarchyVO(long segmentPK, AgentHierarchyVO[] agentHierarchyVO)
    {
        this.agentHierarchyVO = agentHierarchyVO;
    }

//    private void appendSegmentVO(SegmentVO segmentVO) throws Exception
//    {
//        SegmentVO[] riderSegmentVOs = (SegmentVO[]) crud.retrieveVOFromDB(SegmentVO.class,  SegmentVO.class, segmentVO.getSegmentPK());
//
//        if (riderSegmentVOs != null) segmentVO.setSegmentVO(riderSegmentVOs);
//    }

    /**
     * Add all the riders to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendRiders(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        SegmentVO[] riderVOs = (SegmentVO[]) crud.retrieveVOFromDB(SegmentVO.class, SegmentVO.class, segmentVO.getSegmentPK());

        voInclusionList.add(SegmentVO.class);

        if (riderVOs != null)
        {
            for (int i = 0; i < riderVOs.length; i++)
            {
                composeForBaseAndRiders(riderVOs[i]);
                segmentVO.addSegmentVO(riderVOs[i]);
            }
        }
    }

    /**
     * Add all the Note Reminders to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendNoteReminderVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        if (noteReminderVO == null) //No overrides
        {
            noteReminderVO = (NoteReminderVO[]) crud.retrieveVOFromDB(NoteReminderVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (noteReminderVO != null) segmentVO.setNoteReminderVO(noteReminderVO);

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add all the Payouts to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendPayoutVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        if (payoutVO == null)
        {
            payoutVO = (PayoutVO[]) crud.retrieveVOFromDB(PayoutVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (payoutVO != null) segmentVO.setPayoutVO(payoutVO);

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Get the LifeVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendLifeVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        if (lifeVO == null)
        {
            lifeVO = (LifeVO[]) crud.retrieveVOFromDB(LifeVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (lifeVO != null) segmentVO.setLifeVO(lifeVO);

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add all the agent hierarchies to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendAgentHierarchyVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        if (agentHierarchyVO == null)
        {
            agentHierarchyVO = (AgentHierarchyVO[]) crud.retrieveVOFromDB(AgentHierarchyVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (agentHierarchyVO != null)
        {
            segmentVO.setAgentHierarchyVO(agentHierarchyVO);

            for (int i = 0; i < agentHierarchyVO.length; i++)
            {
                if (voInclusionList.contains(AgentSnapshotVO.class)) appendAgentSnapshotVO(agentHierarchyVO[i]);
                if (voInclusionList.contains(AgentVO.class)) associateAgentVO(agentHierarchyVO[i]);
            }
        }

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add all the agentSnaphot to each agent Hierarchy
     * @param segmentVO
     * @throws Exception
     */
    private void appendAgentSnapshotVO(AgentHierarchyVO agentHierarchyVO) throws Exception
    {
        voInclusionList.remove(AgentHierarchyVO.class);

//        if (agentSnapshotVO == null)
//        {
        agentSnapshotVO = (AgentSnapshotVO[]) crud.retrieveVOFromDB(AgentSnapshotVO.class, AgentHierarchyVO.class, agentHierarchyVO.getAgentHierarchyPK());
//        }

        if (agentSnapshotVO != null) agentHierarchyVO.setAgentSnapshotVO(agentSnapshotVO);

        voInclusionList.add(AgentHierarchyVO.class);
    }

    /**
     * Add all the deposit to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendDepositsVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        if (depositsVO == null)
        {
            depositsVO = (DepositsVO[]) crud.retrieveVOFromDB(DepositsVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (depositsVO != null)
        {
            segmentVO.setDepositsVO(depositsVO);
        }

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add all the investments to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendInvestmentVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);
        InvestmentVO[] investmentVOs = null;

        if (investmentVO == null)
        {
            investmentVOs = (InvestmentVO[]) crud.retrieveVOFromDB(InvestmentVO.class, SegmentVO.class, segmentVO.getSegmentPK());
            if (investmentVOs != null)
            {
                segmentVO.setInvestmentVO(investmentVOs);

                for (int i = 0; i < investmentVOs.length; i++)
                {
                    boolean investmentsInLoop = true;
                    if (voInclusionList.contains(InvestmentAllocationVO.class)) appendInvestmentAllocationVO(investmentVOs[i], investmentsInLoop);
                    if (voInclusionList.contains(BucketVO.class)) appendBucketVO(investmentVOs[i]);
                }
            }
        }

        if (investmentVO != null)
        {
            segmentVO.setInvestmentVO(investmentVO);

            for (int i = 0; i < investmentVO.length; i++)
            {
                boolean investmentsInLoop = true;
                if (voInclusionList.contains(InvestmentAllocationVO.class)) appendInvestmentAllocationVO(investmentVO[i], investmentsInLoop);
                if (voInclusionList.contains(BucketVO.class)) appendBucketVO(investmentVO[i]);
            }
        }

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add all the contractClients to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendContractClientVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        ContractClientVO[] contractClientVO = (ContractClientVO[]) crud.retrieveVOFromDB(ContractClientVO.class, SegmentVO.class, segmentVO.getSegmentPK());

        if (contractClientVO != null)
        {
            segmentVO.setContractClientVO(contractClientVO);

            for (int i = 0; i < contractClientVO.length; i++)
            {
                boolean investmentsInLoop = true;
                if (voInclusionList.contains(ContractClientAllocationVO.class)) appendContractClientAllocationVO(contractClientVO[i]);
                if (voInclusionList.contains(ClientRoleVO.class)) associateClientRoleVO(contractClientVO[i]);
            }
        }

        voInclusionList.add(SegmentVO.class);
    }

    private void associateAgentVO(AgentHierarchyVO agentHierarchyVO)
    {
        voInclusionList.remove(AgentHierarchyVO.class);

        AgentVO agentVO = (AgentVO) crud.retrieveVOFromDB(AgentVO.class, agentHierarchyVO.getAgentFK());

        if (agentVO != null) agentHierarchyVO.setParentVO(AgentVO.class, agentVO);

        voInclusionList.add(AgentHierarchyVO.class);
    }

    /**
     * Add all the ContractClientAllocations to each ContractClient
     * @param segmentVO
     * @throws Exception
     */
    private void appendContractClientAllocationVO(ContractClientVO contractClientVO)
    {
        voInclusionList.remove(ContractClientVO.class);

        ContractClientAllocationVO[] contractClientAllocationVO = (ContractClientAllocationVO[]) crud.retrieveVOFromDB(ContractClientAllocationVO.class, ContractClientVO.class, contractClientVO.getContractClientPK());

        if (contractClientAllocationVO != null) contractClientVO.setContractClientAllocationVO(contractClientAllocationVO);

        voInclusionList.add(ContractClientVO.class);
    }

    /**
     * Add all the InvestmentAllocations to each Investment
     * @param segmentVO
     * @throws Exception
     */
    private void appendInvestmentAllocationVO(InvestmentVO investmentVO, boolean investmentsInLoop) throws Exception
    {
        voInclusionList.remove(InvestmentVO.class);

        if (investmentAllocationVO == null || investmentsInLoop)
        {
            investmentAllocationVO = (InvestmentAllocationVO[]) crud.retrieveVOFromDB(InvestmentAllocationVO.class, InvestmentVO.class, investmentVO.getInvestmentPK());
        }

        if (investmentAllocationVO != null) investmentVO.setInvestmentAllocationVO(investmentAllocationVO);

        voInclusionList.add(InvestmentVO.class);
    }

    /**
     * Add all the Buckets to each Investment
     * @param segmentVO
     * @throws Exception
     */
    private void appendBucketVO(InvestmentVO investmentVO) throws Exception
    {
        voInclusionList.remove(InvestmentVO.class);

        BucketVO[] bucketVO = (BucketVO[]) crud.retrieveVOFromDB(BucketVO.class, InvestmentVO.class, investmentVO.getInvestmentPK());
        if (bucketVO != null)
        {
            for (int b = 0; b < bucketVO.length; b++)
            {
                if (voInclusionList.contains(BucketAllocationVO.class)) appendBucketAllocationVO(bucketVO[b]);
                if (voInclusionList.contains(AnnualizedSubBucketVO.class)) appendAnnualizedSubBucketVO(bucketVO[b]);
            }
        }

        if (bucketVO != null) investmentVO.setBucketVO(bucketVO);

        voInclusionList.add(InvestmentVO.class);
    }

    /**
     * Add all the BucketAllocation to each Bucket
     * @param segmentVO
     * @throws Exception
     */
    private void appendBucketAllocationVO(BucketVO bucketVO) throws Exception
    {
        voInclusionList.remove(BucketVO.class);

        BucketAllocationVO[] bucketAllocationVO = (BucketAllocationVO[]) crud.retrieveVOFromDB(BucketAllocationVO.class, BucketVO.class, bucketVO.getBucketPK());

        if (bucketAllocationVO != null) bucketVO.setBucketAllocationVO(bucketAllocationVO);

        voInclusionList.add(BucketVO.class);
    }

    /**
     * Add all the nherent Riders to the SegmentVO for the requested segmentPK
     * @param segmentVO
     * @throws Exception
     */
    private void appendInherentRiderVO(SegmentVO segmentVO)
    {
         voInclusionList.remove(SegmentVO.class);

        if (inherentRiderVO == null)
        {
            inherentRiderVO = (InherentRiderVO[]) crud.retrieveVOFromDB(InherentRiderVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (inherentRiderVO != null)
        {
            segmentVO.setInherentRiderVO(inherentRiderVO);
        }

        voInclusionList.add(SegmentVO.class);
    }
    private void appendAnnualizedSubBucketVO(BucketVO bucketVO) throws Exception
    {
        voInclusionList.remove(BucketVO.class);
        //BucketAllocationVO[] bucketAllocationVO = (BucketAllocationVO[]) crud.retrieveVOFromDB(BucketAllocationVO.class, BucketVO.class, bucketVO.getBucketPK());
        AnnualizedSubBucketVO[] annualSubBucketVO= (AnnualizedSubBucketVO[]) crud.retrieveVOFromDB(AnnualizedSubBucketVO.class, BucketVO.class, bucketVO.getBucketPK());
        if (annualSubBucketVO != null) bucketVO.setAnnualizedSubBucketVO(annualSubBucketVO);
        voInclusionList.add(BucketVO.class);
    }
    /**
     * Add the Change History data to the SegmentVO, if any exist
     * @param segmentVO
     */
    private void appendChangeHistoryVO(SegmentVO segmentVO)
     {
         voInclusionList.remove(SegmentVO.class);

        if (changeHistoryVO == null)
        {
            changeHistoryVO = (ChangeHistoryVO[]) crud.retrieveVOFromDB(ChangeHistoryVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (changeHistoryVO != null)
        {
//            segmentVO.setChangeHistoryVO(changeHistoryVO);
        }

        voInclusionList.add(SegmentVO.class);
     }

    /**
     * Add the ContractRequirement data to segment vo, if it exists
     * @param segmentVO
     */
    private void appendContractRequirementVO(SegmentVO segmentVO)
    {
        voInclusionList.remove(SegmentVO.class);

        if (contractRequirementVO == null)
        {
            contractRequirementVO = (ContractRequirementVO[]) crud.retrieveVOFromDB(ContractRequirementVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (contractRequirementVO != null)
        {
            segmentVO.setContractRequirementVO(contractRequirementVO);
        }

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add the RMD data to segment vo, if it exists
     * @param segmentVO
     */
    private void appendRequiredMinDistributionVO(SegmentVO segmentVO)
    {
        voInclusionList.remove(SegmentVO.class);

        if (rmdVO == null)
        {
            rmdVO = (RequiredMinDistributionVO[]) crud.retrieveVOFromDB(RequiredMinDistributionVO.class,
                                                                        SegmentVO.class,
                                                                        segmentVO.getSegmentPK());
        }

        if (rmdVO != null)
        {
            segmentVO.setRequiredMinDistributionVO(rmdVO);
        }

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add the PremiumDue data to segment vo, if it exists
     * @param segmentVO
     */
    private void appendPremiumDueVO(SegmentVO segmentVO)
    {
        voInclusionList.remove(SegmentVO.class);

        if (premiumDueVO == null)
        {
            premiumDueVO = (PremiumDueVO[]) crud.retrieveVOFromDB(PremiumDueVO.class,
                                                           SegmentVO.class,
                                                           segmentVO.getSegmentPK());
        }

        if (premiumDueVO != null)
        {
            if (voInclusionList.contains(CommissionPhaseVO.class))
            {
                for (int i = 0; i < premiumDueVO.length; i++)
                {
                    CommissionPhaseVO[] commissionPhaseVO = (CommissionPhaseVO[]) crud.retrieveVOFromDB(CommissionPhaseVO.class,
                                                                                                        PremiumDueVO.class,
                                                                                                        premiumDueVO[i].getPremiumDuePK());

                    premiumDueVO[i].setCommissionPhaseVO(commissionPhaseVO);
                }
            }

            segmentVO.setPremiumDueVO(premiumDueVO);
        }

        voInclusionList.add(SegmentVO.class);
    }

    /**
     * Add all the Master to the SegmentVO for the requested segmentPK, this is the parent
     * @param segmentVO
     * @throws Exception
     */
//    private void associateMasterVO(SegmentVO segmentVO) throws Exception
//    {
//        if (segmentVO.getMasterFK() != 0)
//        {
//            voInclusionList.remove(SegmentVO.class);
//
//            MasterComposer composer = new MasterComposer(voInclusionList);
//
//            MasterVO masterVO = composer.compose(segmentVO.getMasterFK());
//
//            if (masterVO != null)
//            {
//                segmentVO.setParentVO(MasterVO.class, masterVO);
//
//                masterVO.addSegmentVO(segmentVO);
//            }
//
//            voInclusionList.add(SegmentVO.class);
//        }
//    }

    private void associateBillScheduleVO(SegmentVO segmentVO) throws Exception
    {
        if (segmentVO.getBillScheduleFK() != 0)
        {
            voInclusionList.remove(SegmentVO.class);

            BillScheduleVO billScheduleVO = (BillScheduleVO) crud.retrieveVOFromDB(BillScheduleVO.class, segmentVO.getBillScheduleFK());

            if (billScheduleVO != null)
            {
                segmentVO.setParentVO(BillScheduleVO.class, billScheduleVO);

                billScheduleVO.addSegmentVO(segmentVO);
            }

            voInclusionList.add(SegmentVO.class);
        }
    }

    private void associateClientRoleVO(ContractClientVO contractClientVO) throws Exception
    {
        voInclusionList.remove(ContractClientVO.class);

        ClientRoleComposer composer = new ClientRoleComposer(voInclusionList);

        ClientRoleVO clientRoleVO = composer.compose(contractClientVO.getClientRoleFK());

        if (clientRoleVO != null)
        {
            contractClientVO.setParentVO(ClientRoleVO.class, clientRoleVO);

            clientRoleVO.addContractClientVO(contractClientVO);
        }

        voInclusionList.add(ContractClientVO.class);
    }

    private void appendValueAtIssueVO(SegmentVO segmentVO) throws Exception
    {
        voInclusionList.remove(SegmentVO.class);

        if (valueAtIssueVO == null)
        {
            valueAtIssueVO = (ValueAtIssueVO[]) crud.retrieveVOFromDB(ValueAtIssueVO.class, SegmentVO.class, segmentVO.getSegmentPK());
        }

        if (valueAtIssueVO != null)
        {
            segmentVO.setValueAtIssueVO(valueAtIssueVO);
        }

        voInclusionList.add(SegmentVO.class);
    }

}
