/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 1, 2003
 * Time: 7:53:13 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security.dm.dao;

import edit.common.vo.RoleVO;
import edit.common.vo.FilteredRoleVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;

import java.util.List;

        //DBTable.getDBColumn: cannot find column named 'ImpliedRoleFK' in table ComponentMethod
public class RoleDAO extends DAO
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

    private final DBTable IMPLIED_ROLE_DBTABLE;
    private final String IMPLIED_ROLE_TABLENAME;

    private final DBTable OPERATOR_DBTABLE;
    private final String OPERATOR_TABLENAME;

    private final DBTable OPERATOR_ROLE_DBTABLE;
    private final String OPERATOR_ROLE_TABLENAME;

    private final DBTable SECURED_METHOD_DBTABLE;
    private final String SECURED_METHOD_TABLENAME;

    private final DBTable PRODUCT_STRUCTURE_DBTABLE;
    private final String PRODUCT_STRUCTURE_TABLENAME;


    public RoleDAO()
    {
        POOLNAME = ConnectionFactory.SECURITY_POOL;
        DBTABLE = DBTable.getDBTableForTable("Role");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();

        ROLE_DBTABLE = DBTable.getDBTableForTable("Role");
        ROLE_TABLENAME = ROLE_DBTABLE.getFullyQualifiedTableName();

        FILTERED_ROLE_DBTABLE = DBTable.getDBTableForTable("FilteredRole");
        FILTERED_ROLE_TABLENAME  = FILTERED_ROLE_DBTABLE.getFullyQualifiedTableName();

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

        // FROM ENGINE SCHEMA
        PRODUCT_STRUCTURE_DBTABLE = DBTable.getDBTableForTable("ProductStructure");
        PRODUCT_STRUCTURE_TABLENAME = PRODUCT_STRUCTURE_DBTABLE.getFullyQualifiedTableName();

    }

	public RoleVO[] findByPK(long rolePK)
    {
        String rolePKCol = DBTABLE.getDBColumn("RolePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rolePKCol + " = " + rolePK;

        return (RoleVO[]) executeQuery(RoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

	public RoleVO[] findImpliedRoleByRolePK(long rolePK)
    {
        String rolePKCol        = DBTABLE.getDBColumn("RolePK").getFullyQualifiedColumnName();

        String impliedRoleFKCol = IMPLIED_ROLE_DBTABLE.getDBColumn("ImpliedRoleFK").getFullyQualifiedColumnName();
        String roleFKCol        = IMPLIED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + rolePKCol + " IN" +
                     " (SELECT " + impliedRoleFKCol + " FROM " + IMPLIED_ROLE_TABLENAME +
                     " WHERE (" + roleFKCol + " = " + rolePK + "))";
        
        return (RoleVO[]) executeQuery(RoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

	public RoleVO[] findAll()
    {
        String sql = ("SELECT * FROM " + TABLENAME);

        return (RoleVO[]) executeQuery(RoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
	}

    public RoleVO[] findByOperatorPK(long operatorPK, boolean includeChildVOs, List voExclusionList)
    {

        String operatorPKCol = OPERATOR_DBTABLE.getDBColumn("OperatorPK").getFullyQualifiedColumnName();

        String operatorRoleTable = OPERATOR_ROLE_DBTABLE.getFullyQualifiedTableName();

        String operatorFKCol = OPERATOR_ROLE_DBTABLE.getDBColumn("OperatorFK").getFullyQualifiedColumnName();
        String roleFKColOfOperatorRoleTable     = OPERATOR_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String rolePKCol     = DBTABLE.getDBColumn("RolePK").getFullyQualifiedColumnName();
        
        String filteredRoleTable = FILTERED_ROLE_DBTABLE.getFullyQualifiedTableName();
        
        String roleFKColOfFilteredRoleTable = FILTERED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".*" + 
                     " FROM " + TABLENAME + 
                     " JOIN " + operatorRoleTable + 
                     " ON " + rolePKCol + " = " + roleFKColOfOperatorRoleTable + 
                     " JOIN " + OPERATOR_TABLENAME + 
                     " ON " + operatorPKCol + " = " + operatorFKCol + 
                     " JOIN " + filteredRoleTable + 
                     " ON " + rolePKCol + " = " + roleFKColOfFilteredRoleTable + 
                     " WHERE " + operatorPKCol + " = " + operatorPK;

        return (RoleVO[]) executeQuery(RoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  includeChildVOs,
                                                   voExclusionList);
    }

    public RoleVO[] findByRoleName(String roleName)
    {
        String nameCol = DBTABLE.getDBColumn("Name").getFullyQualifiedColumnName();

        String sql = (" SELECT * FROM " + TABLENAME +
                     " WHERE " + nameCol + " = '" + roleName + "'");

        return (RoleVO[]) executeQuery(RoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    public RoleVO[] findByImpliedRolePK(long impliedRolePK)
    {
        String rolePKCol        = DBTABLE.getDBColumn("RolePK").getFullyQualifiedColumnName();

        String roleFKCol        = IMPLIED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();
        String impliedRoleFKCol = IMPLIED_ROLE_DBTABLE.getDBColumn("ImpliedRoleFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE (" + rolePKCol + " IN" +
                     " (SELECT " + roleFKCol + " FROM " + IMPLIED_ROLE_TABLENAME +
                     " WHERE (" + impliedRoleFKCol + " = " + impliedRolePK + ")))";

        return (RoleVO[]) executeQuery(RoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }

    /**
     * Find by component method.
     * <p/>
     * Note - we also join on Product Structure in case security database has
     * been swapped in.  See class note in FilteredRoleDAO.
     * @param componentMethodPK
     * @return
     */
    public RoleVO[] findByComponentMethodPK(long componentMethodPK)
    {
        // ROLE
        String rolePKCol            = DBTABLE.getDBColumn("RolePK").getFullyQualifiedColumnName();

        // SECUREDMETHOD
        String secMethFilteredRoleFKCol = SECURED_METHOD_DBTABLE.getDBColumn("FilteredRoleFK").getFullyQualifiedColumnName();
        String componentMethodFKCol = SECURED_METHOD_DBTABLE.getDBColumn("ComponentMethodFK").getFullyQualifiedColumnName();

        // FILTEREDROLE
        String filteredRolePKCol = FILTERED_ROLE_DBTABLE.getDBColumn("FilteredRolePK").getFullyQualifiedColumnName();
        String filteredRoleRoleFKCol = FILTERED_ROLE_DBTABLE.getDBColumn("RoleFK").getFullyQualifiedColumnName();
        String fRoleProdStructureFKCol = FILTERED_ROLE_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String productStructurePKCol = PRODUCT_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();


        String sql = " SELECT DISTINCT " + TABLENAME + ".* FROM " +
                            TABLENAME + ", " +
                            SECURED_METHOD_TABLENAME + ", " +
                            FILTERED_ROLE_TABLENAME + ", " +
                            PRODUCT_STRUCTURE_TABLENAME +
                     " WHERE " +
                            secMethFilteredRoleFKCol + " = " + filteredRolePKCol +
                            " AND " + filteredRoleRoleFKCol + " = " + rolePKCol +
                            " AND " + componentMethodFKCol + " = " + componentMethodPK +
                            " AND " + fRoleProdStructureFKCol + " = " + productStructurePKCol;

        return (RoleVO[]) executeQuery(RoleVO.class,
                                                sql,
                                                 POOLNAME,
                                                  false,
                                                   null);
    }
}