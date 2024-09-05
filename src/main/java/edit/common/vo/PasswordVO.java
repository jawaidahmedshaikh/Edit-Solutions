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
 * Class PasswordVO.
 * 
 * @version $Revision$ $Date$
 */
public class PasswordVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _passwordPK
     */
    private long _passwordPK;

    /**
     * keeps track of state for field: _passwordPK
     */
    private boolean _has_passwordPK;

    /**
     * Field _encryptedPassword
     */
    private java.lang.String _encryptedPassword;

    /**
     * Field _creationDate
     */
    private java.lang.String _creationDate;

    /**
     * Field _operatorFK
     */
    private long _operatorFK;

    /**
     * keeps track of state for field: _operatorFK
     */
    private boolean _has_operatorFK;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _passwordMaskFK
     */
    private long _passwordMaskFK;

    /**
     * keeps track of state for field: _passwordMaskFK
     */
    private boolean _has_passwordMaskFK;

    /**
     * Field _orderOfCreation
     */
    private int _orderOfCreation;

    /**
     * keeps track of state for field: _orderOfCreation
     */
    private boolean _has_orderOfCreation;


      //----------------/
     //- Constructors -/
    //----------------/

    public PasswordVO() {
        super();
    } //-- edit.common.vo.PasswordVO()


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
        
        if (obj instanceof PasswordVO) {
        
            PasswordVO temp = (PasswordVO)obj;
            if (this._passwordPK != temp._passwordPK)
                return false;
            if (this._has_passwordPK != temp._has_passwordPK)
                return false;
            if (this._encryptedPassword != null) {
                if (temp._encryptedPassword == null) return false;
                else if (!(this._encryptedPassword.equals(temp._encryptedPassword))) 
                    return false;
            }
            else if (temp._encryptedPassword != null)
                return false;
            if (this._creationDate != null) {
                if (temp._creationDate == null) return false;
                else if (!(this._creationDate.equals(temp._creationDate))) 
                    return false;
            }
            else if (temp._creationDate != null)
                return false;
            if (this._operatorFK != temp._operatorFK)
                return false;
            if (this._has_operatorFK != temp._has_operatorFK)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._passwordMaskFK != temp._passwordMaskFK)
                return false;
            if (this._has_passwordMaskFK != temp._has_passwordMaskFK)
                return false;
            if (this._orderOfCreation != temp._orderOfCreation)
                return false;
            if (this._has_orderOfCreation != temp._has_orderOfCreation)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCreationDateReturns the value of field
     * 'creationDate'.
     * 
     * @return the value of field 'creationDate'.
     */
    public java.lang.String getCreationDate()
    {
        return this._creationDate;
    } //-- java.lang.String getCreationDate() 

    /**
     * Method getEncryptedPasswordReturns the value of field
     * 'encryptedPassword'.
     * 
     * @return the value of field 'encryptedPassword'.
     */
    public java.lang.String getEncryptedPassword()
    {
        return this._encryptedPassword;
    } //-- java.lang.String getEncryptedPassword() 

    /**
     * Method getOperatorFKReturns the value of field 'operatorFK'.
     * 
     * @return the value of field 'operatorFK'.
     */
    public long getOperatorFK()
    {
        return this._operatorFK;
    } //-- long getOperatorFK() 

    /**
     * Method getOrderOfCreationReturns the value of field
     * 'orderOfCreation'.
     * 
     * @return the value of field 'orderOfCreation'.
     */
    public int getOrderOfCreation()
    {
        return this._orderOfCreation;
    } //-- int getOrderOfCreation() 

    /**
     * Method getPasswordMaskFKReturns the value of field
     * 'passwordMaskFK'.
     * 
     * @return the value of field 'passwordMaskFK'.
     */
    public long getPasswordMaskFK()
    {
        return this._passwordMaskFK;
    } //-- long getPasswordMaskFK() 

    /**
     * Method getPasswordPKReturns the value of field 'passwordPK'.
     * 
     * @return the value of field 'passwordPK'.
     */
    public long getPasswordPK()
    {
        return this._passwordPK;
    } //-- long getPasswordPK() 

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
     * Method hasOperatorFK
     */
    public boolean hasOperatorFK()
    {
        return this._has_operatorFK;
    } //-- boolean hasOperatorFK() 

    /**
     * Method hasOrderOfCreation
     */
    public boolean hasOrderOfCreation()
    {
        return this._has_orderOfCreation;
    } //-- boolean hasOrderOfCreation() 

    /**
     * Method hasPasswordMaskFK
     */
    public boolean hasPasswordMaskFK()
    {
        return this._has_passwordMaskFK;
    } //-- boolean hasPasswordMaskFK() 

    /**
     * Method hasPasswordPK
     */
    public boolean hasPasswordPK()
    {
        return this._has_passwordPK;
    } //-- boolean hasPasswordPK() 

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
     * Method setCreationDateSets the value of field
     * 'creationDate'.
     * 
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(java.lang.String creationDate)
    {
        this._creationDate = creationDate;
        
        super.setVoChanged(true);
    } //-- void setCreationDate(java.lang.String) 

    /**
     * Method setEncryptedPasswordSets the value of field
     * 'encryptedPassword'.
     * 
     * @param encryptedPassword the value of field
     * 'encryptedPassword'.
     */
    public void setEncryptedPassword(java.lang.String encryptedPassword)
    {
        this._encryptedPassword = encryptedPassword;
        
        super.setVoChanged(true);
    } //-- void setEncryptedPassword(java.lang.String) 

    /**
     * Method setOperatorFKSets the value of field 'operatorFK'.
     * 
     * @param operatorFK the value of field 'operatorFK'.
     */
    public void setOperatorFK(long operatorFK)
    {
        this._operatorFK = operatorFK;
        
        super.setVoChanged(true);
        this._has_operatorFK = true;
    } //-- void setOperatorFK(long) 

    /**
     * Method setOrderOfCreationSets the value of field
     * 'orderOfCreation'.
     * 
     * @param orderOfCreation the value of field 'orderOfCreation'.
     */
    public void setOrderOfCreation(int orderOfCreation)
    {
        this._orderOfCreation = orderOfCreation;
        
        super.setVoChanged(true);
        this._has_orderOfCreation = true;
    } //-- void setOrderOfCreation(int) 

    /**
     * Method setPasswordMaskFKSets the value of field
     * 'passwordMaskFK'.
     * 
     * @param passwordMaskFK the value of field 'passwordMaskFK'.
     */
    public void setPasswordMaskFK(long passwordMaskFK)
    {
        this._passwordMaskFK = passwordMaskFK;
        
        super.setVoChanged(true);
        this._has_passwordMaskFK = true;
    } //-- void setPasswordMaskFK(long) 

    /**
     * Method setPasswordPKSets the value of field 'passwordPK'.
     * 
     * @param passwordPK the value of field 'passwordPK'.
     */
    public void setPasswordPK(long passwordPK)
    {
        this._passwordPK = passwordPK;
        
        super.setVoChanged(true);
        this._has_passwordPK = true;
    } //-- void setPasswordPK(long) 

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
    public static edit.common.vo.PasswordVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PasswordVO) Unmarshaller.unmarshal(edit.common.vo.PasswordVO.class, reader);
    } //-- edit.common.vo.PasswordVO unmarshal(java.io.Reader) 

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
