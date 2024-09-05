/*
 * Version 1.1  03/15/2002
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.dm.dao;

import edit.common.vo.NoteReminderVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;



public class NoteReminderDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public NoteReminderDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("NoteReminder");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public NoteReminderVO[] findBySegmentPK(long segmentPK, boolean includeChildVOs, List voExclusionList)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + segmentFKCol + " = " + segmentPK;

        return (NoteReminderVO[]) executeQuery(NoteReminderVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
    }
}