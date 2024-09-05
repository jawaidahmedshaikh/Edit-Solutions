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
 * Class CodeTableDefVO.
 * 
 * @version $Revision$ $Date$
 */
public class CodeTableDefVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _codeTableDefPK
     */
    private long _codeTableDefPK;

    /**
     * keeps track of state for field: _codeTableDefPK
     */
    private boolean _has_codeTableDefPK;

    /**
     * Field _codeTableName
     */
    private java.lang.String _codeTableName;

    /**
     * Field _codeTableVOList
     */
    private java.util.Vector _codeTableVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CodeTableDefVO() {
        super();
        _codeTableVOList = new Vector();
    } //-- edit.common.vo.CodeTableDefVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addCodeTableVO
     * 
     * @param vCodeTableVO
     */
    public void addCodeTableVO(edit.common.vo.CodeTableVO vCodeTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCodeTableVO.setParentVO(this.getClass(), this);
        _codeTableVOList.addElement(vCodeTableVO);
    } //-- void addCodeTableVO(edit.common.vo.CodeTableVO) 

    /**
     * Method addCodeTableVO
     * 
     * @param index
     * @param vCodeTableVO
     */
    public void addCodeTableVO(int index, edit.common.vo.CodeTableVO vCodeTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCodeTableVO.setParentVO(this.getClass(), this);
        _codeTableVOList.insertElementAt(vCodeTableVO, index);
    } //-- void addCodeTableVO(int, edit.common.vo.CodeTableVO) 

    /**
     * Method enumerateCodeTableVO
     */
    public java.util.Enumeration enumerateCodeTableVO()
    {
        return _codeTableVOList.elements();
    } //-- java.util.Enumeration enumerateCodeTableVO() 

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
        
        if (obj instanceof CodeTableDefVO) {
        
            CodeTableDefVO temp = (CodeTableDefVO)obj;
            if (this._codeTableDefPK != temp._codeTableDefPK)
                return false;
            if (this._has_codeTableDefPK != temp._has_codeTableDefPK)
                return false;
            if (this._codeTableName != null) {
                if (temp._codeTableName == null) return false;
                else if (!(this._codeTableName.equals(temp._codeTableName))) 
                    return false;
            }
            else if (temp._codeTableName != null)
                return false;
            if (this._codeTableVOList != null) {
                if (temp._codeTableVOList == null) return false;
                else if (!(this._codeTableVOList.equals(temp._codeTableVOList))) 
                    return false;
            }
            else if (temp._codeTableVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCodeTableDefPKReturns the value of field
     * 'codeTableDefPK'.
     * 
     * @return the value of field 'codeTableDefPK'.
     */
    public long getCodeTableDefPK()
    {
        return this._codeTableDefPK;
    } //-- long getCodeTableDefPK() 

    /**
     * Method getCodeTableNameReturns the value of field
     * 'codeTableName'.
     * 
     * @return the value of field 'codeTableName'.
     */
    public java.lang.String getCodeTableName()
    {
        return this._codeTableName;
    } //-- java.lang.String getCodeTableName() 

    /**
     * Method getCodeTableVO
     * 
     * @param index
     */
    public edit.common.vo.CodeTableVO getCodeTableVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _codeTableVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CodeTableVO) _codeTableVOList.elementAt(index);
    } //-- edit.common.vo.CodeTableVO getCodeTableVO(int) 

    /**
     * Method getCodeTableVO
     */
    public edit.common.vo.CodeTableVO[] getCodeTableVO()
    {
        int size = _codeTableVOList.size();
        edit.common.vo.CodeTableVO[] mArray = new edit.common.vo.CodeTableVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CodeTableVO) _codeTableVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CodeTableVO[] getCodeTableVO() 

    /**
     * Method getCodeTableVOCount
     */
    public int getCodeTableVOCount()
    {
        return _codeTableVOList.size();
    } //-- int getCodeTableVOCount() 

    /**
     * Method hasCodeTableDefPK
     */
    public boolean hasCodeTableDefPK()
    {
        return this._has_codeTableDefPK;
    } //-- boolean hasCodeTableDefPK() 

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
     * Method removeAllCodeTableVO
     */
    public void removeAllCodeTableVO()
    {
        _codeTableVOList.removeAllElements();
    } //-- void removeAllCodeTableVO() 

    /**
     * Method removeCodeTableVO
     * 
     * @param index
     */
    public edit.common.vo.CodeTableVO removeCodeTableVO(int index)
    {
        java.lang.Object obj = _codeTableVOList.elementAt(index);
        _codeTableVOList.removeElementAt(index);
        return (edit.common.vo.CodeTableVO) obj;
    } //-- edit.common.vo.CodeTableVO removeCodeTableVO(int) 

    /**
     * Method setCodeTableDefPKSets the value of field
     * 'codeTableDefPK'.
     * 
     * @param codeTableDefPK the value of field 'codeTableDefPK'.
     */
    public void setCodeTableDefPK(long codeTableDefPK)
    {
        this._codeTableDefPK = codeTableDefPK;
        
        super.setVoChanged(true);
        this._has_codeTableDefPK = true;
    } //-- void setCodeTableDefPK(long) 

    /**
     * Method setCodeTableNameSets the value of field
     * 'codeTableName'.
     * 
     * @param codeTableName the value of field 'codeTableName'.
     */
    public void setCodeTableName(java.lang.String codeTableName)
    {
        this._codeTableName = codeTableName;
        
        super.setVoChanged(true);
    } //-- void setCodeTableName(java.lang.String) 

    /**
     * Method setCodeTableVO
     * 
     * @param index
     * @param vCodeTableVO
     */
    public void setCodeTableVO(int index, edit.common.vo.CodeTableVO vCodeTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _codeTableVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCodeTableVO.setParentVO(this.getClass(), this);
        _codeTableVOList.setElementAt(vCodeTableVO, index);
    } //-- void setCodeTableVO(int, edit.common.vo.CodeTableVO) 

    /**
     * Method setCodeTableVO
     * 
     * @param codeTableVOArray
     */
    public void setCodeTableVO(edit.common.vo.CodeTableVO[] codeTableVOArray)
    {
        //-- copy array
        _codeTableVOList.removeAllElements();
        for (int i = 0; i < codeTableVOArray.length; i++) {
            codeTableVOArray[i].setParentVO(this.getClass(), this);
            _codeTableVOList.addElement(codeTableVOArray[i]);
        }
    } //-- void setCodeTableVO(edit.common.vo.CodeTableVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CodeTableDefVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CodeTableDefVO) Unmarshaller.unmarshal(edit.common.vo.CodeTableDefVO.class, reader);
    } //-- edit.common.vo.CodeTableDefVO unmarshal(java.io.Reader) 

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
