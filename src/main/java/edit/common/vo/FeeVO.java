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
 * Class FeeVO.
 * 
 * @version $Revision$ $Date$
 */
public class FeeVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _feePK
     */
    private long _feePK;

    /**
     * keeps track of state for field: _feePK
     */
    private boolean _has_feePK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _feeDescriptionFK
     */
    private long _feeDescriptionFK;

    /**
     * keeps track of state for field: _feeDescriptionFK
     */
    private boolean _has_feeDescriptionFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _processDateTime
     */
    private java.lang.String _processDateTime;

    /**
     * Field _originalProcessDate
     */
    private java.lang.String _originalProcessDate;

    /**
     * Field _releaseDate
     */
    private java.lang.String _releaseDate;

    /**
     * Field _releaseInd
     */
    private java.lang.String _releaseInd;

    /**
     * Field _statusCT
     */
    private java.lang.String _statusCT;

    /**
     * Field _accountingPendingStatus
     */
    private java.lang.String _accountingPendingStatus;

    /**
     * Field _trxAmount
     */
    private java.math.BigDecimal _trxAmount;

    /**
     * Field _transactionTypeCT
     */
    private java.lang.String _transactionTypeCT;

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _accumulatedTrxAmtReceived
     */
    private java.math.BigDecimal _accumulatedTrxAmtReceived;

    /**
     * Field _toFromInd
     */
    private java.lang.String _toFromInd;

    /**
     * Field _shares
     */
    private java.math.BigDecimal _shares;

    /**
     * Field _chargeCodeFK
     */
    private long _chargeCodeFK;

    /**
     * keeps track of state for field: _chargeCodeFK
     */
    private boolean _has_chargeCodeFK;

    /**
     * Field _units
     */
    private java.math.BigDecimal _units;

    /**
     * Field _redemptionDate
     */
    private java.lang.String _redemptionDate;

    /**
     * Field _accountingPeriod
     */
    private java.lang.String _accountingPeriod;

    /**
     * Field _feeCorrespondenceVOList
     */
    private java.util.Vector _feeCorrespondenceVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FeeVO() {
        super();
        _feeCorrespondenceVOList = new Vector();
    } //-- edit.common.vo.FeeVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFeeCorrespondenceVO
     * 
     * @param vFeeCorrespondenceVO
     */
    public void addFeeCorrespondenceVO(edit.common.vo.FeeCorrespondenceVO vFeeCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeCorrespondenceVO.setParentVO(this.getClass(), this);
        _feeCorrespondenceVOList.addElement(vFeeCorrespondenceVO);
    } //-- void addFeeCorrespondenceVO(edit.common.vo.FeeCorrespondenceVO) 

    /**
     * Method addFeeCorrespondenceVO
     * 
     * @param index
     * @param vFeeCorrespondenceVO
     */
    public void addFeeCorrespondenceVO(int index, edit.common.vo.FeeCorrespondenceVO vFeeCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeCorrespondenceVO.setParentVO(this.getClass(), this);
        _feeCorrespondenceVOList.insertElementAt(vFeeCorrespondenceVO, index);
    } //-- void addFeeCorrespondenceVO(int, edit.common.vo.FeeCorrespondenceVO) 

    /**
     * Method enumerateFeeCorrespondenceVO
     */
    public java.util.Enumeration enumerateFeeCorrespondenceVO()
    {
        return _feeCorrespondenceVOList.elements();
    } //-- java.util.Enumeration enumerateFeeCorrespondenceVO() 

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
        
        if (obj instanceof FeeVO) {
        
            FeeVO temp = (FeeVO)obj;
            if (this._feePK != temp._feePK)
                return false;
            if (this._has_feePK != temp._has_feePK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._feeDescriptionFK != temp._feeDescriptionFK)
                return false;
            if (this._has_feeDescriptionFK != temp._has_feeDescriptionFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._processDateTime != null) {
                if (temp._processDateTime == null) return false;
                else if (!(this._processDateTime.equals(temp._processDateTime))) 
                    return false;
            }
            else if (temp._processDateTime != null)
                return false;
            if (this._originalProcessDate != null) {
                if (temp._originalProcessDate == null) return false;
                else if (!(this._originalProcessDate.equals(temp._originalProcessDate))) 
                    return false;
            }
            else if (temp._originalProcessDate != null)
                return false;
            if (this._releaseDate != null) {
                if (temp._releaseDate == null) return false;
                else if (!(this._releaseDate.equals(temp._releaseDate))) 
                    return false;
            }
            else if (temp._releaseDate != null)
                return false;
            if (this._releaseInd != null) {
                if (temp._releaseInd == null) return false;
                else if (!(this._releaseInd.equals(temp._releaseInd))) 
                    return false;
            }
            else if (temp._releaseInd != null)
                return false;
            if (this._statusCT != null) {
                if (temp._statusCT == null) return false;
                else if (!(this._statusCT.equals(temp._statusCT))) 
                    return false;
            }
            else if (temp._statusCT != null)
                return false;
            if (this._accountingPendingStatus != null) {
                if (temp._accountingPendingStatus == null) return false;
                else if (!(this._accountingPendingStatus.equals(temp._accountingPendingStatus))) 
                    return false;
            }
            else if (temp._accountingPendingStatus != null)
                return false;
            if (this._trxAmount != null) {
                if (temp._trxAmount == null) return false;
                else if (!(this._trxAmount.equals(temp._trxAmount))) 
                    return false;
            }
            else if (temp._trxAmount != null)
                return false;
            if (this._transactionTypeCT != null) {
                if (temp._transactionTypeCT == null) return false;
                else if (!(this._transactionTypeCT.equals(temp._transactionTypeCT))) 
                    return false;
            }
            else if (temp._transactionTypeCT != null)
                return false;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._accumulatedTrxAmtReceived != null) {
                if (temp._accumulatedTrxAmtReceived == null) return false;
                else if (!(this._accumulatedTrxAmtReceived.equals(temp._accumulatedTrxAmtReceived))) 
                    return false;
            }
            else if (temp._accumulatedTrxAmtReceived != null)
                return false;
            if (this._toFromInd != null) {
                if (temp._toFromInd == null) return false;
                else if (!(this._toFromInd.equals(temp._toFromInd))) 
                    return false;
            }
            else if (temp._toFromInd != null)
                return false;
            if (this._shares != null) {
                if (temp._shares == null) return false;
                else if (!(this._shares.equals(temp._shares))) 
                    return false;
            }
            else if (temp._shares != null)
                return false;
            if (this._chargeCodeFK != temp._chargeCodeFK)
                return false;
            if (this._has_chargeCodeFK != temp._has_chargeCodeFK)
                return false;
            if (this._units != null) {
                if (temp._units == null) return false;
                else if (!(this._units.equals(temp._units))) 
                    return false;
            }
            else if (temp._units != null)
                return false;
            if (this._redemptionDate != null) {
                if (temp._redemptionDate == null) return false;
                else if (!(this._redemptionDate.equals(temp._redemptionDate))) 
                    return false;
            }
            else if (temp._redemptionDate != null)
                return false;
            if (this._accountingPeriod != null) {
                if (temp._accountingPeriod == null) return false;
                else if (!(this._accountingPeriod.equals(temp._accountingPeriod))) 
                    return false;
            }
            else if (temp._accountingPeriod != null)
                return false;
            if (this._feeCorrespondenceVOList != null) {
                if (temp._feeCorrespondenceVOList == null) return false;
                else if (!(this._feeCorrespondenceVOList.equals(temp._feeCorrespondenceVOList))) 
                    return false;
            }
            else if (temp._feeCorrespondenceVOList != null)
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
     * Method getAccountingPeriodReturns the value of field
     * 'accountingPeriod'.
     * 
     * @return the value of field 'accountingPeriod'.
     */
    public java.lang.String getAccountingPeriod()
    {
        return this._accountingPeriod;
    } //-- java.lang.String getAccountingPeriod() 

    /**
     * Method getAccumulatedTrxAmtReceivedReturns the value of
     * field 'accumulatedTrxAmtReceived'.
     * 
     * @return the value of field 'accumulatedTrxAmtReceived'.
     */
    public java.math.BigDecimal getAccumulatedTrxAmtReceived()
    {
        return this._accumulatedTrxAmtReceived;
    } //-- java.math.BigDecimal getAccumulatedTrxAmtReceived() 

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
     * Method getContractNumberReturns the value of field
     * 'contractNumber'.
     * 
     * @return the value of field 'contractNumber'.
     */
    public java.lang.String getContractNumber()
    {
        return this._contractNumber;
    } //-- java.lang.String getContractNumber() 

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
     * Method getFeeCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.FeeCorrespondenceVO getFeeCorrespondenceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FeeCorrespondenceVO) _feeCorrespondenceVOList.elementAt(index);
    } //-- edit.common.vo.FeeCorrespondenceVO getFeeCorrespondenceVO(int) 

    /**
     * Method getFeeCorrespondenceVO
     */
    public edit.common.vo.FeeCorrespondenceVO[] getFeeCorrespondenceVO()
    {
        int size = _feeCorrespondenceVOList.size();
        edit.common.vo.FeeCorrespondenceVO[] mArray = new edit.common.vo.FeeCorrespondenceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FeeCorrespondenceVO) _feeCorrespondenceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FeeCorrespondenceVO[] getFeeCorrespondenceVO() 

    /**
     * Method getFeeCorrespondenceVOCount
     */
    public int getFeeCorrespondenceVOCount()
    {
        return _feeCorrespondenceVOList.size();
    } //-- int getFeeCorrespondenceVOCount() 

    /**
     * Method getFeeDescriptionFKReturns the value of field
     * 'feeDescriptionFK'.
     * 
     * @return the value of field 'feeDescriptionFK'.
     */
    public long getFeeDescriptionFK()
    {
        return this._feeDescriptionFK;
    } //-- long getFeeDescriptionFK() 

    /**
     * Method getFeePKReturns the value of field 'feePK'.
     * 
     * @return the value of field 'feePK'.
     */
    public long getFeePK()
    {
        return this._feePK;
    } //-- long getFeePK() 

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
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getOriginalProcessDateReturns the value of field
     * 'originalProcessDate'.
     * 
     * @return the value of field 'originalProcessDate'.
     */
    public java.lang.String getOriginalProcessDate()
    {
        return this._originalProcessDate;
    } //-- java.lang.String getOriginalProcessDate() 

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
     * Method getRedemptionDateReturns the value of field
     * 'redemptionDate'.
     * 
     * @return the value of field 'redemptionDate'.
     */
    public java.lang.String getRedemptionDate()
    {
        return this._redemptionDate;
    } //-- java.lang.String getRedemptionDate() 

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
     * Method getReleaseIndReturns the value of field 'releaseInd'.
     * 
     * @return the value of field 'releaseInd'.
     */
    public java.lang.String getReleaseInd()
    {
        return this._releaseInd;
    } //-- java.lang.String getReleaseInd() 

    /**
     * Method getSharesReturns the value of field 'shares'.
     * 
     * @return the value of field 'shares'.
     */
    public java.math.BigDecimal getShares()
    {
        return this._shares;
    } //-- java.math.BigDecimal getShares() 

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
     * Method getToFromIndReturns the value of field 'toFromInd'.
     * 
     * @return the value of field 'toFromInd'.
     */
    public java.lang.String getToFromInd()
    {
        return this._toFromInd;
    } //-- java.lang.String getToFromInd() 

    /**
     * Method getTransactionTypeCTReturns the value of field
     * 'transactionTypeCT'.
     * 
     * @return the value of field 'transactionTypeCT'.
     */
    public java.lang.String getTransactionTypeCT()
    {
        return this._transactionTypeCT;
    } //-- java.lang.String getTransactionTypeCT() 

    /**
     * Method getTrxAmountReturns the value of field 'trxAmount'.
     * 
     * @return the value of field 'trxAmount'.
     */
    public java.math.BigDecimal getTrxAmount()
    {
        return this._trxAmount;
    } //-- java.math.BigDecimal getTrxAmount() 

    /**
     * Method getUnitsReturns the value of field 'units'.
     * 
     * @return the value of field 'units'.
     */
    public java.math.BigDecimal getUnits()
    {
        return this._units;
    } //-- java.math.BigDecimal getUnits() 

    /**
     * Method hasChargeCodeFK
     */
    public boolean hasChargeCodeFK()
    {
        return this._has_chargeCodeFK;
    } //-- boolean hasChargeCodeFK() 

    /**
     * Method hasFeeDescriptionFK
     */
    public boolean hasFeeDescriptionFK()
    {
        return this._has_feeDescriptionFK;
    } //-- boolean hasFeeDescriptionFK() 

    /**
     * Method hasFeePK
     */
    public boolean hasFeePK()
    {
        return this._has_feePK;
    } //-- boolean hasFeePK() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

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
     * Method removeAllFeeCorrespondenceVO
     */
    public void removeAllFeeCorrespondenceVO()
    {
        _feeCorrespondenceVOList.removeAllElements();
    } //-- void removeAllFeeCorrespondenceVO() 

    /**
     * Method removeFeeCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.FeeCorrespondenceVO removeFeeCorrespondenceVO(int index)
    {
        java.lang.Object obj = _feeCorrespondenceVOList.elementAt(index);
        _feeCorrespondenceVOList.removeElementAt(index);
        return (edit.common.vo.FeeCorrespondenceVO) obj;
    } //-- edit.common.vo.FeeCorrespondenceVO removeFeeCorrespondenceVO(int) 

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
     * Method setAccountingPeriodSets the value of field
     * 'accountingPeriod'.
     * 
     * @param accountingPeriod the value of field 'accountingPeriod'
     */
    public void setAccountingPeriod(java.lang.String accountingPeriod)
    {
        this._accountingPeriod = accountingPeriod;
        
        super.setVoChanged(true);
    } //-- void setAccountingPeriod(java.lang.String) 

    /**
     * Method setAccumulatedTrxAmtReceivedSets the value of field
     * 'accumulatedTrxAmtReceived'.
     * 
     * @param accumulatedTrxAmtReceived the value of field
     * 'accumulatedTrxAmtReceived'.
     */
    public void setAccumulatedTrxAmtReceived(java.math.BigDecimal accumulatedTrxAmtReceived)
    {
        this._accumulatedTrxAmtReceived = accumulatedTrxAmtReceived;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedTrxAmtReceived(java.math.BigDecimal) 

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
     * Method setContractNumberSets the value of field
     * 'contractNumber'.
     * 
     * @param contractNumber the value of field 'contractNumber'.
     */
    public void setContractNumber(java.lang.String contractNumber)
    {
        this._contractNumber = contractNumber;
        
        super.setVoChanged(true);
    } //-- void setContractNumber(java.lang.String) 

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
     * Method setFeeCorrespondenceVO
     * 
     * @param index
     * @param vFeeCorrespondenceVO
     */
    public void setFeeCorrespondenceVO(int index, edit.common.vo.FeeCorrespondenceVO vFeeCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFeeCorrespondenceVO.setParentVO(this.getClass(), this);
        _feeCorrespondenceVOList.setElementAt(vFeeCorrespondenceVO, index);
    } //-- void setFeeCorrespondenceVO(int, edit.common.vo.FeeCorrespondenceVO) 

    /**
     * Method setFeeCorrespondenceVO
     * 
     * @param feeCorrespondenceVOArray
     */
    public void setFeeCorrespondenceVO(edit.common.vo.FeeCorrespondenceVO[] feeCorrespondenceVOArray)
    {
        //-- copy array
        _feeCorrespondenceVOList.removeAllElements();
        for (int i = 0; i < feeCorrespondenceVOArray.length; i++) {
            feeCorrespondenceVOArray[i].setParentVO(this.getClass(), this);
            _feeCorrespondenceVOList.addElement(feeCorrespondenceVOArray[i]);
        }
    } //-- void setFeeCorrespondenceVO(edit.common.vo.FeeCorrespondenceVO) 

    /**
     * Method setFeeDescriptionFKSets the value of field
     * 'feeDescriptionFK'.
     * 
     * @param feeDescriptionFK the value of field 'feeDescriptionFK'
     */
    public void setFeeDescriptionFK(long feeDescriptionFK)
    {
        this._feeDescriptionFK = feeDescriptionFK;
        
        super.setVoChanged(true);
        this._has_feeDescriptionFK = true;
    } //-- void setFeeDescriptionFK(long) 

    /**
     * Method setFeePKSets the value of field 'feePK'.
     * 
     * @param feePK the value of field 'feePK'.
     */
    public void setFeePK(long feePK)
    {
        this._feePK = feePK;
        
        super.setVoChanged(true);
        this._has_feePK = true;
    } //-- void setFeePK(long) 

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
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

    /**
     * Method setOriginalProcessDateSets the value of field
     * 'originalProcessDate'.
     * 
     * @param originalProcessDate the value of field
     * 'originalProcessDate'.
     */
    public void setOriginalProcessDate(java.lang.String originalProcessDate)
    {
        this._originalProcessDate = originalProcessDate;
        
        super.setVoChanged(true);
    } //-- void setOriginalProcessDate(java.lang.String) 

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
     * Method setRedemptionDateSets the value of field
     * 'redemptionDate'.
     * 
     * @param redemptionDate the value of field 'redemptionDate'.
     */
    public void setRedemptionDate(java.lang.String redemptionDate)
    {
        this._redemptionDate = redemptionDate;
        
        super.setVoChanged(true);
    } //-- void setRedemptionDate(java.lang.String) 

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
     * Method setReleaseIndSets the value of field 'releaseInd'.
     * 
     * @param releaseInd the value of field 'releaseInd'.
     */
    public void setReleaseInd(java.lang.String releaseInd)
    {
        this._releaseInd = releaseInd;
        
        super.setVoChanged(true);
    } //-- void setReleaseInd(java.lang.String) 

    /**
     * Method setSharesSets the value of field 'shares'.
     * 
     * @param shares the value of field 'shares'.
     */
    public void setShares(java.math.BigDecimal shares)
    {
        this._shares = shares;
        
        super.setVoChanged(true);
    } //-- void setShares(java.math.BigDecimal) 

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
     * Method setToFromIndSets the value of field 'toFromInd'.
     * 
     * @param toFromInd the value of field 'toFromInd'.
     */
    public void setToFromInd(java.lang.String toFromInd)
    {
        this._toFromInd = toFromInd;
        
        super.setVoChanged(true);
    } //-- void setToFromInd(java.lang.String) 

    /**
     * Method setTransactionTypeCTSets the value of field
     * 'transactionTypeCT'.
     * 
     * @param transactionTypeCT the value of field
     * 'transactionTypeCT'.
     */
    public void setTransactionTypeCT(java.lang.String transactionTypeCT)
    {
        this._transactionTypeCT = transactionTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTransactionTypeCT(java.lang.String) 

    /**
     * Method setTrxAmountSets the value of field 'trxAmount'.
     * 
     * @param trxAmount the value of field 'trxAmount'.
     */
    public void setTrxAmount(java.math.BigDecimal trxAmount)
    {
        this._trxAmount = trxAmount;
        
        super.setVoChanged(true);
    } //-- void setTrxAmount(java.math.BigDecimal) 

    /**
     * Method setUnitsSets the value of field 'units'.
     * 
     * @param units the value of field 'units'.
     */
    public void setUnits(java.math.BigDecimal units)
    {
        this._units = units;
        
        super.setVoChanged(true);
    } //-- void setUnits(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FeeVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FeeVO) Unmarshaller.unmarshal(edit.common.vo.FeeVO.class, reader);
    } //-- edit.common.vo.FeeVO unmarshal(java.io.Reader) 

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
