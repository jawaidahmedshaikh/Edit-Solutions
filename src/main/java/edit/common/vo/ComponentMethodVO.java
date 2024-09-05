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
 * Class ComponentMethodVO.
 * 
 * @version $Revision$ $Date$
 */
public class ComponentMethodVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _componentMethodPK
     */
    private long _componentMethodPK;

    /**
     * keeps track of state for field: _componentMethodPK
     */
    private boolean _has_componentMethodPK;

    /**
     * Field _componentNameCT
     */
    private java.lang.String _componentNameCT;

    /**
     * Field _componentClassName
     */
    private java.lang.String _componentClassName;

    /**
     * Field _methodName
     */
    private java.lang.String _methodName;

    /**
     * Field _securedMethodVOList
     */
    private java.util.Vector _securedMethodVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ComponentMethodVO() {
        super();
        _securedMethodVOList = new Vector();
    } //-- edit.common.vo.ComponentMethodVO()


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
        
        if (obj instanceof ComponentMethodVO) {
        
            ComponentMethodVO temp = (ComponentMethodVO)obj;
            if (this._componentMethodPK != temp._componentMethodPK)
                return false;
            if (this._has_componentMethodPK != temp._has_componentMethodPK)
                return false;
            if (this._componentNameCT != null) {
                if (temp._componentNameCT == null) return false;
                else if (!(this._componentNameCT.equals(temp._componentNameCT))) 
                    return false;
            }
            else if (temp._componentNameCT != null)
                return false;
            if (this._componentClassName != null) {
                if (temp._componentClassName == null) return false;
                else if (!(this._componentClassName.equals(temp._componentClassName))) 
                    return false;
            }
            else if (temp._componentClassName != null)
                return false;
            if (this._methodName != null) {
                if (temp._methodName == null) return false;
                else if (!(this._methodName.equals(temp._methodName))) 
                    return false;
            }
            else if (temp._methodName != null)
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
     * Method getComponentClassNameReturns the value of field
     * 'componentClassName'.
     * 
     * @return the value of field 'componentClassName'.
     */
    public java.lang.String getComponentClassName()
    {
        return this._componentClassName;
    } //-- java.lang.String getComponentClassName() 

    /**
     * Method getComponentMethodPKReturns the value of field
     * 'componentMethodPK'.
     * 
     * @return the value of field 'componentMethodPK'.
     */
    public long getComponentMethodPK()
    {
        return this._componentMethodPK;
    } //-- long getComponentMethodPK() 

    /**
     * Method getComponentNameCTReturns the value of field
     * 'componentNameCT'.
     * 
     * @return the value of field 'componentNameCT'.
     */
    public java.lang.String getComponentNameCT()
    {
        return this._componentNameCT;
    } //-- java.lang.String getComponentNameCT() 

    /**
     * Method getMethodNameReturns the value of field 'methodName'.
     * 
     * @return the value of field 'methodName'.
     */
    public java.lang.String getMethodName()
    {
        return this._methodName;
    } //-- java.lang.String getMethodName() 

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
     * Method hasComponentMethodPK
     */
    public boolean hasComponentMethodPK()
    {
        return this._has_componentMethodPK;
    } //-- boolean hasComponentMethodPK() 

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
     * Method setComponentClassNameSets the value of field
     * 'componentClassName'.
     * 
     * @param componentClassName the value of field
     * 'componentClassName'.
     */
    public void setComponentClassName(java.lang.String componentClassName)
    {
        this._componentClassName = componentClassName;
        
        super.setVoChanged(true);
    } //-- void setComponentClassName(java.lang.String) 

    /**
     * Method setComponentMethodPKSets the value of field
     * 'componentMethodPK'.
     * 
     * @param componentMethodPK the value of field
     * 'componentMethodPK'.
     */
    public void setComponentMethodPK(long componentMethodPK)
    {
        this._componentMethodPK = componentMethodPK;
        
        super.setVoChanged(true);
        this._has_componentMethodPK = true;
    } //-- void setComponentMethodPK(long) 

    /**
     * Method setComponentNameCTSets the value of field
     * 'componentNameCT'.
     * 
     * @param componentNameCT the value of field 'componentNameCT'.
     */
    public void setComponentNameCT(java.lang.String componentNameCT)
    {
        this._componentNameCT = componentNameCT;
        
        super.setVoChanged(true);
    } //-- void setComponentNameCT(java.lang.String) 

    /**
     * Method setMethodNameSets the value of field 'methodName'.
     * 
     * @param methodName the value of field 'methodName'.
     */
    public void setMethodName(java.lang.String methodName)
    {
        this._methodName = methodName;
        
        super.setVoChanged(true);
    } //-- void setMethodName(java.lang.String) 

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
    public static edit.common.vo.ComponentMethodVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ComponentMethodVO) Unmarshaller.unmarshal(edit.common.vo.ComponentMethodVO.class, reader);
    } //-- edit.common.vo.ComponentMethodVO unmarshal(java.io.Reader) 

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
