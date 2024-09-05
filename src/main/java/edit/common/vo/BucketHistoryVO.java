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
 * Class BucketHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class BucketHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bucketHistoryPK
     */
    private long _bucketHistoryPK;

    /**
     * keeps track of state for field: _bucketHistoryPK
     */
    private boolean _has_bucketHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _bucketFK
     */
    private long _bucketFK;

    /**
     * keeps track of state for field: _bucketFK
     */
    private boolean _has_bucketFK;

    /**
     * Field _dollars
     */
    private java.math.BigDecimal _dollars;

    /**
     * Field _units
     */
    private java.math.BigDecimal _units;

    /**
     * Field _previousValuationDate
     */
    private java.lang.String _previousValuationDate;

    /**
     * Field _previousValue
     */
    private java.math.BigDecimal _previousValue;

    /**
     * Field _previousPayoutValue
     */
    private java.math.BigDecimal _previousPayoutValue;

    /**
     * Field _interestEarnedGuaranteed
     */
    private java.math.BigDecimal _interestEarnedGuaranteed;

    /**
     * Field _interestEarnedCurrent
     */
    private java.math.BigDecimal _interestEarnedCurrent;

    /**
     * Field _cumDollars
     */
    private java.math.BigDecimal _cumDollars;

    /**
     * Field _cumUnits
     */
    private java.math.BigDecimal _cumUnits;

    /**
     * Field _toFromStatus
     */
    private java.lang.String _toFromStatus;

    /**
     * Field _bonusInterestEarned
     */
    private java.math.BigDecimal _bonusInterestEarned;

    /**
     * Field _previousGuaranteeValue
     */
    private java.math.BigDecimal _previousGuaranteeValue;

    /**
     * Field _bonusAmount
     */
    private java.math.BigDecimal _bonusAmount;

    /**
     * Field _priorRebalanceAmount
     */
    private java.math.BigDecimal _priorRebalanceAmount;

    /**
     * Field _bucketLiability
     */
    private java.math.BigDecimal _bucketLiability;

    /**
     * Field _interimAccountIndicator
     */
    private java.lang.String _interimAccountIndicator;

    /**
     * Field _guarCumValue
     */
    private java.math.BigDecimal _guarCumValue;

    /**
     * Field _hedgeFundInvestmentFK
     */
    private long _hedgeFundInvestmentFK;

    /**
     * keeps track of state for field: _hedgeFundInvestmentFK
     */
    private boolean _has_hedgeFundInvestmentFK;

    /**
     * Field _holdingAccountIndicator
     */
    private java.lang.String _holdingAccountIndicator;

    /**
     * Field _previousPriorBucketRate
     */
    private java.math.BigDecimal _previousPriorBucketRate;

    /**
     * Field _previousLastRenewalDate
     */
    private java.lang.String _previousLastRenewalDate;

    /**
     * Field _previousBucketRate
     */
    private java.math.BigDecimal _previousBucketRate;

    /**
     * Field _loanPrincipalDollars
     */
    private java.math.BigDecimal _loanPrincipalDollars;

    /**
     * Field _loanInterestDollars
     */
    private java.math.BigDecimal _loanInterestDollars;

    /**
     * Field _previousLoanCumDollars
     */
    private java.math.BigDecimal _previousLoanCumDollars;

    /**
     * Field _previousLoanInterestDollars
     */
    private java.math.BigDecimal _previousLoanInterestDollars;

    /**
     * Field _previousLoanPrincipalRemaining
     */
    private java.math.BigDecimal _previousLoanPrincipalRemaining;

    /**
     * Field _cumLoanDollars
     */
    private java.math.BigDecimal _cumLoanDollars;

    /**
     * Field _loanDollarsToFromStatus
     */
    private java.lang.String _loanDollarsToFromStatus;

    /**
     * Field _loanInterestLiability
     */
    private java.math.BigDecimal _loanInterestLiability;

    /**
     * Field _prevInterestPaidThroughDate
     */
    private java.lang.String _prevInterestPaidThroughDate;

    /**
     * Field _prevUnearnedLoanInterest
     */
    private java.math.BigDecimal _prevUnearnedLoanInterest;

    /**
     * Field _unearnedInterestCredit
     */
    private java.math.BigDecimal _unearnedInterestCredit;

    /**
     * Field _overShortAmount
     */
    private java.math.BigDecimal _overShortAmount;

    /**
     * Field _bucketChargeHistoryVOList
     */
    private java.util.Vector _bucketChargeHistoryVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BucketHistoryVO() {
        super();
        _bucketChargeHistoryVOList = new Vector();
    } //-- edit.common.vo.BucketHistoryVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBucketChargeHistoryVO
     * 
     * @param vBucketChargeHistoryVO
     */
    public void addBucketChargeHistoryVO(edit.common.vo.BucketChargeHistoryVO vBucketChargeHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketChargeHistoryVO.setParentVO(this.getClass(), this);
        _bucketChargeHistoryVOList.addElement(vBucketChargeHistoryVO);
    } //-- void addBucketChargeHistoryVO(edit.common.vo.BucketChargeHistoryVO) 

    /**
     * Method addBucketChargeHistoryVO
     * 
     * @param index
     * @param vBucketChargeHistoryVO
     */
    public void addBucketChargeHistoryVO(int index, edit.common.vo.BucketChargeHistoryVO vBucketChargeHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketChargeHistoryVO.setParentVO(this.getClass(), this);
        _bucketChargeHistoryVOList.insertElementAt(vBucketChargeHistoryVO, index);
    } //-- void addBucketChargeHistoryVO(int, edit.common.vo.BucketChargeHistoryVO) 

    /**
     * Method enumerateBucketChargeHistoryVO
     */
    public java.util.Enumeration enumerateBucketChargeHistoryVO()
    {
        return _bucketChargeHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateBucketChargeHistoryVO() 

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
        
        if (obj instanceof BucketHistoryVO) {
        
            BucketHistoryVO temp = (BucketHistoryVO)obj;
            if (this._bucketHistoryPK != temp._bucketHistoryPK)
                return false;
            if (this._has_bucketHistoryPK != temp._has_bucketHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._bucketFK != temp._bucketFK)
                return false;
            if (this._has_bucketFK != temp._has_bucketFK)
                return false;
            if (this._dollars != null) {
                if (temp._dollars == null) return false;
                else if (!(this._dollars.equals(temp._dollars))) 
                    return false;
            }
            else if (temp._dollars != null)
                return false;
            if (this._units != null) {
                if (temp._units == null) return false;
                else if (!(this._units.equals(temp._units))) 
                    return false;
            }
            else if (temp._units != null)
                return false;
            if (this._previousValuationDate != null) {
                if (temp._previousValuationDate == null) return false;
                else if (!(this._previousValuationDate.equals(temp._previousValuationDate))) 
                    return false;
            }
            else if (temp._previousValuationDate != null)
                return false;
            if (this._previousValue != null) {
                if (temp._previousValue == null) return false;
                else if (!(this._previousValue.equals(temp._previousValue))) 
                    return false;
            }
            else if (temp._previousValue != null)
                return false;
            if (this._previousPayoutValue != null) {
                if (temp._previousPayoutValue == null) return false;
                else if (!(this._previousPayoutValue.equals(temp._previousPayoutValue))) 
                    return false;
            }
            else if (temp._previousPayoutValue != null)
                return false;
            if (this._interestEarnedGuaranteed != null) {
                if (temp._interestEarnedGuaranteed == null) return false;
                else if (!(this._interestEarnedGuaranteed.equals(temp._interestEarnedGuaranteed))) 
                    return false;
            }
            else if (temp._interestEarnedGuaranteed != null)
                return false;
            if (this._interestEarnedCurrent != null) {
                if (temp._interestEarnedCurrent == null) return false;
                else if (!(this._interestEarnedCurrent.equals(temp._interestEarnedCurrent))) 
                    return false;
            }
            else if (temp._interestEarnedCurrent != null)
                return false;
            if (this._cumDollars != null) {
                if (temp._cumDollars == null) return false;
                else if (!(this._cumDollars.equals(temp._cumDollars))) 
                    return false;
            }
            else if (temp._cumDollars != null)
                return false;
            if (this._cumUnits != null) {
                if (temp._cumUnits == null) return false;
                else if (!(this._cumUnits.equals(temp._cumUnits))) 
                    return false;
            }
            else if (temp._cumUnits != null)
                return false;
            if (this._toFromStatus != null) {
                if (temp._toFromStatus == null) return false;
                else if (!(this._toFromStatus.equals(temp._toFromStatus))) 
                    return false;
            }
            else if (temp._toFromStatus != null)
                return false;
            if (this._bonusInterestEarned != null) {
                if (temp._bonusInterestEarned == null) return false;
                else if (!(this._bonusInterestEarned.equals(temp._bonusInterestEarned))) 
                    return false;
            }
            else if (temp._bonusInterestEarned != null)
                return false;
            if (this._previousGuaranteeValue != null) {
                if (temp._previousGuaranteeValue == null) return false;
                else if (!(this._previousGuaranteeValue.equals(temp._previousGuaranteeValue))) 
                    return false;
            }
            else if (temp._previousGuaranteeValue != null)
                return false;
            if (this._bonusAmount != null) {
                if (temp._bonusAmount == null) return false;
                else if (!(this._bonusAmount.equals(temp._bonusAmount))) 
                    return false;
            }
            else if (temp._bonusAmount != null)
                return false;
            if (this._priorRebalanceAmount != null) {
                if (temp._priorRebalanceAmount == null) return false;
                else if (!(this._priorRebalanceAmount.equals(temp._priorRebalanceAmount))) 
                    return false;
            }
            else if (temp._priorRebalanceAmount != null)
                return false;
            if (this._bucketLiability != null) {
                if (temp._bucketLiability == null) return false;
                else if (!(this._bucketLiability.equals(temp._bucketLiability))) 
                    return false;
            }
            else if (temp._bucketLiability != null)
                return false;
            if (this._interimAccountIndicator != null) {
                if (temp._interimAccountIndicator == null) return false;
                else if (!(this._interimAccountIndicator.equals(temp._interimAccountIndicator))) 
                    return false;
            }
            else if (temp._interimAccountIndicator != null)
                return false;
            if (this._guarCumValue != null) {
                if (temp._guarCumValue == null) return false;
                else if (!(this._guarCumValue.equals(temp._guarCumValue))) 
                    return false;
            }
            else if (temp._guarCumValue != null)
                return false;
            if (this._hedgeFundInvestmentFK != temp._hedgeFundInvestmentFK)
                return false;
            if (this._has_hedgeFundInvestmentFK != temp._has_hedgeFundInvestmentFK)
                return false;
            if (this._holdingAccountIndicator != null) {
                if (temp._holdingAccountIndicator == null) return false;
                else if (!(this._holdingAccountIndicator.equals(temp._holdingAccountIndicator))) 
                    return false;
            }
            else if (temp._holdingAccountIndicator != null)
                return false;
            if (this._previousPriorBucketRate != null) {
                if (temp._previousPriorBucketRate == null) return false;
                else if (!(this._previousPriorBucketRate.equals(temp._previousPriorBucketRate))) 
                    return false;
            }
            else if (temp._previousPriorBucketRate != null)
                return false;
            if (this._previousLastRenewalDate != null) {
                if (temp._previousLastRenewalDate == null) return false;
                else if (!(this._previousLastRenewalDate.equals(temp._previousLastRenewalDate))) 
                    return false;
            }
            else if (temp._previousLastRenewalDate != null)
                return false;
            if (this._previousBucketRate != null) {
                if (temp._previousBucketRate == null) return false;
                else if (!(this._previousBucketRate.equals(temp._previousBucketRate))) 
                    return false;
            }
            else if (temp._previousBucketRate != null)
                return false;
            if (this._loanPrincipalDollars != null) {
                if (temp._loanPrincipalDollars == null) return false;
                else if (!(this._loanPrincipalDollars.equals(temp._loanPrincipalDollars))) 
                    return false;
            }
            else if (temp._loanPrincipalDollars != null)
                return false;
            if (this._loanInterestDollars != null) {
                if (temp._loanInterestDollars == null) return false;
                else if (!(this._loanInterestDollars.equals(temp._loanInterestDollars))) 
                    return false;
            }
            else if (temp._loanInterestDollars != null)
                return false;
            if (this._previousLoanCumDollars != null) {
                if (temp._previousLoanCumDollars == null) return false;
                else if (!(this._previousLoanCumDollars.equals(temp._previousLoanCumDollars))) 
                    return false;
            }
            else if (temp._previousLoanCumDollars != null)
                return false;
            if (this._previousLoanInterestDollars != null) {
                if (temp._previousLoanInterestDollars == null) return false;
                else if (!(this._previousLoanInterestDollars.equals(temp._previousLoanInterestDollars))) 
                    return false;
            }
            else if (temp._previousLoanInterestDollars != null)
                return false;
            if (this._previousLoanPrincipalRemaining != null) {
                if (temp._previousLoanPrincipalRemaining == null) return false;
                else if (!(this._previousLoanPrincipalRemaining.equals(temp._previousLoanPrincipalRemaining))) 
                    return false;
            }
            else if (temp._previousLoanPrincipalRemaining != null)
                return false;
            if (this._cumLoanDollars != null) {
                if (temp._cumLoanDollars == null) return false;
                else if (!(this._cumLoanDollars.equals(temp._cumLoanDollars))) 
                    return false;
            }
            else if (temp._cumLoanDollars != null)
                return false;
            if (this._loanDollarsToFromStatus != null) {
                if (temp._loanDollarsToFromStatus == null) return false;
                else if (!(this._loanDollarsToFromStatus.equals(temp._loanDollarsToFromStatus))) 
                    return false;
            }
            else if (temp._loanDollarsToFromStatus != null)
                return false;
            if (this._loanInterestLiability != null) {
                if (temp._loanInterestLiability == null) return false;
                else if (!(this._loanInterestLiability.equals(temp._loanInterestLiability))) 
                    return false;
            }
            else if (temp._loanInterestLiability != null)
                return false;
            if (this._prevInterestPaidThroughDate != null) {
                if (temp._prevInterestPaidThroughDate == null) return false;
                else if (!(this._prevInterestPaidThroughDate.equals(temp._prevInterestPaidThroughDate))) 
                    return false;
            }
            else if (temp._prevInterestPaidThroughDate != null)
                return false;
            if (this._prevUnearnedLoanInterest != null) {
                if (temp._prevUnearnedLoanInterest == null) return false;
                else if (!(this._prevUnearnedLoanInterest.equals(temp._prevUnearnedLoanInterest))) 
                    return false;
            }
            else if (temp._prevUnearnedLoanInterest != null)
                return false;
            if (this._unearnedInterestCredit != null) {
                if (temp._unearnedInterestCredit == null) return false;
                else if (!(this._unearnedInterestCredit.equals(temp._unearnedInterestCredit))) 
                    return false;
            }
            else if (temp._unearnedInterestCredit != null)
                return false;
            if (this._overShortAmount != null) {
                if (temp._overShortAmount == null) return false;
                else if (!(this._overShortAmount.equals(temp._overShortAmount))) 
                    return false;
            }
            else if (temp._overShortAmount != null)
                return false;
            if (this._bucketChargeHistoryVOList != null) {
                if (temp._bucketChargeHistoryVOList == null) return false;
                else if (!(this._bucketChargeHistoryVOList.equals(temp._bucketChargeHistoryVOList))) 
                    return false;
            }
            else if (temp._bucketChargeHistoryVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusAmountReturns the value of field
     * 'bonusAmount'.
     * 
     * @return the value of field 'bonusAmount'.
     */
    public java.math.BigDecimal getBonusAmount()
    {
        return this._bonusAmount;
    } //-- java.math.BigDecimal getBonusAmount() 

    /**
     * Method getBonusInterestEarnedReturns the value of field
     * 'bonusInterestEarned'.
     * 
     * @return the value of field 'bonusInterestEarned'.
     */
    public java.math.BigDecimal getBonusInterestEarned()
    {
        return this._bonusInterestEarned;
    } //-- java.math.BigDecimal getBonusInterestEarned() 

    /**
     * Method getBucketChargeHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BucketChargeHistoryVO getBucketChargeHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketChargeHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BucketChargeHistoryVO) _bucketChargeHistoryVOList.elementAt(index);
    } //-- edit.common.vo.BucketChargeHistoryVO getBucketChargeHistoryVO(int) 

    /**
     * Method getBucketChargeHistoryVO
     */
    public edit.common.vo.BucketChargeHistoryVO[] getBucketChargeHistoryVO()
    {
        int size = _bucketChargeHistoryVOList.size();
        edit.common.vo.BucketChargeHistoryVO[] mArray = new edit.common.vo.BucketChargeHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BucketChargeHistoryVO) _bucketChargeHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BucketChargeHistoryVO[] getBucketChargeHistoryVO() 

    /**
     * Method getBucketChargeHistoryVOCount
     */
    public int getBucketChargeHistoryVOCount()
    {
        return _bucketChargeHistoryVOList.size();
    } //-- int getBucketChargeHistoryVOCount() 

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
     * Method getBucketHistoryPKReturns the value of field
     * 'bucketHistoryPK'.
     * 
     * @return the value of field 'bucketHistoryPK'.
     */
    public long getBucketHistoryPK()
    {
        return this._bucketHistoryPK;
    } //-- long getBucketHistoryPK() 

    /**
     * Method getBucketLiabilityReturns the value of field
     * 'bucketLiability'.
     * 
     * @return the value of field 'bucketLiability'.
     */
    public java.math.BigDecimal getBucketLiability()
    {
        return this._bucketLiability;
    } //-- java.math.BigDecimal getBucketLiability() 

    /**
     * Method getCumDollarsReturns the value of field 'cumDollars'.
     * 
     * @return the value of field 'cumDollars'.
     */
    public java.math.BigDecimal getCumDollars()
    {
        return this._cumDollars;
    } //-- java.math.BigDecimal getCumDollars() 

    /**
     * Method getCumLoanDollarsReturns the value of field
     * 'cumLoanDollars'.
     * 
     * @return the value of field 'cumLoanDollars'.
     */
    public java.math.BigDecimal getCumLoanDollars()
    {
        return this._cumLoanDollars;
    } //-- java.math.BigDecimal getCumLoanDollars() 

    /**
     * Method getCumUnitsReturns the value of field 'cumUnits'.
     * 
     * @return the value of field 'cumUnits'.
     */
    public java.math.BigDecimal getCumUnits()
    {
        return this._cumUnits;
    } //-- java.math.BigDecimal getCumUnits() 

    /**
     * Method getDollarsReturns the value of field 'dollars'.
     * 
     * @return the value of field 'dollars'.
     */
    public java.math.BigDecimal getDollars()
    {
        return this._dollars;
    } //-- java.math.BigDecimal getDollars() 

    /**
     * Method getEDITTrxHistoryFKReturns the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @return the value of field 'EDITTrxHistoryFK'.
     */
    public long getEDITTrxHistoryFK()
    {
        return this._EDITTrxHistoryFK;
    } //-- long getEDITTrxHistoryFK() 

    /**
     * Method getGuarCumValueReturns the value of field
     * 'guarCumValue'.
     * 
     * @return the value of field 'guarCumValue'.
     */
    public java.math.BigDecimal getGuarCumValue()
    {
        return this._guarCumValue;
    } //-- java.math.BigDecimal getGuarCumValue() 

    /**
     * Method getHedgeFundInvestmentFKReturns the value of field
     * 'hedgeFundInvestmentFK'.
     * 
     * @return the value of field 'hedgeFundInvestmentFK'.
     */
    public long getHedgeFundInvestmentFK()
    {
        return this._hedgeFundInvestmentFK;
    } //-- long getHedgeFundInvestmentFK() 

    /**
     * Method getHoldingAccountIndicatorReturns the value of field
     * 'holdingAccountIndicator'.
     * 
     * @return the value of field 'holdingAccountIndicator'.
     */
    public java.lang.String getHoldingAccountIndicator()
    {
        return this._holdingAccountIndicator;
    } //-- java.lang.String getHoldingAccountIndicator() 

    /**
     * Method getInterestEarnedCurrentReturns the value of field
     * 'interestEarnedCurrent'.
     * 
     * @return the value of field 'interestEarnedCurrent'.
     */
    public java.math.BigDecimal getInterestEarnedCurrent()
    {
        return this._interestEarnedCurrent;
    } //-- java.math.BigDecimal getInterestEarnedCurrent() 

    /**
     * Method getInterestEarnedGuaranteedReturns the value of field
     * 'interestEarnedGuaranteed'.
     * 
     * @return the value of field 'interestEarnedGuaranteed'.
     */
    public java.math.BigDecimal getInterestEarnedGuaranteed()
    {
        return this._interestEarnedGuaranteed;
    } //-- java.math.BigDecimal getInterestEarnedGuaranteed() 

    /**
     * Method getInterimAccountIndicatorReturns the value of field
     * 'interimAccountIndicator'.
     * 
     * @return the value of field 'interimAccountIndicator'.
     */
    public java.lang.String getInterimAccountIndicator()
    {
        return this._interimAccountIndicator;
    } //-- java.lang.String getInterimAccountIndicator() 

    /**
     * Method getLoanDollarsToFromStatusReturns the value of field
     * 'loanDollarsToFromStatus'.
     * 
     * @return the value of field 'loanDollarsToFromStatus'.
     */
    public java.lang.String getLoanDollarsToFromStatus()
    {
        return this._loanDollarsToFromStatus;
    } //-- java.lang.String getLoanDollarsToFromStatus() 

    /**
     * Method getLoanInterestDollarsReturns the value of field
     * 'loanInterestDollars'.
     * 
     * @return the value of field 'loanInterestDollars'.
     */
    public java.math.BigDecimal getLoanInterestDollars()
    {
        return this._loanInterestDollars;
    } //-- java.math.BigDecimal getLoanInterestDollars() 

    /**
     * Method getLoanInterestLiabilityReturns the value of field
     * 'loanInterestLiability'.
     * 
     * @return the value of field 'loanInterestLiability'.
     */
    public java.math.BigDecimal getLoanInterestLiability()
    {
        return this._loanInterestLiability;
    } //-- java.math.BigDecimal getLoanInterestLiability() 

    /**
     * Method getLoanPrincipalDollarsReturns the value of field
     * 'loanPrincipalDollars'.
     * 
     * @return the value of field 'loanPrincipalDollars'.
     */
    public java.math.BigDecimal getLoanPrincipalDollars()
    {
        return this._loanPrincipalDollars;
    } //-- java.math.BigDecimal getLoanPrincipalDollars() 

    /**
     * Method getOverShortAmountReturns the value of field
     * 'overShortAmount'.
     * 
     * @return the value of field 'overShortAmount'.
     */
    public java.math.BigDecimal getOverShortAmount()
    {
        return this._overShortAmount;
    } //-- java.math.BigDecimal getOverShortAmount() 

    /**
     * Method getPrevInterestPaidThroughDateReturns the value of
     * field 'prevInterestPaidThroughDate'.
     * 
     * @return the value of field 'prevInterestPaidThroughDate'.
     */
    public java.lang.String getPrevInterestPaidThroughDate()
    {
        return this._prevInterestPaidThroughDate;
    } //-- java.lang.String getPrevInterestPaidThroughDate() 

    /**
     * Method getPrevUnearnedLoanInterestReturns the value of field
     * 'prevUnearnedLoanInterest'.
     * 
     * @return the value of field 'prevUnearnedLoanInterest'.
     */
    public java.math.BigDecimal getPrevUnearnedLoanInterest()
    {
        return this._prevUnearnedLoanInterest;
    } //-- java.math.BigDecimal getPrevUnearnedLoanInterest() 

    /**
     * Method getPreviousBucketRateReturns the value of field
     * 'previousBucketRate'.
     * 
     * @return the value of field 'previousBucketRate'.
     */
    public java.math.BigDecimal getPreviousBucketRate()
    {
        return this._previousBucketRate;
    } //-- java.math.BigDecimal getPreviousBucketRate() 

    /**
     * Method getPreviousGuaranteeValueReturns the value of field
     * 'previousGuaranteeValue'.
     * 
     * @return the value of field 'previousGuaranteeValue'.
     */
    public java.math.BigDecimal getPreviousGuaranteeValue()
    {
        return this._previousGuaranteeValue;
    } //-- java.math.BigDecimal getPreviousGuaranteeValue() 

    /**
     * Method getPreviousLastRenewalDateReturns the value of field
     * 'previousLastRenewalDate'.
     * 
     * @return the value of field 'previousLastRenewalDate'.
     */
    public java.lang.String getPreviousLastRenewalDate()
    {
        return this._previousLastRenewalDate;
    } //-- java.lang.String getPreviousLastRenewalDate() 

    /**
     * Method getPreviousLoanCumDollarsReturns the value of field
     * 'previousLoanCumDollars'.
     * 
     * @return the value of field 'previousLoanCumDollars'.
     */
    public java.math.BigDecimal getPreviousLoanCumDollars()
    {
        return this._previousLoanCumDollars;
    } //-- java.math.BigDecimal getPreviousLoanCumDollars() 

    /**
     * Method getPreviousLoanInterestDollarsReturns the value of
     * field 'previousLoanInterestDollars'.
     * 
     * @return the value of field 'previousLoanInterestDollars'.
     */
    public java.math.BigDecimal getPreviousLoanInterestDollars()
    {
        return this._previousLoanInterestDollars;
    } //-- java.math.BigDecimal getPreviousLoanInterestDollars() 

    /**
     * Method getPreviousLoanPrincipalRemainingReturns the value of
     * field 'previousLoanPrincipalRemaining'.
     * 
     * @return the value of field 'previousLoanPrincipalRemaining'.
     */
    public java.math.BigDecimal getPreviousLoanPrincipalRemaining()
    {
        return this._previousLoanPrincipalRemaining;
    } //-- java.math.BigDecimal getPreviousLoanPrincipalRemaining() 

    /**
     * Method getPreviousPayoutValueReturns the value of field
     * 'previousPayoutValue'.
     * 
     * @return the value of field 'previousPayoutValue'.
     */
    public java.math.BigDecimal getPreviousPayoutValue()
    {
        return this._previousPayoutValue;
    } //-- java.math.BigDecimal getPreviousPayoutValue() 

    /**
     * Method getPreviousPriorBucketRateReturns the value of field
     * 'previousPriorBucketRate'.
     * 
     * @return the value of field 'previousPriorBucketRate'.
     */
    public java.math.BigDecimal getPreviousPriorBucketRate()
    {
        return this._previousPriorBucketRate;
    } //-- java.math.BigDecimal getPreviousPriorBucketRate() 

    /**
     * Method getPreviousValuationDateReturns the value of field
     * 'previousValuationDate'.
     * 
     * @return the value of field 'previousValuationDate'.
     */
    public java.lang.String getPreviousValuationDate()
    {
        return this._previousValuationDate;
    } //-- java.lang.String getPreviousValuationDate() 

    /**
     * Method getPreviousValueReturns the value of field
     * 'previousValue'.
     * 
     * @return the value of field 'previousValue'.
     */
    public java.math.BigDecimal getPreviousValue()
    {
        return this._previousValue;
    } //-- java.math.BigDecimal getPreviousValue() 

    /**
     * Method getPriorRebalanceAmountReturns the value of field
     * 'priorRebalanceAmount'.
     * 
     * @return the value of field 'priorRebalanceAmount'.
     */
    public java.math.BigDecimal getPriorRebalanceAmount()
    {
        return this._priorRebalanceAmount;
    } //-- java.math.BigDecimal getPriorRebalanceAmount() 

    /**
     * Method getToFromStatusReturns the value of field
     * 'toFromStatus'.
     * 
     * @return the value of field 'toFromStatus'.
     */
    public java.lang.String getToFromStatus()
    {
        return this._toFromStatus;
    } //-- java.lang.String getToFromStatus() 

    /**
     * Method getUnearnedInterestCreditReturns the value of field
     * 'unearnedInterestCredit'.
     * 
     * @return the value of field 'unearnedInterestCredit'.
     */
    public java.math.BigDecimal getUnearnedInterestCredit()
    {
        return this._unearnedInterestCredit;
    } //-- java.math.BigDecimal getUnearnedInterestCredit() 

    /**
     * Method getUnitsReturns the value of field 'units'.
     * 
     * @return the value of field 'units'.
     */
    public java.math.BigDecimal getUnits()
    {
        return this._units;
    } //-- java.math.BigDecimal getUnits() 

    /**
     * Method hasBucketFK
     */
    public boolean hasBucketFK()
    {
        return this._has_bucketFK;
    } //-- boolean hasBucketFK() 

    /**
     * Method hasBucketHistoryPK
     */
    public boolean hasBucketHistoryPK()
    {
        return this._has_bucketHistoryPK;
    } //-- boolean hasBucketHistoryPK() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasHedgeFundInvestmentFK
     */
    public boolean hasHedgeFundInvestmentFK()
    {
        return this._has_hedgeFundInvestmentFK;
    } //-- boolean hasHedgeFundInvestmentFK() 

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
     * Method removeAllBucketChargeHistoryVO
     */
    public void removeAllBucketChargeHistoryVO()
    {
        _bucketChargeHistoryVOList.removeAllElements();
    } //-- void removeAllBucketChargeHistoryVO() 

    /**
     * Method removeBucketChargeHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BucketChargeHistoryVO removeBucketChargeHistoryVO(int index)
    {
        java.lang.Object obj = _bucketChargeHistoryVOList.elementAt(index);
        _bucketChargeHistoryVOList.removeElementAt(index);
        return (edit.common.vo.BucketChargeHistoryVO) obj;
    } //-- edit.common.vo.BucketChargeHistoryVO removeBucketChargeHistoryVO(int) 

    /**
     * Method setBonusAmountSets the value of field 'bonusAmount'.
     * 
     * @param bonusAmount the value of field 'bonusAmount'.
     */
    public void setBonusAmount(java.math.BigDecimal bonusAmount)
    {
        this._bonusAmount = bonusAmount;
        
        super.setVoChanged(true);
    } //-- void setBonusAmount(java.math.BigDecimal) 

    /**
     * Method setBonusInterestEarnedSets the value of field
     * 'bonusInterestEarned'.
     * 
     * @param bonusInterestEarned the value of field
     * 'bonusInterestEarned'.
     */
    public void setBonusInterestEarned(java.math.BigDecimal bonusInterestEarned)
    {
        this._bonusInterestEarned = bonusInterestEarned;
        
        super.setVoChanged(true);
    } //-- void setBonusInterestEarned(java.math.BigDecimal) 

    /**
     * Method setBucketChargeHistoryVO
     * 
     * @param index
     * @param vBucketChargeHistoryVO
     */
    public void setBucketChargeHistoryVO(int index, edit.common.vo.BucketChargeHistoryVO vBucketChargeHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketChargeHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBucketChargeHistoryVO.setParentVO(this.getClass(), this);
        _bucketChargeHistoryVOList.setElementAt(vBucketChargeHistoryVO, index);
    } //-- void setBucketChargeHistoryVO(int, edit.common.vo.BucketChargeHistoryVO) 

    /**
     * Method setBucketChargeHistoryVO
     * 
     * @param bucketChargeHistoryVOArray
     */
    public void setBucketChargeHistoryVO(edit.common.vo.BucketChargeHistoryVO[] bucketChargeHistoryVOArray)
    {
        //-- copy array
        _bucketChargeHistoryVOList.removeAllElements();
        for (int i = 0; i < bucketChargeHistoryVOArray.length; i++) {
            bucketChargeHistoryVOArray[i].setParentVO(this.getClass(), this);
            _bucketChargeHistoryVOList.addElement(bucketChargeHistoryVOArray[i]);
        }
    } //-- void setBucketChargeHistoryVO(edit.common.vo.BucketChargeHistoryVO) 

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
     * Method setBucketHistoryPKSets the value of field
     * 'bucketHistoryPK'.
     * 
     * @param bucketHistoryPK the value of field 'bucketHistoryPK'.
     */
    public void setBucketHistoryPK(long bucketHistoryPK)
    {
        this._bucketHistoryPK = bucketHistoryPK;
        
        super.setVoChanged(true);
        this._has_bucketHistoryPK = true;
    } //-- void setBucketHistoryPK(long) 

    /**
     * Method setBucketLiabilitySets the value of field
     * 'bucketLiability'.
     * 
     * @param bucketLiability the value of field 'bucketLiability'.
     */
    public void setBucketLiability(java.math.BigDecimal bucketLiability)
    {
        this._bucketLiability = bucketLiability;
        
        super.setVoChanged(true);
    } //-- void setBucketLiability(java.math.BigDecimal) 

    /**
     * Method setCumDollarsSets the value of field 'cumDollars'.
     * 
     * @param cumDollars the value of field 'cumDollars'.
     */
    public void setCumDollars(java.math.BigDecimal cumDollars)
    {
        this._cumDollars = cumDollars;
        
        super.setVoChanged(true);
    } //-- void setCumDollars(java.math.BigDecimal) 

    /**
     * Method setCumLoanDollarsSets the value of field
     * 'cumLoanDollars'.
     * 
     * @param cumLoanDollars the value of field 'cumLoanDollars'.
     */
    public void setCumLoanDollars(java.math.BigDecimal cumLoanDollars)
    {
        this._cumLoanDollars = cumLoanDollars;
        
        super.setVoChanged(true);
    } //-- void setCumLoanDollars(java.math.BigDecimal) 

    /**
     * Method setCumUnitsSets the value of field 'cumUnits'.
     * 
     * @param cumUnits the value of field 'cumUnits'.
     */
    public void setCumUnits(java.math.BigDecimal cumUnits)
    {
        this._cumUnits = cumUnits;
        
        super.setVoChanged(true);
    } //-- void setCumUnits(java.math.BigDecimal) 

    /**
     * Method setDollarsSets the value of field 'dollars'.
     * 
     * @param dollars the value of field 'dollars'.
     */
    public void setDollars(java.math.BigDecimal dollars)
    {
        this._dollars = dollars;
        
        super.setVoChanged(true);
    } //-- void setDollars(java.math.BigDecimal) 

    /**
     * Method setEDITTrxHistoryFKSets the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @param EDITTrxHistoryFK the value of field 'EDITTrxHistoryFK'
     */
    public void setEDITTrxHistoryFK(long EDITTrxHistoryFK)
    {
        this._EDITTrxHistoryFK = EDITTrxHistoryFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxHistoryFK = true;
    } //-- void setEDITTrxHistoryFK(long) 

    /**
     * Method setGuarCumValueSets the value of field
     * 'guarCumValue'.
     * 
     * @param guarCumValue the value of field 'guarCumValue'.
     */
    public void setGuarCumValue(java.math.BigDecimal guarCumValue)
    {
        this._guarCumValue = guarCumValue;
        
        super.setVoChanged(true);
    } //-- void setGuarCumValue(java.math.BigDecimal) 

    /**
     * Method setHedgeFundInvestmentFKSets the value of field
     * 'hedgeFundInvestmentFK'.
     * 
     * @param hedgeFundInvestmentFK the value of field
     * 'hedgeFundInvestmentFK'.
     */
    public void setHedgeFundInvestmentFK(long hedgeFundInvestmentFK)
    {
        this._hedgeFundInvestmentFK = hedgeFundInvestmentFK;
        
        super.setVoChanged(true);
        this._has_hedgeFundInvestmentFK = true;
    } //-- void setHedgeFundInvestmentFK(long) 

    /**
     * Method setHoldingAccountIndicatorSets the value of field
     * 'holdingAccountIndicator'.
     * 
     * @param holdingAccountIndicator the value of field
     * 'holdingAccountIndicator'.
     */
    public void setHoldingAccountIndicator(java.lang.String holdingAccountIndicator)
    {
        this._holdingAccountIndicator = holdingAccountIndicator;
        
        super.setVoChanged(true);
    } //-- void setHoldingAccountIndicator(java.lang.String) 

    /**
     * Method setInterestEarnedCurrentSets the value of field
     * 'interestEarnedCurrent'.
     * 
     * @param interestEarnedCurrent the value of field
     * 'interestEarnedCurrent'.
     */
    public void setInterestEarnedCurrent(java.math.BigDecimal interestEarnedCurrent)
    {
        this._interestEarnedCurrent = interestEarnedCurrent;
        
        super.setVoChanged(true);
    } //-- void setInterestEarnedCurrent(java.math.BigDecimal) 

    /**
     * Method setInterestEarnedGuaranteedSets the value of field
     * 'interestEarnedGuaranteed'.
     * 
     * @param interestEarnedGuaranteed the value of field
     * 'interestEarnedGuaranteed'.
     */
    public void setInterestEarnedGuaranteed(java.math.BigDecimal interestEarnedGuaranteed)
    {
        this._interestEarnedGuaranteed = interestEarnedGuaranteed;
        
        super.setVoChanged(true);
    } //-- void setInterestEarnedGuaranteed(java.math.BigDecimal) 

    /**
     * Method setInterimAccountIndicatorSets the value of field
     * 'interimAccountIndicator'.
     * 
     * @param interimAccountIndicator the value of field
     * 'interimAccountIndicator'.
     */
    public void setInterimAccountIndicator(java.lang.String interimAccountIndicator)
    {
        this._interimAccountIndicator = interimAccountIndicator;
        
        super.setVoChanged(true);
    } //-- void setInterimAccountIndicator(java.lang.String) 

    /**
     * Method setLoanDollarsToFromStatusSets the value of field
     * 'loanDollarsToFromStatus'.
     * 
     * @param loanDollarsToFromStatus the value of field
     * 'loanDollarsToFromStatus'.
     */
    public void setLoanDollarsToFromStatus(java.lang.String loanDollarsToFromStatus)
    {
        this._loanDollarsToFromStatus = loanDollarsToFromStatus;
        
        super.setVoChanged(true);
    } //-- void setLoanDollarsToFromStatus(java.lang.String) 

    /**
     * Method setLoanInterestDollarsSets the value of field
     * 'loanInterestDollars'.
     * 
     * @param loanInterestDollars the value of field
     * 'loanInterestDollars'.
     */
    public void setLoanInterestDollars(java.math.BigDecimal loanInterestDollars)
    {
        this._loanInterestDollars = loanInterestDollars;
        
        super.setVoChanged(true);
    } //-- void setLoanInterestDollars(java.math.BigDecimal) 

    /**
     * Method setLoanInterestLiabilitySets the value of field
     * 'loanInterestLiability'.
     * 
     * @param loanInterestLiability the value of field
     * 'loanInterestLiability'.
     */
    public void setLoanInterestLiability(java.math.BigDecimal loanInterestLiability)
    {
        this._loanInterestLiability = loanInterestLiability;
        
        super.setVoChanged(true);
    } //-- void setLoanInterestLiability(java.math.BigDecimal) 

    /**
     * Method setLoanPrincipalDollarsSets the value of field
     * 'loanPrincipalDollars'.
     * 
     * @param loanPrincipalDollars the value of field
     * 'loanPrincipalDollars'.
     */
    public void setLoanPrincipalDollars(java.math.BigDecimal loanPrincipalDollars)
    {
        this._loanPrincipalDollars = loanPrincipalDollars;
        
        super.setVoChanged(true);
    } //-- void setLoanPrincipalDollars(java.math.BigDecimal) 

    /**
     * Method setOverShortAmountSets the value of field
     * 'overShortAmount'.
     * 
     * @param overShortAmount the value of field 'overShortAmount'.
     */
    public void setOverShortAmount(java.math.BigDecimal overShortAmount)
    {
        this._overShortAmount = overShortAmount;
        
        super.setVoChanged(true);
    } //-- void setOverShortAmount(java.math.BigDecimal) 

    /**
     * Method setPrevInterestPaidThroughDateSets the value of field
     * 'prevInterestPaidThroughDate'.
     * 
     * @param prevInterestPaidThroughDate the value of field
     * 'prevInterestPaidThroughDate'.
     */
    public void setPrevInterestPaidThroughDate(java.lang.String prevInterestPaidThroughDate)
    {
        this._prevInterestPaidThroughDate = prevInterestPaidThroughDate;
        
        super.setVoChanged(true);
    } //-- void setPrevInterestPaidThroughDate(java.lang.String) 

    /**
     * Method setPrevUnearnedLoanInterestSets the value of field
     * 'prevUnearnedLoanInterest'.
     * 
     * @param prevUnearnedLoanInterest the value of field
     * 'prevUnearnedLoanInterest'.
     */
    public void setPrevUnearnedLoanInterest(java.math.BigDecimal prevUnearnedLoanInterest)
    {
        this._prevUnearnedLoanInterest = prevUnearnedLoanInterest;
        
        super.setVoChanged(true);
    } //-- void setPrevUnearnedLoanInterest(java.math.BigDecimal) 

    /**
     * Method setPreviousBucketRateSets the value of field
     * 'previousBucketRate'.
     * 
     * @param previousBucketRate the value of field
     * 'previousBucketRate'.
     */
    public void setPreviousBucketRate(java.math.BigDecimal previousBucketRate)
    {
        this._previousBucketRate = previousBucketRate;
        
        super.setVoChanged(true);
    } //-- void setPreviousBucketRate(java.math.BigDecimal) 

    /**
     * Method setPreviousGuaranteeValueSets the value of field
     * 'previousGuaranteeValue'.
     * 
     * @param previousGuaranteeValue the value of field
     * 'previousGuaranteeValue'.
     */
    public void setPreviousGuaranteeValue(java.math.BigDecimal previousGuaranteeValue)
    {
        this._previousGuaranteeValue = previousGuaranteeValue;
        
        super.setVoChanged(true);
    } //-- void setPreviousGuaranteeValue(java.math.BigDecimal) 

    /**
     * Method setPreviousLastRenewalDateSets the value of field
     * 'previousLastRenewalDate'.
     * 
     * @param previousLastRenewalDate the value of field
     * 'previousLastRenewalDate'.
     */
    public void setPreviousLastRenewalDate(java.lang.String previousLastRenewalDate)
    {
        this._previousLastRenewalDate = previousLastRenewalDate;
        
        super.setVoChanged(true);
    } //-- void setPreviousLastRenewalDate(java.lang.String) 

    /**
     * Method setPreviousLoanCumDollarsSets the value of field
     * 'previousLoanCumDollars'.
     * 
     * @param previousLoanCumDollars the value of field
     * 'previousLoanCumDollars'.
     */
    public void setPreviousLoanCumDollars(java.math.BigDecimal previousLoanCumDollars)
    {
        this._previousLoanCumDollars = previousLoanCumDollars;
        
        super.setVoChanged(true);
    } //-- void setPreviousLoanCumDollars(java.math.BigDecimal) 

    /**
     * Method setPreviousLoanInterestDollarsSets the value of field
     * 'previousLoanInterestDollars'.
     * 
     * @param previousLoanInterestDollars the value of field
     * 'previousLoanInterestDollars'.
     */
    public void setPreviousLoanInterestDollars(java.math.BigDecimal previousLoanInterestDollars)
    {
        this._previousLoanInterestDollars = previousLoanInterestDollars;
        
        super.setVoChanged(true);
    } //-- void setPreviousLoanInterestDollars(java.math.BigDecimal) 

    /**
     * Method setPreviousLoanPrincipalRemainingSets the value of
     * field 'previousLoanPrincipalRemaining'.
     * 
     * @param previousLoanPrincipalRemaining the value of field
     * 'previousLoanPrincipalRemaining'.
     */
    public void setPreviousLoanPrincipalRemaining(java.math.BigDecimal previousLoanPrincipalRemaining)
    {
        this._previousLoanPrincipalRemaining = previousLoanPrincipalRemaining;
        
        super.setVoChanged(true);
    } //-- void setPreviousLoanPrincipalRemaining(java.math.BigDecimal) 

    /**
     * Method setPreviousPayoutValueSets the value of field
     * 'previousPayoutValue'.
     * 
     * @param previousPayoutValue the value of field
     * 'previousPayoutValue'.
     */
    public void setPreviousPayoutValue(java.math.BigDecimal previousPayoutValue)
    {
        this._previousPayoutValue = previousPayoutValue;
        
        super.setVoChanged(true);
    } //-- void setPreviousPayoutValue(java.math.BigDecimal) 

    /**
     * Method setPreviousPriorBucketRateSets the value of field
     * 'previousPriorBucketRate'.
     * 
     * @param previousPriorBucketRate the value of field
     * 'previousPriorBucketRate'.
     */
    public void setPreviousPriorBucketRate(java.math.BigDecimal previousPriorBucketRate)
    {
        this._previousPriorBucketRate = previousPriorBucketRate;
        
        super.setVoChanged(true);
    } //-- void setPreviousPriorBucketRate(java.math.BigDecimal) 

    /**
     * Method setPreviousValuationDateSets the value of field
     * 'previousValuationDate'.
     * 
     * @param previousValuationDate the value of field
     * 'previousValuationDate'.
     */
    public void setPreviousValuationDate(java.lang.String previousValuationDate)
    {
        this._previousValuationDate = previousValuationDate;
        
        super.setVoChanged(true);
    } //-- void setPreviousValuationDate(java.lang.String) 

    /**
     * Method setPreviousValueSets the value of field
     * 'previousValue'.
     * 
     * @param previousValue the value of field 'previousValue'.
     */
    public void setPreviousValue(java.math.BigDecimal previousValue)
    {
        this._previousValue = previousValue;
        
        super.setVoChanged(true);
    } //-- void setPreviousValue(java.math.BigDecimal) 

    /**
     * Method setPriorRebalanceAmountSets the value of field
     * 'priorRebalanceAmount'.
     * 
     * @param priorRebalanceAmount the value of field
     * 'priorRebalanceAmount'.
     */
    public void setPriorRebalanceAmount(java.math.BigDecimal priorRebalanceAmount)
    {
        this._priorRebalanceAmount = priorRebalanceAmount;
        
        super.setVoChanged(true);
    } //-- void setPriorRebalanceAmount(java.math.BigDecimal) 

    /**
     * Method setToFromStatusSets the value of field
     * 'toFromStatus'.
     * 
     * @param toFromStatus the value of field 'toFromStatus'.
     */
    public void setToFromStatus(java.lang.String toFromStatus)
    {
        this._toFromStatus = toFromStatus;
        
        super.setVoChanged(true);
    } //-- void setToFromStatus(java.lang.String) 

    /**
     * Method setUnearnedInterestCreditSets the value of field
     * 'unearnedInterestCredit'.
     * 
     * @param unearnedInterestCredit the value of field
     * 'unearnedInterestCredit'.
     */
    public void setUnearnedInterestCredit(java.math.BigDecimal unearnedInterestCredit)
    {
        this._unearnedInterestCredit = unearnedInterestCredit;
        
        super.setVoChanged(true);
    } //-- void setUnearnedInterestCredit(java.math.BigDecimal) 

    /**
     * Method setUnitsSets the value of field 'units'.
     * 
     * @param units the value of field 'units'.
     */
    public void setUnits(java.math.BigDecimal units)
    {
        this._units = units;
        
        super.setVoChanged(true);
    } //-- void setUnits(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BucketHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BucketHistoryVO) Unmarshaller.unmarshal(edit.common.vo.BucketHistoryVO.class, reader);
    } //-- edit.common.vo.BucketHistoryVO unmarshal(java.io.Reader) 

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
