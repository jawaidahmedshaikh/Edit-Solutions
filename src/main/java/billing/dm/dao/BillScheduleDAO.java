package billing.dm.dao;

import edit.services.db.*;
import edit.common.vo.*;

public class BillScheduleDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public BillScheduleDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("BillSchedule");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }
    
    /**
     * Finds Areas by grouping and field.
     * @param grouping
     * @param field
     * @return
     */
    public BillScheduleVO[] findByBillSchedulePK(long billSchedulePK)
    {
        String billSchedulePKCol = DBTABLE.getDBColumn("BillSchedulePK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + billSchedulePKCol + " = " + billSchedulePK;

        return (BillScheduleVO[]) executeQuery(BillScheduleVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }  
}
