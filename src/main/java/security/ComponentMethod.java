/*
 * User: gfrosti
 * Date: Jan 20, 2004
 * Time: 11:19:28 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.common.exceptions.EDITSecurityException;
import edit.common.vo.BIZComponentMethodVO;
import edit.common.vo.ComponentMethodVO;

import edit.services.db.hibernate.*;
import edit.services.component.*;

import engine.ProductStructure;

import java.util.*;
import java.lang.reflect.*;


public class ComponentMethod extends HibernateEntity
{
    private Long componentMethodPK;

    private String componentNameCT;
    private String componentClassName;
    private String methodName;
    
    // Children
    private Set<SecuredMethod> securedMethods;

    public static final String DATABASE = SessionHelper.SECURITY;
    public static final String VIEW_ALL_CLIENTS = "viewAllClients";
    public static final String VIEW_ALL_AGENTS = "viewAllAgents";
    public static final String VIEW_ALL_SUSPENSE = "viewAllSuspense";

    public ComponentMethod()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getComponentMethodPK()
    {
        return this.componentMethodPK;
    }

    /**
     * Getter.
     * @return
     */
    public String getComponentNameCT()
    {
        return this.componentNameCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getComponentClassName()
    {
        return this.componentClassName;
    }

    /**
     * Getter.
     * @return
     */
    public String getMethodName()
    {
        return this.methodName;
    }

    /**
     * Setter.
     * @param componentMethodPK
     */
    public void setComponentMethodPK(Long componentMethodPK)
    {
        this.componentMethodPK = componentMethodPK;
    }

    /**
     * Setter.
     * @param componentNameCT
     */
    public void setComponentNameCT(String componentNameCT)
    {
        this.componentNameCT = componentNameCT;
    }

    /**
     * Setter.
     * @param componentClassName
     */
    public void setComponentClassName(String componentClassName)
    {
        this.componentClassName = componentClassName;
    }

    /**
     * Setter.
     * @param methodName
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    /**
     * Setter.
     * @param securedMethods
     */
    public void setSecuredMethods(Set<SecuredMethod> securedMethods)
    {
        this.securedMethods = securedMethods;
    }

    /**
     * Getter.
     * @return
     */
    public Set<SecuredMethod> getSecuredMethods()
    {
        return securedMethods;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ComponentMethod.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ComponentMethod.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ComponentMethod.DATABASE;
    }

    public static ComponentMethod findByComponentClassNameAndMethodName(String componentClassName, String methodName)
    {
        ComponentMethod componentMethod = null;

        String hql = "select componentMethod from ComponentMethod componentMethod " +
                " where componentMethod.ComponentClassName = :componentClassName " +
                " and componentMethod.MethodName = :methodName";

        Map params = new HashMap();
        params.put("componentClassName", componentClassName);
        params.put("methodName", methodName);

        List results = SessionHelper.executeHQL(hql, params, ComponentMethod.DATABASE);

        if (!results.isEmpty())
        {
            componentMethod = (ComponentMethod) results.get(0);
        }

        return componentMethod;
    }

    public static ComponentMethod[] findByComponentNameCT(String componentNameCT)
    {
        String hql = "select componentMethod from ComponentMethod componentMethod " +
                " where componentMethod.ComponentNameCT = :componentNameCT";

        Map params = new HashMap();
        params.put("componentNameCT", componentNameCT);

        List results = SessionHelper.executeHQL(hql, params, ComponentMethod.DATABASE);

        return (ComponentMethod[]) results.toArray(new ComponentMethod[results.size()]); 
    }

    /**
     * A componentMethod is not editable if it has been indirectly mapped to the target role, and it has
     * not been directly mapped to the target role.
     *
     * @param role
     * @param productStructure
     *
     * @return
     */
    public boolean isEditable(Role role, ProductStructure productStructure)
    {
        boolean isEditable = true;

        // Only drill-down as far as possible. Start with the target role,
        // and then continue with the implied roles if necessary.
        SecuredMethod securedMethod =
                SecuredMethod.findByRoleMethodProductStructure(role, this, productStructure.getProductStructurePK());

        if (securedMethod != null) // directly?
        {
            isEditable = true;
        }
        else // indirectly?
        {
            Role[] impliedRoles = role.getImpliedRoles();

            if (impliedRoles != null)
            {
                for (int i = 0; i < impliedRoles.length; i++)
                {
                    Role impliedRole = Role.findByPK(impliedRoles[i].getRolePK());

                    securedMethod = SecuredMethod.findByRoleMethodProductStructure(impliedRole,
                            this, productStructure.getProductStructurePK());

                    if (securedMethod != null)
                    {
                        isEditable = false;

                        break;
                    }
                }
            }
        }

        return isEditable;
    }

    /**
     * A componentMethod is not editable if it has been indirectly mapped to the target role, and it has
     * not been directly mapped to the target role.
     *
     * @param filteredRole
     *
     * @return
     */
    public boolean isEditable(FilteredRole filteredRole)
    {
        boolean isEditable = true;

        Role role = filteredRole.getRole();
        ProductStructure productStructure = ProductStructure.findByPK(filteredRole.getProductStructureFK());

        // Only drill-down as far as possible. Start with the target role,
        // and then continue with the implied roles if necessary.
        SecuredMethod securedMethod = SecuredMethod.findByRoleMethodProductStructure(role, this, productStructure.getProductStructurePK());

        if (securedMethod != null) // directly?
        {
            isEditable = true;
        }
        else // indirectly?
        {
            Role[] impliedRoles = role.getImpliedRoles();

            if (impliedRoles != null)
            {
                for (int i = 0; i < impliedRoles.length; i++)
                {
                    Role impliedRole = Role.findByPK(impliedRoles[i].getRolePK());

                    securedMethod = SecuredMethod.findByRoleMethodProductStructure(impliedRole,
                            this, productStructure.getProductStructurePK());

                    if (securedMethod != null)
                    {
                        isEditable = false;

                        break;
                    }
                }
            }
        }

        return isEditable;
    }

    //public boolean isAuthorized(Role role)
    //{
    //    return componentMethodImpl.isAuthorized(this, role);
    //}

    /**
     * A role is authorized to invoke a method directly or indirectly, otherwise the role is not authorized.
     *
     * @param role
     * @param productStructure
     *
     * @return
     */
    public boolean isAuthorized(Role role, ProductStructure productStructure)
    {
//        FilteredRole filteredRole = new FilteredRole(role.getRolePK(), productStructure.getPK());
        FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(role.getRolePK(), productStructure.getProductStructurePK());

        boolean isAuthorized = false;

        // Only drill-down as far as possible. Start with the target role, and then continue with the implied roles if necessary.
        SecuredMethod securedMethod =
                SecuredMethod.findByFilteredRoleComponentMethod(filteredRole,
                        this);

        if (securedMethod != null) // directly?
        {
            isAuthorized = true;
        }
        else // indirectly?
        {
            Role[] impliedRoles = role.getImpliedRoles();

            if (impliedRoles != null)
            {
                for (int i = 0; i < impliedRoles.length; i++)
                {
                    Long productStructurePK = filteredRole.getProductStructureFK();
                    Long componentMethodPK = this.getComponentMethodPK();

                    Role impliedRole = Role.findByPK(impliedRoles[i].getRolePK());

                    securedMethod = SecuredMethod.findByRoleMethodProductStructure(impliedRole, this, productStructurePK);

                    if (securedMethod != null)
                    {
                        isAuthorized = true;

                        break;
                    }
                }
            }
        }

        return isAuthorized;
    }

    /**
     * A role is authorized to invoke a method directly or indirectly, otherwise the role is not authorized.
     *
     * @param filteredRole
     *
     * @return
     */
    public boolean isAuthorized(FilteredRole filteredRole)
    {
        boolean isAuthorized = false;

        // Only drill-down as far as possible. Start with the target role, and then continue with the implied roles if necessary.
        SecuredMethod securedMethod = SecuredMethod.findByFilteredRoleComponentMethod(filteredRole, this);

        if (securedMethod != null) // directly?
        {
            isAuthorized = true;
        }
        else // indirectly?
        {
            Role[] impliedRoles = filteredRole.getImpliedRoles();

            if (impliedRoles != null)
            {
                List<Long> rolePKs = new ArrayList<Long>();

                for (int i = 0; i < impliedRoles.length; i++)
                {
                    Long rolePK = impliedRoles[i].getRolePK();

                    rolePKs.add(rolePK);
                }

                securedMethod = SecuredMethod.findByRolesMethodProductStructure(rolePKs,
                          this, filteredRole.getProductStructureFK());

                if (securedMethod != null)
                {
                    isAuthorized = true;
                }
            }
        }

        return isAuthorized;
    }

    /**
     * Is this ComponentMethod authorized to be used by the operator
     *
     * @param sessionId
     *
     * @return
     */
    public boolean isAuthorized(String sessionId)
    {
        SecuritySession securitySession = SecuritySession.getSecuritySession(sessionId);

        boolean isAuthorized = false;

        // all of the FilteredRoles that will allow this component method
        // this hits the db
        FilteredRole[] requiredFilteredRoles = getMappedFilteredRoles();

        if (requiredFilteredRoles == null) // If this componentMethod has never been mapped to a Role, is is not accessible
        {
            isAuthorized = false;
        }
        else
        {

            // All of the FilteredRoles that the Operator has based on
            // what roles he's been authorized.
            FilteredRole[] filteredRolesFromSession =
                    securitySession.getFilteredRoles();

            loop:
            for (int i = 0; i < filteredRolesFromSession.length; i++)
            {
                for (int j = 0; j < requiredFilteredRoles.length; j++)
                {
                    // did we find a match?
                    if (requiredFilteredRoles[j].getFilteredRolePK().equals(filteredRolesFromSession[i].getFilteredRolePK()))
                    {
                        isAuthorized = true;

                        break loop;

                    }
                }
            }
        }

        return isAuthorized;
    }

    /**
     * Filter the array so that only those that have a particular
     * product structure are left.
     *
     * @param filteredRoles
     * @param productStructurePK
     *
     * @return
     */
    private FilteredRole[] filterForProductStructure(FilteredRole[] filteredRoles,
                                                     Long productStructurePK)
    {
        List filteredList = new ArrayList();
        for (int i = 0; i < filteredRoles.length; i++)
        {
            FilteredRole filteredRole = filteredRoles[i];
            if (productStructurePK.equals(filteredRole.getProductStructureFK()))
            {
                filteredList.add(filteredRole);
            }
        }

        return (FilteredRole[]) filteredList.toArray(new FilteredRole[filteredList.size()]);
    }

    //    private Role[] getMappedRoles()
    //    {
    //        return Role.findByComponentMethodPK(getPK());
    //    }

    private FilteredRole[] getMappedFilteredRoles()
    {
        return FilteredRole.findByComponentMethodPK(this.getComponentMethodPK());
    }

    /**
     * @param role
     * @param productStructure
     *
     * @return
     *
     * @throws Exception
     */
    public BIZComponentMethodVO getBIZComponentMethodVO(Role role, ProductStructure productStructure) throws Exception
    {
        BIZComponentMethodVO bizComponentMethodVO = new BIZComponentMethodVO();

        bizComponentMethodVO.setIsAuthorized(isAuthorized(role, productStructure));

        bizComponentMethodVO.setIsEditable(isEditable(role, productStructure));
        
        bizComponentMethodVO.setComponentMethodVO(getVO());

        return bizComponentMethodVO;
    }
    
    //  This method is for temporary purpose only. Please do not use for any other purpose but populating BIZComponentMethodVO. - SP
    public ComponentMethodVO getVO()
    {
        ComponentMethodVO componentMethodVO = new ComponentMethodVO();
        
        componentMethodVO.setComponentMethodPK(this.getComponentMethodPK().longValue());
        componentMethodVO.setComponentClassName(this.getComponentNameCT());
        componentMethodVO.setComponentClassName(this.getComponentClassName());
        componentMethodVO.setMethodName(this.getMethodName());
        
        return componentMethodVO;
    }
    
    /**
     * Any time a new usecase method is added to the component the new entry has to be 
     * inserted in to the database. This is taken care by this method while booting the system.
     * @throws EDITSecurityException
     */
    public static void synchronizeAllComponentMethodsWithDB() throws EDITSecurityException
    {
        Role[] roles = Role.findByRoleName("admin");

        if (roles != null) 
        {
            Role adminRole = roles[0];
            
            ProductStructure securityProductStructure = ProductStructure.findBy_CompanyName("Security")[0];

            FilteredRole[] filteredRolesForAdminRole = FilteredRole.findByRole(adminRole.getRolePK());

            // Map SecurityComponent methods to the admin role.
            String[] componentCategories = Component.COMPONENT_CATEGORIES;

            for (int i = 0; i < componentCategories.length; i++)
            {
                // Secure only that are newly added to database
                ComponentMethod[] newComponentMethodsAddedToDB = ComponentMethod.synchronizeComponentMethodsWithDB(componentCategories[i]);

                for (int j = 0; j < filteredRolesForAdminRole.length; j++)
                {
                    // Do not secure the ComponentMethod with "Security" product structure
                    if (securityProductStructure.getProductStructurePK().longValue() != filteredRolesForAdminRole[j].getProductStructureFK().longValue()) 
                    {
                        for (int k = 0; k < newComponentMethodsAddedToDB.length; k++)
                        {
                            FilteredRole filteredRole = filteredRolesForAdminRole[j];

                            filteredRole.secureComponentMethod(newComponentMethodsAddedToDB[k], true);
                        }    
                    }
                }
            }    
        }
    }

    public static ComponentMethod[] synchronizeComponentMethodsWithDB(String componentNameCT)
    {
        List<ComponentMethod> newComponentMethodsAddedToDB = new ArrayList<ComponentMethod>();

        ComponentMethod[] dbComponentMethods = findByComponentNameCT(componentNameCT);

        ComponentMethod[] classComponentMethods = getUniqueComponentMethods(componentNameCT);

        try
        {
            SessionHelper.beginTransaction(ComponentMethod.DATABASE);

            if (dbComponentMethods != null) // Synchronize the component's and the db's methods
            {
                for (int i = 0; i < dbComponentMethods.length; i++) // Remove abandoned db methods
                {
                    if (!componentMethodExistsByValue(classComponentMethods, dbComponentMethods[i]))
                    {
                        dbComponentMethods[i].hDelete();
            }
                }

                for (int i = 0; i < classComponentMethods.length; i++) // Add new db methods
                {
                    if (!componentMethodExistsByValue(dbComponentMethods, classComponentMethods[i]))
                    {
                        classComponentMethods[i].hSave();

                        newComponentMethodsAddedToDB.add(classComponentMethods[i]);
                    }
                }
            }
            else // Map all component methods to DB
            {
                for (int i = 0; i < classComponentMethods.length; i++)
                {
                    classComponentMethods[i].hSave();

                    newComponentMethodsAddedToDB.add(classComponentMethods[i]);
                }
            }

            SessionHelper.commitTransaction(ComponentMethod.DATABASE);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            
            SessionHelper.rollbackTransaction(ComponentMethod.DATABASE);

            throw new RuntimeException(e);
        }

        return (ComponentMethod[]) newComponentMethodsAddedToDB.toArray(new ComponentMethod[newComponentMethodsAddedToDB.size()]);
    }

//    public boolean equalsByValue(ComponentMethod componentMethod)
//    {
//        boolean equalsByValue = true;
//
//        ComponentMethodVO argVO = (ComponentMethodVO) componentMethod.getVO();
//
//        if (!this.componentMethodVO.getComponentNameCT().equals(argVO.getComponentNameCT()))
//        {
//            equalsByValue = false;
//        }
//        else if (!this.componentMethodVO.getComponentClassName().equals(argVO.getComponentClassName()))
//        {
//            equalsByValue = false;
//        }
//        else if (!this.componentMethodVO.getMethodName().equals(argVO.getMethodName()))
//        {
//            equalsByValue = false;
//        }
//
//        return equalsByValue;
//    }
    
    public boolean equalsByValue(ComponentMethod componentMethod)
    {
        boolean equalsByValue = true;

        if (!this.getComponentNameCT().equals(componentMethod.getComponentNameCT()))
        {
            equalsByValue = false;
        }
        else if (!this.getComponentClassName().equals(componentMethod.getComponentClassName()))
        {
            equalsByValue = false;
        }
        else if (!this.getMethodName().equals(componentMethod.getMethodName()))
        {
            equalsByValue = false;
        }

        return equalsByValue;
    }

    //public static BIZComponentMethodVO[] mapEntityToBIZVO(ComponentMethod[] componentMethods, Role role) throws Exception
    //{
    //    BIZComponentMethodVO[] bizComponentMethodVO = null;
    //
    //    if (componentMethods != null)
    //    {
    //        bizComponentMethodVO = new BIZComponentMethodVO[componentMethods.length];
    //
    //        for (int i = 0; i < componentMethods.length; i++)
    //        {
    //            bizComponentMethodVO[i] = componentMethods[i].getBIZComponentMethodVO(role);
    //        }
    //    }
    //
    //    return bizComponentMethodVO;
    //}

    public static BIZComponentMethodVO[] mapEntityToBIZVO(ComponentMethod[] componentMethods,
                                                          Role role,
                                                          ProductStructure productStructure)
            throws Exception
    {
        BIZComponentMethodVO[] bizComponentMethodVO = null;

        if (componentMethods != null)
        {
            bizComponentMethodVO = new BIZComponentMethodVO[componentMethods.length];

            for (int i = 0; i < componentMethods.length; i++)
            {
                bizComponentMethodVO[i] = componentMethods[i].
                        getBIZComponentMethodVO(role, productStructure);
            }
        }

        return bizComponentMethodVO;
    }


    public static ComponentMethod[] getUniqueComponentMethods(String componentNameCT)
    {
        Map uniqueComponentMethods = new HashMap();

        Class componentInterface = null;
        Class componentClass = null;

        if (componentNameCT.equals(Component.ACCOUNTING))
        {
            componentInterface = accounting.business.AccountingUseCase.class;
            componentClass = accounting.component.AccountingUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.AGENT))
        {
            componentInterface = agent.business.AgentUseCase.class;
            componentClass = agent.component.AgentUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.BATCH))
        {
            componentInterface = batch.business.BatchUseCase.class;
            componentClass = batch.component.BatchUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.CLIENT))
        {
            componentInterface = client.business.ClientUseCase.class;
            componentClass = client.component.ClientUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.EVENT))
        {
            componentInterface = event.business.EventUseCase.class;
            componentClass = event.component.EventUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.INFORCE))
        {
            componentInterface = contract.business.InforceUseCase.class;
            componentClass = contract.component.InforceUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.NEWBUSINESS))
        {
            componentInterface = contract.business.NewBusinessUseCase.class;
            componentClass = contract.component.NewBusinessUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.PRODUCTBUILD))
        {
            componentInterface = productbuild.business.ProductBuildUseCase.class;
            componentClass = productbuild.component.ProductBuildUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.SECURITY))
        {
            componentInterface = security.business.SecurityUseCase.class;
            componentClass = security.component.SecurityUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.REINSURANCE))
        {
            componentInterface = reinsurance.business.ReinsuranceUseCase.class;
            componentClass = reinsurance.component.ReinsuranceUseCaseComponent.class;
        }
        else if (componentNameCT.equals(Component.RMD))
        {
            componentInterface = reporting.business.RMDNotificationUseCase.class;
            componentClass = reporting.component.RMDNotificationUseCaseComponent.class;
        }
//        else if (componentNameCT.equals(Component.STAGING))
//        {
//            componentInterface = staging.business.StagingUseCase.class;
//            componentClass = staging.component.StagingUseCaseComponent.class;
//        }
        else if (componentNameCT.equals(Component.CASETRACKING))
        {
            componentInterface = casetracking.usecase.CasetrackingUseCase.class;
            componentClass = casetracking.usecase.CasetrackingUseCaseImpl.class;
        }
        else if (componentNameCT.equals(Component.CASE))
        {
            componentInterface = contract.business.CaseUseCase.class;
            componentClass = contract.component.CaseUseCaseComponent.class;
        }
        // Add all interface methods from the target component
        if (componentInterface != null)
        {
            addMethodToComponentMethods(uniqueComponentMethods, componentNameCT, componentInterface, componentClass);
        }

        // We need the default constructor of each of the components
//        if (componentClass != null)
//        {
//            addConstructorToComponentMethods(uniqueComponentMethods, componentNameCT, componentClass);
//        }

        return (ComponentMethod[]) uniqueComponentMethods.values().toArray(new ComponentMethod[uniqueComponentMethods.size()]);
    }
    
    public static ComponentMethod findByPK(Long componentMethodPK)
    {
        return (ComponentMethod) SessionHelper.get(ComponentMethod.class, componentMethodPK, ComponentMethod.DATABASE);
    }

    private static void addMethodToComponentMethods(Map uniqueComponentMethods, String componentNameCT, Class targetInterface, Class targetClass)
    {
        Method[] interfaceMethods = targetInterface.getMethods();

        String className = targetClass.getName();

        for (int i = 0; i < interfaceMethods.length; i++)
        {
            String methodName = interfaceMethods[i].getName();

            addUniqueComponentMethod(uniqueComponentMethods, componentNameCT, className, methodName);
        }
    }

    private static String parseCompoundName(String name)
    {
        return name.substring(name.lastIndexOf(".") + 1, name.length());
    }

    private static boolean componentMethodExistsByValue(ComponentMethod[] componentMethods, ComponentMethod componentMethod)
    {
        boolean componentMethodExistsByValue = false;

        for (int i = 0; i < componentMethods.length; i++)
        {
            ComponentMethod currentComponentMethod = componentMethods[i];

            if (currentComponentMethod.equalsByValue(componentMethod))
            {
                componentMethodExistsByValue = true;

                break;
            }
        }

        return componentMethodExistsByValue;
    }

    private static String getComponentMethodKey(ComponentMethod componentMethod)
    {
        String componentNameCT = componentMethod.getComponentNameCT();

        String componentClassName = componentMethod.getComponentClassName();

        String methodName = componentMethod.getMethodName();

        return componentNameCT + "." + componentClassName + "." + methodName;
    }

    private static void addUniqueComponentMethod(Map uniqueComponentMethods, String componentNameCT, String componentClassName, String methodName)
    {
        ComponentMethod componentMethod = new ComponentMethod();

        componentMethod.setComponentNameCT(componentNameCT);

        componentMethod.setComponentClassName(componentClassName);

        componentMethod.setMethodName(methodName);

        String key = getComponentMethodKey(componentMethod);

        if (!uniqueComponentMethods.containsKey(key))
        {
            uniqueComponentMethods.put(key, componentMethod);
        }
    }
}

