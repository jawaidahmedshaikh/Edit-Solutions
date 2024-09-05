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

import java.util.ArrayList;
import java.util.List;

/**
 * Axis of a table.  Can contain a set of Axises or a set of Ys, but not both
 * @author gfrosti
 */
public class Axis
{
    private Element element = new DefaultElement("Axis");

    /**
     * Stores a single tabular value. Y should repeat only in circumstances where
     * the value has been coded as an "array" according to the KeyDef or AxisDef.
     */
    private List ys = new ArrayList();

    /**
     * List of Axis objects
     */
    private List axes = new ArrayList();



    /**
     * Creates an instance with the specified axisDefID.  The axisDefID is displayed in the Axis tag for readability.
     *
     * @param axisDefID         the same id that was used in the defining AxisDef
     */
    public Axis(String axisDefID)
    {
        this.element.addAttribute("AxisDefID", axisDefID);
    }

    /**
     * Creates an instance with the specified axisDefID and tValue.  The axisDefID is displayed in the Axis tag for
     * readability.  The tValue is displayed on the Axis tag, it specifies the value for this Axis
     *
     * @param axisDefID         the same id that was used in the defining AxisDef
     * @param tValue            the value for this Axis
     */
    public Axis(String axisDefID, String tValue)
    {
        this(axisDefID);

        this.element.addAttribute("t", tValue);
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
     * Returns the list of Axis objects
     *
     * @return  List containing Axis objects
     */
    public List getAxes()
    {
        return this.axes;
    }

    /**
     * Adds an Axis to the list of axes
     * @param axis
     */
    public void add(Axis axis)
    {
        this.axes.add(axis);

        this.element.add(axis.getElement());
    }

    /**
     * Removes an Axis from the list of axes.
     * @param axis
     */
    public void remove(Axis axis)
    {
        this.axes.remove(axis);

        this.element.remove(axis.getElement());
    }

    /**
     * Returns the list of Y objects
     *
     * @return List containing Y objects
     */
    public List getYs()
    {
        return this.ys;
    }

    /**
     * Adds a Y object to the list of Ys
     *
     * @param y
     */
    public void add(Y y)
    {
        this.ys.add(y);

        this.element.add(y.getElement());
    }

    /**
     * Removes a Y object from the list of Ys
     * @param y
     */
    public void remove(Y y)
    {
        this.ys.remove(y);

        this.element.remove(y.getElement());
    }
}
