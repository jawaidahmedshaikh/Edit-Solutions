/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 10:41:47 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.dao;

import edit.common.vo.ScheduledEventVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ScheduledEventDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ScheduledEventDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ScheduledEvent");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ScheduledEventVO[] findByGroupSetupPK(long groupSetupPK)
    {
        DBTable groupSetupDBTable = DBTable.getDBTableForTable("GroupSetup");

        String groupSetupTable = groupSetupDBTable.getFullyQualifiedTableName();


        String groupSetupPKCol = groupSetupDBTable.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String groupSetupFKCol = DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();


        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + groupSetupTable +
                     " WHERE " + groupSetupPKCol + " = " + groupSetupFKCol +
                     " AND " + groupSetupPKCol + " = " + groupSetupPK;

        return (ScheduledEventVO[]) executeQuery(ScheduledEventVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                     null);
    }

    public ScheduledEventVO[] findByEDITTrxPK(long editTrxPK)
    {
        DBTable groupSetupDBTable    = DBTable.getDBTableForTable("GroupSetup");
        DBTable contractSetupDBTable = DBTable.getDBTableForTable("ContractSetup");
        DBTable clientSetupDBTable   = DBTable.getDBTableForTable("ClientSetup");
        DBTable editTrxDBTable       = DBTable.getDBTableForTable("EDITTrx");

        String groupSetupTable    = groupSetupDBTable.getFullyQualifiedTableName();
        String contractSetupTable = contractSetupDBTable.getFullyQualifiedTableName();
        String clientSetupTable   = clientSetupDBTable.getFullyQualifiedTableName();
        String editTrxTable       = editTrxDBTable.getFullyQualifiedTableName();


        String seGroupSetupFKCol  = DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();

        String groupSetupPKCol    = groupSetupDBTable.getDBColumn("GroupSetupPK").getFullyQualifiedColumnName();

        String csGroupSetupFKCol  = contractSetupDBTable.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();
        String contractSetupPKCol = contractSetupDBTable.getDBColumn("ContractSetupPK").getFullyQualifiedColumnName();

        String contractSetupFKCol = clientSetupDBTable.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();
        String clientSetupPKCol   = clientSetupDBTable.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String clientSetupFKCol   = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String editTrxPKCol       = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();


        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + groupSetupTable + ", " + contractSetupTable + ", " + clientSetupTable + ", " + editTrxTable +
                     " WHERE " +  groupSetupPKCol + " = " + seGroupSetupFKCol +
                     " AND " +  groupSetupPKCol + " = " + csGroupSetupFKCol +
                     " AND " + contractSetupPKCol + " = " + contractSetupFKCol +
                     " AND " + clientSetupPKCol + " = " + clientSetupFKCol +
                     " AND " + editTrxPKCol + " = " + editTrxPK;

        return (ScheduledEventVO[]) executeQuery(ScheduledEventVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                     null);
    }
}