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
 * Class SecuredMethodVO.
 * 
 * @version $Revision$ $Date$
 */
public class SecuredMethodVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _securedMethodPK
     */
    private long _securedMethodPK;

    /**
     * keeps track of state for field: _securedMethodPK
     */
    private boolean _has_securedMethodPK;

    /**
     * Field _componentMethodFK
     */
    private long _componentMethodFK;

    /**
     * keeps track of state for field: _componentMethodFK
     */
    private boolean _has_componentMethodFK;

    /**
     * Field _roleFK
     */
    private long _roleFK;

    /**
     * keeps track of state for field: _roleFK
     */
    private boolean _has_roleFK;

    /**
     * Field _filteredRoleFK
     */
    private long _filteredRoleFK;

    /**
     * keeps track of state for field: _filteredRoleFK
     */
    private boolean _has_filteredRoleFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public SecuredMethodVO() {
        super();
    } //-- edit.common.vo.SecuredMethodVO()


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
        
        if (obj instanceof SecuredMethodVO) {
        
            SecuredMethodVO temp = (SecuredMethodVO)obj;
            if (this._securedMethodPK != temp._securedMethodPK)
                return false;
            if (this._has_securedMethodPK != temp._has_securedMethodPK)
                return false;
            if (this._componentMethodFK != temp._componentMethodFK)
                return false;
            if (this._has_componentMethodFK != temp._has_componentMethodFK)
                return false;
            if (this._roleFK != temp._roleFK)
                return false;
            if (this._has_roleFK != temp._has_roleFK)
                return false;
            if (this._filteredRoleFK != temp._filteredRoleFK)
                return false;
            if (this._has_filteredRoleFK != temp._has_filteredRoleFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getComponentMethodFKReturns the value of field
     * 'componentMethodFK'.
     * 
     * @return the value of field 'componentMethodFK'.
     */
    public long getComponentMethodFK()
    {
        return this._componentMethodFK;
    } //-- long getComponentMethodFK() 

    /**
     * Method getFilteredRoleFKReturns the value of field
     * 'filteredRoleFK'.
     * 
     * @return the value of field 'filteredRoleFK'.
     */
    public long getFilteredRoleFK()
    {
        return this._filteredRoleFK;
    } //-- long getFilteredRoleFK() 

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
     * Method getSecuredMethodPKReturns the value of field
     * 'securedMethodPK'.
     * 
     * @return the value of field 'securedMethodPK'.
     */
    public long getSecuredMethodPK()
    {
        return this._securedMethodPK;
    } //-- long getSecuredMethodPK() 

    /**
     * Method hasComponentMethodFK
     */
    public boolean hasComponentMethodFK()
    {
        return this._has_componentMethodFK;
    } //-- boolean hasComponentMethodFK() 

    /**
     * Method hasFilteredRoleFK
     */
    public boolean hasFilteredRoleFK()
    {
        return this._has_filteredRoleFK;
    } //-- boolean hasFilteredRoleFK() 

    /**
     * Method hasRoleFK
     */
    public boolean hasRoleFK()
    {
        return this._has_roleFK;
    } //-- boolean hasRoleFK() 

    /**
     * Method hasSecuredMethodPK
     */
    public boolean hasSecuredMethodPK()
    {
        return this._has_securedMethodPK;
    } //-- boolean hasSecuredMethodPK() 

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
     * Method setComponentMethodFKSets the value of field
     * 'componentMethodFK'.
     * 
     * @param componentMethodFK the value of field
     * 'componentMethodFK'.
     */
    public void setComponentMethodFK(long componentMethodFK)
    {
        this._componentMethodFK = componentMethodFK;
        
        super.setVoChanged(true);
        this._has_componentMethodFK = true;
    } //-- void setComponentMethodFK(long) 

    /**
     * Method setFilteredRoleFKSets the value of field
     * 'filteredRoleFK'.
     * 
     * @param filteredRoleFK the value of field 'filteredRoleFK'.
     */
    public void setFilteredRoleFK(long filteredRoleFK)
    {
        this._filteredRoleFK = filteredRoleFK;
        
        super.setVoChanged(true);
        this._has_filteredRoleFK = true;
    } //-- void setFilteredRoleFK(long) 

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
     * Method setSecuredMethodPKSets the value of field
     * 'securedMethodPK'.
     * 
     * @param securedMethodPK the value of field 'securedMethodPK'.
     */
    public void setSecuredMethodPK(long securedMethodPK)
    {
        this._securedMethodPK = securedMethodPK;
        
        super.setVoChanged(true);
        this._has_securedMethodPK = true;
    } //-- void setSecuredMethodPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SecuredMethodVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SecuredMethodVO) Unmarshaller.unmarshal(edit.common.vo.SecuredMethodVO.class, reader);
    } //-- edit.common.vo.SecuredMethodVO unmarshal(java.io.Reader) 

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
