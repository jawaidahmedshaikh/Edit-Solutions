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

import edit.common.vo.ChargeVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class ChargeDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public ChargeDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Charge");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ChargeVO[] findBySegmentActivityPK(long segmentActivityPK) throws Exception
    {
        String segmentActivityFK = DBTABLE.getDBColumn("SegmentActivityFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentActivityFK + " = " + segmentActivityPK;

        return (ChargeVO[]) executeQuery(ChargeVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
    }

    public ChargeVO[] findByGroupSetupPK(long groupSetupPK) throws Exception
    {
        String groupSetupFK = DBTABLE.getDBColumn("GroupSetupFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + groupSetupFK + " =  " + groupSetupPK;

        return (ChargeVO[]) executeQuery(ChargeVO.class,
                                          sql,
                                           POOLNAME,
                                            false,
                                             null);
    }
}
