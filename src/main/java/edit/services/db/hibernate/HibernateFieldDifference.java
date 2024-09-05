package edit.services.db.hibernate;

import fission.utility.Util;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * A child entity of HibernateEntityDifference. It represents
 * the field of a HibernateEntity that has changes with its before and after values.
 */
public class HibernateFieldDifference
{
    /**
     * The name of the field that has a change in its before/after value.
     */
    private String fieldName;
    
    /**
     * The before value. It could be null if the newValue is not null.
     */
    private Object oldValue;
    
    /**
     * The after value. It could be null if the oldValue is not null.
     */
    private Object newValue;
    
    public HibernateFieldDifference(String fieldName, Object oldValue, Object newValue)
    {
        this.fieldName = fieldName;
        
        this.oldValue = oldValue;
        
        this.newValue = newValue;
    }
    
    /**
     * Returns the DOM4J equivalent of this HibernateFieldDifference 
     * in the following format:
     * 
     * <FieldDifference>
     *      <FieldName>fooFieldName</FieldName>
     *      <OldValue>fooOldValue</OldValue>
     *      <NewValue>fooNewValue</NewValue>
     * </FieldDifference>.
     * @return
     */
    public Element getAsElement()
    {
        // Field Difference
        Element fieldDifferenceElement = new DefaultElement("FieldDifference");
        
        // Field Name
        Element fieldNameElement = new DefaultElement("FieldName");
        
        fieldNameElement.setText(getFieldName());
        
        // Old Value
        Element oldValueElement = null;

        if (getOldValue() != null)
        {
            oldValueElement = new DefaultElement("OldValue");
            
            oldValueElement.setText(getValueAsString(fieldNameElement.getText(),  getOldValue()));            
        }
        
        // New Value
        Element newValueElement = null;
        
        if (getNewValue() != null)
        {
            newValueElement = new DefaultElement("NewValue");
            
            newValueElement.setText(getValueAsString(fieldNameElement.getText(), getNewValue()));            
        }
        
        // Assemble the Element...
        fieldDifferenceElement.add(fieldNameElement);
        
        if (getOldValue() != null)
        {
            fieldDifferenceElement.add(oldValueElement);
        }

        if (getNewValue() != null)
        {
            fieldDifferenceElement.add(newValueElement);
        }
        
        return fieldDifferenceElement;
    }

    /**
     * @see #fieldName
     * @return
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * @see #oldValue
     * @return
     */
    public Object getOldValue()
    {
        return oldValue;
    }

    /**
     * @see #newValue
     * @return
     */
    public Object getNewValue()
    {
        return newValue;
    }
    
    /**
     * Converts the specified value to its String equivalent, or to the
     * reserved word value of "#NULL" if the specifie value is null.
     * @param value
     * @return
     */
    private String getValueAsString(String name, Object value)
    {
        String valueAsString = null;
        
        if (value != null)
        {
            valueAsString = value.toString();
        }
        else
        {
            value = "#NULL";
        }
        
        return valueAsString;
    }
}
