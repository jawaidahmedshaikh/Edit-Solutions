/*
 * User: gfrosti
 * Date: Nov 17, 2004
 * Time: 10:55:10 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance.dm.dao;

import edit.common.vo.*;

import edit.services.db.*;


public class TreatyGroupDAO extends DAO
{
    private final DBTable DBTABLE;
    private final String POOLNAME;
    private final String TABLENAME;

    public TreatyGroupDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("TreatyGroup");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @return
     */
    public TreatyGroupVO[] findAllTreatyGroupVOs()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (TreatyGroupVO[]) executeQuery(TreatyGroupVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param groupNumber
     * @return
     */
    public TreatyGroupVO[] findBy_GroupNumber(String groupNumber)
    {
        String groupNumberCol = DBTABLE.getDBColumn("TreatyGroupNumber").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + groupNumberCol + " = '" + groupNumber + "'";

        return (TreatyGroupVO[]) executeQuery(TreatyGroupVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param productStructurePK
     */
    public TreatyGroupVO[] findBy_ProductStructurePK(long productStructurePK)
    {
        DBTable filteredTreatyGroupDBTable = DBTable.getDBTableForTable("FilteredTreatyGroup");
        String filteredTreatyGroupTable = filteredTreatyGroupDBTable.getFullyQualifiedTableName();
        String productStructureFKCol = filteredTreatyGroupDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String treatyGroupFKCol = filteredTreatyGroupDBTable.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();

        String treatyGroupPKCol = DBTABLE.getDBColumn("TreatyGroupPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " INNER JOIN " + filteredTreatyGroupTable +
                " ON " + treatyGroupPKCol + " = " + treatyGroupFKCol +
                " WHERE " + productStructureFKCol + " = " + productStructurePK;

        return (TreatyGroupVO[]) executeQuery(TreatyGroupVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param treatyGroupPK
     * @param segmentPK
     * @return
     */
    public TreatyGroupVO[] findTreatyGroupBy_TreatyGroupPK_SegmentPK(long treatyGroupPK, long segmentPK)
    {
        String treatyGroupPKCol = DBTABLE.getDBColumn("TreatyGroupPK").getFullyQualifiedColumnName();

        DBTable treatyDBTable = DBTable.getDBTableForTable("Treaty");
        String treatyTable = treatyDBTable.getFullyQualifiedTableName();
        String treatyPKCol = treatyDBTable.getDBColumn("TreatyPK").getFullyQualifiedColumnName();
        String treatyGroupFKCol = treatyDBTable.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();

        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String treatyFKCol = contractTreatyDBTable.getDBColumn("TreatyFK").getFullyQualifiedColumnName();
        String segmentFKCol = contractTreatyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        String segmentTable = segmentDBTable.getFullyQualifiedTableName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + treatyTable +
                " ON " + treatyGroupPKCol + " = " + treatyGroupFKCol +
                " INNER JOIN " + contractTreatyTable +
                " ON " + treatyPKCol + " = " + treatyFKCol +
                " INNER JOIN " + segmentTable +
                " ON " + segmentFKCol + " = " + segmentPKCol +
                " WHERE " + treatyGroupPKCol + " = " + treatyGroupPK +
                " AND " + segmentPKCol + " = " + segmentPK;

        return (TreatyGroupVO[]) executeQuery(TreatyGroupVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param groupNumber
     * @return
     */
    public TreatyGroupVO[] findBy_PK(long treatyGroupPK)
    {
        String treatyGroupPKCol = DBTABLE.getDBColumn("TreatyGroupPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + treatyGroupPKCol + " = " + treatyGroupPK;

        return (TreatyGroupVO[]) executeQuery(TreatyGroupVO.class, sql, POOLNAME, false, null);
    }
}

