package agent.dm.composer;

import edit.common.vo.*;
import edit.services.db.*;
import role.dm.composer.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 25, 2003
 * Time: 2:05:57 PM
 * To change this template use Options | File Templates.
 */
public class AgentComposer extends Composer
{
    private List voInclusionList;

    CRUD crud = null;

    public AgentComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public AgentVO compose(long agentPK) throws Exception
    {
        AgentVO agentVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            agentVO = (AgentVO) crud.retrieveVOFromDB(AgentVO.class, agentPK);

            compose(agentVO);
        }
        catch(Exception e)
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

        return agentVO;
    }

    public void compose(AgentVO agentVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(AgentNoteVO.class)) appendAgentNoteVO(agentVO);
            if (voInclusionList.contains(AgentLicenseVO.class)) appendAgentLicenseVO(agentVO);
            if (voInclusionList.contains(CheckAdjustmentVO.class)) appendCheckAdjustmentVO(agentVO);
            if (voInclusionList.contains(AgentContractVO.class)) appendAgentContractVO(agentVO);
            if (voInclusionList.contains(VestingVO.class)) appendVestingVO(agentVO);
            if (voInclusionList.contains(ClientRoleVO.class)) appendClientRoleVO(agentVO);
            if (voInclusionList.contains(RedirectVO.class)) appendRedirectVO(agentVO);
            if (voInclusionList.contains(AgentRequirementVO.class)) appendRequirementVO(agentVO);
        }
        catch(Exception e)
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
    }

    public AgentVO compose(String agentNumber)
    {
        AgentVO[] agentVO = agent.dm.dao.DAOFactory.getAgentDAO().findAgentByAgentNumber(agentNumber);
        if (agentVO != null && agentVO.length > 0)
        {
            compose(agentVO[0]);
            return agentVO[0];
        }
        else
        {
            return null;
        }
    }

    private void appendClientRoleVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        ClientRoleVO[] clientRoleVO = (ClientRoleVO[]) crud.retrieveVOFromDB(ClientRoleVO.class, AgentVO.class, agentVO.getAgentPK());

        if (clientRoleVO != null)
        {
            agentVO.setClientRoleVO(clientRoleVO);

            for (int i = 0; i < clientRoleVO.length; i++)
            {
                new ClientRoleComposer(voInclusionList).compose(clientRoleVO[i]);
                clientRoleVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }

    private void appendAgentNoteVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        AgentNoteVO[] agentNoteVO = (AgentNoteVO[]) crud.retrieveVOFromDB(AgentNoteVO.class, AgentVO.class, agentVO.getAgentPK());

        if (agentNoteVO != null)
        {
            agentVO.setAgentNoteVO(agentNoteVO);

            for (int i = 0; i < agentNoteVO.length; i++)
            {
                agentNoteVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }

    private void appendAgentLicenseVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        AgentLicenseVO[] agentLicenseVO = (AgentLicenseVO[]) crud.retrieveVOFromDB(AgentLicenseVO.class, AgentVO.class, agentVO.getAgentPK());

        if (agentLicenseVO != null)
        {
            agentVO.setAgentLicenseVO(agentLicenseVO);

            for (int i = 0; i < agentLicenseVO.length; i++)
            {
                agentLicenseVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }

    private void appendCheckAdjustmentVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        CheckAdjustmentVO[] checkAdjustmentVO = (CheckAdjustmentVO[]) crud.retrieveVOFromDB(CheckAdjustmentVO.class, AgentVO.class, agentVO.getAgentPK());

        if (checkAdjustmentVO != null)
        {
            agentVO.setCheckAdjustmentVO(checkAdjustmentVO);

            for (int i = 0; i < checkAdjustmentVO.length; i++)
            {
                checkAdjustmentVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }

    private void appendAgentContractVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        AgentContractVO[] agentContractVO = (AgentContractVO[]) crud.retrieveVOFromDB(AgentContractVO.class, AgentVO.class, agentVO.getAgentPK());

        if (agentContractVO != null)
        {
            agentVO.setAgentContractVO(agentContractVO);

            for (int i = 0; i < agentContractVO.length; i++)
            {
                new AgentContractComposer(voInclusionList).compose(agentContractVO[i]);

                agentContractVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }

    private void appendRedirectVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        RedirectVO[] redirectVO = (RedirectVO[]) crud.retrieveVOFromDB(RedirectVO.class, AgentVO.class, agentVO.getAgentPK());

        if (redirectVO != null)
        {
            agentVO.setRedirectVO(redirectVO);

            for (int i = 0; i < redirectVO.length; i++)
            {
                new RedirectComposer(voInclusionList).compose(redirectVO[i]);

                redirectVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }

    private void appendVestingVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        VestingVO[] vestingVO = (VestingVO[]) crud.retrieveVOFromDB(VestingVO.class, AgentVO.class, agentVO.getAgentPK());

        if (vestingVO != null)
        {
            agentVO.setVestingVO(vestingVO);

            for (int i = 0; i < vestingVO.length; i++)
            {
                vestingVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }

    private void appendRequirementVO(AgentVO agentVO) throws Exception
    {
        voInclusionList.remove(AgentVO.class);

        AgentRequirementVO[] agentRequirementVO = (AgentRequirementVO[]) crud.retrieveVOFromDB(AgentRequirementVO.class, AgentVO.class, agentVO.getAgentPK());

        if (agentRequirementVO != null)
        {
            agentVO.setAgentRequirementVO(agentRequirementVO);

            for (int i = 0; i < agentRequirementVO.length; i++)
            {
                agentRequirementVO[i].setParentVO(AgentVO.class, agentVO);
            }
        }

        voInclusionList.add(AgentVO.class);
    }
}
