/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 2:42:13 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package agent.dm.composer;

import agent.dm.dao.DAOFactory;
import edit.common.vo.AgentContractVO;
import edit.common.vo.CommissionProfileVO;
import edit.common.vo.PlacedAgentVO;
import edit.common.vo.ClientRoleVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

import role.dm.composer.ClientRoleComposer;

public class PlacedAgentComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public PlacedAgentComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public PlacedAgentVO compose(long placedAgentPK)
    {
        PlacedAgentVO placedAgentVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            placedAgentVO = (PlacedAgentVO) crud.retrieveVOFromDB(PlacedAgentVO.class, placedAgentPK);

            compose(placedAgentVO);
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

        return placedAgentVO;
    }

    public PlacedAgentVO[] composeByAgentContractFK(long agentContractFK) throws Exception
    {
        PlacedAgentVO[] placedAgentVOs = null;

        try
        {
            placedAgentVOs = DAOFactory.getPlacedAgentDAO().findByAgentContractPK(agentContractFK);
            if (placedAgentVOs != null)
            {
                for (int p = 0; p < placedAgentVOs.length; p++)
                {
                    compose(placedAgentVOs[p]);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }

        return placedAgentVOs;
    }

    public void compose(PlacedAgentVO placedAgentVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(AgentContractVO.class)) associateAgentContractVO(placedAgentVO);
            if (voInclusionList.contains(ClientRoleVO.class)) associateClientRoleVO(placedAgentVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void associateAgentContractVO(PlacedAgentVO placedAgentVO) throws Exception
    {
        if (placedAgentVO.getAgentContractFK() != 0)
        {
            voInclusionList.remove(PlacedAgentVO.class);

            AgentContractComposer composer = new AgentContractComposer(voInclusionList);

            AgentContractVO agentContractVO = composer.compose(placedAgentVO.getAgentContractFK());

            if (agentContractVO != null)
            {
                placedAgentVO.setParentVO(AgentContractVO.class, agentContractVO);

                agentContractVO.addPlacedAgentVO(placedAgentVO);
            }

            voInclusionList.add(PlacedAgentVO.class);
        }
    }

    private void associateClientRoleVO(PlacedAgentVO placedAgentVO) throws Exception
    {
        if (placedAgentVO.getClientRoleFK() != 0)
        {
            voInclusionList.remove(PlacedAgentVO.class);

            ClientRoleComposer composer = new ClientRoleComposer(voInclusionList);

            ClientRoleVO clientRoleVO = composer.compose(placedAgentVO.getClientRoleFK());

            if (clientRoleVO != null)
            {
                placedAgentVO.setParentVO(ClientRoleVO.class, clientRoleVO);

                clientRoleVO.addPlacedAgentVO(placedAgentVO);
            }

            voInclusionList.add(PlacedAgentVO.class);
        }
    }
}
