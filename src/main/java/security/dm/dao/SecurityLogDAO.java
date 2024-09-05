/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 31, 2002
 * Time: 4:06:26 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security.dm.dao;

import edit.common.vo.SecurityLogVO;
import edit.services.db.DAO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;

public class SecurityLogDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public SecurityLogDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("SecurityLog");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public SecurityLogVO[] findAll()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (SecurityLogVO[]) executeQuery(SecurityLogVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }
}
