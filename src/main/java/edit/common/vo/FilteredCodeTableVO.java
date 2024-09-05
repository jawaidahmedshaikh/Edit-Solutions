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
 * Class FilteredCodeTableVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredCodeTableVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredCodeTablePK
     */
    private long _filteredCodeTablePK;

    /**
     * keeps track of state for field: _filteredCodeTablePK
     */
    private boolean _has_filteredCodeTablePK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _codeTableFK
     */
    private long _codeTableFK;

    /**
     * keeps track of state for field: _codeTableFK
     */
    private boolean _has_codeTableFK;

    /**
     * Field _codeDesc
     */
    private java.lang.String _codeDesc;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredCodeTableVO() {
        super();
    } //-- edit.common.vo.FilteredCodeTableVO()


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
        
        if (obj instanceof FilteredCodeTableVO) {
        
            FilteredCodeTableVO temp = (FilteredCodeTableVO)obj;
            if (this._filteredCodeTablePK != temp._filteredCodeTablePK)
                return false;
            if (this._has_filteredCodeTablePK != temp._has_filteredCodeTablePK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._codeTableFK != temp._codeTableFK)
                return false;
            if (this._has_codeTableFK != temp._has_codeTableFK)
                return false;
            if (this._codeDesc != null) {
                if (temp._codeDesc == null) return false;
                else if (!(this._codeDesc.equals(temp._codeDesc))) 
                    return false;
            }
            else if (temp._codeDesc != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getCodeTableFKReturns the value of field
     * 'codeTableFK'.
     * 
     * @return the value of field 'codeTableFK'.
     */
    public long getCodeTableFK()
    {
        return this._codeTableFK;
    } //-- long getCodeTableFK() 

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
     * Method getProductStructureFKReturns the value of field
     * 'productStructureFK'.
     * 
     * @return the value of field 'productStructureFK'.
     */
    public long getProductStructureFK()
    {
        return this._productStructureFK;
    } //-- long getProductStructureFK() 

    /**
     * Method hasCodeTableFK
     */
    public boolean hasCodeTableFK()
    {
        return this._has_codeTableFK;
    } //-- boolean hasCodeTableFK() 

    /**
     * Method hasFilteredCodeTablePK
     */
    public boolean hasFilteredCodeTablePK()
    {
        return this._has_filteredCodeTablePK;
    } //-- boolean hasFilteredCodeTablePK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

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
     * Method setCodeTableFKSets the value of field 'codeTableFK'.
     * 
     * @param codeTableFK the value of field 'codeTableFK'.
     */
    public void setCodeTableFK(long codeTableFK)
    {
        this._codeTableFK = codeTableFK;
        
        super.setVoChanged(true);
        this._has_codeTableFK = true;
    } //-- void setCodeTableFK(long) 

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
     * Method setProductStructureFKSets the value of field
     * 'productStructureFK'.
     * 
     * @param productStructureFK the value of field
     * 'productStructureFK'.
     */
    public void setProductStructureFK(long productStructureFK)
    {
        this._productStructureFK = productStructureFK;
        
        super.setVoChanged(true);
        this._has_productStructureFK = true;
    } //-- void setProductStructureFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FilteredCodeTableVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredCodeTableVO) Unmarshaller.unmarshal(edit.common.vo.FilteredCodeTableVO.class, reader);
    } //-- edit.common.vo.FilteredCodeTableVO unmarshal(java.io.Reader) 

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
