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

import edit.common.vo.AreaVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class AreaDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AreaDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Area");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public AreaVO[] findByOverrideStatus(String overrideStatus)
    {
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + overrideStatusCol + " = '" + overrideStatus + "'";

        return (AreaVO[]) executeQuery(AreaVO.class,
                                            sql,
                                            POOLNAME,
                                            false,
                                            null);
    }

    public AreaVO[] findByAreaCT_AND_OverrideStatus(String areaCT, String overrideStatus)
    {
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();
        String areaCTCol         = DBTABLE.getDBColumn("AreaCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + overrideStatusCol + " = '" + overrideStatus + "'" +
                     " AND " + areaCTCol + " = '" + areaCT + "'";

        return (AreaVO[]) executeQuery(AreaVO.class,
                                            sql,
                                            POOLNAME,
                                            false,
                                            null);
    }

    public AreaVO[] findByProductStructurePK_AND_AreaCT(long productStructurePK, String areaCT)
    {
        /*
        " SELECT Area.* FROM Area, FilteredArea
        WHERE FilteredArea.AreaFK = Area.AreaPK
        AND Area.AreaCT = '" + areaCT + "'
        AND FilteredArea.ProductStructureFK = " + productStructurePK*/

        DBTable filteredAreaDBTable = DBTable.getDBTableForTable("FilteredArea");

        String filteredAreaTable = filteredAreaDBTable.getFullyQualifiedTableName();


        String areaPKCol = DBTABLE.getDBColumn("AreaPK").getFullyQualifiedColumnName();
        String areaCTCol = DBTABLE.getDBColumn("AreaCT").getFullyQualifiedColumnName();

        String areaFKCol             = filteredAreaDBTable.getDBColumn("AreaFK").getFullyQualifiedColumnName();
        String productStructureFKCol = filteredAreaDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + filteredAreaTable +
                     " WHERE " + areaFKCol + " = " + areaPKCol +
                     " AND " + areaCTCol + " = '" + areaCT + "'" +
                     " AND " + productStructureFKCol + " = " + productStructurePK;

        return (AreaVO[]) executeQuery(AreaVO.class,
                                            sql,
                                            POOLNAME,
                                            false,
                                            null);
    }
}