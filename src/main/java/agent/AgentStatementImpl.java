/*
 * User: gfrosti
 * Date: Nov 26, 2003
 * Time: 2:48:51 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.*;
import edit.common.vo.*;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import edit.services.EditServiceLocator;
import event.CommissionHistory;
import event.EDITTrx;
import event.dm.composer.*;
import role.*;
import java.util.*;

import org.hibernate.Criteria;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import staging.CommissionStaging;
import logging.Log;
import logging.LogEvent;
import batch.business.Batch;
import fission.utility.XMLUtil;
import fission.utility.Util;



public class AgentStatementImpl
{		
	protected boolean generateStatement(AgentStatement agentStatement,
			AgentContract agentContract,
			String outputFileType,
			EDITDate processDate,
			EDITDateTime stagingDate,
			File exportFile,
			Set<Long> updatedFinancialRolePks,
			HashMap<String, CommissionStaging> stagingInstances)
	{
		String agentNumber = null;
		String agentName = null;
		
		boolean agentStatementGenerated = false;

		try {

			for(ClientRole clientRole : ClientRole.findBy_AgentContract_PlacedAgent(agentContract)) {	
				Set<PlacedAgent> placedAgents = clientRole.filterPlacedAgentsByAgentContract(agentContract);
				// Set StatementDate
				agentStatement.getVO().setStatementDate(new EDITDate().getFormattedDate());

				List<CommissionHistory> allCommissionHistoryRecords = new ArrayList<CommissionHistory>();
				EDITBigDecimal agentLastStatementAmount = new EDITBigDecimal();
				String agentLastStatementDate = "";

				ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();
				clientRoleFinancial.setPriorLastStatementDateTime(clientRoleFinancial.getLastStatementDateTime());
				clientRoleFinancial.save();
				
				boolean shouldStageCommission = false;
				for (Iterator<PlacedAgent> iterator = placedAgents.iterator(); iterator.hasNext();) {

					shouldStageCommission = false;
					PlacedAgent placedAgent = iterator.next();
					
					// For error logging:
					agentNumber = placedAgent.getClientRole().getReferenceID();
					agentName = placedAgent.get_AgentContract().get_Agent().getAgentName();
					
					// Generate AgentStatementLines, and return the CommissionHistoryVOs used.
					long startTime = System.currentTimeMillis();
					CommissionHistory[] currentCommissionHistories = generateStatementLines(placedAgent, agentStatement);
					long endTime = System.currentTimeMillis();
					//System.out.println((endTime - startTime) + "ms to retrieve " + currentCommissionHistories.length + " commission history VOs for placed agent " + placedAgent.getPK());

					allCommissionHistoryRecords.addAll(Arrays.asList(currentCommissionHistories));

					for (Iterator<CommissionHistory> chIterator = allCommissionHistoryRecords.iterator(); chIterator.hasNext();) {
						CommissionHistory commissionHistory = chIterator.next();
						if (commissionHistory.getPlacedAgentFK() == placedAgent.getPlacedAgentPK().longValue()) {
							shouldStageCommission = true;
							break;
						}
					}

					if (outputFileType.equalsIgnoreCase("Staging")) {
						
						startTime = System.currentTimeMillis();
						// cache and re-use CommissionStaging since some YTD values may exist from previous contracts
						CommissionStaging commissionStaging = null;
						if(!stagingInstances.containsKey(clientRole.getReferenceID())) {
							commissionStaging = new CommissionStaging(stagingDate);
							stagingInstances.put(clientRole.getReferenceID(), commissionStaging);
						} else {
							commissionStaging = stagingInstances.get(clientRole.getReferenceID());
						}
						 
						if(shouldStageCommission) {
							commissionStaging.stageTables(Arrays.asList(currentCommissionHistories), agentContract, placedAgent);
						}
						endTime = System.currentTimeMillis();
						//System.out.println((endTime - startTime) + " ms to stage " + currentCommissionHistories.length + " commission history VOs for placed agent " + placedAgent.getPK());

						startTime = System.currentTimeMillis();
						// always try to stage YTD info
						commissionStaging.stageYearToDate(placedAgent, agentContract);
						endTime = System.currentTimeMillis();
						//System.out.println((endTime - startTime) + " ms to stage " + currentCommissionHistories.length + " YTD VOs for placed agent " + placedAgent.getPK());
					}
				}

				updatedFinancialRolePks.add(clientRoleFinancial.getPK());

				clientRoleFinancial.setStatementProducedInd("Y");
				clientRoleFinancial.setLastStatementAmount(clientRoleFinancial.getCommBalance());

				EDITBigDecimal lastStatementAmount = clientRoleFinancial.getLastStatementAmount();
				agentLastStatementAmount = agentLastStatementAmount.addEditBigDecimal(lastStatementAmount);

				String lastStatementDate = clientRoleFinancial.getLastStatementDateTime().toString();
				agentLastStatementDate = lastStatementDate;

				clientRoleFinancial.save();

				PlacedAgent currentActivePlacedAgent = getCurrentActivePlacedAgent(placedAgents);

				AgentEarnings agentEarnings = null;

				if (!allCommissionHistoryRecords.isEmpty()) {
					// AgentEarnings required generated from above CommissionHistoryVOs (even if there aren't any)
					agentEarnings = new AgentEarnings();
					agentEarnings.generateEarnings(agentContract, (CommissionHistory[]) allCommissionHistoryRecords.toArray(new CommissionHistory[allCommissionHistoryRecords.size()]));
					agentStatement.setAgentEarnings(agentEarnings);
					agentStatement.setLastStatementAmount(agentLastStatementAmount);
					agentStatement.setLastStatementDate(agentLastStatementDate);

					// AgentInfo required.
					AgentInfo agentInfo = new AgentInfo();
					agentInfo.generateAgentInfo(currentActivePlacedAgent, new EDITDate().getFormattedDate()); // Any PlacedAgent
					agentStatement.setAgentInfo(agentInfo);

					agentStatementGenerated = true;

					if (agentStatementGenerated) {
						if (outputFileType.equals("XML")) {
							exportAgentStatement(agentStatement, exportFile);
						}

						EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_COMMISSION_STATEMENTS).updateSuccess();
					}
				} else {
					for (Iterator<PlacedAgent> iterator = placedAgents.iterator(); iterator.hasNext(); ) {
						PlacedAgent agent = iterator.next();
						clientRoleFinancial = agent.getClientRole().getClientRoleFinancial();

						//String refID = agent.getClientRole().getReferenceID();

						if (clientRoleFinancial.getCommBalance().isLT("0")) {
							// AgentEarngings is minimal in that we can only get the TaxableIncomeYTD. The other fields are 0.
							agentEarnings = new AgentEarnings();
							agentEarnings.generateEarnings(agentContract);
							agentStatement.setAgentEarnings(agentEarnings);
							agentStatement.setLastStatementAmount(clientRoleFinancial.getLastStatementAmount());
							agentStatement.setLastStatementDate(clientRoleFinancial.getLastStatementDateTime().toString());

							// AgentInfo required.
							AgentInfo agentInfo = new AgentInfo();
							agentInfo.generateAgentInfo(currentActivePlacedAgent, new EDITDate().getFormattedDate()); // Any PlacedAgent
							agentStatement.setAgentInfo(agentInfo);

							clientRoleFinancial.setStatementProducedInd("Y");

							agentStatementGenerated = true;
						} else {
							clientRoleFinancial.setStatementProducedInd("N");

							agentStatementGenerated = false;
						}

						String checkAmountForLastStatementAmount = ServicesConfig.getCheckAmountForLastStatmentAmount();

						if (agentStatementGenerated) {
							if (checkAmountForLastStatementAmount == null || checkAmountForLastStatementAmount.equals("N")
									|| new EDITBigDecimal(((ClientRoleFinancialVO) clientRoleFinancial.getVO()).getCommBalance()).isLT("0")) {
								clientRoleFinancial.setLastStatementAmount(new EDITBigDecimal(((ClientRoleFinancialVO) clientRoleFinancial.getVO()).getCommBalance()));
							} else {
								clientRoleFinancial.setLastStatementAmount(new EDITBigDecimal(((AgentEarningsVO) agentEarnings.getVO()).getNetPolicyEarnings()));
							}
						}

						clientRoleFinancial.save();
					}
				}
			}
		} catch (Exception e) {
			EditServiceLocator.getSingleton().getBatchAgent()
			.getBatchStat(Batch.BATCH_JOB_GENERATE_COMMISSION_STATEMENTS).updateFailure();

			Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
			Log.logGeneralExceptionToDatabase(null, e);
			System.out.println(e);
			e.printStackTrace(); // Don't throw.

			String message = "Failed For agentContractPK [" + agentContract.getAgentContractPK() + "] with error: " + e.getMessage();

			//  Log to database
			EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
			columnInfo.put("AgentNumber", agentNumber);
			columnInfo.put("AgentName", agentName);

			Log.logToDatabase(Log.COMMISSION_STATEMENTS, message, columnInfo);
			
		}
		
		return agentStatementGenerated;
	}

	private PlacedAgent getCurrentActivePlacedAgent(Set<PlacedAgent> placedAgents) {        
		EDITDate currentDate = new EDITDate();

		PlacedAgent currentActivePlacedAgent = null;

		for (Iterator<PlacedAgent> iterator = placedAgents.iterator(); iterator
				.hasNext();) {
			PlacedAgent placedAgent = iterator.next();

			EDITDate startDate = placedAgent.getStartDate();
			EDITDate stopDate = placedAgent.getStopDate();

			if (startDate.beforeOREqual(currentDate)
					&& stopDate.afterOREqual(currentDate)) {
				currentActivePlacedAgent = placedAgent;
				break;
			}
		}

		if (currentActivePlacedAgent == null) {
			EDITDate stopDate = null;

			for (Iterator<PlacedAgent> iterator = placedAgents.iterator(); iterator
					.hasNext();) {
				PlacedAgent placedAgent = iterator.next();

				if (stopDate == null
						|| placedAgent.getStopDate().after(stopDate)) {
					stopDate = placedAgent.getStopDate();
					currentActivePlacedAgent = placedAgent;
				}
			}
		}

		return currentActivePlacedAgent;
	}

	private CommissionHistory[] generateStatementLines(PlacedAgent placedAgent, AgentStatement agentStatement) throws Exception
	{
		List<CommissionHistory> commissionHistoryVOs = new ArrayList<CommissionHistory>();

		@SuppressWarnings("unchecked")
		List<CommissionHistory> commissionHistoryVO = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS)
				.createQuery("select CH from CommissionHistory CH" + 
							 " join fetch CH.PlacedAgent PA " +
							 " left join fetch PA.AgentContract PAC " +
							 " left join fetch PAC.Agent PACA " +
							 " join fetch PA.ClientRole PACR " +
							 " join fetch PACR.ClientRoleFinancials PACRF " +
							 " join fetch CH.EDITTrxHistory as ETH" + 
							 " join fetch ETH.EDITTrx as ET" + 
							 " left join fetch ET.ClientSetup as CliS" + 
							 " left join fetch CliS.ContractSetup as CS" + 
							 " left join fetch CS.Segment as S" +
							 " left join fetch CH.SourcePlacedAgent SPA " +
							 " left join fetch SPA.AgentContract SPAC " +
							 " left join fetch SPAC.Agent SPACA " +
							 " left join fetch S.BatchContractSetup as BCS" +
							 " left join fetch S.DepartmentLocation as DL" +
							 " left join fetch S.ContractGroup as CGroup" +
							 " left join CGroup.FilteredProducts as FilteredProducts" +
							 " left join FilteredProducts.MasterContracts as MContracts" +
							 " left join CGroup.AgentHierarchies as AgHier" +
							 " left join AgHier.AgentHierarchyAllocations as AgHierAlloc" +
							 " left join AgHier.AgentSnapshots as AgSnap" +
							 " left join fetch CGroup.ContractGroup as CGroup2" + 
							 " left join S.ContractClients as Clients" + 
							 " left join Clients.ClientRole as CRole" +
							 " left join Clients.ContractClientAllocations as CAllocs" +
							 " left join CRole.ClientDetail as CDetail" + 
							 " left join CDetail.ClientAddresses as CAddrs" + 
							 " left join S.Segments as RiderS" + 
							 " where CH.PlacedAgentFK = :placedAgentFK and  CH.StatementInd = 'Y' " + 
							 " and CH.UpdateStatus in ('H', 'L') and " +
							 " (CH.UpdateDateTime > PACRF.LastStatementDateTime or PACRF.LastStatementDateTime is null)")
				.setParameter("placedAgentFK", placedAgent.getPlacedAgentPK())
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		
		/*List voInclusionList = new ArrayList();
		voInclusionList.add(EDITTrxHistoryVO.class);
		voInclusionList.add(FinancialHistoryVO.class);
		voInclusionList.add(EDITTrxVO.class);
		voInclusionList.add(ClientSetupVO.class);
		voInclusionList.add(ContractSetupVO.class);
		voInclusionList.add(SegmentVO.class);
		voInclusionList.add(AgentHierarchyVO.class);
		CommissionHistoryVO[] oldRecords = new VOComposer()
				.composeCommissionHistoryVOByUpdateDateTime_AND_PlacedAgentPK_AND_StatementInd(
						placedAgent.getPK(), "Y", voInclusionList);
		if(oldRecords != null)
			System.out.println("Records via old technique: " + oldRecords.length);*/
		
		if (commissionHistoryVO != null) {
			// We will need the entire set of CommissionHistoryVOs for
			// summations below.
			commissionHistoryVOs.addAll(commissionHistoryVO);

			for (int j = 0; j < commissionHistoryVO.size(); j++) {
				EDITTrx editTrx = commissionHistoryVO.get(j).getEDITTrxHistory().getEDITTrx();

				if (!editTrx.getTransactionTypeCT().equalsIgnoreCase("CK")
						&& !editTrx.getTransactionTypeCT().equalsIgnoreCase("BCK")) {
					AgentStatementLine agentStatementLine = new AgentStatementLine();

					agentStatementLine.generateStatementLine(placedAgent,
							commissionHistoryVO.get(j),
							editTrx.getTransactionTypeCT());

					agentStatement.addAgentStatementLine(agentStatementLine);
				}
			}
		}

		return (CommissionHistory[]) commissionHistoryVOs
				.toArray(new CommissionHistory[commissionHistoryVOs.size()]);
	}

	protected void composeAgentStatementVO(AgentStatement agentStatement) throws Exception
	{
		AgentStatementVO agentStatementVO = agentStatement.getVO();

		AgentStatementLine[] agentStatementLines = agentStatement
				.getAgentStatementLine();

		AgentInfo agentInfo = agentStatement.getAgentInfo();

		AgentEarnings agentEarnings = agentStatement.getAgentEarnings();

		// Add the AgentStatementLinesVOs
		for (int i = 0; i < agentStatementLines.length; i++) {
			agentStatementVO.addAgentStatementLineVO(agentStatementLines[i]
					.getVO());
		}

		// Add the AgentInfoVO
		agentStatementVO.addAgentInfoVO(agentInfo.getVO());

		// Add the AgentEarningsVO
		agentStatementVO.addAgentEarningsVO(agentEarnings.getVO());
	}

	private void exportAgentStatement(AgentStatement agentStatement, File exportFile)
			throws Exception
	{
		AgentStatementVO agentStatementVO = agentStatement.composeVO();

		String parsedXML = roundDollarFields(agentStatementVO);

		parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);

		appendToFile(exportFile, parsedXML);
	}

	/**
	 * Rounds the values of the dollar fields to 2 places for currency.
	 * The fields of interest belong to the AgentStatmentLineVO and the AgentEarningsVO.  The incoming AgentStatementVO
	 * is a composition which includes these other 2 VOs.
	 * @param agentStatementVO - composed VO
	 * @return modified AgentStatementVO as a String
	 */
	private String roundDollarFields(AgentStatementVO agentStatementVO)
			throws Exception
	{
		String[] fieldNames = setupFieldNamesForRounding();

		String voToXML = Util.roundDollarTextFields(agentStatementVO, fieldNames);

		return voToXML;
	}

	/**
	 * Sets the fields names that are dollar fields in the AgentStatementLineVO
	 * @return array of strings of field names
	 */
	private String[] setupFieldNamesForRounding()
	{
		List<String> fieldNames = new ArrayList<String>();

		fieldNames.add("AgentStatementLineVO.InitialPremium");
		fieldNames.add("AgentStatementLineVO.CommissionPremium");
		fieldNames.add("AgentStatementLineVO.AmountDue");

		fieldNames.add("AgentEarningsVO.TaxableIncome");
		fieldNames.add("AgentEarningsVO.TaxableIncomeYTD");
		fieldNames.add("AgentEarningsVO.PositivePolicyEarnings");
		fieldNames.add("AgentEarningsVO.NegativePolicyEarnings");
		fieldNames.add("AgentEarningsVO.NetPolicyEarnings");

		return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
	}

	private void appendToFile(File exportFile, String data)
			throws Exception
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));

		bw.write(data);

		bw.flush();

		bw.close();
	}
}
