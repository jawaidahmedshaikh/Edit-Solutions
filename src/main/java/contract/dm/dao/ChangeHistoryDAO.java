/*
 * ChangeHistoryDAO.java   Version 1.1   10/10/2001
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
 
package contract.dm.dao;


import edit.common.vo.ChangeHistoryVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class ChangeHistoryDAO extends DAO
{

    private static final String tableNameSql = "('Segment', 'ContractClientAllocation', 'Payout', 'InvestmentAllocation', " +
                          "'Deposits', 'EDITTrx', 'ContractClient', 'AnnualizedSubBucket', 'FinancialHistory', " +
                          "'ContractRequirement', 'ClientRole', 'Investment', 'Bucket')";

    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ChangeHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ChangeHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ChangeHistoryVO[] findChangeHistoryByPK(long changeHistoryPK)
    {
        String changeHistoryPKCol = DBTABLE.getDBColumn("ChangeHistoryPK").getFullyQualifiedColumnName();
        String effectiveDateCol   = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + changeHistoryPKCol + " = " + changeHistoryPK +
                     " ORDER BY " + effectiveDateCol + " ASC";

        return (ChangeHistoryVO[]) executeQuery(ChangeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    /**
     * Finds all ChangeHistory records for a given contractNumber
     * @param contractNumber                contract identifier for which ChangeHistory records should be found
     * @return  Array of ChangeHistoryVOs
     * @throws Exception
     */
    public ChangeHistoryVO[] findByContractNumber(String contractNumber)
    {
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");

        String segmentTable = segmentDBTable.getFullyQualifiedTableName();

        String contractNumberCol = segmentDBTable.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String segmentPKCol      = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String parentFKCol     = DBTABLE.getDBColumn("ParentFK").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String tableNameCol = DBTABLE.getDBColumn("TableName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + ", " + segmentTable +
                     " WHERE " + contractNumberCol + " = '" + contractNumber + "'" +
                     " AND " + segmentPKCol + " = " + parentFKCol +
                     " AND " + tableNameCol + " IN " + tableNameSql +
                     " ORDER BY " + effectiveDateCol + " ASC";

        return (ChangeHistoryVO[]) executeQuery(ChangeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    public ChangeHistoryVO[] findByModifiedKey(long modifiedKey)
    {
        String modifiedRecordFKCol = DBTABLE.getDBColumn("ModifiedRecordFK").getFullyQualifiedColumnName();
        String effectiveDateCol    = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String parentFKCol         = DBTABLE.getDBColumn("ParentFK").getFullyQualifiedColumnName();
        String tableNameCol        = DBTABLE.getDBColumn("TableName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE (" + modifiedRecordFKCol + " = " + modifiedKey +
                     "  OR  " + parentFKCol + " = " + modifiedKey + ")" +
                     " AND " + tableNameCol + " IN " + tableNameSql +
                     " ORDER BY " + effectiveDateCol + " ASC";

        return (ChangeHistoryVO[]) executeQuery(ChangeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    /**
     * Finds the set of associated ChangeHistories by SegmentPK.
     * @param segmentPK
     * @return
     */
    public ChangeHistoryVO[] findBySegmentPK(long segmentPK)
    {
        String parentFKCol = DBTABLE.getDBColumn("ParentFK").getFullyQualifiedColumnName();
        String tableNameCol = DBTABLE.getDBColumn("TableName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + parentFKCol + " = " + segmentPK +
                     " AND " + tableNameCol + " IN " + tableNameSql;

        return (ChangeHistoryVO[]) executeQuery(ChangeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    public ChangeHistoryVO[] getAgentLicenseChangeHistories(long agentPK)
    {
        String modifiedRecordFKCol = DBTABLE.getDBColumn("ModifiedRecordFK").getFullyQualifiedColumnName();
        String effectiveDateCol    = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String parentFKCol         = DBTABLE.getDBColumn("ParentFK").getFullyQualifiedColumnName();

        DBTable AgentDBTable   = DBTable.getDBTableForTable("Agent");
        String AgentTableName = AgentDBTable.getFullyQualifiedTableName();
        DBTable AgentLicenseDBTable   = DBTable.getDBTableForTable("AgentLicense");
        String AgentLicenseTableName = AgentLicenseDBTable.getFullyQualifiedTableName();

        String agentPKCol         = AgentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String agentFKCol         = AgentLicenseDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String agentLicensePKCol  = AgentLicenseDBTable.getDBColumn("AgentLicensePK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + AgentLicenseTableName +
                     " INNER JOIN " + AgentTableName + "ON " + agentFKCol +
                     " = " + agentPKCol + " INNER JOIN " + TABLENAME + " ON " +
                     agentLicensePKCol + " = " + modifiedRecordFKCol +
                     " WHERE " +agentPKCol + " = " + agentPK;

        return (ChangeHistoryVO[]) executeQuery(ChangeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    public ChangeHistoryVO[] getAgentChangeHistories(long agentPK)
    {
        String modifiedRecordFKCol = DBTABLE.getDBColumn("ModifiedRecordFK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME +
//                     " INNER JOIN " + TABLENAME + " ON " +
//                     agentPKCol + " = " + modifiedRecordFKCol +
                     " WHERE " + modifiedRecordFKCol + " = " + agentPK;
        System.out.println("sql = " + sql);

        return (ChangeHistoryVO[]) executeQuery(ChangeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }
}