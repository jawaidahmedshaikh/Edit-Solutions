/*
 * User: gfrosti
 * Date: May 2, 2005
 * Time: 9:30:22 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import client.*;
import contract.*;
import edit.common.*;
import edit.common.vo.*;
import engine.*;
import event.*;



public class BonusStatementLine
{
    private BonusCommissionHistory bonusCommissionHistory;
    private String agentNumber;
    private String agentName;
    private String productType;
    private String policyNumber;
    private String ownerName;
    private EDITBigDecimal bonusPremium;
    private String commissionType;
    private EDITDate effectiveDate ;
    private EDITDateTime processDate;
    private EDITBigDecimal allocationPercent;

    public BonusStatementLine(BonusCommissionHistory bonusCommissionHistory)
    {
        this.bonusCommissionHistory = bonusCommissionHistory;
    }

    /**
     * Generates AgentNumber, AgentName, ProductType, Owner's Name, PolicyNumber, BonusComm
     */
    public void generateBonusStatementLine()
    {
        // Required Entities
        PlacedAgent placedAgent = bonusCommissionHistory.get_CommissionHistory().get_PlacedAgent();
        Agent agent = placedAgent.get_AgentContract().get_Agent();

        ClientDetail agentClientDetail = placedAgent.getClientRole().get_ClientDetail();

        CommissionHistory commissionHistory = bonusCommissionHistory.getCommissionHistory();

        EDITTrxHistory editTrxHistory = commissionHistory.getEDITTrxHistory();

        EDITTrx editTrx = editTrxHistory.getEDITTrx();

        FinancialHistory financialHistory = editTrxHistory.getFinancialHistory();

        Segment segment = editTrx.getClientSetup().getContractSetup().getSegment();

        ProductStructure productStructure = segment.getProductStructure();

        AgentHierarchy agentHierarchy = AgentHierarchy.findBy_SegmentPK_AND_PlacedAgentPK(segment.getPK(), placedAgent.getPK());

        // Required Fields
        agentNumber = agent.getAgentNumber();
        agentName = agentClientDetail.getName();
        productType = productStructure.getBusinessContractName();
        ownerName = segment.getOwner().getName();
        policyNumber = segment.getContractNumber();

        bonusPremium = bonusCommissionHistory.getAmount();
        commissionType = "B";

        effectiveDate = editTrx.getEffectiveDate();
        processDate = editTrxHistory.getProcessDateTime();

        AgentHierarchyAllocation agentHierarchyAllocation = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(
                    agentHierarchy.getAgentHierarchyPK(), editTrx.getEffectiveDate());

        allocationPercent = agentHierarchyAllocation.getAllocationPercent();
    }

    /**
     * The VO (read only) representation of this entity.
     * @return
     */
    public BonusStatementLineVO getBonusStatementLineVO()
    {
        BonusStatementLineVO bonusStatementLineVO = new BonusStatementLineVO();

        bonusStatementLineVO.setAgentNumber(agentNumber);
        bonusStatementLineVO.setAgentName(agentName);
        bonusStatementLineVO.setProductType(productType);
        bonusStatementLineVO.setOwnerName(ownerName);
        bonusStatementLineVO.setPolicyNumber(policyNumber);

        //can be a negative amount
        bonusStatementLineVO.setBonusPremium(bonusPremium.getBigDecimal());
        bonusStatementLineVO.setType(commissionType);

        bonusStatementLineVO.setEffectiveDate(effectiveDate.getFormattedDate());
        bonusStatementLineVO.setProcessDate(processDate.getEDITDate().getFormattedDate());

        bonusStatementLineVO.setAllocationPercent(allocationPercent.getBigDecimal());

        return bonusStatementLineVO;
    }
}
