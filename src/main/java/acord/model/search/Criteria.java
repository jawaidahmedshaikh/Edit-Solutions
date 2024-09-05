/*
 * User: sdorman
 * Date: Aug 29, 2006
 * Time: 8:51:57 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.model.search;

import org.dom4j.*;
import org.dom4j.tree.*;
import fission.utility.*;

/**
 * Represents a single search parameter in the collection.As of 2.7.90 (02-2.01.TD1) this object has moved from OLifE
 * to TXLife. Its use in OLifE is deprecated
 * <P>
 * Note that the ResultSet concept is used in two places:1. Searching2. Results of other method calls.The criteria
 * object is only obtained in the case that the ResultSet is obtained from Server.OliRequestResultSet().To process
 * the criteria you set, call OliOpen() on the ResultSet. 124Directions for use:. You can have one or multiple criteria
 * objects in a collection.. Like and not like is read from the left, no wild cards allowed. Queries are processed in
 * order. No parenthetical grouping for version 2.0. Ordering of object responsibility of client app. Support of
 * 'Order By' functionality not for 2.0 Order by default will be:
 * <BR>
 * - party object - ordered by FullName- activity object - ordered by DueDate and StartTime- group object - ordered by
 * FullName- holding object - ordered by HoldingID- Investment Product Object - ordered by ProductName- Policy Product
 * Object - ordered by ProductName. Case insensitive search. Multiple objects in the collection are connected with an
 * 'AND' condition.Working with ResultSet:. Criteria object is off of the ResultSet Object. To Restrict maximum # of
 * records returned, - set MaxRecords property on ResultSet. SearchType needs to be added as a parameter of the
 * ResultSet object. The type of object returned from the search would depend on the type of search performed, not on
 * the object types in the criteria object.
 * <P>
 * SearchType would be set to indicate:
 * <BR>
 * OLI_SEARCH_PARTYOLI_SEARCH_ACTIVITYOLI_SEARCH_GROUPOLI_SEARCH_HOLDINGOLI_SEARCH_INVPRODUCTOLI_SEARCH_POLPRODUCT
 * (See Search Types). The objects that are allowed in a Search are indicated by the SearchType.. All objects allowed
 * in a single criteria collection must be from a single top level object hierarchy. For instance, you can have criteria
 * set for party and address objects, but not for party and activity objects within the same set of criteria.
 */
public class Criteria
{
    private Element element = new DefaultElement("Criteria");

    /**
     * @see acord.search.Constants
     */
    private Element objectType = new DefaultElement("ObjectType");                    // Required

    private Element operation = new DefaultElement("Operation");                     // Required
    private Element propertyName = new DefaultElement("PropertyName");                  // Required
    private Element propertyValue = new DefaultElement("PropertyValue");                 // Required
    private Element comparedPropertyName = new DefaultElement("ComparedPropertyName");
    private Element comparedObjectType = new DefaultElement("ComparedObjectType");


    /**
     * Instantiates an object with the minimum required information
     *
     * @param operation
     * @param propertyName
     * @param propertyValue
     */
    public Criteria(int objectType, int operation, String propertyName, String propertyValue)
    {
        this.element.add(this.objectType);
        this.element.add(this.operation);
        this.element.add(this.propertyName);
        this.element.add(this.propertyValue);

        this.setObjectType(objectType);
        this.setOperation(operation);
        this.setPropertyName(propertyName);
        this.setPropertyValue(propertyValue);
    }

    /**
     * Returns the XML element for this object
     *
     * @return
     */
    public Element getElement()
    {
        return this.element;
    }

    public int getObjectType()
    {
        return Integer.parseInt(objectType.getText());
    }

    public void setObjectType(int objectType)
    {
        if (this.objectType.content() != null)
        {
            this.objectType.clearContent();
        }

        String text = Util.getResourceMessage("objectType." + objectType);

        this.objectType.add(new DefaultText(text));
        this.objectType.addAttribute("tc", objectType + "");
    }

    /**
     * Returns the typecode of acceptable operation. (=, <>, <, >, <=, >=, like, not like)
     *
     * @return
     */
    public int getOperation()
    {
        return Integer.parseInt(objectType.getText());
    }

    /**
     * Sets the typecode of acceptable operation. (=, <>, <, >, <=, >=, like, not like)
     *
     * @param operation
     */
    public void setOperation(int operation)
    {
        if (this.operation.content() != null)
        {
            this.operation.clearContent();
        }

        String text = Util.getResourceMessage("operation." + operation);

        this.operation.add(new DefaultText(text));
        this.operation.addAttribute("tc", operation + "");
    }

    /**
     * Returns the name of the property that is being searched.
     *
     * @return
     */
    public String getPropertyName()
    {
        return this.propertyName.getText();
    }

    /**
     * Sets the name of the property that is being searched.
     *
     * @param propertyName
     */
    public void setPropertyName(String propertyName)
    {
        if (this.propertyName.content() != null)
        {
            this.propertyName.clearContent();
        }

        this.propertyName.add(new DefaultText(propertyName));
    }

    /**
     * Returns the property value
     *
     * @return
     */
    public String getPropertyValue()
    {
        return this.propertyValue.getText();
    }

    /**
     * Sets the property value
     *
     * @param propertyValue
     */
    public void setPropertyValue(String propertyValue)
    {
        if (this.propertyValue.content() != null)
        {
            this.propertyValue.clearContent();
        }

        this.propertyValue.add(new DefaultText(propertyValue));
    }

    /**
     * Returns the the Property Name to compare against
     *
     * @return
     */
    public String getComparedPropertyName()
    {
        return this.comparedPropertyName.getText();
    }

    /**
     * Sets the the Property Name to compare against
     *
     * @param comparedPropertyName
     */

    public void setComparedPropertyName(String comparedPropertyName)
    {
        if (this.comparedPropertyName.content() != null)
        {
            this.comparedPropertyName.clearContent();
        }

        this.comparedPropertyName.add(new DefaultText(comparedPropertyName));

        this.element.add(this.comparedPropertyName);
    }

    /**
     * Returns the Object Type to compare against
     *
     * @return
     */
    public int getComparedObjectType()
    {
        return Integer.parseInt(comparedObjectType.getText());
    }

    /**
     * Sets the Object Type to compare against
     *
     * @param comparedObjectType
     */
    public void setComparedObjectType(int comparedObjectType)
    {
        if (this.comparedObjectType.content() != null)
        {
            this.comparedObjectType.clearContent();
        }

        String text = Util.getResourceMessage("objectType." + comparedObjectType);

        this.comparedObjectType.add(new DefaultText(text));
        this.comparedObjectType.addAttribute("tc", comparedObjectType + "");

        this.element.add(this.comparedObjectType);
    }
}
