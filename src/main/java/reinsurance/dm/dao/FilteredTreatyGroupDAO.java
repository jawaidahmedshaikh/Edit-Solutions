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


public class FilteredTreatyGroupDAO extends DAO
{
    private final DBTable DBTABLE;
    private final String POOLNAME;
    private final String TABLENAME;

    public FilteredTreatyGroupDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("FilteredTreatyGroup");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @return
     */
    public FilteredTreatyGroupVO[] findBy_TreatyGroupPK(long treatyPK)
    {
        String treatyFKCol = DBTABLE.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * FROM " + TABLENAME +
                " WHERE " + treatyFKCol + " = " + treatyPK;

        return (FilteredTreatyGroupVO[]) executeQuery(FilteredTreatyGroupVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param treatyGroupPK
     * @return
     */
    public FilteredTreatyGroupVO[] findBy_ProductStructurePK_TreatyGroupPK(long productStructurePK, long treatyGroupPK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String treatyGroupFKCol = DBTABLE.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * FROM " + TABLENAME +
                " WHERE " + productStructureFKCol + " = " + productStructurePK +
                " AND " + treatyGroupFKCol + " = " + treatyGroupPK;

        return (FilteredTreatyGroupVO[]) executeQuery(FilteredTreatyGroupVO.class, sql, POOLNAME, false, null);
    }
}

