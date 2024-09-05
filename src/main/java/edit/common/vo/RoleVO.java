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
 * Class RoleVO.
 * 
 * @version $Revision$ $Date$
 */
public class RoleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _rolePK
     */
    private long _rolePK;

    /**
     * keeps track of state for field: _rolePK
     */
    private boolean _has_rolePK;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _securedMethodVOList
     */
    private java.util.Vector _securedMethodVOList;

    /**
     * Field _impliedRoleVOList
     */
    private java.util.Vector _impliedRoleVOList;

    /**
     * Field _operatorRoleVOList
     */
    private java.util.Vector _operatorRoleVOList;

    /**
     * Field _filteredRoleVOList
     */
    private java.util.Vector _filteredRoleVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public RoleVO() {
        super();
        _securedMethodVOList = new Vector();
        _impliedRoleVOList = new Vector();
        _operatorRoleVOList = new Vector();
        _filteredRoleVOList = new Vector();
    } //-- edit.common.vo.RoleVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredRoleVO
     * 
     * @param vFilteredRoleVO
     */
    public void addFilteredRoleVO(edit.common.vo.FilteredRoleVO vFilteredRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredRoleVO.setParentVO(this.getClass(), this);
        _filteredRoleVOList.addElement(vFilteredRoleVO);
    } //-- void addFilteredRoleVO(edit.common.vo.FilteredRoleVO) 

    /**
     * Method addFilteredRoleVO
     * 
     * @param index
     * @param vFilteredRoleVO
     */
    public void addFilteredRoleVO(int index, edit.common.vo.FilteredRoleVO vFilteredRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredRoleVO.setParentVO(this.getClass(), this);
        _filteredRoleVOList.insertElementAt(vFilteredRoleVO, index);
    } //-- void addFilteredRoleVO(int, edit.common.vo.FilteredRoleVO) 

    /**
     * Method addImpliedRoleVO
     * 
     * @param vImpliedRoleVO
     */
    public void addImpliedRoleVO(edit.common.vo.ImpliedRoleVO vImpliedRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vImpliedRoleVO.setParentVO(this.getClass(), this);
        _impliedRoleVOList.addElement(vImpliedRoleVO);
    } //-- void addImpliedRoleVO(edit.common.vo.ImpliedRoleVO) 

    /**
     * Method addImpliedRoleVO
     * 
     * @param index
     * @param vImpliedRoleVO
     */
    public void addImpliedRoleVO(int index, edit.common.vo.ImpliedRoleVO vImpliedRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vImpliedRoleVO.setParentVO(this.getClass(), this);
        _impliedRoleVOList.insertElementAt(vImpliedRoleVO, index);
    } //-- void addImpliedRoleVO(int, edit.common.vo.ImpliedRoleVO) 

    /**
     * Method addOperatorRoleVO
     * 
     * @param vOperatorRoleVO
     */
    public void addOperatorRoleVO(edit.common.vo.OperatorRoleVO vOperatorRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOperatorRoleVO.setParentVO(this.getClass(), this);
        _operatorRoleVOList.addElement(vOperatorRoleVO);
    } //-- void addOperatorRoleVO(edit.common.vo.OperatorRoleVO) 

    /**
     * Method addOperatorRoleVO
     * 
     * @param index
     * @param vOperatorRoleVO
     */
    public void addOperatorRoleVO(int index, edit.common.vo.OperatorRoleVO vOperatorRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOperatorRoleVO.setParentVO(this.getClass(), this);
        _operatorRoleVOList.insertElementAt(vOperatorRoleVO, index);
    } //-- void addOperatorRoleVO(int, edit.common.vo.OperatorRoleVO) 

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
     * Method enumerateFilteredRoleVO
     */
    public java.util.Enumeration enumerateFilteredRoleVO()
    {
        return _filteredRoleVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredRoleVO() 

    /**
     * Method enumerateImpliedRoleVO
     */
    public java.util.Enumeration enumerateImpliedRoleVO()
    {
        return _impliedRoleVOList.elements();
    } //-- java.util.Enumeration enumerateImpliedRoleVO() 

    /**
     * Method enumerateOperatorRoleVO
     */
    public java.util.Enumeration enumerateOperatorRoleVO()
    {
        return _operatorRoleVOList.elements();
    } //-- java.util.Enumeration enumerateOperatorRoleVO() 

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
        
        if (obj instanceof RoleVO) {
        
            RoleVO temp = (RoleVO)obj;
            if (this._rolePK != temp._rolePK)
                return false;
            if (this._has_rolePK != temp._has_rolePK)
                return false;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            if (this._securedMethodVOList != null) {
                if (temp._securedMethodVOList == null) return false;
                else if (!(this._securedMethodVOList.equals(temp._securedMethodVOList))) 
                    return false;
            }
            else if (temp._securedMethodVOList != null)
                return false;
            if (this._impliedRoleVOList != null) {
                if (temp._impliedRoleVOList == null) return false;
                else if (!(this._impliedRoleVOList.equals(temp._impliedRoleVOList))) 
                    return false;
            }
            else if (temp._impliedRoleVOList != null)
                return false;
            if (this._operatorRoleVOList != null) {
                if (temp._operatorRoleVOList == null) return false;
                else if (!(this._operatorRoleVOList.equals(temp._operatorRoleVOList))) 
                    return false;
            }
            else if (temp._operatorRoleVOList != null)
                return false;
            if (this._filteredRoleVOList != null) {
                if (temp._filteredRoleVOList == null) return false;
                else if (!(this._filteredRoleVOList.equals(temp._filteredRoleVOList))) 
                    return false;
            }
            else if (temp._filteredRoleVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredRoleVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredRoleVO getFilteredRoleVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredRoleVO) _filteredRoleVOList.elementAt(index);
    } //-- edit.common.vo.FilteredRoleVO getFilteredRoleVO(int) 

    /**
     * Method getFilteredRoleVO
     */
    public edit.common.vo.FilteredRoleVO[] getFilteredRoleVO()
    {
        int size = _filteredRoleVOList.size();
        edit.common.vo.FilteredRoleVO[] mArray = new edit.common.vo.FilteredRoleVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredRoleVO) _filteredRoleVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredRoleVO[] getFilteredRoleVO() 

    /**
     * Method getFilteredRoleVOCount
     */
    public int getFilteredRoleVOCount()
    {
        return _filteredRoleVOList.size();
    } //-- int getFilteredRoleVOCount() 

    /**
     * Method getImpliedRoleVO
     * 
     * @param index
     */
    public edit.common.vo.ImpliedRoleVO getImpliedRoleVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _impliedRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ImpliedRoleVO) _impliedRoleVOList.elementAt(index);
    } //-- edit.common.vo.ImpliedRoleVO getImpliedRoleVO(int) 

    /**
     * Method getImpliedRoleVO
     */
    public edit.common.vo.ImpliedRoleVO[] getImpliedRoleVO()
    {
        int size = _impliedRoleVOList.size();
        edit.common.vo.ImpliedRoleVO[] mArray = new edit.common.vo.ImpliedRoleVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ImpliedRoleVO) _impliedRoleVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ImpliedRoleVO[] getImpliedRoleVO() 

    /**
     * Method getImpliedRoleVOCount
     */
    public int getImpliedRoleVOCount()
    {
        return _impliedRoleVOList.size();
    } //-- int getImpliedRoleVOCount() 

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
     * Method getOperatorRoleVO
     * 
     * @param index
     */
    public edit.common.vo.OperatorRoleVO getOperatorRoleVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _operatorRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OperatorRoleVO) _operatorRoleVOList.elementAt(index);
    } //-- edit.common.vo.OperatorRoleVO getOperatorRoleVO(int) 

    /**
     * Method getOperatorRoleVO
     */
    public edit.common.vo.OperatorRoleVO[] getOperatorRoleVO()
    {
        int size = _operatorRoleVOList.size();
        edit.common.vo.OperatorRoleVO[] mArray = new edit.common.vo.OperatorRoleVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OperatorRoleVO) _operatorRoleVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OperatorRoleVO[] getOperatorRoleVO() 

    /**
     * Method getOperatorRoleVOCount
     */
    public int getOperatorRoleVOCount()
    {
        return _operatorRoleVOList.size();
    } //-- int getOperatorRoleVOCount() 

    /**
     * Method getRolePKReturns the value of field 'rolePK'.
     * 
     * @return the value of field 'rolePK'.
     */
    public long getRolePK()
    {
        return this._rolePK;
    } //-- long getRolePK() 

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
     * Method hasRolePK
     */
    public boolean hasRolePK()
    {
        return this._has_rolePK;
    } //-- boolean hasRolePK() 

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
     * Method removeAllFilteredRoleVO
     */
    public void removeAllFilteredRoleVO()
    {
        _filteredRoleVOList.removeAllElements();
    } //-- void removeAllFilteredRoleVO() 

    /**
     * Method removeAllImpliedRoleVO
     */
    public void removeAllImpliedRoleVO()
    {
        _impliedRoleVOList.removeAllElements();
    } //-- void removeAllImpliedRoleVO() 

    /**
     * Method removeAllOperatorRoleVO
     */
    public void removeAllOperatorRoleVO()
    {
        _operatorRoleVOList.removeAllElements();
    } //-- void removeAllOperatorRoleVO() 

    /**
     * Method removeAllSecuredMethodVO
     */
    public void removeAllSecuredMethodVO()
    {
        _securedMethodVOList.removeAllElements();
    } //-- void removeAllSecuredMethodVO() 

    /**
     * Method removeFilteredRoleVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredRoleVO removeFilteredRoleVO(int index)
    {
        java.lang.Object obj = _filteredRoleVOList.elementAt(index);
        _filteredRoleVOList.removeElementAt(index);
        return (edit.common.vo.FilteredRoleVO) obj;
    } //-- edit.common.vo.FilteredRoleVO removeFilteredRoleVO(int) 

    /**
     * Method removeImpliedRoleVO
     * 
     * @param index
     */
    public edit.common.vo.ImpliedRoleVO removeImpliedRoleVO(int index)
    {
        java.lang.Object obj = _impliedRoleVOList.elementAt(index);
        _impliedRoleVOList.removeElementAt(index);
        return (edit.common.vo.ImpliedRoleVO) obj;
    } //-- edit.common.vo.ImpliedRoleVO removeImpliedRoleVO(int) 

    /**
     * Method removeOperatorRoleVO
     * 
     * @param index
     */
    public edit.common.vo.OperatorRoleVO removeOperatorRoleVO(int index)
    {
        java.lang.Object obj = _operatorRoleVOList.elementAt(index);
        _operatorRoleVOList.removeElementAt(index);
        return (edit.common.vo.OperatorRoleVO) obj;
    } //-- edit.common.vo.OperatorRoleVO removeOperatorRoleVO(int) 

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
     * Method setFilteredRoleVO
     * 
     * @param index
     * @param vFilteredRoleVO
     */
    public void setFilteredRoleVO(int index, edit.common.vo.FilteredRoleVO vFilteredRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredRoleVO.setParentVO(this.getClass(), this);
        _filteredRoleVOList.setElementAt(vFilteredRoleVO, index);
    } //-- void setFilteredRoleVO(int, edit.common.vo.FilteredRoleVO) 

    /**
     * Method setFilteredRoleVO
     * 
     * @param filteredRoleVOArray
     */
    public void setFilteredRoleVO(edit.common.vo.FilteredRoleVO[] filteredRoleVOArray)
    {
        //-- copy array
        _filteredRoleVOList.removeAllElements();
        for (int i = 0; i < filteredRoleVOArray.length; i++) {
            filteredRoleVOArray[i].setParentVO(this.getClass(), this);
            _filteredRoleVOList.addElement(filteredRoleVOArray[i]);
        }
    } //-- void setFilteredRoleVO(edit.common.vo.FilteredRoleVO) 

    /**
     * Method setImpliedRoleVO
     * 
     * @param index
     * @param vImpliedRoleVO
     */
    public void setImpliedRoleVO(int index, edit.common.vo.ImpliedRoleVO vImpliedRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _impliedRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vImpliedRoleVO.setParentVO(this.getClass(), this);
        _impliedRoleVOList.setElementAt(vImpliedRoleVO, index);
    } //-- void setImpliedRoleVO(int, edit.common.vo.ImpliedRoleVO) 

    /**
     * Method setImpliedRoleVO
     * 
     * @param impliedRoleVOArray
     */
    public void setImpliedRoleVO(edit.common.vo.ImpliedRoleVO[] impliedRoleVOArray)
    {
        //-- copy array
        _impliedRoleVOList.removeAllElements();
        for (int i = 0; i < impliedRoleVOArray.length; i++) {
            impliedRoleVOArray[i].setParentVO(this.getClass(), this);
            _impliedRoleVOList.addElement(impliedRoleVOArray[i]);
        }
    } //-- void setImpliedRoleVO(edit.common.vo.ImpliedRoleVO) 

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
     * Method setOperatorRoleVO
     * 
     * @param index
     * @param vOperatorRoleVO
     */
    public void setOperatorRoleVO(int index, edit.common.vo.OperatorRoleVO vOperatorRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _operatorRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOperatorRoleVO.setParentVO(this.getClass(), this);
        _operatorRoleVOList.setElementAt(vOperatorRoleVO, index);
    } //-- void setOperatorRoleVO(int, edit.common.vo.OperatorRoleVO) 

    /**
     * Method setOperatorRoleVO
     * 
     * @param operatorRoleVOArray
     */
    public void setOperatorRoleVO(edit.common.vo.OperatorRoleVO[] operatorRoleVOArray)
    {
        //-- copy array
        _operatorRoleVOList.removeAllElements();
        for (int i = 0; i < operatorRoleVOArray.length; i++) {
            operatorRoleVOArray[i].setParentVO(this.getClass(), this);
            _operatorRoleVOList.addElement(operatorRoleVOArray[i]);
        }
    } //-- void setOperatorRoleVO(edit.common.vo.OperatorRoleVO) 

    /**
     * Method setRolePKSets the value of field 'rolePK'.
     * 
     * @param rolePK the value of field 'rolePK'.
     */
    public void setRolePK(long rolePK)
    {
        this._rolePK = rolePK;
        
        super.setVoChanged(true);
        this._has_rolePK = true;
    } //-- void setRolePK(long) 

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
    public static edit.common.vo.RoleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RoleVO) Unmarshaller.unmarshal(edit.common.vo.RoleVO.class, reader);
    } //-- edit.common.vo.RoleVO unmarshal(java.io.Reader) 

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
