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
 * Class PremiumDueVO.
 * 
 * @version $Revision$ $Date$
 */
public class PremiumDueVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _premiumDuePK
     */
    private long _premiumDuePK;

    /**
     * keeps track of state for field: _premiumDuePK
     */
    private boolean _has_premiumDuePK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _billAmount
     */
    private java.math.BigDecimal _billAmount;

    /**
     * Field _deductionAmount
     */
    private java.math.BigDecimal _deductionAmount;

    /**
     * Field _numberOfDeductions
     */
    private int _numberOfDeductions;

    /**
     * keeps track of state for field: _numberOfDeductions
     */
    private boolean _has_numberOfDeductions;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _pendingExtractIndicator
     */
    private java.lang.String _pendingExtractIndicator;

    /**
     * Field _priorBillAmount
     */
    private java.math.BigDecimal _priorBillAmount;

    /**
     * Field _priorDeductionAmount
     */
    private java.math.BigDecimal _priorDeductionAmount;

    /**
     * Field _adjustmentAmount
     */
    private java.math.BigDecimal _adjustmentAmount;

    /**
     * Field _deductionAmountOverride
     */
    private java.math.BigDecimal _deductionAmountOverride;

    /**
     * Field _billAmountOverride
     */
    private java.math.BigDecimal _billAmountOverride;

    /**
     * Field _commissionPhaseVOList
     */
    private java.util.Vector _commissionPhaseVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PremiumDueVO() {
        super();
        _commissionPhaseVOList = new Vector();
    } //-- edit.common.vo.PremiumDueVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addCommissionPhaseVO
     * 
     * @param vCommissionPhaseVO
     */
    public void addCommissionPhaseVO(edit.common.vo.CommissionPhaseVO vCommissionPhaseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionPhaseVO.setParentVO(this.getClass(), this);
        _commissionPhaseVOList.addElement(vCommissionPhaseVO);
    } //-- void addCommissionPhaseVO(edit.common.vo.CommissionPhaseVO) 

    /**
     * Method addCommissionPhaseVO
     * 
     * @param index
     * @param vCommissionPhaseVO
     */
    public void addCommissionPhaseVO(int index, edit.common.vo.CommissionPhaseVO vCommissionPhaseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionPhaseVO.setParentVO(this.getClass(), this);
        _commissionPhaseVOList.insertElementAt(vCommissionPhaseVO, index);
    } //-- void addCommissionPhaseVO(int, edit.common.vo.CommissionPhaseVO) 

    /**
     * Method enumerateCommissionPhaseVO
     */
    public java.util.Enumeration enumerateCommissionPhaseVO()
    {
        return _commissionPhaseVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionPhaseVO() 

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
        
        if (obj instanceof PremiumDueVO) {
        
            PremiumDueVO temp = (PremiumDueVO)obj;
            if (this._premiumDuePK != temp._premiumDuePK)
                return false;
            if (this._has_premiumDuePK != temp._has_premiumDuePK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._billAmount != null) {
                if (temp._billAmount == null) return false;
                else if (!(this._billAmount.equals(temp._billAmount))) 
                    return false;
            }
            else if (temp._billAmount != null)
                return false;
            if (this._deductionAmount != null) {
                if (temp._deductionAmount == null) return false;
                else if (!(this._deductionAmount.equals(temp._deductionAmount))) 
                    return false;
            }
            else if (temp._deductionAmount != null)
                return false;
            if (this._numberOfDeductions != temp._numberOfDeductions)
                return false;
            if (this._has_numberOfDeductions != temp._has_numberOfDeductions)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._pendingExtractIndicator != null) {
                if (temp._pendingExtractIndicator == null) return false;
                else if (!(this._pendingExtractIndicator.equals(temp._pendingExtractIndicator))) 
                    return false;
            }
            else if (temp._pendingExtractIndicator != null)
                return false;
            if (this._priorBillAmount != null) {
                if (temp._priorBillAmount == null) return false;
                else if (!(this._priorBillAmount.equals(temp._priorBillAmount))) 
                    return false;
            }
            else if (temp._priorBillAmount != null)
                return false;
            if (this._priorDeductionAmount != null) {
                if (temp._priorDeductionAmount == null) return false;
                else if (!(this._priorDeductionAmount.equals(temp._priorDeductionAmount))) 
                    return false;
            }
            else if (temp._priorDeductionAmount != null)
                return false;
            if (this._adjustmentAmount != null) {
                if (temp._adjustmentAmount == null) return false;
                else if (!(this._adjustmentAmount.equals(temp._adjustmentAmount))) 
                    return false;
            }
            else if (temp._adjustmentAmount != null)
                return false;
            if (this._deductionAmountOverride != null) {
                if (temp._deductionAmountOverride == null) return false;
                else if (!(this._deductionAmountOverride.equals(temp._deductionAmountOverride))) 
                    return false;
            }
            else if (temp._deductionAmountOverride != null)
                return false;
            if (this._billAmountOverride != null) {
                if (temp._billAmountOverride == null) return false;
                else if (!(this._billAmountOverride.equals(temp._billAmountOverride))) 
                    return false;
            }
            else if (temp._billAmountOverride != null)
                return false;
            if (this._commissionPhaseVOList != null) {
                if (temp._commissionPhaseVOList == null) return false;
                else if (!(this._commissionPhaseVOList.equals(temp._commissionPhaseVOList))) 
                    return false;
            }
            else if (temp._commissionPhaseVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdjustmentAmountReturns the value of field
     * 'adjustmentAmount'.
     * 
     * @return the value of field 'adjustmentAmount'.
     */
    public java.math.BigDecimal getAdjustmentAmount()
    {
        return this._adjustmentAmount;
    } //-- java.math.BigDecimal getAdjustmentAmount() 

    /**
     * Method getBillAmountReturns the value of field 'billAmount'.
     * 
     * @return the value of field 'billAmount'.
     */
    public java.math.BigDecimal getBillAmount()
    {
        return this._billAmount;
    } //-- java.math.BigDecimal getBillAmount() 

    /**
     * Method getBillAmountOverrideReturns the value of field
     * 'billAmountOverride'.
     * 
     * @return the value of field 'billAmountOverride'.
     */
    public java.math.BigDecimal getBillAmountOverride()
    {
        return this._billAmountOverride;
    } //-- java.math.BigDecimal getBillAmountOverride() 

    /**
     * Method getCommissionPhaseVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionPhaseVO getCommissionPhaseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionPhaseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionPhaseVO) _commissionPhaseVOList.elementAt(index);
    } //-- edit.common.vo.CommissionPhaseVO getCommissionPhaseVO(int) 

    /**
     * Method getCommissionPhaseVO
     */
    public edit.common.vo.CommissionPhaseVO[] getCommissionPhaseVO()
    {
        int size = _commissionPhaseVOList.size();
        edit.common.vo.CommissionPhaseVO[] mArray = new edit.common.vo.CommissionPhaseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionPhaseVO) _commissionPhaseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionPhaseVO[] getCommissionPhaseVO() 

    /**
     * Method getCommissionPhaseVOCount
     */
    public int getCommissionPhaseVOCount()
    {
        return _commissionPhaseVOList.size();
    } //-- int getCommissionPhaseVOCount() 

    /**
     * Method getDeductionAmountReturns the value of field
     * 'deductionAmount'.
     * 
     * @return the value of field 'deductionAmount'.
     */
    public java.math.BigDecimal getDeductionAmount()
    {
        return this._deductionAmount;
    } //-- java.math.BigDecimal getDeductionAmount() 

    /**
     * Method getDeductionAmountOverrideReturns the value of field
     * 'deductionAmountOverride'.
     * 
     * @return the value of field 'deductionAmountOverride'.
     */
    public java.math.BigDecimal getDeductionAmountOverride()
    {
        return this._deductionAmountOverride;
    } //-- java.math.BigDecimal getDeductionAmountOverride() 

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
     * Method getNumberOfDeductionsReturns the value of field
     * 'numberOfDeductions'.
     * 
     * @return the value of field 'numberOfDeductions'.
     */
    public int getNumberOfDeductions()
    {
        return this._numberOfDeductions;
    } //-- int getNumberOfDeductions() 

    /**
     * Method getPendingExtractIndicatorReturns the value of field
     * 'pendingExtractIndicator'.
     * 
     * @return the value of field 'pendingExtractIndicator'.
     */
    public java.lang.String getPendingExtractIndicator()
    {
        return this._pendingExtractIndicator;
    } //-- java.lang.String getPendingExtractIndicator() 

    /**
     * Method getPremiumDuePKReturns the value of field
     * 'premiumDuePK'.
     * 
     * @return the value of field 'premiumDuePK'.
     */
    public long getPremiumDuePK()
    {
        return this._premiumDuePK;
    } //-- long getPremiumDuePK() 

    /**
     * Method getPriorBillAmountReturns the value of field
     * 'priorBillAmount'.
     * 
     * @return the value of field 'priorBillAmount'.
     */
    public java.math.BigDecimal getPriorBillAmount()
    {
        return this._priorBillAmount;
    } //-- java.math.BigDecimal getPriorBillAmount() 

    /**
     * Method getPriorDeductionAmountReturns the value of field
     * 'priorDeductionAmount'.
     * 
     * @return the value of field 'priorDeductionAmount'.
     */
    public java.math.BigDecimal getPriorDeductionAmount()
    {
        return this._priorDeductionAmount;
    } //-- java.math.BigDecimal getPriorDeductionAmount() 

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
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasNumberOfDeductions
     */
    public boolean hasNumberOfDeductions()
    {
        return this._has_numberOfDeductions;
    } //-- boolean hasNumberOfDeductions() 

    /**
     * Method hasPremiumDuePK
     */
    public boolean hasPremiumDuePK()
    {
        return this._has_premiumDuePK;
    } //-- boolean hasPremiumDuePK() 

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
     * Method removeAllCommissionPhaseVO
     */
    public void removeAllCommissionPhaseVO()
    {
        _commissionPhaseVOList.removeAllElements();
    } //-- void removeAllCommissionPhaseVO() 

    /**
     * Method removeCommissionPhaseVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionPhaseVO removeCommissionPhaseVO(int index)
    {
        java.lang.Object obj = _commissionPhaseVOList.elementAt(index);
        _commissionPhaseVOList.removeElementAt(index);
        return (edit.common.vo.CommissionPhaseVO) obj;
    } //-- edit.common.vo.CommissionPhaseVO removeCommissionPhaseVO(int) 

    /**
     * Method setAdjustmentAmountSets the value of field
     * 'adjustmentAmount'.
     * 
     * @param adjustmentAmount the value of field 'adjustmentAmount'
     */
    public void setAdjustmentAmount(java.math.BigDecimal adjustmentAmount)
    {
        this._adjustmentAmount = adjustmentAmount;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentAmount(java.math.BigDecimal) 

    /**
     * Method setBillAmountSets the value of field 'billAmount'.
     * 
     * @param billAmount the value of field 'billAmount'.
     */
    public void setBillAmount(java.math.BigDecimal billAmount)
    {
        this._billAmount = billAmount;
        
        super.setVoChanged(true);
    } //-- void setBillAmount(java.math.BigDecimal) 

    /**
     * Method setBillAmountOverrideSets the value of field
     * 'billAmountOverride'.
     * 
     * @param billAmountOverride the value of field
     * 'billAmountOverride'.
     */
    public void setBillAmountOverride(java.math.BigDecimal billAmountOverride)
    {
        this._billAmountOverride = billAmountOverride;
        
        super.setVoChanged(true);
    } //-- void setBillAmountOverride(java.math.BigDecimal) 

    /**
     * Method setCommissionPhaseVO
     * 
     * @param index
     * @param vCommissionPhaseVO
     */
    public void setCommissionPhaseVO(int index, edit.common.vo.CommissionPhaseVO vCommissionPhaseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionPhaseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionPhaseVO.setParentVO(this.getClass(), this);
        _commissionPhaseVOList.setElementAt(vCommissionPhaseVO, index);
    } //-- void setCommissionPhaseVO(int, edit.common.vo.CommissionPhaseVO) 

    /**
     * Method setCommissionPhaseVO
     * 
     * @param commissionPhaseVOArray
     */
    public void setCommissionPhaseVO(edit.common.vo.CommissionPhaseVO[] commissionPhaseVOArray)
    {
        //-- copy array
        _commissionPhaseVOList.removeAllElements();
        for (int i = 0; i < commissionPhaseVOArray.length; i++) {
            commissionPhaseVOArray[i].setParentVO(this.getClass(), this);
            _commissionPhaseVOList.addElement(commissionPhaseVOArray[i]);
        }
    } //-- void setCommissionPhaseVO(edit.common.vo.CommissionPhaseVO) 

    /**
     * Method setDeductionAmountSets the value of field
     * 'deductionAmount'.
     * 
     * @param deductionAmount the value of field 'deductionAmount'.
     */
    public void setDeductionAmount(java.math.BigDecimal deductionAmount)
    {
        this._deductionAmount = deductionAmount;
        
        super.setVoChanged(true);
    } //-- void setDeductionAmount(java.math.BigDecimal) 

    /**
     * Method setDeductionAmountOverrideSets the value of field
     * 'deductionAmountOverride'.
     * 
     * @param deductionAmountOverride the value of field
     * 'deductionAmountOverride'.
     */
    public void setDeductionAmountOverride(java.math.BigDecimal deductionAmountOverride)
    {
        this._deductionAmountOverride = deductionAmountOverride;
        
        super.setVoChanged(true);
    } //-- void setDeductionAmountOverride(java.math.BigDecimal) 

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
     * Method setNumberOfDeductionsSets the value of field
     * 'numberOfDeductions'.
     * 
     * @param numberOfDeductions the value of field
     * 'numberOfDeductions'.
     */
    public void setNumberOfDeductions(int numberOfDeductions)
    {
        this._numberOfDeductions = numberOfDeductions;
        
        super.setVoChanged(true);
        this._has_numberOfDeductions = true;
    } //-- void setNumberOfDeductions(int) 

    /**
     * Method setPendingExtractIndicatorSets the value of field
     * 'pendingExtractIndicator'.
     * 
     * @param pendingExtractIndicator the value of field
     * 'pendingExtractIndicator'.
     */
    public void setPendingExtractIndicator(java.lang.String pendingExtractIndicator)
    {
        this._pendingExtractIndicator = pendingExtractIndicator;
        
        super.setVoChanged(true);
    } //-- void setPendingExtractIndicator(java.lang.String) 

    /**
     * Method setPremiumDuePKSets the value of field
     * 'premiumDuePK'.
     * 
     * @param premiumDuePK the value of field 'premiumDuePK'.
     */
    public void setPremiumDuePK(long premiumDuePK)
    {
        this._premiumDuePK = premiumDuePK;
        
        super.setVoChanged(true);
        this._has_premiumDuePK = true;
    } //-- void setPremiumDuePK(long) 

    /**
     * Method setPriorBillAmountSets the value of field
     * 'priorBillAmount'.
     * 
     * @param priorBillAmount the value of field 'priorBillAmount'.
     */
    public void setPriorBillAmount(java.math.BigDecimal priorBillAmount)
    {
        this._priorBillAmount = priorBillAmount;
        
        super.setVoChanged(true);
    } //-- void setPriorBillAmount(java.math.BigDecimal) 

    /**
     * Method setPriorDeductionAmountSets the value of field
     * 'priorDeductionAmount'.
     * 
     * @param priorDeductionAmount the value of field
     * 'priorDeductionAmount'.
     */
    public void setPriorDeductionAmount(java.math.BigDecimal priorDeductionAmount)
    {
        this._priorDeductionAmount = priorDeductionAmount;
        
        super.setVoChanged(true);
    } //-- void setPriorDeductionAmount(java.math.BigDecimal) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PremiumDueVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PremiumDueVO) Unmarshaller.unmarshal(edit.common.vo.PremiumDueVO.class, reader);
    } //-- edit.common.vo.PremiumDueVO unmarshal(java.io.Reader) 

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
