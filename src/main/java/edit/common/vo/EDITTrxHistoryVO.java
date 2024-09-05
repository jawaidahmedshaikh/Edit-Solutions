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
 * Class EDITTrxHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class EDITTrxHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _EDITTrxHistoryPK
     */
    private long _EDITTrxHistoryPK;

    /**
     * keeps track of state for field: _EDITTrxHistoryPK
     */
    private boolean _has_EDITTrxHistoryPK;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _cycleDate
     */
    private java.lang.String _cycleDate;

    /**
     * Field _originalProcessDateTime
     */
    private java.lang.String _originalProcessDateTime;

    /**
     * Field _accountingPendingStatus
     */
    private java.lang.String _accountingPendingStatus;

    /**
     * Field _controlNumber
     */
    private java.lang.String _controlNumber;

    /**
     * Field _releaseDate
     */
    private java.lang.String _releaseDate;

    /**
     * Field _returnDate
     */
    private java.lang.String _returnDate;

    /**
     * Field _correspondenceTypeCT
     */
    private java.lang.String _correspondenceTypeCT;

    /**
     * Field _processID
     */
    private long _processID;

    /**
     * keeps track of state for field: _processID
     */
    private boolean _has_processID;

    /**
     * Field _realTimeInd
     */
    private java.lang.String _realTimeInd;

    /**
     * Field _addressTypeCT
     */
    private java.lang.String _addressTypeCT;

    /**
     * Field _processDateTime
     */
    private java.lang.String _processDateTime;

    /**
     * Field _originalCycleDate
     */
    private java.lang.String _originalCycleDate;

    /**
     * Field _bucketHistoryVOList
     */
    private java.util.Vector _bucketHistoryVOList;

    /**
     * Field _chargeHistoryVOList
     */
    private java.util.Vector _chargeHistoryVOList;

    /**
     * Field _commissionHistoryVOList
     */
    private java.util.Vector _commissionHistoryVOList;

    /**
     * Field _financialHistoryVOList
     */
    private java.util.Vector _financialHistoryVOList;

    /**
     * Field _inSuspenseVOList
     */
    private java.util.Vector _inSuspenseVOList;

    /**
     * Field _investmentHistoryVOList
     */
    private java.util.Vector _investmentHistoryVOList;

    /**
     * Field _reinsuranceHistoryVOList
     */
    private java.util.Vector _reinsuranceHistoryVOList;

    /**
     * Field _withholdingHistoryVOList
     */
    private java.util.Vector _withholdingHistoryVOList;

    /**
     * Field _segmentHistoryVOList
     */
    private java.util.Vector _segmentHistoryVOList;

    /**
     * Field _commissionablePremiumHistoryVOList
     */
    private java.util.Vector _commissionablePremiumHistoryVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITTrxHistoryVO() {
        super();
        _bucketHistoryVOList = new Vector();
        _chargeHistoryVOList = new Vector();
        _commissionHistoryVOList = new Vector();
        _financialHistoryVOList = new Vector();
        _inSuspenseVOList = new Vector();
        _investmentHistoryVOList = new Vector();
        _reinsuranceHistoryVOList = new Vector();
        _withholdingHistoryVOList = new Vector();
        _segmentHistoryVOList = new Vector();
        _commissionablePremiumHistoryVOList = new Vector();
    } //-- edit.common.vo.EDITTrxHistoryVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBucketHistoryVO
     * 
     * @param vBucketHistoryVO
     */
    public void addBucketHistoryVO(edit.common.vo.BucketHistoryVO vBucketHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketHistoryVO.setParentVO(this.getClass(), this);
        _bucketHistoryVOList.addElement(vBucketHistoryVO);
    } //-- void addBucketHistoryVO(edit.common.vo.BucketHistoryVO) 

    /**
     * Method addBucketHistoryVO
     * 
     * @param index
     * @param vBucketHistoryVO
     */
    public void addBucketHistoryVO(int index, edit.common.vo.BucketHistoryVO vBucketHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBucketHistoryVO.setParentVO(this.getClass(), this);
        _bucketHistoryVOList.insertElementAt(vBucketHistoryVO, index);
    } //-- void addBucketHistoryVO(int, edit.common.vo.BucketHistoryVO) 

    /**
     * Method addChargeHistoryVO
     * 
     * @param vChargeHistoryVO
     */
    public void addChargeHistoryVO(edit.common.vo.ChargeHistoryVO vChargeHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChargeHistoryVO.setParentVO(this.getClass(), this);
        _chargeHistoryVOList.addElement(vChargeHistoryVO);
    } //-- void addChargeHistoryVO(edit.common.vo.ChargeHistoryVO) 

    /**
     * Method addChargeHistoryVO
     * 
     * @param index
     * @param vChargeHistoryVO
     */
    public void addChargeHistoryVO(int index, edit.common.vo.ChargeHistoryVO vChargeHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChargeHistoryVO.setParentVO(this.getClass(), this);
        _chargeHistoryVOList.insertElementAt(vChargeHistoryVO, index);
    } //-- void addChargeHistoryVO(int, edit.common.vo.ChargeHistoryVO) 

    /**
     * Method addCommissionHistoryVO
     * 
     * @param vCommissionHistoryVO
     */
    public void addCommissionHistoryVO(edit.common.vo.CommissionHistoryVO vCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionHistoryVO.setParentVO(this.getClass(), this);
        _commissionHistoryVOList.addElement(vCommissionHistoryVO);
    } //-- void addCommissionHistoryVO(edit.common.vo.CommissionHistoryVO) 

    /**
     * Method addCommissionHistoryVO
     * 
     * @param index
     * @param vCommissionHistoryVO
     */
    public void addCommissionHistoryVO(int index, edit.common.vo.CommissionHistoryVO vCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionHistoryVO.setParentVO(this.getClass(), this);
        _commissionHistoryVOList.insertElementAt(vCommissionHistoryVO, index);
    } //-- void addCommissionHistoryVO(int, edit.common.vo.CommissionHistoryVO) 

    /**
     * Method addCommissionablePremiumHistoryVO
     * 
     * @param vCommissionablePremiumHistoryVO
     */
    public void addCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO vCommissionablePremiumHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionablePremiumHistoryVO.setParentVO(this.getClass(), this);
        _commissionablePremiumHistoryVOList.addElement(vCommissionablePremiumHistoryVO);
    } //-- void addCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO) 

    /**
     * Method addCommissionablePremiumHistoryVO
     * 
     * @param index
     * @param vCommissionablePremiumHistoryVO
     */
    public void addCommissionablePremiumHistoryVO(int index, edit.common.vo.CommissionablePremiumHistoryVO vCommissionablePremiumHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionablePremiumHistoryVO.setParentVO(this.getClass(), this);
        _commissionablePremiumHistoryVOList.insertElementAt(vCommissionablePremiumHistoryVO, index);
    } //-- void addCommissionablePremiumHistoryVO(int, edit.common.vo.CommissionablePremiumHistoryVO) 

    /**
     * Method addFinancialHistoryVO
     * 
     * @param vFinancialHistoryVO
     */
    public void addFinancialHistoryVO(edit.common.vo.FinancialHistoryVO vFinancialHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFinancialHistoryVO.setParentVO(this.getClass(), this);
        _financialHistoryVOList.addElement(vFinancialHistoryVO);
    } //-- void addFinancialHistoryVO(edit.common.vo.FinancialHistoryVO) 

    /**
     * Method addFinancialHistoryVO
     * 
     * @param index
     * @param vFinancialHistoryVO
     */
    public void addFinancialHistoryVO(int index, edit.common.vo.FinancialHistoryVO vFinancialHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFinancialHistoryVO.setParentVO(this.getClass(), this);
        _financialHistoryVOList.insertElementAt(vFinancialHistoryVO, index);
    } //-- void addFinancialHistoryVO(int, edit.common.vo.FinancialHistoryVO) 

    /**
     * Method addInSuspenseVO
     * 
     * @param vInSuspenseVO
     */
    public void addInSuspenseVO(edit.common.vo.InSuspenseVO vInSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInSuspenseVO.setParentVO(this.getClass(), this);
        _inSuspenseVOList.addElement(vInSuspenseVO);
    } //-- void addInSuspenseVO(edit.common.vo.InSuspenseVO) 

    /**
     * Method addInSuspenseVO
     * 
     * @param index
     * @param vInSuspenseVO
     */
    public void addInSuspenseVO(int index, edit.common.vo.InSuspenseVO vInSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInSuspenseVO.setParentVO(this.getClass(), this);
        _inSuspenseVOList.insertElementAt(vInSuspenseVO, index);
    } //-- void addInSuspenseVO(int, edit.common.vo.InSuspenseVO) 

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
     * Method addReinsuranceHistoryVO
     * 
     * @param vReinsuranceHistoryVO
     */
    public void addReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO vReinsuranceHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceHistoryVO.setParentVO(this.getClass(), this);
        _reinsuranceHistoryVOList.addElement(vReinsuranceHistoryVO);
    } //-- void addReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO) 

    /**
     * Method addReinsuranceHistoryVO
     * 
     * @param index
     * @param vReinsuranceHistoryVO
     */
    public void addReinsuranceHistoryVO(int index, edit.common.vo.ReinsuranceHistoryVO vReinsuranceHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceHistoryVO.setParentVO(this.getClass(), this);
        _reinsuranceHistoryVOList.insertElementAt(vReinsuranceHistoryVO, index);
    } //-- void addReinsuranceHistoryVO(int, edit.common.vo.ReinsuranceHistoryVO) 

    /**
     * Method addSegmentHistoryVO
     * 
     * @param vSegmentHistoryVO
     */
    public void addSegmentHistoryVO(edit.common.vo.SegmentHistoryVO vSegmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentHistoryVO.setParentVO(this.getClass(), this);
        _segmentHistoryVOList.addElement(vSegmentHistoryVO);
    } //-- void addSegmentHistoryVO(edit.common.vo.SegmentHistoryVO) 

    /**
     * Method addSegmentHistoryVO
     * 
     * @param index
     * @param vSegmentHistoryVO
     */
    public void addSegmentHistoryVO(int index, edit.common.vo.SegmentHistoryVO vSegmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentHistoryVO.setParentVO(this.getClass(), this);
        _segmentHistoryVOList.insertElementAt(vSegmentHistoryVO, index);
    } //-- void addSegmentHistoryVO(int, edit.common.vo.SegmentHistoryVO) 

    /**
     * Method addWithholdingHistoryVO
     * 
     * @param vWithholdingHistoryVO
     */
    public void addWithholdingHistoryVO(edit.common.vo.WithholdingHistoryVO vWithholdingHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingHistoryVO.setParentVO(this.getClass(), this);
        _withholdingHistoryVOList.addElement(vWithholdingHistoryVO);
    } //-- void addWithholdingHistoryVO(edit.common.vo.WithholdingHistoryVO) 

    /**
     * Method addWithholdingHistoryVO
     * 
     * @param index
     * @param vWithholdingHistoryVO
     */
    public void addWithholdingHistoryVO(int index, edit.common.vo.WithholdingHistoryVO vWithholdingHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingHistoryVO.setParentVO(this.getClass(), this);
        _withholdingHistoryVOList.insertElementAt(vWithholdingHistoryVO, index);
    } //-- void addWithholdingHistoryVO(int, edit.common.vo.WithholdingHistoryVO) 

    /**
     * Method enumerateBucketHistoryVO
     */
    public java.util.Enumeration enumerateBucketHistoryVO()
    {
        return _bucketHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateBucketHistoryVO() 

    /**
     * Method enumerateChargeHistoryVO
     */
    public java.util.Enumeration enumerateChargeHistoryVO()
    {
        return _chargeHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateChargeHistoryVO() 

    /**
     * Method enumerateCommissionHistoryVO
     */
    public java.util.Enumeration enumerateCommissionHistoryVO()
    {
        return _commissionHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionHistoryVO() 

    /**
     * Method enumerateCommissionablePremiumHistoryVO
     */
    public java.util.Enumeration enumerateCommissionablePremiumHistoryVO()
    {
        return _commissionablePremiumHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionablePremiumHistoryVO() 

    /**
     * Method enumerateFinancialHistoryVO
     */
    public java.util.Enumeration enumerateFinancialHistoryVO()
    {
        return _financialHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateFinancialHistoryVO() 

    /**
     * Method enumerateInSuspenseVO
     */
    public java.util.Enumeration enumerateInSuspenseVO()
    {
        return _inSuspenseVOList.elements();
    } //-- java.util.Enumeration enumerateInSuspenseVO() 

    /**
     * Method enumerateInvestmentHistoryVO
     */
    public java.util.Enumeration enumerateInvestmentHistoryVO()
    {
        return _investmentHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentHistoryVO() 

    /**
     * Method enumerateReinsuranceHistoryVO
     */
    public java.util.Enumeration enumerateReinsuranceHistoryVO()
    {
        return _reinsuranceHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateReinsuranceHistoryVO() 

    /**
     * Method enumerateSegmentHistoryVO
     */
    public java.util.Enumeration enumerateSegmentHistoryVO()
    {
        return _segmentHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentHistoryVO() 

    /**
     * Method enumerateWithholdingHistoryVO
     */
    public java.util.Enumeration enumerateWithholdingHistoryVO()
    {
        return _withholdingHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateWithholdingHistoryVO() 

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
        
        if (obj instanceof EDITTrxHistoryVO) {
        
            EDITTrxHistoryVO temp = (EDITTrxHistoryVO)obj;
            if (this._EDITTrxHistoryPK != temp._EDITTrxHistoryPK)
                return false;
            if (this._has_EDITTrxHistoryPK != temp._has_EDITTrxHistoryPK)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._cycleDate != null) {
                if (temp._cycleDate == null) return false;
                else if (!(this._cycleDate.equals(temp._cycleDate))) 
                    return false;
            }
            else if (temp._cycleDate != null)
                return false;
            if (this._originalProcessDateTime != null) {
                if (temp._originalProcessDateTime == null) return false;
                else if (!(this._originalProcessDateTime.equals(temp._originalProcessDateTime))) 
                    return false;
            }
            else if (temp._originalProcessDateTime != null)
                return false;
            if (this._accountingPendingStatus != null) {
                if (temp._accountingPendingStatus == null) return false;
                else if (!(this._accountingPendingStatus.equals(temp._accountingPendingStatus))) 
                    return false;
            }
            else if (temp._accountingPendingStatus != null)
                return false;
            if (this._controlNumber != null) {
                if (temp._controlNumber == null) return false;
                else if (!(this._controlNumber.equals(temp._controlNumber))) 
                    return false;
            }
            else if (temp._controlNumber != null)
                return false;
            if (this._releaseDate != null) {
                if (temp._releaseDate == null) return false;
                else if (!(this._releaseDate.equals(temp._releaseDate))) 
                    return false;
            }
            else if (temp._releaseDate != null)
                return false;
            if (this._returnDate != null) {
                if (temp._returnDate == null) return false;
                else if (!(this._returnDate.equals(temp._returnDate))) 
                    return false;
            }
            else if (temp._returnDate != null)
                return false;
            if (this._correspondenceTypeCT != null) {
                if (temp._correspondenceTypeCT == null) return false;
                else if (!(this._correspondenceTypeCT.equals(temp._correspondenceTypeCT))) 
                    return false;
            }
            else if (temp._correspondenceTypeCT != null)
                return false;
            if (this._processID != temp._processID)
                return false;
            if (this._has_processID != temp._has_processID)
                return false;
            if (this._realTimeInd != null) {
                if (temp._realTimeInd == null) return false;
                else if (!(this._realTimeInd.equals(temp._realTimeInd))) 
                    return false;
            }
            else if (temp._realTimeInd != null)
                return false;
            if (this._addressTypeCT != null) {
                if (temp._addressTypeCT == null) return false;
                else if (!(this._addressTypeCT.equals(temp._addressTypeCT))) 
                    return false;
            }
            else if (temp._addressTypeCT != null)
                return false;
            if (this._processDateTime != null) {
                if (temp._processDateTime == null) return false;
                else if (!(this._processDateTime.equals(temp._processDateTime))) 
                    return false;
            }
            else if (temp._processDateTime != null)
                return false;
            if (this._originalCycleDate != null) {
                if (temp._originalCycleDate == null) return false;
                else if (!(this._originalCycleDate.equals(temp._originalCycleDate))) 
                    return false;
            }
            else if (temp._originalCycleDate != null)
                return false;
            if (this._bucketHistoryVOList != null) {
                if (temp._bucketHistoryVOList == null) return false;
                else if (!(this._bucketHistoryVOList.equals(temp._bucketHistoryVOList))) 
                    return false;
            }
            else if (temp._bucketHistoryVOList != null)
                return false;
            if (this._chargeHistoryVOList != null) {
                if (temp._chargeHistoryVOList == null) return false;
                else if (!(this._chargeHistoryVOList.equals(temp._chargeHistoryVOList))) 
                    return false;
            }
            else if (temp._chargeHistoryVOList != null)
                return false;
            if (this._commissionHistoryVOList != null) {
                if (temp._commissionHistoryVOList == null) return false;
                else if (!(this._commissionHistoryVOList.equals(temp._commissionHistoryVOList))) 
                    return false;
            }
            else if (temp._commissionHistoryVOList != null)
                return false;
            if (this._financialHistoryVOList != null) {
                if (temp._financialHistoryVOList == null) return false;
                else if (!(this._financialHistoryVOList.equals(temp._financialHistoryVOList))) 
                    return false;
            }
            else if (temp._financialHistoryVOList != null)
                return false;
            if (this._inSuspenseVOList != null) {
                if (temp._inSuspenseVOList == null) return false;
                else if (!(this._inSuspenseVOList.equals(temp._inSuspenseVOList))) 
                    return false;
            }
            else if (temp._inSuspenseVOList != null)
                return false;
            if (this._investmentHistoryVOList != null) {
                if (temp._investmentHistoryVOList == null) return false;
                else if (!(this._investmentHistoryVOList.equals(temp._investmentHistoryVOList))) 
                    return false;
            }
            else if (temp._investmentHistoryVOList != null)
                return false;
            if (this._reinsuranceHistoryVOList != null) {
                if (temp._reinsuranceHistoryVOList == null) return false;
                else if (!(this._reinsuranceHistoryVOList.equals(temp._reinsuranceHistoryVOList))) 
                    return false;
            }
            else if (temp._reinsuranceHistoryVOList != null)
                return false;
            if (this._withholdingHistoryVOList != null) {
                if (temp._withholdingHistoryVOList == null) return false;
                else if (!(this._withholdingHistoryVOList.equals(temp._withholdingHistoryVOList))) 
                    return false;
            }
            else if (temp._withholdingHistoryVOList != null)
                return false;
            if (this._segmentHistoryVOList != null) {
                if (temp._segmentHistoryVOList == null) return false;
                else if (!(this._segmentHistoryVOList.equals(temp._segmentHistoryVOList))) 
                    return false;
            }
            else if (temp._segmentHistoryVOList != null)
                return false;
            if (this._commissionablePremiumHistoryVOList != null) {
                if (temp._commissionablePremiumHistoryVOList == null) return false;
                else if (!(this._commissionablePremiumHistoryVOList.equals(temp._commissionablePremiumHistoryVOList))) 
                    return false;
            }
            else if (temp._commissionablePremiumHistoryVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountingPendingStatusReturns the value of field
     * 'accountingPendingStatus'.
     * 
     * @return the value of field 'accountingPendingStatus'.
     */
    public java.lang.String getAccountingPendingStatus()
    {
        return this._accountingPendingStatus;
    } //-- java.lang.String getAccountingPendingStatus() 

    /**
     * Method getAddressTypeCTReturns the value of field
     * 'addressTypeCT'.
     * 
     * @return the value of field 'addressTypeCT'.
     */
    public java.lang.String getAddressTypeCT()
    {
        return this._addressTypeCT;
    } //-- java.lang.String getAddressTypeCT() 

    /**
     * Method getBucketHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BucketHistoryVO getBucketHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BucketHistoryVO) _bucketHistoryVOList.elementAt(index);
    } //-- edit.common.vo.BucketHistoryVO getBucketHistoryVO(int) 

    /**
     * Method getBucketHistoryVO
     */
    public edit.common.vo.BucketHistoryVO[] getBucketHistoryVO()
    {
        int size = _bucketHistoryVOList.size();
        edit.common.vo.BucketHistoryVO[] mArray = new edit.common.vo.BucketHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BucketHistoryVO) _bucketHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BucketHistoryVO[] getBucketHistoryVO() 

    /**
     * Method getBucketHistoryVOCount
     */
    public int getBucketHistoryVOCount()
    {
        return _bucketHistoryVOList.size();
    } //-- int getBucketHistoryVOCount() 

    /**
     * Method getChargeHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.ChargeHistoryVO getChargeHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _chargeHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ChargeHistoryVO) _chargeHistoryVOList.elementAt(index);
    } //-- edit.common.vo.ChargeHistoryVO getChargeHistoryVO(int) 

    /**
     * Method getChargeHistoryVO
     */
    public edit.common.vo.ChargeHistoryVO[] getChargeHistoryVO()
    {
        int size = _chargeHistoryVOList.size();
        edit.common.vo.ChargeHistoryVO[] mArray = new edit.common.vo.ChargeHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ChargeHistoryVO) _chargeHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ChargeHistoryVO[] getChargeHistoryVO() 

    /**
     * Method getChargeHistoryVOCount
     */
    public int getChargeHistoryVOCount()
    {
        return _chargeHistoryVOList.size();
    } //-- int getChargeHistoryVOCount() 

    /**
     * Method getCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionHistoryVO getCommissionHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionHistoryVO) _commissionHistoryVOList.elementAt(index);
    } //-- edit.common.vo.CommissionHistoryVO getCommissionHistoryVO(int) 

    /**
     * Method getCommissionHistoryVO
     */
    public edit.common.vo.CommissionHistoryVO[] getCommissionHistoryVO()
    {
        int size = _commissionHistoryVOList.size();
        edit.common.vo.CommissionHistoryVO[] mArray = new edit.common.vo.CommissionHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionHistoryVO) _commissionHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionHistoryVO[] getCommissionHistoryVO() 

    /**
     * Method getCommissionHistoryVOCount
     */
    public int getCommissionHistoryVOCount()
    {
        return _commissionHistoryVOList.size();
    } //-- int getCommissionHistoryVOCount() 

    /**
     * Method getCommissionablePremiumHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionablePremiumHistoryVO getCommissionablePremiumHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionablePremiumHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionablePremiumHistoryVO) _commissionablePremiumHistoryVOList.elementAt(index);
    } //-- edit.common.vo.CommissionablePremiumHistoryVO getCommissionablePremiumHistoryVO(int) 

    /**
     * Method getCommissionablePremiumHistoryVO
     */
    public edit.common.vo.CommissionablePremiumHistoryVO[] getCommissionablePremiumHistoryVO()
    {
        int size = _commissionablePremiumHistoryVOList.size();
        edit.common.vo.CommissionablePremiumHistoryVO[] mArray = new edit.common.vo.CommissionablePremiumHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionablePremiumHistoryVO) _commissionablePremiumHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionablePremiumHistoryVO[] getCommissionablePremiumHistoryVO() 

    /**
     * Method getCommissionablePremiumHistoryVOCount
     */
    public int getCommissionablePremiumHistoryVOCount()
    {
        return _commissionablePremiumHistoryVOList.size();
    } //-- int getCommissionablePremiumHistoryVOCount() 

    /**
     * Method getControlNumberReturns the value of field
     * 'controlNumber'.
     * 
     * @return the value of field 'controlNumber'.
     */
    public java.lang.String getControlNumber()
    {
        return this._controlNumber;
    } //-- java.lang.String getControlNumber() 

    /**
     * Method getCorrespondenceTypeCTReturns the value of field
     * 'correspondenceTypeCT'.
     * 
     * @return the value of field 'correspondenceTypeCT'.
     */
    public java.lang.String getCorrespondenceTypeCT()
    {
        return this._correspondenceTypeCT;
    } //-- java.lang.String getCorrespondenceTypeCT() 

    /**
     * Method getCycleDateReturns the value of field 'cycleDate'.
     * 
     * @return the value of field 'cycleDate'.
     */
    public java.lang.String getCycleDate()
    {
        return this._cycleDate;
    } //-- java.lang.String getCycleDate() 

    /**
     * Method getEDITTrxFKReturns the value of field 'EDITTrxFK'.
     * 
     * @return the value of field 'EDITTrxFK'.
     */
    public long getEDITTrxFK()
    {
        return this._EDITTrxFK;
    } //-- long getEDITTrxFK() 

    /**
     * Method getEDITTrxHistoryPKReturns the value of field
     * 'EDITTrxHistoryPK'.
     * 
     * @return the value of field 'EDITTrxHistoryPK'.
     */
    public long getEDITTrxHistoryPK()
    {
        return this._EDITTrxHistoryPK;
    } //-- long getEDITTrxHistoryPK() 

    /**
     * Method getFinancialHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.FinancialHistoryVO getFinancialHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _financialHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FinancialHistoryVO) _financialHistoryVOList.elementAt(index);
    } //-- edit.common.vo.FinancialHistoryVO getFinancialHistoryVO(int) 

    /**
     * Method getFinancialHistoryVO
     */
    public edit.common.vo.FinancialHistoryVO[] getFinancialHistoryVO()
    {
        int size = _financialHistoryVOList.size();
        edit.common.vo.FinancialHistoryVO[] mArray = new edit.common.vo.FinancialHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FinancialHistoryVO) _financialHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FinancialHistoryVO[] getFinancialHistoryVO() 

    /**
     * Method getFinancialHistoryVOCount
     */
    public int getFinancialHistoryVOCount()
    {
        return _financialHistoryVOList.size();
    } //-- int getFinancialHistoryVOCount() 

    /**
     * Method getInSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.InSuspenseVO getInSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _inSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InSuspenseVO) _inSuspenseVOList.elementAt(index);
    } //-- edit.common.vo.InSuspenseVO getInSuspenseVO(int) 

    /**
     * Method getInSuspenseVO
     */
    public edit.common.vo.InSuspenseVO[] getInSuspenseVO()
    {
        int size = _inSuspenseVOList.size();
        edit.common.vo.InSuspenseVO[] mArray = new edit.common.vo.InSuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InSuspenseVO) _inSuspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InSuspenseVO[] getInSuspenseVO() 

    /**
     * Method getInSuspenseVOCount
     */
    public int getInSuspenseVOCount()
    {
        return _inSuspenseVOList.size();
    } //-- int getInSuspenseVOCount() 

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
     * Method getOriginalCycleDateReturns the value of field
     * 'originalCycleDate'.
     * 
     * @return the value of field 'originalCycleDate'.
     */
    public java.lang.String getOriginalCycleDate()
    {
        return this._originalCycleDate;
    } //-- java.lang.String getOriginalCycleDate() 

    /**
     * Method getOriginalProcessDateTimeReturns the value of field
     * 'originalProcessDateTime'.
     * 
     * @return the value of field 'originalProcessDateTime'.
     */
    public java.lang.String getOriginalProcessDateTime()
    {
        return this._originalProcessDateTime;
    } //-- java.lang.String getOriginalProcessDateTime() 

    /**
     * Method getProcessDateTimeReturns the value of field
     * 'processDateTime'.
     * 
     * @return the value of field 'processDateTime'.
     */
    public java.lang.String getProcessDateTime()
    {
        return this._processDateTime;
    } //-- java.lang.String getProcessDateTime() 

    /**
     * Method getProcessIDReturns the value of field 'processID'.
     * 
     * @return the value of field 'processID'.
     */
    public long getProcessID()
    {
        return this._processID;
    } //-- long getProcessID() 

    /**
     * Method getRealTimeIndReturns the value of field
     * 'realTimeInd'.
     * 
     * @return the value of field 'realTimeInd'.
     */
    public java.lang.String getRealTimeInd()
    {
        return this._realTimeInd;
    } //-- java.lang.String getRealTimeInd() 

    /**
     * Method getReinsuranceHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceHistoryVO getReinsuranceHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ReinsuranceHistoryVO) _reinsuranceHistoryVOList.elementAt(index);
    } //-- edit.common.vo.ReinsuranceHistoryVO getReinsuranceHistoryVO(int) 

    /**
     * Method getReinsuranceHistoryVO
     */
    public edit.common.vo.ReinsuranceHistoryVO[] getReinsuranceHistoryVO()
    {
        int size = _reinsuranceHistoryVOList.size();
        edit.common.vo.ReinsuranceHistoryVO[] mArray = new edit.common.vo.ReinsuranceHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ReinsuranceHistoryVO) _reinsuranceHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ReinsuranceHistoryVO[] getReinsuranceHistoryVO() 

    /**
     * Method getReinsuranceHistoryVOCount
     */
    public int getReinsuranceHistoryVOCount()
    {
        return _reinsuranceHistoryVOList.size();
    } //-- int getReinsuranceHistoryVOCount() 

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
     * Method getReturnDateReturns the value of field 'returnDate'.
     * 
     * @return the value of field 'returnDate'.
     */
    public java.lang.String getReturnDate()
    {
        return this._returnDate;
    } //-- java.lang.String getReturnDate() 

    /**
     * Method getSegmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentHistoryVO getSegmentHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SegmentHistoryVO) _segmentHistoryVOList.elementAt(index);
    } //-- edit.common.vo.SegmentHistoryVO getSegmentHistoryVO(int) 

    /**
     * Method getSegmentHistoryVO
     */
    public edit.common.vo.SegmentHistoryVO[] getSegmentHistoryVO()
    {
        int size = _segmentHistoryVOList.size();
        edit.common.vo.SegmentHistoryVO[] mArray = new edit.common.vo.SegmentHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SegmentHistoryVO) _segmentHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SegmentHistoryVO[] getSegmentHistoryVO() 

    /**
     * Method getSegmentHistoryVOCount
     */
    public int getSegmentHistoryVOCount()
    {
        return _segmentHistoryVOList.size();
    } //-- int getSegmentHistoryVOCount() 

    /**
     * Method getWithholdingHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingHistoryVO getWithholdingHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.WithholdingHistoryVO) _withholdingHistoryVOList.elementAt(index);
    } //-- edit.common.vo.WithholdingHistoryVO getWithholdingHistoryVO(int) 

    /**
     * Method getWithholdingHistoryVO
     */
    public edit.common.vo.WithholdingHistoryVO[] getWithholdingHistoryVO()
    {
        int size = _withholdingHistoryVOList.size();
        edit.common.vo.WithholdingHistoryVO[] mArray = new edit.common.vo.WithholdingHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.WithholdingHistoryVO) _withholdingHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.WithholdingHistoryVO[] getWithholdingHistoryVO() 

    /**
     * Method getWithholdingHistoryVOCount
     */
    public int getWithholdingHistoryVOCount()
    {
        return _withholdingHistoryVOList.size();
    } //-- int getWithholdingHistoryVOCount() 

    /**
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasEDITTrxHistoryPK
     */
    public boolean hasEDITTrxHistoryPK()
    {
        return this._has_EDITTrxHistoryPK;
    } //-- boolean hasEDITTrxHistoryPK() 

    /**
     * Method hasProcessID
     */
    public boolean hasProcessID()
    {
        return this._has_processID;
    } //-- boolean hasProcessID() 

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
     * Method removeAllBucketHistoryVO
     */
    public void removeAllBucketHistoryVO()
    {
        _bucketHistoryVOList.removeAllElements();
    } //-- void removeAllBucketHistoryVO() 

    /**
     * Method removeAllChargeHistoryVO
     */
    public void removeAllChargeHistoryVO()
    {
        _chargeHistoryVOList.removeAllElements();
    } //-- void removeAllChargeHistoryVO() 

    /**
     * Method removeAllCommissionHistoryVO
     */
    public void removeAllCommissionHistoryVO()
    {
        _commissionHistoryVOList.removeAllElements();
    } //-- void removeAllCommissionHistoryVO() 

    /**
     * Method removeAllCommissionablePremiumHistoryVO
     */
    public void removeAllCommissionablePremiumHistoryVO()
    {
        _commissionablePremiumHistoryVOList.removeAllElements();
    } //-- void removeAllCommissionablePremiumHistoryVO() 

    /**
     * Method removeAllFinancialHistoryVO
     */
    public void removeAllFinancialHistoryVO()
    {
        _financialHistoryVOList.removeAllElements();
    } //-- void removeAllFinancialHistoryVO() 

    /**
     * Method removeAllInSuspenseVO
     */
    public void removeAllInSuspenseVO()
    {
        _inSuspenseVOList.removeAllElements();
    } //-- void removeAllInSuspenseVO() 

    /**
     * Method removeAllInvestmentHistoryVO
     */
    public void removeAllInvestmentHistoryVO()
    {
        _investmentHistoryVOList.removeAllElements();
    } //-- void removeAllInvestmentHistoryVO() 

    /**
     * Method removeAllReinsuranceHistoryVO
     */
    public void removeAllReinsuranceHistoryVO()
    {
        _reinsuranceHistoryVOList.removeAllElements();
    } //-- void removeAllReinsuranceHistoryVO() 

    /**
     * Method removeAllSegmentHistoryVO
     */
    public void removeAllSegmentHistoryVO()
    {
        _segmentHistoryVOList.removeAllElements();
    } //-- void removeAllSegmentHistoryVO() 

    /**
     * Method removeAllWithholdingHistoryVO
     */
    public void removeAllWithholdingHistoryVO()
    {
        _withholdingHistoryVOList.removeAllElements();
    } //-- void removeAllWithholdingHistoryVO() 

    /**
     * Method removeBucketHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BucketHistoryVO removeBucketHistoryVO(int index)
    {
        java.lang.Object obj = _bucketHistoryVOList.elementAt(index);
        _bucketHistoryVOList.removeElementAt(index);
        return (edit.common.vo.BucketHistoryVO) obj;
    } //-- edit.common.vo.BucketHistoryVO removeBucketHistoryVO(int) 

    /**
     * Method removeChargeHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.ChargeHistoryVO removeChargeHistoryVO(int index)
    {
        java.lang.Object obj = _chargeHistoryVOList.elementAt(index);
        _chargeHistoryVOList.removeElementAt(index);
        return (edit.common.vo.ChargeHistoryVO) obj;
    } //-- edit.common.vo.ChargeHistoryVO removeChargeHistoryVO(int) 

    /**
     * Method removeCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionHistoryVO removeCommissionHistoryVO(int index)
    {
        java.lang.Object obj = _commissionHistoryVOList.elementAt(index);
        _commissionHistoryVOList.removeElementAt(index);
        return (edit.common.vo.CommissionHistoryVO) obj;
    } //-- edit.common.vo.CommissionHistoryVO removeCommissionHistoryVO(int) 

    /**
     * Method removeCommissionablePremiumHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionablePremiumHistoryVO removeCommissionablePremiumHistoryVO(int index)
    {
        java.lang.Object obj = _commissionablePremiumHistoryVOList.elementAt(index);
        _commissionablePremiumHistoryVOList.removeElementAt(index);
        return (edit.common.vo.CommissionablePremiumHistoryVO) obj;
    } //-- edit.common.vo.CommissionablePremiumHistoryVO removeCommissionablePremiumHistoryVO(int) 

    /**
     * Method removeFinancialHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.FinancialHistoryVO removeFinancialHistoryVO(int index)
    {
        java.lang.Object obj = _financialHistoryVOList.elementAt(index);
        _financialHistoryVOList.removeElementAt(index);
        return (edit.common.vo.FinancialHistoryVO) obj;
    } //-- edit.common.vo.FinancialHistoryVO removeFinancialHistoryVO(int) 

    /**
     * Method removeInSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.InSuspenseVO removeInSuspenseVO(int index)
    {
        java.lang.Object obj = _inSuspenseVOList.elementAt(index);
        _inSuspenseVOList.removeElementAt(index);
        return (edit.common.vo.InSuspenseVO) obj;
    } //-- edit.common.vo.InSuspenseVO removeInSuspenseVO(int) 

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
     * Method removeReinsuranceHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceHistoryVO removeReinsuranceHistoryVO(int index)
    {
        java.lang.Object obj = _reinsuranceHistoryVOList.elementAt(index);
        _reinsuranceHistoryVOList.removeElementAt(index);
        return (edit.common.vo.ReinsuranceHistoryVO) obj;
    } //-- edit.common.vo.ReinsuranceHistoryVO removeReinsuranceHistoryVO(int) 

    /**
     * Method removeSegmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentHistoryVO removeSegmentHistoryVO(int index)
    {
        java.lang.Object obj = _segmentHistoryVOList.elementAt(index);
        _segmentHistoryVOList.removeElementAt(index);
        return (edit.common.vo.SegmentHistoryVO) obj;
    } //-- edit.common.vo.SegmentHistoryVO removeSegmentHistoryVO(int) 

    /**
     * Method removeWithholdingHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingHistoryVO removeWithholdingHistoryVO(int index)
    {
        java.lang.Object obj = _withholdingHistoryVOList.elementAt(index);
        _withholdingHistoryVOList.removeElementAt(index);
        return (edit.common.vo.WithholdingHistoryVO) obj;
    } //-- edit.common.vo.WithholdingHistoryVO removeWithholdingHistoryVO(int) 

    /**
     * Method setAccountingPendingStatusSets the value of field
     * 'accountingPendingStatus'.
     * 
     * @param accountingPendingStatus the value of field
     * 'accountingPendingStatus'.
     */
    public void setAccountingPendingStatus(java.lang.String accountingPendingStatus)
    {
        this._accountingPendingStatus = accountingPendingStatus;
        
        super.setVoChanged(true);
    } //-- void setAccountingPendingStatus(java.lang.String) 

    /**
     * Method setAddressTypeCTSets the value of field
     * 'addressTypeCT'.
     * 
     * @param addressTypeCT the value of field 'addressTypeCT'.
     */
    public void setAddressTypeCT(java.lang.String addressTypeCT)
    {
        this._addressTypeCT = addressTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAddressTypeCT(java.lang.String) 

    /**
     * Method setBucketHistoryVO
     * 
     * @param index
     * @param vBucketHistoryVO
     */
    public void setBucketHistoryVO(int index, edit.common.vo.BucketHistoryVO vBucketHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bucketHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBucketHistoryVO.setParentVO(this.getClass(), this);
        _bucketHistoryVOList.setElementAt(vBucketHistoryVO, index);
    } //-- void setBucketHistoryVO(int, edit.common.vo.BucketHistoryVO) 

    /**
     * Method setBucketHistoryVO
     * 
     * @param bucketHistoryVOArray
     */
    public void setBucketHistoryVO(edit.common.vo.BucketHistoryVO[] bucketHistoryVOArray)
    {
        //-- copy array
        _bucketHistoryVOList.removeAllElements();
        for (int i = 0; i < bucketHistoryVOArray.length; i++) {
            bucketHistoryVOArray[i].setParentVO(this.getClass(), this);
            _bucketHistoryVOList.addElement(bucketHistoryVOArray[i]);
        }
    } //-- void setBucketHistoryVO(edit.common.vo.BucketHistoryVO) 

    /**
     * Method setChargeHistoryVO
     * 
     * @param index
     * @param vChargeHistoryVO
     */
    public void setChargeHistoryVO(int index, edit.common.vo.ChargeHistoryVO vChargeHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _chargeHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vChargeHistoryVO.setParentVO(this.getClass(), this);
        _chargeHistoryVOList.setElementAt(vChargeHistoryVO, index);
    } //-- void setChargeHistoryVO(int, edit.common.vo.ChargeHistoryVO) 

    /**
     * Method setChargeHistoryVO
     * 
     * @param chargeHistoryVOArray
     */
    public void setChargeHistoryVO(edit.common.vo.ChargeHistoryVO[] chargeHistoryVOArray)
    {
        //-- copy array
        _chargeHistoryVOList.removeAllElements();
        for (int i = 0; i < chargeHistoryVOArray.length; i++) {
            chargeHistoryVOArray[i].setParentVO(this.getClass(), this);
            _chargeHistoryVOList.addElement(chargeHistoryVOArray[i]);
        }
    } //-- void setChargeHistoryVO(edit.common.vo.ChargeHistoryVO) 

    /**
     * Method setCommissionHistoryVO
     * 
     * @param index
     * @param vCommissionHistoryVO
     */
    public void setCommissionHistoryVO(int index, edit.common.vo.CommissionHistoryVO vCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionHistoryVO.setParentVO(this.getClass(), this);
        _commissionHistoryVOList.setElementAt(vCommissionHistoryVO, index);
    } //-- void setCommissionHistoryVO(int, edit.common.vo.CommissionHistoryVO) 

    /**
     * Method setCommissionHistoryVO
     * 
     * @param commissionHistoryVOArray
     */
    public void setCommissionHistoryVO(edit.common.vo.CommissionHistoryVO[] commissionHistoryVOArray)
    {
        //-- copy array
        _commissionHistoryVOList.removeAllElements();
        for (int i = 0; i < commissionHistoryVOArray.length; i++) {
            commissionHistoryVOArray[i].setParentVO(this.getClass(), this);
            _commissionHistoryVOList.addElement(commissionHistoryVOArray[i]);
        }
    } //-- void setCommissionHistoryVO(edit.common.vo.CommissionHistoryVO) 

    /**
     * Method setCommissionablePremiumHistoryVO
     * 
     * @param index
     * @param vCommissionablePremiumHistoryVO
     */
    public void setCommissionablePremiumHistoryVO(int index, edit.common.vo.CommissionablePremiumHistoryVO vCommissionablePremiumHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionablePremiumHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionablePremiumHistoryVO.setParentVO(this.getClass(), this);
        _commissionablePremiumHistoryVOList.setElementAt(vCommissionablePremiumHistoryVO, index);
    } //-- void setCommissionablePremiumHistoryVO(int, edit.common.vo.CommissionablePremiumHistoryVO) 

    /**
     * Method setCommissionablePremiumHistoryVO
     * 
     * @param commissionablePremiumHistoryVOArray
     */
    public void setCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO[] commissionablePremiumHistoryVOArray)
    {
        //-- copy array
        _commissionablePremiumHistoryVOList.removeAllElements();
        for (int i = 0; i < commissionablePremiumHistoryVOArray.length; i++) {
            commissionablePremiumHistoryVOArray[i].setParentVO(this.getClass(), this);
            _commissionablePremiumHistoryVOList.addElement(commissionablePremiumHistoryVOArray[i]);
        }
    } //-- void setCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO) 

    /**
     * Method setControlNumberSets the value of field
     * 'controlNumber'.
     * 
     * @param controlNumber the value of field 'controlNumber'.
     */
    public void setControlNumber(java.lang.String controlNumber)
    {
        this._controlNumber = controlNumber;
        
        super.setVoChanged(true);
    } //-- void setControlNumber(java.lang.String) 

    /**
     * Method setCorrespondenceTypeCTSets the value of field
     * 'correspondenceTypeCT'.
     * 
     * @param correspondenceTypeCT the value of field
     * 'correspondenceTypeCT'.
     */
    public void setCorrespondenceTypeCT(java.lang.String correspondenceTypeCT)
    {
        this._correspondenceTypeCT = correspondenceTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCorrespondenceTypeCT(java.lang.String) 

    /**
     * Method setCycleDateSets the value of field 'cycleDate'.
     * 
     * @param cycleDate the value of field 'cycleDate'.
     */
    public void setCycleDate(java.lang.String cycleDate)
    {
        this._cycleDate = cycleDate;
        
        super.setVoChanged(true);
    } //-- void setCycleDate(java.lang.String) 

    /**
     * Method setEDITTrxFKSets the value of field 'EDITTrxFK'.
     * 
     * @param EDITTrxFK the value of field 'EDITTrxFK'.
     */
    public void setEDITTrxFK(long EDITTrxFK)
    {
        this._EDITTrxFK = EDITTrxFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxFK = true;
    } //-- void setEDITTrxFK(long) 

    /**
     * Method setEDITTrxHistoryPKSets the value of field
     * 'EDITTrxHistoryPK'.
     * 
     * @param EDITTrxHistoryPK the value of field 'EDITTrxHistoryPK'
     */
    public void setEDITTrxHistoryPK(long EDITTrxHistoryPK)
    {
        this._EDITTrxHistoryPK = EDITTrxHistoryPK;
        
        super.setVoChanged(true);
        this._has_EDITTrxHistoryPK = true;
    } //-- void setEDITTrxHistoryPK(long) 

    /**
     * Method setFinancialHistoryVO
     * 
     * @param index
     * @param vFinancialHistoryVO
     */
    public void setFinancialHistoryVO(int index, edit.common.vo.FinancialHistoryVO vFinancialHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _financialHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFinancialHistoryVO.setParentVO(this.getClass(), this);
        _financialHistoryVOList.setElementAt(vFinancialHistoryVO, index);
    } //-- void setFinancialHistoryVO(int, edit.common.vo.FinancialHistoryVO) 

    /**
     * Method setFinancialHistoryVO
     * 
     * @param financialHistoryVOArray
     */
    public void setFinancialHistoryVO(edit.common.vo.FinancialHistoryVO[] financialHistoryVOArray)
    {
        //-- copy array
        _financialHistoryVOList.removeAllElements();
        for (int i = 0; i < financialHistoryVOArray.length; i++) {
            financialHistoryVOArray[i].setParentVO(this.getClass(), this);
            _financialHistoryVOList.addElement(financialHistoryVOArray[i]);
        }
    } //-- void setFinancialHistoryVO(edit.common.vo.FinancialHistoryVO) 

    /**
     * Method setInSuspenseVO
     * 
     * @param index
     * @param vInSuspenseVO
     */
    public void setInSuspenseVO(int index, edit.common.vo.InSuspenseVO vInSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _inSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInSuspenseVO.setParentVO(this.getClass(), this);
        _inSuspenseVOList.setElementAt(vInSuspenseVO, index);
    } //-- void setInSuspenseVO(int, edit.common.vo.InSuspenseVO) 

    /**
     * Method setInSuspenseVO
     * 
     * @param inSuspenseVOArray
     */
    public void setInSuspenseVO(edit.common.vo.InSuspenseVO[] inSuspenseVOArray)
    {
        //-- copy array
        _inSuspenseVOList.removeAllElements();
        for (int i = 0; i < inSuspenseVOArray.length; i++) {
            inSuspenseVOArray[i].setParentVO(this.getClass(), this);
            _inSuspenseVOList.addElement(inSuspenseVOArray[i]);
        }
    } //-- void setInSuspenseVO(edit.common.vo.InSuspenseVO) 

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
     * Method setOriginalCycleDateSets the value of field
     * 'originalCycleDate'.
     * 
     * @param originalCycleDate the value of field
     * 'originalCycleDate'.
     */
    public void setOriginalCycleDate(java.lang.String originalCycleDate)
    {
        this._originalCycleDate = originalCycleDate;
        
        super.setVoChanged(true);
    } //-- void setOriginalCycleDate(java.lang.String) 

    /**
     * Method setOriginalProcessDateTimeSets the value of field
     * 'originalProcessDateTime'.
     * 
     * @param originalProcessDateTime the value of field
     * 'originalProcessDateTime'.
     */
    public void setOriginalProcessDateTime(java.lang.String originalProcessDateTime)
    {
        this._originalProcessDateTime = originalProcessDateTime;
        
        super.setVoChanged(true);
    } //-- void setOriginalProcessDateTime(java.lang.String) 

    /**
     * Method setProcessDateTimeSets the value of field
     * 'processDateTime'.
     * 
     * @param processDateTime the value of field 'processDateTime'.
     */
    public void setProcessDateTime(java.lang.String processDateTime)
    {
        this._processDateTime = processDateTime;
        
        super.setVoChanged(true);
    } //-- void setProcessDateTime(java.lang.String) 

    /**
     * Method setProcessIDSets the value of field 'processID'.
     * 
     * @param processID the value of field 'processID'.
     */
    public void setProcessID(long processID)
    {
        this._processID = processID;
        
        super.setVoChanged(true);
        this._has_processID = true;
    } //-- void setProcessID(long) 

    /**
     * Method setRealTimeIndSets the value of field 'realTimeInd'.
     * 
     * @param realTimeInd the value of field 'realTimeInd'.
     */
    public void setRealTimeInd(java.lang.String realTimeInd)
    {
        this._realTimeInd = realTimeInd;
        
        super.setVoChanged(true);
    } //-- void setRealTimeInd(java.lang.String) 

    /**
     * Method setReinsuranceHistoryVO
     * 
     * @param index
     * @param vReinsuranceHistoryVO
     */
    public void setReinsuranceHistoryVO(int index, edit.common.vo.ReinsuranceHistoryVO vReinsuranceHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vReinsuranceHistoryVO.setParentVO(this.getClass(), this);
        _reinsuranceHistoryVOList.setElementAt(vReinsuranceHistoryVO, index);
    } //-- void setReinsuranceHistoryVO(int, edit.common.vo.ReinsuranceHistoryVO) 

    /**
     * Method setReinsuranceHistoryVO
     * 
     * @param reinsuranceHistoryVOArray
     */
    public void setReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO[] reinsuranceHistoryVOArray)
    {
        //-- copy array
        _reinsuranceHistoryVOList.removeAllElements();
        for (int i = 0; i < reinsuranceHistoryVOArray.length; i++) {
            reinsuranceHistoryVOArray[i].setParentVO(this.getClass(), this);
            _reinsuranceHistoryVOList.addElement(reinsuranceHistoryVOArray[i]);
        }
    } //-- void setReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO) 

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
     * Method setReturnDateSets the value of field 'returnDate'.
     * 
     * @param returnDate the value of field 'returnDate'.
     */
    public void setReturnDate(java.lang.String returnDate)
    {
        this._returnDate = returnDate;
        
        super.setVoChanged(true);
    } //-- void setReturnDate(java.lang.String) 

    /**
     * Method setSegmentHistoryVO
     * 
     * @param index
     * @param vSegmentHistoryVO
     */
    public void setSegmentHistoryVO(int index, edit.common.vo.SegmentHistoryVO vSegmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSegmentHistoryVO.setParentVO(this.getClass(), this);
        _segmentHistoryVOList.setElementAt(vSegmentHistoryVO, index);
    } //-- void setSegmentHistoryVO(int, edit.common.vo.SegmentHistoryVO) 

    /**
     * Method setSegmentHistoryVO
     * 
     * @param segmentHistoryVOArray
     */
    public void setSegmentHistoryVO(edit.common.vo.SegmentHistoryVO[] segmentHistoryVOArray)
    {
        //-- copy array
        _segmentHistoryVOList.removeAllElements();
        for (int i = 0; i < segmentHistoryVOArray.length; i++) {
            segmentHistoryVOArray[i].setParentVO(this.getClass(), this);
            _segmentHistoryVOList.addElement(segmentHistoryVOArray[i]);
        }
    } //-- void setSegmentHistoryVO(edit.common.vo.SegmentHistoryVO) 

    /**
     * Method setWithholdingHistoryVO
     * 
     * @param index
     * @param vWithholdingHistoryVO
     */
    public void setWithholdingHistoryVO(int index, edit.common.vo.WithholdingHistoryVO vWithholdingHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vWithholdingHistoryVO.setParentVO(this.getClass(), this);
        _withholdingHistoryVOList.setElementAt(vWithholdingHistoryVO, index);
    } //-- void setWithholdingHistoryVO(int, edit.common.vo.WithholdingHistoryVO) 

    /**
     * Method setWithholdingHistoryVO
     * 
     * @param withholdingHistoryVOArray
     */
    public void setWithholdingHistoryVO(edit.common.vo.WithholdingHistoryVO[] withholdingHistoryVOArray)
    {
        //-- copy array
        _withholdingHistoryVOList.removeAllElements();
        for (int i = 0; i < withholdingHistoryVOArray.length; i++) {
            withholdingHistoryVOArray[i].setParentVO(this.getClass(), this);
            _withholdingHistoryVOList.addElement(withholdingHistoryVOArray[i]);
        }
    } //-- void setWithholdingHistoryVO(edit.common.vo.WithholdingHistoryVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITTrxHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITTrxHistoryVO) Unmarshaller.unmarshal(edit.common.vo.EDITTrxHistoryVO.class, reader);
    } //-- edit.common.vo.EDITTrxHistoryVO unmarshal(java.io.Reader) 

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
