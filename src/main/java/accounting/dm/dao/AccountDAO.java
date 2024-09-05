/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jan 17, 2002
 * Time: 1:40:15 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package accounting.dm.dao;


import edit.common.vo.AccountVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class AccountDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AccountDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Account");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public AccountVO[] findAccountsByElementStructureId(long elementStructureId)
    {
        String elementStructureFKCol = DBTABLE.getDBColumn("ElementStructureFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementStructureFKCol + " = " + elementStructureId;

        return (AccountVO[]) executeQuery(AccountVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public AccountVO[] findByAccountId(long accountId)
    {
        String accountPKCol = DBTABLE.getDBColumn("AccountPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + accountPKCol + " = " + accountId;

        return (AccountVO[]) executeQuery(AccountVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    /**
     * Finder By Account Number.
     * @param accountNumber
     * @return
     */
    public AccountVO[] findByAccountNumber(String accountNumber)
    {
        String accountNumberCol = DBTABLE.getDBColumn("AccountNumber").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " WHERE " + accountNumberCol + " = " + accountNumber;

        return (AccountVO[]) executeQuery(AccountVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
    }
}
