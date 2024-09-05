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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

import billing.BillSchedule;
import contract.Segment;
import edit.common.EDITDate;
import engine.AreaValue;
import engine.ProductStructure;

/**
 * Class BillScheduleVO.
 * 
 * @version $Revision$ $Date$
 */
public class BillScheduleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _billSchedulePK
     */
    private long _billSchedulePK;

    /**
     * keeps track of state for field: _billSchedulePK
     */
    private boolean _has_billSchedulePK;

    /**
     * Field _billingCompanyCT
     */
    private java.lang.String _billingCompanyCT;

    /**
     * Field _firstBillDueDate
     */
    private java.lang.String _firstBillDueDate;

    /**
     * Field _leadDaysOR
     */
    private int _leadDaysOR;

    /**
     * keeps track of state for field: _leadDaysOR
     */
    private boolean _has_leadDaysOR;

    /**
     * Field _weekDayCT
     */
    private java.lang.String _weekDayCT;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _statusCT
     */
    private java.lang.String _statusCT;

    /**
     * Field _sortOptionCT
     */
    private java.lang.String _sortOptionCT;

    /**
     * Field _billingConsolidationCT
     */
    private java.lang.String _billingConsolidationCT;

    /**
     * Field _socialSecurityMaskCT
     */
    private java.lang.String _socialSecurityMaskCT;

    /**
     * Field _numberOfCopiesGroup
     */
    private int _numberOfCopiesGroup;

    /**
     * keeps track of state for field: _numberOfCopiesGroup
     */
    private boolean _has_numberOfCopiesGroup;

    /**
     * Field _numberOfCopiesAgent
     */
    private int _numberOfCopiesAgent;

    /**
     * keeps track of state for field: _numberOfCopiesAgent
     */
    private boolean _has_numberOfCopiesAgent;

    /**
     * Field _nextBillExtractDate
     */
    private java.lang.String _nextBillExtractDate;

    /**
     * Field _nextBillDueDate
     */
    private java.lang.String _nextBillDueDate;

    /**
     * Field _lastBillDueDate
     */
    private java.lang.String _lastBillDueDate;

    /**
     * Field _billingModeCT
     */
    private java.lang.String _billingModeCT;

    /**
     * Field _billTypeCT
     */
    private java.lang.String _billTypeCT;

    /**
     * Field _creationOperator
     */
    private java.lang.String _creationOperator;

    /**
     * Field _creationDate
     */
    private java.lang.String _creationDate;

    /**
     * Field _repName
     */
    private java.lang.String _repName;

    /**
     * Field _repPhoneNumber
     */
    private java.lang.String _repPhoneNumber;

    /**
     * Field _billMethodCT
     */
    private java.lang.String _billMethodCT;

    /**
     * Field _skipMonthStart1CT
     */
    private java.lang.String _skipMonthStart1CT;

    /**
     * Field _skipNumberOfMonths1CT
     */
    private java.lang.String _skipNumberOfMonths1CT;

    /**
     * Field _skipMonthStart2CT
     */
    private java.lang.String _skipMonthStart2CT;

    /**
     * Field _skipNumberOfMonths2CT
     */
    private java.lang.String _skipNumberOfMonths2CT;

    /**
     * Field _skipMonthStart3CT
     */
    private java.lang.String _skipMonthStart3CT;

    /**
     * Field _skipNumberOfMonths3CT
     */
    private java.lang.String _skipNumberOfMonths3CT;

    /**
     * Field _firstDeductionDate
     */
    private java.lang.String _firstDeductionDate;

    /**
     * Field _lastDeductionDate
     */
    private java.lang.String _lastDeductionDate;

    /**
     * Field _deductionFrequencyCT
     */
    private java.lang.String _deductionFrequencyCT;

    /**
     * Field _requiredPremiumAmount
     */
    private java.math.BigDecimal _requiredPremiumAmount;

    /**
     * Field _transitionPeriodEndDate
     */
    private java.lang.String _transitionPeriodEndDate;

    /**
     * Field _billChangeStartDate
     */
    private java.lang.String _billChangeStartDate;

    /**
     * Field _varMonthDedChangeStartDate
     */
    private java.lang.String _varMonthDedChangeStartDate;

    /**
     * Field _changeEffectiveDate
     */
    private java.lang.String _changeEffectiveDate;

    /**
     * Field _lastPremiumChangeStartDate
     */
    private java.lang.String _lastPremiumChangeStartDate;

    /**
     * Field _EFTDraftDay
     */
    private int _EFTDraftDay;

    /**
     * keeps track of state for field: _EFTDraftDay
     */
    private boolean _has_EFTDraftDay;

    /**
     * Field _EFTDraftDayStartMonthCT
     */
    private java.lang.String _EFTDraftDayStartMonthCT;

    /**
     * Field _billGroupVOList
     */
    private java.util.Vector _billGroupVOList;

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;

    /**
     * Field _contractGroupVOList
     */
    private java.util.Vector _contractGroupVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BillScheduleVO() {
        super();
        _billGroupVOList = new Vector();
        _segmentVOList = new Vector();
        _contractGroupVOList = new Vector();
    } //-- edit.common.vo.BillScheduleVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBillGroupVO
     * 
     * @param vBillGroupVO
     */
    public void addBillGroupVO(edit.common.vo.BillGroupVO vBillGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBillGroupVO.setParentVO(this.getClass(), this);
        _billGroupVOList.addElement(vBillGroupVO);
    } //-- void addBillGroupVO(edit.common.vo.BillGroupVO) 

    /**
     * Method addBillGroupVO
     * 
     * @param index
     * @param vBillGroupVO
     */
    public void addBillGroupVO(int index, edit.common.vo.BillGroupVO vBillGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBillGroupVO.setParentVO(this.getClass(), this);
        _billGroupVOList.insertElementAt(vBillGroupVO, index);
    } //-- void addBillGroupVO(int, edit.common.vo.BillGroupVO) 

    /**
     * Method addContractGroupVO
     * 
     * @param vContractGroupVO
     */
    public void addContractGroupVO(edit.common.vo.ContractGroupVO vContractGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractGroupVO.setParentVO(this.getClass(), this);
        _contractGroupVOList.addElement(vContractGroupVO);
    } //-- void addContractGroupVO(edit.common.vo.ContractGroupVO) 

    /**
     * Method addContractGroupVO
     * 
     * @param index
     * @param vContractGroupVO
     */
    public void addContractGroupVO(int index, edit.common.vo.ContractGroupVO vContractGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractGroupVO.setParentVO(this.getClass(), this);
        _contractGroupVOList.insertElementAt(vContractGroupVO, index);
    } //-- void addContractGroupVO(int, edit.common.vo.ContractGroupVO) 

    /**
     * Method addSegmentVO
     * 
     * @param vSegmentVO
     */
    public void addSegmentVO(edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.addElement(vSegmentVO);
    } //-- void addSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method addSegmentVO
     * 
     * @param index
     * @param vSegmentVO
     */
    public void addSegmentVO(int index, edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.insertElementAt(vSegmentVO, index);
    } //-- void addSegmentVO(int, edit.common.vo.SegmentVO) 

    /**
     * Method enumerateBillGroupVO
     */
    public java.util.Enumeration enumerateBillGroupVO()
    {
        return _billGroupVOList.elements();
    } //-- java.util.Enumeration enumerateBillGroupVO() 

    /**
     * Method enumerateContractGroupVO
     */
    public java.util.Enumeration enumerateContractGroupVO()
    {
        return _contractGroupVOList.elements();
    } //-- java.util.Enumeration enumerateContractGroupVO() 

    /**
     * Method enumerateSegmentVO
     */
    public java.util.Enumeration enumerateSegmentVO()
    {
        return _segmentVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentVO() 

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
        
        if (obj instanceof BillScheduleVO) {
        
            BillScheduleVO temp = (BillScheduleVO)obj;
            if (this._billSchedulePK != temp._billSchedulePK)
                return false;
            if (this._has_billSchedulePK != temp._has_billSchedulePK)
                return false;
            if (this._billingCompanyCT != null) {
                if (temp._billingCompanyCT == null) return false;
                else if (!(this._billingCompanyCT.equals(temp._billingCompanyCT))) 
                    return false;
            }
            else if (temp._billingCompanyCT != null)
                return false;
            if (this._firstBillDueDate != null) {
                if (temp._firstBillDueDate == null) return false;
                else if (!(this._firstBillDueDate.equals(temp._firstBillDueDate))) 
                    return false;
            }
            else if (temp._firstBillDueDate != null)
                return false;
            if (this._leadDaysOR != temp._leadDaysOR)
                return false;
            if (this._has_leadDaysOR != temp._has_leadDaysOR)
                return false;
            if (this._weekDayCT != null) {
                if (temp._weekDayCT == null) return false;
                else if (!(this._weekDayCT.equals(temp._weekDayCT))) 
                    return false;
            }
            else if (temp._weekDayCT != null)
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
            if (this._statusCT != null) {
                if (temp._statusCT == null) return false;
                else if (!(this._statusCT.equals(temp._statusCT))) 
                    return false;
            }
            else if (temp._statusCT != null)
                return false;
            if (this._sortOptionCT != null) {
                if (temp._sortOptionCT == null) return false;
                else if (!(this._sortOptionCT.equals(temp._sortOptionCT))) 
                    return false;
            }
            else if (temp._sortOptionCT != null)
                return false;
            if (this._billingConsolidationCT != null) {
                if (temp._billingConsolidationCT == null) return false;
                else if (!(this._billingConsolidationCT.equals(temp._billingConsolidationCT))) 
                    return false;
            }
            else if (temp._billingConsolidationCT != null)
                return false;
            if (this._socialSecurityMaskCT != null) {
                if (temp._socialSecurityMaskCT == null) return false;
                else if (!(this._socialSecurityMaskCT.equals(temp._socialSecurityMaskCT))) 
                    return false;
            }
            else if (temp._socialSecurityMaskCT != null)
                return false;
            if (this._numberOfCopiesGroup != temp._numberOfCopiesGroup)
                return false;
            if (this._has_numberOfCopiesGroup != temp._has_numberOfCopiesGroup)
                return false;
            if (this._numberOfCopiesAgent != temp._numberOfCopiesAgent)
                return false;
            if (this._has_numberOfCopiesAgent != temp._has_numberOfCopiesAgent)
                return false;
            if (this._nextBillExtractDate != null) {
                if (temp._nextBillExtractDate == null) return false;
                else if (!(this._nextBillExtractDate.equals(temp._nextBillExtractDate))) 
                    return false;
            }
            else if (temp._nextBillExtractDate != null)
                return false;
            if (this._nextBillDueDate != null) {
                if (temp._nextBillDueDate == null) return false;
                else if (!(this._nextBillDueDate.equals(temp._nextBillDueDate))) 
                    return false;
            }
            else if (temp._nextBillDueDate != null)
                return false;
            if (this._lastBillDueDate != null) {
                if (temp._lastBillDueDate == null) return false;
                else if (!(this._lastBillDueDate.equals(temp._lastBillDueDate))) 
                    return false;
            }
            else if (temp._lastBillDueDate != null)
                return false;
            if (this._billingModeCT != null) {
                if (temp._billingModeCT == null) return false;
                else if (!(this._billingModeCT.equals(temp._billingModeCT))) 
                    return false;
            }
            else if (temp._billingModeCT != null)
                return false;
            if (this._billTypeCT != null) {
                if (temp._billTypeCT == null) return false;
                else if (!(this._billTypeCT.equals(temp._billTypeCT))) 
                    return false;
            }
            else if (temp._billTypeCT != null)
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
            if (this._repName != null) {
                if (temp._repName == null) return false;
                else if (!(this._repName.equals(temp._repName))) 
                    return false;
            }
            else if (temp._repName != null)
                return false;
            if (this._repPhoneNumber != null) {
                if (temp._repPhoneNumber == null) return false;
                else if (!(this._repPhoneNumber.equals(temp._repPhoneNumber))) 
                    return false;
            }
            else if (temp._repPhoneNumber != null)
                return false;
            if (this._billMethodCT != null) {
                if (temp._billMethodCT == null) return false;
                else if (!(this._billMethodCT.equals(temp._billMethodCT))) 
                    return false;
            }
            else if (temp._billMethodCT != null)
                return false;
            if (this._skipMonthStart1CT != null) {
                if (temp._skipMonthStart1CT == null) return false;
                else if (!(this._skipMonthStart1CT.equals(temp._skipMonthStart1CT))) 
                    return false;
            }
            else if (temp._skipMonthStart1CT != null)
                return false;
            if (this._skipNumberOfMonths1CT != null) {
                if (temp._skipNumberOfMonths1CT == null) return false;
                else if (!(this._skipNumberOfMonths1CT.equals(temp._skipNumberOfMonths1CT))) 
                    return false;
            }
            else if (temp._skipNumberOfMonths1CT != null)
                return false;
            if (this._skipMonthStart2CT != null) {
                if (temp._skipMonthStart2CT == null) return false;
                else if (!(this._skipMonthStart2CT.equals(temp._skipMonthStart2CT))) 
                    return false;
            }
            else if (temp._skipMonthStart2CT != null)
                return false;
            if (this._skipNumberOfMonths2CT != null) {
                if (temp._skipNumberOfMonths2CT == null) return false;
                else if (!(this._skipNumberOfMonths2CT.equals(temp._skipNumberOfMonths2CT))) 
                    return false;
            }
            else if (temp._skipNumberOfMonths2CT != null)
                return false;
            if (this._skipMonthStart3CT != null) {
                if (temp._skipMonthStart3CT == null) return false;
                else if (!(this._skipMonthStart3CT.equals(temp._skipMonthStart3CT))) 
                    return false;
            }
            else if (temp._skipMonthStart3CT != null)
                return false;
            if (this._skipNumberOfMonths3CT != null) {
                if (temp._skipNumberOfMonths3CT == null) return false;
                else if (!(this._skipNumberOfMonths3CT.equals(temp._skipNumberOfMonths3CT))) 
                    return false;
            }
            else if (temp._skipNumberOfMonths3CT != null)
                return false;
            if (this._firstDeductionDate != null) {
                if (temp._firstDeductionDate == null) return false;
                else if (!(this._firstDeductionDate.equals(temp._firstDeductionDate))) 
                    return false;
            }
            else if (temp._firstDeductionDate != null)
                return false;
            if (this._lastDeductionDate != null) {
                if (temp._lastDeductionDate == null) return false;
                else if (!(this._lastDeductionDate.equals(temp._lastDeductionDate))) 
                    return false;
            }
            else if (temp._lastDeductionDate != null)
                return false;
            if (this._deductionFrequencyCT != null) {
                if (temp._deductionFrequencyCT == null) return false;
                else if (!(this._deductionFrequencyCT.equals(temp._deductionFrequencyCT))) 
                    return false;
            }
            else if (temp._deductionFrequencyCT != null)
                return false;
            if (this._requiredPremiumAmount != null) {
                if (temp._requiredPremiumAmount == null) return false;
                else if (!(this._requiredPremiumAmount.equals(temp._requiredPremiumAmount))) 
                    return false;
            }
            else if (temp._requiredPremiumAmount != null)
                return false;
            if (this._transitionPeriodEndDate != null) {
                if (temp._transitionPeriodEndDate == null) return false;
                else if (!(this._transitionPeriodEndDate.equals(temp._transitionPeriodEndDate))) 
                    return false;
            }
            else if (temp._transitionPeriodEndDate != null)
                return false;
            if (this._billChangeStartDate != null) {
                if (temp._billChangeStartDate == null) return false;
                else if (!(this._billChangeStartDate.equals(temp._billChangeStartDate))) 
                    return false;
            }
            else if (temp._billChangeStartDate != null)
                return false;
            if (this._varMonthDedChangeStartDate != null) {
                if (temp._varMonthDedChangeStartDate == null) return false;
                else if (!(this._varMonthDedChangeStartDate.equals(temp._varMonthDedChangeStartDate))) 
                    return false;
            }
            else if (temp._varMonthDedChangeStartDate != null)
                return false;
            if (this._changeEffectiveDate != null) {
                if (temp._changeEffectiveDate == null) return false;
                else if (!(this._changeEffectiveDate.equals(temp._changeEffectiveDate))) 
                    return false;
            }
            else if (temp._changeEffectiveDate != null)
                return false;
            if (this._lastPremiumChangeStartDate != null) {
                if (temp._lastPremiumChangeStartDate == null) return false;
                else if (!(this._lastPremiumChangeStartDate.equals(temp._lastPremiumChangeStartDate))) 
                    return false;
            }
            else if (temp._lastPremiumChangeStartDate != null)
                return false;
            if (this._EFTDraftDay != temp._EFTDraftDay)
                return false;
            if (this._has_EFTDraftDay != temp._has_EFTDraftDay)
                return false;
            if (this._EFTDraftDayStartMonthCT != null) {
                if (temp._EFTDraftDayStartMonthCT == null) return false;
                else if (!(this._EFTDraftDayStartMonthCT.equals(temp._EFTDraftDayStartMonthCT))) 
                    return false;
            }
            else if (temp._EFTDraftDayStartMonthCT != null)
                return false;
            if (this._billGroupVOList != null) {
                if (temp._billGroupVOList == null) return false;
                else if (!(this._billGroupVOList.equals(temp._billGroupVOList))) 
                    return false;
            }
            else if (temp._billGroupVOList != null)
                return false;
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            if (this._contractGroupVOList != null) {
                if (temp._contractGroupVOList == null) return false;
                else if (!(this._contractGroupVOList.equals(temp._contractGroupVOList))) 
                    return false;
            }
            else if (temp._contractGroupVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBillChangeStartDateReturns the value of field
     * 'billChangeStartDate'.
     * 
     * @return the value of field 'billChangeStartDate'.
     */
    public java.lang.String getBillChangeStartDate()
    {
        return this._billChangeStartDate;
    } //-- java.lang.String getBillChangeStartDate() 

    /**
     * Method getBillGroupVO
     * 
     * @param index
     */
    public edit.common.vo.BillGroupVO getBillGroupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _billGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BillGroupVO) _billGroupVOList.elementAt(index);
    } //-- edit.common.vo.BillGroupVO getBillGroupVO(int) 

    /**
     * Method getBillGroupVO
     */
    public edit.common.vo.BillGroupVO[] getBillGroupVO()
    {
        int size = _billGroupVOList.size();
        edit.common.vo.BillGroupVO[] mArray = new edit.common.vo.BillGroupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BillGroupVO) _billGroupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BillGroupVO[] getBillGroupVO() 

    /**
     * Method getBillGroupVOCount
     */
    public int getBillGroupVOCount()
    {
        return _billGroupVOList.size();
    } //-- int getBillGroupVOCount() 

    /**
     * Method getBillMethodCTReturns the value of field
     * 'billMethodCT'.
     * 
     * @return the value of field 'billMethodCT'.
     */
    public java.lang.String getBillMethodCT()
    {
        return this._billMethodCT;
    } //-- java.lang.String getBillMethodCT() 

    /**
     * Method getBillSchedulePKReturns the value of field
     * 'billSchedulePK'.
     * 
     * @return the value of field 'billSchedulePK'.
     */
    public long getBillSchedulePK()
    {
        return this._billSchedulePK;
    } //-- long getBillSchedulePK() 

    /**
     * Method getBillTypeCTReturns the value of field 'billTypeCT'.
     * 
     * @return the value of field 'billTypeCT'.
     */
    public java.lang.String getBillTypeCT()
    {
        return this._billTypeCT;
    } //-- java.lang.String getBillTypeCT() 

    /**
     * Method getBillingCompanyCTReturns the value of field
     * 'billingCompanyCT'.
     * 
     * @return the value of field 'billingCompanyCT'.
     */
    public java.lang.String getBillingCompanyCT()
    {
        return this._billingCompanyCT;
    } //-- java.lang.String getBillingCompanyCT() 

    /**
     * Method getBillingConsolidationCTReturns the value of field
     * 'billingConsolidationCT'.
     * 
     * @return the value of field 'billingConsolidationCT'.
     */
    public java.lang.String getBillingConsolidationCT()
    {
        return this._billingConsolidationCT;
    } //-- java.lang.String getBillingConsolidationCT() 

    /**
     * Method getBillingModeCTReturns the value of field
     * 'billingModeCT'.
     * 
     * @return the value of field 'billingModeCT'.
     */
    public java.lang.String getBillingModeCT()
    {
        return this._billingModeCT;
    } //-- java.lang.String getBillingModeCT() 

    /**
     * Method getChangeEffectiveDateReturns the value of field
     * 'changeEffectiveDate'.
     * 
     * @return the value of field 'changeEffectiveDate'.
     */
    public java.lang.String getChangeEffectiveDate()
    {
        return this._changeEffectiveDate;
    } //-- java.lang.String getChangeEffectiveDate() 

    /**
     * Method getContractGroupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractGroupVO getContractGroupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractGroupVO) _contractGroupVOList.elementAt(index);
    } //-- edit.common.vo.ContractGroupVO getContractGroupVO(int) 

    /**
     * Method getContractGroupVO
     */
    public edit.common.vo.ContractGroupVO[] getContractGroupVO()
    {
        int size = _contractGroupVOList.size();
        edit.common.vo.ContractGroupVO[] mArray = new edit.common.vo.ContractGroupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractGroupVO) _contractGroupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractGroupVO[] getContractGroupVO() 

    /**
     * Method getContractGroupVOCount
     */
    public int getContractGroupVOCount()
    {
        return _contractGroupVOList.size();
    } //-- int getContractGroupVOCount() 

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
     * Method getDeductionFrequencyCTReturns the value of field
     * 'deductionFrequencyCT'.
     * 
     * @return the value of field 'deductionFrequencyCT'.
     */
    public java.lang.String getDeductionFrequencyCT()
    {
        return this._deductionFrequencyCT;
    } //-- java.lang.String getDeductionFrequencyCT() 

    /**
     * Method getEFTDraftDayReturns the value of field
     * 'EFTDraftDay'.
     * 
     * @return the value of field 'EFTDraftDay'.
     */
    public int getEFTDraftDay()
    {
        return this._EFTDraftDay;
    } //-- int getEFTDraftDay() 

    /**
     * Method getEFTDraftDayStartMonthCTReturns the value of field
     * 'EFTDraftDayStartMonthCT'.
     * 
     * @return the value of field 'EFTDraftDayStartMonthCT'.
     */
    public java.lang.String getEFTDraftDayStartMonthCT()
    {
        return this._EFTDraftDayStartMonthCT;
    } //-- java.lang.String getEFTDraftDayStartMonthCT() 

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
     * Method getFirstBillDueDateReturns the value of field
     * 'firstBillDueDate'.
     * 
     * @return the value of field 'firstBillDueDate'.
     */
    public java.lang.String getFirstBillDueDate()
    {
        return this._firstBillDueDate;
    } //-- java.lang.String getFirstBillDueDate() 

    /**
     * Method getFirstDeductionDateReturns the value of field
     * 'firstDeductionDate'.
     * 
     * @return the value of field 'firstDeductionDate'.
     */
    public java.lang.String getFirstDeductionDate()
    {
        return this._firstDeductionDate;
    } //-- java.lang.String getFirstDeductionDate() 

    /**
     * Method getLastBillDueDateReturns the value of field
     * 'lastBillDueDate'.
     * 
     * @return the value of field 'lastBillDueDate'.
     */
    public java.lang.String getLastBillDueDate()
    {
        return this._lastBillDueDate;
    } //-- java.lang.String getLastBillDueDate() 

    /**
     * Method getLastDeductionDateReturns the value of field
     * 'lastDeductionDate'.
     * 
     * @return the value of field 'lastDeductionDate'.
     */
    public java.lang.String getLastDeductionDate()
    {
        return this._lastDeductionDate;
    } //-- java.lang.String getLastDeductionDate() 

    /**
     * Method getLastPremiumChangeStartDateReturns the value of
     * field 'lastPremiumChangeStartDate'.
     * 
     * @return the value of field 'lastPremiumChangeStartDate'.
     */
    public java.lang.String getLastPremiumChangeStartDate()
    {
        return this._lastPremiumChangeStartDate;
    } //-- java.lang.String getLastPremiumChangeStartDate() 

    /**
     * Method getLeadDaysORReturns the value of field 'leadDaysOR'.
     * 
     * @return the value of field 'leadDaysOR'.
     */
    public int getLeadDaysOR()
    {
        return this._leadDaysOR;
    } //-- int getLeadDaysOR() 

    /**
     * Method getNextBillDueDateReturns the value of field
     * 'nextBillDueDate'.
     * 
     * @return the value of field 'nextBillDueDate'.
     */
    public java.lang.String getNextBillDueDate()
    {
        return this._nextBillDueDate;
    } //-- java.lang.String getNextBillDueDate() 

    /**
     * Method getNextBillExtractDateReturns the value of field
     * 'nextBillExtractDate'.
     * 
     * @return the value of field 'nextBillExtractDate'.
     */
    public java.lang.String getNextBillExtractDate()
    {
        return this._nextBillExtractDate;
    } //-- java.lang.String getNextBillExtractDate() 

    /**
     * Method getNumberOfCopiesAgentReturns the value of field
     * 'numberOfCopiesAgent'.
     * 
     * @return the value of field 'numberOfCopiesAgent'.
     */
    public int getNumberOfCopiesAgent()
    {
        return this._numberOfCopiesAgent;
    } //-- int getNumberOfCopiesAgent() 

    /**
     * Method getNumberOfCopiesGroupReturns the value of field
     * 'numberOfCopiesGroup'.
     * 
     * @return the value of field 'numberOfCopiesGroup'.
     */
    public int getNumberOfCopiesGroup()
    {
        return this._numberOfCopiesGroup;
    } //-- int getNumberOfCopiesGroup() 

    /**
     * Method getRepNameReturns the value of field 'repName'.
     * 
     * @return the value of field 'repName'.
     */
    public java.lang.String getRepName()
    {
        return this._repName;
    } //-- java.lang.String getRepName() 

    /**
     * Method getRepPhoneNumberReturns the value of field
     * 'repPhoneNumber'.
     * 
     * @return the value of field 'repPhoneNumber'.
     */
    public java.lang.String getRepPhoneNumber()
    {
        return this._repPhoneNumber;
    } //-- java.lang.String getRepPhoneNumber() 

    /**
     * Method getRequiredPremiumAmountReturns the value of field
     * 'requiredPremiumAmount'.
     * 
     * @return the value of field 'requiredPremiumAmount'.
     */
    public java.math.BigDecimal getRequiredPremiumAmount()
    {
        return this._requiredPremiumAmount;
    } //-- java.math.BigDecimal getRequiredPremiumAmount() 

    /**
     * Method getSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentVO getSegmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SegmentVO) _segmentVOList.elementAt(index);
    } //-- edit.common.vo.SegmentVO getSegmentVO(int) 

    /**
     * Method getSegmentVO
     */
    public edit.common.vo.SegmentVO[] getSegmentVO()
    {
        int size = _segmentVOList.size();
        edit.common.vo.SegmentVO[] mArray = new edit.common.vo.SegmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SegmentVO) _segmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SegmentVO[] getSegmentVO() 

    /**
     * Method getSegmentVOCount
     */
    public int getSegmentVOCount()
    {
        return _segmentVOList.size();
    } //-- int getSegmentVOCount() 

    /**
     * Method getSkipMonthStart1CTReturns the value of field
     * 'skipMonthStart1CT'.
     * 
     * @return the value of field 'skipMonthStart1CT'.
     */
    public java.lang.String getSkipMonthStart1CT()
    {
        return this._skipMonthStart1CT;
    } //-- java.lang.String getSkipMonthStart1CT() 

    /**
     * Method getSkipMonthStart2CTReturns the value of field
     * 'skipMonthStart2CT'.
     * 
     * @return the value of field 'skipMonthStart2CT'.
     */
    public java.lang.String getSkipMonthStart2CT()
    {
        return this._skipMonthStart2CT;
    } //-- java.lang.String getSkipMonthStart2CT() 

    /**
     * Method getSkipMonthStart3CTReturns the value of field
     * 'skipMonthStart3CT'.
     * 
     * @return the value of field 'skipMonthStart3CT'.
     */
    public java.lang.String getSkipMonthStart3CT()
    {
        return this._skipMonthStart3CT;
    } //-- java.lang.String getSkipMonthStart3CT() 

    /**
     * Method getSkipNumberOfMonths1CTReturns the value of field
     * 'skipNumberOfMonths1CT'.
     * 
     * @return the value of field 'skipNumberOfMonths1CT'.
     */
    public java.lang.String getSkipNumberOfMonths1CT()
    {
        return this._skipNumberOfMonths1CT;
    } //-- java.lang.String getSkipNumberOfMonths1CT() 

    /**
     * Method getSkipNumberOfMonths2CTReturns the value of field
     * 'skipNumberOfMonths2CT'.
     * 
     * @return the value of field 'skipNumberOfMonths2CT'.
     */
    public java.lang.String getSkipNumberOfMonths2CT()
    {
        return this._skipNumberOfMonths2CT;
    } //-- java.lang.String getSkipNumberOfMonths2CT() 

    /**
     * Method getSkipNumberOfMonths3CTReturns the value of field
     * 'skipNumberOfMonths3CT'.
     * 
     * @return the value of field 'skipNumberOfMonths3CT'.
     */
    public java.lang.String getSkipNumberOfMonths3CT()
    {
        return this._skipNumberOfMonths3CT;
    } //-- java.lang.String getSkipNumberOfMonths3CT() 

    /**
     * Method getSocialSecurityMaskCTReturns the value of field
     * 'socialSecurityMaskCT'.
     * 
     * @return the value of field 'socialSecurityMaskCT'.
     */
    public java.lang.String getSocialSecurityMaskCT()
    {
        return this._socialSecurityMaskCT;
    } //-- java.lang.String getSocialSecurityMaskCT() 

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
     * Method getStatusCTReturns the value of field 'statusCT'.
     * 
     * @return the value of field 'statusCT'.
     */
    public java.lang.String getStatusCT()
    {
        return this._statusCT;
    } //-- java.lang.String getStatusCT() 

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
     * Method getTransitionPeriodEndDateReturns the value of field
     * 'transitionPeriodEndDate'.
     * 
     * @return the value of field 'transitionPeriodEndDate'.
     */
    public java.lang.String getTransitionPeriodEndDate()
    {
        return this._transitionPeriodEndDate;
    } //-- java.lang.String getTransitionPeriodEndDate() 

    /**
     * Method getVarMonthDedChangeStartDateReturns the value of
     * field 'varMonthDedChangeStartDate'.
     * 
     * @return the value of field 'varMonthDedChangeStartDate'.
     */
    public java.lang.String getVarMonthDedChangeStartDate()
    {
        return this._varMonthDedChangeStartDate;
    } //-- java.lang.String getVarMonthDedChangeStartDate() 

    /**
     * Method getWeekDayCTReturns the value of field 'weekDayCT'.
     * 
     * @return the value of field 'weekDayCT'.
     */
    public java.lang.String getWeekDayCT()
    {
        return this._weekDayCT;
    } //-- java.lang.String getWeekDayCT() 

    /**
     * Method hasBillSchedulePK
     */
    public boolean hasBillSchedulePK()
    {
        return this._has_billSchedulePK;
    } //-- boolean hasBillSchedulePK() 

    /**
     * Method hasEFTDraftDay
     */
    public boolean hasEFTDraftDay()
    {
        return this._has_EFTDraftDay;
    } //-- boolean hasEFTDraftDay() 

    /**
     * Method hasLeadDaysOR
     */
    public boolean hasLeadDaysOR()
    {
        return this._has_leadDaysOR;
    } //-- boolean hasLeadDaysOR() 

    /**
     * Method hasNumberOfCopiesAgent
     */
    public boolean hasNumberOfCopiesAgent()
    {
        return this._has_numberOfCopiesAgent;
    } //-- boolean hasNumberOfCopiesAgent() 

    /**
     * Method hasNumberOfCopiesGroup
     */
    public boolean hasNumberOfCopiesGroup()
    {
        return this._has_numberOfCopiesGroup;
    } //-- boolean hasNumberOfCopiesGroup() 

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
     * Method removeAllBillGroupVO
     */
    public void removeAllBillGroupVO()
    {
        _billGroupVOList.removeAllElements();
    } //-- void removeAllBillGroupVO() 

    /**
     * Method removeAllContractGroupVO
     */
    public void removeAllContractGroupVO()
    {
        _contractGroupVOList.removeAllElements();
    } //-- void removeAllContractGroupVO() 

    /**
     * Method removeAllSegmentVO
     */
    public void removeAllSegmentVO()
    {
        _segmentVOList.removeAllElements();
    } //-- void removeAllSegmentVO() 

    /**
     * Method removeBillGroupVO
     * 
     * @param index
     */
    public edit.common.vo.BillGroupVO removeBillGroupVO(int index)
    {
        java.lang.Object obj = _billGroupVOList.elementAt(index);
        _billGroupVOList.removeElementAt(index);
        return (edit.common.vo.BillGroupVO) obj;
    } //-- edit.common.vo.BillGroupVO removeBillGroupVO(int) 

    /**
     * Method removeContractGroupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractGroupVO removeContractGroupVO(int index)
    {
        java.lang.Object obj = _contractGroupVOList.elementAt(index);
        _contractGroupVOList.removeElementAt(index);
        return (edit.common.vo.ContractGroupVO) obj;
    } //-- edit.common.vo.ContractGroupVO removeContractGroupVO(int) 

    /**
     * Method removeSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentVO removeSegmentVO(int index)
    {
        java.lang.Object obj = _segmentVOList.elementAt(index);
        _segmentVOList.removeElementAt(index);
        return (edit.common.vo.SegmentVO) obj;
    } //-- edit.common.vo.SegmentVO removeSegmentVO(int) 

    /**
     * Method setBillChangeStartDateSets the value of field
     * 'billChangeStartDate'.
     * 
     * @param billChangeStartDate the value of field
     * 'billChangeStartDate'.
     */
    public void setBillChangeStartDate(java.lang.String billChangeStartDate)
    {
        this._billChangeStartDate = billChangeStartDate;
        
        super.setVoChanged(true);
    } //-- void setBillChangeStartDate(java.lang.String) 

    /**
     * Method setBillGroupVO
     * 
     * @param index
     * @param vBillGroupVO
     */
    public void setBillGroupVO(int index, edit.common.vo.BillGroupVO vBillGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _billGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBillGroupVO.setParentVO(this.getClass(), this);
        _billGroupVOList.setElementAt(vBillGroupVO, index);
    } //-- void setBillGroupVO(int, edit.common.vo.BillGroupVO) 

    /**
     * Method setBillGroupVO
     * 
     * @param billGroupVOArray
     */
    public void setBillGroupVO(edit.common.vo.BillGroupVO[] billGroupVOArray)
    {
        //-- copy array
        _billGroupVOList.removeAllElements();
        for (int i = 0; i < billGroupVOArray.length; i++) {
            billGroupVOArray[i].setParentVO(this.getClass(), this);
            _billGroupVOList.addElement(billGroupVOArray[i]);
        }
    } //-- void setBillGroupVO(edit.common.vo.BillGroupVO) 

    /**
     * Method setBillMethodCTSets the value of field
     * 'billMethodCT'.
     * 
     * @param billMethodCT the value of field 'billMethodCT'.
     */
    public void setBillMethodCT(java.lang.String billMethodCT)
    {
        this._billMethodCT = billMethodCT;
        
        super.setVoChanged(true);
    } //-- void setBillMethodCT(java.lang.String) 

    /**
     * Method setBillSchedulePKSets the value of field
     * 'billSchedulePK'.
     * 
     * @param billSchedulePK the value of field 'billSchedulePK'.
     */
    public void setBillSchedulePK(long billSchedulePK)
    {
        this._billSchedulePK = billSchedulePK;
        
        super.setVoChanged(true);
        this._has_billSchedulePK = true;
    } //-- void setBillSchedulePK(long) 

    /**
     * Method setBillTypeCTSets the value of field 'billTypeCT'.
     * 
     * @param billTypeCT the value of field 'billTypeCT'.
     */
    public void setBillTypeCT(java.lang.String billTypeCT)
    {
        this._billTypeCT = billTypeCT;
        
        super.setVoChanged(true);
    } //-- void setBillTypeCT(java.lang.String) 

    /**
     * Method setBillingCompanyCTSets the value of field
     * 'billingCompanyCT'.
     * 
     * @param billingCompanyCT the value of field 'billingCompanyCT'
     */
    public void setBillingCompanyCT(java.lang.String billingCompanyCT)
    {
        this._billingCompanyCT = billingCompanyCT;
        
        super.setVoChanged(true);
    } //-- void setBillingCompanyCT(java.lang.String) 

    /**
     * Method setBillingConsolidationCTSets the value of field
     * 'billingConsolidationCT'.
     * 
     * @param billingConsolidationCT the value of field
     * 'billingConsolidationCT'.
     */
    public void setBillingConsolidationCT(java.lang.String billingConsolidationCT)
    {
        this._billingConsolidationCT = billingConsolidationCT;
        
        super.setVoChanged(true);
    } //-- void setBillingConsolidationCT(java.lang.String) 

    /**
     * Method setBillingModeCTSets the value of field
     * 'billingModeCT'.
     * 
     * @param billingModeCT the value of field 'billingModeCT'.
     */
    public void setBillingModeCT(java.lang.String billingModeCT)
    {
        this._billingModeCT = billingModeCT;
        
        super.setVoChanged(true);
    } //-- void setBillingModeCT(java.lang.String) 

    /**
     * Method setChangeEffectiveDateSets the value of field
     * 'changeEffectiveDate'.
     * 
     * @param changeEffectiveDate the value of field
     * 'changeEffectiveDate'.
     */
    public void setChangeEffectiveDate(java.lang.String changeEffectiveDate)
    {
        this._changeEffectiveDate = changeEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setChangeEffectiveDate(java.lang.String) 

    /**
     * Method setContractGroupVO
     * 
     * @param index
     * @param vContractGroupVO
     */
    public void setContractGroupVO(int index, edit.common.vo.ContractGroupVO vContractGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractGroupVO.setParentVO(this.getClass(), this);
        _contractGroupVOList.setElementAt(vContractGroupVO, index);
    } //-- void setContractGroupVO(int, edit.common.vo.ContractGroupVO) 

    /**
     * Method setContractGroupVO
     * 
     * @param contractGroupVOArray
     */
    public void setContractGroupVO(edit.common.vo.ContractGroupVO[] contractGroupVOArray)
    {
        //-- copy array
        _contractGroupVOList.removeAllElements();
        for (int i = 0; i < contractGroupVOArray.length; i++) {
            contractGroupVOArray[i].setParentVO(this.getClass(), this);
            _contractGroupVOList.addElement(contractGroupVOArray[i]);
        }
    } //-- void setContractGroupVO(edit.common.vo.ContractGroupVO) 

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
     * Method setDeductionFrequencyCTSets the value of field
     * 'deductionFrequencyCT'.
     * 
     * @param deductionFrequencyCT the value of field
     * 'deductionFrequencyCT'.
     */
    public void setDeductionFrequencyCT(java.lang.String deductionFrequencyCT)
    {
        this._deductionFrequencyCT = deductionFrequencyCT;
        
        super.setVoChanged(true);
    } //-- void setDeductionFrequencyCT(java.lang.String) 

    /**
     * Method setEFTDraftDaySets the value of field 'EFTDraftDay'.
     * 
     * @param EFTDraftDay the value of field 'EFTDraftDay'.
     */
    public void setEFTDraftDay(int EFTDraftDay)
    {
        this._EFTDraftDay = EFTDraftDay;
        
        super.setVoChanged(true);
        this._has_EFTDraftDay = true;
    } //-- void setEFTDraftDay(int) 

    /**
     * Method setEFTDraftDayStartMonthCTSets the value of field
     * 'EFTDraftDayStartMonthCT'.
     * 
     * @param EFTDraftDayStartMonthCT the value of field
     * 'EFTDraftDayStartMonthCT'.
     */
    public void setEFTDraftDayStartMonthCT(java.lang.String EFTDraftDayStartMonthCT)
    {
        this._EFTDraftDayStartMonthCT = EFTDraftDayStartMonthCT;
        
        super.setVoChanged(true);
    } //-- void setEFTDraftDayStartMonthCT(java.lang.String) 

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
     * Method setFirstBillDueDateSets the value of field
     * 'firstBillDueDate'.
     * 
     * @param firstBillDueDate the value of field 'firstBillDueDate'
     */
    public void setFirstBillDueDate(java.lang.String firstBillDueDate)
    {
        this._firstBillDueDate = firstBillDueDate;
        
        super.setVoChanged(true);
    } //-- void setFirstBillDueDate(java.lang.String) 

    /**
     * Method setFirstDeductionDateSets the value of field
     * 'firstDeductionDate'.
     * 
     * @param firstDeductionDate the value of field
     * 'firstDeductionDate'.
     */
    public void setFirstDeductionDate(java.lang.String firstDeductionDate)
    {
        this._firstDeductionDate = firstDeductionDate;
        
        super.setVoChanged(true);
    } //-- void setFirstDeductionDate(java.lang.String) 

    /**
     * Method setLastBillDueDateSets the value of field
     * 'lastBillDueDate'.
     * 
     * @param lastBillDueDate the value of field 'lastBillDueDate'.
     */
    public void setLastBillDueDate(java.lang.String lastBillDueDate)
    {
        this._lastBillDueDate = lastBillDueDate;
        
        super.setVoChanged(true);
    } //-- void setLastBillDueDate(java.lang.String) 

    /**
     * Method setLastDeductionDateSets the value of field
     * 'lastDeductionDate'.
     * 
     * @param lastDeductionDate the value of field
     * 'lastDeductionDate'.
     */
    public void setLastDeductionDate(java.lang.String lastDeductionDate)
    {
        this._lastDeductionDate = lastDeductionDate;
        
        super.setVoChanged(true);
    } //-- void setLastDeductionDate(java.lang.String) 

    /**
     * Method setLastPremiumChangeStartDateSets the value of field
     * 'lastPremiumChangeStartDate'.
     * 
     * @param lastPremiumChangeStartDate the value of field
     * 'lastPremiumChangeStartDate'.
     */
    public void setLastPremiumChangeStartDate(java.lang.String lastPremiumChangeStartDate)
    {
        this._lastPremiumChangeStartDate = lastPremiumChangeStartDate;
        
        super.setVoChanged(true);
    } //-- void setLastPremiumChangeStartDate(java.lang.String) 

    /**
     * Method setLeadDaysORSets the value of field 'leadDaysOR'.
     * 
     * @param leadDaysOR the value of field 'leadDaysOR'.
     */
    public void setLeadDaysOR(int leadDaysOR)
    {
        this._leadDaysOR = leadDaysOR;
        
        super.setVoChanged(true);
        this._has_leadDaysOR = true;
    } //-- void setLeadDaysOR(int) 

    /**
     * Method setNextBillDueDateSets the value of field
     * 'nextBillDueDate'.
     * 
     * @param nextBillDueDate the value of field 'nextBillDueDate'.
     */
    public void setNextBillDueDate(java.lang.String nextBillDueDate)
    {
        this._nextBillDueDate = nextBillDueDate;
        
        super.setVoChanged(true);
    } //-- void setNextBillDueDate(java.lang.String) 

    /**
     * Method setNextBillExtractDateSets the value of field
     * 'nextBillExtractDate'.
     * 
     * @param nextBillExtractDate the value of field
     * 'nextBillExtractDate'.
     */
    public void setNextBillExtractDate(java.lang.String nextBillExtractDate)
    {
        this._nextBillExtractDate = nextBillExtractDate;
        
        super.setVoChanged(true);
    } //-- void setNextBillExtractDate(java.lang.String) 

    /**
     * Method setNumberOfCopiesAgentSets the value of field
     * 'numberOfCopiesAgent'.
     * 
     * @param numberOfCopiesAgent the value of field
     * 'numberOfCopiesAgent'.
     */
    public void setNumberOfCopiesAgent(int numberOfCopiesAgent)
    {
        this._numberOfCopiesAgent = numberOfCopiesAgent;
        
        super.setVoChanged(true);
        this._has_numberOfCopiesAgent = true;
    } //-- void setNumberOfCopiesAgent(int) 

    /**
     * Method setNumberOfCopiesGroupSets the value of field
     * 'numberOfCopiesGroup'.
     * 
     * @param numberOfCopiesGroup the value of field
     * 'numberOfCopiesGroup'.
     */
    public void setNumberOfCopiesGroup(int numberOfCopiesGroup)
    {
        this._numberOfCopiesGroup = numberOfCopiesGroup;
        
        super.setVoChanged(true);
        this._has_numberOfCopiesGroup = true;
    } //-- void setNumberOfCopiesGroup(int) 

    /**
     * Method setRepNameSets the value of field 'repName'.
     * 
     * @param repName the value of field 'repName'.
     */
    public void setRepName(java.lang.String repName)
    {
        this._repName = repName;
        
        super.setVoChanged(true);
    } //-- void setRepName(java.lang.String) 

    /**
     * Method setRepPhoneNumberSets the value of field
     * 'repPhoneNumber'.
     * 
     * @param repPhoneNumber the value of field 'repPhoneNumber'.
     */
    public void setRepPhoneNumber(java.lang.String repPhoneNumber)
    {
        this._repPhoneNumber = repPhoneNumber;
        
        super.setVoChanged(true);
    } //-- void setRepPhoneNumber(java.lang.String) 

    /**
     * Method setRequiredPremiumAmountSets the value of field
     * 'requiredPremiumAmount'.
     * 
     * @param requiredPremiumAmount the value of field
     * 'requiredPremiumAmount'.
     */
    public void setRequiredPremiumAmount(java.math.BigDecimal requiredPremiumAmount)
    {
        this._requiredPremiumAmount = requiredPremiumAmount;
        
        super.setVoChanged(true);
    } //-- void setRequiredPremiumAmount(java.math.BigDecimal) 

    /**
     * Method setSegmentVO
     * 
     * @param index
     * @param vSegmentVO
     */
    public void setSegmentVO(int index, edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.setElementAt(vSegmentVO, index);
    } //-- void setSegmentVO(int, edit.common.vo.SegmentVO) 

    /**
     * Method setSegmentVO
     * 
     * @param segmentVOArray
     */
    public void setSegmentVO(edit.common.vo.SegmentVO[] segmentVOArray)
    {
        //-- copy array
        _segmentVOList.removeAllElements();
        for (int i = 0; i < segmentVOArray.length; i++) {
            segmentVOArray[i].setParentVO(this.getClass(), this);
            _segmentVOList.addElement(segmentVOArray[i]);
        }
    } //-- void setSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method setSkipMonthStart1CTSets the value of field
     * 'skipMonthStart1CT'.
     * 
     * @param skipMonthStart1CT the value of field
     * 'skipMonthStart1CT'.
     */
    public void setSkipMonthStart1CT(java.lang.String skipMonthStart1CT)
    {
        this._skipMonthStart1CT = skipMonthStart1CT;
        
        super.setVoChanged(true);
    } //-- void setSkipMonthStart1CT(java.lang.String) 

    /**
     * Method setSkipMonthStart2CTSets the value of field
     * 'skipMonthStart2CT'.
     * 
     * @param skipMonthStart2CT the value of field
     * 'skipMonthStart2CT'.
     */
    public void setSkipMonthStart2CT(java.lang.String skipMonthStart2CT)
    {
        this._skipMonthStart2CT = skipMonthStart2CT;
        
        super.setVoChanged(true);
    } //-- void setSkipMonthStart2CT(java.lang.String) 

    /**
     * Method setSkipMonthStart3CTSets the value of field
     * 'skipMonthStart3CT'.
     * 
     * @param skipMonthStart3CT the value of field
     * 'skipMonthStart3CT'.
     */
    public void setSkipMonthStart3CT(java.lang.String skipMonthStart3CT)
    {
        this._skipMonthStart3CT = skipMonthStart3CT;
        
        super.setVoChanged(true);
    } //-- void setSkipMonthStart3CT(java.lang.String) 

    /**
     * Method setSkipNumberOfMonths1CTSets the value of field
     * 'skipNumberOfMonths1CT'.
     * 
     * @param skipNumberOfMonths1CT the value of field
     * 'skipNumberOfMonths1CT'.
     */
    public void setSkipNumberOfMonths1CT(java.lang.String skipNumberOfMonths1CT)
    {
        this._skipNumberOfMonths1CT = skipNumberOfMonths1CT;
        
        super.setVoChanged(true);
    } //-- void setSkipNumberOfMonths1CT(java.lang.String) 

    /**
     * Method setSkipNumberOfMonths2CTSets the value of field
     * 'skipNumberOfMonths2CT'.
     * 
     * @param skipNumberOfMonths2CT the value of field
     * 'skipNumberOfMonths2CT'.
     */
    public void setSkipNumberOfMonths2CT(java.lang.String skipNumberOfMonths2CT)
    {
        this._skipNumberOfMonths2CT = skipNumberOfMonths2CT;
        
        super.setVoChanged(true);
    } //-- void setSkipNumberOfMonths2CT(java.lang.String) 

    /**
     * Method setSkipNumberOfMonths3CTSets the value of field
     * 'skipNumberOfMonths3CT'.
     * 
     * @param skipNumberOfMonths3CT the value of field
     * 'skipNumberOfMonths3CT'.
     */
    public void setSkipNumberOfMonths3CT(java.lang.String skipNumberOfMonths3CT)
    {
        this._skipNumberOfMonths3CT = skipNumberOfMonths3CT;
        
        super.setVoChanged(true);
    } //-- void setSkipNumberOfMonths3CT(java.lang.String) 

    /**
     * Method setSocialSecurityMaskCTSets the value of field
     * 'socialSecurityMaskCT'.
     * 
     * @param socialSecurityMaskCT the value of field
     * 'socialSecurityMaskCT'.
     */
    public void setSocialSecurityMaskCT(java.lang.String socialSecurityMaskCT)
    {
        this._socialSecurityMaskCT = socialSecurityMaskCT;
        
        super.setVoChanged(true);
    } //-- void setSocialSecurityMaskCT(java.lang.String) 

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
     * Method setTransitionPeriodEndDateSets the value of field
     * 'transitionPeriodEndDate'.
     * 
     * @param transitionPeriodEndDate the value of field
     * 'transitionPeriodEndDate'.
     */
    public void setTransitionPeriodEndDate(java.lang.String transitionPeriodEndDate)
    {
        this._transitionPeriodEndDate = transitionPeriodEndDate;
        
        super.setVoChanged(true);
    } //-- void setTransitionPeriodEndDate(java.lang.String) 

    /**
     * Method setVarMonthDedChangeStartDateSets the value of field
     * 'varMonthDedChangeStartDate'.
     * 
     * @param varMonthDedChangeStartDate the value of field
     * 'varMonthDedChangeStartDate'.
     */
    public void setVarMonthDedChangeStartDate(java.lang.String varMonthDedChangeStartDate)
    {
        this._varMonthDedChangeStartDate = varMonthDedChangeStartDate;
        
        super.setVoChanged(true);
    } //-- void setVarMonthDedChangeStartDate(java.lang.String) 

    /**
     * Method setWeekDayCTSets the value of field 'weekDayCT'.
     * 
     * @param weekDayCT the value of field 'weekDayCT'.
     */
    public void setWeekDayCT(java.lang.String weekDayCT)
    {
        this._weekDayCT = weekDayCT;
        
        super.setVoChanged(true);
    } //-- void setWeekDayCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BillScheduleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BillScheduleVO) Unmarshaller.unmarshal(edit.common.vo.BillScheduleVO.class, reader);
    } //-- edit.common.vo.BillScheduleVO unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

    /**
     * Used to determine if updates to a BillSchedule record require the writing of a new record
     * *Note the method of same-name in BillSchedule.java
     */
    public boolean requiresNewBillScheduleRecord(BillScheduleVO existingBillSchedule, String contractNumber)
    {    	
    	String existingBillingMode = existingBillSchedule.getBillingModeCT();
    	String existingBillMethod = existingBillSchedule.getBillMethodCT();
    	String existingDeductionFreq = existingBillSchedule.getDeductionFrequencyCT();
    	String existingSkipNumber1 = existingBillSchedule.getSkipNumberOfMonths1CT();
    	String existingSkipNumber2 = existingBillSchedule.getSkipNumberOfMonths2CT();
    	String existingSkipNumber3 = existingBillSchedule.getSkipNumberOfMonths3CT();
    	EDITDate existingBillChangeStartDate = new EDITDate(existingBillSchedule.getBillChangeStartDate());

		Segment[] segments = Segment.findBy_BillScheduleFK(this._billSchedulePK);

		if (segments != null && segments.length > 0)
		{
			for(Segment segment : segments)
			{
				if (!segment.getContractNumber().equalsIgnoreCase(contractNumber))
				{
					return false;
				}
			}
		}
		
    	// Check for billingModeCT changes
    	if (this._billingModeCT != null)
    	{
    		if (existingBillingMode != null && !this._billingModeCT.equalsIgnoreCase(existingBillingMode))
    		{
    			return true;
    		}
    		else if (existingBillingMode == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingBillingMode != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for billMethodCT changes
    	if (this._billMethodCT != null)
    	{
    		if (existingBillMethod != null && !this._billMethodCT.equalsIgnoreCase(existingBillMethod))
    		{
    			return true;
    		}
    		else if (existingBillMethod == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingBillMethod != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for deductionFrequencyCT changes
    	if (this._deductionFrequencyCT != null)
    	{
    		if (existingDeductionFreq != null && !this._deductionFrequencyCT.equalsIgnoreCase(existingDeductionFreq))
    		{
    			return true;
    		}
    		else if (existingDeductionFreq == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingDeductionFreq != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for skipNumberOfMonths1CT changes
    	if (this._skipNumberOfMonths1CT != null)
    	{
    		if (existingSkipNumber1 != null && !this._skipNumberOfMonths1CT.equalsIgnoreCase(existingSkipNumber1))
    		{
    			return true;
    		}
    		else if (existingSkipNumber1 == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingSkipNumber1 != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for skipNumberOfMonths2CT changes
    	if (this._skipNumberOfMonths2CT != null)
    	{
    		if (existingSkipNumber2 != null && !this._skipNumberOfMonths2CT.equalsIgnoreCase(existingSkipNumber2))
    		{
    			return true;
    		}
    		else if (existingSkipNumber2 == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingSkipNumber2 != null)
    		{
    			return true;
    		}
    	}

    	// Check for skipNumberOfMonths3CT changes
    	if (this._skipNumberOfMonths3CT != null)
    	{
    		if (existingSkipNumber3 != null && !this._skipNumberOfMonths3CT.equalsIgnoreCase(existingSkipNumber3))
    		{
    			return true;
    		}
    		else if (existingSkipNumber3 == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingSkipNumber3 != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for billChangeStartDate changes
    	if (this._billChangeStartDate != null)
    	{
    		if (existingBillChangeStartDate != null && !this._billChangeStartDate.equals(existingBillChangeStartDate))
    		{
    			return true;
    		}
    		else if (existingBillChangeStartDate == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingBillChangeStartDate != null)
    		{
    			return true;
    		}
    	}

    	return false;
    }
     
    public EDITDate getMinNextBillDueDate()
    {
        int leadDays = 0;

        ProductStructure productStructure = null;

        if (this.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL))
        {
            productStructure = ProductStructure.findBy_CompanyName("Case")[0];
        }
        else
        {
            Segment[] segment = Segment.findBy_BillScheduleFK(this.getBillSchedulePK());
            if (segment.length > 0)
            {
                productStructure = segment[0].getProductStructure();
            }
        }

        if (productStructure != null)
        {
            AreaValue[] areaValues = null;
            if (this.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_EFT))
            {
                areaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(productStructure.getPK(),BillSchedule.AREA_GROUPING_BILLING, BillSchedule.AREA_FIELD_EFTLEADDAYS);
            }
            else
            {
                areaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(productStructure.getPK(),BillSchedule.AREA_GROUPING_BILLING, BillSchedule.AREA_FIELD_LEADDAYS);
            }

            if (areaValues != null)
            {
                leadDays = Integer.parseInt(areaValues[0].getAreaValue());
            }
        }
        
        return (new EDITDate()).addDays(leadDays);
    }

}
