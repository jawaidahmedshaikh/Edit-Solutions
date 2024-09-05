package role;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 24, 2003
 * Time: 11:23:14 AM
 * To change this template use Options | File Templates.
 */
public class ClientRoleImpl extends CRUDEntityImpl
{
    protected void load(ClientRole clientRole, long clientRolePK)
    {
        super.load(clientRole, clientRolePK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(ClientRole clientRole)
    {
        super.save(clientRole, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(ClientRole clientRole)
    {
        super.delete(clientRole, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
