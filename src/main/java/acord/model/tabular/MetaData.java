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

import java.util.*;

import org.dom4j.tree.*;
import org.dom4j.*;
import fission.utility.*;

/**
 * Contains information on how to interpret the values in the table.
 * @author gfrosti
 */
public class MetaData
{
    private Element element = new DefaultElement("MetaData");

    /**
     * Contains definition of normal dimensions like dates and numeric values.
     */
    private List axisDefs = new ArrayList();

    /**
     * The currency type used at the SubAccount level.
     * @see Constants
     */
//    private Element currencyTypeCode = new DefaultElement("CurrencyTypeCode");       // Constants has not been updated to contain values yet

    /**
     * Represents the type of data contained in the table.
     * @see Constants
     */
    private Element dataType = new DefaultElement("DataType");
    
    /**
     * Freeform description of the contents of the table or sub table being included.
     */
    private Element tableDescription = new DefaultElement("TableDescription");
    
    /**
     * Indicates the multiplier for the values of the table by reflecting the power of 10 
     * to be multiplied against the actual value (0 = 1, 1=10, 2=100, etc.) 
     * Default value is 0. It can be positive or negative.
     */
    private Element scalingFactor = new DefaultElement("ScalingFactor");

    /**
     * @see Constants
     */
    private Element nation = new DefaultElement("Nation");



    /**
     * Creates a new instance of MetaData
     * */
    public MetaData()
    {
    }

    public Element getElement()
    {
        return this.element;
    }

//    public int getcurrencyTypeCode()
//    {
//        return Integer.parseInt(currencyTypeCode.getText());
//    }
//
//    public void setCurrencyTypeCode(int currencyTypeCode)
//    {
//        if (this.currencyTypeCode.content() != null)
//        {
//            this.currencyTypeCode.clearContent();
//        }
//
//        element.add(this.currencyTypeCode);
//
//        String text = Util.getResourceMessage("currencyTypeCode." + currencyTypeCode);
//
//        this.currencyTypeCode.add(new DefaultText(text));
//        this.currencyTypeCode.addAttribute("tc", currencyTypeCode + "");
//    }
//
    public int getDataType()
    {
        return Integer.parseInt(dataType.getText());
    }

    public void setDataType(int dataType)
    {
        if (this.dataType.content() != null)
        {
            this.dataType.clearContent();
        }

        element.add(this.dataType);

        String text = Util.getResourceMessage("dataType." + dataType);

        this.dataType.add(new DefaultText(text));
        this.dataType.addAttribute("tc", dataType + "");
    }

    public String getTableDescription()
    {
        return tableDescription.getText();
    }

    public void setTableDescription(String tableDescription)
    {
        if (this.tableDescription.content() != null)
        {
            this.tableDescription.clearContent();
        }

        element.add(this.tableDescription);

        this.tableDescription.add(new DefaultText(tableDescription));
    }

    public int getScalingFactor()
    {
        return Integer.parseInt(scalingFactor.getText());
    }

    public void setScalingFactor(int scalingFactor)
    {
        //  Remove the already added default scaling factor and add the new one
        if (this.scalingFactor.content() != null)
        {
            this.scalingFactor.clearContent();
        }

        element.add(this.scalingFactor);

        this.scalingFactor.add(new DefaultText(scalingFactor + ""));
    }

    public int getNation()
    {
        return Integer.parseInt(nation.getText());
    }

    public void setNation(int nation)
    {
        if (this.nation.content() != null)
        {
            this.nation.clearContent();
        }

        element.add(this.nation);

        String text = Util.getResourceMessage("nation." + nation);

        this.nation.add(new DefaultText(text));
        this.nation.addAttribute("tc", nation + "");
    }

    public List getAxisDefs()
    {
        return axisDefs;
    }

    public void add(AxisDef axisDef)
    {
        this.axisDefs.add(axisDef);

        element.add(axisDef.getElement());
    }

    public void remove(AxisDef axisDef)
    {
        this.axisDefs.remove(axisDef);

        element.remove(axisDef.getElement());
    }
}