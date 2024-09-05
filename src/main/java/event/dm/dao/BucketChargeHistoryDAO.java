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

import edit.common.vo.BucketChargeHistoryVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class BucketChargeHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public BucketChargeHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("BucketChargeHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public BucketChargeHistoryVO[] findByBucketHistoryPK(long bucketHistoryPK)
    {
        String bucketHistoryFKCol = DBTABLE.getDBColumn("BucketHistoryFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + bucketHistoryFKCol + " = " + bucketHistoryPK;

        return (BucketChargeHistoryVO[]) executeQuery(BucketChargeHistoryVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 null);
    }
}