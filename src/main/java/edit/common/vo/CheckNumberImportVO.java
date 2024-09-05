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
 * Used to hold the check number import file lines
 * 
 * @version $Revision$ $Date$
 */
public class CheckNumberImportVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _controlNumber
     */
    private java.lang.String _controlNumber;

    /**
     * Field _checkAmount
     */
    private java.math.BigDecimal _checkAmount;

    /**
     * Field _processDate
     */
    private java.lang.String _processDate;

    /**
     * Field _referenceId
     */
    private java.lang.String _referenceId;


      //----------------/
     //- Constructors -/
    //----------------/

    public CheckNumberImportVO() {
        super();
    } //-- edit.common.vo.CheckNumberImportVO()


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
        
        if (obj instanceof CheckNumberImportVO) {
        
            CheckNumberImportVO temp = (CheckNumberImportVO)obj;
            if (this._controlNumber != null) {
                if (temp._controlNumber == null) return false;
                else if (!(this._controlNumber.equals(temp._controlNumber))) 
                    return false;
            }
            else if (temp._controlNumber != null)
                return false;
            if (this._checkAmount != null) {
                if (temp._checkAmount == null) return false;
                else if (!(this._checkAmount.equals(temp._checkAmount))) 
                    return false;
            }
            else if (temp._checkAmount != null)
                return false;
            if (this._processDate != null) {
                if (temp._processDate == null) return false;
                else if (!(this._processDate.equals(temp._processDate))) 
                    return false;
            }
            else if (temp._processDate != null)
                return false;
            if (this._referenceId != null) {
                if (temp._referenceId == null) return false;
                else if (!(this._referenceId.equals(temp._referenceId))) 
                    return false;
            }
            else if (temp._referenceId != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCheckAmountReturns the value of field
     * 'checkAmount'.
     * 
     * @return the value of field 'checkAmount'.
     */
    public java.math.BigDecimal getCheckAmount()
    {
        return this._checkAmount;
    } //-- java.math.BigDecimal getCheckAmount() 

    /**
     * Method getControlNumberReturns the value of field
     * 'controlNumber'.
     * 
     * @return the value of field 'controlNumber'.
     */
    public java.lang.String getControlNumber()
    {
        return this._controlNumber;
    } //-- java.lang.String getControlNumber() 

    /**
     * Method getProcessDateReturns the value of field
     * 'processDate'.
     * 
     * @return the value of field 'processDate'.
     */
    public java.lang.String getProcessDate()
    {
        return this._processDate;
    } //-- java.lang.String getProcessDate() 

    /**
     * Method getReferenceIdReturns the value of field
     * 'referenceId'.
     * 
     * @return the value of field 'referenceId'.
     */
    public java.lang.String getReferenceId()
    {
        return this._referenceId;
    } //-- java.lang.String getReferenceId() 

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
     * Method setCheckAmountSets the value of field 'checkAmount'.
     * 
     * @param checkAmount the value of field 'checkAmount'.
     */
    public void setCheckAmount(java.math.BigDecimal checkAmount)
    {
        this._checkAmount = checkAmount;
        
        super.setVoChanged(true);
    } //-- void setCheckAmount(java.math.BigDecimal) 

    /**
     * Method setControlNumberSets the value of field
     * 'controlNumber'.
     * 
     * @param controlNumber the value of field 'controlNumber'.
     */
    public void setControlNumber(java.lang.String controlNumber)
    {
        this._controlNumber = controlNumber;
        
        super.setVoChanged(true);
    } //-- void setControlNumber(java.lang.String) 

    /**
     * Method setProcessDateSets the value of field 'processDate'.
     * 
     * @param processDate the value of field 'processDate'.
     */
    public void setProcessDate(java.lang.String processDate)
    {
        this._processDate = processDate;
        
        super.setVoChanged(true);
    } //-- void setProcessDate(java.lang.String) 

    /**
     * Method setReferenceIdSets the value of field 'referenceId'.
     * 
     * @param referenceId the value of field 'referenceId'.
     */
    public void setReferenceId(java.lang.String referenceId)
    {
        this._referenceId = referenceId;
        
        super.setVoChanged(true);
    } //-- void setReferenceId(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CheckNumberImportVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CheckNumberImportVO) Unmarshaller.unmarshal(edit.common.vo.CheckNumberImportVO.class, reader);
    } //-- edit.common.vo.CheckNumberImportVO unmarshal(java.io.Reader) 

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
