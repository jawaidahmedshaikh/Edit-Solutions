/*
 * User: gfrosti
 * Date: Jul 5, 2006
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent;

import edit.common.EDITDate;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.*;


/**
 *
 * @author gfrosti
 */
public class PlacedAgentCommissionProfile extends HibernateEntity
{
    private CommissionProfile commissionProfile;
    
    private PlacedAgent placedAgent;

    private Long commissionProfileFK;
    private Long placedAgentFK;

    /**
     * The date that this PlacedAgentCommissionProfile becomes effective.
     */
    private EDITDate startDate;
    
    /**
     * The date that this PlacedAgentCommissionProfile is no longer effective. 
     * It defaults to the system max date if not specified.
     * @see EDITDate#DEFAULT_MAX_DATE
     */
    private EDITDate stopDate;
    
    private Long placedAgentCommissionProfilePK;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    /** Creates a new instance of PlacedAgentCommissionProfile */
    public PlacedAgentCommissionProfile()
    {
        setStopDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    }

    public CommissionProfile getCommissionProfile()
    {
        return commissionProfile;
    }

    public void setCommissionProfile(CommissionProfile commissionProfile)
    {
        this.commissionProfile = commissionProfile;
    }

    public Long getCommissionProfileFK()
    {
        return this.commissionProfileFK;
    }

    public void setCommissionProfileFK(Long commissionProfileFK)
    {
        this.commissionProfileFK = commissionProfileFK;
    }

    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }

    public void setPlacedAgent(PlacedAgent placedAgent)
    {
        this.placedAgent = placedAgent;
    }

    public Long getPlacedAgentFK()
    {
        return this.placedAgentFK;
    }

    public void setPlacedAgentFK(Long placedAgentFK)
    {
        this.placedAgentFK = placedAgentFK;
    }

    public EDITDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(EDITDate startDate)
    {
        this.startDate = startDate;
    }

    public EDITDate getStopDate()
    {
        return stopDate;
    }

    public void setStopDate(EDITDate stopDate)
    {
        this.stopDate = stopDate;
    }

    public String getDatabase()
    {
        return PlacedAgentCommissionProfile.DATABASE;
    }

    /**
     * Finds the PlacedAgentCommissionProfile associated with the specified PlacedAgent and:
     * StartDate <= editDate <= StopDate.
     * @return the PlacedAgentCommissionProfile satisfying the PlacedAgent/Date contraints 
     */
    public static PlacedAgentCommissionProfile findBy_PlacedAgent_Date(PlacedAgent placedAgent, EDITDate editDate)
    {
        PlacedAgentCommissionProfile placedAgentCommissionProfile = null;
        
        String hql = " from PlacedAgentCommissionProfile placedAgentCommissionProfile" +
                        " where placedAgentCommissionProfile.PlacedAgent = :placedAgent" +
                        " and placedAgentCommissionProfile.StartDate <= :editDate " +
                        " and placedAgentCommissionProfile.StopDate >= :editDate";
        
        Map params = new HashMap();
        
        params.put("placedAgent", placedAgent);
                
        params.put("editDate", editDate);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgentCommissionProfile.DATABASE);
        
        if (!results.isEmpty())
        {
            placedAgentCommissionProfile = (PlacedAgentCommissionProfile) results.get(0);
        }
        
        return placedAgentCommissionProfile;
    }

    public Long getPlacedAgentCommissionProfilePK()
    {
        return placedAgentCommissionProfilePK;
    }

    public void setPlacedAgentCommissionProfilePK(Long placedAgentCommissionProfilePK)
    {
        this.placedAgentCommissionProfilePK = placedAgentCommissionProfilePK;
    }

    /**
     * Finder.
     */
    public static PlacedAgentCommissionProfile findBy_PlacedAgent_CommissionProfile_StartStopDates(PlacedAgent placedAgent, CommissionProfile commissionProfile, EDITDate startDate, EDITDate stopDate)
    { 
        PlacedAgentCommissionProfile placedAgentCommissionProfile = null;
        
        String hql = " from PlacedAgentCommissionProfile placedAgentCommissionProfile" +
                     " where placedAgentCommissionProfile.CommissionProfile = :commissionProfile" +
                     " and placedAgentCommissionProfile.PlacedAgent = :placedAgent" +
                     " and placedAgentCommissionProfile.StartDate = :startDate" +
                     " and placedAgentCommissionProfile.StopDate = :stopDate";
        
        Map params = new HashMap();
        
        params.put("commissionProfile", commissionProfile);
        
        params.put("placedAgent", placedAgent);
        
        params.put("startDate", startDate);
        
        params.put("stopDate", stopDate);
        
        List results = SessionHelper.executeHQL(hql, params, PlacedAgentCommissionProfile.DATABASE);
        
        if (!results.isEmpty())
        {
            placedAgentCommissionProfile = (PlacedAgentCommissionProfile) results.get(0);
        }
        
        return placedAgentCommissionProfile;
    }
    
    /**
     * True if the specified date is within the range of the Start/Stop dates of this
     * PlacedAgentCommissionProfile. i.e.
     * StartDate <= date <= StopDate.
     * @param date Any valid EDITDate
     * @return true if the specified date is within the Start/Stop dates
     */
    public boolean isActive(EDITDate date)
    {
        boolean isActive = false;
        
        isActive = (getStartDate().before(date) || getStartDate().equals(date)) && (date.before(getStopDate()) || date.equals(getStopDate()));
        
        return isActive;
    }
}
