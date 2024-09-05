package event.dm.composer;

import edit.common.vo.*;
import edit.services.db.Composer;
import event.dm.dao.DAOFactory;

import java.util.ArrayList;
import java.util.List;

import client.ClientAddress;
import client.ClientDetail;
import contract.ContractClient;
import role.ClientRole;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 26, 2003
 * Time: 12:48:52 PM
 * To change this template use Options | File Templates.
 */
public class ClientComposer extends Composer
{
    public ClientVO compose(ClientSetupVO clientSetupVO) throws Exception
    {
        List contractClientVOInclusionList = new ArrayList();
        contractClientVOInclusionList.add(ContractClientVO.class);
        contractClientVOInclusionList.add(ContractClientAllocationVO.class);
        contractClientVOInclusionList.add(WithholdingVO.class);

        // ContractClientAllocationOverrideVO[] contractClientAllocationOverrideVO = DAOFactory.getContractClientAllocationOverrideDAO().findByClientSetupPK(clientSetupVO.getClientSetupPK());
        // commented above line(s) for change in table name ContractClientAllocationOverride
        // sprasad 9/29/2004
        ContractClientAllocationOvrdVO[] contractClientAllocationOverrideVO = DAOFactory.getContractClientAllocationOverrideDAO().findByClientSetupPK(clientSetupVO.getClientSetupPK());
        WithholdingOverrideVO[] withholdingOverrideVO = DAOFactory.getWithholdingOverrideDAO().findByClientSetupPK(clientSetupVO.getClientSetupPK());

        // if (contractClientAllocationOverrideVO != null) clientSetupVO.setContractClientAllocationOverrideVO(contractClientAllocationOverrideVO);
        // commented above line(s) for change in table name ContractClientAllocationOverride
        // sprasad 9/29/2004
        if (contractClientAllocationOverrideVO != null) clientSetupVO.setContractClientAllocationOvrdVO(contractClientAllocationOverrideVO);
        if (withholdingOverrideVO != null) clientSetupVO.setWithholdingOverrideVO(withholdingOverrideVO);

        // A. Get ContractClientVO
        ContractClientVO contractClientVO = null;

        long clientRolePK = 0;

        if (clientSetupVO.getContractClientFK() != 0)
        {
            contractClientVO = new contract.dm.composer.VOComposer().composeContractClientVO(clientSetupVO, contractClientVOInclusionList);

            clientRolePK = contractClientVO.getClientRoleFK();
        }
        else if (clientSetupVO.getClientRoleFK() != 0)
        {
            clientRolePK = clientSetupVO.getClientRoleFK();
        }

        // B. Get ClientRoleVO
        ClientRoleVO[] clientRoleVO = role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(clientRolePK, false, new ArrayList());

        // C. Get ClientDetailVO
        List clientDetailVOInclusionList = new ArrayList();
        clientDetailVOInclusionList.add(PreferenceVO.class);
        clientDetailVOInclusionList.add(TaxInformationVO.class);
        clientDetailVOInclusionList.add(TaxProfileVO.class);

        ClientDetailVO clientDetailVO = new client.dm.composer.VOComposer().composeClientDetailVO(clientRoleVO[0].getClientDetailFK(), clientRoleVO[0].getPreferenceFK(), clientRoleVO[0].getTaxProfileFK(), clientDetailVOInclusionList);

        ClientAddress[] clientAddresses = ClientAddress.findAllActiveByClientDetailPK(clientDetailVO.getClientDetailPK());
        if (clientAddresses != null)
        {
            for (int i = 0; i < clientAddresses.length; i++)
            {
                clientDetailVO.addClientAddressVO((ClientAddressVO) clientAddresses[i].getVO());
            }
        }

        // D. Build the ClientVO
        ClientVO clientVO = new ClientVO();
        clientVO.addClientDetailVO(clientDetailVO);
        if (contractClientVO != null) clientVO.addContractClientVO(contractClientVO);
        clientVO.setRoleTypeCT(clientRoleVO[0].getRoleTypeCT());
        clientVO.setNewIssuesEligibilityStatusCT(clientRoleVO[0].getNewIssuesEligibilityStatusCT());
        clientVO.setClientPK(1);

        return clientVO;
    }

    public ClientVO compose(ContractClientVO contractClientVO) throws Exception
    {
        long clientRolePK = contractClientVO.getClientRoleFK();

        ClientRoleVO[] clientRoleVO = role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(clientRolePK, false, new ArrayList());

        // C. Get ClientDetailVO
        List clientDetailVOInclusionList = new ArrayList();
        clientDetailVOInclusionList.add(ClientAddressVO.class);
        clientDetailVOInclusionList.add(PreferenceVO.class);
        clientDetailVOInclusionList.add(TaxInformationVO.class);
        clientDetailVOInclusionList.add(TaxProfileVO.class);

        ClientDetailVO clientDetailVO = new client.dm.composer.VOComposer().composeClientDetailVO(clientRoleVO[0].getClientDetailFK(), clientRoleVO[0].getPreferenceFK(), clientRoleVO[0].getTaxProfileFK(), clientDetailVOInclusionList);

        // D. Build the ClientVO
        ClientVO clientVO = new ClientVO();
        clientVO.addClientDetailVO(clientDetailVO);
        clientVO.addContractClientVO(contractClientVO);
        clientVO.setRoleTypeCT(clientRoleVO[0].getRoleTypeCT());
        clientVO.setClientPK(1);

        return clientVO;
    }

    public ClientVO compose(ContractClient contractClient, int clientKey) throws Exception
    {
        ContractClientVO contractClientVO = (ContractClientVO) contractClient.getVO();

        // B. Get ClientRoleVO
        ClientRole clientRole = contractClient.getClientRole();

        ClientDetail clientDetail = clientRole.getClientDetail();

        ClientDetailVO clientDetailVO = (ClientDetailVO) clientDetail.getVO();

        ClientAddress clientAddress = ClientAddress.findCurrentAddress(clientDetail.getClientDetailPK());

        if (clientAddress != null)
        {
            clientDetailVO.addClientAddressVO((ClientAddressVO) clientAddress.getVO());
        }

        // D. Build the ClientVO
        ClientVO clientVO = new ClientVO();
        clientVO.addClientDetailVO(clientDetailVO);
        clientVO.addContractClientVO(contractClientVO);
        clientVO.setRoleTypeCT(clientRole.getRoleTypeCT());
        clientVO.setNewIssuesEligibilityStatusCT(clientRole.getNewIssuesEligibilityStatusCT());
        clientVO.setClientPK(clientKey);

        return clientVO;
    }
}
