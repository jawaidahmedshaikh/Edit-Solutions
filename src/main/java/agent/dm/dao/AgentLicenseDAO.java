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

package agent.dm.dao;

import edit.common.vo.AgentLicenseVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class AgentLicenseDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AgentLicenseDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("AgentLicense");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public AgentLicenseVO[] findByAgentPK_AND_IssueState(long agentPK, String issueState)
    {
        String agentFKCol  = DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String stateCTCol  = DBTABLE.getDBColumn("StateCT").getFullyQualifiedColumnName();
        String statusCTCol = DBTABLE.getDBColumn("StatusCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + agentFKCol + " = " + agentPK +
                     " AND (" + stateCTCol + " = '" + issueState + "'" +
                     " OR " + stateCTCol + " = '*')" +
                     " AND " + statusCTCol + " = 'Active'";

        return (AgentLicenseVO[]) executeQuery(AgentLicenseVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}