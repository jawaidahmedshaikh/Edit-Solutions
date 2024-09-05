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
 * Class EDITTrxVO.
 * 
 * @version $Revision$ $Date$
 */
public class EDITTrxVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _EDITTrxPK
     */
    private long _EDITTrxPK;

    /**
     * keeps track of state for field: _EDITTrxPK
     */
    private boolean _has_EDITTrxPK;

    /**
     * Field _clientSetupFK
     */
    private long _clientSetupFK;

    /**
     * Field _selectedRiderPK
     */
    private Long _selectedRiderPK;

    /**
     * Field _terminationTrxFK
     */
    private long _terminationTrxFK;
    
    /**
     * keeps track of state for field: _clientSetupFK
     */
    private boolean _has_clientSetupFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _pendingStatus
     */
    private java.lang.String _pendingStatus;

    /**
     * Field _sequenceNumber
     */
    private int _sequenceNumber;

    /**
     * keeps track of state for field: _sequenceNumber
     */
    private boolean _has_sequenceNumber;

    /**
     * Field _taxYear
     */
    private int _taxYear;

    /**
     * keeps track of state for field: _taxYear
     */
    private boolean _has_taxYear;

    /**
     * Field _trxAmount
     */
    private java.math.BigDecimal _trxAmount;

    /**
     * Field _originalAmount
     */
    private java.math.BigDecimal _originalAmount;

    /**
     * Field _dueDate
     */
    private java.lang.String _dueDate;

    /**
     * Field _transactionTypeCT
     */
    private java.lang.String _transactionTypeCT;

    /**
     * Field _trxIsRescheduledInd
     */
    private java.lang.String _trxIsRescheduledInd;

    /**
     * Field _reapplyEDITTrxFK
     */
    private long _reapplyEDITTrxFK;

    /**
     * keeps track of state for field: _reapplyEDITTrxFK
     */
    private boolean _has_reapplyEDITTrxFK;

    /**
     * Field _commissionStatus
     */
    private java.lang.String _commissionStatus;

    /**
     * Field _lookBackInd
     */
    private java.lang.String _lookBackInd;

    /**
     * Field _originatingTrxFK
     */
    private long _originatingTrxFK;

    /**
     * keeps track of state for field: _originatingTrxFK
     */
    private boolean _has_originatingTrxFK;

    /**
     * Field _noCorrespondenceInd
     */
    private java.lang.String _noCorrespondenceInd;

    /**
     * Field _noAccountingInd
     */
    private java.lang.String _noAccountingInd;

    /**
     * Field _noCommissionInd
     */
    private java.lang.String _noCommissionInd;

    /**
     * Field _zeroLoadInd
     */
    private java.lang.String _zeroLoadInd;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _notificationAmount
     */
    private java.math.BigDecimal _notificationAmount;

    /**
     * Field _notificationAmountReceived
     */
    private java.math.BigDecimal _notificationAmountReceived;

    /**
     * Field _bonusCommissionAmount
     */
    private java.math.BigDecimal _bonusCommissionAmount;

    /**
     * Field _excessBonusCommissionAmount
     */
    private java.math.BigDecimal _excessBonusCommissionAmount;

    /**
     * Field _transferTypeCT
     */
    private java.lang.String _transferTypeCT;

    /**
     * Field _advanceNotificationOverride
     */
    private java.lang.String _advanceNotificationOverride;

    /**
     * Field _accountingPeriod
     */
    private java.lang.String _accountingPeriod;

    /**
     * Field _reinsuranceStatus
     */
    private java.lang.String _reinsuranceStatus;

    /**
     * Field _dateContributionExcess
     */
    private java.lang.String _dateContributionExcess;

    /**
     * Field _transferUnitsType
     */
    private java.lang.String _transferUnitsType;

    /**
     * Field _noCheckEFT
     */
    private java.lang.String _noCheckEFT;

    /**
     * Field _newPolicyNumber
     */
    private java.lang.String _newPolicyNumber;

    /**
     * Field _interestProceedsOverride
     */
    private java.math.BigDecimal _interestProceedsOverride;

    /**
     * Field _reversalReasonCodeCT
     */
    private java.lang.String _reversalReasonCodeCT;

    /**
     * Field _checkAdjustmentFK
     */
    private long _checkAdjustmentFK;

    /**
     * keeps track of state for field: _checkAdjustmentFK
     */
    private boolean _has_checkAdjustmentFK;

    /**
     * Field _trxPercent
     */
    private java.math.BigDecimal _trxPercent;

    /**
     * Field _originalAccountingPeriod
     */
    private java.lang.String _originalAccountingPeriod;

    /**
     * Field _authorizedSignatureCT
     */
    private java.lang.String _authorizedSignatureCT;

    /**
     * Field _notTakenOverrideInd
     */
    private java.lang.String _notTakenOverrideInd;

    /**
     * Field _forceoutMinBalInd
     */
    private java.lang.String _forceoutMinBalInd;

    /**
     * Field _premiumDueCreatedIndicator
     */
    private java.lang.String _premiumDueCreatedIndicator;

    /**
     * Field _billAmtEditOverrideInd
     */
    private java.lang.String _billAmtEditOverrideInd;

    /**
     * Field _zeroInterestIndicator
     */
    private java.lang.String _zeroInterestIndicator;

    /**
     * Field _EDITTrxCorrespondenceVOList
     */
    private java.util.Vector _EDITTrxCorrespondenceVOList;

    /**
     * Field _EDITTrxHistoryVOList
     */
    private java.util.Vector _EDITTrxHistoryVOList;

    /**
     * Field _overdueChargeVOList
     */
    private java.util.Vector _overdueChargeVOList;

    /**
     * Field _overdueChargeSettledVOList
     */
    private java.util.Vector _overdueChargeSettledVOList;

    /**
     * Field _premiumDueVOList
     */
    private java.util.Vector _premiumDueVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITTrxVO() {
        super();
        _EDITTrxCorrespondenceVOList = new Vector();
        _EDITTrxHistoryVOList = new Vector();
        _overdueChargeVOList = new Vector();
        _overdueChargeSettledVOList = new Vector();
        _premiumDueVOList = new Vector();
    } //-- edit.common.vo.EDITTrxVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEDITTrxCorrespondenceVO
     * 
     * @param vEDITTrxCorrespondenceVO
     */
    public void addEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO vEDITTrxCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxCorrespondenceVO.setParentVO(this.getClass(), this);
        _EDITTrxCorrespondenceVOList.addElement(vEDITTrxCorrespondenceVO);
    } //-- void addEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method addEDITTrxCorrespondenceVO
     * 
     * @param index
     * @param vEDITTrxCorrespondenceVO
     */
    public void addEDITTrxCorrespondenceVO(int index, edit.common.vo.EDITTrxCorrespondenceVO vEDITTrxCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxCorrespondenceVO.setParentVO(this.getClass(), this);
        _EDITTrxCorrespondenceVOList.insertElementAt(vEDITTrxCorrespondenceVO, index);
    } //-- void addEDITTrxCorrespondenceVO(int, edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method addEDITTrxHistoryVO
     * 
     * @param vEDITTrxHistoryVO
     */
    public void addEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO vEDITTrxHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxHistoryVO.setParentVO(this.getClass(), this);
        _EDITTrxHistoryVOList.addElement(vEDITTrxHistoryVO);
    } //-- void addEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method addEDITTrxHistoryVO
     * 
     * @param index
     * @param vEDITTrxHistoryVO
     */
    public void addEDITTrxHistoryVO(int index, edit.common.vo.EDITTrxHistoryVO vEDITTrxHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxHistoryVO.setParentVO(this.getClass(), this);
        _EDITTrxHistoryVOList.insertElementAt(vEDITTrxHistoryVO, index);
    } //-- void addEDITTrxHistoryVO(int, edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method addOverdueChargeSettledVO
     * 
     * @param vOverdueChargeSettledVO
     */
    public void addOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO vOverdueChargeSettledVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeSettledVO.setParentVO(this.getClass(), this);
        _overdueChargeSettledVOList.addElement(vOverdueChargeSettledVO);
    } //-- void addOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method addOverdueChargeSettledVO
     * 
     * @param index
     * @param vOverdueChargeSettledVO
     */
    public void addOverdueChargeSettledVO(int index, edit.common.vo.OverdueChargeSettledVO vOverdueChargeSettledVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeSettledVO.setParentVO(this.getClass(), this);
        _overdueChargeSettledVOList.insertElementAt(vOverdueChargeSettledVO, index);
    } //-- void addOverdueChargeSettledVO(int, edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method addOverdueChargeVO
     * 
     * @param vOverdueChargeVO
     */
    public void addOverdueChargeVO(edit.common.vo.OverdueChargeVO vOverdueChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeVO.setParentVO(this.getClass(), this);
        _overdueChargeVOList.addElement(vOverdueChargeVO);
    } //-- void addOverdueChargeVO(edit.common.vo.OverdueChargeVO) 

    /**
     * Method addOverdueChargeVO
     * 
     * @param index
     * @param vOverdueChargeVO
     */
    public void addOverdueChargeVO(int index, edit.common.vo.OverdueChargeVO vOverdueChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeVO.setParentVO(this.getClass(), this);
        _overdueChargeVOList.insertElementAt(vOverdueChargeVO, index);
    } //-- void addOverdueChargeVO(int, edit.common.vo.OverdueChargeVO) 

    /**
     * Method addPremiumDueVO
     * 
     * @param vPremiumDueVO
     */
    public void addPremiumDueVO(edit.common.vo.PremiumDueVO vPremiumDueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPremiumDueVO.setParentVO(this.getClass(), this);
        _premiumDueVOList.addElement(vPremiumDueVO);
    } //-- void addPremiumDueVO(edit.common.vo.PremiumDueVO) 

    /**
     * Method addPremiumDueVO
     * 
     * @param index
     * @param vPremiumDueVO
     */
    public void addPremiumDueVO(int index, edit.common.vo.PremiumDueVO vPremiumDueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPremiumDueVO.setParentVO(this.getClass(), this);
        _premiumDueVOList.insertElementAt(vPremiumDueVO, index);
    } //-- void addPremiumDueVO(int, edit.common.vo.PremiumDueVO) 

    /**
     * Method enumerateEDITTrxCorrespondenceVO
     */
    public java.util.Enumeration enumerateEDITTrxCorrespondenceVO()
    {
        return _EDITTrxCorrespondenceVOList.elements();
    } //-- java.util.Enumeration enumerateEDITTrxCorrespondenceVO() 

    /**
     * Method enumerateEDITTrxHistoryVO
     */
    public java.util.Enumeration enumerateEDITTrxHistoryVO()
    {
        return _EDITTrxHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateEDITTrxHistoryVO() 

    /**
     * Method enumerateOverdueChargeSettledVO
     */
    public java.util.Enumeration enumerateOverdueChargeSettledVO()
    {
        return _overdueChargeSettledVOList.elements();
    } //-- java.util.Enumeration enumerateOverdueChargeSettledVO() 

    /**
     * Method enumerateOverdueChargeVO
     */
    public java.util.Enumeration enumerateOverdueChargeVO()
    {
        return _overdueChargeVOList.elements();
    } //-- java.util.Enumeration enumerateOverdueChargeVO() 

    /**
     * Method enumeratePremiumDueVO
     */
    public java.util.Enumeration enumeratePremiumDueVO()
    {
        return _premiumDueVOList.elements();
    } //-- java.util.Enumeration enumeratePremiumDueVO() 

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
        
        if (obj instanceof EDITTrxVO) {
        
            EDITTrxVO temp = (EDITTrxVO)obj;
            if (this._EDITTrxPK != temp._EDITTrxPK)
                return false;
            if (this._has_EDITTrxPK != temp._has_EDITTrxPK)
                return false;
            if (this._clientSetupFK != temp._clientSetupFK)
                return false;
            if (this._has_clientSetupFK != temp._has_clientSetupFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._pendingStatus != null) {
                if (temp._pendingStatus == null) return false;
                else if (!(this._pendingStatus.equals(temp._pendingStatus))) 
                    return false;
            }
            else if (temp._pendingStatus != null)
                return false;
            if (this._sequenceNumber != temp._sequenceNumber)
                return false;
            if (this._has_sequenceNumber != temp._has_sequenceNumber)
                return false;
            if (this._taxYear != temp._taxYear)
                return false;
            if (this._has_taxYear != temp._has_taxYear)
                return false;
            if (this._trxAmount != null) {
                if (temp._trxAmount == null) return false;
                else if (!(this._trxAmount.equals(temp._trxAmount))) 
                    return false;
            }
            else if (temp._trxAmount != null)
                return false;
            if (this._dueDate != null) {
                if (temp._dueDate == null) return false;
                else if (!(this._dueDate.equals(temp._dueDate))) 
                    return false;
            }
            else if (temp._dueDate != null)
                return false;
            if (this._transactionTypeCT != null) {
                if (temp._transactionTypeCT == null) return false;
                else if (!(this._transactionTypeCT.equals(temp._transactionTypeCT))) 
                    return false;
            }
            else if (temp._transactionTypeCT != null)
                return false;
            if (this._trxIsRescheduledInd != null) {
                if (temp._trxIsRescheduledInd == null) return false;
                else if (!(this._trxIsRescheduledInd.equals(temp._trxIsRescheduledInd))) 
                    return false;
            }
            else if (temp._trxIsRescheduledInd != null)
                return false;
            if (this._reapplyEDITTrxFK != temp._reapplyEDITTrxFK)
                return false;
            if (this._has_reapplyEDITTrxFK != temp._has_reapplyEDITTrxFK)
                return false;
            if (this._commissionStatus != null) {
                if (temp._commissionStatus == null) return false;
                else if (!(this._commissionStatus.equals(temp._commissionStatus))) 
                    return false;
            }
            else if (temp._commissionStatus != null)
                return false;

            
            
            if (this._originalAmount != null) {
                if (temp._originalAmount == null) return false;
                else if (!(this._originalAmount.equals(temp._originalAmount))) 
                    return false;
            }
            else if (temp._originalAmount != null)
                return false;
            
            if (this._selectedRiderPK != null) {
                if (temp._selectedRiderPK == null) return false;
                else if (!(this._selectedRiderPK.equals(temp._selectedRiderPK))) 
                    return false;
            }
            else if (temp._selectedRiderPK != null)
                return false;
            
            
            
            
            
            if (this._lookBackInd != null) {
                if (temp._lookBackInd == null) return false;
                else if (!(this._lookBackInd.equals(temp._lookBackInd))) 
                    return false;
            }
            else if (temp._lookBackInd != null)
                return false;
            if (this._originatingTrxFK != temp._originatingTrxFK)
                return false;
            if (this._has_originatingTrxFK != temp._has_originatingTrxFK)
                return false;

            if (this._noCorrespondenceInd != null) {
                if (temp._noCorrespondenceInd == null) return false;
                else if (!(this._noCorrespondenceInd.equals(temp._noCorrespondenceInd))) 
                    return false;
            }
            else if (temp._noCorrespondenceInd != null)
                return false;
            if (this._noAccountingInd != null) {
                if (temp._noAccountingInd == null) return false;
                else if (!(this._noAccountingInd.equals(temp._noAccountingInd))) 
                    return false;
            }
            else if (temp._noAccountingInd != null)
                return false;
            if (this._noCommissionInd != null) {
                if (temp._noCommissionInd == null) return false;
                else if (!(this._noCommissionInd.equals(temp._noCommissionInd))) 
                    return false;
            }
            else if (temp._noCommissionInd != null)
                return false;
            if (this._zeroLoadInd != null) {
                if (temp._zeroLoadInd == null) return false;
                else if (!(this._zeroLoadInd.equals(temp._zeroLoadInd))) 
                    return false;
            }
            else if (temp._zeroLoadInd != null)
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
            if (this._notificationAmount != null) {
                if (temp._notificationAmount == null) return false;
                else if (!(this._notificationAmount.equals(temp._notificationAmount))) 
                    return false;
            }
            else if (temp._notificationAmount != null)
                return false;
            if (this._notificationAmountReceived != null) {
                if (temp._notificationAmountReceived == null) return false;
                else if (!(this._notificationAmountReceived.equals(temp._notificationAmountReceived))) 
                    return false;
            }
            else if (temp._notificationAmountReceived != null)
                return false;
            if (this._bonusCommissionAmount != null) {
                if (temp._bonusCommissionAmount == null) return false;
                else if (!(this._bonusCommissionAmount.equals(temp._bonusCommissionAmount))) 
                    return false;
            }
            else if (temp._bonusCommissionAmount != null)
                return false;
            if (this._excessBonusCommissionAmount != null) {
                if (temp._excessBonusCommissionAmount == null) return false;
                else if (!(this._excessBonusCommissionAmount.equals(temp._excessBonusCommissionAmount))) 
                    return false;
            }
            else if (temp._excessBonusCommissionAmount != null)
                return false;
            if (this._transferTypeCT != null) {
                if (temp._transferTypeCT == null) return false;
                else if (!(this._transferTypeCT.equals(temp._transferTypeCT))) 
                    return false;
            }
            else if (temp._transferTypeCT != null)
                return false;
            if (this._advanceNotificationOverride != null) {
                if (temp._advanceNotificationOverride == null) return false;
                else if (!(this._advanceNotificationOverride.equals(temp._advanceNotificationOverride))) 
                    return false;
            }
            else if (temp._advanceNotificationOverride != null)
                return false;
            if (this._accountingPeriod != null) {
                if (temp._accountingPeriod == null) return false;
                else if (!(this._accountingPeriod.equals(temp._accountingPeriod))) 
                    return false;
            }
            else if (temp._accountingPeriod != null)
                return false;
            if (this._reinsuranceStatus != null) {
                if (temp._reinsuranceStatus == null) return false;
                else if (!(this._reinsuranceStatus.equals(temp._reinsuranceStatus))) 
                    return false;
            }
            else if (temp._reinsuranceStatus != null)
                return false;
            if (this._dateContributionExcess != null) {
                if (temp._dateContributionExcess == null) return false;
                else if (!(this._dateContributionExcess.equals(temp._dateContributionExcess))) 
                    return false;
            }
            else if (temp._dateContributionExcess != null)
                return false;
            if (this._transferUnitsType != null) {
                if (temp._transferUnitsType == null) return false;
                else if (!(this._transferUnitsType.equals(temp._transferUnitsType))) 
                    return false;
            }
            else if (temp._transferUnitsType != null)
                return false;
            if (this._noCheckEFT != null) {
                if (temp._noCheckEFT == null) return false;
                else if (!(this._noCheckEFT.equals(temp._noCheckEFT))) 
                    return false;
            }
            else if (temp._noCheckEFT != null)
                return false;
            if (this._newPolicyNumber != null) {
                if (temp._newPolicyNumber == null) return false;
                else if (!(this._newPolicyNumber.equals(temp._newPolicyNumber))) 
                    return false;
            }
            else if (temp._newPolicyNumber != null)
                return false;
            if (this._interestProceedsOverride != null) {
                if (temp._interestProceedsOverride == null) return false;
                else if (!(this._interestProceedsOverride.equals(temp._interestProceedsOverride))) 
                    return false;
            }
            else if (temp._interestProceedsOverride != null)
                return false;
            if (this._reversalReasonCodeCT != null) {
                if (temp._reversalReasonCodeCT == null) return false;
                else if (!(this._reversalReasonCodeCT.equals(temp._reversalReasonCodeCT))) 
                    return false;
            }
            else if (temp._reversalReasonCodeCT != null)
                return false;
            if (this._checkAdjustmentFK != temp._checkAdjustmentFK)
                return false;
            if (this._has_checkAdjustmentFK != temp._has_checkAdjustmentFK)
                return false;
            if (this._trxPercent != null) {
                if (temp._trxPercent == null) return false;
                else if (!(this._trxPercent.equals(temp._trxPercent))) 
                    return false;
            }
            else if (temp._trxPercent != null)
                return false;
            if (this._originalAccountingPeriod != null) {
                if (temp._originalAccountingPeriod == null) return false;
                else if (!(this._originalAccountingPeriod.equals(temp._originalAccountingPeriod))) 
                    return false;
            }
            else if (temp._originalAccountingPeriod != null)
                return false;
            if (this._authorizedSignatureCT != null) {
                if (temp._authorizedSignatureCT == null) return false;
                else if (!(this._authorizedSignatureCT.equals(temp._authorizedSignatureCT))) 
                    return false;
            }
            else if (temp._authorizedSignatureCT != null)
                return false;
            if (this._notTakenOverrideInd != null) {
                if (temp._notTakenOverrideInd == null) return false;
                else if (!(this._notTakenOverrideInd.equals(temp._notTakenOverrideInd))) 
                    return false;
            }
            else if (temp._notTakenOverrideInd != null)
                return false;
            if (this._forceoutMinBalInd != null) {
                if (temp._forceoutMinBalInd == null) return false;
                else if (!(this._forceoutMinBalInd.equals(temp._forceoutMinBalInd))) 
                    return false;
            }
            else if (temp._forceoutMinBalInd != null)
                return false;
            if (this._premiumDueCreatedIndicator != null) {
                if (temp._premiumDueCreatedIndicator == null) return false;
                else if (!(this._premiumDueCreatedIndicator.equals(temp._premiumDueCreatedIndicator))) 
                    return false;
            }
            else if (temp._premiumDueCreatedIndicator != null)
                return false;
            if (this._billAmtEditOverrideInd != null) {
                if (temp._billAmtEditOverrideInd == null) return false;
                else if (!(this._billAmtEditOverrideInd.equals(temp._billAmtEditOverrideInd))) 
                    return false;
            }
            else if (temp._billAmtEditOverrideInd != null)
                return false;
            if (this._zeroInterestIndicator != null) {
                if (temp._zeroInterestIndicator == null) return false;
                else if (!(this._zeroInterestIndicator.equals(temp._zeroInterestIndicator))) 
                    return false;
            }
            else if (temp._zeroInterestIndicator != null)
                return false;
            if (this._EDITTrxCorrespondenceVOList != null) {
                if (temp._EDITTrxCorrespondenceVOList == null) return false;
                else if (!(this._EDITTrxCorrespondenceVOList.equals(temp._EDITTrxCorrespondenceVOList))) 
                    return false;
            }
            else if (temp._EDITTrxCorrespondenceVOList != null)
                return false;
            if (this._EDITTrxHistoryVOList != null) {
                if (temp._EDITTrxHistoryVOList == null) return false;
                else if (!(this._EDITTrxHistoryVOList.equals(temp._EDITTrxHistoryVOList))) 
                    return false;
            }
            else if (temp._EDITTrxHistoryVOList != null)
                return false;
            if (this._overdueChargeVOList != null) {
                if (temp._overdueChargeVOList == null) return false;
                else if (!(this._overdueChargeVOList.equals(temp._overdueChargeVOList))) 
                    return false;
            }
            else if (temp._overdueChargeVOList != null)
                return false;
            if (this._overdueChargeSettledVOList != null) {
                if (temp._overdueChargeSettledVOList == null) return false;
                else if (!(this._overdueChargeSettledVOList.equals(temp._overdueChargeSettledVOList))) 
                    return false;
            }
            else if (temp._overdueChargeSettledVOList != null)
                return false;
            if (this._premiumDueVOList != null) {
                if (temp._premiumDueVOList == null) return false;
                else if (!(this._premiumDueVOList.equals(temp._premiumDueVOList))) 
                    return false;
            }
            else if (temp._premiumDueVOList != null)
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
     * Method getAdvanceNotificationOverrideReturns the value of
     * field 'advanceNotificationOverride'.
     * 
     * @return the value of field 'advanceNotificationOverride'.
     */
    public java.lang.String getAdvanceNotificationOverride()
    {
        return this._advanceNotificationOverride;
    } //-- java.lang.String getAdvanceNotificationOverride() 

    /**
     * Method getAuthorizedSignatureCTReturns the value of field
     * 'authorizedSignatureCT'.
     * 
     * @return the value of field 'authorizedSignatureCT'.
     */
    public java.lang.String getAuthorizedSignatureCT()
    {
        return this._authorizedSignatureCT;
    } //-- java.lang.String getAuthorizedSignatureCT() 

    /**
     * Method getBillAmtEditOverrideIndReturns the value of field
     * 'billAmtEditOverrideInd'.
     * 
     * @return the value of field 'billAmtEditOverrideInd'.
     */
    public java.lang.String getBillAmtEditOverrideInd()
    {
        return this._billAmtEditOverrideInd;
    } //-- java.lang.String getBillAmtEditOverrideInd() 

    /**
     * Method getBonusCommissionAmountReturns the value of field
     * 'bonusCommissionAmount'.
     * 
     * @return the value of field 'bonusCommissionAmount'.
     */
    public java.math.BigDecimal getBonusCommissionAmount()
    {
        return this._bonusCommissionAmount;
    } //-- java.math.BigDecimal getBonusCommissionAmount() 

    /**
     * Method getOriginalAmount turns the value of field
     * 'originalAmount'.
     * 
     * @return the value of field 'originalAmount'.
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return this._originalAmount;
    } //-- java.math.BigDecimal getOriginalAmount() 

    /**
    /**
     * Method getCheckAdjustmentFKReturns the value of field
     * 'checkAdjustmentFK'.
     * 
     * @return the value of field 'checkAdjustmentFK'.
     */
    public long getCheckAdjustmentFK()
    {
        return this._checkAdjustmentFK;
    } //-- long getCheckAdjustmentFK() 

    /**
     * Method getClientSetupFKReturns the value of field
     * 'clientSetupFK'.
     * 
     * @return the value of field 'clientSetupFK'.
     */
    public long getClientSetupFK()
    {
        return this._clientSetupFK;
    } //-- long getClientSetupFK() 
    
    /**
     * Method getSelectedRiderPK Returns the value of field
     * 'selectedRiderPK'.
     * 
     * @return the value of field 'selectedRiderPK'.
     */
    public Long getSelectedRiderPK()
    {
        return this._selectedRiderPK;
    } //-- long getSelectedRiderPK() 
    
    

    /**
     * Method getTerminationTrxFK the value of field
     * 'TerminationTrxFK'.
     * 
     * @return the value of field 'clientSetupFK'.
     */
    public long getTerminationTrxFK()
    {
        return this._terminationTrxFK;
    }
    /**
     * Method getCommissionStatusReturns the value of field
     * 'commissionStatus'.
     * 
     * @return the value of field 'commissionStatus'.
     */
    public java.lang.String getCommissionStatus()
    {
        return this._commissionStatus;
    } //-- java.lang.String getCommissionStatus() 

    /**
     * Method getDateContributionExcessReturns the value of field
     * 'dateContributionExcess'.
     * 
     * @return the value of field 'dateContributionExcess'.
     */
    public java.lang.String getDateContributionExcess()
    {
        return this._dateContributionExcess;
    } //-- java.lang.String getDateContributionExcess() 

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
     * Method getEDITTrxCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxCorrespondenceVO getEDITTrxCorrespondenceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITTrxCorrespondenceVO) _EDITTrxCorrespondenceVOList.elementAt(index);
    } //-- edit.common.vo.EDITTrxCorrespondenceVO getEDITTrxCorrespondenceVO(int) 

    /**
     * Method getEDITTrxCorrespondenceVO
     */
    public edit.common.vo.EDITTrxCorrespondenceVO[] getEDITTrxCorrespondenceVO()
    {
        int size = _EDITTrxCorrespondenceVOList.size();
        edit.common.vo.EDITTrxCorrespondenceVO[] mArray = new edit.common.vo.EDITTrxCorrespondenceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITTrxCorrespondenceVO) _EDITTrxCorrespondenceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITTrxCorrespondenceVO[] getEDITTrxCorrespondenceVO() 

    /**
     * Method getEDITTrxCorrespondenceVOCount
     */
    public int getEDITTrxCorrespondenceVOCount()
    {
        return _EDITTrxCorrespondenceVOList.size();
    } //-- int getEDITTrxCorrespondenceVOCount() 

    /**
     * Method getEDITTrxHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxHistoryVO getEDITTrxHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITTrxHistoryVO) _EDITTrxHistoryVOList.elementAt(index);
    } //-- edit.common.vo.EDITTrxHistoryVO getEDITTrxHistoryVO(int) 

    /**
     * Method getEDITTrxHistoryVO
     */
    public edit.common.vo.EDITTrxHistoryVO[] getEDITTrxHistoryVO()
    {
        int size = _EDITTrxHistoryVOList.size();
        edit.common.vo.EDITTrxHistoryVO[] mArray = new edit.common.vo.EDITTrxHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITTrxHistoryVO) _EDITTrxHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITTrxHistoryVO[] getEDITTrxHistoryVO() 

    /**
     * Method getEDITTrxHistoryVOCount
     */
    public int getEDITTrxHistoryVOCount()
    {
        return _EDITTrxHistoryVOList.size();
    } //-- int getEDITTrxHistoryVOCount() 

    /**
     * Method getEDITTrxPKReturns the value of field 'EDITTrxPK'.
     * 
     * @return the value of field 'EDITTrxPK'.
     */
    public long getEDITTrxPK()
    {
        return this._EDITTrxPK;
    } //-- long getEDITTrxPK() 

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
     * Method getExcessBonusCommissionAmountReturns the value of
     * field 'excessBonusCommissionAmount'.
     * 
     * @return the value of field 'excessBonusCommissionAmount'.
     */
    public java.math.BigDecimal getExcessBonusCommissionAmount()
    {
        return this._excessBonusCommissionAmount;
    } //-- java.math.BigDecimal getExcessBonusCommissionAmount() 

    /**
     * Method getForceoutMinBalIndReturns the value of field
     * 'forceoutMinBalInd'.
     * 
     * @return the value of field 'forceoutMinBalInd'.
     */
    public java.lang.String getForceoutMinBalInd()
    {
        return this._forceoutMinBalInd;
    } //-- java.lang.String getForceoutMinBalInd() 

    /**
     * Method getInterestProceedsOverrideReturns the value of field
     * 'interestProceedsOverride'.
     * 
     * @return the value of field 'interestProceedsOverride'.
     */
    public java.math.BigDecimal getInterestProceedsOverride()
    {
        return this._interestProceedsOverride;
    } //-- java.math.BigDecimal getInterestProceedsOverride() 

    /**
     * Method getLookBackIndReturns the value of field
     * 'lookBackInd'.
     * 
     * @return the value of field 'lookBackInd'.
     */
    public java.lang.String getLookBackInd()
    {
        return this._lookBackInd;
    } //-- java.lang.String getLookBackInd() 

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
     * Method getNewPolicyNumberReturns the value of field
     * 'newPolicyNumber'.
     * 
     * @return the value of field 'newPolicyNumber'.
     */
    public java.lang.String getNewPolicyNumber()
    {
        return this._newPolicyNumber;
    } //-- java.lang.String getNewPolicyNumber() 

    /**
     * Method getNoAccountingIndReturns the value of field
     * 'noAccountingInd'.
     * 
     * @return the value of field 'noAccountingInd'.
     */
    public java.lang.String getNoAccountingInd()
    {
        return this._noAccountingInd;
    } //-- java.lang.String getNoAccountingInd() 

    /**
     * Method getNoCheckEFTReturns the value of field 'noCheckEFT'.
     * 
     * @return the value of field 'noCheckEFT'.
     */
    public java.lang.String getNoCheckEFT()
    {
        return this._noCheckEFT;
    } //-- java.lang.String getNoCheckEFT() 

    /**
     * Method getNoCommissionIndReturns the value of field
     * 'noCommissionInd'.
     * 
     * @return the value of field 'noCommissionInd'.
     */
    public java.lang.String getNoCommissionInd()
    {
        return this._noCommissionInd;
    } //-- java.lang.String getNoCommissionInd() 

    /**
     * Method getZeroLoadInd returns the value of field
     * 'zeroLoadInd'.
     * 
     * @return the value of field 'zeroLoadInd'.
     */
    public java.lang.String getZeroLoadInd()
    {
        return this._zeroLoadInd;
    } //-- java.lang.String getZeroLoadInd() 

    /**
     * Method getNoCorrespondenceIndReturns the value of field
     * 'noCorrespondenceInd'.
     * 
     * @return the value of field 'noCorrespondenceInd'.
     */
    public java.lang.String getNoCorrespondenceInd()
    {
        return this._noCorrespondenceInd;
    } //-- java.lang.String getNoCorrespondenceInd() 

    /**
     * Method getNotTakenOverrideIndReturns the value of field
     * 'notTakenOverrideInd'.
     * 
     * @return the value of field 'notTakenOverrideInd'.
     */
    public java.lang.String getNotTakenOverrideInd()
    {
        return this._notTakenOverrideInd;
    } //-- java.lang.String getNotTakenOverrideInd() 

    /**
     * Method getNotificationAmountReturns the value of field
     * 'notificationAmount'.
     * 
     * @return the value of field 'notificationAmount'.
     */
    public java.math.BigDecimal getNotificationAmount()
    {
        return this._notificationAmount;
    } //-- java.math.BigDecimal getNotificationAmount() 

    /**
     * Method getNotificationAmountReceivedReturns the value of
     * field 'notificationAmountReceived'.
     * 
     * @return the value of field 'notificationAmountReceived'.
     */
    public java.math.BigDecimal getNotificationAmountReceived()
    {
        return this._notificationAmountReceived;
    } //-- java.math.BigDecimal getNotificationAmountReceived() 

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
     * Method getOriginalAccountingPeriodReturns the value of field
     * 'originalAccountingPeriod'.
     * 
     * @return the value of field 'originalAccountingPeriod'.
     */
    public java.lang.String getOriginalAccountingPeriod()
    {
        return this._originalAccountingPeriod;
    } //-- java.lang.String getOriginalAccountingPeriod() 

    /**
     * Method getOriginatingTrxFKReturns the value of field
     * 'originatingTrxFK'.
     * 
     * @return the value of field 'originatingTrxFK'.
     */
    public long getOriginatingTrxFK()
    {
        return this._originatingTrxFK;
    } //-- long getOriginatingTrxFK() 

    /**
     * Method getOverdueChargeSettledVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeSettledVO getOverdueChargeSettledVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeSettledVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OverdueChargeSettledVO) _overdueChargeSettledVOList.elementAt(index);
    } //-- edit.common.vo.OverdueChargeSettledVO getOverdueChargeSettledVO(int) 

    /**
     * Method getOverdueChargeSettledVO
     */
    public edit.common.vo.OverdueChargeSettledVO[] getOverdueChargeSettledVO()
    {
        int size = _overdueChargeSettledVOList.size();
        edit.common.vo.OverdueChargeSettledVO[] mArray = new edit.common.vo.OverdueChargeSettledVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OverdueChargeSettledVO) _overdueChargeSettledVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OverdueChargeSettledVO[] getOverdueChargeSettledVO() 

    /**
     * Method getOverdueChargeSettledVOCount
     */
    public int getOverdueChargeSettledVOCount()
    {
        return _overdueChargeSettledVOList.size();
    } //-- int getOverdueChargeSettledVOCount() 

    /**
     * Method getOverdueChargeVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeVO getOverdueChargeVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OverdueChargeVO) _overdueChargeVOList.elementAt(index);
    } //-- edit.common.vo.OverdueChargeVO getOverdueChargeVO(int) 

    /**
     * Method getOverdueChargeVO
     */
    public edit.common.vo.OverdueChargeVO[] getOverdueChargeVO()
    {
        int size = _overdueChargeVOList.size();
        edit.common.vo.OverdueChargeVO[] mArray = new edit.common.vo.OverdueChargeVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OverdueChargeVO) _overdueChargeVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OverdueChargeVO[] getOverdueChargeVO() 

    /**
     * Method getOverdueChargeVOCount
     */
    public int getOverdueChargeVOCount()
    {
        return _overdueChargeVOList.size();
    } //-- int getOverdueChargeVOCount() 

    /**
     * Method getPendingStatusReturns the value of field
     * 'pendingStatus'.
     * 
     * @return the value of field 'pendingStatus'.
     */
    public java.lang.String getPendingStatus()
    {
        return this._pendingStatus;
    } //-- java.lang.String getPendingStatus() 

    /**
     * Method getPremiumDueCreatedIndicatorReturns the value of
     * field 'premiumDueCreatedIndicator'.
     * 
     * @return the value of field 'premiumDueCreatedIndicator'.
     */
    public java.lang.String getPremiumDueCreatedIndicator()
    {
        return this._premiumDueCreatedIndicator;
    } //-- java.lang.String getPremiumDueCreatedIndicator() 

    /**
     * Method getPremiumDueVO
     * 
     * @param index
     */
    public edit.common.vo.PremiumDueVO getPremiumDueVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _premiumDueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PremiumDueVO) _premiumDueVOList.elementAt(index);
    } //-- edit.common.vo.PremiumDueVO getPremiumDueVO(int) 

    /**
     * Method getPremiumDueVO
     */
    public edit.common.vo.PremiumDueVO[] getPremiumDueVO()
    {
        int size = _premiumDueVOList.size();
        edit.common.vo.PremiumDueVO[] mArray = new edit.common.vo.PremiumDueVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PremiumDueVO) _premiumDueVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PremiumDueVO[] getPremiumDueVO() 

    /**
     * Method getPremiumDueVOCount
     */
    public int getPremiumDueVOCount()
    {
        return _premiumDueVOList.size();
    } //-- int getPremiumDueVOCount() 

    /**
     * Method getReapplyEDITTrxFKReturns the value of field
     * 'reapplyEDITTrxFK'.
     * 
     * @return the value of field 'reapplyEDITTrxFK'.
     */
    public long getReapplyEDITTrxFK()
    {
        return this._reapplyEDITTrxFK;
    } //-- long getReapplyEDITTrxFK() 

    /**
     * Method getReinsuranceStatusReturns the value of field
     * 'reinsuranceStatus'.
     * 
     * @return the value of field 'reinsuranceStatus'.
     */
    public java.lang.String getReinsuranceStatus()
    {
        return this._reinsuranceStatus;
    } //-- java.lang.String getReinsuranceStatus() 

    /**
     * Method getReversalReasonCodeCTReturns the value of field
     * 'reversalReasonCodeCT'.
     * 
     * @return the value of field 'reversalReasonCodeCT'.
     */
    public java.lang.String getReversalReasonCodeCT()
    {
        return this._reversalReasonCodeCT;
    } //-- java.lang.String getReversalReasonCodeCT() 

    /**
     * Method getSequenceNumberReturns the value of field
     * 'sequenceNumber'.
     * 
     * @return the value of field 'sequenceNumber'.
     */
    public int getSequenceNumber()
    {
        return this._sequenceNumber;
    } //-- int getSequenceNumber() 

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
     * Method getTaxYearReturns the value of field 'taxYear'.
     * 
     * @return the value of field 'taxYear'.
     */
    public int getTaxYear()
    {
        return this._taxYear;
    } //-- int getTaxYear() 

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
     * Method getTransferTypeCTReturns the value of field
     * 'transferTypeCT'.
     * 
     * @return the value of field 'transferTypeCT'.
     */
    public java.lang.String getTransferTypeCT()
    {
        return this._transferTypeCT;
    } //-- java.lang.String getTransferTypeCT() 

    /**
     * Method getTransferUnitsTypeReturns the value of field
     * 'transferUnitsType'.
     * 
     * @return the value of field 'transferUnitsType'.
     */
    public java.lang.String getTransferUnitsType()
    {
        return this._transferUnitsType;
    } //-- java.lang.String getTransferUnitsType() 

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
     * Method getTrxIsRescheduledIndReturns the value of field
     * 'trxIsRescheduledInd'.
     * 
     * @return the value of field 'trxIsRescheduledInd'.
     */
    public java.lang.String getTrxIsRescheduledInd()
    {
        return this._trxIsRescheduledInd;
    } //-- java.lang.String getTrxIsRescheduledInd() 

    /**
     * Method getTrxPercentReturns the value of field 'trxPercent'.
     * 
     * @return the value of field 'trxPercent'.
     */
    public java.math.BigDecimal getTrxPercent()
    {
        return this._trxPercent;
    } //-- java.math.BigDecimal getTrxPercent() 

    /**
     * Method getZeroInterestIndicatorReturns the value of field
     * 'zeroInterestIndicator'.
     * 
     * @return the value of field 'zeroInterestIndicator'.
     */
    public java.lang.String getZeroInterestIndicator()
    {
        return this._zeroInterestIndicator;
    } //-- java.lang.String getZeroInterestIndicator() 

    /**
     * Method hasCheckAdjustmentFK
     */
    public boolean hasCheckAdjustmentFK()
    {
        return this._has_checkAdjustmentFK;
    } //-- boolean hasCheckAdjustmentFK() 

    /**
     * Method hasClientSetupFK
     */
    public boolean hasClientSetupFK()
    {
        return this._has_clientSetupFK;
    } //-- boolean hasClientSetupFK() 

    /**
     * Method hasEDITTrxPK
     */
    public boolean hasEDITTrxPK()
    {
        return this._has_EDITTrxPK;
    } //-- boolean hasEDITTrxPK() 

    /**
     * Method hasOriginatingTrxFK
     */
    public boolean hasOriginatingTrxFK()
    {
        return this._has_originatingTrxFK;
    } //-- boolean hasOriginatingTrxFK() 

    /**
     * Method hasReapplyEDITTrxFK
     */
    public boolean hasReapplyEDITTrxFK()
    {
        return this._has_reapplyEDITTrxFK;
    } //-- boolean hasReapplyEDITTrxFK() 

    /**
     * Method hasSequenceNumber
     */
    public boolean hasSequenceNumber()
    {
        return this._has_sequenceNumber;
    } //-- boolean hasSequenceNumber() 

    /**
     * Method hasTaxYear
     */
    public boolean hasTaxYear()
    {
        return this._has_taxYear;
    } //-- boolean hasTaxYear() 

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
     * Method removeAllEDITTrxCorrespondenceVO
     */
    public void removeAllEDITTrxCorrespondenceVO()
    {
        _EDITTrxCorrespondenceVOList.removeAllElements();
    } //-- void removeAllEDITTrxCorrespondenceVO() 

    /**
     * Method removeAllEDITTrxHistoryVO
     */
    public void removeAllEDITTrxHistoryVO()
    {
        _EDITTrxHistoryVOList.removeAllElements();
    } //-- void removeAllEDITTrxHistoryVO() 

    /**
     * Method removeAllOverdueChargeSettledVO
     */
    public void removeAllOverdueChargeSettledVO()
    {
        _overdueChargeSettledVOList.removeAllElements();
    } //-- void removeAllOverdueChargeSettledVO() 

    /**
     * Method removeAllOverdueChargeVO
     */
    public void removeAllOverdueChargeVO()
    {
        _overdueChargeVOList.removeAllElements();
    } //-- void removeAllOverdueChargeVO() 

    /**
     * Method removeAllPremiumDueVO
     */
    public void removeAllPremiumDueVO()
    {
        _premiumDueVOList.removeAllElements();
    } //-- void removeAllPremiumDueVO() 

    /**
     * Method removeEDITTrxCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxCorrespondenceVO removeEDITTrxCorrespondenceVO(int index)
    {
        java.lang.Object obj = _EDITTrxCorrespondenceVOList.elementAt(index);
        _EDITTrxCorrespondenceVOList.removeElementAt(index);
        return (edit.common.vo.EDITTrxCorrespondenceVO) obj;
     //-- edit.common.vo.EDITTrxCorrespondenceVO removeEDITTrxCorrespondenceVO(int) 
    }

    /**
     * Method removeEDITTrxHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxHistoryVO removeEDITTrxHistoryVO(int index)
    {
        java.lang.Object obj = _EDITTrxHistoryVOList.elementAt(index);
        _EDITTrxHistoryVOList.removeElementAt(index);
        return (edit.common.vo.EDITTrxHistoryVO) obj;
    } //-- edit.common.vo.EDITTrxHistoryVO removeEDITTrxHistoryVO(int) 

    /**
     * Method removeOverdueChargeSettledVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeSettledVO removeOverdueChargeSettledVO(int index)
    {
        java.lang.Object obj = _overdueChargeSettledVOList.elementAt(index);
        _overdueChargeSettledVOList.removeElementAt(index);
        return (edit.common.vo.OverdueChargeSettledVO) obj;
    } //-- edit.common.vo.OverdueChargeSettledVO removeOverdueChargeSettledVO(int) 

    /**
     * Method removeOverdueChargeVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeVO removeOverdueChargeVO(int index)
    {
        java.lang.Object obj = _overdueChargeVOList.elementAt(index);
        _overdueChargeVOList.removeElementAt(index);
        return (edit.common.vo.OverdueChargeVO) obj;
    } //-- edit.common.vo.OverdueChargeVO removeOverdueChargeVO(int) 

    /**
     * Method removePremiumDueVO
     * 
     * @param index
     */
    public edit.common.vo.PremiumDueVO removePremiumDueVO(int index)
    {
        java.lang.Object obj = _premiumDueVOList.elementAt(index);
        _premiumDueVOList.removeElementAt(index);
        return (edit.common.vo.PremiumDueVO) obj;
    } //-- edit.common.vo.PremiumDueVO removePremiumDueVO(int) 

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
     * Method setAdvanceNotificationOverrideSets the value of field
     * 'advanceNotificationOverride'.
     * 
     * @param advanceNotificationOverride the value of field
     * 'advanceNotificationOverride'.
     */
    public void setAdvanceNotificationOverride(java.lang.String advanceNotificationOverride)
    {
        this._advanceNotificationOverride = advanceNotificationOverride;
        
        super.setVoChanged(true);
    } //-- void setAdvanceNotificationOverride(java.lang.String) 

    /**
     * Method setAuthorizedSignatureCTSets the value of field
     * 'authorizedSignatureCT'.
     * 
     * @param authorizedSignatureCT the value of field
     * 'authorizedSignatureCT'.
     */
    public void setAuthorizedSignatureCT(java.lang.String authorizedSignatureCT)
    {
        this._authorizedSignatureCT = authorizedSignatureCT;
        
        super.setVoChanged(true);
    } //-- void setAuthorizedSignatureCT(java.lang.String) 

    /**
     * Method setBillAmtEditOverrideIndSets the value of field
     * 'billAmtEditOverrideInd'.
     * 
     * @param billAmtEditOverrideInd the value of field
     * 'billAmtEditOverrideInd'.
     */
    public void setBillAmtEditOverrideInd(java.lang.String billAmtEditOverrideInd)
    {
        this._billAmtEditOverrideInd = billAmtEditOverrideInd;
        
        super.setVoChanged(true);
    } //-- void setBillAmtEditOverrideInd(java.lang.String) 

    /**
     * Method setBonusCommissionAmountSets the value of field
     * 'bonusCommissionAmount'.
     * 
     * @param bonusCommissionAmount the value of field
     * 'bonusCommissionAmount'.
     */
    public void setBonusCommissionAmount(java.math.BigDecimal bonusCommissionAmount)
    {
        this._bonusCommissionAmount = bonusCommissionAmount;
        
        super.setVoChanged(true);
    } //-- void setBonusCommissionAmount(java.math.BigDecimal) 

    /**
     * Method setCheckAdjustmentFKSets the value of field
     * 'checkAdjustmentFK'.
     * 
     * @param checkAdjustmentFK the value of field
     * 'checkAdjustmentFK'.
     */
    public void setCheckAdjustmentFK(long checkAdjustmentFK)
    {
        this._checkAdjustmentFK = checkAdjustmentFK;
        
        super.setVoChanged(true);
        this._has_checkAdjustmentFK = true;
    } //-- void setCheckAdjustmentFK(long) 

    /**
     * Method setClientSetupFKSets the value of field
     * 'clientSetupFK'.
     * 
     * @param clientSetupFK the value of field 'clientSetupFK'.
     */
    public void setClientSetupFK(long clientSetupFK)
    {
        this._clientSetupFK = clientSetupFK;
        
        super.setVoChanged(true);
        this._has_clientSetupFK = true;
    } //-- void setClientSetupFK(long) 


    /**
     * Method setSelectedRiderPK Sets the value of field
     * 'selectedRiderPK'.
     * 
     * @param selectedRiderPK the value of field 'selectedRiderPK'.
     */
    public void setSelectedRiderPK(Long selectedRiderPK)
    {
        this._selectedRiderPK = selectedRiderPK;
        
    } //-- void setSelectedRiderPKK(long) 

    /**
     * Method setTerminationTrxFK the value of field
     * 'terminationTrxFK'.
     * 
     * @param clientSetupFK the value of field 'terminationTrxFK'.
     */
    public void setTerminationTrxFK(long _terminationTrxFK)
    {
        this._terminationTrxFK = _terminationTrxFK;
    }
    /**
     * Method setCommissionStatusSets the value of field
     * 'commissionStatus'.
     * 
     * @param commissionStatus the value of field 'commissionStatus'
     */
    public void setCommissionStatus(java.lang.String commissionStatus)
    {
        this._commissionStatus = commissionStatus;
        
        super.setVoChanged(true);
    } //-- void setCommissionStatus(java.lang.String) 

    /**
     * Method setDateContributionExcessSets the value of field
     * 'dateContributionExcess'.
     * 
     * @param dateContributionExcess the value of field
     * 'dateContributionExcess'.
     */
    public void setDateContributionExcess(java.lang.String dateContributionExcess)
    {
        this._dateContributionExcess = dateContributionExcess;
        
        super.setVoChanged(true);
    } //-- void setDateContributionExcess(java.lang.String) 

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
     * Method setEDITTrxCorrespondenceVO
     * 
     * @param index
     * @param vEDITTrxCorrespondenceVO
     */
    public void setEDITTrxCorrespondenceVO(int index, edit.common.vo.EDITTrxCorrespondenceVO vEDITTrxCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEDITTrxCorrespondenceVO.setParentVO(this.getClass(), this);
        _EDITTrxCorrespondenceVOList.setElementAt(vEDITTrxCorrespondenceVO, index);
    } //-- void setEDITTrxCorrespondenceVO(int, edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method setEDITTrxCorrespondenceVO
     * 
     * @param EDITTrxCorrespondenceVOArray
     */
    public void setEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO[] EDITTrxCorrespondenceVOArray)
    {
        //-- copy array
        _EDITTrxCorrespondenceVOList.removeAllElements();
        for (int i = 0; i < EDITTrxCorrespondenceVOArray.length; i++) {
            EDITTrxCorrespondenceVOArray[i].setParentVO(this.getClass(), this);
            _EDITTrxCorrespondenceVOList.addElement(EDITTrxCorrespondenceVOArray[i]);
        }
    } //-- void setEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method setEDITTrxHistoryVO
     * 
     * @param index
     * @param vEDITTrxHistoryVO
     */
    public void setEDITTrxHistoryVO(int index, edit.common.vo.EDITTrxHistoryVO vEDITTrxHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEDITTrxHistoryVO.setParentVO(this.getClass(), this);
        _EDITTrxHistoryVOList.setElementAt(vEDITTrxHistoryVO, index);
    } //-- void setEDITTrxHistoryVO(int, edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method setEDITTrxHistoryVO
     * 
     * @param EDITTrxHistoryVOArray
     */
    public void setEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO[] EDITTrxHistoryVOArray)
    {
        //-- copy array
        _EDITTrxHistoryVOList.removeAllElements();
        for (int i = 0; i < EDITTrxHistoryVOArray.length; i++) {
            EDITTrxHistoryVOArray[i].setParentVO(this.getClass(), this);
            _EDITTrxHistoryVOList.addElement(EDITTrxHistoryVOArray[i]);
        }
    } //-- void setEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method setEDITTrxPKSets the value of field 'EDITTrxPK'.
     * 
     * @param EDITTrxPK the value of field 'EDITTrxPK'.
     */
    public void setEDITTrxPK(long EDITTrxPK)
    {
        this._EDITTrxPK = EDITTrxPK;
        
        super.setVoChanged(true);
        this._has_EDITTrxPK = true;
    } //-- void setEDITTrxPK(long) 

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
     * Method setExcessBonusCommissionAmountSets the value of field
     * 'excessBonusCommissionAmount'.
     * 
     * @param excessBonusCommissionAmount the value of field
     * 'excessBonusCommissionAmount'.
     */
    public void setExcessBonusCommissionAmount(java.math.BigDecimal excessBonusCommissionAmount)
    {
        this._excessBonusCommissionAmount = excessBonusCommissionAmount;
        
        super.setVoChanged(true);
    } //-- void setExcessBonusCommissionAmount(java.math.BigDecimal) 

    /**
     * Method setForceoutMinBalIndSets the value of field
     * 'forceoutMinBalInd'.
     * 
     * @param forceoutMinBalInd the value of field
     * 'forceoutMinBalInd'.
     */
    public void setForceoutMinBalInd(java.lang.String forceoutMinBalInd)
    {
        this._forceoutMinBalInd = forceoutMinBalInd;
        
        super.setVoChanged(true);
    } //-- void setForceoutMinBalInd(java.lang.String) 

    /**
     * Method setInterestProceedsOverrideSets the value of field
     * 'interestProceedsOverride'.
     * 
     * @param interestProceedsOverride the value of field
     * 'interestProceedsOverride'.
     */
    public void setInterestProceedsOverride(java.math.BigDecimal interestProceedsOverride)
    {
        this._interestProceedsOverride = interestProceedsOverride;
        
        super.setVoChanged(true);
    } //-- void setInterestProceedsOverride(java.math.BigDecimal) 

    /**
     * Method setLookBackIndSets the value of field 'lookBackInd'.
     * 
     * @param lookBackInd the value of field 'lookBackInd'.
     */
    public void setLookBackInd(java.lang.String lookBackInd)
    {
        this._lookBackInd = lookBackInd;
        
        super.setVoChanged(true);
    } //-- void setLookBackInd(java.lang.String) 

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
     * Method setNewPolicyNumberSets the value of field
     * 'newPolicyNumber'.
     * 
     * @param newPolicyNumber the value of field 'newPolicyNumber'.
     */
    public void setNewPolicyNumber(java.lang.String newPolicyNumber)
    {
        this._newPolicyNumber = newPolicyNumber;
        
        super.setVoChanged(true);
    } //-- void setNewPolicyNumber(java.lang.String) 

    /**
     * Method setNoAccountingIndSets the value of field
     * 'noAccountingInd'.
     * 
     * @param noAccountingInd the value of field 'noAccountingInd'.
     */
    public void setNoAccountingInd(java.lang.String noAccountingInd)
    {
        this._noAccountingInd = noAccountingInd;
        
        super.setVoChanged(true);
    } //-- void setNoAccountingInd(java.lang.String) 

    /**
     * Method setNoCheckEFTSets the value of field 'noCheckEFT'.
     * 
     * @param noCheckEFT the value of field 'noCheckEFT'.
     */
    public void setNoCheckEFT(java.lang.String noCheckEFT)
    {
        this._noCheckEFT = noCheckEFT;
        
        super.setVoChanged(true);
    } //-- void setNoCheckEFT(java.lang.String) 

    /**
     * Method setNoCommissionIndSets the value of field
     * 'noCommissionInd'.
     * 
     * @param noCommissionInd the value of field 'noCommissionInd'.
     */
    public void setNoCommissionInd(java.lang.String noCommissionInd)
    {
        this._noCommissionInd = noCommissionInd;
        
        super.setVoChanged(true);
    } //-- void setNoCommissionInd(java.lang.String) 

    /**
     * Method setZeroLoadInd Sets the value of field
     * 'zeroLoadInd'.
     * 
     * @param zeroLoadInd the value of field 'zeroLoadInd'.
     */
    public void setZeroLoadInd(java.lang.String zeroLoadInd)
    {
        this._zeroLoadInd = zeroLoadInd;
        
        super.setVoChanged(true);
    } //-- void setZeroLoadInd(java.lang.String) 

    /**
     * Method setNoCorrespondenceIndSets the value of field
     * 'noCorrespondenceInd'.
     * 
     * @param noCorrespondenceInd the value of field
     * 'noCorrespondenceInd'.
     */
    public void setNoCorrespondenceInd(java.lang.String noCorrespondenceInd)
    {
        this._noCorrespondenceInd = noCorrespondenceInd;
        
        super.setVoChanged(true);
    } //-- void setNoCorrespondenceInd(java.lang.String) 

    /**
     * Method setNotTakenOverrideIndSets the value of field
     * 'notTakenOverrideInd'.
     * 
     * @param notTakenOverrideInd the value of field
     * 'notTakenOverrideInd'.
     */
    public void setNotTakenOverrideInd(java.lang.String notTakenOverrideInd)
    {
        this._notTakenOverrideInd = notTakenOverrideInd;
        
        super.setVoChanged(true);
    } //-- void setNotTakenOverrideInd(java.lang.String) 

    /**
     * Method setNotificationAmountSets the value of field
     * 'notificationAmount'.
     * 
     * @param notificationAmount the value of field
     * 'notificationAmount'.
     */
    public void setNotificationAmount(java.math.BigDecimal notificationAmount)
    {
        this._notificationAmount = notificationAmount;
        
        super.setVoChanged(true);
    } //-- void setNotificationAmount(java.math.BigDecimal) 

    /**
     * Method setNotificationAmountSets the value of field
     * 'notificationAmount'.
     * 
     * @param notificationAmount the value of field
     * 'notificationAmount'.
     */
    public void setOriginalAmount(java.math.BigDecimal originalAmount)
    {
        this._originalAmount = originalAmount;
        
    } //-- void setNotificationAmount(java.math.BigDecimal) 

    /**
     * Method setNotificationAmountReceivedSets the value of field
     * 'notificationAmountReceived'.
     * 
     * @param notificationAmountReceived the value of field
     * 'notificationAmountReceived'.
     */
    public void setNotificationAmountReceived(java.math.BigDecimal notificationAmountReceived)
    {
        this._notificationAmountReceived = notificationAmountReceived;
        
        super.setVoChanged(true);
    } //-- void setNotificationAmountReceived(java.math.BigDecimal) 

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
     * Method setOriginalAccountingPeriodSets the value of field
     * 'originalAccountingPeriod'.
     * 
     * @param originalAccountingPeriod the value of field
     * 'originalAccountingPeriod'.
     */
    public void setOriginalAccountingPeriod(java.lang.String originalAccountingPeriod)
    {
        this._originalAccountingPeriod = originalAccountingPeriod;
        
        super.setVoChanged(true);
    } //-- void setOriginalAccountingPeriod(java.lang.String) 

    /**
     * Method setOriginatingTrxFKSets the value of field
     * 'originatingTrxFK'.
     * 
     * @param originatingTrxFK the value of field 'originatingTrxFK'
     */
    public void setOriginatingTrxFK(long originatingTrxFK)
    {
        this._originatingTrxFK = originatingTrxFK;
        
        super.setVoChanged(true);
        this._has_originatingTrxFK = true;
    } //-- void setOriginatingTrxFK(long) 

    /**
     * Method setOverdueChargeSettledVO
     * 
     * @param index
     * @param vOverdueChargeSettledVO
     */
    public void setOverdueChargeSettledVO(int index, edit.common.vo.OverdueChargeSettledVO vOverdueChargeSettledVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeSettledVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOverdueChargeSettledVO.setParentVO(this.getClass(), this);
        _overdueChargeSettledVOList.setElementAt(vOverdueChargeSettledVO, index);
    } //-- void setOverdueChargeSettledVO(int, edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method setOverdueChargeSettledVO
     * 
     * @param overdueChargeSettledVOArray
     */
    public void setOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO[] overdueChargeSettledVOArray)
    {
        //-- copy array
        _overdueChargeSettledVOList.removeAllElements();
        for (int i = 0; i < overdueChargeSettledVOArray.length; i++) {
            overdueChargeSettledVOArray[i].setParentVO(this.getClass(), this);
            _overdueChargeSettledVOList.addElement(overdueChargeSettledVOArray[i]);
        }
    } //-- void setOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method setOverdueChargeVO
     * 
     * @param index
     * @param vOverdueChargeVO
     */
    public void setOverdueChargeVO(int index, edit.common.vo.OverdueChargeVO vOverdueChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOverdueChargeVO.setParentVO(this.getClass(), this);
        _overdueChargeVOList.setElementAt(vOverdueChargeVO, index);
    } //-- void setOverdueChargeVO(int, edit.common.vo.OverdueChargeVO) 

    /**
     * Method setOverdueChargeVO
     * 
     * @param overdueChargeVOArray
     */
    public void setOverdueChargeVO(edit.common.vo.OverdueChargeVO[] overdueChargeVOArray)
    {
        //-- copy array
        _overdueChargeVOList.removeAllElements();
        for (int i = 0; i < overdueChargeVOArray.length; i++) {
            overdueChargeVOArray[i].setParentVO(this.getClass(), this);
            _overdueChargeVOList.addElement(overdueChargeVOArray[i]);
        }
    } //-- void setOverdueChargeVO(edit.common.vo.OverdueChargeVO) 

    /**
     * Method setPendingStatusSets the value of field
     * 'pendingStatus'.
     * 
     * @param pendingStatus the value of field 'pendingStatus'.
     */
    public void setPendingStatus(java.lang.String pendingStatus)
    {
        this._pendingStatus = pendingStatus;
        
        super.setVoChanged(true);
    } //-- void setPendingStatus(java.lang.String) 

    /**
     * Method setPremiumDueCreatedIndicatorSets the value of field
     * 'premiumDueCreatedIndicator'.
     * 
     * @param premiumDueCreatedIndicator the value of field
     * 'premiumDueCreatedIndicator'.
     */
    public void setPremiumDueCreatedIndicator(java.lang.String premiumDueCreatedIndicator)
    {
        this._premiumDueCreatedIndicator = premiumDueCreatedIndicator;
        
        super.setVoChanged(true);
    } //-- void setPremiumDueCreatedIndicator(java.lang.String) 

    /**
     * Method setPremiumDueVO
     * 
     * @param index
     * @param vPremiumDueVO
     */
    public void setPremiumDueVO(int index, edit.common.vo.PremiumDueVO vPremiumDueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _premiumDueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPremiumDueVO.setParentVO(this.getClass(), this);
        _premiumDueVOList.setElementAt(vPremiumDueVO, index);
    } //-- void setPremiumDueVO(int, edit.common.vo.PremiumDueVO) 

    /**
     * Method setPremiumDueVO
     * 
     * @param premiumDueVOArray
     */
    public void setPremiumDueVO(edit.common.vo.PremiumDueVO[] premiumDueVOArray)
    {
        //-- copy array
        _premiumDueVOList.removeAllElements();
        for (int i = 0; i < premiumDueVOArray.length; i++) {
            premiumDueVOArray[i].setParentVO(this.getClass(), this);
            _premiumDueVOList.addElement(premiumDueVOArray[i]);
        }
    } //-- void setPremiumDueVO(edit.common.vo.PremiumDueVO) 

    /**
     * Method setReapplyEDITTrxFKSets the value of field
     * 'reapplyEDITTrxFK'.
     * 
     * @param reapplyEDITTrxFK the value of field 'reapplyEDITTrxFK'
     */
    public void setReapplyEDITTrxFK(long reapplyEDITTrxFK)
    {
        this._reapplyEDITTrxFK = reapplyEDITTrxFK;
        
        super.setVoChanged(true);
        this._has_reapplyEDITTrxFK = true;
    } //-- void setReapplyEDITTrxFK(long) 

    /**
     * Method setReinsuranceStatusSets the value of field
     * 'reinsuranceStatus'.
     * 
     * @param reinsuranceStatus the value of field
     * 'reinsuranceStatus'.
     */
    public void setReinsuranceStatus(java.lang.String reinsuranceStatus)
    {
        this._reinsuranceStatus = reinsuranceStatus;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceStatus(java.lang.String) 

    /**
     * Method setReversalReasonCodeCTSets the value of field
     * 'reversalReasonCodeCT'.
     * 
     * @param reversalReasonCodeCT the value of field
     * 'reversalReasonCodeCT'.
     */
    public void setReversalReasonCodeCT(java.lang.String reversalReasonCodeCT)
    {
        this._reversalReasonCodeCT = reversalReasonCodeCT;
        
        super.setVoChanged(true);
    } //-- void setReversalReasonCodeCT(java.lang.String) 

    /**
     * Method setSequenceNumberSets the value of field
     * 'sequenceNumber'.
     * 
     * @param sequenceNumber the value of field 'sequenceNumber'.
     */
    public void setSequenceNumber(int sequenceNumber)
    {
        this._sequenceNumber = sequenceNumber;
        
        super.setVoChanged(true);
        this._has_sequenceNumber = true;
    } //-- void setSequenceNumber(int) 

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
     * Method setTaxYearSets the value of field 'taxYear'.
     * 
     * @param taxYear the value of field 'taxYear'.
     */
    public void setTaxYear(int taxYear)
    {
        this._taxYear = taxYear;
        
        super.setVoChanged(true);
        this._has_taxYear = true;
    } //-- void setTaxYear(int) 

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
     * Method setTransferTypeCTSets the value of field
     * 'transferTypeCT'.
     * 
     * @param transferTypeCT the value of field 'transferTypeCT'.
     */
    public void setTransferTypeCT(java.lang.String transferTypeCT)
    {
        this._transferTypeCT = transferTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTransferTypeCT(java.lang.String) 

    /**
     * Method setTransferUnitsTypeSets the value of field
     * 'transferUnitsType'.
     * 
     * @param transferUnitsType the value of field
     * 'transferUnitsType'.
     */
    public void setTransferUnitsType(java.lang.String transferUnitsType)
    {
        this._transferUnitsType = transferUnitsType;
        
        super.setVoChanged(true);
    } //-- void setTransferUnitsType(java.lang.String) 

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
     * Method setTrxIsRescheduledIndSets the value of field
     * 'trxIsRescheduledInd'.
     * 
     * @param trxIsRescheduledInd the value of field
     * 'trxIsRescheduledInd'.
     */
    public void setTrxIsRescheduledInd(java.lang.String trxIsRescheduledInd)
    {
        this._trxIsRescheduledInd = trxIsRescheduledInd;
        
        super.setVoChanged(true);
    } //-- void setTrxIsRescheduledInd(java.lang.String) 

    /**
     * Method setTrxPercentSets the value of field 'trxPercent'.
     * 
     * @param trxPercent the value of field 'trxPercent'.
     */
    public void setTrxPercent(java.math.BigDecimal trxPercent)
    {
        this._trxPercent = trxPercent;
        
        super.setVoChanged(true);
    } //-- void setTrxPercent(java.math.BigDecimal) 

    /**
     * Method setZeroInterestIndicatorSets the value of field
     * 'zeroInterestIndicator'.
     * 
     * @param zeroInterestIndicator the value of field
     * 'zeroInterestIndicator'.
     */
    public void setZeroInterestIndicator(java.lang.String zeroInterestIndicator)
    {
        this._zeroInterestIndicator = zeroInterestIndicator;
        
        super.setVoChanged(true);
    } //-- void setZeroInterestIndicator(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITTrxVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITTrxVO) Unmarshaller.unmarshal(edit.common.vo.EDITTrxVO.class, reader);
    } //-- edit.common.vo.EDITTrxVO unmarshal(java.io.Reader) 

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
