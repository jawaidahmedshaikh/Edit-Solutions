/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Feb 12, 2002
 * Time: 11:55:45 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.RealTimeActivityVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class RealTimeActivityDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public RealTimeActivityDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("RealTimeActivity");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public RealTimeActivityVO[] findRealTimeLogBySegmentFK(long segmentFK)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK;

        return (RealTimeActivityVO[]) executeQuery(RealTimeActivityVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }

    public RealTimeActivityVO[] findRealTimeActivityByPK(long realTimeActivityPK)
    {
        String realTimeActivityPKCol = DBTABLE.getDBColumn("RealTimeActivityPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + realTimeActivityPKCol + " = " + realTimeActivityPK;

        return (RealTimeActivityVO[]) executeQuery(RealTimeActivityVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }

    public RealTimeActivityVO[] findAllRealTimeActivityEntries()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (RealTimeActivityVO[]) executeQuery(RealTimeActivityVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      false,
                                                       null);
    }
}