package agent;

import agent.dm.dao.DAOFactory;
import edit.common.vo.AdditionalCompensationVO;
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
public class AdditionalCompensationImpl extends CRUDEntityImpl
{
    protected void load(CRUDEntity crudEntity, long pk)
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(CRUDEntity crudEntity)
    {
        super.save(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(CRUDEntity crudEntity)
    {
        super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected static AdditionalCompensation[] findByAgentContractPK(long agentContractPK)
    {
        AdditionalCompensationVO[] additionalCompensationVO = DAOFactory.getAdditionalCompensationDAO().findByAgentContractPK(agentContractPK);

        AdditionalCompensation[] additionalCompensation = null;

        if (additionalCompensationVO != null)
        {
            additionalCompensation = new AdditionalCompensation[additionalCompensationVO.length];

            for (int i = 0; i < additionalCompensationVO.length; i++)
            {
                additionalCompensation[i] = new AdditionalCompensation(additionalCompensationVO[i]);
            }
        }

        return additionalCompensation;
    }
}
