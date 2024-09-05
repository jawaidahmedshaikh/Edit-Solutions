/*
 * User: gfrosti
 * Date: Oct 11, 2003
 * Time: 12:52:04 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import agent.common.Constants;

import contract.dm.composer.VOComposer;

import edit.common.exceptions.EDITAgentException;
import edit.common.vo.AgentSnapshotVO;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.PlacedAgentVO;
import edit.services.db.hibernate.SessionHelper;

import java.util.*;

import role.ClientRole;



public class HierarchyImpl
{
    protected void placeAgent(Hierarchy hierarchy, PlacedAgent placedAgent, PlacedAgent parentPlacedAgent, String agentNumber) throws EDITAgentException
    {
        try
        {
//            hierarchy.checkPlacementConstraints(parentPlacedAgent, placedAgent);

            if (placedAgent.isIndexed())
            {
                throw new EDITAgentException(Constants.ErrorMsg.AGENT_ALREADY_INDEXED);
            }

            AgentContract agentContract = placedAgent.getAgentContract();

            boolean newSituationConflicts = agentContract.situationConflicts(placedAgent, agentNumber);

            if (newSituationConflicts)
            {
                throw new EDITAgentException(Constants.ErrorMsg.INVALID_SITUATION);
            }

            if (parentPlacedAgent == null) // Root Agent - Trivial case
            {
                long leftBoundary = 0;

                long rightBoundary = 0;

                PlacedAgent maxRootAgent = PlacedAgent.findBy_MaxRightBoundary();

                if (maxRootAgent == null)
                {
                    rightBoundary = rightBoundary + PlacedAgent.ROOT_INTERVAL;
                }
                else
                {
                    long maxRight = ((PlacedAgentVO) maxRootAgent.getVO()).getRightBoundary();

                    leftBoundary = ((long) Math.floor((Math.abs(maxRight - 1)) / PlacedAgent.ROOT_INTERVAL) * PlacedAgent.ROOT_INTERVAL) + PlacedAgent.ROOT_INTERVAL;

                    rightBoundary = leftBoundary + PlacedAgent.ROOT_INTERVAL;
                }

                placedAgent.setIndexes(leftBoundary, rightBoundary, 0);

                placedAgent.setModifyingEvent(PlacedAgent.EVENT_NEW_ROOT);

                placedAgent.commitIndexChanges();

                placedAgent.hSave();
            }
            else
            {
                parentPlacedAgent.addChildAgent(placedAgent);
            }
        }
        catch (EDITAgentException e)
        {
            System.out.println(e);
            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
            throw e;
        }
    }

    protected void swap(PlacedAgent placedAgentA, PlacedAgent placedAgentB) throws Exception
    {
        long aLeftBoundary = ((PlacedAgentVO) placedAgentA.getVO()).getLeftBoundary();
        long aRightBoundary = ((PlacedAgentVO) placedAgentA.getVO()).getRightBoundary();
        int aHierarchyLevel = ((PlacedAgentVO) placedAgentA.getVO()).getHierarchyLevel();

        long bLeftBoundary = ((PlacedAgentVO) placedAgentB.getVO()).getLeftBoundary();
        long bRightBoundary = ((PlacedAgentVO) placedAgentB.getVO()).getRightBoundary();
        int bHierarchyLevel = ((PlacedAgentVO) placedAgentB.getVO()).getHierarchyLevel();

        ((PlacedAgentVO) placedAgentA.getVO()).setLeftBoundary(bLeftBoundary);
        ((PlacedAgentVO) placedAgentA.getVO()).setRightBoundary(bRightBoundary);
        ((PlacedAgentVO) placedAgentA.getVO()).setHierarchyLevel(bHierarchyLevel);

        ((PlacedAgentVO) placedAgentB.getVO()).setLeftBoundary(aLeftBoundary);
        ((PlacedAgentVO) placedAgentB.getVO()).setRightBoundary(aRightBoundary);
        ((PlacedAgentVO) placedAgentB.getVO()).setHierarchyLevel(aHierarchyLevel);

        placedAgentA.hSave();
        placedAgentB.hSave();
    }

    protected void removePlacedAgent(PlacedAgent placedAgent) throws EDITAgentException
    {
        if (placedAgentHasDependencies(placedAgent))
        {
            throw new EDITAgentException(Constants.ErrorMsg.AGENT_HAS_DEPENDENCIES);
        }

        List group = placedAgent.getGroup_V1();

        String operator = placedAgent.getOperator();

        Long placedAgentPK = placedAgent.getPlacedAgentPK();
        
        int size = group.size();

        // Promote children up 1 level in the tree
        for (int i = 0; i < size; i++)
        {
            PlacedAgent currentPlacedAgent = (PlacedAgent) group.get(i);

            if (currentPlacedAgent.getPK() != placedAgentPK.longValue()) // The deleted agent needs to be skipped
            {
                int currentHierarchyLevel = currentPlacedAgent.getHierarchyLevel();

                currentPlacedAgent.setHierarchyLevel(currentHierarchyLevel - 1);

                currentPlacedAgent.setOperator(operator);

                currentPlacedAgent.setModifyingEvent(PlacedAgent.EVENT_SHIFTED_UP);

                currentPlacedAgent.hSave();
            }
        }

        ClientRole clientRole = placedAgent.getClientRole();

        placedAgent.hDelete();

        PlacedAgent[] placedAgents = PlacedAgent.findBy_ClientRoleFK_Exclusion(clientRole.getClientRolePK(), placedAgent.getPlacedAgentPK());
        if (placedAgents.length == 0)
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            clientRole.setReferenceID(null);

            SessionHelper.saveOrUpdate(clientRole, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }

        group.clear();
    }

    /**
     * Checks to see if a PlacedAgent has been associated with an AgentSnapshot or a CommissionHistory
     * @param placedAgent
     * @return true if the PlacedAgent has an association
     */
    private boolean placedAgentHasDependencies(PlacedAgent placedAgent)
    {
        boolean hasDependencies = false;

        AgentSnapshotVO[] agentSnapshotVOs = null;

        CommissionHistoryVO[] commissionHistoryVOs = null;

        try
        {
            agentSnapshotVOs = new VOComposer().composeAgentSnapshotVOsByPlacedAgentFK(placedAgent.getPK(), new ArrayList());

            commissionHistoryVOs = new event.dm.composer.VOComposer().composeCommissionHistoryVOByPlacedAgentPK(placedAgent.getPK(), new ArrayList());

            if (agentSnapshotVOs != null)
            {
                hasDependencies = true;
            }
            else if (commissionHistoryVOs != null)
            {
                hasDependencies = true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }

        return hasDependencies;
    }
}
