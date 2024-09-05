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
 * Class PayrollDeductionScheduleVO.
 * 
 * @version $Revision$ $Date$
 */
public class PayrollDeductionScheduleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _payrollDeductionSchedulePK
     */
    private long _payrollDeductionSchedulePK;

    /**
     * keeps track of state for field: _payrollDeductionSchedulePK
     */
    private boolean _has_payrollDeductionSchedulePK;

    /**
     * Field _contractGroupFK
     */
    private long _contractGroupFK;

    /**
     * keeps track of state for field: _contractGroupFK
     */
    private boolean _has_contractGroupFK;

    /**
     * Field _PRDTypeCT
     */
    private java.lang.String _PRDTypeCT;

    /**
     * Field _initialLeadDays
     */
    private int _initialLeadDays;

    /**
     * keeps track of state for field: _initialLeadDays
     */
    private boolean _has_initialLeadDays;

    /**
     * Field _subsequentLeadDays
     */
    private int _subsequentLeadDays;

    /**
     * keeps track of state for field: _subsequentLeadDays
     */
    private boolean _has_subsequentLeadDays;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _lastPRDExtractDate
     */
    private java.lang.String _lastPRDExtractDate;

    /**
     * Field _nextPRDExtractDate
     */
    private java.lang.String _nextPRDExtractDate;

    /**
     * Field _nextPRDDueDate
     */
    private java.lang.String _nextPRDDueDate;

    /**
     * Field _currentDateThru
     */
    private java.lang.String _currentDateThru;

    /**
     * Field _PRDConsolidationCT
     */
    private java.lang.String _PRDConsolidationCT;

    /**
     * Field _reportTypeCT
     */
    private java.lang.String _reportTypeCT;

    /**
     * Field _sortOptionCT
     */
    private java.lang.String _sortOptionCT;

    /**
     * Field _summaryCT
     */
    private java.lang.String _summaryCT;

    /**
     * Field _outputTypeCT
     */
    private java.lang.String _outputTypeCT;

    /**
     * Field _creationOperator
     */
    private java.lang.String _creationOperator;

    /**
     * Field _creationDate
     */
    private java.lang.String _creationDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public PayrollDeductionScheduleVO() {
        super();
    } //-- edit.common.vo.PayrollDeductionScheduleVO()


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
        
        if (obj instanceof PayrollDeductionScheduleVO) {
        
            PayrollDeductionScheduleVO temp = (PayrollDeductionScheduleVO)obj;
            if (this._payrollDeductionSchedulePK != temp._payrollDeductionSchedulePK)
                return false;
            if (this._has_payrollDeductionSchedulePK != temp._has_payrollDeductionSchedulePK)
                return false;
            if (this._contractGroupFK != temp._contractGroupFK)
                return false;
            if (this._has_contractGroupFK != temp._has_contractGroupFK)
                return false;
            if (this._PRDTypeCT != null) {
                if (temp._PRDTypeCT == null) return false;
                else if (!(this._PRDTypeCT.equals(temp._PRDTypeCT))) 
                    return false;
            }
            else if (temp._PRDTypeCT != null)
                return false;
            if (this._initialLeadDays != temp._initialLeadDays)
                return false;
            if (this._has_initialLeadDays != temp._has_initialLeadDays)
                return false;
            if (this._subsequentLeadDays != temp._subsequentLeadDays)
                return false;
            if (this._has_subsequentLeadDays != temp._has_subsequentLeadDays)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._terminationDate != null) {
                if (temp._terminationDate == null) return false;
                else if (!(this._terminationDate.equals(temp._terminationDate))) 
                    return false;
            }
            else if (temp._terminationDate != null)
                return false;
            if (this._lastPRDExtractDate != null) {
                if (temp._lastPRDExtractDate == null) return false;
                else if (!(this._lastPRDExtractDate.equals(temp._lastPRDExtractDate))) 
                    return false;
            }
            else if (temp._lastPRDExtractDate != null)
                return false;
            if (this._nextPRDExtractDate != null) {
                if (temp._nextPRDExtractDate == null) return false;
                else if (!(this._nextPRDExtractDate.equals(temp._nextPRDExtractDate))) 
                    return false;
            }
            else if (temp._nextPRDExtractDate != null)
                return false;
            if (this._nextPRDDueDate != null) {
                if (temp._nextPRDDueDate == null) return false;
                else if (!(this._nextPRDDueDate.equals(temp._nextPRDDueDate))) 
                    return false;
            }
            else if (temp._nextPRDDueDate != null)
                return false;
            if (this._currentDateThru != null) {
                if (temp._currentDateThru == null) return false;
                else if (!(this._currentDateThru.equals(temp._currentDateThru))) 
                    return false;
            }
            else if (temp._currentDateThru != null)
                return false;
            if (this._PRDConsolidationCT != null) {
                if (temp._PRDConsolidationCT == null) return false;
                else if (!(this._PRDConsolidationCT.equals(temp._PRDConsolidationCT))) 
                    return false;
            }
            else if (temp._PRDConsolidationCT != null)
                return false;
            if (this._reportTypeCT != null) {
                if (temp._reportTypeCT == null) return false;
                else if (!(this._reportTypeCT.equals(temp._reportTypeCT))) 
                    return false;
            }
            else if (temp._reportTypeCT != null)
                return false;
            if (this._sortOptionCT != null) {
                if (temp._sortOptionCT == null) return false;
                else if (!(this._sortOptionCT.equals(temp._sortOptionCT))) 
                    return false;
            }
            else if (temp._sortOptionCT != null)
                return false;
            if (this._summaryCT != null) {
                if (temp._summaryCT == null) return false;
                else if (!(this._summaryCT.equals(temp._summaryCT))) 
                    return false;
            }
            else if (temp._summaryCT != null)
                return false;
            if (this._outputTypeCT != null) {
                if (temp._outputTypeCT == null) return false;
                else if (!(this._outputTypeCT.equals(temp._outputTypeCT))) 
                    return false;
            }
            else if (temp._outputTypeCT != null)
                return false;
            if (this._creationOperator != null) {
                if (temp._creationOperator == null) return false;
                else if (!(this._creationOperator.equals(temp._creationOperator))) 
                    return false;
            }
            else if (temp._creationOperator != null)
                return false;
            if (this._creationDate != null) {
                if (temp._creationDate == null) return false;
                else if (!(this._creationDate.equals(temp._creationDate))) 
                    return false;
            }
            else if (temp._creationDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractGroupFKReturns the value of field
     * 'contractGroupFK'.
     * 
     * @return the value of field 'contractGroupFK'.
     */
    public long getContractGroupFK()
    {
        return this._contractGroupFK;
    } //-- long getContractGroupFK() 

    /**
     * Method getCreationDateReturns the value of field
     * 'creationDate'.
     * 
     * @return the value of field 'creationDate'.
     */
    public java.lang.String getCreationDate()
    {
        return this._creationDate;
    } //-- java.lang.String getCreationDate() 

    /**
     * Method getCreationOperatorReturns the value of field
     * 'creationOperator'.
     * 
     * @return the value of field 'creationOperator'.
     */
    public java.lang.String getCreationOperator()
    {
        return this._creationOperator;
    } //-- java.lang.String getCreationOperator() 

    /**
     * Method getCurrentDateThruReturns the value of field
     * 'currentDateThru'.
     * 
     * @return the value of field 'currentDateThru'.
     */
    public java.lang.String getCurrentDateThru()
    {
        return this._currentDateThru;
    } //-- java.lang.String getCurrentDateThru() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getInitialLeadDaysReturns the value of field
     * 'initialLeadDays'.
     * 
     * @return the value of field 'initialLeadDays'.
     */
    public int getInitialLeadDays()
    {
        return this._initialLeadDays;
    } //-- int getInitialLeadDays() 

    /**
     * Method getLastPRDExtractDateReturns the value of field
     * 'lastPRDExtractDate'.
     * 
     * @return the value of field 'lastPRDExtractDate'.
     */
    public java.lang.String getLastPRDExtractDate()
    {
        return this._lastPRDExtractDate;
    } //-- java.lang.String getLastPRDExtractDate() 

    /**
     * Method getNextPRDDueDateReturns the value of field
     * 'nextPRDDueDate'.
     * 
     * @return the value of field 'nextPRDDueDate'.
     */
    public java.lang.String getNextPRDDueDate()
    {
        return this._nextPRDDueDate;
    } //-- java.lang.String getNextPRDDueDate() 

    /**
     * Method getNextPRDExtractDateReturns the value of field
     * 'nextPRDExtractDate'.
     * 
     * @return the value of field 'nextPRDExtractDate'.
     */
    public java.lang.String getNextPRDExtractDate()
    {
        return this._nextPRDExtractDate;
    } //-- java.lang.String getNextPRDExtractDate() 

    /**
     * Method getOutputTypeCTReturns the value of field
     * 'outputTypeCT'.
     * 
     * @return the value of field 'outputTypeCT'.
     */
    public java.lang.String getOutputTypeCT()
    {
        return this._outputTypeCT;
    } //-- java.lang.String getOutputTypeCT() 

    /**
     * Method getPRDConsolidationCTReturns the value of field
     * 'PRDConsolidationCT'.
     * 
     * @return the value of field 'PRDConsolidationCT'.
     */
    public java.lang.String getPRDConsolidationCT()
    {
        return this._PRDConsolidationCT;
    } //-- java.lang.String getPRDConsolidationCT() 

    /**
     * Method getPRDTypeCTReturns the value of field 'PRDTypeCT'.
     * 
     * @return the value of field 'PRDTypeCT'.
     */
    public java.lang.String getPRDTypeCT()
    {
        return this._PRDTypeCT;
    } //-- java.lang.String getPRDTypeCT() 

    /**
     * Method getPayrollDeductionSchedulePKReturns the value of
     * field 'payrollDeductionSchedulePK'.
     * 
     * @return the value of field 'payrollDeductionSchedulePK'.
     */
    public long getPayrollDeductionSchedulePK()
    {
        return this._payrollDeductionSchedulePK;
    } //-- long getPayrollDeductionSchedulePK() 

    /**
     * Method getReportTypeCTReturns the value of field
     * 'reportTypeCT'.
     * 
     * @return the value of field 'reportTypeCT'.
     */
    public java.lang.String getReportTypeCT()
    {
        return this._reportTypeCT;
    } //-- java.lang.String getReportTypeCT() 

    /**
     * Method getSortOptionCTReturns the value of field
     * 'sortOptionCT'.
     * 
     * @return the value of field 'sortOptionCT'.
     */
    public java.lang.String getSortOptionCT()
    {
        return this._sortOptionCT;
    } //-- java.lang.String getSortOptionCT() 

    /**
     * Method getSubsequentLeadDaysReturns the value of field
     * 'subsequentLeadDays'.
     * 
     * @return the value of field 'subsequentLeadDays'.
     */
    public int getSubsequentLeadDays()
    {
        return this._subsequentLeadDays;
    } //-- int getSubsequentLeadDays() 

    /**
     * Method getSummaryCTReturns the value of field 'summaryCT'.
     * 
     * @return the value of field 'summaryCT'.
     */
    public java.lang.String getSummaryCT()
    {
        return this._summaryCT;
    } //-- java.lang.String getSummaryCT() 

    /**
     * Method getTerminationDateReturns the value of field
     * 'terminationDate'.
     * 
     * @return the value of field 'terminationDate'.
     */
    public java.lang.String getTerminationDate()
    {
        return this._terminationDate;
    } //-- java.lang.String getTerminationDate() 

    /**
     * Method hasContractGroupFK
     */
    public boolean hasContractGroupFK()
    {
        return this._has_contractGroupFK;
    } //-- boolean hasContractGroupFK() 

    /**
     * Method hasInitialLeadDays
     */
    public boolean hasInitialLeadDays()
    {
        return this._has_initialLeadDays;
    } //-- boolean hasInitialLeadDays() 

    /**
     * Method hasPayrollDeductionSchedulePK
     */
    public boolean hasPayrollDeductionSchedulePK()
    {
        return this._has_payrollDeductionSchedulePK;
    } //-- boolean hasPayrollDeductionSchedulePK() 

    /**
     * Method hasSubsequentLeadDays
     */
    public boolean hasSubsequentLeadDays()
    {
        return this._has_subsequentLeadDays;
    } //-- boolean hasSubsequentLeadDays() 

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
     * Method setContractGroupFKSets the value of field
     * 'contractGroupFK'.
     * 
     * @param contractGroupFK the value of field 'contractGroupFK'.
     */
    public void setContractGroupFK(long contractGroupFK)
    {
        this._contractGroupFK = contractGroupFK;
        
        super.setVoChanged(true);
        this._has_contractGroupFK = true;
    } //-- void setContractGroupFK(long) 

    /**
     * Method setCreationDateSets the value of field
     * 'creationDate'.
     * 
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(java.lang.String creationDate)
    {
        this._creationDate = creationDate;
        
        super.setVoChanged(true);
    } //-- void setCreationDate(java.lang.String) 

    /**
     * Method setCreationOperatorSets the value of field
     * 'creationOperator'.
     * 
     * @param creationOperator the value of field 'creationOperator'
     */
    public void setCreationOperator(java.lang.String creationOperator)
    {
        this._creationOperator = creationOperator;
        
        super.setVoChanged(true);
    } //-- void setCreationOperator(java.lang.String) 

    /**
     * Method setCurrentDateThruSets the value of field
     * 'currentDateThru'.
     * 
     * @param currentDateThru the value of field 'currentDateThru'.
     */
    public void setCurrentDateThru(java.lang.String currentDateThru)
    {
        this._currentDateThru = currentDateThru;
        
        super.setVoChanged(true);
    } //-- void setCurrentDateThru(java.lang.String) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setInitialLeadDaysSets the value of field
     * 'initialLeadDays'.
     * 
     * @param initialLeadDays the value of field 'initialLeadDays'.
     */
    public void setInitialLeadDays(int initialLeadDays)
    {
        this._initialLeadDays = initialLeadDays;
        
        super.setVoChanged(true);
        this._has_initialLeadDays = true;
    } //-- void setInitialLeadDays(int) 

    /**
     * Method setLastPRDExtractDateSets the value of field
     * 'lastPRDExtractDate'.
     * 
     * @param lastPRDExtractDate the value of field
     * 'lastPRDExtractDate'.
     */
    public void setLastPRDExtractDate(java.lang.String lastPRDExtractDate)
    {
        this._lastPRDExtractDate = lastPRDExtractDate;
        
        super.setVoChanged(true);
    } //-- void setLastPRDExtractDate(java.lang.String) 

    /**
     * Method setNextPRDDueDateSets the value of field
     * 'nextPRDDueDate'.
     * 
     * @param nextPRDDueDate the value of field 'nextPRDDueDate'.
     */
    public void setNextPRDDueDate(java.lang.String nextPRDDueDate)
    {
        this._nextPRDDueDate = nextPRDDueDate;
        
        super.setVoChanged(true);
    } //-- void setNextPRDDueDate(java.lang.String) 

    /**
     * Method setNextPRDExtractDateSets the value of field
     * 'nextPRDExtractDate'.
     * 
     * @param nextPRDExtractDate the value of field
     * 'nextPRDExtractDate'.
     */
    public void setNextPRDExtractDate(java.lang.String nextPRDExtractDate)
    {
        this._nextPRDExtractDate = nextPRDExtractDate;
        
        super.setVoChanged(true);
    } //-- void setNextPRDExtractDate(java.lang.String) 

    /**
     * Method setOutputTypeCTSets the value of field
     * 'outputTypeCT'.
     * 
     * @param outputTypeCT the value of field 'outputTypeCT'.
     */
    public void setOutputTypeCT(java.lang.String outputTypeCT)
    {
        this._outputTypeCT = outputTypeCT;
        
        super.setVoChanged(true);
    } //-- void setOutputTypeCT(java.lang.String) 

    /**
     * Method setPRDConsolidationCTSets the value of field
     * 'PRDConsolidationCT'.
     * 
     * @param PRDConsolidationCT the value of field
     * 'PRDConsolidationCT'.
     */
    public void setPRDConsolidationCT(java.lang.String PRDConsolidationCT)
    {
        this._PRDConsolidationCT = PRDConsolidationCT;
        
        super.setVoChanged(true);
    } //-- void setPRDConsolidationCT(java.lang.String) 

    /**
     * Method setPRDTypeCTSets the value of field 'PRDTypeCT'.
     * 
     * @param PRDTypeCT the value of field 'PRDTypeCT'.
     */
    public void setPRDTypeCT(java.lang.String PRDTypeCT)
    {
        this._PRDTypeCT = PRDTypeCT;
        
        super.setVoChanged(true);
    } //-- void setPRDTypeCT(java.lang.String) 

    /**
     * Method setPayrollDeductionSchedulePKSets the value of field
     * 'payrollDeductionSchedulePK'.
     * 
     * @param payrollDeductionSchedulePK the value of field
     * 'payrollDeductionSchedulePK'.
     */
    public void setPayrollDeductionSchedulePK(long payrollDeductionSchedulePK)
    {
        this._payrollDeductionSchedulePK = payrollDeductionSchedulePK;
        
        super.setVoChanged(true);
        this._has_payrollDeductionSchedulePK = true;
    } //-- void setPayrollDeductionSchedulePK(long) 

    /**
     * Method setReportTypeCTSets the value of field
     * 'reportTypeCT'.
     * 
     * @param reportTypeCT the value of field 'reportTypeCT'.
     */
    public void setReportTypeCT(java.lang.String reportTypeCT)
    {
        this._reportTypeCT = reportTypeCT;
        
        super.setVoChanged(true);
    } //-- void setReportTypeCT(java.lang.String) 

    /**
     * Method setSortOptionCTSets the value of field
     * 'sortOptionCT'.
     * 
     * @param sortOptionCT the value of field 'sortOptionCT'.
     */
    public void setSortOptionCT(java.lang.String sortOptionCT)
    {
        this._sortOptionCT = sortOptionCT;
        
        super.setVoChanged(true);
    } //-- void setSortOptionCT(java.lang.String) 

    /**
     * Method setSubsequentLeadDaysSets the value of field
     * 'subsequentLeadDays'.
     * 
     * @param subsequentLeadDays the value of field
     * 'subsequentLeadDays'.
     */
    public void setSubsequentLeadDays(int subsequentLeadDays)
    {
        this._subsequentLeadDays = subsequentLeadDays;
        
        super.setVoChanged(true);
        this._has_subsequentLeadDays = true;
    } //-- void setSubsequentLeadDays(int) 

    /**
     * Method setSummaryCTSets the value of field 'summaryCT'.
     * 
     * @param summaryCT the value of field 'summaryCT'.
     */
    public void setSummaryCT(java.lang.String summaryCT)
    {
        this._summaryCT = summaryCT;
        
        super.setVoChanged(true);
    } //-- void setSummaryCT(java.lang.String) 

    /**
     * Method setTerminationDateSets the value of field
     * 'terminationDate'.
     * 
     * @param terminationDate the value of field 'terminationDate'.
     */
    public void setTerminationDate(java.lang.String terminationDate)
    {
        this._terminationDate = terminationDate;
        
        super.setVoChanged(true);
    } //-- void setTerminationDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PayrollDeductionScheduleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PayrollDeductionScheduleVO) Unmarshaller.unmarshal(edit.common.vo.PayrollDeductionScheduleVO.class, reader);
    } //-- edit.common.vo.PayrollDeductionScheduleVO unmarshal(java.io.Reader) 

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
