/*
 * User: gfrosti
 * Date: Nov 4, 2002
 * Time: 10:08:38 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.*;

import edit.services.db.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class AreaValueDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AreaValueDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("AreaValue");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param grouping
     * @return
     */
    public AreaValueVO[] findBy_ProductStructurePK_Grouping(long productStructurePK, String grouping)
    {
        DBTable filteredAreaValueDBTable = DBTable.getDBTableForTable("FilteredAreaValue");
        DBTable areaKeyDBTable           = DBTable.getDBTableForTable("AreaKey");

        String filteredAreaValueTable = filteredAreaValueDBTable.getFullyQualifiedTableName();
        String areaKeyTable           = areaKeyDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = filteredAreaValueDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String areaValueFKCol        = filteredAreaValueDBTable.getDBColumn("AreaValueFK").getFullyQualifiedColumnName();

        String areaValuePKCol = DBTABLE.getDBColumn("AreaValuePK").getFullyQualifiedColumnName();
        String areaKeyFKCol          = DBTABLE.getDBColumn("AreaKeyFK").getFullyQualifiedColumnName();

        String areaKeyPKCol = areaKeyDBTable.getDBColumn("AreaKeyPK").getFullyQualifiedColumnName();
        String groupingCol  = areaKeyDBTable.getDBColumn("Grouping").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".*" +
                     " FROM " + filteredAreaValueTable +
                     " INNER JOIN " + TABLENAME +
                     " ON " + areaValueFKCol + " = " + areaValuePKCol +
                     " INNER JOIN " + areaKeyTable +
                     " ON " + areaKeyFKCol + " = " + areaKeyPKCol +
                     " WHERE ( " + productStructureFKCol + " = " + productStructurePK + ")" +
                     " AND ( " + groupingCol + " = '" + grouping + "')";

        return (AreaValueVO[]) executeQuery(AreaValueVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param grouping
     * @param field
     * @return
     */
    public AreaValueVO[] findBy_ProductStructurePK_Grouping_Field(long productStructurePK, String grouping, String field)
    {
        DBTable filteredAreaValueDBTable = DBTable.getDBTableForTable("FilteredAreaValue");
        DBTable areaKeyDBTable           = DBTable.getDBTableForTable("AreaKey");

        String filteredAreaValueTable = filteredAreaValueDBTable.getFullyQualifiedTableName();
        String areaKeyTable           = areaKeyDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = filteredAreaValueDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String areaValueFKCol        = filteredAreaValueDBTable.getDBColumn("AreaValueFK").getFullyQualifiedColumnName();
        String areaKeyFKCol          = DBTABLE.getDBColumn("AreaKeyFK").getFullyQualifiedColumnName();

        String areaValuePKCol = DBTABLE.getDBColumn("AreaValuePK").getFullyQualifiedColumnName();

        String areaKeyPKCol = areaKeyDBTable.getDBColumn("AreaKeyPK").getFullyQualifiedColumnName();
        String groupingCol  = areaKeyDBTable.getDBColumn("Grouping").getFullyQualifiedColumnName();
        String fieldCol     = areaKeyDBTable.getDBColumn("Field").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".*" +
                     " FROM " + filteredAreaValueTable +
                     " INNER JOIN " + TABLENAME +
                     " ON " + areaValueFKCol + " = " + areaValuePKCol +
                     " INNER JOIN " + areaKeyTable +
                     " ON " + areaKeyFKCol + " = " + areaKeyPKCol +
                     " WHERE ( " + productStructureFKCol + " = " + productStructurePK + ")" +
                     " AND ( " + groupingCol + " = '" + grouping + "')" +
                     " AND (" + fieldCol + " = '" + field + "')";

        return (AreaValueVO[]) executeQuery(AreaValueVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param areaKeyPK
     * @return
     */
    public AreaValueVO[] findBy_AreaKeyPK(long areaKeyPK)
    {
        String areaKeyFKCol = DBTABLE.getDBColumn("AreaKeyFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + areaKeyFKCol + " = " + areaKeyPK;

        return (AreaValueVO[]) executeQuery(AreaValueVO.class, sql, POOLNAME, false, null);
    }

    public AreaValueVO[] findByPK(long areaValuePK)
    {
        String areaValuePKCol = DBTABLE.getDBColumn("AreaValuePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + " WHERE " + areaValuePKCol + " = " + areaValuePK;

        return (AreaValueVO[]) executeQuery(AreaValueVO.class,  sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @return
     */
    public AreaValueVO[] findBy_ProductStructurePK(long productStructurePK)
    {
        DBTable filteredAreaValueDBTable = DBTable.getDBTableForTable("FilteredAreaValue");

        String filteredAreaValueTable = filteredAreaValueDBTable.getFullyQualifiedTableName();

        String productStructureFKCol = filteredAreaValueDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String areaValueKeyFKCol     = filteredAreaValueDBTable.getDBColumn("AreaValueFK").getFullyQualifiedColumnName();

        String areaValueKeyPKCol = DBTABLE.getDBColumn("AreaValuePK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".*" +
                     " FROM " + filteredAreaValueTable +
                     " INNER JOIN " + TABLENAME +
                     " ON " + areaValueKeyFKCol + " = " + areaValueKeyPKCol +
                     " WHERE ( " + productStructureFKCol + " = " + productStructurePK + ")";

        return (AreaValueVO[]) executeQuery(AreaValueVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param areaKeyPK
     * @param areaCT
     * @param areaValue
     * @param effectiveDate
     * @param qualifierCT
     * @return
     */
    public AreaValueVO[] findBy_AreaKeyPK_AreaCT_AreaValue(long areaKeyPK,
                                                           String areaCT,
                                                           String areaValue,
                                                           String effectiveDate,
                                                           String qualifierCT)
    {
        Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        PreparedStatement ps = null;

        AreaValueVO[] areaValueVOs = null;

        DBTable areaKeyDBTable = DBTable.getDBTableForTable("AreaKey");
        String areaKeyTable = areaKeyDBTable.getFullyQualifiedTableName();
        String areaKeyPKCol = areaKeyDBTable.getDBColumn("AreaKeyPK").getFullyQualifiedColumnName();

        String areaKeyFKCol = DBTABLE.getDBColumn("AreaKeyFK").getFullyQualifiedColumnName();
        String areaCTCol = DBTABLE.getDBColumn("AreaCT").getFullyQualifiedColumnName();
        String areaValueCol = DBTABLE.getDBColumn("AreaValue").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String qualifierCTCol = DBTABLE.getDBColumn("QualifierCT").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".*" +
                     " FROM " + TABLENAME +
                     " INNER JOIN " + areaKeyTable +
                     " ON " + areaKeyFKCol + " = " + areaKeyPKCol +
                     " WHERE " + areaCTCol + " = ?" +
                     " AND " + areaValueCol + " = ?" +
                     " AND " + areaKeyFKCol + " = ?" +
                     " AND " + effectiveDateCol + " = ?" +
                     " AND " + qualifierCTCol + " = ?";

        try
        {
            ps = conn.prepareStatement(sql);

            ps.setString(1, areaCT);
            ps.setString(2, areaValue);
            ps.setLong(3, areaKeyPK);
            ps.setDate(4, DBUtil.convertStringToDate(effectiveDate));
            ps.setString(5, qualifierCT);

            areaValueVOs = (AreaValueVO[]) executeQuery(AreaValueVO.class,
                                                         ps,
                                                          POOLNAME,
                                                           false,
                                                            null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();

              ps = null;

              if (conn != null) conn.close();

              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return areaValueVOs;
    }
}
