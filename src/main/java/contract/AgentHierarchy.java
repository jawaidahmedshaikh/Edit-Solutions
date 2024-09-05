/*
 * User: gfrosti
 * Date: Sep 7, 2004
 * Time: 11:18:38 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import agent.*;

import agent.common.*;

import contract.dm.dao.*;

import edit.common.*;

import edit.common.exceptions.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.CommissionHistory;

import java.util.*;

import org.dom4j.Element;
import fission.utility.*;
import group.*;


public class AgentHierarchy extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityI crudEntityImpl;
    private AgentHierarchyVO agentHierarchyVO;
    private AgentSnapshot[] agent_Snapshots = null;

    //  Parents
    private Long segmentFK;
    private Segment segment;
    private Long contractGroupFK;
    private ContractGroup contractGroup;
    private Long agentFK;
    private Integer commissionPhaseID;
    private Agent agent;

    //  Children
    private Set<AgentSnapshot> agentSnapshots;
    private Set<AgentHierarchyAllocation> agentHierarchyAllocations;

    /**
     * In support of the SEGElement interface.
     */
    private Element agentHierarchyElement;

    //  Database to use for AgentHierarchy
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    /**
     * Instantiates a AgentHierarchy entity with a default AgentHierarchyVO.
     */
    public AgentHierarchy()
    {
        init();
    }

    /**
     * Instantiates a AgentHierarchy entity with a AgentHierarchyVO retrieved from persistence.
     * @param agentHierarchyPK
     */
    public AgentHierarchy(long agentHierarchyPK)
    {
        init();

        crudEntityImpl.load(this, agentHierarchyPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a AgentHierarchy entity with a supplied AgentHierarchyVO.
     * @param agentHierarchyVO
     */
    public AgentHierarchy(AgentHierarchyVO agentHierarchyVO)
    {
        init();

        this.agentHierarchyVO = agentHierarchyVO;
    }

    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(agentHierarchyVO.getSegmentFK());
    }

    public void setSegmentFK(Long segmentFK)
    {
        agentHierarchyVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    public Long getContractGroupFK()
    {
        return SessionHelper.getPKValue(agentHierarchyVO.getContractGroupFK());
    }

    public void setContractGroupFK(Long contractGroupFK)
    {
        agentHierarchyVO.setContractGroupFK(SessionHelper.getPKValue(contractGroupFK));
    }

    public void setCommissionPhaseID(Long commissionPhaseID)
    {
        agentHierarchyVO.setCommissionPhaseID(agentHierarchyVO.getCommissionPhaseID());
    }

    public Integer getCommissionPhaseID()
    {
        return agentHierarchyVO.getCommissionPhaseID();
    }

    public Long getAgentFK()
    {
        return SessionHelper.getPKValue(agentHierarchyVO.getAgentFK());
    }

    public void setAgentFK(Long agentFK)
    {
        agentHierarchyVO.setAgentFK(SessionHelper.getPKValue(agentFK));
    }


    public Long getAgentHierarchyPK()
    {
        return SessionHelper.getPKValue(agentHierarchyVO.getAgentHierarchyPK());
    }

    public void setAgentHierarchyPK(Long agentHierarchyPK)
    {
        agentHierarchyVO.setAgentHierarchyPK(SessionHelper.getPKValue(agentHierarchyPK));
    }

    /**
     * Getter
     * @return  set of agentSnapshots
     */
    public Set<AgentSnapshot> getAgentSnapshots()
    {
        return agentSnapshots;
    }

    /**
     * Setter
     * @param agentSnapshots      set of agentSnapshots
     */
    public void setAgentSnapshots(Set<AgentSnapshot> agentSnapshots)
    {
        this.agentSnapshots = agentSnapshots;
    }

    /**
     * Adds a AgentSnapshot to the set of children
     * @param agentSnapshot
     */
    public void addAgentSnapshot(AgentSnapshot agentSnapshot)
    {
        this.getAgentSnapshots().add(agentSnapshot);

        agentSnapshot.setAgentHierarchy(this);

        SessionHelper.saveOrUpdate(agentSnapshot, AgentHierarchy.DATABASE);
    }

    /**
     * Removes a AgentSnapshot from the set of children
     * @param agentSnapshot
     */
    public void removeAgentSnapshot(AgentSnapshot agentSnapshot)
    {
        this.getAgentSnapshots().remove(agentSnapshot);

        agentSnapshot.setAgentHierarchy(null);

        SessionHelper.saveOrUpdate(agentSnapshot, AgentHierarchy.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Set<AgentHierarchyAllocation> getAgentHierarchyAllocations()
    {
        return agentHierarchyAllocations;
    }

    /**
     * Setter.
     * @param agentHierarchyAllocations
     */
    public void setAgentHierarchyAllocations(Set<AgentHierarchyAllocation> agentHierarchyAllocations)
    {
        this.agentHierarchyAllocations = agentHierarchyAllocations;
	}

    /**
     * Adds a AgentHierarchyAllocation to the set of children
     * @param agentHierarchyAllocation
     */
    public void addAgentHierarchyAllocation(AgentHierarchyAllocation agentHierarchyAllocation)
    {
        this.getAgentHierarchyAllocations().add(agentHierarchyAllocation);

        agentHierarchyAllocation.setAgentHierarchy(this);

        SessionHelper.saveOrUpdate(agentHierarchyAllocation, AgentHierarchy.DATABASE);
    }

    /**
     * Removes an AgentHierarchyAllocation from the set of children
     * @param agentSnapshot
     */
    public void removeAgentHierarchyAllocation(AgentHierarchyAllocation agentHierarchyAllocation)
    {
        this.getAgentHierarchyAllocations().remove(agentHierarchyAllocation);

        agentHierarchyAllocation.setAgentHierarchy(null);

        SessionHelper.saveOrUpdate(agentHierarchyAllocation, AgentHierarchy.DATABASE);
    }

    public EDITBigDecimal getAdvancePremium()
    {
        return SessionHelper.getEDITBigDecimal(agentHierarchyVO.getAdvancePremium());
    }

    public String getRegion()
    {
        return agentHierarchyVO.getRegion();
    }
    
    public void setAdvancePremium(EDITBigDecimal advancePremium)
    {
        agentHierarchyVO.setAdvancePremium(SessionHelper.getEDITBigDecimal(advancePremium));
    }

    public void setRegion(String region)
    {
        agentHierarchyVO.setRegion(region);
    }

    public void setCommissionPhaseID(Integer commissionPhaseID)
    {
        agentHierarchyVO.setCommissionPhaseID(commissionPhaseID);
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (agentHierarchyVO == null)
        {
            agentHierarchyVO = new AgentHierarchyVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        if (agentSnapshots == null)
        {
            agentSnapshots = new HashSet<AgentSnapshot>();
        }

        if (agentHierarchyAllocations == null)
        {
            agentHierarchyAllocations = new HashSet<AgentHierarchyAllocation>();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        //CONVERT TO HIBERNATE - 09/13/07
        hSave();
//        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        saveChildren();
    }

    public void save(AgentHierarchyVO agentHierarchyVO)
    {
        hSave();
        
        this.agentHierarchyVO = agentHierarchyVO;
        saveChildren();
    }

    @Override
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, getDatabase());
    }

    /**
     * Saves the associated children of this AgentHierarchy.
     */
    private void saveChildren()
    {
        AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();

        for (int i = 0; i < agentSnapshotVOs.length; i++)
        {
            AgentSnapshotVO agentSnapshotVO = agentSnapshotVOs[i];

            agentSnapshotVO.setAgentHierarchyFK(getPK());

            AgentSnapshot agentSnapshot = (AgentSnapshot) SessionHelper.map(agentSnapshotVO, DATABASE);
            agentSnapshot.setAgentHierarchy(this);

            PlacedAgent placedAgent = PlacedAgent.findByPK(new Long(agentSnapshotVO.getPlacedAgentFK()));
            agentSnapshot.setPlacedAgent(placedAgent);

            //CONVERT TO HIBERNATE - 09/13/07
            agentSnapshot.hSave();
    }

        AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = agentHierarchyVO.getAgentHierarchyAllocationVO();

        for (int i = 0; i < agentHierarchyAllocationVOs.length; i++)
        {
            AgentHierarchyAllocationVO agentHierarchyAllocationVO = agentHierarchyAllocationVOs[i];

            agentHierarchyAllocationVO.setAgentHierarchyFK(getPK());

            AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) SessionHelper.map(agentHierarchyAllocationVO, DATABASE);
            agentHierarchyAllocation.setAgentHierarchy(this);

            //CONVERT TO HIBERNATE - 09/13/07
            agentHierarchyAllocation.hSave();
        }
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return agentHierarchyVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return agentHierarchyVO.getAgentHierarchyPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.agentHierarchyVO = (AgentHierarchyVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * CRUD. The array of AgentSnapshots that make-up an AgentHiearchy.
     * @return the array of AgentSnapshots associated with an AgentHiearchy
     */
    public AgentSnapshot[] get_AgentSnapshots()
    {
        if (agent_Snapshots == null)
        {
            agent_Snapshots = (AgentSnapshot[]) crudEntityImpl.getChildEntities(this, AgentSnapshot.class, AgentSnapshotVO.class, ConnectionFactory.EDITSOLUTIONS_POOL);
        }

        return agent_Snapshots;
    }

    /**
     * Deletes the set of AgentSnapshots associated with this AgentHierarchy.
     */
    public void deleteAgentSnapshots()
    {
        List agentSnapshots = new ArrayList(getAgentSnapshots());

        if (agentSnapshots != null)
        {
            int count = agentSnapshots.size();
        
            for (int i = 0; i < count; i++)
            {
                AgentSnapshot currentAgentSnaphot = (AgentSnapshot) agentSnapshots.get(i);
            
                currentAgentSnaphot.hDelete();
            }
        }
    }

    /**
     * Deletes the set of AgentHierarchyAllocations associated with this AgentHierarchy.
     */
    public void deleteAgentHierarchyAllocations()
    {
        List agentHierarchyAllocations = new ArrayList(getAgentHierarchyAllocations());

        if (agentHierarchyAllocations != null)
        {
            int count = agentHierarchyAllocations.size();

            for (int i = 0; i < count; i++)
            {
                AgentHierarchyAllocation currentAgentHierAlloc = (AgentHierarchyAllocation) agentHierarchyAllocations.get(i);

                currentAgentHierAlloc.hDelete();
            }
        }
    }

    /**
     * Uses the Situation of the leaf agent to compare to the current active branch from the PlacedAgent hierarchy.
     * If there has been in a change in the PlacedAgentBranch, then substitute the current active branch into the
     * AgentHierarchy. If any AgentSnapshot of the AgentHierarchy has a CommissionOverride, then abort the swap and
     * throw an exception.
     */
    public void validateAgentHierarchy(EDITDate editDate) throws EDITAgentException
    {
        AgentSnapshot lowestLevelSnapshot = getLowestLevelAgent();

        PlacedAgent placedAgent = lowestLevelSnapshot.getPlacedAgent();

        AgentContract agentContract = placedAgent.getAgentContract();

        PlacedAgentBranch activeBranch = agentContract.findActivePlacedAgentBranch(placedAgent.getSituationCode(), editDate);

        if (activeBranch != null)
        {
            activeBranch.filter(editDate);
        
            if (branchChanged(activeBranch))
            {
                replaceHierarchy(activeBranch);
            }
        }
        else
        {
            throw new EDITAgentException(Constants.ErrorMsg.AGENT_STOPDATE_HAS_EXPIRED);
        }
    }

    /**
     * In a given set of AgentSnapshot(s), the AgentSnapshot whose HierarchyLevel is highest is the lowest level
     * AgentSnapshot.
     * @return the lowest level AgentSnapshot
     */
    public AgentSnapshot getLowestLevelAgent()
    {
        AgentSnapshot lowestLevelAgent;

        List agentSnapshots = new ArrayList(getAgentSnapshots());

        Collections.sort(agentSnapshots);

        lowestLevelAgent = (AgentSnapshot) agentSnapshots.get(agentSnapshots.size() - 1);

        return lowestLevelAgent;
    }

    /**
     * Finds the array of child AgentHierarchies associates with a parent Segment.
     * @param segmentPK
     * @return the array of child AgentHierarchies
     */
    public static AgentHierarchy[] findBySegmentPK(long segmentPK)
    {
        AgentHierarchyVO[] agentHierarchyVOs;

        AgentHierarchy[] agentHierarchies;

        agentHierarchyVOs = DAOFactory.getAgentHierarchyDAO().findBySegmentPK(segmentPK, false, null);

        agentHierarchies = (AgentHierarchy[]) CRUDEntityImpl.mapVOToEntity(agentHierarchyVOs, AgentHierarchy.class);

        return agentHierarchies;
    }

    /**
     * Adds an AgentSnapshot to the set of AgentSnapshots that make-up this AgentHierarchy.
     * @param agentSnapshot
     */
    public void add_AgentSnapshot(AgentSnapshot agentSnapshot)
    {
        agentSnapshot.setAgentHierarchyFK(new Long(getPK()));

        agentSnapshot.save();
    }

    /** 
    /** 
     * Deletes the current set of AgentSnapshots of this AgentHierarchy and replaces them with the set of
     * AgentSnapshots that can be generated from the specified PlacedAgentBranch.
     * @param newBranch
     */
    private void replaceHierarchy(PlacedAgentBranch newBranch)
    {
        deleteAgentSnapshots();

        PlacedAgent[] placedAgents = newBranch.getPlacedAgents();

        SessionHelper.beginTransaction(AgentHierarchy.DATABASE);

        for (int i = 0; i < placedAgents.length; i++)
        {
            PlacedAgent placedAgent = placedAgents[i];
            
            int hierarchyLevel = placedAgent.getHierarchyLevel();

            AgentSnapshot agentSnapshot = (AgentSnapshot) SessionHelper.newInstance(AgentSnapshot.class, AgentHierarchy.DATABASE);

            agentSnapshot.setHierarchyLevel(hierarchyLevel);
            
            placedAgent.add(agentSnapshot);

            addAgentSnapshot(agentSnapshot);

        }
        
        SessionHelper.commitTransaction(AgentHierarchy.DATABASE);
    }

    /**
     * Retrieve the AgentHierarchy for the given agentHierarchyPK
     * @param agentHierarchyPK  - The primary key for the AgentHierarchy that is to be retrieved.
     * @return
     */
    public static AgentHierarchy findByPK(Long agentHierarchyPK)
    {
        return (AgentHierarchy) SessionHelper.get(AgentHierarchy.class, agentHierarchyPK, AgentHierarchy.DATABASE);
    }

    /**
     * Finder.
     * @param agentPK
     * @return
     */
    public static AgentHierarchy[] findBy_AgentPK(Long agentPK)
    {
        String hql = "from AgentHierarchy a where a.AgentFK = :agentFK";

        Map params = new HashMap();

        params.put("agentFK", agentPK);

        List results = SessionHelper.executeHQL(hql, params, AgentHierarchy.DATABASE);

        return (AgentHierarchy[]) results.toArray(new AgentHierarchy[results.size()]);
    }

    /**
     * Finder.
     * @param agentPK
     * @return
     */
    public static AgentHierarchy findBy_AgentFK_AND_PlacedAgentFK(Long agentFK, Long placedAgentFK)
    {
        AgentHierarchy agentHierarchy = null;

        String hql = " select agentHierarchy from AgentHierarchy agentHierarchy" +
                        " join agentHierarchy.AgentSnapshots agentSnapshot" +
                        " where agentSnapshot.PlacedAgentFK = :placedAgentFK" +
                        " and agentHierarchy.AgentFK = :agentFK";

        Map params = new HashMap();

        params.put("agentFK", agentFK);
        params.put("placedAgentFK", placedAgentFK);

        List results = SessionHelper.executeHQL(hql, params, AgentHierarchy.DATABASE);

        if (!results.isEmpty())
        {
            agentHierarchy = (AgentHierarchy) results.get(0);
        }

        return agentHierarchy;
    }
    
    /**
     * Finder.
     * @param agentFK
     * @param segmentFK
     * @return
     */
    public static AgentHierarchy[] findBy_Agent_AND_SegmentFK(Long segmentPK, Long agentFK)
    {
        String hql = " select agentHierarchy from AgentHierarchy agentHierarchy" +
                        " where agentHierarchy.SegmentFK = :segmentPK" +
                        " and agentHierarchy.AgentFK = :agentFK";

        Map params = new HashMap();

        params.put("agentFK", agentFK);
        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, AgentHierarchy.DATABASE);
        
        return (AgentHierarchy[]) results.toArray(new AgentHierarchy[results.size()]);
        
    }

    /**
     * Generates a snapshot by mapping the PlacedAgent elements of the PlacedAgentBranch for the specified lowest-level
     * PlacedAgent.
     * @param placedAgent
     */
    public void generateAgentSnapshot(PlacedAgent placedAgent)
    {
        PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(placedAgent);

        PlacedAgent[] placedAgents = placedAgentBranch.getPlacedAgents();

        for (int i = 0; i < placedAgents.length; i++)
        {
            PlacedAgent currentPlacedAgent = placedAgents[i];

            AgentSnapshot agentSnapshot = (AgentSnapshot) SessionHelper.newInstance(AgentSnapshot.class, AgentHierarchy.DATABASE);
            
            currentPlacedAgent.add(agentSnapshot);

            addAgentSnapshot(agentSnapshot);

            agentSnapshot.setHierarchyLevel(currentPlacedAgent.getHierarchyLevel());
        }
    }
    
    /**
   * Breaks any association between this AgentHierarchy and the specified 
   * AgentSnaspshot.
   * @param agentSnapshot
   */
    public void remove(AgentSnapshot agentSnapshot)
    {
      getAgentSnapshots().remove(agentSnapshot);
      
      agentSnapshot.setAgentHierarchy(null);
      
      SessionHelper.saveOrUpdate(this, AgentHierarchy.DATABASE);
    }

    /**
     * Finder.
     * @param placedAgentPK
     * @return
     */
    public static AgentHierarchy[] findBy_PlacedAgentPK(long placedAgentPK)
    {
        return (AgentHierarchy[]) CRUDEntityImpl.mapVOToEntity(new AgentHierarchyDAO().findBy_PlacedAgentPK(placedAgentPK), AgentHierarchy.class);
    }

    /**
     * Finder. Includes the associated AgentSnapshots.
     * @param placedAgentPK
     * @return
     */
    public static AgentHierarchy[] findBy_PlacedAgent_V1(PlacedAgent placedAgent)
    {
        String hql = " select agentHierarchy from AgentHierarchy agentHierarchy" +
                        " join agentHierarchy.AgentSnapshots agentSnapshot" +
                        " where agentSnapshot.PlacedAgent = :placedAgent";

        Map params = new HashMap();

        params.put("placedAgent", placedAgent);

        List results = SessionHelper.executeHQL(hql, params, AgentHierarchy.DATABASE);

        return (AgentHierarchy[]) results.toArray(new AgentHierarchy[results.size()]);
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
     * For CRUD.
     */
    public Segment get_Segment()
    {
            return new Segment(this.agentHierarchyVO.getSegmentFK()); // CRUD
    }

    /**
     * For Hibernate.
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    public ContractGroup getContractGroup()
    {
        return this.contractGroup;
    }

    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    public Agent getAgent()
    {
        return this.agent;
    }

    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }



    /**
     * Finder.
     * @param segmentPK
     * @param placedAgentPK
     * @return
     */
    public static AgentHierarchy findBy_SegmentPK_AND_PlacedAgentPK(long segmentPK, long placedAgentPK)
    {
        AgentHierarchy agentHierarchy = null;

        AgentHierarchyVO[] agentHierarchyVOs = new AgentHierarchyDAO().findBy_SegmentPK_AND_PlacedAgentPK(segmentPK, placedAgentPK);

        if (agentHierarchyVOs != null)
        {
            agentHierarchy = new AgentHierarchy(agentHierarchyVOs[0]);
        }

        return agentHierarchy;
    }

    /**
     * The AgentSnapshot in the set of AgentSnashots associated with the specified PlacedAgent, if any.
     * @param placedAgent
     */
    public AgentSnapshot getAgentSnapshot(PlacedAgent placedAgent)
    {
        AgentSnapshot agentSnapshot = null;

        Set agentSnapshots = getAgentSnapshots();

        for (Iterator iterator = agentSnapshots.iterator(); iterator.hasNext();)
        {
            AgentSnapshot snapshot = (AgentSnapshot) iterator.next();
            
            if (snapshot.getPlacedAgentFK().longValue() == (placedAgent.getPlacedAgentPK().longValue()))
            {
                agentSnapshot = snapshot;
            }
        }

        return agentSnapshot;
    }

    /**
     * True if the specified writingAgent's hierarchyLevel is 1 greater than than hierarchyLevel of the specfied
     * reportToAgent within the set of AgentSnapshots of this AgentHierarchy.
     * @param writingAgent
     * @param reportToAgent
     * @return
     */
    public boolean directlyReportsTo(PlacedAgent writingAgent, PlacedAgent reportToAgent)
    {
        boolean directlyReportsTo = false;

        int writingAgentLevel = getAgentSnapshot(writingAgent).getHierarchyLevel();

        int reportToAgentLevel = getAgentSnapshot(reportToAgent).getHierarchyLevel();

        directlyReportsTo = (writingAgentLevel == (reportToAgentLevel + 1));

        return directlyReportsTo;
    }

    /**
     * Calculates the total percentage of the agent split.  For each AgentHierarchy, adds the allocationPercent of the
     * active AgentHierarchyAllocations to get the total for the Segment.
     *
     * @param agentHierarchyVOs
     *
     * @return
     */
    public static EDITBigDecimal getTotalAgentSplitPercent(AgentHierarchyVO[] agentHierarchyVOs)
    {
        EDITBigDecimal totalAgentSplitPercent = new EDITBigDecimal();

        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
           AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = agentHierarchyVOs[i].getAgentHierarchyAllocationVO();

           for (int j = 0; j < agentHierarchyAllocationVOs.length; j++)
           {
               AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation(agentHierarchyAllocationVOs[j]);

               if (agentHierarchyAllocation.isActive())
               {
                   totalAgentSplitPercent = totalAgentSplitPercent.addEditBigDecimal(agentHierarchyAllocation.getAllocationPercent());
               }
           }
        }

        return totalAgentSplitPercent;
    }
    
    /**
     * This only works if there are agentHierarchies on the base coverage (none on riders) .. not accurate for UL!!
     * Calculates the total percentage of the agent split.  For each AgentHierarchy, adds the allocationPercent of the
     * active AgentHierarchyAllocations to get the total for the Segment.  Active-status of AgentHierarchyAllocation is 
     * determined using the effectiveDate parameter.
     *
     * @param agentHierarchyVOs
     * @param effectiveDate
     *
     * @return
     */
    public static EDITBigDecimal getTotalAgentSplitPercent(AgentHierarchyVO[] agentHierarchyVOs, EDITDate effectiveDate)
    {
        EDITBigDecimal totalAgentSplitPercent = new EDITBigDecimal();

        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
           AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = agentHierarchyVOs[i].getAgentHierarchyAllocationVO();

           for (int j = 0; j < agentHierarchyAllocationVOs.length; j++)
           {
               AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation(agentHierarchyAllocationVOs[j]);

               if (agentHierarchyAllocation.isActive(effectiveDate))
               {
                   totalAgentSplitPercent = totalAgentSplitPercent.addEditBigDecimal(agentHierarchyAllocation.getAllocationPercent());
               }
           }
        }

        return totalAgentSplitPercent;
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, AgentHierarchy.DATABASE);
    }


    /**
         * break the parent-child relation in all the related tables
         * delete the chlildren AgentHierarchyAllocation & AgentSnapshot(tree)
         * Delete the entity AgentHierarchy using Hibernate
    */
    public void hDelete(AgentHierarchy agentHierarchyCurr) throws EDITCaseException
    {
        AgentHierarchy agentHierarchy = AgentHierarchy.findByPK(agentHierarchyCurr.getAgentHierarchyPK());

        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            agentHierarchy.deleteAgentSnapshots();
            agentHierarchy.deleteAgentHierarchyAllocations();
            agentHierarchy.hDelete();            

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            throw new EDITCaseException(e.getMessage());
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }
    
    
    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Finder.
     * @param segmentPK
     * @return
     */
    public static List findBy_SegmentPK(Long segmentPK)
    {
        String hql = " from AgentHierarchy agentHierarchy" +
                " where agentHierarchy.SegmentFK = :segmentFK";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, AgentHierarchy.DATABASE);

        return results;
    }
    
    /**
   * Finder.
   * @param segmentPK
   * @param commissionHistoryPK
   * @return
   */
    public static AgentHierarchy[] findBy_SegmentPK_AND_CommissionHistoryPK(Long segmentPK, Long commissionHistoryPK)
    {
      String hql = " from AgentHierarchy agentHierarchy" +
                  " join agentHierarchy.AgentSnapshots agentSnapshot" +
                  " join agentSnapshot.PlacedAgent placedAgent" +
                  " join placedAgent.CommissionHistories commissionHistory" +
                  " where agentHierarchy.SegmentFK = :segmentPK" +
                  " and commissionHistory.CommissionHistoryPK = :commissionHistoryPK";
                  
      Map params = new HashMap();
      
      params.put("segmentPK", segmentPK);
      
      params.put("commissionHistoryPK", commissionHistoryPK);
      
      List results = SessionHelper.executeHQL(hql, params, AgentHierarchy.DATABASE);
      
      return (AgentHierarchy[]) results.toArray(new AgentHierarchy[results.size()]);
    }
    
    /**
   * The CommissionHistory.SourcePlacedAgent has an association with (only) the
   * Writing Agent of this AgentHierarchy.
   * @param commissionHistory
   * @return true if the specified CommissionHistory.SourcePlacedAgent matches the Writing Agent's PlacedAgent.
   */
    public boolean writingAgentSharesSourcePlacedAgentOf(CommissionHistory commissionHistory)
    {
      AgentSnapshot writingAgent = getLowestLevelAgent();
      
      long writingAgentPlacedAgentFK = writingAgent.getPlacedAgentFK().longValue();
      
      long commissionHistorySourcePlacedAgentFK = commissionHistory.getSourcePlacedAgent().getPlacedAgentPK().longValue();
      
      return (writingAgentPlacedAgentFK == commissionHistorySourcePlacedAgentFK);
    }
    
    /**
   * Within the set of AgentSnapshots for this AgentHierarchy, there can only be one AgentSnapshot
   * whose PlacedAgent match the specified CommissionHistory.PlacedAgent.
   * 
   * @param commissionHistory
   * @return the AgentSnapshot whose PlacedAgent matches the specified CommissionHistory.PlacedAgent
   */
    public AgentSnapshot matchCommissionHistory(CommissionHistory commissionHistory)
    {
      List agentSnapshots = new ArrayList(getAgentSnapshots());
      
      long commissionHistoryPlacedAgentFK = commissionHistory.getPlacedAgentFK().longValue();
      
      AgentSnapshot matchingAgentSnapshot = null;
      
      for (int i = 0; i <agentSnapshots.size(); i++)
      {
        AgentSnapshot currentAgentSnapshot = (AgentSnapshot) agentSnapshots.get(i);
      
        long agentSnapshotPlacedAgentFK = currentAgentSnapshot.getPlacedAgentFK().longValue();
        
        if (agentSnapshotPlacedAgentFK == commissionHistoryPlacedAgentFK)
        {
          matchingAgentSnapshot = currentAgentSnapshot; 
          
          break;
        }
      }
      
      return matchingAgentSnapshot;
    }

    /**
     * The DOM4J Element equivalent of this AgentHierarchy.
     * @return
     */
    public Element getAsElement()
    {
//    if (agentHierarchyElement == null)
//    {
//      agentHierarchyElement = SessionHelper.mapToElement(this);
//    }
//    else
//    {
//      SessionHelper.mapToElement(this, agentHierarchyElement);
//    }
    
        return SessionHelper.mapToElement(this, this.getDatabase(), false, false);
    }
    
    /**
     * The current set of AgentSnapshots represent a "moment in time" for the
     * set of PlacedAgents driven by a specified Writing Agent. However, "over time",
     * the set of AgentSnapshot may have become stale. This method compares the most
     * current set of PlacedAgents (driven by the Writing Agent) to the set of PlacedAgents
     * captured by the original AgentSnapshots.
     * @param activeBranch
     * @return
     */
    private boolean branchChanged(PlacedAgentBranch activeBranch)
    {
        boolean branchChanged = false;
    
        PlacedAgent[] currentPlacedAgents = activeBranch.getPlacedAgents();
    
        int currentBranchSize = currentPlacedAgents.length;
        
        // Sort them ascending by HierarchyLevel first.
        List<AgentSnapshot> agentSnapshots = new ArrayList<AgentSnapshot>(getAgentSnapshots());

        int originalBranchSize = agentSnapshots.size();
        
        if (currentBranchSize == originalBranchSize) // keep testing
        {
            Collections.sort(agentSnapshots);        
            
            for (int i = 0; i < originalBranchSize; i++)
            {
                Long originalPlacedAgentPK = agentSnapshots.get(i).getPlacedAgentFK();
                
                int originalHierarchyLevel = agentSnapshots.get(i).getHierarchyLevel();
                
                Long currentPlacedAgentPK = currentPlacedAgents[i].getPlacedAgentPK();
                
                int currentHierarchyLevel = currentPlacedAgents[i].getHierarchyLevel();
                
                if ((originalPlacedAgentPK.longValue() != currentPlacedAgentPK.longValue()))
                {
                    branchChanged = true;
                    
                    break;
                }
                else if (originalHierarchyLevel != currentHierarchyLevel)
                {
                    branchChanged = true;
                    
                    break;
                }
            }
        }
        else
        {
            branchChanged = true;
        }
        
        return branchChanged;
    }

    public String getDatabase()
    {
        return AgentHierarchy.DATABASE;
    }

    /**
     * Makes a copy of this AgentHierarchy and its children
     *
     * @return  AgentHierarchy
     */
    public AgentHierarchy deepCopy()
    {
        AgentHierarchy newAgentHierarchy = (AgentHierarchy) SessionHelper.shallowCopy(this, AgentHierarchy.DATABASE);

        newAgentHierarchy.setAgent(this.getAgent());

        for (Iterator iterator = this.agentSnapshots.iterator(); iterator.hasNext();)
        {
            AgentSnapshot agentSnapshot = (AgentSnapshot) iterator.next();

            AgentSnapshot newAgentSnapshot = (AgentSnapshot) SessionHelper.shallowCopy(agentSnapshot, AgentHierarchy.DATABASE);

            newAgentSnapshot.setCommissionProfile(agentSnapshot.getCommissionProfile());
            newAgentSnapshot.setPlacedAgent(agentSnapshot.getPlacedAgent());

            newAgentHierarchy.addAgentSnapshot(newAgentSnapshot);
        }

        for (Iterator iterator = this.agentHierarchyAllocations.iterator(); iterator.hasNext();)
        {
            AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) iterator.next();

            AgentHierarchyAllocation newAgentHierarchyAllocation = (AgentHierarchyAllocation)
                                    SessionHelper.shallowCopy(agentHierarchyAllocation, AgentHierarchy.DATABASE);

            newAgentHierarchy.addAgentHierarchyAllocation(newAgentHierarchyAllocation);
        }

        return newAgentHierarchy;
    }
}