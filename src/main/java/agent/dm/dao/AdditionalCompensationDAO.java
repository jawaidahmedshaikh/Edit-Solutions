/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 12:47:29 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent.dm.dao;

import edit.common.vo.AdditionalCompensationVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

public class AdditionalCompensationDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AdditionalCompensationDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("AdditionalCompensation");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public AdditionalCompensationVO[] findByAgentContractPK(long agentContractPK)
    {
        String agentContractFKCol = DBTABLE.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + agentContractFKCol + " = " + agentContractPK;

        return (AdditionalCompensationVO[]) executeQuery(AdditionalCompensationVO.class,
                                                     sql,
                                                      POOLNAME,
                                                       false,
                                                        null);
    }
}
