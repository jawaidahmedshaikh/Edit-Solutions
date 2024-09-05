package security.dm.dao;

import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.db.ConnectionFactory;
import edit.common.vo.OperatorVO;
import edit.common.vo.FilteredRoleVO;
import edit.common.vo.RoleVO;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilteredRoleDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    private final DBTable COMPONENT_METHOD_TABLE;
    private final String  COMPONENT_METHOD_TABLENAME;

    private final DBTable ROLE_DBTABLE;
    private final String  ROLE_TABLENAME;

    private final DBTable IMPLIED_ROLE_DBTABLE;
    private final String IMPLIED_ROLE_TABLENAME;

    private final DBTable OPERATOR_DBTABLE;
    private final String OPERATOR_TABLENAME;

    private final DBTable OPERATOR_ROLE_DBTABLE;
    private final String OPERATOR_ROLE_TABLENAME;

    private final DBTable SECURED_METHOD_DBTABLE;
    private final String SECURED_METHOD_TABLENAME;

    private final DBTable PASSWORD_DBTABLE;
    private final String PASSWORD_TABLENAME;

    // ENGINE SCHEMA
    private final DBTable PRODUCT_STRUCTURE_DBTABLE;
    private final String PRODUCT_STRUCTURE_TABLENAME;


    /**
     * DAO class for FilteredRole table.
     * <p>
     * Note - we frequently join on FilteredRoles and product structures here
     * to handle cases in development where customer data may have been swapped
     * in without security database.  To handle this mismatch,
     * the security tables can be all deleted (in which case security will be
     * initialized during startup), or if not, during startup, it will detect that
     * FilteredRoles are out of sync with ProductStructures and make sure that
     * the admin role can access all of the product product structures.  But in
     * this case, the security tables that were there are left alone.  This can
     * cause problems when looking at filtered Role data unless we join on product
     * structure.  This ensures that the out-of-sync FilteredRoles are ignored.
     */
    public FilteredRoleDAO()
    {
        POOLNAME = ConnectionFactory.SECURITY_POOL;
        DBTABLE = DBTable.getDBTableForTable("FilteredRole");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();

        ROLE_DBTABLE = DBTable.getDBTableForTable("Role");
        ROLE_TABLENAME = ROLE_DBTABLE.getFullyQualifiedTableName();

        COMPONENT_METHOD_TABLE = DBTable.getDBTableForTable("ComponentMethod");
        COMPONENT_METHOD_TABLENAME = COMPONENT_METHOD_TABLE.getFullyQualifiedTableName();

        IMPLIED_ROLE_DBTABLE = DBTable.getDBTableForTable("ImpliedRole");
        IMPLIED_ROLE_TABLENAME = IMPLIED_ROLE_DBTABLE.getFullyQualifiedTableName();

        OPERATOR_DBTABLE =  DBTable.getDBTableForTable("Operator");
        OPERATOR_TABLENAME = OPERATOR_DBTABLE.getFullyQualifiedTableName();

        OPERATOR_ROLE_DBTABLE =  DBTable.getDBTableForTable("OperatorRole");
        OPERATOR_ROLE_TABLENAME = OPERATOR_ROLE_DBTABLE.getFullyQualifiedTableName();

        SECURED_METHOD_DBTABLE =  DBTable.getDBTableForTable("SecuredMethod");
        SECURED_METHOD_TABLENAME = SECURED_METHOD_DBTABLE.getFullyQualifiedTableName();

        PASSWORD_DBTABLE   = DBTable.getDBTableForTable("Password");
        PASSWORD_TABLENAME = PASSWORD_DBTABLE.getFullyQualifiedTableName();


        // FROM ENGINE SCHEMA
        PRODUCT_STRUCTURE_DBTABLE = DBTable.getDBTableForTable("ProductStructure");
        PRODUCT_STRUCTURE_TABLENAME = PRODUCT_STRUCTURE_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * find all FilteredRole.
     * @return
     */
    public FilteredRoleVO[] findAll()
    {



        String fRoleCompStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String productStructurePKCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME;

        return (FilteredRoleVO[]) executeQuery(FilteredRoleVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Find all FilteredRole.
     * <p>
     * We are joining on those attached
     * to product structures.  See class level note.
     * @return
     */
    public FilteredRoleVO[] findAllWithProductStructures()
    {
        String fRoleCompStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String productStructurePKCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " +
                TABLENAME + ", " +
                PRODUCT_STRUCTURE_TABLENAME +
                " WHERE " + fRoleCompStructureFKCol + " = " + productStructurePKCol;

        return (FilteredRoleVO[]) executeQuery(FilteredRoleVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public FilteredRoleVO[] findByPK(long operatorPK, boolean includeChildVOs, List voExclusionList)
    {
        String filteredRolePKCol = DBTABLE.getDBColumn("FilteredRolePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredRolePKCol + " = " + operatorPK;

        return (FilteredRoleVO[]) executeQuery(FilteredRoleVO.class,
                sql,
                POOLNAME,
                includeChildVOs,
                voExclusionList);
    }

    /**
     * Get all of the FilteredRoleVOs that correspond to this Role FK.
     * <p>
     * Note - join on ProductStructure as well.  See class level note.
     * @param roleFK
     * @return
     */
    public FilteredRoleVO[] findByRole(long roleFK)
    {

        String fRoleCompStructureFKCol =
                DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String productStructurePKCol =
                PRODUCT_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String filteredRoleRoleFKCol =
                DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " +
                TABLENAME + ", " +
                PRODUCT_STRUCTURE_TABLENAME +
                " WHERE " + filteredRoleRoleFKCol + " = " + roleFK +
                " AND " + fRoleCompStructureFKCol + " = " + productStructurePKCol;

        FilteredRoleVO[] filteredRoleVOs =
                (FilteredRoleVO[]) executeQuery(FilteredRoleVO.class,
                        sql,
                        POOLNAME,
                        false,
                        null);

        return filteredRoleVOs;

    }


    public FilteredRoleVO[] findByProductStructure(long productStructureFK)
    {
         String filteredRoleProductStructureFKCol =
                DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredRoleProductStructureFKCol + " = " + productStructureFK;

        FilteredRoleVO[] filteredRoleVOs =
                (FilteredRoleVO[]) executeQuery(FilteredRoleVO.class,
                        sql,
                        POOLNAME,
                        false,
                        null);

        return filteredRoleVOs;

    }

    /**
     * Find the FilteredRole for a given RoleFK and ProductStructureFK.
     * @throws IllegalStateException if more than one row found matching the criteria
     * @return FilteredRoleVO row, or null if not found
     */
    public FilteredRoleVO[] findByRoleAndProductStructure(long roleFK, long productStructureFK)
    {
        String filteredRoleRoleFKCol =
                DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String filteredRoleProductStructureFKCol =
                DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " WHERE " + filteredRoleRoleFKCol + " = " + roleFK +
                " AND " + filteredRoleProductStructureFKCol + " = " + productStructureFK;

        FilteredRoleVO[] filteredRoleVOs =
                (FilteredRoleVO[]) executeQuery(FilteredRoleVO.class,
                        sql,
                        POOLNAME,
                        false,
                        null);

        if (filteredRoleVOs != null && filteredRoleVOs.length > 1)
        {
            throw new IllegalStateException("FilteredRoleDAO was loading " +
                    "FilteredRole for rolefk=" +
                    roleFK + " and productstructurefk="
                    + productStructureFK +
                    " and more than one was found!");
        }

        return filteredRoleVOs;
    }


    /**
     * Find FilteredRoles for a component method.  Note - we also join on
     * Product structure.  See class level note.
     * @param componentMethodPK
     * @return
     */
    public FilteredRoleVO[] findByComponentMethodPK(long componentMethodPK)
    {
        // SECUREDMETHOD
        String secMethFilteredRoleFKCol = SECURED_METHOD_DBTABLE.getDBColumn("FilteredRoleFK").getFullyQualifiedColumnName();
        String componentMethodFKCol = SECURED_METHOD_DBTABLE.getDBColumn("ComponentMethodFK").getFullyQualifiedColumnName();

        // FILTEREDROLE
        String filteredRolePKCol = DBTABLE.getDBColumn("FilteredRolePK").getFullyQualifiedColumnName();
        String fRoleCompStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String productStructurePKCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + TABLENAME + ".* FROM " +
                TABLENAME + ", " +
                SECURED_METHOD_TABLENAME + ", " +
                PRODUCT_STRUCTURE_TABLENAME +
                " WHERE " +
                secMethFilteredRoleFKCol + " = " + filteredRolePKCol +
                " AND " + componentMethodFKCol + " = " + componentMethodPK +
                " AND " + fRoleCompStructureFKCol + " = " + productStructurePKCol;

        return (FilteredRoleVO[]) executeQuery(FilteredRoleVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    //    /**
    //     * This method will remove all rows from the security database.
    //     * It is meant to be used only when a customer's data is swapped
    //     * in with none of their security data.
    //     * <p>
    //     * If the security database is empty, this will not be done.
    //     * <p>
    //     * This will happen during startup, if there are product structures
    //     * that are product-type (security type), and there are FilteredRoles
    //     * and none of the FilteredRoles point to an existing product structure.
    //     */
    //    public void wipeOutSecurity()
    //    {
    //        wipeOutSecurityTable(IMPLIED_ROLE_TABLENAME);
    //        wipeOutSecurityTable(SECURED_METHOD_TABLENAME);
    //        wipeOutSecurityTable(TABLENAME);  // filteredrole
    //        wipeOutSecurityTable(OPERATOR_ROLE_TABLENAME);
    //        wipeOutSecurityTable(ROLE_TABLENAME);
    //        wipeOutSecurityTable(PASSWORD_TABLENAME);
    //        wipeOutSecurityTable(OPERATOR_TABLENAME);
    //        wipeOutSecurityTable(COMPONENT_METHOD_TABLENAME);
    //    }


    //    public void wipeOutSecurityTable(String tableName)
    //    {
    //
    //        Connection crudConn = ConnectionFactory.getSingleton().getConnection(POOLNAME);
    //
    //        String sql = " DELETE FROM " + tableName;
    //
    //        PreparedStatement ps = null;
    //
    //        try
    //        {
    //            ps = crudConn.prepareStatement(sql.toString());
    //
    //            ps.executeUpdate();
    //        }
    //        catch (Exception e)
    //        {
    //            System.out.println(e);
    //
    //            e.printStackTrace();
    //
    //            throw new RuntimeException(e);
    //        }
    //        finally
    //        {
    //            try
    //            {
    //                if (ps != null)
    //                    ps.close();
    //
    //                if (crudConn != null)
    //                    crudConn.close();
    //            }
    //            catch (SQLException e)
    //            {
    //                System.out.println(e);
    //
    //                e.printStackTrace();
    //
    //                throw new RuntimeException(e);
    //            }
    //        }
    //
    //    }

}
