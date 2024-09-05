/*
 * User: dlataill
 * Date: June 1, 2006
 * Time: 10:05:09 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */
package codetable;

import client.*;

import contract.*;

import edit.common.vo.*;

import org.dom4j.*;

import org.dom4j.tree.*;

import java.util.*;

import java.math.BigDecimal;

import event.dm.composer.ClientComposer;

public class ProposalDocument extends PRASEDocument
{
    private Document document;
    private SegmentVO segmentVO;
    private Segment segment;
    private String proposalDate;
    private ProposalVO proposalVO;
    private NaturalDocVO naturalDocVO;
    private List clientVOArray = new ArrayList();

    public ProposalDocument(SegmentVO segmentVO, String proposalDate)
    {
        this.segmentVO = segmentVO;
        this.proposalDate = proposalDate;
    }

    /**
     * Build document for Proposal
     */
    public void buildDocument()
    {
        naturalDocVO = new NaturalDocVO();
        proposalVO = new ProposalVO();
        proposalVO.setProposalDate(proposalDate);
        segment = new Segment(segmentVO);

        try
        {
            document = new DefaultDocument();

            BaseSegmentVO baseSegmentVO = new BaseSegmentVO();
            baseSegmentVO.setSegmentVO(segmentVO);
            populateClientVOArray();
            baseSegmentVO.setClientVO((ClientVO[])clientVOArray.toArray(new ClientVO[clientVOArray.size()]));

            naturalDocVO.setBaseSegmentVO(baseSegmentVO);

            addRiderSegments();

            HashMap trxAccums = new HashMap();

            trxAccums = super.getTransactionAccumFields(null, null, segmentVO);

            setAccumsInDocument(trxAccums);

            setOwnersPremiumTaxState();

            setInsuredFields();

            proposalVO.setNaturalDocVO(naturalDocVO);

            Element proposalElement = VOObject.map(proposalVO);

            document.setRootElement(proposalElement);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Populate the ClientVO array to be included in the BaseSegmentVO
     * @return
     */
    private void populateClientVOArray() throws Exception
    {
        if (segmentVO.getContractClientVOCount() > 0)
        {
            ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

            for (int i = 0; i < contractClientVOs.length; i++)
            {
                ClientVO clientVO = new ClientComposer().compose(contractClientVOs[i]);
                clientVOArray.add(clientVO);
            }
        }
    }

    /**
     * Retrieves all rider segments for this segment
     * @return
     */
    private void addRiderSegments()
    {
        if (segmentVO.getSegmentVOCount() > 0)
        {
            SegmentVO[] riderSegments = segmentVO.getSegmentVO();

            for (int i = 0; i < riderSegments.length; i++)
            {
                RiderSegmentVO riderSegmentVO = new RiderSegmentVO();
                riderSegmentVO.setSegmentVO(riderSegments[i]);

                naturalDocVO.addRiderSegmentVO(riderSegmentVO);
            }
        }
    }

    /**
     * For the Accumulations performed now update the Natural Doc.  If the fields were null, zeros were
     * set into its value.
     * @param trxAccums
     * @param trxType
     */
    private void setAccumsInDocument(HashMap trxAccums)
    {
        naturalDocVO.setPremiumToDate((BigDecimal)trxAccums.get("PremiumToDate"));

        naturalDocVO.setPremiumYearToDate((BigDecimal)trxAccums.get("PremiumYearToDate"));

        naturalDocVO.setPremiumCalYearToDate((BigDecimal)trxAccums.get("PremiumCalYearToDate"));

        naturalDocVO.setNetWithdrawalsToDate((BigDecimal)trxAccums.get("WithDToDate"));

        naturalDocVO.setNetWithdrawalsYearToDate((BigDecimal)trxAccums.get("NetWithDYearToDate"));

        naturalDocVO.setCumInitialPremium((BigDecimal)trxAccums.get("CumInitialPrem"));

        naturalDocVO.setCum1035Premium((BigDecimal)trxAccums.get("Cum1035Prem"));

        naturalDocVO.setPremiumSinceLast7PayDate((BigDecimal)trxAccums.get("PremSinceLast7Pay"));

        naturalDocVO.setWithdrawalsSinceLast7PayDate((BigDecimal)trxAccums.get("WithDSinceLast7Pay"));

        naturalDocVO.setNumberTransfersPolYearToDate(((Integer)trxAccums.get("NumberWithDToDate")).intValue());

        naturalDocVO.setNumberWithdrawalsPolYearToDate(((Integer)trxAccums.get("NumberTransfersToDate")).intValue());
    }

    /**
     * Populates OwnersPremiumTaxState in NatauralDocVO.
     */
    private void setOwnersPremiumTaxState()
    {
        ClientDetail ownerClientDetail = segment.getOwner();

        ClientAddress ownerPrimaryAddress = ownerClientDetail.getPrimaryAddress();
        if (ownerPrimaryAddress != null)
        {
            naturalDocVO.setOwnersPremiumTaxState(ownerPrimaryAddress.getStateCT());
        }
        else
        {
            ClientAddress ownerBusinessAddress = ownerClientDetail.getBusinessAddress();
            if (ownerBusinessAddress != null)
            {
                naturalDocVO.setOwnersPremiumTaxState(ownerBusinessAddress.getStateCT());
            }
        }
    }

    /**
     * Populates Insured fields.
     */
    private void setInsuredFields()
    {
        // only life policies will have insured role.
        ClientDetail insured = segment.getInsured();

        if (insured != null)
        {
            naturalDocVO.setInsuredGender(insured.getGenderCT());
            naturalDocVO.setInsuredDateOfBirth(insured.getBirthDate() == null ? null : insured.getBirthDate().getFormattedDate());

            ClientAddress insuredAddress = insured.getPrimaryAddress();

            if (insuredAddress != null)
            {
                naturalDocVO.setInsuredResidenceState(insuredAddress.getStateCT());
            }
        }

        ContractClient insuredContractClient = segment.getInsuredContractClient();

        if (insuredContractClient != null)
        {
            naturalDocVO.setInsuredIssueAge(insuredContractClient.getIssueAge());
            naturalDocVO.setInsuredClass(insuredContractClient.getClassCT());
            naturalDocVO.setInsuredUnderwritingClass(insuredContractClient.getUnderwritingClassCT());
        }
    }

    public ProposalVO getProposalVO()
    {
        return this.proposalVO;
    }

    /**
     * Returns the composed ProposalVO.
     * @return
     */
    public VOObject getDocumentAsVO()
    {
        return proposalVO;
    }

    /**
     * The composed Dom4j Document.
     * @see #buildDocument(long, edit.common.EDITDate, String)
     * @return
     */
    public Document getDocument()
    {
        return document;
    }
}
