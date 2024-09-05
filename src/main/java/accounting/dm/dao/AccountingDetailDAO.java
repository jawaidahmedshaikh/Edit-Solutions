/*
 * User: dlataill
 * Date: Jan 17, 2002
 * Time: 1:40:15 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package accounting.dm.dao;


import edit.common.vo.AccountingDetailVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AccountingDetailDAO extends DAO {
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AccountingDetailDAO() {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("AccountingDetail");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public AccountingDetailVO[] findDetailByCOandFromToDates(String coName, String fromDate, String toDate, String dateType) {
        AccountingDetailVO[] accountingDetailVOs;

        String sql;

        String accountingProcessDateCol = DBTABLE.getDBColumn("AccountingProcessDate").getFullyQualifiedColumnName();
        String companyNameCol = DBTABLE.getDBColumn("CompanyName").getFullyQualifiedColumnName();
        String accountNumberCol = DBTABLE.getDBColumn("AccountNumber").getFullyQualifiedColumnName();
        String fundNumberCol = DBTABLE.getDBColumn("FundNumber").getFullyQualifiedColumnName();
        String transactionCodeCol = DBTABLE.getDBColumn("TransactionCode").getFullyQualifiedColumnName();
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();

        String dateColumnToUse = accountingProcessDateCol;
        if (dateType.startsWith("Accounting")) {
            dateColumnToUse = accountingPeriodCol;
        }

        if (coName.equalsIgnoreCase("All")) {
            sql = "SELECT * FROM " + TABLENAME +
                    " WHERE " + dateColumnToUse + " >= ?" +
                    " AND " + dateColumnToUse + " <= ?";
        } else {
            sql = "SELECT * FROM " + TABLENAME +
                    " WHERE " + companyNameCol + " = '" + coName + "'" +
                    " AND " + dateColumnToUse + " >= ?" +
                    " AND " + dateColumnToUse + " <= ?";
        }

        sql = sql + " ORDER BY " + companyNameCol + ", " + accountNumberCol + ", " + transactionCodeCol + ", " + fundNumberCol + " ASC";


        try (Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME)) {

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, DBUtil.convertStringToDate(fromDate));
                ps.setDate(2, DBUtil.convertStringToDate(toDate));

                accountingDetailVOs = (AccountingDetailVO[]) executeQuery(AccountingDetailVO.class,
                        ps,
                        POOLNAME,
                        false,
                        null);
            }
        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return accountingDetailVOs;
    }

    public AccountingDetailVO[] findByPendingStatus(String accountingPendingStatus) {
        String accountingPendingStatusCol = DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + accountingPendingStatusCol + " = '" + accountingPendingStatus + "'";

        return (AccountingDetailVO[]) executeQuery(AccountingDetailVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public AccountingDetailVO[] findByAccountingPeriod(String accountingPeriod) {
        String accountingPeriodCol = DBTABLE.getDBColumn("AccountingPeriod").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + accountingPeriodCol + " = '" + accountingPeriod + "'";

        return (AccountingDetailVO[]) executeQuery(AccountingDetailVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @param accountingDetailPK
     * @return
     */
    public AccountingDetailVO[] findByPK(long accountingDetailPK) {
        // AccountingDetail
        String accountingDetailPKCol = DBTABLE.getDBColumn("AccountingDetailPK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + accountingDetailPKCol + " = " + accountingDetailPK;

        return (AccountingDetailVO[]) executeQuery(AccountingDetailVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @param contractNumber
     * @param processDate
     * @return
     */
    public AccountingDetailVO[] findBy_ContractNumber_ProcessDateGTE(String contractNumber, String processDate) {
        AccountingDetailVO[] accountingDetailVOs;

        String contractNumberCol = DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String processDateCol = DBTABLE.getDBColumn("ProcessDate").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + contractNumberCol + " = '" + contractNumber + "'" +
                " AND " + processDateCol + " >= ?";


        try (Connection conn = ConnectionFactory.getSingleton().getConnection(POOLNAME)) {


            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, DBUtil.convertStringToDate(processDate));
                accountingDetailVOs = (AccountingDetailVO[]) executeQuery(AccountingDetailVO.class,
                        ps,
                        POOLNAME,
                        false,
                        null);
            }


        } catch (Exception e) {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        } finally {
        }

        return accountingDetailVOs;
    }


    public boolean setAccountPendingStatusLTEDate(String accountingPendingStatus, String processDate) {
        String processDateCol = DBTABLE.getDBColumn("ProcessDate").getFullyQualifiedColumnName();
        String accountingPendingStatusCol = DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();
        StringBuilder sql = new StringBuilder("UPDATE ").append(TABLENAME).append(" SET ").append(accountingPendingStatusCol).append("=")
                .append("?").append(" WHERE ").append(processDateCol).append("<=?");

        try (Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL)) {

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                ps.setString(1, accountingPendingStatus);
                ps.setDate(2, DBUtil.convertStringToDate(processDate));
                return ps.execute();
            } catch (SQLException e) {
                System.out.println(sql);
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Finder By Accounting Pending Status and Process Date
     * Retrieves all Accounting Detail entries for give Accounting Pending Status and whose Process Date is earlier than given Date.
     *
     * @param accountingPendingStatus
     * @param processDate
     * @return Array of AccountingDetailsVOs
     */
    public AccountingDetailVO[] findBy_AccountingPendingStatus_ProcessDateLTE(String
                                                                                      accountingPendingStatus, String processDate) {
        AccountingDetailVO[] accountingDetailVOs;

        String processDateCol = DBTABLE.getDBColumn("ProcessDate").getFullyQualifiedColumnName();
        String accountingPendingStatusCol = DBTABLE.getDBColumn("AccountingPendingStatus").getFullyQualifiedColumnName();

        String sql = " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + accountingPendingStatusCol + " = ?" +
                " AND " + processDateCol + " <= ?";


        try (Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL)) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, accountingPendingStatus);
                ps.setDate(2, DBUtil.convertStringToDate(processDate));

                accountingDetailVOs = (AccountingDetailVO[]) executeQuery(AccountingDetailVO.class,
                        ps,
                        POOLNAME,
                        false,
                        null);
            }


        } catch (SQLException e) {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return accountingDetailVOs;
    }
}
