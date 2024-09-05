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
 * Class ImpliedRoleVO.
 * 
 * @version $Revision$ $Date$
 */
public class ImpliedRoleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _impliedRolePK
     */
    private long _impliedRolePK;

    /**
     * keeps track of state for field: _impliedRolePK
     */
    private boolean _has_impliedRolePK;

    /**
     * Field _roleFK
     */
    private long _roleFK;

    /**
     * keeps track of state for field: _roleFK
     */
    private boolean _has_roleFK;

    /**
     * Field _impliedRoleFK
     */
    private long _impliedRoleFK;

    /**
     * keeps track of state for field: _impliedRoleFK
     */
    private boolean _has_impliedRoleFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ImpliedRoleVO() {
        super();
    } //-- edit.common.vo.ImpliedRoleVO()


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
        
        if (obj instanceof ImpliedRoleVO) {
        
            ImpliedRoleVO temp = (ImpliedRoleVO)obj;
            if (this._impliedRolePK != temp._impliedRolePK)
                return false;
            if (this._has_impliedRolePK != temp._has_impliedRolePK)
                return false;
            if (this._roleFK != temp._roleFK)
                return false;
            if (this._has_roleFK != temp._has_roleFK)
                return false;
            if (this._impliedRoleFK != temp._impliedRoleFK)
                return false;
            if (this._has_impliedRoleFK != temp._has_impliedRoleFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getImpliedRoleFKReturns the value of field
     * 'impliedRoleFK'.
     * 
     * @return the value of field 'impliedRoleFK'.
     */
    public long getImpliedRoleFK()
    {
        return this._impliedRoleFK;
    } //-- long getImpliedRoleFK() 

    /**
     * Method getImpliedRolePKReturns the value of field
     * 'impliedRolePK'.
     * 
     * @return the value of field 'impliedRolePK'.
     */
    public long getImpliedRolePK()
    {
        return this._impliedRolePK;
    } //-- long getImpliedRolePK() 

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
     * Method hasImpliedRoleFK
     */
    public boolean hasImpliedRoleFK()
    {
        return this._has_impliedRoleFK;
    } //-- boolean hasImpliedRoleFK() 

    /**
     * Method hasImpliedRolePK
     */
    public boolean hasImpliedRolePK()
    {
        return this._has_impliedRolePK;
    } //-- boolean hasImpliedRolePK() 

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
     * Method setImpliedRoleFKSets the value of field
     * 'impliedRoleFK'.
     * 
     * @param impliedRoleFK the value of field 'impliedRoleFK'.
     */
    public void setImpliedRoleFK(long impliedRoleFK)
    {
        this._impliedRoleFK = impliedRoleFK;
        
        super.setVoChanged(true);
        this._has_impliedRoleFK = true;
    } //-- void setImpliedRoleFK(long) 

    /**
     * Method setImpliedRolePKSets the value of field
     * 'impliedRolePK'.
     * 
     * @param impliedRolePK the value of field 'impliedRolePK'.
     */
    public void setImpliedRolePK(long impliedRolePK)
    {
        this._impliedRolePK = impliedRolePK;
        
        super.setVoChanged(true);
        this._has_impliedRolePK = true;
    } //-- void setImpliedRolePK(long) 

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
    public static edit.common.vo.ImpliedRoleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ImpliedRoleVO) Unmarshaller.unmarshal(edit.common.vo.ImpliedRoleVO.class, reader);
    } //-- edit.common.vo.ImpliedRoleVO unmarshal(java.io.Reader) 

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
