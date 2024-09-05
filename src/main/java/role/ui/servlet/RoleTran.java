package role.ui.servlet;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.exceptions.VOEditException;
import edit.common.vo.*;
import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;
import edit.portal.exceptions.PortalEditingException;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.global.AppReqBlock;
import fission.utility.Util;
import role.business.Role;
import role.component.*;

import java.util.Iterator;
import java.util.Map;

public class RoleTran extends Transaction {

    //These are the Actions of the Screen
    private static final String SHOW_ROLES                      = "showRoles";
    private static final String SHOW_SELECT_CLIENT_ID_DIALOG    = "showSelectClientIdForRoles";
    private static final String SHOW_ROLES_FOR_SELECTED_CLIENT  = "showRolesForSelectedClient";
    private static final String CLEAR_ROLES                     = "clearRoles";
    private static final String SHOW_ROLE_DETAIL_SUMMARY        = "showRoleDetailSummary";
    private static final String SHOW_CANCEL_ROLE_CONFIRMATION   = "showCancelRoleConfirmationDialog";
    private static final String SELECT_VALUE_FOR_CLIENT_ROLE    = "selectValueForClientRole";
    private static final String SAVE_ROLE_TO_SUMMARY            = "saveRoleToSummary";
    private static final String LOCK_ROLES                      = "lockRoles";
    private static final String SAVE_ROLES                      = "saveRoles";
    private static final String CANCEL_ROLE                     = "cancelRole";

    private static final String TAB_CONTENT               = "/role/jsp/tabContent.jsp";
    private static final String MAIN_FRAMESET             = "/role/jsp/mainframeset.jsp";

	//Pages that the Tran will return
    private static final String ROLE_INFO                  = "/role/jsp/role.jsp";
    private static final String CLIENT_ID_SELECTION_DIALOG = "/role/jsp/clientIdSelectionDialog.jsp";
    private static final String CANCEL_ROLE_CONFIRMATION_DIALOG = "/role/jsp/cancelRoleConfirmationDialog.jsp";
    private static final String VO_EDIT_EXCEPTION_DIALOG  = "/common/jsp/VOEditExceptionDialog.jsp";
    private static final String EDITING_EXCEPTION_DIALOG  = "/common/jsp/editingExceptionDialog.jsp";

    public String execute(AppReqBlock appReqBlock) throws Exception  {

        preProcessRequest(appReqBlock);

		String action = appReqBlock.getReqParm("action");

        if (action.equals(SHOW_ROLES)) {

			return showRoles(appReqBlock);
		}
        else if (action.equals(SHOW_SELECT_CLIENT_ID_DIALOG)) {

            return showSelectClientIdDialog(appReqBlock);
        }
        else if (action.equals(SHOW_ROLES_FOR_SELECTED_CLIENT)) {

            return showRolesForSelectedClient(appReqBlock);
        }
        else if (action.equals(CLEAR_ROLES)) {

            return clearRoles(appReqBlock);
        }
        else if (action.equals(SHOW_ROLE_DETAIL_SUMMARY)) {

            return showRoleDetailSummary(appReqBlock);
        }
        else if (action.equals(SHOW_CANCEL_ROLE_CONFIRMATION)) {

            return showCancelRoleConfirmationDialog(appReqBlock);
        }
        else if (action.equals(SELECT_VALUE_FOR_CLIENT_ROLE)) {

            return selectValueForClientRole(appReqBlock);
        }
        else if (action.equals(SAVE_ROLE_TO_SUMMARY)) {

            return saveRoleToSummary(appReqBlock);
        }
        else if (action.equals(LOCK_ROLES)) {

            return lockRoles(appReqBlock);
        }
        else if (action.equals(SAVE_ROLES)) {

            return saveRoles(appReqBlock);
        }
        else if (action.equals(CANCEL_ROLE)) {

            return cancelRole(appReqBlock);
        }

		return MAIN_FRAMESET;
	}

    protected String showRoles(AppReqBlock appReqBlock) throws Exception {

        String clientDetailPK = appReqBlock.getFormBean().getValue("clientDetailPK");
        if (clientDetailPK.equals("")) {

            SessionBean clientDetailSessionBean = appReqBlock.getSessionBean("clientDetailSessionBean");
            PageBean clientDetailPageBean = clientDetailSessionBean.getPageBean("pageBean");
            clientDetailPK = clientDetailPageBean.getValue("clientDetailPK");
        }

        ClientDetailVO clientDetailVO = null;
        if (clientDetailPK != null && !clientDetailPK.equals("")) {

            client.business.Lookup clientLookup = new client.component.LookupComponent();

            ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailByClientPK(Long.parseLong(clientDetailPK), true, null);

            if (clientDetailVOs != null && clientDetailVOs.length > 0) {

                clientDetailVO = clientDetailVOs[0];
            }
        }

        if (clientDetailVO != null) {

            getBeanInfoForRoles(appReqBlock, clientDetailVO);
        }

        else {

            PageBean pageBean = new PageBean();
            pageBean.putValue("clientDetailFK", clientDetailPK);
            pageBean.putValue("clientId", "");
            pageBean.putValue("roleStatus", "new");
            appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);
        }

		return ROLE_INFO;
	}

    protected String showSelectClientIdDialog(AppReqBlock appReqBlock) throws Exception {

		return CLIENT_ID_SELECTION_DIALOG;
	}

    protected String showRolesForSelectedClient(AppReqBlock appReqBlock) throws Exception {

        PageBean formBean = appReqBlock.getFormBean();
        String clientId = formBean.getValue("clientId");
        formBean.putValue("clientId", clientId);

        client.business.Lookup lookup = (client.business.Lookup) appReqBlock.getWebService("client-lookup");

        ClientDetailVO[] clientDetailVOs = lookup.getClientByClientId(clientId);

        if (clientDetailVOs != null && clientDetailVOs.length > 0) {

            getBeanInfoForRoles(appReqBlock, clientDetailVOs[0]);
        }

        else {

            String clientMessage = "Client Id " + clientId + " Not Found";
            appReqBlock.getHttpServletRequest().setAttribute("clientMessage", clientMessage);
        }

		return ROLE_INFO;
	}

    private void getBeanInfoForRoles(AppReqBlock appReqBlock, ClientDetailVO clientDetailVO) throws Exception {

        SessionBean bankAccountSessionBean = appReqBlock.getSessionBean("bankAccountSessionBean");
        SessionBean preferenceSessionBean = appReqBlock.getSessionBean("preferenceSessionBean");
        SessionBean taxInformationSessionBean = appReqBlock.getSessionBean("taxInformationSessionBean");
        SessionBean roleSessionBean = appReqBlock.getSessionBean("roleSessionBean");
        bankAccountSessionBean.clearState();
        preferenceSessionBean.clearState();
        taxInformationSessionBean.clearState();
        roleSessionBean.clearState();

        String clientDetailPK = clientDetailVO.getClientDetailPK() + "";
        String clientId = clientDetailVO.getClientIdentification();

        PreferenceVO[] preferenceVO = clientDetailVO.getPreferenceVO();

        if (preferenceVO != null) {

            for (int p = 0; p < preferenceVO.length; p++) {

                PageBean preferencePageBean = new PageBean();

                long preferencePK = preferenceVO[p].getPreferencePK();
                String printAs = preferenceVO[p].getPrintAs();
                if (printAs == null) {

                    printAs = "";
                }

                String printAs2 = preferenceVO[p].getPrintAs2();
                if (printAs2 == null) {

                    printAs2 = "";
                }

                String disbursementSource = preferenceVO[p].getDisbursementSourceCT();
                if (disbursementSource == null) {

                    disbursementSource = "";
                }

                String paymentMode = preferenceVO[p].getPaymentModeCT();
                if (paymentMode == null) {

                    paymentMode = "";
                }

               // String minimumCheck = Util.formatDecimal("########0.00", preferenceVO[p].getMinimumCheck());
                String minimumCheck = Util.formatDecimal("########0.00", new EDITBigDecimal(preferenceVO[p].getMinimumCheck()));
                String bankAccountNumber = preferenceVO[p].getBankAccountNumber();
                String bankRoutingNumber = preferenceVO[p].getBankRoutingNumber();
                String bankAccountType = preferenceVO[p].getBankAccountTypeCT();

                preferencePageBean.putValue("clientDetailFK", clientDetailPK);
                preferencePageBean.putValue("preferencePK", preferencePK + "");
                preferencePageBean.putValue("printAs", printAs);
                preferencePageBean.putValue("printAs2", printAs2);
                preferencePageBean.putValue("disbursementSource", disbursementSource);
                preferencePageBean.putValue("paymentMode", paymentMode);
                preferencePageBean.putValue("minimumCheck", minimumCheck);
                preferencePageBean.putValue("bankAccountNumber", bankAccountNumber);
                preferencePageBean.putValue("bankRoutingNumber", bankRoutingNumber);
                preferencePageBean.putValue("bankAccountType", bankAccountType);

                preferenceSessionBean.putPageBean(preferencePK + "", preferencePageBean);
            }
        }

        TaxInformationVO[] taxInformationVO = clientDetailVO.getTaxInformationVO();

        if (taxInformationVO != null && taxInformationVO.length > 0) {

            TaxProfileVO[] taxProfileVO = taxInformationVO[0].getTaxProfileVO();

            if (taxProfileVO != null) {

                for (int t = 0; t < taxProfileVO.length; t++) {

                    PageBean taxPageBean = new PageBean();

                    long taxProfilePK = taxProfileVO[t].getTaxProfilePK();
                    String taxFilingStatus = taxProfileVO[t].getTaxFilingStatusCT();
                    if (taxFilingStatus == null) {

                        taxFilingStatus = "";
                    }

                    String exemptions = taxProfileVO[t].getExemptions();
                    if (exemptions == null) {

                        exemptions = "";
                    }

                    String taxIndicator = taxProfileVO[t].getTaxIndicatorCT();
                    if (taxIndicator == null) {

                        taxIndicator = "";
                    }

                    String ficaIndicator = taxProfileVO[t].getFicaIndicator();
                    if (ficaIndicator == null) {

                        ficaIndicator = "";
                    }

                    taxPageBean.putValue("clientDetailFK", clientDetailPK);
                    taxPageBean.putValue("taxProfilePK", taxProfilePK + "");
                    taxPageBean.putValue("filingStatusId", taxFilingStatus);
                    taxPageBean.putValue("exemptions", exemptions);
                    taxPageBean.putValue("taxIndicator", taxIndicator);
                    taxPageBean.putValue("ficaIndicator", ficaIndicator);

                    taxInformationSessionBean.putPageBean(taxProfilePK + "", taxPageBean);
                }
            }
        }

        role.business.Lookup roleLookup = (role.business.Lookup) appReqBlock.getWebService("role-lookup");

        ClientRoleVO[] clientRoleVO = roleLookup.getRolesByClientDetailFK(Long.parseLong(clientDetailPK));

        if (clientRoleVO != null) {

            for (int r = 0; r < clientRoleVO.length; r++) {

                PageBean clientRolePageBean = new PageBean();

                long clientRolePK = clientRoleVO[r].getClientRolePK();
                long preferenceFK = clientRoleVO[r].getPreferenceFK();
                long taxProfileFK = clientRoleVO[r].getTaxProfileFK();
                String roleType = clientRoleVO[r].getRoleTypeCT();

                clientRolePageBean.putValue("clientDetailFK", clientDetailPK);
                clientRolePageBean.putValue("rolePK", clientRolePK + "");
                clientRolePageBean.putValue("preferenceFK", preferenceFK + "");
                clientRolePageBean.putValue("taxProfileFK", taxProfileFK + "");
                clientRolePageBean.putValue("roleType", roleType);

                roleSessionBean.putPageBean(roleType + clientRolePK + "" + preferenceFK + taxProfileFK, clientRolePageBean);
            }
        }

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", clientDetailPK);
        pageBean.putValue("clientId", clientId);
        pageBean.putValue("roleStatus", "new");
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);
    }

    protected String clearRoles(AppReqBlock appReqBlock) throws Exception {

        String clientId = appReqBlock.getFormBean().getValue("clientId");
        String clientDetailPK = appReqBlock.getFormBean().getValue("clientDetailFK");

        SessionBean roleSessionBean = appReqBlock.getSessionBean("roleSessionBean");
        SessionBean bankAccountSessionBean = appReqBlock.getSessionBean("bankAccountSessionBean");
        SessionBean preferenceSessionBean = appReqBlock.getSessionBean("preferenceSessionBean");
        SessionBean taxInformationSessionBean = appReqBlock.getSessionBean("taxInformationSessionBean");
        roleSessionBean.clearState();
        bankAccountSessionBean.clearState();
        preferenceSessionBean.clearState();
        taxInformationSessionBean.clearState();

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", clientDetailPK);
        pageBean.putValue("clientId", clientId);
        pageBean.putValue("roleStatus", "new");
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		return ROLE_INFO;
	}

    protected String showRoleDetailSummary (AppReqBlock appReqBlock) throws Exception {

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableVO codeTableVO = null;

        PageBean formBean = appReqBlock.getFormBean();

        setUpFormBean(appReqBlock, formBean);

		appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);

		return ROLE_INFO;
	}

    private void setUpFormBean(AppReqBlock appReqBlock, PageBean formBean) {

        String clientDetailFK = formBean.getValue("clientDetailPK");
        String selectedRolePK = formBean.getValue("selectedRolePK");
        String selectedRole = formBean.getValue("selectedRoleType");
        String selectedBankFK = formBean.getValue("selectedBankFK");
        String selectedPreferenceFK = formBean.getValue("selectedPreferenceFK");
        String selectedTaxProfileFK = formBean.getValue("selectedTaxProfileFK");

		String key = selectedRole + selectedRolePK + selectedBankFK + selectedPreferenceFK + selectedTaxProfileFK;

		SessionBean roleSessionBean = appReqBlock.getSessionBean("roleSessionBean");

		PageBean rolePageBean = roleSessionBean.getPageBean(key);

        formBean.putValue("clientDetailFK", clientDetailFK);
        formBean.putValue("rolePK", rolePageBean.getValue("rolePK"));
        formBean.putValue("roleType", rolePageBean.getValue("roleType"));
        formBean.putValue("bankAccountInformationFK", rolePageBean.getValue("bankAccountInformationFK"));
        formBean.putValue("preferenceFK", rolePageBean.getValue("preferenceFK"));
        formBean.putValue("taxProfileFK", rolePageBean.getValue("taxProfileFK"));
        formBean.putValue("roles", rolePageBean.getValue("roleType"));
        formBean.putValue("roleStatus", "existing");
        formBean.putValue("origBankFK", rolePageBean.getValue("bankAccountInformationFK"));
        formBean.putValue("origPreferenceFK", rolePageBean.getValue("preferenceFK"));
        formBean.putValue("origTaxProfileFK", rolePageBean.getValue("taxProfileFK"));
    }

    protected String showCancelRoleConfirmationDialog(AppReqBlock appReqBlock) throws Exception {

        String clientDetailPK = appReqBlock.getReqParm("clientRolePK");
        String clientId = appReqBlock.getReqParm("clientId");

        appReqBlock.getHttpServletRequest().setAttribute("clientRolePK", clientDetailPK);
        appReqBlock.getHttpServletRequest().setAttribute("clientId", clientId);

        return CANCEL_ROLE_CONFIRMATION_DIALOG;
    }

    protected String selectValueForClientRole(AppReqBlock appReqBlock) throws Exception {

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableVO codeTableVO  = null;

		PageBean formBean = appReqBlock.getFormBean();
        String clientDetailFK = formBean.getValue("clientDetailPK");
        formBean.putValue("clientDetailFK", clientDetailFK);
        String clientId = formBean.getValue("clientId");
        formBean.putValue("clientId", clientId);
		String rolePK = formBean.getValue("rolePK");
        if (rolePK.equals("")) {

            rolePK = "0";
        }

        String selectedRoles = formBean.getValue("selectedRoles");
        String[] selectedRolesST = Util.fastTokenizer(selectedRoles, ",");
        selectedRoles = "";
        for (int r = 0; r < selectedRolesST.length; r++) {

            String roleType = selectedRolesST[r];
            if (roleType.equals("")) {

                break;
            }
            if (Util.isANumber(roleType)) {

                codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(roleType));
                roleType = codeTableVO.getCode();
            }
            else if (roleType.equalsIgnoreCase("Please Select")) {

                roleType = "";
            }
            selectedRolesST[r] = roleType;

            if (r == 0) {

                selectedRoles = selectedRolesST[r];
            }
            else {

                selectedRoles = selectedRoles + "," + selectedRolesST[r];
            }
        }

        String bankFK = formBean.getValue("selectedBankFK");
        String preferenceFK = formBean.getValue("selectedPreferenceFK");
        String taxProfileFK = formBean.getValue("selectedTaxProfileFK");

        formBean.putValue("clientDetailFK", clientDetailFK);
        formBean.putValue("rolePK", rolePK);
        formBean.putValue("roles", selectedRoles);
        formBean.putValue("bankAccountInformationFK", bankFK);
        formBean.putValue("preferenceFK", preferenceFK);
        formBean.putValue("taxProfileFK", taxProfileFK);

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);

		return ROLE_INFO;
	}

    protected String saveRoleToSummary(AppReqBlock appReqBlock) throws Exception {

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableVO codeTableVO  = null;

		PageBean formBean     = appReqBlock.getFormBean();
        String selectedRoles  = formBean.getValue("selectedRoles");
        String[] selectedRolesST = Util.fastTokenizer(selectedRoles, ",");
		SessionBean roleSessionBean = appReqBlock.getSessionBean("roleSessionBean");

        String clientDetailFK = formBean.getValue("clientDetailPK");
        String clientId = formBean.getValue("clientId");
		String rolePK = formBean.getValue("rolePK");
        if (rolePK.equals("")) {

            rolePK = "0";
        }

        for (int r = 0; r < selectedRolesST.length; r++) {

            String roleType = selectedRolesST[r];
            if (roleType.equals("")) {

                break;
            }
            if (Util.isANumber(roleType)) {

                codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(roleType));
                roleType = codeTableVO.getCode();
            }

            else if (roleType.equalsIgnoreCase("Please Select")) {

                roleType = "";
            }

            String origBankFK  = formBean.getValue("origBankFK");
            String origPreferenceFK = formBean.getValue("origPreferenceFK");
            String origTaxProfileFK = formBean.getValue("origTaxProfileFK");
            String bankFK  = formBean.getValue("selectedBankFK");
            String preferenceFK = formBean.getValue("selectedPreferenceFK");
            String taxProfileFK = formBean.getValue("selectedTaxProfileFK");
            if (origBankFK.equals("")) {

                origBankFK = "0";
            }

            if (bankFK.equals("")) {

                bankFK = "0";
            }
            if (origPreferenceFK.equals("")) {

                origPreferenceFK = "0";
            }

            if (preferenceFK.equals("")) {

                preferenceFK = "0";
            }
            if (origTaxProfileFK.equals("")) {

                origTaxProfileFK = "0";
            }

            if (taxProfileFK.equals("")) {

                taxProfileFK = "0";
            }

            String origKey = "";
            if (!origBankFK.equals(bankFK) ||
                !origPreferenceFK.equals(preferenceFK) ||
                !origTaxProfileFK.equals(taxProfileFK)) {

                origKey = roleType + rolePK + origBankFK + origPreferenceFK + origTaxProfileFK;
            }

            String key = roleType + rolePK + bankFK + preferenceFK + taxProfileFK;

            PageBean clientRolePageBean = null;

            if (origKey.equals("") || !roleSessionBean.pageBeanExists(origKey)) {

                if (!roleSessionBean.pageBeanExists(key)) {

                    clientRolePageBean = new PageBean();

                    roleSessionBean.putPageBean(key, clientRolePageBean);
                }
                else {

                    clientRolePageBean = roleSessionBean.getPageBean(key);
                }
            }

            else if (!origKey.equals("")) {

                roleSessionBean.removePageBean(origKey);

                clientRolePageBean = new PageBean();

                roleSessionBean.putPageBean(key, clientRolePageBean);
            }

            clientRolePageBean.putValue("clientDetailFK", clientDetailFK);
            clientRolePageBean.putValue("rolePK", rolePK);
            clientRolePageBean.putValue("bankAccountInformationFK", bankFK);
            clientRolePageBean.putValue("preferenceFK", preferenceFK);
            clientRolePageBean.putValue("taxProfileFK", taxProfileFK);
            clientRolePageBean.putValue("roleType", roleType);

            roleSessionBean.putPageBean(roleType + rolePK + "" + bankFK +
                                         preferenceFK + taxProfileFK, clientRolePageBean);
        }

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", clientDetailFK);
        pageBean.putValue("clientId", clientId);
        pageBean.putValue("roleStatus", "new");
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		return ROLE_INFO;
	}

    protected String lockRoles(AppReqBlock appReqBlock) throws Exception {

        new RoleUseCaseComponent().updateRole();        

        PageBean formBean = appReqBlock.getFormBean();

        setUpFormBean(appReqBlock, formBean);

        String rolePK = formBean.getValue("rolePK");
        if (rolePK.equals("")) {

            formBean.putValue("roleStatus", "new");
        }

        String clientDetailFKAsStr = formBean.getValue("clientDetailPK");

        long clientDetailFK = (Util.isANumber(clientDetailFKAsStr))?Long.parseLong(clientDetailFKAsStr):0;

//        lockCurrentElement(appReqBlock, operator, clientDetailFK, "role-service");

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.lockClientDetail(clientDetailFK);

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);

        return ROLE_INFO;
    }

    protected String saveRoles(AppReqBlock appReqBlock) throws Exception {

        PageBean formBean = appReqBlock.getFormBean();

        SessionBean roleSessionBean = appReqBlock.getSessionBean("roleSessionBean");

        PortalEditingException editingException = null;

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        CodeTableVO codeTableVO = null;

        String clientMessage = "";

        Map roleBeansHT = roleSessionBean.getPageBeans();
        Iterator roleBeanEnum = roleBeansHT.values().iterator();

        String clientDetailFK = "";

        Role roleComp = (Role) appReqBlock.getWebService("role-service");

        while (roleBeanEnum.hasNext()) {

            PageBean rolePageBean = (PageBean) roleBeanEnum.next();

            String currentClientDetailFK = rolePageBean.getValue("clientDetailFK");
            if (!currentClientDetailFK.equals("")) {

                clientDetailFK = currentClientDetailFK;

                String rolePK = rolePageBean.getValue("rolePK");
                String roleType = rolePageBean.getValue("roleType");
                String preferenceFK = rolePageBean.getValue("preferenceFK");
                String taxProfileFK = rolePageBean.getValue("taxProfileFK");

                ClientRoleVO clientRoleVO = new ClientRoleVO();

                if (Util.isANumber(rolePK)) {

                    clientRoleVO.setClientRolePK(Long.parseLong(rolePK));
                }
                else {

                    clientRoleVO.setClientRolePK(0);
                }

                clientRoleVO.setClientDetailFK(Long.parseLong(currentClientDetailFK));

                if (Util.isANumber(preferenceFK)) {

                    clientRoleVO.setPreferenceFK(Long.parseLong(preferenceFK));
                }

                else {

                    clientRoleVO.setPreferenceFK(0);
                }

                if (Util.isANumber(taxProfileFK)) {

                    clientRoleVO.setTaxProfileFK(Long.parseLong(taxProfileFK));
                }

                else {

                    clientRoleVO.setTaxProfileFK(0);
                }

                clientRoleVO.setRoleTypeCT(roleType);
                clientRoleVO.setOverrideStatus("P");

                roleComp.saveOrUpdateClientRole(clientRoleVO);
            }
        }

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlockClientDetail();

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVO = clientLookup.findClientDetailByClientPK(Long.parseLong(clientDetailFK), false, null);

        String clientId = clientDetailVO[0].getClientIdentification();

        formBean.putValue("clientId", clientId);

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);

        return ROLE_INFO;
    }

    protected String cancelRole(AppReqBlock appReqBlock) throws Exception {

//        UILockToken uiLockToken = (UILockToken) appReqBlock.getHttpSession().getAttribute("uiLockToken");
//        if (uiLockToken != null &&
//            uiLockToken.getWebServiceName() != null) {
//
//            this.unlockCurrentElement(uiLockToken, (ILockableElement) appReqBlock.getWebService(uiLockToken.getWebServiceName()));
//        }

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlockClientDetail();

        clearAllRoleSessionBeans(appReqBlock);

        return ROLE_INFO;
	}

    protected void preProcessRequest(AppReqBlock appReqBlock) throws Exception{


    }

    protected void postProcessRequest(AppReqBlock appReqBlock) throws Exception{

    }

    private String[] joinArrays(String[] voMessages, String[] clientMessages) {

        if (voMessages == null) {

            voMessages = new String[0];
        }
        String[] messages = new String[(voMessages.length + clientMessages.length)];

        if (voMessages != null) {
            for (int i = 0; i < voMessages.length; i++){

                messages[i] = voMessages[i];
            }
        }

        int j = voMessages.length;

        if (clientMessages != null) {

            for (int k = 0; k < clientMessages.length; k++) {

                messages[j] = clientMessages[k];

                j++;
            }
        }

        if (messages.length == 0) {

            return null;
        }
        else {

            return messages;
        }
    }

    protected String showVOEditExceptionDialog(AppReqBlock appReqBlock) throws Exception{

        VOEditException voEditException = (VOEditException) appReqBlock.getHttpSession().getAttribute("VOEditException");

        // Remove voEditException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("VOEditException");

        appReqBlock.getHttpServletRequest().setAttribute("VOEditException", voEditException);

        return VO_EDIT_EXCEPTION_DIALOG;
    }

    protected String showEditingExceptionDialog(AppReqBlock appReqBlock) throws Exception{

        PortalEditingException editingException = (PortalEditingException) appReqBlock.getHttpSession().getAttribute("portalEditingException");

        // Remove editingException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("portalEditingException");

        appReqBlock.getHttpServletRequest().setAttribute("portalEditingException", editingException);

        return EDITING_EXCEPTION_DIALOG;
    }

    private void clearAllRoleSessionBeans(AppReqBlock appReqBlock) {

        appReqBlock.getSessionBean("bankAccountSessionBean").clearState();
        appReqBlock.getSessionBean("preferenceSessionBean").clearState();
        appReqBlock.getSessionBean("taxInformationSessionBean").clearState();
        appReqBlock.getSessionBean("roleSessionBean").clearState();
        appReqBlock.getSessionBean("clientStateBean").clearState();
    }
}