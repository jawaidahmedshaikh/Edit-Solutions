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

import edit.common.vo.InSuspenseVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class InSuspenseDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public InSuspenseDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("InSuspense");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public InSuspenseVO[] findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (InSuspenseVO[]) executeQuery(InSuspenseVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }

    /**
     * Finder.
     * @param inSuspensePK
     * @return
     */
    public InSuspenseVO[] findByPK(long inSuspensePK)
    {
        String inSuspensePKCol = DBTABLE.getDBColumn("InSuspensePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + inSuspensePKCol + " = " + inSuspensePK;

        return (InSuspenseVO[]) executeQuery(InSuspenseVO.class,
                                              sql,
                                               POOLNAME,
                                                false,
                                                 null);
    }
}
