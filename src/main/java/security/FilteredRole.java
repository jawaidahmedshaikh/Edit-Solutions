/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.services.db.hibernate.*;
import edit.common.exceptions.EDITSecurityException;
import engine.ProductStructure;

import java.util.*;

public class FilteredRole extends HibernateEntity
{
    private Long filteredRolePK;

    //  Parents
    private Long roleFK;
    private Long productStructureFK;
    private Role role;

    //  Children
    private Set<SecuredMethod> securedMethods;

    private static Map<Long, Role[]> impliedRolesByFilteredRole;

    public static final String DATABASE = SessionHelper.SECURITY;

    private ProductStructure productStructure;

    public FilteredRole()
    {
        init();
    }

    /**
     * Guarantees that the objects are properly instantiated.
     */
    private final void init()
    {
        if (securedMethods == null)
        {
            securedMethods = new HashSet<SecuredMethod>();
        }

        if (impliedRolesByFilteredRole == null)
        {
            impliedRolesByFilteredRole = new HashMap<Long, Role[]>();
        }
    }

    public Long getFilteredRolePK()
    {
        return this.filteredRolePK;
    }

    public Long getRoleFK()
    {
        return this.roleFK;
    }

    public Long getProductStructureFK()
    {
        return this.productStructureFK;
    }

     public Role getRole()
    {
        return this.role;
    }

    public ProductStructure getProductStructure()
    {
        return this.productStructure;
    }

    public void setFilteredRolePK(Long filteredRolePK)
    {
        this.filteredRolePK = filteredRolePK;
    }

    public void setRoleFK(Long roleFK)
    {
        this.roleFK = roleFK;
    }

    public void setProductStructureFK(Long productStructureFK)
    {
        this.productStructureFK = productStructureFK;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public void setProductStructure(ProductStructure productStructure)
    {
        this.productStructure = productStructure;
    }



    /**
     * Getter
     *
     * @return set of securedMethods
     */
    public Set<SecuredMethod> getSecuredMethods()
    {
        return securedMethods;
    }

    /**
     * Setter
     *
     * @param securedMethods set of securedMethods
     */
    public void setSecuredMethods(Set<SecuredMethod> securedMethods)
    {
        this.securedMethods = securedMethods;
    }

    /**
     * Adds a SecuredMethod to the set of children
     *
     * @param securedMethod
     */
    public void addSecuredMethod(SecuredMethod securedMethod)
    {
        this.getSecuredMethods().add(securedMethod);

        securedMethod.setFilteredRole(this);

        SessionHelper.saveOrUpdate(securedMethod, FilteredRole.DATABASE);
    }

    /**
     * Removes a SecuredMethod from the set of children
     *
     * @param securedMethod
     */
    public void removeSecuredMethod(SecuredMethod securedMethod)
    {
        this.getSecuredMethods().remove(securedMethod);

        securedMethod.setFilteredRole(null);

        SessionHelper.saveOrUpdate(securedMethod, FilteredRole.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return FilteredRole.DATABASE;
    }

//    public FilteredRole cloneFilteredRole(ProductStructure toProductStructure)
//    {
//        FilteredRole cloneToFilteredRole = (FilteredRole) SessionHelper.newInstance(FilteredRole.class, SessionHelper.SECURITY);
//        cloneToFilteredRole.setProductStructure(toProductStructure);
//        cloneToFilteredRole.setProductStructureFK(new Long(toProductStructure.getPK()));
//        cloneToFilteredRole.setRole(this.getRole());
//        cloneToFilteredRole.setRoleFK(this.getRoleFK());
//
//        SecuredMethod[] securedMethodsToCopy = SecuredMethod.getAllForFilteredRole(this);
//        if (securedMethodsToCopy != null)
//        {
//            for (int i = 0; i < securedMethodsToCopy.length; i++)
//            {
//                SecuredMethod securedMethod = SecuredMethod.cloneAllSecuredMethods(securedMethodsToCopy[i], cloneToFilteredRole);
//                cloneToFilteredRole.addSecuredMethod(securedMethod);
//            }
//        }
//
//        return cloneToFilteredRole;
//    }

    /**
     * Clones the filteredRole to point to the new "toProductStructure" and the same Role
     * @param toProductStructure
     * @return
     * @throws EDITSecurityException
     */
    public FilteredRole cloneFilteredRole(ProductStructure toProductStructure) throws EDITSecurityException
    {
        FilteredRole cloneToFilteredRole = (FilteredRole) SessionHelper.newInstance(FilteredRole.class, SessionHelper.SECURITY);
        cloneToFilteredRole.setProductStructureFK(toProductStructure.getProductStructurePK());
        cloneToFilteredRole.setRole(this.getRole());

        for (SecuredMethod securedMethod : this.getSecuredMethods())
        {
            SecuredMethod cloneToSecuredMethod = (SecuredMethod) SessionHelper.newInstance(SecuredMethod.class, SessionHelper.SECURITY);
            
            cloneToSecuredMethod.setComponentMethod(securedMethod.getComponentMethod());
            
            cloneToFilteredRole.addSecuredMethod(cloneToSecuredMethod);
        }
        
        cloneToFilteredRole.hSave();

        return cloneToFilteredRole;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        if (filteredRoleExists())           // Why are we checking if it exists?
        {
            try
            {
                throw new EDITSecurityException("Error - Duplicate FilteredRole Name");
            }
            catch (EDITSecurityException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        else
        {
            SessionHelper.beginTransaction(FilteredRole.DATABASE);

            SessionHelper.saveOrUpdate(this, FilteredRole.DATABASE);

            SessionHelper.commitTransaction(FilteredRole.DATABASE);
        }
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        //     delete the secured methods first
//        try
//        {
//            SecuredMethod.deleteAllForFilteredRole(this.getFilteredRolePK());       // is the necessary with hibernate?
//        }
//        catch (EDITSecurityException e)
//        {
//            System.out.println(e);
//            e.printStackTrace();
//        }

        // deleting the filtered role will delete secured methods attached to filtered role 
        // because the mapping is defined as cascade = 'all'
        SessionHelper.delete(this, FilteredRole.DATABASE);
    }

    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof FilteredRole))
            return false;

        final FilteredRole filteredRole = (FilteredRole) o;

        if (this.getFilteredRolePK().equals(filteredRole.getFilteredRolePK()))
            return false;

        return true;
    }

    public void secureComponentMethod(ComponentMethod componentMethod, boolean isAuthorized) throws EDITSecurityException
    {
        if (componentMethod.isEditable(this))
        {
            if (isAuthorized)
            {
                if (!componentMethod.isAuthorized(this))
                {
                    SecuredMethod securedMethod = new SecuredMethod();

                    securedMethod.associate(this, componentMethod);
                }
            }
            else if (!isAuthorized)
            {
                if (componentMethod.isAuthorized(this))
                {
                    SecuredMethod securedMethod = SecuredMethod.findByFilteredRoleComponentMethod(this, componentMethod);

                    securedMethod.hDelete();
                }
            }
        }
        else
        {
            throw new EDITSecurityException("Error - Role/CompanyStructure does not have permission To modify Method");
        }
    }

    public Role[] getImpliedRoles()
    {
        return impliedRolesByFilteredRole.get(this.getFilteredRolePK());
    }

    public static void loadImpliedRolesByFilteredRole()
    {
        impliedRolesByFilteredRole.clear();

        FilteredRole[] filteredRoles = findAll();

        for (FilteredRole filteredRole : filteredRoles)
        {
            Role role = filteredRole.getRole();

            Role[] impliedRoles = role.getImpliedRoles();

            impliedRolesByFilteredRole.put(filteredRole.getFilteredRolePK(), impliedRoles);
        }
    }

    public static FilteredRole[] findAll()
    {
        String hql = "select filteredRole from FilteredRole filteredRole ";

        Map params = new HashMap();

        List results = SessionHelper.executeHQL(hql, params, FilteredRole.DATABASE);

        return (FilteredRole[]) results.toArray(new FilteredRole[results.size()]);
    }

    public static FilteredRole[] findAllWithProductStructures()
    {
        String hql = "select filteredRole from FilteredRole filteredRole " +
                " join filteredRole.ProductStructure productStructure ";

        Map params = new HashMap();

        List results = SessionHelper.executeHQL(hql, params, FilteredRole.DATABASE);

        return (FilteredRole[]) results.toArray(new FilteredRole[results.size()]);
    }

    /**
     * Find FilteredRoles for a component method.  Note - we also join on
     * Product structure.  See class level note.
     *
     * @param componentMethodPK
     *
     * @return
     */
    public static FilteredRole[] findByComponentMethodPK(Long componentMethodPK)
    {
        String hql = "select filteredRole from FilteredRole filteredRole " +
                " join filteredRole.SecuredMethods securedMethod " +
                " where securedMethod.ComponentMethodFK = :componentMethodPK ";

        Map params = new HashMap();
        params.put("componentMethodPK", componentMethodPK);

        List results = SessionHelper.executeHQL(hql, params, FilteredRole.DATABASE);

        return (FilteredRole[]) results.toArray(new FilteredRole[results.size()]);
    }

    /**
     * Finder by Role and Product Structure keys
     * @param rolePK
     * @param productStructurePK
     * @return
     */
    public static FilteredRole findByRoleAndProductStructure(Long rolePK, Long productStructurePK)
    {
        Role role = Role.findByPK(rolePK);

        FilteredRole filteredRole = null;

        String hql = "select filteredRole from FilteredRole filteredRole " +
                " left join filteredRole.SecuredMethods securedMethods " +
                " where filteredRole.Role = :role " +
                " and filteredRole.ProductStructureFK = :productStructurePK";

        Map params = new HashMap();
        params.put("role", role);
        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, FilteredRole.DATABASE);

        if (!results.isEmpty())
        {
            filteredRole = (FilteredRole) results.get(0);
        }

        return filteredRole;
    }

    public static FilteredRole[] findByRole(Long roleFK)
    {
        String hql = "select filteredRole from FilteredRole filteredRole " +
                " where filteredRole.RoleFK = :roleFK";

        Map params = new HashMap();
        params.put("roleFK", roleFK);

        List results = SessionHelper.executeHQL(hql, params, FilteredRole.DATABASE);

        return (FilteredRole[]) results.toArray(new FilteredRole[results.size()]);
    }

    public static FilteredRole[] findByProductStructure(Long productStructureFK)
    {
        String hql = "select filteredRole from FilteredRole filteredRole " +
                " where filteredRole.ProductStructureFK = :productStructureFK";

        Map params = new HashMap();
        params.put("productStructureFK", productStructureFK);

        List results = SessionHelper.executeHQL(hql, params, FilteredRole.DATABASE);

        return (FilteredRole[]) results.toArray(new FilteredRole[results.size()]);
    }


    private boolean filteredRoleExists()
    {
        boolean filteredRoleExists = false;

        Long roleFK = this.getRole().getRolePK();
        Long productStructureFK = this.getProductStructureFK();     // Get just the fk, not the object because ProductStructure is in a diff db

        FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(roleFK, productStructureFK);

        if (filteredRole != null)
        {
            filteredRoleExists = true;
        }

        return filteredRoleExists;
    }
}
