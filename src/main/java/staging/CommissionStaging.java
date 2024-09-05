/**
 * User: dlataill
 * Date: Oct 23, 2007
 * Time: 10:46:47 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITBigDecimal;
import edit.common.vo.*;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import group.ContractGroup;
import contract.Segment;
import contract.AgentHierarchy;
import contract.AgentSnapshot;
import contract.AgentHierarchyAllocation;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.ClientSetup;
import event.ContractSetup;
import event.CommissionHistory;
import event.component.EventComponent;
import event.business.Event;
import agent.AgentStatement;
import agent.AgentContract;
import agent.PlacedAgent;

import java.util.*;

import role.ClientRole;
import role.ClientRoleFinancial;
import logging.LogEvent;

import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import fission.utility.Util;


public class CommissionStaging
{
    public static String eventType = "CommStmt";
    public EDITBigDecimal groupFYEarn;
    public EDITBigDecimal groupRnwlEarn;
    public EDITBigDecimal grpAdvancesOnly;
    public EDITBigDecimal grpChargebacks;
    public EDITBigDecimal grpRecoveries;
    public EDITBigDecimal grpAdvances;
    public EDITBigDecimal indFYEarn;
    public EDITBigDecimal indRnwlEarn;
    public EDITBigDecimal indAdvancesOnly;
    public EDITBigDecimal indChargebacks;
    public EDITBigDecimal indRecoveries;
    public EDITBigDecimal indAdvances;
    public EDITBigDecimal manualAdjustments;
        
    public EDITBigDecimal ytdGroupFYEarn = new EDITBigDecimal();
    public EDITBigDecimal ytdGroupRnwlEarn= new EDITBigDecimal();
    public EDITBigDecimal ytdGrpAdvancesOnly= new EDITBigDecimal();
    public EDITBigDecimal ytdGrpChargebacks= new EDITBigDecimal();
    public EDITBigDecimal ytdGrpRecoveries= new EDITBigDecimal();
    public EDITBigDecimal ytdGrpAdvances= new EDITBigDecimal();
    public EDITBigDecimal ytdIndFYEarn= new EDITBigDecimal();
    public EDITBigDecimal ytdIndRnwlEarn= new EDITBigDecimal();
    public EDITBigDecimal ytdIndAdvancesOnly= new EDITBigDecimal();
    public EDITBigDecimal ytdIndChargebacks= new EDITBigDecimal();
    public EDITBigDecimal ytdIndRecoveries= new EDITBigDecimal();
    public EDITBigDecimal ytdIndAdvances= new EDITBigDecimal();

    boolean newAgentValues;

    private EDITDateTime stagingDate;

    public static final String DATABASE = SessionHelper.STAGING;

    public CommissionStaging(EDITDateTime stagingDate)
    {
        this.stagingDate = stagingDate;
    }
    
    public StagingContext getStagingContext() {
    	return new StagingContext(eventType, stagingDate);
    }
    
    public void stageTables(List<CommissionHistory> commissionHistories, AgentContract agentContract, PlacedAgent placedAgent)
    {
    	
    	String currentAgentId = null;
    try
        {
           SessionHelper.beginTransaction(DATABASE);
           StagingContext stagingContext = getStagingContext();
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
           
            agent.Agent agentAgent = agent.Agent.findBy_PK(agentContract.getAgentFK());

            ClientRole clientRole = ClientRole.findByPK(placedAgent.getClientRoleFK());
            currentAgentId = clientRole.getReferenceID();
            stagingContext.setCurrentAgentNumber(currentAgentId);
 
            Agent agent = Agent.findByStagingFK_AgentNumber(staging.getStagingPK(), stagingContext.getCurrentAgentNumber(), DATABASE);
            if (agent != null)
            {
                stagingContext.setCurrentAgent(agent);
                newAgentValues = false;
            }
            else
            {
                agentAgent.stage(stagingContext, DATABASE);
                newAgentValues = true;
            }

            EDITBigDecimal commBalance = new EDITBigDecimal();
            EDITBigDecimal lifetimeCommBalance = new EDITBigDecimal();
            EDITBigDecimal lifetimeAdvanceBalance = new EDITBigDecimal();
            EDITBigDecimal lastStatementAmount = new EDITBigDecimal();
            EDITBigDecimal OutstandingAdvanceBalance = new EDITBigDecimal();
            
            ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();
            EDITDateTime priorLastStatementDateTime = null;
            if (clientRoleFinancial != null)
            {
                priorLastStatementDateTime = clientRoleFinancial.getPriorLastStatementDateTime();
                commBalance = clientRoleFinancial.getCommBalance();
                lifetimeCommBalance = clientRoleFinancial.getLifetimeCommBalance();
                lifetimeAdvanceBalance = clientRoleFinancial.getLifetimeAdvanceBalance();
                lastStatementAmount =  clientRoleFinancial.getLastStatementAmount();
                OutstandingAdvanceBalance = clientRoleFinancial.getAdvanceAmt().subtractEditBigDecimal(clientRoleFinancial.getAdvRecoveredAmt());
            }

            stagingContext.getCurrentAgent().setCommBalance(commBalance);
            stagingContext.getCurrentAgent().setLifetimeCommBalance(lifetimeCommBalance);
            stagingContext.getCurrentAgent().setLifetimeAdvanceBalance(lifetimeAdvanceBalance);
            stagingContext.getCurrentAgent().setLastStatementAmount(lastStatementAmount);
            stagingContext.getCurrentAgent().setOutstandingAdvanceBalance(OutstandingAdvanceBalance);

            if (priorLastStatementDateTime ==  null)
            {
                priorLastStatementDateTime = new EDITDateTime(EDITDate.DEFAULT_MIN_DATE + EDITDateTime.DATE_TIME_DELIMITER + EDITDateTime.DEFAULT_MIN_TIME);
            }
            if (newAgentValues)
            {
                groupFYEarn = new EDITBigDecimal();
                groupRnwlEarn = new EDITBigDecimal();
                grpChargebacks = new EDITBigDecimal();
                grpRecoveries = new EDITBigDecimal();
                grpAdvances = new EDITBigDecimal();
                indFYEarn = new EDITBigDecimal();
                indRnwlEarn = new EDITBigDecimal();
                indChargebacks = new EDITBigDecimal();
                indRecoveries = new EDITBigDecimal();
                indAdvances = new EDITBigDecimal();
                manualAdjustments = new EDITBigDecimal();
            }
            else
            {
                groupFYEarn = stagingContext.getCurrentAgent().getGroupFYEarn();
                groupRnwlEarn = stagingContext.getCurrentAgent().getGroupRnwlEarn();
                grpAdvances = stagingContext.getCurrentAgent().getGrpAdvances();
                grpChargebacks = new EDITBigDecimal();
                grpRecoveries = new EDITBigDecimal();
                indFYEarn = stagingContext.getCurrentAgent().getIndFYEarn();
                indRnwlEarn = stagingContext.getCurrentAgent().getIndRnwlEarn();
                indChargebacks = new EDITBigDecimal();
                indRecoveries = new EDITBigDecimal();
                indAdvances = stagingContext.getCurrentAgent().getIndAdvances();
                manualAdjustments = stagingContext.getCurrentAgent().getManualAdjustments();
            }


                String commHistoryType = "Curr";
                CommissionHistoryVO[] currBalCommissionHistoryVOs = getCommissionHistoriesForCurrBalances(priorLastStatementDateTime, placedAgent.getPlacedAgentPK());
                stagingContext.setCurrentAgent(populateAgentEarnings(commHistoryType, currBalCommissionHistoryVOs, stagingContext.getCurrentAgent()));

                SessionHelper.commitTransaction(DATABASE);
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent(e.getMessage(), e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }

    	SessionHelper.beginTransaction(DATABASE);
    	StagingContext stagingContext = null;
    	
    	// caches of staging objects
    	HashMap<String, staging.Case> stagingCasesByContractGroup = new HashMap<>();
    	HashMap<String, Group> casePkAndGroupNumberToGroup = new HashMap<>();	
    	
    	// pull all of the SegmentBase records we'll need
    	HashSet<String> refContractNumbers = new HashSet<>();
    	for(CommissionHistory ch : commissionHistories) {
    		Segment segment = ch.getEDITTrxHistory().getEDITTrx()
    				.getClientSetup().getContractSetup().getSegment();
    		if(segment != null)
    			refContractNumbers.add(segment.getContractNumber());
    	}
    	
    	if(stagingContext == null) {
	        stagingContext = getStagingContext();
	        stagingContext.setStaging(Staging.findStagingByDate_EventType(stagingDate, eventType));
	        stagingContext.setCurrentAgentNumber(currentAgentId);
	        stagingContext.setCurrentAgent(Agent.findByStagingFK_AgentNumber(stagingContext.getStaging().getStagingPK(), 
	        					stagingContext.getCurrentAgentNumber(), DATABASE));
    	} 
    	
    	// load in all of the contracts we'll need, 1000 at a time
    	List<SegmentBase> segmentBaseRecords = new ArrayList<SegmentBase>();
    	List<String> contractNumberParamBuffer = new LinkedList<String>();
    	Iterator<String> contractNumberIt = refContractNumbers.iterator();
    	while(contractNumberIt.hasNext()) {
    		while(contractNumberIt.hasNext() && contractNumberParamBuffer.size() < 1000) {
    			contractNumberParamBuffer.add(contractNumberIt.next());
    		}
    		
   	 		@SuppressWarnings("unchecked")
 			List<SegmentBase> curRecords = SessionHelper.getSession(SegmentBase.DATABASE)
 				.createCriteria(SegmentBase.class)
 				.add(Restrictions.in("ContractNumber", contractNumberParamBuffer))
 				.add(Restrictions.eq("StagingFK", stagingContext.getStaging().getStagingPK()))
 				.list();
       	 	segmentBaseRecords.addAll(curRecords);
       	 contractNumberParamBuffer.clear();
    	}
    	
    	Map<String, SegmentBase> segmentBaseByContractNumber = new HashMap<String, SegmentBase>();
    	for(SegmentBase sb : segmentBaseRecords) {
    		segmentBaseByContractNumber.put(sb.getContractNumber(), sb);
    	}
    	
        for (int i = 0; i < commissionHistories.size(); i++)
        {   
            CommissionHistory commissionHistory = commissionHistories.get(i);

            EDITTrxHistory editTrxHistory = commissionHistory.getEDITTrxHistory();
            EDITTrx editTrx = editTrxHistory.getEDITTrx();

            if (!editTrx.getTransactionTypeCT().equalsIgnoreCase("CK") && !editTrx.getTransactionTypeCT().equalsIgnoreCase("CA"))
            {
                ClientSetup clientSetup = editTrx.getClientSetup();
                ContractSetup contractSetup = (ContractSetup) clientSetup.getContractSetup();
                Segment segment = contractSetup.getSegment();
                if (segment != null)
                {
                    if (segment.getContractGroupFK() > 0)
                    {
                        ContractGroup groupContractGroup = segment.getContractGroup();
                        ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

                        Case stagingCase = stagingCasesByContractGroup.get(caseContractGroup.getContractGroupNumber());
                        if(stagingCase == null) {
                        	stagingCase = Case.findByStagingFK_CaseNumber(stagingContext.getStaging().getStagingPK(), 
        							caseContractGroup.getContractGroupNumber(), DATABASE); 
                        	stagingCasesByContractGroup.put(caseContractGroup.getContractGroupNumber(), stagingCase);
                        }
                        
                        if (stagingCase != null)
                        {
                            stagingContext.setCurrentCase(stagingCase);
                        }
                        else
                        {
                            stagingContext.setContractGroupType("Case");
                        	caseContractGroup.stage(stagingContext, DATABASE);
                        	stagingCasesByContractGroup.put(caseContractGroup.getContractGroupNumber(), stagingContext.getCurrentCase());
                        }
                        
                        String stagingGroupKey = groupContractGroup.getContractGroupNumber() + stagingContext.getCurrentCase().getCasePK();
                        Group stagingGroup = casePkAndGroupNumberToGroup.get(stagingGroupKey);
                        if(stagingGroup == null) {
                        	stagingGroup = Group.findByCaseFK_ContractGroupNumber(
                        		stagingContext.getCurrentCase().getCasePK(), groupContractGroup.getContractGroupNumber(), DATABASE);
                        	casePkAndGroupNumberToGroup.put(stagingGroupKey, stagingGroup);
                        }
                        if (stagingGroup != null)
                        {
                            stagingContext.setCurrentGroup(stagingGroup);
                        }
                        else
                        {
                            stagingContext.setContractGroupType("Group");
                        	groupContractGroup.stage(stagingContext, DATABASE);
                        	casePkAndGroupNumberToGroup.put(stagingGroupKey, stagingContext.getCurrentGroup());
                        }

                        SegmentBase segmentBase = segmentBaseByContractNumber.get(segment.getContractNumber());
                        if (segmentBase != null)
                        {
                            stagingContext.setCurrentSegmentBase(segmentBase);
                        }
                        else
                        {
                            stagingContext.setSegmentType("Base");
                            stagingContext = segment.stage(stagingContext, DATABASE);
                            segmentBaseByContractNumber.put(segment.getContractNumber(), stagingContext.getCurrentSegmentBase());

                            Set<contract.ContractClient> contractClients = segment.getContractClients();
                            for (contract.ContractClient contractClient : contractClients)
                            {
                                contractClient.stage(stagingContext, DATABASE);
                            }

                            Segment[] riderSegments = segment.getRiders();
                            if (riderSegments != null)
                            {
                                for (Segment riderSegment : riderSegments)
                                {
                                    stagingContext.setSegmentType("Rider");
                                    stagingContext = riderSegment.stage(stagingContext, DATABASE);

                                    contractClients = riderSegment.getContractClients();
                                    for (contract.ContractClient contractClient : contractClients)
                                    {
                                        contractClient.stage(stagingContext, DATABASE);
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    SegmentBase segmentBase = new SegmentBase();
                    segmentBase.setStaging(stagingContext.getStaging());

                    stagingContext.getStaging().addSegmentBase(segmentBase);

                    stagingContext.setCurrentSegmentBase(segmentBase);

                    SessionHelper.saveOrUpdate(segmentBase, SessionHelper.STAGING);
                }

                AgentSnapshot snapshot = commissionHistory.getAgentSnapshot();
                if (snapshot != null) {
                	Set<AgentHierarchyAllocation> allAgtHierAlloc = snapshot.getAgentHierarchy().getAgentHierarchyAllocations();
                    AgentHierarchyAllocation agtHierAlloc = null;
                    // assuming just one active allocation
                    for(AgentHierarchyAllocation alloc : allAgtHierAlloc) {
                    	if(alloc.getStartDate().beforeOREqual(stagingDate.getEDITDate()) && 
                    			stagingDate.getEDITDate().beforeOREqual(alloc.getStopDate())) {
                    		agtHierAlloc = alloc;
                    	}
                    }
                	
            		stagingContext.setCurrentAgentHierarchyAllocation(agtHierAlloc);
                } else {
                	stagingContext.setCurrentAgentHierarchyAllocation(null);
                }

                stagingContext.setCurrentAgentSnapshot(snapshot);
				if (segment != null) {
					Set<AgentHierarchy> agentHierarchies = segment.getAgentHierarchies();
					for (AgentHierarchy agentHierarchy : agentHierarchies) {
						Set<AgentSnapshot> agentSnapshots = agentHierarchy.getAgentSnapshots();
                    	// writing agent snapshot is highest in the hierarchy
                    	AgentSnapshot writingAgentSnapshot = Collections.max(agentSnapshots, new Comparator<AgentSnapshot>() {
							@Override
							public int compare(AgentSnapshot o1, AgentSnapshot o2) {
								return new Integer(o1.getHierarchyLevel()).compareTo(new Integer(o2.getHierarchyLevel()));
							}
						});
						stagingContext.setCurrentAgentHierarchy(agentHierarchy);

						stagingContext.getCurrentAgent()
								.setServicingAgentIndicator(writingAgentSnapshot.getServicingAgentIndicator());
						SessionHelper.saveOrUpdate(stagingContext.getCurrentAgent(), "STAGING");
					}
				}
            }

            EDITTrx editTrxToStage = new EDITTrx(editTrx.getAsVO());
            editTrx.stage(stagingContext, DATABASE);

            stagingContext.setForceoutMinBalInd(editTrx.getForceoutMinBalInd());

            CommissionHistory commissionHistoryToStage = new CommissionHistory((CommissionHistoryVO) commissionHistory.getVO());
            commissionHistory.stage(stagingContext, DATABASE);
        }
        SessionHelper.commitTransaction(DATABASE);
    	SessionHelper.flushSessions();
        stagingContext = null;
        
        SessionHelper.beginTransaction(DATABASE);
        stagingContext = getStagingContext();
        stagingContext.setStaging(Staging.findStagingByDate_EventType(stagingDate, eventType));
        stagingContext.incrementRecordCount(1);

        stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

        SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

        SessionHelper.commitTransaction(DATABASE);
        SessionHelper.clearSession(DATABASE);
    }
    
    /**
     * Updates year-to-date values in the Staging database based on the commission history
     * records related to the supplied agent and contract.
     * @param placedAgent the agent to locate and stage commission history records for
     * @param contract
     * @throws Exception
     */
    public void stageYearToDate(PlacedAgent placedAgent, AgentContract contract) throws Exception {
    	
    	SessionHelper.beginTransaction(DATABASE);
    	
    	StagingContext stagingContext = this.getStagingContext();
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
    	
        agent.Agent agentAgent = agent.Agent.findBy_PK(contract.getAgentFK());

        ClientRole clientRole = ClientRole.findByPK(placedAgent.getClientRoleFK());
        String currentAgentId = clientRole.getReferenceID();
        stagingContext.setCurrentAgentNumber(currentAgentId);

        Agent agent = Agent.findByStagingFK_AgentNumber(staging.getStagingPK(), stagingContext.getCurrentAgentNumber(), DATABASE);
        if (agent != null)
        {
            stagingContext.setCurrentAgent(agent);
            ytdGroupFYEarn = stagingContext.getCurrentAgent().getYTDGroupFYEarn();
            ytdGroupRnwlEarn = stagingContext.getCurrentAgent().getYTDGroupRnwlEarn();
            ytdGrpAdvancesOnly = stagingContext.getCurrentAgent().getYTDGrpAdvancesOnly();
            ytdGrpChargebacks = stagingContext.getCurrentAgent().getYTDGrpChargeback();
            ytdGrpRecoveries = stagingContext.getCurrentAgent().getYTDGrpRecoveries();
            ytdGrpAdvances = stagingContext.getCurrentAgent().getYTDGrpAdvances();
            ytdIndFYEarn = stagingContext.getCurrentAgent().getYTDIndFYEarn();
            ytdIndRnwlEarn = stagingContext.getCurrentAgent().getYTDIndRnwlEarn();
            ytdIndAdvancesOnly = stagingContext.getCurrentAgent().getYTDIndAdvancesOnly();
            ytdIndChargebacks = stagingContext.getCurrentAgent().getYTDIndChargeback();
            ytdIndRecoveries = stagingContext.getCurrentAgent().getYTDIndRecoveries();
            ytdIndAdvances = stagingContext.getCurrentAgent().getYTDIndAdvances();
        } else
        {
        	// don't stage an agent just because they have YTD values
        	agent = new Agent();
            //agentAgent.stage(stagingContext, DATABASE);
        }
        
    	EDITDate currentDate = new EDITDate();
        CommissionHistoryVO[] commissionHistoryVOs = getCommissionHistoriesForYTDBalances(currentDate.getStartOfYearDate(), currentDate, placedAgent.getPlacedAgentPK());
        if(commissionHistoryVOs.length == 0) {
        	SessionHelper.rollbackTransaction(DATABASE);
        } else {
            stagingContext.setCurrentAgent(populateAgentEarnings("YTD", commissionHistoryVOs, agent));
            SessionHelper.commitTransaction(DATABASE);	
        }
        SessionHelper.clearSession(DATABASE);
    }

    private CommissionHistoryVO[] getCommissionHistoriesForCurrBalances(EDITDateTime fromEDITDateTime, Long placedAgentPK) throws Exception
    {
        Event eventComponent = new EventComponent();

        List voInclusionList = new ArrayList();

        List commissionHistoryVOs = new ArrayList();

        String[] statuses = new String[2];
        statuses[0] = "B";
        statuses[1] = "H";

        long[] placedAgentPKs = new long[] {placedAgentPK.longValue()};

        CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(fromEDITDateTime.toString(), placedAgentPKs, statuses, voInclusionList);

        if (commissionHistoryVO != null)
        {
            commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
        }

        return (CommissionHistoryVO[]) commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]);
    }

    private CommissionHistoryVO[] getCommissionHistoriesForYTDBalances(EDITDate fromDate, EDITDate toDate, Long placedAgentPK) throws Exception
    {
        Event eventComponent = new EventComponent();

        List voInclusionList = new ArrayList();

        List commissionHistoryVOs = new ArrayList();

        String[] statuses = new String[2];
        statuses[0] = "B";
        statuses[1] = "H";

        long[] placedAgentPKs = new long[] {placedAgentPK.longValue()};

        CommissionHistoryVO[] commissionHistoryVO = eventComponent.composeCommissionHistoryVOByUpdateDateTimeGTELTEPlacedAgentPK(placedAgentPKs, toDate.toString(), fromDate.toString(), voInclusionList);

        if (commissionHistoryVO != null)
        {
            commissionHistoryVOs.addAll(Arrays.asList(commissionHistoryVO));
        }

        return (CommissionHistoryVO[]) commissionHistoryVOs.toArray(new CommissionHistoryVO[commissionHistoryVOs.size()]);
    }

    private Agent populateAgentEarnings(String commHistoryType, CommissionHistoryVO[] commissionHistoryVOs, Agent agent)
    {

        String commissionTypeCT = "";
        String groupTypeCT = "";
        for (int i = 0; i < commissionHistoryVOs.length; i++)
        {
            commissionTypeCT = commissionHistoryVOs[i].getCommissionTypeCT();
            groupTypeCT = Util.initString(commissionHistoryVOs[i].getGroupTypeCT(), "");

            if (commHistoryType.equalsIgnoreCase("Curr"))
            {
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_COMM_ADJ))
                {
                    manualAdjustments = manualAdjustments.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                }
            }

            if (groupTypeCT.equalsIgnoreCase(CommissionHistory.GROUPTYPECT_GROUP))
            {
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        groupFYEarn = groupFYEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGroupFYEarn = ytdGroupFYEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN) ||
                         commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        groupFYEarn = groupFYEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGroupFYEarn = ytdGroupFYEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RENEWAL))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        groupRnwlEarn = groupRnwlEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGroupRnwlEarn = ytdGroupRnwlEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RNWL_NEG_EARN))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        groupRnwlEarn = groupRnwlEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGroupRnwlEarn = ytdGroupRnwlEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_ADJUSTMENT) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        grpAdvances = grpAdvances.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGrpAdvances = ytdGrpAdvances.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                        ytdGrpAdvancesOnly = ytdGrpAdvancesOnly.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        grpAdvances = grpAdvances.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGrpAdvances = ytdGrpAdvances.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                        ytdGrpAdvancesOnly = ytdGrpAdvancesOnly.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK)||
                         commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        grpChargebacks = grpChargebacks.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGrpChargebacks = ytdGrpChargebacks.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK_REVERSAL) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        grpChargebacks = grpChargebacks.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGrpChargebacks = ytdGrpChargebacks.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        grpRecoveries = grpRecoveries.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGrpRecoveries = ytdGrpRecoveries.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        grpRecoveries = grpRecoveries.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdGrpRecoveries = ytdGrpRecoveries.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
            }
            if (groupTypeCT.equalsIgnoreCase(CommissionHistory.GROUPTYPECT_INDIVIDUAL))
            {
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indFYEarn = indFYEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndFYEarn = ytdIndFYEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN) ||
                         commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indFYEarn = indFYEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndFYEarn = ytdIndFYEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RENEWAL))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indRnwlEarn = indRnwlEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndRnwlEarn = ytdIndRnwlEarn.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RNWL_NEG_EARN))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indRnwlEarn = indRnwlEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndRnwlEarn = ytdIndRnwlEarn.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_ADJUSTMENT) ||
                    commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indAdvances = indAdvances.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                        indAdvancesOnly = indAdvancesOnly.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndAdvances = ytdIndAdvances.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                        ytdIndAdvancesOnly = ytdIndAdvancesOnly.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indAdvances = indAdvances.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                        indAdvancesOnly = indAdvancesOnly.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndAdvances = ytdIndAdvances.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                        ytdIndAdvancesOnly = ytdIndAdvancesOnly.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
                         commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indChargebacks = indChargebacks.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndChargebacks = ytdIndChargebacks.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK_REVERSAL) ||
                         commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indChargebacks = indChargebacks.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndChargebacks = ytdIndChargebacks.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_ADVANCE_RECOVERY))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indRecoveries = indRecoveries.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndRecoveries = ytdIndRecoveries.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (commissionTypeCT.equalsIgnoreCase(CommissionHistory.COMMISSIONTYPECT_RECOVERY_CHARGEBACK))
                {
                    if (commHistoryType.equalsIgnoreCase("Curr"))
                    {
                        indRecoveries = indRecoveries.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                    else
                    {
                        ytdIndRecoveries = ytdIndRecoveries.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
            }
        }

//        if (commissionHistoryVOs.length > 0)
//        {
            if (commHistoryType.equalsIgnoreCase("Curr"))
            {
                agent.setGroupFYEarn(groupFYEarn);
                agent.setGroupRnwlEarn(groupRnwlEarn);
                agent.setGrpAdvances(grpAdvances.subtractEditBigDecimal(grpChargebacks).subtractEditBigDecimal(grpRecoveries));

                agent.setIndFYEarn(indFYEarn);
                agent.setIndRnwlEarn(indRnwlEarn);
                agent.setIndAdvances(indAdvances.subtractEditBigDecimal(indChargebacks).subtractEditBigDecimal(indRecoveries));

                agent.setManualAdjustments(agent.getManualAdjustments().addEditBigDecimal(manualAdjustments));
                
                agent.setYTDGroupFYEarn(ytdGroupFYEarn);
                agent.setYTDGroupRnwlEarn(ytdGroupRnwlEarn);
                agent.setYTDGrpAdvancesOnly(ytdGrpAdvancesOnly);
                agent.setYTDGrpChargeback(ytdGrpChargebacks);
                agent.setYTDGrpRecoveries(ytdGrpRecoveries);
                agent.setYTDGrpAdvances(ytdGrpAdvances);

                agent.setYTDIndFYEarn(ytdIndFYEarn);
                agent.setYTDIndRnwlEarn(ytdIndRnwlEarn);
                agent.setYTDIndAdvancesOnly(ytdIndAdvancesOnly);
                agent.setYTDIndChargeback(ytdIndChargebacks);
                agent.setYTDIndRecoveries(ytdIndRecoveries);
                agent.setYTDIndAdvances(ytdIndAdvances);
            }
            else
            {
                agent.setYTDGroupFYEarn(ytdGroupFYEarn);
                agent.setYTDGroupRnwlEarn(ytdGroupRnwlEarn);
                agent.setYTDGrpAdvancesOnly(ytdGrpAdvancesOnly);
                agent.setYTDGrpChargeback(ytdGrpChargebacks);
                agent.setYTDGrpRecoveries(ytdGrpRecoveries);
                agent.setYTDGrpAdvances(ytdGrpAdvances);

                agent.setYTDIndFYEarn(ytdIndFYEarn);
                agent.setYTDIndRnwlEarn(ytdIndRnwlEarn);
                agent.setYTDIndAdvancesOnly(ytdIndAdvancesOnly);
                agent.setYTDIndChargeback(ytdIndChargebacks);
                agent.setYTDIndRecoveries(ytdIndRecoveries);
                agent.setYTDIndAdvances(ytdIndAdvances);
            }
//        }

        return agent;
    }
}
