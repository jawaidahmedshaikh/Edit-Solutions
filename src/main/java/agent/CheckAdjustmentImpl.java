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
public class CheckAdjustmentImpl extends CRUDEntityImpl
{
    protected void save(CheckAdjustment checkAdjustment)
    {
        super.save(checkAdjustment, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void load(CheckAdjustment checkAdjustment, long checkAdjustmentPK) throws Exception
    {
        super.load(checkAdjustment, checkAdjustmentPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(CheckAdjustment checkAdjustment) throws Exception
    {
        super.delete(checkAdjustment, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
