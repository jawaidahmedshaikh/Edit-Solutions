/*
 * User: gfrosti
 * Date: Feb 4, 2004
 * Time: 8:59:04 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;



public class ImpliedRole extends HibernateEntity
{
    public static final String DATABASE = SessionHelper.SECURITY;

    private Long impliedRolePK;
    
    // Parent
    // roleFK = This role is inherited from corresponding ImpliedRole.ImpliedRoleFK role.
    // Intentionally did not map as entity - SP
    private Long roleFK; // childRoleFK = RoleFK column in ImpliedRole table.
    
    // ImpliedRoleFK = This role is the parent of corresponding ImpliedRole.RoleFK role.
    private Role parentRole; // parentRole = ImpliedRoleFK column in ImpliedRole table.
    
    private Long impliedRoleFK;

    public ImpliedRole()
    {
    }
    
    /**
     * Setter.
     * @param impliedRolePK
     */
    public void setImpliedRolePK(Long impliedRolePK)
    {
        this.impliedRolePK = impliedRolePK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getImpliedRolePK()
    {
        return impliedRolePK;
    }

    /**
     * Setter.
     * @param roleFK
     */
    public void setRoleFK(Long roleFK)
    {
        this.roleFK = roleFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getRoleFK()
    {
        return roleFK;
    }
    
    /**
     * Setter.
     * @param impliedRoleFK
     */
    public void setImpliedRoleFK(Long impliedRoleFK)
    {
        this.impliedRoleFK = impliedRoleFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getImpliedRoleFK()
    {
        return impliedRoleFK;
    }

    /**
     * Getter.
     * @param parentRole
     */
    public void setParentRole(Role parentRole)
    {
        this.parentRole = parentRole;
    }

    /**
     * Getter.
     * @return
     */
    public Role getParentRole()
    {
        return parentRole;
    }
    
    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ImpliedRole.DATABASE);
    }
    
    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ImpliedRole.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ImpliedRole.DATABASE;
    }

    public static ImpliedRole findByRolePKAndImpliedRolePK(Long rolePK, Long impliedRolePK)
    {
//        return ImpliedRoleImpl.findByRolePKAndImpliedRolePK(rolePK, impliedRolePK);

        ImpliedRole impliedRole = null;
        
        String hql = " select impliedRole " +
                     " from ImpliedRole impliedRole " +
                     " where impliedRole.RoleFK = :roleFK " +
                     " and impliedRole.ImpliedRoleFK = :impliedRoleFK";
                     
        List results = new ArrayList();
        
        Map params = new HashMap();
        
        params.put("roleFK", rolePK);
        params.put("impliedRoleFK", impliedRolePK);
        
        results = SessionHelper.executeHQL(hql, params, ImpliedRole.DATABASE);
        
        if (!results.isEmpty())
        {
            impliedRole = (ImpliedRole) results.get(0);
        }
        
        return impliedRole;
    }

    public static ImpliedRole[] findByImpliedRoleFK(Long impliedRoleFK)
    {
//        return ImpliedRoleImpl.findByImpliedRoleFK(impliedRoleFK);

        String hql = " select impliedRole " +
                     " from ImpliedRole impliedRole " +
                     " where impliedRole.ImpliedRoleFK = :impliedRoleFK";

        Map params = new HashMap();
        params.put("impliedRoleFK", impliedRoleFK);

        List results = SessionHelper.executeHQL(hql, params, ImpliedRole.DATABASE);
        
        return (ImpliedRole[]) results.toArray(new ImpliedRole[results.size()]);
    }

    public static ImpliedRole[] findByRoleFK(Long roleFK)
    {
//        return mapVOToEntity(DAOFactory.getImpliedRoleDAO().findByRoleFK(roleFK));

        String hql = " select impliedRole " +
                     " from ImpliedRole impliedRole " +
                     " where impliedRole.RoleFK = :roleFK";

        Map params = new HashMap();
        params.put("roleFK", roleFK);
        
        List results = SessionHelper.executeHQL(hql, params, ImpliedRole.DATABASE);
        
        return (ImpliedRole[]) results.toArray(new ImpliedRole[results.size()]);
    }
}
