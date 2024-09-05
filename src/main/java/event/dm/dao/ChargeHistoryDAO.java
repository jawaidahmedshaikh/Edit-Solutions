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

import edit.common.vo.ChargeHistoryVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class ChargeHistoryDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ChargeHistoryDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ChargeHistory");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ChargeHistoryVO[] findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        String editTrxHistoryFKCol = DBTABLE.getDBColumn("EDITTrxHistoryFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM "+ TABLENAME +
                     " WHERE " + editTrxHistoryFKCol + " = " + editTrxHistoryPK;

        return (ChargeHistoryVO[]) executeQuery(ChargeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }

    /**
     * Finder.
     * @param chargeHistoryPK
     * @return
     */
    public ChargeHistoryVO[] findByPK(long chargeHistoryPK)
    {
        String chargeHistoryPKCol = DBTABLE.getDBColumn("ChargeHistoryPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM "+ TABLENAME +
                     " WHERE " + chargeHistoryPKCol + " = " + chargeHistoryPK;

        return (ChargeHistoryVO[]) executeQuery(ChargeHistoryVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   false,
                                                    null);
    }
}
