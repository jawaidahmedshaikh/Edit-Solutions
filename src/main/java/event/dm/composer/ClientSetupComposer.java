package event.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.*;

import java.util.List;

import role.dm.composer.ClientRoleComposer;

/**
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Aug 3, 2004
 * Time: 7:30:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientSetupComposer extends Composer
{
    private List voInclusionList;
    private CRUD crud;

    public ClientSetupComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ClientSetupVO compose(long clientSetupPK) throws Exception
    {
        ClientSetupVO clientSetupVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            clientSetupVO = (ClientSetupVO) crud.retrieveVOFromDB(ClientSetupVO.class, clientSetupPK);

            compose(clientSetupVO);
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

        return clientSetupVO;
    }

    public ClientSetupVO compose(ClientSetupVO clientSetupVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(EDITTrxVO.class)) appendEDITTrxVO(clientSetupVO);

            if (voInclusionList.contains(ContractSetupVO.class)) associateContractSetupVO(clientSetupVO);

            if (voInclusionList.contains(ClientRoleVO.class)) associateClientRoleVO(clientSetupVO);
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

        return clientSetupVO;
    }

    private void appendEDITTrxVO(ClientSetupVO clientSetupVO) throws Exception
    {
        voInclusionList.remove(ClientSetupVO.class);

        EDITTrxVO[] editTrxVO = null;

        editTrxVO = (EDITTrxVO[]) crud.retrieveVOFromDB(EDITTrxVO.class, ClientSetupVO.class, clientSetupVO.getClientSetupPK());

        if (editTrxVO != null)
        {
            clientSetupVO.setEDITTrxVO(editTrxVO);
        }

        voInclusionList.add(ClientSetupVO.class);
    }

    private void associateContractSetupVO(ClientSetupVO clientSetupVO) throws Exception
    {
        voInclusionList.remove(ClientSetupVO.class);

        ContractSetupVO contractSetupVO = (ContractSetupVO) crud.retrieveVOFromDB(ContractSetupVO.class, clientSetupVO.getContractSetupFK());

        ContractSetupComposer composer = new ContractSetupComposer(voInclusionList);

        composer.compose(contractSetupVO);

        clientSetupVO.setParentVO(ContractSetupVO.class, contractSetupVO);

        contractSetupVO.addClientSetupVO(clientSetupVO);

        voInclusionList.add(ClientSetupVO.class);
    }

    private void associateClientRoleVO(ClientSetupVO clientSetupVO) throws Exception
    {
        voInclusionList.remove(ClientSetupVO.class);

        ClientRoleVO clientRoleVO = (ClientRoleVO) crud.retrieveVOFromDB(ClientRoleVO.class, clientSetupVO.getClientRoleFK());

        ClientRoleComposer composer = new ClientRoleComposer(voInclusionList);

        composer.compose(clientRoleVO);

        clientSetupVO.setParentVO(ClientRoleVO.class, clientRoleVO);

        clientRoleVO.addClientSetupVO(clientSetupVO);

        voInclusionList.add(ClientSetupVO.class);
    }
}
