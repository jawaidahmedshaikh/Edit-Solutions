/*
 * User: gfrosti
 * Date: Oct 28, 2004
 * Time: 11:21:17 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.dm.dao;

import edit.services.db.*;
import edit.common.vo.*;


public class AreaKeyDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AreaKeyDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("AreaKey");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finds Areas by grouping and field.
     * @param grouping
     * @param field
     * @return
     */
    public AreaKeyVO[] findByGrouping_AND_Field(String grouping, String field)
    {

        String groupingCol = DBTABLE.getDBColumn("Grouping").getFullyQualifiedColumnName();

        String fieldCol = DBTABLE.getDBColumn("Field").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + groupingCol + " = '" + grouping + "'" +
                     " AND " + fieldCol + " = '" + field + "'";

        return (AreaKeyVO[]) executeQuery(AreaKeyVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @param areaPK
     * @return
     */
    public AreaKeyVO[] findBy_AreaPK(long areaPK)
    {
        String areaKeyPKCol = DBTABLE.getDBColumn("AreaKeyPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + areaKeyPKCol + " = " + areaPK;

        return (AreaKeyVO[]) executeQuery(AreaKeyVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @param grouping
     * @return
     */
    public AreaKeyVO[] findBy_Grouping(String grouping)
    {
        String groupingCol  = DBTABLE.getDBColumn("Grouping").getFullyQualifiedColumnName();

        String sql = " SELECT *" +
                     " FROM " + TABLENAME +
                     " WHERE ( " + groupingCol + " = '" + grouping + "')";

        return (AreaKeyVO[]) executeQuery(AreaKeyVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @return
     */
    public AreaKeyVO[] findAll()
    {
        String sql = " SELECT *" +
                     " FROM " + TABLENAME;

        return (AreaKeyVO[]) executeQuery(AreaKeyVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }
}
