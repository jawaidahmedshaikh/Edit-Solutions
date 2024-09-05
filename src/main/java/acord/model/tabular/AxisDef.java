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
import fission.utility.*;

/**
 * Contains definition of normal dimensions like dates and numeric values.
 * @author gfrosti
 */
public class AxisDef
{
    Element element = new DefaultElement("AxisDef");

    /**
     * @see Constants
     */
    private Element scaleType = new DefaultElement("ScaleType");
    
    /**
     * Short description for the axis.
     */
    private Element axisName = new DefaultElement("AxisName");
    
    /**
     * Minimum value for the dimension of the table.
     */
    private Element minScaleValue = new DefaultElement("MinScaleValue");
    
    /**
     * Maximum value for the dimension of the table.
     */
    private Element maxScaleValue = new DefaultElement("MinScaleValue");
    
    /**
     * Minimum date for the dimension of the table.
     */
    private Element minScaleDate = new DefaultElement("MinScaleDate");
    
    /**
     * Maximum date for the dimension of the table.
     */
    private Element maxScaleDate = new DefaultElement("MaxScaleDate");
    
    /**
     * Identifies the increment used between each of the values in this dimension.
     */
    private Element increment = new DefaultElement("Increment");



    /**
     * Creates a new instance of AxisDef
     */
    public AxisDef()
    {
    }

    /**
     * Creates a new instance of AxisDef
     *
     * @param axisName                  the axis name
     */
    public AxisDef(String axisName)
    {
        this();

        this.setAxisName(axisName);
    }

    /**
     * Creates a new instance of AxisDef with basic parameters
     *
     * @param axisName                  axis name
     * @param scaleType                 scale type
     */
    public AxisDef(String axisName, int scaleType)
    {
        this(axisName);

        this.setScaleType(scaleType);
    }

    /**
     * Creates a new instance of AxisDef with some basic parameters
     *
     * @param axisName                  axis name
     * @param scaleType                 scale type
     * @param minScaleValue             minimum scale value
     * @param maxScaleValue             maximum scale value
     * @param increment                 increment
     */
    public AxisDef(String axisName, int scaleType, String minScaleValue, String maxScaleValue, String increment)
    {
        this(axisName);

        this.setScaleType(scaleType);
        this.setMinScaleValue(minScaleValue);
        this.setMaxScaleValue(maxScaleValue);
        this.setIncrement(increment);
    }

    /**
     * Returns the XML element for this object
     * @return
     */
    public Element getElement()
    {
        return this.element;
    }

    public int getScaleType()
    {
        return Integer.parseInt(scaleType.getText());
    }

    public void setScaleType(int scaleType)
    {
        if (this.scaleType.content() != null)
        {
            this.scaleType.clearContent();
        }

        this.element.add(this.scaleType);

        String text = Util.getResourceMessage("scaleType." + scaleType);

        this.scaleType.add(new DefaultText(text));
        this.scaleType.addAttribute("tc", scaleType + "");
    }

    public String getAxisName()
    {
        return axisName.getText();
    }

    public void setAxisName(String axisName)
    {
        String idAttributeName = "id";

        if (this.axisName.content() != null)
        {
            this.axisName.clearContent();

            Attribute idAttribute = element.attribute(idAttributeName);
            element.remove(idAttribute);
        }

        this.element.add(this.axisName);

        this.axisName.add(new DefaultText(axisName));

        element.addAttribute(idAttributeName, axisName);
    }

    public String getMinScaleValue()
    {
        return minScaleValue.getText();
    }

    public void setMinScaleValue(String minScaleValue)
    {
        if (this.minScaleValue.content() != null)
        {
            this.minScaleValue.clearContent();
        }

        this.element.add(this.minScaleValue);

        this.minScaleValue.add(new DefaultText(minScaleValue));
    }

    public String getMaxScaleValue()
    {
        return maxScaleValue.getText();
    }

    public void setMaxScaleValue(String maxScaleValue)
    {
        if (this.maxScaleValue.content() != null)
        {
            this.maxScaleValue.clearContent();
        }

        this.element.add(this.maxScaleValue);

        this.maxScaleValue.add(new DefaultText(maxScaleValue));
    }

    public String getMinScaleDate()
    {
        return minScaleDate.getText();
    }

    public void setMinScaleDate(String minScaleDate)
    {
        if (this.minScaleDate.content() != null)
        {
            this.minScaleDate.clearContent();
        }

        this.element.add(this.minScaleDate);

        this.minScaleDate.add(new DefaultText(minScaleDate));
    }

    public String getMaxScaleDate()
    {
        return maxScaleDate.getText();
    }

    public void setMaxScaleDate(String maxScaleDate)
    {
        if (this.maxScaleDate.content() != null)
        {
            this.maxScaleDate.clearContent();
        }

        this.element.add(this.maxScaleDate);

        this.maxScaleDate.add(new DefaultText(maxScaleDate));
    }

    public String getIncrement()
    {
        return this.increment.getText();
    }

    public void setIncrement(String increment)
    {
        if (this.increment.content() != null)
        {
            this.increment.clearContent();
        }

        this.element.add(this.increment);

        this.increment.add(new DefaultText(increment));
    }
}