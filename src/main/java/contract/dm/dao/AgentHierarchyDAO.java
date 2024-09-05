/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 30, 2003
 * Time: 8:14:44 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.dm.dao;

import edit.common.vo.*;
import edit.services.db.*;

import java.util.*;


public class AgentHierarchyDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public AgentHierarchyDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("AgentHierarchy");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     * @param segmentPK
     * @param includeChildVOs
     * @param voExclusionList
     * @return
     */
    public AgentHierarchyVO[] findBySegmentPK(long segmentPK, boolean includeChildVOs, List voExclusionList)
    {
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + " WHERE " + segmentFKCol + " = " + segmentPK;

        return (AgentHierarchyVO[]) executeQuery(AgentHierarchyVO.class, sql, POOLNAME, includeChildVOs, voExclusionList);
    }

    /**
     * Finder.
     * @param agentPK
     * @return
     */
    public AgentHierarchyVO[] findBy_AgentPK(long agentPK)
    {
        String agentFKCol = DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME + " WHERE " + agentFKCol + " = " + agentPK;

        return (AgentHierarchyVO[]) executeQuery(AgentHierarchyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param placedAgentPK
     * @return
     */
    public AgentHierarchyVO[] findBy_PlacedAgentPK(long placedAgentPK)
    {
        // AgentHierarchy
        String agentHierarchyPKCol = DBTABLE.getDBColumn("AgentHierarchyPK").getFullyQualifiedColumnName();

        // AgentSnapshot
        DBTable agentSnapshotDBTable = DBTable.getDBTableForTable("AgentSnapshot");
        String agentSnapshotTable = agentSnapshotDBTable.getFullyQualifiedTableName();
        String placedAgentFKCol = agentSnapshotDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();
        String agentHierarchyFKCol = agentSnapshotDBTable.getDBColumn("AgentHierarchyFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " INNER JOIN " + agentSnapshotTable +
                " ON " + agentHierarchyPKCol + " = " + agentHierarchyFKCol +
                " WHERE " + placedAgentFKCol + " = " + placedAgentPK;

        return (AgentHierarchyVO[]) executeQuery(AgentHierarchyVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param segmentPK
     * @param placedAgentPK
     * @return
     */
    public AgentHierarchyVO[] findBy_SegmentPK_AND_PlacedAgentPK(long segmentPK, long placedAgentPK)
    {
        // Segment
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        String segmentTable = segmentDBTable.getFullyQualifiedTableName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        // AgentHierarchy
        String agentHierarchyPKCol = DBTABLE.getDBColumn("AgentHierarchyPK").getFullyQualifiedColumnName();
        String segmentFKCol = DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();

        // AgentSnapshot
        DBTable agentSnapshotDBTable = DBTable.getDBTableForTable("AgentSnapshot");
        String agentSnapshotTable = agentSnapshotDBTable.getFullyQualifiedTableName();
        String agentHierarchyFKCol = agentSnapshotDBTable.getDBColumn("AgentHierarchyFK").getFullyQualifiedColumnName();
        String placedAgentFKCol = agentSnapshotDBTable.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + segmentTable +

                " INNER JOIN " + TABLENAME +
                " ON " + segmentPKCol + " = " + segmentFKCol +

                " INNER JOIN " + agentSnapshotTable +
                " ON " + agentHierarchyPKCol + " = " + agentHierarchyFKCol +

                " WHERE " + segmentPKCol + " = " + segmentPK +
                " AND " + placedAgentFKCol + " = " + placedAgentPK;

        return (AgentHierarchyVO[]) executeQuery(AgentHierarchyVO.class, sql, POOLNAME, false, null);
    }
}
