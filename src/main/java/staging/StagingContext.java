/**
 * User: dlataill
 * Date: Oct 3, 2007
 * Time: 8:51:34 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */

package staging;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITDateTime;

import java.util.Set;
import java.util.HashSet;

import group.ContractGroup;
import contract.AgentHierarchy;
import contract.AgentHierarchyAllocation;
import contract.AgentSnapshot;


public class StagingContext
{
    public static final String PRD_STAGING_EVENT = "PRD";
    public static final String BILL_STAGING_EVENT = "BILLING";
    public static final String GENERAL_LEDGER_STAGING_EVENT = "GeneralLedger";
    public static final String CLIENT_ACCOUNTING_STAGING_EVENT = "ClientAccounting";
    public static final String COMMISSION_STATEMENT = "CommStmt";

    private String eventType;
    private EDITDateTime stagingDate;
    private String companyName;
    private String caseNumber;
    private String groupNumber;
    private String contractNumber;
    private String correspondenceType;
    private int recordCount;
    private EDITBigDecimal controlTotal;

    private String currentAgentNumber;
    private String currentRoleType;

    private String contractGroupType;
    private String segmentType;

    private Staging staging;
    private Case currentCase;
    private BillSchedule currentBillSchedule;
    private Group currentGroup;
    private BillGroup currentBillGroup;
    private PayrollDeductionSchedule currentPRDSchedule;
    private SegmentBase currentSegmentBase;
    private SegmentRider currentSegmentRider;
    private Accounting currentAccounting;
    private FinancialActivity currentFinancialActivity;
    private Agent currentAgent;
    private AgentHierarchy currentAgentHierarchy;
    private Enrollment currentEnrollment;
    private PremiumDue currentPremiumDue;
    private ContractClient currentContractClient;
    private String forceoutMinBalInd;
    private Investment currentInvestment;
    private FilteredProduct currentFilteredProduct;
    private AnnualStatement currentAnnualStatement;

    private AgentSnapshot currentAgentSnapshot;
    private AgentHierarchyAllocation currentAgentHierarchyAllocation;

    public StagingContext(String eventType, EDITDateTime stagingDate)
    {
        this.eventType = eventType;
        this.stagingDate = stagingDate;
        this.correspondenceType = null;
        this.companyName = null;
        this.caseNumber = null;
        this.groupNumber = null;
        this.recordCount = 0;
        this.controlTotal = new EDITBigDecimal();
        this.currentCase = null;
        this.currentBillSchedule = null;
        this.currentGroup = null;
        this.currentBillGroup = null;
        this.currentPRDSchedule = null;
        this.currentSegmentBase = null;
        this.currentSegmentRider = null;
        this.currentAccounting = null;
        this.currentFinancialActivity = null;
        this.currentAgent = null;
        this.currentAnnualStatement = null;
        this.currentAgentHierarchy = null;
        this.currentEnrollment = null;
        this.currentPremiumDue = null;
        this.currentContractClient = null;
        this.currentInvestment = null;
        this.currentFilteredProduct = null;

        this.currentAgentNumber = null;
        this.currentRoleType = null;
    }

    public StagingContext(String eventType, EDITDateTime stagingDate, String correspondenceType)
    {
        this.eventType = eventType;
        this.stagingDate = stagingDate;
        this.companyName = null;
        this.caseNumber = null;
        this.groupNumber = null;
        this.correspondenceType = correspondenceType;
        this.recordCount = 0;
        this.controlTotal = new EDITBigDecimal();
        this.currentCase = null;
        this.currentBillSchedule = null;
        this.currentGroup = null;
        this.currentBillGroup = null;
        this.currentPRDSchedule = null;
        this.currentSegmentBase = null;
        this.currentSegmentRider = null;
        this.currentAccounting = null;
        this.currentFinancialActivity = null;
        this.currentAgent = null;
        this.currentAnnualStatement = null;
        this.currentAgentHierarchy = null;
        this.currentEnrollment = null;
        this.currentPremiumDue = null;
        this.currentContractClient = null;
        this.currentInvestment = null;
        this.currentFilteredProduct = null;

        this.currentAgentNumber = null;
        this.currentRoleType = null;
    }

    public StagingContext(String eventType, EDITDateTime stagingDate, String companyName, String contractNumber)
    {
        this.eventType = eventType;
        this.stagingDate = stagingDate;
        this.companyName = companyName;
        this.contractNumber = contractNumber;
        this.caseNumber = null;
        this.groupNumber = null;
        this.recordCount = 0;
        this.controlTotal = new EDITBigDecimal();
        this.currentCase = null;
        this.currentBillSchedule = null;
        this.currentGroup = null;
        this.currentBillGroup = null;
        this.currentPRDSchedule = null;
        this.currentSegmentBase = null;
        this.currentAccounting = null;
        this.currentFinancialActivity = null;
        this.currentAgent = null;
        this.currentAnnualStatement = null;
        this.currentAgentHierarchy = null;
        this.currentEnrollment = null;
        this.currentPremiumDue = null;
        this.currentContractClient = null;
        this.currentInvestment = null;
        this.currentFilteredProduct = null;

        this.currentAgentNumber = null;
        this.currentRoleType = null;
    }

    public StagingContext(String eventType, EDITDateTime stagingDate, String companyName, String caseNumber, String groupNumber)
    {
        this.eventType = eventType;
        this.stagingDate = stagingDate;
        this.companyName = companyName;
        this.caseNumber = caseNumber;
        this.groupNumber = groupNumber;
        this.recordCount = 0;
        this.controlTotal = new EDITBigDecimal();
        this.currentCase = null;
        this.currentBillSchedule = null;
        this.currentGroup = null;
        this.currentBillGroup = null;
        this.currentPRDSchedule = null;
        this.currentSegmentBase = null;
        this.currentSegmentRider = null;
        this.currentAccounting = null;
        this.currentFinancialActivity = null;
        this.currentAgent = null;
        this.currentAnnualStatement = null;
        this.currentAgentHierarchy = null;
        this.currentEnrollment = null;
        this.currentPremiumDue = null;
        this.currentContractClient = null;
        this.currentInvestment = null;
        this.currentFilteredProduct = null;

        this.currentAgentNumber = null;
        this.currentRoleType = null;
    }

    public String getEventType()
    {
        return eventType;
    }

    public EDITDateTime getStagingDate()
    {
        return stagingDate;
    }

    public int getRecordCount()
    {
        return recordCount;
    }

    public void setRecordCount(int recordCount)
    {
        this.recordCount = recordCount;
    }

    public void incrementRecordCount(int numberOfRecords)
    {
        recordCount += numberOfRecords;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public String getCaseNumber()
    {
        return caseNumber;
    }

    public String getGroupNumber()
    {
        return groupNumber;
    }

    public String getCorrespondenceType()
    {
        return correspondenceType;
    }

    public String getContractNumber()
    {
        return contractNumber;
    }

    public EDITBigDecimal getControlTotal()
    {
        return controlTotal;
    }

    public void incrementControlTotal(EDITBigDecimal addition)
    {
        controlTotal = controlTotal.addEditBigDecimal(addition);
    }

    public void decrementControlTotal(EDITBigDecimal subtraction)
    {
        controlTotal = controlTotal.subtractEditBigDecimal(subtraction);
    }

    public void setStaging(Staging staging)
    {
        this.staging = staging;
    }

    public Staging getStaging()
    {
        return staging;
    }

    public void setContractGroupType(String contractGroupType)
    {
        this.contractGroupType = contractGroupType;
    }

    public String getContractGroupType()
    {
        return contractGroupType;
    }

    public void setSegmentType(String segmentType)
    {
        this.segmentType = segmentType;
    }

    public String getSegmentType()
    {
        return segmentType;
    }

    public void setCurrentAgentNumber(String agentNumber)
    {
        this.currentAgentNumber = agentNumber;
    }

    public String getCurrentAgentNumber()
    {
        return currentAgentNumber;
    }

    public void setCurrentRoleType(String roleType)
    {
        this.currentRoleType = roleType;
    }

    public String getCurrentRoleType()
    {
        return currentRoleType;
    }

    public void setCurrentCase(Case currentCase)
    {
        this.currentCase = currentCase;
    }

    public Case getCurrentCase()
    {
        return currentCase;
    }

    public void setCurrentBillSchedule(BillSchedule billSchedule)
    {
        this.currentBillSchedule = billSchedule;
    }

    public BillSchedule getCurrentBillSchedule()
    {
        return currentBillSchedule;
    }

    public void setCurrentGroup(Group group)
    {
        this.currentGroup = group;
    }

    public Group getCurrentGroup()
    {
        return currentGroup;
    }

    public void setCurrentBillGroup(BillGroup billGroup)
    {
        this.currentBillGroup = billGroup;
    }

    public BillGroup getCurrentBillGroup()
    {
        return currentBillGroup;
    }

    public void setCurrentPRDSchedule(PayrollDeductionSchedule prdSched)
    {
        this.currentPRDSchedule = prdSched;
    }

    public PayrollDeductionSchedule getCurrentPRDSchedule()
    {
        return currentPRDSchedule;
    }

    public void setCurrentSegmentBase(SegmentBase segmentBase)
    {
        this.currentSegmentBase = segmentBase;
    }

    public SegmentBase getCurrentSegmentBase()
    {
        return currentSegmentBase;
    }

    public void setCurrentSegmentRider(SegmentRider segmentRider)
    {
        this.currentSegmentRider = segmentRider;
    }

    public SegmentRider getCurrentSegmentRider()
    {
        return currentSegmentRider;
    }

    public void setCurrentAccounting(Accounting accounting)
    {
        this.currentAccounting = accounting;
    }

    public Accounting getCurrentAccounting()
    {
        return currentAccounting;
    }

    public void setCurrentFinancialActivity(FinancialActivity financialActivity)
    {
        this.currentFinancialActivity = financialActivity;
    }

    public FinancialActivity getCurrentFinancialActivity()
    {
        return currentFinancialActivity;
    }

    public void setCurrentAgent(Agent agent)
    {
        this.currentAgent = agent;
    }

    public Agent getCurrentAgent()
    {
        return currentAgent;
    }
    
    public void setCurrentAnnualStatement(AnnualStatement annualStatement)
    {
        this.currentAnnualStatement = annualStatement;
    }

    public AnnualStatement getCurrentAnnualStatement()
    {
        return currentAnnualStatement;
    }

    /**
     * Setter.
     * @param agentHierarchy
     */
    public void setCurrentAgentHierarchy(AgentHierarchy agentHierarchy)
    {
        this.currentAgentHierarchy = agentHierarchy;
    }

    /**
     * Getter.
     * @return
     */
    public AgentHierarchy getCurrentAgentHierarchy()
    {
        return currentAgentHierarchy;
    }

    public void setCurrentEnrollment(Enrollment enrollment)
    {
        this.currentEnrollment = enrollment;
    }

    public Enrollment getCurrentEnrollment()
    {
        return currentEnrollment;
    }

    public void setCurrentPremiumDue(PremiumDue premiumDue)
    {
        this.currentPremiumDue = premiumDue;
    }

    public PremiumDue getCurrentPremiumDue()
    {
        return currentPremiumDue;
    }

    public void setCurrentContractClient(ContractClient contractClient)
    {
        this.currentContractClient = contractClient;
    }

    public ContractClient getCurrentContractClient()
    {
        return currentContractClient;
    }

    public void setCurrentInvestment(Investment investment)
    {
        this.currentInvestment = investment;
    }

    public Investment getCurrentInvestment()
    {
        return currentInvestment;
    }

    public void setCurrentFilteredProduct(FilteredProduct filteredProduct)
    {
        this.currentFilteredProduct = filteredProduct;
    }

    public FilteredProduct getCurrentFilteredProduct()
    {
        return currentFilteredProduct;
    }

    public void setForceoutMinBalInd(String forceoutMinBalInd)
    {
        this.forceoutMinBalInd = forceoutMinBalInd;
    }

    public String getForceoutMinBalInd()
    {
        return forceoutMinBalInd;
    }

	public AgentSnapshot getCurrentAgentSnapshot() {
		return currentAgentSnapshot;
	}

	public void setCurrentAgentSnapshot(AgentSnapshot currentAgentSnapshot) {
		this.currentAgentSnapshot = currentAgentSnapshot;
	}

	public AgentHierarchyAllocation getCurrentAgentHierarchyAllocation() {
		return currentAgentHierarchyAllocation;
	}

	public void setCurrentAgentHierarchyAllocation(
			AgentHierarchyAllocation currentAgentHierarchyAllocation) {
		this.currentAgentHierarchyAllocation = currentAgentHierarchyAllocation;
	}
}
