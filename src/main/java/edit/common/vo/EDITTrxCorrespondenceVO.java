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
 * Class EDITTrxCorrespondenceVO.
 * 
 * @version $Revision$ $Date$
 */
public class EDITTrxCorrespondenceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _EDITTrxCorrespondencePK
     */
    private long _EDITTrxCorrespondencePK;

    /**
     * keeps track of state for field: _EDITTrxCorrespondencePK
     */
    private boolean _has_EDITTrxCorrespondencePK;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _transactionCorrespondenceFK
     */
    private long _transactionCorrespondenceFK;

    /**
     * keeps track of state for field: _transactionCorrespondenceFK
     */
    private boolean _has_transactionCorrespondenceFK;

    /**
     * Field _status
     */
    private java.lang.String _status;

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

    public EDITTrxCorrespondenceVO() {
        super();
    } //-- edit.common.vo.EDITTrxCorrespondenceVO()


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
        
        if (obj instanceof EDITTrxCorrespondenceVO) {
        
            EDITTrxCorrespondenceVO temp = (EDITTrxCorrespondenceVO)obj;
            if (this._EDITTrxCorrespondencePK != temp._EDITTrxCorrespondencePK)
                return false;
            if (this._has_EDITTrxCorrespondencePK != temp._has_EDITTrxCorrespondencePK)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._transactionCorrespondenceFK != temp._transactionCorrespondenceFK)
                return false;
            if (this._has_transactionCorrespondenceFK != temp._has_transactionCorrespondenceFK)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
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
     * Method getEDITTrxCorrespondencePKReturns the value of field
     * 'EDITTrxCorrespondencePK'.
     * 
     * @return the value of field 'EDITTrxCorrespondencePK'.
     */
    public long getEDITTrxCorrespondencePK()
    {
        return this._EDITTrxCorrespondencePK;
    } //-- long getEDITTrxCorrespondencePK() 

    /**
     * Method getEDITTrxFKReturns the value of field 'EDITTrxFK'.
     * 
     * @return the value of field 'EDITTrxFK'.
     */
    public long getEDITTrxFK()
    {
        return this._EDITTrxFK;
    } //-- long getEDITTrxFK() 

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
     * Method getStatusReturns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Method getTransactionCorrespondenceFKReturns the value of
     * field 'transactionCorrespondenceFK'.
     * 
     * @return the value of field 'transactionCorrespondenceFK'.
     */
    public long getTransactionCorrespondenceFK()
    {
        return this._transactionCorrespondenceFK;
    } //-- long getTransactionCorrespondenceFK() 

    /**
     * Method hasEDITTrxCorrespondencePK
     */
    public boolean hasEDITTrxCorrespondencePK()
    {
        return this._has_EDITTrxCorrespondencePK;
    } //-- boolean hasEDITTrxCorrespondencePK() 

    /**
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasTransactionCorrespondenceFK
     */
    public boolean hasTransactionCorrespondenceFK()
    {
        return this._has_transactionCorrespondenceFK;
    } //-- boolean hasTransactionCorrespondenceFK() 

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
     * Method setEDITTrxCorrespondencePKSets the value of field
     * 'EDITTrxCorrespondencePK'.
     * 
     * @param EDITTrxCorrespondencePK the value of field
     * 'EDITTrxCorrespondencePK'.
     */
    public void setEDITTrxCorrespondencePK(long EDITTrxCorrespondencePK)
    {
        this._EDITTrxCorrespondencePK = EDITTrxCorrespondencePK;
        
        super.setVoChanged(true);
        this._has_EDITTrxCorrespondencePK = true;
    } //-- void setEDITTrxCorrespondencePK(long) 

    /**
     * Method setEDITTrxFKSets the value of field 'EDITTrxFK'.
     * 
     * @param EDITTrxFK the value of field 'EDITTrxFK'.
     */
    public void setEDITTrxFK(long EDITTrxFK)
    {
        this._EDITTrxFK = EDITTrxFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxFK = true;
    } //-- void setEDITTrxFK(long) 

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
     * Method setStatusSets the value of field 'status'.
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(java.lang.String status)
    {
        this._status = status;
        
        super.setVoChanged(true);
    } //-- void setStatus(java.lang.String) 

    /**
     * Method setTransactionCorrespondenceFKSets the value of field
     * 'transactionCorrespondenceFK'.
     * 
     * @param transactionCorrespondenceFK the value of field
     * 'transactionCorrespondenceFK'.
     */
    public void setTransactionCorrespondenceFK(long transactionCorrespondenceFK)
    {
        this._transactionCorrespondenceFK = transactionCorrespondenceFK;
        
        super.setVoChanged(true);
        this._has_transactionCorrespondenceFK = true;
    } //-- void setTransactionCorrespondenceFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITTrxCorrespondenceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITTrxCorrespondenceVO) Unmarshaller.unmarshal(edit.common.vo.EDITTrxCorrespondenceVO.class, reader);
    } //-- edit.common.vo.EDITTrxCorrespondenceVO unmarshal(java.io.Reader) 

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
