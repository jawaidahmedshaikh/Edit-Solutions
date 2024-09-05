/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Feb 14, 2002
 * Time: 10:31:49 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.SegmentBackupVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class SegmentBackupDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public SegmentBackupDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("SegmentBackup");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public SegmentBackupVO[] findBySegmentFK(long segmentFK)
    {
        String segmentFKCol  = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String lineNumberCol = DBTABLE.getDBColumn("LineNumber").getFullyQualifiedColumnName();

        String sql = null;

        sql =   " SELECT * FROM " + TABLENAME +
                " WHERE " + segmentFKCol + " = " + segmentFK +
                " ORDER BY " + lineNumberCol;

        return (SegmentBackupVO[]) executeQuery(SegmentBackupVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }
}