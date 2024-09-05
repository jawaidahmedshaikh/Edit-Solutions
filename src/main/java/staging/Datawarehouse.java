/**
 * User: dlataill
 * Date: Dec 24, 2007
 * Time: 7:44:47 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.*;
import edit.services.db.hibernate.SessionHelper;
import edit.services.db.VOClass;
import edit.services.EditServiceLocator;
import edit.services.logging.Logging;
import group.ContractGroup;
import billing.BillSchedule;

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
import edit.common.EDITMap;
import logging.Log;
import logging.LogEvent;
import org.apache.logging.log4j.Logger;



public class Datawarehouse
{
    public static String eventType = "Datawarehouse";

    private EDITDateTime stagingDate;
    private String companyName;
    private String caseNumber;
    private String groupNumber;

    public static final String DATABASE = SessionHelper.DATAWAREHOUSE;

    public Datawarehouse(EDITDateTime dataWarehouseDate, String companyName, String caseNumber, String groupNumber)
    {
        this.stagingDate = dataWarehouseDate;
        this.companyName = companyName;
        this.caseNumber = caseNumber;
        this.groupNumber = groupNumber;
    }

    public void run() throws Exception {
        /* Get all product structures associated with the company name selected by the user */
        Long[] productStructurePKs = getProductStructurePKs();

        /* Get all BillSchedules Associated with specified company */
        Long[] billSchedulePKs = billing.BillSchedule.findByCompany(companyName);

        /* Getting the ContractGroup(s) associated with the case number/group number entered by the user (if any) */
        Long[] contractGroups = getContractGroups();

        StagingContext stagingContext = new StagingContext(eventType, stagingDate, companyName, caseNumber, groupNumber);
        Staging staging = Staging.findDataWarehouseByDate_EventType_Company_Case_Group(stagingDate, eventType, companyName, caseNumber, groupNumber);

        if (staging == null) {
            staging = new Staging(SessionHelper.DATAWAREHOUSE);
            SessionHelper.beginTransaction(DATABASE);
            staging.stage(stagingContext, DATABASE);
            //saving each staging context on every operation when job gets kicked 
            SessionHelper.commitTransaction(DATABASE);
        } else {
            stagingContext.setStaging(staging);
            stagingContext.setRecordCount(staging.getRecordCount());
        }

        Long stagingPK = staging.getStagingPK();

        for (int i = 0; i < billSchedulePKs.length; i++) {

            try {

                //setstaging by pk to avoid multiple stage objects conflict in commit
                staging = (staging.Staging) SessionHelper.get(staging.Staging.class, stagingPK, DATABASE);
                stagingContext.setStaging(staging);

                BillSchedule currentBillSchedule = BillSchedule.findBy_BillSchedulePK(billSchedulePKs[i]);
                SessionHelper.beginTransaction(DATABASE);
                currentBillSchedule.stage(stagingContext, DATABASE);
                //commit every billschedule change
                SessionHelper.commitTransaction(DATABASE);

                Long[] segmentPKs = Segment.findByBillSched_Case_Group_CreationDate(billSchedulePKs[i], contractGroups, stagingDate.getEDITDate());

                //Process the segments associated with the specified company(s)/case/group
                for (int j = 0; j < segmentPKs.length; j++) {
                    try {


                        // for every segment  we are clearing and reinetializing stagingContext so as to reset and clear seassions.
                        staging = (staging.Staging) SessionHelper.get(staging.Staging.class, stagingPK, DATABASE);
                        stagingContext.setStaging(staging);

                        SessionHelper.beginTransaction(SessionHelper.DATAWAREHOUSE);

                        Segment segment = Segment.findByPK(new Long(segmentPKs[j]));
                        ContractGroup groupContractGroup = segment.getContractGroup();
                        ContractGroup caseContractGroup = null;
                        if (groupContractGroup != null) {
                            caseContractGroup = groupContractGroup.getContractGroup();
                        }

                        if (caseContractGroup != null) {
                            stageCase(caseContractGroup, groupContractGroup, stagingContext, staging);
                        }

                        SegmentBase segmentBase = SegmentBase.findByStagingFK_ContractNumber(stagingContext.getStaging().getStagingPK(), segment.getContractNumber());
                        if (segmentBase != null) {
                            stagingContext.setCurrentSegmentBase(segmentBase);
                        } else {
                            stagingContext.setSegmentType("Base");

                            segment.stage(stagingContext, DATABASE);

                            Set<Segment> riders = segment.getSegments();
                            Iterator it = riders.iterator();
                            while (it.hasNext()) {
                                stagingContext.setSegmentType("Rider");
                                Segment rider = (Segment) it.next();
                                rider.stage(stagingContext, DATABASE);

                                Set<contract.ContractClient> contractClients = rider.getContractClients();
                                Iterator it2 = contractClients.iterator();
                                while (it2.hasNext()) {
                                    contract.ContractClient contractClient = (contract.ContractClient) it2.next();
                                    contractClient.stage(stagingContext, DATABASE);
                                }
                            }

                            Set<contract.ContractClient> contractClients = segment.getContractClients();
                            it = contractClients.iterator();
                            while (it.hasNext()) {
                                contract.ContractClient contractClient = (contract.ContractClient) it.next();
                                contractClient.stage(stagingContext, DATABASE);
                            }

                            Set<AgentHierarchy> agentHierarchies = segment.getAgentHierarchies();
                            it = agentHierarchies.iterator();
                            while (it.hasNext()) {
                                AgentHierarchy agentHierarchy = (AgentHierarchy) it.next();
                                AgentSnapshot writingAgentSnapshot = AgentSnapshot.findWritingAgentSnapshotBy_AgentHierarchyPK(agentHierarchy.getAgentHierarchyPK());
                                stagingContext.setCurrentAgentHierarchy(agentHierarchy);
                                Set<AgentSnapshot> agentSnapshots = agentHierarchy.getAgentSnapshots();
                                Iterator it2 = agentSnapshots.iterator();
                                while (it2.hasNext()) {
                                    AgentSnapshot agentSnapshot = (AgentSnapshot) it2.next();
                                    agent.Agent agent = agentSnapshot.getPlacedAgent().getAgentContract().getAgent();
                                    agent.stage(stagingContext, DATABASE);

                                    if (agentSnapshot.getAgentSnapshotPK().equals(writingAgentSnapshot.getAgentSnapshotPK())) {
                                        stagingContext.getCurrentAgent().setAdvancePercent(agentSnapshot.getAdvancePercent());
                                        stagingContext.getCurrentAgent().setRecoveryPercent(agentSnapshot.getRecoveryPercent());

                                        AgentHierarchyAllocation agtHierAlloc = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(agentHierarchy.getAgentHierarchyPK(), stagingDate.getEDITDate());

                                        if (agtHierAlloc != null) {
                                            stagingContext.getCurrentAgent().setSplitPercent(agtHierAlloc.getAllocationPercent());
                                        }
                                        stagingContext.getCurrentAgent().setServicingAgentIndicator(agentSnapshot.getServicingAgentIndicator());
                                        SessionHelper.saveOrUpdate(stagingContext.getCurrentAgent(), DATABASE);
                                    }
                                }
                            }

                            contract.PremiumDue[] premiumDues = contract.PremiumDue.getPremiumDueForDataWarehouse(segment, stagingDate.getEDITDate());

                            if (premiumDues.length > 0) {
                                premiumDues[0].stage(stagingContext, DATABASE);

                                Set<contract.CommissionPhase> commissionPhases = premiumDues[0].getCommissionPhases();
                                it = commissionPhases.iterator();
                                while (it.hasNext()) {
                                    contract.CommissionPhase commissionPhase = (contract.CommissionPhase) it.next();
                                    commissionPhase.stage(stagingContext, DATABASE);
                                }
                            }

                            Set<contract.Investment> investments = segment.getInvestments();
                            it = investments.iterator();
                            while (it.hasNext()) {
                                contract.Investment investment = (contract.Investment) it.next();
                                investment.stage(stagingContext, DATABASE);
                                Set<contract.Bucket> buckets = investment.getBuckets();
                                Iterator it2 = buckets.iterator();
                                while (it2.hasNext()) {
                                    contract.Bucket bucket = (contract.Bucket) it2.next();
                                    bucket.stage(stagingContext, DATABASE);
                                }
                            }

                            boolean segmentHistoryStaged = false;
                            Long[] edittrxPKs = EDITTrx.findBy_Segment_EffectiveDateLTE_PendingStatus(segment.getSegmentPK(), stagingDate.getEDITDate(), new String[]{"H"});
                            for (int k = 0; k < edittrxPKs.length; k++) {
                                EDITTrx edittrxs = EDITTrx.findByPK(edittrxPKs[k]);
                                Set<EDITTrxHistory> editTrxHistories = edittrxs.getEDITTrxHistories();
                                it = editTrxHistories.iterator();
                                segmentHistoryStaged = false;
                                while (it.hasNext()) {
                                    EDITTrxHistory editTrxHistory = (EDITTrxHistory) it.next();
                                    Set<SegmentHistory> segmentHistories = editTrxHistory.getSegmentHistories();
                                    Iterator it2 = segmentHistories.iterator();
                                    while (it2.hasNext()) {
                                        SegmentHistory segmentHistory = (SegmentHistory) it2.next();
                                        if (!segmentHistoryStaged) {
                                            segmentHistory.stage(stagingContext, DATABASE);

                                            segmentHistoryStaged = true;
                                        }

                                        if (stagingContext.getCurrentSegmentBase().getPaidDate() == null
                                                && segmentHistory.getStatusCT() != null
                                                && segmentHistory.getStatusCT().equalsIgnoreCase("Active")
                                                && segmentHistory.getPrevSegmentStatus().equalsIgnoreCase("IssuePendingPremium")) {
                                            stagingContext.getCurrentSegmentBase().setPaidDate(editTrxHistory.getProcessDateTime().getEDITDate());
                                        }
                                    }
                                }
                            }
                        }

                        if (!segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SUBMIT_PEND)) {
                            NaturalDocVO naturalDocVO = getNaturalDocData(segment);
                            CorrespondenceDetailVO correspondenceDetailVO = new CorrespondenceDetailVO();

                            correspondenceDetailVO.setCorrespondenceDetailPK(0);
                            naturalDocVO.setNaturalDocPK(1);

                            ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK());

                            correspondenceDetailVO.addNaturalDocVO(naturalDocVO);
                            correspondenceDetailVO.setCorrespondenceType("DataWarehouse");
                            correspondenceDetailVO.addProductStructureVO((ProductStructureVO) productStructure.getVO());

                            //All updates vo's will be in voObjects
                            VOObject[] voObjects = null;
                            Calculator calcComponent = new CalculatorComponent();

                            SPOutput spOutput = calcComponent.processScript("CorrespondenceDetailVO", correspondenceDetailVO, "Correspondence", "*", "*", stagingDate.getEDITDate().getFormattedDate(), segment.getProductStructureFK().longValue(), true);

                            voObjects = spOutput.getSPOutputVO().getVOObject();

                            if (voObjects != null) {
                                for (int k = 0; k < voObjects.length; k++) {
                                    VOObject voObject = (VOObject) voObjects[k];

                                    if (voObject instanceof CalculatedValuesVO) {
                                        CalculatedValues calculatedValues = new CalculatedValues((CalculatedValuesVO) voObject);
                                        calculatedValues.stage(stagingContext, DATABASE);
                                    }
                                }
                            }
                        }

                        stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

                        SessionHelper.saveOrUpdate(stagingContext.getStaging(), SessionHelper.DATAWAREHOUSE);

                        SessionHelper.commitTransaction(SessionHelper.DATAWAREHOUSE);
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateSuccess();
                    } catch (Exception e) {
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateFailure();

                        SessionHelper.rollbackTransaction(SessionHelper.DATAWAREHOUSE);

                        System.out.println(e);

                        e.printStackTrace();

                        LogEvent logEvent = new LogEvent(e.getMessage(), e);

                        Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                        logger.error(logEvent);

                        EDITMap columnInfo = new EDITMap();

                        columnInfo.put("CompanyName", companyName);

                        Log.logToDatabase(Log.DATAWAREHOUSE, "Data Warehouse Job Errored:" + e.getMessage(), columnInfo);

                    } finally {
                        SessionHelper.clearSessions();
                    }
                }

            } catch (Exception e) {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateFailure();

                SessionHelper.rollbackTransaction(SessionHelper.DATAWAREHOUSE);

                System.out.println(e);

                e.printStackTrace();

                LogEvent logEvent = new LogEvent(e.getMessage(), e);

                Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                logger.error(logEvent);

                EDITMap columnInfo = new EDITMap();

                columnInfo.put("CompanyName", companyName);

                Log.logToDatabase(Log.DATAWAREHOUSE, "Data Warehouse Job Errored:" + e.getMessage(), columnInfo);

            }
            SessionHelper.clearSessions();
        }


        //Now process groups without BillSchedules and Cases without groups
        if (contractGroups.length > 0) {
            for (int i = 0; i < contractGroups.length; i++) {
                ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupPK(contractGroups[i]);
                if (groupContractGroup.getBillSchedule() == null) {
                    ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

                    if (caseContractGroup != null) {
                        try {
                            
                            SessionHelper.beginTransaction(SessionHelper.DATAWAREHOUSE);
                            stageCase(caseContractGroup, groupContractGroup, stagingContext, staging);

                            stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

                            SessionHelper.saveOrUpdate(stagingContext.getStaging(), SessionHelper.DATAWAREHOUSE);

                            SessionHelper.commitTransaction(SessionHelper.DATAWAREHOUSE);

                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateSuccess();

                        } catch (Exception e) {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateFailure();

                            SessionHelper.rollbackTransaction(SessionHelper.DATAWAREHOUSE);

                            System.out.println(e);

                            e.printStackTrace();

                            LogEvent logEvent = new LogEvent(e.getMessage(), e);

                            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                            logger.error(logEvent);

                            EDITMap columnInfo = new EDITMap();

                            columnInfo.put("CompanyName", companyName);

                            Log.logToDatabase(Log.DATAWAREHOUSE, "Data Warehouse Job Errored:" + e.getMessage(), columnInfo);

                        } 
                    }
                }
            }
        } else {
            ContractGroup[] caseContractGroups = ContractGroup.findAllCases();
            boolean groupFound = false;
            for (int i = 0; i < caseContractGroups.length; i++) {
                groupFound = false;
                Set<ContractGroup> groupContractGroups = caseContractGroups[i].getContractGroups();
                Iterator it = groupContractGroups.iterator();
                while (it.hasNext()) {

                    groupFound = true;
                    ContractGroup group = (ContractGroup) it.next();
                    if (group.getBillSchedule() == null) {
                        try {
                            
                            SessionHelper.beginTransaction(SessionHelper.DATAWAREHOUSE);
                            stageCase(caseContractGroups[i], group, stagingContext, staging);

                            stagingContext.setRecordCount(staging.getRecordCount());

                            SessionHelper.saveOrUpdate(stagingContext.getStaging(), SessionHelper.DATAWAREHOUSE);

                            SessionHelper.commitTransaction(SessionHelper.DATAWAREHOUSE);

                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateSuccess();
                        } catch (Exception e) {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateFailure();

                            SessionHelper.rollbackTransaction(SessionHelper.DATAWAREHOUSE);

                            System.out.println(e);

                            e.printStackTrace();

                            LogEvent logEvent = new LogEvent(e.getMessage(), e);

                            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                            logger.error(logEvent);

                            EDITMap columnInfo = new EDITMap();

                            columnInfo.put("CompanyName", companyName);

                            Log.logToDatabase(Log.DATAWAREHOUSE, "Data Warehouse Job Errored:" + e.getMessage(), columnInfo);
                        } 
                    }

                }
                if (!groupFound) {
                    if (caseContractGroups[i].getContractGroupNumber() != null) {

                        try {
                            
                            SessionHelper.beginTransaction(SessionHelper.DATAWAREHOUSE);
                            Case stagingCase = Case.findByStagingFK_CaseNumber(staging.getStagingPK(), caseContractGroups[i].getContractGroupNumber(), DATABASE);
                            if (stagingCase != null) {
                                stagingContext.setCurrentCase(stagingCase);
                            } else {
                                stagingContext.setContractGroupType("Case");
                                caseContractGroups[i].stage(stagingContext, DATABASE);

                                stagingContext = stageEnrollmentsAndChildren(caseContractGroups[i], stagingContext);
                                Set<group.CaseStatusChangeHistory> caseStatusChangeHistories = caseContractGroups[i].getCaseStatusChangeHistories();
                                Iterator it2 = caseStatusChangeHistories.iterator();
                                while (it2.hasNext()) {
                                    group.CaseStatusChangeHistory caseStatusChangeHistory = (group.CaseStatusChangeHistory) it2.next();
                                    if (caseStatusChangeHistory.getChangeEffectiveDate().beforeOREqual(stagingDate.getEDITDate())) {
                                        caseStatusChangeHistory.stage(stagingContext, DATABASE);
                                    }
                                }
                            }

                            stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

                            SessionHelper.saveOrUpdate(stagingContext.getStaging(), SessionHelper.DATAWAREHOUSE);

                            SessionHelper.commitTransaction(SessionHelper.DATAWAREHOUSE);

                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateSuccess();
                        } catch (Exception e) {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateFailure();

                            SessionHelper.rollbackTransaction(SessionHelper.DATAWAREHOUSE);

                            System.out.println(e);

                            e.printStackTrace();

                            LogEvent logEvent = new LogEvent(e.getMessage(), e);

                            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                            logger.error(logEvent);

                            EDITMap columnInfo = new EDITMap();

                            columnInfo.put("CompanyName", companyName);

                            Log.logToDatabase(Log.DATAWAREHOUSE, "Data Warehouse Job Errored:" + e.getMessage(), columnInfo);
                        } 
                    } else {
                        try {

                            staging = (staging.Staging) SessionHelper.get(staging.Staging.class, stagingPK, DATABASE);

                            stagingContext.setContractGroupType("Case");
                            caseContractGroups[i].stage(stagingContext, DATABASE);

                            stagingContext = stageEnrollmentsAndChildren(caseContractGroups[i], stagingContext);
                            Set<group.CaseStatusChangeHistory> caseStatusChangeHistories = caseContractGroups[i].getCaseStatusChangeHistories();
                            Iterator it2 = caseStatusChangeHistories.iterator();
                            while (it2.hasNext()) {
                                group.CaseStatusChangeHistory caseStatusChangeHistory = (group.CaseStatusChangeHistory) it2.next();
                                if (caseStatusChangeHistory.getChangeEffectiveDate().beforeOREqual(stagingDate.getEDITDate())) {
                                    caseStatusChangeHistory.stage(stagingContext, DATABASE);
                                }
                            }

                            stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

                            SessionHelper.saveOrUpdate(stagingContext.getStaging(), SessionHelper.DATAWAREHOUSE);

                            SessionHelper.commitTransaction(SessionHelper.DATAWAREHOUSE);

                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateSuccess();
                        } catch (Exception e) {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateFailure();

                            SessionHelper.rollbackTransaction(SessionHelper.DATAWAREHOUSE);

                            System.out.println(e);

                            e.printStackTrace();

                            LogEvent logEvent = new LogEvent(e.getMessage(), e);

                            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                            logger.error(logEvent);

                            EDITMap columnInfo = new EDITMap();

                            columnInfo.put("CompanyName", companyName);

                            Log.logToDatabase(Log.DATAWAREHOUSE, "Data Warehouse Job Errored:" + e.getMessage(), columnInfo);

                        } 
                    }
                }
            }
        }
    }

    private void stageCase(ContractGroup caseContractGroup, ContractGroup groupContractGroup, StagingContext stagingContext, Staging staging) {

        Case stagingCase = Case.findByStagingFK_CaseNumber(staging.getStagingPK(), caseContractGroup.getContractGroupNumber(), DATABASE);
        if (stagingCase != null) {
            stagingContext.setCurrentCase(stagingCase);
        } else {
            stagingContext.setContractGroupType("Case");
            caseContractGroup.stage(stagingContext, DATABASE);

            stagingContext = stageEnrollmentsAndChildren(caseContractGroup, stagingContext);
            Set<group.CaseStatusChangeHistory> caseStatusChangeHistories = caseContractGroup.getCaseStatusChangeHistories();
            Iterator it = caseStatusChangeHistories.iterator();
            while (it.hasNext()) {
                group.CaseStatusChangeHistory caseStatusChangeHistory = (group.CaseStatusChangeHistory) it.next();
                if (caseStatusChangeHistory.getChangeEffectiveDate().beforeOREqual(stagingDate.getEDITDate())) {
                    caseStatusChangeHistory.stage(stagingContext, DATABASE);
                }
            }
        }

        Group stagingGroup = Group.findByCaseFK_ContractGroupNumber(stagingContext.getCurrentCase().getCasePK(), groupContractGroup.getContractGroupNumber(), DATABASE);
        if (stagingGroup != null) {
            stagingContext.setCurrentGroup(stagingGroup);
        } else {
            stagingContext.setContractGroupType("Group");
            groupContractGroup.stage(stagingContext, DATABASE);
        }
    }

    private Long[] getProductStructurePKs()
    {
        ProductStructure[] productStructures = null;

        if (!companyName.equalsIgnoreCase("All"))
        {
            productStructures = ProductStructure.findBy_CompanyName(companyName);
        }
        else
        {
            productStructures = ProductStructure.findAllProductType();
        }

        Long[] productStructurePKs = null;

        if (productStructures != null && productStructures.length > 0)
        {
            productStructurePKs = new Long[productStructures.length];
            for (int i = 0; i < productStructures.length; i++)
            {
                productStructurePKs[i] = productStructures[i].getProductStructurePK();
            }
        }
        else
        {
            productStructurePKs = new Long[0];
        }

        return productStructurePKs;
    }
    
    /**
     * If the specified Case or Group Number (caseGroupNumber) is "" (empty String) or NULL (predefined
     * constant), then we assume the caseGroupNumber is [not] valid.
     * @param contractNumber
     * @return true as long as contractNumber is not "" or NULL
     */
    private boolean validCaseGroupNumber(String caseGroupNumber)
    {
        boolean validCaseGroupNumber = true;
        
        if (caseGroupNumber.trim().equals(""))
        {
            validCaseGroupNumber = false;
        }
        else if (caseGroupNumber.trim().equalsIgnoreCase(Batch.NULL))
        {
            validCaseGroupNumber = false;
        }
        
        return validCaseGroupNumber;
    }

    private Long[] getContractGroups() throws Exception
    {
        ContractGroup[] groupContractGroups = null;
        ContractGroup caseContractGroup = null;

        if (validCaseGroupNumber(groupNumber))
        {
            ContractGroup group = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(groupNumber, "Group");
            if (group != null)
            {
                groupContractGroups = new ContractGroup[] {group};
            }
        }
        else if (validCaseGroupNumber(caseNumber))
        {
            caseContractGroup = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(caseNumber, "Case");

            if (caseContractGroup != null)
            {
                groupContractGroups = ContractGroup.findBy_ContractGroupFK_ContractGroupTypeCT(caseContractGroup.getContractGroupPK(), "Group");
            }
        }
        else
        {
            groupContractGroups = new ContractGroup[0];
        }

        Long[] contractGroupPKs = null;

        if (groupContractGroups == null)
        {
            throw new Exception("Specified Case/Group For Data Warehouse Job Not Found");
        }
        else
        {
            contractGroupPKs = new Long[groupContractGroups.length];
            for (int i = 0; i < groupContractGroups.length; i++)
            {
                contractGroupPKs[i] = groupContractGroups[i].getContractGroupPK();
            }
        }

        return contractGroupPKs;
    }

    private StagingContext stageEnrollmentsAndChildren(ContractGroup caseContractGroup, StagingContext stagingContext)
    {
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

            Set<group.ProjectedBusinessByMonth> projBusByMonths = enrollment.getProjectedBusinessByMonths();
            it2 = projBusByMonths.iterator();
            while (it2.hasNext())
            {
                group.ProjectedBusinessByMonth projBusByMonth = (group.ProjectedBusinessByMonth) it2.next();

                projBusByMonth.stage(stagingContext, DATABASE);
            }

            Set<group.BatchContractSetup> batchContractSetups = enrollment.getBatchContractSetups();
            Iterator it3 = batchContractSetups.iterator();
            while (it3.hasNext())
            {
                group.BatchContractSetup batchContractSetup = (group.BatchContractSetup) it3.next();

                batchContractSetup.stage(stagingContext, DATABASE);
            }

            Set<group.CaseProductUnderwriting> caseProductUnderwritings = enrollment.getCaseProductUnderwritings();
            Iterator it4 = caseProductUnderwritings.iterator();
            while (it4.hasNext())
            {
                group.CaseProductUnderwriting caseProductUnderwriting = (group.CaseProductUnderwriting) it4.next();

                contract.FilteredProduct filteredProduct = caseProductUnderwriting.getFilteredProduct();
                filteredProduct.stage(stagingContext, DATABASE);

                caseProductUnderwriting.stage(stagingContext, DATABASE);
            }

            Set<group.EnrollmentState> enrollmentStates = enrollment.getEnrollmentStates();
            Iterator it5 = enrollmentStates.iterator();
            while (it5.hasNext())
            {
                group.EnrollmentState enrollmentState = (group.EnrollmentState) it5.next();

                enrollmentState.stage(stagingContext, DATABASE);
            }
        }

        return stagingContext;
    }

    private NaturalDocVO getNaturalDocData(Segment segment) throws Exception
    {
        NaturalDocVO naturalDocVO = null;
        CodeTableComponent codeTableComponent = new CodeTableComponent();

        naturalDocVO = codeTableComponent.buildNaturalDocForDataWarehouse(segment, stagingDate.getEDITDate());

//        getAdditionalData(naturalDocVO);
//
//        String corrAddressType = editTrxCorrespondence.getAddressTypeCT();
//        if (corrAddressType == null  || corrAddressType.equals(""))
//        {
//            corrAddressType = "PrimaryAddress";
//        }
//
//        filterClientAddressByCTTypeAndTerminationDate(naturalDocVO.getBaseSegmentVO().getClientVO(),
//                                                       corrAddressType,
//                                                        new EDITDate(),
//                                                         editTrxCorrespondence.getEDITTrxCorrespondencePK().longValue());
//
        return naturalDocVO;
    }
}
