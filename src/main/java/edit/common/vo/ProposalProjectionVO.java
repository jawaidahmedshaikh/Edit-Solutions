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
 * Class ProposalProjectionVO.
 * 
 * @version $Revision$ $Date$
 */
public class ProposalProjectionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _endOfYear
     */
    private int _endOfYear;

    /**
     * keeps track of state for field: _endOfYear
     */
    private boolean _has_endOfYear;

    /**
     * Field _date
     */
    private java.lang.String _date;

    /**
     * Field _age
     */
    private int _age;

    /**
     * keeps track of state for field: _age
     */
    private boolean _has_age;

    /**
     * Field _premiumsReceived
     */
    private java.math.BigDecimal _premiumsReceived;

    /**
     * Field _charge
     */
    private java.math.BigDecimal _charge;

    /**
     * Field _deathBenefit
     */
    private java.math.BigDecimal _deathBenefit;

    /**
     * Field _nonGuarProjectedFundValue
     */
    private java.math.BigDecimal _nonGuarProjectedFundValue;

    /**
     * Field _guaranteedDeathBenefit
     */
    private java.math.BigDecimal _guaranteedDeathBenefit;

    /**
     * Field _guaranteedFundValue
     */
    private java.math.BigDecimal _guaranteedFundValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProposalProjectionVO() {
        super();
    } //-- edit.common.vo.ProposalProjectionVO()


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
        
        if (obj instanceof ProposalProjectionVO) {
        
            ProposalProjectionVO temp = (ProposalProjectionVO)obj;
            if (this._endOfYear != temp._endOfYear)
                return false;
            if (this._has_endOfYear != temp._has_endOfYear)
                return false;
            if (this._date != null) {
                if (temp._date == null) return false;
                else if (!(this._date.equals(temp._date))) 
                    return false;
            }
            else if (temp._date != null)
                return false;
            if (this._age != temp._age)
                return false;
            if (this._has_age != temp._has_age)
                return false;
            if (this._premiumsReceived != null) {
                if (temp._premiumsReceived == null) return false;
                else if (!(this._premiumsReceived.equals(temp._premiumsReceived))) 
                    return false;
            }
            else if (temp._premiumsReceived != null)
                return false;
            if (this._charge != null) {
                if (temp._charge == null) return false;
                else if (!(this._charge.equals(temp._charge))) 
                    return false;
            }
            else if (temp._charge != null)
                return false;
            if (this._deathBenefit != null) {
                if (temp._deathBenefit == null) return false;
                else if (!(this._deathBenefit.equals(temp._deathBenefit))) 
                    return false;
            }
            else if (temp._deathBenefit != null)
                return false;
            if (this._nonGuarProjectedFundValue != null) {
                if (temp._nonGuarProjectedFundValue == null) return false;
                else if (!(this._nonGuarProjectedFundValue.equals(temp._nonGuarProjectedFundValue))) 
                    return false;
            }
            else if (temp._nonGuarProjectedFundValue != null)
                return false;
            if (this._guaranteedDeathBenefit != null) {
                if (temp._guaranteedDeathBenefit == null) return false;
                else if (!(this._guaranteedDeathBenefit.equals(temp._guaranteedDeathBenefit))) 
                    return false;
            }
            else if (temp._guaranteedDeathBenefit != null)
                return false;
            if (this._guaranteedFundValue != null) {
                if (temp._guaranteedFundValue == null) return false;
                else if (!(this._guaranteedFundValue.equals(temp._guaranteedFundValue))) 
                    return false;
            }
            else if (temp._guaranteedFundValue != null)
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
     * Method getChargeReturns the value of field 'charge'.
     * 
     * @return the value of field 'charge'.
     */
    public java.math.BigDecimal getCharge()
    {
        return this._charge;
    } //-- java.math.BigDecimal getCharge() 

    /**
     * Method getDateReturns the value of field 'date'.
     * 
     * @return the value of field 'date'.
     */
    public java.lang.String getDate()
    {
        return this._date;
    } //-- java.lang.String getDate() 

    /**
     * Method getDeathBenefitReturns the value of field
     * 'deathBenefit'.
     * 
     * @return the value of field 'deathBenefit'.
     */
    public java.math.BigDecimal getDeathBenefit()
    {
        return this._deathBenefit;
    } //-- java.math.BigDecimal getDeathBenefit() 

    /**
     * Method getEndOfYearReturns the value of field 'endOfYear'.
     * 
     * @return the value of field 'endOfYear'.
     */
    public int getEndOfYear()
    {
        return this._endOfYear;
    } //-- int getEndOfYear() 

    /**
     * Method getGuaranteedDeathBenefitReturns the value of field
     * 'guaranteedDeathBenefit'.
     * 
     * @return the value of field 'guaranteedDeathBenefit'.
     */
    public java.math.BigDecimal getGuaranteedDeathBenefit()
    {
        return this._guaranteedDeathBenefit;
    } //-- java.math.BigDecimal getGuaranteedDeathBenefit() 

    /**
     * Method getGuaranteedFundValueReturns the value of field
     * 'guaranteedFundValue'.
     * 
     * @return the value of field 'guaranteedFundValue'.
     */
    public java.math.BigDecimal getGuaranteedFundValue()
    {
        return this._guaranteedFundValue;
    } //-- java.math.BigDecimal getGuaranteedFundValue() 

    /**
     * Method getNonGuarProjectedFundValueReturns the value of
     * field 'nonGuarProjectedFundValue'.
     * 
     * @return the value of field 'nonGuarProjectedFundValue'.
     */
    public java.math.BigDecimal getNonGuarProjectedFundValue()
    {
        return this._nonGuarProjectedFundValue;
    } //-- java.math.BigDecimal getNonGuarProjectedFundValue() 

    /**
     * Method getPremiumsReceivedReturns the value of field
     * 'premiumsReceived'.
     * 
     * @return the value of field 'premiumsReceived'.
     */
    public java.math.BigDecimal getPremiumsReceived()
    {
        return this._premiumsReceived;
    } //-- java.math.BigDecimal getPremiumsReceived() 

    /**
     * Method hasAge
     */
    public boolean hasAge()
    {
        return this._has_age;
    } //-- boolean hasAge() 

    /**
     * Method hasEndOfYear
     */
    public boolean hasEndOfYear()
    {
        return this._has_endOfYear;
    } //-- boolean hasEndOfYear() 

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
     * Method setChargeSets the value of field 'charge'.
     * 
     * @param charge the value of field 'charge'.
     */
    public void setCharge(java.math.BigDecimal charge)
    {
        this._charge = charge;
        
        super.setVoChanged(true);
    } //-- void setCharge(java.math.BigDecimal) 

    /**
     * Method setDateSets the value of field 'date'.
     * 
     * @param date the value of field 'date'.
     */
    public void setDate(java.lang.String date)
    {
        this._date = date;
        
        super.setVoChanged(true);
    } //-- void setDate(java.lang.String) 

    /**
     * Method setDeathBenefitSets the value of field
     * 'deathBenefit'.
     * 
     * @param deathBenefit the value of field 'deathBenefit'.
     */
    public void setDeathBenefit(java.math.BigDecimal deathBenefit)
    {
        this._deathBenefit = deathBenefit;
        
        super.setVoChanged(true);
    } //-- void setDeathBenefit(java.math.BigDecimal) 

    /**
     * Method setEndOfYearSets the value of field 'endOfYear'.
     * 
     * @param endOfYear the value of field 'endOfYear'.
     */
    public void setEndOfYear(int endOfYear)
    {
        this._endOfYear = endOfYear;
        
        super.setVoChanged(true);
        this._has_endOfYear = true;
    } //-- void setEndOfYear(int) 

    /**
     * Method setGuaranteedDeathBenefitSets the value of field
     * 'guaranteedDeathBenefit'.
     * 
     * @param guaranteedDeathBenefit the value of field
     * 'guaranteedDeathBenefit'.
     */
    public void setGuaranteedDeathBenefit(java.math.BigDecimal guaranteedDeathBenefit)
    {
        this._guaranteedDeathBenefit = guaranteedDeathBenefit;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedDeathBenefit(java.math.BigDecimal) 

    /**
     * Method setGuaranteedFundValueSets the value of field
     * 'guaranteedFundValue'.
     * 
     * @param guaranteedFundValue the value of field
     * 'guaranteedFundValue'.
     */
    public void setGuaranteedFundValue(java.math.BigDecimal guaranteedFundValue)
    {
        this._guaranteedFundValue = guaranteedFundValue;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedFundValue(java.math.BigDecimal) 

    /**
     * Method setNonGuarProjectedFundValueSets the value of field
     * 'nonGuarProjectedFundValue'.
     * 
     * @param nonGuarProjectedFundValue the value of field
     * 'nonGuarProjectedFundValue'.
     */
    public void setNonGuarProjectedFundValue(java.math.BigDecimal nonGuarProjectedFundValue)
    {
        this._nonGuarProjectedFundValue = nonGuarProjectedFundValue;
        
        super.setVoChanged(true);
    } //-- void setNonGuarProjectedFundValue(java.math.BigDecimal) 

    /**
     * Method setPremiumsReceivedSets the value of field
     * 'premiumsReceived'.
     * 
     * @param premiumsReceived the value of field 'premiumsReceived'
     */
    public void setPremiumsReceived(java.math.BigDecimal premiumsReceived)
    {
        this._premiumsReceived = premiumsReceived;
        
        super.setVoChanged(true);
    } //-- void setPremiumsReceived(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ProposalProjectionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ProposalProjectionVO) Unmarshaller.unmarshal(edit.common.vo.ProposalProjectionVO.class, reader);
    } //-- edit.common.vo.ProposalProjectionVO unmarshal(java.io.Reader) 

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
