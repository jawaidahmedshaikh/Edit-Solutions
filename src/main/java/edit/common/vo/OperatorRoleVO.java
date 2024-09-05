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
 * Class OperatorRoleVO.
 * 
 * @version $Revision$ $Date$
 */
public class OperatorRoleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _operatorRolePK
     */
    private long _operatorRolePK;

    /**
     * keeps track of state for field: _operatorRolePK
     */
    private boolean _has_operatorRolePK;

    /**
     * Field _operatorFK
     */
    private long _operatorFK;

    /**
     * keeps track of state for field: _operatorFK
     */
    private boolean _has_operatorFK;

    /**
     * Field _roleFK
     */
    private long _roleFK;

    /**
     * keeps track of state for field: _roleFK
     */
    private boolean _has_roleFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public OperatorRoleVO() {
        super();
    } //-- edit.common.vo.OperatorRoleVO()


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
        
        if (obj instanceof OperatorRoleVO) {
        
            OperatorRoleVO temp = (OperatorRoleVO)obj;
            if (this._operatorRolePK != temp._operatorRolePK)
                return false;
            if (this._has_operatorRolePK != temp._has_operatorRolePK)
                return false;
            if (this._operatorFK != temp._operatorFK)
                return false;
            if (this._has_operatorFK != temp._has_operatorFK)
                return false;
            if (this._roleFK != temp._roleFK)
                return false;
            if (this._has_roleFK != temp._has_roleFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getOperatorRolePKReturns the value of field
     * 'operatorRolePK'.
     * 
     * @return the value of field 'operatorRolePK'.
     */
    public long getOperatorRolePK()
    {
        return this._operatorRolePK;
    } //-- long getOperatorRolePK() 

    /**
     * Method getRoleFKReturns the value of field 'roleFK'.
     * 
     * @return the value of field 'roleFK'.
     */
    public long getRoleFK()
    {
        return this._roleFK;
    } //-- long getRoleFK() 

    /**
     * Method hasOperatorFK
     */
    public boolean hasOperatorFK()
    {
        return this._has_operatorFK;
    } //-- boolean hasOperatorFK() 

    /**
     * Method hasOperatorRolePK
     */
    public boolean hasOperatorRolePK()
    {
        return this._has_operatorRolePK;
    } //-- boolean hasOperatorRolePK() 

    /**
     * Method hasRoleFK
     */
    public boolean hasRoleFK()
    {
        return this._has_roleFK;
    } //-- boolean hasRoleFK() 

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
     * Method setOperatorRolePKSets the value of field
     * 'operatorRolePK'.
     * 
     * @param operatorRolePK the value of field 'operatorRolePK'.
     */
    public void setOperatorRolePK(long operatorRolePK)
    {
        this._operatorRolePK = operatorRolePK;
        
        super.setVoChanged(true);
        this._has_operatorRolePK = true;
    } //-- void setOperatorRolePK(long) 

    /**
     * Method setRoleFKSets the value of field 'roleFK'.
     * 
     * @param roleFK the value of field 'roleFK'.
     */
    public void setRoleFK(long roleFK)
    {
        this._roleFK = roleFK;
        
        super.setVoChanged(true);
        this._has_roleFK = true;
    } //-- void setRoleFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.OperatorRoleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OperatorRoleVO) Unmarshaller.unmarshal(edit.common.vo.OperatorRoleVO.class, reader);
    } //-- edit.common.vo.OperatorRoleVO unmarshal(java.io.Reader) 

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
