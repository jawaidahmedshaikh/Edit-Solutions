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
public class AgentNoteImpl extends CRUDEntityImpl
{
    protected void save(AgentNote agentNote)
    {
        super.save(agentNote, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void load(AgentNote agentnote, long agentnotePK)
    {
        super.load(agentnote, agentnotePK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(AgentNote agentnote)
    {
        super.delete(agentnote, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
