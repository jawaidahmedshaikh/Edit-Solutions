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
 * Class ChangeHistoryCorrespondenceVO.
 * 
 * @version $Revision$ $Date$
 */
public class ChangeHistoryCorrespondenceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _changeHistoryCorrespondencePK
     */
    private long _changeHistoryCorrespondencePK;

    /**
     * keeps track of state for field: _changeHistoryCorrespondenceP
     */
    private boolean _has_changeHistoryCorrespondencePK;

    /**
     * Field _changeHistoryFK
     */
    private long _changeHistoryFK;

    /**
     * keeps track of state for field: _changeHistoryFK
     */
    private boolean _has_changeHistoryFK;

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


      //----------------/
     //- Constructors -/
    //----------------/

    public ChangeHistoryCorrespondenceVO() {
        super();
    } //-- edit.common.vo.ChangeHistoryCorrespondenceVO()


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
        
        if (obj instanceof ChangeHistoryCorrespondenceVO) {
        
            ChangeHistoryCorrespondenceVO temp = (ChangeHistoryCorrespondenceVO)obj;
            if (this._changeHistoryCorrespondencePK != temp._changeHistoryCorrespondencePK)
                return false;
            if (this._has_changeHistoryCorrespondencePK != temp._has_changeHistoryCorrespondencePK)
                return false;
            if (this._changeHistoryFK != temp._changeHistoryFK)
                return false;
            if (this._has_changeHistoryFK != temp._has_changeHistoryFK)
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
     * Method getChangeHistoryCorrespondencePKReturns the value of
     * field 'changeHistoryCorrespondencePK'.
     * 
     * @return the value of field 'changeHistoryCorrespondencePK'.
     */
    public long getChangeHistoryCorrespondencePK()
    {
        return this._changeHistoryCorrespondencePK;
    } //-- long getChangeHistoryCorrespondencePK() 

    /**
     * Method getChangeHistoryFKReturns the value of field
     * 'changeHistoryFK'.
     * 
     * @return the value of field 'changeHistoryFK'.
     */
    public long getChangeHistoryFK()
    {
        return this._changeHistoryFK;
    } //-- long getChangeHistoryFK() 

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
     * Method getStatusReturns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Method hasChangeHistoryCorrespondencePK
     */
    public boolean hasChangeHistoryCorrespondencePK()
    {
        return this._has_changeHistoryCorrespondencePK;
    } //-- boolean hasChangeHistoryCorrespondencePK() 

    /**
     * Method hasChangeHistoryFK
     */
    public boolean hasChangeHistoryFK()
    {
        return this._has_changeHistoryFK;
    } //-- boolean hasChangeHistoryFK() 

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
     * Method setChangeHistoryCorrespondencePKSets the value of
     * field 'changeHistoryCorrespondencePK'.
     * 
     * @param changeHistoryCorrespondencePK the value of field
     * 'changeHistoryCorrespondencePK'.
     */
    public void setChangeHistoryCorrespondencePK(long changeHistoryCorrespondencePK)
    {
        this._changeHistoryCorrespondencePK = changeHistoryCorrespondencePK;
        
        super.setVoChanged(true);
        this._has_changeHistoryCorrespondencePK = true;
    } //-- void setChangeHistoryCorrespondencePK(long) 

    /**
     * Method setChangeHistoryFKSets the value of field
     * 'changeHistoryFK'.
     * 
     * @param changeHistoryFK the value of field 'changeHistoryFK'.
     */
    public void setChangeHistoryFK(long changeHistoryFK)
    {
        this._changeHistoryFK = changeHistoryFK;
        
        super.setVoChanged(true);
        this._has_changeHistoryFK = true;
    } //-- void setChangeHistoryFK(long) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ChangeHistoryCorrespondenceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ChangeHistoryCorrespondenceVO) Unmarshaller.unmarshal(edit.common.vo.ChangeHistoryCorrespondenceVO.class, reader);
    } //-- edit.common.vo.ChangeHistoryCorrespondenceVO unmarshal(java.io.Reader) 

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
