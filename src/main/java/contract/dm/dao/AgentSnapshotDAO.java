/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 30, 2003
 * Time: 8:14:44 AM
 *
 * c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.AgentSnapshotVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class AgentSnapshotDAO  extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;


    public AgentSnapshotDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("AgentSnapshot");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public AgentSnapshotVO[] findSortedAgentSnapshotVOs(long agentHierarchyPK)
    {
        String agentHierarchyFKCol = DBTABLE.getDBColumn("AgentHierarchyFK").getFullyQualifiedColumnName();
        String hierarchyLevelCol = DBTABLE.getDBColumn("HierarchyLevel").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + agentHierarchyFKCol + " = " + agentHierarchyPK +
                     " ORDER BY " + hierarchyLevelCol + " DESC";

        return (AgentSnapshotVO[]) executeQuery(AgentSnapshotVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                      null);
    }

    public AgentSnapshotVO[] findAgentSnapshotVOsByPlacedAgentFK(long placedAgentFK)
    {
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + placedAgentFKCol + " = " + placedAgentFK;

        return (AgentSnapshotVO[]) executeQuery(AgentSnapshotVO.class,
                                                  sql,
                                                   POOLNAME,
                                                    false,
                                                      null);
    }
    
    
    /**
   * Finder.
   * @param agentHierarchyPK
   * @return
   */
    public AgentSnapshotVO[] findBy_AgentHierarchyPK(long agentHierarchyPK)
    {
      String agentHierarchyFKCol = DBTABLE.getDBColumn("AgentHierarchyFK").getFullyQualifiedColumnName();

      String sql = "SELECT * FROM " + TABLENAME +
                   " WHERE " + agentHierarchyFKCol + " = " + agentHierarchyPK;

      return (AgentSnapshotVO[]) executeQuery(AgentSnapshotVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                    null);      
    }
}