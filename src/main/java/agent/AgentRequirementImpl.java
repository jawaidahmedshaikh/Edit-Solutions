package agent;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Dec 5, 2003
 * Time: 11:25:21 AM
 * To change this template use Options | File Templates.
 */
public class AgentRequirementImpl extends CRUDEntityImpl
{
    protected void save(AgentRequirement agentRequirement)
    {
        super.save(agentRequirement, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void load(AgentRequirement agentRequirement, long agentRequirementPK) throws Exception
    {
        super.load(agentRequirement, agentRequirementPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(AgentRequirement agentRequirement) throws Exception
    {
        super.delete(agentRequirement, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
