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

import org.dom4j.tree.*;
import org.dom4j.*;

import java.util.*;

/**
 * Contains the actual data for the table as described in the metadata.  Has an Axis or a set of Ys but not both.
 * @author gfrosti
 */
public class Values
{
    private Element element = new DefaultElement("Values");

    /**
     * Stores a single tabular value. Y should repeat only in circumstances where 
     * the value has been coded as an "array" according to the KeyDef or AxisDef.
     * <P>
     * When used on Values, this is used for the rare instance where the "Table" of values has no axes. For example,
     * a product profile might use an XTbML to store the commission rates for a product. For some companies and
     * products, this may vary on a wide variety of criteria. For others, there may be a single rate.
     */
    private List ys = new ArrayList();
    
    /**
     * List of Axis objects
     */
    private List axises = new ArrayList();


    /**
     * Creates an instance of Values
     */
    public Values()
    {
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
     * Returns the list of Y objects
     *
     * @return List of Y objects
     */
    public List getYs()
    {
        return ys;
    }

    /**
     * Adds a Y object
     *
     * @param y
     */
    public void add(Y y)
    {
        this.ys.add(y);

        this.element.add(y.getElement());
    }

    /**
     * Removes a Y object
     * @param y
     */
    public void remove(Y y)
    {
        this.ys.remove(y);

        this.element.remove(y.getElement());
    }

    /**
     * Returns the list of axises
     * @return
     */
    public List getAxis()
    {
        return axises;
    }

    /**
     * Adds an axis
     * @param axis
     */
    public void add(Axis axis)
    {
        this.axises.add(axis);

        this.element.add(axis.getElement());
    }

    /**
     * Removes an axis
     * @param axis
     */
    public void remove(Axis axis)
    {
        this.axises.remove(axis);

        this.element.remove(axis.getElement());
    }
}