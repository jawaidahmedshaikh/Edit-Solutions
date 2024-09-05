package agent;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.common.vo.AgentVO;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Dec 5, 2003
 * Time: 11:25:21 AM
 * To change this template use Options | File Templates.
 */
public class RedirectImpl extends CRUDEntityImpl
{
    protected void save(Redirect redirect)
    {
        super.save(redirect, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void load(Redirect redirect, long redirectPK)
    {
        super.load(redirect, redirectPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(Redirect redirect)
    {
        super.delete(redirect, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
