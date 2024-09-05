/*
 * User: gfrosti
 * Date: Nov 17, 2004
 * Time: 10:55:10 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance.dm.dao;

import edit.common.vo.*;

import edit.services.db.*;

import java.util.ArrayList;
import java.util.List;


public class ReinsuranceHistoryDAO extends DAO
{
    private final DBTable DBTABLE;
    private final String POOLNAME;
    private final String TABLENAME;

    public ReinsuranceHistoryDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("ReinsuranceHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @param updateStatus
     * @return
     */
    public ReinsuranceHistoryVO[] findBy_UpdateStatus(String updateStatus)
    {
        String updateStatusCol = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT *" +
                " FROM " + TABLENAME +
                " WHERE " + updateStatusCol + " = '" + updateStatus + "'";

        return (ReinsuranceHistoryVO[]) executeQuery(ReinsuranceHistoryVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param updateStatus
     * @param productStructurePK
     * @return
     */
    public ReinsuranceHistoryVO[] findBy_UpdateStatus_ProductStructurePK(String updateStatus, long productStructurePK)
    {
        String updateStatusCol = DBTABLE.getDBColumn("UpdateStatus").getFullyQualifiedColumnName();
        String contractTreatyFKCol = DBTABLE.getDBColumn("ContractTreatyFK").getFullyQualifiedColumnName();

        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String contractTreatyPKCol = contractTreatyDBTable.getDBColumn("ContractTreatyPK").getFullyQualifiedColumnName();
        String segmentFKCol = contractTreatyDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        String segmentTable = segmentDBTable.getFullyQualifiedTableName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String productStructureFKCol = segmentDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + contractTreatyTable +
                " ON " + contractTreatyFKCol + " = " + contractTreatyPKCol +
                " INNER JOIN " + segmentTable +
                " ON " + segmentFKCol + " = " + segmentPKCol +
                " WHERE " + updateStatusCol + " = '" + updateStatus + "'" +
                " AND " + productStructureFKCol + " = " + productStructurePK;

        return (ReinsuranceHistoryVO[]) executeQuery(ReinsuranceHistoryVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public ReinsuranceHistoryVO[] findReinsuranceHistoryBy_ReinsurerPK(long reinsurerPK)
    {
        List reinsHistList = new ArrayList();

        DBTable reinsurerDBTable = DBTable.getDBTableForTable("Reinsurer");
        String reinsurerTable = reinsurerDBTable.getFullyQualifiedTableName();
        String reinsurerPKCol = reinsurerDBTable.getDBColumn("ReinsurerPK").getFullyQualifiedColumnName();

        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        String editTrxHistoryTable = editTrxHistoryDBTable.getFullyQualifiedTableName();
        String editTrxHistoryPKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxFKCol = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");
        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();
        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();
        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();

        DBTable clientSetupDBTable = DBTable.getDBTableForTable("ClientSetup");
        String clientSetupTable = clientSetupDBTable.getFullyQualifiedTableName();
        String clientSetupPKCol = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();
        String clSetupTreatyFKCol = clientSetupDBTable.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        DBTable treatyDBTable = DBTable.getDBTableForTable("Treaty");
        String treatyTable = treatyDBTable.getFullyQualifiedTableName();
        String treatyPKCol = treatyDBTable.getDBColumn("TreatyPK").getFullyQualifiedColumnName();
        String reinsurerFKCol = treatyDBTable.getDBColumn("ReinsurerFK").getFullyQualifiedColumnName();

        DBTable contractTreatyDBTable = DBTable.getDBTableForTable("ContractTreaty");
        String contractTreatyTable = contractTreatyDBTable.getFullyQualifiedTableName();
        String contractTreatyPKCol = contractTreatyDBTable.getDBColumn("ContractTreatyPK").getFullyQualifiedColumnName();
        String treatyFKCol = contractTreatyDBTable.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String contractTreatyFKCol = DBTABLE.getDBColumn("ContractTreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".* " +
                " FROM " + TABLENAME +
                " INNER JOIN " + contractTreatyTable +
                " ON " + contractTreatyFKCol + " = " + contractTreatyPKCol +
                " INNER JOIN " + treatyTable +
                " ON " + treatyFKCol + " = " + treatyPKCol +
                " INNER JOIN " + reinsurerTable +
                " ON " + reinsurerFKCol + " = " + reinsurerPKCol +
                " WHERE " + reinsurerPKCol + " = " + reinsurerPK;

        ReinsuranceHistoryVO[] reinsHistVOs = (ReinsuranceHistoryVO[]) executeQuery(ReinsuranceHistoryVO.class, sql, POOLNAME, false, null);

        if (reinsHistVOs != null)
        {
            for (int i = 0; i < reinsHistVOs.length; i++)
            {
                reinsHistList.add(reinsHistVOs[i]);
            }
        }

        sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME +
              " INNER JOIN " + editTrxHistoryTable +
              " ON " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
              " INNER JOIN " + editTrxTable +
              " ON " + editTrxFKCol + " = " + editTrxPKCol +
              " INNER JOIN " + clientSetupTable +
              " ON " + clientSetupFKCol + " = " + clientSetupPKCol +
              " INNER JOIN " + treatyTable +
              " ON " + clSetupTreatyFKCol + " = " + treatyPKCol +
              " INNER JOIN " + reinsurerTable +
              " ON " + reinsurerFKCol + " = " + reinsurerPKCol +
              " WHERE " + reinsurerPKCol + " = " + reinsurerPK;

        ReinsuranceHistoryVO[] rckReinsHistoryVOs = (ReinsuranceHistoryVO[]) executeQuery(ReinsuranceHistoryVO.class, sql, POOLNAME, false, null);

        if (rckReinsHistoryVOs != null)
        {
            for (int i = 0; i < rckReinsHistoryVOs.length; i++)
            {
                reinsHistList.add(rckReinsHistoryVOs[i]);
            }
        }

        return (ReinsuranceHistoryVO[]) reinsHistList.toArray(new ReinsuranceHistoryVO[reinsHistList.size()]);
    }

    /**
     * Finder.
     * @param contractTreatyPK
     * @return
     */
    public ReinsuranceHistoryVO[] findBy_ContractTreatyPK(long contractTreatyPK)
    {
        String contractTreatyFKCol = DBTABLE.getDBColumn("ContractTreatyFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + contractTreatyFKCol + " = " + contractTreatyPK;

        return (ReinsuranceHistoryVO[]) executeQuery(ReinsuranceHistoryVO.class, sql, POOLNAME, false, null);
    }

    public ReinsuranceHistoryVO[] findBy_EDITTrxHistoryFK(long editTrxHistoryFK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryFK;

        return (ReinsuranceHistoryVO[]) executeQuery(ReinsuranceHistoryVO.class, sql, POOLNAME, false, null);
    }
}

