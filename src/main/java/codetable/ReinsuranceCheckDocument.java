/*
 * User: gfrosti
 * Date: Dec 2, 2004
 * Time: 1:34:38 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package codetable;

import contract.*;

import edit.common.vo.*;

import event.*;

import event.financial.client.trx.*;

import reinsurance.*;


public class ReinsuranceCheckDocument extends PRASEDocument
{
    private ReinsuranceCheckDocVO reinsuranceCheckDocVO;
    private ClientTrx clientTrx;

    public ReinsuranceCheckDocument(ClientTrx clientTrx)
    {
        this.clientTrx = clientTrx;

        reinsuranceCheckDocVO = new ReinsuranceCheckDocVO();
    }

    /**
     * Builds an instance of NaturalDocVO containing the following VOs only:
     * a) GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO chain,
     * b) SegmentVO, and
     * c) ReinsuranceVO.
     * Additionally, the TreatyGroup.GroupNumber and Reinsurer.ReinsurerNumber are supplied.
     * @see PRASEDocument#buildDocument()
     */
    public void buildDocument()
    {
        ClientSetup clientSetup = clientTrx.getClientSetup();
        ContractSetup contractSetup = clientSetup.get_ContractSetup();
        GroupSetup groupSetup = contractSetup.get_GroupSetup();

        GroupSetupVO groupSetupVO = groupSetup.getAsVO();
        ContractSetupVO contractSetupVO = (ContractSetupVO) contractSetup.getVO();
        ClientSetupVO clientSetupVO = (ClientSetupVO) clientSetup.getVO();
        EDITTrxVO editTrxVO = clientTrx.getEDITTrxVO();

        groupSetupVO.addContractSetupVO(contractSetupVO);
        contractSetupVO.addClientSetupVO(clientSetupVO);
        clientSetupVO.addEDITTrxVO(editTrxVO);

        Treaty treaty = clientSetup.getTreaty();
        TreatyGroup treatyGroup = treaty.getTreatyGroup();
        Reinsurer reinsurer = treaty.getReinsurer();

        Segment segment = contractSetup.getSegment();

        SegmentVO segmentVO = (SegmentVO) segment.getVO();

        TreatyVO treatyVO = (TreatyVO) treaty.getVO();

        reinsuranceCheckDocVO.addTreatyVO(treatyVO);
        reinsuranceCheckDocVO.setTreatyGroupNumber(treatyGroup.getTreatyGroupNumber());
        reinsuranceCheckDocVO.setReinsurerNumber(reinsurer.getReinsurerNumber());
        reinsuranceCheckDocVO.addGroupSetupVO(groupSetupVO);
        reinsuranceCheckDocVO.addSegmentVO(segmentVO);
    }

    /**
     * Returns the VO representation of this document.
     * @return
     */
    public VOObject getDocumentAsVO()
    {
        return reinsuranceCheckDocVO;
    }
}
