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
 * Class QuoteAdjustmentVO.
 * 
 * @version $Revision$ $Date$
 */
public class QuoteAdjustmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _chargeTypeCT
     */
    private java.lang.String _chargeTypeCT;

    /**
     * Field _chargeAmount
     */
    private java.math.BigDecimal _chargeAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public QuoteAdjustmentVO() {
        super();
    } //-- edit.common.vo.QuoteAdjustmentVO()


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
        
        if (obj instanceof QuoteAdjustmentVO) {
        
            QuoteAdjustmentVO temp = (QuoteAdjustmentVO)obj;
            if (this._chargeTypeCT != null) {
                if (temp._chargeTypeCT == null) return false;
                else if (!(this._chargeTypeCT.equals(temp._chargeTypeCT))) 
                    return false;
            }
            else if (temp._chargeTypeCT != null)
                return false;
            if (this._chargeAmount != null) {
                if (temp._chargeAmount == null) return false;
                else if (!(this._chargeAmount.equals(temp._chargeAmount))) 
                    return false;
            }
            else if (temp._chargeAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.QuoteAdjustmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.QuoteAdjustmentVO) Unmarshaller.unmarshal(edit.common.vo.QuoteAdjustmentVO.class, reader);
    } //-- edit.common.vo.QuoteAdjustmentVO unmarshal(java.io.Reader) 

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
