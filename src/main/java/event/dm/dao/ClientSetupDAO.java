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

import edit.common.vo.ClientSetupVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class ClientSetupDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ClientSetupDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("ClientSetup");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ClientSetupVO[] findByClientSetupPK(long clientSetupPK) 
    {
        String clientSetupPKCol = DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + clientSetupPKCol + " = " + clientSetupPK;

        return (ClientSetupVO[]) executeQuery(ClientSetupVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ClientSetupVO[] findByContractSetupPK(long contractSetupPK) throws Exception
    {
        String contractSetupFKCol = DBTABLE.getDBColumn("ContractSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + contractSetupFKCol + " = " + contractSetupPK;

        return (ClientSetupVO[]) executeQuery(ClientSetupVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ClientSetupVO[] findByEDITTrxPK(long editTrxPK) throws Exception
    {
        DBTable editTrxDBTable = DBTable.getDBTableForTable("EDITTrx");

        String editTrxTable = editTrxDBTable.getFullyQualifiedTableName();

        String clientSetupPKCol = DBTABLE.getDBColumn("ClientSetupPK").getFullyQualifiedColumnName();

        String clientSetupFKCol = editTrxDBTable.getDBColumn("ClientSetupFK").getFullyQualifiedColumnName();
        String editTrxPKCol = editTrxDBTable.getDBColumn("EDITTrxPK").getFullyQualifiedColumnName();

        String sql = "SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + editTrxTable +
                " WHERE " + clientSetupFKCol + " = " + clientSetupPKCol +
                " AND " + editTrxPKCol + " = " + editTrxPK;

        return (ClientSetupVO[]) executeQuery(ClientSetupVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @param treatyPK
     * @return
     */
    public ClientSetupVO[] findBy_TreatyPK(long treatyPK)
    {
        String treatyFKCol = DBTABLE.getDBColumn("TreatyFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + treatyFKCol + " = " + treatyPK;

        return (ClientSetupVO[]) executeQuery(ClientSetupVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Find all ClientSetupVOs by the given ContractClientFK
     * @param contractClientFK
     * @return
     */
    public ClientSetupVO[] findByContractClientFK(long contractClientFK)
    {
        String contractClientFKCol = DBTABLE.getDBColumn("ContractClientFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + contractClientFKCol + " = " +  contractClientFK;

        return (ClientSetupVO[]) executeQuery(ClientSetupVO.class,
                                              sql,
                                              POOLNAME,
                                              false,
                                              null);
    }
}
