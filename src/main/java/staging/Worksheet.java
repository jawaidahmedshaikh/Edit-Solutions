/**
 * User: dlataill
 * Date: Mar 14, 2008
 * Time: 9:21:47 AM
 * <p/>
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import contract.ContractClient;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.*;
import edit.services.db.hibernate.SessionHelper;
import edit.services.db.VOClass;
import edit.services.EditServiceLocator;
import edit.services.logging.Logging;
import group.ContractGroup;

import contract.Segment;
import contract.AgentHierarchy;
import contract.AgentSnapshot;
import contract.AgentHierarchyAllocation;
import engine.ProductStructure;
import engine.sp.SPOutput;
import engine.component.CalculatorComponent;
import engine.business.Calculator;

import java.util.*;

import event.EDITTrxCorrespondence;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.SegmentHistory;
import event.dm.composer.VOComposer;
import event.dm.dao.DAOFactory;
import codetable.component.CodeTableComponent;
import batch.business.Batch;
import logging.LogEvent;
import logging.LogEntry;
import org.apache.logging.log4j.Logger;
import agent.PlacedAgent;
import contract.business.Contract;
import edit.common.EDITMap;
import engine.Company;
import role.ClientRole;


public class Worksheet
{
    public static String eventType = "Worksheet";

    private EDITDateTime stagingDate;

    public static final String DATABASE = SessionHelper.STAGING;

    public Worksheet(EDITDateTime worksheetDate)
    {
        this.stagingDate = worksheetDate;
    }

    public void run(String batchId) throws Exception
    {
        StagingContext stagingContext = new StagingContext(eventType, stagingDate);

        Segment[] segments = Segment.findForWorksheet(batchId);

        if (segments.length == 0)
        {
            SessionHelper.beginTransaction(DATABASE);

            Staging staging = new Staging(DATABASE);
            staging.stage(stagingContext, DATABASE);

            SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

            SessionHelper.commitTransaction(DATABASE);

            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_WORKSHEET).updateSuccess();
        }
        else
        {
            for (int i = 0; i < segments.length; i++)
            {   
                Segment segment =null;
                
                try
                {
                    SessionHelper.beginTransaction(DATABASE);

                    Staging staging = Staging.findStagingByDate_EventType(stagingDate, eventType);

                    if (staging == null)
                    {
                        staging = new Staging(DATABASE);
                        staging.stage(stagingContext, DATABASE);
                    }
                    else
                    {
                        stagingContext.setStaging(staging);
                        stagingContext.setRecordCount(staging.getRecordCount());
                    }

                    segment = segments[i];

                    ContractGroup groupContractGroup = segment.getContractGroup();
                    ContractGroup caseContractGroup = null;
                    if (groupContractGroup != null)
                    {
                        caseContractGroup = groupContractGroup.getContractGroup();
                    }

                    if (caseContractGroup != null)
                    {
                        Case stagingCase = Case.findByStagingFK_CaseNumber(staging.getStagingPK(), caseContractGroup.getContractGroupNumber(), DATABASE);
                        if (stagingCase != null)
                        {
                            stagingContext.setCurrentCase(stagingCase);
                        }
                        else
                        {
                            stagingContext.setContractGroupType("Case");
                            caseContractGroup.stage(stagingContext, DATABASE);

                            Set<group.Enrollment> enrollments = caseContractGroup.getEnrollments();
                            Iterator it = enrollments.iterator();
                            while (it.hasNext())
                            {
                                group.Enrollment enrollment = (group.Enrollment) it.next();

                                enrollment.stage(stagingContext, DATABASE);

                                Set<group.EnrollmentLeadServiceAgent> enrollmentLeadServiceAgents = enrollment.getEnrollmentLeadServiceAgents();
                                Iterator it2 = enrollmentLeadServiceAgents.iterator();
                                while (it2.hasNext())
                                {
                                    group.EnrollmentLeadServiceAgent enrollmentLeadServiceAgent = (group.EnrollmentLeadServiceAgent) it2.next();

                                    enrollmentLeadServiceAgent.stage(stagingContext, DATABASE);
                                }
                            }
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

                            Set<group.PayrollDeductionSchedule> payrollDeductionSchedules = groupContractGroup.getPayrollDeductionSchedules();
                            Iterator it = payrollDeductionSchedules.iterator();
                            while (it.hasNext())
                            {
                                group.PayrollDeductionSchedule payDedSched = (group.PayrollDeductionSchedule) it.next();
                                payDedSched.stage(stagingContext);
                            }
                        }
                    }

                    group.BatchContractSetup batchContractSetup = segment.getBatchContractSetup();
                    if (batchContractSetup != null)
                    {
                        BatchContractSetup stagingBatchContractSetup = BatchContractSetup.findByGroupFK_BatchID(stagingContext.getCurrentGroup().getGroupPK(), batchContractSetup.getBatchID(), DATABASE);
                        if (stagingBatchContractSetup == null)
                        {
                            batchContractSetup.stage(stagingContext, DATABASE);
                        }
                    }

                    billing.BillSchedule billingBillSchedule = segment.getBillSchedule();
                    BillSchedule billSchedule = BillSchedule.findByStagingFK(staging.getStagingPK(), billingBillSchedule.getBillSchedulePK(), DATABASE);
                    if (billSchedule != null)
                    {
                        stagingContext.setCurrentBillSchedule(billSchedule);
                    }
                    else
                    {
                        billingBillSchedule.stage(stagingContext, DATABASE);
                    }

                    stagingContext.setSegmentType("Base");
                    segment.stage(stagingContext, DATABASE);

                    Set<Segment> riders = segment.getSegments();
                    Iterator it = riders.iterator();
                    while (it.hasNext())
                    {
                        stagingContext.setSegmentType("Rider");
                        Segment rider = (Segment) it.next();
                        rider.stage(stagingContext, DATABASE);

                        Set<contract.ContractClient> contractClients = rider.getContractClients();
                        Iterator it2 = contractClients.iterator();
                        while (it2.hasNext())
                        {
                            contract.ContractClient contractClient = (contract.ContractClient) it2.next();
                            contractClient.stage(stagingContext, DATABASE);
                        }
                    }

                    Set<contract.ContractClient> contractClients = segment.getContractClients();
                    it = contractClients.iterator();
                    while (it.hasNext())
                    {
                        contract.ContractClient contractClient = (contract.ContractClient) it.next();
                        contractClient.stage(stagingContext, DATABASE);
                    }

                    Set<AgentHierarchy> agentHierarchies = segment.getAgentHierarchies();
                    it = agentHierarchies.iterator();
                    while (it.hasNext())
                    {
                        AgentHierarchy agentHierarchy = (AgentHierarchy) it.next();
                        AgentSnapshot writingAgentSnapshot = AgentSnapshot.findWritingAgentSnapshotBy_AgentHierarchyPK(agentHierarchy.getAgentHierarchyPK());
                        stagingContext.setCurrentAgentHierarchy(agentHierarchy);
                        Set<AgentSnapshot> agentSnapshots = agentHierarchy.getAgentSnapshots();
                        Iterator it2 = agentSnapshots.iterator();
                        while (it2.hasNext())
                        {
                            AgentSnapshot agentSnapshot = (AgentSnapshot) it2.next();
                            PlacedAgent placedAgent = agentSnapshot.getPlacedAgent();
                            agent.Agent agent = placedAgent.getAgentContract().getAgent();

                            ClientRole clientRole = ClientRole.findByPK(placedAgent.getClientRoleFK());
                            stagingContext.setCurrentAgentNumber(clientRole.getReferenceID());

                            agent.stage(stagingContext, DATABASE);
                            stagingContext.getCurrentAgent().setSituationCode(placedAgent.getSituationCode());
                            stagingContext.getCurrentAgent().setAgentLevel(placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile().getCommissionLevelCT());

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

                    contract.PremiumDue[] premiumDues = contract.PremiumDue.getPremiumDueForDataWarehouse(segment, segment.getEffectiveDate());

                    if (premiumDues.length > 0)
                    {
                        premiumDues[0].stage(stagingContext, DATABASE);

                        Set<contract.CommissionPhase> commissionPhases = premiumDues[0].getCommissionPhases();
                        it = commissionPhases.iterator();
                        while (it.hasNext())
                        {
                            contract.CommissionPhase commissionPhase = (contract.CommissionPhase) it.next();
                            commissionPhase.stage(stagingContext, DATABASE);
                        }
                    }

                    LogEntry[] logEntries = LogEntry.findLatestCreationDateTime_ByContractNumber(segment.getContractNumber());
                    for (int j = 0; j < logEntries.length; j++)
                    {
                        LogEntry logEntry = logEntries[j];
                        logEntry.stage(stagingContext, DATABASE);
                    }

                    stagingContext.incrementRecordCount(1);

                    stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

                    SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

                    SessionHelper.commitTransaction(DATABASE);

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_WORKSHEET).updateSuccess();

                    /* Need to set worksheetTypeCT = null to indicate the worksheet has already been generated for the change(s)
                       made to the contract */
                    SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                    segment.setWorksheetTypeCT(null);
                    SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
                    SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
                }
                catch (Exception e)
                {
                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_WORKSHEET).updateFailure();

                    logErrorToDatabase(e, segment);

                    SessionHelper.rollbackTransaction(DATABASE);

                    System.out.println(e);

                    e.printStackTrace();

                    LogEvent logEvent = new LogEvent(e.getMessage(), e);

                    Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                    logger.error(logEvent);

                }
               
            }
        }
    }



     private void logErrorToDatabase(Exception e, Segment segment)
    {
        Company comapny = Company.findByProductStructurePK(segment.getProductStructureFK());
        String batchNumber = segment.getBatchContractSetup().getBatchID();

        ContractClient payorContractClient = segment.getPayorContractClient();
        ClientRole clientRole = payorContractClient.getClientRole();
        String payorName = clientRole.getClientDetail().getPrettyName();

        EDITMap columnInfo = new EDITMap("CompanyName", comapny.getCompanyName());
        columnInfo.put("ContractNumber", segment.getContractNumber());
        columnInfo.put("BatchNumber", batchNumber);
        columnInfo.put("PayorName", payorName);
        columnInfo.put("WorkSheetType", segment.getWorksheetTypeCT());

        logging.Log.logToDatabase(logging.Log.WORKSHEET, "Worksheet Job Errored: " + e.getMessage(), columnInfo);
    }
}
