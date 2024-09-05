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
 * Class ChargeVO.
 * 
 * @version $Revision$ $Date$
 */
public class ChargeVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _chargePK
     */
    private long _chargePK;

    /**
     * keeps track of state for field: _chargePK
     */
    private boolean _has_chargePK;

    /**
     * Field _groupSetupFK
     */
    private long _groupSetupFK;

    /**
     * keeps track of state for field: _groupSetupFK
     */
    private boolean _has_groupSetupFK;

    /**
     * Field _chargeAmount
     */
    private java.math.BigDecimal _chargeAmount;

    /**
     * Field _chargeTypeCT
     */
    private java.lang.String _chargeTypeCT;

    /**
     * Field _chargePercent
     */
    private java.math.BigDecimal _chargePercent;

    /**
     * Field _oneTimeOnlyInd
     */
    private java.lang.String _oneTimeOnlyInd;

    /**
     * Field _oneTimeOnlyDate
     */
    private java.lang.String _oneTimeOnlyDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public ChargeVO() {
        super();
    } //-- edit.common.vo.ChargeVO()


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
        
        if (obj instanceof ChargeVO) {
        
            ChargeVO temp = (ChargeVO)obj;
            if (this._chargePK != temp._chargePK)
                return false;
            if (this._has_chargePK != temp._has_chargePK)
                return false;
            if (this._groupSetupFK != temp._groupSetupFK)
                return false;
            if (this._has_groupSetupFK != temp._has_groupSetupFK)
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
            if (this._chargePercent != null) {
                if (temp._chargePercent == null) return false;
                else if (!(this._chargePercent.equals(temp._chargePercent))) 
                    return false;
            }
            else if (temp._chargePercent != null)
                return false;
            if (this._oneTimeOnlyInd != null) {
                if (temp._oneTimeOnlyInd == null) return false;
                else if (!(this._oneTimeOnlyInd.equals(temp._oneTimeOnlyInd))) 
                    return false;
            }
            else if (temp._oneTimeOnlyInd != null)
                return false;
            if (this._oneTimeOnlyDate != null) {
                if (temp._oneTimeOnlyDate == null) return false;
                else if (!(this._oneTimeOnlyDate.equals(temp._oneTimeOnlyDate))) 
                    return false;
            }
            else if (temp._oneTimeOnlyDate != null)
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
     * Method getChargePKReturns the value of field 'chargePK'.
     * 
     * @return the value of field 'chargePK'.
     */
    public long getChargePK()
    {
        return this._chargePK;
    } //-- long getChargePK() 

    /**
     * Method getChargePercentReturns the value of field
     * 'chargePercent'.
     * 
     * @return the value of field 'chargePercent'.
     */
    public java.math.BigDecimal getChargePercent()
    {
        return this._chargePercent;
    } //-- java.math.BigDecimal getChargePercent() 

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
     * Method getGroupSetupFKReturns the value of field
     * 'groupSetupFK'.
     * 
     * @return the value of field 'groupSetupFK'.
     */
    public long getGroupSetupFK()
    {
        return this._groupSetupFK;
    } //-- long getGroupSetupFK() 

    /**
     * Method getOneTimeOnlyDateReturns the value of field
     * 'oneTimeOnlyDate'.
     * 
     * @return the value of field 'oneTimeOnlyDate'.
     */
    public java.lang.String getOneTimeOnlyDate()
    {
        return this._oneTimeOnlyDate;
    } //-- java.lang.String getOneTimeOnlyDate() 

    /**
     * Method getOneTimeOnlyIndReturns the value of field
     * 'oneTimeOnlyInd'.
     * 
     * @return the value of field 'oneTimeOnlyInd'.
     */
    public java.lang.String getOneTimeOnlyInd()
    {
        return this._oneTimeOnlyInd;
    } //-- java.lang.String getOneTimeOnlyInd() 

    /**
     * Method hasChargePK
     */
    public boolean hasChargePK()
    {
        return this._has_chargePK;
    } //-- boolean hasChargePK() 

    /**
     * Method hasGroupSetupFK
     */
    public boolean hasGroupSetupFK()
    {
        return this._has_groupSetupFK;
    } //-- boolean hasGroupSetupFK() 

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
     * Method setChargePKSets the value of field 'chargePK'.
     * 
     * @param chargePK the value of field 'chargePK'.
     */
    public void setChargePK(long chargePK)
    {
        this._chargePK = chargePK;
        
        super.setVoChanged(true);
        this._has_chargePK = true;
    } //-- void setChargePK(long) 

    /**
     * Method setChargePercentSets the value of field
     * 'chargePercent'.
     * 
     * @param chargePercent the value of field 'chargePercent'.
     */
    public void setChargePercent(java.math.BigDecimal chargePercent)
    {
        this._chargePercent = chargePercent;
        
        super.setVoChanged(true);
    } //-- void setChargePercent(java.math.BigDecimal) 

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
     * Method setGroupSetupFKSets the value of field
     * 'groupSetupFK'.
     * 
     * @param groupSetupFK the value of field 'groupSetupFK'.
     */
    public void setGroupSetupFK(long groupSetupFK)
    {
        this._groupSetupFK = groupSetupFK;
        
        super.setVoChanged(true);
        this._has_groupSetupFK = true;
    } //-- void setGroupSetupFK(long) 

    /**
     * Method setOneTimeOnlyDateSets the value of field
     * 'oneTimeOnlyDate'.
     * 
     * @param oneTimeOnlyDate the value of field 'oneTimeOnlyDate'.
     */
    public void setOneTimeOnlyDate(java.lang.String oneTimeOnlyDate)
    {
        this._oneTimeOnlyDate = oneTimeOnlyDate;
        
        super.setVoChanged(true);
    } //-- void setOneTimeOnlyDate(java.lang.String) 

    /**
     * Method setOneTimeOnlyIndSets the value of field
     * 'oneTimeOnlyInd'.
     * 
     * @param oneTimeOnlyInd the value of field 'oneTimeOnlyInd'.
     */
    public void setOneTimeOnlyInd(java.lang.String oneTimeOnlyInd)
    {
        this._oneTimeOnlyInd = oneTimeOnlyInd;
        
        super.setVoChanged(true);
    } //-- void setOneTimeOnlyInd(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ChargeVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ChargeVO) Unmarshaller.unmarshal(edit.common.vo.ChargeVO.class, reader);
    } //-- edit.common.vo.ChargeVO unmarshal(java.io.Reader) 

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
