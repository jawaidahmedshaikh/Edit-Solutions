package security.ui.servlet;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITSecurityException;
import edit.common.exceptions.EDITSecurityAccessException;
import edit.common.vo.*;
import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;

import edit.services.db.hibernate.SessionHelper;

import fission.global.AppReqBlock;
import fission.utility.Util;
import security.Mask;
import security.FilteredRole;
import security.SecuredMethod;
import security.Operator;
import security.business.Security;
import security.component.SecurityComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import engine.*;

import security.Password;
import security.PasswordMask;
import security.Role;
import security.SecurityLog;
import security.SecurityProfile;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 12, 2003
 * Time: 1:16:36 PM
 * To change this template use Options | File Templates.
 */
public final class SecurityAdminTran extends Transaction
{
    // Pages
    private static final String PROFILE_JSP = "/security/jsp/profile.jsp";
    private static final String PORTAL_JSP = "/common/jsp/portal.jsp";
    private static final String EXPIRED_PASSWORD_JSP = "/security/jsp/expiredPasswordError.jsp";
    private static final String ROLES_JSP = "/security/jsp/roles.jsp";
    private static final String OPERATORS_JSP = "/security/jsp/users.jsp";
    private static final String USERS_IN_ROLE_DIALOG_JSP = "/security/jsp/usersInRoleDialog.jsp";
    private static final String MAPPED_ROLES_DIALOG_JSP = "/security/jsp/mappedRolesDialog.jsp";
    private static final String LOGIN_JSP = "/security/jsp/login.jsp";
    private static final String SECURITY_LOG_JSP = "/security/jsp/securityLog.jsp";
    private static final String FORWARD_TRANSACTION_ACTION_JSP = "/security/jsp/forwardTransactionAction.jsp";
    private static final String NEW_PASSWORD_DIALOG_JSP = "/security/jsp/newPasswordDialog.jsp";
    private static final String COMPANY_STRUCTURE_NOT_SET_ERROR_PAGE = "/security/jsp/companyStructureNotSetError.jsp";
    private static final String PORTAL                   = "/common/jsp/portal.jsp";

    // Actions
    private static final String UPDATE_EXPIRED_PASSWORD = "updateExpiredPassword";
    private static final String SAVE_PROFILE = "saveProfile";

    private static final String SHOW_ROLES_PAGE = "showRolesPage";
    private static final String SAVE_ROLE = "saveRole";
    private static final String ADD_ROLE = "addRole";
    private static final String CANCEL_ROLE = "cancelRole";
    private static final String SHOW_IMPLIED_ROLES = "showImpliedRoles";
    private static final String SHOW_SECURED_METHODS = "showSecuredMethods";
    private static final String DELETE_ROLE = "deleteRole";
    private static final String ATTACH_IMPLIED_ROLES = "attachImpliedRoles";
    private static final String DETACH_IMPLIED_ROLES = "detachImpliedRoles";
    private static final String SAVE_SECURED_METHODS = "saveSecuredMethods";

    private static final String SHOW_OPERATORS_PAGE = "showOperatorsPage";
    private static final String SHOW_OPERATORS_IN_ROLE = "showOperatorsInRole";
    private static final String SHOW_MAPPED_ROLES = "showMappedRoles";
    private static final String ADD_OPERATOR = "addOperator";
    private static final String DELETE_OPERATOR = "deleteOperator";
    private static final String CANCEL_OPERATOR = "cancelOperator";
    private static final String SHOW_OPERATORS_ROLES = "showOperatorsRoles";
    private static final String SAVE_OPERATOR = "saveOperator";
    private static final String ATTACH_ROLES_TO_OPERATOR = "attachRolesToOperator";
    private static final String DETACH_ROLES_FROM_OPERATOR = "detachRolesFromOperator";
    private static final String SHOW_NEW_PASSWORD_DIALOG = "showNewPasswordDialog";
    private static final String SHOW_PROFILE_PAGE = "showProfilePage";
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
    private static final String SHOW_SECURITY_LOG_PAGE = "showSecurityLogPage";
    private static final String RESET_PASSWORD = "resetPassword";
    private static final String DETACH_PRODUCT_STRUCTURES = "detachProductStructures";
    private static final String ATTACH_PRODUCT_STRUCTURES = "attachProductStructures";
    private static final String SET_PRODUCT_STRUCTURE = "setProductStructure";
    private static final String SHOW_LOGIN_PAGE = "showLoginPage";

    public String execute(AppReqBlock appReqBlock) throws Throwable
    {
        String action = appReqBlock.getHttpServletRequest().getParameter("action");

        String nextPage;

        try
        {
            if (action.equals(DELETE_OPERATOR))
            {
                nextPage = deleteOperator(appReqBlock);
            }
            else if (action.equals(UPDATE_EXPIRED_PASSWORD))
            {
                nextPage = updateExpiredPassword(appReqBlock);
            }
            else if (action.equals(SHOW_PROFILE_PAGE))
            {
                nextPage = showProfilePage(appReqBlock);
            }
            else if (action.equals(SAVE_PROFILE))
            {
                nextPage = saveProfile(appReqBlock);
            }
            else if (action.equals(SHOW_ROLES_PAGE))
            {
                nextPage = showRolesPage(appReqBlock);
            }
            else if (action.equals(SAVE_ROLE))
            {
                nextPage = saveRole(appReqBlock);
            }
            else if (action.equals(ADD_ROLE))
            {
                nextPage = addRole(appReqBlock);
            }
            else if (action.equals(CANCEL_ROLE))
            {
                nextPage = cancelRole(appReqBlock);
            }
            else if (action.equals(SHOW_IMPLIED_ROLES))
            {
                nextPage = showImpliedRoles(appReqBlock);
            }
            else if (action.equals(SHOW_SECURED_METHODS))
            {
                nextPage = showSecuredMethods(appReqBlock);
            }
            else if (action.equals(DELETE_ROLE))
            {
                nextPage = deleteRole(appReqBlock);
            }
            else if (action.equals(ATTACH_IMPLIED_ROLES))
            {
                nextPage = attachImpliedRoles(appReqBlock);
            }
            else if (action.equals(DETACH_IMPLIED_ROLES))
            {
                nextPage = detachImpliedRoles(appReqBlock);
            }
            else if (action.equals(SAVE_SECURED_METHODS))
            {
                nextPage = saveSecuredMethods(appReqBlock);
            }
            else if (action.equals(SHOW_OPERATORS_IN_ROLE))
            {
                nextPage = showOperatorsInRole(appReqBlock);
            }
            else if (action.equals(ADD_OPERATOR))
            {
                nextPage = addOperator(appReqBlock);
            }
            else if (action.equals(CANCEL_OPERATOR))
            {
                nextPage = cancelOperator(appReqBlock);
            }
            else if (action.equals(SHOW_OPERATORS_PAGE))
            {
                nextPage = showOperatorsPage(appReqBlock);
            }
            else if (action.equals(SHOW_OPERATORS_ROLES))
            {
                nextPage = showOperatorsRoles(appReqBlock);
            }
            else if (action.equals(SAVE_OPERATOR))
            {
                nextPage = saveOperator(appReqBlock);
            }
            else if (action.equals(SHOW_SECURITY_LOG_PAGE))
            {
                nextPage = showSecurityLogPage(appReqBlock);
            }
            else if (action.equals(LOGIN))
            {
                nextPage = login(appReqBlock);
            }
            else if (action.equals(SET_PRODUCT_STRUCTURE))
            {
                 nextPage = setProductStructure(appReqBlock);
            }
            else if (action.equals(DETACH_ROLES_FROM_OPERATOR))
            {
                nextPage = detachRolesFromOperator(appReqBlock);
            }
            else if (action.equals(ATTACH_ROLES_TO_OPERATOR))
            {
                nextPage = attachRolesToOperator(appReqBlock);
            }
            else if (action.equals(SHOW_MAPPED_ROLES))
            {
                nextPage = showMappedRoles(appReqBlock);
            }
            else if (action.equals(LOGOUT))
            {
                nextPage = logout(appReqBlock);
            }
            else if (action.equals(SHOW_NEW_PASSWORD_DIALOG))
            {
                nextPage = showNewPasswordDialog(appReqBlock);
            }
            else if(action.equals(RESET_PASSWORD)){
                 nextPage = resetPassword(appReqBlock);
            }
            else if (action.equals(DETACH_PRODUCT_STRUCTURES))
            {
                nextPage = detachProductStructures(appReqBlock);
            }
            else if (action.equals(ATTACH_PRODUCT_STRUCTURES))
            {
                nextPage = attachProductStructures(appReqBlock);
            }
            else if (action.equals(SHOW_LOGIN_PAGE))
            {
                nextPage = "/security/jsp/login.jsp";
            }
            else
            {
                throw new Exception("Invalid Action/Transaction");
            }

            return nextPage;
        }
        catch (EDITSecurityAccessException e)
        {
            if (e.getErrorType() == EDITSecurityAccessException.SESSION_TIMEOUT_ERROR)
            {
                appReqBlock.setReqParm("targetTransaction", "PortalLoginTran");

                appReqBlock.setReqParm("targetAction", "showSecurityAdmin");

                appReqBlock.setReqParm("target", "_top");
            }

            throw new EDITSecurityAccessException(e.getMessage());
        }
    }

    private String showNewPasswordDialog(AppReqBlock appReqBlock) throws Throwable
    {
        String newPasswordMessage = appReqBlock.getReqParm("newPasswordMessage");

        appReqBlock.getHttpServletRequest().setAttribute("newPasswordMessage", newPasswordMessage);

        return NEW_PASSWORD_DIALOG_JSP;
    }

    private String showMappedRoles(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedRolePK = appReqBlock.getReqParm("selectedRolePK");

        Security securityComponent = new SecurityComponent();

        BIZRoleVO[] impliedRoleVOs = securityComponent.getImpliedRoles(Long.parseLong(selectedRolePK));
        
        // RoleVO roleVO = securityComponent.getRole(Long.parseLong(selectedRolePK), null);

        Role role = securityComponent.getRole(Long.parseLong(selectedRolePK), null);

        appReqBlock.getHttpServletRequest().setAttribute("impliedRoleVOs", impliedRoleVOs);

        appReqBlock.getHttpServletRequest().setAttribute("role", role);

        return MAPPED_ROLES_DIALOG_JSP;
    }

    private String login(AppReqBlock appReqBlock) throws Throwable
    {
        String username = appReqBlock.getReqParm("username");

        String password = appReqBlock.getReqParm("password");

        appReqBlock.getHttpServletRequest().setAttribute("username", username);

        appReqBlock.getHttpServletRequest().setAttribute("password", password);

        UserSession userSession = new UserSession(username, appReqBlock.getHttpSession());

        userSession.login(password);

        appReqBlock.getHttpSession().setAttribute("userSession", userSession);

        return PORTAL;
    }


    private String setProductStructure(AppReqBlock appReqBlock) throws Throwable
    {

        // Gets the named form variable from the request and sets the
        // current company in the UserSession.  If it is null or 0,
        // it leaves the current company structure setting alone.
        ProductStructure.setSecurityCurrentProdStructInSession(
                appReqBlock,
                "productStructurePK");


        return PORTAL;
    }

    public String logout(AppReqBlock appReqBlock) throws Throwable
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.logout();

        return LOGIN_JSP;
    }

    /**
     * Attach the selected ProductStructure's to the role if they are not
     * already attached.  Optionally will also clone the SecuredMethods (use case
     * settings) from a clone-from ProductStructure and the selected Role.
     * @param appReqBlock
     * @return
     * @throws Throwable
     */
    private String attachProductStructures(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedProductStructurePKsStr =
            Util.initString(appReqBlock.getReqParm("selectedProductStructurePKs"), null);

        String message = null;
        if (selectedProductStructurePKsStr == null)
        {
            message = "At least one product structure must be selected";
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            return showRolesPage(appReqBlock);
        }

        String selectedRolePKStr =
                Util.initString(appReqBlock.getReqParm("selectedRolePK"), null);

        long selectedRolePK = Long.parseLong(selectedRolePKStr);

        // these are the product structures to attach
        long[] selectedProductStructurePKs =
                getPKsFromCommaDelimitedString(selectedProductStructurePKsStr);

        // if this is filled in, we need to clone the SecuredMethods from this
        // productstructure
        String selectedCloneFromPKStr =
                Util.initString(
                        appReqBlock.getReqParm("cloneFromProductStructureFK"), null);

        // first store a FilteredRole for each productstructure + role
        // if not there

        String cloneDetailMessage = "";
        
        Security securityComponent = new SecurityComponent();
        
        securityComponent.attachRoleToProductStructures(new Long(selectedRolePK), selectedProductStructurePKs, selectedCloneFromPKStr);
            
        message = "Product Structures were successfully attached";

        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        appReqBlock.setReqParm("pageMode", "SELECT");

        return showRolesPage(appReqBlock);
    }

    private String detachProductStructures(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedProductStructurePKsStr =
                Util.initString(appReqBlock.getReqParm("selectedProductStructurePKs"), null);

        String message = null;
        if (selectedProductStructurePKsStr == null)
        {
            message = "At least one product structure must be selected";
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            return showRolesPage(appReqBlock);
        }

        String selectedRolePKStr =
                Util.initString(appReqBlock.getReqParm("selectedRolePK"), null);

        long selectedRolePK = Long.parseLong(selectedRolePKStr);

        long[] selectedProductStructurePKs =
                getPKsFromCommaDelimitedString(selectedProductStructurePKsStr);
                
        Security securityComponent = new SecurityComponent();
        
        securityComponent.detachRoleFromPoductStructures(new Long(selectedRolePK), selectedProductStructurePKs);

        message = "Product Structures were successfully detached";

        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        appReqBlock.setReqParm("selectedSecuredComponentCT", null);

        appReqBlock.setReqParm("pageMode", "SELECT");

        return showRolesPage(appReqBlock);
    }

    private String detachRolesFromOperator(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedOperatorPK = appReqBlock.getReqParm("selectedOperatorPK");

        String selectedRolePKsStr = appReqBlock.getReqParm("selectedRolePKs");

        long[] selectedRolePKs = getPKsFromCommaDelimitedString(selectedRolePKsStr);

        Security securityComponent = new SecurityComponent();

        String message = null;

        try
        {
            for (int i = 0; i < selectedRolePKs.length; i++)
            {
                securityComponent.detachOperatorFromRole(new Long(selectedOperatorPK), new Long(selectedRolePKs[i]));
            }

            appReqBlock.setReqParm("selectedOperatorPK", null);

            appReqBlock.setReqParm("selectedRolePK", null);

            appReqBlock.setReqParm("pageMode", "BROWSE");

            message = "Operator Successfully Detached From Role(s)";
        }
        catch (EDITSecurityException e)
        {
            message = e.getMessage();

            appReqBlock.setReqParm("pageMode", "SELECT");

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showOperatorsPage(appReqBlock);
    }

    private String attachRolesToOperator(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedOperatorPK = appReqBlock.getReqParm("selectedOperatorPK");

        String selectedRolePKsStr = appReqBlock.getReqParm("selectedRolePKs");

        long[] selectedRolePKs = getPKsFromCommaDelimitedString(selectedRolePKsStr);

        Security securityComponent = new SecurityComponent();

        for (int i = 0; i < selectedRolePKs.length; i++)
        {
            securityComponent.attachOperatorToRole(new Long(selectedOperatorPK), new Long(selectedRolePKs[i]));
        }

        appReqBlock.setReqParm("selectedOperatorPK", null);

        appReqBlock.setReqParm("selectedRolePK", null);

        appReqBlock.setReqParm("pageMode", "BROWSE");

        String message = "Operator Successfully Attached To Role(s)";

        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showOperatorsPage(appReqBlock);
    }

    private String deleteOperator(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedOperatorPK = appReqBlock.getReqParm("selectedOperatorPK");

        Security securityComponent = new SecurityComponent();

        try
        {
            securityComponent.deleteOperator(new Long(selectedOperatorPK));

            appReqBlock.setReqParm("selectedOperatorPK", null);

            appReqBlock.setReqParm("selectedRolePK", null);

            appReqBlock.setReqParm("pageMode", "BROWSE");

            String message = "Operator Successfully Deleted";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            appReqBlock.setReqParm("pageMode", "SELECT");
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        return showOperatorsPage(appReqBlock);
    }
    /**
     * sramamurthy 08/19/2004 the password reset funtionality for existing users
     * @param appReqBlock
     * @return
     * @throws Throwable
     */

    private String saveOperator(AppReqBlock appReqBlock) throws Throwable
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        String maintOperator = userSession.getUsername();
        String username = Util.initString(appReqBlock.getReqParm("name"), null);
        String firstName = Util.initString(appReqBlock.getReqParm("firstName"), null);
        String lastName = Util.initString(appReqBlock.getReqParm("lastName"), null);
        String middleInitial = Util.initString(appReqBlock.getReqParm("middleInitial"), null);
        String title = Util.initString(appReqBlock.getReqParm("title"), null);
        String dept = Util.initString(appReqBlock.getReqParm("dept"), null);
        String eMail = Util.initString(appReqBlock.getReqParm("eMail"), null);
        String lockedIndicator = appReqBlock.getReqParm("lockedIndicator");
        String loggedInIndicator = appReqBlock.getReqParm("loggedInIndicator");
        String terminationDate = Util.initString(appReqBlock.getReqParm("terminationDate"), null);
        String telephoneNumber = Util.initString(appReqBlock.getReqParm("telephoneNumber"), null);
        String telephoneExtension = Util.initString(appReqBlock.getReqParm("telephoneExtension"), null);
        String operatorTypeCT = Util.initString(appReqBlock.getReqParm("operatorTypeCT"), null);
        String applyMax = Util.initString(appReqBlock.getReqParm("applyMax"), "0");
        String removeMax = Util.initString(appReqBlock.getReqParm("removeMax"), "0");
        String transferMax = Util.initString(appReqBlock.getReqParm("transferMax"), "0");
        String operatorPK = Util.initString(appReqBlock.getReqParm("selectedOperatorPK"), "0");

        boolean operatorIsNew = false;

        Operator operator = null;

        if (Long.parseLong(operatorPK) == 0L)
        {
            operator = (Operator) SessionHelper.newInstance(Operator.class, SessionHelper.SECURITY);
            operatorIsNew = true;
        }
        else
        {
            operator = Operator.findByPK(new Long(operatorPK));
        }

        operator.setName(username);
        operator.setFirstName(firstName);
        operator.setLastName(lastName);
        operator.setMiddleInitial(middleInitial);
        operator.setTitle(title);
        operator.setDept(dept);
        operator.setMaintOperator(maintOperator);
        operator.setEMail(eMail);
        operator.setLockedIndicator(lockedIndicator);
        operator.setLoggedInIndicator(loggedInIndicator);
        if (terminationDate != null)
        {
            operator.setTerminationDate(new EDITDate(terminationDate));
        }
        else
        {
            operator.setTerminationDate(null);
        }
        operator.setTelephoneNumber(telephoneNumber);
        operator.setTelephoneExtension(telephoneExtension);
        operator.setOperatorTypeCT(operatorTypeCT);
        operator.setApplyMax(new EDITBigDecimal(applyMax));
        operator.setRemoveMax(new EDITBigDecimal(removeMax));
        operator.setTransferMax(new EDITBigDecimal(transferMax));

        Security securityComponent = new SecurityComponent();

        try
        {
            String temporaryPassword = securityComponent.saveOperator(operator, operatorIsNew);

            appReqBlock.setReqParm("selectedOperatorPK", null);

            appReqBlock.setReqParm("selectedRolePK", null);

            appReqBlock.setReqParm("pageMode", "BROWSE");

            if (temporaryPassword != null)
            {
                String newPasswordMessage = "Operator " + username + " Successfully Saved/Updated With Temporary Password [" + temporaryPassword + "]";

                appReqBlock.getHttpServletRequest().setAttribute("newPasswordMessage", newPasswordMessage);
            }
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            appReqBlock.setReqParm("pageMode", "ADD");

            appReqBlock.getHttpServletRequest().setAttribute("selectedOperator", operator);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }

        return showOperatorsPage(appReqBlock);
    }

    private String resetPassword(AppReqBlock appReqBlock) throws Throwable
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        String maintOperator = userSession.getUsername();
        String username = Util.initString(appReqBlock.getReqParm("name"), null);
        String firstName = Util.initString(appReqBlock.getReqParm("firstName"), null);
        String lastName = Util.initString(appReqBlock.getReqParm("lastName"), null);
        String middleInitial = Util.initString(appReqBlock.getReqParm("middleInitial"), null);
        String title = Util.initString(appReqBlock.getReqParm("title"), null);
        String dept = Util.initString(appReqBlock.getReqParm("dept"), null);
        String eMail = Util.initString(appReqBlock.getReqParm("eMail"), null);
        String operatorPK = Util.initString(appReqBlock.getReqParm("selectedOperatorPK"), "0");

//        OperatorVO operatorVO = new OperatorVO();
//
//        operatorVO.setMaintOperator(maintOperator);
//        operatorVO.setName(username);
//        operatorVO.setFirstName(firstName);
//        operatorVO.setLastName(lastName);
//        operatorVO.setMiddleInitial(middleInitial);
//        operatorVO.setTitle(title);
//        operatorVO.setDept(dept);
//        operatorVO.setEMail(eMail);
//        operatorVO.setOperatorPK(Long.parseLong(operatorPK));

        Operator operator = Operator.findByPK(new Long(operatorPK));
        operator.setMaintOperator(maintOperator);
        operator.setName(username);
        operator.setFirstName(firstName);
        operator.setLastName(lastName);
        operator.setMiddleInitial(middleInitial);
        operator.setTitle(title);
        operator.setDept(dept);
        operator.setEMail(eMail);

        Security securityComponent = new SecurityComponent();

        try
        {
            String temporaryPassword = securityComponent.passworddReset(operator);

            appReqBlock.setReqParm("selectedOperatorPK", null);

            appReqBlock.setReqParm("selectedRolePK", null);

            appReqBlock.setReqParm("pageMode", "BROWSE");

            String newPasswordMessage = "Operator "+username+" Successfully Saved/Updated With Temporary Password [" + temporaryPassword + "]";

            appReqBlock.getHttpServletRequest().setAttribute("newPasswordMessage", newPasswordMessage);
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            appReqBlock.setReqParm("pageMode", "ADD");

            appReqBlock.getHttpServletRequest().setAttribute("selectedOperator", operator);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }

        return showOperatorsPage(appReqBlock);
    }

    private String cancelOperator(AppReqBlock appReqBlock) throws Throwable
    {
        appReqBlock.setReqParm("selectedOperatorPK", null);

        appReqBlock.setReqParm("selectedRolePK", null);

        appReqBlock.setReqParm("pageMode", "BROWSE");

        return showOperatorsPage(appReqBlock);
    }

    private String addOperator(AppReqBlock appReqBlock) throws Throwable
    {
        appReqBlock.setReqParm("selectedOperatorPK", null);

        appReqBlock.setReqParm("selectedRolePK", null);

        appReqBlock.setReqParm("pageMode", "ADD");

        return showOperatorsPage(appReqBlock);
    }

    private String showOperatorsInRole(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedRolePK = appReqBlock.getReqParm("selectedRolePK");

        Security securityComponent = new SecurityComponent();

        Operator[] operatorsInRoles = securityComponent.getOperatorsInRole(Long.parseLong(selectedRolePK));

        Role role = securityComponent.getRole(Long.parseLong(selectedRolePK), null);

        appReqBlock.getHttpServletRequest().setAttribute("operatorsInRoles", operatorsInRoles);

        appReqBlock.getHttpServletRequest().setAttribute("role", role);

        return USERS_IN_ROLE_DIALOG_JSP;
    }

    private String showOperatorsRoles(AppReqBlock appReqBlock) throws Throwable
    {
        appReqBlock.setReqParm("pageMode", "SELECT");

        return showOperatorsPage(appReqBlock);
    }

    private String showOperatorsPage(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedOperatorPK = Util.initString(appReqBlock.getReqParm("selectedOperatorPK"), null);

        String pageMode = Util.initString(appReqBlock.getReqParm("pageMode"), null);

        if (pageMode == null) pageMode = "BROWSE";
        
        String includeTerminatedOperators = Util.initString(appReqBlock.getReqParm("includeTerminatedOperators"), "N");

        Security securityComponent = new SecurityComponent();

        if (selectedOperatorPK != null)
        {
            Operator operator = securityComponent.getOperator(new Long(selectedOperatorPK), null);

            BIZRoleVO[] operatorsRoleVOs = securityComponent.getOperatorsRoles(new Long(selectedOperatorPK));

            Password password = securityComponent.getCurrentPassword(Long.parseLong(selectedOperatorPK));

            appReqBlock.getHttpServletRequest().setAttribute("selectedOperator", operator);

            appReqBlock.getHttpServletRequest().setAttribute("selectedPassword", password);

            appReqBlock.getHttpServletRequest().setAttribute("operatorsRoleVOs", operatorsRoleVOs);
        }
        
        Operator[] operators = null;
        
        if (includeTerminatedOperators.equals("Y"))
        {
            operators = securityComponent.getAllOperators();
            
            appReqBlock.getHttpServletRequest().setAttribute("includeTerminatedOperators", "Y");
        }
        else
        {
            operators = securityComponent.getActiveOperators();
        }

        Role[] roles = securityComponent.getAllRoles();

        appReqBlock.getHttpServletRequest().setAttribute("selectedOperatorPK", selectedOperatorPK);

        appReqBlock.getHttpServletRequest().setAttribute("operators", operators);

        appReqBlock.getHttpServletRequest().setAttribute("roles", roles);

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", pageMode);

        return OPERATORS_JSP;
    }
    
    protected String showSecurityLogPage(AppReqBlock appReqBlock) throws Throwable
    {
        Security securityComponent = new SecurityComponent();

        SecurityLog[] securityLogs = securityComponent.getSecurityLogs();

        appReqBlock.getHttpServletRequest().setAttribute("securityLogs", securityLogs);

        return SecurityAdminTran.SECURITY_LOG_JSP;
    }

    private String saveSecuredMethods(AppReqBlock appReqBlock) throws Throwable
    {
        long selectedRolePK = Long.parseLong(appReqBlock.getReqParm("selectedRolePK"));

        String componentMethodPKsStr = appReqBlock.getReqParm("componentMethodPKs");

        long selectedProductStructurePK =
                Long.parseLong(appReqBlock.getReqParm("selectedProductStructurePK"));

        long[] componentMethodPKs = getPKsFromCommaDelimitedString(componentMethodPKsStr);

        String[] componentMethodYNs = Util.fastTokenizer(appReqBlock.getReqParm("componentMethodYNs"), ",");

        Security securityComponent = new SecurityComponent();

        try
        {
            securityComponent.secureComponentMethod(
                    new Long(selectedRolePK),
                    componentMethodPKs,
                    new Long(selectedProductStructurePK),
                    componentMethodYNs);

            appReqBlock.setReqParm("pageMode", "SELECT");
            //appReqBlock.setReqParm("pageMode", "BROWSE");

            //appReqBlock.setReqParm("selectedRolePK", null);

            //appReqBlock.setReqParm("selectedSecuredComponentCT", null);

            appReqBlock.getHttpServletRequest().setAttribute("message", "Secured Methods Successfully Updated");
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }

        //        String selectedSecuredComponentCT = appReqBlock.getReqParm("selectedSecuredComponentCT");
        //        appReqBlock.getHttpServletRequest().setAttribute("selectedRolePK", selectedRolePK + "");
        //        appReqBlock.getHttpServletRequest().setAttribute("selectedProductStructurePK", selectedProductStructurePK + "");
        //        appReqBlock.getHttpServletRequest().setAttribute("selectedSecuredComponentCT", selectedSecuredComponentCT);
        return showRolesPage(appReqBlock);
    }

    private String detachImpliedRoles(AppReqBlock appReqBlock) throws Throwable
    {
        long selectedRolePK = Long.parseLong(appReqBlock.getReqParm("selectedRolePK"));

        String impliedRolePKsStr = Util.initString(appReqBlock.getReqParm("impliedRolePKs"), null);

        long[] impliedRolePKs = getPKsFromCommaDelimitedString(impliedRolePKsStr);

        Security securityComponent = new SecurityComponent();

        try
        {
            for (int i = 0; i < impliedRolePKs.length; i++)
            {
                securityComponent.detachImpliedRole(new Long(selectedRolePK), new Long(impliedRolePKs[i]));
            }

            appReqBlock.setReqParm("pageMode", "BROWSE");

            appReqBlock.setReqParm("selectedRolePK", null);

            appReqBlock.setReqParm("selectedSecuredComponentCT", null);

            appReqBlock.getHttpServletRequest().setAttribute("message", "Implied Role(s) Successfully Detached");
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }

        return showRolesPage(appReqBlock);
    }

    private String attachImpliedRoles(AppReqBlock appReqBlock) throws Throwable
    {
        long selectedRolePK = Long.parseLong(appReqBlock.getReqParm("selectedRolePK"));

        String impliedRolePKsStr = Util.initString(appReqBlock.getReqParm("impliedRolePKs"), null);

        long[] impliedRolePKs = getPKsFromCommaDelimitedString(impliedRolePKsStr);

        Security securityComponent = new SecurityComponent();

        try
        {
            for (int i = 0; i < impliedRolePKs.length; i++)
            {
                securityComponent.attachImpliedRole(new Long(selectedRolePK), new Long(impliedRolePKs[i]));
            }

            appReqBlock.setReqParm("pageMode", "BROWSE");

            appReqBlock.setReqParm("selectedRolePK", null);

            appReqBlock.setReqParm("selectedSecuredComponentCT", null);

            appReqBlock.getHttpServletRequest().setAttribute("message", "Implied Role(s) Successfully Attached");
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }

        return showRolesPage(appReqBlock);
    }

    private String showSecuredMethods(AppReqBlock appReqBlock) throws Throwable
    {
        return showRolesPage(appReqBlock);
    }

    private String cancelRole(AppReqBlock appReqBlock) throws Throwable
    {
        appReqBlock.setReqParm("pageMode", "BROWSE");

        appReqBlock.setReqParm("selectedRolePK", null);

        appReqBlock.setReqParm("selectedSecuredComponentCT", null);

        return showRolesPage(appReqBlock);
    }

    private String addRole(AppReqBlock appReqBlock) throws Throwable
    {
        appReqBlock.setReqParm("pageMode", "ADD");

        appReqBlock.setReqParm("selectedRolePK", null);

        return showRolesPage(appReqBlock);
    }

    private String showImpliedRoles(AppReqBlock appReqBlock) throws Throwable
    {
        appReqBlock.setReqParm("pageMode", "SELECT");

        appReqBlock.setReqParm("selectedSecuredComponentCT", null);
        appReqBlock.setReqParm("selectedProductStructurePK", null);
        appReqBlock.setReqParm("selectedProductStructurePKs", null);

        return showRolesPage(appReqBlock);
    }

    private String deleteRole(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedRolePK = Util.initString(appReqBlock.getReqParm("selectedRolePK"), null);

        Security securityComponent = new SecurityComponent();

        try
        {
            securityComponent.deleteRole(Long.parseLong(selectedRolePK));

            appReqBlock.setReqParm("pageMode", "BROWSE");

            appReqBlock.setReqParm("selectedRolePK", null);

            appReqBlock.getHttpServletRequest().setAttribute("message", "Role Successfully Deleted");
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            appReqBlock.setReqParm("pageMode", "SELECT");

            appReqBlock.setReqParm("selectedRolePK", selectedRolePK);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        return showRolesPage(appReqBlock);
    }

    private String saveRole(AppReqBlock appReqBlock) throws Throwable
    {
        String roleName = Util.initString(appReqBlock.getReqParm("roleName"), null);

        String rolePK = Util.initString(appReqBlock.getReqParm("selectedRolePK"), null);

        Role role = null;

        boolean newRoleAlreadyExists = false;

        if (rolePK == null )
        {
            Role[] existingRoles = Role.findByRoleName(roleName);

            if (existingRoles != null)
            {
                appReqBlock.getHttpServletRequest().setAttribute("message", "Role Already Exists");
                newRoleAlreadyExists = true;
            }
            else
            {
                role = (Role) SessionHelper.newInstance(Role.class, SessionHelper.SECURITY);
            }
        }
        else
        {
            role = Role.findByPK(new Long(rolePK));
        }

        if (!newRoleAlreadyExists)
        {
            role.setName(roleName);

            SecurityComponent securityComponent = new SecurityComponent();

            try
            {
                securityComponent.saveRole(role);
            }
            catch (EDITSecurityException e)
            {
                String message = e.getMessage();

                appReqBlock.getHttpServletRequest().setAttribute("message", message);
            }
            catch (Throwable e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw e;
            }

            appReqBlock.setReqParm("pageMode", "BROWSE");

            appReqBlock.setReqParm("selectedRolePK", null);

            if (rolePK == null ) {
                appReqBlock.getHttpServletRequest().setAttribute("message", "Role Successfully Added");
            }
            else {
                appReqBlock.getHttpServletRequest().setAttribute("message", "Role Successfully Updated");
            }
        }
        else
        {
            appReqBlock.setReqParm("pageMode", "ADD");

            appReqBlock.setReqParm("selectedRolePK", null);
        }

        return showRolesPage(appReqBlock);
    }

    private String showRolesPage(AppReqBlock appReqBlock) throws Throwable
    {
        String selectedSecuredComponentCT =
                Util.initString(appReqBlock.getReqParm("selectedSecuredComponentCT"), null);

        String selectedRolePKStr =
                Util.initString(appReqBlock.getReqParm("selectedRolePK"), null);

        long selectedRolePK = 0;
        if (selectedRolePKStr != null)
        {
            selectedRolePK = Long.parseLong(selectedRolePKStr);
        }

        String selectedProductStructurePKStr =
                Util.initString(appReqBlock.getReqParm("selectedProductStructurePK"),null);

        String selectedProductStructuresPKStr =
                 Util.initString(appReqBlock.getReqParm("selectedProductStructurePKs"),null);

        long selectedProductStructurePK = 0;

        if (selectedProductStructurePKStr != null)
        {
            selectedProductStructurePK =
                    Long.parseLong(selectedProductStructurePKStr);
        }

        String productStructurePKsStr =
                Util.initString(appReqBlock.getReqParm("productStructurePKs"),null);

        long[] productStructurePKs =
                getPKsFromCommaDelimitedString(productStructurePKsStr);

        String pageMode = Util.initString(appReqBlock.getReqParm("pageMode"), null);

        Security securityComponent = new SecurityComponent();

        Role[] roles = securityComponent.getAllRoles();

        ProductStructureVO[] productStructureVOs =
                securityComponent.getProductStructuresForRoleAttachment();

        ProductStructureVO selectedProductStructureVO = null;

        String showUseCaseButtonsString = "false";
        // set this to "true" if there is a single selected ProductStructure
        // and it is attached to the Role
        if (selectedProductStructurePK != 0 && selectedRolePK != 0)
        {
            FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(new Long(selectedRolePK), new Long(selectedProductStructurePK));
            if (filteredRole != null)
            {
               showUseCaseButtonsString = "true";
            }
        }

        if (selectedProductStructurePK != 0 && productStructureVOs != null)
        {   // go find the VO for the selected ProductStructure
            for (int i = 0; i < productStructureVOs.length; i++)
            {
                ProductStructureVO productStructureVO = productStructureVOs[i];
                if (productStructureVO.getProductStructurePK() == selectedProductStructurePK)
                {
                    selectedProductStructureVO = productStructureVOs[i];
                    break;
                }
            }
        }

        // Get implied roles for the selected role
        if (selectedRolePK != 0)
        {
            Role role = securityComponent.getRole(selectedRolePK, null);

            BIZRoleVO[] impliedBIZRoleVOs = securityComponent.getImpliedRoles(selectedRolePK);

            appReqBlock.getHttpServletRequest().setAttribute("impliedBIZRoleVOs", impliedBIZRoleVOs);

            appReqBlock.getHttpServletRequest().setAttribute("selectedRole", role);

            appReqBlock.setReqParm("pageMode", "SELECT");
        }

        Set setOfProductStructurePKsAttachedToSelectedRole = new HashSet();
        // load this Set with the product structure PKs that are attached.
        if (selectedRolePK != 0)
        {
            ProductStructureVO[] productStructureVOsAttachedToRole =
                    securityComponent.
                    getAttachedProductStructuresForRole(selectedRolePK);

            if (productStructureVOsAttachedToRole != null)
            {
                for (int i = 0; i < productStructureVOsAttachedToRole.length; i++)
                {
                    ProductStructureVO productStructureVO = productStructureVOsAttachedToRole[i];
                    long pk = productStructureVO.getProductStructurePK();
                    setOfProductStructurePKsAttachedToSelectedRole.add(new Long(pk));
                }
            }
        }

        // Get secured methods for the selected role and selected product structure

        if (selectedSecuredComponentCT != null
                && selectedRolePK != 0
                && selectedProductStructurePK != 0)
        {
            BIZComponentMethodVO[] bizComponentMethodVOs =
                    securityComponent.getComponentMethods(
                            selectedRolePK,
                            selectedSecuredComponentCT,
                            selectedProductStructurePK);

            appReqBlock.getHttpServletRequest().setAttribute("bizComponentMethodVOs", bizComponentMethodVOs);
        }

        if (pageMode == null)
        {
            pageMode = "BROWSE";
        }

        appReqBlock.getHttpServletRequest().setAttribute("roles", roles);

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", pageMode);

        appReqBlock.getHttpServletRequest().setAttribute("selectedRolePK", selectedRolePKStr);

        appReqBlock.getHttpServletRequest().setAttribute("selectedSecuredComponentCT", selectedSecuredComponentCT);

        if (selectedProductStructurePK != 0)
        {
            appReqBlock.getHttpServletRequest().setAttribute("selectedProductStructurePK", selectedProductStructurePK+"");
        }

        appReqBlock.getHttpServletRequest().setAttribute("selectedProductStructureVO", selectedProductStructureVO);

        long[] selectedProductStructurePKs =
                getPKsFromCommaDelimitedString(selectedProductStructuresPKStr);

        appReqBlock.getHttpServletRequest().setAttribute("setOfProductStructurePKsAttachedToSelectedRole",
                   setOfProductStructurePKsAttachedToSelectedRole);

        appReqBlock.getHttpServletRequest().setAttribute("selectedProductStructurePKs", selectedProductStructurePKs);

        appReqBlock.getHttpServletRequest().setAttribute("showUseCaseButtons", showUseCaseButtonsString);

        return SecurityAdminTran.ROLES_JSP;
    }

    /**
     * Updates the operator's password with the new password.
     * @param appReqBlock
     * @return
     * @throws Throwable
     */
    private String updateExpiredPassword(AppReqBlock appReqBlock) throws Throwable
    {
        String nextPage;
        String username = null;
        String password = null;
        String newPassword = null;
        String targetTransaction = null;
        String targetAction = null;

        try
        {
            username = appReqBlock.getReqParm("username");
            password = appReqBlock.getReqParm("password");
            newPassword = appReqBlock.getReqParm("newPassword");

            Security securityComp = new SecurityComponent();

            securityComp.updatePassword(username, newPassword);

            appReqBlock.setReqParm("password", newPassword);

            UserSession userSession = new UserSession(username, appReqBlock.getHttpSession());

            userSession.login(newPassword);

            appReqBlock.getHttpSession().setAttribute("userSession", userSession);

            nextPage = SecurityAdminTran.PORTAL;
        }
        catch (EDITSecurityException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            appReqBlock.getHttpServletRequest().setAttribute("username", username);
            appReqBlock.getHttpServletRequest().setAttribute("password", password);

            //  If the update failed, still need to maintain the target transaction/action
            appReqBlock.getHttpServletRequest().setAttribute("targetTransaction", targetTransaction);
            appReqBlock.getHttpServletRequest().setAttribute("targetAction", targetAction);

            nextPage = SecurityAdminTran.EXPIRED_PASSWORD_JSP;
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        return nextPage;
    }

    protected String showProfilePage(AppReqBlock appReqBlock) throws Throwable
    {
        Security securityComponent = new SecurityComponent();

        SecurityProfile securityProfile = securityComponent.loadSecurityProfile();

        appReqBlock.getHttpServletRequest().setAttribute("securityProfile", securityProfile);

        return SecurityAdminTran.PROFILE_JSP;
    }

    private String saveProfile(AppReqBlock appReqBlock) throws Throwable
    {
        String usernameMinLength = appReqBlock.getReqParm("usernameMinLength");
        String usernameMaxLength = appReqBlock.getReqParm("usernameMaxLength");
        String usernameMixedCaseCT = appReqBlock.getReqParm("usernameMixedCaseCT");
        String usernameAlphaNumCT = appReqBlock.getReqParm("usernameAlphaNumCT");
        String usernameSpecialCharsCT = appReqBlock.getReqParm("usernameSpecialCharsCT");

        String passwordMinLength = appReqBlock.getReqParm("passwordMinLength");
        String passwordMaxLength = appReqBlock.getReqParm("passwordMaxLength");
        String passwordMixedCaseCT = appReqBlock.getReqParm("passwordMixedCaseCT");
        String passwordAlphaNumCT = appReqBlock.getReqParm("passwordAlphaNumCT");
        String passwordSpecialCharsCT = appReqBlock.getReqParm("passwordSpecialCharsCT");
        String passwordExpiration = appReqBlock.getReqParm("passwordExpiration");
        String passwordRepeatCycle = appReqBlock.getReqParm("passwordRepeatCycle");
        String passwordNoRepeatsStatus = appReqBlock.getReqParm("passwordNoRepeatsStatus");

        String sessionExpiration = appReqBlock.getReqParm("sessionExpiration");
        String maxLoginAttempts = appReqBlock.getReqParm("maxLoginAttempts");

        String securityProfilePK = appReqBlock.getReqParm("securityProfilePK");
        String usernameMaskPK = appReqBlock.getReqParm("usernameMaskPK");
        String passwordMaskPK = appReqBlock.getReqParm("passwordMaskPK");
        String passwordMaskVOPK = appReqBlock.getReqParm("passwordMaskVOPK");

        Mask usernameMask = new Mask(Mask.OPERATOR_MASKTYPE);
        usernameMask.setMaskPK(new Long(Util.initString(usernameMaskPK, "0")));
        usernameMask.setMinLength(Integer.parseInt(Util.initString(usernameMinLength, "0")));
        usernameMask.setMaxLength(Integer.parseInt(Util.initString(usernameMaxLength, "0")));
        usernameMask.setMixedCaseCT(Util.initString(usernameMixedCaseCT, null));
        usernameMask.setAlphaNumericCT(Util.initString(usernameAlphaNumCT, null));
        usernameMask.setSpecialCharsCT(Util.initString(usernameSpecialCharsCT, null));
        usernameMask.setSecurityProfileFK(new Long(securityProfilePK));

        Mask pMask = new Mask(Mask.PASSWORD_MASKTYPE);
        pMask.setMaskPK(new Long(Util.initString(passwordMaskPK, "0")));
        pMask.setMinLength(Integer.parseInt(Util.initString(passwordMinLength, "0")));
        pMask.setMaxLength(Integer.parseInt(Util.initString(passwordMaxLength, "0")));
        pMask.setMixedCaseCT(Util.initString(passwordMixedCaseCT, null));
        pMask.setAlphaNumericCT(Util.initString(passwordAlphaNumCT, null));
        pMask.setSpecialCharsCT(Util.initString(passwordSpecialCharsCT, null));
        pMask.setSecurityProfileFK(new Long(securityProfilePK));

        PasswordMask passwordMask = new PasswordMask();
        passwordMask.setPasswordMaskPK(new Long(Util.initString(passwordMaskVOPK, "0")));
        passwordMask.setMaskFK(pMask.getMaskPK());
        passwordMask.setExpirationInDays(Integer.parseInt(passwordExpiration));
        passwordMask.setNumberOfRepeatCycles(Integer.parseInt(passwordRepeatCycle));
        if (passwordNoRepeatsStatus.equalsIgnoreCase("true"))
        {
            passwordMask.setRestrictRepeats("Y");
        }
        else
        {
            passwordMask.setRestrictRepeats("N");
        }

        Security securityComp = new SecurityComponent();

        SecurityProfile securityProfile = new SecurityProfile();
        securityProfile.setSecurityProfilePK(new Long(securityProfilePK));
        securityProfile.setSessionTimeoutInMinutes(Integer.parseInt(sessionExpiration));
        securityProfile.setMaxLoginAttempts(Integer.parseInt(maxLoginAttempts));

        securityComp.saveSecurityProfile(securityProfile, passwordMask, pMask, usernameMask);

        //  Reload the securityProfile and send to page
        appReqBlock.getHttpServletRequest().setAttribute("securityProfile", SecurityProfile.getSingleton());

        return SecurityAdminTran.PROFILE_JSP;
    }

    private long[] getPKsFromCommaDelimitedString(String pksAsString)
    {
        List pks = new ArrayList();

        if (pksAsString != null)
        {
            String[] pkTokens = Util.fastTokenizer(pksAsString, ",");

            for (int i = 0; i < pkTokens.length; i++)
            {
                String impliedRolePK = pkTokens[i];

                if (Util.isANumber(impliedRolePK))
                {
                    pks.add(new Long(impliedRolePK));
                }
            }
        }

        return Util.convertLongToPrim((Long[]) pks.toArray(new Long[pks.size()]));
    }
}

