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
 * Class BucketVO.
 * 
 * @version $Revision$ $Date$
 */
public class BucketVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bucketPK
     */
    private long _bucketPK;

    /**
     * keeps track of state for field: _bucketPK
     */
    private boolean _has_bucketPK;

    /**
     * Field _investmentFK
     */
    private long _investmentFK;

    /**
     * keeps track of state for field: _investmentFK
     */
    private boolean _has_investmentFK;

    /**
     * Field _cumDollars
     */
    private java.math.BigDecimal _cumDollars;

    /**
     * Field _cumUnits
     */
    private java.math.BigDecimal _cumUnits;

    /**
     * Field _depositDate
     */
    private java.lang.String _depositDate;

    /**
     * Field _depositAmount
     */
    private java.math.BigDecimal _depositAmount;

    /**
     * Field _lastValuationDate
     */
    private java.lang.String _lastValuationDate;

    /**
     * Field _interestRateOverride
     */
    private java.math.BigDecimal _interestRateOverride;

    /**
     * Field _bucketInterestRate
     */
    private java.math.BigDecimal _bucketInterestRate;

    /**
     * Field _priorBucketRate
     */
    private java.math.BigDecimal _priorBucketRate;

    /**
     * Field _durationOverride
     */
    private int _durationOverride;

    /**
     * keeps track of state for field: _durationOverride
     */
    private boolean _has_durationOverride;

    /**
     * Field _payoutUnits
     */
    private java.math.BigDecimal _payoutUnits;

    /**
     * Field _payoutDollars
     */
    private java.math.BigDecimal _payoutDollars;

    /**
     * Field _renewalDate
     */
    private java.lang.String _renewalDate;

    /**
     * Field _lastRenewalDate
     */
    private java.lang.String _lastRenewalDate;

    /**
     * Field _guarCumValue
     */
    private java.math.BigDecimal _guarCumValue;

    /**
     * Field _bonusAmount
     */
    private java.math.BigDecimal _bonusAmount;

    /**
     * Field _indexCapRate
     */
    private java.math.BigDecimal _indexCapRate;

    /**
     * Field _rebalanceAmount
     */
    private java.math.BigDecimal _rebalanceAmount;

    /**
     * Field _depositAmountGain
     */
    private java.math.BigDecimal _depositAmountGain;

    /**
     * Field _bonusIntRate
     */
    private java.math.BigDecimal _bonusIntRate;

    /**
     * Field _bonusIntRateDur
     */
    private int _bonusIntRateDur;

    /**
     * keeps track of state for field: _bonusIntRateDur
     */
    private boolean _has_bonusIntRateDur;

    /**
     * Field _unearnedInterest
     */
    private java.math.BigDecimal _unearnedInterest;

    /**
     * Field _lockupEndDate
     */
    private java.lang.String _lockupEndDate;

    /**
     * Field _loanCumDollars
     */
    private java.math.BigDecimal _loanCumDollars;

    /**
     * Field _loanPrincipalRemaining
     */
    private java.math.BigDecimal _loanPrincipalRemaining;

    /**
     * Field _bucketSourceCT
     */
    private java.lang.String _bucketSourceCT;

    /**
     * Field _loanInterestRate
     */
    private java.math.BigDecimal _loanInterestRate;

    /**
     * Field _previousLoanInterestRate
     */
    private java.math.BigDecimal _previousLoanInterestRate;

    /**
     * Field _accruedLoanInterest
     */
    private java.math.BigDecimal _accruedLoanInterest;

    /**
     * Field _unearnedLoanInterest
     */
    private java.math.BigDecimal _unearnedLoanInterest;

    /**
     * Field _interestPaidThroughDate
     */
    private java.lang.String _interestPaidThroughDate;

    /**
     * Field _billedLoanInterest
     */
    private java.math.BigDecimal _billedLoanInterest;

    /**
     * Field _annualizedSubBucketVOList
     */
    private java.util.Vector _annualizedSubBucketVOList;

    /**
     * Field _bucketAllocationVOList
     */
    private java.util.Vector _bucketAllocationVOList;

    /**
     * Field _bucketHistoryVOList
     */
    private java.util.Vector _bucketHistoryVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BucketVO() {
        super();
        _annualizedSubBucketVOList = new Vector();
        _bucketAllocationVOList = new Vector();
        _bucketHistoryVOList = new Vector();
    } //-- edit.common.vo.BucketVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAnnualizedSubBucketVO
     * 
     * @param vAnnualizedSubBucketVO
     */
    public void addAnnualizedSubBucketVO(edit.common.vo.AnnualizedSubBucketVO vAnnualizedSubBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAnnualizedSubBucketVO.setParentVO(this.getClass(), this);
        _annualizedSubBucketVOList.addElement(vAnnualizedSubBucketVO);
    } //-- void addAnnualizedSubBucketVO(edit.common.vo.AnnualizedSubBucketVO) 

    /**
     * Method addAnnualizedSubBucketVO
     * 
     * @param index
     * @param vAnnualizedSubBucketVO
     */
    public void addAnnualizedSubBucketVO(int index, edit.common.vo.AnnualizedSubBucketVO vAnnualizedSubBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAnnualizedSubBucketVO.setParentVO(this.getClass(), this);
        _annualizedSubBucketVOList.insertElementAt(vAnnualizedSubBucketVO, index);
    } //-- void addAnnualizedSubBucketVO(int, edit.common.vo.AnnualizedSubBucketVO) 

    /**
     * Method addBucketAllocationVO
     * 
     * @param vBucketAllocationVO
     */
    public void addBucketAllocationVO(edit.common.vo.BucketAllocationVO vBucketAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketAllocationVO.setParentVO(this.getClass(), this);
        _bucketAllocationVOList.addElement(vBucketAllocationVO);
    } //-- void addBucketAllocationVO(edit.common.vo.BucketAllocationVO) 

    /**
     * Method addBucketAllocationVO
     * 
     * @param index
     * @param vBucketAllocationVO
     */
    public void addBucketAllocationVO(int index, edit.common.vo.BucketAllocationVO vBucketAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketAllocationVO.setParentVO(this.getClass(), this);
        _bucketAllocationVOList.insertElementAt(vBucketAllocationVO, index);
    } //-- void addBucketAllocationVO(int, edit.common.vo.BucketAllocationVO) 

    /**
     * Method addBucketHistoryVO
     * 
     * @param vBucketHistoryVO
     */
    public void addBucketHistoryVO(edit.common.vo.BucketHistoryVO vBucketHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketHistoryVO.setParentVO(this.getClass(), this);
        _bucketHistoryVOList.addElement(vBucketHistoryVO);
    } //-- void addBucketHistoryVO(edit.common.vo.BucketHistoryVO) 

    /**
     * Method addBucketHistoryVO
     * 
     * @param index
     * @param vBucketHistoryVO
     */
    public void addBucketHistoryVO(int index, edit.common.vo.BucketHistoryVO vBucketHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketHistoryVO.setParentVO(this.getClass(), this);
        _bucketHistoryVOList.insertElementAt(vBucketHistoryVO, index);
    } //-- void addBucketHistoryVO(int, edit.common.vo.BucketHistoryVO) 

    /**
     * Method enumerateAnnualizedSubBucketVO
     */
    public java.util.Enumeration enumerateAnnualizedSubBucketVO()
    {
        return _annualizedSubBucketVOList.elements();
    } //-- java.util.Enumeration enumerateAnnualizedSubBucketVO() 

    /**
     * Method enumerateBucketAllocationVO
     */
    public java.util.Enumeration enumerateBucketAllocationVO()
    {
        return _bucketAllocationVOList.elements();
    } //-- java.util.Enumeration enumerateBucketAllocationVO() 

    /**
     * Method enumerateBucketHistoryVO
     */
    public java.util.Enumeration enumerateBucketHistoryVO()
    {
        return _bucketHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateBucketHistoryVO() 

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
        
        if (obj instanceof BucketVO) {
        
            BucketVO temp = (BucketVO)obj;
            if (this._bucketPK != temp._bucketPK)
                return false;
            if (this._has_bucketPK != temp._has_bucketPK)
                return false;
            if (this._investmentFK != temp._investmentFK)
                return false;
            if (this._has_investmentFK != temp._has_investmentFK)
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
            if (this._depositDate != null) {
                if (temp._depositDate == null) return false;
                else if (!(this._depositDate.equals(temp._depositDate))) 
                    return false;
            }
            else if (temp._depositDate != null)
                return false;
            if (this._depositAmount != null) {
                if (temp._depositAmount == null) return false;
                else if (!(this._depositAmount.equals(temp._depositAmount))) 
                    return false;
            }
            else if (temp._depositAmount != null)
                return false;
            if (this._lastValuationDate != null) {
                if (temp._lastValuationDate == null) return false;
                else if (!(this._lastValuationDate.equals(temp._lastValuationDate))) 
                    return false;
            }
            else if (temp._lastValuationDate != null)
                return false;
            if (this._interestRateOverride != null) {
                if (temp._interestRateOverride == null) return false;
                else if (!(this._interestRateOverride.equals(temp._interestRateOverride))) 
                    return false;
            }
            else if (temp._interestRateOverride != null)
                return false;
            if (this._bucketInterestRate != null) {
                if (temp._bucketInterestRate == null) return false;
                else if (!(this._bucketInterestRate.equals(temp._bucketInterestRate))) 
                    return false;
            }
            else if (temp._bucketInterestRate != null)
                return false;
            if (this._priorBucketRate != null) {
                if (temp._priorBucketRate == null) return false;
                else if (!(this._priorBucketRate.equals(temp._priorBucketRate))) 
                    return false;
            }
            else if (temp._priorBucketRate != null)
                return false;
            if (this._durationOverride != temp._durationOverride)
                return false;
            if (this._has_durationOverride != temp._has_durationOverride)
                return false;
            if (this._payoutUnits != null) {
                if (temp._payoutUnits == null) return false;
                else if (!(this._payoutUnits.equals(temp._payoutUnits))) 
                    return false;
            }
            else if (temp._payoutUnits != null)
                return false;
            if (this._payoutDollars != null) {
                if (temp._payoutDollars == null) return false;
                else if (!(this._payoutDollars.equals(temp._payoutDollars))) 
                    return false;
            }
            else if (temp._payoutDollars != null)
                return false;
            if (this._renewalDate != null) {
                if (temp._renewalDate == null) return false;
                else if (!(this._renewalDate.equals(temp._renewalDate))) 
                    return false;
            }
            else if (temp._renewalDate != null)
                return false;
            if (this._lastRenewalDate != null) {
                if (temp._lastRenewalDate == null) return false;
                else if (!(this._lastRenewalDate.equals(temp._lastRenewalDate))) 
                    return false;
            }
            else if (temp._lastRenewalDate != null)
                return false;
            if (this._guarCumValue != null) {
                if (temp._guarCumValue == null) return false;
                else if (!(this._guarCumValue.equals(temp._guarCumValue))) 
                    return false;
            }
            else if (temp._guarCumValue != null)
                return false;
            if (this._bonusAmount != null) {
                if (temp._bonusAmount == null) return false;
                else if (!(this._bonusAmount.equals(temp._bonusAmount))) 
                    return false;
            }
            else if (temp._bonusAmount != null)
                return false;
            if (this._indexCapRate != null) {
                if (temp._indexCapRate == null) return false;
                else if (!(this._indexCapRate.equals(temp._indexCapRate))) 
                    return false;
            }
            else if (temp._indexCapRate != null)
                return false;
            if (this._rebalanceAmount != null) {
                if (temp._rebalanceAmount == null) return false;
                else if (!(this._rebalanceAmount.equals(temp._rebalanceAmount))) 
                    return false;
            }
            else if (temp._rebalanceAmount != null)
                return false;
            if (this._depositAmountGain != null) {
                if (temp._depositAmountGain == null) return false;
                else if (!(this._depositAmountGain.equals(temp._depositAmountGain))) 
                    return false;
            }
            else if (temp._depositAmountGain != null)
                return false;
            if (this._bonusIntRate != null) {
                if (temp._bonusIntRate == null) return false;
                else if (!(this._bonusIntRate.equals(temp._bonusIntRate))) 
                    return false;
            }
            else if (temp._bonusIntRate != null)
                return false;
            if (this._bonusIntRateDur != temp._bonusIntRateDur)
                return false;
            if (this._has_bonusIntRateDur != temp._has_bonusIntRateDur)
                return false;
            if (this._unearnedInterest != null) {
                if (temp._unearnedInterest == null) return false;
                else if (!(this._unearnedInterest.equals(temp._unearnedInterest))) 
                    return false;
            }
            else if (temp._unearnedInterest != null)
                return false;
            if (this._lockupEndDate != null) {
                if (temp._lockupEndDate == null) return false;
                else if (!(this._lockupEndDate.equals(temp._lockupEndDate))) 
                    return false;
            }
            else if (temp._lockupEndDate != null)
                return false;
            if (this._loanCumDollars != null) {
                if (temp._loanCumDollars == null) return false;
                else if (!(this._loanCumDollars.equals(temp._loanCumDollars))) 
                    return false;
            }
            else if (temp._loanCumDollars != null)
                return false;
            if (this._loanPrincipalRemaining != null) {
                if (temp._loanPrincipalRemaining == null) return false;
                else if (!(this._loanPrincipalRemaining.equals(temp._loanPrincipalRemaining))) 
                    return false;
            }
            else if (temp._loanPrincipalRemaining != null)
                return false;
            if (this._bucketSourceCT != null) {
                if (temp._bucketSourceCT == null) return false;
                else if (!(this._bucketSourceCT.equals(temp._bucketSourceCT))) 
                    return false;
            }
            else if (temp._bucketSourceCT != null)
                return false;
            if (this._loanInterestRate != null) {
                if (temp._loanInterestRate == null) return false;
                else if (!(this._loanInterestRate.equals(temp._loanInterestRate))) 
                    return false;
            }
            else if (temp._loanInterestRate != null)
                return false;
            if (this._previousLoanInterestRate != null) {
                if (temp._previousLoanInterestRate == null) return false;
                else if (!(this._previousLoanInterestRate.equals(temp._previousLoanInterestRate))) 
                    return false;
            }
            else if (temp._previousLoanInterestRate != null)
                return false;
            if (this._accruedLoanInterest != null) {
                if (temp._accruedLoanInterest == null) return false;
                else if (!(this._accruedLoanInterest.equals(temp._accruedLoanInterest))) 
                    return false;
            }
            else if (temp._accruedLoanInterest != null)
                return false;
            if (this._unearnedLoanInterest != null) {
                if (temp._unearnedLoanInterest == null) return false;
                else if (!(this._unearnedLoanInterest.equals(temp._unearnedLoanInterest))) 
                    return false;
            }
            else if (temp._unearnedLoanInterest != null)
                return false;
            if (this._interestPaidThroughDate != null) {
                if (temp._interestPaidThroughDate == null) return false;
                else if (!(this._interestPaidThroughDate.equals(temp._interestPaidThroughDate))) 
                    return false;
            }
            else if (temp._interestPaidThroughDate != null)
                return false;
            if (this._billedLoanInterest != null) {
                if (temp._billedLoanInterest == null) return false;
                else if (!(this._billedLoanInterest.equals(temp._billedLoanInterest))) 
                    return false;
            }
            else if (temp._billedLoanInterest != null)
                return false;
            if (this._annualizedSubBucketVOList != null) {
                if (temp._annualizedSubBucketVOList == null) return false;
                else if (!(this._annualizedSubBucketVOList.equals(temp._annualizedSubBucketVOList))) 
                    return false;
            }
            else if (temp._annualizedSubBucketVOList != null)
                return false;
            if (this._bucketAllocationVOList != null) {
                if (temp._bucketAllocationVOList == null) return false;
                else if (!(this._bucketAllocationVOList.equals(temp._bucketAllocationVOList))) 
                    return false;
            }
            else if (temp._bucketAllocationVOList != null)
                return false;
            if (this._bucketHistoryVOList != null) {
                if (temp._bucketHistoryVOList == null) return false;
                else if (!(this._bucketHistoryVOList.equals(temp._bucketHistoryVOList))) 
                    return false;
            }
            else if (temp._bucketHistoryVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccruedLoanInterestReturns the value of field
     * 'accruedLoanInterest'.
     * 
     * @return the value of field 'accruedLoanInterest'.
     */
    public java.math.BigDecimal getAccruedLoanInterest()
    {
        return this._accruedLoanInterest;
    } //-- java.math.BigDecimal getAccruedLoanInterest() 

    /**
     * Method getAnnualizedSubBucketVO
     * 
     * @param index
     */
    public edit.common.vo.AnnualizedSubBucketVO getAnnualizedSubBucketVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _annualizedSubBucketVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AnnualizedSubBucketVO) _annualizedSubBucketVOList.elementAt(index);
    } //-- edit.common.vo.AnnualizedSubBucketVO getAnnualizedSubBucketVO(int) 

    /**
     * Method getAnnualizedSubBucketVO
     */
    public edit.common.vo.AnnualizedSubBucketVO[] getAnnualizedSubBucketVO()
    {
        int size = _annualizedSubBucketVOList.size();
        edit.common.vo.AnnualizedSubBucketVO[] mArray = new edit.common.vo.AnnualizedSubBucketVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AnnualizedSubBucketVO) _annualizedSubBucketVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AnnualizedSubBucketVO[] getAnnualizedSubBucketVO() 

    /**
     * Method getAnnualizedSubBucketVOCount
     */
    public int getAnnualizedSubBucketVOCount()
    {
        return _annualizedSubBucketVOList.size();
    } //-- int getAnnualizedSubBucketVOCount() 

    /**
     * Method getBilledLoanInterestReturns the value of field
     * 'billedLoanInterest'.
     * 
     * @return the value of field 'billedLoanInterest'.
     */
    public java.math.BigDecimal getBilledLoanInterest()
    {
        return this._billedLoanInterest;
    } //-- java.math.BigDecimal getBilledLoanInterest() 

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
     * Method getBonusIntRateReturns the value of field
     * 'bonusIntRate'.
     * 
     * @return the value of field 'bonusIntRate'.
     */
    public java.math.BigDecimal getBonusIntRate()
    {
        return this._bonusIntRate;
    } //-- java.math.BigDecimal getBonusIntRate() 

    /**
     * Method getBonusIntRateDurReturns the value of field
     * 'bonusIntRateDur'.
     * 
     * @return the value of field 'bonusIntRateDur'.
     */
    public int getBonusIntRateDur()
    {
        return this._bonusIntRateDur;
    } //-- int getBonusIntRateDur() 

    /**
     * Method getBucketAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.BucketAllocationVO getBucketAllocationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BucketAllocationVO) _bucketAllocationVOList.elementAt(index);
    } //-- edit.common.vo.BucketAllocationVO getBucketAllocationVO(int) 

    /**
     * Method getBucketAllocationVO
     */
    public edit.common.vo.BucketAllocationVO[] getBucketAllocationVO()
    {
        int size = _bucketAllocationVOList.size();
        edit.common.vo.BucketAllocationVO[] mArray = new edit.common.vo.BucketAllocationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BucketAllocationVO) _bucketAllocationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BucketAllocationVO[] getBucketAllocationVO() 

    /**
     * Method getBucketAllocationVOCount
     */
    public int getBucketAllocationVOCount()
    {
        return _bucketAllocationVOList.size();
    } //-- int getBucketAllocationVOCount() 

    /**
     * Method getBucketHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BucketHistoryVO getBucketHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BucketHistoryVO) _bucketHistoryVOList.elementAt(index);
    } //-- edit.common.vo.BucketHistoryVO getBucketHistoryVO(int) 

    /**
     * Method getBucketHistoryVO
     */
    public edit.common.vo.BucketHistoryVO[] getBucketHistoryVO()
    {
        int size = _bucketHistoryVOList.size();
        edit.common.vo.BucketHistoryVO[] mArray = new edit.common.vo.BucketHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BucketHistoryVO) _bucketHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BucketHistoryVO[] getBucketHistoryVO() 

    /**
     * Method getBucketHistoryVOCount
     */
    public int getBucketHistoryVOCount()
    {
        return _bucketHistoryVOList.size();
    } //-- int getBucketHistoryVOCount() 

    /**
     * Method getBucketInterestRateReturns the value of field
     * 'bucketInterestRate'.
     * 
     * @return the value of field 'bucketInterestRate'.
     */
    public java.math.BigDecimal getBucketInterestRate()
    {
        return this._bucketInterestRate;
    } //-- java.math.BigDecimal getBucketInterestRate() 

    /**
     * Method getBucketPKReturns the value of field 'bucketPK'.
     * 
     * @return the value of field 'bucketPK'.
     */
    public long getBucketPK()
    {
        return this._bucketPK;
    } //-- long getBucketPK() 

    /**
     * Method getBucketSourceCTReturns the value of field
     * 'bucketSourceCT'.
     * 
     * @return the value of field 'bucketSourceCT'.
     */
    public java.lang.String getBucketSourceCT()
    {
        return this._bucketSourceCT;
    } //-- java.lang.String getBucketSourceCT() 

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
     * Method getCumUnitsReturns the value of field 'cumUnits'.
     * 
     * @return the value of field 'cumUnits'.
     */
    public java.math.BigDecimal getCumUnits()
    {
        return this._cumUnits;
    } //-- java.math.BigDecimal getCumUnits() 

    /**
     * Method getDepositAmountReturns the value of field
     * 'depositAmount'.
     * 
     * @return the value of field 'depositAmount'.
     */
    public java.math.BigDecimal getDepositAmount()
    {
        return this._depositAmount;
    } //-- java.math.BigDecimal getDepositAmount() 

    /**
     * Method getDepositAmountGainReturns the value of field
     * 'depositAmountGain'.
     * 
     * @return the value of field 'depositAmountGain'.
     */
    public java.math.BigDecimal getDepositAmountGain()
    {
        return this._depositAmountGain;
    } //-- java.math.BigDecimal getDepositAmountGain() 

    /**
     * Method getDepositDateReturns the value of field
     * 'depositDate'.
     * 
     * @return the value of field 'depositDate'.
     */
    public java.lang.String getDepositDate()
    {
        return this._depositDate;
    } //-- java.lang.String getDepositDate() 

    /**
     * Method getDurationOverrideReturns the value of field
     * 'durationOverride'.
     * 
     * @return the value of field 'durationOverride'.
     */
    public int getDurationOverride()
    {
        return this._durationOverride;
    } //-- int getDurationOverride() 

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
     * Method getInterestPaidThroughDateReturns the value of field
     * 'interestPaidThroughDate'.
     * 
     * @return the value of field 'interestPaidThroughDate'.
     */
    public java.lang.String getInterestPaidThroughDate()
    {
        return this._interestPaidThroughDate;
    } //-- java.lang.String getInterestPaidThroughDate() 

    /**
     * Method getInterestRateOverrideReturns the value of field
     * 'interestRateOverride'.
     * 
     * @return the value of field 'interestRateOverride'.
     */
    public java.math.BigDecimal getInterestRateOverride()
    {
        return this._interestRateOverride;
    } //-- java.math.BigDecimal getInterestRateOverride() 

    /**
     * Method getInvestmentFKReturns the value of field
     * 'investmentFK'.
     * 
     * @return the value of field 'investmentFK'.
     */
    public long getInvestmentFK()
    {
        return this._investmentFK;
    } //-- long getInvestmentFK() 

    /**
     * Method getLastRenewalDateReturns the value of field
     * 'lastRenewalDate'.
     * 
     * @return the value of field 'lastRenewalDate'.
     */
    public java.lang.String getLastRenewalDate()
    {
        return this._lastRenewalDate;
    } //-- java.lang.String getLastRenewalDate() 

    /**
     * Method getLastValuationDateReturns the value of field
     * 'lastValuationDate'.
     * 
     * @return the value of field 'lastValuationDate'.
     */
    public java.lang.String getLastValuationDate()
    {
        return this._lastValuationDate;
    } //-- java.lang.String getLastValuationDate() 

    /**
     * Method getLoanCumDollarsReturns the value of field
     * 'loanCumDollars'.
     * 
     * @return the value of field 'loanCumDollars'.
     */
    public java.math.BigDecimal getLoanCumDollars()
    {
        return this._loanCumDollars;
    } //-- java.math.BigDecimal getLoanCumDollars() 

    /**
     * Method getLoanInterestRateReturns the value of field
     * 'loanInterestRate'.
     * 
     * @return the value of field 'loanInterestRate'.
     */
    public java.math.BigDecimal getLoanInterestRate()
    {
        return this._loanInterestRate;
    } //-- java.math.BigDecimal getLoanInterestRate() 

    /**
     * Method getLoanPrincipalRemainingReturns the value of field
     * 'loanPrincipalRemaining'.
     * 
     * @return the value of field 'loanPrincipalRemaining'.
     */
    public java.math.BigDecimal getLoanPrincipalRemaining()
    {
        return this._loanPrincipalRemaining;
    } //-- java.math.BigDecimal getLoanPrincipalRemaining() 

    /**
     * Method getLockupEndDateReturns the value of field
     * 'lockupEndDate'.
     * 
     * @return the value of field 'lockupEndDate'.
     */
    public java.lang.String getLockupEndDate()
    {
        return this._lockupEndDate;
    } //-- java.lang.String getLockupEndDate() 

    /**
     * Method getPayoutDollarsReturns the value of field
     * 'payoutDollars'.
     * 
     * @return the value of field 'payoutDollars'.
     */
    public java.math.BigDecimal getPayoutDollars()
    {
        return this._payoutDollars;
    } //-- java.math.BigDecimal getPayoutDollars() 

    /**
     * Method getPayoutUnitsReturns the value of field
     * 'payoutUnits'.
     * 
     * @return the value of field 'payoutUnits'.
     */
    public java.math.BigDecimal getPayoutUnits()
    {
        return this._payoutUnits;
    } //-- java.math.BigDecimal getPayoutUnits() 

    /**
     * Method getPreviousLoanInterestRateReturns the value of field
     * 'previousLoanInterestRate'.
     * 
     * @return the value of field 'previousLoanInterestRate'.
     */
    public java.math.BigDecimal getPreviousLoanInterestRate()
    {
        return this._previousLoanInterestRate;
    } //-- java.math.BigDecimal getPreviousLoanInterestRate() 

    /**
     * Method getPriorBucketRateReturns the value of field
     * 'priorBucketRate'.
     * 
     * @return the value of field 'priorBucketRate'.
     */
    public java.math.BigDecimal getPriorBucketRate()
    {
        return this._priorBucketRate;
    } //-- java.math.BigDecimal getPriorBucketRate() 

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
     * Method getRenewalDateReturns the value of field
     * 'renewalDate'.
     * 
     * @return the value of field 'renewalDate'.
     */
    public java.lang.String getRenewalDate()
    {
        return this._renewalDate;
    } //-- java.lang.String getRenewalDate() 

    /**
     * Method getUnearnedInterestReturns the value of field
     * 'unearnedInterest'.
     * 
     * @return the value of field 'unearnedInterest'.
     */
    public java.math.BigDecimal getUnearnedInterest()
    {
        return this._unearnedInterest;
    } //-- java.math.BigDecimal getUnearnedInterest() 

    /**
     * Method getUnearnedLoanInterestReturns the value of field
     * 'unearnedLoanInterest'.
     * 
     * @return the value of field 'unearnedLoanInterest'.
     */
    public java.math.BigDecimal getUnearnedLoanInterest()
    {
        return this._unearnedLoanInterest;
    } //-- java.math.BigDecimal getUnearnedLoanInterest() 

    /**
     * Method hasBonusIntRateDur
     */
    public boolean hasBonusIntRateDur()
    {
        return this._has_bonusIntRateDur;
    } //-- boolean hasBonusIntRateDur() 

    /**
     * Method hasBucketPK
     */
    public boolean hasBucketPK()
    {
        return this._has_bucketPK;
    } //-- boolean hasBucketPK() 

    /**
     * Method hasDurationOverride
     */
    public boolean hasDurationOverride()
    {
        return this._has_durationOverride;
    } //-- boolean hasDurationOverride() 

    /**
     * Method hasInvestmentFK
     */
    public boolean hasInvestmentFK()
    {
        return this._has_investmentFK;
    } //-- boolean hasInvestmentFK() 

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
     * Method removeAllAnnualizedSubBucketVO
     */
    public void removeAllAnnualizedSubBucketVO()
    {
        _annualizedSubBucketVOList.removeAllElements();
    } //-- void removeAllAnnualizedSubBucketVO() 

    /**
     * Method removeAllBucketAllocationVO
     */
    public void removeAllBucketAllocationVO()
    {
        _bucketAllocationVOList.removeAllElements();
    } //-- void removeAllBucketAllocationVO() 

    /**
     * Method removeAllBucketHistoryVO
     */
    public void removeAllBucketHistoryVO()
    {
        _bucketHistoryVOList.removeAllElements();
    } //-- void removeAllBucketHistoryVO() 

    /**
     * Method removeAnnualizedSubBucketVO
     * 
     * @param index
     */
    public edit.common.vo.AnnualizedSubBucketVO removeAnnualizedSubBucketVO(int index)
    {
        java.lang.Object obj = _annualizedSubBucketVOList.elementAt(index);
        _annualizedSubBucketVOList.removeElementAt(index);
        return (edit.common.vo.AnnualizedSubBucketVO) obj;
    } //-- edit.common.vo.AnnualizedSubBucketVO removeAnnualizedSubBucketVO(int) 

    /**
     * Method removeBucketAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.BucketAllocationVO removeBucketAllocationVO(int index)
    {
        java.lang.Object obj = _bucketAllocationVOList.elementAt(index);
        _bucketAllocationVOList.removeElementAt(index);
        return (edit.common.vo.BucketAllocationVO) obj;
    } //-- edit.common.vo.BucketAllocationVO removeBucketAllocationVO(int) 

    /**
     * Method removeBucketHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BucketHistoryVO removeBucketHistoryVO(int index)
    {
        java.lang.Object obj = _bucketHistoryVOList.elementAt(index);
        _bucketHistoryVOList.removeElementAt(index);
        return (edit.common.vo.BucketHistoryVO) obj;
    } //-- edit.common.vo.BucketHistoryVO removeBucketHistoryVO(int) 

    /**
     * Method setAccruedLoanInterestSets the value of field
     * 'accruedLoanInterest'.
     * 
     * @param accruedLoanInterest the value of field
     * 'accruedLoanInterest'.
     */
    public void setAccruedLoanInterest(java.math.BigDecimal accruedLoanInterest)
    {
        this._accruedLoanInterest = accruedLoanInterest;
        
        super.setVoChanged(true);
    } //-- void setAccruedLoanInterest(java.math.BigDecimal) 

    /**
     * Method setAnnualizedSubBucketVO
     * 
     * @param index
     * @param vAnnualizedSubBucketVO
     */
    public void setAnnualizedSubBucketVO(int index, edit.common.vo.AnnualizedSubBucketVO vAnnualizedSubBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _annualizedSubBucketVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAnnualizedSubBucketVO.setParentVO(this.getClass(), this);
        _annualizedSubBucketVOList.setElementAt(vAnnualizedSubBucketVO, index);
    } //-- void setAnnualizedSubBucketVO(int, edit.common.vo.AnnualizedSubBucketVO) 

    /**
     * Method setAnnualizedSubBucketVO
     * 
     * @param annualizedSubBucketVOArray
     */
    public void setAnnualizedSubBucketVO(edit.common.vo.AnnualizedSubBucketVO[] annualizedSubBucketVOArray)
    {
        //-- copy array
        _annualizedSubBucketVOList.removeAllElements();
        for (int i = 0; i < annualizedSubBucketVOArray.length; i++) {
            annualizedSubBucketVOArray[i].setParentVO(this.getClass(), this);
            _annualizedSubBucketVOList.addElement(annualizedSubBucketVOArray[i]);
        }
    } //-- void setAnnualizedSubBucketVO(edit.common.vo.AnnualizedSubBucketVO) 

    /**
     * Method setBilledLoanInterestSets the value of field
     * 'billedLoanInterest'.
     * 
     * @param billedLoanInterest the value of field
     * 'billedLoanInterest'.
     */
    public void setBilledLoanInterest(java.math.BigDecimal billedLoanInterest)
    {
        this._billedLoanInterest = billedLoanInterest;
        
        super.setVoChanged(true);
    } //-- void setBilledLoanInterest(java.math.BigDecimal) 

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
     * Method setBonusIntRateSets the value of field
     * 'bonusIntRate'.
     * 
     * @param bonusIntRate the value of field 'bonusIntRate'.
     */
    public void setBonusIntRate(java.math.BigDecimal bonusIntRate)
    {
        this._bonusIntRate = bonusIntRate;
        
        super.setVoChanged(true);
    } //-- void setBonusIntRate(java.math.BigDecimal) 

    /**
     * Method setBonusIntRateDurSets the value of field
     * 'bonusIntRateDur'.
     * 
     * @param bonusIntRateDur the value of field 'bonusIntRateDur'.
     */
    public void setBonusIntRateDur(int bonusIntRateDur)
    {
        this._bonusIntRateDur = bonusIntRateDur;
        
        super.setVoChanged(true);
        this._has_bonusIntRateDur = true;
    } //-- void setBonusIntRateDur(int) 

    /**
     * Method setBucketAllocationVO
     * 
     * @param index
     * @param vBucketAllocationVO
     */
    public void setBucketAllocationVO(int index, edit.common.vo.BucketAllocationVO vBucketAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBucketAllocationVO.setParentVO(this.getClass(), this);
        _bucketAllocationVOList.setElementAt(vBucketAllocationVO, index);
    } //-- void setBucketAllocationVO(int, edit.common.vo.BucketAllocationVO) 

    /**
     * Method setBucketAllocationVO
     * 
     * @param bucketAllocationVOArray
     */
    public void setBucketAllocationVO(edit.common.vo.BucketAllocationVO[] bucketAllocationVOArray)
    {
        //-- copy array
        _bucketAllocationVOList.removeAllElements();
        for (int i = 0; i < bucketAllocationVOArray.length; i++) {
            bucketAllocationVOArray[i].setParentVO(this.getClass(), this);
            _bucketAllocationVOList.addElement(bucketAllocationVOArray[i]);
        }
    } //-- void setBucketAllocationVO(edit.common.vo.BucketAllocationVO) 

    /**
     * Method setBucketHistoryVO
     * 
     * @param index
     * @param vBucketHistoryVO
     */
    public void setBucketHistoryVO(int index, edit.common.vo.BucketHistoryVO vBucketHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBucketHistoryVO.setParentVO(this.getClass(), this);
        _bucketHistoryVOList.setElementAt(vBucketHistoryVO, index);
    } //-- void setBucketHistoryVO(int, edit.common.vo.BucketHistoryVO) 

    /**
     * Method setBucketHistoryVO
     * 
     * @param bucketHistoryVOArray
     */
    public void setBucketHistoryVO(edit.common.vo.BucketHistoryVO[] bucketHistoryVOArray)
    {
        //-- copy array
        _bucketHistoryVOList.removeAllElements();
        for (int i = 0; i < bucketHistoryVOArray.length; i++) {
            bucketHistoryVOArray[i].setParentVO(this.getClass(), this);
            _bucketHistoryVOList.addElement(bucketHistoryVOArray[i]);
        }
    } //-- void setBucketHistoryVO(edit.common.vo.BucketHistoryVO) 

    /**
     * Method setBucketInterestRateSets the value of field
     * 'bucketInterestRate'.
     * 
     * @param bucketInterestRate the value of field
     * 'bucketInterestRate'.
     */
    public void setBucketInterestRate(java.math.BigDecimal bucketInterestRate)
    {
        this._bucketInterestRate = bucketInterestRate;
        
        super.setVoChanged(true);
    } //-- void setBucketInterestRate(java.math.BigDecimal) 

    /**
     * Method setBucketPKSets the value of field 'bucketPK'.
     * 
     * @param bucketPK the value of field 'bucketPK'.
     */
    public void setBucketPK(long bucketPK)
    {
        this._bucketPK = bucketPK;
        
        super.setVoChanged(true);
        this._has_bucketPK = true;
    } //-- void setBucketPK(long) 

    /**
     * Method setBucketSourceCTSets the value of field
     * 'bucketSourceCT'.
     * 
     * @param bucketSourceCT the value of field 'bucketSourceCT'.
     */
    public void setBucketSourceCT(java.lang.String bucketSourceCT)
    {
        this._bucketSourceCT = bucketSourceCT;
        
        super.setVoChanged(true);
    } //-- void setBucketSourceCT(java.lang.String) 

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
     * Method setDepositAmountSets the value of field
     * 'depositAmount'.
     * 
     * @param depositAmount the value of field 'depositAmount'.
     */
    public void setDepositAmount(java.math.BigDecimal depositAmount)
    {
        this._depositAmount = depositAmount;
        
        super.setVoChanged(true);
    } //-- void setDepositAmount(java.math.BigDecimal) 

    /**
     * Method setDepositAmountGainSets the value of field
     * 'depositAmountGain'.
     * 
     * @param depositAmountGain the value of field
     * 'depositAmountGain'.
     */
    public void setDepositAmountGain(java.math.BigDecimal depositAmountGain)
    {
        this._depositAmountGain = depositAmountGain;
        
        super.setVoChanged(true);
    } //-- void setDepositAmountGain(java.math.BigDecimal) 

    /**
     * Method setDepositDateSets the value of field 'depositDate'.
     * 
     * @param depositDate the value of field 'depositDate'.
     */
    public void setDepositDate(java.lang.String depositDate)
    {
        this._depositDate = depositDate;
        
        super.setVoChanged(true);
    } //-- void setDepositDate(java.lang.String) 

    /**
     * Method setDurationOverrideSets the value of field
     * 'durationOverride'.
     * 
     * @param durationOverride the value of field 'durationOverride'
     */
    public void setDurationOverride(int durationOverride)
    {
        this._durationOverride = durationOverride;
        
        super.setVoChanged(true);
        this._has_durationOverride = true;
    } //-- void setDurationOverride(int) 

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
     * Method setInterestPaidThroughDateSets the value of field
     * 'interestPaidThroughDate'.
     * 
     * @param interestPaidThroughDate the value of field
     * 'interestPaidThroughDate'.
     */
    public void setInterestPaidThroughDate(java.lang.String interestPaidThroughDate)
    {
        this._interestPaidThroughDate = interestPaidThroughDate;
        
        super.setVoChanged(true);
    } //-- void setInterestPaidThroughDate(java.lang.String) 

    /**
     * Method setInterestRateOverrideSets the value of field
     * 'interestRateOverride'.
     * 
     * @param interestRateOverride the value of field
     * 'interestRateOverride'.
     */
    public void setInterestRateOverride(java.math.BigDecimal interestRateOverride)
    {
        this._interestRateOverride = interestRateOverride;
        
        super.setVoChanged(true);
    } //-- void setInterestRateOverride(java.math.BigDecimal) 

    /**
     * Method setInvestmentFKSets the value of field
     * 'investmentFK'.
     * 
     * @param investmentFK the value of field 'investmentFK'.
     */
    public void setInvestmentFK(long investmentFK)
    {
        this._investmentFK = investmentFK;
        
        super.setVoChanged(true);
        this._has_investmentFK = true;
    } //-- void setInvestmentFK(long) 

    /**
     * Method setLastRenewalDateSets the value of field
     * 'lastRenewalDate'.
     * 
     * @param lastRenewalDate the value of field 'lastRenewalDate'.
     */
    public void setLastRenewalDate(java.lang.String lastRenewalDate)
    {
        this._lastRenewalDate = lastRenewalDate;
        
        super.setVoChanged(true);
    } //-- void setLastRenewalDate(java.lang.String) 

    /**
     * Method setLastValuationDateSets the value of field
     * 'lastValuationDate'.
     * 
     * @param lastValuationDate the value of field
     * 'lastValuationDate'.
     */
    public void setLastValuationDate(java.lang.String lastValuationDate)
    {
        this._lastValuationDate = lastValuationDate;
        
        super.setVoChanged(true);
    } //-- void setLastValuationDate(java.lang.String) 

    /**
     * Method setLoanCumDollarsSets the value of field
     * 'loanCumDollars'.
     * 
     * @param loanCumDollars the value of field 'loanCumDollars'.
     */
    public void setLoanCumDollars(java.math.BigDecimal loanCumDollars)
    {
        this._loanCumDollars = loanCumDollars;
        
        super.setVoChanged(true);
    } //-- void setLoanCumDollars(java.math.BigDecimal) 

    /**
     * Method setLoanInterestRateSets the value of field
     * 'loanInterestRate'.
     * 
     * @param loanInterestRate the value of field 'loanInterestRate'
     */
    public void setLoanInterestRate(java.math.BigDecimal loanInterestRate)
    {
        this._loanInterestRate = loanInterestRate;
        
        super.setVoChanged(true);
    } //-- void setLoanInterestRate(java.math.BigDecimal) 

    /**
     * Method setLoanPrincipalRemainingSets the value of field
     * 'loanPrincipalRemaining'.
     * 
     * @param loanPrincipalRemaining the value of field
     * 'loanPrincipalRemaining'.
     */
    public void setLoanPrincipalRemaining(java.math.BigDecimal loanPrincipalRemaining)
    {
        this._loanPrincipalRemaining = loanPrincipalRemaining;
        
        super.setVoChanged(true);
    } //-- void setLoanPrincipalRemaining(java.math.BigDecimal) 

    /**
     * Method setLockupEndDateSets the value of field
     * 'lockupEndDate'.
     * 
     * @param lockupEndDate the value of field 'lockupEndDate'.
     */
    public void setLockupEndDate(java.lang.String lockupEndDate)
    {
        this._lockupEndDate = lockupEndDate;
        
        super.setVoChanged(true);
    } //-- void setLockupEndDate(java.lang.String) 

    /**
     * Method setPayoutDollarsSets the value of field
     * 'payoutDollars'.
     * 
     * @param payoutDollars the value of field 'payoutDollars'.
     */
    public void setPayoutDollars(java.math.BigDecimal payoutDollars)
    {
        this._payoutDollars = payoutDollars;
        
        super.setVoChanged(true);
    } //-- void setPayoutDollars(java.math.BigDecimal) 

    /**
     * Method setPayoutUnitsSets the value of field 'payoutUnits'.
     * 
     * @param payoutUnits the value of field 'payoutUnits'.
     */
    public void setPayoutUnits(java.math.BigDecimal payoutUnits)
    {
        this._payoutUnits = payoutUnits;
        
        super.setVoChanged(true);
    } //-- void setPayoutUnits(java.math.BigDecimal) 

    /**
     * Method setPreviousLoanInterestRateSets the value of field
     * 'previousLoanInterestRate'.
     * 
     * @param previousLoanInterestRate the value of field
     * 'previousLoanInterestRate'.
     */
    public void setPreviousLoanInterestRate(java.math.BigDecimal previousLoanInterestRate)
    {
        this._previousLoanInterestRate = previousLoanInterestRate;
        
        super.setVoChanged(true);
    } //-- void setPreviousLoanInterestRate(java.math.BigDecimal) 

    /**
     * Method setPriorBucketRateSets the value of field
     * 'priorBucketRate'.
     * 
     * @param priorBucketRate the value of field 'priorBucketRate'.
     */
    public void setPriorBucketRate(java.math.BigDecimal priorBucketRate)
    {
        this._priorBucketRate = priorBucketRate;
        
        super.setVoChanged(true);
    } //-- void setPriorBucketRate(java.math.BigDecimal) 

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
     * Method setRenewalDateSets the value of field 'renewalDate'.
     * 
     * @param renewalDate the value of field 'renewalDate'.
     */
    public void setRenewalDate(java.lang.String renewalDate)
    {
        this._renewalDate = renewalDate;
        
        super.setVoChanged(true);
    } //-- void setRenewalDate(java.lang.String) 

    /**
     * Method setUnearnedInterestSets the value of field
     * 'unearnedInterest'.
     * 
     * @param unearnedInterest the value of field 'unearnedInterest'
     */
    public void setUnearnedInterest(java.math.BigDecimal unearnedInterest)
    {
        this._unearnedInterest = unearnedInterest;
        
        super.setVoChanged(true);
    } //-- void setUnearnedInterest(java.math.BigDecimal) 

    /**
     * Method setUnearnedLoanInterestSets the value of field
     * 'unearnedLoanInterest'.
     * 
     * @param unearnedLoanInterest the value of field
     * 'unearnedLoanInterest'.
     */
    public void setUnearnedLoanInterest(java.math.BigDecimal unearnedLoanInterest)
    {
        this._unearnedLoanInterest = unearnedLoanInterest;
        
        super.setVoChanged(true);
    } //-- void setUnearnedLoanInterest(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BucketVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BucketVO) Unmarshaller.unmarshal(edit.common.vo.BucketVO.class, reader);
    } //-- edit.common.vo.BucketVO unmarshal(java.io.Reader) 

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
