/*
 * User: gfrosti
 * Date: Nov 2, 2004
 * Time: 9:04:17 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package engine.dm.dao;

import edit.common.vo.*;
import edit.services.db.*;


public class FilteredAreaValueDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public FilteredAreaValueDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("FilteredAreaValue");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @param areaKeyPK
     * @return
     */
    public FilteredAreaValueVO[] findBy_AreaKeyPK(long areaKeyPK)
    {
        String areaValueFKCol = DBTABLE.getDBColumn("AreaValueFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + areaValueFKCol + " = " + areaKeyPK;

        return (FilteredAreaValueVO[]) executeQuery(FilteredAreaValueVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param areaValuePK
     * @return
     */
    public FilteredAreaValueVO[] findBy_ProductStructurePK_AreaValueKeyPK(long productStructurePK, long areaValuePK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String areaValueFKCol        = DBTABLE.getDBColumn("AreaValueFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + areaValueFKCol + " = " + areaValuePK +
                     " AND " + productStructureFKCol + " = " + productStructurePK;

        return (FilteredAreaValueVO[]) executeQuery(FilteredAreaValueVO.class, sql, POOLNAME, false, null);
    }
}
