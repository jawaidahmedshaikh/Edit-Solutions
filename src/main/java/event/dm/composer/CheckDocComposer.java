package event.dm.composer;

import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.services.db.Composer;

import java.util.ArrayList;
import java.util.List;

import role.dm.composer.ClientRoleComposer;
import client.dm.composer.ClientDetailComposer;
import event.dm.dao.*;
import event.*;
import reinsurance.*;
import reinsurance.dm.dao.*;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Sep 3, 2003
 * Time: 2:34:24 PM
 * To change this template use Options | File Templates.
 */
public class CheckDocComposer extends Composer
{
    public CheckDocVO compose(EDITTrxVO editTrxVO) throws EDITEventException, Exception
    {
        List editTrxVOInclusionList = new ArrayList();
        editTrxVOInclusionList.add(ContractSetupVO.class);
        editTrxVOInclusionList.add(ChargeVO.class);
        editTrxVOInclusionList.add(GroupSetupVO.class);
        editTrxVOInclusionList.add(ClientSetupVO.class);

        new EDITTrxComposer(editTrxVOInclusionList).compose(editTrxVO);

        List agentInclusionList = new ArrayList();
        agentInclusionList.add(ClientRoleFinancialVO.class);
        agentInclusionList.add(AgentVO.class);
        agentInclusionList.add(CheckAdjustmentVO.class);
        agentInclusionList.add(AgentContractVO.class);
        agentInclusionList.add(PlacedAgentVO.class);

        ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
        ClientRoleVO clientRoleVO = new ClientRoleComposer(agentInclusionList).compose(clientSetupVO.getClientRoleFK());
        AgentVO agentVO = new agent.dm.composer.VOComposer().composeAgentVO(clientRoleVO.getAgentFK(), agentInclusionList);

        PlacedAgentVO[] placedAgentVO = agentVO.getAgentContractVO(0).getPlacedAgentVO();
        if (placedAgentVO == null)
        {
           throw new EDITEventException("Placed Agent not found for Agent Key:" + (agentVO.getAgentPK() + ""));
        }
        else
        {
            agentVO.removeAgentContractVO(0);
        }

        ClientDetailVO clientDetailVO = composeClient(clientRoleVO.getClientDetailFK());

        CheckDocVO checkDocVO = new CheckDocVO();
        checkDocVO.setPlacedAgentFK(clientRoleVO.getPlacedAgentVO()[0].getPlacedAgentPK());
        checkDocVO.addEDITTrxVO(editTrxVO);
        checkDocVO.addClientDetailVO(clientDetailVO);
        checkDocVO.addClientRoleVO(clientRoleVO);

        return checkDocVO;
    }

    public CheckDocVO composeForReinsuranceCheck(EDITTrxVO editTrxVO)
    {
        ClientSetupVO clientSetupVO = ClientSetup.findByPK(editTrxVO.getClientSetupFK());

        TreatyVO treatyVO = new TreatyDAO().findBy_PK(clientSetupVO.getTreatyFK())[0];

        ReinsurerVO reinsurerVO = new ReinsurerDAO().findBy_PK(treatyVO.getReinsurerFK())[0];

        ClientDetailVO clientDetailVO = composeClient(reinsurerVO.getClientDetailFK());

        reinsurerVO.addTreatyVO(treatyVO);
        clientDetailVO.addReinsurerVO(reinsurerVO);

        CheckDocVO checkDocVO = new CheckDocVO();
        checkDocVO.addEDITTrxVO(editTrxVO);
        checkDocVO.addClientDetailVO(clientDetailVO);

        return checkDocVO;
    }

    private ClientDetailVO composeClient(long clientDetailPK)
    {
        List clientInclusionList = new ArrayList();
        clientInclusionList.add(ClientAddressVO.class);
        clientInclusionList.add(PreferenceVO.class);
        clientInclusionList.add(TaxInformationVO.class);
        clientInclusionList.add(TaxProfileVO.class);

        ClientDetailVO clientDetailVO = new ClientDetailComposer(clientInclusionList).compose(clientDetailPK);

        return clientDetailVO;
    }
}
