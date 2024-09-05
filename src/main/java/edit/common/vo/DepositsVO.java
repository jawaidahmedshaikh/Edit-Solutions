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
 * Class DepositsVO.
 * 
 * @version $Revision$ $Date$
 */
public class DepositsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _depositsPK
     */
    private long _depositsPK;

    /**
     * keeps track of state for field: _depositsPK
     */
    private boolean _has_depositsPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _depositTypeCT
     */
    private java.lang.String _depositTypeCT;

    /**
     * Field _oldCompany
     */
    private java.lang.String _oldCompany;

    /**
     * Field _oldPolicyNumber
     */
    private java.lang.String _oldPolicyNumber;

    /**
     * Field _anticipatedAmount
     */
    private java.math.BigDecimal _anticipatedAmount;

    /**
     * Field _dateReceived
     */
    private java.lang.String _dateReceived;

    /**
     * Field _costBasis
     */
    private java.math.BigDecimal _costBasis;

    /**
     * Field _amountReceived
     */
    private java.math.BigDecimal _amountReceived;

    /**
     * Field _suspenseFK
     */
    private long _suspenseFK;

    /**
     * keeps track of state for field: _suspenseFK
     */
    private boolean _has_suspenseFK;

    /**
     * Field _taxYear
     */
    private int _taxYear;

    /**
     * keeps track of state for field: _taxYear
     */
    private boolean _has_taxYear;

    /**
     * Field _cashBatchContractFK
     */
    private long _cashBatchContractFK;

    /**
     * keeps track of state for field: _cashBatchContractFK
     */
    private boolean _has_cashBatchContractFK;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _priorCompanyMECStatusCT
     */
    private java.lang.String _priorCompanyMECStatusCT;

    /**
     * Field _exchangePolicyEffectiveDate
     */
    private java.lang.String _exchangePolicyEffectiveDate;

    /**
     * Field _exchangeIssueAge
     */
    private int _exchangeIssueAge;

    /**
     * keeps track of state for field: _exchangeIssueAge
     */
    private boolean _has_exchangeIssueAge;

    /**
     * Field _exchangeDuration
     */
    private int _exchangeDuration;

    /**
     * keeps track of state for field: _exchangeDuration
     */
    private boolean _has_exchangeDuration;

    /**
     * Field _preTEFRAGain
     */
    private java.math.BigDecimal _preTEFRAGain;

    /**
     * Field _postTEFRAGain
     */
    private java.math.BigDecimal _postTEFRAGain;

    /**
     * Field _preTEFRAAmount
     */
    private java.math.BigDecimal _preTEFRAAmount;

    /**
     * Field _postTEFRAAmount
     */
    private java.math.BigDecimal _postTEFRAAmount;

    /**
     * Field _exchangeLoanAmount
     */
    private java.math.BigDecimal _exchangeLoanAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public DepositsVO() {
        super();
    } //-- edit.common.vo.DepositsVO()


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
        
        if (obj instanceof DepositsVO) {
        
            DepositsVO temp = (DepositsVO)obj;
            if (this._depositsPK != temp._depositsPK)
                return false;
            if (this._has_depositsPK != temp._has_depositsPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._depositTypeCT != null) {
                if (temp._depositTypeCT == null) return false;
                else if (!(this._depositTypeCT.equals(temp._depositTypeCT))) 
                    return false;
            }
            else if (temp._depositTypeCT != null)
                return false;
            if (this._oldCompany != null) {
                if (temp._oldCompany == null) return false;
                else if (!(this._oldCompany.equals(temp._oldCompany))) 
                    return false;
            }
            else if (temp._oldCompany != null)
                return false;
            if (this._oldPolicyNumber != null) {
                if (temp._oldPolicyNumber == null) return false;
                else if (!(this._oldPolicyNumber.equals(temp._oldPolicyNumber))) 
                    return false;
            }
            else if (temp._oldPolicyNumber != null)
                return false;
            if (this._anticipatedAmount != null) {
                if (temp._anticipatedAmount == null) return false;
                else if (!(this._anticipatedAmount.equals(temp._anticipatedAmount))) 
                    return false;
            }
            else if (temp._anticipatedAmount != null)
                return false;
            if (this._dateReceived != null) {
                if (temp._dateReceived == null) return false;
                else if (!(this._dateReceived.equals(temp._dateReceived))) 
                    return false;
            }
            else if (temp._dateReceived != null)
                return false;
            if (this._costBasis != null) {
                if (temp._costBasis == null) return false;
                else if (!(this._costBasis.equals(temp._costBasis))) 
                    return false;
            }
            else if (temp._costBasis != null)
                return false;
            if (this._amountReceived != null) {
                if (temp._amountReceived == null) return false;
                else if (!(this._amountReceived.equals(temp._amountReceived))) 
                    return false;
            }
            else if (temp._amountReceived != null)
                return false;
            if (this._suspenseFK != temp._suspenseFK)
                return false;
            if (this._has_suspenseFK != temp._has_suspenseFK)
                return false;
            if (this._taxYear != temp._taxYear)
                return false;
            if (this._has_taxYear != temp._has_taxYear)
                return false;
            if (this._cashBatchContractFK != temp._cashBatchContractFK)
                return false;
            if (this._has_cashBatchContractFK != temp._has_cashBatchContractFK)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._priorCompanyMECStatusCT != null) {
                if (temp._priorCompanyMECStatusCT == null) return false;
                else if (!(this._priorCompanyMECStatusCT.equals(temp._priorCompanyMECStatusCT))) 
                    return false;
            }
            else if (temp._priorCompanyMECStatusCT != null)
                return false;
            if (this._exchangePolicyEffectiveDate != null) {
                if (temp._exchangePolicyEffectiveDate == null) return false;
                else if (!(this._exchangePolicyEffectiveDate.equals(temp._exchangePolicyEffectiveDate))) 
                    return false;
            }
            else if (temp._exchangePolicyEffectiveDate != null)
                return false;
            if (this._exchangeIssueAge != temp._exchangeIssueAge)
                return false;
            if (this._has_exchangeIssueAge != temp._has_exchangeIssueAge)
                return false;
            if (this._exchangeDuration != temp._exchangeDuration)
                return false;
            if (this._has_exchangeDuration != temp._has_exchangeDuration)
                return false;
            if (this._preTEFRAGain != null) {
                if (temp._preTEFRAGain == null) return false;
                else if (!(this._preTEFRAGain.equals(temp._preTEFRAGain))) 
                    return false;
            }
            else if (temp._preTEFRAGain != null)
                return false;
            if (this._postTEFRAGain != null) {
                if (temp._postTEFRAGain == null) return false;
                else if (!(this._postTEFRAGain.equals(temp._postTEFRAGain))) 
                    return false;
            }
            else if (temp._postTEFRAGain != null)
                return false;
            if (this._preTEFRAAmount != null) {
                if (temp._preTEFRAAmount == null) return false;
                else if (!(this._preTEFRAAmount.equals(temp._preTEFRAAmount))) 
                    return false;
            }
            else if (temp._preTEFRAAmount != null)
                return false;
            if (this._postTEFRAAmount != null) {
                if (temp._postTEFRAAmount == null) return false;
                else if (!(this._postTEFRAAmount.equals(temp._postTEFRAAmount))) 
                    return false;
            }
            else if (temp._postTEFRAAmount != null)
                return false;
            if (this._exchangeLoanAmount != null) {
                if (temp._exchangeLoanAmount == null) return false;
                else if (!(this._exchangeLoanAmount.equals(temp._exchangeLoanAmount))) 
                    return false;
            }
            else if (temp._exchangeLoanAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAmountReceivedReturns the value of field
     * 'amountReceived'.
     * 
     * @return the value of field 'amountReceived'.
     */
    public java.math.BigDecimal getAmountReceived()
    {
        return this._amountReceived;
    } //-- java.math.BigDecimal getAmountReceived() 

    /**
     * Method getAnticipatedAmountReturns the value of field
     * 'anticipatedAmount'.
     * 
     * @return the value of field 'anticipatedAmount'.
     */
    public java.math.BigDecimal getAnticipatedAmount()
    {
        return this._anticipatedAmount;
    } //-- java.math.BigDecimal getAnticipatedAmount() 

    /**
     * Method getCashBatchContractFKReturns the value of field
     * 'cashBatchContractFK'.
     * 
     * @return the value of field 'cashBatchContractFK'.
     */
    public long getCashBatchContractFK()
    {
        return this._cashBatchContractFK;
    } //-- long getCashBatchContractFK() 

    /**
     * Method getCostBasisReturns the value of field 'costBasis'.
     * 
     * @return the value of field 'costBasis'.
     */
    public java.math.BigDecimal getCostBasis()
    {
        return this._costBasis;
    } //-- java.math.BigDecimal getCostBasis() 

    /**
     * Method getDateReceivedReturns the value of field
     * 'dateReceived'.
     * 
     * @return the value of field 'dateReceived'.
     */
    public java.lang.String getDateReceived()
    {
        return this._dateReceived;
    } //-- java.lang.String getDateReceived() 

    /**
     * Method getDepositTypeCTReturns the value of field
     * 'depositTypeCT'.
     * 
     * @return the value of field 'depositTypeCT'.
     */
    public java.lang.String getDepositTypeCT()
    {
        return this._depositTypeCT;
    } //-- java.lang.String getDepositTypeCT() 

    /**
     * Method getDepositsPKReturns the value of field 'depositsPK'.
     * 
     * @return the value of field 'depositsPK'.
     */
    public long getDepositsPK()
    {
        return this._depositsPK;
    } //-- long getDepositsPK() 

    /**
     * Method getEDITTrxFKReturns the value of field 'EDITTrxFK'.
     * 
     * @return the value of field 'EDITTrxFK'.
     */
    public long getEDITTrxFK()
    {
        return this._EDITTrxFK;
    } //-- long getEDITTrxFK() 

    /**
     * Method getExchangeDurationReturns the value of field
     * 'exchangeDuration'.
     * 
     * @return the value of field 'exchangeDuration'.
     */
    public int getExchangeDuration()
    {
        return this._exchangeDuration;
    } //-- int getExchangeDuration() 

    /**
     * Method getExchangeIssueAgeReturns the value of field
     * 'exchangeIssueAge'.
     * 
     * @return the value of field 'exchangeIssueAge'.
     */
    public int getExchangeIssueAge()
    {
        return this._exchangeIssueAge;
    } //-- int getExchangeIssueAge() 

    /**
     * Method getExchangeLoanAmountReturns the value of field
     * 'exchangeLoanAmount'.
     * 
     * @return the value of field 'exchangeLoanAmount'.
     */
    public java.math.BigDecimal getExchangeLoanAmount()
    {
        return this._exchangeLoanAmount;
    } //-- java.math.BigDecimal getExchangeLoanAmount() 

    /**
     * Method getExchangePolicyEffectiveDateReturns the value of
     * field 'exchangePolicyEffectiveDate'.
     * 
     * @return the value of field 'exchangePolicyEffectiveDate'.
     */
    public java.lang.String getExchangePolicyEffectiveDate()
    {
        return this._exchangePolicyEffectiveDate;
    } //-- java.lang.String getExchangePolicyEffectiveDate() 

    /**
     * Method getOldCompanyReturns the value of field 'oldCompany'.
     * 
     * @return the value of field 'oldCompany'.
     */
    public java.lang.String getOldCompany()
    {
        return this._oldCompany;
    } //-- java.lang.String getOldCompany() 

    /**
     * Method getOldPolicyNumberReturns the value of field
     * 'oldPolicyNumber'.
     * 
     * @return the value of field 'oldPolicyNumber'.
     */
    public java.lang.String getOldPolicyNumber()
    {
        return this._oldPolicyNumber;
    } //-- java.lang.String getOldPolicyNumber() 

    /**
     * Method getPostTEFRAAmountReturns the value of field
     * 'postTEFRAAmount'.
     * 
     * @return the value of field 'postTEFRAAmount'.
     */
    public java.math.BigDecimal getPostTEFRAAmount()
    {
        return this._postTEFRAAmount;
    } //-- java.math.BigDecimal getPostTEFRAAmount() 

    /**
     * Method getPostTEFRAGainReturns the value of field
     * 'postTEFRAGain'.
     * 
     * @return the value of field 'postTEFRAGain'.
     */
    public java.math.BigDecimal getPostTEFRAGain()
    {
        return this._postTEFRAGain;
    } //-- java.math.BigDecimal getPostTEFRAGain() 

    /**
     * Method getPreTEFRAAmountReturns the value of field
     * 'preTEFRAAmount'.
     * 
     * @return the value of field 'preTEFRAAmount'.
     */
    public java.math.BigDecimal getPreTEFRAAmount()
    {
        return this._preTEFRAAmount;
    } //-- java.math.BigDecimal getPreTEFRAAmount() 

    /**
     * Method getPreTEFRAGainReturns the value of field
     * 'preTEFRAGain'.
     * 
     * @return the value of field 'preTEFRAGain'.
     */
    public java.math.BigDecimal getPreTEFRAGain()
    {
        return this._preTEFRAGain;
    } //-- java.math.BigDecimal getPreTEFRAGain() 

    /**
     * Method getPriorCompanyMECStatusCTReturns the value of field
     * 'priorCompanyMECStatusCT'.
     * 
     * @return the value of field 'priorCompanyMECStatusCT'.
     */
    public java.lang.String getPriorCompanyMECStatusCT()
    {
        return this._priorCompanyMECStatusCT;
    } //-- java.lang.String getPriorCompanyMECStatusCT() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method getSuspenseFKReturns the value of field 'suspenseFK'.
     * 
     * @return the value of field 'suspenseFK'.
     */
    public long getSuspenseFK()
    {
        return this._suspenseFK;
    } //-- long getSuspenseFK() 

    /**
     * Method getTaxYearReturns the value of field 'taxYear'.
     * 
     * @return the value of field 'taxYear'.
     */
    public int getTaxYear()
    {
        return this._taxYear;
    } //-- int getTaxYear() 

    /**
     * Method hasCashBatchContractFK
     */
    public boolean hasCashBatchContractFK()
    {
        return this._has_cashBatchContractFK;
    } //-- boolean hasCashBatchContractFK() 

    /**
     * Method hasDepositsPK
     */
    public boolean hasDepositsPK()
    {
        return this._has_depositsPK;
    } //-- boolean hasDepositsPK() 

    /**
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasExchangeDuration
     */
    public boolean hasExchangeDuration()
    {
        return this._has_exchangeDuration;
    } //-- boolean hasExchangeDuration() 

    /**
     * Method hasExchangeIssueAge
     */
    public boolean hasExchangeIssueAge()
    {
        return this._has_exchangeIssueAge;
    } //-- boolean hasExchangeIssueAge() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasSuspenseFK
     */
    public boolean hasSuspenseFK()
    {
        return this._has_suspenseFK;
    } //-- boolean hasSuspenseFK() 

    /**
     * Method hasTaxYear
     */
    public boolean hasTaxYear()
    {
        return this._has_taxYear;
    } //-- boolean hasTaxYear() 

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
     * Method setAmountReceivedSets the value of field
     * 'amountReceived'.
     * 
     * @param amountReceived the value of field 'amountReceived'.
     */
    public void setAmountReceived(java.math.BigDecimal amountReceived)
    {
        this._amountReceived = amountReceived;
        
        super.setVoChanged(true);
    } //-- void setAmountReceived(java.math.BigDecimal) 

    /**
     * Method setAnticipatedAmountSets the value of field
     * 'anticipatedAmount'.
     * 
     * @param anticipatedAmount the value of field
     * 'anticipatedAmount'.
     */
    public void setAnticipatedAmount(java.math.BigDecimal anticipatedAmount)
    {
        this._anticipatedAmount = anticipatedAmount;
        
        super.setVoChanged(true);
    } //-- void setAnticipatedAmount(java.math.BigDecimal) 

    /**
     * Method setCashBatchContractFKSets the value of field
     * 'cashBatchContractFK'.
     * 
     * @param cashBatchContractFK the value of field
     * 'cashBatchContractFK'.
     */
    public void setCashBatchContractFK(long cashBatchContractFK)
    {
        this._cashBatchContractFK = cashBatchContractFK;
        
        super.setVoChanged(true);
        this._has_cashBatchContractFK = true;
    } //-- void setCashBatchContractFK(long) 

    /**
     * Method setCostBasisSets the value of field 'costBasis'.
     * 
     * @param costBasis the value of field 'costBasis'.
     */
    public void setCostBasis(java.math.BigDecimal costBasis)
    {
        this._costBasis = costBasis;
        
        super.setVoChanged(true);
    } //-- void setCostBasis(java.math.BigDecimal) 

    /**
     * Method setDateReceivedSets the value of field
     * 'dateReceived'.
     * 
     * @param dateReceived the value of field 'dateReceived'.
     */
    public void setDateReceived(java.lang.String dateReceived)
    {
        this._dateReceived = dateReceived;
        
        super.setVoChanged(true);
    } //-- void setDateReceived(java.lang.String) 

    /**
     * Method setDepositTypeCTSets the value of field
     * 'depositTypeCT'.
     * 
     * @param depositTypeCT the value of field 'depositTypeCT'.
     */
    public void setDepositTypeCT(java.lang.String depositTypeCT)
    {
        this._depositTypeCT = depositTypeCT;
        
        super.setVoChanged(true);
    } //-- void setDepositTypeCT(java.lang.String) 

    /**
     * Method setDepositsPKSets the value of field 'depositsPK'.
     * 
     * @param depositsPK the value of field 'depositsPK'.
     */
    public void setDepositsPK(long depositsPK)
    {
        this._depositsPK = depositsPK;
        
        super.setVoChanged(true);
        this._has_depositsPK = true;
    } //-- void setDepositsPK(long) 

    /**
     * Method setEDITTrxFKSets the value of field 'EDITTrxFK'.
     * 
     * @param EDITTrxFK the value of field 'EDITTrxFK'.
     */
    public void setEDITTrxFK(long EDITTrxFK)
    {
        this._EDITTrxFK = EDITTrxFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxFK = true;
    } //-- void setEDITTrxFK(long) 

    /**
     * Method setExchangeDurationSets the value of field
     * 'exchangeDuration'.
     * 
     * @param exchangeDuration the value of field 'exchangeDuration'
     */
    public void setExchangeDuration(int exchangeDuration)
    {
        this._exchangeDuration = exchangeDuration;
        
        super.setVoChanged(true);
        this._has_exchangeDuration = true;
    } //-- void setExchangeDuration(int) 

    /**
     * Method setExchangeIssueAgeSets the value of field
     * 'exchangeIssueAge'.
     * 
     * @param exchangeIssueAge the value of field 'exchangeIssueAge'
     */
    public void setExchangeIssueAge(int exchangeIssueAge)
    {
        this._exchangeIssueAge = exchangeIssueAge;
        
        super.setVoChanged(true);
        this._has_exchangeIssueAge = true;
    } //-- void setExchangeIssueAge(int) 

    /**
     * Method setExchangeLoanAmountSets the value of field
     * 'exchangeLoanAmount'.
     * 
     * @param exchangeLoanAmount the value of field
     * 'exchangeLoanAmount'.
     */
    public void setExchangeLoanAmount(java.math.BigDecimal exchangeLoanAmount)
    {
        this._exchangeLoanAmount = exchangeLoanAmount;
        
        super.setVoChanged(true);
    } //-- void setExchangeLoanAmount(java.math.BigDecimal) 

    /**
     * Method setExchangePolicyEffectiveDateSets the value of field
     * 'exchangePolicyEffectiveDate'.
     * 
     * @param exchangePolicyEffectiveDate the value of field
     * 'exchangePolicyEffectiveDate'.
     */
    public void setExchangePolicyEffectiveDate(java.lang.String exchangePolicyEffectiveDate)
    {
        this._exchangePolicyEffectiveDate = exchangePolicyEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setExchangePolicyEffectiveDate(java.lang.String) 

    /**
     * Method setOldCompanySets the value of field 'oldCompany'.
     * 
     * @param oldCompany the value of field 'oldCompany'.
     */
    public void setOldCompany(java.lang.String oldCompany)
    {
        this._oldCompany = oldCompany;
        
        super.setVoChanged(true);
    } //-- void setOldCompany(java.lang.String) 

    /**
     * Method setOldPolicyNumberSets the value of field
     * 'oldPolicyNumber'.
     * 
     * @param oldPolicyNumber the value of field 'oldPolicyNumber'.
     */
    public void setOldPolicyNumber(java.lang.String oldPolicyNumber)
    {
        this._oldPolicyNumber = oldPolicyNumber;
        
        super.setVoChanged(true);
    } //-- void setOldPolicyNumber(java.lang.String) 

    /**
     * Method setPostTEFRAAmountSets the value of field
     * 'postTEFRAAmount'.
     * 
     * @param postTEFRAAmount the value of field 'postTEFRAAmount'.
     */
    public void setPostTEFRAAmount(java.math.BigDecimal postTEFRAAmount)
    {
        this._postTEFRAAmount = postTEFRAAmount;
        
        super.setVoChanged(true);
    } //-- void setPostTEFRAAmount(java.math.BigDecimal) 

    /**
     * Method setPostTEFRAGainSets the value of field
     * 'postTEFRAGain'.
     * 
     * @param postTEFRAGain the value of field 'postTEFRAGain'.
     */
    public void setPostTEFRAGain(java.math.BigDecimal postTEFRAGain)
    {
        this._postTEFRAGain = postTEFRAGain;
        
        super.setVoChanged(true);
    } //-- void setPostTEFRAGain(java.math.BigDecimal) 

    /**
     * Method setPreTEFRAAmountSets the value of field
     * 'preTEFRAAmount'.
     * 
     * @param preTEFRAAmount the value of field 'preTEFRAAmount'.
     */
    public void setPreTEFRAAmount(java.math.BigDecimal preTEFRAAmount)
    {
        this._preTEFRAAmount = preTEFRAAmount;
        
        super.setVoChanged(true);
    } //-- void setPreTEFRAAmount(java.math.BigDecimal) 

    /**
     * Method setPreTEFRAGainSets the value of field
     * 'preTEFRAGain'.
     * 
     * @param preTEFRAGain the value of field 'preTEFRAGain'.
     */
    public void setPreTEFRAGain(java.math.BigDecimal preTEFRAGain)
    {
        this._preTEFRAGain = preTEFRAGain;
        
        super.setVoChanged(true);
    } //-- void setPreTEFRAGain(java.math.BigDecimal) 

    /**
     * Method setPriorCompanyMECStatusCTSets the value of field
     * 'priorCompanyMECStatusCT'.
     * 
     * @param priorCompanyMECStatusCT the value of field
     * 'priorCompanyMECStatusCT'.
     */
    public void setPriorCompanyMECStatusCT(java.lang.String priorCompanyMECStatusCT)
    {
        this._priorCompanyMECStatusCT = priorCompanyMECStatusCT;
        
        super.setVoChanged(true);
    } //-- void setPriorCompanyMECStatusCT(java.lang.String) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method setSuspenseFKSets the value of field 'suspenseFK'.
     * 
     * @param suspenseFK the value of field 'suspenseFK'.
     */
    public void setSuspenseFK(long suspenseFK)
    {
        this._suspenseFK = suspenseFK;
        
        super.setVoChanged(true);
        this._has_suspenseFK = true;
    } //-- void setSuspenseFK(long) 

    /**
     * Method setTaxYearSets the value of field 'taxYear'.
     * 
     * @param taxYear the value of field 'taxYear'.
     */
    public void setTaxYear(int taxYear)
    {
        this._taxYear = taxYear;
        
        super.setVoChanged(true);
        this._has_taxYear = true;
    } //-- void setTaxYear(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.DepositsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.DepositsVO) Unmarshaller.unmarshal(edit.common.vo.DepositsVO.class, reader);
    } //-- edit.common.vo.DepositsVO unmarshal(java.io.Reader) 

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
