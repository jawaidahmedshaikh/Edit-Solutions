package edit.services.db.hibernate;

import contract.AgentHierarchyAllocation;

import edit.common.EDITDate;
import edit.common.EDITDateTime;

import edit.common.vo.NonFinancialEntity;

import edit.services.config.ServicesConfig;

import fission.utility.Util;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Element;

import org.dom4j.tree.DefaultElement;

import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.tuple.entity.EntityMetamodel;

/**
 * Captures the entire list of field-level differences between the
 * in-session HibernateEntity and its DB counterpart.
 */
public class HibernateEntityDifference
{
    /**
     * A named parameter to be found in ThreadLocal to be used as the
     * ChangeHistoryDate for Hibernate Non Financial Entities flagged
     * to use a user-supplied date as opposed to the System date.
     */
    public static final String CHANGE_EFFECTIVE_DATE = "CHANGEEFFECTIVEDATE";
    
    /**
     * ChangeHistory tracks the current operator at all time.
     */
    public static final String OPERATOR = "OPERATOR";

    /**
     * The name of the HibernateEntity with before/after field-level differences.
     */
    private String entityName;

    /**
     * The PK of the HibernateEntity with before/after field-Level differences.
     */
    private Long entityPK;

    /**
     * The operator responsible for generating this HibernateEntityDifference.
     * 
     */
    private String operator;

    /**
     * The date/time that this HibernateEntityDifference was generated.
     */
    private EDITDateTime nonFinancialDateTime;

    /**
     * The list of all discovered field-level differences found with the before/after entities.
     */
    private HibernateFieldDifference[] hibernateFieldDifferences;

    /**
     * True if the ChangeHistory.EffectiveDate is NOT driven by the System date, but
     * by a data supplied via ThreadLocal. This 'System' vs 'Parameter' for the
     * EffectiveDate is configured for each Non Financial entity.
     * @return
     */
    private Boolean isParameterDrivenEffectiveDate;
    
    /**
     * The field values of the represented entity. The field names[index] matches the fieldValues[index].
     */
    private Object[] fieldValues;
    
    /**
     * The field names of the represented entity. The field names[index] matches the fieldValues[index].
     */
    private Object[] fieldNames;

    /**
     * Constructor.
     * @param entityName
     * @param fieldNames the field names of the entity's new state
     * @param fieldValues the field values of the entity's new state
     * @param hibernateFieldDifferences
     */
    public HibernateEntityDifference(String entityName, Long entityPK, Object[] fieldNames, Object[] fieldValues, HibernateFieldDifference[] hibernateFieldDifferences)
    {
        this.entityName = entityName;

        this.entityPK = entityPK;
        
        this.fieldNames = fieldNames;
        
        this.fieldValues = fieldValues;

        this.hibernateFieldDifferences = hibernateFieldDifferences;

        setNonFinancialDateTime(new EDITDateTime(getEffectiveDate().getTimeInMilliseconds()));
    }

    /**
     * @see #entityName
     * @return
     */
    public String getEntityName()
    {
        return entityName;
    }

    /**
     * @see #entityPK
     * @return
     */
    public Long getEntityPK()
    {
        return entityPK;
    }

    /**
     * If there is a future-dated ChangeEffectiveDate for this
     * entity, then we should abort the current transaction (blindly), 
     * and commit the future-dated changes of this HibernateEntityDifference
     * to the ChangeHistory.
     * @return
     */
    public boolean shouldAbortOnChangeEffectiveDate()
    {
        boolean shouldAbortOnChangeEffectiveDate = false;
        
        EDITDate changeEffectiveDate = getChangeEffectiveDate();
        
        if (changeEffectiveDate != null)
        {
            EDITDate systemDate = new EDITDate();
            
            if (changeEffectiveDate.after(systemDate))
            {
                shouldAbortOnChangeEffectiveDate = true;
            }
        }
        
        return shouldAbortOnChangeEffectiveDate;
    }

    /**
     * @see #nonFinancialDateTime
     * @param nonFinancialDateTime
     */
    private final void setNonFinancialDateTime(EDITDateTime nonFinancialDateTime)
    {
        this.nonFinancialDateTime = nonFinancialDateTime;
    }

    /**
     * @see #nonFinancialDateTime 
     * @return
     */
    public EDITDateTime getNonFinancialDateTime()
    {
        return nonFinancialDateTime;
    }

    /**
     * @see #hibernateFieldDifferences
     * @return
     */
    public HibernateFieldDifference[] getHibernateEntityFieldDifferences()
    {
        return hibernateFieldDifferences;
    }

    /**
     * Returns the DOM4J Element representation of this HibernateEntityDifference
     * in the following format using SegmentVO as an example:
     * 
     * <EntityDifference>
     *      <EntityName>SegmentVO</EntityName>
     *      <EntityPK>123456789</EntityPK>
     *      <Operator>FooOperator</Operator>
     *      <NonFinancialDateTime>6/25/2007 8:59:18 AM</NonFinancialDateTime>
     *      <FieldDifference> // Repeats for every field difference found in the entity that has just changed
     *          <FieldName>SegmentStatusCT</FieldName>
     *          <OldValue>Pending</OldValue>
     *          <NewValue>Active</NewValue>
     *      </FieldDifference>
     * </EntityDifference>
     * @return
     */
    public Element getAsElement()
    {
        // Entity Difference
        Element entityDifferenceElement = new DefaultElement("EntityDifference");

        // Entity Name(VO)
        Element entityNameElement = new DefaultElement("EntityName");

        entityNameElement.setText(Util.getClassName(getEntityName() + "VO")); // Add 'VO' to be consistent with all our other XML documents

        // Entity PK
        Element entityPKElement = new DefaultElement("EntityPK");

        entityPKElement.setText(getEntityPK().toString());
        
        // Entity FK(s)        
        List<Element> fkElements = buildFKElements();

        // Operator
        Element operatorElement = new DefaultElement("Operator");

        operatorElement.setText(getOperator());

        // NonFinancial DateTime
        Element nonFinancialDateTimeElement = new DefaultElement("NonFinancialDateTime");

        nonFinancialDateTimeElement.setText(getNonFinancialDateTime().toString());

        for (HibernateFieldDifference hibernateFieldDifference: getHibernateEntityFieldDifferences())
        {
            Element hibernateFieldDifferenceElement = hibernateFieldDifference.getAsElement();

            entityDifferenceElement.add(hibernateFieldDifferenceElement);
        }

        // Associate the remaining elements...
        entityDifferenceElement.add(entityNameElement);
        
        entityDifferenceElement.add(entityPKElement);

        for (Element fkElement:fkElements)
        {
            entityDifferenceElement.add(fkElement);
        }

        entityDifferenceElement.add(operatorElement);

        entityDifferenceElement.add(nonFinancialDateTimeElement);

        return entityDifferenceElement;
    }

    /**
     * @see #operator
     * @return
     */
    public String getOperator()
    {
        String operator = (String) SessionHelper.getFromThreadLocal(OPERATOR);

        operator = Util.initString(operator, "N/A");

        return operator;
    }

    /**
     * The EffectiveDate can be (either) the System date, or a date passed-in
     * via ThreadLocal. The one to use is dicated by the original configuration
     * in EDITServicesConfig (our configuration file). If the NonFinancialEntity.ChangeEffectiveDate 
     * is "SYTEM", then use the System date. If it is "PARAMETER", then check ThreadLocal for
     * a value for the name "CHANGEEFFECTIVEDATE" and use that.
     * 
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        EDITDate effectiveDate = getChangeEffectiveDate();
        
        if (effectiveDate == null)
        {
            effectiveDate = new EDITDate();
        }
        
        return effectiveDate;
    }

    /**
     * If the caller is deliberately trying to specify a
     * ChangeEffectiveDate, then we want to flag the
     * corresponding ChangeHistory as Pending "P" with 
     * a future EffectiveDate. The ChangeHistory will be
     * processed at a future date and have its PendingStatus
     * be changed to "H".
     * @see #isParameterDrivenEffectiveDate
     * @return
     */
    public String getPendingStatus()
    {
        String pendingStatus = "H";
        
        if (getChangeEffectiveDate() != null)
        {
            EDITDate systemDate = new EDITDate();
            
            if (getChangeEffectiveDate().after(systemDate))
            {
                pendingStatus = "P";    
            }
        }
        
        return pendingStatus;
    }
    
    /**
     * @see #isParameterDrivenEffectiveDate
     * @return
     */
    private Boolean isParameterDrivenEffectiveDate()
    {
        if (isParameterDrivenEffectiveDate == null)
        {
            for (NonFinancialEntity nonFinancialEntity: ServicesConfig.getNonFinancialEntities())
            {
                if (nonFinancialEntity.getClassName().equals(getEntityName()))
                {
                    String changeEffectiveDateFlag = nonFinancialEntity.getChangeEffectiveDate();
                   // Boolean isParameter = (Boolean) SessionHelper.getFromThreadLocal("useChangeEffectiveDate");

                    if (changeEffectiveDateFlag.equals("SYSTEM"))
                    {
                        isParameterDrivenEffectiveDate = false;
                    }
                    else if (changeEffectiveDateFlag.equals("PARAMETER"))
                    {
                        isParameterDrivenEffectiveDate = true;
                    }

                    break;
                }
            }
        }

        return isParameterDrivenEffectiveDate;
    }
    
    /**
     * The date placed into ThreadLocal.CHANGE_EFFECTIVE_DATE for a PARAMETER driven
     * Non Financial entity, if any.
     * @return
     */
    private EDITDate getChangeEffectiveDate()
    {
        EDITDate changeEffectiveDate = null;

        if (isParameterDrivenEffectiveDate())
        {
            changeEffectiveDate = (EDITDate) SessionHelper.getFromThreadLocal(CHANGE_EFFECTIVE_DATE);
        }
        
        return changeEffectiveDate;
    }
    
    /**
     * Loops through the available FK field names and generates a composite
     * Element for every one found.
     * @return
     * 
     *  <EntityFK>  // Repeats for every FK found
     *      <Name>FooFK</Name>
     *      <Value>123456789</Value>
     *  <EntityPK>
     */
    private List<Element> buildFKElements()
    {
        Element currentElement = null;
        
        List<Element> fkElements = new ArrayList<Element>();
        
        for (int i = 0; i < getFieldNames().length; i++)
        {   
            String fieldName = getFieldNames()[i].toString();
            
             if (fieldName.endsWith("FK"))
            {
                currentElement = new DefaultElement("EntityFK");
            }
            
            if (currentElement != null)
            {
                Element nameElement = new DefaultElement("Name");
                
                nameElement.setText(getFieldNames()[i].toString());
                
                Element valueElement = new DefaultElement("Value");
                
                Object value = getFieldValues()[i];
                
                String valueStr = (value == null)?"#NULL":value.toString();
                
                valueElement.setText(valueStr);
                
                currentElement.add(nameElement);
                
                currentElement.add(valueElement);
                
                fkElements.add(currentElement);
                
                currentElement = null;
            }
        }
        
        return fkElements;
    }

    /**
     * @see HibernateEntityDifference#fieldValues
     * @return
     */
    public Object[] getFieldValues()
    {
        return fieldValues;
    }

    /**
     * @see #fieldNames
     * @return
     */
    public Object[] getFieldNames()
    {
        return fieldNames;
    }
}
