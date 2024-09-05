/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package agent;

import agent.common.Constants;

import contract.AgentHierarchy;

import edit.common.EDITDate;
import edit.common.exceptions.EDITAgentException;

import edit.services.db.hibernate.SessionHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Hierarchy
{
    private static Hierarchy hierarchy;
    public static final String STOPDATEREASONCT_HIERARCHYMOVE = "HierarchyMove";
    public static final String STOPDATEREASONCT_HIERARCHYMOVE_TRANSER = "HierarchyMove-Transfer";
    private HierarchyImpl hierarchyImpl;
    public static final Object AGENT_HIERARCHY_MONITOR = new Object();

    private Hierarchy()
    {
        hierarchyImpl = new HierarchyImpl();
    }

    public static Hierarchy getSingleton()
    {
        if (hierarchy == null)
        {
            hierarchy = new Hierarchy();
        }

        return hierarchy;
    }

    public PlacedAgentBranch getBranch(PlacedAgent placedAgent) throws Exception
    {
        return new PlacedAgentBranch(placedAgent);
    }

    /**
     * Places an AgentContract into the Hierarchy. An AgentContract can only be placed once. From that point on,
     * they can only be repositioned within the Hierarchy.

     * @param agentContract
     * @param parentPlacedAgent
     * @return
     * @throws EDITAgentException Will be thrown if this AgentContract has already been placed.
     */
    public void placeAgentContract(AgentContract agentContract, PlacedAgent parentPlacedAgent, PlacedAgent placedAgentDetails,
                                   String operator, String agentNumber) throws EDITAgentException
    {
        if ((parentPlacedAgent != null) && parentPlacedAgent.isExpired())
        {
            throw new EDITAgentException(Constants.ErrorMsg.AGENT_STOPDATE_HAS_EXPIRED);
        }

        placedAgentDetails.setOperator(operator);

        agentContract.addPlacedAgent(placedAgentDetails);

        hierarchyImpl.placeAgent(this, placedAgentDetails, parentPlacedAgent, agentNumber);
    }

    /**
     * Removes a PlacedAgent from the Hierarchy, and returns the modified PlacedAgentBranch

     * @param placedAgent

     * @return
     * @throws EDITAgentException
     */
    public void remove(PlacedAgent placedAgent) throws EDITAgentException
    {
        hierarchyImpl.removePlacedAgent(placedAgent);
    }

    public void swap(PlacedAgent placedAgentA, PlacedAgent placedAgentB) throws Exception
    {
        hierarchyImpl.swap(placedAgentA, placedAgentB);
    }

    public PlacedAgentBranch shift(PlacedAgent lowestLevelPlacedAgent, PlacedAgent placedAgent, int direction) throws Exception
    {
        if (PlacedAgent.SHIFT_UP == direction)
        {
            placedAgent.setModifyingEvent(PlacedAgent.EVENT_SHIFTED_UP);
        }
        else if (PlacedAgent.SHIFT_DOWN == direction)
        {
            placedAgent.setModifyingEvent(PlacedAgent.EVENT_SHIFTED_DOWN);
        }

        PlacedAgentBranch placedAgentBranch = null;

        placedAgentBranch = new PlacedAgentBranch(lowestLevelPlacedAgent);

        placedAgentBranch.shift(placedAgent, direction);

        return placedAgentBranch;
    }

    /**
     * Inserts this new PlacedAgent above the specified lowest-level PlacedAgent. The lowest-level PlacedAgent is
     * required to avoid inadavertently changing existing branches.
     * @param agentContract
     * @param lowestLevelPlacedAgent
     * @param placedAgentDetails
     * @param operator
     */
    public void insertAbove(AgentContract agentContract, PlacedAgent lowestLevelPlacedAgent, PlacedAgent placedAgentDetails, String operator)
    {
        //        placeAgentContract(agentContract, )
    }

    /**
     * Copies an existing PlacedAgent group to a new location directly beneath the specied lowestLevelPlacedAgent. This is a deep
     * copy. The placedAgentToCopy group has all stop-dates to be -1 day from the specified start-date.
     * @param placedAgentToCopy
     * @param lowestLevelPlacedAgent
     * @param startDate
     * @param stopDateReasonCT
     * @param situationCode
     * @param operator
     */
    public void copyPlacedAgentGroup(PlacedAgent placedAgentToCopy,
                                     PlacedAgent lowestLevelPlacedAgent,
                                     EDITDate startDate,
                                     String stopDateReasonCT,
                                     String situationCode,
                                     String operator,
                                     String agentNumber) throws EDITAgentException
    {
        List origGroup = placedAgentToCopy.getGroup_V1();

        // 0.
        List clonedGroup = cloneGroupForCopy(origGroup, startDate, situationCode, agentNumber);

        // 1.
        prepareHierarchyLevelsForCopy(clonedGroup);

        // 2.
        setStopDatesForCopy(startDate, origGroup);

        // 3.
        buildAgentSnapshotsForCopy(stopDateReasonCT, origGroup);

        // 4.
        lowestLevelPlacedAgent.addChildGroup(clonedGroup);
    }

    /**
     * The original group to be copied needs to be cloned. This includes the associations.
     * @param group
     * @return
     */
    private List cloneGroupForCopy(List group, EDITDate startDate, String situationCode, String agentNumber)
    {
        List clonedGroup = new ArrayList();

        int size = group.size();
        
        try
        {
            for (int i = 0; i < size; i++)
            {
                // Clone the PlacedAgent and set its differing values.
                PlacedAgent placedAgentToClone = (PlacedAgent) group.get(i);

                PlacedAgent newPlacedAgent = (PlacedAgent) SessionHelper.shallowCopy(placedAgentToClone, SessionHelper.EDITSOLUTIONS);
                
                newPlacedAgent.setStartDate(startDate);
                
                newPlacedAgent.setSituationCode(situationCode);

                // Clone the PlacedAgentCommissionProfile and set its differing values.
                PlacedAgentCommissionProfile placedAgentCommissionProfileToClone = placedAgentToClone.getActivePlacedAgentCommissionProfile();
                
                PlacedAgentCommissionProfile newPlacedAgentCommissionProfile = (PlacedAgentCommissionProfile) SessionHelper.shallowCopy(placedAgentCommissionProfileToClone, SessionHelper.EDITSOLUTIONS);

                newPlacedAgentCommissionProfile.setStartDate(startDate);
                
                // Perform the necessary associations.
                CommissionProfile commissionProfile = placedAgentCommissionProfileToClone.getCommissionProfile();
                
                //commissionProfile.add(newPlacedAgentCommissionProfile); // Performance hit too big to do this. Use setter instead.
                newPlacedAgentCommissionProfile.setCommissionProfile(commissionProfile);
                
                //newPlacedAgent.add(newPlacedAgentCommissionProfile); // Performance hit too big to do this. Use setter instead.
                newPlacedAgentCommissionProfile.setPlacedAgent(newPlacedAgent);
                
                AgentContract agentContract = placedAgentToClone.getAgentContract();

                //agentContract.addPlacedAgent(newPlacedAgent); // Performance hit too big to do this. Use setter instead.
                newPlacedAgent.setAgentContract(agentContract);
                
                // This is a temporary association needed in a later process (not proud of this!)
                newPlacedAgent.setCandidatePlacedAgentCommissionProfile(newPlacedAgentCommissionProfile);

                clonedGroup.add(newPlacedAgent);
            }
        } 
        catch (Exception ex)
        {
            System.out.println(ex);
            
            ex.printStackTrace();
            
            throw new RuntimeException(ex);
        }

        return clonedGroup;
    }

    /**
     * When a hierarchy is to be copied/moved, expired PlacedAgents will not be moved. Their child entities must have
     * their hierarchy levels collapsed, and the expired PlacedAgent is removed from the group.
     * @param clonedGroup
     */
    private void prepareHierarchyLevelsForCopy(List clonedGroup)
    {
        List expiredPlacedAgents = new ArrayList();

        int size1 = clonedGroup.size();

        for (int i = 0; i < size1; i++)
        {
            PlacedAgent candidatePlacedAgent = (PlacedAgent) clonedGroup.get(i);

            if (candidatePlacedAgent.isExpired())
            {
                expiredPlacedAgents.add(candidatePlacedAgent);

                List descendents = candidatePlacedAgent.findDescendentsFromGroup(clonedGroup);

                int size2 = descendents.size();

                for (int j = 0; j < size2; j++)
                {
                    PlacedAgent descendentPlacedAgent = (PlacedAgent) descendents.get(j);

                    descendentPlacedAgent.decrementHierarchyLevel();
                }

                // The expired PlacedAgent is really a "candidate" PlacedAgent - it is a clone - be sure it doesn't get saved by reference.
                // Also note that for performance reasons, these "candidate" PlacedAgents were not created to have bi-directional 
                // relationships. So, the visibilities are:
                // (candidate)PlacedAgentCommissionProfile -> CommissionProfile
                // (candidate)PlacedAgentCommissionProfile -> PlacedAgent
                // (candidate)PlacedAgent -> AgentContract
                
                PlacedAgentCommissionProfile candidatePlacedAgentCommissionProfile = candidatePlacedAgent.getCandidatePlacedAgentCommissionProfile();
                  
                // 1. Null all references.
                candidatePlacedAgentCommissionProfile.setCommissionProfile(null);
                
                candidatePlacedAgentCommissionProfile.setPlacedAgent(null);
                
                candidatePlacedAgent.setAgentContract(null);
                
                candidatePlacedAgent.setCandidatePlacedAgentCommissionProfile(null);
                
                // 2. Delete the candidate PlacedAgentCommissionProfile  
                SessionHelper.delete(candidatePlacedAgentCommissionProfile, SessionHelper.EDITSOLUTIONS);
                
                // 3. Delete the candidate PlacedAgent since it has already been added to the 
                // Hibernate Session upon its original creation.
                SessionHelper.delete(candidatePlacedAgent, SessionHelper.EDITSOLUTIONS);
            }
        }

        clonedGroup.removeAll(expiredPlacedAgents);
    }

    /**
     * Verifies:
     * An agent can't be placed under himself within the same contract.
     * @param parentPlacedAgent
     * @param childPlacedAgent
     */
    protected void checkPlacementConstraints(PlacedAgent parentPlacedAgent, PlacedAgent childPlacedAgent) throws EDITAgentException
    {
        // Root PlacedAgents don't even need to be tested.
        if (parentPlacedAgent != null)
        {
            AgentContract parentAgentContract = parentPlacedAgent.getAgentContract();

            AgentContract childAgentContract = childPlacedAgent.getAgentContract();

            if (parentAgentContract.equals(childAgentContract))
            {
                throw new EDITAgentException("An Agent Can Not Be Placed Under Oneself Within The Same Contract");
            }
        }
    }

    /**
     * Finds all current placements of the specified PlacedAgent in all AgentSnapshots. This is most easily accomplished by
     * using the [redundant] information of the AgentHierachy which stores the Agent of the lowest level Agent in its
     * corresponding AgentSnapshot elements.
     * @param stopDateReasonCT
     * @param placedAgents
     */
    private void buildAgentSnapshotsForCopy(String stopDateReasonCT, List placedAgents)
    {
        int size = placedAgents.size();

        for (int i = 0; i < size; i++)
        {
            if (shouldBuildAgentShapshots(stopDateReasonCT))
            {
                PlacedAgent placedAgent = (PlacedAgent) placedAgents.get(i);

                Agent agent = placedAgent.getAgentContract().getAgent();

                AgentHierarchy[] agentHierarchies = AgentHierarchy.findBy_AgentPK(agent.getAgentPK());

                if (agentHierarchies != null)
                {
                    for (int j = 0; j < agentHierarchies.length; j++)
                    {
                        AgentHierarchy agentHierarchy = (AgentHierarchy) agentHierarchies[i].cloneCRUDEntity();

                        agentHierarchy.setAgentSnapshots(new HashSet());

                        agentHierarchy.hSave();

                        agentHierarchy.generateAgentSnapshot(placedAgent);
                    }
                }
            }
        }
    }

    /**
     * When a PlacedAgent group is moved, each PlacedAgent' (prime) may have an existing Snapshot driven by its
     * original PlacedAgent. If the stopDateReasonCT = "HierachyMoveAll", then an attempt should be made to build
     * new AgentSnapshots for each PlacedAgent'.
     * @param stopDateReasonCT
     * @return
     */
    private boolean shouldBuildAgentShapshots(String stopDateReasonCT)
    {
        return (stopDateReasonCT.equals(Hierarchy.STOPDATEREASONCT_HIERARCHYMOVE_TRANSER)); //To change body of created methods use File | Settings | File Templates.
    }

    /**
     * If the new StopDate is < old Stop Date, or if the original StopDate is the Max Default Date, then the
     * new StopDate is accepted, otherwise the old StopDate is kept. These changes are saved.
     * @param placedAgents
     */
    private void setStopDatesForCopy(EDITDate startDate, List placedAgents)
    {
        int size = placedAgents.size();

        for (int i = 0; i < size; i++)
        {
            PlacedAgent origPlacedAgent = (PlacedAgent) placedAgents.get(i);
            
            EDITDate origStartDate = origPlacedAgent.getStartDate();
            
            EDITDate origStopDate = origPlacedAgent.getStopDate();
            
            // The stopDate must be > startDate otherwise we end up with a stopDate < startDate after subtracting 1 day.
            if (origStopDate.after(origStartDate))
            {
                EDITDate candidateStopDate = startDate;
                
                candidateStopDate = candidateStopDate.subtractDays(1);
                
                if (candidateStopDate.before(origStopDate) || origStopDate.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
                {
                    origPlacedAgent.setStopDate(candidateStopDate);
                    
                    origPlacedAgent.hSave();
                }
            }
        }
    }
}
