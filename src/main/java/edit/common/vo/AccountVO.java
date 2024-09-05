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
 * Class AccountVO.
 * 
 * @version $Revision$ $Date$
 */
public class AccountVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _accountPK
     */
    private long _accountPK;

    /**
     * keeps track of state for field: _accountPK
     */
    private boolean _has_accountPK;

    /**
     * Field _elementStructureFK
     */
    private long _elementStructureFK;

    /**
     * keeps track of state for field: _elementStructureFK
     */
    private boolean _has_elementStructureFK;

    /**
     * Field _accountNumber
     */
    private java.lang.String _accountNumber;

    /**
     * Field _accountName
     */
    private java.lang.String _accountName;

    /**
     * Field _effect
     */
    private java.lang.String _effect;

    /**
     * Field _accountDescription
     */
    private java.lang.String _accountDescription;


      //----------------/
     //- Constructors -/
    //----------------/

    public AccountVO() {
        super();
    } //-- edit.common.vo.AccountVO()


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
        
        if (obj instanceof AccountVO) {
        
            AccountVO temp = (AccountVO)obj;
            if (this._accountPK != temp._accountPK)
                return false;
            if (this._has_accountPK != temp._has_accountPK)
                return false;
            if (this._elementStructureFK != temp._elementStructureFK)
                return false;
            if (this._has_elementStructureFK != temp._has_elementStructureFK)
                return false;
            if (this._accountNumber != null) {
                if (temp._accountNumber == null) return false;
                else if (!(this._accountNumber.equals(temp._accountNumber))) 
                    return false;
            }
            else if (temp._accountNumber != null)
                return false;
            if (this._accountName != null) {
                if (temp._accountName == null) return false;
                else if (!(this._accountName.equals(temp._accountName))) 
                    return false;
            }
            else if (temp._accountName != null)
                return false;
            if (this._effect != null) {
                if (temp._effect == null) return false;
                else if (!(this._effect.equals(temp._effect))) 
                    return false;
            }
            else if (temp._effect != null)
                return false;
            if (this._accountDescription != null) {
                if (temp._accountDescription == null) return false;
                else if (!(this._accountDescription.equals(temp._accountDescription))) 
                    return false;
            }
            else if (temp._accountDescription != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountDescriptionReturns the value of field
     * 'accountDescription'.
     * 
     * @return the value of field 'accountDescription'.
     */
    public java.lang.String getAccountDescription()
    {
        return this._accountDescription;
    } //-- java.lang.String getAccountDescription() 

    /**
     * Method getAccountNameReturns the value of field
     * 'accountName'.
     * 
     * @return the value of field 'accountName'.
     */
    public java.lang.String getAccountName()
    {
        return this._accountName;
    } //-- java.lang.String getAccountName() 

    /**
     * Method getAccountNumberReturns the value of field
     * 'accountNumber'.
     * 
     * @return the value of field 'accountNumber'.
     */
    public java.lang.String getAccountNumber()
    {
        return this._accountNumber;
    } //-- java.lang.String getAccountNumber() 

    /**
     * Method getAccountPKReturns the value of field 'accountPK'.
     * 
     * @return the value of field 'accountPK'.
     */
    public long getAccountPK()
    {
        return this._accountPK;
    } //-- long getAccountPK() 

    /**
     * Method getEffectReturns the value of field 'effect'.
     * 
     * @return the value of field 'effect'.
     */
    public java.lang.String getEffect()
    {
        return this._effect;
    } //-- java.lang.String getEffect() 

    /**
     * Method getElementStructureFKReturns the value of field
     * 'elementStructureFK'.
     * 
     * @return the value of field 'elementStructureFK'.
     */
    public long getElementStructureFK()
    {
        return this._elementStructureFK;
    } //-- long getElementStructureFK() 

    /**
     * Method hasAccountPK
     */
    public boolean hasAccountPK()
    {
        return this._has_accountPK;
    } //-- boolean hasAccountPK() 

    /**
     * Method hasElementStructureFK
     */
    public boolean hasElementStructureFK()
    {
        return this._has_elementStructureFK;
    } //-- boolean hasElementStructureFK() 

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
     * Method setAccountDescriptionSets the value of field
     * 'accountDescription'.
     * 
     * @param accountDescription the value of field
     * 'accountDescription'.
     */
    public void setAccountDescription(java.lang.String accountDescription)
    {
        this._accountDescription = accountDescription;
        
        super.setVoChanged(true);
    } //-- void setAccountDescription(java.lang.String) 

    /**
     * Method setAccountNameSets the value of field 'accountName'.
     * 
     * @param accountName the value of field 'accountName'.
     */
    public void setAccountName(java.lang.String accountName)
    {
        this._accountName = accountName;
        
        super.setVoChanged(true);
    } //-- void setAccountName(java.lang.String) 

    /**
     * Method setAccountNumberSets the value of field
     * 'accountNumber'.
     * 
     * @param accountNumber the value of field 'accountNumber'.
     */
    public void setAccountNumber(java.lang.String accountNumber)
    {
        this._accountNumber = accountNumber;
        
        super.setVoChanged(true);
    } //-- void setAccountNumber(java.lang.String) 

    /**
     * Method setAccountPKSets the value of field 'accountPK'.
     * 
     * @param accountPK the value of field 'accountPK'.
     */
    public void setAccountPK(long accountPK)
    {
        this._accountPK = accountPK;
        
        super.setVoChanged(true);
        this._has_accountPK = true;
    } //-- void setAccountPK(long) 

    /**
     * Method setEffectSets the value of field 'effect'.
     * 
     * @param effect the value of field 'effect'.
     */
    public void setEffect(java.lang.String effect)
    {
        this._effect = effect;
        
        super.setVoChanged(true);
    } //-- void setEffect(java.lang.String) 

    /**
     * Method setElementStructureFKSets the value of field
     * 'elementStructureFK'.
     * 
     * @param elementStructureFK the value of field
     * 'elementStructureFK'.
     */
    public void setElementStructureFK(long elementStructureFK)
    {
        this._elementStructureFK = elementStructureFK;
        
        super.setVoChanged(true);
        this._has_elementStructureFK = true;
    } //-- void setElementStructureFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AccountVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AccountVO) Unmarshaller.unmarshal(edit.common.vo.AccountVO.class, reader);
    } //-- edit.common.vo.AccountVO unmarshal(java.io.Reader) 

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
