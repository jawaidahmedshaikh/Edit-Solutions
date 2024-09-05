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
 * Class OnlineReportVO.
 * 
 * @version $Revision$ $Date$
 */
public class OnlineReportVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _onlineReportPK
     */
    private long _onlineReportPK;

    /**
     * keeps track of state for field: _onlineReportPK
     */
    private boolean _has_onlineReportPK;

    /**
     * Field _fileName
     */
    private java.lang.String _fileName;

    /**
     * Field _description
     */
    private java.lang.String _description;

    /**
     * Field _reportCategoryCT
     */
    private java.lang.String _reportCategoryCT;

    /**
     * Field _filteredOnlineReportVOList
     */
    private java.util.Vector _filteredOnlineReportVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public OnlineReportVO() {
        super();
        _filteredOnlineReportVOList = new Vector();
    } //-- edit.common.vo.OnlineReportVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredOnlineReportVO
     * 
     * @param vFilteredOnlineReportVO
     */
    public void addFilteredOnlineReportVO(edit.common.vo.FilteredOnlineReportVO vFilteredOnlineReportVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredOnlineReportVO.setParentVO(this.getClass(), this);
        _filteredOnlineReportVOList.addElement(vFilteredOnlineReportVO);
    } //-- void addFilteredOnlineReportVO(edit.common.vo.FilteredOnlineReportVO) 

    /**
     * Method addFilteredOnlineReportVO
     * 
     * @param index
     * @param vFilteredOnlineReportVO
     */
    public void addFilteredOnlineReportVO(int index, edit.common.vo.FilteredOnlineReportVO vFilteredOnlineReportVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredOnlineReportVO.setParentVO(this.getClass(), this);
        _filteredOnlineReportVOList.insertElementAt(vFilteredOnlineReportVO, index);
    } //-- void addFilteredOnlineReportVO(int, edit.common.vo.FilteredOnlineReportVO) 

    /**
     * Method enumerateFilteredOnlineReportVO
     */
    public java.util.Enumeration enumerateFilteredOnlineReportVO()
    {
        return _filteredOnlineReportVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredOnlineReportVO() 

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
        
        if (obj instanceof OnlineReportVO) {
        
            OnlineReportVO temp = (OnlineReportVO)obj;
            if (this._onlineReportPK != temp._onlineReportPK)
                return false;
            if (this._has_onlineReportPK != temp._has_onlineReportPK)
                return false;
            if (this._fileName != null) {
                if (temp._fileName == null) return false;
                else if (!(this._fileName.equals(temp._fileName))) 
                    return false;
            }
            else if (temp._fileName != null)
                return false;
            if (this._description != null) {
                if (temp._description == null) return false;
                else if (!(this._description.equals(temp._description))) 
                    return false;
            }
            else if (temp._description != null)
                return false;
            if (this._reportCategoryCT != null) {
                if (temp._reportCategoryCT == null) return false;
                else if (!(this._reportCategoryCT.equals(temp._reportCategoryCT))) 
                    return false;
            }
            else if (temp._reportCategoryCT != null)
                return false;
            if (this._filteredOnlineReportVOList != null) {
                if (temp._filteredOnlineReportVOList == null) return false;
                else if (!(this._filteredOnlineReportVOList.equals(temp._filteredOnlineReportVOList))) 
                    return false;
            }
            else if (temp._filteredOnlineReportVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDescriptionReturns the value of field
     * 'description'.
     * 
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Method getFileNameReturns the value of field 'fileName'.
     * 
     * @return the value of field 'fileName'.
     */
    public java.lang.String getFileName()
    {
        return this._fileName;
    } //-- java.lang.String getFileName() 

    /**
     * Method getFilteredOnlineReportVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredOnlineReportVO getFilteredOnlineReportVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredOnlineReportVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredOnlineReportVO) _filteredOnlineReportVOList.elementAt(index);
    } //-- edit.common.vo.FilteredOnlineReportVO getFilteredOnlineReportVO(int) 

    /**
     * Method getFilteredOnlineReportVO
     */
    public edit.common.vo.FilteredOnlineReportVO[] getFilteredOnlineReportVO()
    {
        int size = _filteredOnlineReportVOList.size();
        edit.common.vo.FilteredOnlineReportVO[] mArray = new edit.common.vo.FilteredOnlineReportVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredOnlineReportVO) _filteredOnlineReportVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredOnlineReportVO[] getFilteredOnlineReportVO() 

    /**
     * Method getFilteredOnlineReportVOCount
     */
    public int getFilteredOnlineReportVOCount()
    {
        return _filteredOnlineReportVOList.size();
    } //-- int getFilteredOnlineReportVOCount() 

    /**
     * Method getOnlineReportPKReturns the value of field
     * 'onlineReportPK'.
     * 
     * @return the value of field 'onlineReportPK'.
     */
    public long getOnlineReportPK()
    {
        return this._onlineReportPK;
    } //-- long getOnlineReportPK() 

    /**
     * Method getReportCategoryCTReturns the value of field
     * 'reportCategoryCT'.
     * 
     * @return the value of field 'reportCategoryCT'.
     */
    public java.lang.String getReportCategoryCT()
    {
        return this._reportCategoryCT;
    } //-- java.lang.String getReportCategoryCT() 

    /**
     * Method hasOnlineReportPK
     */
    public boolean hasOnlineReportPK()
    {
        return this._has_onlineReportPK;
    } //-- boolean hasOnlineReportPK() 

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
     * Method removeAllFilteredOnlineReportVO
     */
    public void removeAllFilteredOnlineReportVO()
    {
        _filteredOnlineReportVOList.removeAllElements();
    } //-- void removeAllFilteredOnlineReportVO() 

    /**
     * Method removeFilteredOnlineReportVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredOnlineReportVO removeFilteredOnlineReportVO(int index)
    {
        java.lang.Object obj = _filteredOnlineReportVOList.elementAt(index);
        _filteredOnlineReportVOList.removeElementAt(index);
        return (edit.common.vo.FilteredOnlineReportVO) obj;
    } //-- edit.common.vo.FilteredOnlineReportVO removeFilteredOnlineReportVO(int) 

    /**
     * Method setDescriptionSets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
        
        super.setVoChanged(true);
    } //-- void setDescription(java.lang.String) 

    /**
     * Method setFileNameSets the value of field 'fileName'.
     * 
     * @param fileName the value of field 'fileName'.
     */
    public void setFileName(java.lang.String fileName)
    {
        this._fileName = fileName;
        
        super.setVoChanged(true);
    } //-- void setFileName(java.lang.String) 

    /**
     * Method setFilteredOnlineReportVO
     * 
     * @param index
     * @param vFilteredOnlineReportVO
     */
    public void setFilteredOnlineReportVO(int index, edit.common.vo.FilteredOnlineReportVO vFilteredOnlineReportVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredOnlineReportVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredOnlineReportVO.setParentVO(this.getClass(), this);
        _filteredOnlineReportVOList.setElementAt(vFilteredOnlineReportVO, index);
    } //-- void setFilteredOnlineReportVO(int, edit.common.vo.FilteredOnlineReportVO) 

    /**
     * Method setFilteredOnlineReportVO
     * 
     * @param filteredOnlineReportVOArray
     */
    public void setFilteredOnlineReportVO(edit.common.vo.FilteredOnlineReportVO[] filteredOnlineReportVOArray)
    {
        //-- copy array
        _filteredOnlineReportVOList.removeAllElements();
        for (int i = 0; i < filteredOnlineReportVOArray.length; i++) {
            filteredOnlineReportVOArray[i].setParentVO(this.getClass(), this);
            _filteredOnlineReportVOList.addElement(filteredOnlineReportVOArray[i]);
        }
    } //-- void setFilteredOnlineReportVO(edit.common.vo.FilteredOnlineReportVO) 

    /**
     * Method setOnlineReportPKSets the value of field
     * 'onlineReportPK'.
     * 
     * @param onlineReportPK the value of field 'onlineReportPK'.
     */
    public void setOnlineReportPK(long onlineReportPK)
    {
        this._onlineReportPK = onlineReportPK;
        
        super.setVoChanged(true);
        this._has_onlineReportPK = true;
    } //-- void setOnlineReportPK(long) 

    /**
     * Method setReportCategoryCTSets the value of field
     * 'reportCategoryCT'.
     * 
     * @param reportCategoryCT the value of field 'reportCategoryCT'
     */
    public void setReportCategoryCT(java.lang.String reportCategoryCT)
    {
        this._reportCategoryCT = reportCategoryCT;
        
        super.setVoChanged(true);
    } //-- void setReportCategoryCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.OnlineReportVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OnlineReportVO) Unmarshaller.unmarshal(edit.common.vo.OnlineReportVO.class, reader);
    } //-- edit.common.vo.OnlineReportVO unmarshal(java.io.Reader) 

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
