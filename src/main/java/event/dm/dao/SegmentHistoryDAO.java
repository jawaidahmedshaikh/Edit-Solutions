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

import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.SegmentHistoryVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class SegmentHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public SegmentHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("SegmentHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public SegmentHistoryVO[] findByEditTrxHistoryFK(long editTrxHistoryFK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryFK;

        return (SegmentHistoryVO[]) executeQuery(SegmentHistoryVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 null);
    }
    
    public SegmentHistoryVO[] findByEditTrxHistoryFKAndNonNullBillScheduleFK(long editTrxHistoryFK)
    {
        String segmentHistoryPKCol = DBTABLE.getDBColumn("SegmentHistoryPK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String billScheduleFKCol = DBTABLE.getDBColumn("BillScheduleFK").getFullyQualifiedColumnName();

        String sql = "SELECT TOP 1 * FROM " + TABLENAME +
                " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryFK +
                " AND " + billScheduleFKCol + " IS NOT NULL" +
                 " ORDER BY " + segmentHistoryPKCol + " DESC ";

        return (SegmentHistoryVO[]) executeQuery(SegmentHistoryVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 null);
    }
}