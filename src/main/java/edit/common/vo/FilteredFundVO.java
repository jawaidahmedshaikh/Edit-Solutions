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
 * Class FilteredFundVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredFundVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredFundPK
     */
    private long _filteredFundPK;

    /**
     * keeps track of state for field: _filteredFundPK
     */
    private boolean _has_filteredFundPK;

    /**
     * Field _fundFK
     */
    private long _fundFK;

    /**
     * keeps track of state for field: _fundFK
     */
    private boolean _has_fundFK;

    /**
     * Field _fundNumber
     */
    private java.lang.String _fundNumber;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _pricingDirection
     */
    private java.lang.String _pricingDirection;

    /**
     * Field _guaranteedDuration
     */
    private int _guaranteedDuration;

    /**
     * keeps track of state for field: _guaranteedDuration
     */
    private boolean _has_guaranteedDuration;

    /**
     * Field _indexingMethodCT
     */
    private java.lang.String _indexingMethodCT;

    /**
     * Field _fundAdjustmentCT
     */
    private java.lang.String _fundAdjustmentCT;

    /**
     * Field _indexCapRateGuarPeriod
     */
    private int _indexCapRateGuarPeriod;

    /**
     * keeps track of state for field: _indexCapRateGuarPeriod
     */
    private boolean _has_indexCapRateGuarPeriod;

    /**
     * Field _premiumBonusDuration
     */
    private int _premiumBonusDuration;

    /**
     * keeps track of state for field: _premiumBonusDuration
     */
    private boolean _has_premiumBonusDuration;

    /**
     * Field _minimumTransferAmount
     */
    private java.math.BigDecimal _minimumTransferAmount;

    /**
     * Field _MVAStartingIndexGuarPeriod
     */
    private int _MVAStartingIndexGuarPeriod;

    /**
     * keeps track of state for field: _MVAStartingIndexGuarPeriod
     */
    private boolean _has_MVAStartingIndexGuarPeriod;

    /**
     * Field _subscriptionNotificationDays
     */
    private int _subscriptionNotificationDays;

    /**
     * keeps track of state for field: _subscriptionNotificationDays
     */
    private boolean _has_subscriptionNotificationDays;

    /**
     * Field _COIReplenishmentDays
     */
    private int _COIReplenishmentDays;

    /**
     * keeps track of state for field: _COIReplenishmentDays
     */
    private boolean _has_COIReplenishmentDays;

    /**
     * Field _deathDays
     */
    private int _deathDays;

    /**
     * keeps track of state for field: _deathDays
     */
    private boolean _has_deathDays;

    /**
     * Field _fullSurrenderDays
     */
    private int _fullSurrenderDays;

    /**
     * keeps track of state for field: _fullSurrenderDays
     */
    private boolean _has_fullSurrenderDays;

    /**
     * Field _withdrawalDays
     */
    private int _withdrawalDays;

    /**
     * keeps track of state for field: _withdrawalDays
     */
    private boolean _has_withdrawalDays;

    /**
     * Field _transferDays
     */
    private int _transferDays;

    /**
     * keeps track of state for field: _transferDays
     */
    private boolean _has_transferDays;

    /**
     * Field _loanDays
     */
    private int _loanDays;

    /**
     * keeps track of state for field: _loanDays
     */
    private boolean _has_loanDays;

    /**
     * Field _divisionFeesLiquidationDays
     */
    private int _divisionFeesLiquidationDays;

    /**
     * keeps track of state for field: _divisionFeesLiquidationDays
     */
    private boolean _has_divisionFeesLiquidationDays;

    /**
     * Field _subscriptionDaysTypeCT
     */
    private java.lang.String _subscriptionDaysTypeCT;

    /**
     * Field _COIReplenishmentDaysTypeCT
     */
    private java.lang.String _COIReplenishmentDaysTypeCT;

    /**
     * Field _deathDaysTypeCT
     */
    private java.lang.String _deathDaysTypeCT;

    /**
     * Field _fullSurrenderDaysTypeCT
     */
    private java.lang.String _fullSurrenderDaysTypeCT;

    /**
     * Field _withdrawalDaysTypeCT
     */
    private java.lang.String _withdrawalDaysTypeCT;

    /**
     * Field _transferDaysTypeCT
     */
    private java.lang.String _transferDaysTypeCT;

    /**
     * Field _loanDaysTypeCT
     */
    private java.lang.String _loanDaysTypeCT;

    /**
     * Field _divFeesLiquidatnDaysTypeCT
     */
    private java.lang.String _divFeesLiquidatnDaysTypeCT;

    /**
     * Field _subscriptionModeCT
     */
    private java.lang.String _subscriptionModeCT;

    /**
     * Field _COIReplenishmentModeCT
     */
    private java.lang.String _COIReplenishmentModeCT;

    /**
     * Field _deathModeCT
     */
    private java.lang.String _deathModeCT;

    /**
     * Field _fullSurrenderModeCT
     */
    private java.lang.String _fullSurrenderModeCT;

    /**
     * Field _withdrawalModeCT
     */
    private java.lang.String _withdrawalModeCT;

    /**
     * Field _transferModeCT
     */
    private java.lang.String _transferModeCT;

    /**
     * Field _loanModeCT
     */
    private java.lang.String _loanModeCT;

    /**
     * Field _divisionFeesLiquidationModeCT
     */
    private java.lang.String _divisionFeesLiquidationModeCT;

    /**
     * Field _holdingAccountFK
     */
    private long _holdingAccountFK;

    /**
     * keeps track of state for field: _holdingAccountFK
     */
    private boolean _has_holdingAccountFK;

    /**
     * Field _annualSubBucketCT
     */
    private java.lang.String _annualSubBucketCT;

    /**
     * Field _fundNewClientCloseDate
     */
    private java.lang.String _fundNewClientCloseDate;

    /**
     * Field _fundNewDepositCloseDate
     */
    private java.lang.String _fundNewDepositCloseDate;

    /**
     * Field _contributionLockUpDuration
     */
    private int _contributionLockUpDuration;

    /**
     * keeps track of state for field: _contributionLockUpDuration
     */
    private boolean _has_contributionLockUpDuration;

    /**
     * Field _divisionLevelLockUpDuration
     */
    private int _divisionLevelLockUpDuration;

    /**
     * keeps track of state for field: _divisionLevelLockUpDuration
     */
    private boolean _has_divisionLevelLockUpDuration;

    /**
     * Field _divisionLockUpEndDate
     */
    private java.lang.String _divisionLockUpEndDate;

    /**
     * Field _separateAccountName
     */
    private java.lang.String _separateAccountName;

    /**
     * Field _postLockWithdrawalDateCT
     */
    private java.lang.String _postLockWithdrawalDateCT;

    /**
     * Field _chargeCodeIndicator
     */
    private java.lang.String _chargeCodeIndicator;

    /**
     * Field _categoryCT
     */
    private java.lang.String _categoryCT;

    /**
     * Field _seriesToSeriesEligibilityInd
     */
    private java.lang.String _seriesToSeriesEligibilityInd;

    /**
     * Field _seriesTransferDays
     */
    private int _seriesTransferDays;

    /**
     * keeps track of state for field: _seriesTransferDays
     */
    private boolean _has_seriesTransferDays;

    /**
     * Field _seriesTransferDaysTypeCT
     */
    private java.lang.String _seriesTransferDaysTypeCT;

    /**
     * Field _seriesTransferModeCT
     */
    private java.lang.String _seriesTransferModeCT;

    /**
     * Field _includeInCorrespondenceInd
     */
    private java.lang.String _includeInCorrespondenceInd;

    /**
     * Field _productFilteredFundStructureVOList
     */
    private java.util.Vector _productFilteredFundStructureVOList;

    /**
     * Field _feeVOList
     */
    private java.util.Vector _feeVOList;

    /**
     * Field _feeDescriptionVOList
     */
    private java.util.Vector _feeDescriptionVOList;

    /**
     * Field _interestRateParametersVOList
     */
    private java.util.Vector _interestRateParametersVOList;

    /**
     * Field _unitValuesVOList
     */
    private java.util.Vector _unitValuesVOList;

    /**
     * Field _chargeCodeVOList
     */
    private java.util.Vector _chargeCodeVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredFundVO() {
        super();
        _productFilteredFundStructureVOList = new Vector();
        _feeVOList = new Vector();
        _feeDescriptionVOList = new Vector();
        _interestRateParametersVOList = new Vector();
        _unitValuesVOList = new Vector();
        _chargeCodeVOList = new Vector();
    } //-- edit.common.vo.FilteredFundVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addChargeCodeVO
     * 
     * @param vChargeCodeVO
     */
    public void addChargeCodeVO(edit.common.vo.ChargeCodeVO vChargeCodeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChargeCodeVO.setParentVO(this.getClass(), this);
        _chargeCodeVOList.addElement(vChargeCodeVO);
    } //-- void addChargeCodeVO(edit.common.vo.ChargeCodeVO) 

    /**
     * Method addChargeCodeVO
     * 
     * @param index
     * @param vChargeCodeVO
     */
    public void addChargeCodeVO(int index, edit.common.vo.ChargeCodeVO vChargeCodeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChargeCodeVO.setParentVO(this.getClass(), this);
        _chargeCodeVOList.insertElementAt(vChargeCodeVO, index);
    } //-- void addChargeCodeVO(int, edit.common.vo.ChargeCodeVO) 

    /**
     * Method addFeeDescriptionVO
     * 
     * @param vFeeDescriptionVO
     */
    public void addFeeDescriptionVO(edit.common.vo.FeeDescriptionVO vFeeDescriptionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeDescriptionVO.setParentVO(this.getClass(), this);
        _feeDescriptionVOList.addElement(vFeeDescriptionVO);
    } //-- void addFeeDescriptionVO(edit.common.vo.FeeDescriptionVO) 

    /**
     * Method addFeeDescriptionVO
     * 
     * @param index
     * @param vFeeDescriptionVO
     */
    public void addFeeDescriptionVO(int index, edit.common.vo.FeeDescriptionVO vFeeDescriptionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeDescriptionVO.setParentVO(this.getClass(), this);
        _feeDescriptionVOList.insertElementAt(vFeeDescriptionVO, index);
    } //-- void addFeeDescriptionVO(int, edit.common.vo.FeeDescriptionVO) 

    /**
     * Method addFeeVO
     * 
     * @param vFeeVO
     */
    public void addFeeVO(edit.common.vo.FeeVO vFeeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeVO.setParentVO(this.getClass(), this);
        _feeVOList.addElement(vFeeVO);
    } //-- void addFeeVO(edit.common.vo.FeeVO) 

    /**
     * Method addFeeVO
     * 
     * @param index
     * @param vFeeVO
     */
    public void addFeeVO(int index, edit.common.vo.FeeVO vFeeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeVO.setParentVO(this.getClass(), this);
        _feeVOList.insertElementAt(vFeeVO, index);
    } //-- void addFeeVO(int, edit.common.vo.FeeVO) 

    /**
     * Method addInterestRateParametersVO
     * 
     * @param vInterestRateParametersVO
     */
    public void addInterestRateParametersVO(edit.common.vo.InterestRateParametersVO vInterestRateParametersVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInterestRateParametersVO.setParentVO(this.getClass(), this);
        _interestRateParametersVOList.addElement(vInterestRateParametersVO);
    } //-- void addInterestRateParametersVO(edit.common.vo.InterestRateParametersVO) 

    /**
     * Method addInterestRateParametersVO
     * 
     * @param index
     * @param vInterestRateParametersVO
     */
    public void addInterestRateParametersVO(int index, edit.common.vo.InterestRateParametersVO vInterestRateParametersVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInterestRateParametersVO.setParentVO(this.getClass(), this);
        _interestRateParametersVOList.insertElementAt(vInterestRateParametersVO, index);
    } //-- void addInterestRateParametersVO(int, edit.common.vo.InterestRateParametersVO) 

    /**
     * Method addProductFilteredFundStructureVO
     * 
     * @param vProductFilteredFundStructureVO
     */
    public void addProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO vProductFilteredFundStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductFilteredFundStructureVO.setParentVO(this.getClass(), this);
        _productFilteredFundStructureVOList.addElement(vProductFilteredFundStructureVO);
    } //-- void addProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method addProductFilteredFundStructureVO
     * 
     * @param index
     * @param vProductFilteredFundStructureVO
     */
    public void addProductFilteredFundStructureVO(int index, edit.common.vo.ProductFilteredFundStructureVO vProductFilteredFundStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductFilteredFundStructureVO.setParentVO(this.getClass(), this);
        _productFilteredFundStructureVOList.insertElementAt(vProductFilteredFundStructureVO, index);
    } //-- void addProductFilteredFundStructureVO(int, edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method addUnitValuesVO
     * 
     * @param vUnitValuesVO
     */
    public void addUnitValuesVO(edit.common.vo.UnitValuesVO vUnitValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUnitValuesVO.setParentVO(this.getClass(), this);
        _unitValuesVOList.addElement(vUnitValuesVO);
    } //-- void addUnitValuesVO(edit.common.vo.UnitValuesVO) 

    /**
     * Method addUnitValuesVO
     * 
     * @param index
     * @param vUnitValuesVO
     */
    public void addUnitValuesVO(int index, edit.common.vo.UnitValuesVO vUnitValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUnitValuesVO.setParentVO(this.getClass(), this);
        _unitValuesVOList.insertElementAt(vUnitValuesVO, index);
    } //-- void addUnitValuesVO(int, edit.common.vo.UnitValuesVO) 

    /**
     * Method enumerateChargeCodeVO
     */
    public java.util.Enumeration enumerateChargeCodeVO()
    {
        return _chargeCodeVOList.elements();
    } //-- java.util.Enumeration enumerateChargeCodeVO() 

    /**
     * Method enumerateFeeDescriptionVO
     */
    public java.util.Enumeration enumerateFeeDescriptionVO()
    {
        return _feeDescriptionVOList.elements();
    } //-- java.util.Enumeration enumerateFeeDescriptionVO() 

    /**
     * Method enumerateFeeVO
     */
    public java.util.Enumeration enumerateFeeVO()
    {
        return _feeVOList.elements();
    } //-- java.util.Enumeration enumerateFeeVO() 

    /**
     * Method enumerateInterestRateParametersVO
     */
    public java.util.Enumeration enumerateInterestRateParametersVO()
    {
        return _interestRateParametersVOList.elements();
    } //-- java.util.Enumeration enumerateInterestRateParametersVO() 

    /**
     * Method enumerateProductFilteredFundStructureVO
     */
    public java.util.Enumeration enumerateProductFilteredFundStructureVO()
    {
        return _productFilteredFundStructureVOList.elements();
    } //-- java.util.Enumeration enumerateProductFilteredFundStructureVO() 

    /**
     * Method enumerateUnitValuesVO
     */
    public java.util.Enumeration enumerateUnitValuesVO()
    {
        return _unitValuesVOList.elements();
    } //-- java.util.Enumeration enumerateUnitValuesVO() 

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
        
        if (obj instanceof FilteredFundVO) {
        
            FilteredFundVO temp = (FilteredFundVO)obj;
            if (this._filteredFundPK != temp._filteredFundPK)
                return false;
            if (this._has_filteredFundPK != temp._has_filteredFundPK)
                return false;
            if (this._fundFK != temp._fundFK)
                return false;
            if (this._has_fundFK != temp._has_fundFK)
                return false;
            if (this._fundNumber != null) {
                if (temp._fundNumber == null) return false;
                else if (!(this._fundNumber.equals(temp._fundNumber))) 
                    return false;
            }
            else if (temp._fundNumber != null)
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
            if (this._pricingDirection != null) {
                if (temp._pricingDirection == null) return false;
                else if (!(this._pricingDirection.equals(temp._pricingDirection))) 
                    return false;
            }
            else if (temp._pricingDirection != null)
                return false;
            if (this._guaranteedDuration != temp._guaranteedDuration)
                return false;
            if (this._has_guaranteedDuration != temp._has_guaranteedDuration)
                return false;
            if (this._indexingMethodCT != null) {
                if (temp._indexingMethodCT == null) return false;
                else if (!(this._indexingMethodCT.equals(temp._indexingMethodCT))) 
                    return false;
            }
            else if (temp._indexingMethodCT != null)
                return false;
            if (this._fundAdjustmentCT != null) {
                if (temp._fundAdjustmentCT == null) return false;
                else if (!(this._fundAdjustmentCT.equals(temp._fundAdjustmentCT))) 
                    return false;
            }
            else if (temp._fundAdjustmentCT != null)
                return false;
            if (this._indexCapRateGuarPeriod != temp._indexCapRateGuarPeriod)
                return false;
            if (this._has_indexCapRateGuarPeriod != temp._has_indexCapRateGuarPeriod)
                return false;
            if (this._premiumBonusDuration != temp._premiumBonusDuration)
                return false;
            if (this._has_premiumBonusDuration != temp._has_premiumBonusDuration)
                return false;
            if (this._minimumTransferAmount != null) {
                if (temp._minimumTransferAmount == null) return false;
                else if (!(this._minimumTransferAmount.equals(temp._minimumTransferAmount))) 
                    return false;
            }
            else if (temp._minimumTransferAmount != null)
                return false;
            if (this._MVAStartingIndexGuarPeriod != temp._MVAStartingIndexGuarPeriod)
                return false;
            if (this._has_MVAStartingIndexGuarPeriod != temp._has_MVAStartingIndexGuarPeriod)
                return false;
            if (this._subscriptionNotificationDays != temp._subscriptionNotificationDays)
                return false;
            if (this._has_subscriptionNotificationDays != temp._has_subscriptionNotificationDays)
                return false;
            if (this._COIReplenishmentDays != temp._COIReplenishmentDays)
                return false;
            if (this._has_COIReplenishmentDays != temp._has_COIReplenishmentDays)
                return false;
            if (this._deathDays != temp._deathDays)
                return false;
            if (this._has_deathDays != temp._has_deathDays)
                return false;
            if (this._fullSurrenderDays != temp._fullSurrenderDays)
                return false;
            if (this._has_fullSurrenderDays != temp._has_fullSurrenderDays)
                return false;
            if (this._withdrawalDays != temp._withdrawalDays)
                return false;
            if (this._has_withdrawalDays != temp._has_withdrawalDays)
                return false;
            if (this._transferDays != temp._transferDays)
                return false;
            if (this._has_transferDays != temp._has_transferDays)
                return false;
            if (this._loanDays != temp._loanDays)
                return false;
            if (this._has_loanDays != temp._has_loanDays)
                return false;
            if (this._divisionFeesLiquidationDays != temp._divisionFeesLiquidationDays)
                return false;
            if (this._has_divisionFeesLiquidationDays != temp._has_divisionFeesLiquidationDays)
                return false;
            if (this._subscriptionDaysTypeCT != null) {
                if (temp._subscriptionDaysTypeCT == null) return false;
                else if (!(this._subscriptionDaysTypeCT.equals(temp._subscriptionDaysTypeCT))) 
                    return false;
            }
            else if (temp._subscriptionDaysTypeCT != null)
                return false;
            if (this._COIReplenishmentDaysTypeCT != null) {
                if (temp._COIReplenishmentDaysTypeCT == null) return false;
                else if (!(this._COIReplenishmentDaysTypeCT.equals(temp._COIReplenishmentDaysTypeCT))) 
                    return false;
            }
            else if (temp._COIReplenishmentDaysTypeCT != null)
                return false;
            if (this._deathDaysTypeCT != null) {
                if (temp._deathDaysTypeCT == null) return false;
                else if (!(this._deathDaysTypeCT.equals(temp._deathDaysTypeCT))) 
                    return false;
            }
            else if (temp._deathDaysTypeCT != null)
                return false;
            if (this._fullSurrenderDaysTypeCT != null) {
                if (temp._fullSurrenderDaysTypeCT == null) return false;
                else if (!(this._fullSurrenderDaysTypeCT.equals(temp._fullSurrenderDaysTypeCT))) 
                    return false;
            }
            else if (temp._fullSurrenderDaysTypeCT != null)
                return false;
            if (this._withdrawalDaysTypeCT != null) {
                if (temp._withdrawalDaysTypeCT == null) return false;
                else if (!(this._withdrawalDaysTypeCT.equals(temp._withdrawalDaysTypeCT))) 
                    return false;
            }
            else if (temp._withdrawalDaysTypeCT != null)
                return false;
            if (this._transferDaysTypeCT != null) {
                if (temp._transferDaysTypeCT == null) return false;
                else if (!(this._transferDaysTypeCT.equals(temp._transferDaysTypeCT))) 
                    return false;
            }
            else if (temp._transferDaysTypeCT != null)
                return false;
            if (this._loanDaysTypeCT != null) {
                if (temp._loanDaysTypeCT == null) return false;
                else if (!(this._loanDaysTypeCT.equals(temp._loanDaysTypeCT))) 
                    return false;
            }
            else if (temp._loanDaysTypeCT != null)
                return false;
            if (this._divFeesLiquidatnDaysTypeCT != null) {
                if (temp._divFeesLiquidatnDaysTypeCT == null) return false;
                else if (!(this._divFeesLiquidatnDaysTypeCT.equals(temp._divFeesLiquidatnDaysTypeCT))) 
                    return false;
            }
            else if (temp._divFeesLiquidatnDaysTypeCT != null)
                return false;
            if (this._subscriptionModeCT != null) {
                if (temp._subscriptionModeCT == null) return false;
                else if (!(this._subscriptionModeCT.equals(temp._subscriptionModeCT))) 
                    return false;
            }
            else if (temp._subscriptionModeCT != null)
                return false;
            if (this._COIReplenishmentModeCT != null) {
                if (temp._COIReplenishmentModeCT == null) return false;
                else if (!(this._COIReplenishmentModeCT.equals(temp._COIReplenishmentModeCT))) 
                    return false;
            }
            else if (temp._COIReplenishmentModeCT != null)
                return false;
            if (this._deathModeCT != null) {
                if (temp._deathModeCT == null) return false;
                else if (!(this._deathModeCT.equals(temp._deathModeCT))) 
                    return false;
            }
            else if (temp._deathModeCT != null)
                return false;
            if (this._fullSurrenderModeCT != null) {
                if (temp._fullSurrenderModeCT == null) return false;
                else if (!(this._fullSurrenderModeCT.equals(temp._fullSurrenderModeCT))) 
                    return false;
            }
            else if (temp._fullSurrenderModeCT != null)
                return false;
            if (this._withdrawalModeCT != null) {
                if (temp._withdrawalModeCT == null) return false;
                else if (!(this._withdrawalModeCT.equals(temp._withdrawalModeCT))) 
                    return false;
            }
            else if (temp._withdrawalModeCT != null)
                return false;
            if (this._transferModeCT != null) {
                if (temp._transferModeCT == null) return false;
                else if (!(this._transferModeCT.equals(temp._transferModeCT))) 
                    return false;
            }
            else if (temp._transferModeCT != null)
                return false;
            if (this._loanModeCT != null) {
                if (temp._loanModeCT == null) return false;
                else if (!(this._loanModeCT.equals(temp._loanModeCT))) 
                    return false;
            }
            else if (temp._loanModeCT != null)
                return false;
            if (this._divisionFeesLiquidationModeCT != null) {
                if (temp._divisionFeesLiquidationModeCT == null) return false;
                else if (!(this._divisionFeesLiquidationModeCT.equals(temp._divisionFeesLiquidationModeCT))) 
                    return false;
            }
            else if (temp._divisionFeesLiquidationModeCT != null)
                return false;
            if (this._holdingAccountFK != temp._holdingAccountFK)
                return false;
            if (this._has_holdingAccountFK != temp._has_holdingAccountFK)
                return false;
            if (this._annualSubBucketCT != null) {
                if (temp._annualSubBucketCT == null) return false;
                else if (!(this._annualSubBucketCT.equals(temp._annualSubBucketCT))) 
                    return false;
            }
            else if (temp._annualSubBucketCT != null)
                return false;
            if (this._fundNewClientCloseDate != null) {
                if (temp._fundNewClientCloseDate == null) return false;
                else if (!(this._fundNewClientCloseDate.equals(temp._fundNewClientCloseDate))) 
                    return false;
            }
            else if (temp._fundNewClientCloseDate != null)
                return false;
            if (this._fundNewDepositCloseDate != null) {
                if (temp._fundNewDepositCloseDate == null) return false;
                else if (!(this._fundNewDepositCloseDate.equals(temp._fundNewDepositCloseDate))) 
                    return false;
            }
            else if (temp._fundNewDepositCloseDate != null)
                return false;
            if (this._contributionLockUpDuration != temp._contributionLockUpDuration)
                return false;
            if (this._has_contributionLockUpDuration != temp._has_contributionLockUpDuration)
                return false;
            if (this._divisionLevelLockUpDuration != temp._divisionLevelLockUpDuration)
                return false;
            if (this._has_divisionLevelLockUpDuration != temp._has_divisionLevelLockUpDuration)
                return false;
            if (this._divisionLockUpEndDate != null) {
                if (temp._divisionLockUpEndDate == null) return false;
                else if (!(this._divisionLockUpEndDate.equals(temp._divisionLockUpEndDate))) 
                    return false;
            }
            else if (temp._divisionLockUpEndDate != null)
                return false;
            if (this._separateAccountName != null) {
                if (temp._separateAccountName == null) return false;
                else if (!(this._separateAccountName.equals(temp._separateAccountName))) 
                    return false;
            }
            else if (temp._separateAccountName != null)
                return false;
            if (this._postLockWithdrawalDateCT != null) {
                if (temp._postLockWithdrawalDateCT == null) return false;
                else if (!(this._postLockWithdrawalDateCT.equals(temp._postLockWithdrawalDateCT))) 
                    return false;
            }
            else if (temp._postLockWithdrawalDateCT != null)
                return false;
            if (this._chargeCodeIndicator != null) {
                if (temp._chargeCodeIndicator == null) return false;
                else if (!(this._chargeCodeIndicator.equals(temp._chargeCodeIndicator))) 
                    return false;
            }
            else if (temp._chargeCodeIndicator != null)
                return false;
            if (this._categoryCT != null) {
                if (temp._categoryCT == null) return false;
                else if (!(this._categoryCT.equals(temp._categoryCT))) 
                    return false;
            }
            else if (temp._categoryCT != null)
                return false;
            if (this._seriesToSeriesEligibilityInd != null) {
                if (temp._seriesToSeriesEligibilityInd == null) return false;
                else if (!(this._seriesToSeriesEligibilityInd.equals(temp._seriesToSeriesEligibilityInd))) 
                    return false;
            }
            else if (temp._seriesToSeriesEligibilityInd != null)
                return false;
            if (this._seriesTransferDays != temp._seriesTransferDays)
                return false;
            if (this._has_seriesTransferDays != temp._has_seriesTransferDays)
                return false;
            if (this._seriesTransferDaysTypeCT != null) {
                if (temp._seriesTransferDaysTypeCT == null) return false;
                else if (!(this._seriesTransferDaysTypeCT.equals(temp._seriesTransferDaysTypeCT))) 
                    return false;
            }
            else if (temp._seriesTransferDaysTypeCT != null)
                return false;
            if (this._seriesTransferModeCT != null) {
                if (temp._seriesTransferModeCT == null) return false;
                else if (!(this._seriesTransferModeCT.equals(temp._seriesTransferModeCT))) 
                    return false;
            }
            else if (temp._seriesTransferModeCT != null)
                return false;
            if (this._includeInCorrespondenceInd != null) {
                if (temp._includeInCorrespondenceInd == null) return false;
                else if (!(this._includeInCorrespondenceInd.equals(temp._includeInCorrespondenceInd))) 
                    return false;
            }
            else if (temp._includeInCorrespondenceInd != null)
                return false;
            if (this._productFilteredFundStructureVOList != null) {
                if (temp._productFilteredFundStructureVOList == null) return false;
                else if (!(this._productFilteredFundStructureVOList.equals(temp._productFilteredFundStructureVOList))) 
                    return false;
            }
            else if (temp._productFilteredFundStructureVOList != null)
                return false;
            if (this._feeVOList != null) {
                if (temp._feeVOList == null) return false;
                else if (!(this._feeVOList.equals(temp._feeVOList))) 
                    return false;
            }
            else if (temp._feeVOList != null)
                return false;
            if (this._feeDescriptionVOList != null) {
                if (temp._feeDescriptionVOList == null) return false;
                else if (!(this._feeDescriptionVOList.equals(temp._feeDescriptionVOList))) 
                    return false;
            }
            else if (temp._feeDescriptionVOList != null)
                return false;
            if (this._interestRateParametersVOList != null) {
                if (temp._interestRateParametersVOList == null) return false;
                else if (!(this._interestRateParametersVOList.equals(temp._interestRateParametersVOList))) 
                    return false;
            }
            else if (temp._interestRateParametersVOList != null)
                return false;
            if (this._unitValuesVOList != null) {
                if (temp._unitValuesVOList == null) return false;
                else if (!(this._unitValuesVOList.equals(temp._unitValuesVOList))) 
                    return false;
            }
            else if (temp._unitValuesVOList != null)
                return false;
            if (this._chargeCodeVOList != null) {
                if (temp._chargeCodeVOList == null) return false;
                else if (!(this._chargeCodeVOList.equals(temp._chargeCodeVOList))) 
                    return false;
            }
            else if (temp._chargeCodeVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAnnualSubBucketCTReturns the value of field
     * 'annualSubBucketCT'.
     * 
     * @return the value of field 'annualSubBucketCT'.
     */
    public java.lang.String getAnnualSubBucketCT()
    {
        return this._annualSubBucketCT;
    } //-- java.lang.String getAnnualSubBucketCT() 

    /**
     * Method getCOIReplenishmentDaysReturns the value of field
     * 'COIReplenishmentDays'.
     * 
     * @return the value of field 'COIReplenishmentDays'.
     */
    public int getCOIReplenishmentDays()
    {
        return this._COIReplenishmentDays;
    } //-- int getCOIReplenishmentDays() 

    /**
     * Method getCOIReplenishmentDaysTypeCTReturns the value of
     * field 'COIReplenishmentDaysTypeCT'.
     * 
     * @return the value of field 'COIReplenishmentDaysTypeCT'.
     */
    public java.lang.String getCOIReplenishmentDaysTypeCT()
    {
        return this._COIReplenishmentDaysTypeCT;
    } //-- java.lang.String getCOIReplenishmentDaysTypeCT() 

    /**
     * Method getCOIReplenishmentModeCTReturns the value of field
     * 'COIReplenishmentModeCT'.
     * 
     * @return the value of field 'COIReplenishmentModeCT'.
     */
    public java.lang.String getCOIReplenishmentModeCT()
    {
        return this._COIReplenishmentModeCT;
    } //-- java.lang.String getCOIReplenishmentModeCT() 

    /**
     * Method getCategoryCTReturns the value of field 'categoryCT'.
     * 
     * @return the value of field 'categoryCT'.
     */
    public java.lang.String getCategoryCT()
    {
        return this._categoryCT;
    } //-- java.lang.String getCategoryCT() 

    /**
     * Method getChargeCodeIndicatorReturns the value of field
     * 'chargeCodeIndicator'.
     * 
     * @return the value of field 'chargeCodeIndicator'.
     */
    public java.lang.String getChargeCodeIndicator()
    {
        return this._chargeCodeIndicator;
    } //-- java.lang.String getChargeCodeIndicator() 

    /**
     * Method getChargeCodeVO
     * 
     * @param index
     */
    public edit.common.vo.ChargeCodeVO getChargeCodeVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _chargeCodeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ChargeCodeVO) _chargeCodeVOList.elementAt(index);
    } //-- edit.common.vo.ChargeCodeVO getChargeCodeVO(int) 

    /**
     * Method getChargeCodeVO
     */
    public edit.common.vo.ChargeCodeVO[] getChargeCodeVO()
    {
        int size = _chargeCodeVOList.size();
        edit.common.vo.ChargeCodeVO[] mArray = new edit.common.vo.ChargeCodeVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ChargeCodeVO) _chargeCodeVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ChargeCodeVO[] getChargeCodeVO() 

    /**
     * Method getChargeCodeVOCount
     */
    public int getChargeCodeVOCount()
    {
        return _chargeCodeVOList.size();
    } //-- int getChargeCodeVOCount() 

    /**
     * Method getContributionLockUpDurationReturns the value of
     * field 'contributionLockUpDuration'.
     * 
     * @return the value of field 'contributionLockUpDuration'.
     */
    public int getContributionLockUpDuration()
    {
        return this._contributionLockUpDuration;
    } //-- int getContributionLockUpDuration() 

    /**
     * Method getDeathDaysReturns the value of field 'deathDays'.
     * 
     * @return the value of field 'deathDays'.
     */
    public int getDeathDays()
    {
        return this._deathDays;
    } //-- int getDeathDays() 

    /**
     * Method getDeathDaysTypeCTReturns the value of field
     * 'deathDaysTypeCT'.
     * 
     * @return the value of field 'deathDaysTypeCT'.
     */
    public java.lang.String getDeathDaysTypeCT()
    {
        return this._deathDaysTypeCT;
    } //-- java.lang.String getDeathDaysTypeCT() 

    /**
     * Method getDeathModeCTReturns the value of field
     * 'deathModeCT'.
     * 
     * @return the value of field 'deathModeCT'.
     */
    public java.lang.String getDeathModeCT()
    {
        return this._deathModeCT;
    } //-- java.lang.String getDeathModeCT() 

    /**
     * Method getDivFeesLiquidatnDaysTypeCTReturns the value of
     * field 'divFeesLiquidatnDaysTypeCT'.
     * 
     * @return the value of field 'divFeesLiquidatnDaysTypeCT'.
     */
    public java.lang.String getDivFeesLiquidatnDaysTypeCT()
    {
        return this._divFeesLiquidatnDaysTypeCT;
    } //-- java.lang.String getDivFeesLiquidatnDaysTypeCT() 

    /**
     * Method getDivisionFeesLiquidationDaysReturns the value of
     * field 'divisionFeesLiquidationDays'.
     * 
     * @return the value of field 'divisionFeesLiquidationDays'.
     */
    public int getDivisionFeesLiquidationDays()
    {
        return this._divisionFeesLiquidationDays;
    } //-- int getDivisionFeesLiquidationDays() 

    /**
     * Method getDivisionFeesLiquidationModeCTReturns the value of
     * field 'divisionFeesLiquidationModeCT'.
     * 
     * @return the value of field 'divisionFeesLiquidationModeCT'.
     */
    public java.lang.String getDivisionFeesLiquidationModeCT()
    {
        return this._divisionFeesLiquidationModeCT;
    } //-- java.lang.String getDivisionFeesLiquidationModeCT() 

    /**
     * Method getDivisionLevelLockUpDurationReturns the value of
     * field 'divisionLevelLockUpDuration'.
     * 
     * @return the value of field 'divisionLevelLockUpDuration'.
     */
    public int getDivisionLevelLockUpDuration()
    {
        return this._divisionLevelLockUpDuration;
    } //-- int getDivisionLevelLockUpDuration() 

    /**
     * Method getDivisionLockUpEndDateReturns the value of field
     * 'divisionLockUpEndDate'.
     * 
     * @return the value of field 'divisionLockUpEndDate'.
     */
    public java.lang.String getDivisionLockUpEndDate()
    {
        return this._divisionLockUpEndDate;
    } //-- java.lang.String getDivisionLockUpEndDate() 

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
     * Method getFeeDescriptionVO
     * 
     * @param index
     */
    public edit.common.vo.FeeDescriptionVO getFeeDescriptionVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeDescriptionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FeeDescriptionVO) _feeDescriptionVOList.elementAt(index);
    } //-- edit.common.vo.FeeDescriptionVO getFeeDescriptionVO(int) 

    /**
     * Method getFeeDescriptionVO
     */
    public edit.common.vo.FeeDescriptionVO[] getFeeDescriptionVO()
    {
        int size = _feeDescriptionVOList.size();
        edit.common.vo.FeeDescriptionVO[] mArray = new edit.common.vo.FeeDescriptionVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FeeDescriptionVO) _feeDescriptionVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FeeDescriptionVO[] getFeeDescriptionVO() 

    /**
     * Method getFeeDescriptionVOCount
     */
    public int getFeeDescriptionVOCount()
    {
        return _feeDescriptionVOList.size();
    } //-- int getFeeDescriptionVOCount() 

    /**
     * Method getFeeVO
     * 
     * @param index
     */
    public edit.common.vo.FeeVO getFeeVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FeeVO) _feeVOList.elementAt(index);
    } //-- edit.common.vo.FeeVO getFeeVO(int) 

    /**
     * Method getFeeVO
     */
    public edit.common.vo.FeeVO[] getFeeVO()
    {
        int size = _feeVOList.size();
        edit.common.vo.FeeVO[] mArray = new edit.common.vo.FeeVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FeeVO) _feeVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FeeVO[] getFeeVO() 

    /**
     * Method getFeeVOCount
     */
    public int getFeeVOCount()
    {
        return _feeVOList.size();
    } //-- int getFeeVOCount() 

    /**
     * Method getFilteredFundPKReturns the value of field
     * 'filteredFundPK'.
     * 
     * @return the value of field 'filteredFundPK'.
     */
    public long getFilteredFundPK()
    {
        return this._filteredFundPK;
    } //-- long getFilteredFundPK() 

    /**
     * Method getFullSurrenderDaysReturns the value of field
     * 'fullSurrenderDays'.
     * 
     * @return the value of field 'fullSurrenderDays'.
     */
    public int getFullSurrenderDays()
    {
        return this._fullSurrenderDays;
    } //-- int getFullSurrenderDays() 

    /**
     * Method getFullSurrenderDaysTypeCTReturns the value of field
     * 'fullSurrenderDaysTypeCT'.
     * 
     * @return the value of field 'fullSurrenderDaysTypeCT'.
     */
    public java.lang.String getFullSurrenderDaysTypeCT()
    {
        return this._fullSurrenderDaysTypeCT;
    } //-- java.lang.String getFullSurrenderDaysTypeCT() 

    /**
     * Method getFullSurrenderModeCTReturns the value of field
     * 'fullSurrenderModeCT'.
     * 
     * @return the value of field 'fullSurrenderModeCT'.
     */
    public java.lang.String getFullSurrenderModeCT()
    {
        return this._fullSurrenderModeCT;
    } //-- java.lang.String getFullSurrenderModeCT() 

    /**
     * Method getFundAdjustmentCTReturns the value of field
     * 'fundAdjustmentCT'.
     * 
     * @return the value of field 'fundAdjustmentCT'.
     */
    public java.lang.String getFundAdjustmentCT()
    {
        return this._fundAdjustmentCT;
    } //-- java.lang.String getFundAdjustmentCT() 

    /**
     * Method getFundFKReturns the value of field 'fundFK'.
     * 
     * @return the value of field 'fundFK'.
     */
    public long getFundFK()
    {
        return this._fundFK;
    } //-- long getFundFK() 

    /**
     * Method getFundNewClientCloseDateReturns the value of field
     * 'fundNewClientCloseDate'.
     * 
     * @return the value of field 'fundNewClientCloseDate'.
     */
    public java.lang.String getFundNewClientCloseDate()
    {
        return this._fundNewClientCloseDate;
    } //-- java.lang.String getFundNewClientCloseDate() 

    /**
     * Method getFundNewDepositCloseDateReturns the value of field
     * 'fundNewDepositCloseDate'.
     * 
     * @return the value of field 'fundNewDepositCloseDate'.
     */
    public java.lang.String getFundNewDepositCloseDate()
    {
        return this._fundNewDepositCloseDate;
    } //-- java.lang.String getFundNewDepositCloseDate() 

    /**
     * Method getFundNumberReturns the value of field 'fundNumber'.
     * 
     * @return the value of field 'fundNumber'.
     */
    public java.lang.String getFundNumber()
    {
        return this._fundNumber;
    } //-- java.lang.String getFundNumber() 

    /**
     * Method getGuaranteedDurationReturns the value of field
     * 'guaranteedDuration'.
     * 
     * @return the value of field 'guaranteedDuration'.
     */
    public int getGuaranteedDuration()
    {
        return this._guaranteedDuration;
    } //-- int getGuaranteedDuration() 

    /**
     * Method getHoldingAccountFKReturns the value of field
     * 'holdingAccountFK'.
     * 
     * @return the value of field 'holdingAccountFK'.
     */
    public long getHoldingAccountFK()
    {
        return this._holdingAccountFK;
    } //-- long getHoldingAccountFK() 

    /**
     * Method getIncludeInCorrespondenceIndReturns the value of
     * field 'includeInCorrespondenceInd'.
     * 
     * @return the value of field 'includeInCorrespondenceInd'.
     */
    public java.lang.String getIncludeInCorrespondenceInd()
    {
        return this._includeInCorrespondenceInd;
    } //-- java.lang.String getIncludeInCorrespondenceInd() 

    /**
     * Method getIndexCapRateGuarPeriodReturns the value of field
     * 'indexCapRateGuarPeriod'.
     * 
     * @return the value of field 'indexCapRateGuarPeriod'.
     */
    public int getIndexCapRateGuarPeriod()
    {
        return this._indexCapRateGuarPeriod;
    } //-- int getIndexCapRateGuarPeriod() 

    /**
     * Method getIndexingMethodCTReturns the value of field
     * 'indexingMethodCT'.
     * 
     * @return the value of field 'indexingMethodCT'.
     */
    public java.lang.String getIndexingMethodCT()
    {
        return this._indexingMethodCT;
    } //-- java.lang.String getIndexingMethodCT() 

    /**
     * Method getInterestRateParametersVO
     * 
     * @param index
     */
    public edit.common.vo.InterestRateParametersVO getInterestRateParametersVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _interestRateParametersVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InterestRateParametersVO) _interestRateParametersVOList.elementAt(index);
    } //-- edit.common.vo.InterestRateParametersVO getInterestRateParametersVO(int) 

    /**
     * Method getInterestRateParametersVO
     */
    public edit.common.vo.InterestRateParametersVO[] getInterestRateParametersVO()
    {
        int size = _interestRateParametersVOList.size();
        edit.common.vo.InterestRateParametersVO[] mArray = new edit.common.vo.InterestRateParametersVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InterestRateParametersVO) _interestRateParametersVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InterestRateParametersVO[] getInterestRateParametersVO() 

    /**
     * Method getInterestRateParametersVOCount
     */
    public int getInterestRateParametersVOCount()
    {
        return _interestRateParametersVOList.size();
    } //-- int getInterestRateParametersVOCount() 

    /**
     * Method getLoanDaysReturns the value of field 'loanDays'.
     * 
     * @return the value of field 'loanDays'.
     */
    public int getLoanDays()
    {
        return this._loanDays;
    } //-- int getLoanDays() 

    /**
     * Method getLoanDaysTypeCTReturns the value of field
     * 'loanDaysTypeCT'.
     * 
     * @return the value of field 'loanDaysTypeCT'.
     */
    public java.lang.String getLoanDaysTypeCT()
    {
        return this._loanDaysTypeCT;
    } //-- java.lang.String getLoanDaysTypeCT() 

    /**
     * Method getLoanModeCTReturns the value of field 'loanModeCT'.
     * 
     * @return the value of field 'loanModeCT'.
     */
    public java.lang.String getLoanModeCT()
    {
        return this._loanModeCT;
    } //-- java.lang.String getLoanModeCT() 

    /**
     * Method getMVAStartingIndexGuarPeriodReturns the value of
     * field 'MVAStartingIndexGuarPeriod'.
     * 
     * @return the value of field 'MVAStartingIndexGuarPeriod'.
     */
    public int getMVAStartingIndexGuarPeriod()
    {
        return this._MVAStartingIndexGuarPeriod;
    } //-- int getMVAStartingIndexGuarPeriod() 

    /**
     * Method getMinimumTransferAmountReturns the value of field
     * 'minimumTransferAmount'.
     * 
     * @return the value of field 'minimumTransferAmount'.
     */
    public java.math.BigDecimal getMinimumTransferAmount()
    {
        return this._minimumTransferAmount;
    } //-- java.math.BigDecimal getMinimumTransferAmount() 

    /**
     * Method getPostLockWithdrawalDateCTReturns the value of field
     * 'postLockWithdrawalDateCT'.
     * 
     * @return the value of field 'postLockWithdrawalDateCT'.
     */
    public java.lang.String getPostLockWithdrawalDateCT()
    {
        return this._postLockWithdrawalDateCT;
    } //-- java.lang.String getPostLockWithdrawalDateCT() 

    /**
     * Method getPremiumBonusDurationReturns the value of field
     * 'premiumBonusDuration'.
     * 
     * @return the value of field 'premiumBonusDuration'.
     */
    public int getPremiumBonusDuration()
    {
        return this._premiumBonusDuration;
    } //-- int getPremiumBonusDuration() 

    /**
     * Method getPricingDirectionReturns the value of field
     * 'pricingDirection'.
     * 
     * @return the value of field 'pricingDirection'.
     */
    public java.lang.String getPricingDirection()
    {
        return this._pricingDirection;
    } //-- java.lang.String getPricingDirection() 

    /**
     * Method getProductFilteredFundStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductFilteredFundStructureVO getProductFilteredFundStructureVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productFilteredFundStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProductFilteredFundStructureVO) _productFilteredFundStructureVOList.elementAt(index);
    } //-- edit.common.vo.ProductFilteredFundStructureVO getProductFilteredFundStructureVO(int) 

    /**
     * Method getProductFilteredFundStructureVO
     */
    public edit.common.vo.ProductFilteredFundStructureVO[] getProductFilteredFundStructureVO()
    {
        int size = _productFilteredFundStructureVOList.size();
        edit.common.vo.ProductFilteredFundStructureVO[] mArray = new edit.common.vo.ProductFilteredFundStructureVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProductFilteredFundStructureVO) _productFilteredFundStructureVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProductFilteredFundStructureVO[] getProductFilteredFundStructureVO() 

    /**
     * Method getProductFilteredFundStructureVOCount
     */
    public int getProductFilteredFundStructureVOCount()
    {
        return _productFilteredFundStructureVOList.size();
    } //-- int getProductFilteredFundStructureVOCount() 

    /**
     * Method getSeparateAccountNameReturns the value of field
     * 'separateAccountName'.
     * 
     * @return the value of field 'separateAccountName'.
     */
    public java.lang.String getSeparateAccountName()
    {
        return this._separateAccountName;
    } //-- java.lang.String getSeparateAccountName() 

    /**
     * Method getSeriesToSeriesEligibilityIndReturns the value of
     * field 'seriesToSeriesEligibilityInd'.
     * 
     * @return the value of field 'seriesToSeriesEligibilityInd'.
     */
    public java.lang.String getSeriesToSeriesEligibilityInd()
    {
        return this._seriesToSeriesEligibilityInd;
    } //-- java.lang.String getSeriesToSeriesEligibilityInd() 

    /**
     * Method getSeriesTransferDaysReturns the value of field
     * 'seriesTransferDays'.
     * 
     * @return the value of field 'seriesTransferDays'.
     */
    public int getSeriesTransferDays()
    {
        return this._seriesTransferDays;
    } //-- int getSeriesTransferDays() 

    /**
     * Method getSeriesTransferDaysTypeCTReturns the value of field
     * 'seriesTransferDaysTypeCT'.
     * 
     * @return the value of field 'seriesTransferDaysTypeCT'.
     */
    public java.lang.String getSeriesTransferDaysTypeCT()
    {
        return this._seriesTransferDaysTypeCT;
    } //-- java.lang.String getSeriesTransferDaysTypeCT() 

    /**
     * Method getSeriesTransferModeCTReturns the value of field
     * 'seriesTransferModeCT'.
     * 
     * @return the value of field 'seriesTransferModeCT'.
     */
    public java.lang.String getSeriesTransferModeCT()
    {
        return this._seriesTransferModeCT;
    } //-- java.lang.String getSeriesTransferModeCT() 

    /**
     * Method getSubscriptionDaysTypeCTReturns the value of field
     * 'subscriptionDaysTypeCT'.
     * 
     * @return the value of field 'subscriptionDaysTypeCT'.
     */
    public java.lang.String getSubscriptionDaysTypeCT()
    {
        return this._subscriptionDaysTypeCT;
    } //-- java.lang.String getSubscriptionDaysTypeCT() 

    /**
     * Method getSubscriptionModeCTReturns the value of field
     * 'subscriptionModeCT'.
     * 
     * @return the value of field 'subscriptionModeCT'.
     */
    public java.lang.String getSubscriptionModeCT()
    {
        return this._subscriptionModeCT;
    } //-- java.lang.String getSubscriptionModeCT() 

    /**
     * Method getSubscriptionNotificationDaysReturns the value of
     * field 'subscriptionNotificationDays'.
     * 
     * @return the value of field 'subscriptionNotificationDays'.
     */
    public int getSubscriptionNotificationDays()
    {
        return this._subscriptionNotificationDays;
    } //-- int getSubscriptionNotificationDays() 

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
     * Method getTransferDaysReturns the value of field
     * 'transferDays'.
     * 
     * @return the value of field 'transferDays'.
     */
    public int getTransferDays()
    {
        return this._transferDays;
    } //-- int getTransferDays() 

    /**
     * Method getTransferDaysTypeCTReturns the value of field
     * 'transferDaysTypeCT'.
     * 
     * @return the value of field 'transferDaysTypeCT'.
     */
    public java.lang.String getTransferDaysTypeCT()
    {
        return this._transferDaysTypeCT;
    } //-- java.lang.String getTransferDaysTypeCT() 

    /**
     * Method getTransferModeCTReturns the value of field
     * 'transferModeCT'.
     * 
     * @return the value of field 'transferModeCT'.
     */
    public java.lang.String getTransferModeCT()
    {
        return this._transferModeCT;
    } //-- java.lang.String getTransferModeCT() 

    /**
     * Method getUnitValuesVO
     * 
     * @param index
     */
    public edit.common.vo.UnitValuesVO getUnitValuesVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _unitValuesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.UnitValuesVO) _unitValuesVOList.elementAt(index);
    } //-- edit.common.vo.UnitValuesVO getUnitValuesVO(int) 

    /**
     * Method getUnitValuesVO
     */
    public edit.common.vo.UnitValuesVO[] getUnitValuesVO()
    {
        int size = _unitValuesVOList.size();
        edit.common.vo.UnitValuesVO[] mArray = new edit.common.vo.UnitValuesVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.UnitValuesVO) _unitValuesVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.UnitValuesVO[] getUnitValuesVO() 

    /**
     * Method getUnitValuesVOCount
     */
    public int getUnitValuesVOCount()
    {
        return _unitValuesVOList.size();
    } //-- int getUnitValuesVOCount() 

    /**
     * Method getWithdrawalDaysReturns the value of field
     * 'withdrawalDays'.
     * 
     * @return the value of field 'withdrawalDays'.
     */
    public int getWithdrawalDays()
    {
        return this._withdrawalDays;
    } //-- int getWithdrawalDays() 

    /**
     * Method getWithdrawalDaysTypeCTReturns the value of field
     * 'withdrawalDaysTypeCT'.
     * 
     * @return the value of field 'withdrawalDaysTypeCT'.
     */
    public java.lang.String getWithdrawalDaysTypeCT()
    {
        return this._withdrawalDaysTypeCT;
    } //-- java.lang.String getWithdrawalDaysTypeCT() 

    /**
     * Method getWithdrawalModeCTReturns the value of field
     * 'withdrawalModeCT'.
     * 
     * @return the value of field 'withdrawalModeCT'.
     */
    public java.lang.String getWithdrawalModeCT()
    {
        return this._withdrawalModeCT;
    } //-- java.lang.String getWithdrawalModeCT() 

    /**
     * Method hasCOIReplenishmentDays
     */
    public boolean hasCOIReplenishmentDays()
    {
        return this._has_COIReplenishmentDays;
    } //-- boolean hasCOIReplenishmentDays() 

    /**
     * Method hasContributionLockUpDuration
     */
    public boolean hasContributionLockUpDuration()
    {
        return this._has_contributionLockUpDuration;
    } //-- boolean hasContributionLockUpDuration() 

    /**
     * Method hasDeathDays
     */
    public boolean hasDeathDays()
    {
        return this._has_deathDays;
    } //-- boolean hasDeathDays() 

    /**
     * Method hasDivisionFeesLiquidationDays
     */
    public boolean hasDivisionFeesLiquidationDays()
    {
        return this._has_divisionFeesLiquidationDays;
    } //-- boolean hasDivisionFeesLiquidationDays() 

    /**
     * Method hasDivisionLevelLockUpDuration
     */
    public boolean hasDivisionLevelLockUpDuration()
    {
        return this._has_divisionLevelLockUpDuration;
    } //-- boolean hasDivisionLevelLockUpDuration() 

    /**
     * Method hasFilteredFundPK
     */
    public boolean hasFilteredFundPK()
    {
        return this._has_filteredFundPK;
    } //-- boolean hasFilteredFundPK() 

    /**
     * Method hasFullSurrenderDays
     */
    public boolean hasFullSurrenderDays()
    {
        return this._has_fullSurrenderDays;
    } //-- boolean hasFullSurrenderDays() 

    /**
     * Method hasFundFK
     */
    public boolean hasFundFK()
    {
        return this._has_fundFK;
    } //-- boolean hasFundFK() 

    /**
     * Method hasGuaranteedDuration
     */
    public boolean hasGuaranteedDuration()
    {
        return this._has_guaranteedDuration;
    } //-- boolean hasGuaranteedDuration() 

    /**
     * Method hasHoldingAccountFK
     */
    public boolean hasHoldingAccountFK()
    {
        return this._has_holdingAccountFK;
    } //-- boolean hasHoldingAccountFK() 

    /**
     * Method hasIndexCapRateGuarPeriod
     */
    public boolean hasIndexCapRateGuarPeriod()
    {
        return this._has_indexCapRateGuarPeriod;
    } //-- boolean hasIndexCapRateGuarPeriod() 

    /**
     * Method hasLoanDays
     */
    public boolean hasLoanDays()
    {
        return this._has_loanDays;
    } //-- boolean hasLoanDays() 

    /**
     * Method hasMVAStartingIndexGuarPeriod
     */
    public boolean hasMVAStartingIndexGuarPeriod()
    {
        return this._has_MVAStartingIndexGuarPeriod;
    } //-- boolean hasMVAStartingIndexGuarPeriod() 

    /**
     * Method hasPremiumBonusDuration
     */
    public boolean hasPremiumBonusDuration()
    {
        return this._has_premiumBonusDuration;
    } //-- boolean hasPremiumBonusDuration() 

    /**
     * Method hasSeriesTransferDays
     */
    public boolean hasSeriesTransferDays()
    {
        return this._has_seriesTransferDays;
    } //-- boolean hasSeriesTransferDays() 

    /**
     * Method hasSubscriptionNotificationDays
     */
    public boolean hasSubscriptionNotificationDays()
    {
        return this._has_subscriptionNotificationDays;
    } //-- boolean hasSubscriptionNotificationDays() 

    /**
     * Method hasTransferDays
     */
    public boolean hasTransferDays()
    {
        return this._has_transferDays;
    } //-- boolean hasTransferDays() 

    /**
     * Method hasWithdrawalDays
     */
    public boolean hasWithdrawalDays()
    {
        return this._has_withdrawalDays;
    } //-- boolean hasWithdrawalDays() 

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
     * Method removeAllChargeCodeVO
     */
    public void removeAllChargeCodeVO()
    {
        _chargeCodeVOList.removeAllElements();
    } //-- void removeAllChargeCodeVO() 

    /**
     * Method removeAllFeeDescriptionVO
     */
    public void removeAllFeeDescriptionVO()
    {
        _feeDescriptionVOList.removeAllElements();
    } //-- void removeAllFeeDescriptionVO() 

    /**
     * Method removeAllFeeVO
     */
    public void removeAllFeeVO()
    {
        _feeVOList.removeAllElements();
    } //-- void removeAllFeeVO() 

    /**
     * Method removeAllInterestRateParametersVO
     */
    public void removeAllInterestRateParametersVO()
    {
        _interestRateParametersVOList.removeAllElements();
    } //-- void removeAllInterestRateParametersVO() 

    /**
     * Method removeAllProductFilteredFundStructureVO
     */
    public void removeAllProductFilteredFundStructureVO()
    {
        _productFilteredFundStructureVOList.removeAllElements();
    } //-- void removeAllProductFilteredFundStructureVO() 

    /**
     * Method removeAllUnitValuesVO
     */
    public void removeAllUnitValuesVO()
    {
        _unitValuesVOList.removeAllElements();
    } //-- void removeAllUnitValuesVO() 

    /**
     * Method removeChargeCodeVO
     * 
     * @param index
     */
    public edit.common.vo.ChargeCodeVO removeChargeCodeVO(int index)
    {
        java.lang.Object obj = _chargeCodeVOList.elementAt(index);
        _chargeCodeVOList.removeElementAt(index);
        return (edit.common.vo.ChargeCodeVO) obj;
    } //-- edit.common.vo.ChargeCodeVO removeChargeCodeVO(int) 

    /**
     * Method removeFeeDescriptionVO
     * 
     * @param index
     */
    public edit.common.vo.FeeDescriptionVO removeFeeDescriptionVO(int index)
    {
        java.lang.Object obj = _feeDescriptionVOList.elementAt(index);
        _feeDescriptionVOList.removeElementAt(index);
        return (edit.common.vo.FeeDescriptionVO) obj;
    } //-- edit.common.vo.FeeDescriptionVO removeFeeDescriptionVO(int) 

    /**
     * Method removeFeeVO
     * 
     * @param index
     */
    public edit.common.vo.FeeVO removeFeeVO(int index)
    {
        java.lang.Object obj = _feeVOList.elementAt(index);
        _feeVOList.removeElementAt(index);
        return (edit.common.vo.FeeVO) obj;
    } //-- edit.common.vo.FeeVO removeFeeVO(int) 

    /**
     * Method removeInterestRateParametersVO
     * 
     * @param index
     */
    public edit.common.vo.InterestRateParametersVO removeInterestRateParametersVO(int index)
    {
        java.lang.Object obj = _interestRateParametersVOList.elementAt(index);
        _interestRateParametersVOList.removeElementAt(index);
        return (edit.common.vo.InterestRateParametersVO) obj;
    } //-- edit.common.vo.InterestRateParametersVO removeInterestRateParametersVO(int) 

    /**
     * Method removeProductFilteredFundStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductFilteredFundStructureVO removeProductFilteredFundStructureVO(int index)
    {
        java.lang.Object obj = _productFilteredFundStructureVOList.elementAt(index);
        _productFilteredFundStructureVOList.removeElementAt(index);
        return (edit.common.vo.ProductFilteredFundStructureVO) obj;
    } //-- edit.common.vo.ProductFilteredFundStructureVO removeProductFilteredFundStructureVO(int) 

    /**
     * Method removeUnitValuesVO
     * 
     * @param index
     */
    public edit.common.vo.UnitValuesVO removeUnitValuesVO(int index)
    {
        java.lang.Object obj = _unitValuesVOList.elementAt(index);
        _unitValuesVOList.removeElementAt(index);
        return (edit.common.vo.UnitValuesVO) obj;
    } //-- edit.common.vo.UnitValuesVO removeUnitValuesVO(int) 

    /**
     * Method setAnnualSubBucketCTSets the value of field
     * 'annualSubBucketCT'.
     * 
     * @param annualSubBucketCT the value of field
     * 'annualSubBucketCT'.
     */
    public void setAnnualSubBucketCT(java.lang.String annualSubBucketCT)
    {
        this._annualSubBucketCT = annualSubBucketCT;
        
        super.setVoChanged(true);
    } //-- void setAnnualSubBucketCT(java.lang.String) 

    /**
     * Method setCOIReplenishmentDaysSets the value of field
     * 'COIReplenishmentDays'.
     * 
     * @param COIReplenishmentDays the value of field
     * 'COIReplenishmentDays'.
     */
    public void setCOIReplenishmentDays(int COIReplenishmentDays)
    {
        this._COIReplenishmentDays = COIReplenishmentDays;
        
        super.setVoChanged(true);
        this._has_COIReplenishmentDays = true;
    } //-- void setCOIReplenishmentDays(int) 

    /**
     * Method setCOIReplenishmentDaysTypeCTSets the value of field
     * 'COIReplenishmentDaysTypeCT'.
     * 
     * @param COIReplenishmentDaysTypeCT the value of field
     * 'COIReplenishmentDaysTypeCT'.
     */
    public void setCOIReplenishmentDaysTypeCT(java.lang.String COIReplenishmentDaysTypeCT)
    {
        this._COIReplenishmentDaysTypeCT = COIReplenishmentDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCOIReplenishmentDaysTypeCT(java.lang.String) 

    /**
     * Method setCOIReplenishmentModeCTSets the value of field
     * 'COIReplenishmentModeCT'.
     * 
     * @param COIReplenishmentModeCT the value of field
     * 'COIReplenishmentModeCT'.
     */
    public void setCOIReplenishmentModeCT(java.lang.String COIReplenishmentModeCT)
    {
        this._COIReplenishmentModeCT = COIReplenishmentModeCT;
        
        super.setVoChanged(true);
    } //-- void setCOIReplenishmentModeCT(java.lang.String) 

    /**
     * Method setCategoryCTSets the value of field 'categoryCT'.
     * 
     * @param categoryCT the value of field 'categoryCT'.
     */
    public void setCategoryCT(java.lang.String categoryCT)
    {
        this._categoryCT = categoryCT;
        
        super.setVoChanged(true);
    } //-- void setCategoryCT(java.lang.String) 

    /**
     * Method setChargeCodeIndicatorSets the value of field
     * 'chargeCodeIndicator'.
     * 
     * @param chargeCodeIndicator the value of field
     * 'chargeCodeIndicator'.
     */
    public void setChargeCodeIndicator(java.lang.String chargeCodeIndicator)
    {
        this._chargeCodeIndicator = chargeCodeIndicator;
        
        super.setVoChanged(true);
    } //-- void setChargeCodeIndicator(java.lang.String) 

    /**
     * Method setChargeCodeVO
     * 
     * @param index
     * @param vChargeCodeVO
     */
    public void setChargeCodeVO(int index, edit.common.vo.ChargeCodeVO vChargeCodeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _chargeCodeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vChargeCodeVO.setParentVO(this.getClass(), this);
        _chargeCodeVOList.setElementAt(vChargeCodeVO, index);
    } //-- void setChargeCodeVO(int, edit.common.vo.ChargeCodeVO) 

    /**
     * Method setChargeCodeVO
     * 
     * @param chargeCodeVOArray
     */
    public void setChargeCodeVO(edit.common.vo.ChargeCodeVO[] chargeCodeVOArray)
    {
        //-- copy array
        _chargeCodeVOList.removeAllElements();
        for (int i = 0; i < chargeCodeVOArray.length; i++) {
            chargeCodeVOArray[i].setParentVO(this.getClass(), this);
            _chargeCodeVOList.addElement(chargeCodeVOArray[i]);
        }
    } //-- void setChargeCodeVO(edit.common.vo.ChargeCodeVO) 

    /**
     * Method setContributionLockUpDurationSets the value of field
     * 'contributionLockUpDuration'.
     * 
     * @param contributionLockUpDuration the value of field
     * 'contributionLockUpDuration'.
     */
    public void setContributionLockUpDuration(int contributionLockUpDuration)
    {
        this._contributionLockUpDuration = contributionLockUpDuration;
        
        super.setVoChanged(true);
        this._has_contributionLockUpDuration = true;
    } //-- void setContributionLockUpDuration(int) 

    /**
     * Method setDeathDaysSets the value of field 'deathDays'.
     * 
     * @param deathDays the value of field 'deathDays'.
     */
    public void setDeathDays(int deathDays)
    {
        this._deathDays = deathDays;
        
        super.setVoChanged(true);
        this._has_deathDays = true;
    } //-- void setDeathDays(int) 

    /**
     * Method setDeathDaysTypeCTSets the value of field
     * 'deathDaysTypeCT'.
     * 
     * @param deathDaysTypeCT the value of field 'deathDaysTypeCT'.
     */
    public void setDeathDaysTypeCT(java.lang.String deathDaysTypeCT)
    {
        this._deathDaysTypeCT = deathDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setDeathDaysTypeCT(java.lang.String) 

    /**
     * Method setDeathModeCTSets the value of field 'deathModeCT'.
     * 
     * @param deathModeCT the value of field 'deathModeCT'.
     */
    public void setDeathModeCT(java.lang.String deathModeCT)
    {
        this._deathModeCT = deathModeCT;
        
        super.setVoChanged(true);
    } //-- void setDeathModeCT(java.lang.String) 

    /**
     * Method setDivFeesLiquidatnDaysTypeCTSets the value of field
     * 'divFeesLiquidatnDaysTypeCT'.
     * 
     * @param divFeesLiquidatnDaysTypeCT the value of field
     * 'divFeesLiquidatnDaysTypeCT'.
     */
    public void setDivFeesLiquidatnDaysTypeCT(java.lang.String divFeesLiquidatnDaysTypeCT)
    {
        this._divFeesLiquidatnDaysTypeCT = divFeesLiquidatnDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setDivFeesLiquidatnDaysTypeCT(java.lang.String) 

    /**
     * Method setDivisionFeesLiquidationDaysSets the value of field
     * 'divisionFeesLiquidationDays'.
     * 
     * @param divisionFeesLiquidationDays the value of field
     * 'divisionFeesLiquidationDays'.
     */
    public void setDivisionFeesLiquidationDays(int divisionFeesLiquidationDays)
    {
        this._divisionFeesLiquidationDays = divisionFeesLiquidationDays;
        
        super.setVoChanged(true);
        this._has_divisionFeesLiquidationDays = true;
    } //-- void setDivisionFeesLiquidationDays(int) 

    /**
     * Method setDivisionFeesLiquidationModeCTSets the value of
     * field 'divisionFeesLiquidationModeCT'.
     * 
     * @param divisionFeesLiquidationModeCT the value of field
     * 'divisionFeesLiquidationModeCT'.
     */
    public void setDivisionFeesLiquidationModeCT(java.lang.String divisionFeesLiquidationModeCT)
    {
        this._divisionFeesLiquidationModeCT = divisionFeesLiquidationModeCT;
        
        super.setVoChanged(true);
    } //-- void setDivisionFeesLiquidationModeCT(java.lang.String) 

    /**
     * Method setDivisionLevelLockUpDurationSets the value of field
     * 'divisionLevelLockUpDuration'.
     * 
     * @param divisionLevelLockUpDuration the value of field
     * 'divisionLevelLockUpDuration'.
     */
    public void setDivisionLevelLockUpDuration(int divisionLevelLockUpDuration)
    {
        this._divisionLevelLockUpDuration = divisionLevelLockUpDuration;
        
        super.setVoChanged(true);
        this._has_divisionLevelLockUpDuration = true;
    } //-- void setDivisionLevelLockUpDuration(int) 

    /**
     * Method setDivisionLockUpEndDateSets the value of field
     * 'divisionLockUpEndDate'.
     * 
     * @param divisionLockUpEndDate the value of field
     * 'divisionLockUpEndDate'.
     */
    public void setDivisionLockUpEndDate(java.lang.String divisionLockUpEndDate)
    {
        this._divisionLockUpEndDate = divisionLockUpEndDate;
        
        super.setVoChanged(true);
    } //-- void setDivisionLockUpEndDate(java.lang.String) 

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
     * Method setFeeDescriptionVO
     * 
     * @param index
     * @param vFeeDescriptionVO
     */
    public void setFeeDescriptionVO(int index, edit.common.vo.FeeDescriptionVO vFeeDescriptionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeDescriptionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFeeDescriptionVO.setParentVO(this.getClass(), this);
        _feeDescriptionVOList.setElementAt(vFeeDescriptionVO, index);
    } //-- void setFeeDescriptionVO(int, edit.common.vo.FeeDescriptionVO) 

    /**
     * Method setFeeDescriptionVO
     * 
     * @param feeDescriptionVOArray
     */
    public void setFeeDescriptionVO(edit.common.vo.FeeDescriptionVO[] feeDescriptionVOArray)
    {
        //-- copy array
        _feeDescriptionVOList.removeAllElements();
        for (int i = 0; i < feeDescriptionVOArray.length; i++) {
            feeDescriptionVOArray[i].setParentVO(this.getClass(), this);
            _feeDescriptionVOList.addElement(feeDescriptionVOArray[i]);
        }
    } //-- void setFeeDescriptionVO(edit.common.vo.FeeDescriptionVO) 

    /**
     * Method setFeeVO
     * 
     * @param index
     * @param vFeeVO
     */
    public void setFeeVO(int index, edit.common.vo.FeeVO vFeeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFeeVO.setParentVO(this.getClass(), this);
        _feeVOList.setElementAt(vFeeVO, index);
    } //-- void setFeeVO(int, edit.common.vo.FeeVO) 

    /**
     * Method setFeeVO
     * 
     * @param feeVOArray
     */
    public void setFeeVO(edit.common.vo.FeeVO[] feeVOArray)
    {
        //-- copy array
        _feeVOList.removeAllElements();
        for (int i = 0; i < feeVOArray.length; i++) {
            feeVOArray[i].setParentVO(this.getClass(), this);
            _feeVOList.addElement(feeVOArray[i]);
        }
    } //-- void setFeeVO(edit.common.vo.FeeVO) 

    /**
     * Method setFilteredFundPKSets the value of field
     * 'filteredFundPK'.
     * 
     * @param filteredFundPK the value of field 'filteredFundPK'.
     */
    public void setFilteredFundPK(long filteredFundPK)
    {
        this._filteredFundPK = filteredFundPK;
        
        super.setVoChanged(true);
        this._has_filteredFundPK = true;
    } //-- void setFilteredFundPK(long) 

    /**
     * Method setFullSurrenderDaysSets the value of field
     * 'fullSurrenderDays'.
     * 
     * @param fullSurrenderDays the value of field
     * 'fullSurrenderDays'.
     */
    public void setFullSurrenderDays(int fullSurrenderDays)
    {
        this._fullSurrenderDays = fullSurrenderDays;
        
        super.setVoChanged(true);
        this._has_fullSurrenderDays = true;
    } //-- void setFullSurrenderDays(int) 

    /**
     * Method setFullSurrenderDaysTypeCTSets the value of field
     * 'fullSurrenderDaysTypeCT'.
     * 
     * @param fullSurrenderDaysTypeCT the value of field
     * 'fullSurrenderDaysTypeCT'.
     */
    public void setFullSurrenderDaysTypeCT(java.lang.String fullSurrenderDaysTypeCT)
    {
        this._fullSurrenderDaysTypeCT = fullSurrenderDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setFullSurrenderDaysTypeCT(java.lang.String) 

    /**
     * Method setFullSurrenderModeCTSets the value of field
     * 'fullSurrenderModeCT'.
     * 
     * @param fullSurrenderModeCT the value of field
     * 'fullSurrenderModeCT'.
     */
    public void setFullSurrenderModeCT(java.lang.String fullSurrenderModeCT)
    {
        this._fullSurrenderModeCT = fullSurrenderModeCT;
        
        super.setVoChanged(true);
    } //-- void setFullSurrenderModeCT(java.lang.String) 

    /**
     * Method setFundAdjustmentCTSets the value of field
     * 'fundAdjustmentCT'.
     * 
     * @param fundAdjustmentCT the value of field 'fundAdjustmentCT'
     */
    public void setFundAdjustmentCT(java.lang.String fundAdjustmentCT)
    {
        this._fundAdjustmentCT = fundAdjustmentCT;
        
        super.setVoChanged(true);
    } //-- void setFundAdjustmentCT(java.lang.String) 

    /**
     * Method setFundFKSets the value of field 'fundFK'.
     * 
     * @param fundFK the value of field 'fundFK'.
     */
    public void setFundFK(long fundFK)
    {
        this._fundFK = fundFK;
        
        super.setVoChanged(true);
        this._has_fundFK = true;
    } //-- void setFundFK(long) 

    /**
     * Method setFundNewClientCloseDateSets the value of field
     * 'fundNewClientCloseDate'.
     * 
     * @param fundNewClientCloseDate the value of field
     * 'fundNewClientCloseDate'.
     */
    public void setFundNewClientCloseDate(java.lang.String fundNewClientCloseDate)
    {
        this._fundNewClientCloseDate = fundNewClientCloseDate;
        
        super.setVoChanged(true);
    } //-- void setFundNewClientCloseDate(java.lang.String) 

    /**
     * Method setFundNewDepositCloseDateSets the value of field
     * 'fundNewDepositCloseDate'.
     * 
     * @param fundNewDepositCloseDate the value of field
     * 'fundNewDepositCloseDate'.
     */
    public void setFundNewDepositCloseDate(java.lang.String fundNewDepositCloseDate)
    {
        this._fundNewDepositCloseDate = fundNewDepositCloseDate;
        
        super.setVoChanged(true);
    } //-- void setFundNewDepositCloseDate(java.lang.String) 

    /**
     * Method setFundNumberSets the value of field 'fundNumber'.
     * 
     * @param fundNumber the value of field 'fundNumber'.
     */
    public void setFundNumber(java.lang.String fundNumber)
    {
        this._fundNumber = fundNumber;
        
        super.setVoChanged(true);
    } //-- void setFundNumber(java.lang.String) 

    /**
     * Method setGuaranteedDurationSets the value of field
     * 'guaranteedDuration'.
     * 
     * @param guaranteedDuration the value of field
     * 'guaranteedDuration'.
     */
    public void setGuaranteedDuration(int guaranteedDuration)
    {
        this._guaranteedDuration = guaranteedDuration;
        
        super.setVoChanged(true);
        this._has_guaranteedDuration = true;
    } //-- void setGuaranteedDuration(int) 

    /**
     * Method setHoldingAccountFKSets the value of field
     * 'holdingAccountFK'.
     * 
     * @param holdingAccountFK the value of field 'holdingAccountFK'
     */
    public void setHoldingAccountFK(long holdingAccountFK)
    {
        this._holdingAccountFK = holdingAccountFK;
        
        super.setVoChanged(true);
        this._has_holdingAccountFK = true;
    } //-- void setHoldingAccountFK(long) 

    /**
     * Method setIncludeInCorrespondenceIndSets the value of field
     * 'includeInCorrespondenceInd'.
     * 
     * @param includeInCorrespondenceInd the value of field
     * 'includeInCorrespondenceInd'.
     */
    public void setIncludeInCorrespondenceInd(java.lang.String includeInCorrespondenceInd)
    {
        this._includeInCorrespondenceInd = includeInCorrespondenceInd;
        
        super.setVoChanged(true);
    } //-- void setIncludeInCorrespondenceInd(java.lang.String) 

    /**
     * Method setIndexCapRateGuarPeriodSets the value of field
     * 'indexCapRateGuarPeriod'.
     * 
     * @param indexCapRateGuarPeriod the value of field
     * 'indexCapRateGuarPeriod'.
     */
    public void setIndexCapRateGuarPeriod(int indexCapRateGuarPeriod)
    {
        this._indexCapRateGuarPeriod = indexCapRateGuarPeriod;
        
        super.setVoChanged(true);
        this._has_indexCapRateGuarPeriod = true;
    } //-- void setIndexCapRateGuarPeriod(int) 

    /**
     * Method setIndexingMethodCTSets the value of field
     * 'indexingMethodCT'.
     * 
     * @param indexingMethodCT the value of field 'indexingMethodCT'
     */
    public void setIndexingMethodCT(java.lang.String indexingMethodCT)
    {
        this._indexingMethodCT = indexingMethodCT;
        
        super.setVoChanged(true);
    } //-- void setIndexingMethodCT(java.lang.String) 

    /**
     * Method setInterestRateParametersVO
     * 
     * @param index
     * @param vInterestRateParametersVO
     */
    public void setInterestRateParametersVO(int index, edit.common.vo.InterestRateParametersVO vInterestRateParametersVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _interestRateParametersVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInterestRateParametersVO.setParentVO(this.getClass(), this);
        _interestRateParametersVOList.setElementAt(vInterestRateParametersVO, index);
    } //-- void setInterestRateParametersVO(int, edit.common.vo.InterestRateParametersVO) 

    /**
     * Method setInterestRateParametersVO
     * 
     * @param interestRateParametersVOArray
     */
    public void setInterestRateParametersVO(edit.common.vo.InterestRateParametersVO[] interestRateParametersVOArray)
    {
        //-- copy array
        _interestRateParametersVOList.removeAllElements();
        for (int i = 0; i < interestRateParametersVOArray.length; i++) {
            interestRateParametersVOArray[i].setParentVO(this.getClass(), this);
            _interestRateParametersVOList.addElement(interestRateParametersVOArray[i]);
        }
    } //-- void setInterestRateParametersVO(edit.common.vo.InterestRateParametersVO) 

    /**
     * Method setLoanDaysSets the value of field 'loanDays'.
     * 
     * @param loanDays the value of field 'loanDays'.
     */
    public void setLoanDays(int loanDays)
    {
        this._loanDays = loanDays;
        
        super.setVoChanged(true);
        this._has_loanDays = true;
    } //-- void setLoanDays(int) 

    /**
     * Method setLoanDaysTypeCTSets the value of field
     * 'loanDaysTypeCT'.
     * 
     * @param loanDaysTypeCT the value of field 'loanDaysTypeCT'.
     */
    public void setLoanDaysTypeCT(java.lang.String loanDaysTypeCT)
    {
        this._loanDaysTypeCT = loanDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setLoanDaysTypeCT(java.lang.String) 

    /**
     * Method setLoanModeCTSets the value of field 'loanModeCT'.
     * 
     * @param loanModeCT the value of field 'loanModeCT'.
     */
    public void setLoanModeCT(java.lang.String loanModeCT)
    {
        this._loanModeCT = loanModeCT;
        
        super.setVoChanged(true);
    } //-- void setLoanModeCT(java.lang.String) 

    /**
     * Method setMVAStartingIndexGuarPeriodSets the value of field
     * 'MVAStartingIndexGuarPeriod'.
     * 
     * @param MVAStartingIndexGuarPeriod the value of field
     * 'MVAStartingIndexGuarPeriod'.
     */
    public void setMVAStartingIndexGuarPeriod(int MVAStartingIndexGuarPeriod)
    {
        this._MVAStartingIndexGuarPeriod = MVAStartingIndexGuarPeriod;
        
        super.setVoChanged(true);
        this._has_MVAStartingIndexGuarPeriod = true;
    } //-- void setMVAStartingIndexGuarPeriod(int) 

    /**
     * Method setMinimumTransferAmountSets the value of field
     * 'minimumTransferAmount'.
     * 
     * @param minimumTransferAmount the value of field
     * 'minimumTransferAmount'.
     */
    public void setMinimumTransferAmount(java.math.BigDecimal minimumTransferAmount)
    {
        this._minimumTransferAmount = minimumTransferAmount;
        
        super.setVoChanged(true);
    } //-- void setMinimumTransferAmount(java.math.BigDecimal) 

    /**
     * Method setPostLockWithdrawalDateCTSets the value of field
     * 'postLockWithdrawalDateCT'.
     * 
     * @param postLockWithdrawalDateCT the value of field
     * 'postLockWithdrawalDateCT'.
     */
    public void setPostLockWithdrawalDateCT(java.lang.String postLockWithdrawalDateCT)
    {
        this._postLockWithdrawalDateCT = postLockWithdrawalDateCT;
        
        super.setVoChanged(true);
    } //-- void setPostLockWithdrawalDateCT(java.lang.String) 

    /**
     * Method setPremiumBonusDurationSets the value of field
     * 'premiumBonusDuration'.
     * 
     * @param premiumBonusDuration the value of field
     * 'premiumBonusDuration'.
     */
    public void setPremiumBonusDuration(int premiumBonusDuration)
    {
        this._premiumBonusDuration = premiumBonusDuration;
        
        super.setVoChanged(true);
        this._has_premiumBonusDuration = true;
    } //-- void setPremiumBonusDuration(int) 

    /**
     * Method setPricingDirectionSets the value of field
     * 'pricingDirection'.
     * 
     * @param pricingDirection the value of field 'pricingDirection'
     */
    public void setPricingDirection(java.lang.String pricingDirection)
    {
        this._pricingDirection = pricingDirection;
        
        super.setVoChanged(true);
    } //-- void setPricingDirection(java.lang.String) 

    /**
     * Method setProductFilteredFundStructureVO
     * 
     * @param index
     * @param vProductFilteredFundStructureVO
     */
    public void setProductFilteredFundStructureVO(int index, edit.common.vo.ProductFilteredFundStructureVO vProductFilteredFundStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productFilteredFundStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProductFilteredFundStructureVO.setParentVO(this.getClass(), this);
        _productFilteredFundStructureVOList.setElementAt(vProductFilteredFundStructureVO, index);
    } //-- void setProductFilteredFundStructureVO(int, edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method setProductFilteredFundStructureVO
     * 
     * @param productFilteredFundStructureVOArray
     */
    public void setProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO[] productFilteredFundStructureVOArray)
    {
        //-- copy array
        _productFilteredFundStructureVOList.removeAllElements();
        for (int i = 0; i < productFilteredFundStructureVOArray.length; i++) {
            productFilteredFundStructureVOArray[i].setParentVO(this.getClass(), this);
            _productFilteredFundStructureVOList.addElement(productFilteredFundStructureVOArray[i]);
        }
    } //-- void setProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method setSeparateAccountNameSets the value of field
     * 'separateAccountName'.
     * 
     * @param separateAccountName the value of field
     * 'separateAccountName'.
     */
    public void setSeparateAccountName(java.lang.String separateAccountName)
    {
        this._separateAccountName = separateAccountName;
        
        super.setVoChanged(true);
    } //-- void setSeparateAccountName(java.lang.String) 

    /**
     * Method setSeriesToSeriesEligibilityIndSets the value of
     * field 'seriesToSeriesEligibilityInd'.
     * 
     * @param seriesToSeriesEligibilityInd the value of field
     * 'seriesToSeriesEligibilityInd'.
     */
    public void setSeriesToSeriesEligibilityInd(java.lang.String seriesToSeriesEligibilityInd)
    {
        this._seriesToSeriesEligibilityInd = seriesToSeriesEligibilityInd;
        
        super.setVoChanged(true);
    } //-- void setSeriesToSeriesEligibilityInd(java.lang.String) 

    /**
     * Method setSeriesTransferDaysSets the value of field
     * 'seriesTransferDays'.
     * 
     * @param seriesTransferDays the value of field
     * 'seriesTransferDays'.
     */
    public void setSeriesTransferDays(int seriesTransferDays)
    {
        this._seriesTransferDays = seriesTransferDays;
        
        super.setVoChanged(true);
        this._has_seriesTransferDays = true;
    } //-- void setSeriesTransferDays(int) 

    /**
     * Method setSeriesTransferDaysTypeCTSets the value of field
     * 'seriesTransferDaysTypeCT'.
     * 
     * @param seriesTransferDaysTypeCT the value of field
     * 'seriesTransferDaysTypeCT'.
     */
    public void setSeriesTransferDaysTypeCT(java.lang.String seriesTransferDaysTypeCT)
    {
        this._seriesTransferDaysTypeCT = seriesTransferDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setSeriesTransferDaysTypeCT(java.lang.String) 

    /**
     * Method setSeriesTransferModeCTSets the value of field
     * 'seriesTransferModeCT'.
     * 
     * @param seriesTransferModeCT the value of field
     * 'seriesTransferModeCT'.
     */
    public void setSeriesTransferModeCT(java.lang.String seriesTransferModeCT)
    {
        this._seriesTransferModeCT = seriesTransferModeCT;
        
        super.setVoChanged(true);
    } //-- void setSeriesTransferModeCT(java.lang.String) 

    /**
     * Method setSubscriptionDaysTypeCTSets the value of field
     * 'subscriptionDaysTypeCT'.
     * 
     * @param subscriptionDaysTypeCT the value of field
     * 'subscriptionDaysTypeCT'.
     */
    public void setSubscriptionDaysTypeCT(java.lang.String subscriptionDaysTypeCT)
    {
        this._subscriptionDaysTypeCT = subscriptionDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setSubscriptionDaysTypeCT(java.lang.String) 

    /**
     * Method setSubscriptionModeCTSets the value of field
     * 'subscriptionModeCT'.
     * 
     * @param subscriptionModeCT the value of field
     * 'subscriptionModeCT'.
     */
    public void setSubscriptionModeCT(java.lang.String subscriptionModeCT)
    {
        this._subscriptionModeCT = subscriptionModeCT;
        
        super.setVoChanged(true);
    } //-- void setSubscriptionModeCT(java.lang.String) 

    /**
     * Method setSubscriptionNotificationDaysSets the value of
     * field 'subscriptionNotificationDays'.
     * 
     * @param subscriptionNotificationDays the value of field
     * 'subscriptionNotificationDays'.
     */
    public void setSubscriptionNotificationDays(int subscriptionNotificationDays)
    {
        this._subscriptionNotificationDays = subscriptionNotificationDays;
        
        super.setVoChanged(true);
        this._has_subscriptionNotificationDays = true;
    } //-- void setSubscriptionNotificationDays(int) 

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
     * Method setTransferDaysSets the value of field
     * 'transferDays'.
     * 
     * @param transferDays the value of field 'transferDays'.
     */
    public void setTransferDays(int transferDays)
    {
        this._transferDays = transferDays;
        
        super.setVoChanged(true);
        this._has_transferDays = true;
    } //-- void setTransferDays(int) 

    /**
     * Method setTransferDaysTypeCTSets the value of field
     * 'transferDaysTypeCT'.
     * 
     * @param transferDaysTypeCT the value of field
     * 'transferDaysTypeCT'.
     */
    public void setTransferDaysTypeCT(java.lang.String transferDaysTypeCT)
    {
        this._transferDaysTypeCT = transferDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTransferDaysTypeCT(java.lang.String) 

    /**
     * Method setTransferModeCTSets the value of field
     * 'transferModeCT'.
     * 
     * @param transferModeCT the value of field 'transferModeCT'.
     */
    public void setTransferModeCT(java.lang.String transferModeCT)
    {
        this._transferModeCT = transferModeCT;
        
        super.setVoChanged(true);
    } //-- void setTransferModeCT(java.lang.String) 

    /**
     * Method setUnitValuesVO
     * 
     * @param index
     * @param vUnitValuesVO
     */
    public void setUnitValuesVO(int index, edit.common.vo.UnitValuesVO vUnitValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _unitValuesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vUnitValuesVO.setParentVO(this.getClass(), this);
        _unitValuesVOList.setElementAt(vUnitValuesVO, index);
    } //-- void setUnitValuesVO(int, edit.common.vo.UnitValuesVO) 

    /**
     * Method setUnitValuesVO
     * 
     * @param unitValuesVOArray
     */
    public void setUnitValuesVO(edit.common.vo.UnitValuesVO[] unitValuesVOArray)
    {
        //-- copy array
        _unitValuesVOList.removeAllElements();
        for (int i = 0; i < unitValuesVOArray.length; i++) {
            unitValuesVOArray[i].setParentVO(this.getClass(), this);
            _unitValuesVOList.addElement(unitValuesVOArray[i]);
        }
    } //-- void setUnitValuesVO(edit.common.vo.UnitValuesVO) 

    /**
     * Method setWithdrawalDaysSets the value of field
     * 'withdrawalDays'.
     * 
     * @param withdrawalDays the value of field 'withdrawalDays'.
     */
    public void setWithdrawalDays(int withdrawalDays)
    {
        this._withdrawalDays = withdrawalDays;
        
        super.setVoChanged(true);
        this._has_withdrawalDays = true;
    } //-- void setWithdrawalDays(int) 

    /**
     * Method setWithdrawalDaysTypeCTSets the value of field
     * 'withdrawalDaysTypeCT'.
     * 
     * @param withdrawalDaysTypeCT the value of field
     * 'withdrawalDaysTypeCT'.
     */
    public void setWithdrawalDaysTypeCT(java.lang.String withdrawalDaysTypeCT)
    {
        this._withdrawalDaysTypeCT = withdrawalDaysTypeCT;
        
        super.setVoChanged(true);
    } //-- void setWithdrawalDaysTypeCT(java.lang.String) 

    /**
     * Method setWithdrawalModeCTSets the value of field
     * 'withdrawalModeCT'.
     * 
     * @param withdrawalModeCT the value of field 'withdrawalModeCT'
     */
    public void setWithdrawalModeCT(java.lang.String withdrawalModeCT)
    {
        this._withdrawalModeCT = withdrawalModeCT;
        
        super.setVoChanged(true);
    } //-- void setWithdrawalModeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FilteredFundVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredFundVO) Unmarshaller.unmarshal(edit.common.vo.FilteredFundVO.class, reader);
    } //-- edit.common.vo.FilteredFundVO unmarshal(java.io.Reader) 

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
