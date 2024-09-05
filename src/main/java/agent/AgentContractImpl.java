package agent;

import agent.dm.dao.DAOFactory;
import edit.common.vo.AdditionalCompensationVO;
import edit.common.vo.AgentContractVO;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 24, 2003
 * Time: 11:23:14 AM
 * To change this template use Options | File Templates.
 */
public class AgentContractImpl extends CRUDEntityImpl
{
    protected static AgentContract[] findByCommissionContractPK(long commissionContractPK)
    {
        AgentContractVO[] agentContractVO = DAOFactory.getAgentContractDAO().findByCommissionContractPK(commissionContractPK);

        AgentContract[] agentContract = null;

        if (agentContractVO != null)
        {
            agentContract = new AgentContract[agentContractVO.length];

            for (int i = 0; i < agentContract.length; i++)
            {
                agentContract[i] = new AgentContract(agentContractVO[i]);
            }
        }

        return agentContract;
    }

    protected static AgentContract[] findByAgentPK(long agentPK)
    {
        AgentContractVO[] agentContractVO = DAOFactory.getAgentContractDAO().findByAgentPK(agentPK);

        AgentContract[] agentContract = null;

        if (agentContractVO != null)
        {
            agentContract = new AgentContract[agentContractVO.length];

            for (int i = 0; i < agentContract.length; i++)
            {
                agentContract[i] = new AgentContract(agentContractVO[i]);
            }
        }

        return agentContract;
    }

    protected void save(AgentContract agentContract)
    {
//        super.save(agentContract, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        AgentContractVO agentContractVO = (AgentContractVO)agentContract.getVO();
        if (agentContractVO != null)
        {
            saveChildren(agentContractVO, agentContract);
        }
    }

    private void saveChildren(AgentContractVO agentContractVO, AgentContract agentContract)
    {
        if (agentContractVO.getAdditionalCompensationVOCount() > 0)
        {
            AdditionalCompensationVO[] additionalCompensationVO = agentContractVO.getAdditionalCompensationVO();

            for (int i = 0; i < additionalCompensationVO.length; i++)
            {
//                additionalCompensationVO[i].setAgentContractFK(agentContractVO.getAgentContractPK());
//                additionalCompensation.save();

                AdditionalCompensation additionalCompensation = new AdditionalCompensation(additionalCompensationVO[i]);
                additionalCompensation.setAgentContract(agentContract);
                additionalCompensation.hSave();
            }
        }
    }

    protected void delete(CRUDEntity crudEntity)
    {
        super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void load(CRUDEntity crudEntity, long pk)
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
