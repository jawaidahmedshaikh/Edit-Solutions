/**
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Aug 5, 2004
 * Time: 2:13:36 PM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package codetable;

import contract.Segment;
import edit.common.vo.*;
import event.financial.client.trx.ClientTrx;
import reinsurance.ContractTreaty;


public class TransactionProcessDocument extends NaturalDocument
{
    private ClientTrx clientTrx;

    public TransactionProcessDocument(ClientTrx clientTrx)
    {
        super();
        this.clientTrx = clientTrx;
        this.editTrxVO = clientTrx.getEDITTrxVO();

        setEDITTrxInclusionList();
        setSegmentInclusionList();
    }

    public TransactionProcessDocument(EDITTrxVO editTrxVO)
    {
        super();
        this.editTrxVO = editTrxVO;

        setEDITTrxInclusionList();
        setSegmentInclusionList();
    }

    /**
     * Build NaturalDocVO for Transaction Process
     */
    public void buildDocument()
    {
        try
        {
            super.composeEDITTrx();
            super.buildDocument();
            super.composeBaseAndRiderSegmentVOs();
            super.associateBillingInfo();
            super.setGroupInfo();

            SegmentVO segmentVO = super.getSegmentVO();

            if (clientTrx != null && clientTrx.hasReinsurance())
            {
                associateReinsuranceInformation();
            }

            super.checkForTransactionAccumGeneration();
            super.composeOverdueChargesRemaining();
        }
        catch (Exception e)
        {
//            Logger logger = Logging.getLogger(Logging.LOGGER_CONTRACT_BATCH);
//
//            LogEntryVO logEntryVO = new LogEntryVO();
//
//            logEntryVO.setMessage("Transaction Process Failed - " + e.getMessage());
//            logEntryVO.setHint("NaturalDocVO Was Not Built");
//
//            logger.error(logEntryVO);
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Transactions that are reinsurable have [possibly] Treaties mapped to the Segment as ContractTreaties. The
     * Reinsurance information is driven by these ContractTreaties.
     */
    public void associateReinsuranceInformation()
    {
        // This is assumed to be the ReinsuranceDocument at the base Segment level only. Riders would have to be added
        // in the future.
        BaseSegmentVO baseSegmentVO = naturalDocVO.getBaseSegmentVO();

        Segment segment = new Segment(baseSegmentVO.getSegmentVO());

        ContractTreaty[] contractTreaties = ContractTreaty.findBy_SegmentPK_V1(segment.getSegmentPK());

        if (contractTreaties != null)
        {
            for (int i = 0; i < contractTreaties.length; i++)
            {
                ContractTreaty contractTreaty = contractTreaties[i];

                if (contractTreaty.getStatus() == null)
                {
                    ReinsuranceDocument reinsuranceDocument = PRASEDocumentFactory.getSingleton().getReinsuranceDocument(contractTreaty);

                    reinsuranceDocument.buildDocument();

                    ReinsuranceVO reinsuranceVO = (ReinsuranceVO) reinsuranceDocument.getDocumentAsVO();

                    baseSegmentVO.addReinsuranceVO(reinsuranceVO);
                }
            }
        }
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
     * Set the Inclusion List for the Segment Composer.
     */
    public void setSegmentInclusionList()
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
    public void setEDITTrxInclusionList()
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
