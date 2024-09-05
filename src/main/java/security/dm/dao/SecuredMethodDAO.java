/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 20, 2004
 * Time: 11:45:26 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package security.dm.dao;

import edit.common.vo.SecuredMethodVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;


public class SecuredMethodDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    private final DBTable FILTERED_ROLE_DBTABLE;
    private final String FILTERED_ROLE_TABLENAME;

    private final DBTable COMPONENT_METHOD_TABLE;
    private final String  COMPONENT_METHOD_TABLENAME;

    private final DBTable ROLE_DBTABLE;
    private final String  ROLE_TABLENAME;

    // ENGINE SCHEMA
    private final DBTable PRODUCT_STRUCTURE_DBTABLE;
    private final String PRODUCT_STRUCTURE_TABLENAME;


    public SecuredMethodDAO()
    {
        POOLNAME  = ConnectionFactory.SECURITY_POOL;
        DBTABLE   = DBTable.getDBTableForTable("SecuredMethod");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();

        ROLE_DBTABLE = DBTable.getDBTableForTable("Role");
        ROLE_TABLENAME = ROLE_DBTABLE.getFullyQualifiedTableName();

        FILTERED_ROLE_DBTABLE = DBTable.getDBTableForTable("FilteredRole");
        FILTERED_ROLE_TABLENAME  = FILTERED_ROLE_DBTABLE.getFullyQualifiedTableName();

        COMPONENT_METHOD_TABLE = DBTable.getDBTableForTable("ComponentMethod");
        COMPONENT_METHOD_TABLENAME = COMPONENT_METHOD_TABLE.getFullyQualifiedTableName();

        // FROM ENGINE SCHEMA
        PRODUCT_STRUCTURE_DBTABLE = DBTable.getDBTableForTable("ProductStructure");
        PRODUCT_STRUCTURE_TABLENAME = PRODUCT_STRUCTURE_DBTABLE.getFullyQualifiedTableName();

    }


    /**
     * Join the SecuredMethod, ComponentMethod and FilteredRole tables
     * @param componentNameCT
     * @param rolePK
     * @param productStructurePK
     * @return
     */
    public SecuredMethodVO[] findByComponentNameCTRoleProductStructure(
                                                    String componentNameCT,
                                                    long rolePK,
                                                    long productStructurePK)
    {
        String cMethodComponentNameCTCol   = COMPONENT_METHOD_TABLE.getDBColumn("ComponentNameCT").getFullyQualifiedColumnName();
        String cMethodComponentMethodPKCol = COMPONENT_METHOD_TABLE.getDBColumn("ComponentMethodPK").getFullyQualifiedColumnName();

        String sMethodComponentMethodFKCol = DBTABLE.getDBColumn("ComponentMethodFK").getFullyQualifiedColumnName();
        String sMethodFilteredRoleFKCol = DBTABLE.getDBColumn("FilteredRoleFK").getFullyQualifiedColumnName();

        String fRoleRoleFKCol = FILTERED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();
        String fRolefilteredRolePKCol = FILTERED_ROLE_DBTABLE.getDBColumn("FilteredRolePK").getFullyQualifiedColumnName();
        String fRoleProdStructureFKCol = FILTERED_ROLE_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

		String sql = " SELECT " + TABLENAME + ".* FROM " +
                                        TABLENAME +
                                ", " +  COMPONENT_METHOD_TABLENAME +
                                ", " +  FILTERED_ROLE_TABLENAME +
                     " WHERE " + cMethodComponentNameCTCol   + " = '" + componentNameCT + "'" +
                     " AND "   + fRoleRoleFKCol            + " = " + rolePK +
                     " AND "   + fRolefilteredRolePKCol + " = " + sMethodFilteredRoleFKCol +
                     " AND "   + fRoleProdStructureFKCol + " = " + productStructurePK +
                     " AND "   + sMethodComponentMethodFKCol + " = " + cMethodComponentMethodPKCol;


        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Get all SecuredMethodVO's for a FilteredRole - productStructureFK
     * and RoleFK.
     * @param roleFK
     * @param productStructureFK
     * @return
     */
    public SecuredMethodVO[] findByRoleMethodProductStructure(
            long roleFK, long productStructureFK)
    {
        String secMethodFilteredRoleFKCol =
                DBTABLE.getDBColumn("FilteredRoleFK").getFullyQualifiedColumnName();

        String filteredRoleRoleFKCol =
                FILTERED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String filteredRoleProductStructureFKCol =
                FILTERED_ROLE_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String filteredRoleFilteredRolePKCol =
                FILTERED_ROLE_DBTABLE.getDBColumn("FilteredRolePK").getFullyQualifiedColumnName();

        String sql =
                " SELECT " + TABLENAME + ".* " + " FROM " + TABLENAME + "," + FILTERED_ROLE_TABLENAME +
                " WHERE " +
                secMethodFilteredRoleFKCol + " = " + filteredRoleFilteredRolePKCol +
                " AND " + filteredRoleRoleFKCol + " = " + roleFK +
                " AND " + filteredRoleProductStructureFKCol + " = " + productStructureFK;

        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }


    public SecuredMethodVO[] findByRoleMethodProductStructure(
            long rolePK, long componentMethodPK, long productStructurePK)
    {

        String secMethodFilteredRoleFKCol    =
                DBTABLE.getDBColumn("FilteredRoleFK").getFullyQualifiedColumnName();

        String secMethodComponentMethodFKCol =
                DBTABLE.getDBColumn("ComponentMethodFK").getFullyQualifiedColumnName();

        String filteredRoleRoleFKCol =
                FILTERED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String filteredRoleProductStructureFKCol =
                FILTERED_ROLE_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String filteredRoleFilteredRolePKCol =
                FILTERED_ROLE_DBTABLE.getDBColumn("FilteredRolePK").getFullyQualifiedColumnName();

        String sql =
                " SELECT " + TABLENAME + ".* " + " FROM " + TABLENAME + "," + FILTERED_ROLE_TABLENAME +
                " WHERE " +
                secMethodComponentMethodFKCol + " = " + componentMethodPK +
                " AND " + secMethodFilteredRoleFKCol + " = " + filteredRoleFilteredRolePKCol +
                " AND " + filteredRoleRoleFKCol + " = " + rolePK +
                " AND " + filteredRoleProductStructureFKCol + " = " + productStructurePK;

        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }


    public SecuredMethodVO[] findByComponentMethodPK(long componentMethodPK)
    {
        String componentMethodFKCol = DBTABLE.getDBColumn("ComponentMethodFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + componentMethodFKCol + " = " + componentMethodPK;

        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Use this only for conversion from old to new database structure for security.
     * @see SecuredMethodDAO#findByRolePK(long)
     * @param rolePK
     * @return
     * @deprecated
     */
    public SecuredMethodVO[] findByRolePK_DBConversionOnly(long rolePK)
    {
        String roleFKCol = DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + roleFKCol + " = " + rolePK;

        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public SecuredMethodVO[] findByRolePK(long rolePK)
    {
        // SECUREDMETHOD
        String secMethfilteredRoleFKCol = DBTABLE.getDBColumn("FilteredRoleFK").getFullyQualifiedColumnName();

        // FILTERED ROLE
        String filteredRolePKCol = FILTERED_ROLE_DBTABLE.getDBColumn("FilteredRolePK").getFullyQualifiedColumnName();
        String filteredRoleRoleFKCol = FILTERED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql =
                " SELECT " + TABLENAME + ".* " +
                " FROM " + TABLENAME + ", " +
                        FILTERED_ROLE_TABLENAME +
                " WHERE " +
                     secMethfilteredRoleFKCol + " = " + filteredRolePKCol +
                     " AND " + filteredRoleRoleFKCol + " = " + rolePK;

        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    public SecuredMethodVO[] findByPK(long securedMethodPK)
    {
        String pkCol = DBTABLE.getDBColumn("SecuredMethodPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + pkCol + " = " + securedMethodPK;

        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    public SecuredMethodVO[] findAll()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (SecuredMethodVO[]) executeQuery(SecuredMethodVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Do this directly instead of instantiating lots of SecuredMethod
     * objects and then deleting each separately.  Reduces DB roundtrips
     * to one.
     * @param filteredRoleFK
     */
    public void deleteAllSecuredMethodsForFilteredRole(long filteredRoleFK)
    {

        Connection crudConn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

        String filteredRoleFKCol = DBTABLE.getDBColumn("FilteredRoleFK").getFullyQualifiedColumnName();

        String sql = " DELETE FROM " + TABLENAME +
                " WHERE " + filteredRoleFKCol + " = " + filteredRoleFK;

        PreparedStatement ps = null;

        try
        {
            ps = crudConn.prepareStatement(sql.toString());

            ps.executeUpdate();
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
                if (ps != null)
                    ps.close();

                if (crudConn != null)
                    crudConn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

    }


}