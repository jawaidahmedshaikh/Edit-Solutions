/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 24, 2003
 * Time: 3:43:55 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent.dm.dao;

import edit.common.vo.CommissionLevelDescriptionVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class CommissionLevelDescriptionDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public CommissionLevelDescriptionDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("CommissionLevelDescription");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public CommissionLevelDescriptionVO[] getAllCommissionLevelDescriptions()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (CommissionLevelDescriptionVO[]) executeQuery(CommissionLevelDescriptionVO.class,
                                                              sql,
                                                               POOLNAME,
                                                                false,
                                                                 null);
    }

    public CommissionLevelDescriptionVO[] getCommissionLevelDescriptionByPK(long commLevelDescriptionPK)
    {
        String commissionLevelDescriptionPKCol = DBTABLE.getDBColumn("CommissionLevelDescriptionPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + commissionLevelDescriptionPKCol + " = " + commLevelDescriptionPK;

        return (CommissionLevelDescriptionVO[]) executeQuery(CommissionLevelDescriptionVO.class,
                                                              sql,
                                                               POOLNAME,
                                                                false,
                                                                 null);
    }
}