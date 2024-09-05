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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class TaxFormVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _taxFormName
     */
    private java.lang.String _taxFormName;

    /**
     * Field _premiumType
     */
    private java.lang.String _premiumType;

    /**
     * Field _distributionCode
     */
    private java.lang.String _distributionCode;

    /**
     * Field _grossAmount
     */
    private double _grossAmount;

    /**
     * keeps track of state for field: _grossAmount
     */
    private boolean _has_grossAmount;

    /**
     * Field _taxableBenefit
     */
    private double _taxableBenefit;

    /**
     * keeps track of state for field: _taxableBenefit
     */
    private boolean _has_taxableBenefit;

    /**
     * Field _federalWithholding
     */
    private double _federalWithholding;

    /**
     * keeps track of state for field: _federalWithholding
     */
    private boolean _has_federalWithholding;

    /**
     * Field _stateWithholding
     */
    private double _stateWithholding;

    /**
     * keeps track of state for field: _stateWithholding
     */
    private boolean _has_stateWithholding;

    /**
     * Field _countyWithholding
     */
    private double _countyWithholding;

    /**
     * keeps track of state for field: _countyWithholding
     */
    private boolean _has_countyWithholding;

    /**
     * Field _cityWithholding
     */
    private double _cityWithholding;

    /**
     * keeps track of state for field: _cityWithholding
     */
    private boolean _has_cityWithholding;

    /**
     * Field _marketValue
     */
    private double _marketValue;

    /**
     * keeps track of state for field: _marketValue
     */
    private boolean _has_marketValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaxFormVO() {
        super();
    } //-- edit.common.vo.TaxFormVO()


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
        
        if (obj instanceof TaxFormVO) {
        
            TaxFormVO temp = (TaxFormVO)obj;
            if (this._taxFormName != null) {
                if (temp._taxFormName == null) return false;
                else if (!(this._taxFormName.equals(temp._taxFormName))) 
                    return false;
            }
            else if (temp._taxFormName != null)
                return false;
            if (this._premiumType != null) {
                if (temp._premiumType == null) return false;
                else if (!(this._premiumType.equals(temp._premiumType))) 
                    return false;
            }
            else if (temp._premiumType != null)
                return false;
            if (this._distributionCode != null) {
                if (temp._distributionCode == null) return false;
                else if (!(this._distributionCode.equals(temp._distributionCode))) 
                    return false;
            }
            else if (temp._distributionCode != null)
                return false;
            if (this._grossAmount != temp._grossAmount)
                return false;
            if (this._has_grossAmount != temp._has_grossAmount)
                return false;
            if (this._taxableBenefit != temp._taxableBenefit)
                return false;
            if (this._has_taxableBenefit != temp._has_taxableBenefit)
                return false;
            if (this._federalWithholding != temp._federalWithholding)
                return false;
            if (this._has_federalWithholding != temp._has_federalWithholding)
                return false;
            if (this._stateWithholding != temp._stateWithholding)
                return false;
            if (this._has_stateWithholding != temp._has_stateWithholding)
                return false;
            if (this._countyWithholding != temp._countyWithholding)
                return false;
            if (this._has_countyWithholding != temp._has_countyWithholding)
                return false;
            if (this._cityWithholding != temp._cityWithholding)
                return false;
            if (this._has_cityWithholding != temp._has_cityWithholding)
                return false;
            if (this._marketValue != temp._marketValue)
                return false;
            if (this._has_marketValue != temp._has_marketValue)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCityWithholdingReturns the value of field
     * 'cityWithholding'.
     * 
     * @return the value of field 'cityWithholding'.
     */
    public double getCityWithholding()
    {
        return this._cityWithholding;
    } //-- double getCityWithholding() 

    /**
     * Method getCountyWithholdingReturns the value of field
     * 'countyWithholding'.
     * 
     * @return the value of field 'countyWithholding'.
     */
    public double getCountyWithholding()
    {
        return this._countyWithholding;
    } //-- double getCountyWithholding() 

    /**
     * Method getDistributionCodeReturns the value of field
     * 'distributionCode'.
     * 
     * @return the value of field 'distributionCode'.
     */
    public java.lang.String getDistributionCode()
    {
        return this._distributionCode;
    } //-- java.lang.String getDistributionCode() 

    /**
     * Method getFederalWithholdingReturns the value of field
     * 'federalWithholding'.
     * 
     * @return the value of field 'federalWithholding'.
     */
    public double getFederalWithholding()
    {
        return this._federalWithholding;
    } //-- double getFederalWithholding() 

    /**
     * Method getGrossAmountReturns the value of field
     * 'grossAmount'.
     * 
     * @return the value of field 'grossAmount'.
     */
    public double getGrossAmount()
    {
        return this._grossAmount;
    } //-- double getGrossAmount() 

    /**
     * Method getMarketValueReturns the value of field
     * 'marketValue'.
     * 
     * @return the value of field 'marketValue'.
     */
    public double getMarketValue()
    {
        return this._marketValue;
    } //-- double getMarketValue() 

    /**
     * Method getPremiumTypeReturns the value of field
     * 'premiumType'.
     * 
     * @return the value of field 'premiumType'.
     */
    public java.lang.String getPremiumType()
    {
        return this._premiumType;
    } //-- java.lang.String getPremiumType() 

    /**
     * Method getStateWithholdingReturns the value of field
     * 'stateWithholding'.
     * 
     * @return the value of field 'stateWithholding'.
     */
    public double getStateWithholding()
    {
        return this._stateWithholding;
    } //-- double getStateWithholding() 

    /**
     * Method getTaxFormNameReturns the value of field
     * 'taxFormName'.
     * 
     * @return the value of field 'taxFormName'.
     */
    public java.lang.String getTaxFormName()
    {
        return this._taxFormName;
    } //-- java.lang.String getTaxFormName() 

    /**
     * Method getTaxableBenefitReturns the value of field
     * 'taxableBenefit'.
     * 
     * @return the value of field 'taxableBenefit'.
     */
    public double getTaxableBenefit()
    {
        return this._taxableBenefit;
    } //-- double getTaxableBenefit() 

    /**
     * Method hasCityWithholding
     */
    public boolean hasCityWithholding()
    {
        return this._has_cityWithholding;
    } //-- boolean hasCityWithholding() 

    /**
     * Method hasCountyWithholding
     */
    public boolean hasCountyWithholding()
    {
        return this._has_countyWithholding;
    } //-- boolean hasCountyWithholding() 

    /**
     * Method hasFederalWithholding
     */
    public boolean hasFederalWithholding()
    {
        return this._has_federalWithholding;
    } //-- boolean hasFederalWithholding() 

    /**
     * Method hasGrossAmount
     */
    public boolean hasGrossAmount()
    {
        return this._has_grossAmount;
    } //-- boolean hasGrossAmount() 

    /**
     * Method hasMarketValue
     */
    public boolean hasMarketValue()
    {
        return this._has_marketValue;
    } //-- boolean hasMarketValue() 

    /**
     * Method hasStateWithholding
     */
    public boolean hasStateWithholding()
    {
        return this._has_stateWithholding;
    } //-- boolean hasStateWithholding() 

    /**
     * Method hasTaxableBenefit
     */
    public boolean hasTaxableBenefit()
    {
        return this._has_taxableBenefit;
    } //-- boolean hasTaxableBenefit() 

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
     * Method setCityWithholdingSets the value of field
     * 'cityWithholding'.
     * 
     * @param cityWithholding the value of field 'cityWithholding'.
     */
    public void setCityWithholding(double cityWithholding)
    {
        this._cityWithholding = cityWithholding;
        
        super.setVoChanged(true);
        this._has_cityWithholding = true;
    } //-- void setCityWithholding(double) 

    /**
     * Method setCountyWithholdingSets the value of field
     * 'countyWithholding'.
     * 
     * @param countyWithholding the value of field
     * 'countyWithholding'.
     */
    public void setCountyWithholding(double countyWithholding)
    {
        this._countyWithholding = countyWithholding;
        
        super.setVoChanged(true);
        this._has_countyWithholding = true;
    } //-- void setCountyWithholding(double) 

    /**
     * Method setDistributionCodeSets the value of field
     * 'distributionCode'.
     * 
     * @param distributionCode the value of field 'distributionCode'
     */
    public void setDistributionCode(java.lang.String distributionCode)
    {
        this._distributionCode = distributionCode;
        
        super.setVoChanged(true);
    } //-- void setDistributionCode(java.lang.String) 

    /**
     * Method setFederalWithholdingSets the value of field
     * 'federalWithholding'.
     * 
     * @param federalWithholding the value of field
     * 'federalWithholding'.
     */
    public void setFederalWithholding(double federalWithholding)
    {
        this._federalWithholding = federalWithholding;
        
        super.setVoChanged(true);
        this._has_federalWithholding = true;
    } //-- void setFederalWithholding(double) 

    /**
     * Method setGrossAmountSets the value of field 'grossAmount'.
     * 
     * @param grossAmount the value of field 'grossAmount'.
     */
    public void setGrossAmount(double grossAmount)
    {
        this._grossAmount = grossAmount;
        
        super.setVoChanged(true);
        this._has_grossAmount = true;
    } //-- void setGrossAmount(double) 

    /**
     * Method setMarketValueSets the value of field 'marketValue'.
     * 
     * @param marketValue the value of field 'marketValue'.
     */
    public void setMarketValue(double marketValue)
    {
        this._marketValue = marketValue;
        
        super.setVoChanged(true);
        this._has_marketValue = true;
    } //-- void setMarketValue(double) 

    /**
     * Method setPremiumTypeSets the value of field 'premiumType'.
     * 
     * @param premiumType the value of field 'premiumType'.
     */
    public void setPremiumType(java.lang.String premiumType)
    {
        this._premiumType = premiumType;
        
        super.setVoChanged(true);
    } //-- void setPremiumType(java.lang.String) 

    /**
     * Method setStateWithholdingSets the value of field
     * 'stateWithholding'.
     * 
     * @param stateWithholding the value of field 'stateWithholding'
     */
    public void setStateWithholding(double stateWithholding)
    {
        this._stateWithholding = stateWithholding;
        
        super.setVoChanged(true);
        this._has_stateWithholding = true;
    } //-- void setStateWithholding(double) 

    /**
     * Method setTaxFormNameSets the value of field 'taxFormName'.
     * 
     * @param taxFormName the value of field 'taxFormName'.
     */
    public void setTaxFormName(java.lang.String taxFormName)
    {
        this._taxFormName = taxFormName;
        
        super.setVoChanged(true);
    } //-- void setTaxFormName(java.lang.String) 

    /**
     * Method setTaxableBenefitSets the value of field
     * 'taxableBenefit'.
     * 
     * @param taxableBenefit the value of field 'taxableBenefit'.
     */
    public void setTaxableBenefit(double taxableBenefit)
    {
        this._taxableBenefit = taxableBenefit;
        
        super.setVoChanged(true);
        this._has_taxableBenefit = true;
    } //-- void setTaxableBenefit(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TaxFormVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TaxFormVO) Unmarshaller.unmarshal(edit.common.vo.TaxFormVO.class, reader);
    } //-- edit.common.vo.TaxFormVO unmarshal(java.io.Reader) 

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
