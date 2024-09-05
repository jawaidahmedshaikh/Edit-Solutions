/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
 package client.ui.servlet;

import batch.business.Batch;
import batch.component.BatchComponent;
import client.*;
import client.business.Client;
import client.business.Lookup;
import client.component.ClientUseCaseComponent;
import client.component.EditOFACCheck;
import client.component.OFACBindException;
import contract.ChangeHistory;
import contract.util.*;
import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITLockException;
import edit.common.vo.AgentVO;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleFinancialVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.CodeTableVO;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.ContactInformationVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.PreferenceVO;
import edit.common.vo.RedirectVO;
import edit.common.vo.ReinsurerVO;
import edit.common.vo.TaxInformationVO;
import edit.common.vo.TaxProfileVO;
import edit.common.vo.ValidationVO;
import edit.portal.common.session.UserSession;
import edit.portal.common.transactions.Transaction;
import edit.portal.exceptions.PortalEditingException;
import edit.portal.widget.ClientHistorySummaryTableModel;
import edit.services.db.hibernate.SessionHelper;
import edit.services.command.*;
import engine.sp.*;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.global.AppReqBlock;
import fission.utility.DateTimeUtil;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ClientDetailTran extends Transaction
{
    //These are the Actions of the Screen
    private static final String SHOW_CLIENT_DETAILS = "showClientDetails";
    private static final String SHOW_CLIENT_ADDRESS = "showClientAddress";
    private static final String SHOW_CLIENT_HISTORY = "showClientHistory";
//    private static final String SHOW_QA_CLIENT_ADDRESS = "showQAClientAddress";
    private static final String SHOW_CLIENT_TAX_INFO = "showClientTaxInformation";
    private static final String SHOW_CLIENT_BANK_INFO = "showClientBankInformation";
    private static final String SHOW_CLIENT_PREFERENCES = "showClientPreferences";
    private static final String SHOW_ADDRESS_DETAIL_SUMMARY = "showAddressDetailSummary";
//    private static final String SHOW_QA_ADDRESS_DETAIL_SUMMARY = "showQAAddressDetailSummary";
    private static final String SHOW_SELECTED_CLIENT = "showSelectedClient";
    private static final String SHOW_EDITING_EXCEPTION_DIALOG = "showEditingExceptionDialog";
    private static final String SAVE_CLIENT_DETAILS = "saveClientDetails";
//    private static final String QUICK_ADD_SAVE = "quickAddSave";
//    private static final String QUICK_ADD_LOGOUT = "quickAddLogout";
    private static final String SAVE_ADDRESS_TO_SUMMARY = "saveAddressToSummary";
//    private static final String SAVE_QA_ADDRESS_TO_SUMMARY = "saveQAAddressToSummary";
    private static final String DELETE_CLIENT_DETAILS = "deleteClientDetails";
    private static final String DELETE_SELECTED_ADDRESS = "deleteSelectedAddress";
//    private static final String DELETE_SELECTED_QA_ADDRESS = "deleteSelectedQAAddress";
    private static final String CANCEL_CLIENT_DETAILS = "cancelClientDetails";
    private static final String ADD_NEW_CLIENT = "addNewClient";
    private static final String LOAD_CLIENT_AFTER_SEARCH = "loadClientAfterSearch";
    private static final String LOCK_CLIENT = "lockClient";
    private static final String SHOW_CANCEL_CLIENT_CONFIRMATION_DIALOG = "showCancelClientConfirmationDialog";
//    private static final String SHOW_QA_CANCEL_CLIENT_CONFIRM_DIALOG = "showQACancelClientConfirmationDialog";
    private static final String SHOW_DELETE_CLIENT_CONFIRMATION_DIALOG = "showDeleteClientConfirmationDialog";
    private static final String SHOW_VO_EDIT_EXCEPTION_DIALOG = "showVOEditExceptionDialog";
    private static final String CLEAR_ADDRESS_FOR_ADD_OR_CANCEL = "clearAddressForAddOrCancel";
//    private static final String CLEAR_QA_ADDRESS_FOR_ADD_OR_CANCEL = "clearQAAddressForAddOrCancel";
    private static final String SHOW_JUMP_TO_DIALOG = "showJumpToDialog";
    private static final String JUMP_TO_DIALOG = "/common/jsp/jumpToDialog.jsp";
    private static final String SHOW_CONTACT_INFO_DIALOG = "showContactInfoDialog";
    private static final String CLEAR_CONTACT_FOR_ADD_OR_CANCEL = "clearContactForAddOrCancel";
    private static final String SAVE_CONTACT_TO_SUMMARY = "saveContactToSummary";
    private static final String DELETE_SELECTED_CONTACT = "deleteSelectedContact";
    private static final String SHOW_CONTACT_INFO_DETAIL_SUMMARY = "showContactInfoDetailSummary";
    private static final String CLOSE_CONTACT_INFO_DIALOG = "closeContactInfoDialog";
    private static final String SHOW_DOB_GENDER_DIALOG = "showDOBGenderChangeDialog";
    private static final String SAVE_DOB_GENDER_DIALOG = "saveDOBGenderChangeDialog";
    private static final String SAVE_TAX_INFORMATION = "saveClientTaxPage";
    private static final String SAVE_PREFERENCE_INFORMATION = "savePreference";
    private static final String SHOW_SELECTED_PREFERENCE = "showSelectedPreference";
    private static final String CLEAR_PREFERENCE_FOR_ADD = "clearPreferenceForAdd";
    private static final String SHOW_CONTRACT_AGENT_INFO = "showContractAgentInfo";
    private static final String SHOW_HISTORY_DETAIL_SUMMARY = "showHistoryDetailSummary";

    //Pages that the Tran will return
    public static final String CLIENT_DETAIL = "/client/jsp/clientDetail.jsp";
    private static final String CLIENT_ADDRESS = "/client/jsp/clientAddress.jsp";
    private static final String CLIENT_HISTORY = "/client/jsp/clientHistory.jsp";
//    private static final String QA_CLIENT_ADDRESS = "/client/jsp/quickAddAddress.jsp";
    private static final String CLIENT_TAX_INFORMATION = "/client/jsp/clientTaxInformation.jsp";
    private static final String CLIENT_BANK_INFORMATION = "/client/jsp/clientBankInformation.jsp";
    private static final String CLIENT_PREFERENCE_INFO = "/client/jsp/preferences.jsp";
    private static final String MAIN_FRAMESET = "/client/jsp/mainframeset.jsp";
    private static final String CANCEL_CLIENT_CONFIRMATION_DIALOG = "/client/jsp/cancelClientConfirmationDialog.jsp";
//    private static final String CANCEL_QA_CLIENT_CONFIRM_DIAL0G = "/client/jsp/cancelQAClientConfirmationDialog.jsp";
    private static final String DELETE_CLIENT_CONFIRMATION_DIALOG = "/client/jsp/deleteClientConfirmationDialog.jsp";
    private static final String VO_EDIT_EXCEPTION_DIALOG = "/common/jsp/VOEditExceptionDialog.jsp";
    private static final String EDITING_EXCEPTION_DIALOG = "/common/jsp/editingExceptionDialog.jsp";
    private static final String REPORTING_MAIN = "/reporting/jsp/reportingSelection.jsp";
    private static final String CHECK_OFAC_ALL_CLIENTS = "checkOFACForAllClients";
    private static final String CONTACT_INFO_DIALOG = "/client/jsp/contactInformationDialog.jsp";
    private static final String DOB_GENDER_CHANGE_DIALOG = "/client/jsp/dobGenderChangeDialog.jsp";
    private static final String CONTRACT_AGENT_INFO_DIALOG = "/client/jsp/contractAgentInformationDialog.jsp";

    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        this.preProcessRequest(appReqBlock);

        String action = appReqBlock.getReqParm("action");
        String returnPage = null;

        try
        {
            if (action.equals(SHOW_CLIENT_DETAILS))
            {
                returnPage = showClientDetails(appReqBlock);
            }
            else if (action.equals(SHOW_CLIENT_ADDRESS))
            {
                returnPage = showClientAddress(appReqBlock);
            }
            else if (action.equals(SHOW_CLIENT_HISTORY))
            {
                returnPage = showClientHistory(appReqBlock);
            }
//            else if (action.equals(SHOW_QA_CLIENT_ADDRESS))
//            {
//                returnPage = showQAClientAddress(appReqBlock);
//            }
            else if (action.equals(SHOW_CLIENT_TAX_INFO))
            {
                returnPage = showClientTaxInformation(appReqBlock);
            }
            else if (action.equals(SHOW_CLIENT_BANK_INFO))
            {
                returnPage = showClientBankInformation(appReqBlock);
            }

            if (action.equals(SHOW_CLIENT_PREFERENCES))
            {
                returnPage = showClientPreferences(appReqBlock);
            }
            else if (action.equals(SHOW_ADDRESS_DETAIL_SUMMARY))
            {
                returnPage = showAddressDetailSummary(appReqBlock);
            }
//            else if (action.equals(SHOW_QA_ADDRESS_DETAIL_SUMMARY))
//            {
//                returnPage = showQAAddressDetailSummary(appReqBlock);
//            }
            else if (action.equals(SHOW_SELECTED_CLIENT))
            {
                returnPage = showSelectedClient(appReqBlock);
            }
            else if (action.equals(SAVE_CLIENT_DETAILS))
            {
                returnPage = saveClientDetails(appReqBlock);
            }
//            else if (action.equals(QUICK_ADD_SAVE))
//            {
//                returnPage = quickAddSave(appReqBlock);
//            }
//            else if (action.equals(QUICK_ADD_LOGOUT))
//            {
//                returnPage = quickAddLogout(appReqBlock);
//            }
            else if (action.equals(SAVE_ADDRESS_TO_SUMMARY))
            {
                returnPage = saveAddressToSummary(appReqBlock);
            }
//            else if (action.equals(SAVE_QA_ADDRESS_TO_SUMMARY))
//            {
//                returnPage = saveQAAddressToSummary(appReqBlock);
//            }
            else if (action.equals(DELETE_CLIENT_DETAILS))
            {
                returnPage = deleteClientDetails(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_ADDRESS))
            {
                returnPage = deleteSelectedAddress(appReqBlock);
            }
//            else if (action.equals(DELETE_SELECTED_QA_ADDRESS))
//            {
//                returnPage = deleteSelectedQAAddress(appReqBlock);
//            }
            else if (action.equals(CANCEL_CLIENT_DETAILS))
            {
                returnPage = cancelClientDetails(appReqBlock);
            }
            else if (action.equals(ADD_NEW_CLIENT))
            {
                returnPage = addNewClient(appReqBlock);
            }
            else if (action.equals(LOAD_CLIENT_AFTER_SEARCH))
            {
                returnPage = loadClientAfterSearch(appReqBlock);
            }
            else if (action.equals(LOCK_CLIENT))
            {
                returnPage = lockClient(appReqBlock);
            }
            else if (action.equals(SHOW_CANCEL_CLIENT_CONFIRMATION_DIALOG))
            {
                returnPage = showCancelClientConfirmationDialog(appReqBlock);
            }
//            else if (action.equals(SHOW_QA_CANCEL_CLIENT_CONFIRM_DIALOG))
//            {
//                returnPage = showQACancelClientConfirmationDialog(appReqBlock);
//            }
            else if (action.equals(SHOW_DELETE_CLIENT_CONFIRMATION_DIALOG))
            {
                returnPage = showDeleteClientConfirmationDialog(appReqBlock);
            }
            else if (action.equals(SHOW_VO_EDIT_EXCEPTION_DIALOG))
            {
                returnPage = showVOEditExceptionDialog(appReqBlock);
            }
            else if (action.equals(SHOW_EDITING_EXCEPTION_DIALOG))
            {
                returnPage = showEditingExceptionDialog(appReqBlock);
            }
            else if (action.equals(CLEAR_ADDRESS_FOR_ADD_OR_CANCEL))
            {
                returnPage = clearAddressForAddOrCancel(appReqBlock);
            }
//            else if (action.equals(CLEAR_QA_ADDRESS_FOR_ADD_OR_CANCEL))
//            {
//                returnPage = clearQAAddressForAddOrCancel(appReqBlock);
//            }
            else if (action.equals(SHOW_JUMP_TO_DIALOG))
            {
                returnPage = showJumpToDialog(appReqBlock);
            }
            else if (action.equals(CHECK_OFAC_ALL_CLIENTS))
            {
                returnPage = checkOFACForAllClients(appReqBlock);
            }
            else if (action.equals(SHOW_CONTACT_INFO_DIALOG))
            {
                returnPage = showContactInfoDialog(appReqBlock);
            }
            else if (action.equals(CLEAR_CONTACT_FOR_ADD_OR_CANCEL))
            {
                returnPage = clearContactForAddOrCancel(appReqBlock);
            }
            else if (action.equals(SAVE_CONTACT_TO_SUMMARY))
            {
                returnPage = saveContactToSummary(appReqBlock);
            }
            else if (action.equals(DELETE_SELECTED_CONTACT))
            {
                returnPage = deleteSelectedContact(appReqBlock);
            }
            else if (action.equals(SHOW_CONTACT_INFO_DETAIL_SUMMARY))
            {
                returnPage = showContactInfoDetailSummary(appReqBlock);
            }
            else if (action.equals(CLOSE_CONTACT_INFO_DIALOG))
            {
                returnPage = closeContactInfoDialog(appReqBlock);
            }
            else if (action.equals(SHOW_DOB_GENDER_DIALOG))
            {
                returnPage = showDOBGenderChangeDialog(appReqBlock);
            }
            else if (action.equals(SAVE_DOB_GENDER_DIALOG))
            {
                returnPage = saveDOBGenderChangeDialog(appReqBlock);
            }
            else if (action.equals(SAVE_TAX_INFORMATION))
            {
                returnPage = saveClientTaxPage(appReqBlock);
            }
            else if (action.equals(SAVE_PREFERENCE_INFORMATION))
            {
                returnPage = savePreference(appReqBlock);
            }
            else if (action.equals(SHOW_SELECTED_PREFERENCE))
            {
                returnPage = showSelectedPreference(appReqBlock);
            }
            else if (action.equals(CLEAR_PREFERENCE_FOR_ADD))
            {
                returnPage = clearPreferenceForAdd(appReqBlock);
            }
            else if (action.equals(SHOW_CONTRACT_AGENT_INFO))
            {
                returnPage = showContractAgentInfo(appReqBlock);
            }
            else if (action.equals(SHOW_HISTORY_DETAIL_SUMMARY))
            {
                returnPage = showHistoryDetailSummary(appReqBlock);
            }

            SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
            String previousPage = stateBean.getValue("currentPage");

            stateBean.putValue("previousPage", previousPage);
            stateBean.putValue("currentPage", returnPage);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
            throw e;
        }

        return returnPage;
    }


    protected String showJumpToDialog(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlockClientDetail();

        appReqBlock.getHttpServletRequest().setAttribute("jumpToTarget", appReqBlock.getReqParm("jumpToTarget"));

        return JUMP_TO_DIALOG;
    }

    protected String showDeleteClientConfirmationDialog(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ClientUseCaseComponent().deleteClient();

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailPK", appReqBlock.getReqParm("clientDetailPK"));
        appReqBlock.getHttpServletRequest().setAttribute("clientId", appReqBlock.getReqParm("clientId"));

        return DELETE_CLIENT_CONFIRMATION_DIALOG;
    }

    protected String showCancelClientConfirmationDialog(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("clientDetailPK", appReqBlock.getReqParm("clientDetailPK"));
        appReqBlock.getHttpServletRequest().setAttribute("clientId", appReqBlock.getReqParm("clientId"));

        return CANCEL_CLIENT_CONFIRMATION_DIALOG;
    }

//    protected String showQACancelClientConfirmationDialog(AppReqBlock appReqBlock) throws Exception
//    {
//        appReqBlock.getHttpServletRequest().setAttribute("clientDetailPK", appReqBlock.getReqParm("clientDetailPK"));
//        appReqBlock.getHttpServletRequest().setAttribute("clientId", appReqBlock.getReqParm("clientId"));
//
//        return CANCEL_QA_CLIENT_CONFIRM_DIAL0G;
//    }

    protected String lockClient(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ClientUseCaseComponent().updateClient();

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        long clientDetailPK = userSession.getClientDetailPK();

        try
        {
            userSession.lockClientDetail(clientDetailPK);
        }
        catch (EDITLockException e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("errorMessage", e.getMessage());
        }

        return showSelectedClient(appReqBlock);
    }

    protected String addNewClient(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ClientUseCaseComponent().addNewClient();

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.lockClientDetail(0);

        clearAllClientSessionBeans(appReqBlock);

        return CLIENT_DETAIL;
    }

    protected String showClientDetails(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

        return CLIENT_DETAIL;
    }

    protected String showClientAddress(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        SessionBean clientAddressSessionBean = appReqBlock.getSessionBean("clientAddressSessionBean");

        Map addressBeans = clientAddressSessionBean.getPageBeans();
        Iterator addressBeansEnum = addressBeans.values().iterator();

        while (addressBeansEnum.hasNext())
        {
            PageBean addressBean = (PageBean) addressBeansEnum.next();
            String addressType = addressBean.getValue("addressTypeId");

            String terminationDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(addressBean.getValue("terminationDate"));

            EDITDate termDate;

            if (terminationDate.equals(""))
            {
                termDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
            }
            else
            {
                termDate = new EDITDate(terminationDate);
            }

            EDITDate currentDate = new EDITDate();

            if (addressType.equalsIgnoreCase("PrimaryAddress") && (termDate.after(currentDate) || termDate.equals(currentDate)))
            {
                addressBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
                appReqBlock.getHttpServletRequest().setAttribute("pageBean", addressBean);
            }
        }

        return CLIENT_ADDRESS;
    }

    protected String showClientHistory(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        new ClientHistorySummaryTableModel(appReqBlock);

        return CLIENT_HISTORY;
    }

    protected String showHistoryDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        String key = appReqBlock.getReqParm("selectedRowIds_ClientHistorySummaryTableModel");
        ChangeHistory changeHistory = ChangeHistory.findByPK(new Long(key));

        appReqBlock.getHttpServletRequest().setAttribute("changeHistory", changeHistory);

        SessionHelper.evict(changeHistory, SessionHelper.EDITSOLUTIONS);

        new ClientHistorySummaryTableModel(appReqBlock);

        return CLIENT_HISTORY;
    }

//    protected String showQAClientAddress(AppReqBlock appReqBlock) throws Exception
//    {
//        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
//        String currentPage = stateBean.getValue("currentPage");
//
//        savePreviousPageFormBean(appReqBlock, currentPage);
//
//        SessionBean clientAddressSessionBean = appReqBlock.getSessionBean("clientAddressSessionBean");
//
//        Map addressBeans = clientAddressSessionBean.getPageBeans();
//        Iterator addressBeansEnum = addressBeans.values().iterator();
//
//        while (addressBeansEnum.hasNext())
//        {
//            PageBean addressBean = (PageBean) addressBeansEnum.next();
//
//            if (addressBean.getValue("addressTypeId").equalsIgnoreCase("PrimaryAddress"))
//            {
//                addressBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
//                appReqBlock.getHttpServletRequest().setAttribute("pageBean", addressBean);
//            }
//        }
//
//        return QA_CLIENT_ADDRESS;
//    }

    protected String showClientTaxInformation(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ClientUseCaseComponent().accessTaxes();

        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage"); 

        savePreviousPageFormBean(appReqBlock, currentPage);

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

        return CLIENT_TAX_INFORMATION;
    }

    protected String showClientBankInformation(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

        return CLIENT_BANK_INFORMATION;
    }

    protected String showClientPreferences(AppReqBlock appReqBlock) throws Exception
    {
        // Check for authorization
        new ClientUseCaseComponent().accessPreference();

        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

        return CLIENT_PREFERENCE_INFO;
    }

    protected String loadClientAfterSearch(AppReqBlock appReqBlock) throws Exception
    {
        String searchClientDetailPK = appReqBlock.getReqParm("searchValue");

        appReqBlock.getHttpSession().setAttribute("searchValue", searchClientDetailPK);

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.setClientDetailPK(Long.parseLong(appReqBlock.getReqParm("searchValue")));

        return MAIN_FRAMESET;
    }

    protected String showSelectedClient(AppReqBlock appReqBlock) throws Exception
    {
        clearAllClientSessionBeans(appReqBlock);

        SessionBean clientDetailSessionBean = appReqBlock.getSessionBean("clientDetailSessionBean");
        SessionBean clientAddressSessionBean = appReqBlock.getSessionBean("clientAddressSessionBean");
        SessionBean taxInformationSessionBean = appReqBlock.getSessionBean("taxInformationSessionBean");
        SessionBean preferenceSessionBean = appReqBlock.getSessionBean("preferenceSessionBean");
        SessionBean contactInfoSessionBean = appReqBlock.getSessionBean("contactInfoSessionBean");

        String clientDetailPK = appReqBlock.getFormBean().getValue("clientDetailPK");

        if (clientDetailPK.equals(""))
        {
            UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");
            clientDetailPK = userSession.getClientDetailPK() + "";
        }

        PageBean clientDetailPageBean = new PageBean();

        Lookup lookup = new client.component.LookupComponent();

        List voExclusionList = new ArrayList();
        voExclusionList.add(RedirectVO.class);
        voExclusionList.add(ClientSetupVO.class);
        voExclusionList.add(ContractClientVO.class);
        voExclusionList.add(ClientRoleFinancialVO.class);
        voExclusionList.add(ReinsurerVO.class);
        voExclusionList.add(AgentVO.class);
        voExclusionList.add(CommissionHistoryVO.class);

        ClientDetailVO[] clientDetailVO = null;

        if (!clientDetailPK.equals(""))
        {
            clientDetailVO = lookup.findClientDetailByClientPK(Long.parseLong(clientDetailPK), true, voExclusionList);

            //Only one will ever be returned
        }

        if (clientDetailVO != null)
        {
            clientDetailPageBean.putValue("clientDetailPK", clientDetailVO[0].getClientDetailPK() + "");
            clientDetailPageBean.putValue("clientId", clientDetailVO[0].getClientIdentification());
            clientDetailPageBean.putValue("taxId", clientDetailVO[0].getTaxIdentification() + "");
            clientDetailPageBean.putValue("namePrefix", clientDetailVO[0].getNamePrefix());
            clientDetailPageBean.putValue("nameSuffix", clientDetailVO[0].getNameSuffix());
            clientDetailPageBean.putValue("firstName", clientDetailVO[0].getFirstName());
            clientDetailPageBean.putValue("middleName", clientDetailVO[0].getMiddleName());
            clientDetailPageBean.putValue("lastName", clientDetailVO[0].getLastName());
            clientDetailPageBean.putValue("corporateName", clientDetailVO[0].getCorporateName());

            String dateOfBirth = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVO[0].getBirthDate());//converted for JSP page display
            clientDetailPageBean.putValue("dateOfBirth", dateOfBirth);

            clientDetailPageBean.putValue("genderId", clientDetailVO[0].getGenderCT());
            clientDetailPageBean.putValue("trustTypeId", clientDetailVO[0].getTrustTypeCT());
            clientDetailPageBean.putValue("status", clientDetailVO[0].getStatusCT());
            clientDetailPageBean.putValue("lastChangeDateTime", clientDetailVO[0].getMaintDateTime());
            clientDetailPageBean.putValue("lastChangeOperator", clientDetailVO[0].getOperator());
            clientDetailPageBean.putValue("mothersMaidenName", clientDetailVO[0].getMothersMaidenName());
            clientDetailPageBean.putValue("occupation", clientDetailVO[0].getOccupation());
            clientDetailPageBean.putValue("lastOFACCheckDate", clientDetailVO[0].getLastOFACCheckDate());
            clientDetailPageBean.putValue("sicCode", clientDetailVO[0].getSICCodeCT());

            String privacyIndStatus = clientDetailVO[0].getPrivacyInd();

            if ((privacyIndStatus != null) && privacyIndStatus.equalsIgnoreCase("Y"))
            {
                privacyIndStatus = "checked";
            }
            else
            {
                privacyIndStatus = "unchecked";
            }

            clientDetailPageBean.putValue("privacyIndStatus", privacyIndStatus);

            String dateOfDeath = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVO[0].getDateOfDeath());//converted for JSP page display
            clientDetailPageBean.putValue("dateOfDeath",dateOfDeath);

            String proofOfDeathReceivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVO[0].getProofOfDeathReceivedDate());//converted for JSP page display
            clientDetailPageBean.putValue("proofOfDeath",proofOfDeathReceivedDate);

            clientDetailPageBean.putValue("stateOfDeathId", clientDetailVO[0].getStateOfDeathCT());
            clientDetailPageBean.putValue("residentStateAtDeathId", clientDetailVO[0].getResidentStateAtDeathCT());
            clientDetailPageBean.putValue("caseTrackingProcess", clientDetailVO[0].getCaseTrackingProcess());
            clientDetailPageBean.putValue("notificationReceivedDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVO[0].getNotificationReceivedDate()));
            clientDetailPageBean.putValue("companyFK", clientDetailVO[0].getCompanyFK() + "");

            if (clientDetailVO[0].getContactInformationVOCount() > 0)
            {
                clientDetailPageBean.putValue("contactInfoIndStatus", "checked");
            }
            else
            {
                clientDetailPageBean.putValue("contactInfoIndStatus", "unchecked");
            }

            //display the fields on the RoleTable
            ClientRoleVO clientRoleVO = getClientOwnerRole(clientDetailVO[0]);
            appReqBlock.getHttpSession().setAttribute("clientRoleVO", clientRoleVO);

            clientDetailSessionBean.putPageBean("pageBean", clientDetailPageBean);

            ContactInformationVO[] contactInformationVOs = clientDetailVO[0].getContactInformationVO();

            if (contactInformationVOs != null)
            {
                for (int i = 0; i < contactInformationVOs.length; i++)
                {
                    PageBean contactInfoPageBean = new PageBean();

                    long contactInformationPK = contactInformationVOs[i].getContactInformationPK();
                    String contactTypeCT = contactInformationVOs[i].getContactTypeCT();

                    contactInfoPageBean.putValue("contactInformationPK", contactInformationPK + "");
                    contactInfoPageBean.putValue("clientDetailFK", clientDetailPK + "");
                    contactInfoPageBean.putValue("contactTypeCT", Util.initString(contactInformationVOs[i].getContactTypeCT(), ""));
                    contactInfoPageBean.putValue("phoneEmail", Util.initString(contactInformationVOs[i].getPhoneEmail(), ""));
                    contactInfoPageBean.putValue("name", Util.initString(contactInformationVOs[i].getName(), ""));

                    contactInfoSessionBean.putPageBean(contactInformationPK + "" + contactTypeCT, contactInfoPageBean);
                }
            }

            //Address Layer
            ClientAddressVO[] clientAddresses = clientDetailVO[0].getClientAddressVO();

            if (clientAddresses != null)
            {
                for (int a = 0; a < clientAddresses.length; a++)
                {
                    if (clientAddresses[a].getOverrideStatus() == null)
                    {
                        PageBean addressPageBean = new PageBean();

                        long clientAddressPK = clientAddresses[a].getClientAddressPK();
                        int sequenceNumber = clientAddresses[a].getSequenceNumber();
                        String addressType = clientAddresses[a].getAddressTypeCT();

                        addressPageBean.putValue("clientDetailFK", clientDetailPK + "");
                        addressPageBean.putValue("clientAddressPK", clientAddressPK + "");
                        addressPageBean.putValue("addressTypeId", addressType);
                        addressPageBean.putValue("sequenceNumber", sequenceNumber + "");

                        //  Format the date to be mm/dd/yyyy for the page
                        String effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientAddresses[a].getEffectiveDate());
                        addressPageBean.putValue("effectiveDate", effectiveDate);

                        String terminationDate = clientAddresses[a].getTerminationDate();

                        if (terminationDate == null)
                        {
                            terminationDate = EDITDate.DEFAULT_MAX_DATE;
                        }
                        //  Format the date to be mm/dd/yyyy for the page
                        terminationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(terminationDate);

                        addressPageBean.putValue("terminationDate", terminationDate);

                        String startDate = clientAddresses[a].getStartDate();

                        if (startDate == null)
                        {
                            startDate = ClientAddress.defaultStartDate();
                        }

                        addressPageBean.putValue("startDate", startDate);
                        addressPageBean.putValue("startMonth", ClientAddress.getStartDateMonth(startDate));
                        addressPageBean.putValue("startDay", ClientAddress.getStartDateDay(startDate));

                        String stopDate = clientAddresses[a].getStopDate();

                        if (stopDate == null)
                        {
                            stopDate = ClientAddress.defaultStopDate();
                        }

                        addressPageBean.putValue("stopDate", stopDate);
                        addressPageBean.putValue("stopMonth", ClientAddress.getStopDateMonth(stopDate));
                        addressPageBean.putValue("stopDay", ClientAddress.getStopDateDay(stopDate));

                        addressPageBean.putValue("addressLine1", clientAddresses[a].getAddressLine1());
                        addressPageBean.putValue("addressLine2", clientAddresses[a].getAddressLine2());
                        addressPageBean.putValue("addressLine3", clientAddresses[a].getAddressLine3());
                        addressPageBean.putValue("addressLine4", clientAddresses[a].getAddressLine4());
                        addressPageBean.putValue("county", clientAddresses[a].getCounty());
                        addressPageBean.putValue("city", clientAddresses[a].getCity());
                        addressPageBean.putValue("areaId", clientAddresses[a].getStateCT());
                        addressPageBean.putValue("countryId", clientAddresses[a].getCountryCT());
                        addressPageBean.putValue("zipCode", clientAddresses[a].getZipCode());
                        addressPageBean.putValue("zipCodePositionId", clientAddresses[a].getZipCodePlacementCT());
                
                        addressPageBean.putValue("maintDateTime", clientAddresses[a].getMaintDateTime());
                        addressPageBean.putValue("operator", clientAddresses[a].getOperator());

                        clientAddressSessionBean.putPageBean(clientAddressPK + "" + addressType, addressPageBean);
                    }
                }
            }

            //Taxes Layer
            TaxInformationVO[] taxInformationVO = clientDetailVO[0].getTaxInformationVO();

            PageBean taxPageBean = new PageBean();

            taxPageBean.putValue("clientDetailFK", clientDetailPK + "");

            if ((taxInformationVO != null) && (taxInformationVO.length > 0))
            {
                taxPageBean.putValue("taxInformationPK", taxInformationVO[0].getTaxInformationPK() + "");
                taxPageBean.putValue("taxTypeId", taxInformationVO[0].getTaxIdTypeCT());
                clientDetailPageBean.putValue("taxTypeId", taxInformationVO[0].getTaxIdTypeCT());
                taxPageBean.putValue("proofOfAgeId", taxInformationVO[0].getProofOfAgeIndCT());
                taxPageBean.putValue("maritalStatusId", taxInformationVO[0].getMaritalStatusCT());
                taxPageBean.putValue("stateOfBirthId", taxInformationVO[0].getStateOfBirthCT());
                taxPageBean.putValue("countryOfBirthId", taxInformationVO[0].getCountryOfBirthCT());
                taxPageBean.putValue("usCitizenId", taxInformationVO[0].getCitizenshipIndCT());

                TaxProfileVO[] taxProfileVOs = taxInformationVO[0].getTaxProfileVO();

                if (taxProfileVOs != null)
                {
                    for (int t = 0; t < taxProfileVOs.length; t++)
                    {
                        String overrideStatus = Util.initString(taxProfileVOs[t].getOverrideStatus(), "P");
                        if (overrideStatus.equalsIgnoreCase("P"))
                        {
                            long taxProfilePK = taxProfileVOs[t].getTaxProfilePK();
                            String taxFilingStatus = taxProfileVOs[t].getTaxFilingStatusCT();

                            if (taxFilingStatus == null)
                            {
                                taxFilingStatus = "";
                            }

                            String exemptions = taxProfileVOs[t].getExemptions();

                            if (exemptions == null)
                            {
                                exemptions = "";
                            }

                            String taxIndicator = taxProfileVOs[t].getTaxIndicatorCT();

                            if (taxIndicator == null)
                            {
                                taxIndicator = "";
                            }

                            String ficaIndicator = taxProfileVOs[t].getFicaIndicator();

                            if ((ficaIndicator != null) && ficaIndicator.equalsIgnoreCase("Y"))
                            {
                                ficaIndicator = "checked";
                            }
                            else
                            {
                                ficaIndicator = "unchecked";
                            }

                            taxPageBean.putValue("taxProfilePK", taxProfilePK + "");
                            taxPageBean.putValue("filingStatusId", taxFilingStatus);
                            taxPageBean.putValue("exemptions", exemptions);
                            taxPageBean.putValue("taxIndicator", taxIndicator);
                            taxPageBean.putValue("ficaIndicatorStatus", ficaIndicator);
                        }
                    }
                }
            }

            taxInformationSessionBean.putPageBean("pageBean", taxPageBean);

            PreferenceVO[] preferenceVO = clientDetailVO[0].getPreferenceVO();

            if (preferenceVO != null)
            {
                for (int p = 0; p < preferenceVO.length; p++)
                {
                    PageBean preferencePageBean = new PageBean();

                    preferencePageBean.putValue("clientDetailFK", clientDetailPK + "");
                    preferencePageBean.putValue("preferencePK", preferenceVO[p].getPreferencePK() + "");
                    preferencePageBean.putValue("printAs", Util.initString(preferenceVO[p].getPrintAs(), ""));
                    preferencePageBean.putValue("printAs2", Util.initString(preferenceVO[p].getPrintAs2(), ""));
                    preferencePageBean.putValue("disbursementSource", Util.initString(preferenceVO[p].getDisbursementSourceCT(), ""));
                    preferencePageBean.putValue("paymentMode", Util.initString(preferenceVO[p].getPaymentModeCT(), ""));
                    preferencePageBean.putValue("minimumCheck", preferenceVO[p].getMinimumCheck() + "");
                    preferencePageBean.putValue("bankAccountNumber", Util.initString(preferenceVO[p].getBankAccountNumber(), ""));
                    preferencePageBean.putValue("bankRoutingNumber", Util.initString(preferenceVO[p].getBankRoutingNumber(), ""));
                    preferencePageBean.putValue("bankAccountType", Util.initString(preferenceVO[p].getBankAccountTypeCT(), ""));
                    preferencePageBean.putValue("bankName", Util.initString(preferenceVO[p].getBankName(), ""));
                    preferencePageBean.putValue("bankAddressLine1", Util.initString(preferenceVO[p].getBankAddressLine1(), ""));
                    preferencePageBean.putValue("bankAddressLine2", Util.initString(preferenceVO[p].getBankAddressLine2(), ""));
                    preferencePageBean.putValue("bankCity", Util.initString(preferenceVO[p].getBankCity(), ""));
                    preferencePageBean.putValue("bankState", Util.initString(preferenceVO[p].getBankStateCT(), ""));
                    preferencePageBean.putValue("bankZipCode", Util.initString(preferenceVO[p].getBankZipCode(), ""));

                    preferencePageBean.putValue("overrideStatus", Util.initString(preferenceVO[p].getOverrideStatus(), ""));
                    preferencePageBean.putValue("preferenceType", Util.initString(preferenceVO[p].getPreferenceTypeCT(), ""));

                    preferenceSessionBean.putPageBean(preferenceVO[p].getPreferencePK() + "", preferencePageBean);
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailPK", clientDetailPK);

        return CLIENT_DETAIL;
    }

    private ClientRoleVO getClientOwnerRole(ClientDetailVO clientDetailVO)
    {
        ClientRoleVO[] clientRoleVOs = clientDetailVO.getClientRoleVO();
        ClientRoleVO clientRoleVO = null;

        for (int i = 0; i < clientRoleVOs.length; i++)
        {
            if (clientRoleVOs[i].getRoleTypeCT().equalsIgnoreCase("OWN"))
            {
                clientRoleVO = clientRoleVOs[i];
            }
        }

        return clientRoleVO;
    }

    protected void preProcessRequest(AppReqBlock appReqBlock) throws Exception
    {
    }

    protected void postProcessRequest(AppReqBlock appReqBlock) throws Exception
    {
    }

    protected String showAddressDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        setAddressDetail(appReqBlock);

        return CLIENT_ADDRESS;
    }

//    protected String showQAAddressDetailSummary(AppReqBlock appReqBlock) throws Exception
//    {
//        setAddressDetail(appReqBlock);
//
//        return QA_CLIENT_ADDRESS;
//    }

    /**
     * checks OFAC for all clients
     * method added by sprasad for OFAC-SOAP 08/3/04
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String checkOFACForAllClients(AppReqBlock appReqBlock) throws Exception
    {
        Batch batch = new BatchComponent();

        batch.ofacCheckOnAllClients(null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");
        
        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    private void setAddressDetail(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();

        formBean.putValue("clientDetailFK", formBean.getValue("clientDetailPK"));

        String selectedAddressType = formBean.getValue("selectedAddressType");
        String selectedAddressLine1 = formBean.getValue("selectedAddressLine1");
        String selectedClientAddressPK = formBean.getValue("selectedClientAddressPK");
        int endingIndex = selectedClientAddressPK.indexOf("_");
        selectedClientAddressPK = selectedClientAddressPK.substring(0, endingIndex);

        String key = selectedClientAddressPK + selectedAddressType;

        SessionBean clientAddressSessionBean = appReqBlock.getSessionBean("clientAddressSessionBean");

        PageBean addressPageBean = clientAddressSessionBean.getPageBean(key);

        formBean.putValue("clientAddressPK", addressPageBean.getValue("clientAddressPK"));
        formBean.putValue("addressTypeId", addressPageBean.getValue("addressTypeId"));

        formBean.putValue("effectiveDate", addressPageBean.getValue("effectiveDate"));
        formBean.putValue("terminationDate", addressPageBean.getValue("terminationDate"));

        formBean.putValue("sequenceNumber", addressPageBean.getValue("sequenceNumber"));
        formBean.putValue("startMonth", addressPageBean.getValue("startMonth"));
        formBean.putValue("startDay", addressPageBean.getValue("startDay"));
        formBean.putValue("stopMonth", addressPageBean.getValue("stopMonth"));
        formBean.putValue("stopDay", addressPageBean.getValue("stopDay"));
        formBean.putValue("addressLine1", addressPageBean.getValue("addressLine1"));
        formBean.putValue("addressLine2", addressPageBean.getValue("addressLine2"));
        formBean.putValue("addressLine3", addressPageBean.getValue("addressLine3"));
        formBean.putValue("addressLine4", addressPageBean.getValue("addressLine4"));
        formBean.putValue("city", addressPageBean.getValue("city"));
        formBean.putValue("county", addressPageBean.getValue("county"));
        formBean.putValue("areaId", addressPageBean.getValue("areaId"));
        formBean.putValue("countryId", addressPageBean.getValue("countryId"));
        formBean.putValue("zipCode", addressPageBean.getValue("zipCode"));
        formBean.putValue("zipCodePositionId", addressPageBean.getValue("zipCodePositionId"));
        formBean.putValue("operator", addressPageBean.getValue("operator"));
        formBean.putValue("maintDateTime", addressPageBean.getValue("maintDateTime"));

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);
    }

    protected String loadClientDetails(AppReqBlock appReqBlock) throws Exception
    {
        return CLIENT_DETAIL;
    }

    protected String saveClientDetails(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        if (!ignoreEditWarnings.equalsIgnoreCase("true"))
        {
            savePreviousPageFormBean(appReqBlock, currentPage);
        }

        UtilitiesForTran.setupRecordPRASEEvents(appReqBlock, appReqBlock.getFormBean().getValue("recordPRASEEvents"));

        String clientMessage = "";
        Lookup clientLookup = new client.component.LookupComponent();

        //Client Details
        PageBean clientDetailPageBean = appReqBlock.getSessionBean("clientDetailSessionBean").getPageBean("pageBean");
        String clientDetailPK = clientDetailPageBean.getValue("clientDetailPK");
        String clientId = clientDetailPageBean.getValue("clientId");
        String taxId = clientDetailPageBean.getValue("taxId");
        String taxTypeId = clientDetailPageBean.getValue("taxTypeId");

        String trustTypeId    = clientDetailPageBean.getValue("trustTypeId");
        if (taxId.equals(""))
        {
            taxId = "00000000000";
        }

        //Tax ids where duplicates ar allowed
        boolean duplicatesTaxIdsAllowed = false;
        if (taxId.equals("00000000000") || taxId.equals("999999999"))
        {
            duplicatesTaxIdsAllowed = true;
        }

        ClientDetailVO[] taxIdClientDetail = null;

        if (!duplicatesTaxIdsAllowed)
        {
            List voExclusionList = new ArrayList();
            voExclusionList.add(ClientAddressVO.class);
            voExclusionList.add(PreferenceVO.class);
            voExclusionList.add(TaxProfileVO.class);
            voExclusionList.add(ClientRoleVO.class);

            taxIdClientDetail = clientLookup.getClientByTaxId(taxId, true, voExclusionList);
        }

        if ((taxIdClientDetail != null) && !duplicatesTaxIdsAllowed)
        {
            boolean noTaxErrors = true;

            for (int d = 0; d < taxIdClientDetail.length; d++)
            {
                TaxInformationVO[] taxInformationVOs = taxIdClientDetail[d].getTaxInformationVO();

                if ((taxInformationVOs != null) && (taxInformationVOs.length > 0))
                {
                    String taxIdTypeCT = taxInformationVOs[0].getTaxIdTypeCT();
                    String trustType = taxIdClientDetail[d].getTrustTypeCT();

                    if (trustType != null)
                    {
                        if (trustType.equalsIgnoreCase(trustTypeId) &&
                            !(taxIdClientDetail[d].getClientDetailPK() + "").equals(clientDetailPK))
                        {
                            clientMessage = "Tax ID already exists.";
                            appReqBlock.getHttpServletRequest().setAttribute("clientMessage", clientMessage);
                            appReqBlock.getFormBean().putValue("clientDetailPK", clientDetailPK);
                            noTaxErrors = false;

                            break;
                        }
                    }
                }
            }

            if (noTaxErrors)
            {
                try
                {
                    saveAndUnlockClient(appReqBlock, clientDetailPK, clientId, clientDetailPageBean);
                }
                catch (OFACBindException e)
                {
                    System.out.println("123:" + e.getMessage());
                    appReqBlock.getHttpServletRequest().setAttribute("errorMessage", EditOFACCheck.OFAC_SERVICE_DOWN_MESSAGE);

                    return CLIENT_DETAIL;

                    /*                    if( e != null && e.getMessage().indexOf("OFAC.wsdl") != -1) {
                                          appReqBlock.getHttpServletRequest().
                                                  setAttribute("errorMessage", "OFAC webservice down! Please Bypass OFAC");
                                          return CLIENT_DETAIL;
                                        }else{
                                            throw e;
                                        }*/
                }

                return showClientDetails(appReqBlock);
            }
        }
        else
        {
            try
            {
                saveAndUnlockClient(appReqBlock, clientDetailPK, clientId, clientDetailPageBean);
            }
            catch (OFACBindException e)
            {
                System.out.println("456:" + e.getMessage());
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", EditOFACCheck.OFAC_SERVICE_DOWN_MESSAGE);

                return CLIENT_DETAIL;

                /*                    if( e != null && e.getMessage().indexOf("OFAC.wsdl") != -1) {
                                      appReqBlock.getHttpServletRequest().
                                              setAttribute("errorMessage", "OFAC webservice down! Please Bypass OFAC");
                                      return CLIENT_DETAIL;
                                    }else{
                                        throw e;
                                    }*/
            }

            return showClientDetails(appReqBlock);
        }

        return CLIENT_DETAIL;
    }

    private void saveAndUnlockClient(AppReqBlock appReqBlock, String clientDetailPK, String clientId, PageBean clientDetailPageBean) throws Exception
    {
        PortalEditingException editingException = null;

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null) ? "" : ignoreEditWarnings;

        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        String operator = userSession.getUsername();

        ClientDetailVO clientDetailVO = buildClientDetailVO(appReqBlock, clientDetailPK, clientId, operator);

        if (!ignoreEditWarnings.equalsIgnoreCase("true"))
        {
            try
            {
                validateClient(clientDetailVO);
            }
            catch (PortalEditingException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

                throw e;
            }
        }

        if (editingException != null)
        {
            throw editingException;
        }
        else
        {
            //            Client clientComp = (Client) appReqBlock.getWebService("client-service");
            client.business.Client clientComp = new client.component.ClientComponent();

            EDITDate eligibilityStartDateED = null;
            String eligibilityStatus = Util.initString(clientDetailPageBean.getValue("eligibilityStatusId"), null);
            String eligibilityStartDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientDetailPageBean.getValue("eligibilityStartDate"));

            if (eligibilityStartDate != null)
            {
                eligibilityStartDateED = new EDITDate(eligibilityStartDate);
            }
            else if (eligibilityStatus != null)
            {
                eligibilityStartDateED = new EDITDate();
            }

            clientDetailPK = clientComp.saveOrUpdateClient(clientDetailVO, eligibilityStatus, eligibilityStartDateED,
                                                            ((clientDetailPageBean.getValue("bypassOFAC") != null) && clientDetailPageBean.getValue("bypassOFAC").trim().equalsIgnoreCase("checked")) ? true : false) + "";

//            updateRoleForeignKeys(Long.parseLong(clientDetailPK), appReqBlock);

            appReqBlock.getFormBean().putValue("clientDetailPK", clientDetailPK);

            userSession.unlockClientDetail();

            clearAllClientSessionBeans(appReqBlock);
        }
    }


    private ClientDetailVO buildClientDetailVO(AppReqBlock appReqBlock, String clientDetailPK, String clientId, String operator) throws Exception
    {
        ClientDetailVO clientDetailVO = new ClientDetailVO();

        if (!clientDetailPK.equals(""))
        {
            clientDetailVO.setClientDetailPK(Long.parseLong(clientDetailPK));
        }
        else
        {
            clientDetailVO.setClientDetailPK(0);
        }

        clientDetailVO.setClientIdentification(Util.initString(clientId, null));
        clientDetailVO.setOperator(Util.initString(operator, null));

        getAndSetMainDetail(appReqBlock, clientDetailVO);
        getAndSetContactInfo(appReqBlock, clientDetailVO);
        getAndSetAddresses(appReqBlock, clientDetailVO);
        getAndSetTaxes(appReqBlock, clientDetailVO);
        getAndSetPreference(appReqBlock, clientDetailVO);

        return clientDetailVO;
    }

    private void getAndSetMainDetail(AppReqBlock appReqBlock, ClientDetailVO clientDetailVO)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        SessionBean clientDetailSessionBean = appReqBlock.getSessionBean("clientDetailSessionBean");

        //Client Details
        PageBean clientDetailPageBean = clientDetailSessionBean.getPageBean("pageBean");

        String taxId = clientDetailPageBean.getValue("taxId");

        if (taxId.equals(""))
        {
            taxId = "00000000000";
        }

        String dateOfBirth = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientDetailPageBean.getValue("dateOfBirth"));
        String notificationReceivedDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientDetailPageBean.getValue("notificationReceivedDate"));

        EDITDateTime lastChangeDateTime = new EDITDateTime();
        clientDetailPageBean.putValue("lastChangeDateTime", lastChangeDateTime.getFormattedDateTime());

        clientDetailPageBean.putValue("lastChangeOperator", clientDetailVO.getOperator());

        String dateOfDeath = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientDetailPageBean.getValue("dateOfDeath"));

        if (dateOfDeath == null)
        {
            dateOfDeath = EDITDate.DEFAULT_MAX_DATE;
        }

        String proofOfDeath = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(clientDetailPageBean.getValue("proofOfDeath"));

        if (proofOfDeath == null)
        {
            proofOfDeath = EDITDate.DEFAULT_MAX_DATE;
        }

        String status = clientDetailPageBean.getValue("status");

        if (!dateOfDeath.equals(EDITDate.DEFAULT_MAX_DATE))
        {
            status = "Deceased";
        }
        else if (status.equalsIgnoreCase(""))
        {
            status = "Active";
        }

        clientDetailPageBean.putValue("status", status);

        String privacyIndStatus = clientDetailPageBean.getValue("privacyIndStatus");

        if (privacyIndStatus.equalsIgnoreCase("checked"))
        {
            privacyIndStatus = "Y";
        }
        else
        {
            privacyIndStatus = "N";
        }

        String sicCode = clientDetailPageBean.getValue("sicCode");

        clientDetailVO.setTaxIdentification(Util.initString(taxId, null));
        clientDetailVO.setLastName(Util.initString(clientDetailPageBean.getValue("lastName"), null));
        clientDetailVO.setFirstName(Util.initString(clientDetailPageBean.getValue("firstName"), null));
        clientDetailVO.setMiddleName(Util.initString(clientDetailPageBean.getValue("middleName"), null));
        clientDetailVO.setNamePrefix(Util.initString(clientDetailPageBean.getValue("namePrefix"), null));
        clientDetailVO.setNameSuffix(Util.initString(clientDetailPageBean.getValue("nameSuffix"), null));
        clientDetailVO.setCorporateName(Util.initString(clientDetailPageBean.getValue("corporateName"), null));
        clientDetailVO.setBirthDate(dateOfBirth);
        clientDetailVO.setGenderCT(Util.initString(clientDetailPageBean.getValue("genderId"), null));
        clientDetailVO.setTrustTypeCT(Util.initString(clientDetailPageBean.getValue("trustTypeId"), null));
        clientDetailVO.setMothersMaidenName(Util.initString(clientDetailPageBean.getValue("mothersMaidenName"), null));
        clientDetailVO.setOccupation(Util.initString(clientDetailPageBean.getValue("occupation"), null));
        clientDetailVO.setDateOfDeath(dateOfDeath);
        clientDetailVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        clientDetailVO.setStatusCT(Util.initString(status, null));
        clientDetailVO.setPrivacyInd(Util.initString(privacyIndStatus, null));
        clientDetailVO.setStateOfDeathCT(Util.initString(clientDetailPageBean.getValue("stateOfDeathId"), null));
        clientDetailVO.setResidentStateAtDeathCT(Util.initString(clientDetailPageBean.getValue("residentStateAtDeathId"), null));
        clientDetailVO.setProofOfDeathReceivedDate(proofOfDeath);
        clientDetailVO.setCaseTrackingProcess(Util.initString(clientDetailPageBean.getValue("caseTrackingProcess"), null));
        clientDetailVO.setNotificationReceivedDate(notificationReceivedDate);
        clientDetailVO.setSICCodeCT(Util.initString(sicCode, null));
        clientDetailVO.setCompanyFK(Long.parseLong(clientDetailPageBean.getValue("companyFK")));
    }

    private void getAndSetAddresses(AppReqBlock appReqBlock, ClientDetailVO clientDetailVO) throws Exception
    {
        SessionBean clientAddressSessionBean = appReqBlock.getSessionBean("clientAddressSessionBean");

        //Client Address Information
        Map addressBeans = clientAddressSessionBean.getPageBeans();

        if (addressBeans != null)
        {
            client.business.Lookup clientLookup = new client.component.LookupComponent();

            long clientDetailPK = clientDetailVO.getClientDetailPK();
            ClientAddressVO[] tempClientAddresses = null;

            if (clientDetailPK > 0)
            {
                List voExclusionList = new ArrayList();
                voExclusionList.add(ClientRoleVO.class);

                ClientDetailVO[] tempClientDetail = clientLookup.findByClientPK(clientDetailPK, true, voExclusionList);

                if ((tempClientDetail != null) && (tempClientDetail.length > 0))
                {
                    tempClientAddresses = tempClientDetail[0].getClientAddressVO();
                }
            }

            Iterator addressEnum = addressBeans.values().iterator();

            int newSeqNbr = 1;

            while (addressEnum.hasNext())
            {
                PageBean addressPageBean = (PageBean) addressEnum.next();

                String clientAddressPK = addressPageBean.getValue("clientAddressPK");
                if (Long.parseLong(clientAddressPK) < 0)
                {
                    clientAddressPK = "0";
                }

                String addressTypeId = addressPageBean.getValue("addressTypeId");

                String effectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(addressPageBean.getValue("effectiveDate"));

                String terminationDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(addressPageBean.getValue("terminationDate"));

                if (terminationDate == null)
                {
                    terminationDate = EDITDate.DEFAULT_MAX_DATE;
                }

                String sequenceNumber = addressPageBean.getValue("sequenceNumber");

                if (sequenceNumber.equals(""))
                {
                    int existingSeqNbr = 0;

                    if (tempClientAddresses != null)
                    {
                        for (int t = 0; t < tempClientAddresses.length; t++)
                        {
                            existingSeqNbr = tempClientAddresses[t].getSequenceNumber();

                            if (existingSeqNbr >= newSeqNbr)
                            {
                                newSeqNbr = existingSeqNbr + 1;
                            }
                        }
                    }

                    sequenceNumber = newSeqNbr + "";
                    newSeqNbr += 1;
                }

                String startMonth = addressPageBean.getValue("startMonth");
                String startDay = addressPageBean.getValue("startDay");
                String stopMonth = addressPageBean.getValue("stopMonth");
                String stopDay = addressPageBean.getValue("stopDay");
                String startDate = ClientAddress.defaultStartDate();
                String stopDate = ClientAddress.defaultStopDate();

                if (!startMonth.equals("") && !startDay.equals(""))
                {
                    startDate = ClientAddress.buildStartDate(startMonth, startDay);
                }

                if (!stopMonth.equals("") && !stopDay.equals(""))
                {
                    stopDate = ClientAddress.buildStopDate(stopMonth, stopDay);
                }

                ClientAddressVO clientAddressVO = new ClientAddressVO();

                if (Util.isANumber(clientAddressPK) && (Long.parseLong(clientAddressPK) > 0))
                {
                    clientAddressVO.setClientAddressPK(Long.parseLong(clientAddressPK));
                }
                else
                {
                    clientAddressVO.setClientAddressPK(0);
                }

                String areaId = addressPageBean.getValue("areaId");

                if (areaId.equalsIgnoreCase("Please Select"))
                {
                    areaId = null;
                }

                String countryId = addressPageBean.getValue("countryId");

                if (countryId.equalsIgnoreCase("Please Select"))
                {
                    countryId = null;
                }

                String zipCodePositionId = addressPageBean.getValue("zipCodePositionId");

                if (zipCodePositionId.equalsIgnoreCase("Please Select"))
                {
                    zipCodePositionId = null;
                }
                
                clientAddressVO.setClientDetailFK(clientDetailVO.getClientDetailPK());
                clientAddressVO.setAddressTypeCT(Util.initString(addressTypeId, null));
                clientAddressVO.setEffectiveDate(effectiveDate);
                clientAddressVO.setTerminationDate(terminationDate);
                clientAddressVO.setSequenceNumber(Integer.parseInt(sequenceNumber));
                clientAddressVO.setStartDate(startDate);
                clientAddressVO.setStopDate(stopDate);
                clientAddressVO.setAddressLine1(Util.initString(addressPageBean.getValue("addressLine1"), null));
                clientAddressVO.setAddressLine2(Util.initString(addressPageBean.getValue("addressLine2"), null));
                clientAddressVO.setAddressLine3(Util.initString(addressPageBean.getValue("addressLine3"), null));
                clientAddressVO.setAddressLine4(Util.initString(addressPageBean.getValue("addressLine4"), null));
                clientAddressVO.setCounty(Util.initString(addressPageBean.getValue("county"), null));
                clientAddressVO.setCity(Util.initString(addressPageBean.getValue("city"), null));
                clientAddressVO.setStateCT(Util.initString(areaId, null));
                clientAddressVO.setCountryCT(Util.initString(countryId, null));
                clientAddressVO.setZipCode(Util.initString(addressPageBean.getValue("zipCode"), null));
                clientAddressVO.setZipCodePlacementCT(Util.initString(zipCodePositionId, null));
                //Only set operator and maint dateTime on add
                if (clientAddressVO.getClientAddressPK() == 0)
                {
                    clientAddressVO.setOperator(clientDetailVO.getOperator());
                    clientAddressVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
                }
                else
                {
                   clientAddressVO.setOperator(Util.initString(addressPageBean.getValue("operator"), null));
                    clientAddressVO.setMaintDateTime(Util.initString(addressPageBean.getValue("maintDateTime"), null));
                }

                clientDetailVO.addClientAddressVO(clientAddressVO);
            }
        }
    }

    private void getAndSetContactInfo(AppReqBlock appReqBlock, ClientDetailVO clientDetailVO) throws Exception
    {
        SessionBean contactInfoSessionBean = appReqBlock.getSessionBean("contactInfoSessionBean");

        //Contact Information
        Map contactBeans = contactInfoSessionBean.getPageBeans();

        if (contactBeans != null)
        {
            client.business.Lookup clientLookup = new client.component.LookupComponent();

            long clientDetailPK = clientDetailVO.getClientDetailPK();
            ContactInformationVO[] tempContactInfo = null;

            if (clientDetailPK > 0)
            {
                List voExclusionList = new ArrayList();
                voExclusionList.add(ClientRoleVO.class);

                ClientDetailVO[] tempClientDetail = clientLookup.findByClientPK(clientDetailPK, true, voExclusionList);

                if ((tempClientDetail != null) && (tempClientDetail.length > 0))
                {
                    tempContactInfo = tempClientDetail[0].getContactInformationVO();
                }
            }

            Iterator it = contactBeans.values().iterator();

            int newSeqNbr = 1;

            while (it.hasNext())
            {
                PageBean contactPageBean = (PageBean) it.next();

                String contactInformationPK = contactPageBean.getValue("contactInformationPK");
                if (Long.parseLong(contactInformationPK) < 0)
                {
                    contactInformationPK = "0";
                }

                String contactTypeCT = contactPageBean.getValue("contactTypeCT");
                String name = contactPageBean.getValue("name");
                String phoneEmail = contactPageBean.getValue("phoneEmail");

                ContactInformationVO contactInformationVO = new ContactInformationVO();

                if (Util.isANumber(contactInformationPK) && (Long.parseLong(contactInformationPK) > 0))
                {
                    contactInformationVO.setContactInformationPK(Long.parseLong(contactInformationPK));
                }
                else
                {
                    contactInformationVO.setContactInformationPK(0);
                }

                contactInformationVO.setClientDetailFK(clientDetailVO.getClientDetailPK());
                contactInformationVO.setContactTypeCT(Util.initString(contactTypeCT, null));
                contactInformationVO.setName(name);
                contactInformationVO.setPhoneEmail(phoneEmail);
                clientDetailVO.addContactInformationVO(contactInformationVO);
            }
        }
    }

    private void getAndSetTaxes(AppReqBlock appReqBlock, ClientDetailVO clientDetailVO)
    {
        SessionBean taxInformationSessionBean = appReqBlock.getSessionBean("taxInformationSessionBean");

        //Client Tax Information
        PageBean taxPageBean = taxInformationSessionBean.getPageBean("pageBean");

        String taxInformationPK = taxPageBean.getValue("taxInformationPK");
        String taxProfilePK = taxPageBean.getValue("taxProfilePK");
        String ficaIndicator = taxPageBean.getValue("ficaIndicatorStatus");

        if (ficaIndicator.equalsIgnoreCase("checked"))
        {
            ficaIndicator = "Y";
        }
        else
        {
            ficaIndicator = "N";
        }

        TaxInformationVO taxInformationVO = new TaxInformationVO();
        TaxProfileVO taxProfileVO = new TaxProfileVO();

        if (Util.isANumber(taxInformationPK))
        {
            taxInformationVO.setTaxInformationPK(Long.parseLong(taxInformationPK));
        }
        else
        {
            taxInformationVO.setTaxInformationPK(0);
        }

        taxInformationVO.setClientDetailFK(clientDetailVO.getClientDetailPK());
        taxInformationVO.setTaxIdTypeCT(Util.initString(taxPageBean.getValue("taxTypeId"), null));
        taxInformationVO.setProofOfAgeIndCT(Util.initString(taxPageBean.getValue("proofOfAgeId"), null));
        taxInformationVO.setMaritalStatusCT(Util.initString(taxPageBean.getValue("maritalStatusId"), null));
        taxInformationVO.setStateOfBirthCT(Util.initString(taxPageBean.getValue("stateOfBirthId"), null));
        taxInformationVO.setCountryOfBirthCT(Util.initString(taxPageBean.getValue("countryOfBirthId"), null));
        taxInformationVO.setCitizenshipIndCT(Util.initString(taxPageBean.getValue("usCitizenId"), null));

        if (Util.isANumber(taxProfilePK))
        {
            taxProfileVO.setTaxProfilePK(Long.parseLong(taxProfilePK));
        }
        else
        {
            taxProfileVO.setTaxProfilePK(0);
        }

        taxProfileVO.setTaxInformationFK(taxInformationVO.getTaxInformationPK());
        taxProfileVO.setTaxFilingStatusCT(Util.initString(taxPageBean.getValue("filingStatusId"), null));
        taxProfileVO.setExemptions(Util.initString(taxPageBean.getValue("exemptions"), null));
        taxProfileVO.setTaxIndicatorCT(Util.initString(taxPageBean.getValue("taxIndicator"), null));
        taxProfileVO.setFicaIndicator(ficaIndicator);
        taxProfileVO.setOverrideStatus("P");

        taxInformationVO.addTaxProfileVO(taxProfileVO);

        clientDetailVO.addTaxInformationVO(taxInformationVO);
    }

    private void getAndSetPreference(AppReqBlock appReqBlock, ClientDetailVO clientDetailVO)
    {
        SessionBean preferenceSessionBean = appReqBlock.getSessionBean("preferenceSessionBean");

        //Client Preferences
        Map preferenceBeans = preferenceSessionBean.getPageBeans();

        if (!preferenceBeans.isEmpty())
        {
            Iterator preferenceEnum = preferenceBeans.values().iterator();

            while (preferenceEnum.hasNext())
            {
                PageBean preferencePageBean = (PageBean) preferenceEnum.next();

                String preferencePK = preferencePageBean.getValue("preferencePK");

                PreferenceVO preferenceVO = checkPreferenceFields(preferencePageBean, clientDetailVO);

                if (preferenceVO != null)
                {
                    if (preferencePK.startsWith("-"))
                    {
                        preferencePK = "";
                    }
                    if (Util.isANumber(preferencePK))
                    {
                        preferenceVO.setPreferencePK(Long.parseLong(preferencePK));
                    }

                    clientDetailVO.addPreferenceVO(preferenceVO);
                }
            }
        }
    }

    private PreferenceVO checkPreferenceFields(PageBean preferencePageBean, ClientDetailVO clientDetailVO)
    {
        String preferencePK = Util.initString(preferencePageBean.getValue("preferencePK"), "0");
        String printAs = Util.initString(preferencePageBean.getValue("printAs"), null);
        String printAs2 = Util.initString(preferencePageBean.getValue("printAs2"), null);
        String disbursementSource = Util.initString(preferencePageBean.getValue("disbursementSource"), null);
        String paymentMode = Util.initString(preferencePageBean.getValue("paymentMode"), null);
        String preferenceType = Util.initString(preferencePageBean.getValue("preferenceType"), null);
        String bankAccountNumber = Util.initString(preferencePageBean.getValue("bankAccountNumber"), null);
        String bankRoutingNumber = Util.initString(preferencePageBean.getValue("bankRoutingNumber"), null);
        String bankAccountType = Util.initString(preferencePageBean.getValue("bankAccountType"), null);
        String bankName = Util.initString(preferencePageBean.getValue("bankName"), null);
        String bankAddressLine1 = Util.initString(preferencePageBean.getValue("bankAddressLine1"), null);
        String bankAddressLine2 = Util.initString(preferencePageBean.getValue("bankAddressLine2"), null);
        String bankCity = Util.initString(preferencePageBean.getValue("bankCity"), null);
        String bankState = Util.initString(preferencePageBean.getValue("bankState"), null);
        String bankZipCode = Util.initString(preferencePageBean.getValue("bankZipCode"), null);
        String overrideStatus = Util.initString(preferencePageBean.getValue("overrideStatus"), "P");
		String minimumCheck = Util.initString(preferencePageBean.getValue("minimumCheck"), "0");

        boolean doNotCreatePreference = false;
        EDITBigDecimal minCheck;
        PreferenceVO preferenceVO = null;

        minCheck = new EDITBigDecimal(minimumCheck);

        if ((printAs == null) && (printAs2 == null) && (disbursementSource == null) && (paymentMode == null) && (minCheck.isEQ("0")))
        {
            // do not create preference if fields are null and is a new prefence
            if (Long.parseLong(preferencePK) == 0L)
            {
                doNotCreatePreference = true;
            }
        }

        if (!doNotCreatePreference)
        {
            preferenceVO = new PreferenceVO();

            preferenceVO.setMinimumCheck(minCheck.getBigDecimal());
            preferenceVO.setClientDetailFK(clientDetailVO.getClientDetailPK());
            preferenceVO.setPrintAs(printAs);
            preferenceVO.setPrintAs2(printAs2);
            preferenceVO.setDisbursementSourceCT(disbursementSource);
            preferenceVO.setPaymentModeCT(paymentMode);
            preferenceVO.setPreferencePK(0);
            preferenceVO.setPreferenceTypeCT(preferenceType);
            preferenceVO.setOverrideStatus(overrideStatus);
            preferenceVO.setBankAccountNumber(bankAccountNumber);
            preferenceVO.setBankRoutingNumber(bankRoutingNumber);
            preferenceVO.setBankAccountTypeCT(bankAccountType);
            preferenceVO.setBankName(bankName);
            preferenceVO.setBankAddressLine1(bankAddressLine1);
            preferenceVO.setBankAddressLine2(bankAddressLine2);
            preferenceVO.setBankCity(bankCity);
            preferenceVO.setBankStateCT(bankState);
            preferenceVO.setBankZipCode(bankZipCode);
        }

        return preferenceVO;
    }

//    protected String quickAddSave(AppReqBlock appReqBlock) throws Exception
//    {
//        return saveClientDetails(appReqBlock);
//    }

//    protected String quickAddLogout(AppReqBlock appReqBlock) throws Exception
//    {
//        appReqBlock.getHttpServletRequest().setAttribute("quickAddLogout", "true");
//        clearAllClientSessionBeans(appReqBlock);
//
//        return CLIENT_DETAIL;
//    }

    protected String saveAddressToSummary(AppReqBlock appReqBlock) throws Exception
    {
        saveAddress(appReqBlock);

        return CLIENT_ADDRESS;
    }

//    protected String saveQAAddressToSummary(AppReqBlock appReqBlock) throws Exception
//    {
//        saveAddress(appReqBlock);
//
//        return QA_CLIENT_ADDRESS;
//    }

    private void saveAddress(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        SessionBean clientAddressSessionBean = appReqBlock.getSessionBean("clientAddressSessionBean");
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableVO codeTableVO = null;

        formBean.putValue("clientDetailFK", formBean.getValue("clientDetailPK"));

        String addressTypeId = formBean.getValue("addressTypeId");

        if (Util.isANumber(addressTypeId))
        {
            codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(addressTypeId));
            addressTypeId = codeTableVO.getCode();
            formBean.putValue("addressTypeId", addressTypeId);
        }

        String clientAddressPK = formBean.getValue("clientAddressPK");
        if (!Util.isANumber(clientAddressPK))
        {
            client.business.Client clientComponent = new client.component.ClientComponent();
            clientAddressPK = (clientComponent.getNextAvailableKey() * -1) + "";
        }
        
         
        String effectiveDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("effectiveDate"));
        if(effectiveDate == null || effectiveDate.equals(""))
        {
            effectiveDate = EDITDate.DEFAULT_MIN_DATE;
        }
        
        String terminationDate = DateTimeUtil.formatMMDDYYYYToYYYYMMDD(formBean.getValue("terminationDate"));

        String sequenceNumber = formBean.getValue("sequenceNumber");
        String startMonth = formBean.getValue("startMonth");
        String startDay = formBean.getValue("startDay");
        String stopMonth = formBean.getValue("stopMonth");
        String stopDay = formBean.getValue("stopDay");
        String areaId = formBean.getValue("areaId");

        if (Util.isANumber(areaId))
        {
            codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(areaId));
            areaId = codeTableVO.getCode();
            formBean.putValue("areaId", areaId);
        }

        String countryId = formBean.getValue("countryId");

        if (Util.isANumber(countryId))
        {
            codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(countryId));
            countryId = codeTableVO.getCode();
            formBean.putValue("countryId", countryId);
        }

        String zipCodePositionId = formBean.getValue("zipCodePositionId");

        if (Util.isANumber(zipCodePositionId))
        {
            codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(zipCodePositionId));
            zipCodePositionId = codeTableVO.getCode();
            formBean.putValue("zipCodePositionId", zipCodePositionId);
        }

        String key = clientAddressPK + addressTypeId;

        PageBean addressPageBean = null;

        Map clientPageBeans = clientAddressSessionBean.getPageBeans();
        String keyToRemove = null;

        if (!clientPageBeans.isEmpty())
        {
            Iterator it = clientPageBeans.keySet().iterator();

            while (it.hasNext())
            {
                String pageBeanKey = (String) it.next();

                if (pageBeanKey.startsWith(clientAddressPK))
                {
                    keyToRemove = pageBeanKey;
                }
            }
        }

        if (keyToRemove != null)
        {
            addressPageBean = clientAddressSessionBean.getPageBean(keyToRemove);

            clientAddressSessionBean.removePageBean(keyToRemove);
        }
        else
        {
            addressPageBean = new PageBean();
        }

        if (clientAddressPK.equals(""))
        {
            client.business.Client clientComponent = new client.component.ClientComponent();
            clientAddressPK = (clientComponent.getNextAvailableKey() * -1) + "";

            key = clientAddressPK + addressTypeId;
        }

        addressPageBean.putValue("clientAddressPK", clientAddressPK);
        addressPageBean.putValue("addressTypeId", addressTypeId);
        addressPageBean.putValue("sequenceNumber", sequenceNumber);

        
        addressPageBean.putValue("effectiveDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate));

        addressPageBean.putValue("terminationDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(terminationDate));

        addressPageBean.putValue("startMonth", startMonth);
        addressPageBean.putValue("startDay", startDay);

        String startDate = ClientAddress.defaultStartDate();

        if (!startMonth.equals(""))
        {
            startDate = ClientAddress.buildStartDate(startMonth, startDay);
        }

        addressPageBean.putValue("startDate", startDate);

        addressPageBean.putValue("stopMonth", stopMonth);
        addressPageBean.putValue("stopDay", stopDay);

        String stopDate = ClientAddress.defaultStopDate();

        if (!stopMonth.equals(""))
        {
            stopDate = ClientAddress.buildStopDate(stopMonth, stopDay);
        }

        addressPageBean.putValue("stopDate", stopDate);

        addressPageBean.putValue("addressLine1", formBean.getValue("addressLine1"));
        addressPageBean.putValue("addressLine2", formBean.getValue("addressLine2"));
        addressPageBean.putValue("addressLine3", formBean.getValue("addressLine3"));
        addressPageBean.putValue("addressLine4", formBean.getValue("addressLine4"));
        addressPageBean.putValue("city", formBean.getValue("city"));
        addressPageBean.putValue("county", formBean.getValue("county"));
        addressPageBean.putValue("areaId", areaId);
        addressPageBean.putValue("countryId", countryId);
        addressPageBean.putValue("zipCode", formBean.getValue("zipCode"));
        addressPageBean.putValue("zipCodePositionId", zipCodePositionId);
        addressPageBean.putValue("operator", formBean.getValue("operator"));
        addressPageBean.putValue("maintDateTime", formBean.getValue("maintDateTime"));

        clientAddressSessionBean.putPageBean(key, addressPageBean);

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", addressPageBean);
    }

    protected String deleteClientDetails(AppReqBlock appReqBlock) throws Exception
    {
//        String clientDetailPK = appReqBlock.getReqParm("clientDetailPK");
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");
        long clientDetailPK = userSession.getClientDetailPK();

        if (clientDetailPK != 0)
        {
            try
            {
                role.business.Lookup roleLookup = new role.component.LookupComponent();
                ClientRoleVO[] clientRoleVOs = roleLookup.getRolesByClientDetailFK(clientDetailPK);

                if (clientRoleVOs != null)
                {
                    appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Cannot Delete Client - Role(s) exist");

                    return showSelectedClient(appReqBlock);
                }
                else
                {
                    Client client = (Client) appReqBlock.getWebService("client-service");

                    client.deleteClient(clientDetailPK);

                    appReqBlock.getSessionBean("clientAddressSessionBean").clearState();
                    appReqBlock.getSessionBean("clientDetailSessionBean").clearState();
                    appReqBlock.getSessionBean("taxInformationSessionBean").clearState();
                    appReqBlock.getSessionBean("preferenceSessionBean").clearState();
                    appReqBlock.getSessionBean("contactInfoSessionBean").clearState();

                    return CLIENT_DETAIL;
                }
            }
            catch (EDITDeleteException e)
            {
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", e.getMessage());

                return showSelectedClient(appReqBlock);
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        else
        {
            return CLIENT_DETAIL;
        }
    }

    protected String deleteSelectedAddress(AppReqBlock appReqBlock) throws Exception
    {
        deleteAddress(appReqBlock);

        return CLIENT_ADDRESS;
    }

//    protected String deleteSelectedQAAddress(AppReqBlock appReqBlock) throws Exception
//    {
//        deleteAddress(appReqBlock);
//
//        return QA_CLIENT_ADDRESS;
//    }

    private void deleteAddress(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();

        formBean.putValue("clientDetailFK", formBean.getValue("clientDetailPK"));

        String selectedAddressType = formBean.getValue("selectedAddressType");
        String selectedAddressLine1 = formBean.getValue("selectedAddressLine1");
        String selectedClientAddressPK = formBean.getValue("selectedClientAddressPK");
        int endingIndex = selectedClientAddressPK.indexOf("_");
        selectedClientAddressPK = selectedClientAddressPK.substring(0, endingIndex);

        String key = selectedClientAddressPK + selectedAddressType;

        SessionBean clientAddressSessionBean = appReqBlock.getSessionBean("clientAddressSessionBean");

        clientAddressSessionBean.removePageBean(key);

        formBean.putValue("clientAddressPK", "");
        formBean.putValue("addressTypeId", "");
        formBean.putValue("effectiveDate", "");

        formBean.putValue("terminationDate", "");

        formBean.putValue("sequenceNumber", "");
        formBean.putValue("startMonth", "");
        formBean.putValue("startDay", "");
        formBean.putValue("stopMonth", "");
        formBean.putValue("stopDay", "");
        formBean.putValue("addressLine1", "");
        formBean.putValue("addressLine2", "");
        formBean.putValue("addressLine3", "");
        formBean.putValue("addressLine4", "");
        formBean.putValue("city", "");
        formBean.putValue("county", "");
        formBean.putValue("areaId", "");
        formBean.putValue("countryId", "");
        formBean.putValue("zipCode", "");
        formBean.putValue("zipCodePositionId", "");
        formBean.putValue("operator", "");
        formBean.putValue("maintDateTime", "");

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);
    }

    protected String cancelClientDetails(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        userSession.unlockClientDetail();

        clearAllClientSessionBeans(appReqBlock);

        return CLIENT_DETAIL;
    }

    private String clearAddressForAddOrCancel(AppReqBlock appReqBlock) throws Exception
    {
        return CLIENT_ADDRESS;
    }

//    private String clearQAAddressForAddOrCancel(AppReqBlock appReqBlock) throws Exception
//    {
//        return QA_CLIENT_ADDRESS;
//    }

    private String clearContactForAddOrCancel(AppReqBlock appReqBlock)
    {
        return CONTACT_INFO_DIALOG;
    }

    protected String saveContactToSummary(AppReqBlock appReqBlock) throws Exception
    {
        saveContact(appReqBlock);

        return CONTACT_INFO_DIALOG;
    }

    private void saveContact(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        SessionBean contactInfoSessionBean = appReqBlock.getSessionBean("contactInfoSessionBean");
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableVO codeTableVO = null;

        formBean.putValue("clientDetailFK", formBean.getValue("clientDetailPK"));

        String contactTypeCT = formBean.getValue("contactTypeId");

        if (Util.isANumber(contactTypeCT))
        {
            codeTableVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(contactTypeCT));
            contactTypeCT = codeTableVO.getCode();
            formBean.putValue("contactTypeCT", contactTypeCT);
        }

        String contactInformationPK = formBean.getValue("contactInformationPK");
        if (!Util.isANumber(contactInformationPK))
        {
            client.business.Client clientComponent = new client.component.ClientComponent();
            contactInformationPK = (clientComponent.getNextAvailableKey() * -1) + "";
        }

        String name = formBean.getValue("name");
        String phoneEmail = formBean.getValue("phoneEmail");

        String key = contactInformationPK + contactTypeCT;

        PageBean contactPageBean = null;

        Map contactPageBeans = contactInfoSessionBean.getPageBeans();
        String keyToRemove = null;

        if (!contactPageBeans.isEmpty())
        {
            Iterator it = contactPageBeans.keySet().iterator();

            while (it.hasNext())
            {
                String pageBeanKey = (String) it.next();

                if (pageBeanKey.startsWith(contactInformationPK))
                {
                    keyToRemove = pageBeanKey;
                }
            }
        }

        if (keyToRemove != null)
        {
            contactPageBean = contactInfoSessionBean.getPageBean(keyToRemove);

            contactInfoSessionBean.removePageBean(keyToRemove);
        }
        else
        {
            contactPageBean = new PageBean();
        }

        if (contactInformationPK.equals(""))
        {
            client.business.Client clientComponent = new client.component.ClientComponent();
            contactInformationPK = (clientComponent.getNextAvailableKey() * -1) + "";

            key = contactInformationPK + contactTypeCT;
        }

        contactPageBean.putValue("contactInformationPK", contactInformationPK);
        contactPageBean.putValue("clientDetailFK", formBean.getValue("clientDetailPK"));
        contactPageBean.putValue("contactTypeCT", contactTypeCT);
        contactPageBean.putValue("name", name);
        contactPageBean.putValue("phoneEmail", phoneEmail);

        contactInfoSessionBean.putPageBean(key, contactPageBean);

        SessionBean clientDetailSessionBean = appReqBlock.getSessionBean("clientDetailSessionBean");
        PageBean clientDetailPageBean = clientDetailSessionBean.getPageBean("pageBean");
        clientDetailPageBean.putValue("contactInfoIndStatus", "checked");

        clientDetailSessionBean.putPageBean("pageBean", clientDetailPageBean);
    }

    protected String deleteSelectedContact(AppReqBlock appReqBlock) throws Exception
    {
        deleteContact(appReqBlock);

        return CONTACT_INFO_DIALOG;
    }

    private void deleteContact(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();

        formBean.putValue("clientDetailFK", formBean.getValue("clientDetailPK"));

        String selectedContactType = formBean.getValue("selectedContactType");
        String selectedContactInformationPK = formBean.getValue("selectedContactInformationPK");
        int endingIndex = selectedContactInformationPK.indexOf("_");
        selectedContactInformationPK = selectedContactInformationPK.substring(0, endingIndex);

        String key = selectedContactInformationPK + selectedContactType;

        SessionBean contactInfoSessionBean = appReqBlock.getSessionBean("contactInfoSessionBean");

        contactInfoSessionBean.removePageBean(key);

        formBean.putValue("contactInformationPK", "");
        formBean.putValue("contactTypeCT", "");
        formBean.putValue("name", "");
        formBean.putValue("phoneEmail", "");

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);

        SessionBean clientDetailSessionBean = appReqBlock.getSessionBean("clientDetailSessionBean");
        PageBean clientDetailPageBean = clientDetailSessionBean.getPageBean("pageBean");
        if (contactInfoSessionBean.hasPageBeans())
        {
            clientDetailPageBean.putValue("contactInfoIndStatus", "checked");
        }
        else
        {
            clientDetailPageBean.putValue("contactInfoIndStatus", "unchecked");
        }

        clientDetailSessionBean.putPageBean("pageBean", clientDetailPageBean);
    }

    protected String showContactInfoDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        setContactDetail(appReqBlock);

        return CONTACT_INFO_DIALOG;
    }

    protected String closeContactInfoDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        stateBean.putValue("previousPage", currentPage);
        stateBean.putValue("currentPage", CLIENT_DETAIL);

        return CLIENT_DETAIL;
    }

    private void setContactDetail(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();

        formBean.putValue("clientDetailFK", formBean.getValue("clientDetailPK"));

        String selectedContactType = formBean.getValue("selectedContactType");
        String selectedContactInformationPK = formBean.getValue("selectedContactInformationPK");
        int endingIndex = selectedContactInformationPK.indexOf("_");
        selectedContactInformationPK = selectedContactInformationPK.substring(0, endingIndex);

        String key = selectedContactInformationPK + selectedContactType;

        SessionBean contactInfoSessionBean = appReqBlock.getSessionBean("contactInfoSessionBean");

        PageBean contactInfoPageBean = contactInfoSessionBean.getPageBean(key);

        formBean.putValue("contactInformationPK", contactInfoPageBean.getValue("contactInformationPK"));
        formBean.putValue("contactTypeCT", contactInfoPageBean.getValue("contactTypeCT"));
        formBean.putValue("name", contactInfoPageBean.getValue("name"));
        formBean.putValue("phoneEmail", contactInfoPageBean.getValue("phoneEmail"));

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", formBean);
    }

    //    private void validateVO(ClientDetailVO clientVO,  AppReqBlock appReqBlock, PageBean pageBean) throws Exception, VOEditException
    //    {
    //        Editing editingComp = (Editing) appReqBlock.getWebService("editing-service");
    //        String[] VOMessages = null;
    //        String[] clientMessages = null;
    //        String[] messages = null;
    //
    //        VOMessages = editingComp.validateVO(clientVO);    // birth date error????
    //
    //        clientMessages = performAdditionalEdits(clientVO);
    //
    //        messages = joinArrays(VOMessages, clientMessages);
    //
    //        if (messages != null)
    //        {
    //            VOEditException editException = new VOEditException();
    //            editException.setVOErrors(messages);
    //            appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);
    //            editException.setReturnPage(CLIENT_DETAIL);
    //
    //            throw editException;
    //        }
    //    }
    private String[] joinArrays(String[] voMessages, String[] clientMessages)
    {
        if (voMessages == null)
        {
            voMessages = new String[0];
        }

        String[] messages = new String[(voMessages.length + clientMessages.length)];

        if (voMessages != null)
        {
            for (int i = 0; i < voMessages.length; i++)
            {
                messages[i] = voMessages[i];
            }
        }

        int j = voMessages.length;

        if (clientMessages != null)
        {
            for (int k = 0; k < clientMessages.length; k++)
            {
                messages[j] = clientMessages[k];

                j++;
            }
        }

        if (messages.length == 0)
        {
            return null;
        }
        else
        {
            return messages;
        }
    }

    protected String showVOEditExceptionDialog(AppReqBlock appReqBlock) throws Exception
    {
//        VOEditException voEditException = (VOEditException) appReqBlock.getHttpSession().getAttribute("VOEditException");
//
//        // Remove voEditException from Session (to clear it), and move it to request scope.
//        appReqBlock.getHttpSession().removeAttribute("VOEditException");
//
//        appReqBlock.getHttpServletRequest().setAttribute("VOEditException", voEditException);

        return VO_EDIT_EXCEPTION_DIALOG;
    }

    private String[] performAdditionalEdits(ClientDetailVO clientVO)
    {
        List messageList = new ArrayList();

        String messageFormat = "ClientDetailVO:";

        if (!Util.isANumber(clientVO.getTaxIdentification()))
        {
            messageList.add(messageFormat + "Tax Identification:" + "Tax Id Must Be Numeric");
        }

        PreferenceVO[] preferenceVOs = clientVO.getPreferenceVO();

        for (int i = 0; i < preferenceVOs.length; i++)
        {
            String bankAccountNumber = preferenceVOs[i].getBankAccountNumber();
            String bankRoutingNumber = preferenceVOs[i].getBankRoutingNumber();

            if ((bankAccountNumber != null) && !bankAccountNumber.equals(""))
            {
                if (!Util.isANumber(bankAccountNumber))
                {
                    messageList.add(messageFormat + "Bank Account Number:" + "Bank Account Number Must Be Numeric");
                }
            }

            if (bankRoutingNumber != null)
            {
                if (!bankRoutingNumber.equals(""))
                {
                    if (bankRoutingNumber.length() != 9)
                    {
                        messageList.add(messageFormat + "Bank Routing Number:" + "Bank Routing Number Must Be 9 Positions");
                    }

                    if (!Util.isANumber(bankRoutingNumber))
                    {
                        messageList.add(messageFormat + "Bank Routing Number:" + "Bank Routing Number Must Be Numeric");
                    }
                }
            }

            String disbursementSource = preferenceVOs[i].getDisbursementSourceCT();

            if (disbursementSource != null)
            {
                if (disbursementSource.equalsIgnoreCase("EFT") &&
                    (preferenceVOs[i].getBankAccountNumber() == null ||
                     preferenceVOs[i].getBankRoutingNumber() == null ||
                     preferenceVOs[i].getBankAccountTypeCT() == null))
                {
                     messageList.add(messageFormat + "Bank Information Missing for EFT Disbursement");
                }
            }
        }

        return (String[]) (messageList.toArray(new String[messageList.size()]));
    }

    /**
     * Validates the client information
     *
     * @param clientDetailVO
     * @throws SPException
     * @throws PortalEditingException
     */
    private void validateClient(ClientDetailVO clientDetailVO) throws SPException, PortalEditingException
    {
        //  Convert the fluffy VO to a fluffy hibernate object in order to validate
        ClientDetail clientDetail = ClientDetail.buildFluffyClientDetailFromVO(clientDetailVO);

        SPOutput spOutput = clientDetail.validateClient();

        ValidationVO[] validationVOs = spOutput.getSPOutputVO().getValidationVO();

        if (spOutput.hasValidationOutputs())
        {
            PortalEditingException editingException = new PortalEditingException();
            editingException.setValidationVOs(validationVOs);
            editingException.setReturnPage(CLIENT_DETAIL);
            
//            logValidateClientSave(clientDetailVO, validationVOs, appReqBlock);

            throw editingException;
        }
    }

    protected String showEditingExceptionDialog(AppReqBlock appReqBlock) throws Exception
    {
        PortalEditingException editingException = (PortalEditingException) appReqBlock.getHttpSession().getAttribute("portalEditingException");

        // Remove editingException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("portalEditingException");

        appReqBlock.getHttpServletRequest().setAttribute("portalEditingException", editingException);

        return EDITING_EXCEPTION_DIALOG;
    }

    protected String showContactInfoDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

        PageBean pageBean = new PageBean();
        pageBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

        return CONTACT_INFO_DIALOG;
    }

    private void savePreviousPageFormBean(AppReqBlock appReqBlock, String previousPage) throws Exception
    {
        if (previousPage.equals(CLIENT_DETAIL) || previousPage.equals(EDITING_EXCEPTION_DIALOG) || previousPage.equals(VO_EDIT_EXCEPTION_DIALOG))
        {
            saveClientMainPage(appReqBlock);
        }
//        else if (previousPage.equals(CLIENT_TAX_INFORMATION))
//        {
//            saveClientTaxPage(appReqBlock);
//        }
//        else if (previousPage.equals(CLIENT_PREFERENCE_INFO))
//        {
//            saveBankInformationPage(appReqBlock);
//            savePreferencePage(appReqBlock);
//        }
    }

    private void saveClientMainPage(AppReqBlock appReqBlock)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();
        PageBean clientDetailPageBean = appReqBlock.getSessionBean("clientDetailSessionBean").getPageBean("pageBean");


        String trustTypeId = formBean.getValue("trustTypeId");

        if (Util.isANumber(trustTypeId))
        {
            trustTypeId = codeTableWrapper.getCodeTableEntry(Long.parseLong(trustTypeId)).getCode();
        }
        else if (trustTypeId.equalsIgnoreCase("Please Select"))
        {
            trustTypeId = "";
        }

        formBean.putValue("trustTypeId", trustTypeId);

        String taxTypeId = formBean.getValue("taxTypeId");

        if (Util.isANumber(taxTypeId))
        {
            taxTypeId = codeTableWrapper.getCodeTableEntry(Long.parseLong(taxTypeId)).getCode();
        }
        else if (taxTypeId.equalsIgnoreCase("Please Select"))
        {
            taxTypeId = "";
        }

        formBean.putValue("taxTypeId", taxTypeId);

        PageBean taxPageBean = appReqBlock.getSessionBean("taxInformationSessionBean").getPageBean("pageBean");
        taxPageBean.putValue("taxTypeId", taxTypeId);

        String genderId = Util.initString(formBean.getValue("genderId"), "");

        if (Util.isANumber(genderId))
        {
            genderId = codeTableWrapper.getCodeTableEntry(Long.parseLong(genderId)).getCode();
        }
        else if (genderId.equalsIgnoreCase("Please Select"))
        {
            genderId = "";
        }

        formBean.putValue("genderId", genderId);

        String sicCode = formBean.getValue("sicCode");

        if (Util.isANumber(sicCode))
        {
            sicCode = codeTableWrapper.getCodeTableEntry(Long.parseLong(sicCode)).getCode();
        }
        else if (sicCode.equalsIgnoreCase("Please Select"))
        {
            sicCode = "";
        }

        formBean.putValue("sicCode", sicCode);

        String status = formBean.getValue("status");

        if (Util.isANumber(status))
        {
            status = codeTableWrapper.getCodeTableEntry(Long.parseLong(status)).getCode();
        }
        else if (status.equalsIgnoreCase("Please Select"))
        {
            status = "";
        }

        formBean.putValue("status", status);

        formBean.putValue("bypassOFAC", appReqBlock.getReqParm("bypassOFAC"));

        //        formBean.putValue("bypassOFAC",formBean.getValue("bypassOFAC"));
        String stateOfDeath = formBean.getValue("stateOfDeathId");

        if (Util.isANumber(stateOfDeath))
        {
            stateOfDeath = codeTableWrapper.getCodeTableEntry(Long.parseLong(stateOfDeath)).getCode();
        }
        else if (stateOfDeath.equalsIgnoreCase("Please Select"))
        {
            stateOfDeath = "";
        }

        formBean.putValue("stateOfDeathId", stateOfDeath);

        String residentStateAtDeath = formBean.getValue("residentStateAtDeathId");

        if (Util.isANumber(residentStateAtDeath))
        {
            residentStateAtDeath = codeTableWrapper.getCodeTableEntry(Long.parseLong(residentStateAtDeath)).getCode();
        }
        else if (residentStateAtDeath.equalsIgnoreCase("Please Select"))
        {
            residentStateAtDeath = "";
        }

        formBean.putValue("residentStateAtDeathId", residentStateAtDeath);

        String eligibilityStatus = formBean.getValue("eligibilityStatusId");
        if (eligibilityStatus.equalsIgnoreCase("Please Select"))
        {
            eligibilityStatus = "";
        }

        String companyFK = Util.initString(formBean.getValue("companyFK"), "0");

        formBean.putValue("eligibilityStatusId", eligibilityStatus);
        formBean.putValue("eligibilityStartDate", formBean.getValue("eligibilityStartDate"));
        formBean.putValue("caseTrackingProcess", clientDetailPageBean.getValue("caseTrackingProcess"));
        formBean.putValue("notificationReceivedDate", clientDetailPageBean.getValue("notificationReceivedDate"));
        formBean.putValue("companyFK", companyFK);

        appReqBlock.getSessionBean("clientDetailSessionBean").putPageBean("pageBean", formBean);
    }

    public String saveClientTaxPage(AppReqBlock appReqBlock)
    {
        new ClientUseCaseComponent().updateTaxes();

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        PageBean formBean = appReqBlock.getFormBean();

        String proofOfAgeId = Util.initString(formBean.getValue("proofOfAgeId"), "");

        formBean.putValue("proofOfAgeId", proofOfAgeId);

        String maritalStatusId = Util.initString(formBean.getValue("maritalStatusId"), "");

        formBean.putValue("maritalStatusId", maritalStatusId);

        String filingStatusId = Util.initString(formBean.getValue("filingStatusId"), "");

        formBean.putValue("filingStatusId", filingStatusId);

        String taxIndicator = Util.initString(formBean.getValue("taxIndicator"), "");


        formBean.putValue("taxIndicator", taxIndicator);

        String stateOfBirthId = Util.initString(formBean.getValue("stateOfBirthId"), "");


        formBean.putValue("stateOfBirthId", stateOfBirthId);

        String countryOfBirthId = Util.initString(formBean.getValue("countryOfBirthId"), "");

        formBean.putValue("countryOfBirthId", countryOfBirthId);

        String usCitizenId = Util.initString(formBean.getValue("usCitizenId"), "");

        formBean.putValue("usCitizenId", usCitizenId);
        formBean.putValue("exemptions", Util.initString(formBean.getValue("exemptions"), ""));
        formBean.putValue("ficaIndicatorStatus", Util.initString(formBean.getValue("ficaIndicatorStatus"), ""));

        appReqBlock.getSessionBean("taxInformationSessionBean").putPageBean("pageBean", formBean);

        return CLIENT_DETAIL;
    }

    private String savePreference(AppReqBlock appReqBlock)
    {
        new ClientUseCaseComponent().updatePreference();

        PageBean formBean = appReqBlock.getFormBean();

        boolean primaryPreferenceFound = false;

        String preferencePK = Util.initString(formBean.getValue("preferencePK"), "0");
        String overrideStatus = Util.initString(formBean.getValue("overrideStatus"), "");
        String preferenceType = Util.initString(formBean.getValue("preferenceType"), "");

        if (preferencePK.equals("") || preferencePK.equals("0"))
        {
            SessionBean preferenceSessionBean = appReqBlock.getSessionBean("preferenceSessionBean");
            Map preferencePageBeans = preferenceSessionBean.getPageBeans();
            Set preferenceKeys = preferencePageBeans.keySet();
            Iterator it = preferenceKeys.iterator();
            while (it.hasNext())
            {
                String pageBeanPK = (String) it.next();
                PageBean pageBean = (PageBean) preferencePageBeans.get(pageBeanPK);
                if (pageBean.getValue("overrideStatus").equalsIgnoreCase("P"))
                {
                    String preferenceTYPEPB = Util.initString(pageBean.getValue("preferenceType"), "");
                    if (preferenceTYPEPB.equalsIgnoreCase(preferenceType))
                    {
                        primaryPreferenceFound = true;
                        break;
                    }
                }
            }

            if (!primaryPreferenceFound)
            {
                client.business.Client clientComponent = new client.component.ClientComponent();

                preferencePK = (clientComponent.getNextAvailableKey() * -1) + "";
                overrideStatus = "P";
            }
            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("errorMessage", "Default Information Already Exists");
            }
        }

        if (!primaryPreferenceFound)
        {
            formBean.putValue("bankAccountType", Util.initString(formBean.getValue("bankAccountType"), ""));
            formBean.putValue("bankState", Util.initString(formBean.getValue("bankState"), ""));
            formBean.putValue("bankAccountNumber", formBean.getValue("bankAccountNumber"));
            formBean.putValue("bankRoutingNumber", formBean.getValue("bankRoutingNumber"));
            formBean.putValue("bankName", formBean.getValue("bankName"));
            formBean.putValue("bankAddressLine1", formBean.getValue("bankAddressLine1"));
            formBean.putValue("bankAddressLine2", formBean.getValue("bankAddressLine2"));
            formBean.putValue("bankCity", formBean.getValue("bankCity"));
            formBean.putValue("bankZipCode", formBean.getValue("bankZipCode"));
            formBean.putValue("paymentMode", Util.initString(formBean.getValue("paymentMode"), ""));
            formBean.putValue("disbursementSource", Util.initString(formBean.getValue("disbursementSource"), ""));
            formBean.putValue("preferencePK", preferencePK);
            formBean.putValue("printAs", Util.initString(formBean.getValue("printAs"), ""));
            formBean.putValue("printAs2", Util.initString(formBean.getValue("printAs2"), ""));
            formBean.putValue("minimumCheck", Util.initString(formBean.getValue("minimumCheck"), "0"));
            formBean.putValue("overrideStatus", overrideStatus);
            formBean.putValue("preferenceType", Util.initString(formBean.getValue("preferenceType"), ""));

            appReqBlock.getSessionBean("preferenceSessionBean").putPageBean(preferencePK, formBean);
        }

        return CLIENT_PREFERENCE_INFO;
    }

    private String showSelectedPreference(AppReqBlock appReqBlock)
    {
        PageBean formBean = appReqBlock.getFormBean();
        String preferencePK = formBean.getValue("preferencePK");

        PageBean pageBean = appReqBlock.getSessionBean("preferenceSessionBean").getPageBean(preferencePK);

        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

        return CLIENT_PREFERENCE_INFO;
    }

    private String clearPreferenceForAdd(AppReqBlock appReqBlock)
    {
        return CLIENT_PREFERENCE_INFO;
    }

    private String showContractAgentInfo(AppReqBlock appReqBlock)
    {
        String preferencePK = appReqBlock.getFormBean().getValue("preferencePK");
        appReqBlock.getHttpServletRequest().setAttribute("preferencePK", preferencePK);
        
        return CONTRACT_AGENT_INFO_DIALOG;
    }

    private void clearAllClientSessionBeans(AppReqBlock appReqBlock)
    {
        appReqBlock.getSessionBean("clientDetailSessionBean").clearState();
        appReqBlock.getSessionBean("clientAddressSessionBean").clearState();
        appReqBlock.getSessionBean("taxInformationSessionBean").clearState();
        appReqBlock.getSessionBean("preferenceSessionBean").clearState();
        appReqBlock.getSessionBean("substandardSessionBean").clearState();
        appReqBlock.getSessionBean("contactInfoSessionBean").clearState();
        appReqBlock.getSessionBean("roleSessionBean").clearState();
        appReqBlock.getSessionBean("clientStateBean").clearState();
        appReqBlock.getHttpSession().removeAttribute("clientRoleVO");
    }


    private String showDOBGenderChangeDialog(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean stateBean = appReqBlock.getSessionBean("clientStateBean");
        String currentPage = stateBean.getValue("currentPage");

        savePreviousPageFormBean(appReqBlock, currentPage);

//        PageBean pageBean = new PageBean();
//        pageBean.putValue("clientDetailFK", appReqBlock.getFormBean().getValue("clientDetailPK"));
//        appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

        return DOB_GENDER_CHANGE_DIALOG;
    }

    private String saveDOBGenderChangeDialog(AppReqBlock appReqBlock)
    {
        new ClientUseCaseComponent().updateDOBGenderChange();

        PageBean formBean = appReqBlock.getFormBean();
        PageBean clientDetailPageBean = appReqBlock.getSessionBean("clientDetailSessionBean").getPageBean("pageBean");

        clientDetailPageBean.putValue("genderId", formBean.getValue("genderId"));

        clientDetailPageBean.putValue("dateOfBirth", formBean.getValue("dob"));

        appReqBlock.getSessionBean("clientDetailSessionBean").putPageBean("pageBean", clientDetailPageBean);

        return CLIENT_DETAIL;
    }

    /**
     *  Online logs require capturing the edits generated when trying to 
     *  save a client. Those information to capture is:
     *  1. Operator.
     *  2. LastName.
     *  3. CorporateName.
     *  4. TaxIdentification.
     *  5. Severity.
     *  6. ProcessDate.
     *  7. Error.
     */  
//    private void logValidateClientSave(ClientDetailVO clientDetailVO, ValidationVO[] validationVOs, AppReqBlock appReqBlock)
//    {
//        // Collect the information.
//        String operator = ((UserSession) appReqBlock.getHttpSession().getAttribute("userSession")).getUsername();
//
//        String lastName = clientDetailVO.getLastName();
//
//        String corporateName = clientDetailVO.getCorporateName();
//
//        String taxIdentification = clientDetailVO.getTaxIdentification();
//
//        EDITDate processDate = new EDITDate();
//
//        String severity = null;
//
//        String error = null;
//
//        // Setup the column data as defined in the log.
//
//        for (ValidationVO validationVO: validationVOs)
//        {
//            severity = validationVO.getSeverity();
//
//            error = validationVO.getMessage();
//
//            EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
//
//            columnInfo.put("Operator", operator);
//
//            columnInfo.put("LastName", lastName);
//
//            columnInfo.put("CorporateName", corporateName);
//
//            columnInfo.put("TaxIdentification", taxIdentification);
//
//            columnInfo.put("Severity", severity);
//
//            columnInfo.put("ProcessDate", processDate.getFormattedDate());
//
//            columnInfo.put("Error", error); // To me, this is a doubl entry since the error being logged separately.
//
//            Log.logToDatabase(Log.VALIDATE_CLIENT_SAVE, error, columnInfo);
//        }
//    }
}
