/*
 * User: gfrosti
 * Date: Dec 29, 2004
 * Time: 11:41:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.EDITDate;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.ProductStructure;

import fission.utility.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class ContributingProduct extends HibernateEntity
{
    private Long contributingProductPK;
    private Long productStructureFK;
    private Long agentGroupFK;
    private EDITDate startDate;
    private EDITDate stopDate;
    private AgentGroup agentGroup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public ContributingProduct()
    {
        stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }

    public ContributingProduct(EDITDate startDate, EDITDate stopDate)
    {
        this();

        this.startDate = startDate;

        this.stopDate = stopDate;
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
    public Long getContributingProductPK()
    {
        return contributingProductPK;
    }

    /**
     * Setter.
     * @param contributingProductPK
     */
    public void setContributingProductPK(Long contributingProductPK)
    {
        this.contributingProductPK = contributingProductPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getProductStructureFK()
    {
        return productStructureFK;
    }

    /**
     * Getter. Since ProductStructure is in a differenet database, this is an impromptu method.
     * @return
     */
    public ProductStructure getProductStructure()
    {
        return ProductStructure.findByPK(getProductStructureFK());
    }

    /**
     * Setter.
     * @param productStructureFK
     */
    public void setProductStructureFK(Long productStructureFK)
    {
        this.productStructureFK = productStructureFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentGroupFK()
    {
        return agentGroupFK;
    }

    /**
     * Setter.
     * @param agentGroupFK
     */
    public void setAgentGroupFK(Long agentGroupFK)
    {
        this.agentGroupFK = agentGroupFK;
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
    public EDITDate getStopDate()
    {
        return stopDate;
    }

    /**
     * Setter.
     * @param stopDate
     */
    public void setStopDate(EDITDate stopDate)
    {
        this.stopDate = stopDate;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContributingProduct.DATABASE);
    }

    public void hDelete()
    {
        // Remove from AgentGroup
        getAgentGroup().getContributingProducts().remove(this);

        // Manually done since ProductStructure is in a different DB.
        setProductStructureFK(null);

        SessionHelper.delete(this, ContributingProduct.DATABASE);
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     * @return
     */
    public String getUIStartDate()
    {
        String date = null;

        if (getStartDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getStartDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIStartDate(String uiStartDate)
    {
        setStartDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiStartDate));
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     * @return
     */
    public String getUIStopDate()
    {
        String date = null;

        if (getStopDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getStopDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIStopDate(String uiStopDate)
    {
        setStopDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiStopDate));
    }

    /**
     * Finder.
     * @param contributingProductPK
     * @return
     */
    public static ContributingProduct findBy_PK(Long contributingProductPK)
    {
        return (ContributingProduct) SessionHelper.get(ContributingProduct.class, contributingProductPK, ContributingProduct.DATABASE);
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
        return ContributingProduct.DATABASE;
    }

    /**
     * Finder.
     * @param agentGroup
     * @return
     */
    public static ContributingProduct[] findBy_AgentGroup(AgentGroup agentGroup)
    {
        String hql = " from ContributingProduct contributingProduct" +
                    " where contributingProduct.AgentGroup = :agentGroup";

        Map params = new HashMap();

        params.put("agentGroup", agentGroup);

        List results = SessionHelper.executeHQL(hql, params, ContributingProduct.DATABASE);

        return (ContributingProduct[]) results.toArray(new ContributingProduct[results.size()]);
    }
}
