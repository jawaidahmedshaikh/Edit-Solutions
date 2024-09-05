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

import edit.common.vo.OperatorVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;

public class OperatorDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public OperatorDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Operator");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public OperatorVO[] findByOperatorName(String name)
    {
        String nameCol = DBTABLE.getDBColumn("Name").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + nameCol + " = '" + name + "'";

        return (OperatorVO[]) executeQuery(OperatorVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public OperatorVO[] findByOperatorNameAndEncryptedPassword(String name, String encryptedPassword)
    {
        DBTable passwordDBTable = DBTable.getDBTableForTable("Password");

        String passwordTable = passwordDBTable.getFullyQualifiedTableName();

        String nameCol              = DBTABLE.getDBColumn("Name").getFullyQualifiedColumnName();
        String operatorPKCol        = DBTABLE.getDBColumn("OperatorPK").getFullyQualifiedColumnName();

        String encryptedPasswordCol = passwordDBTable.getDBColumn("EncryptedPassword").getFullyQualifiedColumnName();
        String operatorFKCol        = passwordDBTable.getDBColumn("OperatorFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + ", " + passwordTable +
                     " WHERE " + nameCol              + " = '" + name + "'" +
                     " AND "   + encryptedPasswordCol + " = '" + encryptedPassword + "'" +
                     " AND "   + operatorPKCol        + " = " + operatorFKCol;

        return (OperatorVO[]) executeQuery(OperatorVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public OperatorVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (OperatorVO[]) executeQuery(OperatorVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public OperatorVO[] findByPK(long operatorPK, boolean includeChildVOs, List voExclusionList)
    {
        String operatorPKCol = DBTABLE.getDBColumn("OperatorPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + operatorPKCol + " = " + operatorPK;

        return (OperatorVO[]) executeQuery(OperatorVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    public OperatorVO[] findByRolePK(long rolePK)
    {
        DBTable operatorRoleDBTable = DBTable.getDBTableForTable("OperatorRole");

        String operatorRoleTable = operatorRoleDBTable.getFullyQualifiedTableName();

        String operatorPKCol = DBTABLE.getDBColumn("OperatorPK").getFullyQualifiedColumnName();
        
        String operatorFKCol = operatorRoleDBTable.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String roleFKCol     = operatorRoleDBTable.getDBColumn("RoleFK").getFullyQualifiedColumnName();


        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + operatorRoleTable +
                     " WHERE " + operatorPKCol + " = " + operatorFKCol +
                     " AND " + roleFKCol + " = " + rolePK;

        return (OperatorVO[]) executeQuery(OperatorVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }
}
