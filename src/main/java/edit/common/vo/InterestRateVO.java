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
 * Class InterestRateVO.
 * 
 * @version $Revision$ $Date$
 */
public class InterestRateVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _interestRatePK
     */
    private long _interestRatePK;

    /**
     * keeps track of state for field: _interestRatePK
     */
    private boolean _has_interestRatePK;

    /**
     * Field _interestRateParametersFK
     */
    private long _interestRateParametersFK;

    /**
     * keeps track of state for field: _interestRateParametersFK
     */
    private boolean _has_interestRateParametersFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _guaranteeDuration
     */
    private java.lang.String _guaranteeDuration;

    /**
     * Field _rate
     */
    private java.math.BigDecimal _rate;


      //----------------/
     //- Constructors -/
    //----------------/

    public InterestRateVO() {
        super();
    } //-- edit.common.vo.InterestRateVO()


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
        
        if (obj instanceof InterestRateVO) {
        
            InterestRateVO temp = (InterestRateVO)obj;
            if (this._interestRatePK != temp._interestRatePK)
                return false;
            if (this._has_interestRatePK != temp._has_interestRatePK)
                return false;
            if (this._interestRateParametersFK != temp._interestRateParametersFK)
                return false;
            if (this._has_interestRateParametersFK != temp._has_interestRateParametersFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._guaranteeDuration != null) {
                if (temp._guaranteeDuration == null) return false;
                else if (!(this._guaranteeDuration.equals(temp._guaranteeDuration))) 
                    return false;
            }
            else if (temp._guaranteeDuration != null)
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
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getGuaranteeDurationReturns the value of field
     * 'guaranteeDuration'.
     * 
     * @return the value of field 'guaranteeDuration'.
     */
    public java.lang.String getGuaranteeDuration()
    {
        return this._guaranteeDuration;
    } //-- java.lang.String getGuaranteeDuration() 

    /**
     * Method getInterestRatePKReturns the value of field
     * 'interestRatePK'.
     * 
     * @return the value of field 'interestRatePK'.
     */
    public long getInterestRatePK()
    {
        return this._interestRatePK;
    } //-- long getInterestRatePK() 

    /**
     * Method getInterestRateParametersFKReturns the value of field
     * 'interestRateParametersFK'.
     * 
     * @return the value of field 'interestRateParametersFK'.
     */
    public long getInterestRateParametersFK()
    {
        return this._interestRateParametersFK;
    } //-- long getInterestRateParametersFK() 

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
     * Method hasInterestRatePK
     */
    public boolean hasInterestRatePK()
    {
        return this._has_interestRatePK;
    } //-- boolean hasInterestRatePK() 

    /**
     * Method hasInterestRateParametersFK
     */
    public boolean hasInterestRateParametersFK()
    {
        return this._has_interestRateParametersFK;
    } //-- boolean hasInterestRateParametersFK() 

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
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setGuaranteeDurationSets the value of field
     * 'guaranteeDuration'.
     * 
     * @param guaranteeDuration the value of field
     * 'guaranteeDuration'.
     */
    public void setGuaranteeDuration(java.lang.String guaranteeDuration)
    {
        this._guaranteeDuration = guaranteeDuration;
        
        super.setVoChanged(true);
    } //-- void setGuaranteeDuration(java.lang.String) 

    /**
     * Method setInterestRatePKSets the value of field
     * 'interestRatePK'.
     * 
     * @param interestRatePK the value of field 'interestRatePK'.
     */
    public void setInterestRatePK(long interestRatePK)
    {
        this._interestRatePK = interestRatePK;
        
        super.setVoChanged(true);
        this._has_interestRatePK = true;
    } //-- void setInterestRatePK(long) 

    /**
     * Method setInterestRateParametersFKSets the value of field
     * 'interestRateParametersFK'.
     * 
     * @param interestRateParametersFK the value of field
     * 'interestRateParametersFK'.
     */
    public void setInterestRateParametersFK(long interestRateParametersFK)
    {
        this._interestRateParametersFK = interestRateParametersFK;
        
        super.setVoChanged(true);
        this._has_interestRateParametersFK = true;
    } //-- void setInterestRateParametersFK(long) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InterestRateVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InterestRateVO) Unmarshaller.unmarshal(edit.common.vo.InterestRateVO.class, reader);
    } //-- edit.common.vo.InterestRateVO unmarshal(java.io.Reader) 

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
