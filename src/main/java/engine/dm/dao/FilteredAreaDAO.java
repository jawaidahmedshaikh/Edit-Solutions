/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 4, 2002
 * Time: 10:08:38 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.FilteredAreaVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class FilteredAreaDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public FilteredAreaDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("FilteredArea");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public FilteredAreaVO[] findByAreaPK(long areaPK)
    {
        String areaFKCol = DBTABLE.getDBColumn("AreaFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + areaFKCol + " = " + areaPK;

        return (FilteredAreaVO[]) executeQuery(FilteredAreaVO.class,
                                            sql,
                                            POOLNAME,
                                            false,
                                            null);
    }

    public FilteredAreaVO[] findByProductStructurePK(long productStructurePK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK;

        return (FilteredAreaVO[]) executeQuery(FilteredAreaVO.class,
                                            sql,
                                            POOLNAME,
                                            false,
                                            null);
    }

    public FilteredAreaVO[] findByProductStructurePK_AND_AreaCT(long productStructurePK, String areaCT)
    {
        DBTable areaDBTable = DBTable.getDBTableForTable("Area");

        String areaTable = areaDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String areaFKCol             = DBTABLE.getDBColumn("AreaFK").getFullyQualifiedColumnName();

        String areaCTCol = areaDBTable.getDBColumn("AreaCT").getFullyQualifiedColumnName();
        String areaPKCol = areaDBTable.getDBColumn("AreaPK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* " +
                     " FROM " + TABLENAME + ", " + areaTable +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + areaCTCol + " = '" + areaCT + "'" +
                     " AND " + areaPKCol + " = " + areaFKCol;

        return (FilteredAreaVO[]) executeQuery(FilteredAreaVO.class,
                                            sql,
                                            POOLNAME,
                                            false,
                                            null);
    }

    public FilteredAreaVO[] findByAreaCT_AND_OverrideStatus(String areaCT, String overrideStatus)
    {
        DBTable areaDBTable = DBTable.getDBTableForTable("Area");

        String areaTable = areaDBTable.getFullyQualifiedTableName();

        String areaCTCol         = areaDBTable.getDBColumn("AreaCT").getFullyQualifiedColumnName();
        String overrideStatusCol = areaDBTable.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + areaTable +
                     " WHERE " + areaCTCol + " = '" + areaCT + "'" +
                     " AND " + overrideStatusCol + " = '" + overrideStatus + "'";

        return (FilteredAreaVO[]) executeQuery(FilteredAreaVO.class,
                                            sql,
                                            POOLNAME,
                                            false,
                                            null);
    }
}