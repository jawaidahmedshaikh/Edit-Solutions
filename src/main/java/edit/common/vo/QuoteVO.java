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
 * Quote processor
 * 
 * @version $Revision$ $Date$
 */
public class QuoteVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;

    /**
     * Field _clientDetailVOList
     */
    private java.util.Vector _clientDetailVOList;

    /**
     * Field _billScheduleVO
     */
    private edit.common.vo.BillScheduleVO _billScheduleVO;

    /**
     * Field _commutedValue
     */
    private java.math.BigDecimal _commutedValue;

    /**
     * Field _quoteDate
     */
    private java.lang.String _quoteDate;

    /**
     * Field _quoteTypeCT
     */
    private java.lang.String _quoteTypeCT;

    /**
     * Field _accumValue
     */
    private java.math.BigDecimal _accumValue;

    /**
     * Field _surrenderCharge
     */
    private java.math.BigDecimal _surrenderCharge;

    /**
     * Field _MVA
     */
    private java.math.BigDecimal _MVA;

    /**
     * Field _cashSurrenderValue
     */
    private java.math.BigDecimal _cashSurrenderValue;

    /**
     * Field _guarMinimumSurrenderValue
     */
    private java.math.BigDecimal _guarMinimumSurrenderValue;

    /**
     * Field _maxPreferredAvailable
     */
    private java.math.BigDecimal _maxPreferredAvailable;

    /**
     * Field _maxNonPreferredAvailable
     */
    private java.math.BigDecimal _maxNonPreferredAvailable;

    /**
     * Field _grossLoanableAmount
     */
    private java.math.BigDecimal _grossLoanableAmount;

    /**
     * Field _loanCollateral
     */
    private java.math.BigDecimal _loanCollateral;

    /**
     * Field _deathBenefit
     */
    private java.math.BigDecimal _deathBenefit;

    /**
     * Field _unlockedCashValue
     */
    private java.math.BigDecimal _unlockedCashValue;

    /**
     * Field _premiumRefund
     */
    private java.math.BigDecimal _premiumRefund;

    /**
     * Field _surrenderValue
     */
    private java.math.BigDecimal _surrenderValue;

    /**
     * Field _adjustedSurrenderValue
     */
    private java.math.BigDecimal _adjustedSurrenderValue;

    /**
     * Field _loanPayoff
     */
    private java.math.BigDecimal _loanPayoff;

    /**
     * Field _checkAmount
     */
    private java.math.BigDecimal _checkAmount;

    /**
     * Field _longTermCareLienBalance
     */
    private java.math.BigDecimal _longTermCareLienBalance;

    /**
     * Field _TILienBalance
     */
    private java.math.BigDecimal _TILienBalance;

    /**
     * Field _paidToDate
     */
    private java.lang.String _paidToDate;

    /**
     * Field _netSinglePremium
     */
    private java.math.BigDecimal _netSinglePremium;

    /**
     * Field _reducedPaidUpAmount
     */
    private java.math.BigDecimal _reducedPaidUpAmount;

    /**
     * Field _lienBalance
     */
    private java.math.BigDecimal _lienBalance;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _quoteInvestmentVOList
     */
    private java.util.Vector _quoteInvestmentVOList;

    /**
     * Field _quoteAdjustmentVOList
     */
    private java.util.Vector _quoteAdjustmentVOList;

    /**
     * Field _EDITTrxVOList
     */
    private java.util.Vector _EDITTrxVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public QuoteVO() {
        super();
        _segmentVOList = new Vector();
        _clientDetailVOList = new Vector();
        _quoteInvestmentVOList = new Vector();
        _quoteAdjustmentVOList = new Vector();
        _EDITTrxVOList = new Vector();
    } //-- edit.common.vo.QuoteVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientDetailVO
     * 
     * @param vClientDetailVO
     */
    public void addClientDetailVO(edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.addElement(vClientDetailVO);
    } //-- void addClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method addClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void addClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.insertElementAt(vClientDetailVO, index);
    } //-- void addClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method addEDITTrxVO
     * 
     * @param vEDITTrxVO
     */
    public void addEDITTrxVO(edit.common.vo.EDITTrxVO vEDITTrxVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxVO.setParentVO(this.getClass(), this);
        _EDITTrxVOList.addElement(vEDITTrxVO);
    } //-- void addEDITTrxVO(edit.common.vo.EDITTrxVO) 

    /**
     * Method addEDITTrxVO
     * 
     * @param index
     * @param vEDITTrxVO
     */
    public void addEDITTrxVO(int index, edit.common.vo.EDITTrxVO vEDITTrxVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxVO.setParentVO(this.getClass(), this);
        _EDITTrxVOList.insertElementAt(vEDITTrxVO, index);
    } //-- void addEDITTrxVO(int, edit.common.vo.EDITTrxVO) 

    /**
     * Method addQuoteAdjustmentVO
     * 
     * @param vQuoteAdjustmentVO
     */
    public void addQuoteAdjustmentVO(edit.common.vo.QuoteAdjustmentVO vQuoteAdjustmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuoteAdjustmentVO.setParentVO(this.getClass(), this);
        _quoteAdjustmentVOList.addElement(vQuoteAdjustmentVO);
    } //-- void addQuoteAdjustmentVO(edit.common.vo.QuoteAdjustmentVO) 

    /**
     * Method addQuoteAdjustmentVO
     * 
     * @param index
     * @param vQuoteAdjustmentVO
     */
    public void addQuoteAdjustmentVO(int index, edit.common.vo.QuoteAdjustmentVO vQuoteAdjustmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuoteAdjustmentVO.setParentVO(this.getClass(), this);
        _quoteAdjustmentVOList.insertElementAt(vQuoteAdjustmentVO, index);
    } //-- void addQuoteAdjustmentVO(int, edit.common.vo.QuoteAdjustmentVO) 

    /**
     * Method addQuoteInvestmentVO
     * 
     * @param vQuoteInvestmentVO
     */
    public void addQuoteInvestmentVO(edit.common.vo.QuoteInvestmentVO vQuoteInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuoteInvestmentVO.setParentVO(this.getClass(), this);
        _quoteInvestmentVOList.addElement(vQuoteInvestmentVO);
    } //-- void addQuoteInvestmentVO(edit.common.vo.QuoteInvestmentVO) 

    /**
     * Method addQuoteInvestmentVO
     * 
     * @param index
     * @param vQuoteInvestmentVO
     */
    public void addQuoteInvestmentVO(int index, edit.common.vo.QuoteInvestmentVO vQuoteInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuoteInvestmentVO.setParentVO(this.getClass(), this);
        _quoteInvestmentVOList.insertElementAt(vQuoteInvestmentVO, index);
    } //-- void addQuoteInvestmentVO(int, edit.common.vo.QuoteInvestmentVO) 

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
     * Method enumerateClientDetailVO
     */
    public java.util.Enumeration enumerateClientDetailVO()
    {
        return _clientDetailVOList.elements();
    } //-- java.util.Enumeration enumerateClientDetailVO() 

    /**
     * Method enumerateEDITTrxVO
     */
    public java.util.Enumeration enumerateEDITTrxVO()
    {
        return _EDITTrxVOList.elements();
    } //-- java.util.Enumeration enumerateEDITTrxVO() 

    /**
     * Method enumerateQuoteAdjustmentVO
     */
    public java.util.Enumeration enumerateQuoteAdjustmentVO()
    {
        return _quoteAdjustmentVOList.elements();
    } //-- java.util.Enumeration enumerateQuoteAdjustmentVO() 

    /**
     * Method enumerateQuoteInvestmentVO
     */
    public java.util.Enumeration enumerateQuoteInvestmentVO()
    {
        return _quoteInvestmentVOList.elements();
    } //-- java.util.Enumeration enumerateQuoteInvestmentVO() 

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
        
        if (obj instanceof QuoteVO) {
        
            QuoteVO temp = (QuoteVO)obj;
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            if (this._clientDetailVOList != null) {
                if (temp._clientDetailVOList == null) return false;
                else if (!(this._clientDetailVOList.equals(temp._clientDetailVOList))) 
                    return false;
            }
            else if (temp._clientDetailVOList != null)
                return false;
            if (this._billScheduleVO != null) {
                if (temp._billScheduleVO == null) return false;
                else if (!(this._billScheduleVO.equals(temp._billScheduleVO))) 
                    return false;
            }
            else if (temp._billScheduleVO != null)
                return false;
            if (this._commutedValue != null) {
                if (temp._commutedValue == null) return false;
                else if (!(this._commutedValue.equals(temp._commutedValue))) 
                    return false;
            }
            else if (temp._commutedValue != null)
                return false;
            if (this._quoteDate != null) {
                if (temp._quoteDate == null) return false;
                else if (!(this._quoteDate.equals(temp._quoteDate))) 
                    return false;
            }
            else if (temp._quoteDate != null)
                return false;
            if (this._quoteTypeCT != null) {
                if (temp._quoteTypeCT == null) return false;
                else if (!(this._quoteTypeCT.equals(temp._quoteTypeCT))) 
                    return false;
            }
            else if (temp._quoteTypeCT != null)
                return false;
            if (this._accumValue != null) {
                if (temp._accumValue == null) return false;
                else if (!(this._accumValue.equals(temp._accumValue))) 
                    return false;
            }
            else if (temp._accumValue != null)
                return false;
            if (this._surrenderCharge != null) {
                if (temp._surrenderCharge == null) return false;
                else if (!(this._surrenderCharge.equals(temp._surrenderCharge))) 
                    return false;
            }
            else if (temp._surrenderCharge != null)
                return false;
            if (this._MVA != null) {
                if (temp._MVA == null) return false;
                else if (!(this._MVA.equals(temp._MVA))) 
                    return false;
            }
            else if (temp._MVA != null)
                return false;
            if (this._cashSurrenderValue != null) {
                if (temp._cashSurrenderValue == null) return false;
                else if (!(this._cashSurrenderValue.equals(temp._cashSurrenderValue))) 
                    return false;
            }
            else if (temp._cashSurrenderValue != null)
                return false;
            if (this._guarMinimumSurrenderValue != null) {
                if (temp._guarMinimumSurrenderValue == null) return false;
                else if (!(this._guarMinimumSurrenderValue.equals(temp._guarMinimumSurrenderValue))) 
                    return false;
            }
            else if (temp._guarMinimumSurrenderValue != null)
                return false;
            if (this._maxPreferredAvailable != null) {
                if (temp._maxPreferredAvailable == null) return false;
                else if (!(this._maxPreferredAvailable.equals(temp._maxPreferredAvailable))) 
                    return false;
            }
            else if (temp._maxPreferredAvailable != null)
                return false;
            if (this._maxNonPreferredAvailable != null) {
                if (temp._maxNonPreferredAvailable == null) return false;
                else if (!(this._maxNonPreferredAvailable.equals(temp._maxNonPreferredAvailable))) 
                    return false;
            }
            else if (temp._maxNonPreferredAvailable != null)
                return false;
            if (this._grossLoanableAmount != null) {
                if (temp._grossLoanableAmount == null) return false;
                else if (!(this._grossLoanableAmount.equals(temp._grossLoanableAmount))) 
                    return false;
            }
            else if (temp._grossLoanableAmount != null)
                return false;
            if (this._loanCollateral != null) {
                if (temp._loanCollateral == null) return false;
                else if (!(this._loanCollateral.equals(temp._loanCollateral))) 
                    return false;
            }
            else if (temp._loanCollateral != null)
                return false;
            if (this._deathBenefit != null) {
                if (temp._deathBenefit == null) return false;
                else if (!(this._deathBenefit.equals(temp._deathBenefit))) 
                    return false;
            }
            else if (temp._deathBenefit != null)
                return false;
            if (this._unlockedCashValue != null) {
                if (temp._unlockedCashValue == null) return false;
                else if (!(this._unlockedCashValue.equals(temp._unlockedCashValue))) 
                    return false;
            }
            else if (temp._unlockedCashValue != null)
                return false;
            if (this._premiumRefund != null) {
                if (temp._premiumRefund == null) return false;
                else if (!(this._premiumRefund.equals(temp._premiumRefund))) 
                    return false;
            }
            else if (temp._premiumRefund != null)
                return false;
            if (this._surrenderValue != null) {
                if (temp._surrenderValue == null) return false;
                else if (!(this._surrenderValue.equals(temp._surrenderValue))) 
                    return false;
            }
            else if (temp._surrenderValue != null)
                return false;
            if (this._adjustedSurrenderValue != null) {
                if (temp._adjustedSurrenderValue == null) return false;
                else if (!(this._adjustedSurrenderValue.equals(temp._adjustedSurrenderValue))) 
                    return false;
            }
            else if (temp._adjustedSurrenderValue != null)
                return false;
            if (this._loanPayoff != null) {
                if (temp._loanPayoff == null) return false;
                else if (!(this._loanPayoff.equals(temp._loanPayoff))) 
                    return false;
            }
            else if (temp._loanPayoff != null)
                return false;
            if (this._checkAmount != null) {
                if (temp._checkAmount == null) return false;
                else if (!(this._checkAmount.equals(temp._checkAmount))) 
                    return false;
            }
            else if (temp._checkAmount != null)
                return false;
            if (this._longTermCareLienBalance != null) {
                if (temp._longTermCareLienBalance == null) return false;
                else if (!(this._longTermCareLienBalance.equals(temp._longTermCareLienBalance))) 
                    return false;
            }
            else if (temp._longTermCareLienBalance != null)
                return false;
            if (this._TILienBalance != null) {
                if (temp._TILienBalance == null) return false;
                else if (!(this._TILienBalance.equals(temp._TILienBalance))) 
                    return false;
            }
            else if (temp._TILienBalance != null)
                return false;
            if (this._paidToDate != null) {
                if (temp._paidToDate == null) return false;
                else if (!(this._paidToDate.equals(temp._paidToDate))) 
                    return false;
            }
            else if (temp._paidToDate != null)
                return false;
            if (this._netSinglePremium != null) {
                if (temp._netSinglePremium == null) return false;
                else if (!(this._netSinglePremium.equals(temp._netSinglePremium))) 
                    return false;
            }
            else if (temp._netSinglePremium != null)
                return false;
            if (this._reducedPaidUpAmount != null) {
                if (temp._reducedPaidUpAmount == null) return false;
                else if (!(this._reducedPaidUpAmount.equals(temp._reducedPaidUpAmount))) 
                    return false;
            }
            else if (temp._reducedPaidUpAmount != null)
                return false;
            if (this._lienBalance != null) {
                if (temp._lienBalance == null) return false;
                else if (!(this._lienBalance.equals(temp._lienBalance))) 
                    return false;
            }
            else if (temp._lienBalance != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._quoteInvestmentVOList != null) {
                if (temp._quoteInvestmentVOList == null) return false;
                else if (!(this._quoteInvestmentVOList.equals(temp._quoteInvestmentVOList))) 
                    return false;
            }
            else if (temp._quoteInvestmentVOList != null)
                return false;
            if (this._quoteAdjustmentVOList != null) {
                if (temp._quoteAdjustmentVOList == null) return false;
                else if (!(this._quoteAdjustmentVOList.equals(temp._quoteAdjustmentVOList))) 
                    return false;
            }
            else if (temp._quoteAdjustmentVOList != null)
                return false;
            if (this._EDITTrxVOList != null) {
                if (temp._EDITTrxVOList == null) return false;
                else if (!(this._EDITTrxVOList.equals(temp._EDITTrxVOList))) 
                    return false;
            }
            else if (temp._EDITTrxVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumValueReturns the value of field 'accumValue'.
     * 
     * @return the value of field 'accumValue'.
     */
    public java.math.BigDecimal getAccumValue()
    {
        return this._accumValue;
    } //-- java.math.BigDecimal getAccumValue() 

    /**
     * Method getAdjustedSurrenderValueReturns the value of field
     * 'adjustedSurrenderValue'.
     * 
     * @return the value of field 'adjustedSurrenderValue'.
     */
    public java.math.BigDecimal getAdjustedSurrenderValue()
    {
        return this._adjustedSurrenderValue;
    } //-- java.math.BigDecimal getAdjustedSurrenderValue() 

    /**
     * Method getBillScheduleVOReturns the value of field
     * 'billScheduleVO'.
     * 
     * @return the value of field 'billScheduleVO'.
     */
    public edit.common.vo.BillScheduleVO getBillScheduleVO()
    {
        return this._billScheduleVO;
    } //-- edit.common.vo.BillScheduleVO getBillScheduleVO() 

    /**
     * Method getCashSurrenderValueReturns the value of field
     * 'cashSurrenderValue'.
     * 
     * @return the value of field 'cashSurrenderValue'.
     */
    public java.math.BigDecimal getCashSurrenderValue()
    {
        return this._cashSurrenderValue;
    } //-- java.math.BigDecimal getCashSurrenderValue() 

    /**
     * Method getCheckAmountReturns the value of field
     * 'checkAmount'.
     * 
     * @return the value of field 'checkAmount'.
     */
    public java.math.BigDecimal getCheckAmount()
    {
        return this._checkAmount;
    } //-- java.math.BigDecimal getCheckAmount() 

    /**
     * Method getClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO(int) 

    /**
     * Method getClientDetailVO
     */
    public edit.common.vo.ClientDetailVO[] getClientDetailVO()
    {
        int size = _clientDetailVOList.size();
        edit.common.vo.ClientDetailVO[] mArray = new edit.common.vo.ClientDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientDetailVO[] getClientDetailVO() 

    /**
     * Method getClientDetailVOCount
     */
    public int getClientDetailVOCount()
    {
        return _clientDetailVOList.size();
    } //-- int getClientDetailVOCount() 

    /**
     * Method getCommutedValueReturns the value of field
     * 'commutedValue'.
     * 
     * @return the value of field 'commutedValue'.
     */
    public java.math.BigDecimal getCommutedValue()
    {
        return this._commutedValue;
    } //-- java.math.BigDecimal getCommutedValue() 

    /**
     * Method getDeathBenefitReturns the value of field
     * 'deathBenefit'.
     * 
     * @return the value of field 'deathBenefit'.
     */
    public java.math.BigDecimal getDeathBenefit()
    {
        return this._deathBenefit;
    } //-- java.math.BigDecimal getDeathBenefit() 

    /**
     * Method getEDITTrxVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxVO getEDITTrxVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITTrxVO) _EDITTrxVOList.elementAt(index);
    } //-- edit.common.vo.EDITTrxVO getEDITTrxVO(int) 

    /**
     * Method getEDITTrxVO
     */
    public edit.common.vo.EDITTrxVO[] getEDITTrxVO()
    {
        int size = _EDITTrxVOList.size();
        edit.common.vo.EDITTrxVO[] mArray = new edit.common.vo.EDITTrxVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITTrxVO) _EDITTrxVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITTrxVO[] getEDITTrxVO() 

    /**
     * Method getEDITTrxVOCount
     */
    public int getEDITTrxVOCount()
    {
        return _EDITTrxVOList.size();
    } //-- int getEDITTrxVOCount() 

    /**
     * Method getGrossLoanableAmountReturns the value of field
     * 'grossLoanableAmount'.
     * 
     * @return the value of field 'grossLoanableAmount'.
     */
    public java.math.BigDecimal getGrossLoanableAmount()
    {
        return this._grossLoanableAmount;
    } //-- java.math.BigDecimal getGrossLoanableAmount() 

    /**
     * Method getGuarMinimumSurrenderValueReturns the value of
     * field 'guarMinimumSurrenderValue'.
     * 
     * @return the value of field 'guarMinimumSurrenderValue'.
     */
    public java.math.BigDecimal getGuarMinimumSurrenderValue()
    {
        return this._guarMinimumSurrenderValue;
    } //-- java.math.BigDecimal getGuarMinimumSurrenderValue() 

    /**
     * Method getLienBalanceReturns the value of field
     * 'lienBalance'.
     * 
     * @return the value of field 'lienBalance'.
     */
    public java.math.BigDecimal getLienBalance()
    {
        return this._lienBalance;
    } //-- java.math.BigDecimal getLienBalance() 

    /**
     * Method getLoanCollateralReturns the value of field
     * 'loanCollateral'.
     * 
     * @return the value of field 'loanCollateral'.
     */
    public java.math.BigDecimal getLoanCollateral()
    {
        return this._loanCollateral;
    } //-- java.math.BigDecimal getLoanCollateral() 

    /**
     * Method getLoanPayoffReturns the value of field 'loanPayoff'.
     * 
     * @return the value of field 'loanPayoff'.
     */
    public java.math.BigDecimal getLoanPayoff()
    {
        return this._loanPayoff;
    } //-- java.math.BigDecimal getLoanPayoff() 

    /**
     * Method getLongTermCareLienBalanceReturns the value of field
     * 'longTermCareLienBalance'.
     * 
     * @return the value of field 'longTermCareLienBalance'.
     */
    public java.math.BigDecimal getLongTermCareLienBalance()
    {
        return this._longTermCareLienBalance;
    } //-- java.math.BigDecimal getLongTermCareLienBalance() 

    /**
     * Method getMVAReturns the value of field 'MVA'.
     * 
     * @return the value of field 'MVA'.
     */
    public java.math.BigDecimal getMVA()
    {
        return this._MVA;
    } //-- java.math.BigDecimal getMVA() 

    /**
     * Method getMaxNonPreferredAvailableReturns the value of field
     * 'maxNonPreferredAvailable'.
     * 
     * @return the value of field 'maxNonPreferredAvailable'.
     */
    public java.math.BigDecimal getMaxNonPreferredAvailable()
    {
        return this._maxNonPreferredAvailable;
    } //-- java.math.BigDecimal getMaxNonPreferredAvailable() 

    /**
     * Method getMaxPreferredAvailableReturns the value of field
     * 'maxPreferredAvailable'.
     * 
     * @return the value of field 'maxPreferredAvailable'.
     */
    public java.math.BigDecimal getMaxPreferredAvailable()
    {
        return this._maxPreferredAvailable;
    } //-- java.math.BigDecimal getMaxPreferredAvailable() 

    /**
     * Method getNetSinglePremiumReturns the value of field
     * 'netSinglePremium'.
     * 
     * @return the value of field 'netSinglePremium'.
     */
    public java.math.BigDecimal getNetSinglePremium()
    {
        return this._netSinglePremium;
    } //-- java.math.BigDecimal getNetSinglePremium() 

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
     * Method getPaidToDateReturns the value of field 'paidToDate'.
     * 
     * @return the value of field 'paidToDate'.
     */
    public java.lang.String getPaidToDate()
    {
        return this._paidToDate;
    } //-- java.lang.String getPaidToDate() 

    /**
     * Method getPremiumRefundReturns the value of field
     * 'premiumRefund'.
     * 
     * @return the value of field 'premiumRefund'.
     */
    public java.math.BigDecimal getPremiumRefund()
    {
        return this._premiumRefund;
    } //-- java.math.BigDecimal getPremiumRefund() 

    /**
     * Method getQuoteAdjustmentVO
     * 
     * @param index
     */
    public edit.common.vo.QuoteAdjustmentVO getQuoteAdjustmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _quoteAdjustmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.QuoteAdjustmentVO) _quoteAdjustmentVOList.elementAt(index);
    } //-- edit.common.vo.QuoteAdjustmentVO getQuoteAdjustmentVO(int) 

    /**
     * Method getQuoteAdjustmentVO
     */
    public edit.common.vo.QuoteAdjustmentVO[] getQuoteAdjustmentVO()
    {
        int size = _quoteAdjustmentVOList.size();
        edit.common.vo.QuoteAdjustmentVO[] mArray = new edit.common.vo.QuoteAdjustmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.QuoteAdjustmentVO) _quoteAdjustmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.QuoteAdjustmentVO[] getQuoteAdjustmentVO() 

    /**
     * Method getQuoteAdjustmentVOCount
     */
    public int getQuoteAdjustmentVOCount()
    {
        return _quoteAdjustmentVOList.size();
    } //-- int getQuoteAdjustmentVOCount() 

    /**
     * Method getQuoteDateReturns the value of field 'quoteDate'.
     * 
     * @return the value of field 'quoteDate'.
     */
    public java.lang.String getQuoteDate()
    {
        return this._quoteDate;
    } //-- java.lang.String getQuoteDate() 

    /**
     * Method getQuoteInvestmentVO
     * 
     * @param index
     */
    public edit.common.vo.QuoteInvestmentVO getQuoteInvestmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _quoteInvestmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.QuoteInvestmentVO) _quoteInvestmentVOList.elementAt(index);
    } //-- edit.common.vo.QuoteInvestmentVO getQuoteInvestmentVO(int) 

    /**
     * Method getQuoteInvestmentVO
     */
    public edit.common.vo.QuoteInvestmentVO[] getQuoteInvestmentVO()
    {
        int size = _quoteInvestmentVOList.size();
        edit.common.vo.QuoteInvestmentVO[] mArray = new edit.common.vo.QuoteInvestmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.QuoteInvestmentVO) _quoteInvestmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.QuoteInvestmentVO[] getQuoteInvestmentVO() 

    /**
     * Method getQuoteInvestmentVOCount
     */
    public int getQuoteInvestmentVOCount()
    {
        return _quoteInvestmentVOList.size();
    } //-- int getQuoteInvestmentVOCount() 

    /**
     * Method getQuoteTypeCTReturns the value of field
     * 'quoteTypeCT'.
     * 
     * @return the value of field 'quoteTypeCT'.
     */
    public java.lang.String getQuoteTypeCT()
    {
        return this._quoteTypeCT;
    } //-- java.lang.String getQuoteTypeCT() 

    /**
     * Method getReducedPaidUpAmountReturns the value of field
     * 'reducedPaidUpAmount'.
     * 
     * @return the value of field 'reducedPaidUpAmount'.
     */
    public java.math.BigDecimal getReducedPaidUpAmount()
    {
        return this._reducedPaidUpAmount;
    } //-- java.math.BigDecimal getReducedPaidUpAmount() 

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
     * Method getSurrenderChargeReturns the value of field
     * 'surrenderCharge'.
     * 
     * @return the value of field 'surrenderCharge'.
     */
    public java.math.BigDecimal getSurrenderCharge()
    {
        return this._surrenderCharge;
    } //-- java.math.BigDecimal getSurrenderCharge() 

    /**
     * Method getSurrenderValueReturns the value of field
     * 'surrenderValue'.
     * 
     * @return the value of field 'surrenderValue'.
     */
    public java.math.BigDecimal getSurrenderValue()
    {
        return this._surrenderValue;
    } //-- java.math.BigDecimal getSurrenderValue() 

    /**
     * Method getTILienBalanceReturns the value of field
     * 'TILienBalance'.
     * 
     * @return the value of field 'TILienBalance'.
     */
    public java.math.BigDecimal getTILienBalance()
    {
        return this._TILienBalance;
    } //-- java.math.BigDecimal getTILienBalance() 

    /**
     * Method getUnlockedCashValueReturns the value of field
     * 'unlockedCashValue'.
     * 
     * @return the value of field 'unlockedCashValue'.
     */
    public java.math.BigDecimal getUnlockedCashValue()
    {
        return this._unlockedCashValue;
    } //-- java.math.BigDecimal getUnlockedCashValue() 

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
     * Method removeAllClientDetailVO
     */
    public void removeAllClientDetailVO()
    {
        _clientDetailVOList.removeAllElements();
    } //-- void removeAllClientDetailVO() 

    /**
     * Method removeAllEDITTrxVO
     */
    public void removeAllEDITTrxVO()
    {
        _EDITTrxVOList.removeAllElements();
    } //-- void removeAllEDITTrxVO() 

    /**
     * Method removeAllQuoteAdjustmentVO
     */
    public void removeAllQuoteAdjustmentVO()
    {
        _quoteAdjustmentVOList.removeAllElements();
    } //-- void removeAllQuoteAdjustmentVO() 

    /**
     * Method removeAllQuoteInvestmentVO
     */
    public void removeAllQuoteInvestmentVO()
    {
        _quoteInvestmentVOList.removeAllElements();
    } //-- void removeAllQuoteInvestmentVO() 

    /**
     * Method removeAllSegmentVO
     */
    public void removeAllSegmentVO()
    {
        _segmentVOList.removeAllElements();
    } //-- void removeAllSegmentVO() 

    /**
     * Method removeClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO removeClientDetailVO(int index)
    {
        java.lang.Object obj = _clientDetailVOList.elementAt(index);
        _clientDetailVOList.removeElementAt(index);
        return (edit.common.vo.ClientDetailVO) obj;
    } //-- edit.common.vo.ClientDetailVO removeClientDetailVO(int) 

    /**
     * Method removeEDITTrxVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxVO removeEDITTrxVO(int index)
    {
        java.lang.Object obj = _EDITTrxVOList.elementAt(index);
        _EDITTrxVOList.removeElementAt(index);
        return (edit.common.vo.EDITTrxVO) obj;
    } //-- edit.common.vo.EDITTrxVO removeEDITTrxVO(int) 

    /**
     * Method removeQuoteAdjustmentVO
     * 
     * @param index
     */
    public edit.common.vo.QuoteAdjustmentVO removeQuoteAdjustmentVO(int index)
    {
        java.lang.Object obj = _quoteAdjustmentVOList.elementAt(index);
        _quoteAdjustmentVOList.removeElementAt(index);
        return (edit.common.vo.QuoteAdjustmentVO) obj;
    } //-- edit.common.vo.QuoteAdjustmentVO removeQuoteAdjustmentVO(int) 

    /**
     * Method removeQuoteInvestmentVO
     * 
     * @param index
     */
    public edit.common.vo.QuoteInvestmentVO removeQuoteInvestmentVO(int index)
    {
        java.lang.Object obj = _quoteInvestmentVOList.elementAt(index);
        _quoteInvestmentVOList.removeElementAt(index);
        return (edit.common.vo.QuoteInvestmentVO) obj;
    } //-- edit.common.vo.QuoteInvestmentVO removeQuoteInvestmentVO(int) 

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
     * Method setAccumValueSets the value of field 'accumValue'.
     * 
     * @param accumValue the value of field 'accumValue'.
     */
    public void setAccumValue(java.math.BigDecimal accumValue)
    {
        this._accumValue = accumValue;
        
        super.setVoChanged(true);
    } //-- void setAccumValue(java.math.BigDecimal) 

    /**
     * Method setAdjustedSurrenderValueSets the value of field
     * 'adjustedSurrenderValue'.
     * 
     * @param adjustedSurrenderValue the value of field
     * 'adjustedSurrenderValue'.
     */
    public void setAdjustedSurrenderValue(java.math.BigDecimal adjustedSurrenderValue)
    {
        this._adjustedSurrenderValue = adjustedSurrenderValue;
        
        super.setVoChanged(true);
    } //-- void setAdjustedSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setBillScheduleVOSets the value of field
     * 'billScheduleVO'.
     * 
     * @param billScheduleVO the value of field 'billScheduleVO'.
     */
    public void setBillScheduleVO(edit.common.vo.BillScheduleVO billScheduleVO)
    {
        this._billScheduleVO = billScheduleVO;
    } //-- void setBillScheduleVO(edit.common.vo.BillScheduleVO) 

    /**
     * Method setCashSurrenderValueSets the value of field
     * 'cashSurrenderValue'.
     * 
     * @param cashSurrenderValue the value of field
     * 'cashSurrenderValue'.
     */
    public void setCashSurrenderValue(java.math.BigDecimal cashSurrenderValue)
    {
        this._cashSurrenderValue = cashSurrenderValue;
        
        super.setVoChanged(true);
    } //-- void setCashSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setCheckAmountSets the value of field 'checkAmount'.
     * 
     * @param checkAmount the value of field 'checkAmount'.
     */
    public void setCheckAmount(java.math.BigDecimal checkAmount)
    {
        this._checkAmount = checkAmount;
        
        super.setVoChanged(true);
    } //-- void setCheckAmount(java.math.BigDecimal) 

    /**
     * Method setClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void setClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.setElementAt(vClientDetailVO, index);
    } //-- void setClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method setClientDetailVO
     * 
     * @param clientDetailVOArray
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO[] clientDetailVOArray)
    {
        //-- copy array
        _clientDetailVOList.removeAllElements();
        for (int i = 0; i < clientDetailVOArray.length; i++) {
            clientDetailVOArray[i].setParentVO(this.getClass(), this);
            _clientDetailVOList.addElement(clientDetailVOArray[i]);
        }
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method setCommutedValueSets the value of field
     * 'commutedValue'.
     * 
     * @param commutedValue the value of field 'commutedValue'.
     */
    public void setCommutedValue(java.math.BigDecimal commutedValue)
    {
        this._commutedValue = commutedValue;
        
        super.setVoChanged(true);
    } //-- void setCommutedValue(java.math.BigDecimal) 

    /**
     * Method setDeathBenefitSets the value of field
     * 'deathBenefit'.
     * 
     * @param deathBenefit the value of field 'deathBenefit'.
     */
    public void setDeathBenefit(java.math.BigDecimal deathBenefit)
    {
        this._deathBenefit = deathBenefit;
        
        super.setVoChanged(true);
    } //-- void setDeathBenefit(java.math.BigDecimal) 

    /**
     * Method setEDITTrxVO
     * 
     * @param index
     * @param vEDITTrxVO
     */
    public void setEDITTrxVO(int index, edit.common.vo.EDITTrxVO vEDITTrxVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEDITTrxVO.setParentVO(this.getClass(), this);
        _EDITTrxVOList.setElementAt(vEDITTrxVO, index);
    } //-- void setEDITTrxVO(int, edit.common.vo.EDITTrxVO) 

    /**
     * Method setEDITTrxVO
     * 
     * @param EDITTrxVOArray
     */
    public void setEDITTrxVO(edit.common.vo.EDITTrxVO[] EDITTrxVOArray)
    {
        //-- copy array
        _EDITTrxVOList.removeAllElements();
        for (int i = 0; i < EDITTrxVOArray.length; i++) {
            EDITTrxVOArray[i].setParentVO(this.getClass(), this);
            _EDITTrxVOList.addElement(EDITTrxVOArray[i]);
        }
    } //-- void setEDITTrxVO(edit.common.vo.EDITTrxVO) 

    /**
     * Method setGrossLoanableAmountSets the value of field
     * 'grossLoanableAmount'.
     * 
     * @param grossLoanableAmount the value of field
     * 'grossLoanableAmount'.
     */
    public void setGrossLoanableAmount(java.math.BigDecimal grossLoanableAmount)
    {
        this._grossLoanableAmount = grossLoanableAmount;
        
        super.setVoChanged(true);
    } //-- void setGrossLoanableAmount(java.math.BigDecimal) 

    /**
     * Method setGuarMinimumSurrenderValueSets the value of field
     * 'guarMinimumSurrenderValue'.
     * 
     * @param guarMinimumSurrenderValue the value of field
     * 'guarMinimumSurrenderValue'.
     */
    public void setGuarMinimumSurrenderValue(java.math.BigDecimal guarMinimumSurrenderValue)
    {
        this._guarMinimumSurrenderValue = guarMinimumSurrenderValue;
        
        super.setVoChanged(true);
    } //-- void setGuarMinimumSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setLienBalanceSets the value of field 'lienBalance'.
     * 
     * @param lienBalance the value of field 'lienBalance'.
     */
    public void setLienBalance(java.math.BigDecimal lienBalance)
    {
        this._lienBalance = lienBalance;
        
        super.setVoChanged(true);
    } //-- void setLienBalance(java.math.BigDecimal) 

    /**
     * Method setLoanCollateralSets the value of field
     * 'loanCollateral'.
     * 
     * @param loanCollateral the value of field 'loanCollateral'.
     */
    public void setLoanCollateral(java.math.BigDecimal loanCollateral)
    {
        this._loanCollateral = loanCollateral;
        
        super.setVoChanged(true);
    } //-- void setLoanCollateral(java.math.BigDecimal) 

    /**
     * Method setLoanPayoffSets the value of field 'loanPayoff'.
     * 
     * @param loanPayoff the value of field 'loanPayoff'.
     */
    public void setLoanPayoff(java.math.BigDecimal loanPayoff)
    {
        this._loanPayoff = loanPayoff;
        
        super.setVoChanged(true);
    } //-- void setLoanPayoff(java.math.BigDecimal) 

    /**
     * Method setLongTermCareLienBalanceSets the value of field
     * 'longTermCareLienBalance'.
     * 
     * @param longTermCareLienBalance the value of field
     * 'longTermCareLienBalance'.
     */
    public void setLongTermCareLienBalance(java.math.BigDecimal longTermCareLienBalance)
    {
        this._longTermCareLienBalance = longTermCareLienBalance;
        
        super.setVoChanged(true);
    } //-- void setLongTermCareLienBalance(java.math.BigDecimal) 

    /**
     * Method setMVASets the value of field 'MVA'.
     * 
     * @param MVA the value of field 'MVA'.
     */
    public void setMVA(java.math.BigDecimal MVA)
    {
        this._MVA = MVA;
        
        super.setVoChanged(true);
    } //-- void setMVA(java.math.BigDecimal) 

    /**
     * Method setMaxNonPreferredAvailableSets the value of field
     * 'maxNonPreferredAvailable'.
     * 
     * @param maxNonPreferredAvailable the value of field
     * 'maxNonPreferredAvailable'.
     */
    public void setMaxNonPreferredAvailable(java.math.BigDecimal maxNonPreferredAvailable)
    {
        this._maxNonPreferredAvailable = maxNonPreferredAvailable;
        
        super.setVoChanged(true);
    } //-- void setMaxNonPreferredAvailable(java.math.BigDecimal) 

    /**
     * Method setMaxPreferredAvailableSets the value of field
     * 'maxPreferredAvailable'.
     * 
     * @param maxPreferredAvailable the value of field
     * 'maxPreferredAvailable'.
     */
    public void setMaxPreferredAvailable(java.math.BigDecimal maxPreferredAvailable)
    {
        this._maxPreferredAvailable = maxPreferredAvailable;
        
        super.setVoChanged(true);
    } //-- void setMaxPreferredAvailable(java.math.BigDecimal) 

    /**
     * Method setNetSinglePremiumSets the value of field
     * 'netSinglePremium'.
     * 
     * @param netSinglePremium the value of field 'netSinglePremium'
     */
    public void setNetSinglePremium(java.math.BigDecimal netSinglePremium)
    {
        this._netSinglePremium = netSinglePremium;
        
        super.setVoChanged(true);
    } //-- void setNetSinglePremium(java.math.BigDecimal) 

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
     * Method setPaidToDateSets the value of field 'paidToDate'.
     * 
     * @param paidToDate the value of field 'paidToDate'.
     */
    public void setPaidToDate(java.lang.String paidToDate)
    {
        this._paidToDate = paidToDate;
        
        super.setVoChanged(true);
    } //-- void setPaidToDate(java.lang.String) 

    /**
     * Method setPremiumRefundSets the value of field
     * 'premiumRefund'.
     * 
     * @param premiumRefund the value of field 'premiumRefund'.
     */
    public void setPremiumRefund(java.math.BigDecimal premiumRefund)
    {
        this._premiumRefund = premiumRefund;
        
        super.setVoChanged(true);
    } //-- void setPremiumRefund(java.math.BigDecimal) 

    /**
     * Method setQuoteAdjustmentVO
     * 
     * @param index
     * @param vQuoteAdjustmentVO
     */
    public void setQuoteAdjustmentVO(int index, edit.common.vo.QuoteAdjustmentVO vQuoteAdjustmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _quoteAdjustmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vQuoteAdjustmentVO.setParentVO(this.getClass(), this);
        _quoteAdjustmentVOList.setElementAt(vQuoteAdjustmentVO, index);
    } //-- void setQuoteAdjustmentVO(int, edit.common.vo.QuoteAdjustmentVO) 

    /**
     * Method setQuoteAdjustmentVO
     * 
     * @param quoteAdjustmentVOArray
     */
    public void setQuoteAdjustmentVO(edit.common.vo.QuoteAdjustmentVO[] quoteAdjustmentVOArray)
    {
        //-- copy array
        _quoteAdjustmentVOList.removeAllElements();
        for (int i = 0; i < quoteAdjustmentVOArray.length; i++) {
            quoteAdjustmentVOArray[i].setParentVO(this.getClass(), this);
            _quoteAdjustmentVOList.addElement(quoteAdjustmentVOArray[i]);
        }
    } //-- void setQuoteAdjustmentVO(edit.common.vo.QuoteAdjustmentVO) 

    /**
     * Method setQuoteDateSets the value of field 'quoteDate'.
     * 
     * @param quoteDate the value of field 'quoteDate'.
     */
    public void setQuoteDate(java.lang.String quoteDate)
    {
        this._quoteDate = quoteDate;
        
        super.setVoChanged(true);
    } //-- void setQuoteDate(java.lang.String) 

    /**
     * Method setQuoteInvestmentVO
     * 
     * @param index
     * @param vQuoteInvestmentVO
     */
    public void setQuoteInvestmentVO(int index, edit.common.vo.QuoteInvestmentVO vQuoteInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _quoteInvestmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vQuoteInvestmentVO.setParentVO(this.getClass(), this);
        _quoteInvestmentVOList.setElementAt(vQuoteInvestmentVO, index);
    } //-- void setQuoteInvestmentVO(int, edit.common.vo.QuoteInvestmentVO) 

    /**
     * Method setQuoteInvestmentVO
     * 
     * @param quoteInvestmentVOArray
     */
    public void setQuoteInvestmentVO(edit.common.vo.QuoteInvestmentVO[] quoteInvestmentVOArray)
    {
        //-- copy array
        _quoteInvestmentVOList.removeAllElements();
        for (int i = 0; i < quoteInvestmentVOArray.length; i++) {
            quoteInvestmentVOArray[i].setParentVO(this.getClass(), this);
            _quoteInvestmentVOList.addElement(quoteInvestmentVOArray[i]);
        }
    } //-- void setQuoteInvestmentVO(edit.common.vo.QuoteInvestmentVO) 

    /**
     * Method setQuoteTypeCTSets the value of field 'quoteTypeCT'.
     * 
     * @param quoteTypeCT the value of field 'quoteTypeCT'.
     */
    public void setQuoteTypeCT(java.lang.String quoteTypeCT)
    {
        this._quoteTypeCT = quoteTypeCT;
        
        super.setVoChanged(true);
    } //-- void setQuoteTypeCT(java.lang.String) 

    /**
     * Method setReducedPaidUpAmountSets the value of field
     * 'reducedPaidUpAmount'.
     * 
     * @param reducedPaidUpAmount the value of field
     * 'reducedPaidUpAmount'.
     */
    public void setReducedPaidUpAmount(java.math.BigDecimal reducedPaidUpAmount)
    {
        this._reducedPaidUpAmount = reducedPaidUpAmount;
        
        super.setVoChanged(true);
    } //-- void setReducedPaidUpAmount(java.math.BigDecimal) 

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
     * Method setSurrenderChargeSets the value of field
     * 'surrenderCharge'.
     * 
     * @param surrenderCharge the value of field 'surrenderCharge'.
     */
    public void setSurrenderCharge(java.math.BigDecimal surrenderCharge)
    {
        this._surrenderCharge = surrenderCharge;
        
        super.setVoChanged(true);
    } //-- void setSurrenderCharge(java.math.BigDecimal) 

    /**
     * Method setSurrenderValueSets the value of field
     * 'surrenderValue'.
     * 
     * @param surrenderValue the value of field 'surrenderValue'.
     */
    public void setSurrenderValue(java.math.BigDecimal surrenderValue)
    {
        this._surrenderValue = surrenderValue;
        
        super.setVoChanged(true);
    } //-- void setSurrenderValue(java.math.BigDecimal) 

    /**
     * Method setTILienBalanceSets the value of field
     * 'TILienBalance'.
     * 
     * @param TILienBalance the value of field 'TILienBalance'.
     */
    public void setTILienBalance(java.math.BigDecimal TILienBalance)
    {
        this._TILienBalance = TILienBalance;
        
        super.setVoChanged(true);
    } //-- void setTILienBalance(java.math.BigDecimal) 

    /**
     * Method setUnlockedCashValueSets the value of field
     * 'unlockedCashValue'.
     * 
     * @param unlockedCashValue the value of field
     * 'unlockedCashValue'.
     */
    public void setUnlockedCashValue(java.math.BigDecimal unlockedCashValue)
    {
        this._unlockedCashValue = unlockedCashValue;
        
        super.setVoChanged(true);
    } //-- void setUnlockedCashValue(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.QuoteVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.QuoteVO) Unmarshaller.unmarshal(edit.common.vo.QuoteVO.class, reader);
    } //-- edit.common.vo.QuoteVO unmarshal(java.io.Reader) 

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
