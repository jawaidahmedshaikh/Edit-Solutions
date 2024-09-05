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
public class UIFilteredFundVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredFundVOList
     */
    private java.util.Vector _filteredFundVOList;

    /**
     * Field _fundVOList
     */
    private java.util.Vector _fundVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public UIFilteredFundVO() {
        super();
        _filteredFundVOList = new Vector();
        _fundVOList = new Vector();
    } //-- edit.common.vo.UIFilteredFundVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredFundVO
     * 
     * @param vFilteredFundVO
     */
    public void addFilteredFundVO(edit.common.vo.FilteredFundVO vFilteredFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredFundVO.setParentVO(this.getClass(), this);
        _filteredFundVOList.addElement(vFilteredFundVO);
    } //-- void addFilteredFundVO(edit.common.vo.FilteredFundVO) 

    /**
     * Method addFilteredFundVO
     * 
     * @param index
     * @param vFilteredFundVO
     */
    public void addFilteredFundVO(int index, edit.common.vo.FilteredFundVO vFilteredFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredFundVO.setParentVO(this.getClass(), this);
        _filteredFundVOList.insertElementAt(vFilteredFundVO, index);
    } //-- void addFilteredFundVO(int, edit.common.vo.FilteredFundVO) 

    /**
     * Method addFundVO
     * 
     * @param vFundVO
     */
    public void addFundVO(edit.common.vo.FundVO vFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFundVO.setParentVO(this.getClass(), this);
        _fundVOList.addElement(vFundVO);
    } //-- void addFundVO(edit.common.vo.FundVO) 

    /**
     * Method addFundVO
     * 
     * @param index
     * @param vFundVO
     */
    public void addFundVO(int index, edit.common.vo.FundVO vFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFundVO.setParentVO(this.getClass(), this);
        _fundVOList.insertElementAt(vFundVO, index);
    } //-- void addFundVO(int, edit.common.vo.FundVO) 

    /**
     * Method enumerateFilteredFundVO
     */
    public java.util.Enumeration enumerateFilteredFundVO()
    {
        return _filteredFundVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredFundVO() 

    /**
     * Method enumerateFundVO
     */
    public java.util.Enumeration enumerateFundVO()
    {
        return _fundVOList.elements();
    } //-- java.util.Enumeration enumerateFundVO() 

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
        
        if (obj instanceof UIFilteredFundVO) {
        
            UIFilteredFundVO temp = (UIFilteredFundVO)obj;
            if (this._filteredFundVOList != null) {
                if (temp._filteredFundVOList == null) return false;
                else if (!(this._filteredFundVOList.equals(temp._filteredFundVOList))) 
                    return false;
            }
            else if (temp._filteredFundVOList != null)
                return false;
            if (this._fundVOList != null) {
                if (temp._fundVOList == null) return false;
                else if (!(this._fundVOList.equals(temp._fundVOList))) 
                    return false;
            }
            else if (temp._fundVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredFundVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredFundVO getFilteredFundVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredFundVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredFundVO) _filteredFundVOList.elementAt(index);
    } //-- edit.common.vo.FilteredFundVO getFilteredFundVO(int) 

    /**
     * Method getFilteredFundVO
     */
    public edit.common.vo.FilteredFundVO[] getFilteredFundVO()
    {
        int size = _filteredFundVOList.size();
        edit.common.vo.FilteredFundVO[] mArray = new edit.common.vo.FilteredFundVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredFundVO) _filteredFundVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredFundVO[] getFilteredFundVO() 

    /**
     * Method getFilteredFundVOCount
     */
    public int getFilteredFundVOCount()
    {
        return _filteredFundVOList.size();
    } //-- int getFilteredFundVOCount() 

    /**
     * Method getFundVO
     * 
     * @param index
     */
    public edit.common.vo.FundVO getFundVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _fundVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FundVO) _fundVOList.elementAt(index);
    } //-- edit.common.vo.FundVO getFundVO(int) 

    /**
     * Method getFundVO
     */
    public edit.common.vo.FundVO[] getFundVO()
    {
        int size = _fundVOList.size();
        edit.common.vo.FundVO[] mArray = new edit.common.vo.FundVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FundVO) _fundVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FundVO[] getFundVO() 

    /**
     * Method getFundVOCount
     */
    public int getFundVOCount()
    {
        return _fundVOList.size();
    } //-- int getFundVOCount() 

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
     * Method removeAllFilteredFundVO
     */
    public void removeAllFilteredFundVO()
    {
        _filteredFundVOList.removeAllElements();
    } //-- void removeAllFilteredFundVO() 

    /**
     * Method removeAllFundVO
     */
    public void removeAllFundVO()
    {
        _fundVOList.removeAllElements();
    } //-- void removeAllFundVO() 

    /**
     * Method removeFilteredFundVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredFundVO removeFilteredFundVO(int index)
    {
        java.lang.Object obj = _filteredFundVOList.elementAt(index);
        _filteredFundVOList.removeElementAt(index);
        return (edit.common.vo.FilteredFundVO) obj;
    } //-- edit.common.vo.FilteredFundVO removeFilteredFundVO(int) 

    /**
     * Method removeFundVO
     * 
     * @param index
     */
    public edit.common.vo.FundVO removeFundVO(int index)
    {
        java.lang.Object obj = _fundVOList.elementAt(index);
        _fundVOList.removeElementAt(index);
        return (edit.common.vo.FundVO) obj;
    } //-- edit.common.vo.FundVO removeFundVO(int) 

    /**
     * Method setFilteredFundVO
     * 
     * @param index
     * @param vFilteredFundVO
     */
    public void setFilteredFundVO(int index, edit.common.vo.FilteredFundVO vFilteredFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredFundVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredFundVO.setParentVO(this.getClass(), this);
        _filteredFundVOList.setElementAt(vFilteredFundVO, index);
    } //-- void setFilteredFundVO(int, edit.common.vo.FilteredFundVO) 

    /**
     * Method setFilteredFundVO
     * 
     * @param filteredFundVOArray
     */
    public void setFilteredFundVO(edit.common.vo.FilteredFundVO[] filteredFundVOArray)
    {
        //-- copy array
        _filteredFundVOList.removeAllElements();
        for (int i = 0; i < filteredFundVOArray.length; i++) {
            filteredFundVOArray[i].setParentVO(this.getClass(), this);
            _filteredFundVOList.addElement(filteredFundVOArray[i]);
        }
    } //-- void setFilteredFundVO(edit.common.vo.FilteredFundVO) 

    /**
     * Method setFundVO
     * 
     * @param index
     * @param vFundVO
     */
    public void setFundVO(int index, edit.common.vo.FundVO vFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _fundVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFundVO.setParentVO(this.getClass(), this);
        _fundVOList.setElementAt(vFundVO, index);
    } //-- void setFundVO(int, edit.common.vo.FundVO) 

    /**
     * Method setFundVO
     * 
     * @param fundVOArray
     */
    public void setFundVO(edit.common.vo.FundVO[] fundVOArray)
    {
        //-- copy array
        _fundVOList.removeAllElements();
        for (int i = 0; i < fundVOArray.length; i++) {
            fundVOArray[i].setParentVO(this.getClass(), this);
            _fundVOList.addElement(fundVOArray[i]);
        }
    } //-- void setFundVO(edit.common.vo.FundVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UIFilteredFundVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UIFilteredFundVO) Unmarshaller.unmarshal(edit.common.vo.UIFilteredFundVO.class, reader);
    } //-- edit.common.vo.UIFilteredFundVO unmarshal(java.io.Reader) 

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
