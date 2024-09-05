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
 * Class FilteredAreaValueVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredAreaValueVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredAreaValuePK
     */
    private long _filteredAreaValuePK;

    /**
     * keeps track of state for field: _filteredAreaValuePK
     */
    private boolean _has_filteredAreaValuePK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _areaValueFK
     */
    private long _areaValueFK;

    /**
     * keeps track of state for field: _areaValueFK
     */
    private boolean _has_areaValueFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredAreaValueVO() {
        super();
    } //-- edit.common.vo.FilteredAreaValueVO()


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
        
        if (obj instanceof FilteredAreaValueVO) {
        
            FilteredAreaValueVO temp = (FilteredAreaValueVO)obj;
            if (this._filteredAreaValuePK != temp._filteredAreaValuePK)
                return false;
            if (this._has_filteredAreaValuePK != temp._has_filteredAreaValuePK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._areaValueFK != temp._areaValueFK)
                return false;
            if (this._has_areaValueFK != temp._has_areaValueFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAreaValueFKReturns the value of field
     * 'areaValueFK'.
     * 
     * @return the value of field 'areaValueFK'.
     */
    public long getAreaValueFK()
    {
        return this._areaValueFK;
    } //-- long getAreaValueFK() 

    /**
     * Method getFilteredAreaValuePKReturns the value of field
     * 'filteredAreaValuePK'.
     * 
     * @return the value of field 'filteredAreaValuePK'.
     */
    public long getFilteredAreaValuePK()
    {
        return this._filteredAreaValuePK;
    } //-- long getFilteredAreaValuePK() 

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
     * Method hasAreaValueFK
     */
    public boolean hasAreaValueFK()
    {
        return this._has_areaValueFK;
    } //-- boolean hasAreaValueFK() 

    /**
     * Method hasFilteredAreaValuePK
     */
    public boolean hasFilteredAreaValuePK()
    {
        return this._has_filteredAreaValuePK;
    } //-- boolean hasFilteredAreaValuePK() 

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
     * Method setAreaValueFKSets the value of field 'areaValueFK'.
     * 
     * @param areaValueFK the value of field 'areaValueFK'.
     */
    public void setAreaValueFK(long areaValueFK)
    {
        this._areaValueFK = areaValueFK;
        
        super.setVoChanged(true);
        this._has_areaValueFK = true;
    } //-- void setAreaValueFK(long) 

    /**
     * Method setFilteredAreaValuePKSets the value of field
     * 'filteredAreaValuePK'.
     * 
     * @param filteredAreaValuePK the value of field
     * 'filteredAreaValuePK'.
     */
    public void setFilteredAreaValuePK(long filteredAreaValuePK)
    {
        this._filteredAreaValuePK = filteredAreaValuePK;
        
        super.setVoChanged(true);
        this._has_filteredAreaValuePK = true;
    } //-- void setFilteredAreaValuePK(long) 

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
    public static edit.common.vo.FilteredAreaValueVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredAreaValueVO) Unmarshaller.unmarshal(edit.common.vo.FilteredAreaValueVO.class, reader);
    } //-- edit.common.vo.FilteredAreaValueVO unmarshal(java.io.Reader) 

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
