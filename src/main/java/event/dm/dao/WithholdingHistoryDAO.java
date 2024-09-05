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

import edit.common.vo.WithholdingHistoryVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class WithholdingHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public WithholdingHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("WithholdingHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public WithholdingHistoryVO[] findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (WithholdingHistoryVO[]) executeQuery(WithholdingHistoryVO.class,
                                                      sql,
                                                       POOLNAME,
                                                        false,
                                                         null);
    }

    /**
     * Finder.
     * @param withholdingHistoryPK
     * @return
     */
    public WithholdingHistoryVO[] findByPK(long withholdingHistoryPK)
    {
        String withholdingHistoryPKCol = DBTABLE.getDBColumn("WithholdingHistoryPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + withholdingHistoryPKCol + " = " + withholdingHistoryPK;

        return (WithholdingHistoryVO[]) executeQuery(WithholdingHistoryVO.class,
                                                      sql,
                                                       POOLNAME,
                                                        false,
                                                         null);
    }
}