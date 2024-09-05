/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo.user;

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
 * Class ControlsAndBalancesTotalsVO.
 * 
 * @version $Revision$ $Date$
 */
public class ControlsAndBalancesTotalsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _businessContract
     */
    private java.lang.String _businessContract;

    /**
     * Field _trxTypeDesc
     */
    private java.lang.String _trxTypeDesc;

    /**
     * Field _fundNumber
     */
    private java.lang.String _fundNumber;

    /**
     * Field _beginningDollarBalance
     */
    private java.math.BigDecimal _beginningDollarBalance;

    /**
     * Field _beginningUnitBalance
     */
    private java.math.BigDecimal _beginningUnitBalance;

    /**
     * Field _beginningBalanceCycleDate
     */
    private java.lang.String _beginningBalanceCycleDate;

    /**
     * Field _fundName
     */
    private java.lang.String _fundName;

    /**
     * Field _totalDollars
     */
    private java.math.BigDecimal _totalDollars;

    /**
     * Field _totalUnits
     */
    private java.math.BigDecimal _totalUnits;

    /**
     * Field _totalAccruedContribToMortRSV
     */
    private java.math.BigDecimal _totalAccruedContribToMortRSV;

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
     * Field _totalCashRVPFees
     */
    private java.math.BigDecimal _totalCashRVPFees;


      //----------------/
     //- Constructors -/
    //----------------/

    public ControlsAndBalancesTotalsVO() {
        super();
    } //-- edit.common.vo.user.ControlsAndBalancesTotalsVO()


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
        
        if (obj instanceof ControlsAndBalancesTotalsVO) {
        
            ControlsAndBalancesTotalsVO temp = (ControlsAndBalancesTotalsVO)obj;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._businessContract != null) {
                if (temp._businessContract == null) return false;
                else if (!(this._businessContract.equals(temp._businessContract))) 
                    return false;
            }
            else if (temp._businessContract != null)
                return false;
            if (this._trxTypeDesc != null) {
                if (temp._trxTypeDesc == null) return false;
                else if (!(this._trxTypeDesc.equals(temp._trxTypeDesc))) 
                    return false;
            }
            else if (temp._trxTypeDesc != null)
                return false;
            if (this._fundNumber != null) {
                if (temp._fundNumber == null) return false;
                else if (!(this._fundNumber.equals(temp._fundNumber))) 
                    return false;
            }
            else if (temp._fundNumber != null)
                return false;
            if (this._beginningDollarBalance != null) {
                if (temp._beginningDollarBalance == null) return false;
                else if (!(this._beginningDollarBalance.equals(temp._beginningDollarBalance))) 
                    return false;
            }
            else if (temp._beginningDollarBalance != null)
                return false;
            if (this._beginningUnitBalance != null) {
                if (temp._beginningUnitBalance == null) return false;
                else if (!(this._beginningUnitBalance.equals(temp._beginningUnitBalance))) 
                    return false;
            }
            else if (temp._beginningUnitBalance != null)
                return false;
            if (this._beginningBalanceCycleDate != null) {
                if (temp._beginningBalanceCycleDate == null) return false;
                else if (!(this._beginningBalanceCycleDate.equals(temp._beginningBalanceCycleDate))) 
                    return false;
            }
            else if (temp._beginningBalanceCycleDate != null)
                return false;
            if (this._fundName != null) {
                if (temp._fundName == null) return false;
                else if (!(this._fundName.equals(temp._fundName))) 
                    return false;
            }
            else if (temp._fundName != null)
                return false;
            if (this._totalDollars != null) {
                if (temp._totalDollars == null) return false;
                else if (!(this._totalDollars.equals(temp._totalDollars))) 
                    return false;
            }
            else if (temp._totalDollars != null)
                return false;
            if (this._totalUnits != null) {
                if (temp._totalUnits == null) return false;
                else if (!(this._totalUnits.equals(temp._totalUnits))) 
                    return false;
            }
            else if (temp._totalUnits != null)
                return false;
            if (this._totalAccruedContribToMortRSV != null) {
                if (temp._totalAccruedContribToMortRSV == null) return false;
                else if (!(this._totalAccruedContribToMortRSV.equals(temp._totalAccruedContribToMortRSV))) 
                    return false;
            }
            else if (temp._totalAccruedContribToMortRSV != null)
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
            if (this._totalCashRVPFees != null) {
                if (temp._totalCashRVPFees == null) return false;
                else if (!(this._totalCashRVPFees.equals(temp._totalCashRVPFees))) 
                    return false;
            }
            else if (temp._totalCashRVPFees != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBeginningBalanceCycleDateReturns the value of
     * field 'beginningBalanceCycleDate'.
     * 
     * @return the value of field 'beginningBalanceCycleDate'.
     */
    public java.lang.String getBeginningBalanceCycleDate()
    {
        return this._beginningBalanceCycleDate;
    } //-- java.lang.String getBeginningBalanceCycleDate() 

    /**
     * Method getBeginningDollarBalanceReturns the value of field
     * 'beginningDollarBalance'.
     * 
     * @return the value of field 'beginningDollarBalance'.
     */
    public java.math.BigDecimal getBeginningDollarBalance()
    {
        return this._beginningDollarBalance;
    } //-- java.math.BigDecimal getBeginningDollarBalance() 

    /**
     * Method getBeginningUnitBalanceReturns the value of field
     * 'beginningUnitBalance'.
     * 
     * @return the value of field 'beginningUnitBalance'.
     */
    public java.math.BigDecimal getBeginningUnitBalance()
    {
        return this._beginningUnitBalance;
    } //-- java.math.BigDecimal getBeginningUnitBalance() 

    /**
     * Method getBusinessContractReturns the value of field
     * 'businessContract'.
     * 
     * @return the value of field 'businessContract'.
     */
    public java.lang.String getBusinessContract()
    {
        return this._businessContract;
    } //-- java.lang.String getBusinessContract() 

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
     * Method getCompanyNameReturns the value of field
     * 'companyName'.
     * 
     * @return the value of field 'companyName'.
     */
    public java.lang.String getCompanyName()
    {
        return this._companyName;
    } //-- java.lang.String getCompanyName() 

    /**
     * Method getFundNameReturns the value of field 'fundName'.
     * 
     * @return the value of field 'fundName'.
     */
    public java.lang.String getFundName()
    {
        return this._fundName;
    } //-- java.lang.String getFundName() 

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
     * Method getTotalAccruedContribToMortRSVReturns the value of
     * field 'totalAccruedContribToMortRSV'.
     * 
     * @return the value of field 'totalAccruedContribToMortRSV'.
     */
    public java.math.BigDecimal getTotalAccruedContribToMortRSV()
    {
        return this._totalAccruedContribToMortRSV;
    } //-- java.math.BigDecimal getTotalAccruedContribToMortRSV() 

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
     * Method getTotalCashRVPFeesReturns the value of field
     * 'totalCashRVPFees'.
     * 
     * @return the value of field 'totalCashRVPFees'.
     */
    public java.math.BigDecimal getTotalCashRVPFees()
    {
        return this._totalCashRVPFees;
    } //-- java.math.BigDecimal getTotalCashRVPFees() 

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
     * Method getTotalDollarsReturns the value of field
     * 'totalDollars'.
     * 
     * @return the value of field 'totalDollars'.
     */
    public java.math.BigDecimal getTotalDollars()
    {
        return this._totalDollars;
    } //-- java.math.BigDecimal getTotalDollars() 

    /**
     * Method getTotalUnitsReturns the value of field 'totalUnits'.
     * 
     * @return the value of field 'totalUnits'.
     */
    public java.math.BigDecimal getTotalUnits()
    {
        return this._totalUnits;
    } //-- java.math.BigDecimal getTotalUnits() 

    /**
     * Method getTrxTypeDescReturns the value of field
     * 'trxTypeDesc'.
     * 
     * @return the value of field 'trxTypeDesc'.
     */
    public java.lang.String getTrxTypeDesc()
    {
        return this._trxTypeDesc;
    } //-- java.lang.String getTrxTypeDesc() 

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
     * Method setBeginningBalanceCycleDateSets the value of field
     * 'beginningBalanceCycleDate'.
     * 
     * @param beginningBalanceCycleDate the value of field
     * 'beginningBalanceCycleDate'.
     */
    public void setBeginningBalanceCycleDate(java.lang.String beginningBalanceCycleDate)
    {
        this._beginningBalanceCycleDate = beginningBalanceCycleDate;
        
        super.setVoChanged(true);
    } //-- void setBeginningBalanceCycleDate(java.lang.String) 

    /**
     * Method setBeginningDollarBalanceSets the value of field
     * 'beginningDollarBalance'.
     * 
     * @param beginningDollarBalance the value of field
     * 'beginningDollarBalance'.
     */
    public void setBeginningDollarBalance(java.math.BigDecimal beginningDollarBalance)
    {
        this._beginningDollarBalance = beginningDollarBalance;
        
        super.setVoChanged(true);
    } //-- void setBeginningDollarBalance(java.math.BigDecimal) 

    /**
     * Method setBeginningUnitBalanceSets the value of field
     * 'beginningUnitBalance'.
     * 
     * @param beginningUnitBalance the value of field
     * 'beginningUnitBalance'.
     */
    public void setBeginningUnitBalance(java.math.BigDecimal beginningUnitBalance)
    {
        this._beginningUnitBalance = beginningUnitBalance;
        
        super.setVoChanged(true);
    } //-- void setBeginningUnitBalance(java.math.BigDecimal) 

    /**
     * Method setBusinessContractSets the value of field
     * 'businessContract'.
     * 
     * @param businessContract the value of field 'businessContract'
     */
    public void setBusinessContract(java.lang.String businessContract)
    {
        this._businessContract = businessContract;
        
        super.setVoChanged(true);
    } //-- void setBusinessContract(java.lang.String) 

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
     * Method setCompanyNameSets the value of field 'companyName'.
     * 
     * @param companyName the value of field 'companyName'.
     */
    public void setCompanyName(java.lang.String companyName)
    {
        this._companyName = companyName;
        
        super.setVoChanged(true);
    } //-- void setCompanyName(java.lang.String) 

    /**
     * Method setFundNameSets the value of field 'fundName'.
     * 
     * @param fundName the value of field 'fundName'.
     */
    public void setFundName(java.lang.String fundName)
    {
        this._fundName = fundName;
        
        super.setVoChanged(true);
    } //-- void setFundName(java.lang.String) 

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
     * Method setTotalAccruedContribToMortRSVSets the value of
     * field 'totalAccruedContribToMortRSV'.
     * 
     * @param totalAccruedContribToMortRSV the value of field
     * 'totalAccruedContribToMortRSV'.
     */
    public void setTotalAccruedContribToMortRSV(java.math.BigDecimal totalAccruedContribToMortRSV)
    {
        this._totalAccruedContribToMortRSV = totalAccruedContribToMortRSV;
        
        super.setVoChanged(true);
    } //-- void setTotalAccruedContribToMortRSV(java.math.BigDecimal) 

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
     * Method setTotalCashRVPFeesSets the value of field
     * 'totalCashRVPFees'.
     * 
     * @param totalCashRVPFees the value of field 'totalCashRVPFees'
     */
    public void setTotalCashRVPFees(java.math.BigDecimal totalCashRVPFees)
    {
        this._totalCashRVPFees = totalCashRVPFees;
        
        super.setVoChanged(true);
    } //-- void setTotalCashRVPFees(java.math.BigDecimal) 

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
     * Method setTotalDollarsSets the value of field
     * 'totalDollars'.
     * 
     * @param totalDollars the value of field 'totalDollars'.
     */
    public void setTotalDollars(java.math.BigDecimal totalDollars)
    {
        this._totalDollars = totalDollars;
        
        super.setVoChanged(true);
    } //-- void setTotalDollars(java.math.BigDecimal) 

    /**
     * Method setTotalUnitsSets the value of field 'totalUnits'.
     * 
     * @param totalUnits the value of field 'totalUnits'.
     */
    public void setTotalUnits(java.math.BigDecimal totalUnits)
    {
        this._totalUnits = totalUnits;
        
        super.setVoChanged(true);
    } //-- void setTotalUnits(java.math.BigDecimal) 

    /**
     * Method setTrxTypeDescSets the value of field 'trxTypeDesc'.
     * 
     * @param trxTypeDesc the value of field 'trxTypeDesc'.
     */
    public void setTrxTypeDesc(java.lang.String trxTypeDesc)
    {
        this._trxTypeDesc = trxTypeDesc;
        
        super.setVoChanged(true);
    } //-- void setTrxTypeDesc(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.user.ControlsAndBalancesTotalsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.user.ControlsAndBalancesTotalsVO) Unmarshaller.unmarshal(edit.common.vo.user.ControlsAndBalancesTotalsVO.class, reader);
    } //-- edit.common.vo.user.ControlsAndBalancesTotalsVO unmarshal(java.io.Reader) 

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
