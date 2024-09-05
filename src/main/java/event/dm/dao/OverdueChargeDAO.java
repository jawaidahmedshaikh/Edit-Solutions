package event.dm.dao;

import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.ConnectionFactory;
import edit.services.db.DBUtil;
import edit.common.vo.OverdueChargeVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * User: sprasad
 * Date: Dec 2, 2004
 * Time: 5:35:40 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

public class OverdueChargeDAO extends DAO
{
    private final String POOLNAME;

    private final DBTable OVERDUE_CHARGE_DBTABLE;
    private final DBTable EDITTRX_DBTABLE;
    private final DBTable CLIENT_SETUP_DBTABLE;
    private final DBTable CONTRACT_SETUP_DBTABLE;

    private final String OVERDUE_CHARGE_TABLENAME;
    private final String EDITTRX_TABLENAME;
    private final String CLIENT_SETUP_TABLENAME;
    private final String CONTRACT_SETUP_TABLENAME;

    public OverdueChargeDAO()
    {
        POOLNAME    = ConnectionFactory.EDITSOLUTIONS_POOL;

        OVERDUE_CHARGE_DBTABLE = DBTable.getDBTableForTable("OverdueCharge");
        EDITTRX_DBTABLE        = DBTable.getDBTableForTable("EDITTrx");
        CLIENT_SETUP_DBTABLE   = DBTable.getDBTableForTable("ClientSetup");
        CONTRACT_SETUP_DBTABLE = DBTable.getDBTableForTable("ContractSetup");

        OVERDUE_CHARGE_TABLENAME   = OVERDUE_CHARGE_DBTABLE.getFullyQualifiedTableName();
        EDITTRX_TABLENAME        = EDITTRX_DBTABLE.getFullyQualifiedTableName();
        CLIENT_SETUP_TABLENAME   = CLIENT_SETUP_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_SETUP_TABLENAME = CONTRACT_SETUP_DBTABLE.getFullyQualifiedTableName();
    }

    public OverdueChargeVO[] findBySegmentPK_AND_TransactionType (long segmentPK, String[] transactionType, String effectiveDate)
    {
        OverdueChargeVO[] overdueChargeVOs = null;

        String editTrxFKCol = OVERDUE_CHARGE_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxPKCol = EDITTRX_DBTABLE.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String statusCol = EDITTRX_DBTABLE.getDBColumn("Status").getFullyQualifiedColumnName();
        String effectiveDateCol = EDITTRX_DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String clientSetupFKCol = EDITTRX_DBTABLE.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol = CLIENT_SETUP_DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String segmentFKCol = CONTRACT_SETUP_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = EDITTRX_DBTABLE.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        String sqlIn = "";

        for (int i = 0; i < transactionType.length; i++)
        {
            if (i < transactionType.length - 1)
            {
                sqlIn = sqlIn + "'" + transactionType[i] + "', ";
            }
            else
            {
                sqlIn = sqlIn + "'" + transactionType[i] + "'";
            }
        }

        String sql = " SELECT " + OVERDUE_CHARGE_TABLENAME + ".* " + " FROM " +  OVERDUE_CHARGE_TABLENAME +
                     " INNER JOIN " + EDITTRX_TABLENAME + " ON " + editTrxFKCol + " = " + editTrxPKCol +
                     " INNER JOIN " + CLIENT_SETUP_TABLENAME + " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
                     " INNER JOIN " + CONTRACT_SETUP_TABLENAME + " ON " + contractSetupFKCol + " = " + contractSetupPKCol +
                     " WHERE " + segmentFKCol + " = ?" +
                     " AND " + transactionTypeCTCol + " IN (" + sqlIn + ")" +
                     " AND " + statusCol + " IN ('N', 'A')" +
                     " AND " + effectiveDateCol + " <= ?";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setLong(1, segmentPK);
            ps.setDate(2, DBUtil.convertStringToDate(effectiveDate));

            overdueChargeVOs = (OverdueChargeVO[]) executeQuery(OverdueChargeVO.class,
                                                     ps,
                                                     POOLNAME,
                                                     true,
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

        return overdueChargeVOs;
    }

    public OverdueChargeVO[] findByEDITTrxFK(long editTrxFK)
    {
        String editTrxFKCol = OVERDUE_CHARGE_DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + OVERDUE_CHARGE_TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxFK;

        return (OverdueChargeVO[]) executeQuery(OverdueChargeVO.class,
                                                sql,
                                                POOLNAME,
                                                true,
                                                null);
    }

    public OverdueChargeVO[] findByPK(long overdueChargePK)
    {
        String overdueChargePKCol = OVERDUE_CHARGE_DBTABLE.getDBColumn("OverdueChargePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + OVERDUE_CHARGE_TABLENAME +
                     " WHERE " + overdueChargePKCol + " = " + overdueChargePK;

        return (OverdueChargeVO[]) executeQuery(OverdueChargeVO.class,
                                                sql,
                                                POOLNAME,
                                                false,
                                                null);
    }
}
