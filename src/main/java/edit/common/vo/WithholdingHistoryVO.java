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
 * Class WithholdingHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class WithholdingHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _withholdingHistoryPK
     */
    private long _withholdingHistoryPK;

    /**
     * keeps track of state for field: _withholdingHistoryPK
     */
    private boolean _has_withholdingHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _federalWithholdingAmount
     */
    private java.math.BigDecimal _federalWithholdingAmount;

    /**
     * Field _stateWithholdingAmount
     */
    private java.math.BigDecimal _stateWithholdingAmount;

    /**
     * Field _cityWithholdingAmount
     */
    private java.math.BigDecimal _cityWithholdingAmount;

    /**
     * Field _countyWithholdingAmount
     */
    private java.math.BigDecimal _countyWithholdingAmount;

    /**
     * Field _FICA
     */
    private java.math.BigDecimal _FICA;


      //----------------/
     //- Constructors -/
    //----------------/

    public WithholdingHistoryVO() {
        super();
    } //-- edit.common.vo.WithholdingHistoryVO()


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
        
        if (obj instanceof WithholdingHistoryVO) {
        
            WithholdingHistoryVO temp = (WithholdingHistoryVO)obj;
            if (this._withholdingHistoryPK != temp._withholdingHistoryPK)
                return false;
            if (this._has_withholdingHistoryPK != temp._has_withholdingHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._federalWithholdingAmount != null) {
                if (temp._federalWithholdingAmount == null) return false;
                else if (!(this._federalWithholdingAmount.equals(temp._federalWithholdingAmount))) 
                    return false;
            }
            else if (temp._federalWithholdingAmount != null)
                return false;
            if (this._stateWithholdingAmount != null) {
                if (temp._stateWithholdingAmount == null) return false;
                else if (!(this._stateWithholdingAmount.equals(temp._stateWithholdingAmount))) 
                    return false;
            }
            else if (temp._stateWithholdingAmount != null)
                return false;
            if (this._cityWithholdingAmount != null) {
                if (temp._cityWithholdingAmount == null) return false;
                else if (!(this._cityWithholdingAmount.equals(temp._cityWithholdingAmount))) 
                    return false;
            }
            else if (temp._cityWithholdingAmount != null)
                return false;
            if (this._countyWithholdingAmount != null) {
                if (temp._countyWithholdingAmount == null) return false;
                else if (!(this._countyWithholdingAmount.equals(temp._countyWithholdingAmount))) 
                    return false;
            }
            else if (temp._countyWithholdingAmount != null)
                return false;
            if (this._FICA != null) {
                if (temp._FICA == null) return false;
                else if (!(this._FICA.equals(temp._FICA))) 
                    return false;
            }
            else if (temp._FICA != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCityWithholdingAmountReturns the value of field
     * 'cityWithholdingAmount'.
     * 
     * @return the value of field 'cityWithholdingAmount'.
     */
    public java.math.BigDecimal getCityWithholdingAmount()
    {
        return this._cityWithholdingAmount;
    } //-- java.math.BigDecimal getCityWithholdingAmount() 

    /**
     * Method getCountyWithholdingAmountReturns the value of field
     * 'countyWithholdingAmount'.
     * 
     * @return the value of field 'countyWithholdingAmount'.
     */
    public java.math.BigDecimal getCountyWithholdingAmount()
    {
        return this._countyWithholdingAmount;
    } //-- java.math.BigDecimal getCountyWithholdingAmount() 

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
     * Method getFICAReturns the value of field 'FICA'.
     * 
     * @return the value of field 'FICA'.
     */
    public java.math.BigDecimal getFICA()
    {
        return this._FICA;
    } //-- java.math.BigDecimal getFICA() 

    /**
     * Method getFederalWithholdingAmountReturns the value of field
     * 'federalWithholdingAmount'.
     * 
     * @return the value of field 'federalWithholdingAmount'.
     */
    public java.math.BigDecimal getFederalWithholdingAmount()
    {
        return this._federalWithholdingAmount;
    } //-- java.math.BigDecimal getFederalWithholdingAmount() 

    /**
     * Method getStateWithholdingAmountReturns the value of field
     * 'stateWithholdingAmount'.
     * 
     * @return the value of field 'stateWithholdingAmount'.
     */
    public java.math.BigDecimal getStateWithholdingAmount()
    {
        return this._stateWithholdingAmount;
    } //-- java.math.BigDecimal getStateWithholdingAmount() 

    /**
     * Method getWithholdingHistoryPKReturns the value of field
     * 'withholdingHistoryPK'.
     * 
     * @return the value of field 'withholdingHistoryPK'.
     */
    public long getWithholdingHistoryPK()
    {
        return this._withholdingHistoryPK;
    } //-- long getWithholdingHistoryPK() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasWithholdingHistoryPK
     */
    public boolean hasWithholdingHistoryPK()
    {
        return this._has_withholdingHistoryPK;
    } //-- boolean hasWithholdingHistoryPK() 

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
     * Method setCityWithholdingAmountSets the value of field
     * 'cityWithholdingAmount'.
     * 
     * @param cityWithholdingAmount the value of field
     * 'cityWithholdingAmount'.
     */
    public void setCityWithholdingAmount(java.math.BigDecimal cityWithholdingAmount)
    {
        this._cityWithholdingAmount = cityWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setCityWithholdingAmount(java.math.BigDecimal) 

    /**
     * Method setCountyWithholdingAmountSets the value of field
     * 'countyWithholdingAmount'.
     * 
     * @param countyWithholdingAmount the value of field
     * 'countyWithholdingAmount'.
     */
    public void setCountyWithholdingAmount(java.math.BigDecimal countyWithholdingAmount)
    {
        this._countyWithholdingAmount = countyWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setCountyWithholdingAmount(java.math.BigDecimal) 

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
     * Method setFICASets the value of field 'FICA'.
     * 
     * @param FICA the value of field 'FICA'.
     */
    public void setFICA(java.math.BigDecimal FICA)
    {
        this._FICA = FICA;
        
        super.setVoChanged(true);
    } //-- void setFICA(java.math.BigDecimal) 

    /**
     * Method setFederalWithholdingAmountSets the value of field
     * 'federalWithholdingAmount'.
     * 
     * @param federalWithholdingAmount the value of field
     * 'federalWithholdingAmount'.
     */
    public void setFederalWithholdingAmount(java.math.BigDecimal federalWithholdingAmount)
    {
        this._federalWithholdingAmount = federalWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setFederalWithholdingAmount(java.math.BigDecimal) 

    /**
     * Method setStateWithholdingAmountSets the value of field
     * 'stateWithholdingAmount'.
     * 
     * @param stateWithholdingAmount the value of field
     * 'stateWithholdingAmount'.
     */
    public void setStateWithholdingAmount(java.math.BigDecimal stateWithholdingAmount)
    {
        this._stateWithholdingAmount = stateWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setStateWithholdingAmount(java.math.BigDecimal) 

    /**
     * Method setWithholdingHistoryPKSets the value of field
     * 'withholdingHistoryPK'.
     * 
     * @param withholdingHistoryPK the value of field
     * 'withholdingHistoryPK'.
     */
    public void setWithholdingHistoryPK(long withholdingHistoryPK)
    {
        this._withholdingHistoryPK = withholdingHistoryPK;
        
        super.setVoChanged(true);
        this._has_withholdingHistoryPK = true;
    } //-- void setWithholdingHistoryPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.WithholdingHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.WithholdingHistoryVO) Unmarshaller.unmarshal(edit.common.vo.WithholdingHistoryVO.class, reader);
    } //-- edit.common.vo.WithholdingHistoryVO unmarshal(java.io.Reader) 

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
