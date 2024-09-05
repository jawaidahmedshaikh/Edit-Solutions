/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jun 9, 2004
 * Time: 8:56:38 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.services.db.DAO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;
import edit.common.vo.DepositsVO;



public class DepositsDAO  extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public DepositsDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Deposits");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public DepositsVO[] findByPK(long depositsPK)
    {
        String depositsPKCol = DBTABLE.getDBColumn("DepositsPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + depositsPKCol + " = " + depositsPK;

        return (DepositsVO[]) executeQuery(DepositsVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
    }

    public DepositsVO[] findBySegmentFK(long segmentFK)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK;

        return (DepositsVO[]) executeQuery(DepositsVO.class,
                                           sql,
                                           POOLNAME,
                                           false,
                                           null);
    }

    public DepositsVO[] findDepositsBySegmentPKAmtReceivedAndEDITTrxPK(long segmentPK,
                                                                        double amtReceived,
                                                                         long editTrxPK)
    {
        String segmentFKCol      = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String amountReceivedCol = DBTABLE.getDBColumn("AmountReceived").getFullyQualifiedColumnName();
        String editTrxFKCol      = DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT DISTINCT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK +
                     " AND (" + amountReceivedCol + " = " + amtReceived +
                     " OR " + editTrxFKCol + " = " + editTrxPK + ")";

        return (DepositsVO[]) executeQuery(DepositsVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
    }

    public DepositsVO[] findDepositsByEDITTrxPK(long editTrxPK)
    {
        String editTrxFKCol = DBTABLE.getDBColumn("EDITTrxFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + editTrxFKCol + " = " + editTrxPK;

        return (DepositsVO[]) executeQuery(DepositsVO.class,
                                            sql,
                                             POOLNAME,
                                              false,
                                               null);
    }
}