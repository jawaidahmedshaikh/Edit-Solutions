/*
 * User: gfrosti
 * Date: Dec 1, 2003
 * Time: 11:50:13 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import client.dm.composer.*;
import edit.common.*;
import edit.common.vo.*;
import role.*;

import java.util.*;

public class AgentInfoImpl
{
    public void generateAgentInfo(AgentInfo agentInfo, PlacedAgent placedAgent, String statementDate)
    {
        Agent agent = Agent.findByAgentContract(placedAgent.getAgentContractFK());
        AgentInfoVO agentInfoVO = agentInfo.getVO();

//        Agent agent = placedAgent.get_AgentContract().get_Agent();
        String corrAddressType = ((AgentVO) agent.getVO()).getCorrespondenceAddressTypeCT();

        String[] redirectInformation = getRedirectInformation((AgentVO)agent.getVO(), statementDate);

        ClientDetailVO clientDetailVO = getClientDetailVO(agent);
        if (corrAddressType == null || corrAddressType.equals(""))
        {
            corrAddressType = "PrimaryAddress";
        }
        ClientAddressVO clientAddressVO = getAgentAddress(corrAddressType, clientDetailVO, statementDate);
        String reportToId = getReportToId(placedAgent);
        String disbursementSource = getDisbursementSource(clientDetailVO.getPreferenceVO());
        String paymentMode = getPaymentMode(clientDetailVO.getPreferenceVO());

        String agentName = getAgentName(clientDetailVO);

        agentInfoVO.setName(agentName);
        agentInfoVO.setFirstName(clientDetailVO.getFirstName());
        agentInfoVO.setNameSuffix(clientDetailVO.getNameSuffix());
        agentInfoVO.setAgentNumber(((AgentVO) agent.getVO()).getAgentNumber());
        agentInfoVO.setAddress1(clientAddressVO.getAddressLine1());
        agentInfoVO.setAddress2(clientAddressVO.getAddressLine2());
        agentInfoVO.setAddress3(clientAddressVO.getAddressLine3());
        agentInfoVO.setAddress4(clientAddressVO.getAddressLine4());
        agentInfoVO.setCity(clientAddressVO.getCity());
        agentInfoVO.setState(clientAddressVO.getStateCT());
        agentInfoVO.setZip(clientAddressVO.getZipCode());
        agentInfoVO.setAgentType(((AgentVO) agent.getVO()).getAgentTypeCT());
        agentInfoVO.setReportTo(reportToId);
        agentInfoVO.setDisbursementSource(disbursementSource);
        agentInfoVO.setPaymentMode(paymentMode);

        if (redirectInformation != null)
        {
            agentInfoVO.setRedirectName(redirectInformation[0]);
            agentInfoVO.setRedirectNumber(redirectInformation[1]);
            agentInfoVO.setRedirectAddress1(redirectInformation[2]);
            agentInfoVO.setRedirectAddress2(redirectInformation[3]);
            agentInfoVO.setRedirectAddress3(redirectInformation[4]);
            agentInfoVO.setRedirectAddress4(redirectInformation[5]);
            agentInfoVO.setRedirectCity(redirectInformation[6]);
            agentInfoVO.setRedirectState(redirectInformation[7]);
            agentInfoVO.setRedirectZip(redirectInformation[8]);
        }
        else
        {
            agentInfoVO.setRedirectName(agentInfoVO.getName());
            agentInfoVO.setRedirectNumber(agentInfoVO.getAgentNumber());
            agentInfoVO.setRedirectAddress1(agentInfoVO.getAddress1());
            agentInfoVO.setRedirectAddress2(agentInfoVO.getAddress2());
            agentInfoVO.setRedirectAddress3(agentInfoVO.getAddress3());
            agentInfoVO.setRedirectAddress4(agentInfoVO.getAddress4());
            agentInfoVO.setRedirectCity(agentInfoVO.getCity());
            agentInfoVO.setRedirectState(agentInfoVO.getState());
            agentInfoVO.setRedirectZip(agentInfoVO.getZip());
        }
    }

    private String getDisbursementSource(PreferenceVO[] preferenceVO)
    {
        String disbursementSourceCT = null;

        if (preferenceVO.length == 0)
        {
            disbursementSourceCT = "NA";
        }
        else
        {
            disbursementSourceCT = preferenceVO[0].getDisbursementSourceCT();
        }

        return disbursementSourceCT;
    }

    private String getPaymentMode(PreferenceVO[] preferenceVO)
    {
        String paymentMode = null;

        if (preferenceVO.length == 0)
        {
            paymentMode = "NA";
        }
        else
        {
            paymentMode = preferenceVO[0].getPaymentModeCT();
        }

        return paymentMode;
    }

    private String getReportToId(PlacedAgent placedAgent)
    {
        String reportToId = null;

        PlacedAgent reportToPlacedAgent = placedAgent.getParent();

        if (reportToPlacedAgent != null)
        {
            Agent agent = reportToPlacedAgent.get_AgentContract().get_Agent();

            reportToId = ((AgentVO) agent.getVO()).getAgentNumber();
         }

        return reportToId;
    }

    private ClientDetailVO getClientDetailVO(Agent agent)
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(PreferenceVO.class);
        voInclusionList.add(ClientAddressVO.class);

        ClientRole[] clientRoles = ClientRole.findByAgentFK(agent.getAgentPK());
        ClientRole clientRole = clientRoles[0];

        long clientDetailFK = ((ClientRoleVO)clientRole.getVO()).getClientDetailFK();
        ClientDetailVO clientDetailVO = new ClientDetailComposer(voInclusionList).compose(clientDetailFK);

        return clientDetailVO;
    }

    private ClientAddressVO getAgentAddress(String corrAddressType, ClientDetailVO clientDetailVO, String statementDate)
    {
        EDITDate edStatementDate = new EDITDate(statementDate);

        ClientAddressVO agentAddress = null;

        ClientAddressVO[] clientAddressVO = clientDetailVO.getClientAddressVO();

        if (corrAddressType.equalsIgnoreCase("PrimaryAddress"))
        {
            agentAddress = checkForSecondaryAddress(clientAddressVO, edStatementDate);
        }

        if (agentAddress == null)
        {
            for (int i = 0; i < clientAddressVO.length; i++)
            {
                if (clientAddressVO[i].getAddressTypeCT().equals(corrAddressType))
                {
                    String effectiveDateString = clientAddressVO[i].getEffectiveDate();
                    String terminateDateString =clientAddressVO[i].getTerminationDate();
                    if (effectiveDateString != null && terminateDateString != null)
                    {
                        EDITDate effectiveDate = new EDITDate(effectiveDateString);
                        EDITDate terminationDate = new EDITDate(terminateDateString);

                        if ( (edStatementDate.after(effectiveDate) || edStatementDate.equals(effectiveDate))&&
                             (edStatementDate.before(terminationDate) || edStatementDate.equals(terminationDate)) )
                        {
                            agentAddress = clientAddressVO[i];
                        }
                    }
                    else if (effectiveDateString == null && terminateDateString != null)
                    {
                        agentAddress = clientAddressVO[i];
                    }
                }
            }
        }

        if (agentAddress == null)
        {
            agentAddress = new ClientAddressVO();
        }

        return agentAddress;
    }

    private ClientAddressVO checkForSecondaryAddress(ClientAddressVO[] clientAddressVOs, EDITDate statementDate)
    {
        ClientAddressVO secondaryAddress = null;

        for (int i = 0; i < clientAddressVOs.length; i++)
        {
            if (clientAddressVOs[i].getAddressTypeCT().equals("SecondaryAddress"))
            {
                EDITDate effectiveDate = new EDITDate(clientAddressVOs[i].getEffectiveDate());
                EDITDate terminationDate = new EDITDate(clientAddressVOs[i].getTerminationDate());

                if ( (statementDate.after(effectiveDate) || statementDate.equals(effectiveDate)) &&
                     (statementDate.before(terminationDate) || statementDate.equals(terminationDate)) )
                {
                    String startDate = clientAddressVOs[i].getStartDate();
                    String stopDate = clientAddressVOs[i].getStopDate();

                    String stmtDate = statementDate.getFormattedMonth() + EDITDate.DATE_DELIMITER + statementDate.getFormattedDay();

                    if ((stmtDate).compareTo(startDate) >= 0 &&
                        (stmtDate).compareTo(stopDate) <= 0)
                    {
                        secondaryAddress = clientAddressVOs[i];
                        break;
                    }
                }
            }
        }

        return secondaryAddress;
    }

    private String getAgentName(ClientDetailVO clientDetailVO)
    {
        String agentName = null;

        if (clientDetailVO.getTrustTypeCT().equals("CorporateTrust") || clientDetailVO.getTrustTypeCT().equals("Corporate"))
        {
            agentName = clientDetailVO.getCorporateName();
        }
        else
        {
            agentName = clientDetailVO.getLastName();
        }

        return agentName;
    }

    private String[] getRedirectInformation(AgentVO agentVO, String statementDate)
    {
        String[] redirectInformation = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);

        long agentFK = agentVO.getAgentPK();

        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        RedirectVO redirectVO = agentComponent.composeRedirectByAgentFK(agentFK, voInclusionList);

        if (redirectVO != null)
        {
            ClientRoleVO clientRoleVO = (ClientRoleVO) redirectVO.getParentVO(ClientRoleVO.class);

            AgentVO redirectAgentVO = (AgentVO) clientRoleVO.getParentVO(AgentVO.class);

            String corrAddressType = redirectAgentVO.getCorrespondenceAddressTypeCT();

            ClientDetailVO clientDetailVO = getClientDetailVO(new Agent(redirectAgentVO));
            if (corrAddressType == null || corrAddressType.equals(""))
            {
                corrAddressType = "PrimaryAddress";
            }

            ClientAddressVO clientAddressVO = getAgentAddress(corrAddressType, clientDetailVO, statementDate);

            String agentName = getAgentName(clientDetailVO);

            redirectInformation = new String[9];
            redirectInformation[0] = agentName;
            redirectInformation[1] = redirectAgentVO.getAgentNumber();
            redirectInformation[2] = clientAddressVO.getAddressLine1();
            redirectInformation[3] = clientAddressVO.getAddressLine2();
            redirectInformation[4] = clientAddressVO.getAddressLine3();
            redirectInformation[5] = clientAddressVO.getAddressLine4();
            redirectInformation[6] = clientAddressVO.getCity();
            redirectInformation[7] = clientAddressVO.getStateCT();
            redirectInformation[8] = clientAddressVO.getZipCode();
        }

        return redirectInformation;
    }
}
