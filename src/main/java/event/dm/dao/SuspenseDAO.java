/*
 * SuspenseDAO.java   Version 1.1   10/10/2001
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.*;
import edit.services.db.*;

import java.sql.*;
import java.util.*;

public class SuspenseDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    private final DBTable EDITTRX_HISTORY_DBTABLE;
    private final DBTable INSUSPENSE_DBTABLE;

    private final String EDITTRX_HISTORY_TABLENAME;
    private final String INSUSPENSE_TABLENAME;

    public SuspenseDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Suspense");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();

        EDITTRX_HISTORY_DBTABLE = DBTable.getDBTableForTable("EDITTrxHistory");
        INSUSPENSE_DBTABLE = DBTable.getDBTableForTable("InSuspense");

        EDITTRX_HISTORY_TABLENAME = EDITTRX_HISTORY_DBTABLE.getFullyQualifiedTableName();
        INSUSPENSE_TABLENAME = INSUSPENSE_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Retrieves the Suspense record whose SuspensePK matches the suspensePK parameter value
     * @param suspensePK
     * @return
     */
    public SuspenseVO[] findBySuspensePK(long suspensePK)
    {
        String suspensePKCol = DBTABLE.getDBColumn("SuspensePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + suspensePKCol + " = " + suspensePK;

        return (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                                null);
    }

    /**
     * Retrieves all Suspense records whose UserDefNumber matches the userDefNumber parameter value
     * @param userDefNumber
     * @return
     */
    public SuspenseVO[] findByUserDefNumber(String userDefNumber)
    {
        String userDefNumberCol = DBTABLE.getDBColumn("UserDefNumber").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + userDefNumberCol + ") = UPPER('" + userDefNumber + "')";

        return (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                                null);

    }

    /**
     * Retrieves all Suspense records
     * @return
     */
    public SuspenseVO[] findAll()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                                null);
    }

    /**
     * Retrieves all SuspenseVO records whose SuspenseType = "Batch"
     * @return
     */
    public SuspenseVO[] findAllBatchSuspense()
    {
        String suspenseTypeCol = DBTABLE.getDBColumn("SuspenseType").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + suspenseTypeCol + " = 'Batch'" +
                     " ORDER BY " + effectiveDateCol + " ASC";

        return (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                                null);
    }

    /**
     * Retrieves all Suspense records whose ProcessDate <= the processDate parameter value and whose
     * AccountingPendingInd = 'Y'
     * @param processDate
     * @return
     */
    public SuspenseVO[] findAccountingPendingSuspenseEntries(String processDate)
    {
        String processDateCol = DBTABLE.getDBColumn("ProcessDate").getFullyQualifiedColumnName();
        String accountingPendingIndCol = DBTABLE.getDBColumn("AccountingPendingInd").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + processDateCol + " <= ?" +
                     " AND " + accountingPendingIndCol + " = 'Y'";


        Connection conn = null;

        PreparedStatement ps = null;

        SuspenseVO[] suspenseVOs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(processDate));

            suspenseVOs = (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                                     ps,
                                                      POOLNAME,
                                                       false,
                                                        null);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
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
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return suspenseVOs;


    }

    /**
     * Retrieves all Suspense records whose AccountingPendingInd = 'N' and whose MaintenanceInd = 'D'
     * @return
     */
    public SuspenseVO[] findSuspenseToDelete()
    {
        String maintenanceIndCol = DBTABLE.getDBColumn("MaintenanceInd").getFullyQualifiedColumnName();
        String accountingPendingIndCol = DBTABLE.getDBColumn("AccountingPendingInd").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + maintenanceIndCol + " IN ('D', 'U') AND " +
                       accountingPendingIndCol + " = 'N'";

        return (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                                null);
    }

    /**
     * Retrieves all Suspense records whose DirectionCT matches the direction parameter value and whose
     * maintenanceInd value = 'D'
     * @param direction
     * @return
     */
    public SuspenseVO[] findAllByDirection(String direction)
    {
        String directionCTCol = DBTABLE.getDBColumn("DirectionCT").getFullyQualifiedColumnName();
        String maintenanceIndCol = DBTABLE.getDBColumn("MaintenanceInd").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + directionCTCol + " = '" + direction + "'" +
                     " AND NOT " + maintenanceIndCol + " = 'D'";

        return (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                                null);
    }

    /**
     * Retrieves all Suspense records whose FilteredFundFK matches the filteredFundFK parameter value
     * @param filteredFundFK
     * @return
     */
    public SuspenseVO[] findAllByFilteredFundFK(long filteredFundFK)
    {
        DBTable cashBatchDBTable   = DBTable.getDBTableForTable("CashBatchContract");
        String cashBatchTableName = cashBatchDBTable.getFullyQualifiedTableName();

        String filteredFundFKCol = DBTABLE.getDBColumn("FilteredFundFK").getFullyQualifiedColumnName();
        String cashBatchContractFKCol = DBTABLE.getDBColumn("CashBatchContractFK").getFullyQualifiedColumnName();
        String maintenanceIndCol = DBTABLE.getDBColumn("MaintenanceInd").getFullyQualifiedColumnName();
        String cashBatchContractPKCol = cashBatchDBTable.getDBColumn("CashBatchContractPK").getFullyQualifiedColumnName();
        String releaseIndicatorCol = cashBatchDBTable.getDBColumn("ReleaseIndicator").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " LEFT JOIN " + cashBatchTableName +
                     " ON " + cashBatchContractFKCol + " = " + cashBatchContractPKCol +
                     " AND " + releaseIndicatorCol + " = 'R'" +
                     " WHERE " + filteredFundFKCol + " = " + filteredFundFK +
                     " AND " + maintenanceIndCol + " NOT IN ('V')";

        return (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
    }


    public SuspenseVO[] findBy_EDITTrxFK(long editTrxFK, CRUD crud)
    {
        // EDITTrxHistory
        String editTrxFKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol = EDITTRX_HISTORY_DBTABLE.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();

        //Suspense
        String maintenanceIndCol = DBTABLE.getDBColumn("MaintenanceInd").getFullyQualifiedColumnName();
        String suspensePKCol       = DBTABLE.getDBColumn("SuspensePK").getFullyQualifiedColumnName();

        //InSuspense
        String suspenseFKCol = INSUSPENSE_DBTABLE.getDBColumn("SuspenseFK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = INSUSPENSE_DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        List voExclusionList = new ArrayList();
        voExclusionList.add(OutSuspenseVO.class);
        voExclusionList.add(EDITTrxHistoryVO.class);

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " +TABLENAME +
                " INNER JOIN " + INSUSPENSE_TABLENAME +
                " ON " + suspenseFKCol + " = " + suspensePKCol +
                " INNER JOIN " + EDITTRX_HISTORY_TABLENAME +
                " ON " + editTrxHistoryPKCol + " = " + editTrxHistoryFKCol +
                " WHERE " + editTrxFKCol + " = ?" +
                " AND " + maintenanceIndCol + " NOT IN ('D')";


        SuspenseVO[] suspenseVOs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try
        {
            if (crud == null)
            {
                conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
            }
            else
            {
                conn = crud.getCrudConn();
            }

            ps = conn.prepareStatement(sql);

            ps.setLong(1, editTrxFK);

            suspenseVOs = (SuspenseVO[]) executeQuery(SuspenseVO.class,
                                                                  ps,
                                                                  POOLNAME,
                                                                  true,
                                                                  voExclusionList);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
              if (ps != null) ps.close();
              ps = null;
            //DO NOT close the connection passed in
            if (crud == null)
            {
                if (conn != null) conn.close();
            }
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return suspenseVOs;
    }
}