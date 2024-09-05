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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class ScheduledEventVO.
 * 
 * @version $Revision$ $Date$
 */
public class ScheduledEventVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _scheduledEventPK
     */
    private long _scheduledEventPK;

    /**
     * keeps track of state for field: _scheduledEventPK
     */
    private boolean _has_scheduledEventPK;

    /**
     * Field _groupSetupFK
     */
    private long _groupSetupFK;

    /**
     * keeps track of state for field: _groupSetupFK
     */
    private boolean _has_groupSetupFK;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _lastDayOfMonthInd
     */
    private java.lang.String _lastDayOfMonthInd;

    /**
     * Field _frequencyCT
     */
    private java.lang.String _frequencyCT;

    /**
     * Field _lifeContingentCT
     */
    private java.lang.String _lifeContingentCT;

    /**
     * Field _costOfLivingInd
     */
    private java.lang.String _costOfLivingInd;


      //----------------/
     //- Constructors -/
    //----------------/

    public ScheduledEventVO() {
        super();
    } //-- edit.common.vo.ScheduledEventVO()


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
        
        if (obj instanceof ScheduledEventVO) {
        
            ScheduledEventVO temp = (ScheduledEventVO)obj;
            if (this._scheduledEventPK != temp._scheduledEventPK)
                return false;
            if (this._has_scheduledEventPK != temp._has_scheduledEventPK)
                return false;
            if (this._groupSetupFK != temp._groupSetupFK)
                return false;
            if (this._has_groupSetupFK != temp._has_groupSetupFK)
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
            if (this._lastDayOfMonthInd != null) {
                if (temp._lastDayOfMonthInd == null) return false;
                else if (!(this._lastDayOfMonthInd.equals(temp._lastDayOfMonthInd))) 
                    return false;
            }
            else if (temp._lastDayOfMonthInd != null)
                return false;
            if (this._frequencyCT != null) {
                if (temp._frequencyCT == null) return false;
                else if (!(this._frequencyCT.equals(temp._frequencyCT))) 
                    return false;
            }
            else if (temp._frequencyCT != null)
                return false;
            if (this._lifeContingentCT != null) {
                if (temp._lifeContingentCT == null) return false;
                else if (!(this._lifeContingentCT.equals(temp._lifeContingentCT))) 
                    return false;
            }
            else if (temp._lifeContingentCT != null)
                return false;
            if (this._costOfLivingInd != null) {
                if (temp._costOfLivingInd == null) return false;
                else if (!(this._costOfLivingInd.equals(temp._costOfLivingInd))) 
                    return false;
            }
            else if (temp._costOfLivingInd != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCostOfLivingIndReturns the value of field
     * 'costOfLivingInd'.
     * 
     * @return the value of field 'costOfLivingInd'.
     */
    public java.lang.String getCostOfLivingInd()
    {
        return this._costOfLivingInd;
    } //-- java.lang.String getCostOfLivingInd() 

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
     * Method getGroupSetupFKReturns the value of field
     * 'groupSetupFK'.
     * 
     * @return the value of field 'groupSetupFK'.
     */
    public long getGroupSetupFK()
    {
        return this._groupSetupFK;
    } //-- long getGroupSetupFK() 

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
     * Method getLifeContingentCTReturns the value of field
     * 'lifeContingentCT'.
     * 
     * @return the value of field 'lifeContingentCT'.
     */
    public java.lang.String getLifeContingentCT()
    {
        return this._lifeContingentCT;
    } //-- java.lang.String getLifeContingentCT() 

    /**
     * Method getScheduledEventPKReturns the value of field
     * 'scheduledEventPK'.
     * 
     * @return the value of field 'scheduledEventPK'.
     */
    public long getScheduledEventPK()
    {
        return this._scheduledEventPK;
    } //-- long getScheduledEventPK() 

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
     * Method hasGroupSetupFK
     */
    public boolean hasGroupSetupFK()
    {
        return this._has_groupSetupFK;
    } //-- boolean hasGroupSetupFK() 

    /**
     * Method hasScheduledEventPK
     */
    public boolean hasScheduledEventPK()
    {
        return this._has_scheduledEventPK;
    } //-- boolean hasScheduledEventPK() 

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
     * Method setCostOfLivingIndSets the value of field
     * 'costOfLivingInd'.
     * 
     * @param costOfLivingInd the value of field 'costOfLivingInd'.
     */
    public void setCostOfLivingInd(java.lang.String costOfLivingInd)
    {
        this._costOfLivingInd = costOfLivingInd;
        
        super.setVoChanged(true);
    } //-- void setCostOfLivingInd(java.lang.String) 

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
     * Method setGroupSetupFKSets the value of field
     * 'groupSetupFK'.
     * 
     * @param groupSetupFK the value of field 'groupSetupFK'.
     */
    public void setGroupSetupFK(long groupSetupFK)
    {
        this._groupSetupFK = groupSetupFK;
        
        super.setVoChanged(true);
        this._has_groupSetupFK = true;
    } //-- void setGroupSetupFK(long) 

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
     * Method setLifeContingentCTSets the value of field
     * 'lifeContingentCT'.
     * 
     * @param lifeContingentCT the value of field 'lifeContingentCT'
     */
    public void setLifeContingentCT(java.lang.String lifeContingentCT)
    {
        this._lifeContingentCT = lifeContingentCT;
        
        super.setVoChanged(true);
    } //-- void setLifeContingentCT(java.lang.String) 

    /**
     * Method setScheduledEventPKSets the value of field
     * 'scheduledEventPK'.
     * 
     * @param scheduledEventPK the value of field 'scheduledEventPK'
     */
    public void setScheduledEventPK(long scheduledEventPK)
    {
        this._scheduledEventPK = scheduledEventPK;
        
        super.setVoChanged(true);
        this._has_scheduledEventPK = true;
    } //-- void setScheduledEventPK(long) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ScheduledEventVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ScheduledEventVO) Unmarshaller.unmarshal(edit.common.vo.ScheduledEventVO.class, reader);
    } //-- edit.common.vo.ScheduledEventVO unmarshal(java.io.Reader) 

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
