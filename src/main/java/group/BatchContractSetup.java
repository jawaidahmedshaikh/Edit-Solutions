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

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;

import contract.*;
import security.*;
import engine.*;
import fission.utility.*;
import staging.IStaging;
import staging.StagingContext;


public class BatchContractSetup extends HibernateEntity implements IStaging
{
    /**
     * Primary key
     */
    private Long batchContractSetupPK;

    /**
     * Identifier that is the Company's companyName + Group ContractGroup's groupNumber + sequence number.
     * This id is system generated using autoGenerateBatchID().  The groupNumber and sequence number
     * parts of the id are fixed lengths and padded with zeroes at the beginning portion of the field.
     */
    private String batchID;

    private String statusCT;
    private int numberOfContracts;
    private EDITDate receiptDate;
    private int numberOfWorkdays;
    private String autoNumberCT;
    private String applicationTypeCT;
    private String batchEventCT;
    private EDITDate effectiveDate;
    private EDITDate applicationSignedDate;
    private String applicationSignedStateCT;
    private String ratedGenderCT;
    private String issueStateCT;
    private String deathBenefitOptionCT;
    private String nonForfeitureOptionCT;
    private String creationOperator;
    private EDITDate creationDate;
    private String enrollmentMethodCT;

    //  Parents
    private ContractGroup contractGroup;
    private FilteredProduct filteredProduct;
    private Enrollment enrollment;
    private Long contractGroupFK;
    private Long filteredProductFK;
    private Long enrollmentFK;

    //  Children
    private Set<Segment> segments;
    private Set<SelectedAgentHierarchy> selectedAgentHierarchies;
    private Set<BatchProductLog> batchProductLogs;
    private Set<BatchProgressLog> batchProgressLogs;
    private Set<ContractGroupRequirement> contractGroupRequirements;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    private static final int BATCHID_GROUPNUMBER_LENGTH = 10;
    private static final int BATCHID_SEQUENCENUMBER_LENGTH = 4;


    /**
     * Constructor
     */
    public BatchContractSetup()
    {
        init();
    }

    private void init()
    {
        segments = new HashSet<Segment>();
        selectedAgentHierarchies = new HashSet<SelectedAgentHierarchy>();
        batchProductLogs = new HashSet<BatchProductLog>();
        batchProgressLogs = new HashSet<BatchProgressLog>();
    }

    public static BatchContractSetup buildDefault(ContractGroup groupContractGroup)
    {
        BatchContractSetup batchContractSetup = new BatchContractSetup();

        batchContractSetup.setCreationDate(new EDITDate());
        batchContractSetup.setCreationOperator(Operator.OPERATOR_SYSTEM);

        batchContractSetup.setContractGroup(groupContractGroup);

        return batchContractSetup;
    }

    public Long getBatchContractSetupPK()
    {
        return batchContractSetupPK;
    }

    public void setBatchContractSetupPK(Long batchContractSetupPK)
    {
        this.batchContractSetupPK = batchContractSetupPK;
    }

    public String getBatchID()
    {
        return batchID;
    }

    public void setBatchID(String batchID)
    {
        this.batchID = batchID;
    }

    public String getStatusCT()
    {
        return statusCT;
    }

    public void setStatusCT(String statusCT)
    {
        this.statusCT = statusCT;
    }

    public int getNumberOfContracts()
    {
        return this.numberOfContracts;
    }

    public void setNumberOfContracts(int numberOfContracts)
    {
        this.numberOfContracts = numberOfContracts;
    }

    public EDITDate getReceiptDate()
    {
        return receiptDate;
    }

    public void setReceiptDate(EDITDate receiptDate)
    {
        this.receiptDate = receiptDate;
    }

    public int getNumberOfWorkdays()
    {
        return numberOfWorkdays;
    }

    public void setNumberOfWorkdays(int numberOfWorkdays)
    {
        this.numberOfWorkdays = numberOfWorkdays;
    }

    public String getAutoNumberCT()
    {
        return autoNumberCT;
    }

    public void setAutoNumberCT(String autoNumberCT)
    {
        this.autoNumberCT = autoNumberCT;
    }

    public String getApplicationTypeCT()
    {
        return applicationTypeCT;
    }

    public void setApplicationTypeCT(String applicationTypeCT)
    {
        this.applicationTypeCT = applicationTypeCT;
    }

    public String getBatchEventCT()
    {
        return batchEventCT;
    }

    public void setBatchEventCT(String batchEventCT)
    {
        this.batchEventCT = batchEventCT;
    }

    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public EDITDate getApplicationSignedDate()
    {
        return applicationSignedDate;
    }

    public void setApplicationSignedDate(EDITDate applicationSignedDate)
    {
        this.applicationSignedDate = applicationSignedDate;
    }

    public String getApplicationSignedStateCT()
    {
        return applicationSignedStateCT;
    }

    public void setApplicationSignedStateCT(String applicationSignedStateCT)
    {
        this.applicationSignedStateCT = applicationSignedStateCT;
    }

     public String getRatedGenderCT()
    {
        return ratedGenderCT;
    }

    public void setRatedGenderCT(String ratedGenderCT)
    {
        this.ratedGenderCT = ratedGenderCT;
    }
    public String getIssueStateCT()
    {
        return issueStateCT;
    }

    public void setIssueStateCT(String issueStateCT)
    {
        this.issueStateCT = issueStateCT;
    }

    public String getDeathBenefitOptionCT()
    {
        return deathBenefitOptionCT;
    }

    public void setDeathBenefitOptionCT(String deathBenefitOptionCT)
    {
        this.deathBenefitOptionCT = deathBenefitOptionCT;
    }

    public String getNonForfeitureOptionCT()
    {
        return nonForfeitureOptionCT;
    }

    public void setNonForfeitureOptionCT(String nonForfeitureOptionCT)
    {
        this.nonForfeitureOptionCT = nonForfeitureOptionCT;
    }

    public String getCreationOperator()
    {
        return creationOperator;
    }

    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
    }

    public EDITDate getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getEnrollmentMethodCT()
    {
        return enrollmentMethodCT;
    }

    public void setEnrollmentMethodCT(String enrollmentMethodCT)
    {
        this.enrollmentMethodCT = enrollmentMethodCT;
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

    public FilteredProduct getFilteredProduct()
    {
        return filteredProduct;
    }

    public void setFilteredProduct(FilteredProduct filteredProduct)
    {
        this.filteredProduct = filteredProduct;
    }

    public Long getFilteredProductFK()
    {
        return this.filteredProductFK;
    }

    public void setFilteredProductFK(Long filteredProductFK)
    {
        this.filteredProductFK = filteredProductFK;
    }

    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
    }

    public Long getEnrollmentFK()
    {
        return this.enrollmentFK;
    }

    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }

    public Set getSegments()
    {
        return this.segments;
    }

    public void setSegments(Set<Segment> segments)
    {
        this.segments = segments;
    }

    public void addSegment(Segment segment)
    {
        this.segments.add(segment);

        segment.setBatchContractSetup(this);
    }

    public void removeSegment(Segment segment)
    {
        this.segments.remove(segment);

        segment.setBatchContractSetup(null);
    }

    public Set<SelectedAgentHierarchy> getSelectedAgentHierarchies()
    {
        return this.selectedAgentHierarchies;
    }

    public void setSelectedAgentHierarchies(Set<SelectedAgentHierarchy> selectedAgentHierarchies)
    {
        this.selectedAgentHierarchies = selectedAgentHierarchies;
    }

    public void addSelectedAgentHierarchy(SelectedAgentHierarchy selectedAgentHierarchy)
    {
        this.selectedAgentHierarchies.add(selectedAgentHierarchy);

        selectedAgentHierarchy.setBatchContractSetup(this);
    }

    /**
     * Removes the SelectedAgentHierarchy from this BatchContractSetup
     *
     * @param selectedAgentHierarchy
     */
    public void removeSelectedAgentHierarchy(SelectedAgentHierarchy selectedAgentHierarchy)
    {
        this.selectedAgentHierarchies.remove(selectedAgentHierarchy);

        selectedAgentHierarchy.remove();
    }

    public Set<BatchProductLog> getBatchProductLogs()
    {
        return this.batchProductLogs;
    }

    public void setBatchProductLogs(Set<BatchProductLog> batchProductLogs)
    {
        this.batchProductLogs = batchProductLogs;
    }

    public void addBatchProductLog(BatchProductLog batchProductLog)
    {
        getBatchProductLogs().add(batchProductLog);

        batchProductLog.setBatchContractSetup(this);
        
        SessionHelper.saveOrUpdate(this, getDatabase());
    }

    /**
     * Removes the BatchProductLog from this BatchContractSetup
     *
     * @param batchProductLog
     */
    public void removeBatchProductLog(BatchProductLog batchProductLog)
    {
        this.batchProductLogs.remove(batchProductLog);

        batchProductLog.remove();
    }

    public Set<BatchProgressLog> getBatchProgressLogs()
    {
        return this.batchProgressLogs;
    }

    public void setBatchProgressLogs(Set<BatchProgressLog> batchProgressLogs)
    {
        this.batchProgressLogs = batchProgressLogs;
    }

    public void addBatchProgressLog(BatchProgressLog batchProgressLog)
    {
        getBatchProgressLogs().add(batchProgressLog);

        batchProgressLog.setBatchContractSetup(this);
        
        SessionHelper.saveOrUpdate(this, getDatabase());
    }

    /**
     * Removes the BatchProgressLog from this BatchContractSetup
     *
     * @param batchProgressLog
     */
    public void removeBatchProgressLog(BatchProgressLog batchProgressLog)
    {
        this.batchProgressLogs.remove(batchProgressLog);

        batchProgressLog.remove();
    }

    /**
     * Convenience method to remove all of the BatchContractSetup's children
     */
    public void removeAllChildren()
    {
        getBatchProductLogs().clear();
        
        getBatchProgressLogs().clear();
        
        getSelectedAgentHierarchies().clear();
    }
    
    /**
     * A convenience method to remove all of the SelectedAgentHierarchies from this BatchContractSetup.
     */
    public void removeAllSelectedAgentHierarchies()
    {
        for (Iterator<SelectedAgentHierarchy> selectedAgentHierarchyIterator = this.selectedAgentHierarchies.iterator(); selectedAgentHierarchyIterator.hasNext();)
        {
            SelectedAgentHierarchy selectedAgentHierarchy = selectedAgentHierarchyIterator.next();
        
            selectedAgentHierarchyIterator.remove(); // removes from the collection
            
            selectedAgentHierarchy.setAgentHierarchy(null);
            
            selectedAgentHierarchy.setBatchContractSetup(null);
            
            SessionHelper.delete(selectedAgentHierarchy, selectedAgentHierarchy.getDatabase());
        }        
    }

    /**
     * Detaches ContractGroupRequirements from BatchContractSetup
     */
    public void detachContractGroupRequirements()
    {
        for (ContractGroupRequirement contractGroupRequirement : this.getContractGroupRequirements())
        {
            contractGroupRequirement.setBatchContractSetup(null);

            SessionHelper.saveOrUpdate(contractGroupRequirement, SessionHelper.EDITSOLUTIONS);
        }
    }

    /**
     * Returns the "actual" AgentHierarchies.  That is, it returns the AgentHierarchy objects pointed to by the
     * SelectedAgentHierarchy objects.
     *
     * @return  Set of AgentHierarchy objects belonging to the SelectedAgentHierarchy objects
     */
    public Set<AgentHierarchy> getActualAgentHierarchies()
    {
        Set<AgentHierarchy> actualAgentHierarchies = new HashSet<AgentHierarchy>();

        Set<SelectedAgentHierarchy> selectedAgentHierarchies = this.getSelectedAgentHierarchies();

        for (Iterator iterator = selectedAgentHierarchies.iterator(); iterator.hasNext();)
        {
            SelectedAgentHierarchy selectedAgentHierarchy = (SelectedAgentHierarchy) iterator.next();

//            AgentHierarchy agentHierarchy = AgentHierarchy.findByPK(selectedAgentHierarchy.getAgentHierarchyFK());
            AgentHierarchy agentHierarchy = selectedAgentHierarchy.getAgentHierarchy();

            actualAgentHierarchies.add(agentHierarchy);
        }

        return actualAgentHierarchies;
    }

    /**
     * Returns the group ContractGroup for this object
     * @return
     */
    public ContractGroup getGroupContractGroup()
    {
        return this.getContractGroup();
    }

    /**
     * Returns the case ContractGroup for this object
     * @return
     */
    public ContractGroup getCaseContractGroup()
    {
        return this.getGroupContractGroup().getContractGroup();
    }

    public String getDatabase()
    {
        return BatchContractSetup.DATABASE;
    }

    /**
     * Finds the BatchContractSetup with the given primary key
     *
     * @param batchContractSetupPK
     *
     * @return
     */
    public static BatchContractSetup findByPK(Long batchContractSetupPK)
    {
        String hql = " select batchContractSetup from BatchContractSetup batchContractSetup" +
                     " where batchContractSetup.BatchContractSetupPK = :batchContractSetupPK";

        EDITMap params = new EDITMap();

        params.put("batchContractSetupPK", batchContractSetupPK);

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        if (results.size() > 0)
        {
            return (BatchContractSetup) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds the BatchContractSetup with the given ContractGroup foreign key
     *
     * @param contractGroupFK
     *
     * @return
     */
    public static BatchContractSetup[] findBy_ContractGroupFK(Long contractGroupFK)
    {
        String hql = " select batchContractSetup from BatchContractSetup batchContractSetup" +
                     " where batchContractSetup.ContractGroupFK = :contractGroupFK";

        EDITMap params = new EDITMap();

        params.put("contractGroupFK", contractGroupFK);

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        if (results.size() > 0)
        {
            return results.toArray(new BatchContractSetup[results.size()]);
        }
        else
        {
            return null;
        }
    }

    public static BatchContractSetup findByEnrollmentPK(Long enrollmentPK)
    {
        BatchContractSetup batchContractSetup = null;

        String hql = " select batchContractSetup from BatchContractSetup batchContractSetup" +
                     " where batchContractSetup.EnrollmentFK = :enrollmentPK";

        EDITMap params = new EDITMap();

        params.put("enrollmentPK", enrollmentPK);

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        if (results.size() > 0)
        {
            batchContractSetup =  (BatchContractSetup) results.get(0);
        }

        return batchContractSetup;
    }

    /**
     * Finds all BatchContractSetups
     *
     * @return array of BatchContractSetup objects
     */
    public static BatchContractSetup[] findAll()
    {
        String hql = " select batchContractSetup" + 
                     " from BatchContractSetup batchContractSetup" +
                    " join batchContractSetup.ContractGroup contractGroup" +
                    " order by contractGroup.ContractGroupNumber asc, batchContractSetup.ReceiptDate desc";

        EDITMap params = new EDITMap();

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        return results.toArray(new BatchContractSetup[results.size()]);
    }

    /**
     * Finds all BatchContractSetups that have active group ContractGroups (i.e. whose termination date is greater than
     * or equal to today's date)
     *
     * @return array of BatchContractSetup objects
     */
    public static BatchContractSetup[] findAllWithActiveGroups()
    {
        String hql = " select batchContractSetup" +
                     " from BatchContractSetup batchContractSetup" +
                     " join batchContractSetup.ContractGroup contractGroup" +
                     " where contractGroup.TerminationDate >= :todaysDate" +
                     " order by contractGroup.ContractGroupNumber asc, batchContractSetup.ReceiptDate desc";

        EDITMap params = new EDITMap();
        params.put("todaysDate", new EDITDate());

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        return results.toArray(new BatchContractSetup[results.size()]);
    }
    
    /**
     * Finds all BatchContractSetups that have active group ContractGroups (i.e. whose termination date is greater than
     * or equal to today's date)
     *
     * @return array of BatchContractSetup objects
     */
    public static BatchContractSetup[] findAllWithActiveGroupsBy_PartialContractGroupNumber(String partialContractGroupNumber)
    {
        String hql = " select batchContractSetup" +
                         " from BatchContractSetup batchContractSetup" +
                         " join batchContractSetup.ContractGroup contractGroup" +
                         " join contractGroup.BillSchedule billSchedule" +
                         " where contractGroup.ContractGroupNumber like :partialContractGroupNumber" +
                         " and contractGroup.TerminationDate >= :todaysDate" +
                         " order by contractGroup.ContractGroupNumber asc, batchContractSetup.ReceiptDate desc";

            EDITMap params = new EDITMap();
            params.put("partialContractGroupNumber", partialContractGroupNumber + "%");
            params.put("todaysDate", new EDITDate());

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        return results.toArray(new BatchContractSetup[results.size()]);
    }    


    /**
     * Finds the BatchContractSetup with the last sequence number at the end if the BatchID field.
     *
     * @param batchIDPrefix                         beginning part of the BatchID
     *
     * @return
     */
    public static BatchContractSetup findLastSequenceByBatchIDPrefix(String batchIDPrefix)
    {
        String hql = " select batchContractSetup from BatchContractSetup batchContractSetup" +
                     " where batchContractSetup.BatchID = " +
                     " (select max(batchContractSetup.BatchID) from batchContractSetup" +
                     " where batchContractSetup.BatchID like :batchIDPrefix)";

        EDITMap params = new EDITMap();
        params.put("batchIDPrefix", "%" + batchIDPrefix + "%");

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        if (results.size() > 0)
        {
            return (BatchContractSetup) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finder.  Finds all BatchContractSetups for a given contractGroupNumber (a Group contractGroup)
     *
     * @param contractGroupNumber
     *
     * @return array of BatchContractSetups whose Group ContractGroup has the given contractGroupNumber
     */
    public static BatchContractSetup[] findBy_ContractGroupNumber(String contractGroupNumber)
    {
        String hql = " select batchContractSetup from BatchContractSetup batchContractSetup" +
                     " join batchContractSetup.ContractGroup contractGroup" +
                     " where contractGroup.ContractGroupNumber = :contractGroupNumber" +
                     " and contractGroup.ContractGroupTypeCT = :contractGroupTypeCT";

        EDITMap params = new EDITMap();
        params.put("contractGroupNumber", contractGroupNumber);
        params.put("contractGroupTypeCT", ContractGroup.CONTRACTGROUPTYPECT_GROUP);

        List<BatchContractSetup> results = SessionHelper.executeHQL(hql, params, BatchContractSetup.DATABASE);

        return results.toArray(new BatchContractSetup[results.size()]);
    }

    /**
     * Increases the numberOfContracts by 1.  This should be called whenever a segment is created using this BatchContractSetup.
     */
    public void increaseNumberOfContractsCount()
    {
        this.numberOfContracts++;
    }

    /**
     * Generates the BatchID and sets the field.  The BatchID is the CompanyName + GroupNumber + 4 digit sequence number.
     */
    public void autoGenerateBatchID()
    {
        ProductStructure productStructure = this.getFilteredProduct().getProductStructure();

        String companyName =  productStructure.getCompanyName();

        String contractGroupNumber = this.contractGroup.getContractGroupNumber();

        String paddedContractGroupNumber = Util.padWithZero(contractGroupNumber, BatchContractSetup.BATCHID_GROUPNUMBER_LENGTH);

        String batchIDPrefix = companyName + paddedContractGroupNumber;

        String sequenceNumber = this.getNextSequenceNumber(batchIDPrefix);

        String batchID = batchIDPrefix + sequenceNumber;

        this.setBatchID(batchID);
    }

    /**
     * Returns the set of AgentHierarcys that are "candidates" for the BatchContractSetup.  They are candidates if:
     * 
     * 1. They belong to the BatchContractSetup's Case ContractGroup 
     * 2. Those AgentHierarchys are not SelectedAgentHierarchys.
     * 3. The AgentHierarchy.
     * 
     *  4. Ignore the Candidate agent if the Stop Date of the applicable Hierarchy (Case Agent Stop Date) has a Stop Date < Beginning Policy Date
     *
     * @return   Set of AgentHierarchy objects that are on the Case but not in the SelectedAgentHierarchy list
     */
    public Set<AgentHierarchy> getCandidateAgentHierarchies(EDITDate effectiveDate)
    {
        Set<AgentHierarchy> candidateAgentHierarchies = new HashSet<AgentHierarchy>();

        ContractGroup caseContractGroup = this.getCaseContractGroup();

        Set<AgentHierarchy> agentHierarchies = caseContractGroup.getAgentHierarchies();

        Set<SelectedAgentHierarchy> selectedAgentHierarchies = this.getSelectedAgentHierarchies();

        //  Make a list of agentHierarchyFKs in the SelectedAgentHierarchys off this object
        List selectedAgentHierarchyFKs = new ArrayList();

        for (Iterator iterator = selectedAgentHierarchies.iterator(); iterator.hasNext();)
        {
            SelectedAgentHierarchy selectedAgentHierarchy = (SelectedAgentHierarchy) iterator.next();

            selectedAgentHierarchyFKs.add(selectedAgentHierarchy.getAgentHierarchyFK());
        }

        //  Add the AgentHierarchy objects that are not "selected" into the candidate list 
        for (Iterator iterator = agentHierarchies.iterator(); iterator.hasNext();)
        {
            AgentHierarchy agentHierarchy = (AgentHierarchy) iterator.next();

            if (! selectedAgentHierarchyFKs.contains(agentHierarchy.getAgentHierarchyPK()))
            {
                //  not a selected one, put it in the list - but check the dates first.
                AgentHierarchyAllocation activeAgentHierarchyAllocation = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), effectiveDate);
                
                if (activeAgentHierarchyAllocation != null)
                {
                    candidateAgentHierarchies.add(agentHierarchy);
                }
            }
        }

        return candidateAgentHierarchies;
    }

    /**
     * Determines the number that is next in the sequence for the BatchID.  The sequence number is fixed lenght at
     * 4 digits with zeroes padded at the beginning.
     *
     * @param batchIDPrefix                     beginning part of the BatchID that precedes the sequence number
     *
     * @return  next sequence number
     */
    private String getNextSequenceNumber(String batchIDPrefix)
    {
        BatchContractSetup lastInSequenceBatchContractSetup = BatchContractSetup.findLastSequenceByBatchIDPrefix(batchIDPrefix);

        int nextSequenceNumber = 0;

        if (lastInSequenceBatchContractSetup == null)
        {
            //  This is the first BatchContractSetup for the ContractGroup, start the sequence at 1
            nextSequenceNumber = 1;
        }
        else
        {
            //  Have the last one, get the sequence number from the batchID
            String lastBatchID = lastInSequenceBatchContractSetup.getBatchID();

            String sequenceNumber = Util.stripString(lastBatchID, batchIDPrefix);

            nextSequenceNumber = new Integer(sequenceNumber).intValue() + 1;
        }

        return Util.padWithZero(nextSequenceNumber + "", BatchContractSetup.BATCHID_SEQUENCENUMBER_LENGTH);
    }

    public Set<SelectedAgentHierarchy> get_selectedAgentHierarchies()
    {
        return selectedAgentHierarchies;
    }

    public void setContractGroupRequirements(Set<ContractGroupRequirement> contractGroupRequirements)
    {
        this.contractGroupRequirements = contractGroupRequirements;
    }

    public Set<ContractGroupRequirement> getContractGroupRequirements()
    {
        return contractGroupRequirements;
    }

    /**
     * Adder.
     * @param contractGroupRequirement
     */
    public void addContractGroupRequirement(ContractGroupRequirement contractGroupRequirement)
    {
        getContractGroupRequirements().add(contractGroupRequirement);
        
        contractGroupRequirement.setBatchContractSetup(this);
        
        SessionHelper.saveOrUpdate(this, getDatabase());
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.BatchContractSetup stagingBatchContractSetup = new staging.BatchContractSetup();

        stagingBatchContractSetup.setBatchID(this.batchID);
        stagingBatchContractSetup.setStatus(this.statusCT);
        stagingBatchContractSetup.setNumberOfContracts(this.numberOfContracts);
        stagingBatchContractSetup.setAutoNumber(this.autoNumberCT);
        stagingBatchContractSetup.setApplicationType(this.applicationTypeCT);
        stagingBatchContractSetup.setBatchEvent(this.batchEventCT);
        stagingBatchContractSetup.setEffectiveDate(this.effectiveDate);
        stagingBatchContractSetup.setApplicationSignedDate(this.applicationSignedDate);
        stagingBatchContractSetup.setApplicationSignedState(this.applicationSignedStateCT);
       // stagingBatchContractSetup.setRatedGender(this.applicationSignedStateCT);
        stagingBatchContractSetup.setIssueState(this.issueStateCT);
        stagingBatchContractSetup.setDeathBenefitOption(this.deathBenefitOptionCT);
        stagingBatchContractSetup.setNonForfeitureOption(this.nonForfeitureOptionCT);
        stagingBatchContractSetup.setCreationOperator(this.creationOperator);
        stagingBatchContractSetup.setCreationDate(this.creationDate);
        stagingBatchContractSetup.setReceiptDate(this.receiptDate);
        stagingBatchContractSetup.setNumberOfWorkdays(this.numberOfWorkdays);
        stagingBatchContractSetup.setEnrollmentMethod(this.enrollmentMethodCT);

        if (stagingContext.getCurrentEnrollment() != null)
        {
            stagingBatchContractSetup.setEnrollment(stagingContext.getCurrentEnrollment());
            stagingContext.getCurrentEnrollment().addBatchContractSetup(stagingBatchContractSetup);
        }

        SessionHelper.saveOrUpdate(stagingBatchContractSetup, database);

        return stagingContext;
    }
}
