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
 * Class AccountingDetailVO.
 * 
 * @version $Revision$ $Date$
 */
public class AccountingDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _accountingDetailPK
     */
    private long _accountingDetailPK;

    /**
     * keeps track of state for field: _accountingDetailPK
     */
    private boolean _has_accountingDetailPK;

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _marketingPackageName
     */
    private java.lang.String _marketingPackageName;

    /**
     * Field _businessContractName
     */
    private java.lang.String _businessContractName;

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _qualNonQualCT
     */
    private java.lang.String _qualNonQualCT;

    /**
     * Field _optionCodeCT
     */
    private java.lang.String _optionCodeCT;

    /**
     * Field _groupNumber
     */
    private java.lang.String _groupNumber;

    /**
     * Field _groupName
     */
    private java.lang.String _groupName;

    /**
     * Field _transactionCode
     */
    private java.lang.String _transactionCode;

    /**
     * Field _reversalInd
     */
    private java.lang.String _reversalInd;

    /**
     * Field _memoCode
     */
    private java.lang.String _memoCode;

    /**
     * Field _stateCodeCT
     */
    private java.lang.String _stateCodeCT;

    /**
     * Field _accountNumber
     */
    private java.lang.String _accountNumber;

    /**
     * Field _accountName
     */
    private java.lang.String _accountName;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;

    /**
     * Field _debitCreditInd
     */
    private java.lang.String _debitCreditInd;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _processDate
     */
    private java.lang.String _processDate;

    /**
     * Field _fundNumber
     */
    private java.lang.String _fundNumber;

    /**
     * Field _outOfBalanceInd
     */
    private java.lang.String _outOfBalanceInd;

    /**
     * Field _accountingPendingStatus
     */
    private java.lang.String _accountingPendingStatus;

    /**
     * Field _qualifiedTypeCT
     */
    private java.lang.String _qualifiedTypeCT;

    /**
     * Field _originalContractNumber
     */
    private java.lang.String _originalContractNumber;

    /**
     * Field _accountingProcessDate
     */
    private java.lang.String _accountingProcessDate;

    /**
     * Field _accountingPeriod
     */
    private java.lang.String _accountingPeriod;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _chargeCode
     */
    private java.lang.String _chargeCode;

    /**
     * Field _distributionCodeCT
     */
    private java.lang.String _distributionCodeCT;

    /**
     * Field _reinsurerNumber
     */
    private java.lang.String _reinsurerNumber;

    /**
     * Field _treatyGroupNumber
     */
    private java.lang.String _treatyGroupNumber;

    /**
     * Field _comments
     */
    private java.lang.String _comments;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _certainDuration
     */
    private int _certainDuration;

    /**
     * keeps track of state for field: _certainDuration
     */
    private boolean _has_certainDuration;

    /**
     * Field _batchAmount
     */
    private java.math.BigDecimal _batchAmount;

    /**
     * Field _batchControl
     */
    private java.lang.String _batchControl;

    /**
     * Field _voucherSource
     */
    private java.lang.String _voucherSource;

    /**
     * Field _description
     */
    private java.lang.String _description;

    /**
     * Field _agentCode
     */
    private java.lang.String _agentCode;

    /**
     * Field _source
     */
    private java.lang.String _source;

    /**
     * Field _contractClientFK
     */
    private long _contractClientFK;

    /**
     * keeps track of state for field: _contractClientFK
     */
    private boolean _has_contractClientFK;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public AccountingDetailVO() {
        super();
    } //-- edit.common.vo.AccountingDetailVO()


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
        
        if (obj instanceof AccountingDetailVO) {
        
            AccountingDetailVO temp = (AccountingDetailVO)obj;
            if (this._accountingDetailPK != temp._accountingDetailPK)
                return false;
            if (this._has_accountingDetailPK != temp._has_accountingDetailPK)
                return false;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._marketingPackageName != null) {
                if (temp._marketingPackageName == null) return false;
                else if (!(this._marketingPackageName.equals(temp._marketingPackageName))) 
                    return false;
            }
            else if (temp._marketingPackageName != null)
                return false;
            if (this._businessContractName != null) {
                if (temp._businessContractName == null) return false;
                else if (!(this._businessContractName.equals(temp._businessContractName))) 
                    return false;
            }
            else if (temp._businessContractName != null)
                return false;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._qualNonQualCT != null) {
                if (temp._qualNonQualCT == null) return false;
                else if (!(this._qualNonQualCT.equals(temp._qualNonQualCT))) 
                    return false;
            }
            else if (temp._qualNonQualCT != null)
                return false;
            if (this._optionCodeCT != null) {
                if (temp._optionCodeCT == null) return false;
                else if (!(this._optionCodeCT.equals(temp._optionCodeCT))) 
                    return false;
            }
            else if (temp._optionCodeCT != null)
                return false;
            if (this._transactionCode != null) {
                if (temp._transactionCode == null) return false;
                else if (!(this._transactionCode.equals(temp._transactionCode))) 
                    return false;
            }
            else if (temp._groupNumber != null)
                return false;
            if (this._groupNumber != null) {
                if (temp._groupNumber == null) return false;
                else if (!(this._groupNumber.equals(temp._groupNumber))) 
                    return false;
            }
            else if (temp._groupName != null)
                return false;
            if (this._groupName != null) {
                if (temp._groupName == null) return false;
                else if (!(this._groupName.equals(temp._groupName))) 
                    return false;
            }
            else if (temp._transactionCode != null)
                return false;
            if (this._reversalInd != null) {
                if (temp._reversalInd == null) return false;
                else if (!(this._reversalInd.equals(temp._reversalInd))) 
                    return false;
            }
            else if (temp._reversalInd != null)
                return false;
            if (this._memoCode != null) {
                if (temp._memoCode == null) return false;
                else if (!(this._memoCode.equals(temp._memoCode))) 
                    return false;
            }
            else if (temp._memoCode != null)
                return false;
            if (this._stateCodeCT != null) {
                if (temp._stateCodeCT == null) return false;
                else if (!(this._stateCodeCT.equals(temp._stateCodeCT))) 
                    return false;
            }
            else if (temp._stateCodeCT != null)
                return false;
            if (this._accountNumber != null) {
                if (temp._accountNumber == null) return false;
                else if (!(this._accountNumber.equals(temp._accountNumber))) 
                    return false;
            }
            else if (temp._accountNumber != null)
                return false;
            if (this._accountName != null) {
                if (temp._accountName == null) return false;
                else if (!(this._accountName.equals(temp._accountName))) 
                    return false;
            }
            else if (temp._accountName != null)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            if (this._debitCreditInd != null) {
                if (temp._debitCreditInd == null) return false;
                else if (!(this._debitCreditInd.equals(temp._debitCreditInd))) 
                    return false;
            }
            else if (temp._debitCreditInd != null)
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
            if (this._fundNumber != null) {
                if (temp._fundNumber == null) return false;
                else if (!(this._fundNumber.equals(temp._fundNumber))) 
                    return false;
            }
            else if (temp._fundNumber != null)
                return false;
            if (this._outOfBalanceInd != null) {
                if (temp._outOfBalanceInd == null) return false;
                else if (!(this._outOfBalanceInd.equals(temp._outOfBalanceInd))) 
                    return false;
            }
            else if (temp._outOfBalanceInd != null)
                return false;
            if (this._accountingPendingStatus != null) {
                if (temp._accountingPendingStatus == null) return false;
                else if (!(this._accountingPendingStatus.equals(temp._accountingPendingStatus))) 
                    return false;
            }
            else if (temp._accountingPendingStatus != null)
                return false;
            if (this._qualifiedTypeCT != null) {
                if (temp._qualifiedTypeCT == null) return false;
                else if (!(this._qualifiedTypeCT.equals(temp._qualifiedTypeCT))) 
                    return false;
            }
            else if (temp._qualifiedTypeCT != null)
                return false;
            if (this._originalContractNumber != null) {
                if (temp._originalContractNumber == null) return false;
                else if (!(this._originalContractNumber.equals(temp._originalContractNumber))) 
                    return false;
            }
            else if (temp._originalContractNumber != null)
                return false;
            if (this._accountingProcessDate != null) {
                if (temp._accountingProcessDate == null) return false;
                else if (!(this._accountingProcessDate.equals(temp._accountingProcessDate))) 
                    return false;
            }
            else if (temp._accountingProcessDate != null)
                return false;
            if (this._accountingPeriod != null) {
                if (temp._accountingPeriod == null) return false;
                else if (!(this._accountingPeriod.equals(temp._accountingPeriod))) 
                    return false;
            }
            else if (temp._accountingPeriod != null)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._chargeCode != null) {
                if (temp._chargeCode == null) return false;
                else if (!(this._chargeCode.equals(temp._chargeCode))) 
                    return false;
            }
            else if (temp._chargeCode != null)
                return false;
            if (this._distributionCodeCT != null) {
                if (temp._distributionCodeCT == null) return false;
                else if (!(this._distributionCodeCT.equals(temp._distributionCodeCT))) 
                    return false;
            }
            else if (temp._distributionCodeCT != null)
                return false;
            if (this._reinsurerNumber != null) {
                if (temp._reinsurerNumber == null) return false;
                else if (!(this._reinsurerNumber.equals(temp._reinsurerNumber))) 
                    return false;
            }
            else if (temp._reinsurerNumber != null)
                return false;
            if (this._treatyGroupNumber != null) {
                if (temp._treatyGroupNumber == null) return false;
                else if (!(this._treatyGroupNumber.equals(temp._treatyGroupNumber))) 
                    return false;
            }
            else if (temp._treatyGroupNumber != null)
                return false;
            if (this._comments != null) {
                if (temp._comments == null) return false;
                else if (!(this._comments.equals(temp._comments))) 
                    return false;
            }
            else if (temp._comments != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._certainDuration != temp._certainDuration)
                return false;
            if (this._has_certainDuration != temp._has_certainDuration)
                return false;
            if (this._batchAmount != null) {
                if (temp._batchAmount == null) return false;
                else if (!(this._batchAmount.equals(temp._batchAmount))) 
                    return false;
            }
            else if (temp._batchAmount != null)
                return false;
            if (this._batchControl != null) {
                if (temp._batchControl == null) return false;
                else if (!(this._batchControl.equals(temp._batchControl))) 
                    return false;
            }
            else if (temp._batchControl != null)
                return false;
            if (this._voucherSource != null) {
                if (temp._voucherSource == null) return false;
                else if (!(this._voucherSource.equals(temp._voucherSource))) 
                    return false;
            }
            else if (temp._voucherSource != null)
                return false;
            if (this._description != null) {
                if (temp._description == null) return false;
                else if (!(this._description.equals(temp._description))) 
                    return false;
            }
            else if (temp._description != null)
                return false;
            if (this._agentCode != null) {
                if (temp._agentCode == null) return false;
                else if (!(this._agentCode.equals(temp._agentCode))) 
                    return false;
            }
            else if (temp._agentCode != null)
                return false;
            if (this._source != null) {
                if (temp._source == null) return false;
                else if (!(this._source.equals(temp._source))) 
                    return false;
            }
            else if (temp._source != null)
                return false;
            if (this._contractClientFK != temp._contractClientFK)
                return false;
            if (this._has_contractClientFK != temp._has_contractClientFK)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountNameReturns the value of field
     * 'accountName'.
     * 
     * @return the value of field 'accountName'.
     */
    public java.lang.String getAccountName()
    {
        return this._accountName;
    } //-- java.lang.String getAccountName() 

    /**
     * Method getAccountNumberReturns the value of field
     * 'accountNumber'.
     * 
     * @return the value of field 'accountNumber'.
     */
    public java.lang.String getAccountNumber()
    {
        return this._accountNumber;
    } //-- java.lang.String getAccountNumber() 

    /**
     * Method getAccountingDetailPKReturns the value of field
     * 'accountingDetailPK'.
     * 
     * @return the value of field 'accountingDetailPK'.
     */
    public long getAccountingDetailPK()
    {
        return this._accountingDetailPK;
    } //-- long getAccountingDetailPK() 

    /**
     * Method getAccountingPendingStatusReturns the value of field
     * 'accountingPendingStatus'.
     * 
     * @return the value of field 'accountingPendingStatus'.
     */
    public java.lang.String getAccountingPendingStatus()
    {
        return this._accountingPendingStatus;
    } //-- java.lang.String getAccountingPendingStatus() 

    /**
     * Method getAccountingPeriodReturns the value of field
     * 'accountingPeriod'.
     * 
     * @return the value of field 'accountingPeriod'.
     */
    public java.lang.String getAccountingPeriod()
    {
        return this._accountingPeriod;
    } //-- java.lang.String getAccountingPeriod() 

    /**
     * Method getAccountingProcessDateReturns the value of field
     * 'accountingProcessDate'.
     * 
     * @return the value of field 'accountingProcessDate'.
     */
    public java.lang.String getAccountingProcessDate()
    {
        return this._accountingProcessDate;
    } //-- java.lang.String getAccountingProcessDate() 

    /**
     * Method getAgentCodeReturns the value of field 'agentCode'.
     * 
     * @return the value of field 'agentCode'.
     */
    public java.lang.String getAgentCode()
    {
        return this._agentCode;
    } //-- java.lang.String getAgentCode() 

    /**
     * Method getAmountReturns the value of field 'amount'.
     * 
     * @return the value of field 'amount'.
     */
    public java.math.BigDecimal getAmount()
    {
        return this._amount;
    } //-- java.math.BigDecimal getAmount() 

    /**
     * Method getBatchAmountReturns the value of field
     * 'batchAmount'.
     * 
     * @return the value of field 'batchAmount'.
     */
    public java.math.BigDecimal getBatchAmount()
    {
        return this._batchAmount;
    } //-- java.math.BigDecimal getBatchAmount() 

    /**
     * Method getBatchControlReturns the value of field
     * 'batchControl'.
     * 
     * @return the value of field 'batchControl'.
     */
    public java.lang.String getBatchControl()
    {
        return this._batchControl;
    } //-- java.lang.String getBatchControl() 

    /**
     * Method getBusinessContractNameReturns the value of field
     * 'businessContractName'.
     * 
     * @return the value of field 'businessContractName'.
     */
    public java.lang.String getBusinessContractName()
    {
        return this._businessContractName;
    } //-- java.lang.String getBusinessContractName() 

    /**
     * Method getCertainDurationReturns the value of field
     * 'certainDuration'.
     * 
     * @return the value of field 'certainDuration'.
     */
    public int getCertainDuration()
    {
        return this._certainDuration;
    } //-- int getCertainDuration() 

    /**
     * Method getChargeCodeReturns the value of field 'chargeCode'.
     * 
     * @return the value of field 'chargeCode'.
     */
    public java.lang.String getChargeCode()
    {
        return this._chargeCode;
    } //-- java.lang.String getChargeCode() 

    /**
     * Method getCommentsReturns the value of field 'comments'.
     * 
     * @return the value of field 'comments'.
     */
    public java.lang.String getComments()
    {
        return this._comments;
    } //-- java.lang.String getComments() 

    /**
     * Method getCompanyNameReturns the value of field
     * 'companyName'.
     * 
     * @return the value of field 'companyName'.
     */
    public java.lang.String getCompanyName()
    {
        return this._companyName;
    } //-- java.lang.String getCompanyName() 

    /**
     * Method getContractClientFKReturns the value of field
     * 'contractClientFK'.
     * 
     * @return the value of field 'contractClientFK'.
     */
    public long getContractClientFK()
    {
        return this._contractClientFK;
    } //-- long getContractClientFK() 

    /**
     * Method getContractNumberReturns the value of field
     * 'contractNumber'.
     * 
     * @return the value of field 'contractNumber'.
     */
    public java.lang.String getContractNumber()
    {
        return this._contractNumber;
    } //-- java.lang.String getContractNumber() 

    /**
     * Method getDebitCreditIndReturns the value of field
     * 'debitCreditInd'.
     * 
     * @return the value of field 'debitCreditInd'.
     */
    public java.lang.String getDebitCreditInd()
    {
        return this._debitCreditInd;
    } //-- java.lang.String getDebitCreditInd() 

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
     * Method getDistributionCodeCTReturns the value of field
     * 'distributionCodeCT'.
     * 
     * @return the value of field 'distributionCodeCT'.
     */
    public java.lang.String getDistributionCodeCT()
    {
        return this._distributionCodeCT;
    } //-- java.lang.String getDistributionCodeCT() 

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
     * Method getFundNumberReturns the value of field 'fundNumber'.
     * 
     * @return the value of field 'fundNumber'.
     */
    public java.lang.String getFundNumber()
    {
        return this._fundNumber;
    } //-- java.lang.String getFundNumber() 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

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
     * Method getMemoCodeReturns the value of field 'memoCode'.
     * 
     * @return the value of field 'memoCode'.
     */
    public java.lang.String getMemoCode()
    {
        return this._memoCode;
    } //-- java.lang.String getMemoCode() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getOptionCodeCTReturns the value of field
     * 'optionCodeCT'.
     * 
     * @return the value of field 'optionCodeCT'.
     */
    public java.lang.String getOptionCodeCT()
    {
        return this._optionCodeCT;
    } //-- java.lang.String getOptionCodeCT() 

    /**
     * Method getGroupNumber returns the value of field
     * '_groupNumber'.
     * 
     * @return the value of field '_groupNumber'.
     */
    public java.lang.String getGroupNumber()
    {
        return this._groupNumber;
    } //-- java.lang.String getGroupNumber() 

    /**
     * Method getGroupName returns the value of field
     * '_groupName'.
     * 
     * @return the value of field '_groupName'.
     */
    public java.lang.String getGroupName()
    {
        return this._groupName;
    } //-- java.lang.String getGroupName() 

    /**
     * Method getOriginalContractNumberReturns the value of field
     * 'originalContractNumber'.
     * 
     * @return the value of field 'originalContractNumber'.
     */
    public java.lang.String getOriginalContractNumber()
    {
        return this._originalContractNumber;
    } //-- java.lang.String getOriginalContractNumber() 

    /**
     * Method getOutOfBalanceIndReturns the value of field
     * 'outOfBalanceInd'.
     * 
     * @return the value of field 'outOfBalanceInd'.
     */
    public java.lang.String getOutOfBalanceInd()
    {
        return this._outOfBalanceInd;
    } //-- java.lang.String getOutOfBalanceInd() 

    /**
     * Method getPlacedAgentFKReturns the value of field
     * 'placedAgentFK'.
     * 
     * @return the value of field 'placedAgentFK'.
     */
    public long getPlacedAgentFK()
    {
        return this._placedAgentFK;
    } //-- long getPlacedAgentFK() 

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
     * Method getQualNonQualCTReturns the value of field
     * 'qualNonQualCT'.
     * 
     * @return the value of field 'qualNonQualCT'.
     */
    public java.lang.String getQualNonQualCT()
    {
        return this._qualNonQualCT;
    } //-- java.lang.String getQualNonQualCT() 

    /**
     * Method getQualifiedTypeCTReturns the value of field
     * 'qualifiedTypeCT'.
     * 
     * @return the value of field 'qualifiedTypeCT'.
     */
    public java.lang.String getQualifiedTypeCT()
    {
        return this._qualifiedTypeCT;
    } //-- java.lang.String getQualifiedTypeCT() 

    /**
     * Method getReinsurerNumberReturns the value of field
     * 'reinsurerNumber'.
     * 
     * @return the value of field 'reinsurerNumber'.
     */
    public java.lang.String getReinsurerNumber()
    {
        return this._reinsurerNumber;
    } //-- java.lang.String getReinsurerNumber() 

    /**
     * Method getReversalIndReturns the value of field
     * 'reversalInd'.
     * 
     * @return the value of field 'reversalInd'.
     */
    public java.lang.String getReversalInd()
    {
        return this._reversalInd;
    } //-- java.lang.String getReversalInd() 

    /**
     * Method getSourceReturns the value of field 'source'.
     * 
     * @return the value of field 'source'.
     */
    public java.lang.String getSource()
    {
        return this._source;
    } //-- java.lang.String getSource() 

    /**
     * Method getStateCodeCTReturns the value of field
     * 'stateCodeCT'.
     * 
     * @return the value of field 'stateCodeCT'.
     */
    public java.lang.String getStateCodeCT()
    {
        return this._stateCodeCT;
    } //-- java.lang.String getStateCodeCT() 

    /**
     * Method getTransactionCodeReturns the value of field
     * 'transactionCode'.
     * 
     * @return the value of field 'transactionCode'.
     */
    public java.lang.String getTransactionCode()
    {
        return this._transactionCode;
    } //-- java.lang.String getTransactionCode() 

    /**
     * Method getTreatyGroupNumberReturns the value of field
     * 'treatyGroupNumber'.
     * 
     * @return the value of field 'treatyGroupNumber'.
     */
    public java.lang.String getTreatyGroupNumber()
    {
        return this._treatyGroupNumber;
    } //-- java.lang.String getTreatyGroupNumber() 

    /**
     * Method getVoucherSourceReturns the value of field
     * 'voucherSource'.
     * 
     * @return the value of field 'voucherSource'.
     */
    public java.lang.String getVoucherSource()
    {
        return this._voucherSource;
    } //-- java.lang.String getVoucherSource() 

    /**
     * Method hasAccountingDetailPK
     */
    public boolean hasAccountingDetailPK()
    {
        return this._has_accountingDetailPK;
    } //-- boolean hasAccountingDetailPK() 

    /**
     * Method hasCertainDuration
     */
    public boolean hasCertainDuration()
    {
        return this._has_certainDuration;
    } //-- boolean hasCertainDuration() 

    /**
     * Method hasContractClientFK
     */
    public boolean hasContractClientFK()
    {
        return this._has_contractClientFK;
    } //-- boolean hasContractClientFK() 

    /**
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

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
     * Method setAccountNameSets the value of field 'accountName'.
     * 
     * @param accountName the value of field 'accountName'.
     */
    public void setAccountName(java.lang.String accountName)
    {
        this._accountName = accountName;
        
        super.setVoChanged(true);
    } //-- void setAccountName(java.lang.String) 

    /**
     * Method setAccountNumberSets the value of field
     * 'accountNumber'.
     * 
     * @param accountNumber the value of field 'accountNumber'.
     */
    public void setAccountNumber(java.lang.String accountNumber)
    {
        this._accountNumber = accountNumber;
        
        super.setVoChanged(true);
    } //-- void setAccountNumber(java.lang.String) 

    /**
     * Method setAccountingDetailPKSets the value of field
     * 'accountingDetailPK'.
     * 
     * @param accountingDetailPK the value of field
     * 'accountingDetailPK'.
     */
    public void setAccountingDetailPK(long accountingDetailPK)
    {
        this._accountingDetailPK = accountingDetailPK;
        
        super.setVoChanged(true);
        this._has_accountingDetailPK = true;
    } //-- void setAccountingDetailPK(long) 

    /**
     * Method setAccountingPendingStatusSets the value of field
     * 'accountingPendingStatus'.
     * 
     * @param accountingPendingStatus the value of field
     * 'accountingPendingStatus'.
     */
    public void setAccountingPendingStatus(java.lang.String accountingPendingStatus)
    {
        this._accountingPendingStatus = accountingPendingStatus;
        
        super.setVoChanged(true);
    } //-- void setAccountingPendingStatus(java.lang.String) 

    /**
     * Method setAccountingPeriodSets the value of field
     * 'accountingPeriod'.
     * 
     * @param accountingPeriod the value of field 'accountingPeriod'
     */
    public void setAccountingPeriod(java.lang.String accountingPeriod)
    {
        this._accountingPeriod = accountingPeriod;
        
        super.setVoChanged(true);
    } //-- void setAccountingPeriod(java.lang.String) 

    /**
     * Method setAccountingProcessDateSets the value of field
     * 'accountingProcessDate'.
     * 
     * @param accountingProcessDate the value of field
     * 'accountingProcessDate'.
     */
    public void setAccountingProcessDate(java.lang.String accountingProcessDate)
    {
        this._accountingProcessDate = accountingProcessDate;
        
        super.setVoChanged(true);
    } //-- void setAccountingProcessDate(java.lang.String) 

    /**
     * Method setAgentCodeSets the value of field 'agentCode'.
     * 
     * @param agentCode the value of field 'agentCode'.
     */
    public void setAgentCode(java.lang.String agentCode)
    {
        this._agentCode = agentCode;
        
        super.setVoChanged(true);
    } //-- void setAgentCode(java.lang.String) 

    /**
     * Method setAmountSets the value of field 'amount'.
     * 
     * @param amount the value of field 'amount'.
     */
    public void setAmount(java.math.BigDecimal amount)
    {
        this._amount = amount;
        
        super.setVoChanged(true);
    } //-- void setAmount(java.math.BigDecimal) 

    /**
     * Method setBatchAmountSets the value of field 'batchAmount'.
     * 
     * @param batchAmount the value of field 'batchAmount'.
     */
    public void setBatchAmount(java.math.BigDecimal batchAmount)
    {
        this._batchAmount = batchAmount;
        
        super.setVoChanged(true);
    } //-- void setBatchAmount(java.math.BigDecimal) 

    /**
     * Method setBatchControlSets the value of field
     * 'batchControl'.
     * 
     * @param batchControl the value of field 'batchControl'.
     */
    public void setBatchControl(java.lang.String batchControl)
    {
        this._batchControl = batchControl;
        
        super.setVoChanged(true);
    } //-- void setBatchControl(java.lang.String) 

    /**
     * Method setBusinessContractNameSets the value of field
     * 'businessContractName'.
     * 
     * @param businessContractName the value of field
     * 'businessContractName'.
     */
    public void setBusinessContractName(java.lang.String businessContractName)
    {
        this._businessContractName = businessContractName;
        
        super.setVoChanged(true);
    } //-- void setBusinessContractName(java.lang.String) 

    /**
     * Method setCertainDurationSets the value of field
     * 'certainDuration'.
     * 
     * @param certainDuration the value of field 'certainDuration'.
     */
    public void setCertainDuration(int certainDuration)
    {
        this._certainDuration = certainDuration;
        
        super.setVoChanged(true);
        this._has_certainDuration = true;
    } //-- void setCertainDuration(int) 

    /**
     * Method setChargeCodeSets the value of field 'chargeCode'.
     * 
     * @param chargeCode the value of field 'chargeCode'.
     */
    public void setChargeCode(java.lang.String chargeCode)
    {
        this._chargeCode = chargeCode;
        
        super.setVoChanged(true);
    } //-- void setChargeCode(java.lang.String) 

    /**
     * Method setCommentsSets the value of field 'comments'.
     * 
     * @param comments the value of field 'comments'.
     */
    public void setComments(java.lang.String comments)
    {
        this._comments = comments;
        
        super.setVoChanged(true);
    } //-- void setComments(java.lang.String) 

    /**
     * Method setCompanyNameSets the value of field 'companyName'.
     * 
     * @param companyName the value of field 'companyName'.
     */
    public void setCompanyName(java.lang.String companyName)
    {
        this._companyName = companyName;
        
        super.setVoChanged(true);
    } //-- void setCompanyName(java.lang.String) 

    /**
     * Method setContractClientFKSets the value of field
     * 'contractClientFK'.
     * 
     * @param contractClientFK the value of field 'contractClientFK'
     */
    public void setContractClientFK(long contractClientFK)
    {
        this._contractClientFK = contractClientFK;
        
        super.setVoChanged(true);
        this._has_contractClientFK = true;
    } //-- void setContractClientFK(long) 

    /**
     * Method setContractNumberSets the value of field
     * 'contractNumber'.
     * 
     * @param contractNumber the value of field 'contractNumber'.
     */
    public void setContractNumber(java.lang.String contractNumber)
    {
        this._contractNumber = contractNumber;
        
        super.setVoChanged(true);
    } //-- void setContractNumber(java.lang.String) 

    /**
     * Method setDebitCreditIndSets the value of field
     * 'debitCreditInd'.
     * 
     * @param debitCreditInd the value of field 'debitCreditInd'.
     */
    public void setDebitCreditInd(java.lang.String debitCreditInd)
    {
        this._debitCreditInd = debitCreditInd;
        
        super.setVoChanged(true);
    } //-- void setDebitCreditInd(java.lang.String) 

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
     * Method setDistributionCodeCTSets the value of field
     * 'distributionCodeCT'.
     * 
     * @param distributionCodeCT the value of field
     * 'distributionCodeCT'.
     */
    public void setDistributionCodeCT(java.lang.String distributionCodeCT)
    {
        this._distributionCodeCT = distributionCodeCT;
        
        super.setVoChanged(true);
    } //-- void setDistributionCodeCT(java.lang.String) 

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
     * Method setFundNumberSets the value of field 'fundNumber'.
     * 
     * @param fundNumber the value of field 'fundNumber'.
     */
    public void setFundNumber(java.lang.String fundNumber)
    {
        this._fundNumber = fundNumber;
        
        super.setVoChanged(true);
    } //-- void setFundNumber(java.lang.String) 

    /**
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

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
     * Method setMemoCodeSets the value of field 'memoCode'.
     * 
     * @param memoCode the value of field 'memoCode'.
     */
    public void setMemoCode(java.lang.String memoCode)
    {
        this._memoCode = memoCode;
        
        super.setVoChanged(true);
    } //-- void setMemoCode(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

    /**
     * Method setOptionCodeCTSets the value of field
     * 'optionCodeCT'.
     * 
     * @param optionCodeCT the value of field 'optionCodeCT'.
     */
    public void setOptionCodeCT(java.lang.String optionCodeCT)
    {
        this._optionCodeCT = optionCodeCT;
        
        super.setVoChanged(true);
    } //-- void setOptionCodeCT(java.lang.String) 

    /**
     * Method setGroupNumber sets the value of field
     * 'groupNumber'.
     * 
     * @param groupNumber the value of field 'groupNumber'.
     */
    public void setGroupNumber(java.lang.String groupNumber)
    {
        this._groupNumber = groupNumber;
        
        super.setVoChanged(true);
    } //-- void setGroupNumber(java.lang.String) 

    /**
     * Method setGroupName sets the value of field
     * 'groupName'.
     * 
     * @param groupName the value of field 'groupName'.
     */
    public void setGroupName(java.lang.String groupName)
    {
        this._groupName = groupName;
        
        super.setVoChanged(true);
    } //-- void setGroupName(java.lang.String) 

    /**
     * Method setOriginalContractNumberSets the value of field
     * 'originalContractNumber'.
     * 
     * @param originalContractNumber the value of field
     * 'originalContractNumber'.
     */
    public void setOriginalContractNumber(java.lang.String originalContractNumber)
    {
        this._originalContractNumber = originalContractNumber;
        
        super.setVoChanged(true);
    } //-- void setOriginalContractNumber(java.lang.String) 

    /**
     * Method setOutOfBalanceIndSets the value of field
     * 'outOfBalanceInd'.
     * 
     * @param outOfBalanceInd the value of field 'outOfBalanceInd'.
     */
    public void setOutOfBalanceInd(java.lang.String outOfBalanceInd)
    {
        this._outOfBalanceInd = outOfBalanceInd;
        
        super.setVoChanged(true);
    } //-- void setOutOfBalanceInd(java.lang.String) 

    /**
     * Method setPlacedAgentFKSets the value of field
     * 'placedAgentFK'.
     * 
     * @param placedAgentFK the value of field 'placedAgentFK'.
     */
    public void setPlacedAgentFK(long placedAgentFK)
    {
        this._placedAgentFK = placedAgentFK;
        
        super.setVoChanged(true);
        this._has_placedAgentFK = true;
    } //-- void setPlacedAgentFK(long) 

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
     * Method setQualNonQualCTSets the value of field
     * 'qualNonQualCT'.
     * 
     * @param qualNonQualCT the value of field 'qualNonQualCT'.
     */
    public void setQualNonQualCT(java.lang.String qualNonQualCT)
    {
        this._qualNonQualCT = qualNonQualCT;
        
        super.setVoChanged(true);
    } //-- void setQualNonQualCT(java.lang.String) 

    /**
     * Method setQualifiedTypeCTSets the value of field
     * 'qualifiedTypeCT'.
     * 
     * @param qualifiedTypeCT the value of field 'qualifiedTypeCT'.
     */
    public void setQualifiedTypeCT(java.lang.String qualifiedTypeCT)
    {
        this._qualifiedTypeCT = qualifiedTypeCT;
        
        super.setVoChanged(true);
    } //-- void setQualifiedTypeCT(java.lang.String) 

    /**
     * Method setReinsurerNumberSets the value of field
     * 'reinsurerNumber'.
     * 
     * @param reinsurerNumber the value of field 'reinsurerNumber'.
     */
    public void setReinsurerNumber(java.lang.String reinsurerNumber)
    {
        this._reinsurerNumber = reinsurerNumber;
        
        super.setVoChanged(true);
    } //-- void setReinsurerNumber(java.lang.String) 

    /**
     * Method setReversalIndSets the value of field 'reversalInd'.
     * 
     * @param reversalInd the value of field 'reversalInd'.
     */
    public void setReversalInd(java.lang.String reversalInd)
    {
        this._reversalInd = reversalInd;
        
        super.setVoChanged(true);
    } //-- void setReversalInd(java.lang.String) 

    /**
     * Method setSourceSets the value of field 'source'.
     * 
     * @param source the value of field 'source'.
     */
    public void setSource(java.lang.String source)
    {
        this._source = source;
        
        super.setVoChanged(true);
    } //-- void setSource(java.lang.String) 

    /**
     * Method setStateCodeCTSets the value of field 'stateCodeCT'.
     * 
     * @param stateCodeCT the value of field 'stateCodeCT'.
     */
    public void setStateCodeCT(java.lang.String stateCodeCT)
    {
        this._stateCodeCT = stateCodeCT;
        
        super.setVoChanged(true);
    } //-- void setStateCodeCT(java.lang.String) 

    /**
     * Method setTransactionCodeSets the value of field
     * 'transactionCode'.
     * 
     * @param transactionCode the value of field 'transactionCode'.
     */
    public void setTransactionCode(java.lang.String transactionCode)
    {
        this._transactionCode = transactionCode;
        
        super.setVoChanged(true);
    } //-- void setTransactionCode(java.lang.String) 

    /**
     * Method setTreatyGroupNumberSets the value of field
     * 'treatyGroupNumber'.
     * 
     * @param treatyGroupNumber the value of field
     * 'treatyGroupNumber'.
     */
    public void setTreatyGroupNumber(java.lang.String treatyGroupNumber)
    {
        this._treatyGroupNumber = treatyGroupNumber;
        
        super.setVoChanged(true);
    } //-- void setTreatyGroupNumber(java.lang.String) 

    /**
     * Method setVoucherSourceSets the value of field
     * 'voucherSource'.
     * 
     * @param voucherSource the value of field 'voucherSource'.
     */
    public void setVoucherSource(java.lang.String voucherSource)
    {
        this._voucherSource = voucherSource;
        
        super.setVoChanged(true);
    } //-- void setVoucherSource(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AccountingDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AccountingDetailVO) Unmarshaller.unmarshal(edit.common.vo.AccountingDetailVO.class, reader);
    } //-- edit.common.vo.AccountingDetailVO unmarshal(java.io.Reader) 

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
