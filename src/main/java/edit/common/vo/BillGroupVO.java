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
 * Class BillGroupVO.
 * 
 * @version $Revision$ $Date$
 */
public class BillGroupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _billGroupPK
     */
    private long _billGroupPK;

    /**
     * keeps track of state for field: _billGroupPK
     */
    private boolean _has_billGroupPK;

    /**
     * Field _billScheduleFK
     */
    private long _billScheduleFK;

    /**
     * keeps track of state for field: _billScheduleFK
     */
    private boolean _has_billScheduleFK;

    /**
     * Field _extractDate
     */
    private java.lang.String _extractDate;

    /**
     * Field _dueDate
     */
    private java.lang.String _dueDate;

    /**
     * Field _totalBilledAmount
     */
    private java.math.BigDecimal _totalBilledAmount;

    /**
     * Field _totalPaidAmount
     */
    private java.math.BigDecimal _totalPaidAmount;

    /**
     * Field _releaseDate
     */
    private java.lang.String _releaseDate;

    /**
     * Field _stopReasonCT
     */
    private java.lang.String _stopReasonCT;

    /**
     * Field _billVOList
     */
    private java.util.Vector _billVOList;

    /**
     * Field _overageFundsAmount
     */
    private java.math.BigDecimal _overageFundsAmount;

    /**
     * Field _shortageFundsAmount
     */
    private java.math.BigDecimal _shortageFundsAmount;

    /**
     * Field _creditFundsAmount
     */
    private java.math.BigDecimal _creditFundsAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public BillGroupVO() {
        super();
        _billVOList = new Vector();
    } //-- edit.common.vo.BillGroupVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBillVO
     * 
     * @param vBillVO
     */
    public void addBillVO(edit.common.vo.BillVO vBillVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBillVO.setParentVO(this.getClass(), this);
        _billVOList.addElement(vBillVO);
    } //-- void addBillVO(edit.common.vo.BillVO) 

    /**
     * Method addBillVO
     * 
     * @param index
     * @param vBillVO
     */
    public void addBillVO(int index, edit.common.vo.BillVO vBillVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBillVO.setParentVO(this.getClass(), this);
        _billVOList.insertElementAt(vBillVO, index);
    } //-- void addBillVO(int, edit.common.vo.BillVO) 

    /**
     * Method enumerateBillVO
     */
    public java.util.Enumeration enumerateBillVO()
    {
        return _billVOList.elements();
    } //-- java.util.Enumeration enumerateBillVO() 

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
        
        if (obj instanceof BillGroupVO) {
        
            BillGroupVO temp = (BillGroupVO)obj;
            if (this._billGroupPK != temp._billGroupPK)
                return false;
            if (this._has_billGroupPK != temp._has_billGroupPK)
                return false;
            if (this._billScheduleFK != temp._billScheduleFK)
                return false;
            if (this._has_billScheduleFK != temp._has_billScheduleFK)
                return false;
            if (this._extractDate != null) {
                if (temp._extractDate == null) return false;
                else if (!(this._extractDate.equals(temp._extractDate))) 
                    return false;
            }
            else if (temp._extractDate != null)
                return false;
            if (this._dueDate != null) {
                if (temp._dueDate == null) return false;
                else if (!(this._dueDate.equals(temp._dueDate))) 
                    return false;
            }
            else if (temp._dueDate != null)
                return false;
            if (this._totalBilledAmount != null) {
                if (temp._totalBilledAmount == null) return false;
                else if (!(this._totalBilledAmount.equals(temp._totalBilledAmount))) 
                    return false;
            }
            else if (temp._totalBilledAmount != null)
                return false;
            if (this._totalPaidAmount != null) {
                if (temp._totalPaidAmount == null) return false;
                else if (!(this._totalPaidAmount.equals(temp._totalPaidAmount))) 
                    return false;
            }
            else if (temp._totalPaidAmount != null)
                return false;
            if (this._releaseDate != null) {
                if (temp._releaseDate == null) return false;
                else if (!(this._releaseDate.equals(temp._releaseDate))) 
                    return false;
            }
            else if (temp._releaseDate != null)
                return false;
            if (this._stopReasonCT != null) {
                if (temp._stopReasonCT == null) return false;
                else if (!(this._stopReasonCT.equals(temp._stopReasonCT))) 
                    return false;
            }
            else if (temp._stopReasonCT != null)
                return false;
            if (this._billVOList != null) {
                if (temp._billVOList == null) return false;
                else if (!(this._billVOList.equals(temp._billVOList))) 
                    return false;
            }
            else if (temp._billVOList != null)
                return false;
            if (this._overageFundsAmount != null) {
                if (temp._overageFundsAmount == null) return false;
                else if (!(this._overageFundsAmount.equals(temp._overageFundsAmount))) 
                    return false;
            }
            else if (temp._overageFundsAmount != null)
                return false;
            if (this._shortageFundsAmount != null) {
                if (temp._shortageFundsAmount == null) return false;
                else if (!(this._shortageFundsAmount.equals(temp._shortageFundsAmount))) 
                    return false;
            }
            else if (temp._shortageFundsAmount != null)
                return false;
            if (this._creditFundsAmount != null) {
                if (temp._creditFundsAmount == null) return false;
                else if (!(this._creditFundsAmount.equals(temp._creditFundsAmount))) 
                    return false;
            }
            else if (temp._creditFundsAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBillGroupPKReturns the value of field
     * 'billGroupPK'.
     * 
     * @return the value of field 'billGroupPK'.
     */
    public long getBillGroupPK()
    {
        return this._billGroupPK;
    } //-- long getBillGroupPK() 

    /**
     * Method getBillScheduleFKReturns the value of field
     * 'billScheduleFK'.
     * 
     * @return the value of field 'billScheduleFK'.
     */
    public long getBillScheduleFK()
    {
        return this._billScheduleFK;
    } //-- long getBillScheduleFK() 

    /**
     * Method getBillVO
     * 
     * @param index
     */
    public edit.common.vo.BillVO getBillVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _billVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BillVO) _billVOList.elementAt(index);
    } //-- edit.common.vo.BillVO getBillVO(int) 

    /**
     * Method getBillVO
     */
    public edit.common.vo.BillVO[] getBillVO()
    {
        int size = _billVOList.size();
        edit.common.vo.BillVO[] mArray = new edit.common.vo.BillVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BillVO) _billVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BillVO[] getBillVO() 

    /**
     * Method getBillVOCount
     */
    public int getBillVOCount()
    {
        return _billVOList.size();
    } //-- int getBillVOCount() 

    /**
     * Method getCreditFundsAmountReturns the value of field
     * 'creditFundsAmount'.
     * 
     * @return the value of field 'creditFundsAmount'.
     */
    public java.math.BigDecimal getCreditFundsAmount()
    {
        return this._creditFundsAmount;
    } //-- java.math.BigDecimal getCreditFundsAmount() 

    /**
     * Method getDueDateReturns the value of field 'dueDate'.
     * 
     * @return the value of field 'dueDate'.
     */
    public java.lang.String getDueDate()
    {
        return this._dueDate;
    } //-- java.lang.String getDueDate() 

    /**
     * Method getExtractDateReturns the value of field
     * 'extractDate'.
     * 
     * @return the value of field 'extractDate'.
     */
    public java.lang.String getExtractDate()
    {
        return this._extractDate;
    } //-- java.lang.String getExtractDate() 

    /**
     * Method getOverageFundsAmountReturns the value of field
     * 'overageFundsAmount'.
     * 
     * @return the value of field 'overageFundsAmount'.
     */
    public java.math.BigDecimal getOverageFundsAmount()
    {
        return this._overageFundsAmount;
    } //-- java.math.BigDecimal getOverageFundsAmount() 

    /**
     * Method getReleaseDateReturns the value of field
     * 'releaseDate'.
     * 
     * @return the value of field 'releaseDate'.
     */
    public java.lang.String getReleaseDate()
    {
        return this._releaseDate;
    } //-- java.lang.String getReleaseDate() 

    /**
     * Method getShortageFundsAmountReturns the value of field
     * 'shortageFundsAmount'.
     * 
     * @return the value of field 'shortageFundsAmount'.
     */
    public java.math.BigDecimal getShortageFundsAmount()
    {
        return this._shortageFundsAmount;
    } //-- java.math.BigDecimal getShortageFundsAmount() 

    /**
     * Method getStopReasonCTReturns the value of field
     * 'stopReasonCT'.
     * 
     * @return the value of field 'stopReasonCT'.
     */
    public java.lang.String getStopReasonCT()
    {
        return this._stopReasonCT;
    } //-- java.lang.String getStopReasonCT() 

    /**
     * Method getTotalBilledAmountReturns the value of field
     * 'totalBilledAmount'.
     * 
     * @return the value of field 'totalBilledAmount'.
     */
    public java.math.BigDecimal getTotalBilledAmount()
    {
        return this._totalBilledAmount;
    } //-- java.math.BigDecimal getTotalBilledAmount() 

    /**
     * Method getTotalPaidAmountReturns the value of field
     * 'totalPaidAmount'.
     * 
     * @return the value of field 'totalPaidAmount'.
     */
    public java.math.BigDecimal getTotalPaidAmount()
    {
        return this._totalPaidAmount;
    } //-- java.math.BigDecimal getTotalPaidAmount() 

    /**
     * Method hasBillGroupPK
     */
    public boolean hasBillGroupPK()
    {
        return this._has_billGroupPK;
    } //-- boolean hasBillGroupPK() 

    /**
     * Method hasBillScheduleFK
     */
    public boolean hasBillScheduleFK()
    {
        return this._has_billScheduleFK;
    } //-- boolean hasBillScheduleFK() 

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
     * Method removeAllBillVO
     */
    public void removeAllBillVO()
    {
        _billVOList.removeAllElements();
    } //-- void removeAllBillVO() 

    /**
     * Method removeBillVO
     * 
     * @param index
     */
    public edit.common.vo.BillVO removeBillVO(int index)
    {
        java.lang.Object obj = _billVOList.elementAt(index);
        _billVOList.removeElementAt(index);
        return (edit.common.vo.BillVO) obj;
    } //-- edit.common.vo.BillVO removeBillVO(int) 

    /**
     * Method setBillGroupPKSets the value of field 'billGroupPK'.
     * 
     * @param billGroupPK the value of field 'billGroupPK'.
     */
    public void setBillGroupPK(long billGroupPK)
    {
        this._billGroupPK = billGroupPK;
        
        super.setVoChanged(true);
        this._has_billGroupPK = true;
    } //-- void setBillGroupPK(long) 

    /**
     * Method setBillScheduleFKSets the value of field
     * 'billScheduleFK'.
     * 
     * @param billScheduleFK the value of field 'billScheduleFK'.
     */
    public void setBillScheduleFK(long billScheduleFK)
    {
        this._billScheduleFK = billScheduleFK;
        
        super.setVoChanged(true);
        this._has_billScheduleFK = true;
    } //-- void setBillScheduleFK(long) 

    /**
     * Method setBillVO
     * 
     * @param index
     * @param vBillVO
     */
    public void setBillVO(int index, edit.common.vo.BillVO vBillVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _billVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBillVO.setParentVO(this.getClass(), this);
        _billVOList.setElementAt(vBillVO, index);
    } //-- void setBillVO(int, edit.common.vo.BillVO) 

    /**
     * Method setBillVO
     * 
     * @param billVOArray
     */
    public void setBillVO(edit.common.vo.BillVO[] billVOArray)
    {
        //-- copy array
        _billVOList.removeAllElements();
        for (int i = 0; i < billVOArray.length; i++) {
            billVOArray[i].setParentVO(this.getClass(), this);
            _billVOList.addElement(billVOArray[i]);
        }
    } //-- void setBillVO(edit.common.vo.BillVO) 

    /**
     * Method setCreditFundsAmountSets the value of field
     * 'creditFundsAmount'.
     * 
     * @param creditFundsAmount the value of field
     * 'creditFundsAmount'.
     */
    public void setCreditFundsAmount(java.math.BigDecimal creditFundsAmount)
    {
        this._creditFundsAmount = creditFundsAmount;
        
        super.setVoChanged(true);
    } //-- void setCreditFundsAmount(java.math.BigDecimal) 

    /**
     * Method setDueDateSets the value of field 'dueDate'.
     * 
     * @param dueDate the value of field 'dueDate'.
     */
    public void setDueDate(java.lang.String dueDate)
    {
        this._dueDate = dueDate;
        
        super.setVoChanged(true);
    } //-- void setDueDate(java.lang.String) 

    /**
     * Method setExtractDateSets the value of field 'extractDate'.
     * 
     * @param extractDate the value of field 'extractDate'.
     */
    public void setExtractDate(java.lang.String extractDate)
    {
        this._extractDate = extractDate;
        
        super.setVoChanged(true);
    } //-- void setExtractDate(java.lang.String) 

    /**
     * Method setOverageFundsAmountSets the value of field
     * 'overageFundsAmount'.
     * 
     * @param overageFundsAmount the value of field
     * 'overageFundsAmount'.
     */
    public void setOverageFundsAmount(java.math.BigDecimal overageFundsAmount)
    {
        this._overageFundsAmount = overageFundsAmount;
        
        super.setVoChanged(true);
    } //-- void setOverageFundsAmount(java.math.BigDecimal) 

    /**
     * Method setReleaseDateSets the value of field 'releaseDate'.
     * 
     * @param releaseDate the value of field 'releaseDate'.
     */
    public void setReleaseDate(java.lang.String releaseDate)
    {
        this._releaseDate = releaseDate;
        
        super.setVoChanged(true);
    } //-- void setReleaseDate(java.lang.String) 

    /**
     * Method setShortageFundsAmountSets the value of field
     * 'shortageFundsAmount'.
     * 
     * @param shortageFundsAmount the value of field
     * 'shortageFundsAmount'.
     */
    public void setShortageFundsAmount(java.math.BigDecimal shortageFundsAmount)
    {
        this._shortageFundsAmount = shortageFundsAmount;
        
        super.setVoChanged(true);
    } //-- void setShortageFundsAmount(java.math.BigDecimal) 

    /**
     * Method setStopReasonCTSets the value of field
     * 'stopReasonCT'.
     * 
     * @param stopReasonCT the value of field 'stopReasonCT'.
     */
    public void setStopReasonCT(java.lang.String stopReasonCT)
    {
        this._stopReasonCT = stopReasonCT;
        
        super.setVoChanged(true);
    } //-- void setStopReasonCT(java.lang.String) 

    /**
     * Method setTotalBilledAmountSets the value of field
     * 'totalBilledAmount'.
     * 
     * @param totalBilledAmount the value of field
     * 'totalBilledAmount'.
     */
    public void setTotalBilledAmount(java.math.BigDecimal totalBilledAmount)
    {
        this._totalBilledAmount = totalBilledAmount;
        
        super.setVoChanged(true);
    } //-- void setTotalBilledAmount(java.math.BigDecimal) 

    /**
     * Method setTotalPaidAmountSets the value of field
     * 'totalPaidAmount'.
     * 
     * @param totalPaidAmount the value of field 'totalPaidAmount'.
     */
    public void setTotalPaidAmount(java.math.BigDecimal totalPaidAmount)
    {
        this._totalPaidAmount = totalPaidAmount;
        
        super.setVoChanged(true);
    } //-- void setTotalPaidAmount(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BillGroupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BillGroupVO) Unmarshaller.unmarshal(edit.common.vo.BillGroupVO.class, reader);
    } //-- edit.common.vo.BillGroupVO unmarshal(java.io.Reader) 

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
