/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class FinancialHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class FinancialHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _financialHistoryPK
     */
    private long _financialHistoryPK;

    /**
     * keeps track of state for field: _financialHistoryPK
     */
    private boolean _has_financialHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _grossAmount
     */
    private java.math.BigDecimal _grossAmount;

    /**
     * Field _netAmount
     */
    private java.math.BigDecimal _netAmount;

    /**
     * Field _checkAmount
     */
    private java.math.BigDecimal _checkAmount;

    /**
     * Field _freeAmount
     */
    private java.math.BigDecimal _freeAmount;

    /**
     * Field _taxableBenefit
     */
    private java.math.BigDecimal _taxableBenefit;

    /**
     * Field _disbursementSourceCT
     */
    private java.lang.String _disbursementSourceCT;

    private java.lang.String _priorDeathBenefitOption;

    private java.lang.String _unnecessaryPremiumInd;

    /**
     * Field _liability
     */
    private java.math.BigDecimal _liability;

    /**
     * Field _commissionableAmount
     */
    private java.math.BigDecimal _commissionableAmount;

    /**
     * Field _maxCommissionAmount
     */
    private java.math.BigDecimal _maxCommissionAmount;

    /**
     * Field _costBasis
     */
    private java.math.BigDecimal _costBasis;

    /**
     * Field _accumulatedValue
     */
    private java.math.BigDecimal _accumulatedValue;

    /**
     * Field _surrenderValue
     */
    private java.math.BigDecimal _surrenderValue;

    /**
     * Field _guarAccumulatedValue
     */
    private java.math.BigDecimal _guarAccumulatedValue;

    /**
     * Field _priorCostBasis
     */
    private java.math.BigDecimal _priorCostBasis;

    /**
     * Field _priorRecoveredCostBasis
     */
    private java.math.BigDecimal _priorRecoveredCostBasis;

    /**
     * Field _priorDueDate
     */
    private java.lang.String _priorDueDate;

    /**
     * Field _priorExtractDate
     */
    private java.lang.String _priorExtractDate;

    /**
     * Field _priorLapsePendingDate
     */
    private java.lang.String _priorLapsePendingDate;

    /**
     * Field _priorLapseDate
     */
    private java.lang.String _priorLapseDate;

    /**
     * Field _priorFixedAmount
     */
    private java.math.BigDecimal _priorFixedAmount;

    /**
     * Field _prevGuidelineSinglePremium
     */
    private java.math.BigDecimal _prevGuidelineSinglePremium;

    /**
     * Field _prevGuidelineLevelPremium
     */
    private java.math.BigDecimal _prevGuidelineLevelPremium;

    /**
     * Field _prevTamra
     */
    private java.math.BigDecimal _prevTamra;

    private java.math.BigDecimal _prevTamraInitAdjValue;

    /**
     * Field _prevTamraStartDate
     */
    private java.lang.String _prevTamraStartDate;

    /**
     * Field _prevComplexChangeValue
     */
    private java.lang.String _prevComplexChangeValue;

    /**
     * Field _prevMECGuidelineSinglePrem
     */
    private java.math.BigDecimal _prevMECGuidelineSinglePrem;

    /**
     * Field _prevMECGuidelineLevelPrem
     */
    private java.math.BigDecimal _prevMECGuidelineLevelPrem;

    /**
     * Field _prevMECStatusCT
     */
    private java.lang.String _prevMECStatusCT;

    /**
     * Field _prevMECDate
     */
    private java.lang.String _prevMECDate;

    private java.lang.String _prevMAPEndDate;

    /**
     * Field _taxableIndicator
     */
    private java.lang.String _taxableIndicator;

    /**
     * Field _netAmountAtRisk
     */
    private java.math.BigDecimal _netAmountAtRisk;

    /**
     * Field _prevChargeDeductAmount
     */
    private java.math.BigDecimal _prevChargeDeductAmount;

    /**
     * Field _interestProceeds
     */
    private java.math.BigDecimal _interestProceeds;

    /**
     * Field _distributionCodeCT
     */
    private java.lang.String _distributionCodeCT;

    /**
     * Field _netIncomeAttributable
     */
    private java.math.BigDecimal _netIncomeAttributable;

    /**
     * Field _priorInitialCYAccumValue
     */
    private java.math.BigDecimal _priorInitialCYAccumValue;

    /**
     * Field _prevCumGLP
     */
    private java.math.BigDecimal _prevCumGLP;

    /**
     * Field _prevTotalFaceAmount
     */
    private java.math.BigDecimal _prevTotalFaceAmount;

    /**
     * Field _insuranceInforce
     */
    private java.math.BigDecimal _insuranceInforce;

    /**
     * Field _priorTotalActiveBeneficiaries
     */
    private int _priorTotalActiveBeneficiaries;

    /**
     * keeps track of state for field: _priorTotalActiveBeneficiarie
     */
    private boolean _has_priorTotalActiveBeneficiaries;

    /**
     * Field _priorRemainingBeneficiaries
     */
    private int _priorRemainingBeneficiaries;

    /**
     * keeps track of state for field: _priorRemainingBeneficiaries
     */
    private boolean _has_priorRemainingBeneficiaries;

    /**
     * Field _priorSettlementAmount
     */
    private java.math.BigDecimal _priorSettlementAmount;

    /**
     * Field _priorLastSettlementValDate
     */
    private java.lang.String _priorLastSettlementValDate;

    /**
     * Field _sevenPayRate
     */
    private java.math.BigDecimal _sevenPayRate;

    /**
     * Field _duration
     */
    private int _duration;

    /**
     * keeps track of state for field: _duration
     */
    private boolean _has_duration;

    /**
     * Field _currentDeathBenefit
     */
    private java.math.BigDecimal _currentDeathBenefit;

    private java.math.BigDecimal _currIntRate;

    /**
     * Field _currentCorridorPercent
     */
    private java.math.BigDecimal _currentCorridorPercent;

    /**
     * Field _surrenderCharge
     */
    private java.math.BigDecimal _surrenderCharge;

      //----------------/
     //- Constructors -/
    //----------------/

    public FinancialHistoryVO() {
        super();
    } //-- edit.common.vo.FinancialHistoryVO()


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
        
        if (obj instanceof FinancialHistoryVO) {
        
            FinancialHistoryVO temp = (FinancialHistoryVO)obj;
            if (this._financialHistoryPK != temp._financialHistoryPK)
                return false;
            if (this._has_financialHistoryPK != temp._has_financialHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._grossAmount != null) {
                if (temp._grossAmount == null) return false;
                else if (!(this._grossAmount.equals(temp._grossAmount))) 
                    return false;
            }
            else if (temp._grossAmount != null)
                return false;
            if (this._netAmount != null) {
                if (temp._netAmount == null) return false;
                else if (!(this._netAmount.equals(temp._netAmount))) 
                    return false;
            }
            else if (temp._netAmount != null)
                return false;
            if (this._checkAmount != null) {
                if (temp._checkAmount == null) return false;
                else if (!(this._checkAmount.equals(temp._checkAmount))) 
                    return false;
            }
            else if (temp._checkAmount != null)
                return false;
            if (this._freeAmount != null) {
                if (temp._freeAmount == null) return false;
                else if (!(this._freeAmount.equals(temp._freeAmount))) 
                    return false;
            }
            else if (temp._freeAmount != null)
                return false;
            if (this._taxableBenefit != null) {
                if (temp._taxableBenefit == null) return false;
                else if (!(this._taxableBenefit.equals(temp._taxableBenefit))) 
                    return false;
            }
            else if (temp._taxableBenefit != null)
                return false;

            if (this._disbursementSourceCT != null) {
                if (temp._disbursementSourceCT == null) return false;
                else if (!(this._disbursementSourceCT.equals(temp._disbursementSourceCT))) 
                    return false;
            }
            else if (temp._disbursementSourceCT != null)
                return false;

            if (this._priorDeathBenefitOption != null) {
                if (temp._priorDeathBenefitOption == null) return false;
                else if (!(this._priorDeathBenefitOption.equals(temp._priorDeathBenefitOption))) 
                    return false;
            }
            else if (temp._priorDeathBenefitOption != null)
                return false;

            if (this._unnecessaryPremiumInd != null) {
                if (temp._unnecessaryPremiumInd == null) return false;
                else if (!(this._unnecessaryPremiumInd.equals(temp._unnecessaryPremiumInd))) 
                    return false;
            }
            else if (temp._unnecessaryPremiumInd != null)
                return false;

            if (this._liability != null) {
                if (temp._liability == null) return false;
                else if (!(this._liability.equals(temp._liability))) 
                    return false;
            }
            else if (temp._liability != null)
                return false;
            if (this._commissionableAmount != null) {
                if (temp._commissionableAmount == null) return false;
                else if (!(this._commissionableAmount.equals(temp._commissionableAmount))) 
                    return false;
            }
            else if (temp._commissionableAmount != null)
                return false;
            if (this._maxCommissionAmount != null) {
                if (temp._maxCommissionAmount == null) return false;
                else if (!(this._maxCommissionAmount.equals(temp._maxCommissionAmount))) 
                    return false;
            }
            else if (temp._maxCommissionAmount != null)
                return false;
            if (this._costBasis != null) {
                if (temp._costBasis == null) return false;
                else if (!(this._costBasis.equals(temp._costBasis))) 
                    return false;
            }
            else if (temp._costBasis != null)
                return false;
            if (this._accumulatedValue != null) {
                if (temp._accumulatedValue == null) return false;
                else if (!(this._accumulatedValue.equals(temp._accumulatedValue))) 
                    return false;
            }
            else if (temp._accumulatedValue != null)
                return false;
            if (this._surrenderValue != null) {
                if (temp._surrenderValue == null) return false;
                else if (!(this._surrenderValue.equals(temp._surrenderValue))) 
                    return false;
            }
            else if (temp._surrenderValue != null)
                return false;
            if (this._guarAccumulatedValue != null) {
                if (temp._guarAccumulatedValue == null) return false;
                else if (!(this._guarAccumulatedValue.equals(temp._guarAccumulatedValue))) 
                    return false;
            }
            else if (temp._guarAccumulatedValue != null)
                return false;
            if (this._priorCostBasis != null) {
                if (temp._priorCostBasis == null) return false;
                else if (!(this._priorCostBasis.equals(temp._priorCostBasis))) 
                    return false;
            }
            else if (temp._priorCostBasis != null)
                return false;
            if (this._priorRecoveredCostBasis != null) {
                if (temp._priorRecoveredCostBasis == null) return false;
                else if (!(this._priorRecoveredCostBasis.equals(temp._priorRecoveredCostBasis))) 
                    return false;
            }
            else if (temp._priorRecoveredCostBasis != null)
                return false;
            if (this._priorDueDate != null) {
                if (temp._priorDueDate == null) return false;
                else if (!(this._priorDueDate.equals(temp._priorDueDate))) 
                    return false;
            }
            else if (temp._priorDueDate != null)
                return false;
            if (this._priorExtractDate != null) {
                if (temp._priorExtractDate == null) return false;
                else if (!(this._priorExtractDate.equals(temp._priorExtractDate))) 
                    return false;
            }
            else if (temp._priorExtractDate != null)
                return false;
            if (this._priorLapsePendingDate != null) {
                if (temp._priorLapsePendingDate == null) return false;
                else if (!(this._priorLapsePendingDate.equals(temp._priorLapsePendingDate))) 
                    return false;
            }
            else if (temp._priorLapsePendingDate != null)
                return false;
            if (this._priorLapseDate != null) {
                if (temp._priorLapseDate == null) return false;
                else if (!(this._priorLapseDate.equals(temp._priorLapseDate))) 
                    return false;
            }
            else if (temp._priorLapseDate != null)
                return false;
            if (this._priorFixedAmount != null) {
                if (temp._priorFixedAmount == null) return false;
                else if (!(this._priorFixedAmount.equals(temp._priorFixedAmount))) 
                    return false;
            }
            else if (temp._priorFixedAmount != null)
                return false;
            if (this._prevGuidelineSinglePremium != null) {
                if (temp._prevGuidelineSinglePremium == null) return false;
                else if (!(this._prevGuidelineSinglePremium.equals(temp._prevGuidelineSinglePremium))) 
                    return false;
            }
            else if (temp._prevGuidelineSinglePremium != null)
                return false;
            if (this._prevGuidelineLevelPremium != null) {
                if (temp._prevGuidelineLevelPremium == null) return false;
                else if (!(this._prevGuidelineLevelPremium.equals(temp._prevGuidelineLevelPremium))) 
                    return false;
            }
            else if (temp._prevGuidelineLevelPremium != null)
                return false;

            if (this._prevTamra != null) {
                if (temp._prevTamra == null) return false;
                else if (!(this._prevTamra.equals(temp._prevTamra))) 
                    return false;
            }
            else if (temp._prevTamra != null)
                return false;

            if (this._prevTamraInitAdjValue != null) {
                if (temp._prevTamraInitAdjValue == null) return false;
                else if (!(this._prevTamraInitAdjValue.equals(temp._prevTamraInitAdjValue))) 
                    return false;
            }
            else if (temp._prevTamraInitAdjValue != null)
                return false;

            if (this._prevTamraStartDate != null) {
                if (temp._prevTamraStartDate == null) return false;
                else if (!(this._prevTamraStartDate.equals(temp._prevTamraStartDate))) 
                    return false;
            }
            else if (temp._prevTamraStartDate != null)
                return false;
            if (this._prevComplexChangeValue != null) {
                if (temp._prevComplexChangeValue == null) return false;
                else if (!(this._prevComplexChangeValue.equals(temp._prevComplexChangeValue))) 
                    return false;
            }
            else if (temp._prevComplexChangeValue != null)
                return false;
            if (this._prevMECGuidelineSinglePrem != null) {
                if (temp._prevMECGuidelineSinglePrem == null) return false;
                else if (!(this._prevMECGuidelineSinglePrem.equals(temp._prevMECGuidelineSinglePrem))) 
                    return false;
            }
            else if (temp._prevMECGuidelineSinglePrem != null)
                return false;
            if (this._prevMECGuidelineLevelPrem != null) {
                if (temp._prevMECGuidelineLevelPrem == null) return false;
                else if (!(this._prevMECGuidelineLevelPrem.equals(temp._prevMECGuidelineLevelPrem))) 
                    return false;
            }
            else if (temp._prevMECGuidelineLevelPrem != null)
                return false;
            if (this._prevMECStatusCT != null) {
                if (temp._prevMECStatusCT == null) return false;
                else if (!(this._prevMECStatusCT.equals(temp._prevMECStatusCT))) 
                    return false;
            }
            else if (temp._prevMECStatusCT != null)
                return false;

            if (this._prevMECDate != null) {
                if (temp._prevMECDate == null) return false;
                else if (!(this._prevMECDate.equals(temp._prevMECDate))) 
                    return false;
            }
            else if (temp._prevMECDate != null)
                return false;

            if (this._prevMAPEndDate != null) {
                if (temp._prevMAPEndDate == null) return false;
                else if (!(this._prevMAPEndDate.equals(temp._prevMAPEndDate))) 
                    return false;
            }
            else if (temp._prevMAPEndDate != null)
                return false;

            if (this._taxableIndicator != null) {
                if (temp._taxableIndicator == null) return false;
                else if (!(this._taxableIndicator.equals(temp._taxableIndicator))) 
                    return false;
            }

            else if (temp._taxableIndicator != null)
                return false;
            if (this._netAmountAtRisk != null) {
                if (temp._netAmountAtRisk == null) return false;
                else if (!(this._netAmountAtRisk.equals(temp._netAmountAtRisk))) 
                    return false;
            }
            else if (temp._netAmountAtRisk != null)
                return false;
            if (this._prevChargeDeductAmount != null) {
                if (temp._prevChargeDeductAmount == null) return false;
                else if (!(this._prevChargeDeductAmount.equals(temp._prevChargeDeductAmount))) 
                    return false;
            }
            else if (temp._prevChargeDeductAmount != null)
                return false;
            if (this._interestProceeds != null) {
                if (temp._interestProceeds == null) return false;
                else if (!(this._interestProceeds.equals(temp._interestProceeds))) 
                    return false;
            }
            else if (temp._interestProceeds != null)
                return false;
            if (this._distributionCodeCT != null) {
                if (temp._distributionCodeCT == null) return false;
                else if (!(this._distributionCodeCT.equals(temp._distributionCodeCT))) 
                    return false;
            }
            else if (temp._distributionCodeCT != null)
                return false;
            if (this._netIncomeAttributable != null) {
                if (temp._netIncomeAttributable == null) return false;
                else if (!(this._netIncomeAttributable.equals(temp._netIncomeAttributable))) 
                    return false;
            }
            else if (temp._netIncomeAttributable != null)
                return false;
            if (this._priorInitialCYAccumValue != null) {
                if (temp._priorInitialCYAccumValue == null) return false;
                else if (!(this._priorInitialCYAccumValue.equals(temp._priorInitialCYAccumValue))) 
                    return false;
            }
            else if (temp._priorInitialCYAccumValue != null)
                return false;
            if (this._prevCumGLP != null) {
                if (temp._prevCumGLP == null) return false;
                else if (!(this._prevCumGLP.equals(temp._prevCumGLP))) 
                    return false;
            }
            else if (temp._prevCumGLP != null)
                return false;
            if (this._prevTotalFaceAmount != null) {
                if (temp._prevTotalFaceAmount == null) return false;
                else if (!(this._prevTotalFaceAmount.equals(temp._prevTotalFaceAmount))) 
                    return false;
            }
            else if (temp._prevTotalFaceAmount != null)
                return false;
            if (this._insuranceInforce != null) {
                if (temp._insuranceInforce == null) return false;
                else if (!(this._insuranceInforce.equals(temp._insuranceInforce))) 
                    return false;
            }
            else if (temp._insuranceInforce != null)
                return false;
            if (this._priorTotalActiveBeneficiaries != temp._priorTotalActiveBeneficiaries)
                return false;
            if (this._has_priorTotalActiveBeneficiaries != temp._has_priorTotalActiveBeneficiaries)
                return false;
            if (this._priorRemainingBeneficiaries != temp._priorRemainingBeneficiaries)
                return false;
            if (this._has_priorRemainingBeneficiaries != temp._has_priorRemainingBeneficiaries)
                return false;
            if (this._priorSettlementAmount != null) {
                if (temp._priorSettlementAmount == null) return false;
                else if (!(this._priorSettlementAmount.equals(temp._priorSettlementAmount))) 
                    return false;
            }
            else if (temp._priorSettlementAmount != null)
                return false;
            if (this._priorLastSettlementValDate != null) {
                if (temp._priorLastSettlementValDate == null) return false;
                else if (!(this._priorLastSettlementValDate.equals(temp._priorLastSettlementValDate))) 
                    return false;
            }
            else if (temp._priorLastSettlementValDate != null)
                return false;
            if (this._sevenPayRate != null) {
                if (temp._sevenPayRate == null) return false;
                else if (!(this._sevenPayRate.equals(temp._sevenPayRate))) 
                    return false;
            }
            else if (temp._sevenPayRate != null)
                return false;
            if (this._currentDeathBenefit != null) {
                if (temp._currentDeathBenefit == null) return false;
                else if (!(this._currentDeathBenefit.equals(temp._currentDeathBenefit))) 
                    return false;
            }
            else if (temp._currentDeathBenefit != null)
                return false;
            if (this._currentCorridorPercent != null) {
                if (temp._currentCorridorPercent == null) return false;
                else if (!(this._currentCorridorPercent.equals(temp._currentCorridorPercent))) 
                    return false;
            }
            else if (temp._currentCorridorPercent != null)
                return false;
            if (this._surrenderCharge != null) {
                if (temp._surrenderCharge == null) return false;
                else if (!(this._surrenderCharge.equals(temp._surrenderCharge))) 
                    return false;
            }
            else if (temp._surrenderCharge != null)
                return false;
            if (this._duration != temp._duration)
                return false;
            if (this._has_duration != temp._has_duration)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumulatedValueReturns the value of field
     * 'accumulatedValue'.
     * 
     * @return the value of field 'accumulatedValue'.
     */
    public java.math.BigDecimal getAccumulatedValue()
    {
        return this._accumulatedValue;
    } //-- java.math.BigDecimal getAccumulatedValue() 

    /**
     * Method getCheckAmountReturns the value of field
     * 'checkAmount'.
     * 
     * @return the value of field 'checkAmount'.
     */
    public java.math.BigDecimal getCheckAmount()
    {
        return this._checkAmount;
    } //-- java.math.BigDecimal getCheckAmount() 

    /**
     * Method getCommissionableAmountReturns the value of field
     * 'commissionableAmount'.
     * 
     * @return the value of field 'commissionableAmount'.
     */
    public java.math.BigDecimal getCommissionableAmount()
    {
        return this._commissionableAmount;
    } //-- java.math.BigDecimal getCommissionableAmount() 

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
     * Method getDisbursementSourceCTReturns the value of field
     * 'disbursementSourceCT'.
     * 
     * @return the value of field 'disbursementSourceCT'.
     */
    public java.lang.String getDisbursementSourceCT()
    {
        return this._disbursementSourceCT;
    } //-- java.lang.String getDisbursementSourceCT() 

    public java.lang.String getPriorDeathBenefitOption()
    {
        return this._priorDeathBenefitOption;
    } 

    public java.lang.String getUnnecessaryPremiumInd()
    {
        return this._unnecessaryPremiumInd;
    }

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
     * Method getDurationReturns the value of field 'duration'.
     * 
     * @return the value of field 'duration'.
     */
    public int getDuration()
    {
        return this._duration;
    } //-- int getDuration() 

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
     * Method getFinancialHistoryPKReturns the value of field
     * 'financialHistoryPK'.
     * 
     * @return the value of field 'financialHistoryPK'.
     */
    public long getFinancialHistoryPK()
    {
        return this._financialHistoryPK;
    } //-- long getFinancialHistoryPK() 

    /**
     * Method getFreeAmountReturns the value of field 'freeAmount'.
     * 
     * @return the value of field 'freeAmount'.
     */
    public java.math.BigDecimal getFreeAmount()
    {
        return this._freeAmount;
    } //-- java.math.BigDecimal getFreeAmount() 

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
     * Method getGuarAccumulatedValueReturns the value of field
     * 'guarAccumulatedValue'.
     * 
     * @return the value of field 'guarAccumulatedValue'.
     */
    public java.math.BigDecimal getGuarAccumulatedValue()
    {
        return this._guarAccumulatedValue;
    } //-- java.math.BigDecimal getGuarAccumulatedValue() 

    /**
     * Method getInsuranceInforceReturns the value of field
     * 'insuranceInforce'.
     * 
     * @return the value of field 'insuranceInforce'.
     */
    public java.math.BigDecimal getInsuranceInforce()
    {
        return this._insuranceInforce;
    } //-- java.math.BigDecimal getInsuranceInforce() 

    /**
     * Method getInterestProceedsReturns the value of field
     * 'interestProceeds'.
     * 
     * @return the value of field 'interestProceeds'.
     */
    public java.math.BigDecimal getInterestProceeds()
    {
        return this._interestProceeds;
    } //-- java.math.BigDecimal getInterestProceeds() 

    /**
     * Method getLiabilityReturns the value of field 'liability'.
     * 
     * @return the value of field 'liability'.
     */
    public java.math.BigDecimal getLiability()
    {
        return this._liability;
    } //-- java.math.BigDecimal getLiability() 

    /**
     * Method getMaxCommissionAmountReturns the value of field
     * 'maxCommissionAmount'.
     * 
     * @return the value of field 'maxCommissionAmount'.
     */
    public java.math.BigDecimal getMaxCommissionAmount()
    {
        return this._maxCommissionAmount;
    } //-- java.math.BigDecimal getMaxCommissionAmount() 

    /**
     * Method getNetAmountReturns the value of field 'netAmount'.
     * 
     * @return the value of field 'netAmount'.
     */
    public java.math.BigDecimal getNetAmount()
    {
        return this._netAmount;
    } //-- java.math.BigDecimal getNetAmount() 

    /**
     * Method getNetAmountAtRiskReturns the value of field
     * 'netAmountAtRisk'.
     * 
     * @return the value of field 'netAmountAtRisk'.
     */
    public java.math.BigDecimal getNetAmountAtRisk()
    {
        return this._netAmountAtRisk;
    } //-- java.math.BigDecimal getNetAmountAtRisk() 

    /**
     * Method getNetIncomeAttributableReturns the value of field
     * 'netIncomeAttributable'.
     * 
     * @return the value of field 'netIncomeAttributable'.
     */
    public java.math.BigDecimal getNetIncomeAttributable()
    {
        return this._netIncomeAttributable;
    } //-- java.math.BigDecimal getNetIncomeAttributable() 

    /**
     * Method getPrevChargeDeductAmountReturns the value of field
     * 'prevChargeDeductAmount'.
     * 
     * @return the value of field 'prevChargeDeductAmount'.
     */
    public java.math.BigDecimal getPrevChargeDeductAmount()
    {
        return this._prevChargeDeductAmount;
    } //-- java.math.BigDecimal getPrevChargeDeductAmount() 

    /**
     * Method getPrevComplexChangeValueReturns the value of field
     * 'prevComplexChangeValue'.
     * 
     * @return the value of field 'prevComplexChangeValue'.
     */
    public java.lang.String getPrevComplexChangeValue()
    {
        return this._prevComplexChangeValue;
    } //-- java.lang.String getPrevComplexChangeValue() 

    /**
     * Method getPrevCumGLPReturns the value of field 'prevCumGLP'.
     * 
     * @return the value of field 'prevCumGLP'.
     */
    public java.math.BigDecimal getPrevCumGLP()
    {
        return this._prevCumGLP;
    } //-- java.math.BigDecimal getPrevCumGLP() 

    /**
     * Method getPrevGuidelineLevelPremiumReturns the value of
     * field 'prevGuidelineLevelPremium'.
     * 
     * @return the value of field 'prevGuidelineLevelPremium'.
     */
    public java.math.BigDecimal getPrevGuidelineLevelPremium()
    {
        return this._prevGuidelineLevelPremium;
    } //-- java.math.BigDecimal getPrevGuidelineLevelPremium() 

    /**
     * Method getPrevGuidelineSinglePremiumReturns the value of
     * field 'prevGuidelineSinglePremium'.
     * 
     * @return the value of field 'prevGuidelineSinglePremium'.
     */
    public java.math.BigDecimal getPrevGuidelineSinglePremium()
    {
        return this._prevGuidelineSinglePremium;
    } //-- java.math.BigDecimal getPrevGuidelineSinglePremium() 

    /**
     * Method getPrevMECDateReturns the value of field
     * 'prevMECDate'.
     * 
     * @return the value of field 'prevMECDate'.
     */
    public java.lang.String getPrevMECDate()
    {
        return this._prevMECDate;
    } //-- java.lang.String getPrevMECDate() 


    public java.lang.String getPrevMAPEndDate()
    {
        return this._prevMAPEndDate;
    } 

    /**
     * Method getPrevMECGuidelineLevelPremReturns the value of
     * field 'prevMECGuidelineLevelPrem'.
     * 
     * @return the value of field 'prevMECGuidelineLevelPrem'.
     */
    public java.math.BigDecimal getPrevMECGuidelineLevelPrem()
    {
        return this._prevMECGuidelineLevelPrem;
    } //-- java.math.BigDecimal getPrevMECGuidelineLevelPrem() 

    /**
     * Method getPrevMECGuidelineSinglePremReturns the value of
     * field 'prevMECGuidelineSinglePrem'.
     * 
     * @return the value of field 'prevMECGuidelineSinglePrem'.
     */
    public java.math.BigDecimal getPrevMECGuidelineSinglePrem()
    {
        return this._prevMECGuidelineSinglePrem;
    } //-- java.math.BigDecimal getPrevMECGuidelineSinglePrem() 

    /**
     * Method getPrevMECStatusCTReturns the value of field
     * 'prevMECStatusCT'.
     * 
     * @return the value of field 'prevMECStatusCT'.
     */
    public java.lang.String getPrevMECStatusCT()
    {
        return this._prevMECStatusCT;
    } //-- java.lang.String getPrevMECStatusCT() 

    /**
     * Method getPrevTamraReturns the value of field 'prevTamra'.
     * 
     * @return the value of field 'prevTamra'.
     */
    public java.math.BigDecimal getPrevTamra()
    {
        return this._prevTamra;
    } //-- java.math.BigDecimal getPrevTamra() 

    public java.math.BigDecimal getPrevTamraInitAdjValue()
    {
        return this._prevTamraInitAdjValue;
    } //-- java.math.BigDecimal getPrevTamra() 

    /**
     * Method getPrevTamraStartDateReturns the value of field
     * 'prevTamraStartDate'.
     * 
     * @return the value of field 'prevTamraStartDate'.
     */
    public java.lang.String getPrevTamraStartDate()
    {
        return this._prevTamraStartDate;
    } //-- java.lang.String getPrevTamraStartDate() 

    /**
     * Method getPrevTotalFaceAmountReturns the value of field
     * 'prevTotalFaceAmount'.
     * 
     * @return the value of field 'prevTotalFaceAmount'.
     */
    public java.math.BigDecimal getPrevTotalFaceAmount()
    {
        return this._prevTotalFaceAmount;
    } //-- java.math.BigDecimal getPrevTotalFaceAmount() 

    /**
     * Method getPriorCostBasisReturns the value of field
     * 'priorCostBasis'.
     * 
     * @return the value of field 'priorCostBasis'.
     */
    public java.math.BigDecimal getPriorCostBasis()
    {
        return this._priorCostBasis;
    } //-- java.math.BigDecimal getPriorCostBasis() 

    /**
     * Method getPriorDueDateReturns the value of field
     * 'priorDueDate'.
     * 
     * @return the value of field 'priorDueDate'.
     */
    public java.lang.String getPriorDueDate()
    {
        return this._priorDueDate;
    } //-- java.lang.String getPriorDueDate() 

    /**
     * Method getPriorExtractDateReturns the value of field
     * 'priorExtractDate'.
     * 
     * @return the value of field 'priorExtractDate'.
     */
    public java.lang.String getPriorExtractDate()
    {
        return this._priorExtractDate;
    } //-- java.lang.String getPriorExtractDate() 

    /**
     * Method getPriorFixedAmountReturns the value of field
     * 'priorFixedAmount'.
     * 
     * @return the value of field 'priorFixedAmount'.
     */
    public java.math.BigDecimal getPriorFixedAmount()
    {
        return this._priorFixedAmount;
    } //-- java.math.BigDecimal getPriorFixedAmount() 

    /**
     * Method getPriorInitialCYAccumValueReturns the value of field
     * 'priorInitialCYAccumValue'.
     * 
     * @return the value of field 'priorInitialCYAccumValue'.
     */
    public java.math.BigDecimal getPriorInitialCYAccumValue()
    {
        return this._priorInitialCYAccumValue;
    } //-- java.math.BigDecimal getPriorInitialCYAccumValue() 

    /**
     * Method getPriorLapseDateReturns the value of field
     * 'priorLapseDate'.
     * 
     * @return the value of field 'priorLapseDate'.
     */
    public java.lang.String getPriorLapseDate()
    {
        return this._priorLapseDate;
    } //-- java.lang.String getPriorLapseDate() 

    /**
     * Method getPriorLapsePendingDateReturns the value of field
     * 'priorLapsePendingDate'.
     * 
     * @return the value of field 'priorLapsePendingDate'.
     */
    public java.lang.String getPriorLapsePendingDate()
    {
        return this._priorLapsePendingDate;
    } //-- java.lang.String getPriorLapsePendingDate() 

    /**
     * Method getPriorLastSettlementValDateReturns the value of
     * field 'priorLastSettlementValDate'.
     * 
     * @return the value of field 'priorLastSettlementValDate'.
     */
    public java.lang.String getPriorLastSettlementValDate()
    {
        return this._priorLastSettlementValDate;
    } //-- java.lang.String getPriorLastSettlementValDate() 

    /**
     * Method getPriorRecoveredCostBasisReturns the value of field
     * 'priorRecoveredCostBasis'.
     * 
     * @return the value of field 'priorRecoveredCostBasis'.
     */
    public java.math.BigDecimal getPriorRecoveredCostBasis()
    {
        return this._priorRecoveredCostBasis;
    } //-- java.math.BigDecimal getPriorRecoveredCostBasis() 

    /**
     * Method getPriorRemainingBeneficiariesReturns the value of
     * field 'priorRemainingBeneficiaries'.
     * 
     * @return the value of field 'priorRemainingBeneficiaries'.
     */
    public int getPriorRemainingBeneficiaries()
    {
        return this._priorRemainingBeneficiaries;
    } //-- int getPriorRemainingBeneficiaries() 

    /**
     * Method getPriorSettlementAmountReturns the value of field
     * 'priorSettlementAmount'.
     * 
     * @return the value of field 'priorSettlementAmount'.
     */
    public java.math.BigDecimal getPriorSettlementAmount()
    {
        return this._priorSettlementAmount;
    } //-- java.math.BigDecimal getPriorSettlementAmount() 

    /**
     * Method getPriorTotalActiveBeneficiariesReturns the value of
     * field 'priorTotalActiveBeneficiaries'.
     * 
     * @return the value of field 'priorTotalActiveBeneficiaries'.
     */
    public int getPriorTotalActiveBeneficiaries()
    {
        return this._priorTotalActiveBeneficiaries;
    } //-- int getPriorTotalActiveBeneficiaries() 

    /**
     * Method getSevenPayRateReturns the value of field
     * 'sevenPayRate'.
     * 
     * @return the value of field 'sevenPayRate'.
     */
    public java.math.BigDecimal getSevenPayRate()
    {
        return this._sevenPayRate;
    } //-- java.math.BigDecimal getSevenPayRate() 

    /**
     * Method getSurrenderValueReturns the value of field
     * 'surrenderValue'.
     * 
     * @return the value of field 'surrenderValue'.
     */
    public java.math.BigDecimal getSurrenderValue()
    {
        return this._surrenderValue;
    } //-- java.math.BigDecimal getSurrenderValue() 

    /**
     * Method getTaxableBenefitReturns the value of field
     * 'taxableBenefit'.
     * 
     * @return the value of field 'taxableBenefit'.
     */
    public java.math.BigDecimal getTaxableBenefit()
    {
        return this._taxableBenefit;
    } //-- java.math.BigDecimal getTaxableBenefit() 

    /**
     * Method getTaxableIndicatorReturns the value of field
     * 'taxableIndicator'.
     * 
     * @return the value of field 'taxableIndicator'.
     */
    public java.lang.String getTaxableIndicator()
    {
        return this._taxableIndicator;
    } //-- java.lang.String getTaxableIndicator() 

    /**
     * Method hasDuration
     */
    public boolean hasDuration()
    {
        return this._has_duration;
    } //-- boolean hasDuration() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasFinancialHistoryPK
     */
    public boolean hasFinancialHistoryPK()
    {
        return this._has_financialHistoryPK;
    } //-- boolean hasFinancialHistoryPK() 

    /**
     * Method hasPriorRemainingBeneficiaries
     */
    public boolean hasPriorRemainingBeneficiaries()
    {
        return this._has_priorRemainingBeneficiaries;
    } //-- boolean hasPriorRemainingBeneficiaries() 

    /**
     * Method hasPriorTotalActiveBeneficiaries
     */
    public boolean hasPriorTotalActiveBeneficiaries()
    {
        return this._has_priorTotalActiveBeneficiaries;
    } //-- boolean hasPriorTotalActiveBeneficiaries() 

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
     * Method setAccumulatedValueSets the value of field
     * 'accumulatedValue'.
     * 
     * @param accumulatedValue the value of field 'accumulatedValue'
     */
    public void setAccumulatedValue(java.math.BigDecimal accumulatedValue)
    {
        this._accumulatedValue = accumulatedValue;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedValue(java.math.BigDecimal) 

    /**
     * Method setCheckAmountSets the value of field 'checkAmount'.
     * 
     * @param checkAmount the value of field 'checkAmount'.
     */
    public void setCheckAmount(java.math.BigDecimal checkAmount)
    {
        this._checkAmount = checkAmount;
        
        super.setVoChanged(true);
    } //-- void setCheckAmount(java.math.BigDecimal) 

    /**
     * Method setCommissionableAmountSets the value of field
     * 'commissionableAmount'.
     * 
     * @param commissionableAmount the value of field
     * 'commissionableAmount'.
     */
    public void setCommissionableAmount(java.math.BigDecimal commissionableAmount)
    {
        this._commissionableAmount = commissionableAmount;
        
        super.setVoChanged(true);
    } //-- void setCommissionableAmount(java.math.BigDecimal) 

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

    public void setPriorDeathBenefitOption(java.lang.String priorDeathBenefitOption)
    {
        this._priorDeathBenefitOption = priorDeathBenefitOption;
        
        super.setVoChanged(true);
    } //-- void setDisbursementSourceCT(java.lang.String) 

    public void setUnnecessaryPremiumInd(java.lang.String unnecessaryPremiumInd)
    {
        this._unnecessaryPremiumInd = unnecessaryPremiumInd;
        
        super.setVoChanged(true);
    } //-- void setDisbursementSourceCT(java.lang.String) 

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
     * Method setDurationSets the value of field 'duration'.
     * 
     * @param duration the value of field 'duration'.
     */
    public void setDuration(int duration)
    {
        this._duration = duration;
        
        super.setVoChanged(true);
        this._has_duration = true;
    } //-- void setDuration(int) 

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
     * Method setFinancialHistoryPKSets the value of field
     * 'financialHistoryPK'.
     * 
     * @param financialHistoryPK the value of field
     * 'financialHistoryPK'.
     */
    public void setFinancialHistoryPK(long financialHistoryPK)
    {
        this._financialHistoryPK = financialHistoryPK;
        
        super.setVoChanged(true);
        this._has_financialHistoryPK = true;
    } //-- void setFinancialHistoryPK(long) 

    /**
     * Method setFreeAmountSets the value of field 'freeAmount'.
     * 
     * @param freeAmount the value of field 'freeAmount'.
     */
    public void setFreeAmount(java.math.BigDecimal freeAmount)
    {
        this._freeAmount = freeAmount;
        
        super.setVoChanged(true);
    } //-- void setFreeAmount(java.math.BigDecimal) 

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
     * Method setGuarAccumulatedValueSets the value of field
     * 'guarAccumulatedValue'.
     * 
     * @param guarAccumulatedValue the value of field
     * 'guarAccumulatedValue'.
     */
    public void setGuarAccumulatedValue(java.math.BigDecimal guarAccumulatedValue)
    {
        this._guarAccumulatedValue = guarAccumulatedValue;
        
        super.setVoChanged(true);
    } //-- void setGuarAccumulatedValue(java.math.BigDecimal) 

    /**
     * Method setInsuranceInforceSets the value of field
     * 'insuranceInforce'.
     * 
     * @param insuranceInforce the value of field 'insuranceInforce'
     */
    public void setInsuranceInforce(java.math.BigDecimal insuranceInforce)
    {
        this._insuranceInforce = insuranceInforce;
        
        super.setVoChanged(true);
    } //-- void setInsuranceInforce(java.math.BigDecimal) 

    /**
     * Method setInterestProceedsSets the value of field
     * 'interestProceeds'.
     * 
     * @param interestProceeds the value of field 'interestProceeds'
     */
    public void setInterestProceeds(java.math.BigDecimal interestProceeds)
    {
        this._interestProceeds = interestProceeds;
        
        super.setVoChanged(true);
    } //-- void setInterestProceeds(java.math.BigDecimal) 

    /**
     * Method setLiabilitySets the value of field 'liability'.
     * 
     * @param liability the value of field 'liability'.
     */
    public void setLiability(java.math.BigDecimal liability)
    {
        this._liability = liability;
        
        super.setVoChanged(true);
    } //-- void setLiability(java.math.BigDecimal) 

    /**
     * Method setMaxCommissionAmountSets the value of field
     * 'maxCommissionAmount'.
     * 
     * @param maxCommissionAmount the value of field
     * 'maxCommissionAmount'.
     */
    public void setMaxCommissionAmount(java.math.BigDecimal maxCommissionAmount)
    {
        this._maxCommissionAmount = maxCommissionAmount;
        
        super.setVoChanged(true);
    } //-- void setMaxCommissionAmount(java.math.BigDecimal) 

    /**
     * Method setNetAmountSets the value of field 'netAmount'.
     * 
     * @param netAmount the value of field 'netAmount'.
     */
    public void setNetAmount(java.math.BigDecimal netAmount)
    {
        this._netAmount = netAmount;
        
        super.setVoChanged(true);
    } //-- void setNetAmount(java.math.BigDecimal) 

    /**
     * Method setNetAmountAtRiskSets the value of field
     * 'netAmountAtRisk'.
     * 
     * @param netAmountAtRisk the value of field 'netAmountAtRisk'.
     */
    public void setNetAmountAtRisk(java.math.BigDecimal netAmountAtRisk)
    {
        this._netAmountAtRisk = netAmountAtRisk;
        
        super.setVoChanged(true);
    } //-- void setNetAmountAtRisk(java.math.BigDecimal) 

    /**
     * Method setNetIncomeAttributableSets the value of field
     * 'netIncomeAttributable'.
     * 
     * @param netIncomeAttributable the value of field
     * 'netIncomeAttributable'.
     */
    public void setNetIncomeAttributable(java.math.BigDecimal netIncomeAttributable)
    {
        this._netIncomeAttributable = netIncomeAttributable;
        
        super.setVoChanged(true);
    } //-- void setNetIncomeAttributable(java.math.BigDecimal) 

    /**
     * Method setPrevChargeDeductAmountSets the value of field
     * 'prevChargeDeductAmount'.
     * 
     * @param prevChargeDeductAmount the value of field
     * 'prevChargeDeductAmount'.
     */
    public void setPrevChargeDeductAmount(java.math.BigDecimal prevChargeDeductAmount)
    {
        this._prevChargeDeductAmount = prevChargeDeductAmount;
        
        super.setVoChanged(true);
    } //-- void setPrevChargeDeductAmount(java.math.BigDecimal) 

    /**
     * Method setPrevComplexChangeValueSets the value of field
     * 'prevComplexChangeValue'.
     * 
     * @param prevComplexChangeValue the value of field
     * 'prevComplexChangeValue'.
     */
    public void setPrevComplexChangeValue(java.lang.String prevComplexChangeValue)
    {
        this._prevComplexChangeValue = prevComplexChangeValue;
        
        super.setVoChanged(true);
    } //-- void setPrevComplexChangeValue(java.lang.String) 

    /**
     * Method setPrevCumGLPSets the value of field 'prevCumGLP'.
     * 
     * @param prevCumGLP the value of field 'prevCumGLP'.
     */
    public void setPrevCumGLP(java.math.BigDecimal prevCumGLP)
    {
        this._prevCumGLP = prevCumGLP;
        
        super.setVoChanged(true);
    } //-- void setPrevCumGLP(java.math.BigDecimal) 

    /**
     * Method setPrevGuidelineLevelPremiumSets the value of field
     * 'prevGuidelineLevelPremium'.
     * 
     * @param prevGuidelineLevelPremium the value of field
     * 'prevGuidelineLevelPremium'.
     */
    public void setPrevGuidelineLevelPremium(java.math.BigDecimal prevGuidelineLevelPremium)
    {
        this._prevGuidelineLevelPremium = prevGuidelineLevelPremium;
        
        super.setVoChanged(true);
    } //-- void setPrevGuidelineLevelPremium(java.math.BigDecimal) 

    /**
     * Method setPrevGuidelineSinglePremiumSets the value of field
     * 'prevGuidelineSinglePremium'.
     * 
     * @param prevGuidelineSinglePremium the value of field
     * 'prevGuidelineSinglePremium'.
     */
    public void setPrevGuidelineSinglePremium(java.math.BigDecimal prevGuidelineSinglePremium)
    {
        this._prevGuidelineSinglePremium = prevGuidelineSinglePremium;
        
        super.setVoChanged(true);
    } //-- void setPrevGuidelineSinglePremium(java.math.BigDecimal) 

    /**
     * Method setPrevMECDateSets the value of field 'prevMECDate'.
     * 
     * @param prevMECDate the value of field 'prevMECDate'.
     */
    public void setPrevMECDate(java.lang.String prevMECDate)
    {
        this._prevMECDate = prevMECDate;
        
        super.setVoChanged(true);
    } //-- void setPrevMECDate(java.lang.String) 

    public void setPrevMAPEndDate(java.lang.String prevMAPEndDate)
    {
        this._prevMAPEndDate = prevMAPEndDate;
        
        super.setVoChanged(true);
    } 

    /**
     * Method setPrevMECGuidelineLevelPremSets the value of field
     * 'prevMECGuidelineLevelPrem'.
     * 
     * @param prevMECGuidelineLevelPrem the value of field
     * 'prevMECGuidelineLevelPrem'.
     */
    public void setPrevMECGuidelineLevelPrem(java.math.BigDecimal prevMECGuidelineLevelPrem)
    {
        this._prevMECGuidelineLevelPrem = prevMECGuidelineLevelPrem;
        
        super.setVoChanged(true);
    } //-- void setPrevMECGuidelineLevelPrem(java.math.BigDecimal) 

    /**
     * Method setPrevMECGuidelineSinglePremSets the value of field
     * 'prevMECGuidelineSinglePrem'.
     * 
     * @param prevMECGuidelineSinglePrem the value of field
     * 'prevMECGuidelineSinglePrem'.
     */
    public void setPrevMECGuidelineSinglePrem(java.math.BigDecimal prevMECGuidelineSinglePrem)
    {
        this._prevMECGuidelineSinglePrem = prevMECGuidelineSinglePrem;
        
        super.setVoChanged(true);
    } //-- void setPrevMECGuidelineSinglePrem(java.math.BigDecimal) 

    /**
     * Method setPrevMECStatusCTSets the value of field
     * 'prevMECStatusCT'.
     * 
     * @param prevMECStatusCT the value of field 'prevMECStatusCT'.
     */
    public void setPrevMECStatusCT(java.lang.String prevMECStatusCT)
    {
        this._prevMECStatusCT = prevMECStatusCT;
        
        super.setVoChanged(true);
    } //-- void setPrevMECStatusCT(java.lang.String) 

    /**
     * Method setPrevTamraSets the value of field 'prevTamra'.
     * 
     * @param prevTamra the value of field 'prevTamra'.
     */
    public void setPrevTamra(java.math.BigDecimal prevTamra)
    {
        this._prevTamra = prevTamra;
        
        super.setVoChanged(true);
    } //-- void setPrevTamra(java.math.BigDecimal) 

    public void setPrevTamraInitAdjValue(java.math.BigDecimal prevTamraInitAdjValue)
    {
        this._prevTamraInitAdjValue = prevTamraInitAdjValue;
        
        super.setVoChanged(true);
    } //-- void setPrevTamra(java.math.BigDecimal) 

    /**
     * Method setPrevTamraStartDateSets the value of field
     * 'prevTamraStartDate'.
     * 
     * @param prevTamraStartDate the value of field
     * 'prevTamraStartDate'.
     */
    public void setPrevTamraStartDate(java.lang.String prevTamraStartDate)
    {
        this._prevTamraStartDate = prevTamraStartDate;
        
        super.setVoChanged(true);
    } //-- void setPrevTamraStartDate(java.lang.String) 

    /**
     * Method setPrevTotalFaceAmountSets the value of field
     * 'prevTotalFaceAmount'.
     * 
     * @param prevTotalFaceAmount the value of field
     * 'prevTotalFaceAmount'.
     */
    public void setPrevTotalFaceAmount(java.math.BigDecimal prevTotalFaceAmount)
    {
        this._prevTotalFaceAmount = prevTotalFaceAmount;
        
        super.setVoChanged(true);
    } //-- void setPrevTotalFaceAmount(java.math.BigDecimal) 

    /**
     * Method setPriorCostBasisSets the value of field
     * 'priorCostBasis'.
     * 
     * @param priorCostBasis the value of field 'priorCostBasis'.
     */
    public void setPriorCostBasis(java.math.BigDecimal priorCostBasis)
    {
        this._priorCostBasis = priorCostBasis;
        
        super.setVoChanged(true);
    } //-- void setPriorCostBasis(java.math.BigDecimal) 

    /**
     * Method setPriorDueDateSets the value of field
     * 'priorDueDate'.
     * 
     * @param priorDueDate the value of field 'priorDueDate'.
     */
    public void setPriorDueDate(java.lang.String priorDueDate)
    {
        this._priorDueDate = priorDueDate;
        
        super.setVoChanged(true);
    } //-- void setPriorDueDate(java.lang.String) 

    /**
     * Method setPriorExtractDateSets the value of field
     * 'priorExtractDate'.
     * 
     * @param priorExtractDate the value of field 'priorExtractDate'
     */
    public void setPriorExtractDate(java.lang.String priorExtractDate)
    {
        this._priorExtractDate = priorExtractDate;
        
        super.setVoChanged(true);
    } //-- void setPriorExtractDate(java.lang.String) 

    /**
     * Method setPriorFixedAmountSets the value of field
     * 'priorFixedAmount'.
     * 
     * @param priorFixedAmount the value of field 'priorFixedAmount'
     */
    public void setPriorFixedAmount(java.math.BigDecimal priorFixedAmount)
    {
        this._priorFixedAmount = priorFixedAmount;
        
        super.setVoChanged(true);
    } //-- void setPriorFixedAmount(java.math.BigDecimal) 

    /**
     * Method setPriorInitialCYAccumValueSets the value of field
     * 'priorInitialCYAccumValue'.
     * 
     * @param priorInitialCYAccumValue the value of field
     * 'priorInitialCYAccumValue'.
     */
    public void setPriorInitialCYAccumValue(java.math.BigDecimal priorInitialCYAccumValue)
    {
        this._priorInitialCYAccumValue = priorInitialCYAccumValue;
        
        super.setVoChanged(true);
    } //-- void setPriorInitialCYAccumValue(java.math.BigDecimal) 

    /**
     * Method setPriorLapseDateSets the value of field
     * 'priorLapseDate'.
     * 
     * @param priorLapseDate the value of field 'priorLapseDate'.
     */
    public void setPriorLapseDate(java.lang.String priorLapseDate)
    {
        this._priorLapseDate = priorLapseDate;
        
        super.setVoChanged(true);
    } //-- void setPriorLapseDate(java.lang.String) 

    /**
     * Method setPriorLapsePendingDateSets the value of field
     * 'priorLapsePendingDate'.
     * 
     * @param priorLapsePendingDate the value of field
     * 'priorLapsePendingDate'.
     */
    public void setPriorLapsePendingDate(java.lang.String priorLapsePendingDate)
    {
        this._priorLapsePendingDate = priorLapsePendingDate;
        
        super.setVoChanged(true);
    } //-- void setPriorLapsePendingDate(java.lang.String) 

    /**
     * Method setPriorLastSettlementValDateSets the value of field
     * 'priorLastSettlementValDate'.
     * 
     * @param priorLastSettlementValDate the value of field
     * 'priorLastSettlementValDate'.
     */
    public void setPriorLastSettlementValDate(java.lang.String priorLastSettlementValDate)
    {
        this._priorLastSettlementValDate = priorLastSettlementValDate;
        
        super.setVoChanged(true);
    } //-- void setPriorLastSettlementValDate(java.lang.String) 

    /**
     * Method setPriorRecoveredCostBasisSets the value of field
     * 'priorRecoveredCostBasis'.
     * 
     * @param priorRecoveredCostBasis the value of field
     * 'priorRecoveredCostBasis'.
     */
    public void setPriorRecoveredCostBasis(java.math.BigDecimal priorRecoveredCostBasis)
    {
        this._priorRecoveredCostBasis = priorRecoveredCostBasis;
        
        super.setVoChanged(true);
    } //-- void setPriorRecoveredCostBasis(java.math.BigDecimal) 

    /**
     * Method setPriorRemainingBeneficiariesSets the value of field
     * 'priorRemainingBeneficiaries'.
     * 
     * @param priorRemainingBeneficiaries the value of field
     * 'priorRemainingBeneficiaries'.
     */
    public void setPriorRemainingBeneficiaries(int priorRemainingBeneficiaries)
    {
        this._priorRemainingBeneficiaries = priorRemainingBeneficiaries;
        
        super.setVoChanged(true);
        this._has_priorRemainingBeneficiaries = true;
    } //-- void setPriorRemainingBeneficiaries(int) 

    /**
     * Method setPriorSettlementAmountSets the value of field
     * 'priorSettlementAmount'.
     * 
     * @param priorSettlementAmount the value of field
     * 'priorSettlementAmount'.
     */
    public void setPriorSettlementAmount(java.math.BigDecimal priorSettlementAmount)
    {
        this._priorSettlementAmount = priorSettlementAmount;
        
        super.setVoChanged(true);
    } //-- void setPriorSettlementAmount(java.math.BigDecimal) 

    /**
     * Method setPriorTotalActiveBeneficiariesSets the value of
     * field 'priorTotalActiveBeneficiaries'.
     * 
     * @param priorTotalActiveBeneficiaries the value of field
     * 'priorTotalActiveBeneficiaries'.
     */
    public void setPriorTotalActiveBeneficiaries(int priorTotalActiveBeneficiaries)
    {
        this._priorTotalActiveBeneficiaries = priorTotalActiveBeneficiaries;
        
        super.setVoChanged(true);
        this._has_priorTotalActiveBeneficiaries = true;
    } //-- void setPriorTotalActiveBeneficiaries(int) 

    /**
     * Method setSevenPayRateSets the value of field
     * 'sevenPayRate'.
     * 
     * @param sevenPayRate the value of field 'sevenPayRate'.
     */
    public void setSevenPayRate(java.math.BigDecimal sevenPayRate)
    {
        this._sevenPayRate = sevenPayRate;
        
        super.setVoChanged(true);
    } //-- void setSevenPayRate(java.math.BigDecimal) 

    /**
     * Method setSurrenderValueSets the value of field
     * 'surrenderValue'.
     * 
     * @param surrenderValue the value of field 'surrenderValue'.
     */
    public void setSurrenderValue(java.math.BigDecimal surrenderValue)
    {
        this._surrenderValue = surrenderValue;
        
        super.setVoChanged(true);
    } //-- void setSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setTaxableBenefitSets the value of field
     * 'taxableBenefit'.
     * 
     * @param taxableBenefit the value of field 'taxableBenefit'.
     */
    public void setTaxableBenefit(java.math.BigDecimal taxableBenefit)
    {
        this._taxableBenefit = taxableBenefit;
        
        super.setVoChanged(true);
    } //-- void setTaxableBenefit(java.math.BigDecimal) 

    /**
     * Method setTaxableIndicatorSets the value of field
     * 'taxableIndicator'.
     * 
     * @param taxableIndicator the value of field 'taxableIndicator'
     */
    public void setTaxableIndicator(java.lang.String taxableIndicator)
    {
        this._taxableIndicator = taxableIndicator;
        
        super.setVoChanged(true);
    } //-- void setTaxableIndicator(java.lang.String) 

	public void setCurrentDeathBenefit(java.math.BigDecimal currentDeathBenefit) {
		this._currentDeathBenefit = currentDeathBenefit;
        super.setVoChanged(true);
	}

    public java.math.BigDecimal getCurrentDeathBenefit() {
		return _currentDeathBenefit;
	}

    public java.math.BigDecimal getCurrIntRate() {
		return _currIntRate;
	}

	public void setCurrIntRate(java.math.BigDecimal currIntRate) {
		this._currIntRate = currIntRate;
        super.setVoChanged(true);
	}


	public java.math.BigDecimal getCurrentCorridorPercent() {
		return _currentCorridorPercent;
	}


	public void setCurrentCorridorPercent(java.math.BigDecimal currentCorridorPercent) {
		this._currentCorridorPercent = currentCorridorPercent;
        super.setVoChanged(true);
	}


	public java.math.BigDecimal getSurrenderCharge() {
		return _surrenderCharge;
	}


	public void setSurrenderCharge(java.math.BigDecimal surrenderCharge) {
		this._surrenderCharge = surrenderCharge;
        super.setVoChanged(true);
	}

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FinancialHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FinancialHistoryVO) Unmarshaller.unmarshal(edit.common.vo.FinancialHistoryVO.class, reader);
    } //-- edit.common.vo.FinancialHistoryVO unmarshal(java.io.Reader) 

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
