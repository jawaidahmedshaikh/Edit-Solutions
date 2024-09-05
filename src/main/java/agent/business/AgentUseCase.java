package agent.business;

import edit.services.component.IUseCase;
import agent.*;
import contract.FilteredRequirement;
import casetracking.CaseRequirement;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:19:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentUseCase extends IUseCase
{
    public void accessAgentDetail();

    public void addAgent();

    public void updateAgent();

    public void deleteAgent();

    public void viewComissionExtract();

    public void accessAgentHierarchy();

    public void addPlacedAgent();

    public void removePlacedAgent();

    public void updateAgentNotes();

    public void accessBonus();       

    /**
     * Security checkpoint.
     */
    public void accessAgentBonusProgram();

    public void associateRequirementsToAgent(agent.Agent agent, FilteredRequirement[] filteredRequirements);

    public void associateFilteredRequirementToAgent(agent.Agent agent, FilteredRequirement filteredRequirement);

    public void deleteRequirement(AgentRequirement agentRequirement);

    public void updateAgentRequirement(AgentRequirement agentRequirement);
}
