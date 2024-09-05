/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.WithholdingOverrideVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class WithholdingOverrideDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public WithholdingOverrideDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("WithholdingOverride");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public WithholdingOverrideVO[] findByClientSetupPK(long clientSetupPK)
    {
        String clientSetupFKCol = DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientSetupFKCol + " = " + clientSetupPK;

        return (WithholdingOverrideVO[]) executeQuery(WithholdingOverrideVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         false,
                                                          null);
    }

    public WithholdingOverrideVO[] findByWithholdingOverridePK(long withholdingOverridePK)
    {
        String withholdingOverridePKCol = DBTABLE.getDBColumn("WithholdingOverridePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + withholdingOverridePKCol + " = " + withholdingOverridePK;

        return (WithholdingOverrideVO[]) executeQuery(WithholdingOverrideVO.class,
                                                       sql,
                                                        POOLNAME,
                                                         false,
                                                          null);
    }
}