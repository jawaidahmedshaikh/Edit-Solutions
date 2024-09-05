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

import edit.common.vo.PasswordVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class PasswordDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public PasswordDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Password");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

	public PasswordVO[] findByOperatorNameAndEncryptedPassword(String name, String encryptedPassword,
                                                               boolean includeChildVOs, List voExclusionList)
    {
        DBTable operatorDBTable = DBTable.getDBTableForTable("Operator");

        String operatorTable = operatorDBTable.getFullyQualifiedTableName();

        String nameCol              = operatorDBTable.getDBColumn("Name").getFullyQualifiedColumnName();
        String operatorPKCol        = operatorDBTable.getDBColumn("OperatorPK").getFullyQualifiedColumnName();

        String encryptedPasswordCol = DBTABLE.getDBColumn("EncryptedPassword").getFullyQualifiedColumnName();
        String operatorFKCol        = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String creationDateCol      = DBTABLE.getDBColumn("CreationDate").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " + operatorTable + ", " + TABLENAME +
                     " WHERE " + nameCol              + " = '" + name + "'" +
                     " AND "   + encryptedPasswordCol + " = '" + encryptedPassword + "'" +
                     " AND "   + operatorPKCol        + " = " + operatorFKCol +
                     " ORDER BY " + creationDateCol + " ASC";

        return (PasswordVO[]) executeQuery(PasswordVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

	public PasswordVO[] findByOperatorNameAndEncryptedPasswordAndStatus(String name, String encryptedPassword,
                                                                        String status, boolean includeChildVOs, List voExclusionList)
    {
        DBTable operatorDBTable = DBTable.getDBTableForTable("Operator");

        String operatorTable = operatorDBTable.getFullyQualifiedTableName();

        String nameCol              = operatorDBTable.getDBColumn("Name").getFullyQualifiedColumnName();
        String operatorPKCol        = operatorDBTable.getDBColumn("OperatorPK").getFullyQualifiedColumnName();

        String encryptedPasswordCol = DBTABLE.getDBColumn("EncryptedPassword").getFullyQualifiedColumnName();
        String operatorFKCol        = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String statusCol            = DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + operatorTable + ", " + TABLENAME +
                     " WHERE " + nameCol            + " = '" + name + "'" +
                     " AND " + encryptedPasswordCol + " = '" + encryptedPassword + "'" +
                     " AND " + operatorPKCol        + " = " + operatorFKCol +
                     " AND " + statusCol            + " = '" + status + "'";

        return (PasswordVO[]) executeQuery(PasswordVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

	public PasswordVO[] findByOperatorPKAndStatus(long operatorPK, String status)
    {
        String operatorFKCol = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String statusCol     = DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + operatorFKCol + " = " + operatorPK +
                     " AND " + statusCol + " = '" + status + "'";

        return (PasswordVO[]) executeQuery(PasswordVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

    public PasswordVO[] findByOperatorPK(long operatorPK)
    {
        String operatorFKCol = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + operatorFKCol + " = " + operatorPK;

        return (PasswordVO[]) executeQuery(PasswordVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

	public PasswordVO[] findAllByOperatorName(String name,  boolean includeChildVOs, List voExclusionList)
    {
        DBTable operatorDBTable = DBTable.getDBTableForTable("Operator");

        String operatorTable = operatorDBTable.getFullyQualifiedTableName();

        String nameCol         = operatorDBTable.getDBColumn("Name").getFullyQualifiedColumnName();
        String operatorPKCol   = operatorDBTable.getDBColumn("OperatorPK").getFullyQualifiedColumnName();

        String operatorFKCol   = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String creationDateCol = DBTABLE.getDBColumn("CreationDate").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + operatorTable + ", " + TABLENAME +
                     " WHERE " + nameCol       + " = '" + name + "'" +
                     " AND "   + operatorPKCol + " = " + operatorFKCol +
                     " ORDER BY " + creationDateCol + " DESC";

        return (PasswordVO[]) executeQuery(PasswordVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

    public PasswordVO[] findByLatestOrderOfCreation(long operatorPK)
    {
        String operatorFKCol      = DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String orderOfCreationCol = DBTABLE.getDBColumn("OrderOfCreation").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + operatorFKCol + " = " + operatorPK +
                     " AND " + orderOfCreationCol +
                            " = (SELECT MAX(" + orderOfCreationCol + ") FROM " + TABLENAME + " WHERE " + operatorFKCol + " = " + operatorPK + " )";

        return (PasswordVO[]) executeQuery(PasswordVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}