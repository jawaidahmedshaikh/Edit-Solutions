package agent;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.hibernate.*;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 25, 2003
 * Time: 3:50:56 PM
 * To change this template use Options | File Templates.
 */
public class PlacedAgentBranch
{
    public static final int ASCENDING = 0;
    public static final int DESCENDING = 1;
    private PlacedAgent[] placedAgentBranch;
    private int branchDepth;

    /**
     * The current sort order of the the PlacedAgent elements.
     * @see #ASCENDING
     * @see #DESCENDING
     */
    private int currentSortOrder = -1;

    /**
     * Determines the number of branch nodes to include starting from the leaf. Branches can be expensive to construct
     * in a large PlacedAgent system. The resulting branch contains PlacedAgent nodes, but each PlacedAgent has AgentContract,
     * Agent, ClientRole, ClientDetail and CommissionProfile associated objects.
     * @param leafPlacedAgent
     * @param branchDepth
     */
    public PlacedAgentBranch(PlacedAgent leafPlacedAgent, int branchDepth)
    {
        this.branchDepth = branchDepth;

        buildPlacedAgentBranch(leafPlacedAgent, branchDepth);
    }

    public PlacedAgentBranch(PlacedAgent leafPlacedAgent)
    {
        this(leafPlacedAgent, 0);
    }

    public PlacedAgentBranchVO getVO()
    {
        PlacedAgentBranchVO placedAgentBranchVO = null;

        if (placedAgentBranch != null)
        {
            placedAgentBranchVO = new PlacedAgentBranchVO();

            for (int i = 0; i < placedAgentBranch.length; i++)
            {
                placedAgentBranchVO.addPlacedAgentVO((PlacedAgentVO) placedAgentBranch[i].getVO());
            }
        }

        return placedAgentBranchVO;
    }

    /**
     * A STATELESS query that finds the set of PlacedAgent nodes that constitute this PlacedAgentBranch. each node in the branch has its
     * associated AgentContract, Agent, ClientRole, ClientDetail, CommissionProfile. The default order of the 
     * elemental PlacedAgents is ascending HierarchyLevel (e.g. the Root is the 0th element, and the Writing Agent is the nth - 1)
     * @param placedAgent
     */
    private final void buildPlacedAgentBranch(PlacedAgent placedAgent, int branchDepth)
    {
        long leftBoundary = ((PlacedAgentVO) placedAgent.getVO()).getLeftBoundary();
        long rightBoundary = ((PlacedAgentVO) placedAgent.getVO()).getRightBoundary();

        int maxHierarchyLevel = ((PlacedAgentVO) placedAgent.getVO()).getHierarchyLevel();
        int minHierarchyLevel = 0;

        if (branchDepth > 0)
        {
            minHierarchyLevel = maxHierarchyLevel - branchDepth + 1;
        }

        String hql = "select p from PlacedAgent p" +
                    " join fetch p.AgentContract ac" +
                    " join fetch ac.Agent a" +
                    " join fetch p.ClientRole cr" +
                    " join fetch cr.ClientDetail cr" +
                    " join fetch p.PlacedAgentCommissionProfiles placedAgentCommissionProfile" +
                    " left join fetch placedAgentCommissionProfile.CommissionProfile cp" +
                    " where p.LeftBoundary <= :leftBoundary" + " and p.RightBoundary >= :rightBoundary" + " and p.HierarchyLevel between :minHierarchyLevel and :maxHierarchyLevel";

        Map params = new HashMap();
        params.put("leftBoundary", new Long(leftBoundary));
        params.put("rightBoundary", new Long(rightBoundary));
        params.put("maxHierarchyLevel", new Integer(maxHierarchyLevel));
        params.put("minHierarchyLevel", new Integer(minHierarchyLevel));

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
        
        results = SessionHelper.makeUnique(results);

        placedAgentBranch = (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);

        sort(PlacedAgentBranch.ASCENDING);
    }
    
    private int getPlacedAgentCount()
    {
        return placedAgentBranch.length;
    }

    public PlacedAgent getPlacedAgent(int index)
    {
        return placedAgentBranch[index];
    }

    private int getIndex(PlacedAgent placedAgent) throws Exception
    {
        int branchIndex = -1;

        for (int i = 0; i < placedAgentBranch.length; i++)
        {
            if (placedAgentBranch[i].equals(placedAgent))
            {
                branchIndex = i;
            }
        }

        return branchIndex;
    }

    protected void shift(PlacedAgent placedAgent, int direction) throws Exception
    {
        int indexOfPlacedAgent = getIndex(placedAgent);

        int indexOfNeighbor = ((direction > 0) ? (indexOfPlacedAgent - 1) : (indexOfPlacedAgent + 1));

        if ((indexOfNeighbor == getPlacedAgentCount()) || (indexOfNeighbor == -1)) // Not a valid neighbor.
        {
            return; // Keep the same branch - don't change it.
        }

        else
        {
            PlacedAgent neighborPlacedAgent = getPlacedAgent(indexOfNeighbor);

            Hierarchy.getSingleton().swap(placedAgent, neighborPlacedAgent);

            placedAgentBranch[indexOfNeighbor] = placedAgent;
            placedAgentBranch[indexOfPlacedAgent] = neighborPlacedAgent;
        }
    }

    /**
     * Retrieves the root of the branch.
     * @return the root of the branch
     */
    public PlacedAgent getRoot()
    {
        PlacedAgent root = null;
        
        if (getCurrentSortOrder() == ASCENDING)
        {
          root = getPlacedAgent(0);
        }
        else if (getCurrentSortOrder() == DESCENDING)
        {
          root = getPlacedAgent(getPlacedAgentCount() - 1);
        }
        else
        {
          throw new RuntimeException("A PlacedAgentBranch sort order is not set.");
        }
    
        return root;
    }

    /**
     * Retrieves the leaf of the branch.
     * @return the leaf of the branch
     */
    public PlacedAgent getLeaf()
    {
      PlacedAgent leaf = null;
      
      if (getCurrentSortOrder() == ASCENDING)
      {
        leaf = getPlacedAgent(getPlacedAgentCount() - 1);
      }
      else if (getCurrentSortOrder() == DESCENDING)
      {
        leaf = getPlacedAgent(0);
      }
      else
      {
        throw new RuntimeException("A PlacedAgentBranch sort order is not set.");
      }
      
      return leaf;
    }

    /**
     * Two PlacedAgentBranches are equal of they contain the same number of PlacedAgent elements with matching PKs.
     * @param o the PlacedAgentBranch to compare to
     * @return true if the two PlacedAgentBranches are equal
     */
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof PlacedAgentBranch))
        {
            return false;
        }

        final PlacedAgentBranch placedAgentBranch1 = (PlacedAgentBranch) o;

        if (!Arrays.equals(placedAgentBranch, placedAgentBranch1.placedAgentBranch))
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return super.hashCode(); //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Removes all PlacedAgents from the branch that fail to satisfy (StartDate <= date <= StopDate)
     * @param date
     * @return true if PlacedAgent elements still exist after the filter is applied, false otherwise
     */
    public boolean filter(EDITDate date)
    {
        List placedAgentsToKeep = new ArrayList();

        boolean placedAgentElementsExist = false;

        for (int i = 0; i < placedAgentBranch.length; i++)
        {
            Situation situation = placedAgentBranch[i].getSituation();

            if (situation.dateIsInRange(date))
            {
                placedAgentsToKeep.add(placedAgentBranch[i]);
            }
        }

        if (placedAgentsToKeep.isEmpty())
        {
            placedAgentBranch = null;
        }
        else if (!placedAgentsToKeep.contains(getLeaf())) // If the leaf has been removed, then this branch is gone by definition.
        {
            placedAgentBranch = null;
        }
        else
        {
            placedAgentBranch = (PlacedAgent[]) placedAgentsToKeep.toArray(new PlacedAgent[placedAgentsToKeep.size()]);

            placedAgentElementsExist = true;
        }

        return placedAgentElementsExist;
    }

    /**
     * Uses the leafPlacedAgentPK to build the associated PlacedAgentBranch. Each PlacedAgent element of the branch
     * is associated with AgentContract, Agent, ClientRole, ClientDetail, and CommissionProfile
     * @param leafPlacedAgentPK
     * @return
     */
    public static final PlacedAgentBranch findBy_PlacedAgentPK(Long leafPlacedAgentPK)
    {
        PlacedAgent leafPlacedAgent = PlacedAgent.findBy_PlacedAgentPK(leafPlacedAgentPK);

        PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(leafPlacedAgent);

        return placedAgentBranch;
    }

    /**
     * Uses the agentContractPK to build the associated PlacedAgentBranch. Each PlacedAgent element of the branch
     * is associated with AgentContract, Agent, ClientRole, ClientDetail, and CommissionProfile
     * @param agentContractPK
     * @return
     */
    public static final PlacedAgentBranch[] findBy_AgentContractPK(Long agentContractPK, int branchDepth)
    {
        List placedAgentBranches = new ArrayList();

        PlacedAgent[] placedAgents = PlacedAgent.findBy_AgentContractPK(agentContractPK);

        for (int i = 0; i < placedAgents.length; i++)
        {
            PlacedAgent placedAgent = placedAgents[i];

            PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(placedAgent);

            placedAgentBranches.add(placedAgentBranch);
        }

        return (PlacedAgentBranch[]) placedAgentBranches.toArray(new PlacedAgentBranch[placedAgentBranches.size()]);
    }

    /**
     * STATELESS Finder.
     * @see PlacedAgentBranch(PlacedAgent, int)
     * @param agentId
     * @param contractCodeCT
     * @return
     */
    public static final PlacedAgentBranch[] findBy_AgentId_AND_ContractCodeCT(String agentId, String contractCodeCT, int branchDepth)
    {
        List placedAgentBranches = new ArrayList();

        PlacedAgent[] placedAgents = PlacedAgent.findBy_AgentId_AND_ContractCodeCT(agentId, contractCodeCT);

        for (int i = 0; i < placedAgents.length; i++)
        {
            PlacedAgent placedAgent = placedAgents[i];

            PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(placedAgent, branchDepth);

            placedAgentBranches.add(placedAgentBranch);
        }

        return (PlacedAgentBranch[]) placedAgentBranches.toArray(new PlacedAgentBranch[placedAgentBranches.size()]);
    }

    /**
     * Finder.
     * @param agentName
     * @param contractCodeCT
     * @return
     */
    public static PlacedAgentBranch[] findBy_AgentName_AND_ContractCodeCT(String agentName, String contractCodeCT, int branchDepth)
    {
        List placedAgentBranches = new ArrayList();

        PlacedAgent[] placedAgents = PlacedAgent.findBy_AgentName_AND_ContractCodeCT(agentName, contractCodeCT);

        for (int i = 0; i < placedAgents.length; i++)
        {
            PlacedAgent placedAgent = placedAgents[i];

            PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(placedAgent, branchDepth);

            placedAgentBranches.add(placedAgentBranch);
        }

        return (PlacedAgentBranch[]) placedAgentBranches.toArray(new PlacedAgentBranch[placedAgentBranches.size()]);
    }
    
    /**
     * Finder.
     * @param agentName
     * @param contractCodeCT
     * @param stopDate to prevent picking-up expired PlacedAgents
     * @return
     */    
    public static PlacedAgentBranch[] findBy_AgentName_AND_ContractCodeCT_AND_StopDate(String agentName, String contractCodeCT, EDITDate stopDate, int branchDepth)
    {           
        List placedAgentBranches = new ArrayList();

        PlacedAgent[] placedAgents = PlacedAgent.findBy_AgentName_AND_ContractCodeCT_AND_StopDate(agentName, contractCodeCT, stopDate);

        for (int i = 0; i < placedAgents.length; i++)
        {
            PlacedAgent placedAgent = placedAgents[i];
            
            PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(placedAgent, branchDepth);

            placedAgentBranches.add(placedAgentBranch);
        }

        return (PlacedAgentBranch[]) placedAgentBranches.toArray(new PlacedAgentBranch[placedAgentBranches.size()]);                
    }
    
    /**
     * Finder.
     * @param agentName
     * @param contractCodeCT
     * @param stopDate to prevent picking-up expired PlacedAgents
     * @return
     */    
    public static PlacedAgentBranch[] findBy_AgentNumber_AND_ContractCodeCT_AND_StopDate(String agentNumber, String contractCodeCT, EDITDate stopDate, int branchDepth)
    {           
        List placedAgentBranches = new ArrayList();

        PlacedAgent[] placedAgents = PlacedAgent.findBy_AgentNumber_AND_ContractCodeCT_AND_StopDate(agentNumber, contractCodeCT, stopDate);

        for (int i = 0; i < placedAgents.length; i++)
        {
            PlacedAgent placedAgent = placedAgents[i];
            
            PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(placedAgent, branchDepth);

            placedAgentBranches.add(placedAgentBranch);
        }

        return (PlacedAgentBranch[]) placedAgentBranches.toArray(new PlacedAgentBranch[placedAgentBranches.size()]);                
    }    

    /**
     * The set of PlacedAgents that make up this PlacedAgentBranch.
     * @return
     */
    public PlacedAgent[] getPlacedAgents()
    {
        return placedAgentBranch;
    }

    /**
      * By default, the branch elements are ordered by the PlacedAgent.HierarchyLevel ascending.
      * The order can, however, be explictly specified.
      */
    public void sort(int order)
    {
      List elements = Arrays.asList(getPlacedAgents());
  
      Collections.sort(elements);
  
      if (order == DESCENDING)
      {
        Collections.reverse(elements);
      }
  
      setCurrentSortOrder(order);
    }

  /**
   * Set internally anytime the PlacedAgent elements of this
   * PlacedAgentBranch are re-ordered.
   * @param currentSortOrder
   * @see #currentSortOrder
   */
  private void setCurrentSortOrder(int currentSortOrder)
  {
    this.currentSortOrder = currentSortOrder;
  }
  
  /**
   * The current sort state of this PlacedAgentBranch.
   * @see #ASCENDING
   * @see #DESCENDING
   * @return
   */
  public int getCurrentSortOrder()
  {
    return currentSortOrder;
  }
}
