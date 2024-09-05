/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.BucketAllocationVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class BucketAllocationDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public BucketAllocationDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("BucketAllocation");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public BucketAllocationVO[] findByBucketAllocationPK(long bucketAllocationPK, boolean includeChildVOs, List voExclusionList)
    {
        String bucketAllocationPKCol = DBTABLE.getDBColumn("BucketAllocationPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + bucketAllocationPKCol + " = " + bucketAllocationPK;

        return (BucketAllocationVO[]) executeQuery(BucketAllocationVO.class,
                                                    sql,
                                                     POOLNAME,
                                                      includeChildVOs,
                                                       voExclusionList);
    }
}
