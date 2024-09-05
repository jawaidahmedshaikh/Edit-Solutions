/**
 * User: dlataill
 * Date: Feb 10, 2005
 * Time: 2:28:53 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package agent.dm.dao;

import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.ConnectionFactory;
import edit.common.vo.RedirectVO;

public class RedirectDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public RedirectDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Redirect");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public RedirectVO[] findByAgentFK(long agentFK)
    {
        String agentFKCol  = DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + agentFKCol + " = " + agentFK;

        return (RedirectVO[]) executeQuery(RedirectVO.class,
                                           sql,
                                           POOLNAME,
                                           false,
                                           null);
    }
}
