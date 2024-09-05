/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class InvestmentArrayVO.
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentArrayVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _cumUnits
     */
    private java.math.BigDecimal _cumUnits;

    /**
     * Field _unitValue
     */
    private java.math.BigDecimal _unitValue;

    /**
     * Field _unitValueDate
     */
    private java.lang.String _unitValueDate;

    /**
     * Field _investmentFK
     */
    private long _investmentFK;

    /**
     * keeps track of state for field: _investmentFK
     */
    private boolean _has_investmentFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentArrayVO() {
        super();
    } //-- edit.common.vo.InvestmentArrayVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Note: hashCode() has not been overriden
     * 
     * @param obj
     */
    public boolean equals(java.lang.Object obj)
    {
        if ( this == obj )
            return true;
        
        if (super.equals(obj)==false)
            return false;
        
        if (obj instanceof InvestmentArrayVO) {
        
            InvestmentArrayVO temp = (InvestmentArrayVO)obj;
            if (this._cumUnits != null) {
                if (temp._cumUnits == null) return false;
                else if (!(this._cumUnits.equals(temp._cumUnits))) 
                    return false;
            }
            else if (temp._cumUnits != null)
                return false;
            if (this._unitValue != null) {
                if (temp._unitValue == null) return false;
                else if (!(this._unitValue.equals(temp._unitValue))) 
                    return false;
            }
            else if (temp._unitValue != null)
                return false;
            if (this._unitValueDate != null) {
                if (temp._unitValueDate == null) return false;
                else if (!(this._unitValueDate.equals(temp._unitValueDate))) 
                    return false;
            }
            else if (temp._unitValueDate != null)
                return false;
            if (this._investmentFK != temp._investmentFK)
                return false;
            if (this._has_investmentFK != temp._has_investmentFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCumUnitsReturns the value of field 'cumUnits'.
     * 
     * @return the value of field 'cumUnits'.
     */
    public java.math.BigDecimal getCumUnits()
    {
        return this._cumUnits;
    } //-- java.math.BigDecimal getCumUnits() 

    /**
     * Method getInvestmentFKReturns the value of field
     * 'investmentFK'.
     * 
     * @return the value of field 'investmentFK'.
     */
    public long getInvestmentFK()
    {
        return this._investmentFK;
    } //-- long getInvestmentFK() 

    /**
     * Method getUnitValueReturns the value of field 'unitValue'.
     * 
     * @return the value of field 'unitValue'.
     */
    public java.math.BigDecimal getUnitValue()
    {
        return this._unitValue;
    } //-- java.math.BigDecimal getUnitValue() 

    /**
     * Method getUnitValueDateReturns the value of field
     * 'unitValueDate'.
     * 
     * @return the value of field 'unitValueDate'.
     */
    public java.lang.String getUnitValueDate()
    {
        return this._unitValueDate;
    } //-- java.lang.String getUnitValueDate() 

    /**
     * Method hasInvestmentFK
     */
    public boolean hasInvestmentFK()
    {
        return this._has_investmentFK;
    } //-- boolean hasInvestmentFK() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method setCumUnitsSets the value of field 'cumUnits'.
     * 
     * @param cumUnits the value of field 'cumUnits'.
     */
    public void setCumUnits(java.math.BigDecimal cumUnits)
    {
        this._cumUnits = cumUnits;
        
        super.setVoChanged(true);
    } //-- void setCumUnits(java.math.BigDecimal) 

    /**
     * Method setInvestmentFKSets the value of field
     * 'investmentFK'.
     * 
     * @param investmentFK the value of field 'investmentFK'.
     */
    public void setInvestmentFK(long investmentFK)
    {
        this._investmentFK = investmentFK;
        
        super.setVoChanged(true);
        this._has_investmentFK = true;
    } //-- void setInvestmentFK(long) 

    /**
     * Method setUnitValueSets the value of field 'unitValue'.
     * 
     * @param unitValue the value of field 'unitValue'.
     */
    public void setUnitValue(java.math.BigDecimal unitValue)
    {
        this._unitValue = unitValue;
        
        super.setVoChanged(true);
    } //-- void setUnitValue(java.math.BigDecimal) 

    /**
     * Method setUnitValueDateSets the value of field
     * 'unitValueDate'.
     * 
     * @param unitValueDate the value of field 'unitValueDate'.
     */
    public void setUnitValueDate(java.lang.String unitValueDate)
    {
        this._unitValueDate = unitValueDate;
        
        super.setVoChanged(true);
    } //-- void setUnitValueDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentArrayVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentArrayVO) Unmarshaller.unmarshal(edit.common.vo.InvestmentArrayVO.class, reader);
    } //-- edit.common.vo.InvestmentArrayVO unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
