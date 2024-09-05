/*
 * User: gfrosti
 * Date: Nov 4, 2004
 * Time: 1:59:48 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance.ui.servlet;

import edit.common.*;

import edit.common.exceptions.*;

import edit.common.vo.*;

import edit.portal.common.transactions.Transaction;
import edit.portal.common.session.*;
import edit.services.db.*;

import fission.global.*;

import fission.utility.*;

import reinsurance.business.*;

import reinsurance.component.*;

import reinsurance.ui.*;
import reinsurance.*;

import java.math.*;
import java.util.*;

import batch.business.*;
import batch.component.*;
import engine.*;
import client.*;
import edit.portal.widget.ContactInformationTableModel;
import group.ContractGroup;

public class ReinsuranceTran extends Transaction
{
    // Actions
    private static final String SHOW_REINSURER_ADD_DIALOG = "showReinsurerAddDialog";
    private static final String SHOW_REINSURER_DETAIL_AFTER_ADD = "showReinsurerDetailAfterAdd";
    private static final String SHOW_REINSURER_DETAIL = "showReinsurerDetail";
    private static final String SHOW_REINSURER_TREATY = "showReinsurerTreaty";
    private static final String SHOW_SEARCH_FOR_TREATIES_DIALOG = "showSearchForTreatiesDialog";
    private static final String SHOW_REINSURER_HISTORY = "showReinsurerHistory";
    private static final String SHOW_TREATY_RELATIONS = "showTreatyRelations";
    private static final String ADD_TREATY = "addTreaty";
    private static final String DELETE_TREATY = "deleteTreaty";
    private static final String FIND_CLIENTS_BY_PARTIAL_CORPORATE_NAME = "findClientsByPartialCorporateName";
    private static final String FIND_CLIENT_BY_ID = "findClientByTaxId";
    private static final String FIND_CASE_BY_CASE_NUMBER = "findCaseByCaseNumber";
    private static final String FIND_SEGMENT_BY_CONTRACT_NUMBER = "findSegmentByContractNumber";
    private static final String FIND_SEGMENT_BY_PARTIAL_CORPORATE_NAME = "findSegmentByPartialCorporateName";
    private static final String SAVE_REINSURER_DETAILS = "saveReinsurerDetails";
    private static final String SHOW_CONTRACT_TREATY_RELATIONS = "showContractTreatyRelations";
    private static final String SHOW_COVERAGE_SUMMARY_AFTER_SEARCH = "showCoverageSummaryAfterSearch";
    private static final String SHOW_REINSURER_TREATY_GROUP_DIALOG = "showReinsurerTreatyGroupDialog";
    private static final String ADD_TREATY_GROUP = "addTreatyGroup";
    private static final String SAVE_TREATY_GROUP = "saveTreatyGroup";
    private static final String CANCEL_TREATY_GROUP = "cancelTreatyGroup";
    private static final String SHOW_TREATY_GROUP_DETAILS = "showTreatyGroupDetails";
    private static final String DELETE_TREATY_GROUP = "deleteTreatyGroup";
    private static final String SAVE_TREATY_TO_SUMMARY = "saveTreatyToSummary";
    private static final String SHOW_REINSURER_SEARCH_DIALOG = "showReinsurerSearchDialog";
    private static final String FIND_REINSURER_BY_PARTIAL_CORPORATE_NAME = "findReinsurerByPartialCorporateName";
    private static final String FIND_REINSURER_BY_REINSURER_NUMBER = "findReinsurerByReinsurerNumber";
    private static final String FIND_REINSURER_BY_TAXIDENTIFICATION = "findReinsurerByTaxIdentification";
    private static final String SHOW_REINSURER_DETAIL_AFTER_SEARCH = "showReinsurerDetailAfterSearch";
    private static final String LOCK_REINSURER = "lockReinsurer";
    private static final String CANCEL_REINSURER_EDITS = "cancelReinsurerEdits";
    private static final String CANCEL_TREATY_EDITS = "cancelTreatyEdits";
    private static final String SHOW_TREATY_DETAILS = "showTreatyDetails";
    private static final String SHOW_ATTACHED_TREATY_GROUPS = "showAttachedTreatyGroups";
    private static final String ATTACH_TREATY_GROUPS_TO_PRODUCT_STRUCTURE = "attachTreatyGroupsToProductStructure";
    private static final String DETACH_TREATY_GROUPS_FROM_PRODUCT_STRUCTURE = "detachTreatyGroupsFromProductStructure";
    private static final String SHOW_ATTACHED_TREATIES_BY_SEGMENT = "showAttachedTreatiesBySegment";
    private static final String ATTACH_TREATY_GROUPS_TO_SEGMENT = "attachTreatyGroupsToSegment";
    private static final String DETACH_TREATy_GROUPS_FROM_SEGMENT = "detachTreatyGroupsFromSegment";
    private static final String SHOW_TREATY_GROUP_LIST_DIALOG = "showTreatyGroupListDialog";
    private static final String SAVE_CONTRACT_TREATY_OVERRIDES = "saveContractTreatyOverrides";
    private static final String UPDATE_REINSURANCE_BALANCES = "updateReinsuranceBalances";
    private static final String CREATE_REINSURANCE_CHECK_TRANSACTIONS = "createReinsuranceCheckTransactions";
    private static final String SHOW_UPDATE_REINSURANCE_BALANCES = "showUpdateReinsuranceBalances";
    private static final String SHOW_REINSURANCE_CHECK_TRANSACTIONS = "showReinsuranceCheckTransactions";
    private static final String SHOW_SEGMENT_TREATY_RELATIONS = "showSegmentTreatyRelations";
    private static final String ATTACH_TREATIES_TO_SEGMENT = "attachTreatiesToSegment";
    private static final String DETACH_TREATIES_FROM_SEGMENT = "detachTreatiesFromSegment";
    private static final String SHOW_CONTRACT_TREATY_DIALOG = "showContractTreatyDialog";
    private static final String SHOW_REINSURANCE_HISTORY_DETAIL = "showReinsuranceHistoryDetail";
    private static final String SHOW_REINSURER_CONTACTINFORMATION_DETAIL = "showReinsurerContactInformationDialog";
    private static final String SHOW_CASE_SUMMARY_AFTER_SEARCH = "showCaseSummaryAfterSearch";
    private static final String ATTACH_TREATYGROUPS_TO_CONTRACTGROUP = "attachTreatyGroupsToContractGroup";
    private static final String SHOW_CONTRACTGROUP_TREATY_RELATIONS = "showContractGroupTreatyRelations";

    // Pages
    private static final String REINSURER_ADD_DIALOG = "/reinsurance/jsp/reinsurerAddDialog.jsp";
    private static final String REINSURER_DETAIL = "/reinsurance/jsp/reinsurerDetail.jsp";
    private static final String REINSURER_TREATY = "/reinsurance/jsp/reinsurerTreaty.jsp";
    private static final String CONTRACT_TREATY_RELATIONS = "/reinsurance/jsp/contractTreatyRelations.jsp";
    private static final String CONTRACT_TREATY_SEARCH_DIALOG = "/reinsurance/jsp/contractTreatySearchDialog.jsp";
    private static final String TREATY_RELATIONS = "/reinsurance/jsp/treatyRelations.jsp";
    private static final String CONTRACT_TREATY_OVERRIDE_DIALOG = "/reinsurance/jsp/contractTreatyOverrideDialog.jsp";
    private static final String REINSURER_HISTORY = "/reinsurance/jsp/reinsurerHistory.jsp";
    private static final String REINSURER_TREATY_GROUP_DIALOG = "/reinsurance/jsp/reinsurerTreatyGroupDialog.jsp";
    private static final String REINSURER_SEARCH_DIALOG = "/reinsurance/jsp/reinsurerSearchDialog.jsp";
    private static final String TREATY_GROUP_LIST_DIALOG = "/reinsurance/jsp/treatyGroupListDialog.jsp";
    private static final String REINSURANCE_UPDATE_REINSURANCE_BALANCES = "/reinsurance/jsp/reinsuranceUpdateBalancesJob.jsp";
    private static final String REINSURANCE_CREATE_REINSURANCE_CHECK_TRANSACTIONS = "/reinsurance/jsp/reinsuranceCheckTransactionsJob.jsp";
    private static final String SEGMENT_TREATY_RELATIONS = "/reinsurance/jsp/contractTreatySegmentTreatyRelations.jsp";
    private static final String REPORTING_MAIN = "/reporting/jsp/reportingSelection.jsp";
    private static final String REINSURER_CONTACTINFORMATION_DIALOG = "/reinsurance/jsp/reinsurerContactInformationDialog.jsp";


    /**
     * @param appReqBlock
     * @return
     * @throws Throwable
     * @see Transaction
     */
    public String execute(AppReqBlock appReqBlock) throws Throwable
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        preprocessRequest(appReqBlock);

        if (action.equals(SHOW_REINSURER_ADD_DIALOG))
        {
            returnPage = showReinsurerAddDialog(appReqBlock);
        }
        else if (action.equals(FIND_CLIENT_BY_ID))
        {
            returnPage = findClientByTaxId(appReqBlock);
        }
        else if (action.equals(FIND_CLIENTS_BY_PARTIAL_CORPORATE_NAME))
        {
            returnPage = findClientsByPartialCorporateName(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_DETAIL_AFTER_ADD))
        {
            returnPage = showReinsurerDetailAfterAdd(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_TREATY))
        {
            returnPage = showReinsurerTreaty(appReqBlock);
        }
        else if (action.equals(SHOW_TREATY_RELATIONS))
        {
            returnPage = showTreatyRelations(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACT_TREATY_RELATIONS))
        {
            returnPage = showContractTreatyRelations(appReqBlock);
        }
        else if (action.equals(SHOW_SEARCH_FOR_TREATIES_DIALOG))
        {
            returnPage = showSearchForTreatiesDialog(appReqBlock);
        }
        else if (action.equals(FIND_CASE_BY_CASE_NUMBER))
        {
            returnPage = findCaseByCaseNumber(appReqBlock);
        }
        else if (action.equals(FIND_SEGMENT_BY_CONTRACT_NUMBER))
        {
            returnPage = findSegmentByContractNumber(appReqBlock);
        }
        else if (action.equals(FIND_SEGMENT_BY_PARTIAL_CORPORATE_NAME))
        {
            returnPage = findSegmentByPartialCorporateName(appReqBlock);
        }
        else if (action.equals(SHOW_COVERAGE_SUMMARY_AFTER_SEARCH))
        {
            returnPage = showCoverageSummaryAfterSearch(appReqBlock);
        }
        else if (action.equals(SAVE_REINSURER_DETAILS))
        {
            returnPage = saveReinsurerDetails(appReqBlock);
        }
        else if (action.equals(ADD_TREATY))
        {
            returnPage = addTreaty(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_HISTORY))
        {
            returnPage = showReinsurerHistory(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_TREATY_GROUP_DIALOG))
        {
            returnPage = showReinsurerTreatyGroupDialog(appReqBlock);
        }
        else if (action.equals(ADD_TREATY_GROUP))
        {
            returnPage = addTreatyGroup(appReqBlock);
        }
        else if (action.equals(SAVE_TREATY_GROUP))
        {
            returnPage = saveTreatyGroup(appReqBlock);
        }
        else if (action.equals(CANCEL_TREATY_GROUP))
        {
            returnPage = cancelTreatyGroup(appReqBlock);
        }
        else if (action.equals(SHOW_TREATY_GROUP_DETAILS))
        {
            returnPage = showTreatyGroupDetails(appReqBlock);
        }
        else if (action.equals(DELETE_TREATY_GROUP))
        {
            returnPage = deleteTreatyGroup(appReqBlock);
        }
        else if (action.equals(SAVE_TREATY_TO_SUMMARY))
        {
            returnPage = saveTreatyToSummary(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_DETAIL))
        {
            returnPage = showReinsurerDetail(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_SEARCH_DIALOG))
        {
            returnPage = showReinsurerSearchDialog(appReqBlock);
        }
        else if (action.equals(FIND_REINSURER_BY_PARTIAL_CORPORATE_NAME))
        {
            returnPage = findReinsurerByPartialCorporateName(appReqBlock);
        }
        else if (action.equals(FIND_REINSURER_BY_REINSURER_NUMBER))
        {
            returnPage = findReinsurerByReinsurerNumber(appReqBlock);
        }
        else if (action.equals(FIND_REINSURER_BY_TAXIDENTIFICATION))
        {
            returnPage = findReinsurerByTaxIdentification(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_DETAIL_AFTER_SEARCH))
        {
            returnPage = showReinsurerDetailAfterSearch(appReqBlock);
        }
        else if (action.equals(LOCK_REINSURER))
        {
            returnPage = lockReinsurer(appReqBlock);
        }
        else if (action.equals(CANCEL_REINSURER_EDITS))
        {
            returnPage = cancelReinsurerEdits(appReqBlock);
        }
        else if (action.equals(CANCEL_TREATY_EDITS))
        {
            returnPage = cancelTreatyEdits(appReqBlock);
        }
        else if (action.equals(SHOW_TREATY_DETAILS))
        {
            returnPage = showTreatyDetails(appReqBlock);
        }
        else if (action.equals(SHOW_ATTACHED_TREATY_GROUPS))
        {
            returnPage = showAttachedTreatyGroups(appReqBlock);
        }
        else if (action.equals(ATTACH_TREATY_GROUPS_TO_PRODUCT_STRUCTURE))
        {
            returnPage = attachTreatyGroupsToProductStructure(appReqBlock);
        }
        else if (action.equals(DETACH_TREATY_GROUPS_FROM_PRODUCT_STRUCTURE))
        {
            returnPage = detachTreatyGroupsFromProductStructure(appReqBlock);
        }
        else if (action.equals(ATTACH_TREATY_GROUPS_TO_SEGMENT))
        {
            returnPage = attachTreatyGroupsToSegment(appReqBlock);
        }
        else if (action.equals(SHOW_ATTACHED_TREATIES_BY_SEGMENT))
        {
            returnPage = showAttachedTreatiesBySegment(appReqBlock);
        }
        else if (action.equals(DETACH_TREATy_GROUPS_FROM_SEGMENT))
        {
            returnPage = detachTreatyGroupsFromSegment(appReqBlock);
        }
        else if (action.equals(SHOW_TREATY_GROUP_LIST_DIALOG))
        {
            returnPage = showTreatyGroupListDialog(appReqBlock);
        }
        else if (action.equals(SAVE_CONTRACT_TREATY_OVERRIDES))
        {
            returnPage = saveContractTreatyOverrides(appReqBlock);
        }
        else if (action.equals(UPDATE_REINSURANCE_BALANCES))
        {
            returnPage = updateReinsuranceBalances(appReqBlock);
        }
        else if (action.equals(CREATE_REINSURANCE_CHECK_TRANSACTIONS))
        {
            returnPage = createReinsuranceCheckTransactions(appReqBlock);
        }
        else if (action.equals(SHOW_UPDATE_REINSURANCE_BALANCES))
        {
            returnPage = showUpdateReinsuranceBalances(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURANCE_CHECK_TRANSACTIONS))
        {
            returnPage = showReinsuranceCheckTransactions(appReqBlock);
        }
        else if (action.equals(SHOW_SEGMENT_TREATY_RELATIONS))
        {
            returnPage = showSegmentTreatyRelations(appReqBlock);
        }
        else if (action.equals(ATTACH_TREATIES_TO_SEGMENT))
        {
            returnPage = attachTreatiesToSegment(appReqBlock);
        }
        else if (action.equals(DETACH_TREATIES_FROM_SEGMENT))
        {
            returnPage = detachTreatiesFromSegment(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACT_TREATY_DIALOG))
        {
            returnPage = showContractTreatyDialog(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURANCE_HISTORY_DETAIL))
        {
            returnPage = showReinsuranceHistoryDetail(appReqBlock);
        }
        else if (action.equals(DELETE_TREATY))
        {
            returnPage = deleteTreaty(appReqBlock);
        }
        else if (action.equals(SHOW_REINSURER_CONTACTINFORMATION_DETAIL))
        {
            returnPage = showReinsurerContactInformationDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CASE_SUMMARY_AFTER_SEARCH))
        {
            returnPage = showCaseSummaryAfterSearch(appReqBlock);
        }
        else if (action.equals(ATTACH_TREATYGROUPS_TO_CONTRACTGROUP))
        {
            returnPage = attachTreatyGroupsToContractGroup(appReqBlock);
        }
        else if (action.equals(SHOW_CONTRACTGROUP_TREATY_RELATIONS))
        {
            returnPage = showContractGroupTreatyRelations(appReqBlock);
        }
        else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }

    /**
     * Shows the dialog for mapping a Segment to its Treaty(s)
     * @param appReqBlock
     * @return
     */
    private String showContractGroupTreatyRelations(AppReqBlock appReqBlock)
    {
        String activeCasePK = Util.initString(appReqBlock.getReqParm("activeCasePK"), null);

        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), null);

        String activeTreatyGroupPK = Util.initString(appReqBlock.getReqParm("activeTreatyGroupPK"), null);

        Treaty[] treaties = Treaty.findBy_TreatyGroupPK_ContractGroupPK(Long.parseLong(activeTreatyGroupPK), Long.parseLong(activeCasePK));

        TreatyVO[] treatyVOs = null;

        if (treaties.length > 0)
        {
            treatyVOs = new TreatyVO[treaties.length];

            for (int i = 0; i < treaties.length; i++)
            {
                treatyVOs[i] = (TreatyVO) treaties[i].getVO();
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("treatyVOs", treatyVOs);

        appReqBlock.getHttpServletRequest().setAttribute("activeCasePK", activeCasePK);

        appReqBlock.getHttpServletRequest().setAttribute("activeSegmentPK", activeSegmentPK);

        appReqBlock.getHttpServletRequest().setAttribute("activeTreatyGroupPK", activeTreatyGroupPK);

        return SEGMENT_TREATY_RELATIONS;
    }

    /**
     * User has selected a ContractGroup and one or more TreatyGroups.
     * @param appReqBlock
     * @return
     */
    private String attachTreatyGroupsToContractGroup(AppReqBlock appReqBlock)
    {
        String selectedTreatyGroupPKs = Util.initString(appReqBlock.getReqParm("selectedTreatyGroupPKs"), null);

        String activeCasePK = Util.initString(appReqBlock.getReqParm("activeCasePK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String[] treatyGroupPKs = Util.fastTokenizer(selectedTreatyGroupPKs, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < treatyGroupPKs.length; i++)
            {
                reinsurance.attachTreatyGroupToContractGroup(Long.parseLong(treatyGroupPKs[i]), Long.parseLong(activeCasePK));
            }

            responseMessage = "Treaty Groups(s) Successfully Attached";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            RuntimeException e2 = new RuntimeException(e);

            e2.setStackTrace(e.getStackTrace());

            throw e2;
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeCasePK", activeCasePK);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showContractTreatyRelations(appReqBlock);
    }

    /**
     * User has search for a ContractGroup (Case) by ContractGroup.ContractGroupNumber, and now
     * wished to associate TreatyGroups to it in the ContractTreatyRelations page.
     * @param appReqBlock
     * @return
     */
    private String showCaseSummaryAfterSearch(AppReqBlock appReqBlock)
    {
        String casePK = Util.initString(appReqBlock.getReqParm("casePK"), null);

        appReqBlock.setReqParm("activeCasePK", casePK);

        return showContractTreatyRelations(appReqBlock);
    }

    /**
     * The dialog that shows all related Contact Information for the currently
     * showing Reinsurer.
     * @param appReqBlock
     * @return
     */
    private String showReinsurerContactInformationDialog(AppReqBlock appReqBlock)
    {
        new ContactInformationTableModel(appReqBlock);

        return REINSURER_CONTACTINFORMATION_DIALOG;
    }

    /**
     * Deletes the selected Treaty.
     * @param appReqBlock
     * @return
     */
    private String deleteTreaty(AppReqBlock appReqBlock)
    {
        String treatyPK = Util.initString(appReqBlock.getReqParm("treatyPK"), null);

        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        cloud.removeTreatyFromSummary(Long.parseLong(treatyPK));

        return showReinsurerTreaty(appReqBlock);
    }

    /**
     * Shows ReinsuranceHistory detail.
     * @param appReqBlock
     * @return
     */
    private String showReinsuranceHistoryDetail(AppReqBlock appReqBlock)
    {
        String activeReinsuranceHistoryPK = appReqBlock.getReqParm("activeReinsuranceHistoryPK");

        appReqBlock.getHttpServletRequest().setAttribute("activeReinsuranceHistoryPK", activeReinsuranceHistoryPK);

        return showReinsurerHistory(appReqBlock);
    }

    /**
     * Detaches the set of selected Treaty(s) to the specified Segment.
     * @param appReqBlock
     * @return
     */
    private String detachTreatiesFromSegment(AppReqBlock appReqBlock)
    {
        String selectedTreatyPKs = Util.initString(appReqBlock.getReqParm("selectedTreatyPKs"), null);

        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String[] treatyPKs = Util.fastTokenizer(selectedTreatyPKs, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < treatyPKs.length; i++)
            {
                reinsurance.detachTreatyFromSegment(Long.parseLong(treatyPKs[i]), Long.parseLong(activeSegmentPK));
            }

            responseMessage = "Treaty(s) Successfully Detached";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showSegmentTreatyRelations(appReqBlock);
    }

    /**
     * Attaches the set of selected Treaty(s) to the specified Segment.
     * @param appReqBlock
     * @return
     */
    private String attachTreatiesToSegment(AppReqBlock appReqBlock)
    {
        String selectedTreatyPKs = Util.initString(appReqBlock.getReqParm("selectedTreatyPKs"), null);

        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String[] treatyPKs = Util.fastTokenizer(selectedTreatyPKs, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < treatyPKs.length; i++)
            {
                reinsurance.attachTreatyToSegment(Long.parseLong(treatyPKs[i]), Long.parseLong(activeSegmentPK));
            }

            responseMessage = "Treaty(s) Successfully Attached";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            RuntimeException e2 = new RuntimeException(e);

            e2.setStackTrace(e.getStackTrace());

            throw e2;
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showSegmentTreatyRelations(appReqBlock);
    }

    /**
     * Shows the dialog for mapping a Segment to its Treaty(s)
     * @param appReqBlock
     * @return
     */
    private String showSegmentTreatyRelations(AppReqBlock appReqBlock)
    {
        String activeCasePK = Util.initString(appReqBlock.getReqParm("activeCasePK"), null);

        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), null);

        String activeTreatyGroupPK = Util.initString(appReqBlock.getReqParm("activeTreatyGroupPK"), null);

        TreatyVO[] treatyVOs = (TreatyVO[]) CRUDEntityImpl.mapEntityToVO(Treaty.findBy_TreatyGroupPK_SegmentPK(Long.parseLong(activeTreatyGroupPK), Long.parseLong(activeSegmentPK)), TreatyVO.class);

        if (treatyVOs == null) treatyVOs = new TreatyVO[0];

        appReqBlock.getHttpServletRequest().setAttribute("treatyVOs", treatyVOs);

        appReqBlock.getHttpServletRequest().setAttribute("activeCasePK", activeCasePK);

        appReqBlock.getHttpServletRequest().setAttribute("activeSegmentPK", activeSegmentPK);

        appReqBlock.getHttpServletRequest().setAttribute("activeTreatyGroupPK", activeTreatyGroupPK);

        return SEGMENT_TREATY_RELATIONS;
    }

    /**
     * Returns the reinsurance check job page.
     * @param appReqBlock
     * @return
     */
    private String showReinsuranceCheckTransactions(AppReqBlock appReqBlock)
    {
        String[] companyNames = findAllProductStructures(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("companyNames", companyNames);

        return REINSURANCE_CREATE_REINSURANCE_CHECK_TRANSACTIONS;
    }

    /**
     * Returns the reinsurance balances job page.
     * @param appReqBlock
     * @return
     */
    private String showUpdateReinsuranceBalances(AppReqBlock appReqBlock)
    {
        String[] companyNames = findAllProductStructures(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("companyNames", companyNames);

        return REINSURANCE_UPDATE_REINSURANCE_BALANCES;
    }

    /**
     * Process to map Reinsurance History amounts to their proper Treaties.
     * @param appReqBlock
     */
    public String updateReinsuranceBalances(AppReqBlock appReqBlock)
    {
        String companyName = appReqBlock.getReqParm("companyName");

        String operator = appReqBlock.getUserSession().getUsername();

        Batch batch = new BatchComponent();

        batch.updateReinsuranceBalances(companyName, operator, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.putInRequestScope("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Process to create Reinsurance Check transactions for Reinsureres with positive balances on their respective
     * Treaties.
     * @param appReqBlock
     * @return
     */
    private String createReinsuranceCheckTransactions(AppReqBlock appReqBlock)
    {
        String companyName = appReqBlock.getReqParm("companyName");

        String operator = appReqBlock.getUserSession().getUsername();

        Batch batch = new BatchComponent();

        batch.setupReinsuranceChecks(companyName, operator, null, null, Batch.ASYNCHRONOUS);

        String responseMessage = Util.getResourceMessage("batch.job.in.progress");

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return REPORTING_MAIN;
    }

    /**
     * Saves the Contract Treaty overrides.
     * @param appReqBlock
     * @return
     */
    private String saveContractTreatyOverrides(AppReqBlock appReqBlock)
    {
        ContractTreatyVO contractTreatyVO = mapFormDataToContractTreatyVO(appReqBlock);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String responseMessage = null;

        try
        {
            contractTreatyVO = reinsurance.saveContractTreatyOverrides(contractTreatyVO);

            responseMessage = "Contract Treaty Successfully Updated";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }

        //  COMMENTED OUT THE FOLLOWING LINE BECAUSE CASE WAS REPLACED WITH CONTRACTGROUP AND THE DETAILS OF HOW THAT
        //  WILL WORK WITH CONTRACTTREATY HAVE NOT BEEN DECIDED YET
//        appReqBlock.getHttpServletRequest().setAttribute("activeCasePK", String.valueOf(contractTreatyVO.getCaseFK()));

        appReqBlock.getHttpServletRequest().setAttribute("activeSegmentPK", String.valueOf(contractTreatyVO.getSegmentFK()));

        appReqBlock.getHttpServletRequest().setAttribute("activeTreatyPK", String.valueOf(contractTreatyVO.getTreatyFK()));

        appReqBlock.getHttpServletRequest().setAttribute("activeContractTreatyPK", String.valueOf(contractTreatyVO.getContractTreatyPK()));

        appReqBlock.getHttpServletRequest().setAttribute("activeContractTreatyVO", contractTreatyVO);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return CONTRACT_TREATY_OVERRIDE_DIALOG;
    }

    /**
     * Renders dialog showing the list of Treaties for the selected Treaty Group.
     * @param appReqBlock
     * @return
     */
    private String showTreatyGroupListDialog(AppReqBlock appReqBlock)
    {
        String activeTreatyGroupPK = Util.initString(appReqBlock.getReqParm("activeTreatyGroupPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        TreatyVO[] treatyVOs = reinsurance.findTreatiesBy_TreatyGroupPK(Long.parseLong(activeTreatyGroupPK));

        appReqBlock.getHttpServletRequest().setAttribute("treatyVOs", treatyVOs);

        return TREATY_GROUP_LIST_DIALOG;
    }

    /**
     * Shows the dialog used to override Contract Treaty defaults.
     * @param appReqBlock
     * @return
     */
    private String showContractTreatyDialog(AppReqBlock appReqBlock)
    {
        String activeCasePK = Util.initString(appReqBlock.getReqParm("activeCasePK"), null);

        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), null);

        String activeTreatyPK = Util.initString(appReqBlock.getReqParm("activeTreatyPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        ContractTreatyVO activeContractTreatyVO = reinsurance.findContractTreatyBy_SegmentPK_TreatyPK(Long.parseLong(activeSegmentPK), Long.parseLong(activeTreatyPK));

        appReqBlock.getHttpServletRequest().setAttribute("activeContractTreatyVO", activeContractTreatyVO);

        appReqBlock.getHttpServletRequest().setAttribute("activeContractTreatyPK", String.valueOf(activeContractTreatyVO.getContractTreatyPK()));

        appReqBlock.getHttpServletRequest().setAttribute("activeCasePK", activeCasePK);

        appReqBlock.getHttpServletRequest().setAttribute("activeSegmentPK", activeSegmentPK);

        appReqBlock.getHttpServletRequest().setAttribute("activeTreatyPK", activeTreatyPK);

        return CONTRACT_TREATY_OVERRIDE_DIALOG;
    }

    /**
     * Detaches Treaties from the specified Segment.
     * @param appReqBlock
     * @return
     */
    private String detachTreatyGroupsFromSegment(AppReqBlock appReqBlock)
    {
        String selectedTreatyGroupPKs = Util.initString(appReqBlock.getReqParm("selectedTreatyGroupPKs"), null);

        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String[] treatyGroupPKs = Util.fastTokenizer(selectedTreatyGroupPKs, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < treatyGroupPKs.length; i++)
            {
                reinsurance.detachTreatyGroupFromSegment(Long.parseLong(treatyGroupPKs[i]), Long.parseLong(activeSegmentPK));
            }

            responseMessage = "Treaty Group(s) Successfully Detached";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeSegmentPK", activeSegmentPK);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showContractTreatyRelations(appReqBlock);
    }

    /**
     * Shows the set of Treaties attached to the selected Segment.
     * @param appReqBlock
     * @return
     */
    private String showAttachedTreatiesBySegment(AppReqBlock appReqBlock)
    {
        return showContractTreatyRelations(appReqBlock);
    }

    /**
     * Attaches the selected Treaties to the specified Segment.
     * @param appReqBlock
     * @return
     */
    private String attachTreatyGroupsToSegment(AppReqBlock appReqBlock)
    {
        String selectedTreatyGroupPKs = Util.initString(appReqBlock.getReqParm("selectedTreatyGroupPKs"), null);

        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String[] treatyGroupPKs = Util.fastTokenizer(selectedTreatyGroupPKs, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < treatyGroupPKs.length; i++)
            {
                reinsurance.attachTreatyGroupToSegment(Long.parseLong(treatyGroupPKs[i]), Long.parseLong(activeSegmentPK));
            }

            responseMessage = "Treaty Groups(s) Successfully Attached";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            RuntimeException e2 = new RuntimeException(e);

            e2.setStackTrace(e.getStackTrace());

            throw e2;
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeSegmentPK", activeSegmentPK);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showContractTreatyRelations(appReqBlock);
    }

    /**
     * Detaches the set of selected TreatyGroups to the specified Product Structure.
     * @param appReqBlock
     * @return
     */
    private String detachTreatyGroupsFromProductStructure(AppReqBlock appReqBlock)
    {
        String treatyGroupPKsList = Util.initString(appReqBlock.getReqParm("treatyGroupPKs"), null);

        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String[] treatyGroupPKs = Util.fastTokenizer(treatyGroupPKsList, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < treatyGroupPKs.length; i++)
            {
                reinsurance.detachTreatyGroupFromProductStructure(Long.parseLong(treatyGroupPKs[i]), Long.parseLong(activeProductStructurePK));
            }

            responseMessage = "Treaty Groups(s) Successfully Detached";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            RuntimeException e2 = new RuntimeException(e);

            e2.setStackTrace(e.getStackTrace());

            throw e2;
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", activeProductStructurePK);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showTreatyRelations(appReqBlock);
    }

    /**
     * Attaches the set of selected TreatyGroups to the specified Product Structure.
     * @param appReqBlock
     * @return
     */
    private String attachTreatyGroupsToProductStructure(AppReqBlock appReqBlock)
    {
        String treatyGroupPKsList = Util.initString(appReqBlock.getReqParm("treatyGroupPKs"), null);

        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String[] treatyGroupPKs = Util.fastTokenizer(treatyGroupPKsList, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < treatyGroupPKs.length; i++)
            {
                reinsurance.attachTreatyGroupToProductStructure(Long.parseLong(treatyGroupPKs[i]), Long.parseLong(activeProductStructurePK));
            }

            responseMessage = "Treaty Groups(s) Successfully Attached";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            RuntimeException e2 = new RuntimeException(e);

            e2.setStackTrace(e.getStackTrace());

            throw e2;
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", activeProductStructurePK);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showTreatyRelations(appReqBlock);
    }

    /**
     * @param appReqBlock
     * @return
     */
    private String showAttachedTreatyGroups(AppReqBlock appReqBlock)
    {
        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", activeProductStructurePK);

        return showTreatyRelations(appReqBlock);
    }

    /**
     * Shows the details of the selected Treaty.
     * @param appReqBlock
     * @return
     */
    private String showTreatyDetails(AppReqBlock appReqBlock)
    {
        String treatyPK = Util.initString(appReqBlock.getReqParm("treatyPK"), null);

        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        TreatyVO treatyVO = cloud.getTreatyVO(Long.parseLong(treatyPK));

        cloud.setActiveTreatyVO(treatyVO);

        appReqBlock.getHttpServletRequest().setAttribute("treatyVO", treatyVO);

        return showReinsurerTreaty(appReqBlock);
    }

    /**
     * Cancels any current Treaty edits.
     * @param appReqBlock
     * @return
     */
    private String cancelTreatyEdits(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        cloud.setActiveTreatyVO(null);

        return showReinsurerTreaty(appReqBlock);
    }

    /**
     * Cancels any current edits and unlocks the Reinsurer.
     * @param appReqBlock
     * @return
     */
    private String cancelReinsurerEdits(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        cloud.clearCloudState();

        UserSession userSession = appReqBlock.getUserSession();

        userSession.unlockReinsurer();

        return showReinsurerDetail(appReqBlock);
    }

    /**
     * Locks the specified Reinsurer for exlusive editing access.
     * @param appReqBlock
     * @return
     */
    private String lockReinsurer(AppReqBlock appReqBlock)
    {
        new ReinsuranceUseCaseComponent().updateReinsurance();

        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        long reinsurerPK = cloud.getActiveReinsurerVO().getReinsurerPK();

        UserSession userSession = appReqBlock.getUserSession();

        try
        {
            userSession.lockReinsurer(reinsurerPK);
        }
        catch (EDITLockException e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", e.getMessage());
        }

        return showReinsurerDetail(appReqBlock);
    }

    /**
     * Displays an existing Reinsurer detail as a result of the search dialog.
     * @param appReqBlock
     * @return
     */
    private String showReinsurerDetailAfterSearch(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        cloud.clearCloudState();

        String reinsurerPK = Util.initString(appReqBlock.getReqParm("reinsurerPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        ReinsurerVO reinsurerVO = reinsurance.findReinsurerBy_ReinsurerPK(Long.parseLong(reinsurerPK));

        TreatyVO[] treatyVOs = reinsurance.findTreatiesBy_ReinsurerNumber(reinsurerVO.getReinsurerNumber());

        if (treatyVOs != null)
        {
            cloud.addTreatiesToSummary(treatyVOs);
        }

        cloud.setActiveReinsurerVO(reinsurerVO);

        UserSession userSession = appReqBlock.getUserSession();

        userSession.setReinsurerPK(Long.parseLong(reinsurerPK));

        return showReinsurerDetail(appReqBlock);
    }

    /**
     * Finder Reinsurer by TaxIdentification, if any.
     * @param appReqBlock
     * @return
     */
    private String findReinsurerByTaxIdentification(AppReqBlock appReqBlock)
    {
        String taxIdentification = Util.initString(appReqBlock.getReqParm("taxIdentification"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        ReinsurerVO reinsurerVO = reinsurance.findReinsurerBy_TaxIdentication(taxIdentification);

        if (reinsurerVO != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("reinsurerVOs", new ReinsurerVO[]{reinsurerVO});
        }

        return showReinsurerSearchDialog(appReqBlock);
    }

    /**
     * Finds Reinsurer By Reinsurer Number, if any.
     * @param appReqBlock
     * @return
     */
    private String findReinsurerByReinsurerNumber(AppReqBlock appReqBlock)
    {
        String reinsurerNumber = Util.initString(appReqBlock.getReqParm("reinsurerNumber"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        ReinsurerVO reinsurerVO = reinsurance.findReinsurerBy_ReinsurerNumber(reinsurerNumber);

        if (reinsurerVO != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("reinsurerVOs", new ReinsurerVO[]{reinsurerVO});
        }

        return showReinsurerSearchDialog(appReqBlock);
    }

    /**
     * Finds Reinsurers by partial Corporate Name, if any.
     * @param appReqBlock
     * @return
     */
    private String findReinsurerByPartialCorporateName(AppReqBlock appReqBlock)
    {
        String partialCorporateName = Util.initString(appReqBlock.getReqParm("corporateName"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        ReinsurerVO[] reinsurerVOs = reinsurance.findReinsurersBy_PartialCorporateName(partialCorporateName);

        appReqBlock.getHttpServletRequest().setAttribute("reinsurerVOs", reinsurerVOs);

        return showReinsurerSearchDialog(appReqBlock);
    }

    /**
     * Shows the reinsurerSearchDialog
     * @param appReqBlock
     * @return
     */
    private String showReinsurerSearchDialog(AppReqBlock appReqBlock)
    {
        return REINSURER_SEARCH_DIALOG;
    }

    /**
     * Saves the just-submitted Treaty to cloudland's summary of Treaties.
     * @param appReqBlock
     */
    private String saveTreatyToSummary(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        TreatyVO treatyVO = mapFormDataToTreatyVO(appReqBlock);

        cloud.addTreatyToSummary(treatyVO);

        cloud.setActiveTreatyVO(null);

        return showReinsurerTreaty(appReqBlock);
    }

    /**
     * Deletes the selected TreatyGroup, if possible.
     * @param appReqBlock
     * @return
     */
    private String deleteTreatyGroup(AppReqBlock appReqBlock)
    {
        new ReinsuranceUseCaseComponent().deleteTreaty();

        String activeTreatyGroupPK = Util.initString(appReqBlock.getReqParm("activeTreatyGroupPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        String responseMessage = null;

        try
        {
            reinsurance.deleteTreatyGroup(Long.parseLong(activeTreatyGroupPK));

            responseMessage = "Treaty Group Successfully Deleted";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showReinsurerTreatyGroupDialog(appReqBlock);
    }

    /**
     * Shows the details of the selected Treaty Group.
     * @param appReqBlock
     * @return
     */
    private String showTreatyGroupDetails(AppReqBlock appReqBlock)
    {
        String activeTreatyGroupPK = Util.initString(appReqBlock.getReqParm("activeTreatyGroupPK"), null);

        Reinsurance reinsurance = new ReinsuranceComponent();

        TreatyGroupVO treatyGroupVO = reinsurance.findTreatyGroupBy_TreatyGroupPK(Long.parseLong(activeTreatyGroupPK));

        appReqBlock.getHttpServletRequest().setAttribute("activeTreatyGroupVO", treatyGroupVO);

        return showReinsurerTreatyGroupDialog(appReqBlock);
    }

    /**
     * Cancels and current Treaty Group edits.
     * @param appReqBlock
     * @return
     */
    private String cancelTreatyGroup(AppReqBlock appReqBlock)
    {
        return showReinsurerTreatyGroupDialog(appReqBlock);
    }

    /**
     * Saves the Treaty Group.
     * @param appReqBlock
     * @return
     */
    private String saveTreatyGroup(AppReqBlock appReqBlock)
    {
        String groupNumber = Util.initString(appReqBlock.getReqParm("groupNumber"), null);

        String activeTreatyGroupPK = Util.initString(appReqBlock.getReqParm("activeTreatyGroupPK"), "0");

        TreatyGroupVO treatyGroupVO = new TreatyGroupVO();

        treatyGroupVO.setTreatyGroupNumber(groupNumber);

        treatyGroupVO.setTreatyGroupPK(Long.parseLong(activeTreatyGroupPK));

        Reinsurance reinsurance = new ReinsuranceComponent();

        String responseMessage = null;

        try
        {
            reinsurance.saveTreatyGroup(treatyGroupVO);

            responseMessage = "Treaty Group Successfully Saved";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showReinsurerTreatyGroupDialog(appReqBlock);
    }

    /**
     * Prepares the Treaty Group Dialog for adding a new Treaty Group.
     * @param appReqBlock
     * @return
     */
    private String addTreatyGroup(AppReqBlock appReqBlock)
    {
        return showReinsurerTreatyGroupDialog(appReqBlock);
    }

    /**
     * Opens the Treaty Group Dialog
     * @param appReqBlock
     * @return
     */
    private String showReinsurerTreatyGroupDialog(AppReqBlock appReqBlock)
    {
        Reinsurance reinsurance = new ReinsuranceComponent();

        TreatyGroupVO[] treatyGroupVOs = reinsurance.findAllTreatyGroups();

        appReqBlock.getHttpServletRequest().setAttribute("treatyGroupVOs", treatyGroupVOs);

        return REINSURER_TREATY_GROUP_DIALOG;
    }

    /**
     * Prepares the reinsurerTreaty.jsp page for the addtion of a new Treaty.
     * @param appReqBlock
     * @return
     */
    private String addTreaty(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        cloud.setActiveTreatyVO(null);

        return showReinsurerTreaty(appReqBlock);
    }

    /**
     * Finds the client(s) by partial last name (if any).
     * @param appReqBlock
     * @return
     */
    private String findClientByTaxId(AppReqBlock appReqBlock)
    {
        String taxIdentificationNumber = Util.initString(appReqBlock.getReqParm("taxIdentificationNumber"), null);

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailByTaxId(taxIdentificationNumber, false, null);

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailVOs", clientDetailVOs);

        return showReinsurerAddDialog(appReqBlock);
    }

    /**
     * Finds the client(s) by partial last name (if any).
     * @param appReqBlock
     * @return
     */
    private String findClientsByPartialCorporateName(AppReqBlock appReqBlock)
    {
        String partialCorporateName = Util.initString(appReqBlock.getReqParm("partialCorporateName"), null);

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailsBy_PartialCorporateName(partialCorporateName);

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailVOs", clientDetailVOs);

        return showReinsurerAddDialog(appReqBlock);
    }

    /**
     * Returns the search dialog with the found Case, if any.
     * @param appReqBlock
     * @return
     */
    private String findCaseByCaseNumber(AppReqBlock appReqBlock)
    {
        // Some assumptions were changed (01/15/2009). We now search for a ContractGroup (Case).
        String caseNumber = Util.initString(appReqBlock.getReqParm("caseNumber"), null);

        ContractGroup foundCase = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(caseNumber, ContractGroup.CONTRACTGROUPTYPECT_CASE);

        if (foundCase != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("case", foundCase);
        }

        return CONTRACT_TREATY_SEARCH_DIALOG;
    }

    /**
     * Returns the search dialog with the targeted Segment Contract Number, if any.
     * @param appReqBlock
     * @return
     */
    private String findSegmentByContractNumber(AppReqBlock appReqBlock)
    {
        String contractNumber = appReqBlock.getReqParm("contractNumber");

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        SegmentVO[] segmentVOs = contractLookup.findSegmentVOByContractNumber(contractNumber, false, null);

        appReqBlock.getHttpServletRequest().setAttribute("segmentVOs", segmentVOs);

        return CONTRACT_TREATY_SEARCH_DIALOG;
    }

    /**
     * Returns the search dialog with the targeted Segment by partial Corporate Name, if any.
     * @param appReqBlock
     * @return
     */
    private String findSegmentByPartialCorporateName(AppReqBlock appReqBlock)
    {
        String partialCorporateName = appReqBlock.getReqParm("partialCorporateName");

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        SegmentVO[] segmentVOs = contractLookup.findSegmentsBy_PartialCorporateName(partialCorporateName);

        appReqBlock.getHttpServletRequest().setAttribute("segmentVOs", segmentVOs);

        return CONTRACT_TREATY_SEARCH_DIALOG;
    }

    /**
     * If required, it is possible to pre-process a request before its actual excecution. A common use of this would
     * be to store the "cloud-land" state of the page being unloaded from the browser.
     * @param appReqBlock
     */
    private void preprocessRequest(AppReqBlock appReqBlock)
    {
        String pageName = Util.initString(appReqBlock.getReqParm("pageName"), null);

        if (pageName != null)
        {
            if (pageName.equals("reinsurerDetail"))
            {
                storeReinsurerFormDataToSession(appReqBlock);
            }

            else if (pageName.equals("reinsurerTreaty"))
            {
                storeReinsurerTreatyFormDataToSession(appReqBlock);
            }
        }
    }

    /**
     * Commits all information stored in "cloudland (i.e. session)" to the back end.
     * @param appReqBlock
     * @return
     */
    private String saveReinsurerDetails(AppReqBlock appReqBlock)
    {
        new ReinsuranceUseCaseComponent().updateReinsurance();

        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        ReinsurerVO reinsurerVO = cloud.getActiveReinsurerVO();

        TreatyVO[] treatyVOs = cloud.getTreatyVOs();

        Reinsurance reinsurance = new ReinsuranceComponent();

        String responseMessage = null;

        try
        {
            reinsurerVO = reinsurance.saveReinsurer(reinsurerVO);

            for (int i = 0; i < treatyVOs.length; i++)
            {
                TreatyVO currentTreatyVO = treatyVOs[i];

                if (currentTreatyVO.getTreatyPK() < 0) // It's a dummy PK for new Treaties in cloudland - reset it before saving.
                {
                    currentTreatyVO.setTreatyPK(0);
                }

                currentTreatyVO.setReinsurerFK(reinsurerVO.getReinsurerPK());

                if (currentTreatyVO.getVoShouldBeDeleted()) // The Treaty could have been flagged for deletion.
                {
                    reinsurance.deleteTreaty(currentTreatyVO.getTreatyPK());
                }
                else
                {
                    reinsurance.saveTreaty(treatyVOs[i]);
                }
            }

            appReqBlock.getUserSession().unlockReinsurer();

            cloud.clearCloudState();

            UserSession userSession = appReqBlock.getUserSession();

            userSession.unlockReinsurer();

            responseMessage = "Reinsurer Info Successfully Saved";
        }
        catch (EDITReinsuranceException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showReinsurerDetail(appReqBlock);
    }

    /**
     * The page defaults as empty, unless a Product Structure is available by which to load the Treaties.
     * @param appReqBlock
     * @return
     */
    private String showContractTreatyRelations(AppReqBlock appReqBlock)
    {
        // We've changed a number of assumptions (as of 01/15/2009). We can
        // have an active ContractGroup (Group), or an active Segment, but
        // never both at the same time.
        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), "0");

        String activeCasePK = Util.initString(appReqBlock.getReqParm("activeCasePK"), "0");

        String disableCaseSummary = Util.initString(appReqBlock.getReqParm("disableCaseSummary"), null);

        String disableSegmentSummary = Util.initString(appReqBlock.getReqParm("disableSegmentSummary"), null);

        ContractGroup activeCase = null;

        SegmentVO activeSegmentVO = null;

        SegmentVO[] segmentVOs = null;

        TreatyGroupVO[] treatyGroupVOs = null;

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        if (!activeSegmentPK.equals("0")) // A selected/searched-for Segment is driving the data.
        {
            activeSegmentVO = contractLookup.findSegmentBy_SegmentPK(Long.parseLong(activeSegmentPK));

            segmentVOs = new SegmentVO[]{activeSegmentVO};

            Reinsurance reinsurance = new ReinsuranceComponent();

            treatyGroupVOs = reinsurance.findTreatyGroupsBy_ProductStructurePK(activeSegmentVO.getProductStructureFK());
        }

        if (!activeCasePK.equals("0")) // A selected/searched-for Case is driving the data.
        {
            activeCase = ContractGroup.findByPK(new Long(activeCasePK));

            TreatyGroup[] treatyGroups = TreatyGroup.findBy_ContractGroupPK(new Long(activeCasePK));

            if (treatyGroups.length > 0)
            {
                treatyGroupVOs = new TreatyGroupVO[treatyGroups.length];

                for (int i = 0; i < treatyGroups.length; i++)
                {
                    TreatyGroup treatyGroup = treatyGroups[i];

                    TreatyGroupVO treatyGroupVO = (TreatyGroupVO) treatyGroup.getVO();

                    treatyGroupVOs[i] = treatyGroupVO;
                }
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("treatyGroupVOs", treatyGroupVOs);

        appReqBlock.getHttpServletRequest().setAttribute("activeCase", activeCase);

        appReqBlock.getHttpServletRequest().setAttribute("segmentVOs", segmentVOs);

        appReqBlock.getHttpServletRequest().setAttribute("activeSegmentVO", activeSegmentVO);

        //appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", activeProductStructurePK);

        appReqBlock.getHttpServletRequest().setAttribute("disableCaseSummary", disableCaseSummary);

        appReqBlock.getHttpServletRequest().setAttribute("disableSegmentSummary", disableSegmentSummary);

        return CONTRACT_TREATY_RELATIONS;
    }

    /**
     * Populates the contract treaty relations page with the searched-for coverage.
     * @param appReqBlock
     * @return
     */
    private String showCoverageSummaryAfterSearch(AppReqBlock appReqBlock)
    {
        String segmentPK = Util.initString(appReqBlock.getReqParm("segmentPK"), null);

        appReqBlock.setReqParm("activeSegmentPK", segmentPK);

        appReqBlock.setReqParm("disableCaseSummary", "true");

        return showContractTreatyRelations(appReqBlock);
    }

    /**
     * Shows the dialog from which a user can select a client to use as a reinsurer.
     */
    private String showReinsurerAddDialog(AppReqBlock appReqBlock)
    {
        return REINSURER_ADD_DIALOG;
    }

    /**
     * Shows the reinsurerDetail.jsp rendering any state currently in session.
     * @param appReqBlock
     * @return
     */
    private String showReinsurerDetail(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        ReinsurerVO reinsurerVO = cloud.getActiveReinsurerVO();

        ClientDetail clientDetail = null;

        ClientAddress clientAddress = null;
        
        ContactInformation[] contactInformations = null;

        if (reinsurerVO != null && reinsurerVO.getClientDetailFK() != 0)
        {
            long clientDetailPK = reinsurerVO.getClientDetailFK();

            clientDetail = ClientDetail.findByPK(new Long(clientDetailPK));
        }

        appReqBlock.getHttpServletRequest().setAttribute("reinsurerVO", reinsurerVO);

        appReqBlock.getHttpServletRequest().setAttribute("clientDetail", clientDetail);

        return REINSURER_DETAIL;
    }

    /**
     * Returns the reinsurerDetail.jsp populated with the just-selected ClientDetail info.
     * @param appReqBlock
     * @return
     */
    private String showReinsurerDetailAfterAdd(AppReqBlock appReqBlock)
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        try
        {
            userSession.lockReinsurer(0);
        }
        catch (EDITLockException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", message);
        }

        ReinsuranceCloud cloud = ((ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class));

        if (cloud != null)
        {
            cloud.clearCloudState();
        }

        ClientDetail clientDetail = null;

        ReinsurerVO reinsurerVO = null;

        String clientDetailPK = Util.initString(appReqBlock.getReqParm("clientDetailPK"), null);

        reinsurerVO = new ReinsurerVO();

        reinsurerVO.setClientDetailFK(Long.parseLong(clientDetailPK));

        cloud.setActiveReinsurerVO(reinsurerVO);

        clientDetail = ClientDetail.findByPK(new Long(clientDetailPK));

        appReqBlock.getHttpServletRequest().setAttribute("clientDetail", clientDetail);

        appReqBlock.getHttpServletRequest().setAttribute("reinsurerVO", reinsurerVO);

        return REINSURER_DETAIL;
    }

    /**
     * Shows the reinsurerTreaty.jsp with the summary of Treaties for this Reinsurer.
     * @param appReqBlock
     * @return
     */
    private String showReinsurerTreaty(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        Reinsurance reinsurance = new ReinsuranceComponent();

        TreatyVO treatyVO = cloud.getActiveTreatyVO();

        TreatyVO[] treatyVOs = cloud.getTreatyVOs(); 

        appReqBlock.getHttpServletRequest().setAttribute("treatyVOs", treatyVOs);

        appReqBlock.getHttpServletRequest().setAttribute("treatyVO", treatyVO);

        TreatyGroupVO[] treatyGroupVOs = reinsurance.findAllTreatyGroups();

        appReqBlock.getHttpServletRequest().setAttribute("treatyGroupVOs", treatyGroupVOs);

        return REINSURER_TREATY;
    }

    /**
     * Returns the ContractTreaty search dialog.
     * @param appReqBlock
     * @return
     */
    private String showSearchForTreatiesDialog(AppReqBlock appReqBlock)
    {
        return CONTRACT_TREATY_SEARCH_DIALOG;
    }

    /**
     * Returns the treatyHistory page.
     * @param appReqBlock
     * @return
     */
    private String showReinsurerHistory(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getCloud(reinsurance.ui.ReinsuranceCloud.class);

        ReinsurerVO reinsurerVO = cloud.getActiveReinsurerVO();

        ReinsuranceHistoryVO[] reinsuranceHistoryVOs = null;

        if (reinsurerVO != null)
        {
            Reinsurance reinsurance = new ReinsuranceComponent();

            reinsuranceHistoryVOs = reinsurance.findReinsuranceHistoryBy_ReinsurerPK(reinsurerVO.getReinsurerPK());
        }

        appReqBlock.getHttpServletRequest().setAttribute("reinsuranceHistoryVOs", reinsuranceHistoryVOs);

        return REINSURER_HISTORY;
    }

    /**
     * Returns the Product Structure to Treaty Group relations page.
     * @param appReqBlock
     * @return
     */
    private String showTreatyRelations(AppReqBlock appReqBlock)
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        reinsurance.business.Reinsurance reinsurance = new reinsurance.component.ReinsuranceComponent();

        ProductStructureVO[] productStructureVOs = engineLookup.findByTypeCode("Product", false, null);

        TreatyGroupVO[] treatyGroupVOs = reinsurance.findAllTreatyGroups();

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        appReqBlock.getHttpServletRequest().setAttribute("treatyGroupVOs", treatyGroupVOs);

        return TREATY_RELATIONS;
    }

    /**
     * Stores the form data of the reinsurerDetail page to session.
     * @param appReqBlock
     */
    private void storeReinsurerFormDataToSession(AppReqBlock appReqBlock)
    {
        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getUserSession().getCloudland().getCloud(reinsurance.ui.ReinsuranceCloud.class);

        ReinsurerVO reinsurerVO = mapFormDataToReinsurerVO(appReqBlock);

        cloud.setActiveReinsurerVO(reinsurerVO);
    }

    /**
     * Stores reinsurerTreaty.jsp form data to session.
     * @param appReqBlock
     */
    private void storeReinsurerTreatyFormDataToSession(AppReqBlock appReqBlock)
    {
        TreatyVO treatyVO = mapFormDataToTreatyVO(appReqBlock);

        ReinsuranceCloud cloud = (ReinsuranceCloud) appReqBlock.getUserSession().getCloudland().getCloud(reinsurance.ui.ReinsuranceCloud.class);

        cloud.setActiveTreatyVO(treatyVO);
    }

    /**
     * Maps submitted form data to the TreatyVO.
     * @param appReqBlock
     * @return
     */
    private ContractTreatyVO mapFormDataToContractTreatyVO(AppReqBlock appReqBlock)
    {
        String activeContractTreatyPK = Util.initString(appReqBlock.getReqParm("activeContractTreatyPK"), "0");
        String activeCasePK = Util.initString(appReqBlock.getReqParm("activeCasePK"), "0");
        String activeSegmentPK = Util.initString(appReqBlock.getReqParm("activeSegmentPK"), "0");
        String activeTreatyPK = Util.initString(appReqBlock.getReqParm("activeTreatyPK"), "0");

        String effectiveDateMonth = Util.initString(appReqBlock.getReqParm("effectiveDateMonth"), null);
        String effectiveDateDay = Util.initString(appReqBlock.getReqParm("effectiveDateDay"), null);
        String effectiveDateYear = Util.initString(appReqBlock.getReqParm("effectiveDateYear"), null);
        String effectiveDate = DateTimeUtil.initDate(effectiveDateMonth, effectiveDateDay, effectiveDateYear, null);

        String maxReinsuranceAmount = Util.initString(appReqBlock.getReqParm("maxReinsuranceAmount"), "0.00");
        String reinsuranceIndicatorCT = Util.initString(appReqBlock.getReqParm("reinsuranceIndicatorCT"), null);
        String treatyTypeCT = Util.initString(appReqBlock.getReqParm("treatyTypeCT"), null);
        String reinsuranceTypeCT = Util.initString(appReqBlock.getReqParm("reinsuranceTypeCT"), null);
        String retentionAmount = Util.initString(appReqBlock.getReqParm("retentionAmount"), "0.00");
        String poolPercentage = Util.initString(appReqBlock.getReqParm("poolPercentage"), "0.00");

        String reinsuranceClassCT = Util.initString(appReqBlock.getReqParm("reinsuranceClassCT"), null);
        String tableRatingCT = Util.initString(appReqBlock.getReqParm("tableRatingCT"), null);
        String flatExtra = Util.initString(appReqBlock.getReqParm("flatExtra"), "0.00");
        String flatExtraAge = Util.initString(appReqBlock.getReqParm("flatExtraAge"), "0");
        String flatExtraDuration = Util.initString(appReqBlock.getReqParm("flatExtraDuration"), "0");
        String percentExtra = Util.initString(appReqBlock.getReqParm("percentExtra"), "0");
        String percentExtraAge = Util.initString(appReqBlock.getReqParm("percentExtraAge"), "0");
        String percentExtraDuration = Util.initString(appReqBlock.getReqParm("percentExtraDuration"), "0");

        String treatyOverrideInd = Util.initString(appReqBlock.getReqParm("treatyOverrideInd"), "off");
        treatyOverrideInd = (treatyOverrideInd.equals("off")) ? "N" : "Y";
        String policyOverrideInd = Util.initString(appReqBlock.getReqParm("policyOverrideInd"), "off");
        policyOverrideInd = (policyOverrideInd.equals("off")) ? "N" : "Y";

        ContractTreatyVO contractTreatyVO = new ContractTreatyVO();

        contractTreatyVO.setContractTreatyPK(Long.parseLong(activeContractTreatyPK));
        //  COMMENTED OUT THE FOLLOWING LINE BECAUSE CASE WAS REPLACED WITH CONTRACTGROUP AND THE DETAILS OF HOW THAT
        //  WILL WORK WITH CONTRACTTREATY HAVE NOT BEEN DECIDED YET
//        contractTreatyVO.setCaseFK(Long.parseLong(activeCasePK));
        contractTreatyVO.setSegmentFK(Long.parseLong(activeSegmentPK));
        contractTreatyVO.setTreatyFK(Long.parseLong(activeTreatyPK));

        contractTreatyVO.setEffectiveDate(effectiveDate);

        contractTreatyVO.setMaxReinsuranceAmount(new BigDecimal(maxReinsuranceAmount));
        contractTreatyVO.setReinsuranceIndicatorCT(reinsuranceIndicatorCT);
        contractTreatyVO.setTreatyTypeCT(treatyTypeCT);
        contractTreatyVO.setReinsuranceTypeCT(reinsuranceTypeCT);
        contractTreatyVO.setRetentionAmount(new BigDecimal(retentionAmount));
        contractTreatyVO.setPoolPercentage(new BigDecimal(poolPercentage));

        contractTreatyVO.setReinsuranceClassCT(reinsuranceClassCT);
        contractTreatyVO.setTableRatingCT(tableRatingCT);
        contractTreatyVO.setFlatExtra(new BigDecimal(flatExtra));
        contractTreatyVO.setFlatExtraAge(Integer.parseInt(flatExtraAge));
        contractTreatyVO.setFlatExtraDuration(Integer.parseInt(flatExtraDuration));
        contractTreatyVO.setPercentExtra(new BigDecimal(percentExtra));
        contractTreatyVO.setPercentExtraAge(Integer.parseInt(percentExtraAge));
        contractTreatyVO.setPercentExtraDuration(Integer.parseInt(percentExtraDuration));

        contractTreatyVO.setTreatyOverrideInd(treatyOverrideInd);
        contractTreatyVO.setPolicyOverrideInd(policyOverrideInd);

        return contractTreatyVO;
    }

    /**
     * Maps form data to its VO representation.
     * @param appReqBlock
     * @return
     */
    private ReinsurerVO mapFormDataToReinsurerVO(AppReqBlock appReqBlock)
    {
        ReinsurerVO reinsurerVO = new ReinsurerVO();

        long reinsurerPK = Long.parseLong(Util.initString(appReqBlock.getReqParm("reinsurerPK"), "0"));
        String reinsurerNumber = Util.initString(appReqBlock.getReqParm("reinsurerNumber"), null);
        long clientDetailFK = Long.parseLong(Util.initString(appReqBlock.getReqParm("clientDetailFK"), "0"));

        reinsurerVO.setClientDetailFK(clientDetailFK);
        reinsurerVO.setReinsurerNumber(reinsurerNumber);
        reinsurerVO.setReinsurerPK(reinsurerPK);

        return reinsurerVO;
    }

    /**
     * Maps form data to its VO representation.
     * @param appReqBlock
     * @return
     */
    private TreatyVO mapFormDataToTreatyVO(AppReqBlock appReqBlock)
    {
        String startDateMonth = Util.initString(appReqBlock.getReqParm("startDateMonth"), null);
        String startDateDay = Util.initString(appReqBlock.getReqParm("startDateDay"), null);
        String startDateYear = Util.initString(appReqBlock.getReqParm("startDateYear"), null);
        String startDate = DateTimeUtil.initDate(startDateMonth, startDateDay, startDateYear, null);

        String stopDateMonth = Util.initString(appReqBlock.getReqParm("stopDateMonth"), null);
        String stopDateDay = Util.initString(appReqBlock.getReqParm("stopDateDay"), null);
        String stopDateYear = Util.initString(appReqBlock.getReqParm("stopDateYear"), null);
        String stopDate = DateTimeUtil.initDate(stopDateMonth, stopDateDay, stopDateYear, null);

        String settlementPeriod = Util.initString(appReqBlock.getReqParm("settlementPeriod"), "0");
        String retentionAmount = Util.initString(appReqBlock.getReqParm("retentionAmount"), "0.0");
        String poolPercentage = Util.initString(appReqBlock.getReqParm("poolPercentage"), "0.0");
        String reinsurerBalance = Util.initString(appReqBlock.getReqParm("reinsurerBalance"), "0.0");
        String paymentModeCT = Util.initString(appReqBlock.getReqParm("paymentModeCT"), null);
        String calculationModeCT = Util.initString(appReqBlock.getReqParm("calculationModeCT"), null);

        String lastCheckDateMonth = Util.initString(appReqBlock.getReqParm("lastCheckDateMonth"), null);
        String lastCheckDateDay = Util.initString(appReqBlock.getReqParm("lastCheckDateDay"), null);
        String lastCheckDateYear = Util.initString(appReqBlock.getReqParm("lastCheckDateYear"), null);
        String lastCheckDate = DateTimeUtil.initDate(lastCheckDateMonth, lastCheckDateDay, lastCheckDateYear, null);

        String reinsurerFK = Util.initString(appReqBlock.getReqParm("reinsurerFK"), "0");
        String treatyGroupFK = Util.initString(appReqBlock.getReqParm("treatyGroupFK"), "0");
        String treatyPK = Util.initString(appReqBlock.getReqParm("treatyPK"), "0");

        TreatyVO treatyVO = new TreatyVO();

        treatyVO.setCalculationModeCT(calculationModeCT);
        treatyVO.setPaymentModeCT(paymentModeCT);
        treatyVO.setPoolPercentage(new EDITBigDecimal(poolPercentage).getBigDecimal());
        treatyVO.setReinsurerFK(Long.parseLong(reinsurerFK));
        treatyVO.setSettlementPeriod(Integer.parseInt(settlementPeriod));
        treatyVO.setStartDate(startDate);
        treatyVO.setStopDate(stopDate);
        treatyVO.setTreatyGroupFK(Long.parseLong(treatyGroupFK));
        treatyVO.setTreatyPK(Long.parseLong(treatyPK));
        treatyVO.setReinsurerBalance(new EDITBigDecimal(reinsurerBalance).getBigDecimal());
        treatyVO.setLastCheckDate(lastCheckDate);
        treatyVO.setRetentionAmount(new EDITBigDecimal(retentionAmount).getBigDecimal());

        return treatyVO;
    }

    /**
     * Builds the set ProductStructure names for drop-down list.
     * @param includeChildVOs
     * @param voClassExclusionList
     * @return
     */
    private String[] findAllProductStructures(boolean includeChildVOs, String[] voClassExclusionList)
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVO = engineLookup.findAllProductStructureVOs(includeChildVOs, voClassExclusionList);

        Set uniqueCompanyNames = new HashSet();

        if (productStructureVO != null)
        {
            for (int i = 0; i < productStructureVO.length; i++)
            {
                Company company = Company.findByPK(productStructureVO[i].getCompanyFK());
                String companyName = company.getCompanyName();

                uniqueCompanyNames.add(companyName);
            }
        }

        Collections.sort(new ArrayList(uniqueCompanyNames));

        return (String[]) uniqueCompanyNames.toArray(new String[uniqueCompanyNames.size()]);
    }
}
