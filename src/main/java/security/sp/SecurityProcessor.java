/*
 * User: gfrosti
 * Date: Dec 31, 2002
 * Time: 4:01:20 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security.sp;

import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.component.*;
import edit.services.config.*;
import edit.services.db.hibernate.*;
import engine.*;
import security.*;
import security.jaas.*;

import javax.security.auth.*;
import javax.security.auth.login.*;
import java.util.*;


public class SecurityProcessor
{
    private static boolean securityIsEnabled = false;

    /**
     * Getter.
     * @return
     */
    public static boolean isSecurityIsEnabled()
    {
        return securityIsEnabled;
    }

    /**
     * Setter.
     * @param securityIsEnabled
     */
    public static void setSecurityIsEnabled(boolean securityIsEnabled)
    {
        SecurityProcessor.securityIsEnabled = securityIsEnabled;
    }

    public String login(String username, String password)
    {
        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();

        JAASConfig[] jaasConfigs = editServicesConfig.getJAASConfig();

        for (int i = 0; i < jaasConfigs.length; i++)
        {
            System.setProperty(jaasConfigs[i].getProperty(), jaasConfigs[i].getFile());
        }

        String sessionId = null;

        SEGCallbackHandler callbackHandler = new SEGCallbackHandler(username, password);

        try
        {
            LoginContext lc = new LoginContext("EDITSolutionsJAAS", callbackHandler);
            
            lc.login();

            Subject subject = lc.getSubject();

            sessionId = getSessionId(subject);
            
            updateOperator(username);

            updateSecurityLog(username, "Operator Logged-In", "INFORMATION");
        }
        catch (LoginException e1)
        {
            EDITSecurityAccessException e2 = null;

            if (e1 instanceof EDITLoginException)
            {
                e2 = ((EDITLoginException) e1).getEDITSecurityAccessException();
            }
            else
            {
                e2 = new EDITSecurityAccessException("Invalid Username / Password");

                e2.setErrorType(EDITSecurityAccessException.LOGIN_ERROR);
            }

            System.out.println(e2);

            e2.printStackTrace();

            updateSecurityLog(username, e2.getMessage(), "ERROR");

            throw e2;
        }

        return sessionId;
    }

    private String getSessionId(Subject subject)
    {
        String sessionId = null;

        List principals = new ArrayList(subject.getPrincipals());

        for (int i = 0; i < principals.size(); i++)
        {
            SEGPrincipal segPrincipal = (SEGPrincipal) principals.get(i);

            int principalType = segPrincipal.getType();

            String principalName = segPrincipal.getName();

            if (principalType == SEGPrincipal.TYPE_SESSION_ID) // == 1
            {
                sessionId = principalName;

                break;
            }
        }

        return sessionId;
    }

    public void logout(String sessionId)
    {
        try
        {
            SecuritySession securitySession = SecuritySession.getSecuritySession(sessionId);

            LoginContext lc = new LoginContext("EDITSolutionsJAAS", securitySession.getSubject());

            lc.logout();

            Operator operator = Operator.findByPK(securitySession.getOperatorPK());
            
            operator.setLoggedInIndicator("N");
            
            operator.hSave();

            updateSecurityLog(operator.getName(), "Operator Logged-Out", "INFORMATION");
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    public void saveSecurityProfile(SecurityProfile securityProfile, Mask pMask, Mask operatorMask, PasswordMask passwordMask) throws EDITSecurityException
    {
        try
        {
            passwordMask.setMask(pMask);
        
            pMask.setSecurityProfile(securityProfile);
            operatorMask.setSecurityProfile(securityProfile);
        
            securityProfile.hSave();
            operatorMask.hSave();
            pMask.hSave();
            passwordMask.hSave();

//            securityProfile.associate(operatorMask);
//            securityProfile.associate(passwordMask);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    public SecurityProfile getSecurityProfile()
    {
        SecurityProfile securityProfile = SecurityProfile.getSingleton();

        return securityProfile;
    }
    
    private void updateOperator(String username)
    {
        Operator operator = Operator.findByOperatorName(username);
        
        operator.setLoggedInIndicator("Y");
        
        operator.hSave();
    }

    private void updateSecurityLog(String operatorName, String message, String type)
    {
        if ((operatorName != null) && (operatorName.length() != 0))
        {
            SecurityLog securityLog = (SecurityLog) SessionHelper.newInstance(SecurityLog.class, SessionHelper.SECURITY);
            securityLog.setMaintDateTime(new EDITDateTime());
            securityLog.setMessage(message);
            securityLog.setOperatorName(operatorName);
            securityLog.setType(type);
            
            securityLog.hSave();
        }
    }

    public boolean securityIsInitialized()
    {
        return (Operator.findByOperatorName("admin") != null);
    }

    public void loadInitialSecurity() throws EDITSecurityException
    {
        // Setup SecurityProfile and Masks
        SecurityProfile securityProfile = new SecurityProfile();

        PasswordMask passwordMask = new PasswordMask();
        Mask pMask = passwordMask.getMask();
        pMask.setSecurityProfile(securityProfile);
        passwordMask.hSave();

        Mask operatorMask = new Mask(Mask.OPERATOR_MASKTYPE);
        operatorMask.setSecurityProfile(securityProfile);
        operatorMask.hSave();

//        securityProfile.associate(operatorMask);        

//        securityProfile.associate(passwordMask);

        securityProfile.hSave();
        

        // Map the Operator to Role
        Operator adminOp = new Operator();
        adminOp.setName("admin");
        adminOp.hSave();

        Role adminRole = new Role();
        adminRole.setRoleName("admin");
        adminRole.hSave();

        adminRole.associate(adminOp);

        ProductStructure[] productStructures = ProductStructure.findAllProductType();

        if ((productStructures == null) || (productStructures.length == 0))
        {
            ProductStructure.createDefaultForProduct();
        }

        allowAllSecurityForRole(adminRole);
    }

    /**
     * Create all of the security for a role.  All methods and product structures.
     * @param aRole
     * @throws EDITSecurityException
     */
    private void allowAllSecurityForRole(Role aRole) throws EDITSecurityException
    {
        // If the role has filteredRoles attached with product structures, skip this
        // This next find also makes sure there are product structures attached
        FilteredRole[] filteredRoles = FilteredRole.findByRole(aRole.getRolePK());

        if ((filteredRoles != null) && (filteredRoles.length > 0))
        {
            return;
        }

        FilteredRole[] filteredRolesForAdminRole = createAllFilteredRolesForRole(aRole);

        // Map SecurityComponent methods to the admin role.
        String[] componentCategories = Component.COMPONENT_CATEGORIES;

        for (int i = 0; i < componentCategories.length; i++)
        {
            ComponentMethod.synchronizeComponentMethodsWithDB(componentCategories[i]);

            ComponentMethod[] componentMethods = ComponentMethod.findByComponentNameCT(componentCategories[i]);

            for (int j = 0; j < filteredRolesForAdminRole.length; j++)
            {
                for (int k = 0; k < componentMethods.length; k++)
                {
                    FilteredRole filteredRole = filteredRolesForAdminRole[j];
                    filteredRole.secureComponentMethod(componentMethods[k], true);
                }
            }
        }
    }

    /**
     * If the security database is out of sync, make sure we have
     * an admin operator, admin role with security for everything.
     * @throws EDITSecurityException
     */
    public void handleDbMismatch() throws EDITSecurityException
    {
        // is there an admin operator?
        Operator adminOp = Operator.findByOperatorName("admin");

        if (adminOp == null)
        {
            adminOp = new Operator();
            adminOp.setName("admin");
            adminOp.hSave();
        }
        else
        {
            adminOp.updatePassword("admin");
        }

        // is there an admin role?
        Role adminRole = null;
        Role[] adminRoles = Role.findByRoleName("admin");

        if ((adminRoles == null) || (adminRoles.length == 0))
        {
            adminRole = new Role();
            adminRole.setRoleName("admin");
            adminRole.hSave();

            adminRole.associate(adminOp);
        }
        else
        {
            adminRole = adminRoles[0];
        }

        // attach admin role to all product product structures
        // if the role does not have filteredRoles with product structures
        allowAllSecurityForRole(adminRole);
    }

    /**
     * Not of general use so not placed elsewhere.
     * @param role
     * @return
     * @throws EDITSecurityException
     */
    private FilteredRole[] createAllFilteredRolesForRole(Role role) throws EDITSecurityException
    {
        List newFilteredRoles = new ArrayList();

        ProductStructure[] productStructures = ProductStructure.findAllProductType();

        for (ProductStructure productStructure : productStructures)
        {
            FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(role.getRolePK(), productStructure.getProductStructurePK());

            if ((filteredRole == null))
            {
                filteredRole = new FilteredRole();
                filteredRole.setProductStructureFK(productStructure.getProductStructurePK());
                filteredRole.setRoleFK(role.getRolePK());
                filteredRole.hSave();
            }

            newFilteredRoles.add(filteredRole);
        }

        return (FilteredRole[]) newFilteredRoles.toArray(new FilteredRole[newFilteredRoles.size()]);

        //empty template ok
    }
}
