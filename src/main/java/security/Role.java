 /*
 * User: gfrosti
 * Date: Jan 9, 2004
 * Time: 3:03:56 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.common.exceptions.EDITSecurityException;
import edit.common.vo.BIZRoleVO;
import edit.common.vo.RoleVO;

import edit.services.db.hibernate.*;
import engine.*;

import java.util.*;


public class Role extends HibernateEntity
{
    private Long rolePK;
    private String name;

    //  Children
    private Set<OperatorRole> operatorRoles;
    private Set<FilteredRole> filteredRoles;
    private Set<ImpliedRole> parentRoles;

    public static final String DATABASE = SessionHelper.SECURITY;

    public static String DIRECTLY_IMPLIED = "DIRECT";

    public static String INDIRECTLY_IMPLIED = "INDIRECT";

    public static String NOT_IMPLIED = "NOT IMPLIED";

    private String roleImplication;

    public Role()
    {
        init();
    }

    private void init()
    {
        if (operatorRoles == null)
        {
            operatorRoles = new HashSet<OperatorRole>();
        }

        if (filteredRoles == null)
        {
            filteredRoles = new HashSet<FilteredRole>();
        }

        if (parentRoles == null)
        {
            parentRoles = new HashSet<ImpliedRole>();
        }
    }

    /**
     * Getter.
     * @return
     */
    public Long getRolePK()
    {
        return this.rolePK;
    }

    /**
     * Getter.
     * @return
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Setter.
     * @param rolePK
     */
    public void setRolePK(Long rolePK)
    {
        this.rolePK = rolePK;
    }

    /**
     * Setter.
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Getter
     *
     * @return set of operatorRoles
     */
    public Set<OperatorRole> getOperatorRoles()
    {
        return operatorRoles;
    }

    /**
     * Setter
     *
     * @param operatorRoles set of operatorRoles
     */
    public void setOperatorRoles(Set<OperatorRole> operatorRoles)
    {
        this.operatorRoles = operatorRoles;
    }

    /**
     * Adds a OperatorRole to the set of children
     *
     * @param operatorRole
     */
    public void addOperatorRole(OperatorRole operatorRole)
    {
        this.getOperatorRoles().add(operatorRole);

        operatorRole.setRole(this);

        SessionHelper.saveOrUpdate(operatorRole, Role.DATABASE);
    }

    /**
     * Removes a OperatorRole from the set of children
     *
     * @param operatorRole
     */
    public void removeOperatorRole(OperatorRole operatorRole)
    {
        this.getOperatorRoles().remove(operatorRole);

        operatorRole.setRole(null);

        SessionHelper.saveOrUpdate(operatorRole, Role.DATABASE);
    }

    /**
     * Getter
     *
     * @return set of filteredRoles
     */
    public Set<FilteredRole> getFilteredRoles()
    {
        return filteredRoles;
    }

    /**
     * Setter
     *
     * @param filteredRoles set of filteredRoles
     */
    public void setFilteredRoles(Set<FilteredRole> filteredRoles)
    {
        this.filteredRoles = filteredRoles;
    }

    /**
     * Adds a FilteredRole to the set of children
     *
     * @param filteredRole
     */
    public void addFilteredRole(FilteredRole filteredRole)
    {
        this.getFilteredRoles().add(filteredRole);

        filteredRole.setRole(this);

        SessionHelper.saveOrUpdate(filteredRole, Role.DATABASE);
    }

    /**
     * Removes a FilteredRole from the set of children
     *
     * @param filteredRole
     */
    public void removeFilteredRole(FilteredRole filteredRole)
    {
        this.getFilteredRoles().remove(filteredRole);

        filteredRole.setRole(null);

        SessionHelper.saveOrUpdate(filteredRole, Role.DATABASE);
    }


    /**
     * Getter
     *
     * @return set of impliedRoles
     */
    public Set<ImpliedRole> getParentRoles()
    {
        return parentRoles;
    }

    /**
     * Setter
     *
     * @param parentRoles set of impliedRoles
     */
    public void setParentRoles(Set<ImpliedRole> parentRoles)
    {
        this.parentRoles = parentRoles;
    }

    /**
     * Adds a ImpliedRole to the set of children
     *
     * @param impliedRole
     */
    public void addImpliedRole(ImpliedRole impliedRole)
    {
        this.getParentRoles().add(impliedRole);

        impliedRole.setParentRole(this);

        SessionHelper.saveOrUpdate(impliedRole, Role.DATABASE);
    }

    /**
     * Removes a ImpliedRole from the set of children
     *
     * @param impliedRole
     */
    public void removeImpliedRole(ImpliedRole impliedRole)
    {
        this.getParentRoles().remove(impliedRole);

        impliedRole.setParentRole(null);

        SessionHelper.saveOrUpdate(impliedRole, Role.DATABASE);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        if (roleExists())
        {
            try
            {
                throw new EDITSecurityException("Error - Duplicate Role Name");
            }
            catch (EDITSecurityException e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        else
        {
            SessionHelper.saveOrUpdate(this, Role.DATABASE);
        }
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SecuredMethod[] securedMethod = SecuredMethod.findByRolePK(this.getRolePK());

        if (securedMethod != null)
        {
            for (int i = 0; i < securedMethod.length; i++)
            {
                securedMethod[i].hDelete();
            }
        }

        ImpliedRole[] impliedRole = ImpliedRole.findByRoleFK(this.getRolePK());

        if (impliedRole != null)
        {
            for (int i = 0; i < impliedRole.length; i++)
            {
                impliedRole[i].hDelete();
            }
        }

        FilteredRole[] filteredRoles = FilteredRole.findByRole(this.getRolePK());
        if (filteredRoles != null)
        {
            for (int i = 0; i < filteredRoles.length; i++)
            {
                FilteredRole filteredRole = filteredRoles[i];
                filteredRole.hDelete();
            }
        }

        SessionHelper.delete(this, Role.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Role.DATABASE;
    }

    public BIZRoleVO getBIZRoleVO()
    {
        BIZRoleVO bizRoleVO = new BIZRoleVO();

        bizRoleVO.setRoleVO(getVO());

        bizRoleVO.setImplication(getRoleImplication());

        return bizRoleVO;
    }
    
    //  This method is temporary to populate BIZRoleVO. Please do not use this method for any other purposes. - SP
    public RoleVO getVO()
    {
        RoleVO roleVO = new RoleVO();
        roleVO.setRolePK(this.getRolePK().longValue());
        roleVO.setName(this.getName());
        
        return roleVO;
    }


    public void setRoleName(String roleName)
    {
        this.setName(roleName);
    }

    protected void setRoleImplication(String roleImplication)
    {
        this.roleImplication = roleImplication;
    }

    private String getRoleImplication()
    {
        return roleImplication;
    }

    public void attachImpliedRole(Role associatedRole) throws EDITSecurityException
    {
        ImpliedRole impliedRole = new ImpliedRole();

        if (ImpliedRole.findByRolePKAndImpliedRolePK(this.getRolePK(), associatedRole.getRolePK()) == null)
        {
            if (!roleInSetOfImpliedRoles(associatedRole, this)) // Don't allow recursive associations
            {
                impliedRole.setRoleFK(this.getRolePK());

//                impliedRole.setImpliedRoleFK(associatedRole.getRolePK());
                impliedRole.setParentRole(associatedRole);

                impliedRole.hSave();
            }
            else
            {
                throw new EDITSecurityException("Error - (A Implies B) AND (B Implies A) Associations Are Not Allowed");
            }
        }

        // New one is added ... Reload cache
        FilteredRole.loadImpliedRolesByFilteredRole();
    }

    public void detachImpliedRole(Role associatedRole) throws EDITSecurityException
    {
        String roleImplication = getRoleImplication(this, associatedRole);

        if (roleImplication.equals(Role.DIRECTLY_IMPLIED))
        {
            ImpliedRole impliedRole = ImpliedRole.findByRolePKAndImpliedRolePK(this.getRolePK(), associatedRole.getRolePK());

            impliedRole.hDelete();
        }

        else if ((roleImplication.equals(Role.INDIRECTLY_IMPLIED)))
        {
            throw new EDITSecurityException("Error - Indirectly Mapped Roles Can Not Be Detached");
        }

    }

    /**
     * Recursively finds all unique implied Roles. It does not include the target Role itself
     *
     * @return
     */
    public Role[] getImpliedRoles()
    {
        Role[] allImpliedRoles = getImpliedRolesRecursively(this);

        if (allImpliedRoles != null)
        {
            for (int i = 0; i < allImpliedRoles.length; i++)
            {
                allImpliedRoles[i].setRoleImplication(getRoleImplication(this, allImpliedRoles[i]));
            }
        }

        return allImpliedRoles;
    }

    public Operator[] getOperators()
    {
        return Operator.findByRolePK(this);
    }

    public ComponentMethod[] getComponentMethods(String componentNameCT, ProductStructure productStructure)
    {
        ComponentMethod.synchronizeComponentMethodsWithDB(componentNameCT);

        // Find all methods which are accessible
        List targetRoleAccessibleMethods = new ArrayList();

        SecuredMethod[] securedMethods =
                SecuredMethod.
                findByComponentNameCTRoleProductStructure(componentNameCT, this, productStructure.getProductStructurePK());

        if (securedMethods != null)
        {
            for (int i = 0; i < securedMethods.length; i++)
            {
                ComponentMethod componentMethod = securedMethods[i].getComponentMethod();

                targetRoleAccessibleMethods.add(componentMethod);
            }
        }

        Role[] impliedRoles = this.getImpliedRoles();

        if (impliedRoles != null)
        {
            for (int i = 0; i < impliedRoles.length; i++)
            {
                Role impliedRole = Role.findByPK(impliedRoles[i].getRolePK());
                securedMethods = SecuredMethod.findByComponentNameCTRoleProductStructure(componentNameCT, impliedRole, productStructure.getProductStructurePK());

                if (securedMethods != null)
                {
                    for (int j = 0; j < securedMethods.length; j++)
                    {
                        ComponentMethod componentMethod = securedMethods[j].getComponentMethod();

                        if (!targetRoleAccessibleMethods.contains(componentMethod))
                        {
                            targetRoleAccessibleMethods.add(componentMethod);
                        }
                    }
                }
            }
        }

        unionWithComponentMethod(targetRoleAccessibleMethods, componentNameCT);

        ComponentMethod[] componentMethods = (ComponentMethod[]) targetRoleAccessibleMethods.toArray(new ComponentMethod[targetRoleAccessibleMethods.size()]);

        return componentMethods;
    }

    public static Role[] findByOperatorPK(Long operatorPK)
    {
        Role[] roles = null;

        String hql = "select role from Role role " +
                " join role.OperatorRoles operatorRole " +
                " where operatorRole.OperatorFK = :operatorPK";

        Map params = new HashMap();
        params.put("operatorPK", operatorPK);

        List results = SessionHelper.executeHQL(hql, params, Role.DATABASE);

        if (!results.isEmpty())
        {
            roles = (Role[]) results.toArray(new Role[results.size()]);
        }

        return roles;
    }

    public static Role[] findByComponentMethodPK(Long componentMethodPK)
    {
        Role[] roles = null;

        String hql = "select distinct role from Role role " +
                " join role.SecuredMethod securedMethod " +
                " join role.FilteredRole filteredRole " +
                " join securedMethod.CompnentMethod componentMethod " +
                " join filteredRole.CompanyStructure companyStructure " +
                " where componentMethod.ComponentMethodPK = :componentMethodPK";

        Map params = new HashMap();
        params.put("componentMethodPK", componentMethodPK);

        List results = SessionHelper.executeHQL(hql, params, Role.DATABASE);

        if (!results.isEmpty())
        {
            roles = (Role[]) results.toArray(new Role[results.size()]);
        }

        return roles;
    }

    public static Role[] findByRoleName(String name)
    {
        Role[] roles = null;

        String hql = "select role from Role role " +
                " where role.Name = :name";

        Map params = new HashMap();
        params.put("name", name);

        List results = SessionHelper.executeHQL(hql, params, Role.DATABASE);

        if (!results.isEmpty())
        {
            roles = (Role[]) results.toArray(new Role[results.size()]);
        }

        return roles;
    }

    public static Role[] findAll() throws Exception
    {
        Role[] roles = null;

        String hql = "select role from Role role";

        Map params = new HashMap();

        List results = SessionHelper.executeHQL(hql, params, Role.DATABASE);

        if (!results.isEmpty())
        {
            roles = (Role[]) results.toArray(new Role[results.size()]);
        }

        return roles;
    }

    //    responsibility moved to FilteredRole and FilteredRoleImpl
    //    public void secureComponentMethod(ComponentMethod componentMethod, boolean authorize) throws EDITSecurityException
    //    {
    //        roleImpl.secureComponentMethod(this, componentMethod, authorize);
    //    }

   /**
     * Finder.
     * @param rolePK
     * @return
     */
    public static Role findByPK(Long rolePK)
    {
        return (Role) SessionHelper.get(Role.class, rolePK, SessionHelper.SECURITY);
    }

    public static Role[] findImpliedRolesByRolePK(Long rolePK)
    {
        String hql = "select role from Role role " +
                " where role.RolePK IN (select impliedRole.ImpliedRoleFK from ImpliedRole impliedRole " +
                "                       where (impliedRole.RoleFK = :rolePK))";

        Map params = new HashMap();
        params.put("rolePK", rolePK);

        List results = SessionHelper.executeHQL(hql, params, Role.DATABASE);

        return (Role[]) results.toArray(new Role[results.size()]);
    }

    public void associate(Operator operator) throws EDITSecurityException
    {
        OperatorRole operatorRole = OperatorRole.findByOperatorPKAndRolePK(operator.getOperatorPK(), this.getRolePK());

        if (operatorRole == null)
        {
            operatorRole = (OperatorRole) SessionHelper.newInstance(OperatorRole.class, Role.DATABASE);

//            operatorRole.associate(operator, this);

            operatorRole.setRole(this);
            
            operatorRole.setOperator(operator);
            
            operatorRole.hSave();
        }
    }

    public void disassociate(Operator operator) throws EDITSecurityException
    {
        OperatorRole operatorRole = OperatorRole.findByOperatorPKAndRolePK(operator.getOperatorPK(), this.getRolePK());

        if (operatorRole != null)
        {
            operatorRole.hDelete();
        }
        else
        {
            throw new EDITSecurityException("Error - Indirectly Mapped Roles Can Not Be Detached");
        }
    }


    public static BIZRoleVO[] mapEntityToBIZVO(Role[] roles)
    {
        BIZRoleVO[] bizRoleVO = null;

        if (roles != null)
        {
            bizRoleVO = new BIZRoleVO[roles.length];

            for (int i = 0; i < roles.length; i++)
            {
                bizRoleVO[i] = roles[i].getBIZRoleVO();
            }
        }

        return bizRoleVO;
    }

    public boolean hasDependantRoles()
    {
        boolean hasDependantRoles = false;

        ImpliedRole[] impliedRole = ImpliedRole.findByImpliedRoleFK(this.getRolePK());

        hasDependantRoles = (impliedRole.length > 0);

        return hasDependantRoles;
    }

    public boolean hasDependantOperators()
    {
        boolean hasDependantOperators = false;

        OperatorRole[] operatorRole = OperatorRole.findByRolePK(this.getRolePK());

        hasDependantOperators = (operatorRole != null);

        return hasDependantOperators;
    }

    private boolean roleExists()
    {
        boolean roleExists = false;

        String roleName = this.getName();

        Role[] roles = findByRoleName(roleName);

        if (roles != null)
        {
            roleExists = true;
        }

        return roleExists;
    }

    private Role[] getImpliedRolesRecursively(Role role)
    {
        HashSet allImpliedRoles = new HashSet(); // We only want unique Roles

        Role[] impliedRoles = findImpliedRolesByRolePK(role.getRolePK());

        if (impliedRoles != null)
        {
            allImpliedRoles.addAll(Arrays.asList(impliedRoles));

            for (int i = 0; i < impliedRoles.length; i++)
            {
                Role[] currentImpliedRoles = getImpliedRolesRecursively(impliedRoles[i]);

                if (currentImpliedRoles != null)
                {
                    allImpliedRoles.addAll(Arrays.asList(currentImpliedRoles));
                }
            }
        }

        if (allImpliedRoles.isEmpty())
        {
            return null;
        }
        else
        {
            return (Role[]) allImpliedRoles.toArray(new Role[allImpliedRoles.size()]);
        }
    }
    
    private void unionWithComponentMethod(List targetRoleAccessibleMethods, String componentNameCT)
    {
        ComponentMethod[] componentMethods = ComponentMethod.findByComponentNameCT(componentNameCT);

        for (int i = 0; i < componentMethods.length; i++)
        {
            if (!targetRoleAccessibleMethods.contains(componentMethods[i]))
            {
                targetRoleAccessibleMethods.add(componentMethods[i]);
            }
        }
    }

    private boolean roleInSetOfImpliedRoles(Role role, Role impliedRole)
    {
        boolean roleInSetOfImpliedRoles = false;

        Role[] impliedRoles = getImpliedRolesRecursively(role);

        if (impliedRoles != null)
        {
            for (int i = 0; i < impliedRoles.length; i++)
            {
                if (impliedRole.getRolePK() == impliedRoles[i].getRolePK())
                {
                    roleInSetOfImpliedRoles = true;

                    break;
                }
            }
        }

        return roleInSetOfImpliedRoles;
    }

    private String getRoleImplication(Role role, Role impliedRole)
    {
        String roleImplication = null;

        if (roleInSetOfImpliedRoles(role, impliedRole))
        {
            if (ImpliedRole.findByRolePKAndImpliedRolePK(role.getRolePK(), impliedRole.getRolePK()) == null)
            {
                roleImplication = Role.INDIRECTLY_IMPLIED;
            }
            else
            {
                roleImplication = Role.DIRECTLY_IMPLIED;
            }
        }
        else
        {
            roleImplication = Role.NOT_IMPLIED;
        }

        return roleImplication;
    }
}

