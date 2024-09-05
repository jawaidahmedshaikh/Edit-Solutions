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
 * Class FilteredAreaVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredAreaVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredAreaPK
     */
    private long _filteredAreaPK;

    /**
     * keeps track of state for field: _filteredAreaPK
     */
    private boolean _has_filteredAreaPK;

    /**
     * Field _companyStructureFK
     */
    private long _companyStructureFK;

    /**
     * keeps track of state for field: _companyStructureFK
     */
    private boolean _has_companyStructureFK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _areaFK
     */
    private long _areaFK;

    /**
     * keeps track of state for field: _areaFK
     */
    private boolean _has_areaFK;

    /**
     * Field _renewalFundToFK
     */
    private long _renewalFundToFK;

    /**
     * keeps track of state for field: _renewalFundToFK
     */
    private boolean _has_renewalFundToFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredAreaVO() {
        super();
    } //-- edit.common.vo.FilteredAreaVO()


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
        
        if (obj instanceof FilteredAreaVO) {
        
            FilteredAreaVO temp = (FilteredAreaVO)obj;
            if (this._filteredAreaPK != temp._filteredAreaPK)
                return false;
            if (this._has_filteredAreaPK != temp._has_filteredAreaPK)
                return false;
            if (this._companyStructureFK != temp._companyStructureFK)
                return false;
            if (this._has_companyStructureFK != temp._has_companyStructureFK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._areaFK != temp._areaFK)
                return false;
            if (this._has_areaFK != temp._has_areaFK)
                return false;
            if (this._renewalFundToFK != temp._renewalFundToFK)
                return false;
            if (this._has_renewalFundToFK != temp._has_renewalFundToFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAreaFKReturns the value of field 'areaFK'.
     * 
     * @return the value of field 'areaFK'.
     */
    public long getAreaFK()
    {
        return this._areaFK;
    } //-- long getAreaFK() 

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
     * Method getFilteredAreaPKReturns the value of field
     * 'filteredAreaPK'.
     * 
     * @return the value of field 'filteredAreaPK'.
     */
    public long getFilteredAreaPK()
    {
        return this._filteredAreaPK;
    } //-- long getFilteredAreaPK() 

    /**
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

    /**
     * Method getRenewalFundToFKReturns the value of field
     * 'renewalFundToFK'.
     * 
     * @return the value of field 'renewalFundToFK'.
     */
    public long getRenewalFundToFK()
    {
        return this._renewalFundToFK;
    } //-- long getRenewalFundToFK() 

    /**
     * Method hasAreaFK
     */
    public boolean hasAreaFK()
    {
        return this._has_areaFK;
    } //-- boolean hasAreaFK() 

    /**
     * Method hasCompanyStructureFK
     */
    public boolean hasCompanyStructureFK()
    {
        return this._has_companyStructureFK;
    } //-- boolean hasCompanyStructureFK() 

    /**
     * Method hasFilteredAreaPK
     */
    public boolean hasFilteredAreaPK()
    {
        return this._has_filteredAreaPK;
    } //-- boolean hasFilteredAreaPK() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

    /**
     * Method hasRenewalFundToFK
     */
    public boolean hasRenewalFundToFK()
    {
        return this._has_renewalFundToFK;
    } //-- boolean hasRenewalFundToFK() 

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
     * Method setAreaFKSets the value of field 'areaFK'.
     * 
     * @param areaFK the value of field 'areaFK'.
     */
    public void setAreaFK(long areaFK)
    {
        this._areaFK = areaFK;
        
        super.setVoChanged(true);
        this._has_areaFK = true;
    } //-- void setAreaFK(long) 

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
     * Method setFilteredAreaPKSets the value of field
     * 'filteredAreaPK'.
     * 
     * @param filteredAreaPK the value of field 'filteredAreaPK'.
     */
    public void setFilteredAreaPK(long filteredAreaPK)
    {
        this._filteredAreaPK = filteredAreaPK;
        
        super.setVoChanged(true);
        this._has_filteredAreaPK = true;
    } //-- void setFilteredAreaPK(long) 

    /**
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

    /**
     * Method setRenewalFundToFKSets the value of field
     * 'renewalFundToFK'.
     * 
     * @param renewalFundToFK the value of field 'renewalFundToFK'.
     */
    public void setRenewalFundToFK(long renewalFundToFK)
    {
        this._renewalFundToFK = renewalFundToFK;
        
        super.setVoChanged(true);
        this._has_renewalFundToFK = true;
    } //-- void setRenewalFundToFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FilteredAreaVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredAreaVO) Unmarshaller.unmarshal(edit.common.vo.FilteredAreaVO.class, reader);
    } //-- edit.common.vo.FilteredAreaVO unmarshal(java.io.Reader) 

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
