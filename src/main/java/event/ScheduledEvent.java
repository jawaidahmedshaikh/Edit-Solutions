/*
 * User: gfrosti
 * Date: Jun 21, 2005
 * Time: 1:32:27 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;

import java.util.*;


import event.dm.dao.*;
import event.dm.dao.*;


public class ScheduledEvent extends HibernateEntity
{
    public static final String FREQUENCYCT_ANNUAL = "Annual";
    
    private ScheduledEventVO scheduledEventVO;

    private GroupSetup groupSetup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public ScheduledEvent()
    {
        this.scheduledEventVO = new ScheduledEventVO();
    }
    
    public ScheduledEvent(ScheduledEventVO scheduledEventVO) 
    {
        this.scheduledEventVO = scheduledEventVO;        
    }

    /**
     * Getter,
     * @return
     */
    public GroupSetup getGroupSetup()
    {
        return groupSetup;
    }

    /**
     * Setter.
     * @param groupSetup
     */
    public void setGroupSetup(GroupSetup groupSetup)
    {
        this.groupSetup = groupSetup;
    }

    /**
     * Getter.
     * @return
     */
    public Long getScheduledEventPK()
    {
        return SessionHelper.getPKValue(this.scheduledEventVO.getScheduledEventPK());
    }

    /**
     * Setter.
      * @param scheduledEventPK
     */
    public void setScheduledEventPK(Long scheduledEventPK)
    {
        this.scheduledEventVO.setScheduledEventPK(SessionHelper.getPKValue(scheduledEventPK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getGroupSetupFK()
    {
        return SessionHelper.getPKValue(this.scheduledEventVO.getGroupSetupFK());
    }

    /**
     * Setter.
      * @param groupSetupFK
     */
    public void setGroupSetupFK(Long groupSetupFK)
    {
        this.scheduledEventVO.setGroupSetupFK(SessionHelper.getPKValue(groupSetupFK));
    }

    /**
    * Getter.
    * @return
    */
    public EDITDate getStartDate()
    {
        return SessionHelper.getEDITDate(this.scheduledEventVO.getStartDate());
    }

    /**
    * Setter.
    * @param startDate
    */
    public void setStartDate(EDITDate startDate)
    {
        this.scheduledEventVO.setStartDate(SessionHelper.getEDITDate(startDate));
    }

    /**
    * Getter.
    * @return
    */
    public EDITDate getStopDate()
    {
        return SessionHelper.getEDITDate(this.scheduledEventVO.getStopDate());
    }

    /**
    * Setter.
    * @param stopDate
    */
    public void setStopDate(EDITDate stopDate)
    {
        this.scheduledEventVO.setStopDate(SessionHelper.getEDITDate(stopDate));
    }

    /**
    * Getter.
    * @return
    */
    public String getLastDayOfMonthInd()
    {
        return this.scheduledEventVO.getLastDayOfMonthInd();
    }

    /**
    * Setter.
    * @param lastDayOfMonthInd
    */
    public void setLastDayOfMonthInd(String lastDayOfMonthInd)
    {
        this.scheduledEventVO.setLastDayOfMonthInd(lastDayOfMonthInd);
    }

    /**
    * Getter.
    * @return
    */
    public String getFrequencyCT()
    {
        return this.scheduledEventVO.getFrequencyCT();
    }

    /**
    * Setter.
    * @param frequencyCT
    */
    public void setFrequencyCT(String frequencyCT)
    {
        this.scheduledEventVO.setFrequencyCT(frequencyCT);
    }

    /**
    * Getter.
    * @return
    */
    public String getLifeContingentCT()
    {
        return this.scheduledEventVO.getLifeContingentCT();
    }

    /**
    * Setter.
    * @param lifeContingentCT
    */
    public void setLifeContingentCT(String lifeContingentCT)
    {
        this.scheduledEventVO.setLifeContingentCT(lifeContingentCT);
    }

    /**
    * Getter.
    * @return
    */
    public String getCostOfLivingInd()
    {
        return this.scheduledEventVO.getCostOfLivingInd();
    }

    /**
    * Setter.
    * @param costOfLivingInd
    */
    public void setCostOfLivingInd(String costOfLivingInd)
    {
        this.scheduledEventVO.setCostOfLivingInd(costOfLivingInd);
    }
    
    /**
     * Returns the VO Object.
     * @return
     */
    public ScheduledEventVO getVO()
    {
        return this.scheduledEventVO;
    }
    
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ScheduledEvent)) return false;

        final ScheduledEvent event = (ScheduledEvent) o;

        if (!getScheduledEventPK().equals(event.getScheduledEventPK())) return false;

        return true;
    }

    public int hashCode()
    {
        int hashCode = 0;

        if (getScheduledEventPK() != null)
        {
            hashCode = getScheduledEventPK().hashCode();
        }
        
        return hashCode;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ScheduledEvent.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, ScheduledEvent.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ScheduledEvent.DATABASE;
    }

    /**
     * Originally in ScheduledEventDAO.findByEDITTrxPK
     * @param editTrxPK
     * @return
     */
    public static ScheduledEvent[] findBy_EDITTrxPK(Long editTrxPK)
    {
        String hql = "select scheduledEvent from ScheduledEvent scheduledEvent " +
                     " join scheduledEvent.GroupSetup groupSetup" +
                     " join fetch groupSetup.ContractSetup contractSetup" +
                     " join fetch contractSetup.ClientSetup clientSetup" +
                     " join fetch clientSetup.EDITTrx editTrx" +
                     " where editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();
        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, ScheduledEvent.DATABASE);

        return (ScheduledEvent[]) results.toArray(new ScheduledEvent[results.size()]);
    }
    
    public static ScheduledEvent findByEditTrx(EDITTrx editTrx)
    {
        ScheduledEvent scheduledEvent = null;

        String hql = "select se from ScheduledEvent se join se.GroupSetup gs join gs.ContractSetups cs" +
                     " join cs.ClientSetups cls join cls.EDITTrxs et where et = :editTrx";

        Map params = new HashMap();

        params.put("editTrx", editTrx);

        List results = SessionHelper.executeHQL(hql, params, ScheduledEvent.DATABASE);

        if (!results.isEmpty())
        {
            scheduledEvent = (ScheduledEvent) results.get(0);
        }

        return scheduledEvent;
    }

    public static ScheduledEventVO findByEditTrx_UsingCRUD(long editTrxPK)
    {
        ScheduledEventVO scheduledEventVO = null;

        ScheduledEventVO[] scheduledEventVOs = new ScheduledEventDAO().findByEDITTrxPK(editTrxPK);

        if (scheduledEventVOs != null)
        {
            scheduledEventVO = scheduledEventVOs[0];
        }

        return scheduledEventVO;
    }
}
