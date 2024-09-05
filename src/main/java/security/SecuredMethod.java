/*
 * User: gfrosti
 * Date: Jan 21, 2004
 * Time: 1:44:40 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.common.exceptions.EDITSecurityException;
import edit.services.db.hibernate.*;

import java.util.*;


public class SecuredMethod extends HibernateEntity
{
    private Long securedMethodPK;

    //  Parents
    private Long roleFK;
    private Long filteredRoleFK;
    private Long componentMethodFK;
    private FilteredRole filteredRole;
    private ComponentMethod componentMethod;

    public static final String DATABASE = SessionHelper.SECURITY;


    public SecuredMethod()
    {
    }

    public Long getSecuredMethodPK()
    {
        return this.securedMethodPK;
    }

    public Long getRoleFK()
    {
        return this.roleFK;
    }

    public Long getFilteredRoleFK()
    {
        return this.filteredRoleFK;
    }

    public Long getComponentMethodFK()
    {
        return this.componentMethodFK;
    }

    public FilteredRole getFilteredRole()
    {
        return this.filteredRole;
    }

    public ComponentMethod getComponentMethod()
    {
        return this.componentMethod;
    }

    public void setSecuredMethodPK(Long securedMethodPK)
    {
        this.securedMethodPK = securedMethodPK;
    }

    public void setRoleFK(Long roleFK)
    {
        this.roleFK = roleFK;
    }

    public void setFilteredRoleFK(Long filteredRoleFK)
    {
        this.filteredRoleFK = filteredRoleFK;
    }

    public void setComponentMethodFK(Long componentMethodFK)
    {
        this.componentMethodFK = componentMethodFK;
    }

    public void setFilteredRole(FilteredRole filteredRole)
    {
        this.filteredRole = filteredRole;
    }

    public void setComponentMethod(ComponentMethod componentMethod)
    {
        this.componentMethod = componentMethod;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, SecuredMethod.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, SecuredMethod.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return SecuredMethod.DATABASE;
    }


    public void associate(FilteredRole filteredRole, ComponentMethod componentMethod) throws EDITSecurityException
    {
        this.setFilteredRole(filteredRole);

        this.setComponentMethod(componentMethod);

        hSave();
    }

    /**
     * Do this directly instead of instantiating lots of SecuredMethod
     * objects and then deleting each separately.  Reduces DB roundtrips
     * to one.
     * @param filteredRoleFK
     */
    public static void deleteAllForFilteredRole(Long filteredRoleFK) throws EDITSecurityException
    {
//        SecuredMethodImpl.deleteAllForFilteredRole(filteredRolePK);

         String hql = " delete from SecuredMethod securedMethod " +
                      " where securedMethod.FilteredRoleFK = :filteredRoleFK";
                      
         Map params = new HashMap();
         params.put("filteredRoleFK", filteredRoleFK);
         
         SessionHelper.executeHQL(hql, params, SecuredMethod.DATABASE);
    }

    /**
     * Take all of the secured methods under filteredRoleCloneFrom and
     * insert copies of them under filteredRoleCloneTo.
     *
     * @param filteredRoleCloneFrom
     * @param filteredRoleCloneTo
     *
     * @throws EDITSecurityException
     */
    public static void cloneAllSecuredMethods(FilteredRole filteredRoleCloneFrom, FilteredRole filteredRoleCloneTo)
            throws EDITSecurityException
    {
//        {
//             SecuredMethod[] securedMethodsToCopy = SecuredMethod.getAllForFilteredRole(filteredRoleCloneFrom);
//
//             if (securedMethodsToCopy == null)
//             {
//                 return;
//             }
//
//             for (int i = 0; i < securedMethodsToCopy.length; i++)
//             {
//                 SecuredMethod securedMethod = securedMethodsToCopy[i];
//
//                 securedMethod.setFilteredRoleFK(filteredRoleCloneTo.getFilteredRolePK());
//
//                 // force an insert rather than an update
//                 securedMethod.setSecuredMethodPK(0);
//                 securedMethod.hSave();
//             }
//        }
    
         SecuredMethod[] securedMethodsToCopy = SecuredMethod.getAllForFilteredRole(filteredRoleCloneFrom);

         if (securedMethodsToCopy == null)
         {
             return;
         }

         for (SecuredMethod securedMethodToCopy: securedMethodsToCopy)
         {
             // force an insert rather than an update
             SecuredMethod securedMethod = (SecuredMethod) SessionHelper.newInstance(SecuredMethod.class, SecuredMethod.DATABASE);

             securedMethod.setFilteredRole(filteredRoleCloneTo);
             securedMethod.setComponentMethod(securedMethodToCopy.getComponentMethod());                 

             securedMethod.hSave();
         }
    }

    public static SecuredMethod[] getAllForFilteredRole(FilteredRole filteredRole)
    {
        Long productStructureFK = filteredRole.getProductStructureFK();
        Long roleFK = filteredRole.getRoleFK();

        return findByRoleProductStructure(roleFK, productStructureFK);
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////// FINDERS ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public static SecuredMethod[] findByRoleProductStructure(Long roleFK, Long productStructureFK)
    {
        Role role = Role.findByPK(roleFK);

        SecuredMethod[] securedMethods = null;

        String hql = "select securedMethod from SecuredMethod securedMethod " +
                " join securedMethod.FilteredRole filteredRole " +
                " where filteredRole.Role = :role " +
                " and filteredRole.ProductStructureFK = :productStructureFK";

        Map params = new HashMap();
        params.put("role", role);
        params.put("productStructureFK", productStructureFK);

        List results = SessionHelper.executeHQL(hql, params, SecuredMethod.DATABASE);

        if (!results.isEmpty())
        {
            securedMethods = (SecuredMethod[]) results.toArray(new SecuredMethod[results.size()]);
        }

        return securedMethods;
    }

    public static SecuredMethod[] findByRolePK(Long rolePK)
    {
        SecuredMethod[] securedMethods = null;

        String hql = "select securedMethod from SecuredMethod securedMethod " +
                " join securedMethod.FilteredRole filteredRole " +
                " where filteredRole.RoleFK = :rolePK";

        Map params = new HashMap();
        params.put("rolePK", rolePK);

        List results = SessionHelper.executeHQL(hql, params, SecuredMethod.DATABASE);

        if (!results.isEmpty())
        {
            securedMethods = (SecuredMethod[]) results.toArray(new SecuredMethod[results.size()]);
        }

        return securedMethods;
    }

    public static SecuredMethod[] findByComponentNameCTRoleProductStructure(String componentNameCT, Role role, Long productStructurePK)
    {
        SecuredMethod[] securedMethods = null;

        String hql = "select securedMethod from SecuredMethod securedMethod " +
                " join securedMethod.FilteredRole filteredRole " +
                " join securedMethod.ComponentMethod componentMethod " +
                " where componentMethod.ComponentNameCT = :componentNameCT " +
                " and filteredRole.Role = :role " +
                " and filteredRole.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();
        params.put("componentNameCT", componentNameCT);
        params.put("role", role);
        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, SecuredMethod.DATABASE);

        if (!results.isEmpty())
        {
            securedMethods = (SecuredMethod[]) results.toArray(new SecuredMethod[results.size()]);
        }

        return securedMethods;
    }

    public static SecuredMethod findByRoleMethodProductStructure(Role role, ComponentMethod componentMethod, Long productStructurePK)
    {
        SecuredMethod securedMethod = null;

        String hql = "select securedMethod from SecuredMethod securedMethod " +
                " join securedMethod.FilteredRole filteredRole " +
                " where securedMethod.ComponentMethod = :componentMethod " +
                " and filteredRole.Role = :role " +
                " and filteredRole.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();
        params.put("componentMethod", componentMethod);
        params.put("role", role);
        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, SecuredMethod.DATABASE);

        if (!results.isEmpty())
        {
            securedMethod = (SecuredMethod) results.get(0);
        }

        return securedMethod;
    }

    /**
     * Finder by ComponentMethod, ProductStructure and RolePKs.
     * @param rolePKs
     * @param componentMethod
     * @param productStructurePK
     * @return
     */
    public static SecuredMethod findByRolesMethodProductStructure(List<Long> rolePKs, ComponentMethod componentMethod, Long productStructurePK)
    {
        SecuredMethod securedMethod = null;

        String hql = "select securedMethod from SecuredMethod securedMethod " +
                " join securedMethod.FilteredRole filteredRole " +
                " where securedMethod.ComponentMethod = :componentMethod " +
                " and filteredRole.RoleFK in (:rolePKs)" +
                " and filteredRole.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();
        params.put("componentMethod", componentMethod);
        params.put("rolePKs", rolePKs);
        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, SecuredMethod.DATABASE);

        if (!results.isEmpty())
        {
            securedMethod = (SecuredMethod) results.get(0);
        }

        return securedMethod;
    }

    public static SecuredMethod findByFilteredRoleComponentMethod(FilteredRole filteredRole, ComponentMethod componentMethod)
    {
        SecuredMethod securedMethod = null;

        String hql = "select securedMethod from SecuredMethod securedMethod " +
                " join securedMethod.FilteredRole filteredRole " +
                " where securedMethod.ComponentMethod = :componentMethod " +
                " and securedMethod.FilteredRole = :filteredRole " +
                " and filteredRole.ProductStructureFK = :productStructureFK";

        Map params = new HashMap();
        params.put("componentMethod", componentMethod);
        params.put("filteredRole", filteredRole);
        params.put("productStructureFK", filteredRole.getProductStructureFK());

        List results = SessionHelper.executeHQL(hql, params, SecuredMethod.DATABASE);

        if (!results.isEmpty())
        {
            securedMethod = (SecuredMethod) results.get(0);
        }

        return securedMethod;
    }
}
