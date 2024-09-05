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
 * Class SecurityLogVO.
 * 
 * @version $Revision$ $Date$
 */
public class SecurityLogVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _securityLogPK
     */
    private long _securityLogPK;

    /**
     * keeps track of state for field: _securityLogPK
     */
    private boolean _has_securityLogPK;

    /**
     * Field _operatorName
     */
    private java.lang.String _operatorName;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _message
     */
    private java.lang.String _message;

    /**
     * Field _type
     */
    private java.lang.String _type;


      //----------------/
     //- Constructors -/
    //----------------/

    public SecurityLogVO() {
        super();
    } //-- edit.common.vo.SecurityLogVO()


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
        
        if (obj instanceof SecurityLogVO) {
        
            SecurityLogVO temp = (SecurityLogVO)obj;
            if (this._securityLogPK != temp._securityLogPK)
                return false;
            if (this._has_securityLogPK != temp._has_securityLogPK)
                return false;
            if (this._operatorName != null) {
                if (temp._operatorName == null) return false;
                else if (!(this._operatorName.equals(temp._operatorName))) 
                    return false;
            }
            else if (temp._operatorName != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._message != null) {
                if (temp._message == null) return false;
                else if (!(this._message.equals(temp._message))) 
                    return false;
            }
            else if (temp._message != null)
                return false;
            if (this._type != null) {
                if (temp._type == null) return false;
                else if (!(this._type.equals(temp._type))) 
                    return false;
            }
            else if (temp._type != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getMessageReturns the value of field 'message'.
     * 
     * @return the value of field 'message'.
     */
    public java.lang.String getMessage()
    {
        return this._message;
    } //-- java.lang.String getMessage() 

    /**
     * Method getOperatorNameReturns the value of field
     * 'operatorName'.
     * 
     * @return the value of field 'operatorName'.
     */
    public java.lang.String getOperatorName()
    {
        return this._operatorName;
    } //-- java.lang.String getOperatorName() 

    /**
     * Method getSecurityLogPKReturns the value of field
     * 'securityLogPK'.
     * 
     * @return the value of field 'securityLogPK'.
     */
    public long getSecurityLogPK()
    {
        return this._securityLogPK;
    } //-- long getSecurityLogPK() 

    /**
     * Method getTypeReturns the value of field 'type'.
     * 
     * @return the value of field 'type'.
     */
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

    /**
     * Method hasSecurityLogPK
     */
    public boolean hasSecurityLogPK()
    {
        return this._has_securityLogPK;
    } //-- boolean hasSecurityLogPK() 

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
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setMessageSets the value of field 'message'.
     * 
     * @param message the value of field 'message'.
     */
    public void setMessage(java.lang.String message)
    {
        this._message = message;
        
        super.setVoChanged(true);
    } //-- void setMessage(java.lang.String) 

    /**
     * Method setOperatorNameSets the value of field
     * 'operatorName'.
     * 
     * @param operatorName the value of field 'operatorName'.
     */
    public void setOperatorName(java.lang.String operatorName)
    {
        this._operatorName = operatorName;
        
        super.setVoChanged(true);
    } //-- void setOperatorName(java.lang.String) 

    /**
     * Method setSecurityLogPKSets the value of field
     * 'securityLogPK'.
     * 
     * @param securityLogPK the value of field 'securityLogPK'.
     */
    public void setSecurityLogPK(long securityLogPK)
    {
        this._securityLogPK = securityLogPK;
        
        super.setVoChanged(true);
        this._has_securityLogPK = true;
    } //-- void setSecurityLogPK(long) 

    /**
     * Method setTypeSets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(java.lang.String type)
    {
        this._type = type;
        
        super.setVoChanged(true);
    } //-- void setType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SecurityLogVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SecurityLogVO) Unmarshaller.unmarshal(edit.common.vo.SecurityLogVO.class, reader);
    } //-- edit.common.vo.SecurityLogVO unmarshal(java.io.Reader) 

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
