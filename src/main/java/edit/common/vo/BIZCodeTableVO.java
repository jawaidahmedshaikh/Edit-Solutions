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
public class BIZCodeTableVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _isCodeTableAttached
     */
    private boolean _isCodeTableAttached;

    /**
     * keeps track of state for field: _isCodeTableAttached
     */
    private boolean _has_isCodeTableAttached;

    /**
     * Field _filteredCodeTablePK
     */
    private long _filteredCodeTablePK;

    /**
     * keeps track of state for field: _filteredCodeTablePK
     */
    private boolean _has_filteredCodeTablePK;

    /**
     * Field _codeDescriptionOverride
     */
    private java.lang.String _codeDescriptionOverride;

    /**
     * Field _codeTableVO
     */
    private edit.common.vo.CodeTableVO _codeTableVO;


      //----------------/
     //- Constructors -/
    //----------------/

    public BIZCodeTableVO() {
        super();
    } //-- edit.common.vo.BIZCodeTableVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof BIZCodeTableVO) {
        
            BIZCodeTableVO temp = (BIZCodeTableVO)obj;
            if (this._isCodeTableAttached != temp._isCodeTableAttached)
                return false;
            if (this._has_isCodeTableAttached != temp._has_isCodeTableAttached)
                return false;
            if (this._filteredCodeTablePK != temp._filteredCodeTablePK)
                return false;
            if (this._has_filteredCodeTablePK != temp._has_filteredCodeTablePK)
                return false;
            if (this._codeDescriptionOverride != null) {
                if (temp._codeDescriptionOverride == null) return false;
                else if (!(this._codeDescriptionOverride.equals(temp._codeDescriptionOverride))) 
                    return false;
            }
            else if (temp._codeDescriptionOverride != null)
                return false;
            if (this._codeTableVO != null) {
                if (temp._codeTableVO == null) return false;
                else if (!(this._codeTableVO.equals(temp._codeTableVO))) 
                    return false;
            }
            else if (temp._codeTableVO != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCodeDescriptionOverrideReturns the value of field
     * 'codeDescriptionOverride'.
     * 
     * @return the value of field 'codeDescriptionOverride'.
     */
    public java.lang.String getCodeDescriptionOverride()
    {
        return this._codeDescriptionOverride;
    } //-- java.lang.String getCodeDescriptionOverride() 

    /**
     * Method getCodeTableVOReturns the value of field
     * 'codeTableVO'.
     * 
     * @return the value of field 'codeTableVO'.
     */
    public edit.common.vo.CodeTableVO getCodeTableVO()
    {
        return this._codeTableVO;
    } //-- edit.common.vo.CodeTableVO getCodeTableVO() 

    /**
     * Method getFilteredCodeTablePKReturns the value of field
     * 'filteredCodeTablePK'.
     * 
     * @return the value of field 'filteredCodeTablePK'.
     */
    public long getFilteredCodeTablePK()
    {
        return this._filteredCodeTablePK;
    } //-- long getFilteredCodeTablePK() 

    /**
     * Method getIsCodeTableAttachedReturns the value of field
     * 'isCodeTableAttached'.
     * 
     * @return the value of field 'isCodeTableAttached'.
     */
    public boolean getIsCodeTableAttached()
    {
        return this._isCodeTableAttached;
    } //-- boolean getIsCodeTableAttached() 

    /**
     * Method hasFilteredCodeTablePK
     */
    public boolean hasFilteredCodeTablePK()
    {
        return this._has_filteredCodeTablePK;
    } //-- boolean hasFilteredCodeTablePK() 

    /**
     * Method hasIsCodeTableAttached
     */
    public boolean hasIsCodeTableAttached()
    {
        return this._has_isCodeTableAttached;
    } //-- boolean hasIsCodeTableAttached() 

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
     * Method setCodeDescriptionOverrideSets the value of field
     * 'codeDescriptionOverride'.
     * 
     * @param codeDescriptionOverride the value of field
     * 'codeDescriptionOverride'.
     */
    public void setCodeDescriptionOverride(java.lang.String codeDescriptionOverride)
    {
        this._codeDescriptionOverride = codeDescriptionOverride;
        
        super.setVoChanged(true);
    } //-- void setCodeDescriptionOverride(java.lang.String) 

    /**
     * Method setCodeTableVOSets the value of field 'codeTableVO'.
     * 
     * @param codeTableVO the value of field 'codeTableVO'.
     */
    public void setCodeTableVO(edit.common.vo.CodeTableVO codeTableVO)
    {
        this._codeTableVO = codeTableVO;
    } //-- void setCodeTableVO(edit.common.vo.CodeTableVO) 

    /**
     * Method setFilteredCodeTablePKSets the value of field
     * 'filteredCodeTablePK'.
     * 
     * @param filteredCodeTablePK the value of field
     * 'filteredCodeTablePK'.
     */
    public void setFilteredCodeTablePK(long filteredCodeTablePK)
    {
        this._filteredCodeTablePK = filteredCodeTablePK;
        
        super.setVoChanged(true);
        this._has_filteredCodeTablePK = true;
    } //-- void setFilteredCodeTablePK(long) 

    /**
     * Method setIsCodeTableAttachedSets the value of field
     * 'isCodeTableAttached'.
     * 
     * @param isCodeTableAttached the value of field
     * 'isCodeTableAttached'.
     */
    public void setIsCodeTableAttached(boolean isCodeTableAttached)
    {
        this._isCodeTableAttached = isCodeTableAttached;
        
        super.setVoChanged(true);
        this._has_isCodeTableAttached = true;
    } //-- void setIsCodeTableAttached(boolean) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BIZCodeTableVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BIZCodeTableVO) Unmarshaller.unmarshal(edit.common.vo.BIZCodeTableVO.class, reader);
    } //-- edit.common.vo.BIZCodeTableVO unmarshal(java.io.Reader) 

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
