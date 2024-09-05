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
 * Class EquityIndexHedgeDetailVO.
 * 
 * @version $Revision$ $Date$
 */
public class EquityIndexHedgeDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _policyNumber
     */
    private java.lang.String _policyNumber;

    /**
     * Field _policyDate
     */
    private java.lang.String _policyDate;

    /**
     * Field _nextAnniversaryDate
     */
    private java.lang.String _nextAnniversaryDate;

    /**
     * Field _incomeDate
     */
    private java.lang.String _incomeDate;

    /**
     * Field _policyDuration
     */
    private int _policyDuration;

    /**
     * keeps track of state for field: _policyDuration
     */
    private boolean _has_policyDuration;

    /**
     * Field _ownerIssueAge
     */
    private int _ownerIssueAge;

    /**
     * keeps track of state for field: _ownerIssueAge
     */
    private boolean _has_ownerIssueAge;

    /**
     * Field _ownerSex
     */
    private java.lang.String _ownerSex;

    /**
     * Field _annuitantSex
     */
    private java.lang.String _annuitantSex;

    /**
     * Field _annuitantIssueAge
     */
    private int _annuitantIssueAge;

    /**
     * keeps track of state for field: _annuitantIssueAge
     */
    private boolean _has_annuitantIssueAge;

    /**
     * Field _jointAnnuitantSex
     */
    private java.lang.String _jointAnnuitantSex;

    /**
     * Field _jointAnnuitantAge
     */
    private int _jointAnnuitantAge;

    /**
     * keeps track of state for field: _jointAnnuitantAge
     */
    private boolean _has_jointAnnuitantAge;

    /**
     * Field _account
     */
    private java.lang.String _account;

    /**
     * Field _guarMinCashValueInterestRate
     */
    private java.math.BigDecimal _guarMinCashValueInterestRate;

    /**
     * Field _currentInterestRate
     */
    private java.math.BigDecimal _currentInterestRate;

    /**
     * Field _startingMVAIndexRate
     */
    private java.math.BigDecimal _startingMVAIndexRate;

    /**
     * Field _priorAnnivIndexValue
     */
    private java.math.BigDecimal _priorAnnivIndexValue;

    /**
     * Field _priorAnnivAccountValue
     */
    private java.math.BigDecimal _priorAnnivAccountValue;

    /**
     * Field _withdrawalsSincePriorAnniv
     */
    private java.math.BigDecimal _withdrawalsSincePriorAnniv;

    /**
     * Field _currentAccountValue
     */
    private java.math.BigDecimal _currentAccountValue;

    /**
     * Field _currentMinCashSurrenderValue
     */
    private java.math.BigDecimal _currentMinCashSurrenderValue;

    /**
     * Field _indexCapRate
     */
    private java.math.BigDecimal _indexCapRate;

    /**
     * Field _indexCapRateGuarTerm
     */
    private int _indexCapRateGuarTerm;

    /**
     * keeps track of state for field: _indexCapRateGuarTerm
     */
    private boolean _has_indexCapRateGuarTerm;

    /**
     * Field _indexCapMinimum
     */
    private java.math.BigDecimal _indexCapMinimum;

    /**
     * Field _participationRate
     */
    private java.math.BigDecimal _participationRate;

    /**
     * Field _indexMargin
     */
    private java.math.BigDecimal _indexMargin;

    /**
     * Field _fundNumber
     */
    private java.lang.String _fundNumber;

    /**
     * Field _bucketFK
     */
    private long _bucketFK;

    /**
     * keeps track of state for field: _bucketFK
     */
    private boolean _has_bucketFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _plannedAllocation
     */
    private java.math.BigDecimal _plannedAllocation;

    /**
     * Field _qualifiedIndicator
     */
    private java.lang.String _qualifiedIndicator;

    /**
     * Field _qualifiedType
     */
    private java.lang.String _qualifiedType;

    /**
     * Field _residentStateOfOwner
     */
    private java.lang.String _residentStateOfOwner;

    /**
     * Field _interestRateAsOf1231
     */
    private java.math.BigDecimal _interestRateAsOf1231;

    /**
     * Field _currentFixedInterestEndDate
     */
    private java.lang.String _currentFixedInterestEndDate;

    /**
     * Field _currentSurrenderCharge
     */
    private java.math.BigDecimal _currentSurrenderCharge;

    /**
     * Field _premiumBonusAmount
     */
    private java.math.BigDecimal _premiumBonusAmount;

    /**
     * Field _firstYearBonus
     */
    private java.math.BigDecimal _firstYearBonus;

    /**
     * Field _priorYearSurrenderValue
     */
    private java.math.BigDecimal _priorYearSurrenderValue;

    /**
     * Field _currentMVACharge
     */
    private java.math.BigDecimal _currentMVACharge;

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _marketingPackageName
     */
    private java.lang.String _marketingPackageName;

    /**
     * Field _groupProductName
     */
    private java.lang.String _groupProductName;

    /**
     * Field _areaName
     */
    private java.lang.String _areaName;

    /**
     * Field _businessContractName
     */
    private java.lang.String _businessContractName;

    /**
     * Field _grossPremiumCollected
     */
    private java.math.BigDecimal _grossPremiumCollected;

    /**
     * Field _bonusInterestRate
     */
    private java.math.BigDecimal _bonusInterestRate;

    /**
     * Field _bonusRateDuration
     */
    private int _bonusRateDuration;

    /**
     * keeps track of state for field: _bonusRateDuration
     */
    private boolean _has_bonusRateDuration;

    /**
     * Field _contractSurrValGuar
     */
    private java.math.BigDecimal _contractSurrValGuar;

    /**
     * Field _issueState
     */
    private java.lang.String _issueState;

    /**
     * Field _issueDate
     */
    private java.lang.String _issueDate;

    /**
     * Field _priorYearEndAcctValue
     */
    private java.math.BigDecimal _priorYearEndAcctValue;

    /**
     * Field _priorYearEndMinAcctValue
     */
    private java.math.BigDecimal _priorYearEndMinAcctValue;

    /**
     * Field _priorYearEndCashSurrenderValue
     */
    private java.math.BigDecimal _priorYearEndCashSurrenderValue;

    /**
     * Field _totalMinAccountValue
     */
    private java.math.BigDecimal _totalMinAccountValue;

    /**
     * Field _rebalanceAmount
     */
    private java.math.BigDecimal _rebalanceAmount;

    /**
     * Field _minGuarRate
     */
    private java.math.BigDecimal _minGuarRate;

    /**
     * Field _freeAmountRemaining
     */
    private java.math.BigDecimal _freeAmountRemaining;

    /**
     * Field _equityIndexHedgeDetailPK
     */
    private long _equityIndexHedgeDetailPK;

    /**
     * keeps track of state for field: _equityIndexHedgeDetailPK
     */
    private boolean _has_equityIndexHedgeDetailPK;

    /**
     * Field _createSubBucketInd
     */
    private java.lang.String _createSubBucketInd;

    /**
     * Field _riderName
     */
    private java.lang.String _riderName;

    /**
     * Field _segmentStatus
     */
    private java.lang.String _segmentStatus;

    /**
     * Field _subBucketDetailVOList
     */
    private java.util.Vector _subBucketDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EquityIndexHedgeDetailVO() {
        super();
        _subBucketDetailVOList = new Vector();
    } //-- edit.common.vo.EquityIndexHedgeDetailVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSubBucketDetailVO
     * 
     * @param vSubBucketDetailVO
     */
    public void addSubBucketDetailVO(edit.common.vo.SubBucketDetailVO vSubBucketDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSubBucketDetailVO.setParentVO(this.getClass(), this);
        _subBucketDetailVOList.addElement(vSubBucketDetailVO);
    } //-- void addSubBucketDetailVO(edit.common.vo.SubBucketDetailVO) 

    /**
     * Method addSubBucketDetailVO
     * 
     * @param index
     * @param vSubBucketDetailVO
     */
    public void addSubBucketDetailVO(int index, edit.common.vo.SubBucketDetailVO vSubBucketDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSubBucketDetailVO.setParentVO(this.getClass(), this);
        _subBucketDetailVOList.insertElementAt(vSubBucketDetailVO, index);
    } //-- void addSubBucketDetailVO(int, edit.common.vo.SubBucketDetailVO) 

    /**
     * Method enumerateSubBucketDetailVO
     */
    public java.util.Enumeration enumerateSubBucketDetailVO()
    {
        return _subBucketDetailVOList.elements();
    } //-- java.util.Enumeration enumerateSubBucketDetailVO() 

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
        
        if (obj instanceof EquityIndexHedgeDetailVO) {
        
            EquityIndexHedgeDetailVO temp = (EquityIndexHedgeDetailVO)obj;
            if (this._policyNumber != null) {
                if (temp._policyNumber == null) return false;
                else if (!(this._policyNumber.equals(temp._policyNumber))) 
                    return false;
            }
            else if (temp._policyNumber != null)
                return false;
            if (this._policyDate != null) {
                if (temp._policyDate == null) return false;
                else if (!(this._policyDate.equals(temp._policyDate))) 
                    return false;
            }
            else if (temp._policyDate != null)
                return false;
            if (this._nextAnniversaryDate != null) {
                if (temp._nextAnniversaryDate == null) return false;
                else if (!(this._nextAnniversaryDate.equals(temp._nextAnniversaryDate))) 
                    return false;
            }
            else if (temp._nextAnniversaryDate != null)
                return false;
            if (this._incomeDate != null) {
                if (temp._incomeDate == null) return false;
                else if (!(this._incomeDate.equals(temp._incomeDate))) 
                    return false;
            }
            else if (temp._incomeDate != null)
                return false;
            if (this._policyDuration != temp._policyDuration)
                return false;
            if (this._has_policyDuration != temp._has_policyDuration)
                return false;
            if (this._ownerIssueAge != temp._ownerIssueAge)
                return false;
            if (this._has_ownerIssueAge != temp._has_ownerIssueAge)
                return false;
            if (this._ownerSex != null) {
                if (temp._ownerSex == null) return false;
                else if (!(this._ownerSex.equals(temp._ownerSex))) 
                    return false;
            }
            else if (temp._ownerSex != null)
                return false;
            if (this._annuitantSex != null) {
                if (temp._annuitantSex == null) return false;
                else if (!(this._annuitantSex.equals(temp._annuitantSex))) 
                    return false;
            }
            else if (temp._annuitantSex != null)
                return false;
            if (this._annuitantIssueAge != temp._annuitantIssueAge)
                return false;
            if (this._has_annuitantIssueAge != temp._has_annuitantIssueAge)
                return false;
            if (this._jointAnnuitantSex != null) {
                if (temp._jointAnnuitantSex == null) return false;
                else if (!(this._jointAnnuitantSex.equals(temp._jointAnnuitantSex))) 
                    return false;
            }
            else if (temp._jointAnnuitantSex != null)
                return false;
            if (this._jointAnnuitantAge != temp._jointAnnuitantAge)
                return false;
            if (this._has_jointAnnuitantAge != temp._has_jointAnnuitantAge)
                return false;
            if (this._account != null) {
                if (temp._account == null) return false;
                else if (!(this._account.equals(temp._account))) 
                    return false;
            }
            else if (temp._account != null)
                return false;
            if (this._guarMinCashValueInterestRate != null) {
                if (temp._guarMinCashValueInterestRate == null) return false;
                else if (!(this._guarMinCashValueInterestRate.equals(temp._guarMinCashValueInterestRate))) 
                    return false;
            }
            else if (temp._guarMinCashValueInterestRate != null)
                return false;
            if (this._currentInterestRate != null) {
                if (temp._currentInterestRate == null) return false;
                else if (!(this._currentInterestRate.equals(temp._currentInterestRate))) 
                    return false;
            }
            else if (temp._currentInterestRate != null)
                return false;
            if (this._startingMVAIndexRate != null) {
                if (temp._startingMVAIndexRate == null) return false;
                else if (!(this._startingMVAIndexRate.equals(temp._startingMVAIndexRate))) 
                    return false;
            }
            else if (temp._startingMVAIndexRate != null)
                return false;
            if (this._priorAnnivIndexValue != null) {
                if (temp._priorAnnivIndexValue == null) return false;
                else if (!(this._priorAnnivIndexValue.equals(temp._priorAnnivIndexValue))) 
                    return false;
            }
            else if (temp._priorAnnivIndexValue != null)
                return false;
            if (this._priorAnnivAccountValue != null) {
                if (temp._priorAnnivAccountValue == null) return false;
                else if (!(this._priorAnnivAccountValue.equals(temp._priorAnnivAccountValue))) 
                    return false;
            }
            else if (temp._priorAnnivAccountValue != null)
                return false;
            if (this._withdrawalsSincePriorAnniv != null) {
                if (temp._withdrawalsSincePriorAnniv == null) return false;
                else if (!(this._withdrawalsSincePriorAnniv.equals(temp._withdrawalsSincePriorAnniv))) 
                    return false;
            }
            else if (temp._withdrawalsSincePriorAnniv != null)
                return false;
            if (this._currentAccountValue != null) {
                if (temp._currentAccountValue == null) return false;
                else if (!(this._currentAccountValue.equals(temp._currentAccountValue))) 
                    return false;
            }
            else if (temp._currentAccountValue != null)
                return false;
            if (this._currentMinCashSurrenderValue != null) {
                if (temp._currentMinCashSurrenderValue == null) return false;
                else if (!(this._currentMinCashSurrenderValue.equals(temp._currentMinCashSurrenderValue))) 
                    return false;
            }
            else if (temp._currentMinCashSurrenderValue != null)
                return false;
            if (this._indexCapRate != null) {
                if (temp._indexCapRate == null) return false;
                else if (!(this._indexCapRate.equals(temp._indexCapRate))) 
                    return false;
            }
            else if (temp._indexCapRate != null)
                return false;
            if (this._indexCapRateGuarTerm != temp._indexCapRateGuarTerm)
                return false;
            if (this._has_indexCapRateGuarTerm != temp._has_indexCapRateGuarTerm)
                return false;
            if (this._indexCapMinimum != null) {
                if (temp._indexCapMinimum == null) return false;
                else if (!(this._indexCapMinimum.equals(temp._indexCapMinimum))) 
                    return false;
            }
            else if (temp._indexCapMinimum != null)
                return false;
            if (this._participationRate != null) {
                if (temp._participationRate == null) return false;
                else if (!(this._participationRate.equals(temp._participationRate))) 
                    return false;
            }
            else if (temp._participationRate != null)
                return false;
            if (this._indexMargin != null) {
                if (temp._indexMargin == null) return false;
                else if (!(this._indexMargin.equals(temp._indexMargin))) 
                    return false;
            }
            else if (temp._indexMargin != null)
                return false;
            if (this._fundNumber != null) {
                if (temp._fundNumber == null) return false;
                else if (!(this._fundNumber.equals(temp._fundNumber))) 
                    return false;
            }
            else if (temp._fundNumber != null)
                return false;
            if (this._bucketFK != temp._bucketFK)
                return false;
            if (this._has_bucketFK != temp._has_bucketFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._plannedAllocation != null) {
                if (temp._plannedAllocation == null) return false;
                else if (!(this._plannedAllocation.equals(temp._plannedAllocation))) 
                    return false;
            }
            else if (temp._plannedAllocation != null)
                return false;
            if (this._qualifiedIndicator != null) {
                if (temp._qualifiedIndicator == null) return false;
                else if (!(this._qualifiedIndicator.equals(temp._qualifiedIndicator))) 
                    return false;
            }
            else if (temp._qualifiedIndicator != null)
                return false;
            if (this._qualifiedType != null) {
                if (temp._qualifiedType == null) return false;
                else if (!(this._qualifiedType.equals(temp._qualifiedType))) 
                    return false;
            }
            else if (temp._qualifiedType != null)
                return false;
            if (this._residentStateOfOwner != null) {
                if (temp._residentStateOfOwner == null) return false;
                else if (!(this._residentStateOfOwner.equals(temp._residentStateOfOwner))) 
                    return false;
            }
            else if (temp._residentStateOfOwner != null)
                return false;
            if (this._interestRateAsOf1231 != null) {
                if (temp._interestRateAsOf1231 == null) return false;
                else if (!(this._interestRateAsOf1231.equals(temp._interestRateAsOf1231))) 
                    return false;
            }
            else if (temp._interestRateAsOf1231 != null)
                return false;
            if (this._currentFixedInterestEndDate != null) {
                if (temp._currentFixedInterestEndDate == null) return false;
                else if (!(this._currentFixedInterestEndDate.equals(temp._currentFixedInterestEndDate))) 
                    return false;
            }
            else if (temp._currentFixedInterestEndDate != null)
                return false;
            if (this._currentSurrenderCharge != null) {
                if (temp._currentSurrenderCharge == null) return false;
                else if (!(this._currentSurrenderCharge.equals(temp._currentSurrenderCharge))) 
                    return false;
            }
            else if (temp._currentSurrenderCharge != null)
                return false;
            if (this._premiumBonusAmount != null) {
                if (temp._premiumBonusAmount == null) return false;
                else if (!(this._premiumBonusAmount.equals(temp._premiumBonusAmount))) 
                    return false;
            }
            else if (temp._premiumBonusAmount != null)
                return false;
            if (this._firstYearBonus != null) {
                if (temp._firstYearBonus == null) return false;
                else if (!(this._firstYearBonus.equals(temp._firstYearBonus))) 
                    return false;
            }
            else if (temp._firstYearBonus != null)
                return false;
            if (this._priorYearSurrenderValue != null) {
                if (temp._priorYearSurrenderValue == null) return false;
                else if (!(this._priorYearSurrenderValue.equals(temp._priorYearSurrenderValue))) 
                    return false;
            }
            else if (temp._priorYearSurrenderValue != null)
                return false;
            if (this._currentMVACharge != null) {
                if (temp._currentMVACharge == null) return false;
                else if (!(this._currentMVACharge.equals(temp._currentMVACharge))) 
                    return false;
            }
            else if (temp._currentMVACharge != null)
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
            if (this._groupProductName != null) {
                if (temp._groupProductName == null) return false;
                else if (!(this._groupProductName.equals(temp._groupProductName))) 
                    return false;
            }
            else if (temp._groupProductName != null)
                return false;
            if (this._areaName != null) {
                if (temp._areaName == null) return false;
                else if (!(this._areaName.equals(temp._areaName))) 
                    return false;
            }
            else if (temp._areaName != null)
                return false;
            if (this._businessContractName != null) {
                if (temp._businessContractName == null) return false;
                else if (!(this._businessContractName.equals(temp._businessContractName))) 
                    return false;
            }
            else if (temp._businessContractName != null)
                return false;
            if (this._grossPremiumCollected != null) {
                if (temp._grossPremiumCollected == null) return false;
                else if (!(this._grossPremiumCollected.equals(temp._grossPremiumCollected))) 
                    return false;
            }
            else if (temp._grossPremiumCollected != null)
                return false;
            if (this._bonusInterestRate != null) {
                if (temp._bonusInterestRate == null) return false;
                else if (!(this._bonusInterestRate.equals(temp._bonusInterestRate))) 
                    return false;
            }
            else if (temp._bonusInterestRate != null)
                return false;
            if (this._bonusRateDuration != temp._bonusRateDuration)
                return false;
            if (this._has_bonusRateDuration != temp._has_bonusRateDuration)
                return false;
            if (this._contractSurrValGuar != null) {
                if (temp._contractSurrValGuar == null) return false;
                else if (!(this._contractSurrValGuar.equals(temp._contractSurrValGuar))) 
                    return false;
            }
            else if (temp._contractSurrValGuar != null)
                return false;
            if (this._issueState != null) {
                if (temp._issueState == null) return false;
                else if (!(this._issueState.equals(temp._issueState))) 
                    return false;
            }
            else if (temp._issueState != null)
                return false;
            if (this._issueDate != null) {
                if (temp._issueDate == null) return false;
                else if (!(this._issueDate.equals(temp._issueDate))) 
                    return false;
            }
            else if (temp._issueDate != null)
                return false;
            if (this._priorYearEndAcctValue != null) {
                if (temp._priorYearEndAcctValue == null) return false;
                else if (!(this._priorYearEndAcctValue.equals(temp._priorYearEndAcctValue))) 
                    return false;
            }
            else if (temp._priorYearEndAcctValue != null)
                return false;
            if (this._priorYearEndMinAcctValue != null) {
                if (temp._priorYearEndMinAcctValue == null) return false;
                else if (!(this._priorYearEndMinAcctValue.equals(temp._priorYearEndMinAcctValue))) 
                    return false;
            }
            else if (temp._priorYearEndMinAcctValue != null)
                return false;
            if (this._priorYearEndCashSurrenderValue != null) {
                if (temp._priorYearEndCashSurrenderValue == null) return false;
                else if (!(this._priorYearEndCashSurrenderValue.equals(temp._priorYearEndCashSurrenderValue))) 
                    return false;
            }
            else if (temp._priorYearEndCashSurrenderValue != null)
                return false;
            if (this._totalMinAccountValue != null) {
                if (temp._totalMinAccountValue == null) return false;
                else if (!(this._totalMinAccountValue.equals(temp._totalMinAccountValue))) 
                    return false;
            }
            else if (temp._totalMinAccountValue != null)
                return false;
            if (this._rebalanceAmount != null) {
                if (temp._rebalanceAmount == null) return false;
                else if (!(this._rebalanceAmount.equals(temp._rebalanceAmount))) 
                    return false;
            }
            else if (temp._rebalanceAmount != null)
                return false;
            if (this._minGuarRate != null) {
                if (temp._minGuarRate == null) return false;
                else if (!(this._minGuarRate.equals(temp._minGuarRate))) 
                    return false;
            }
            else if (temp._minGuarRate != null)
                return false;
            if (this._freeAmountRemaining != null) {
                if (temp._freeAmountRemaining == null) return false;
                else if (!(this._freeAmountRemaining.equals(temp._freeAmountRemaining))) 
                    return false;
            }
            else if (temp._freeAmountRemaining != null)
                return false;
            if (this._equityIndexHedgeDetailPK != temp._equityIndexHedgeDetailPK)
                return false;
            if (this._has_equityIndexHedgeDetailPK != temp._has_equityIndexHedgeDetailPK)
                return false;
            if (this._createSubBucketInd != null) {
                if (temp._createSubBucketInd == null) return false;
                else if (!(this._createSubBucketInd.equals(temp._createSubBucketInd))) 
                    return false;
            }
            else if (temp._createSubBucketInd != null)
                return false;
            if (this._riderName != null) {
                if (temp._riderName == null) return false;
                else if (!(this._riderName.equals(temp._riderName))) 
                    return false;
            }
            else if (temp._riderName != null)
                return false;
            if (this._segmentStatus != null) {
                if (temp._segmentStatus == null) return false;
                else if (!(this._segmentStatus.equals(temp._segmentStatus))) 
                    return false;
            }
            else if (temp._segmentStatus != null)
                return false;
            if (this._subBucketDetailVOList != null) {
                if (temp._subBucketDetailVOList == null) return false;
                else if (!(this._subBucketDetailVOList.equals(temp._subBucketDetailVOList))) 
                    return false;
            }
            else if (temp._subBucketDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountReturns the value of field 'account'.
     * 
     * @return the value of field 'account'.
     */
    public java.lang.String getAccount()
    {
        return this._account;
    } //-- java.lang.String getAccount() 

    /**
     * Method getAnnuitantIssueAgeReturns the value of field
     * 'annuitantIssueAge'.
     * 
     * @return the value of field 'annuitantIssueAge'.
     */
    public int getAnnuitantIssueAge()
    {
        return this._annuitantIssueAge;
    } //-- int getAnnuitantIssueAge() 

    /**
     * Method getAnnuitantSexReturns the value of field
     * 'annuitantSex'.
     * 
     * @return the value of field 'annuitantSex'.
     */
    public java.lang.String getAnnuitantSex()
    {
        return this._annuitantSex;
    } //-- java.lang.String getAnnuitantSex() 

    /**
     * Method getAreaNameReturns the value of field 'areaName'.
     * 
     * @return the value of field 'areaName'.
     */
    public java.lang.String getAreaName()
    {
        return this._areaName;
    } //-- java.lang.String getAreaName() 

    /**
     * Method getBonusInterestRateReturns the value of field
     * 'bonusInterestRate'.
     * 
     * @return the value of field 'bonusInterestRate'.
     */
    public java.math.BigDecimal getBonusInterestRate()
    {
        return this._bonusInterestRate;
    } //-- java.math.BigDecimal getBonusInterestRate() 

    /**
     * Method getBonusRateDurationReturns the value of field
     * 'bonusRateDuration'.
     * 
     * @return the value of field 'bonusRateDuration'.
     */
    public int getBonusRateDuration()
    {
        return this._bonusRateDuration;
    } //-- int getBonusRateDuration() 

    /**
     * Method getBucketFKReturns the value of field 'bucketFK'.
     * 
     * @return the value of field 'bucketFK'.
     */
    public long getBucketFK()
    {
        return this._bucketFK;
    } //-- long getBucketFK() 

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
     * Method getContractSurrValGuarReturns the value of field
     * 'contractSurrValGuar'.
     * 
     * @return the value of field 'contractSurrValGuar'.
     */
    public java.math.BigDecimal getContractSurrValGuar()
    {
        return this._contractSurrValGuar;
    } //-- java.math.BigDecimal getContractSurrValGuar() 

    /**
     * Method getCreateSubBucketIndReturns the value of field
     * 'createSubBucketInd'.
     * 
     * @return the value of field 'createSubBucketInd'.
     */
    public java.lang.String getCreateSubBucketInd()
    {
        return this._createSubBucketInd;
    } //-- java.lang.String getCreateSubBucketInd() 

    /**
     * Method getCurrentAccountValueReturns the value of field
     * 'currentAccountValue'.
     * 
     * @return the value of field 'currentAccountValue'.
     */
    public java.math.BigDecimal getCurrentAccountValue()
    {
        return this._currentAccountValue;
    } //-- java.math.BigDecimal getCurrentAccountValue() 

    /**
     * Method getCurrentFixedInterestEndDateReturns the value of
     * field 'currentFixedInterestEndDate'.
     * 
     * @return the value of field 'currentFixedInterestEndDate'.
     */
    public java.lang.String getCurrentFixedInterestEndDate()
    {
        return this._currentFixedInterestEndDate;
    } //-- java.lang.String getCurrentFixedInterestEndDate() 

    /**
     * Method getCurrentInterestRateReturns the value of field
     * 'currentInterestRate'.
     * 
     * @return the value of field 'currentInterestRate'.
     */
    public java.math.BigDecimal getCurrentInterestRate()
    {
        return this._currentInterestRate;
    } //-- java.math.BigDecimal getCurrentInterestRate() 

    /**
     * Method getCurrentMVAChargeReturns the value of field
     * 'currentMVACharge'.
     * 
     * @return the value of field 'currentMVACharge'.
     */
    public java.math.BigDecimal getCurrentMVACharge()
    {
        return this._currentMVACharge;
    } //-- java.math.BigDecimal getCurrentMVACharge() 

    /**
     * Method getCurrentMinCashSurrenderValueReturns the value of
     * field 'currentMinCashSurrenderValue'.
     * 
     * @return the value of field 'currentMinCashSurrenderValue'.
     */
    public java.math.BigDecimal getCurrentMinCashSurrenderValue()
    {
        return this._currentMinCashSurrenderValue;
    } //-- java.math.BigDecimal getCurrentMinCashSurrenderValue() 

    /**
     * Method getCurrentSurrenderChargeReturns the value of field
     * 'currentSurrenderCharge'.
     * 
     * @return the value of field 'currentSurrenderCharge'.
     */
    public java.math.BigDecimal getCurrentSurrenderCharge()
    {
        return this._currentSurrenderCharge;
    } //-- java.math.BigDecimal getCurrentSurrenderCharge() 

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
     * Method getEquityIndexHedgeDetailPKReturns the value of field
     * 'equityIndexHedgeDetailPK'.
     * 
     * @return the value of field 'equityIndexHedgeDetailPK'.
     */
    public long getEquityIndexHedgeDetailPK()
    {
        return this._equityIndexHedgeDetailPK;
    } //-- long getEquityIndexHedgeDetailPK() 

    /**
     * Method getFirstYearBonusReturns the value of field
     * 'firstYearBonus'.
     * 
     * @return the value of field 'firstYearBonus'.
     */
    public java.math.BigDecimal getFirstYearBonus()
    {
        return this._firstYearBonus;
    } //-- java.math.BigDecimal getFirstYearBonus() 

    /**
     * Method getFreeAmountRemainingReturns the value of field
     * 'freeAmountRemaining'.
     * 
     * @return the value of field 'freeAmountRemaining'.
     */
    public java.math.BigDecimal getFreeAmountRemaining()
    {
        return this._freeAmountRemaining;
    } //-- java.math.BigDecimal getFreeAmountRemaining() 

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
     * Method getGrossPremiumCollectedReturns the value of field
     * 'grossPremiumCollected'.
     * 
     * @return the value of field 'grossPremiumCollected'.
     */
    public java.math.BigDecimal getGrossPremiumCollected()
    {
        return this._grossPremiumCollected;
    } //-- java.math.BigDecimal getGrossPremiumCollected() 

    /**
     * Method getGroupProductNameReturns the value of field
     * 'groupProductName'.
     * 
     * @return the value of field 'groupProductName'.
     */
    public java.lang.String getGroupProductName()
    {
        return this._groupProductName;
    } //-- java.lang.String getGroupProductName() 

    /**
     * Method getGuarMinCashValueInterestRateReturns the value of
     * field 'guarMinCashValueInterestRate'.
     * 
     * @return the value of field 'guarMinCashValueInterestRate'.
     */
    public java.math.BigDecimal getGuarMinCashValueInterestRate()
    {
        return this._guarMinCashValueInterestRate;
    } //-- java.math.BigDecimal getGuarMinCashValueInterestRate() 

    /**
     * Method getIncomeDateReturns the value of field 'incomeDate'.
     * 
     * @return the value of field 'incomeDate'.
     */
    public java.lang.String getIncomeDate()
    {
        return this._incomeDate;
    } //-- java.lang.String getIncomeDate() 

    /**
     * Method getIndexCapMinimumReturns the value of field
     * 'indexCapMinimum'.
     * 
     * @return the value of field 'indexCapMinimum'.
     */
    public java.math.BigDecimal getIndexCapMinimum()
    {
        return this._indexCapMinimum;
    } //-- java.math.BigDecimal getIndexCapMinimum() 

    /**
     * Method getIndexCapRateReturns the value of field
     * 'indexCapRate'.
     * 
     * @return the value of field 'indexCapRate'.
     */
    public java.math.BigDecimal getIndexCapRate()
    {
        return this._indexCapRate;
    } //-- java.math.BigDecimal getIndexCapRate() 

    /**
     * Method getIndexCapRateGuarTermReturns the value of field
     * 'indexCapRateGuarTerm'.
     * 
     * @return the value of field 'indexCapRateGuarTerm'.
     */
    public int getIndexCapRateGuarTerm()
    {
        return this._indexCapRateGuarTerm;
    } //-- int getIndexCapRateGuarTerm() 

    /**
     * Method getIndexMarginReturns the value of field
     * 'indexMargin'.
     * 
     * @return the value of field 'indexMargin'.
     */
    public java.math.BigDecimal getIndexMargin()
    {
        return this._indexMargin;
    } //-- java.math.BigDecimal getIndexMargin() 

    /**
     * Method getInterestRateAsOf1231Returns the value of field
     * 'interestRateAsOf1231'.
     * 
     * @return the value of field 'interestRateAsOf1231'.
     */
    public java.math.BigDecimal getInterestRateAsOf1231()
    {
        return this._interestRateAsOf1231;
    } //-- java.math.BigDecimal getInterestRateAsOf1231() 

    /**
     * Method getIssueDateReturns the value of field 'issueDate'.
     * 
     * @return the value of field 'issueDate'.
     */
    public java.lang.String getIssueDate()
    {
        return this._issueDate;
    } //-- java.lang.String getIssueDate() 

    /**
     * Method getIssueStateReturns the value of field 'issueState'.
     * 
     * @return the value of field 'issueState'.
     */
    public java.lang.String getIssueState()
    {
        return this._issueState;
    } //-- java.lang.String getIssueState() 

    /**
     * Method getJointAnnuitantAgeReturns the value of field
     * 'jointAnnuitantAge'.
     * 
     * @return the value of field 'jointAnnuitantAge'.
     */
    public int getJointAnnuitantAge()
    {
        return this._jointAnnuitantAge;
    } //-- int getJointAnnuitantAge() 

    /**
     * Method getJointAnnuitantSexReturns the value of field
     * 'jointAnnuitantSex'.
     * 
     * @return the value of field 'jointAnnuitantSex'.
     */
    public java.lang.String getJointAnnuitantSex()
    {
        return this._jointAnnuitantSex;
    } //-- java.lang.String getJointAnnuitantSex() 

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
     * Method getMinGuarRateReturns the value of field
     * 'minGuarRate'.
     * 
     * @return the value of field 'minGuarRate'.
     */
    public java.math.BigDecimal getMinGuarRate()
    {
        return this._minGuarRate;
    } //-- java.math.BigDecimal getMinGuarRate() 

    /**
     * Method getNextAnniversaryDateReturns the value of field
     * 'nextAnniversaryDate'.
     * 
     * @return the value of field 'nextAnniversaryDate'.
     */
    public java.lang.String getNextAnniversaryDate()
    {
        return this._nextAnniversaryDate;
    } //-- java.lang.String getNextAnniversaryDate() 

    /**
     * Method getOwnerIssueAgeReturns the value of field
     * 'ownerIssueAge'.
     * 
     * @return the value of field 'ownerIssueAge'.
     */
    public int getOwnerIssueAge()
    {
        return this._ownerIssueAge;
    } //-- int getOwnerIssueAge() 

    /**
     * Method getOwnerSexReturns the value of field 'ownerSex'.
     * 
     * @return the value of field 'ownerSex'.
     */
    public java.lang.String getOwnerSex()
    {
        return this._ownerSex;
    } //-- java.lang.String getOwnerSex() 

    /**
     * Method getParticipationRateReturns the value of field
     * 'participationRate'.
     * 
     * @return the value of field 'participationRate'.
     */
    public java.math.BigDecimal getParticipationRate()
    {
        return this._participationRate;
    } //-- java.math.BigDecimal getParticipationRate() 

    /**
     * Method getPlannedAllocationReturns the value of field
     * 'plannedAllocation'.
     * 
     * @return the value of field 'plannedAllocation'.
     */
    public java.math.BigDecimal getPlannedAllocation()
    {
        return this._plannedAllocation;
    } //-- java.math.BigDecimal getPlannedAllocation() 

    /**
     * Method getPolicyDateReturns the value of field 'policyDate'.
     * 
     * @return the value of field 'policyDate'.
     */
    public java.lang.String getPolicyDate()
    {
        return this._policyDate;
    } //-- java.lang.String getPolicyDate() 

    /**
     * Method getPolicyDurationReturns the value of field
     * 'policyDuration'.
     * 
     * @return the value of field 'policyDuration'.
     */
    public int getPolicyDuration()
    {
        return this._policyDuration;
    } //-- int getPolicyDuration() 

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
     * Method getPremiumBonusAmountReturns the value of field
     * 'premiumBonusAmount'.
     * 
     * @return the value of field 'premiumBonusAmount'.
     */
    public java.math.BigDecimal getPremiumBonusAmount()
    {
        return this._premiumBonusAmount;
    } //-- java.math.BigDecimal getPremiumBonusAmount() 

    /**
     * Method getPriorAnnivAccountValueReturns the value of field
     * 'priorAnnivAccountValue'.
     * 
     * @return the value of field 'priorAnnivAccountValue'.
     */
    public java.math.BigDecimal getPriorAnnivAccountValue()
    {
        return this._priorAnnivAccountValue;
    } //-- java.math.BigDecimal getPriorAnnivAccountValue() 

    /**
     * Method getPriorAnnivIndexValueReturns the value of field
     * 'priorAnnivIndexValue'.
     * 
     * @return the value of field 'priorAnnivIndexValue'.
     */
    public java.math.BigDecimal getPriorAnnivIndexValue()
    {
        return this._priorAnnivIndexValue;
    } //-- java.math.BigDecimal getPriorAnnivIndexValue() 

    /**
     * Method getPriorYearEndAcctValueReturns the value of field
     * 'priorYearEndAcctValue'.
     * 
     * @return the value of field 'priorYearEndAcctValue'.
     */
    public java.math.BigDecimal getPriorYearEndAcctValue()
    {
        return this._priorYearEndAcctValue;
    } //-- java.math.BigDecimal getPriorYearEndAcctValue() 

    /**
     * Method getPriorYearEndCashSurrenderValueReturns the value of
     * field 'priorYearEndCashSurrenderValue'.
     * 
     * @return the value of field 'priorYearEndCashSurrenderValue'.
     */
    public java.math.BigDecimal getPriorYearEndCashSurrenderValue()
    {
        return this._priorYearEndCashSurrenderValue;
    } //-- java.math.BigDecimal getPriorYearEndCashSurrenderValue() 

    /**
     * Method getPriorYearEndMinAcctValueReturns the value of field
     * 'priorYearEndMinAcctValue'.
     * 
     * @return the value of field 'priorYearEndMinAcctValue'.
     */
    public java.math.BigDecimal getPriorYearEndMinAcctValue()
    {
        return this._priorYearEndMinAcctValue;
    } //-- java.math.BigDecimal getPriorYearEndMinAcctValue() 

    /**
     * Method getPriorYearSurrenderValueReturns the value of field
     * 'priorYearSurrenderValue'.
     * 
     * @return the value of field 'priorYearSurrenderValue'.
     */
    public java.math.BigDecimal getPriorYearSurrenderValue()
    {
        return this._priorYearSurrenderValue;
    } //-- java.math.BigDecimal getPriorYearSurrenderValue() 

    /**
     * Method getQualifiedIndicatorReturns the value of field
     * 'qualifiedIndicator'.
     * 
     * @return the value of field 'qualifiedIndicator'.
     */
    public java.lang.String getQualifiedIndicator()
    {
        return this._qualifiedIndicator;
    } //-- java.lang.String getQualifiedIndicator() 

    /**
     * Method getQualifiedTypeReturns the value of field
     * 'qualifiedType'.
     * 
     * @return the value of field 'qualifiedType'.
     */
    public java.lang.String getQualifiedType()
    {
        return this._qualifiedType;
    } //-- java.lang.String getQualifiedType() 

    /**
     * Method getRebalanceAmountReturns the value of field
     * 'rebalanceAmount'.
     * 
     * @return the value of field 'rebalanceAmount'.
     */
    public java.math.BigDecimal getRebalanceAmount()
    {
        return this._rebalanceAmount;
    } //-- java.math.BigDecimal getRebalanceAmount() 

    /**
     * Method getResidentStateOfOwnerReturns the value of field
     * 'residentStateOfOwner'.
     * 
     * @return the value of field 'residentStateOfOwner'.
     */
    public java.lang.String getResidentStateOfOwner()
    {
        return this._residentStateOfOwner;
    } //-- java.lang.String getResidentStateOfOwner() 

    /**
     * Method getRiderNameReturns the value of field 'riderName'.
     * 
     * @return the value of field 'riderName'.
     */
    public java.lang.String getRiderName()
    {
        return this._riderName;
    } //-- java.lang.String getRiderName() 

    /**
     * Method getSegmentStatusReturns the value of field
     * 'segmentStatus'.
     * 
     * @return the value of field 'segmentStatus'.
     */
    public java.lang.String getSegmentStatus()
    {
        return this._segmentStatus;
    } //-- java.lang.String getSegmentStatus() 

    /**
     * Method getStartingMVAIndexRateReturns the value of field
     * 'startingMVAIndexRate'.
     * 
     * @return the value of field 'startingMVAIndexRate'.
     */
    public java.math.BigDecimal getStartingMVAIndexRate()
    {
        return this._startingMVAIndexRate;
    } //-- java.math.BigDecimal getStartingMVAIndexRate() 

    /**
     * Method getSubBucketDetailVO
     * 
     * @param index
     */
    public edit.common.vo.SubBucketDetailVO getSubBucketDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _subBucketDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SubBucketDetailVO) _subBucketDetailVOList.elementAt(index);
    } //-- edit.common.vo.SubBucketDetailVO getSubBucketDetailVO(int) 

    /**
     * Method getSubBucketDetailVO
     */
    public edit.common.vo.SubBucketDetailVO[] getSubBucketDetailVO()
    {
        int size = _subBucketDetailVOList.size();
        edit.common.vo.SubBucketDetailVO[] mArray = new edit.common.vo.SubBucketDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SubBucketDetailVO) _subBucketDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SubBucketDetailVO[] getSubBucketDetailVO() 

    /**
     * Method getSubBucketDetailVOCount
     */
    public int getSubBucketDetailVOCount()
    {
        return _subBucketDetailVOList.size();
    } //-- int getSubBucketDetailVOCount() 

    /**
     * Method getTotalMinAccountValueReturns the value of field
     * 'totalMinAccountValue'.
     * 
     * @return the value of field 'totalMinAccountValue'.
     */
    public java.math.BigDecimal getTotalMinAccountValue()
    {
        return this._totalMinAccountValue;
    } //-- java.math.BigDecimal getTotalMinAccountValue() 

    /**
     * Method getWithdrawalsSincePriorAnnivReturns the value of
     * field 'withdrawalsSincePriorAnniv'.
     * 
     * @return the value of field 'withdrawalsSincePriorAnniv'.
     */
    public java.math.BigDecimal getWithdrawalsSincePriorAnniv()
    {
        return this._withdrawalsSincePriorAnniv;
    } //-- java.math.BigDecimal getWithdrawalsSincePriorAnniv() 

    /**
     * Method hasAnnuitantIssueAge
     */
    public boolean hasAnnuitantIssueAge()
    {
        return this._has_annuitantIssueAge;
    } //-- boolean hasAnnuitantIssueAge() 

    /**
     * Method hasBonusRateDuration
     */
    public boolean hasBonusRateDuration()
    {
        return this._has_bonusRateDuration;
    } //-- boolean hasBonusRateDuration() 

    /**
     * Method hasBucketFK
     */
    public boolean hasBucketFK()
    {
        return this._has_bucketFK;
    } //-- boolean hasBucketFK() 

    /**
     * Method hasEquityIndexHedgeDetailPK
     */
    public boolean hasEquityIndexHedgeDetailPK()
    {
        return this._has_equityIndexHedgeDetailPK;
    } //-- boolean hasEquityIndexHedgeDetailPK() 

    /**
     * Method hasIndexCapRateGuarTerm
     */
    public boolean hasIndexCapRateGuarTerm()
    {
        return this._has_indexCapRateGuarTerm;
    } //-- boolean hasIndexCapRateGuarTerm() 

    /**
     * Method hasJointAnnuitantAge
     */
    public boolean hasJointAnnuitantAge()
    {
        return this._has_jointAnnuitantAge;
    } //-- boolean hasJointAnnuitantAge() 

    /**
     * Method hasOwnerIssueAge
     */
    public boolean hasOwnerIssueAge()
    {
        return this._has_ownerIssueAge;
    } //-- boolean hasOwnerIssueAge() 

    /**
     * Method hasPolicyDuration
     */
    public boolean hasPolicyDuration()
    {
        return this._has_policyDuration;
    } //-- boolean hasPolicyDuration() 

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
     * Method removeAllSubBucketDetailVO
     */
    public void removeAllSubBucketDetailVO()
    {
        _subBucketDetailVOList.removeAllElements();
    } //-- void removeAllSubBucketDetailVO() 

    /**
     * Method removeSubBucketDetailVO
     * 
     * @param index
     */
    public edit.common.vo.SubBucketDetailVO removeSubBucketDetailVO(int index)
    {
        java.lang.Object obj = _subBucketDetailVOList.elementAt(index);
        _subBucketDetailVOList.removeElementAt(index);
        return (edit.common.vo.SubBucketDetailVO) obj;
    } //-- edit.common.vo.SubBucketDetailVO removeSubBucketDetailVO(int) 

    /**
     * Method setAccountSets the value of field 'account'.
     * 
     * @param account the value of field 'account'.
     */
    public void setAccount(java.lang.String account)
    {
        this._account = account;
        
        super.setVoChanged(true);
    } //-- void setAccount(java.lang.String) 

    /**
     * Method setAnnuitantIssueAgeSets the value of field
     * 'annuitantIssueAge'.
     * 
     * @param annuitantIssueAge the value of field
     * 'annuitantIssueAge'.
     */
    public void setAnnuitantIssueAge(int annuitantIssueAge)
    {
        this._annuitantIssueAge = annuitantIssueAge;
        
        super.setVoChanged(true);
        this._has_annuitantIssueAge = true;
    } //-- void setAnnuitantIssueAge(int) 

    /**
     * Method setAnnuitantSexSets the value of field
     * 'annuitantSex'.
     * 
     * @param annuitantSex the value of field 'annuitantSex'.
     */
    public void setAnnuitantSex(java.lang.String annuitantSex)
    {
        this._annuitantSex = annuitantSex;
        
        super.setVoChanged(true);
    } //-- void setAnnuitantSex(java.lang.String) 

    /**
     * Method setAreaNameSets the value of field 'areaName'.
     * 
     * @param areaName the value of field 'areaName'.
     */
    public void setAreaName(java.lang.String areaName)
    {
        this._areaName = areaName;
        
        super.setVoChanged(true);
    } //-- void setAreaName(java.lang.String) 

    /**
     * Method setBonusInterestRateSets the value of field
     * 'bonusInterestRate'.
     * 
     * @param bonusInterestRate the value of field
     * 'bonusInterestRate'.
     */
    public void setBonusInterestRate(java.math.BigDecimal bonusInterestRate)
    {
        this._bonusInterestRate = bonusInterestRate;
        
        super.setVoChanged(true);
    } //-- void setBonusInterestRate(java.math.BigDecimal) 

    /**
     * Method setBonusRateDurationSets the value of field
     * 'bonusRateDuration'.
     * 
     * @param bonusRateDuration the value of field
     * 'bonusRateDuration'.
     */
    public void setBonusRateDuration(int bonusRateDuration)
    {
        this._bonusRateDuration = bonusRateDuration;
        
        super.setVoChanged(true);
        this._has_bonusRateDuration = true;
    } //-- void setBonusRateDuration(int) 

    /**
     * Method setBucketFKSets the value of field 'bucketFK'.
     * 
     * @param bucketFK the value of field 'bucketFK'.
     */
    public void setBucketFK(long bucketFK)
    {
        this._bucketFK = bucketFK;
        
        super.setVoChanged(true);
        this._has_bucketFK = true;
    } //-- void setBucketFK(long) 

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
     * Method setContractSurrValGuarSets the value of field
     * 'contractSurrValGuar'.
     * 
     * @param contractSurrValGuar the value of field
     * 'contractSurrValGuar'.
     */
    public void setContractSurrValGuar(java.math.BigDecimal contractSurrValGuar)
    {
        this._contractSurrValGuar = contractSurrValGuar;
        
        super.setVoChanged(true);
    } //-- void setContractSurrValGuar(java.math.BigDecimal) 

    /**
     * Method setCreateSubBucketIndSets the value of field
     * 'createSubBucketInd'.
     * 
     * @param createSubBucketInd the value of field
     * 'createSubBucketInd'.
     */
    public void setCreateSubBucketInd(java.lang.String createSubBucketInd)
    {
        this._createSubBucketInd = createSubBucketInd;
        
        super.setVoChanged(true);
    } //-- void setCreateSubBucketInd(java.lang.String) 

    /**
     * Method setCurrentAccountValueSets the value of field
     * 'currentAccountValue'.
     * 
     * @param currentAccountValue the value of field
     * 'currentAccountValue'.
     */
    public void setCurrentAccountValue(java.math.BigDecimal currentAccountValue)
    {
        this._currentAccountValue = currentAccountValue;
        
        super.setVoChanged(true);
    } //-- void setCurrentAccountValue(java.math.BigDecimal) 

    /**
     * Method setCurrentFixedInterestEndDateSets the value of field
     * 'currentFixedInterestEndDate'.
     * 
     * @param currentFixedInterestEndDate the value of field
     * 'currentFixedInterestEndDate'.
     */
    public void setCurrentFixedInterestEndDate(java.lang.String currentFixedInterestEndDate)
    {
        this._currentFixedInterestEndDate = currentFixedInterestEndDate;
        
        super.setVoChanged(true);
    } //-- void setCurrentFixedInterestEndDate(java.lang.String) 

    /**
     * Method setCurrentInterestRateSets the value of field
     * 'currentInterestRate'.
     * 
     * @param currentInterestRate the value of field
     * 'currentInterestRate'.
     */
    public void setCurrentInterestRate(java.math.BigDecimal currentInterestRate)
    {
        this._currentInterestRate = currentInterestRate;
        
        super.setVoChanged(true);
    } //-- void setCurrentInterestRate(java.math.BigDecimal) 

    /**
     * Method setCurrentMVAChargeSets the value of field
     * 'currentMVACharge'.
     * 
     * @param currentMVACharge the value of field 'currentMVACharge'
     */
    public void setCurrentMVACharge(java.math.BigDecimal currentMVACharge)
    {
        this._currentMVACharge = currentMVACharge;
        
        super.setVoChanged(true);
    } //-- void setCurrentMVACharge(java.math.BigDecimal) 

    /**
     * Method setCurrentMinCashSurrenderValueSets the value of
     * field 'currentMinCashSurrenderValue'.
     * 
     * @param currentMinCashSurrenderValue the value of field
     * 'currentMinCashSurrenderValue'.
     */
    public void setCurrentMinCashSurrenderValue(java.math.BigDecimal currentMinCashSurrenderValue)
    {
        this._currentMinCashSurrenderValue = currentMinCashSurrenderValue;
        
        super.setVoChanged(true);
    } //-- void setCurrentMinCashSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setCurrentSurrenderChargeSets the value of field
     * 'currentSurrenderCharge'.
     * 
     * @param currentSurrenderCharge the value of field
     * 'currentSurrenderCharge'.
     */
    public void setCurrentSurrenderCharge(java.math.BigDecimal currentSurrenderCharge)
    {
        this._currentSurrenderCharge = currentSurrenderCharge;
        
        super.setVoChanged(true);
    } //-- void setCurrentSurrenderCharge(java.math.BigDecimal) 

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
     * Method setEquityIndexHedgeDetailPKSets the value of field
     * 'equityIndexHedgeDetailPK'.
     * 
     * @param equityIndexHedgeDetailPK the value of field
     * 'equityIndexHedgeDetailPK'.
     */
    public void setEquityIndexHedgeDetailPK(long equityIndexHedgeDetailPK)
    {
        this._equityIndexHedgeDetailPK = equityIndexHedgeDetailPK;
        
        super.setVoChanged(true);
        this._has_equityIndexHedgeDetailPK = true;
    } //-- void setEquityIndexHedgeDetailPK(long) 

    /**
     * Method setFirstYearBonusSets the value of field
     * 'firstYearBonus'.
     * 
     * @param firstYearBonus the value of field 'firstYearBonus'.
     */
    public void setFirstYearBonus(java.math.BigDecimal firstYearBonus)
    {
        this._firstYearBonus = firstYearBonus;
        
        super.setVoChanged(true);
    } //-- void setFirstYearBonus(java.math.BigDecimal) 

    /**
     * Method setFreeAmountRemainingSets the value of field
     * 'freeAmountRemaining'.
     * 
     * @param freeAmountRemaining the value of field
     * 'freeAmountRemaining'.
     */
    public void setFreeAmountRemaining(java.math.BigDecimal freeAmountRemaining)
    {
        this._freeAmountRemaining = freeAmountRemaining;
        
        super.setVoChanged(true);
    } //-- void setFreeAmountRemaining(java.math.BigDecimal) 

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
     * Method setGrossPremiumCollectedSets the value of field
     * 'grossPremiumCollected'.
     * 
     * @param grossPremiumCollected the value of field
     * 'grossPremiumCollected'.
     */
    public void setGrossPremiumCollected(java.math.BigDecimal grossPremiumCollected)
    {
        this._grossPremiumCollected = grossPremiumCollected;
        
        super.setVoChanged(true);
    } //-- void setGrossPremiumCollected(java.math.BigDecimal) 

    /**
     * Method setGroupProductNameSets the value of field
     * 'groupProductName'.
     * 
     * @param groupProductName the value of field 'groupProductName'
     */
    public void setGroupProductName(java.lang.String groupProductName)
    {
        this._groupProductName = groupProductName;
        
        super.setVoChanged(true);
    } //-- void setGroupProductName(java.lang.String) 

    /**
     * Method setGuarMinCashValueInterestRateSets the value of
     * field 'guarMinCashValueInterestRate'.
     * 
     * @param guarMinCashValueInterestRate the value of field
     * 'guarMinCashValueInterestRate'.
     */
    public void setGuarMinCashValueInterestRate(java.math.BigDecimal guarMinCashValueInterestRate)
    {
        this._guarMinCashValueInterestRate = guarMinCashValueInterestRate;
        
        super.setVoChanged(true);
    } //-- void setGuarMinCashValueInterestRate(java.math.BigDecimal) 

    /**
     * Method setIncomeDateSets the value of field 'incomeDate'.
     * 
     * @param incomeDate the value of field 'incomeDate'.
     */
    public void setIncomeDate(java.lang.String incomeDate)
    {
        this._incomeDate = incomeDate;
        
        super.setVoChanged(true);
    } //-- void setIncomeDate(java.lang.String) 

    /**
     * Method setIndexCapMinimumSets the value of field
     * 'indexCapMinimum'.
     * 
     * @param indexCapMinimum the value of field 'indexCapMinimum'.
     */
    public void setIndexCapMinimum(java.math.BigDecimal indexCapMinimum)
    {
        this._indexCapMinimum = indexCapMinimum;
        
        super.setVoChanged(true);
    } //-- void setIndexCapMinimum(java.math.BigDecimal) 

    /**
     * Method setIndexCapRateSets the value of field
     * 'indexCapRate'.
     * 
     * @param indexCapRate the value of field 'indexCapRate'.
     */
    public void setIndexCapRate(java.math.BigDecimal indexCapRate)
    {
        this._indexCapRate = indexCapRate;
        
        super.setVoChanged(true);
    } //-- void setIndexCapRate(java.math.BigDecimal) 

    /**
     * Method setIndexCapRateGuarTermSets the value of field
     * 'indexCapRateGuarTerm'.
     * 
     * @param indexCapRateGuarTerm the value of field
     * 'indexCapRateGuarTerm'.
     */
    public void setIndexCapRateGuarTerm(int indexCapRateGuarTerm)
    {
        this._indexCapRateGuarTerm = indexCapRateGuarTerm;
        
        super.setVoChanged(true);
        this._has_indexCapRateGuarTerm = true;
    } //-- void setIndexCapRateGuarTerm(int) 

    /**
     * Method setIndexMarginSets the value of field 'indexMargin'.
     * 
     * @param indexMargin the value of field 'indexMargin'.
     */
    public void setIndexMargin(java.math.BigDecimal indexMargin)
    {
        this._indexMargin = indexMargin;
        
        super.setVoChanged(true);
    } //-- void setIndexMargin(java.math.BigDecimal) 

    /**
     * Method setInterestRateAsOf1231Sets the value of field
     * 'interestRateAsOf1231'.
     * 
     * @param interestRateAsOf1231 the value of field
     * 'interestRateAsOf1231'.
     */
    public void setInterestRateAsOf1231(java.math.BigDecimal interestRateAsOf1231)
    {
        this._interestRateAsOf1231 = interestRateAsOf1231;
        
        super.setVoChanged(true);
    } //-- void setInterestRateAsOf1231(java.math.BigDecimal) 

    /**
     * Method setIssueDateSets the value of field 'issueDate'.
     * 
     * @param issueDate the value of field 'issueDate'.
     */
    public void setIssueDate(java.lang.String issueDate)
    {
        this._issueDate = issueDate;
        
        super.setVoChanged(true);
    } //-- void setIssueDate(java.lang.String) 

    /**
     * Method setIssueStateSets the value of field 'issueState'.
     * 
     * @param issueState the value of field 'issueState'.
     */
    public void setIssueState(java.lang.String issueState)
    {
        this._issueState = issueState;
        
        super.setVoChanged(true);
    } //-- void setIssueState(java.lang.String) 

    /**
     * Method setJointAnnuitantAgeSets the value of field
     * 'jointAnnuitantAge'.
     * 
     * @param jointAnnuitantAge the value of field
     * 'jointAnnuitantAge'.
     */
    public void setJointAnnuitantAge(int jointAnnuitantAge)
    {
        this._jointAnnuitantAge = jointAnnuitantAge;
        
        super.setVoChanged(true);
        this._has_jointAnnuitantAge = true;
    } //-- void setJointAnnuitantAge(int) 

    /**
     * Method setJointAnnuitantSexSets the value of field
     * 'jointAnnuitantSex'.
     * 
     * @param jointAnnuitantSex the value of field
     * 'jointAnnuitantSex'.
     */
    public void setJointAnnuitantSex(java.lang.String jointAnnuitantSex)
    {
        this._jointAnnuitantSex = jointAnnuitantSex;
        
        super.setVoChanged(true);
    } //-- void setJointAnnuitantSex(java.lang.String) 

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
     * Method setMinGuarRateSets the value of field 'minGuarRate'.
     * 
     * @param minGuarRate the value of field 'minGuarRate'.
     */
    public void setMinGuarRate(java.math.BigDecimal minGuarRate)
    {
        this._minGuarRate = minGuarRate;
        
        super.setVoChanged(true);
    } //-- void setMinGuarRate(java.math.BigDecimal) 

    /**
     * Method setNextAnniversaryDateSets the value of field
     * 'nextAnniversaryDate'.
     * 
     * @param nextAnniversaryDate the value of field
     * 'nextAnniversaryDate'.
     */
    public void setNextAnniversaryDate(java.lang.String nextAnniversaryDate)
    {
        this._nextAnniversaryDate = nextAnniversaryDate;
        
        super.setVoChanged(true);
    } //-- void setNextAnniversaryDate(java.lang.String) 

    /**
     * Method setOwnerIssueAgeSets the value of field
     * 'ownerIssueAge'.
     * 
     * @param ownerIssueAge the value of field 'ownerIssueAge'.
     */
    public void setOwnerIssueAge(int ownerIssueAge)
    {
        this._ownerIssueAge = ownerIssueAge;
        
        super.setVoChanged(true);
        this._has_ownerIssueAge = true;
    } //-- void setOwnerIssueAge(int) 

    /**
     * Method setOwnerSexSets the value of field 'ownerSex'.
     * 
     * @param ownerSex the value of field 'ownerSex'.
     */
    public void setOwnerSex(java.lang.String ownerSex)
    {
        this._ownerSex = ownerSex;
        
        super.setVoChanged(true);
    } //-- void setOwnerSex(java.lang.String) 

    /**
     * Method setParticipationRateSets the value of field
     * 'participationRate'.
     * 
     * @param participationRate the value of field
     * 'participationRate'.
     */
    public void setParticipationRate(java.math.BigDecimal participationRate)
    {
        this._participationRate = participationRate;
        
        super.setVoChanged(true);
    } //-- void setParticipationRate(java.math.BigDecimal) 

    /**
     * Method setPlannedAllocationSets the value of field
     * 'plannedAllocation'.
     * 
     * @param plannedAllocation the value of field
     * 'plannedAllocation'.
     */
    public void setPlannedAllocation(java.math.BigDecimal plannedAllocation)
    {
        this._plannedAllocation = plannedAllocation;
        
        super.setVoChanged(true);
    } //-- void setPlannedAllocation(java.math.BigDecimal) 

    /**
     * Method setPolicyDateSets the value of field 'policyDate'.
     * 
     * @param policyDate the value of field 'policyDate'.
     */
    public void setPolicyDate(java.lang.String policyDate)
    {
        this._policyDate = policyDate;
        
        super.setVoChanged(true);
    } //-- void setPolicyDate(java.lang.String) 

    /**
     * Method setPolicyDurationSets the value of field
     * 'policyDuration'.
     * 
     * @param policyDuration the value of field 'policyDuration'.
     */
    public void setPolicyDuration(int policyDuration)
    {
        this._policyDuration = policyDuration;
        
        super.setVoChanged(true);
        this._has_policyDuration = true;
    } //-- void setPolicyDuration(int) 

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
     * Method setPremiumBonusAmountSets the value of field
     * 'premiumBonusAmount'.
     * 
     * @param premiumBonusAmount the value of field
     * 'premiumBonusAmount'.
     */
    public void setPremiumBonusAmount(java.math.BigDecimal premiumBonusAmount)
    {
        this._premiumBonusAmount = premiumBonusAmount;
        
        super.setVoChanged(true);
    } //-- void setPremiumBonusAmount(java.math.BigDecimal) 

    /**
     * Method setPriorAnnivAccountValueSets the value of field
     * 'priorAnnivAccountValue'.
     * 
     * @param priorAnnivAccountValue the value of field
     * 'priorAnnivAccountValue'.
     */
    public void setPriorAnnivAccountValue(java.math.BigDecimal priorAnnivAccountValue)
    {
        this._priorAnnivAccountValue = priorAnnivAccountValue;
        
        super.setVoChanged(true);
    } //-- void setPriorAnnivAccountValue(java.math.BigDecimal) 

    /**
     * Method setPriorAnnivIndexValueSets the value of field
     * 'priorAnnivIndexValue'.
     * 
     * @param priorAnnivIndexValue the value of field
     * 'priorAnnivIndexValue'.
     */
    public void setPriorAnnivIndexValue(java.math.BigDecimal priorAnnivIndexValue)
    {
        this._priorAnnivIndexValue = priorAnnivIndexValue;
        
        super.setVoChanged(true);
    } //-- void setPriorAnnivIndexValue(java.math.BigDecimal) 

    /**
     * Method setPriorYearEndAcctValueSets the value of field
     * 'priorYearEndAcctValue'.
     * 
     * @param priorYearEndAcctValue the value of field
     * 'priorYearEndAcctValue'.
     */
    public void setPriorYearEndAcctValue(java.math.BigDecimal priorYearEndAcctValue)
    {
        this._priorYearEndAcctValue = priorYearEndAcctValue;
        
        super.setVoChanged(true);
    } //-- void setPriorYearEndAcctValue(java.math.BigDecimal) 

    /**
     * Method setPriorYearEndCashSurrenderValueSets the value of
     * field 'priorYearEndCashSurrenderValue'.
     * 
     * @param priorYearEndCashSurrenderValue the value of field
     * 'priorYearEndCashSurrenderValue'.
     */
    public void setPriorYearEndCashSurrenderValue(java.math.BigDecimal priorYearEndCashSurrenderValue)
    {
        this._priorYearEndCashSurrenderValue = priorYearEndCashSurrenderValue;
        
        super.setVoChanged(true);
    } //-- void setPriorYearEndCashSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setPriorYearEndMinAcctValueSets the value of field
     * 'priorYearEndMinAcctValue'.
     * 
     * @param priorYearEndMinAcctValue the value of field
     * 'priorYearEndMinAcctValue'.
     */
    public void setPriorYearEndMinAcctValue(java.math.BigDecimal priorYearEndMinAcctValue)
    {
        this._priorYearEndMinAcctValue = priorYearEndMinAcctValue;
        
        super.setVoChanged(true);
    } //-- void setPriorYearEndMinAcctValue(java.math.BigDecimal) 

    /**
     * Method setPriorYearSurrenderValueSets the value of field
     * 'priorYearSurrenderValue'.
     * 
     * @param priorYearSurrenderValue the value of field
     * 'priorYearSurrenderValue'.
     */
    public void setPriorYearSurrenderValue(java.math.BigDecimal priorYearSurrenderValue)
    {
        this._priorYearSurrenderValue = priorYearSurrenderValue;
        
        super.setVoChanged(true);
    } //-- void setPriorYearSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setQualifiedIndicatorSets the value of field
     * 'qualifiedIndicator'.
     * 
     * @param qualifiedIndicator the value of field
     * 'qualifiedIndicator'.
     */
    public void setQualifiedIndicator(java.lang.String qualifiedIndicator)
    {
        this._qualifiedIndicator = qualifiedIndicator;
        
        super.setVoChanged(true);
    } //-- void setQualifiedIndicator(java.lang.String) 

    /**
     * Method setQualifiedTypeSets the value of field
     * 'qualifiedType'.
     * 
     * @param qualifiedType the value of field 'qualifiedType'.
     */
    public void setQualifiedType(java.lang.String qualifiedType)
    {
        this._qualifiedType = qualifiedType;
        
        super.setVoChanged(true);
    } //-- void setQualifiedType(java.lang.String) 

    /**
     * Method setRebalanceAmountSets the value of field
     * 'rebalanceAmount'.
     * 
     * @param rebalanceAmount the value of field 'rebalanceAmount'.
     */
    public void setRebalanceAmount(java.math.BigDecimal rebalanceAmount)
    {
        this._rebalanceAmount = rebalanceAmount;
        
        super.setVoChanged(true);
    } //-- void setRebalanceAmount(java.math.BigDecimal) 

    /**
     * Method setResidentStateOfOwnerSets the value of field
     * 'residentStateOfOwner'.
     * 
     * @param residentStateOfOwner the value of field
     * 'residentStateOfOwner'.
     */
    public void setResidentStateOfOwner(java.lang.String residentStateOfOwner)
    {
        this._residentStateOfOwner = residentStateOfOwner;
        
        super.setVoChanged(true);
    } //-- void setResidentStateOfOwner(java.lang.String) 

    /**
     * Method setRiderNameSets the value of field 'riderName'.
     * 
     * @param riderName the value of field 'riderName'.
     */
    public void setRiderName(java.lang.String riderName)
    {
        this._riderName = riderName;
        
        super.setVoChanged(true);
    } //-- void setRiderName(java.lang.String) 

    /**
     * Method setSegmentStatusSets the value of field
     * 'segmentStatus'.
     * 
     * @param segmentStatus the value of field 'segmentStatus'.
     */
    public void setSegmentStatus(java.lang.String segmentStatus)
    {
        this._segmentStatus = segmentStatus;
        
        super.setVoChanged(true);
    } //-- void setSegmentStatus(java.lang.String) 

    /**
     * Method setStartingMVAIndexRateSets the value of field
     * 'startingMVAIndexRate'.
     * 
     * @param startingMVAIndexRate the value of field
     * 'startingMVAIndexRate'.
     */
    public void setStartingMVAIndexRate(java.math.BigDecimal startingMVAIndexRate)
    {
        this._startingMVAIndexRate = startingMVAIndexRate;
        
        super.setVoChanged(true);
    } //-- void setStartingMVAIndexRate(java.math.BigDecimal) 

    /**
     * Method setSubBucketDetailVO
     * 
     * @param index
     * @param vSubBucketDetailVO
     */
    public void setSubBucketDetailVO(int index, edit.common.vo.SubBucketDetailVO vSubBucketDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _subBucketDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSubBucketDetailVO.setParentVO(this.getClass(), this);
        _subBucketDetailVOList.setElementAt(vSubBucketDetailVO, index);
    } //-- void setSubBucketDetailVO(int, edit.common.vo.SubBucketDetailVO) 

    /**
     * Method setSubBucketDetailVO
     * 
     * @param subBucketDetailVOArray
     */
    public void setSubBucketDetailVO(edit.common.vo.SubBucketDetailVO[] subBucketDetailVOArray)
    {
        //-- copy array
        _subBucketDetailVOList.removeAllElements();
        for (int i = 0; i < subBucketDetailVOArray.length; i++) {
            subBucketDetailVOArray[i].setParentVO(this.getClass(), this);
            _subBucketDetailVOList.addElement(subBucketDetailVOArray[i]);
        }
    } //-- void setSubBucketDetailVO(edit.common.vo.SubBucketDetailVO) 

    /**
     * Method setTotalMinAccountValueSets the value of field
     * 'totalMinAccountValue'.
     * 
     * @param totalMinAccountValue the value of field
     * 'totalMinAccountValue'.
     */
    public void setTotalMinAccountValue(java.math.BigDecimal totalMinAccountValue)
    {
        this._totalMinAccountValue = totalMinAccountValue;
        
        super.setVoChanged(true);
    } //-- void setTotalMinAccountValue(java.math.BigDecimal) 

    /**
     * Method setWithdrawalsSincePriorAnnivSets the value of field
     * 'withdrawalsSincePriorAnniv'.
     * 
     * @param withdrawalsSincePriorAnniv the value of field
     * 'withdrawalsSincePriorAnniv'.
     */
    public void setWithdrawalsSincePriorAnniv(java.math.BigDecimal withdrawalsSincePriorAnniv)
    {
        this._withdrawalsSincePriorAnniv = withdrawalsSincePriorAnniv;
        
        super.setVoChanged(true);
    } //-- void setWithdrawalsSincePriorAnniv(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EquityIndexHedgeDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EquityIndexHedgeDetailVO) Unmarshaller.unmarshal(edit.common.vo.EquityIndexHedgeDetailVO.class, reader);
    } //-- edit.common.vo.EquityIndexHedgeDetailVO unmarshal(java.io.Reader) 

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
