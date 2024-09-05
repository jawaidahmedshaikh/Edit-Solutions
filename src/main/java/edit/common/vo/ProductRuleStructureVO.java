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
 * Class ProductRuleStructureVO.
 * 
 * @version $Revision$ $Date$
 */
public class ProductRuleStructureVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _productRuleStructurePK
     */
    private long _productRuleStructurePK;

    /**
     * keeps track of state for field: _productRuleStructurePK
     */
    private boolean _has_productRuleStructurePK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _rulesFK
     */
    private long _rulesFK;

    /**
     * keeps track of state for field: _rulesFK
     */
    private boolean _has_rulesFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProductRuleStructureVO() {
        super();
    } //-- edit.common.vo.ProductRuleStructureVO()


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
        
        if (obj instanceof ProductRuleStructureVO) {
        
            ProductRuleStructureVO temp = (ProductRuleStructureVO)obj;
            if (this._productRuleStructurePK != temp._productRuleStructurePK)
                return false;
            if (this._has_productRuleStructurePK != temp._has_productRuleStructurePK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._rulesFK != temp._rulesFK)
                return false;
            if (this._has_rulesFK != temp._has_rulesFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getProductRuleStructurePKReturns the value of field
     * 'productRuleStructurePK'.
     * 
     * @return the value of field 'productRuleStructurePK'.
     */
    public long getProductRuleStructurePK()
    {
        return this._productRuleStructurePK;
    } //-- long getProductRuleStructurePK() 

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
     * Method getRulesFKReturns the value of field 'rulesFK'.
     * 
     * @return the value of field 'rulesFK'.
     */
    public long getRulesFK()
    {
        return this._rulesFK;
    } //-- long getRulesFK() 

    /**
     * Method hasProductRuleStructurePK
     */
    public boolean hasProductRuleStructurePK()
    {
        return this._has_productRuleStructurePK;
    } //-- boolean hasProductRuleStructurePK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

    /**
     * Method hasRulesFK
     */
    public boolean hasRulesFK()
    {
        return this._has_rulesFK;
    } //-- boolean hasRulesFK() 

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
     * Method setProductRuleStructurePKSets the value of field
     * 'productRuleStructurePK'.
     * 
     * @param productRuleStructurePK the value of field
     * 'productRuleStructurePK'.
     */
    public void setProductRuleStructurePK(long productRuleStructurePK)
    {
        this._productRuleStructurePK = productRuleStructurePK;
        
        super.setVoChanged(true);
        this._has_productRuleStructurePK = true;
    } //-- void setProductRuleStructurePK(long) 

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
     * Method setRulesFKSets the value of field 'rulesFK'.
     * 
     * @param rulesFK the value of field 'rulesFK'.
     */
    public void setRulesFK(long rulesFK)
    {
        this._rulesFK = rulesFK;
        
        super.setVoChanged(true);
        this._has_rulesFK = true;
    } //-- void setRulesFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ProductRuleStructureVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ProductRuleStructureVO) Unmarshaller.unmarshal(edit.common.vo.ProductRuleStructureVO.class, reader);
    } //-- edit.common.vo.ProductRuleStructureVO unmarshal(java.io.Reader) 

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
