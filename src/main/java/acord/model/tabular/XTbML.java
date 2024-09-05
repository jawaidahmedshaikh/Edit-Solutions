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

import java.util.*;

/**
 * The aggregates for XTbML the Society of Actuaries ACORD tabular standard.
 * @author gfrosti
 */
public class XTbML
{
    private Element element = new DefaultElement("XTbML");

    private static String ID_ATTRIBUTE_NAME = "id";
    private static String VERSION_ATTRIBUTE_NAME = "Version";

    /**
     * @see ContentClassification (required)
     */
    private ContentClassification contentClassification;
    
    /**
     * The set of ACORD Tables containing the tabular values.
     */
    private List tables = new ArrayList();



    /**
     * Creates a new instance of XTbML with the minimum required information
     *
     * @param tableID                   unique XML ID (required)
     * @param tableName                 ContentClassification tableName  (required)
     * @param contentType               ContentClassification contentType (required)
     */
    public XTbML(String tableID, String tableName, int contentType)
    {
        ContentClassification contentClassification = new ContentClassification(tableName, contentType);

        this.setContentClassification(contentClassification);

        this.setID(tableID);
    }

    /**
     * Returns the document element
     * @return
     */
    public Element getElement()
    {
        return element;
    }

    /**
     * Gets the unique XML ID used as a reference for a given XML document (required parameter)
     */
    public String getID()
    {
        return this.element.attributeValue(XTbML.ID_ATTRIBUTE_NAME);
    }

    /**
     * Sets the unique XML ID used as a reference for a given XML document (required parameter)
     *
     * @param id                the unique XML id to be set
     */
    public void setID(String id)
    {
        //  if the id is already set, remove the old one from the element first.
        Attribute idAttribute = element.attribute(XTbML.ID_ATTRIBUTE_NAME);

        if (idAttribute != null)
        {
            element.remove(idAttribute);
        }

        element.addAttribute(XTbML.ID_ATTRIBUTE_NAME, id);
    }

    /**
     * Returns the version of the ACORD specification this transaction used
     */
    public String getVersion()
    {
        return this.element.attributeValue(XTbML.VERSION_ATTRIBUTE_NAME);
    }

    /**
     * Sets the version of the ACORD specification this transaction used
     *
     * @param version
     */
    public void setVersion(String version)
    {
        //  if the version is already set, remove the old one from the element first.
        Attribute versionAttribute = element.attribute(XTbML.VERSION_ATTRIBUTE_NAME);

        if (versionAttribute != null)
        {
            element.remove(versionAttribute);
        }

        element.addAttribute(XTbML.VERSION_ATTRIBUTE_NAME, version);
    }

    /**
     * Returns the ContentClassification object
     *
     * @return
     */
    public ContentClassification getcontentClassification()
    {
        return this.contentClassification;
    }

    /**
     * Sets the XTbML's ContentClassification.  Replaces the existing ContentClassification that was required on
     * instantiation of the XTbML object.
     *
     * @param contentClassification
     */
    private void setContentClassification(ContentClassification contentClassification)
    {
        //  Remove old one first, then add
        if (this.contentClassification != null)
        {
            this.element.remove(this.contentClassification.getElement());
        }

        this.contentClassification = contentClassification;
        this.element.add(contentClassification.getElement());
    }

    /**
     * Returns the Table object for the specified index
     *
     * @param index             index denoting which table to return
     *
     * @return  Table object
     */
    public Table getTable(int index)
    {
        return (Table) this.tables.get(index);
    }

//    public void addTable(Map tableData)
//    {
//        Table table = new Table(tableData);
//
//        this.tables.add(table);
//
//        this.element.add(table.getElement());
//    }

    /**
     * Adds a Table to the XTbML.
     *
     * @param table         table to be added
     */
    public void add(Table table)
    {
        this.tables.add(table);

        this.element.add(table.getElement());
    }

    /**
     * Removes a Table from the XTbML
     *
     * @param table         table to be removed
     */
    public void remove(Table table)
    {
        this.tables.remove(table);

        this.element.remove(table.getElement());
    }
}
