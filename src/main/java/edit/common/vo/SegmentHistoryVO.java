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
 * Class SegmentHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class SegmentHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _segmentHistoryPK
     */
    private long _segmentHistoryPK;

    /**
     * keeps track of state for field: _segmentHistoryPK
     */
    private boolean _has_segmentHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;
    
    /**
     * Field _billScheduleFK
     */
    private long _billScheduleFK;
    
    /**
     * keeps track of state for field: _billScheduleFK
     */
    private boolean _has_billScheduleFK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _prevFaceAmount
     */
    private java.math.BigDecimal _prevFaceAmount;

    private java.math.BigDecimal _prevAnnualPremium;

    /**
     * Field _prevSegmentStatus
     */
    private java.lang.String _prevSegmentStatus;

    private java.lang.String _priorRateClass;

    /**
     * Field _prevLastAnniversaryDate
     */
    private java.lang.String _prevLastAnniversaryDate;

    /**
     * Field _priorPaidToDate
     */
    private java.lang.String _priorPaidToDate;

    /**
     * Field _guarPaidUpTerm
     */
    private java.math.BigDecimal _guarPaidUpTerm;

    /**
     * Field _nonGuarPaidUpTerm
     */
    private java.math.BigDecimal _nonGuarPaidUpTerm;

    /**
     * Field _statusCT
     */
    private java.lang.String _statusCT;

    /**
     * Field _priorTerminationDate
     */
    private java.lang.String _priorTerminationDate;

      //----------------/
     //- Constructors -/
    //----------------/

    public SegmentHistoryVO() {
        super();
    } //-- edit.common.vo.SegmentHistoryVO()


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
        
        if (obj instanceof SegmentHistoryVO) {
        
            SegmentHistoryVO temp = (SegmentHistoryVO)obj;
            if (this._segmentHistoryPK != temp._segmentHistoryPK)
                return false;
            if (this._has_segmentHistoryPK != temp._has_segmentHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._has_billScheduleFK != temp._has_billScheduleFK)
            	return false;
            if (this._prevFaceAmount != null) {
                if (temp._prevFaceAmount == null) return false;
                else if (!(this._prevFaceAmount.equals(temp._prevFaceAmount))) 
                    return false;
            }
            else if (temp._prevFaceAmount != null)
                return false;
            if (this._prevSegmentStatus != null) {
                if (temp._prevSegmentStatus == null) return false;
                else if (!(this._prevSegmentStatus.equals(temp._prevSegmentStatus))) 
                    return false;
            }
            else if (temp._prevSegmentStatus != null)
                return false;
            if (this._prevLastAnniversaryDate != null) {
                if (temp._prevLastAnniversaryDate == null) return false;
                else if (!(this._prevLastAnniversaryDate.equals(temp._prevLastAnniversaryDate))) 
                    return false;
            }
            else if (temp._prevLastAnniversaryDate != null)
                return false;
            if (this._priorPaidToDate != null) {
                if (temp._priorPaidToDate == null) return false;
                else if (!(this._priorPaidToDate.equals(temp._priorPaidToDate))) 
                    return false;
            }
            else if (temp._priorPaidToDate != null)
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
            if (this._statusCT != null) {
                if (temp._statusCT == null) return false;
                else if (!(this._statusCT.equals(temp._statusCT))) 
                    return false;
            }
            else if (temp._priorTerminationDate != null)
                return false;
            if (this._priorTerminationDate != null) {
                if (temp._priorTerminationDate == null) return false;
                else if (!(this._priorTerminationDate.equals(temp._priorTerminationDate))) 
                    return false;
            }
            else if (temp._statusCT != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getPrevFaceAmountReturns the value of field
     * 'prevFaceAmount'.
     * 
     * @return the value of field 'prevFaceAmount'.
     */
    public java.math.BigDecimal getPrevFaceAmount()
    {
        return this._prevFaceAmount;
    } //-- java.math.BigDecimal getPrevFaceAmount() 

    public java.math.BigDecimal getPrevAnnualPremium()
    {
        return this._prevAnnualPremium;
    } //-- java.math.BigDecimal getPrevAnnualPremium() 

    /**
     * Method getPrevLastAnniversaryDateReturns the value of field
     * 'prevLastAnniversaryDate'.
     * 
     * @return the value of field 'prevLastAnniversaryDate'.
     */
    public java.lang.String getPrevLastAnniversaryDate()
    {
        return this._prevLastAnniversaryDate;
    } //-- java.lang.String getPrevLastAnniversaryDate() 

    /**
     * Method getPrevSegmentStatusReturns the value of field
     * 'prevSegmentStatus'.
     * 
     * @return the value of field 'prevSegmentStatus'.
     */
    public java.lang.String getPrevSegmentStatus()
    {
        return this._prevSegmentStatus;
    } //-- java.lang.String getPrevSegmentStatus() 

    public java.lang.String getPriorRateClass()
    {
        return this._priorRateClass;
    } 

    /**
     * Method getPriorPaidToDateReturns the value of field
     * 'priorPaidToDate'.
     * 
     * @return the value of field 'priorPaidToDate'.
     */
    public java.lang.String getPriorPaidToDate()
    {
        return this._priorPaidToDate;
    } //-- java.lang.String getPriorPaidToDate() 

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
     * Method getBillScheduleFK Returns the value of field 'BillScheduleFK'.
     * 
     * @return the value of field 'billScheduleFK'.
     */
    public long getBillScheduleFK()
    {
        return this._billScheduleFK;
    }
    
    /**
     * Method getPriorTerminationDate
     * Returns the value of field '_priorTerminationDate'.
     * 
     * @return the value of field '_priorTerminationDate'.
     */
    public java.lang.String getPriorTerminationDate()
    {
        return this._priorTerminationDate;
    }
    
    /**
     * Method getSegmentHistoryPKReturns the value of field
     * 'segmentHistoryPK'.
     * 
     * @return the value of field 'segmentHistoryPK'.
     */
    public long getSegmentHistoryPK()
    {
        return this._segmentHistoryPK;
    } //-- long getSegmentHistoryPK() 

    /**
     * Method getStatusCTReturns the value of field 'statusCT'.
     * 
     * @return the value of field 'statusCT'.
     */
    public java.lang.String getStatusCT()
    {
        return this._statusCT;
    } //-- java.lang.String getStatusCT() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 
    
    /**
     * Method hasBillScheduleFK
     */
    public boolean hasBillScheduleFK()
    {
        return this._has_billScheduleFK;
    }

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasSegmentHistoryPK
     */
    public boolean hasSegmentHistoryPK()
    {
        return this._has_segmentHistoryPK;
    } //-- boolean hasSegmentHistoryPK() 

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
     * Method setPrevFaceAmountSets the value of field
     * 'prevFaceAmount'.
     * 
     * @param prevFaceAmount the value of field 'prevFaceAmount'.
     */
    public void setPrevFaceAmount(java.math.BigDecimal prevFaceAmount)
    {
        this._prevFaceAmount = prevFaceAmount;
        
        super.setVoChanged(true);
    } //-- void setPrevFaceAmount(java.math.BigDecimal) 

    public void setPrevAnnualPremium(java.math.BigDecimal prevAnnualPremium)
    {
        this._prevAnnualPremium = prevAnnualPremium;
        
        super.setVoChanged(true);
    } //-- void setPrevAnnualPremium(java.math.BigDecimal) 

    /**
     * Method setPrevLastAnniversaryDateSets the value of field
     * 'prevLastAnniversaryDate'.
     * 
     * @param prevLastAnniversaryDate the value of field
     * 'prevLastAnniversaryDate'.
     */
    public void setPrevLastAnniversaryDate(java.lang.String prevLastAnniversaryDate)
    {
        this._prevLastAnniversaryDate = prevLastAnniversaryDate;
        
        super.setVoChanged(true);
    } //-- void setPrevLastAnniversaryDate(java.lang.String) 

    /**
     * Method setPrevSegmentStatusSets the value of field
     * 'prevSegmentStatus'.
     * 
     * @param prevSegmentStatus the value of field
     * 'prevSegmentStatus'.
     */
    public void setPrevSegmentStatus(java.lang.String prevSegmentStatus)
    {
        this._prevSegmentStatus = prevSegmentStatus;
        
        super.setVoChanged(true);
    } //-- void setPrevSegmentStatus(java.lang.String) 

    public void setPriorRateClass(java.lang.String priorRateClass)
    {
        this._priorRateClass = priorRateClass;
        
        super.setVoChanged(true);
    } //-- void setPrevSegmentStatus(java.lang.String) 

    /**
     * Method setPriorPaidToDateSets the value of field
     * 'priorPaidToDate'.
     * 
     * @param priorPaidToDate the value of field 'priorPaidToDate'.
     */
    public void setPriorPaidToDate(java.lang.String priorPaidToDate)
    {
        this._priorPaidToDate = priorPaidToDate;
        
        super.setVoChanged(true);
    } //-- void setPriorPaidToDate(java.lang.String) 

    /**
     * Method setPriorTerminationDate Sets the value of field
     * 'priorPaidToDate'.
     * 
     * @param priorTerminationDate
     */
    public void setPriorTerminationDate(java.lang.String priorTerminationDate)
    {
        this._priorTerminationDate = priorTerminationDate;
        
        super.setVoChanged(true);
    }
    
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
     * Method setBillScheduleFK Sets the value of field 'billScheduleFK'.
     * 
     * @param billScheduleFK the value of field 'billScheduleFK'.
     */
    public void setBillScheduleFK(long billScheduleFK)
    {
        this._billScheduleFK = billScheduleFK;
        
        super.setVoChanged(true);
        this._has_billScheduleFK = true;
    }

    /**
     * Method setSegmentHistoryPKSets the value of field
     * 'segmentHistoryPK'.
     * 
     * @param segmentHistoryPK the value of field 'segmentHistoryPK'
     */
    public void setSegmentHistoryPK(long segmentHistoryPK)
    {
        this._segmentHistoryPK = segmentHistoryPK;
        
        super.setVoChanged(true);
        this._has_segmentHistoryPK = true;
    } //-- void setSegmentHistoryPK(long) 

    /**
     * Method setStatusCTSets the value of field 'statusCT'.
     * 
     * @param statusCT the value of field 'statusCT'.
     */
    public void setStatusCT(java.lang.String statusCT)
    {
        this._statusCT = statusCT;
        
        super.setVoChanged(true);
    } //-- void setStatusCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SegmentHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SegmentHistoryVO) Unmarshaller.unmarshal(edit.common.vo.SegmentHistoryVO.class, reader);
    } //-- edit.common.vo.SegmentHistoryVO unmarshal(java.io.Reader) 

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
