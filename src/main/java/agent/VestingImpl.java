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
public class VestingImpl extends CRUDEntityImpl
{
    protected void save(Vesting vesting)
    {
        super.save(vesting, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void load(Vesting vesting, long vestingPK) throws Exception
    {
        super.load(vesting, vestingPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(Vesting vesting) throws Exception
    {
        super.delete(vesting, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
