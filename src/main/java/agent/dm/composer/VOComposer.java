/*
 * User: dlataill
 * Date: Oct 16, 2003
 * Time: 12:54:42 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent.dm.composer;

import agent.*;
import agent.dm.dao.DAOFactory;
import client.dm.composer.ClientDetailComposer;
import contract.AgentSnapshot;
import edit.common.EDITBigDecimal;
import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import event.EDITTrx;

import event.TransactionPriority;

import java.util.ArrayList;
import java.util.List;

import role.ClientRole;

public class VOComposer
{
    public PlacedAgentVO[] composePlacedAgentVOByAgentContractPK(long agentContractPK, List voInclusionList) throws Exception
    {
        PlacedAgentVO[] placedAgentVO = DAOFactory.getPlacedAgentDAO().findByAgentContractPK(agentContractPK);

        if (placedAgentVO != null && !voInclusionList.isEmpty())
        {
            for (int i = 0; i < placedAgentVO.length; i++)
            {
                new PlacedAgentComposer(voInclusionList).compose(placedAgentVO[i]);
            }
        }

        return placedAgentVO;
    }

    public AgentContractVO[] composeAgentContractVOByAgentPK(long agentPK, List voInclusionList) throws Exception
    {
        AgentContractVO[] agentContractVO = DAOFactory.getAgentContractDAO().findByAgentPK(agentPK);

        if (agentContractVO != null && !voInclusionList.isEmpty())
        {
            for (int i = 0; i < agentContractVO.length; i++)
            {
                new AgentContractComposer(voInclusionList).compose(agentContractVO[i]);
            }
        }

        return agentContractVO;
    }

    public AgentContractVO[] composeAgentContractVOByCommissionContractPK(long commissionContractPK, List voInclusionList) throws Exception
    {
        AgentContractVO[] agentContractVO = DAOFactory.getAgentContractDAO().findByCommissionContractPK(commissionContractPK);

        if (agentContractVO != null)
        {
            for (int i = 0; i < agentContractVO.length; i++)
            {
                new AgentContractComposer(voInclusionList).compose(agentContractVO[i]);
            }
        }

        return agentContractVO;
    }

    public AgentVO composeAgentVO(long agentPK, List voInclusionList) throws Exception
    {
        AgentComposer composer = new AgentComposer(voInclusionList);

        AgentVO agentVO = composer.compose(agentPK);

        return agentVO;
    }

    public AgentVO composeAgentVOByAgentNumber(String agentNumber, List voInclusionList)
    {
        AgentComposer composer = new AgentComposer(voInclusionList);

        AgentVO agentVO = composer.compose(agentNumber);

        return agentVO;
    }

    public AgentSnapshotDetailVO[] composeCommissionVO(EDITTrx editTrx, AgentSnapshotVO[] agentSnapshotVO, long segmentFK, List voInclusionList) throws Exception
    {
        List agentSnapshot = new ArrayList();
        AgentComposer agentComposer = new AgentComposer(voInclusionList);
        CommissionProfileComposer commissionProfileComposer = new CommissionProfileComposer(voInclusionList);

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            for (int i = 0; i < agentSnapshotVO.length; i++)
            {
                PlacedAgentVO placedAgentVO = (PlacedAgentVO) crud.retrieveVOFromDB(PlacedAgentVO.class, agentSnapshotVO[i].getPlacedAgentFK());

                if (placedAgentVO == null)
                {
                    throw new Exception("Placed Agent not found for Placed Agent Key:" + (agentSnapshotVO[i].getPlacedAgentFK() + ""));
                }

                AgentVO agentVO = DAOFactory.getAgentDAO().findByPlacedAgentPK(placedAgentVO.getPlacedAgentPK())[0];

                agentComposer.compose(agentVO);

                AdditionalCompensationVO[] additionalCompensationVO = null;
                ClientDetailVO clientDetailVO = null;

                AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();
                agentVO.removeAllAgentContractVO();

                AgentContractVO agentContractVO = null;

                for (int j = 0; j < agentContractVOs.length; j++)
                {
                    if (agentContractVOs[j].getAgentContractPK() == placedAgentVO.getAgentContractFK())
                    {
                        agentContractVO = agentContractVOs[j];
                        break;
                    }
                }

                if (agentContractVO != null)
                {
                    if (agentContractVO.getAdditionalCompensationVOCount() > 0)
                    {
                        additionalCompensationVO = agentContractVO.getAdditionalCompensationVO();
                        agentContractVO.removeAllAdditionalCompensationVO();
                    }
                }

                ClientRoleVO clientRoleVO = (ClientRoleVO) ClientRole.findByPK(placedAgentVO.getClientRoleFK()).getVO();
                clientDetailVO = getComposedClientDetailVO(clientRoleVO);

                // use commission contract to get the profile and level description
                //CommissionProfileVO commissionProfileVO = commissionProfileComposer.compose(agentSnapshotVO[i].getCommissionProfileFK());
                
                CommissionProfileVO commissionProfileVO =  CommissionProfile.findActiveCommissionProfileVO(placedAgentVO.getPlacedAgentPK(), editTrx.getEffectiveDate().getFormattedDate());

                AgentSnapshotDetailVO agentSnapshotDetailVO = new AgentSnapshotDetailVO();

                if (agentVO != null)
                {
                    agentSnapshotDetailVO.addAgentVO(agentVO);
                }

                if (agentContractVO != null)
                {
                    agentSnapshotDetailVO.addAgentContractVO(agentContractVO);
                }

                if (additionalCompensationVO != null)
                {
                    agentSnapshotDetailVO.setAdditionalCompensationVO(additionalCompensationVO);
                }

                agentSnapshotDetailVO.setAgentSnapshotDetailPK(i + 1);
                agentSnapshotDetailVO.setCommissionOverrideAmount(agentSnapshotVO[i].getCommissionOverrideAmount());
                agentSnapshotDetailVO.setCommissionOverridePercent(agentSnapshotVO[i].getCommissionOverridePercent());
                agentSnapshotDetailVO.setPlacedAgentFK(agentSnapshotVO[i].getPlacedAgentFK());
                agentSnapshotDetailVO.setAgentSnapshotPK(agentSnapshotVO[i].getAgentSnapshotPK());
                agentSnapshotDetailVO.setAdvanceAmount(agentSnapshotVO[i].getAdvanceAmount());
                agentSnapshotDetailVO.setAdvanceRecovery(agentSnapshotVO[i].getAdvanceRecovery());
                agentSnapshotDetailVO.setAdvancePercent(agentSnapshotVO[i].getAdvancePercent());
                agentSnapshotDetailVO.setRecoveryPercent(agentSnapshotVO[i].getRecoveryPercent());
                
                EDITBigDecimal totalCommissionsPaid = getTotalCommissionsPaid(agentSnapshotVO[i].getAgentSnapshotPK(), segmentFK);
 
                agentSnapshotDetailVO.setTotalCommissionsPaid(totalCommissionsPaid.getBigDecimal());

                if (commissionProfileVO != null)
                {
                    agentSnapshotDetailVO.addCommissionProfileVO(commissionProfileVO);
                }

                if (clientDetailVO != null)
                {
                    agentSnapshotDetailVO.addClientDetailVO(clientDetailVO);
                }

                agentSnapshot.add(agentSnapshotDetailVO);

            }//inner for loop
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }


        // Commented-out until AgentGroup processing is resolved regarding future of AgentSnapshot.
//        if (editTrx.isCommissionable())
//        {
//            AgentSnapshot writingAgentSnapshot = AgentSnapshot.findBy_PK(new Long(agentSnapshotVO[0].getAgentSnapshotPK()));
//
//            if (writingAgentSnapshot.isParticipatingInAgentGroup())
//            {
//                insertAgentSnapshot(writingAgentSnapshot, agentSnapshot);
//            }
//        }

        return (AgentSnapshotDetailVO[]) agentSnapshot.toArray(new AgentSnapshotDetailVO[agentSnapshot.size()]);
    }

    /**
     * Builds a ClientDetail composed of ClientAddress, Preference, BankAccountInformation, TaxInformation, and TaxProfile.
     * @param clientRoleVO
     * @return a composed ClientDetailVO
     */
    private ClientDetailVO getComposedClientDetailVO(ClientRoleVO clientRoleVO)
    {
        List clientVOInclusionList = new ArrayList();

        clientVOInclusionList.add(ClientAddressVO.class);
        clientVOInclusionList.add(PreferenceVO.class);
        clientVOInclusionList.add(TaxInformationVO.class);
        clientVOInclusionList.add(TaxProfileVO.class);

        ClientDetailVO clientDetailVO = new ClientDetailComposer(clientVOInclusionList).compose(clientRoleVO.getClientDetailFK());

        return clientDetailVO;
    }

    /**
     * When a writing agent AgentSnapshot is associated with an AgentGroup, an AgentSnapshot that represents the
     * AgentGroup is to be inserted immediately above the writing agent in the list of AgentSnapshots.
     * @param writingAgentSnapshot
     * @param agentSnapshots
     */
    private void insertAgentSnapshot(AgentSnapshot writingAgentSnapshot, List agentSnapshots)
    {
//        PlacedAgent writingPlacedAgent = writingAgentSnapshot.getPlacedAgent();
//
//        AgentGroup agentGroup = ((AgentGroupAssociation) writingPlacedAgent.getAgentGroupAssociations().toArray()[0]).getAgentGroup();
//
//        CommissionProfile commissionProfile = agentGroup.getCommissionProfile();
//
//        Agent agent = agentGroup.getAgent();
//
//        AgentContract agentContract = AgentContract.findBy_AgentId_AND_ContractCodeCT(agent.getAgentNumber(), agentGroup.getContractCodeCT());
//
//        AgentSnapshotDetailVO agentSnapshotDetailVO = new AgentSnapshotDetailVO();
//
//        ClientDetailVO clientDetailVO = getComposedClientDetailVO((ClientRoleVO) agent.getClientRole().getVO());
//
//        EDITBigDecimal totalCommissionsPaid = getTotalCommissionsPaid(writingAgentSnapshot);
//
//        // Set the snapshot values...
//        agentSnapshotDetailVO.setAgentVO(0, (AgentVO) agent.getVO());
//
//        agentSnapshotDetailVO.setAgentContractVO(0, (AgentContractVO) agentContract.getVO());
//
//        agentSnapshotDetailVO.setCommissionProfileVO(0, (CommissionProfileVO) commissionProfile.getVO());
//
//        agentSnapshotDetailVO.setClientDetailVO(0, clientDetailVO);
//
//        agentSnapshotDetailVO.setAgentGroupFK(agentGroup.getAgentGroupPK().longValue());
//
//        agentSnapshotDetailVO.setTotalCommissionsPaid(totalCommissionsPaid.getBigDecimal());
//
//        // Add new Snapshot to the list of snapshots just after the 1st Agent (the writing agent).
//        agentSnapshots.add(1, agentSnapshotDetailVO);
//
//        // Re-number the artificial PKs.
//        for (int i = 0; i < agentSnapshots.size(); i++)
//        {
//            AgentSnapshotDetailVO snapshotDetailVO = (AgentSnapshotDetailVO) agentSnapshots.get(i);
//
//            snapshotDetailVO.setAgentSnapshotDetailPK(i + 1);
//        }
    }

    private EDITBigDecimal getTotalCommissionsPaid(long agentSnapshotPK, long segmentFK) throws Exception
    {
        EDITBigDecimal totalCommissionsPaid = null;

        event.business.Event eventComponent = new event.component.EventComponent();
 
        String[] trxTypeCTs = TransactionPriority.getCommissionableEvents();

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);

        CommissionHistoryVO[] commissionHistoryVOs = eventComponent.composeCommissionHistoryVOByAgentSnapshotPK_SegmentFK(agentSnapshotPK, segmentFK, trxTypeCTs, voInclusionList);

        totalCommissionsPaid = sumCommissionAmounts(commissionHistoryVOs);

        return totalCommissionsPaid;
    }

    /**
     * Sums the CommissionHistory.CommissionAmount. If the CommissionHistory.CommissionTypeCT = 'ChargeBack', then
     * the current CommissionHistory.CommissionAmount is negated before being added to the sum total.
     * @param commissionHistoryVOs
     * @return
     */
    private EDITBigDecimal sumCommissionAmounts(CommissionHistoryVO[] commissionHistoryVOs)
    {
        EDITBigDecimal totalCommissionsPaid = new EDITBigDecimal();

        if (commissionHistoryVOs != null)
        {
            for (int i = 0; i < commissionHistoryVOs.length; i++)
            {
                EDITTrxVO editTrxVO = (EDITTrxVO) commissionHistoryVOs[i].getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);

                boolean isRemovalTrx = checkIfTrxIsRemoval(editTrxVO);

                String commissionType = commissionHistoryVOs[i].getCommissionTypeCT();

                if (commissionType.equalsIgnoreCase("Chargeback"))
                {
                    if (!isRemovalTrx)
                    {
                        totalCommissionsPaid = totalCommissionsPaid.subtractEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                    }
                }
                else if (!commissionType.equalsIgnoreCase("ChargebackRev"))
                {
                    totalCommissionsPaid = totalCommissionsPaid.addEditBigDecimal(commissionHistoryVOs[i].getCommissionAmount());
                }
            }
        }
        return totalCommissionsPaid;
    }

    private boolean checkIfTrxIsRemoval(EDITTrxVO editTrxVO)
    {
        boolean isRemovalTrx = false;

        EDITTrx editTrx = new EDITTrx(editTrxVO);

        if (editTrx.isRemovalTransaction() || editTrx.isPartialRemovalTransaction())
        {
            isRemovalTrx = true;
        }

        return isRemovalTrx;
    }

    /**
     * Sums the total CommissionHistory.CommissionAmount for all CommissionHistories associated with the specified
     * AgentGroup for the specified Segment.
     * @param placedAgentPK
     * @param segmentFK
     * @return
     * @throws Exception
     */
    private EDITBigDecimal getTotalCommissionsPaid(AgentSnapshot writingAgentSnapshot)
    {
//        EDITBigDecimal totalCommissionsPaid = null;
//
//        event.business.Event eventComponent = new event.component.EventComponent();
//
//        AgentGroup agentGroup = writingAgentSnapshot.getPlacedAgent().getAgentGroup();
//
//        Segment segment = writingAgentSnapshot.getAgentHierarchy().getSegment();
//
//        CommissionHistoryVO[] commissionHistoryVOs = new event.dm.composer.VOComposer().composeCommissionHistoryVOByAgentGroupPK_SegmentFK(agentGroup.getAgentGroupPK().longValue(), segment.getSegmentPK().longValue(), new ArrayList());
//
//        totalCommissionsPaid = sumCommissionAmounts(commissionHistoryVOs);
//
//        return totalCommissionsPaid;
        return null;
    }

    public AgentVO[] composeAgentVOByRolePK(long clientRolePK, List voInclusionList)
    {

        List agentVOs = new ArrayList();

        AgentVO[] agentVO = DAOFactory.getAgentDAO().findByClientRolePK(clientRolePK);
        if (agentVO != null)
        {

            for (int a = 0; a < agentVO.length; a++)
            {

                AgentComposer composer = new AgentComposer(voInclusionList);
                composer.compose(agentVO[a]);
                agentVOs.add(agentVO[a]);
            }
        }

        if (agentVOs.size() == 0)
        {

            return null;
        }

        else
        {

            return (AgentVO[]) agentVOs.toArray(new AgentVO[agentVOs.size()]);
        }
    }

    public AgentVO composeAgentVOByAgentId(String agentId, List voInclusionList) throws Exception
    {

        AgentVO returnedAgentVO = null;

        ClientDetailVO[] clientDetailVOs = client.dm.dao.DAOFactory.getClientDetailDAO().findByAgentId(agentId, false, null);
        if (clientDetailVOs != null && clientDetailVOs.length > 0)
        {

            long clientDetailPK = clientDetailVOs[0].getClientDetailPK();
            ClientRoleVO[] clientRoleVOs =
                    role.dm.dao.DAOFactory.getClientRoleDAO().
                    getClientRoleByClientDetailAndRoleType(clientDetailPK,
                            "Agent",
                            false,
                            null);
            if (clientRoleVOs != null && clientRoleVOs.length > 0)
            {

                clientRoleVOs[0].setParentVO(ClientDetailVO.class, clientDetailVOs[0]);

                long clientRolePK = clientRoleVOs[0].getClientRolePK();
                AgentVO[] agentVO = DAOFactory.getAgentDAO().findByClientRolePK(clientRolePK);
                if (agentVO != null && agentVO.length > 0)
                {

                    agentVO[0].setParentVO(ClientRoleVO.class, clientRoleVOs[0]);
                    returnedAgentVO = agentVO[0];
                }
            }
        }

        return returnedAgentVO;
    }

    public CommissionProfileVO[] composeCommissionProfiles(List voInclusionList) throws Exception
    {

        List commissionProfileVOs = new ArrayList();

        CommissionProfileVO[] commissionProfileVO = DAOFactory.getCommissionProfileDAO().getAllCommissionProfiles();
        if (commissionProfileVO != null)
        {

            for (int p = 0; p < commissionProfileVO.length; p++)
            {

                CommissionProfileComposer composer = new CommissionProfileComposer(voInclusionList);
                composer.compose(commissionProfileVO[p]);
                commissionProfileVOs.add(commissionProfileVO[p]);
            }
        }

        if (commissionProfileVOs.size() == 0)
        {

            return null;
        }

        else
        {

            return (CommissionProfileVO[]) commissionProfileVOs.toArray(new CommissionProfileVO[commissionProfileVOs.size()]);
        }
    }

    public CommissionProfileVO[] composeCommissionProfileVOByContractCodeCT(String contractCodeCT, List voInclusionList) throws Exception
    {
        CommissionProfileVO[] commissionProfileVO = DAOFactory.getCommissionProfileDAO().findBycontractCodeCT(contractCodeCT);

        if (commissionProfileVO != null)
        {
            for (int i = 0; i < commissionProfileVO.length; i++)
            {
                new CommissionProfileComposer(voInclusionList).compose(commissionProfileVO[i]);
            }
        }

        return commissionProfileVO;
    }

    public RedirectVO composeRedirectVOByAgentFK(long agentFK, List voInclusionList)
    {
        RedirectVO[] redirectVO = DAOFactory.getRedirectDAO().findByAgentFK(agentFK);

        if (redirectVO != null)
        {
            new RedirectComposer(voInclusionList).compose(redirectVO[0]);

            return redirectVO[0];
        }
        else
        {
            return null;
        }
    }
}
