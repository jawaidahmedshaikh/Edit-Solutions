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
 * Class GIOOptionValueVO.
 * 
 * @version $Revision$ $Date$
 */
public class GIOOptionValueVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _GIOOptionValuePK
     */
    private long _GIOOptionValuePK;

    /**
     * keeps track of state for field: _GIOOptionValuePK
     */
    private boolean _has_GIOOptionValuePK;

    /**
     * Field _optionDate
     */
    private java.lang.String _optionDate;

    /**
     * Field _DBIncreaseAmount
     */
    private java.math.BigDecimal _DBIncreaseAmount;

    /**
     * Field _DBIncreaseAmountGuaranteed
     */
    private java.math.BigDecimal _DBIncreaseAmountGuaranteed;

    /**
     * Field _annualPremium
     */
    private java.math.BigDecimal _annualPremium;


      //----------------/
     //- Constructors -/
    //----------------/

    public GIOOptionValueVO() {
        super();
    } //-- edit.common.vo.GIOOptionValueVO()


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
        
        if (obj instanceof GIOOptionValueVO) {
        
            GIOOptionValueVO temp = (GIOOptionValueVO)obj;
            if (this._GIOOptionValuePK != temp._GIOOptionValuePK)
                return false;
            if (this._has_GIOOptionValuePK != temp._has_GIOOptionValuePK)
                return false;
            if (this._optionDate != null) {
                if (temp._optionDate == null) return false;
                else if (!(this._optionDate.equals(temp._optionDate))) 
                    return false;
            }
            else if (temp._optionDate != null)
                return false;
            if (this._DBIncreaseAmount != null) {
                if (temp._DBIncreaseAmount == null) return false;
                else if (!(this._DBIncreaseAmount.equals(temp._DBIncreaseAmount))) 
                    return false;
            }
            else if (temp._DBIncreaseAmount != null)
                return false;
            if (this._DBIncreaseAmountGuaranteed != null) {
                if (temp._DBIncreaseAmountGuaranteed == null) return false;
                else if (!(this._DBIncreaseAmountGuaranteed.equals(temp._DBIncreaseAmountGuaranteed))) 
                    return false;
            }
            else if (temp._DBIncreaseAmountGuaranteed != null)
                return false;
            if (this._annualPremium != null) {
                if (temp._annualPremium == null) return false;
                else if (!(this._annualPremium.equals(temp._annualPremium))) 
                    return false;
            }
            else if (temp._annualPremium != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAnnualPremiumReturns the value of field
     * 'annualPremium'.
     * 
     * @return the value of field 'annualPremium'.
     */
    public java.math.BigDecimal getAnnualPremium()
    {
        return this._annualPremium;
    } //-- java.math.BigDecimal getAnnualPremium() 

    /**
     * Method getDBIncreaseAmountReturns the value of field
     * 'DBIncreaseAmount'.
     * 
     * @return the value of field 'DBIncreaseAmount'.
     */
    public java.math.BigDecimal getDBIncreaseAmount()
    {
        return this._DBIncreaseAmount;
    } //-- java.math.BigDecimal getDBIncreaseAmount() 

    /**
     * Method getDBIncreaseAmountGuaranteedReturns the value of
     * field 'DBIncreaseAmountGuaranteed'.
     * 
     * @return the value of field 'DBIncreaseAmountGuaranteed'.
     */
    public java.math.BigDecimal getDBIncreaseAmountGuaranteed()
    {
        return this._DBIncreaseAmountGuaranteed;
    } //-- java.math.BigDecimal getDBIncreaseAmountGuaranteed() 

    /**
     * Method getGIOOptionValuePKReturns the value of field
     * 'GIOOptionValuePK'.
     * 
     * @return the value of field 'GIOOptionValuePK'.
     */
    public long getGIOOptionValuePK()
    {
        return this._GIOOptionValuePK;
    } //-- long getGIOOptionValuePK() 

    /**
     * Method getOptionDateReturns the value of field 'optionDate'.
     * 
     * @return the value of field 'optionDate'.
     */
    public java.lang.String getOptionDate()
    {
        return this._optionDate;
    } //-- java.lang.String getOptionDate() 

    /**
     * Method hasGIOOptionValuePK
     */
    public boolean hasGIOOptionValuePK()
    {
        return this._has_GIOOptionValuePK;
    } //-- boolean hasGIOOptionValuePK() 

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
     * Method setAnnualPremiumSets the value of field
     * 'annualPremium'.
     * 
     * @param annualPremium the value of field 'annualPremium'.
     */
    public void setAnnualPremium(java.math.BigDecimal annualPremium)
    {
        this._annualPremium = annualPremium;
        
        super.setVoChanged(true);
    } //-- void setAnnualPremium(java.math.BigDecimal) 

    /**
     * Method setDBIncreaseAmountSets the value of field
     * 'DBIncreaseAmount'.
     * 
     * @param DBIncreaseAmount the value of field 'DBIncreaseAmount'
     */
    public void setDBIncreaseAmount(java.math.BigDecimal DBIncreaseAmount)
    {
        this._DBIncreaseAmount = DBIncreaseAmount;
        
        super.setVoChanged(true);
    } //-- void setDBIncreaseAmount(java.math.BigDecimal) 

    /**
     * Method setDBIncreaseAmountGuaranteedSets the value of field
     * 'DBIncreaseAmountGuaranteed'.
     * 
     * @param DBIncreaseAmountGuaranteed the value of field
     * 'DBIncreaseAmountGuaranteed'.
     */
    public void setDBIncreaseAmountGuaranteed(java.math.BigDecimal DBIncreaseAmountGuaranteed)
    {
        this._DBIncreaseAmountGuaranteed = DBIncreaseAmountGuaranteed;
        
        super.setVoChanged(true);
    } //-- void setDBIncreaseAmountGuaranteed(java.math.BigDecimal) 

    /**
     * Method setGIOOptionValuePKSets the value of field
     * 'GIOOptionValuePK'.
     * 
     * @param GIOOptionValuePK the value of field 'GIOOptionValuePK'
     */
    public void setGIOOptionValuePK(long GIOOptionValuePK)
    {
        this._GIOOptionValuePK = GIOOptionValuePK;
        
        super.setVoChanged(true);
        this._has_GIOOptionValuePK = true;
    } //-- void setGIOOptionValuePK(long) 

    /**
     * Method setOptionDateSets the value of field 'optionDate'.
     * 
     * @param optionDate the value of field 'optionDate'.
     */
    public void setOptionDate(java.lang.String optionDate)
    {
        this._optionDate = optionDate;
        
        super.setVoChanged(true);
    } //-- void setOptionDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.GIOOptionValueVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.GIOOptionValueVO) Unmarshaller.unmarshal(edit.common.vo.GIOOptionValueVO.class, reader);
    } //-- edit.common.vo.GIOOptionValueVO unmarshal(java.io.Reader) 

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
