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


import java.util.List;
import java.util.Set;
import java.util.HashSet;


public class AgentGroup extends HibernateEntity
{
    private Long agentGroupPK;
    private String contractCodeCT;
    private EDITDate effectiveDate;
    private EDITDate terminationDate;
    private String agentGroupTypeCT;
    private Agent agent;
    private CommissionProfile commissionProfile;
    private Set agentGroupAssociations = new HashSet();
    private Set contributingProducts = new HashSet();
    private Long commissionProfileFK;
    private Long agentFK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public AgentGroup()
    {
        setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentFK()
    {
        return agentFK;
    }

    /**
     * Setter.
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        this.agentFK = agentFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCommissionProfileFK()
    {
        return commissionProfileFK;
    }

    /**
     * Setter.
     * @param commissionProfileFK
     */
    public void setCommissionProfileFK(Long commissionProfileFK)
    {
        this.commissionProfileFK = commissionProfileFK;
    }

    /**
     * Getter.
     * @return
     */
    public Set getContributingProducts()
    {
        return contributingProducts;
    }

    /**
     * Setter.
     * @param contributingProducts
     */
    public void setContributingProducts(Set contributingProducts)
    {
        this.contributingProducts = contributingProducts;
    }

    /**
     * @see HibernateEntity#hSave()
     */
    public void hSave()
    {
    }

    /**
     * @see HibernateEntity#hDelete()
     */
    public void hDelete()
    {
        // Remove from Agent
        Agent agent = Agent.findByPK(getAgentFK());
        agent.removeAgentGroup(this);

        // Remove from CommissionProfile
        CommissionProfile commissionProfile = CommissionProfile.findBy_PK(getCommissionProfileFK());
        commissionProfile.removeAgentGroup(this);

        // This will delete child entities by its hbm file.
        SessionHelper.delete(this, AgentGroup.DATABASE);
    }

    /**
     * Setter.
     * @param agentGroupPK
     */
    public void setAgentGroupPK(Long agentGroupPK)
    {
        this.agentGroupPK = agentGroupPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentGroupPK()
    {
        return agentGroupPK;
    }

    /**
     * Setter.
     * @param contractCodeCT
     */
    public void setContractCodeCT(String contractCodeCT)
    {
        this.contractCodeCT = contractCodeCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getContractCodeCT()
    {
        return contractCodeCT;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * Setter.
     * @param agentGroupTypeCT
     */
    public void setAgentGroupTypeCT(String agentGroupTypeCT)
    {
        this.agentGroupTypeCT = agentGroupTypeCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getAgentGroupTypeCT()
    {
        return agentGroupTypeCT;
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
     * Getter.
     * @return
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Setter.
     * @param commissionProfile
     */
    public void setCommissionProfile(CommissionProfile commissionProfile)
    {
        this.commissionProfile = commissionProfile;
    }

    /**
     * Getter.
     * @return
     */
    public CommissionProfile getCommissionProfile()
    {
        return commissionProfile;
    }

    /**
     * Setter.
     * @param agentGroupAssociations
     */
    public void setAgentGroupAssociations(Set agentGroupAssociations)
    {
        this.agentGroupAssociations = agentGroupAssociations;
    }

    /**
     * Getter.
     * @return
     */
    public Set getAgentGroupAssociations()
    {
        return agentGroupAssociations;
    }

    /**
     * Adder.
     * @param agentGroupAssociation
     */
    public void addAgentGroupAssociation(AgentGroupAssociation agentGroupAssociation)
    {
        getAgentGroupAssociations().add(agentGroupAssociation);

        agentGroupAssociation.setAgentGroup(this);
    }

    /**
     * Remover.
     * @param agentGroupAssociation
     */
    public void removeAgentGroupAssociation(AgentGroupAssociation agentGroupAssociation)
    {
        getAgentGroupAssociations().remove(agentGroupAssociation);

        agentGroupAssociation.setAgentGroup(null);

        SessionHelper.saveOrUpdate(this, AgentGroup.DATABASE);
    }

    /**
     * Finder. Results are sorted by Agent's name ascending.
     * @param state
     * @see SessionHelper.STATEFUL
     * @see SessionHelper.STATELESS
     * @return
     */
    public static AgentGroup[] findAll()
    {
        String hql = "from AgentGroup";

        List results = SessionHelper.executeHQL(hql, null, AgentGroup.DATABASE);

        return (AgentGroup[]) results.toArray(new AgentGroup[results.size()]);
    }

    /**
     * Finder.
     * @param agentGroupPK
     * @return
     */
    public static AgentGroup findBy_PK(Long agentGroupPK)
    {
        return (AgentGroup) SessionHelper.get(AgentGroup.class, agentGroupPK, AgentGroup.DATABASE);
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     * @return
     */
    public String getUiEffectiveDate()
    {
        String date = null;

        if (getEffectiveDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getEffectiveDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUiEffectiveDate(String uiEffectiveDate)
    {
        setEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiEffectiveDate));
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     * @return
     */
    public String getUiTerminationDate()
    {
        String date = null;

        if (getTerminationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getTerminationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUiTerminationDate(String uiTerminationDate)
    {
        setTerminationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiTerminationDate));
    }

    /**
     * Adder.
     * @param contributingProduct
     */
    public void add(ContributingProduct contributingProduct)
    {
        getContributingProducts().add(contributingProduct);

        contributingProduct.setAgentGroup(this);
    }

    /**
     * Checks to see if this AgentGroup has associations to Agent and CommissionProfile which are required assocations.
     * @return true if the Agent and CommissionProfile associations exist
     */
    public boolean hasAgentCommissionProfileAssociations()
    {
        return ((getAgent() != null) && (getCommissionProfile() != null));
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
        return AgentGroup.DATABASE;
    }
}
