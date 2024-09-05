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
import java.math.BigDecimal;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class BonusStatementLineVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusStatementLineVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentNumber
     */
    private java.lang.String _agentNumber;

    /**
     * Field _productType
     */
    private java.lang.String _productType;

    /**
     * Field _agentName
     */
    private java.lang.String _agentName;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _processDate
     */
    private java.lang.String _processDate;

    /**
     * Field _ownerName
     */
    private java.lang.String _ownerName;

    /**
     * Field _policyNumber
     */
    private java.lang.String _policyNumber;

    /**
     * Field _bonusPremium
     */
    private java.math.BigDecimal _bonusPremium;

    /**
     * Field _type
     */
    private java.lang.String _type;

    /**
     * Field _allocationPercent
     */
    private java.math.BigDecimal _allocationPercent;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusStatementLineVO() {
        super();
    } //-- edit.common.vo.BonusStatementLineVO()


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
        
        if (obj instanceof BonusStatementLineVO) {
        
            BonusStatementLineVO temp = (BonusStatementLineVO)obj;
            if (this._agentNumber != null) {
                if (temp._agentNumber == null) return false;
                else if (!(this._agentNumber.equals(temp._agentNumber))) 
                    return false;
            }
            else if (temp._agentNumber != null)
                return false;
            if (this._productType != null) {
                if (temp._productType == null) return false;
                else if (!(this._productType.equals(temp._productType))) 
                    return false;
            }
            else if (temp._productType != null)
                return false;
            if (this._agentName != null) {
                if (temp._agentName == null) return false;
                else if (!(this._agentName.equals(temp._agentName))) 
                    return false;
            }
            else if (temp._agentName != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._processDate != null) {
                if (temp._processDate == null) return false;
                else if (!(this._processDate.equals(temp._processDate))) 
                    return false;
            }
            else if (temp._processDate != null)
                return false;
            if (this._ownerName != null) {
                if (temp._ownerName == null) return false;
                else if (!(this._ownerName.equals(temp._ownerName))) 
                    return false;
            }
            else if (temp._ownerName != null)
                return false;
            if (this._policyNumber != null) {
                if (temp._policyNumber == null) return false;
                else if (!(this._policyNumber.equals(temp._policyNumber))) 
                    return false;
            }
            else if (temp._policyNumber != null)
                return false;
            if (this._bonusPremium != null) {
                if (temp._bonusPremium == null) return false;
                else if (!(this._bonusPremium.equals(temp._bonusPremium))) 
                    return false;
            }
            else if (temp._bonusPremium != null)
                return false;
            if (this._type != null) {
                if (temp._type == null) return false;
                else if (!(this._type.equals(temp._type))) 
                    return false;
            }
            else if (temp._type != null)
                return false;
            if (this._allocationPercent != null) {
                if (temp._allocationPercent == null) return false;
                else if (!(this._allocationPercent.equals(temp._allocationPercent))) 
                    return false;
            }
            else if (temp._allocationPercent != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentNameReturns the value of field 'agentName'.
     * 
     * @return the value of field 'agentName'.
     */
    public java.lang.String getAgentName()
    {
        return this._agentName;
    } //-- java.lang.String getAgentName() 

    /**
     * Method getAgentNumberReturns the value of field
     * 'agentNumber'.
     * 
     * @return the value of field 'agentNumber'.
     */
    public java.lang.String getAgentNumber()
    {
        return this._agentNumber;
    } //-- java.lang.String getAgentNumber() 

    /**
     * Method getAllocationPercentReturns the value of field
     * 'allocationPercent'.
     * 
     * @return the value of field 'allocationPercent'.
     */
    public java.math.BigDecimal getAllocationPercent()
    {
        return this._allocationPercent;
    } //-- java.math.BigDecimal getAllocationPercent() 

    /**
     * Method getBonusPremiumReturns the value of field
     * 'bonusPremium'.
     * 
     * @return the value of field 'bonusPremium'.
     */
    public java.math.BigDecimal getBonusPremium()
    {
        return this._bonusPremium;
    } //-- java.math.BigDecimal getBonusPremium() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getOwnerNameReturns the value of field 'ownerName'.
     * 
     * @return the value of field 'ownerName'.
     */
    public java.lang.String getOwnerName()
    {
        return this._ownerName;
    } //-- java.lang.String getOwnerName() 

    /**
     * Method getPolicyNumberReturns the value of field
     * 'policyNumber'.
     * 
     * @return the value of field 'policyNumber'.
     */
    public java.lang.String getPolicyNumber()
    {
        return this._policyNumber;
    } //-- java.lang.String getPolicyNumber() 

    /**
     * Method getProcessDateReturns the value of field
     * 'processDate'.
     * 
     * @return the value of field 'processDate'.
     */
    public java.lang.String getProcessDate()
    {
        return this._processDate;
    } //-- java.lang.String getProcessDate() 

    /**
     * Method getProductTypeReturns the value of field
     * 'productType'.
     * 
     * @return the value of field 'productType'.
     */
    public java.lang.String getProductType()
    {
        return this._productType;
    } //-- java.lang.String getProductType() 

    /**
     * Method getTypeReturns the value of field 'type'.
     * 
     * @return the value of field 'type'.
     */
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

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
     * Method setAgentNameSets the value of field 'agentName'.
     * 
     * @param agentName the value of field 'agentName'.
     */
    public void setAgentName(java.lang.String agentName)
    {
        this._agentName = agentName;
        
        super.setVoChanged(true);
    } //-- void setAgentName(java.lang.String) 

    /**
     * Method setAgentNumberSets the value of field 'agentNumber'.
     * 
     * @param agentNumber the value of field 'agentNumber'.
     */
    public void setAgentNumber(java.lang.String agentNumber)
    {
        this._agentNumber = agentNumber;
        
        super.setVoChanged(true);
    } //-- void setAgentNumber(java.lang.String) 

    /**
     * Method setAllocationPercentSets the value of field
     * 'allocationPercent'.
     * 
     * @param allocationPercent the value of field
     * 'allocationPercent'.
     */
    public void setAllocationPercent(java.math.BigDecimal allocationPercent)
    {
        this._allocationPercent = allocationPercent;
        
        super.setVoChanged(true);
    } //-- void setAllocationPercent(java.math.BigDecimal) 

    /**
     * Method setBonusPremiumSets the value of field
     * 'bonusPremium'.
     * 
     * @param bonusPremium the value of field 'bonusPremium'.
     */
    public void setBonusPremium(java.math.BigDecimal bonusPremium)
    {
        this._bonusPremium = bonusPremium;
        
        super.setVoChanged(true);
    } //-- void setBonusPremium(java.math.BigDecimal) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setOwnerNameSets the value of field 'ownerName'.
     * 
     * @param ownerName the value of field 'ownerName'.
     */
    public void setOwnerName(java.lang.String ownerName)
    {
        this._ownerName = ownerName;
        
        super.setVoChanged(true);
    } //-- void setOwnerName(java.lang.String) 

    /**
     * Method setPolicyNumberSets the value of field
     * 'policyNumber'.
     * 
     * @param policyNumber the value of field 'policyNumber'.
     */
    public void setPolicyNumber(java.lang.String policyNumber)
    {
        this._policyNumber = policyNumber;
        
        super.setVoChanged(true);
    } //-- void setPolicyNumber(java.lang.String) 

    /**
     * Method setProcessDateSets the value of field 'processDate'.
     * 
     * @param processDate the value of field 'processDate'.
     */
    public void setProcessDate(java.lang.String processDate)
    {
        this._processDate = processDate;
        
        super.setVoChanged(true);
    } //-- void setProcessDate(java.lang.String) 

    /**
     * Method setProductTypeSets the value of field 'productType'.
     * 
     * @param productType the value of field 'productType'.
     */
    public void setProductType(java.lang.String productType)
    {
        this._productType = productType;
        
        super.setVoChanged(true);
    } //-- void setProductType(java.lang.String) 

    /**
     * Method setTypeSets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(java.lang.String type)
    {
        this._type = type;
        
        super.setVoChanged(true);
    } //-- void setType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BonusStatementLineVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusStatementLineVO) Unmarshaller.unmarshal(edit.common.vo.BonusStatementLineVO.class, reader);
    } //-- edit.common.vo.BonusStatementLineVO unmarshal(java.io.Reader) 

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
