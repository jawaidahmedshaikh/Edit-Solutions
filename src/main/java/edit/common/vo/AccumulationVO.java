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
import java.util.Enumeration;
import java.util.Vector;
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
public class AccumulationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _amountPaidToDate
     */
    private double _amountPaidToDate;

    /**
     * keeps track of state for field: _amountPaidToDate
     */
    private boolean _has_amountPaidToDate;

    /**
     * Field _amountPaidYTD
     */
    private double _amountPaidYTD;

    /**
     * keeps track of state for field: _amountPaidYTD
     */
    private boolean _has_amountPaidYTD;

    /**
     * Field _withdrawalsYTD
     */
    private double _withdrawalsYTD;

    /**
     * keeps track of state for field: _withdrawalsYTD
     */
    private boolean _has_withdrawalsYTD;

    /**
     * Field _lumpSumPaidToDate
     */
    private double _lumpSumPaidToDate;

    /**
     * keeps track of state for field: _lumpSumPaidToDate
     */
    private boolean _has_lumpSumPaidToDate;

    /**
     * Field _lumpSumPaidYTD
     */
    private double _lumpSumPaidYTD;

    /**
     * keeps track of state for field: _lumpSumPaidYTD
     */
    private boolean _has_lumpSumPaidYTD;

    /**
     * Field _accumLoads
     */
    private double _accumLoads;

    /**
     * keeps track of state for field: _accumLoads
     */
    private boolean _has_accumLoads;

    /**
     * Field _accumFees
     */
    private double _accumFees;

    /**
     * keeps track of state for field: _accumFees
     */
    private boolean _has_accumFees;

    /**
     * Field _totalDisbursements
     */
    private double _totalDisbursements;

    /**
     * keeps track of state for field: _totalDisbursements
     */
    private boolean _has_totalDisbursements;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _payeeAccumsVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _allocationHistoryAccumsVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AccumulationVO() {
        super();
        _payeeAccumsVOList = new Vector();
        _allocationHistoryAccumsVOList = new Vector();
    } //-- edit.common.vo.AccumulationVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAllocationHistoryAccumsVO
     * 
     * @param vAllocationHistoryAccumsVO
     */
    public void addAllocationHistoryAccumsVO(edit.common.vo.AllocationHistoryAccumsVO vAllocationHistoryAccumsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAllocationHistoryAccumsVO.setParentVO(this.getClass(), this);
        _allocationHistoryAccumsVOList.addElement(vAllocationHistoryAccumsVO);
    } //-- void addAllocationHistoryAccumsVO(edit.common.vo.AllocationHistoryAccumsVO) 

    /**
     * Method addAllocationHistoryAccumsVO
     * 
     * @param index
     * @param vAllocationHistoryAccumsVO
     */
    public void addAllocationHistoryAccumsVO(int index, edit.common.vo.AllocationHistoryAccumsVO vAllocationHistoryAccumsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAllocationHistoryAccumsVO.setParentVO(this.getClass(), this);
        _allocationHistoryAccumsVOList.insertElementAt(vAllocationHistoryAccumsVO, index);
    } //-- void addAllocationHistoryAccumsVO(int, edit.common.vo.AllocationHistoryAccumsVO) 

    /**
     * Method addPayeeAccumsVO
     * 
     * @param vPayeeAccumsVO
     */
    public void addPayeeAccumsVO(edit.common.vo.PayeeAccumsVO vPayeeAccumsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPayeeAccumsVO.setParentVO(this.getClass(), this);
        _payeeAccumsVOList.addElement(vPayeeAccumsVO);
    } //-- void addPayeeAccumsVO(edit.common.vo.PayeeAccumsVO) 

    /**
     * Method addPayeeAccumsVO
     * 
     * @param index
     * @param vPayeeAccumsVO
     */
    public void addPayeeAccumsVO(int index, edit.common.vo.PayeeAccumsVO vPayeeAccumsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPayeeAccumsVO.setParentVO(this.getClass(), this);
        _payeeAccumsVOList.insertElementAt(vPayeeAccumsVO, index);
    } //-- void addPayeeAccumsVO(int, edit.common.vo.PayeeAccumsVO) 

    /**
     * Method enumerateAllocationHistoryAccumsVO
     */
    public java.util.Enumeration enumerateAllocationHistoryAccumsVO()
    {
        return _allocationHistoryAccumsVOList.elements();
    } //-- java.util.Enumeration enumerateAllocationHistoryAccumsVO() 

    /**
     * Method enumeratePayeeAccumsVO
     */
    public java.util.Enumeration enumeratePayeeAccumsVO()
    {
        return _payeeAccumsVOList.elements();
    } //-- java.util.Enumeration enumeratePayeeAccumsVO() 

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
        
        if (obj instanceof AccumulationVO) {
        
            AccumulationVO temp = (AccumulationVO)obj;
            if (this._amountPaidToDate != temp._amountPaidToDate)
                return false;
            if (this._has_amountPaidToDate != temp._has_amountPaidToDate)
                return false;
            if (this._amountPaidYTD != temp._amountPaidYTD)
                return false;
            if (this._has_amountPaidYTD != temp._has_amountPaidYTD)
                return false;
            if (this._withdrawalsYTD != temp._withdrawalsYTD)
                return false;
            if (this._has_withdrawalsYTD != temp._has_withdrawalsYTD)
                return false;
            if (this._lumpSumPaidToDate != temp._lumpSumPaidToDate)
                return false;
            if (this._has_lumpSumPaidToDate != temp._has_lumpSumPaidToDate)
                return false;
            if (this._lumpSumPaidYTD != temp._lumpSumPaidYTD)
                return false;
            if (this._has_lumpSumPaidYTD != temp._has_lumpSumPaidYTD)
                return false;
            if (this._accumLoads != temp._accumLoads)
                return false;
            if (this._has_accumLoads != temp._has_accumLoads)
                return false;
            if (this._accumFees != temp._accumFees)
                return false;
            if (this._has_accumFees != temp._has_accumFees)
                return false;
            if (this._totalDisbursements != temp._totalDisbursements)
                return false;
            if (this._has_totalDisbursements != temp._has_totalDisbursements)
                return false;
            if (this._payeeAccumsVOList != null) {
                if (temp._payeeAccumsVOList == null) return false;
                else if (!(this._payeeAccumsVOList.equals(temp._payeeAccumsVOList))) 
                    return false;
            }
            else if (temp._payeeAccumsVOList != null)
                return false;
            if (this._allocationHistoryAccumsVOList != null) {
                if (temp._allocationHistoryAccumsVOList == null) return false;
                else if (!(this._allocationHistoryAccumsVOList.equals(temp._allocationHistoryAccumsVOList))) 
                    return false;
            }
            else if (temp._allocationHistoryAccumsVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumFeesReturns the value of field 'accumFees'.
     * 
     * @return the value of field 'accumFees'.
     */
    public double getAccumFees()
    {
        return this._accumFees;
    } //-- double getAccumFees() 

    /**
     * Method getAccumLoadsReturns the value of field 'accumLoads'.
     * 
     * @return the value of field 'accumLoads'.
     */
    public double getAccumLoads()
    {
        return this._accumLoads;
    } //-- double getAccumLoads() 

    /**
     * Method getAllocationHistoryAccumsVO
     * 
     * @param index
     */
    public edit.common.vo.AllocationHistoryAccumsVO getAllocationHistoryAccumsVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _allocationHistoryAccumsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AllocationHistoryAccumsVO) _allocationHistoryAccumsVOList.elementAt(index);
    } //-- edit.common.vo.AllocationHistoryAccumsVO getAllocationHistoryAccumsVO(int) 

    /**
     * Method getAllocationHistoryAccumsVO
     */
    public edit.common.vo.AllocationHistoryAccumsVO[] getAllocationHistoryAccumsVO()
    {
        int size = _allocationHistoryAccumsVOList.size();
        edit.common.vo.AllocationHistoryAccumsVO[] mArray = new edit.common.vo.AllocationHistoryAccumsVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AllocationHistoryAccumsVO) _allocationHistoryAccumsVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AllocationHistoryAccumsVO[] getAllocationHistoryAccumsVO() 

    /**
     * Method getAllocationHistoryAccumsVOCount
     */
    public int getAllocationHistoryAccumsVOCount()
    {
        return _allocationHistoryAccumsVOList.size();
    } //-- int getAllocationHistoryAccumsVOCount() 

    /**
     * Method getAmountPaidToDateReturns the value of field
     * 'amountPaidToDate'.
     * 
     * @return the value of field 'amountPaidToDate'.
     */
    public double getAmountPaidToDate()
    {
        return this._amountPaidToDate;
    } //-- double getAmountPaidToDate() 

    /**
     * Method getAmountPaidYTDReturns the value of field
     * 'amountPaidYTD'.
     * 
     * @return the value of field 'amountPaidYTD'.
     */
    public double getAmountPaidYTD()
    {
        return this._amountPaidYTD;
    } //-- double getAmountPaidYTD() 

    /**
     * Method getLumpSumPaidToDateReturns the value of field
     * 'lumpSumPaidToDate'.
     * 
     * @return the value of field 'lumpSumPaidToDate'.
     */
    public double getLumpSumPaidToDate()
    {
        return this._lumpSumPaidToDate;
    } //-- double getLumpSumPaidToDate() 

    /**
     * Method getLumpSumPaidYTDReturns the value of field
     * 'lumpSumPaidYTD'.
     * 
     * @return the value of field 'lumpSumPaidYTD'.
     */
    public double getLumpSumPaidYTD()
    {
        return this._lumpSumPaidYTD;
    } //-- double getLumpSumPaidYTD() 

    /**
     * Method getPayeeAccumsVO
     * 
     * @param index
     */
    public edit.common.vo.PayeeAccumsVO getPayeeAccumsVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _payeeAccumsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PayeeAccumsVO) _payeeAccumsVOList.elementAt(index);
    } //-- edit.common.vo.PayeeAccumsVO getPayeeAccumsVO(int) 

    /**
     * Method getPayeeAccumsVO
     */
    public edit.common.vo.PayeeAccumsVO[] getPayeeAccumsVO()
    {
        int size = _payeeAccumsVOList.size();
        edit.common.vo.PayeeAccumsVO[] mArray = new edit.common.vo.PayeeAccumsVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PayeeAccumsVO) _payeeAccumsVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PayeeAccumsVO[] getPayeeAccumsVO() 

    /**
     * Method getPayeeAccumsVOCount
     */
    public int getPayeeAccumsVOCount()
    {
        return _payeeAccumsVOList.size();
    } //-- int getPayeeAccumsVOCount() 

    /**
     * Method getTotalDisbursementsReturns the value of field
     * 'totalDisbursements'.
     * 
     * @return the value of field 'totalDisbursements'.
     */
    public double getTotalDisbursements()
    {
        return this._totalDisbursements;
    } //-- double getTotalDisbursements() 

    /**
     * Method getWithdrawalsYTDReturns the value of field
     * 'withdrawalsYTD'.
     * 
     * @return the value of field 'withdrawalsYTD'.
     */
    public double getWithdrawalsYTD()
    {
        return this._withdrawalsYTD;
    } //-- double getWithdrawalsYTD() 

    /**
     * Method hasAccumFees
     */
    public boolean hasAccumFees()
    {
        return this._has_accumFees;
    } //-- boolean hasAccumFees() 

    /**
     * Method hasAccumLoads
     */
    public boolean hasAccumLoads()
    {
        return this._has_accumLoads;
    } //-- boolean hasAccumLoads() 

    /**
     * Method hasAmountPaidToDate
     */
    public boolean hasAmountPaidToDate()
    {
        return this._has_amountPaidToDate;
    } //-- boolean hasAmountPaidToDate() 

    /**
     * Method hasAmountPaidYTD
     */
    public boolean hasAmountPaidYTD()
    {
        return this._has_amountPaidYTD;
    } //-- boolean hasAmountPaidYTD() 

    /**
     * Method hasLumpSumPaidToDate
     */
    public boolean hasLumpSumPaidToDate()
    {
        return this._has_lumpSumPaidToDate;
    } //-- boolean hasLumpSumPaidToDate() 

    /**
     * Method hasLumpSumPaidYTD
     */
    public boolean hasLumpSumPaidYTD()
    {
        return this._has_lumpSumPaidYTD;
    } //-- boolean hasLumpSumPaidYTD() 

    /**
     * Method hasTotalDisbursements
     */
    public boolean hasTotalDisbursements()
    {
        return this._has_totalDisbursements;
    } //-- boolean hasTotalDisbursements() 

    /**
     * Method hasWithdrawalsYTD
     */
    public boolean hasWithdrawalsYTD()
    {
        return this._has_withdrawalsYTD;
    } //-- boolean hasWithdrawalsYTD() 

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
     * Method removeAllAllocationHistoryAccumsVO
     */
    public void removeAllAllocationHistoryAccumsVO()
    {
        _allocationHistoryAccumsVOList.removeAllElements();
    } //-- void removeAllAllocationHistoryAccumsVO() 

    /**
     * Method removeAllPayeeAccumsVO
     */
    public void removeAllPayeeAccumsVO()
    {
        _payeeAccumsVOList.removeAllElements();
    } //-- void removeAllPayeeAccumsVO() 

    /**
     * Method removeAllocationHistoryAccumsVO
     * 
     * @param index
     */
    public edit.common.vo.AllocationHistoryAccumsVO removeAllocationHistoryAccumsVO(int index)
    {
        java.lang.Object obj = _allocationHistoryAccumsVOList.elementAt(index);
        _allocationHistoryAccumsVOList.removeElementAt(index);
        return (edit.common.vo.AllocationHistoryAccumsVO) obj;
    } //-- edit.common.vo.AllocationHistoryAccumsVO removeAllocationHistoryAccumsVO(int) 

    /**
     * Method removePayeeAccumsVO
     * 
     * @param index
     */
    public edit.common.vo.PayeeAccumsVO removePayeeAccumsVO(int index)
    {
        java.lang.Object obj = _payeeAccumsVOList.elementAt(index);
        _payeeAccumsVOList.removeElementAt(index);
        return (edit.common.vo.PayeeAccumsVO) obj;
    } //-- edit.common.vo.PayeeAccumsVO removePayeeAccumsVO(int) 

    /**
     * Method setAccumFeesSets the value of field 'accumFees'.
     * 
     * @param accumFees the value of field 'accumFees'.
     */
    public void setAccumFees(double accumFees)
    {
        this._accumFees = accumFees;
        
        super.setVoChanged(true);
        this._has_accumFees = true;
    } //-- void setAccumFees(double) 

    /**
     * Method setAccumLoadsSets the value of field 'accumLoads'.
     * 
     * @param accumLoads the value of field 'accumLoads'.
     */
    public void setAccumLoads(double accumLoads)
    {
        this._accumLoads = accumLoads;
        
        super.setVoChanged(true);
        this._has_accumLoads = true;
    } //-- void setAccumLoads(double) 

    /**
     * Method setAllocationHistoryAccumsVO
     * 
     * @param index
     * @param vAllocationHistoryAccumsVO
     */
    public void setAllocationHistoryAccumsVO(int index, edit.common.vo.AllocationHistoryAccumsVO vAllocationHistoryAccumsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _allocationHistoryAccumsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAllocationHistoryAccumsVO.setParentVO(this.getClass(), this);
        _allocationHistoryAccumsVOList.setElementAt(vAllocationHistoryAccumsVO, index);
    } //-- void setAllocationHistoryAccumsVO(int, edit.common.vo.AllocationHistoryAccumsVO) 

    /**
     * Method setAllocationHistoryAccumsVO
     * 
     * @param allocationHistoryAccumsVOArray
     */
    public void setAllocationHistoryAccumsVO(edit.common.vo.AllocationHistoryAccumsVO[] allocationHistoryAccumsVOArray)
    {
        //-- copy array
        _allocationHistoryAccumsVOList.removeAllElements();
        for (int i = 0; i < allocationHistoryAccumsVOArray.length; i++) {
            allocationHistoryAccumsVOArray[i].setParentVO(this.getClass(), this);
            _allocationHistoryAccumsVOList.addElement(allocationHistoryAccumsVOArray[i]);
        }
    } //-- void setAllocationHistoryAccumsVO(edit.common.vo.AllocationHistoryAccumsVO) 

    /**
     * Method setAmountPaidToDateSets the value of field
     * 'amountPaidToDate'.
     * 
     * @param amountPaidToDate the value of field 'amountPaidToDate'
     */
    public void setAmountPaidToDate(double amountPaidToDate)
    {
        this._amountPaidToDate = amountPaidToDate;
        
        super.setVoChanged(true);
        this._has_amountPaidToDate = true;
    } //-- void setAmountPaidToDate(double) 

    /**
     * Method setAmountPaidYTDSets the value of field
     * 'amountPaidYTD'.
     * 
     * @param amountPaidYTD the value of field 'amountPaidYTD'.
     */
    public void setAmountPaidYTD(double amountPaidYTD)
    {
        this._amountPaidYTD = amountPaidYTD;
        
        super.setVoChanged(true);
        this._has_amountPaidYTD = true;
    } //-- void setAmountPaidYTD(double) 

    /**
     * Method setLumpSumPaidToDateSets the value of field
     * 'lumpSumPaidToDate'.
     * 
     * @param lumpSumPaidToDate the value of field
     * 'lumpSumPaidToDate'.
     */
    public void setLumpSumPaidToDate(double lumpSumPaidToDate)
    {
        this._lumpSumPaidToDate = lumpSumPaidToDate;
        
        super.setVoChanged(true);
        this._has_lumpSumPaidToDate = true;
    } //-- void setLumpSumPaidToDate(double) 

    /**
     * Method setLumpSumPaidYTDSets the value of field
     * 'lumpSumPaidYTD'.
     * 
     * @param lumpSumPaidYTD the value of field 'lumpSumPaidYTD'.
     */
    public void setLumpSumPaidYTD(double lumpSumPaidYTD)
    {
        this._lumpSumPaidYTD = lumpSumPaidYTD;
        
        super.setVoChanged(true);
        this._has_lumpSumPaidYTD = true;
    } //-- void setLumpSumPaidYTD(double) 

    /**
     * Method setPayeeAccumsVO
     * 
     * @param index
     * @param vPayeeAccumsVO
     */
    public void setPayeeAccumsVO(int index, edit.common.vo.PayeeAccumsVO vPayeeAccumsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _payeeAccumsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPayeeAccumsVO.setParentVO(this.getClass(), this);
        _payeeAccumsVOList.setElementAt(vPayeeAccumsVO, index);
    } //-- void setPayeeAccumsVO(int, edit.common.vo.PayeeAccumsVO) 

    /**
     * Method setPayeeAccumsVO
     * 
     * @param payeeAccumsVOArray
     */
    public void setPayeeAccumsVO(edit.common.vo.PayeeAccumsVO[] payeeAccumsVOArray)
    {
        //-- copy array
        _payeeAccumsVOList.removeAllElements();
        for (int i = 0; i < payeeAccumsVOArray.length; i++) {
            payeeAccumsVOArray[i].setParentVO(this.getClass(), this);
            _payeeAccumsVOList.addElement(payeeAccumsVOArray[i]);
        }
    } //-- void setPayeeAccumsVO(edit.common.vo.PayeeAccumsVO) 

    /**
     * Method setTotalDisbursementsSets the value of field
     * 'totalDisbursements'.
     * 
     * @param totalDisbursements the value of field
     * 'totalDisbursements'.
     */
    public void setTotalDisbursements(double totalDisbursements)
    {
        this._totalDisbursements = totalDisbursements;
        
        super.setVoChanged(true);
        this._has_totalDisbursements = true;
    } //-- void setTotalDisbursements(double) 

    /**
     * Method setWithdrawalsYTDSets the value of field
     * 'withdrawalsYTD'.
     * 
     * @param withdrawalsYTD the value of field 'withdrawalsYTD'.
     */
    public void setWithdrawalsYTD(double withdrawalsYTD)
    {
        this._withdrawalsYTD = withdrawalsYTD;
        
        super.setVoChanged(true);
        this._has_withdrawalsYTD = true;
    } //-- void setWithdrawalsYTD(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AccumulationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AccumulationVO) Unmarshaller.unmarshal(edit.common.vo.AccumulationVO.class, reader);
    } //-- edit.common.vo.AccumulationVO unmarshal(java.io.Reader) 

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
