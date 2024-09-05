/*
 * User: dlataille
 * Date: June 5, 2006
 * Time: 11:05:40 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.hibernate.*;
import edit.services.db.CRUDEntity;

import contract.FilteredRequirement;
import contract.Requirement;

import java.util.Map;
import java.util.HashMap;
import java.util.List;


public class AgentRequirement extends HibernateEntity implements CRUDEntity
{
    private Agent agent;
    private FilteredRequirement filteredRequirement;

    private Long agentRequirementPK;
    private Long agentFK;
    private Long filteredRequirementFK;
    private String requirementStatusCT;
    private EDITDate effectiveDate;
    private EDITDate followupDate;
    private EDITDate receivedDate;

    public static final String REQUIREMENT_STATUS_CT_OUTSTANDING = "Outstanding";
    public static final String REQUIREMENT_STATUS_CT_RECEIVED = "Received";
    public static final String REQUIREMENT_STATUS_CT_WAIVED = "Waived";

    private AgentRequirementVO agentRequirementVO;

    private AgentRequirementImpl agentRequirementImpl;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public AgentRequirement()
    {
        agentRequirementVO = new AgentRequirementVO();
        agentRequirementImpl = new AgentRequirementImpl();
    }

    public AgentRequirement(AgentRequirementVO agentRequirementVO)
    {
        this();
        this.agentRequirementVO = agentRequirementVO;
    }

    /**
     * Constructor.
     * @param agent
     * @param filteredRequirement
     */
    public AgentRequirement(Agent agent, FilteredRequirement filteredRequirement)
    {
        this();

        agent.addAgentRequirement(this);

        filteredRequirement.addAgentRequirement(this);

        init();
    }

    /**
     * Initializaiton method.
     */
    private void init()
    {
        agentRequirementVO.setRequirementStatusCT(REQUIREMENT_STATUS_CT_OUTSTANDING);

        agentRequirementVO.setEffectiveDate(new EDITDate().getFormattedDate());

        calculateAndSetFollowupDate();
    }

    /**
     * FolloupDate Calculation.
     */
    public void calculateAndSetFollowupDate()
    {
        FilteredRequirement filteredRequirement = this.getFilteredRequirement();

        Requirement requirement = filteredRequirement.getRequirement();

        EDITDate effDate = getEffectiveDate().addDays(requirement.getFollowupDays());

        agentRequirementVO.setFollowupDate(effDate.getFormattedDate());
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentRequirementPK()
    {
        return SessionHelper.getPKValue(agentRequirementVO.getAgentRequirementPK());
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentFK()
    {
        return SessionHelper.getPKValue(agentRequirementVO.getAgentFK());
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredRequirementFK()
    {
        return SessionHelper.getPKValue(agentRequirementVO.getFilteredRequirementFK());
    }

    /**
     * Getter.
     * @return
     */
    public String getRequirementStatusCT()
    {
        return agentRequirementVO.getRequirementStatusCT();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(agentRequirementVO.getEffectiveDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFollowupDate()
    {
        return SessionHelper.getEDITDate(agentRequirementVO.getFollowupDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReceivedDate()
    {
        return SessionHelper.getEDITDate(agentRequirementVO.getReceivedDate());
    }

    /**
     * Setter.
     * @param agentRequirementPK
     */
    public void setAgentRequirementPK(Long agentRequirementPK)
    {
        agentRequirementVO.setAgentRequirementPK(SessionHelper.getPKValue(agentRequirementPK));
    }

    /**
     * Setter.
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        agentRequirementVO.setAgentFK(SessionHelper.getPKValue(agentFK));
    }

    /**
     * Setter.
     * @param filteredRequirementFK
     */
    public void setFilteredRequirementFK(Long filteredRequirementFK)
    {
        agentRequirementVO.setFilteredRequirementFK(SessionHelper.getPKValue(filteredRequirementFK));
    }

    /**
     * Setter.
     * @param requirementStatusCT
     */
    public void setRequirementStatusCT(String requirementStatusCT)
    {
        agentRequirementVO.setRequirementStatusCT(requirementStatusCT);
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        agentRequirementVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }

    /**
     * Setter.
     * @param followupDate
     */
    public void setFollowupDate(EDITDate followupDate)
    {
        agentRequirementVO.setFollowupDate(SessionHelper.getEDITDate(followupDate));
    }

    /**
     * Setter.
     * @param receivedDate
     */
    public void setReceivedDate(EDITDate receivedDate)
    {
        agentRequirementVO.setReceivedDate(SessionHelper.getEDITDate(receivedDate));
    }

    public void save()
    {
        this.agentRequirementImpl.save(this);
    }

    public void delete() throws Exception
    {
        this.agentRequirementImpl.delete(this);
    }

    public long getPK()
    {
        return agentRequirementVO.getAgentRequirementPK();
    }

    /**
     * Getter/Hibernate
     * @return
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Setter.
     * @param agent
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    /**
     * Getter/Hibernate
     * @return
     */
    public FilteredRequirement getFilteredRequirement()
    {
        return filteredRequirement;
    }

    /**
     * Setter.
     * @param filteredRequirement
     */
    public void setFilteredRequirement(FilteredRequirement filteredRequirement)
    {
        this.filteredRequirement = filteredRequirement;
    }

    /**
     * Verifies the Requirement Status and Updates Received Date accordingly.
     * The first time when the Status is changed to 'Received' the ReceivedDate should be set to current Date
     * if one is not entered.
     */
    public void checkRequirementStatusAndUpdateReceivedDate()
    {
        // Need to evict the existing object from hibernated cache to get the previous state of object from database.
        if (agentRequirementPK != null)
        {
            if (REQUIREMENT_STATUS_CT_RECEIVED.equals(this.getRequirementStatusCT()))
            {
                if (this.getReceivedDate() == null)
                {
                    this.setReceivedDate(new EDITDate());
                }
            }
        }
    }

    /**
     * Finder by PK.
     * @param agentRequirementPK
     * @return
     */
    public static final AgentRequirement findByPK(Long agentRequirementPK)
    {
        return (AgentRequirement) SessionHelper.get(AgentRequirement.class, agentRequirementPK, AgentRequirement.DATABASE);
    }

    public static final AgentRequirement[] findByAgent(Agent agent)
    {
        AgentRequirement[] agentRequirements = null;

        String hql = "select ar from AgentRequirement ar where ar.Agent = :agent";

        Map params = new HashMap();

        params.put("agent", agent);

        List results = SessionHelper.executeHQL(hql, params, AgentRequirement.DATABASE);

        if (!results.isEmpty())
        {
            agentRequirements = (AgentRequirement[]) results.toArray(new AgentRequirement[results.size()]);
        }

        return agentRequirements;
    }

    /**
     * Finder.
     * @param agent
     * @param filteredRequirement
     * @return
     */
    public static final AgentRequirement findBy_Agent_And_FilteredRequirement(Agent agent, FilteredRequirement filteredRequirement)
    {
        AgentRequirement agentRequirement = null;

        String hql = "select ar from AgentRequirement ar where ar.Agent = :agent" +
                     " and ar.FilteredRequirement = :filteredRequirement";

        Map params = new HashMap();

        params.put("agent", agent);

        params.put("filteredRequirement", filteredRequirement);

        List results = SessionHelper.executeHQL(hql, params, AgentRequirement.DATABASE);

        if (!results.isEmpty())
        {
            agentRequirement = (AgentRequirement) results.get(0);
        }

        return agentRequirement;
    }

    public VOObject getVO()
    {
        return agentRequirementVO;
    }

    public void setVO(VOObject voObject)
    {
        this.agentRequirementVO = (AgentRequirementVO) voObject;
    }

    public boolean isNew()
    {
        return this.agentRequirementImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.agentRequirementImpl.cloneCRUDEntity(this);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AgentRequirement.DATABASE;
    }
}
