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
 * Class RMDVO.
 * 
 * @version $Revision$ $Date$
 */
public class RMDVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _RMDPK
     */
    private long _RMDPK;

    /**
     * keeps track of state for field: _RMDPK
     */
    private boolean _has_RMDPK;

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
     * Field _RMDElectionCT
     */
    private java.lang.String _RMDElectionCT;

    /**
     * Field _initialRMDAmount
     */
    private java.math.BigDecimal _initialRMDAmount;

    /**
     * Field _RMDAmount
     */
    private java.math.BigDecimal _RMDAmount;

    /**
     * Field _RMDAnnualDate
     */
    private java.lang.String _RMDAnnualDate;

    /**
     * Field _RMDFirstPayDate
     */
    private java.lang.String _RMDFirstPayDate;

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
     * Field _RMDFrequencyCT
     */
    private java.lang.String _RMDFrequencyCT;

    /**
     * Field _RMDModalOverrideAmount
     */
    private java.math.BigDecimal _RMDModalOverrideAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public RMDVO() {
        super();
    } //-- edit.common.vo.RMDVO()


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
        
        if (obj instanceof RMDVO) {
        
            RMDVO temp = (RMDVO)obj;
            if (this._RMDPK != temp._RMDPK)
                return false;
            if (this._has_RMDPK != temp._has_RMDPK)
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
            if (this._RMDElectionCT != null) {
                if (temp._RMDElectionCT == null) return false;
                else if (!(this._RMDElectionCT.equals(temp._RMDElectionCT))) 
                    return false;
            }
            else if (temp._RMDElectionCT != null)
                return false;
            if (this._initialRMDAmount != null) {
                if (temp._initialRMDAmount == null) return false;
                else if (!(this._initialRMDAmount.equals(temp._initialRMDAmount))) 
                    return false;
            }
            else if (temp._initialRMDAmount != null)
                return false;
            if (this._RMDAmount != null) {
                if (temp._RMDAmount == null) return false;
                else if (!(this._RMDAmount.equals(temp._RMDAmount))) 
                    return false;
            }
            else if (temp._RMDAmount != null)
                return false;
            if (this._RMDAnnualDate != null) {
                if (temp._RMDAnnualDate == null) return false;
                else if (!(this._RMDAnnualDate.equals(temp._RMDAnnualDate))) 
                    return false;
            }
            else if (temp._RMDAnnualDate != null)
                return false;
            if (this._RMDFirstPayDate != null) {
                if (temp._RMDFirstPayDate == null) return false;
                else if (!(this._RMDFirstPayDate.equals(temp._RMDFirstPayDate))) 
                    return false;
            }
            else if (temp._RMDFirstPayDate != null)
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
            if (this._RMDFrequencyCT != null) {
                if (temp._RMDFrequencyCT == null) return false;
                else if (!(this._RMDFrequencyCT.equals(temp._RMDFrequencyCT))) 
                    return false;
            }
            else if (temp._RMDFrequencyCT != null)
                return false;
            if (this._RMDModalOverrideAmount != null) {
                if (temp._RMDModalOverrideAmount == null) return false;
                else if (!(this._RMDModalOverrideAmount.equals(temp._RMDModalOverrideAmount))) 
                    return false;
            }
            else if (temp._RMDModalOverrideAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getRMDAmountReturns the value of field 'RMDAmount'.
     * 
     * @return the value of field 'RMDAmount'.
     */
    public java.math.BigDecimal getRMDAmount()
    {
        return this._RMDAmount;
    } //-- java.math.BigDecimal getRMDAmount() 

    /**
     * Method getRMDAnnualDateReturns the value of field
     * 'RMDAnnualDate'.
     * 
     * @return the value of field 'RMDAnnualDate'.
     */
    public java.lang.String getRMDAnnualDate()
    {
        return this._RMDAnnualDate;
    } //-- java.lang.String getRMDAnnualDate() 

    /**
     * Method getRMDElectionCTReturns the value of field
     * 'RMDElectionCT'.
     * 
     * @return the value of field 'RMDElectionCT'.
     */
    public java.lang.String getRMDElectionCT()
    {
        return this._RMDElectionCT;
    } //-- java.lang.String getRMDElectionCT() 

    /**
     * Method getRMDFirstPayDateReturns the value of field
     * 'RMDFirstPayDate'.
     * 
     * @return the value of field 'RMDFirstPayDate'.
     */
    public java.lang.String getRMDFirstPayDate()
    {
        return this._RMDFirstPayDate;
    } //-- java.lang.String getRMDFirstPayDate() 

    /**
     * Method getRMDFrequencyCTReturns the value of field
     * 'RMDFrequencyCT'.
     * 
     * @return the value of field 'RMDFrequencyCT'.
     */
    public java.lang.String getRMDFrequencyCT()
    {
        return this._RMDFrequencyCT;
    } //-- java.lang.String getRMDFrequencyCT() 

    /**
     * Method getRMDModalOverrideAmountReturns the value of field
     * 'RMDModalOverrideAmount'.
     * 
     * @return the value of field 'RMDModalOverrideAmount'.
     */
    public java.math.BigDecimal getRMDModalOverrideAmount()
    {
        return this._RMDModalOverrideAmount;
    } //-- java.math.BigDecimal getRMDModalOverrideAmount() 

    /**
     * Method getRMDPKReturns the value of field 'RMDPK'.
     * 
     * @return the value of field 'RMDPK'.
     */
    public long getRMDPK()
    {
        return this._RMDPK;
    } //-- long getRMDPK() 

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
     * Method hasRMDPK
     */
    public boolean hasRMDPK()
    {
        return this._has_RMDPK;
    } //-- boolean hasRMDPK() 

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
     * Method setRMDAmountSets the value of field 'RMDAmount'.
     * 
     * @param RMDAmount the value of field 'RMDAmount'.
     */
    public void setRMDAmount(java.math.BigDecimal RMDAmount)
    {
        this._RMDAmount = RMDAmount;
        
        super.setVoChanged(true);
    } //-- void setRMDAmount(java.math.BigDecimal) 

    /**
     * Method setRMDAnnualDateSets the value of field
     * 'RMDAnnualDate'.
     * 
     * @param RMDAnnualDate the value of field 'RMDAnnualDate'.
     */
    public void setRMDAnnualDate(java.lang.String RMDAnnualDate)
    {
        this._RMDAnnualDate = RMDAnnualDate;
        
        super.setVoChanged(true);
    } //-- void setRMDAnnualDate(java.lang.String) 

    /**
     * Method setRMDElectionCTSets the value of field
     * 'RMDElectionCT'.
     * 
     * @param RMDElectionCT the value of field 'RMDElectionCT'.
     */
    public void setRMDElectionCT(java.lang.String RMDElectionCT)
    {
        this._RMDElectionCT = RMDElectionCT;
        
        super.setVoChanged(true);
    } //-- void setRMDElectionCT(java.lang.String) 

    /**
     * Method setRMDFirstPayDateSets the value of field
     * 'RMDFirstPayDate'.
     * 
     * @param RMDFirstPayDate the value of field 'RMDFirstPayDate'.
     */
    public void setRMDFirstPayDate(java.lang.String RMDFirstPayDate)
    {
        this._RMDFirstPayDate = RMDFirstPayDate;
        
        super.setVoChanged(true);
    } //-- void setRMDFirstPayDate(java.lang.String) 

    /**
     * Method setRMDFrequencyCTSets the value of field
     * 'RMDFrequencyCT'.
     * 
     * @param RMDFrequencyCT the value of field 'RMDFrequencyCT'.
     */
    public void setRMDFrequencyCT(java.lang.String RMDFrequencyCT)
    {
        this._RMDFrequencyCT = RMDFrequencyCT;
        
        super.setVoChanged(true);
    } //-- void setRMDFrequencyCT(java.lang.String) 

    /**
     * Method setRMDModalOverrideAmountSets the value of field
     * 'RMDModalOverrideAmount'.
     * 
     * @param RMDModalOverrideAmount the value of field
     * 'RMDModalOverrideAmount'.
     */
    public void setRMDModalOverrideAmount(java.math.BigDecimal RMDModalOverrideAmount)
    {
        this._RMDModalOverrideAmount = RMDModalOverrideAmount;
        
        super.setVoChanged(true);
    } //-- void setRMDModalOverrideAmount(java.math.BigDecimal) 

    /**
     * Method setRMDPKSets the value of field 'RMDPK'.
     * 
     * @param RMDPK the value of field 'RMDPK'.
     */
    public void setRMDPK(long RMDPK)
    {
        this._RMDPK = RMDPK;
        
        super.setVoChanged(true);
        this._has_RMDPK = true;
    } //-- void setRMDPK(long) 

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
    public static edit.common.vo.RMDVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RMDVO) Unmarshaller.unmarshal(edit.common.vo.RMDVO.class, reader);
    } //-- edit.common.vo.RMDVO unmarshal(java.io.Reader) 

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
