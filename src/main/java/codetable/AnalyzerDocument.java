/**
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Aug 5, 2004
 * Time: 1:28:25 PM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package codetable;

import edit.common.vo.*;
import event.financial.group.strategy.SaveGroup;
import event.financial.client.trx.ClientTrx;
import event.dm.dao.DAOFactory;
import org.apache.logging.log4j.Logger;
import contract.*;
import reinsurance.*;


public class AnalyzerDocument extends NaturalDocument 
{
    private String processName = null;
    private String optionCode = null;
    private long productStructureFK = 0;
    private InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO = null;

    public AnalyzerDocument(GroupSetupVO groupSetupVO,
                             EDITTrxVO editTrxVO,
                              String processName,
                               String optionCode,
                                long productStructureFK)
    {
        super();
        super.groupSetupVO = groupSetupVO;
        super.editTrxVO = editTrxVO;
        this.processName = processName;
        this.optionCode = optionCode;
        this.productStructureFK = productStructureFK;

        setSegmentInclusionList();
    }

    /**
     * Build NaturalDocVO for Analyzer
     */
    public void buildDocument()
    {
        try
        {
            // A. Build the GroupSetupVO
            SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrxVO, processName, optionCode);

            saveGroup.build();
            super.setGroupSetupVO(saveGroup.getGroupSetupVO());
            super.buildDocument();
            super.setGroupInfo();
            super.composeBaseAndRiderSegmentVOs();
            super.associateBillingInfo();

            //Check for reinsurance
            ClientTrx clientTrx = new ClientTrx(editTrxVO);

            if (clientTrx.hasReinsurance())
            {
                associateReinsuranceInformation();
            }

            super.checkForTransactionAccumGeneration();
            super.composeOverdueChargesRemaining();
            super.retainCorrectCharges();
        }
        catch (Exception e)
        {
//            Logger logger = Logging.getLogger(Logging.LOGGER_CONTRACT_BATCH);
//
//            LogEntryVO logEntryVO = new LogEntryVO();
//
//            LogMessageVO logMessageVO = new LogMessageVO();
//
//            logMessageVO.setMessage("Analyzer Failed - " + e.getMessage());
//
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
    private void associateReinsuranceInformation()
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
}
