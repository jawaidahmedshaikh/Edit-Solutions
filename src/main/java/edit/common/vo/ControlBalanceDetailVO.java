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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class ControlBalanceDetailVO.
 * 
 * @version $Revision$ $Date$
 */
public class ControlBalanceDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _controlBalanceDetailPK
     */
    private long _controlBalanceDetailPK;

    /**
     * keeps track of state for field: _controlBalanceDetailPK
     */
    private boolean _has_controlBalanceDetailPK;

    /**
     * Field _controlBalanceFK
     */
    private long _controlBalanceFK;

    /**
     * keeps track of state for field: _controlBalanceFK
     */
    private boolean _has_controlBalanceFK;

    /**
     * Field _netAssets
     */
    private java.math.BigDecimal _netAssets;

    /**
     * Field _policyOwnerValue
     */
    private java.math.BigDecimal _policyOwnerValue;

    /**
     * Field _accruedGainLoss
     */
    private java.math.BigDecimal _accruedGainLoss;

    /**
     * Field _totalAccruedNetPremiums
     */
    private java.math.BigDecimal _totalAccruedNetPremiums;

    /**
     * Field _totalAccruedAdminFees
     */
    private java.math.BigDecimal _totalAccruedAdminFees;

    /**
     * Field _totalAccruedCOI
     */
    private java.math.BigDecimal _totalAccruedCOI;

    /**
     * Field _totalAccruedReallocations
     */
    private java.math.BigDecimal _totalAccruedReallocations;

    /**
     * Field _totalAccruedRRD
     */
    private java.math.BigDecimal _totalAccruedRRD;

    /**
     * Field _totalAccruedSurrenders
     */
    private java.math.BigDecimal _totalAccruedSurrenders;

    /**
     * Field _totalAccruedContribToMortRsv
     */
    private java.math.BigDecimal _totalAccruedContribToMortRsv;

    /**
     * Field _totalAccruedMAndE
     */
    private java.math.BigDecimal _totalAccruedMAndE;

    /**
     * Field _totalAccruedAdvisoryFees
     */
    private java.math.BigDecimal _totalAccruedAdvisoryFees;

    /**
     * Field _totalAccruedMgtFees
     */
    private java.math.BigDecimal _totalAccruedMgtFees;

    /**
     * Field _totalAccruedRVPFees
     */
    private java.math.BigDecimal _totalAccruedRVPFees;

    /**
     * Field _totalAccruedAdvanceTransfers
     */
    private java.math.BigDecimal _totalAccruedAdvanceTransfers;

    /**
     * Field _MAndEPayableReceivables
     */
    private java.math.BigDecimal _MAndEPayableReceivables;

    /**
     * Field _advisoryFeePayablesReceivables
     */
    private java.math.BigDecimal _advisoryFeePayablesReceivables;

    /**
     * Field _mgtFeePayablesReceivables
     */
    private java.math.BigDecimal _mgtFeePayablesReceivables;

    /**
     * Field _RVPPayablesReceivables
     */
    private java.math.BigDecimal _RVPPayablesReceivables;

    /**
     * Field _unitBalance
     */
    private java.math.BigDecimal _unitBalance;

    /**
     * Field _accountingPeriod
     */
    private java.lang.String _accountingPeriod;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _valuationDate
     */
    private java.lang.String _valuationDate;

    /**
     * Field _shareBalance
     */
    private java.math.BigDecimal _shareBalance;

    /**
     * Field _totalCashNetPremium
     */
    private java.math.BigDecimal _totalCashNetPremium;

    /**
     * Field _cashGainLoss
     */
    private java.math.BigDecimal _cashGainLoss;

    /**
     * Field _totalCashAdminFees
     */
    private java.math.BigDecimal _totalCashAdminFees;

    /**
     * Field _totalCashCoi
     */
    private java.math.BigDecimal _totalCashCoi;

    /**
     * Field _totalCashReallocations
     */
    private java.math.BigDecimal _totalCashReallocations;

    /**
     * Field _totalCashRRD
     */
    private java.math.BigDecimal _totalCashRRD;

    /**
     * Field _totalCashSurrenders
     */
    private java.math.BigDecimal _totalCashSurrenders;

    /**
     * Field _totalCashContribToMortRsv
     */
    private java.math.BigDecimal _totalCashContribToMortRsv;

    /**
     * Field _totalCashMAndE
     */
    private java.math.BigDecimal _totalCashMAndE;

    /**
     * Field _totalCashAdvisoryFees
     */
    private java.math.BigDecimal _totalCashAdvisoryFees;

    /**
     * Field _totalCashMgmtFees
     */
    private java.math.BigDecimal _totalCashMgmtFees;

    /**
     * Field _totalCashSVAFees
     */
    private java.math.BigDecimal _totalCashSVAFees;

    /**
     * Field _totalCashAdvanceTransfers
     */
    private java.math.BigDecimal _totalCashAdvanceTransfers;

    /**
     * Field _netCash
     */
    private java.math.BigDecimal _netCash;

    /**
     * Field _sharesPurchased
     */
    private java.math.BigDecimal _sharesPurchased;

    /**
     * Field _policyActivity
     */
    private java.math.BigDecimal _policyActivity;

    /**
     * Field _unitsPurchased
     */
    private java.math.BigDecimal _unitsPurchased;


      //----------------/
     //- Constructors -/
    //----------------/

    public ControlBalanceDetailVO() {
        super();
    } //-- edit.common.vo.ControlBalanceDetailVO()


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
        
        if (obj instanceof ControlBalanceDetailVO) {
        
            ControlBalanceDetailVO temp = (ControlBalanceDetailVO)obj;
            if (this._controlBalanceDetailPK != temp._controlBalanceDetailPK)
                return false;
            if (this._has_controlBalanceDetailPK != temp._has_controlBalanceDetailPK)
                return false;
            if (this._controlBalanceFK != temp._controlBalanceFK)
                return false;
            if (this._has_controlBalanceFK != temp._has_controlBalanceFK)
                return false;
            if (this._netAssets != null) {
                if (temp._netAssets == null) return false;
                else if (!(this._netAssets.equals(temp._netAssets))) 
                    return false;
            }
            else if (temp._netAssets != null)
                return false;
            if (this._policyOwnerValue != null) {
                if (temp._policyOwnerValue == null) return false;
                else if (!(this._policyOwnerValue.equals(temp._policyOwnerValue))) 
                    return false;
            }
            else if (temp._policyOwnerValue != null)
                return false;
            if (this._accruedGainLoss != null) {
                if (temp._accruedGainLoss == null) return false;
                else if (!(this._accruedGainLoss.equals(temp._accruedGainLoss))) 
                    return false;
            }
            else if (temp._accruedGainLoss != null)
                return false;
            if (this._totalAccruedNetPremiums != null) {
                if (temp._totalAccruedNetPremiums == null) return false;
                else if (!(this._totalAccruedNetPremiums.equals(temp._totalAccruedNetPremiums))) 
                    return false;
            }
            else if (temp._totalAccruedNetPremiums != null)
                return false;
            if (this._totalAccruedAdminFees != null) {
                if (temp._totalAccruedAdminFees == null) return false;
                else if (!(this._totalAccruedAdminFees.equals(temp._totalAccruedAdminFees))) 
                    return false;
            }
            else if (temp._totalAccruedAdminFees != null)
                return false;
            if (this._totalAccruedCOI != null) {
                if (temp._totalAccruedCOI == null) return false;
                else if (!(this._totalAccruedCOI.equals(temp._totalAccruedCOI))) 
                    return false;
            }
            else if (temp._totalAccruedCOI != null)
                return false;
            if (this._totalAccruedReallocations != null) {
                if (temp._totalAccruedReallocations == null) return false;
                else if (!(this._totalAccruedReallocations.equals(temp._totalAccruedReallocations))) 
                    return false;
            }
            else if (temp._totalAccruedReallocations != null)
                return false;
            if (this._totalAccruedRRD != null) {
                if (temp._totalAccruedRRD == null) return false;
                else if (!(this._totalAccruedRRD.equals(temp._totalAccruedRRD))) 
                    return false;
            }
            else if (temp._totalAccruedRRD != null)
                return false;
            if (this._totalAccruedSurrenders != null) {
                if (temp._totalAccruedSurrenders == null) return false;
                else if (!(this._totalAccruedSurrenders.equals(temp._totalAccruedSurrenders))) 
                    return false;
            }
            else if (temp._totalAccruedSurrenders != null)
                return false;
            if (this._totalAccruedContribToMortRsv != null) {
                if (temp._totalAccruedContribToMortRsv == null) return false;
                else if (!(this._totalAccruedContribToMortRsv.equals(temp._totalAccruedContribToMortRsv))) 
                    return false;
            }
            else if (temp._totalAccruedContribToMortRsv != null)
                return false;
            if (this._totalAccruedMAndE != null) {
                if (temp._totalAccruedMAndE == null) return false;
                else if (!(this._totalAccruedMAndE.equals(temp._totalAccruedMAndE))) 
                    return false;
            }
            else if (temp._totalAccruedMAndE != null)
                return false;
            if (this._totalAccruedAdvisoryFees != null) {
                if (temp._totalAccruedAdvisoryFees == null) return false;
                else if (!(this._totalAccruedAdvisoryFees.equals(temp._totalAccruedAdvisoryFees))) 
                    return false;
            }
            else if (temp._totalAccruedAdvisoryFees != null)
                return false;
            if (this._totalAccruedMgtFees != null) {
                if (temp._totalAccruedMgtFees == null) return false;
                else if (!(this._totalAccruedMgtFees.equals(temp._totalAccruedMgtFees))) 
                    return false;
            }
            else if (temp._totalAccruedMgtFees != null)
                return false;
            if (this._totalAccruedRVPFees != null) {
                if (temp._totalAccruedRVPFees == null) return false;
                else if (!(this._totalAccruedRVPFees.equals(temp._totalAccruedRVPFees))) 
                    return false;
            }
            else if (temp._totalAccruedRVPFees != null)
                return false;
            if (this._totalAccruedAdvanceTransfers != null) {
                if (temp._totalAccruedAdvanceTransfers == null) return false;
                else if (!(this._totalAccruedAdvanceTransfers.equals(temp._totalAccruedAdvanceTransfers))) 
                    return false;
            }
            else if (temp._totalAccruedAdvanceTransfers != null)
                return false;
            if (this._MAndEPayableReceivables != null) {
                if (temp._MAndEPayableReceivables == null) return false;
                else if (!(this._MAndEPayableReceivables.equals(temp._MAndEPayableReceivables))) 
                    return false;
            }
            else if (temp._MAndEPayableReceivables != null)
                return false;
            if (this._advisoryFeePayablesReceivables != null) {
                if (temp._advisoryFeePayablesReceivables == null) return false;
                else if (!(this._advisoryFeePayablesReceivables.equals(temp._advisoryFeePayablesReceivables))) 
                    return false;
            }
            else if (temp._advisoryFeePayablesReceivables != null)
                return false;
            if (this._mgtFeePayablesReceivables != null) {
                if (temp._mgtFeePayablesReceivables == null) return false;
                else if (!(this._mgtFeePayablesReceivables.equals(temp._mgtFeePayablesReceivables))) 
                    return false;
            }
            else if (temp._mgtFeePayablesReceivables != null)
                return false;
            if (this._RVPPayablesReceivables != null) {
                if (temp._RVPPayablesReceivables == null) return false;
                else if (!(this._RVPPayablesReceivables.equals(temp._RVPPayablesReceivables))) 
                    return false;
            }
            else if (temp._RVPPayablesReceivables != null)
                return false;
            if (this._unitBalance != null) {
                if (temp._unitBalance == null) return false;
                else if (!(this._unitBalance.equals(temp._unitBalance))) 
                    return false;
            }
            else if (temp._unitBalance != null)
                return false;
            if (this._accountingPeriod != null) {
                if (temp._accountingPeriod == null) return false;
                else if (!(this._accountingPeriod.equals(temp._accountingPeriod))) 
                    return false;
            }
            else if (temp._accountingPeriod != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._valuationDate != null) {
                if (temp._valuationDate == null) return false;
                else if (!(this._valuationDate.equals(temp._valuationDate))) 
                    return false;
            }
            else if (temp._valuationDate != null)
                return false;
            if (this._shareBalance != null) {
                if (temp._shareBalance == null) return false;
                else if (!(this._shareBalance.equals(temp._shareBalance))) 
                    return false;
            }
            else if (temp._shareBalance != null)
                return false;
            if (this._totalCashNetPremium != null) {
                if (temp._totalCashNetPremium == null) return false;
                else if (!(this._totalCashNetPremium.equals(temp._totalCashNetPremium))) 
                    return false;
            }
            else if (temp._totalCashNetPremium != null)
                return false;
            if (this._cashGainLoss != null) {
                if (temp._cashGainLoss == null) return false;
                else if (!(this._cashGainLoss.equals(temp._cashGainLoss))) 
                    return false;
            }
            else if (temp._cashGainLoss != null)
                return false;
            if (this._totalCashAdminFees != null) {
                if (temp._totalCashAdminFees == null) return false;
                else if (!(this._totalCashAdminFees.equals(temp._totalCashAdminFees))) 
                    return false;
            }
            else if (temp._totalCashAdminFees != null)
                return false;
            if (this._totalCashCoi != null) {
                if (temp._totalCashCoi == null) return false;
                else if (!(this._totalCashCoi.equals(temp._totalCashCoi))) 
                    return false;
            }
            else if (temp._totalCashCoi != null)
                return false;
            if (this._totalCashReallocations != null) {
                if (temp._totalCashReallocations == null) return false;
                else if (!(this._totalCashReallocations.equals(temp._totalCashReallocations))) 
                    return false;
            }
            else if (temp._totalCashReallocations != null)
                return false;
            if (this._totalCashRRD != null) {
                if (temp._totalCashRRD == null) return false;
                else if (!(this._totalCashRRD.equals(temp._totalCashRRD))) 
                    return false;
            }
            else if (temp._totalCashRRD != null)
                return false;
            if (this._totalCashSurrenders != null) {
                if (temp._totalCashSurrenders == null) return false;
                else if (!(this._totalCashSurrenders.equals(temp._totalCashSurrenders))) 
                    return false;
            }
            else if (temp._totalCashSurrenders != null)
                return false;
            if (this._totalCashContribToMortRsv != null) {
                if (temp._totalCashContribToMortRsv == null) return false;
                else if (!(this._totalCashContribToMortRsv.equals(temp._totalCashContribToMortRsv))) 
                    return false;
            }
            else if (temp._totalCashContribToMortRsv != null)
                return false;
            if (this._totalCashMAndE != null) {
                if (temp._totalCashMAndE == null) return false;
                else if (!(this._totalCashMAndE.equals(temp._totalCashMAndE))) 
                    return false;
            }
            else if (temp._totalCashMAndE != null)
                return false;
            if (this._totalCashAdvisoryFees != null) {
                if (temp._totalCashAdvisoryFees == null) return false;
                else if (!(this._totalCashAdvisoryFees.equals(temp._totalCashAdvisoryFees))) 
                    return false;
            }
            else if (temp._totalCashAdvisoryFees != null)
                return false;
            if (this._totalCashMgmtFees != null) {
                if (temp._totalCashMgmtFees == null) return false;
                else if (!(this._totalCashMgmtFees.equals(temp._totalCashMgmtFees))) 
                    return false;
            }
            else if (temp._totalCashMgmtFees != null)
                return false;
            if (this._totalCashSVAFees != null) {
                if (temp._totalCashSVAFees == null) return false;
                else if (!(this._totalCashSVAFees.equals(temp._totalCashSVAFees))) 
                    return false;
            }
            else if (temp._totalCashSVAFees != null)
                return false;
            if (this._totalCashAdvanceTransfers != null) {
                if (temp._totalCashAdvanceTransfers == null) return false;
                else if (!(this._totalCashAdvanceTransfers.equals(temp._totalCashAdvanceTransfers))) 
                    return false;
            }
            else if (temp._totalCashAdvanceTransfers != null)
                return false;
            if (this._netCash != null) {
                if (temp._netCash == null) return false;
                else if (!(this._netCash.equals(temp._netCash))) 
                    return false;
            }
            else if (temp._netCash != null)
                return false;
            if (this._sharesPurchased != null) {
                if (temp._sharesPurchased == null) return false;
                else if (!(this._sharesPurchased.equals(temp._sharesPurchased))) 
                    return false;
            }
            else if (temp._sharesPurchased != null)
                return false;
            if (this._policyActivity != null) {
                if (temp._policyActivity == null) return false;
                else if (!(this._policyActivity.equals(temp._policyActivity))) 
                    return false;
            }
            else if (temp._policyActivity != null)
                return false;
            if (this._unitsPurchased != null) {
                if (temp._unitsPurchased == null) return false;
                else if (!(this._unitsPurchased.equals(temp._unitsPurchased))) 
                    return false;
            }
            else if (temp._unitsPurchased != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getAccruedGainLossReturns the value of field
     * 'accruedGainLoss'.
     * 
     * @return the value of field 'accruedGainLoss'.
     */
    public java.math.BigDecimal getAccruedGainLoss()
    {
        return this._accruedGainLoss;
    } //-- java.math.BigDecimal getAccruedGainLoss() 

    /**
     * Method getAdvisoryFeePayablesReceivablesReturns the value of
     * field 'advisoryFeePayablesReceivables'.
     * 
     * @return the value of field 'advisoryFeePayablesReceivables'.
     */
    public java.math.BigDecimal getAdvisoryFeePayablesReceivables()
    {
        return this._advisoryFeePayablesReceivables;
    } //-- java.math.BigDecimal getAdvisoryFeePayablesReceivables() 

    /**
     * Method getCashGainLossReturns the value of field
     * 'cashGainLoss'.
     * 
     * @return the value of field 'cashGainLoss'.
     */
    public java.math.BigDecimal getCashGainLoss()
    {
        return this._cashGainLoss;
    } //-- java.math.BigDecimal getCashGainLoss() 

    /**
     * Method getControlBalanceDetailPKReturns the value of field
     * 'controlBalanceDetailPK'.
     * 
     * @return the value of field 'controlBalanceDetailPK'.
     */
    public long getControlBalanceDetailPK()
    {
        return this._controlBalanceDetailPK;
    } //-- long getControlBalanceDetailPK() 

    /**
     * Method getControlBalanceFKReturns the value of field
     * 'controlBalanceFK'.
     * 
     * @return the value of field 'controlBalanceFK'.
     */
    public long getControlBalanceFK()
    {
        return this._controlBalanceFK;
    } //-- long getControlBalanceFK() 

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
     * Method getMAndEPayableReceivablesReturns the value of field
     * 'MAndEPayableReceivables'.
     * 
     * @return the value of field 'MAndEPayableReceivables'.
     */
    public java.math.BigDecimal getMAndEPayableReceivables()
    {
        return this._MAndEPayableReceivables;
    } //-- java.math.BigDecimal getMAndEPayableReceivables() 

    /**
     * Method getMgtFeePayablesReceivablesReturns the value of
     * field 'mgtFeePayablesReceivables'.
     * 
     * @return the value of field 'mgtFeePayablesReceivables'.
     */
    public java.math.BigDecimal getMgtFeePayablesReceivables()
    {
        return this._mgtFeePayablesReceivables;
    } //-- java.math.BigDecimal getMgtFeePayablesReceivables() 

    /**
     * Method getNetAssetsReturns the value of field 'netAssets'.
     * 
     * @return the value of field 'netAssets'.
     */
    public java.math.BigDecimal getNetAssets()
    {
        return this._netAssets;
    } //-- java.math.BigDecimal getNetAssets() 

    /**
     * Method getNetCashReturns the value of field 'netCash'.
     * 
     * @return the value of field 'netCash'.
     */
    public java.math.BigDecimal getNetCash()
    {
        return this._netCash;
    } //-- java.math.BigDecimal getNetCash() 

    /**
     * Method getPolicyActivityReturns the value of field
     * 'policyActivity'.
     * 
     * @return the value of field 'policyActivity'.
     */
    public java.math.BigDecimal getPolicyActivity()
    {
        return this._policyActivity;
    } //-- java.math.BigDecimal getPolicyActivity() 

    /**
     * Method getPolicyOwnerValueReturns the value of field
     * 'policyOwnerValue'.
     * 
     * @return the value of field 'policyOwnerValue'.
     */
    public java.math.BigDecimal getPolicyOwnerValue()
    {
        return this._policyOwnerValue;
    } //-- java.math.BigDecimal getPolicyOwnerValue() 

    /**
     * Method getRVPPayablesReceivablesReturns the value of field
     * 'RVPPayablesReceivables'.
     * 
     * @return the value of field 'RVPPayablesReceivables'.
     */
    public java.math.BigDecimal getRVPPayablesReceivables()
    {
        return this._RVPPayablesReceivables;
    } //-- java.math.BigDecimal getRVPPayablesReceivables() 

    /**
     * Method getShareBalanceReturns the value of field
     * 'shareBalance'.
     * 
     * @return the value of field 'shareBalance'.
     */
    public java.math.BigDecimal getShareBalance()
    {
        return this._shareBalance;
    } //-- java.math.BigDecimal getShareBalance() 

    /**
     * Method getSharesPurchasedReturns the value of field
     * 'sharesPurchased'.
     * 
     * @return the value of field 'sharesPurchased'.
     */
    public java.math.BigDecimal getSharesPurchased()
    {
        return this._sharesPurchased;
    } //-- java.math.BigDecimal getSharesPurchased() 

    /**
     * Method getTotalAccruedAdminFeesReturns the value of field
     * 'totalAccruedAdminFees'.
     * 
     * @return the value of field 'totalAccruedAdminFees'.
     */
    public java.math.BigDecimal getTotalAccruedAdminFees()
    {
        return this._totalAccruedAdminFees;
    } //-- java.math.BigDecimal getTotalAccruedAdminFees() 

    /**
     * Method getTotalAccruedAdvanceTransfersReturns the value of
     * field 'totalAccruedAdvanceTransfers'.
     * 
     * @return the value of field 'totalAccruedAdvanceTransfers'.
     */
    public java.math.BigDecimal getTotalAccruedAdvanceTransfers()
    {
        return this._totalAccruedAdvanceTransfers;
    } //-- java.math.BigDecimal getTotalAccruedAdvanceTransfers() 

    /**
     * Method getTotalAccruedAdvisoryFeesReturns the value of field
     * 'totalAccruedAdvisoryFees'.
     * 
     * @return the value of field 'totalAccruedAdvisoryFees'.
     */
    public java.math.BigDecimal getTotalAccruedAdvisoryFees()
    {
        return this._totalAccruedAdvisoryFees;
    } //-- java.math.BigDecimal getTotalAccruedAdvisoryFees() 

    /**
     * Method getTotalAccruedCOIReturns the value of field
     * 'totalAccruedCOI'.
     * 
     * @return the value of field 'totalAccruedCOI'.
     */
    public java.math.BigDecimal getTotalAccruedCOI()
    {
        return this._totalAccruedCOI;
    } //-- java.math.BigDecimal getTotalAccruedCOI() 

    /**
     * Method getTotalAccruedContribToMortRsvReturns the value of
     * field 'totalAccruedContribToMortRsv'.
     * 
     * @return the value of field 'totalAccruedContribToMortRsv'.
     */
    public java.math.BigDecimal getTotalAccruedContribToMortRsv()
    {
        return this._totalAccruedContribToMortRsv;
    } //-- java.math.BigDecimal getTotalAccruedContribToMortRsv() 

    /**
     * Method getTotalAccruedMAndEReturns the value of field
     * 'totalAccruedMAndE'.
     * 
     * @return the value of field 'totalAccruedMAndE'.
     */
    public java.math.BigDecimal getTotalAccruedMAndE()
    {
        return this._totalAccruedMAndE;
    } //-- java.math.BigDecimal getTotalAccruedMAndE() 

    /**
     * Method getTotalAccruedMgtFeesReturns the value of field
     * 'totalAccruedMgtFees'.
     * 
     * @return the value of field 'totalAccruedMgtFees'.
     */
    public java.math.BigDecimal getTotalAccruedMgtFees()
    {
        return this._totalAccruedMgtFees;
    } //-- java.math.BigDecimal getTotalAccruedMgtFees() 

    /**
     * Method getTotalAccruedNetPremiumsReturns the value of field
     * 'totalAccruedNetPremiums'.
     * 
     * @return the value of field 'totalAccruedNetPremiums'.
     */
    public java.math.BigDecimal getTotalAccruedNetPremiums()
    {
        return this._totalAccruedNetPremiums;
    } //-- java.math.BigDecimal getTotalAccruedNetPremiums() 

    /**
     * Method getTotalAccruedRRDReturns the value of field
     * 'totalAccruedRRD'.
     * 
     * @return the value of field 'totalAccruedRRD'.
     */
    public java.math.BigDecimal getTotalAccruedRRD()
    {
        return this._totalAccruedRRD;
    } //-- java.math.BigDecimal getTotalAccruedRRD() 

    /**
     * Method getTotalAccruedRVPFeesReturns the value of field
     * 'totalAccruedRVPFees'.
     * 
     * @return the value of field 'totalAccruedRVPFees'.
     */
    public java.math.BigDecimal getTotalAccruedRVPFees()
    {
        return this._totalAccruedRVPFees;
    } //-- java.math.BigDecimal getTotalAccruedRVPFees() 

    /**
     * Method getTotalAccruedReallocationsReturns the value of
     * field 'totalAccruedReallocations'.
     * 
     * @return the value of field 'totalAccruedReallocations'.
     */
    public java.math.BigDecimal getTotalAccruedReallocations()
    {
        return this._totalAccruedReallocations;
    } //-- java.math.BigDecimal getTotalAccruedReallocations() 

    /**
     * Method getTotalAccruedSurrendersReturns the value of field
     * 'totalAccruedSurrenders'.
     * 
     * @return the value of field 'totalAccruedSurrenders'.
     */
    public java.math.BigDecimal getTotalAccruedSurrenders()
    {
        return this._totalAccruedSurrenders;
    } //-- java.math.BigDecimal getTotalAccruedSurrenders() 

    /**
     * Method getTotalCashAdminFeesReturns the value of field
     * 'totalCashAdminFees'.
     * 
     * @return the value of field 'totalCashAdminFees'.
     */
    public java.math.BigDecimal getTotalCashAdminFees()
    {
        return this._totalCashAdminFees;
    } //-- java.math.BigDecimal getTotalCashAdminFees() 

    /**
     * Method getTotalCashAdvanceTransfersReturns the value of
     * field 'totalCashAdvanceTransfers'.
     * 
     * @return the value of field 'totalCashAdvanceTransfers'.
     */
    public java.math.BigDecimal getTotalCashAdvanceTransfers()
    {
        return this._totalCashAdvanceTransfers;
    } //-- java.math.BigDecimal getTotalCashAdvanceTransfers() 

    /**
     * Method getTotalCashAdvisoryFeesReturns the value of field
     * 'totalCashAdvisoryFees'.
     * 
     * @return the value of field 'totalCashAdvisoryFees'.
     */
    public java.math.BigDecimal getTotalCashAdvisoryFees()
    {
        return this._totalCashAdvisoryFees;
    } //-- java.math.BigDecimal getTotalCashAdvisoryFees() 

    /**
     * Method getTotalCashCoiReturns the value of field
     * 'totalCashCoi'.
     * 
     * @return the value of field 'totalCashCoi'.
     */
    public java.math.BigDecimal getTotalCashCoi()
    {
        return this._totalCashCoi;
    } //-- java.math.BigDecimal getTotalCashCoi() 

    /**
     * Method getTotalCashContribToMortRsvReturns the value of
     * field 'totalCashContribToMortRsv'.
     * 
     * @return the value of field 'totalCashContribToMortRsv'.
     */
    public java.math.BigDecimal getTotalCashContribToMortRsv()
    {
        return this._totalCashContribToMortRsv;
    } //-- java.math.BigDecimal getTotalCashContribToMortRsv() 

    /**
     * Method getTotalCashMAndEReturns the value of field
     * 'totalCashMAndE'.
     * 
     * @return the value of field 'totalCashMAndE'.
     */
    public java.math.BigDecimal getTotalCashMAndE()
    {
        return this._totalCashMAndE;
    } //-- java.math.BigDecimal getTotalCashMAndE() 

    /**
     * Method getTotalCashMgmtFeesReturns the value of field
     * 'totalCashMgmtFees'.
     * 
     * @return the value of field 'totalCashMgmtFees'.
     */
    public java.math.BigDecimal getTotalCashMgmtFees()
    {
        return this._totalCashMgmtFees;
    } //-- java.math.BigDecimal getTotalCashMgmtFees() 

    /**
     * Method getTotalCashNetPremiumReturns the value of field
     * 'totalCashNetPremium'.
     * 
     * @return the value of field 'totalCashNetPremium'.
     */
    public java.math.BigDecimal getTotalCashNetPremium()
    {
        return this._totalCashNetPremium;
    } //-- java.math.BigDecimal getTotalCashNetPremium() 

    /**
     * Method getTotalCashRRDReturns the value of field
     * 'totalCashRRD'.
     * 
     * @return the value of field 'totalCashRRD'.
     */
    public java.math.BigDecimal getTotalCashRRD()
    {
        return this._totalCashRRD;
    } //-- java.math.BigDecimal getTotalCashRRD() 

    /**
     * Method getTotalCashReallocationsReturns the value of field
     * 'totalCashReallocations'.
     * 
     * @return the value of field 'totalCashReallocations'.
     */
    public java.math.BigDecimal getTotalCashReallocations()
    {
        return this._totalCashReallocations;
    } //-- java.math.BigDecimal getTotalCashReallocations() 

    /**
     * Method getTotalCashSVAFeesReturns the value of field
     * 'totalCashSVAFees'.
     * 
     * @return the value of field 'totalCashSVAFees'.
     */
    public java.math.BigDecimal getTotalCashSVAFees()
    {
        return this._totalCashSVAFees;
    } //-- java.math.BigDecimal getTotalCashSVAFees() 

    /**
     * Method getTotalCashSurrendersReturns the value of field
     * 'totalCashSurrenders'.
     * 
     * @return the value of field 'totalCashSurrenders'.
     */
    public java.math.BigDecimal getTotalCashSurrenders()
    {
        return this._totalCashSurrenders;
    } //-- java.math.BigDecimal getTotalCashSurrenders() 

    /**
     * Method getUnitBalanceReturns the value of field
     * 'unitBalance'.
     * 
     * @return the value of field 'unitBalance'.
     */
    public java.math.BigDecimal getUnitBalance()
    {
        return this._unitBalance;
    } //-- java.math.BigDecimal getUnitBalance() 

    /**
     * Method getUnitsPurchasedReturns the value of field
     * 'unitsPurchased'.
     * 
     * @return the value of field 'unitsPurchased'.
     */
    public java.math.BigDecimal getUnitsPurchased()
    {
        return this._unitsPurchased;
    } //-- java.math.BigDecimal getUnitsPurchased() 

    /**
     * Method getValuationDateReturns the value of field
     * 'valuationDate'.
     * 
     * @return the value of field 'valuationDate'.
     */
    public java.lang.String getValuationDate()
    {
        return this._valuationDate;
    } //-- java.lang.String getValuationDate() 

    /**
     * Method hasControlBalanceDetailPK
     */
    public boolean hasControlBalanceDetailPK()
    {
        return this._has_controlBalanceDetailPK;
    } //-- boolean hasControlBalanceDetailPK() 

    /**
     * Method hasControlBalanceFK
     */
    public boolean hasControlBalanceFK()
    {
        return this._has_controlBalanceFK;
    } //-- boolean hasControlBalanceFK() 

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
     * Method setAccruedGainLossSets the value of field
     * 'accruedGainLoss'.
     * 
     * @param accruedGainLoss the value of field 'accruedGainLoss'.
     */
    public void setAccruedGainLoss(java.math.BigDecimal accruedGainLoss)
    {
        this._accruedGainLoss = accruedGainLoss;
        
        super.setVoChanged(true);
    } //-- void setAccruedGainLoss(java.math.BigDecimal) 

    /**
     * Method setAdvisoryFeePayablesReceivablesSets the value of
     * field 'advisoryFeePayablesReceivables'.
     * 
     * @param advisoryFeePayablesReceivables the value of field
     * 'advisoryFeePayablesReceivables'.
     */
    public void setAdvisoryFeePayablesReceivables(java.math.BigDecimal advisoryFeePayablesReceivables)
    {
        this._advisoryFeePayablesReceivables = advisoryFeePayablesReceivables;
        
        super.setVoChanged(true);
    } //-- void setAdvisoryFeePayablesReceivables(java.math.BigDecimal) 

    /**
     * Method setCashGainLossSets the value of field
     * 'cashGainLoss'.
     * 
     * @param cashGainLoss the value of field 'cashGainLoss'.
     */
    public void setCashGainLoss(java.math.BigDecimal cashGainLoss)
    {
        this._cashGainLoss = cashGainLoss;
        
        super.setVoChanged(true);
    } //-- void setCashGainLoss(java.math.BigDecimal) 

    /**
     * Method setControlBalanceDetailPKSets the value of field
     * 'controlBalanceDetailPK'.
     * 
     * @param controlBalanceDetailPK the value of field
     * 'controlBalanceDetailPK'.
     */
    public void setControlBalanceDetailPK(long controlBalanceDetailPK)
    {
        this._controlBalanceDetailPK = controlBalanceDetailPK;
        
        super.setVoChanged(true);
        this._has_controlBalanceDetailPK = true;
    } //-- void setControlBalanceDetailPK(long) 

    /**
     * Method setControlBalanceFKSets the value of field
     * 'controlBalanceFK'.
     * 
     * @param controlBalanceFK the value of field 'controlBalanceFK'
     */
    public void setControlBalanceFK(long controlBalanceFK)
    {
        this._controlBalanceFK = controlBalanceFK;
        
        super.setVoChanged(true);
        this._has_controlBalanceFK = true;
    } //-- void setControlBalanceFK(long) 

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
     * Method setMAndEPayableReceivablesSets the value of field
     * 'MAndEPayableReceivables'.
     * 
     * @param MAndEPayableReceivables the value of field
     * 'MAndEPayableReceivables'.
     */
    public void setMAndEPayableReceivables(java.math.BigDecimal MAndEPayableReceivables)
    {
        this._MAndEPayableReceivables = MAndEPayableReceivables;
        
        super.setVoChanged(true);
    } //-- void setMAndEPayableReceivables(java.math.BigDecimal) 

    /**
     * Method setMgtFeePayablesReceivablesSets the value of field
     * 'mgtFeePayablesReceivables'.
     * 
     * @param mgtFeePayablesReceivables the value of field
     * 'mgtFeePayablesReceivables'.
     */
    public void setMgtFeePayablesReceivables(java.math.BigDecimal mgtFeePayablesReceivables)
    {
        this._mgtFeePayablesReceivables = mgtFeePayablesReceivables;
        
        super.setVoChanged(true);
    } //-- void setMgtFeePayablesReceivables(java.math.BigDecimal) 

    /**
     * Method setNetAssetsSets the value of field 'netAssets'.
     * 
     * @param netAssets the value of field 'netAssets'.
     */
    public void setNetAssets(java.math.BigDecimal netAssets)
    {
        this._netAssets = netAssets;
        
        super.setVoChanged(true);
    } //-- void setNetAssets(java.math.BigDecimal) 

    /**
     * Method setNetCashSets the value of field 'netCash'.
     * 
     * @param netCash the value of field 'netCash'.
     */
    public void setNetCash(java.math.BigDecimal netCash)
    {
        this._netCash = netCash;
        
        super.setVoChanged(true);
    } //-- void setNetCash(java.math.BigDecimal) 

    /**
     * Method setPolicyActivitySets the value of field
     * 'policyActivity'.
     * 
     * @param policyActivity the value of field 'policyActivity'.
     */
    public void setPolicyActivity(java.math.BigDecimal policyActivity)
    {
        this._policyActivity = policyActivity;
        
        super.setVoChanged(true);
    } //-- void setPolicyActivity(java.math.BigDecimal) 

    /**
     * Method setPolicyOwnerValueSets the value of field
     * 'policyOwnerValue'.
     * 
     * @param policyOwnerValue the value of field 'policyOwnerValue'
     */
    public void setPolicyOwnerValue(java.math.BigDecimal policyOwnerValue)
    {
        this._policyOwnerValue = policyOwnerValue;
        
        super.setVoChanged(true);
    } //-- void setPolicyOwnerValue(java.math.BigDecimal) 

    /**
     * Method setRVPPayablesReceivablesSets the value of field
     * 'RVPPayablesReceivables'.
     * 
     * @param RVPPayablesReceivables the value of field
     * 'RVPPayablesReceivables'.
     */
    public void setRVPPayablesReceivables(java.math.BigDecimal RVPPayablesReceivables)
    {
        this._RVPPayablesReceivables = RVPPayablesReceivables;
        
        super.setVoChanged(true);
    } //-- void setRVPPayablesReceivables(java.math.BigDecimal) 

    /**
     * Method setShareBalanceSets the value of field
     * 'shareBalance'.
     * 
     * @param shareBalance the value of field 'shareBalance'.
     */
    public void setShareBalance(java.math.BigDecimal shareBalance)
    {
        this._shareBalance = shareBalance;
        
        super.setVoChanged(true);
    } //-- void setShareBalance(java.math.BigDecimal) 

    /**
     * Method setSharesPurchasedSets the value of field
     * 'sharesPurchased'.
     * 
     * @param sharesPurchased the value of field 'sharesPurchased'.
     */
    public void setSharesPurchased(java.math.BigDecimal sharesPurchased)
    {
        this._sharesPurchased = sharesPurchased;
        
        super.setVoChanged(true);
    } //-- void setSharesPurchased(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedAdminFeesSets the value of field
     * 'totalAccruedAdminFees'.
     * 
     * @param totalAccruedAdminFees the value of field
     * 'totalAccruedAdminFees'.
     */
    public void setTotalAccruedAdminFees(java.math.BigDecimal totalAccruedAdminFees)
    {
        this._totalAccruedAdminFees = totalAccruedAdminFees;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedAdminFees(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedAdvanceTransfersSets the value of
     * field 'totalAccruedAdvanceTransfers'.
     * 
     * @param totalAccruedAdvanceTransfers the value of field
     * 'totalAccruedAdvanceTransfers'.
     */
    public void setTotalAccruedAdvanceTransfers(java.math.BigDecimal totalAccruedAdvanceTransfers)
    {
        this._totalAccruedAdvanceTransfers = totalAccruedAdvanceTransfers;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedAdvanceTransfers(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedAdvisoryFeesSets the value of field
     * 'totalAccruedAdvisoryFees'.
     * 
     * @param totalAccruedAdvisoryFees the value of field
     * 'totalAccruedAdvisoryFees'.
     */
    public void setTotalAccruedAdvisoryFees(java.math.BigDecimal totalAccruedAdvisoryFees)
    {
        this._totalAccruedAdvisoryFees = totalAccruedAdvisoryFees;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedAdvisoryFees(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedCOISets the value of field
     * 'totalAccruedCOI'.
     * 
     * @param totalAccruedCOI the value of field 'totalAccruedCOI'.
     */
    public void setTotalAccruedCOI(java.math.BigDecimal totalAccruedCOI)
    {
        this._totalAccruedCOI = totalAccruedCOI;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedCOI(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedContribToMortRsvSets the value of
     * field 'totalAccruedContribToMortRsv'.
     * 
     * @param totalAccruedContribToMortRsv the value of field
     * 'totalAccruedContribToMortRsv'.
     */
    public void setTotalAccruedContribToMortRsv(java.math.BigDecimal totalAccruedContribToMortRsv)
    {
        this._totalAccruedContribToMortRsv = totalAccruedContribToMortRsv;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedContribToMortRsv(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedMAndESets the value of field
     * 'totalAccruedMAndE'.
     * 
     * @param totalAccruedMAndE the value of field
     * 'totalAccruedMAndE'.
     */
    public void setTotalAccruedMAndE(java.math.BigDecimal totalAccruedMAndE)
    {
        this._totalAccruedMAndE = totalAccruedMAndE;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedMAndE(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedMgtFeesSets the value of field
     * 'totalAccruedMgtFees'.
     * 
     * @param totalAccruedMgtFees the value of field
     * 'totalAccruedMgtFees'.
     */
    public void setTotalAccruedMgtFees(java.math.BigDecimal totalAccruedMgtFees)
    {
        this._totalAccruedMgtFees = totalAccruedMgtFees;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedMgtFees(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedNetPremiumsSets the value of field
     * 'totalAccruedNetPremiums'.
     * 
     * @param totalAccruedNetPremiums the value of field
     * 'totalAccruedNetPremiums'.
     */
    public void setTotalAccruedNetPremiums(java.math.BigDecimal totalAccruedNetPremiums)
    {
        this._totalAccruedNetPremiums = totalAccruedNetPremiums;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedNetPremiums(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedRRDSets the value of field
     * 'totalAccruedRRD'.
     * 
     * @param totalAccruedRRD the value of field 'totalAccruedRRD'.
     */
    public void setTotalAccruedRRD(java.math.BigDecimal totalAccruedRRD)
    {
        this._totalAccruedRRD = totalAccruedRRD;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedRRD(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedRVPFeesSets the value of field
     * 'totalAccruedRVPFees'.
     * 
     * @param totalAccruedRVPFees the value of field
     * 'totalAccruedRVPFees'.
     */
    public void setTotalAccruedRVPFees(java.math.BigDecimal totalAccruedRVPFees)
    {
        this._totalAccruedRVPFees = totalAccruedRVPFees;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedRVPFees(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedReallocationsSets the value of field
     * 'totalAccruedReallocations'.
     * 
     * @param totalAccruedReallocations the value of field
     * 'totalAccruedReallocations'.
     */
    public void setTotalAccruedReallocations(java.math.BigDecimal totalAccruedReallocations)
    {
        this._totalAccruedReallocations = totalAccruedReallocations;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedReallocations(java.math.BigDecimal) 

    /**
     * Method setTotalAccruedSurrendersSets the value of field
     * 'totalAccruedSurrenders'.
     * 
     * @param totalAccruedSurrenders the value of field
     * 'totalAccruedSurrenders'.
     */
    public void setTotalAccruedSurrenders(java.math.BigDecimal totalAccruedSurrenders)
    {
        this._totalAccruedSurrenders = totalAccruedSurrenders;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedSurrenders(java.math.BigDecimal) 

    /**
     * Method setTotalCashAdminFeesSets the value of field
     * 'totalCashAdminFees'.
     * 
     * @param totalCashAdminFees the value of field
     * 'totalCashAdminFees'.
     */
    public void setTotalCashAdminFees(java.math.BigDecimal totalCashAdminFees)
    {
        this._totalCashAdminFees = totalCashAdminFees;
        
        super.setVoChanged(true);
    } //-- void setTotalCashAdminFees(java.math.BigDecimal) 

    /**
     * Method setTotalCashAdvanceTransfersSets the value of field
     * 'totalCashAdvanceTransfers'.
     * 
     * @param totalCashAdvanceTransfers the value of field
     * 'totalCashAdvanceTransfers'.
     */
    public void setTotalCashAdvanceTransfers(java.math.BigDecimal totalCashAdvanceTransfers)
    {
        this._totalCashAdvanceTransfers = totalCashAdvanceTransfers;
        
        super.setVoChanged(true);
    } //-- void setTotalCashAdvanceTransfers(java.math.BigDecimal) 

    /**
     * Method setTotalCashAdvisoryFeesSets the value of field
     * 'totalCashAdvisoryFees'.
     * 
     * @param totalCashAdvisoryFees the value of field
     * 'totalCashAdvisoryFees'.
     */
    public void setTotalCashAdvisoryFees(java.math.BigDecimal totalCashAdvisoryFees)
    {
        this._totalCashAdvisoryFees = totalCashAdvisoryFees;
        
        super.setVoChanged(true);
    } //-- void setTotalCashAdvisoryFees(java.math.BigDecimal) 

    /**
     * Method setTotalCashCoiSets the value of field
     * 'totalCashCoi'.
     * 
     * @param totalCashCoi the value of field 'totalCashCoi'.
     */
    public void setTotalCashCoi(java.math.BigDecimal totalCashCoi)
    {
        this._totalCashCoi = totalCashCoi;
        
        super.setVoChanged(true);
    } //-- void setTotalCashCoi(java.math.BigDecimal) 

    /**
     * Method setTotalCashContribToMortRsvSets the value of field
     * 'totalCashContribToMortRsv'.
     * 
     * @param totalCashContribToMortRsv the value of field
     * 'totalCashContribToMortRsv'.
     */
    public void setTotalCashContribToMortRsv(java.math.BigDecimal totalCashContribToMortRsv)
    {
        this._totalCashContribToMortRsv = totalCashContribToMortRsv;
        
        super.setVoChanged(true);
    } //-- void setTotalCashContribToMortRsv(java.math.BigDecimal) 

    /**
     * Method setTotalCashMAndESets the value of field
     * 'totalCashMAndE'.
     * 
     * @param totalCashMAndE the value of field 'totalCashMAndE'.
     */
    public void setTotalCashMAndE(java.math.BigDecimal totalCashMAndE)
    {
        this._totalCashMAndE = totalCashMAndE;
        
        super.setVoChanged(true);
    } //-- void setTotalCashMAndE(java.math.BigDecimal) 

    /**
     * Method setTotalCashMgmtFeesSets the value of field
     * 'totalCashMgmtFees'.
     * 
     * @param totalCashMgmtFees the value of field
     * 'totalCashMgmtFees'.
     */
    public void setTotalCashMgmtFees(java.math.BigDecimal totalCashMgmtFees)
    {
        this._totalCashMgmtFees = totalCashMgmtFees;
        
        super.setVoChanged(true);
    } //-- void setTotalCashMgmtFees(java.math.BigDecimal) 

    /**
     * Method setTotalCashNetPremiumSets the value of field
     * 'totalCashNetPremium'.
     * 
     * @param totalCashNetPremium the value of field
     * 'totalCashNetPremium'.
     */
    public void setTotalCashNetPremium(java.math.BigDecimal totalCashNetPremium)
    {
        this._totalCashNetPremium = totalCashNetPremium;
        
        super.setVoChanged(true);
    } //-- void setTotalCashNetPremium(java.math.BigDecimal) 

    /**
     * Method setTotalCashRRDSets the value of field
     * 'totalCashRRD'.
     * 
     * @param totalCashRRD the value of field 'totalCashRRD'.
     */
    public void setTotalCashRRD(java.math.BigDecimal totalCashRRD)
    {
        this._totalCashRRD = totalCashRRD;
        
        super.setVoChanged(true);
    } //-- void setTotalCashRRD(java.math.BigDecimal) 

    /**
     * Method setTotalCashReallocationsSets the value of field
     * 'totalCashReallocations'.
     * 
     * @param totalCashReallocations the value of field
     * 'totalCashReallocations'.
     */
    public void setTotalCashReallocations(java.math.BigDecimal totalCashReallocations)
    {
        this._totalCashReallocations = totalCashReallocations;
        
        super.setVoChanged(true);
    } //-- void setTotalCashReallocations(java.math.BigDecimal) 

    /**
     * Method setTotalCashSVAFeesSets the value of field
     * 'totalCashSVAFees'.
     * 
     * @param totalCashSVAFees the value of field 'totalCashSVAFees'
     */
    public void setTotalCashSVAFees(java.math.BigDecimal totalCashSVAFees)
    {
        this._totalCashSVAFees = totalCashSVAFees;
        
        super.setVoChanged(true);
    } //-- void setTotalCashSVAFees(java.math.BigDecimal) 

    /**
     * Method setTotalCashSurrendersSets the value of field
     * 'totalCashSurrenders'.
     * 
     * @param totalCashSurrenders the value of field
     * 'totalCashSurrenders'.
     */
    public void setTotalCashSurrenders(java.math.BigDecimal totalCashSurrenders)
    {
        this._totalCashSurrenders = totalCashSurrenders;
        
        super.setVoChanged(true);
    } //-- void setTotalCashSurrenders(java.math.BigDecimal) 

    /**
     * Method setUnitBalanceSets the value of field 'unitBalance'.
     * 
     * @param unitBalance the value of field 'unitBalance'.
     */
    public void setUnitBalance(java.math.BigDecimal unitBalance)
    {
        this._unitBalance = unitBalance;
        
        super.setVoChanged(true);
    } //-- void setUnitBalance(java.math.BigDecimal) 

    /**
     * Method setUnitsPurchasedSets the value of field
     * 'unitsPurchased'.
     * 
     * @param unitsPurchased the value of field 'unitsPurchased'.
     */
    public void setUnitsPurchased(java.math.BigDecimal unitsPurchased)
    {
        this._unitsPurchased = unitsPurchased;
        
        super.setVoChanged(true);
    } //-- void setUnitsPurchased(java.math.BigDecimal) 

    /**
     * Method setValuationDateSets the value of field
     * 'valuationDate'.
     * 
     * @param valuationDate the value of field 'valuationDate'.
     */
    public void setValuationDate(java.lang.String valuationDate)
    {
        this._valuationDate = valuationDate;
        
        super.setVoChanged(true);
    } //-- void setValuationDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ControlBalanceDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ControlBalanceDetailVO) Unmarshaller.unmarshal(edit.common.vo.ControlBalanceDetailVO.class, reader);
    } //-- edit.common.vo.ControlBalanceDetailVO unmarshal(java.io.Reader) 

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
