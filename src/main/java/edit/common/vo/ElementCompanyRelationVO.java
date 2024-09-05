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
 * Class ElementCompanyRelationVO.
 * 
 * @version $Revision$ $Date$
 */
public class ElementCompanyRelationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _elementCompanyRelationPK
     */
    private long _elementCompanyRelationPK;

    /**
     * keeps track of state for field: _elementCompanyRelationPK
     */
    private boolean _has_elementCompanyRelationPK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _elementFK
     */
    private long _elementFK;

    /**
     * keeps track of state for field: _elementFK
     */
    private boolean _has_elementFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElementCompanyRelationVO() {
        super();
    } //-- edit.common.vo.ElementCompanyRelationVO()


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
        
        if (obj instanceof ElementCompanyRelationVO) {
        
            ElementCompanyRelationVO temp = (ElementCompanyRelationVO)obj;
            if (this._elementCompanyRelationPK != temp._elementCompanyRelationPK)
                return false;
            if (this._has_elementCompanyRelationPK != temp._has_elementCompanyRelationPK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._elementFK != temp._elementFK)
                return false;
            if (this._has_elementFK != temp._has_elementFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getElementCompanyRelationPKReturns the value of field
     * 'elementCompanyRelationPK'.
     * 
     * @return the value of field 'elementCompanyRelationPK'.
     */
    public long getElementCompanyRelationPK()
    {
        return this._elementCompanyRelationPK;
    } //-- long getElementCompanyRelationPK() 

    /**
     * Method getElementFKReturns the value of field 'elementFK'.
     * 
     * @return the value of field 'elementFK'.
     */
    public long getElementFK()
    {
        return this._elementFK;
    } //-- long getElementFK() 

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
     * Method hasElementCompanyRelationPK
     */
    public boolean hasElementCompanyRelationPK()
    {
        return this._has_elementCompanyRelationPK;
    } //-- boolean hasElementCompanyRelationPK() 

    /**
     * Method hasElementFK
     */
    public boolean hasElementFK()
    {
        return this._has_elementFK;
    } //-- boolean hasElementFK() 

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
     * Method setElementCompanyRelationPKSets the value of field
     * 'elementCompanyRelationPK'.
     * 
     * @param elementCompanyRelationPK the value of field
     * 'elementCompanyRelationPK'.
     */
    public void setElementCompanyRelationPK(long elementCompanyRelationPK)
    {
        this._elementCompanyRelationPK = elementCompanyRelationPK;
        
        super.setVoChanged(true);
        this._has_elementCompanyRelationPK = true;
    } //-- void setElementCompanyRelationPK(long) 

    /**
     * Method setElementFKSets the value of field 'elementFK'.
     * 
     * @param elementFK the value of field 'elementFK'.
     */
    public void setElementFK(long elementFK)
    {
        this._elementFK = elementFK;
        
        super.setVoChanged(true);
        this._has_elementFK = true;
    } //-- void setElementFK(long) 

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
    public static edit.common.vo.ElementCompanyRelationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ElementCompanyRelationVO) Unmarshaller.unmarshal(edit.common.vo.ElementCompanyRelationVO.class, reader);
    } //-- edit.common.vo.ElementCompanyRelationVO unmarshal(java.io.Reader) 

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
