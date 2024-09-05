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
 * Class AreaKeyVO.
 * 
 * @version $Revision$ $Date$
 */
public class AreaKeyVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _areaKeyPK
     */
    private long _areaKeyPK;

    /**
     * keeps track of state for field: _areaKeyPK
     */
    private boolean _has_areaKeyPK;

    /**
     * Field _grouping
     */
    private java.lang.String _grouping;

    /**
     * Field _field
     */
    private java.lang.String _field;

    /**
     * Field _areaValueVOList
     */
    private java.util.Vector _areaValueVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AreaKeyVO() {
        super();
        _areaValueVOList = new Vector();
    } //-- edit.common.vo.AreaKeyVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAreaValueVO
     * 
     * @param vAreaValueVO
     */
    public void addAreaValueVO(edit.common.vo.AreaValueVO vAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAreaValueVO.setParentVO(this.getClass(), this);
        _areaValueVOList.addElement(vAreaValueVO);
    } //-- void addAreaValueVO(edit.common.vo.AreaValueVO) 

    /**
     * Method addAreaValueVO
     * 
     * @param index
     * @param vAreaValueVO
     */
    public void addAreaValueVO(int index, edit.common.vo.AreaValueVO vAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAreaValueVO.setParentVO(this.getClass(), this);
        _areaValueVOList.insertElementAt(vAreaValueVO, index);
    } //-- void addAreaValueVO(int, edit.common.vo.AreaValueVO) 

    /**
     * Method enumerateAreaValueVO
     */
    public java.util.Enumeration enumerateAreaValueVO()
    {
        return _areaValueVOList.elements();
    } //-- java.util.Enumeration enumerateAreaValueVO() 

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
        
        if (obj instanceof AreaKeyVO) {
        
            AreaKeyVO temp = (AreaKeyVO)obj;
            if (this._areaKeyPK != temp._areaKeyPK)
                return false;
            if (this._has_areaKeyPK != temp._has_areaKeyPK)
                return false;
            if (this._grouping != null) {
                if (temp._grouping == null) return false;
                else if (!(this._grouping.equals(temp._grouping))) 
                    return false;
            }
            else if (temp._grouping != null)
                return false;
            if (this._field != null) {
                if (temp._field == null) return false;
                else if (!(this._field.equals(temp._field))) 
                    return false;
            }
            else if (temp._field != null)
                return false;
            if (this._areaValueVOList != null) {
                if (temp._areaValueVOList == null) return false;
                else if (!(this._areaValueVOList.equals(temp._areaValueVOList))) 
                    return false;
            }
            else if (temp._areaValueVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAreaKeyPKReturns the value of field 'areaKeyPK'.
     * 
     * @return the value of field 'areaKeyPK'.
     */
    public long getAreaKeyPK()
    {
        return this._areaKeyPK;
    } //-- long getAreaKeyPK() 

    /**
     * Method getAreaValueVO
     * 
     * @param index
     */
    public edit.common.vo.AreaValueVO getAreaValueVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _areaValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AreaValueVO) _areaValueVOList.elementAt(index);
    } //-- edit.common.vo.AreaValueVO getAreaValueVO(int) 

    /**
     * Method getAreaValueVO
     */
    public edit.common.vo.AreaValueVO[] getAreaValueVO()
    {
        int size = _areaValueVOList.size();
        edit.common.vo.AreaValueVO[] mArray = new edit.common.vo.AreaValueVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AreaValueVO) _areaValueVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AreaValueVO[] getAreaValueVO() 

    /**
     * Method getAreaValueVOCount
     */
    public int getAreaValueVOCount()
    {
        return _areaValueVOList.size();
    } //-- int getAreaValueVOCount() 

    /**
     * Method getFieldReturns the value of field 'field'.
     * 
     * @return the value of field 'field'.
     */
    public java.lang.String getField()
    {
        return this._field;
    } //-- java.lang.String getField() 

    /**
     * Method getGroupingReturns the value of field 'grouping'.
     * 
     * @return the value of field 'grouping'.
     */
    public java.lang.String getGrouping()
    {
        return this._grouping;
    } //-- java.lang.String getGrouping() 

    /**
     * Method hasAreaKeyPK
     */
    public boolean hasAreaKeyPK()
    {
        return this._has_areaKeyPK;
    } //-- boolean hasAreaKeyPK() 

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
     * Method removeAllAreaValueVO
     */
    public void removeAllAreaValueVO()
    {
        _areaValueVOList.removeAllElements();
    } //-- void removeAllAreaValueVO() 

    /**
     * Method removeAreaValueVO
     * 
     * @param index
     */
    public edit.common.vo.AreaValueVO removeAreaValueVO(int index)
    {
        java.lang.Object obj = _areaValueVOList.elementAt(index);
        _areaValueVOList.removeElementAt(index);
        return (edit.common.vo.AreaValueVO) obj;
    } //-- edit.common.vo.AreaValueVO removeAreaValueVO(int) 

    /**
     * Method setAreaKeyPKSets the value of field 'areaKeyPK'.
     * 
     * @param areaKeyPK the value of field 'areaKeyPK'.
     */
    public void setAreaKeyPK(long areaKeyPK)
    {
        this._areaKeyPK = areaKeyPK;
        
        super.setVoChanged(true);
        this._has_areaKeyPK = true;
    } //-- void setAreaKeyPK(long) 

    /**
     * Method setAreaValueVO
     * 
     * @param index
     * @param vAreaValueVO
     */
    public void setAreaValueVO(int index, edit.common.vo.AreaValueVO vAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _areaValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAreaValueVO.setParentVO(this.getClass(), this);
        _areaValueVOList.setElementAt(vAreaValueVO, index);
    } //-- void setAreaValueVO(int, edit.common.vo.AreaValueVO) 

    /**
     * Method setAreaValueVO
     * 
     * @param areaValueVOArray
     */
    public void setAreaValueVO(edit.common.vo.AreaValueVO[] areaValueVOArray)
    {
        //-- copy array
        _areaValueVOList.removeAllElements();
        for (int i = 0; i < areaValueVOArray.length; i++) {
            areaValueVOArray[i].setParentVO(this.getClass(), this);
            _areaValueVOList.addElement(areaValueVOArray[i]);
        }
    } //-- void setAreaValueVO(edit.common.vo.AreaValueVO) 

    /**
     * Method setFieldSets the value of field 'field'.
     * 
     * @param field the value of field 'field'.
     */
    public void setField(java.lang.String field)
    {
        this._field = field;
        
        super.setVoChanged(true);
    } //-- void setField(java.lang.String) 

    /**
     * Method setGroupingSets the value of field 'grouping'.
     * 
     * @param grouping the value of field 'grouping'.
     */
    public void setGrouping(java.lang.String grouping)
    {
        this._grouping = grouping;
        
        super.setVoChanged(true);
    } //-- void setGrouping(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AreaKeyVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AreaKeyVO) Unmarshaller.unmarshal(edit.common.vo.AreaKeyVO.class, reader);
    } //-- edit.common.vo.AreaKeyVO unmarshal(java.io.Reader) 

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
