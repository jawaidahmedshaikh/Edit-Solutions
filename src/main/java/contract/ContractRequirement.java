/*
 * User: cgleason
 * Date: Oct 27, 2003
 * Time: 12:15:53 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.vo.*;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.exceptions.EDITValidationException;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.services.db.hibernate.HibernateEntity;
import contract.dm.dao.RequirementDAO;
import contract.dm.dao.FilteredRequirementDAO;
import org.hibernate.Session;

import java.util.List;

import staging.IStaging;
import staging.StagingContext;
import role.ClientRole;
import client.ClientDetail;
import client.ClientAddress;


public class ContractRequirement extends HibernateEntity implements CRUDEntity, IStaging
{
    private ContractRequirementVO contractRequirementVO;

    private ContractRequirementImpl contractRequirementImpl;

    public static final String REQUIREMENTSTATUSCT_OUTSTANDING = "Outstanding";
    public static final String REQUIREMENTSTATUSCT_RECEIVED = "Received";

    private String requirementStatusCT;
    private EDITDate effectiveDate;
    private EDITDate followupDate;
    private String freeFormDescription;
    private Segment segment;
    private FilteredRequirement filteredRequirement;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public ContractRequirement(ContractRequirementVO contractRequirementVO)
    {
        this();
        this.contractRequirementVO = contractRequirementVO;
    }

    public ContractRequirement(long contractRequirementPK) throws Exception
    {
        this();
        this.contractRequirementImpl.load(this, contractRequirementPK);
    }

    public ContractRequirement()
    {
        this.contractRequirementImpl = new ContractRequirementImpl();
        this.contractRequirementVO = new ContractRequirementVO();

        setDefaultValues();
    }

    /**
     * Initializes default values upon initial construction.
     */
    private void setDefaultValues()
    {
        this.contractRequirementVO.setRequirementStatusCT(ContractRequirement.REQUIREMENTSTATUSCT_OUTSTANDING);

        this.contractRequirementVO.setEffectiveDate(new EDITDate().getFormattedDate());
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractRequirementPK()
    {
        return SessionHelper.getPKValue(contractRequirementVO.getContractRequirementPK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(contractRequirementVO.getEffectiveDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getExecutedDate()
    {
        return SessionHelper.getEDITDate(contractRequirementVO.getExecutedDate());
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredRequirementFK()
    {
        return SessionHelper.getPKValue(contractRequirementVO.getFilteredRequirementFK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFollowupDate()
    {
        return SessionHelper.getEDITDate(contractRequirementVO.getFollowupDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReceivedDate()
    {
        return SessionHelper.getEDITDate(contractRequirementVO.getReceivedDate());
    }

    /**
     * Getter.
     * @return
     */
    public String getRequirementStatusCT()
    {
        return contractRequirementVO.getRequirementStatusCT();
    }

    /**
     * Getter.
     * @return
     */
    public String getFreeFormDescription()
    {
        return contractRequirementVO.getFreeFormDescription();
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(contractRequirementVO.getSegmentFK());
    }

    /**
     * Setter.
     * @param contractRequirementPK
     */
    public void setContractRequirementPK(Long contractRequirementPK)
    {
        contractRequirementVO.setContractRequirementPK(SessionHelper.getPKValue(contractRequirementPK));
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        contractRequirementVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }

    /**
     * Setter.
     * @param executedDate
     */
    public void setExecutedDate(EDITDate executedDate)
    {
        contractRequirementVO.setExecutedDate(SessionHelper.getEDITDate(executedDate));
    }

    /**
     * Setter.
     * @param filteredRequirementFK
     */
    public void setFilteredRequirementFK(Long filteredRequirementFK)
    {
        contractRequirementVO.setFilteredRequirementFK(SessionHelper.getPKValue(filteredRequirementFK));
    }

    /**
     * Setter.
     * @param followupDate
     */
    public void setFollowupDate(EDITDate followupDate)
    {
        contractRequirementVO.setFollowupDate(SessionHelper.getEDITDate(followupDate));
    }

    /**
     * Setter.
     * @param receivedDate
     */
    public void setReceivedDate(EDITDate receivedDate)
    {
        contractRequirementVO.setReceivedDate(SessionHelper.getEDITDate(receivedDate));
    }

    /**
     * Setter.
     * @param requirementStatusCT
     */
    public void setRequirementStatusCT(String requirementStatusCT)
    {
        contractRequirementVO.setRequirementStatusCT(requirementStatusCT);
    }

    /**
     * Setter.
     * @param freeFormDescription
     */
    public void setFreeFormDescription(String freeFormDescription)
    {
        contractRequirementVO.setFreeFormDescription(freeFormDescription);
    }

    /**
     * Setter.
     * @param segmentFK
     */
    public void setSegmentFK(Long segmentFK)
    {
        contractRequirementVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    /**
     * Getter
     * @return
     */
    public String getRequirementInformation()
    {
        return contractRequirementVO.getRequirementInformation();
    } //-- java.lang.String getRequirementInformation()

    /**
     * Setter
     * @param requirementInformation
     */
    public void setRequirementInformation(String requirementInformation)
    {
        contractRequirementVO.setRequirementInformation(requirementInformation);
    } //-- void setRequirementInformation(java.lang.String)

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return this.segment;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
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

    public void calcFollowupDate(int followupDays, ContractRequirementVO contractRequirementVO)
    {
        this.contractRequirementImpl.calcFollowupDate(followupDays, contractRequirementVO);
    }

    /**
     * The ValidationException are thrown
     */
    public void saveForHibernate() throws EDITValidationException
    {
        validate();

        if (isNew())
        {
            contractRequirementImpl.calculateFollowupDate(contractRequirementVO);
        }
        else if (contractRequirementVO.getFollowupDate() == null)
        {
            contractRequirementImpl.calculateFollowupDate(contractRequirementVO);            
        }

        //CONVERT TO HIBERNATE - 09/13/07
        hSave();
//        contractRequirementImpl.save(this);
    }

    /**
     * The ValidationException are thrown
     */
    public void save() throws EDITValidationException
    {
        validate();

        if (isNew())
        {
            contractRequirementImpl.calculateFollowupDate(contractRequirementVO);
        }
        else if (contractRequirementVO.getFollowupDate() == null)
        {
            contractRequirementImpl.calculateFollowupDate(contractRequirementVO);
        }

        contractRequirementImpl.save(this);
    }    

    public void delete() throws Exception
    {
        contractRequirementImpl.delete(this);
    }

    public long getPK()
    {
        return contractRequirementVO.getContractRequirementPK();
    }

    public void setVO(VOObject voObject)
    {
        this.contractRequirementVO = (ContractRequirementVO) voObject;
    }

    public boolean isNew()
    {
        return this.contractRequirementImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.contractRequirementImpl.cloneCRUDEntity(this);
    }

    /**
     * Validates Contract Requirement.
     * Scenario 1: If the RequirmentStatus is 'Received' then ReceivedDate should be entered.
     * Scenario 2: If the ReceivedDate is entered then RequirementStatus should be Received.
     * Scenario 3: If the RequirementStatus is 'Received' and if the Requirement's UpdatePolicyDeliverDateInd
     * is set then ExecutedDate should be entered.
     * @throws EDITValidationException
     */
    private void validate() throws EDITValidationException
    {
        if (REQUIREMENTSTATUSCT_RECEIVED.equals(this.getRequirementStatusCT()))
        {
            if (this.getReceivedDate() == null)
            {
                throw new EDITValidationException("Status = [Received]. Received Date Required.");
            }
            else
            {
                FilteredRequirement filteredRequirement = FilteredRequirement.findByPK(this.getFilteredRequirementFK());

                Requirement requirement = filteredRequirement.getRequirement();

                if ("Y".equals(requirement.getUpdatePolicyDeliveryDateInd()))
                {
                    if (this.getExecutedDate() == null)
                    {
                        throw new EDITValidationException("Executed Date Required.");
                    }
                }
            }
        }

        if (this.getReceivedDate() != null)
        {
            if (!REQUIREMENTSTATUSCT_RECEIVED.equals(this.getRequirementStatusCT()))
            {
                throw new EDITValidationException("Received Date Entered. Invalid Status");
            }
        }
    }

    /**
     * Verifies if policy delivery date should be updated or not.
     * @return true if policy delivery date has to be updated.
     */
    public boolean isUpdatePolicyDeliveryDate() throws EDITValidationException
    {
        boolean isUpdatePolicyDeliveryDate = false;

        FilteredRequirementVO filteredRequirementVO = new FilteredRequirementDAO().findByPK(contractRequirementVO.getFilteredRequirementFK(), false, null)[0];

        RequirementVO requirementVO = new RequirementDAO().findByPK(filteredRequirementVO.getRequirementFK(), false, null)[0];

        if ("Y".equals(requirementVO.getUpdatePolicyDeliveryDateInd()))
        {
            if (this.getExecutedDate() == null)
            {
                throw new EDITValidationException("Executed Date Required.");
            }
            isUpdatePolicyDeliveryDate = true;
        }

        return isUpdatePolicyDeliveryDate;
    }

    public VOObject getVO()
    {
        if (contractRequirementVO.getFollowupDate() == null)
        {
            contractRequirementImpl.calculateFollowupDate(contractRequirementVO);
        }

        return contractRequirementVO;
    }

    public ContractRequirementVO[] buildInitialContractRequirements(long productStrucutreFK)
    {
        ContractRequirementVO[] contractRequirementVOs  = contractRequirementImpl.buildInitialContractRequirements(productStrucutreFK);

        return contractRequirementVOs;
    }

    public ContractRequirementVO calculateFollowupDate(ContractRequirementVO contractRequirementVO){

        contractRequirementImpl.calculateFollowupDate(contractRequirementVO);

        return contractRequirementVO;
    }

    /**
     * Associates this ContractRequirement to the specified Segment
     * @param segment
     */
    public void associateSegment(Segment segment)
    {
        this.contractRequirementVO.setSegmentFK(segment.getPK());
    }

    /**
     * Associates this ContractRequirement to the specified FilteredRequirement
     * @param filteredRequirement
     */
    public void associateFilteredRequirement(FilteredRequirement filteredRequirement)
    {
        this.contractRequirementVO.setFilteredRequirementFK(filteredRequirement.getPK());
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContractRequirement.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ContractRequirement.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContractRequirement.DATABASE;
    }

    /**
     * Finds the ContractRequirements that are outstanding
     * @param reqStatus
     * @return
     */
    public static ContractRequirement[] findBy_Status(String reqStatus)
    {
      String hql = " select cr from ContractRequirement cr" +
                  " where cr.RequirementStatusCT = :reqStatus";

        EDITMap params = new EDITMap("reqStatus", reqStatus);

        List<ContractClient> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return results.toArray(new ContractRequirement[results.size()]);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.Requirement requirement = new staging.Requirement();
        requirement.setSegmentBase(stagingContext.getCurrentSegmentBase());
        requirement.setRequirementDescription(this.getFilteredRequirement().getRequirement().getRequirementDescription());
        requirement.setRequirementStatus(this.getRequirementStatusCT());
        requirement.setEffectiveDate(this.getEffectiveDate());
        requirement.setFollowupDate(this.getFollowupDate());
        requirement.setReceivedDate(this.getReceivedDate());
        requirement.setFreeFormReqDescription(this.getFreeFormDescription());

        stagingContext.getCurrentSegmentBase().addRequirement(requirement);

        SessionHelper.saveOrUpdate(requirement, database);

        return stagingContext;
    }
}

