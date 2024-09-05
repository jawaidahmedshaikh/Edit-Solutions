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
 * Class TreatyGroupVO.
 * 
 * @version $Revision$ $Date$
 */
public class TreatyGroupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _treatyGroupPK
     */
    private long _treatyGroupPK;

    /**
     * keeps track of state for field: _treatyGroupPK
     */
    private boolean _has_treatyGroupPK;

    /**
     * Field _treatyGroupNumber
     */
    private java.lang.String _treatyGroupNumber;

    /**
     * Field _filteredTreatyGroupVOList
     */
    private java.util.Vector _filteredTreatyGroupVOList;

    /**
     * Field _treatyVOList
     */
    private java.util.Vector _treatyVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TreatyGroupVO() {
        super();
        _filteredTreatyGroupVOList = new Vector();
        _treatyVOList = new Vector();
    } //-- edit.common.vo.TreatyGroupVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredTreatyGroupVO
     * 
     * @param vFilteredTreatyGroupVO
     */
    public void addFilteredTreatyGroupVO(edit.common.vo.FilteredTreatyGroupVO vFilteredTreatyGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredTreatyGroupVO.setParentVO(this.getClass(), this);
        _filteredTreatyGroupVOList.addElement(vFilteredTreatyGroupVO);
    } //-- void addFilteredTreatyGroupVO(edit.common.vo.FilteredTreatyGroupVO) 

    /**
     * Method addFilteredTreatyGroupVO
     * 
     * @param index
     * @param vFilteredTreatyGroupVO
     */
    public void addFilteredTreatyGroupVO(int index, edit.common.vo.FilteredTreatyGroupVO vFilteredTreatyGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredTreatyGroupVO.setParentVO(this.getClass(), this);
        _filteredTreatyGroupVOList.insertElementAt(vFilteredTreatyGroupVO, index);
    } //-- void addFilteredTreatyGroupVO(int, edit.common.vo.FilteredTreatyGroupVO) 

    /**
     * Method addTreatyVO
     * 
     * @param vTreatyVO
     */
    public void addTreatyVO(edit.common.vo.TreatyVO vTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTreatyVO.setParentVO(this.getClass(), this);
        _treatyVOList.addElement(vTreatyVO);
    } //-- void addTreatyVO(edit.common.vo.TreatyVO) 

    /**
     * Method addTreatyVO
     * 
     * @param index
     * @param vTreatyVO
     */
    public void addTreatyVO(int index, edit.common.vo.TreatyVO vTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTreatyVO.setParentVO(this.getClass(), this);
        _treatyVOList.insertElementAt(vTreatyVO, index);
    } //-- void addTreatyVO(int, edit.common.vo.TreatyVO) 

    /**
     * Method enumerateFilteredTreatyGroupVO
     */
    public java.util.Enumeration enumerateFilteredTreatyGroupVO()
    {
        return _filteredTreatyGroupVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredTreatyGroupVO() 

    /**
     * Method enumerateTreatyVO
     */
    public java.util.Enumeration enumerateTreatyVO()
    {
        return _treatyVOList.elements();
    } //-- java.util.Enumeration enumerateTreatyVO() 

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
        
        if (obj instanceof TreatyGroupVO) {
        
            TreatyGroupVO temp = (TreatyGroupVO)obj;
            if (this._treatyGroupPK != temp._treatyGroupPK)
                return false;
            if (this._has_treatyGroupPK != temp._has_treatyGroupPK)
                return false;
            if (this._treatyGroupNumber != null) {
                if (temp._treatyGroupNumber == null) return false;
                else if (!(this._treatyGroupNumber.equals(temp._treatyGroupNumber))) 
                    return false;
            }
            else if (temp._treatyGroupNumber != null)
                return false;
            if (this._filteredTreatyGroupVOList != null) {
                if (temp._filteredTreatyGroupVOList == null) return false;
                else if (!(this._filteredTreatyGroupVOList.equals(temp._filteredTreatyGroupVOList))) 
                    return false;
            }
            else if (temp._filteredTreatyGroupVOList != null)
                return false;
            if (this._treatyVOList != null) {
                if (temp._treatyVOList == null) return false;
                else if (!(this._treatyVOList.equals(temp._treatyVOList))) 
                    return false;
            }
            else if (temp._treatyVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredTreatyGroupVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredTreatyGroupVO getFilteredTreatyGroupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredTreatyGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredTreatyGroupVO) _filteredTreatyGroupVOList.elementAt(index);
    } //-- edit.common.vo.FilteredTreatyGroupVO getFilteredTreatyGroupVO(int) 

    /**
     * Method getFilteredTreatyGroupVO
     */
    public edit.common.vo.FilteredTreatyGroupVO[] getFilteredTreatyGroupVO()
    {
        int size = _filteredTreatyGroupVOList.size();
        edit.common.vo.FilteredTreatyGroupVO[] mArray = new edit.common.vo.FilteredTreatyGroupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredTreatyGroupVO) _filteredTreatyGroupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredTreatyGroupVO[] getFilteredTreatyGroupVO() 

    /**
     * Method getFilteredTreatyGroupVOCount
     */
    public int getFilteredTreatyGroupVOCount()
    {
        return _filteredTreatyGroupVOList.size();
    } //-- int getFilteredTreatyGroupVOCount() 

    /**
     * Method getTreatyGroupNumberReturns the value of field
     * 'treatyGroupNumber'.
     * 
     * @return the value of field 'treatyGroupNumber'.
     */
    public java.lang.String getTreatyGroupNumber()
    {
        return this._treatyGroupNumber;
    } //-- java.lang.String getTreatyGroupNumber() 

    /**
     * Method getTreatyGroupPKReturns the value of field
     * 'treatyGroupPK'.
     * 
     * @return the value of field 'treatyGroupPK'.
     */
    public long getTreatyGroupPK()
    {
        return this._treatyGroupPK;
    } //-- long getTreatyGroupPK() 

    /**
     * Method getTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.TreatyVO getTreatyVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _treatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TreatyVO) _treatyVOList.elementAt(index);
    } //-- edit.common.vo.TreatyVO getTreatyVO(int) 

    /**
     * Method getTreatyVO
     */
    public edit.common.vo.TreatyVO[] getTreatyVO()
    {
        int size = _treatyVOList.size();
        edit.common.vo.TreatyVO[] mArray = new edit.common.vo.TreatyVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TreatyVO) _treatyVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TreatyVO[] getTreatyVO() 

    /**
     * Method getTreatyVOCount
     */
    public int getTreatyVOCount()
    {
        return _treatyVOList.size();
    } //-- int getTreatyVOCount() 

    /**
     * Method hasTreatyGroupPK
     */
    public boolean hasTreatyGroupPK()
    {
        return this._has_treatyGroupPK;
    } //-- boolean hasTreatyGroupPK() 

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
     * Method removeAllFilteredTreatyGroupVO
     */
    public void removeAllFilteredTreatyGroupVO()
    {
        _filteredTreatyGroupVOList.removeAllElements();
    } //-- void removeAllFilteredTreatyGroupVO() 

    /**
     * Method removeAllTreatyVO
     */
    public void removeAllTreatyVO()
    {
        _treatyVOList.removeAllElements();
    } //-- void removeAllTreatyVO() 

    /**
     * Method removeFilteredTreatyGroupVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredTreatyGroupVO removeFilteredTreatyGroupVO(int index)
    {
        java.lang.Object obj = _filteredTreatyGroupVOList.elementAt(index);
        _filteredTreatyGroupVOList.removeElementAt(index);
        return (edit.common.vo.FilteredTreatyGroupVO) obj;
    } //-- edit.common.vo.FilteredTreatyGroupVO removeFilteredTreatyGroupVO(int) 

    /**
     * Method removeTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.TreatyVO removeTreatyVO(int index)
    {
        java.lang.Object obj = _treatyVOList.elementAt(index);
        _treatyVOList.removeElementAt(index);
        return (edit.common.vo.TreatyVO) obj;
    } //-- edit.common.vo.TreatyVO removeTreatyVO(int) 

    /**
     * Method setFilteredTreatyGroupVO
     * 
     * @param index
     * @param vFilteredTreatyGroupVO
     */
    public void setFilteredTreatyGroupVO(int index, edit.common.vo.FilteredTreatyGroupVO vFilteredTreatyGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredTreatyGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredTreatyGroupVO.setParentVO(this.getClass(), this);
        _filteredTreatyGroupVOList.setElementAt(vFilteredTreatyGroupVO, index);
    } //-- void setFilteredTreatyGroupVO(int, edit.common.vo.FilteredTreatyGroupVO) 

    /**
     * Method setFilteredTreatyGroupVO
     * 
     * @param filteredTreatyGroupVOArray
     */
    public void setFilteredTreatyGroupVO(edit.common.vo.FilteredTreatyGroupVO[] filteredTreatyGroupVOArray)
    {
        //-- copy array
        _filteredTreatyGroupVOList.removeAllElements();
        for (int i = 0; i < filteredTreatyGroupVOArray.length; i++) {
            filteredTreatyGroupVOArray[i].setParentVO(this.getClass(), this);
            _filteredTreatyGroupVOList.addElement(filteredTreatyGroupVOArray[i]);
        }
    } //-- void setFilteredTreatyGroupVO(edit.common.vo.FilteredTreatyGroupVO) 

    /**
     * Method setTreatyGroupNumberSets the value of field
     * 'treatyGroupNumber'.
     * 
     * @param treatyGroupNumber the value of field
     * 'treatyGroupNumber'.
     */
    public void setTreatyGroupNumber(java.lang.String treatyGroupNumber)
    {
        this._treatyGroupNumber = treatyGroupNumber;
        
        super.setVoChanged(true);
    } //-- void setTreatyGroupNumber(java.lang.String) 

    /**
     * Method setTreatyGroupPKSets the value of field
     * 'treatyGroupPK'.
     * 
     * @param treatyGroupPK the value of field 'treatyGroupPK'.
     */
    public void setTreatyGroupPK(long treatyGroupPK)
    {
        this._treatyGroupPK = treatyGroupPK;
        
        super.setVoChanged(true);
        this._has_treatyGroupPK = true;
    } //-- void setTreatyGroupPK(long) 

    /**
     * Method setTreatyVO
     * 
     * @param index
     * @param vTreatyVO
     */
    public void setTreatyVO(int index, edit.common.vo.TreatyVO vTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _treatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTreatyVO.setParentVO(this.getClass(), this);
        _treatyVOList.setElementAt(vTreatyVO, index);
    } //-- void setTreatyVO(int, edit.common.vo.TreatyVO) 

    /**
     * Method setTreatyVO
     * 
     * @param treatyVOArray
     */
    public void setTreatyVO(edit.common.vo.TreatyVO[] treatyVOArray)
    {
        //-- copy array
        _treatyVOList.removeAllElements();
        for (int i = 0; i < treatyVOArray.length; i++) {
            treatyVOArray[i].setParentVO(this.getClass(), this);
            _treatyVOList.addElement(treatyVOArray[i]);
        }
    } //-- void setTreatyVO(edit.common.vo.TreatyVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TreatyGroupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TreatyGroupVO) Unmarshaller.unmarshal(edit.common.vo.TreatyGroupVO.class, reader);
    } //-- edit.common.vo.TreatyGroupVO unmarshal(java.io.Reader) 

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
