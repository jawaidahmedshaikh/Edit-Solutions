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
public class ExtractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _reservesType
     */
    private java.lang.String _reservesType;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _extractIdVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ExtractVO() {
        super();
        _extractIdVOList = new Vector();
    } //-- edit.common.vo.ExtractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addExtractIdVO
     * 
     * @param vExtractIdVO
     */
    public void addExtractIdVO(edit.common.vo.ExtractIdVO vExtractIdVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vExtractIdVO.setParentVO(this.getClass(), this);
        _extractIdVOList.addElement(vExtractIdVO);
    } //-- void addExtractIdVO(edit.common.vo.ExtractIdVO) 

    /**
     * Method addExtractIdVO
     * 
     * @param index
     * @param vExtractIdVO
     */
    public void addExtractIdVO(int index, edit.common.vo.ExtractIdVO vExtractIdVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vExtractIdVO.setParentVO(this.getClass(), this);
        _extractIdVOList.insertElementAt(vExtractIdVO, index);
    } //-- void addExtractIdVO(int, edit.common.vo.ExtractIdVO) 

    /**
     * Method enumerateExtractIdVO
     */
    public java.util.Enumeration enumerateExtractIdVO()
    {
        return _extractIdVOList.elements();
    } //-- java.util.Enumeration enumerateExtractIdVO() 

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
        
        if (obj instanceof ExtractVO) {
        
            ExtractVO temp = (ExtractVO)obj;
            if (this._reservesType != null) {
                if (temp._reservesType == null) return false;
                else if (!(this._reservesType.equals(temp._reservesType))) 
                    return false;
            }
            else if (temp._reservesType != null)
                return false;
            if (this._extractIdVOList != null) {
                if (temp._extractIdVOList == null) return false;
                else if (!(this._extractIdVOList.equals(temp._extractIdVOList))) 
                    return false;
            }
            else if (temp._extractIdVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getExtractIdVO
     * 
     * @param index
     */
    public edit.common.vo.ExtractIdVO getExtractIdVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _extractIdVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ExtractIdVO) _extractIdVOList.elementAt(index);
    } //-- edit.common.vo.ExtractIdVO getExtractIdVO(int) 

    /**
     * Method getExtractIdVO
     */
    public edit.common.vo.ExtractIdVO[] getExtractIdVO()
    {
        int size = _extractIdVOList.size();
        edit.common.vo.ExtractIdVO[] mArray = new edit.common.vo.ExtractIdVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ExtractIdVO) _extractIdVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ExtractIdVO[] getExtractIdVO() 

    /**
     * Method getExtractIdVOCount
     */
    public int getExtractIdVOCount()
    {
        return _extractIdVOList.size();
    } //-- int getExtractIdVOCount() 

    /**
     * Method getReservesTypeReturns the value of field
     * 'reservesType'.
     * 
     * @return the value of field 'reservesType'.
     */
    public java.lang.String getReservesType()
    {
        return this._reservesType;
    } //-- java.lang.String getReservesType() 

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
     * Method removeAllExtractIdVO
     */
    public void removeAllExtractIdVO()
    {
        _extractIdVOList.removeAllElements();
    } //-- void removeAllExtractIdVO() 

    /**
     * Method removeExtractIdVO
     * 
     * @param index
     */
    public edit.common.vo.ExtractIdVO removeExtractIdVO(int index)
    {
        java.lang.Object obj = _extractIdVOList.elementAt(index);
        _extractIdVOList.removeElementAt(index);
        return (edit.common.vo.ExtractIdVO) obj;
    } //-- edit.common.vo.ExtractIdVO removeExtractIdVO(int) 

    /**
     * Method setExtractIdVO
     * 
     * @param index
     * @param vExtractIdVO
     */
    public void setExtractIdVO(int index, edit.common.vo.ExtractIdVO vExtractIdVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _extractIdVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vExtractIdVO.setParentVO(this.getClass(), this);
        _extractIdVOList.setElementAt(vExtractIdVO, index);
    } //-- void setExtractIdVO(int, edit.common.vo.ExtractIdVO) 

    /**
     * Method setExtractIdVO
     * 
     * @param extractIdVOArray
     */
    public void setExtractIdVO(edit.common.vo.ExtractIdVO[] extractIdVOArray)
    {
        //-- copy array
        _extractIdVOList.removeAllElements();
        for (int i = 0; i < extractIdVOArray.length; i++) {
            extractIdVOArray[i].setParentVO(this.getClass(), this);
            _extractIdVOList.addElement(extractIdVOArray[i]);
        }
    } //-- void setExtractIdVO(edit.common.vo.ExtractIdVO) 

    /**
     * Method setReservesTypeSets the value of field
     * 'reservesType'.
     * 
     * @param reservesType the value of field 'reservesType'.
     */
    public void setReservesType(java.lang.String reservesType)
    {
        this._reservesType = reservesType;
        
        super.setVoChanged(true);
    } //-- void setReservesType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ExtractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ExtractVO) Unmarshaller.unmarshal(edit.common.vo.ExtractVO.class, reader);
    } //-- edit.common.vo.ExtractVO unmarshal(java.io.Reader) 

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
