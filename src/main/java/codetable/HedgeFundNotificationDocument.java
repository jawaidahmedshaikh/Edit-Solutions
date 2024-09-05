/*
 * User: dlataill
 * Date: Nov 5, 2004
 * Time: 8:58:50 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package codetable;

import edit.common.vo.*;

public class HedgeFundNotificationDocument extends NaturalDocument
{
    public HedgeFundNotificationDocument(EDITTrxCorrespondenceVO editTrxCorrespondenceVO)
    {
        super();

        setSegmentInclusionList();
        setEditTrxInclusionList();

        super.editTrxVO = (EDITTrxVO) editTrxCorrespondenceVO.getParentVO(EDITTrxVO.class);
    }

    public void buildDocument()
    {
        try
        {
            super.composeEDITTrx();
            super.buildDocument();
            super.composeBaseAndRiderSegmentVOs();
        }
        catch (Exception e)
        {
//            Logger logger = Logging.getLogger(Logging.LOGGER_CONTRACT_BATCH);
//
//            LogEntryVO logEntryVO = new LogEntryVO();
//
//            logEntryVO.setMessage("Hedge Fund Notification Process Failed - " + e.getMessage());
//            logEntryVO.setHint("NaturalDocVO Was Not Built");
//
//            logger.error(logEntryVO);
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Set the Inclusion List for the Segment Composer.
     */
    public void setSegmentInclusionList()
    {
        super.setSegmentInclusionList();
        segmentVOInclusionList.add(SegmentVO.class);
        segmentVOInclusionList.add(ContractClientVO.class);
    }

    /**
     * Set the Inclusion List for the EDITTrx Composer.
     */
    public void setEditTrxInclusionList()
    {
        super.setEDITTrxInclusionList();
        editTrxVOInclusionList.add(ClientSetupVO.class);
        editTrxVOInclusionList.add(ContractSetupVO.class);
        editTrxVOInclusionList.add(InvestmentAllocationOverrideVO.class);
        editTrxVOInclusionList.add(GroupSetupVO.class);
    }
}
