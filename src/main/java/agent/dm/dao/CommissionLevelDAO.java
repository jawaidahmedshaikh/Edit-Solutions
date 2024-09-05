/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 12:45:15 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent.dm.dao;

import edit.common.vo.CommissionLevelVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class CommissionLevelDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public CommissionLevelDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("CommissionLevel");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public CommissionLevelVO[] getAllCommissionLevels()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (CommissionLevelVO[]) executeQuery(CommissionLevelVO.class,
                                                   sql,
                                                    POOLNAME,
                                                     false,
                                                      null);
    }

    public CommissionLevelVO[] getCommissionLevelByPK(long commLevelPK)
    {
        String commissionLevelPKCol = DBTABLE.getDBColumn("CommissionLevelPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + commissionLevelPKCol + " = " + commLevelPK;

        return (CommissionLevelVO[]) executeQuery(CommissionLevelVO.class,
                                                   sql,
                                                    POOLNAME,
                                                     false,
                                                      null);
    }

    public CommissionLevelVO[] findByCommissionLevelDescriptionPK_AND_CommissionContractPK(long commLevelDescriptionFK,
                                                                                            long commContractFK)
    {
        String commissionLevelDescriptionFKCol = DBTABLE.getDBColumn("CommissionLevelDescriptionFK").getFullyQualifiedColumnName();
        String commissionContractFKCol         = DBTABLE.getDBColumn("CommissionContractFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + commissionLevelDescriptionFKCol + " = " + commLevelDescriptionFK +
                     " AND " + commissionContractFKCol + " = " + commContractFK;

        return (CommissionLevelVO[]) executeQuery(CommissionLevelVO.class,
                                                   sql,
                                                    POOLNAME,
                                                     false,
                                                      null);
    }
}
