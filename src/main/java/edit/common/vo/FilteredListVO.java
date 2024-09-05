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
 * Class FilteredListVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredListVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredListPK
     */
    private long _filteredListPK;

    /**
     * keeps track of state for field: _filteredListPK
     */
    private boolean _has_filteredListPK;

    /**
     * Field _companyStructureFK
     */
    private long _companyStructureFK;

    /**
     * keeps track of state for field: _companyStructureFK
     */
    private boolean _has_companyStructureFK;

    /**
     * Field _codeTableDefFK
     */
    private long _codeTableDefFK;

    /**
     * keeps track of state for field: _codeTableDefFK
     */
    private boolean _has_codeTableDefFK;

    /**
     * Field _codeTableFK
     */
    private long _codeTableFK;

    /**
     * keeps track of state for field: _codeTableFK
     */
    private boolean _has_codeTableFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredListVO() {
        super();
    } //-- edit.common.vo.FilteredListVO()


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
        
        if (obj instanceof FilteredListVO) {
        
            FilteredListVO temp = (FilteredListVO)obj;
            if (this._filteredListPK != temp._filteredListPK)
                return false;
            if (this._has_filteredListPK != temp._has_filteredListPK)
                return false;
            if (this._companyStructureFK != temp._companyStructureFK)
                return false;
            if (this._has_companyStructureFK != temp._has_companyStructureFK)
                return false;
            if (this._codeTableDefFK != temp._codeTableDefFK)
                return false;
            if (this._has_codeTableDefFK != temp._has_codeTableDefFK)
                return false;
            if (this._codeTableFK != temp._codeTableFK)
                return false;
            if (this._has_codeTableFK != temp._has_codeTableFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getCompanyStructureFKReturns the value of field
     * 'companyStructureFK'.
     * 
     * @return the value of field 'companyStructureFK'.
     */
    public long getCompanyStructureFK()
    {
        return this._companyStructureFK;
    } //-- long getCompanyStructureFK() 

    /**
     * Method getFilteredListPKReturns the value of field
     * 'filteredListPK'.
     * 
     * @return the value of field 'filteredListPK'.
     */
    public long getFilteredListPK()
    {
        return this._filteredListPK;
    } //-- long getFilteredListPK() 

    /**
     * Method hasCodeTableDefFK
     */
    public boolean hasCodeTableDefFK()
    {
        return this._has_codeTableDefFK;
    } //-- boolean hasCodeTableDefFK() 

    /**
     * Method hasCodeTableFK
     */
    public boolean hasCodeTableFK()
    {
        return this._has_codeTableFK;
    } //-- boolean hasCodeTableFK() 

    /**
     * Method hasCompanyStructureFK
     */
    public boolean hasCompanyStructureFK()
    {
        return this._has_companyStructureFK;
    } //-- boolean hasCompanyStructureFK() 

    /**
     * Method hasFilteredListPK
     */
    public boolean hasFilteredListPK()
    {
        return this._has_filteredListPK;
    } //-- boolean hasFilteredListPK() 

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
     * Method setCompanyStructureFKSets the value of field
     * 'companyStructureFK'.
     * 
     * @param companyStructureFK the value of field
     * 'companyStructureFK'.
     */
    public void setCompanyStructureFK(long companyStructureFK)
    {
        this._companyStructureFK = companyStructureFK;
        
        super.setVoChanged(true);
        this._has_companyStructureFK = true;
    } //-- void setCompanyStructureFK(long) 

    /**
     * Method setFilteredListPKSets the value of field
     * 'filteredListPK'.
     * 
     * @param filteredListPK the value of field 'filteredListPK'.
     */
    public void setFilteredListPK(long filteredListPK)
    {
        this._filteredListPK = filteredListPK;
        
        super.setVoChanged(true);
        this._has_filteredListPK = true;
    } //-- void setFilteredListPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FilteredListVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredListVO) Unmarshaller.unmarshal(edit.common.vo.FilteredListVO.class, reader);
    } //-- edit.common.vo.FilteredListVO unmarshal(java.io.Reader) 

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
