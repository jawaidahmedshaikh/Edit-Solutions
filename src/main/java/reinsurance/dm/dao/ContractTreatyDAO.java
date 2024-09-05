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

public class ContractTreatyDAO extends DAO
{
    private final DBTable DBTABLE;
    private final String POOLNAME;
    private final String TABLENAME;

    /**
     * *********************************** Constructor Methods *************************************
     */
    public ContractTreatyDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("ContractTreaty");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /************************************** Public Methods **************************************/
    /**
     * Finder.
     * @param segmentPK
     * @param treatyPK
     * @return
     */
    public ContractTreatyVO[] findBy_SegmentPK_TreatyPK(long segmentPK, long treatyPK)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String treatyFKCol = DBTABLE.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + segmentFKCol + " = " + segmentPK +
                " AND " + treatyFKCol + " = " + treatyPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param casePK
     * @param treatyPK
     * @return
     */
    public ContractTreatyVO[] findBy_CasePK_TreatyPK(long casePK, long treatyPK)
    {
        String caseFKCol = DBTABLE.getDBColumn("CaseFK").getFullyQualifiedColumnName();
        String treatyFKCol = DBTABLE.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + caseFKCol + " = " + casePK +
                " AND " + treatyFKCol + " = " + treatyPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param casePK
     * @param segmentPK
     * @return
     */
    public ContractTreatyVO[] findBy_CasePK_SegmentPK_TreatyPK(long casePK, long segmentPK, long treatyPK)
    {
        String caseFKCol = DBTABLE.getDBColumn("CaseFK").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String treatyFKCol = DBTABLE.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + caseFKCol + " = " + casePK +
                " AND " + segmentFKCol + " = " + segmentPK +
                " AND " + treatyFKCol + " = " + treatyPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param casePK
     * @param segmentPK
     * @return
     */
    public ContractTreatyVO[] findBy_CasePK_SegmentPK(long casePK, long segmentPK)
    {
        String caseFKCol = DBTABLE.getDBColumn("CaseFK").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + caseFKCol + " = " + casePK +
                " AND " + segmentFKCol + " = " + segmentPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder. SegmentFK is assumed NULL.
     * @param casePK
     * @param treatyPK
     * @return
     */
    public ContractTreatyVO[] findBy_CasePK_SegmentPK_IS_NULL_TreatyPK(long casePK, long treatyPK)
    {
        String caseFKCol = DBTABLE.getDBColumn("CaseFK").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String treatyFKCol = DBTABLE.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + caseFKCol + " = " + casePK +
                " AND " + segmentFKCol + "IS NULL" +
                " AND " + treatyFKCol + " = " + treatyPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder
     * @param segmentPK
     */
    public ContractTreatyVO[] findBy_SegmentPK(long segmentPK)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + segmentFKCol + " = " + segmentPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param treatyPK
     * @return
     */
    public ContractTreatyVO[] findBy_TreatyPK(long treatyPK)
    {
        String treatyFKCol = DBTABLE.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + treatyFKCol + " = " + treatyPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param contractTreatyPK
     * @return
     */
    public ContractTreatyVO[] findBy_PK(long contractTreatyPK)
    {
        String contractTreatyPKCol = DBTABLE.getDBColumn("ContractTreatyPK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + contractTreatyPKCol + " = " + contractTreatyPK;

        return (ContractTreatyVO[]) executeQuery(ContractTreatyVO.class, sql, POOLNAME, false, null);
    }
}
