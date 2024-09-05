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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentRateVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _investmentRatePK
     */
    private long _investmentRatePK;

    /**
     * keeps track of state for field: _investmentRatePK
     */
    private boolean _has_investmentRatePK;

    /**
     * Field _investmentFK
     */
    private long _investmentFK;

    /**
     * keeps track of state for field: _investmentFK
     */
    private boolean _has_investmentFK;

    /**
     * Field _fundType
     */
    private java.lang.String _fundType;

    /**
     * Field _indexingMethod
     */
    private java.lang.String _indexingMethod;

    /**
     * Field _indexCapMinimum
     */
    private double _indexCapMinimum;

    /**
     * keeps track of state for field: _indexCapMinimum
     */
    private boolean _has_indexCapMinimum;

    /**
     * Field _participationRate
     */
    private double _participationRate;

    /**
     * keeps track of state for field: _participationRate
     */
    private boolean _has_participationRate;

    /**
     * Field _indexMargin
     */
    private double _indexMargin;

    /**
     * keeps track of state for field: _indexMargin
     */
    private boolean _has_indexMargin;

    /**
     * Field _indexCapRateGuarPeriod
     */
    private int _indexCapRateGuarPeriod;

    /**
     * keeps track of state for field: _indexCapRateGuarPeriod
     */
    private boolean _has_indexCapRateGuarPeriod;

    /**
     * Field _MVAStartingGuarPeriod
     */
    private int _MVAStartingGuarPeriod;

    /**
     * keeps track of state for field: _MVAStartingGuarPeriod
     */
    private boolean _has_MVAStartingGuarPeriod;

    /**
     * Field _premiumBonusDuration
     */
    private int _premiumBonusDuration;

    /**
     * keeps track of state for field: _premiumBonusDuration
     */
    private boolean _has_premiumBonusDuration;

    /**
     * Field _totalPremPercentAllowed
     */
    private double _totalPremPercentAllowed;

    /**
     * keeps track of state for field: _totalPremPercentAllowed
     */
    private boolean _has_totalPremPercentAllowed;

    /**
     * Field _minimumTransferAmount
     */
    private double _minimumTransferAmount;

    /**
     * keeps track of state for field: _minimumTransferAmount
     */
    private boolean _has_minimumTransferAmount;

    /**
     * Field _currentRate
     */
    private double _currentRate;

    /**
     * keeps track of state for field: _currentRate
     */
    private boolean _has_currentRate;

    /**
     * Field _bonusRate
     */
    private double _bonusRate;

    /**
     * keeps track of state for field: _bonusRate
     */
    private boolean _has_bonusRate;

    /**
     * Field _initialGuaranteedPeriod
     */
    private double _initialGuaranteedPeriod;

    /**
     * keeps track of state for field: _initialGuaranteedPeriod
     */
    private boolean _has_initialGuaranteedPeriod;

    /**
     * Field _currentRateDate
     */
    private java.lang.String _currentRateDate;

    /**
     * Field _guarIntRate
     */
    private double _guarIntRate;

    /**
     * keeps track of state for field: _guarIntRate
     */
    private boolean _has_guarIntRate;

    /**
     * Field _beginningIndexValue
     */
    private java.math.BigDecimal _beginningIndexValue;

    /**
     * Field _endingIndexValue
     */
    private java.math.BigDecimal _endingIndexValue;

    /**
     * Field _startingIndexValue
     */
    private java.math.BigDecimal _startingIndexValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentRateVO() {
        super();
    } //-- edit.common.vo.InvestmentRateVO()


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
        
        if (obj instanceof InvestmentRateVO) {
        
            InvestmentRateVO temp = (InvestmentRateVO)obj;
            if (this._investmentRatePK != temp._investmentRatePK)
                return false;
            if (this._has_investmentRatePK != temp._has_investmentRatePK)
                return false;
            if (this._investmentFK != temp._investmentFK)
                return false;
            if (this._has_investmentFK != temp._has_investmentFK)
                return false;
            if (this._fundType != null) {
                if (temp._fundType == null) return false;
                else if (!(this._fundType.equals(temp._fundType))) 
                    return false;
            }
            else if (temp._fundType != null)
                return false;
            if (this._indexingMethod != null) {
                if (temp._indexingMethod == null) return false;
                else if (!(this._indexingMethod.equals(temp._indexingMethod))) 
                    return false;
            }
            else if (temp._indexingMethod != null)
                return false;
            if (this._indexCapMinimum != temp._indexCapMinimum)
                return false;
            if (this._has_indexCapMinimum != temp._has_indexCapMinimum)
                return false;
            if (this._participationRate != temp._participationRate)
                return false;
            if (this._has_participationRate != temp._has_participationRate)
                return false;
            if (this._indexMargin != temp._indexMargin)
                return false;
            if (this._has_indexMargin != temp._has_indexMargin)
                return false;
            if (this._indexCapRateGuarPeriod != temp._indexCapRateGuarPeriod)
                return false;
            if (this._has_indexCapRateGuarPeriod != temp._has_indexCapRateGuarPeriod)
                return false;
            if (this._MVAStartingGuarPeriod != temp._MVAStartingGuarPeriod)
                return false;
            if (this._has_MVAStartingGuarPeriod != temp._has_MVAStartingGuarPeriod)
                return false;
            if (this._premiumBonusDuration != temp._premiumBonusDuration)
                return false;
            if (this._has_premiumBonusDuration != temp._has_premiumBonusDuration)
                return false;
            if (this._totalPremPercentAllowed != temp._totalPremPercentAllowed)
                return false;
            if (this._has_totalPremPercentAllowed != temp._has_totalPremPercentAllowed)
                return false;
            if (this._minimumTransferAmount != temp._minimumTransferAmount)
                return false;
            if (this._has_minimumTransferAmount != temp._has_minimumTransferAmount)
                return false;
            if (this._currentRate != temp._currentRate)
                return false;
            if (this._has_currentRate != temp._has_currentRate)
                return false;
            if (this._bonusRate != temp._bonusRate)
                return false;
            if (this._has_bonusRate != temp._has_bonusRate)
                return false;
            if (this._initialGuaranteedPeriod != temp._initialGuaranteedPeriod)
                return false;
            if (this._has_initialGuaranteedPeriod != temp._has_initialGuaranteedPeriod)
                return false;
            if (this._currentRateDate != null) {
                if (temp._currentRateDate == null) return false;
                else if (!(this._currentRateDate.equals(temp._currentRateDate))) 
                    return false;
            }
            else if (temp._currentRateDate != null)
                return false;
            if (this._guarIntRate != temp._guarIntRate)
                return false;
            if (this._has_guarIntRate != temp._has_guarIntRate)
                return false;
            if (this._beginningIndexValue != null) {
                if (temp._beginningIndexValue == null) return false;
                else if (!(this._beginningIndexValue.equals(temp._beginningIndexValue))) 
                    return false;
            }
            else if (temp._beginningIndexValue != null)
                return false;
            if (this._endingIndexValue != null) {
                if (temp._endingIndexValue == null) return false;
                else if (!(this._endingIndexValue.equals(temp._endingIndexValue))) 
                    return false;
            }
            else if (temp._endingIndexValue != null)
                return false;
            if (this._startingIndexValue != null) {
                if (temp._startingIndexValue == null) return false;
                else if (!(this._startingIndexValue.equals(temp._startingIndexValue))) 
                    return false;
            }
            else if (temp._startingIndexValue != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBeginningIndexValueReturns the value of field
     * 'beginningIndexValue'.
     * 
     * @return the value of field 'beginningIndexValue'.
     */
    public java.math.BigDecimal getBeginningIndexValue()
    {
        return this._beginningIndexValue;
    } //-- java.math.BigDecimal getBeginningIndexValue() 

    /**
     * Method getBonusRateReturns the value of field 'bonusRate'.
     * 
     * @return the value of field 'bonusRate'.
     */
    public double getBonusRate()
    {
        return this._bonusRate;
    } //-- double getBonusRate() 

    /**
     * Method getCurrentRateReturns the value of field
     * 'currentRate'.
     * 
     * @return the value of field 'currentRate'.
     */
    public double getCurrentRate()
    {
        return this._currentRate;
    } //-- double getCurrentRate() 

    /**
     * Method getCurrentRateDateReturns the value of field
     * 'currentRateDate'.
     * 
     * @return the value of field 'currentRateDate'.
     */
    public java.lang.String getCurrentRateDate()
    {
        return this._currentRateDate;
    } //-- java.lang.String getCurrentRateDate() 

    /**
     * Method getEndingIndexValueReturns the value of field
     * 'endingIndexValue'.
     * 
     * @return the value of field 'endingIndexValue'.
     */
    public java.math.BigDecimal getEndingIndexValue()
    {
        return this._endingIndexValue;
    } //-- java.math.BigDecimal getEndingIndexValue() 

    /**
     * Method getFundTypeReturns the value of field 'fundType'.
     * 
     * @return the value of field 'fundType'.
     */
    public java.lang.String getFundType()
    {
        return this._fundType;
    } //-- java.lang.String getFundType() 

    /**
     * Method getGuarIntRateReturns the value of field
     * 'guarIntRate'.
     * 
     * @return the value of field 'guarIntRate'.
     */
    public double getGuarIntRate()
    {
        return this._guarIntRate;
    } //-- double getGuarIntRate() 

    /**
     * Method getIndexCapMinimumReturns the value of field
     * 'indexCapMinimum'.
     * 
     * @return the value of field 'indexCapMinimum'.
     */
    public double getIndexCapMinimum()
    {
        return this._indexCapMinimum;
    } //-- double getIndexCapMinimum() 

    /**
     * Method getIndexCapRateGuarPeriodReturns the value of field
     * 'indexCapRateGuarPeriod'.
     * 
     * @return the value of field 'indexCapRateGuarPeriod'.
     */
    public int getIndexCapRateGuarPeriod()
    {
        return this._indexCapRateGuarPeriod;
    } //-- int getIndexCapRateGuarPeriod() 

    /**
     * Method getIndexMarginReturns the value of field
     * 'indexMargin'.
     * 
     * @return the value of field 'indexMargin'.
     */
    public double getIndexMargin()
    {
        return this._indexMargin;
    } //-- double getIndexMargin() 

    /**
     * Method getIndexingMethodReturns the value of field
     * 'indexingMethod'.
     * 
     * @return the value of field 'indexingMethod'.
     */
    public java.lang.String getIndexingMethod()
    {
        return this._indexingMethod;
    } //-- java.lang.String getIndexingMethod() 

    /**
     * Method getInitialGuaranteedPeriodReturns the value of field
     * 'initialGuaranteedPeriod'.
     * 
     * @return the value of field 'initialGuaranteedPeriod'.
     */
    public double getInitialGuaranteedPeriod()
    {
        return this._initialGuaranteedPeriod;
    } //-- double getInitialGuaranteedPeriod() 

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
     * Method getInvestmentRatePKReturns the value of field
     * 'investmentRatePK'.
     * 
     * @return the value of field 'investmentRatePK'.
     */
    public long getInvestmentRatePK()
    {
        return this._investmentRatePK;
    } //-- long getInvestmentRatePK() 

    /**
     * Method getMVAStartingGuarPeriodReturns the value of field
     * 'MVAStartingGuarPeriod'.
     * 
     * @return the value of field 'MVAStartingGuarPeriod'.
     */
    public int getMVAStartingGuarPeriod()
    {
        return this._MVAStartingGuarPeriod;
    } //-- int getMVAStartingGuarPeriod() 

    /**
     * Method getMinimumTransferAmountReturns the value of field
     * 'minimumTransferAmount'.
     * 
     * @return the value of field 'minimumTransferAmount'.
     */
    public double getMinimumTransferAmount()
    {
        return this._minimumTransferAmount;
    } //-- double getMinimumTransferAmount() 

    /**
     * Method getParticipationRateReturns the value of field
     * 'participationRate'.
     * 
     * @return the value of field 'participationRate'.
     */
    public double getParticipationRate()
    {
        return this._participationRate;
    } //-- double getParticipationRate() 

    /**
     * Method getPremiumBonusDurationReturns the value of field
     * 'premiumBonusDuration'.
     * 
     * @return the value of field 'premiumBonusDuration'.
     */
    public int getPremiumBonusDuration()
    {
        return this._premiumBonusDuration;
    } //-- int getPremiumBonusDuration() 

    /**
     * Method getStartingIndexValueReturns the value of field
     * 'startingIndexValue'.
     * 
     * @return the value of field 'startingIndexValue'.
     */
    public java.math.BigDecimal getStartingIndexValue()
    {
        return this._startingIndexValue;
    } //-- java.math.BigDecimal getStartingIndexValue() 

    /**
     * Method getTotalPremPercentAllowedReturns the value of field
     * 'totalPremPercentAllowed'.
     * 
     * @return the value of field 'totalPremPercentAllowed'.
     */
    public double getTotalPremPercentAllowed()
    {
        return this._totalPremPercentAllowed;
    } //-- double getTotalPremPercentAllowed() 

    /**
     * Method hasBonusRate
     */
    public boolean hasBonusRate()
    {
        return this._has_bonusRate;
    } //-- boolean hasBonusRate() 

    /**
     * Method hasCurrentRate
     */
    public boolean hasCurrentRate()
    {
        return this._has_currentRate;
    } //-- boolean hasCurrentRate() 

    /**
     * Method hasGuarIntRate
     */
    public boolean hasGuarIntRate()
    {
        return this._has_guarIntRate;
    } //-- boolean hasGuarIntRate() 

    /**
     * Method hasIndexCapMinimum
     */
    public boolean hasIndexCapMinimum()
    {
        return this._has_indexCapMinimum;
    } //-- boolean hasIndexCapMinimum() 

    /**
     * Method hasIndexCapRateGuarPeriod
     */
    public boolean hasIndexCapRateGuarPeriod()
    {
        return this._has_indexCapRateGuarPeriod;
    } //-- boolean hasIndexCapRateGuarPeriod() 

    /**
     * Method hasIndexMargin
     */
    public boolean hasIndexMargin()
    {
        return this._has_indexMargin;
    } //-- boolean hasIndexMargin() 

    /**
     * Method hasInitialGuaranteedPeriod
     */
    public boolean hasInitialGuaranteedPeriod()
    {
        return this._has_initialGuaranteedPeriod;
    } //-- boolean hasInitialGuaranteedPeriod() 

    /**
     * Method hasInvestmentFK
     */
    public boolean hasInvestmentFK()
    {
        return this._has_investmentFK;
    } //-- boolean hasInvestmentFK() 

    /**
     * Method hasInvestmentRatePK
     */
    public boolean hasInvestmentRatePK()
    {
        return this._has_investmentRatePK;
    } //-- boolean hasInvestmentRatePK() 

    /**
     * Method hasMVAStartingGuarPeriod
     */
    public boolean hasMVAStartingGuarPeriod()
    {
        return this._has_MVAStartingGuarPeriod;
    } //-- boolean hasMVAStartingGuarPeriod() 

    /**
     * Method hasMinimumTransferAmount
     */
    public boolean hasMinimumTransferAmount()
    {
        return this._has_minimumTransferAmount;
    } //-- boolean hasMinimumTransferAmount() 

    /**
     * Method hasParticipationRate
     */
    public boolean hasParticipationRate()
    {
        return this._has_participationRate;
    } //-- boolean hasParticipationRate() 

    /**
     * Method hasPremiumBonusDuration
     */
    public boolean hasPremiumBonusDuration()
    {
        return this._has_premiumBonusDuration;
    } //-- boolean hasPremiumBonusDuration() 

    /**
     * Method hasTotalPremPercentAllowed
     */
    public boolean hasTotalPremPercentAllowed()
    {
        return this._has_totalPremPercentAllowed;
    } //-- boolean hasTotalPremPercentAllowed() 

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
     * Method setBeginningIndexValueSets the value of field
     * 'beginningIndexValue'.
     * 
     * @param beginningIndexValue the value of field
     * 'beginningIndexValue'.
     */
    public void setBeginningIndexValue(java.math.BigDecimal beginningIndexValue)
    {
        this._beginningIndexValue = beginningIndexValue;
        
        super.setVoChanged(true);
    } //-- void setBeginningIndexValue(java.math.BigDecimal) 

    /**
     * Method setBonusRateSets the value of field 'bonusRate'.
     * 
     * @param bonusRate the value of field 'bonusRate'.
     */
    public void setBonusRate(double bonusRate)
    {
        this._bonusRate = bonusRate;
        
        super.setVoChanged(true);
        this._has_bonusRate = true;
    } //-- void setBonusRate(double) 

    /**
     * Method setCurrentRateSets the value of field 'currentRate'.
     * 
     * @param currentRate the value of field 'currentRate'.
     */
    public void setCurrentRate(double currentRate)
    {
        this._currentRate = currentRate;
        
        super.setVoChanged(true);
        this._has_currentRate = true;
    } //-- void setCurrentRate(double) 

    /**
     * Method setCurrentRateDateSets the value of field
     * 'currentRateDate'.
     * 
     * @param currentRateDate the value of field 'currentRateDate'.
     */
    public void setCurrentRateDate(java.lang.String currentRateDate)
    {
        this._currentRateDate = currentRateDate;
        
        super.setVoChanged(true);
    } //-- void setCurrentRateDate(java.lang.String) 

    /**
     * Method setEndingIndexValueSets the value of field
     * 'endingIndexValue'.
     * 
     * @param endingIndexValue the value of field 'endingIndexValue'
     */
    public void setEndingIndexValue(java.math.BigDecimal endingIndexValue)
    {
        this._endingIndexValue = endingIndexValue;
        
        super.setVoChanged(true);
    } //-- void setEndingIndexValue(java.math.BigDecimal) 

    /**
     * Method setFundTypeSets the value of field 'fundType'.
     * 
     * @param fundType the value of field 'fundType'.
     */
    public void setFundType(java.lang.String fundType)
    {
        this._fundType = fundType;
        
        super.setVoChanged(true);
    } //-- void setFundType(java.lang.String) 

    /**
     * Method setGuarIntRateSets the value of field 'guarIntRate'.
     * 
     * @param guarIntRate the value of field 'guarIntRate'.
     */
    public void setGuarIntRate(double guarIntRate)
    {
        this._guarIntRate = guarIntRate;
        
        super.setVoChanged(true);
        this._has_guarIntRate = true;
    } //-- void setGuarIntRate(double) 

    /**
     * Method setIndexCapMinimumSets the value of field
     * 'indexCapMinimum'.
     * 
     * @param indexCapMinimum the value of field 'indexCapMinimum'.
     */
    public void setIndexCapMinimum(double indexCapMinimum)
    {
        this._indexCapMinimum = indexCapMinimum;
        
        super.setVoChanged(true);
        this._has_indexCapMinimum = true;
    } //-- void setIndexCapMinimum(double) 

    /**
     * Method setIndexCapRateGuarPeriodSets the value of field
     * 'indexCapRateGuarPeriod'.
     * 
     * @param indexCapRateGuarPeriod the value of field
     * 'indexCapRateGuarPeriod'.
     */
    public void setIndexCapRateGuarPeriod(int indexCapRateGuarPeriod)
    {
        this._indexCapRateGuarPeriod = indexCapRateGuarPeriod;
        
        super.setVoChanged(true);
        this._has_indexCapRateGuarPeriod = true;
    } //-- void setIndexCapRateGuarPeriod(int) 

    /**
     * Method setIndexMarginSets the value of field 'indexMargin'.
     * 
     * @param indexMargin the value of field 'indexMargin'.
     */
    public void setIndexMargin(double indexMargin)
    {
        this._indexMargin = indexMargin;
        
        super.setVoChanged(true);
        this._has_indexMargin = true;
    } //-- void setIndexMargin(double) 

    /**
     * Method setIndexingMethodSets the value of field
     * 'indexingMethod'.
     * 
     * @param indexingMethod the value of field 'indexingMethod'.
     */
    public void setIndexingMethod(java.lang.String indexingMethod)
    {
        this._indexingMethod = indexingMethod;
        
        super.setVoChanged(true);
    } //-- void setIndexingMethod(java.lang.String) 

    /**
     * Method setInitialGuaranteedPeriodSets the value of field
     * 'initialGuaranteedPeriod'.
     * 
     * @param initialGuaranteedPeriod the value of field
     * 'initialGuaranteedPeriod'.
     */
    public void setInitialGuaranteedPeriod(double initialGuaranteedPeriod)
    {
        this._initialGuaranteedPeriod = initialGuaranteedPeriod;
        
        super.setVoChanged(true);
        this._has_initialGuaranteedPeriod = true;
    } //-- void setInitialGuaranteedPeriod(double) 

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
     * Method setInvestmentRatePKSets the value of field
     * 'investmentRatePK'.
     * 
     * @param investmentRatePK the value of field 'investmentRatePK'
     */
    public void setInvestmentRatePK(long investmentRatePK)
    {
        this._investmentRatePK = investmentRatePK;
        
        super.setVoChanged(true);
        this._has_investmentRatePK = true;
    } //-- void setInvestmentRatePK(long) 

    /**
     * Method setMVAStartingGuarPeriodSets the value of field
     * 'MVAStartingGuarPeriod'.
     * 
     * @param MVAStartingGuarPeriod the value of field
     * 'MVAStartingGuarPeriod'.
     */
    public void setMVAStartingGuarPeriod(int MVAStartingGuarPeriod)
    {
        this._MVAStartingGuarPeriod = MVAStartingGuarPeriod;
        
        super.setVoChanged(true);
        this._has_MVAStartingGuarPeriod = true;
    } //-- void setMVAStartingGuarPeriod(int) 

    /**
     * Method setMinimumTransferAmountSets the value of field
     * 'minimumTransferAmount'.
     * 
     * @param minimumTransferAmount the value of field
     * 'minimumTransferAmount'.
     */
    public void setMinimumTransferAmount(double minimumTransferAmount)
    {
        this._minimumTransferAmount = minimumTransferAmount;
        
        super.setVoChanged(true);
        this._has_minimumTransferAmount = true;
    } //-- void setMinimumTransferAmount(double) 

    /**
     * Method setParticipationRateSets the value of field
     * 'participationRate'.
     * 
     * @param participationRate the value of field
     * 'participationRate'.
     */
    public void setParticipationRate(double participationRate)
    {
        this._participationRate = participationRate;
        
        super.setVoChanged(true);
        this._has_participationRate = true;
    } //-- void setParticipationRate(double) 

    /**
     * Method setPremiumBonusDurationSets the value of field
     * 'premiumBonusDuration'.
     * 
     * @param premiumBonusDuration the value of field
     * 'premiumBonusDuration'.
     */
    public void setPremiumBonusDuration(int premiumBonusDuration)
    {
        this._premiumBonusDuration = premiumBonusDuration;
        
        super.setVoChanged(true);
        this._has_premiumBonusDuration = true;
    } //-- void setPremiumBonusDuration(int) 

    /**
     * Method setStartingIndexValueSets the value of field
     * 'startingIndexValue'.
     * 
     * @param startingIndexValue the value of field
     * 'startingIndexValue'.
     */
    public void setStartingIndexValue(java.math.BigDecimal startingIndexValue)
    {
        this._startingIndexValue = startingIndexValue;
        
        super.setVoChanged(true);
    } //-- void setStartingIndexValue(java.math.BigDecimal) 

    /**
     * Method setTotalPremPercentAllowedSets the value of field
     * 'totalPremPercentAllowed'.
     * 
     * @param totalPremPercentAllowed the value of field
     * 'totalPremPercentAllowed'.
     */
    public void setTotalPremPercentAllowed(double totalPremPercentAllowed)
    {
        this._totalPremPercentAllowed = totalPremPercentAllowed;
        
        super.setVoChanged(true);
        this._has_totalPremPercentAllowed = true;
    } //-- void setTotalPremPercentAllowed(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentRateVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentRateVO) Unmarshaller.unmarshal(edit.common.vo.InvestmentRateVO.class, reader);
    } //-- edit.common.vo.InvestmentRateVO unmarshal(java.io.Reader) 

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
