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
 * Class DetailRecord.
 * 
 * @version $Revision$ $Date$
 */
public class DetailRecord extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _price
     */
    private java.math.BigDecimal _price;

    /**
     * Field _netInvestFactors
     */
    private java.math.BigDecimal _netInvestFactors;

    /**
     * Field _units
     */
    private java.math.BigDecimal _units;

    /**
     * Field _netAssets
     */
    private java.math.BigDecimal _netAssets;

    /**
     * Field _fees
     */
    private java.math.BigDecimal _fees;

    /**
     * Field _feesPayable
     */
    private java.math.BigDecimal _feesPayable;


      //----------------/
     //- Constructors -/
    //----------------/

    public DetailRecord() {
        super();
    } //-- edit.common.vo.DetailRecord()


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
        
        if (obj instanceof DetailRecord) {
        
            DetailRecord temp = (DetailRecord)obj;
            if (this._price != null) {
                if (temp._price == null) return false;
                else if (!(this._price.equals(temp._price))) 
                    return false;
            }
            else if (temp._price != null)
                return false;
            if (this._netInvestFactors != null) {
                if (temp._netInvestFactors == null) return false;
                else if (!(this._netInvestFactors.equals(temp._netInvestFactors))) 
                    return false;
            }
            else if (temp._netInvestFactors != null)
                return false;
            if (this._units != null) {
                if (temp._units == null) return false;
                else if (!(this._units.equals(temp._units))) 
                    return false;
            }
            else if (temp._units != null)
                return false;
            if (this._netAssets != null) {
                if (temp._netAssets == null) return false;
                else if (!(this._netAssets.equals(temp._netAssets))) 
                    return false;
            }
            else if (temp._netAssets != null)
                return false;
            if (this._fees != null) {
                if (temp._fees == null) return false;
                else if (!(this._fees.equals(temp._fees))) 
                    return false;
            }
            else if (temp._fees != null)
                return false;
            if (this._feesPayable != null) {
                if (temp._feesPayable == null) return false;
                else if (!(this._feesPayable.equals(temp._feesPayable))) 
                    return false;
            }
            else if (temp._feesPayable != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFeesReturns the value of field 'fees'.
     * 
     * @return the value of field 'fees'.
     */
    public java.math.BigDecimal getFees()
    {
        return this._fees;
    } //-- java.math.BigDecimal getFees() 

    /**
     * Method getFeesPayableReturns the value of field
     * 'feesPayable'.
     * 
     * @return the value of field 'feesPayable'.
     */
    public java.math.BigDecimal getFeesPayable()
    {
        return this._feesPayable;
    } //-- java.math.BigDecimal getFeesPayable() 

    /**
     * Method getNetAssetsReturns the value of field 'netAssets'.
     * 
     * @return the value of field 'netAssets'.
     */
    public java.math.BigDecimal getNetAssets()
    {
        return this._netAssets;
    } //-- java.math.BigDecimal getNetAssets() 

    /**
     * Method getNetInvestFactorsReturns the value of field
     * 'netInvestFactors'.
     * 
     * @return the value of field 'netInvestFactors'.
     */
    public java.math.BigDecimal getNetInvestFactors()
    {
        return this._netInvestFactors;
    } //-- java.math.BigDecimal getNetInvestFactors() 

    /**
     * Method getPriceReturns the value of field 'price'.
     * 
     * @return the value of field 'price'.
     */
    public java.math.BigDecimal getPrice()
    {
        return this._price;
    } //-- java.math.BigDecimal getPrice() 

    /**
     * Method getUnitsReturns the value of field 'units'.
     * 
     * @return the value of field 'units'.
     */
    public java.math.BigDecimal getUnits()
    {
        return this._units;
    } //-- java.math.BigDecimal getUnits() 

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
     * Method setFeesSets the value of field 'fees'.
     * 
     * @param fees the value of field 'fees'.
     */
    public void setFees(java.math.BigDecimal fees)
    {
        this._fees = fees;
        
        super.setVoChanged(true);
    } //-- void setFees(java.math.BigDecimal) 

    /**
     * Method setFeesPayableSets the value of field 'feesPayable'.
     * 
     * @param feesPayable the value of field 'feesPayable'.
     */
    public void setFeesPayable(java.math.BigDecimal feesPayable)
    {
        this._feesPayable = feesPayable;
        
        super.setVoChanged(true);
    } //-- void setFeesPayable(java.math.BigDecimal) 

    /**
     * Method setNetAssetsSets the value of field 'netAssets'.
     * 
     * @param netAssets the value of field 'netAssets'.
     */
    public void setNetAssets(java.math.BigDecimal netAssets)
    {
        this._netAssets = netAssets;
        
        super.setVoChanged(true);
    } //-- void setNetAssets(java.math.BigDecimal) 

    /**
     * Method setNetInvestFactorsSets the value of field
     * 'netInvestFactors'.
     * 
     * @param netInvestFactors the value of field 'netInvestFactors'
     */
    public void setNetInvestFactors(java.math.BigDecimal netInvestFactors)
    {
        this._netInvestFactors = netInvestFactors;
        
        super.setVoChanged(true);
    } //-- void setNetInvestFactors(java.math.BigDecimal) 

    /**
     * Method setPriceSets the value of field 'price'.
     * 
     * @param price the value of field 'price'.
     */
    public void setPrice(java.math.BigDecimal price)
    {
        this._price = price;
        
        super.setVoChanged(true);
    } //-- void setPrice(java.math.BigDecimal) 

    /**
     * Method setUnitsSets the value of field 'units'.
     * 
     * @param units the value of field 'units'.
     */
    public void setUnits(java.math.BigDecimal units)
    {
        this._units = units;
        
        super.setVoChanged(true);
    } //-- void setUnits(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.DetailRecord unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.DetailRecord) Unmarshaller.unmarshal(edit.common.vo.DetailRecord.class, reader);
    } //-- edit.common.vo.DetailRecord unmarshal(java.io.Reader) 

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
