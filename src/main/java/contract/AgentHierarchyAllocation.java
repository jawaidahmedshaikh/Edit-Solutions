/*
 * User: sdorman
 * Date: Nov 15, 2006
 * Time: 11:18:38 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.*;
import edit.common.vo.*;

import edit.services.db.hibernate.*;
import edit.services.db.*;
import org.dom4j.*;

import java.util.*;

import org.hibernate.Session;


/**
 * Contains the historic and current information of the percentage of commissions going to an agent for the its contract.
 * There is only one current (active) AgentHierarchyAllocation for an AgentHierarchy.  The rest are "expired".  The
 * start and stop dates define the date range that the allocation percentage was active.
 *
 * NOTE:  Even though this was a new table/class, it couldn't be written only as a HibernateEntity.  That is because
 * the front end uses cloud land to save the VO objects recursively (using UIAgentHierarchyVO).  It was too difficult
 * to try to get Hibernate support in cloud land.  The easier route was to make an AgentHierarchyAllocationVO and allow
 * it to be saved via CRUD.
 */
public class AgentHierarchyAllocation extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityI crudEntityImpl;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    private Long agentHierarchyAllocationPK;

    private AgentHierarchy agentHierarchy;
    private AgentHierarchyAllocationVO agentHierarchyAllocationVO;

    private Long agentHierarchyFK;

    private EDITDate startDate;
    private EDITDate stopDate;
    private EDITBigDecimal allocationPercent;





    public AgentHierarchyAllocation()
    {
        init();
    }

    public AgentHierarchyAllocation(EDITBigDecimal allocationPercent, EDITDate startDate, EDITDate stopDate)
    {
        init();

        this.agentHierarchyAllocationVO = new AgentHierarchyAllocationVO();

        this.setAllocationPercent(allocationPercent);
        this.setStartDate(startDate);
        this.setStopDate(stopDate);
    }

    /**
     * Instantiates a AgentHierarchyAllocation entity with a AgentHierarchyAllocationVO retrieved from persistence.
     * @param agentHierarchyPK
     */
    public AgentHierarchyAllocation(long agentHierarchyAllocationPK)
    {
        init();

        crudEntityImpl.load(this, agentHierarchyAllocationPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a AgentHierarchyAllocation entity with a supplied AgentHierarchyAllocationVO.
     * @param agentHierarchyAllocationVO
     */
    public AgentHierarchyAllocation(AgentHierarchyAllocationVO agentHierarchyAllocationVO)
    {
        init();

        this.agentHierarchyAllocationVO = agentHierarchyAllocationVO;
    }

    public void init()
    {
        if (agentHierarchyAllocationVO == null)
        {
            agentHierarchyAllocationVO = new AgentHierarchyAllocationVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    public Long getAgentHierarchyAllocationPK()
    {
        this.agentHierarchyAllocationPK = SessionHelper.getPKValue(agentHierarchyAllocationVO.getAgentHierarchyAllocationPK());

        return this.agentHierarchyAllocationPK;
    }

    public void setAgentHierarchyAllocationPK(Long agentHierarchyAllocationPK)
    {
        this.agentHierarchyAllocationPK = agentHierarchyAllocationPK;

        agentHierarchyAllocationVO.setAgentHierarchyAllocationPK(SessionHelper.getPKValue(agentHierarchyAllocationPK));
    }

    public Long getAgentHierarchyFK()
    {
        this.agentHierarchyFK = SessionHelper.getPKValue(agentHierarchyAllocationVO.getAgentHierarchyFK());

        return this.agentHierarchyFK;
    }

    public void setAgentHierarchyFK(Long agentHierarchyFK)
    {
        this.agentHierarchyFK = agentHierarchyFK;

        agentHierarchyAllocationVO.setAgentHierarchyFK(SessionHelper.getPKValue(agentHierarchyFK));
    }

    public EDITDate getStartDate()
    {
        this.startDate = SessionHelper.getEDITDate(agentHierarchyAllocationVO.getStartDate());

        return startDate;
    }

    public void setStartDate(EDITDate startDate)
    {
        this.startDate = startDate;

        agentHierarchyAllocationVO.setStartDate(SessionHelper.getEDITDate(startDate));
    }

    public EDITDate getStopDate()
    {
        this.stopDate = SessionHelper.getEDITDate(agentHierarchyAllocationVO.getStopDate());

        return this.stopDate;
    }

    public void setStopDate(EDITDate stopDate)
    {
        this.stopDate = stopDate;

        agentHierarchyAllocationVO.setStopDate(SessionHelper.getEDITDate(stopDate));
    }

    public EDITBigDecimal getAllocationPercent()
    {
        this.allocationPercent = SessionHelper.getEDITBigDecimal(agentHierarchyAllocationVO.getAllocationPercent());

        return this.allocationPercent;
    }

    public void setAllocationPercent(EDITBigDecimal allocationPercent)
    {
        this.allocationPercent = allocationPercent;

        agentHierarchyAllocationVO.setAllocationPercent(SessionHelper.getEDITBigDecimal(allocationPercent));
    }

    public AgentHierarchy getAgentHierarchy()
    {
        return this.agentHierarchy;
    }

    public void setAgentHierarchy(AgentHierarchy agentHierarchy)
    {
        this.agentHierarchy = agentHierarchy;
    }

    /**
     * Get the AgentHierarchy via CRUD (VOs), not Hibernate
     * @return
     */
    public AgentHierarchy get_AgentHierarchyViaVOs()
    {
        if (agentHierarchy == null)
        {
            agentHierarchy = new AgentHierarchy(this.agentHierarchyAllocationVO.getAgentHierarchyFK()); // CRUD
        }

        return agentHierarchy;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, AgentHierarchyAllocation.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, AgentHierarchyAllocation.DATABASE);
    }


    public String getDatabase()
    {
        return AgentHierarchyAllocation.DATABASE;
    }

    /**
     * Determines if this AgentHierarchyAllocation is active or not.  If the currentDate date is between (or equal to) the
     * start and stop dates, it is active, otherwise it is not.
     * @return
     */
    public boolean isActive()
    {     
    	EDITDate currentDate = new EDITDate();

        if ((currentDate.equals(this.getStartDate()) || currentDate.after(this.getStartDate())) &&
             (currentDate.equals(this.getStopDate())  || currentDate.before(this.getStopDate())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Determines if this AgentHierarchyAllocation is active or not.  If the effectiveDate date is between (or equal to) the
     * start and stop dates, it is active, otherwise it is not.
     * @param effectiveDate
     * 
     * @return
     */
    public boolean isActive(EDITDate effectiveDate)
    {     
    	EDITDate currentDate = new EDITDate();
                
        if (effectiveDate==null){
        	effectiveDate = currentDate;
        }

        if ((effectiveDate.equals(this.getStartDate()) || effectiveDate.after(this.getStartDate())) &&
             (effectiveDate.equals(this.getStopDate())  || effectiveDate.before(this.getStopDate())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Inactivates the allocation by changing the stopDate to 1 day less than the start date of the supplied active allocation.
     */
    public void inactivate(AgentHierarchyAllocation newlyActivatedAgentHierarchyAllocation)
    {
        this.setStopDate(newlyActivatedAgentHierarchyAllocation.getStartDate().subtractDays(1));
    }

    /**
     * Inactivates the allocation by changing the stopDate to 1 day less than the supplied date.  This is used many
     * times by the front end which wants the stopDate to be 1 day less than the startDate.  Although it seems odd to
     * pass a date to the method which is not used directly (i.e. 1 day is subtracted from it), we wanted to capture the
     * business logic of making inactive allocations end 1 day before the start of the active allocation.  We wanted
     * that capture to be in the entity object, not the front end.
     */
    public void inactivate(EDITDate suppliedDate)
    {
        this.setStopDate(suppliedDate.subtractDays(1));
    }

    public static AgentHierarchyAllocation findByPK(Long agentHierarchyAllocationPK)
    {
        return (AgentHierarchyAllocation) SessionHelper.get(AgentHierarchyAllocation.class, agentHierarchyAllocationPK, AgentHierarchyAllocation.DATABASE);
    }

    /**
     * Finds all AgentHierarchyAllocations for a given AgentHierarchyFK
     * @param agentHierarchyFK
     * @return
     */
    public static AgentHierarchyAllocation[] findByAgentHierarchyFK(Long agentHierarchyFK)
    {
        String hql = " select agentHierarchyAllocation from AgentHierarchyAllocation agentHierarchyAllocation " +
                     " where agentHierarchyAllocation.AgentHierarchyFK = :agentHierarchyFK";

        Map params = new HashMap();

        params.put("agentHierarchyFK", agentHierarchyFK);

        List results =  SessionHelper.executeHQL(hql, params, AgentHierarchyAllocation.DATABASE);

        return (AgentHierarchyAllocation[]) results.toArray(new AgentHierarchyAllocation[results.size()]);
    }

    /**
     * Finds the active AgentHierarchyAllocation for a given AgentHierarchyFK and a comparison date.  The date is used
     * to determine if it is active or not.  If the specified comparison date is between (or equal to) the start and
     * stop dates, it is active.
     *
     * @param agentHierarchyFK
     * @param comparisonDate
     * @return
     */
    public static AgentHierarchyAllocation findActiveByAgentHierarchyFK_Date(Long agentHierarchyFK, EDITDate comparisonDate)
    {
        AgentHierarchyAllocation agentHierarchyAllocation = null;

        String hql = " select agentHierarchyAllocation from AgentHierarchyAllocation agentHierarchyAllocation " +
                     " where agentHierarchyAllocation.AgentHierarchyFK = :agentHierarchyFK" +
                     " and agentHierarchyAllocation.StartDate <= :comparisonDate" +
                     " and agentHierarchyAllocation.StopDate >= :comparisonDate";

        Map params = new HashMap();

        params.put("agentHierarchyFK", agentHierarchyFK);
        params.put("comparisonDate", comparisonDate);

        List results =  SessionHelper.executeHQL(hql, params, AgentHierarchyAllocation.DATABASE);

        if (!results.isEmpty())
        {
            agentHierarchyAllocation = (AgentHierarchyAllocation) results.get(0);
        }

        return agentHierarchyAllocation;
    }
    
  /**
   * Finds the active AgentHierarchyAllocation (using a separate Hibernate Session) (be careful, there is also the "normal" version of this method)
   * for a given AgentHierarchyFK and a comparison date.  The date is used
   * to determine if it is active or not.  If the specified comparison date is between (or equal to) the start and
   * stop dates, it is active.
   *
   * @param agentHierarchyFK
   * @param comparisonDate
   * @return
   */
  public static AgentHierarchyAllocation findSeparateActiveByAgentHierarchyFK_Date(Long agentHierarchyFK, EDITDate comparisonDate)
  {
      AgentHierarchyAllocation agentHierarchyAllocation = null;

      String hql = " select agentHierarchyAllocation from AgentHierarchyAllocation agentHierarchyAllocation " +
                   " where agentHierarchyAllocation.AgentHierarchyFK = :agentHierarchyFK" +
                   " and agentHierarchyAllocation.StartDate <= :comparisonDate" +
                   " and agentHierarchyAllocation.StopDate >= :comparisonDate";

      Map params = new HashMap();

      params.put("agentHierarchyFK", agentHierarchyFK);
      params.put("comparisonDate", comparisonDate);
      
      Session session = null;
      
      try
      {
        session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
        
        List results =  SessionHelper.executeHQL(session, hql, params, 0);

        if (!results.isEmpty())
        {
            agentHierarchyAllocation = (AgentHierarchyAllocation) results.get(0);
        }        
      }
      finally
      {
        if (session != null) session.close();
      }

      return agentHierarchyAllocation;
  }    

    /**
     * Finds the active AgentHierarchyAllocation for a given SegmentFK and a comparison date.  The date is used
     * to determine if it is active or not.  If the specified comparison date is between (or equal to) the start and
     * stop dates, it is active.
     *
     * @param segmentFK
     * @param comparisonDate
     * @return
     */
    public static AgentHierarchyAllocation[] findAllActiveBySegmentFK_Date(Long segmentFK, EDITDate comparisonDate)
    {
        String hql = " select agentHierarchyAllocation from AgentHierarchyAllocation agentHierarchyAllocation " +
                     " join agentHierarchyAllocation.AgentHierarchy agentHierarchy" +
                     " where agentHierarchy.SegmentFK = :segmentFK" +
                     " and agentHierarchyAllocation.StartDate <= :comparisonDate" +
                     " and agentHierarchyAllocation.StopDate >= :comparisonDate";

        Map params = new HashMap();

        params.put("segmentFK", segmentFK);
        params.put("comparisonDate", comparisonDate);

        List results =  SessionHelper.executeHQL(hql, params, AgentHierarchyAllocation.DATABASE);

        return (AgentHierarchyAllocation[]) results.toArray(new AgentHierarchyAllocation[results.size()]);
    }

    /**
     * Finds all of the AgentHierarchyAllocations for a given contractNumber.
     *
     * @param contractNumber
     * @return
     */
    public static AgentHierarchyAllocation[] findAllByContractNumber(String contractNumber)
    {
        String hql = " select agentHierarchyAllocation from AgentHierarchyAllocation agentHierarchyAllocation, " +
                     "  AgentHierarchy agentHierarchy," +
                     "  Segment segment" +        
                     " where agentHierarchyAllocation.AgentHierarchyFK=agentHierarchy.AgentHierarchyPK" +
                     " and agentHierarchy.SegmentFK=segment.SegmentPK" +
                     " and segment.ContractNumber = :contractNumber";

        Map params = new HashMap();

        params.put("contractNumber", contractNumber);

        List results =  SessionHelper.executeHQL(hql, params, AgentHierarchyAllocation.DATABASE);

        return (AgentHierarchyAllocation[]) results.toArray(new AgentHierarchyAllocation[results.size()]);
    }
    
    /**
     * Finds all of the AgentHierarchyAllocations for a given SegmentFK.
     *
     * @param segmentFK
     * @return
     */
    public static AgentHierarchyAllocation[] findAllBySegmentFK(Long segmentFK)
    {
        String hql = " select agentHierarchyAllocation from AgentHierarchyAllocation agentHierarchyAllocation " +
                     " join agentHierarchyAllocation.AgentHierarchy agentHierarchy" +
                     " where agentHierarchy.SegmentFK = :segmentFK";

        Map params = new HashMap();

        params.put("segmentFK", segmentFK);

        List results =  SessionHelper.executeHQL(hql, params, AgentHierarchyAllocation.DATABASE);

        return (AgentHierarchyAllocation[]) results.toArray(new AgentHierarchyAllocation[results.size()]);
    }

    /////////////////////////  CRUD SPECIFIC METHODS ///////////////////////////////////////
    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
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
        return agentHierarchyAllocationVO;
    }

    /**
     * Returns the VO object with the proper object type
     * @return
     */
    public AgentHierarchyAllocationVO getAsVO()
    {
        return (AgentHierarchyAllocationVO) getVO();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return agentHierarchyAllocationVO.getAgentHierarchyAllocationPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.agentHierarchyAllocationVO = (AgentHierarchyAllocationVO) voObject;
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
}
