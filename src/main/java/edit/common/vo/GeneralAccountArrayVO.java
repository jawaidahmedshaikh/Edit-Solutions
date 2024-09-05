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
 * Class GeneralAccountArrayVO.
 * 
 * @version $Revision$ $Date$
 */
public class GeneralAccountArrayVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _cumDollars
     */
    private java.math.BigDecimal _cumDollars;

    /**
     * Field _dollars
     */
    private java.math.BigDecimal _dollars;

    /**
     * Field _businessDate
     */
    private java.lang.String _businessDate;

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

    public GeneralAccountArrayVO() {
        super();
    } //-- edit.common.vo.GeneralAccountArrayVO()


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
        
        if (obj instanceof GeneralAccountArrayVO) {
        
            GeneralAccountArrayVO temp = (GeneralAccountArrayVO)obj;
            if (this._cumDollars != null) {
                if (temp._cumDollars == null) return false;
                else if (!(this._cumDollars.equals(temp._cumDollars))) 
                    return false;
            }
            else if (temp._cumDollars != null)
                return false;
            if (this._dollars != null) {
                if (temp._dollars == null) return false;
                else if (!(this._dollars.equals(temp._dollars))) 
                    return false;
            }
            else if (temp._dollars != null)
                return false;
            if (this._businessDate != null) {
                if (temp._businessDate == null) return false;
                else if (!(this._businessDate.equals(temp._businessDate))) 
                    return false;
            }
            else if (temp._businessDate != null)
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
     * Method getBusinessDateReturns the value of field
     * 'businessDate'.
     * 
     * @return the value of field 'businessDate'.
     */
    public java.lang.String getBusinessDate()
    {
        return this._businessDate;
    } //-- java.lang.String getBusinessDate() 

    /**
     * Method getCumDollarsReturns the value of field 'cumDollars'.
     * 
     * @return the value of field 'cumDollars'.
     */
    public java.math.BigDecimal getCumDollars()
    {
        return this._cumDollars;
    } //-- java.math.BigDecimal getCumDollars() 

    /**
     * Method getDollarsReturns the value of field 'dollars'.
     * 
     * @return the value of field 'dollars'.
     */
    public java.math.BigDecimal getDollars()
    {
        return this._dollars;
    } //-- java.math.BigDecimal getDollars() 

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
     * Method setBusinessDateSets the value of field
     * 'businessDate'.
     * 
     * @param businessDate the value of field 'businessDate'.
     */
    public void setBusinessDate(java.lang.String businessDate)
    {
        this._businessDate = businessDate;
        
        super.setVoChanged(true);
    } //-- void setBusinessDate(java.lang.String) 

    /**
     * Method setCumDollarsSets the value of field 'cumDollars'.
     * 
     * @param cumDollars the value of field 'cumDollars'.
     */
    public void setCumDollars(java.math.BigDecimal cumDollars)
    {
        this._cumDollars = cumDollars;
        
        super.setVoChanged(true);
    } //-- void setCumDollars(java.math.BigDecimal) 

    /**
     * Method setDollarsSets the value of field 'dollars'.
     * 
     * @param dollars the value of field 'dollars'.
     */
    public void setDollars(java.math.BigDecimal dollars)
    {
        this._dollars = dollars;
        
        super.setVoChanged(true);
    } //-- void setDollars(java.math.BigDecimal) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.GeneralAccountArrayVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.GeneralAccountArrayVO) Unmarshaller.unmarshal(edit.common.vo.GeneralAccountArrayVO.class, reader);
    } //-- edit.common.vo.GeneralAccountArrayVO unmarshal(java.io.Reader) 

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
