/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package agent.ui.servlet;

import edit.common.vo.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

import event.business.Event;
import event.component.EventComponent;
import event.dm.dao.DAOFactory;
import agent.business.Agent;
import agent.component.AgentComponent;
import agent.PlacedAgent;
import role.ClientRole;

/**
 * Helps with lazy-loading Commission Extract history only when needed.
 * Depending on the amount of CommissionHistory with UpdateStatus = 'U',
 * this can take some time.  This class helps optimize the default loading.
 * It could also be changed to optimize the other two types of loading
 * as well.  All of the data loads and access for this session data
 * <CODE>agentExtractVOs</CODE> was moved here to organize it and remove
 * cut-and-paste duplication.
 * <p>
 * If the default data has been loaded already, the
 * <CODE>getCommissionExtracts</CODE> just returns it.  If it has not been
 * loaded, it retrieves it, stores it in the session and then returns it.
 * <p>
 * Places where the session attribute <CODE>agentExtractVOs</CODE> was
 * removed from the session were changed to reference the constant
 * CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY to make the
 * connection explicit.  A separate method to remove it was not needed
 * since this reference serves to let the programmer know that
 * this class is involved.
 * <p>
 * AgentDetailTran has three different ways of loading the session data
 * keyed by agentExtractVOs.
 * <p>
 * It used to load this data when the Agent Detail page was first
 * accessed after a search.  This was the default
 * set of data that was in the Session.  Now it is not loaded unless it is
 * needed.  So when the getCommissionExtracts() method is called from
 * the AgentDetailTran or from the JSP pages, the default is to return
 * the data by placed agent.
 * <p>
 * Some of the routines in AgentDetailTran explicitly go after all of the data
 * or by a filter agent id.  For these the two load methods are called.
 * This way this data access for the same session data is organized in one
 * place.  Doing this removed some code duplication.
 * <p>
 * Usages:
 * <PRE>
 *      CommissionHistoryExtractCache commHistExractCache =
 *               new CommissionHistoryExtractCache(theSession);
 *
 *      CommissionHistoryVO[] commissionHistoryVOs =
 *          commHistExractCache.getCommissionExtracts();
 *
 *  // FOR THE SPECIAL CASES WHERE THE AgentDetailTran WANTS TO LOAD
 *  // A VARIATION OF THE DATA INTO THE SESSION ...
 *
 *      CommissionHistoryExtractCache commHistExractCache =
 *               new CommissionHistoryExtractCache(theSession);
 *
 *      commHistExractCache.loadCommissionExtractsByUpdateStatusIntoSession();
 *
 *   // OR ...
 *      commHistExractCache.loadFilteredExtractsIntoSession(filterAgentId);
 *
 * </PRE>
 */
public class CommissionHistoryExtractCache
{

    public static String AGENT_EXTRACT_SESSION_KEY = "agentExtractVOs";

    private HttpSession httpSession;

    public CommissionHistoryExtractCache(HttpSession _httpSession)
    {
        this.httpSession = _httpSession;


    }

    public CommissionHistoryVO[] getCommissionExtracts() throws Exception
    {
        CommissionHistoryVO[] agentExtractVOs =
                (CommissionHistoryVO[])
                this.httpSession.getAttribute("agentExtractVOs");
        
        //becos, if any extract is not found for an agent#, it shd not load all the commisssion; rather it shd show nothing
        if (agentExtractVOs == null && this.httpSession.getAttribute("filterAgentId") == null)
        {
            agentExtractVOs = getDefaultDataByPlacedAgent();
            // NOTE IT USED TO LOAD THIS AUTOMATICALLY WHEN THE
            // AGENT DETAIL WAS LOADED.  SO WHEN ASKING FOR THE
            // SESSION DATA, IF THERE IS NONE LOADED, GO GET THIS
            // DEFAULT DATA WHICH IS BY PLACED AGENT.

            if (agentExtractVOs == null)
            {
                agentExtractVOs = new CommissionHistoryVO[0];
            }
            this.httpSession.setAttribute("agentExtractVOs", agentExtractVOs);
        }

        return agentExtractVOs;
    }

    public CommissionHistoryVO[] getCommissionExtracts(String selectedAgentNumber) throws Exception
    {
        ClientRole clientRole = ClientRole.findBy_RoleType_ReferenceID(ClientRole.ROLETYPECT_AGENT, selectedAgentNumber)[0];
        Set<PlacedAgent> placedAgents = clientRole.getPlacedAgents();
        Iterator it = placedAgents.iterator();
        long[] placedAgentPKs = new long[placedAgents.size()];
        int i = 0;
        while (it.hasNext())
        {
            PlacedAgent placedAgent = (PlacedAgent) it.next();
            placedAgentPKs[i] = placedAgent.getPlacedAgentPK().longValue();
            i += 1;
        }
        
        /*if an agent has more than one agentID, the 2nd one will see the values for 1st agentID
         * that's why it has to go to DB to get the VO each time; it shd not be taken from session; issueLog-41
         */
        CommissionHistoryVO[] agentExtractVOs = null;
                //(CommissionHistoryVO[])
                //this.httpSession.getAttribute("agentExtractVOs");

        if (agentExtractVOs == null)
        {
            agentExtractVOs = getDefaultDataByPlacedAgent(placedAgentPKs);

            if (agentExtractVOs == null)
            {
                agentExtractVOs = new CommissionHistoryVO[0];
            }
            this.httpSession.setAttribute("agentExtractVOs", agentExtractVOs);
        }

        return agentExtractVOs;
    }

    /**
     * CommissionHistory records that held for large cases.
     * @return CommissionHistories with UpdateStatus = 'L'
     */
    public CommissionHistoryVO[] getCommissionHistoriesHeldForLargeCase()
    {
        AgentVO agentVO = (AgentVO) this.httpSession.getAttribute("agentVO");

        if (agentVO == null)
        {
            throw new IllegalStateException(
                    "CommissionHistoryExtractCache cannot find agentVO in Session!");
        }

        AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();

        List commissionHistoriesHeldForLargeCase = new ArrayList();

        for (int i = 0; i < agentContractVOs.length; i++)
        {
            PlacedAgentVO[] placedAgentVOs = agentContractVOs[i].getPlacedAgentVO();

            for (int j = 0; j < placedAgentVOs.length; j++)
            {
                CommissionHistoryVO[] commissionHistoryVOs =
                        DAOFactory.getCommissionHistoryDAO().findByPlacedAgentPKAndUpdateStatus(placedAgentVOs[j].getPlacedAgentPK(), new String[] {"L"});

                if (commissionHistoryVOs != null)
                {
                    for (int k = 0; k < commissionHistoryVOs.length; k++)
                    {
                        commissionHistoriesHeldForLargeCase.add(commissionHistoryVOs[k]);
                    }
                }
            }
        }

        return (CommissionHistoryVO[]) commissionHistoriesHeldForLargeCase.toArray(new CommissionHistoryVO[commissionHistoriesHeldForLargeCase.size()]);
    }

    public void loadCommissionExtractsByUpdateStatusIntoSession() throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVOs = null;
        Event eventComponent = new EventComponent();
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientDetailVO.class);
        commissionHistoryVOs = eventComponent.
                composeCommissionHistoryVOByUpdateStatus("U", voInclusionList);
        if (commissionHistoryVOs == null)
        {
            commissionHistoryVOs = new CommissionHistoryVO[0];
        }

        this.httpSession.setAttribute("agentExtractVOs", commissionHistoryVOs);
    }

    public String loadFilteredExtractsIntoSession(String filterAgentId, AgentVO agentVO) throws Exception
    {
        String extractMessage = "";

        Agent agentComponent = new AgentComponent();
        Event eventComponent = new EventComponent();

        List commissionExtracts = null;
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientDetailVO.class);
        
        if(filterAgentId != null && agentVO == null)
        {
            agentVO = agentComponent.composeAgentVOByAgentNumber(filterAgentId, voInclusionList);
        }
        
        if (agentVO != null)
        {
            long agentPK = agentVO.getAgentPK();
            agentVO = agentComponent.composeAgentVO(agentPK, voInclusionList);

            AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();
            if (agentContractVOs != null)
            {
                for (int c = 0; c < agentContractVOs.length; c++)
                {
                    PlacedAgentVO[] placedAgentVOs = agentContractVOs[c].getPlacedAgentVO();
                    if (placedAgentVOs != null)
                    {
                        for (int p = 0; p < placedAgentVOs.length; p++)
                        {
                            /*if user wants to filter for a agent# / refID, that happens here
                             * the unwanted clientRole/placedAgent for the refID gets filtered out
                             */
                            String refIDclientRole = ((ClientRoleVO)placedAgentVOs[p].getParentVO(ClientRoleVO.class)).getReferenceID();
                            if(filterAgentId != null && !filterAgentId.equalsIgnoreCase(refIDclientRole))
                            {
                                continue;
                            }
                            
                            voInclusionList.clear();
                            voInclusionList.add(EDITTrxHistoryVO.class);
                            voInclusionList.add(FinancialHistoryVO.class);
                            voInclusionList.add(EDITTrxVO.class);
                            voInclusionList.add(ClientSetupVO.class);
                            voInclusionList.add(ContractSetupVO.class);
                            voInclusionList.add(SegmentVO.class);
                            voInclusionList.add(AgentContractVO.class);
                            voInclusionList.add(PlacedAgentVO.class);
                            voInclusionList.add(AgentVO.class);
                            voInclusionList.add(ClientRoleVO.class);
                            voInclusionList.add(ClientDetailVO.class);

                            CommissionHistoryVO[] extractVO =
                                    eventComponent.
                                    composeCommHistVOByPlacedAgentPKAndUpdateStatus(
                                            placedAgentVOs[p].getPlacedAgentPK(),
                                            new String[] {"U", "L"},
                                            voInclusionList);

                            if (extractVO != null)
                            {
                                for (int h = 0; h < extractVO.length; h++)
                                {
                                    if (commissionExtracts == null)
                                    {
                                        commissionExtracts = new ArrayList();
                                    }
                                    commissionExtracts.add(extractVO[h]);
                                }
                            }
                        }
                    }
                    else
                    {
                        extractMessage = "No Extracts Found for Specified Agent";
                    }
                }

                if (commissionExtracts != null)
                {
                    CommissionHistoryVO[] extractVOs = (CommissionHistoryVO[])
                            commissionExtracts.toArray(
                                    new CommissionHistoryVO[commissionExtracts.size()]);

                    this.httpSession.setAttribute(
                            CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY,
                            extractVOs);
                }
                else
                {
                    extractMessage = "No Extracts Found for Specified Agent";
                    this.httpSession.setAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY, null);
                }
            }
            else
            {
                extractMessage = "No Extracts Found for Specified Agent";
                this.httpSession.setAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY, null);
            }
        }
        else
        {
            extractMessage = "Agent Not Found";
            this.httpSession.setAttribute(CommissionHistoryExtractCache.AGENT_EXTRACT_SESSION_KEY, null);
        }

        return extractMessage;
    }


    private CommissionHistoryVO[] getDefaultDataByPlacedAgent() throws Exception
    {
        AgentVO agentVO = (AgentVO) this.httpSession.getAttribute("agentVO");
        if (agentVO == null)
        {
            throw new IllegalStateException(
                    "CommissionHistoryExtractCache cannot find agentVO in Session!");
        }

        event.business.Event eventComponent = new event.component.EventComponent();

        List commissionExtracts = new ArrayList();

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientDetailVO.class);

        AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();

        if (agentContractVOs != null)
        {
            for (int i = 0; i < agentContractVOs.length; i++)
            {
                PlacedAgentVO[] placedAgentVOs = agentContractVOs[i].getPlacedAgentVO();

                if (placedAgentVOs != null)
                {
                    for (int j = 0; j < placedAgentVOs.length; j++)
                    {
                        CommissionHistoryVO[] extractVOs =
                                eventComponent.
                                    composeCommHistVOByPlacedAgentPKAndUpdateStatus(
                                        placedAgentVOs[j].getPlacedAgentPK(),
                                        new String[] {"U", "L"},
                                        voInclusionList);
                        if (extractVOs != null)
                        {
                            for (int k = 0; k < extractVOs.length; k++)
                            {
                                commissionExtracts.add(extractVOs[k]);
                            }
                        }
                    }
                }
            }

            CommissionHistoryVO[] extractVOs = (CommissionHistoryVO[])
                    commissionExtracts.toArray(new CommissionHistoryVO[commissionExtracts.size()]);

            return extractVOs;

        }
        else
        {
            return new CommissionHistoryVO[0];
        }
    }

    private CommissionHistoryVO[] getDefaultDataByPlacedAgent(long[] placedAgentPKs) throws Exception
    {
        AgentVO agentVO = (AgentVO) this.httpSession.getAttribute("agentVO");
        if (agentVO == null)
        {
            throw new IllegalStateException("CommissionHistoryExtractCache cannot find agentVO in Session!");
        }

        event.business.Event eventComponent = new event.component.EventComponent();

        List commissionExtracts = new ArrayList();

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(PlacedAgentVO.class);
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientDetailVO.class);

        for (int i = 0; i < placedAgentPKs.length; i++)
        {
            CommissionHistoryVO[] extractVOs =
                    eventComponent.
                        composeCommHistVOByPlacedAgentPKAndUpdateStatus(
                            placedAgentPKs[i],
                            new String[] {"U", "L"},
                            voInclusionList);
            if (extractVOs != null)
            {
                for (int k = 0; k < extractVOs.length; k++)
                {
                    commissionExtracts.add(extractVOs[k]);
                }
            }
        }

        CommissionHistoryVO[] extractVOs = (CommissionHistoryVO[])
                commissionExtracts.toArray(new CommissionHistoryVO[commissionExtracts.size()]);

        return extractVOs;
    }
}
