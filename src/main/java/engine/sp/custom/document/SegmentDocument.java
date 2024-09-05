package engine.sp.custom.document;

import billing.BillSchedule;

import contract.*;

import edit.common.*;

import event.CommissionablePremiumHistory;

import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import fission.utility.*;


/**
 * Contains the Segment and associated entities.
 * The following builing rules apply:
 * 
 * 1. The following entities will always be included iff present:
 /**
 * Builds the following Segment structure:
 * 
 * SegmentDocVO
 * SegmentDocVO.SegmentVO
 * SegmentDocVO.SegmentVO.RequiredMinDistributionVO
 * SegmentDocVO.SegmentVO.DepositsVO
 * SegmentDocVO.SegmentVO.PayoutVO
 * SegmentDocVO.LifeVO
 * 
 * There are no rules associated with the building of this structure.
 */
public class SegmentDocument extends PRASEDocBuilder
{
    /**
     * The driving SegmentPK.
     */
    private Long segmentPK;

    /**
     * The driving Segment.
     */
    private Segment drivingSegment;

    /**
     * The named param used to build this Document.
     */
    public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK";
    
    /**
     * A name parameter where, if it exists, determines if PremiumDue records are included in the
     * document.
     */
    public static final String BUILDING_PARAMETER_NAME_INCLUDE_PREMIUM_DUE = "IncludePremiumDue";
    
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_SEGMENTPK,
                                                            BUILDING_PARAMETER_NAME_INCLUDE_PREMIUM_DUE};

    /**
     * A flag to be set in working storage iff the additional tables of PremiumDue, 
     * CommissionPhase, and CommissionablePremiumHistory are to be added to this
     * document.
     */
    public static final String INCLUDE_PREMIUM_DUE = "IncludePremiumDue";
    
    public static final String ROOT_ELEMENT_NAME = "SegmentDocVO";
    
    public SegmentDocument(){}

    public SegmentDocument(Long segmentPK)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, segmentPK.toString()));

        this.segmentPK = segmentPK;
    }

    public SegmentDocument(Map<String, String> buildingParameters)
    {
        super(buildingParameters);

        this.segmentPK = new Long(buildingParameters.get(SegmentDocument.BUILDING_PARAMETER_NAME_SEGMENTPK));
    }
    
    /**
     * Constructor. Uses the specified Document as the "built" document.
     * The SegmentPK is assumed to be 0 and acts as the building parameter.
     * @param segmentVOElement
     */
    public SegmentDocument(Element segmentVOElement)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, "0").put(BUILDING_PARAMETER_NAME_INCLUDE_PREMIUM_DUE, "Y"));
        
        Element rootElement = new DefaultElement(getRootElementName());
        
        rootElement.add(segmentVOElement);
        
        setRootElement(rootElement);
        
        setDocumentBuilt(true);
    }

    /**
     * Constructor that takes an external data source as a Segment.
     * @param drivingSegment
     */
    public SegmentDocument(Segment drivingSegment)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, drivingSegment.getSegmentPK().toString()));

        this.drivingSegment = drivingSegment;

        segmentPK = drivingSegment.getSegmentPK();
    }

    /**
     * Builds the Segment.Investment.Bucket structure and other associated entities.
     * 
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element segmentDocumentElement = buildSegmentDocumentElement();

            setRootElement(segmentDocumentElement);

            setDocumentBuilt(true);
        }
    }

    /**
     * Getter.
     * @see #segmentPK
     * @return
     */
    public Long getSegmentPK()
    {
        return segmentPK;
    }

    /**
     * Builds the root of this Document and continues with the building of Segment.
     * @param
     * @return
     */
    private Element buildSegmentDocumentElement()
    {
        Element segmentDocElement = new DefaultElement(getRootElementName());

        Segment segment = getDrivingSegment();

        buildSegmentElement(segment, segmentDocElement);

        return segmentDocElement;
    }

    /**
     * Builds the Segment Element and continues with Investment.
     * @param segment the data source for the Element
     * @param segmentDocElement the containing Element
     */
    private void buildSegmentElement(Segment segment, Element segmentDocElement)
    {
        Element segmentElement = segment.getAsElement();

        segmentDocElement.add(segmentElement);

        buildRequiredMinDistributionElement(segment.getRequiredMinDistributions(), segmentElement);

        buildPayoutElement(segment.getPayouts(), segmentElement);

        buildDepositElement(segment.getDeposits(), segmentElement);

        buildLifeElement(segment.getLifes(), segmentElement);
        
        if (includePremiumDue())
        {
            buildPremiumDueElement(segment, segment.getLife(), segmentElement);
        }
        
        buildBillScheduleElement(segment.getBillSchedule(), segmentElement);

        buildInherentRiderElement(segment.getInherentRiders(), segmentElement);

        buildRiderSegmentElement(segment.getSegments(), segmentElement);
    }


    /**
     * Builds the RequiredMinDistribution (at most one) and adds itself to the specified SegmentElement.
     * @param requiredMinDistributions there are at most one in the Set
     * @param segmentElement
     */
    private void buildRequiredMinDistributionElement(Set<RequiredMinDistribution> requiredMinDistributions, Element segmentElement)
    {
        for (RequiredMinDistribution requiredMinDistribution: requiredMinDistributions)
        {
            Element requiredMinDistributionElement = requiredMinDistribution.getAsElement();

            segmentElement.add(requiredMinDistributionElement);
        }
    }

    /**
     * Builds the Payout Element (at most one).
     * @param payouts
     * @param segmentElement
     */
    private void buildPayoutElement(Set<Payout> payouts, Element segmentElement)
    {
        for (Payout payout: payouts)
        {
            Element payoutElement = payout.getAsElement();

            segmentElement.add(payoutElement);
        }
    }

    /**
     * Builds the Deposit Element and adds itself to the Segment Element.
     * @param deposits
     * @param segmentElement
     */
    private void buildDepositElement(Set<Deposits> deposits, Element segmentElement)
    {
        for (Deposits deposit: deposits)
        {
            Element depositElement = deposit.getAsElement();

            segmentElement.add(depositElement);
        }
    }

    /**
     * Builds the Life Element and adds itself to the Segment Element.
     * @param lifes there are at most one Life in the Set
     * @param segmentElement
     */
    private void buildLifeElement(Set<Life> lifes, Element segmentElement)
    {
        for (Life life: lifes)
        {
            Element lifeElement = life.getAsElement();

            segmentElement.add(lifeElement);
        }
    }

    /**
     * It is possible that the driving Segment was supplied externally.
     * @return
     */
    private Segment getDrivingSegment()
    {
        if (drivingSegment == null)
        {
//            if (includePremiumDue())
//            {
//                drivingSegment = Segment.findSeparateBy_SegmentPK_VTWO(getSegmentPK());
//            }
//            else
//            {
                drivingSegment = Segment.findSeparateBy_SegmentPK_VONE(getSegmentPK());
//            }
        }

        return drivingSegment;
    }

    /**
     * 
     * @return
     */
    public String getRootElementName()
    {
        return ROOT_ELEMENT_NAME;
    }

    /**
     * True if working storage contains the IncludePremiumDue key.
     * @return
     */
    private boolean includePremiumDue()
    {
        boolean includePremiumDue = false;
        
        String includePremiumDueValue = getBuildingParameters().get(INCLUDE_PREMIUM_DUE);
        
        if (includePremiumDueValue != null && includePremiumDueValue.equalsIgnoreCase("Y"))
        {
            includePremiumDue = true;
        }
        return includePremiumDue;
    }

    /**
     * Builds the PremiumDue element, adds itself to the SegmentElement and
     * continues with the CommissionPhase elements.
     * @param premiumDues
     * @param segmentElement
     */
    private void buildPremiumDueElement(Segment segment, Life life, Element segmentElement)
    {
        Long segmentPK = segment.getSegmentPK();
        EDITDate paidToDate =life.getPaidToDate();

        if (paidToDate == null)
        {
            paidToDate = segment.getEffectiveDate();
        }

        PremiumDue[] activePremiumDues = PremiumDue.findActiveSeparateBySegmentPK(segmentPK, paidToDate);
        PremiumDue[] futurePRemiumDues = PremiumDue.findFutureSeparateBySegmentPK(segmentPK, paidToDate);
        PremiumDue[] premiumDues = (PremiumDue[])Util.joinArrays(activePremiumDues, futurePRemiumDues, PremiumDue.class);

        if (premiumDues != null)
        {
            for (PremiumDue premiumDue:premiumDues)
            {
                Element premiumDueElement = premiumDue.getAsElement();

                segmentElement.add(premiumDueElement);

                buildCommissionPhaseElement(premiumDue.getCommissionPhases(), premiumDueElement);
            }
        }
    }

    /**
     * Builds the CommissionPhase element(s), adds itself to its parent premiumDueElement, and continues
     * with the CommissionablePremiumHistory element(s).
     * @param commissionPhases
     */
    private void buildCommissionPhaseElement(Set<CommissionPhase> commissionPhases, Element premiumDueElement)
    {
        for (CommissionPhase commissionPhase:commissionPhases)
        {
            Element commissionPhaseElement = commissionPhase.getAsElement();
            
            premiumDueElement.add(commissionPhaseElement);
            
            buildCommissionablePremiumHistoryElement(commissionPhase.getCommissionablePremiumHistories(), commissionPhaseElement);
        }
    }

    /**
     * Builds the CommissionablePremiumHistory element, and adds itself to its parent CommissioPhase element.
     * @param commissionablePremiumHistories
     */
    private void buildCommissionablePremiumHistoryElement(Set<CommissionablePremiumHistory> commissionablePremiumHistories, Element commissionPhaseElement)
    {
        for (CommissionablePremiumHistory commissionablePremiumHistory:commissionablePremiumHistories)
        {
            Element commissionablePremiumHistoryElement = commissionablePremiumHistory.getAsElement();
            
            commissionPhaseElement.add(commissionablePremiumHistoryElement);
        }
    }
    
    /**
     * Builds the BillSchedule element, and adds itself to Segment element.
     * @param billSchedule
     * @param segmentElement
     */
    private void buildBillScheduleElement(BillSchedule billSchedule, Element segmentElement)
    {
        // A segment may or may not have BillSchedule
        if (billSchedule != null)
        {
            // Add BillSchedule that are not 'List' type
            if (!billSchedule.getBillMethodCT().equals(BillSchedule.BILL_METHOD_LISTBILL))
            {
                Element billScheduleElement = billSchedule.getAsElement();
                
                segmentElement.add(billScheduleElement);
            }
        }
    }

    /**
     * Builds the InherentRider Element and adds itself to the Segment Element.
     * @param inherentRiders
     * @param segmentElement
     */
    private void buildInherentRiderElement(Set<InherentRider> inherentRiders, Element segmentElement)
    {
        for (InherentRider inherentRider: inherentRiders)
        {
            Element inherentRiderElement = inherentRider.getAsElement();

            segmentElement.add(inherentRiderElement);
        }
    }

    private void buildRiderSegmentElement(Set<Segment> riderSegments, Element segmentElement)
    {
        for (Segment riderSegment: riderSegments)
        {
            Element riderElement = riderSegment.getAsElement();

            segmentElement.add(riderElement);

            buildValueAtIssueElement(riderSegment.getValueAtIssues(), riderElement);
        }
    }

    private void buildValueAtIssueElement(Set<ValueAtIssue> valueAtIssues, Element riderElement)
    {
        for (ValueAtIssue valueAtIssue: valueAtIssues)
        {
            Element valueAtIssueElement = valueAtIssue.getAsElement();

            riderElement.add(valueAtIssueElement);
        }
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
