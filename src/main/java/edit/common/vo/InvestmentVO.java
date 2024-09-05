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

/**
 * Class InvestmentVO.
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _investmentPK
     */
    private long _investmentPK;

    /**
     * keeps track of state for field: _investmentPK
     */
    private boolean _has_investmentPK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _excessInterestCalculationDate
     */
    private java.lang.String _excessInterestCalculationDate;

    /**
     * Field _excessInterestPaymentDate
     */
    private java.lang.String _excessInterestPaymentDate;

    /**
     * Field _excessInterest
     */
    private java.math.BigDecimal _excessInterest;

    /**
     * Field _excessInterestMethod
     */
    private java.lang.String _excessInterestMethod;

    /**
     * Field _excessInterestStartDate
     */
    private java.lang.String _excessInterestStartDate;

    /**
     * Field _assumedInvestmentReturn
     */
    private java.math.BigDecimal _assumedInvestmentReturn;

    /**
     * Field _chargeCodeFK
     */
    private long _chargeCodeFK;

    /**
     * keeps track of state for field: _chargeCodeFK
     */
    private boolean _has_chargeCodeFK;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _bucketVOList
     */
    private java.util.Vector _bucketVOList;

    /**
     * Field _commissionInvestmentHistoryVOList
     */
    private java.util.Vector _commissionInvestmentHistoryVOList;

    /**
     * Field _investmentAllocationVOList
     */
    private java.util.Vector _investmentAllocationVOList;

    /**
     * Field _investmentHistoryVOList
     */
    private java.util.Vector _investmentHistoryVOList;

    /**
     * Field _investmentAllocationOverrideVOList
     */
    private java.util.Vector _investmentAllocationOverrideVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentVO() {
        super();
        _bucketVOList = new Vector();
        _commissionInvestmentHistoryVOList = new Vector();
        _investmentAllocationVOList = new Vector();
        _investmentHistoryVOList = new Vector();
        _investmentAllocationOverrideVOList = new Vector();
    } //-- edit.common.vo.InvestmentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBucketVO
     * 
     * @param vBucketVO
     */
    public void addBucketVO(edit.common.vo.BucketVO vBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketVO.setParentVO(this.getClass(), this);
        _bucketVOList.addElement(vBucketVO);
    } //-- void addBucketVO(edit.common.vo.BucketVO) 

    /**
     * Method addBucketVO
     * 
     * @param index
     * @param vBucketVO
     */
    public void addBucketVO(int index, edit.common.vo.BucketVO vBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketVO.setParentVO(this.getClass(), this);
        _bucketVOList.insertElementAt(vBucketVO, index);
    } //-- void addBucketVO(int, edit.common.vo.BucketVO) 

    /**
     * Method addCommissionInvestmentHistoryVO
     * 
     * @param vCommissionInvestmentHistoryVO
     */
    public void addCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO vCommissionInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _commissionInvestmentHistoryVOList.addElement(vCommissionInvestmentHistoryVO);
    } //-- void addCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method addCommissionInvestmentHistoryVO
     * 
     * @param index
     * @param vCommissionInvestmentHistoryVO
     */
    public void addCommissionInvestmentHistoryVO(int index, edit.common.vo.CommissionInvestmentHistoryVO vCommissionInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _commissionInvestmentHistoryVOList.insertElementAt(vCommissionInvestmentHistoryVO, index);
    } //-- void addCommissionInvestmentHistoryVO(int, edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.addElement(vInvestmentAllocationOverrideVO);
    } //-- void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.insertElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void addInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method addInvestmentAllocationVO
     * 
     * @param vInvestmentAllocationVO
     */
    public void addInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO vInvestmentAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationVO.setParentVO(this.getClass(), this);
        _investmentAllocationVOList.addElement(vInvestmentAllocationVO);
    } //-- void addInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method addInvestmentAllocationVO
     * 
     * @param index
     * @param vInvestmentAllocationVO
     */
    public void addInvestmentAllocationVO(int index, edit.common.vo.InvestmentAllocationVO vInvestmentAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationVO.setParentVO(this.getClass(), this);
        _investmentAllocationVOList.insertElementAt(vInvestmentAllocationVO, index);
    } //-- void addInvestmentAllocationVO(int, edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method addInvestmentHistoryVO
     * 
     * @param vInvestmentHistoryVO
     */
    public void addInvestmentHistoryVO(edit.common.vo.InvestmentHistoryVO vInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _investmentHistoryVOList.addElement(vInvestmentHistoryVO);
    } //-- void addInvestmentHistoryVO(edit.common.vo.InvestmentHistoryVO) 

    /**
     * Method addInvestmentHistoryVO
     * 
     * @param index
     * @param vInvestmentHistoryVO
     */
    public void addInvestmentHistoryVO(int index, edit.common.vo.InvestmentHistoryVO vInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _investmentHistoryVOList.insertElementAt(vInvestmentHistoryVO, index);
    } //-- void addInvestmentHistoryVO(int, edit.common.vo.InvestmentHistoryVO) 

    /**
     * Method enumerateBucketVO
     */
    public java.util.Enumeration enumerateBucketVO()
    {
        return _bucketVOList.elements();
    } //-- java.util.Enumeration enumerateBucketVO() 

    /**
     * Method enumerateCommissionInvestmentHistoryVO
     */
    public java.util.Enumeration enumerateCommissionInvestmentHistoryVO()
    {
        return _commissionInvestmentHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionInvestmentHistoryVO() 

    /**
     * Method enumerateInvestmentAllocationOverrideVO
     */
    public java.util.Enumeration enumerateInvestmentAllocationOverrideVO()
    {
        return _investmentAllocationOverrideVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentAllocationOverrideVO() 

    /**
     * Method enumerateInvestmentAllocationVO
     */
    public java.util.Enumeration enumerateInvestmentAllocationVO()
    {
        return _investmentAllocationVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentAllocationVO() 

    /**
     * Method enumerateInvestmentHistoryVO
     */
    public java.util.Enumeration enumerateInvestmentHistoryVO()
    {
        return _investmentHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentHistoryVO() 

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
        
        if (obj instanceof InvestmentVO) {
        
            InvestmentVO temp = (InvestmentVO)obj;
            if (this._investmentPK != temp._investmentPK)
                return false;
            if (this._has_investmentPK != temp._has_investmentPK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._excessInterestCalculationDate != null) {
                if (temp._excessInterestCalculationDate == null) return false;
                else if (!(this._excessInterestCalculationDate.equals(temp._excessInterestCalculationDate))) 
                    return false;
            }
            else if (temp._excessInterestCalculationDate != null)
                return false;
            if (this._excessInterestPaymentDate != null) {
                if (temp._excessInterestPaymentDate == null) return false;
                else if (!(this._excessInterestPaymentDate.equals(temp._excessInterestPaymentDate))) 
                    return false;
            }
            else if (temp._excessInterestPaymentDate != null)
                return false;
            if (this._excessInterest != null) {
                if (temp._excessInterest == null) return false;
                else if (!(this._excessInterest.equals(temp._excessInterest))) 
                    return false;
            }
            else if (temp._excessInterest != null)
                return false;
            if (this._excessInterestMethod != null) {
                if (temp._excessInterestMethod == null) return false;
                else if (!(this._excessInterestMethod.equals(temp._excessInterestMethod))) 
                    return false;
            }
            else if (temp._excessInterestMethod != null)
                return false;
            if (this._excessInterestStartDate != null) {
                if (temp._excessInterestStartDate == null) return false;
                else if (!(this._excessInterestStartDate.equals(temp._excessInterestStartDate))) 
                    return false;
            }
            else if (temp._excessInterestStartDate != null)
                return false;
            if (this._assumedInvestmentReturn != null) {
                if (temp._assumedInvestmentReturn == null) return false;
                else if (!(this._assumedInvestmentReturn.equals(temp._assumedInvestmentReturn))) 
                    return false;
            }
            else if (temp._assumedInvestmentReturn != null)
                return false;
            if (this._chargeCodeFK != temp._chargeCodeFK)
                return false;
            if (this._has_chargeCodeFK != temp._has_chargeCodeFK)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._bucketVOList != null) {
                if (temp._bucketVOList == null) return false;
                else if (!(this._bucketVOList.equals(temp._bucketVOList))) 
                    return false;
            }
            else if (temp._bucketVOList != null)
                return false;
            if (this._commissionInvestmentHistoryVOList != null) {
                if (temp._commissionInvestmentHistoryVOList == null) return false;
                else if (!(this._commissionInvestmentHistoryVOList.equals(temp._commissionInvestmentHistoryVOList))) 
                    return false;
            }
            else if (temp._commissionInvestmentHistoryVOList != null)
                return false;
            if (this._investmentAllocationVOList != null) {
                if (temp._investmentAllocationVOList == null) return false;
                else if (!(this._investmentAllocationVOList.equals(temp._investmentAllocationVOList))) 
                    return false;
            }
            else if (temp._investmentAllocationVOList != null)
                return false;
            if (this._investmentHistoryVOList != null) {
                if (temp._investmentHistoryVOList == null) return false;
                else if (!(this._investmentHistoryVOList.equals(temp._investmentHistoryVOList))) 
                    return false;
            }
            else if (temp._investmentHistoryVOList != null)
                return false;
            if (this._investmentAllocationOverrideVOList != null) {
                if (temp._investmentAllocationOverrideVOList == null) return false;
                else if (!(this._investmentAllocationOverrideVOList.equals(temp._investmentAllocationOverrideVOList))) 
                    return false;
            }
            else if (temp._investmentAllocationOverrideVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAssumedInvestmentReturnReturns the value of field
     * 'assumedInvestmentReturn'.
     * 
     * @return the value of field 'assumedInvestmentReturn'.
     */
    public java.math.BigDecimal getAssumedInvestmentReturn()
    {
        return this._assumedInvestmentReturn;
    } //-- java.math.BigDecimal getAssumedInvestmentReturn() 

    /**
     * Method getBucketVO
     * 
     * @param index
     */
    public edit.common.vo.BucketVO getBucketVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BucketVO) _bucketVOList.elementAt(index);
    } //-- edit.common.vo.BucketVO getBucketVO(int) 

    /**
     * Method getBucketVO
     */
    public edit.common.vo.BucketVO[] getBucketVO()
    {
        int size = _bucketVOList.size();
        edit.common.vo.BucketVO[] mArray = new edit.common.vo.BucketVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BucketVO) _bucketVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BucketVO[] getBucketVO() 

    /**
     * Method getBucketVOCount
     */
    public int getBucketVOCount()
    {
        return _bucketVOList.size();
    } //-- int getBucketVOCount() 

    /**
     * Method getChargeCodeFKReturns the value of field
     * 'chargeCodeFK'.
     * 
     * @return the value of field 'chargeCodeFK'.
     */
    public long getChargeCodeFK()
    {
        return this._chargeCodeFK;
    } //-- long getChargeCodeFK() 

    /**
     * Method getCommissionInvestmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionInvestmentHistoryVO getCommissionInvestmentHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionInvestmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionInvestmentHistoryVO) _commissionInvestmentHistoryVOList.elementAt(index);
    } //-- edit.common.vo.CommissionInvestmentHistoryVO getCommissionInvestmentHistoryVO(int) 

    /**
     * Method getCommissionInvestmentHistoryVO
     */
    public edit.common.vo.CommissionInvestmentHistoryVO[] getCommissionInvestmentHistoryVO()
    {
        int size = _commissionInvestmentHistoryVOList.size();
        edit.common.vo.CommissionInvestmentHistoryVO[] mArray = new edit.common.vo.CommissionInvestmentHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionInvestmentHistoryVO) _commissionInvestmentHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionInvestmentHistoryVO[] getCommissionInvestmentHistoryVO() 

    /**
     * Method getCommissionInvestmentHistoryVOCount
     */
    public int getCommissionInvestmentHistoryVOCount()
    {
        return _commissionInvestmentHistoryVOList.size();
    } //-- int getCommissionInvestmentHistoryVOCount() 

    /**
     * Method getExcessInterestReturns the value of field
     * 'excessInterest'.
     * 
     * @return the value of field 'excessInterest'.
     */
    public java.math.BigDecimal getExcessInterest()
    {
        return this._excessInterest;
    } //-- java.math.BigDecimal getExcessInterest() 

    /**
     * Method getExcessInterestCalculationDateReturns the value of
     * field 'excessInterestCalculationDate'.
     * 
     * @return the value of field 'excessInterestCalculationDate'.
     */
    public java.lang.String getExcessInterestCalculationDate()
    {
        return this._excessInterestCalculationDate;
    } //-- java.lang.String getExcessInterestCalculationDate() 

    /**
     * Method getExcessInterestMethodReturns the value of field
     * 'excessInterestMethod'.
     * 
     * @return the value of field 'excessInterestMethod'.
     */
    public java.lang.String getExcessInterestMethod()
    {
        return this._excessInterestMethod;
    } //-- java.lang.String getExcessInterestMethod() 

    /**
     * Method getExcessInterestPaymentDateReturns the value of
     * field 'excessInterestPaymentDate'.
     * 
     * @return the value of field 'excessInterestPaymentDate'.
     */
    public java.lang.String getExcessInterestPaymentDate()
    {
        return this._excessInterestPaymentDate;
    } //-- java.lang.String getExcessInterestPaymentDate() 

    /**
     * Method getExcessInterestStartDateReturns the value of field
     * 'excessInterestStartDate'.
     * 
     * @return the value of field 'excessInterestStartDate'.
     */
    public java.lang.String getExcessInterestStartDate()
    {
        return this._excessInterestStartDate;
    } //-- java.lang.String getExcessInterestStartDate() 

    /**
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

    /**
     * Method getInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int) 

    /**
     * Method getInvestmentAllocationOverrideVO
     */
    public edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO()
    {
        int size = _investmentAllocationOverrideVOList.size();
        edit.common.vo.InvestmentAllocationOverrideVO[] mArray = new edit.common.vo.InvestmentAllocationOverrideVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO() 

    /**
     * Method getInvestmentAllocationOverrideVOCount
     */
    public int getInvestmentAllocationOverrideVOCount()
    {
        return _investmentAllocationOverrideVOList.size();
    } //-- int getInvestmentAllocationOverrideVOCount() 

    /**
     * Method getInvestmentAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationVO getInvestmentAllocationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentAllocationVO) _investmentAllocationVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentAllocationVO getInvestmentAllocationVO(int) 

    /**
     * Method getInvestmentAllocationVO
     */
    public edit.common.vo.InvestmentAllocationVO[] getInvestmentAllocationVO()
    {
        int size = _investmentAllocationVOList.size();
        edit.common.vo.InvestmentAllocationVO[] mArray = new edit.common.vo.InvestmentAllocationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentAllocationVO) _investmentAllocationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentAllocationVO[] getInvestmentAllocationVO() 

    /**
     * Method getInvestmentAllocationVOCount
     */
    public int getInvestmentAllocationVOCount()
    {
        return _investmentAllocationVOList.size();
    } //-- int getInvestmentAllocationVOCount() 

    /**
     * Method getInvestmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentHistoryVO getInvestmentHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentHistoryVO) _investmentHistoryVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentHistoryVO getInvestmentHistoryVO(int) 

    /**
     * Method getInvestmentHistoryVO
     */
    public edit.common.vo.InvestmentHistoryVO[] getInvestmentHistoryVO()
    {
        int size = _investmentHistoryVOList.size();
        edit.common.vo.InvestmentHistoryVO[] mArray = new edit.common.vo.InvestmentHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentHistoryVO) _investmentHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentHistoryVO[] getInvestmentHistoryVO() 

    /**
     * Method getInvestmentHistoryVOCount
     */
    public int getInvestmentHistoryVOCount()
    {
        return _investmentHistoryVOList.size();
    } //-- int getInvestmentHistoryVOCount() 

    /**
     * Method getInvestmentPKReturns the value of field
     * 'investmentPK'.
     * 
     * @return the value of field 'investmentPK'.
     */
    public long getInvestmentPK()
    {
        return this._investmentPK;
    } //-- long getInvestmentPK() 

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
     * Method getStatusReturns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Method hasChargeCodeFK
     */
    public boolean hasChargeCodeFK()
    {
        return this._has_chargeCodeFK;
    } //-- boolean hasChargeCodeFK() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

    /**
     * Method hasInvestmentPK
     */
    public boolean hasInvestmentPK()
    {
        return this._has_investmentPK;
    } //-- boolean hasInvestmentPK() 

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
     * Method removeAllBucketVO
     */
    public void removeAllBucketVO()
    {
        _bucketVOList.removeAllElements();
    } //-- void removeAllBucketVO() 

    /**
     * Method removeAllCommissionInvestmentHistoryVO
     */
    public void removeAllCommissionInvestmentHistoryVO()
    {
        _commissionInvestmentHistoryVOList.removeAllElements();
    } //-- void removeAllCommissionInvestmentHistoryVO() 

    /**
     * Method removeAllInvestmentAllocationOverrideVO
     */
    public void removeAllInvestmentAllocationOverrideVO()
    {
        _investmentAllocationOverrideVOList.removeAllElements();
    } //-- void removeAllInvestmentAllocationOverrideVO() 

    /**
     * Method removeAllInvestmentAllocationVO
     */
    public void removeAllInvestmentAllocationVO()
    {
        _investmentAllocationVOList.removeAllElements();
    } //-- void removeAllInvestmentAllocationVO() 

    /**
     * Method removeAllInvestmentHistoryVO
     */
    public void removeAllInvestmentHistoryVO()
    {
        _investmentHistoryVOList.removeAllElements();
    } //-- void removeAllInvestmentHistoryVO() 

    /**
     * Method removeBucketVO
     * 
     * @param index
     */
    public edit.common.vo.BucketVO removeBucketVO(int index)
    {
        java.lang.Object obj = _bucketVOList.elementAt(index);
        _bucketVOList.removeElementAt(index);
        return (edit.common.vo.BucketVO) obj;
    } //-- edit.common.vo.BucketVO removeBucketVO(int) 

    /**
     * Method removeCommissionInvestmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionInvestmentHistoryVO removeCommissionInvestmentHistoryVO(int index)
    {
        java.lang.Object obj = _commissionInvestmentHistoryVOList.elementAt(index);
        _commissionInvestmentHistoryVOList.removeElementAt(index);
        return (edit.common.vo.CommissionInvestmentHistoryVO) obj;
    } //-- edit.common.vo.CommissionInvestmentHistoryVO removeCommissionInvestmentHistoryVO(int) 

    /**
     * Method removeInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int index)
    {
        java.lang.Object obj = _investmentAllocationOverrideVOList.elementAt(index);
        _investmentAllocationOverrideVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentAllocationOverrideVO) obj;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int) 

    /**
     * Method removeInvestmentAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationVO removeInvestmentAllocationVO(int index)
    {
        java.lang.Object obj = _investmentAllocationVOList.elementAt(index);
        _investmentAllocationVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentAllocationVO) obj;
    } //-- edit.common.vo.InvestmentAllocationVO removeInvestmentAllocationVO(int) 

    /**
     * Method removeInvestmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentHistoryVO removeInvestmentHistoryVO(int index)
    {
        java.lang.Object obj = _investmentHistoryVOList.elementAt(index);
        _investmentHistoryVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentHistoryVO) obj;
    } //-- edit.common.vo.InvestmentHistoryVO removeInvestmentHistoryVO(int) 

    /**
     * Method setAssumedInvestmentReturnSets the value of field
     * 'assumedInvestmentReturn'.
     * 
     * @param assumedInvestmentReturn the value of field
     * 'assumedInvestmentReturn'.
     */
    public void setAssumedInvestmentReturn(java.math.BigDecimal assumedInvestmentReturn)
    {
        this._assumedInvestmentReturn = assumedInvestmentReturn;
        
        super.setVoChanged(true);
    } //-- void setAssumedInvestmentReturn(java.math.BigDecimal) 

    /**
     * Method setBucketVO
     * 
     * @param index
     * @param vBucketVO
     */
    public void setBucketVO(int index, edit.common.vo.BucketVO vBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBucketVO.setParentVO(this.getClass(), this);
        _bucketVOList.setElementAt(vBucketVO, index);
    } //-- void setBucketVO(int, edit.common.vo.BucketVO) 

    /**
     * Method setBucketVO
     * 
     * @param bucketVOArray
     */
    public void setBucketVO(edit.common.vo.BucketVO[] bucketVOArray)
    {
        //-- copy array
        _bucketVOList.removeAllElements();
        for (int i = 0; i < bucketVOArray.length; i++) {
            bucketVOArray[i].setParentVO(this.getClass(), this);
            _bucketVOList.addElement(bucketVOArray[i]);
        }
    } //-- void setBucketVO(edit.common.vo.BucketVO) 

    /**
     * Method setChargeCodeFKSets the value of field
     * 'chargeCodeFK'.
     * 
     * @param chargeCodeFK the value of field 'chargeCodeFK'.
     */
    public void setChargeCodeFK(long chargeCodeFK)
    {
        this._chargeCodeFK = chargeCodeFK;
        
        super.setVoChanged(true);
        this._has_chargeCodeFK = true;
    } //-- void setChargeCodeFK(long) 

    /**
     * Method setCommissionInvestmentHistoryVO
     * 
     * @param index
     * @param vCommissionInvestmentHistoryVO
     */
    public void setCommissionInvestmentHistoryVO(int index, edit.common.vo.CommissionInvestmentHistoryVO vCommissionInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionInvestmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _commissionInvestmentHistoryVOList.setElementAt(vCommissionInvestmentHistoryVO, index);
    } //-- void setCommissionInvestmentHistoryVO(int, edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method setCommissionInvestmentHistoryVO
     * 
     * @param commissionInvestmentHistoryVOArray
     */
    public void setCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO[] commissionInvestmentHistoryVOArray)
    {
        //-- copy array
        _commissionInvestmentHistoryVOList.removeAllElements();
        for (int i = 0; i < commissionInvestmentHistoryVOArray.length; i++) {
            commissionInvestmentHistoryVOArray[i].setParentVO(this.getClass(), this);
            _commissionInvestmentHistoryVOList.addElement(commissionInvestmentHistoryVOArray[i]);
        }
    } //-- void setCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method setExcessInterestSets the value of field
     * 'excessInterest'.
     * 
     * @param excessInterest the value of field 'excessInterest'.
     */
    public void setExcessInterest(java.math.BigDecimal excessInterest)
    {
        this._excessInterest = excessInterest;
        
        super.setVoChanged(true);
    } //-- void setExcessInterest(java.math.BigDecimal) 

    /**
     * Method setExcessInterestCalculationDateSets the value of
     * field 'excessInterestCalculationDate'.
     * 
     * @param excessInterestCalculationDate the value of field
     * 'excessInterestCalculationDate'.
     */
    public void setExcessInterestCalculationDate(java.lang.String excessInterestCalculationDate)
    {
        this._excessInterestCalculationDate = excessInterestCalculationDate;
        
        super.setVoChanged(true);
    } //-- void setExcessInterestCalculationDate(java.lang.String) 

    /**
     * Method setExcessInterestMethodSets the value of field
     * 'excessInterestMethod'.
     * 
     * @param excessInterestMethod the value of field
     * 'excessInterestMethod'.
     */
    public void setExcessInterestMethod(java.lang.String excessInterestMethod)
    {
        this._excessInterestMethod = excessInterestMethod;
        
        super.setVoChanged(true);
    } //-- void setExcessInterestMethod(java.lang.String) 

    /**
     * Method setExcessInterestPaymentDateSets the value of field
     * 'excessInterestPaymentDate'.
     * 
     * @param excessInterestPaymentDate the value of field
     * 'excessInterestPaymentDate'.
     */
    public void setExcessInterestPaymentDate(java.lang.String excessInterestPaymentDate)
    {
        this._excessInterestPaymentDate = excessInterestPaymentDate;
        
        super.setVoChanged(true);
    } //-- void setExcessInterestPaymentDate(java.lang.String) 

    /**
     * Method setExcessInterestStartDateSets the value of field
     * 'excessInterestStartDate'.
     * 
     * @param excessInterestStartDate the value of field
     * 'excessInterestStartDate'.
     */
    public void setExcessInterestStartDate(java.lang.String excessInterestStartDate)
    {
        this._excessInterestStartDate = excessInterestStartDate;
        
        super.setVoChanged(true);
    } //-- void setExcessInterestStartDate(java.lang.String) 

    /**
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void setInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.setElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void setInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param investmentAllocationOverrideVOArray
     */
    public void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOArray)
    {
        //-- copy array
        _investmentAllocationOverrideVOList.removeAllElements();
        for (int i = 0; i < investmentAllocationOverrideVOArray.length; i++) {
            investmentAllocationOverrideVOArray[i].setParentVO(this.getClass(), this);
            _investmentAllocationOverrideVOList.addElement(investmentAllocationOverrideVOArray[i]);
        }
    } //-- void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setInvestmentAllocationVO
     * 
     * @param index
     * @param vInvestmentAllocationVO
     */
    public void setInvestmentAllocationVO(int index, edit.common.vo.InvestmentAllocationVO vInvestmentAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentAllocationVO.setParentVO(this.getClass(), this);
        _investmentAllocationVOList.setElementAt(vInvestmentAllocationVO, index);
    } //-- void setInvestmentAllocationVO(int, edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method setInvestmentAllocationVO
     * 
     * @param investmentAllocationVOArray
     */
    public void setInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO[] investmentAllocationVOArray)
    {
        //-- copy array
        _investmentAllocationVOList.removeAllElements();
        for (int i = 0; i < investmentAllocationVOArray.length; i++) {
            investmentAllocationVOArray[i].setParentVO(this.getClass(), this);
            _investmentAllocationVOList.addElement(investmentAllocationVOArray[i]);
        }
    } //-- void setInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method setInvestmentHistoryVO
     * 
     * @param index
     * @param vInvestmentHistoryVO
     */
    public void setInvestmentHistoryVO(int index, edit.common.vo.InvestmentHistoryVO vInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _investmentHistoryVOList.setElementAt(vInvestmentHistoryVO, index);
    } //-- void setInvestmentHistoryVO(int, edit.common.vo.InvestmentHistoryVO) 

    /**
     * Method setInvestmentHistoryVO
     * 
     * @param investmentHistoryVOArray
     */
    public void setInvestmentHistoryVO(edit.common.vo.InvestmentHistoryVO[] investmentHistoryVOArray)
    {
        //-- copy array
        _investmentHistoryVOList.removeAllElements();
        for (int i = 0; i < investmentHistoryVOArray.length; i++) {
            investmentHistoryVOArray[i].setParentVO(this.getClass(), this);
            _investmentHistoryVOList.addElement(investmentHistoryVOArray[i]);
        }
    } //-- void setInvestmentHistoryVO(edit.common.vo.InvestmentHistoryVO) 

    /**
     * Method setInvestmentPKSets the value of field
     * 'investmentPK'.
     * 
     * @param investmentPK the value of field 'investmentPK'.
     */
    public void setInvestmentPK(long investmentPK)
    {
        this._investmentPK = investmentPK;
        
        super.setVoChanged(true);
        this._has_investmentPK = true;
    } //-- void setInvestmentPK(long) 

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
     * Method setStatusSets the value of field 'status'.
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(java.lang.String status)
    {
        this._status = status;
        
        super.setVoChanged(true);
    } //-- void setStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentVO) Unmarshaller.unmarshal(edit.common.vo.InvestmentVO.class, reader);
    } //-- edit.common.vo.InvestmentVO unmarshal(java.io.Reader) 

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
