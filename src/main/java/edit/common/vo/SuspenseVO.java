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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class SuspenseVO.
 * 
 * @version $Revision$ $Date$
 */
public class SuspenseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _suspensePK
     */
    private long _suspensePK;

    /**
     * keeps track of state for field: _suspensePK
     */
    private boolean _has_suspensePK;

    /**
     * Field _userDefNumber
     */
    private java.lang.String _userDefNumber;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _suspenseAmount
     */
    private java.math.BigDecimal _suspenseAmount;

    /**
     * Field _memoCode
     */
    private java.lang.String _memoCode;

    /**
     * Field _originalContractNumber
     */
    private java.lang.String _originalContractNumber;

    /**
     * Field _originalAmount
     */
    private java.math.BigDecimal _originalAmount;

    /**
     * Field _originalMemoCode
     */
    private java.lang.String _originalMemoCode;

    /**
     * Field _pendingSuspenseAmount
     */
    private java.math.BigDecimal _pendingSuspenseAmount;

    /**
     * Field _accountingPendingInd
     */
    private java.lang.String _accountingPendingInd;

    /**
     * Field _maintenanceInd
     */
    private java.lang.String _maintenanceInd;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _processDate
     */
    private java.lang.String _processDate;

    /**
     * Field _directionCT
     */
    private java.lang.String _directionCT;

    /**
     * Field _premiumTypeCT
     */
    private java.lang.String _premiumTypeCT;

    /**
     * Field _suspenseType
     */
    private java.lang.String _suspenseType;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _taxYear
     */
    private int _taxYear;

    /**
     * keeps track of state for field: _taxYear
     */
    private boolean _has_taxYear;

    /**
     * Field _contractPlacedFrom
     */
    private java.lang.String _contractPlacedFrom;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

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
     * Field _cashBatchContractFK
     */
    private long _cashBatchContractFK;

    /**
     * keeps track of state for field: _cashBatchContractFK
     */
    private boolean _has_cashBatchContractFK;

    /**
     * Field _grossAmount
     */
    private java.math.BigDecimal _grossAmount;

    /**
     * Field _checkNumber
     */
    private java.lang.String _checkNumber;

    /**
     * Field _plannedIndCT
     */
    private java.lang.String _plannedIndCT;

    /**
     * Field _lastName
     */
    private java.lang.String _lastName;

    /**
     * Field _firstName
     */
    private java.lang.String _firstName;

    /**
     * Field _exchangeCompany
     */
    private java.lang.String _exchangeCompany;

    /**
     * Field _exchangePolicy
     */
    private java.lang.String _exchangePolicy;

    /**
     * Field _costBasis
     */
    private java.math.BigDecimal _costBasis;

    /**
     * Field _depositTypeCT
     */
    private java.lang.String _depositTypeCT;

    /**
     * Field _corporateName
     */
    private java.lang.String _corporateName;

    /**
     * Field _refundAmount
     */
    private java.math.BigDecimal _refundAmount;

    /**
     * Field _removalReason
     */
    private java.lang.String _removalReason;

    /**
     * Field _dateAppliedRemoved
     */
    private java.lang.String _dateAppliedRemoved;

    /**
     * Field _reasonCodeCT
     */
    private java.lang.String _reasonCodeCT;

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _clientAddressFK
     */
    private long _clientAddressFK;

    /**
     * keeps track of state for field: _clientAddressFK
     */
    private boolean _has_clientAddressFK;

    /**
     * Field _bankAccountInformationFK
     */
    private long _bankAccountInformationFK;

    /**
     * keeps track of state for field: _bankAccountInformationFK
     */
    private boolean _has_bankAccountInformationFK;

    /**
     * Field _preferenceFK
     */
    private long _preferenceFK;

    /**
     * keeps track of state for field: _preferenceFK
     */
    private boolean _has_preferenceFK;

    /**
     * Field _addressTypeCT
     */
    private java.lang.String _addressTypeCT;

    /**
     * Field _disbursementSourceCT
     */
    private java.lang.String _disbursementSourceCT;

    /**
     * Field _companyFK
     */
    private long _companyFK;

    /**
     * keeps track of state for field: _companyFK
     */
    private boolean _has_companyFK;

    /**
     * Field _inSuspenseVOList
     */
    private java.util.Vector _inSuspenseVOList;

    /**
     * Field _outSuspenseVOList
     */
    private java.util.Vector _outSuspenseVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SuspenseVO() {
        super();
        _inSuspenseVOList = new Vector();
        _outSuspenseVOList = new Vector();
    } //-- edit.common.vo.SuspenseVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addInSuspenseVO
     * 
     * @param vInSuspenseVO
     */
    public void addInSuspenseVO(edit.common.vo.InSuspenseVO vInSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInSuspenseVO.setParentVO(this.getClass(), this);
        _inSuspenseVOList.addElement(vInSuspenseVO);
    } //-- void addInSuspenseVO(edit.common.vo.InSuspenseVO) 

    /**
     * Method addInSuspenseVO
     * 
     * @param index
     * @param vInSuspenseVO
     */
    public void addInSuspenseVO(int index, edit.common.vo.InSuspenseVO vInSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInSuspenseVO.setParentVO(this.getClass(), this);
        _inSuspenseVOList.insertElementAt(vInSuspenseVO, index);
    } //-- void addInSuspenseVO(int, edit.common.vo.InSuspenseVO) 

    /**
     * Method addOutSuspenseVO
     * 
     * @param vOutSuspenseVO
     */
    public void addOutSuspenseVO(edit.common.vo.OutSuspenseVO vOutSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOutSuspenseVO.setParentVO(this.getClass(), this);
        _outSuspenseVOList.addElement(vOutSuspenseVO);
    } //-- void addOutSuspenseVO(edit.common.vo.OutSuspenseVO) 

    /**
     * Method addOutSuspenseVO
     * 
     * @param index
     * @param vOutSuspenseVO
     */
    public void addOutSuspenseVO(int index, edit.common.vo.OutSuspenseVO vOutSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOutSuspenseVO.setParentVO(this.getClass(), this);
        _outSuspenseVOList.insertElementAt(vOutSuspenseVO, index);
    } //-- void addOutSuspenseVO(int, edit.common.vo.OutSuspenseVO) 

    /**
     * Method enumerateInSuspenseVO
     */
    public java.util.Enumeration enumerateInSuspenseVO()
    {
        return _inSuspenseVOList.elements();
    } //-- java.util.Enumeration enumerateInSuspenseVO() 

    /**
     * Method enumerateOutSuspenseVO
     */
    public java.util.Enumeration enumerateOutSuspenseVO()
    {
        return _outSuspenseVOList.elements();
    } //-- java.util.Enumeration enumerateOutSuspenseVO() 

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
        
        if (obj instanceof SuspenseVO) {
        
            SuspenseVO temp = (SuspenseVO)obj;
            if (this._suspensePK != temp._suspensePK)
                return false;
            if (this._has_suspensePK != temp._has_suspensePK)
                return false;
            if (this._userDefNumber != null) {
                if (temp._userDefNumber == null) return false;
                else if (!(this._userDefNumber.equals(temp._userDefNumber))) 
                    return false;
            }
            else if (temp._userDefNumber != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._suspenseAmount != null) {
                if (temp._suspenseAmount == null) return false;
                else if (!(this._suspenseAmount.equals(temp._suspenseAmount))) 
                    return false;
            }
            else if (temp._suspenseAmount != null)
                return false;
            if (this._memoCode != null) {
                if (temp._memoCode == null) return false;
                else if (!(this._memoCode.equals(temp._memoCode))) 
                    return false;
            }
            else if (temp._memoCode != null)
                return false;
            if (this._originalContractNumber != null) {
                if (temp._originalContractNumber == null) return false;
                else if (!(this._originalContractNumber.equals(temp._originalContractNumber))) 
                    return false;
            }
            else if (temp._originalContractNumber != null)
                return false;
            if (this._originalAmount != null) {
                if (temp._originalAmount == null) return false;
                else if (!(this._originalAmount.equals(temp._originalAmount))) 
                    return false;
            }
            else if (temp._originalAmount != null)
                return false;
            if (this._originalMemoCode != null) {
                if (temp._originalMemoCode == null) return false;
                else if (!(this._originalMemoCode.equals(temp._originalMemoCode))) 
                    return false;
            }
            else if (temp._originalMemoCode != null)
                return false;
            if (this._pendingSuspenseAmount != null) {
                if (temp._pendingSuspenseAmount == null) return false;
                else if (!(this._pendingSuspenseAmount.equals(temp._pendingSuspenseAmount))) 
                    return false;
            }
            else if (temp._pendingSuspenseAmount != null)
                return false;
            if (this._accountingPendingInd != null) {
                if (temp._accountingPendingInd == null) return false;
                else if (!(this._accountingPendingInd.equals(temp._accountingPendingInd))) 
                    return false;
            }
            else if (temp._accountingPendingInd != null)
                return false;
            if (this._maintenanceInd != null) {
                if (temp._maintenanceInd == null) return false;
                else if (!(this._maintenanceInd.equals(temp._maintenanceInd))) 
                    return false;
            }
            else if (temp._maintenanceInd != null)
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
            if (this._processDate != null) {
                if (temp._processDate == null) return false;
                else if (!(this._processDate.equals(temp._processDate))) 
                    return false;
            }
            else if (temp._processDate != null)
                return false;
            if (this._directionCT != null) {
                if (temp._directionCT == null) return false;
                else if (!(this._directionCT.equals(temp._directionCT))) 
                    return false;
            }
            else if (temp._directionCT != null)
                return false;
            if (this._premiumTypeCT != null) {
                if (temp._premiumTypeCT == null) return false;
                else if (!(this._premiumTypeCT.equals(temp._premiumTypeCT))) 
                    return false;
            }
            else if (temp._premiumTypeCT != null)
                return false;
            if (this._suspenseType != null) {
                if (temp._suspenseType == null) return false;
                else if (!(this._suspenseType.equals(temp._suspenseType))) 
                    return false;
            }
            else if (temp._suspenseType != null)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._taxYear != temp._taxYear)
                return false;
            if (this._has_taxYear != temp._has_taxYear)
                return false;
            if (this._contractPlacedFrom != null) {
                if (temp._contractPlacedFrom == null) return false;
                else if (!(this._contractPlacedFrom.equals(temp._contractPlacedFrom))) 
                    return false;
            }
            else if (temp._contractPlacedFrom != null)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
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
            if (this._cashBatchContractFK != temp._cashBatchContractFK)
                return false;
            if (this._has_cashBatchContractFK != temp._has_cashBatchContractFK)
                return false;
            if (this._grossAmount != null) {
                if (temp._grossAmount == null) return false;
                else if (!(this._grossAmount.equals(temp._grossAmount))) 
                    return false;
            }
            else if (temp._grossAmount != null)
                return false;
            if (this._checkNumber != null) {
                if (temp._checkNumber == null) return false;
                else if (!(this._checkNumber.equals(temp._checkNumber))) 
                    return false;
            }
            else if (temp._checkNumber != null)
                return false;
            if (this._plannedIndCT != null) {
                if (temp._plannedIndCT == null) return false;
                else if (!(this._plannedIndCT.equals(temp._plannedIndCT))) 
                    return false;
            }
            else if (temp._plannedIndCT != null)
                return false;
            if (this._lastName != null) {
                if (temp._lastName == null) return false;
                else if (!(this._lastName.equals(temp._lastName))) 
                    return false;
            }
            else if (temp._lastName != null)
                return false;
            if (this._firstName != null) {
                if (temp._firstName == null) return false;
                else if (!(this._firstName.equals(temp._firstName))) 
                    return false;
            }
            else if (temp._firstName != null)
                return false;
            if (this._exchangeCompany != null) {
                if (temp._exchangeCompany == null) return false;
                else if (!(this._exchangeCompany.equals(temp._exchangeCompany))) 
                    return false;
            }
            else if (temp._exchangeCompany != null)
                return false;
            if (this._exchangePolicy != null) {
                if (temp._exchangePolicy == null) return false;
                else if (!(this._exchangePolicy.equals(temp._exchangePolicy))) 
                    return false;
            }
            else if (temp._exchangePolicy != null)
                return false;
            if (this._costBasis != null) {
                if (temp._costBasis == null) return false;
                else if (!(this._costBasis.equals(temp._costBasis))) 
                    return false;
            }
            else if (temp._costBasis != null)
                return false;
            if (this._depositTypeCT != null) {
                if (temp._depositTypeCT == null) return false;
                else if (!(this._depositTypeCT.equals(temp._depositTypeCT))) 
                    return false;
            }
            else if (temp._depositTypeCT != null)
                return false;
            if (this._corporateName != null) {
                if (temp._corporateName == null) return false;
                else if (!(this._corporateName.equals(temp._corporateName))) 
                    return false;
            }
            else if (temp._corporateName != null)
                return false;
            if (this._refundAmount != null) {
                if (temp._refundAmount == null) return false;
                else if (!(this._refundAmount.equals(temp._refundAmount))) 
                    return false;
            }
            else if (temp._refundAmount != null)
                return false;
            if (this._removalReason != null) {
                if (temp._removalReason == null) return false;
                else if (!(this._removalReason.equals(temp._removalReason))) 
                    return false;
            }
            else if (temp._removalReason != null)
                return false;
            if (this._dateAppliedRemoved != null) {
                if (temp._dateAppliedRemoved == null) return false;
                else if (!(this._dateAppliedRemoved.equals(temp._dateAppliedRemoved))) 
                    return false;
            }
            else if (temp._dateAppliedRemoved != null)
                return false;
            if (this._reasonCodeCT != null) {
                if (temp._reasonCodeCT == null) return false;
                else if (!(this._reasonCodeCT.equals(temp._reasonCodeCT))) 
                    return false;
            }
            else if (temp._reasonCodeCT != null)
                return false;
            if (this._clientDetailFK != temp._clientDetailFK)
                return false;
            if (this._has_clientDetailFK != temp._has_clientDetailFK)
                return false;
            if (this._clientAddressFK != temp._clientAddressFK)
                return false;
            if (this._has_clientAddressFK != temp._has_clientAddressFK)
                return false;
            if (this._bankAccountInformationFK != temp._bankAccountInformationFK)
                return false;
            if (this._has_bankAccountInformationFK != temp._has_bankAccountInformationFK)
                return false;
            if (this._preferenceFK != temp._preferenceFK)
                return false;
            if (this._has_preferenceFK != temp._has_preferenceFK)
                return false;
            if (this._addressTypeCT != null) {
                if (temp._addressTypeCT == null) return false;
                else if (!(this._addressTypeCT.equals(temp._addressTypeCT))) 
                    return false;
            }
            else if (temp._addressTypeCT != null)
                return false;
            if (this._disbursementSourceCT != null) {
                if (temp._disbursementSourceCT == null) return false;
                else if (!(this._disbursementSourceCT.equals(temp._disbursementSourceCT))) 
                    return false;
            }
            else if (temp._disbursementSourceCT != null)
                return false;
            if (this._companyFK != temp._companyFK)
                return false;
            if (this._has_companyFK != temp._has_companyFK)
                return false;
            if (this._inSuspenseVOList != null) {
                if (temp._inSuspenseVOList == null) return false;
                else if (!(this._inSuspenseVOList.equals(temp._inSuspenseVOList))) 
                    return false;
            }
            else if (temp._inSuspenseVOList != null)
                return false;
            if (this._outSuspenseVOList != null) {
                if (temp._outSuspenseVOList == null) return false;
                else if (!(this._outSuspenseVOList.equals(temp._outSuspenseVOList))) 
                    return false;
            }
            else if (temp._outSuspenseVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountingPendingIndReturns the value of field
     * 'accountingPendingInd'.
     * 
     * @return the value of field 'accountingPendingInd'.
     */
    public java.lang.String getAccountingPendingInd()
    {
        return this._accountingPendingInd;
    } //-- java.lang.String getAccountingPendingInd() 

    /**
     * Method getAddressTypeCTReturns the value of field
     * 'addressTypeCT'.
     * 
     * @return the value of field 'addressTypeCT'.
     */
    public java.lang.String getAddressTypeCT()
    {
        return this._addressTypeCT;
    } //-- java.lang.String getAddressTypeCT() 

    /**
     * Method getBankAccountInformationFKReturns the value of field
     * 'bankAccountInformationFK'.
     * 
     * @return the value of field 'bankAccountInformationFK'.
     */
    public long getBankAccountInformationFK()
    {
        return this._bankAccountInformationFK;
    } //-- long getBankAccountInformationFK() 

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
     * Method getCheckNumberReturns the value of field
     * 'checkNumber'.
     * 
     * @return the value of field 'checkNumber'.
     */
    public java.lang.String getCheckNumber()
    {
        return this._checkNumber;
    } //-- java.lang.String getCheckNumber() 

    /**
     * Method getClientAddressFKReturns the value of field
     * 'clientAddressFK'.
     * 
     * @return the value of field 'clientAddressFK'.
     */
    public long getClientAddressFK()
    {
        return this._clientAddressFK;
    } //-- long getClientAddressFK() 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

    /**
     * Method getCompanyFKReturns the value of field 'companyFK'.
     * 
     * @return the value of field 'companyFK'.
     */
    public long getCompanyFK()
    {
        return this._companyFK;
    } //-- long getCompanyFK() 

    /**
     * Method getContractPlacedFromReturns the value of field
     * 'contractPlacedFrom'.
     * 
     * @return the value of field 'contractPlacedFrom'.
     */
    public java.lang.String getContractPlacedFrom()
    {
        return this._contractPlacedFrom;
    } //-- java.lang.String getContractPlacedFrom() 

    /**
     * Method getCorporateNameReturns the value of field
     * 'corporateName'.
     * 
     * @return the value of field 'corporateName'.
     */
    public java.lang.String getCorporateName()
    {
        return this._corporateName;
    } //-- java.lang.String getCorporateName() 

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
     * Method getDateAppliedRemovedReturns the value of field
     * 'dateAppliedRemoved'.
     * 
     * @return the value of field 'dateAppliedRemoved'.
     */
    public java.lang.String getDateAppliedRemoved()
    {
        return this._dateAppliedRemoved;
    } //-- java.lang.String getDateAppliedRemoved() 

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
     * Method getDirectionCTReturns the value of field
     * 'directionCT'.
     * 
     * @return the value of field 'directionCT'.
     */
    public java.lang.String getDirectionCT()
    {
        return this._directionCT;
    } //-- java.lang.String getDirectionCT() 

    /**
     * Method getDisbursementSourceCTReturns the value of field
     * 'disbursementSourceCT'.
     * 
     * @return the value of field 'disbursementSourceCT'.
     */
    public java.lang.String getDisbursementSourceCT()
    {
        return this._disbursementSourceCT;
    } //-- java.lang.String getDisbursementSourceCT() 

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
     * Method getExchangeCompanyReturns the value of field
     * 'exchangeCompany'.
     * 
     * @return the value of field 'exchangeCompany'.
     */
    public java.lang.String getExchangeCompany()
    {
        return this._exchangeCompany;
    } //-- java.lang.String getExchangeCompany() 

    /**
     * Method getExchangePolicyReturns the value of field
     * 'exchangePolicy'.
     * 
     * @return the value of field 'exchangePolicy'.
     */
    public java.lang.String getExchangePolicy()
    {
        return this._exchangePolicy;
    } //-- java.lang.String getExchangePolicy() 

    /**
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

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
     * Method getGrossAmountReturns the value of field
     * 'grossAmount'.
     * 
     * @return the value of field 'grossAmount'.
     */
    public java.math.BigDecimal getGrossAmount()
    {
        return this._grossAmount;
    } //-- java.math.BigDecimal getGrossAmount() 

    /**
     * Method getInSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.InSuspenseVO getInSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _inSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InSuspenseVO) _inSuspenseVOList.elementAt(index);
    } //-- edit.common.vo.InSuspenseVO getInSuspenseVO(int) 

    /**
     * Method getInSuspenseVO
     */
    public edit.common.vo.InSuspenseVO[] getInSuspenseVO()
    {
        int size = _inSuspenseVOList.size();
        edit.common.vo.InSuspenseVO[] mArray = new edit.common.vo.InSuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InSuspenseVO) _inSuspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InSuspenseVO[] getInSuspenseVO() 

    /**
     * Method getInSuspenseVOCount
     */
    public int getInSuspenseVOCount()
    {
        return _inSuspenseVOList.size();
    } //-- int getInSuspenseVOCount() 

    /**
     * Method getLastNameReturns the value of field 'lastName'.
     * 
     * @return the value of field 'lastName'.
     */
    public java.lang.String getLastName()
    {
        return this._lastName;
    } //-- java.lang.String getLastName() 

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
     * Method getMaintenanceIndReturns the value of field
     * 'maintenanceInd'.
     * 
     * @return the value of field 'maintenanceInd'.
     */
    public java.lang.String getMaintenanceInd()
    {
        return this._maintenanceInd;
    } //-- java.lang.String getMaintenanceInd() 

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
     * Method getOriginalAmountReturns the value of field
     * 'originalAmount'.
     * 
     * @return the value of field 'originalAmount'.
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return this._originalAmount;
    } //-- java.math.BigDecimal getOriginalAmount() 

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
     * Method getOriginalMemoCodeReturns the value of field
     * 'originalMemoCode'.
     * 
     * @return the value of field 'originalMemoCode'.
     */
    public java.lang.String getOriginalMemoCode()
    {
        return this._originalMemoCode;
    } //-- java.lang.String getOriginalMemoCode() 

    /**
     * Method getOutSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.OutSuspenseVO getOutSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _outSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OutSuspenseVO) _outSuspenseVOList.elementAt(index);
    } //-- edit.common.vo.OutSuspenseVO getOutSuspenseVO(int) 

    /**
     * Method getOutSuspenseVO
     */
    public edit.common.vo.OutSuspenseVO[] getOutSuspenseVO()
    {
        int size = _outSuspenseVOList.size();
        edit.common.vo.OutSuspenseVO[] mArray = new edit.common.vo.OutSuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OutSuspenseVO) _outSuspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OutSuspenseVO[] getOutSuspenseVO() 

    /**
     * Method getOutSuspenseVOCount
     */
    public int getOutSuspenseVOCount()
    {
        return _outSuspenseVOList.size();
    } //-- int getOutSuspenseVOCount() 

    /**
     * Method getPendingSuspenseAmountReturns the value of field
     * 'pendingSuspenseAmount'.
     * 
     * @return the value of field 'pendingSuspenseAmount'.
     */
    public java.math.BigDecimal getPendingSuspenseAmount()
    {
        return this._pendingSuspenseAmount;
    } //-- java.math.BigDecimal getPendingSuspenseAmount() 

    /**
     * Method getPlannedIndCTReturns the value of field
     * 'plannedIndCT'.
     * 
     * @return the value of field 'plannedIndCT'.
     */
    public java.lang.String getPlannedIndCT()
    {
        return this._plannedIndCT;
    } //-- java.lang.String getPlannedIndCT() 

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
     * Method getPreferenceFKReturns the value of field
     * 'preferenceFK'.
     * 
     * @return the value of field 'preferenceFK'.
     */
    public long getPreferenceFK()
    {
        return this._preferenceFK;
    } //-- long getPreferenceFK() 

    /**
     * Method getPremiumTypeCTReturns the value of field
     * 'premiumTypeCT'.
     * 
     * @return the value of field 'premiumTypeCT'.
     */
    public java.lang.String getPremiumTypeCT()
    {
        return this._premiumTypeCT;
    } //-- java.lang.String getPremiumTypeCT() 

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
     * Method getReasonCodeCTReturns the value of field
     * 'reasonCodeCT'.
     * 
     * @return the value of field 'reasonCodeCT'.
     */
    public java.lang.String getReasonCodeCT()
    {
        return this._reasonCodeCT;
    } //-- java.lang.String getReasonCodeCT() 

    /**
     * Method getRefundAmountReturns the value of field
     * 'refundAmount'.
     * 
     * @return the value of field 'refundAmount'.
     */
    public java.math.BigDecimal getRefundAmount()
    {
        return this._refundAmount;
    } //-- java.math.BigDecimal getRefundAmount() 

    /**
     * Method getRemovalReasonReturns the value of field
     * 'removalReason'.
     * 
     * @return the value of field 'removalReason'.
     */
    public java.lang.String getRemovalReason()
    {
        return this._removalReason;
    } //-- java.lang.String getRemovalReason() 

    /**
     * Method getStatusReturns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Method getSuspenseAmountReturns the value of field
     * 'suspenseAmount'.
     * 
     * @return the value of field 'suspenseAmount'.
     */
    public java.math.BigDecimal getSuspenseAmount()
    {
        return this._suspenseAmount;
    } //-- java.math.BigDecimal getSuspenseAmount() 

    /**
     * Method getSuspensePKReturns the value of field 'suspensePK'.
     * 
     * @return the value of field 'suspensePK'.
     */
    public long getSuspensePK()
    {
        return this._suspensePK;
    } //-- long getSuspensePK() 

    /**
     * Method getSuspenseTypeReturns the value of field
     * 'suspenseType'.
     * 
     * @return the value of field 'suspenseType'.
     */
    public java.lang.String getSuspenseType()
    {
        return this._suspenseType;
    } //-- java.lang.String getSuspenseType() 

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
     * Method getUserDefNumberReturns the value of field
     * 'userDefNumber'.
     * 
     * @return the value of field 'userDefNumber'.
     */
    public java.lang.String getUserDefNumber()
    {
        return this._userDefNumber;
    } //-- java.lang.String getUserDefNumber() 

    /**
     * Method hasBankAccountInformationFK
     */
    public boolean hasBankAccountInformationFK()
    {
        return this._has_bankAccountInformationFK;
    } //-- boolean hasBankAccountInformationFK() 

    /**
     * Method hasCashBatchContractFK
     */
    public boolean hasCashBatchContractFK()
    {
        return this._has_cashBatchContractFK;
    } //-- boolean hasCashBatchContractFK() 

    /**
     * Method hasClientAddressFK
     */
    public boolean hasClientAddressFK()
    {
        return this._has_clientAddressFK;
    } //-- boolean hasClientAddressFK() 

    /**
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

    /**
     * Method hasCompanyFK
     */
    public boolean hasCompanyFK()
    {
        return this._has_companyFK;
    } //-- boolean hasCompanyFK() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

    /**
     * Method hasPreferenceFK
     */
    public boolean hasPreferenceFK()
    {
        return this._has_preferenceFK;
    } //-- boolean hasPreferenceFK() 

    /**
     * Method hasSuspensePK
     */
    public boolean hasSuspensePK()
    {
        return this._has_suspensePK;
    } //-- boolean hasSuspensePK() 

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
     * Method removeAllInSuspenseVO
     */
    public void removeAllInSuspenseVO()
    {
        _inSuspenseVOList.removeAllElements();
    } //-- void removeAllInSuspenseVO() 

    /**
     * Method removeAllOutSuspenseVO
     */
    public void removeAllOutSuspenseVO()
    {
        _outSuspenseVOList.removeAllElements();
    } //-- void removeAllOutSuspenseVO() 

    /**
     * Method removeInSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.InSuspenseVO removeInSuspenseVO(int index)
    {
        java.lang.Object obj = _inSuspenseVOList.elementAt(index);
        _inSuspenseVOList.removeElementAt(index);
        return (edit.common.vo.InSuspenseVO) obj;
    } //-- edit.common.vo.InSuspenseVO removeInSuspenseVO(int) 

    /**
     * Method removeOutSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.OutSuspenseVO removeOutSuspenseVO(int index)
    {
        java.lang.Object obj = _outSuspenseVOList.elementAt(index);
        _outSuspenseVOList.removeElementAt(index);
        return (edit.common.vo.OutSuspenseVO) obj;
    } //-- edit.common.vo.OutSuspenseVO removeOutSuspenseVO(int) 

    /**
     * Method setAccountingPendingIndSets the value of field
     * 'accountingPendingInd'.
     * 
     * @param accountingPendingInd the value of field
     * 'accountingPendingInd'.
     */
    public void setAccountingPendingInd(java.lang.String accountingPendingInd)
    {
        this._accountingPendingInd = accountingPendingInd;
        
        super.setVoChanged(true);
    } //-- void setAccountingPendingInd(java.lang.String) 

    /**
     * Method setAddressTypeCTSets the value of field
     * 'addressTypeCT'.
     * 
     * @param addressTypeCT the value of field 'addressTypeCT'.
     */
    public void setAddressTypeCT(java.lang.String addressTypeCT)
    {
        this._addressTypeCT = addressTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAddressTypeCT(java.lang.String) 

    /**
     * Method setBankAccountInformationFKSets the value of field
     * 'bankAccountInformationFK'.
     * 
     * @param bankAccountInformationFK the value of field
     * 'bankAccountInformationFK'.
     */
    public void setBankAccountInformationFK(long bankAccountInformationFK)
    {
        this._bankAccountInformationFK = bankAccountInformationFK;
        
        super.setVoChanged(true);
        this._has_bankAccountInformationFK = true;
    } //-- void setBankAccountInformationFK(long) 

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
     * Method setCheckNumberSets the value of field 'checkNumber'.
     * 
     * @param checkNumber the value of field 'checkNumber'.
     */
    public void setCheckNumber(java.lang.String checkNumber)
    {
        this._checkNumber = checkNumber;
        
        super.setVoChanged(true);
    } //-- void setCheckNumber(java.lang.String) 

    /**
     * Method setClientAddressFKSets the value of field
     * 'clientAddressFK'.
     * 
     * @param clientAddressFK the value of field 'clientAddressFK'.
     */
    public void setClientAddressFK(long clientAddressFK)
    {
        this._clientAddressFK = clientAddressFK;
        
        super.setVoChanged(true);
        this._has_clientAddressFK = true;
    } //-- void setClientAddressFK(long) 

    /**
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setCompanyFKSets the value of field 'companyFK'.
     * 
     * @param companyFK the value of field 'companyFK'.
     */
    public void setCompanyFK(long companyFK)
    {
        this._companyFK = companyFK;
        
        super.setVoChanged(true);
        this._has_companyFK = true;
    } //-- void setCompanyFK(long) 

    /**
     * Method setContractPlacedFromSets the value of field
     * 'contractPlacedFrom'.
     * 
     * @param contractPlacedFrom the value of field
     * 'contractPlacedFrom'.
     */
    public void setContractPlacedFrom(java.lang.String contractPlacedFrom)
    {
        this._contractPlacedFrom = contractPlacedFrom;
        
        super.setVoChanged(true);
    } //-- void setContractPlacedFrom(java.lang.String) 

    /**
     * Method setCorporateNameSets the value of field
     * 'corporateName'.
     * 
     * @param corporateName the value of field 'corporateName'.
     */
    public void setCorporateName(java.lang.String corporateName)
    {
        this._corporateName = corporateName;
        
        super.setVoChanged(true);
    } //-- void setCorporateName(java.lang.String) 

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
     * Method setDateAppliedRemovedSets the value of field
     * 'dateAppliedRemoved'.
     * 
     * @param dateAppliedRemoved the value of field
     * 'dateAppliedRemoved'.
     */
    public void setDateAppliedRemoved(java.lang.String dateAppliedRemoved)
    {
        this._dateAppliedRemoved = dateAppliedRemoved;
        
        super.setVoChanged(true);
    } //-- void setDateAppliedRemoved(java.lang.String) 

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
     * Method setDirectionCTSets the value of field 'directionCT'.
     * 
     * @param directionCT the value of field 'directionCT'.
     */
    public void setDirectionCT(java.lang.String directionCT)
    {
        this._directionCT = directionCT;
        
        super.setVoChanged(true);
    } //-- void setDirectionCT(java.lang.String) 

    /**
     * Method setDisbursementSourceCTSets the value of field
     * 'disbursementSourceCT'.
     * 
     * @param disbursementSourceCT the value of field
     * 'disbursementSourceCT'.
     */
    public void setDisbursementSourceCT(java.lang.String disbursementSourceCT)
    {
        this._disbursementSourceCT = disbursementSourceCT;
        
        super.setVoChanged(true);
    } //-- void setDisbursementSourceCT(java.lang.String) 

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
     * Method setExchangeCompanySets the value of field
     * 'exchangeCompany'.
     * 
     * @param exchangeCompany the value of field 'exchangeCompany'.
     */
    public void setExchangeCompany(java.lang.String exchangeCompany)
    {
        this._exchangeCompany = exchangeCompany;
        
        super.setVoChanged(true);
    } //-- void setExchangeCompany(java.lang.String) 

    /**
     * Method setExchangePolicySets the value of field
     * 'exchangePolicy'.
     * 
     * @param exchangePolicy the value of field 'exchangePolicy'.
     */
    public void setExchangePolicy(java.lang.String exchangePolicy)
    {
        this._exchangePolicy = exchangePolicy;
        
        super.setVoChanged(true);
    } //-- void setExchangePolicy(java.lang.String) 

    /**
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

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
     * Method setGrossAmountSets the value of field 'grossAmount'.
     * 
     * @param grossAmount the value of field 'grossAmount'.
     */
    public void setGrossAmount(java.math.BigDecimal grossAmount)
    {
        this._grossAmount = grossAmount;
        
        super.setVoChanged(true);
    } //-- void setGrossAmount(java.math.BigDecimal) 

    /**
     * Method setInSuspenseVO
     * 
     * @param index
     * @param vInSuspenseVO
     */
    public void setInSuspenseVO(int index, edit.common.vo.InSuspenseVO vInSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _inSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInSuspenseVO.setParentVO(this.getClass(), this);
        _inSuspenseVOList.setElementAt(vInSuspenseVO, index);
    } //-- void setInSuspenseVO(int, edit.common.vo.InSuspenseVO) 

    /**
     * Method setInSuspenseVO
     * 
     * @param inSuspenseVOArray
     */
    public void setInSuspenseVO(edit.common.vo.InSuspenseVO[] inSuspenseVOArray)
    {
        //-- copy array
        _inSuspenseVOList.removeAllElements();
        for (int i = 0; i < inSuspenseVOArray.length; i++) {
            inSuspenseVOArray[i].setParentVO(this.getClass(), this);
            _inSuspenseVOList.addElement(inSuspenseVOArray[i]);
        }
    } //-- void setInSuspenseVO(edit.common.vo.InSuspenseVO) 

    /**
     * Method setLastNameSets the value of field 'lastName'.
     * 
     * @param lastName the value of field 'lastName'.
     */
    public void setLastName(java.lang.String lastName)
    {
        this._lastName = lastName;
        
        super.setVoChanged(true);
    } //-- void setLastName(java.lang.String) 

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
     * Method setMaintenanceIndSets the value of field
     * 'maintenanceInd'.
     * 
     * @param maintenanceInd the value of field 'maintenanceInd'.
     */
    public void setMaintenanceInd(java.lang.String maintenanceInd)
    {
        this._maintenanceInd = maintenanceInd;
        
        super.setVoChanged(true);
    } //-- void setMaintenanceInd(java.lang.String) 

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
     * Method setOriginalAmountSets the value of field
     * 'originalAmount'.
     * 
     * @param originalAmount the value of field 'originalAmount'.
     */
    public void setOriginalAmount(java.math.BigDecimal originalAmount)
    {
        this._originalAmount = originalAmount;
        
        super.setVoChanged(true);
    } //-- void setOriginalAmount(java.math.BigDecimal) 

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
     * Method setOriginalMemoCodeSets the value of field
     * 'originalMemoCode'.
     * 
     * @param originalMemoCode the value of field 'originalMemoCode'
     */
    public void setOriginalMemoCode(java.lang.String originalMemoCode)
    {
        this._originalMemoCode = originalMemoCode;
        
        super.setVoChanged(true);
    } //-- void setOriginalMemoCode(java.lang.String) 

    /**
     * Method setOutSuspenseVO
     * 
     * @param index
     * @param vOutSuspenseVO
     */
    public void setOutSuspenseVO(int index, edit.common.vo.OutSuspenseVO vOutSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _outSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOutSuspenseVO.setParentVO(this.getClass(), this);
        _outSuspenseVOList.setElementAt(vOutSuspenseVO, index);
    } //-- void setOutSuspenseVO(int, edit.common.vo.OutSuspenseVO) 

    /**
     * Method setOutSuspenseVO
     * 
     * @param outSuspenseVOArray
     */
    public void setOutSuspenseVO(edit.common.vo.OutSuspenseVO[] outSuspenseVOArray)
    {
        //-- copy array
        _outSuspenseVOList.removeAllElements();
        for (int i = 0; i < outSuspenseVOArray.length; i++) {
            outSuspenseVOArray[i].setParentVO(this.getClass(), this);
            _outSuspenseVOList.addElement(outSuspenseVOArray[i]);
        }
    } //-- void setOutSuspenseVO(edit.common.vo.OutSuspenseVO) 

    /**
     * Method setPendingSuspenseAmountSets the value of field
     * 'pendingSuspenseAmount'.
     * 
     * @param pendingSuspenseAmount the value of field
     * 'pendingSuspenseAmount'.
     */
    public void setPendingSuspenseAmount(java.math.BigDecimal pendingSuspenseAmount)
    {
        this._pendingSuspenseAmount = pendingSuspenseAmount;
        
        super.setVoChanged(true);
    } //-- void setPendingSuspenseAmount(java.math.BigDecimal) 

    /**
     * Method setPlannedIndCTSets the value of field
     * 'plannedIndCT'.
     * 
     * @param plannedIndCT the value of field 'plannedIndCT'.
     */
    public void setPlannedIndCT(java.lang.String plannedIndCT)
    {
        this._plannedIndCT = plannedIndCT;
        
        super.setVoChanged(true);
    } //-- void setPlannedIndCT(java.lang.String) 

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
     * Method setPreferenceFKSets the value of field
     * 'preferenceFK'.
     * 
     * @param preferenceFK the value of field 'preferenceFK'.
     */
    public void setPreferenceFK(long preferenceFK)
    {
        this._preferenceFK = preferenceFK;
        
        super.setVoChanged(true);
        this._has_preferenceFK = true;
    } //-- void setPreferenceFK(long) 

    /**
     * Method setPremiumTypeCTSets the value of field
     * 'premiumTypeCT'.
     * 
     * @param premiumTypeCT the value of field 'premiumTypeCT'.
     */
    public void setPremiumTypeCT(java.lang.String premiumTypeCT)
    {
        this._premiumTypeCT = premiumTypeCT;
        
        super.setVoChanged(true);
    } //-- void setPremiumTypeCT(java.lang.String) 

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
     * Method setReasonCodeCTSets the value of field
     * 'reasonCodeCT'.
     * 
     * @param reasonCodeCT the value of field 'reasonCodeCT'.
     */
    public void setReasonCodeCT(java.lang.String reasonCodeCT)
    {
        this._reasonCodeCT = reasonCodeCT;
        
        super.setVoChanged(true);
    } //-- void setReasonCodeCT(java.lang.String) 

    /**
     * Method setRefundAmountSets the value of field
     * 'refundAmount'.
     * 
     * @param refundAmount the value of field 'refundAmount'.
     */
    public void setRefundAmount(java.math.BigDecimal refundAmount)
    {
        this._refundAmount = refundAmount;
        
        super.setVoChanged(true);
    } //-- void setRefundAmount(java.math.BigDecimal) 

    /**
     * Method setRemovalReasonSets the value of field
     * 'removalReason'.
     * 
     * @param removalReason the value of field 'removalReason'.
     */
    public void setRemovalReason(java.lang.String removalReason)
    {
        this._removalReason = removalReason;
        
        super.setVoChanged(true);
    } //-- void setRemovalReason(java.lang.String) 

    /**
     * Method setStatusSets the value of field 'status'.
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(java.lang.String status)
    {
        this._status = status;
        
        super.setVoChanged(true);
    } //-- void setStatus(java.lang.String) 

    /**
     * Method setSuspenseAmountSets the value of field
     * 'suspenseAmount'.
     * 
     * @param suspenseAmount the value of field 'suspenseAmount'.
     */
    public void setSuspenseAmount(java.math.BigDecimal suspenseAmount)
    {
        this._suspenseAmount = suspenseAmount;
        
        super.setVoChanged(true);
    } //-- void setSuspenseAmount(java.math.BigDecimal) 

    /**
     * Method setSuspensePKSets the value of field 'suspensePK'.
     * 
     * @param suspensePK the value of field 'suspensePK'.
     */
    public void setSuspensePK(long suspensePK)
    {
        this._suspensePK = suspensePK;
        
        super.setVoChanged(true);
        this._has_suspensePK = true;
    } //-- void setSuspensePK(long) 

    /**
     * Method setSuspenseTypeSets the value of field
     * 'suspenseType'.
     * 
     * @param suspenseType the value of field 'suspenseType'.
     */
    public void setSuspenseType(java.lang.String suspenseType)
    {
        this._suspenseType = suspenseType;
        
        super.setVoChanged(true);
    } //-- void setSuspenseType(java.lang.String) 

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
     * Method setUserDefNumberSets the value of field
     * 'userDefNumber'.
     * 
     * @param userDefNumber the value of field 'userDefNumber'.
     */
    public void setUserDefNumber(java.lang.String userDefNumber)
    {
        this._userDefNumber = userDefNumber;
        
        super.setVoChanged(true);
    } //-- void setUserDefNumber(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SuspenseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SuspenseVO) Unmarshaller.unmarshal(edit.common.vo.SuspenseVO.class, reader);
    } //-- edit.common.vo.SuspenseVO unmarshal(java.io.Reader) 

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
