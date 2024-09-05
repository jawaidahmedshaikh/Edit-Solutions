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
 * Class FilteredOnlineReportVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredOnlineReportVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredOnlineReportPK
     */
    private long _filteredOnlineReportPK;

    /**
     * keeps track of state for field: _filteredOnlineReportPK
     */
    private boolean _has_filteredOnlineReportPK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _onlineReportFK
     */
    private long _onlineReportFK;

    /**
     * keeps track of state for field: _onlineReportFK
     */
    private boolean _has_onlineReportFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredOnlineReportVO() {
        super();
    } //-- edit.common.vo.FilteredOnlineReportVO()


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
        
        if (obj instanceof FilteredOnlineReportVO) {
        
            FilteredOnlineReportVO temp = (FilteredOnlineReportVO)obj;
            if (this._filteredOnlineReportPK != temp._filteredOnlineReportPK)
                return false;
            if (this._has_filteredOnlineReportPK != temp._has_filteredOnlineReportPK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._onlineReportFK != temp._onlineReportFK)
                return false;
            if (this._has_onlineReportFK != temp._has_onlineReportFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredOnlineReportPKReturns the value of field
     * 'filteredOnlineReportPK'.
     * 
     * @return the value of field 'filteredOnlineReportPK'.
     */
    public long getFilteredOnlineReportPK()
    {
        return this._filteredOnlineReportPK;
    } //-- long getFilteredOnlineReportPK() 

    /**
     * Method getOnlineReportFKReturns the value of field
     * 'onlineReportFK'.
     * 
     * @return the value of field 'onlineReportFK'.
     */
    public long getOnlineReportFK()
    {
        return this._onlineReportFK;
    } //-- long getOnlineReportFK() 

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
     * Method hasFilteredOnlineReportPK
     */
    public boolean hasFilteredOnlineReportPK()
    {
        return this._has_filteredOnlineReportPK;
    } //-- boolean hasFilteredOnlineReportPK() 

    /**
     * Method hasOnlineReportFK
     */
    public boolean hasOnlineReportFK()
    {
        return this._has_onlineReportFK;
    } //-- boolean hasOnlineReportFK() 

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
     * Method setFilteredOnlineReportPKSets the value of field
     * 'filteredOnlineReportPK'.
     * 
     * @param filteredOnlineReportPK the value of field
     * 'filteredOnlineReportPK'.
     */
    public void setFilteredOnlineReportPK(long filteredOnlineReportPK)
    {
        this._filteredOnlineReportPK = filteredOnlineReportPK;
        
        super.setVoChanged(true);
        this._has_filteredOnlineReportPK = true;
    } //-- void setFilteredOnlineReportPK(long) 

    /**
     * Method setOnlineReportFKSets the value of field
     * 'onlineReportFK'.
     * 
     * @param onlineReportFK the value of field 'onlineReportFK'.
     */
    public void setOnlineReportFK(long onlineReportFK)
    {
        this._onlineReportFK = onlineReportFK;
        
        super.setVoChanged(true);
        this._has_onlineReportFK = true;
    } //-- void setOnlineReportFK(long) 

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
    public static edit.common.vo.FilteredOnlineReportVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredOnlineReportVO) Unmarshaller.unmarshal(edit.common.vo.FilteredOnlineReportVO.class, reader);
    } //-- edit.common.vo.FilteredOnlineReportVO unmarshal(java.io.Reader) 

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
