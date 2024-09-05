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
 * Class AgentStatementLineVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentStatementLineVO extends edit.common.vo.VOObject  
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
     * Field _firstName
     */
    private java.lang.String _firstName;

    /**
     * Field _name
     */
    private java.lang.String _name;

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
     * Field _marketingPackageName
     */
    private java.lang.String _marketingPackageName;

    /**
     * Field _initialPremium
     */
    private java.math.BigDecimal _initialPremium;

    /**
     * Field _commissionRate
     */
    private java.math.BigDecimal _commissionRate;

    /**
     * Field _commissionPremium
     */
    private java.math.BigDecimal _commissionPremium;

    /**
     * Field _type
     */
    private java.lang.String _type;

    /**
     * Field _amountDue
     */
    private java.math.BigDecimal _amountDue;

    /**
     * Field _debitBalanceRepayment
     */
    private java.math.BigDecimal _debitBalanceRepayment;

    /**
     * Field _allocationPercent
     */
    private java.math.BigDecimal _allocationPercent;

    /**
     * Field _description
     */
    private java.lang.String _description;

    /**
     * Field _transactionType
     */
    private java.lang.String _transactionType;

    /**
     * Field _reportToNumber
     */
    private java.lang.String _reportToNumber;

    /**
     * Field _reportToName
     */
    private java.lang.String _reportToName;

    /**
     * Field _updateStatus
     */
    private java.lang.String _updateStatus;

    /**
     * Field _commHoldReleaseDate
     */
    private java.lang.String _commHoldReleaseDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentStatementLineVO() {
        super();
    } //-- edit.common.vo.AgentStatementLineVO()


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
        
        if (obj instanceof AgentStatementLineVO) {
        
            AgentStatementLineVO temp = (AgentStatementLineVO)obj;
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
            if (this._firstName != null) {
                if (temp._firstName == null) return false;
                else if (!(this._firstName.equals(temp._firstName))) 
                    return false;
            }
            else if (temp._firstName != null)
                return false;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
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
            if (this._marketingPackageName != null) {
                if (temp._marketingPackageName == null) return false;
                else if (!(this._marketingPackageName.equals(temp._marketingPackageName))) 
                    return false;
            }
            else if (temp._marketingPackageName != null)
                return false;
            if (this._initialPremium != null) {
                if (temp._initialPremium == null) return false;
                else if (!(this._initialPremium.equals(temp._initialPremium))) 
                    return false;
            }
            else if (temp._initialPremium != null)
                return false;
            if (this._commissionRate != null) {
                if (temp._commissionRate == null) return false;
                else if (!(this._commissionRate.equals(temp._commissionRate))) 
                    return false;
            }
            else if (temp._commissionRate != null)
                return false;
            if (this._commissionPremium != null) {
                if (temp._commissionPremium == null) return false;
                else if (!(this._commissionPremium.equals(temp._commissionPremium))) 
                    return false;
            }
            else if (temp._commissionPremium != null)
                return false;
            if (this._type != null) {
                if (temp._type == null) return false;
                else if (!(this._type.equals(temp._type))) 
                    return false;
            }
            else if (temp._type != null)
                return false;
            if (this._amountDue != null) {
                if (temp._amountDue == null) return false;
                else if (!(this._amountDue.equals(temp._amountDue))) 
                    return false;
            }
            else if (temp._amountDue != null)
                return false;
            if (this._debitBalanceRepayment != null) {
                if (temp._debitBalanceRepayment == null) return false;
                else if (!(this._debitBalanceRepayment.equals(temp._debitBalanceRepayment))) 
                    return false;
            }
            else if (temp._debitBalanceRepayment != null)
                return false;
            if (this._allocationPercent != null) {
                if (temp._allocationPercent == null) return false;
                else if (!(this._allocationPercent.equals(temp._allocationPercent))) 
                    return false;
            }
            else if (temp._allocationPercent != null)
                return false;
            if (this._description != null) {
                if (temp._description == null) return false;
                else if (!(this._description.equals(temp._description))) 
                    return false;
            }
            else if (temp._description != null)
                return false;
            if (this._transactionType != null) {
                if (temp._transactionType == null) return false;
                else if (!(this._transactionType.equals(temp._transactionType))) 
                    return false;
            }
            else if (temp._transactionType != null)
                return false;
            if (this._reportToNumber != null) {
                if (temp._reportToNumber == null) return false;
                else if (!(this._reportToNumber.equals(temp._reportToNumber))) 
                    return false;
            }
            else if (temp._reportToNumber != null)
                return false;
            if (this._reportToName != null) {
                if (temp._reportToName == null) return false;
                else if (!(this._reportToName.equals(temp._reportToName))) 
                    return false;
            }
            else if (temp._reportToName != null)
                return false;
            if (this._updateStatus != null) {
                if (temp._updateStatus == null) return false;
                else if (!(this._updateStatus.equals(temp._updateStatus))) 
                    return false;
            }
            else if (temp._updateStatus != null)
                return false;
            if (this._commHoldReleaseDate != null) {
                if (temp._commHoldReleaseDate == null) return false;
                else if (!(this._commHoldReleaseDate.equals(temp._commHoldReleaseDate))) 
                    return false;
            }
            else if (temp._commHoldReleaseDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getAmountDueReturns the value of field 'amountDue'.
     * 
     * @return the value of field 'amountDue'.
     */
    public java.math.BigDecimal getAmountDue()
    {
        return this._amountDue;
    } //-- java.math.BigDecimal getAmountDue() 

    /**
     * Method getCommHoldReleaseDateReturns the value of field
     * 'commHoldReleaseDate'.
     * 
     * @return the value of field 'commHoldReleaseDate'.
     */
    public java.lang.String getCommHoldReleaseDate()
    {
        return this._commHoldReleaseDate;
    } //-- java.lang.String getCommHoldReleaseDate() 

    /**
     * Method getCommissionPremiumReturns the value of field
     * 'commissionPremium'.
     * 
     * @return the value of field 'commissionPremium'.
     */
    public java.math.BigDecimal getCommissionPremium()
    {
        return this._commissionPremium;
    } //-- java.math.BigDecimal getCommissionPremium() 

    /**
     * Method getCommissionRateReturns the value of field
     * 'commissionRate'.
     * 
     * @return the value of field 'commissionRate'.
     */
    public java.math.BigDecimal getCommissionRate()
    {
        return this._commissionRate;
    } //-- java.math.BigDecimal getCommissionRate() 

    /**
     * Method getDebitBalanceRepaymentReturns the value of field
     * 'debitBalanceRepayment'.
     * 
     * @return the value of field 'debitBalanceRepayment'.
     */
    public java.math.BigDecimal getDebitBalanceRepayment()
    {
        return this._debitBalanceRepayment;
    } //-- java.math.BigDecimal getDebitBalanceRepayment() 

    /**
     * Method getDescriptionReturns the value of field
     * 'description'.
     * 
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

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
     * Method getFirstNameReturns the value of field 'firstName'.
     * 
     * @return the value of field 'firstName'.
     */
    public java.lang.String getFirstName()
    {
        return this._firstName;
    } //-- java.lang.String getFirstName() 

    /**
     * Method getInitialPremiumReturns the value of field
     * 'initialPremium'.
     * 
     * @return the value of field 'initialPremium'.
     */
    public java.math.BigDecimal getInitialPremium()
    {
        return this._initialPremium;
    } //-- java.math.BigDecimal getInitialPremium() 

    /**
     * Method getMarketingPackageNameReturns the value of field
     * 'marketingPackageName'.
     * 
     * @return the value of field 'marketingPackageName'.
     */
    public java.lang.String getMarketingPackageName()
    {
        return this._marketingPackageName;
    } //-- java.lang.String getMarketingPackageName() 

    /**
     * Method getNameReturns the value of field 'name'.
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

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
     * Method getReportToNameReturns the value of field
     * 'reportToName'.
     * 
     * @return the value of field 'reportToName'.
     */
    public java.lang.String getReportToName()
    {
        return this._reportToName;
    } //-- java.lang.String getReportToName() 

    /**
     * Method getReportToNumberReturns the value of field
     * 'reportToNumber'.
     * 
     * @return the value of field 'reportToNumber'.
     */
    public java.lang.String getReportToNumber()
    {
        return this._reportToNumber;
    } //-- java.lang.String getReportToNumber() 

    /**
     * Method getTransactionTypeReturns the value of field
     * 'transactionType'.
     * 
     * @return the value of field 'transactionType'.
     */
    public java.lang.String getTransactionType()
    {
        return this._transactionType;
    } //-- java.lang.String getTransactionType() 

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
     * Method getUpdateStatusReturns the value of field
     * 'updateStatus'.
     * 
     * @return the value of field 'updateStatus'.
     */
    public java.lang.String getUpdateStatus()
    {
        return this._updateStatus;
    } //-- java.lang.String getUpdateStatus() 

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
     * Method setAmountDueSets the value of field 'amountDue'.
     * 
     * @param amountDue the value of field 'amountDue'.
     */
    public void setAmountDue(java.math.BigDecimal amountDue)
    {
        this._amountDue = amountDue;
        
        super.setVoChanged(true);
    } //-- void setAmountDue(java.math.BigDecimal) 

    /**
     * Method setCommHoldReleaseDateSets the value of field
     * 'commHoldReleaseDate'.
     * 
     * @param commHoldReleaseDate the value of field
     * 'commHoldReleaseDate'.
     */
    public void setCommHoldReleaseDate(java.lang.String commHoldReleaseDate)
    {
        this._commHoldReleaseDate = commHoldReleaseDate;
        
        super.setVoChanged(true);
    } //-- void setCommHoldReleaseDate(java.lang.String) 

    /**
     * Method setCommissionPremiumSets the value of field
     * 'commissionPremium'.
     * 
     * @param commissionPremium the value of field
     * 'commissionPremium'.
     */
    public void setCommissionPremium(java.math.BigDecimal commissionPremium)
    {
        this._commissionPremium = commissionPremium;
        
        super.setVoChanged(true);
    } //-- void setCommissionPremium(java.math.BigDecimal) 

    /**
     * Method setCommissionRateSets the value of field
     * 'commissionRate'.
     * 
     * @param commissionRate the value of field 'commissionRate'.
     */
    public void setCommissionRate(java.math.BigDecimal commissionRate)
    {
        this._commissionRate = commissionRate;
        
        super.setVoChanged(true);
    } //-- void setCommissionRate(java.math.BigDecimal) 

    /**
     * Method setDebitBalanceRepaymentSets the value of field
     * 'debitBalanceRepayment'.
     * 
     * @param debitBalanceRepayment the value of field
     * 'debitBalanceRepayment'.
     */
    public void setDebitBalanceRepayment(java.math.BigDecimal debitBalanceRepayment)
    {
        this._debitBalanceRepayment = debitBalanceRepayment;
        
        super.setVoChanged(true);
    } //-- void setDebitBalanceRepayment(java.math.BigDecimal) 

    /**
     * Method setDescriptionSets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
        
        super.setVoChanged(true);
    } //-- void setDescription(java.lang.String) 

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
     * Method setFirstNameSets the value of field 'firstName'.
     * 
     * @param firstName the value of field 'firstName'.
     */
    public void setFirstName(java.lang.String firstName)
    {
        this._firstName = firstName;
        
        super.setVoChanged(true);
    } //-- void setFirstName(java.lang.String) 

    /**
     * Method setInitialPremiumSets the value of field
     * 'initialPremium'.
     * 
     * @param initialPremium the value of field 'initialPremium'.
     */
    public void setInitialPremium(java.math.BigDecimal initialPremium)
    {
        this._initialPremium = initialPremium;
        
        super.setVoChanged(true);
    } //-- void setInitialPremium(java.math.BigDecimal) 

    /**
     * Method setMarketingPackageNameSets the value of field
     * 'marketingPackageName'.
     * 
     * @param marketingPackageName the value of field
     * 'marketingPackageName'.
     */
    public void setMarketingPackageName(java.lang.String marketingPackageName)
    {
        this._marketingPackageName = marketingPackageName;
        
        super.setVoChanged(true);
    } //-- void setMarketingPackageName(java.lang.String) 

    /**
     * Method setNameSets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
        
        super.setVoChanged(true);
    } //-- void setName(java.lang.String) 

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
     * Method setReportToNameSets the value of field
     * 'reportToName'.
     * 
     * @param reportToName the value of field 'reportToName'.
     */
    public void setReportToName(java.lang.String reportToName)
    {
        this._reportToName = reportToName;
        
        super.setVoChanged(true);
    } //-- void setReportToName(java.lang.String) 

    /**
     * Method setReportToNumberSets the value of field
     * 'reportToNumber'.
     * 
     * @param reportToNumber the value of field 'reportToNumber'.
     */
    public void setReportToNumber(java.lang.String reportToNumber)
    {
        this._reportToNumber = reportToNumber;
        
        super.setVoChanged(true);
    } //-- void setReportToNumber(java.lang.String) 

    /**
     * Method setTransactionTypeSets the value of field
     * 'transactionType'.
     * 
     * @param transactionType the value of field 'transactionType'.
     */
    public void setTransactionType(java.lang.String transactionType)
    {
        this._transactionType = transactionType;
        
        super.setVoChanged(true);
    } //-- void setTransactionType(java.lang.String) 

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
     * Method setUpdateStatusSets the value of field
     * 'updateStatus'.
     * 
     * @param updateStatus the value of field 'updateStatus'.
     */
    public void setUpdateStatus(java.lang.String updateStatus)
    {
        this._updateStatus = updateStatus;
        
        super.setVoChanged(true);
    } //-- void setUpdateStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentStatementLineVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentStatementLineVO) Unmarshaller.unmarshal(edit.common.vo.AgentStatementLineVO.class, reader);
    } //-- edit.common.vo.AgentStatementLineVO unmarshal(java.io.Reader) 

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
