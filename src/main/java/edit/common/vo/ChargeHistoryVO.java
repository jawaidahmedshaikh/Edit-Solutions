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
 * Class ChargeHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class ChargeHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _chargeHistoryPK
     */
    private long _chargeHistoryPK;

    /**
     * keeps track of state for field: _chargeHistoryPK
     */
    private boolean _has_chargeHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _chargeAmount
     */
    private java.math.BigDecimal _chargeAmount;

    /**
     * Field _chargeTypeCT
     */
    private java.lang.String _chargeTypeCT;

    /**
     * Field _adjustmentTaxable
     */
    private java.math.BigDecimal _adjustmentTaxable;

    /**
     * Field _adjustmentNonTaxable
     */
    private java.math.BigDecimal _adjustmentNonTaxable;


      //----------------/
     //- Constructors -/
    //----------------/

    public ChargeHistoryVO() {
        super();
    } //-- edit.common.vo.ChargeHistoryVO()


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
        
        if (obj instanceof ChargeHistoryVO) {
        
            ChargeHistoryVO temp = (ChargeHistoryVO)obj;
            if (this._chargeHistoryPK != temp._chargeHistoryPK)
                return false;
            if (this._has_chargeHistoryPK != temp._has_chargeHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._chargeAmount != null) {
                if (temp._chargeAmount == null) return false;
                else if (!(this._chargeAmount.equals(temp._chargeAmount))) 
                    return false;
            }
            else if (temp._chargeAmount != null)
                return false;
            if (this._chargeTypeCT != null) {
                if (temp._chargeTypeCT == null) return false;
                else if (!(this._chargeTypeCT.equals(temp._chargeTypeCT))) 
                    return false;
            }
            else if (temp._chargeTypeCT != null)
                return false;
            if (this._adjustmentTaxable != null) {
                if (temp._adjustmentTaxable == null) return false;
                else if (!(this._adjustmentTaxable.equals(temp._adjustmentTaxable))) 
                    return false;
            }
            else if (temp._adjustmentTaxable != null)
                return false;
            if (this._adjustmentNonTaxable != null) {
                if (temp._adjustmentNonTaxable == null) return false;
                else if (!(this._adjustmentNonTaxable.equals(temp._adjustmentNonTaxable))) 
                    return false;
            }
            else if (temp._adjustmentNonTaxable != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdjustmentNonTaxableReturns the value of field
     * 'adjustmentNonTaxable'.
     * 
     * @return the value of field 'adjustmentNonTaxable'.
     */
    public java.math.BigDecimal getAdjustmentNonTaxable()
    {
        return this._adjustmentNonTaxable;
    } //-- java.math.BigDecimal getAdjustmentNonTaxable() 

    /**
     * Method getAdjustmentTaxableReturns the value of field
     * 'adjustmentTaxable'.
     * 
     * @return the value of field 'adjustmentTaxable'.
     */
    public java.math.BigDecimal getAdjustmentTaxable()
    {
        return this._adjustmentTaxable;
    } //-- java.math.BigDecimal getAdjustmentTaxable() 

    /**
     * Method getChargeAmountReturns the value of field
     * 'chargeAmount'.
     * 
     * @return the value of field 'chargeAmount'.
     */
    public java.math.BigDecimal getChargeAmount()
    {
        return this._chargeAmount;
    } //-- java.math.BigDecimal getChargeAmount() 

    /**
     * Method getChargeHistoryPKReturns the value of field
     * 'chargeHistoryPK'.
     * 
     * @return the value of field 'chargeHistoryPK'.
     */
    public long getChargeHistoryPK()
    {
        return this._chargeHistoryPK;
    } //-- long getChargeHistoryPK() 

    /**
     * Method getChargeTypeCTReturns the value of field
     * 'chargeTypeCT'.
     * 
     * @return the value of field 'chargeTypeCT'.
     */
    public java.lang.String getChargeTypeCT()
    {
        return this._chargeTypeCT;
    } //-- java.lang.String getChargeTypeCT() 

    /**
     * Method getEDITTrxHistoryFKReturns the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @return the value of field 'EDITTrxHistoryFK'.
     */
    public long getEDITTrxHistoryFK()
    {
        return this._EDITTrxHistoryFK;
    } //-- long getEDITTrxHistoryFK() 

    /**
     * Method hasChargeHistoryPK
     */
    public boolean hasChargeHistoryPK()
    {
        return this._has_chargeHistoryPK;
    } //-- boolean hasChargeHistoryPK() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

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
     * Method setAdjustmentNonTaxableSets the value of field
     * 'adjustmentNonTaxable'.
     * 
     * @param adjustmentNonTaxable the value of field
     * 'adjustmentNonTaxable'.
     */
    public void setAdjustmentNonTaxable(java.math.BigDecimal adjustmentNonTaxable)
    {
        this._adjustmentNonTaxable = adjustmentNonTaxable;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentNonTaxable(java.math.BigDecimal) 

    /**
     * Method setAdjustmentTaxableSets the value of field
     * 'adjustmentTaxable'.
     * 
     * @param adjustmentTaxable the value of field
     * 'adjustmentTaxable'.
     */
    public void setAdjustmentTaxable(java.math.BigDecimal adjustmentTaxable)
    {
        this._adjustmentTaxable = adjustmentTaxable;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentTaxable(java.math.BigDecimal) 

    /**
     * Method setChargeAmountSets the value of field
     * 'chargeAmount'.
     * 
     * @param chargeAmount the value of field 'chargeAmount'.
     */
    public void setChargeAmount(java.math.BigDecimal chargeAmount)
    {
        this._chargeAmount = chargeAmount;
        
        super.setVoChanged(true);
    } //-- void setChargeAmount(java.math.BigDecimal) 

    /**
     * Method setChargeHistoryPKSets the value of field
     * 'chargeHistoryPK'.
     * 
     * @param chargeHistoryPK the value of field 'chargeHistoryPK'.
     */
    public void setChargeHistoryPK(long chargeHistoryPK)
    {
        this._chargeHistoryPK = chargeHistoryPK;
        
        super.setVoChanged(true);
        this._has_chargeHistoryPK = true;
    } //-- void setChargeHistoryPK(long) 

    /**
     * Method setChargeTypeCTSets the value of field
     * 'chargeTypeCT'.
     * 
     * @param chargeTypeCT the value of field 'chargeTypeCT'.
     */
    public void setChargeTypeCT(java.lang.String chargeTypeCT)
    {
        this._chargeTypeCT = chargeTypeCT;
        
        super.setVoChanged(true);
    } //-- void setChargeTypeCT(java.lang.String) 

    /**
     * Method setEDITTrxHistoryFKSets the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @param EDITTrxHistoryFK the value of field 'EDITTrxHistoryFK'
     */
    public void setEDITTrxHistoryFK(long EDITTrxHistoryFK)
    {
        this._EDITTrxHistoryFK = EDITTrxHistoryFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxHistoryFK = true;
    } //-- void setEDITTrxHistoryFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ChargeHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ChargeHistoryVO) Unmarshaller.unmarshal(edit.common.vo.ChargeHistoryVO.class, reader);
    } //-- edit.common.vo.ChargeHistoryVO unmarshal(java.io.Reader) 

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
