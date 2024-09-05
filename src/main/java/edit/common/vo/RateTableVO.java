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
 * Class RateTableVO.
 * 
 * @version $Revision$ $Date$
 */
public class RateTableVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _rateTablePK
     */
    private long _rateTablePK;

    /**
     * keeps track of state for field: _rateTablePK
     */
    private boolean _has_rateTablePK;

    /**
     * Field _tableKeysFK
     */
    private long _tableKeysFK;

    /**
     * keeps track of state for field: _tableKeysFK
     */
    private boolean _has_tableKeysFK;

    /**
     * Field _age
     */
    private int _age;

    /**
     * keeps track of state for field: _age
     */
    private boolean _has_age;

    /**
     * Field _duration
     */
    private int _duration;

    /**
     * keeps track of state for field: _duration
     */
    private boolean _has_duration;

    /**
     * Field _rate
     */
    private java.math.BigDecimal _rate;


      //----------------/
     //- Constructors -/
    //----------------/

    public RateTableVO() {
        super();
    } //-- edit.common.vo.RateTableVO()


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
        
        if (obj instanceof RateTableVO) {
        
            RateTableVO temp = (RateTableVO)obj;
            if (this._rateTablePK != temp._rateTablePK)
                return false;
            if (this._has_rateTablePK != temp._has_rateTablePK)
                return false;
            if (this._tableKeysFK != temp._tableKeysFK)
                return false;
            if (this._has_tableKeysFK != temp._has_tableKeysFK)
                return false;
            if (this._age != temp._age)
                return false;
            if (this._has_age != temp._has_age)
                return false;
            if (this._duration != temp._duration)
                return false;
            if (this._has_duration != temp._has_duration)
                return false;
            if (this._rate != null) {
                if (temp._rate == null) return false;
                else if (!(this._rate.equals(temp._rate))) 
                    return false;
            }
            else if (temp._rate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgeReturns the value of field 'age'.
     * 
     * @return the value of field 'age'.
     */
    public int getAge()
    {
        return this._age;
    } //-- int getAge() 

    /**
     * Method getDurationReturns the value of field 'duration'.
     * 
     * @return the value of field 'duration'.
     */
    public int getDuration()
    {
        return this._duration;
    } //-- int getDuration() 

    /**
     * Method getRateReturns the value of field 'rate'.
     * 
     * @return the value of field 'rate'.
     */
    public java.math.BigDecimal getRate()
    {
        return this._rate;
    } //-- java.math.BigDecimal getRate() 

    /**
     * Method getRateTablePKReturns the value of field
     * 'rateTablePK'.
     * 
     * @return the value of field 'rateTablePK'.
     */
    public long getRateTablePK()
    {
        return this._rateTablePK;
    } //-- long getRateTablePK() 

    /**
     * Method getTableKeysFKReturns the value of field
     * 'tableKeysFK'.
     * 
     * @return the value of field 'tableKeysFK'.
     */
    public long getTableKeysFK()
    {
        return this._tableKeysFK;
    } //-- long getTableKeysFK() 

    /**
     * Method hasAge
     */
    public boolean hasAge()
    {
        return this._has_age;
    } //-- boolean hasAge() 

    /**
     * Method hasDuration
     */
    public boolean hasDuration()
    {
        return this._has_duration;
    } //-- boolean hasDuration() 

    /**
     * Method hasRateTablePK
     */
    public boolean hasRateTablePK()
    {
        return this._has_rateTablePK;
    } //-- boolean hasRateTablePK() 

    /**
     * Method hasTableKeysFK
     */
    public boolean hasTableKeysFK()
    {
        return this._has_tableKeysFK;
    } //-- boolean hasTableKeysFK() 

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
     * Method setAgeSets the value of field 'age'.
     * 
     * @param age the value of field 'age'.
     */
    public void setAge(int age)
    {
        this._age = age;
        
        super.setVoChanged(true);
        this._has_age = true;
    } //-- void setAge(int) 

    /**
     * Method setDurationSets the value of field 'duration'.
     * 
     * @param duration the value of field 'duration'.
     */
    public void setDuration(int duration)
    {
        this._duration = duration;
        
        super.setVoChanged(true);
        this._has_duration = true;
    } //-- void setDuration(int) 

    /**
     * Method setRateSets the value of field 'rate'.
     * 
     * @param rate the value of field 'rate'.
     */
    public void setRate(java.math.BigDecimal rate)
    {
        this._rate = rate;
        
        super.setVoChanged(true);
    } //-- void setRate(java.math.BigDecimal) 

    /**
     * Method setRateTablePKSets the value of field 'rateTablePK'.
     * 
     * @param rateTablePK the value of field 'rateTablePK'.
     */
    public void setRateTablePK(long rateTablePK)
    {
        this._rateTablePK = rateTablePK;
        
        super.setVoChanged(true);
        this._has_rateTablePK = true;
    } //-- void setRateTablePK(long) 

    /**
     * Method setTableKeysFKSets the value of field 'tableKeysFK'.
     * 
     * @param tableKeysFK the value of field 'tableKeysFK'.
     */
    public void setTableKeysFK(long tableKeysFK)
    {
        this._tableKeysFK = tableKeysFK;
        
        super.setVoChanged(true);
        this._has_tableKeysFK = true;
    } //-- void setTableKeysFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RateTableVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RateTableVO) Unmarshaller.unmarshal(edit.common.vo.RateTableVO.class, reader);
    } //-- edit.common.vo.RateTableVO unmarshal(java.io.Reader) 

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
