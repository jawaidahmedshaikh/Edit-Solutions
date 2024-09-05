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
 * Class CheckAdjustmentVO.
 * 
 * @version $Revision$ $Date$
 */
public class CheckAdjustmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _checkAdjustmentPK
     */
    private long _checkAdjustmentPK;

    /**
     * keeps track of state for field: _checkAdjustmentPK
     */
    private boolean _has_checkAdjustmentPK;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

    /**
     * Field _adjustmentTypeCT
     */
    private java.lang.String _adjustmentTypeCT;

    /**
     * Field _adjustmentPercent
     */
    private java.math.BigDecimal _adjustmentPercent;

    /**
     * Field _adjustmentDollar
     */
    private java.math.BigDecimal _adjustmentDollar;

    /**
     * Field _taxableStatusCT
     */
    private java.lang.String _taxableStatusCT;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _adjustmentStatusCT
     */
    private java.lang.String _adjustmentStatusCT;

    /**
     * Field _modeCT
     */
    private java.lang.String _modeCT;

    /**
     * Field _nextDueDate
     */
    private java.lang.String _nextDueDate;

    /**
     * Field _description
     */
    private java.lang.String _description;

    /**
     * Field _adjustmentCompleteInd
     */
    private java.lang.String _adjustmentCompleteInd;

    /**
     * Field _accumulatedDebitBalance
     */
    private java.math.BigDecimal _accumulatedDebitBalance;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public CheckAdjustmentVO() {
        super();
    } //-- edit.common.vo.CheckAdjustmentVO()


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
        
        if (obj instanceof CheckAdjustmentVO) {
        
            CheckAdjustmentVO temp = (CheckAdjustmentVO)obj;
            if (this._checkAdjustmentPK != temp._checkAdjustmentPK)
                return false;
            if (this._has_checkAdjustmentPK != temp._has_checkAdjustmentPK)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
                return false;
            if (this._adjustmentTypeCT != null) {
                if (temp._adjustmentTypeCT == null) return false;
                else if (!(this._adjustmentTypeCT.equals(temp._adjustmentTypeCT))) 
                    return false;
            }
            else if (temp._adjustmentTypeCT != null)
                return false;
            if (this._adjustmentPercent != null) {
                if (temp._adjustmentPercent == null) return false;
                else if (!(this._adjustmentPercent.equals(temp._adjustmentPercent))) 
                    return false;
            }
            else if (temp._adjustmentPercent != null)
                return false;
            if (this._adjustmentDollar != null) {
                if (temp._adjustmentDollar == null) return false;
                else if (!(this._adjustmentDollar.equals(temp._adjustmentDollar))) 
                    return false;
            }
            else if (temp._adjustmentDollar != null)
                return false;
            if (this._taxableStatusCT != null) {
                if (temp._taxableStatusCT == null) return false;
                else if (!(this._taxableStatusCT.equals(temp._taxableStatusCT))) 
                    return false;
            }
            else if (temp._taxableStatusCT != null)
                return false;
            if (this._startDate != null) {
                if (temp._startDate == null) return false;
                else if (!(this._startDate.equals(temp._startDate))) 
                    return false;
            }
            else if (temp._startDate != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            if (this._adjustmentStatusCT != null) {
                if (temp._adjustmentStatusCT == null) return false;
                else if (!(this._adjustmentStatusCT.equals(temp._adjustmentStatusCT))) 
                    return false;
            }
            else if (temp._adjustmentStatusCT != null)
                return false;
            if (this._modeCT != null) {
                if (temp._modeCT == null) return false;
                else if (!(this._modeCT.equals(temp._modeCT))) 
                    return false;
            }
            else if (temp._modeCT != null)
                return false;
            if (this._nextDueDate != null) {
                if (temp._nextDueDate == null) return false;
                else if (!(this._nextDueDate.equals(temp._nextDueDate))) 
                    return false;
            }
            else if (temp._nextDueDate != null)
                return false;
            if (this._description != null) {
                if (temp._description == null) return false;
                else if (!(this._description.equals(temp._description))) 
                    return false;
            }
            else if (temp._description != null)
                return false;
            if (this._adjustmentCompleteInd != null) {
                if (temp._adjustmentCompleteInd == null) return false;
                else if (!(this._adjustmentCompleteInd.equals(temp._adjustmentCompleteInd))) 
                    return false;
            }
            else if (temp._adjustmentCompleteInd != null)
                return false;
            if (this._accumulatedDebitBalance != null) {
                if (temp._accumulatedDebitBalance == null) return false;
                else if (!(this._accumulatedDebitBalance.equals(temp._accumulatedDebitBalance))) 
                    return false;
            }
            else if (temp._accumulatedDebitBalance != null)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumulatedDebitBalanceReturns the value of field
     * 'accumulatedDebitBalance'.
     * 
     * @return the value of field 'accumulatedDebitBalance'.
     */
    public java.math.BigDecimal getAccumulatedDebitBalance()
    {
        return this._accumulatedDebitBalance;
    } //-- java.math.BigDecimal getAccumulatedDebitBalance() 

    /**
     * Method getAdjustmentCompleteIndReturns the value of field
     * 'adjustmentCompleteInd'.
     * 
     * @return the value of field 'adjustmentCompleteInd'.
     */
    public java.lang.String getAdjustmentCompleteInd()
    {
        return this._adjustmentCompleteInd;
    } //-- java.lang.String getAdjustmentCompleteInd() 

    /**
     * Method getAdjustmentDollarReturns the value of field
     * 'adjustmentDollar'.
     * 
     * @return the value of field 'adjustmentDollar'.
     */
    public java.math.BigDecimal getAdjustmentDollar()
    {
        return this._adjustmentDollar;
    } //-- java.math.BigDecimal getAdjustmentDollar() 

    /**
     * Method getAdjustmentPercentReturns the value of field
     * 'adjustmentPercent'.
     * 
     * @return the value of field 'adjustmentPercent'.
     */
    public java.math.BigDecimal getAdjustmentPercent()
    {
        return this._adjustmentPercent;
    } //-- java.math.BigDecimal getAdjustmentPercent() 

    /**
     * Method getAdjustmentStatusCTReturns the value of field
     * 'adjustmentStatusCT'.
     * 
     * @return the value of field 'adjustmentStatusCT'.
     */
    public java.lang.String getAdjustmentStatusCT()
    {
        return this._adjustmentStatusCT;
    } //-- java.lang.String getAdjustmentStatusCT() 

    /**
     * Method getAdjustmentTypeCTReturns the value of field
     * 'adjustmentTypeCT'.
     * 
     * @return the value of field 'adjustmentTypeCT'.
     */
    public java.lang.String getAdjustmentTypeCT()
    {
        return this._adjustmentTypeCT;
    } //-- java.lang.String getAdjustmentTypeCT() 

    /**
     * Method getAgentFKReturns the value of field 'agentFK'.
     * 
     * @return the value of field 'agentFK'.
     */
    public long getAgentFK()
    {
        return this._agentFK;
    } //-- long getAgentFK() 

    /**
     * Method getCheckAdjustmentPKReturns the value of field
     * 'checkAdjustmentPK'.
     * 
     * @return the value of field 'checkAdjustmentPK'.
     */
    public long getCheckAdjustmentPK()
    {
        return this._checkAdjustmentPK;
    } //-- long getCheckAdjustmentPK() 

    /**
     * Method getDescriptionReturns the value of field
     * 'description'.
     * 
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Method getModeCTReturns the value of field 'modeCT'.
     * 
     * @return the value of field 'modeCT'.
     */
    public java.lang.String getModeCT()
    {
        return this._modeCT;
    } //-- java.lang.String getModeCT() 

    /**
     * Method getNextDueDateReturns the value of field
     * 'nextDueDate'.
     * 
     * @return the value of field 'nextDueDate'.
     */
    public java.lang.String getNextDueDate()
    {
        return this._nextDueDate;
    } //-- java.lang.String getNextDueDate() 

    /**
     * Method getPlacedAgentFKReturns the value of field
     * 'placedAgentFK'.
     * 
     * @return the value of field 'placedAgentFK'.
     */
    public long getPlacedAgentFK()
    {
        return this._placedAgentFK;
    } //-- long getPlacedAgentFK() 

    /**
     * Method getStartDateReturns the value of field 'startDate'.
     * 
     * @return the value of field 'startDate'.
     */
    public java.lang.String getStartDate()
    {
        return this._startDate;
    } //-- java.lang.String getStartDate() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method getTaxableStatusCTReturns the value of field
     * 'taxableStatusCT'.
     * 
     * @return the value of field 'taxableStatusCT'.
     */
    public java.lang.String getTaxableStatusCT()
    {
        return this._taxableStatusCT;
    } //-- java.lang.String getTaxableStatusCT() 

    /**
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

    /**
     * Method hasCheckAdjustmentPK
     */
    public boolean hasCheckAdjustmentPK()
    {
        return this._has_checkAdjustmentPK;
    } //-- boolean hasCheckAdjustmentPK() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

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
     * Method setAccumulatedDebitBalanceSets the value of field
     * 'accumulatedDebitBalance'.
     * 
     * @param accumulatedDebitBalance the value of field
     * 'accumulatedDebitBalance'.
     */
    public void setAccumulatedDebitBalance(java.math.BigDecimal accumulatedDebitBalance)
    {
        this._accumulatedDebitBalance = accumulatedDebitBalance;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedDebitBalance(java.math.BigDecimal) 

    /**
     * Method setAdjustmentCompleteIndSets the value of field
     * 'adjustmentCompleteInd'.
     * 
     * @param adjustmentCompleteInd the value of field
     * 'adjustmentCompleteInd'.
     */
    public void setAdjustmentCompleteInd(java.lang.String adjustmentCompleteInd)
    {
        this._adjustmentCompleteInd = adjustmentCompleteInd;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentCompleteInd(java.lang.String) 

    /**
     * Method setAdjustmentDollarSets the value of field
     * 'adjustmentDollar'.
     * 
     * @param adjustmentDollar the value of field 'adjustmentDollar'
     */
    public void setAdjustmentDollar(java.math.BigDecimal adjustmentDollar)
    {
        this._adjustmentDollar = adjustmentDollar;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentDollar(java.math.BigDecimal) 

    /**
     * Method setAdjustmentPercentSets the value of field
     * 'adjustmentPercent'.
     * 
     * @param adjustmentPercent the value of field
     * 'adjustmentPercent'.
     */
    public void setAdjustmentPercent(java.math.BigDecimal adjustmentPercent)
    {
        this._adjustmentPercent = adjustmentPercent;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentPercent(java.math.BigDecimal) 

    /**
     * Method setAdjustmentStatusCTSets the value of field
     * 'adjustmentStatusCT'.
     * 
     * @param adjustmentStatusCT the value of field
     * 'adjustmentStatusCT'.
     */
    public void setAdjustmentStatusCT(java.lang.String adjustmentStatusCT)
    {
        this._adjustmentStatusCT = adjustmentStatusCT;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentStatusCT(java.lang.String) 

    /**
     * Method setAdjustmentTypeCTSets the value of field
     * 'adjustmentTypeCT'.
     * 
     * @param adjustmentTypeCT the value of field 'adjustmentTypeCT'
     */
    public void setAdjustmentTypeCT(java.lang.String adjustmentTypeCT)
    {
        this._adjustmentTypeCT = adjustmentTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentTypeCT(java.lang.String) 

    /**
     * Method setAgentFKSets the value of field 'agentFK'.
     * 
     * @param agentFK the value of field 'agentFK'.
     */
    public void setAgentFK(long agentFK)
    {
        this._agentFK = agentFK;
        
        super.setVoChanged(true);
        this._has_agentFK = true;
    } //-- void setAgentFK(long) 

    /**
     * Method setCheckAdjustmentPKSets the value of field
     * 'checkAdjustmentPK'.
     * 
     * @param checkAdjustmentPK the value of field
     * 'checkAdjustmentPK'.
     */
    public void setCheckAdjustmentPK(long checkAdjustmentPK)
    {
        this._checkAdjustmentPK = checkAdjustmentPK;
        
        super.setVoChanged(true);
        this._has_checkAdjustmentPK = true;
    } //-- void setCheckAdjustmentPK(long) 

    /**
     * Method setDescriptionSets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
        
        super.setVoChanged(true);
    } //-- void setDescription(java.lang.String) 

    /**
     * Method setModeCTSets the value of field 'modeCT'.
     * 
     * @param modeCT the value of field 'modeCT'.
     */
    public void setModeCT(java.lang.String modeCT)
    {
        this._modeCT = modeCT;
        
        super.setVoChanged(true);
    } //-- void setModeCT(java.lang.String) 

    /**
     * Method setNextDueDateSets the value of field 'nextDueDate'.
     * 
     * @param nextDueDate the value of field 'nextDueDate'.
     */
    public void setNextDueDate(java.lang.String nextDueDate)
    {
        this._nextDueDate = nextDueDate;
        
        super.setVoChanged(true);
    } //-- void setNextDueDate(java.lang.String) 

    /**
     * Method setPlacedAgentFKSets the value of field
     * 'placedAgentFK'.
     * 
     * @param placedAgentFK the value of field 'placedAgentFK'.
     */
    public void setPlacedAgentFK(long placedAgentFK)
    {
        this._placedAgentFK = placedAgentFK;
        
        super.setVoChanged(true);
        this._has_placedAgentFK = true;
    } //-- void setPlacedAgentFK(long) 

    /**
     * Method setStartDateSets the value of field 'startDate'.
     * 
     * @param startDate the value of field 'startDate'.
     */
    public void setStartDate(java.lang.String startDate)
    {
        this._startDate = startDate;
        
        super.setVoChanged(true);
    } //-- void setStartDate(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method setTaxableStatusCTSets the value of field
     * 'taxableStatusCT'.
     * 
     * @param taxableStatusCT the value of field 'taxableStatusCT'.
     */
    public void setTaxableStatusCT(java.lang.String taxableStatusCT)
    {
        this._taxableStatusCT = taxableStatusCT;
        
        super.setVoChanged(true);
    } //-- void setTaxableStatusCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CheckAdjustmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CheckAdjustmentVO) Unmarshaller.unmarshal(edit.common.vo.CheckAdjustmentVO.class, reader);
    } //-- edit.common.vo.CheckAdjustmentVO unmarshal(java.io.Reader) 

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
