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

import edit.common.vo.BucketHistoryVO;
import edit.services.db.*;

public class BucketHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public BucketHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("BucketHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public BucketHistoryVO[] findByEditTrxHistoryPK(long editTrxHistoryPK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (BucketHistoryVO[]) executeQuery(BucketHistoryVO.class,
                                                 sql,
                                                 POOLNAME,
                                                 false,
                                                 null);
    }

    public BucketHistoryVO[] findByBucketFK(long bucketFK)
    {
        String bucketFKCol = DBTABLE.getDBColumn("BucketFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + bucketFKCol + " = " + bucketFK;

        return (BucketHistoryVO[]) executeQuery(BucketHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    public BucketHistoryVO[] findByInvestmentAndEditTrxHistory(long investmentFK,
                                                                long editTrxHistoryPK) throws Exception
    {
        DBTable bucketDBTable = DBTable.getDBTableForTable("Bucket");

        String bucketTablename = bucketDBTable.getFullyQualifiedTableName();

        String bucketFKCol = DBTABLE.getDBColumn("BucketFK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String bucketPKCol = bucketDBTable.getDBColumn("BucketPK").getFullyQualifiedColumnName();
        String investmentFKCol = bucketDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + bucketTablename +
                     " WHERE " + bucketFKCol + " = " + bucketPKCol +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPK +
                     " AND " + investmentFKCol + " = " + investmentFK;

        return (BucketHistoryVO[]) executeQuery(BucketHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }
}