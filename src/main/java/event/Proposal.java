/*
 * User: dlataill
 * Date: Jun 1, 2006
 * Time: 12:21:47 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event;

import codetable.PRASEDocumentFactory;
import codetable.ProposalDocument;

import contract.Segment;

import edit.common.EDITDate;
import edit.common.vo.ProposalProjectionVO;
import edit.common.vo.ProposalVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.VOObject;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPOutput;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;


public class Proposal
{
    private SegmentVO segmentVO;
    private String proposalDate;

    public Proposal(SegmentVO segmentVO, String proposalDate)
    {
        this.segmentVO = segmentVO;
        this.proposalDate = proposalDate;
    }

    public ProposalVO getProposal() throws Exception
    {
        ProposalDocument proposalDocument = PRASEDocumentFactory.getSingleton().getProposalDocument(segmentVO, proposalDate);

        proposalDocument.buildDocument();

        ProposalVO proposalVO = null;

        try
        {
            Calculator calcComponent = new CalculatorComponent();

            Document currentDocument = proposalDocument.getDocument();

            Segment segment = new Segment(segmentVO);
            String eventType = segment.setEventTypeForDriverScript();

            SPOutput spOutput = calcComponent.processScriptWithDocument("ProposalVO", currentDocument, "Proposal",
                    "*", eventType, new EDITDate().getFormattedDate(),
                    segmentVO.getProductStructureFK(), true);

            VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

            List proposalProjectionVOs = new ArrayList();

            for (int i = 0; i < voObjects.length; i++)
            {
                VOObject voObject = voObjects[i];

                if (voObject instanceof ProposalProjectionVO)
                {
                    proposalProjectionVOs.add(voObject);
                }
            }

            proposalVO = proposalDocument.getProposalVO();

            for (int i = 0; i < proposalProjectionVOs.size(); i++)
            {
                proposalVO.addProposalProjectionVO((ProposalProjectionVO) proposalProjectionVOs.get(i));
            }

            return proposalVO;
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw e;
        }
    }
}
