/*
 * User: gfrosti
 * Date: Nov 26, 2003
 * Time: 3:09:13 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import client.ClientDetail;
import client.dm.composer.*;

import contract.dm.composer.VOComposer;
import contract.*;

import edit.common.*;

import edit.common.vo.*;

import event.dm.composer.*;
import event.*;
import role.*;

import role.dm.composer.*;

import java.util.*;




public class AgentStatementLineImpl
{
    public void generateStatementLine(AgentStatementLine agentStatementLine, PlacedAgent placedAgent, CommissionHistory commissionHistory, String transactionTypeCT) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(AgentHierarchyVO.class);

        AgentStatementLineVO agentStatementLineVO = agentStatementLine.getVO();

        PlacedAgent writingAgent = placedAgent;

        if (commissionHistory.getSourcePlacedAgent() != null)
        {
            writingAgent = commissionHistory.getSourcePlacedAgent();
        }

        AgentVO agentVO = (AgentVO) writingAgent.getAgentContract().getAgent().getVO();

        //eliminated going to the component with change of database
        //        CommissionHistoryVO composedCommissionHistoryVO = null;
        //new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO);

        //        composedCommissionHistoryVO = commissionHistoryVO;
        EDITTrxHistory editTrxHistory = commissionHistory.getEDITTrxHistory();

        // Get all useful VOs
        FinancialHistory financialHistory = null;

        if (editTrxHistory.getFinancialHistories().size() > 0)
        {
            financialHistory = editTrxHistory.getFinancialHistories().iterator().next(); // Should be one and only one.
        }

        EDITTrx editTrx = editTrxHistory.getEDITTrx();

        ClientSetup clientSetup = editTrx.getClientSetup();

        ContractSetup contractSetup = (ContractSetup) clientSetup.getContractSetup();

        String contractNumber = null;

        String policyOwnerName = null;

        String businessContractName = null;

        String marketingPackageName = null;

        EDITBigDecimal allocationPercent = new EDITBigDecimal();

        if (!transactionTypeCT.equalsIgnoreCase("CA"))
        {
            Segment segment = contractSetup.getSegment();

            Set<AgentHierarchy> agentHierarchies = segment.getAgentHierarchies();

            for (AgentHierarchy agentHierarcy : agentHierarchies)
            {
                if (agentHierarcy.getAgentFK() == agentVO.getAgentPK())
                {
                	// get active hierarchy allocation
                    AgentHierarchyAllocation agentHierarchyAllocation = null;
                    for(AgentHierarchyAllocation curAlloc : agentHierarcy.getAgentHierarchyAllocations()) {
                    	if(curAlloc.getStartDate().beforeOREqual(editTrx.getEffectiveDate()) && 
                    			curAlloc.getStopDate().afterOREqual(editTrx.getEffectiveDate())) {
                    		agentHierarchyAllocation = curAlloc;
                    	}
                    }

                    if (agentHierarchyAllocation != null)
                    {
                        allocationPercent = agentHierarchyAllocation.getAllocationPercent();
                        break;
                    }
                }
            }
 
            // Need the Contract (for Contract Number)
            contractNumber = segment.getContractNumber();

            // Need the owner (for Owner Name)
            policyOwnerName = getPolicyOwnerName(segment.getContractClients("OWN"));

            ProductStructureVO productStructureVO = null;

            if (segment != null)
            {
                engine.business.Lookup engineLookup = new engine.component.LookupComponent();

                // TODO: we should join against product structure up front
                productStructureVO = engineLookup.findProductStructureVOByPK(segment.getProductStructureFK(), false, null)[0];

                // Need the ProductStructure (For Business Contract)
                businessContractName = getBusinessContractName(productStructureVO);

                marketingPackageName = getMarketingPackageName(productStructureVO);
            }
        }

        ClientDetail agentClientDetail = writingAgent.getClientRole().getClientDetail();

        String agentName = getAgentName(agentClientDetail);

        // Set necessary value
        agentStatementLineVO.setAgentNumber(agentVO.getAgentNumber());
        agentStatementLineVO.setProductType(businessContractName);
        agentStatementLineVO.setOwnerName(policyOwnerName);
        agentStatementLineVO.setPolicyNumber(contractNumber);
        agentStatementLineVO.setMarketingPackageName(marketingPackageName);

        if (financialHistory != null)
        {
            agentStatementLineVO.setInitialPremium(financialHistory.getGrossAmount().getBigDecimal());
            agentStatementLineVO.setCommissionPremium(financialHistory.getCommissionableAmount().getBigDecimal());
        }

        agentStatementLineVO.setCommissionRate(commissionHistory.getCommissionRate().getBigDecimal());
        agentStatementLineVO.setType(commissionHistory.getCommissionTypeCT());

        agentStatementLineVO.setAmountDue(getCommissionAmount(commissionHistory).getBigDecimal());
        agentStatementLineVO.setName(agentName);
        agentStatementLineVO.setFirstName(agentClientDetail.getFirstName());
        agentStatementLineVO.setEffectiveDate(editTrx.getEffectiveDate().getFormattedDate());

        EDITDateTime processDateTime = new EDITDateTime(editTrxHistory.getProcessDateTime());
        agentStatementLineVO.setProcessDate(processDateTime.getEDITDate().getFormattedDate());
        agentStatementLineVO.setAllocationPercent(allocationPercent.getBigDecimal());

        if (editTrx.getTransactionTypeCT().equals(EDITTrx.TRANSACTIONTYPECT_COMMISSIONADJUSTMENT))
        {
            long checkAdjustmentFK = editTrx.getCheckAdjustmentFK();
            if (checkAdjustmentFK != 0)
            {
                CheckAdjustment checkAdjustment = CheckAdjustment.findByPK(new Long(checkAdjustmentFK));
                agentStatementLineVO.setDescription(checkAdjustment.getDescription());
            }
        }

        agentStatementLineVO.setTransactionType(editTrx.getTransactionTypeCT());

        agentStatementLineVO.setDebitBalanceRepayment(commissionHistory.getDebitBalanceAmount().getBigDecimal());

        getReportToInfo(placedAgent, writingAgent, agentStatementLineVO);

        agentStatementLineVO.setUpdateStatus(commissionHistory.getUpdateStatus());

        if(commissionHistory.getCommHoldReleaseDate() != null) {
        	agentStatementLineVO.setCommHoldReleaseDate(commissionHistory.getCommHoldReleaseDate().getFormattedDate());
        }
    }

    private String getAgentName(ClientDetail clientDetail)
    {
        String agentName = null;

        if (clientDetail.getTrustTypeCT().equals("CorporateTrust") || clientDetail.getTrustTypeCT().equals("Corporate"))
        {
            agentName = clientDetail.getCorporateName();
        }
        else
        {
            agentName = clientDetail.getLastName();
        }

        return agentName;
    }

    private EDITBigDecimal getCommissionAmount(CommissionHistory commissionHistory) throws Exception
    {
        String commissionTypeCT = commissionHistory.getCommissionTypeCT();

        /* double commissionAmount = commissionHistoryVO.getCommissionAmount();

        if (commissionTypeCT.equals("ChargeBack"))
        {
            commissionAmount *= -1;
        }  */

        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/23/2004
        // sprasad 09/23/2004 double to BigDecimal conversion
        EDITBigDecimal commissionAmount = commissionHistory.getCommissionAmount();

        if (commissionTypeCT.equals("ChargeBack"))
        {
            commissionAmount.negate();
        }

        return commissionAmount;
    }

    private String getContractNumber(long segmentPK) throws Exception
    {
        String contractNumber = null;

        if (segmentPK != 0)
        {
            SegmentVO segmentVO = new VOComposer().composeSegmentVO(segmentPK, new ArrayList());

            contractNumber = segmentVO.getContractNumber();
        }
        else
        {
            contractNumber = "NA";
        }

        return contractNumber;
    }

    private String getPolicyOwnerName(ContractClient[] clients) throws Exception
    {
        for (ContractClient client : clients)
        {
            if (client.getClientRole().getRoleTypeCT().equals("OWN") && 
            		client.getOverrideStatus().equals("P") && 
            		client.getTerminationDate().after(new EDITDate()))
            {
                if (client.getClientDetail().getCorporateName() != null && 
                		client.getClientDetail().getCorporateName().length() > 0)
                {
                    return client.getClientDetail().getCorporateName();
                }
                else
                {
                    return client.getClientDetail().getLastName();
                }
            }
        }
        
        return "NA";
    }

    private String getBusinessContractName(ProductStructureVO productStructureVO) throws Exception
    {
        String businessContractName = null;

        if (productStructureVO != null)
        {
            businessContractName = productStructureVO.getBusinessContractName();
        }
        else
        {
            businessContractName = "NA";
        }

        return businessContractName;
    }

    private String getMarketingPackageName(ProductStructureVO productStructureVO)
    {
        String marketingPackageName = null;

        if (productStructureVO != null)
        {
            marketingPackageName = productStructureVO.getMarketingPackageName();
        }
        else
        {
            marketingPackageName = "NA";
        }

        return marketingPackageName;
    }

    private ClientDetailVO getAgent(PlacedAgent targetPlacedAgent, CommissionHistoryVO commissionHistoryVO) throws Exception
    {
        ClientRole agentClientRole = ClientRole.findByPK(targetPlacedAgent.getClientRoleFK());

        ClientRoleVO agentClientRoleVO = (ClientRoleVO) agentClientRole.getVO();

        ClientDetailVO agentClientDetailVO = new ClientDetailComposer(new ArrayList()).compose(agentClientRoleVO.getClientDetailFK());

        return agentClientDetailVO;
    }

    /**
     * If the currentPlacedAgent is the same as the writingAgent, then there is no Report-To since we are dealing with the 
     * Writing Agent himself. Otherwise, the Report-To is actually a misnomer. We are [actually] looking for the Agent 
     * who "Reports-To" the current currentPlacedAgent.
     * @param originalPlacedaAgent
     */
    private void getReportToInfo(PlacedAgent currentPlacedAgent, PlacedAgent writingAgent, AgentStatementLineVO agentStatementLineVO) throws Exception
    {
        String reportToNumber = null;
        
        String reportToName = null;
        
        // If currentPlacedAgent is the same as writingAgent, then there is no report-to to worry about.
        if (!currentPlacedAgent.equals(writingAgent))
        {
            PlacedAgentBranch placedAgentBranch = new PlacedAgentBranch(writingAgent);
            
            int currentPlacedAgentHierarchyLevel = currentPlacedAgent.getHierarchyLevel();
            
            PlacedAgent reportToPlacedAgent = null;
            
            PlacedAgent[] branchPlacedAgents = placedAgentBranch.getPlacedAgents();
            
            for (int i = 0; i < branchPlacedAgents.length; i++)
            {
                // Find the PlacedAgent reporting-to (hierarchy level + 1) of the current Placed Agent.
                if (currentPlacedAgentHierarchyLevel == (branchPlacedAgents[i].getHierarchyLevel() - 1))
                {
                    reportToPlacedAgent = branchPlacedAgents[i];
                    
                    break;
                }
            }
            
            Agent agent = reportToPlacedAgent.getAgentContract().getAgent();

            reportToNumber = agent.getAgentNumber();
            
            ClientDetail clientDetail = reportToPlacedAgent.getClientRole().getClientDetail();
            
            reportToName = getAgentName(clientDetail);
        }
        
        agentStatementLineVO.setReportToNumber(reportToNumber);
        
        agentStatementLineVO.setReportToName(reportToName);
    }
}
