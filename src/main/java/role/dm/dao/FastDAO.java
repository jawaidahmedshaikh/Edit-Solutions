/*
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package role.dm.dao;

import edit.common.vo.*;


import edit.services.db.*;
import fission.utility.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FastDAO extends AbstractFastDAO
{
    private final String POOLNAME;

    private final DBTable AGENT_CONTRACT_DBTABLE;
    private final DBTable AGENT_DBTABLE;
    private final DBTable CLIENT_ROLE_DBTABLE;
    private final DBTable CLIENT_DETAIL_DBTABLE;
    private final DBTable PLACED_AGENT_DBTABLE;

    private final String AGENT_CONTRACT_TABLENAME;
    private final String AGENT_TABLENAME;
    private final String CLIENT_ROLE_TABLENAME;
    private final String CLIENT_DETAIL_TABLENAME;
    private final String PLACED_AGENT_TABLENAME;


    public FastDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        AGENT_CONTRACT_DBTABLE      = DBTable.getDBTableForTable("AgentContract");
        AGENT_DBTABLE               = DBTable.getDBTableForTable("Agent");
        CLIENT_ROLE_DBTABLE         = DBTable.getDBTableForTable("ClientRole");
        CLIENT_DETAIL_DBTABLE       = DBTable.getDBTableForTable("ClientDetail");
        PLACED_AGENT_DBTABLE        = DBTable.getDBTableForTable("PlacedAgent");

        AGENT_CONTRACT_TABLENAME      = AGENT_CONTRACT_DBTABLE.getFullyQualifiedTableName();
        AGENT_TABLENAME               = AGENT_DBTABLE.getFullyQualifiedTableName();
        CLIENT_ROLE_TABLENAME         = CLIENT_ROLE_DBTABLE.getFullyQualifiedTableName();
        CLIENT_DETAIL_TABLENAME       = CLIENT_DETAIL_DBTABLE.getFullyQualifiedTableName();
        PLACED_AGENT_TABLENAME        = PLACED_AGENT_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finds the AgentContractVO.AgentVO.ClientRoleVO.ClientDetailVO chain by AgentContractPK using a join instead of object composition.
     * @param agentContractPK
     * @return
     */
    public ClientRoleVO findByAgentContractPK(long agentContractPK)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String agentFKCol         = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String agentContractPKCol = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();

        String agentPKCol      = AGENT_DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String clientRoleFKCol = AGENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        String clientRolePKCol = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();

        String sql;

        sql =   " SELECT " + CLIENT_ROLE_TABLENAME + ".* FROM " + AGENT_CONTRACT_TABLENAME +
                " INNER JOIN " + AGENT_TABLENAME + " ON " + agentFKCol + " = " + agentPKCol +
                " INNER JOIN " + CLIENT_ROLE_TABLENAME + " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                " WHERE " + agentContractPKCol + " = " + agentContractPK;

        ClientRoleVO clientRoleVO = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            VOClass clientRoleVOClass = VOClass.getVOClassMetaData(ClientRoleVO.class);

            while (rs.next())
            {
                clientRoleVO = (ClientRoleVO) CRUD.populateVOFromResultSetRow(rs, clientRoleVOClass);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return clientRoleVO;
    }

    /**
     * Associates the AgentContractVO, AgentVO, ClientRoleVO, CommissionContractVO, and ClientDetailVO for each PlacedAgentVO within the
     * branch. Since it is often unecessary to compose each and every node in the branch, the client can specify the
     * depth of nodes to compose. For example, if the branch has 5 nodes, and the depth is set to 2, only the first two
     * nodes will be composed.
     * @param placedAgentBranchVO
     * @param depth
     * @return
     */
    public PlacedAgentBranchVO composePlacedAgentBranchVO(PlacedAgentBranchVO placedAgentBranchVO, int depth)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String agentFKCol              = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String agentContractPKCol      = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();

        String agentPKCol              = AGENT_DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String clientRoleFKCol         = AGENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        String clientRolePKCol         = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol       = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        PlacedAgentVO placedAgentVO = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            int branchCount = placedAgentBranchVO.getPlacedAgentVOCount();

            for (int i = 0; i < depth && i < branchCount; i++)
            {
                placedAgentVO = placedAgentBranchVO.getPlacedAgentVO(i);

                long agentContractPK = placedAgentVO.getAgentContractFK();

                String sql;

                sql =   " SELECT * FROM " + AGENT_CONTRACT_TABLENAME +
                        " INNER JOIN " + AGENT_TABLENAME +
                        " ON " + agentFKCol + " = " + agentPKCol +
                        " INNER JOIN " + CLIENT_ROLE_TABLENAME +
                        " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                        " INNER JOIN " + CLIENT_DETAIL_TABLENAME +
                        " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                        " WHERE " + agentContractPKCol + " = " + agentContractPK;

                s = conn.createStatement();

                rs = s.executeQuery(sql);

                VOClass agentContractVOClass = VOClass.getVOClassMetaData(AgentContractVO.class);
                VOClass agentVOClass = VOClass.getVOClassMetaData(AgentVO.class);
                VOClass clientRoleVOClass = VOClass.getVOClassMetaData(ClientRoleVO.class);
                VOClass clientDetailVOClass = VOClass.getVOClassMetaData(ClientDetailVO.class);

                while (rs.next())
                {
                    AgentContractVO agentContractVO = (AgentContractVO) CRUD.populateVOFromResultSetRow(rs, agentContractVOClass);
                    AgentVO agentVO = (AgentVO) CRUD.populateVOFromResultSetRow(rs, agentVOClass);
                    ClientRoleVO clientRoleVO = (ClientRoleVO) CRUD.populateVOFromResultSetRow(rs, clientRoleVOClass);
                    ClientDetailVO clientDetailVO = (ClientDetailVO) CRUD.populateVOFromResultSetRow(rs, clientDetailVOClass);

                    agentContractVO.setParentVO(AgentVO.class, agentVO);
                    agentVO.setParentVO(ClientRoleVO.class, clientRoleVO);
                    clientRoleVO.setParentVO(ClientDetailVO.class, clientDetailVO);

                    placedAgentVO.setParentVO(AgentContractVO.class, agentContractVO);
                }

                if (rs != null)
                {
                    rs.close();

                    rs = null;
                }

                if (s != null)
                {
                    s.close();

                    s = null;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();

                if (s != null) s.close();

                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return placedAgentBranchVO;
    }

    /**
     * Finds the AgentContractVO.AgentVO.ClientRoleVO.ClientDetailVO chain by PlacedAgentPK using a join instead of object composition.
     * @param contractCodeCT
     * @return
     */
    public AgentContractVO[] findAgentContractVOsByAgentName_AND_ContractCodeCT(String agentName, String contractCodeCT)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String agentFKCol              = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String contractCodeCTCol       = AGENT_CONTRACT_DBTABLE.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();

        String agentPKCol              = AGENT_DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String clientRoleFKCol         = AGENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        String clientRolePKCol         = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol       = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String lastNameCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String trustTypeCTCol          = CLIENT_DETAIL_DBTABLE.getDBColumn("TrustTypeCT").getFullyQualifiedColumnName();
        String corporateNameCol        = CLIENT_DETAIL_DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();


        String sql = " SELECT *" +
                     " FROM " + AGENT_CONTRACT_TABLENAME +
                     " INNER JOIN " + AGENT_TABLENAME + " ON " + agentFKCol + " = " + agentPKCol +
                     " INNER JOIN " + CLIENT_ROLE_TABLENAME + " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                     " INNER JOIN " + CLIENT_DETAIL_TABLENAME + " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                     " WHERE " + contractCodeCTCol + " = '" +  contractCodeCT + "'" +
                     " AND " + lastNameCol + " LIKE '" + agentName + "%'" +
                     " AND " + trustTypeCTCol + " = 'Individual'" +
                     " UNION " +
                     " SELECT *" +
                     " FROM " + AGENT_CONTRACT_TABLENAME +
                     " INNER JOIN " + AGENT_TABLENAME + " ON " + agentFKCol + " = " + agentPKCol +
                     " INNER JOIN " + CLIENT_ROLE_TABLENAME + " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                     " INNER JOIN " + CLIENT_DETAIL_TABLENAME + " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                     " WHERE " + contractCodeCTCol + " = '" + contractCodeCT + "'" +
                     " AND " + corporateNameCol + " LIKE '" + agentName + "%'" +
                     " AND " + trustTypeCTCol + " = 'Corporate'";

        List agentContractVOs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            VOClass agentContractVOClass = VOClass.getVOClassMetaData(AgentContractVO.class);
            VOClass agentVOClass = VOClass.getVOClassMetaData(AgentVO.class);
            VOClass clientRoleVOClass = VOClass.getVOClassMetaData(ClientRoleVO.class);
            VOClass clientDetailVOClass = VOClass.getVOClassMetaData(ClientDetailVO.class);

            while (rs.next())
            {
                AgentContractVO agentContractVO = (AgentContractVO) CRUD.populateVOFromResultSetRow(rs, agentContractVOClass);
                AgentVO agentVO = (AgentVO) CRUD.populateVOFromResultSetRow(rs, agentVOClass);
                ClientRoleVO clientRoleVO = (ClientRoleVO) CRUD.populateVOFromResultSetRow(rs, clientRoleVOClass);
                ClientDetailVO clientDetailVO = (ClientDetailVO) CRUD.populateVOFromResultSetRow(rs, clientDetailVOClass);

                agentContractVO.setParentVO(AgentVO.class, agentVO);
                agentVO.setParentVO(ClientRoleVO.class, clientRoleVO);
                clientRoleVO.setParentVO(ClientDetailVO.class, clientDetailVO);

                agentContractVOs.add(agentContractVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (agentContractVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return ((AgentContractVO[]) agentContractVOs.toArray(new AgentContractVO[agentContractVOs.size()]));
        }
    }

    /**
     * Finds the AgentContractVO.AgentVO.ClientRoleVO.ClientDetailVO chain by PlacedAgentPK using a join instead of object composition.
     * @param contractCodeCT
     * @return
     */
    public AgentContractVO[] findAgentContractVOsByAgentId_AND_ContractCodeCT(String agentId, String contractCodeCT)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String agentFKCol              = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();
        String contractCodeCTCol = AGENT_CONTRACT_DBTABLE.getDBColumn("ContractCodeCT").getFullyQualifiedColumnName();

        String agentPKCol              = AGENT_DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String clientRoleFKCol         = AGENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String agentNumberCol          = AGENT_DBTABLE.getDBColumn("AgentNumber").getFullyQualifiedColumnName();

        String clientRolePKCol         = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol       = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + AGENT_CONTRACT_TABLENAME +
                     " INNER JOIN " + AGENT_TABLENAME + " ON " + agentFKCol + " = " + agentPKCol +
                     " INNER JOIN " + CLIENT_ROLE_TABLENAME + " ON " + clientRoleFKCol + " = " + clientRolePKCol +
                     " INNER JOIN " + CLIENT_DETAIL_TABLENAME + " ON " + clientDetailFKCol + " = " + clientDetailPKCol +
                     " WHERE " + contractCodeCTCol + " = '" + contractCodeCT + "'" +
                     " AND " + agentNumberCol + " = '" + agentId + "'";

        List agentContractVOs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            VOClass agentContractVOClass = VOClass.getVOClassMetaData(AgentContractVO.class);
            VOClass agentVOClass = VOClass.getVOClassMetaData(AgentVO.class);
            VOClass clientRoleVOClass = VOClass.getVOClassMetaData(ClientRoleVO.class);
            VOClass clientDetailVOClass = VOClass.getVOClassMetaData(ClientDetailVO.class);

            while (rs.next())
            {
                AgentContractVO agentContractVO = (AgentContractVO) CRUD.populateVOFromResultSetRow(rs, agentContractVOClass);
                AgentVO agentVO = (AgentVO) CRUD.populateVOFromResultSetRow(rs, agentVOClass);
                ClientRoleVO clientRoleVO = (ClientRoleVO) CRUD.populateVOFromResultSetRow(rs, clientRoleVOClass);
                ClientDetailVO clientDetailVO = (ClientDetailVO) CRUD.populateVOFromResultSetRow(rs, clientDetailVOClass);

                agentContractVO.setParentVO(AgentVO.class, agentVO);
                agentVO.setParentVO(ClientRoleVO.class, clientRoleVO);
                clientRoleVO.setParentVO(ClientDetailVO.class, clientDetailVO);

                agentContractVOs.add(agentContractVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (agentContractVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return ((AgentContractVO[]) agentContractVOs.toArray(new AgentContractVO[agentContractVOs.size()]));
        }
    }

    public long[] findClientRolePKsByClientRolePKAndRoleType(long clientRolePK, String roleType)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientRolePKCol = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol   = CLIENT_ROLE_DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        String sql;

        sql =   " SELECT * FROM " + CLIENT_ROLE_TABLENAME +
                " WHERE " + clientRolePKCol + " = " + clientRolePK +
                " AND " + roleTypeCTCol + " = '" + roleType + "'";

        List clientRolePKs = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            while (rs.next())
            {
                clientRolePKs.add(new Long(rs.getLong("ClientRolePK")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (clientRolePKs.size() == 0)
        {
            return null;
        }
        else
        {
            return Util.convertLongToPrim((Long[]) clientRolePKs.toArray(new Long[clientRolePKs.size()]));
        }
    }


    public long findClientRolePKByPlacedAgentPK(long placedAgentPK)
    {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String placedAgentPKCol   = PLACED_AGENT_DBTABLE.getDBColumn("PlacedAgentPK").getFullyQualifiedColumnName();
        String agentContractFKCol = PLACED_AGENT_DBTABLE.getDBColumn( "AgentContractFK").getFullyQualifiedColumnName();

        String agentContractPKCol = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentContractPK").getFullyQualifiedColumnName();
        String agentFKCol         = AGENT_CONTRACT_DBTABLE.getDBColumn("AgentFK").getFullyQualifiedColumnName();

        String agentPKCol         = AGENT_DBTABLE.getDBColumn("AgentPK").getFullyQualifiedColumnName();
        String clientRoleFKCol    = AGENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        String clientRolePKCol    = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();

        String sql;

        sql =   " SELECT " + clientRolePKCol +
                " FROM " + PLACED_AGENT_TABLENAME + ", " + AGENT_CONTRACT_TABLENAME + ", " + AGENT_TABLENAME + ", " + CLIENT_ROLE_TABLENAME +
                " WHERE " + placedAgentPKCol + " = " + placedAgentPK +
                " AND " + agentContractFKCol + " = " + agentContractPKCol +
                " AND " + agentFKCol + " = " + agentPKCol +
                " AND " + clientRoleFKCol + " = " + clientRolePKCol;

        long clientRolePK = 0;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            if (rs.next())
            {
                clientRolePK = rs.getLong(1);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return clientRolePK;
    }
}