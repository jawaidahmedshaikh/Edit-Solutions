/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.InvestmentVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class InvestmentDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public InvestmentDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Investment");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public InvestmentVO[] findByInvestmentPK(long investmentPK, boolean includeChildVOs, List voExclusionList)
    {
        String investmentPKCol = DBTABLE.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + investmentPKCol + " = " + investmentPK;

        return (InvestmentVO[]) executeQuery(InvestmentVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public InvestmentVO[] findByFilteredFundFKAndSegmentFK(long filteredFundFK,
                                                            long segmentFK,
                                                             boolean includeChildVOs,
                                                              List voExclusionList)
    {
        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String segmentFKCol      = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundFK +
                     " AND " + segmentFKCol + " = " + segmentFK;

        return (InvestmentVO[]) executeQuery(InvestmentVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public InvestmentVO[] findBySegmentPK(long segmentPK, boolean includeChildVOs, List voExclusionList)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK;

        return (InvestmentVO[]) executeQuery(InvestmentVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public InvestmentVO[] findBySegmentPKAndInvestmentAllocationOverrideStatus(long segmentPK,
                                                                                String overrideStatus,
                                                                                 boolean includeChildVOs,
                                                                                  List voExclusionList)
    {
        DBTable investmentAllocationDBTable = DBTable.getDBTableForTable("InvestmentAllocation");

        String investmentAllocationTable = investmentAllocationDBTable.getFullyQualifiedTableName();

        String segmentFKCol    = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String investmentPKCol = DBTABLE.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();
        String statusCol = DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();

        String investmentFKCol   = investmentAllocationDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();
        String overrideStatusCol = investmentAllocationDBTable.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + investmentAllocationTable +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND " + investmentPKCol + " = " + investmentFKCol +
                     " AND " + overrideStatusCol + " = '" + overrideStatus + "'" +
                     " AND " + statusCol + " IS NULL";

        return (InvestmentVO[]) executeQuery(InvestmentVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }

    public InvestmentVO[] findByBucketPK(long bucketPK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable bucketDBTable = DBTable.getDBTableForTable("Bucket");

        String bucketTable = bucketDBTable.getFullyQualifiedTableName();

        String investmentPKCol = DBTABLE.getDBColumn("InvestmentPK").getFullyQualifiedColumnName();

        String investmentFKCol = bucketDBTable.getDBColumn("InvestmentFK").getFullyQualifiedColumnName();
        String bucketPKCol     = bucketDBTable.getDBColumn("BucketPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + bucketTable +
                     " WHERE " + investmentFKCol +  " = " + investmentPKCol +
                     " AND " + bucketPKCol + " = " + bucketPK;

        return (InvestmentVO[]) executeQuery(InvestmentVO.class,
                                              sql,
                                               POOLNAME,
                                                includeChildVOs,
                                                 voExclusionList);
    }


}