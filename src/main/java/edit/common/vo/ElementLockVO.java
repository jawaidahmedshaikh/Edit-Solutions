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
 * Class ElementLockVO.
 * 
 * @version $Revision$ $Date$
 */
public class ElementLockVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _elementLockPK
     */
    private long _elementLockPK;

    /**
     * keeps track of state for field: _elementLockPK
     */
    private boolean _has_elementLockPK;

    /**
     * Field _username
     */
    private java.lang.String _username;

    /**
     * Field _lockDateTime
     */
    private java.lang.String _lockDateTime;

    /**
     * Field _elementFK
     */
    private long _elementFK;

    /**
     * keeps track of state for field: _elementFK
     */
    private boolean _has_elementFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElementLockVO() {
        super();
    } //-- edit.common.vo.ElementLockVO()


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
        
        if (obj instanceof ElementLockVO) {
        
            ElementLockVO temp = (ElementLockVO)obj;
            if (this._elementLockPK != temp._elementLockPK)
                return false;
            if (this._has_elementLockPK != temp._has_elementLockPK)
                return false;
            if (this._username != null) {
                if (temp._username == null) return false;
                else if (!(this._username.equals(temp._username))) 
                    return false;
            }
            else if (temp._username != null)
                return false;
            if (this._lockDateTime != null) {
                if (temp._lockDateTime == null) return false;
                else if (!(this._lockDateTime.equals(temp._lockDateTime))) 
                    return false;
            }
            else if (temp._lockDateTime != null)
                return false;
            if (this._elementFK != temp._elementFK)
                return false;
            if (this._has_elementFK != temp._has_elementFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getElementFKReturns the value of field 'elementFK'.
     * 
     * @return the value of field 'elementFK'.
     */
    public long getElementFK()
    {
        return this._elementFK;
    } //-- long getElementFK() 

    /**
     * Method getElementLockPKReturns the value of field
     * 'elementLockPK'.
     * 
     * @return the value of field 'elementLockPK'.
     */
    public long getElementLockPK()
    {
        return this._elementLockPK;
    } //-- long getElementLockPK() 

    /**
     * Method getLockDateTimeReturns the value of field
     * 'lockDateTime'.
     * 
     * @return the value of field 'lockDateTime'.
     */
    public java.lang.String getLockDateTime()
    {
        return this._lockDateTime;
    } //-- java.lang.String getLockDateTime() 

    /**
     * Method getUsernameReturns the value of field 'username'.
     * 
     * @return the value of field 'username'.
     */
    public java.lang.String getUsername()
    {
        return this._username;
    } //-- java.lang.String getUsername() 

    /**
     * Method hasElementFK
     */
    public boolean hasElementFK()
    {
        return this._has_elementFK;
    } //-- boolean hasElementFK() 

    /**
     * Method hasElementLockPK
     */
    public boolean hasElementLockPK()
    {
        return this._has_elementLockPK;
    } //-- boolean hasElementLockPK() 

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
     * Method setElementFKSets the value of field 'elementFK'.
     * 
     * @param elementFK the value of field 'elementFK'.
     */
    public void setElementFK(long elementFK)
    {
        this._elementFK = elementFK;
        
        super.setVoChanged(true);
        this._has_elementFK = true;
    } //-- void setElementFK(long) 

    /**
     * Method setElementLockPKSets the value of field
     * 'elementLockPK'.
     * 
     * @param elementLockPK the value of field 'elementLockPK'.
     */
    public void setElementLockPK(long elementLockPK)
    {
        this._elementLockPK = elementLockPK;
        
        super.setVoChanged(true);
        this._has_elementLockPK = true;
    } //-- void setElementLockPK(long) 

    /**
     * Method setLockDateTimeSets the value of field
     * 'lockDateTime'.
     * 
     * @param lockDateTime the value of field 'lockDateTime'.
     */
    public void setLockDateTime(java.lang.String lockDateTime)
    {
        this._lockDateTime = lockDateTime;
        
        super.setVoChanged(true);
    } //-- void setLockDateTime(java.lang.String) 

    /**
     * Method setUsernameSets the value of field 'username'.
     * 
     * @param username the value of field 'username'.
     */
    public void setUsername(java.lang.String username)
    {
        this._username = username;
        
        super.setVoChanged(true);
    } //-- void setUsername(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ElementLockVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ElementLockVO) Unmarshaller.unmarshal(edit.common.vo.ElementLockVO.class, reader);
    } //-- edit.common.vo.ElementLockVO unmarshal(java.io.Reader) 

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
