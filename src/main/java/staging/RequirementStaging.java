/**
 * User: dlataill
 * Date: Oct 26, 2007
 * Time: 1:13:47 PM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDateTime;
import edit.services.db.hibernate.SessionHelper;
import group.ContractGroup;

import contract.*;
import edit.common.EDITMap;
import engine.Company;

import java.util.Set;
import java.util.Iterator;


public class RequirementStaging
{
    public static String eventType = "PendingRequirements";

    private EDITDateTime stagingDate;
    private String stagingDateDay;

    public static final String DATABASE = SessionHelper.STAGING;

    public RequirementStaging(EDITDateTime extractDate)
    {
        this.stagingDate = extractDate;
        this.stagingDateDay = this.stagingDate.getEDITDate().getDayOfWeek();
    }

    public int stageTables()
    {
        int requirementCount = 0;

        Segment segment = null;

        contract.Requirement requirement = null;
        try
        {
            StagingContext stagingContext = new StagingContext(eventType, stagingDate);

        ContractRequirement[] contractRequirements = ContractRequirement.findBy_Status(ContractRequirement.REQUIREMENTSTATUSCT_OUTSTANDING);

        requirementCount = contractRequirements.length;

        for (int i = 0; i < contractRequirements.length; i++)
        {
            segment = contractRequirements[i].getSegment();

            requirement = null;
            
            if (reportRequirement(segment.getSegmentStatusCT()))
            {
                SessionHelper.beginTransaction(DATABASE);

                Staging staging = Staging.findStagingByDate_EventType(stagingDate, eventType);

                if (staging == null)
                {
                    staging = new Staging();
                    staging.stage(stagingContext, DATABASE);
                }
                else
                {
                    stagingContext.setStaging(staging);
                    stagingContext.setRecordCount(staging.getRecordCount());
                }

                ContractGroup groupContractGroup = null;
                ContractGroup caseContractGroup = null;

                if (segment.getContractGroupFK() > 0)
                {
                    groupContractGroup = segment.getContractGroup();
                }

                String requirementNotifyDay = "";
                if (groupContractGroup != null)
                {
                    caseContractGroup = groupContractGroup.getContractGroup();
                }

                if (caseContractGroup  != null)
                {
                    if (caseContractGroup.getRequirementNotifyDayCT() != null)
                    {
                        requirementNotifyDay = caseContractGroup.getRequirementNotifyDayCT();
                    }
                }
                // deck: Look at autoReceipt first.
                if (contractRequirements[i].getFilteredRequirement().getRequirement().getAutoReceipt().equals("Y") ||
                    (requirementNotifyDay.equalsIgnoreCase(stagingDateDay) ||
                    (!requirementNotifyDay.equalsIgnoreCase(stagingDateDay)) &&
                     (segment.getFirstNotifyDate() == null || segment.getFirstNotifyDate().equals(stagingDate.getEDITDate()))))
                {
                    SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                    if (segment.getFirstNotifyDate() == null)
                    {
                        segment.setFirstNotifyDate(stagingDate.getEDITDate());
                    }
                    else if (!segment.getFirstNotifyDate().equals(stagingDate.getEDITDate()))
                    {
                        segment.setPreviousNotifyDate(stagingDate.getEDITDate());
                    }

                    SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
                    SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

                    Case stagingCase = Case.findByStagingFK_CaseNumber(staging.getStagingPK(), caseContractGroup.getContractGroupNumber(), DATABASE);
                    if (stagingCase != null)
                    {
                        stagingContext.setCurrentCase(stagingCase);
                    }
                    else
                    {
                        stagingContext.setContractGroupType("Case");
                        caseContractGroup.stage(stagingContext, DATABASE);
                    }

                    Group stagingGroup = Group.findByCaseFK_ContractGroupNumber(stagingContext.getCurrentCase().getCasePK(), groupContractGroup.getContractGroupNumber(), DATABASE);
                    if (stagingGroup != null)
                    {
                        stagingContext.setCurrentGroup(stagingGroup);
                    }
                    else
                    {
                        stagingContext.setContractGroupType("Group");
                        groupContractGroup.stage(stagingContext, DATABASE);
                    }

                    SegmentBase segmentBase = SegmentBase.findByStagingFK_ContractNumber(stagingContext.getStaging().getStagingPK(), segment.getContractNumber());
                    if (segmentBase != null)
                    {
                        stagingContext.setCurrentSegmentBase(segmentBase);
                    }
                    else
                    {
                        stagingContext.setSegmentType("Base");
                        segment.stage(stagingContext, DATABASE);
                    }

                    if (stagingContext.getCurrentSegmentBase().getContractClients().isEmpty())
                    {
                        Set<contract.ContractClient> contractClients = segment.getContractClients();
                        Iterator<contract.ContractClient> it = contractClients.iterator();
                        while (it.hasNext())
                        {
                            contract.ContractClient contractContractClient = it.next();
                            contractContractClient.stage(stagingContext, DATABASE);
                        }
                    }

                    if (stagingContext.getCurrentSegmentBase().getAgents().isEmpty())
                    {
                        Set<AgentHierarchy> agentHierarchies = segment.getAgentHierarchies();
                        Iterator<AgentHierarchy> it2 = agentHierarchies.iterator();
                        while (it2.hasNext())
                        {
                            AgentHierarchy agentHierarchy = it2.next();
                            AgentSnapshot writingAgentSnapshot = AgentSnapshot.findWritingAgentSnapshotBy_AgentHierarchyPK(agentHierarchy.getAgentHierarchyPK());
                            stagingContext.setCurrentAgentHierarchy(agentHierarchy);
                            Set<AgentSnapshot> agentSnapshots = agentHierarchy.getAgentSnapshots();
                            Iterator<AgentSnapshot> it3 = agentSnapshots.iterator();
                            while (it3.hasNext())
                            {
                                AgentSnapshot agentSnapshot = it3.next();
                                agent.Agent agent = agentSnapshot.getPlacedAgent().getAgentContract().getAgent();
                                stagingContext.setCurrentAgentNumber(agentSnapshot.getPlacedAgent().getClientRole().getReferenceID());
                                agent.stage(stagingContext, DATABASE);

                                if (agentSnapshot.getAgentSnapshotPK().equals(writingAgentSnapshot.getAgentSnapshotPK()))
                                {
                                    stagingContext.getCurrentAgent().setAdvancePercent(agentSnapshot.getAdvancePercent());
                                    stagingContext.getCurrentAgent().setRecoveryPercent(agentSnapshot.getRecoveryPercent());

                                    AgentHierarchyAllocation agtHierAlloc = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), segment.getEffectiveDate());

                                    stagingContext.getCurrentAgent().setSplitPercent(agtHierAlloc.getAllocationPercent());
                                    stagingContext.getCurrentAgent().setServicingAgentIndicator(agentSnapshot.getServicingAgentIndicator());
                                    SessionHelper.saveOrUpdate(stagingContext.getCurrentAgent(), DATABASE);
                                }
                            }
                        }
                    }

                    contractRequirements[i].stage(stagingContext, DATABASE);

                    stagingContext.incrementRecordCount(1);

                    stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

                    SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

                    SessionHelper.commitTransaction(DATABASE);

                    FilteredRequirement filteredRequirement = contractRequirements[i].getFilteredRequirement();
                    requirement = filteredRequirement.getRequirement();

                    if (requirement.getAutoReceipt() != null && requirement.getAutoReceipt().equalsIgnoreCase("Y"))
                    {
                        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                        contractRequirements[i].setRequirementStatusCT(ContractRequirement.REQUIREMENTSTATUSCT_RECEIVED);
                        contractRequirements[i].setReceivedDate(stagingDate.getEDITDate());
                        SessionHelper.saveOrUpdate(contractRequirements[i], SessionHelper.EDITSOLUTIONS);
                        SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                    }
                }
            }
        }

        }
        catch (Exception e)
        {
             System.out.println(e);

             e.printStackTrace();

             logErrorToDatabase(e, segment, requirement);
        }
        

        return requirementCount;
    }

    private void logErrorToDatabase(Exception e, Segment segment, contract.Requirement requirement)
    {
        String companyName="";

        String contractNumber ="";

        String groupNumber ="";

        String requirementNumber ="";

        EDITMap columnInfo = new EDITMap();

        Company company = Company.findByProductStructurePK(segment.getProductStructureFK());
        companyName = company.getCompanyName();
        contractNumber = segment.getContractNumber();
        ContractGroup groupContractGroup = segment.getContractGroup();
        groupNumber = groupContractGroup.getContractGroupNumber();
        requirementNumber =  requirement.getRequirementId();


        columnInfo.put("CompanyName", companyName);
        columnInfo.put("ContractNumber", contractNumber);
        columnInfo.put("GroupNumber", groupNumber);
        columnInfo.put("RequirementNumber", requirementNumber);

        logging.Log.logToDatabase(logging.Log.PENDING_REQUIREMENTS, "Pending Requirements Export Job Errored:"+ e.getMessage(), columnInfo);


    }

    private boolean reportRequirement(String segmentStatus)
    {
        boolean reportRequirement = true;

        for (int i = 0; i < Segment.NO_REQ_REPTG_STATUSES.length; i++)
        {
            if (Segment.NO_REQ_REPTG_STATUSES[i].equalsIgnoreCase(segmentStatus))
            {
                reportRequirement = false;
                break;
            }
        }

        return reportRequirement;
    }
}
