/*
 * User: gfrosti
 * Date: Sep 7, 2004
 * Time: 11:19:55 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import agent.PlacedAgent;
import agent.CommissionProfile;

import contract.dm.dao.AgentSnapshotDAO;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.AgentSnapshotVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityI;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import event.CommissionHistory;
import event.TransactionPriority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AgentSnapshot extends HibernateEntity implements CRUDEntity, Comparable
{
    private CRUDEntityI crudEntityImpl;
    private AgentSnapshotVO agentSnapshotVO;
    private AgentHierarchy agentHierarchy;
    private PlacedAgent placedAgent;
    private CommissionProfile commissionProfile;
    private Set<CommissionHistory> commissionHistories = new HashSet<CommissionHistory>();

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    /**
     * Checks if the associated PlacedAgent is, itself, associated with an AgentGroup.
     * @return
     */
    public boolean isParticipatingInAgentGroup()
    {
//        PlacedAgent placedAgent = PlacedAgent.findBy_PK(getPlacedAgentFK());
//
//        Segment segment = getAgentHierarchy().getSegment();
//
//        EDITDate appReceivedDate = segment.getApplicationReceivedDate();
//
//        return placedAgent.isParticipatingInAgentGroup(appReceivedDate);
        return false;
    }

    /**
     * Instantiates a AgentSnapshot entity with a default AgentSnapshotVO.
     */
    public AgentSnapshot()
    {
        init();
    }

    /**
     * Instantiates a AgentSnapshot entity with a AgentSnapshotVO retrieved from persistence.
     * @param agentSnapshotPK
     */
    public AgentSnapshot(long agentSnapshotPK)
    {
        init();

        crudEntityImpl.load(this, agentSnapshotPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a AgentSnapshot entity with a supplied AgentSnapshotVO.
     * @param agentSnapshotVO
     */
    public AgentSnapshot(AgentSnapshotVO agentSnapshotVO)
    {
        init();

        this.agentSnapshotVO = agentSnapshotVO;
    }

    /**
     * Getter.
     * @return
     */
    public AgentHierarchy getAgentHierarchy()
    {
        return agentHierarchy;
    }

    /**
     * Setter.
     * @param agentHierarchy
     */
    public void setAgentHierarchy(AgentHierarchy agentHierarchy)
    {
        this.agentHierarchy = agentHierarchy;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (agentSnapshotVO == null)
        {
            agentSnapshotVO = new AgentSnapshotVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return agentSnapshotVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return agentSnapshotVO.getAgentSnapshotPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.agentSnapshotVO = (AgentSnapshotVO) voObject;
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
     * (AgentSnapshot1 < AgentSnapshot) if (AgentSnapshot1.HierarchyLevel < AgentSnapshot2.HierarchyLevel)
     * @param obj the AgentSnapshot to compare to
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj)
    {
        int compareToValue;

        AgentSnapshot visitingAgentSnapshot = (AgentSnapshot) obj;

        int visitingHierarchyLevel = visitingAgentSnapshot.getHierarchyLevel();

        compareToValue = new Integer(getHierarchyLevel()).compareTo(new Integer(visitingHierarchyLevel));

        return compareToValue;
    }

    public Long getAgentHierarchyFK()
    {
        return SessionHelper.getPKValue(agentSnapshotVO.getAgentHierarchyFK());
    }

    //-- long getAgentHierarchyFK()
    public Long getAgentSnapshotPK()
    {
        return SessionHelper.getPKValue(agentSnapshotVO.getAgentSnapshotPK());
    }

    //-- long getAgentSnapshotPK()
    public EDITBigDecimal getCommissionOverrideAmount()
    {
        return SessionHelper.getEDITBigDecimal(agentSnapshotVO.getCommissionOverrideAmount());
    }

    //-- double getCommissionOverrideAmount()
    public EDITBigDecimal getCommissionOverridePercent()
    {
        return SessionHelper.getEDITBigDecimal(agentSnapshotVO.getCommissionOverridePercent());
    }

    //-- long getCommissionProfileFK()
    public int getHierarchyLevel()
    {
        return agentSnapshotVO.getHierarchyLevel();
    }

    //-- int getHierarchyLevel()
    public Long getPlacedAgentFK()
    {
        return SessionHelper.getPKValue(agentSnapshotVO.getPlacedAgentFK());
    }

    //-- long getPlacedAgentFK()

    public EDITBigDecimal getAdvanceAmount()
    {
        return SessionHelper.getEDITBigDecimal(agentSnapshotVO.getAdvanceAmount());
    }

    public EDITBigDecimal getAdvanceRecovery()
    {
        return SessionHelper.getEDITBigDecimal(agentSnapshotVO.getAdvanceRecovery());
    }

    public String getServicingAgentIndicator()
    {
        return agentSnapshotVO.getServicingAgentIndicator();
    }

    public void setServicingAgentIndicator(String servicingAgentIndicator)
    {
        agentSnapshotVO.setServicingAgentIndicator(servicingAgentIndicator);
    }

    public EDITBigDecimal getAdvancePercent()
    {
        return SessionHelper.getEDITBigDecimal(agentSnapshotVO.getAdvancePercent());
    }

    public void setAdvancePercent(EDITBigDecimal advancePercent)
    {
        agentSnapshotVO.setAdvancePercent(SessionHelper.getEDITBigDecimal(advancePercent));
    }

    public EDITBigDecimal getRecoveryPercent()
    {
        return SessionHelper.getEDITBigDecimal(agentSnapshotVO.getRecoveryPercent());
    }

    public void setRecoveryPercent(EDITBigDecimal recoveryPercent)
    {
        agentSnapshotVO.setRecoveryPercent(SessionHelper.getEDITBigDecimal(recoveryPercent));
    }

    public void setAgentHierarchyFK(Long agentHierarchyFK)
    {
        agentSnapshotVO.setAgentHierarchyFK(SessionHelper.getPKValue(agentHierarchyFK));
    }

    public Long getCommissionProfileFK()
    {
        return SessionHelper.getPKValue(agentSnapshotVO.getCommissionProfileFK());
    }

    public void setCommissionProfileFK(Long commissionProfileFK)
    {
        agentSnapshotVO.setCommissionProfileFK(SessionHelper.getPKValue(commissionProfileFK));
    }

    public CommissionProfile get_CommissionProfile()
    {
        return new CommissionProfile(agentSnapshotVO.getCommissionProfileFK());
    }

    public CommissionProfile getCommissionProfile()
    {
        return commissionProfile;
    }

    public void setCommissionProfile(CommissionProfile commissionProfile)
    {
        this.commissionProfile = commissionProfile;
    }

    //-- void setAgentHierarchyFK(long)
    public void setAgentSnapshotPK(Long agentSnapshotPK)
    {
        agentSnapshotVO.setAgentSnapshotPK(SessionHelper.getPKValue(agentSnapshotPK));
    }

    //-- void setAgentSnapshotPK(long)
    public void setCommissionOverrideAmount(EDITBigDecimal commissionOverrideAmount)
    {
        agentSnapshotVO.setCommissionOverrideAmount(SessionHelper.getEDITBigDecimal(commissionOverrideAmount));
    }

    //-- void setCommissionOverrideAmount(double)
    public void setCommissionOverridePercent(EDITBigDecimal commissionOverridePercent)
    {
        agentSnapshotVO.setCommissionOverridePercent(SessionHelper.getEDITBigDecimal(commissionOverridePercent));
    }

    //-- void setCommissionProfileFK(long)
    public void setHierarchyLevel(int hierarchyLevel)
    {
        agentSnapshotVO.setHierarchyLevel(hierarchyLevel);
    }

    //-- void setHierarchyLevel(int)
    public void setPlacedAgentFK(Long placedAgentFK)
    {
        agentSnapshotVO.setPlacedAgentFK(SessionHelper.getPKValue(placedAgentFK));
    }

    public void setAdvanceAmount(EDITBigDecimal advanceAmount)
    {
        agentSnapshotVO.setAdvanceAmount(SessionHelper.getEDITBigDecimal(advanceAmount));
    }

    public void setAdvanceRecovery(EDITBigDecimal advanceRecovery)
    {
        agentSnapshotVO.setAdvanceRecovery(SessionHelper.getEDITBigDecimal(advanceRecovery));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCommHoldAmountOverride()
    {
        return SessionHelper.getEDITBigDecimal(agentSnapshotVO.getCommHoldAmountOverride());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getCommHoldReleaseDateOverride()
    {
        return SessionHelper.getEDITDate(agentSnapshotVO.getCommHoldReleaseDateOverride());
    }

    /**
     * Setter.
     * @param commHoldAmountOverride
     */
    public void setCommHoldAmountOverride(EDITBigDecimal commHoldAmountOverride)
    {
        agentSnapshotVO.setCommHoldAmountOverride(SessionHelper.getEDITBigDecimal(commHoldAmountOverride));
    }

    /**
     * Setter.
     * @param commHoldReleaseDateOverride
     */
    public void setCommHoldReleaseDateOverride(EDITDate commHoldReleaseDateOverride)
    {
        agentSnapshotVO.setCommHoldReleaseDateOverride(SessionHelper.getEDITDate(commHoldReleaseDateOverride));
    }

    //-- void setPlacedAgentFK(long)

    /**
     * Getter/CRUD
     * @return
     */
    public PlacedAgent get_PlacedAgent()
    {
        return new PlacedAgent(agentSnapshotVO.getPlacedAgentFK());
    }

    @Override
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, getDatabase());
    }
    
    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    { 
        // Even though the Hibernate is set to cascade the delete to 
        // CommissionHistory, CommissionHistory has another parent
        // by default so we are safe.
        
        // 1. Remove the association from AgentHierarchy->AgentSnapshot
        getAgentHierarchy().remove(this);
        
        // 2. Remove the association from AgentSnapshot->CommissionHistory
        List commissionHistories = new ArrayList(getCommissionHistories());
        
        int count = commissionHistories.size();
        
        for (int i = 0; i < count; i++)
        {
          CommissionHistory currentCommissionHistory = (CommissionHistory) commissionHistories.get(i);
          
          remove(currentCommissionHistory);
        }
        
        // 3. Remove the association from PlacedAgent->AgentSnapshot
        getPlacedAgent().remove(this);
        
        // 4. Finally, delete this AgentSnapshot
        SessionHelper.delete(this, AgentSnapshot.DATABASE);
    }
    
    /**
   * Breaks all associations between this AgentSnapshot and the specified
   * CommissionHistory.
   * @param commissionHistory
   */
    public void remove(CommissionHistory commissionHistory)
    {
      getCommissionHistories().remove(commissionHistory);
      
      commissionHistory.setAgentSnapshot(null);
      
      SessionHelper.saveOrUpdate(this, AgentSnapshot.DATABASE);
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Finder.
     * @param agentSnapshotPK
     * @return
     */
    public static AgentSnapshot findBy_PK(Long agentSnapshotPK)
    {
        return (AgentSnapshot) SessionHelper.get(AgentSnapshot.class, agentSnapshotPK, AgentSnapshot.DATABASE);
    }
    
    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }

  /**
   * Setter.
   * @param commissionHistories
   */
  public void setCommissionHistories(Set<CommissionHistory> commissionHistories)
  {
    this.commissionHistories = commissionHistories;
  }

  /**
   * Getter.
   * @return
   */
  public Set<CommissionHistory> getCommissionHistories()
  {
    return commissionHistories;
  }
  
  /**
   * Adder.
   * @param commissionHistory
   */
  public void add(CommissionHistory commissionHistory)
  {
    getCommissionHistories().add(commissionHistory);
    
    commissionHistory.setAgentSnapshot(this);
  }

    public void setPlacedAgent(PlacedAgent placedAgent)
    {
        this.placedAgent = placedAgent;
    }

  /**
   * Finder.
   * @param agentHierarchyPK
   * @return
   */
  public static AgentSnapshot[] findBy_AgentHierarchyPK(long agentHierarchyPK)
  {
    AgentSnapshotVO[] agentSnapshotVOs = new AgentSnapshotDAO().findBy_AgentHierarchyPK(agentHierarchyPK);

    return (AgentSnapshot[]) CRUDEntityImpl.mapVOToEntity(agentSnapshotVOs, AgentSnapshot.class);    
  }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AgentSnapshot.DATABASE;
    }

  /**
   * Sums all CommissionHistory.CommissionAmount across [all} CommissionHistories that:
   * 
   * 1. Are Commissionable Events (determined from the TransactionTypes from the TransactionProcessor table).
   * 2. Associated with the specified AgentSnapshot.
   * 
   * Summation is performed by subtracting any CommissionAmount that is labeled with CommissionHistory.ChargeTypeCT of "Chargeback", and
   * adding any other one.
   */  
  public EDITBigDecimal getTotalCommissionsPaid()
  {
    String[] trxTypeCTs = TransactionPriority.getCommissionableEvents();

    EDITBigDecimal totalPositiveCommissionsPaid = CommissionHistory.findSeparateTotalPositiveCommissionsPaidBy_AgentSnapshot_CommissionableEvents(this, trxTypeCTs);

    EDITBigDecimal totalNegativeCommissionsPaid = CommissionHistory.findSeparateTotalNegativeCommissionsPaidBy_AgentSnapshot_CommissionableEvents(this, trxTypeCTs);

    EDITBigDecimal totalCommissionsPaid = totalPositiveCommissionsPaid.subtractEditBigDecimal(totalNegativeCommissionsPaid);
    
    return totalCommissionsPaid;
  }
  
  /**
     * Finds all [writing Agent] AgentSnapshots with the following
     * composition (prefetched):
     * 
     * AgentSnapshot
     *      AgentHierarchy
     *             AgentHierarchyAllocation (only one, right?)
     *      PlacedAgent
     *             AgentContract
     *                    Agent
     *              PlacedAgentCommissionProfile
     *                    CommissionProfile
     * @param agentHierarchy
     * @return
     */
    public static AgentSnapshot findBy_AgentHierarchyPK_V1(Long agentHierarchyPK)
    {
        AgentSnapshot agentSnapshot = null;
    
        String hql = " select agentSnapshot" +
                    " from AgentSnapshot agentSnapshot" +
                    " join fetch agentSnapshot.AgentHierarchy agentHierarchy" +
                    " join fetch agentHierarchy.AgentHierarchyAllocations" +
                    " join fetch agentSnapshot.PlacedAgent placedAgent" +
                    " join fetch placedAgent.AgentContract agentContract" +
                    " join fetch agentContract.Agent agent" +
                    " join fetch placedAgent.ClientRole clientRole" +
                    " join fetch clientRole.ClientDetail" +
                    " join fetch placedAgent.PlacedAgentCommissionProfiles placedAgentCommissionProfile" +
                    " join fetch placedAgentCommissionProfile.CommissionProfile" +
                    
                    " where agentSnapshot.AgentHierarchyFK = :agentHierarchyPK" +                    
                    " and agentSnapshot.HierarchyLevel = " +
                    
                    "(" +
                        " select max(agentSnapshot.HierarchyLevel)" +
                        " from AgentSnapshot agentSnapshot" +
                        
                        " where agentSnapshot.AgentHierarchyFK = :agentHierarchyPK" + 
                    ")";
                    
        EDITMap params = new EDITMap("agentHierarchyPK", agentHierarchyPK);                    
                    
        List<AgentSnapshot> results = SessionHelper.executeHQL(hql, params, AgentSnapshot.DATABASE);
        
        if (!results.isEmpty())
        {
            agentSnapshot = results.get(0);   
        }
          
        return agentSnapshot;
    }

    /**
       * Finds the [writing Agent] AgentSnapshot
       * @param agentHierarchyPK
       * @return
       */
      public static AgentSnapshot findWritingAgentSnapshotBy_AgentHierarchyPK(Long agentHierarchyPK)
      {
          AgentSnapshot agentSnapshot = null;

          String hql = " select agentSnapshot" +
                      " from AgentSnapshot agentSnapshot" +
                      " where agentSnapshot.AgentHierarchyFK = :agentHierarchyPK" +
                      " and agentSnapshot.HierarchyLevel = " +
                      "(select max(agentSnapshot.HierarchyLevel) from AgentSnapshot agentSnapshot" +
                      " where agentSnapshot.AgentHierarchyFK = :agentHierarchyPK)";

          EDITMap params = new EDITMap("agentHierarchyPK", agentHierarchyPK);

          List<AgentSnapshot> results = SessionHelper.executeHQL(hql, params, AgentSnapshot.DATABASE);

          if (!results.isEmpty())
          {
              agentSnapshot = results.get(0);
          }

          return agentSnapshot;
      }

      public static EDITBigDecimal sumAvanceAmount(Long segmentPK)
      {
          Object summedAmount = null;
          EDITBigDecimal totalAdvanceAmount = new EDITBigDecimal();

          String hql = " select sum(agentSnapshot.AdvanceAmount)" +
                       " from AgentSnapshot agentSnapshot" +
                       " join agentSnapshot.AgentHierarchy agentHierarchy" +
                       " where agentHierarchy.SegmentFK = :segmentPK";


          EDITMap params = new EDITMap("segmentPK", segmentPK);

          List results = SessionHelper.executeHQL(hql, params, AgentSnapshot.DATABASE);

          if (!results.isEmpty())
          {
              summedAmount = results.get(0);
          }

          if (summedAmount != null)
          {
              totalAdvanceAmount = new EDITBigDecimal(summedAmount.toString());
          }

          return totalAdvanceAmount;
      }

      public static EDITBigDecimal sumAvanceRecovery(Long segmentPK)
      {
          Object summedAmount = null;
          EDITBigDecimal totalAdvanceRecovery = new EDITBigDecimal();

          String hql = " select sum(agentSnapshot.AdvanceRecovery)" +
                       " from AgentSnapshot agentSnapshot" +
                       " join agentSnapshot.AgentHierarchy agentHierarchy" +
                       " where agentHierarchy.SegmentFK = :segmentPK";


          EDITMap params = new EDITMap("segmentPK", segmentPK);

          List results = SessionHelper.executeHQL(hql, params, AgentSnapshot.DATABASE);

          if (!results.isEmpty())
          {
              summedAmount = results.get(0);
          }

          if (summedAmount != null)
          {
              totalAdvanceRecovery = new EDITBigDecimal(summedAmount.toString());
          }

          return totalAdvanceRecovery;
      }
}
