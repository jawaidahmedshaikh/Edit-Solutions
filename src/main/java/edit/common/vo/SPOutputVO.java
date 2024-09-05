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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class SPOutputVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _validationVOList
     */
    private java.util.Vector _validationVOList;

    /**
     * Field _VOObjectList
     */
    private java.util.Vector _VOObjectList;

    /**
     * Field _processedWithErrors
     */
    private boolean _processedWithErrors;

    /**
     * keeps track of state for field: _processedWithErrors
     */
    private boolean _has_processedWithErrors;


      //----------------/
     //- Constructors -/
    //----------------/

    public SPOutputVO() {
        super();
        _validationVOList = new Vector();
        _VOObjectList = new Vector();
    } //-- edit.common.vo.SPOutputVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addVOObject
     * 
     * @param vVOObject
     */
    public void addVOObject(edit.common.vo.VOObject vVOObject)
        throws java.lang.IndexOutOfBoundsException
    {
        _VOObjectList.addElement(vVOObject);
    } //-- void addVOObject(edit.common.vo.VOObject) 

    /**
     * Method addVOObject
     * 
     * @param index
     * @param vVOObject
     */
    public void addVOObject(int index, edit.common.vo.VOObject vVOObject)
        throws java.lang.IndexOutOfBoundsException
    {
        _VOObjectList.insertElementAt(vVOObject, index);
    } //-- void addVOObject(int, edit.common.vo.VOObject) 

    /**
     * Method addValidationVO
     * 
     * @param vValidationVO
     */
    public void addValidationVO(edit.common.vo.ValidationVO vValidationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vValidationVO.setParentVO(this.getClass(), this);
        _validationVOList.addElement(vValidationVO);
    } //-- void addValidationVO(edit.common.vo.ValidationVO) 

    /**
     * Method addValidationVO
     * 
     * @param index
     * @param vValidationVO
     */
    public void addValidationVO(int index, edit.common.vo.ValidationVO vValidationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vValidationVO.setParentVO(this.getClass(), this);
        _validationVOList.insertElementAt(vValidationVO, index);
    } //-- void addValidationVO(int, edit.common.vo.ValidationVO) 

    /**
     * Method enumerateVOObject
     */
    public java.util.Enumeration enumerateVOObject()
    {
        return _VOObjectList.elements();
    } //-- java.util.Enumeration enumerateVOObject() 

    /**
     * Method enumerateValidationVO
     */
    public java.util.Enumeration enumerateValidationVO()
    {
        return _validationVOList.elements();
    } //-- java.util.Enumeration enumerateValidationVO() 

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
        
        if (obj instanceof SPOutputVO) {
        
            SPOutputVO temp = (SPOutputVO)obj;
            if (this._validationVOList != null) {
                if (temp._validationVOList == null) return false;
                else if (!(this._validationVOList.equals(temp._validationVOList))) 
                    return false;
            }
            else if (temp._validationVOList != null)
                return false;
            if (this._VOObjectList != null) {
                if (temp._VOObjectList == null) return false;
                else if (!(this._VOObjectList.equals(temp._VOObjectList))) 
                    return false;
            }
            else if (temp._VOObjectList != null)
                return false;
            if (this._processedWithErrors != temp._processedWithErrors)
                return false;
            if (this._has_processedWithErrors != temp._has_processedWithErrors)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getProcessedWithErrorsReturns the value of field
     * 'processedWithErrors'.
     * 
     * @return the value of field 'processedWithErrors'.
     */
    public boolean getProcessedWithErrors()
    {
        return this._processedWithErrors;
    } //-- boolean getProcessedWithErrors() 

    /**
     * Method getVOObject
     * 
     * @param index
     */
    public edit.common.vo.VOObject getVOObject(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _VOObjectList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.VOObject) _VOObjectList.elementAt(index);
    } //-- edit.common.vo.VOObject getVOObject(int) 

    /**
     * Method getVOObject
     */
    public edit.common.vo.VOObject[] getVOObject()
    {
        int size = _VOObjectList.size();
        edit.common.vo.VOObject[] mArray = new edit.common.vo.VOObject[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.VOObject) _VOObjectList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.VOObject[] getVOObject() 

    /**
     * Method getVOObjectCount
     */
    public int getVOObjectCount()
    {
        return _VOObjectList.size();
    } //-- int getVOObjectCount() 

    /**
     * Method getValidationVO
     * 
     * @param index
     */
    public edit.common.vo.ValidationVO getValidationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _validationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ValidationVO) _validationVOList.elementAt(index);
    } //-- edit.common.vo.ValidationVO getValidationVO(int) 

    /**
     * Method getValidationVO
     */
    public edit.common.vo.ValidationVO[] getValidationVO()
    {
        int size = _validationVOList.size();
        edit.common.vo.ValidationVO[] mArray = new edit.common.vo.ValidationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ValidationVO) _validationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ValidationVO[] getValidationVO() 

    /**
     * Method getValidationVOCount
     */
    public int getValidationVOCount()
    {
        return _validationVOList.size();
    } //-- int getValidationVOCount() 

    /**
     * Method hasProcessedWithErrors
     */
    public boolean hasProcessedWithErrors()
    {
        return this._has_processedWithErrors;
    } //-- boolean hasProcessedWithErrors() 

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
     * Method removeAllVOObject
     */
    public void removeAllVOObject()
    {
        _VOObjectList.removeAllElements();
    } //-- void removeAllVOObject() 

    /**
     * Method removeAllValidationVO
     */
    public void removeAllValidationVO()
    {
        _validationVOList.removeAllElements();
    } //-- void removeAllValidationVO() 

    /**
     * Method removeVOObject
     * 
     * @param index
     */
    public edit.common.vo.VOObject removeVOObject(int index)
    {
        java.lang.Object obj = _VOObjectList.elementAt(index);
        _VOObjectList.removeElementAt(index);
        return (edit.common.vo.VOObject) obj;
    } //-- edit.common.vo.VOObject removeVOObject(int) 

    /**
     * Method removeValidationVO
     * 
     * @param index
     */
    public edit.common.vo.ValidationVO removeValidationVO(int index)
    {
        java.lang.Object obj = _validationVOList.elementAt(index);
        _validationVOList.removeElementAt(index);
        return (edit.common.vo.ValidationVO) obj;
    } //-- edit.common.vo.ValidationVO removeValidationVO(int) 

    /**
     * Method setProcessedWithErrorsSets the value of field
     * 'processedWithErrors'.
     * 
     * @param processedWithErrors the value of field
     * 'processedWithErrors'.
     */
    public void setProcessedWithErrors(boolean processedWithErrors)
    {
        this._processedWithErrors = processedWithErrors;
        
        super.setVoChanged(true);
        this._has_processedWithErrors = true;
    } //-- void setProcessedWithErrors(boolean) 

    /**
     * Method setVOObject
     * 
     * @param index
     * @param vVOObject
     */
    public void setVOObject(int index, edit.common.vo.VOObject vVOObject)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _VOObjectList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _VOObjectList.setElementAt(vVOObject, index);
    } //-- void setVOObject(int, edit.common.vo.VOObject) 

    /**
     * Method setVOObject
     * 
     * @param VOObjectArray
     */
    public void setVOObject(edit.common.vo.VOObject[] VOObjectArray)
    {
        //-- copy array
        _VOObjectList.removeAllElements();
        for (int i = 0; i < VOObjectArray.length; i++) {
            _VOObjectList.addElement(VOObjectArray[i]);
        }
    } //-- void setVOObject(edit.common.vo.VOObject) 

    /**
     * Method setValidationVO
     * 
     * @param index
     * @param vValidationVO
     */
    public void setValidationVO(int index, edit.common.vo.ValidationVO vValidationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _validationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vValidationVO.setParentVO(this.getClass(), this);
        _validationVOList.setElementAt(vValidationVO, index);
    } //-- void setValidationVO(int, edit.common.vo.ValidationVO) 

    /**
     * Method setValidationVO
     * 
     * @param validationVOArray
     */
    public void setValidationVO(edit.common.vo.ValidationVO[] validationVOArray)
    {
        //-- copy array
        _validationVOList.removeAllElements();
        for (int i = 0; i < validationVOArray.length; i++) {
            validationVOArray[i].setParentVO(this.getClass(), this);
            _validationVOList.addElement(validationVOArray[i]);
        }
    } //-- void setValidationVO(edit.common.vo.ValidationVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SPOutputVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SPOutputVO) Unmarshaller.unmarshal(edit.common.vo.SPOutputVO.class, reader);
    } //-- edit.common.vo.SPOutputVO unmarshal(java.io.Reader) 

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
