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
 * Class CodeTableVO.
 * 
 * @version $Revision$ $Date$
 */
public class CodeTableVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _codeTablePK
     */
    private long _codeTablePK;

    /**
     * keeps track of state for field: _codeTablePK
     */
    private boolean _has_codeTablePK;

    /**
     * Field _codeTableDefFK
     */
    private long _codeTableDefFK;

    /**
     * keeps track of state for field: _codeTableDefFK
     */
    private boolean _has_codeTableDefFK;

    /**
     * Field _code
     */
    private java.lang.String _code;

    /**
     * Field _codeDesc
     */
    private java.lang.String _codeDesc;

    /**
     * Field _filteredCodeTableVOList
     */
    private java.util.Vector _filteredCodeTableVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CodeTableVO() {
        super();
        _filteredCodeTableVOList = new Vector();
    } //-- edit.common.vo.CodeTableVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredCodeTableVO
     * 
     * @param vFilteredCodeTableVO
     */
    public void addFilteredCodeTableVO(edit.common.vo.FilteredCodeTableVO vFilteredCodeTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredCodeTableVO.setParentVO(this.getClass(), this);
        _filteredCodeTableVOList.addElement(vFilteredCodeTableVO);
    } //-- void addFilteredCodeTableVO(edit.common.vo.FilteredCodeTableVO) 

    /**
     * Method addFilteredCodeTableVO
     * 
     * @param index
     * @param vFilteredCodeTableVO
     */
    public void addFilteredCodeTableVO(int index, edit.common.vo.FilteredCodeTableVO vFilteredCodeTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredCodeTableVO.setParentVO(this.getClass(), this);
        _filteredCodeTableVOList.insertElementAt(vFilteredCodeTableVO, index);
    } //-- void addFilteredCodeTableVO(int, edit.common.vo.FilteredCodeTableVO) 

    /**
     * Method enumerateFilteredCodeTableVO
     */
    public java.util.Enumeration enumerateFilteredCodeTableVO()
    {
        return _filteredCodeTableVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredCodeTableVO() 

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
        
        if (obj instanceof CodeTableVO) {
        
            CodeTableVO temp = (CodeTableVO)obj;
            if (this._codeTablePK != temp._codeTablePK)
                return false;
            if (this._has_codeTablePK != temp._has_codeTablePK)
                return false;
            if (this._codeTableDefFK != temp._codeTableDefFK)
                return false;
            if (this._has_codeTableDefFK != temp._has_codeTableDefFK)
                return false;
            if (this._code != null) {
                if (temp._code == null) return false;
                else if (!(this._code.equals(temp._code))) 
                    return false;
            }
            else if (temp._code != null)
                return false;
            if (this._codeDesc != null) {
                if (temp._codeDesc == null) return false;
                else if (!(this._codeDesc.equals(temp._codeDesc))) 
                    return false;
            }
            else if (temp._codeDesc != null)
                return false;
            if (this._filteredCodeTableVOList != null) {
                if (temp._filteredCodeTableVOList == null) return false;
                else if (!(this._filteredCodeTableVOList.equals(temp._filteredCodeTableVOList))) 
                    return false;
            }
            else if (temp._filteredCodeTableVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCodeReturns the value of field 'code'.
     * 
     * @return the value of field 'code'.
     */
    public java.lang.String getCode()
    {
        return this._code;
    } //-- java.lang.String getCode() 

    /**
     * Method getCodeDescReturns the value of field 'codeDesc'.
     * 
     * @return the value of field 'codeDesc'.
     */
    public java.lang.String getCodeDesc()
    {
        return this._codeDesc;
    } //-- java.lang.String getCodeDesc() 

    /**
     * Method getCodeTableDefFKReturns the value of field
     * 'codeTableDefFK'.
     * 
     * @return the value of field 'codeTableDefFK'.
     */
    public long getCodeTableDefFK()
    {
        return this._codeTableDefFK;
    } //-- long getCodeTableDefFK() 

    /**
     * Method getCodeTablePKReturns the value of field
     * 'codeTablePK'.
     * 
     * @return the value of field 'codeTablePK'.
     */
    public long getCodeTablePK()
    {
        return this._codeTablePK;
    } //-- long getCodeTablePK() 

    /**
     * Method getFilteredCodeTableVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredCodeTableVO getFilteredCodeTableVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredCodeTableVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredCodeTableVO) _filteredCodeTableVOList.elementAt(index);
    } //-- edit.common.vo.FilteredCodeTableVO getFilteredCodeTableVO(int) 

    /**
     * Method getFilteredCodeTableVO
     */
    public edit.common.vo.FilteredCodeTableVO[] getFilteredCodeTableVO()
    {
        int size = _filteredCodeTableVOList.size();
        edit.common.vo.FilteredCodeTableVO[] mArray = new edit.common.vo.FilteredCodeTableVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredCodeTableVO) _filteredCodeTableVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredCodeTableVO[] getFilteredCodeTableVO() 

    /**
     * Method getFilteredCodeTableVOCount
     */
    public int getFilteredCodeTableVOCount()
    {
        return _filteredCodeTableVOList.size();
    } //-- int getFilteredCodeTableVOCount() 

    /**
     * Method hasCodeTableDefFK
     */
    public boolean hasCodeTableDefFK()
    {
        return this._has_codeTableDefFK;
    } //-- boolean hasCodeTableDefFK() 

    /**
     * Method hasCodeTablePK
     */
    public boolean hasCodeTablePK()
    {
        return this._has_codeTablePK;
    } //-- boolean hasCodeTablePK() 

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
     * Method removeAllFilteredCodeTableVO
     */
    public void removeAllFilteredCodeTableVO()
    {
        _filteredCodeTableVOList.removeAllElements();
    } //-- void removeAllFilteredCodeTableVO() 

    /**
     * Method removeFilteredCodeTableVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredCodeTableVO removeFilteredCodeTableVO(int index)
    {
        java.lang.Object obj = _filteredCodeTableVOList.elementAt(index);
        _filteredCodeTableVOList.removeElementAt(index);
        return (edit.common.vo.FilteredCodeTableVO) obj;
    } //-- edit.common.vo.FilteredCodeTableVO removeFilteredCodeTableVO(int) 

    /**
     * Method setCodeSets the value of field 'code'.
     * 
     * @param code the value of field 'code'.
     */
    public void setCode(java.lang.String code)
    {
        this._code = code;
        
        super.setVoChanged(true);
    } //-- void setCode(java.lang.String) 

    /**
     * Method setCodeDescSets the value of field 'codeDesc'.
     * 
     * @param codeDesc the value of field 'codeDesc'.
     */
    public void setCodeDesc(java.lang.String codeDesc)
    {
        this._codeDesc = codeDesc;
        
        super.setVoChanged(true);
    } //-- void setCodeDesc(java.lang.String) 

    /**
     * Method setCodeTableDefFKSets the value of field
     * 'codeTableDefFK'.
     * 
     * @param codeTableDefFK the value of field 'codeTableDefFK'.
     */
    public void setCodeTableDefFK(long codeTableDefFK)
    {
        this._codeTableDefFK = codeTableDefFK;
        
        super.setVoChanged(true);
        this._has_codeTableDefFK = true;
    } //-- void setCodeTableDefFK(long) 

    /**
     * Method setCodeTablePKSets the value of field 'codeTablePK'.
     * 
     * @param codeTablePK the value of field 'codeTablePK'.
     */
    public void setCodeTablePK(long codeTablePK)
    {
        this._codeTablePK = codeTablePK;
        
        super.setVoChanged(true);
        this._has_codeTablePK = true;
    } //-- void setCodeTablePK(long) 

    /**
     * Method setFilteredCodeTableVO
     * 
     * @param index
     * @param vFilteredCodeTableVO
     */
    public void setFilteredCodeTableVO(int index, edit.common.vo.FilteredCodeTableVO vFilteredCodeTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredCodeTableVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredCodeTableVO.setParentVO(this.getClass(), this);
        _filteredCodeTableVOList.setElementAt(vFilteredCodeTableVO, index);
    } //-- void setFilteredCodeTableVO(int, edit.common.vo.FilteredCodeTableVO) 

    /**
     * Method setFilteredCodeTableVO
     * 
     * @param filteredCodeTableVOArray
     */
    public void setFilteredCodeTableVO(edit.common.vo.FilteredCodeTableVO[] filteredCodeTableVOArray)
    {
        //-- copy array
        _filteredCodeTableVOList.removeAllElements();
        for (int i = 0; i < filteredCodeTableVOArray.length; i++) {
            filteredCodeTableVOArray[i].setParentVO(this.getClass(), this);
            _filteredCodeTableVOList.addElement(filteredCodeTableVOArray[i]);
        }
    } //-- void setFilteredCodeTableVO(edit.common.vo.FilteredCodeTableVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CodeTableVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CodeTableVO) Unmarshaller.unmarshal(edit.common.vo.CodeTableVO.class, reader);
    } //-- edit.common.vo.CodeTableVO unmarshal(java.io.Reader) 

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
