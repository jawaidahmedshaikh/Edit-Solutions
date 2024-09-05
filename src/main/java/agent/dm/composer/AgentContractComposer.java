/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 2:42:13 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package agent.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

public class AgentContractComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public AgentContractComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public AgentContractVO compose(long agentContractPK) throws Exception
    {
        AgentContractVO agentContractVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            agentContractVO = (AgentContractVO) crud.retrieveVOFromDB(AgentContractVO.class, agentContractPK);

            compose(agentContractVO);
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

        return agentContractVO;
    }

    public void compose(AgentContractVO agentContractVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(AgentVO.class)) associateAgentVO(agentContractVO);
            if (voInclusionList.contains(PlacedAgentVO.class)) appendPlacedAgentVO(agentContractVO);
            if (voInclusionList.contains(AdditionalCompensationVO.class)) appendAdditionalCompensationVO(agentContractVO);
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

    private void appendAdditionalCompensationVO(AgentContractVO agentContractVO) throws Exception
    {
        voInclusionList.remove(AgentContractVO.class);

        AdditionalCompensationVO[] additionalCompensationVO = (AdditionalCompensationVO[]) crud.retrieveVOFromDB(AdditionalCompensationVO.class, AgentContractVO.class, agentContractVO.getAgentContractPK());

        if (additionalCompensationVO != null)
        {
            agentContractVO.setAdditionalCompensationVO(additionalCompensationVO);

            for (int i = 0; i < additionalCompensationVO.length; i++)
            {
                additionalCompensationVO[i].setParentVO(AgentContractVO.class, agentContractVO);
            }
        }

        voInclusionList.add(AgentContractVO.class);
    }

    private void associateAgentVO(AgentContractVO agentContractVO) throws Exception
    {
        if (agentContractVO.getAgentFK() != 0)
        {
            voInclusionList.remove(AgentContractVO.class);

            AgentComposer agentComposer = new AgentComposer(voInclusionList);

            AgentVO agentVO = agentComposer.compose(agentContractVO.getAgentFK());

            if (agentVO != null)
            {
                agentContractVO.setParentVO(AgentVO.class, agentVO);

                agentVO.addAgentContractVO(agentContractVO);
            }

            voInclusionList.add(AgentContractVO.class);
        }
    }

    private void appendPlacedAgentVO(AgentContractVO agentContractVO) throws Exception
    {
        voInclusionList.remove(AgentContractVO.class);

        PlacedAgentVO[] placedAgentVO = (PlacedAgentVO[]) crud.retrieveVOFromDB(PlacedAgentVO.class, AgentContractVO.class, agentContractVO.getAgentContractPK());

        if (placedAgentVO != null)
        {
            agentContractVO.setPlacedAgentVO(placedAgentVO);

            for (int i = 0; i < placedAgentVO.length; i++)
            {
                new PlacedAgentComposer(voInclusionList).compose(placedAgentVO[i]);

                placedAgentVO[i].setParentVO(AgentContractVO.class,  agentContractVO);
            }
        }

        voInclusionList.add(AgentContractVO.class);
    }
}
