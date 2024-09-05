/*
 * User: gfrosti
 * Date: Aug 26, 2003
 * Time: 1:47:19 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import agent.common.Constants;

import agent.dm.dao.DAOFactory;
import agent.dm.dao.PlacedAgentDAO;

import contract.AgentSnapshot;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITAgentException;
import edit.common.vo.PlacedAgentVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import event.*;

import fission.utility.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import role.ClientRole;



public class PlacedAgent extends HibernateEntity implements Comparable, CRUDEntity, Cloneable
{
    // ------------------------------ FIELDS ------------------------------
    public static final long ROOT_INTERVAL = 10000000000000L; // 10 trillion (formally 1 billion)
    public static final int SHIFT_UP = 1;
    public static final int SHIFT_DOWN = -1;
    public static final String EVENT_NEW_ROOT = "NEW ROOT";
    public static final String EVENT_NEW_CHILD = "NEW CHILD";
    public static final String EVENT_SHIFTED_UP = "SHIFTED UP";
    public static final String EVENT_SHIFTED_DOWN = "SHIFTED DOWN";
    public static final String EVENT_REINDEXED = "REINDEXED";
    public static final int DEFAULT_NEW_LEFT_BOUNDARY = -1;
    public static final int DEFAULT_NEW_RIGHT_BOUNDARY = -1;
    public static final int DEFAULT_NEW_HIERARCHY_LEVEL = -1;
    private PlacedAgentVO placedAgentVO;
    private PlacedAgent parentPlacedAgent;
    private PlacedAgentImpl placedAgentImpl;
    private long newLeftBoundary = DEFAULT_NEW_LEFT_BOUNDARY;
    private long newRightBoundary = DEFAULT_NEW_RIGHT_BOUNDARY;
    private int newHierarchyLevel = DEFAULT_NEW_HIERARCHY_LEVEL;
    private AgentContract agentContract;
    private Set participatingAgents;
    private Set commissionHistories;
    private Set<PlacedAgentCommissionProfile> placedAgentCommissionProfiles;
    private Set<AgentSnapshot> agentSnapshots;
    private Set agentGroupAssociations;
    private Set <CheckAdjustment> checkAdjustments;
    private ClientRole clientRole;

    /**
     *  There is a tremendous performance hit when adding
     *  PlacedAgentCommissionProfile to PlacedAgent. Those performance
     *  tweaks have forced the use of a "candidate" PlacedAgentCommissionProfile that 
     *  will often be discarded and never persisted to the DB.
     */
    private PlacedAgentCommissionProfile candidatePlacedAgentCommissionProfile;
    
    /**
     *  A performance tune - stores the current active PlacedAgentCommissionProfile
     *  as defined by its respective method (check @see). This is a read-only property
     *  that gets nulled whenever a new PlacedAgentCommissionProfile is made.
     *  @see #getActivePlacedAgentCommissionProfile()
     */
    private PlacedAgentCommissionProfile activePlacedAgentCommissionProfile = null;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    // --------------------------- CONSTRUCTORS ---------------------------
    public PlacedAgent()
    {
        placedAgentVO = new PlacedAgentVO();
        this.placedAgentImpl = new PlacedAgentImpl();
        this.agentGroupAssociations = new HashSet();
        this.participatingAgents = new HashSet();
        this.commissionHistories = new HashSet();
        this.placedAgentCommissionProfiles = new HashSet<PlacedAgentCommissionProfile>();
        this.agentSnapshots = new HashSet<AgentSnapshot>();
        this.checkAdjustments = new HashSet<CheckAdjustment>();
        
        setDefaults();
    }
    
    public PlacedAgent(long placedAgentPK)
    {
        this();
        placedAgentImpl.load(this, placedAgentPK);
    }
    
    /**
     * Constructor.
     *
     * @param placedAgentVO
     * @throws EDITAgentException if initial state validations fail
     * @see PlacedAgent#validate()
     */
    public PlacedAgent(PlacedAgentVO placedAgentVO) throws EDITAgentException
    {
        this();
        this.placedAgentVO = placedAgentVO;
        setDefaults();
        validate();
    }
    
    /**
     * Getter.
     *
     * @return
     */
    public Set getCommissionHistories()
    {
        return commissionHistories;
    }
    
    /**
     * Setter.
     *
     * @param commissionHistories
     */
    public void setCommissionHistories(Set commissionHistories)
    {
        this.commissionHistories = commissionHistories;
    }
 

    /**
     * Removes a AgentSnapshot from the set of children
     * @param agentSnapshot
     */
    public void removeAgentSnapshot(AgentSnapshot agentSnapshot)
    {
        this.getAgentSnapshots().remove(agentSnapshot);

        agentSnapshot.setPlacedAgent(null);

        SessionHelper.saveOrUpdate(agentSnapshot, PlacedAgent.DATABASE);
    }


    public Long getAgentContractFK()
    {
        return SessionHelper.getPKValue(placedAgentVO.getAgentContractFK());
    }

    public Long getClientRoleFK()
    {
        return SessionHelper.getPKValue(placedAgentVO.getClientRoleFK());
    }


    //-- long getCommissionProfileFK()
    public String getInactiveIndicator()
    {
        return placedAgentVO.getInactiveIndicator();
    }
    
    //-- java.lang.String getInactiveIndicator()
    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(placedAgentVO.getMaintDateTime());
    }
    
    //-- java.lang.String getMaintDateTime()
    public String getModifyingEvent()
    {
        return placedAgentVO.getModifyingEvent();
    }
    
    //-- java.lang.String getModifyingEvent()
    public String getOperator()
    {
        return placedAgentVO.getOperator();
    }
    
    //-- java.lang.String getOperator()
    public Long getPlacedAgentPK()
    {
        return SessionHelper.getPKValue(placedAgentVO.getPlacedAgentPK());
    }
    
    //-- long getPlacedAgentPK()
    public String getStopDateReasonCT()
    {
        return placedAgentVO.getStopDateReasonCT();
    }

    //-- java.lang.String getStopDateReasonCT()

    public void setAgentContractFK(Long agentContractFK)
    {
        placedAgentVO.setAgentContractFK(SessionHelper.getPKValue(agentContractFK));
    }

    /**
     * Setter
     * @param clientRoleFK
     */
    public void setClientRoleFK(Long clientRoleFK)
    {
        placedAgentVO.setClientRoleFK(SessionHelper.getPKValue(clientRoleFK));
    }

    //-- void setCommissionProfileFK(long)
    public void setInactiveIndicator(String inactiveIndicator)
    {
        placedAgentVO.setInactiveIndicator(inactiveIndicator);
    }
    
    //-- void setInactiveIndicator(java.lang.String)
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        placedAgentVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    }
    
    //-- void setMaintDateTime(java.lang.String)
    public void setPlacedAgentPK(Long placedAgentPK)
    {
        placedAgentVO.setPlacedAgentPK(SessionHelper.getPKValue(placedAgentPK));
    }
    
    //-- void setPlacedAgentPK(long)
    public void setStopDate(String stopDate)
    {
        placedAgentVO.setStopDate(stopDate);
    }
    
    //-- void setStopDate(java.lang.String)
    public void setStartDate(String startDate)
    {
        placedAgentVO.setStartDate(startDate);
    }
    
    //-- void setStartDate(java.lang.String)
    //-- void setStopDateReasonCT(java.lang.String)
    
    /**
     * Validates initial state requirements.
     *
     * @throws EDITAgentException if startDate is missing.
     */
    public void validate() throws EDITAgentException
    {
        EDITDate startDate = getStartDate();
        
        if (startDate == null)
        {
            throw new EDITAgentException(Constants.ErrorMsg.START_DATE_REQUIRED);
        }
        
        EDITDate stopDate = null;
        
        if (stopDate != null)
        {
            if (startDate.after(stopDate))
            {
                throw new EDITAgentException(Constants.ErrorMsg.STOP_DATE_LESS_THAN_START_DATE);
            }
        }
    }
    
    //-- java.lang.String getSituation()
    public EDITDate getStartDate()
    {
        return SessionHelper.getEDITDate(placedAgentVO.getStartDate());
    }
    
    /**
     * The Situation.
     *
     * @return the Situation
     */
    public Situation getSituation()
    {
        Situation situation = new Situation(this);
        
        return situation;   
    }
    
    public String getSituationCode()
    {
        return placedAgentVO.getSituationCode();
    }
    
    //-- java.lang.String getStartDate()
    public EDITDate getStopDate()
    {
        return SessionHelper.getEDITDate(placedAgentVO.getStopDate());
    }
    
    // ------------------------ CANONICAL METHODS ------------------------
    public boolean equals(Object obj)
    {
        boolean areEqual = false;
        
        long thisPK = getPK();
        
        long visitingPK = ((PlacedAgent) obj).getPK();
        
        if ((thisPK == 0) && (visitingPK == 0))
        {
            areEqual = super.equals(obj);
        }
        else
        {
            areEqual = (placedAgentVO.getPlacedAgentPK() == ((PlacedAgent) obj).getPK());
        }
        
        return areEqual;
    }
    
    public long getPK()
    {
        return placedAgentVO.getPlacedAgentPK();
    }
    
    // ------------------------ INTERFACE METHODS ------------------------
    // --------------------- Interface CRUDEntity ---------------------
    public void delete()
    {
        placedAgentImpl.delete(this);
    }
    
    public VOObject getVO()
    {
        return placedAgentVO;
    }
    
    public void setVO(VOObject voObject)
    {
        this.placedAgentVO = (PlacedAgentVO) voObject;
    }
    
    public boolean isNew()
    {
        return placedAgentImpl.isNew(this);
    }
    
    public CRUDEntity cloneCRUDEntity()
    {
        return placedAgentImpl.cloneCRUDEntity(this);
    }
    
    /**
     * Clones this PlacedAgent but not its associations. All PKs and FKs are nulled.
     *
     * @return
     */
    public Object clone()
    {
        PlacedAgent clonedPlacedAgent = null;
        clonedPlacedAgent = new PlacedAgent();
        
        clonedPlacedAgent.setAgentContractFK(null);
        clonedPlacedAgent.setHierarchyLevel(getHierarchyLevel());
        clonedPlacedAgent.setInactiveIndicator(getInactiveIndicator());
        clonedPlacedAgent.setLeftBoundary(getLeftBoundary());
        clonedPlacedAgent.setMaintDateTime(getMaintDateTime());
        clonedPlacedAgent.setModifyingEvent(getModifyingEvent());
        clonedPlacedAgent.setNewHierarchyLevel(getNewHierarchyLevel());
        clonedPlacedAgent.setNewLeftBoundary(getNewLeftBoundary());
        clonedPlacedAgent.setNewRightBoundary(getNewRightBoundary());
        clonedPlacedAgent.setOperator(getOperator());
        clonedPlacedAgent.setPlacedAgentPK(null);
        clonedPlacedAgent.setRightBoundary(getRightBoundary());
        clonedPlacedAgent.setSituationCode(getSituationCode());
        clonedPlacedAgent.setStartDate(getStartDate());
        clonedPlacedAgent.setStopDate(getStopDate());
        clonedPlacedAgent.setStopDate(getStopDate());
        clonedPlacedAgent.setStopDateReasonCT(getStopDateReasonCT());
        clonedPlacedAgent.setPlacedAgentPK(null);
        clonedPlacedAgent.setClientRoleFK(null);

        return clonedPlacedAgent;
    }
    
    // --------------------- Interface Comparable ---------------------
    public int compareTo(Object o)
    {
        int hierarchyLevel = this.placedAgentVO.getHierarchyLevel();
        
        PlacedAgent visitingAgent = (PlacedAgent) o;
        
        int visitingHierarchyLevel = visitingAgent.getHierarchyLevel();
        
        return new Double(hierarchyLevel).compareTo(new Double(visitingHierarchyLevel));
    }
    
    public int getHierarchyLevel()
    {
        return placedAgentVO.getHierarchyLevel();
    }
    
    // -------------------------- OTHER METHODS --------------------------
    public void addChildAgent(PlacedAgent childAgent)
    {
        // Prepare the parent PlacedAgent
        setNewLeftBoundary(getLeftBoundary());
        
        setNewRightBoundary(getRightBoundary());
        
        setNewHierarchyLevel(getHierarchyLevel());
        
        // Prepare the target child PlacedAgent (a temporary setting, it will be reindexed)
        int childLevel = getNewHierarchyLevel() + 1;
        
        childAgent.setIndexes(getRightBoundary(), getRightBoundary(), childLevel);
        
        childAgent.commitIndexChanges();
        
        // This new child is now guaranteed to be an immediate child of this (parent) PlacedAgent.
        List group = getGroup_V1();
        
        group.add(childAgent);
        
        // Reindex and commit
        reindex(group);
        
        group.remove(0); // The parent has not been reindexed, so just remove it before committing changes.
        
        String operator = ((PlacedAgentVO) childAgent.getVO()).getOperator();
        
        childAgent.setModifyingEvent(PlacedAgent.EVENT_NEW_CHILD);
        
        commitIndexChanges(group, operator);
        
        group.clear();
    }
    
    /**
     * Adds the specified group beneath this PlacedAgent. The specified group is assumed boundary valid within itself (i.e.
     * it's own left, right, and hierarchylevels are correct). As usual, the 1st element of the placedAgentGroup is
     * assumed to be the root.
     *
     * @param placedAgentGroup
     */
    public void addChildGroup(List placedAgentGroup)
    {
        PlacedAgent rootPlacedAgent = (PlacedAgent) placedAgentGroup.get(0);
        
        // Preserve these values - they are about to be changed.
        long oldLeftBoundary = rootPlacedAgent.getLeftBoundary();
        long oldRightBoundary = rootPlacedAgent.getRightBoundary();
        int oldHierarchyLevel = rootPlacedAgent.getHierarchyLevel();
        
        // Add the root as a normal child PlacedAgent. Once this is set, we can reindex the rest of the group from here.
        addChildAgent(rootPlacedAgent);
        
        long newLeftBoundary = rootPlacedAgent.getLeftBoundary();
        long newRightBoundary = rootPlacedAgent.getRightBoundary();
        int newHierarchyLevel = rootPlacedAgent.getHierarchyLevel();
        
        rootPlacedAgent.setLeftBoundary(oldLeftBoundary);
        rootPlacedAgent.setRightBoundary(oldRightBoundary);
        rootPlacedAgent.setHierarchyLevel(oldHierarchyLevel);
        
        rootPlacedAgent.setNewLeftBoundary(newLeftBoundary);
        rootPlacedAgent.setNewRightBoundary(newRightBoundary);
        rootPlacedAgent.setNewHierarchyLevel(newHierarchyLevel);
        
        // Reindex
        reindex(placedAgentGroup);
        
        String operator = rootPlacedAgent.getOperator();
        
        int size = placedAgentGroup.size();
        
        for (int i = 0; i < size; i++)
        {
            PlacedAgent placedAgent = (PlacedAgent) placedAgentGroup.get(i);
            
            placedAgent.setModifyingEvent(PlacedAgent.EVENT_NEW_CHILD);
        }
        
        commitIndexChanges(placedAgentGroup, operator);
        
        placedAgentGroup.clear();
    }
    
    public long getLeftBoundary()
    {
        return placedAgentVO.getLeftBoundary();
    }
    
    public long getRightBoundary()
    {
        return placedAgentVO.getRightBoundary();
    }
    
    public void commitIndexChanges()
    {
        if (isReindexed())
        {
            placedAgentVO.setLeftBoundary(getNewLeftBoundary());
            
            placedAgentVO.setRightBoundary(getNewRightBoundary());
            
            placedAgentVO.setHierarchyLevel(getNewHierarchyLevel());
        }
    }
    
    /**
     * True if the specified child PlacedAgent is a <b>direct</b> child of this parent PlacedAgent. For this to be
     * true, the hierarchy level of the specified child PlacedAgent must be exactly ParentPlacedAgent.HierarchyLevel + 1;
     *
     * @param childAgent
     * @return
     */
    public boolean isChild(PlacedAgent childAgent)
    {
        boolean isChild = false;
        
        int parentLevel = getHierarchyLevel();
        
        long parentLBoundary = getLeftBoundary();
        
        long parentRBoundary = getRightBoundary();
        
        int childLevel = childAgent.getHierarchyLevel();
        
        if (--childLevel == parentLevel) // Looking for immediate children only
        {
            long childLBoundary = childAgent.getLeftBoundary();
            
            long childRBoundary = childAgent.getRightBoundary();
            
            if ((childLBoundary >= parentLBoundary) && (childRBoundary <= parentRBoundary))
            {
                isChild = true;
            }
        }
        
        return isChild;
    }
    
    /**
     * True if the specified child PlacedAgent is a <b>descendent</b> of this ancestor PlacedAgent. For this to be
     * true, the hierarchy level of the specified must be > the ParentPlacedAgent.HierarchyLevel;
     *
     * @param descendentAgent
     * @return
     */
    public boolean isDescendent(PlacedAgent descendentAgent)
    {
        boolean isDescendent = false;
        
        int parentLevel = placedAgentVO.getHierarchyLevel();
        
        long parentLBoundary = placedAgentVO.getLeftBoundary();
        
        long parentRBoundary = placedAgentVO.getRightBoundary();
        
        int childLevel = descendentAgent.getHierarchyLevel();
        
        if (childLevel > parentLevel) // Looking for descendents
        {
            long childLBoundary = descendentAgent.getLeftBoundary();
            
            long childRBoundary = descendentAgent.getRightBoundary();
            
            if ((childLBoundary >= parentLBoundary) && (childRBoundary <= parentRBoundary))
            {
                isDescendent = true;
            }
        }
        
        return isDescendent;
    }
    
    public void setModifyingEvent(String modifyingEvent)
    {
        this.placedAgentVO.setModifyingEvent(modifyingEvent);
    }
    
    public void setOperator(String operator)
    {
        this.placedAgentVO.setOperator(operator);
    }
    
    public void save() throws Throwable
    {
    }
    
    public void hSave()
    {
        setMaintDateTime(new EDITDateTime());
        
        SessionHelper.saveOrUpdate(this, PlacedAgent.DATABASE);
    }
    
    public void hDelete()
    {
        getAgentContract().removePlacedAgent(this);
        getClientRole().removePlacedAgent(this);

        setAgentContract(null);
        
        SessionHelper.delete(this, PlacedAgent.DATABASE);
    }
    
    public void associateAgentContract(AgentContract agentContract) throws EDITAgentException
    {
        placedAgentVO.setAgentContractFK(agentContract.getPK());
    }
    

    public boolean boundariesConflict(PlacedAgent testPlacedAgent)
    {
        return placedAgentImpl.boundariesConflict(this, testPlacedAgent);
    }
    
    public AgentContract get_AgentContract()
    {
        if (agentContract == null)
        {
            agentContract = new AgentContract(placedAgentVO.getAgentContractFK());
        }
        
        return agentContract;
    }
    
    public AgentContract getAgentContract()
    {
        return agentContract;
    }

    public ClientRole getClientRole()
    {
        return clientRole;
    }

    public String getDatabase()
    {
        return PlacedAgent.DATABASE;
    }

    /**
     * The immediate children reporting directly to this PlacedAgent.
     *
     * @return
     */
    public PlacedAgent[] getChildren()
    {
        //        return placedAgentImpl.getChildren(this);
        Integer childHierarchyLevel = new Integer(getHierarchyLevel() + 1);
        
        return findBy_HierarchyLevel_AND_LeftBoundaryGTE_AND_RightBoundaryLTE(childHierarchyLevel, new Long(getLeftBoundary()), new Long(getRightBoundary()));
    }
    
    /**
     * Useful to find the immediate children of a given PlacedAgent (only included hierarchyLevel + 1 children)
     *
     * @param hierarchyLevel
     * @param leftBoundary
     * @param rightBoundary
     * @return
     */
    public static final PlacedAgent[] findBy_HierarchyLevel_AND_LeftBoundaryGTE_AND_RightBoundaryLTE(Integer hierarchyLevel, Long leftBoundary, Long rightBoundary)
    {
        String hql = "from PlacedAgent p where p.HierarchyLevel = :hierarchyLevel and p.LeftBoundary >= :leftBoundary and p.RightBoundary <= :rightBoundary";
        
        Map params = new HashMap();
        
        params.put("hierarchyLevel", hierarchyLevel);
        
        params.put("leftBoundary", leftBoundary);
        
        params.put("rightBoundary", rightBoundary);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    public PlacedAgent getParent()
    {
        if (parentPlacedAgent != null)
        {
            return parentPlacedAgent;
        }
        else if (!isIndexed())
        {
            throw new RuntimeException(Constants.ErrorMsg.PLACED_AGENT_NOT_INDEXED);
        }
        else
        {
            return placedAgentImpl.getParent(this);
        }
    }
    
/**
     * Hibernate version.
     *
     * @return
     */
    public PlacedAgent get_Parent()
    {
        PlacedAgent parentPlacedAgent = null;
        
        String hql = " SELECT placedAgent from PlacedAgent placedAgent" + " where placedAgent.HierarchyLevel = :hierarchyLevel" + " and placedAgent.LeftBoundary <= :leftBoundary" + " and placedAgent.RightBoundary >= :rightBoundary";
        
        Map params = new HashMap();
        params.put("leftBoundary", new Long(getLeftBoundary()));
        params.put("rightBoundary", new Long(getRightBoundary()));
        params.put("hierarchyLevel", new Integer(getHierarchyLevel() - 1));
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        if (!results.isEmpty())
        {
            parentPlacedAgent = (PlacedAgent) results.get(0);
        }
        
        return parentPlacedAgent;
    }
    

    public boolean isIndexed()
    {
        return ((placedAgentVO.getLeftBoundary() != 0) || (placedAgentVO.getRightBoundary() != 0));
    }
    
    /**
     * Tests to see the PlacedAgent's stopDate is less than the current date.
     *
     * @return true if the stopDate < the current date
     */
    public boolean isExpired()
    {
        boolean isExpired = false;
        
        EDITDate currentDate = new EDITDate();
      
        if (getStopDate() != null)
        {
            isExpired = getStopDate().before(currentDate);
        }
        
        return isExpired;
    }
    
    /**
     * Resets left-boundary, right-boundary, and hierarchy-level to their initial states.
     */
    public void resetIndexes()
    {
        setLeftBoundary(0);
        setRightBoundary(0);
        setHierarchyLevel(0);
    }
    
    public void setLeftBoundary(long leftBoundary)
    {
        this.placedAgentVO.setLeftBoundary(leftBoundary);
    }
    
    public void setRightBoundary(long rightBoundary)
    {
        this.placedAgentVO.setRightBoundary(rightBoundary);
    }
    
    public void setHierarchyLevel(int hierarchyLevel)
    {
        this.placedAgentVO.setHierarchyLevel(hierarchyLevel);
    }
    
    public void setParent(PlacedAgent parentPlacedAgent)
    {
        this.parentPlacedAgent = parentPlacedAgent;
    }
    
    //-- void setStopDate(java.lang.String)
    public void setSituationCode(String situationCode)
    {
        placedAgentVO.setSituationCode(situationCode);
    }
    
    //-- java.lang.String getStopDate()
    public void setStartDate(EDITDate startDate)
    {
        placedAgentVO.setStartDate(SessionHelper.getEDITDate(startDate));
    }
    
    //-- void setStartDate(java.lang.String)
    public void setStopDate(EDITDate stopDate)
    {
        placedAgentVO.setStopDate(SessionHelper.getEDITDate(stopDate));
    }
    
    //-- void setSituation(java.lang.String)
    public void setStopDateReasonCT(String stopDateReasonCT)
    {
        placedAgentVO.setStopDateReasonCT(stopDateReasonCT);
    }

    /**
     * Determines if the Situation if the specficied PlacedAgent conflicts with the Situation of this PlacedAgent.
     *
     * @param placedAgent
     * @return true of the compared Situations conflict
     */
    public boolean situationConficts(PlacedAgent placedAgent, String agentNumber)
    {
        boolean situationConflicts;
        
        if (this.equals(placedAgent))
        {
            situationConflicts = false; // A PlacedAgent's Situation can't conflict with itself.
        }
        else
        {
            Situation situation = placedAgent.getSituation();

            Situation thisSituation = getSituation();

            String thisAgentNumber = getClientRole().getReferenceID();

            situationConflicts = situation.situationConflicts(thisSituation, thisAgentNumber, agentNumber);
        }
        
        return situationConflicts;
    }
    
    protected void setIndexes(long leftB, long rightB, int level)
    {
        setNewLeftBoundary(leftB);
        
        setNewRightBoundary(rightB);
        
        setNewHierarchyLevel(level);
        
        placedAgentVO.setModifyingEvent(PlacedAgent.EVENT_REINDEXED);
        
        if ((getNewLeftBoundary() == DEFAULT_NEW_LEFT_BOUNDARY) || (getNewRightBoundary() == DEFAULT_NEW_RIGHT_BOUNDARY))
        {
            throw new RuntimeException("An Attempt To Reindex A Child PlacedAgent Occured Before Its Parent PlacedAgent Was Indexed [Child PlacedAgentPK = " + placedAgentVO.getPlacedAgentPK() + "]");
        }
    }
    
    /**
     * The list of immediate child PlacedAgents of this parent Placed Agent (ParentPlacedAgent.HierarchyLevel + 1).
     *
     * @param placedAgentCache
     * @return
     */
    protected List findChildrenFromGroup(List group)
    {
        List childAgents = new ArrayList();
        
        for (Iterator iterator = group.iterator(); iterator.hasNext();)
        {
            PlacedAgent currentPlacedAgent = (PlacedAgent) iterator.next();
            
            if (isChild(currentPlacedAgent))
            {
                childAgents.add(currentPlacedAgent);
            }
        }
        
        return childAgents;
    }
    
    /**
     * Children of this PlacedAgent have a hierarchy level + 1, and are
     * retrieved from the specified Map keyed by hierarchy level.
     */
    public List findChildrenFromMap(Map cache)
    {
        List results = new ArrayList();
                
        // Children will always be one level + than their parent.
        Integer hierarchyLevel = new Integer(getHierarchyLevel() + 1);
        
        List list = (List) cache.get(hierarchyLevel);
        
        if (list != null)
        {
            int size = list.size();

            for (int i = 0; i < size; i++)
            {
                PlacedAgent placedAgent = (PlacedAgent) list.get(i);

                if (isChild(placedAgent))
                {
                    results.add(placedAgent);
                }
            }
        }
        
        return results;
    }    
    
    /**
     * The list descendent PlacedAgents of this ancestor Placed Agent.
     *
     * @param placedAgentCache
     * @return
     */
    public List findDescendentsFromGroup(List placedAgentCache)
    {
        List descendentAgents = new ArrayList();
        
        for (Iterator iterator = placedAgentCache.iterator(); iterator.hasNext();)
        {
            PlacedAgent currentPlacedAgent = (PlacedAgent) iterator.next();
            
            if (isDescendent(currentPlacedAgent))
            {
                descendentAgents.add(currentPlacedAgent);
            }
        }
        
        return descendentAgents;
    }
    
    // --------------------- GETTER / SETTER METHODS ---------------------
    
    /**
     * Returns the group reporting to this PlacedAgent. This PlacedAgent [is] included in the group. Elements are ordered
     * by HierarchyLevel, therefore, the parent of this group will always be the 1st element of the group list.
     * No other entities are associated via Hibernate's fetch.
     *
     * @return
     */
    public List getGroup_V1()
    {
        List group = new ArrayList();
        
        try
        {
            long leftBoundary = getLeftBoundary();
            long rightBoundary = getRightBoundary();
            int hierarchyLevel = getHierarchyLevel();
            
            PlacedAgent[] groupArray = PlacedAgent.findByHierarchyLevelGT_AND_LeftBoundaryGTE_AND_RightBoundaryLTE_V1(leftBoundary, rightBoundary, hierarchyLevel);
            
            group.add(this); // This PlacedAgent itself.
            
            for (int i = 0; i < groupArray.length; i++)
            {
                group.add(groupArray[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        
        return group;
    }
    
    private void commitIndexChanges(List group, String operator)
    {
        int size = group.size();
        
        for (int i = 0; i < size; i++)
        {
            PlacedAgent currentAgent = (PlacedAgent) group.get(i);
            
            currentAgent.setOperator(placedAgentVO.getOperator());
            
            currentAgent.commitIndexChanges();
            
            currentAgent.setOperator(operator);
            
            currentAgent.hSave();
        }
    }
    
    /**
     * Some values which are not required, are to be defaulted.
     * StopDate defaults to EDITDate.DEFAULT_MAX_DATE.
     */
    private void setDefaults()
    {
        String stopDate = placedAgentVO.getStopDate();
        
        if (stopDate == null)
        {
            stopDate = EDITDate.DEFAULT_MAX_DATE;
        }
        
        placedAgentVO.setStopDate(stopDate);
    }
    
    private boolean isReindexed()
    {
        return ((newLeftBoundary != -1) || (newRightBoundary != -1));
    }
    
    private void reindex(List group)
    {
        Map placedAgentCache = mapToMap(group);
        
        int groupSize = group.size();
        
        for (int i = 0; i < groupSize; i++)
        {
            PlacedAgent parentPlacedAgent = (PlacedAgent) group.get(i);
            
            long parentNewLeftBoundary = parentPlacedAgent.getNewLeftBoundary();
            
            long parentNewRightBoundary = parentPlacedAgent.getNewRightBoundary();
            
            int parentNewHierarchyLevel = parentPlacedAgent.getNewHierarchyLevel();
            
            List childAgents = parentPlacedAgent.findChildrenFromMap(placedAgentCache);
            
            int childCount = childAgents.size();
            
            if (childCount > 0)
            {
                double stepSize = stepSize(parentPlacedAgent, childAgents.size());
                
                for (int j = 0; j < childCount; j++)
                {
                    PlacedAgent childPlacedAgent = (PlacedAgent) childAgents.get(j);
                    
                    long childNewLeftBoundary = (long) Math.floor(parentNewLeftBoundary + (j * stepSize));
                    
                    long childNewRightBoundary = 0;
                    
                    if (j < (childCount - 1))
                    {
                        childNewRightBoundary = (long) Math.ceil(childNewLeftBoundary + stepSize); // Use-up all of the remaining interval
                    }
                    else
                    {
                        childNewRightBoundary = parentNewRightBoundary;
                    }
                    
                    int childNewHierarchyLevel = parentNewHierarchyLevel + 1;
                    
                    childPlacedAgent.setIndexes(childNewLeftBoundary, childNewRightBoundary, childNewHierarchyLevel);
                    
                    childPlacedAgent.setModifyingEvent(PlacedAgent.EVENT_REINDEXED);
                }
            }
        }
    }
    
    private void removeEntriesFromCache(List placedAgentCache, List childAgents)
    {
        int count = childAgents.size();
        
        for (int i = 0; i < count; i++)
        {
            placedAgentCache.remove(childAgents.get(i));
        }
    }
    
    private double stepSize(PlacedAgent placedAgent, int childCount)
     {
      long leftBoundary = placedAgent.getNewLeftBoundary();
      
      long rightBoundary = placedAgent.getNewRightBoundary();
      
      double stepSize = (rightBoundary - leftBoundary) / childCount;
      
      if (stepSize == 0)
      {
          throw new RuntimeException("Left and Right Boundaries Have Converged And Cannot Be Reindexed For Parent PlacedAgent [PlacedAgentPK = " + placedAgent.getPlacedAgentPK() + "]");
      }
      
      return stepSize;
    }
    
    private int getNewHierarchyLevel()
    {
        return newHierarchyLevel;
    }
    
    private void setNewHierarchyLevel(int newHierarchyLevel)
    {
        this.newHierarchyLevel = newHierarchyLevel;
    }
    
    private long getNewLeftBoundary()
    {
        return newLeftBoundary;
    }
    
    private void setNewLeftBoundary(long newLeftBoundary)
    {
        this.newLeftBoundary = newLeftBoundary;
    }
    
    private long getNewRightBoundary()
    {
        return newRightBoundary;
    }
    
    private void setNewRightBoundary(long newRightBoundary)
    {
        this.newRightBoundary = newRightBoundary;
    }
    
    // -------------------------- STATIC METHODS --------------------------
    public static PlacedAgent[] findByAgentContractPK(long agentContractPK)
    {
        return PlacedAgentImpl.findByAgentContractPK(agentContractPK);
    }
    
    /**
     * Finder.
     *
     * @return
     */
    public static final PlacedAgent findBy_MaxRightBoundary()
    {
        PlacedAgent placedAgent = null;
        
        String hql = "from PlacedAgent p1 where p1.RightBoundary = (select max(p2.RightBoundary) from PlacedAgent p2)";
        
        List results = SessionHelper.executeHQL(hql, null, PlacedAgent.DATABASE);
        
        if (!results.isEmpty())
        {
            placedAgent = (PlacedAgent) results.get(0);
        }
        
        return placedAgent;
    }
    
    /**
     * Useful to find the branch of a PlaceAgent (from child up).
     *
     * @param leftBoundary
     * @param rightBoundary
     * @param hierarchyLevel
     * @return
     */
    public static final PlacedAgent[] findByHierarchyLevelLTE_AND_LeftBoundaryLTE_AND_RightBoundaryGTE(long leftBoundary, long rightBoundary, int hierarchyLevel)
    {
        String hql = "from PlacedAgent p " + " where p.LeftBoundary <= :leftBoundary" + " and p.RightBoundary >= :rightBoundary" + " and p.HierarchyLevel <= :hierarchyLevel";
        
        Map params = new HashMap();
        params.put("leftBoundary", new Long(leftBoundary));
        params.put("rightBoundary", new Long(rightBoundary));
        params.put("hierarchyLevel", new Integer(hierarchyLevel));
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    public static PlacedAgent[] mapVOToEntity(PlacedAgentVO[] placedAgentVO)
    {
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
            
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
            
            throw new RuntimeException(e);
        }
        
        return placedAgent;
    }
    
    /**
     * Useful to find the entire Hierarchy of a PlacedAgent. This version
     * includes the PlacedAgentCommissionProfile and CommissionProfile as
     * fetch entities, but does [not] include the parent PlacedAgentItself.
     *
     * @param leftBoundary
     * @param rightBoundary
     * @return
     */
    public static final PlacedAgent[] findByHierarchyLevelGT_AND_LeftBoundaryGTE_AND_RightBoundaryLTE_V1(long leftBoundary, long rightBoundary, int hierarchyLevel) throws Exception
    {
        String hql = " select p from PlacedAgent p " + 
                     " join fetch p.AgentContract" +   
                    " where p.LeftBoundary >= :leftBoundary" + 
                    " and p.RightBoundary <= :rightBoundary" +  
                    " and p.HierarchyLevel > :hierarchyLevel order by p.HierarchyLevel asc";
        
        Map params = new HashMap();
        params.put("leftBoundary", new Long(leftBoundary));
        params.put("rightBoundary", new Long(rightBoundary));
        params.put("hierarchyLevel", new Integer(hierarchyLevel));
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    /**
     * Finder.
     *
     * @param contractCodeCT
     * @param commissionLevelCT
     * @param startDate
     * @param stopDate
     * @return
     */
    public static final PlacedAgent[] findBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(String contractCodeCT, String commissionLevelCT, String startDate, String stopDate)
    {
        PlacedAgent[] placedAgents = null;
        
        placedAgents = mapVOToEntity(DAOFactory.getPlacedAgentDAO().findBy_ContractCodeCT_CommissionLevelCT_StartDate_StopDate(contractCodeCT, commissionLevelCT, startDate, stopDate));
        
        return placedAgents;
    }
    
    /**
     * Finder.
     *
     * @param contractCodeCT
     * @param agentNumber
     * @param startDate
     * @param stopDate
     * @return
     */
    public static PlacedAgent[] findBy_CommissionContractCT_AgentNumber_StartDate_StopDate(String contractCodeCT, String agentNumber, String startDate, String stopDate)
    {
        PlacedAgent[] placedAgents = null;
        
        placedAgents = mapVOToEntity(DAOFactory.getPlacedAgentDAO().findBy_CommissionContractCT_AgentNumber_StartDate_StopDate(contractCodeCT, agentNumber, startDate, stopDate));
        
        return placedAgents;
    }
    
    /**
     * Finder. Additionally
     *
     * @param contractCodeCT
     * @param agentName
     * @param startDate
     * @param stopDate
     * @return
     */
    public static PlacedAgent[] findBy_CommissionContractCT_AgentName_StartDate_StopDate(String contractCodeCT, String agentName, String startDate, String stopDate)
    {
        return (PlacedAgent[]) CRUDEntityImpl.mapVOToEntity(new PlacedAgentDAO().findBy_CommissionContractCT_AgentName_StartDate_StopDate(contractCodeCT, agentName, startDate, stopDate), PlacedAgent.class);
    }
    
    /**
     * Lowers this PlacedAgent's hierarchyLevel by 1 (moves up in the hierarchyLevel).
     */
    public void decrementHierarchyLevel()
    {
        int currentHierarchyLevel = getHierarchyLevel();
        
        setHierarchyLevel(currentHierarchyLevel - 1);
    }
    
    /**
     * True if this PlacedAgent is participating in the specified BonusProgram.
     *
     * @param bonusProgram
     * @return
     */
    public boolean isParticipating(BonusProgram bonusProgram)
    {
        boolean isParticipating = false;
        
        ParticipatingAgent[] participatingAgents = ParticipatingAgent.findBy_BonusProgramPK_PlacedAgentPK(bonusProgram.getBonusProgramPK(), getPlacedAgentPK());
        
        isParticipating = (participatingAgents != null);
        
        return isParticipating;
    }
    
    /**
     * Setter.
     *
     * @param agentContract
     */
    public void setAgentContract(AgentContract agentContract)
    {
        this.agentContract = agentContract;
    }

    /**
     * Setter.
     * @param clientRole
     */
    public void setClientRole(ClientRole clientRole)
    {
        this.clientRole = clientRole;
    }

    /**
     * Finder.
     *
     * @param placedAgentPK
     * @return
     */
    public static final PlacedAgent findBy_PlacedAgentPK(Long placedAgentPK)
    {
        return (PlacedAgent) SessionHelper.get(PlacedAgent.class, placedAgentPK, PlacedAgent.DATABASE);
    }
    
    /**
     * Finder.
     *
     * @param agentContractPK
     * @return
     */
    public static PlacedAgent[] findBy_AgentContractPK(Long agentContractPK)
    {
        String hql = "from PlacedAgent p where p.AgentContractFK = :agentContractFK";
        
        Map params = new HashMap();
        
        params.put("agentContractFK", agentContractPK);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    /**
     * STATELESS Finder.
     *
     * @param agentId
     * @param contractCodeCT
     * @return
     */
    public static PlacedAgent[] findBy_AgentId_AND_ContractCodeCT(String agentId, String contractCodeCT)
    {
        String hql = "select p from PlacedAgent p " + 
                " join p.AgentContract ac" + 
                " join ac.Agent a" +
                " join p.ClientRole cr" +
                " where cr.ReferenceID = :agentNumber" +
                " and ac.ContractCodeCT = :contractCodeCT";
        
        Map params = new HashMap();
        
        params.put("agentNumber", agentId);
        
        params.put("contractCodeCT", contractCodeCT);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    /**
     * STATELESS Finder.
     *
     * @param agentName
     * @param contractCodeCT
     * @return
     */
    public static PlacedAgent[] findBy_AgentName_AND_ContractCodeCT(String agentName, String contractCodeCT)
    {
        String hql = " select p from PlacedAgent p" +
                " join fetch p.AgentContract ac" + 
                " join fetch ac.Agent a" + 
                " join fetch p.ClientRole cr" +
                " join fetch cr.ClientDetail cd" + 
                " where ac.ContractCodeCT = :contractCodeCT" + 
                " and ((cd.LastName like :lastName and cd.TrustTypeCT = 'Individual') or (cd.CorporateName like :corporateName and (cd.TrustTypeCT = 'Corporate' or cd.TrustTypeCT = 'LLC')))";
        
        Map params = new HashMap();
        params.put("contractCodeCT", contractCodeCT);
        params.put("lastName", agentName + "%");
        params.put("corporateName", agentName + "%");
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    public static final PlacedAgent findByPK(Long placedAgentPK)
    {
        return (PlacedAgent) SessionHelper.get(PlacedAgent.class, placedAgentPK, PlacedAgent.DATABASE);
    }
    
    /**
     * Finder. Includes the associations of AgentContract, Agent, ClientRole, ClientDetail, CommissionProfile.
     *
     * @param placedAgentPK
     * @return
     */
    public static PlacedAgent findBy_PK_V1(Long placedAgentPK)
    {
        PlacedAgent placedAgent = null;
        
        String hql = " select placedAgent from PlacedAgent placedAgent" + 
                    " join fetch placedAgent.AgentContract agentContract" + 
                    " join fetch agentContract.Agent agent" + 
                    " join fetch placedAgent.ClientRole clientRole" +
                    " join fetch clientRole.ClientDetail" + 
                    " join fetch placedAgent.PlacedAgentCommissionProfiles placedAgentCommissionProfile" +
                    " join fetch placedAgentCommissionProfile.CommissionProfile" +
                    " where placedAgent.PlacedAgentPK = :placedAgentPK";
        
        Map params = new HashMap();
        
        params.put("placedAgentPK", placedAgentPK);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        if (!results.isEmpty())
        {
            placedAgent = (PlacedAgent) results.get(0); 
        }
        
        return placedAgent;
    }
    
    /**
     * Finder. Includes the associations of AgentContract, Agent, ClientRole, ClientDetail.
     *
     * @param placedAgentPK
     * @return
     */
    public static PlacedAgent findBy_PK_V2(Long placedAgentPK)
    {
        PlacedAgent placedAgent = null;
        
        String hql = " select placedAgent from PlacedAgent placedAgent" +
                     " join fetch placedAgent.AgentContract agentContract" +
                     " join fetch agentContract.Agent agent" +
                     " join fetch placedAgent.ClientRole clientRole" +
                     " join fetch clientRole.ClientDetail" +
                     " where placedAgent.PlacedAgentPK = :placedAgentPK";
        
        Map params = new HashMap();
        
        params.put("placedAgentPK", placedAgentPK);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        if (!results.isEmpty())
        {
            placedAgent = (PlacedAgent) results.get(0);
        }
        
        return placedAgent;
    }
    

    /**
     * STATELESS Finds the PlacedAgent and the associated entities of AgentContract, Agent, ClientRole, and ClientDetail.
     *
     * @param contractCodeCT
     * @return
     */
    public static final PlacedAgent[] findBy_HierarchyLevel_and_ContractCodeCT(Integer hierarchyLevel, String contractCodeCT)
    {
        String hql = " select pa from PlacedAgent pa" +
                     " join fetch pa.AgentContract ac" +
                     " join fetch pa.ClientRole cr" +
                     " join fetch cr.ClientDetail cd" +
                     " where pa.HierarchyLevel = :hierarchyLevel and ac.ContractCodeCT = :contractCodeCT";
        
        Map params = new HashMap();
        
        params.put("hierarchyLevel", hierarchyLevel);
        
        params.put("contractCodeCT", contractCodeCT);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    /**
     * This is similar to another finder to find the immediate children of a PlacedAgent except that this version
     * includes the associated entities of AgentContract, Agent, ClientRole, ClientDetail.
     *
     * @return
     */
    public static final PlacedAgent[] findBy_HierarchyLevel_and_LeftBoundaryGTE_AND_RightBoundaryLTE(Integer hierarchyLevel, Long leftBoundary, Long rightBoundary)
    {
        String hql = "from PlacedAgent pa join fetch pa.AgentContract ac join fetch pa.ClientRole cr join fetch cr.ClientDetail" +
                     " where pa.HierarchyLevel = :hierarchyLevel and pa.LeftBoundary >= :leftBoundary and pa.RightBoundary <= :rightBoundary";
        
        Map params = new HashMap();
        
        params.put("hierarchyLevel", hierarchyLevel);
        
        params.put("leftBoundary", leftBoundary);
        
        params.put("rightBoundary", rightBoundary);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    /**
     * STATELESS Useful to find the entire reporting group (including the root/parent PlacedAgent) of a root/parent PlacedAgent.
     * The results are ordered by HierarchyLevel ascending. The resulting PlacedAgent has the AgentContract, Agent, ClientRole and ClientDetail
     * associations.
     *
     * @return
     */
    public static final PlacedAgent[] findBy_HierarchyLevelGTE_and_LeftBoundaryGTE_AND_RightBoundaryLTE(Integer hierarchyLevel, Long leftBoundary, Long rightBoundary)
    {
        String hql = " select pa from PlacedAgent pa" + " join fetch pa.AgentContract ac" +
                     " join fetch pa.ClientRole cr" + " join fetch cr.ClientDetail" +
                     " where pa.HierarchyLevel >= :hierarchyLevel and pa.LeftBoundary >= :leftBoundary and pa.RightBoundary <= :rightBoundary" + " order by pa.HierarchyLevel asc, pa.LeftBoundary asc, pa.RightBoundary asc";
        
        Map params = new HashMap();
        
        params.put("hierarchyLevel", hierarchyLevel);
        
        params.put("leftBoundary", leftBoundary);
        
        params.put("rightBoundary", rightBoundary);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    /**
     * STATELESS Useful to find the entire reporting group (including the root/parent PlacedAgent) of a root/parent PlacedAgent.
     * The results are ordered by HierarchyLevel ascending. The resulting PlacedAgent has the AgentContract, Agent, ClientRole and ClientDetail
     * associations.
     *
     * @return
     */
    public static final PlacedAgent[] findBy_HierarchyLevelGTE_and_LeftBoundaryGTE_AND_RightBoundaryLTE_AND_StopDate(Integer hierarchyLevel, Long leftBoundary, Long rightBoundary, EDITDate stopDate)
    {
        String hql = " select pa from PlacedAgent pa" + 
                " join fetch pa.AgentContract ac" + 
                " join fetch pa.ClientRole cr" +
                " join fetch cr.ClientDetail" + 
                " where pa.HierarchyLevel >= :hierarchyLevel" + 
                " and pa.LeftBoundary >= :leftBoundary" + 
                " and pa.RightBoundary <= :rightBoundary" + 
                " and pa.StopDate >= :stopDate" +
                " order by pa.HierarchyLevel asc, pa.LeftBoundary asc, pa.RightBoundary asc";
        
        Map params = new HashMap();
        
        params.put("hierarchyLevel", hierarchyLevel);
        
        params.put("leftBoundary", leftBoundary);
        
        params.put("rightBoundary", rightBoundary);
        
        params.put("stopDate", stopDate);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }    
    
//    /**
//     * Setter.
//     *
//     * @param agentGroupAssociations
//     */
//    public void setAgentGroupAssociations(Set agentGroupAssociations)
//    {
//        this.agentGroupAssociations = agentGroupAssociations;
//    }
//
//    /**
//     * Getter.
//     *
//     * @return
//     */
//    public Set getAgentGroupAssociations()
//    {
//        return agentGroupAssociations;
//    }
    
    /**
     * Getter.
     *
     * @return
     */
    public Set getAgentGroupAssociations()
    {
        return agentGroupAssociations;
    }
    
    /**
     * Invokes an hql query to find the count of AgentGroupAssocations.
     *
     * @return int - the count of AgentGroupAssocations
     */
    public int getAgentGroupAssociationsCount()
    {
        String hql = "select count(*) from AgentGroupAssociation agentGroupAssociation where agentGroupAssociation.PlacedAgent = :placedAgent";
        
        Map params = new HashMap();
        
        params.put("placedAgent", this);
        
        return SessionHelper.executeHQLForCount(hql, params, PlacedAgent.DATABASE);
    }
    
    /**
     * Setter.
     *
     * @param participatingAgents
     */
    public void setParticipatingAgents(Set participatingAgents)
    {
        this.participatingAgents = participatingAgents;
    }
    
    /**
     * Getter.
     *
     * @return
     */
    public Set getParticipatingAgents()
    {
        return participatingAgents;
    }
    
    /**
     * Adder.
     *
     * @param agentGroupAssociation
     */
    public void addAgentGroupAssociation(AgentGroupAssociation agentGroupAssociation)
    {
        getAgentGroupAssociations().add(agentGroupAssociation);
        
        agentGroupAssociation.setPlacedAgent(this);
        
        SessionHelper.saveOrUpdate(this, PlacedAgent.DATABASE);
    }
    
    /**
     * Adder.
     *
     * @param participatingAgent
     */
    public void addParticipatingAgent(ParticipatingAgent participatingAgent)
    {
        getParticipatingAgents().add(participatingAgent);
        
        participatingAgent.setPlacedAgent(this);
        
        SessionHelper.saveOrUpdate(this, PlacedAgent.DATABASE);
    }
    
    /**
     * Remover.
     *
     * @param agentGroupAssociation
     */
    public void removeAgentGroupAssocation(AgentGroupAssociation agentGroupAssociation)
    {
        getAgentGroupAssociations().remove(agentGroupAssociation);
        
        agentGroupAssociation.setPlacedAgent(null);
        
        SessionHelper.saveOrUpdate(this, PlacedAgent.DATABASE);
    }

    /**
     * Adder.
     *
     * @param commissionHistory
     */
    public void addCommissionHistory(CommissionHistory commissionHistory)
    {
        this.getCommissionHistories().add(commissionHistory);

        commissionHistory.setPlacedAgent(this);

        SessionHelper.saveOrUpdate(commissionHistory, PlacedAgent.DATABASE);
    }

    /**
     * Finder.
     *
     * @param placedAgentPK
     * @param state
     * @return
     */
    public static PlacedAgent findBy_PK(Long placedAgentPK)
    {
        return (PlacedAgent) SessionHelper.get(PlacedAgent.class, placedAgentPK, PlacedAgent.DATABASE);
    }
    
    /**
     * Checks if the associated PlacedAgent is, itself, associated with an AgentGroup.
     *
     * @return
     */
    public boolean isParticipatingInAgentGroup(EDITDate applicationReceivedDate)
    {
        boolean isParticipating = false;

        if (getAgentGroupAssociationsCount() > 0)
        {
            AgentGroupAssociation agentGroupAssociation = (AgentGroupAssociation) getAgentGroupAssociations().iterator().next();

            if (agentGroupAssociation.isWithinStartStopDate(applicationReceivedDate))
            {
                isParticipating = true;
            }
        }

        return isParticipating;
    }

    
    /**
     * Convenience method that returns the [only] associated AgentGroup if any.
     *
     * @return AgentGroup or null
     */
    public AgentGroup getAgentGroup()
    {
        AgentGroup agentGroup = null;
        
        Set agentGroupAssocations = getAgentGroupAssociations();
        
        if (!agentGroupAssocations.isEmpty())
        {
            AgentGroupAssociation agentGroupAssociation = (AgentGroupAssociation) agentGroupAssocations.iterator().next();
            
            agentGroup = agentGroupAssociation.getAgentGroup();
        }
        
        return agentGroup;
    }
    
    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public static final PlacedAgent findBy_ParticipatingAgent(ParticipatingAgent participatingAgent)
    {
        String hql = "select placedAgent from PlacedAgent placedAgent " +
                " join placedAgent.ParticipatingAgents participatingAgent " +
                " join fetch placedAgent.AgentContract agentContract " +
                " join fetch agentContract.Agent agent " +
                " join fetch agent.Redirects redirect " +
                " where participatingAgent = :participatingAgent";
        
        Map params = new HashMap();
        
        params.put("participatingAgent", participatingAgent);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent) results.get(0);
    }

    public static final PlacedAgent[] findBy_Agent(Agent agent)
    {
        String hql = "select placedAgent from PlacedAgent placedAgent " +
                " join fetch placedAgent.AgentContract agentContract " +
                " where agentContract.AgentFK = :agentFK";

        Map params = new HashMap();

        params.put("agentFK", agent.getAgentPK());

        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);

        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }

    public static PlacedAgent findByBonusCommissionHistory(BonusCommissionHistory bonusCommissionHistory)
    {
        String hql = "select placedAgent from PlacedAgent placedAgent " +
                " join placedAgent.CommissionHistories commissionHistory " +
                " join commissionHistory.BonusCommissionHistory bonusCommissionHistory" +
                " join commissionHistory.EDITTrxHistory editTrxHistory " +
                " join fetch editTrxHistory.FinancialHistory " +
                " join fetch editTrxHistory.EDITTrx " +
                " where bonusCommissionHistory = :bonusCommissionHistory";
        
        Map params = new HashMap();
        
        params.put("bonusCommissionHistory", bonusCommissionHistory);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent) results.get(0);
    }
    
    /**
     * Finder. StopDate must be > specified stopDate as it only finds PlacedAgents that have NOT expired.
     */
    public static PlacedAgent[] findBy_AgentName_AND_ContractCodeCT_AND_StopDate(String agentName, String contractCodeCT, EDITDate stopDate)
    {
        String hql = " select p from PlacedAgent p join p.AgentContract ac " +
                " join pa.ClientRole cr" +
                " join cr.ClientDetail cd" + 
                " where ac.ContractCodeCT = :contractCodeCT" + 
                " and ((cd.LastName like :lastName and cd.TrustTypeCT = 'Individual') or (cd.CorporateName like :corporateName and cd.TrustTypeCT = 'Corporate'))" + 
                " and p.StopDate > :stopDate order by cr.ReferenceID";
        
        Map params = new HashMap();
        params.put("contractCodeCT", contractCodeCT);
        params.put("lastName", agentName + "%");
        params.put("corporateName", agentName + "%");
        params.put("stopDate", stopDate);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }
    
    /**
     * Finder. StopDate must be > specified stopDate as it only finds PlacedAgents that have NOT expired.
     */
    public static PlacedAgent[] findBy_AgentNumber_AND_ContractCodeCT_AND_StopDate(String agentNumber, String contractCodeCT, EDITDate stopDate)
    {
        String hql = " select p from PlacedAgent p join p.AgentContract ac " +
                " join pa.ClientRole cr" +
                " join cr.ClientDetail cd" + 
                " where ac.ContractCodeCT = :contractCodeCT" + 
                " and cr.ReferenceID = :agentNumber" +
                " and p.StopDate > :stopDate order by cr.ReferenceID";
        
        Map params = new HashMap();
        params.put("contractCodeCT", contractCodeCT);
        params.put("agentNumber", agentNumber);
        params.put("stopDate", stopDate);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);
        
        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }    

    /**
     * Finder. StopDate must be > specified stopDate as it only finds PlacedAgents that have NOT expired.
     */
    public static PlacedAgent[] findBy_ClientRoleFK_AND_StopDate(Long clientRoleFK, EDITDate stopDate)
    {
        String hql = " select p from PlacedAgent p" +
                " join p.ClientRole cr" +
                " join cr.ClientDetail cd" +
                " where p.ClientRoleFK = :clientRoleFK" +
                " and p.StopDate > :stopDate";

        Map params = new HashMap();
        params.put("clientRoleFK", clientRoleFK);
        params.put("stopDate", stopDate);

        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);

        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }    

    /**
     * Finder. Find all placed agents who are utilizing a given ClientRole, excluding the specified PlacedAgent.
     */
    public static PlacedAgent[] findBy_ClientRoleFK_Exclusion(Long clientRoleFK, Long excludingPlacedAgentPK)
    {
        String hql = " select p from PlacedAgent p" +
                " join p.ClientRole cr" +
                " where p.ClientRoleFK = :clientRoleFK" +
                " and p.PlacedAgentPK <> :placedAgentPK";
        Map params = new HashMap();
        params.put("clientRoleFK", clientRoleFK);
        params.put("placedAgentPK", excludingPlacedAgentPK);

        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);

        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }

    /**
     * Finder.
     */
    public static PlacedAgent[] findByAgentNumber(String agentNumber)
    {
        String hql = " select p from PlacedAgent p" +
                     " where p.AgentNumber = :agentNumber";

        Map params = new HashMap();
        params.put("agentNumber", agentNumber);

        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);

        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }

    /**
     * Finder.
     */
    public static PlacedAgent[] findByClientRoleFK(Long clientRoleFK)
    {
        String hql = " select p from PlacedAgent p" +
                     " where p.ClientRoleFK = :clientRoleFK";

        Map params = new HashMap();
        params.put("clientRoleFK", clientRoleFK);

        List results = SessionHelper.executeHQL(hql, params, PlacedAgent.DATABASE);

        return (PlacedAgent[]) results.toArray(new PlacedAgent[results.size()]);
    }

    /**
     * Children of this PlacedAgent have a hierarchy level + 1, and are
     * retrieved from the specified Map keyed by hierarchy level.
     */
//    public List findChildrenFromMap(Map cache)
//    {
//        List results = new ArrayList();
//                
//        // Children will always be one level + than their parent.
//        Integer hierarchyLevel = new Integer(getHierarchyLevel() + 1);
//        
//        List list = (List) cache.get(hierarchyLevel);
//        
//        if (list != null)
//        {
//            int size = list.size();
//
//            for (int i = 0; i < size; i++)
//            {
//                PlacedAgent placedAgent = (PlacedAgent) list.get(i);
//
//                if (isChild(placedAgent))
//                {
//                    results.add(placedAgent);
//                } 
//            }
//        }
//        
//        return results;
//    }
    
    /**
     * Descendants of this PlacedAgent have a hierarchy level >= 1, and are
     * retrieved from the specified Map keyed by hierarchy level. This method finds
     * the next closest descendants in increments of 1.
     */
    public List findClosestDescendantsFromMap(Map cache)
    {
        int totalKeys = cache.keySet().size();
        
        int keyCount = 0;
        
        List results = new ArrayList();
        
        int hierarchyStep = 1;
        
        boolean descendantsFound = false;
                
        // Children will always be one level + than their parent.
        Integer hierarchyLevel = new Integer(getHierarchyLevel() + hierarchyStep);
        
        while (!descendantsFound && (keyCount < totalKeys))
        {
            List list = (List) cache.get(hierarchyLevel);
            
            if (list != null)
            {
                int cacheSize = list.size();
                            
                for (int i = 0; i < cacheSize; i++)
                {
                    PlacedAgent placedAgent = (PlacedAgent) list.get(i);

                    if (isChild(placedAgent))
                    {
                        results.add(placedAgent);
                        
                        descendantsFound = true;
                    }
                }
                
                if (!descendantsFound)
                {
                    hierarchyStep++;
                }
            }
            
            keyCount++;
        }
        
        return results;
    }    
    
    /**
     * For performance reasons only, it is faster to work with a Map than a List.
     */
    public static Map mapToMap(List group) {
        
        Map cache = new HashMap();
        
        int size = group.size();
        
        for (int i = 0; i < size; i++)
        {
            PlacedAgent placedAgent = (PlacedAgent) group.get(i);
            
            Integer hierarchyLevel = new Integer(placedAgent.getHierarchyLevel());
            
            List placedAgentsAtCurrentLevel = (List) cache.get(hierarchyLevel);
            
            if (placedAgentsAtCurrentLevel == null)
            {
                placedAgentsAtCurrentLevel = new ArrayList();
                
                cache.put(hierarchyLevel, placedAgentsAtCurrentLevel);
            }
            
            placedAgentsAtCurrentLevel.add(placedAgent);
        }
        
        return cache;
    }    

    public Set<PlacedAgentCommissionProfile> getPlacedAgentCommissionProfiles()
    {
        return placedAgentCommissionProfiles;
    }

    public void setPlacedAgentCommissionProfiles(Set<PlacedAgentCommissionProfile> placedAgentCommissionProfiles)
    {
        this.placedAgentCommissionProfiles = placedAgentCommissionProfiles;
    }

    /**
     * Identifies the PlacedAgentCommissionProfile whose Start/Stop dates contain the
     * StopDate of this PlacedAgent. e.g.:
     * PlacedAgentCommissionProfile.StartDate <= PlacedAgent.StopDate <= PlacedAgentCommissionProfile.StopDate.
     * If this PlacedAgent already has an active Set of PlacedAgentCommissionProfiles, those are evaluated, otherwise
     * the DB is hit.
     * @return the PlacedAgentCommissionProfile that satisifies the Start/Stop date requirements
     */
    public PlacedAgentCommissionProfile getActivePlacedAgentCommissionProfile()
    {
        if (activePlacedAgentCommissionProfile == null)
        {
            activePlacedAgentCommissionProfile = PlacedAgentCommissionProfile.findBy_PlacedAgent_Date(this, getStopDate());
        }
        else
        {
            List placedAgentCommissionProfiles = new ArrayList(getPlacedAgentCommissionProfiles());
            
            int size = placedAgentCommissionProfiles.size();
            
            for (int i = 0; i < size; i++)
            {
                PlacedAgentCommissionProfile currentPlacedAgentCommissionProfile = (PlacedAgentCommissionProfile) placedAgentCommissionProfiles.get(i);
                
                if (currentPlacedAgentCommissionProfile.isActive(getStopDate()))
                {
                    activePlacedAgentCommissionProfile = currentPlacedAgentCommissionProfile;
                    
                    break;
                }
            }
        }
        
        return activePlacedAgentCommissionProfile;
    }
    
    /**
    * Setter for ActivePlacedAgentCommissionProfile.
    * 
    * @param activePlacedAgentCommissionProfile
   */
    private void setActivePlacedAgentCommissionProfile(PlacedAgentCommissionProfile activePlacedAgentCommissionProfile)
    {
      this.activePlacedAgentCommissionProfile = activePlacedAgentCommissionProfile;
    }


    /**
     * Adder (non-trivial). Every time a new PlacedAgentCommissionProfile is 
     * associated with a PlacedAgent, the last active one has to have its
     * StopDate be set to one day less than the new StartDate. The new StartDate
     * must be greater than any existing StartDate, otherwise the new last active's
     * Start/Stop dates can't be rigorously set.
     */
    public void add(PlacedAgentCommissionProfile placedAgentCommissionProfile) throws EDITAgentException
    {
        EDITDate newStartDate = placedAgentCommissionProfile.getStartDate();
        
        PlacedAgentCommissionProfile currentActivePlacedAgentCommissionProfile = getActivePlacedAgentCommissionProfile();
                
        EDITDate oldStartDate = null;
        
        if (currentActivePlacedAgentCommissionProfile != null)
        {
            oldStartDate = currentActivePlacedAgentCommissionProfile.getStartDate();
            
            if (!newStartDate.after(oldStartDate))
            {
                throw new EDITAgentException("New start date must be > [" + DateTimeUtil.formatEDITDateAsMMDDYYYY(oldStartDate) + "]");
            }       
        
            EDITDate oldStopDate = newStartDate.subtractDays(1);
            
            currentActivePlacedAgentCommissionProfile.setStopDate(oldStopDate);            
        }
                
        getPlacedAgentCommissionProfiles().add(placedAgentCommissionProfile);
        
        placedAgentCommissionProfile.setPlacedAgent(this);
        
        setActivePlacedAgentCommissionProfile(placedAgentCommissionProfile);
    }

    /**
     * Remover. This is purposely set to be non-public. There should always be
     * an "active" PlacedAgentCommissionProfile; it is not acceptable to simply
     * remove the PlacedAgentCommissionProfile determing a new active one.
     */
    protected void remove(PlacedAgentCommissionProfile placedAgentCommissionProfile)
    {
        getPlacedAgentCommissionProfiles().remove(placedAgentCommissionProfile);
        
        placedAgentCommissionProfile.setPlacedAgent(null);
        
        setActivePlacedAgentCommissionProfile(null); // Be safe - maybe this was the active one.
    }

    public Set<AgentSnapshot> getAgentSnapshots()
    {
        return agentSnapshots;
    }

    public void setAgentSnapshots(Set<AgentSnapshot> agentSnapshots)
    {
        this.agentSnapshots = agentSnapshots;
    }
    
    /**
     * Setter.
     * @param checkAdjustments
     */
    public void setCheckAdjustments(Set<CheckAdjustment> checkAdjustments) 
    {
        this.checkAdjustments = checkAdjustments;
    }

    /**
     * Getter.
     * @return
     */
    public Set<CheckAdjustment> getCheckAdjustments() 
    {
        return checkAdjustments;
    }


    /**
     * Adder.
     */
    public void add(AgentSnapshot agentSnapshot)
    {
        getAgentSnapshots().add(agentSnapshot);
        
        agentSnapshot.setPlacedAgent(this);
    }

  /**
   * Getter for candidatePlacedAgent.
   * 
   * @return
   */
  public PlacedAgentCommissionProfile getCandidatePlacedAgentCommissionProfile()
  {
    return candidatePlacedAgentCommissionProfile;
  }

  /**
   * Setter for candidatePlacedAgent.
   * @param candidatePlacedAgentCommissionProfile
   */
  public void setCandidatePlacedAgentCommissionProfile(PlacedAgentCommissionProfile candidatePlacedAgentCommissionProfile)
  {
    this.candidatePlacedAgentCommissionProfile = candidatePlacedAgentCommissionProfile;
  }

  /**
   * Breaks all associations between this PlacedAgent and the specified
   * AgentSnapshot.
   * @param agentSnapshot
   */
  public void remove(AgentSnapshot agentSnapshot)
  {
    getAgentSnapshots().remove(agentSnapshot);
    
    agentSnapshot.setPlacedAgent(null);
    
    SessionHelper.saveOrUpdate(this, PlacedAgent.DATABASE);
  }

    public ClientRole findByClientRoleFK(long clientRoleFK)
    {
        ClientRole clientRole = ClientRole.findByPK(clientRoleFK);

        return clientRole;
    }
}
