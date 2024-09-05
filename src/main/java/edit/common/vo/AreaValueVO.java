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
 * Class AreaValueVO.
 * 
 * @version $Revision$ $Date$
 */
public class AreaValueVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _areaValuePK
     */
    private long _areaValuePK;

    /**
     * keeps track of state for field: _areaValuePK
     */
    private boolean _has_areaValuePK;

    /**
     * Field _areaKeyFK
     */
    private long _areaKeyFK;

    /**
     * keeps track of state for field: _areaKeyFK
     */
    private boolean _has_areaKeyFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _areaValue
     */
    private java.lang.String _areaValue;

    /**
     * Field _areaCT
     */
    private java.lang.String _areaCT;

    /**
     * Field _qualifierCT
     */
    private java.lang.String _qualifierCT;

    /**
     * Field _filteredAreaValueVOList
     */
    private java.util.Vector _filteredAreaValueVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AreaValueVO() {
        super();
        _filteredAreaValueVOList = new Vector();
    } //-- edit.common.vo.AreaValueVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredAreaValueVO
     * 
     * @param vFilteredAreaValueVO
     */
    public void addFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO vFilteredAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredAreaValueVO.setParentVO(this.getClass(), this);
        _filteredAreaValueVOList.addElement(vFilteredAreaValueVO);
    } //-- void addFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method addFilteredAreaValueVO
     * 
     * @param index
     * @param vFilteredAreaValueVO
     */
    public void addFilteredAreaValueVO(int index, edit.common.vo.FilteredAreaValueVO vFilteredAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredAreaValueVO.setParentVO(this.getClass(), this);
        _filteredAreaValueVOList.insertElementAt(vFilteredAreaValueVO, index);
    } //-- void addFilteredAreaValueVO(int, edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method enumerateFilteredAreaValueVO
     */
    public java.util.Enumeration enumerateFilteredAreaValueVO()
    {
        return _filteredAreaValueVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredAreaValueVO() 

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
        
        if (obj instanceof AreaValueVO) {
        
            AreaValueVO temp = (AreaValueVO)obj;
            if (this._areaValuePK != temp._areaValuePK)
                return false;
            if (this._has_areaValuePK != temp._has_areaValuePK)
                return false;
            if (this._areaKeyFK != temp._areaKeyFK)
                return false;
            if (this._has_areaKeyFK != temp._has_areaKeyFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._areaValue != null) {
                if (temp._areaValue == null) return false;
                else if (!(this._areaValue.equals(temp._areaValue))) 
                    return false;
            }
            else if (temp._areaValue != null)
                return false;
            if (this._areaCT != null) {
                if (temp._areaCT == null) return false;
                else if (!(this._areaCT.equals(temp._areaCT))) 
                    return false;
            }
            else if (temp._areaCT != null)
                return false;
            if (this._qualifierCT != null) {
                if (temp._qualifierCT == null) return false;
                else if (!(this._qualifierCT.equals(temp._qualifierCT))) 
                    return false;
            }
            else if (temp._qualifierCT != null)
                return false;
            if (this._filteredAreaValueVOList != null) {
                if (temp._filteredAreaValueVOList == null) return false;
                else if (!(this._filteredAreaValueVOList.equals(temp._filteredAreaValueVOList))) 
                    return false;
            }
            else if (temp._filteredAreaValueVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAreaCTReturns the value of field 'areaCT'.
     * 
     * @return the value of field 'areaCT'.
     */
    public java.lang.String getAreaCT()
    {
        return this._areaCT;
    } //-- java.lang.String getAreaCT() 

    /**
     * Method getAreaKeyFKReturns the value of field 'areaKeyFK'.
     * 
     * @return the value of field 'areaKeyFK'.
     */
    public long getAreaKeyFK()
    {
        return this._areaKeyFK;
    } //-- long getAreaKeyFK() 

    /**
     * Method getAreaValueReturns the value of field 'areaValue'.
     * 
     * @return the value of field 'areaValue'.
     */
    public java.lang.String getAreaValue()
    {
        return this._areaValue;
    } //-- java.lang.String getAreaValue() 

    /**
     * Method getAreaValuePKReturns the value of field
     * 'areaValuePK'.
     * 
     * @return the value of field 'areaValuePK'.
     */
    public long getAreaValuePK()
    {
        return this._areaValuePK;
    } //-- long getAreaValuePK() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getFilteredAreaValueVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredAreaValueVO getFilteredAreaValueVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredAreaValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredAreaValueVO) _filteredAreaValueVOList.elementAt(index);
    } //-- edit.common.vo.FilteredAreaValueVO getFilteredAreaValueVO(int) 

    /**
     * Method getFilteredAreaValueVO
     */
    public edit.common.vo.FilteredAreaValueVO[] getFilteredAreaValueVO()
    {
        int size = _filteredAreaValueVOList.size();
        edit.common.vo.FilteredAreaValueVO[] mArray = new edit.common.vo.FilteredAreaValueVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredAreaValueVO) _filteredAreaValueVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredAreaValueVO[] getFilteredAreaValueVO() 

    /**
     * Method getFilteredAreaValueVOCount
     */
    public int getFilteredAreaValueVOCount()
    {
        return _filteredAreaValueVOList.size();
    } //-- int getFilteredAreaValueVOCount() 

    /**
     * Method getQualifierCTReturns the value of field
     * 'qualifierCT'.
     * 
     * @return the value of field 'qualifierCT'.
     */
    public java.lang.String getQualifierCT()
    {
        return this._qualifierCT;
    } //-- java.lang.String getQualifierCT() 

    /**
     * Method hasAreaKeyFK
     */
    public boolean hasAreaKeyFK()
    {
        return this._has_areaKeyFK;
    } //-- boolean hasAreaKeyFK() 

    /**
     * Method hasAreaValuePK
     */
    public boolean hasAreaValuePK()
    {
        return this._has_areaValuePK;
    } //-- boolean hasAreaValuePK() 

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
     * Method removeAllFilteredAreaValueVO
     */
    public void removeAllFilteredAreaValueVO()
    {
        _filteredAreaValueVOList.removeAllElements();
    } //-- void removeAllFilteredAreaValueVO() 

    /**
     * Method removeFilteredAreaValueVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredAreaValueVO removeFilteredAreaValueVO(int index)
    {
        java.lang.Object obj = _filteredAreaValueVOList.elementAt(index);
        _filteredAreaValueVOList.removeElementAt(index);
        return (edit.common.vo.FilteredAreaValueVO) obj;
    } //-- edit.common.vo.FilteredAreaValueVO removeFilteredAreaValueVO(int) 

    /**
     * Method setAreaCTSets the value of field 'areaCT'.
     * 
     * @param areaCT the value of field 'areaCT'.
     */
    public void setAreaCT(java.lang.String areaCT)
    {
        this._areaCT = areaCT;
        
        super.setVoChanged(true);
    } //-- void setAreaCT(java.lang.String) 

    /**
     * Method setAreaKeyFKSets the value of field 'areaKeyFK'.
     * 
     * @param areaKeyFK the value of field 'areaKeyFK'.
     */
    public void setAreaKeyFK(long areaKeyFK)
    {
        this._areaKeyFK = areaKeyFK;
        
        super.setVoChanged(true);
        this._has_areaKeyFK = true;
    } //-- void setAreaKeyFK(long) 

    /**
     * Method setAreaValueSets the value of field 'areaValue'.
     * 
     * @param areaValue the value of field 'areaValue'.
     */
    public void setAreaValue(java.lang.String areaValue)
    {
        this._areaValue = areaValue;
        
        super.setVoChanged(true);
    } //-- void setAreaValue(java.lang.String) 

    /**
     * Method setAreaValuePKSets the value of field 'areaValuePK'.
     * 
     * @param areaValuePK the value of field 'areaValuePK'.
     */
    public void setAreaValuePK(long areaValuePK)
    {
        this._areaValuePK = areaValuePK;
        
        super.setVoChanged(true);
        this._has_areaValuePK = true;
    } //-- void setAreaValuePK(long) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setFilteredAreaValueVO
     * 
     * @param index
     * @param vFilteredAreaValueVO
     */
    public void setFilteredAreaValueVO(int index, edit.common.vo.FilteredAreaValueVO vFilteredAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredAreaValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredAreaValueVO.setParentVO(this.getClass(), this);
        _filteredAreaValueVOList.setElementAt(vFilteredAreaValueVO, index);
    } //-- void setFilteredAreaValueVO(int, edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method setFilteredAreaValueVO
     * 
     * @param filteredAreaValueVOArray
     */
    public void setFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO[] filteredAreaValueVOArray)
    {
        //-- copy array
        _filteredAreaValueVOList.removeAllElements();
        for (int i = 0; i < filteredAreaValueVOArray.length; i++) {
            filteredAreaValueVOArray[i].setParentVO(this.getClass(), this);
            _filteredAreaValueVOList.addElement(filteredAreaValueVOArray[i]);
        }
    } //-- void setFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method setQualifierCTSets the value of field 'qualifierCT'.
     * 
     * @param qualifierCT the value of field 'qualifierCT'.
     */
    public void setQualifierCT(java.lang.String qualifierCT)
    {
        this._qualifierCT = qualifierCT;
        
        super.setVoChanged(true);
    } //-- void setQualifierCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AreaValueVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AreaValueVO) Unmarshaller.unmarshal(edit.common.vo.AreaValueVO.class, reader);
    } //-- edit.common.vo.AreaValueVO unmarshal(java.io.Reader) 

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
