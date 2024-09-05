package role.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;

import agent.dm.composer.AgentComposer;
import agent.dm.composer.PlacedAgentComposer;
import client.dm.composer.ClientDetailComposer;
import client.dm.composer.PreferenceComposer;
import client.dm.composer.TaxProfileComposer;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 6, 2003
 * Time: 1:07:19 PM
 * To change this template use Options | File Templates.
 */
public class ClientRoleComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public ClientRoleComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ClientRoleVO compose(long clientRolePK)
    {
        ClientRoleVO clientRoleVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            clientRoleVO = (ClientRoleVO) crud.retrieveVOFromDB(ClientRoleVO.class, clientRolePK);

            compose(clientRoleVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return clientRoleVO;
    }

    public void compose(ClientRoleVO clientRoleVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ClientDetailVO.class)) associateClientDetailVO(clientRoleVO);
            if (voInclusionList.contains(PreferenceVO.class)) associatePreferenceVO(clientRoleVO);
            if (voInclusionList.contains(TaxProfileVO.class)) associateTaxProfileVO(clientRoleVO);
            if (voInclusionList.contains(ClientRoleFinancialVO.class)) appendClientRoleFinancialVO(clientRoleVO);
            if (voInclusionList.contains(AgentVO.class)) associateAgentVO(clientRoleVO);
            if (voInclusionList.contains(PlacedAgentVO.class)) appendPlacedAgentVO(clientRoleVO);

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
    }

    private void appendClientRoleFinancialVO(ClientRoleVO clientRoleVO) throws Exception
    {
        voInclusionList.remove(ClientRoleVO.class);

        ClientRoleFinancialVO[] clientRoleFinancialVO = (ClientRoleFinancialVO[]) crud.retrieveVOFromDB(ClientRoleFinancialVO.class, ClientRoleVO.class, clientRoleVO.getClientRolePK());

        if (clientRoleFinancialVO != null)
        {
            clientRoleVO.setClientRoleFinancialVO(clientRoleFinancialVO);

            for (int i = 0; i < clientRoleFinancialVO.length; i++)
            {
                new ClientRoleFinancialComposer(voInclusionList).compose(clientRoleFinancialVO[i]);

                clientRoleFinancialVO[i].setParentVO(ClientRoleVO.class, clientRoleFinancialVO[i]);
            }
        }

        voInclusionList.add(ClientRoleVO.class);
    }

    private void appendPlacedAgentVO(ClientRoleVO clientRoleVO) throws Exception
    {
        voInclusionList.remove(ClientRoleVO.class);

        PlacedAgentVO[] placedAgentVO = (PlacedAgentVO[]) crud.retrieveVOFromDB(PlacedAgentVO.class, ClientRoleVO.class, clientRoleVO.getClientRolePK());

        if (placedAgentVO != null)
        {
            clientRoleVO.setPlacedAgentVO(placedAgentVO);

            for (int i = 0; i < placedAgentVO.length; i++)
            {
                new PlacedAgentComposer(voInclusionList).compose(placedAgentVO[i]);

                placedAgentVO[i].setParentVO(ClientRoleVO.class, clientRoleVO);
            }
        }

        voInclusionList.add(ClientRoleVO.class);
    }

    private void associateAgentVO(ClientRoleVO clientRoleVO) throws Exception
    {
        voInclusionList.remove(ClientRoleVO.class);

        if (clientRoleVO != null && clientRoleVO.getAgentFK() > 0)
        {
            AgentVO agentVO = new AgentComposer(voInclusionList).compose(clientRoleVO.getAgentFK());

            if (agentVO != null)
            {
                clientRoleVO.setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(ClientRoleVO.class);
    }

    private void associateClientDetailVO(ClientRoleVO clientRoleVO) throws Exception
    {
        voInclusionList.remove(ClientRoleVO.class);

        if (clientRoleVO != null)
        {
            ClientDetailVO clientDetailVO = new ClientDetailComposer(voInclusionList).compose(clientRoleVO.getClientDetailFK());

            if (clientDetailVO != null)
            {
                clientRoleVO.setParentVO(ClientDetailVO.class, clientDetailVO);
            }
        }

        voInclusionList.add(ClientRoleVO.class);
    }

    private void associatePreferenceVO(ClientRoleVO clientRoleVO) throws Exception
    {
        voInclusionList.remove(ClientRoleVO.class);

        PreferenceVO preferenceVO = new PreferenceComposer(new ArrayList()).compose(clientRoleVO.getPreferenceFK());

        if (preferenceVO != null)
        {
            clientRoleVO.setParentVO(PreferenceVO.class, preferenceVO);
        }

        voInclusionList.add(ClientRoleVO.class);
    }

    private void associateTaxProfileVO(ClientRoleVO clientRoleVO) throws Exception
    {
        voInclusionList.remove(ClientRoleVO.class);

        TaxProfileVO taxProfileVO = new TaxProfileComposer(new ArrayList()).compose(clientRoleVO.getTaxProfileFK());

        if (taxProfileVO != null)
        {
            clientRoleVO.setParentVO(TaxProfileVO.class, taxProfileVO);
        }

        voInclusionList.add(ClientRoleVO.class);
    }
}
