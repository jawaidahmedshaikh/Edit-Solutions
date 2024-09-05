/*
 * User: dlataill
 * Date: Oct 9, 2007
 * Time: 10:10:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Enrollment extends HibernateEntity
{
    /**
     * Primary key
     */
    private Long enrollmentPK;
    private Long caseFK;
    private EDITDate beginningPolicyDate;
    private EDITDate endingPolicyDate;
    private String enrollmentType;
    private int numberOfEligibles;
    private EDITDate offerDateNeeded;
    private EDITDate anticipatedEnrollmentDate;
    private EDITDate anticipatedInhouseDate;

    //  Parents
    private Case stagingCase;

    //  Children
    private Set<ProjectedBusinessByMonth> projectedBusinessByMonths;
    private Set<BatchContractSetup> batchContractSetups;
    private Set<EnrollmentLeadServiceAgent> enrollmentLeadServiceAgents;
    private Set<EnrollmentState> enrollmentStates;
    private Set<CaseProductUnderwriting> caseProductUnderwritings;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.STAGING;

    /**
     * Constructor
     */
    public Enrollment()
    {
        init();
    }

    private void init()
    {
        projectedBusinessByMonths = new HashSet<ProjectedBusinessByMonth>();
        batchContractSetups = new HashSet<BatchContractSetup>();
        enrollmentLeadServiceAgents = new HashSet<EnrollmentLeadServiceAgent>();
        enrollmentStates = new HashSet<EnrollmentState>();
        caseProductUnderwritings = new HashSet<CaseProductUnderwriting>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getEnrollmentPK()
    {
        return enrollmentPK;
    }

    /**
     * Setter.
     * @param enrollmentPK
     */
    public void setEnrollmentPK(Long enrollmentPK)
    {
        this.enrollmentPK = enrollmentPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCaseFK()
    {
        return caseFK;
    }

    /**
     * Setter.
     * @param caseFK
     */
    public void setCaseFK(Long caseFK)
    {
        this.caseFK = caseFK;
    }

    /**
     * Getter.
     * @return
     */
    public Case getStagingCase()
    {
        return stagingCase;
    }

    /**
     * Setter.
     * @param stagingCase
     */
    public void setStagingCase(Case stagingCase)
    {
        this.stagingCase = stagingCase;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getBeginningPolicyDate()
    {
        return beginningPolicyDate;
    }

    /**
     * Setter.
     * @param beginningPolicyDate
     */
    public void setBeginningPolicyDate(EDITDate beginningPolicyDate)
    {
        this.beginningPolicyDate = beginningPolicyDate;
    }
     
    /**
     * Getter.
     * @return
     */
    public EDITDate getEndingPolicyDate()
    {
        return endingPolicyDate;
    }

    /**
     * Setter.
     * @param endingPolicyDate
     */
    public void setEndingPolicyDate(EDITDate endingPolicyDate)
    {
        this.endingPolicyDate = endingPolicyDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getEnrollmentType()
    {
        return enrollmentType;
    }

    /**
     * Setter.
     * @param enrollmentType
     */
    public void setEnrollmentType(String enrollmentType)
    {
        this.enrollmentType = enrollmentType;
    }

    public int getNumberOfEligibles()
    {
        return numberOfEligibles;
    }

    public void setNumberOfEligibles(int numberOfEligibles)
    {
        this.numberOfEligibles = numberOfEligibles;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getOfferDateNeeded()
    {
        return offerDateNeeded;
    }

    /**
     * Setter.
     * @param offerDateNeeded
     */
    public void setOfferDateNeeded(EDITDate offerDateNeeded)
    {
        this.offerDateNeeded = offerDateNeeded;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getAnticipatedEnrollmentDate()
    {
        return anticipatedEnrollmentDate;
    }

    /**
     * Setter.
     * @param anticipatedEnrollmentDate
     */
    public void setAnticipatedEnrollmentDate(EDITDate anticipatedEnrollmentDate)
    {
        this.anticipatedEnrollmentDate = anticipatedEnrollmentDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getAnticipatedInhouseDate()
    {
        return anticipatedInhouseDate;
    }

    /**
     * Setter.
     * @param anticipatedInhouseDate
     */
    public void setAnticipatedInhouseDate(EDITDate anticipatedInhouseDate)
    {
        this.anticipatedInhouseDate = anticipatedInhouseDate;
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

    public String getDatabase()
    {
        return Enrollment.DATABASE;
    }

    public static Enrollment findByCaseFK_BegPolDate(Long caseFK, EDITDate begPolDate, String database)
    {
        String hql = " select e from Enrollment e" +
                    " where e.CaseFK = :caseFK" +
                    " and e.BeginningPolicyDate = :begPolDate";

        EDITMap params = new EDITMap();
        params.put("caseFK", caseFK);
        params.put("begPolDate", begPolDate);

        List<Enrollment> results = SessionHelper.executeHQL(hql, params, database);

        if (results.size() > 0)
        {
            return (Enrollment) results.get(0);
        }
        else
        {
            return null;
        }
    }
}
