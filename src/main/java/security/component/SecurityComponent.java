/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 8, 2003
 * Time: 5:17:21 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package security.component;

import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.component.*;
import edit.services.db.*;
import edit.services.db.hibernate.SessionHelper;

import engine.*;
import security.*;
import security.business.*;
import security.sp.*;

import java.util.*;

public class SecurityComponent extends AbstractComponent implements Security
{
    public SecurityComponent(){}

    public void updatePassword(String username, String newPassword) throws EDITSecurityException
    {
        Operator operator = Operator.findByOperatorName(username);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            operator.updatePassword(newPassword);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (EDITSecurityException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception
    {
        return 0;
    }

    public String[] findVOs()
    {
        return null;
    };

    public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
        return super.deleteVO(voClass, primaryKey, ConnectionFactory.SECURITY_POOL, recursively);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList)
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.SECURITY_POOL, recursively, voInclusionList);
    }

    public String login(String username, String password) throws EDITSecurityException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            String sessionId = new SecurityProcessor().login(username, password);

            return sessionId;
        }
        catch (EDITSecurityAccessException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            SessionHelper.commitTransaction(SessionHelper.SECURITY);

            SessionHelper.clearSessions();
        }
    }

    public void logout(String sessionId)
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            new SecurityProcessor().logout(sessionId);
        }
        catch (EDITSecurityAccessException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            SessionHelper.commitTransaction(SessionHelper.SECURITY);

            SessionHelper.clearSessions();
        }
    }

    /**
     * Persists the securityProfile.  Instantiates the entity objects and sets their VOs.
     * @param securityProfile
     * @param passwordMask
     * @param pMask - mask that defines the password
     * @param operatorMask - mask that defines the operator
     * @throws EDITSecurityException
     */
    public void saveSecurityProfile(SecurityProfile securityProfile, PasswordMask passwordMask,
                                      Mask pMask, Mask operatorMask) throws EDITSecurityException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            new SecurityProcessor().saveSecurityProfile(securityProfile, pMask, operatorMask, passwordMask);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (EDITSecurityException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public SecurityProfile loadSecurityProfile()
    {
        // SecurityProfileVO securityProfileVO = null;
         SecurityProfile securityProfile = null;

        try
        {
            // SecurityProfile securityProfile = new SecurityProcessor().getSecurityProfile();            
            securityProfile = new SecurityProcessor().getSecurityProfile();

            // securityProfileVO = (SecurityProfileVO) securityProfile.getComposedVO();
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return securityProfile;
    }

    /**
     * Renders a list of the methods for a specified component as secured by a
     * given role. Methods which can not be edited are notes as such. The
     * assumption being that the method is accessible because of an implied role.
     * @param rolePK
     * @param componentNameCT
     * @return
     */
    public BIZComponentMethodVO[] getComponentMethods(
            long rolePK, String componentNameCT, ProductStructure productStructure)
    {
        BIZComponentMethodVO[] bizComponentMethodVOs = null;

        try
        {
            Role role = Role.findByPK(new Long(rolePK));

            ComponentMethod[] componentMethods =
                    role.getComponentMethods(componentNameCT, productStructure);

            bizComponentMethodVOs =
                    ComponentMethod.mapEntityToBIZVO(componentMethods, role, productStructure);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return bizComponentMethodVOs;
    }


    public BIZComponentMethodVO[] getComponentMethods(
                                    long rolePK,
                                    String componentNameCT,
                                    long productStructurePK)
    {
        BIZComponentMethodVO[] bizComponentMethodVOs = null;

        try
        {
            Role role = Role.findByPK(new Long(rolePK));

            ProductStructure productStructure = new ProductStructure(productStructurePK);

            ComponentMethod[] componentMethods =
                    role.getComponentMethods(componentNameCT, productStructure);

            bizComponentMethodVOs = ComponentMethod.mapEntityToBIZVO(componentMethods, role, productStructure);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return bizComponentMethodVOs;
    }


    public Role[] getAllRoles()
    {
        Role[] roles = null;

        try
        {
            roles = Role.findAll();

//            roleVOs = Role.mapEntityToVO(roles);

            // testing getting all product structures attached to a particular role
            //RoleVO testit = roleVOs[0];
            //getAttachedProductStructuresForRole(testit.getRolePK());

        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return roles;
    }

    public ProductStructureVO[] getAllProductTypeProductStructures()
    {

        ProductStructureVO[] productStructureVOs = null;

        try
        {
            ProductStructure[] productStructures = ProductStructure.findAllProductType();

            productStructureVOs = ProductStructure.mapEntityToVO(productStructures);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return productStructureVOs;
    }

    public ProductStructureVO[] getProductStructuresForRoleAttachment()
    {
        ProductStructureVO[] productStructureVOs = null;

        try
        {
            ProductStructure[] productStructures = ProductStructure.findAllProductType();

            ProductStructure[] securityProductStructures = ProductStructure.findBy_CompanyName("Security");

            List allProductStructures = new ArrayList();

            for (int i = 0; i < productStructures.length; i++)
            {
                allProductStructures.add(productStructures[i]);
            }

            for (int i = 0; i < securityProductStructures.length; i++)
            {
                allProductStructures.add(securityProductStructures[i]);
            }

            productStructures = (ProductStructure[]) allProductStructures.toArray(new ProductStructure[allProductStructures.size()]);

            productStructureVOs = ProductStructure.mapEntityToVO(productStructures);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return productStructureVOs;
    }

    public ProductStructureVO[] getAttachedProductStructuresForRole(long rolePK)
    {
        ProductStructureVO[] productStructureVOs =
                engine.dm.dao.DAOFactory.getProductStructureDAO().findForRole(rolePK, false, null);

        return productStructureVOs;
    }

    public void saveRole(String roleName) throws EDITSecurityException
    {
        Role role = new Role();

        role.setRoleName(roleName);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);
        
            role.hSave();
            
            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void saveRole(Role role) throws EDITSecurityException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);
        
            role.hSave();
            
            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }


    public void deleteRole(long rolePK) throws EDITSecurityException
    {
        Role role = Role.findByPK(new Long(rolePK));

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            role.hDelete();

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public BIZRoleVO[] getImpliedRoles(long rolePK)
    {
        Role role = Role.findByPK(new Long(rolePK));

        Role[] impliedRoles = role.getImpliedRoles();

        BIZRoleVO[] impliedBIZRoleVOs = null;

        if (impliedRoles != null)
        {
            impliedBIZRoleVOs = new BIZRoleVO[impliedRoles.length];

            for (int i = 0; i < impliedBIZRoleVOs.length; i++)
            {
                impliedBIZRoleVOs[i] = impliedRoles[i].getBIZRoleVO();
            }
        }

        return impliedBIZRoleVOs;
    }

    public void attachImpliedRole(Long rolePK, Long impliedRolePK) throws EDITSecurityException
    {
        Role role = Role.findByPK(rolePK);

        Role impliedRole = Role.findByPK(impliedRolePK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            role.attachImpliedRole(impliedRole);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void detachImpliedRole(Long rolePK, Long impliedRolePK) throws EDITSecurityException
    {
        Role role = Role.findByPK(rolePK);

        Role impliedRole = Role.findByPK(impliedRolePK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            role.detachImpliedRole(impliedRole);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void secureComponentMethod(Long rolePK,
                                      long[] componentMethodPKs,
                                      Long productStructurePK,
                                      String[] componentMethodYNs) throws EDITSecurityException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            for (int i = 0; i < componentMethodPKs.length; i++)
            {
                long componentMethodPK = componentMethodPKs[i];

                boolean isAuthorized = (componentMethodYNs[i].equals("Y"));

                FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(rolePK, productStructurePK);

                ComponentMethod componentMethod = ComponentMethod.findByPK(componentMethodPK);

                filteredRole.secureComponentMethod(componentMethod, isAuthorized);
            }

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }


    public Operator getOperator(Long operatorPK, List voInclusionList)
    {
        Operator operator = Operator.findByPK(operatorPK);

//        OperatorVO operatorVO = (OperatorVO) operator.getVO();
//
//        new VOHelper().compose(operatorVO, voInclusionList);

        return operator;
    }

    public Role getRole(long rolePK, List voInclusionList)
    {
        Role role = Role.findByPK(new Long(rolePK));

//        RoleVO roleVO = (RoleVO) role.getVO();

//        new VOHelper().compose(roleVO, voInclusionList);

        return role;
    }

    public BIZRoleVO[] getOperatorsRoles(Long operatorPK)
    {
        Operator operator = Operator.findByPK(operatorPK);

        Role[] roles = operator.getRoles();

        BIZRoleVO[] bizRoleVOs = Role.mapEntityToBIZVO(roles);

        return bizRoleVOs;
    }

    public Operator[] getAllOperators()
    {
        Operator[] operators = Operator.findAll();

        return operators;
    }
    
    public Operator[] getActiveOperators()
    {
        return Operator.getActiveOperators();
    }

    public Password getCurrentPassword(long operatorPK)
    {
        Operator operator = Operator.findByPK(new Long(operatorPK));

        Password activePassword = operator.getCurrentPassword();

        if (activePassword != null)
        {
            return activePassword;

        }
        else
        {
            return null;
        }
    }

    public Operator[] getOperatorsInRole(long rolePK)
    {
        Role role = Role.findByPK(new Long(rolePK));

        Operator[] operators = role.getOperators();

        return operators;
    }


    public String saveOperator(Operator operator, boolean operatorIsNew) throws EDITSecurityException
    {
        SecurityProfile securityProfile = SecurityProfile.getSingleton();

        Mask operatorMask = securityProfile.getOperatorMask();

        operator.setMask(operatorMask);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            operator.save(operatorIsNew);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);

            String unencryptedTemporaryPassword = operator.getUnencryptedTemporaryPassword();

            return unencryptedTemporaryPassword;
        }
        catch (EDITSecurityException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }
   /**
    * sramamurthy 08/19/2004 the password reset funtionality for existing users
    * @param operator
    * @return
    * @throws EDITSecurityException
    */
    public String passworddReset(Operator operator) throws EDITSecurityException
    {
       try
       {
           SessionHelper.beginTransaction(SessionHelper.SECURITY);

           operator.passwordReset();

           SessionHelper.commitTransaction(SessionHelper.SECURITY);

           String unencryptedTemporaryPassword = operator.getUnencryptedTemporaryPassword();

           return unencryptedTemporaryPassword;
       }
       catch (EDITSecurityException e)
       {
           SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

           System.out.println(e);

           e.printStackTrace();  //To change body of catch statement use Options | File Templates.

           throw e;
       }
       finally
       {
           SessionHelper.clearSessions();
       }
    }


    public void deleteOperator(Long operatorPK) throws EDITSecurityException
    {
        Operator operator = Operator.findByPK(operatorPK);

        SessionHelper.beginTransaction(SessionHelper.SECURITY);

        operator.hDelete();

        SessionHelper.commitTransaction(SessionHelper.SECURITY);

        SessionHelper.clearSessions();
    }

    public void attachOperatorToRole(Long operatorPK, Long rolePK) throws EDITSecurityException
    {
        Operator operator = Operator.findByPK(operatorPK);

        Role role = Role.findByPK(rolePK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            role.associate(operator);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (EDITSecurityException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public void detachOperatorFromRole(Long operatorPK, Long rolePK) throws EDITSecurityException
    {
        Operator operator = Operator.findByPK(operatorPK);

        Role role = Role.findByPK(rolePK);

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            role.disassociate(operator);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (EDITSecurityException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    public SecurityLog[] getSecurityLogs()
    {
        SecurityLog[] securityLogs = SecurityLog.findAll();

        return securityLogs;
    }

    public boolean authorize(
                    String sessionId,
                    String componentClassName,
                    String methodName,
                    ProductStructure productStructure)
    {
        ComponentMethod componentMethod =
                ComponentMethod.findByComponentClassNameAndMethodName(
                        componentClassName, methodName);

        if (componentMethod == null)
        {
            EDITSecurityAccessException e = new EDITSecurityAccessException(
                "Authorization Error - Methods Need To Be Mapped To Roles In Security");

            e.setErrorType(EDITSecurityAccessException.AUTHORIZATION_ERROR);

            throw e;
        }

        if (productStructure == null)
        {
            EDITSecurityAccessException e = new EDITSecurityAccessException(
                "Authorization Error - Current product structure not set.  User has no product structure authorization.");

            e.setErrorType(EDITSecurityAccessException.COMPANY_STRUCTURE_NOT_SET_EXCEPTION);

            throw e;
        }

        return componentMethod.isAuthorized(sessionId);
    }
    
    public boolean securityIsInitialized()
    {
        return new SecurityProcessor().securityIsInitialized();
    }

    public void loadInitialSecurity() throws EDITSecurityException
    {
        SessionHelper.beginTransaction(SessionHelper.SECURITY);
    
        new SecurityProcessor().loadInitialSecurity();
        
        SessionHelper.commitTransaction(SessionHelper.SECURITY);

        System.out.println("! SECURITY DATABASE INITIALIZED AT " + (new java.util.Date()));
    }

    public String[] getRoleNames(String sessionId)
    {
        return SecuritySession.getSecuritySession(sessionId).getRoleNames();
    }

    /**
     * Return true if there are product-type product structures and some
     * of the FilteredRoles point to non-existent product structures
     * @return
     */
    public boolean isSecurityDbMismatch()
    {
                
        ProductStructure[] productStructures = ProductStructure.findAllProductType();

        if (productStructures == null || productStructures.length == 0)
        {
            return false;  // may be an initialized db but no comp structure mismatch
        }

        // here we want all of them - not just ones joined to product structures
        FilteredRole[] allFilteredRoles = FilteredRole.findAll();

        if (allFilteredRoles == null || allFilteredRoles.length == 0)
        {
            return false;   // not be converted yet
        }

        FilteredRole[] joinedFilteredRoles = FilteredRole.findAllWithProductStructures();
        if (joinedFilteredRoles == null || joinedFilteredRoles.length == 0)
        {
            return true;  // none match up
        }

        if (joinedFilteredRoles.length != allFilteredRoles.length)
        {
            return true; // partial mismatch
        }

        return false;
    }


    public void handleDbMismatch() throws EDITSecurityException
    {
        new SecurityProcessor().handleDbMismatch();

        System.out.println(
                "\n\n! Security DB has some FilteredRoles not matching ProductStructures.");
        System.out.println(
                "admin (operator and role) is OK to use for all product structures\n\n");
    }

    /**
     * Purpose of this is to check that the FilteredRole table has
     * been defined in the database.  If not, we can put out a
     * more meaningful error message.
     * @return
     */
    public boolean isFilteredRoleDefinedInDatabase()
    {
        try
        {
            DBTable test = DBTable.getDBTableForTable("FilteredRole");
        }
        catch(NullPointerException npe)
        {
            return false;
        }
        return true;
    }

    /**
     * @see security.business.Security#isSecurityEnabled()
     * @return
     */
    public boolean isSecurityEnabled()
    {
        return SecurityProcessor.isSecurityIsEnabled();
    }

    /**
     * @see security.business.Security#getAllProductStructures()
     * @return
     */
    public ProductStructureVO[] getAllProductStructures()
    {
        ProductStructure[] productStructures = ProductStructure.findAll();

        return ProductStructure.mapEntityToVO(productStructures);
    }
    
    /**
     * @see security.business.Security#attachRoleToProductStructures
     * @param rolePK
     * @param productStructurePKs
     * @param cloneFromPKStr
     * @throws EDITSecurityException
     */
    public void attachRoleToProductStructures(Long rolePK, long[] productStructurePKs, String cloneFromPKStr) throws EDITSecurityException
    {
        if (productStructurePKs != null)
        {
            try
            {
                SessionHelper.beginTransaction(SessionHelper.SECURITY);

                for (int i = 0; i < productStructurePKs.length; i++)
                {
                    long productStructurePK = productStructurePKs[i];

                    insertFilteredRoleIfNotThere(new Long(rolePK), new Long(productStructurePK));

                    if (cloneFromPKStr != null)
                    {
                        // for each role, if clone-from selected, insert a copy
                        // of the Secured Methods
                        long cloneFromProductStructurePK = Long.parseLong(cloneFromPKStr);

                        insertSecuredMethods(new Long(cloneFromProductStructurePK), new Long(rolePK), new Long(productStructurePK));
                    }
                }

                SessionHelper.commitTransaction(SessionHelper.SECURITY);
            }
            catch (Exception e)
            {
                SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
            finally
            {
                SessionHelper.clearSessions();
            }
        }
    }
    
    /**
     * If the FilteredRole is not there, insert it.
     * @param selectedRolePK
     * @param selectedProductStructurePK
     */
    private void insertFilteredRoleIfNotThere(Long selectedRolePK, Long selectedProductStructurePK) throws EDITSecurityException
    {

        FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(selectedRolePK,selectedProductStructurePK);

        if (filteredRole == null)
        {
            Role role = Role.findByPK(selectedRolePK);

            try
            {
                SessionHelper.beginTransaction(SessionHelper.SECURITY);

                filteredRole = new FilteredRole();
                filteredRole.setProductStructureFK(selectedProductStructurePK);
                filteredRole.setRoleFK(selectedRolePK);
                filteredRole.setRole(role);
                filteredRole.hSave();

                SessionHelper.commitTransaction(SessionHelper.SECURITY);
            }
            catch (Exception e)
            {
                SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
            finally
            {
                SessionHelper.clearSessions();
            }
        }
    }
    
    private void insertSecuredMethods(Long cloneFromProductStructurePK,
                                      Long selectedRolePK,
                                      Long selectedProductStructurePK)
            throws EDITSecurityException
    {

        FilteredRole filteredRoleCloneTo =
                getFilteredRole(selectedRolePK, selectedProductStructurePK);

        FilteredRole filteredRoleCloneFrom =
                getFilteredRole(selectedRolePK, cloneFromProductStructurePK);

        // copy all of the SecuredMethods from filteredRoleCloneFrom to
        // filteredRoleForMethods

        try
        {
            SessionHelper.beginTransaction(SessionHelper.SECURITY);

            SecuredMethod.cloneAllSecuredMethods(filteredRoleCloneFrom, filteredRoleCloneTo);

            SessionHelper.commitTransaction(SessionHelper.SECURITY);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }
    
    private FilteredRole getFilteredRole(Long roleFK, Long productStructureFK)
    {
        FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(roleFK, productStructureFK);

        if (filteredRole == null)
        {
            throw new IllegalStateException("SecurityAdminTran - " +
                    "FilteredRole not found for " +
                    "RoleFK=" + roleFK + " and ProductStructureFK=" +
                    productStructureFK);
        }

        return filteredRole;
    }
    
    /**
     * @see security.business.Security#detachRoleFromPoductStructures
     * @param rolePK
     * @param productStructurePKs
     * @throws EDITSecurityException
     */
    public void detachRoleFromPoductStructures(Long rolePK, long[] productStructurePKs) throws EDITSecurityException
    {
        if (productStructurePKs != null)
        {
            try
            {
                SessionHelper.beginTransaction(SessionHelper.SECURITY);

                for (int i = 0; i < productStructurePKs.length; i++)
                {
                    long selectedProductStructurePK = productStructurePKs[i];
                    FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(new Long(rolePK), new Long(selectedProductStructurePK));

                    if (filteredRole != null)
                    {
                        // there is at most one
                        // this deletes the SecuredMethods as well as the FilteredRole
                        filteredRole.hDelete();
                    }
                }

                SessionHelper.commitTransaction(SessionHelper.SECURITY);
            }
            catch (Exception e)
            {
                SessionHelper.rollbackTransaction(SessionHelper.SECURITY);

                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
            finally
            {
                SessionHelper.clearSessions();
            }
        }
    }
    
    /**
     * 
     * @param sessionId
     * @param componentClassName
     * @param methodName
     * @return
     */
    public boolean authorize(String sessionId, String componentClassName, String methodName)
    {
        boolean isAuthorized = false;
    
        ComponentMethod componentMethod =
                ComponentMethod.findByComponentClassNameAndMethodName(
                        componentClassName, methodName);
                        
        if (componentMethod == null)
        {
            EDITSecurityAccessException e = new EDITSecurityAccessException(
                "Authorization Error - Methods Need To Be Mapped To Roles In Security");

            e.setErrorType(EDITSecurityAccessException.AUTHORIZATION_ERROR);

            throw e;
        }
        
        SecuritySession securitySession = SecuritySession.getSecuritySession(sessionId);
        
        FilteredRole[] filteredRolesFromSession =  securitySession.getFilteredRoles();
        
        for (FilteredRole filteredRole : filteredRolesFromSession)
        {
            isAuthorized = componentMethod.isAuthorized(filteredRole);
            
            // if atleast one role has access to component method.
            if (isAuthorized == true)
            {
                break;
            }
        }
        
        return isAuthorized;
    }
}
