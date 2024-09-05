/*
 * User: gfrosti
 * Date: Feb 6, 2004
 * Time: 1:57:58 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.services.db.hibernate.*;

import java.util.*;


public class OperatorRole extends HibernateEntity
{
    private Long operatorRolePK;

    // Parents
    private Long operatorFK;
    private Long roleFK;
    private Operator operator;
    private Role role;

    public static final String DATABASE = SessionHelper.SECURITY;


    public OperatorRole()
    {
    }

//    public void associate(Operator operator, Role role) throws EDITSecurityException
//    {
//        this.operatorRoleVO.setOperatorFK(operator.getPK());
//
//        this.operatorRoleVO.setRoleFK(role.getPK());
//
//        this.save();
//    }

    public Long getOperatorRolePK()
    {
        return this.operatorRolePK;
    }

    public Long getOperatorFK()
    {
        return this.operatorFK;
    }

    public Long getRoleFK()
    {
        return this.roleFK;
    }

    public Operator getOperator()
    {
        return this.operator;
    }

    public Role getRole()
    {
        return this.role;
    }

    public void setOperatorRolePK(Long operatorRolePK)
    {
        this.operatorRolePK = operatorRolePK;
    }

    public void setOperatorFK(Long operatorFK)
    {
        this.operatorFK = operatorFK;
    }

    public void setRoleFK(Long roleFK)
    {
        this.roleFK = roleFK;
    }

    public void setOperator(Operator operator)
    {
        this.operator = operator;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, OperatorRole.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, OperatorRole.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return OperatorRole.DATABASE;
    }

    public static OperatorRole[] findByRolePK(Long rolePK)
    {
        OperatorRole[] operatorRoles = null;

        String hql = "select operatorRole from OperatorRole operatorRole " +
                     " where operatorRole.RoleFK = :rolePK";

        Map params = new HashMap();
        params.put("rolePK", rolePK);

        List results = SessionHelper.executeHQL(hql, params, OperatorRole.DATABASE);

        if (! results.isEmpty())
        {
            operatorRoles = (OperatorRole[]) results.toArray(new OperatorRole[results.size()]);
        }

        return operatorRoles;
    }

    public static OperatorRole[] findByOperatorPK(Long operatorPK)
    {
        OperatorRole[] operatorRoles = null;

        String hql = "select operatorRole from OperatorRole operatorRole " +
                     " where operatorRole.OperatorFK = :operatorPK";

        Map params = new HashMap();
        params.put("operatorPK", operatorPK);

        List results = SessionHelper.executeHQL(hql, params, OperatorRole.DATABASE);

        if (! results.isEmpty())
        {
            operatorRoles = (OperatorRole[]) results.toArray(new OperatorRole[results.size()]);
        }

        return operatorRoles;
    }

    public static OperatorRole findByOperatorPKAndRolePK(Long operatorPK, Long rolePK)
    {
        OperatorRole operatorRole = null;

        String hql = "select operatorRole from OperatorRole operatorRole " +
                     " where operatorRole.OperatorFK = :operatorPK" +
                     " and operatorRole.RoleFK = :rolePK";

        Map params = new HashMap();
        params.put("operatorPK", operatorPK);
        params.put("rolePK", rolePK);

        List results = SessionHelper.executeHQL(hql, params, OperatorRole.DATABASE);

        if (! results.isEmpty())
        {
            operatorRole = (OperatorRole) results.get(0);
        }

        return operatorRole;
    }
}
