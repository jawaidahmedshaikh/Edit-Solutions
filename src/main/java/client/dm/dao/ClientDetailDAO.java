/*
 * ClientDetailDAO.java      Version 1.1  09/24/2001 .
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package client.dm.dao;

import edit.common.vo.*;
import edit.services.db.*;

import java.util.*;
import java.sql.*;


public class ClientDetailDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    private final DBTable CLIENT_ADDRESS_DBTABLE;
    private final String CLIENT_ADDRESS_TABLENAME;


    public ClientDetailDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ClientDetail");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
        CLIENT_ADDRESS_DBTABLE   = DBTable.getDBTableForTable("ClientAddress");
        CLIENT_ADDRESS_TABLENAME   = CLIENT_ADDRESS_DBTABLE.getFullyQualifiedTableName();

    }

	public ClientDetailVO[] findByTaxId(String taxId, boolean includeChildVOs, List voExclusionList)
    {
        String taxIdentificationCol = DBTABLE.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + taxIdentificationCol + " = '" + taxId + "'";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

    public ClientDetailVO[] findByAgentId(String agentId, boolean includeChildVOs, List voExclusionList)
    {
        String clientIdentificationCol = DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientIdentificationCol + " = '" + agentId + "'";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

	public ClientDetailVO[] findByClientPK(long clientPK, boolean includeChildVOs, List voExclusionList)
    {
        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailPKCol + " = " + clientPK;


        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}



    public ClientDetailVO[] findByPartialLastName(String partialLastName, boolean includeChildVOs, List voExclusionList)
    {
        String lastNameCol      = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String corporateNameCol = DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE (UPPER(" + lastNameCol + ") LIKE UPPER ('" + partialLastName + "%')" +
                     " OR UPPER(" + corporateNameCol + ") LIKE UPPER ('" + partialLastName + "%'))";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

    public ClientDetailVO[] findByPartialLastNameWithoutOvrds(String partialLastName, boolean includeChildVOs, List voExclusionList)
     {
         String lastNameCol      = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
         String corporateNameCol = DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();
         String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

         String sql = "SELECT * FROM " + TABLENAME +
                      " WHERE (UPPER(" + lastNameCol + ") LIKE UPPER ('" + partialLastName + "%')" +
                      " OR UPPER(" + corporateNameCol + ") LIKE UPPER ('" + partialLastName + "%'))" +
                      " AND " + overrideStatusCol + " IS NULL";

         return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                 sql,
                                                  POOLNAME,
                                                   includeChildVOs,
                                                    voExclusionList);
     }

    /**
     * Finder.
     * @param partialCorporateName
     * @return
     */
	public ClientDetailVO[] findBy_PartialCorporateName(String partialCorporateName)
    {
        String corporateNameCol = DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + corporateNameCol + ") LIKE UPPER ('" + partialCorporateName + "%')";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

	public ClientDetailVO[] findByPartialLastName_AND_BirthDate(String partialLastName,
                                                                 String birthDate,
                                                                  boolean includeChildVOs,
                                                                   List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String birthDateCol = DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE (UPPER(" + lastNameCol + ") LIKE UPPER ('" + partialLastName + "%')" +
                     " AND " + birthDateCol + " = ?";

        ClientDetailVO[] clientDetailVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(birthDate));

            clientDetailVOs = (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      includeChildVOs,
                                                      voExclusionList);
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
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return clientDetailVOs;
	}

	public ClientDetailVO[] findByLastNamePartialFirstName(String lastName,
                                                            String partialFirstName,
                                                             boolean includeChildVOs,
                                                              List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol = DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + lastNameCol + ") = UPPER('" + lastName + "')" +
                     " AND UPPER(" + firstNameCol + ") LIKE UPPER ('" + partialFirstName + "%')";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

    public ClientDetailVO[] findByLastNamePartialFirstNameWithoutOvrds(String lastName,
                                                            String partialFirstName,
                                                             boolean includeChildVOs,
                                                              List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol = DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + lastNameCol + ") = UPPER('" + lastName + "')" +
                     " AND UPPER(" + firstNameCol + ") LIKE UPPER ('" + partialFirstName + "%')" +
                     " AND " + overrideStatusCol + " IS NULL";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
    }


	public ClientDetailVO[] findByLastNamePartialFirstName_AND_BirthDate(String lastName,
                                                                          String partialFirstName,
                                                                           String birthDate,
                                                                            boolean includeChildVOs,
                                                                             List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol = DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
        String birthDateCol = DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + lastNameCol + ") = UPPER('" + lastName + "')" +
                     " AND UPPER(" + firstNameCol + ") LIKE UPPER ('" + partialFirstName + "%')" +
                     " AND " + birthDateCol + " = ?";

        ClientDetailVO[] clientDetailVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(birthDate));

            clientDetailVOs = (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      includeChildVOs,
                                                      voExclusionList);
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
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return clientDetailVOs;
	}

	public ClientDetailVO[] findByLastName(String lastName, boolean includeChildVOs, List voExclusionList)
    {
        String lastNameCol = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + lastNameCol + " = '" + lastName + "'";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

 	public ClientDetailVO[] findByLastNameWithoutOvrds(String lastName, boolean includeChildVOs, List voExclusionList)
    {
        String lastNameCol = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + lastNameCol + " = '" + lastName + "'" +
                     " AND " + overrideStatusCol + " IS NULL";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}
	public ClientDetailVO[] findByLastName_AND_BirthDate(String lastName,
                                                          String birthDate,
                                                           boolean includeChildVOs,
                                                            List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String birthDateCol = DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + lastNameCol + " = '" + lastName + "'" +
                     " AND " + birthDateCol + " = ?";

        ClientDetailVO[] clientDetailVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(birthDate));

            clientDetailVOs = (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      includeChildVOs,
                                                      voExclusionList);
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
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return clientDetailVOs;
	}

	public ClientDetailVO[] findByLastName_AND_BirthDateWithoutOvrds(String lastName,
                                                          String birthDate,
                                                           boolean includeChildVOs,
                                                            List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String birthDateCol = DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + lastNameCol + " = '" + lastName + "'" +
                     " AND " + birthDateCol + " = ?" +
                     " AND " + overrideStatusCol + " IS NULL";

        ClientDetailVO[] clientDetailVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(birthDate));

            clientDetailVOs = (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      includeChildVOs,
                                                      voExclusionList);
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
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return clientDetailVOs;
	}

	public ClientDetailVO[] findByLastNamePartialFirstName_AND_BirthDateWithoutOvrds(String lastName,
                                                                          String partialFirstName,
                                                                           String birthDate,
                                                                            boolean includeChildVOs,
                                                                             List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol = DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
        String birthDateCol = DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + lastNameCol + ") = UPPER('" + lastName + "')" +
                     " AND UPPER(" + firstNameCol + ") LIKE UPPER ('" + partialFirstName + "%')" +
                     " AND " + birthDateCol + " = ?" +
                     " AND " + overrideStatusCol + " IS NULL";

        ClientDetailVO[] clientDetailVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(birthDate));

            clientDetailVOs = (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      includeChildVOs,
                                                      voExclusionList);
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
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return clientDetailVOs;
	}

	public ClientDetailVO[] findByPartialLastName_AND_BirthDateWithoutOvrds(String partialLastName,
                                                                 String birthDate,
                                                                  boolean includeChildVOs,
                                                                   List voExclusionList)
    {
        String lastNameCol  = DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String birthDateCol = DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE (UPPER(" + lastNameCol + ") LIKE UPPER ('" + partialLastName + "%')" +
                     " AND " + birthDateCol + " = ?" +
                     " AND " + overrideStatusCol + " IS NULL)";

        ClientDetailVO[] clientDetailVOs = null;
        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(birthDate));

            clientDetailVOs = (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                      ps,
                                                      POOLNAME,
                                                      includeChildVOs,
                                                      voExclusionList);
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
              if (ps != null) ps.close();
              ps = null;
              if (conn != null) conn.close();
              conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return clientDetailVOs;
	}

	public ClientDetailVO[] findByTaxIdWithoutOvrds(String taxId, boolean includeChildVOs, List voExclusionList)
    {
        String taxIdentificationCol = DBTABLE.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();
        String overrideStatusCol = DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + taxIdentificationCol + " = '" + taxId + "'" +
                     " AND " + overrideStatusCol + " IS NULL";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}
	public ClientDetailVO[] findAll()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  true,
                                                    null);
	}

	public ClientDetailVO[] findByClientPK(long clientPK)
    {
        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailPKCol + " = " + clientPK;

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  true,
                                                    null);
	}

	public ClientDetailVO[] findByClientId(String clientId)
    {
        String clientIdentificationCol = DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientIdentificationCol + " = '" + clientId + "'";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  true,
                                                    null);
	}

	public ClientDetailVO[] findByClientIdentification(String clientIdentification,
                                                        boolean includeChildsVOs,
                                                         List voExclusionList)
    {
        String clientIdentificationCol = DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientIdentificationCol + " = '" + clientIdentification + "'";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildsVOs,
                                                   voExclusionList);
	}

	public ClientDetailVO[] findByClientDetailPK(long clientDetailPK,
                                                  boolean includeChildVOs,
                                                   List voExclusionList)
    {
        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + clientDetailPKCol + " = " + clientDetailPK;

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
	}

    /**
     * Finder.
     * @param segmentPK
     * @param roleTypeCT
     * @return
     */
    public ClientDetailVO[] findBy_SegmentPK_RoleType(long segmentPK, String roleTypeCT)
    {
        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String roleTypeCTCol = clientRoleDBTable.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        DBTable contractClientDBTable = DBTable.getDBTableForTable("ContractClient");
        String contractClientTable = contractClientDBTable.getFullyQualifiedTableName();
        String segmentFKCol = contractClientDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = contractClientDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();

        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        String segmentTable = segmentDBTable.getFullyQualifiedTableName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                " INNER JOIN " + contractClientTable +
                " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                " INNER JOIN " + segmentTable +
                " ON " + segmentFKCol + " = " + segmentPKCol +
                " WHERE " + segmentPKCol + " = " + segmentPK +
                " AND " + roleTypeCTCol + " = '" + roleTypeCT + "'";  

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public ClientDetailVO[] findBy_ReinsurerPK(long reinsurerPK)
    {
        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        DBTable reinsurerDBTable = DBTable.getDBTableForTable("Reinsurer");
        String reinsurerTable = reinsurerDBTable.getFullyQualifiedTableName();
        String reinsurerPKCol = reinsurerDBTable.getDBColumn("ReinsurerPK").getFullyQualifiedColumnName();
        String clientDetailFKCol = reinsurerDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + reinsurerTable +
                " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                " WHERE " + reinsurerPKCol + " = " + reinsurerPK;

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class, sql, POOLNAME, false, null);
    }

    /**
     * Finder.
     * @param contractClientPK
     * @return
     */
    public ClientDetailVO[] findBy_ContractClientPK(long contractClientPK)
    {
        // ClientDetail
        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientDetailFKCol = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();

        // ContractClient
        DBTable contractClientDBTable = DBTable.getDBTableForTable("ContractClient");
        String contractClientTable =  contractClientDBTable.getFullyQualifiedTableName();
        String clientRoleFKCol = contractClientDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String contractClientPKCol = contractClientDBTable.getDBColumn("ContractClientPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                " INNER JOIN " + contractClientTable +
                " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                " AND " + contractClientPKCol + " = " + contractClientPK;

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class, sql, POOLNAME, false, null);
    }

   /**
     * Finder. Finds the ClientDetail with the "biggest" ContractClient.TerminationDate for the specified RoleTypeCT and Segment. This
     * guarantees the active ContractClient of the specifed RoleTypeCT.
     * @param segmentPK
     * @param roleTypeCT
     * @return
     */
    public ClientDetailVO[] findActive_By_SegmentPK_RoleType(long segmentPK, String roleTypeCT)
    {
        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();

        // ClientRole
        DBTable clientRoleDBTable = DBTable.getDBTableForTable("ClientRole");
        String clientRoleTable = clientRoleDBTable.getFullyQualifiedTableName();
        String clientRolePKCol = clientRoleDBTable.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String clientDetailFKCol = clientRoleDBTable.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String roleTypeCTCol = clientRoleDBTable.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();

        // ContractClient
        DBTable contractClientDBTable = DBTable.getDBTableForTable("ContractClient");
        String contractClientTable = contractClientDBTable.getFullyQualifiedTableName();
        String segmentFKCol = contractClientDBTable.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol = contractClientDBTable.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String terminationDateCol = contractClientDBTable.getDBColumn("TerminationDate").getFullyQualifiedColumnName();

        // Segment
        DBTable segmentDBTable = DBTable.getDBTableForTable("Segment");
        String segmentTable = segmentDBTable.getFullyQualifiedTableName();
        String segmentPKCol = segmentDBTable.getDBColumn("SegmentPK").getFullyQualifiedColumnName();

        String sql;
        sql =   " SELECT " + TABLENAME + ".*" +
                " FROM " + TABLENAME +
                " INNER JOIN " + clientRoleTable +
                " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                " INNER JOIN " + contractClientTable +
                " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                " INNER JOIN " + segmentTable +
                " ON " + segmentFKCol + " = " + segmentPKCol +
                " WHERE " + segmentPKCol + " = " + segmentPK +
                " AND " + roleTypeCTCol + " = '" + roleTypeCT + "'" +
                " AND " + terminationDateCol + " = " +
                        " ((SELECT MAX(" + terminationDateCol + ")" +
                        " FROM " + TABLENAME +
                        " INNER JOIN " + clientRoleTable +
                        " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                        " INNER JOIN " + contractClientTable +
                        " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                        " INNER JOIN " + segmentTable +
                        " ON " + segmentFKCol + " = " + segmentPKCol +
                        " WHERE " + segmentPKCol + " = " + segmentPK +
                        " AND " + roleTypeCTCol + " = '" + roleTypeCT + "'))" +
                        " OR IS NULL)";

        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class, sql, POOLNAME, false, null);
    }

//    public ClientDetailVO[] findByClientPK(long clientPK, boolean includeChildVOs, List voExclusionList)
//    {
//        String clientDetailPKCol = DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
//
//        String sql = "SELECT * FROM " + TABLENAME +
//                     " WHERE " + clientDetailPKCol + " = " + clientPK;
//
//        boolean addressExcluded = examineExclusionList(voExclusionList);
//        //Override Addresses should not be returned
//        if (includeChildVOs && !addressExcluded)
//        {
//            String overrideStatusCol = CLIENT_ADDRESS_DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();
//            String clientDetailFKCol = CLIENT_ADDRESS_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
//
//            sql =   "SELECT * FROM " + TABLENAME + ", " + CLIENT_ADDRESS_TABLENAME +
//                     " WHERE " + clientDetailPKCol + " = " + clientPK  +
//                     " AND  " +clientDetailPKCol + " = " + clientDetailFKCol +
//                     " AND (" + overrideStatusCol + " IS NULL OR NOT " + overrideStatusCol + " = 'O')";
//        }
//
//        return (ClientDetailVO[]) executeQuery(ClientDetailVO.class,
//                                                sql,
//                                                 POOLNAME,
//                                                  includeChildVOs,
//                                                   voExclusionList);
//    }

//   private boolean examineExclusionList(List voExclusionList)
//    {
//        boolean addressExcluded = false;
//
//        for (Iterator iterator = voExclusionList.iterator(); iterator.hasNext();)
//        {
//              if (iterator.next().equals(ClientAddressVO.class))
//              {
//                  addressExcluded = true;
//                  break;
//              }
//        }
//
//        return addressExcluded;
//    }
}