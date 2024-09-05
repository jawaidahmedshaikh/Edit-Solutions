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
 * Class BonusContributingProductVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusContributingProductVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bonusContributingProductPK
     */
    private long _bonusContributingProductPK;

    /**
     * keeps track of state for field: _bonusContributingProductPK
     */
    private boolean _has_bonusContributingProductPK;

    /**
     * Field _bonusCriteriaFK
     */
    private long _bonusCriteriaFK;

    /**
     * keeps track of state for field: _bonusCriteriaFK
     */
    private boolean _has_bonusCriteriaFK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusContributingProductVO() {
        super();
    } //-- edit.common.vo.BonusContributingProductVO()


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
        
        if (obj instanceof BonusContributingProductVO) {
        
            BonusContributingProductVO temp = (BonusContributingProductVO)obj;
            if (this._bonusContributingProductPK != temp._bonusContributingProductPK)
                return false;
            if (this._has_bonusContributingProductPK != temp._has_bonusContributingProductPK)
                return false;
            if (this._bonusCriteriaFK != temp._bonusCriteriaFK)
                return false;
            if (this._has_bonusCriteriaFK != temp._has_bonusCriteriaFK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusContributingProductPKReturns the value of
     * field 'bonusContributingProductPK'.
     * 
     * @return the value of field 'bonusContributingProductPK'.
     */
    public long getBonusContributingProductPK()
    {
        return this._bonusContributingProductPK;
    } //-- long getBonusContributingProductPK() 

    /**
     * Method getBonusCriteriaFKReturns the value of field
     * 'bonusCriteriaFK'.
     * 
     * @return the value of field 'bonusCriteriaFK'.
     */
    public long getBonusCriteriaFK()
    {
        return this._bonusCriteriaFK;
    } //-- long getBonusCriteriaFK() 

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
     * Method hasBonusContributingProductPK
     */
    public boolean hasBonusContributingProductPK()
    {
        return this._has_bonusContributingProductPK;
    } //-- boolean hasBonusContributingProductPK() 

    /**
     * Method hasBonusCriteriaFK
     */
    public boolean hasBonusCriteriaFK()
    {
        return this._has_bonusCriteriaFK;
    } //-- boolean hasBonusCriteriaFK() 

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
     * Method setBonusContributingProductPKSets the value of field
     * 'bonusContributingProductPK'.
     * 
     * @param bonusContributingProductPK the value of field
     * 'bonusContributingProductPK'.
     */
    public void setBonusContributingProductPK(long bonusContributingProductPK)
    {
        this._bonusContributingProductPK = bonusContributingProductPK;
        
        super.setVoChanged(true);
        this._has_bonusContributingProductPK = true;
    } //-- void setBonusContributingProductPK(long) 

    /**
     * Method setBonusCriteriaFKSets the value of field
     * 'bonusCriteriaFK'.
     * 
     * @param bonusCriteriaFK the value of field 'bonusCriteriaFK'.
     */
    public void setBonusCriteriaFK(long bonusCriteriaFK)
    {
        this._bonusCriteriaFK = bonusCriteriaFK;
        
        super.setVoChanged(true);
        this._has_bonusCriteriaFK = true;
    } //-- void setBonusCriteriaFK(long) 

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
    public static edit.common.vo.BonusContributingProductVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusContributingProductVO) Unmarshaller.unmarshal(edit.common.vo.BonusContributingProductVO.class, reader);
    } //-- edit.common.vo.BonusContributingProductVO unmarshal(java.io.Reader) 

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
