/*
 * User: unknown
 * Date: Jan 1, 2000
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

import fission.utility.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class AgentGroupAssociation extends HibernateEntity
{
    private Long agentGroupAssociationPK;

    private EDITDate startDate;

    private EDITDate stopDate;

    private String stopDateReasonCT;

    private AgentGroup agentGroup;

    private PlacedAgent placedAgent;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public AgentGroupAssociation()
    {
        setStopDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    }

    /**
     * @see HibernateEntity#hSave()
     */
    public void hSave()
    {
    }

    /**
     * Deletes this AgentGroupAssociation and its association(s) with all parent(s).
     * @see HibernateEntity#hDelete()
     */
    public void hDelete()
    {
//        getPlacedAgent().removeAgentGroupAssocation(this);

        getAgentGroup().removeAgentGroupAssociation(this);

        SessionHelper.delete(this, AgentGroupAssociation.DATABASE);
    }

    /**
     * Setter.
     * @param agentGroupAssociationPK
     */
    public void setAgentGroupAssociationPK(Long agentGroupAssociationPK)
    {
        this.agentGroupAssociationPK = agentGroupAssociationPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentGroupAssociationPK()
    {
        return agentGroupAssociationPK;
    }

    /**
     * Setter.
     * @param startDate
     */
    public void setStartDate(EDITDate startDate)
    {
        this.startDate = startDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getStartDate()
    {
        return startDate;
    }

    /**
     * Setter.
     * @param stopDate
     */
    public void setStopDate(EDITDate stopDate)
    {
        this.stopDate = stopDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getStopDate()
    {
        return stopDate;
    }

    /**
     * Setter.
     * @param stopDateReasonCT
     */
    public void setStopDateReasonCT(String stopDateReasonCT)
    {
        this.stopDateReasonCT = stopDateReasonCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getStopDateReasonCT()
    {
        return stopDateReasonCT;
    }

    /**
     * Getter for UI StartDate
     * @return
     */
    public String getUiStartDate()
    {
        String date = null;

        if (getStartDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(getStartDate());
        }

        return date;
    }

    /**
     * Setter for UT StartDate
     * @param uiStartDate
     */
    public void setUiStartDate(String uiStartDate)
    {
        setStartDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiStartDate));
    }


    /**
     * Getter for UI StopDate
     * @return
     */
    public String getUiStopDate()
    {
        String date = null;

        if (getStopDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(getStopDate());
        }

        return date;
    }

    /**
     * Setter for UI StopDate
     * @param uiStopDate
     */
    public void setUiStopDate(String uiStopDate)
    {
        setStopDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiStopDate));
    }

    /**
     * Setter.
     * @param agentGroup
     */
    public void setAgentGroup(AgentGroup agentGroup)
    {
        this.agentGroup = agentGroup;
    }

    /**
     * Getter.
     * @return
     */
    public AgentGroup getAgentGroup()
    {
        return agentGroup;
    }

    /**
     * Setter.
     * @param placedAgent
     */
    public void setPlacedAgent(PlacedAgent placedAgent)
    {
        this.placedAgent = placedAgent;
    }

    /**
     * Getter.
     * @return
     */
    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }

    /**
     * Finder.
     * @param agentGroupAssociationPK
     * @return
     */
    public static AgentGroupAssociation findBy_PK(Long agentGroupAssociationPK)
    {
        return (AgentGroupAssociation) SessionHelper.get(AgentGroupAssociation.class, agentGroupAssociationPK, AgentGroupAssociation.DATABASE);
    }


    /**
     * Checks to see if the specified date is:
     * AgentGroupAssociation.StartDate <= editDate <= AgentGroupAssociation.StopDate
     * @param editDate
     */
    public boolean isWithinStartStopDate(EDITDate editDate)
    {
        return (getStartDate().before(editDate) || getStartDate().equals(editDate)) && (getStopDate().after(editDate) || getStopDate().equals(editDate));
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AgentGroupAssociation.DATABASE;
    }

    /**
     * Finder.
     * @param agentGroup
     * @return
     */
    public static AgentGroupAssociation[] findBy_AgentGroup(AgentGroup agentGroup)
    {
        String hql = " from AgentGroupAssociation agentGroupAssociation" +
                    " where agentGroupAssociation.AgentGroup = :agentGroup";

        Map params = new HashMap();

        params.put("agentGroup", agentGroup);

        List results = SessionHelper.executeHQL(hql, params, AgentGroupAssociation.DATABASE);

        return (AgentGroupAssociation[]) results.toArray(new AgentGroupAssociation[results.size()]);
    }
}
