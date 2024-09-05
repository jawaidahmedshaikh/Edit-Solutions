/*
* TestPayoutDAO.java   Version 1.1  03/15/2002
*
* Copyright (c)  2000 Systems Engineering Group, LLC.  All Rights Reserved.
*
* This program is the confidential and proprietary information of
* Systems Engineering Group, LLC and may not be copied in whole or in part
* without the written permission of Systems Engineering Group, LLC.
*/
package contract.dm.dao;

import edit.common.vo.PayoutVO;
import edit.common.vo.LifeVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;


public class LifeDAO extends DAO
{
    private final String POOLNAME;

    private final DBTable LIFE_DBTABLE;

    private final String LIFE_TABLENAME;

    public LifeDAO()
    {
        POOLNAME        = ConnectionFactory.EDITSOLUTIONS_POOL;

        LIFE_DBTABLE    = DBTable.getDBTableForTable("Life");

        LIFE_TABLENAME  = LIFE_DBTABLE.getFullyQualifiedTableName();

    }

    public LifeVO[] findLifeBySegmentFK(long segmentFK, boolean includeChildVOs, List voExclusionList)
    {
        String segmentFKCol = LIFE_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + LIFE_TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK;

        return (LifeVO[]) executeQuery(LifeVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
    }

    public LifeVO[] findLifeByEDITTrx(long editTrxPK, boolean includeChildVOs, List voExclusionList)
    {
        String segmentFKCol = LIFE_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        DBTable editTrxHistoryDBTable = DBTable.getDBTableForTable("EDITTrxHistory");
        String editTrxHistoryTableName = editTrxHistoryDBTable.getFullyQualifiedTableName();
        DBTable segmentHistoryDBTable = DBTable.getDBTableForTable("SegmentHistory");
        String segmentHistoryTableName = segmentHistoryDBTable.getFullyQualifiedTableName();
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        String segmentTableName = segmentDBTable.getFullyQualifiedTableName();

        String editTrxFKCol   = editTrxHistoryDBTable.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();
        String editTrxHistoryPKCol =  editTrxHistoryDBTable.getDBColumn("EDITTrxHistoryPK").getFullyQualifiedColumnName();
        String editTrxHistoryFKCol =  segmentHistoryDBTable.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();
        String segmentHistorySegmentFKCol =  segmentHistoryDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String prevSegmentStatusCol =  segmentHistoryDBTable.getDBColumn("PrevSegmentStatus").getFullyQualifiedColumnName();

        String sql = "SELECT " + LIFE_TABLENAME + ".* FROM " + LIFE_TABLENAME + ", " + editTrxHistoryTableName + ", " +
                     segmentHistoryTableName +
                     " WHERE " + segmentFKCol + " = " + segmentHistorySegmentFKCol +
                     " AND " + editTrxHistoryFKCol + " = " + editTrxHistoryPKCol +
                     " AND " + prevSegmentStatusCol + " = 'Pending'" +
                     " AND " + editTrxFKCol + " = " + editTrxPK;

        return (LifeVO[]) executeQuery(LifeVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
    }
//    public LifeVO[] findLifeByPK(long lifePK, boolean includeChildVOs, List voExclusionList)
//    {
//        String lifePKCol = SQLFormatter.formatColumnName(tableName, "LifePK");
//
//        String sql = "SELECT * FROM " + tableName +
//                     " WHERE " + lifePKCol + " = " + lifePK;
//
//        return (LifeVO[]) executeQuery(LifeVO.class,
//                                          sql,
//                                           POOLNAME,
//                                            includeChildVOs,
//                                             voExclusionList);
//    }
}
