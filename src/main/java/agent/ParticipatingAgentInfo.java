/*
 * User: gfrosti
 * Date: May 2, 2005
 * Time: 3:17:00 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.*;
import edit.common.vo.*;

import java.util.*;

import engine.*;


public class ParticipatingAgentInfo
{
    private AgentInfo agentInfo;
    private EDITBigDecimal bonusCheckAmount;

    private String bonusName;

    List premiumLeveStatementlArray;
    private PremiumLevelStatement[] premiumLevelStatements;


    public ParticipatingAgentInfo()
    {
        this.agentInfo = new AgentInfo();
        this.premiumLeveStatementlArray = new ArrayList();
    }

    /**
     * The relevant information (name, address, etc.) as well as the BonusCheckAmount.
     * @param participatingAgent
     * @param processDate
     */
    public void generateAgentInfo(ParticipatingAgent participatingAgent, EDITDate processDate, PremiumLevel[] premiumLevels, ProductStructure[] productStructures)
    {
        PlacedAgent placedAgent = participatingAgent.getPlacedAgent();

        agentInfo.generateAgentInfo(placedAgent, processDate.getFormattedDate());

        bonusCheckAmount = participatingAgent.getLastCheckAmount();

        bonusName = participatingAgent.getBonusProgram().getBonusName();

        for (int i = 0; i < premiumLevels.length; i++)
        {
            PremiumLevelStatement premiumLevelStatement = new PremiumLevelStatement(participatingAgent);

            premiumLevelStatement.generatePremiumLevelStatement(premiumLevels[i], productStructures);

            premiumLeveStatementlArray.add(premiumLevelStatement);
        }

        List premiumLevelArray = this.premiumLeveStatementlArray;
        premiumLevelStatements = (PremiumLevelStatement[])premiumLevelArray.toArray(new PremiumLevelStatement[premiumLevelArray.size()]);

    }

    /**
     * The VO (read-only) version of this entity.
     * @return
     */
    public ParticipatingAgentInfoVO getParticipatingAgentInfoVO()
    {
        AgentInfoVO agentInfoVO = agentInfo.getVO();

        ParticipatingAgentInfoVO participatingAgentInfoVO = new ParticipatingAgentInfoVO();

        participatingAgentInfoVO.setAddress1(agentInfoVO.getAddress1());
        participatingAgentInfoVO.setAddress2(agentInfoVO.getAddress2());
        participatingAgentInfoVO.setAddress3(agentInfoVO.getAddress3());
        participatingAgentInfoVO.setAddress4(agentInfoVO.getAddress4());

        participatingAgentInfoVO.setAgentNumber(agentInfoVO.getAgentNumber());
        participatingAgentInfoVO.setAgentType(agentInfoVO.getAgentType());
        participatingAgentInfoVO.setCity(agentInfoVO.getCity());
        participatingAgentInfoVO.setDisbursementSource(agentInfoVO.getDisbursementSource());
        participatingAgentInfoVO.setFirstName(agentInfoVO.getFirstName());
        participatingAgentInfoVO.setName(agentInfoVO.getName());

        participatingAgentInfoVO.setRedirectAddress1(agentInfoVO.getRedirectAddress1());
        participatingAgentInfoVO.setRedirectAddress2(agentInfoVO.getRedirectAddress2());
        participatingAgentInfoVO.setRedirectAddress3(agentInfoVO.getRedirectAddress3());
        participatingAgentInfoVO.setRedirectAddress4(agentInfoVO.getRedirectAddress4());
        participatingAgentInfoVO.setRedirectCity(agentInfoVO.getRedirectCity());
        participatingAgentInfoVO.setRedirectName(agentInfoVO.getRedirectName());
        participatingAgentInfoVO.setRedirectNumber(agentInfoVO.getRedirectNumber());
        participatingAgentInfoVO.setRedirectState(agentInfoVO.getRedirectState());
        participatingAgentInfoVO.setRedirectZip(agentInfoVO.getRedirectZip());
        participatingAgentInfoVO.setReportTo(agentInfoVO.getReportTo());

        participatingAgentInfoVO.setState(agentInfoVO.getState());
        participatingAgentInfoVO.setZip(agentInfoVO.getZip());

        participatingAgentInfoVO.setBonusCheckAmount(bonusCheckAmount.getBigDecimal());
        participatingAgentInfoVO.setBounsName(bonusName);


        for (int i = 0; i < premiumLevelStatements.length; i++)
        {
            PremiumLevelStatementVO premiumLevelStatementVO = premiumLevelStatements[i].getPremiumLevelStatementVO();
            participatingAgentInfoVO.addPremiumLevelStatementVO(premiumLevelStatementVO);
        }

        return participatingAgentInfoVO;
    }
}
