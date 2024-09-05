/*
 * User: sprasad
 * Date: Jun 2, 2005
 * Time: 2:58:13 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package casetracking;

import edit.common.EDITDate;
import edit.services.db.hibernate.*;
import client.ClientDetail;
import contract.FilteredRequirement;
import contract.Requirement;

import java.util.Map;
import java.util.HashMap;
import java.util.List;


public class CaseRequirement extends HibernateEntity
{
    public static final String REQUIREMENT_STATUS_CT_OUTSTANDING = "Outstanding";
    public static final String REQUIREMENT_STATUS_CT_RECEIVED = "Received";
    public static final String REQUIREMENT_STATUS_CT_WAIVED = "Waived";
    private Long caseRequirementPK;
    private String requirementStatusCT;
    private EDITDate effectiveDate;
    private EDITDate followupDate;
    private EDITDate receivedDate;
    private String freeFormDescription;
    private Long clientDetailFK;
    private Long filteredRequirementFK;
    private ClientDetail clientDetail;
    private FilteredRequirement filteredRequirement;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Default Constructor
     */
    public CaseRequirement()
    {
    }

    /**
     * Constructor.
     * @param clientDetail
     * @param filteredRequirement
     */
    public CaseRequirement(ClientDetail clientDetail, FilteredRequirement filteredRequirement)
    {
        clientDetail.addCaseRequirement(this);

        filteredRequirement.addCaseRequirement(this);

        init();
    }

    public Long getClientDetailFK()
    {
        return clientDetailFK;
    }

    public void setClientDetailFK(Long clientDetailFK)
    {
        this.clientDetailFK = clientDetailFK;
    }

    public Long getFilteredRequirementFK()
    {
        return filteredRequirementFK;
    }

    public void setFilteredRequirementFK(Long filteredRequirementFK)
    {
        this.filteredRequirementFK = filteredRequirementFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCaseRequirementPK()
    {
        return caseRequirementPK;
    }

    /**
     * Setter.
     * @param caseRequirementPK
     */
    public void setCaseRequirementPK(Long caseRequirementPK)
    {
        this.caseRequirementPK = caseRequirementPK;
    }

    /**
     * Getter.
     * @return
     */
    public String getRequirementStatusCT()
    {
        return requirementStatusCT;
    }

    /**
     * Setter.
     * @param requirementStatusCT
     */
    public void setRequirementStatusCT(String requirementStatusCT)
    {
        this.requirementStatusCT = requirementStatusCT;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFollowupDate()
    {
        return followupDate;
    }

    /**
     * Setter.
     * @param followupDate
     */
    public void setFollowupDate(EDITDate followupDate)
    {
        this.followupDate = followupDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReceivedDate()
    {
        return receivedDate;
    }

    /**
     * Setter.
     * @param receivedDate
     */
    public void setReceivedDate(EDITDate receivedDate)
    {
        this.receivedDate = receivedDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getFreeFormDescription()
    {
        return freeFormDescription;
    }

    /**
     * Setter.
     * @param freeFormDescription
     */
    public void setFreeFormDescription(String freeFormDescription)
    {
        this.freeFormDescription = freeFormDescription;
    }

    /**
     * Getter.
     * @return
     */
    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    /**
     * Setter.
     * @param clientDetail
     */
    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    /**
     * Getter.
     * @return
     */
    public FilteredRequirement getFilteredRequirement()
    {
        return filteredRequirement;
    }

    /**
     * Setter.
     * @param filteredRequirement
     */
    public void setFilteredRequirement(FilteredRequirement filteredRequirement)
    {
        this.filteredRequirement = filteredRequirement;
    }

    /**
     * Verifies the Requirement Status and Updates Received Date accordingly.
     * The first time when the Status is changed to 'Received' the ReceivedDate should be set to current Date
     * if one is not entered.
     */
    public void checkRequirementStatusAndUpdateReceivedDate()
    {
        // Need to evict the existing object from hibernated cache to get the previous state of object from database.
//        SessionHelper.evict(this, CaseRequirement.DATABASE); // Do SessionHelper changes fix this?

        if (caseRequirementPK != null)
        {
//            CaseRequirement persistedCaseRequirement = CaseRequirement.findByPK(this.getCaseRequirementPK());

//            SessionHelper.evict(persistedCaseRequirement, CaseRequirement.DATABASE); GF - Do SessionHelper changes fix this?

//            if (REQUIREMENT_STATUS_CT_OUTSTANDING.equals(persistedCaseRequirement.getRequirementStatusCT()))
//            {
                if (REQUIREMENT_STATUS_CT_RECEIVED.equals(this.getRequirementStatusCT()))
                {
                    if (this.getReceivedDate() == null)
                    {
                        this.setReceivedDate(new EDITDate());
                    }
                }
//            }
        }
    }

    /**
     * Initializaiton method.
     */
    private void init()
    {
        this.setRequirementStatusCT(REQUIREMENT_STATUS_CT_OUTSTANDING);

        this.setEffectiveDate(new EDITDate());

        calculateAndSetFollowupDate();
    }

    /**
     * FolloupDate Calculation.
     */
    public void calculateAndSetFollowupDate()
    {
        FilteredRequirement filteredRequirement = this.getFilteredRequirement();

        Requirement requirement = filteredRequirement.getRequirement();

        EDITDate effDate = getEffectiveDate();

        this.setFollowupDate(effDate.addDays(requirement.getFollowupDays()));
    }

    /************************************** Static Methods **************************************/
    /**
     * Finder by PK.
     * @param caseRequirementPK
     * @return
     */
    public static final CaseRequirement findByPK(Long caseRequirementPK)
    {
        return (CaseRequirement) SessionHelper.get(CaseRequirement.class, caseRequirementPK, CaseRequirement.DATABASE);
    }

    /**
     * Finder.
     * @param clientDetail
     * @param filteredRequirement
     * @return
     */
    public static final CaseRequirement findBy_ClientDetail_And_FilteredRequirement(ClientDetail clientDetail, FilteredRequirement filteredRequirement)
    {
        CaseRequirement caseRequirement = null;

        String hql = "select cr from CaseRequirement cr where cr.ClientDetail = :clientDetail and cr.FilteredRequirement = :filteredRequirement";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);

        params.put("filteredRequirement", filteredRequirement);

        List results = SessionHelper.executeHQL(hql, params, CaseRequirement.DATABASE);

        if (!results.isEmpty())
        {
            caseRequirement = (CaseRequirement) results.get(0);
        }

        return caseRequirement;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, CaseRequirement.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, CaseRequirement.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CaseRequirement.DATABASE;
    }
}
