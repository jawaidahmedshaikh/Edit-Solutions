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
 * Class AllowableTransaction.
 * 
 * @version $Revision$ $Date$
 */
public class AllowableTransaction extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _transactionType
     */
    private java.lang.String _transactionType;

    /**
     * Field _dateRestriction
     */
    private java.lang.String _dateRestriction;

    /**
     * Field _roleRestriction
     */
    private java.lang.String _roleRestriction;


      //----------------/
     //- Constructors -/
    //----------------/

    public AllowableTransaction() {
        super();
    } //-- edit.common.vo.AllowableTransaction()


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
        
        if (obj instanceof AllowableTransaction) {
        
            AllowableTransaction temp = (AllowableTransaction)obj;
            if (this._transactionType != null) {
                if (temp._transactionType == null) return false;
                else if (!(this._transactionType.equals(temp._transactionType))) 
                    return false;
            }
            else if (temp._transactionType != null)
                return false;
            if (this._dateRestriction != null) {
                if (temp._dateRestriction == null) return false;
                else if (!(this._dateRestriction.equals(temp._dateRestriction))) 
                    return false;
            }
            else if (temp._dateRestriction != null)
                return false;
            if (this._roleRestriction != null) {
                if (temp._roleRestriction == null) return false;
                else if (!(this._roleRestriction.equals(temp._roleRestriction))) 
                    return false;
            }
            else if (temp._roleRestriction != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDateRestrictionReturns the value of field
     * 'dateRestriction'.
     * 
     * @return the value of field 'dateRestriction'.
     */
    public java.lang.String getDateRestriction()
    {
        return this._dateRestriction;
    } //-- java.lang.String getDateRestriction() 

    /**
     * Method getRoleRestrictionReturns the value of field
     * 'roleRestriction'.
     * 
     * @return the value of field 'roleRestriction'.
     */
    public java.lang.String getRoleRestriction()
    {
        return this._roleRestriction;
    } //-- java.lang.String getRoleRestriction() 

    /**
     * Method getTransactionTypeReturns the value of field
     * 'transactionType'.
     * 
     * @return the value of field 'transactionType'.
     */
    public java.lang.String getTransactionType()
    {
        return this._transactionType;
    } //-- java.lang.String getTransactionType() 

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
     * Method setDateRestrictionSets the value of field
     * 'dateRestriction'.
     * 
     * @param dateRestriction the value of field 'dateRestriction'.
     */
    public void setDateRestriction(java.lang.String dateRestriction)
    {
        this._dateRestriction = dateRestriction;
        
        super.setVoChanged(true);
    } //-- void setDateRestriction(java.lang.String) 

    /**
     * Method setRoleRestrictionSets the value of field
     * 'roleRestriction'.
     * 
     * @param roleRestriction the value of field 'roleRestriction'.
     */
    public void setRoleRestriction(java.lang.String roleRestriction)
    {
        this._roleRestriction = roleRestriction;
        
        super.setVoChanged(true);
    } //-- void setRoleRestriction(java.lang.String) 

    /**
     * Method setTransactionTypeSets the value of field
     * 'transactionType'.
     * 
     * @param transactionType the value of field 'transactionType'.
     */
    public void setTransactionType(java.lang.String transactionType)
    {
        this._transactionType = transactionType;
        
        super.setVoChanged(true);
    } //-- void setTransactionType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AllowableTransaction unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AllowableTransaction) Unmarshaller.unmarshal(edit.common.vo.AllowableTransaction.class, reader);
    } //-- edit.common.vo.AllowableTransaction unmarshal(java.io.Reader) 

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
