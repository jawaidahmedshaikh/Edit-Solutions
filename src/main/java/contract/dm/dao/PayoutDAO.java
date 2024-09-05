/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
*/
package contract.dm.dao;

import edit.common.vo.PayoutVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;

public class PayoutDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public PayoutDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Payout");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public PayoutVO[] findPayoutBySegmentFK(long segmentFK)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK;

        return (PayoutVO[]) executeQuery(PayoutVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
    }

    public PayoutVO[] findBySegmentPK(long segmentFK, boolean includeChildVOs, List voExclusionList)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentFK;

        return (PayoutVO[]) executeQuery(PayoutVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
    }

    public PayoutVO[] findAll(boolean includeChildVOs, List voExclusionList)
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (PayoutVO[]) executeQuery(PayoutVO.class,
                                          sql,
                                           POOLNAME,
                                            includeChildVOs,
                                             voExclusionList);
    }

    public PayoutVO[] findByPK(long payoutPK)
    {
        String payoutPKCol = DBTABLE.getDBColumn("PayoutPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + payoutPKCol + " = " + payoutPK;

        return (PayoutVO[]) executeQuery(PayoutVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
    }
}