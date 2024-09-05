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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class FilteredRoleVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredRoleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredRolePK
     */
    private long _filteredRolePK;

    /**
     * keeps track of state for field: _filteredRolePK
     */
    private boolean _has_filteredRolePK;

    /**
     * Field _roleFK
     */
    private long _roleFK;

    /**
     * keeps track of state for field: _roleFK
     */
    private boolean _has_roleFK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _securedMethodVOList
     */
    private java.util.Vector _securedMethodVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredRoleVO() {
        super();
        _securedMethodVOList = new Vector();
    } //-- edit.common.vo.FilteredRoleVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSecuredMethodVO
     * 
     * @param vSecuredMethodVO
     */
    public void addSecuredMethodVO(edit.common.vo.SecuredMethodVO vSecuredMethodVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSecuredMethodVO.setParentVO(this.getClass(), this);
        _securedMethodVOList.addElement(vSecuredMethodVO);
    } //-- void addSecuredMethodVO(edit.common.vo.SecuredMethodVO) 

    /**
     * Method addSecuredMethodVO
     * 
     * @param index
     * @param vSecuredMethodVO
     */
    public void addSecuredMethodVO(int index, edit.common.vo.SecuredMethodVO vSecuredMethodVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSecuredMethodVO.setParentVO(this.getClass(), this);
        _securedMethodVOList.insertElementAt(vSecuredMethodVO, index);
    } //-- void addSecuredMethodVO(int, edit.common.vo.SecuredMethodVO) 

    /**
     * Method enumerateSecuredMethodVO
     */
    public java.util.Enumeration enumerateSecuredMethodVO()
    {
        return _securedMethodVOList.elements();
    } //-- java.util.Enumeration enumerateSecuredMethodVO() 

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
        
        if (obj instanceof FilteredRoleVO) {
        
            FilteredRoleVO temp = (FilteredRoleVO)obj;
            if (this._filteredRolePK != temp._filteredRolePK)
                return false;
            if (this._has_filteredRolePK != temp._has_filteredRolePK)
                return false;
            if (this._roleFK != temp._roleFK)
                return false;
            if (this._has_roleFK != temp._has_roleFK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._securedMethodVOList != null) {
                if (temp._securedMethodVOList == null) return false;
                else if (!(this._securedMethodVOList.equals(temp._securedMethodVOList))) 
                    return false;
            }
            else if (temp._securedMethodVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredRolePKReturns the value of field
     * 'filteredRolePK'.
     * 
     * @return the value of field 'filteredRolePK'.
     */
    public long getFilteredRolePK()
    {
        return this._filteredRolePK;
    } //-- long getFilteredRolePK() 

    /**
     * Method getProductStructureFKReturns the value of field
     * 'productStructureFK'.
     * 
     * @return the value of field 'productStructureFK'.
     */
    public long getProductStructureFK()
    {
        return this._productStructureFK;
    } //-- long getProductStructureFK() 

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
     * Method getSecuredMethodVO
     * 
     * @param index
     */
    public edit.common.vo.SecuredMethodVO getSecuredMethodVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _securedMethodVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SecuredMethodVO) _securedMethodVOList.elementAt(index);
    } //-- edit.common.vo.SecuredMethodVO getSecuredMethodVO(int) 

    /**
     * Method getSecuredMethodVO
     */
    public edit.common.vo.SecuredMethodVO[] getSecuredMethodVO()
    {
        int size = _securedMethodVOList.size();
        edit.common.vo.SecuredMethodVO[] mArray = new edit.common.vo.SecuredMethodVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SecuredMethodVO) _securedMethodVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SecuredMethodVO[] getSecuredMethodVO() 

    /**
     * Method getSecuredMethodVOCount
     */
    public int getSecuredMethodVOCount()
    {
        return _securedMethodVOList.size();
    } //-- int getSecuredMethodVOCount() 

    /**
     * Method hasFilteredRolePK
     */
    public boolean hasFilteredRolePK()
    {
        return this._has_filteredRolePK;
    } //-- boolean hasFilteredRolePK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

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
     * Method removeAllSecuredMethodVO
     */
    public void removeAllSecuredMethodVO()
    {
        _securedMethodVOList.removeAllElements();
    } //-- void removeAllSecuredMethodVO() 

    /**
     * Method removeSecuredMethodVO
     * 
     * @param index
     */
    public edit.common.vo.SecuredMethodVO removeSecuredMethodVO(int index)
    {
        java.lang.Object obj = _securedMethodVOList.elementAt(index);
        _securedMethodVOList.removeElementAt(index);
        return (edit.common.vo.SecuredMethodVO) obj;
    } //-- edit.common.vo.SecuredMethodVO removeSecuredMethodVO(int) 

    /**
     * Method setFilteredRolePKSets the value of field
     * 'filteredRolePK'.
     * 
     * @param filteredRolePK the value of field 'filteredRolePK'.
     */
    public void setFilteredRolePK(long filteredRolePK)
    {
        this._filteredRolePK = filteredRolePK;
        
        super.setVoChanged(true);
        this._has_filteredRolePK = true;
    } //-- void setFilteredRolePK(long) 

    /**
     * Method setProductStructureFKSets the value of field
     * 'productStructureFK'.
     * 
     * @param productStructureFK the value of field
     * 'productStructureFK'.
     */
    public void setProductStructureFK(long productStructureFK)
    {
        this._productStructureFK = productStructureFK;
        
        super.setVoChanged(true);
        this._has_productStructureFK = true;
    } //-- void setProductStructureFK(long) 

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
     * Method setSecuredMethodVO
     * 
     * @param index
     * @param vSecuredMethodVO
     */
    public void setSecuredMethodVO(int index, edit.common.vo.SecuredMethodVO vSecuredMethodVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _securedMethodVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSecuredMethodVO.setParentVO(this.getClass(), this);
        _securedMethodVOList.setElementAt(vSecuredMethodVO, index);
    } //-- void setSecuredMethodVO(int, edit.common.vo.SecuredMethodVO) 

    /**
     * Method setSecuredMethodVO
     * 
     * @param securedMethodVOArray
     */
    public void setSecuredMethodVO(edit.common.vo.SecuredMethodVO[] securedMethodVOArray)
    {
        //-- copy array
        _securedMethodVOList.removeAllElements();
        for (int i = 0; i < securedMethodVOArray.length; i++) {
            securedMethodVOArray[i].setParentVO(this.getClass(), this);
            _securedMethodVOList.addElement(securedMethodVOArray[i]);
        }
    } //-- void setSecuredMethodVO(edit.common.vo.SecuredMethodVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FilteredRoleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredRoleVO) Unmarshaller.unmarshal(edit.common.vo.FilteredRoleVO.class, reader);
    } //-- edit.common.vo.FilteredRoleVO unmarshal(java.io.Reader) 

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
