/*
 * User: gfrosti
 * Date: Apr 29, 2005
 * Time: 1:58:01 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.*;
import edit.common.vo.*;
import event.*;

import engine.*;

public class BonusStatement
{
    private ParticipatingAgent participatingAgent;

    private EDITDate statementDate;

    private EDITBigDecimal lastStatementAmount;

    private EDITDateTime lastStatementDateTime;

    private ParticipatingAgentInfo participatingAgentInfo;

    private BonusEarnings bonusEarnings;

    private String contractCodeCT;

    public BonusStatement(ParticipatingAgent participatingAgent, String contractCodeCT, EDITDate processDate, EDITDateTime statementDateTime)
    {
        this.participatingAgent = participatingAgent;

        this.contractCodeCT = contractCodeCT;

        this.statementDate = processDate;

        this.lastStatementDateTime = statementDateTime;
    }

    /**
     * Generates the Bonus Commission Statement for the supplied ParticipatingAgent.
     */
    public boolean generateBonusStatement()
    {
        boolean statementGenerated = false;
        // Premium levels can be attached to the participatingAgent (override) or the BonusProgram.
        PremiumLevel[] premiumLevels = participatingAgent.getBizAppliedPremiumLevels();

        if (premiumLevels != null && premiumLevels.length > 0)
        {
            ProductStructure[] productStructures = participatingAgent.getBonusProgram().getProductStructures();

            buildParticipatingAgentInfo(premiumLevels, productStructures);

            BonusCommissionHistory[]  bonusCommissionHistories = BonusCommissionHistory.findAllBy_ParticipatingAgent_TrxTypeCT(participatingAgent, "BCK");

            buildBonusEarnings(bonusCommissionHistories);
            statementGenerated = true;
        }
        else
        {
            statementGenerated = false;
        }

        return statementGenerated;
    }


    /**
     * Generates the relative BonusEarnings (taxable income, positive policy earnings, etc.).
     */
    private void buildBonusEarnings(BonusCommissionHistory[] bonusCommissionHistories)
    {
        bonusEarnings = new BonusEarnings(participatingAgent);

        bonusEarnings.generateBonusEarnings(participatingAgent, bonusCommissionHistories);
    }


    /**
     * Builds the respectful AgentInfo the supplied ParticipatingAgent.
     */
    private void buildParticipatingAgentInfo(PremiumLevel[] premiumLevels, ProductStructure[] productStructures)
    {
        participatingAgentInfo = new ParticipatingAgentInfo();

        participatingAgentInfo.generateAgentInfo(participatingAgent, statementDate, premiumLevels, productStructures);

    }

    /**
     * The VO (read-only) representation of this entity.
     * @return
     */
    public BonusStatementVO getBonusStatementVO()
    {
        BonusStatementVO bonusStatementVO = new BonusStatementVO();

        if (bonusEarnings != null)
        {
            bonusStatementVO.setBonusEarningsVO(bonusEarnings.getBonusEarningsVO());
        }

        if (participatingAgentInfo != null)
        {
            bonusStatementVO.setParticipatingAgentInfoVO(participatingAgentInfo.getParticipatingAgentInfoVO());
        }

        if (lastStatementDateTime != null)
        {
            bonusStatementVO.setLastStatementDate(lastStatementDateTime.getEDITDate().getFormattedDate());
        }

        if (statementDate != null)
        {
            bonusStatementVO.setStatementDate(statementDate.getFormattedDate());
        }

        if (lastStatementAmount != null)
        {
            bonusStatementVO.setLastStatementAmount(lastStatementAmount.getBigDecimal());
        }

        return bonusStatementVO;
    }
}
