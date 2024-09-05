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
 * Class FeeCorrespondenceVO.
 * 
 * @version $Revision$ $Date$
 */
public class FeeCorrespondenceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _feeCorrespondencePK
     */
    private long _feeCorrespondencePK;

    /**
     * keeps track of state for field: _feeCorrespondencePK
     */
    private boolean _has_feeCorrespondencePK;

    /**
     * Field _feeFK
     */
    private long _feeFK;

    /**
     * keeps track of state for field: _feeFK
     */
    private boolean _has_feeFK;

    /**
     * Field _statusCT
     */
    private java.lang.String _statusCT;

    /**
     * Field _correspondenceDate
     */
    private java.lang.String _correspondenceDate;

    /**
     * Field _addressTypeCT
     */
    private java.lang.String _addressTypeCT;

    /**
     * Field _notificationAmount
     */
    private java.math.BigDecimal _notificationAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public FeeCorrespondenceVO() {
        super();
    } //-- edit.common.vo.FeeCorrespondenceVO()


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
        
        if (obj instanceof FeeCorrespondenceVO) {
        
            FeeCorrespondenceVO temp = (FeeCorrespondenceVO)obj;
            if (this._feeCorrespondencePK != temp._feeCorrespondencePK)
                return false;
            if (this._has_feeCorrespondencePK != temp._has_feeCorrespondencePK)
                return false;
            if (this._feeFK != temp._feeFK)
                return false;
            if (this._has_feeFK != temp._has_feeFK)
                return false;
            if (this._statusCT != null) {
                if (temp._statusCT == null) return false;
                else if (!(this._statusCT.equals(temp._statusCT))) 
                    return false;
            }
            else if (temp._statusCT != null)
                return false;
            if (this._correspondenceDate != null) {
                if (temp._correspondenceDate == null) return false;
                else if (!(this._correspondenceDate.equals(temp._correspondenceDate))) 
                    return false;
            }
            else if (temp._correspondenceDate != null)
                return false;
            if (this._addressTypeCT != null) {
                if (temp._addressTypeCT == null) return false;
                else if (!(this._addressTypeCT.equals(temp._addressTypeCT))) 
                    return false;
            }
            else if (temp._addressTypeCT != null)
                return false;
            if (this._notificationAmount != null) {
                if (temp._notificationAmount == null) return false;
                else if (!(this._notificationAmount.equals(temp._notificationAmount))) 
                    return false;
            }
            else if (temp._notificationAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAddressTypeCTReturns the value of field
     * 'addressTypeCT'.
     * 
     * @return the value of field 'addressTypeCT'.
     */
    public java.lang.String getAddressTypeCT()
    {
        return this._addressTypeCT;
    } //-- java.lang.String getAddressTypeCT() 

    /**
     * Method getCorrespondenceDateReturns the value of field
     * 'correspondenceDate'.
     * 
     * @return the value of field 'correspondenceDate'.
     */
    public java.lang.String getCorrespondenceDate()
    {
        return this._correspondenceDate;
    } //-- java.lang.String getCorrespondenceDate() 

    /**
     * Method getFeeCorrespondencePKReturns the value of field
     * 'feeCorrespondencePK'.
     * 
     * @return the value of field 'feeCorrespondencePK'.
     */
    public long getFeeCorrespondencePK()
    {
        return this._feeCorrespondencePK;
    } //-- long getFeeCorrespondencePK() 

    /**
     * Method getFeeFKReturns the value of field 'feeFK'.
     * 
     * @return the value of field 'feeFK'.
     */
    public long getFeeFK()
    {
        return this._feeFK;
    } //-- long getFeeFK() 

    /**
     * Method getNotificationAmountReturns the value of field
     * 'notificationAmount'.
     * 
     * @return the value of field 'notificationAmount'.
     */
    public java.math.BigDecimal getNotificationAmount()
    {
        return this._notificationAmount;
    } //-- java.math.BigDecimal getNotificationAmount() 

    /**
     * Method getStatusCTReturns the value of field 'statusCT'.
     * 
     * @return the value of field 'statusCT'.
     */
    public java.lang.String getStatusCT()
    {
        return this._statusCT;
    } //-- java.lang.String getStatusCT() 

    /**
     * Method hasFeeCorrespondencePK
     */
    public boolean hasFeeCorrespondencePK()
    {
        return this._has_feeCorrespondencePK;
    } //-- boolean hasFeeCorrespondencePK() 

    /**
     * Method hasFeeFK
     */
    public boolean hasFeeFK()
    {
        return this._has_feeFK;
    } //-- boolean hasFeeFK() 

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
     * Method setAddressTypeCTSets the value of field
     * 'addressTypeCT'.
     * 
     * @param addressTypeCT the value of field 'addressTypeCT'.
     */
    public void setAddressTypeCT(java.lang.String addressTypeCT)
    {
        this._addressTypeCT = addressTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAddressTypeCT(java.lang.String) 

    /**
     * Method setCorrespondenceDateSets the value of field
     * 'correspondenceDate'.
     * 
     * @param correspondenceDate the value of field
     * 'correspondenceDate'.
     */
    public void setCorrespondenceDate(java.lang.String correspondenceDate)
    {
        this._correspondenceDate = correspondenceDate;
        
        super.setVoChanged(true);
    } //-- void setCorrespondenceDate(java.lang.String) 

    /**
     * Method setFeeCorrespondencePKSets the value of field
     * 'feeCorrespondencePK'.
     * 
     * @param feeCorrespondencePK the value of field
     * 'feeCorrespondencePK'.
     */
    public void setFeeCorrespondencePK(long feeCorrespondencePK)
    {
        this._feeCorrespondencePK = feeCorrespondencePK;
        
        super.setVoChanged(true);
        this._has_feeCorrespondencePK = true;
    } //-- void setFeeCorrespondencePK(long) 

    /**
     * Method setFeeFKSets the value of field 'feeFK'.
     * 
     * @param feeFK the value of field 'feeFK'.
     */
    public void setFeeFK(long feeFK)
    {
        this._feeFK = feeFK;
        
        super.setVoChanged(true);
        this._has_feeFK = true;
    } //-- void setFeeFK(long) 

    /**
     * Method setNotificationAmountSets the value of field
     * 'notificationAmount'.
     * 
     * @param notificationAmount the value of field
     * 'notificationAmount'.
     */
    public void setNotificationAmount(java.math.BigDecimal notificationAmount)
    {
        this._notificationAmount = notificationAmount;
        
        super.setVoChanged(true);
    } //-- void setNotificationAmount(java.math.BigDecimal) 

    /**
     * Method setStatusCTSets the value of field 'statusCT'.
     * 
     * @param statusCT the value of field 'statusCT'.
     */
    public void setStatusCT(java.lang.String statusCT)
    {
        this._statusCT = statusCT;
        
        super.setVoChanged(true);
    } //-- void setStatusCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FeeCorrespondenceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FeeCorrespondenceVO) Unmarshaller.unmarshal(edit.common.vo.FeeCorrespondenceVO.class, reader);
    } //-- edit.common.vo.FeeCorrespondenceVO unmarshal(java.io.Reader) 

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
