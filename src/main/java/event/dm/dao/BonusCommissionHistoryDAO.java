/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Apr 19, 2005
 * Time: 11:39:55 AM
 * To change this template use File | Settings | File Templates.
 */
package event.dm.dao;

import edit.common.vo.*;
import edit.services.db.*;


public class BonusCommissionHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public BonusCommissionHistoryDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("BonusCommissionHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public BonusCommissionHistoryVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (BonusCommissionHistoryVO[]) executeQuery(BonusCommissionHistoryVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     *
     * @param bonusCommissionHistoryPK
     */
    public BonusCommissionHistoryVO[] findByPK(long bonusCommissionHistoryPK)
    {
        String bonusCommissionHistoryPKCol = DBTABLE.getDBColumn("BonusCommissionHistoryPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + bonusCommissionHistoryPKCol + " = " + bonusCommissionHistoryPK;

        return (BonusCommissionHistoryVO[]) executeQuery(BonusCommissionHistoryVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param participatingAgentPK
     * @param commissionHistoryPK
     * @return
     */
    public BonusCommissionHistoryVO[] findBy_ParticipatingAgentPK_CommissionHistoryPK(long participatingAgentPK, long commissionHistoryPK)
    {
        String commissionHistoryFKCol = DBTABLE.getDBColumn("CommissionHistoryFK").getFullyQualifiedColumnName();
        String participatingAgentFKCol = DBTABLE.getDBColumn("ParticipatingAgentFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + participatingAgentFKCol + " = " + participatingAgentPK + " AND " + commissionHistoryFKCol + " = " + commissionHistoryFKCol;

        return (BonusCommissionHistoryVO[]) executeQuery(BonusCommissionHistoryVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param participatingAgentPK
     * @return
     */
    public BonusCommissionHistoryVO[] findBy_ParticipatingAgentPK(long participatingAgentPK)
    {
        String participatingAgentFKCol = DBTABLE.getDBColumn("ParticipatingAgentFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + participatingAgentFKCol + " = " + participatingAgentPK;

        return (BonusCommissionHistoryVO[]) executeQuery(BonusCommissionHistoryVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param participatingAgentPK
     * @param contractCodeCT
     * @param trxTypeCT
     * @return
     */
    public BonusCommissionHistoryVO[] findBy_ParticipatingAgentPK_LastCheckDateTime_LastStatementDateTime_ContractCodeCT_TrxTypeCT(long participatingAgentPK, String contractCodeCT, String trxTypeCT)
    {
        // ParticipatingAgent
        DBTable participatingAgentDBTable = DBTable.getDBTableForTable("ParticipatingAgent");
        String participatingAgentTable = participatingAgentDBTable.getFullyQualifiedTableName();
        String participatingAgentPKCol = participatingAgentDBTable.getDBColumn("ParticipatingAgentPK").getFullyQualifiedColumnName();
        String lastStatementDateTimeCol = participatingAgentDBTable.getDBColumn("LastStatementDateTime").getFullyQualifiedColumnName();
        String lastCheckDateTimeCol = participatingAgentDBTable.getDBColumn("LastCheckDateTime").getFullyQualifiedColumnName();
        String placedAgentFKCol = participatingAgentDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        // BonusCommissionHistory
        String participatingAgentFKCol = DBTABLE.getDBColumn("ParticipatingAgentFK").getFullyQualifiedColumnName();
        String commissionHistoryFKCol = DBTABLE.getDBColumn("CommissionHistoryFK").getFullyQualifiedColumnName();
        String bonusUpdateDateTimeCol = DBTABLE.getDBColumn("BonusUpdateDateTime").getFullyQualifiedColumnName();

        // CommisionHistory
        DBTable commissionHistoryDBTable = DBTable.getDBTableForTable("CommissionHistory");
        String commissionHistoryTable = commissionHistoryDBTable.getFullyQualifiedTableName();
        String commissionHistoryPKCol = commissionHistoryDBTable.getDBColumn("CommissionHistoryPK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol = commissionHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        // EDITTrxHistory
        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        // EDITTrx
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String transactionTypeCTCol = editTrxDBTable.getDBColumn("TransactionTypeCT").getFullyQualifiedColumnName();

        // PlacedAgent
        DBTable placedAgentDBTable = DBTable.getDBTableForTable("PlacedAgent");
        String placedAgentTable = placedAgentDBTable.getFullyQualifiedTableName();
        String placedAgentPKCol = placedAgentDBTable.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String commissionProfileFKCol = placedAgentDBTable.getDBColumn("CommissionProfileFK").getFullyQualifiedColumnName();

        // CommissionProfile
        DBTable commissionProfileDBTable = DBTable.getDBTableForTable("CommissionProfile");
        String commissionProfileTable = commissionProfileDBTable.getFullyQualifiedTableName();
        String commissionProfilePKCol = commissionProfileDBTable.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();
        String contractCodeCTCol = commissionProfileDBTable.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT " + TABLENAME + ".*" +

                " FROM " + participatingAgentTable +

                " INNER JOIN " + TABLENAME +
                " ON " + participatingAgentFKCol + " = " +  participatingAgentPKCol +

                " INNER JOIN " + commissionHistoryTable +
                " ON " + commissionHistoryFKCol + " = " + commissionHistoryPKCol +

                " INNER JOIN " + editTrxHistoryTable +
                " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +

                " INNER JOIN " + editTrxTable +
                " ON " + editTrxFKCol + " = " + editTrxPKCol +

                " INNER JOIN " + placedAgentTable +
                " ON " + placedAgentFKCol + " = " + placedAgentPKCol +

                " INNER JOIN " + commissionProfileTable +
                " ON " + commissionProfileFKCol + " = " + commissionProfilePKCol +

                " WHERE " + participatingAgentPKCol + " = " + participatingAgentPK +

                " AND (" + bonusUpdateDateTimeCol + " > " +  lastStatementDateTimeCol  + " OR " +  lastStatementDateTimeCol + " IS NULL)" +

                " AND (" + bonusUpdateDateTimeCol + " <= " +  lastCheckDateTimeCol + ")" +

                " AND " + contractCodeCTCol + " = '" + contractCodeCT + "'" +

                " AND " + transactionTypeCTCol + " = '" + trxTypeCT + "'";

        return (BonusCommissionHistoryVO[]) executeQuery(BonusCommissionHistoryVO.class, sql, POOLNAME, false, null);
    }
}
