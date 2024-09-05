/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 5, 2005
 * Time: 12:58:35 PM
 * To change this template use File | Settings | File Templates.
 */
package agent.dm.dao;

import edit.common.vo.ParticipatingAgentVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;


public class ParticipatingAgentDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    public ParticipatingAgentDAO()
    {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("ParticipatingAgent");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finder.
     */
    public ParticipatingAgentVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     *
     * @param participatingAgentPK
     */
    public ParticipatingAgentVO[] findByPK(long participatingAgentPK)
    {
        String participatingAgentPKCol = DBTABLE.getDBColumn("ParticipatingAgentPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + participatingAgentPKCol + " = " + participatingAgentPK;

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     *
     * @param bonusProgramPK
     */
    public ParticipatingAgentVO[] findBy_BonusProgramPK(long bonusProgramPK)
    {
        String bonusProgramFKCol = DBTABLE.getDBColumn("BonusProgramFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + " WHERE " + bonusProgramFKCol + " = " + bonusProgramPK;

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param bonusProgramPK
     * @param placedAgentPK
     * @return
     */
    public ParticipatingAgentVO[] findBy_BonusProgramPKAndPlacedAgentPK(long bonusProgramPK, long placedAgentPK)
    {
        String bonusProgramFKCol = DBTABLE.getDBColumn("BonusProgramFK").getFullyQualifiedColumnName();
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String sql;
        sql =   "SELECT * FROM " + TABLENAME +
                " WHERE " + bonusProgramFKCol + " = " + bonusProgramPK +
                " AND " + placedAgentFKCol + " = " + placedAgentPK;

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class, sql, POOLNAME, false, null);    }


    /**
     * Finder.
     * @param agentPK
     * @return
     */
    public ParticipatingAgentVO[] findBy_AgentPK(long agentPK)
    {
        // ParticipatingAgent
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        // PlacedAgent
        DBTable placedAgentDBTable = DBTable.getDBTableForTable("PlacedAgent");
        String placedAgentTable = placedAgentDBTable.getFullyQualifiedTableName();
        String placedAgentPKCol = placedAgentDBTable.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String agentContractFKCol = placedAgentDBTable.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();

        // AgentContract
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");
        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();
        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentFKCol = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        // Agent
        DBTable agentDBTable = DBTable.getDBTableForTable("Agent");
        String agentTable = agentDBTable.getFullyQualifiedTableName();
        String agentPKCol = agentDBTable.getDBColumn("AgentPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + placedAgentTable +
                " ON " + placedAgentFKCol + " = " + placedAgentPKCol +
                " INNER JOIN " + agentContractTable +
                " ON " + agentContractFKCol + " = " + agentContractPKCol +
                " INNER JOIN " + agentTable +
                " ON " + agentFKCol + " = " + agentPKCol +
                " WHERE " + agentPKCol + " = " + agentPK;

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Finder.
     * @param bonusProgramPK
     * @param agentPK
     * @return
     */
    public ParticipatingAgentVO[] findBy_BonusProgramPK_AgentPK(long bonusProgramPK, long agentPK)
    {
        // BonusProgram
        DBTable bonusProgramDBTable = DBTable.getDBTableForTable("BonusProgram");
        String bonusProgramTable = bonusProgramDBTable.getFullyQualifiedTableName();
        String bonusProgramPKCol = bonusProgramDBTable.getDBColumn("BonusProgramPK").getFullyQualifiedColumnName();

        // ParticipatingAgent
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        // PlacedAgent
        DBTable placedAgentDBTable = DBTable.getDBTableForTable("PlacedAgent");
        String placedAgentTable = placedAgentDBTable.getFullyQualifiedTableName();
        String placedAgentPKCol = placedAgentDBTable.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String agentContractFKCol = placedAgentDBTable.getDBColumn("AgentContractFK").getFullyQualifiedColumnName();

        // AgentContract
        DBTable agentContractDBTable = DBTable.getDBTableForTable("AgentContract");
        String agentContractTable = agentContractDBTable.getFullyQualifiedTableName();
        String agentContractPKCol = agentContractDBTable.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentFKCol = agentContractDBTable.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + bonusProgramTable +
                " INNER JOIN " + TABLENAME +
                " ON " + bonusProgramPKCol + " = " + bonusProgramPKCol + 
                " INNER JOIN " + placedAgentTable +
                " ON " + placedAgentFKCol + " = " + placedAgentPKCol +
                " INNER JOIN " + agentContractTable +
                " ON " + agentContractFKCol + " = " + agentContractPKCol +
                " WHERE " + bonusProgramPKCol + " = " + bonusProgramPK +
                " AND " + agentFKCol + " = " + agentPK;

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Finder.
     * @param placedAgentPK
     * @return
     */
    public ParticipatingAgentVO[] findBy_PlacedAgentPK(long placedAgentPK)
    {
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        String sql;
        sql = " SELECT * " +
                " FROM " + TABLENAME +
                " WHERE " + placedAgentFKCol + " = " + placedAgentPK;

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Finder.
     * @param contractCodeCT
     * @return
     */
    public ParticipatingAgentVO[] findBy_ContractCodeCT(String contractCodeCT)
    {
        // ParticipatingAgent
        String placedAgentFKCol = DBTABLE.getDBColumn("PlacedAgentFK").getFullyQualifiedColumnName();

        // PlacedAgent
        DBTable placedAgentDBTable = DBTable.getDBTableForTable("PlacedAgent");
        String placedAgentTable = placedAgentDBTable.getFullyQualifiedTableName();
        String placedAgentPKCol = placedAgentDBTable.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String commissionProfileFKCol = placedAgentDBTable.getDBColumn("CommissionProfileFK").getFullyQualifiedColumnName();

        // CommissionProfile
        DBTable commissionProfileDBTable = DBTable.getDBTableForTable("CommissionProfile");
        String commissionProfileTable = commissionProfileDBTable.getFullyQualifiedTableName();
        String commissionProfilePKCol = commissionProfileDBTable.getDBColumn("CommissionProfilePK").getFullyQualifiedColumnName();
        String contractCodeCTCol = commissionProfileDBTable.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +

                " INNER JOIN " + placedAgentTable +
                " ON " + placedAgentFKCol + " = " + placedAgentPKCol +

                " INNER JOIN " + commissionProfileTable +
                " ON " + commissionProfileFKCol + " = " + commissionProfilePKCol +

                " WHERE " + contractCodeCTCol + " = '" + contractCodeCT + "'";

        return (ParticipatingAgentVO[]) executeQuery(ParticipatingAgentVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}
