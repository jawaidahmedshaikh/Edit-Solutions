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

import org.apache.commons.collections.CollectionUtils;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

import edit.common.EDITBigDecimal;

/**
 * Class SegmentVO.
 * 
 * @version $Revision$ $Date$
 */
public class SegmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _segmentPK
     */
    private long _segmentPK;

    /**
     * keeps track of state for field: _segmentPK
     */
    private boolean _has_segmentPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _billScheduleFK
     */
    private long _billScheduleFK;

    /**
     * keeps track of state for field: _billScheduleFK
     */
    private boolean _has_billScheduleFK;

    /**
     * Field _batchContractSetupFK
     */
    private long _batchContractSetupFK;

    /**
     * keeps track of state for field: _batchContractSetupFK
     */
    private boolean _has_batchContractSetupFK;

    /**
     * Field _contractGroupFK
     */
    private long _contractGroupFK;

    /**
     * keeps track of state for field: _contractGroupFK
     */
    private boolean _has_contractGroupFK;

    /**
     * Field _departmentLocationFK
     */
    private long _departmentLocationFK;

    /**
     * keeps track of state for field: _departmentLocationFK
     */
    private boolean _has_departmentLocationFK;

    /**
     * Field _priorContractGroupFK
     */
    private long _priorContractGroupFK;

    /**
     * keeps track of state for field: _priorContractGroupFK
     */
    private boolean _has_priorContractGroupFK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;

    /**
     * Field _qualNonQualCT
     */
    private java.lang.String _qualNonQualCT;

    /**
     * Field _exchangeInd
     */
    private java.lang.String _exchangeInd;

    /**
     * Field _costBasis
     */
    private java.math.BigDecimal _costBasis;

    /**
     * Field _recoveredCostBasis
     */
    private java.math.BigDecimal _recoveredCostBasis;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;
    
    /**
     * Field _expiryDate
     */
    private java.lang.String _expiryDate;

    /**
     * Field _location
     */
    private String _location;
    
    /**
     * Field _sequence
     */
    private String _sequence;

    /**
     * Field _indivAnnPremium
     */
    private java.math.BigDecimal _indivAnnPremium;
    
    /**
     * Field _statusChangeDate
     */
    private java.lang.String _statusChangeDate;

    /**
     * Field _segmentNameCT
     */
    private java.lang.String _segmentNameCT;

    /**
     * Field _segmentStatusCT
     */
    private java.lang.String _segmentStatusCT;

    /**
     * Field _optionCodeCT
     */
    private java.lang.String _optionCodeCT;

    /**
     * Field _issueStateCT
     */
    private java.lang.String _issueStateCT;

    /**
     * Field _originalStateCT
     */
    private java.lang.String _originalStateCT;

    /**
     * Field _issueStateORInd
     */
    private java.lang.String _issueStateORInd;

    /**
     * Field _qualifiedTypeCT
     */
    private java.lang.String _qualifiedTypeCT;

    /**
     * Field _quoteDate
     */
    private java.lang.String _quoteDate;

    /**
     * Field _charges
     */
    private java.math.BigDecimal _charges;

    /**
     * Field _loads
     */
    private java.math.BigDecimal _loads;

    /**
     * Field _fees
     */
    private java.math.BigDecimal _fees;

    /**
     * Field _taxReportingGroup
     */
    private java.lang.String _taxReportingGroup;

    /**
     * Field _issueDate
     */
    private java.lang.String _issueDate;

    /**
     * Field _cashWithAppInd
     */
    private java.lang.String _cashWithAppInd;

    /**
     * Field _waiverInEffect
     */
    private java.lang.String _waiverInEffect;

    /**
     * Field _freeAmountRemaining
     */
    private java.math.BigDecimal _freeAmountRemaining;

    /**
     * Field _freeAmount
     */
    private java.math.BigDecimal _freeAmount;

    /**
     * Field _dateInEffect
     */
    private java.lang.String _dateInEffect;

    /**
     * Field _creationOperator
     */
    private java.lang.String _creationOperator;

    /**
     * Field _creationDate
     */
    private java.lang.String _creationDate;

    /**
     * Field _lastAnniversaryDate
     */
    private java.lang.String _lastAnniversaryDate;

    /**
     * Field _applicationSignedDate
     */
    private java.lang.String _applicationSignedDate;

    /**
     * Field _applicationReceivedDate
     */
    private java.lang.String _applicationReceivedDate;

    /**
     * Field _savingsPercent
     */
    private java.math.BigDecimal _savingsPercent;

    /**
     * Field _annualInsuranceAmount
     */
    private java.math.BigDecimal _annualInsuranceAmount;

    /**
     * Field _annualInvestmentAmount
     */
    private java.math.BigDecimal _annualInvestmentAmount;

    /**
     * Field _dismembermentPercent
     */
    private java.math.BigDecimal _dismembermentPercent;

    /**
     * Field _policyDeliveryDate
     */
    private java.lang.String _policyDeliveryDate;

    /**
     * Field _waiveFreeLookIndicator
     */
    private java.lang.String _waiveFreeLookIndicator;

    /**
     * Field _freeLookDaysOverride
     */
    private int _freeLookDaysOverride;

    /**
     * keeps track of state for field: _freeLookDaysOverride
     */
    private boolean _has_freeLookDaysOverride;

    /**
     * Field _freeLookEndDate
     */
    private java.lang.String _freeLookEndDate;

    /**
     * Field _segmentChangeEffectiveDate
     */
    private java.lang.String _segmentChangeEffectiveDate;

    /**
     * Field _pointInScaleIndicator
     */
    private java.lang.String _pointInScaleIndicator;

    /**
     * Field _chargeDeductDivisionInd
     */
    private java.lang.String _chargeDeductDivisionInd;

    /**
     * Field _dialableSalesLoadPercentage
     */
    private java.math.BigDecimal _dialableSalesLoadPercentage;

    /**
     * Field _chargeDeductAmount
     */
    private java.math.BigDecimal _chargeDeductAmount;

    /**
     * Field _riderNumber
     */
    private int _riderNumber;

    /**
     * keeps track of state for field: _riderNumber
     */
    private boolean _has_riderNumber;

    /**
     * Field _commitmentIndicator
     */
    private java.lang.String _commitmentIndicator;

    /**
     * Field _commitmentAmount
     */
    private java.math.BigDecimal _commitmentAmount;

    /**
     * Field _chargeCodeStatus
     */
    private java.lang.String _chargeCodeStatus;

    /**
     * Field _dateOfDeathValue
     */
    private java.math.BigDecimal _dateOfDeathValue;

    /**
     * Field _suppOriginalContractNumber
     */
    private java.lang.String _suppOriginalContractNumber;

    /**
     * Field _openClaimEndDate
     */
    private java.lang.String _openClaimEndDate;

    /**
     * Field _annuitizationValue
     */
    private java.math.BigDecimal _annuitizationValue;

    /**
     * Field _ROTHConvInd
     */
    private java.lang.String _ROTHConvInd;

    /**
     * Field _priorROTHCostBasis
     */
    private java.math.BigDecimal _priorROTHCostBasis;

    /**
     * Field _casetrackingOptionCT
     */
    private java.lang.String _casetrackingOptionCT;

    /**
     * Field _printLine1
     */
    private java.lang.String _printLine1;

    /**
     * Field _printLine2
     */
    private java.lang.String _printLine2;

    /**
     * Field _totalActiveBeneficiaries
     */
    private int _totalActiveBeneficiaries;

    /**
     * keeps track of state for field: _totalActiveBeneficiaries
     */
    private boolean _has_totalActiveBeneficiaries;

    /**
     * Field _remainingBeneficiaries
     */
    private int _remainingBeneficiaries;

    /**
     * keeps track of state for field: _remainingBeneficiaries
     */
    private boolean _has_remainingBeneficiaries;

    /**
     * Field _totalFaceAmount
     */
    private java.math.BigDecimal _totalFaceAmount;

    /**
     * Field _settlementAmount
     */
    private java.math.BigDecimal _settlementAmount;

    /**
     * Field _lastSettlementValDate
     */
    private java.lang.String _lastSettlementValDate;

    /**
     * Field _contractTypeCT
     */
    private java.lang.String _contractTypeCT;

    /**
     * Field _memberOfContractGroup
     */
    private java.lang.String _memberOfContractGroup;

    /**
     * Field _applicationNumber
     */
    private java.lang.String _applicationNumber;

    /**
     * Field _applicationSignedStateCT
     */
    private java.lang.String _applicationSignedStateCT;

    /**
     * Field _units
     */
    private java.math.BigDecimal _units;

    /**
     * Field _annualPremium
     */
    private java.math.BigDecimal _annualPremium;

    /**
     * Field _commissionPhaseID
     */
    private int _commissionPhaseID;

    /**
     * keeps track of state for field: _commissionPhaseID
     */
    private boolean _has_commissionPhaseID;

    /**
     * Field _commissionPhaseOverride
     */
    private java.lang.String _commissionPhaseOverride;

    /**
     * Field _authorizedSignatureCT
     */
    private java.lang.String _authorizedSignatureCT;

    /**
     * Field _ratedGenderCT
     */
    private java.lang.String _ratedGenderCT;

    /**
     * Field _groupPlan
     */
    private java.lang.String _groupPlan;

    /**
     * Field _underwritingClassCT
     */
    private java.lang.String _underwritingClassCT;

    /**
     * Field _ageAtIssue
     */
    private int _ageAtIssue;

    /**
     * keeps track of state for field: _ageAtIssue
     */
    private boolean _has_ageAtIssue;

    /**
     * Field _originalUnits
     */
    private java.math.BigDecimal _originalUnits;

    /**
     * Field _consecutiveAPLCount
     */
    private int _consecutiveAPLCount;

    /**
     * keeps track of state for field: _consecutiveAPLCount
     */
    private boolean _has_consecutiveAPLCount;

    /**
     * Field _firstNotifyDate
     */
    private java.lang.String _firstNotifyDate;

    /**
     * Field _previousNotifyDate
     */
    private java.lang.String _previousNotifyDate;

    /**
     * Field _finalNotifyDate
     */
    private java.lang.String _finalNotifyDate;

    /**
     * Field _advanceFinalNotify
     */
    private java.lang.String _advanceFinalNotify;

    /**
     * Field _postIssueStatusCT
     */
    private java.lang.String _postIssueStatusCT;

    /**
     * Field _priorPRDDue
     */
    private java.lang.String _priorPRDDue;

    /**
     * Field _conversionDate
     */
    private java.lang.String _conversionDate;

    /**
     * Field _clientUpdate
     */
    private java.lang.String _clientUpdate;

    /**
     * Field _worksheetTypeCT
     */
    private java.lang.String _worksheetTypeCT;

    /**
     * Field _estateOfTheInsured
     */
    private java.lang.String _estateOfTheInsured;

    /**
     * Field _requirementEffectiveDate
     */
    private java.lang.String _requirementEffectiveDate;

    /**
     * Field _referenceID
     */
    private java.lang.String _referenceID;

    /**
     * Field _billScheduleChangeType
     */
    private java.lang.String _billScheduleChangeType;

    /**
     * Field _masterContractFK
     */
    private long _masterContractFK;

    /**
     * keeps track of state for field: _masterContractFK
     */
    private boolean _has_masterContractFK;

    /**
     * Field _dividendOptionCT
     */
    private java.lang.String _dividendOptionCT;

    /**
     * Field _originalContractGroupFK
     */
    private long _originalContractGroupFK;

    /**
     * keeps track of state for field: _originalContractGroupFK
     */
    private boolean _has_originalContractGroupFK;

    /**
     * Field _scheduledTerminationDate
     */
    private java.lang.String _scheduledTerminationDate;

    /**
     * Field _claimStopDate
     */
    private java.lang.String _claimStopDate;

    /**
     * Field _EOBMultiple
     */
    private int _EOBMultiple;

    /**
     * keeps track of state for field: _EOBMultiple
     */
    private boolean _has_EOBMultiple;

    /**
     * Field _EOBMaximum
     */
    private java.math.BigDecimal _EOBMaximum;

    /**
     * Field _EOBCum
     */
    private java.math.BigDecimal _EOBCum;

    /**
     * Field _premiumTaxSitusOverrideCT
     */
    private java.lang.String _premiumTaxSitusOverrideCT;

    /**
     * Field _GIOOption
     */
    private java.lang.String _GIOOption;

    /**
     * Field _deductionAmountOverride
     */
    private java.math.BigDecimal _deductionAmountOverride;

    /**
     * Field _deductionAmountEffectiveDate
     */
    private java.lang.String _deductionAmountEffectiveDate;

    /**
     * Field _agentHierarchyVOList
     */
    private java.util.Vector _agentHierarchyVOList;

    /**
     * Field _contractClientVOList
     */
    private java.util.Vector _contractClientVOList;

    /**
     * Field _contractRequirementVOList
     */
    private java.util.Vector _contractRequirementVOList;

    /**
     * Field _contractTreatyVOList
     */
    private java.util.Vector _contractTreatyVOList;

    /**
     * Field _depositsVOList
     */
    private java.util.Vector _depositsVOList;

    /**
     * Field _inherentRiderVOList
     */
    private java.util.Vector _inherentRiderVOList;

    /**
     * Field _investmentVOList
     */
    private java.util.Vector _investmentVOList;

    /**
     * Field _lifeVOList
     */
    private java.util.Vector _lifeVOList;

    /**
     * Field _noteReminderVOList
     */
    private java.util.Vector _noteReminderVOList;

    /**
     * Field _payoutVOList
     */
    private java.util.Vector _payoutVOList;

    /**
     * Field _realTimeActivityVOList
     */
    private java.util.Vector _realTimeActivityVOList;

    /**
     * Field _requiredMinDistributionVOList
     */
    private java.util.Vector _requiredMinDistributionVOList;

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;

    /**
     * Field _segmentBackupVOList
     */
    private java.util.Vector _segmentBackupVOList;

    /**
     * Field _contractSetupVOList
     */
    private java.util.Vector _contractSetupVOList;

    /**
     * Field _premiumDueVOList
     */
    private java.util.Vector _premiumDueVOList;

    /**
     * Field _billVOList
     */
    private java.util.Vector _billVOList;

    /**
     * Field _valueAtIssueVOList
     */
    private java.util.Vector _valueAtIssueVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SegmentVO() {
        super();
        _agentHierarchyVOList = new Vector();
        _contractClientVOList = new Vector();
        _contractRequirementVOList = new Vector();
        _contractTreatyVOList = new Vector();
        _depositsVOList = new Vector();
        _inherentRiderVOList = new Vector();
        _investmentVOList = new Vector();
        _lifeVOList = new Vector();
        _noteReminderVOList = new Vector();
        _payoutVOList = new Vector();
        _realTimeActivityVOList = new Vector();
        _requiredMinDistributionVOList = new Vector();
        _segmentVOList = new Vector();
        _segmentBackupVOList = new Vector();
        _contractSetupVOList = new Vector();
        _premiumDueVOList = new Vector();
        _billVOList = new Vector();
        _valueAtIssueVOList = new Vector();
    } //-- edit.common.vo.SegmentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentHierarchyVO
     * 
     * @param vAgentHierarchyVO
     */
    public void addAgentHierarchyVO(edit.common.vo.AgentHierarchyVO vAgentHierarchyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentHierarchyVO.setParentVO(this.getClass(), this);
        _agentHierarchyVOList.addElement(vAgentHierarchyVO);
    } //-- void addAgentHierarchyVO(edit.common.vo.AgentHierarchyVO) 

    /**
     * Method addAgentHierarchyVO
     * 
     * @param index
     * @param vAgentHierarchyVO
     */
    public void addAgentHierarchyVO(int index, edit.common.vo.AgentHierarchyVO vAgentHierarchyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentHierarchyVO.setParentVO(this.getClass(), this);
        _agentHierarchyVOList.insertElementAt(vAgentHierarchyVO, index);
    } //-- void addAgentHierarchyVO(int, edit.common.vo.AgentHierarchyVO) 

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
     * Method addContractClientVO
     * 
     * @param vContractClientVO
     */
    public void addContractClientVO(edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.addElement(vContractClientVO);
    } //-- void addContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method addContractClientVO
     * 
     * @param index
     * @param vContractClientVO
     */
    public void addContractClientVO(int index, edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.insertElementAt(vContractClientVO, index);
    } //-- void addContractClientVO(int, edit.common.vo.ContractClientVO) 

    /**
     * Method addContractRequirementVO
     * 
     * @param vContractRequirementVO
     */
    public void addContractRequirementVO(edit.common.vo.ContractRequirementVO vContractRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractRequirementVO.setParentVO(this.getClass(), this);
        _contractRequirementVOList.addElement(vContractRequirementVO);
    } //-- void addContractRequirementVO(edit.common.vo.ContractRequirementVO) 

    /**
     * Method addContractRequirementVO
     * 
     * @param index
     * @param vContractRequirementVO
     */
    public void addContractRequirementVO(int index, edit.common.vo.ContractRequirementVO vContractRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractRequirementVO.setParentVO(this.getClass(), this);
        _contractRequirementVOList.insertElementAt(vContractRequirementVO, index);
    } //-- void addContractRequirementVO(int, edit.common.vo.ContractRequirementVO) 

    /**
     * Method addContractSetupVO
     * 
     * @param vContractSetupVO
     */
    public void addContractSetupVO(edit.common.vo.ContractSetupVO vContractSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractSetupVO.setParentVO(this.getClass(), this);
        _contractSetupVOList.addElement(vContractSetupVO);
    } //-- void addContractSetupVO(edit.common.vo.ContractSetupVO) 

    /**
     * Method addContractSetupVO
     * 
     * @param index
     * @param vContractSetupVO
     */
    public void addContractSetupVO(int index, edit.common.vo.ContractSetupVO vContractSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractSetupVO.setParentVO(this.getClass(), this);
        _contractSetupVOList.insertElementAt(vContractSetupVO, index);
    } //-- void addContractSetupVO(int, edit.common.vo.ContractSetupVO) 

    /**
     * Method addContractTreatyVO
     * 
     * @param vContractTreatyVO
     */
    public void addContractTreatyVO(edit.common.vo.ContractTreatyVO vContractTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractTreatyVO.setParentVO(this.getClass(), this);
        _contractTreatyVOList.addElement(vContractTreatyVO);
    } //-- void addContractTreatyVO(edit.common.vo.ContractTreatyVO) 

    /**
     * Method addContractTreatyVO
     * 
     * @param index
     * @param vContractTreatyVO
     */
    public void addContractTreatyVO(int index, edit.common.vo.ContractTreatyVO vContractTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractTreatyVO.setParentVO(this.getClass(), this);
        _contractTreatyVOList.insertElementAt(vContractTreatyVO, index);
    } //-- void addContractTreatyVO(int, edit.common.vo.ContractTreatyVO) 

    /**
     * Method addDepositsVO
     * 
     * @param vDepositsVO
     */
    public void addDepositsVO(edit.common.vo.DepositsVO vDepositsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vDepositsVO.setParentVO(this.getClass(), this);
        _depositsVOList.addElement(vDepositsVO);
    } //-- void addDepositsVO(edit.common.vo.DepositsVO) 

    /**
     * Method addDepositsVO
     * 
     * @param index
     * @param vDepositsVO
     */
    public void addDepositsVO(int index, edit.common.vo.DepositsVO vDepositsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vDepositsVO.setParentVO(this.getClass(), this);
        _depositsVOList.insertElementAt(vDepositsVO, index);
    } //-- void addDepositsVO(int, edit.common.vo.DepositsVO) 

    /**
     * Method addInherentRiderVO
     * 
     * @param vInherentRiderVO
     */
    public void addInherentRiderVO(edit.common.vo.InherentRiderVO vInherentRiderVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInherentRiderVO.setParentVO(this.getClass(), this);
        _inherentRiderVOList.addElement(vInherentRiderVO);
    } //-- void addInherentRiderVO(edit.common.vo.InherentRiderVO) 

    /**
     * Method addInherentRiderVO
     * 
     * @param index
     * @param vInherentRiderVO
     */
    public void addInherentRiderVO(int index, edit.common.vo.InherentRiderVO vInherentRiderVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInherentRiderVO.setParentVO(this.getClass(), this);
        _inherentRiderVOList.insertElementAt(vInherentRiderVO, index);
    } //-- void addInherentRiderVO(int, edit.common.vo.InherentRiderVO) 

    /**
     * Method addInvestmentVO
     * 
     * @param vInvestmentVO
     */
    public void addInvestmentVO(edit.common.vo.InvestmentVO vInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentVO.setParentVO(this.getClass(), this);
        _investmentVOList.addElement(vInvestmentVO);
    } //-- void addInvestmentVO(edit.common.vo.InvestmentVO) 

    /**
     * Method addInvestmentVO
     * 
     * @param index
     * @param vInvestmentVO
     */
    public void addInvestmentVO(int index, edit.common.vo.InvestmentVO vInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentVO.setParentVO(this.getClass(), this);
        _investmentVOList.insertElementAt(vInvestmentVO, index);
    } //-- void addInvestmentVO(int, edit.common.vo.InvestmentVO) 

    /**
     * Method addLifeVO
     * 
     * @param vLifeVO
     */
    public void addLifeVO(edit.common.vo.LifeVO vLifeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLifeVO.setParentVO(this.getClass(), this);
        _lifeVOList.addElement(vLifeVO);
    } //-- void addLifeVO(edit.common.vo.LifeVO) 

    /**
     * Method addLifeVO
     * 
     * @param index
     * @param vLifeVO
     */
    public void addLifeVO(int index, edit.common.vo.LifeVO vLifeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLifeVO.setParentVO(this.getClass(), this);
        _lifeVOList.insertElementAt(vLifeVO, index);
    } //-- void addLifeVO(int, edit.common.vo.LifeVO) 

    /**
     * Method addNoteReminderVO
     * 
     * @param vNoteReminderVO
     */
    public void addNoteReminderVO(edit.common.vo.NoteReminderVO vNoteReminderVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vNoteReminderVO.setParentVO(this.getClass(), this);
        _noteReminderVOList.addElement(vNoteReminderVO);
    } //-- void addNoteReminderVO(edit.common.vo.NoteReminderVO) 

    /**
     * Method addNoteReminderVO
     * 
     * @param index
     * @param vNoteReminderVO
     */
    public void addNoteReminderVO(int index, edit.common.vo.NoteReminderVO vNoteReminderVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vNoteReminderVO.setParentVO(this.getClass(), this);
        _noteReminderVOList.insertElementAt(vNoteReminderVO, index);
    } //-- void addNoteReminderVO(int, edit.common.vo.NoteReminderVO) 

    /**
     * Method addPayoutVO
     * 
     * @param vPayoutVO
     */
    public void addPayoutVO(edit.common.vo.PayoutVO vPayoutVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPayoutVO.setParentVO(this.getClass(), this);
        _payoutVOList.addElement(vPayoutVO);
    } //-- void addPayoutVO(edit.common.vo.PayoutVO) 

    /**
     * Method addPayoutVO
     * 
     * @param index
     * @param vPayoutVO
     */
    public void addPayoutVO(int index, edit.common.vo.PayoutVO vPayoutVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPayoutVO.setParentVO(this.getClass(), this);
        _payoutVOList.insertElementAt(vPayoutVO, index);
    } //-- void addPayoutVO(int, edit.common.vo.PayoutVO) 

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
     * Method addRealTimeActivityVO
     * 
     * @param vRealTimeActivityVO
     */
    public void addRealTimeActivityVO(edit.common.vo.RealTimeActivityVO vRealTimeActivityVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRealTimeActivityVO.setParentVO(this.getClass(), this);
        _realTimeActivityVOList.addElement(vRealTimeActivityVO);
    } //-- void addRealTimeActivityVO(edit.common.vo.RealTimeActivityVO) 

    /**
     * Method addRealTimeActivityVO
     * 
     * @param index
     * @param vRealTimeActivityVO
     */
    public void addRealTimeActivityVO(int index, edit.common.vo.RealTimeActivityVO vRealTimeActivityVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRealTimeActivityVO.setParentVO(this.getClass(), this);
        _realTimeActivityVOList.insertElementAt(vRealTimeActivityVO, index);
    } //-- void addRealTimeActivityVO(int, edit.common.vo.RealTimeActivityVO) 

    /**
     * Method addRequiredMinDistributionVO
     * 
     * @param vRequiredMinDistributionVO
     */
    public void addRequiredMinDistributionVO(edit.common.vo.RequiredMinDistributionVO vRequiredMinDistributionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRequiredMinDistributionVO.setParentVO(this.getClass(), this);
        _requiredMinDistributionVOList.addElement(vRequiredMinDistributionVO);
    } //-- void addRequiredMinDistributionVO(edit.common.vo.RequiredMinDistributionVO) 

    /**
     * Method addRequiredMinDistributionVO
     * 
     * @param index
     * @param vRequiredMinDistributionVO
     */
    public void addRequiredMinDistributionVO(int index, edit.common.vo.RequiredMinDistributionVO vRequiredMinDistributionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRequiredMinDistributionVO.setParentVO(this.getClass(), this);
        _requiredMinDistributionVOList.insertElementAt(vRequiredMinDistributionVO, index);
    } //-- void addRequiredMinDistributionVO(int, edit.common.vo.RequiredMinDistributionVO) 

    /**
     * Method addSegmentBackupVO
     * 
     * @param vSegmentBackupVO
     */
    public void addSegmentBackupVO(edit.common.vo.SegmentBackupVO vSegmentBackupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentBackupVO.setParentVO(this.getClass(), this);
        _segmentBackupVOList.addElement(vSegmentBackupVO);
    } //-- void addSegmentBackupVO(edit.common.vo.SegmentBackupVO) 

    /**
     * Method addSegmentBackupVO
     * 
     * @param index
     * @param vSegmentBackupVO
     */
    public void addSegmentBackupVO(int index, edit.common.vo.SegmentBackupVO vSegmentBackupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentBackupVO.setParentVO(this.getClass(), this);
        _segmentBackupVOList.insertElementAt(vSegmentBackupVO, index);
    } //-- void addSegmentBackupVO(int, edit.common.vo.SegmentBackupVO) 

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
     * Method addValueAtIssueVO
     * 
     * @param vValueAtIssueVO
     */
    public void addValueAtIssueVO(edit.common.vo.ValueAtIssueVO vValueAtIssueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vValueAtIssueVO.setParentVO(this.getClass(), this);
        _valueAtIssueVOList.addElement(vValueAtIssueVO);
    } //-- void addValueAtIssueVO(edit.common.vo.ValueAtIssueVO) 

    /**
     * Method addValueAtIssueVO
     * 
     * @param index
     * @param vValueAtIssueVO
     */
    public void addValueAtIssueVO(int index, edit.common.vo.ValueAtIssueVO vValueAtIssueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vValueAtIssueVO.setParentVO(this.getClass(), this);
        _valueAtIssueVOList.insertElementAt(vValueAtIssueVO, index);
    } //-- void addValueAtIssueVO(int, edit.common.vo.ValueAtIssueVO) 

    /**
     * Method enumerateAgentHierarchyVO
     */
    public java.util.Enumeration enumerateAgentHierarchyVO()
    {
        return _agentHierarchyVOList.elements();
    } //-- java.util.Enumeration enumerateAgentHierarchyVO() 

    /**
     * Method enumerateBillVO
     */
    public java.util.Enumeration enumerateBillVO()
    {
        return _billVOList.elements();
    } //-- java.util.Enumeration enumerateBillVO() 

    /**
     * Method enumerateContractClientVO
     */
    public java.util.Enumeration enumerateContractClientVO()
    {
        return _contractClientVOList.elements();
    } //-- java.util.Enumeration enumerateContractClientVO() 

    /**
     * Method enumerateContractRequirementVO
     */
    public java.util.Enumeration enumerateContractRequirementVO()
    {
        return _contractRequirementVOList.elements();
    } //-- java.util.Enumeration enumerateContractRequirementVO() 

    /**
     * Method enumerateContractSetupVO
     */
    public java.util.Enumeration enumerateContractSetupVO()
    {
        return _contractSetupVOList.elements();
    } //-- java.util.Enumeration enumerateContractSetupVO() 

    /**
     * Method enumerateContractTreatyVO
     */
    public java.util.Enumeration enumerateContractTreatyVO()
    {
        return _contractTreatyVOList.elements();
    } //-- java.util.Enumeration enumerateContractTreatyVO() 

    /**
     * Method enumerateDepositsVO
     */
    public java.util.Enumeration enumerateDepositsVO()
    {
        return _depositsVOList.elements();
    } //-- java.util.Enumeration enumerateDepositsVO() 

    /**
     * Method enumerateInherentRiderVO
     */
    public java.util.Enumeration enumerateInherentRiderVO()
    {
        return _inherentRiderVOList.elements();
    } //-- java.util.Enumeration enumerateInherentRiderVO() 

    /**
     * Method enumerateInvestmentVO
     */
    public java.util.Enumeration enumerateInvestmentVO()
    {
        return _investmentVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentVO() 

    /**
     * Method enumerateLifeVO
     */
    public java.util.Enumeration enumerateLifeVO()
    {
        return _lifeVOList.elements();
    } //-- java.util.Enumeration enumerateLifeVO() 

    /**
     * Method enumerateNoteReminderVO
     */
    public java.util.Enumeration enumerateNoteReminderVO()
    {
        return _noteReminderVOList.elements();
    } //-- java.util.Enumeration enumerateNoteReminderVO() 

    /**
     * Method enumeratePayoutVO
     */
    public java.util.Enumeration enumeratePayoutVO()
    {
        return _payoutVOList.elements();
    } //-- java.util.Enumeration enumeratePayoutVO() 

    /**
     * Method enumeratePremiumDueVO
     */
    public java.util.Enumeration enumeratePremiumDueVO()
    {
        return _premiumDueVOList.elements();
    } //-- java.util.Enumeration enumeratePremiumDueVO() 

    /**
     * Method enumerateRealTimeActivityVO
     */
    public java.util.Enumeration enumerateRealTimeActivityVO()
    {
        return _realTimeActivityVOList.elements();
    } //-- java.util.Enumeration enumerateRealTimeActivityVO() 

    /**
     * Method enumerateRequiredMinDistributionVO
     */
    public java.util.Enumeration enumerateRequiredMinDistributionVO()
    {
        return _requiredMinDistributionVOList.elements();
    } //-- java.util.Enumeration enumerateRequiredMinDistributionVO() 

    /**
     * Method enumerateSegmentBackupVO
     */
    public java.util.Enumeration enumerateSegmentBackupVO()
    {
        return _segmentBackupVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentBackupVO() 

    /**
     * Method enumerateSegmentVO
     */
    public java.util.Enumeration enumerateSegmentVO()
    {
        return _segmentVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentVO() 

    /**
     * Method enumerateValueAtIssueVO
     */
    public java.util.Enumeration enumerateValueAtIssueVO()
    {
        return _valueAtIssueVOList.elements();
    } //-- java.util.Enumeration enumerateValueAtIssueVO() 

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
        
        if (obj instanceof SegmentVO) {
        
            SegmentVO temp = (SegmentVO)obj;
            if (this._segmentPK != temp._segmentPK)
                return false;
            if (this._has_segmentPK != temp._has_segmentPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._billScheduleFK != temp._billScheduleFK)
                return false;
            if (this._has_billScheduleFK != temp._has_billScheduleFK)
                return false;
            if (this._batchContractSetupFK != temp._batchContractSetupFK)
                return false;
            if (this._has_batchContractSetupFK != temp._has_batchContractSetupFK)
                return false;
            if (this._contractGroupFK != temp._contractGroupFK)
                return false;
            if (this._has_contractGroupFK != temp._has_contractGroupFK)
                return false;
            if (this._departmentLocationFK != temp._departmentLocationFK)
                return false;
            if (this._has_departmentLocationFK != temp._has_departmentLocationFK)
                return false;
            if (this._priorContractGroupFK != temp._priorContractGroupFK)
                return false;
            if (this._has_priorContractGroupFK != temp._has_priorContractGroupFK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            if (this._qualNonQualCT != null) {
                if (temp._qualNonQualCT == null) return false;
                else if (!(this._qualNonQualCT.equals(temp._qualNonQualCT))) 
                    return false;
            }
            else if (temp._qualNonQualCT != null)
                return false;
            if (this._exchangeInd != null) {
                if (temp._exchangeInd == null) return false;
                else if (!(this._exchangeInd.equals(temp._exchangeInd))) 
                    return false;
            }
            else if (temp._exchangeInd != null)
                return false;
            if (this._costBasis != null) {
                if (temp._costBasis == null) return false;
                else if (!(this._costBasis.equals(temp._costBasis))) 
                    return false;
            }
            else if (temp._costBasis != null)
                return false;
            if (this._recoveredCostBasis != null) {
                if (temp._recoveredCostBasis == null) return false;
                else if (!(this._recoveredCostBasis.equals(temp._recoveredCostBasis))) 
                    return false;
            }
            else if (temp._recoveredCostBasis != null)
                return false;
            if (this._terminationDate != null) {
                if (temp._terminationDate == null) return false;
                else if (!(this._terminationDate.equals(temp._terminationDate))) 
                    return false;
            }
            else if (temp._terminationDate != null)
                return false;
            if (this._statusChangeDate != null) {
                if (temp._statusChangeDate == null) return false;
                else if (!(this._statusChangeDate.equals(temp._statusChangeDate))) 
                    return false;
            }
            else if (temp._statusChangeDate != null)
                return false;
            if (this._segmentNameCT != null) {
                if (temp._segmentNameCT == null) return false;
                else if (!(this._segmentNameCT.equals(temp._segmentNameCT))) 
                    return false;
            }
            else if (temp._segmentNameCT != null)
                return false;
            if (this._segmentStatusCT != null) {
                if (temp._segmentStatusCT == null) return false;
                else if (!(this._segmentStatusCT.equals(temp._segmentStatusCT))) 
                    return false;
            }
            else if (temp._segmentStatusCT != null)
                return false;
            if (this._optionCodeCT != null) {
                if (temp._optionCodeCT == null) return false;
                else if (!(this._optionCodeCT.equals(temp._optionCodeCT))) 
                    return false;
            }
            else if (temp._optionCodeCT != null)
                return false;

            if (this._issueStateCT != null) {
                if (temp._issueStateCT == null) return false;
                else if (!(this._issueStateCT.equals(temp._issueStateCT))) 
                    return false;
            }
            else if (temp._issueStateCT != null)
                return false;

            if (this._originalStateCT != null) {
                if (temp._originalStateCT == null) return false;
                else if (!(this._originalStateCT.equals(temp._originalStateCT))) 
                    return false;
            }
            else if (temp._originalStateCT != null)
                return false;

            if (this._issueStateORInd != null) {
                if (temp._issueStateORInd == null) return false;
                else if (!(this._issueStateORInd.equals(temp._issueStateORInd))) 
                    return false;
            }
            else if (temp._issueStateORInd != null)
                return false;
            if (this._qualifiedTypeCT != null) {
                if (temp._qualifiedTypeCT == null) return false;
                else if (!(this._qualifiedTypeCT.equals(temp._qualifiedTypeCT))) 
                    return false;
            }
            else if (temp._qualifiedTypeCT != null)
                return false;
            if (this._quoteDate != null) {
                if (temp._quoteDate == null) return false;
                else if (!(this._quoteDate.equals(temp._quoteDate))) 
                    return false;
            }
            else if (temp._quoteDate != null)
                return false;
            if (this._charges != null) {
                if (temp._charges == null) return false;
                else if (!(this._charges.equals(temp._charges))) 
                    return false;
            }
            else if (temp._charges != null)
                return false;
            if (this._loads != null) {
                if (temp._loads == null) return false;
                else if (!(this._loads.equals(temp._loads))) 
                    return false;
            }
            else if (temp._loads != null)
                return false;
            if (this._fees != null) {
                if (temp._fees == null) return false;
                else if (!(this._fees.equals(temp._fees))) 
                    return false;
            }
            else if (temp._fees != null)
                return false;
            if (this._taxReportingGroup != null) {
                if (temp._taxReportingGroup == null) return false;
                else if (!(this._taxReportingGroup.equals(temp._taxReportingGroup))) 
                    return false;
            }
            else if (temp._taxReportingGroup != null)
                return false;
            if (this._issueDate != null) {
                if (temp._issueDate == null) return false;
                else if (!(this._issueDate.equals(temp._issueDate))) 
                    return false;
            }
            else if (temp._issueDate != null)
                return false;
            if (this._cashWithAppInd != null) {
                if (temp._cashWithAppInd == null) return false;
                else if (!(this._cashWithAppInd.equals(temp._cashWithAppInd))) 
                    return false;
            }
            else if (temp._cashWithAppInd != null)
                return false;
            if (this._waiverInEffect != null) {
                if (temp._waiverInEffect == null) return false;
                else if (!(this._waiverInEffect.equals(temp._waiverInEffect))) 
                    return false;
            }
            else if (temp._waiverInEffect != null)
                return false;
            if (this._freeAmountRemaining != null) {
                if (temp._freeAmountRemaining == null) return false;
                else if (!(this._freeAmountRemaining.equals(temp._freeAmountRemaining))) 
                    return false;
            }
            else if (temp._freeAmountRemaining != null)
                return false;
            if (this._freeAmount != null) {
                if (temp._freeAmount == null) return false;
                else if (!(this._freeAmount.equals(temp._freeAmount))) 
                    return false;
            }
            else if (temp._freeAmount != null)
                return false;
            if (this._dateInEffect != null) {
                if (temp._dateInEffect == null) return false;
                else if (!(this._dateInEffect.equals(temp._dateInEffect))) 
                    return false;
            }
            else if (temp._dateInEffect != null)
                return false;
            if (this._creationOperator != null) {
                if (temp._creationOperator == null) return false;
                else if (!(this._creationOperator.equals(temp._creationOperator))) 
                    return false;
            }
            else if (temp._creationOperator != null)
                return false;
            if (this._creationDate != null) {
                if (temp._creationDate == null) return false;
                else if (!(this._creationDate.equals(temp._creationDate))) 
                    return false;
            }
            else if (temp._creationDate != null)
                return false;
            if (this._lastAnniversaryDate != null) {
                if (temp._lastAnniversaryDate == null) return false;
                else if (!(this._lastAnniversaryDate.equals(temp._lastAnniversaryDate))) 
                    return false;
            }
            else if (temp._lastAnniversaryDate != null)
                return false;
            if (this._applicationSignedDate != null) {
                if (temp._applicationSignedDate == null) return false;
                else if (!(this._applicationSignedDate.equals(temp._applicationSignedDate))) 
                    return false;
            }
            else if (temp._applicationSignedDate != null)
                return false;
            if (this._applicationReceivedDate != null) {
                if (temp._applicationReceivedDate == null) return false;
                else if (!(this._applicationReceivedDate.equals(temp._applicationReceivedDate))) 
                    return false;
            }
            else if (temp._applicationReceivedDate != null)
                return false;
            if (this._savingsPercent != null) {
                if (temp._savingsPercent == null) return false;
                else if (!(this._savingsPercent.equals(temp._savingsPercent))) 
                    return false;
            }
            else if (temp._savingsPercent != null)
                return false;
            if (this._annualInsuranceAmount != null) {
                if (temp._annualInsuranceAmount == null) return false;
                else if (!(this._annualInsuranceAmount.equals(temp._annualInsuranceAmount))) 
                    return false;
            }
            else if (temp._annualInsuranceAmount != null)
                return false;
            if (this._annualInvestmentAmount != null) {
                if (temp._annualInvestmentAmount == null) return false;
                else if (!(this._annualInvestmentAmount.equals(temp._annualInvestmentAmount))) 
                    return false;
            }
            else if (temp._annualInvestmentAmount != null)
                return false;
            if (this._dismembermentPercent != null) {
                if (temp._dismembermentPercent == null) return false;
                else if (!(this._dismembermentPercent.equals(temp._dismembermentPercent))) 
                    return false;
            }
            else if (temp._dismembermentPercent != null)
                return false;
            if (this._policyDeliveryDate != null) {
                if (temp._policyDeliveryDate == null) return false;
                else if (!(this._policyDeliveryDate.equals(temp._policyDeliveryDate))) 
                    return false;
            }
            else if (temp._policyDeliveryDate != null)
                return false;
            if (this._waiveFreeLookIndicator != null) {
                if (temp._waiveFreeLookIndicator == null) return false;
                else if (!(this._waiveFreeLookIndicator.equals(temp._waiveFreeLookIndicator))) 
                    return false;
            }
            else if (temp._waiveFreeLookIndicator != null)
                return false;
            
            if (this._freeLookDaysOverride != temp._freeLookDaysOverride)
                return false;
            if (this._has_freeLookDaysOverride != temp._has_freeLookDaysOverride)
                return false;

            if (this._freeLookEndDate != null) {
                if (temp._freeLookEndDate == null) return false;
                else if (!(this._freeLookEndDate.equals(temp._freeLookEndDate))) 
                    return false;
            }
            else if (temp._freeLookEndDate != null)
                return false;

            if (this._segmentChangeEffectiveDate != null) {
                if (temp._segmentChangeEffectiveDate == null) return false;
                else if (!(this._segmentChangeEffectiveDate.equals(temp._segmentChangeEffectiveDate))) 
                    return false;
            }
            else if (temp._segmentChangeEffectiveDate != null)
                return false;

            if (this._pointInScaleIndicator != null) {
                if (temp._pointInScaleIndicator == null) return false;
                else if (!(this._pointInScaleIndicator.equals(temp._pointInScaleIndicator))) 
                    return false;
            }
            else if (temp._pointInScaleIndicator != null)
                return false;
            if (this._chargeDeductDivisionInd != null) {
                if (temp._chargeDeductDivisionInd == null) return false;
                else if (!(this._chargeDeductDivisionInd.equals(temp._chargeDeductDivisionInd))) 
                    return false;
            }
            else if (temp._chargeDeductDivisionInd != null)
                return false;
            if (this._dialableSalesLoadPercentage != null) {
                if (temp._dialableSalesLoadPercentage == null) return false;
                else if (!(this._dialableSalesLoadPercentage.equals(temp._dialableSalesLoadPercentage))) 
                    return false;
            }
            else if (temp._dialableSalesLoadPercentage != null)
                return false;
            if (this._chargeDeductAmount != null) {
                if (temp._chargeDeductAmount == null) return false;
                else if (!(this._chargeDeductAmount.equals(temp._chargeDeductAmount))) 
                    return false;
            }
            else if (temp._chargeDeductAmount != null)
                return false;
            if (this._riderNumber != temp._riderNumber)
                return false;
            if (this._has_riderNumber != temp._has_riderNumber)
                return false;
            if (this._commitmentIndicator != null) {
                if (temp._commitmentIndicator == null) return false;
                else if (!(this._commitmentIndicator.equals(temp._commitmentIndicator))) 
                    return false;
            }
            else if (temp._commitmentIndicator != null)
                return false;
            if (this._commitmentAmount != null) {
                if (temp._commitmentAmount == null) return false;
                else if (!(this._commitmentAmount.equals(temp._commitmentAmount))) 
                    return false;
            }
            else if (temp._commitmentAmount != null)
                return false;
            if (this._chargeCodeStatus != null) {
                if (temp._chargeCodeStatus == null) return false;
                else if (!(this._chargeCodeStatus.equals(temp._chargeCodeStatus))) 
                    return false;
            }
            else if (temp._chargeCodeStatus != null)
                return false;
            if (this._dateOfDeathValue != null) {
                if (temp._dateOfDeathValue == null) return false;
                else if (!(this._dateOfDeathValue.equals(temp._dateOfDeathValue))) 
                    return false;
            }
            else if (temp._dateOfDeathValue != null)
                return false;
            if (this._suppOriginalContractNumber != null) {
                if (temp._suppOriginalContractNumber == null) return false;
                else if (!(this._suppOriginalContractNumber.equals(temp._suppOriginalContractNumber))) 
                    return false;
            }
            else if (temp._suppOriginalContractNumber != null)
                return false;
            if (this._openClaimEndDate != null) {
                if (temp._openClaimEndDate == null) return false;
                else if (!(this._openClaimEndDate.equals(temp._openClaimEndDate))) 
                    return false;
            }
            else if (temp._openClaimEndDate != null)
                return false;
            if (this._annuitizationValue != null) {
                if (temp._annuitizationValue == null) return false;
                else if (!(this._annuitizationValue.equals(temp._annuitizationValue))) 
                    return false;
            }
            else if (temp._annuitizationValue != null)
                return false;
            if (this._ROTHConvInd != null) {
                if (temp._ROTHConvInd == null) return false;
                else if (!(this._ROTHConvInd.equals(temp._ROTHConvInd))) 
                    return false;
            }
            else if (temp._ROTHConvInd != null)
                return false;
            if (this._priorROTHCostBasis != null) {
                if (temp._priorROTHCostBasis == null) return false;
                else if (!(this._priorROTHCostBasis.equals(temp._priorROTHCostBasis))) 
                    return false;
            }
            else if (temp._priorROTHCostBasis != null)
                return false;
            if (this._casetrackingOptionCT != null) {
                if (temp._casetrackingOptionCT == null) return false;
                else if (!(this._casetrackingOptionCT.equals(temp._casetrackingOptionCT))) 
                    return false;
            }
            else if (temp._casetrackingOptionCT != null)
                return false;
            if (this._printLine1 != null) {
                if (temp._printLine1 == null) return false;
                else if (!(this._printLine1.equals(temp._printLine1))) 
                    return false;
            }
            else if (temp._printLine1 != null)
                return false;
            if (this._printLine2 != null) {
                if (temp._printLine2 == null) return false;
                else if (!(this._printLine2.equals(temp._printLine2))) 
                    return false;
            }
            else if (temp._printLine2 != null)
                return false;
            if (this._totalActiveBeneficiaries != temp._totalActiveBeneficiaries)
                return false;
            if (this._has_totalActiveBeneficiaries != temp._has_totalActiveBeneficiaries)
                return false;
            if (this._remainingBeneficiaries != temp._remainingBeneficiaries)
                return false;
            if (this._has_remainingBeneficiaries != temp._has_remainingBeneficiaries)
                return false;
            if (this._totalFaceAmount != null) {
                if (temp._totalFaceAmount == null) return false;
                else if (!(this._totalFaceAmount.equals(temp._totalFaceAmount))) 
                    return false;
            }
            else if (temp._totalFaceAmount != null)
                return false;
            if (this._settlementAmount != null) {
                if (temp._settlementAmount == null) return false;
                else if (!(this._settlementAmount.equals(temp._settlementAmount))) 
                    return false;
            }
            else if (temp._settlementAmount != null)
                return false;
            if (this._lastSettlementValDate != null) {
                if (temp._lastSettlementValDate == null) return false;
                else if (!(this._lastSettlementValDate.equals(temp._lastSettlementValDate))) 
                    return false;
            }
            else if (temp._lastSettlementValDate != null)
                return false;
            if (this._contractTypeCT != null) {
                if (temp._contractTypeCT == null) return false;
                else if (!(this._contractTypeCT.equals(temp._contractTypeCT))) 
                    return false;
            }
            else if (temp._contractTypeCT != null)
                return false;
            if (this._memberOfContractGroup != null) {
                if (temp._memberOfContractGroup == null) return false;
                else if (!(this._memberOfContractGroup.equals(temp._memberOfContractGroup))) 
                    return false;
            }
            else if (temp._memberOfContractGroup != null)
                return false;
            if (this._applicationNumber != null) {
                if (temp._applicationNumber == null) return false;
                else if (!(this._applicationNumber.equals(temp._applicationNumber))) 
                    return false;
            }
            else if (temp._applicationNumber != null)
                return false;
            if (this._applicationSignedStateCT != null) {
                if (temp._applicationSignedStateCT == null) return false;
                else if (!(this._applicationSignedStateCT.equals(temp._applicationSignedStateCT))) 
                    return false;
            }
            else if (temp._applicationSignedStateCT != null)
                return false;
            if (this._units != null) {
                if (temp._units == null) return false;
                else if (!(this._units.equals(temp._units))) 
                    return false;
            }
            else if (temp._units != null)
                return false;
            if (this._annualPremium != null) {
                if (temp._annualPremium == null) return false;
                else if (!(this._annualPremium.equals(temp._annualPremium))) 
                    return false;
            }
            else if (temp._annualPremium != null)
                return false;
            if (this._commissionPhaseID != temp._commissionPhaseID)
                return false;
            if (this._has_commissionPhaseID != temp._has_commissionPhaseID)
                return false;
            if (this._commissionPhaseOverride != null) {
                if (temp._commissionPhaseOverride == null) return false;
                else if (!(this._commissionPhaseOverride.equals(temp._commissionPhaseOverride))) 
                    return false;
            }
            else if (temp._commissionPhaseOverride != null)
                return false;
            if (this._authorizedSignatureCT != null) {
                if (temp._authorizedSignatureCT == null) return false;
                else if (!(this._authorizedSignatureCT.equals(temp._authorizedSignatureCT))) 
                    return false;
            }
            else if (temp._authorizedSignatureCT != null)
                return false;
            if (this._ratedGenderCT != null) {
                if (temp._ratedGenderCT == null) return false;
                else if (!(this._ratedGenderCT.equals(temp._ratedGenderCT))) 
                    return false;
            }
            else if (temp._ratedGenderCT != null)
                return false;
            if (this._groupPlan != null) {
                if (temp._groupPlan == null) return false;
                else if (!(this._groupPlan.equals(temp._groupPlan))) 
                    return false;
            }
            else if (temp._groupPlan != null)
                return false;
            if (this._underwritingClassCT != null) {
                if (temp._underwritingClassCT == null) return false;
                else if (!(this._underwritingClassCT.equals(temp._underwritingClassCT))) 
                    return false;
            }
            else if (temp._underwritingClassCT != null)
                return false;
            if (this._ageAtIssue != temp._ageAtIssue)
                return false;
            if (this._has_ageAtIssue != temp._has_ageAtIssue)
                return false;
            if (this._originalUnits != null) {
                if (temp._originalUnits == null) return false;
                else if (!(this._originalUnits.equals(temp._originalUnits))) 
                    return false;
            }
            else if (temp._originalUnits != null)
                return false;
            if (this._consecutiveAPLCount != temp._consecutiveAPLCount)
                return false;
            if (this._has_consecutiveAPLCount != temp._has_consecutiveAPLCount)
                return false;
            if (this._firstNotifyDate != null) {
                if (temp._firstNotifyDate == null) return false;
                else if (!(this._firstNotifyDate.equals(temp._firstNotifyDate))) 
                    return false;
            }
            else if (temp._firstNotifyDate != null)
                return false;
            if (this._previousNotifyDate != null) {
                if (temp._previousNotifyDate == null) return false;
                else if (!(this._previousNotifyDate.equals(temp._previousNotifyDate))) 
                    return false;
            }
            else if (temp._previousNotifyDate != null)
                return false;
            if (this._finalNotifyDate != null) {
                if (temp._finalNotifyDate == null) return false;
                else if (!(this._finalNotifyDate.equals(temp._finalNotifyDate))) 
                    return false;
            }
            else if (temp._finalNotifyDate != null)
                return false;
            if (this._advanceFinalNotify != null) {
                if (temp._advanceFinalNotify == null) return false;
                else if (!(this._advanceFinalNotify.equals(temp._advanceFinalNotify))) 
                    return false;
            }
            else if (temp._advanceFinalNotify != null)
                return false;
            if (this._postIssueStatusCT != null) {
                if (temp._postIssueStatusCT == null) return false;
                else if (!(this._postIssueStatusCT.equals(temp._postIssueStatusCT))) 
                    return false;
            }
            else if (temp._postIssueStatusCT != null)
                return false;
            if (this._priorPRDDue != null) {
                if (temp._priorPRDDue == null) return false;
                else if (!(this._priorPRDDue.equals(temp._priorPRDDue))) 
                    return false;
            }
            else if (temp._priorPRDDue != null)
                return false;
            if (this._conversionDate != null) {
                if (temp._conversionDate == null) return false;
                else if (!(this._conversionDate.equals(temp._conversionDate))) 
                    return false;
            }
            else if (temp._conversionDate != null)
                return false;
            if (this._clientUpdate != null) {
                if (temp._clientUpdate == null) return false;
                else if (!(this._clientUpdate.equals(temp._clientUpdate))) 
                    return false;
            }
            else if (temp._clientUpdate != null)
                return false;
            if (this._worksheetTypeCT != null) {
                if (temp._worksheetTypeCT == null) return false;
                else if (!(this._worksheetTypeCT.equals(temp._worksheetTypeCT))) 
                    return false;
            }
            else if (temp._worksheetTypeCT != null)
                return false;
            if (this._estateOfTheInsured != null) {
                if (temp._estateOfTheInsured == null) return false;
                else if (!(this._estateOfTheInsured.equals(temp._estateOfTheInsured))) 
                    return false;
            }
            else if (temp._estateOfTheInsured != null)
                return false;
            if (this._requirementEffectiveDate != null) {
                if (temp._requirementEffectiveDate == null) return false;
                else if (!(this._requirementEffectiveDate.equals(temp._requirementEffectiveDate))) 
                    return false;
            }
            else if (temp._requirementEffectiveDate != null)
                return false;
            if (this._referenceID != null) {
                if (temp._referenceID == null) return false;
                else if (!(this._referenceID.equals(temp._referenceID))) 
                    return false;
            }
            else if (temp._referenceID != null)
                return false;
            if (this._billScheduleChangeType != null) {
                if (temp._billScheduleChangeType == null) return false;
                else if (!(this._billScheduleChangeType.equals(temp._billScheduleChangeType))) 
                    return false;
            }
            else if (temp._billScheduleChangeType != null)
                return false;
            if (this._masterContractFK != temp._masterContractFK)
                return false;
            if (this._has_masterContractFK != temp._has_masterContractFK)
                return false;
            if (this._dividendOptionCT != null) {
                if (temp._dividendOptionCT == null) return false;
                else if (!(this._dividendOptionCT.equals(temp._dividendOptionCT))) 
                    return false;
            }
            else if (temp._dividendOptionCT != null)
                return false;
            if (this._originalContractGroupFK != temp._originalContractGroupFK)
                return false;
            if (this._has_originalContractGroupFK != temp._has_originalContractGroupFK)
                return false;
            if (this._scheduledTerminationDate != null) {
                if (temp._scheduledTerminationDate == null) return false;
                else if (!(this._scheduledTerminationDate.equals(temp._scheduledTerminationDate))) 
                    return false;
            }
            else if (temp._scheduledTerminationDate != null)
                return false;
            if (this._claimStopDate != null) {
                if (temp._claimStopDate == null) return false;
                else if (!(this._claimStopDate.equals(temp._claimStopDate))) 
                    return false;
            }
            else if (temp._claimStopDate != null)
                return false;
            if (this._EOBMultiple != temp._EOBMultiple)
                return false;
            if (this._has_EOBMultiple != temp._has_EOBMultiple)
                return false;
            if (this._EOBMaximum != null) {
                if (temp._EOBMaximum == null) return false;
                else if (!(this._EOBMaximum.equals(temp._EOBMaximum))) 
                    return false;
            }
            else if (temp._EOBMaximum != null)
                return false;
            if (this._EOBCum != null) {
                if (temp._EOBCum == null) return false;
                else if (!(this._EOBCum.equals(temp._EOBCum))) 
                    return false;
            }
            else if (temp._EOBCum != null)
                return false;
            if (this._premiumTaxSitusOverrideCT != null) {
                if (temp._premiumTaxSitusOverrideCT == null) return false;
                else if (!(this._premiumTaxSitusOverrideCT.equals(temp._premiumTaxSitusOverrideCT))) 
                    return false;
            }
            else if (temp._premiumTaxSitusOverrideCT != null)
                return false;
            if (this._GIOOption != null) {
                if (temp._GIOOption == null) return false;
                else if (!(this._GIOOption.equals(temp._GIOOption))) 
                    return false;
            }
            else if (temp._GIOOption != null)
                return false;
            if (this._deductionAmountOverride != null) {
                if (temp._deductionAmountOverride == null) return false;
                else if (!(this._deductionAmountOverride.equals(temp._deductionAmountOverride))) 
                    return false;
            }
            else if (temp._deductionAmountOverride != null)
                return false;
            if (this._deductionAmountEffectiveDate != null) {
                if (temp._deductionAmountEffectiveDate == null) return false;
                else if (!(this._deductionAmountEffectiveDate.equals(temp._deductionAmountEffectiveDate))) 
                    return false;
            }
            else if (temp._deductionAmountEffectiveDate != null)
                return false;
            if (this._agentHierarchyVOList != null) {
                if (temp._agentHierarchyVOList == null) return false;
                else if (!(this._agentHierarchyVOList.equals(temp._agentHierarchyVOList))) 
                    return false;
            }
            else if (temp._agentHierarchyVOList != null)
                return false;
            if (this._contractClientVOList != null) {
                if (temp._contractClientVOList == null) return false;
                else if (!(this._contractClientVOList.equals(temp._contractClientVOList))) 
                    return false;
            }
            else if (temp._contractClientVOList != null)
                return false;
            if (this._contractRequirementVOList != null) {
                if (temp._contractRequirementVOList == null) return false;
                else if (!(this._contractRequirementVOList.equals(temp._contractRequirementVOList))) 
                    return false;
            }
            else if (temp._contractRequirementVOList != null)
                return false;
            if (this._contractTreatyVOList != null) {
                if (temp._contractTreatyVOList == null) return false;
                else if (!(this._contractTreatyVOList.equals(temp._contractTreatyVOList))) 
                    return false;
            }
            else if (temp._contractTreatyVOList != null)
                return false;
            if (this._depositsVOList != null) {
                if (temp._depositsVOList == null) return false;
                else if (!(this._depositsVOList.equals(temp._depositsVOList))) 
                    return false;
            }
            else if (temp._depositsVOList != null)
                return false;
            if (this._inherentRiderVOList != null) {
                if (temp._inherentRiderVOList == null) return false;
                else if (!(this._inherentRiderVOList.equals(temp._inherentRiderVOList))) 
                    return false;
            }
            else if (temp._inherentRiderVOList != null)
                return false;
            if (this._investmentVOList != null) {
                if (temp._investmentVOList == null) return false;
                else if (!(this._investmentVOList.equals(temp._investmentVOList))) 
                    return false;
            }
            else if (temp._investmentVOList != null)
                return false;
            if (this._lifeVOList != null) {
                if (temp._lifeVOList == null) return false;
                else if (!(this._lifeVOList.equals(temp._lifeVOList))) 
                    return false;
            }
            else if (temp._lifeVOList != null)
                return false;
            if (this._noteReminderVOList != null) {
                if (temp._noteReminderVOList == null) return false;
                else if (!(this._noteReminderVOList.equals(temp._noteReminderVOList))) 
                    return false;
            }
            else if (temp._noteReminderVOList != null)
                return false;
            if (this._payoutVOList != null) {
                if (temp._payoutVOList == null) return false;
                else if (!(this._payoutVOList.equals(temp._payoutVOList))) 
                    return false;
            }
            else if (temp._payoutVOList != null)
                return false;
            if (this._realTimeActivityVOList != null) {
                if (temp._realTimeActivityVOList == null) return false;
                else if (!(this._realTimeActivityVOList.equals(temp._realTimeActivityVOList))) 
                    return false;
            }
            else if (temp._realTimeActivityVOList != null)
                return false;
            if (this._requiredMinDistributionVOList != null) {
                if (temp._requiredMinDistributionVOList == null) return false;
                else if (!(this._requiredMinDistributionVOList.equals(temp._requiredMinDistributionVOList))) 
                    return false;
            }
            else if (temp._requiredMinDistributionVOList != null)
                return false;
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            if (this._segmentBackupVOList != null) {
                if (temp._segmentBackupVOList == null) return false;
                else if (!(this._segmentBackupVOList.equals(temp._segmentBackupVOList))) 
                    return false;
            }
            else if (temp._segmentBackupVOList != null)
                return false;
            if (this._contractSetupVOList != null) {
                if (temp._contractSetupVOList == null) return false;
                else if (!(this._contractSetupVOList.equals(temp._contractSetupVOList))) 
                    return false;
            }
            else if (temp._contractSetupVOList != null)
                return false;
            if (this._premiumDueVOList != null) {
                if (temp._premiumDueVOList == null) return false;
                else if (!(this._premiumDueVOList.equals(temp._premiumDueVOList))) 
                    return false;
            }
            else if (temp._premiumDueVOList != null)
                return false;
            if (this._billVOList != null) {
                if (temp._billVOList == null) return false;
                else if (!(this._billVOList.equals(temp._billVOList))) 
                    return false;
            }
            else if (temp._billVOList != null)
                return false;
            if (this._valueAtIssueVOList != null) {
                if (temp._valueAtIssueVOList == null) return false;
                else if (!(this._valueAtIssueVOList.equals(temp._valueAtIssueVOList))) 
                    return false;
            }
            else if (temp._valueAtIssueVOList != null)
                return false;
            
            if (this._expiryDate != null) {
                if (temp._expiryDate == null) return false;
                else if (!(this._expiryDate.equals(temp._expiryDate))) 
                    return false;
            }
            else if (temp._expiryDate != null)
                return false;
            
            if (this._location != null) {
                if (temp._location == null) return false;
                else if (!(this._location.equals(temp._location))) 
                    return false;
            }
            else if (temp._location != null)
                return false;

            if (this._sequence != null) {
                if (temp._sequence == null) return false;
                else if (!(this._sequence.equals(temp._sequence))) 
                    return false;
            }
            else if (temp._sequence != null)
                return false;
            
            if (this._indivAnnPremium != null) {
                if (temp._indivAnnPremium == null) return false;
                else if (!(this._indivAnnPremium.equals(temp._indivAnnPremium))) 
                    return false;
            }
            else if (temp._indivAnnPremium != null)
                return false;

            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdvanceFinalNotifyReturns the value of field
     * 'advanceFinalNotify'.
     * 
     * @return the value of field 'advanceFinalNotify'.
     */
    public java.lang.String getAdvanceFinalNotify()
    {
        return this._advanceFinalNotify;
    } //-- java.lang.String getAdvanceFinalNotify() 

    /**
     * Method getAgeAtIssueReturns the value of field 'ageAtIssue'.
     * 
     * @return the value of field 'ageAtIssue'.
     */
    public int getAgeAtIssue()
    {
        return this._ageAtIssue;
    } //-- int getAgeAtIssue() 

    /**
     * Method getAgentHierarchyVO
     * 
     * @param index
     */
    public edit.common.vo.AgentHierarchyVO getAgentHierarchyVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentHierarchyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentHierarchyVO) _agentHierarchyVOList.elementAt(index);
    } //-- edit.common.vo.AgentHierarchyVO getAgentHierarchyVO(int) 

    /**
     * Method getAgentHierarchyVO
     */
    public edit.common.vo.AgentHierarchyVO[] getAgentHierarchyVO()
    {
        int size = _agentHierarchyVOList.size();
        edit.common.vo.AgentHierarchyVO[] mArray = new edit.common.vo.AgentHierarchyVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentHierarchyVO) _agentHierarchyVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentHierarchyVO[] getAgentHierarchyVO() 

    /**
     * Method getAgentHierarchyVOCount
     */
    public int getAgentHierarchyVOCount()
    {
        return _agentHierarchyVOList.size();
    } //-- int getAgentHierarchyVOCount() 

    /**
     * Method getAmountReturns the value of field 'amount'.
     * 
     * @return the value of field 'amount'.
     */
    public java.math.BigDecimal getAmount()
    {
        return this._amount;
    } //-- java.math.BigDecimal getAmount() 

    /**
     * Method getAnnualInsuranceAmountReturns the value of field
     * 'annualInsuranceAmount'.
     * 
     * @return the value of field 'annualInsuranceAmount'.
     */
    public java.math.BigDecimal getAnnualInsuranceAmount()
    {
        return this._annualInsuranceAmount;
    } //-- java.math.BigDecimal getAnnualInsuranceAmount() 

    /**
     * Method getAnnualInvestmentAmountReturns the value of field
     * 'annualInvestmentAmount'.
     * 
     * @return the value of field 'annualInvestmentAmount'.
     */
    public java.math.BigDecimal getAnnualInvestmentAmount()
    {
        return this._annualInvestmentAmount;
    } //-- java.math.BigDecimal getAnnualInvestmentAmount() 

    /**
     * Method getAnnualPremiumReturns the value of field
     * 'annualPremium'.
     * 
     * @return the value of field 'annualPremium'.
     */
    public java.math.BigDecimal getAnnualPremium()
    {
        return this._annualPremium;
    } //-- java.math.BigDecimal getAnnualPremium() 

    /**
     * Method getAnnuitizationValueReturns the value of field
     * 'annuitizationValue'.
     * 
     * @return the value of field 'annuitizationValue'.
     */
    public java.math.BigDecimal getAnnuitizationValue()
    {
        return this._annuitizationValue;
    } //-- java.math.BigDecimal getAnnuitizationValue() 

    /**
     * Method getApplicationNumberReturns the value of field
     * 'applicationNumber'.
     * 
     * @return the value of field 'applicationNumber'.
     */
    public java.lang.String getApplicationNumber()
    {
        return this._applicationNumber;
    } //-- java.lang.String getApplicationNumber() 

    /**
     * Method getApplicationReceivedDateReturns the value of field
     * 'applicationReceivedDate'.
     * 
     * @return the value of field 'applicationReceivedDate'.
     */
    public java.lang.String getApplicationReceivedDate()
    {
        return this._applicationReceivedDate;
    } //-- java.lang.String getApplicationReceivedDate() 

    /**
     * Method getApplicationSignedDateReturns the value of field
     * 'applicationSignedDate'.
     * 
     * @return the value of field 'applicationSignedDate'.
     */
    public java.lang.String getApplicationSignedDate()
    {
        return this._applicationSignedDate;
    } //-- java.lang.String getApplicationSignedDate() 

    /**
     * Method getApplicationSignedStateCTReturns the value of field
     * 'applicationSignedStateCT'.
     * 
     * @return the value of field 'applicationSignedStateCT'.
     */
    public java.lang.String getApplicationSignedStateCT()
    {
        return this._applicationSignedStateCT;
    } //-- java.lang.String getApplicationSignedStateCT() 

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
     * Method getBatchContractSetupFKReturns the value of field
     * 'batchContractSetupFK'.
     * 
     * @return the value of field 'batchContractSetupFK'.
     */
    public long getBatchContractSetupFK()
    {
        return this._batchContractSetupFK;
    } //-- long getBatchContractSetupFK() 

    /**
     * Method getBillScheduleChangeTypeReturns the value of field
     * 'billScheduleChangeType'.
     * 
     * @return the value of field 'billScheduleChangeType'.
     */
    public java.lang.String getBillScheduleChangeType()
    {
        return this._billScheduleChangeType;
    } //-- java.lang.String getBillScheduleChangeType() 

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
     * Method getCasetrackingOptionCTReturns the value of field
     * 'casetrackingOptionCT'.
     * 
     * @return the value of field 'casetrackingOptionCT'.
     */
    public java.lang.String getCasetrackingOptionCT()
    {
        return this._casetrackingOptionCT;
    } //-- java.lang.String getCasetrackingOptionCT() 

    /**
     * Method getCashWithAppIndReturns the value of field
     * 'cashWithAppInd'.
     * 
     * @return the value of field 'cashWithAppInd'.
     */
    public java.lang.String getCashWithAppInd()
    {
        return this._cashWithAppInd;
    } //-- java.lang.String getCashWithAppInd() 

    /**
     * Method getChargeCodeStatusReturns the value of field
     * 'chargeCodeStatus'.
     * 
     * @return the value of field 'chargeCodeStatus'.
     */
    public java.lang.String getChargeCodeStatus()
    {
        return this._chargeCodeStatus;
    } //-- java.lang.String getChargeCodeStatus() 

    /**
     * Method getChargeDeductAmountReturns the value of field
     * 'chargeDeductAmount'.
     * 
     * @return the value of field 'chargeDeductAmount'.
     */
    public java.math.BigDecimal getChargeDeductAmount()
    {
        return this._chargeDeductAmount;
    } //-- java.math.BigDecimal getChargeDeductAmount() 

    /**
     * Method getChargeDeductDivisionIndReturns the value of field
     * 'chargeDeductDivisionInd'.
     * 
     * @return the value of field 'chargeDeductDivisionInd'.
     */
    public java.lang.String getChargeDeductDivisionInd()
    {
        return this._chargeDeductDivisionInd;
    } //-- java.lang.String getChargeDeductDivisionInd() 

    /**
     * Method getChargesReturns the value of field 'charges'.
     * 
     * @return the value of field 'charges'.
     */
    public java.math.BigDecimal getCharges()
    {
        return this._charges;
    } //-- java.math.BigDecimal getCharges() 

    /**
     * Method getClaimStopDateReturns the value of field
     * 'claimStopDate'.
     * 
     * @return the value of field 'claimStopDate'.
     */
    public java.lang.String getClaimStopDate()
    {
        return this._claimStopDate;
    } //-- java.lang.String getClaimStopDate() 

    /**
     * Method getClientUpdateReturns the value of field
     * 'clientUpdate'.
     * 
     * @return the value of field 'clientUpdate'.
     */
    public java.lang.String getClientUpdate()
    {
        return this._clientUpdate;
    } //-- java.lang.String getClientUpdate() 

    /**
     * Method getCommissionPhaseIDReturns the value of field
     * 'commissionPhaseID'.
     * 
     * @return the value of field 'commissionPhaseID'.
     */
    public int getCommissionPhaseID()
    {
        return this._commissionPhaseID;
    } //-- int getCommissionPhaseID() 

    /**
     * Method getCommissionPhaseOverrideReturns the value of field
     * 'commissionPhaseOverride'.
     * 
     * @return the value of field 'commissionPhaseOverride'.
     */
    public java.lang.String getCommissionPhaseOverride()
    {
        return this._commissionPhaseOverride;
    } //-- java.lang.String getCommissionPhaseOverride() 

    /**
     * Method getCommitmentAmountReturns the value of field
     * 'commitmentAmount'.
     * 
     * @return the value of field 'commitmentAmount'.
     */
    public java.math.BigDecimal getCommitmentAmount()
    {
        return this._commitmentAmount;
    } //-- java.math.BigDecimal getCommitmentAmount() 

    /**
     * Method getCommitmentIndicatorReturns the value of field
     * 'commitmentIndicator'.
     * 
     * @return the value of field 'commitmentIndicator'.
     */
    public java.lang.String getCommitmentIndicator()
    {
        return this._commitmentIndicator;
    } //-- java.lang.String getCommitmentIndicator() 

    /**
     * Method getConsecutiveAPLCountReturns the value of field
     * 'consecutiveAPLCount'.
     * 
     * @return the value of field 'consecutiveAPLCount'.
     */
    public int getConsecutiveAPLCount()
    {
        return this._consecutiveAPLCount;
    } //-- int getConsecutiveAPLCount() 

    /**
     * Method getContractClientVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientVO getContractClientVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractClientVO) _contractClientVOList.elementAt(index);
    } //-- edit.common.vo.ContractClientVO getContractClientVO(int) 

    /**
     * Method getContractClientVO
     */
    public edit.common.vo.ContractClientVO[] getContractClientVO()
    {
        int size = _contractClientVOList.size();
        edit.common.vo.ContractClientVO[] mArray = new edit.common.vo.ContractClientVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractClientVO) _contractClientVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractClientVO[] getContractClientVO() 

    /**
     * Method getContractClientVOCount
     */
    public int getContractClientVOCount()
    {
        return _contractClientVOList.size();
    } //-- int getContractClientVOCount() 

    /**
     * Method getContractGroupFKReturns the value of field
     * 'contractGroupFK'.
     * 
     * @return the value of field 'contractGroupFK'.
     */
    public long getContractGroupFK()
    {
        return this._contractGroupFK;
    } //-- long getContractGroupFK() 

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
     * Method getContractRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.ContractRequirementVO getContractRequirementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractRequirementVO) _contractRequirementVOList.elementAt(index);
    } //-- edit.common.vo.ContractRequirementVO getContractRequirementVO(int) 

    /**
     * Method getContractRequirementVO
     */
    public edit.common.vo.ContractRequirementVO[] getContractRequirementVO()
    {
        int size = _contractRequirementVOList.size();
        edit.common.vo.ContractRequirementVO[] mArray = new edit.common.vo.ContractRequirementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractRequirementVO) _contractRequirementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractRequirementVO[] getContractRequirementVO() 

    /**
     * Method getContractRequirementVOCount
     */
    public int getContractRequirementVOCount()
    {
        return _contractRequirementVOList.size();
    } //-- int getContractRequirementVOCount() 

    /**
     * Method getContractSetupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractSetupVO getContractSetupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractSetupVO) _contractSetupVOList.elementAt(index);
    } //-- edit.common.vo.ContractSetupVO getContractSetupVO(int) 

    /**
     * Method getContractSetupVO
     */
    public edit.common.vo.ContractSetupVO[] getContractSetupVO()
    {
        int size = _contractSetupVOList.size();
        edit.common.vo.ContractSetupVO[] mArray = new edit.common.vo.ContractSetupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractSetupVO) _contractSetupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractSetupVO[] getContractSetupVO() 

    /**
     * Method getContractSetupVOCount
     */
    public int getContractSetupVOCount()
    {
        return _contractSetupVOList.size();
    } //-- int getContractSetupVOCount() 

    /**
     * Method getContractTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.ContractTreatyVO getContractTreatyVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractTreatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractTreatyVO) _contractTreatyVOList.elementAt(index);
    } //-- edit.common.vo.ContractTreatyVO getContractTreatyVO(int) 

    /**
     * Method getContractTreatyVO
     */
    public edit.common.vo.ContractTreatyVO[] getContractTreatyVO()
    {
        int size = _contractTreatyVOList.size();
        edit.common.vo.ContractTreatyVO[] mArray = new edit.common.vo.ContractTreatyVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractTreatyVO) _contractTreatyVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractTreatyVO[] getContractTreatyVO() 

    /**
     * Method getContractTreatyVOCount
     */
    public int getContractTreatyVOCount()
    {
        return _contractTreatyVOList.size();
    } //-- int getContractTreatyVOCount() 

    /**
     * Method getContractTypeCTReturns the value of field
     * 'contractTypeCT'.
     * 
     * @return the value of field 'contractTypeCT'.
     */
    public java.lang.String getContractTypeCT()
    {
        return this._contractTypeCT;
    } //-- java.lang.String getContractTypeCT() 

    /**
     * Method getConversionDateReturns the value of field
     * 'conversionDate'.
     * 
     * @return the value of field 'conversionDate'.
     */
    public java.lang.String getConversionDate()
    {
        return this._conversionDate;
    } //-- java.lang.String getConversionDate() 

    /**
     * Method getCostBasisReturns the value of field 'costBasis'.
     * 
     * @return the value of field 'costBasis'.
     */
    public java.math.BigDecimal getCostBasis()
    {
        return this._costBasis;
    } //-- java.math.BigDecimal getCostBasis() 

    /**
     * Method getCreationDateReturns the value of field
     * 'creationDate'.
     * 
     * @return the value of field 'creationDate'.
     */
    public java.lang.String getCreationDate()
    {
        return this._creationDate;
    } //-- java.lang.String getCreationDate() 

    /**
     * Method getCreationOperatorReturns the value of field
     * 'creationOperator'.
     * 
     * @return the value of field 'creationOperator'.
     */
    public java.lang.String getCreationOperator()
    {
        return this._creationOperator;
    } //-- java.lang.String getCreationOperator() 

    /**
     * Method getDateInEffectReturns the value of field
     * 'dateInEffect'.
     * 
     * @return the value of field 'dateInEffect'.
     */
    public java.lang.String getDateInEffect()
    {
        return this._dateInEffect;
    } //-- java.lang.String getDateInEffect() 

    /**
     * Method getDateOfDeathValueReturns the value of field
     * 'dateOfDeathValue'.
     * 
     * @return the value of field 'dateOfDeathValue'.
     */
    public java.math.BigDecimal getDateOfDeathValue()
    {
        return this._dateOfDeathValue;
    } //-- java.math.BigDecimal getDateOfDeathValue() 

    /**
     * Method getDeductionAmountEffectiveDateReturns the value of
     * field 'deductionAmountEffectiveDate'.
     * 
     * @return the value of field 'deductionAmountEffectiveDate'.
     */
    public java.lang.String getDeductionAmountEffectiveDate()
    {
        return this._deductionAmountEffectiveDate;
    } //-- java.lang.String getDeductionAmountEffectiveDate() 

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
     * Method getDepartmentLocationFKReturns the value of field
     * 'departmentLocationFK'.
     * 
     * @return the value of field 'departmentLocationFK'.
     */
    public long getDepartmentLocationFK()
    {
        return this._departmentLocationFK;
    } //-- long getDepartmentLocationFK() 

    /**
     * Method getDepositsVO
     * 
     * @param index
     */
    public edit.common.vo.DepositsVO getDepositsVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _depositsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.DepositsVO) _depositsVOList.elementAt(index);
    } //-- edit.common.vo.DepositsVO getDepositsVO(int) 

    /**
     * Method getDepositsVO
     */
    public edit.common.vo.DepositsVO[] getDepositsVO()
    {
        int size = _depositsVOList.size();
        edit.common.vo.DepositsVO[] mArray = new edit.common.vo.DepositsVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.DepositsVO) _depositsVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.DepositsVO[] getDepositsVO() 

    /**
     * Method getDepositsVOCount
     */
    public int getDepositsVOCount()
    {
        return _depositsVOList.size();
    } //-- int getDepositsVOCount() 

    /**
     * Method getDialableSalesLoadPercentageReturns the value of
     * field 'dialableSalesLoadPercentage'.
     * 
     * @return the value of field 'dialableSalesLoadPercentage'.
     */
    public java.math.BigDecimal getDialableSalesLoadPercentage()
    {
        return this._dialableSalesLoadPercentage;
    } //-- java.math.BigDecimal getDialableSalesLoadPercentage() 

    /**
     * Method getDismembermentPercentReturns the value of field
     * 'dismembermentPercent'.
     * 
     * @return the value of field 'dismembermentPercent'.
     */
    public java.math.BigDecimal getDismembermentPercent()
    {
        return this._dismembermentPercent;
    } //-- java.math.BigDecimal getDismembermentPercent() 

    /**
     * Method getDividendOptionCTReturns the value of field
     * 'dividendOptionCT'.
     * 
     * @return the value of field 'dividendOptionCT'.
     */
    public java.lang.String getDividendOptionCT()
    {
        return this._dividendOptionCT;
    } //-- java.lang.String getDividendOptionCT() 

    /**
     * Method getEOBCumReturns the value of field 'EOBCum'.
     * 
     * @return the value of field 'EOBCum'.
     */
    public java.math.BigDecimal getEOBCum()
    {
        return this._EOBCum;
    } //-- java.math.BigDecimal getEOBCum() 

    /**
     * Method getEOBMaximumReturns the value of field 'EOBMaximum'.
     * 
     * @return the value of field 'EOBMaximum'.
     */
    public java.math.BigDecimal getEOBMaximum()
    {
        return this._EOBMaximum;
    } //-- java.math.BigDecimal getEOBMaximum() 

    /**
     * Method getEOBMultipleReturns the value of field
     * 'EOBMultiple'.
     * 
     * @return the value of field 'EOBMultiple'.
     */
    public int getEOBMultiple()
    {
        return this._EOBMultiple;
    } //-- int getEOBMultiple() 

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
     * Method getEstateOfTheInsuredReturns the value of field
     * 'estateOfTheInsured'.
     * 
     * @return the value of field 'estateOfTheInsured'.
     */
    public java.lang.String getEstateOfTheInsured()
    {
        return this._estateOfTheInsured;
    } //-- java.lang.String getEstateOfTheInsured() 

    /**
     * Method getExchangeIndReturns the value of field
     * 'exchangeInd'.
     * 
     * @return the value of field 'exchangeInd'.
     */
    public java.lang.String getExchangeInd()
    {
        return this._exchangeInd;
    } //-- java.lang.String getExchangeInd() 

    /**
     * Method getFeesReturns the value of field 'fees'.
     * 
     * @return the value of field 'fees'.
     */
    public java.math.BigDecimal getFees()
    {
        return this._fees;
    } //-- java.math.BigDecimal getFees() 

    /**
     * Method getFinalNotifyDateReturns the value of field
     * 'finalNotifyDate'.
     * 
     * @return the value of field 'finalNotifyDate'.
     */
    public java.lang.String getFinalNotifyDate()
    {
        return this._finalNotifyDate;
    } //-- java.lang.String getFinalNotifyDate() 

    /**
     * Method getFirstNotifyDateReturns the value of field
     * 'firstNotifyDate'.
     * 
     * @return the value of field 'firstNotifyDate'.
     */
    public java.lang.String getFirstNotifyDate()
    {
        return this._firstNotifyDate;
    } //-- java.lang.String getFirstNotifyDate() 

    /**
     * Method getFreeAmountReturns the value of field 'freeAmount'.
     * 
     * @return the value of field 'freeAmount'.
     */
    public java.math.BigDecimal getFreeAmount()
    {
        return this._freeAmount;
    } //-- java.math.BigDecimal getFreeAmount() 

    /**
     * Method getFreeAmountRemainingReturns the value of field
     * 'freeAmountRemaining'.
     * 
     * @return the value of field 'freeAmountRemaining'.
     */
    public java.math.BigDecimal getFreeAmountRemaining()
    {
        return this._freeAmountRemaining;
    } //-- java.math.BigDecimal getFreeAmountRemaining() 
    
    
    // for UL rider changes
    public java.lang.String getSegmentChangeEffectiveDate() {
		return _segmentChangeEffectiveDate;
	}


	public void setSegmentChangeEffectiveDate(java.lang.String segmentChangeEffectiveDate) {
		this._segmentChangeEffectiveDate = segmentChangeEffectiveDate;
	}


	/**
     * Method getFreeLookDaysOverrideReturns the value of field
     * 'freeLookDaysOverride'.
     * 
     * @return the value of field 'freeLookDaysOverride'.
     */
    public int getFreeLookDaysOverride()
    {
        return this._freeLookDaysOverride;
    } //-- int getFreeLookDaysOverride() 

    /**
     * Method getFreeLookEndDateReturns the value of field
     * 'freeLookEndDate'.
     * 
     * @return the value of field 'freeLookEndDate'.
     */
    public java.lang.String getFreeLookEndDate()
    {
        return this._freeLookEndDate;
    } //-- java.lang.String getFreeLookEndDate() 

    /**
     * Method getGIOOptionReturns the value of field 'GIOOption'.
     * 
     * @return the value of field 'GIOOption'.
     */
    public java.lang.String getGIOOption()
    {
        return this._GIOOption;
    } //-- java.lang.String getGIOOption() 

    /**
     * Method getGroupPlanReturns the value of field 'groupPlan'.
     * 
     * @return the value of field 'groupPlan'.
     */
    public java.lang.String getGroupPlan()
    {
        return this._groupPlan;
    } //-- java.lang.String getGroupPlan() 

    /**
     * Method getInherentRiderVO
     * 
     * @param index
     */
    public edit.common.vo.InherentRiderVO getInherentRiderVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _inherentRiderVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InherentRiderVO) _inherentRiderVOList.elementAt(index);
    } //-- edit.common.vo.InherentRiderVO getInherentRiderVO(int) 

    /**
     * Method getInherentRiderVO
     */
    public edit.common.vo.InherentRiderVO[] getInherentRiderVO()
    {
        int size = _inherentRiderVOList.size();
        edit.common.vo.InherentRiderVO[] mArray = new edit.common.vo.InherentRiderVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InherentRiderVO) _inherentRiderVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InherentRiderVO[] getInherentRiderVO() 

    /**
     * Method getInherentRiderVOCount
     */
    public int getInherentRiderVOCount()
    {
        return _inherentRiderVOList.size();
    } //-- int getInherentRiderVOCount() 

    /**
     * Method getInvestmentVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentVO getInvestmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentVO) _investmentVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentVO getInvestmentVO(int) 

    /**
     * Method getInvestmentVO
     */
    public edit.common.vo.InvestmentVO[] getInvestmentVO()
    {
        int size = _investmentVOList.size();
        edit.common.vo.InvestmentVO[] mArray = new edit.common.vo.InvestmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentVO) _investmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentVO[] getInvestmentVO() 

    /**
     * Method getInvestmentVOCount
     */
    public int getInvestmentVOCount()
    {
        return _investmentVOList.size();
    } //-- int getInvestmentVOCount() 

    /**
     * Method getIssueDateReturns the value of field 'issueDate'.
     * 
     * @return the value of field 'issueDate'.
     */
    public java.lang.String getIssueDate()
    {
        return this._issueDate;
    } //-- java.lang.String getIssueDate() 

    /**
     * Method getIssueStateCTReturns the value of field
     * 'issueStateCT'.
     * 
     * @return the value of field 'issueStateCT'.
     */
    public java.lang.String getIssueStateCT()
    {
        return this._issueStateCT;
    } //-- java.lang.String getIssueStateCT() 

    /**
     * Method getIssueStateORIndReturns the value of field
     * 'issueStateORInd'.
     * 
     * @return the value of field 'issueStateORInd'.
     */
    public java.lang.String getIssueStateORInd()
    {
        return this._issueStateORInd;
    } //-- java.lang.String getIssueStateORInd() 

    /**
     * Method getOriginalStateCTReturns the value of field
     * 'originalStateCT'.
     * 
     * @return the value of field 'originalStateCT'.
     */
    public java.lang.String getOriginalStateCT()
    {
        return this._originalStateCT;
    } //-- java.lang.String getOriginalStateCT() 


    /**
     * Method getLastAnniversaryDateReturns the value of field
     * 'lastAnniversaryDate'.
     * 
     * @return the value of field 'lastAnniversaryDate'.
     */
    public java.lang.String getLastAnniversaryDate()
    {
        return this._lastAnniversaryDate;
    } //-- java.lang.String getLastAnniversaryDate() 

    /**
     * Method getLastSettlementValDateReturns the value of field
     * 'lastSettlementValDate'.
     * 
     * @return the value of field 'lastSettlementValDate'.
     */
    public java.lang.String getLastSettlementValDate()
    {
        return this._lastSettlementValDate;
    } //-- java.lang.String getLastSettlementValDate() 

    /**
     * Method getLifeVO
     * 
     * @param index
     */
    public edit.common.vo.LifeVO getLifeVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _lifeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.LifeVO) _lifeVOList.elementAt(index);
    } //-- edit.common.vo.LifeVO getLifeVO(int) 

    /**
     * Method getLifeVO
     */
    public edit.common.vo.LifeVO[] getLifeVO()
    {
        int size = _lifeVOList.size();
        edit.common.vo.LifeVO[] mArray = new edit.common.vo.LifeVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.LifeVO) _lifeVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.LifeVO[] getLifeVO() 

    /**
     * Method getLifeVOCount
     */
    public int getLifeVOCount()
    {
        return _lifeVOList.size();
    } //-- int getLifeVOCount() 

    /**
     * Method getLoadsReturns the value of field 'loads'.
     * 
     * @return the value of field 'loads'.
     */
    public java.math.BigDecimal getLoads()
    {
        return this._loads;
    } //-- java.math.BigDecimal getLoads() 

    /**
     * Method getMasterContractFKReturns the value of field
     * 'masterContractFK'.
     * 
     * @return the value of field 'masterContractFK'.
     */
    public long getMasterContractFK()
    {
        return this._masterContractFK;
    } //-- long getMasterContractFK() 

    /**
     * Method getMemberOfContractGroupReturns the value of field
     * 'memberOfContractGroup'.
     * 
     * @return the value of field 'memberOfContractGroup'.
     */
    public java.lang.String getMemberOfContractGroup()
    {
        return this._memberOfContractGroup;
    } //-- java.lang.String getMemberOfContractGroup() 

    /**
     * Method getNoteReminderVO
     * 
     * @param index
     */
    public edit.common.vo.NoteReminderVO getNoteReminderVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _noteReminderVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.NoteReminderVO) _noteReminderVOList.elementAt(index);
    } //-- edit.common.vo.NoteReminderVO getNoteReminderVO(int) 

    /**
     * Method getNoteReminderVO
     */
    public edit.common.vo.NoteReminderVO[] getNoteReminderVO()
    {
        int size = _noteReminderVOList.size();
        edit.common.vo.NoteReminderVO[] mArray = new edit.common.vo.NoteReminderVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.NoteReminderVO) _noteReminderVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.NoteReminderVO[] getNoteReminderVO() 

    /**
     * Method getNoteReminderVOCount
     */
    public int getNoteReminderVOCount()
    {
        return _noteReminderVOList.size();
    } //-- int getNoteReminderVOCount() 

    /**
     * Method getOpenClaimEndDateReturns the value of field
     * 'openClaimEndDate'.
     * 
     * @return the value of field 'openClaimEndDate'.
     */
    public java.lang.String getOpenClaimEndDate()
    {
        return this._openClaimEndDate;
    } //-- java.lang.String getOpenClaimEndDate() 

    /**
     * Method getOptionCodeCTReturns the value of field
     * 'optionCodeCT'.
     * 
     * @return the value of field 'optionCodeCT'.
     */
    public java.lang.String getOptionCodeCT()
    {
        return this._optionCodeCT;
    } //-- java.lang.String getOptionCodeCT() 

    /**
     * Method getOriginalContractGroupFKReturns the value of field
     * 'originalContractGroupFK'.
     * 
     * @return the value of field 'originalContractGroupFK'.
     */
    public long getOriginalContractGroupFK()
    {
        return this._originalContractGroupFK;
    } //-- long getOriginalContractGroupFK() 

    /**
     * Method getOriginalUnitsReturns the value of field
     * 'originalUnits'.
     * 
     * @return the value of field 'originalUnits'.
     */
    public java.math.BigDecimal getOriginalUnits()
    {
        return this._originalUnits;
    } //-- java.math.BigDecimal getOriginalUnits() 

    /**
     * Method getPayoutVO
     * 
     * @param index
     */
    public edit.common.vo.PayoutVO getPayoutVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _payoutVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PayoutVO) _payoutVOList.elementAt(index);
    } //-- edit.common.vo.PayoutVO getPayoutVO(int) 

    /**
     * Method getPayoutVO
     */
    public edit.common.vo.PayoutVO[] getPayoutVO()
    {
        int size = _payoutVOList.size();
        edit.common.vo.PayoutVO[] mArray = new edit.common.vo.PayoutVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PayoutVO) _payoutVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PayoutVO[] getPayoutVO() 

    /**
     * Method getPayoutVOCount
     */
    public int getPayoutVOCount()
    {
        return _payoutVOList.size();
    } //-- int getPayoutVOCount() 

    /**
     * Method getPointInScaleIndicatorReturns the value of field
     * 'pointInScaleIndicator'.
     * 
     * @return the value of field 'pointInScaleIndicator'.
     */
    public java.lang.String getPointInScaleIndicator()
    {
        return this._pointInScaleIndicator;
    } //-- java.lang.String getPointInScaleIndicator() 

    /**
     * Method getPolicyDeliveryDateReturns the value of field
     * 'policyDeliveryDate'.
     * 
     * @return the value of field 'policyDeliveryDate'.
     */
    public java.lang.String getPolicyDeliveryDate()
    {
        return this._policyDeliveryDate;
    } //-- java.lang.String getPolicyDeliveryDate() 

    /**
     * Method getPostIssueStatusCTReturns the value of field
     * 'postIssueStatusCT'.
     * 
     * @return the value of field 'postIssueStatusCT'.
     */
    public java.lang.String getPostIssueStatusCT()
    {
        return this._postIssueStatusCT;
    } //-- java.lang.String getPostIssueStatusCT() 

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
     * Method getPremiumTaxSitusOverrideCTReturns the value of
     * field 'premiumTaxSitusOverrideCT'.
     * 
     * @return the value of field 'premiumTaxSitusOverrideCT'.
     */
    public java.lang.String getPremiumTaxSitusOverrideCT()
    {
        return this._premiumTaxSitusOverrideCT;
    } //-- java.lang.String getPremiumTaxSitusOverrideCT() 

    /**
     * Method getPreviousNotifyDateReturns the value of field
     * 'previousNotifyDate'.
     * 
     * @return the value of field 'previousNotifyDate'.
     */
    public java.lang.String getPreviousNotifyDate()
    {
        return this._previousNotifyDate;
    } //-- java.lang.String getPreviousNotifyDate() 

    /**
     * Method getPrintLine1Returns the value of field 'printLine1'.
     * 
     * @return the value of field 'printLine1'.
     */
    public java.lang.String getPrintLine1()
    {
        return this._printLine1;
    } //-- java.lang.String getPrintLine1() 

    /**
     * Method getPrintLine2Returns the value of field 'printLine2'.
     * 
     * @return the value of field 'printLine2'.
     */
    public java.lang.String getPrintLine2()
    {
        return this._printLine2;
    } //-- java.lang.String getPrintLine2() 

    /**
     * Method getPriorContractGroupFKReturns the value of field
     * 'priorContractGroupFK'.
     * 
     * @return the value of field 'priorContractGroupFK'.
     */
    public long getPriorContractGroupFK()
    {
        return this._priorContractGroupFK;
    } //-- long getPriorContractGroupFK() 

    /**
     * Method getPriorPRDDueReturns the value of field
     * 'priorPRDDue'.
     * 
     * @return the value of field 'priorPRDDue'.
     */
    public java.lang.String getPriorPRDDue()
    {
        return this._priorPRDDue;
    } //-- java.lang.String getPriorPRDDue() 

    /**
     * Method getPriorROTHCostBasisReturns the value of field
     * 'priorROTHCostBasis'.
     * 
     * @return the value of field 'priorROTHCostBasis'.
     */
    public java.math.BigDecimal getPriorROTHCostBasis()
    {
        return this._priorROTHCostBasis;
    } //-- java.math.BigDecimal getPriorROTHCostBasis() 

    /**
     * Method getProductStructureFKReturns the value of field
     * 'productStructureFK'.
     * 
     * @return the value of field 'productStructureFK'.
     */
    public long getProductStructureFK()
    {
        return this._productStructureFK;
    } //-- long getProductStructureFK() 

    /**
     * Method getQualNonQualCTReturns the value of field
     * 'qualNonQualCT'.
     * 
     * @return the value of field 'qualNonQualCT'.
     */
    public java.lang.String getQualNonQualCT()
    {
        return this._qualNonQualCT;
    } //-- java.lang.String getQualNonQualCT() 

    /**
     * Method getQualifiedTypeCTReturns the value of field
     * 'qualifiedTypeCT'.
     * 
     * @return the value of field 'qualifiedTypeCT'.
     */
    public java.lang.String getQualifiedTypeCT()
    {
        return this._qualifiedTypeCT;
    } //-- java.lang.String getQualifiedTypeCT() 

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
     * Method getROTHConvIndReturns the value of field
     * 'ROTHConvInd'.
     * 
     * @return the value of field 'ROTHConvInd'.
     */
    public java.lang.String getROTHConvInd()
    {
        return this._ROTHConvInd;
    } //-- java.lang.String getROTHConvInd() 

    /**
     * Method getRatedGenderCTReturns the value of field
     * 'ratedGenderCT'.
     * 
     * @return the value of field 'ratedGenderCT'.
     */
    public java.lang.String getRatedGenderCT()
    {
        return this._ratedGenderCT;
    } //-- java.lang.String getRatedGenderCT() 

    /**
     * Method getRealTimeActivityVO
     * 
     * @param index
     */
    public edit.common.vo.RealTimeActivityVO getRealTimeActivityVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _realTimeActivityVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RealTimeActivityVO) _realTimeActivityVOList.elementAt(index);
    } //-- edit.common.vo.RealTimeActivityVO getRealTimeActivityVO(int) 

    /**
     * Method getRealTimeActivityVO
     */
    public edit.common.vo.RealTimeActivityVO[] getRealTimeActivityVO()
    {
        int size = _realTimeActivityVOList.size();
        edit.common.vo.RealTimeActivityVO[] mArray = new edit.common.vo.RealTimeActivityVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RealTimeActivityVO) _realTimeActivityVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RealTimeActivityVO[] getRealTimeActivityVO() 

    /**
     * Method getRealTimeActivityVOCount
     */
    public int getRealTimeActivityVOCount()
    {
        return _realTimeActivityVOList.size();
    } //-- int getRealTimeActivityVOCount() 

    /**
     * Method getRecoveredCostBasisReturns the value of field
     * 'recoveredCostBasis'.
     * 
     * @return the value of field 'recoveredCostBasis'.
     */
    public java.math.BigDecimal getRecoveredCostBasis()
    {
        return this._recoveredCostBasis;
    } //-- java.math.BigDecimal getRecoveredCostBasis() 

    /**
     * Method getReferenceIDReturns the value of field
     * 'referenceID'.
     * 
     * @return the value of field 'referenceID'.
     */
    public java.lang.String getReferenceID()
    {
        return this._referenceID;
    } //-- java.lang.String getReferenceID() 

    /**
     * Method getRemainingBeneficiariesReturns the value of field
     * 'remainingBeneficiaries'.
     * 
     * @return the value of field 'remainingBeneficiaries'.
     */
    public int getRemainingBeneficiaries()
    {
        return this._remainingBeneficiaries;
    } //-- int getRemainingBeneficiaries() 

    /**
     * Method getRequiredMinDistributionVO
     * 
     * @param index
     */
    public edit.common.vo.RequiredMinDistributionVO getRequiredMinDistributionVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _requiredMinDistributionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RequiredMinDistributionVO) _requiredMinDistributionVOList.elementAt(index);
    } //-- edit.common.vo.RequiredMinDistributionVO getRequiredMinDistributionVO(int) 

    /**
     * Method getRequiredMinDistributionVO
     */
    public edit.common.vo.RequiredMinDistributionVO[] getRequiredMinDistributionVO()
    {
        int size = _requiredMinDistributionVOList.size();
        edit.common.vo.RequiredMinDistributionVO[] mArray = new edit.common.vo.RequiredMinDistributionVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RequiredMinDistributionVO) _requiredMinDistributionVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RequiredMinDistributionVO[] getRequiredMinDistributionVO() 

    /**
     * Method getRequiredMinDistributionVOCount
     */
    public int getRequiredMinDistributionVOCount()
    {
        return _requiredMinDistributionVOList.size();
    } //-- int getRequiredMinDistributionVOCount() 

    /**
     * Method getRequirementEffectiveDateReturns the value of field
     * 'requirementEffectiveDate'.
     * 
     * @return the value of field 'requirementEffectiveDate'.
     */
    public java.lang.String getRequirementEffectiveDate()
    {
        return this._requirementEffectiveDate;
    } //-- java.lang.String getRequirementEffectiveDate() 

    /**
     * Method getRiderNumberReturns the value of field
     * 'riderNumber'.
     * 
     * @return the value of field 'riderNumber'.
     */
    public int getRiderNumber()
    {
        return this._riderNumber;
    } //-- int getRiderNumber() 

    /**
     * Method getSavingsPercentReturns the value of field
     * 'savingsPercent'.
     * 
     * @return the value of field 'savingsPercent'.
     */
    public java.math.BigDecimal getSavingsPercent()
    {
        return this._savingsPercent;
    } //-- java.math.BigDecimal getSavingsPercent() 

    /**
     * Method getScheduledTerminationDateReturns the value of field
     * 'scheduledTerminationDate'.
     * 
     * @return the value of field 'scheduledTerminationDate'.
     */
    public java.lang.String getScheduledTerminationDate()
    {
        return this._scheduledTerminationDate;
    } //-- java.lang.String getScheduledTerminationDate() 

    /**
     * Method getSegmentBackupVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentBackupVO getSegmentBackupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentBackupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SegmentBackupVO) _segmentBackupVOList.elementAt(index);
    } //-- edit.common.vo.SegmentBackupVO getSegmentBackupVO(int) 

    /**
     * Method getSegmentBackupVO
     */
    public edit.common.vo.SegmentBackupVO[] getSegmentBackupVO()
    {
        int size = _segmentBackupVOList.size();
        edit.common.vo.SegmentBackupVO[] mArray = new edit.common.vo.SegmentBackupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SegmentBackupVO) _segmentBackupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SegmentBackupVO[] getSegmentBackupVO() 

    /**
     * Method getSegmentBackupVOCount
     */
    public int getSegmentBackupVOCount()
    {
        return _segmentBackupVOList.size();
    } //-- int getSegmentBackupVOCount() 

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
     * Method getSegmentNameCTReturns the value of field
     * 'segmentNameCT'.
     * 
     * @return the value of field 'segmentNameCT'.
     */
    public java.lang.String getSegmentNameCT()
    {
        return this._segmentNameCT;
    } //-- java.lang.String getSegmentNameCT() 

    /**
     * Method getSegmentPKReturns the value of field 'segmentPK'.
     * 
     * @return the value of field 'segmentPK'.
     */
    public long getSegmentPK()
    {
        return this._segmentPK;
    } //-- long getSegmentPK() 

    /**
     * Method getSegmentStatusCTReturns the value of field
     * 'segmentStatusCT'.
     * 
     * @return the value of field 'segmentStatusCT'.
     */
    public java.lang.String getSegmentStatusCT()
    {
        return this._segmentStatusCT;
    } //-- java.lang.String getSegmentStatusCT() 

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
     * Method getSettlementAmountReturns the value of field
     * 'settlementAmount'.
     * 
     * @return the value of field 'settlementAmount'.
     */
    public java.math.BigDecimal getSettlementAmount()
    {
        return this._settlementAmount;
    } //-- java.math.BigDecimal getSettlementAmount() 

    /**
     * Method getStatusChangeDateReturns the value of field
     * 'statusChangeDate'.
     * 
     * @return the value of field 'statusChangeDate'.
     */
    public java.lang.String getStatusChangeDate()
    {
        return this._statusChangeDate;
    } //-- java.lang.String getStatusChangeDate() 

    /**
     * Method getSuppOriginalContractNumberReturns the value of
     * field 'suppOriginalContractNumber'.
     * 
     * @return the value of field 'suppOriginalContractNumber'.
     */
    public java.lang.String getSuppOriginalContractNumber()
    {
        return this._suppOriginalContractNumber;
    } //-- java.lang.String getSuppOriginalContractNumber() 

    /**
     * Method getTaxReportingGroupReturns the value of field
     * 'taxReportingGroup'.
     * 
     * @return the value of field 'taxReportingGroup'.
     */
    public java.lang.String getTaxReportingGroup()
    {
        return this._taxReportingGroup;
    } //-- java.lang.String getTaxReportingGroup() 

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
     * Method getExpiryDate returns the value of field
     * 'expiryDate'.
     * 
     * @return the value of field 'expiryDate'.
     */
    public java.lang.String getExpiryDate()
    {
        return this._expiryDate;
    } 

    /**   
     * Method setLocation
     * Sets the value of field 'location'.
     * 
     * @param location the value of field 'location'
     */
    public void setLocation(String location)
    {
        this._location = location;
        
        super.setVoChanged(true);
    }
    
    /**
     * Method getLocation
     * Returns the value of field 'location'.
     * 
     * @return the value of field 'location'.
     */
    public String getLocation()
    {
        return this._location;
    }
    
    /**   
     * Method setSequence
     * Sets the value of field 'sequence'.
     * 
     * @param the value of field 'sequence'
     */
    public void setSequence(String sequence)
    {
        this._sequence = sequence;
        
        super.setVoChanged(true);
    }
    
    /**
     * Method getSequence
     * Returns the value of field 'sequence'.
     * 
     * @return the value of field 'sequence'.
     */
    public String getSequence()
    {
        return this._sequence;
    }
    
    /**   
     * Method setIndivAnnPremium
     * Sets the value of field 'indivAnnPremium'.
     * 
     * @param the value of field 'indivAnnPremium'
     */
    public void setIndivAnnPremium(java.math.BigDecimal indivAnnPremium)
    {
        this._indivAnnPremium = indivAnnPremium;
        
        super.setVoChanged(true);
    }
    
    /**
     * Method getIndivAnnPremium
     * Returns the value of field 'indivAnnPremium'.
     * 
     * @return the value of field 'indivAnnPremium'.
     */
    public java.math.BigDecimal getIndivAnnPremium()
    {
        return this._indivAnnPremium;
    }
    
    /**
     * Method getTotalActiveBeneficiariesReturns the value of field
     * 'totalActiveBeneficiaries'.
     * 
     * @return the value of field 'totalActiveBeneficiaries'.
     */
    public int getTotalActiveBeneficiaries()
    {
        return this._totalActiveBeneficiaries;
    } //-- int getTotalActiveBeneficiaries() 

    /**
     * Method getTotalFaceAmountReturns the value of field
     * 'totalFaceAmount'.
     * 
     * @return the value of field 'totalFaceAmount'.
     */
    public java.math.BigDecimal getTotalFaceAmount()
    {
        return this._totalFaceAmount;
    } //-- java.math.BigDecimal getTotalFaceAmount() 

    /**
     * Method getUnderwritingClassCTReturns the value of field
     * 'underwritingClassCT'.
     * 
     * @return the value of field 'underwritingClassCT'.
     */
    public java.lang.String getUnderwritingClassCT()
    {
        return this._underwritingClassCT;
    } //-- java.lang.String getUnderwritingClassCT() 

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
     * Method getValueAtIssueVO
     * 
     * @param index
     */
    public edit.common.vo.ValueAtIssueVO getValueAtIssueVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _valueAtIssueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ValueAtIssueVO) _valueAtIssueVOList.elementAt(index);
    } //-- edit.common.vo.ValueAtIssueVO getValueAtIssueVO(int) 

    /**
     * Method getValueAtIssueVO
     */
    public edit.common.vo.ValueAtIssueVO[] getValueAtIssueVO()
    {
        int size = _valueAtIssueVOList.size();
        edit.common.vo.ValueAtIssueVO[] mArray = new edit.common.vo.ValueAtIssueVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ValueAtIssueVO) _valueAtIssueVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ValueAtIssueVO[] getValueAtIssueVO() 

    /**
     * Method getValueAtIssueVOCount
     */
    public int getValueAtIssueVOCount()
    {
        return _valueAtIssueVOList.size();
    } //-- int getValueAtIssueVOCount() 

    /**
     * Method getWaiveFreeLookIndicatorReturns the value of field
     * 'waiveFreeLookIndicator'.
     * 
     * @return the value of field 'waiveFreeLookIndicator'.
     */
    public java.lang.String getWaiveFreeLookIndicator()
    {
        return this._waiveFreeLookIndicator;
    } //-- java.lang.String getWaiveFreeLookIndicator() 

    /**
     * Method getWaiverInEffectReturns the value of field
     * 'waiverInEffect'.
     * 
     * @return the value of field 'waiverInEffect'.
     */
    public java.lang.String getWaiverInEffect()
    {
        return this._waiverInEffect;
    } //-- java.lang.String getWaiverInEffect() 

    /**
     * Method getWorksheetTypeCTReturns the value of field
     * 'worksheetTypeCT'.
     * 
     * @return the value of field 'worksheetTypeCT'.
     */
    public java.lang.String getWorksheetTypeCT()
    {
        return this._worksheetTypeCT;
    } //-- java.lang.String getWorksheetTypeCT() 

    /**
     * Method hasAgeAtIssue
     */
    public boolean hasAgeAtIssue()
    {
        return this._has_ageAtIssue;
    } //-- boolean hasAgeAtIssue() 

    /**
     * Method hasBatchContractSetupFK
     */
    public boolean hasBatchContractSetupFK()
    {
        return this._has_batchContractSetupFK;
    } //-- boolean hasBatchContractSetupFK() 

    /**
     * Method hasBillScheduleFK
     */
    public boolean hasBillScheduleFK()
    {
        return this._has_billScheduleFK;
    } //-- boolean hasBillScheduleFK() 

    /**
     * Method hasCommissionPhaseID
     */
    public boolean hasCommissionPhaseID()
    {
        return this._has_commissionPhaseID;
    } //-- boolean hasCommissionPhaseID() 

    /**
     * Method hasConsecutiveAPLCount
     */
    public boolean hasConsecutiveAPLCount()
    {
        return this._has_consecutiveAPLCount;
    } //-- boolean hasConsecutiveAPLCount() 

    /**
     * Method hasContractGroupFK
     */
    public boolean hasContractGroupFK()
    {
        return this._has_contractGroupFK;
    } //-- boolean hasContractGroupFK() 

    /**
     * Method hasDepartmentLocationFK
     */
    public boolean hasDepartmentLocationFK()
    {
        return this._has_departmentLocationFK;
    } //-- boolean hasDepartmentLocationFK() 

    /**
     * Method hasEOBMultiple
     */
    public boolean hasEOBMultiple()
    {
        return this._has_EOBMultiple;
    } //-- boolean hasEOBMultiple() 

    /**
     * Method hasFreeLookDaysOverride
     */
    public boolean hasFreeLookDaysOverride()
    {
        return this._has_freeLookDaysOverride;
    } //-- boolean hasFreeLookDaysOverride() 

    /**
     * Method hasMasterContractFK
     */
    public boolean hasMasterContractFK()
    {
        return this._has_masterContractFK;
    } //-- boolean hasMasterContractFK() 

    /**
     * Method hasOriginalContractGroupFK
     */
    public boolean hasOriginalContractGroupFK()
    {
        return this._has_originalContractGroupFK;
    } //-- boolean hasOriginalContractGroupFK() 

    /**
     * Method hasPriorContractGroupFK
     */
    public boolean hasPriorContractGroupFK()
    {
        return this._has_priorContractGroupFK;
    } //-- boolean hasPriorContractGroupFK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

    /**
     * Method hasRemainingBeneficiaries
     */
    public boolean hasRemainingBeneficiaries()
    {
        return this._has_remainingBeneficiaries;
    } //-- boolean hasRemainingBeneficiaries() 

    /**
     * Method hasRiderNumber
     */
    public boolean hasRiderNumber()
    {
        return this._has_riderNumber;
    } //-- boolean hasRiderNumber() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasSegmentPK
     */
    public boolean hasSegmentPK()
    {
        return this._has_segmentPK;
    } //-- boolean hasSegmentPK() 

    /**
     * Method hasTotalActiveBeneficiaries
     */
    public boolean hasTotalActiveBeneficiaries()
    {
        return this._has_totalActiveBeneficiaries;
    } //-- boolean hasTotalActiveBeneficiaries() 

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
     * Method removeAgentHierarchyVO
     * 
     * @param index
     */
    public edit.common.vo.AgentHierarchyVO removeAgentHierarchyVO(int index)
    {
        java.lang.Object obj = _agentHierarchyVOList.elementAt(index);
        _agentHierarchyVOList.removeElementAt(index);
        return (edit.common.vo.AgentHierarchyVO) obj;
    } //-- edit.common.vo.AgentHierarchyVO removeAgentHierarchyVO(int) 

    /**
     * Method removeAllAgentHierarchyVO
     */
    public void removeAllAgentHierarchyVO()
    {
        _agentHierarchyVOList.removeAllElements();
    } //-- void removeAllAgentHierarchyVO() 

    /**
     * Method removeAllBillVO
     */
    public void removeAllBillVO()
    {
        _billVOList.removeAllElements();
    } //-- void removeAllBillVO() 

    /**
     * Method removeAllContractClientVO
     */
    public void removeAllContractClientVO()
    {
        _contractClientVOList.removeAllElements();
    } //-- void removeAllContractClientVO() 

    /**
     * Method removeAllContractRequirementVO
     */
    public void removeAllContractRequirementVO()
    {
        _contractRequirementVOList.removeAllElements();
    } //-- void removeAllContractRequirementVO() 

    /**
     * Method removeAllContractSetupVO
     */
    public void removeAllContractSetupVO()
    {
        _contractSetupVOList.removeAllElements();
    } //-- void removeAllContractSetupVO() 

    /**
     * Method removeAllContractTreatyVO
     */
    public void removeAllContractTreatyVO()
    {
        _contractTreatyVOList.removeAllElements();
    } //-- void removeAllContractTreatyVO() 

    /**
     * Method removeAllDepositsVO
     */
    public void removeAllDepositsVO()
    {
        _depositsVOList.removeAllElements();
    } //-- void removeAllDepositsVO() 

    /**
     * Method removeAllInherentRiderVO
     */
    public void removeAllInherentRiderVO()
    {
        _inherentRiderVOList.removeAllElements();
    } //-- void removeAllInherentRiderVO() 

    /**
     * Method removeAllInvestmentVO
     */
    public void removeAllInvestmentVO()
    {
        _investmentVOList.removeAllElements();
    } //-- void removeAllInvestmentVO() 

    /**
     * Method removeAllLifeVO
     */
    public void removeAllLifeVO()
    {
        _lifeVOList.removeAllElements();
    } //-- void removeAllLifeVO() 

    /**
     * Method removeAllNoteReminderVO
     */
    public void removeAllNoteReminderVO()
    {
        _noteReminderVOList.removeAllElements();
    } //-- void removeAllNoteReminderVO() 

    /**
     * Method removeAllPayoutVO
     */
    public void removeAllPayoutVO()
    {
        _payoutVOList.removeAllElements();
    } //-- void removeAllPayoutVO() 

    /**
     * Method removeAllPremiumDueVO
     */
    public void removeAllPremiumDueVO()
    {
        _premiumDueVOList.removeAllElements();
    } //-- void removeAllPremiumDueVO() 

    /**
     * Method removeAllRealTimeActivityVO
     */
    public void removeAllRealTimeActivityVO()
    {
        _realTimeActivityVOList.removeAllElements();
    } //-- void removeAllRealTimeActivityVO() 

    /**
     * Method removeAllRequiredMinDistributionVO
     */
    public void removeAllRequiredMinDistributionVO()
    {
        _requiredMinDistributionVOList.removeAllElements();
    } //-- void removeAllRequiredMinDistributionVO() 

    /**
     * Method removeAllSegmentBackupVO
     */
    public void removeAllSegmentBackupVO()
    {
        _segmentBackupVOList.removeAllElements();
    } //-- void removeAllSegmentBackupVO() 

    /**
     * Method removeAllSegmentVO
     */
    public void removeAllSegmentVO()
    {
        _segmentVOList.removeAllElements();
    } //-- void removeAllSegmentVO() 

    /**
     * Method removeAllValueAtIssueVO
     */
    public void removeAllValueAtIssueVO()
    {
        _valueAtIssueVOList.removeAllElements();
    } //-- void removeAllValueAtIssueVO() 

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
     * Method removeContractClientVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientVO removeContractClientVO(int index)
    {
        java.lang.Object obj = _contractClientVOList.elementAt(index);
        _contractClientVOList.removeElementAt(index);
        return (edit.common.vo.ContractClientVO) obj;
    } //-- edit.common.vo.ContractClientVO removeContractClientVO(int) 

    /**
     * Method removeContractRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.ContractRequirementVO removeContractRequirementVO(int index)
    {
        java.lang.Object obj = _contractRequirementVOList.elementAt(index);
        _contractRequirementVOList.removeElementAt(index);
        return (edit.common.vo.ContractRequirementVO) obj;
    } //-- edit.common.vo.ContractRequirementVO removeContractRequirementVO(int) 

    /**
     * Method removeContractSetupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractSetupVO removeContractSetupVO(int index)
    {
        java.lang.Object obj = _contractSetupVOList.elementAt(index);
        _contractSetupVOList.removeElementAt(index);
        return (edit.common.vo.ContractSetupVO) obj;
    } //-- edit.common.vo.ContractSetupVO removeContractSetupVO(int) 

    /**
     * Method removeContractTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.ContractTreatyVO removeContractTreatyVO(int index)
    {
        java.lang.Object obj = _contractTreatyVOList.elementAt(index);
        _contractTreatyVOList.removeElementAt(index);
        return (edit.common.vo.ContractTreatyVO) obj;
    } //-- edit.common.vo.ContractTreatyVO removeContractTreatyVO(int) 

    /**
     * Method removeDepositsVO
     * 
     * @param index
     */
    public edit.common.vo.DepositsVO removeDepositsVO(int index)
    {
        java.lang.Object obj = _depositsVOList.elementAt(index);
        _depositsVOList.removeElementAt(index);
        return (edit.common.vo.DepositsVO) obj;
    } //-- edit.common.vo.DepositsVO removeDepositsVO(int) 

    /**
     * Method removeInherentRiderVO
     * 
     * @param index
     */
    public edit.common.vo.InherentRiderVO removeInherentRiderVO(int index)
    {
        java.lang.Object obj = _inherentRiderVOList.elementAt(index);
        _inherentRiderVOList.removeElementAt(index);
        return (edit.common.vo.InherentRiderVO) obj;
    } //-- edit.common.vo.InherentRiderVO removeInherentRiderVO(int) 

    /**
     * Method removeInvestmentVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentVO removeInvestmentVO(int index)
    {
        java.lang.Object obj = _investmentVOList.elementAt(index);
        _investmentVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentVO) obj;
    } //-- edit.common.vo.InvestmentVO removeInvestmentVO(int) 

    /**
     * Method removeLifeVO
     * 
     * @param index
     */
    public edit.common.vo.LifeVO removeLifeVO(int index)
    {
        java.lang.Object obj = _lifeVOList.elementAt(index);
        _lifeVOList.removeElementAt(index);
        return (edit.common.vo.LifeVO) obj;
    } //-- edit.common.vo.LifeVO removeLifeVO(int) 

    /**
     * Method removeNoteReminderVO
     * 
     * @param index
     */
    public edit.common.vo.NoteReminderVO removeNoteReminderVO(int index)
    {
        java.lang.Object obj = _noteReminderVOList.elementAt(index);
        _noteReminderVOList.removeElementAt(index);
        return (edit.common.vo.NoteReminderVO) obj;
    } //-- edit.common.vo.NoteReminderVO removeNoteReminderVO(int) 

    /**
     * Method removePayoutVO
     * 
     * @param index
     */
    public edit.common.vo.PayoutVO removePayoutVO(int index)
    {
        java.lang.Object obj = _payoutVOList.elementAt(index);
        _payoutVOList.removeElementAt(index);
        return (edit.common.vo.PayoutVO) obj;
    } //-- edit.common.vo.PayoutVO removePayoutVO(int) 

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
     * Method removeRealTimeActivityVO
     * 
     * @param index
     */
    public edit.common.vo.RealTimeActivityVO removeRealTimeActivityVO(int index)
    {
        java.lang.Object obj = _realTimeActivityVOList.elementAt(index);
        _realTimeActivityVOList.removeElementAt(index);
        return (edit.common.vo.RealTimeActivityVO) obj;
    } //-- edit.common.vo.RealTimeActivityVO removeRealTimeActivityVO(int) 

    /**
     * Method removeRequiredMinDistributionVO
     * 
     * @param index
     */
    public edit.common.vo.RequiredMinDistributionVO removeRequiredMinDistributionVO(int index)
    {
        java.lang.Object obj = _requiredMinDistributionVOList.elementAt(index);
        _requiredMinDistributionVOList.removeElementAt(index);
        return (edit.common.vo.RequiredMinDistributionVO) obj;
    } //-- edit.common.vo.RequiredMinDistributionVO removeRequiredMinDistributionVO(int) 

    /**
     * Method removeSegmentBackupVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentBackupVO removeSegmentBackupVO(int index)
    {
        java.lang.Object obj = _segmentBackupVOList.elementAt(index);
        _segmentBackupVOList.removeElementAt(index);
        return (edit.common.vo.SegmentBackupVO) obj;
    } //-- edit.common.vo.SegmentBackupVO removeSegmentBackupVO(int) 

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
     * Method removeValueAtIssueVO
     * 
     * @param index
     */
    public edit.common.vo.ValueAtIssueVO removeValueAtIssueVO(int index)
    {
        java.lang.Object obj = _valueAtIssueVOList.elementAt(index);
        _valueAtIssueVOList.removeElementAt(index);
        return (edit.common.vo.ValueAtIssueVO) obj;
    } //-- edit.common.vo.ValueAtIssueVO removeValueAtIssueVO(int) 

    /**
     * Method setAdvanceFinalNotifySets the value of field
     * 'advanceFinalNotify'.
     * 
     * @param advanceFinalNotify the value of field
     * 'advanceFinalNotify'.
     */
    public void setAdvanceFinalNotify(java.lang.String advanceFinalNotify)
    {
        this._advanceFinalNotify = advanceFinalNotify;
        
        super.setVoChanged(true);
    } //-- void setAdvanceFinalNotify(java.lang.String) 

    /**
     * Method setAgeAtIssueSets the value of field 'ageAtIssue'.
     * 
     * @param ageAtIssue the value of field 'ageAtIssue'.
     */
    public void setAgeAtIssue(int ageAtIssue)
    {
        this._ageAtIssue = ageAtIssue;
        
        super.setVoChanged(true);
        this._has_ageAtIssue = true;
    } //-- void setAgeAtIssue(int) 

    /**
     * Method setAgentHierarchyVO
     * 
     * @param index
     * @param vAgentHierarchyVO
     */
    public void setAgentHierarchyVO(int index, edit.common.vo.AgentHierarchyVO vAgentHierarchyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentHierarchyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentHierarchyVO.setParentVO(this.getClass(), this);
        _agentHierarchyVOList.setElementAt(vAgentHierarchyVO, index);
    } //-- void setAgentHierarchyVO(int, edit.common.vo.AgentHierarchyVO) 

    /**
     * Method setAgentHierarchyVO
     * 
     * @param agentHierarchyVOArray
     */
    public void setAgentHierarchyVO(edit.common.vo.AgentHierarchyVO[] agentHierarchyVOArray)
    {
        //-- copy array
        _agentHierarchyVOList.removeAllElements();
        for (int i = 0; i < agentHierarchyVOArray.length; i++) {
            agentHierarchyVOArray[i].setParentVO(this.getClass(), this);
            _agentHierarchyVOList.addElement(agentHierarchyVOArray[i]);
        }
    } //-- void setAgentHierarchyVO(edit.common.vo.AgentHierarchyVO) 

    /**
     * Method setAmountSets the value of field 'amount'.
     * 
     * @param amount the value of field 'amount'.
     */
    public void setAmount(java.math.BigDecimal amount)
    {
        this._amount = amount;
        
        super.setVoChanged(true);
    } //-- void setAmount(java.math.BigDecimal) 

    /**
     * Method setAnnualInsuranceAmountSets the value of field
     * 'annualInsuranceAmount'.
     * 
     * @param annualInsuranceAmount the value of field
     * 'annualInsuranceAmount'.
     */
    public void setAnnualInsuranceAmount(java.math.BigDecimal annualInsuranceAmount)
    {
        this._annualInsuranceAmount = annualInsuranceAmount;
        
        super.setVoChanged(true);
    } //-- void setAnnualInsuranceAmount(java.math.BigDecimal) 

    /**
     * Method setAnnualInvestmentAmountSets the value of field
     * 'annualInvestmentAmount'.
     * 
     * @param annualInvestmentAmount the value of field
     * 'annualInvestmentAmount'.
     */
    public void setAnnualInvestmentAmount(java.math.BigDecimal annualInvestmentAmount)
    {
        this._annualInvestmentAmount = annualInvestmentAmount;
        
        super.setVoChanged(true);
    } //-- void setAnnualInvestmentAmount(java.math.BigDecimal) 

    /**
     * Method setAnnualPremiumSets the value of field
     * 'annualPremium'.
     * 
     * @param annualPremium the value of field 'annualPremium'.
     */
    public void setAnnualPremium(java.math.BigDecimal annualPremium)
    {
        this._annualPremium = annualPremium;
        
        super.setVoChanged(true);
    } //-- void setAnnualPremium(java.math.BigDecimal) 

    /**
     * Method setAnnuitizationValueSets the value of field
     * 'annuitizationValue'.
     * 
     * @param annuitizationValue the value of field
     * 'annuitizationValue'.
     */
    public void setAnnuitizationValue(java.math.BigDecimal annuitizationValue)
    {
        this._annuitizationValue = annuitizationValue;
        
        super.setVoChanged(true);
    } //-- void setAnnuitizationValue(java.math.BigDecimal) 

    /**
     * Method setApplicationNumberSets the value of field
     * 'applicationNumber'.
     * 
     * @param applicationNumber the value of field
     * 'applicationNumber'.
     */
    public void setApplicationNumber(java.lang.String applicationNumber)
    {
        this._applicationNumber = applicationNumber;
        
        super.setVoChanged(true);
    } //-- void setApplicationNumber(java.lang.String) 

    /**
     * Method setApplicationReceivedDateSets the value of field
     * 'applicationReceivedDate'.
     * 
     * @param applicationReceivedDate the value of field
     * 'applicationReceivedDate'.
     */
    public void setApplicationReceivedDate(java.lang.String applicationReceivedDate)
    {
        this._applicationReceivedDate = applicationReceivedDate;
        
        super.setVoChanged(true);
    } //-- void setApplicationReceivedDate(java.lang.String) 

    /**
     * Method setApplicationSignedDateSets the value of field
     * 'applicationSignedDate'.
     * 
     * @param applicationSignedDate the value of field
     * 'applicationSignedDate'.
     */
    public void setApplicationSignedDate(java.lang.String applicationSignedDate)
    {
        this._applicationSignedDate = applicationSignedDate;
        
        super.setVoChanged(true);
    } //-- void setApplicationSignedDate(java.lang.String) 

    /**
     * Method setApplicationSignedStateCTSets the value of field
     * 'applicationSignedStateCT'.
     * 
     * @param applicationSignedStateCT the value of field
     * 'applicationSignedStateCT'.
     */
    public void setApplicationSignedStateCT(java.lang.String applicationSignedStateCT)
    {
        this._applicationSignedStateCT = applicationSignedStateCT;
        
        super.setVoChanged(true);
    } //-- void setApplicationSignedStateCT(java.lang.String) 

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
     * Method setBatchContractSetupFKSets the value of field
     * 'batchContractSetupFK'.
     * 
     * @param batchContractSetupFK the value of field
     * 'batchContractSetupFK'.
     */
    public void setBatchContractSetupFK(long batchContractSetupFK)
    {
        this._batchContractSetupFK = batchContractSetupFK;
        
        super.setVoChanged(true);
        this._has_batchContractSetupFK = true;
    } //-- void setBatchContractSetupFK(long) 

    /**
     * Method setBillScheduleChangeTypeSets the value of field
     * 'billScheduleChangeType'.
     * 
     * @param billScheduleChangeType the value of field
     * 'billScheduleChangeType'.
     */
    public void setBillScheduleChangeType(java.lang.String billScheduleChangeType)
    {
        this._billScheduleChangeType = billScheduleChangeType;
        
        super.setVoChanged(true);
    } //-- void setBillScheduleChangeType(java.lang.String) 

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
     * Method setCasetrackingOptionCTSets the value of field
     * 'casetrackingOptionCT'.
     * 
     * @param casetrackingOptionCT the value of field
     * 'casetrackingOptionCT'.
     */
    public void setCasetrackingOptionCT(java.lang.String casetrackingOptionCT)
    {
        this._casetrackingOptionCT = casetrackingOptionCT;
        
        super.setVoChanged(true);
    } //-- void setCasetrackingOptionCT(java.lang.String) 

    /**
     * Method setCashWithAppIndSets the value of field
     * 'cashWithAppInd'.
     * 
     * @param cashWithAppInd the value of field 'cashWithAppInd'.
     */
    public void setCashWithAppInd(java.lang.String cashWithAppInd)
    {
        this._cashWithAppInd = cashWithAppInd;
        
        super.setVoChanged(true);
    } //-- void setCashWithAppInd(java.lang.String) 

    /**
     * Method setChargeCodeStatusSets the value of field
     * 'chargeCodeStatus'.
     * 
     * @param chargeCodeStatus the value of field 'chargeCodeStatus'
     */
    public void setChargeCodeStatus(java.lang.String chargeCodeStatus)
    {
        this._chargeCodeStatus = chargeCodeStatus;
        
        super.setVoChanged(true);
    } //-- void setChargeCodeStatus(java.lang.String) 

    /**
     * Method setChargeDeductAmountSets the value of field
     * 'chargeDeductAmount'.
     * 
     * @param chargeDeductAmount the value of field
     * 'chargeDeductAmount'.
     */
    public void setChargeDeductAmount(java.math.BigDecimal chargeDeductAmount)
    {
        this._chargeDeductAmount = chargeDeductAmount;
        
        super.setVoChanged(true);
    } //-- void setChargeDeductAmount(java.math.BigDecimal) 

    /**
     * Method setChargeDeductDivisionIndSets the value of field
     * 'chargeDeductDivisionInd'.
     * 
     * @param chargeDeductDivisionInd the value of field
     * 'chargeDeductDivisionInd'.
     */
    public void setChargeDeductDivisionInd(java.lang.String chargeDeductDivisionInd)
    {
        this._chargeDeductDivisionInd = chargeDeductDivisionInd;
        
        super.setVoChanged(true);
    } //-- void setChargeDeductDivisionInd(java.lang.String) 

    /**
     * Method setChargesSets the value of field 'charges'.
     * 
     * @param charges the value of field 'charges'.
     */
    public void setCharges(java.math.BigDecimal charges)
    {
        this._charges = charges;
        
        super.setVoChanged(true);
    } //-- void setCharges(java.math.BigDecimal) 

    /**
     * Method setClaimStopDateSets the value of field
     * 'claimStopDate'.
     * 
     * @param claimStopDate the value of field 'claimStopDate'.
     */
    public void setClaimStopDate(java.lang.String claimStopDate)
    {
        this._claimStopDate = claimStopDate;
        
        super.setVoChanged(true);
    } //-- void setClaimStopDate(java.lang.String) 

    /**
     * Method setClientUpdateSets the value of field
     * 'clientUpdate'.
     * 
     * @param clientUpdate the value of field 'clientUpdate'.
     */
    public void setClientUpdate(java.lang.String clientUpdate)
    {
        this._clientUpdate = clientUpdate;
        
        super.setVoChanged(true);
    } //-- void setClientUpdate(java.lang.String) 

    /**
     * Method setCommissionPhaseIDSets the value of field
     * 'commissionPhaseID'.
     * 
     * @param commissionPhaseID the value of field
     * 'commissionPhaseID'.
     */
    public void setCommissionPhaseID(int commissionPhaseID)
    {
        this._commissionPhaseID = commissionPhaseID;
        
        super.setVoChanged(true);
        this._has_commissionPhaseID = true;
    } //-- void setCommissionPhaseID(int) 

    /**
     * Method setCommissionPhaseOverrideSets the value of field
     * 'commissionPhaseOverride'.
     * 
     * @param commissionPhaseOverride the value of field
     * 'commissionPhaseOverride'.
     */
    public void setCommissionPhaseOverride(java.lang.String commissionPhaseOverride)
    {
        this._commissionPhaseOverride = commissionPhaseOverride;
        
        super.setVoChanged(true);
    } //-- void setCommissionPhaseOverride(java.lang.String) 

    /**
     * Method setCommitmentAmountSets the value of field
     * 'commitmentAmount'.
     * 
     * @param commitmentAmount the value of field 'commitmentAmount'
     */
    public void setCommitmentAmount(java.math.BigDecimal commitmentAmount)
    {
        this._commitmentAmount = commitmentAmount;
        
        super.setVoChanged(true);
    } //-- void setCommitmentAmount(java.math.BigDecimal) 

    /**
     * Method setCommitmentIndicatorSets the value of field
     * 'commitmentIndicator'.
     * 
     * @param commitmentIndicator the value of field
     * 'commitmentIndicator'.
     */
    public void setCommitmentIndicator(java.lang.String commitmentIndicator)
    {
        this._commitmentIndicator = commitmentIndicator;
        
        super.setVoChanged(true);
    } //-- void setCommitmentIndicator(java.lang.String) 

    /**
     * Method setConsecutiveAPLCountSets the value of field
     * 'consecutiveAPLCount'.
     * 
     * @param consecutiveAPLCount the value of field
     * 'consecutiveAPLCount'.
     */
    public void setConsecutiveAPLCount(int consecutiveAPLCount)
    {
        this._consecutiveAPLCount = consecutiveAPLCount;
        
        super.setVoChanged(true);
        this._has_consecutiveAPLCount = true;
    } //-- void setConsecutiveAPLCount(int) 

    /**
     * Method setContractClientVO
     * 
     * @param index
     * @param vContractClientVO
     */
    public void setContractClientVO(int index, edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.setElementAt(vContractClientVO, index);
    } //-- void setContractClientVO(int, edit.common.vo.ContractClientVO) 

    /**
     * Method setContractClientVO
     * 
     * @param contractClientVOArray
     */
    public void setContractClientVO(edit.common.vo.ContractClientVO[] contractClientVOArray)
    {
        //-- copy array
        _contractClientVOList.removeAllElements();
        for (int i = 0; i < contractClientVOArray.length; i++) {
            contractClientVOArray[i].setParentVO(this.getClass(), this);
            _contractClientVOList.addElement(contractClientVOArray[i]);
        }
    } //-- void setContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method setContractGroupFKSets the value of field
     * 'contractGroupFK'.
     * 
     * @param contractGroupFK the value of field 'contractGroupFK'.
     */
    public void setContractGroupFK(long contractGroupFK)
    {
        this._contractGroupFK = contractGroupFK;
        
        super.setVoChanged(true);
        this._has_contractGroupFK = true;
    } //-- void setContractGroupFK(long) 

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
     * Method setContractRequirementVO
     * 
     * @param index
     * @param vContractRequirementVO
     */
    public void setContractRequirementVO(int index, edit.common.vo.ContractRequirementVO vContractRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractRequirementVO.setParentVO(this.getClass(), this);
        _contractRequirementVOList.setElementAt(vContractRequirementVO, index);
    } //-- void setContractRequirementVO(int, edit.common.vo.ContractRequirementVO) 

    /**
     * Method setContractRequirementVO
     * 
     * @param contractRequirementVOArray
     */
    public void setContractRequirementVO(edit.common.vo.ContractRequirementVO[] contractRequirementVOArray)
    {
        //-- copy array
        _contractRequirementVOList.removeAllElements();
        for (int i = 0; i < contractRequirementVOArray.length; i++) {
            contractRequirementVOArray[i].setParentVO(this.getClass(), this);
            _contractRequirementVOList.addElement(contractRequirementVOArray[i]);
        }
    } //-- void setContractRequirementVO(edit.common.vo.ContractRequirementVO) 

    /**
     * Method setContractSetupVO
     * 
     * @param index
     * @param vContractSetupVO
     */
    public void setContractSetupVO(int index, edit.common.vo.ContractSetupVO vContractSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractSetupVO.setParentVO(this.getClass(), this);
        _contractSetupVOList.setElementAt(vContractSetupVO, index);
    } //-- void setContractSetupVO(int, edit.common.vo.ContractSetupVO) 

    /**
     * Method setContractSetupVO
     * 
     * @param contractSetupVOArray
     */
    public void setContractSetupVO(edit.common.vo.ContractSetupVO[] contractSetupVOArray)
    {
        //-- copy array
        _contractSetupVOList.removeAllElements();
        for (int i = 0; i < contractSetupVOArray.length; i++) {
            contractSetupVOArray[i].setParentVO(this.getClass(), this);
            _contractSetupVOList.addElement(contractSetupVOArray[i]);
        }
    } //-- void setContractSetupVO(edit.common.vo.ContractSetupVO) 

    /**
     * Method setContractTreatyVO
     * 
     * @param index
     * @param vContractTreatyVO
     */
    public void setContractTreatyVO(int index, edit.common.vo.ContractTreatyVO vContractTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractTreatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractTreatyVO.setParentVO(this.getClass(), this);
        _contractTreatyVOList.setElementAt(vContractTreatyVO, index);
    } //-- void setContractTreatyVO(int, edit.common.vo.ContractTreatyVO) 

    /**
     * Method setContractTreatyVO
     * 
     * @param contractTreatyVOArray
     */
    public void setContractTreatyVO(edit.common.vo.ContractTreatyVO[] contractTreatyVOArray)
    {
        //-- copy array
        _contractTreatyVOList.removeAllElements();
        for (int i = 0; i < contractTreatyVOArray.length; i++) {
            contractTreatyVOArray[i].setParentVO(this.getClass(), this);
            _contractTreatyVOList.addElement(contractTreatyVOArray[i]);
        }
    } //-- void setContractTreatyVO(edit.common.vo.ContractTreatyVO) 

    /**
     * Method setContractTypeCTSets the value of field
     * 'contractTypeCT'.
     * 
     * @param contractTypeCT the value of field 'contractTypeCT'.
     */
    public void setContractTypeCT(java.lang.String contractTypeCT)
    {
        this._contractTypeCT = contractTypeCT;
        
        super.setVoChanged(true);
    } //-- void setContractTypeCT(java.lang.String) 

    /**
     * Method setConversionDateSets the value of field
     * 'conversionDate'.
     * 
     * @param conversionDate the value of field 'conversionDate'.
     */
    public void setConversionDate(java.lang.String conversionDate)
    {
        this._conversionDate = conversionDate;
        
        super.setVoChanged(true);
    } //-- void setConversionDate(java.lang.String) 

    /**
     * Method setCostBasisSets the value of field 'costBasis'.
     * 
     * @param costBasis the value of field 'costBasis'.
     */
    public void setCostBasis(java.math.BigDecimal costBasis)
    {
        this._costBasis = costBasis;
        
        super.setVoChanged(true);
    } //-- void setCostBasis(java.math.BigDecimal) 

    /**
     * Method setCreationDateSets the value of field
     * 'creationDate'.
     * 
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(java.lang.String creationDate)
    {
        this._creationDate = creationDate;
        
        super.setVoChanged(true);
    } //-- void setCreationDate(java.lang.String) 

    /**
     * Method setCreationOperatorSets the value of field
     * 'creationOperator'.
     * 
     * @param creationOperator the value of field 'creationOperator'
     */
    public void setCreationOperator(java.lang.String creationOperator)
    {
        this._creationOperator = creationOperator;
        
        super.setVoChanged(true);
    } //-- void setCreationOperator(java.lang.String) 

    /**
     * Method setDateInEffectSets the value of field
     * 'dateInEffect'.
     * 
     * @param dateInEffect the value of field 'dateInEffect'.
     */
    public void setDateInEffect(java.lang.String dateInEffect)
    {
        this._dateInEffect = dateInEffect;
        
        super.setVoChanged(true);
    } //-- void setDateInEffect(java.lang.String) 

    /**
     * Method setDateOfDeathValueSets the value of field
     * 'dateOfDeathValue'.
     * 
     * @param dateOfDeathValue the value of field 'dateOfDeathValue'
     */
    public void setDateOfDeathValue(java.math.BigDecimal dateOfDeathValue)
    {
        this._dateOfDeathValue = dateOfDeathValue;
        
        super.setVoChanged(true);
    } //-- void setDateOfDeathValue(java.math.BigDecimal) 

    /**
     * Method setDeductionAmountEffectiveDateSets the value of
     * field 'deductionAmountEffectiveDate'.
     * 
     * @param deductionAmountEffectiveDate the value of field
     * 'deductionAmountEffectiveDate'.
     */
    public void setDeductionAmountEffectiveDate(java.lang.String deductionAmountEffectiveDate)
    {
        this._deductionAmountEffectiveDate = deductionAmountEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setDeductionAmountEffectiveDate(java.lang.String) 

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
     * Method setDepartmentLocationFKSets the value of field
     * 'departmentLocationFK'.
     * 
     * @param departmentLocationFK the value of field
     * 'departmentLocationFK'.
     */
    public void setDepartmentLocationFK(long departmentLocationFK)
    {
        this._departmentLocationFK = departmentLocationFK;
        
        super.setVoChanged(true);
        this._has_departmentLocationFK = true;
    } //-- void setDepartmentLocationFK(long) 

    /**
     * Method setDepositsVO
     * 
     * @param index
     * @param vDepositsVO
     */
    public void setDepositsVO(int index, edit.common.vo.DepositsVO vDepositsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _depositsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vDepositsVO.setParentVO(this.getClass(), this);
        _depositsVOList.setElementAt(vDepositsVO, index);
    } //-- void setDepositsVO(int, edit.common.vo.DepositsVO) 

    /**
     * Method setDepositsVO
     * 
     * @param depositsVOArray
     */
    public void setDepositsVO(edit.common.vo.DepositsVO[] depositsVOArray)
    {
        //-- copy array
        _depositsVOList.removeAllElements();
        for (int i = 0; i < depositsVOArray.length; i++) {
            depositsVOArray[i].setParentVO(this.getClass(), this);
            _depositsVOList.addElement(depositsVOArray[i]);
        }
    } //-- void setDepositsVO(edit.common.vo.DepositsVO) 

    /**
     * Method setDialableSalesLoadPercentageSets the value of field
     * 'dialableSalesLoadPercentage'.
     * 
     * @param dialableSalesLoadPercentage the value of field
     * 'dialableSalesLoadPercentage'.
     */
    public void setDialableSalesLoadPercentage(java.math.BigDecimal dialableSalesLoadPercentage)
    {
        this._dialableSalesLoadPercentage = dialableSalesLoadPercentage;
        
        super.setVoChanged(true);
    } //-- void setDialableSalesLoadPercentage(java.math.BigDecimal) 

    /**
     * Method setDismembermentPercentSets the value of field
     * 'dismembermentPercent'.
     * 
     * @param dismembermentPercent the value of field
     * 'dismembermentPercent'.
     */
    public void setDismembermentPercent(java.math.BigDecimal dismembermentPercent)
    {
        this._dismembermentPercent = dismembermentPercent;
        
        super.setVoChanged(true);
    } //-- void setDismembermentPercent(java.math.BigDecimal) 

    /**
     * Method setDividendOptionCTSets the value of field
     * 'dividendOptionCT'.
     * 
     * @param dividendOptionCT the value of field 'dividendOptionCT'
     */
    public void setDividendOptionCT(java.lang.String dividendOptionCT)
    {
        this._dividendOptionCT = dividendOptionCT;
        
        super.setVoChanged(true);
    } //-- void setDividendOptionCT(java.lang.String) 

    /**
     * Method setEOBCumSets the value of field 'EOBCum'.
     * 
     * @param EOBCum the value of field 'EOBCum'.
     */
    public void setEOBCum(java.math.BigDecimal EOBCum)
    {
        this._EOBCum = EOBCum;
        
        super.setVoChanged(true);
    } //-- void setEOBCum(java.math.BigDecimal) 

    /**
     * Method setEOBMaximumSets the value of field 'EOBMaximum'.
     * 
     * @param EOBMaximum the value of field 'EOBMaximum'.
     */
    public void setEOBMaximum(java.math.BigDecimal EOBMaximum)
    {
        this._EOBMaximum = EOBMaximum;
        
        super.setVoChanged(true);
    } //-- void setEOBMaximum(java.math.BigDecimal) 

    /**
     * Method setEOBMultipleSets the value of field 'EOBMultiple'.
     * 
     * @param EOBMultiple the value of field 'EOBMultiple'.
     */
    public void setEOBMultiple(int EOBMultiple)
    {
        this._EOBMultiple = EOBMultiple;
        
        super.setVoChanged(true);
        this._has_EOBMultiple = true;
    } //-- void setEOBMultiple(int) 

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
     * Method setEstateOfTheInsuredSets the value of field
     * 'estateOfTheInsured'.
     * 
     * @param estateOfTheInsured the value of field
     * 'estateOfTheInsured'.
     */
    public void setEstateOfTheInsured(java.lang.String estateOfTheInsured)
    {
        this._estateOfTheInsured = estateOfTheInsured;
        
        super.setVoChanged(true);
    } //-- void setEstateOfTheInsured(java.lang.String) 

    /**
     * Method setExchangeIndSets the value of field 'exchangeInd'.
     * 
     * @param exchangeInd the value of field 'exchangeInd'.
     */
    public void setExchangeInd(java.lang.String exchangeInd)
    {
        this._exchangeInd = exchangeInd;
        
        super.setVoChanged(true);
    } //-- void setExchangeInd(java.lang.String) 

    /**
     * Method setFeesSets the value of field 'fees'.
     * 
     * @param fees the value of field 'fees'.
     */
    public void setFees(java.math.BigDecimal fees)
    {
        this._fees = fees;
        
        super.setVoChanged(true);
    } //-- void setFees(java.math.BigDecimal) 

    /**
     * Method setFinalNotifyDateSets the value of field
     * 'finalNotifyDate'.
     * 
     * @param finalNotifyDate the value of field 'finalNotifyDate'.
     */
    public void setFinalNotifyDate(java.lang.String finalNotifyDate)
    {
        this._finalNotifyDate = finalNotifyDate;
        
        super.setVoChanged(true);
    } //-- void setFinalNotifyDate(java.lang.String) 

    /**
     * Method setFirstNotifyDateSets the value of field
     * 'firstNotifyDate'.
     * 
     * @param firstNotifyDate the value of field 'firstNotifyDate'.
     */
    public void setFirstNotifyDate(java.lang.String firstNotifyDate)
    {
        this._firstNotifyDate = firstNotifyDate;
        
        super.setVoChanged(true);
    } //-- void setFirstNotifyDate(java.lang.String) 

    /**
     * Method setFreeAmountSets the value of field 'freeAmount'.
     * 
     * @param freeAmount the value of field 'freeAmount'.
     */
    public void setFreeAmount(java.math.BigDecimal freeAmount)
    {
        this._freeAmount = freeAmount;
        
        super.setVoChanged(true);
    } //-- void setFreeAmount(java.math.BigDecimal) 

    /**
     * Method setFreeAmountRemainingSets the value of field
     * 'freeAmountRemaining'.
     * 
     * @param freeAmountRemaining the value of field
     * 'freeAmountRemaining'.
     */
    public void setFreeAmountRemaining(java.math.BigDecimal freeAmountRemaining)
    {
        this._freeAmountRemaining = freeAmountRemaining;
        
        super.setVoChanged(true);
    } //-- void setFreeAmountRemaining(java.math.BigDecimal) 

    /**
     * Method setFreeLookDaysOverrideSets the value of field
     * 'freeLookDaysOverride'.
     * 
     * @param freeLookDaysOverride the value of field
     * 'freeLookDaysOverride'.
     */
    public void setFreeLookDaysOverride(int freeLookDaysOverride)
    {
        this._freeLookDaysOverride = freeLookDaysOverride;
        
        super.setVoChanged(true);
        this._has_freeLookDaysOverride = true;
    } //-- void setFreeLookDaysOverride(int) 

    /**
     * Method setFreeLookEndDateSets the value of field
     * 'freeLookEndDate'.
     * 
     * @param freeLookEndDate the value of field 'freeLookEndDate'.
     */
    public void setFreeLookEndDate(java.lang.String freeLookEndDate)
    {
        this._freeLookEndDate = freeLookEndDate;
        
        super.setVoChanged(true);
    } //-- void setFreeLookEndDate(java.lang.String) 

    /**
     * Method setGIOOptionSets the value of field 'GIOOption'.
     * 
     * @param GIOOption the value of field 'GIOOption'.
     */
    public void setGIOOption(java.lang.String GIOOption)
    {
        this._GIOOption = GIOOption;
        
        super.setVoChanged(true);
    } //-- void setGIOOption(java.lang.String) 

    /**
     * Method setGroupPlanSets the value of field 'groupPlan'.
     * 
     * @param groupPlan the value of field 'groupPlan'.
     */
    public void setGroupPlan(java.lang.String groupPlan)
    {
        this._groupPlan = groupPlan;
        
        super.setVoChanged(true);
    } //-- void setGroupPlan(java.lang.String) 

    /**
     * Method setInherentRiderVO
     * 
     * @param index
     * @param vInherentRiderVO
     */
    public void setInherentRiderVO(int index, edit.common.vo.InherentRiderVO vInherentRiderVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _inherentRiderVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInherentRiderVO.setParentVO(this.getClass(), this);
        _inherentRiderVOList.setElementAt(vInherentRiderVO, index);
    } //-- void setInherentRiderVO(int, edit.common.vo.InherentRiderVO) 

    /**
     * Method setInherentRiderVO
     * 
     * @param inherentRiderVOArray
     */
    public void setInherentRiderVO(edit.common.vo.InherentRiderVO[] inherentRiderVOArray)
    {
        //-- copy array
        _inherentRiderVOList.removeAllElements();
        for (int i = 0; i < inherentRiderVOArray.length; i++) {
            inherentRiderVOArray[i].setParentVO(this.getClass(), this);
            _inherentRiderVOList.addElement(inherentRiderVOArray[i]);
        }
    } //-- void setInherentRiderVO(edit.common.vo.InherentRiderVO) 

    /**
     * Method setInvestmentVO
     * 
     * @param index
     * @param vInvestmentVO
     */
    public void setInvestmentVO(int index, edit.common.vo.InvestmentVO vInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentVO.setParentVO(this.getClass(), this);
        _investmentVOList.setElementAt(vInvestmentVO, index);
    } //-- void setInvestmentVO(int, edit.common.vo.InvestmentVO) 

    /**
     * Method setInvestmentVO
     * 
     * @param investmentVOArray
     */
    public void setInvestmentVO(edit.common.vo.InvestmentVO[] investmentVOArray)
    {
        //-- copy array
        _investmentVOList.removeAllElements();
        for (int i = 0; i < investmentVOArray.length; i++) {
            investmentVOArray[i].setParentVO(this.getClass(), this);
            _investmentVOList.addElement(investmentVOArray[i]);
        }
    } //-- void setInvestmentVO(edit.common.vo.InvestmentVO) 

    /**
     * Method setIssueDateSets the value of field 'issueDate'.
     * 
     * @param issueDate the value of field 'issueDate'.
     */
    public void setIssueDate(java.lang.String issueDate)
    {
        this._issueDate = issueDate;
        
        super.setVoChanged(true);
    } //-- void setIssueDate(java.lang.String) 

    /**
     * Method setIssueStateCTSets the value of field
     * 'issueStateCT'.
     * 
     * @param issueStateCT the value of field 'issueStateCT'.
     */
    public void setIssueStateCT(java.lang.String issueStateCT)
    {
        this._issueStateCT = issueStateCT;
        
        super.setVoChanged(true);
    } //-- void setIssueStateCT(java.lang.String) 

    /**
     * Method setIssueStateORIndSets the value of field
     * 'issueStateORInd'.
     * 
     * @param issueStateORInd the value of field 'issueStateORInd'.
     */
    public void setIssueStateORInd(java.lang.String issueStateORInd)
    {
        this._issueStateORInd = issueStateORInd;
        
        super.setVoChanged(true);
    } //-- void setIssueStateORInd(java.lang.String) 

    /**
     * Method setOriginalStateCT Sets the value of field
     * 'issueStateCT'.
     * 
     * @param originalStateCT the value of field 'originalStateCT'.
     */
    public void setOriginalStateCT(java.lang.String originalStateCT)
    {
        this._originalStateCT = originalStateCT;
        
        super.setVoChanged(true);
    } //-- void setIssueStateCT(java.lang.String) 

    /**
     * Method setLastAnniversaryDateSets the value of field
     * 'lastAnniversaryDate'.
     * 
     * @param lastAnniversaryDate the value of field
     * 'lastAnniversaryDate'.
     */
    public void setLastAnniversaryDate(java.lang.String lastAnniversaryDate)
    {
        this._lastAnniversaryDate = lastAnniversaryDate;
        
        super.setVoChanged(true);
    } //-- void setLastAnniversaryDate(java.lang.String) 

    /**
     * Method setLastSettlementValDateSets the value of field
     * 'lastSettlementValDate'.
     * 
     * @param lastSettlementValDate the value of field
     * 'lastSettlementValDate'.
     */
    public void setLastSettlementValDate(java.lang.String lastSettlementValDate)
    {
        this._lastSettlementValDate = lastSettlementValDate;
        
        super.setVoChanged(true);
    } //-- void setLastSettlementValDate(java.lang.String) 

    /**
     * Method setLifeVO
     * 
     * @param index
     * @param vLifeVO
     */
    public void setLifeVO(int index, edit.common.vo.LifeVO vLifeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _lifeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vLifeVO.setParentVO(this.getClass(), this);
        _lifeVOList.setElementAt(vLifeVO, index);
    } //-- void setLifeVO(int, edit.common.vo.LifeVO) 

    /**
     * Method setLifeVO
     * 
     * @param lifeVOArray
     */
    public void setLifeVO(edit.common.vo.LifeVO[] lifeVOArray)
    {
        //-- copy array
        _lifeVOList.removeAllElements();
        for (int i = 0; i < lifeVOArray.length; i++) {
            lifeVOArray[i].setParentVO(this.getClass(), this);
            _lifeVOList.addElement(lifeVOArray[i]);
        }
    } //-- void setLifeVO(edit.common.vo.LifeVO) 

    /**
     * Method setLoadsSets the value of field 'loads'.
     * 
     * @param loads the value of field 'loads'.
     */
    public void setLoads(java.math.BigDecimal loads)
    {
        this._loads = loads;
        
        super.setVoChanged(true);
    } //-- void setLoads(java.math.BigDecimal) 

    /**
     * Method setMasterContractFKSets the value of field
     * 'masterContractFK'.
     * 
     * @param masterContractFK the value of field 'masterContractFK'
     */
    public void setMasterContractFK(long masterContractFK)
    {
        this._masterContractFK = masterContractFK;
        
        super.setVoChanged(true);
        this._has_masterContractFK = true;
    } //-- void setMasterContractFK(long) 

    /**
     * Method setMemberOfContractGroupSets the value of field
     * 'memberOfContractGroup'.
     * 
     * @param memberOfContractGroup the value of field
     * 'memberOfContractGroup'.
     */
    public void setMemberOfContractGroup(java.lang.String memberOfContractGroup)
    {
        this._memberOfContractGroup = memberOfContractGroup;
        
        super.setVoChanged(true);
    } //-- void setMemberOfContractGroup(java.lang.String) 

    /**
     * Method setNoteReminderVO
     * 
     * @param index
     * @param vNoteReminderVO
     */
    public void setNoteReminderVO(int index, edit.common.vo.NoteReminderVO vNoteReminderVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _noteReminderVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vNoteReminderVO.setParentVO(this.getClass(), this);
        _noteReminderVOList.setElementAt(vNoteReminderVO, index);
    } //-- void setNoteReminderVO(int, edit.common.vo.NoteReminderVO) 

    /**
     * Method setNoteReminderVO
     * 
     * @param noteReminderVOArray
     */
    public void setNoteReminderVO(edit.common.vo.NoteReminderVO[] noteReminderVOArray)
    {
        //-- copy array
        _noteReminderVOList.removeAllElements();
        for (int i = 0; i < noteReminderVOArray.length; i++) {
            noteReminderVOArray[i].setParentVO(this.getClass(), this);
            _noteReminderVOList.addElement(noteReminderVOArray[i]);
        }
    } //-- void setNoteReminderVO(edit.common.vo.NoteReminderVO) 

    /**
     * Method setOpenClaimEndDateSets the value of field
     * 'openClaimEndDate'.
     * 
     * @param openClaimEndDate the value of field 'openClaimEndDate'
     */
    public void setOpenClaimEndDate(java.lang.String openClaimEndDate)
    {
        this._openClaimEndDate = openClaimEndDate;
        
        super.setVoChanged(true);
    } //-- void setOpenClaimEndDate(java.lang.String) 

    /**
     * Method setOptionCodeCTSets the value of field
     * 'optionCodeCT'.
     * 
     * @param optionCodeCT the value of field 'optionCodeCT'.
     */
    public void setOptionCodeCT(java.lang.String optionCodeCT)
    {
        this._optionCodeCT = optionCodeCT;
        
        super.setVoChanged(true);
    } //-- void setOptionCodeCT(java.lang.String) 

    /**
     * Method setOriginalContractGroupFKSets the value of field
     * 'originalContractGroupFK'.
     * 
     * @param originalContractGroupFK the value of field
     * 'originalContractGroupFK'.
     */
    public void setOriginalContractGroupFK(long originalContractGroupFK)
    {
        this._originalContractGroupFK = originalContractGroupFK;
        
        super.setVoChanged(true);
        this._has_originalContractGroupFK = true;
    } //-- void setOriginalContractGroupFK(long) 

    /**
     * Method setOriginalUnitsSets the value of field
     * 'originalUnits'.
     * 
     * @param originalUnits the value of field 'originalUnits'.
     */
    public void setOriginalUnits(java.math.BigDecimal originalUnits)
    {
        this._originalUnits = originalUnits;
        
        super.setVoChanged(true);
    } //-- void setOriginalUnits(java.math.BigDecimal) 

    /**
     * Method setPayoutVO
     * 
     * @param index
     * @param vPayoutVO
     */
    public void setPayoutVO(int index, edit.common.vo.PayoutVO vPayoutVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _payoutVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPayoutVO.setParentVO(this.getClass(), this);
        _payoutVOList.setElementAt(vPayoutVO, index);
    } //-- void setPayoutVO(int, edit.common.vo.PayoutVO) 

    /**
     * Method setPayoutVO
     * 
     * @param payoutVOArray
     */
    public void setPayoutVO(edit.common.vo.PayoutVO[] payoutVOArray)
    {
        //-- copy array
        _payoutVOList.removeAllElements();
        for (int i = 0; i < payoutVOArray.length; i++) {
            payoutVOArray[i].setParentVO(this.getClass(), this);
            _payoutVOList.addElement(payoutVOArray[i]);
        }
    } //-- void setPayoutVO(edit.common.vo.PayoutVO) 

    /**
     * Method setPointInScaleIndicatorSets the value of field
     * 'pointInScaleIndicator'.
     * 
     * @param pointInScaleIndicator the value of field
     * 'pointInScaleIndicator'.
     */
    public void setPointInScaleIndicator(java.lang.String pointInScaleIndicator)
    {
        this._pointInScaleIndicator = pointInScaleIndicator;
        
        super.setVoChanged(true);
    } //-- void setPointInScaleIndicator(java.lang.String) 

    /**
     * Method setPolicyDeliveryDateSets the value of field
     * 'policyDeliveryDate'.
     * 
     * @param policyDeliveryDate the value of field
     * 'policyDeliveryDate'.
     */
    public void setPolicyDeliveryDate(java.lang.String policyDeliveryDate)
    {
        this._policyDeliveryDate = policyDeliveryDate;
        
        super.setVoChanged(true);
    } //-- void setPolicyDeliveryDate(java.lang.String) 

    /**
     * Method setPostIssueStatusCTSets the value of field
     * 'postIssueStatusCT'.
     * 
     * @param postIssueStatusCT the value of field
     * 'postIssueStatusCT'.
     */
    public void setPostIssueStatusCT(java.lang.String postIssueStatusCT)
    {
        this._postIssueStatusCT = postIssueStatusCT;
        
        super.setVoChanged(true);
    } //-- void setPostIssueStatusCT(java.lang.String) 

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
     * Method setPremiumTaxSitusOverrideCTSets the value of field
     * 'premiumTaxSitusOverrideCT'.
     * 
     * @param premiumTaxSitusOverrideCT the value of field
     * 'premiumTaxSitusOverrideCT'.
     */
    public void setPremiumTaxSitusOverrideCT(java.lang.String premiumTaxSitusOverrideCT)
    {
        this._premiumTaxSitusOverrideCT = premiumTaxSitusOverrideCT;
        
        super.setVoChanged(true);
    } //-- void setPremiumTaxSitusOverrideCT(java.lang.String) 

    /**
     * Method setPreviousNotifyDateSets the value of field
     * 'previousNotifyDate'.
     * 
     * @param previousNotifyDate the value of field
     * 'previousNotifyDate'.
     */
    public void setPreviousNotifyDate(java.lang.String previousNotifyDate)
    {
        this._previousNotifyDate = previousNotifyDate;
        
        super.setVoChanged(true);
    } //-- void setPreviousNotifyDate(java.lang.String) 

    /**
     * Method setPrintLine1Sets the value of field 'printLine1'.
     * 
     * @param printLine1 the value of field 'printLine1'.
     */
    public void setPrintLine1(java.lang.String printLine1)
    {
        this._printLine1 = printLine1;
        
        super.setVoChanged(true);
    } //-- void setPrintLine1(java.lang.String) 

    /**
     * Method setPrintLine2Sets the value of field 'printLine2'.
     * 
     * @param printLine2 the value of field 'printLine2'.
     */
    public void setPrintLine2(java.lang.String printLine2)
    {
        this._printLine2 = printLine2;
        
        super.setVoChanged(true);
    } //-- void setPrintLine2(java.lang.String) 

    /**
     * Method setPriorContractGroupFKSets the value of field
     * 'priorContractGroupFK'.
     * 
     * @param priorContractGroupFK the value of field
     * 'priorContractGroupFK'.
     */
    public void setPriorContractGroupFK(long priorContractGroupFK)
    {
        this._priorContractGroupFK = priorContractGroupFK;
        
        super.setVoChanged(true);
        this._has_priorContractGroupFK = true;
    } //-- void setPriorContractGroupFK(long) 

    /**
     * Method setPriorPRDDueSets the value of field 'priorPRDDue'.
     * 
     * @param priorPRDDue the value of field 'priorPRDDue'.
     */
    public void setPriorPRDDue(java.lang.String priorPRDDue)
    {
        this._priorPRDDue = priorPRDDue;
        
        super.setVoChanged(true);
    } //-- void setPriorPRDDue(java.lang.String) 

    /**
     * Method setPriorROTHCostBasisSets the value of field
     * 'priorROTHCostBasis'.
     * 
     * @param priorROTHCostBasis the value of field
     * 'priorROTHCostBasis'.
     */
    public void setPriorROTHCostBasis(java.math.BigDecimal priorROTHCostBasis)
    {
        this._priorROTHCostBasis = priorROTHCostBasis;
        
        super.setVoChanged(true);
    } //-- void setPriorROTHCostBasis(java.math.BigDecimal) 

    /**
     * Method setProductStructureFKSets the value of field
     * 'productStructureFK'.
     * 
     * @param productStructureFK the value of field
     * 'productStructureFK'.
     */
    public void setProductStructureFK(long productStructureFK)
    {
        this._productStructureFK = productStructureFK;
        
        super.setVoChanged(true);
        this._has_productStructureFK = true;
    } //-- void setProductStructureFK(long) 

    /**
     * Method setQualNonQualCTSets the value of field
     * 'qualNonQualCT'.
     * 
     * @param qualNonQualCT the value of field 'qualNonQualCT'.
     */
    public void setQualNonQualCT(java.lang.String qualNonQualCT)
    {
        this._qualNonQualCT = qualNonQualCT;
        
        super.setVoChanged(true);
    } //-- void setQualNonQualCT(java.lang.String) 

    /**
     * Method setQualifiedTypeCTSets the value of field
     * 'qualifiedTypeCT'.
     * 
     * @param qualifiedTypeCT the value of field 'qualifiedTypeCT'.
     */
    public void setQualifiedTypeCT(java.lang.String qualifiedTypeCT)
    {
        this._qualifiedTypeCT = qualifiedTypeCT;
        
        super.setVoChanged(true);
    } //-- void setQualifiedTypeCT(java.lang.String) 

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
     * Method setROTHConvIndSets the value of field 'ROTHConvInd'.
     * 
     * @param ROTHConvInd the value of field 'ROTHConvInd'.
     */
    public void setROTHConvInd(java.lang.String ROTHConvInd)
    {
        this._ROTHConvInd = ROTHConvInd;
        
        super.setVoChanged(true);
    } //-- void setROTHConvInd(java.lang.String) 

    /**
     * Method setRatedGenderCTSets the value of field
     * 'ratedGenderCT'.
     * 
     * @param ratedGenderCT the value of field 'ratedGenderCT'.
     */
    public void setRatedGenderCT(java.lang.String ratedGenderCT)
    {
        this._ratedGenderCT = ratedGenderCT;
        
        super.setVoChanged(true);
    } //-- void setRatedGenderCT(java.lang.String) 

    /**
     * Method setRealTimeActivityVO
     * 
     * @param index
     * @param vRealTimeActivityVO
     */
    public void setRealTimeActivityVO(int index, edit.common.vo.RealTimeActivityVO vRealTimeActivityVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _realTimeActivityVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRealTimeActivityVO.setParentVO(this.getClass(), this);
        _realTimeActivityVOList.setElementAt(vRealTimeActivityVO, index);
    } //-- void setRealTimeActivityVO(int, edit.common.vo.RealTimeActivityVO) 

    /**
     * Method setRealTimeActivityVO
     * 
     * @param realTimeActivityVOArray
     */
    public void setRealTimeActivityVO(edit.common.vo.RealTimeActivityVO[] realTimeActivityVOArray)
    {
        //-- copy array
        _realTimeActivityVOList.removeAllElements();
        for (int i = 0; i < realTimeActivityVOArray.length; i++) {
            realTimeActivityVOArray[i].setParentVO(this.getClass(), this);
            _realTimeActivityVOList.addElement(realTimeActivityVOArray[i]);
        }
    } //-- void setRealTimeActivityVO(edit.common.vo.RealTimeActivityVO) 

    /**
     * Method setRecoveredCostBasisSets the value of field
     * 'recoveredCostBasis'.
     * 
     * @param recoveredCostBasis the value of field
     * 'recoveredCostBasis'.
     */
    public void setRecoveredCostBasis(java.math.BigDecimal recoveredCostBasis)
    {
        this._recoveredCostBasis = recoveredCostBasis;
        
        super.setVoChanged(true);
    } //-- void setRecoveredCostBasis(java.math.BigDecimal) 

    /**
     * Method setReferenceIDSets the value of field 'referenceID'.
     * 
     * @param referenceID the value of field 'referenceID'.
     */
    public void setReferenceID(java.lang.String referenceID)
    {
        this._referenceID = referenceID;
        
        super.setVoChanged(true);
    } //-- void setReferenceID(java.lang.String) 

    /**
     * Method setRemainingBeneficiariesSets the value of field
     * 'remainingBeneficiaries'.
     * 
     * @param remainingBeneficiaries the value of field
     * 'remainingBeneficiaries'.
     */
    public void setRemainingBeneficiaries(int remainingBeneficiaries)
    {
        this._remainingBeneficiaries = remainingBeneficiaries;
        
        super.setVoChanged(true);
        this._has_remainingBeneficiaries = true;
    } //-- void setRemainingBeneficiaries(int) 

    /**
     * Method setRequiredMinDistributionVO
     * 
     * @param index
     * @param vRequiredMinDistributionVO
     */
    public void setRequiredMinDistributionVO(int index, edit.common.vo.RequiredMinDistributionVO vRequiredMinDistributionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _requiredMinDistributionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRequiredMinDistributionVO.setParentVO(this.getClass(), this);
        _requiredMinDistributionVOList.setElementAt(vRequiredMinDistributionVO, index);
    } //-- void setRequiredMinDistributionVO(int, edit.common.vo.RequiredMinDistributionVO) 

    /**
     * Method setRequiredMinDistributionVO
     * 
     * @param requiredMinDistributionVOArray
     */
    public void setRequiredMinDistributionVO(edit.common.vo.RequiredMinDistributionVO[] requiredMinDistributionVOArray)
    {
        //-- copy array
        _requiredMinDistributionVOList.removeAllElements();
        for (int i = 0; i < requiredMinDistributionVOArray.length; i++) {
            requiredMinDistributionVOArray[i].setParentVO(this.getClass(), this);
            _requiredMinDistributionVOList.addElement(requiredMinDistributionVOArray[i]);
        }
    } //-- void setRequiredMinDistributionVO(edit.common.vo.RequiredMinDistributionVO) 

    /**
     * Method setRequirementEffectiveDateSets the value of field
     * 'requirementEffectiveDate'.
     * 
     * @param requirementEffectiveDate the value of field
     * 'requirementEffectiveDate'.
     */
    public void setRequirementEffectiveDate(java.lang.String requirementEffectiveDate)
    {
        this._requirementEffectiveDate = requirementEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setRequirementEffectiveDate(java.lang.String) 

    /**
     * Method setRiderNumberSets the value of field 'riderNumber'.
     * 
     * @param riderNumber the value of field 'riderNumber'.
     */
    public void setRiderNumber(int riderNumber)
    {
        this._riderNumber = riderNumber;
        
        super.setVoChanged(true);
        this._has_riderNumber = true;
    } //-- void setRiderNumber(int) 

    /**
     * Method setSavingsPercentSets the value of field
     * 'savingsPercent'.
     * 
     * @param savingsPercent the value of field 'savingsPercent'.
     */
    public void setSavingsPercent(java.math.BigDecimal savingsPercent)
    {
        this._savingsPercent = savingsPercent;
        
        super.setVoChanged(true);
    } //-- void setSavingsPercent(java.math.BigDecimal) 

    /**
     * Method setScheduledTerminationDateSets the value of field
     * 'scheduledTerminationDate'.
     * 
     * @param scheduledTerminationDate the value of field
     * 'scheduledTerminationDate'.
     */
    public void setScheduledTerminationDate(java.lang.String scheduledTerminationDate)
    {
        this._scheduledTerminationDate = scheduledTerminationDate;
        
        super.setVoChanged(true);
    } //-- void setScheduledTerminationDate(java.lang.String) 

    /**
     * Method setSegmentBackupVO
     * 
     * @param index
     * @param vSegmentBackupVO
     */
    public void setSegmentBackupVO(int index, edit.common.vo.SegmentBackupVO vSegmentBackupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentBackupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSegmentBackupVO.setParentVO(this.getClass(), this);
        _segmentBackupVOList.setElementAt(vSegmentBackupVO, index);
    } //-- void setSegmentBackupVO(int, edit.common.vo.SegmentBackupVO) 

    /**
     * Method setSegmentBackupVO
     * 
     * @param segmentBackupVOArray
     */
    public void setSegmentBackupVO(edit.common.vo.SegmentBackupVO[] segmentBackupVOArray)
    {
        //-- copy array
        _segmentBackupVOList.removeAllElements();
        for (int i = 0; i < segmentBackupVOArray.length; i++) {
            segmentBackupVOArray[i].setParentVO(this.getClass(), this);
            _segmentBackupVOList.addElement(segmentBackupVOArray[i]);
        }
    } //-- void setSegmentBackupVO(edit.common.vo.SegmentBackupVO) 

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
     * Method setSegmentNameCTSets the value of field
     * 'segmentNameCT'.
     * 
     * @param segmentNameCT the value of field 'segmentNameCT'.
     */
    public void setSegmentNameCT(java.lang.String segmentNameCT)
    {
        this._segmentNameCT = segmentNameCT;
        
        super.setVoChanged(true);
    } //-- void setSegmentNameCT(java.lang.String) 

    /**
     * Method setSegmentPKSets the value of field 'segmentPK'.
     * 
     * @param segmentPK the value of field 'segmentPK'.
     */
    public void setSegmentPK(long segmentPK)
    {
        this._segmentPK = segmentPK;
        
        super.setVoChanged(true);
        this._has_segmentPK = true;
    } //-- void setSegmentPK(long) 

    /**
     * Method setSegmentStatusCTSets the value of field
     * 'segmentStatusCT'.
     * 
     * @param segmentStatusCT the value of field 'segmentStatusCT'.
     */
    public void setSegmentStatusCT(java.lang.String segmentStatusCT)
    {
        this._segmentStatusCT = segmentStatusCT;
        
        super.setVoChanged(true);
    } //-- void setSegmentStatusCT(java.lang.String) 

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
     * Method setSettlementAmountSets the value of field
     * 'settlementAmount'.
     * 
     * @param settlementAmount the value of field 'settlementAmount'
     */
    public void setSettlementAmount(java.math.BigDecimal settlementAmount)
    {
        this._settlementAmount = settlementAmount;
        
        super.setVoChanged(true);
    } //-- void setSettlementAmount(java.math.BigDecimal) 

    /**
     * Method setStatusChangeDateSets the value of field
     * 'statusChangeDate'.
     * 
     * @param statusChangeDate the value of field 'statusChangeDate'
     */
    public void setStatusChangeDate(java.lang.String statusChangeDate)
    {
        this._statusChangeDate = statusChangeDate;
        
        super.setVoChanged(true);
    } //-- void setStatusChangeDate(java.lang.String) 

    /**
     * Method setSuppOriginalContractNumberSets the value of field
     * 'suppOriginalContractNumber'.
     * 
     * @param suppOriginalContractNumber the value of field
     * 'suppOriginalContractNumber'.
     */
    public void setSuppOriginalContractNumber(java.lang.String suppOriginalContractNumber)
    {
        this._suppOriginalContractNumber = suppOriginalContractNumber;
        
        super.setVoChanged(true);
    } //-- void setSuppOriginalContractNumber(java.lang.String) 

    /**
     * Method setTaxReportingGroupSets the value of field
     * 'taxReportingGroup'.
     * 
     * @param taxReportingGroup the value of field
     * 'taxReportingGroup'.
     */
    public void setTaxReportingGroup(java.lang.String taxReportingGroup)
    {
        this._taxReportingGroup = taxReportingGroup;
        
        super.setVoChanged(true);
    } //-- void setTaxReportingGroup(java.lang.String) 

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
     * Method setExpiryDate sets the value of field
     * 'expiryDate'.
     * 
     * @param expiryDate the value of field 'expiryDate'.
     */
    public void setExpiryDate(java.lang.String expiryDate)
    {
        this._expiryDate = expiryDate;
        
        super.setVoChanged(true);
    } 
    
    /**
     * Method setTotalActiveBeneficiariesSets the value of field
     * 'totalActiveBeneficiaries'.
     * 
     * @param totalActiveBeneficiaries the value of field
     * 'totalActiveBeneficiaries'.
     */
    public void setTotalActiveBeneficiaries(int totalActiveBeneficiaries)
    {
        this._totalActiveBeneficiaries = totalActiveBeneficiaries;
        
        super.setVoChanged(true);
        this._has_totalActiveBeneficiaries = true;
    } //-- void setTotalActiveBeneficiaries(int) 

    /**
     * Method setTotalFaceAmountSets the value of field
     * 'totalFaceAmount'.
     * 
     * @param totalFaceAmount the value of field 'totalFaceAmount'.
     */
    public void setTotalFaceAmount(java.math.BigDecimal totalFaceAmount)
    {
        this._totalFaceAmount = totalFaceAmount;
        
        super.setVoChanged(true);
    } //-- void setTotalFaceAmount(java.math.BigDecimal) 

    /**
     * Method setUnderwritingClassCTSets the value of field
     * 'underwritingClassCT'.
     * 
     * @param underwritingClassCT the value of field
     * 'underwritingClassCT'.
     */
    public void setUnderwritingClassCT(java.lang.String underwritingClassCT)
    {
        this._underwritingClassCT = underwritingClassCT;
        
        super.setVoChanged(true);
    } //-- void setUnderwritingClassCT(java.lang.String) 

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
     * Method setValueAtIssueVO
     * 
     * @param index
     * @param vValueAtIssueVO
     */
    public void setValueAtIssueVO(int index, edit.common.vo.ValueAtIssueVO vValueAtIssueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _valueAtIssueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vValueAtIssueVO.setParentVO(this.getClass(), this);
        _valueAtIssueVOList.setElementAt(vValueAtIssueVO, index);
    } //-- void setValueAtIssueVO(int, edit.common.vo.ValueAtIssueVO) 

    /**
     * Method setValueAtIssueVO
     * 
     * @param valueAtIssueVOArray
     */
    public void setValueAtIssueVO(edit.common.vo.ValueAtIssueVO[] valueAtIssueVOArray)
    {
        //-- copy array
        _valueAtIssueVOList.removeAllElements();
        for (int i = 0; i < valueAtIssueVOArray.length; i++) {
            valueAtIssueVOArray[i].setParentVO(this.getClass(), this);
            _valueAtIssueVOList.addElement(valueAtIssueVOArray[i]);
        }
    } //-- void setValueAtIssueVO(edit.common.vo.ValueAtIssueVO) 

    /**
     * Method setWaiveFreeLookIndicatorSets the value of field
     * 'waiveFreeLookIndicator'.
     * 
     * @param waiveFreeLookIndicator the value of field
     * 'waiveFreeLookIndicator'.
     */
    public void setWaiveFreeLookIndicator(java.lang.String waiveFreeLookIndicator)
    {
        this._waiveFreeLookIndicator = waiveFreeLookIndicator;
        
        super.setVoChanged(true);
    } //-- void setWaiveFreeLookIndicator(java.lang.String) 

    /**
     * Method setWaiverInEffectSets the value of field
     * 'waiverInEffect'.
     * 
     * @param waiverInEffect the value of field 'waiverInEffect'.
     */
    public void setWaiverInEffect(java.lang.String waiverInEffect)
    {
        this._waiverInEffect = waiverInEffect;
        
        super.setVoChanged(true);
    } //-- void setWaiverInEffect(java.lang.String) 

    /**
     * Method setWorksheetTypeCTSets the value of field
     * 'worksheetTypeCT'.
     * 
     * @param worksheetTypeCT the value of field 'worksheetTypeCT'.
     */
    public void setWorksheetTypeCT(java.lang.String worksheetTypeCT)
    {
        this._worksheetTypeCT = worksheetTypeCT;
        
        super.setVoChanged(true);
    } //-- void setWorksheetTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SegmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SegmentVO) Unmarshaller.unmarshal(edit.common.vo.SegmentVO.class, reader);
    } //-- edit.common.vo.SegmentVO unmarshal(java.io.Reader) 

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
