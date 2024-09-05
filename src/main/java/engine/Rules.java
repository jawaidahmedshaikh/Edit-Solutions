/*
 * User: dlataill
 * Date: Jul 1, 2005
 * Time: 9:44:09 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.Set;

public class Rules extends HibernateEntity
{
    private Long rulesPK;
    private String ruleName;
    private String processName;
    private String eventName;
    private String eventTypeName;
    private String subRuleName;
    private EDITDate effectiveDate;
    private EDITDateTime maintDateTime;
    private String operator;
    private String description;

    private Set productStructures;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;


    /**
     * Setter.
     * @param fundPK
     */
    public void setRulesPK(Long rulesPK)
    {
        this.rulesPK = rulesPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getRulesPK()
    {
        return rulesPK;
    }

    /**
     * Setter.
     * @param ruleName
     */
    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }

    /**
     * Getter.
     * @return
     */
    public String getRuleName()
    {
        return ruleName;
    }

    /**
     * Setter.
     * @param processName
     */
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    /**
     * Getter.
     * @return
     */
    public String getProcessName()
    {
        return processName;
    }

    /**
     * Setter.
     * @param eventName
     */
    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    /**
     * Getter.
     * @return
     */
    public String getEventName()
    {
        return eventName;
    }

    /**
     * Setter.
     * @param eventTypeName
     */
    public void setEventTypeName(String eventTypeName)
    {
        this.eventTypeName = eventTypeName;
    }

    /**
     * Getter.
     * @return
     */
    public String getEventTypeName()
    {
        return eventTypeName;
    }

    /**
     * Setter.
     * @param subRuleName
     */
    public void setSubRuleName(String subRuleName)
    {
        this.subRuleName = subRuleName;
    }

    /**
     * Getter.
     * @return
     */
    public String getSubRuleName()
    {
        return subRuleName;
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
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Setter.
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Getter.
     * @return
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Getter
     * @return  set of productStructures
     */
    public Set getProductStructures()
    {
        return productStructures;
    }

    /**
     * Setter
     * @param productStructures      set of productStructures
     */
    public void setProductStructures(Set productStructures)
    {
        this.productStructures = productStructures;
    }

    /**
     * Adds a ProductStructure to the set of children
     * @param productStructure
     */
    public void addProductStructure(ProductStructure productStructure)
    {
        this.getProductStructures().add(productStructure);

        productStructure.addRule(this);

        SessionHelper.saveOrUpdate(productStructure, Rules.DATABASE);
    }

    /**
     * Removes a ProductStructure from the set of children
     * @param productStructure
     */
    public void removeProductStructure(ProductStructure productStructure)
    {
        this.getProductStructures().remove(productStructure);

        productStructure.removeRule(this);

        SessionHelper.saveOrUpdate(productStructure, Rules.DATABASE);
    }


    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Rules.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Rules.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Rules.DATABASE;
    }
}
