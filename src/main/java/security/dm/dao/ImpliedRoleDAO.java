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

import edit.common.vo.ImpliedRoleVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class ImpliedRoleDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ImpliedRoleDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ImpliedRole");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }


	public ImpliedRoleVO[] findByRolePKAndImpliedRolePK(long rolePK, long impliedRolePK)
    {
        String roleFKCol        = DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();
        String impliedRoleFKCol = DBTABLE.getDBColumn("ImpliedRoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + roleFKCol + " = " + rolePK +
                     " AND " + impliedRoleFKCol + " = " + impliedRolePK;

        return (ImpliedRoleVO[]) executeQuery(ImpliedRoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

	public ImpliedRoleVO[] findByImpliedRoleFK(long impliedRoleFK)
    {
        String impliedRoleFKCol = DBTABLE.getDBColumn("ImpliedRoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + impliedRoleFKCol + " = " + impliedRoleFK;

        return (ImpliedRoleVO[]) executeQuery(ImpliedRoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}
    
	public ImpliedRoleVO[] findByRoleFK(long roleFK)
    {
        String roleFKCol = DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + roleFKCol + " = " + roleFK;

        return (ImpliedRoleVO[]) executeQuery(ImpliedRoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}
}