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
 * Class ContactInformationVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContactInformationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contactInformationPK
     */
    private long _contactInformationPK;

    /**
     * keeps track of state for field: _contactInformationPK
     */
    private boolean _has_contactInformationPK;

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _contactTypeCT
     */
    private java.lang.String _contactTypeCT;

    /**
     * Field _phoneEmail
     */
    private java.lang.String _phoneEmail;

    /**
     * Field _name
     */
    private java.lang.String _name;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContactInformationVO() {
        super();
    } //-- edit.common.vo.ContactInformationVO()


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
        
        if (obj instanceof ContactInformationVO) {
        
            ContactInformationVO temp = (ContactInformationVO)obj;
            if (this._contactInformationPK != temp._contactInformationPK)
                return false;
            if (this._has_contactInformationPK != temp._has_contactInformationPK)
                return false;
            if (this._clientDetailFK != temp._clientDetailFK)
                return false;
            if (this._has_clientDetailFK != temp._has_clientDetailFK)
                return false;
            if (this._contactTypeCT != null) {
                if (temp._contactTypeCT == null) return false;
                else if (!(this._contactTypeCT.equals(temp._contactTypeCT))) 
                    return false;
            }
            else if (temp._contactTypeCT != null)
                return false;
            if (this._phoneEmail != null) {
                if (temp._phoneEmail == null) return false;
                else if (!(this._phoneEmail.equals(temp._phoneEmail))) 
                    return false;
            }
            else if (temp._phoneEmail != null)
                return false;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

    /**
     * Method getContactInformationPKReturns the value of field
     * 'contactInformationPK'.
     * 
     * @return the value of field 'contactInformationPK'.
     */
    public long getContactInformationPK()
    {
        return this._contactInformationPK;
    } //-- long getContactInformationPK() 

    /**
     * Method getContactTypeCTReturns the value of field
     * 'contactTypeCT'.
     * 
     * @return the value of field 'contactTypeCT'.
     */
    public java.lang.String getContactTypeCT()
    {
        return this._contactTypeCT;
    } //-- java.lang.String getContactTypeCT() 

    /**
     * Method getNameReturns the value of field 'name'.
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Method getPhoneEmailReturns the value of field 'phoneEmail'.
     * 
     * @return the value of field 'phoneEmail'.
     */
    public java.lang.String getPhoneEmail()
    {
        return this._phoneEmail;
    } //-- java.lang.String getPhoneEmail() 

    /**
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

    /**
     * Method hasContactInformationPK
     */
    public boolean hasContactInformationPK()
    {
        return this._has_contactInformationPK;
    } //-- boolean hasContactInformationPK() 

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
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setContactInformationPKSets the value of field
     * 'contactInformationPK'.
     * 
     * @param contactInformationPK the value of field
     * 'contactInformationPK'.
     */
    public void setContactInformationPK(long contactInformationPK)
    {
        this._contactInformationPK = contactInformationPK;
        
        super.setVoChanged(true);
        this._has_contactInformationPK = true;
    } //-- void setContactInformationPK(long) 

    /**
     * Method setContactTypeCTSets the value of field
     * 'contactTypeCT'.
     * 
     * @param contactTypeCT the value of field 'contactTypeCT'.
     */
    public void setContactTypeCT(java.lang.String contactTypeCT)
    {
        this._contactTypeCT = contactTypeCT;
        
        super.setVoChanged(true);
    } //-- void setContactTypeCT(java.lang.String) 

    /**
     * Method setNameSets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
        
        super.setVoChanged(true);
    } //-- void setName(java.lang.String) 

    /**
     * Method setPhoneEmailSets the value of field 'phoneEmail'.
     * 
     * @param phoneEmail the value of field 'phoneEmail'.
     */
    public void setPhoneEmail(java.lang.String phoneEmail)
    {
        this._phoneEmail = phoneEmail;
        
        super.setVoChanged(true);
    } //-- void setPhoneEmail(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContactInformationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContactInformationVO) Unmarshaller.unmarshal(edit.common.vo.ContactInformationVO.class, reader);
    } //-- edit.common.vo.ContactInformationVO unmarshal(java.io.Reader) 

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
