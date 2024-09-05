/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp.custom.document;

import agent.*;

import contract.AgentHierarchy;
import contract.AgentHierarchyAllocation;
import contract.AgentSnapshot;
import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.*;

import edit.services.db.CRUD;
import edit.services.db.hibernate.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import role.ClientRole;
import role.ClientRoleFinancial;


/**
 * Each AgentHierarchy off of a Segment requires the building of its own
 * CommissionVO. The set of the CommissionVOs grouped together form the
 * CommissionDocument. The Document is structural (a few DB queries).
 * 
 * 1. There are no business/conditional rules to be applied at this time.
 */
public class CommissionDocument extends PRASEDocBuilder
{
    /**
     * The document-driving segmentPK.
     */
    private Long segmentPK;

    private EDITDate trxEffectiveDate;

    /**
     * Name for the SegmentPK building param.
     */
    public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK";

    /**
     * Name for the TrxEffectiveDate building param.
     */
    public static final String BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE = "TrxEffectiveDate";
    
    /**
     * The list of parameters names that will be used when building this document.
     */
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_SEGMENTPK, BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE};

    public CommissionDocument(){}

    /**
     * Constructor. Document is driven by the Segment.
     *
     * @param segmentPK
     */
    public CommissionDocument(Long segmentPK, EDITDate trxEffectiveDate)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, segmentPK.toString()).put(BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE, trxEffectiveDate.getFormattedDate()));

        this.segmentPK = segmentPK;

        this.trxEffectiveDate = trxEffectiveDate;
    }

    /**
     * Constuctor. Assumes that the specified building parameters contains
     * the required parameters of SegmentPK and TrxEffectiveDate.
     *
     * @param buildingParameters
     *
     * @see
     */
    public CommissionDocument(Map<String, String> buildingParameters)
    {
        super(buildingParameters);

        this.segmentPK = new Long(buildingParameters.get(BUILDING_PARAMETER_NAME_SEGMENTPK));

        this.trxEffectiveDate = new EDITDate(buildingParameters.get(BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE));
    }

    /**
     * Constructor. Document is driven by the Segment.
     *
     * @param segment
     */
    public CommissionDocument(Segment segment, EDITDate trxEffectiveDate)
    {
        this(segment.getSegmentPK(), trxEffectiveDate);
    }

    /**
     * Constructor. Document is driven by quote validation.
     *
     * @param segment
     */
    public CommissionDocument(SegmentVO segmentVO)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, new Long(segmentVO.getSegmentPK()).toString()).put(BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE, new EDITDate(segmentVO.getEffectiveDate()).getFormattedDate()));

        Element commissionDocumentElement = buildCommissionDocumentElement();

        buildCommissionElementFromCloudland(segmentVO, commissionDocumentElement);

        setRootElement(commissionDocumentElement);

        setDocumentBuilt(true);
    }


    /**
     * Builds the Document according to the following rules:
     * For each AgentHierarchy of a Segment:
     * For each AgentSnapshot of the AgentHierarchy
     * CommissionVO
     * CommissionVO.AgentSnapshotDetailVO
     * CommissionVO.AgentSnapshotDetailVO.AgentVO
     * CommissionVO.AgentSnapshotDetailVO.AgentVOCheckAdjustmentVO
     * CommissionVO.AgentSnapshotDetailVO.AgentContractVO
     * CommissionVO.AgentSnapshotDetailVO.CommissionProfileVO
     * CommissionVO.AgentSnapshotDetailVO.AdditionalCompensationVO
     * CommissionVO.AgentSnapshotDetailVO.ClientDetailVO
     * CommissionVO.AgentSnapshotDetailVO.ClientDetailVO.ClientRoleVO
     * CommissionVO.AgentSnapshotDetailVO.ClientDetailVO.ClientRoleVO.ClientRoleFinancialVO
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            // Used as the data source for the document.

            Element commissionDocumentElement = buildCommissionDocumentElement();
            
            Segment segment = Segment.findSeparateBy_SegmentPK_V1(getSegmentPK(), getTrxEffectiveDate());

            if (segment != null) // Should only be null if the Segment has no Agents.
            {
                buildCommissionElement(segment, commissionDocumentElement);
            }
            
            List<Segment> riders = Segment.findSeparateBy_SegmentFK_V1(getSegmentPK(), getTrxEffectiveDate());
            if (riders != null && riders.size() > 0) {
            	for (Segment riderSegment : riders) {
            		buildCommissionElement(riderSegment, commissionDocumentElement);
            	}
            }
            
            setRootElement(commissionDocumentElement);

            setDocumentBuilt(true);
        }
    }

    /**
     * Builds the Element version of the Segment. Flow is passed to its
     * children to continue the building process.
     *
     * @return
     *
     * @parem segment the document data source
     */
    private void buildCommissionElement(Segment segment, Element commissionDocumentElement)
    {
        Set<AgentHierarchy> agentHierarchies = segment.getAgentHierarchies();

        for (AgentHierarchy agentHierarchy : agentHierarchies)
        {
            Element commissionElement = buildCommissionVOElement(agentHierarchy);

            if (commissionElement != null)
            {
                commissionDocumentElement.add(commissionElement);

                buildAgentSnapshotDetailVO(agentHierarchy.getAgentSnapshots(), commissionElement);
            }
        }
    }

    public Long getSegmentPK()
    {
        return segmentPK;
    }

    /**
     * Effective Date of the currently executing EDITTrx.
     *
     * @return
     */
    public EDITDate getTrxEffectiveDate()
    {
        return trxEffectiveDate;
    }

    /**
     * Each AgentHierarchy drives the building of its own
     * Commission Element set with the AgentHierarchy.AllocationPercent
     * and a "fake" CommissionPK.
     * <P>
     * Should only create a commission element if the agent hierarchy has an active agentHierarchyAllocation AND its
     * allocationPercent is not equal to zero.
     *
     * @return a Commission Element with the AllocationPercent and CommissionPK set. Null if the agentHierarchy does
     * not have an active allocation or if the allocationPercent is equal to zero.
     */
    private Element buildCommissionVOElement(AgentHierarchy agentHierarchy)
    {
        Element commissionElement = null;

        AgentHierarchyAllocation agentHierarchyAllocation = AgentHierarchyAllocation.findSeparateActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), trxEffectiveDate);

        if (agentHierarchyAllocation != null)  // if trx is back dated, may not have an active allocation for that effective date
        {
            EDITBigDecimal allocationPercent = agentHierarchyAllocation.getAllocationPercent();

            //  Don't create commissions for allocation percents of zero
            if (! allocationPercent.isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
            {
                commissionElement = new DefaultElement("CommissionVO");

                Element commissionPKElement = new DefaultElement("CommissionPK");

                // The fake pk.
                commissionPKElement.setText(CRUD.getNextAvailableKey() + "");

                Element allocationPercentElement = new DefaultElement("AllocationPercent");

                allocationPercentElement.setText(allocationPercent.toString());

                commissionElement.add(commissionPKElement);

                commissionElement.add(allocationPercentElement);
            }
        }

        return commissionElement;
    }

    /**
     * The set of all CommissionElements are contained in a root document
     * called the CommissionDoc Element.
     *
     * @return
     */
    private Element buildCommissionDocumentElement()
    {
        return new DefaultElement("CommissionDocVO");
    }

    /**
     * Every AgentSnapshot within an AgentHierarchy has an AgentSnapshotDetail Element
     * built. This AgentSnapshotDetail combines disparate information into a
     * single informational structure.
     *
     * @param aSnapshots    the set of AgentSnapshots for the current AgentHierarchy represented by a Commission Element
     * @param commissionElement a representation of the AgentHierarchy
     */
    private void buildAgentSnapshotDetailVO(Set<AgentSnapshot> aSnapshots, Element commissionElement)
    {
        List<AgentSnapshot> agentSnapshots = sortByHierarchyLevel(aSnapshots);
    
        for (AgentSnapshot agentSnapshot : agentSnapshots)
        {
            setAgentSnapshotDetail(agentSnapshot, commissionElement);
        }
    }

    /**
     * The associated Agent via AgentSnapshot.PlacedAgent.AgentContract.Agent is added
     * to the specified AgentSnapshotDetail Element. Building continues with
     * CheckAdjustment.
     *
     * @param agent                      the AgentSnapshot.PlacedAgent.AgentContract.Agent
     * @param agentSnapshotDetailElement the containing Element
     */
    private void buildAgentElement(Agent agent, Element agentSnapshotDetailElement)
    {
        Element agentElement = agent.getAsElement();

        agentSnapshotDetailElement.add(agentElement);

        buildCheckAdjustment(agent.getCheckAdjustments(), agentElement);
    }

    /**
     * The associated CheckAdjustment (pre-filtered by the driving SQL query).
     *
     * @param checkAdjustments the associated CheckAdjustment
     * @param agentElement
     */
    private void buildCheckAdjustment(Set<CheckAdjustment> checkAdjustments, Element agentElement)
    {
        EDITDate currentDate = new EDITDate();

        for (CheckAdjustment checkAdjustment : checkAdjustments)
        {
            if ((checkAdjustment.getAdjustmentCompleteInd() != null &&
                 checkAdjustment.getAdjustmentCompleteInd().equalsIgnoreCase("Y")) ||
                (currentDate.before(checkAdjustment.getStartDate()) ||
                 currentDate.after(checkAdjustment.getStopDate())))
            {
                continue;
            }
            else
            {
                Element checkAdjustmentElement = checkAdjustment.getAsElement();

                agentElement.add(checkAdjustmentElement);
            }
        }
    }

    /**
     * The associated AgentContract via AgentSnapshot.PlacedAgent.AgentContract.
     *
     * @param agentContract              the associated AgentContract
     * @param agentSnapshotDetailElement the containing Element
     */
    private void buildAgentContractElement(AgentContract agentContract, Element agentSnapshotDetailElement)
    {
        agentSnapshotDetailElement.add(agentContract.getAsElement());
    }

    /**
     * The associated CommissionProfile from AgentSnapshot.PlacedAgent.PlacedAgentCommissionProfile.CommissionProfile where
     * CommissionProfile.StartDate <= TrxEffectiveDate <= CommissionProfile.StopDate.
     *
     * @param commissionProfile          the filtered CommissionProfile
     * @param agentSnapshotDetailElement the containing Element
     */
    private void buildCommissionProfileElement(CommissionProfile commissionProfile, Element agentSnapshotDetailElement)
    {
        agentSnapshotDetailElement.add(commissionProfile.getAsElement());
    }

    /**
     * The associated AdditionalCompensations from AgentSnapshot.PlacedAgent.AgentContract.AdditionalCompensation.
     *
     * @param additionalCompensations    the associated AdditionalCompensations
     * @param agentSnapshotDetailElement the containing Element
     */
    private void buildAdditionalCompensationElement(Set<AdditionalCompensation> additionalCompensations, Element agentSnapshotDetailElement)
    {
        for (AdditionalCompensation additionalCompensation : additionalCompensations)
        {
            agentSnapshotDetailElement.add(additionalCompensation.getAsElement());
        }
    }

    /**
     * The associated AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole.ClientDetail.
     * The building process continues with ClientRole.
     *
     * @param clientRole                 the child ClientRole from which the parent ClientDetail will be retrieved
     * @param agentSnapshotDetailElement the containing Element
     */
    private void buildClientDetailElement(ClientRole clientRole, Element agentSnapshotDetailElement)
    {
        Element clientDetailElement = clientRole.getClientDetail().getAsElement();

        agentSnapshotDetailElement.add(clientDetailElement);

        buildClientRoleElement(clientRole, clientDetailElement);
    }

    /**
     * The associated AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole where
     * the ClientRole.RoleTypeCT = 'Agent'. Building continues with ClientRoleFinancial.
     *
     * @param clientRole          the targeted ClientRole
     * @param clientDetailElement the containing Element
     */
    private void buildClientRoleElement(ClientRole clientRole, Element clientDetailElement)
    {
        Element clientRoleElement = clientRole.getAsElement();

        clientDetailElement.add(clientRoleElement);

        buildClientRoleFinancialElement((ClientRoleFinancial) clientRole.getClientRoleFinancials().iterator().next(), clientRoleElement);
    }

    /**
     * The associated AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole.ClientRoleFinancial.
     *
     * @param clientRoleFinancial the targeted ClientRoleFinancial
     * @param clientRoleElement   the containing Element
     */
    private void buildClientRoleFinancialElement(ClientRoleFinancial clientRoleFinancial, Element clientRoleElement)
    {
        clientRoleElement.add(clientRoleFinancial.getAsElement());
    }

    /**
     * @return
     */
    public String getRootElementName()
    {
        return "CommissionDocVO";
    }

  /**
   * The AgentSnapshots need to be sorted by HierarchyLevel descending.
   * @param agentSnapshots
   */
  private List<AgentSnapshot> sortByHierarchyLevel(Set<AgentSnapshot> agentSnapshots)
  {
    List<AgentSnapshot> sortedSnapshots = new ArrayList<AgentSnapshot>(agentSnapshots);
  
    Collections.sort(sortedSnapshots); // sort ascending
    
    Collections.reverse(sortedSnapshots); // and now descending
    
    return sortedSnapshots;
  }

  /**
   * Gets the total commissions paid to the specified AgentSnapshot and adds it to its parent element.
   * @param agentSnapshot
   * @param agentSnapshotDetailElement
   */
  private void buildTotalCommissionsPaidElement(AgentSnapshot agentSnapshot, Element agentSnapshotDetailElement)
  {
    EDITBigDecimal totalCommissionsPaid = agentSnapshot.getTotalCommissionsPaid();
    
    Element totalCommissionsPaidElement = new DefaultElement("TotalCommissionsPaid");
    
    totalCommissionsPaidElement.setText(totalCommissionsPaid.toString());
    
    agentSnapshotDetailElement.add(totalCommissionsPaidElement);
  }

    /**
     * Gets the AdvanceAmount for the specified AgentSnapshot and adds it to its parent element.
     * @param agentSnapshot
     * @param agentSnapshotDetailElement
     */
    private void buildAdvanceAmountElement(AgentSnapshot agentSnapshot, Element agentSnapshotDetailElement)
    {
        EDITBigDecimal advanceAmount = agentSnapshot.getAdvanceAmount();

        Element advanceAmountElement = new DefaultElement("AdvanceAmount");

        advanceAmountElement.setText(advanceAmount.toString());

        agentSnapshotDetailElement.add(advanceAmountElement);
    }

    /**
     * Gets the AdvanceRecovery for the specified AgentSnapshot and adds it to its parent element.
     * @param agentSnapshot
     * @param agentSnapshotDetailElement
     */
    private void buildAdvanceRecoveryElement(AgentSnapshot agentSnapshot, Element agentSnapshotDetailElement)
    {
        EDITBigDecimal advanceRecovery = agentSnapshot.getAdvanceRecovery();

        Element advanceRecoveryElement = new DefaultElement("AdvanceRecovery");

        advanceRecoveryElement.setText(advanceRecovery.toString());

        agentSnapshotDetailElement.add(advanceRecoveryElement);
    }

    /**
     * For Quote Validation processing the Cloudland methods will be used to build the Commission Document
     * @param segmentVO
     * @param commissionDocumentElement
     */
    private void buildCommissionElementFromCloudland(SegmentVO segmentVO, Element commissionDocumentElement)
    {
        AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();

        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
            Element commissionElement = buildCommissionVOElementFromCloudland(agentHierarchyVOs[i]);

            commissionDocumentElement.add(commissionElement);

            buildAgentSnapshotDetailVOFromCloudland(agentHierarchyVOs[i].getAgentSnapshotVO(), commissionElement);
        }

    }

    /**
     * Use the AgentHierarchy in the segmentVO passed in to build the Commission Document from cloudland data.
     * @param agentHierarchyVO
     * @return
     */
    private Element buildCommissionVOElementFromCloudland(AgentHierarchyVO agentHierarchyVO)
    {
        Element commissionElement = null;

        AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = agentHierarchyVO.getAgentHierarchyAllocationVO();

        for (int i = 0; i < agentHierarchyAllocationVOs.length; i++)
        {
            EDITBigDecimal allocationPercent = new EDITBigDecimal(agentHierarchyAllocationVOs[i].getAllocationPercent());

            commissionElement = new DefaultElement("CommissionVO");

            Element commissionPKElement = new DefaultElement("CommissionPK");

            // The fake pk.
            commissionPKElement.setText(CRUD.getNextAvailableKey() + "");

            Element allocationPercentElement = new DefaultElement("AllocationPercent");

            allocationPercentElement.setText(allocationPercent.toString());

            commissionElement.add(commissionPKElement);

            commissionElement.add(allocationPercentElement);
        }

        return commissionElement;
    }

    /**
     * Using the agentSnapshots in the segmentVO, build the Commission Document.
     * @param agentSnapshotVOs
     * @param commissionElement
     */
    private void buildAgentSnapshotDetailVOFromCloudland(AgentSnapshotVO[] agentSnapshotVOs, Element commissionElement)
    {
        for (AgentSnapshotVO agentSnapshotVO : agentSnapshotVOs)
        {
            AgentSnapshot agentSnapshot = (AgentSnapshot)SessionHelper.map(agentSnapshotVO, SessionHelper.EDITSOLUTIONS);

            PlacedAgentVO placedAgentVO = (PlacedAgentVO)agentSnapshotVO.getParentVO(PlacedAgentVO.class);
            agentSnapshot.setPlacedAgent((PlacedAgent)SessionHelper.map(placedAgentVO, SessionHelper.EDITSOLUTIONS));

            setAgentSnapshotDetail(agentSnapshot, commissionElement);
        }
    }

    /**
     * Shared agentSnapshot building
     * @param agentSnapshot
     * @param commissionElement
     */
    private void setAgentSnapshotDetail(AgentSnapshot agentSnapshot, Element commissionElement)
    {
            Element agentSnapshotDetailElement = new DefaultElement("AgentSnapshotDetailVO");

            commissionElement.add(agentSnapshotDetailElement);

            // AgentGroupFK
            Element agentGroupFKElement = new DefaultElement("AgentGroupFK");

            agentGroupFKElement.setText(CRUD.getNextAvailableKey() + "");

            // AgentSnapshotDetailPK
            Element agentSnapshotDetailPKElement = new DefaultElement("AgentSnapshotDetailPK");

            agentSnapshotDetailPKElement.setText(CRUD.getNextAvailableKey() + "");

            // AgentShapshotPK
            Element agentSnapshotPKElement = new DefaultElement("AgentSnapshotPK");

            agentSnapshotPKElement.setText(agentSnapshot.getAgentSnapshotPK().toString());

            //PlacedAgentFK
            Element placedAgentFKElement = new DefaultElement("PlacedAgentFK");

            placedAgentFKElement.setText(agentSnapshot.getPlacedAgentFK().toString());

            // CommissionOverrideAmount
            Element commissionOverrideAmountElement = new DefaultElement("CommissionOverrideAmount");

            commissionOverrideAmountElement.setText(agentSnapshot.getCommissionOverrideAmount().toString());

            // CommissionOverridePercent
            Element commissionOverridePercentElement = new DefaultElement("CommissionOverridePercent");

            commissionOverridePercentElement.setText(agentSnapshot.getCommissionOverridePercent().toString());

            // RecoverPercent
            Element recoveryPercentElement = new DefaultElement("RecoveryPercent");
            recoveryPercentElement.setText(agentSnapshot.getRecoveryPercent().toString());

            //AdvancePercent
            Element advancePercentElement = new DefaultElement("AdvancePercent");
            advancePercentElement.setText(agentSnapshot.getAdvancePercent().toString());

            // Add the Elements together.
            agentSnapshotDetailElement.add(agentGroupFKElement);

            agentSnapshotDetailElement.add(agentSnapshotDetailPKElement);

            agentSnapshotDetailElement.add(agentSnapshotPKElement);

            agentSnapshotDetailElement.add(placedAgentFKElement);

            agentSnapshotDetailElement.add(commissionOverrideAmountElement);

            agentSnapshotDetailElement.add(commissionOverridePercentElement);
            agentSnapshotDetailElement.add(recoveryPercentElement);
            agentSnapshotDetailElement.add(advancePercentElement);

            // Keep the building process going.

            buildTotalCommissionsPaidElement(agentSnapshot, agentSnapshotDetailElement);

            buildAdvanceAmountElement(agentSnapshot, agentSnapshotDetailElement);

            buildAdvanceRecoveryElement(agentSnapshot, agentSnapshotDetailElement);

            buildAgentElement(agentSnapshot.getPlacedAgent().getAgentContract().getAgent(), agentSnapshotDetailElement);

            buildAgentContractElement(agentSnapshot.getPlacedAgent().getAgentContract(), agentSnapshotDetailElement);

            buildCommissionProfileElement((agentSnapshot.getPlacedAgent().getPlacedAgentCommissionProfiles().iterator().next()).getCommissionProfile(), agentSnapshotDetailElement);

            buildAdditionalCompensationElement(agentSnapshot.getPlacedAgent().getAgentContract().getAdditionalCompensations(), agentSnapshotDetailElement);

            buildClientDetailElement(agentSnapshot.getPlacedAgent().getClientRole(), agentSnapshotDetailElement);
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
