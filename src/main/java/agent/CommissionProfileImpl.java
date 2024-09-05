package agent;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 29, 2003
 * Time: 2:26:49 PM
 * To change this template use Options | File Templates.
 */
public class CommissionProfileImpl extends CRUDEntityImpl
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
}