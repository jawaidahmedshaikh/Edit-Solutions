/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.BucketVO;
import edit.common.vo.AnnualizedSubBucketVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;
import java.util.ArrayList;

public class BucketDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public BucketDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Bucket");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public BucketVO[] findByBucketPK(long bucketPK, boolean includeChildVOs, List voExclusionList)
    {
        String bucketPKCol = DBTABLE.getDBColumn("BucketPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + bucketPKCol + " = " + bucketPK;

        return (BucketVO[]) executeQuery(BucketVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
    }

    public BucketVO[] findByInvestmentFK(long investmentFK, boolean includeChildVOs, List voExclusionList)
    {
        String investmentFKCol = DBTABLE.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + investmentFKCol + " = " + investmentFK;

        return (BucketVO[]) executeQuery(BucketVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
     }

      public AnnualizedSubBucketVO[] findSubBucketByEDITTrxFK(long editTrxFK) throws Exception
      {
          DBTable annualizedSubBucketTable = DBTable.getDBTableForTable("AnnualizedSubBucket");
          String annualizedSubBucketTableName = annualizedSubBucketTable.getFullyQualifiedTableName();
          String editTrxFKCol = annualizedSubBucketTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

          String sql = "SELECT * FROM " + annualizedSubBucketTableName +
                       " WHERE " + editTrxFKCol + " = " + editTrxFK;

          return (AnnualizedSubBucketVO[]) executeQuery(AnnualizedSubBucketVO.class,
                                                        sql,
                                                        POOLNAME,
                                                        false,
                                                        new ArrayList());
       }
  }