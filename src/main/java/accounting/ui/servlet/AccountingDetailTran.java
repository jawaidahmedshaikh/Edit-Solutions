/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package accounting.ui.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.codehaus.jettison.json.JSONObject;

import accounting.AccountingDetail;
import accounting.JournalAdjustmentLookup;
import accounting.business.Accounting;
import accounting.business.Lookup;
import accounting.dm.dao.FastDAO;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.GenericListWrapper;
import edit.common.vo.AccountVO;
import edit.common.vo.AccountingDetailVO;
import edit.common.vo.ChargeCodeVO;
import edit.common.vo.CompanyStructureNameVO;
import edit.common.vo.ElementCompanyRelationVO;
import edit.common.vo.ElementStructureVO;
import edit.common.vo.ElementVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.ReinsurerVO;
import edit.common.vo.TreatyGroupVO;
import edit.portal.common.transactions.Transaction;
import edit.portal.widget.AccountingDetailTableModel;
import edit.services.db.CRUD;
import engine.ChargeCode;
import engine.Company;
import engine.FilteredFund;
import engine.business.Calculator;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.dm.SMException;
import fission.global.AppReqBlock;
import fission.utility.DateTimeUtil;
import fission.utility.Util;
import reinsurance.business.Reinsurance;
import reinsurance.component.ReinsuranceComponent;


public class AccountingDetailTran extends Transaction {


    //These are the Actions of the Dialog Screen
	private static final String SHOW_COMPANY_STRUCTURE_DIALOG = "showCompanyStructureDialog";

	//These are Main Screen Actions
	private static final String SHOW_ACCOUNTING_MAIN_DEFAULT       = "showAccountingMainDefault";
	private static final String SHOW_ACCOUNTING_SUMMARY            = "showAccountingSummary";
    private static final String SHOW_ACCOUNTING_DETAIL             = "showAccountingDetail";
    private static final String SHOW_ACCOUNTING_RELATION           = "showAccountingRelation";
    private static final String SHOW_ACCOUNTING_COMPANY_STRUCTURE  = "showAccountingCompanyStructure";
	private static final String SHOW_CHART_SUMMARY_BY_CSID         = "showChartSummaryByCSID";
	private static final String SHOW_SELECTED_DETAILS              = "showSelectedDetails";
	private static final String SHOW_ELEMENT_HIGHLIGHT             = "showElementHighlight";
	private static final String SHOW_ATTACHED_ELEMENTS             = "showAttachedElements";
    private static final String SHOW_CLONE_COMPANY_STRUCTURE_DIALOG = "showCloneCompanyStructureDialog";
    private static final String APPLY_COMPANY_STRUCTURE            = "applyCompanyStructure";
	private static final String SAVE_COMPANY_STRUCTURE             = "saveCompanyStructure";
	private static final String UPDATE_CHART_DETAILS               = "updateChartDetails";
	private static final String CANCEL_COMPANY_STRUCTURE           = "cancelCompanyStructure";
    private static final String CLEAR_COMPANY_STRUCTURE            = "clearCompanyStructure";
	private static final String LOAD_DETAILS_FOR_ELEMENT		   = "loadDetailsForElement";
	private static final String DELETE_DETAILS_FOR_ELEMENT		   = "deleteDetailsForElement";
	private static final String CLEAR_CHART_DETAILS                = "clearChartDetails";
	private static final String ATTACH_ELEMENT_COMPANY             = "attachElementCompany";
	private static final String DETACH_ELEMENT_COMPANY             = "detachElementCompany";
	private static final String CANCEL_RELATION                    = "cancelRelation";
	private static final String DELETE_ELEMENT                     = "deleteElement";
    private static final String SHOW_JOURNAL_ADJUSTMENT            = "showJournalAdjustment";
    private static final String SHOW_JOURNAL_ADJUSTMENT_DETAIL_SUMMARY = "showJournalAdjustmentDetailSummary";
    private static final String ADD_JOURNAL_ADJUSTMENT             = "addJournalAdjustment";
    private static final String SAVE_JOURNAL_ADJUSTMENT            = "saveJournalAdjustment";
    private static final String CANCEL_JOURNAL_ADJUSTMENT             = "cancelJournalAdjustment";
    private static final String CLONE_COMPANY_STRUCTURE            = "cloneCompanyStructure";
    private static final String CLEAR_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY_FOR_ADD_OR_CANCEL = "clearAccountingDetailAccountSummaryForAddOrCancel";
    private static final String SAVE_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY           = "saveAccountingDetailAccountSummary";
    private static final String DELETE_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY         = "deleteAccountingDetailAccountSummary";
    private static final String SHOW_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY_DETAIL    = "showAccountingDetailAccountSummaryDetail";

	//Dialogs Returned
	private static final String COMPANY_STRUCTURE        = "/accounting/jsp/accountingCompanyStructureDialog.jsp";

	//Screens Returned
	private static final String CHART_SUMMARY_PAGE           = "/accounting/jsp/chartSummaryPage.jsp";
	private static final String CHART_DETAIL_PAGE            = "/accounting/jsp/chartDetail.jsp";
	private static final String CHART_RELATION_PAGE          = "/accounting/jsp/chartRelation.jsp";
	private static final String ACCOUNTING_COMPANY_STRUCTURE = "/accounting/jsp/accountingCompanyStructure.jsp";
    private static final String JOURNAL_ADJUSTMENT           = "/accounting/jsp/journalAdjustment.jsp";
    private static final String CLONE_COMPANY_STRUCTURE_DIALOG = "/accounting/jsp/cloneCompanyStructureDialog.jsp";

    /**
     * NOTE: companyStructureId and productStructure are used interchangeably
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    public String execute(AppReqBlock appReqBlock) throws Exception  {

        String action = appReqBlock.getReqParm("action");

		if (action.equals(SHOW_ACCOUNTING_MAIN_DEFAULT)) {

            return showAccountingMainDefault(appReqBlock);
        }
		else if (action.equals(SHOW_COMPANY_STRUCTURE_DIALOG)) {

            return showCompanyStructureDialog(appReqBlock);
        }
		else if (action.equals(SHOW_ACCOUNTING_SUMMARY)) {

            return showAccountingSummary(appReqBlock);
        }
		else if (action.equals(SHOW_ACCOUNTING_DETAIL)) {

            return showAccountingDetail(appReqBlock);
        }
		else if (action.equals(SHOW_ACCOUNTING_RELATION)) {

            return showAccountingRelation(appReqBlock);
        }
		else if (action.equals(SHOW_ACCOUNTING_COMPANY_STRUCTURE)) {

            return showAccountingCompanyStructure(appReqBlock);
        }
		else if (action.equals(SHOW_CHART_SUMMARY_BY_CSID)) {

            return showChartSummaryByCSID(appReqBlock);
        }
        else if (action.equals(APPLY_COMPANY_STRUCTURE)) {

            return applyCompanyStructure(appReqBlock);
        }
		else if (action.equals(SAVE_COMPANY_STRUCTURE)) {

            return saveCompanyStructure(appReqBlock);
        }
		else if (action.equals(CANCEL_COMPANY_STRUCTURE)) {

            return cancelCompanyStructure(appReqBlock);
        }
        else if (action.equals(CLEAR_COMPANY_STRUCTURE)) {

            return clearCompanyStructure(appReqBlock);
        }
		else if (action.equals(UPDATE_CHART_DETAILS)) {

            return updateChartDetails(appReqBlock);
        }
		else if (action.equals(LOAD_DETAILS_FOR_ELEMENT)) {

            return loadDetailsForElement(appReqBlock);
        }
		else if (action.equals(DELETE_DETAILS_FOR_ELEMENT)) {

            return deleteDetailsForElement(appReqBlock);
        }
		else if (action.equals(SHOW_SELECTED_DETAILS)) {

            return showSelectedDetails(appReqBlock);
        }
		else if (action.equals(	SHOW_ACCOUNTING_RELATION)) {

            return showAccountingRelation(appReqBlock);
        }
		else if (action.equals(CLEAR_CHART_DETAILS)) {

            return clearChartDetails(appReqBlock);
        }
		else if (action.equals(SHOW_ELEMENT_HIGHLIGHT)) {

            return showElementHighlight(appReqBlock);
        }
        else if (action.equals(SHOW_ATTACHED_ELEMENTS)) {

            return showAttachedElements(appReqBlock);
        }
		else if (action.equals(ATTACH_ELEMENT_COMPANY)) {

            return attachElementCompany(appReqBlock);
        }
        else if (action.equals(DETACH_ELEMENT_COMPANY)) {

            return detachElementCompany(appReqBlock);
        }
        else if (action.equals(CANCEL_RELATION)) {

            return cancelRelation(appReqBlock);
        }
        else if (action.equals(DELETE_ELEMENT)) {

            return deleteElement(appReqBlock);
        }
        else if (action.equals(SHOW_JOURNAL_ADJUSTMENT)) {

            return showJournalAdjustment(appReqBlock);
        }
        else if (action.equals(SHOW_JOURNAL_ADJUSTMENT_DETAIL_SUMMARY)) {

            return showJournalAdjustmentDetailSummary(appReqBlock);
        }
        else if (action.equals(ADD_JOURNAL_ADJUSTMENT)) {

            return addJournalAdjustment(appReqBlock);
        }
        else if (action.equals(CANCEL_JOURNAL_ADJUSTMENT)) {

            return cancelJournalAdjustment(appReqBlock);
        }
        else if (action.equals(SAVE_JOURNAL_ADJUSTMENT)) {

            return saveJournalAdjustment(appReqBlock);
        }
        else if (action.equals(SHOW_CLONE_COMPANY_STRUCTURE_DIALOG))
        {
            return showCloneCompanyStructureDialog(appReqBlock);
        }
        else if (action.equals(CLONE_COMPANY_STRUCTURE))
        {
            return cloneCompanyStructure(appReqBlock);
        }
        else if (action.equals(CLEAR_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY_FOR_ADD_OR_CANCEL))
        {
            return clearAccountingDetailAccountSummaryForAddOrCancel(appReqBlock);
        }
        else if (action.equals(SAVE_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY))
        {
            return saveAccountingDetailAccountSummary(appReqBlock);
        }
        else if (action.equals(SHOW_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY_DETAIL))
        {
            return showAccountingDetailAccountSummaryDetail(appReqBlock);
        }
        else if (action.equals(DELETE_ACCOUNTING_DETAIL_ACCOUNT_SUMMARY))
        {
            return deleteAccountingDetailAccountSummary(appReqBlock);
        }
        else
        {
            return CHART_SUMMARY_PAGE;
        }
    }

	protected String showAccountingMainDefault(AppReqBlock appReqBlock) throws Exception {

		buildElementSummary(appReqBlock);

		return CHART_SUMMARY_PAGE;
	}

	protected String showAccountingSummary(AppReqBlock appReqBlock) throws Exception {


        PageBean pageBean = appReqBlock.getSessionBean("accountingSummarySessionBean").getPageBean("formBean");
		String companyName = pageBean.getValue("companyName");
        String marketingPackage = pageBean.getValue("marketingPackage");
        String groupProduct     = pageBean.getValue("groupProduct");
        String areaName         = pageBean.getValue("areaName");
        String businessContract = pageBean.getValue("businessContract");
        String companyStructure = companyName + " " + marketingPackage + " " +
                                  groupProduct + " " + areaName + " " + businessContract;

        if ((companyName != null) && (!companyName.equals(""))) {

            appReqBlock.getFormBean().putValue("companyStructureId", pageBean.getValue("companyStructureId"));
            appReqBlock.getFormBean().putValue("companyStructure", companyStructure);
            return showChartSummaryByCSID(appReqBlock);
        }

        else {

            this.buildElementSummary(appReqBlock);

            return CHART_SUMMARY_PAGE;
        }
	}

	protected String showAccountingDetail(AppReqBlock appReqBlock) throws Exception {

		buildChartDetailLists(appReqBlock);

		String elementId = appReqBlock.getFormBean().getValue("elementId");

		if (! elementId.equals("") ) {

			buildChartDetailSummary(appReqBlock);
		}

		return CHART_DETAIL_PAGE;
	}

	protected String clearChartDetails(AppReqBlock appReqBlock) throws Exception {

		PageBean formBean = appReqBlock.getFormBean();

		formBean.removeValue("elementId");
		formBean.removeValue("sequence");

		formBean.removeValue("effectiveMonth");
		formBean.removeValue("effectiveDay");
		formBean.removeValue("effectiveYear");

		formBean.removeValue("process");
		formBean.removeValue("event");
		formBean.removeValue("eventType");
		formBean.removeValue("element");

		buildChartDetailLists(appReqBlock);

		return CHART_DETAIL_PAGE;
	}

	protected String showAccountingRelation(AppReqBlock appReqBlock) throws Exception {

		buildElementSummary(appReqBlock);
		buildAllCompanyStructures(appReqBlock);

		return CHART_RELATION_PAGE;
	}

	protected String showSelectedDetails(AppReqBlock appReqBlock) throws Exception {

		PageBean formBean  = appReqBlock.getFormBean();

		String key         = formBean.getValue("key");

		int indexOf_ = key.indexOf("_");

		String elementStructureId 	= key.substring(0, indexOf_);
		String accountId			= key.substring(indexOf_ + 1, key.length());

        Lookup lookup = new accounting.component.LookupComponent();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        AccountVO[] accountVO = null;

        if (!accountId.equals("")) {

            accountVO = lookup.getAccountByAccountId(Long.parseLong(accountId));
        }

		ElementStructureVO[] elementStructureVO = lookup.getElementStructureByElementStructureId(Long.parseLong(elementStructureId));

		String memoCode         = elementStructureVO[0].getMemoCode();
		String duration         = elementStructureVO[0].getCertainPeriod() + "";
		String qualNonQual	    = elementStructureVO[0].getQualNonQualCT();
		long   fund			    = elementStructureVO[0].getFundFK();
        String fundName         = "";

        if (fund != 0) {

            FilteredFundVO[] filteredFundVOs = engineLookup.getByFundId(fund);

            fundName = filteredFundVOs[0].getFundNumber();
        }

        else {

            fundName = "0";
        }

        long chargeCodeFK = elementStructureVO[0].getChargeCodeFK();

		String switchEffect   = elementStructureVO[0].getSwitchEffectInd();
		if (switchEffect.equalsIgnoreCase("Y")) {

			switchEffect = "checked";
		}

		String suppressedInd  = elementStructureVO[0].getSuppressAccountingInd();
		if (suppressedInd.equalsIgnoreCase("Y")) {

			suppressedInd = "checked";
		}

        String accountNumber = "";
        String accountName = "";
        String accountEffectId = "";
        String accountDescription = "";

        if (accountVO != null) {

            accountNumber  = accountVO[0].getAccountNumber();
            accountName	  = accountVO[0].getAccountName();
            accountEffectId	  = accountVO[0].getEffect();
            accountDescription = accountVO[0].getAccountDescription();
        }

		formBean.putValue("memoCode", memoCode);
		formBean.putValue("duration", duration);
		formBean.putValue("qualNonQual", qualNonQual);
		formBean.putValue("fundName", fundName);
        formBean.putValue("chargeCodeFK", chargeCodeFK + "");
		formBean.putValue("switchEffectIndStatus", switchEffect);
		formBean.putValue("suppressedIndStatus", suppressedInd);
		formBean.putValue("accountNumber", accountNumber);
		formBean.putValue("accountName", accountName);
		formBean.putValue("accountEffectId", accountEffectId);
		formBean.putValue("accountDescription", accountDescription);

		appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);

		buildChartDetailLists(appReqBlock);
		buildChartDetailSummary(appReqBlock);

		return CHART_DETAIL_PAGE;
	}

	protected String loadDetailsForElement(AppReqBlock appReqBlock) throws Exception {

		buildChartDetailLists(appReqBlock);

		buildChartDetailSummary(appReqBlock);

		return CHART_DETAIL_PAGE;
	}

	protected String deleteDetailsForElement(AppReqBlock appReqBlock) throws Exception {

        PageBean formBean = appReqBlock.getFormBean();

		String key = formBean.getValue("key");
        String elementId = formBean.getValue("elementId");
        String companyStructureId = formBean.getValue("companyStructureId");
        formBean.clearState();
        formBean.putValue("elementId", elementId);
        formBean.putValue("companyStructureId", companyStructureId);

        String accountId = "";
        String elementStructureId = "";

        if (!key.equals(""))
        {
            accountId = key.substring(key.indexOf("_") + 1);
            elementStructureId = key.substring(0, key.indexOf("_"));
        }

        Accounting accounting = new accounting.component.AccountingComponent();
        Lookup accountingLookup = new accounting.component.LookupComponent();

        if (Util.isANumber(accountId))
        {
            long primaryKey = Long.parseLong(accountId);

            accounting.deleteVO(AccountVO.class, primaryKey, false);
        }

        if (Util.isANumber(elementStructureId))
        {
            AccountVO[] accountVOs = accountingLookup.getAccountsByElementStructureId(Long.parseLong(elementStructureId));

            if (accountVOs == null) {

                accounting.deleteVO(ElementStructureVO.class, Long.parseLong(elementStructureId), false);
            }
        }

        if (!Util.isANumber(accountId) && !Util.isANumber(elementStructureId))
        {
            appReqBlock.getHttpServletRequest().setAttribute("message", "Please Select Item For Deletion");
        }

        buildChartDetailLists(appReqBlock);
        buildChartDetailSummary(appReqBlock);

		return CHART_DETAIL_PAGE;
	}

	protected String updateChartDetails(AppReqBlock appReqBlock) throws Exception {

        Lookup lookup = new accounting.component.LookupComponent();
        engine.business.Lookup lookupComponent = new engine.component.LookupComponent();

		PageBean formBean = appReqBlock.getFormBean();

		String key = formBean.getValue("key");

		String elementStructureId = "0";
		String accountId          = "0";

		if (! key.equals("")) {

			elementStructureId = key.substring(0, key.indexOf("_"));
			accountId          = key.substring(key.indexOf("_") + 1, key.length());
		}

		String elementId      = formBean.getValue("elementId");

		String sequence       = formBean.getValue("sequence");

		String effectiveMonth = formBean.getValue("effectiveMonth");
		String effectiveDay   = formBean.getValue("effectiveDay");
		String effectiveYear  = formBean.getValue("effectiveYear");
		String effectiveDate  = DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear, null);

		String process      = formBean.getValue("process");
		String event        = formBean.getValue("event");
		String eventType    = formBean.getValue("eventType");
		String element      = formBean.getValue("element");
		String operator     = (String) appReqBlock.getHttpSession().getAttribute("userId");
		String maintDate    = new EDITDateTime().getFormattedDateTime();

		String memoCode 	= formBean.getValue("memoCode");
		String duration		= formBean.getValue("duration");
        if (duration.equals("*")) {

            duration = "0";
        }

		String qualNonQual	= formBean.getValue("qualNonQual");
		String fundName		= formBean.getValue("fundName");
        long fundFK = 0;

        if (!fundName.equals("*")) {

            FilteredFundVO[] filteredFundVOs = lookupComponent.getByFundNumber(fundName);

            if (filteredFundVOs != null)  {

                fundFK = filteredFundVOs[0].getFundFK();
            }
        }

        String chargeCodeFKStr = formBean.getValue("chargeCodeFK");
        long chargeCodeFK = 0L;
        if (chargeCodeFKStr != null && chargeCodeFKStr.trim().length() > 0)
        {
            chargeCodeFK = Long.parseLong(chargeCodeFKStr);
        }

		String accountNumber 		= formBean.getValue("accountNumber");
		String accountName			= formBean.getValue("accountName");
		String accountEffect		= formBean.getValue("accountEffectId");
		String accountDescription	= formBean.getValue("accountDescription");

		String switchEffectIndStatus = formBean.getValue("switchEffectIndStatus");
		if (switchEffectIndStatus.equalsIgnoreCase("checked") ) {

			switchEffectIndStatus = "Y";
		}

        else {

            switchEffectIndStatus = "N";
        }

		String suppressedIndStatus = formBean.getValue("suppressedIndStatus");
		if (suppressedIndStatus.equalsIgnoreCase("checked") ) {

			suppressedIndStatus = "Y";
		}

        else {

            suppressedIndStatus = "N";
        }

        ElementStructureVO elementStructureVO = new ElementStructureVO();

        ElementVO elementVO = null;
        ElementVO[] elementVOs = null;
        ElementStructureVO[] oldElementStructureVO = null;
        ElementStructureVO[] elementStructureVOs   = null;
		AccountVO accountVO 					   = null;
        AccountVO[] accountVOs                     = null;

        if (!elementId.equals("")) {

            elementVOs = lookup.getElementById(Long.parseLong(elementId));

            elementVO = elementVOs[0];
        }

        else {

            elementVO = new ElementVO();
            elementVO.setElementPK(0);
        }

        elementVO.setEffectiveDate(effectiveDate);
        elementVO.setSequenceNumber(Integer.parseInt(sequence));
        elementVO.setProcess(process);
        elementVO.setEvent(event);
        elementVO.setEventType(eventType);
        elementVO.setElementName(element);
        elementVO.setOperator(operator);
        elementVO.setMaintDateTime(maintDate);

        if (!elementId.equals("")) {

            oldElementStructureVO = lookup.getElementStructureByNames(Long.parseLong(elementId),
                                                                       memoCode,
                                                                        Integer.parseInt(duration),
                                                                         qualNonQual,
                                                                          fundFK,
                                                                           chargeCodeFK);
        }

        if (oldElementStructureVO == null) {
            //if entry of structure not required more code needed here to determine creation

            elementStructureVO.setElementStructurePK(Long.parseLong(elementStructureId));
            elementStructureVO.setElementFK(elementVO.getElementPK());
            elementStructureVO.setMemoCode(memoCode);
            elementStructureVO.setCertainPeriod(Integer.parseInt(duration));
            elementStructureVO.setQualNonQualCT(qualNonQual);
            elementStructureVO.setFundFK(fundFK);
            if (chargeCodeFK > 0L)
            {
                elementStructureVO.setChargeCodeFK(chargeCodeFK);
            }
            elementStructureVO.setSwitchEffectInd(switchEffectIndStatus);
            elementStructureVO.setSuppressAccountingInd(suppressedIndStatus);

            if (!accountNumber.equals("")) {

                accountVO = new AccountVO();
                accountVO.setAccountPK(Long.parseLong(accountId));
                accountVO.setElementStructureFK(Long.parseLong(elementStructureId));
                accountVO.setAccountNumber(accountNumber);
                accountVO.setAccountName(accountName);
                accountVO.setEffect(accountEffect);
                accountVO.setAccountDescription(accountDescription);
                elementStructureVO.addAccountVO(accountVO);
            }

            elementVO.addElementStructureVO(elementStructureVO);
        }

        else {

            elementStructureVOs = elementVO.getElementStructureVO();

            for (int i = 0; i < elementStructureVOs.length; i++) {

                if (elementStructureVOs[i].getElementStructurePK() == oldElementStructureVO[0].getElementStructurePK()) {
              //match ids

                    elementStructureVOs[i].setElementFK(Long.parseLong(elementId));
                    elementStructureVOs[i].setMemoCode(memoCode);
                    elementStructureVOs[i].setCertainPeriod(Integer.parseInt(duration));
                    elementStructureVOs[i].setQualNonQualCT(qualNonQual);
                    elementStructureVOs[i].setFundFK(fundFK);
                    if (chargeCodeFK > 0L)
                    {
                        elementStructureVOs[i].setChargeCodeFK(chargeCodeFK);
                    }
                    elementStructureVOs[i].setSwitchEffectInd(switchEffectIndStatus);
                    elementStructureVOs[i].setSuppressAccountingInd(suppressedIndStatus);

                    if (!accountNumber.equals("")) {

                        if (accountId.equals("0")) {

                            accountVO = new AccountVO();
                            accountVO.setAccountPK(Long.parseLong(accountId));
                            accountVO.setElementStructureFK(oldElementStructureVO[0].getElementStructurePK());
                            accountVO.setAccountNumber(accountNumber);
                            accountVO.setAccountName(accountName);
                            accountVO.setEffect(accountEffect);
                            accountVO.setAccountDescription(accountDescription);
                            elementStructureVOs[i].addAccountVO(accountVO);
                        }

                        else {

                            accountVOs = elementStructureVOs[i].getAccountVO();

                            for (int j = 0; j < accountVOs.length; j++) {

                                if (accountVOs[j].getAccountPK() == (Long.parseLong(accountId))) {

                                    accountVOs[j].setAccountNumber(accountNumber);
                                    accountVOs[j].setAccountName(accountName);
                                    accountVOs[j].setEffect(accountEffect);
                                    accountVOs[j].setAccountDescription(accountDescription);
                                }
                            }
                        }
                    }
                }
            }
        }

        Accounting accounting = new accounting.component.AccountingComponent();

		long elementPK = accounting.createOrUpdateVO(elementVO, true);
        elementId = elementPK + "";
		appReqBlock.getFormBean().putValue("elementId", elementId);

		buildChartDetailSummary(appReqBlock);

		buildChartDetailLists(appReqBlock);

		buildElementSummary(appReqBlock);

		appReqBlock.getHttpServletRequest().setAttribute("formBean",formBean);

		return CHART_DETAIL_PAGE;
	}

	protected String showAccountingCompanyStructure(AppReqBlock appReqBlock) throws Exception  {

		buildCompanyStructureLists(appReqBlock);

		return ACCOUNTING_COMPANY_STRUCTURE;
	}

    protected String applyCompanyStructure(AppReqBlock aAppReqBlock) throws SMException, Exception {

		String newCompanyStructure;

		PageBean pageBean = new PageBean();

		//get company Structure from request
   	    newCompanyStructure = aAppReqBlock.getReqParm("companyStructure");

        Calculator calcComponent = new engine.component.CalculatorComponent();

        calcComponent.saveProductStructure(newCompanyStructure);

        buildCompanyStructureLists(aAppReqBlock);

		//Selected Values for Company structure page
		StringTokenizer companyStructure = new StringTokenizer(newCompanyStructure,",");
		String company = companyStructure.nextToken();
		String marketing = companyStructure.nextToken();
		String groupProduct = companyStructure.nextToken();
		String area = companyStructure.nextToken();
		String business = companyStructure.nextToken();

        pageBean.putValue("company",company);
		pageBean.putValue("marketingPackage",marketing);
		pageBean.putValue("groupProduct",groupProduct);
		pageBean.putValue("area",area);
		pageBean.putValue("businessContract",business);

		//add page Bean to request
		aAppReqBlock.getHttpServletRequest().setAttribute("formBean",pageBean);

		return ACCOUNTING_COMPANY_STRUCTURE;
    }

	protected String saveCompanyStructure(AppReqBlock appReqBlock) throws Exception {

		//get company Structure from request
		String newCompanyStructure =
	    		         appReqBlock.getFormBean().getValue("companyStructure");

        Calculator calcComponent = new engine.component.CalculatorComponent();
        calcComponent.saveProductStructure(newCompanyStructure);

		buildCompanyStructureLists(appReqBlock);

		return ACCOUNTING_COMPANY_STRUCTURE;
	}

	protected String cancelCompanyStructure(AppReqBlock appReqBlock) throws Exception {

		buildCompanyStructureLists(appReqBlock);

		return ACCOUNTING_COMPANY_STRUCTURE;
	}

    protected String clearCompanyStructure(AppReqBlock aAppReqBlock) throws SMException,Exception {

		PageBean pageBean = new PageBean();

        buildCompanyStructureLists(aAppReqBlock);

		//add page Bean to request
		aAppReqBlock.getHttpServletRequest().setAttribute("formBean",pageBean);

		return ACCOUNTING_COMPANY_STRUCTURE;
    }

	//Dialog Actions

	protected String showCompanyStructureDialog(AppReqBlock appReqBlock) throws Exception {

		buildFilteredCompanyStructures(appReqBlock);

		return COMPANY_STRUCTURE;
	}

	protected String showChartSummaryByCSID(AppReqBlock appReqBlock) throws Exception {

		String companyStructureId = appReqBlock.getFormBean().getValue("companyStructureId");
		String companyStructure   = appReqBlock.getFormBean().getValue("companyStructure");

		// Not to be in session scope but request scope
		SessionBean elements = new SessionBean();

        Lookup lookup = new accounting.component.LookupComponent();

		ElementVO[] elementVOs = lookup.getAllElementsByProductStructure(Long.parseLong(companyStructureId));

		if (elementVOs != null) {

			for(int i = 0;  i < elementVOs.length; i++) {

				String elementId     = elementVOs[i].getElementPK() + "";
				String sequence      = elementVOs[i].getSequenceNumber() + "";
				String effectiveDate = elementVOs[i].getEffectiveDate();
				String process       = elementVOs[i].getProcess();
				String event         = elementVOs[i].getEvent();
				String eventType     = elementVOs[i].getEventType();
				String element		 = elementVOs[i].getElementName();

				PageBean elementBean = new PageBean();

				elementBean.putValue("sequence", sequence);
				elementBean.putValue("elementId", elementId);
				elementBean.putValue("effectiveDate", effectiveDate);
				elementBean.putValue("process", process);
				elementBean.putValue("event", event);
				elementBean.putValue("eventType", eventType);
				elementBean.putValue("element", element);

				elements.putPageBean(elementId, elementBean);
			}

			// Tokenize the Company Structure
			StringTokenizer tokenizer = new StringTokenizer(companyStructure);

			String companyName      = tokenizer.nextToken();
			String marketingPackage = tokenizer.nextToken();
			String groupProduct     = tokenizer.nextToken();
			String areaName         = tokenizer.nextToken();
			String businessContract = tokenizer.nextToken();

			PageBean formBean = new PageBean();

			formBean.putValue("companyName", companyName);
			formBean.putValue("marketingPackage", marketingPackage);
			formBean.putValue("groupProduct", groupProduct);
			formBean.putValue("areaName", areaName);
			formBean.putValue("businessContract", businessContract);
            formBean.putValue("companyStructureId", companyStructureId);

			appReqBlock.getSessionBean("accountingSummarySessionBean").putPageBean("formBean", formBean);

            appReqBlock.getHttpServletRequest().setAttribute("elements", elements);
		}

		else {

			PageBean formBean = appReqBlock.getFormBean();

			formBean.removeValue("companyName");
			formBean.removeValue("marketingPackage");
			formBean.removeValue("groupProduct");
			formBean.removeValue("areaName");
			formBean.removeValue("businessContract");

            appReqBlock.getSessionBean("accountingSummarySessionBean").putPageBean("formBean", formBean);

            buildElementSummary(appReqBlock);
		}

		return CHART_SUMMARY_PAGE;
	}

	protected String showElementHighlight(AppReqBlock appReqBlock) throws Exception {

		PageBean formBean = appReqBlock.getFormBean();
		String elementId = formBean.getValue("selectedElementId");

		buildElementSummary(appReqBlock);
		buildAllCompanyStructures(appReqBlock);

		formBean.putValue("selectedElementId", elementId);

		appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);

		return CHART_RELATION_PAGE;
	}

    /**
     * After a company structure is selected on the Chart Relation page, this method is invoked in order to dispaly all
     * Elements on the Element table in the Accounting Component and highlight the elements attached to the company
     * structure selected.  The company structure will always be selected for this method.
     * @param appReqBlock
     * @return jsp page - chartRelation.jsp
     * @throws Exception
     */
	protected String showAttachedElements(AppReqBlock appReqBlock) throws Exception {

		PageBean formBean = appReqBlock.getFormBean();
		String productStructureId = formBean.getValue("selectedCompanyStructureId");

        accounting.business.Lookup accountingLookup = new accounting.component.LookupComponent();

        ElementVO[] attachedElementVOs = accountingLookup.getAllElementsByProductStructure(Long.parseLong(productStructureId));
        appReqBlock.getHttpServletRequest().setAttribute("attachedElementVOs", attachedElementVOs);

		buildElementSummary(appReqBlock);
		buildAllCompanyStructures(appReqBlock);

		formBean.putValue("selectedCompanyStructureId", productStructureId);

		appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);

		return CHART_RELATION_PAGE;
	}

    /**
     * For the company structure and elements selected on the page, ElementCompanyRelation records will be built and
     * added to the table.  Errors will occur if the company structure is not selected, set to zero, and if element(s)
     * are not selected, set to null. After update to the table, the page is rebuilt showing all existing and new relations.
     * @param appReqBlock
     * @return  jsp page - chartRelation.jsp
     * @throws Exception
     */
	protected String attachElementCompany(AppReqBlock appReqBlock) throws Exception {

		PageBean formBean         = appReqBlock.getFormBean();

		String productStructureId = initParam(formBean.getValue("selectedCompanyStructureId"), "0");

        if (productStructureId.equals("0"))
        {
            String message = "Company Structure Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            buildElementSummary(appReqBlock);
            buildAllCompanyStructures(appReqBlock);

            return CHART_RELATION_PAGE;
        }

        String selectedElementPKs = initParam(appReqBlock.getReqParm("selectedElementPKs"), null);

        if (selectedElementPKs == null)
        {
            String message = "Element Selection Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            buildElementSummary(appReqBlock);
            buildAllCompanyStructures(appReqBlock);

            return CHART_RELATION_PAGE;
        }

        String[] elementsToAttachTokens = Util.fastTokenizer(selectedElementPKs, ",");

        List elementsToAttach = new ArrayList();

        for (int i = 0; i < elementsToAttachTokens.length; i++)
        {
            if (Util.isANumber(elementsToAttachTokens[i]))
            {
                elementsToAttach.add(new Long(elementsToAttachTokens[i]));
            }
        }

        accounting.business.Accounting accounting =  new accounting.component.AccountingComponent();
        accounting.business.Lookup accountingLookup = new accounting.component.LookupComponent();

		accounting.attachElement(Long.parseLong(productStructureId), Util.convertLongToPrim((Long[]) elementsToAttach.toArray(new Long[elementsToAttach.size()])));

        ElementVO[] attachedElementVOs = accountingLookup.getAllElementsByProductStructure(Long.parseLong(productStructureId));

		buildElementSummary(appReqBlock);
		buildAllCompanyStructures(appReqBlock);

		formBean.removeValue("selectedElementPKs");

        appReqBlock.getHttpServletRequest().setAttribute("attachedElementVOs", attachedElementVOs);
		appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);

		return CHART_RELATION_PAGE;
	}

    /**
     * For the company structure and elements selected on the page, ElementCompanyRelation records will be deleted from
     * the table.  Errors will occur if the company structure is not selected, set to zero, and if element(s)
     * are not selected, set to null. After update to the table, the page is rebuilt showing all existing relations.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
	protected String detachElementCompany(AppReqBlock appReqBlock) throws Exception {

		PageBean formBean         = appReqBlock.getFormBean();

		String productStructureId = initParam(formBean.getValue("selectedCompanyStructureId"), "0");

        if (productStructureId.equals("0"))
        {
            String message = "Company Structure Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            buildElementSummary(appReqBlock);
            buildAllCompanyStructures(appReqBlock);

            return CHART_RELATION_PAGE;
        }

       String selectedElementPKs = initParam(appReqBlock.getReqParm("selectedElementPKs"), null);

        if (selectedElementPKs == null)
        {
            String message = "Element Selection Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            buildElementSummary(appReqBlock);
            buildAllCompanyStructures(appReqBlock);

            return CHART_RELATION_PAGE;
        }

        String[] elementsToDetachTokens = Util.fastTokenizer(selectedElementPKs, ",");

        List elementsToDetach = new ArrayList();

        for (int i = 0; i < elementsToDetachTokens.length; i++)
        {
            if (Util.isANumber(elementsToDetachTokens[i]))
            {
                elementsToDetach.add(new Long(elementsToDetachTokens[i]));
            }
        }

        accounting.business.Accounting accounting =  new accounting.component.AccountingComponent();
        accounting.business.Lookup accountingLookup = new accounting.component.LookupComponent();

		accounting.detachElement(Util.convertLongToPrim((Long[]) elementsToDetach.toArray(new Long[elementsToDetach.size()])), Long.parseLong(productStructureId));

        ElementVO[] attachedElementVOs = accountingLookup.getAllElementsByProductStructure(Long.parseLong(productStructureId));

		buildElementSummary(appReqBlock);
		buildAllCompanyStructures(appReqBlock);

		formBean.removeValue("selectedElementPKs");

        appReqBlock.getHttpServletRequest().setAttribute("attachedElementVOs", attachedElementVOs);
		appReqBlock.getHttpServletRequest().setAttribute("formBean", formBean);

		return CHART_RELATION_PAGE;
	}

	protected String cancelRelation(AppReqBlock appReqBlock) throws Exception {

		buildElementSummary(appReqBlock);
		buildAllCompanyStructures(appReqBlock);

		return CHART_RELATION_PAGE;
	}

	protected String deleteElement(AppReqBlock appReqBlock) throws Exception {

		PageBean formBean = appReqBlock.getFormBean();

       String selectedElementPKs = initParam(appReqBlock.getReqParm("selectedElementPKs"), null);

        String productStructureId = Util.initString(formBean.getValue("selectedCompanyStructureId"), "0");

        Accounting accounting =  new accounting.component.AccountingComponent();
        String message = null;

        if (!productStructureId.equals("0") && selectedElementPKs == null)
        {
            message = "Cannot Delete Company Structure";
        }
        else if (selectedElementPKs != null)
        {
            String[] elementsToDeleteTokens = Util.fastTokenizer(selectedElementPKs, ",");

            // verify if attached
            ElementCompanyRelationVO[] elementCompanyRelationVOs = accounting.findRelationByElementPK(Long.parseLong(elementsToDeleteTokens[0]));
            if (elementCompanyRelationVOs != null)
            {
                message = "Cannot Delete, Element Is Attached";
            }
            else
            {
                ElementStructureVO[] elementStructureVOs = accounting.findStructuresByElementPK(Long.parseLong(elementsToDeleteTokens[0]));
                if (elementStructureVOs != null)
                {
                    message = "Cannot Delete, Account Information Still Attached To Element";
                }
                else
                {
                    accounting.deleteVO(ElementVO.class,(Long.parseLong(elementsToDeleteTokens[0])), false);
                    message = "Delete Successful";
                }
            }
        }
        else
        {
            message = "Element not Selected for Deletion";
        }

        appReqBlock.getHttpServletRequest().setAttribute("message", message);

		buildElementSummary(appReqBlock);
		buildAllCompanyStructures(appReqBlock);

		return CHART_RELATION_PAGE;
	}

    protected String showJournalAdjustment(AppReqBlock appReqBlock) throws Exception
    {
        placeListValuesInRequestScopeToPopulateDropDownBoxes(appReqBlock);

        new AccountingDetailTableModel(appReqBlock);

        return JOURNAL_ADJUSTMENT;
    }

    protected String showJournalAdjustmentDetailSummary(AppReqBlock appReqBlock) throws Exception
    {
        String isAccountingDetailSelectedFromSummary = appReqBlock.getReqParm("isAccountingDetailSelectedFromSummary");

        if (isAccountingDetailSelectedFromSummary != null && isAccountingDetailSelectedFromSummary.equals("Y"))
        {
            String selectedAccountingDetailPK = new AccountingDetailTableModel(appReqBlock).getSelectedRowId();

            AccountingDetailVO selectedAccountingDetailVO = (AccountingDetailVO) AccountingDetail.findByPK(Long.parseLong(selectedAccountingDetailPK)).getVO();

            appReqBlock.putInRequestScope("selectedAccountingDetailVO", selectedAccountingDetailVO);

            // To handle AccountInfoSummary
            List accountingDetailAccountSummaryVOs = new ArrayList();

            accountingDetailAccountSummaryVOs.add(selectedAccountingDetailVO);

            appReqBlock.putInSessionScope("AccountingDetail.JournalAdjustment.AccountSummary", accountingDetailAccountSummaryVOs);

            appReqBlock.putInRequestScope("selectedAccountingDetailAccountSummaryVO", selectedAccountingDetailVO);
        }

        return showJournalAdjustment(appReqBlock);
    }

    protected String addJournalAdjustment(AppReqBlock appReqBlock) throws Exception
    {
        AccountingDetailVO accountingDetailVO = new AccountingDetailVO();

        accountingDetailVO.setProcessDate(new EDITDate().getFormattedDate());

        accountingDetailVO.setAccountingProcessDate(new EDITDate().getFormattedDate());

        appReqBlock.putInRequestScope("selectedAccountingDetailVO", accountingDetailVO);

        appReqBlock.putInSessionScope("AccountingDetail.JournalAdjustment.AccountSummary", null);

        new AccountingDetailTableModel(appReqBlock).resetAllRows();

        return showJournalAdjustment(appReqBlock);
    }

    protected String cancelJournalAdjustment(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.putInSessionScope("AccountingDetail.JournalAdjustment.AccountSummary", null);

        new AccountingDetailTableModel(appReqBlock).resetAllRows();

        return showJournalAdjustment(appReqBlock);
    }

    protected String saveJournalAdjustment(AppReqBlock appReqBlock) throws Exception
    {
        String accountingDetailPK = Util.initString(appReqBlock.getReqParm("selectedAccountingDetailPK"), "0");

        String effectiveDate = Util.initString(appReqBlock.getReqParm("effectiveDate"), null);
        String accountingProcessDate = Util.initString(appReqBlock.getReqParm("accountingProcessDate"), null);
        String accountingPeriod = Util.initString(appReqBlock.getReqParm("accountingPeriod"), null);
        // disabled field ... always should be populated with current date
//        String processDate = Util.initString(appReqBlock.getReqParm("processDate"), null);

        String companyName = Util.initString(appReqBlock.getReqParm("companyName"), null);
        String marketingPackageName = Util.initString(appReqBlock.getReqParm("marketingPackageName"), null);
        String groupName = Util.initString(appReqBlock.getReqParm("groupName"), null);
        String groupNumber = Util.initString(appReqBlock.getReqParm("groupNumber"), null);
        String businessContractName = Util.initString(appReqBlock.getReqParm("businessContractName"), null);

        String contractNumber = Util.initString(appReqBlock.getReqParm("contractNumber"), null);
        String transactionType = Util.initString(appReqBlock.getReqParm("transactionType"), null);
        String coverage = Util.initString(appReqBlock.getReqParm("coverage"), null);
        String qualNonQual = Util.initString(appReqBlock.getReqParm("qualNonQual"), null);

        String qualifiedType = Util.initString(appReqBlock.getReqParm("qualifiedType"), null);
        String fundNumber = Util.initString(appReqBlock.getReqParm("fundNumber"), null);
        String reinsurerNumber = Util.initString(appReqBlock.getReqParm("reinsurerNumber"), null);
        String treatyGroupNumber = Util.initString(appReqBlock.getReqParm("treatyGroupNumber"), null);

        String comments = Util.initString(appReqBlock.getReqParm("comments"), null);

      //String ignoreDebitCreditAmountDifference = Util.initString(appReqBlock.getReqParm("ignoreDebitCreditAmountDifference"), null);

        accounting.business.Accounting accountingComponent = new accounting.component.AccountingComponent();

        List accountingDetailAccountSummaryVOs = (List) appReqBlock.getHttpSession().getAttribute("AccountingDetail.JournalAdjustment.AccountSummary");

        boolean isCreditAmountEqualsDebitAmount = false;

        // Check for the credit and debit amounts only when saving new records.
        if (Long.parseLong(accountingDetailPK) == 0L)
        {
                if (accountingDetailAccountSummaryVOs != null)
                {
                    EDITBigDecimal debitAmount = new EDITBigDecimal();
                    EDITBigDecimal creditAmount = new EDITBigDecimal();

                    for (int i = 0; i < accountingDetailAccountSummaryVOs.size(); i++)
                    {
                        AccountingDetailVO accountingDetailAccountSummaryVO = (AccountingDetailVO) accountingDetailAccountSummaryVOs.get(i);

                        if (accountingDetailAccountSummaryVO.getDebitCreditInd().equals(AccountingDetail.DEBITCREDITIND_DEBIT))
                        {
                            debitAmount = debitAmount.addEditBigDecimal(accountingDetailAccountSummaryVO.getAmount());
                        }
                        else if (accountingDetailAccountSummaryVO.getDebitCreditInd().equals(AccountingDetail.DEBITCREDITIND_CREDIT))
                        {
                            creditAmount = creditAmount.addEditBigDecimal(accountingDetailAccountSummaryVO.getAmount());
                        }
                    }

                    if (debitAmount.isEQ(creditAmount))
                    {
                        isCreditAmountEqualsDebitAmount = true;
                    }
                    else
                    {	
                    	saveAccountDetailInformation(appReqBlock);
                    	
                        appReqBlock.getHttpServletRequest().setAttribute("responseMessageType", "warning");
                        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Warning: Sum Of Debit Amounts Does Not Equal Sum Of Credit Amounts!");
                    }
                }
        }
        else
        {
            isCreditAmountEqualsDebitAmount = true;
        }

        if (isCreditAmountEqualsDebitAmount)
        {
            if (accountingDetailAccountSummaryVOs != null)
            {
                for (int i = 0; i < accountingDetailAccountSummaryVOs.size(); i++)
                {
                    AccountingDetailVO accountingDetailAccountSummaryVO = (AccountingDetailVO) accountingDetailAccountSummaryVOs.get(i);

                    AccountingDetailVO accountingDetailVO = new AccountingDetailVO();
                    accountingDetailVO.setAccountingDetailPK(Long.parseLong(accountingDetailPK));

                    accountingDetailVO.setEffectiveDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(effectiveDate));
                    if (accountingProcessDate != null)
                    {
                        accountingDetailVO.setAccountingProcessDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(accountingProcessDate));
                    }
                    // the requirement is to populate this field with current date and not enterable
                    accountingDetailVO.setProcessDate(new EDITDate().getFormattedDate());                    
                    accountingDetailVO.setAccountingPeriod(accountingPeriod);

                    accountingDetailVO.setCompanyName(companyName);
                    accountingDetailVO.setMarketingPackageName(marketingPackageName);
                    accountingDetailVO.setGroupName(groupName);
                    accountingDetailVO.setGroupNumber(groupNumber);
                    accountingDetailVO.setBusinessContractName(businessContractName);

                    accountingDetailVO.setContractNumber(contractNumber);
                    accountingDetailVO.setTransactionCode(transactionType);
                    accountingDetailVO.setOptionCodeCT(coverage);
                    accountingDetailVO.setQualNonQualCT(qualNonQual);

                    accountingDetailVO.setQualifiedTypeCT(qualifiedType);
                    accountingDetailVO.setFundNumber(fundNumber);
                    accountingDetailVO.setReinsurerNumber(reinsurerNumber);
                    accountingDetailVO.setTreatyGroupNumber(treatyGroupNumber);

                    accountingDetailVO.setComments(comments);

                    accountingDetailVO.setReversalInd("N");
                    accountingDetailVO.setOutOfBalanceInd("N");
                    accountingDetailVO.setAccountingPendingStatus(accountingDetailAccountSummaryVO.getAccountingPendingStatus());

                    accountingDetailVO.setAccountNumber(accountingDetailAccountSummaryVO.getAccountNumber());
                    accountingDetailVO.setAccountName(accountingDetailAccountSummaryVO.getAccountName());
                    accountingDetailVO.setDebitCreditInd(accountingDetailAccountSummaryVO.getDebitCreditInd());
                    accountingDetailVO.setAmount(accountingDetailAccountSummaryVO.getAmount());
                    accountingDetailVO.setStateCodeCT(accountingDetailAccountSummaryVO.getStateCodeCT());
                    accountingDetailVO.setAccountingPendingStatus(accountingDetailAccountSummaryVO.getAccountingPendingStatus());

                    accountingDetailVO.setSource(AccountingDetail.SOURCE_MANUAL);

                    accountingDetailVO.setOperator(appReqBlock.getUserSession().getUsername());
                    accountingDetailVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

                    accountingComponent.createOrUpdateVO(accountingDetailVO, false);
                }
            }

            appReqBlock.putInSessionScope("AccountingDetail.JournalAdjustment.AccountSummary", null);

            new AccountingDetailTableModel(appReqBlock).resetAllRows();
        }

        return showJournalAdjustment(appReqBlock);
    }

    protected String clearAccountingDetailAccountSummaryForAddOrCancel(AppReqBlock appReqBlock) throws Exception
    {
        saveAccountDetailInformation(appReqBlock);

        return showJournalAdjustmentDetailSummary(appReqBlock);
    }

    protected String saveAccountingDetailAccountSummary(AppReqBlock appReqBlock) throws Exception
    {
        saveAccountDetailInformation(appReqBlock);

        List accountingDetailAccountSummaryVOs = (List) appReqBlock.getHttpSession().getAttribute("AccountingDetail.JournalAdjustment.AccountSummary");

        if (accountingDetailAccountSummaryVOs == null)
        {
            accountingDetailAccountSummaryVOs = new ArrayList();
        }

        String accountNumberWithAccountName = Util.initString(appReqBlock.getReqParm("accountNumberWithAccountName"), null);
        String debitAmount = Util.initString(appReqBlock.getReqParm("debitAmount"), "0");
        String creditAmount = Util.initString(appReqBlock.getReqParm("creditAmount"), "0");
        String state = Util.initString(appReqBlock.getReqParm("state"), null);
        String suppressInd = Util.initString(appReqBlock.getReqParm("suppressInd"), null);

        String selectedAccountingDetailAccountSummaryPK = Util.initString(appReqBlock.getReqParm("selectedAccountingDetailAccountSummaryPK"), null);

        AccountingDetailVO accountingDetailVO = null;

        if (selectedAccountingDetailAccountSummaryPK == null)
        {
            accountingDetailVO = new AccountingDetailVO();

            long accountingDetailPK = CRUD.getNextAvailableKey() * -1;

            // Temporarily assign PK value
            accountingDetailVO.setAccountingDetailPK(accountingDetailPK);

            accountingDetailAccountSummaryVOs.add(accountingDetailVO);
        }
        else
        {
            for (int i = 0; i < accountingDetailAccountSummaryVOs.size(); i++)
            {
                AccountingDetailVO accountingDetailAccountSummaryVO = (AccountingDetailVO) accountingDetailAccountSummaryVOs.get(i);

                if (accountingDetailAccountSummaryVO.getAccountingDetailPK() == Long.parseLong(selectedAccountingDetailAccountSummaryPK))
                {
                    accountingDetailVO = accountingDetailAccountSummaryVO;
                }
            }
        }

        accountingDetailVO.setAccountNumber(Util.fastTokenizer(accountNumberWithAccountName, "-")[0].trim());
        accountingDetailVO.setAccountName(Util.fastTokenizer(accountNumberWithAccountName, "-")[1].trim());

        if (new EDITBigDecimal(debitAmount).isEQ("0"))
        {
            accountingDetailVO.setDebitCreditInd(AccountingDetail.DEBITCREDITIND_CREDIT);
            accountingDetailVO.setAmount(new EDITBigDecimal(creditAmount).getBigDecimal());
        }
        else if (new EDITBigDecimal(creditAmount).isEQ("0"))
        {
            accountingDetailVO.setDebitCreditInd(AccountingDetail.DEBITCREDITIND_DEBIT);
            accountingDetailVO.setAmount(new EDITBigDecimal(debitAmount).getBigDecimal());
        }

        accountingDetailVO.setStateCodeCT(state);
        accountingDetailVO.setAccountingPendingStatus(suppressInd);

        appReqBlock.putInSessionScope("AccountingDetail.JournalAdjustment.AccountSummary", accountingDetailAccountSummaryVOs);

        return showJournalAdjustmentDetailSummary(appReqBlock);
    }

    protected String showAccountingDetailAccountSummaryDetail(AppReqBlock appReqBlock) throws Exception
    {
        saveAccountDetailInformation(appReqBlock);

        String accountingDetailAccountSummaryPK = appReqBlock.getReqParm("selectedAccountingDetailAccountSummaryPK");

        List accountingDetailAccountSummaryVOs = (List) appReqBlock.getHttpSession().getAttribute("AccountingDetail.JournalAdjustment.AccountSummary");

        for (int i = 0; i < accountingDetailAccountSummaryVOs.size(); i++)
        {
            AccountingDetailVO accountingDetailVO = (AccountingDetailVO) accountingDetailAccountSummaryVOs.get(i);

            if (Long.parseLong(accountingDetailAccountSummaryPK) == accountingDetailVO.getAccountingDetailPK())
            {
                appReqBlock.getHttpServletRequest().setAttribute("selectedAccountingDetailAccountSummaryVO", accountingDetailVO);
            }
        }

        return showJournalAdjustmentDetailSummary(appReqBlock);
    }

    protected String deleteAccountingDetailAccountSummary(AppReqBlock appReqBlock) throws Exception
    {
        saveAccountDetailInformation(appReqBlock);

        List accountingDetailAccountSummaryVOs = (List) appReqBlock.getHttpSession().getAttribute("AccountingDetail.JournalAdjustment.AccountSummary");

        String selectedAccountingDetailAccountSummaryPK = appReqBlock.getReqParm("selectedAccountingDetailAccountSummaryPK");

        for (int i = 0; i < accountingDetailAccountSummaryVOs.size(); i++)
        {
            AccountingDetailVO accountingDetailVO = (AccountingDetailVO) accountingDetailAccountSummaryVOs.get(i);

            if (accountingDetailVO.getAccountingDetailPK() == Long.parseLong(selectedAccountingDetailAccountSummaryPK))
            {
                accountingDetailAccountSummaryVOs.remove(i);

                break;
            }
        }

        appReqBlock.putInSessionScope("AccountingDetail.JournalAdjustment.AccountSummary", accountingDetailAccountSummaryVOs);

        return showJournalAdjustmentDetailSummary(appReqBlock);
    }

    private void placeListValuesInRequestScopeToPopulateDropDownBoxes(AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        CompanyStructureNameVO companyStructureNameVO = engineLookup.findCompanyStructureNameVO();

        appReqBlock.getHttpServletRequest().setAttribute("companyStructureNameVO", companyStructureNameVO);

        FilteredFundVO[] filteredFundVOs = engineLookup.getAllFilteredFunds();

        filteredFundVOs = (FilteredFundVO[]) Util.sortObjects(filteredFundVOs, new String[] {"getFundNumber"});

        appReqBlock.getHttpServletRequest().setAttribute("filteredFundVOs", filteredFundVOs);

        Reinsurance reinsurance = new ReinsuranceComponent();

        TreatyGroupVO[] treatyGroupVOs = reinsurance.findAllTreatyGroups();

        treatyGroupVOs = (TreatyGroupVO[]) Util.sortObjects(treatyGroupVOs, new String[] {"getTreatyGroupNumber"});

        appReqBlock.getHttpServletRequest().setAttribute("treatyGroupVOs", treatyGroupVOs);

        ReinsurerVO[] reinsurerVOs = reinsurance.findAllReinsurers();

        reinsurerVOs = (ReinsurerVO[]) Util.sortObjects(reinsurerVOs, new String[] {"getReinsurerNumber"});

        appReqBlock.getHttpServletRequest().setAttribute("reinsurerVOs", reinsurerVOs);

    	JournalAdjustmentLookup[] journalAdjustmentLookups = JournalAdjustmentLookup.findAll();
        Map<String, ArrayList<String>> companiesWithAccountNumbersAndNames = new HashMap<String, ArrayList<String>>();
        Map<String, ArrayList<String>> companyAccountWithRequiredFields = new HashMap<String, ArrayList<String>>();
    	
    	for (JournalAdjustmentLookup jal : journalAdjustmentLookups)
    	{
        	String accountNameNumCombo = jal.getAccountNumber() + " - " + jal.getAccountName().replace("'", "\\'"); 
        	String companyName = jal.getCompanyName();
        	
        	if (!companiesWithAccountNumbersAndNames.containsKey(companyName))
        	{
        		ArrayList<String> companyNameSet = new ArrayList<>();
        		companyNameSet.add(accountNameNumCombo);
        		companiesWithAccountNumbersAndNames.put(companyName, companyNameSet);
        	}
        	else
        	{
        		companiesWithAccountNumbersAndNames.get(companyName).add(accountNameNumCombo);
        	}
        	
        	String requiredFieldsKey = jal.getCompanyName() + "-" + jal.getAccountNumber(); 
        	ArrayList<String> stateAndAgentStatus = new ArrayList<>();
        	stateAndAgentStatus.add(jal.getStateCodeRequiredInd());
        	stateAndAgentStatus.add(jal.getAgentCodeRequiredInd());
        	companyAccountWithRequiredFields.put(requiredFieldsKey, stateAndAgentStatus);
    	}
    	
    	HashMap<String, String> groupNumbersAndNames = new FastDAO().findAllGroupNumbersWithNames();
    			
//    	Map<String, ArrayList<String>> accountNumbersWithAccountNames = new FastDAO().findAllAccountNumbersWithAccountNames();
    	appReqBlock.getHttpServletRequest().setAttribute("groupNumbers", groupNumbersAndNames.keySet().toArray(new String[0]));
        appReqBlock.getHttpServletRequest().setAttribute("groupNumbersWithNames", new JSONObject(groupNumbersAndNames).toString());
    	appReqBlock.getHttpServletRequest().setAttribute("companyNames", companiesWithAccountNumbersAndNames.keySet().toArray(new String[0]));
        appReqBlock.getHttpServletRequest().setAttribute("accountNumbersWithAccountNames", new JSONObject(companiesWithAccountNumbersAndNames).toString());
        appReqBlock.getHttpServletRequest().setAttribute("companyAccountWithRequiredFields", new JSONObject(companyAccountWithRequiredFields).toString());
    }

    private void saveAccountDetailInformation(AppReqBlock appReqBlock)
    {
        String accountingDetailPK = Util.initString(appReqBlock.getReqParm("selectedAccountingDetailPK"), "0");

        String effectiveDate = Util.initString(appReqBlock.getReqParm("effectiveDate"), null);
        String accountingProcessDate = Util.initString(appReqBlock.getReqParm("accountingProcessDate"), null);
        String accountingPeriod = Util.initString(appReqBlock.getReqParm("accountingPeriod"), null);
        // disabled field ... always should be populated with current date
//        String processDate = Util.initString(appReqBlock.getReqParm("processDate"), null);

        String companyName = Util.initString(appReqBlock.getReqParm("companyName"), null);
        String marketingPackageName = Util.initString(appReqBlock.getReqParm("marketingPackageName"), null);
        String groupName = Util.initString(appReqBlock.getReqParm("groupName"), null);
        String groupNumber = Util.initString(appReqBlock.getReqParm("groupNumber"), null);
        String businessContractName = Util.initString(appReqBlock.getReqParm("businessContractName"), null);

        String contractNumber = Util.initString(appReqBlock.getReqParm("contractNumber"), null);
        String transactionType = Util.initString(appReqBlock.getReqParm("transactionType"), null);
        String coverage = Util.initString(appReqBlock.getReqParm("coverage"), null);
        String qualNonQual = Util.initString(appReqBlock.getReqParm("qualNonQual"), null);

        String qualifiedType = Util.initString(appReqBlock.getReqParm("qualifiedType"), null);
        String fundNumber = Util.initString(appReqBlock.getReqParm("fundNumber"), null);
        String reinsurerNumber  = Util.initString(appReqBlock.getReqParm("reinsurerNumber"), null);
        String treatyGroupNumber = Util.initString(appReqBlock.getReqParm("treatyGroupNumber"), null);

        String comments = Util.initString(appReqBlock.getReqParm("comments"), null);

        String batchAmount = Util.initString(appReqBlock.getReqParm("batchAmount"), "0");
        String batchControl = Util.initString(appReqBlock.getReqParm("batchControl"), null);
        String voucherSource = Util.initString(appReqBlock.getReqParm("voucherSource"), null);
        String description = Util.initString(appReqBlock.getReqParm("description"), null);
        String agentCode = Util.initString(appReqBlock.getReqParm("agentCode"), null);

        AccountingDetailVO accountingDetailVO = null;

        if (Long.parseLong(accountingDetailPK) == 0L)
        {
            accountingDetailVO = new AccountingDetailVO();
        }
        else
        {
            accountingDetailVO = (AccountingDetailVO) AccountingDetail.findByPK(Long.parseLong(accountingDetailPK)).getVO();
        }

        accountingDetailVO.setEffectiveDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(effectiveDate));
        if (accountingProcessDate != null)
        {
            accountingDetailVO.setAccountingProcessDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(accountingProcessDate));
        }

        accountingDetailVO.setAccountingPeriod(accountingPeriod);

        // the requirement is to populate this field with current date and not enterable
        accountingDetailVO.setProcessDate(new EDITDate().getFormattedDate());

        accountingDetailVO.setCompanyName(companyName);
        accountingDetailVO.setMarketingPackageName(marketingPackageName);
        accountingDetailVO.setGroupName(groupName);
        accountingDetailVO.setGroupNumber(groupNumber);
        accountingDetailVO.setBusinessContractName(businessContractName);

        accountingDetailVO.setContractNumber(contractNumber);
        accountingDetailVO.setTransactionCode(transactionType);
        accountingDetailVO.setOptionCodeCT(coverage);
        accountingDetailVO.setQualNonQualCT(qualNonQual);

        accountingDetailVO.setQualifiedTypeCT(qualifiedType);
        accountingDetailVO.setFundNumber(fundNumber);
        accountingDetailVO.setReinsurerNumber(reinsurerNumber);
        accountingDetailVO.setTreatyGroupNumber(treatyGroupNumber);
        accountingDetailVO.setOutOfBalanceInd("N");
        accountingDetailVO.setAccountingPendingStatus("Y");
        accountingDetailVO.setQualifiedTypeCT(qualifiedType);
        if (!Util.isANumber(batchAmount))
        {
             batchAmount = new EDITBigDecimal().toString();
        }

        accountingDetailVO.setBatchAmount((new EDITBigDecimal(batchAmount)).getBigDecimal());
        accountingDetailVO.setBatchControl(batchControl);
        accountingDetailVO.setVoucherSource(voucherSource);
        accountingDetailVO.setDescription(description);
        accountingDetailVO.setAgentCode(agentCode);
        accountingDetailVO.setSource("Manual");

        accountingDetailVO.setComments(comments);

        appReqBlock.putInRequestScope("selectedAccountingDetailVO", accountingDetailVO);
    }

    //***************************
    //     Helper Methods
    //***************************
    private void loadElementList(PageBean pageBean, Lookup lookup) throws Exception {

        GenericListWrapper genericListWrapper = new GenericListWrapper();

    	ElementVO[] elementList  = lookup.getAllElementNames();

        String[] nameList = new String[]{"getProcess", "getEvent", "getEventType",
                                         "getElementName"};

        if (elementList != null) {

            genericListWrapper.initializeLists(elementList, nameList);

            String[] processNameList   = genericListWrapper.getNameList("Process");
            String[] eventNameList     = genericListWrapper.getNameList("Event");
            String[] eventTypeNameList = genericListWrapper.getNameList("EventType");
            String[] elementNameList   = genericListWrapper.getNameList("ElementName");

            pageBean.putValues("processNames", processNameList
                               ,new String [] {"toString"}
                               ,null);

            pageBean.putValues("eventNames", eventNameList
                               ,new String [] {"toString"}
                               ,null);

            pageBean.putValues("eventTypeNames", eventTypeNameList
                               ,new String [] {"toString"}
                               ,null);

            pageBean.putValues("elementNames", elementNameList
                               ,new String [] {"toString"}
                               ,null);
        }
    }

	private void buildChartDetailLists(AppReqBlock appReqBlock) throws Exception {

        String productStructureId = appReqBlock.getFormBean().getValue("companyStructureId");

		//Not stored in Sesion scope but request scope
		SessionBean elementStructureLists = new SessionBean();

        Lookup lookup = new accounting.component.LookupComponent();
        engine.business.Lookup lookupComponent = new engine.component.LookupComponent();

		PageBean elementNameLists = new PageBean();

        loadElementList(elementNameLists, lookup);

        GenericListWrapper genericListWrapper = new GenericListWrapper();
        PageBean elementStructureNameLists = new PageBean();

    	ElementStructureVO[] elementStructureList  = lookup.getAllElementStructureNames();

        //build memo code list
        String[] nameList = new String[]{"getMemoCode"};

        if (elementStructureList != null) {

            genericListWrapper.initializeLists(elementStructureList, nameList);

            String[] memoCodeNameList   = genericListWrapper.getNameList("MemoCode");

            // build duration list
            TreeSet durationVector = new TreeSet();
            int certainPeriod = 0;

            for (int i = 0; i < elementStructureList.length; i++) {

                certainPeriod = elementStructureList[i].getCertainPeriod();

                durationVector.add(new Integer(certainPeriod));
            }

            String[] durationList = new String[durationVector.size()];
            int m = 0;

            Iterator it1 = durationVector.iterator();

            while (it1.hasNext()) {

                durationList[m] = it1.next().toString();
                m++;
            }

            //build fund number list
            long fundId = 0;
            Map fundHT = new HashMap();
            FilteredFundVO[] filteredFundVOs = null;

            for (int j = 0; j < elementStructureList.length; j++) {

                fundId = elementStructureList[j].getFundFK();

                if (fundId != 0) {

                    if (!productStructureId.equals("")) {

                        filteredFundVOs = lookupComponent.getByCSIdFundId(Long.parseLong(productStructureId), fundId);
                        if (filteredFundVOs != null) {

                            if (!fundHT.containsKey(fundId + "")) {

                                fundHT.put(fundId + "", filteredFundVOs[0].getFundNumber());
                            }
                        }
                    }

                    else {

                        filteredFundVOs = lookupComponent.getByFundId(fundId);

                        if (filteredFundVOs != null && filteredFundVOs.length > 0)
                        {
                        if (!fundHT.containsKey(fundId + "")) {

                            fundHT.put(fundId + "", filteredFundVOs[0].getFundNumber());
                        }
                    }
                }
            }
            }

            String[] fundNumberList = new String[fundHT.size()];
            String[] fundFKList     = new String[fundHT.size()];
            int n = 0;

            TreeMap fundMap = sortFundsByName(fundHT);

            Iterator fundIterator = fundMap.keySet().iterator();

            while (fundIterator.hasNext()) {

                String fundKey  = (String) fundIterator.next();
                String fundName = (String) fundMap.get(fundKey);

                int indexOfKey  = fundName.length();
                fundNumberList[n] = fundName;
                fundFKList[n] = fundKey.substring(indexOfKey);
                n++;
            }

            elementStructureNameLists.putValues("memoCodeNames",
                                                 memoCodeNameList,
                                                 new String [] {"toString"},
                                                 null);

            elementStructureNameLists.putValues("durationNames",
                                                 durationList,
                                                 new String [] {"toString"},
                                                 null);

            elementStructureNameLists.putValues("fundNames"
                                                ,fundNumberList
                                                ,new String [] {"toString"}
							                    ,null);

            elementStructureNameLists.putValues("fundKeys"
                                                ,fundFKList
                                                ,new String [] {"toString"}
							                    ,null);


            String[] strings = getAllChargeCodesAndKeys();

            String chargeCodeNumbers = (String) strings[0];
            // EXAMPLE  "fundname1|123|456^fundname2|678^fundname3|123|789|666"

            String chargeCodeFKs = (String) strings[1];
            // EXAMPLE  "fundname1|444444|222222^fundname2|111111^fundname3|3333333|3333339|4444444"
            
            elementStructureNameLists.putValue("chargeCodes", chargeCodeNumbers);
            elementStructureNameLists.putValue("chargeCodeFKs", chargeCodeFKs);

            elementNameLists.putValue("companyStructureId", productStructureId);

            elementStructureLists.putPageBean("elementStructureNameLists", elementStructureNameLists);
            elementStructureLists.putPageBean("elementNameLists", elementNameLists);
        }

		appReqBlock.getHttpServletRequest().setAttribute("elementStructureLists", elementStructureLists);

	}

    /**
     * Get the array of charge codes and FKs.  This is used to build two associative
     * arrays on the page.
     * @return String[] with first the delimited String of chargecode numbers and the second, the FKs
     */
    private String[] getAllChargeCodesAndKeys()
    {

        // THESE ARE THE TWO STRINGS WE ARE CREATING
        // 300 here is a fund number, so is 8502 and 8504
        // the first set is followed by the charge code nubmers
        // the second set has the pks for each charge code in the
        // same order
        // this is used by the JSP page for associative arrays
        //  "300|123|456^8502|678^8504|123|789|666">
        //  "300|444444|222222^8502|111111^8504|3333333|3333339|4444444">


        ChargeCode[] chargeCodes = ChargeCode.findAll();

        if (chargeCodes == null)
        {
            chargeCodes = new ChargeCode[0];
        }

        Map orderedMapOfFunds = new TreeMap();

        for (int i = 0; i < chargeCodes.length; i++)
        {
            ChargeCode chargeCode = chargeCodes[i];

            ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
            String chargeCodeForFFund = chargeCodeVO.getChargeCode();
            long chargeCodePK = chargeCodeVO.getChargeCodePK();

            long filteredFundPK = chargeCodeVO.getFilteredFundFK();
            FilteredFund filteredFund = FilteredFund.findByPK(filteredFundPK);
            FilteredFundVO filteredFundVO = (FilteredFundVO) filteredFund.getVO();
            String fundNumber = filteredFundVO.getFundNumber();

            if (!orderedMapOfFunds.containsKey(fundNumber))
            {
                orderedMapOfFunds.put(fundNumber, new ArrayList());
            }

            List ccodesAndPKs = (List) orderedMapOfFunds.get(fundNumber);
            ccodesAndPKs.add(chargeCodeForFFund + "|" + chargeCodePK);
        }


        StringBuffer sbChargeCodesForJSP = new StringBuffer();
        StringBuffer sbChargeCodePKsForJSP = new StringBuffer();

        Iterator it = orderedMapOfFunds.keySet().iterator();
        for (int i = 0; it.hasNext(); i++)
        {
            String fund = (String) it.next();
            List values = (List) orderedMapOfFunds.get(fund);
            Collections.sort(values);  // now the charge codes are orders
            StringBuffer sbChargeCodes = new StringBuffer();
            StringBuffer sbChargeCodePKs = new StringBuffer();
            sbChargeCodes.append(fund).append("|");
            sbChargeCodePKs.append(fund).append("|");
            for (int j = 0; j < values.size(); j++)
            {

                String chgCodeAndPK =  (String) values.get(j);
                int ix = chgCodeAndPK.indexOf("|");
                String chgCode =  chgCodeAndPK.substring(0, ix);
                String pk =  chgCodeAndPK.substring(ix+1);

                if (j!=0)
                    sbChargeCodes.append("|");
                sbChargeCodes.append(chgCode);

                if (j!=0)
                    sbChargeCodePKs.append("|");
                sbChargeCodePKs.append(pk);

            }

            String chargeCodesTemp = sbChargeCodes.toString();
            String chargeCodePKsTemp = sbChargeCodePKs.toString();

            if (i != 0)
            {
                sbChargeCodesForJSP.append("^");
                sbChargeCodePKsForJSP.append("^");
            }
            sbChargeCodesForJSP.append(chargeCodesTemp);
            sbChargeCodePKsForJSP.append(chargeCodePKsTemp);
        }

        return new String[] {sbChargeCodesForJSP.toString(), sbChargeCodePKsForJSP.toString()};

    }


    //    /**
    //     * Go get an ordered array of charge codes (eliminating dups) that match any of
    //     * the filtered fund foreign keys in the array of fundFKs.
    //     * @param fundNames an array of filtered fund Names
    //     * @return  an array of charge codes (ordered)
    //     */
    //    private String[] getChargeCodesForFunds(String[] fundNames)
    //    {
    //        ChargeCode[] chargeCodes = ChargeCode.findByFilteredFundNames(fundNames);
    //
    //        if (chargeCodes == null)
    //        {
    //            return new String[0];
    //        }
    //
    //        Set setOfChargeCodeNums = new HashSet();
    //
    //        for (int i = 0; i < chargeCodes.length; i++)
    //        {
    //            ChargeCode chargeCode = chargeCodes[i];
    //
    //            ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
    //            String chargeCodeNum = chargeCodeVO.getChargeCode();
    //            setOfChargeCodeNums.add(chargeCodeNum);   // eliminate dups
    //        }
    //
    //        TreeSet sortedChargeCodeNums = new TreeSet(setOfChargeCodeNums);
    //
    //        String[] chargeCodesToReturn = new String[sortedChargeCodeNums.size()];
    //
    //        Iterator it = sortedChargeCodeNums.iterator();
    //
    //        for (int i = 0; it.hasNext(); i++)
    //        {
    //            String tempChgCode = (String) it.next();
    //            chargeCodesToReturn[i] = tempChgCode;
    //        }
    //
    //        return chargeCodesToReturn;
    //
    //    }

    private void buildChartDetailSummary(AppReqBlock appReqBlock) throws Exception {

		// Not stored in session scope but in request scope
		SessionBean structures = new SessionBean();

        Lookup lookup = new accounting.component.LookupComponent();

		String elementId = appReqBlock.getFormBean().getValue("elementId");

        if (Util.isANumber(elementId))
        {
            ElementVO[] elementVO = lookup.getElementById(Long.parseLong(elementId));

            elementId = elementVO[0].getElementPK() + "";
            String sequence  = elementVO[0].getSequenceNumber() + "";

            EDITDate effectiveDate  = new EDITDate(elementVO[0].getEffectiveDate());
            String effectiveMonth = effectiveDate.getFormattedMonth();
            String effectiveDay   = effectiveDate.getFormattedDay();
            String effectiveYear  = effectiveDate.getFormattedYear();

            String process   = elementVO[0].getProcess();
            String event     = elementVO[0].getEvent();
            String eventType = elementVO[0].getEventType();
            String element   = elementVO[0].getElementName();
            String operator  = elementVO[0].getOperator();
            String maintDate = elementVO[0].getMaintDateTime();

            PageBean formBean     = appReqBlock.getFormBean();

            formBean.putValue("sequence", sequence);
            formBean.putValue("elementId", elementId);
            formBean.putValue("effectiveMonth", effectiveMonth);
            formBean.putValue("effectiveDay", effectiveDay);
            formBean.putValue("effectiveYear", effectiveYear);
            formBean.putValue("process", process);
            formBean.putValue("event", event);
            formBean.putValue("eventType", eventType);
            formBean.putValue("element", element);
            formBean.putValue("operator", operator);
            formBean.putValue("maintDate", maintDate);

            appReqBlock.getHttpServletRequest().setAttribute("formBean",formBean);

            ElementStructureVO[] elementStructureVOs = elementVO[0].getElementStructureVO();

            if (elementStructureVOs != null) {

                for (int i = 0; i < elementStructureVOs.length; i++) {

                    String elementStructureId = elementStructureVOs[i].getElementStructurePK() + "";
                    String memoCode           = elementStructureVOs[i].getMemoCode();
                    String duration           = elementStructureVOs[i].getCertainPeriod() + "";
                    String qualNonQual        = elementStructureVOs[i].getQualNonQualCT();
                    String fundName           = elementStructureVOs[i].getFundFK() + "";
                    String suppressedInd      = elementStructureVOs[i].getSuppressAccountingInd();
                    String switchEffect       = elementStructureVOs[i].getSwitchEffectInd();
                    long chargeCodeFK         = elementStructureVOs[i].getChargeCodeFK();

                    AccountVO[] accountVOs = elementStructureVOs[i].getAccountVO();

                    if (accountVOs.length > 0) {

                        for(int j = 0;  j < accountVOs.length; j++) {

                            PageBean elementStructureAccounts = new PageBean();

                            elementStructureAccounts.putValue("elementStructureId", elementStructureId);
                            elementStructureAccounts.putValue("memoCode", memoCode);
                            elementStructureAccounts.putValue("duration", duration);
                            elementStructureAccounts.putValue("fundName", fundName);
                            elementStructureAccounts.putValue("qualNonQual", qualNonQual);
                            elementStructureAccounts.putValue("chargeCodeFK", chargeCodeFK + "");

                            if (suppressedInd.equalsIgnoreCase("Y")) {

                                elementStructureAccounts.putValue("suppressedIndStatus", "checked");
                            }

                            if (switchEffect.equalsIgnoreCase("Y")) {

                                elementStructureAccounts.putValue("switchEffectIndStatus", "checked");
                            }

                            String accountDescription = accountVOs[j].getAccountDescription();
                            String accountId          = accountVOs[j].getAccountPK() + "";
                            String accountName        = accountVOs[j].getAccountName();
                            String accountNumber      = accountVOs[j].getAccountNumber();
                            String accountEffect      = accountVOs[j].getEffect();

                            String key = elementStructureId + "_" + accountId;

                            elementStructureAccounts.putValue("accountDescription", accountDescription);
                            elementStructureAccounts.putValue("accountId", accountId);
                            elementStructureAccounts.putValue("accountName", accountName);
                            elementStructureAccounts.putValue("accountNumber", accountNumber);
                            elementStructureAccounts.putValue("accountEffectId", accountEffect);
                            elementStructureAccounts.putValue("key", key);

                            structures.putPageBean(key, elementStructureAccounts);
                        }
                    }

                    else {

                        PageBean elementStructureAccounts = new PageBean();

                        elementStructureAccounts.putValue("elementStructureId", elementStructureId);
                        elementStructureAccounts.putValue("memoCode", memoCode);

                        elementStructureAccounts.putValue("duration", duration);
                        elementStructureAccounts.putValue("fundName", fundName);
                        elementStructureAccounts.putValue("qualNonQual", qualNonQual);
                        elementStructureAccounts.putValue("chargeCodeFK", chargeCodeFK + "");

                        if (suppressedInd.equalsIgnoreCase("Y")) {

                            elementStructureAccounts.putValue("suppressedIndStatus", "checked");
                        }

                        if (switchEffect.equalsIgnoreCase("Y")) {

                            elementStructureAccounts.putValue("switchEffectIndStatus", "checked");
                        }

                        String key = elementStructureId + "_" + "";
                        elementStructureAccounts.putValue("key", key);
                        structures.putPageBean(key, elementStructureAccounts);
                    }
                }
            }

            appReqBlock.getHttpServletRequest().setAttribute("structures", structures);
        }
	}

	private void buildElementSummary(AppReqBlock appReqBlock) throws Exception  {

		// Not stored in session scope, but in request scope.
		SessionBean elements = new SessionBean();

        Lookup lookup = new accounting.component.LookupComponent();

		ElementVO[] elementVOs = lookup.getAllElements();

		if (elementVOs != null) {

			for(int i = 0;  i < elementVOs.length; i++) {

				String elementId     = elementVOs[i].getElementPK() + "";
				String sequence      = elementVOs[i].getSequenceNumber() + "";
				String effectiveDate = elementVOs[i].getEffectiveDate();
				String process       = elementVOs[i].getProcess();
				String event         = elementVOs[i].getEvent();
				String eventType     = elementVOs[i].getEventType();
				String element		 = elementVOs[i].getElementName();

				PageBean elementBean = new PageBean();

				elementBean.putValue("sequence", sequence);
				elementBean.putValue("elementId", elementId);
				elementBean.putValue("effectiveDate", DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate));
				elementBean.putValue("process", process);
				elementBean.putValue("event", event);
				elementBean.putValue("eventType", eventType);
				elementBean.putValue("element", element);

				elements.putPageBean(elementId, elementBean);
			}
		}

		appReqBlock.getHttpServletRequest().setAttribute("elements", elements);
	}

	private void buildAllCompanyStructures(AppReqBlock appReqBlock) throws Exception {

		// Not stored in session scope but request scope
		//Gets all Company Structures for the relation page

		SessionBean allCompanyStructures = new SessionBean();

        engine.business.Lookup lookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = lookup.getAllProductStructures();

		for (int i = 0; i < productStructureVOs.length; i++) {

			PageBean companyStructureBean = new PageBean();

            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
			companyStructureBean.putValue("company", company.getCompanyName());
			companyStructureBean.putValue("marketingPackage", productStructureVOs[i].getMarketingPackageName());
			companyStructureBean.putValue("groupProduct", productStructureVOs[i].getGroupProductName());
			companyStructureBean.putValue("area", productStructureVOs[i].getAreaName());
			companyStructureBean.putValue("businessContract", productStructureVOs[i].getBusinessContractName());

			String productStructureId = productStructureVOs[i].getProductStructurePK() + "";

			companyStructureBean.putValue("companyStructureId", productStructureId);

			allCompanyStructures.putPageBean(productStructureId, companyStructureBean);
		}

		appReqBlock.getHttpServletRequest().setAttribute("allCompanyStructures", allCompanyStructures);
	}

	private void buildFilteredCompanyStructures(AppReqBlock appReqBlock) throws Exception {

		// Not stored in session scope but in request scope
		SessionBean filteredCompanyStructures = new SessionBean();

        Lookup lookup = new accounting.component.LookupComponent();

        ElementCompanyRelationVO[] elementCompanyRelationVOs = lookup.getAllProductStructureIds();

        TreeSet csVector = new TreeSet();

        for (int i = 0; i < elementCompanyRelationVOs.length; i++) {

            csVector.add(new Long(elementCompanyRelationVOs[i].getProductStructureFK()));
        }

        long[] productStructureIds = new long[csVector.size()];

        Iterator it = csVector.iterator();
        int j = 0;

        while (it.hasNext()) {

            productStructureIds[j] = ((Long) it.next()).longValue();
            j++;
        }

        engine.business.Lookup lookupComponent = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = lookupComponent.getAllProductStructuresByIds(productStructureIds);

        String[] structures = new String[productStructureVOs.length];

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            Company company = Company.findByPK(productStructureVOs[i].getCompanyFK());
            structures[i] = company.getCompanyName() + "," + productStructureVOs[i].getMarketingPackageName() +
                            "," + productStructureVOs[i].getGroupProductName() + ","+ productStructureVOs[i].getAreaName() +
                            "," + productStructureVOs[i].getBusinessContractName();
        }

        filteredCompanyStructures.putValues("companyStructures", structures);


		filteredCompanyStructures.putValues("companyStructureIds",
											 productStructureVOs,
											 new String [] {"getProductStructurePK"},
											 null);

		appReqBlock.getHttpServletRequest().setAttribute("filteredCompanyStructures", filteredCompanyStructures);
	}

	private void buildCompanyStructureLists(AppReqBlock appReqBlock) throws Exception {

		PageBean companyStructureListBean = new PageBean();

		//get list of Companies,MarketingPackage,Group Product,
		//Area and Business contract

        engine.business.Lookup lookupComponent = new engine.component.LookupComponent();
        GenericListWrapper genericListWrapper = new GenericListWrapper();

        ProductStructureVO[] productStructureList = lookupComponent.getAllProductStructures();

        String[] nameList = new String[]{"getMarketingPackageName",
                                           "getGroupProductName", "getAreaName", "getBusinessContractName"};

        genericListWrapper.initializeLists(productStructureList, nameList);

        for (int i = 0; i < productStructureList.length; i++)
        {
            Company company = (Company) Company.findByPK(productStructureList[i].getCompanyFK());
            genericListWrapper.setNames("getCompanyName", company.getCompanyName());
        }

        String[] companyNameList = genericListWrapper.getNameList("CompanyName");
        String[] marketingNameList = genericListWrapper.getNameList("MarketingPackageName");
        String[] groupNameList = genericListWrapper.getNameList("GroupProductName");
        String[] areaNameList = genericListWrapper.getNameList("AreaName");
        String[] businessNameList = genericListWrapper.getNameList("BusinessContractName");

        companyStructureListBean.putValues("companyList"
		                   ,companyNameList
		                   ,new String [] {"toString"}
		                   ,null);
		companyStructureListBean.putValues("marketingPackageList"
		                   ,marketingNameList
		                   ,new String [] {"toString"}
		                   ,null);
		companyStructureListBean.putValues("groupProductList"
		                   ,groupNameList
		                   ,new String [] {"toString"}
		                   ,null);
		companyStructureListBean.putValues("areaList"
		                   ,areaNameList
		                   ,new String [] {"toString"}
		                   ,null);
		companyStructureListBean.putValues("businessContractList"
		                   ,businessNameList
		                   ,new String [] {"toString"}
		                   ,null);

		//Default values for Company structure page
		companyStructureListBean.putValue("company","Please Select");
		companyStructureListBean.putValue("marketingPackage","Please Select");
		companyStructureListBean.putValue("groupProduct","Please Select");
		companyStructureListBean.putValue("area","Please Select");
		companyStructureListBean.putValue("businessContract","Please Select");

		appReqBlock.getHttpServletRequest().setAttribute("companyStructureLists", companyStructureListBean);
	}

    private TreeMap sortFundsByName(Map fundHT) {

		TreeMap sortedFunds = new TreeMap();

		Iterator keys  = fundHT.keySet().iterator();

		while (keys.hasNext()) {

			String fundKey = (String) keys.next();
            String fundName = (String) fundHT.get(fundKey);

            sortedFunds.put(fundName + fundKey, fundName);
		}

		return sortedFunds;
    }

    /**
     * From a selected company structure and the clone button on the chartsRelation page, this method gets invoked in
     * order to display cloning information. For this page, the selected company structure and all its attached elements,
     * will display along with all company structures.
     * @param appReqBlock
     * @return  jsp - cloneCompanyStructureDialog.jsp
     * @throws Exception
     */
    protected String showCloneCompanyStructureDialog(AppReqBlock appReqBlock) throws Exception
    {
		PageBean formBean = appReqBlock.getFormBean();
        String productStructureId = initParam(formBean.getValue("selectedCompanyStructureId"), "0");

        if (productStructureId.equals("0"))
        {
            String message = "Company Structure Required";
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCompanyStructureId", "0");

            return CLONE_COMPANY_STRUCTURE_DIALOG;
        }

        engine.business.Lookup calcLookup = new engine.component.LookupComponent();

        // Get the ProductStructureVO
        ProductStructureVO[] productStructureVO = calcLookup.findProductStructureVOByPK(Long.parseLong(productStructureId), false, null);

        // Get All ProductStructureVOs
        ProductStructureVO[] productStructureVOs = calcLookup.findAllProductTypeStructureVOs(false, null);

        // Get attached Elements
        accounting.business.Lookup accountingLookup = new accounting.component.LookupComponent();
        ElementVO[] elementVOs = accountingLookup.getAllElementsByProductStructure(Long.parseLong(productStructureId));

		appReqBlock.getHttpServletRequest().setAttribute("elementVOs", elementVOs);
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVO", productStructureVO);
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);

        return CLONE_COMPANY_STRUCTURE_DIALOG;
    }

    /**
     * From the cloneCompanyStructureDialog page, a to company structure is selected.  Using the from company structure's
     * ElementCompanyRelation records, ElementCompanyRelation records are created for the to company structure. An error is produced
     * if the from company structure is never selected.
     * @param appReqBlock
     * @return  jsp - cloneCompanyStructureDialog.jsp
     * @throws Exception
     */
    protected String cloneCompanyStructure(AppReqBlock appReqBlock) throws Exception
    {
        String productStructurePK = initParam(appReqBlock.getReqParm("selectedCompanyStructureId"), "0");
        String cloneCompanyStructurePK = initParam(appReqBlock.getReqParm("cloneCompanyStructurePK"), "0");

        if (cloneCompanyStructurePK.equals("0"))
        {
            String message = "A CompanyStructure To Clone Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showCloneCompanyStructureDialog(appReqBlock);
        }

        accounting.business.Accounting accountingComponent = new accounting.component.AccountingComponent();
        accountingComponent.cloneCompanyStructure(Long.parseLong(productStructurePK), Long.parseLong(cloneCompanyStructurePK));

        String closeOnMessage = "Company Structure Successfully Cloned";
        appReqBlock.getHttpServletRequest().setAttribute("closeOnMessage", closeOnMessage);

        return showCloneCompanyStructureDialog(appReqBlock);
    }
}
