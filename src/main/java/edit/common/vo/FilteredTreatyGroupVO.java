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
 * Class FilteredTreatyGroupVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredTreatyGroupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredTreatyGroupPK
     */
    private long _filteredTreatyGroupPK;

    /**
     * keeps track of state for field: _filteredTreatyGroupPK
     */
    private boolean _has_filteredTreatyGroupPK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _treatyGroupFK
     */
    private long _treatyGroupFK;

    /**
     * keeps track of state for field: _treatyGroupFK
     */
    private boolean _has_treatyGroupFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredTreatyGroupVO() {
        super();
    } //-- edit.common.vo.FilteredTreatyGroupVO()


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
        
        if (obj instanceof FilteredTreatyGroupVO) {
        
            FilteredTreatyGroupVO temp = (FilteredTreatyGroupVO)obj;
            if (this._filteredTreatyGroupPK != temp._filteredTreatyGroupPK)
                return false;
            if (this._has_filteredTreatyGroupPK != temp._has_filteredTreatyGroupPK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._treatyGroupFK != temp._treatyGroupFK)
                return false;
            if (this._has_treatyGroupFK != temp._has_treatyGroupFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredTreatyGroupPKReturns the value of field
     * 'filteredTreatyGroupPK'.
     * 
     * @return the value of field 'filteredTreatyGroupPK'.
     */
    public long getFilteredTreatyGroupPK()
    {
        return this._filteredTreatyGroupPK;
    } //-- long getFilteredTreatyGroupPK() 

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
     * Method getTreatyGroupFKReturns the value of field
     * 'treatyGroupFK'.
     * 
     * @return the value of field 'treatyGroupFK'.
     */
    public long getTreatyGroupFK()
    {
        return this._treatyGroupFK;
    } //-- long getTreatyGroupFK() 

    /**
     * Method hasFilteredTreatyGroupPK
     */
    public boolean hasFilteredTreatyGroupPK()
    {
        return this._has_filteredTreatyGroupPK;
    } //-- boolean hasFilteredTreatyGroupPK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

    /**
     * Method hasTreatyGroupFK
     */
    public boolean hasTreatyGroupFK()
    {
        return this._has_treatyGroupFK;
    } //-- boolean hasTreatyGroupFK() 

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
     * Method setFilteredTreatyGroupPKSets the value of field
     * 'filteredTreatyGroupPK'.
     * 
     * @param filteredTreatyGroupPK the value of field
     * 'filteredTreatyGroupPK'.
     */
    public void setFilteredTreatyGroupPK(long filteredTreatyGroupPK)
    {
        this._filteredTreatyGroupPK = filteredTreatyGroupPK;
        
        super.setVoChanged(true);
        this._has_filteredTreatyGroupPK = true;
    } //-- void setFilteredTreatyGroupPK(long) 

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
     * Method setTreatyGroupFKSets the value of field
     * 'treatyGroupFK'.
     * 
     * @param treatyGroupFK the value of field 'treatyGroupFK'.
     */
    public void setTreatyGroupFK(long treatyGroupFK)
    {
        this._treatyGroupFK = treatyGroupFK;
        
        super.setVoChanged(true);
        this._has_treatyGroupFK = true;
    } //-- void setTreatyGroupFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FilteredTreatyGroupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredTreatyGroupVO) Unmarshaller.unmarshal(edit.common.vo.FilteredTreatyGroupVO.class, reader);
    } //-- edit.common.vo.FilteredTreatyGroupVO unmarshal(java.io.Reader) 

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
