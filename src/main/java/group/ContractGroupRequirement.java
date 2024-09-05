package group;

import edit.common.EDITDate;

import edit.common.EDITMap;
import edit.common.vo.ContractRequirementVO;
import edit.common.vo.FilteredRequirementVO;
import edit.common.vo.RequirementVO;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import java.util.List;
import java.util.ArrayList;

import contract.*;
import fission.utility.Util;
import fission.utility.DateTimeUtil;

/**
 * A ContractGroup needs to have (either) manual and/or automatic requirements
 * satisfied before it can enter the next phase of its life-cycle. For
 * a Segment (as an example) all requirements need to be met before being issued.
 */
public class ContractGroupRequirement extends HibernateEntity
{
    public static String STATUS_OUTSTANDING = "Outstanding";

  /**
   * The PK.
   */
  private Long contractGroupRequirementPK;

  /**
   * The ContractGroup for which this ContractGroupRequirement (via 
   * FilteredRequirement) applies.
   */
  private Long contractGroupFK;


  /**
   * Represents one of the set of Requirements that may be applied to 
   * the associated ContractGroup.
   */
  private Long filteredRequirementFK;
  
  /**
   * Associating made during BatchTransmittal/BatchProgressLog creation.
   */
  private Long batchContractSetupFK;

  /**
   * Examples might include "Received" or "Outstanding".
   */
  private String requirementStatusCT;

  /**
   * Typically, the date the Requirement was added.  Follow-up dates (etc.)
   * are calculated relative to this date.
   */
  private EDITDate effectiveDate;

  /**
   * Informational to determine the next date to attempt
   * to solidify this Requirement.
   */
  private EDITDate followupDate;

  /**
   * The date this Requirement was received.
   */
  private EDITDate receivedDate;

  /**
   * @todo
   */
  private EDITDate executedDate;
  
  /**
   * The associated ContractGroup.
   */
  private ContractGroup contractGroup;
  
  /**
   * Association made when a ContractGroupRequirement is made
   * as a part of the BatchTransmittal process of BatchContract.
   */
  private BatchContractSetup batchContractSetup;

  /**
   * The associated FilteredRequirement.
   */
  private FilteredRequirement filteredRequirement;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


  public ContractGroupRequirement()
  {
  }

  /**
   * @see #contractGroupRequirementPK
   * @param contractGroupRequirementPK
   */
  public void setContractGroupRequirementPK(Long contractGroupRequirementPK)
  {
    this.contractGroupRequirementPK = contractGroupRequirementPK;
  }

  /**
   * @see #contractGroupRequirementPK
   * @return
   */
  public Long getContractGroupRequirementPK()
  {
    return contractGroupRequirementPK;
  }

  /**
   * @see #contractGroupFK
   * @param contractGroupFK
   */
  public void setContractGroupFK(Long contractGroupFK)
  {
    this.contractGroupFK = contractGroupFK;
  }

  /**
   * @see #contractGroupFK
   * @return
   */
  public Long getContractGroupFK()
  {
    return contractGroupFK;
  }

  /**
   * @see #filteredRequirementFK
   * @param filteredRequirementFK
   */
  public void setFilteredRequirementFK(Long filteredRequirementFK)
  {
    this.filteredRequirementFK = filteredRequirementFK;
  }

  /**
   * @see #filteredRequirementFK
   * @return
   */
  public Long getFilteredRequirementFK()
  {
    return filteredRequirementFK;
  }

  /**
   * @see #requirementStatusCT
   * @param requirementStatusCT
   */
  public void setRequirementStatusCT(String requirementStatusCT)
  {
    this.requirementStatusCT = requirementStatusCT;
  }

  /**
   * @see #requirementStatusCT
   * @return
   */
  public String getRequirementStatusCT()
  {
    return requirementStatusCT;
  }

  /**
   * @see #effectiveDate
   * @param effectiveDate
   */
  public void setEffectiveDate(EDITDate effectiveDate)
  {
    this.effectiveDate = effectiveDate;
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
   * @see #followupDate
   * @param followupDate
   */
  public void setFollowupDate(EDITDate followupDate)
  {
    this.followupDate = followupDate;
  }

  /**
   * @see #followupDate
   * @return
   */
  public EDITDate getFollowupDate()
  {
    return followupDate;
  }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIFollowupDate()
    {
        String date = null;

        if (getFollowupDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getFollowupDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIFollowupDate(String uiFollowupDate)
    {
        if (uiFollowupDate != null)
        {
            setFollowupDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiFollowupDate));
        }
    }

  /**
   * @see #receivedDate
   * @param receivedDate
   */
  public void setReceivedDate(EDITDate receivedDate)
  {
    this.receivedDate = receivedDate;
  }

  /**
   * @see #receivedDate
   * @return
   */
  public EDITDate getReceivedDate()
  {
    return receivedDate;
  }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIReceivedDate()
    {
        String date = null;

        if (getReceivedDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getReceivedDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIReceivedDate(String uiReceivedDate)
    {
        if (uiReceivedDate != null)
        {
            setReceivedDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiReceivedDate));
        }
    }

  /**
   * @see #executedDate
   * @param executedDate
   */
  public void setExecutedDate(EDITDate executedDate)
  {
    this.executedDate = executedDate;
  }

  /**
   * @see #executedDate
   * @return
   */
  public EDITDate getExecutedDate()
  {
    return executedDate;
  }
    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIExecutedDate()
    {
        String date = null;

        if (getExecutedDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getExecutedDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIExecutedDate(String uiExecutedDate)
    {
        if (uiExecutedDate != null)
        {
            setExecutedDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiExecutedDate));
        }
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
   * @return ContractGroup
   */
  public ContractGroup getContractGroup()
  {
    return contractGroup;
  }

  /**
   * @see #filteredRequirement
   * @param filteredRequirement
   */
  public void setFilteredRequirement(FilteredRequirement filteredRequirement)
  {
    this.filteredRequirement = filteredRequirement;
  }

  /**
   * @see #filteredRequirement
   * @return FilteredRequirement
   */
  public FilteredRequirement getFilteredRequirement()
  {
    return filteredRequirement;
  }

    public String getDatabase()
    {
        return ContractGroupRequirement.DATABASE;
    }

  /**
     * Finder.
     * @param contractGroupPK
     * @return
     */
  public static ContractGroupRequirement[] findBy_ContractGroupPK(Long contractGroupPK)
  {
    String hql = " from ContractGroupRequirement contractGroupRequirement" +
                " where contractGroupRequirement.ContractGroupFK = :contractGroupFK";
                
    EDITMap params = new EDITMap("contractGroupFK", contractGroupPK);                
                
    List<ContractGroupRequirement> results = SessionHelper.executeHQL(hql, params, ContractGroupRequirement.DATABASE);
    
    return results.toArray(new ContractGroupRequirement[results.size()]);
  }
  
    /**
     * Finder.
     * @param contractGroup
     * @param filteredRequirement
     * @return
     */
    public static ContractGroupRequirement findBy_ContractGroup_FilteredRequirement(ContractGroup contractGroup, FilteredRequirement filteredRequirement)
    {
        ContractGroupRequirement contractGroupRequirement = null;
    
        String hql = " from ContractGroupRequirement contractGroupRequirement" + " where contractGroupRequirement.ContractGroup = :contractGroup" + " and contractGroupRequirement.FilteredRequirement = :filteredRequirement";

        EDITMap params = new EDITMap("contractGroup", contractGroup).put("filteredRequirement", filteredRequirement);

        List<ContractGroupRequirement> results = SessionHelper.executeHQL(hql, params, ContractGroupRequirement.DATABASE);

        if (!results.isEmpty())
        {
            contractGroupRequirement = results.get(0); // should only be one.
        }

        return contractGroupRequirement;
    }

    /**
     * Finder.
     * @param batchContractSetupFK
     * @return
     */
    public static ContractGroupRequirement[] findBy_BatcContractSetupFK(Long batchContractSetupFK)
    {
        ContractGroupRequirement[] contractGroupRequirements = null;

        String hql = " from ContractGroupRequirement contractGroupRequirement" +
                     " where contractGroupRequirement.BatchContractSetupFK = :batchContractSetupFK";

        EDITMap params = new EDITMap("batchContractSetupFK", batchContractSetupFK);

        List<ContractGroupRequirement> results = SessionHelper.executeHQL(hql, params, ContractGroupRequirement.DATABASE);

        if (!results.isEmpty())
        {
            contractGroupRequirements = (ContractGroupRequirement[]) results.toArray(new ContractGroupRequirement[results.size()]);
        }

        return contractGroupRequirements;
    }

    /**
     * Finder.
     * @param contractGroupRequirementPK
     * @return
     */
    public static ContractGroupRequirement findBy_ContractGroupRequirementPK(Long contractGroupRequirementPK)
    {
        return (ContractGroupRequirement) SessionHelper.get(ContractGroupRequirement.class, contractGroupRequirementPK, ContractGroupRequirement.DATABASE);
    }

    public static ContractGroupRequirement buildNewRequirement(ContractGroup contractGroup, Requirement requirement, FilteredRequirement filteredRequirement)
    {
        ContractGroupRequirement contractGroupRequirement = new ContractGroupRequirement();
        contractGroupRequirement.setFilteredRequirement(filteredRequirement);
        contractGroupRequirement.setRequirementStatusCT(ContractGroupRequirement.STATUS_OUTSTANDING);
        contractGroupRequirement.setEffectiveDate(new EDITDate());
        EDITDate followupDate = new EDITDate().addDays(requirement.getFollowupDays());
        contractGroupRequirement.setFollowupDate(followupDate);

        return contractGroupRequirement;
    }
    
    /**
     * Builds a ContractGroupRequirement with the specified ContractGroup, FilteredRequirement(PK) and defaults
     * the following information:
     * 1. RequirmentStatusCT = "Outstanding"
     * 2. EffectiveDate = System Date
     * 3. All other fields are null.
     * @param caseContractGroup
     * @param filteredRequirement
     * @return
     */
    public static ContractGroupRequirement buildNewRequirement(BatchContractSetup batchContractSetup, ContractGroup caseContractGroup, FilteredRequirement filteredRequirement)
    {
        ContractGroupRequirement contractGroupRequirement = new ContractGroupRequirement();
        
        // Set defaults
        contractGroupRequirement.setRequirementStatusCT(ContractGroupRequirement.STATUS_OUTSTANDING);
        
        contractGroupRequirement.setEffectiveDate(new EDITDate());
        
        // Make core associations
        caseContractGroup.addContractGroupRequirement(contractGroupRequirement);
        
        filteredRequirement.addContractGroupRequirement(contractGroupRequirement);
        
        batchContractSetup.addContractGroupRequirement(contractGroupRequirement);
        
        return contractGroupRequirement;
    }

    public void setBatchContractSetup(BatchContractSetup batchContractSetup)
    {
        this.batchContractSetup = batchContractSetup;
    }

    public BatchContractSetup getBatchContractSetup()
    {
        return batchContractSetup;
    }

    public void setBatchContractSetupFK(Long batchContractSetupFK)
    {
        this.batchContractSetupFK = batchContractSetupFK;
    }

    public Long getBatchContractSetupFK()
    {
        return batchContractSetupFK;
    }
}
