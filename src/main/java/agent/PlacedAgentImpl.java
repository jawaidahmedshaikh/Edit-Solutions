/*
 * User: gfrosti
 * Date: Oct 11, 2003
 * Time: 1:08:51 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import agent.dm.dao.DAOFactory;
import edit.common.EDITDateTime;
import edit.common.exceptions.*;
import edit.common.vo.PlacedAgentVO;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;


public class PlacedAgentImpl extends CRUDEntityImpl
{
    protected void delete(PlacedAgent placedAgent)
    {
        super.delete(placedAgent, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void load(PlacedAgent placedAgent, long pk)
    {
        super.load(placedAgent, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected PlacedAgent getParent(PlacedAgent placedAgent)
    {
        PlacedAgent parentPlacedAgent = null;

        try
        {
            long leftBoundary = ((PlacedAgentVO) placedAgent.getVO()).getLeftBoundary();
            long rightBoundary = ((PlacedAgentVO) placedAgent.getVO()).getRightBoundary();
            int hierarchyLevel = ((PlacedAgentVO) placedAgent.getVO()).getHierarchyLevel();

            if (hierarchyLevel > 0)
            {
                PlacedAgentVO[] parentPlacedAgentVO = DAOFactory.getPlacedAgentDAO().
                        findByHiearchyLevel_AND_LeftBoundaryLTE_AND_RightBoundaryGTE(hierarchyLevel - 1, // Parent Level
                                                                                     leftBoundary,
                                                                                     rightBoundary
                        );

                if (parentPlacedAgentVO != null)
                {
                    parentPlacedAgent = new PlacedAgent(parentPlacedAgentVO[0]);
                }
            }
        }
        catch (EDITAgentException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return parentPlacedAgent;
    }

    protected void save(PlacedAgent placedAgent)
    {
        PlacedAgentVO placedAgentVO = (PlacedAgentVO) placedAgent.getVO();

        placedAgentVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

        super.save(placedAgent, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected static PlacedAgent[] findByAgentContractPK(long agentContractPK)
    {
        PlacedAgentVO[] placedAgentVO = DAOFactory.getPlacedAgentDAO().findByAgentContractPK(agentContractPK);

        PlacedAgent[] placedAgent = null;

        try
        {
            if (placedAgentVO != null)
            {
                placedAgent = new PlacedAgent[placedAgentVO.length];

                for (int i = 0; i < placedAgentVO.length; i++)
                {
                    placedAgent[i] = new PlacedAgent(placedAgentVO[i]);
                }
            }
        }
        catch (EDITAgentException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return placedAgent;
    }

    protected boolean boundariesConflict(PlacedAgent placedAgent, PlacedAgent testPlacedAgent)
    {
        boolean boundariesConflict = false;

        PlacedAgentVO placedAgentVO = (PlacedAgentVO) placedAgent.getVO();
        long leftBoundary = placedAgentVO.getLeftBoundary();
        long rightBoundary = placedAgentVO.getRightBoundary();
        int hierarchyLevel = placedAgentVO.getHierarchyLevel();

        PlacedAgentVO testPlacedAgentVO = (PlacedAgentVO) testPlacedAgent.getVO();
        long testLeftBoundary = testPlacedAgentVO.getLeftBoundary();
        long testRightBoundary = testPlacedAgentVO.getRightBoundary();
        int testHierarchyLevel = testPlacedAgentVO.getHierarchyLevel();

        if (placedAgent.equals(testPlacedAgent)) // trivial case
        {
            boundariesConflict = false;
        }
        else if (hierarchyLevel != testHierarchyLevel) // different hierarchy levels can never conflict
        {
            boundariesConflict = false;
        }
        else if ( ((testLeftBoundary > leftBoundary) && (testLeftBoundary < rightBoundary) ) ||
                ( (testRightBoundary > leftBoundary) && (testRightBoundary < rightBoundary) ) ) // are there overlapping left and right boundaries?
        {
            boundariesConflict = true;
        }
        else if ( (testLeftBoundary == leftBoundary) && (testRightBoundary == rightBoundary) )
        {
            boundariesConflict = true;
        }

        return boundariesConflict;
    }
}
