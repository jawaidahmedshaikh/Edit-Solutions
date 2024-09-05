/*
 * User: dlataill
 * Date: Jan 5, 2005
 * Time: 12:27:42 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package codetable;

import edit.common.vo.*;
import contract.Segment;
import contract.ContractClient;
import client.ClientDetail;
import client.ClientAddress;

public class COIReplenishmentDocument extends PRASEDocument
{
    protected CoiReplenishmentVO coiReplenishmentVO;

    public COIReplenishmentDocument(String effectiveDate, SegmentVO segmentVO)
    {
        coiReplenishmentVO = new CoiReplenishmentVO();
        coiReplenishmentVO.setEffectiveDate(effectiveDate);
        composeBaseAndRiderSegmentVOs(segmentVO);
        setInsuredFields(segmentVO);
    }

    public void buildDocument()
    {

    }

    /**
     * Build the BaseSegmentVO and RiderSegmentVOs, including the insured client only.
     * @throws Exception
     */
    protected void composeBaseAndRiderSegmentVOs(SegmentVO segmentVO)
    {
        try
        {
            SegmentVO[] riderSegments = segmentVO.getSegmentVO();
            segmentVO.removeAllSegmentVO();

            ClientVO clientVO = super.buildInsuredClientVO(segmentVO);

            segmentVO.removeAllContractClientVO();

            BaseSegmentVO baseSegmentVO = new BaseSegmentVO();

            baseSegmentVO.setSegmentVO(segmentVO);
            baseSegmentVO.addClientVO(clientVO);

            coiReplenishmentVO.setBaseSegmentVO(baseSegmentVO);

            if (riderSegments != null)
            {
                for (int i = 0; i < riderSegments.length; i++)
                {
                    ClientVO riderClientVO = super.buildInsuredClientVO(riderSegments[i]);

                    riderSegments[i].removeAllContractClientVO();

                    RiderSegmentVO riderSegmentVO = new RiderSegmentVO();

                    riderSegmentVO.setSegmentVO(riderSegments[i]);
                    if (riderClientVO != null)
                    {
                        riderSegmentVO.addClientVO(riderClientVO);
                    }

                    coiReplenishmentVO.addRiderSegmentVO(riderSegmentVO);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("COI Replenishment Failed - Exception is: " + e.getMessage());

            coiReplenishmentVO = null;
        }
    }

    /**
     * Populates Insured fields.
     */
    private void setInsuredFields(SegmentVO segmentVO)
    {
        Segment segment = new Segment(segmentVO);

        // only life policies will have insured role.
        ClientDetail insured = segment.getInsured();

        if (insured != null)
        {
            coiReplenishmentVO.setInsuredGender(insured.getGenderCT());
            coiReplenishmentVO.setInsuredDateOfBirth(insured.getBirthDate() == null ? null : insured.getBirthDate().getFormattedDate());

            ClientAddress insuredAddress = insured.getPrimaryAddress();

            if (insuredAddress != null)
            {
                coiReplenishmentVO.setInsuredResidenceState(insuredAddress.getStateCT());
            }
        }

        ContractClient insuredContractClient = segment.getInsuredContractClient();

        if (insuredContractClient != null)
        {
            coiReplenishmentVO.setInsuredIssueAge(insuredContractClient.getIssueAge());
            coiReplenishmentVO.setInsuredClass(insuredContractClient.getClassCT());
            coiReplenishmentVO.setInsuredUnderwritingClass(insuredContractClient.getUnderwritingClassCT());
        }
    }

    /**
     * Returns the composed COIReplenishmentVO.
     * @return
     */
    public VOObject getDocumentAsVO()
    {
        return coiReplenishmentVO;
    }
}
