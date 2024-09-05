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
 * Class RequiredMinDistributionVO.
 * 
 * @version $Revision$ $Date$
 */
public class RequiredMinDistributionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _requiredMinDistributionPK
     */
    private long _requiredMinDistributionPK;

    /**
     * keeps track of state for field: _requiredMinDistributionPK
     */
    private boolean _has_requiredMinDistributionPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _seventyAndHalfDate
     */
    private java.lang.String _seventyAndHalfDate;

    /**
     * Field _electionCT
     */
    private java.lang.String _electionCT;

    /**
     * Field _initialRMDAmount
     */
    private java.math.BigDecimal _initialRMDAmount;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;

    /**
     * Field _annualDate
     */
    private java.lang.String _annualDate;

    /**
     * Field _firstPayDate
     */
    private java.lang.String _firstPayDate;

    /**
     * Field _lastNotificationDate
     */
    private java.lang.String _lastNotificationDate;

    /**
     * Field _lastPaymentDate
     */
    private java.lang.String _lastPaymentDate;

    /**
     * Field _lastPaymentAmount
     */
    private java.math.BigDecimal _lastPaymentAmount;

    /**
     * Field _nextPaymentDate
     */
    private java.lang.String _nextPaymentDate;

    /**
     * Field _lifeExpectancyMultipleCT
     */
    private java.lang.String _lifeExpectancyMultipleCT;

    /**
     * Field _frequencyCT
     */
    private java.lang.String _frequencyCT;

    /**
     * Field _modalOverrideAmount
     */
    private java.math.BigDecimal _modalOverrideAmount;

    /**
     * Field _calculatedRMDAmount
     */
    private java.math.BigDecimal _calculatedRMDAmount;

    /**
     * Field _initialCYAccumValue
     */
    private java.math.BigDecimal _initialCYAccumValue;

    /**
     * Field _RMDStartDate
     */
    private java.lang.String _RMDStartDate;

    /**
     * Field _deceasedSeventyAndHalfDate
     */
    private java.lang.String _deceasedSeventyAndHalfDate;

    /**
     * Field _beneficiaryStatusCT
     */
    private java.lang.String _beneficiaryStatusCT;


      //----------------/
     //- Constructors -/
    //----------------/

    public RequiredMinDistributionVO() {
        super();
    } //-- edit.common.vo.RequiredMinDistributionVO()


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
        
        if (obj instanceof RequiredMinDistributionVO) {
        
            RequiredMinDistributionVO temp = (RequiredMinDistributionVO)obj;
            if (this._requiredMinDistributionPK != temp._requiredMinDistributionPK)
                return false;
            if (this._has_requiredMinDistributionPK != temp._has_requiredMinDistributionPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._seventyAndHalfDate != null) {
                if (temp._seventyAndHalfDate == null) return false;
                else if (!(this._seventyAndHalfDate.equals(temp._seventyAndHalfDate))) 
                    return false;
            }
            else if (temp._seventyAndHalfDate != null)
                return false;
            if (this._electionCT != null) {
                if (temp._electionCT == null) return false;
                else if (!(this._electionCT.equals(temp._electionCT))) 
                    return false;
            }
            else if (temp._electionCT != null)
                return false;
            if (this._initialRMDAmount != null) {
                if (temp._initialRMDAmount == null) return false;
                else if (!(this._initialRMDAmount.equals(temp._initialRMDAmount))) 
                    return false;
            }
            else if (temp._initialRMDAmount != null)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            if (this._annualDate != null) {
                if (temp._annualDate == null) return false;
                else if (!(this._annualDate.equals(temp._annualDate))) 
                    return false;
            }
            else if (temp._annualDate != null)
                return false;
            if (this._firstPayDate != null) {
                if (temp._firstPayDate == null) return false;
                else if (!(this._firstPayDate.equals(temp._firstPayDate))) 
                    return false;
            }
            else if (temp._firstPayDate != null)
                return false;
            if (this._lastNotificationDate != null) {
                if (temp._lastNotificationDate == null) return false;
                else if (!(this._lastNotificationDate.equals(temp._lastNotificationDate))) 
                    return false;
            }
            else if (temp._lastNotificationDate != null)
                return false;
            if (this._lastPaymentDate != null) {
                if (temp._lastPaymentDate == null) return false;
                else if (!(this._lastPaymentDate.equals(temp._lastPaymentDate))) 
                    return false;
            }
            else if (temp._lastPaymentDate != null)
                return false;
            if (this._lastPaymentAmount != null) {
                if (temp._lastPaymentAmount == null) return false;
                else if (!(this._lastPaymentAmount.equals(temp._lastPaymentAmount))) 
                    return false;
            }
            else if (temp._lastPaymentAmount != null)
                return false;
            if (this._nextPaymentDate != null) {
                if (temp._nextPaymentDate == null) return false;
                else if (!(this._nextPaymentDate.equals(temp._nextPaymentDate))) 
                    return false;
            }
            else if (temp._nextPaymentDate != null)
                return false;
            if (this._lifeExpectancyMultipleCT != null) {
                if (temp._lifeExpectancyMultipleCT == null) return false;
                else if (!(this._lifeExpectancyMultipleCT.equals(temp._lifeExpectancyMultipleCT))) 
                    return false;
            }
            else if (temp._lifeExpectancyMultipleCT != null)
                return false;
            if (this._frequencyCT != null) {
                if (temp._frequencyCT == null) return false;
                else if (!(this._frequencyCT.equals(temp._frequencyCT))) 
                    return false;
            }
            else if (temp._frequencyCT != null)
                return false;
            if (this._modalOverrideAmount != null) {
                if (temp._modalOverrideAmount == null) return false;
                else if (!(this._modalOverrideAmount.equals(temp._modalOverrideAmount))) 
                    return false;
            }
            else if (temp._modalOverrideAmount != null)
                return false;
            if (this._calculatedRMDAmount != null) {
                if (temp._calculatedRMDAmount == null) return false;
                else if (!(this._calculatedRMDAmount.equals(temp._calculatedRMDAmount))) 
                    return false;
            }
            else if (temp._calculatedRMDAmount != null)
                return false;
            if (this._initialCYAccumValue != null) {
                if (temp._initialCYAccumValue == null) return false;
                else if (!(this._initialCYAccumValue.equals(temp._initialCYAccumValue))) 
                    return false;
            }
            else if (temp._initialCYAccumValue != null)
                return false;
            if (this._RMDStartDate != null) {
                if (temp._RMDStartDate == null) return false;
                else if (!(this._RMDStartDate.equals(temp._RMDStartDate))) 
                    return false;
            }
            else if (temp._RMDStartDate != null)
                return false;
            if (this._deceasedSeventyAndHalfDate != null) {
                if (temp._deceasedSeventyAndHalfDate == null) return false;
                else if (!(this._deceasedSeventyAndHalfDate.equals(temp._deceasedSeventyAndHalfDate))) 
                    return false;
            }
            else if (temp._deceasedSeventyAndHalfDate != null)
                return false;
            if (this._beneficiaryStatusCT != null) {
                if (temp._beneficiaryStatusCT == null) return false;
                else if (!(this._beneficiaryStatusCT.equals(temp._beneficiaryStatusCT))) 
                    return false;
            }
            else if (temp._beneficiaryStatusCT != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getAnnualDateReturns the value of field 'annualDate'.
     * 
     * @return the value of field 'annualDate'.
     */
    public java.lang.String getAnnualDate()
    {
        return this._annualDate;
    } //-- java.lang.String getAnnualDate() 

    /**
     * Method getBeneficiaryStatusCTReturns the value of field
     * 'beneficiaryStatusCT'.
     * 
     * @return the value of field 'beneficiaryStatusCT'.
     */
    public java.lang.String getBeneficiaryStatusCT()
    {
        return this._beneficiaryStatusCT;
    } //-- java.lang.String getBeneficiaryStatusCT() 

    /**
     * Method getCalculatedRMDAmountReturns the value of field
     * 'calculatedRMDAmount'.
     * 
     * @return the value of field 'calculatedRMDAmount'.
     */
    public java.math.BigDecimal getCalculatedRMDAmount()
    {
        return this._calculatedRMDAmount;
    } //-- java.math.BigDecimal getCalculatedRMDAmount() 

    /**
     * Method getDeceasedSeventyAndHalfDateReturns the value of
     * field 'deceasedSeventyAndHalfDate'.
     * 
     * @return the value of field 'deceasedSeventyAndHalfDate'.
     */
    public java.lang.String getDeceasedSeventyAndHalfDate()
    {
        return this._deceasedSeventyAndHalfDate;
    } //-- java.lang.String getDeceasedSeventyAndHalfDate() 

    /**
     * Method getElectionCTReturns the value of field 'electionCT'.
     * 
     * @return the value of field 'electionCT'.
     */
    public java.lang.String getElectionCT()
    {
        return this._electionCT;
    } //-- java.lang.String getElectionCT() 

    /**
     * Method getFirstPayDateReturns the value of field
     * 'firstPayDate'.
     * 
     * @return the value of field 'firstPayDate'.
     */
    public java.lang.String getFirstPayDate()
    {
        return this._firstPayDate;
    } //-- java.lang.String getFirstPayDate() 

    /**
     * Method getFrequencyCTReturns the value of field
     * 'frequencyCT'.
     * 
     * @return the value of field 'frequencyCT'.
     */
    public java.lang.String getFrequencyCT()
    {
        return this._frequencyCT;
    } //-- java.lang.String getFrequencyCT() 

    /**
     * Method getInitialCYAccumValueReturns the value of field
     * 'initialCYAccumValue'.
     * 
     * @return the value of field 'initialCYAccumValue'.
     */
    public java.math.BigDecimal getInitialCYAccumValue()
    {
        return this._initialCYAccumValue;
    } //-- java.math.BigDecimal getInitialCYAccumValue() 

    /**
     * Method getInitialRMDAmountReturns the value of field
     * 'initialRMDAmount'.
     * 
     * @return the value of field 'initialRMDAmount'.
     */
    public java.math.BigDecimal getInitialRMDAmount()
    {
        return this._initialRMDAmount;
    } //-- java.math.BigDecimal getInitialRMDAmount() 

    /**
     * Method getLastNotificationDateReturns the value of field
     * 'lastNotificationDate'.
     * 
     * @return the value of field 'lastNotificationDate'.
     */
    public java.lang.String getLastNotificationDate()
    {
        return this._lastNotificationDate;
    } //-- java.lang.String getLastNotificationDate() 

    /**
     * Method getLastPaymentAmountReturns the value of field
     * 'lastPaymentAmount'.
     * 
     * @return the value of field 'lastPaymentAmount'.
     */
    public java.math.BigDecimal getLastPaymentAmount()
    {
        return this._lastPaymentAmount;
    } //-- java.math.BigDecimal getLastPaymentAmount() 

    /**
     * Method getLastPaymentDateReturns the value of field
     * 'lastPaymentDate'.
     * 
     * @return the value of field 'lastPaymentDate'.
     */
    public java.lang.String getLastPaymentDate()
    {
        return this._lastPaymentDate;
    } //-- java.lang.String getLastPaymentDate() 

    /**
     * Method getLifeExpectancyMultipleCTReturns the value of field
     * 'lifeExpectancyMultipleCT'.
     * 
     * @return the value of field 'lifeExpectancyMultipleCT'.
     */
    public java.lang.String getLifeExpectancyMultipleCT()
    {
        return this._lifeExpectancyMultipleCT;
    } //-- java.lang.String getLifeExpectancyMultipleCT() 

    /**
     * Method getModalOverrideAmountReturns the value of field
     * 'modalOverrideAmount'.
     * 
     * @return the value of field 'modalOverrideAmount'.
     */
    public java.math.BigDecimal getModalOverrideAmount()
    {
        return this._modalOverrideAmount;
    } //-- java.math.BigDecimal getModalOverrideAmount() 

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
     * Method getRMDStartDateReturns the value of field
     * 'RMDStartDate'.
     * 
     * @return the value of field 'RMDStartDate'.
     */
    public java.lang.String getRMDStartDate()
    {
        return this._RMDStartDate;
    } //-- java.lang.String getRMDStartDate() 

    /**
     * Method getRequiredMinDistributionPKReturns the value of
     * field 'requiredMinDistributionPK'.
     * 
     * @return the value of field 'requiredMinDistributionPK'.
     */
    public long getRequiredMinDistributionPK()
    {
        return this._requiredMinDistributionPK;
    } //-- long getRequiredMinDistributionPK() 

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
     * Method getSeventyAndHalfDateReturns the value of field
     * 'seventyAndHalfDate'.
     * 
     * @return the value of field 'seventyAndHalfDate'.
     */
    public java.lang.String getSeventyAndHalfDate()
    {
        return this._seventyAndHalfDate;
    } //-- java.lang.String getSeventyAndHalfDate() 

    /**
     * Method hasRequiredMinDistributionPK
     */
    public boolean hasRequiredMinDistributionPK()
    {
        return this._has_requiredMinDistributionPK;
    } //-- boolean hasRequiredMinDistributionPK() 

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
     * Method setAnnualDateSets the value of field 'annualDate'.
     * 
     * @param annualDate the value of field 'annualDate'.
     */
    public void setAnnualDate(java.lang.String annualDate)
    {
        this._annualDate = annualDate;
        
        super.setVoChanged(true);
    } //-- void setAnnualDate(java.lang.String) 

    /**
     * Method setBeneficiaryStatusCTSets the value of field
     * 'beneficiaryStatusCT'.
     * 
     * @param beneficiaryStatusCT the value of field
     * 'beneficiaryStatusCT'.
     */
    public void setBeneficiaryStatusCT(java.lang.String beneficiaryStatusCT)
    {
        this._beneficiaryStatusCT = beneficiaryStatusCT;
        
        super.setVoChanged(true);
    } //-- void setBeneficiaryStatusCT(java.lang.String) 

    /**
     * Method setCalculatedRMDAmountSets the value of field
     * 'calculatedRMDAmount'.
     * 
     * @param calculatedRMDAmount the value of field
     * 'calculatedRMDAmount'.
     */
    public void setCalculatedRMDAmount(java.math.BigDecimal calculatedRMDAmount)
    {
        this._calculatedRMDAmount = calculatedRMDAmount;
        
        super.setVoChanged(true);
    } //-- void setCalculatedRMDAmount(java.math.BigDecimal) 

    /**
     * Method setDeceasedSeventyAndHalfDateSets the value of field
     * 'deceasedSeventyAndHalfDate'.
     * 
     * @param deceasedSeventyAndHalfDate the value of field
     * 'deceasedSeventyAndHalfDate'.
     */
    public void setDeceasedSeventyAndHalfDate(java.lang.String deceasedSeventyAndHalfDate)
    {
        this._deceasedSeventyAndHalfDate = deceasedSeventyAndHalfDate;
        
        super.setVoChanged(true);
    } //-- void setDeceasedSeventyAndHalfDate(java.lang.String) 

    /**
     * Method setElectionCTSets the value of field 'electionCT'.
     * 
     * @param electionCT the value of field 'electionCT'.
     */
    public void setElectionCT(java.lang.String electionCT)
    {
        this._electionCT = electionCT;
        
        super.setVoChanged(true);
    } //-- void setElectionCT(java.lang.String) 

    /**
     * Method setFirstPayDateSets the value of field
     * 'firstPayDate'.
     * 
     * @param firstPayDate the value of field 'firstPayDate'.
     */
    public void setFirstPayDate(java.lang.String firstPayDate)
    {
        this._firstPayDate = firstPayDate;
        
        super.setVoChanged(true);
    } //-- void setFirstPayDate(java.lang.String) 

    /**
     * Method setFrequencyCTSets the value of field 'frequencyCT'.
     * 
     * @param frequencyCT the value of field 'frequencyCT'.
     */
    public void setFrequencyCT(java.lang.String frequencyCT)
    {
        this._frequencyCT = frequencyCT;
        
        super.setVoChanged(true);
    } //-- void setFrequencyCT(java.lang.String) 

    /**
     * Method setInitialCYAccumValueSets the value of field
     * 'initialCYAccumValue'.
     * 
     * @param initialCYAccumValue the value of field
     * 'initialCYAccumValue'.
     */
    public void setInitialCYAccumValue(java.math.BigDecimal initialCYAccumValue)
    {
        this._initialCYAccumValue = initialCYAccumValue;
        
        super.setVoChanged(true);
    } //-- void setInitialCYAccumValue(java.math.BigDecimal) 

    /**
     * Method setInitialRMDAmountSets the value of field
     * 'initialRMDAmount'.
     * 
     * @param initialRMDAmount the value of field 'initialRMDAmount'
     */
    public void setInitialRMDAmount(java.math.BigDecimal initialRMDAmount)
    {
        this._initialRMDAmount = initialRMDAmount;
        
        super.setVoChanged(true);
    } //-- void setInitialRMDAmount(java.math.BigDecimal) 

    /**
     * Method setLastNotificationDateSets the value of field
     * 'lastNotificationDate'.
     * 
     * @param lastNotificationDate the value of field
     * 'lastNotificationDate'.
     */
    public void setLastNotificationDate(java.lang.String lastNotificationDate)
    {
        this._lastNotificationDate = lastNotificationDate;
        
        super.setVoChanged(true);
    } //-- void setLastNotificationDate(java.lang.String) 

    /**
     * Method setLastPaymentAmountSets the value of field
     * 'lastPaymentAmount'.
     * 
     * @param lastPaymentAmount the value of field
     * 'lastPaymentAmount'.
     */
    public void setLastPaymentAmount(java.math.BigDecimal lastPaymentAmount)
    {
        this._lastPaymentAmount = lastPaymentAmount;
        
        super.setVoChanged(true);
    } //-- void setLastPaymentAmount(java.math.BigDecimal) 

    /**
     * Method setLastPaymentDateSets the value of field
     * 'lastPaymentDate'.
     * 
     * @param lastPaymentDate the value of field 'lastPaymentDate'.
     */
    public void setLastPaymentDate(java.lang.String lastPaymentDate)
    {
        this._lastPaymentDate = lastPaymentDate;
        
        super.setVoChanged(true);
    } //-- void setLastPaymentDate(java.lang.String) 

    /**
     * Method setLifeExpectancyMultipleCTSets the value of field
     * 'lifeExpectancyMultipleCT'.
     * 
     * @param lifeExpectancyMultipleCT the value of field
     * 'lifeExpectancyMultipleCT'.
     */
    public void setLifeExpectancyMultipleCT(java.lang.String lifeExpectancyMultipleCT)
    {
        this._lifeExpectancyMultipleCT = lifeExpectancyMultipleCT;
        
        super.setVoChanged(true);
    } //-- void setLifeExpectancyMultipleCT(java.lang.String) 

    /**
     * Method setModalOverrideAmountSets the value of field
     * 'modalOverrideAmount'.
     * 
     * @param modalOverrideAmount the value of field
     * 'modalOverrideAmount'.
     */
    public void setModalOverrideAmount(java.math.BigDecimal modalOverrideAmount)
    {
        this._modalOverrideAmount = modalOverrideAmount;
        
        super.setVoChanged(true);
    } //-- void setModalOverrideAmount(java.math.BigDecimal) 

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
     * Method setRMDStartDateSets the value of field
     * 'RMDStartDate'.
     * 
     * @param RMDStartDate the value of field 'RMDStartDate'.
     */
    public void setRMDStartDate(java.lang.String RMDStartDate)
    {
        this._RMDStartDate = RMDStartDate;
        
        super.setVoChanged(true);
    } //-- void setRMDStartDate(java.lang.String) 

    /**
     * Method setRequiredMinDistributionPKSets the value of field
     * 'requiredMinDistributionPK'.
     * 
     * @param requiredMinDistributionPK the value of field
     * 'requiredMinDistributionPK'.
     */
    public void setRequiredMinDistributionPK(long requiredMinDistributionPK)
    {
        this._requiredMinDistributionPK = requiredMinDistributionPK;
        
        super.setVoChanged(true);
        this._has_requiredMinDistributionPK = true;
    } //-- void setRequiredMinDistributionPK(long) 

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
     * Method setSeventyAndHalfDateSets the value of field
     * 'seventyAndHalfDate'.
     * 
     * @param seventyAndHalfDate the value of field
     * 'seventyAndHalfDate'.
     */
    public void setSeventyAndHalfDate(java.lang.String seventyAndHalfDate)
    {
        this._seventyAndHalfDate = seventyAndHalfDate;
        
        super.setVoChanged(true);
    } //-- void setSeventyAndHalfDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RequiredMinDistributionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RequiredMinDistributionVO) Unmarshaller.unmarshal(edit.common.vo.RequiredMinDistributionVO.class, reader);
    } //-- edit.common.vo.RequiredMinDistributionVO unmarshal(java.io.Reader) 

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
