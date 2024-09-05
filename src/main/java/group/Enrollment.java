/*
 * User: sdorman
 * Date: Jun 19, 2007
 * Time: 12:45:46 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package group;

import agent.Agent;
import agent.PlacedAgent;

import client.ClientDetail;

import edit.common.*;
import edit.common.exceptions.EDITCaseException;

import edit.services.db.hibernate.*;

import java.util.*;

import fission.utility.DateTimeUtil;

import role.ClientRole;

import staging.IStaging;
import staging.StagingContext;


public class Enrollment extends HibernateEntity implements IStaging
{
    /**
     * Primary key
     */
    private Long enrollmentPK;

    private EDITDate beginningPolicyDate;
    private EDITDate endingPolicyDate;
    private String enrollmentTypeCT;
    private int numberOfEligibles;
    private EDITDate offerDateNeeded;
    private EDITDate anticipatedEnrollmentDate;
    private EDITDate anticipatedInhouseDate;

    //  Parents
    private ContractGroup contractGroup;
    private Long contractGroupFK;

    //  Children
    private Set<CaseProductUnderwriting> caseProductUnderwritings;
    private Set<ProjectedBusinessByMonth> projectedBusinessByMonths;
    private Set<BatchContractSetup> batchContractSetups;
    private Set<EnrollmentState> enrollmentStates;
    private Set<EnrollmentLeadServiceAgent> enrollmentLeadServiceAgents;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Constructor
     */
    public Enrollment()
    {
        init();
    }

    private void init()
    {
        caseProductUnderwritings = new HashSet<CaseProductUnderwriting>();
        projectedBusinessByMonths = new HashSet<ProjectedBusinessByMonth>();
        batchContractSetups = new HashSet<BatchContractSetup>();
        enrollmentStates = new HashSet<EnrollmentState>();
        enrollmentLeadServiceAgents = new HashSet<EnrollmentLeadServiceAgent>();
    }

    public Long getEnrollmentPK()
    {
        return enrollmentPK;
    }

    public void setEnrollmentPK(Long enrollmentPK)
    {
        this.enrollmentPK = enrollmentPK;
    }

    public EDITDate getBeginningPolicyDate()
    {
        return beginningPolicyDate;
    }

    public void setBeginningPolicyDate(EDITDate beginningPolicyDate)
    {
        this.beginningPolicyDate = beginningPolicyDate;
    }

    public EDITDate getEndingPolicyDate()
    {
        return endingPolicyDate;
    }

    public void setEndingPolicyDate(EDITDate endingPolicyDate)
    {
        this.endingPolicyDate = endingPolicyDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIBeginningPolicyDate()
    {
        String date = null;

        if (getBeginningPolicyDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getBeginningPolicyDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIBeginningPolicyDate(String uiBeginningPolicyDate)
    {
        if (uiBeginningPolicyDate != null)
        {
            setBeginningPolicyDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiBeginningPolicyDate));
        }
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIEndingPolicyDate()
    {
        String date = null;

        if (getEndingPolicyDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getEndingPolicyDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIEndingPolicyDate(String uiEndingPolicyDate)
    {
        if (uiEndingPolicyDate != null)
        {
            setEndingPolicyDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiEndingPolicyDate));
        }
    }

    public String getEnrollmentTypeCT()
    {
        return enrollmentTypeCT;
    }

    public void setEnrollmentTypeCT(String enrollmentTypeCT)
    {
        this.enrollmentTypeCT = enrollmentTypeCT;
    }

    public int getNumberOfEligibles()
    {
        return numberOfEligibles;
    }

    public void setNumberOfEligibles(int numberOfEligibles)
    {
        this.numberOfEligibles = numberOfEligibles;
    }

    public EDITDate getOfferDateNeeded()
    {
        return offerDateNeeded;
    }

    public void setOfferDateNeeded(EDITDate offerDateNeeded)
    {
        this.offerDateNeeded = offerDateNeeded;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIOfferDateNeeded()
    {
        String date = null;

        if (getOfferDateNeeded() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getOfferDateNeeded());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIOfferDateNeeded(String uiOfferDateNeeded)
    {
        if (uiOfferDateNeeded != null)
        {
            setOfferDateNeeded(DateTimeUtil.getEDITDateFromMMDDYYYY(uiOfferDateNeeded));
        }
    }

    public EDITDate getAnticipatedEnrollmentDate()
    {
        return anticipatedEnrollmentDate;
    }

    public void setAnticipatedEnrollmentDate(EDITDate anticipatedEnrollmentDate)
    {
        this.anticipatedEnrollmentDate = anticipatedEnrollmentDate;
    }


    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIAnticipatedEnrollmentDate()
    {
        String date = null;

        if (getAnticipatedEnrollmentDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getAnticipatedEnrollmentDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIAnticipatedEnrollmentDate(String uiAnticipatedEnrollmentDate)
    {
        if (uiAnticipatedEnrollmentDate != null)
        {
            setAnticipatedEnrollmentDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiAnticipatedEnrollmentDate));
        }
    }

    public EDITDate getAnticipatedInhouseDate()
    {
        return anticipatedInhouseDate;
    }

    public void setAnticipatedInhouseDate(EDITDate anticipatedInhouseDate)
    {
        this.anticipatedInhouseDate = anticipatedInhouseDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIAnticipatedInhouseDate()
    {
        String date = null;

        if (getAnticipatedInhouseDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getAnticipatedInhouseDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIAnticipatedInhouseDate(String uIAnticipatedInhouseDate)
    {
        if (uIAnticipatedInhouseDate != null)
        {
            setAnticipatedInhouseDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uIAnticipatedInhouseDate));
        }
    }

    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    public Long getContractGroupFK()
    {
        return this.contractGroupFK;
    }

    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }

    public Set<CaseProductUnderwriting> getCaseProductUnderwritings()
    {
        return this.caseProductUnderwritings;
    }

    public void setCaseProductUnderwritings(Set<CaseProductUnderwriting> caseProductUnderwritings)
    {
        this.caseProductUnderwritings = caseProductUnderwritings;
    }

    public void addCaseProductUnderwriting(CaseProductUnderwriting caseProductUnderwriting)
    {
        this.caseProductUnderwritings.add(caseProductUnderwriting);

        caseProductUnderwriting.setEnrollment(this);
    }

    public void removeCaseProductUnderwriting(CaseProductUnderwriting caseProductUnderwriting)
    {
        this.caseProductUnderwritings.remove(caseProductUnderwriting);

        caseProductUnderwriting.setEnrollment(null);
    }

    public Set<ProjectedBusinessByMonth> getProjectedBusinessByMonths()
    {
        return this.projectedBusinessByMonths;
    }

    public void setProjectedBusinessByMonths(Set<ProjectedBusinessByMonth> projectedBusinessByMonths)
    {
        this.projectedBusinessByMonths = projectedBusinessByMonths;
    }

    public void addProjectedBusinessByMonth(ProjectedBusinessByMonth projectedBusinessByMonth)
    {
        this.projectedBusinessByMonths.add(projectedBusinessByMonth);

        projectedBusinessByMonth.setEnrollment(this);
    }

    public void removeProjectedBusinessByMonth(ProjectedBusinessByMonth projectedBusinessByMonth)
    {
        this.projectedBusinessByMonths.remove(projectedBusinessByMonth);

        projectedBusinessByMonth.setEnrollment(null);
    }

    public Set<BatchContractSetup> getBatchContractSetups()
    {
        return this.batchContractSetups;
    }

    public void setBatchContractSetups(Set<BatchContractSetup> batchContractSetups)
    {
        this.batchContractSetups = batchContractSetups;
    }

    public void addBatchContractSetup(BatchContractSetup batchContractSetup)
    {
        this.batchContractSetups.add(batchContractSetup);

        batchContractSetup.setEnrollment(this);
    }

    public void removeBatchContractSetup(BatchContractSetup batchContractSetup)
    {
        this.batchContractSetups.remove(batchContractSetup);

        batchContractSetup.setEnrollment(null);
    }

    public Set<EnrollmentState> getEnrollmentStates()
    {
        return this.enrollmentStates;
    }

    public void setEnrollmentStates(Set<EnrollmentState> enrollmentStates)
    {
        this.enrollmentStates = enrollmentStates;
    }

    public void addEnrollmentState(EnrollmentState enrollmentState)
    {
        this.enrollmentStates.add(enrollmentState);

        enrollmentState.setEnrollment(this);
    }

    public void removeEnrollmentState(EnrollmentState enrollmentState)
    {
        this.enrollmentStates.remove(enrollmentState);

        enrollmentState.setEnrollment(null);
    }

    public Set<EnrollmentLeadServiceAgent> getEnrollmentLeadServiceAgents()
    {
        return this.enrollmentLeadServiceAgents;
    }

    public void setEnrollmentLeadServiceAgents(Set<EnrollmentLeadServiceAgent> enrollmentLeadServiceAgents)
    {
        this.enrollmentLeadServiceAgents = enrollmentLeadServiceAgents;
    }

    public void addEnrollmentLeadServiceAgent(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent)
    {
        this.enrollmentLeadServiceAgents.add(enrollmentLeadServiceAgent);

        enrollmentLeadServiceAgent.setEnrollment(this);
    }

    public void removeEnrollmentLeadServiceAgent(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent)
    {
        this.enrollmentLeadServiceAgents.remove(enrollmentLeadServiceAgent);

        enrollmentLeadServiceAgent.setEnrollment(null);
    }
    
    /**
     * Checks to see if there are [any] EnrollmentLeadServiceAgents associated
     * to this Enrollment.
     * @return true if there are any assocaited EnrollmentLeadServiceAgents associated with this Enrollment
     */
    public boolean enrollmentLeadServiceAgentsExist() 
    {
        String hql = "select count(*) from EnrollmentLeadServiceAgent enrollmentLeadServiceAgent where enrollmentLeadServiceAgent.Enrollment = :enrollment";
        
        Map params = new HashMap();
        
        params.put("enrollment", this);
        
        return (SessionHelper.executeHQLForCount(hql, params, getDatabase()) > 0);    
    }

    public String getDatabase()
    {
        return Enrollment.DATABASE;
    }

    /**
     * Determines if the supplied policyDate is within the range of the beginning and ending policy dates.
     *
     * @param policyDate
     *
     * @return  true if >= beginningPolicyDate and <= endingPolicyDate.  False otherwise.  If the endingPolicyDate is
     * null, it is not checked.
     */
    public boolean isPolicyDateInRange(EDITDate policyDate)
    {
        boolean policyDateInRange = false;

        if (policyDate.afterOREqual(this.getBeginningPolicyDate()))
        {
            if (this.getEndingPolicyDate() != null)
            {
                if (policyDate.beforeOREqual(this.getEndingPolicyDate()))
                {
                    //  Successful against both dates
                    policyDateInRange = true;
                }
            }
            else
            {
                //  Ending date is null, don't check it.  It was successful against the beginning date
                policyDateInRange = true;
            }
        }

        return policyDateInRange;
    }

    /**
     * Finds the Enrollment with the given primary key
     *
     * @param enrollmentPK
     *
     * @return
     */
    public static Enrollment findByPK(Long enrollmentPK)
    {
        String hql = " select enrollment from Enrollment enrollment" +
                     " where enrollment.EnrollmentPK = :enrollmentPK";

        EDITMap params = new EDITMap();

        params.put("enrollmentPK", enrollmentPK);

        List<Enrollment> results = SessionHelper.executeHQL(hql, params, Enrollment.DATABASE);

        if (results.size() > 0)
        {
            return (Enrollment) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all Enrollments
     *
     * @return array of Enrollment objects      ;
     */
    public static Enrollment[] findAll()
    {
        String hql = " from Enrollment enrollment";

        EDITMap params = new EDITMap();

        List<Enrollment> results = SessionHelper.executeHQL(hql, params, Enrollment.DATABASE);

        return results.toArray(new Enrollment[results.size()]);
    }

    /**
     * Finder.
     * @param contractGroup
     * @return
     */
    public static Enrollment[] findByContractGroup(Long contractGroupFK)
    {
        String hql = " from Enrollment enrollment" +
                    " where enrollment.ContractGroupFK = :contractGroupFK";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);

        List<Enrollment> results = SessionHelper.executeHQL(hql, params, Enrollment.DATABASE);

        return (Enrollment[]) results.toArray(new Enrollment[results.size()]);
    }

    /**
     * Finds the Enrollment with the greatest BeginningPolicyDate that is less than or equal to the specified
     * beginningPolicyDate - and whose ContractGroupFK matches.
     *
     * @param contractGroupFK
     * @param beginningPolicyDate
     *
     * @return  single Enrollment
     */
    public static Enrollment findByContractGroup_GreatestBeginningPolicyDate(Long contractGroupFK, EDITDate beginningPolicyDate)
    {
        String hql = "select enrollment from Enrollment enrollment" +
                    " where enrollment.BeginningPolicyDate = " +
                    "      (select MAX(enrollment1.BeginningPolicyDate) from Enrollment enrollment1" +
                    "       where enrollment1.BeginningPolicyDate <= :beginningPolicyDate" +
                    "       and enrollment1.ContractGroupFK = :contractGroupFK)";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);
        params.put("beginningPolicyDate", beginningPolicyDate);

        List<Enrollment> results = SessionHelper.executeHQL(hql, params, Enrollment.DATABASE);

        if (results.size() > 0)
        {
            return (Enrollment) results.get(0);
        }
        else
        {
            return null;
        }
    }


    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.Enrollment stagingEnrollment = new staging.Enrollment();

        stagingEnrollment.setStagingCase(stagingContext.getCurrentCase());
        stagingEnrollment.setBeginningPolicyDate(this.beginningPolicyDate);
        stagingEnrollment.setEndingPolicyDate(this.endingPolicyDate);
        stagingEnrollment.setEnrollmentType(this.enrollmentTypeCT);
        stagingEnrollment.setNumberOfEligibles(this.numberOfEligibles);
        stagingEnrollment.setOfferDateNeeded(this.offerDateNeeded);
        stagingEnrollment.setAnticipatedEnrollmentDate(this.anticipatedEnrollmentDate);
        stagingEnrollment.setAnticipatedInhouseDate(this.anticipatedInhouseDate);

        stagingContext.getCurrentCase().addEnrollment(stagingEnrollment);
        stagingContext.setCurrentEnrollment(stagingEnrollment);

        SessionHelper.saveOrUpdate(stagingEnrollment, database);

        return stagingContext;
    }

    /**
     * Checks to see if the EffectiveDate/TerminationDate of the specified EnrollmentLeadServiceAgent overlap with
     * any existing EnrollmentLeadServiceAgent's (for the same Agent) EffectiveDate/Termination date which share a common ClientRole.RoleTypeCT.
     * @param enrollmentLeadServiceAgent
     * @return true if the incoming dates are valid, false otherwise
     */
    public void verifyNonConflictingEnrollmentLeadServiceAgents(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent) throws EDITCaseException
    {
        boolean datesOverlap = false;
        
        Agent agent = enrollmentLeadServiceAgent.getClientRole().getAgent();
        
        Set<EnrollmentLeadServiceAgent> enrollmentLeadServiceAgents = getEnrollmentLeadServiceAgents();
        
        Long enrollmentLeadServiceAgentPK = enrollmentLeadServiceAgent.getEnrollmentLeadServiceAgentPK();
        
        String roleTypeCT = enrollmentLeadServiceAgent.getClientRole().getRoleTypeCT();
        
        if (enrollmentLeadServiceAgents.size() > 0) 
        {
            for (EnrollmentLeadServiceAgent currentEnrollmentLeadServiceAgent:enrollmentLeadServiceAgents) 
            {
                // Don't validate against itself.
                if ((enrollmentLeadServiceAgentPK != null) && currentEnrollmentLeadServiceAgent.getEnrollmentLeadServiceAgentPK().equals(enrollmentLeadServiceAgentPK)) 
                {
                    continue;                    
                }
                
                // Only validate for similar roles
                else if (roleTypeCT.equals(currentEnrollmentLeadServiceAgent.getClientRole().getRoleTypeCT())) 
                {
                    Agent currentAgent = currentEnrollmentLeadServiceAgent.getClientRole().getAgent();
                    
                    // We only care about conflicts for the same Agent.
                    if (currentAgent.getAgentPK().longValue() == agent.getAgentPK().longValue()) 
                    {
                        EDITDate visEffectiveDate = enrollmentLeadServiceAgent.getEffectiveDate();
                        
                        EDITDate visTerminationDate = enrollmentLeadServiceAgent.getTerminationDate();
                        
                        boolean visEffectiveDateInRange = currentEnrollmentLeadServiceAgent.dateIsInRange(visEffectiveDate);
                        
                        boolean visTerminationDateInRange = currentEnrollmentLeadServiceAgent.dateIsInRange(visTerminationDate);
                        
                        if (visEffectiveDateInRange || visTerminationDateInRange)
                        {
                            datesOverlap = true;
                            
                            break;
                        }                        
                    }
                }
            }
        }
        
        if (datesOverlap) 
        {
            throw new EDITCaseException("EffectiveDate/TerminationDate can not Overlap between Enrollment Lead Service Agents");    
        }        
    }
    
    /**
     * Saves the specified EnrollmentLeadService while establishing EnrollmentLeadServiceAgent -> ClientRole -> ClientDetail associations.
     * The associations are purposely set as many-to-one (not one-to-many)(i.e. not bidirectional) for performance reasons.
     * The save is not done blindly in that the following rules must be enforced:
     * 
     * 1. An existing ClientRole of the specified roleTypeCT/referenceID/Agent combination is checked for its existence.
     * 2. Case # 1: If #1 does not exist, then create a new ClientRole and be done with it since there is no concern for overlapping dates.
     * 3. Case # 2: If #1 does exist, then we need to take overlapping dates into consideration. The target enrollmentLeadServiceAgent can't
     *    have its Start/Stop dates overlap with any existing EnrollmentLeadServiceAgents. If so, the process stops and an exception
     *    is thrown stating the problem of overlapping dates.
     *    
     * @param enrollmentLeadServiceAgent
     * @param agent
     * @param clientDetail
     * @param roleTypeCT
     * @param referenceID
     */
    public void saveEnrollmentLeadServiceAgent(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent, Agent agent, ClientDetail clientDetail, String roleTypeCT, String referenceID) throws EDITCaseException
    {
        ClientRole clientRole = ClientRole.findBy_ClientDetail_RoleTypeCT_ReferenceID(clientDetail, roleTypeCT, referenceID);
        
        // Case # 1 - there is no existing ClientRole
        if (clientRole == null) 
        {
            clientRole = ClientRole.build(roleTypeCT, referenceID, agent, clientDetail);    
            
            enrollmentLeadServiceAgent.setClientRole(clientRole);
            
            enrollmentLeadServiceAgent.setEnrollment(this);
            
            clientRole.setClientDetail(clientDetail);
            
            clientRole.setAgent(agent);
        }
        
        // Case # 2 - there is an existing ClientRole
        else 
        {
            enrollmentLeadServiceAgent.setEnrollment(this);
                
            enrollmentLeadServiceAgent.setClientRole(clientRole);
            
            verifyNonConflictingEnrollmentLeadServiceAgents(enrollmentLeadServiceAgent);       
        }
    }      
    
    /**
     * Updates the specified EnrollmentLeadServiceAgent with the specified regionCT, effectiveDate, terminationDate while verifying that
     * the effectiveDate, terminationDate do not conflict with any existing EnrollmentLeadServiceAgent.
     * @param enrollmentLeadServiceAgent
     * @param regionCT
     * @param effectiveDate
     * @param terminationDate
     * @throws EDITCaseException if the effectiveDate, terminationDate conflict with an existing EnrollmentLeadServiceAgent
     */
    public void updateEnrollmentLeadServiceAgent(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent, String regionCT, EDITDate effectiveDate, EDITDate terminationDate) throws EDITCaseException
    {
        enrollmentLeadServiceAgent.setRegionCT(regionCT);
        
        enrollmentLeadServiceAgent.setEffectiveDate(effectiveDate);
        
        enrollmentLeadServiceAgent.setTerminationDate(terminationDate);
        
        verifyNonConflictingEnrollmentLeadServiceAgents(enrollmentLeadServiceAgent);       
    }        
}
