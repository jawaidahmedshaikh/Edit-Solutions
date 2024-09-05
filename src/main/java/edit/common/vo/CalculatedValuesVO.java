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
 * Used for Correspondence calculated values
 * 
 * @version $Revision$ $Date$
 */
public class CalculatedValuesVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _calculatedValuesPK
     */
    private long _calculatedValuesPK;

    /**
     * keeps track of state for field: _calculatedValuesPK
     */
    private boolean _has_calculatedValuesPK;

    /**
     * Field _cashValue
     */
    private java.math.BigDecimal _cashValue;

    /**
     * Field _cashSurrenderValue
     */
    private java.math.BigDecimal _cashSurrenderValue;

    /**
     * Field _loanPrincipal
     */
    private java.math.BigDecimal _loanPrincipal;

    /**
     * Field _unitValues
     */
    private java.math.BigDecimal _unitValues;

    /**
     * Field _reserveAmount
     */
    private java.math.BigDecimal _reserveAmount;

    /**
     * Field _paidUpTermVested
     */
    private java.math.BigDecimal _paidUpTermVested;

    /**
     * Field _loanBalance
     */
    private java.math.BigDecimal _loanBalance;

    /**
     * Field _lastLoanActivityDate
     */
    private java.lang.String _lastLoanActivityDate;

    /**
     * Field _lastLoanDate
     */
    private java.lang.String _lastLoanDate;

    /**
     * Field _policyDuration
     */
    private int _policyDuration;

    /**
     * keeps track of state for field: _policyDuration
     */
    private boolean _has_policyDuration;

    /**
     * Field _accumLTCClaims
     */
    private java.math.BigDecimal _accumLTCClaims;

    /**
     * Field _accumHomeCareClaims
     */
    private java.math.BigDecimal _accumHomeCareClaims;

    /**
     * Field _LTCNumberOfMonthsPaid
     */
    private int _LTCNumberOfMonthsPaid;

    /**
     * keeps track of state for field: _LTCNumberOfMonthsPaid
     */
    private boolean _has_LTCNumberOfMonthsPaid;

    /**
     * Field _homeCareNumberOfMonthsPaid
     */
    private int _homeCareNumberOfMonthsPaid;

    /**
     * keeps track of state for field: _homeCareNumberOfMonthsPaid
     */
    private boolean _has_homeCareNumberOfMonthsPaid;

    /**
     * Field _accumWithdrawalAmount
     */
    private java.math.BigDecimal _accumWithdrawalAmount;

    /**
     * Field _accumWithdrawalUnits
     */
    private java.math.BigDecimal _accumWithdrawalUnits;

    /**
     * Field _accumCriticalCareAmount
     */
    private java.math.BigDecimal _accumCriticalCareAmount;

    /**
     * Field _paidUpTermDate
     */
    private java.lang.String _paidUpTermDate;

    /**
     * Field _reducedDBAge
     */
    private int _reducedDBAge;

    /**
     * keeps track of state for field: _reducedDBAge
     */
    private boolean _has_reducedDBAge;

    /**
     * Field _LTCConfinementAmount
     */
    private java.math.BigDecimal _LTCConfinementAmount;

    /**
     * Field _LTCHHCAmount
     */
    private java.math.BigDecimal _LTCHHCAmount;

    /**
     * Field _LTCWaitingPeriod
     */
    private int _LTCWaitingPeriod;

    /**
     * keeps track of state for field: _LTCWaitingPeriod
     */
    private boolean _has_LTCWaitingPeriod;

    /**
     * Field _LTCEliminationPeriod
     */
    private int _LTCEliminationPeriod;

    /**
     * keeps track of state for field: _LTCEliminationPeriod
     */
    private boolean _has_LTCEliminationPeriod;

    /**
     * Field _guarPaidUpPurchasePremium
     */
    private java.math.BigDecimal _guarPaidUpPurchasePremium;

    /**
     * Field _currentInterestRate
     */
    private java.math.BigDecimal _currentInterestRate;

    /**
     * Field _contractSurrValGuar
     */
    private java.math.BigDecimal _contractSurrValGuar;

    /**
     * Field _currentMVACharge
     */
    private java.math.BigDecimal _currentMVACharge;

    /**
     * Field _guaranteedPremiumPercent
     */
    private java.math.BigDecimal _guaranteedPremiumPercent;

    /**
     * Field _priorYearEndAccountValue
     */
    private java.math.BigDecimal _priorYearEndAccountValue;

    /**
     * Field _priorYearEndCashSurrenderValue
     */
    private java.math.BigDecimal _priorYearEndCashSurrenderValue;

    /**
     * Field _priorYearEndMinAcctValue
     */
    private java.math.BigDecimal _priorYearEndMinAcctValue;

    /**
     * Field _priorYearSurrenderValue
     */
    private java.math.BigDecimal _priorYearSurrenderValue;

    /**
     * Field _totalMinAccountValue
     */
    private java.math.BigDecimal _totalMinAccountValue;

    /**
     * Field _withdrawalSinceInception
     */
    private java.math.BigDecimal _withdrawalSinceInception;

    /**
     * Field _coverageFee
     */
    private java.math.BigDecimal _coverageFee;

    /**
     * Field _premiumsPaid
     */
    private java.math.BigDecimal _premiumsPaid;


      //----------------/
     //- Constructors -/
    //----------------/

    public CalculatedValuesVO() {
        super();
    } //-- edit.common.vo.CalculatedValuesVO()


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
        
        if (obj instanceof CalculatedValuesVO) {
        
            CalculatedValuesVO temp = (CalculatedValuesVO)obj;
            if (this._calculatedValuesPK != temp._calculatedValuesPK)
                return false;
            if (this._has_calculatedValuesPK != temp._has_calculatedValuesPK)
                return false;
            if (this._cashValue != null) {
                if (temp._cashValue == null) return false;
                else if (!(this._cashValue.equals(temp._cashValue))) 
                    return false;
            }
            else if (temp._cashValue != null)
                return false;
            if (this._cashSurrenderValue != null) {
                if (temp._cashSurrenderValue == null) return false;
                else if (!(this._cashSurrenderValue.equals(temp._cashSurrenderValue))) 
                    return false;
            }
            else if (temp._cashSurrenderValue != null)
                return false;
            if (this._loanPrincipal != null) {
                if (temp._loanPrincipal == null) return false;
                else if (!(this._loanPrincipal.equals(temp._loanPrincipal))) 
                    return false;
            }
            else if (temp._loanPrincipal != null)
                return false;
            if (this._unitValues != null) {
                if (temp._unitValues == null) return false;
                else if (!(this._unitValues.equals(temp._unitValues))) 
                    return false;
            }
            else if (temp._unitValues != null)
                return false;
            if (this._reserveAmount != null) {
                if (temp._reserveAmount == null) return false;
                else if (!(this._reserveAmount.equals(temp._reserveAmount))) 
                    return false;
            }
            else if (temp._reserveAmount != null)
                return false;
            if (this._paidUpTermVested != null) {
                if (temp._paidUpTermVested == null) return false;
                else if (!(this._paidUpTermVested.equals(temp._paidUpTermVested))) 
                    return false;
            }
            else if (temp._paidUpTermVested != null)
                return false;
            if (this._loanBalance != null) {
                if (temp._loanBalance == null) return false;
                else if (!(this._loanBalance.equals(temp._loanBalance))) 
                    return false;
            }
            else if (temp._loanBalance != null)
                return false;
            if (this._lastLoanActivityDate != null) {
                if (temp._lastLoanActivityDate == null) return false;
                else if (!(this._lastLoanActivityDate.equals(temp._lastLoanActivityDate))) 
                    return false;
            }
            else if (temp._lastLoanActivityDate != null)
                return false;
            if (this._lastLoanDate != null) {
                if (temp._lastLoanDate == null) return false;
                else if (!(this._lastLoanDate.equals(temp._lastLoanDate))) 
                    return false;
            }
            else if (temp._lastLoanDate != null)
                return false;
            if (this._policyDuration != temp._policyDuration)
                return false;
            if (this._has_policyDuration != temp._has_policyDuration)
                return false;
            if (this._accumLTCClaims != null) {
                if (temp._accumLTCClaims == null) return false;
                else if (!(this._accumLTCClaims.equals(temp._accumLTCClaims))) 
                    return false;
            }
            else if (temp._accumLTCClaims != null)
                return false;
            if (this._accumHomeCareClaims != null) {
                if (temp._accumHomeCareClaims == null) return false;
                else if (!(this._accumHomeCareClaims.equals(temp._accumHomeCareClaims))) 
                    return false;
            }
            else if (temp._accumHomeCareClaims != null)
                return false;
            if (this._LTCNumberOfMonthsPaid != temp._LTCNumberOfMonthsPaid)
                return false;
            if (this._has_LTCNumberOfMonthsPaid != temp._has_LTCNumberOfMonthsPaid)
                return false;
            if (this._homeCareNumberOfMonthsPaid != temp._homeCareNumberOfMonthsPaid)
                return false;
            if (this._has_homeCareNumberOfMonthsPaid != temp._has_homeCareNumberOfMonthsPaid)
                return false;
            if (this._accumWithdrawalAmount != null) {
                if (temp._accumWithdrawalAmount == null) return false;
                else if (!(this._accumWithdrawalAmount.equals(temp._accumWithdrawalAmount))) 
                    return false;
            }
            else if (temp._accumWithdrawalAmount != null)
                return false;
            if (this._accumWithdrawalUnits != null) {
                if (temp._accumWithdrawalUnits == null) return false;
                else if (!(this._accumWithdrawalUnits.equals(temp._accumWithdrawalUnits))) 
                    return false;
            }
            else if (temp._accumWithdrawalUnits != null)
                return false;
            if (this._accumCriticalCareAmount != null) {
                if (temp._accumCriticalCareAmount == null) return false;
                else if (!(this._accumCriticalCareAmount.equals(temp._accumCriticalCareAmount))) 
                    return false;
            }
            else if (temp._accumCriticalCareAmount != null)
                return false;
            if (this._paidUpTermDate != null) {
                if (temp._paidUpTermDate == null) return false;
                else if (!(this._paidUpTermDate.equals(temp._paidUpTermDate))) 
                    return false;
            }
            else if (temp._paidUpTermDate != null)
                return false;
            if (this._reducedDBAge != temp._reducedDBAge)
                return false;
            if (this._has_reducedDBAge != temp._has_reducedDBAge)
                return false;
            if (this._LTCConfinementAmount != null) {
                if (temp._LTCConfinementAmount == null) return false;
                else if (!(this._LTCConfinementAmount.equals(temp._LTCConfinementAmount))) 
                    return false;
            }
            else if (temp._LTCConfinementAmount != null)
                return false;
            if (this._LTCHHCAmount != null) {
                if (temp._LTCHHCAmount == null) return false;
                else if (!(this._LTCHHCAmount.equals(temp._LTCHHCAmount))) 
                    return false;
            }
            else if (temp._LTCHHCAmount != null)
                return false;
            if (this._LTCWaitingPeriod != temp._LTCWaitingPeriod)
                return false;
            if (this._has_LTCWaitingPeriod != temp._has_LTCWaitingPeriod)
                return false;
            if (this._LTCEliminationPeriod != temp._LTCEliminationPeriod)
                return false;
            if (this._has_LTCEliminationPeriod != temp._has_LTCEliminationPeriod)
                return false;
            if (this._guarPaidUpPurchasePremium != null) {
                if (temp._guarPaidUpPurchasePremium == null) return false;
                else if (!(this._guarPaidUpPurchasePremium.equals(temp._guarPaidUpPurchasePremium))) 
                    return false;
            }
            else if (temp._guarPaidUpPurchasePremium != null)
                return false;
            if (this._currentInterestRate != null) {
                if (temp._currentInterestRate == null) return false;
                else if (!(this._currentInterestRate.equals(temp._currentInterestRate))) 
                    return false;
            }
            else if (temp._currentInterestRate != null)
                return false;
            if (this._contractSurrValGuar != null) {
                if (temp._contractSurrValGuar == null) return false;
                else if (!(this._contractSurrValGuar.equals(temp._contractSurrValGuar))) 
                    return false;
            }
            else if (temp._contractSurrValGuar != null)
                return false;
            if (this._currentMVACharge != null) {
                if (temp._currentMVACharge == null) return false;
                else if (!(this._currentMVACharge.equals(temp._currentMVACharge))) 
                    return false;
            }
            else if (temp._currentMVACharge != null)
                return false;
            if (this._guaranteedPremiumPercent != null) {
                if (temp._guaranteedPremiumPercent == null) return false;
                else if (!(this._guaranteedPremiumPercent.equals(temp._guaranteedPremiumPercent))) 
                    return false;
            }
            else if (temp._guaranteedPremiumPercent != null)
                return false;
            if (this._priorYearEndAccountValue != null) {
                if (temp._priorYearEndAccountValue == null) return false;
                else if (!(this._priorYearEndAccountValue.equals(temp._priorYearEndAccountValue))) 
                    return false;
            }
            else if (temp._priorYearEndAccountValue != null)
                return false;
            if (this._priorYearEndCashSurrenderValue != null) {
                if (temp._priorYearEndCashSurrenderValue == null) return false;
                else if (!(this._priorYearEndCashSurrenderValue.equals(temp._priorYearEndCashSurrenderValue))) 
                    return false;
            }
            else if (temp._priorYearEndCashSurrenderValue != null)
                return false;
            if (this._priorYearEndMinAcctValue != null) {
                if (temp._priorYearEndMinAcctValue == null) return false;
                else if (!(this._priorYearEndMinAcctValue.equals(temp._priorYearEndMinAcctValue))) 
                    return false;
            }
            else if (temp._priorYearEndMinAcctValue != null)
                return false;
            if (this._priorYearSurrenderValue != null) {
                if (temp._priorYearSurrenderValue == null) return false;
                else if (!(this._priorYearSurrenderValue.equals(temp._priorYearSurrenderValue))) 
                    return false;
            }
            else if (temp._priorYearSurrenderValue != null)
                return false;
            if (this._totalMinAccountValue != null) {
                if (temp._totalMinAccountValue == null) return false;
                else if (!(this._totalMinAccountValue.equals(temp._totalMinAccountValue))) 
                    return false;
            }
            else if (temp._totalMinAccountValue != null)
                return false;
            if (this._withdrawalSinceInception != null) {
                if (temp._withdrawalSinceInception == null) return false;
                else if (!(this._withdrawalSinceInception.equals(temp._withdrawalSinceInception))) 
                    return false;
            }
            else if (temp._withdrawalSinceInception != null)
                return false;
            if (this._coverageFee != null) {
                if (temp._coverageFee == null) return false;
                else if (!(this._coverageFee.equals(temp._coverageFee))) 
                    return false;
            }
            else if (temp._coverageFee != null)
                return false;
            if (this._premiumsPaid != null) {
                if (temp._premiumsPaid == null) return false;
                else if (!(this._premiumsPaid.equals(temp._premiumsPaid))) 
                    return false;
            }
            else if (temp._premiumsPaid != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumCriticalCareAmountReturns the value of field
     * 'accumCriticalCareAmount'.
     * 
     * @return the value of field 'accumCriticalCareAmount'.
     */
    public java.math.BigDecimal getAccumCriticalCareAmount()
    {
        return this._accumCriticalCareAmount;
    } //-- java.math.BigDecimal getAccumCriticalCareAmount() 

    /**
     * Method getAccumHomeCareClaimsReturns the value of field
     * 'accumHomeCareClaims'.
     * 
     * @return the value of field 'accumHomeCareClaims'.
     */
    public java.math.BigDecimal getAccumHomeCareClaims()
    {
        return this._accumHomeCareClaims;
    } //-- java.math.BigDecimal getAccumHomeCareClaims() 

    /**
     * Method getAccumLTCClaimsReturns the value of field
     * 'accumLTCClaims'.
     * 
     * @return the value of field 'accumLTCClaims'.
     */
    public java.math.BigDecimal getAccumLTCClaims()
    {
        return this._accumLTCClaims;
    } //-- java.math.BigDecimal getAccumLTCClaims() 

    /**
     * Method getAccumWithdrawalAmountReturns the value of field
     * 'accumWithdrawalAmount'.
     * 
     * @return the value of field 'accumWithdrawalAmount'.
     */
    public java.math.BigDecimal getAccumWithdrawalAmount()
    {
        return this._accumWithdrawalAmount;
    } //-- java.math.BigDecimal getAccumWithdrawalAmount() 

    /**
     * Method getAccumWithdrawalUnitsReturns the value of field
     * 'accumWithdrawalUnits'.
     * 
     * @return the value of field 'accumWithdrawalUnits'.
     */
    public java.math.BigDecimal getAccumWithdrawalUnits()
    {
        return this._accumWithdrawalUnits;
    } //-- java.math.BigDecimal getAccumWithdrawalUnits() 

    /**
     * Method getCalculatedValuesPKReturns the value of field
     * 'calculatedValuesPK'.
     * 
     * @return the value of field 'calculatedValuesPK'.
     */
    public long getCalculatedValuesPK()
    {
        return this._calculatedValuesPK;
    } //-- long getCalculatedValuesPK() 

    /**
     * Method getCashSurrenderValueReturns the value of field
     * 'cashSurrenderValue'.
     * 
     * @return the value of field 'cashSurrenderValue'.
     */
    public java.math.BigDecimal getCashSurrenderValue()
    {
        return this._cashSurrenderValue;
    } //-- java.math.BigDecimal getCashSurrenderValue() 

    /**
     * Method getCashValueReturns the value of field 'cashValue'.
     * 
     * @return the value of field 'cashValue'.
     */
    public java.math.BigDecimal getCashValue()
    {
        return this._cashValue;
    } //-- java.math.BigDecimal getCashValue() 

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
     * Method getCoverageFeeReturns the value of field
     * 'coverageFee'.
     * 
     * @return the value of field 'coverageFee'.
     */
    public java.math.BigDecimal getCoverageFee()
    {
        return this._coverageFee;
    } //-- java.math.BigDecimal getCoverageFee() 

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
     * Method getGuarPaidUpPurchasePremiumReturns the value of
     * field 'guarPaidUpPurchasePremium'.
     * 
     * @return the value of field 'guarPaidUpPurchasePremium'.
     */
    public java.math.BigDecimal getGuarPaidUpPurchasePremium()
    {
        return this._guarPaidUpPurchasePremium;
    } //-- java.math.BigDecimal getGuarPaidUpPurchasePremium() 

    /**
     * Method getGuaranteedPremiumPercentReturns the value of field
     * 'guaranteedPremiumPercent'.
     * 
     * @return the value of field 'guaranteedPremiumPercent'.
     */
    public java.math.BigDecimal getGuaranteedPremiumPercent()
    {
        return this._guaranteedPremiumPercent;
    } //-- java.math.BigDecimal getGuaranteedPremiumPercent() 

    /**
     * Method getHomeCareNumberOfMonthsPaidReturns the value of
     * field 'homeCareNumberOfMonthsPaid'.
     * 
     * @return the value of field 'homeCareNumberOfMonthsPaid'.
     */
    public int getHomeCareNumberOfMonthsPaid()
    {
        return this._homeCareNumberOfMonthsPaid;
    } //-- int getHomeCareNumberOfMonthsPaid() 

    /**
     * Method getLTCConfinementAmountReturns the value of field
     * 'LTCConfinementAmount'.
     * 
     * @return the value of field 'LTCConfinementAmount'.
     */
    public java.math.BigDecimal getLTCConfinementAmount()
    {
        return this._LTCConfinementAmount;
    } //-- java.math.BigDecimal getLTCConfinementAmount() 

    /**
     * Method getLTCEliminationPeriodReturns the value of field
     * 'LTCEliminationPeriod'.
     * 
     * @return the value of field 'LTCEliminationPeriod'.
     */
    public int getLTCEliminationPeriod()
    {
        return this._LTCEliminationPeriod;
    } //-- int getLTCEliminationPeriod() 

    /**
     * Method getLTCHHCAmountReturns the value of field
     * 'LTCHHCAmount'.
     * 
     * @return the value of field 'LTCHHCAmount'.
     */
    public java.math.BigDecimal getLTCHHCAmount()
    {
        return this._LTCHHCAmount;
    } //-- java.math.BigDecimal getLTCHHCAmount() 

    /**
     * Method getLTCNumberOfMonthsPaidReturns the value of field
     * 'LTCNumberOfMonthsPaid'.
     * 
     * @return the value of field 'LTCNumberOfMonthsPaid'.
     */
    public int getLTCNumberOfMonthsPaid()
    {
        return this._LTCNumberOfMonthsPaid;
    } //-- int getLTCNumberOfMonthsPaid() 

    /**
     * Method getLTCWaitingPeriodReturns the value of field
     * 'LTCWaitingPeriod'.
     * 
     * @return the value of field 'LTCWaitingPeriod'.
     */
    public int getLTCWaitingPeriod()
    {
        return this._LTCWaitingPeriod;
    } //-- int getLTCWaitingPeriod() 

    /**
     * Method getLastLoanActivityDateReturns the value of field
     * 'lastLoanActivityDate'.
     * 
     * @return the value of field 'lastLoanActivityDate'.
     */
    public java.lang.String getLastLoanActivityDate()
    {
        return this._lastLoanActivityDate;
    } //-- java.lang.String getLastLoanActivityDate() 

    /**
     * Method getLastLoanDateReturns the value of field
     * 'lastLoanDate'.
     * 
     * @return the value of field 'lastLoanDate'.
     */
    public java.lang.String getLastLoanDate()
    {
        return this._lastLoanDate;
    } //-- java.lang.String getLastLoanDate() 

    /**
     * Method getLoanBalanceReturns the value of field
     * 'loanBalance'.
     * 
     * @return the value of field 'loanBalance'.
     */
    public java.math.BigDecimal getLoanBalance()
    {
        return this._loanBalance;
    } //-- java.math.BigDecimal getLoanBalance() 

    /**
     * Method getLoanPrincipalReturns the value of field
     * 'loanPrincipal'.
     * 
     * @return the value of field 'loanPrincipal'.
     */
    public java.math.BigDecimal getLoanPrincipal()
    {
        return this._loanPrincipal;
    } //-- java.math.BigDecimal getLoanPrincipal() 

    /**
     * Method getPaidUpTermDateReturns the value of field
     * 'paidUpTermDate'.
     * 
     * @return the value of field 'paidUpTermDate'.
     */
    public java.lang.String getPaidUpTermDate()
    {
        return this._paidUpTermDate;
    } //-- java.lang.String getPaidUpTermDate() 

    /**
     * Method getPaidUpTermVestedReturns the value of field
     * 'paidUpTermVested'.
     * 
     * @return the value of field 'paidUpTermVested'.
     */
    public java.math.BigDecimal getPaidUpTermVested()
    {
        return this._paidUpTermVested;
    } //-- java.math.BigDecimal getPaidUpTermVested() 

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
     * Method getPremiumsPaidReturns the value of field
     * 'premiumsPaid'.
     * 
     * @return the value of field 'premiumsPaid'.
     */
    public java.math.BigDecimal getPremiumsPaid()
    {
        return this._premiumsPaid;
    } //-- java.math.BigDecimal getPremiumsPaid() 

    /**
     * Method getPriorYearEndAccountValueReturns the value of field
     * 'priorYearEndAccountValue'.
     * 
     * @return the value of field 'priorYearEndAccountValue'.
     */
    public java.math.BigDecimal getPriorYearEndAccountValue()
    {
        return this._priorYearEndAccountValue;
    } //-- java.math.BigDecimal getPriorYearEndAccountValue() 

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
     * Method getReducedDBAgeReturns the value of field
     * 'reducedDBAge'.
     * 
     * @return the value of field 'reducedDBAge'.
     */
    public int getReducedDBAge()
    {
        return this._reducedDBAge;
    } //-- int getReducedDBAge() 

    /**
     * Method getReserveAmountReturns the value of field
     * 'reserveAmount'.
     * 
     * @return the value of field 'reserveAmount'.
     */
    public java.math.BigDecimal getReserveAmount()
    {
        return this._reserveAmount;
    } //-- java.math.BigDecimal getReserveAmount() 

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
     * Method getUnitValuesReturns the value of field 'unitValues'.
     * 
     * @return the value of field 'unitValues'.
     */
    public java.math.BigDecimal getUnitValues()
    {
        return this._unitValues;
    } //-- java.math.BigDecimal getUnitValues() 

    /**
     * Method getWithdrawalSinceInceptionReturns the value of field
     * 'withdrawalSinceInception'.
     * 
     * @return the value of field 'withdrawalSinceInception'.
     */
    public java.math.BigDecimal getWithdrawalSinceInception()
    {
        return this._withdrawalSinceInception;
    } //-- java.math.BigDecimal getWithdrawalSinceInception() 

    /**
     * Method hasCalculatedValuesPK
     */
    public boolean hasCalculatedValuesPK()
    {
        return this._has_calculatedValuesPK;
    } //-- boolean hasCalculatedValuesPK() 

    /**
     * Method hasHomeCareNumberOfMonthsPaid
     */
    public boolean hasHomeCareNumberOfMonthsPaid()
    {
        return this._has_homeCareNumberOfMonthsPaid;
    } //-- boolean hasHomeCareNumberOfMonthsPaid() 

    /**
     * Method hasLTCEliminationPeriod
     */
    public boolean hasLTCEliminationPeriod()
    {
        return this._has_LTCEliminationPeriod;
    } //-- boolean hasLTCEliminationPeriod() 

    /**
     * Method hasLTCNumberOfMonthsPaid
     */
    public boolean hasLTCNumberOfMonthsPaid()
    {
        return this._has_LTCNumberOfMonthsPaid;
    } //-- boolean hasLTCNumberOfMonthsPaid() 

    /**
     * Method hasLTCWaitingPeriod
     */
    public boolean hasLTCWaitingPeriod()
    {
        return this._has_LTCWaitingPeriod;
    } //-- boolean hasLTCWaitingPeriod() 

    /**
     * Method hasPolicyDuration
     */
    public boolean hasPolicyDuration()
    {
        return this._has_policyDuration;
    } //-- boolean hasPolicyDuration() 

    /**
     * Method hasReducedDBAge
     */
    public boolean hasReducedDBAge()
    {
        return this._has_reducedDBAge;
    } //-- boolean hasReducedDBAge() 

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
     * Method setAccumCriticalCareAmountSets the value of field
     * 'accumCriticalCareAmount'.
     * 
     * @param accumCriticalCareAmount the value of field
     * 'accumCriticalCareAmount'.
     */
    public void setAccumCriticalCareAmount(java.math.BigDecimal accumCriticalCareAmount)
    {
        this._accumCriticalCareAmount = accumCriticalCareAmount;
        
        super.setVoChanged(true);
    } //-- void setAccumCriticalCareAmount(java.math.BigDecimal) 

    /**
     * Method setAccumHomeCareClaimsSets the value of field
     * 'accumHomeCareClaims'.
     * 
     * @param accumHomeCareClaims the value of field
     * 'accumHomeCareClaims'.
     */
    public void setAccumHomeCareClaims(java.math.BigDecimal accumHomeCareClaims)
    {
        this._accumHomeCareClaims = accumHomeCareClaims;
        
        super.setVoChanged(true);
    } //-- void setAccumHomeCareClaims(java.math.BigDecimal) 

    /**
     * Method setAccumLTCClaimsSets the value of field
     * 'accumLTCClaims'.
     * 
     * @param accumLTCClaims the value of field 'accumLTCClaims'.
     */
    public void setAccumLTCClaims(java.math.BigDecimal accumLTCClaims)
    {
        this._accumLTCClaims = accumLTCClaims;
        
        super.setVoChanged(true);
    } //-- void setAccumLTCClaims(java.math.BigDecimal) 

    /**
     * Method setAccumWithdrawalAmountSets the value of field
     * 'accumWithdrawalAmount'.
     * 
     * @param accumWithdrawalAmount the value of field
     * 'accumWithdrawalAmount'.
     */
    public void setAccumWithdrawalAmount(java.math.BigDecimal accumWithdrawalAmount)
    {
        this._accumWithdrawalAmount = accumWithdrawalAmount;
        
        super.setVoChanged(true);
    } //-- void setAccumWithdrawalAmount(java.math.BigDecimal) 

    /**
     * Method setAccumWithdrawalUnitsSets the value of field
     * 'accumWithdrawalUnits'.
     * 
     * @param accumWithdrawalUnits the value of field
     * 'accumWithdrawalUnits'.
     */
    public void setAccumWithdrawalUnits(java.math.BigDecimal accumWithdrawalUnits)
    {
        this._accumWithdrawalUnits = accumWithdrawalUnits;
        
        super.setVoChanged(true);
    } //-- void setAccumWithdrawalUnits(java.math.BigDecimal) 

    /**
     * Method setCalculatedValuesPKSets the value of field
     * 'calculatedValuesPK'.
     * 
     * @param calculatedValuesPK the value of field
     * 'calculatedValuesPK'.
     */
    public void setCalculatedValuesPK(long calculatedValuesPK)
    {
        this._calculatedValuesPK = calculatedValuesPK;
        
        super.setVoChanged(true);
        this._has_calculatedValuesPK = true;
    } //-- void setCalculatedValuesPK(long) 

    /**
     * Method setCashSurrenderValueSets the value of field
     * 'cashSurrenderValue'.
     * 
     * @param cashSurrenderValue the value of field
     * 'cashSurrenderValue'.
     */
    public void setCashSurrenderValue(java.math.BigDecimal cashSurrenderValue)
    {
        this._cashSurrenderValue = cashSurrenderValue;
        
        super.setVoChanged(true);
    } //-- void setCashSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setCashValueSets the value of field 'cashValue'.
     * 
     * @param cashValue the value of field 'cashValue'.
     */
    public void setCashValue(java.math.BigDecimal cashValue)
    {
        this._cashValue = cashValue;
        
        super.setVoChanged(true);
    } //-- void setCashValue(java.math.BigDecimal) 

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
     * Method setCoverageFeeSets the value of field 'coverageFee'.
     * 
     * @param coverageFee the value of field 'coverageFee'.
     */
    public void setCoverageFee(java.math.BigDecimal coverageFee)
    {
        this._coverageFee = coverageFee;
        
        super.setVoChanged(true);
    } //-- void setCoverageFee(java.math.BigDecimal) 

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
     * Method setGuarPaidUpPurchasePremiumSets the value of field
     * 'guarPaidUpPurchasePremium'.
     * 
     * @param guarPaidUpPurchasePremium the value of field
     * 'guarPaidUpPurchasePremium'.
     */
    public void setGuarPaidUpPurchasePremium(java.math.BigDecimal guarPaidUpPurchasePremium)
    {
        this._guarPaidUpPurchasePremium = guarPaidUpPurchasePremium;
        
        super.setVoChanged(true);
    } //-- void setGuarPaidUpPurchasePremium(java.math.BigDecimal) 

    /**
     * Method setGuaranteedPremiumPercentSets the value of field
     * 'guaranteedPremiumPercent'.
     * 
     * @param guaranteedPremiumPercent the value of field
     * 'guaranteedPremiumPercent'.
     */
    public void setGuaranteedPremiumPercent(java.math.BigDecimal guaranteedPremiumPercent)
    {
        this._guaranteedPremiumPercent = guaranteedPremiumPercent;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedPremiumPercent(java.math.BigDecimal) 

    /**
     * Method setHomeCareNumberOfMonthsPaidSets the value of field
     * 'homeCareNumberOfMonthsPaid'.
     * 
     * @param homeCareNumberOfMonthsPaid the value of field
     * 'homeCareNumberOfMonthsPaid'.
     */
    public void setHomeCareNumberOfMonthsPaid(int homeCareNumberOfMonthsPaid)
    {
        this._homeCareNumberOfMonthsPaid = homeCareNumberOfMonthsPaid;
        
        super.setVoChanged(true);
        this._has_homeCareNumberOfMonthsPaid = true;
    } //-- void setHomeCareNumberOfMonthsPaid(int) 

    /**
     * Method setLTCConfinementAmountSets the value of field
     * 'LTCConfinementAmount'.
     * 
     * @param LTCConfinementAmount the value of field
     * 'LTCConfinementAmount'.
     */
    public void setLTCConfinementAmount(java.math.BigDecimal LTCConfinementAmount)
    {
        this._LTCConfinementAmount = LTCConfinementAmount;
        
        super.setVoChanged(true);
    } //-- void setLTCConfinementAmount(java.math.BigDecimal) 

    /**
     * Method setLTCEliminationPeriodSets the value of field
     * 'LTCEliminationPeriod'.
     * 
     * @param LTCEliminationPeriod the value of field
     * 'LTCEliminationPeriod'.
     */
    public void setLTCEliminationPeriod(int LTCEliminationPeriod)
    {
        this._LTCEliminationPeriod = LTCEliminationPeriod;
        
        super.setVoChanged(true);
        this._has_LTCEliminationPeriod = true;
    } //-- void setLTCEliminationPeriod(int) 

    /**
     * Method setLTCHHCAmountSets the value of field
     * 'LTCHHCAmount'.
     * 
     * @param LTCHHCAmount the value of field 'LTCHHCAmount'.
     */
    public void setLTCHHCAmount(java.math.BigDecimal LTCHHCAmount)
    {
        this._LTCHHCAmount = LTCHHCAmount;
        
        super.setVoChanged(true);
    } //-- void setLTCHHCAmount(java.math.BigDecimal) 

    /**
     * Method setLTCNumberOfMonthsPaidSets the value of field
     * 'LTCNumberOfMonthsPaid'.
     * 
     * @param LTCNumberOfMonthsPaid the value of field
     * 'LTCNumberOfMonthsPaid'.
     */
    public void setLTCNumberOfMonthsPaid(int LTCNumberOfMonthsPaid)
    {
        this._LTCNumberOfMonthsPaid = LTCNumberOfMonthsPaid;
        
        super.setVoChanged(true);
        this._has_LTCNumberOfMonthsPaid = true;
    } //-- void setLTCNumberOfMonthsPaid(int) 

    /**
     * Method setLTCWaitingPeriodSets the value of field
     * 'LTCWaitingPeriod'.
     * 
     * @param LTCWaitingPeriod the value of field 'LTCWaitingPeriod'
     */
    public void setLTCWaitingPeriod(int LTCWaitingPeriod)
    {
        this._LTCWaitingPeriod = LTCWaitingPeriod;
        
        super.setVoChanged(true);
        this._has_LTCWaitingPeriod = true;
    } //-- void setLTCWaitingPeriod(int) 

    /**
     * Method setLastLoanActivityDateSets the value of field
     * 'lastLoanActivityDate'.
     * 
     * @param lastLoanActivityDate the value of field
     * 'lastLoanActivityDate'.
     */
    public void setLastLoanActivityDate(java.lang.String lastLoanActivityDate)
    {
        this._lastLoanActivityDate = lastLoanActivityDate;
        
        super.setVoChanged(true);
    } //-- void setLastLoanActivityDate(java.lang.String) 

    /**
     * Method setLastLoanDateSets the value of field
     * 'lastLoanDate'.
     * 
     * @param lastLoanDate the value of field 'lastLoanDate'.
     */
    public void setLastLoanDate(java.lang.String lastLoanDate)
    {
        this._lastLoanDate = lastLoanDate;
        
        super.setVoChanged(true);
    } //-- void setLastLoanDate(java.lang.String) 

    /**
     * Method setLoanBalanceSets the value of field 'loanBalance'.
     * 
     * @param loanBalance the value of field 'loanBalance'.
     */
    public void setLoanBalance(java.math.BigDecimal loanBalance)
    {
        this._loanBalance = loanBalance;
        
        super.setVoChanged(true);
    } //-- void setLoanBalance(java.math.BigDecimal) 

    /**
     * Method setLoanPrincipalSets the value of field
     * 'loanPrincipal'.
     * 
     * @param loanPrincipal the value of field 'loanPrincipal'.
     */
    public void setLoanPrincipal(java.math.BigDecimal loanPrincipal)
    {
        this._loanPrincipal = loanPrincipal;
        
        super.setVoChanged(true);
    } //-- void setLoanPrincipal(java.math.BigDecimal) 

    /**
     * Method setPaidUpTermDateSets the value of field
     * 'paidUpTermDate'.
     * 
     * @param paidUpTermDate the value of field 'paidUpTermDate'.
     */
    public void setPaidUpTermDate(java.lang.String paidUpTermDate)
    {
        this._paidUpTermDate = paidUpTermDate;
        
        super.setVoChanged(true);
    } //-- void setPaidUpTermDate(java.lang.String) 

    /**
     * Method setPaidUpTermVestedSets the value of field
     * 'paidUpTermVested'.
     * 
     * @param paidUpTermVested the value of field 'paidUpTermVested'
     */
    public void setPaidUpTermVested(java.math.BigDecimal paidUpTermVested)
    {
        this._paidUpTermVested = paidUpTermVested;
        
        super.setVoChanged(true);
    } //-- void setPaidUpTermVested(java.math.BigDecimal) 

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
     * Method setPremiumsPaidSets the value of field
     * 'premiumsPaid'.
     * 
     * @param premiumsPaid the value of field 'premiumsPaid'.
     */
    public void setPremiumsPaid(java.math.BigDecimal premiumsPaid)
    {
        this._premiumsPaid = premiumsPaid;
        
        super.setVoChanged(true);
    } //-- void setPremiumsPaid(java.math.BigDecimal) 

    /**
     * Method setPriorYearEndAccountValueSets the value of field
     * 'priorYearEndAccountValue'.
     * 
     * @param priorYearEndAccountValue the value of field
     * 'priorYearEndAccountValue'.
     */
    public void setPriorYearEndAccountValue(java.math.BigDecimal priorYearEndAccountValue)
    {
        this._priorYearEndAccountValue = priorYearEndAccountValue;
        
        super.setVoChanged(true);
    } //-- void setPriorYearEndAccountValue(java.math.BigDecimal) 

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
     * Method setReducedDBAgeSets the value of field
     * 'reducedDBAge'.
     * 
     * @param reducedDBAge the value of field 'reducedDBAge'.
     */
    public void setReducedDBAge(int reducedDBAge)
    {
        this._reducedDBAge = reducedDBAge;
        
        super.setVoChanged(true);
        this._has_reducedDBAge = true;
    } //-- void setReducedDBAge(int) 

    /**
     * Method setReserveAmountSets the value of field
     * 'reserveAmount'.
     * 
     * @param reserveAmount the value of field 'reserveAmount'.
     */
    public void setReserveAmount(java.math.BigDecimal reserveAmount)
    {
        this._reserveAmount = reserveAmount;
        
        super.setVoChanged(true);
    } //-- void setReserveAmount(java.math.BigDecimal) 

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
     * Method setUnitValuesSets the value of field 'unitValues'.
     * 
     * @param unitValues the value of field 'unitValues'.
     */
    public void setUnitValues(java.math.BigDecimal unitValues)
    {
        this._unitValues = unitValues;
        
        super.setVoChanged(true);
    } //-- void setUnitValues(java.math.BigDecimal) 

    /**
     * Method setWithdrawalSinceInceptionSets the value of field
     * 'withdrawalSinceInception'.
     * 
     * @param withdrawalSinceInception the value of field
     * 'withdrawalSinceInception'.
     */
    public void setWithdrawalSinceInception(java.math.BigDecimal withdrawalSinceInception)
    {
        this._withdrawalSinceInception = withdrawalSinceInception;
        
        super.setVoChanged(true);
    } //-- void setWithdrawalSinceInception(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CalculatedValuesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CalculatedValuesVO) Unmarshaller.unmarshal(edit.common.vo.CalculatedValuesVO.class, reader);
    } //-- edit.common.vo.CalculatedValuesVO unmarshal(java.io.Reader) 

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
