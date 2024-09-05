/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 1, 2003
 * Time: 7:53:13 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security.dm.dao;

import edit.common.vo.OperatorRoleVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class OperatorRoleDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public OperatorRoleDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("OperatorRole");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public OperatorRoleVO[] findByOperatorPKRolePK(long operatorPK, long rolePK)
    {
        String operatorFKCol = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String roleFKCol     = DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + operatorFKCol + " = " + operatorPK +
                     " AND " + roleFKCol + " = " + rolePK;

        return (OperatorRoleVO[]) executeQuery(OperatorRoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    public OperatorRoleVO[] findByRolePK(long rolePK)
    {
        String roleFKCol = DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + roleFKCol + " = " + rolePK;

        return (OperatorRoleVO[]) executeQuery(OperatorRoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    public OperatorRoleVO[] findByOperatorPK(long operatorPK)
    {
        String operatorFKCol = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + operatorFKCol + " = " + operatorPK;

        return (OperatorRoleVO[]) executeQuery(OperatorRoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}