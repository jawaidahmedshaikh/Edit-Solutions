/*
 * User: sdorman
 * Date: Aug 17, 2006
 * Time: 10:50:11 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.model.tabular;

import org.dom4j.*;
import org.dom4j.tree.*;

/**
 * Stores a single tabular value. Y should repeat only in circumstances where the value has been coded as an "array"
 * according to the KeyDef or AxisDef.
 * <P>
 * When used on Values, this is used for the rare instance where the "Table" of values has no axes. For example, a
 * product profile might use an XTbML to store the commission rates for a product. For some companies and products,
 * this may vary on a wide variety of criteria. For others, there may be a single rate.
 */
public class Y
{
    Element element = new DefaultElement("Y");

    /**
     * Creates an instance with the specified yValue defined as a String
     *
     * @param yValue        the y value
     * @param tValue        the t value (index)
     */
    public Y(String yValue, String tValue)
    {
        this.element.add(new DefaultText(yValue));
        this.element.addAttribute("t", tValue);
    }

    /**
     * Creates an instance with the specified yValue defined as a double
     *
     * @param yValue        the y value
     * @param tValue        the t value (index)
     */
    public Y(double yValue, String tValue)
    {
        this.element.add(new DefaultText(yValue + ""));
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
}
