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
 * Class PayoutVO.
 * 
 * @version $Revision$ $Date$
 */
public class PayoutVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _payoutPK
     */
    private long _payoutPK;

    /**
     * keeps track of state for field: _payoutPK
     */
    private boolean _has_payoutPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _paymentStartDate
     */
    private java.lang.String _paymentStartDate;

    /**
     * Field _certainDuration
     */
    private int _certainDuration;

    /**
     * keeps track of state for field: _certainDuration
     */
    private boolean _has_certainDuration;

    /**
     * Field _postJune1986Investment
     */
    private java.lang.String _postJune1986Investment;

    /**
     * Field _paymentAmount
     */
    private java.math.BigDecimal _paymentAmount;

    /**
     * Field _reducePercent1
     */
    private java.math.BigDecimal _reducePercent1;

    /**
     * Field _reducePercent2
     */
    private java.math.BigDecimal _reducePercent2;

    /**
     * Field _totalExpectedReturnAmount
     */
    private java.math.BigDecimal _totalExpectedReturnAmount;

    /**
     * Field _finalDistributionAmount
     */
    private java.math.BigDecimal _finalDistributionAmount;

    /**
     * Field _exclusionRatio
     */
    private java.math.BigDecimal _exclusionRatio;

    /**
     * Field _yearlyTaxableBenefit
     */
    private java.math.BigDecimal _yearlyTaxableBenefit;

    /**
     * Field _finalPaymentDate
     */
    private java.lang.String _finalPaymentDate;

    /**
     * Field _lastCheckDate
     */
    private java.lang.String _lastCheckDate;

    /**
     * Field _nextPaymentDate
     */
    private java.lang.String _nextPaymentDate;

    /**
     * Field _exclusionAmount
     */
    private java.math.BigDecimal _exclusionAmount;

    /**
     * Field _certainPeriodEndDate
     */
    private java.lang.String _certainPeriodEndDate;

    /**
     * Field _paymentFrequencyCT
     */
    private java.lang.String _paymentFrequencyCT;

    /**
     * Field _lastDayOfMonthInd
     */
    private java.lang.String _lastDayOfMonthInd;


      //----------------/
     //- Constructors -/
    //----------------/

    public PayoutVO() {
        super();
    } //-- edit.common.vo.PayoutVO()


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
        
        if (obj instanceof PayoutVO) {
        
            PayoutVO temp = (PayoutVO)obj;
            if (this._payoutPK != temp._payoutPK)
                return false;
            if (this._has_payoutPK != temp._has_payoutPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._paymentStartDate != null) {
                if (temp._paymentStartDate == null) return false;
                else if (!(this._paymentStartDate.equals(temp._paymentStartDate))) 
                    return false;
            }
            else if (temp._paymentStartDate != null)
                return false;
            if (this._certainDuration != temp._certainDuration)
                return false;
            if (this._has_certainDuration != temp._has_certainDuration)
                return false;
            if (this._postJune1986Investment != null) {
                if (temp._postJune1986Investment == null) return false;
                else if (!(this._postJune1986Investment.equals(temp._postJune1986Investment))) 
                    return false;
            }
            else if (temp._postJune1986Investment != null)
                return false;
            if (this._paymentAmount != null) {
                if (temp._paymentAmount == null) return false;
                else if (!(this._paymentAmount.equals(temp._paymentAmount))) 
                    return false;
            }
            else if (temp._paymentAmount != null)
                return false;
            if (this._reducePercent1 != null) {
                if (temp._reducePercent1 == null) return false;
                else if (!(this._reducePercent1.equals(temp._reducePercent1))) 
                    return false;
            }
            else if (temp._reducePercent1 != null)
                return false;
            if (this._reducePercent2 != null) {
                if (temp._reducePercent2 == null) return false;
                else if (!(this._reducePercent2.equals(temp._reducePercent2))) 
                    return false;
            }
            else if (temp._reducePercent2 != null)
                return false;
            if (this._totalExpectedReturnAmount != null) {
                if (temp._totalExpectedReturnAmount == null) return false;
                else if (!(this._totalExpectedReturnAmount.equals(temp._totalExpectedReturnAmount))) 
                    return false;
            }
            else if (temp._totalExpectedReturnAmount != null)
                return false;
            if (this._finalDistributionAmount != null) {
                if (temp._finalDistributionAmount == null) return false;
                else if (!(this._finalDistributionAmount.equals(temp._finalDistributionAmount))) 
                    return false;
            }
            else if (temp._finalDistributionAmount != null)
                return false;
            if (this._exclusionRatio != null) {
                if (temp._exclusionRatio == null) return false;
                else if (!(this._exclusionRatio.equals(temp._exclusionRatio))) 
                    return false;
            }
            else if (temp._exclusionRatio != null)
                return false;
            if (this._yearlyTaxableBenefit != null) {
                if (temp._yearlyTaxableBenefit == null) return false;
                else if (!(this._yearlyTaxableBenefit.equals(temp._yearlyTaxableBenefit))) 
                    return false;
            }
            else if (temp._yearlyTaxableBenefit != null)
                return false;
            if (this._finalPaymentDate != null) {
                if (temp._finalPaymentDate == null) return false;
                else if (!(this._finalPaymentDate.equals(temp._finalPaymentDate))) 
                    return false;
            }
            else if (temp._finalPaymentDate != null)
                return false;
            if (this._lastCheckDate != null) {
                if (temp._lastCheckDate == null) return false;
                else if (!(this._lastCheckDate.equals(temp._lastCheckDate))) 
                    return false;
            }
            else if (temp._lastCheckDate != null)
                return false;
            if (this._nextPaymentDate != null) {
                if (temp._nextPaymentDate == null) return false;
                else if (!(this._nextPaymentDate.equals(temp._nextPaymentDate))) 
                    return false;
            }
            else if (temp._nextPaymentDate != null)
                return false;
            if (this._exclusionAmount != null) {
                if (temp._exclusionAmount == null) return false;
                else if (!(this._exclusionAmount.equals(temp._exclusionAmount))) 
                    return false;
            }
            else if (temp._exclusionAmount != null)
                return false;
            if (this._certainPeriodEndDate != null) {
                if (temp._certainPeriodEndDate == null) return false;
                else if (!(this._certainPeriodEndDate.equals(temp._certainPeriodEndDate))) 
                    return false;
            }
            else if (temp._certainPeriodEndDate != null)
                return false;
            if (this._paymentFrequencyCT != null) {
                if (temp._paymentFrequencyCT == null) return false;
                else if (!(this._paymentFrequencyCT.equals(temp._paymentFrequencyCT))) 
                    return false;
            }
            else if (temp._paymentFrequencyCT != null)
                return false;
            if (this._lastDayOfMonthInd != null) {
                if (temp._lastDayOfMonthInd == null) return false;
                else if (!(this._lastDayOfMonthInd.equals(temp._lastDayOfMonthInd))) 
                    return false;
            }
            else if (temp._lastDayOfMonthInd != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getCertainPeriodEndDateReturns the value of field
     * 'certainPeriodEndDate'.
     * 
     * @return the value of field 'certainPeriodEndDate'.
     */
    public java.lang.String getCertainPeriodEndDate()
    {
        return this._certainPeriodEndDate;
    } //-- java.lang.String getCertainPeriodEndDate() 

    /**
     * Method getExclusionAmountReturns the value of field
     * 'exclusionAmount'.
     * 
     * @return the value of field 'exclusionAmount'.
     */
    public java.math.BigDecimal getExclusionAmount()
    {
        return this._exclusionAmount;
    } //-- java.math.BigDecimal getExclusionAmount() 

    /**
     * Method getExclusionRatioReturns the value of field
     * 'exclusionRatio'.
     * 
     * @return the value of field 'exclusionRatio'.
     */
    public java.math.BigDecimal getExclusionRatio()
    {
        return this._exclusionRatio;
    } //-- java.math.BigDecimal getExclusionRatio() 

    /**
     * Method getFinalDistributionAmountReturns the value of field
     * 'finalDistributionAmount'.
     * 
     * @return the value of field 'finalDistributionAmount'.
     */
    public java.math.BigDecimal getFinalDistributionAmount()
    {
        return this._finalDistributionAmount;
    } //-- java.math.BigDecimal getFinalDistributionAmount() 

    /**
     * Method getFinalPaymentDateReturns the value of field
     * 'finalPaymentDate'.
     * 
     * @return the value of field 'finalPaymentDate'.
     */
    public java.lang.String getFinalPaymentDate()
    {
        return this._finalPaymentDate;
    } //-- java.lang.String getFinalPaymentDate() 

    /**
     * Method getLastCheckDateReturns the value of field
     * 'lastCheckDate'.
     * 
     * @return the value of field 'lastCheckDate'.
     */
    public java.lang.String getLastCheckDate()
    {
        return this._lastCheckDate;
    } //-- java.lang.String getLastCheckDate() 

    /**
     * Method getLastDayOfMonthIndReturns the value of field
     * 'lastDayOfMonthInd'.
     * 
     * @return the value of field 'lastDayOfMonthInd'.
     */
    public java.lang.String getLastDayOfMonthInd()
    {
        return this._lastDayOfMonthInd;
    } //-- java.lang.String getLastDayOfMonthInd() 

    /**
     * Method getNextPaymentDateReturns the value of field
     * 'nextPaymentDate'.
     * 
     * @return the value of field 'nextPaymentDate'.
     */
    public java.lang.String getNextPaymentDate()
    {
        return this._nextPaymentDate;
    } //-- java.lang.String getNextPaymentDate() 

    /**
     * Method getPaymentAmountReturns the value of field
     * 'paymentAmount'.
     * 
     * @return the value of field 'paymentAmount'.
     */
    public java.math.BigDecimal getPaymentAmount()
    {
        return this._paymentAmount;
    } //-- java.math.BigDecimal getPaymentAmount() 

    /**
     * Method getPaymentFrequencyCTReturns the value of field
     * 'paymentFrequencyCT'.
     * 
     * @return the value of field 'paymentFrequencyCT'.
     */
    public java.lang.String getPaymentFrequencyCT()
    {
        return this._paymentFrequencyCT;
    } //-- java.lang.String getPaymentFrequencyCT() 

    /**
     * Method getPaymentStartDateReturns the value of field
     * 'paymentStartDate'.
     * 
     * @return the value of field 'paymentStartDate'.
     */
    public java.lang.String getPaymentStartDate()
    {
        return this._paymentStartDate;
    } //-- java.lang.String getPaymentStartDate() 

    /**
     * Method getPayoutPKReturns the value of field 'payoutPK'.
     * 
     * @return the value of field 'payoutPK'.
     */
    public long getPayoutPK()
    {
        return this._payoutPK;
    } //-- long getPayoutPK() 

    /**
     * Method getPostJune1986InvestmentReturns the value of field
     * 'postJune1986Investment'.
     * 
     * @return the value of field 'postJune1986Investment'.
     */
    public java.lang.String getPostJune1986Investment()
    {
        return this._postJune1986Investment;
    } //-- java.lang.String getPostJune1986Investment() 

    /**
     * Method getReducePercent1Returns the value of field
     * 'reducePercent1'.
     * 
     * @return the value of field 'reducePercent1'.
     */
    public java.math.BigDecimal getReducePercent1()
    {
        return this._reducePercent1;
    } //-- java.math.BigDecimal getReducePercent1() 

    /**
     * Method getReducePercent2Returns the value of field
     * 'reducePercent2'.
     * 
     * @return the value of field 'reducePercent2'.
     */
    public java.math.BigDecimal getReducePercent2()
    {
        return this._reducePercent2;
    } //-- java.math.BigDecimal getReducePercent2() 

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
     * Method getTotalExpectedReturnAmountReturns the value of
     * field 'totalExpectedReturnAmount'.
     * 
     * @return the value of field 'totalExpectedReturnAmount'.
     */
    public java.math.BigDecimal getTotalExpectedReturnAmount()
    {
        return this._totalExpectedReturnAmount;
    } //-- java.math.BigDecimal getTotalExpectedReturnAmount() 

    /**
     * Method getYearlyTaxableBenefitReturns the value of field
     * 'yearlyTaxableBenefit'.
     * 
     * @return the value of field 'yearlyTaxableBenefit'.
     */
    public java.math.BigDecimal getYearlyTaxableBenefit()
    {
        return this._yearlyTaxableBenefit;
    } //-- java.math.BigDecimal getYearlyTaxableBenefit() 

    /**
     * Method hasCertainDuration
     */
    public boolean hasCertainDuration()
    {
        return this._has_certainDuration;
    } //-- boolean hasCertainDuration() 

    /**
     * Method hasPayoutPK
     */
    public boolean hasPayoutPK()
    {
        return this._has_payoutPK;
    } //-- boolean hasPayoutPK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method setCertainPeriodEndDateSets the value of field
     * 'certainPeriodEndDate'.
     * 
     * @param certainPeriodEndDate the value of field
     * 'certainPeriodEndDate'.
     */
    public void setCertainPeriodEndDate(java.lang.String certainPeriodEndDate)
    {
        this._certainPeriodEndDate = certainPeriodEndDate;
        
        super.setVoChanged(true);
    } //-- void setCertainPeriodEndDate(java.lang.String) 

    /**
     * Method setExclusionAmountSets the value of field
     * 'exclusionAmount'.
     * 
     * @param exclusionAmount the value of field 'exclusionAmount'.
     */
    public void setExclusionAmount(java.math.BigDecimal exclusionAmount)
    {
        this._exclusionAmount = exclusionAmount;
        
        super.setVoChanged(true);
    } //-- void setExclusionAmount(java.math.BigDecimal) 

    /**
     * Method setExclusionRatioSets the value of field
     * 'exclusionRatio'.
     * 
     * @param exclusionRatio the value of field 'exclusionRatio'.
     */
    public void setExclusionRatio(java.math.BigDecimal exclusionRatio)
    {
        this._exclusionRatio = exclusionRatio;
        
        super.setVoChanged(true);
    } //-- void setExclusionRatio(java.math.BigDecimal) 

    /**
     * Method setFinalDistributionAmountSets the value of field
     * 'finalDistributionAmount'.
     * 
     * @param finalDistributionAmount the value of field
     * 'finalDistributionAmount'.
     */
    public void setFinalDistributionAmount(java.math.BigDecimal finalDistributionAmount)
    {
        this._finalDistributionAmount = finalDistributionAmount;
        
        super.setVoChanged(true);
    } //-- void setFinalDistributionAmount(java.math.BigDecimal) 

    /**
     * Method setFinalPaymentDateSets the value of field
     * 'finalPaymentDate'.
     * 
     * @param finalPaymentDate the value of field 'finalPaymentDate'
     */
    public void setFinalPaymentDate(java.lang.String finalPaymentDate)
    {
        this._finalPaymentDate = finalPaymentDate;
        
        super.setVoChanged(true);
    } //-- void setFinalPaymentDate(java.lang.String) 

    /**
     * Method setLastCheckDateSets the value of field
     * 'lastCheckDate'.
     * 
     * @param lastCheckDate the value of field 'lastCheckDate'.
     */
    public void setLastCheckDate(java.lang.String lastCheckDate)
    {
        this._lastCheckDate = lastCheckDate;
        
        super.setVoChanged(true);
    } //-- void setLastCheckDate(java.lang.String) 

    /**
     * Method setLastDayOfMonthIndSets the value of field
     * 'lastDayOfMonthInd'.
     * 
     * @param lastDayOfMonthInd the value of field
     * 'lastDayOfMonthInd'.
     */
    public void setLastDayOfMonthInd(java.lang.String lastDayOfMonthInd)
    {
        this._lastDayOfMonthInd = lastDayOfMonthInd;
        
        super.setVoChanged(true);
    } //-- void setLastDayOfMonthInd(java.lang.String) 

    /**
     * Method setNextPaymentDateSets the value of field
     * 'nextPaymentDate'.
     * 
     * @param nextPaymentDate the value of field 'nextPaymentDate'.
     */
    public void setNextPaymentDate(java.lang.String nextPaymentDate)
    {
        this._nextPaymentDate = nextPaymentDate;
        
        super.setVoChanged(true);
    } //-- void setNextPaymentDate(java.lang.String) 

    /**
     * Method setPaymentAmountSets the value of field
     * 'paymentAmount'.
     * 
     * @param paymentAmount the value of field 'paymentAmount'.
     */
    public void setPaymentAmount(java.math.BigDecimal paymentAmount)
    {
        this._paymentAmount = paymentAmount;
        
        super.setVoChanged(true);
    } //-- void setPaymentAmount(java.math.BigDecimal) 

    /**
     * Method setPaymentFrequencyCTSets the value of field
     * 'paymentFrequencyCT'.
     * 
     * @param paymentFrequencyCT the value of field
     * 'paymentFrequencyCT'.
     */
    public void setPaymentFrequencyCT(java.lang.String paymentFrequencyCT)
    {
        this._paymentFrequencyCT = paymentFrequencyCT;
        
        super.setVoChanged(true);
    } //-- void setPaymentFrequencyCT(java.lang.String) 

    /**
     * Method setPaymentStartDateSets the value of field
     * 'paymentStartDate'.
     * 
     * @param paymentStartDate the value of field 'paymentStartDate'
     */
    public void setPaymentStartDate(java.lang.String paymentStartDate)
    {
        this._paymentStartDate = paymentStartDate;
        
        super.setVoChanged(true);
    } //-- void setPaymentStartDate(java.lang.String) 

    /**
     * Method setPayoutPKSets the value of field 'payoutPK'.
     * 
     * @param payoutPK the value of field 'payoutPK'.
     */
    public void setPayoutPK(long payoutPK)
    {
        this._payoutPK = payoutPK;
        
        super.setVoChanged(true);
        this._has_payoutPK = true;
    } //-- void setPayoutPK(long) 

    /**
     * Method setPostJune1986InvestmentSets the value of field
     * 'postJune1986Investment'.
     * 
     * @param postJune1986Investment the value of field
     * 'postJune1986Investment'.
     */
    public void setPostJune1986Investment(java.lang.String postJune1986Investment)
    {
        this._postJune1986Investment = postJune1986Investment;
        
        super.setVoChanged(true);
    } //-- void setPostJune1986Investment(java.lang.String) 

    /**
     * Method setReducePercent1Sets the value of field
     * 'reducePercent1'.
     * 
     * @param reducePercent1 the value of field 'reducePercent1'.
     */
    public void setReducePercent1(java.math.BigDecimal reducePercent1)
    {
        this._reducePercent1 = reducePercent1;
        
        super.setVoChanged(true);
    } //-- void setReducePercent1(java.math.BigDecimal) 

    /**
     * Method setReducePercent2Sets the value of field
     * 'reducePercent2'.
     * 
     * @param reducePercent2 the value of field 'reducePercent2'.
     */
    public void setReducePercent2(java.math.BigDecimal reducePercent2)
    {
        this._reducePercent2 = reducePercent2;
        
        super.setVoChanged(true);
    } //-- void setReducePercent2(java.math.BigDecimal) 

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
     * Method setTotalExpectedReturnAmountSets the value of field
     * 'totalExpectedReturnAmount'.
     * 
     * @param totalExpectedReturnAmount the value of field
     * 'totalExpectedReturnAmount'.
     */
    public void setTotalExpectedReturnAmount(java.math.BigDecimal totalExpectedReturnAmount)
    {
        this._totalExpectedReturnAmount = totalExpectedReturnAmount;
        
        super.setVoChanged(true);
    } //-- void setTotalExpectedReturnAmount(java.math.BigDecimal) 

    /**
     * Method setYearlyTaxableBenefitSets the value of field
     * 'yearlyTaxableBenefit'.
     * 
     * @param yearlyTaxableBenefit the value of field
     * 'yearlyTaxableBenefit'.
     */
    public void setYearlyTaxableBenefit(java.math.BigDecimal yearlyTaxableBenefit)
    {
        this._yearlyTaxableBenefit = yearlyTaxableBenefit;
        
        super.setVoChanged(true);
    } //-- void setYearlyTaxableBenefit(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PayoutVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PayoutVO) Unmarshaller.unmarshal(edit.common.vo.PayoutVO.class, reader);
    } //-- edit.common.vo.PayoutVO unmarshal(java.io.Reader) 

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
