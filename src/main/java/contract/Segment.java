/*
 * User: sdorman
 * Date: Jul 15, 2004
 * Time: 8:41:21 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import agent.Agent;
import agent.PlacedAgent;

import billing.Bill;
import billing.BillSchedule;

import casetracking.CasetrackingLog;

import client.ClientDetail;

import contract.dm.composer.VOComposer;
import contract.dm.dao.DAOFactory;
import contract.dm.dao.FilteredRequirementDAO;
import contract.dm.dao.RequirementDAO;
import contract.dm.dao.SegmentDAO;

import edit.common.Change;
import edit.common.ChangeProcessor;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.exceptions.EDITAgentException;
import edit.common.exceptions.EDITCaseException;
import edit.common.exceptions.EDITContractException;
import edit.common.exceptions.EDITEventException;
import edit.common.exceptions.EDITValidationException;
import edit.common.vo.*;

import edit.services.db.CRUD;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityI;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.Area;
import engine.AreaKey;
import engine.AreaValue;
import engine.Company;
import engine.FilteredFund;
import engine.ProductStructure;
import engine.sp.Getareatable;
import engine.sp.SPException;

import event.BonusCommissionHistory;
import event.CashBatchContract;
import event.ClientSetup;
import event.CommissionHistory;
import event.ContractSetup;
import event.EDITTrx;
import event.GroupSetup;
import event.ScheduledEvent;
import event.SegmentHistory;
import event.Suspense;

import event.common.Constants;

import event.dm.dao.CommissionHistoryDAO;
import event.dm.dao.TransactionAccumsFastDAO;

import event.transaction.TransactionProcessor;

import fission.utility.Util;

import group.*;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Element;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import reinsurance.ContractTreaty;

import role.ClientRole;

import staging.IStaging;
import staging.SegmentBase;
import staging.SegmentRider;
import staging.StagingContext;

public class Segment extends HibernateEntity implements CRUDEntity, IStaging {
    public static final String SEGMENTSTATUSCT_ACTIVE = "Active";
    public static final String SEGMENTSTATUSCT_SURRENDERED = "Surrendered";
    public static final String SEGMENTSTATUSCT_INACTIVE = "Inactive";
    public static final String SEGMENTSTATUSCT_RESCINDED = "Rescinded";
    public static final String SEGMENTSTATUSCT_LAPSE = "Lapse";
    public static final String SEGMENTSTATUSCT_OVERDUE = "Overdue";
    public static final String SEGMENTSTATUSCT_DEAD = "Dead";
    public static final String SEGMENTSTATUSCT_DEATH = "Death";
    public static final String SEGMENTSTATUSCT_DEATHPENDING = "DeathPending";
    public static final String SEGMENTSTATUSCT_DEATHHEDGEFUNDPEND = "DeathHedgeFundPend";
    public static final String SEGMENTSTATUSCT_FROZEN = "Frozen";
    public static final String SEGMENTSTATUSCT_MATURE = "Mature";
    public static final String SEGMENTSTATUSCT_LAPSEPENDING = "LapsePending";
    public static final String SEGMENTSTATUSCT_OVERLOAN = "Overloan";
    public static final String SEGMENTSTATUSCT_PENDING = "Pending";
    public static final String SEGMENTSTATUSCT_SUBMITTED = "Submitted";
    public static final String SEGMENTSTATUSCT_REOPEN = "Reopen";
    public static final String SEGMENTSTATUSCT_WITHDRAWN = "Withdrawn";
    public static final String SEGMENTSTATUSCT_POSTPONED = "Postponed";
    public static final String SEGMENTSTATUSCT_INCOMPLETE = "Incomplete";
    public static final String SEGMENTSTATUSCT_SUBMIT_PEND = "SubmitPend";
    public static final String SEGMENTSTATUSCT_INITIAL_UW = "InitialUW";
    public static final String SEGMENTSTATUSCT_APPROVED = "Approved";
    public static final String SEGMENTSTATUSCT_ISSUEPENDINGPREMIUM = "IssuePendingPremium";
    public static final String SEGMENTSTATUSCT_ISSUEPENDINGREQ = "IssuedPendingReq";
    public static final String SEGMENTSTATUSCT_DECLINED = "Declined";
    public static final String SEGMENTSTATUSCT_DECLINEDMED = "DeclinedMed";
    public static final String SEGMENTSTATUSCT_DECLINEELIG = "DeclineElig";
    public static final String SEGMENTSTATUSCT_DECLINEREQ = "DeclineReq";
    public static final String SEGMENTSTATUSCT_REDUCED_PAIDUP = "ReducedPaidUp";
    public static final String SEGMENTSTATUSCT_NOT_TAKEN = "NotTaken";
    public static final String SEGMENTSTATUSCT_LTC = "LTC";
    public static final String SEGMENTSTATUSCT_CONVERSION_PENDING = "ConversionPending";
    public static final String SEGMENTSTATUSCT_TRANSITION = "Transition";
    public static final String SEGMENTSTATUSCT_TERMINATED = "Terminated";
    public static final String SEGMENTSTATUSCT_PUTERM = "PUTerm";
    public static final String SEGMENTSTATUSCT_EXTENSION = "Extension";
    public static final String SEGMENTSTATUSCT_REDUCED_BENEFIT = "ReducedBenefit";

    public static final String OPTIONCODECT_DEFERRED_ANNUITY = "DFA";
    public static final String OPTIONCODECT_VARIABLE_LIFE = "VL";
    public static final String OPTIONCODECT_TRADITIONAL_LIFE = "Traditional";
    public static final String OPTIONCODECT_UNIVERSAL_LIFE = "UL";
    public static final List<String> OPTIONCODES_AH = new ArrayList<>(Arrays.asList("GCI", "GHI", "GACC"));
    
    public static final String OPTIONCODECT_RIDER_ACCIDENTAL_DEATH_BENEFIT = "ADB";
    public static final String OPTIONCODECT_RIDER_LTCTI = "LTCTI";
    public static final String OPTIONCODECT_RIDER_LTC = "LTC";
    public static final String OPTIONCODECT_RIDER_TI = "TI";
    public static final String OPTIONCODECT_RIDER_GUARANTEED_INSURANCE_OPTION = "GIO";
    public static final String OPTIONCODECT_RIDER_LEVEL_TERM_GUARANTEED_INSURANCE_OPTION = "LTGIO";
    public static final String OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT = "LT";
	public static final String OPTIONCODECT_RIDER_CTR = "CTR";
    public static final String OPTIONCODECT_RIDER_CTR5 = "CTR5";
    public static final String OPTIONCODECT_RIDER_OTHER_INSURED_TERM = "OIR";

    public static final String SEGMENTNAMECT_LIFE = "Life";
    public static final String SEGMENTNAMECT_DEFERREDANNUITY = "DFA";
    public static final String SEGMENTNAMECT_PAYOUT = "Payout";
    public static final String SEGMENTNAMECT_TRADITIONAL = "Traditional";
	public static final String SEGMENTNAMECT_UL = "UL";
	public static final String SEGMENTNAMECT_AH = "A&H";

    public static final String QUALIFIEDTYPECT_IRA = "IRA";
    public static final String QUALIFIEDTYPECT_SEP = "SEP";
    public static final String QUALIFIEDTYPECT_SIMPLE = "SIMPLE";
    public static final String QUALIFIEDTYPECT_ROTH = "ROTH";

    public static final String CASETRACKINGOPTION_SPOUSALCONTINUATION = "SpousalContinuation";

    public static final String CONTRACTTYPECT_JOINT = "Joint";
    public static final String CONTRACTTYPECT_INDIVIDUAL = "Individual";

    public static final String WORKSHEETTYPECT_SUBMITTED = "Submitted";
    public static final String WORKSHEETTYPECT_CORRECTION = "Correction";
    public static final String WORKSHEETTYPECT_FINAL = "Final";

	public static final String[] WORKSHEETTYPES = new String[] { Segment.WORKSHEETTYPECT_CORRECTION,
			Segment.WORKSHEETTYPECT_FINAL, Segment.WORKSHEETTYPECT_SUBMITTED };

	public static final String[] NO_REQ_REPTG_STATUSES = new String[] { Segment.SEGMENTSTATUSCT_WITHDRAWN,
			Segment.SEGMENTSTATUSCT_POSTPONED, Segment.SEGMENTSTATUSCT_INCOMPLETE, Segment.SEGMENTSTATUSCT_DECLINEDMED,
			Segment.SEGMENTSTATUSCT_DECLINEELIG, Segment.SEGMENTSTATUSCT_DECLINEREQ };

    /**
     * General yes/no indicator values (used by many different fields)
     */
    public static final String INDICATOR_NO = "N";
    public static final String INDICATOR_YES = "Y";

    /**
	 * If Segment status is Death and Chargecodestatus is P show Zero for ADB Amount
	 * field in Financial Tab.
     */
    public static final String SEGMENT_CHARGECODESTATUS = "P";
    /**
     * Area table values returned to designate the status of a contract
     */
    public static final String PENDING = "NB";
    public static final String TERMINATED = "TM";
    public static final String ACTIVE = "IF";
    /**
     *  Denotes whether this Segment is a member of a ContractGroup or not
     */
    public static final String MEMBEROFCONTRACTGROUP_NO = Segment.INDICATOR_NO;
    public static final String MEMBEROFCONTRACTGROUP_YES = Segment.INDICATOR_YES;

    /**
	 * Denotes what type of BillSchedule change occurred on the Segment, if it was
	 * changed at all
     */
    public static final String BILLSCHEDULE_CHANGED_LIST_TO_LIST = "ListToList";
    public static final String BILLSCHEDULE_CHANGED_LIST_TO_INDIVIDUAL = "ListToIndividual";
    public static final String BILLSCHEDULE_CHANGED_INDIVIDUAL_TO_LIST = "IndividualToList";
    public static final String BILLSCHEDULE_CHANGED_WAS_NOT_CHANGED = "WasNotChanged";


    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    private CRUDEntityI crudEntityImpl;

    private String operator;
    private boolean saveChangeHistory;
    private SegmentVO segmentVO;
    private int riderNumber;
    private ProductStructure productStructure;
    private EDITDate changeHistoryEffDate;
    private String selectedCasetrackingOption;
    
    /**
     * In support of the SEGElement interface.
     */
    private Element segmentElement;

    //   children
    private Set<Payout> payouts;
    private Set<ContractClient> contractClients;
    private Set<Investment> investments;
    private Set<ContractSetup> contractSetups;
    private Set<Life> lifes;
    private Set<AgentHierarchy> agentHierarchies;
    private Set<Deposits> deposits;
    private Set inherentRiders;
    private Set<RequiredMinDistribution> requiredMinDistributions;
    private Set contractRequirements;
//    private Set segments;
    private Set contractTreaties;
    private Set<Bill> bills;
    private Set<PremiumDue> premiumDues;
//    private Set cases;
    private Set<Segment> riderSegments;
    private Set noteReminders;
    private Set<PayrollDeduction> payrollDeductions;
    private Set<SegmentHistory> segmentHistories;
    private Set<ValueAtIssue> valueAtIssues;
    private Set<SegmentSecondary> segmentSecondaryParents;
    private Set<SegmentSecondary> segmentSecondaryChildren;
    
    //  Parents
    private Long billScheduleFK;
    private Long batchContractSetupFK;
    private Long contractGroupFK;
    private Long departmentLocationFK;
    private Long segmentFK;
    private Long priorContractGroupFK;
    private Long originalContractGroupFK;
    private BillSchedule billSchedule;
    private BatchContractSetup batchContractSetup;
    private ContractGroup contractGroup;
    private MasterContract masterContract;
    private Long masterContractFK;
    private DepartmentLocation departmentLocation;
    private Segment segment;
    private ContractGroup priorContractGroup;
    private ContractGroup originalContractGroup;



    /**
     * Instantiates a Segment entity with a default SegmentVO.
     */
	public Segment() {
        init();
    }

    /**
     * Instantiates a Segment entity with a SegmentVO retrieved from persistence.
	 * 
     * @param segmentPK
     */
	public Segment(long segmentPK) {
        init();

        crudEntityImpl.load(this, segmentPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a Segment entity with a supplied SegmentVO.
	 * 
     * @param segmentVO
     */
	public Segment(SegmentVO segmentVO) {
        init();

        this.segmentVO = segmentVO;
    }

    /**
	 * Constructor that takes the minimal information needed to create a Segment.
	 * Sets default values.
     *
     * @param segmentNameCT                 segment name
     * @param optionCodeCT                  option code
     * @param segmentStatusCT               segment status
     */
	public Segment(String segmentNameCT, String optionCodeCT, String segmentStatusCT) {
        init();

        this.setDefaults();

        this.setSegmentNameCT(segmentNameCT);
        this.setSegmentStatusCT(segmentStatusCT);
        this.setOptionCodeCT(optionCodeCT);
		if (optionCodeCT.equalsIgnoreCase(Segment.OPTIONCODECT_TRADITIONAL_LIFE)) {
            this.setWorksheetTypeCT(Segment.WORKSHEETTYPECT_SUBMITTED);
        }
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public RequiredMinDistribution getRequiredMinDistribution() {
		RequiredMinDistribution requiredMinDistribution = getRequiredMinDistributions().isEmpty() ? null
				: (RequiredMinDistribution) getRequiredMinDistributions().iterator().next();

        return requiredMinDistribution;
    }


    /**
     * Getter.
	 * 
     * @return
     */
	public Set<RequiredMinDistribution> getRequiredMinDistributions() {
        return requiredMinDistributions;
    }

    /**
     * Setter.
	 * 
     * @param requiredMinDistributions
     */
	public void setRequiredMinDistributions(Set<RequiredMinDistribution> requiredMinDistributions) {
        this.requiredMinDistributions = requiredMinDistributions;
    }
    
	public String getRatedGenderCT() {
        return segmentVO.getRatedGenderCT();
    }

	public void setRatedGenderCT(String ratedGenderCT) {
        segmentVO.setRatedGenderCT(ratedGenderCT);
    }
    
	public String getGroupPlan() {
        return segmentVO.getGroupPlan();
    }

	public void setGroupPlan(String groupPlan) {
        segmentVO.setGroupPlan(groupPlan);
    }

	public String getUnderwritingClassCT() {
        return segmentVO.getUnderwritingClassCT();
    }

	public void setUnderwritingClassCT(String underwritingClass) {
        segmentVO.setUnderwritingClassCT(underwritingClass);
    }
    
    /**
     * Getter
     *
     * @return  set of ContractRequirements
     */
	public Set getContractRequirements() {
        return contractRequirements;
    }

    /**
     * Setter
     *
     * @param contractRequirements      set of ContractRequirements
     */
	public void setContractRequirements(Set contractRequirements) {
        this.contractRequirements = contractRequirements;
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Set<PayrollDeduction> getPayrollDeductions() {
        return payrollDeductions;
    }

    /**
     * Setter.
	 * 
     * @param payrollDeductions
     */
	public void setPayrollDeductions(Set<PayrollDeduction> payrollDeductions) {
        this.payrollDeductions = payrollDeductions;
    }

    /**
     * Add another payrollDeduction to the current mapped payrollDeductions.
	 * 
     * @param payrollDeduction
     */
	public void addPayrollDeduction(PayrollDeduction payrollDeduction) {
        this.payrollDeductions.add(payrollDeduction);
    }

	public void addRequiredMinDistribution(RequiredMinDistribution requiredMinDistribution) {
        this.getRequiredMinDistributions().add(requiredMinDistribution);

        requiredMinDistribution.setSegment(this);

        SessionHelper.saveOrUpdate(requiredMinDistribution, Segment.DATABASE);
    }

	public void removeRequiredMinDistribution(RequiredMinDistribution requiredMinDistribution) {
        this.getRequiredMinDistributions().remove(requiredMinDistribution);

        requiredMinDistribution.setSegment(null);

        SessionHelper.saveOrUpdate(requiredMinDistribution, Segment.DATABASE);
    }

    /**
     * Adds a ContractRequirement to the set of children
     *
     * @param contractRequirement
     */
	public void addContractRequirement(ContractRequirement contractRequirement) {
        this.getContractRequirements().add(contractRequirement);

        contractRequirement.setSegment(this);

        SessionHelper.saveOrUpdate(contractRequirement, Segment.DATABASE);
    }

    /**
     * Removes a ContractRequirement from the set of children
	 * 
     * @param contractRequirement
     */
	public void removeContractRequirement(ContractRequirement contractRequirement) {
        this.getContractRequirements().remove(contractRequirement);

        contractRequirement.setSegment(null);

        SessionHelper.saveOrUpdate(contractRequirement, Segment.DATABASE);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public InherentRider getInherentRider() {
		InherentRider inherentRider = getInherentRiders().isEmpty() ? null
				: (InherentRider) getInherentRiders().iterator().next();

        return inherentRider;
    }

   
    
    /**
     * Setter.
	 * 
     * @param inherentRider
     */
	public void setInherentRider(InherentRider inherentRider) {
        getInherentRiders().add(inherentRider);

        inherentRider.setSegment(this);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Set getInherentRiders() {
        return inherentRiders;
    }

    /**
     * Setter.
	 * 
     * @param inherentRiders
     */
	public void setInherentRiders(Set inherentRiders) {
        this.inherentRiders = inherentRiders;
    }

    /**
     * Removes a InherentRider from the set of children
	 * 
     * @param inherentRider
     */
	public void removeInherentRider(InherentRider inherentRider) {
        this.getInherentRiders().remove(inherentRider);

        inherentRider.setSegment(null);

        SessionHelper.saveOrUpdate(inherentRider, Segment.DATABASE);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Set<Deposits> getDeposits() {
        return deposits;
    }

    /**
     * Setter.
	 * 
     * @param deposits
     */
	public void setDeposits(Set<Deposits> deposits) {
        this.deposits = deposits;
    }

    /**
     * Removes a Deposit from the set of children
	 * 
     * @param deposit
     */
	public void removeDeposit(Deposits deposit) {
        this.getDeposits().remove(deposit);

        deposit.setSegment(null);

        SessionHelper.saveOrUpdate(deposit, Segment.DATABASE);
    }

    /**
     * Getter
	 * 
     * @return  set of agentHierarchies
     */
	public Set<AgentHierarchy> getAgentHierarchies() {
        return agentHierarchies;
    }

    /**
     * Setter
	 * 
     * @param agentHierarchies      set of agentHierarchies
     */
	public void setAgentHierarchies(Set agentHierarchies) {
        this.agentHierarchies = agentHierarchies;
    }

    /**
     * Adds a AgentHierarchy to the set of children
	 * 
     * @param agentHierarchy
     */
	public void addAgentHierarchy(AgentHierarchy agentHierarchy) {
        this.getAgentHierarchies().add(agentHierarchy);

        agentHierarchy.setSegment(this);

        SessionHelper.saveOrUpdate(agentHierarchy, Segment.DATABASE);
    }

    /**
     * Removes a AgentHierarchy from the set of children
	 * 
     * @param agentHierarchy
     */
	public void removeAgentHierarchy(AgentHierarchy agentHierarchy) {
        this.getAgentHierarchies().remove(agentHierarchy);

        agentHierarchy.setSegment(null);

        SessionHelper.saveOrUpdate(agentHierarchy, Segment.DATABASE);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Set getContractTreaties() {
        return contractTreaties;
    }

    /**
     * Setter.
	 * 
     * @param contractTreaties
     */
	public void setContractTreaties(Set contractTreaties) {
        this.contractTreaties = contractTreaties;
    }

    /**
     * Getter
	 * 
     * @return  set of bills
     */
	public Set getBills() {
        return bills;
    }

    /**
     * Setter
	 * 
     * @param bills      set of bills
     */
	public void setBills(Set bills) {
        this.bills = bills;
    }

    /**
     * Adds a Bill to the set of children
	 * 
     * @param bill
     */
	public void addBill(Bill bill) {
        this.getBills().add(bill);

        bill.setSegment(this);

        SessionHelper.saveOrUpdate(this, Segment.DATABASE);
    }

    /**
     * Removes a Bill from the set of children
	 * 
     * @param bill
     */
	public void removeBill(Bill bill) {
        this.getBills().remove(bill);

        bill.setSegment(null);

        SessionHelper.saveOrUpdate(bill, Segment.DATABASE);
    }
    /**
     * Getter
	 * 
     * @return  set of premiumDues
     */
	public Set<PremiumDue> getPremiumDues() {
        return premiumDues;
    }

    /**
     * Setter
	 * 
     * @param premiumDues      set of premiumDues
     */
	public void setPremiumDues(Set<PremiumDue> premiumDues) {
        this.premiumDues = premiumDues;
    }

    /**
     * Adds a PremiumDue to the set of children
	 * 
     * @param premiumDue
     */
	public void addPremiumDue(PremiumDue premiumDue) {
        this.getPremiumDues().add(premiumDue);

        premiumDue.setSegment(this);

        SessionHelper.saveOrUpdate(premiumDue, Segment.DATABASE);
    }

    /**
     * Removes a PremiumDue from the set of children
	 * 
     * @param premiumDue
     */
	public void removePremiumDue(PremiumDue premiumDue) {
        this.getPremiumDues().remove(premiumDue);

        premiumDue.setSegment(null);

        SessionHelper.saveOrUpdate(premiumDue, Segment.DATABASE);
    }

    /**
     * Getter
	 * 
     * @return  set of valueAtIssues
     */
	public Set<ValueAtIssue> getValueAtIssues() {
        return valueAtIssues;
    }

    /**
     * Setter
	 * 
     * @param valueAtIssues      set of valueAtIssues
     */
	public void setValueAtIssues(Set<ValueAtIssue> valueAtIssues) {
        this.valueAtIssues = valueAtIssues;
    }

    /**
     * Adds a ValueAtIssue to the set of children
	 * 
     * @param valueAtIssue
     */
	public void addValueAtIssue(ValueAtIssue valueAtIssue) {
        this.getValueAtIssues().add(valueAtIssue);

        valueAtIssue.setSegment(this);

        SessionHelper.saveOrUpdate(valueAtIssue, Segment.DATABASE);
    }

    /**
     * Removes a ValueAtIssue from the set of children
	 * 
     * @param valueAtIssue
     */
	public void removeValueAtIssue(ValueAtIssue valueAtIssue) {
        this.getValueAtIssues().remove(valueAtIssue);

        valueAtIssue.setSegment(null);

        SessionHelper.saveOrUpdate(valueAtIssue, Segment.DATABASE);
    }


    /**
     * Getter.
	 * 
     * @return
     */
	public Set<ContractSetup> getContractSetups() {
        return contractSetups;
    }

    /**
     * Getter.
	 * 
     * @param contractSetups
     */
	public void setContractSetups(Set<ContractSetup> contractSetups) {
        this.contractSetups = contractSetups;
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Long getSegmentPK() {
        return SessionHelper.getPKValue(segmentVO.getSegmentPK());
    }

    /**
     * Setter.
	 * 
     * @param segmentPK
     */
	public void setSegmentPK(Long segmentPK) {
       this.segmentVO.setSegmentPK(SessionHelper.getPKValue(segmentPK));
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Long getSegmentFK() {
        return SessionHelper.getPKValue(segmentVO.getSegmentFK());
    }

    /**
     * Setter.
	 * 
     * @param segmentFK
     */
	public void setSegmentFK(Long segmentFK) {
        this.segmentVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

	public EDITBigDecimal getAnnualInvestmentAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getAnnualInvestmentAmount());
    }

	public EDITDate getApplicationSignedDate() {
		return ((segmentVO.getApplicationSignedDate() != null) ? new EDITDate(segmentVO.getApplicationSignedDate())
				: null);
    }

	public String getCashWithAppInd() {
        return segmentVO.getCashWithAppInd();
    }

	public String getChargeCodeStatus() {
        return segmentVO.getChargeCodeStatus();
    }

	public EDITBigDecimal getChargeDeductAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getChargeDeductAmount());
    }

	public String getChargeDeductDivisionInd() {
        return segmentVO.getChargeDeductDivisionInd();
    }

	public EDITBigDecimal getCharges() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getCharges());
    }

	public EDITBigDecimal getCommitmentAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getCommitmentAmount());
    }

	public String getCommitmentIndicator() {
        return segmentVO.getCommitmentIndicator();
    }

	public EDITBigDecimal getCostBasis() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getCostBasis());
    }

	public String getCreationOperator() {
        return segmentVO.getCreationOperator();
    }

	public EDITDate getDateInEffect() {
        return ((segmentVO.getDateInEffect() != null) ? new EDITDate(segmentVO.getDateInEffect()) : null);
    }

	public EDITBigDecimal getDateOfDeathValue() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getDateOfDeathValue());
    }

	public EDITBigDecimal getAnnuitizationValue() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getAnnuitizationValue());
    }

	public EDITBigDecimal getDialableSalesLoadPercentage() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getDialableSalesLoadPercentage());
    }

	public EDITBigDecimal getDismembermentPercent() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getDismembermentPercent());
    }

	public String getExchangeInd() {
        return segmentVO.getExchangeInd();
    }

	public EDITBigDecimal getFreeAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getFreeAmount());
    }

	public EDITBigDecimal getFreeAmountRemaining() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getFreeAmountRemaining());
    }

	public int getFreeLookDaysOverride() {
        return segmentVO.getFreeLookDaysOverride();
    }

	public EDITDate getLastAnniversaryDate() {
        return ((segmentVO.getLastAnniversaryDate() != null) ? new EDITDate(segmentVO.getLastAnniversaryDate()) : null);
    }

	public EDITDate getOpenClaimEndDate() {
        return ((segmentVO.getOpenClaimEndDate() != null) ? new EDITDate(segmentVO.getOpenClaimEndDate()) : null);
    }

	public String getPointInScaleIndicator() {
        return segmentVO.getPointInScaleIndicator();
    }

	public EDITDate getPolicyDeliveryDate() {
        return ((segmentVO.getPolicyDeliveryDate() != null) ? new EDITDate(segmentVO.getPolicyDeliveryDate()) : null);
    }

	public String getQualNonQualCT() {
        return segmentVO.getQualNonQualCT();
    }

	public String getQualifiedTypeCT() {
        return segmentVO.getQualifiedTypeCT();
    }

	public String getAuthorizedSignatureCT() {
        return segmentVO.getAuthorizedSignatureCT();
    }

	public String getPostIssueStatusCT() {
        return segmentVO.getPostIssueStatusCT();
    }

	public String getPriorPRDDue() {
        return segmentVO.getPriorPRDDue();
    }

	public EDITDate getQuoteDate() {
        return ((segmentVO.getQuoteDate() != null) ? new EDITDate(segmentVO.getQuoteDate()) : null);
    }

	public EDITBigDecimal getRecoveredCostBasis() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getRecoveredCostBasis());
    }

	public int getRiderNumber() {
        return segmentVO.getRiderNumber();
    }

	public int getConsecutiveAPLCount() {
        return segmentVO.getConsecutiveAPLCount();
    }

	public EDITBigDecimal getSavingsPercent() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getSavingsPercent());
    }

//    public java.lang.String getSuppNewContractNumber()
//    {
//        return segmentVO.getSuppNewContractNumber();
//    }

	public java.lang.String getSuppOriginalContractNumber() {
        return segmentVO.getSuppOriginalContractNumber();
    }

	public EDITBigDecimal getTotalFaceAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getTotalFaceAmount());
    }

	public EDITBigDecimal getEOBCum() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getEOBCum());
    } //-- java.math.BigDecimal getEOBCum()

	public EDITBigDecimal getEOBMaximum() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getEOBMaximum());
    } //-- java.math.BigDecimal getEOBMaximum()

	public int getEOBMultiple() {
        return segmentVO.getEOBMultiple();
    } //-- java.math.integer getEOBMultiple()

	public void setAnnualInsuranceAmount(EDITBigDecimal annualInsuranceAmount) {
        segmentVO.setAnnualInsuranceAmount(SessionHelper.getEDITBigDecimal(annualInsuranceAmount));
    }

	public void setAnnualInvestmentAmount(EDITBigDecimal annualInvestmentAmount) {
        segmentVO.setAnnualInvestmentAmount(SessionHelper.getEDITBigDecimal(annualInvestmentAmount));
    }

	public void setApplicationReceivedDate(EDITDate applicationReceivedDate) {
		segmentVO.setApplicationReceivedDate(
				(applicationReceivedDate != null) ? applicationReceivedDate.getFormattedDate() : null);
    }

	public void setApplicationSignedDate(EDITDate applicationSignedDate) {
		segmentVO.setApplicationSignedDate(
				(applicationSignedDate != null) ? applicationSignedDate.getFormattedDate() : null);
    }

	public void setCashWithAppInd(String cashWithAppInd) {
        segmentVO.setCashWithAppInd(cashWithAppInd);
    }

    //-- void setChargeCodeStatus(java.lang.String)
	public void setChargeCodeStatus(String chargeCodeStatus) {
        segmentVO.setChargeCodeStatus(chargeCodeStatus);
    }

    //-- void setChargeDeductAmount(java.math.BigDecimal)
	public void setChargeDeductAmount(EDITBigDecimal chargeDeductAmount) {
        segmentVO.setChargeDeductAmount(SessionHelper.getEDITBigDecimal(chargeDeductAmount));
    }

    //-- void setChargeDeductDivisionInd(java.lang.String)
	public void setChargeDeductDivisionInd(String chargeDeductDivisionInd) {
        segmentVO.setChargeDeductDivisionInd(chargeDeductDivisionInd);
    }

    //-- void setCharges(java.math.BigDecimal)
	public void setCharges(EDITBigDecimal charges) {
        segmentVO.setCharges(SessionHelper.getEDITBigDecimal(charges));
    }

    //-- void setCommitmentAmount(java.math.BigDecimal)
	public void setCommitmentAmount(EDITBigDecimal commitmentAmount) {
        segmentVO.setCommitmentAmount(SessionHelper.getEDITBigDecimal(commitmentAmount));
    }

    //-- void setCommitmentIndicator(java.lang.String)
	public void setCommitmentIndicator(String commitmentIndicator) {
        segmentVO.setCommitmentIndicator(commitmentIndicator);
    }

    //-- void setContractNumber(java.lang.String)
	public void setContractNumber(String contractNumber) {
        segmentVO.setContractNumber(contractNumber);
    }

    //-- void setCostBasis(java.math.BigDecimal)
	public void setCostBasis(EDITBigDecimal costBasis) {
        segmentVO.setCostBasis(SessionHelper.getEDITBigDecimal(costBasis));
    }

    //-- void setCreationDate(java.lang.String)
	public void setCreationDate(EDITDate creationDate) {
        segmentVO.setCreationDate((creationDate != null) ? creationDate.getFormattedDate() : null);
    }

    //-- void setCreationOperator(java.lang.String)
	public void setCreationOperator(String creationOperator) {
        segmentVO.setCreationOperator(creationOperator);
    }

    //-- void setDateInEffect(java.lang.String)
	public void setDateInEffect(EDITDate dateInEffect) {
        segmentVO.setDateInEffect((dateInEffect != null) ? dateInEffect.getFormattedDate() : null);
    }

    //-- void setDateOfDeathValue(java.math.BigDecimal)
	public void setDateOfDeathValue(EDITBigDecimal dateOfDeathValue) {
        segmentVO.setDateOfDeathValue(SessionHelper.getEDITBigDecimal(dateOfDeathValue));
    }

    //-- void setAnnuitizationValue(java.math.BigDecimal)
	public void setAnnuitizationValue(EDITBigDecimal annuitizationValue) {
        segmentVO.setAnnuitizationValue(SessionHelper.getEDITBigDecimal(annuitizationValue));
    }

    //-- void setDialableSalesLoadPercentage(java.math.BigDecimal)
	public void setDialableSalesLoadPercentage(EDITBigDecimal dialableSalesLoadPercentage) {
        segmentVO.setDialableSalesLoadPercentage(SessionHelper.getEDITBigDecimal(dialableSalesLoadPercentage));
    }

    //-- void setDismembermentPercent(java.math.BigDecimal)
	public void setDismembermentPercent(EDITBigDecimal dismembermentPercent) {
        segmentVO.setDismembermentPercent(SessionHelper.getEDITBigDecimal(dismembermentPercent));
    }

    //-- void setExchangeInd(java.lang.String)
	public void setExchangeInd(String exchangeInd) {
        segmentVO.setExchangeInd(exchangeInd);
    }

    //-- void setFees(java.math.BigDecimal)
	public void setFees(EDITBigDecimal fees) {
        segmentVO.setFees(SessionHelper.getEDITBigDecimal(fees));
    }

    //-- void setFreeAmount(java.math.BigDecimal)
	public void setFreeAmount(EDITBigDecimal freeAmount) {
        segmentVO.setFreeAmount(SessionHelper.getEDITBigDecimal(freeAmount));
    }

    //-- void setFreeAmountRemaining(java.math.BigDecimal)
	public void setFreeAmountRemaining(EDITBigDecimal freeAmountRemaining) {
        segmentVO.setFreeAmountRemaining(SessionHelper.getEDITBigDecimal(freeAmountRemaining));
    }

    //-- void setFreeLookDaysOverride(int)
	public void setFreeLookDaysOverride(int freeLookDaysOverride) {
        segmentVO.setFreeLookDaysOverride(freeLookDaysOverride);
    }

	public void setIssueDate(EDITDate issueDate) {
        segmentVO.setIssueDate((issueDate != null) ? issueDate.getFormattedDate() : null);
    }

	public void setIssueStateCT(String issueStateCT) {
        segmentVO.setIssueStateCT(issueStateCT);
    }

	public void setIssueStateORInd(String issueStateORInd) {
        segmentVO.setIssueStateORInd(issueStateORInd);
    }

	public void setOriginalStateCT(String originalStateCT) {
        segmentVO.setOriginalStateCT(originalStateCT);
    }

	public void setAuthorizedSignatureCT(String authorizedSignatureCT) {
        segmentVO.setAuthorizedSignatureCT(authorizedSignatureCT);
    }

    //-- void setLastAnniversaryDate(java.lang.String)
	public void setLastAnniversaryDate(EDITDate lastAnniversaryDate) {
        segmentVO.setLastAnniversaryDate((lastAnniversaryDate != null) ? lastAnniversaryDate.getFormattedDate() : null);
    }

    //-- void setLoads(java.math.BigDecimal)
	public void setLoads(EDITBigDecimal loads) {
        segmentVO.setLoads(SessionHelper.getEDITBigDecimal(loads));
    }

    //-- void setOptionCodeCT(java.lang.String)
	public void setOptionCodeCT(String optionCodeCT) {
        segmentVO.setOptionCodeCT(optionCodeCT);
    }

    //-- void setOpenClaimEndDate(java.lang.String)
	public void setOpenClaimEndDate(EDITDate openClaimEndDate) {
        segmentVO.setOpenClaimEndDate((openClaimEndDate != null) ? openClaimEndDate.getFormattedDate() : null);
    }

    //-- void setPointInScaleIndicator(java.lang.String)
	public void setPointInScaleIndicator(String pointInScaleIndicator) {
        segmentVO.setPointInScaleIndicator(pointInScaleIndicator);
    }

    //-- void setPolicyDeliveryDate(java.lang.String)
	public void setPolicyDeliveryDate(EDITDate policyDeliveryDate) {
        segmentVO.setPolicyDeliveryDate((policyDeliveryDate != null) ? policyDeliveryDate.getFormattedDate() : null);
    }

	public void setPostIssueStatusCT(String postIssueStatusCT) {
        segmentVO.setPostIssueStatusCT(postIssueStatusCT);
    }

	public void setPriorPRDDue(String priorPRDDue) {
        segmentVO.setPriorPRDDue(priorPRDDue);
    }

    //-- void setQualNonQualCT(java.lang.String)
	public void setQualNonQualCT(String qualNonQualCT) {
        segmentVO.setQualNonQualCT(qualNonQualCT);
    }

    //-- void setQualifiedTypeCT(java.lang.String)
	public void setQualifiedTypeCT(String qualifiedTypeCT) {
        segmentVO.setQualifiedTypeCT(qualifiedTypeCT);
    }

    //-- void setQuoteDate(java.lang.String)
	public void setQuoteDate(EDITDate quoteDate) {
        segmentVO.setQuoteDate((quoteDate != null) ? quoteDate.getFormattedDate() : null);
    }

    //-- void setRecoveredCostBasis(java.math.BigDecimal)
	public void setRecoveredCostBasis(EDITBigDecimal recoveredCostBasis) {
        segmentVO.setRecoveredCostBasis(SessionHelper.getEDITBigDecimal(recoveredCostBasis));
    }

    //-- void setRiderNumber(int)
	public void setRiderNumber(int riderNumber) {
        segmentVO.setRiderNumber(riderNumber);
    }

    //-- void setConsecutiveAPLCount(int)
	public void setConsecutiveAPLCount(int consecutiveAPLCount) {
        segmentVO.setConsecutiveAPLCount(consecutiveAPLCount);
    }

    //-- void setSavingsPercent(java.math.BigDecimal)
	public void setSavingsPercent(EDITBigDecimal savingsPercent) {
        segmentVO.setSavingsPercent(SessionHelper.getEDITBigDecimal(savingsPercent));
    }



    //-- void setSegmentNameCT(java.lang.String)
	public void setSegmentNameCT(String segmentNameCT) {
        segmentVO.setSegmentNameCT(segmentNameCT);
    }

    //-- void setStatusChangeDate(java.lang.String)
	public void setStatusChangeDate(EDITDate statusChangeDate) {
        segmentVO.setStatusChangeDate((statusChangeDate != null) ? statusChangeDate.getFormattedDate() : null);
    }

    //-- void setSuppNewContractNumber(java.lang.String)
//    public void setSuppNewContractNumber(String suppNewContractNumber)
//    {
//        segmentVO.setSuppNewContractNumber(suppNewContractNumber);
//    }

    //-- void setSuppOriginalContractNumber(java.lang.String)
	public void setSuppOriginalContractNumber(String suppOriginalContractNumber) {
        segmentVO.setSuppOriginalContractNumber(suppOriginalContractNumber);
    }

    //-- void setTaxReportingGroup(java.lang.String)
	public void setTaxReportingGroup(String taxReportingGroup) {
        segmentVO.setTaxReportingGroup(taxReportingGroup);
    }

    //-- void setTerminationDate(java.lang.String)
	public void setTerminationDate(EDITDate terminationDate) {
        segmentVO.setTerminationDate((terminationDate != null) ? terminationDate.getFormattedDate() : null);
    }
    
	public void setExpiryDate(EDITDate expiryDate) {
        segmentVO.setExpiryDate((expiryDate != null) ? expiryDate.getFormattedDate() : null);
    }
	
	public void setSegmentChangeEffectiveDate(EDITDate segmentChangeEffectiveDate) {
        segmentVO.setSegmentChangeEffectiveDate((segmentChangeEffectiveDate != null) ? segmentChangeEffectiveDate.getFormattedDate() : null);
	}
    
	public EDITBigDecimal getIndivAnnPremium() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getIndivAnnPremium());
    }
    
	public void setIndivAnnPremium(EDITBigDecimal indivAnnPremium) {
        segmentVO.setIndivAnnPremium(SessionHelper.getEDITBigDecimal(indivAnnPremium));

    }
    
	public String getSequence() {
        return segmentVO.getSequence();
    }
    
	public void setSequence(String sequence) {
    	segmentVO.setSequence(sequence);
    }
    
	public String getLocation() {
        return segmentVO.getLocation();
    }
    
	public void setLocation(String location) {
    	segmentVO.setLocation(location);
    }

    //-- void setWaiveFreeLookIndicator(java.lang.String)
	public void setWaiveFreeLookIndicator(String waiveFreeLookIndicator) {
        segmentVO.setWaiveFreeLookIndicator(waiveFreeLookIndicator);
    }

    //-- void setWaiverInEffect(java.lang.String)
	public void setWaiverInEffect(String waiverInEffect) {
        segmentVO.setWaiverInEffect(waiverInEffect);
    }

	public void setTotalFaceAmount(EDITBigDecimal totalFaceAmount) {
        segmentVO.setTotalFaceAmount(SessionHelper.getEDITBigDecimal(totalFaceAmount));
    }

    //-- java.lang.String getTaxReportingGroup()
	public String getTaxReportingGroup() {
        return segmentVO.getTaxReportingGroup();
    }

    //-- java.lang.String getTerminationDate()
	public EDITDate getTerminationDate() {
        return ((segmentVO.getTerminationDate() != null) ? new EDITDate(segmentVO.getTerminationDate()) : null);
    }

	public EDITDate getSegmentChangeEffectiveDate() {
        return ((segmentVO.getSegmentChangeEffectiveDate() != null) ? new EDITDate(segmentVO.getSegmentChangeEffectiveDate()) : null);
	}
    
	public EDITDate getExpiryDate() {
        return ((segmentVO.getExpiryDate() != null) ? new EDITDate(segmentVO.getExpiryDate()) : null);
    }

    //-- java.lang.String getWaiveFreeLookIndicator()
	public String getWaiveFreeLookIndicator() {
        return segmentVO.getWaiveFreeLookIndicator();
    }

    //-- java.lang.String getWaiverInEffect()
	public String getWaiverInEffect() {
        return segmentVO.getWaiverInEffect();
    }

	public String getContractTypeCT() {
        return segmentVO.getContractTypeCT();
    } //-- java.lang.String getContractTypeCT()

	public void setContractTypeCT(String contractTypeCT) {
        segmentVO.setContractTypeCT(contractTypeCT);
    } //-- void setContractTypeCT(java.lang.String)

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITBigDecimal getUnits() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getUnits());
    }

    /**
     * Setter.
	 * 
     * @param units
     */
	public void setUnits(EDITBigDecimal units) {
        segmentVO.setUnits(SessionHelper.getEDITBigDecimal(units));
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITBigDecimal getAnnualPremium() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getAnnualPremium());
    }

    /**
     * Setter.
	 * 
     * @param units
     */
	public void setAnnualPremium(EDITBigDecimal annualPremium) {
        segmentVO.setAnnualPremium(SessionHelper.getEDITBigDecimal(annualPremium));
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public int getCommissionPhaseID() {
        return segmentVO.getCommissionPhaseID();
    }

    /**
     * Setter.
	 * 
     * @param units
     */
	public void setCommissionPhaseID(int commissionPhaseID) {
        segmentVO.setCommissionPhaseID(commissionPhaseID);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getCommissionPhaseOverride() {
        return segmentVO.getCommissionPhaseOverride();
    }

    /**
     * Setter.
	 * 
     * @param units
     */
	public void setCommissionPhaseOverride(String commissionPhaseOverride) {
        segmentVO.setCommissionPhaseOverride(commissionPhaseOverride);
    }

	public String getMemberOfContractGroup() {
        return segmentVO.getMemberOfContractGroup();
    }

	public void setMemberOfContractGroup(String memberOfContractGroup) {
        segmentVO.setMemberOfContractGroup(memberOfContractGroup);
    }

	public String getApplicationNumber() {
        return segmentVO.getApplicationNumber();
    }

	public void setApplicationNumber(String applicationNumber) {
        segmentVO.setApplicationNumber(applicationNumber);
    }

	public String getApplicationSignedStateCT() {
        return segmentVO.getApplicationSignedStateCT();
    }

	public void setApplicationSignedStateCT(String applicationSignedStateCT) {
        segmentVO.setApplicationSignedStateCT(applicationSignedStateCT);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITBigDecimal getAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getAmount());
    }

    //-- java.math.BigDecimal getAmount()

    /**
     * Setter.
	 * 
     * @param amount
     */
	public void setAmount(EDITBigDecimal amount) {
        segmentVO.setAmount(SessionHelper.getEDITBigDecimal(amount));
    }
    
    /**
     * Getter.
	 * 
     * @return
     */
	public int getAgeAtIssue() {
        return segmentVO.getAgeAtIssue();
    }    
    
    /**
     * Setter.
	 * 
     * @param ageAtIssue
     */
	public void setAgeAtIssue(int ageAtIssue) {
        segmentVO.setAgeAtIssue(ageAtIssue);
    }
    
    /**
     * Getter.
	 * 
     * @return
     */
	public EDITBigDecimal getOriginalUnits() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getOriginalUnits());
    }
    
    /**
     * Setter.
	 * 
     * @param originalUnits
     */
	public void setOriginalUnits(EDITBigDecimal originalUnits) {
        segmentVO.setOriginalUnits(SessionHelper.getEDITBigDecimal(originalUnits));
    }

	public EDITDate getConversionDate() {
        return ((segmentVO.getConversionDate() != null) ? new EDITDate(segmentVO.getConversionDate()) : null);
    }

	public void setConversionDate(EDITDate conversionDate) {
        segmentVO.setConversionDate((conversionDate != null) ? conversionDate.getFormattedDate() : null);
    }

    //-- void setAmount(java.math.BigDecimal)

	public void setEOBCum(EDITBigDecimal EOBCum) {
        segmentVO.setEOBCum(SessionHelper.getEDITBigDecimal(EOBCum));
    } //-- void setEOBCum(java.math.BigDecimal)

	public void setEOBMaximum(EDITBigDecimal EOBMaximum) {
        segmentVO.setEOBMaximum(SessionHelper.getEDITBigDecimal(EOBMaximum));
    } //-- void setEOBMaximum(java.math.BigDecimal)

	public void setEOBMultiple(int EOBMultiple) {
        segmentVO.setEOBMultiple(EOBMultiple);
    } //-- void setEOBMultiple(java.math.integer)
    
    /**
     * Getter.
	 * 
     * @return
     */
	public Set<ContractClient> getContractClients() {
        return contractClients;
    }

    /**
     * Getter
	 * 
     * @return
     */
	public String getGIOOption() {
        return segmentVO.getGIOOption();
    } //-- java.lang.String getGIOOption()

    /**
     * Setter
	 * 
     * @param GIOOption
     */
	public void setGIOOption(String gioOption) {
        segmentVO.setGIOOption(gioOption);
    } //-- void setGIOOption(java.lang.String)

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITBigDecimal getDeductionAmountOverride() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getDeductionAmountOverride());
    }

    /**
     * Setter.
	 * 
     * @param deductionAmountOverride
     */
	public void setDeductionAmountOverride(EDITBigDecimal deductionAmountOverride) {
        segmentVO.setDeductionAmountOverride(SessionHelper.getEDITBigDecimal(deductionAmountOverride));
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITDate getDeductionAmountEffectiveDate() {
        return SessionHelper.getEDITDate(segmentVO.getDeductionAmountEffectiveDate());
    }

    /**
     * Setter.
	 * 
     * @param deductionAmountEffectiveDate
     */
	public void setDeductionAmountEffectiveDate(EDITDate deductionAmountEffectiveDate) {
        segmentVO.setDeductionAmountEffectiveDate(SessionHelper.getEDITDate(deductionAmountEffectiveDate));
    }

    /**
     * 
     * Gets the associated ContractClients with the specified RoleTyepCT.
	 * 
     * @param roleTypeCT
     * @return the ContractClients of type roleTypeCT
     */
	public ContractClient[] getContractClients(String roleTypeCT) {
        List<ContractClient> contractClientsOfRoleType = new ArrayList<ContractClient>();
    
        Set<ContractClient> contractClients = getContractClients();
        
		for (ContractClient contractClient : contractClients) {
			if (contractClient.getClientRole().getRoleTypeCT().equals(roleTypeCT)) {
                contractClientsOfRoleType.add(contractClient);
            }
        }
        
        return contractClientsOfRoleType.toArray(new ContractClient[contractClientsOfRoleType.size()]);
    }

    /**
     * Setter.
	 * 
     * @param contractClients
     */
	public void setContractClients(Set<ContractClient> contractClients) {
        this.contractClients = contractClients;

        //
		// ContractClient[] contractClientArray = (ContractClient[])
		// contractClients.toArray(new ContractClient[contractClients.size()]);
        //
        //        for (int i = 0; i < contractClientArray.length; i++)
        //        {
        //            contractClient = contractClientArray[i];
        //            contractClient.setSegment(this);
        //        }
    }

   /**
     * Removes a ContractClient from the set of children
	 * 
     * @param contractClient
     */
	public void removeContractClient(ContractClient contractClient) {
        this.getContractClients().remove(contractClient);

        contractClient.setSegment(null);

        SessionHelper.saveOrUpdate(contractClient, Segment.DATABASE);
    }

    /**
      * Get a single ContractClient
	 * 
      * @return
      */
	public ContractClient getContractClient() {
		ContractClient contractClient = getContractClients().isEmpty() ? null
				: (ContractClient) getContractClients().iterator().next();

         return contractClient;
     }


    /**
     * Getter.
	 * 
     * @return
     */
	public Set<Investment> getInvestments() {
        return investments;
    }

    /**
      * Setter.
	 * 
      * @param investments
      */
	public void setInvestments(Set<Investment> investments) {
        this.investments = investments;
    }

   /**
     * Removes a Investment from the set of children
	 * 
     * @param life
     */
	public void removeInvestment(Investment investment) {
        this.getInvestments().remove(investment);

        investment.setSegment(null);

        SessionHelper.saveOrUpdate(investment, Segment.DATABASE);
    }

	/**
     * Getter.
	 * 
     * @return
     */
	public int getTotalActiveBeneficiaries() {
        return segmentVO.getTotalActiveBeneficiaries();
    }

    /**
     * Setter.
	 * 
     * @param totalActiveBenes
     */
	public void setTotalActiveBeneficiaries(int totalActiveBenes) {
        segmentVO.setTotalActiveBeneficiaries(totalActiveBenes);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public int getRemainingBeneficiaries() {
        return segmentVO.getRemainingBeneficiaries();
    }

	/**
	 * Setter.
	 * 
     * @param remainingBenes
     */
	public void setRemainingBeneficiaries(int remainingBenes) {
        segmentVO.setRemainingBeneficiaries(remainingBenes);
	}

    /**
    * @return
    * @see CRUDEntity#cloneCRUDEntity()
    */
	public CRUDEntity cloneCRUDEntity() {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

	public void addInvestment(Investment investment) {
        getInvestments().add(investment);

        investment.setSegment(this);

//        SessionHelper.saveOrUpdate(investment, SessionHelper.EDITSOLUTIONS);

        SessionHelper.saveOrUpdate(this, Segment.DATABASE);
    }
    
	public void addLife(Life life) {
        getLifes().add(life);
        
        life.setSegment(this);
    }

    /**
     * @see CRUDEntity#delete()
     */
	public void delete() {
        //  If it's PolicyGroup is an individual, delete it too
//        if (this.policyGroup.isTypeIndividual())
//        {
//            this.policyGroup.hDelete();
//        }
    }

    /**
     * Deletes all ChangeHistory entities associated with this Segment entity.
     */
	public void deleteChangeHistory() {
        ChangeHistory[] changeHistories = getChangeHistory();

		if (changeHistories != null) {
			for (int i = 0; i < changeHistories.length; i++) {
                changeHistories[i].delete();
            }
        }
    }

    /**
	 * Cycles through all AgentHierarchies for the current Segment and validates
	 * that the PlacedAgents of its Snapshot matches the PlacedAgents of the
	 * originating PlacedAgentBranch.
	 * 
	 * @throws EDITAgentException if an Snapshot can not longer be matched to its
	 *                            origination PlacedAgentBranch
     */
	public void establishValidAgentHierarchies() throws EDITAgentException {
        List agentHierarchies = AgentHierarchy.findBy_SegmentPK(getSegmentPK());

		for (int i = 0; i < agentHierarchies.size(); i++) {
            AgentHierarchy agentHierarchy = (AgentHierarchy) agentHierarchies.get(i);

            agentHierarchy.validateAgentHierarchy(getApplicationReceivedDate());
        }

//        SessionHelper.clearSession(SessionHelper.EDITSOLUTIONS); GF Still necessary?
    }

    /**
     * CRUD. The set of child AgentHierachy entities associated with this Segment.
	 * 
     * @return the set of AgentHierarchy entities, or null
     */
	public AgentHierarchy[] get_AgentHierarchies() {
        AgentHierarchy[] agentHierarchies;

		agentHierarchies = (AgentHierarchy[]) crudEntityImpl.getChildEntities(this, AgentHierarchy.class,
				AgentHierarchyVO.class, ConnectionFactory.EDITSOLUTIONS_POOL);

        return agentHierarchies;
    }

    /**
     * Getter
	 * 
     * @return   EDITDate
     */
	public EDITDate getApplicationReceivedDate() {
		return ((segmentVO.getApplicationReceivedDate() != null) ? new EDITDate(segmentVO.getApplicationReceivedDate())
				: null);
    }

    /**
     * Returns the set of associated ChangeHistory entities.
	 * 
     * @return
     */
	public ChangeHistory[] getChangeHistory() {
        return ChangeHistory.findBySegmentPK(getPK());
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getContractNumber() {
        return segmentVO.getContractNumber();
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getStatus() {
        return segmentVO.getSegmentStatusCT();
    }

    /**
      * Getter.
	 * 
      * @return
      */
	public String getOptionCodeCT() {
        return segmentVO.getOptionCodeCT();
    }

    /**
      * Getter.
	 * 
      * @return
      */
	public Long getProductStructureFK() {
        return SessionHelper.getPKValue(segmentVO.getProductStructureFK());
    }

    /**
     * Getter.
	 * 
      * @return
     */
	public ContractClientVO[] getContractClientVOs() {
        return segmentVO.getContractClientVO();
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITDate getEffectiveDate() {
        return ((segmentVO.getEffectiveDate() != null) ? new EDITDate(segmentVO.getEffectiveDate()) : null);
    }

	public void setEffectiveDate(EDITDate editDate) {
        segmentVO.setEffectiveDate((editDate != null) ? editDate.getFormattedDate() : null);
    }

    /**
     * Getter for billScheduleFK
	 * 
     * @return
     */
	public Long getBillScheduleFK() {
        return SessionHelper.getPKValue(segmentVO.getBillScheduleFK());
    }

    /**
     * Getter for batchContractSetupFK
	 * 
     * @return
     */
	public Long getBatchContractSetupFK() {
        return SessionHelper.getPKValue(segmentVO.getBatchContractSetupFK());
    }

    /**
     * Getter for contractGroupFK
	 * 
     * @return
     */
	public Long getContractGroupFK() {
        return SessionHelper.getPKValue(segmentVO.getContractGroupFK());
    }

    /**
     * Getter for masterContractFK
	 * 
     * @return
     */
	public Long getMasterContractFK() {
        return masterContractFK;
    }

    /**
     * Getter for masterContract
	 * 
     * @return
     */
	public MasterContract getMasterContract() {
        return this.masterContract;
    }

    /**
     * Getter for departmentLocationFK
	 * 
     * @return
     */
	public Long getDepartmentLocationFK() {
        return SessionHelper.getPKValue(segmentVO.getDepartmentLocationFK());
    }

    /**
     * Getter for priorContractGroupFK
	 * 
     * @return
     */
	public Long getPriorContractGroupFK() {
        return SessionHelper.getPKValue(segmentVO.getPriorContractGroupFK());
    }

    
   
    /**
     * Getter for originalContractGroupFK
	 * 
     * @return
     */
	public Long getOriginalContractGroupFK() {
        return SessionHelper.getPKValue(segmentVO.getOriginalContractGroupFK());
    }

    /**
     * Getter
	 * 
     * @return
     */
	public EDITDate getScheduledTerminationDate() {
		return ((segmentVO.getScheduledTerminationDate() != null)
				? new EDITDate(segmentVO.getScheduledTerminationDate())
				: null);
    } //-- java.lang.String getScheduledTerminationDate()

    /**
     * @return
     * @see CRUDEntity#getPK()
     */
	public long getPK() {
        return segmentVO.getSegmentPK();
    }

    /**
	 * Finds the rider Segments associated with this base Segment. Using CRUD.
	 * 
     * @return
     */
	public Segment[] get_Riders() {
        SegmentVO[] riderVOs = new SegmentDAO().findRidersBySegmentPK(getPK());

        return (Segment[]) CRUDEntityImpl.mapVOToEntity(riderVOs, Segment.class);
    }
    
    /**
     * Returns all associated rider Segments of this Base Segment.
	 * 
     * @return
     */
	public Segment[] getRiders() {
        Segment[] riderSegments = Segment.findBy_SegmentFK(getSegmentPK());
        
        return riderSegments;
    }

    /**
     * @return
     * @see CRUDEntity#getVO()
     */
	public VOObject getVO() {
        return segmentVO;
    }

    /**
     * @return
     * @see CRUDEntity#isNew()
     */
	public boolean isNew() {
        return crudEntityImpl.isNew(this);
    }

    /**
	 * Determines whether this Segment belongs to a Case or not (i.e. it's an
	 * individual Segment)
     *
     * @return  true if belongs to a Case, false otherwise
     */
	public boolean belongsToACase() {
		if (this.contractGroupFK == null) {
            return false;
		} else {
            return true;
        }
    }

    /**
	 * Saves the Segment recursively and creates and saves an Individual PolicyGroup
	 * if required
     * <p/>
	 * NOTE: This method "cheats" - it does not use crudEntityImpl's save method
	 * because that method does not do a recursive save. Segment has a lot of
	 * children that have not been created into CRUDEntity objects yet and it's too
	 * much work to do at this time. The cheat is that this method uses
	 * StorageManager's saveSegment method to do the recursive save (as well as
	 * dealing with ChangeHistory). This needs to be addressed as we continue to
	 * develop more CRUDEntity objects and move the business logic to the entity
	 * objects.
     *
     * @throws EDITContractException        if there is an error on the save
     */
	public void saveSegmentForNewBusiness() throws EDITContractException {
        // needs to be verified before saving the contract
        // check whether it is necessary to create freelook transaction or not
        boolean createFreeLookTransaction = createFreeLookTransaction();

		// At this point of time the persistence of new business and active contracts
		// are done at two places.
        // and the new bussines is still saved in the old way using storage manager.
        verifyAndUpdatePolicyDeliveryDate();

        contract.dm.StorageManager sm = new contract.dm.StorageManager();

        setContractNumberToUpperCase();

        sm.saveSegment(this, this.operator);

		if (createFreeLookTransaction) {
            createFreeLookEDITTrx();
        }

		try {
			if (this.segmentVO.getRequiredMinDistributionVOCount() > 0) {
                RequiredMinDistributionVO rmdVO = this.segmentVO.getRequiredMinDistributionVO(0);

				if ((rmdVO.getElectionCT() != null) && (rmdVO.getFrequencyCT() != null)) {
                    generateOrUpdateRmdTrx(rmdVO);
                }
            }

			// need to do call the following method other wise the updates done via CRUD may
			// not be reflected in the casetracking pages.
//            SessionHelper.clearSession(SessionHelper.EDITSOLUTIONS);  GF Still necessary?
		} catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
	 * Recursive save of the segment and all its children. This method is used after
	 * the contract is active.
     */
	public void save() {
		try {
            SessionHelper.beginTransaction(Segment.DATABASE);

            //SAVE USING HIBERNATE - 09/13/07
            this.hSave();
            
            //When adding a new contract the segmentVO PK is still zero,
            segmentVO.setSegmentPK(this.getSegmentPK());
            String product = segmentVO.getOptionCodeCT();
            //and children of the BaseSegment
            saveChildrenOfBaseAndRiders(segmentVO);

            //then riders and their children
            if (segmentVO.getSegmentVOCount() > 0)
            {
                SegmentVO[] riderVOs = segmentVO.getSegmentVO();
                Segment rider = null;

                for (int i = 0; i < riderVOs.length; i++)
                {
                	if (riderVOs[i].getAgentHierarchyVOCount() > 0)
                    {
                		// save agent hierarchy changes on the riders
                        AgentHierarchyVO[] agentHierarchyVOs = riderVOs[i].getAgentHierarchyVO();
                        for (int a = 0; a < agentHierarchyVOs.length; a++)
                        {
                            agentHierarchyVOs[a].setSegmentFK(riderVOs[i].getSegmentPK());

                            AgentHierarchy agentHierarchy = (AgentHierarchy) SessionHelper.mapVOToHibernateEntity(agentHierarchyVOs[a], DATABASE);
                            
                            rider = (Segment) SessionHelper.map(riderVOs[i], SessionHelper.EDITSOLUTIONS);
                            agentHierarchy.setSegment(rider);
                            
                            int commissionPhaseID;
                        	if (rider.getCommissionPhaseOverride() != null && !rider.getCommissionPhaseOverride().trim().equals("")) {
                        		commissionPhaseID = Integer.parseInt(rider.getCommissionPhaseOverride());
                        	} else {
                        		commissionPhaseID = rider.getCommissionPhaseID();
                        	}
                        	
                        	agentHierarchy.setCommissionPhaseID(commissionPhaseID);

                            AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = (AgentHierarchyAllocationVO[]) agentHierarchyVOs[a].getAgentHierarchyAllocationVO();
                            for (int j = 0; j < agentHierarchyAllocationVOs.length; j++)
                            {
                                AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) SessionHelper.map(agentHierarchyAllocationVOs[j], DATABASE);
                                agentHierarchy.addAgentHierarchyAllocation(agentHierarchyAllocation);
                            }

                            AgentSnapshotVO[] agentSnapshotVOs = (AgentSnapshotVO[]) agentHierarchyVOs[a].getAgentSnapshotVO();
                            for (int j = 0; j < agentSnapshotVOs.length; j++)
                            {
                                AgentSnapshot agentSnapshot = (AgentSnapshot) SessionHelper.map(agentSnapshotVOs[j], DATABASE);

                                PlacedAgent placedAgent = PlacedAgent.findByPK(new Long(agentSnapshotVOs[j].getPlacedAgentFK()));
                                agentSnapshot.setPlacedAgent(placedAgent);
                                agentHierarchy.addAgentSnapshot(agentSnapshot);
                            }

                            Agent agent = Agent.findBy_PK(new Long(agentHierarchyVOs[a].getAgentFK()));
                            agentHierarchy.setAgent(agent);

                            agentHierarchy.hSave();
                        }
                    }
                	
                    if (riderVOs[i].getVoShouldBeDeleted())
                    {
                        Segment riderSegment = (Segment) SessionHelper.map(riderVOs[i], DATABASE);
                        this.removeRiderSegment(riderSegment);
                        riderSegment.hDelete();
                    }
                    else
                    {
                        riderVOs[i].setSegmentFK(segmentVO.getSegmentPK());

                        Segment riderSegment = mapRiderSegmentAndChildren(riderVOs[i]);
                        riderSegment.setOperator(this.getOperator());
                        riderSegment.setSegment(this);

                        if (riderSegment.getSegmentNameCT().equalsIgnoreCase("Life"))
                        {
                            ComplexChange complexChange = new ComplexChange();
                            //get the contractclients from the segmentVO
//                            get_ContractClients();
                            complexChange.checkForFaceIncrease(riderSegment, (ContractClient[]) contractClients.toArray(new ContractClient[contractClients.size()]));
                        }

                        //CONVERT TO HIBERNATE - 09/13/07
                        riderSegment.hSave();
                    }


//                    crudEntityImpl.save(riderSegment, ConnectionFactory.EDITSOLUTIONS_POOL, false);
                    //currently riders have no children
//                    saveChildrenOfBaseAndRiders(riderVOs[i]);
                }
            }
            
                // Check for defaults to GroupPlan before the saving of the Base and its Riders.

                defaultGroupRatedGenUnderwriting("CASEBASE", product);
                
			for (Segment riderSegment : getSegments()) {
                    riderSegment.defaultGroupRatedGenUnderwriting("CASERIDERS", product);
                }

            SessionHelper.commitTransaction(Segment.DATABASE);
		} catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
	 * As of 03-23-2006 hibernate and entities are not fully implemented. For
	 * complex change processing the contract client are still in the segmentVo and
	 * not the Segment entity.
     */
	private void get_ContractClients() {
        contractClients = new HashSet();
        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

		for (int i = 0; i < contractClientVOs.length; i++) {
            ContractClient contractClient = new ContractClient(contractClientVOs[i]);
            contractClients.add(contractClient);
        }
    }
    
	private Segment mapRiderSegmentAndChildren(SegmentVO riderSegmentVO) {
        Segment riderSegment = (Segment) SessionHelper.map(riderSegmentVO, DATABASE);
        
		if (riderSegmentVO.getLifeVOCount() > 0) {
            LifeVO[] lifeVOs = riderSegmentVO.getLifeVO();
            
			for (int i = 0; i < lifeVOs.length; i++) {
                Life life = (Life) SessionHelper.map(lifeVOs[i], Segment.DATABASE);
                
                riderSegment.addLife(life);
            }
        }

		if (riderSegmentVO.getContractClientVOCount() > 0) {
            ContractClientVO[] contractClientVOs = riderSegmentVO.getContractClientVO();

			for (int i = 0; i < contractClientVOs.length; i++) {
                contractClientVOs[i].setSegmentFK(riderSegmentVO.getSegmentPK());
                ClientRole clientRole = ClientRole.findByPK(contractClientVOs[i].getClientRoleFK());

				ContractClient contractClient = (ContractClient) SessionHelper.map(contractClientVOs[i],
						Segment.DATABASE);
                contractClient.setClientRole(clientRole);
                
				ContractClientAllocationVO[] contractClientAllocationVOs = (ContractClientAllocationVO[]) segmentVO
						.getContractClientVO()[i].getContractClientAllocationVO();
				for (int j = 0; j < contractClientAllocationVOs.length; j++) {
					ContractClientAllocation contractClientAllocation = (ContractClientAllocation) SessionHelper
							.map(contractClientAllocationVOs[j], DATABASE);
                    contractClient.addContractClientAllocation(contractClientAllocation);
                }

                riderSegment.addContractClient(contractClient);
            }
        }

		if (riderSegmentVO.getValueAtIssueVOCount() > 0) {
            ValueAtIssueVO[] valueAtIssueVOs = riderSegmentVO.getValueAtIssueVO();
			for (int i = 0; i < valueAtIssueVOs.length; i++) {
                ValueAtIssue valueAtIssue = (ValueAtIssue)SessionHelper.map(valueAtIssueVOs[i], Segment.DATABASE);
                riderSegment.addValueAtIssue(valueAtIssue);
            }
        }

        return riderSegment;
    }


    /**
	 * Verifies if policy delivery date should be updated? At this point of time,
	 * potentially there will be only one Requirement that would be marked to update
	 * the policy delivery date. Policy delivery date will be updated with
	 * ContractRequirment.ExecutedDate.
     */
	public void verifyAndUpdatePolicyDeliveryDate() {
        ContractRequirementVO[] contractRequirementVOs = segmentVO.getContractRequirementVO();

		for (int i = 0; i < contractRequirementVOs.length; i++) {
            ContractRequirementVO contractRequirementVO = contractRequirementVOs[i];

			FilteredRequirementVO filteredRequirementVO = new FilteredRequirementDAO()
					.findByPK(contractRequirementVO.getFilteredRequirementFK(), false, null)[0];

			RequirementVO requirementVO = new RequirementDAO().findByPK(filteredRequirementVO.getRequirementFK(), false,
					null)[0];

			// At this point of time there would be only one requirement that would have
			// UpdatePolicyDeliveryDate set to yes.
			// In most cases, the requirement would be added after the policy has become
			// active.
			if ("Y".equals(requirementVO.getUpdatePolicyDeliveryDateInd())) {
                segmentVO.setPolicyDeliveryDate(contractRequirementVO.getExecutedDate());
                break;
            }
        }
    }

    /**
	 * Recursive save of the children of the segment The segment entity is fully
	 * populated by hibernate with what currently exists on the data base
	 * 
     * @param segmentVO
     */
	private void saveChildrenOfBaseAndRiders(SegmentVO segmentVO) {
        long segmentPK = segmentVO.getSegmentPK();

		try {
			if (segmentVO.getPayoutVOCount() > 0) {
                segmentVO.getPayoutVO(0).setSegmentFK(segmentPK);
                Payout payout = (Payout) SessionHelper.map(segmentVO.getPayoutVO(0), DATABASE);
                payout.setSegment(this);
                payout.save();
            }

			if (segmentVO.getLifeVOCount() > 0) {
                segmentVO.getLifeVO(0).setSegmentFK(segmentPK);
                Life life = (Life) SessionHelper.map(segmentVO.getLifeVO(0), DATABASE);
                life.setSegment(this);
                life.save();
            }

			if (segmentVO.getInherentRiderVOCount() > 0) {
                segmentVO.getInherentRiderVO(0).setSegmentFK(segmentPK);
				InherentRider inherentRider = (InherentRider) SessionHelper.map(segmentVO.getInherentRiderVO(0),
						DATABASE);
                inherentRider.setSegment(this);

                //CONVERT TO HIBERNATE - 09/13/07
                inherentRider.hSave();
            }

			if (segmentVO.getContractClientVOCount() > 0) {
                ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();
				for (int i = 0; i < contractClientVOs.length; i++) {
                    contractClientVOs[i].setSegmentFK(segmentPK);

					ContractClient contractClient = (ContractClient) SessionHelper
							.map(segmentVO.getContractClientVO()[i], DATABASE);

                    contractClient.setSegment(this);

					ContractClientAllocationVO[] contractClientAllocationVOs = (ContractClientAllocationVO[]) segmentVO
							.getContractClientVO()[i].getContractClientAllocationVO();
					for (int j = 0; j < contractClientAllocationVOs.length; j++) {
						ContractClientAllocation contractClientAllocation = (ContractClientAllocation) SessionHelper
								.map(contractClientAllocationVOs[j], DATABASE);
                        contractClient.addContractClientAllocation(contractClientAllocation);
                    }

                    WithholdingVO[] withholdingVOs = segmentVO.getContractClientVO()[i].getWithholdingVO();
					for (int k = 0; k < withholdingVOs.length; k++) {
                        Withholding withholding = (Withholding) SessionHelper.map(withholdingVOs[k], DATABASE);
                        contractClient.addWithholding(withholding);
                    }

                    contractClient.saveForHibernate();
                }
            }

			if (segmentVO.getInvestmentVOCount() > 0) {
                InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
				for (int i = 0; i < investmentVOs.length; i++) {
                    investmentVOs[i].setSegmentFK(segmentPK);

                    Investment investment = (Investment) SessionHelper.map(investmentVOs[i], DATABASE);
                    investment.setSegment(this);

                    InvestmentAllocationVO[] investmentAllocationVOs = investmentVOs[i].getInvestmentAllocationVO();
					for (int j = 0; j < investmentAllocationVOs.length; j++) {
						InvestmentAllocation investmentAllocation = (InvestmentAllocation) SessionHelper
								.map(investmentAllocationVOs[j], DATABASE);
                        investment.addInvestmentAllocation(investmentAllocation);
                    }

                    investment.saveForHibernate();
                }
            }

			if (segmentVO.getDepositsVOCount() > 0) {
                DepositsVO[] depositsVOs = segmentVO.getDepositsVO();

				for (int i = 0; i < depositsVOs.length; i++) {
                    depositsVOs[i].setSegmentFK(segmentPK);

                    Deposits deposits = (Deposits) SessionHelper.map(depositsVOs[i], DATABASE);

                    //same is true for Suspense, edittrx, and CashBatchContract
                    Long suspensePK = depositsVOs[i].getSuspenseFK();
					if (suspensePK != null && suspensePK != 0) {
                        Suspense suspense = Suspense.findByPK(suspensePK);
                        deposits.setSuspense(suspense);
                    }

                    Long editTrxFK = depositsVOs[i].getEDITTrxFK();
					if (editTrxFK != null && editTrxFK != 0) {
                        EDITTrx editTrx = EDITTrx.findBy_PK(editTrxFK);
                        deposits.setEDITTrx(editTrx);
                    }
                    Long cashBatchContractPK = depositsVOs[i].getCashBatchContractFK();
					if (cashBatchContractPK != null && cashBatchContractPK != 0) {
                        CashBatchContract cashBatchContract = CashBatchContract.findByPK(cashBatchContractPK);
                        deposits.setCashBatchContract(cashBatchContract);
                    }

                    deposits.setSegment(this);

                    //CONVERT TO HIBERNATE - 09/13/07
                    deposits.hSave();
//                    deposits.save();
                }
            }

			if (segmentVO.getAgentHierarchyVOCount() > 0) {
                AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
				for (int i = 0; i < agentHierarchyVOs.length; i++) {
                    agentHierarchyVOs[i].setSegmentFK(segmentPK);

					AgentHierarchy agentHierarchy = (AgentHierarchy) SessionHelper
							.mapVOToHibernateEntity(agentHierarchyVOs[i], DATABASE);
                    agentHierarchy.setSegment(this);

					AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = (AgentHierarchyAllocationVO[]) agentHierarchyVOs[i]
							.getAgentHierarchyAllocationVO();
					for (int j = 0; j < agentHierarchyAllocationVOs.length; j++) {
						AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) SessionHelper
								.map(agentHierarchyAllocationVOs[j], DATABASE);
                        agentHierarchy.addAgentHierarchyAllocation(agentHierarchyAllocation);
                    }

                    AgentSnapshotVO[] agentSnapshotVOs = (AgentSnapshotVO[]) agentHierarchyVOs[i].getAgentSnapshotVO();
					for (int j = 0; j < agentSnapshotVOs.length; j++) {
                        AgentSnapshot agentSnapshot = (AgentSnapshot) SessionHelper.map(agentSnapshotVOs[j], DATABASE);

						PlacedAgent placedAgent = PlacedAgent
								.findByPK(new Long(agentSnapshotVOs[j].getPlacedAgentFK()));
                        agentSnapshot.setPlacedAgent(placedAgent);
                        agentHierarchy.addAgentSnapshot(agentSnapshot);
                    }

                    Agent agent = Agent.findBy_PK(new Long(agentHierarchyVOs[i].getAgentFK()));
                    agentHierarchy.setAgent(agent);

                    agentHierarchy.hSave();
                }
            }

			if (segmentVO.getContractRequirementVOCount() > 0) {
                ContractRequirementVO[] contractRequirementVOs = segmentVO.getContractRequirementVO();

				for (int i = 0; i < contractRequirementVOs.length; i++) {
                    contractRequirementVOs[i].setSegmentFK(segmentPK);

					ContractRequirement contractRequirement = (ContractRequirement) SessionHelper
							.map(contractRequirementVOs[i], DATABASE);
                    
                    // check for deleted requirements 
                    if (contractRequirementVOs[i].getVoShouldBeDeleted()) {
                        this.removeContractRequirement(contractRequirement);
                        contractRequirement.hDelete();
                    } else {

                        // Verify if Segment PolicyDeliveryDate should be updated or not.
                        // There might be special requirement that would update policy delivery date.
						// At this point of time there would be only one requirement that would segment
						// policy delivery date.
                        if (contractRequirement.isUpdatePolicyDeliveryDate()) {
                            segmentVO.setPolicyDeliveryDate(contractRequirement.getExecutedDate().getFormattedDate());
                            // child is updating the parent field, save the parent.
                            //CONVERT TO HIBERNATE - 09/13/07
//                        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
                            updateCommissionHistoryHoldReleaseDate();
                        }
                        contractRequirement.setSegment(this);
						FilteredRequirement filteredRequirement = FilteredRequirement
								.findByPK(contractRequirementVOs[i].getFilteredRequirementFK());
                        contractRequirement.setFilteredRequirement(filteredRequirement);

                        contractRequirement.saveForHibernate();
                    }
                    
                }
            }

			if (segmentVO.getNoteReminderVOCount() > 0) {
                NoteReminderVO[] noteReminderVOs = segmentVO.getNoteReminderVO();

				for (int i = 0; i < noteReminderVOs.length; i++) {
                    noteReminderVOs[i].setSegmentFK(segmentPK);

                    NoteReminder noteReminder = (NoteReminder) SessionHelper.map(noteReminderVOs[i], DATABASE);
                    noteReminder.setSegment(this);

                    //CONVERT TO HIBERNATE - 09/13/07
                    noteReminder.hSave();
                }
            }

			if (segmentVO.getRequiredMinDistributionVOCount() > 0) {
                RequiredMinDistributionVO requiredMinDistributionVO = segmentVO.getRequiredMinDistributionVO(0);

                requiredMinDistributionVO.setSegmentFK(segmentPK);

				RequiredMinDistribution requiredMinDistribution = (RequiredMinDistribution) SessionHelper
						.map(requiredMinDistributionVO, DATABASE);
                requiredMinDistribution.setSegment(this);
                //CONVERT TO HIBERNATE - 09/13/07
                requiredMinDistribution.hSave();
            }
		} catch (EDITContractException e) {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
		} catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
	 * Compare to existing data base table, using the ChangeProcessor. For the
	 * ChangeVOs returned, generate ChangeHistory.
     */
	public void checkForNonFinancialChanges() {
        ChangeProcessor changeProcessor = new ChangeProcessor();
		Change[] changes = changeProcessor.checkForChanges(segmentVO, segmentVO.getVoShouldBeDeleted(),
				ConnectionFactory.EDITSOLUTIONS_POOL, null);

		if (changes != null) {
			for (int i = 0; i < changes.length; i++) {
                //DeathPending status change might be setting this date
				if (changeHistoryEffDate != null) {
                    changes[i].setEffectiveDate(changeHistoryEffDate.getFormattedDate());
                }

                changeProcessor.processForChanges(changes[i], this, this.operator, this.getPK());
            }
        }
    }

	public void saveNonRecursively() {
        contract.dm.StorageManager sm = new contract.dm.StorageManager();

        //check for Segment changes to generate NF history, only when requested
		if (saveChangeHistory) {
            checkForNonFinancialChanges();
        }

        sm.saveSegmentNonRecursively(this, this.saveChangeHistory, this.operator, false);
    }

    /**
     * Setter for masterFK
	 * 
     * @param masterFK
     */
//    public void setMasterFK(Long masterFK)
//    {
//        this.segmentVO.setMasterFK(SessionHelper.getPKValue(masterFK));
//    }

//    public void setMaster(Master master)
//    {
//        this.master = master;
//
//        this.setMasterFK(master.getMasterPK());
//    }

    /**
     * Setter
	 * 
     * @param billScheduleFK
     */
	public void setBillScheduleFK(Long billScheduleFK) {
        this.segmentVO.setBillScheduleFK(SessionHelper.getPKValue(billScheduleFK));
    }

    /**
     * Setter
	 * 
     * @param batchContractSetupFK
     */
	public void setBatchContractSetupFK(Long batchContractSetupFK) {
        this.segmentVO.setBatchContractSetupFK(SessionHelper.getPKValue(batchContractSetupFK));
    }

    /**
     * Setter
	 * 
     * @param contractGroupFK
     */
	public void setContractGroupFK(Long contractGroupFK) {
        this.segmentVO.setContractGroupFK(SessionHelper.getPKValue(contractGroupFK));
    }

    /**
     * @see #masterContractFK
     * @param masterContractFK
     */
	public void setMasterContractFK(Long masterContractFK) {
        this.masterContractFK = masterContractFK;
    }

    /**
     * Setter
	 * 
     * @param MasterContract
     */
	public void setMasterContract(MasterContract masterContract) {
        this.masterContract = masterContract;

		if (masterContract != null) {
            this.setMasterContractFK(masterContract.getMasterContractPK());
        }
    }

    /**
     * Setter
	 * 
     * @param contractGroupFK
     */
	public void setDepartmentLocationFK(Long departmentLocationFK) {
        this.segmentVO.setDepartmentLocationFK(SessionHelper.getPKValue(departmentLocationFK));
    }

    /**
     * Setter
	 * 
     * @param priorContractGroupFK
     */
	public void setPriorContractGroupFK(Long priorContractGroupFK) {
        this.segmentVO.setPriorContractGroupFK(SessionHelper.getPKValue(priorContractGroupFK));
    }

   
   /**
     * Setter
	 * 
     * @param originalContractGroupFK
     */
	public void setOriginalContractGroupFK(Long originalContractGroupFK) {
        this.segmentVO.setOriginalContractGroupFK(SessionHelper.getPKValue(originalContractGroupFK));
    }

    /**
     * Setter
	 * 
     * @param scheduledTerminationDate
     */
	public void setScheduledTerminationDate(EDITDate scheduledTerminationDate) {
		segmentVO.setScheduledTerminationDate(
				(scheduledTerminationDate != null) ? scheduledTerminationDate.getFormattedDate() : null);
    } //-- void setScheduledTerminationDate(java.lang.String)

	public void setBillSchedule(BillSchedule billSchedule) {
        this.billSchedule = billSchedule;

		if (billSchedule != null) {
            this.setBillScheduleFK(billSchedule.getBillSchedulePK());
        }
    }

	public void setBatchContractSetup(BatchContractSetup batchContractSetup) {
        this.batchContractSetup = batchContractSetup;

		if (batchContractSetup != null) {
            this.setBatchContractSetupFK(batchContractSetup.getBatchContractSetupPK());
        }
    }

	public void setContractGroup(ContractGroup contractGroup) {
        this.contractGroup = contractGroup;

		if (contractGroup != null) {
            this.setContractGroupFK(contractGroup.getContractGroupPK());
        }
    }

	public void setDepartmentLocation(DepartmentLocation departmentLocation) {
        this.departmentLocation = departmentLocation;

		if (departmentLocation != null) {
            this.setDepartmentLocationFK(departmentLocation.getDepartmentLocationPK());
        }
    }

	public void setPriorContractGroup(ContractGroup priorContractGroup) {
        this.priorContractGroup = priorContractGroup;

		if (priorContractGroup != null) {
            this.setPriorContractGroupFK(priorContractGroup.getContractGroupPK());
        }
    }

	public void setOriginalContractGroup(ContractGroup originalContractGroup) {
        this.originalContractGroup = originalContractGroup;

		if (originalContractGroup != null) {
            this.setOriginalContractGroupFK(originalContractGroup.getContractGroupPK());
        }
    }

    /**
     * Sets the parent (base) Segment
	 * 
     * @param segment
     */
	public void setSegment(Segment segment) {
        this.segment = segment;

		if (segment != null) {
            this.setSegmentFK(segment.getSegmentPK());
        }
    }

    /**
     * Setter for operator
	 * 
     * @param operator
     */
	public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Getter for operator
	 * 
     * @return
     */
	public String getOperator() {
        return this.operator;
    }

    /**
     * Setter for ChangeHistory
	 * 
     * @param saveChangeHistory
     */
	public void setSaveChangeHistory(boolean saveChangeHistory) {
        this.saveChangeHistory = saveChangeHistory;
    }

    /**
     * Getter for ChangeHistory boolean
     */
	public boolean getSaveChangeHistory() {
        return this.saveChangeHistory;
    }

    /**
     * Setter for the SegmentStatusCT field.
	 * 
     * @param segmentStatusCT
     */
	public void setSegmentStatusCT(String segmentStatusCT) {
        segmentVO.setSegmentStatusCT(segmentStatusCT);
    }

    //-- void setSegmentStatusCT(java.lang.String)

    /**
     * @param voObject
     */
	public void setVO(VOObject voObject) {
        this.segmentVO = (SegmentVO) voObject;
    }

    /**
     * creates free look transaction
     */
	public void createFreeLookEDITTrx() {
        String transactionType = "FT";

        EDITDate effectiveDate = this.getFreeLookEndDate();

        int taxYear = effectiveDate.getYear();

        String operator = "System";

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);

        editTrx.createFreeLookTransactionGroupSetup(this);
    }

    /**
     * verifies whether it is required to create freelook transaction or not
	 * 
     * @return
     */
	public boolean createFreeLookTransaction() {
        String policyDeliveryDate = this.segmentVO.getPolicyDeliveryDate();

		if (policyDeliveryDate != null) {
            String freeLookEndDate = calculateFreeLookEndDate();

            this.segmentVO.setFreeLookEndDate(freeLookEndDate);
        }

        String waiveFreeLookIndicator = this.segmentVO.getWaiveFreeLookIndicator();
        boolean createFreeLookTransaction = false;

		// create FT transaction when policy delivery date has been entered and did not
		// waive the freelook indicator
		if ((waiveFreeLookIndicator == null) || waiveFreeLookIndicator.equalsIgnoreCase("N")) {
			if (policyDeliveryDate != null) {
                long segmentPK = this.segmentVO.getSegmentPK();

                // if contract is newone
				if (segmentPK == 0) {
                    createFreeLookTransaction = true;
                }

                // get the previous delivery date entered
				else {
                    Segment segment = Segment.findByPK(new Long(segmentPK));
                    EDITDate previousPolicyDeliveryDate = segment.getPolicyDeliveryDate();

					if (previousPolicyDeliveryDate == null) {
                        createFreeLookTransaction = true;
					} else {
//                        if (new EDITDate(previousPolicyDeliveryDate).ifNE(new EDITDate(policyDeliveryDate)))
						if (!previousPolicyDeliveryDate.equals(new EDITDate(policyDeliveryDate))) {
                            createFreeLookTransaction = true;
                        }
                    }
                }
            }
        }

        return createFreeLookTransaction;
    }

    /**
     * Creates the RMD Notification (RC) Transaction
     */
	public void createRmdNotificationTransaction() {
        String transactionType = "RC";

        EDITDate effectiveDate = new EDITDate();

        int taxYear = effectiveDate.getYear();

        String operator = "System";

//        ContractEvent contractEvent = new ContractEvent();

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);
        editTrx.setNoAccountingInd("Y");

//        EDITTrxVO editTrxVO = contractEvent.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);
        editTrx.setNoAccountingInd("Y");

        editTrx.createRmdNotificationTransactionGroupSetup(this);
    }

    /**
	 * The database values for the material changes are needed to determine if
	 * changes have been done. The segmentVO set already contains the changes.
	 * 
     * @return   SegmentVO
     */
	public SegmentVO getExistingSegment() {
        List voInclusionList = new ArrayList();

        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(LifeVO.class);

        SegmentVO existingSegmentVO = null;

		try {
            existingSegmentVO = new VOComposer().composeSegmentVO(segmentVO.getSegmentPK(), voInclusionList);
		} catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return existingSegmentVO;
    }

	private int getFreeLookDays() {
        long productStructurePK = this.segmentVO.getProductStructureFK();
        String areaCT = this.segmentVO.getIssueStateCT();
        String qualifierCT = "*";
        String grouping = "FREELOOKPROCESS";

        EDITDate effectiveDate = new EDITDate(this.segmentVO.getEffectiveDate());
        String field = "FREELOOKDAYS";
        int freeLookDays = 0;

        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue = area.getAreaValue(field);

        AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

        String numberOfFreeLookDays = areaValueVO.getAreaValue();

        freeLookDays = Integer.parseInt(numberOfFreeLookDays);

        return freeLookDays;
    }

    /**
     * calculates freelook end date same freelook transaction effective date
	 * 
     * @return
     */
	private String calculateFreeLookEndDate() {
        String policyDeliveryDate = this.segmentVO.getPolicyDeliveryDate();
        int freeLookDays = this.segmentVO.getFreeLookDaysOverride();
        String freeLookEndDate = null;

        // when freelook days are not entered get freelook days from AreaValue table
		if (freeLookDays == 0) {
            freeLookDays = getFreeLookDays();
        }

        freeLookEndDate = new EDITDate(policyDeliveryDate).addDays(freeLookDays).getFormattedDate();

        return freeLookEndDate;
    }

    /**
     * Setter.
	 * 
     * @param editDate
     */
	public void setFreeLookEndDate(EDITDate editDate) {
        segmentVO.setFreeLookEndDate((editDate != null) ? editDate.getFormattedDate() : null);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITDate getFreeLookEndDate() {
        return ((segmentVO.getFreeLookEndDate() != null) ? new EDITDate(segmentVO.getFreeLookEndDate()) : null);
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
	private final void init() {
		if (segmentVO == null) {
            segmentVO = new SegmentVO();
        }

		if (crudEntityImpl == null) {
            crudEntityImpl = new CRUDEntityImpl();
        }

		if (contractSetups == null) {
          contractSetups = new HashSet<ContractSetup>();
        }

		if (contractClients == null) {
            contractClients = new HashSet();
        }

		if (investments == null) {
            investments = new HashSet<Investment>();
        }

		if (payouts == null) {
            payouts = new HashSet<Payout>();
        }

		if (requiredMinDistributions == null) {
            requiredMinDistributions = new HashSet<RequiredMinDistribution>();
    }

		if (agentHierarchies == null) {
            agentHierarchies = new HashSet<AgentHierarchy>();
        }

		if (lifes == null) {
            lifes = new HashSet<Life>();
        }

		if (inherentRiders == null) {
            inherentRiders = new HashSet();
        }

		if (contractTreaties == null) {
            contractTreaties = new HashSet();
        }

		if (bills == null) {
            bills = new HashSet<Bill>();
        }

		if (premiumDues == null) {
            premiumDues = new HashSet<PremiumDue>();
        }

		if (deposits == null) {
            deposits = new HashSet<Deposits>();
        }

		if (riderSegments == null) {
            riderSegments = new HashSet();
        }

		if (valueAtIssues == null) {
            valueAtIssues = new HashSet();
        }
    }

    /**
     * Finds the Segment associated with this EDITTrx.
	 * 
     * @param editTrxPK
     * @return
     */
	public static Segment findByEditTrxPK(long editTrxPK) {
        Segment segment = null;

        SegmentVO[] segmentVOs = null;

        segmentVOs = DAOFactory.getSegmentDAO().findByEDITTrxPK(editTrxPK);

		if (segmentVOs != null) {
            segment = ((Segment[]) CRUDEntityImpl.mapVOToEntity(segmentVOs, Segment.class))[0];
        }

        return segment;
    }

    /**
     * Finder.
	 * 
     * @param partialCorporateName
     * @return
     */
	public static final Segment[] findBy_PartialCorporateName(String partialCorporateName) {
        SegmentVO[] segmentVOs = new SegmentDAO().findSegmentsBy_PartialCorporateName(partialCorporateName);

        return (Segment[]) CRUDEntityImpl.mapVOToEntity(segmentVOs, Segment.class);
    }

    /**
	 * Returns true if this Segment is a rider Segment, false otherwise (assumed
	 * base).
	 * 
     * @return
     */
	public boolean isRider() {
        boolean isRider = false;

		if (segmentVO.getSegmentFK() != 0) {
            isRider = true;
        }

        return isRider;
    }

    /**
	 * Returns true if this Segment is a base Segment, true otherwise (assumed
	 * rider).
	 * 
     * @return
     */
	public boolean isBase() {
        boolean isBase = false;

		if (segmentVO.getSegmentFK() == 0) {
            isBase = true;
        }

        return isBase;
    }

    /**
     * Returns the Master of this base or rider Segment.
	 * 
     * @return
     */
//    public Master getMaster()
//    {
//        Master master = null;
//
//        if (isRider())
//        {
//            master = getBase().getMaster();
//        }
//        else
//        {
//            master = Master.findByPKPolymorphically(segmentVO.getMasterFK());
//        }
//
//        return master;
//    }

	public BillSchedule getBillSchedule() {
        return this.billSchedule;
    }

	public BatchContractSetup getBatchContractSetup() {
        return this.batchContractSetup;
    }

	public ContractGroup getContractGroup() {
        return this.contractGroup;
    }

	public DepartmentLocation getDepartmentLocation() {
        return this.departmentLocation;
    }

	public ContractGroup getPriorContractGroup() {
        return this.priorContractGroup;
    }

	public ContractGroup getOriginalContractGroup() {
        return this.originalContractGroup;
    }

    /**
     * Returns the parent (base) segment
	 * 
     * @return
     */
	public Segment getSegment() {
        return this.segment;
    }

    /**
	 * Returns the proper Contract Treaty based a set of override rules. 1) If this
	 * Segment is a rider, and it does not have its own Contract Treaty, it is to
	 * use the Contract Treaty of its base Segment. 2) If the base Segment does not
	 * have its own Contract Treaty, then it is to use the Contract Treaty of its
	 * Case (if it belongs to one). 3) If the Case does not have Contract Treaty,
	 * then there is none.
	 * 
     * @return
     */
//    public ContractTreaty getContractTreaty(Treaty treaty)
//    {
//        ContractTreaty contractTreaty = null;
//        long contractGroupPK = 0;
//
//        if (this.getContractGroup() != null)
//        {
//            contractGroupPK = this.getContractGroup().getContractGroupFK().longValue();
//        }
//
//        if (isRider())
//        {
//            contractTreaty = ContractTreaty.findBy_CasePK_SegmentPK_TreatyPK(contractGroupPK, getPK(), treaty.getPK());
//        }
//
//        if (!isRider() && (contractTreaty == null)) // Not found at the rider level, try base level.
//        {
//            Segment base = getBase();
//
//            contractTreaty = ContractTreaty.findBy_CasePK_SegmentPK_TreatyPK(contractGroupPK, base.getPK(), treaty.getPK());
//        }
//
//        if (contractTreaty == null) // Still didn't find it, try at the Case level (if this Segment belongs to a Case)
//        {
//            if (this.belongsToACase())
//            {
//                contractTreaty = ContractTreaty.findBy_CasePK_SegmentPK_IS_NULL_TreatyPK(contractGroupPK, treaty.getPK());
//            }
//        }
//
//        return contractTreaty;
//    }

    /**
	 * Finds all ContractTreaties directly mapped to this Segment. In the future,
	 * indirect mappings (via PolicyGroup) will likely also be included.
	 * 
     * @return
     */
	public ContractTreaty[] getContractTreaty() {
        ContractTreaty[] contractTreaties = ContractTreaty.findBy_SegmentPK(getPK());

        return contractTreaties;
    }

        /**
     * Finder.
	 * 
     * @param reinsuranceHistoryPK
     * @return
     */
	public static final Segment findBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK) {
        Segment segment = null;

        SegmentVO[] segmentVOs = new SegmentDAO().findBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

		if (segmentVOs != null) {
            segment = new Segment(segmentVOs[0]);
        }

        return segment;
    }

    /**
	 * Generate an RMD (RW) Transaction or update the existing RMD (RW) Transaction
	 * if it already exists using the RMDVO information provided
	 * 
     * @param rmdVO
     * @throws Exception
     */
	public void generateOrUpdateRmdTrx(RequiredMinDistributionVO rmdVO) throws Exception {
        EDITBigDecimal modalOvrdAmt = new EDITBigDecimal(rmdVO.getModalOverrideAmount());
        String rmdFrequency = rmdVO.getFrequencyCT();

        EDITDate currentDate = new EDITDate();
        int currentYear = currentDate.getYear();

        EDITDate effectiveDate = null;
        int taxYear = 0;
        EDITBigDecimal trxAmount = new EDITBigDecimal();

        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(ScheduledEventVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(ChargeVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(InvestmentAllocationOverrideVO.class);
        voInclusionList.add(ContractClientAllocationOvrdVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(WithholdingOverrideVO.class);
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(CommissionHistoryVO.class);
        voInclusionList.add(InvestmentHistoryVO.class);
        voInclusionList.add(WithholdingHistoryVO.class);
        voInclusionList.add(CommissionInvestmentHistoryVO.class);
        voInclusionList.add(ReinsuranceHistoryVO.class);

		GroupSetupVO[] rwGroupSetupVO = eventComponent.findRMDTransaction(this.segmentVO.getSegmentPK(), "RW",
				voInclusionList);

		if ((rwGroupSetupVO != null) && (rwGroupSetupVO.length > 0)) {
            effectiveDate = new EDITDate(rmdVO.getRMDStartDate());

			for (int i = 0; i < rwGroupSetupVO.length; i++) {
                ScheduledEventVO schedEventVO = rwGroupSetupVO[i].getScheduledEventVO(0);

				if (!schedEventVO.getStartDate().equals(schedEventVO.getStopDate())) {
                    EDITDate edEffDate = effectiveDate;

                    ContractSetupVO[] contractSetupVOs = rwGroupSetupVO[i].getContractSetupVO();

					for (int j = 0; j < contractSetupVOs.length; j++) {
                        ClientSetupVO[] clientSetupVOs = contractSetupVOs[j].getClientSetupVO();

						for (int k = 0; k < clientSetupVOs.length; k++) {
                            EDITTrxVO[] editTrxVOs = clientSetupVOs[k].getEDITTrxVO();

							for (int l = 0; l < editTrxVOs.length; l++) {
								if (editTrxVOs[l].getPendingStatus().equalsIgnoreCase("P")) {
									if (effectiveDate != null) {
                                        EDITDate trxEffDate = new EDITDate(editTrxVOs[l].getEffectiveDate());
										if (edEffDate.after(trxEffDate) || edEffDate.equals(trxEffDate)) {
                                            editTrxVOs[l].setEffectiveDate(effectiveDate.getFormattedDate());
                                            editTrxVOs[l].setDueDate(effectiveDate.getFormattedDate());
                                        }
                                    }

                                    editTrxVOs[l].setTrxAmount(modalOvrdAmt.getBigDecimal());
                                }
                            }
                        }
                    }

                    schedEventVO.setFrequencyCT(rmdFrequency);

                    GroupSetup groupSetup = new GroupSetup(rwGroupSetupVO[i]);
                    groupSetup.save();
                }
            }
		} else {
            String transactionType = "RW";
            String operator = "System";

//            ContractEvent contractEvent = new ContractEvent();

            boolean oneTimeTrx = false;

			if (rmdVO.getElectionCT().equalsIgnoreCase("Opt3")) {
				if (modalOvrdAmt.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)) {
                    trxAmount = modalOvrdAmt;
                }

                effectiveDate = new EDITDate(rmdVO.getRMDStartDate());
                taxYear = effectiveDate.getYear();
			} else if (rmdVO.getElectionCT().equalsIgnoreCase("Opt4")) {
                EDITDate nextYear = currentDate.addYears(1);
                taxYear = currentYear;

                effectiveDate = new EDITDate(nextYear.getYear(), 3, 31);

                trxAmount = new EDITBigDecimal(rmdVO.getInitialRMDAmount());

                EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);
//                EDITTrxVO editTrxVO = contractEvent.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);
                editTrx.setTrxAmount(trxAmount);
                editTrx.setDueDate(effectiveDate);

                oneTimeTrx = true;
                editTrx.createRWTransactionGroupSetup(this, oneTimeTrx, rmdFrequency, rmdVO.getElectionCT());

                oneTimeTrx = false;

				if (modalOvrdAmt.isGT("0")) {
                    trxAmount = modalOvrdAmt;
				} else {
                    trxAmount = new EDITBigDecimal();
                }

                effectiveDate = new EDITDate(nextYear.getYear(), 1, nextYear.getDay()).getEndOfModeDate(rmdFrequency);
                taxYear = nextYear.getYear();
            }

            EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);
//            EDITTrxVO editTrxVO = contractEvent.buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);

            editTrx.setDueDate(effectiveDate);
            editTrx.setTrxAmount(trxAmount);

            editTrx.createRWTransactionGroupSetup(this, oneTimeTrx, rmdFrequency, rmdVO.getElectionCT());
        }
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITDate getCreationDate() {
        return ((segmentVO.getCreationDate() != null) ? new EDITDate(segmentVO.getCreationDate()) : null);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getIssueStateCT() {
        return segmentVO.getIssueStateCT();
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getIssueStateORInd() {
        return segmentVO.getIssueStateORInd();
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getOriginalStateCT() {
		// segment does not have an originalStateCT until the contract is issued. Must
		// use IssueStateCT before the issue.
    	//if (segmentVO.getOriginalStateCT() == null) {
    	//	return segmentVO.getIssueStateCT();
    	//}
        return segmentVO.getOriginalStateCT();
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getSegmentStatusCT() {
        return segmentVO.getSegmentStatusCT();
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITDate getIssueDate() {
        return ((segmentVO.getIssueDate() != null) ? new EDITDate(segmentVO.getIssueDate()) : null);
    }

    //-- java.lang.String getIssueDate()

    /**
     * Returns the active "owner" of the policy as a ContractClient.
	 * 
     * @return
     */
	public ContractClient getOwnerContractClient() {
        return ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this, ClientRole.ROLETYPECT_OWNER);
    }

    /**
	 * This method is temporary and for 5498 tax extract purpose only. The way we
	 * check for the current owner getOwner() is not correct. We should be changing
	 * this at later point of time. For now we use this method to get current owner
	 * checking against termination date. We should check by TerminationDate =
	 * '12/31/9999' instead of max(EffectiveDate)
	 * 
     * @return owner ContractClient.
     */
	public ContractClient getCurrentOwnerContractClient() {
		return ContractClient.findBy_SegmentPK_RoleTypeCT_TerminationDateGTE(this, ClientRole.ROLETYPECT_OWNER,
                                                                          new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITBigDecimal getAnnualInsuranceAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getAnnualInsuranceAmount());
    }

    //-- java.math.BigDecimal getAnnualInsuranceAmount()

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITBigDecimal getFees() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getFees());
    }

    //-- java.math.BigDecimal getFees()

    /**
     * Returns the associated Life Segment, if any.
	 * 
     * @return
     */
	public Segment getLifeSegment() {
        Segment lifeSegment = null;

        SegmentVO[] segmentVOs = new SegmentDAO().findLifeSegmentBy_SegmentPK(getPK());

		if (segmentVOs != null) {
            lifeSegment = new Segment(segmentVOs[0]);
        }

        return lifeSegment;
    }

    /**
     * CRUD. Returns the associated Life entity, if any.
	 * 
     * @return
     */
	public Life get_Life() {
        return Life.findBy_SegmentPK(getPK());
    }


        /**
     * CRUD. Returns the associated Life entity, if any.
	 * 
     * @return
     */
	public Payout get_Payout() {
        return Payout.findByPK(getPK());
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Life getLife() {
        Life life = getLifes().isEmpty()?null:(Life) getLifes().iterator().next();

        return life;
    }

    /**
     * Setter.
	 * 
     * @param life
     */
	public void setLife(Life life) {
        getLifes().add(life);

        life.setSegment(this);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Set<Life> getLifes() {
        return lifes;
    }

    /**
     * Setter.
	 * 
     * @param lifes
     */
	public void setLifes(Set<Life> lifes) {
        this.lifes = lifes;
    }

    /**
     * Removes a Life from the set of children
	 * 
     * @param life
     */
	public void removeLife(Life life) {
        this.getLifes().remove(life);

        life.setSegment(null);

        SessionHelper.saveOrUpdate(life, Segment.DATABASE);
    }

    /**
     * Finder.
	 * 
     * @param date
     * @param segmentStatusCT
     * @return
     */
	public static final Segment[] findBaseSegmentsBy_CreationDateGTE_SegmentStatusCT(String date,
			String segmentStatusCT) {
		SegmentVO[] segmentVOs = new SegmentDAO().findBy_CreationDateGTE_SegmentStatusCT_AND_SegmentFKISNULL(date,
				segmentStatusCT);

        return (Segment[]) CRUDEntityImpl.mapVOToEntity(segmentVOs, Segment.class);
    }

	public static final SegmentVO[] findBySegmentStatus(String[] segmentStatuses) {
        return new SegmentDAO().findBySegmentStatus(segmentStatuses);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public EDITDate getStatusChangeDate() {
        return SessionHelper.getEDITDate(segmentVO.getStatusChangeDate());
    }

    /**
     * True if this Segment can be classified as a Life product.
	 * 
     * @return
     */
	public boolean isLifeProduct() {
        boolean isLifeProduct = false;

		// ADD UL product - 2020-08-18 DE
		if (getSegmentNameCT().equals(Segment.SEGMENTNAMECT_LIFE) 
				|| getSegmentNameCT().equals(Segment.SEGMENTNAMECT_UL)
				|| getSegmentNameCT().equals(Segment.SEGMENTNAMECT_AH)
				|| getSegmentNameCT().equals(Segment.SEGMENTNAMECT_TRADITIONAL)) {
            isLifeProduct = true;
        }

        return isLifeProduct;
    }

    /**
     * True if this Segment can be classified as a Deferred Annuity product.
	 * 
     * @return
     */
	public boolean isDeferredAnnuityProduct() {
        boolean isDeferredAnnuityProduct = false;

		if (getSegmentNameCT().equals(Segment.SEGMENTNAMECT_DEFERREDANNUITY)) {
            isDeferredAnnuityProduct = true;
        }

        return isDeferredAnnuityProduct;
    }

    /**
     * True if this Segment can be classified as a Payout product.
	 * 
     * @return
     */
	public boolean isPayoutProduct() {
        boolean isPayoutProduct = false;

		if (getSegmentNameCT().equals(Segment.SEGMENTNAMECT_PAYOUT)) {
            isPayoutProduct = true;
        }

        return isPayoutProduct;
    }

    /**
     * Returns the active "Annuitant" of this Contract as ClientDetail.
	 * 
     * @return
     */
	public ClientDetail getAnnuitant() {
        ClientDetail annuitant = null;

        // only Derferred Anuity policies will have annuitant role.
		if (isDeferredAnnuityProduct()) {
            annuitant = getAnnuitantContractClient().getClientRole().getClientDetail();
        }

        return annuitant;
    }

    /**
     * Finder by PK.
	 * 
     * @param segmentPK
     * @return
     */
	public static final Segment findByPK(long segmentPK) {
        Segment segment = null;

        SegmentVO[] segmentVOs = new SegmentDAO().findBySegmentPK(segmentPK, false, null);

		if (segmentVOs != null) {
            segment = new Segment(segmentVOs[0]);
        }

        return segment;
    }

    /**
     * Finds a given requirement is associated to contract.
	 * 
     * @param requirementId
     * @return
     */
	public boolean isRequirementAssociated(String requirementId) {
        boolean isAssociated = false;

        Requirement requirement = Requirement.findBySegmentPK_And_RequirementId(getPK(), requirementId);

		if (requirement != null) {
            isAssociated = true;
        }

        return isAssociated;
    }

    /**
     * Associates a filtered requirement to the contract.
	 * 
     * @param requirementId
     * @return
     */

    // this is returning ContractRequirment to add it to segmentVO
    // this is used only in validateQuote because the quote is saved recursively.
	public ContractRequirement insertRequirement(String requirementId) throws EDITValidationException {
        long productStructurePK = segmentVO.getProductStructureFK();

        ContractRequirement contractRequirement = null;

		if (!isRequirementAssociated(requirementId)) {
			FilteredRequirement filteredRequirement = FilteredRequirement
					.findBy_ProductStructurePK_AND_RequirementId(productStructurePK, requirementId);

            contractRequirement = new ContractRequirement();

            contractRequirement.associateSegment(this);
            contractRequirement.associateFilteredRequirement(filteredRequirement);

            contractRequirement.save();
        }

        return contractRequirement;
    }

    /**
     * Setter
	 * 
     * @param riderNumber
     */
	private void setCurrentRiderNumber(int riderNumber) {
        this.riderNumber = riderNumber;
    }

    /**
     * Getter
	 * 
     * @return
     */
	public int getCurrentRiderNumber() {
        return segmentVO.getRiderNumber();
    }

    /**
     * Finder by Contract Number and RiderNumber.
	 * 
     * @param contractNumber
     * @return
     */
	public static Segment findBy_ContractNumberAndRiderNumber(String contractNumber, int riderNumber) {
        SegmentVO[] segmentVOs = new SegmentDAO().findSegmentByContractNumberAndRiderNumber(contractNumber, riderNumber, false, null);

        if ((segmentVOs != null) && (segmentVOs.length > 0)) {
            return (Segment) CRUDEntityImpl.mapVOToEntity(segmentVOs, Segment.class)[0];
        } 
        return null;
    }


    /**
     * Finder by Contract Number.
	 * 
     * @param contractNumber
     * @return
     */
	public static Segment[] findBy_ContractNumber(String contractNumber) {
        SegmentVO[] segmentVOs = new SegmentDAO().findSegmentByContractNumber(contractNumber, false, null);

        return (Segment[]) CRUDEntityImpl.mapVOToEntity(segmentVOs, Segment.class);
    }

    /**
     * Finder.
	 * 
     * @param contractClient
     * @return
     */
	public static final Segment findBy_ContractClient(ContractClient contractClient) {
        Segment segment = null;

        String hql = "select s from Segment s join s.ContractClients cc where cc = :contractClient";

        Map params = new HashMap();

        params.put("contractClient", contractClient);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segment = (Segment) results.get(0);
        }

        return segment;
    }
	public static final Segment findBy_ContractClientPK(long contractClientPK) {
        Segment segment = null;
        String hql = "select s from Segment s join s.ContractClients cc where cc.ContractClientPK = :contractClientPK";
        Map params = new HashMap();
        params.put("contractClientPK", contractClientPK);
        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
		if (!results.isEmpty()) {
            segment = (Segment) results.get(0);
        }
        return segment;
    }

    public static Segment getSegmentLimitedData(String contractNumber) {
        Segment segment = null;
		String sql = "SELECT SegmentPK, IssueDate, EffectiveDate, ContractGroupFK"
				+ " FROM Segment WHERE ContractNumber=? ORDER BY SegmentPK ASC";
		try (Connection connection = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL)
				.getCrudConn()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, contractNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                    	segment = new Segment();
                        segment.setSegmentPK(rs.getLong("SegmentPK"));
                        if (rs.getDate("IssueDate") != null)
                            segment.setIssueDate(new EDITDate(rs.getDate("IssueDate").getTime()));
                        if (rs.getDate("EffectiveDate") != null)
                            segment.setEffectiveDate(new EDITDate(rs.getDate("EffectiveDate").getTime()));
                        segment.setContractGroupFK(rs.getLong("ContractGroupFK"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get Segment data for contractNumber: " + contractNumber);
            e.printStackTrace();
        }

        return segment;
    }

    public static Segment getSegmentPKContractGroupFKByContractNumber(String contractNumber) {
        Map<String, Long> keys = new HashMap<>();


        Session session = SessionHelper.getSession(Segment.DATABASE);
		Criteria cr = session.createCriteria(Segment.class)
				.setProjection(Projections.projectionList().add(Projections.property("SegmentPK"), "SegmentPK")
                .add(Projections.property("IssueDate"), "IssueDate")
                .add(Projections.property("EffectiveDate"), "EffectiveDate")
                .add(Projections.property("ContractGroupFK"), "ContractGroupFK"))
				.setResultTransformer(Transformers.aliasToBean(Segment.class)).setMaxResults(1).setFirstResult(0)
				.add(Restrictions.eq("ContractNumber", contractNumber.toUpperCase()));

        List<Segment> segments = cr.list();
        if (segments != null) {
            return segments.get(0);
        }

        return null;
    }

	// TODO: This takes about %75 of the total execution time. ---- KL - Accounting
	// chh
	public static final Segment findByContractNumber(String contractNumber) {
        Segment segment = null;

		String hql = "select s from Segment s where s.ContractNumber = :contractNumber"
				+ " and s.SegmentFK IS NULL order by s.SegmentPK asc";

        Map params = new HashMap();

        params.put("contractNumber", contractNumber.toUpperCase());

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segment = (Segment) results.get(0);
        }

        return segment;
    }
    
    public static final Segment[] findAllByContractNumber(String contractNumber)
    {
        Segment[] segments = null;

        String hql = "select s from Segment s where s.ContractNumber = :contractNumber " +
                     "order by s.SegmentPK asc";

        Map params = new HashMap();

        params.put("contractNumber", contractNumber.toUpperCase());

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

        if (!results.isEmpty())
        {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }

	public static final Segment[] findByProductStructureFK(Long productStructureFK) {
        Segment[] segments = null;

		String hql = "select s from Segment s where s.ProductStructureFK = :productStructureFK"
				+ " and (s.SegmentStatusCT = :activeStatus " + " or s.SegmentStatusCT = :fsHedgeFundPendStatus "
				+ " or s.SegmentStatusCT = :frozenStatus " + " or s.SegmentStatusCT = :lapsePendingStatus)"
				+ " order by s.SegmentPK asc";

        Map params = new HashMap();

        params.put("productStructureFK", productStructureFK);
        params.put("activeStatus", "Active");
        params.put("fsHedgeFundPendStatus", "FSHedgeFundPend");
        params.put("frozenStatus", "Frozen");
        params.put("lapsePendingStatus", "LapsePending");

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }

	public static final Segment[] findByCreationDateLE(EDITDate paramDate) {
        Segment[] segments = null;

		String hql = "select s from Segment s where s.CreationDate <= :creationDate" + " and s.SegmentFK is null";

        Map params = new HashMap();

        params.put("creationDate", paramDate);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }

	public static final String getSegmentStatusBySegmentPK(Long segmentPK) {
        String segmentStatus = null;

        String hql = "select s.SegmentStatusCT from Segment s where s.SegmentPK = :segmentPK";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
        	segmentStatus = (String) results.get(0);
        }

        return segmentStatus;
    }
	
	public static final String getSegmentNameBySegmentPK(Long segmentPK) {
        String segmentName = null;

        String hql = "select s.SegmentNameCT from Segment s where s.SegmentPK = :segmentPK";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
			segmentName = (String) results.get(0);
        }

        return segmentName;
    }
    
    public static final List<String> getDistinctOptionCodesByContractNumber(String contractNumber)
    {
        String[] optionCodes = null;

        String hql = "select s.OptionCodeCT from Segment s where s.ContractNumber = :contractNumber";

        Map<String, String> params = new HashMap<>();

        params.put("contractNumber", contractNumber);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
        
        if (!results.isEmpty())
        {
        	optionCodes = (String[]) results.toArray(new String[results.size()]);

        }

        return results;
    }

    /**
     * Returns the active "insured" of the policy as a ContractClient, if any.
	 * 
     * @return
     */
	public ContractClient getInsuredContractClient() {
        ContractClient insuredContractClient = null;

        // only life policies will have insured role.
		if (isLifeProduct()) {
			insuredContractClient = ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this,
					ClientRole.ROLETYPECT_INSURED);
        }

        return insuredContractClient;
    }

    /**
	 * Returns the active "TermInsured" of the segment as a ContractClient, if it
	 * exists.
	 * 
     * @return
     */
	public ContractClient getTermInsuredContractClient() {
        ContractClient termInsuredContractClient = null;

		if (isLifeProduct()) {
			termInsuredContractClient = ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this,
					ClientRole.ROLETYPECT_TERM_INSURED);
        }

        return termInsuredContractClient;
    }

    /**
     * Returns the active "payor" of the policy as a ContractClient, if any.
	 * 
     * @return
     */
	public ContractClient getPayorContractClient() {
        ContractClient payorContractClient = null;

        // only life policies will have payor role.
		if (isLifeProduct()) {
			payorContractClient = ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this,
					ClientRole.ROLETYPECT_PAYOR);
        }

        return payorContractClient;
    }

    /**
     * Returns the active "annuitant" of the policy as a ContractClient, if any.
	 * 
     * @return
     */
	public ContractClient getAnnuitantContractClient() {
        ContractClient annuitantContractClient = null;

        // only deferred annuity policies will have annuitant role.
		if (isDeferredAnnuityProduct()) {
			annuitantContractClient = ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this,
					ClientRole.ROLETYPECT_ANNUITANT);
        }

        return annuitantContractClient;
    }

    /**
     * Returns secondary owner of the policy as ContractClient if exists.
	 * 
     * @return
     */
	public ContractClient getSecondaryOwner() {
        return ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this, ClientRole.ROLETYPECT_SECONDARY_OWNER);
    }

    /**
     * Returns beneficiaries for the segment as ContractClient array.
	 * 
     * @return
     */
	public ContractClient[] getBeneficiaryContractClients() {
        List beneficiaries = new ArrayList();

        String[] rolesToUse = null;
		if (selectedCasetrackingOption != null && selectedCasetrackingOption.equalsIgnoreCase("SpousalContinuation")) {
            rolesToUse = ClientRole.BENEFICIARY_SECONDARY_OWNER;
		} else {
            rolesToUse = ClientRole.BENEFICIARY_ROLES;
        }

		for (Iterator itr = getContractClients().iterator(); itr.hasNext();) {
            ContractClient contractClient = (ContractClient) itr.next();

            ClientRole clientRole = contractClient.getClientRole();

            String roleTypeCT = clientRole.getRoleTypeCT();

			if (Util.verifyStringExistsInArray(rolesToUse, roleTypeCT)) {
                EDITDate terminationDate = contractClient.getTerminationDate();

				if (!terminationDate.before(new EDITDate())) {
                    beneficiaries.add(contractClient);
                }
            }
        }

        return (ContractClient[]) beneficiaries.toArray(new ContractClient[beneficiaries.size()]);
    }

	public void adjustCostBasis(EDITBigDecimal costBasis) {
        EDITBigDecimal segmentCostBasis = getCostBasis();
        segmentCostBasis = segmentCostBasis.addEditBigDecimal(costBasis);
        segmentVO.setCostBasis(segmentCostBasis.getBigDecimal());
        this.saveChangeHistory = true;
        saveNonRecursively();
    }

    /**
     * setter
	 * 
     * @param casetrackingOption
     */
	public void setCasetrackingOptionCT(String casetrackingOption) {
        segmentVO.setCasetrackingOptionCT(casetrackingOption);
    }
    //-- void setCasetrackingOption(java.lang.String)

   /**
    * getter
	 * 
    * @return
    */
	public String getPrintLine1() {
        return segmentVO.getPrintLine1();
    } //-- java.lang.String getPrintLine1()

    /**
     * getter
	 * 
     * @return
     */
	public String getPrintLine2() {
        return segmentVO.getPrintLine2();
    } //-- java.lang.String getPrintLine2()

    /**
     * setter
	 * 
     * @param printLine1
     */
	public void setPrintLine1(String printLine1) {
        segmentVO.setPrintLine1(printLine1);
    } //-- void setPrintLine1(java.lang.String)

    /**
     * setter
	 * 
     * @param printLine2
     */
	public void setPrintLine2(String printLine2) {
        segmentVO.setPrintLine2(printLine2);
    } //-- void setPrintLine2(java.lang.String)

    /**
     * getter
	 * 
     * @return
     */
	public String getCasetrackingOptionCT() {
        return segmentVO.getCasetrackingOptionCT();
    } //-- java.lang.String getCasetrackingOption()

   /**
     * getter
	 * 
     * @return
     */
	public String getSelectedCasetrackingOption() {
        return selectedCasetrackingOption;
    }

    /**
     * setter
	 * 
     * @param selectedCasetrackingOption
     */
	public void setSelectedCasetrackingOption(String selectedCasetrackingOption) {
        this.selectedCasetrackingOption = selectedCasetrackingOption;
    }

    /**
     * getter
	 * 
     * @return
     */
	public EDITDate getLastSettlementValDate() {
        return SessionHelper.getEDITDate(segmentVO.getLastSettlementValDate());
    } //-- EDITDate getLastSetlementValDate()

    /**
     * getter
	 * 
     * @return
     */
	public EDITBigDecimal getSettlementAmount() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getSettlementAmount());
    } //-- EDITBigDecimal getSettlementAmount()

    /**
     * setter
	 * 
     * @param lastSettlementValDate
     */
	public void setLastSettlementValDate(EDITDate lastSettlementValDate) {
        segmentVO.setLastSettlementValDate(SessionHelper.getEDITDate(lastSettlementValDate));
    } //-- void setLastSetlementValDate(EDITDate)

    /**
     * setter
	 * 
     * @param settlementAmount
     */
	public void setSettlementAmount(EDITBigDecimal settlementAmount) {
        segmentVO.setSettlementAmount(SessionHelper.getEDITBigDecimal(settlementAmount));
    } //-- void setSettlementAmount(EDITBigDecimal)

    /**
     * Getter
	 * 
     * @return
     */
	public EDITDate getFirstNotifyDate() {
        return SessionHelper.getEDITDate(segmentVO.getFirstNotifyDate());
    }

    /**
     * Setter.
	 * 
     * @param firstNotifyDate
     */
	public void setFirstNotifyDate(EDITDate firstNotifyDate) {
        segmentVO.setFirstNotifyDate(SessionHelper.getEDITDate(firstNotifyDate));
    }

    /**
     * Getter
	 * 
     * @return
     */
	public EDITDate getPreviousNotifyDate() {
        return SessionHelper.getEDITDate(segmentVO.getPreviousNotifyDate());
    }

    /**
     * Setter.
	 * 
     * @param previousNotifyDate
     */
	public void setPreviousNotifyDate(EDITDate previousNotifyDate) {
        segmentVO.setPreviousNotifyDate(SessionHelper.getEDITDate(previousNotifyDate));
    }

    /**
     * Getter
	 * 
     * @return
     */
	public EDITDate getFinalNotifyDate() {
        return SessionHelper.getEDITDate(segmentVO.getFinalNotifyDate());
    }

    /**
     * Setter.
	 * 
     * @param finalNotifyDate
     */
	public void setFinalNotifyDate(EDITDate finalNotifyDate) {
        segmentVO.setFinalNotifyDate(SessionHelper.getEDITDate(finalNotifyDate));
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public String getAdvanceFinalNotify() {
        return segmentVO.getAdvanceFinalNotify();
    }

    /**
     * Setter.
	 * 
     * @param advanceFinalNotify
     */
	public void setAdvanceFinalNotify(String advanceFinalNotify) {
        segmentVO.setAdvanceFinalNotify(advanceFinalNotify);
    }

    /**
     * Getter
	 * 
     * @return
     */
	public String getClientUpdate() {
        return segmentVO.getClientUpdate();
    } //-- java.lang.String getClientUpdate()

    /**
     * Setter
	 * 
     * @param clientUpdate
     */
	public void setClientUpdate(String clientUpdate) {
        segmentVO.setClientUpdate(clientUpdate);
    } //-- void setClientUpdate(java.lang.String)

    /**
     * Getter.
	 * 
     * @return
     */
	public String getWorksheetTypeCT() {
        return segmentVO.getWorksheetTypeCT();
    }

    /**
     * Setter.
	 * 
     * @param worksheetTypeCT
     */
	public void setWorksheetTypeCT(String worksheetTypeCT) {
        segmentVO.setWorksheetTypeCT(worksheetTypeCT);
    }

	public String getEstateOfTheInsured() {
        return segmentVO.getEstateOfTheInsured();
    }

	public void setEstateOfTheInsured(String estateOfTheInsured) {
        segmentVO.setEstateOfTheInsured(estateOfTheInsured);
    }

    /**
	 * Getter. The BillScheduleChangeType is an indicator that is used by scripts to
	 * determine what type of change was made to the BillSchedule.
	 * 
     * @return
     */
	public String getBillScheduleChangeType() {
        return segmentVO.getBillScheduleChangeType();
    }

	public void setBillScheduleChangeType(String billScheduleChangeType) {
        segmentVO.setBillScheduleChangeType(billScheduleChangeType);
    }

	public String getDividendOptionCT() {
        return segmentVO.getDividendOptionCT();
    }

	public void setDividendOptionCT(String dividendOptionCT) {
        segmentVO.setDividendOptionCT(dividendOptionCT);
    }

	public EDITDate getRequirementEffectiveDate() {
        return SessionHelper.getEDITDate(segmentVO.getRequirementEffectiveDate());
    }

	public void setRequirementEffectiveDate(EDITDate requirementEffectiveDate) {
        segmentVO.setRequirementEffectiveDate(SessionHelper.getEDITDate(requirementEffectiveDate));
    }

	public void createAndSaveNewContract(ClientDetail clientDetail, CasetrackingLog casetrackingLog, Map fundMap) {
        createNewContractClientsForBeneSelected(clientDetail);

		if (fundMap != null) {
            createNewInvestments(fundMap);
        }


        //save the new policy
        this.setSegmentNameCT("DFA");
        this.setSegmentStatusCT("Pending");
        this.setApplicationReceivedDate(this.getEffectiveDate());
        this.setApplicationSignedDate(this.getEffectiveDate());
        this.setCasetrackingOptionCT(casetrackingLog.getCaseTrackingEvent());
        
        SessionHelper.saveOrUpdate(this, Segment.DATABASE);

    }

    /**
     * Establish the Roles/ ContractClients required for an annuity policy
	 * 
     * @param clientDetail
     */
	public void createNewContractClientsForBeneSelected(ClientDetail clientDetail) {
        //setup Annuitiant ContractClient
        ClientRole clientRole = clientDetail.findOrCreateRoles("ANN");
        ContractClient newContractClient = formatContractClient(clientRole, this.getEffectiveDate(), "P");
        this.addContractClient(newContractClient);

        //setup Owner ContractClient
        clientRole = clientDetail.findOrCreateRoles("OWN");
        newContractClient = formatContractClient(clientRole, this.getEffectiveDate(), "P");
        this.addContractClient(newContractClient);

        //setup Payee ContractClient
        clientRole = clientDetail.findOrCreateRoles("PAY");
        newContractClient = formatContractClient(clientRole, this.getEffectiveDate(), "P");
        this.addContractClient(newContractClient);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Set getNoteReminders() {
        return noteReminders;
    }

    /**
     * Setter.
	 * 
     * @param noteReminders
     */
	public void setNoteReminders(Set noteReminders) {
        this.noteReminders = noteReminders;
    }

    /**
     * Removes a NoteReminder from the set of children
	 * 
     * @param noteReminder
     */
	public void removeNoteReminder(NoteReminder noteReminder) {
        this.getNoteReminders().remove(noteReminder);

        noteReminder.setSegment(null);

        SessionHelper.saveOrUpdate(noteReminder, Segment.DATABASE);
    }


    /**
     * Getter.
	 * 
     * @return
     */
	public Set<Segment> getSegments() {
        return riderSegments;
    }

    /**
     * Setter.
	 * 
     * @param riderSegments
     */
	public void setSegments(Set<Segment> riderSegments) {
        this.riderSegments = riderSegments;
    }

	public void addRiderSegment(Segment riderSegment) {
        this.getSegments().add(riderSegment);

        riderSegment.setSegment(this);
    }

	public void removeRiderSegment(Segment riderSegment) {
        this.getSegments().remove(riderSegment);

        riderSegment.setSegment(null);

        SessionHelper.saveOrUpdate(riderSegment, Segment.DATABASE);
    }

    /**
	 * Returns true if the segment status is changed It compares itself against
	 * persisted state.
	 * 
     * @return
     */
	public boolean isSegmentStatusChanged() {
        boolean isChanged = false;

        Segment persistedSegment = Segment.findByPK(segmentVO.getSegmentPK());

		if (persistedSegment != null) {
            String previousStatus = persistedSegment.getSegmentStatusCT();

			if (previousStatus != null) {
				if (!previousStatus.equalsIgnoreCase(this.getSegmentStatusCT())) {
                    isChanged = true;
                }
            }
        }

        return isChanged;
    }

    /**
     * For the parameters passed in, set them into a new ContractClient.
	 * 
     * @param clientRole
     * @param effectiveDate
     * @param status
     * @return
     */
	public ContractClient formatContractClient(ClientRole clientRole, EDITDate effectiveDate, String status) {
        ContractClient newContractClient = new ContractClient();

        //format new beneContractClient for the clientRole passed in
        newContractClient.setOverrideStatus(status);
        newContractClient.setClientRole(clientRole);
        newContractClient.setEffectiveDate(effectiveDate);
        newContractClient.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

        return newContractClient;
    }

    /**
	 * From the Investment selection in the fundMap, create an Investment and
	 * InvestmentAllocation table records.
	 * 
     * @param fundMap
     */
	private void createNewInvestments(Map fundMap) {
        Set keys = fundMap.keySet();
        String[] keysArray = (String[])keys.toArray(new String[keys.size()]);

		for (int i = 0; i < keysArray.length; i++) {
            Investment investment = new Investment();
            investment.setFilteredFundFK(new Long(keysArray[i]));
            InvestmentAllocation investmentAllocation = new InvestmentAllocation();
            investmentAllocation.setAllocationPercent(new EDITBigDecimal((String)fundMap.get(keysArray[i])));
            investmentAllocation.setOverrideStatus("P");
            investment.addInvestmentAllocation(investmentAllocation);
            this.addInvestment(investment);
        }
    }

	public void createNewBeneficiary(ClientDetail clientDetail, String beneRole, ContractClient contractClient,
			String operator, EDITBigDecimal allocationPercent, String splitEqualInd, EDITBigDecimal allocationAmount) {
        //create and save the new role for the new bene
        ClientRole clientRole = clientDetail.saveClientRole(beneRole);

        contractClient.setOverrideStatus("P");
        contractClient.setClientRole(clientRole);
        contractClient.setEffectiveDate(new EDITDate());

		if (contractClient.getTerminationDate() == null) {
            contractClient.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
        }

        //required for changeHistory creation
        this.setOperator(operator);
        this.setChangeHistoryEffDate(new EDITDate());

        this.addContractClient(contractClient);

        contractClient.checkForNonFinancialChanges();

        contractClient.createContractClientAllocation(allocationPercent, "P", splitEqualInd, allocationAmount);

        SessionHelper.saveOrUpdate(contractClient, Segment.DATABASE);
    }

	public ContractClient updateExistingBeneficiary(ContractClient contractClient, String relationship,
			EDITDate terminationDate, String terminationReason, String operator, EDITBigDecimal allocationPercent,
			String splitEqualInd, EDITBigDecimal allocationAmount) {
        this.setOperator(operator);
        this.setChangeHistoryEffDate(new EDITDate());

        contractClient.setRelationshipToInsuredCT(relationship);
        contractClient.setTerminationDate(terminationDate);
        contractClient.setTerminationReasonCT(terminationReason);

        contractClient.checkForNonFinancialChanges();

        ContractClientAllocation contractClientAllocation = contractClient.getContractClientAllocation();
        contractClientAllocation.setAllocationPercent(allocationPercent);
        contractClientAllocation.setAllocationDollars(allocationAmount);
        contractClientAllocation.setSplitEqual(splitEqualInd);
        contractClient.addContractClientAllocation(contractClientAllocation);

        return contractClient;
    }



   /**
     * Returns the decease role of the policy as a ContractClient.
	 * 
     * @return
     */
	public ContractClient getDeceasedContractClient() {
        return ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this, ClientRole.ROLETYPECT_DECEASED);
    }

	public void setContractNumberToUpperCase() {
        String contractNumber = segmentVO.getContractNumber();
        segmentVO.setContractNumber(contractNumber.toUpperCase());
    }

    /**
    * Getter.
	 * 
    * @return
    */
	public EDITBigDecimal getLoads() {
        return SessionHelper.getEDITBigDecimal(segmentVO.getLoads());
    }

    /**
    * Getter.
	 * 
    * @return
    */
	public String getSegmentNameCT() {
        return segmentVO.getSegmentNameCT();
    }
    
    /**
     * Returns true if contract is in pending status.
	 * 
     * @return
     */
	public boolean isPending() {
        boolean isPending = false;
    
		if (this.getSegmentStatusCT().equals(Segment.SEGMENTSTATUSCT_PENDING)) {
            isPending = true;
        }
        
        return isPending;
    }

    /*
    * The "active" owner of this policy as ClientDetail.
	 * 
    * @return
    */
	public ClientDetail getOwner() {
        ClientDetail clientDetail = null;
    
		if (this.isPending()) {
            // Syam Lingamallu
            // Though getCurrentOwner() should be used for 5498 tax purpose only,
            // I used the method to avoid duplication of another HQL.
			// The getOwner method is being used to get the owner of the contract for
			// building the
			// issue document, but by that time the ContractClient.EffectiveDate is null and
			// hence
            // the hql for getting the contract client with max effectivedate  is failing. 
            clientDetail = getCurrentOwner();
		} else {
            clientDetail = getOwnerContractClient().getClientRole().getClientDetail();
        }
    
        return clientDetail;
    }

    /**
     * Returns the active "insured" of this contract as Client Detail.
	 * 
     * @return
     */
	public ClientDetail getInsured() {
        ClientDetail insured = null;

        // only life policies will have insured role.
		if (isLifeProduct()) {
            insured = getInsuredContractClient().getClientRole().getClientDetail();
        }

        return insured;
    }

	public ClientDetail getTermInsured() {
        ClientDetail termInsured = null;

		if (isLifeProduct()) {
            termInsured = getTermInsuredContractClient().getClientRole().getClientDetail();
        }

        return termInsured;
    }

    /**
     * Returns the active "payor" of this contract as Client Detail.
	 * 
     * @return
     */
	public ClientDetail getPayor() {
        ClientDetail payor = null;

        // only life policies will have payor role.
		if (isLifeProduct()) {
            payor = getPayorContractClient().getClientRole().getClientDetail();
        }

        return payor;
    }

    /**
     * Terminate the current owner, this is a case tracking option
     */
	public ContractClient terminateCurrentOwner() {
        //Find the owner of the contract
        ContractClient contractClient = getOwnerContractClient();
        
        EDITDate effectiveDate = contractClient.getEffectiveDate();

        //Calculate the termination date effectiveDate minus one
        EDITDate terminationDate = effectiveDate.subtractDays(1);
        contractClient.setTerminationDate(terminationDate);
        contractClient.setTerminationReasonCT("SpousalContinuation");

        //The segment entity has been set into this contractClient
        contractClient.setSegment(this);

        return contractClient;
    }


//    /**
//     * The insured ContractClient, if any.
//     * @return
//     */
//    public ContractClient getInsuredContractClient()
//    {
//        return ContractClient.findBy_SegmentPK_RoleTypeCT(getSegmentPK(), ClientRole.ROLETYPECT_INSURED);
//    }

    /**
     * Setter.
     */
	public void setProductStructureFK(Long productStructureFK) {
        //        segmentVO.setProductStructureFK(SessionHelper.getPKValue(productStructureFK));
        segmentVO.setProductStructureFK(SessionHelper.getPKValue(productStructureFK));
    }


    /**
     * Getter.
	 * 
     * @return
     */
	public Set<Payout> getPayouts() {
        return payouts;
    }

    /**
     * Setter.
	 * 
     * @param payouts
     */
	public void setPayouts(Set<Payout> payouts) {
        this.payouts = payouts;
    }

    /**
     * Associates a Payout to this Segment.
	 * 
     * @param payout
     */
	public void setPayout(Payout payout) {
        getPayouts().add(payout);

        payout.setSegment(this);
    }

    /**
     * Getter.
	 * 
     * @return
     */
	public Payout getPayout() {
        Payout payout = getPayouts().isEmpty()?null:(Payout) getPayouts().iterator().next();

        return payout;
    }

    /**
     * Removes a Payout from the set of children
	 * 
     * @param payout
     */
	public void removePayout(Payout payout) {
        this.getPayouts().remove(payout);

        payout.setSegment(null);

        SessionHelper.saveOrUpdate(payout, Segment.DATABASE);
    }

//    /**
//     * Getter.
//     * @return
//     */
//    public Set getSegments()
//    {
//        return segments;
//    }
//
//    /**
//     * Setter.
//     * @param segments
//     */
//    public void setSegments(Set segments)
//    {
//        this.segments = segments;
//    }

    /**
	 * Adds a ContractClient to the set of ContractClients associated with this
	 * Segment.
	 * 
     * @param contractClient
     */
	public void addContractClient(ContractClient contractClient) {
        getContractClients().add(contractClient);

        contractClient.setSegment(this);
    }

    /**
     * Adds a Deposit to the set of Deposits associated with this Segment.
	 * 
     * @param deposit
     */
	public void addDeposit(Deposits deposit) {
        getDeposits().add(deposit);

        deposit.setSegment(this);

        SessionHelper.saveOrUpdate(this, Segment.DATABASE);
    }

    /**
     * Use this date for the changeHistory record on the DP process.
	 * 
     * @param effectiveDate
     */
	public void setChangeHistoryEffDate(EDITDate effectiveDate) {
        this.changeHistoryEffDate = effectiveDate;
    }

    /**
     * Getter
	 * 
     * @return
     */
	public EDITDate getChangeHistoryEffDate() {
        return this.changeHistoryEffDate;
    }
    
    /**
     * Getter
	 * 
     * @return
     */
	public String getPremiumTaxSitusOverrideCT() {
        return segmentVO.getPremiumTaxSitusOverrideCT();
    } //-- java.lang.String getPremiumTaxSitusOverrideCT() 
    
     /**
      * Setter
	 * 
      * @param premiumTaxSitusOverrideCT
      */
	public void setPremiumTaxSitusOverrideCT(String premiumTaxSitusOverrideCT) {
         segmentVO.setPremiumTaxSitusOverrideCT(premiumTaxSitusOverrideCT);
     } //-- void setPremiumTaxSitusOverrideCT(java.lang.String)

    /**
     * Getter
	 * 
     * @return
     */
	public String getROTHConvInd() {
        return segmentVO.getROTHConvInd();
    } //-- java.lang.String getROTHConvInd()


    /**
     * Setter
	 * 
     * @param ROTHConvInd
     */
	public void setROTHConvInd(String ROTHConvInd) {
        segmentVO.setROTHConvInd(ROTHConvInd);
    } //-- void setROTHConvInd(java.lang.String)

    /**
     * Finder by PK.
	 * 
     * @param segmentPK
     * @return
     */
	public static final Segment findByPK(Long segmentPK) {
        return (Segment) SessionHelper.get(Segment.class, segmentPK, Segment.DATABASE);
    }

    /*
     * The associated ProductStructure.
	 * 
     * @return
    */
	public ProductStructure getProductStructure() {
		if (productStructure == null) {
            productStructure = ProductStructure.findByPK(this.segmentVO.getProductStructureFK());
        }

        return productStructure;
    }



    /**
	 * Returns total Interest Proceeds for all LumpSum transactions for this
	 * segment.
	 * 
     * @return
     */
	public EDITBigDecimal getTotalInterestProceedsForLumpSumTransactions() {
        EDITBigDecimal totalInterestProceeds = null;

        TransactionAccumsFastDAO fastDAO = new TransactionAccumsFastDAO();

        totalInterestProceeds = fastDAO.accumLS_InterestProceeds(getPK());

        return totalInterestProceeds;
    }

    /**
     * Returns total Gross Proceeds for all LumpSum transactions for this segment.
	 * 
     * @return
     */
	public EDITBigDecimal getTotalGrossProceedsForLumpSumTransactions() {
        EDITBigDecimal totalGrossProceeds = null;

        TransactionAccumsFastDAO fastDAO = new TransactionAccumsFastDAO();

        totalGrossProceeds = fastDAO.accumLS_GrossAmount(getPK());

        return totalGrossProceeds;
    }

    /**
	 * Returns total Accumulated Value for all LumpSum transactions for this
	 * segment.
	 * 
     * @return
     */
	public EDITBigDecimal getTotalAccumulatedValueForLumpSumTransactions() {
        EDITBigDecimal totalAccumulatedValue = null;

        TransactionAccumsFastDAO fastDAO = new TransactionAccumsFastDAO();

        totalAccumulatedValue = fastDAO.accumLS_AccumulatedValue(getPK());

        return totalAccumulatedValue;
    }

	public EDITBigDecimal calculateTotalBeneficiaryAllocationPercent() {
        EDITBigDecimal totalAllocationPercent = new EDITBigDecimal();

        ContractClient[] beneficiaryContractClients = this.getBeneficiaryContractClients();

		for (int i = 0; i < beneficiaryContractClients.length; i++) {
            ContractClient beneficiaryContractClient = beneficiaryContractClients[i];

			ContractClientAllocation beneficiaryContractClientAllocation = beneficiaryContractClient
					.getContractClientAllocation();

            EDITBigDecimal allocationPercent = beneficiaryContractClientAllocation.getAllocationPercent();

            totalAllocationPercent = totalAllocationPercent.addEditBigDecimal(allocationPercent);
        }

        return totalAllocationPercent;
    }

    /**
     * Calculate the loan payoff for a given BucketSourceCT
     * 
	 * @param bucketSourceCT - the BucketSourceCT to use
     * @return EDITBigDecimal - the loan payoff amount 
     */
	public EDITBigDecimal calculateLoanPayoff(String bucketSourceCT) {
    	EDITBigDecimal loanPayoffAmount = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
		for (Investment investment : investments) {
    		Set<Bucket> bucketList = investment.getBuckets();
			if (CollectionUtils.isNotEmpty(bucketList)) {
				for (Bucket bucket : bucketList) {
					if (bucket != null && bucketSourceCT.equals(bucket.getBucketSourceCT())) {
    					loanPayoffAmount = loanPayoffAmount.addEditBigDecimal(bucket.getLoanCumDollars());
    				}
    			}
    		}
    	}
    	return loanPayoffAmount;
    }

    /**
	 * Determines whether any of the Beneficiaries has the splitEqual indicator set
	 * turned on.
     *
	 * @return true if any of the beneficiaries has the splitEqual indicator turned
	 *         on, false otherwise
     */
	public boolean isBeneAllocSplitEqualOn() {
        boolean splitEqual = false;

        ContractClient[] beneficiaryContractClients = this.getBeneficiaryContractClients();

		for (int i = 0; i < beneficiaryContractClients.length; i++) {
            ContractClient beneficiaryContractClient = beneficiaryContractClients[i];

			ContractClientAllocation beneficiaryContractClientAllocation = beneficiaryContractClient
					.getContractClientAllocation();

			if (beneficiaryContractClientAllocation.getSplitEqual().equalsIgnoreCase("Y")) {
                splitEqual = true;
                break;
            }
        }

        return splitEqual;
    }

    /**
	 * Determines whether any of the beneficiaries has allocation amounts set or
	 * not. The amounts can be dollars or percent. If the amounts are not null or
	 * zero, they are considered to be "set".
     *
	 * @return true if any of the beneficiaries has an allocation amount set to a
	 *         non-null or non-zero value, false otherwise
     */
	public boolean isBeneAllocAmountsSet() {
        boolean allocationAmountsSet = false;

        ContractClient[] beneficiaryContractClients = this.getBeneficiaryContractClients();

		for (int i = 0; i < beneficiaryContractClients.length; i++) {
            ContractClient beneficiaryContractClient = beneficiaryContractClients[i];

			ContractClientAllocation beneficiaryContractClientAllocation = beneficiaryContractClient
					.getContractClientAllocation();

            EDITBigDecimal allocationPercent = beneficiaryContractClientAllocation.getAllocationPercent();
            EDITBigDecimal allocationDollars = beneficiaryContractClientAllocation.getAllocationDollars();

			if (allocationPercent != null && allocationDollars != null) {
				if (!allocationPercent.isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)
						|| !allocationDollars.isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)) {
                    allocationAmountsSet = true;
                    break;
                }
            }
        }

        return allocationAmountsSet;
    }

	public void checkForComplexChange() {
        ChangeProcessor changeProcessor = new ChangeProcessor();
		Change[] changes = changeProcessor.checkForChanges(segmentVO, segmentVO.getVoShouldBeDeleted(),
				ConnectionFactory.EDITSOLUTIONS_POOL, null);
		if (changes != null && changes.length > 0) {
			for (int i = 0; i < changes.length; i++) {
				if (changes[i].getStatus() == (Change.CHANGED)) {
					if (changes[i].getFieldName().equalsIgnoreCase("QualifiedTypeCT")
							&& (changes[i].getBeforeValue() != null
									&& changes[i].getBeforeValue().equalsIgnoreCase("IRA"))
							&& (changes[i].getAfterValue() != null
									&& changes[i].getAfterValue().equalsIgnoreCase("ROTH"))) {
                        segmentVO.setROTHConvInd("Y");
                        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();
						for (int j = 0; j < contractClientVOs.length; j++) {
                            ContractClient contractClient = new ContractClient(contractClientVOs[j]);
                            this.addContractClient(contractClient);
                        }
                        ComplexChange complexChange = new ComplexChange();
						complexChange.createComplexChangeForRothConversion(this, changes[i].getFieldName(), changes[i],
								getOperator());
                    }
                }
            }
        }
    }

    /**
     * Finder.
	 * 
     * @param segmentFK
     * @param riderNumber
     * @return
     */
	public static Segment findBy_SegmentFK_RiderNumber(Long segmentFK, Integer riderNumber) {
        Segment segment = null;

		String hql = " from Segment segment" + " where segment.SegmentFK = :segmentFK"
				+ " and segment.RiderNumber = :riderNumber";

        Map params = new HashMap();

        params.put("segmentFK", segmentFK);

        params.put("riderNumber", riderNumber);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segment = (Segment) results.get(0);
        }

        return segment;
    }

    /**
     * Originally in SegmentDAO.findRidersBySegmentPK
	 * 
     * @param segmentPK
     * @return
     */
	public static Segment[] findRidersBySegmentPK(Long segmentPK) {
        Segment[] segments = null;
		String hql = "select segment from Segment segment " + " where segment.SegmentFK = :segmentPK";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }
    
  /**
   * Originally in SegmentDAO.findRidersBySegmentPK
	 * 
   * @param segmentPK
   * @return
   */
	public static Segment[] findRidersBy_SegmentPK_V2(Long segmentPK) {
		String hql = " select segment" + " from Segment segment" + " left join fetch segment.RequiredMinDistributions"
				+ " left join fetch segment.Payouts" + " left join fetch segment.Deposits"
				+ " left join fetch segment.Lifes" + " where segment.SegmentFK = :segmentPK";
                
    EDITMap params = new EDITMap("segmentPK", segmentPK);
    
    List<Segment> results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
    
    return results.toArray(new Segment[results.size()]);   
  }

	public static Segment[] findRidersBy_SegmentPK_V3(Long segmentPK) {
        Segment[] segments = null;

		String hql = " select distinct segment" + " from Segment segment" + " left join fetch segment.RequiredMinDistributions"
				+ " left join fetch segment.Payouts" + " left join fetch segment.Deposits"
				+ " left join fetch segment.Lifes" + " left join fetch segment.InherentRiders"
				+ " left join fetch segment.Segments" + " left join fetch segment.BillSchedule"
				+ " where segment.SegmentFK = :segmentPK";

        EDITMap params = new EDITMap("segmentPK", segmentPK);

        Session session = null;

		try {
          session = SessionHelper.getSeparateSession(Segment.DATABASE);

          List<Segment> results = SessionHelper.executeHQL(session, hql, params, 0);

          segments = results.toArray(new Segment[results.size()]);
		} finally {
			if (session != null)
				session.close();
        }

        return segments;
    }

    /**
	 * Finds all riders whose SegmentFK equals the specified segmentPK. Includes
	 * Life tables.
	 * 
     * @param segmentPK
     * @return
     */
	public static Segment[] findRidersBy_SegmentPK_V4(Long segmentPK) {
        Segment[] segments = null;

		String hql = "select segment from Segment segment " + " left join fetch segment.Lifes life"
				+ " where segment.SegmentFK = :segmentPK";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }

    /**
     * Adder.
	 * 
     * @param contractSetup
     */
	public void addContractSetup(ContractSetup contractSetup) {
        getContractSetups().add(contractSetup);

        contractSetup.setSegment(this);
    }

    /**
	 * The set of ContractClients that are active for the specified
	 * transactionTypeCT.
	 * 
     * @param transactionTypeCT
     * @return
     */
	public ContractClient[] getActiveContractClients(String roleTypeCT) {
        ContractClient[] contractClients;

        contractClients = ContractClient.findBy_SegmentPK_And_RoleTypeCT(getSegmentPK(), roleTypeCT);

		if ((contractClients.length == 0) && !(roleTypeCT.equals(Constants.RoleType.OWNER))) // Find the owner if all
																								// else fails.
        {
            roleTypeCT = Constants.RoleType.OWNER;

            contractClients = ContractClient.findBy_SegmentPK_And_RoleTypeCT(getSegmentPK(), roleTypeCT);
        }

        return contractClients;
    }

    /**
	 * Finds a Segment for the specified pk and includes the Investment and
	 * InvestmentAllocation
     *
     * Originally from a composing a Segment
     *
     * @param segmentPK
     *
     * @return
     */
	public static Segment findBy_PK_IncludeInvestment_InvestmentAllocation(Long segmentPK) {
        Segment segment = null;

		String hql = "select segment from Segment segment " + " join fetch segment.Investment investment"
				+ " join fetch investment.InvestmentAllocation investmentAllocation"
				+ " where segment.SegmentPK = :segmentPK";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segment = (Segment) results.get(0);
        }

        return segment;
    }

	public static Segment[] findBy_ProductStructureFKs(Long[] productStructureFKs) {
		String hql = "select distinct segment from Segment segment "
				+ " where segment.ProductStructureFK in (:productStructureFKs)";

        Map params = new HashMap();
        params.put("productStructureFKs", productStructureFKs);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

        return (Segment[]) results.toArray(new Segment[results.size()]);
    }

    /**
     * Finds all segments that used to belong to the specific group
	 * 
     * @param contractGroupFK  The primary key of the Group
     *
     * @return  an array of Segments
     */
	public static Segment[] findBy_PriorContractGroupFK(Long contractGroupFK) {
		String hql = "select segment from Segment segment " + " where segment.PriorContractGroupFK = :contractGroupFK"
				+ " and segment.PriorPRDDue = :priorPRDDue";

        Map params = new HashMap();
        params.put("contractGroupFK", contractGroupFK);
        params.put("priorPRDDue", "Y");

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (results == null) {
            return null;
		} else {
            return (Segment[]) results.toArray(new Segment[results.size()]);
        }
    }

    /**
     * Finds all segments that belong to the specific group
	 * 
     * @param contractGroupFK  The primary key of the Group
     *
     * @return  an array of Segments
     */
	public static Segment[] findBy_ContractGroupFK(Long contractGroupFK) {
		String hql = "select segment from Segment segment " + " where segment.ContractGroupFK = :contractGroupFK";

        Map params = new HashMap();
        params.put("contractGroupFK", contractGroupFK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (results == null || results.isEmpty()) {
            return null;
		} else {
            return (Segment[]) results.toArray(new Segment[results.size()]);
        }
    }

    /**
     * Finds all EFT segments that belong to the specific group
	 * 
     * @param contractGroupFK  The primary key of the Group
     *
     * @return  an array of Segments
     */
	public static Segment[] findBy_ContractGroupFKForNachaFile_hold( Long contractGroupFK, String processDate) {
		String hql = "select distinct segment from Preference preference " + 
	                " join fetch preference.ClientDetail clientDetail " +
	                " join fetch clientDetail.clientRoles clientRole " +
	                " join fetch segment.BillSchedule billSchedule " +
	                " where segment.SegmentStatusCT in ('Active', 'IssuePendingPremium', 'Submitted') " +
	                "and segment.SegmentFK is null " +
	                "and clientRole.RoleTypeCT = 'POR' " +
	                "and preference.PreferenceTypeCT != 'Bank' " +
	                "and segment.ContractGroupFK = :contractGroupFK " +
	                "and billSchedule.BillTypeCT = 'INDIV' and billSchedule.BillMethodCT = 'EFT' " +
	                "and billSchedule.TerminationDate > :processDate " +
	                "and billSchedule.NextBillExtractDate <= :processDate "; 

        Map params = new HashMap();
        params.put("contractGroupFK", contractGroupFK);
        params.put("processDate", new EDITDate(processDate));

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (results == null || results.isEmpty()) {
            return null;
		} else {
            return (Segment[]) results.toArray(new Segment[results.size()]);
        }
    }
	
	public static Segment[] findBy_ContractGroupFKForNachaFile(Long contractGroupFK, String processDate, String companyPrefix) throws SQLException {
		
		String sql = "SELECT distinct s.SegmentPK FROM Segment s \r\n" + 
				"  inner join ContractClient cc on cc.SegmentFK = s.SegmentPK \r\n" + 
				"  inner join BillSchedule b on b.BillSchedulePK = s.BillScheduleFK \r\n" + 
				"  inner join ContractGroup cg on cg.ContractGroupPK = s.ContractGroupFK \r\n" + 
				"  inner join ClientRole cr on cc.ClientRoleFK = cr.ClientRolePK \r\n" + 
				"  inner join ClientDetail cd on cr.ClientDetailFK = cd.ClientDetailPK \r\n" + 
				"  inner join Preference p on p.ClientDetailFK = cd.ClientDetailPK \r\n" + 
				"  where b.BillMethodCT = 'EFT' and b.BillTypeCT = 'INDIV' \r\n" + 
//				"  and s.ContractGroupFK = :contractGroupFK \r\n " +
				"  and s.ContractGroupFK = " + contractGroupFK + " \r\n " +
				"  and b.NextBillExtractDate <= '" + processDate + "' " + " \r\n " + 
				"  and cr.RoleTypeCT = 'POR' \r\n" + 
				"  and s.ContractNumber like '" + companyPrefix + "%' " +
				"  and s.SegmentFK is null and s.SegmentStatusCT in ('Active', 'Submitted', 'IssuePendingPremium') \r\n" + 
				"  and p.PreferenceTypeCT != 'Bank' " +
				"  and p.DisbursementSourceCT = 'EFT' ";
			
		Connection connection = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection();
		List<Segment> segments = new ArrayList<>();
			//PreparedStatement statement = connection.prepareStatement(sql);
			Statement statement = connection.createStatement();
			//statement.setLong(1, contractGroupFK);
			//statement.setDate(2, java.sql.Date.valueOf(processDate));
//			ResultSet rs = statement.executeQuery();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				long segmentPK = rs.getLong("SegmentPK");
				segments.add(Segment.findByPK(segmentPK));
			}

			

        return (Segment[])segments.toArray(new Segment[segments.size()]);
		
		/*
        SQLQuery query = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).createSQLQuery(sql);

        query.addEntity(Segment.class);
        query.setParameter("contractGroupFK", contractGroupFK);
        query.setParameter("processDate", processDate);

        List<Segment> results = query.list();

		if (results == null || results.isEmpty()) {
            return null;
		} else {
            return (Segment[])results.toArray(new Segment[results.size()]);
        }
        */
    }

	public static Segment[] findNachaSegments(String processDate, String companyPrefix) throws SQLException {
		
		String sql = "SELECT distinct s.SegmentPK FROM Segment s \r\n" + 
				"  inner join ContractClient cc on cc.SegmentFK = s.SegmentPK \r\n" + 
				"  inner join BillSchedule b on b.BillSchedulePK = s.BillScheduleFK \r\n" + 
				"  inner join ContractGroup cg on cg.ContractGroupPK = s.ContractGroupFK \r\n" + 
				"  inner join ClientRole cr on cc.ClientRoleFK = cr.ClientRolePK \r\n" + 
				"  inner join ClientDetail cd on cr.ClientDetailFK = cd.ClientDetailPK \r\n" + 
				"  inner join Preference p on p.ClientDetailFK = cd.ClientDetailPK \r\n" + 
				"  where b.BillMethodCT = 'EFT' and b.BillTypeCT = 'INDIV' \r\n" + 
				"  and b.NextBillExtractDate <= '" + processDate + "' " + " \r\n " + 
				"  and b.TerminationDate > '" + processDate + "' " + " \r\n " + 
				"  and cr.RoleTypeCT = 'POR' \r\n" + 
				"  and s.ContractNumber like '" + companyPrefix + "%' " +
				"  and s.SegmentFK is null and s.SegmentStatusCT in ('Active', 'Submitted', 'IssuePendingPremium') \r\n" + 
				"  and p.PreferenceTypeCT != 'Bank' " +
				"  and p.DisbursementSourceCT = 'EFT' ";
			
		Connection connection = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).connection();
		List<Segment> segments = new ArrayList<>();
			//PreparedStatement statement = connection.prepareStatement(sql);
			Statement statement = connection.createStatement();
			//statement.setLong(1, contractGroupFK);
			//statement.setDate(2, java.sql.Date.valueOf(processDate));
//			ResultSet rs = statement.executeQuery();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				long segmentPK = rs.getLong("SegmentPK");
				segments.add(Segment.findByPK(segmentPK));
			}

			

        return (Segment[])segments.toArray(new Segment[segments.size()]);
		
		/*
        SQLQuery query = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).createSQLQuery(sql);

        query.addEntity(Segment.class);
        query.setParameter("contractGroupFK", contractGroupFK);
        query.setParameter("processDate", processDate);

        List<Segment> results = query.list();

		if (results == null || results.isEmpty()) {
            return null;
		} else {
            return (Segment[])results.toArray(new Segment[results.size()]);
        }
        */
    }


    /**
     * Finds all distinct Segments filtered by the given criteria - Using CRUD
     *
     * @param productStructurePKs
     * @param lapsePendingDate
     * @return
     */
	public static Segment[] findBy_ProductStructurePKs_LapsePendingDate_UsingCRUD(long[] productStructurePKs,
			EDITDate lapsePendingDate) {
		SegmentVO[] segmentVOs = new SegmentDAO().findBy_ProductStructurePKs_LapsePendingDate(productStructurePKs,
				lapsePendingDate);

        return (Segment[]) CRUDEntityImpl.mapVOToEntity(segmentVOs, Segment.class);
    }

    /**
     * Finds all distinct Segments filtered by the given criteria - Using CRUD
     *
     * @param productStructurePKs
     * @param lapseDate
     * @return
     */
	public static Segment[] findBy_ProductStructurePKs_LapseDate_UsingCRUD(long[] productStructurePKs,
			EDITDate lapseDate) {
        SegmentVO[] segmentVOs = new SegmentDAO().findBy_ProductStructurePKs_LapseDate(productStructurePKs, lapseDate);

        return (Segment[]) CRUDEntityImpl.mapVOToEntity(segmentVOs, Segment.class);
    }

    /**
     * Save the entity using Hibernate
     */
	public void hSave() {
		// To save the FK's existing in the Segment row alreday the entity must be set
		// here for hibernate to be aware.
        Long pk = this.getBillScheduleFK();
		if (pk != null && pk > 0) {
            BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(pk);
            this.setBillSchedule(billSchedule);
        }

        pk = this.getContractGroupFK();
		if (pk != null && pk > 0) {
            ContractGroup contractGroup = ContractGroup.findByPK(pk);
            this.setContractGroup(contractGroup);
        }

        pk = this.getBatchContractSetupFK();
		if (pk != null && pk > 0) {
            BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(pk);
            this.setBatchContractSetup(batchContractSetup);
        }

        pk = this.getDepartmentLocationFK();
		if (pk != null && pk > 0) {
            DepartmentLocation departmentLocation = DepartmentLocation.findBy_DepartmentLocationPK(pk);
            this.setDepartmentLocation(departmentLocation);
        }

        pk = this.getPriorContractGroupFK();
		if (pk != null && pk > 0) {
            ContractGroup priorContractGroup = ContractGroup.findByPK(pk);
            this.setPriorContractGroup(priorContractGroup);
        }

        SessionHelper.saveOrUpdate(this, Segment.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
	public void hDelete() {
        SessionHelper.delete(this, Segment.DATABASE);
    }

    /**
     * Finder by PK.
	 * 
     * @param segmentPK
     * @return
     */
	public static final Segment findByPK_UsingCRUD(long segmentPK, List voExclutionList) {
        Segment segment = null;

        SegmentVO[] segmentVOs = new SegmentDAO().findBySegmentPK(segmentPK, true, voExclutionList);

		if (segmentVOs != null) {
            segment = new Segment(segmentVOs[0]);
        }

        return segment;
    }

    /**
      * Terminate Pending transactions if they require it.
	 * 
      * @param crud
      * @throws Exception
      */
	public void updatePendingTransactions(CRUD crud, String transactionType, EDITDate trxEffectiveDate)
			throws Exception {
        long segmentPK = this.getPK();

		boolean hasAllowableTransactionList = TransactionProcessor
				.hasAllowableList(this.getProductStructureFK().longValue());

		long[] editTrxPKs = new event.dm.dao.FastDAO().findBase_RiderTrxBySegmentPK_AND_PendingStatus(segmentPK,
				new String[] { "P", "M", "B", "C", "F", "L", "S" }, transactionType, crud);

		if (editTrxPKs != null) {
			if (hasAllowableTransactionList) {
                determineAllowableTransaction(editTrxPKs, crud, transactionType, trxEffectiveDate);
			} else {
                terminateQualifyingTransactions(editTrxPKs, crud);
            }
        }
    }

     /**
	 * When the config file doesn't contain any entries, loop through the pending
	 * transactions and terminate. The status field must equal "N" or "A".
	 * 
      * @param editTrxPKs
      * @param crud
      */
	private void terminateQualifyingTransactions(long[] editTrxPKs, CRUD crud) {
		for (int i = 0; i < editTrxPKs.length; i++) {
            EDITTrxVO editTrxVO = EDITTrx.findByPK_UsingCRUD(editTrxPKs[i]);

			if ((editTrxVO.getStatus().equalsIgnoreCase("N") || editTrxVO.getStatus().equalsIgnoreCase("A"))) {
                saveTerminatedTrx(crud, editTrxVO, "T");
            }
        }
    }

    /**
	 * Compare each pending transaction to the list of allowable transactions to
	 * determine if it can be allowed
	 * 
     * @param editTrxPKs
     * @param crud
     * @throws Exception
     */
	private void determineAllowableTransaction(long[] editTrxPKs, CRUD crud, String originalTrxCode,
			EDITDate originalTrxEffDate) throws Exception {
		for (int i = 0; i < editTrxPKs.length; i++) {
            EDITTrxVO editTrxVO = EDITTrx.findByPK_UsingCRUD(editTrxPKs[i]);

            ScheduledEventVO scheduledEventVO = ScheduledEvent.findByEditTrx_UsingCRUD(editTrxPKs[i]);
            String frequency = null;
			if (scheduledEventVO != null) {
                frequency = scheduledEventVO.getFrequencyCT();
            }

			boolean trxCanProcess = TransactionProcessor.checkForAllowableTransaction(this,
					editTrxVO.getTransactionTypeCT(), new EDITDate(editTrxVO.getEffectiveDate()), frequency,
					originalTrxCode, originalTrxEffDate);

            if (!trxCanProcess)
//                if (!trxCanProcess && (editTrxVO.getStatus().equalsIgnoreCase("N") || editTrxVO.getStatus().equalsIgnoreCase("A")))
            {
                saveTerminatedTrx(crud, editTrxVO, "T");
            }
        }
    }

	private void saveTerminatedTrx(CRUD crud, EDITTrxVO editTrxVO, String terminatedStatus) {
        editTrxVO.setPendingStatus(terminatedStatus);
        crud.createOrUpdateVOInDB(editTrxVO);
    }

    /**
	 * For any pending transactions change the contractClient on the clientSetup
	 * when the owner changes. Owners are can change with a complexChange trx or
	 * spousalContinuation selection in Casetracking.
     *
     * @param newContractClient
     * @throws Exception
     */
	public void updateOwnerForPendingTransactions(ContractClient newContractClient, String transactionType)
			throws Exception {
        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

        long[] editTrxPKs = new long[0];

		try {
			editTrxPKs = new event.dm.dao.FastDAO().findBySegmentPK_AND_PendingStatus(this.getPK(),
					new String[] { "P", "M", "B", "C", "F", "L", "S" }, transactionType, crud);
		} catch (Exception e) {
          System.out.println(e);

            e.printStackTrace();
		} finally {
            crud.close();
        }

		if (editTrxPKs != null) {
			for (int i = 0; i < editTrxPKs.length; i++) {
                ClientSetup clientSetup = ClientSetup.findByEDITTrxFK_UsingCRUD(editTrxPKs[i]);

                clientSetup.setContractClientFK(newContractClient.getContractClientPK());
                clientSetup.save();
            }
        }
    }

    /**
	 * This method is for 5498 tax extract purposes only. The way we check for the
	 * current owner getOwner() is not correct. We should be changing this at later
	 * point of time. For now we use this method to get current owner checking
	 * against termination date. We should check by TerminationDate = '12/31/9999'
	 * instead of max(EffectiveDate)
	 * 
     * @return ClientDeatil of Current Owner ContractClient
     */
	public ClientDetail getCurrentOwner() {
        return getCurrentOwnerContractClient().getClientRole().getClientDetail();
    }

    /**
     * To verify if the given filtered fund is investment for the contract.
	 * 
     * @param filteredFund
	 * @return boolean true - if filtered fund is an invemstment or false if is not
	 *         an invetment
     */
	public boolean isFilteredFundAnInvestment(FilteredFund filteredFund) {
        boolean isInvestment = false;

        long filteredFundPK = filteredFund.getFilteredFundPK().longValue();

        Set investments = this.getInvestments();

		for (Iterator iterator = investments.iterator(); iterator.hasNext();) {
            Investment investment = (Investment) iterator.next();

            long filteredFundFK = investment.getFilteredFundFK().longValue();

			if (filteredFundPK == filteredFundFK) {
                isInvestment = true;

                break;
            }
        }

        return isInvestment;
    }

    /**
     * Returns this contract is spousal continuation or not.
	 * 
     * @return
     */
	public boolean isSpousalContinuation() {
        return CASETRACKINGOPTION_SPOUSALCONTINUATION.equalsIgnoreCase(this.getCasetrackingOptionCT());
    }

    /**
     * Returns the Immediate Previous Owner's ContractClient.
	 * 
     * @return
     */
	public ContractClient getImmediatePreviousOwnerContractClient() {
		return ContractClient.findBy_SegmentPK_RoleTypeCT_MaxTerminationDateAndNot12319999(this,
				ClientRole.ROLETYPECT_OWNER);
    }

    /**
     * Helper method to get are values.
	 * 
     * @param grouping
     * @param field
     * @return
     */
	private int getAreaValue(String grouping, String field) {
		Area area = new Area(segmentVO.getProductStructureFK(), segmentVO.getIssueStateCT(), grouping,
				new EDITDate(segmentVO.getEffectiveDate()), "*");

        AreaValue areaValue = area.getAreaValue(field);

        return Integer.parseInt(((AreaValueVO) areaValue.getVO()).getAreaValue());
    }

    /**
	 * Updates CommissionHistory.CommHoldReleaseDate for
	 * CommissionHistory.UpdateStatus = 'L' CommmissionHistory.CommHoldReleaseDate =
	 * [Segment.PolicyDeliveryDate] + [Free-look Days(Aread Table)] + [Hold
	 * Days(ArealTable)]
     */
	private void updateCommissionHistoryHoldReleaseDate() {
		CommissionHistoryVO[] commissionHistoryVOs = new CommissionHistoryDAO()
				.findBySegmentPK_UpdateStatus(segmentVO.getSegmentPK(), CommissionHistory.UPDATESTATUS_L);

		if (commissionHistoryVOs != null) {
            EDITDate commHoldReleaseDate = new EDITDate(segmentVO.getPolicyDeliveryDate());

			commHoldReleaseDate = commHoldReleaseDate
					.addDays(getAreaValue(AreaKey.GROUPING_FREELOOKPROCESS, AreaKey.FIELD_FREELOOKDAYS));

			commHoldReleaseDate = commHoldReleaseDate
					.addDays(getAreaValue(AreaKey.GROUPING_COMMISSION, AreaKey.FIELD_HOLDDAYS));

			for (int i = 0; i < commissionHistoryVOs.length; i++) {
                CommissionHistoryVO commissionHistoryVO = commissionHistoryVOs[i];

				if (commissionHistoryVO.getCommHoldReleaseDate() == null) {
                    commissionHistoryVO.setCommHoldReleaseDate(commHoldReleaseDate.getFormattedDate());

                    CommissionHistory commissionHistory = new CommissionHistory(commissionHistoryVO);

                    commissionHistory.save();
                }
            }
        }
    }

    /**
	 * Updates the commission history records for this contract. Queries for all
	 * commission history records for this contract with UpdateStatus =
	 * <code>updateStatus</code> and marks them either as hold(L) or delete(D) At
	 * this point of time only premium records will have CommissionHistory records
	 * with UpdateStatus = 'L' Even in the future if other transactions might have
	 * CommissionHistory records with UpdateStatus = 'L' no need to query by
	 * transaction type if this method is used for Not_Taken natural execution or
	 * reversal. Because all CommissionHistory records belonging to this contract
	 * should be updated accordingly. Since at this point of time it is used while
	 * executing the 'NT' (NotTaken) natural transaction or reversing the 'NT'
	 * (NotTaken) transaction and since transaction updates are in database
	 * transaction and are done via crud so this method expects crud connection to
	 * avoid possible dead locks. The CommissionHistory records with UpdateStatus =
	 * 'L' will be created by scripts while executing the premium transactions if
	 * they want to hold commission for large case processings. (L = Commission on
	 * Hold for Large Case Processing, D = Mark for Deleted uniquely.)
	 * 
     * @param crud
     * @param updateStatus
     */
	public void updateCommissionHistoryRecords(CRUD crud, String updateStatus) {
		CommissionHistoryVO[] commissionHistoryVOs = new CommissionHistoryDAO()
				.findBySegmentPK_UpdateStatus(segmentVO.getSegmentPK(), updateStatus, crud);

		if (commissionHistoryVOs != null) {
			for (int i = 0; i < commissionHistoryVOs.length; i++) {
                CommissionHistoryVO commissionHistoryVO = commissionHistoryVOs[i];

				if (CommissionHistory.UPDATESTATUS_L.equals(commissionHistoryVO.getUpdateStatus())) {
                    commissionHistoryVO.setUpdateStatus(CommissionHistory.UPDATESTATUS_D);
				} else if (CommissionHistory.UPDATESTATUS_D.equals(commissionHistoryVO.getUpdateStatus())) {
                    commissionHistoryVO.setUpdateStatus(CommissionHistory.UPDATESTATUS_L);
                }

				if ("Y".equals(commissionHistoryVO.getAccountingPendingStatus())) {
                    commissionHistoryVO.setAccountingPendingStatus("N");
				} else if ("N".equals(commissionHistoryVO.getAccountingPendingStatus())) {
                    commissionHistoryVO.setAccountingPendingStatus("Y");
                }

                crud.createOrUpdateVOInDB(commissionHistoryVO);
            }
        }
    }

    /**
	 * Returns the immediate previous owner. Assumtption is no two previous contract
	 * client's (of same role type) termination date is not the same and current
	 * owner's Termination Date is set to '12/31/9999'
	 * 
     * @return Client Detail of Immediate Previous Owner Contract Client
     */
	public ClientDetail getImmediatePreviousOwner() {
        return getImmediatePreviousOwnerContractClient().getClientRole().getClientDetail();
    }

  /**
	 * Returns the allowable roleTypeCT for the given rider type. Used by the user
	 * interface.
     *
     * @param riderOptionCodeCT                 rider type using the Code field from the CodeTable
     *
     * @return the proper roleTypeCT
     */
	public static String getAllowableRoleForRiderType(String riderOptionCodeCT) {
		if (riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_GUARANTEED_INSURANCE_OPTION)
				|| riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_GUARANTEED_INSURANCE_OPTION)) {
            return ClientRole.ROLETYPECT_INSURED;
		} else if (riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT) ||
				riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_OTHER_INSURED_TERM)) {
            return ClientRole.ROLETYPECT_TERM_INSURED;
		} else {
            return null;
        }
    }

    /**
	 * Returns the appropriate setting for the ContactClientAllowed XML tag based on
	 * the rider type. Used by the user interface.
     *
     * @param riderOptionCodeCT               rider type using the Code field from the CodeTable
     *
	 * @return Y or N depending whether the rider is allowed to have a
	 *         ContractClient or not
     */
	public static String getContractClientAllowedForRiderType(String riderOptionCodeCT) {
		if (riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_GUARANTEED_INSURANCE_OPTION)
				|| riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_GUARANTEED_INSURANCE_OPTION)) {
            return "N";
		} else {
            return "Y";
        }
    }

    /**
	 * Returns the appropriate setting for the GIOSelectionAllowed XML tag based on
	 * the rider type. Used by the user interface.
     *
     * @param riderOptionCodeCT             rider type using the Code field from the CodeTable
     *
	 * @return Y or N depending on whether the rider is allowed to have a GIO option
	 *         selected or not
     */                                                          
	public static String getGIOSelectionAllowedForRiderType(String riderOptionCodeCT) {
		if (riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_GUARANTEED_INSURANCE_OPTION)
				|| riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_GUARANTEED_INSURANCE_OPTION)) {
            return "Y";
		} else {
            return "N";
        }
    }

    /**
	 * Returns the appropriate setting for the AllowRiderClientSelection XML tag
	 * based on the rider type. Used by the user interface
     *
     * @param riderOptionCodeCT             rider type using the Code field form the CodeTable
     *
	 * @return Y or N depending on whether the rider is allowed to select clients or
	 *         not
     */
	public static String getAllowRiderClientSelectionForRiderType(String riderOptionCodeCT) {
		if (riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_GUARANTEED_INSURANCE_OPTION) ||
			riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_GUARANTEED_INSURANCE_OPTION) ||
			riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT) ||
			riderOptionCodeCT.equals(Segment.OPTIONCODECT_RIDER_OTHER_INSURED_TERM)) {
            return "Y";
		} else {
            return "N";
        }
    }


  /**
	 * Finder. This query assumes the existance of a BonusCommissionHistory
	 * associated with the specified ParticipatingAgent.
	 * 
   * @param bonusCommissionHistory
   * @return
   */
	public static Segment findBy_BonusCommissionHistory(BonusCommissionHistory bonusCommissionHistory) {
    Segment segment = null;
    
		String hql = " select segment " + " from BonusCommissionHistory bonusCommissionHistory"
				+ " join bonusCommissionHistory.CommissionHistory commissionHistory"
				+ " join commissionHistory.EDITTrxHistory editTrxHistory" + " join editTrxHistory.EDITTrx editTrx"
				+ " join editTrx.ClientSetup clientSetup" + " join clientSetup.ContractSetup contractSetup"
				+ " join contractSetup.Segment segment" + " where bonusCommissionHistory = :bonusCommissionHistory";
                  
    Map params = new HashMap();
    
    params.put("bonusCommissionHistory", bonusCommissionHistory);
                  
    List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
    
		if (!results.isEmpty()) {
      segment = (Segment) results.get(0);            
    }
    
    return segment;
  }

  /**
   * Finder.
	 * 
   * @param editTrx
   * @return
   */
	public static Segment findBy_EDITTrx(EDITTrx editTrx) {
    Segment segment = null;
    
		String hql = " select segment" + " from EDITTrx editTrx" + " join editTrx.ClientSetup clientSetup"
				+ " join clientSetup.ContractSetup contractSetup" + " join contractSetup.Segment segment"
				+ " where editTrx = :editTrx";
    
    Map params = new HashMap();
    
    params.put("editTrx", editTrx);
    
    List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
    
		if (!results.isEmpty()) {
      segment = (Segment) results.get(0);
    }
    
    return segment;
  }
  
  /**
   * Finder.
	 * 
   * @param editTrxPK
   * @return
   */
	public static Segment findBy_EDITTrxPK(Long editTrxPK) {
    Segment segment = null;
    
		String hql = " select segment" + " from EDITTrx editTrx" + " join editTrx.ClientSetup clientSetup"
				+ " join clientSetup.ContractSetup contractSetup" + " join contractSetup.Segment segment"
				+ " where editTrxPK = :editTrxPK";
    
    Map params = new HashMap();
    
    params.put("editTrxPK", editTrxPK);
    
    List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
    
		if (!results.isEmpty()) {
      segment = (Segment) results.get(0);
    }
    
    return segment;
  }
  
  /**
   * Finder. Retrieves the following structure:
   * 
	 * Segment Segment.Investment Segment.Investment.Bucket
	 * 
   * @param editTrx
   * @return
   */
	public static Segment findSeparateBy_EDITTrx_V1(EDITTrx editTrx) {
    Segment segment = null;
    
		String hql = " select segment" + " from EDITTrx editTrx" + " join editTrx.ClientSetup clientSetup"
				+ " join clientSetup.ContractSetup contractSetup" + " join contractSetup.Segment segment"
				+ " join fetch segment.Investments investment" + " left join fetch investment.Buckets"
				+ " where editTrx = :editTrx";
    
    Map params = new HashMap();
    
    params.put("editTrx", editTrx);
    
    Session session = null;
    
		try {
      session = SessionHelper.getSeparateSession(Segment.DATABASE);
      
      List<Segment> results = SessionHelper.executeHQL(session, hql, params, 0);
      
			if (!results.isEmpty()) {
        segment = results.get(0);
      }
		} finally {
			if (session != null)
				session.close();
    }
    
    return segment;
  }  

    /**
     * Find all Rider Segments for the given SegmentFK
	 * 
     * @param editTrx
     * @return
     */
	public static Segment[] findBy_SegmentFK(Long segmentFK) {
      String hql = "Select s from Segment s where s.SegmentFK = :segmentFK";

      Map params = new HashMap();

      params.put("segmentFK", segmentFK);

      List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

      return (Segment[]) results.toArray(new Segment[results.size()]);
    }
    
    /**
   * Finder.
	 * 
   * @param commissionHistoryPK
   * @return
   */
	public static Segment findBy_CommissionHistoryPK(Long commissionHistoryPK) {
        Segment segment = null;
    
		String hql = " select segment" + " from Segment segment" + " join segment.ContractSetups contractSetup"
				+ " join contractSetup.ClientSetups clientSetup" + " join clientSetup.EDITTrxs editTrx"
				+ " join editTrx.EDITTrxHistories editTrxHistories"
				+ " join editTrxHistories.CommissionHistories commissionHistory"
				+ " where commissionHistory.CommissionHistoryPK = :commissionHistoryPK";
                      
        Map params = new HashMap();                      
        
        params.put("commissionHistoryPK", commissionHistoryPK);
        
        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
        
		if (!results.isEmpty()) {
          segment = (Segment) results.get(0);
        }
        
        return segment;
    }

	public ClientDetail getSecondaryAnnuitant() {
       ClientDetail secondaryAnnuitant = null;

		if (isPayoutProduct()) {
            ContractClient secondaryContractClientAnnuitant = getSANContractClient();
			if (secondaryContractClientAnnuitant != null) {
                secondaryAnnuitant = secondaryContractClientAnnuitant.getClientRole().getClientDetail();
            }
        }

        return secondaryAnnuitant;

    }

	public ContractClient getSANContractClient() {
        ContractClient secondayAnnContractClient = null;

		if (isPayoutProduct()) {
			secondayAnnContractClient = ContractClient.findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(this,
					ClientRole.ROLETYPECT_SECONDARY_ANNUITANT);
        }

        return secondayAnnContractClient;
	}

	public String setEventTypeForDriverScript() {
        String eventType = null;

		if (this.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_PAYOUT)) {
            eventType = this.getOptionCodeCT();
		} else {
			if (this.getIssueStateCT() == null) {
                eventType = "*";
			} else {
                eventType = this.getIssueStateCT();
            }
        }

        return eventType;
    }

	public String setEventTypeForValidationScript() {
         String eventType = null;

		if (this.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_PAYOUT)) {
            eventType = this.getOptionCodeCT();
		} else {
            eventType = "*";
        }

        return eventType;
    }

  /**
	 * Builds an aggregate Segment as follows in its [own] Hiberate Session: Segment
	 * Segment.AgentHierarchy Segment.AgentHierachy.AgentSnapshot
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.PlacedAgentCommissionProfile
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.PlacedAgentCommissionProfile.CommissionProfile
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.AdditionalCompensation
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.CheckAdjustment
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole.ClientRoleFinancial
   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole.ClientDetail
   * 
	 * PlacedAgentCommissionProfile is filtered by
	 * PlacedAgentCommissionProfile.StartDate <= TrxEffectiveDate <=
	 * PlacedAgentCommissionProfile.StopDate.
   * 
	 * CheckAdjustment is filtered as CheckAdjustment.StartDate < SystemDate <
	 * CheckAdjustment.StopDate AND CheckAdjustment.AdjustmentCompleteInd <> 'Y'.
   * 
   * ClientRole is filtered by ClientRole.RoleTypeCT = 'Agent'.
	 * 
   * @param segmentPK
   * @return
   */
	public static Segment findSeparateBy_SegmentPK_V1(Long segmentPK, EDITDate trxEffectiveDate) {
    Segment segment = null;
  
		String hql = " select segment from Segment segment" + " join fetch segment.AgentHierarchies agentHierarchy"
				+ " join fetch agentHierarchy.AgentSnapshots agentSnapshot"
				+ " join fetch agentSnapshot.PlacedAgent placedAgent"
				+ " join fetch placedAgent.PlacedAgentCommissionProfiles placedAgentCommissionProfile"
				+ " join fetch placedAgentCommissionProfile.CommissionProfile commissionProfile"
				+ " join fetch placedAgent.AgentContract agentContract"
				+ " left join fetch agentContract.AdditionalCompensations" + " join fetch agentContract.Agent agent"
				+ " left join fetch agent.CheckAdjustments checkAdjustment"
				+ " join fetch placedAgent.ClientRole clientRole" + " join fetch clientRole.ClientRoleFinancials"
				+ " join fetch clientRole.ClientDetail" + " where segment.SegmentPK = :segmentPK"
				+ " and placedAgentCommissionProfile.StartDate <= :trxEffectiveDate"
				+ " and placedAgentCommissionProfile.StopDate >= :trxEffectiveDate"
				+ " and clientRole.RoleTypeCT = 'Agent'";

		EDITMap params = new EDITMap().put("segmentPK", segmentPK).put("trxEffectiveDate", trxEffectiveDate);
                
    List<Segment> results = null;

    Session session = null;
    
		try {
      session = SessionHelper.getSeparateSession(Segment.DATABASE);
    
      results = SessionHelper.executeHQL(session, hql, params, 0);
      
			if (!results.isEmpty()) {
        segment = results.get(0);
      }
		} finally {
			if (session != null)
				session.close();
    }
    
    return segment;
  }
	

	  /**
		 * Builds rider Segments as follows in its [own] Hiberate Session: Segment
		 * Segment.AgentHierarchy Segment.AgentHierachy.AgentSnapshot
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.PlacedAgentCommissionProfile
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.PlacedAgentCommissionProfile.CommissionProfile
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.AdditionalCompensation
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.CheckAdjustment
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole.ClientRoleFinancial
	   * Segment.AgentHierachy.AgentSnapshot.PlacedAgent.AgentContract.Agent.ClientRole.ClientDetail
	   * 
		 * PlacedAgentCommissionProfile is filtered by
		 * PlacedAgentCommissionProfile.StartDate <= TrxEffectiveDate <=
		 * PlacedAgentCommissionProfile.StopDate.
	   * 
		 * CheckAdjustment is filtered as CheckAdjustment.StartDate < SystemDate <
		 * CheckAdjustment.StopDate AND CheckAdjustment.AdjustmentCompleteInd <> 'Y'.
	   * 
	   * ClientRole is filtered by ClientRole.RoleTypeCT = 'Agent'.
		 * 
	   * @param segmentFK
	   * @return
	   */
		public static List<Segment> findSeparateBy_SegmentFK_V1(Long segmentFK, EDITDate trxEffectiveDate) {
	  
			String hql = " select distinct segment from Segment segment" + " join fetch segment.AgentHierarchies agentHierarchy"
					+ " join fetch agentHierarchy.AgentSnapshots agentSnapshot"
					+ " join fetch agentSnapshot.PlacedAgent placedAgent"
					+ " join fetch placedAgent.PlacedAgentCommissionProfiles placedAgentCommissionProfile"
					+ " join fetch placedAgentCommissionProfile.CommissionProfile commissionProfile"
					+ " join fetch placedAgent.AgentContract agentContract"
					+ " left join fetch agentContract.AdditionalCompensations" + " join fetch agentContract.Agent agent"
					+ " left join fetch agent.CheckAdjustments checkAdjustment"
					+ " join fetch placedAgent.ClientRole clientRole" + " join fetch clientRole.ClientRoleFinancials"
					+ " join fetch clientRole.ClientDetail" + " where segment.SegmentFK = :segmentFK"
					+ " and placedAgentCommissionProfile.StartDate <= :trxEffectiveDate"
					+ " and placedAgentCommissionProfile.StopDate >= :trxEffectiveDate"
					+ " and clientRole.RoleTypeCT = 'Agent'";

		EDITMap params = new EDITMap().put("segmentFK", segmentFK).put("trxEffectiveDate", trxEffectiveDate);
	                
	    List<Segment> results = null;

	    Session session = null;
	    
		try {
	      session = SessionHelper.getSeparateSession(Segment.DATABASE);
	    
	      results = SessionHelper.executeHQL(session, hql, params, 0);
	      Segment[] segment = results.toArray(new Segment[results.size()]);
	      
		} finally {
			if (session != null)
				session.close();
		}
	    
	    return results;
	  }

  /**
	 * Finds the associated SegmentPK (using a separate Hibernate Session) from the
	 * specified EDITTrxPK.
	 * 
   * @param editTrxPK
   * @return the associated SegmentPK
   */
	public static Long findSeparateBy_EDITTrxPK(Long editTrxPK) {
    Long segmentPK = null;
  
		String hql = " select contractSetup.SegmentFK" + " from EDITTrx editTrx"
				+ " join editTrx.ClientSetup clientSetup" + " join clientSetup.ContractSetup contractSetup"
				+ " where editTrx.EDITTrxPK = :editTrxPK";
                
    EDITMap params = new EDITMap("editTrxPK", editTrxPK);
    
    Session session = null;
    
		try {
      List<Long> results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
      
			if (!results.isEmpty()) {
        segmentPK = results.get(0);
      }
            
		} finally {
			if (session != null)
				session.close();
    }
    
    return segmentPK;
  }
  
  /**
   * Adder.
	 * 
   * @param contractTreaty
   */
	public void add(ContractTreaty contractTreaty) {
    getContractTreaties().add(contractTreaty);
    
    contractTreaty.setSegment(this);
  }
  
  /**
	 * Finder. Builds the following structure: Segment
	 * Segment.RequiredMinDistribution Segment.Payout Segment.Deposit Segment.Life
	 * 
   * @param segmentPK
   * @return
   */
	public static Segment findSeparateBy_SegmentPK_VONE(Long segmentPK) {
    Segment segment = null;
  
		String hql = " select segment" + " from Segment segment" + " left join fetch segment.RequiredMinDistributions"
				+ " left join fetch segment.Payouts" + " left join fetch segment.Deposits"
				+ " left join fetch segment.Lifes" + " left join fetch segment.BillSchedule billSchedule"
				+ " left join fetch segment.InherentRiders inherentRider" + " left join fetch segment.Segments segments"
				+ " left join fetch segments.ValueAtIssues" + " where segment.SegmentPK = :segmentPK";
                
    EDITMap params = new EDITMap("segmentPK", segmentPK);
    
    List<Segment> results = null;
    
    Session session = null;
    
		try {
      session = SessionHelper.getSeparateSession(Segment.DATABASE);
    
      results = SessionHelper.executeHQL(session, hql, params, 0);
      
			if (!results.isEmpty()) {
        segment = results.get(0);
      }      
		} finally {
			if (session != null)
				session.close();
    }
                
    return segment;                
  }
  
    /**
	 * Finder. Builds the following structure: Segment
	 * Segment.RequiredMinDistribution Segment.Payout Segment.Deposit Segment.Life
     * Segment.PremiumDue.CommissionPhase.CommissionablePremiumHistory
	 * 
     * @param segmentPK
     * @return
     */
	public static Segment findSeparateBy_SegmentPK_VTWO(Long segmentPK) {
      Segment segment = null;
    
		String hql = " select segment" + " from Segment segment" + " left join fetch segment.RequiredMinDistributions"
				+ " left join fetch segment.Payouts" + " left join fetch segment.Deposits"
				+ " left join fetch segment.Lifes" + " left join fetch segment.PremiumDues premiumDue"
				+ " left join fetch premiumDue.CommissionPhases commissionPhase"
				+ " left join fetch commissionPhase.CommissionablePremiumHistories"
				+ " left join fetch segment.BillSchedule billSchedule"
				+ " left join fetch segment.InherentRiders inherentRider" + " left join fetch segment.Segments segments"
				+ " left join fetch segments.ValueAtIssues" + " where segment.SegmentPK = :segmentPK";
                  
      EDITMap params = new EDITMap("segmentPK", segmentPK);
      
      List<Segment> results = null;
      
      Session session = null;
      
		try {
        session = SessionHelper.getSeparateSession(Segment.DATABASE);
      
        results = SessionHelper.executeHQL(session, hql, params, 0);
        
			if (!results.isEmpty()) {
          segment = results.get(0);
        }      
		} finally {
			if (session != null)
				session.close();
      }
                  
      return segment;                
    }  
  
  /**
	 * Finder. Builds the following structure: Segment
	 * Segment.RequiredMinDistribution Segment.Payout Segment.Deposit Segment.Life
	 * 
   * @param segmentPK
   * @return
   */
	public static Segment findSeparateBy_SegmentPK(Long segmentPK) {
    Segment segment = null;
  
		String hql = " select segment" + " from Segment segment" + " where segment.SegmentPK = :segmentPK";
                
    EDITMap params = new EDITMap("segmentPK", segmentPK);
    
    List<Segment> results = null;
    
    Session session = null;
    
		try {
      session = SessionHelper.getSeparateSession(Segment.DATABASE);
    
      results = SessionHelper.executeHQL(session, hql, params, 0);
      
			if (!results.isEmpty()) {
        segment = results.get(0);
      }      
		} finally {
			if (session != null)
				session.close();
    }
                
    return segment;                
  }  
  
  /**
	 * Finder. Note that a [separate] Hibernate Session is used. Builds the
	 * following structure: Segment Segment.ContractSetup
	 * Segment.ContractSetup.ClientSetup Segment.ContractSetup.ClientSetup.EDITTrx
   * Segment.ContractSetup.InvestmentAllocationOverride
	 * 
   * @param editTrxPK
   * @return
   */
	public static Segment findSeparateBy_EDITTrxPK_V2(Long editTrxPK) {
    Segment segment = null;
    
		String hql = " select segment" + " from Segment segment" +
                 
				" join fetch segment.ContractSetups contractSetup"
				+ " join fetch contractSetup.ClientSetups clientSetup" + " join fetch clientSetup.EDITTrxs editTrx1" +
                  
				" join fetch segment.Investments investment"
				+ " join fetch investment.InvestmentAllocations investmentAllocation"
				+ " left join investment.InvestmentAllocationOverrides investmentAllocationOverride" +
                 
                  " where editTrx1.EDITTrxPK = :editTrxPK";
                  
//                  " and investmentAllocationOverride in (" +
//                  " select investmentAllocationOverride from InvestmentAllocationOverride investmentAllocationOverride" +
//                  " join investmentAllocationOverride.ContractSetup contractSetup" +
//                  " join contractSetup.ClientSetups clientSetup" +
//                  " join clientSetup.EDITTrxs editTrx" +
//                  " where editTrx.EDITTrxPK = :editTrxPK)";
                  
                
                    EDITMap params = new EDITMap("editTrxPK", editTrxPK);
    
    Session session = null;
    
		try {
      List<Segment> results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

			if (!results.isEmpty()) {
        segment = results.get(0);// doesn't matter which one
      }      
		} finally {
			if (session != null)
				session.close();
    }
                
    return segment;                
  }
  
	public String getDatabase() {
        return Segment.DATABASE;
    }

    /**
     * Sets the default values for this Segment
     */
	public void setDefaults() {
        this.setAmount(new EDITBigDecimal());
        this.setCostBasis(new EDITBigDecimal());
        this.setRecoveredCostBasis(new EDITBigDecimal());
        this.setCharges(new EDITBigDecimal());
        this.setLoads(new EDITBigDecimal());
        this.setFees(new EDITBigDecimal());
        this.setFreeAmountRemaining(new EDITBigDecimal());
        this.setFreeAmount(new EDITBigDecimal());
        this.setAnnualInsuranceAmount(new EDITBigDecimal());
        this.setAnnualInvestmentAmount(new EDITBigDecimal());
        this.setSavingsPercent(new EDITBigDecimal());
        this.setDismembermentPercent(new EDITBigDecimal());
        this.setDialableSalesLoadPercentage(new EDITBigDecimal());
        this.setChargeDeductAmount(new EDITBigDecimal());
        this.setCommitmentAmount(new EDITBigDecimal());
        this.setDateOfDeathValue(new EDITBigDecimal());
        this.setAnnuitizationValue(new EDITBigDecimal());
        this.setSettlementAmount(new EDITBigDecimal());

        this.setFreeLookDaysOverride(0);
        this.setRiderNumber(0);
        this.setTotalActiveBeneficiaries(0);
        this.setRemainingBeneficiaries(0);
        this.setConsecutiveAPLCount(0);

        this.setExchangeInd(Segment.INDICATOR_NO);
        this.setCashWithAppInd(Segment.INDICATOR_NO);
        this.setWaiverInEffect(Segment.INDICATOR_NO);
        this.setWaiveFreeLookIndicator(Segment.INDICATOR_NO);

        this.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
        this.setQuoteDate(new EDITDate());

        this.setCreationDate(new EDITDate());
    }

    /**
	 * Generates the contractNumber based on values set in the Company. The Company
	 * is passed in because a brand new Segment may not be associated with a Company
	 * yet.
     * <P>
	 * The contractNumber is the Company's policyNumberPrefix + latest sequence
	 * number. The sequence number is then pre-padded with zeroes to the length
	 * specified by the Company's policySequenceLength.
     *
	 * @param company Company which contains criteria for generating a
	 *                contractNumber
     *
	 * @throws EDITCaseException if the generated contract number already exists (a
	 *                           user may have assigned the same number is a
	 *                           previous submission or there may have been a
	 *                           conflict in timing when getting/changing the
	 *                           Company's sequence number).
     */
	public void autoGenerateGroupContractNumber(Company company) throws EDITCaseException {
        String prefix = company.getPolicyNumberPrefix();
        int sequenceNumber = company.determineLatestPolicyNumberSequenceNumber();
        int sequenceLength = company.getPolicySequenceLength();
        String suffix = Util.initString(company.getPolicyNumberSuffix(), "");

        String paddedSequenceNumber = Util.padWithZero(sequenceNumber + "", sequenceLength);

        String contractNumber = prefix + paddedSequenceNumber + suffix;

		if (Segment.contractNumberExists(contractNumber)) {
			throw new EDITCaseException("Auto generated contract number " + contractNumber
					+ " already exists.  Change Company's PolicyNumberSequenceNumber");
		} else {
            this.setContractNumber(contractNumber);

			// Save the Company because it's sequenceNumber has been changed. It is in a
			// different database so it
			// needs to be saved in a separate transaction and right away before another
			// user gets the same
            //  sequenceNumber.
            company.saveToDBImmediately();
        }
    }

    /**
	 * Changes the Segment's billing from List (group) billing to Direct
	 * (individual) billing. It first checks to see if the change is allowed based
	 * on information in the AreaValue table. If it change is allowed, a new
	 * BillSchedule is created with the same values as the existing List bill and
	 * the new BillSchedule's billMethod is changed to the passed in value. The
	 * Segment is then changed to point to the new individual BillSchedule.
     * <P>
	 * NOTE: A Billing Change trx will be created for this process but not until the
	 * "big save" of the Segment (don't want to create a trx if the change was
	 * undone). The trx is created regardless of whether the areaValue allowed the
     * bill change to occur or not.
     *
     * @param billMethodCT                  the type of billing to change to
     *
     * @return true if the billing was changed, false otherwise
     */
	public boolean changeToIndividualBilling(String billMethodCT, String operator, String areaValue)
			throws EDITEventException {
        boolean changedBilling = false;

		if (shouldChangeToIndividualBill(areaValue)) {
            BillSchedule individualBillSchedule = this.getBillSchedule().createIndividualBill(billMethodCT);

            this.setBillSchedule(individualBillSchedule);

			// We are essentially moving the Segment out of the ContractGroup when we go to
			// individual billing
			// Therefore, set the ContractGroup to null and preserve it in
			// priorContractGroup
            this.setPriorContractGroup(this.getContractGroup());
            this.setContractGroup(null);

            changedBilling = true;

            SessionHelper.saveOrUpdate(this, Segment.DATABASE);
        }

		// Set the BillScheduleChangeType to the areaValue. The save script uses this to
		// determine whether it
        //  should create a BC trx and, if so, what its complex change type should be
        this.setBillScheduleChangeType(areaValue);

        return changedBilling;
    }

    /**
	 * Determines if a BillSchedule should be changed to an individual bill or not
	 * based on the areaValue. If the AreaValue is set to conversion, shouldn't
	 * change the billing.
     *
     * @return  false if the AreaValue is a conversion, true otherwise
     */
	private boolean shouldChangeToIndividualBill(String areaValue) {
        String value = "CONV";      // conversion

		if (areaValue.equals(value)) {
            return false;
		} else {
            return true;
        }
    }

    /**
	 * Changes the Segment's billing from individual billing to List (group) billing
	 * or from List to a different List. The BillScheduleFK is changed to point to
	 * the Group ContractGroup's BillSchedule and the DepartmentLocationFK is
	 * changed to point to the specified one.
     * <P>
	 * NOTE: A Billing Change trx will be created for this process but not until the
	 * "big save" of the Segment (don't want to create a trx if the change was
	 * undone).
     *
     *
	 * @param contractGroupNumber  number of the Group ContractGroup to associate
	 *                             the Segment with
	 * @param departmentLocationPK pk of the DepartmentLocation to associate the
	 *                             Segment with
     *
     * @throws EDITEventException
     */
	public void changeToListBilling(String contractGroupNumber, Long departmentLocationPK) throws EDITEventException {
		ContractGroup newContractGroup = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(
				contractGroupNumber, ContractGroup.CONTRACTGROUPTYPECT_GROUP);

		if (newContractGroup == null) {
			throw new EDITEventException("Group ContractGroupNumber " + contractGroupNumber
					+ " does not exist.  Change to List billing failed");
		} else {
            ContractGroup currentContractGroup = this.getContractGroup();

			if (currentContractGroup != null) {
                //  This is a List-to-List change, set the priorPRDDue to yes
                this.setPriorPRDDue("Y");
            }

            this.setPriorContractGroup(currentContractGroup);     // set to "current" ContractGroup
            this.setContractGroup(newContractGroup);               // replace with new ContractGroup
            this.setBillSchedule(newContractGroup.getBillSchedule());

            //  A null DepartmentLocation is valid, the user is not forced to select one
			if (departmentLocationPK == null) {
                this.setDepartmentLocation(null);
			} else {
				DepartmentLocation departmentLocation = DepartmentLocation
						.findBy_DepartmentLocationPK(departmentLocationPK);
                this.setDepartmentLocation(departmentLocation);
            }

			// Set the BillScheduleChangeType to the List ComplexChangeType. The save script
			// uses this to determine
			// whether it should create a BC trx and, if so, what its complex change type
			// should be
            this.setBillScheduleChangeType(ContractSetup.COMPLEXCHANGETYPECT_LIST);

            SessionHelper.saveOrUpdate(this, Segment.DATABASE);
        }
    }

    /**
     * Finder that returns a composite structure as:
     * 
     * Segment.ContractClient.ClientRole
	 * 
     * @param clientDetailPK
     * @return
     */
	public static Segment[] findBy_ClientDetailPK_V1(Long clientDetailPK) {
		String hql = " select segment" + " from Segment segment" + " join fetch segment.ContractClients contractClient"
				+ " join fetch contractClient.ClientRole clientRole"
				+ " where clientRole.ClientDetailFK = :clientDetailPK";
                    
        EDITMap params = new EDITMap("clientDetailPK", clientDetailPK);
        
		List<Segment> results = SessionHelper
				.makeUnique(SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS));

        return results.toArray(new Segment[results.size()]);
    }

    /**
     * Finder by BatchContractSetupFK
	 * 
     * @param batchContractSetupFK
     * @return
     */
	public static Segment[] findBy_BatchContractSetupFK(Long batchContractSetupFK) {
		String hql = " select segment" + " from Segment segment"
				+ " where segment.BatchContractSetupFK = :batchContractSetupFK";

        EDITMap params = new EDITMap("batchContractSetupFK", batchContractSetupFK);

        List<Segment> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return results.toArray(new Segment[results.size()]);
    }

    /**
     * Finder that returns segments associated with a billSchedule
     *
     * @param billScheduleFK
     * @return
     */
	public static Segment[] findBy_BillScheduleFK(Long billScheduleFK) {
		String hql = " select segment from Segment segment" + " where segment.BillScheduleFK = :billScheduleFK"
				+ " and segment.SegmentFK is null";

        EDITMap params = new EDITMap("billScheduleFK", billScheduleFK);

        List<Segment> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return results.toArray(new Segment[results.size()]);
    }

	public static Segment[] findBy_BillScheduleFK_2(Long billScheduleFK) {
		String hql = " select segment from Segment segment" + " where segment.BillScheduleFK = :billScheduleFK"
				+ " and SegmentStatusCT = 'Active' and segment.SegmentFK is null";

        EDITMap params = new EDITMap("billScheduleFK", billScheduleFK);

        List<Segment> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return results.toArray(new Segment[results.size()]);
    }	
	
	
    public static Long[] findByBillSched_Case_Group_CreationDate(Long billScheduleFK, Long[] contractGroupFKs,
			EDITDate effDate) {
		String hql = "select segment.SegmentPK from Segment segment "
				+ " where segment.BillScheduleFK = :billScheduleFK" + " and segment.SegmentFK is null"
				+ " and segment.CreationDate <= :effDate";

        Map params = new HashMap();
        params.put("billScheduleFK", billScheduleFK);
        params.put("effDate", effDate);

		if (contractGroupFKs.length > 0) {
            hql = hql + " and segment.ContractGroupFK in (:contractGroupFKs)";

            params.put("contractGroupFKs", contractGroupFKs);
        }

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);
//        List resultsToReturn = new ArrayList();
//        for (int i = 0; i < results.size(); i++)
//        {
//            Segment segment = (Segment) results.get(i);
//            if (segment.getSegmentFK() == null)
//            {
//                resultsToReturn.add(segment);
//            }
//        }
        return (Long[]) results.toArray(new Long[results.size()]); 
		// return (Segment[]) resultsToReturn.toArray(new
		// Segment[resultsToReturn.size()]);
    }

    /**
     * Finder that returns segments where WorksheetTypeCT is not null
     *
     * @return
     */
	public static Segment[] findForWorksheet(String batchId) {
        String hql = null;

        Map params = new HashMap();
        params.put("worksheetTypes", Segment.WORKSHEETTYPES);

		if (batchId != null && !batchId.equals("") && !batchId.toUpperCase().equals("ALL")) {
			hql = " select segment from Segment segment" + " join segment.BatchContractSetup batchContractSetup"
					+ " where segment.BatchContractSetupFK = (select BatchContractSetupPK from batchContractSetup"
					+ " where batchContractSetup.BatchID = :batchId)"
					+ " and segment.WorksheetTypeCT in (:worksheetTypes)";

            params.put("batchId", batchId);
		} else {
			hql = " select segment from Segment segment" + " where segment.WorksheetTypeCT in (:worksheetTypes)";
        }

        List<Segment> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return results.toArray(new Segment[results.size()]);
    }

	public StagingContext stage(StagingContext stagingContext, String database) {
		if (stagingContext.getSegmentType().equalsIgnoreCase("Base")) {
            Life life = null;
			if (this.getLife() == null) {
                life = Life.findSeparateBy_SegmentPK(this.getSegmentPK());
			} else {
                life = this.getLife();
            }

            SegmentBase segmentBase = new SegmentBase();
            segmentBase.setGroup(stagingContext.getCurrentGroup());
            segmentBase.setStaging(stagingContext.getStaging());
            segmentBase.setBillSchedule(stagingContext.getCurrentBillSchedule());
            segmentBase.setContractNumber(this.getContractNumber());
            segmentBase.setEffectiveDate(this.getEffectiveDate());
            segmentBase.setTerminationDate(this.getTerminationDate());
            segmentBase.setSegmentStatus(this.getSegmentStatusCT());
            segmentBase.setOptionCode(this.getOptionCodeCT());
            segmentBase.setRatedGenderCT(this.getRatedGenderCT());
            segmentBase.setUnderwritingClassCT(this.getUnderwritingClassCT());
            segmentBase.setGroupPlan(this.getGroupPlan());
            segmentBase.setIssueState(this.getIssueStateCT());
            segmentBase.setOriginalState(this.getOriginalStateCT());
            segmentBase.setIssueDate(this.getIssueDate());
            segmentBase.setUnits(this.getUnits());
            segmentBase.setApplicationSignedDate(this.getApplicationSignedDate());
            EDITDate lastAnniversaryDate = this.getLastAnniversaryDate();
			if (lastAnniversaryDate == null) {
                lastAnniversaryDate = this.getEffectiveDate();
            }

            EDITDate nextAnniversaryDate = lastAnniversaryDate.addYears(1);
            segmentBase.setNextAnniversaryDate(nextAnniversaryDate);
            segmentBase.setLastAnniversaryDate(lastAnniversaryDate);
			if (life != null) {
                segmentBase.setFaceAmount(life.getFaceAmount());
                segmentBase.setPaidToDate(life.getPaidToDate());
                segmentBase.setDeathBenefitOption(life.getDeathBenefitOptionCT());
                segmentBase.setGuarPaidUpTerm(life.getGuarPaidUpTerm());
                segmentBase.setNonGuarPaidUpTerm(life.getNonGuarPaidUpTerm());
                segmentBase.setOneYearTerm(life.getOneYearTerm());
                segmentBase.setLapseDate(life.getLapseDate());
                segmentBase.setNonForfeitureOption(life.getNonForfeitureOptionCT());
            }

            MasterContract masterContract = this.getMasterContract();
			if (masterContract != null) {
                segmentBase.setMasterContractName(masterContract.getMasterContractName());
                segmentBase.setMasterContractNumber(masterContract.getMasterContractNumber());
                segmentBase.setMasterContractEffectiveDate(masterContract.getMasterContractEffectiveDate());
                segmentBase.setNoInterimCoverage(masterContract.isNoInterimCoverage());
                segmentBase.setStateCT(masterContract.getStateCT());
                segmentBase.setBrandingCompanyCT(masterContract.getBrandingCompanyCT());
            }

            segmentBase.setAnnualPremium(this.getAnnualPremium());

			ProductStructure productStructure = ProductStructure
					.findBy_ProductStructurePK_V1(this.getProductStructureFK());
            segmentBase.setCompanyName(productStructure.getCompanyName());
            segmentBase.setMarketingPackage(productStructure.getMarketingPackageName());
            segmentBase.setGroupName(productStructure.getGroupProductName());
            segmentBase.setArea(productStructure.getAreaName());
            segmentBase.setBusinessContract(productStructure.getBusinessContractName());
			if (productStructure.getExternalProductName() != null
					&& productStructure.getExternalProductName().length() > 20) {
                segmentBase.setMajorLine(productStructure.getExternalProductName().substring(0, 20));   
			} else {
                segmentBase.setMajorLine(productStructure.getExternalProductName());
            }

            segmentBase.setGroupType(productStructure.getGroupTypeCT());
            segmentBase.setAgeAtIssue(this.getAgeAtIssue());
            segmentBase.setConversionDate(this.getConversionDate());
            Company company = productStructure.getCompany();
            segmentBase.setBillingCompanyNumber(company.getBillingCompanyNumber());
            segmentBase.setEstateOfTheInsured(this.getEstateOfTheInsured());

            String batchID = null;

			if (this.getBatchContractSetupFK() != null) {
                BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(this.getBatchContractSetupFK());
                segmentBase.setBatchID(batchContractSetup.getBatchID());
                batchID = batchContractSetup.getBatchID();

				if (stagingContext.getCurrentCase() != null) {
                    Set<staging.Enrollment> stagingEnrollments = stagingContext.getCurrentCase().getEnrollments();
                    Iterator it = stagingEnrollments.iterator();
					while (it.hasNext()) {
                        staging.Enrollment enrollment = (staging.Enrollment) it.next();
						Set<staging.BatchContractSetup> stagingBatchContractSetups = enrollment
								.getBatchContractSetups();
                        Iterator it2 = stagingBatchContractSetups.iterator();
						while (it2.hasNext()) {
                            staging.BatchContractSetup bcs = (staging.BatchContractSetup) it2.next();
							if (bcs.getBatchID().equalsIgnoreCase(batchID)) {
                                segmentBase.setBatchContractSetup(bcs);
                                //bcs.addSegmentBase(segmentBase);
                            }
                        }
                    }
                }
            }

            DepartmentLocation deptLoc = this.getDepartmentLocation();
			if (deptLoc != null) {
                segmentBase.setDeptLocName(deptLoc.getDeptLocName());
                segmentBase.setDeptLocNumber(deptLoc.getDeptLocCode());
            }

            segmentBase.setWorksheetType(this.getWorksheetTypeCT());
            segmentBase.setCreationOperator(this.getCreationOperator());
            segmentBase.setClientUpdate(this.getClientUpdate());
            segmentBase.setRequirementEffectiveDate(this.getRequirementEffectiveDate());
            segmentBase.setFirstNotifyDate(this.getFirstNotifyDate());
            segmentBase.setPreviousNotifyDate(this.getPreviousNotifyDate());
            segmentBase.setFinalNotifyDate(this.getFinalNotifyDate());

			EDITTrx[] submitTrx = EDITTrx.findBy_TransactionType_SegmentPK_PendingStatus(
					EDITTrx.TRANSACTIONTYPECT_SUBMIT, this.getSegmentPK(), EDITTrx.PENDINGSTATUS_HISTORY);
			if (submitTrx != null) {
				for (int i = 0; i < submitTrx.length; i++) {
					if (submitTrx[i].getStatus().equalsIgnoreCase(EDITTrx.STATUS_APPLY)
							|| submitTrx[i].getStatus().equalsIgnoreCase(EDITTrx.STATUS_NATURAL)) {
                        segmentBase.setSubmitDate(submitTrx[i].getEffectiveDate());
                    }
                }
            }

			if (stagingContext.getCurrentGroup() != null) {
                //stagingContext.getCurrentGroup().addSegmentBase(segmentBase);
                segmentBase.setGroup(stagingContext.getCurrentGroup());
                
            }

            //stagingContext.getStaging().addSegmentBase(segmentBase);
            segmentBase.setStaging(stagingContext.getStaging());
            stagingContext.setCurrentSegmentBase(segmentBase);

            SessionHelper.saveOrUpdate(segmentBase, database);
		} else {
            Life life = null;
			if (this.getLife() == null) {
                life = Life.findSeparateBy_SegmentPK(this.getSegmentPK());
			} else {
                life = this.getLife();
            }

            SegmentRider segmentRider = new SegmentRider();
            segmentRider.setSegmentBase(stagingContext.getCurrentSegmentBase());
            segmentRider.setEffectiveDate(this.getEffectiveDate());
            segmentRider.setTerminationDate(this.getTerminationDate());
            segmentRider.setSegmentStatus(this.getSegmentStatusCT());
            segmentRider.setOptionCode(this.getOptionCodeCT());
            segmentRider.setRatedGenderCT(this.getRatedGenderCT());
            segmentRider.setUnderwritingClassCT(this.getUnderwritingClassCT());
            segmentRider.setGroupPlan(this.getGroupPlan());
            segmentRider.setUnits(this.getUnits());
            segmentRider.setFaceAmount(this.getAmount());
            segmentRider.setPaidToDate(stagingContext.getCurrentSegmentBase().getPaidToDate());
            segmentRider.setAnnualPremium(this.getAnnualPremium());
            segmentRider.setAgeAtIssue(this.getAgeAtIssue());

            segmentRider.setEOBCum(this.getEOBCum());
            segmentRider.setEOBMaximum(this.getEOBMaximum());
            segmentRider.setMultipleFactor(this.getEOBMultiple());
            segmentRider.setGIOOption(this.getGIOOption());

			if (life != null) {
                segmentRider.setGuarPaidUpTerm(life.getGuarPaidUpTerm());
                segmentRider.setNonGuarPaidUpTerm(life.getNonGuarPaidUpTerm());
                segmentRider.setOneYearTerm(life.getOneYearTerm());
            }

            //stagingContext.getCurrentSegmentBase().addSegmentRider(segmentRider);
            segmentRider.setSegmentBase(stagingContext.getCurrentSegmentBase());

            stagingContext.setCurrentSegmentRider(segmentRider);

            SessionHelper.saveOrUpdate(segmentRider, database);
        }

        return stagingContext;
    }

    /**
	 * Determines if a Segment already exists in persistence with the given
	 * contractNumber
     *
     * @param contractNumber
     *
	 * @return true if a contract with the given contractNumber exists in
	 *         persistence, false otherwise.
     */
	public static boolean contractNumberExists(String contractNumber) {
        Segment existingSegment = Segment.findByContractNumber(contractNumber);

		if (existingSegment == null) {
            //  Segment does not already exist with that contractNumber
            return false;
		} else {
            //  Segment exists with that contractNumber
            return true;
        }
    }
    
    /**
     * True if this Segment has associated EnrollmentLeadServiceAgent(s) that are
     * active.
	 * 
     * @see #getActiveEnrollmentLeadServiceAgents()
     * @return
     */
	public boolean activeEnrollmentLeadServicingAgentsExist() {
        return (getActiveEnrollmentLeadServiceAgents().length > 0);        
    }
    
    /**
	 * Gets the Segment.ContractClient.ClientRole.EnrollmentLeadServiceAgent(s) for
	 * this Segment where:
     * 
	 * EnrollmentLeadServiceAgent.EffectiveDate <= specifiedDate <
	 * EnrollmentLeadServiceAgent.TerminationDate.
     * 
     * @return
     */
	public EnrollmentLeadServiceAgent[] getActiveEnrollmentLeadServiceAgents() {
		EnrollmentLeadServiceAgent[] enrollmentLeadServiceAgents = EnrollmentLeadServiceAgent
				.findBy_Segment_EffectiveDate(this, new EDITDate());
        
        return enrollmentLeadServiceAgents;
    }

	public static Segment[] findbyMasterContractDetails(Long contractGroupPK, Long productStructureFK,
			EDITDate masterContractEffectiveDate, String stateCT) {
        Segment[] segments = null;
         
		String hql = " select segment" + " from Segment segment" + " join segment.ContractGroup contractGroup"
				+ " where segment.IssueStateCT  = :stateCT" + " and segment.ProductStructureFK = :productStructureFK"
				+ " and segment.EffectiveDate <= :masterContractEffectiveDate"
				+ " and segment.ContractGroupFK = :contractGroupPK";

        Map params = new HashMap();

        params.put("stateCT", stateCT);
        params.put("productStructureFK", productStructureFK);
        params.put("masterContractEffectiveDate", masterContractEffectiveDate);
        params.put("contractGroupPK", contractGroupPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }
     
	public static Segment[] findByMasterContractFK(Long masterContractPK) {
        Segment[] segments = null;

		String hql = " select segment from Segment segment" + " where segment.MasterContractFK = :masterContractPK";

        Map params = new HashMap();

        params.put("masterContractPK", masterContractPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }
     
	public Segment[] checkForNewRider(SegmentVO segmentVO) {
        Segment[] newRiderSegments = null;
        List riderArray = new ArrayList();

        SegmentVO[] riderSegmentVOs = segmentVO.getSegmentVO();
		if (riderSegmentVOs != null) {
            //find the ones added
			for (int i = 0; i < riderSegmentVOs.length; i++) {
                Segment rider = new Segment(riderSegmentVOs[i]);
				if (rider.getSegmentPK() == null) {
                    riderArray.add(rider);
                }

            }
        }

		if (!riderArray.isEmpty()) {
            newRiderSegments = (Segment[]) riderArray.toArray(new Segment[riderArray.size()]);
        }
        return newRiderSegments;
    }

    /**
	 * Determines if the segment has riders that will be deleted when the segment is
	 * saved.
     *
     * @return  true if any one of the riders will be deleted, false otherwise
     */
	public Segment[] ridersDeleted() {
        List riderArray = new ArrayList();
        Segment[] deletedRiderSegments = null;

        SegmentVO segmentVO = (SegmentVO) this.getVO();

        SegmentVO[] riderVOs = segmentVO.getSegmentVO();

		if (riderVOs != null) {
			for (int i = 0; i < riderVOs.length; i++) {
				if (riderVOs[i].getVoShouldBeDeleted()) {
                    Segment rider = new Segment(riderVOs[i]);
                    riderArray.add(rider);
                }
            }
        }

		if (!riderArray.isEmpty()) {
            deletedRiderSegments = (Segment[]) riderArray.toArray(new Segment[riderArray.size()]);
        }

        return deletedRiderSegments;
    }

    /**
	 * Determines if the owner has been deleted on this segment. When changing an
	 * owner, there will be 2 ContractClients that are owners - 1 that was deleted
	 * and 1 that was added. Therefore, must check for all owners and determine if
	 * any were deleted.
     *
     * @return true if the owner was deleted, false otherwise
     */
	public boolean ownerDeleted() {
        boolean ownerDeleted = false;

        SegmentVO segmentVO = (SegmentVO) this.getVO();

        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

		for (int i = 0; i < contractClientVOs.length; i++) {
            ContractClientVO contractClientVO = contractClientVOs[i];

            //  Use entity for convenience of finding owner
            ContractClient contractClient = new ContractClient();
            contractClient.setVO(contractClientVO);

			if (contractClient.isOwner()) {
                //  Owner found, has it been deleted?
				if (contractClientVO.getVoShouldBeDeleted()) {
                    ownerDeleted = true;
                    break;
                }
            }
        }

        return ownerDeleted;
    }

    /**
	 * Get the type of segmentStatus. They are thre classifications, NewBusiness,
	 * Inforce and Terminate
	 * 
     * @param segment
     * @param effectiveDate
     * @return
     */
	public String determineSegmentStatusType(Segment segment, EDITDate effectiveDate) {
        String statusType = null;
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String grouping = "STATUS";
        String field = segment.getSegmentStatusCT().toUpperCase();
        String qualifierCT = "*";

		AreaValueVO areaValueVO = engineLookup.getAreaValue(segment.getProductStructureFK().longValue(),
				segment.getIssueStateCT(), grouping, effectiveDate, field, qualifierCT);

		if (areaValueVO != null) {
            statusType = areaValueVO.getAreaValue();
        }

        return statusType;
    }

	public static List<Segment> getSegmentsByContractGroupFK(Long contractGroupPK) {

		String hql = " select segment from Segment segment" + " where segment.ContractGroupFK = :contractGroupPK";

        Map params = new HashMap();

        params.put("contractGroupPK", contractGroupPK);

        List<Segment> results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

        return results;
    }

	public static Segment[] findByContractGroupFK(Long contractGroupPK) {
        Segment[] segments = null;

		String hql = " select segment from Segment segment" + " where segment.ContractGroupFK = :contractGroupPK";

        Map params = new HashMap();

        params.put("contractGroupPK", contractGroupPK);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }

    /**
	 * Determines which kind of billSchedule this segment changed from and to, if it
	 * was changed at all.
     * <P>
	 * It compares the BillScheduleFK on "this" unsaved segment to the one in
	 * persistence. If they are the same, the billSchedule was not changed. If they
	 * are different, this segment's BillSchedule is checked for it's "type". If
	 * this segment's BillSchedule.BillMethodCT is equal to
	 * BillSchedule.BILL_METHOD_LISTBILL, the persisted segment's
	 * BillSchedule.BillMethodCT is checked. If it was a list, then it is a
	 * list-to-list changed. If it was an individual, then it is an
	 * individual-to-list change.
     * <P>
	 * If this segment's BillSchedule.BillMethodCT is NOT
	 * BillSchedule.BILL_METHOD_LISTBILL, it's a list-to-individual change (no such
	 * thing as an individual-to-individual - yet).
     * <P>
	 * Unfortunately, we have to go through all of this instead of allowing the
	 * Segment's changeToIndividualBill and changeToListBill methods set a
	 * parameter. That's because wonderful cloudland does not hang onto the Segment,
     * it only holds onto the segmentPK and builds the segment before the save.
     *
	 * @return String containing what type of billSchedule the segment's
	 *         BillSchedule was changed to or
     * BILLSCHEDULE_CHANGED_TO_WAS_NOT_CHANGED if it wasn't changed.
     */
	public String getBillScheduleChangedType() {
        Segment persistedSegment = Segment.findSeparateBy_SegmentPK(this.getSegmentPK());

        //  A new contract will not have a persisted segment
		if (persistedSegment == null) {
            return Segment.BILLSCHEDULE_CHANGED_WAS_NOT_CHANGED;
		} else {
            Long billScheduleFK = persistedSegment.getBillScheduleFK();

            //  Only some contracts have BillSchedules
			if (billScheduleFK == null) {
                return Segment.BILLSCHEDULE_CHANGED_WAS_NOT_CHANGED;
			} else {
				if (segmentVO.getBillScheduleFK() == 0
						|| persistedSegment.getBillScheduleFK().equals(this.getBillScheduleFK())) {
                    return Segment.BILLSCHEDULE_CHANGED_WAS_NOT_CHANGED;
				} else {
					BillSchedule persistedBillSchedule = BillSchedule
							.findBy_BillSchedulePK(persistedSegment.getBillScheduleFK());
                    BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(this.getBillScheduleFK());

                    String billMethodCT = billSchedule.getBillMethodCT();

					if (billMethodCT.equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL)) {
						if (persistedBillSchedule.getBillMethodCT()
								.equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL)) {
                            return Segment.BILLSCHEDULE_CHANGED_LIST_TO_LIST;
						} else {
                            return Segment.BILLSCHEDULE_CHANGED_INDIVIDUAL_TO_LIST;
                        }
					} else {
						// There is no such change as an individual-to-individual, so this must be a
						// list-to-individual
                        return Segment.BILLSCHEDULE_CHANGED_LIST_TO_INDIVIDUAL;
                    }
                }
            }
        }
    }

    /**
	 * Creates and saves (processes real-time) the BC transaction associated with
	 * the billSchedule change. If the billSchedule was not changed, a BC trx is not
	 * created.
     *
     * @param billScheduleChangedType       type of BillSchedule change that occurred
     *
     * @throws EDITEventException
     */
	public void createBCTrxForBillScheduleChange(String billScheduleChangedType, String conversionValue)
			throws EDITEventException {
		if (!billScheduleChangedType.equalsIgnoreCase(Segment.BILLSCHEDULE_CHANGED_WAS_NOT_CHANGED)
				|| conversionValue.equalsIgnoreCase("CONV")) {
			// The billSchedule was changed to a different billSchedule, create BC trx
			// depending on the type
            //  of billSchedule it was changed from/to.
			if (billScheduleChangedType.equalsIgnoreCase(Segment.BILLSCHEDULE_CHANGED_LIST_TO_INDIVIDUAL)
					|| conversionValue.equalsIgnoreCase("CONV")) {
                BillSchedule billSchedule = this.getBillSchedule();
                EDITDate effectiveDate = billSchedule.getBillChangeStartDate();
				if (effectiveDate == null) {
                    effectiveDate = new EDITDate();
                }

                EDITTrx.createBillingChangeTrxGroupSetup(this, operator, conversionValue, effectiveDate);

            }
			/*
			 * ECK - 7-24-17 - The creation of a BC for a Bill Schedule change to list is
			 * not necessary as it is done earlier via the createTransaction method in the
			 * PRASE SCRIPTS
             * 
			 * else if (billScheduleChangedType.equalsIgnoreCase(Segment.
			 * BILLSCHEDULE_CHANGED_INDIVIDUAL_TO_LIST) ||
			 * billScheduleChangedType.equalsIgnoreCase(Segment.
			 * BILLSCHEDULE_CHANGED_LIST_TO_LIST)) {
			 * EDITTrx.createBillingChangeTrxGroupSetup(this, operator,
			 * ContractSetup.COMPLEXCHANGETYPECT_LIST, new EDITDate()); }
			 */
        }
    }
    
	public EDITDate getClaimStopDate() {
        return ((segmentVO.getClaimStopDate() != null) ? new EDITDate(segmentVO.getClaimStopDate()) : null);
    }    
    
	public void setClaimStopDate(EDITDate claimStopDate) {
        segmentVO.setClaimStopDate((claimStopDate != null) ? claimStopDate.getFormattedDate() : null);
    }

	public void updateWorksheetType(String worksheetType) {
        this.setWorksheetTypeCT(worksheetType);

        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        SessionHelper.saveOrUpdate(this, SessionHelper.EDITSOLUTIONS);

        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
    }

    /**
     * @return the segmentHistories
     */
	public Set<SegmentHistory> getSegmentHistories() {
        return segmentHistories;
    }

    /**
     * @param segmentHistories the segmentHistories to set
     */
	public void setSegmentHistories(Set<SegmentHistory> segmentHistories) {
        this.segmentHistories = segmentHistories;
    }
    
    //
    /**
     * @return the segmentSecondaryParents
     */
	public Set<SegmentSecondary> getSegmentSecondaryParents() {
        return segmentSecondaryParents;
    }

    /**
     * @param segmentSecondaryParents the segmentSecondaryParents to set
     */
	public void setSegmentSecondaryParents(Set<SegmentSecondary> segmentSecondaryParents) {
        this.segmentSecondaryParents = segmentSecondaryParents;
    }
    
    /**
     * @return the segmentSecondaryChildren
     */
	public Set<SegmentSecondary> getSegmentSecondaryChildren() {
        return segmentSecondaryChildren;
    }

    /**
     * @param segmentSecondaryChildren the segmentSecondaryChildren to set
     */
	public void setSegmentSecondaryChildren(Set<SegmentSecondary> segmentSecondaryChildren) {
        this.segmentSecondaryChildren = segmentSecondaryChildren;
    }
    //

	public static Segment[] findByBillScheduleFK_EffectiveDate_TransactionType(Long billScheduleFK, EDITDate trxDate,
			EDITDate cycleDate) {
        Segment[] segments = null;

		String hql = " select segment from Segment segment" + " left join fetch segment.ContractSetups contractSetups"
				+ " left join fetch contractSetups.ClientSetups clientSetups"
				+ " left join fetch clientSetups.EDITTrxs editTrxs" + " where segment.BillScheduleFK = :billScheduleFK"
				+ " and (editTrxs.EffectiveDate > :effectiveDate" + " and editTrxs.EffectiveDate <= :trxDate)"
				+ " and editTrxs.PendingStatus = 'P'" + " and editTrxs.PremiumDueCreatedIndicator = 'N'"
				+ " and (editTrxs.TransactionTypeCT = 'FI'" + " or editTrxs.TransactionTypeCT = 'MA'"
				+ " or editTrxs.TransactionTypeCT = 'BC')";

        Map params = new HashMap();

        params.put("billScheduleFK", billScheduleFK);
        params.put("effectiveDate", cycleDate);
        params.put("trxDate", trxDate);

        List results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (!results.isEmpty()) {
            segments = (Segment[]) results.toArray(new Segment[results.size()]);
        }

        return segments;
    }

	public Long getSegmentPKWithPendingPRD(Long segmentPK, EDITDate cycleDate) {
        Long validSegmentPK = null;

		String hql = " select distinct segment from Segment segment"
				+ " left join fetch segment.ContractSetups contractSetups"
				+ " left join fetch contractSetups.ClientSetups clientSetups"
				+ " left join fetch clientSetups.EDITTrxs editTrxs"
				+ " left join fetch segment.BillSchedule billSchedule"
				+ " left join fetch segment.ContractGroup contractGroup"
				+ " left join fetch contractGroup.PayrollDeductionSchedules pds"
				+ " where segment.SegmentPK = :segmentPK" + " and pds.NextPRDExtractDate <= :cycleDate"
				+ " and billSchedule.BillMethodCT = 'list'";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("cycleDate", cycleDate);

        List<Segment> results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (results.size() > 0) {
            validSegmentPK = ((Segment) results.get(0)).getSegmentPK();
        }

        return validSegmentPK;
    }

    /**
	 * If the user did not specify a specific GroupPlan, then we default it based on
	 * values that have been setup in the AreaTable and CaseProductUnderwriting
	 * tables. Ends up that this very logic has already been written in the
	 * GetAreaTable instruction. It's unusual to call a PRASE instruction outside of
	 * the execution of Scripts, but it was too "convenient".
     */
	public void defaultGroupRatedGenUnderwriting(String grouping) throws EDITCaseException {
		defaultGroupRatedGenUnderwriting(grouping, "");
	}

	public void defaultGroupRatedGenUnderwriting(String grouping, String productType) throws EDITCaseException {
        String areaGroup = grouping;  
        
		if (getGroupPlan() == null || getGroupPlan().equals("")) {
            
            long productStructurePK = getProductStructureFK().longValue();
            String areaCT = "*";
            EDITDate areaDate = getEffectiveDate();
            String qualifierCT = "*";
            String areaField = null;
            String relationshipToEmployeeCT = null;
            String batchContractSetupPKString = null;
            String groupContractGroupPKString = null;
            
			if (areaGroup.equals("CASEBASE")) {
                batchContractSetupPKString = getBatchContractSetup().getBatchContractSetupPK().toString();
                groupContractGroupPKString = getContractGroup().getContractGroupPK().toString();
            }
			
			if (areaGroup.equals("CASERIDERS")) {
				batchContractSetupPKString = this.getSegment().getBatchContractSetup().getBatchContractSetupPK().toString();
				groupContractGroupPKString =  this.getSegment().getContractGroup().getContractGroupPK().toString();
            }
            
            Map<String, Object> results = new HashMap<String, Object>();
            try {
            	// first find generic *-qualifier values
				new Getareatable().runAreaTableLookup(productStructurePK, areaCT, areaGroup, areaDate, qualifierCT,
						areaField, batchContractSetupPKString, groupContractGroupPKString, relationshipToEmployeeCT,
						results);
				
				if (areaGroup.equals("CASERIDERS")) {
					// then look for values specific to the rider optionCode (as qualifier) and overwrite the generics if found
					new Getareatable().runAreaTableLookup(productStructurePK, areaCT, areaGroup, areaDate, this.getOptionCodeCT(),
						areaField, batchContractSetupPKString, groupContractGroupPKString, relationshipToEmployeeCT,
						results);
				}
				
            } catch (SPException ex) {
                throw new EDITCaseException(ex.getMessage());
            }
             
			if (!results.isEmpty()) {
				if (results.containsKey("GROUPPLAN")) {
                 setGroupPlan(results.get("GROUPPLAN").toString());
				}
			}
		}
     
		//String productType = batchContractSetup.getFilteredProduct().getProductStructure().getBusinessContractName();
		if ((this.getRatedGenderCT() == null || this.getRatedGenderCT().equals("")) && !productType.equals("UL")) {
			
			CaseProductUnderwriting[] ratedGenders = null;
			
			if (areaGroup.equals("CASERIDERS")) {
                batchContractSetup =  this.getSegment().getBatchContractSetup();
            
                // look for specific rider ratedGender value
                ratedGenders = CaseProductUnderwriting
					.findByEnrollmentFK_FilteredProductFK_Grouping_Field_Qualifier_RelationshipToEmployeeCT(
							batchContractSetup.getEnrollmentFK(), batchContractSetup.getFilteredProductFK(),
							CaseProductUnderwriting.GROUPING_CASERIDERS, CaseProductUnderwriting.FIELD_RATEDGENDER,
							this.getOptionCodeCT(), null);
			}

			if (ratedGenders != null && ratedGenders.length > 0) {
               setRatedGenderCT(ratedGenders[0].getValue());
               
            } else {
            	// get generic ratedGender
            	ratedGenders = CaseProductUnderwriting
    					.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(
    							batchContractSetup.getEnrollmentFK(), batchContractSetup.getFilteredProductFK(),
    							CaseProductUnderwriting.GROUPING_CASEOTHER, CaseProductUnderwriting.FIELD_RATEDGENDER,
    							null);
            	
            	if (ratedGenders != null && ratedGenders.length > 0) {
                    setRatedGenderCT(ratedGenders[0].getValue());
                    
                 }
            }
        }
        
		if (this.getUnderwritingClassCT() == null || this.getUnderwritingClassCT().equals("")) {             
			if (areaGroup.equals("CASERIDERS")) {
                batchContractSetup =  this.getSegment().getBatchContractSetup();
            }

			if (batchContractSetup != null) {
			    CaseProductUnderwriting[] underwritingClasses = CaseProductUnderwriting
					.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(
							batchContractSetup.getEnrollmentFK(), batchContractSetup.getFilteredProductFK(),
							CaseProductUnderwriting.GROUPING_CASEOTHER,
							CaseProductUnderwriting.FIELD_UNDERWRITING_CLASS, null);

			    if (underwritingClasses != null && underwritingClasses.length > 0) {
                    setUnderwritingClassCT(underwritingClasses[0].getValue());
                }
			}
        }
	}
	
}