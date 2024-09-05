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
 * Class LifeVO.
 * 
 * @version $Revision$ $Date$
 */
public class LifeVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _lifePK
     */
    private long _lifePK;

    /**
     * keeps track of state for field: _lifePK
     */
    private boolean _has_lifePK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _deathBenefitOptionCT
     */
    private java.lang.String _deathBenefitOptionCT;

    /**
     * Field _option7702CT
     */
    private java.lang.String _option7702CT;

    /**
     * Field _guidelineSinglePremium
     */
    private java.math.BigDecimal _guidelineSinglePremium;

    /**
     * Field _guidelineLevelPremium
     */
    private java.math.BigDecimal _guidelineLevelPremium;

    /**
     * Field _tamra
     */
    private java.math.BigDecimal _tamra;

    /**
     * Field _tamraInitAdjValue
     */
    private java.math.BigDecimal _tamraInitAdjValue;

    /**
     * Field _faceAmount
     */
    private java.math.BigDecimal _faceAmount;

    /**
     * Field _tamraStartDate
     */
    private java.lang.String _tamraStartDate;

    /**
     * Field _MAPEndDate
     */
    private java.lang.String _MAPEndDate;

    /**
     * Field _startNew7PayIndicator
     */
    private java.lang.String _startNew7PayIndicator;

    /**
     * Field _MECGuidelineSinglePremium
     */
    private java.math.BigDecimal _MECGuidelineSinglePremium;

    /**
     * Field _MECGuidelineLevelPremium
     */
    private java.math.BigDecimal _MECGuidelineLevelPremium;

    /**
     * Field _MECStatusCT
     */
    private java.lang.String _MECStatusCT;

    /**
     * Field _MECDate
     */
    private java.lang.String _MECDate;

    /**
     * Field _pendingDBOChangeInd
     */
    private java.lang.String _pendingDBOChangeInd;

    /**
     * Field _cumGuidelineLevelPremium
     */
    private java.math.BigDecimal _cumGuidelineLevelPremium;

    /**
     * Field _maximumNetAmountAtRisk
     */
    private java.math.BigDecimal _maximumNetAmountAtRisk;

    /**
     * Field _paidToDate
     */
    private java.lang.String _paidToDate;

    /**
     * Field _lapsePendingDate
     */
    private java.lang.String _lapsePendingDate;

    /**
     * Field _lapseDate
     */
    private java.lang.String _lapseDate;

    /**
     * Field _term
     */
    private int _term;

    /**
     * keeps track of state for field: _term
     */
    private boolean _has_term;

    /**
     * Field _nonForfeitureOptionCT
     */
    private java.lang.String _nonForfeitureOptionCT;

    /**
     * Field _dueAndUnpaid
     */
    private java.math.BigDecimal _dueAndUnpaid;

    /**
     * Field _guarPaidUpTerm
     */
    private java.math.BigDecimal _guarPaidUpTerm;

    /**
     * Field _nonGuarPaidUpTerm
     */
    private java.math.BigDecimal _nonGuarPaidUpTerm;

    /**
     * Field _oneYearTerm
     */
    private java.math.BigDecimal _oneYearTerm;

    /**
     * Field _currentDeathBenefit
     */
    private java.math.BigDecimal _currentDeathBenefit;

    /**
     * Field _mortalityCredit
     */
    private java.math.BigDecimal _mortalityCredit;

    /**
     * Field _endowmentCredit
     */
    private java.math.BigDecimal _endowmentCredit;

    /**
     * Field _excessInterestCredit
     */
    private java.math.BigDecimal _excessInterestCredit;

    /**
     * Field _paidUpAge
     */
    private int _paidUpAge;

    /**
     * keeps track of state for field: _paidUpAge
     */
    private boolean _has_paidUpAge;

    /**
     * Field _RPUDeathBenefit
     */
    private java.math.BigDecimal _RPUDeathBenefit;

    /**
     * Field _paidUpTermDate
     */
    private java.lang.String _paidUpTermDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public LifeVO() {
        super();
    } //-- edit.common.vo.LifeVO()


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
        
        if (obj instanceof LifeVO) {
        
            LifeVO temp = (LifeVO)obj;
            if (this._lifePK != temp._lifePK)
                return false;
            if (this._has_lifePK != temp._has_lifePK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._deathBenefitOptionCT != null) {
                if (temp._deathBenefitOptionCT == null) return false;
                else if (!(this._deathBenefitOptionCT.equals(temp._deathBenefitOptionCT))) 
                    return false;
            }
            else if (temp._deathBenefitOptionCT != null)
                return false;
            if (this._option7702CT != null) {
                if (temp._option7702CT == null) return false;
                else if (!(this._option7702CT.equals(temp._option7702CT))) 
                    return false;
            }
            else if (temp._option7702CT != null)
                return false;
            if (this._guidelineSinglePremium != null) {
                if (temp._guidelineSinglePremium == null) return false;
                else if (!(this._guidelineSinglePremium.equals(temp._guidelineSinglePremium))) 
                    return false;
            }
            else if (temp._guidelineSinglePremium != null)
                return false;
            if (this._guidelineLevelPremium != null) {
                if (temp._guidelineLevelPremium == null) return false;
                else if (!(this._guidelineLevelPremium.equals(temp._guidelineLevelPremium))) 
                    return false;
            }
            else if (temp._guidelineLevelPremium != null)
                return false;

            if (this._tamra != null) {
                if (temp._tamra == null) return false;
                else if (!(this._tamra.equals(temp._tamra))) 
                    return false;
            }
            else if (temp._tamra != null)
                return false;

            if (this._tamraInitAdjValue != null) {
                if (temp._tamraInitAdjValue == null) return false;
                else if (!(this._tamra.equals(temp._tamraInitAdjValue))) 
                    return false;
            }
            else if (temp._tamraInitAdjValue != null)
                return false;


            if (this._faceAmount != null) {
                if (temp._faceAmount == null) return false;
                else if (!(this._faceAmount.equals(temp._faceAmount))) 
                    return false;
            }
            else if (temp._faceAmount != null)
                return false;
            if (this._tamraStartDate != null) {
                if (temp._tamraStartDate == null) return false;
                else if (!(this._tamraStartDate.equals(temp._tamraStartDate))) 
                    return false;
            }
            else if (temp._tamraStartDate != null)
                return false;
            if (this._MAPEndDate != null) {
                if (temp._MAPEndDate == null) return false;
                else if (!(this._MAPEndDate.equals(temp._MAPEndDate))) 
                    return false;
            }
            else if (temp._MAPEndDate != null)
                return false;
            if (this._startNew7PayIndicator != null) {
                if (temp._startNew7PayIndicator == null) return false;
                else if (!(this._startNew7PayIndicator.equals(temp._startNew7PayIndicator))) 
                    return false;
            }
            else if (temp._startNew7PayIndicator != null)
                return false;
            if (this._MECGuidelineSinglePremium != null) {
                if (temp._MECGuidelineSinglePremium == null) return false;
                else if (!(this._MECGuidelineSinglePremium.equals(temp._MECGuidelineSinglePremium))) 
                    return false;
            }
            else if (temp._MECGuidelineSinglePremium != null)
                return false;
            if (this._MECGuidelineLevelPremium != null) {
                if (temp._MECGuidelineLevelPremium == null) return false;
                else if (!(this._MECGuidelineLevelPremium.equals(temp._MECGuidelineLevelPremium))) 
                    return false;
            }
            else if (temp._MECGuidelineLevelPremium != null)
                return false;
            if (this._MECStatusCT != null) {
                if (temp._MECStatusCT == null) return false;
                else if (!(this._MECStatusCT.equals(temp._MECStatusCT))) 
                    return false;
            }
            else if (temp._MECStatusCT != null)
                return false;
            if (this._MECDate != null) {
                if (temp._MECDate == null) return false;
                else if (!(this._MECDate.equals(temp._MECDate))) 
                    return false;
            }
            else if (temp._MECDate != null)
                return false;
            if (this._pendingDBOChangeInd != null) {
                if (temp._pendingDBOChangeInd == null) return false;
                else if (!(this._pendingDBOChangeInd.equals(temp._pendingDBOChangeInd))) 
                    return false;
            }
            else if (temp._pendingDBOChangeInd != null)
                return false;
            if (this._cumGuidelineLevelPremium != null) {
                if (temp._cumGuidelineLevelPremium == null) return false;
                else if (!(this._cumGuidelineLevelPremium.equals(temp._cumGuidelineLevelPremium))) 
                    return false;
            }
            else if (temp._cumGuidelineLevelPremium != null)
                return false;
            if (this._maximumNetAmountAtRisk != null) {
                if (temp._maximumNetAmountAtRisk == null) return false;
                else if (!(this._maximumNetAmountAtRisk.equals(temp._maximumNetAmountAtRisk))) 
                    return false;
            }
            else if (temp._maximumNetAmountAtRisk != null)
                return false;
            if (this._paidToDate != null) {
                if (temp._paidToDate == null) return false;
                else if (!(this._paidToDate.equals(temp._paidToDate))) 
                    return false;
            }
            else if (temp._paidToDate != null)
                return false;
            if (this._lapsePendingDate != null) {
                if (temp._lapsePendingDate == null) return false;
                else if (!(this._lapsePendingDate.equals(temp._lapsePendingDate))) 
                    return false;
            }
            else if (temp._lapsePendingDate != null)
                return false;
            if (this._lapseDate != null) {
                if (temp._lapseDate == null) return false;
                else if (!(this._lapseDate.equals(temp._lapseDate))) 
                    return false;
            }
            else if (temp._lapseDate != null)
                return false;
            if (this._term != temp._term)
                return false;
            if (this._has_term != temp._has_term)
                return false;
            if (this._nonForfeitureOptionCT != null) {
                if (temp._nonForfeitureOptionCT == null) return false;
                else if (!(this._nonForfeitureOptionCT.equals(temp._nonForfeitureOptionCT))) 
                    return false;
            }
            else if (temp._nonForfeitureOptionCT != null)
                return false;
            if (this._dueAndUnpaid != null) {
                if (temp._dueAndUnpaid == null) return false;
                else if (!(this._dueAndUnpaid.equals(temp._dueAndUnpaid))) 
                    return false;
            }
            else if (temp._dueAndUnpaid != null)
                return false;
            if (this._guarPaidUpTerm != null) {
                if (temp._guarPaidUpTerm == null) return false;
                else if (!(this._guarPaidUpTerm.equals(temp._guarPaidUpTerm))) 
                    return false;
            }
            else if (temp._guarPaidUpTerm != null)
                return false;
            if (this._nonGuarPaidUpTerm != null) {
                if (temp._nonGuarPaidUpTerm == null) return false;
                else if (!(this._nonGuarPaidUpTerm.equals(temp._nonGuarPaidUpTerm))) 
                    return false;
            }
            else if (temp._nonGuarPaidUpTerm != null)
                return false;
            if (this._oneYearTerm != null) {
                if (temp._oneYearTerm == null) return false;
                else if (!(this._oneYearTerm.equals(temp._oneYearTerm))) 
                    return false;
            }
            else if (temp._oneYearTerm != null)
                return false;
            if (this._currentDeathBenefit != null) {
                if (temp._currentDeathBenefit == null) return false;
                else if (!(this._currentDeathBenefit.equals(temp._currentDeathBenefit))) 
                    return false;
            }
            else if (temp._currentDeathBenefit != null)
                return false;
            if (this._mortalityCredit != null) {
                if (temp._mortalityCredit == null) return false;
                else if (!(this._mortalityCredit.equals(temp._mortalityCredit))) 
                    return false;
            }
            else if (temp._mortalityCredit != null)
                return false;
            if (this._endowmentCredit != null) {
                if (temp._endowmentCredit == null) return false;
                else if (!(this._endowmentCredit.equals(temp._endowmentCredit))) 
                    return false;
            }
            else if (temp._endowmentCredit != null)
                return false;
            if (this._excessInterestCredit != null) {
                if (temp._excessInterestCredit == null) return false;
                else if (!(this._excessInterestCredit.equals(temp._excessInterestCredit))) 
                    return false;
            }
            else if (temp._excessInterestCredit != null)
                return false;
            if (this._paidUpAge != temp._paidUpAge)
                return false;
            if (this._has_paidUpAge != temp._has_paidUpAge)
                return false;
            if (this._RPUDeathBenefit != null) {
                if (temp._RPUDeathBenefit == null) return false;
                else if (!(this._RPUDeathBenefit.equals(temp._RPUDeathBenefit))) 
                    return false;
            }
            else if (temp._RPUDeathBenefit != null)
                return false;
            if (this._paidUpTermDate != null) {
                if (temp._paidUpTermDate == null) return false;
                else if (!(this._paidUpTermDate.equals(temp._paidUpTermDate))) 
                    return false;
            }
            else if (temp._paidUpTermDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCumGuidelineLevelPremiumReturns the value of field
     * 'cumGuidelineLevelPremium'.
     * 
     * @return the value of field 'cumGuidelineLevelPremium'.
     */
    public java.math.BigDecimal getCumGuidelineLevelPremium()
    {
        return this._cumGuidelineLevelPremium;
    } //-- java.math.BigDecimal getCumGuidelineLevelPremium() 

    /**
     * Method getCurrentDeathBenefitReturns the value of field
     * 'currentDeathBenefit'.
     * 
     * @return the value of field 'currentDeathBenefit'.
     */
    public java.math.BigDecimal getCurrentDeathBenefit()
    {
        return this._currentDeathBenefit;
    } //-- java.math.BigDecimal getCurrentDeathBenefit() 

    /**
     * Method getDeathBenefitOptionCTReturns the value of field
     * 'deathBenefitOptionCT'.
     * 
     * @return the value of field 'deathBenefitOptionCT'.
     */
    public java.lang.String getDeathBenefitOptionCT()
    {
        return this._deathBenefitOptionCT;
    } //-- java.lang.String getDeathBenefitOptionCT() 

    /**
     * Method getDueAndUnpaidReturns the value of field
     * 'dueAndUnpaid'.
     * 
     * @return the value of field 'dueAndUnpaid'.
     */
    public java.math.BigDecimal getDueAndUnpaid()
    {
        return this._dueAndUnpaid;
    } //-- java.math.BigDecimal getDueAndUnpaid() 

    /**
     * Method getEndowmentCreditReturns the value of field
     * 'endowmentCredit'.
     * 
     * @return the value of field 'endowmentCredit'.
     */
    public java.math.BigDecimal getEndowmentCredit()
    {
        return this._endowmentCredit;
    } //-- java.math.BigDecimal getEndowmentCredit() 

    /**
     * Method getExcessInterestCreditReturns the value of field
     * 'excessInterestCredit'.
     * 
     * @return the value of field 'excessInterestCredit'.
     */
    public java.math.BigDecimal getExcessInterestCredit()
    {
        return this._excessInterestCredit;
    } //-- java.math.BigDecimal getExcessInterestCredit() 

    /**
     * Method getFaceAmountReturns the value of field 'faceAmount'.
     * 
     * @return the value of field 'faceAmount'.
     */
    public java.math.BigDecimal getFaceAmount()
    {
        return this._faceAmount;
    } //-- java.math.BigDecimal getFaceAmount() 

    /**
     * Method getGuarPaidUpTermReturns the value of field
     * 'guarPaidUpTerm'.
     * 
     * @return the value of field 'guarPaidUpTerm'.
     */
    public java.math.BigDecimal getGuarPaidUpTerm()
    {
        return this._guarPaidUpTerm;
    } //-- java.math.BigDecimal getGuarPaidUpTerm() 

    /**
     * Method getGuidelineLevelPremiumReturns the value of field
     * 'guidelineLevelPremium'.
     * 
     * @return the value of field 'guidelineLevelPremium'.
     */
    public java.math.BigDecimal getGuidelineLevelPremium()
    {
        return this._guidelineLevelPremium;
    } //-- java.math.BigDecimal getGuidelineLevelPremium() 

    /**
     * Method getGuidelineSinglePremiumReturns the value of field
     * 'guidelineSinglePremium'.
     * 
     * @return the value of field 'guidelineSinglePremium'.
     */
    public java.math.BigDecimal getGuidelineSinglePremium()
    {
        return this._guidelineSinglePremium;
    } //-- java.math.BigDecimal getGuidelineSinglePremium() 

    /**
     * Method getLapseDateReturns the value of field 'lapseDate'.
     * 
     * @return the value of field 'lapseDate'.
     */
    public java.lang.String getLapseDate()
    {
        return this._lapseDate;
    } //-- java.lang.String getLapseDate() 

    /**
     * Method getLapsePendingDateReturns the value of field
     * 'lapsePendingDate'.
     * 
     * @return the value of field 'lapsePendingDate'.
     */
    public java.lang.String getLapsePendingDate()
    {
        return this._lapsePendingDate;
    } //-- java.lang.String getLapsePendingDate() 

    /**
     * Method getLifePKReturns the value of field 'lifePK'.
     * 
     * @return the value of field 'lifePK'.
     */
    public long getLifePK()
    {
        return this._lifePK;
    } //-- long getLifePK() 

    /**
     * Method getMECDateReturns the value of field 'MECDate'.
     * 
     * @return the value of field 'MECDate'.
     */
    public java.lang.String getMECDate()
    {
        return this._MECDate;
    } //-- java.lang.String getMECDate() 

    /**
     * Method getMECGuidelineLevelPremiumReturns the value of field
     * 'MECGuidelineLevelPremium'.
     * 
     * @return the value of field 'MECGuidelineLevelPremium'.
     */
    public java.math.BigDecimal getMECGuidelineLevelPremium()
    {
        return this._MECGuidelineLevelPremium;
    } //-- java.math.BigDecimal getMECGuidelineLevelPremium() 

    /**
     * Method getMECGuidelineSinglePremiumReturns the value of
     * field 'MECGuidelineSinglePremium'.
     * 
     * @return the value of field 'MECGuidelineSinglePremium'.
     */
    public java.math.BigDecimal getMECGuidelineSinglePremium()
    {
        return this._MECGuidelineSinglePremium;
    } //-- java.math.BigDecimal getMECGuidelineSinglePremium() 

    /**
     * Method getMECStatusCTReturns the value of field
     * 'MECStatusCT'.
     * 
     * @return the value of field 'MECStatusCT'.
     */
    public java.lang.String getMECStatusCT()
    {
        return this._MECStatusCT;
    } //-- java.lang.String getMECStatusCT() 

    /**
     * Method getMaximumNetAmountAtRiskReturns the value of field
     * 'maximumNetAmountAtRisk'.
     * 
     * @return the value of field 'maximumNetAmountAtRisk'.
     */
    public java.math.BigDecimal getMaximumNetAmountAtRisk()
    {
        return this._maximumNetAmountAtRisk;
    } //-- java.math.BigDecimal getMaximumNetAmountAtRisk() 

    /**
     * Method getMortalityCreditReturns the value of field
     * 'mortalityCredit'.
     * 
     * @return the value of field 'mortalityCredit'.
     */
    public java.math.BigDecimal getMortalityCredit()
    {
        return this._mortalityCredit;
    } //-- java.math.BigDecimal getMortalityCredit() 

    /**
     * Method getNonForfeitureOptionCTReturns the value of field
     * 'nonForfeitureOptionCT'.
     * 
     * @return the value of field 'nonForfeitureOptionCT'.
     */
    public java.lang.String getNonForfeitureOptionCT()
    {
        return this._nonForfeitureOptionCT;
    } //-- java.lang.String getNonForfeitureOptionCT() 

    /**
     * Method getNonGuarPaidUpTermReturns the value of field
     * 'nonGuarPaidUpTerm'.
     * 
     * @return the value of field 'nonGuarPaidUpTerm'.
     */
    public java.math.BigDecimal getNonGuarPaidUpTerm()
    {
        return this._nonGuarPaidUpTerm;
    } //-- java.math.BigDecimal getNonGuarPaidUpTerm() 

    /**
     * Method getOneYearTermReturns the value of field
     * 'oneYearTerm'.
     * 
     * @return the value of field 'oneYearTerm'.
     */
    public java.math.BigDecimal getOneYearTerm()
    {
        return this._oneYearTerm;
    } //-- java.math.BigDecimal getOneYearTerm() 

    /**
     * Method getOption7702CTReturns the value of field
     * 'option7702CT'.
     * 
     * @return the value of field 'option7702CT'.
     */
    public java.lang.String getOption7702CT()
    {
        return this._option7702CT;
    } //-- java.lang.String getOption7702CT() 

    /**
     * Method getPaidToDateReturns the value of field 'paidToDate'.
     * 
     * @return the value of field 'paidToDate'.
     */
    public java.lang.String getPaidToDate()
    {
        return this._paidToDate;
    } //-- java.lang.String getPaidToDate() 

    /**
     * Method getPaidUpAgeReturns the value of field 'paidUpAge'.
     * 
     * @return the value of field 'paidUpAge'.
     */
    public int getPaidUpAge()
    {
        return this._paidUpAge;
    } //-- int getPaidUpAge() 

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
     * Method getPendingDBOChangeIndReturns the value of field
     * 'pendingDBOChangeInd'.
     * 
     * @return the value of field 'pendingDBOChangeInd'.
     */
    public java.lang.String getPendingDBOChangeInd()
    {
        return this._pendingDBOChangeInd;
    } //-- java.lang.String getPendingDBOChangeInd() 

    /**
     * Method getRPUDeathBenefitReturns the value of field
     * 'RPUDeathBenefit'.
     * 
     * @return the value of field 'RPUDeathBenefit'.
     */
    public java.math.BigDecimal getRPUDeathBenefit()
    {
        return this._RPUDeathBenefit;
    } //-- java.math.BigDecimal getRPUDeathBenefit() 

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
     * Method getStartNew7PayIndicatorReturns the value of field
     * 'startNew7PayIndicator'.
     * 
     * @return the value of field 'startNew7PayIndicator'.
     */
    public java.lang.String getStartNew7PayIndicator()
    {
        return this._startNew7PayIndicator;
    } //-- java.lang.String getStartNew7PayIndicator() 

    /**
     * Method getTamraReturns the value of field 'tamra'.
     * 
     * @return the value of field 'tamra'.
     */
    public java.math.BigDecimal getTamra()
    {
        return this._tamra;
    } //-- java.math.BigDecimal getTamra() 

    public java.math.BigDecimal getTamraInitAdjValue()
    {
        return this._tamraInitAdjValue;
    } //-- java.math.BigDecimal getTamraInitAdjValue() 

    /**
     * Method getTamraStartDateReturns the value of field
     * 'tamraStartDate'.
     * 
     * @return the value of field 'tamraStartDate'.
     */
    public java.lang.String getTamraStartDate()
    {
        return this._tamraStartDate;
    } //-- java.lang.String getTamraStartDate() 

    /**
     * Method getMAPEndDate returns the value of field
     * 'MAPEndDate'.
     * 
     * @return the value of field 'MAPEndDate'.
     */
    public java.lang.String getMAPEndDate()
    {
        return this._MAPEndDate;
    } //-- java.lang.String getMAPEndDate() 
    
    /**
     * Method getTermReturns the value of field 'term'.
     * 
     * @return the value of field 'term'.
     */
    public int getTerm()
    {
        return this._term;
    } //-- int getTerm() 

    /**
     * Method hasLifePK
     */
    public boolean hasLifePK()
    {
        return this._has_lifePK;
    } //-- boolean hasLifePK() 

    /**
     * Method hasPaidUpAge
     */
    public boolean hasPaidUpAge()
    {
        return this._has_paidUpAge;
    } //-- boolean hasPaidUpAge() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasTerm
     */
    public boolean hasTerm()
    {
        return this._has_term;
    } //-- boolean hasTerm() 

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
     * Method setCumGuidelineLevelPremiumSets the value of field
     * 'cumGuidelineLevelPremium'.
     * 
     * @param cumGuidelineLevelPremium the value of field
     * 'cumGuidelineLevelPremium'.
     */
    public void setCumGuidelineLevelPremium(java.math.BigDecimal cumGuidelineLevelPremium)
    {
        this._cumGuidelineLevelPremium = cumGuidelineLevelPremium;
        
        super.setVoChanged(true);
    } //-- void setCumGuidelineLevelPremium(java.math.BigDecimal) 

    /**
     * Method setCurrentDeathBenefitSets the value of field
     * 'currentDeathBenefit'.
     * 
     * @param currentDeathBenefit the value of field
     * 'currentDeathBenefit'.
     */
    public void setCurrentDeathBenefit(java.math.BigDecimal currentDeathBenefit)
    {
        this._currentDeathBenefit = currentDeathBenefit;
        
        super.setVoChanged(true);
    } //-- void setCurrentDeathBenefit(java.math.BigDecimal) 

    /**
     * Method setDeathBenefitOptionCTSets the value of field
     * 'deathBenefitOptionCT'.
     * 
     * @param deathBenefitOptionCT the value of field
     * 'deathBenefitOptionCT'.
     */
    public void setDeathBenefitOptionCT(java.lang.String deathBenefitOptionCT)
    {
        this._deathBenefitOptionCT = deathBenefitOptionCT;
        
        super.setVoChanged(true);
    } //-- void setDeathBenefitOptionCT(java.lang.String) 

    /**
     * Method setDueAndUnpaidSets the value of field
     * 'dueAndUnpaid'.
     * 
     * @param dueAndUnpaid the value of field 'dueAndUnpaid'.
     */
    public void setDueAndUnpaid(java.math.BigDecimal dueAndUnpaid)
    {
        this._dueAndUnpaid = dueAndUnpaid;
        
        super.setVoChanged(true);
    } //-- void setDueAndUnpaid(java.math.BigDecimal) 

    /**
     * Method setEndowmentCreditSets the value of field
     * 'endowmentCredit'.
     * 
     * @param endowmentCredit the value of field 'endowmentCredit'.
     */
    public void setEndowmentCredit(java.math.BigDecimal endowmentCredit)
    {
        this._endowmentCredit = endowmentCredit;
        
        super.setVoChanged(true);
    } //-- void setEndowmentCredit(java.math.BigDecimal) 

    /**
     * Method setExcessInterestCreditSets the value of field
     * 'excessInterestCredit'.
     * 
     * @param excessInterestCredit the value of field
     * 'excessInterestCredit'.
     */
    public void setExcessInterestCredit(java.math.BigDecimal excessInterestCredit)
    {
        this._excessInterestCredit = excessInterestCredit;
        
        super.setVoChanged(true);
    } //-- void setExcessInterestCredit(java.math.BigDecimal) 

    /**
     * Method setFaceAmountSets the value of field 'faceAmount'.
     * 
     * @param faceAmount the value of field 'faceAmount'.
     */
    public void setFaceAmount(java.math.BigDecimal faceAmount)
    {
        this._faceAmount = faceAmount;
        
        super.setVoChanged(true);
    } //-- void setFaceAmount(java.math.BigDecimal) 

    /**
     * Method setGuarPaidUpTermSets the value of field
     * 'guarPaidUpTerm'.
     * 
     * @param guarPaidUpTerm the value of field 'guarPaidUpTerm'.
     */
    public void setGuarPaidUpTerm(java.math.BigDecimal guarPaidUpTerm)
    {
        this._guarPaidUpTerm = guarPaidUpTerm;
        
        super.setVoChanged(true);
    } //-- void setGuarPaidUpTerm(java.math.BigDecimal) 

    /**
     * Method setGuidelineLevelPremiumSets the value of field
     * 'guidelineLevelPremium'.
     * 
     * @param guidelineLevelPremium the value of field
     * 'guidelineLevelPremium'.
     */
    public void setGuidelineLevelPremium(java.math.BigDecimal guidelineLevelPremium)
    {
        this._guidelineLevelPremium = guidelineLevelPremium;
        
        super.setVoChanged(true);
    } //-- void setGuidelineLevelPremium(java.math.BigDecimal) 

    /**
     * Method setGuidelineSinglePremiumSets the value of field
     * 'guidelineSinglePremium'.
     * 
     * @param guidelineSinglePremium the value of field
     * 'guidelineSinglePremium'.
     */
    public void setGuidelineSinglePremium(java.math.BigDecimal guidelineSinglePremium)
    {
        this._guidelineSinglePremium = guidelineSinglePremium;
        
        super.setVoChanged(true);
    } //-- void setGuidelineSinglePremium(java.math.BigDecimal) 

    /**
     * Method setLapseDateSets the value of field 'lapseDate'.
     * 
     * @param lapseDate the value of field 'lapseDate'.
     */
    public void setLapseDate(java.lang.String lapseDate)
    {
        this._lapseDate = lapseDate;
        
        super.setVoChanged(true);
    } //-- void setLapseDate(java.lang.String) 

    /**
     * Method setLapsePendingDateSets the value of field
     * 'lapsePendingDate'.
     * 
     * @param lapsePendingDate the value of field 'lapsePendingDate'
     */
    public void setLapsePendingDate(java.lang.String lapsePendingDate)
    {
        this._lapsePendingDate = lapsePendingDate;
        
        super.setVoChanged(true);
    } //-- void setLapsePendingDate(java.lang.String) 

    /**
     * Method setLifePKSets the value of field 'lifePK'.
     * 
     * @param lifePK the value of field 'lifePK'.
     */
    public void setLifePK(long lifePK)
    {
        this._lifePK = lifePK;
        
        super.setVoChanged(true);
        this._has_lifePK = true;
    } //-- void setLifePK(long) 

    /**
     * Method setMECDateSets the value of field 'MECDate'.
     * 
     * @param MECDate the value of field 'MECDate'.
     */
    public void setMECDate(java.lang.String MECDate)
    {
        this._MECDate = MECDate;
        
        super.setVoChanged(true);
    } //-- void setMECDate(java.lang.String) 

    /**
     * Method setMECGuidelineLevelPremiumSets the value of field
     * 'MECGuidelineLevelPremium'.
     * 
     * @param MECGuidelineLevelPremium the value of field
     * 'MECGuidelineLevelPremium'.
     */
    public void setMECGuidelineLevelPremium(java.math.BigDecimal MECGuidelineLevelPremium)
    {
        this._MECGuidelineLevelPremium = MECGuidelineLevelPremium;
        
        super.setVoChanged(true);
    } //-- void setMECGuidelineLevelPremium(java.math.BigDecimal) 

    /**
     * Method setMECGuidelineSinglePremiumSets the value of field
     * 'MECGuidelineSinglePremium'.
     * 
     * @param MECGuidelineSinglePremium the value of field
     * 'MECGuidelineSinglePremium'.
     */
    public void setMECGuidelineSinglePremium(java.math.BigDecimal MECGuidelineSinglePremium)
    {
        this._MECGuidelineSinglePremium = MECGuidelineSinglePremium;
        
        super.setVoChanged(true);
    } //-- void setMECGuidelineSinglePremium(java.math.BigDecimal) 

    /**
     * Method setMECStatusCTSets the value of field 'MECStatusCT'.
     * 
     * @param MECStatusCT the value of field 'MECStatusCT'.
     */
    public void setMECStatusCT(java.lang.String MECStatusCT)
    {
        this._MECStatusCT = MECStatusCT;
        
        super.setVoChanged(true);
    } //-- void setMECStatusCT(java.lang.String) 

    /**
     * Method setMaximumNetAmountAtRiskSets the value of field
     * 'maximumNetAmountAtRisk'.
     * 
     * @param maximumNetAmountAtRisk the value of field
     * 'maximumNetAmountAtRisk'.
     */
    public void setMaximumNetAmountAtRisk(java.math.BigDecimal maximumNetAmountAtRisk)
    {
        this._maximumNetAmountAtRisk = maximumNetAmountAtRisk;
        
        super.setVoChanged(true);
    } //-- void setMaximumNetAmountAtRisk(java.math.BigDecimal) 

    /**
     * Method setMortalityCreditSets the value of field
     * 'mortalityCredit'.
     * 
     * @param mortalityCredit the value of field 'mortalityCredit'.
     */
    public void setMortalityCredit(java.math.BigDecimal mortalityCredit)
    {
        this._mortalityCredit = mortalityCredit;
        
        super.setVoChanged(true);
    } //-- void setMortalityCredit(java.math.BigDecimal) 

    /**
     * Method setNonForfeitureOptionCTSets the value of field
     * 'nonForfeitureOptionCT'.
     * 
     * @param nonForfeitureOptionCT the value of field
     * 'nonForfeitureOptionCT'.
     */
    public void setNonForfeitureOptionCT(java.lang.String nonForfeitureOptionCT)
    {
        this._nonForfeitureOptionCT = nonForfeitureOptionCT;
        
        super.setVoChanged(true);
    } //-- void setNonForfeitureOptionCT(java.lang.String) 

    /**
     * Method setNonGuarPaidUpTermSets the value of field
     * 'nonGuarPaidUpTerm'.
     * 
     * @param nonGuarPaidUpTerm the value of field
     * 'nonGuarPaidUpTerm'.
     */
    public void setNonGuarPaidUpTerm(java.math.BigDecimal nonGuarPaidUpTerm)
    {
        this._nonGuarPaidUpTerm = nonGuarPaidUpTerm;
        
        super.setVoChanged(true);
    } //-- void setNonGuarPaidUpTerm(java.math.BigDecimal) 

    /**
     * Method setOneYearTermSets the value of field 'oneYearTerm'.
     * 
     * @param oneYearTerm the value of field 'oneYearTerm'.
     */
    public void setOneYearTerm(java.math.BigDecimal oneYearTerm)
    {
        this._oneYearTerm = oneYearTerm;
        
        super.setVoChanged(true);
    } //-- void setOneYearTerm(java.math.BigDecimal) 

    /**
     * Method setOption7702CTSets the value of field
     * 'option7702CT'.
     * 
     * @param option7702CT the value of field 'option7702CT'.
     */
    public void setOption7702CT(java.lang.String option7702CT)
    {
        this._option7702CT = option7702CT;
        
        super.setVoChanged(true);
    } //-- void setOption7702CT(java.lang.String) 

    /**
     * Method setPaidToDateSets the value of field 'paidToDate'.
     * 
     * @param paidToDate the value of field 'paidToDate'.
     */
    public void setPaidToDate(java.lang.String paidToDate)
    {
        this._paidToDate = paidToDate;
        
        super.setVoChanged(true);
    } //-- void setPaidToDate(java.lang.String) 

    /**
     * Method setPaidUpAgeSets the value of field 'paidUpAge'.
     * 
     * @param paidUpAge the value of field 'paidUpAge'.
     */
    public void setPaidUpAge(int paidUpAge)
    {
        this._paidUpAge = paidUpAge;
        
        super.setVoChanged(true);
        this._has_paidUpAge = true;
    } //-- void setPaidUpAge(int) 

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
     * Method setPendingDBOChangeIndSets the value of field
     * 'pendingDBOChangeInd'.
     * 
     * @param pendingDBOChangeInd the value of field
     * 'pendingDBOChangeInd'.
     */
    public void setPendingDBOChangeInd(java.lang.String pendingDBOChangeInd)
    {
        this._pendingDBOChangeInd = pendingDBOChangeInd;
        
        super.setVoChanged(true);
    } //-- void setPendingDBOChangeInd(java.lang.String) 

    /**
     * Method setRPUDeathBenefitSets the value of field
     * 'RPUDeathBenefit'.
     * 
     * @param RPUDeathBenefit the value of field 'RPUDeathBenefit'.
     */
    public void setRPUDeathBenefit(java.math.BigDecimal RPUDeathBenefit)
    {
        this._RPUDeathBenefit = RPUDeathBenefit;
        
        super.setVoChanged(true);
    } //-- void setRPUDeathBenefit(java.math.BigDecimal) 

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
     * Method setStartNew7PayIndicatorSets the value of field
     * 'startNew7PayIndicator'.
     * 
     * @param startNew7PayIndicator the value of field
     * 'startNew7PayIndicator'.
     */
    public void setStartNew7PayIndicator(java.lang.String startNew7PayIndicator)
    {
        this._startNew7PayIndicator = startNew7PayIndicator;
        
        super.setVoChanged(true);
    } //-- void setStartNew7PayIndicator(java.lang.String) 

    /**
     * Method setTamraSets the value of field 'tamra'.
     * 
     * @param tamra the value of field 'tamra'.
     */
    public void setTamra(java.math.BigDecimal tamra)
    {
        this._tamra = tamra;
        
        super.setVoChanged(true);
    } //-- void setTamra(java.math.BigDecimal) 

    public void setTamraInitAdjValue(java.math.BigDecimal tamraInitAdjValue)
    {
        this._tamraInitAdjValue = tamraInitAdjValue;
        
        super.setVoChanged(true);
    } //-- void setTamraInitAdjValue(java.math.BigDecimal) 

    /**
     * Method setTamraStartDateSets the value of field
     * 'tamraStartDate'.
     * 
     * @param tamraStartDate the value of field 'tamraStartDate'.
     */
    public void setTamraStartDate(java.lang.String tamraStartDate)
    {
        this._tamraStartDate = tamraStartDate;
        
        super.setVoChanged(true);
    } //-- void setTamraStartDate(java.lang.String) 

    /**
     * Method setMAPEndDate sets the value of field
     * 'MAPEndDate'.
     * 
     * @param MAPEndDate the value of field 'MAPEndDate'.
     */
    public void setMAPEndDate(String MAPEndDate)
    {
        this._MAPEndDate = MAPEndDate;
        
        super.setVoChanged(true);
    } //-- void setMAPEndDate(java.lang.String) 

    /**
     * Method setTermSets the value of field 'term'.
     * 
     * @param term the value of field 'term'.
     */
    public void setTerm(int term)
    {
        this._term = term;
        
        super.setVoChanged(true);
        this._has_term = true;
    } //-- void setTerm(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.LifeVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.LifeVO) Unmarshaller.unmarshal(edit.common.vo.LifeVO.class, reader);
    } //-- edit.common.vo.LifeVO unmarshal(java.io.Reader) 

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
