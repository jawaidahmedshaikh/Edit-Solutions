/*
 * User: gfrosti
 * Date: Nov 18, 2004
 * Time: 8:17:31 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance.dm.dao;

import edit.common.vo.*;

import edit.services.db.*;

public class TreatyDAO extends DAO
{
    private final DBTable DBTABLE;
    private final String POOLNAME;
    private final String TABLENAME;

    /**
     * *********************************** Constructor Methods *************************************
     */
    public TreatyDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("Treaty");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /************************************** Public Methods **************************************/
    /**
     * Finder.
     * @param productStructurePK
     * @return
     */
    public TreatyVO[] findBy_ProductStructurePK(long productStructurePK)
    {
        DBTable treatyGroupDBTable = DBTable.getDBTableForTable("TreatyGroup");
        DBTable filteredTreatyGroupDBTable = DBTable.getDBTableForTable("FilteredTreatyGroup");

        String treatyGroupTable = treatyGroupDBTable.getFullyQualifiedTableName();
        String filteredTreatyGroupTable = filteredTreatyGroupDBTable.getFullyQualifiedTableName();

        String treatyTreatyGroupFKCol = DBTABLE.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();
        String treatyGroupPKCol = treatyGroupDBTable.getDBColumn("TreatyGroupPK").getFullyQualifiedColumnName();
        String filteredTreatyGroupTreatyGroupFKCol = filteredTreatyGroupDBTable.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();
        String productStructureFKCol = filteredTreatyGroupDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + treatyGroupTable +
                " ON " + treatyTreatyGroupFKCol + " = " + treatyGroupPKCol +
                " INNER JOIN " + filteredTreatyGroupTable +
                " ON " + treatyGroupPKCol + " = " + filteredTreatyGroupTreatyGroupFKCol +
                " WHERE " + productStructureFKCol + " = " + productStructurePK;

        return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param treatyGroupPK
     * @return
     */
    public TreatyVO[] findBy_TreatyGroupPK(long treatyGroupPK)
    {
        DBTable treatyGroupDBTable = DBTable.getDBTableForTable("TreatyGroup");

        String treatyGroupTable = treatyGroupDBTable.getFullyQualifiedTableName();

        String treatyGroupFKCol = DBTABLE.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();
        String treatyGroupPKCol = treatyGroupDBTable.getDBColumn("TreatyGroupPK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + treatyGroupTable +
                " ON " + treatyGroupFKCol + " = " + treatyGroupPKCol +
                " WHERE " + treatyGroupPKCol + " = " + treatyGroupPK;

        return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param treatyGroupPK
     * @return
     */
    public TreatyVO[] findBy_TreatyGroupPK_SegmentPK(long treatyGroupPK, long segmentPK)
    {
        String treatyGroupFKCol = DBTABLE.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();
        String treatyPKCol = DBTABLE.getDBColumn("TreatyPK").getFullyQualifiedColumnName();

        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String segmentFKCol = contractTreatyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String treatyFKCol = contractTreatyDBTable.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + contractTreatyTable +
                " ON " + treatyPKCol + " = " + treatyFKCol +
                " WHERE " + treatyGroupFKCol + " = " + treatyGroupPK +
                " AND " + segmentFKCol + " = " + segmentPK;

        return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param reinsurerNumber
     * @return
     */
    public TreatyVO[] findBy_ReinsurerNumber(String reinsurerNumber)
    {
        DBTable reinsurerDBTable = DBTable.getDBTableForTable("Reinsurer");
        String reinsurerTable = reinsurerDBTable.getFullyQualifiedTableName();
        String reinurerPKCol = reinsurerDBTable.getDBColumn("ReinsurerPK").getFullyQualifiedColumnName();
        String reinsurerNumberCol = reinsurerDBTable.getDBColumn("ReinsurerNumber").getFullyQualifiedColumnName();

        String reinsurerFKCol = DBTABLE.getDBColumn("ReinsurerFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + reinsurerTable +
                " ON " + reinsurerFKCol + " = " + reinurerPKCol +
                " WHERE " + reinsurerNumberCol + " = '" + reinsurerNumber + "'";

        return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param contractTreatyPK
     * @return
     */
    public TreatyVO[] findBy_ContractTreatyPK(long contractTreatyPK)
    {
        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String treatyFKCol = contractTreatyDBTable.getDBColumn("TreatyFK").getFullyQualifiedColumnName();
        String contractTreatyPKCol = contractTreatyDBTable.getDBColumn("ContractTreatyPK").getFullyQualifiedColumnName();

        String treatyPKCol = DBTABLE.getDBColumn("TreatyPK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + contractTreatyTable +
                " ON " + treatyPKCol + " = " + treatyFKCol +
                " WHERE " + contractTreatyPKCol + " = " + contractTreatyPK;

        return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finds all TreatyVOs that have a ReinsuranceProcess greater than the specified amount.
     * @param reinsurerBalance
     * @return
     */
    public TreatyVO[] findBy_ReinsurerBalance_GT(double reinsurerBalance)
    {
        String reinsurerBalanceCol = DBTABLE.getDBColumn("ReinsurerBalance").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + reinsurerBalanceCol + " > " + reinsurerBalance;

        return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finds all TreatyVOs that have a ReinsuranceProcess greater than the specified amount for the specified
     * ProductStructure.
     * @param reinsurerBalance
     * @return
     */
    public TreatyVO[] findBy_ReinsurerBalance_GT_ProductStructurePK(double reinsurerBalance, long productStructurePK)
    {
        String reinsurerBalanceCol = DBTABLE.getDBColumn("ReinsurerBalance").getFullyQualifiedColumnName();
        String treatyPKCol = DBTABLE.getDBColumn("TreatyPK").getFullyQualifiedColumnName();

        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String treatyFKCol = contractTreatyDBTable.getDBColumn("TreatyFK").getFullyQualifiedColumnName();
        String segmentFKCol = contractTreatyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        String segmentTable = segmentDBTable.getFullyQualifiedTableName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String productStructureFKCol = segmentDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + contractTreatyTable +
                " ON " + treatyPKCol + " = " + treatyFKCol +
                " INNER JOIN " + segmentTable +
                " ON " + segmentFKCol + " = " + segmentPKCol +
                " WHERE " + reinsurerBalanceCol + " > " + reinsurerBalance +
                " AND " + productStructureFKCol + " = " +  productStructurePK;

        return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
      * Finder.
      * @param treatyGroupPK
      * @return
      */
     public TreatyVO[] findBy_PK(long treatyPK)
     {
         String treatyPKCol = DBTABLE.getDBColumn("TreatyPK").getFullyQualifiedColumnName();

         String sql;
         sql = " SELECT " + TABLENAME + ".*" +
                 " FROM " + TABLENAME +
                 " WHERE " + treatyPKCol + " = " + treatyPK;

         return (TreatyVO[]) executeQuery(TreatyVO.class, sql, POOLNAME, false, null);
     }

}
