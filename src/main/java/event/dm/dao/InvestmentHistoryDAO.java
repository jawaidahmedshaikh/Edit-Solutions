/*
 * User: dlataill
 * Date: Nov 16, 2004
 * Time: 3:42:10 PM
 * <p/>
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.dm.dao;

import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.ConnectionFactory;
import edit.common.vo.InvestmentHistoryVO;

public class InvestmentHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public InvestmentHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("InvestmentHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public InvestmentHistoryVO[] findByEDITTrxHistoryFK(long editTrxHistoryFK)
    {
        String editTrxHistoryFKCol= DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String valuationDateCol = DBTABLE.getDBColumn("ValuationDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryFK +
                     " ORDER BY " + valuationDateCol + " ASC";


        return (InvestmentHistoryVO[]) executeQuery(InvestmentHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    /**
     * Finder.
     * @param investmentHistoryPK
     * @return
     */
    public InvestmentHistoryVO[] findByPK(long investmentHistoryPK)
    {
        String investmentHistoryPKCol = DBTABLE.getDBColumn("InvestmentHistoryPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + investmentHistoryPKCol + " = " + investmentHistoryPK;

        return (InvestmentHistoryVO[]) executeQuery(InvestmentHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public InvestmentHistoryVO[] findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (InvestmentHistoryVO[]) executeQuery(InvestmentHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public InvestmentHistoryVO[] findByEDITTrxHistoryPK_FinalPriceStatus(long editTrxHistoryPK, String finalPriceStatus)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String finalPriceStatusCol = DBTABLE.getDBColumn("FinalPriceStatus").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK +
                " AND " + finalPriceStatusCol + " = " + finalPriceStatus;

        return (InvestmentHistoryVO[]) executeQuery(InvestmentHistoryVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                         null);
    }
}
