/*
 * User: gfrosti
 * Date: Jun 8, 2006
 * Time: 9:14:00 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package acord.model.tabular;

import org.dom4j.*;
import org.dom4j.tree.*;


/**
 * Container for tabular data. Table should only repeat if it varies 
 * across jurisdictions and/or if it is a Select-Ultimate table
 * @author gfrosti
 */
public class Table
{
    private Element element = new DefaultElement("Table");

    /**
     * Contains information on how to interpret the values in the table. (Required)
     */
    private MetaData metaData;

    /**
     * Contains the actual data for the table as described in the metadata. (Required)
     */
    private Values values;


    /**
     * Creates a new instance of Table with the minimum required information
     */
    public Table(MetaData metaData, Values values)
    {
        this.add(metaData);
        this.add(values);
    }

    /**
     * Returns the XML element for this object
     * @return
     */
    public Element getElement()
    {
        return this.element;
    }

    /**
     * Returns the MetaData object
     * @return
     */
    public MetaData getMetaData()
    {
        return this.metaData;
    }

    /**
     * Adds a MetaData object.  Only 1 MetaData is allowed, it is a required object
     * @param metaData
     */
    public void add(MetaData metaData)
    {
        //  Remove the old one first
        if (this.metaData != null)
        {
            this.element.remove(this.metaData.getElement());
        }

        // Now add
        this.metaData = metaData;

        this.element.add(metaData.getElement());
    }

    /**
     * Returns the Values object
     * @return
     */
    public Values getValues()
    {
        return this.values;
    }

    /**
     * Adds the Values object.  Only 1 Values is allowed, it is a required object
     * @param values
     */
    public void add(Values values)
    {
        //  Remove the old one first
        if (this.values != null)
        {
            this.element.remove(this.values.getElement());
        }

        // Now add
        this.values = values;

        this.element.add(values.getElement());
    }
}
