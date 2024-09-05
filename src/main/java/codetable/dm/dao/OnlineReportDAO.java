/*
 * OnlineReportDAO.java      Version 2.0  05/18/2004
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
*/
package codetable.dm.dao;

import edit.common.vo.OnlineReportVO;
import edit.services.db.*;



public class OnlineReportDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public OnlineReportDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("OnlineReport");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Using the input of report category and productStructurePK access the OnlineReport and FilteredOnlineReport  tables,
     * using a join.
     * @param reportCategory
     * @param productStructurePK
     * @return OnlineReportVO[]
     * @throws Exception
     */
    public OnlineReportVO[] findOnlineReportForCategory(String reportCategory, long productStructurePK)
    {
        DBTable filteredOnlineReportDBTable = DBTable.getDBTableForTable("FilteredOnlineReport");

        String filteredOnlineReportTable = filteredOnlineReportDBTable.getFullyQualifiedTableName();

        String reportCategoryCTCol = DBTABLE.getDBColumn("ReportCategoryCT").getFullyQualifiedColumnName();
        String onlineReportPKCol   = DBTABLE.getDBColumn("OnlineReportPK").getFullyQualifiedColumnName();

        String productStructureFKCol = filteredOnlineReportDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String onlineReportFKCol     = filteredOnlineReportDBTable.getDBColumn("OnlineReportFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + ", " + filteredOnlineReportTable +
                     " WHERE " + reportCategoryCTCol + " = '" + reportCategory + "'" +
                     " AND " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + onlineReportPKCol + " = " + onlineReportFKCol;

        return (OnlineReportVO[]) executeQuery(OnlineReportVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Get the requested OnlineReport record
     * @param onlineReportPK
     * @return
     * @throws Exception
     */
    public OnlineReportVO[] findSpecificOnlineReportEntry(long onlineReportPK)
    {
        String onlineReportPKCol = DBTABLE.getDBColumn("OnlineReportPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + onlineReportPKCol + " = " + onlineReportPK;

        return (OnlineReportVO[]) executeQuery(OnlineReportVO.class,
                                                sql,
                                                 POOLNAME,
                                                  true,
                                                   null);
    }

    /**
     * Get all the OnlineReport records
     * @return  OnlineReportVO array
     * @throws Exception
     */
    public OnlineReportVO[] findAllOnlineReportVOs()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (OnlineReportVO[]) executeQuery(OnlineReportVO.class,
                                                sql,
                                                 POOLNAME,
                                                  true,
                                                   null);
    }
}