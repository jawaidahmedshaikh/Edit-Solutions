/*
 * FilteredOnlineReportDAO.java      Version 2.0  05/18/2004
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
*/
package codetable.dm.dao;

import edit.common.vo.FilteredOnlineReportVO;
import edit.services.db.*;


public class FilteredOnlineReportDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public FilteredOnlineReportDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("FilteredOnlineReport");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public FilteredOnlineReportVO[] findPKByOnlineReportPKAndProductStructure(long onlineReportPK, long productStructureFK)
    {
        String onlineReportFKCol     = DBTABLE.getDBColumn("OnlineReportFK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + onlineReportFKCol + " = " + onlineReportPK +
                     " AND " + productStructureFKCol + " = " + productStructureFK;

        return (FilteredOnlineReportVO[]) executeQuery(FilteredOnlineReportVO.class,
                                                        sql,
                                                         POOLNAME,
                                                          false,
                                                           null);
    }

    public FilteredOnlineReportVO[] findByPK(long filteredOnlineReportPK)
    {
        String filteredOnlineReportPKCol = DBTABLE.getDBColumn("FilteredOnlineReportPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + filteredOnlineReportPKCol + " = " + filteredOnlineReportPK;

        return (FilteredOnlineReportVO[]) executeQuery(FilteredOnlineReportVO.class,
                                                        sql,
                                                         POOLNAME,
                                                          false,
                                                           null);
    }

    public FilteredOnlineReportVO[] findByProductStructure(long productStructurePK)
    {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructureFKCol + " = " + productStructurePK;

        return (FilteredOnlineReportVO[]) executeQuery(FilteredOnlineReportVO.class,
                                                        sql,
                                                         POOLNAME,
                                                          false,
                                                           null);
    }

    /**
     * Get all the children of OnlineReport with the matching foreign key
     * @param OnlineReportPK
     * @return FilteredOnlineReportVO array
     * @throws Exception
     */
    public FilteredOnlineReportVO[] findByOnlineReportPK(long onlineReportPK)
    {
        String onlineReportFKCol = DBTABLE.getDBColumn("OnlineReportFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + onlineReportFKCol + " = " + onlineReportPK;

        return (FilteredOnlineReportVO[]) executeQuery(FilteredOnlineReportVO.class,
                                                        sql,
                                                         POOLNAME,
                                                          false,
                                                           null);
    }

    public FilteredOnlineReportVO[] getAllFilteredOnlineReportVOs()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (FilteredOnlineReportVO[]) executeQuery(FilteredOnlineReportVO.class,
                                                        sql,
                                                         POOLNAME,
                                                          false,
                                                           null);
    }
}