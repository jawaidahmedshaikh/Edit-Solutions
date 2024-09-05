/*
 * User: gfrosti
 * Date: May 17, 2007
 * Time: 1:59:05 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package group;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SQLQuery;

import agent.PlacedAgent;
import billing.BillSchedule;
import client.ClientAddress;
import client.ClientDetail;
import contract.AgentHierarchy;
import contract.AgentSnapshot;
import contract.FilteredProduct;
import contract.FilteredRequirement;
import contract.MasterContract;
import contract.Requirement;
import contract.Segment;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;
import fission.utility.DateTimeUtil;
import reinsurance.ContractTreaty;
import role.ClientRole;
import staging.IStaging;
import staging.StagingContext;

/**
 * OBSOLETE?? :
 * With the concept of group processing, we would like to dynamically generate
 * tree-like structures. For example, a 'Main' group would have a 'Sub' group which
 * might have a "Sub-Sub' group so on and so forth. The ContractGroup is that basic
 * grouping structure. ContractGroup can have one or more child ContractGroups in a
 * recursive manner. Billing, Clients, etc. will exist at this level. These associations
 * are a repeat of some of the relationships at the Segment level. The long-term goal
 * is to have all of these relationships at the ContractGroup level while removing them
 * at the Segment level. This will only be possible if [every] Segment is associated
 * with a ContractGroup. Even a single Segment would have a ContractGroup of "Individual."
 *
 * CURRENT:
 * There are 2 types of ContractGroups - Case and Group.  Each contains the same set of information each has
 * different associations.  The Case is associated with FilteredProduct, ContractGroupRequirement, ClientRole, and
 * AgentHierarchy.  The Group is associated with BatchContractSetup, Segment, BillSchedule, ClientRole, and ContractGroupInvestment.
 *
 * @author gfrosti
 */
public class ContractGroup extends HibernateEntity implements IStaging
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * PK.
     */
    private Long contractGroupPK;

    /**
     * Parent ContractGroup.
     */
    private Long contractGroupFK;

    /**
     * @todo definition?
     * BillSchedule associated with the Group
     */
    private BillSchedule billSchedule;
    private Long billScheduleFK;

    /**
     * @todo definition?
     * ClientRole associated with the Case or Group
     */
    private ClientRole clientRole;
    private Long clientRoleFK;

    /**
     * @todo definition?
     */
    private String contractGroupNumber;
    private String groupID;

    /**
     * The date this grouping becomes effective.
     */
    private EDITDate effectiveDate;

    /**
     * The date this grouping is terminated.
     */
    private EDITDate terminationDate;

    /**
     * A unique identification across ContractGroups. Several ContractGroups that
     * do not belong to the same hierarchical structure may want to share some
     * common processes.
     */
    private String caseAssociationCode;

    /**
     * Examples might be "Group", "Case", "Certificate".
     */
    private String contractGroupTypeCT;

    /**
     * Examples might be "Proposal", "Offer".
     */
    private String groupStatusCT;

    /**
     * The set of associated Requirements to be assigned at the "Case" level. The assumption
     * is that Case is the highest level ContractGroup. Lower ContractGroups (Group, etc)
     * are not to have their own ContractGroupRequirements.
     */
    private Set<ContractGroupRequirement> contractGroupRequirements;
    
    /**
     * The set of associated BatchContractSetups that exists only for 
     * "Group" type ContractGroups.
     */
    private Set<BatchContractSetup> batchContractSetups;

    /**
     * The set of associated Notes to be assigned at the "Case" level. The assumption
     * is that Case is the highest level ContractGroup.
     */
    private Set<ContractGroupNote> contractGroupNotes;

    /**
     * The set of associated CaseSetup to be assigned at the "Case" level. The assumption
     * is that Case is the highest level ContractGroup.
     */
    private Set<CaseSetup> caseSetups;

    /**
     * For Reinsurance, the set of ContractTreaties that map a ContractGroup to its Treaties.
     */
    private Set<ContractTreaty> contractTreaties;

    /** The number of hours that an employee must work per week to be considered an 'active' employee */
    private BigDecimal activeEligibilityHours;
    
    /** A number of months or days after initial hire than an active employee is not eligible for benefits */
    private BigDecimal ineligibilityPeriodUnits;
    
    /** Indicates whether ineligibilityPeriodUnits represents a duration of days or months */
    private String ineligibilityPeriodType;
    
    /**
     * @todo
     * 
     */
    private String caseStatusCT;

     /**
     * US State this ContractGroup has its domicile in
     */
    private String domicileStateCT;

    /**
     * The date this group was created (sytem date).
     */
    private EDITDate creationDate;

    /**
     * The user logged-in that created this group.
     */
    private String creationOperator;

    /**
     * Examples might be "Association", "Employer", "Union".
     */
    private String caseTypeCT;

    /**
     * Set to "Y" if the automatic Requirements have been associated 
     * to this ContractGroup; "N" otherwise. Since ContractRequirements
     * can only be associated to a 'Case" ContractGroup, this would always
     * be "N" for any other ContractGroup than a 'Case'.
     */
    private String contractRequirementsGenerated;

    private String groupTrustStateCT;

    private String requirementNotifyDayCT;

    private EDITBigDecimal deductionAmtMinTol;

    private EDITBigDecimal deductionAmtMaxTol;

    /** Days setting of ineligibilityPeriodType*/
    public static final String INELIGIBILITY_PERIOD_DAYS = "Days";
    
    /** Months setting of ineligibilityPeriodType*/
    public static final String INELIGIBILITY_PERIOD_MONTHS = "Months";    
    
    /**
     * Implies that automatic Requirements have been generated and
     * associated via the ContractGroupRequirement entity to this
     * ContractGroup (a 'Case' ContractGroup).
     */
    public static final String REQUIREMENTS_GENERATED_YES = "Y";

    /**
     * Implies that automatic Requirements have not yet been generated
     * and associated with this ContractGroup (a 'Case' ContractGroup).
     */
    public static final String REQUIREMENTS_GENERATED_NO = "N";

    /**
     * A 'Case' ContractGroup.
     */
    public static final String CONTRACTGROUPTYPECT_CASE = "Case";

    /**
     * A 'Group' ContractGroup.
     */
    public static final String CONTRACTGROUPTYPECT_GROUP = "Group";

    /**
     * The set of allowable Products (as mapped by FilteredProducts) mapped
     * to this ContractGroup.  Associated with Case
     */
    private Set<FilteredProduct> filteredProducts;

    private Set<MasterContract> masterContracts;
    /**
     * The parent grouping element of this ContractGroup. An example would be
     * a "Group" ContractGroup has a parent "Case" ContractGroup.
     */
    private ContractGroup contractGroup;
    
    /**
     * The child grouping elements of this ContractGroup. An example would be
     * a "Case" ContractGroup having a set of child "Group" ContractGroups.
     */
    private Set<ContractGroup> contractGroups;

    /**
     * The set of payroll deduction schedules mapped to the ContractGroup
     */
    private Set<PayrollDeductionSchedule> payrollDeductionSchedules;

    /**
     * Candidate AgentHierarchies for this ContractGroup.  Associated with the Case
     */
    private Set<AgentHierarchy> agentHierarchies;

    /**
     * Segments that belong to this ContractGroup.  Associated with the Group
     */
    private Set<Segment> segments;

    /**
     * Associated with the Group
     */
    private Set<ContractGroupInvestment> contractGroupInvestments;

    /**
     * The set of departments/locations mapped to the ContractGroup
     */
    private Set<DepartmentLocation> departmentLocations;

    /**
     * The set of CaseStatusChangeHistories mapped to the ContractGroup (applies to the Case level only)
     */
    private Set<CaseStatusChangeHistory> caseStatusChangeHistories;

    /**
     * The set of Enrollments mapped to the ContractGroup (applies to the Case level only)
     */
    private Set<Enrollment> enrollments;
    
    

    public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	/**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public ContractGroup()
    {
        init();

        // Set defaults
        terminationDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);

//        effectiveDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);

        contractGroupRequirements = new HashSet<ContractGroupRequirement>();

        contractRequirementsGenerated = REQUIREMENTS_GENERATED_NO;
    }

    private void init()
    {
        if (segments == null)
        {
            segments = new HashSet<Segment>();
        }

        if (payrollDeductionSchedules == null)
        {
            payrollDeductionSchedules = new HashSet<PayrollDeductionSchedule>();
        }

        if (departmentLocations == null)
        {
            departmentLocations = new HashSet<DepartmentLocation>();
        }

        if (caseStatusChangeHistories == null)
        {
            caseStatusChangeHistories = new HashSet<CaseStatusChangeHistory>();
        }

        if (enrollments == null)
        {
            enrollments = new HashSet<Enrollment>();
        }

        if (contractGroupNotes == null)
        {
            contractGroupNotes = new HashSet<ContractGroupNote>();
        }

        if (caseSetups  == null)
        {
            caseSetups = new HashSet<CaseSetup>();
        }

        if (getContractTreaties() == null)
        {
            setContractTreaties(new HashSet<ContractTreaty>());
        }
    }

    /**
     * @see #contractGroupPK
     * @return the PK
     */
    public Long getContractGroupPK()
    {
        return contractGroupPK;
    }

    /**
     * @param contractGroupPK the PK
     * @see #contractGroupPK
     */
    public void setContractGroupPK(Long contractGroupPK)
    {
        this.contractGroupPK = contractGroupPK;
    }

    /**
     * @see #contractGroupFK
     * @return the parent grouping's FK
     */
    public Long getContractGroupFK()
    {
        return contractGroupFK;
    }

    /**
     * @param contractGroupFK
     * @see #contractGroupFK
     */
    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }


    public ClientRole getClientRole()
    {
        return this.clientRole;
    }

    public void setClientRole(ClientRole clientRole)
    {
        this.clientRole = clientRole;
    }

    public Long getClientRoleFK()
    {
        return this.clientRoleFK;
    }

    public void setClientRoleFK(Long clientRoleFK)
    {
        this.clientRoleFK = clientRoleFK;
    }

    /**
     * @see #contractGroupNumber
     * @return the contractGroupNumber
     */
    public String getContractGroupNumber()
    {
        return contractGroupNumber;
    }

    /**
     * @param contractGroupNumber
     * @see #contractGroupNumber
     */
    public void setContractGroupNumber(String contractGroupNumber)
    {
        this.contractGroupNumber = contractGroupNumber;
    }

    /**
     * @see #effectiveDate
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIEffectiveDate()
    {
        String date = null;

        if (getEffectiveDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getEffectiveDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIEffectiveDate(String uiEffectiveDate)
    {
        if (uiEffectiveDate != null)
        {
            setEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiEffectiveDate));
        }
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUICreationDate()
    {
        String date = null;

        if (getCreationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getCreationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUICreationDate(String uiCreationDate)
    {
        if (uiCreationDate != null)
        {
            setCreationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiCreationDate));
        }
    }

    /**
     * @param effectiveDate
     * @see #effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @see #terminationDate
     * @return
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * @param terminationDate
     * @see #terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUITerminationDate()
    {
        String date = null;

        if (getTerminationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getTerminationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUITerminationDate(String uiTerminationDate)
    {
        if (uiTerminationDate != null)
        {
            setTerminationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiTerminationDate));
        }
    }

    /**
     * @see #caseAssociationCode
     * @return
     */
    public String getCaseAssociationCode()
    {
        return caseAssociationCode;
    }

    /**
     * @param caseAssociationCode
     * @see #caseAssociationCode
     */
    public void setCaseAssociationCode(String caseAssociationCode)
    {
        this.caseAssociationCode = caseAssociationCode;
    }

    /**
     * @see #contractGroupTypeCT
     * @return
     */
    public String getContractGroupTypeCT()
    {
        return contractGroupTypeCT;
    }

    /**
     * @param contractGroupTypeCT
     * @see #contractGroupTypeCT
     */
    public void setContractGroupTypeCT(String contractGroupTypeCT)
    {
        this.contractGroupTypeCT = contractGroupTypeCT;
    }

    /**
     * @see #groupStatusCT
     * @return
     */
    public String getGroupStatusCT()
    {
        return groupStatusCT;
    }

    /**
     * @param groupStatusCT
     * @see #groupStatusCT
     */
    public void setGroupStatusCT(String groupStatusCT)
    {
        this.groupStatusCT = groupStatusCT;
    }

    /**
     * @see #creationDate
     * @return
     */
    public EDITDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * @param creationDate
     * @see #creationDate
     */
    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * @see #creationOperator
     * @return
     */
    public String getCreationOperator()
    {
        return creationOperator;
    }

    /**
     * @param creationOperator
     * @see #creationOperator
     */
    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
    }

    /**
     * Finder.
     * @param contractGroupPK
     * @return
     */
    public static ContractGroup findBy_ContractGroupPK(Long contractGroupPK)
    {
        return (ContractGroup) SessionHelper.get(ContractGroup.class, contractGroupPK, ContractGroup.DATABASE);
    }
    
    /**
     * Finder. Builds the ContractGroup and its associated BillSchedule and PayrollDeductionSchedule.
     * entities (if they exist). All entities are fetched.
     * @param contractGroupPK
     * @return
     */
    @SuppressWarnings("unchecked")
	public static ContractGroup findBy_ContractGroupPK_V1(Long contractGroupPK)
    {
        ContractGroup contractGroup = null;
    
        String hql = " select contractGroup" + 
                    " from ContractGroup contractGroup" +
                    " left join fetch contractGroup.BillSchedule billSchedule" +
                    " left join fetch contractGroup.PayrollDeductionSchedules" +
                    " where contractGroup.ContractGroupPK = :contractGroupPK";
                    
        EDITMap params = new EDITMap("contractGroupPK", contractGroupPK);                    
                    
        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);
        
        if (!results.isEmpty())
        {
            contractGroup = results.get(0);
        }
        
        return contractGroup;
    }

    /**
     * @see #caseStatusCT
     * @param caseStatusCT
     */
    public void setCaseStatusCT(String caseStatusCT)
    {
        this.caseStatusCT = caseStatusCT;
    }

    /**
     * @see #caseStatusCT
     * @return String
     */
    public String getCaseStatusCT()
    {
        return caseStatusCT;
    }

    /**
     * @see #caseTypeCT
     * @param caseTypeCT
     */
    public void setCaseTypeCT(String caseTypeCT)
    {
        this.caseTypeCT = caseTypeCT;
    }

    /**
     * @see #caseTypeCT
     * @return String
     */
    public String getCaseTypeCT()
    {
        return caseTypeCT;
    }

    /**
     * @see #domicileStateCT
     * @return
     */
    public String getDomicileStateCT()
    {
        return domicileStateCT;
    }

    /**
     * @see #domicileStateCT
     * @param domicileStateCT
     */
    public void setDomicileStateCT(String domicileStateCT)
    {
        this.domicileStateCT = domicileStateCT;
    }

    /**
     * ContractGroupRequirements are specific to a ContractGroup at the
     * highest level. From a business perspective, the highest ContractGroup level
     * would be that of 'Case' (e.g. a Case has Group(s) which has Certificate(s)).
     * 
     * ContractGroupRequirements (like other Requirements) are dynamic. Requirements
     * flagged as automatic will be automatically associated to the entity in question.
     * In the case of ContractGroup, all Requirements (in PRASE) flagged as automatic
     * will be associated to the ContractGroup (Requirements of product key Case ***)
     * automatically if flagged as automatic. 
     * 
     * The dynamic association needs to be controlled. To support this, a system field
     * is added to ContractGroup called "RequirementsGenerated = Y/N". If "Y", then
     * it is unnecessary to attempt to generate the requirements via ContractGroupRequirement
     * a second time.
     * 
     * @see #contractGroupRequirements
     */
    public Set<ContractGroupRequirement> getContractGroupRequirements()
    {
        return this.contractGroupRequirements;
    }


    /**
     * @see #contractGroupRequirements
     * @param contractGroupRequirements
     */
    public void setContractGroupRequirements(Set<ContractGroupRequirement> contractGroupRequirements)
    {
        this.contractGroupRequirements = contractGroupRequirements;
    }

        /**
     * @see #segments
     * @param segments
     */
    public void setSegments(Set<Segment> segments)
    {
        this.segments = segments;
    }

    /**
     * @see #segments
     * @return
     */
    public Set<Segment> getSegments()
    {
        return segments;
    }

    public void addSegment(Segment segment)
    {
        this.segments.add(segment);

        segment.setContractGroup(this);
    }

    public void removeSegment(Segment segment)
    {
        this.segments.remove(segment);

        segment.setContractGroup(null);
    }

    /**
     * @see #agentHierarchies
     * @param agentHierarchies
     */
    public void setAgentHierarchies(Set<AgentHierarchy> agentHierarchies)
    {
        this.agentHierarchies = agentHierarchies;
    }

    /**
     * @see #agentHierarchies
     * @return
     */
    public Set<AgentHierarchy> getAgentHierarchies()
    {
        return agentHierarchies;
    }

    public void addAgentHierarchy(AgentHierarchy agentHierarchy)
    {
        this.getAgentHierarchies().add(agentHierarchy);

        agentHierarchy.setContractGroup(this);

        SessionHelper.saveOrUpdate(agentHierarchy, ContractGroup.DATABASE);
    }

    public void removeAgentHierarchy(AgentHierarchy agentHierarchy)
    {
        this.agentHierarchies.remove(agentHierarchy);

        agentHierarchy.setContractGroup(null);
    }
    
    /**
     * Retrieve the agent snapshots of specific writing agent number/situation code from this contract group's agent hierarchies
     * @param agentNumber
     * @param situationCode
     * @return
     */
    public AgentSnapshot[] getAgentSnaphots_FindByAgentNameSitCode(String agentNumber, String situationCode)
    {
    	AgentSnapshot[] agentSnapshots = null;
    	
    	Set<AgentHierarchy> agentHierarchies = this.getAgentHierarchies();
    	
    	Iterator<AgentHierarchy> it = agentHierarchies.iterator();
    	
    	while (it.hasNext())
    	{    		
    		AgentHierarchy agentHierarchy = it.next();
    		AgentSnapshot[] tempAgentSnapshots = agentHierarchy.get_AgentSnapshots();
    		int indexLowestLevelPlacedAgent = tempAgentSnapshots.length-1;
    		
    		Long placedAgentPK = tempAgentSnapshots[indexLowestLevelPlacedAgent].getPlacedAgentFK();
    		PlacedAgent placedAgent = PlacedAgent.findByPK(placedAgentPK);
    		String caseAgentName = placedAgent.getClientRole().getReferenceID();
    	
    		String caseSituationCode = placedAgent.getSituationCode();
    		
    		if ((caseAgentName != null && caseAgentName.equalsIgnoreCase(agentNumber)) &&
    			(caseSituationCode != null && caseSituationCode.equalsIgnoreCase(situationCode)))
    		{
    			agentSnapshots = tempAgentSnapshots;
    			break;
    		}
    	}
    	
    	return agentSnapshots;
    }
        
    /**
     * Adder.
     * @param contractGroupRequirement
     */
    public void addContractGroupRequirement(ContractGroupRequirement contractGroupRequirement)
    {
        this.getContractGroupRequirements().add(contractGroupRequirement);

        contractGroupRequirement.setContractGroup(this);

        SessionHelper.saveOrUpdate(this, ContractGroup.DATABASE);
    }

    public void removeContractGroupRequirement(ContractGroupRequirement contractGroupRequirement)
    {
        this.contractGroupRequirements.remove(contractGroupRequirement);

        contractGroupRequirement.setContractGroup(null);
    }

    /**
     * Getter
     * @return
     */
    public Set<ContractGroupNote> getContractGroupNotes()
    {
        return this.contractGroupNotes;
    }


    /**
     * @see #contractGroupNotes
     * @param contractGroupNotes
     */
    public void setContractGroupNotes(Set<ContractGroupNote> contractGroupNotes)
    {
        this.contractGroupNotes = contractGroupNotes;
    }

    /**
     * Add a contractGroupNote to the set
     * @param contractGroupNote
     */
    public void addContractGroupNote(ContractGroupNote contractGroupNote)
    {
        this.getContractGroupNotes().add(contractGroupNote);

        contractGroupNote.setContractGroup(this);

        SessionHelper.saveOrUpdate(this, ContractGroup.DATABASE);
    }

    public void removeContractGroupNote(ContractGroupNote contractGroupNote)
    {
        this.contractGroupNotes.remove(contractGroupNote);

        contractGroupNote.setContractGroup(null);
    }


    /**
     * Getter
     * @return
     */
    public Set<CaseSetup> getCaseSetups()
    {
        return this.caseSetups;
    }


    /**
     * @see #caseSetups
     * @param caseSetups
     */
    public void setCaseSetups(Set<CaseSetup> caseSetups)
    {
        this.caseSetups = caseSetups;
    }

    /**
     * Add a caseSetups to the set
     * @param caseSetups
     */
    public void addCaseSetup(CaseSetup caseSetup)
    {
        this.getCaseSetups().add(caseSetup);

        caseSetup.setContractGroup(this);

        SessionHelper.saveOrUpdate(this, ContractGroup.DATABASE);
    }

    public void removeCaseSetup(CaseSetup caseSetup)
    {
        this.caseSetups.remove(caseSetup);

        caseSetup.setContractGroup(null);
    }

    /**
     * Removes the specified contractGroup from its collection.
     * 
     * Further delegates the removal process to the specified contractGroup.
     * @param contractGroup
     */
    public void removeContractGroup(ContractGroup contractGroup)
    {
        this.contractGroups.remove(contractGroup);

        contractGroup.remove();
    }

    /**
     * @see #contractGroupInvestments
     * @param contractGroupInvestments
     */
    public void setContractGroupInvestments(Set<ContractGroupInvestment> contractGroupInvestments)
    {
        this.contractGroupInvestments = contractGroupInvestments;
    }

    /**
     * @see #contractGroupInvestments
     * @return
     */
    public Set<ContractGroupInvestment> getContractGroupInvestments()
    {
        return contractGroupInvestments;
    }

    /**
     * @see #departmentLocations
     * @param departmentLocations
     */
    public void setDepartmentLocations(Set<DepartmentLocation> departmentLocations)
    {
        this.departmentLocations = departmentLocations;
    }

    /**
     * @see #departmentLocations
     * @return
     */
    public Set<DepartmentLocation> getDepartmentLocations()
    {
        return departmentLocations;
    }

    public void addDepartmentLocation(DepartmentLocation departmentLocation)
    {
        this.getDepartmentLocations().add(departmentLocation);

        departmentLocation.setContractGroup(this);

        SessionHelper.saveOrUpdate(departmentLocation, ContractGroup.DATABASE);
    }

    /**
     * @see #enrollments
     * @param enrollments
     */
    public void setEnrollments(Set<Enrollment> enrollments)
    {
        this.enrollments = enrollments;
    }

    /**
     * @see #enrollments
     * @return
     */
    public Set<Enrollment> getEnrollments()
    {
        return enrollments;
    }

    public void addEnrollment(Enrollment enrollment)
    {
        this.getEnrollments().add(enrollment);

        enrollment.setContractGroup(this);

        SessionHelper.saveOrUpdate(enrollment, ContractGroup.DATABASE);
    }

    /**
     * @see #caseStatusChangeHistories
     * @param caseStatusChangeHistories
     */
    public void setCaseStatusChangeHistories(Set<CaseStatusChangeHistory> caseStatusChangeHistories)
    {
        this.caseStatusChangeHistories = caseStatusChangeHistories;
    }

    /**
     * @see #caseStatusChangeHistories
     * @return
     */
    public Set<CaseStatusChangeHistory> getCaseStatusChangeHistories()
    {
        return caseStatusChangeHistories;
    }

    public void addCaseStatusChangeHistory(CaseStatusChangeHistory caseStatusChangeHistory)
    {
        this.getCaseStatusChangeHistories().add(caseStatusChangeHistory);

        caseStatusChangeHistory.setContractGroup(this);

        SessionHelper.saveOrUpdate(caseStatusChangeHistory, ContractGroup.DATABASE);
    }

    /**
     * This would only apply to a 'Case' ContractGroup; any other would 
     * return false by definition. If this ContractGroup is a 'Case', and
     * the 'contractRequirementsGenerated' field is currently set to 'N', then they should
     * be generated, and this method would return true.
     * 
     * Note: It is assumed that the candidate Requirements have already been mapped to 
     * the system product key (Case ****).
     * @return boolean
     * @see #contractRequirementsGenerated
     */
    private boolean shouldGenerateContractRequirements()
    {
        boolean generateContractRequirements = false;

        if (getContractGroupTypeCT().equals(CONTRACTGROUPTYPECT_CASE))
        {
            if (getContractRequirementsGenerated().equals(REQUIREMENTS_GENERATED_NO))
            {
                generateContractRequirements = true;
            }
        }

        return generateContractRequirements;
    }

    /**
     * @see #contractRequirementsGenerated
     * @param contractRequirementsGenerated
     */
    public void setContractRequirementsGenerated(String contractRequirementsGenerated)
    {
        this.contractRequirementsGenerated = contractRequirementsGenerated;
    }

    /**
     * @see #contractRequirementsGenerated
     * @return
     */
    public String getContractRequirementsGenerated()
    {
        return contractRequirementsGenerated;
    }

    /**
     * Setter.
     * @param groupTrustStateCT
     */
    public void setGroupTrustStateCT(String groupTrustStateCT)
    {
        this.groupTrustStateCT = groupTrustStateCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupTrustStateCT()
    {
        return groupTrustStateCT;
    }

    /**
     * Setter.
     * @param requirementNotifyDayCT
     */
    public void setRequirementNotifyDayCT(String requirementNotifyDayCT)
    {
        this.requirementNotifyDayCT = requirementNotifyDayCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getRequirementNotifyDayCT()
    {
        return requirementNotifyDayCT;
    }

    /**
     * Setter.
     * @param deductionAmtMinTol
     */
    public void setDeductionAmtMinTol(EDITBigDecimal deductionAmtMinTol)
    {
        this.deductionAmtMinTol = deductionAmtMinTol;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDeductionAmtMinTol()
    {
        return deductionAmtMinTol;
    }

    /**
     * Setter.
     * @param deductionAmtMaxTol
     */
    public void  setDeductionAmtMaxTol(EDITBigDecimal deductionAmtMaxTol)
    {
        this.deductionAmtMaxTol = deductionAmtMaxTol;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDeductionAmtMaxTol()
    {
        return deductionAmtMaxTol;
    }

    /**
     * Finds all candidate Requirements mapped to the system level 'Case'
     * product key. Every Requirement flagged as 'automatic' will
     * be associated to this ContractGroup via the ContractGroupRequirement
     * entity. Once completed, this ContractGroup's 'contractRequirementsGenerated'
     * state is set to 'Y' so that this process is not repeated every time
     * the ContractGroupRequirements are requested.
     *
     */
    private void generateAutomaticContractGroupRequirements()
    {
        // This is assumed to exist - accept the null as an error if need-be.
        ProductStructure caseProductStructure = ProductStructure.findBy_CompanyName_MPN_GPN_AN_BCN("Case", "*", "*", "*", "*");

        FilteredRequirement[] automaticFilteredRequirements = FilteredRequirement.findBy_ProductStructureFK_And_ManualInd(caseProductStructure.getProductStructurePK(), Requirement.MANUALIND_NO);

        for (FilteredRequirement filteredRequirement: automaticFilteredRequirements)
        {
            ContractGroupRequirement contractGroupRequirement = (ContractGroupRequirement) SessionHelper.newInstance(ContractGroupRequirement.class, ContractGroup.DATABASE);

            contractGroupRequirement.setContractGroup(this);

            contractGroupRequirement.setFilteredRequirement(filteredRequirement);
        }

        // Once genererated, flag this so that it doees not happen again.
        setContractRequirementsGenerated(REQUIREMENTS_GENERATED_YES);
    }

    /**
     * A Case ContractGroup can be saved without going through normal validation via the object model or PRASE's validation 
     * framework. In this state, any/all of the Case's fields may be missing. Even in this incomplete state, the
     * Case is [still required] to have an associated ClientDetail (the owner). It is for this reason that
     * a minimal ContractClient, ClientRole, and ClientDetail are created (if necessary). Additionally, ContractGroupRequirements
     * need to be built for all automatic Requirements associated to the "Case" (system) product key.
     * 
     * These required steps go beyond what is feasible within a normal Constructor justifying 
     * this builder method.
     */
    public static void buildCaseWithoutValidation(ContractGroup contractGroup, Long clientDetailPK)
    {
        if (contractGroup.shouldGenerateContractRequirements())
        {
            contractGroup.generateAutomaticContractGroupRequirements();
        }

        if (contractGroup.shouldBuildRole(clientDetailPK, ClientRole.ROLETYPECT_CASE, contractGroup.getContractGroupNumber()))
        {
            contractGroup.buildRole(clientDetailPK, ClientRole.ROLETYPECT_CASE, contractGroup.getContractGroupNumber());
        }
        else
        {
            contractGroup.retrieveRole(clientDetailPK, ClientRole.ROLETYPECT_CASE, contractGroup.getContractGroupNumber());
        }
    }
    
    /**
     * A Group ContractGroup can be saved without going through normal validation via the object model or PRASE's validation 
     * framework. In this state, any/all of the Group's fields may be missing. Even in this incomplete state, the
     * Group is [still required] to have an associated ClientDetail (the owner). It is for this reason that
     * a minimal ContractClient, ClientRole, and ClientDetail are created (if necessary). 
     * 
     * These required steps go beyond what is feasible within a normal Constructor justifying 
     * this builder method.
     * @param groupContractGroup the targeted Group ContractGroup
     * @param caseContractGroup the parent Case to the specified Group ContractGroup
     * @param clientDetailPK the associated ClientDetail
     */
    public static void buildGroupWithoutValidation(ContractGroup groupContractGroup, ContractGroup caseContractGroup, Long clientDetailPK)
    {
        if (groupContractGroup.shouldBuildRole(clientDetailPK, ClientRole.ROLETYPECT_GROUP, groupContractGroup.getContractGroupNumber()))
        {
            groupContractGroup.buildRole(clientDetailPK, ClientRole.ROLETYPECT_GROUP, groupContractGroup.getContractGroupNumber());
        }
        else
        {
            groupContractGroup.retrieveRole(clientDetailPK, ClientRole.ROLETYPECT_GROUP, groupContractGroup.getContractGroupNumber());
        }

        groupContractGroup.setContractGroup(caseContractGroup);
    }    

    /**
     * Builds an "Case" ContractClient and associates it to
     * this ContractGroup. [No] check is done to see if
     * an existing owner already exists.
     */
    private void buildRole(Long clientDetailPK, String roleType, String contractGroupNumber)
    {
        ClientDetail clientDetail = ClientDetail.findBy_ClientDetailPK(clientDetailPK);

        ClientRole clientRole = (ClientRole) SessionHelper.newInstance(ClientRole.class, ContractGroup.DATABASE);

        clientRole.setRoleTypeCT(roleType);

        clientRole.setClientDetail(clientDetail);

        clientRole.setReferenceID(contractGroupNumber);

        clientRole.setOverrideStatus("P");

        this.setClientRole(clientRole);
    }

    private void retrieveRole(Long clientDetailPK, String roleType, String referenceID)
    {
        ClientDetail clientDetail = ClientDetail.findBy_ClientDetailPK(clientDetailPK);

        ClientRole ownerClientRole = ClientRole.findBy_ClientDetail_RoleTypeCT_ReferenceID(clientDetail, roleType, referenceID);

        this.setClientRole(ownerClientRole);
    }

    /**
     * The state of this ContractGroup may be incomplete requiring
     * the association of a "stubbed-out" ContractClient [and] the
     * association of a "stubbed-out" ClientRole to the specified
     * ClientDetailPK.
     * @return
     */
    private boolean shouldBuildRole(Long clientDetailPK, String roleType, String contractGroupNumber)
    {
        boolean shouldBuildRole = false;

        ClientDetail clientDetail = ClientDetail.findBy_ClientDetailPK(clientDetailPK);

        ClientRole role = ClientRole.findBy_ClientDetail_RoleTypeCT_ReferenceID(clientDetail, roleType, contractGroupNumber);

        if (role == null)
        {
            shouldBuildRole = true;
        }

        return shouldBuildRole;
    }

    /**
     * True if a ContractGroup of type "Case" exists by the specified contractGroupNumber,
     * false otherwise.
     * @param contractGroupNumber
     * @return boolean.
     */
    public static boolean contractGroupExists(String contractGroupNumber)
    {
        boolean caseExists = false;

        ContractGroup contractGroup = ContractGroup.findBy_ContractGroupNumber(contractGroupNumber);

        if (contractGroup != null)
        {
            caseExists = true;
        }

        return caseExists;
    }

    /**
     * Finder.
     * @param contractGroupNumber
     * @return
     */
    @SuppressWarnings("unchecked")
	public static ContractGroup findBy_ContractGroupNumber_ContractGroupTypeCT(String contractGroupNumber, String contractGroupTypeCT)
    {
        ContractGroup contractGroup = null;

        String hql = " from ContractGroup contractGroup" +
                    " where contractGroup.ContractGroupNumber = :contractGroupNumber" +
                    " and contractGroup.ContractGroupTypeCT = :contractGroupTypeCT";

        EDITMap params = new EDITMap("contractGroupNumber", contractGroupNumber)
                        .put("contractGroupTypeCT", contractGroupTypeCT);

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        if (!results.isEmpty())
        {
            contractGroup = results.get(0);
        }

        return contractGroup;
    }

    /**
     * Finder.
     * @param groupName                     name of the ContractGroup (which is the CorporateName on the associated
     *                                      ClientDetail)
     * @param contractGroupTypeCT           the ContractGroupTypeCT field on the ContractGroups to be found
     *
     * @return  all found ContractGroups
     */
    @SuppressWarnings("unchecked")
	public static ContractGroup[] findBy_GroupName_ContractGroupTypeCT(String groupName, String contractGroupTypeCT)
    {
        String hql = " from ContractGroup contractGroup" +
                     " join fetch contractGroup.ClientRole clientRole" +
                     " join fetch clientRole.ClientDetail clientDetail" +
                     " where clientDetail.CorporateName like :groupName" +
                     " and contractGroup.ContractGroupTypeCT = :contractGroupTypeCT";

        EDITMap params = new EDITMap("groupName", groupName + "%")
                        .put("contractGroupTypeCT", contractGroupTypeCT);

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        return results.toArray(new ContractGroup[results.size()]);
    }

    /**
     * Finder.
     * @param contractGroupFK
     * @return
     */
    @SuppressWarnings("unchecked")
	public static ContractGroup[] findBy_ContractGroupFK_ContractGroupTypeCT(Long contractGroupFK, String contractGroupTypeCT)
    {
        //ContractGroup contractGroup = null;

        String hql = " from ContractGroup contractGroup" +
                    " where contractGroup.ContractGroupFK = :contractGroupFK" +
                    " and contractGroup.ContractGroupTypeCT = :contractGroupTypeCT";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK)
                        .put("contractGroupTypeCT", contractGroupTypeCT);

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        return results.toArray(new ContractGroup[results.size()]);
    }    
    
    /**
     * Finder.
     * @param contractGroupNumber
     * @return
     */
    @SuppressWarnings("unchecked")
	public static ContractGroup findBy_ContractGroupNumber(String contractGroupNumber)
    {
        ContractGroup contractGroup = null;

        String hql = "from ContractGroup contractGroup where contractGroup.ContractGroupNumber = :contractGroupNumber";

        EDITMap params = new EDITMap("contractGroupNumber", contractGroupNumber);

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        if (!results.isEmpty())
        {
            contractGroup = results.get(0);
        }

        return contractGroup;
    }
    

    /**
     * A ContractGroup can have one and only one associated ClientDetail via the ClientRole of owner.
     * @return
     */
    public ClientDetail getOwnerClient()
    {
        ClientRole clientRole = this.getClientRole();

        ClientDetail clientDetail = clientRole.getClientDetail();

        return clientDetail;
    }
    
	public static ContractGroup[] findContractGroupsForNachaFile(String companyPrefix, String processDate) {
		String sql = "SELECT distinct cg.* " + 
				"  FROM Segment s " + 
				"  inner join BillSchedule b on b.BillSchedulePK = s.BillScheduleFK " + 
				"  inner join ContractGroup cg on cg.ContractGroupPK = s.ContractGroupFK " + 
				"  inner join ClientRole cr on cg.ClientRoleFK = cr.ClientRolePK " +
				"  inner join ClientDetail cd on cr.ClientDetailFK = cd.ClientDetailPK " +
				//"  inner join Preference p on p.ClientDetailFK = cd.ClientDetailPK " +
				"  where b.BillMethodCT = 'EFT' and b.BillTypeCT = 'INDIV' " +
				"  and b.NextBillExtractDate <= :processDate" + 
	            "  and s.ContractNumber like '" + companyPrefix + "%' "  + 
	            //"  and p.PreferenceTypeCT != 'Bank' " +
				"  and s.SegmentFK is null and s.SegmentStatusCT in ('Active', 'Submitted', 'IssuePendingPremium')";

		String hql = "select distinct segment.ContractGroupFK from Segment segment  " + 
	                " join fetch segment.BillSchedule billSchedule " +
	                " where segment.SegmentStatusCT in ('Active', 'IssuePendingPremium', 'Submitted') " +
	                " and segment.ContractNumber like '" + companyPrefix + "%' "  + 
	                " and segment.SegmentFK is null " +
	                " and billSchedule.BillTypeCT = 'INDIV' and billSchedule.BillMethodCT = 'EFT' " +
	                " and billSchedule.NextBillExtractDate <= :processDate "; 

  //      Map params = new HashMap();
   //     params.put("processDate", new EDITDate(processDate));
        
        SQLQuery query = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS).createSQLQuery(sql);

        query.addEntity(ContractGroup.class);
        query.setParameter("processDate", processDate);

        List<ContractGroup> results = query.list();
        //List<Long> results = SessionHelper.executeHQL(hql, params, Segment.DATABASE);

		if (results == null || results.isEmpty()) {
            return null;
		} else {
            return (ContractGroup[])results.toArray(new ContractGroup[results.size()]);
        }
    }

    /**
     * Finds all ContractGroups via partial name of the associated ClientDetail.
     * Join fetches are done between ContractGroup.ContractClient.ClientRole.ClientDetail
     * since ClientDetail is one of the intended entities.
     * @param partialName
     * @return
     */
    @SuppressWarnings("unchecked")
	public static ContractGroup[] findBy_PartialName_AssociatedWithContractGroup(String partialName)
    {
        String hql = " select contractGroup from ContractGroup contractGroup" +
                     " join fetch contractGroup.ClientRole clientRole" +
                     " join fetch clientRole.ClientDetail clientDetail" +
                     " where (upper(clientDetail.LastName) like upper(:partialName)" +
                     " or upper(clientDetail.CorporateName) like upper(:partialName))" +
                     " and contractGroup.ContractGroupTypeCT = :contractGroupTypeCT";

        EDITMap params = new EDITMap();

        params.put("partialName", "%" + partialName.trim() + "%");
        params.put("contractGroupTypeCT", "Case");

        List<ClientDetail> results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE));

        return results.toArray(new ContractGroup[results.size()]);
    }

    @SuppressWarnings("unchecked")
	public static ContractGroup[] findAllGroupContractGroups()
    {
        String hql = " select contractGroup from ContractGroup contractGroup" +
                     " where contractGroup.ContractGroupTypeCT = :contractGroupTypeCT";

        EDITMap params = new EDITMap();
        params.put("contractGroupTypeCT", "Group");

        List<ClientDetail> results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE));

        return results.toArray(new ContractGroup[results.size()]);
    }

    /**
     * Finds the ContractGroup for the given ContractGroupPK
     * @param contractGroupPK
     * @return
     */
    @SuppressWarnings("unchecked")
	public static ContractGroup findByPK(Long contractGroupPK)
    {
        String hql = " select contractGroup from ContractGroup contractGroup" +
                     " where contractGroup.ContractGroupPK = :contractGroupPK";

        EDITMap params = new EDITMap();

        params.put("contractGroupPK", contractGroupPK);

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        if (results.size() > 0)
        {
            return (ContractGroup) results.get(0);
        }
        else
        {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
	public static String[] findAllCaseNumbers()
    {
        String hql = "select contractGroup from ContractGroup contractGroup" +
                     " where contractGroup.ContractGroupFK is null";

        EDITMap params = new EDITMap();

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        SessionHelper.clearSessions();

        String[] caseNumbers = new String[results.size()];

        for (int i = 0; i < results.size(); i++)
        {
            caseNumbers[i] = ((ContractGroup) results.get(i)).getContractGroupNumber();
        }

        return caseNumbers;
    }

    @SuppressWarnings("unchecked")
	public static ContractGroup[] findAllCases()
    {
        //ContractGroup[] cases = null;

        String hql = "select contractGroup from ContractGroup contractGroup" +
                     " where contractGroup.ContractGroupFK is null";

        EDITMap params = new EDITMap();

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        return (ContractGroup[]) results.toArray(new ContractGroup[results.size()]);
    }

    public static String[] findGroupNumbersByCase(String caseNumber)
    {
        ContractGroup caseContractGroup = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(caseNumber, ContractGroup.CONTRACTGROUPTYPECT_CASE);

        Set<ContractGroup> groupContractGroups = caseContractGroup.getContractGroups();

        String[] groupNumbers = new String[groupContractGroups.size()];

        Iterator<ContractGroup> it = groupContractGroups.iterator();

        int i = 0;

        while (it.hasNext())
        {
            groupNumbers[i] = (it.next()).getContractGroupNumber();
        }

        SessionHelper.clearSessions();

        return groupNumbers;
    }
    /**
     * @see #filteredProducts
     * @param filteredProducts
     */
    public void setFilteredProducts(Set<FilteredProduct> filteredProducts)
    {
        this.filteredProducts = filteredProducts;
    }

    /**
     * @see #filteredProducts
     * @return
     */
    public Set<FilteredProduct> getFilteredProducts()
    {
        return filteredProducts;
    }

    /**
     * @see #mastercontracts
     * @param mastercontracts
     */
    public void setMasterContracts(Set<MasterContract> masterContracts)
    {
        this.masterContracts = masterContracts;
    }

    /**
     * @see #mastercontracts
     * @return
     */
    public Set<MasterContract> getMasterContracts()
    {
        return masterContracts;
    }
    /**
     * @see #contractGroup
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    /**
     * @see #contractGroup
     * @return
     */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * @see #contractGroup
     * @param contractGroups
     */
    public void setContractGroups(Set<ContractGroup> contractGroups)
    {
        this.contractGroups = contractGroups;
    }

    /**
     * @see #contractGroup
     * @return
     */
    public Set<ContractGroup> getContractGroups()
    {
        return contractGroups;
    }

    /**
     * @see #payrollDeductionSchedules
     * @param payrollDeductionSchedules
     */
    public void setPayrollDeductionSchedules(Set<PayrollDeductionSchedule> payrollDeductionSchedules)
    {
        this.payrollDeductionSchedules = payrollDeductionSchedules;
    }

    /**
     * @see #payrollDeductionSchedules
     * @return
     */
    public Set<PayrollDeductionSchedule> getPayrollDeductionSchedules()
    {
        return payrollDeductionSchedules;
    }

    public BillSchedule getBillSchedule()
    {
        return billSchedule;
    }

    public void setBillSchedule(BillSchedule billSchedule)
    {
        this.billSchedule = billSchedule;
    }

    public Long getBillScheduleFK()
    {
        return this.billScheduleFK;
    }

    public void setBillScheduleFK(Long billScheduleFK)
    {
        this.billScheduleFK = billScheduleFK;
    }

    /**
     * Determines if this ContractGroup is a Case or not.
     *
     * @return  true if this ContractGroup is a Case, false otherwise
     */
    public boolean isCase()
    {
        if (this.getContractGroupTypeCT().equalsIgnoreCase(ContractGroup.CONTRACTGROUPTYPECT_CASE))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines if this ContractGroup is a Group or not.
     *
     * @return  true if this ContractGroup is a Group, false otherwise
     */
    public boolean isGroup()
    {
        if (this.getContractGroupTypeCT().equalsIgnoreCase(ContractGroup.CONTRACTGROUPTYPECT_GROUP))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @see #batchContractSetups
     * @param batchContractSetups
     */
    public void setBatchContractSetups(Set<BatchContractSetup> batchContractSetups)
    {
        this.batchContractSetups = batchContractSetups;
    }

    /**
     * @see #batchContractSetups
     * @return
     */
    public Set<BatchContractSetup> getBatchContractSetups()
    {
        return batchContractSetups;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContractGroup.DATABASE;
    }

    /**
     * Nullifies it parent references and deletes itself.
     */
    public void remove()
    {
        setContractGroup(null);
        
        SessionHelper.delete(this, getDatabase());
    }

    /**
     * 
     * @param filteredRequirement
     * @return
     */
    public boolean filteredRequirementExists(FilteredRequirement filteredRequirement)
    {
        ContractGroupRequirement contractGroupRequirement = ContractGroupRequirement.findBy_ContractGroup_FilteredRequirement(this, filteredRequirement);
        
        return (contractGroupRequirement != null);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        ClientDetail clientDetail = this.getClientRole().getClientDetail();
        ClientAddress clientAddress = clientDetail.getPrimaryAddress();
        if (clientAddress == null)
        {
            clientAddress = clientDetail.getBusinessAddress();
        }

        if (stagingContext.getContractGroupType().equalsIgnoreCase("Case"))
        {
            staging.Case stagingCase = new staging.Case();

            stagingCase.setCorporateName(clientDetail.getCorporateName());
            if (clientAddress != null)
            {
                stagingCase.setAddressLine1(clientAddress.getAddressLine1());
                stagingCase.setAddressLine2(clientAddress.getAddressLine2());
                stagingCase.setAddressLine3(clientAddress.getAddressLine3());
                stagingCase.setAddressLine4(clientAddress.getAddressLine4());
                stagingCase.setCity(clientAddress.getCity());
                stagingCase.setState(clientAddress.getStateCT());
                stagingCase.setZip(clientAddress.getZipCode());
            }
            stagingCase.setSicCode(clientDetail.getSICCodeCT());

            stagingCase.setContractGroupNumber(this.contractGroupNumber);
            stagingCase.setContractGroupType(this.contractGroupTypeCT);

            stagingCase.setCaseStatus(this.caseStatusCT);
            stagingCase.setCaseType(this.caseTypeCT);
            stagingCase.setCreationDate(this.creationDate);
            stagingCase.setDomicileState(this.domicileStateCT);
            stagingCase.setGroupTrustState(this.groupTrustStateCT);
            stagingCase.setActiveEligibilityHours(this.activeEligibilityHours);
            stagingCase.setIneligibilityPeriodType(this.ineligibilityPeriodType);
            stagingCase.setIneligibilityPeriodUnits(this.ineligibilityPeriodUnits);

            Set<FilteredProduct> filteredProducts = this.getFilteredProducts();
            Iterator<FilteredProduct> it = filteredProducts.iterator();
            while (it.hasNext())
            {
                FilteredProduct filteredProduct = it.next();
                ProductStructure productStructure = ProductStructure.findByPK(filteredProduct.getProductStructureFK());

                staging.FilteredProduct stagingFilteredProduct = new staging.FilteredProduct();
                stagingFilteredProduct.setCase(stagingCase);
                stagingFilteredProduct.setCompanyName(productStructure.getCompanyName());
                stagingFilteredProduct.setMarketingPackage(productStructure.getMarketingPackageName());
                stagingFilteredProduct.setGroupName(productStructure.getGroupProductName());
                stagingFilteredProduct.setArea(productStructure.getAreaName());
                stagingFilteredProduct.setBusinessContract(productStructure.getBusinessContractName());

//                stagingCase.addFilteredProduct(stagingFilteredProduct);
                stagingFilteredProduct.setCase(stagingCase);
                
                SessionHelper.saveOrUpdate(stagingFilteredProduct, database);

            }
            
            stagingCase.setStaging(stagingContext.getStaging());

            stagingContext.setCurrentCase(stagingCase);

            SessionHelper.saveOrUpdate(stagingCase, database);
        }
        else
        {
            staging.Group group = new staging.Group();

            group.setCorporateName(clientDetail.getCorporateName());
            if (clientAddress != null)
            {
                group.setAddressLine1(clientAddress.getAddressLine1());
                group.setAddressLine2(clientAddress.getAddressLine2());
                group.setAddressLine3(clientAddress.getAddressLine3());
                group.setAddressLine4(clientAddress.getAddressLine4());
                group.setCity(clientAddress.getCity());
                group.setState(clientAddress.getStateCT());
                group.setZip(clientAddress.getZipCode());
            }

            group.setGroupStatus(this.getGroupStatusCT());

            group.setContractGroupNumber(this.contractGroupNumber);
            group.setContractGroupType(this.contractGroupTypeCT);
            group.setBillSchedule(stagingContext.getCurrentBillSchedule());
            group.setContractGroupCase(stagingContext.getCurrentCase());
            group.setActiveEligibilityHours(this.activeEligibilityHours);
            group.setIneligibilityPeriodType(this.ineligibilityPeriodType);
            group.setIneligibilityPeriodUnits(this.ineligibilityPeriodUnits);

            stagingContext.getCurrentCase().addGroup(group);

            Set<BatchContractSetup> groupBCSs = this.getBatchContractSetups();
            
            BatchContractSetup[] groupBatchContractSetups = groupBCSs.toArray(new BatchContractSetup[groupBCSs.size()]);
            
            
            Set<staging.Enrollment> stagingEnrollments = stagingContext.getCurrentCase().getEnrollments();
            Iterator<staging.Enrollment> it = stagingEnrollments.iterator();
            while (it.hasNext())
            {
                staging.Enrollment stagingEnrollment = it.next();
                Set<staging.BatchContractSetup> stagingBatchContractSetups = stagingEnrollment.getBatchContractSetups();
                Iterator<staging.BatchContractSetup> it2 = stagingBatchContractSetups.iterator();
                while (it2.hasNext())
                {
                    staging.BatchContractSetup batchContractSetup = it2.next();
                    for (int i = 0; i < groupBatchContractSetups.length; i++)
                    {
                        if (groupBatchContractSetups[i].getBatchID() != null &&
                            groupBatchContractSetups[i].getBatchID().equalsIgnoreCase(batchContractSetup.getBatchID()))
                        {
                            group.addBatchContractSetups(batchContractSetup);

                            batchContractSetup.setGroup(group);
                        }
                    }
                }
            }

            stagingContext.setCurrentGroup(group);

            SessionHelper.saveOrUpdate(group, database);
        }

        return stagingContext;
    }

  /**
   * Finder.
   * @param billSchedulePK
   * @return
   */
  @SuppressWarnings("unchecked")
public static ContractGroup findBy_BillSchedulePK(Long billSchedulePK)
  {
      ContractGroup contractGroup = null;
    
      String hql = "from ContractGroup contractGroup " +
                   " where contractGroup.BillScheduleFK = :billSchedulePK";
      
      EDITMap params = new EDITMap("billSchedulePK", billSchedulePK);
      
      List<ContractGroup> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
      
      if (!results.isEmpty())
      {
        contractGroup = results.get(0);
      }
    
    return contractGroup;
  }

    @SuppressWarnings("unchecked")
	public static ContractGroup findBy_ContractGroupFK(Long contractGroupFK)
    {
        ContractGroup contractGroup = null;

        String hql = " select contractGroup" +
                    " from ContractGroup contractGroup" +
                    " join contractGroup.ContractGroups groups" +
                    " where groups.ContractGroupPK = :contractGroupFK";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);

        List<ContractGroup> results = SessionHelper.executeHQL(hql, params, ContractGroup.DATABASE);

        if (!results.isEmpty())
        {
            contractGroup = results.get(0);
        }

        return contractGroup;
    }

    public static boolean doesCaseHaveGroups(Long contractGroupPK)
    {
        boolean groupExists = false;

        ContractGroup[] contractGroups = ContractGroup.findBy_ContractGroupFK_ContractGroupTypeCT(contractGroupPK, "Group");

        if (contractGroups.length > 0)
        {
            groupExists = true;
        }

        return groupExists;
    }

    public static boolean doesGroupHaveBatchContracts(Long contractGroupPK)
    {
        boolean batchContractExists = false;

        BatchContractSetup[] batchContractSetups = BatchContractSetup.findBy_ContractGroupFK(contractGroupPK);

        if (batchContractSetups != null)
        {
            batchContractExists = true;
        }

        return batchContractExists;
    }

    public static boolean doesGroupHavePolicies(Long contractGroupPK)
    {
        boolean policiesExist = false;

        Segment[] segments = Segment.findBy_ContractGroupFK(contractGroupPK);

        if (segments != null)
        {
        	policiesExist = true;
        }

        return policiesExist;
    }

    public static boolean isCaseAttachedToContract(Long contractGroupPK, Long groupContractGroupPK)
    {
        boolean contractExists = false;

        ContractGroup[] contractGroups = ContractGroup.findBy_ContractGroupFK_ContractGroupTypeCT(contractGroupPK, "Group");

        if (contractGroups != null)
        {
            for (int i = 0; i < contractGroups.length; i++)
            {
                if (groupContractGroupPK == null || contractGroups[i].getContractGroupPK().equals(groupContractGroupPK))
                {
                    Segment[] segment = Segment.findByContractGroupFK(contractGroups[i].getContractGroupPK());
                    if (segment != null)
                    {
                        contractExists = true;
                        break;
                    }
                }
            }
        }

        return contractExists;
    }

    /**
     * @return the contractTreaties
     */
    public Set<ContractTreaty> getContractTreaties()
    {
        return contractTreaties;
    }

    /**
     * @param contractTreaties the contractTreaties to set
     */
    public void setContractTreaties(Set<ContractTreaty> contractTreaties)
    {
        this.contractTreaties = contractTreaties;
    }

	public BigDecimal getIneligibilityPeriodUnits() {
		return ineligibilityPeriodUnits;
	}

	public void setIneligibilityPeriodUnits(BigDecimal ineligibilityPeriodUnits) {
		this.ineligibilityPeriodUnits = ineligibilityPeriodUnits;
	}

	public String getIneligibilityPeriodType() {
		return ineligibilityPeriodType;
	}

	public void setIneligibilityPeriodType(String ineligibilityPeriodType) {
		this.ineligibilityPeriodType = ineligibilityPeriodType;
	}

	public BigDecimal getActiveEligibilityHours() {
		return activeEligibilityHours;
	}

	public void setActiveEligibilityHours(BigDecimal activeEligibilityHours) {
		this.activeEligibilityHours = activeEligibilityHours;
	}
}
