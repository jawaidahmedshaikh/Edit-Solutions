package agent.component;

import agent.business.AgentUseCase;
import agent.Agent;
import agent.AgentRequirement;
import client.ClientDetail;
import contract.FilteredRequirement;
import contract.Requirement;
import edit.services.db.hibernate.SessionHelper;
import casetracking.CaseRequirement;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:20:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class AgentUseCaseComponent implements AgentUseCase
{
    public void accessAgent()
    {
    }

    public void accessAgentDetail()
    {
    }

    public void addAgent()
    {
    }

    public void updateAgent()
    {
    }

    public void deleteAgent()
    {
    }

    public void viewComissionExtract()
    {
    }

    public void accessAgentHierarchy()
    {
    }

    public void addPlacedAgent()
    {
    }

    public void removePlacedAgent()
    {
    }

    public void updateAgentNotes()
    {
    }

    public void accessBonus()
    {
    }

    /**
     * Security checkpoint.
     */
    public void accessAgentBonusProgram()
    {

    }

    /**
     * Associates filteredRequirements to agent.
     * @param agent
     * @param filteredRequirements
     */
    public void associateRequirementsToAgent(Agent agent, FilteredRequirement[] filteredRequirements)
    {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        for (int i = 0; i < filteredRequirements.length; i++)
        {
            Requirement requirement = filteredRequirements[i].getRequirement();

            if (!requirement.isManualRequirement())
            {
                AgentRequirement agentRequirement = new AgentRequirement(agent, filteredRequirements[i]);

                SessionHelper.saveOrUpdate(agentRequirement, SessionHelper.EDITSOLUTIONS);
            }
        }

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Adds manual requirement and associates to company structure and agent
     * @param agent
     * @param filteredRequirement
     */
    public void associateFilteredRequirementToAgent(Agent agent, FilteredRequirement filteredRequirement)
    {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        AgentRequirement agentRequirement = new AgentRequirement(agent, filteredRequirement);

        SessionHelper.saveOrUpdate(agentRequirement, SessionHelper.EDITSOLUTIONS);

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }

    public void deleteRequirement(AgentRequirement agentRequirement)
    {
        Agent agent = agentRequirement.getAgent();
        FilteredRequirement filteredRequirement = agentRequirement.getFilteredRequirement();

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        agent.deleteAgentRequirement(agentRequirement);
        filteredRequirement.deleteAgentRequirement(agentRequirement);

        SessionHelper.delete(agentRequirement, SessionHelper.EDITSOLUTIONS);

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }

    /**
     * Updates existing AgentRequirement entity.
     * @param agentRequirement
     */
    public void updateAgentRequirement(AgentRequirement agentRequirement)
    {
        if (agentRequirement.getFollowupDate() == null)
        {
            agentRequirement.calculateAndSetFollowupDate();
        }

        agentRequirement.checkRequirementStatusAndUpdateReceivedDate();

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        SessionHelper.saveOrUpdate(agentRequirement, SessionHelper.EDITSOLUTIONS);

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }
}
