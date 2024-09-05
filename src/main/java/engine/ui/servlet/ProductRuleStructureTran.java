/*
 * User: unknown
 * Date: Jun 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.ui.servlet;

import edit.common.exceptions.EDITDeleteException;
import edit.common.vo.*;
import edit.common.vo.user.UIRulesVO;
import edit.portal.common.transactions.Transaction;
import engine.business.Calculator;
import engine.business.Lookup;
import engine.component.CalculatorComponent;
import engine.component.LookupComponent;
import fission.beans.PageBean;
import fission.global.AppReqBlock;
import fission.utility.Util;
import edit.common.*;

import java.util.*;


/**
 * CompanyRuleStructureTran is used to handle Company/Rule related requests
 */
public class ProductRuleStructureTran extends Transaction
{
    public static final String SHOW_ASSOCIATED_SCRIPT_FOR_RULE = "showAssociatedScriptForRule";

    public static final String TEMPLATE_MAIN = "/common/jsp/template/template-main.jsp";
    public static final String SCRIPT_DIALOG = "/engine/jsp/scriptDialog.jsp";


    /**
     * retrieves action and calls appropriate method
     * <p>
     * @param appReqBlock AppReqBlock retrieves action
     * @return returns String for screen display
     */
    public String execute(AppReqBlock appReqBlock) throws Exception
    {

        String action = appReqBlock.getReqParm("action");

        if (action.equalsIgnoreCase("showRelation"))
        {
            return showRelation(appReqBlock);
        }
        else if (action.equalsIgnoreCase("attachProductAndRule"))
        {
            return attachProductAndRule(appReqBlock);
        }
        else if (action.equalsIgnoreCase("detachProductAndRule"))
        {
            return detachProductAndRule(appReqBlock);
        }
        else if (action.equalsIgnoreCase("cancelRelation"))
        {
            return cancelRelation(appReqBlock);
        }
        else if (action.equalsIgnoreCase("showRuleSummary"))
        {
            return showRuleSummary(appReqBlock);
        }
        else if (action.equals("showBuildRuleDialog"))
        {
            return showBuildRuleDialog(appReqBlock);
        }
        else if (action.equals("cloneProductStructure"))
        {
            return cloneProductStructure(appReqBlock);
        }
        else if (action.equals("showRuleDetail"))
        {
            return showRuleDetail(appReqBlock);
        }
        else if (action.equals("showAttachedProductStructures"))
        {
            return showAttachedProductStructures(appReqBlock);
        }
        else if (action.equals("saveRule"))
        {
            return saveRule(appReqBlock);
        }
        else if (action.equals("addRule"))
        {
            return addRule(appReqBlock);
        }
        else if (action.equals("cancelRuleEdits"))
        {
            return cancelRuleEdits(appReqBlock);
        }
        else if (action.equals("deleteRule"))
        {
            return deleteRule(appReqBlock);
        }
        else if (action.equals("showFilterScriptRulesDialog"))
        {
            return showFilterScriptRulesDialog(appReqBlock);
        }
        else if (action.equals("showFilterTableRulesDialog"))
        {
            return showFilterTableRulesDialog(appReqBlock);
        }
        else if (action.equals("populateFilterNames"))
        {
            return populateFilterNames(appReqBlock);
        }
        else if (action.equals("filterScriptRules"))
        {
            return filterScriptRules(appReqBlock);
        }
        else if (action.equals("filterTableRules"))
        {
            return filterTableRules(appReqBlock);
        }
        else if (action.equals(SHOW_ASSOCIATED_SCRIPT_FOR_RULE))
        {
            return showAssoicatedScriptForRule(appReqBlock);
        }
        else
        {
            throw new Exception("ProductRuleStructure Tran: " + action + " is invalid action.");
        }

    }


//****************************************
//           PROTECTED METHODS
//****************************************

    /**
     * retrieves session data, sets up bean, retrieves data, sets tree heading,
     * and loads the session with data.
     * <p>
     * @param appReqBlock AppReqBlock retrieves session data
     * <p>
     */

    protected String deleteRule(AppReqBlock appReqBlock) throws Exception
    {
        String rulesPK = initParam(appReqBlock.getReqParm("rulesPK"), "0");

        if (rulesPK.equals("0"))
        {
            String message = "A Rule Must Be Selected";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        else
        {
            Calculator calcComponent = new CalculatorComponent();

            try
            {
                calcComponent.deleteRule(Long.parseLong(rulesPK));

                String message = "Rule Successfully Deleted";

                appReqBlock.getHttpServletRequest().setAttribute("message", message);
            }
            catch (EDITDeleteException e)
            {
                Lookup calcLookup = new LookupComponent();

                RulesVO rulesVO = calcLookup.findRulesVOByPK(Long.parseLong(rulesPK), false, null)[0];

                appReqBlock.getHttpServletRequest().setAttribute("message", e.getMessage());
                appReqBlock.getHttpServletRequest().setAttribute("rulesVO", rulesVO);
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showRuleSummary(appReqBlock);
    }

    protected String cancelRuleEdits(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showRuleSummary(appReqBlock);
    }

    protected String addRule(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "ADD");

        return showRuleSummary(appReqBlock);
    }

    protected String saveRule(AppReqBlock appReqBlock) throws Exception
    {
        // RulesVO info
        String scriptPK = initParam(appReqBlock.getReqParm("scriptPK"), "0");
        String tableDefPK = initParam(appReqBlock.getReqParm("tableDefPK"), "0");
        String keySourceScriptPK = initParam(appReqBlock.getReqParm("keySourceScriptPK"), "0");
        String rulesPK = initParam(appReqBlock.getReqParm("rulesPK"), "0");

        String ruleName = initParam(appReqBlock.getReqParm("ruleName"), null);
        String processName = initParam(appReqBlock.getReqParm("processName"), null);
        String eventName = initParam(appReqBlock.getReqParm("eventName"), null);
        String eventTypeName = initParam(appReqBlock.getReqParm("eventTypeName"), null);

        String effectiveDay = initParam(appReqBlock.getReqParm("effectiveDay"), null);
        String effectiveMonth = initParam(appReqBlock.getReqParm("effectiveMonth"), null);
        String effectiveYear = initParam(appReqBlock.getReqParm("effectiveYear"), null);

        String operator = appReqBlock.getUserSession().getUsername();

        String description = initParam(appReqBlock.getReqParm("description"), null);

        RulesVO rulesVO = new RulesVO();

        rulesVO.setDescription(description);
        rulesVO.setEffectiveDate(new EDITDate(effectiveYear, effectiveMonth, effectiveDay).getFormattedDate());
        rulesVO.setEventName(eventName);
        rulesVO.setEventTypeName(eventTypeName);
        rulesVO.setProcessName(processName);
        rulesVO.setRuleName(ruleName);
        rulesVO.setOperator(operator);
        rulesVO.setRulesPK(Long.parseLong(rulesPK));

        //  Determine if script should be the key source script or the regular script
        //  If a table has been selected, only a key source script may be selected (not a regular script)
        if (tableDefPK.equals("0"))
        {
            rulesVO.setScriptFK(Long.parseLong(scriptPK));
        }
        else
        {
            rulesVO.setScriptFK(Long.parseLong(keySourceScriptPK));
        }

        rulesVO.setTableDefFK(Long.parseLong(tableDefPK));

        if ( (scriptPK.equals("0") && tableDefPK.equals("0")) || (ruleName == null) || (effectiveDay == null) || (effectiveMonth == null) || (effectiveYear == null))
        {
            String message = "Script, Table, RuleName, EffectiveDate Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            appReqBlock.getHttpServletRequest().setAttribute("rulesVO", rulesVO);
            appReqBlock.getHttpServletRequest().setAttribute("pageMode", "ADD");

            return this.showRuleSummary(appReqBlock);
        }

        Calculator calculatorComp = new CalculatorComponent();
        calculatorComp.saveRule(rulesVO);

        String message = "Rule Successfully Saved";

        appReqBlock.getHttpServletRequest().setAttribute("message", message);
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");


        return this.showRuleSummary(appReqBlock);
    }

    protected String showAttachedProductStructures(AppReqBlock appReqBlock) throws Exception
    {
        String rulesPK = initParam(appReqBlock.getReqParm("rulesPK"), "0");

        Lookup calcLookup = new LookupComponent();

        ProductStructureVO[] productStructureVOs = calcLookup.findAttachedProductStructureVOsByRulesPK(Long.parseLong(rulesPK), false, null);

        RulesVO[] rulesVOs = calcLookup.findRulesVOByPK(Long.parseLong(rulesPK), false, null);

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        if (rulesVOs != null)
        {
            UIRulesVO[] uiRulesVOs = this.buildUIRulesVOs(rulesVOs);

            appReqBlock.getHttpServletRequest().setAttribute("uiRulesVOs", uiRulesVOs);
        }

        return "/engine/jsp/attachedCompanyStructuresDialog.jsp";
    }

    protected String showRuleDetail(AppReqBlock appReqBlock) throws Exception
    {
        String rulesPK = initParam(appReqBlock.getReqParm("rulesPK"), "0");

        Lookup calcLookup = new LookupComponent();

        RulesVO[] rulesVOs = calcLookup.findRulesVOByPK(Long.parseLong(rulesPK), false, null);

        appReqBlock.getHttpServletRequest().setAttribute("rulesVO", rulesVOs[0]);
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "VIEW");

        return showRuleSummary(appReqBlock);
    }

    protected String cloneProductStructure(AppReqBlock appReqBlock) throws Exception
    {
        saveRuleFilters(appReqBlock);

        String productStructurePK = initParam(appReqBlock.getReqParm("productStructurePK"), "0");
        String cloneProductStructurePK = initParam(appReqBlock.getReqParm("cloneProductStructurePK"), "0");

        if (cloneProductStructurePK.equals("0"))
        {
            String message = "A ProductStructure To Clone Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showBuildRuleDialog(appReqBlock);
        }

        Calculator calcComp = new CalculatorComponent();
        calcComp.cloneProductStructure(Long.parseLong(productStructurePK), Long.parseLong(cloneProductStructurePK));

        appReqBlock.getHttpServletRequest().setAttribute("message", "Product Structure Successfully Cloned");
        appReqBlock.setReqParm("productStructurePK", cloneProductStructurePK);

        return showBuildRuleDialog(appReqBlock);
    }

    private UIRulesVO[] buildUIRulesVOs(RulesVO[] rulesVOs) throws Exception
    {
        List uiRulesVOs = new ArrayList();
        Lookup calcLookup = new LookupComponent();

        for (int i = 0; i < rulesVOs.length; i++)
        {
            UIRulesVO uiRulesVO = new UIRulesVO();
            uiRulesVO.setRulesVO(rulesVOs[i]);

            long scriptPK = rulesVOs[i].getScriptFK();
            long tableDefPK = rulesVOs[i].getTableDefFK();

            ScriptVO[] scriptVOs = null;

            if (scriptPK != 0)
            {
                scriptVOs = calcLookup.findScriptVOByPK(scriptPK, false, null);
                uiRulesVO.setScriptVO(scriptVOs[0]);
            }

            TableDefVO[] tableDefVOs = null;

            if (tableDefPK != 0)
            {
                tableDefVOs = calcLookup.findTableDefVOByPK(tableDefPK, false, null);
                uiRulesVO.setTableDefVO(tableDefVOs[0]);
            }

            uiRulesVOs.add(uiRulesVO);
        }

        if (uiRulesVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (UIRulesVO[]) uiRulesVOs.toArray(new UIRulesVO[uiRulesVOs.size()]);
        }
    }

    protected String showRelation(AppReqBlock appReqBlock) throws Exception
    {
        String productStructurePK = initParam(appReqBlock.getReqParm("productStructurePK"), "0");

        getRelationInformation(productStructurePK, appReqBlock);

        return "/engine/jsp/rulesRelationPage.jsp";
    }

    private void getRelationInformation(String productStructurePK, AppReqBlock appReqBlock) throws Exception
    {

        List attachedRulesPKs = new ArrayList();
        List repeatingRulePKs = new ArrayList();

        Lookup calcLookup = new LookupComponent();

// Get All ProductStructureVOs
        ProductStructureVO[] productStructureVOs = calcLookup.findAllProductStructureVOs(false, null);

// Get All RuleVOs, but expand then into UIRuleVO
        UIRulesVO[] uiRulesVOs = null;

        RulesVO[] rulesVOs = calcLookup.findAllRulesVOs(false, null);

        if (rulesVOs != null)
        {
            uiRulesVOs = this.buildUIRulesVOs(rulesVOs);
        }

// Find attached Rules
        if (!productStructurePK.equals("0"))
        {
            long[] attachedPKs = calcLookup.findRulesPKsByProductStructurePK(Long.parseLong(productStructurePK));

            if (attachedPKs != null)
            {
                for (int i = 0; i < attachedPKs.length; i++)
                {
                    attachedRulesPKs.add(new Long(attachedPKs[i]));
                }
            }
        }

// Find Repeating Rule Names
        if (rulesVOs != null)
        {
            repeatingRulePKs = findRepeatingRulePKs(rulesVOs);
        }

        if (uiRulesVOs != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("uiRulesVOs", uiRulesVOs);
        }

        if (productStructureVOs != null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);
        }

        appReqBlock.getHttpServletRequest().setAttribute("productStructurePK", productStructurePK);
        appReqBlock.getHttpServletRequest().setAttribute("attachedRulesPKs", attachedRulesPKs);
        appReqBlock.getHttpServletRequest().setAttribute("repeatingRulePKs", repeatingRulePKs);
    }

    private List findRepeatingRulePKs(RulesVO[] rulesVOs) throws Exception
    {
        List repeatingRulePKs = new ArrayList();

        Map repeatingRules = new HashMap();

        if (rulesVOs != null)
        {
            for (int i = 0; i < rulesVOs.length; i++)
            {
                String currentRuleName = rulesVOs[i].getRuleName();

                if (!repeatingRules.containsKey(currentRuleName))
                {
                    List repeatingRulePKsForRuleName = new ArrayList();

                    repeatingRules.put(currentRuleName, repeatingRulePKsForRuleName);
                }

                List repeatingRulePKsForRuleName = (List) repeatingRules.get(currentRuleName);

                repeatingRulePKsForRuleName.add(new Long(rulesVOs[i].getRulesPK()));
            }
        }

        Iterator keys = repeatingRules.keySet().iterator();

        while (keys.hasNext())
        {
            String currentRuleName = keys.next().toString();

            List repeatingRulePKsForRuleName = (List) repeatingRules.get(currentRuleName);

            if (repeatingRulePKsForRuleName.size() > 1)
            {
                repeatingRulePKs.addAll(repeatingRulePKsForRuleName);
            }
        }

        return repeatingRulePKs;
    }

    protected String showBuildRuleDialog(AppReqBlock appReqBlock) throws Exception
    {
        String productStructurePK = initParam(appReqBlock.getReqParm("productStructurePK"), "0");

        Lookup calcLookup = new LookupComponent();

        // Build UIRulesVOs
        RulesVO[] rulesVOs = calcLookup.findRulesVOsByProductStructurePK(Long.parseLong(productStructurePK), false, null);

        UIRulesVO[] uiRulesVOs = null;

        if (rulesVOs != null)
        {
            uiRulesVOs = buildUIRulesVOs(rulesVOs);
        }

        // Get the ProductStructureVO
        ProductStructureVO[] productStructureVO = calcLookup.findProductStructureVOByPK(Long.parseLong(productStructurePK), false, null);

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVO", productStructureVO);

        getRelationInformation(productStructurePK, appReqBlock);

        return "/engine/jsp/buildRuleDialog.jsp";
    }

    protected String attachProductAndRule(AppReqBlock appReqBlock) throws Exception
    {
        saveRuleFilters(appReqBlock);

        String productStructurePK = initParam(appReqBlock.getReqParm("productStructurePK"), "0");
        String selectedRulePKs = initParam(appReqBlock.getReqParm("selectedRulePKs"), null);

        if (productStructurePK.equals("0"))
        {
            String message = "Company Structure Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showRelation(appReqBlock);
        }

        if (selectedRulePKs == null)
        {
            String message = "Rules Reqired";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showRelation(appReqBlock);
        }

        String[] rulesToAttachTokens = Util.fastTokenizer(selectedRulePKs, ",");

        List rulesToAttach = new ArrayList();

        for (int i = 0; i < rulesToAttachTokens.length; i++)
        {
            if (Util.isANumber(rulesToAttachTokens[i]))
            {
                rulesToAttach.add(new Long(rulesToAttachTokens[i]));
            }
        }

        //attach product and rule
        Calculator calcComponent = new engine.component.CalculatorComponent();

        calcComponent.attachRulesToProductStructure(Long.parseLong(productStructurePK), Util.convertLongToPrim((Long[]) rulesToAttach.toArray(new Long[rulesToAttach.size()])));

        return showBuildRuleDialog(appReqBlock);
    }

    protected String detachProductAndRule(AppReqBlock appReqBlock) throws Exception
    {
        saveRuleFilters(appReqBlock);

        String productStructurePK = initParam(appReqBlock.getReqParm("productStructurePK"), "0");
        String selectedRulePKs = initParam(appReqBlock.getReqParm("selectedRulePKs"), null);

        if (productStructurePK.equals("0"))
        {
            String message = "Product Structure Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showBuildRuleDialog(appReqBlock);
        }

        if (selectedRulePKs == null)
        {
            String message = "Rules Reqired";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showBuildRuleDialog(appReqBlock);
        }

        String[] rulesToAttachTokens = Util.fastTokenizer(selectedRulePKs, ",");

        List rulesToAttach = new ArrayList();

        for (int i = 0; i < rulesToAttachTokens.length; i++)
        {
            if (Util.isANumber(rulesToAttachTokens[i]))
            {
                rulesToAttach.add(new Long(rulesToAttachTokens[i]));
            }
        }

        //attach product and rule
        Calculator calcComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        calcComponent.detachRulesFromProductStructure(Long.parseLong(productStructurePK), Util.convertLongToPrim((Long[]) rulesToAttach.toArray(new Long[rulesToAttach.size()])));

        return showBuildRuleDialog(appReqBlock);
    }

    protected String cancelRelation(AppReqBlock appReqBlock) throws Exception
    {
        return showRelation(appReqBlock);
    }

    protected String showRuleSummary(AppReqBlock appReqBlock) throws Exception
    {
        TableDefVO[] tableDefVOs = getAllTableDefVOs(false, null);
        ScriptVO[] scriptVOs = getAllScriptVOs(false, null);
        RuleNameVO ruleNameVO = getRuleNameVO();

        Lookup calcLookup = new LookupComponent();

        UIRulesVO[] uiRulesVOs = null;

        RulesVO[] rulesVOs = calcLookup.findAllRulesVOs(false, null);

        if (rulesVOs != null)
        {
            uiRulesVOs = this.buildUIRulesVOs(rulesVOs);
        }

// Find Rules that are attached to something

        long[] attachedPKs = calcLookup.findAttachedRulesPKs();

        List attachedRulesPKs = new ArrayList();

        if (attachedPKs != null)
        {
            for (int i = 0; i < attachedPKs.length; i++)
            {
                attachedRulesPKs.add(new Long(attachedPKs[i]));
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("attachedRulesPKs", attachedRulesPKs);
        appReqBlock.getHttpServletRequest().setAttribute("ruleNameVO", ruleNameVO);
        appReqBlock.getHttpServletRequest().setAttribute("uiRulesVOs", uiRulesVOs);
        appReqBlock.getHttpServletRequest().setAttribute("tableDefVOs", tableDefVOs);
        appReqBlock.getHttpServletRequest().setAttribute("scriptVOs", scriptVOs);

        return "/engine/jsp/rules.jsp";
    }

    private RuleNameVO getRuleNameVO() throws Exception
    {
        Lookup calcLookup = new LookupComponent();

        RuleNameVO ruleNameVO = calcLookup.findRuleNameVO();

        return ruleNameVO;
    }

    private ScriptVO[] getAllScriptVOs(boolean includeChildVOs, List voExclusionList) throws Exception
    {
        Lookup calcLookup = new LookupComponent();

        return calcLookup.findAllScriptVOs(includeChildVOs, voExclusionList);
    }

    private TableDefVO[] getAllTableDefVOs(boolean includeChildVOs, List voExclusionList) throws Exception
    {
        Lookup calcLookup = new LookupComponent();

        TableDefVO[] tableDefVOs = calcLookup.findAllTableDefVOs(includeChildVOs, voExclusionList);

        return tableDefVOs;
    }

    private String showFilterScriptRulesDialog(AppReqBlock appReqBlock) throws Exception
    {
        String filterType = Util.initString(appReqBlock.getReqParm("scriptFilterType"), "All");

        saveRuleFilters(appReqBlock);
        appReqBlock.getHttpServletRequest().setAttribute("productStructurePK", appReqBlock.getReqParm("productStructurePK"));

        if (!filterType.equalsIgnoreCase("All"))
        {
            getFilterNames(filterType, appReqBlock);
        }

        return "/engine/jsp/filterScriptRulesDialog.jsp";
    }

    private String showFilterTableRulesDialog(AppReqBlock appReqBlock) throws Exception
    {
        String filterType = Util.initString(appReqBlock.getReqParm("tableFilterType"), "All");

        saveRuleFilters(appReqBlock);
        appReqBlock.getHttpServletRequest().setAttribute("productStructurePK", appReqBlock.getReqParm("productStructurePK"));

        if (!filterType.equalsIgnoreCase("All"))
        {
            getFilterNames(filterType, appReqBlock);
        }

        return "/engine/jsp/filterTableRulesDialog.jsp";
    }

    private String populateFilterNames(AppReqBlock appReqBlock) throws Exception
    {
        String filterPage = appReqBlock.getReqParm("filterPage");
        String filterType = appReqBlock.getReqParm("filterType");

        if (!filterType.equalsIgnoreCase("All"))
        {
            getFilterNames(filterType, appReqBlock);
        }

        appReqBlock.getHttpServletRequest().setAttribute("filterType", filterType);
        appReqBlock.getHttpServletRequest().setAttribute("productStructurePK", appReqBlock.getReqParm("productStructurePK"));

        if (filterPage.equalsIgnoreCase("Script"))
        {
            appReqBlock.getHttpServletRequest().setAttribute("scriptFilterType", filterType);
            appReqBlock.getHttpServletRequest().setAttribute("tableFilterType", appReqBlock.getReqParm("tableFilterType"));
            appReqBlock.getHttpServletRequest().setAttribute("tableFilterName", appReqBlock.getReqParm("tableFilterName"));
            return "/engine/jsp/filterScriptRulesDialog.jsp";
        }
        else
        {
            appReqBlock.getHttpServletRequest().setAttribute("tableFilterType", filterType);
            appReqBlock.getHttpServletRequest().setAttribute("scriptFilterType", appReqBlock.getReqParm("scriptFilterType"));
            appReqBlock.getHttpServletRequest().setAttribute("scriptFilterName", appReqBlock.getReqParm("scriptFilterName"));
            return "/engine/jsp/filterTableRulesDialog.jsp";
        }
    }

    private void getFilterNames(String filterType, AppReqBlock appReqBlock) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        RulesVO[] rulesVOs = engineLookup.findAllRulesVOs(false, new ArrayList());

        if (rulesVOs != null)
        {
            if (filterType.equalsIgnoreCase("Process"))
            {
                getUniqueProcessNames(appReqBlock, rulesVOs);
            }
            else
            {
                getUniqueRuleNames(appReqBlock, rulesVOs);
            }
        }
    }

    private void getUniqueProcessNames(AppReqBlock appReqBlock, RulesVO[] rulesVOs) throws Exception
    {
        rulesVOs = (RulesVO[]) Util.sortObjects(rulesVOs, new String[] {"getProcessName"});

        List processNameList = new ArrayList();
        for (int i = 0; i < rulesVOs.length; i++)
        {
            if (!processNameList.contains(rulesVOs[i].getProcessName()))
            {
                processNameList.add(rulesVOs[i].getProcessName());
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("filterNames", processNameList);
    }

    private void getUniqueRuleNames(AppReqBlock appReqBlock, RulesVO[] rulesVOs) throws Exception
    {
        rulesVOs = (RulesVO[]) Util.sortObjects(rulesVOs, new String[] {"getRuleName"});

        List ruleNameList = new ArrayList();
        for (int i = 0; i < rulesVOs.length; i++)
        {
            if (!ruleNameList.contains(rulesVOs[i].getRuleName()))
            {
                ruleNameList.add(rulesVOs[i].getRuleName());
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("filterNames", ruleNameList);
    }

    private String filterScriptRules(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("scriptFilterType", appReqBlock.getReqParm("filterType"));
        if (!appReqBlock.getReqParm("filterType").equalsIgnoreCase("All"))
        {
            appReqBlock.getHttpServletRequest().setAttribute("scriptFilterName", appReqBlock.getReqParm("filterName"));
        }
        appReqBlock.getHttpServletRequest().setAttribute("tableFilterType", appReqBlock.getReqParm("tableFilterType"));
        appReqBlock.getHttpServletRequest().setAttribute("tableFilterName", appReqBlock.getReqParm("tableFilterName"));
        appReqBlock.getHttpServletRequest().setAttribute("productStructurePK", appReqBlock.getReqParm("productStructurePK"));

        return showBuildRuleDialog(appReqBlock);
    }

    private String filterTableRules(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("scriptFilterType", appReqBlock.getReqParm("scriptFilterType"));
        appReqBlock.getHttpServletRequest().setAttribute("scriptFilterName", appReqBlock.getReqParm("scriptFilterName"));
        appReqBlock.getHttpServletRequest().setAttribute("tableFilterType", appReqBlock.getReqParm("filterType"));
        if (!appReqBlock.getReqParm("filterType").equalsIgnoreCase("All"))
        {
            appReqBlock.getHttpServletRequest().setAttribute("tableFilterName", appReqBlock.getReqParm("filterName"));
        }
        appReqBlock.getHttpServletRequest().setAttribute("productStructurePK", appReqBlock.getReqParm("productStructurePK"));

        return showBuildRuleDialog(appReqBlock);
    }

    //**********************************
    //       HELPER METHODS
    //**********************************

    private void saveRuleFilters(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpServletRequest().setAttribute("scriptFilterType", appReqBlock.getReqParm("scriptFilterType"));
        appReqBlock.getHttpServletRequest().setAttribute("scriptFilterName", appReqBlock.getReqParm("scriptFilterName"));
        appReqBlock.getHttpServletRequest().setAttribute("tableFilterType", appReqBlock.getReqParm("tableFilterType"));
        appReqBlock.getHttpServletRequest().setAttribute("tableFilterName", appReqBlock.getReqParm("tableFilterName"));
    }

    private String getScriptName(long scriptId, ScriptVO[] scriptVO)
    {
        for (int i = 0; i < scriptVO.length; ++i)
        {
            String name = scriptVO[i].getScriptName();
            long id = scriptVO[i].getScriptPK();
            if (id == scriptId)
            {
                return name;
            }
            else
            {
                continue;
            }
        }
        return null;
    }

    private String getTableName(long tableDefId, TableDefVO[] tableVO)
    {
        for (int i = 0; i < tableVO.length; ++i)
        {
            String name = tableVO[i].getTableName();
            long id = tableVO[i].getTableDefPK();
            if (id == tableDefId)
            {
                return name;
            }
            else
            {
                continue;
            }
        }
        return null;
    }

    private void defaultValues(PageBean pageBean)
    {
        String defaultValue = "Please Select";

        pageBean.putValue("process", defaultValue);
        pageBean.putValue("event", defaultValue);
        pageBean.putValue("eventType", defaultValue);
        pageBean.putValue("rule", defaultValue);
        pageBean.putValue("script", defaultValue);
        pageBean.putValue("table", defaultValue);
        pageBean.putValue("description", "");

    }

    private void loadRuleList(PageBean pageBean,
                              List effDate,
                              List process,
                              List event,
                              List eventType,
                              List subRule,
                              List rule,
                              List ruleIds,
                              List scriptTable)
    {

        pageBean.putValues("effectiveDate",
                           (String[]) effDate.toArray(new String[effDate.size()]),
                           new String[]{"toString"},
                           null);

        pageBean.putValues("process",
                           (String[]) process.toArray(new String[process.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("event",
                           (String[]) event.toArray(new String[event.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("eventType",
                           (String[]) eventType.toArray(new String[eventType.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("subRule",
                           (String[]) subRule.toArray(new String[subRule.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("ruleName",
                           (String[]) rule.toArray(new String[rule.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("ruleIds",
                           (String[]) ruleIds.toArray(new String[ruleIds.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("scriptTable",
                           (String[]) scriptTable.toArray(new String[scriptTable.size()]),
                           new String[]{"toString"},
                           null);
    }

    private void loadProductStructureList(PageBean pageBean,
                                          List productStructureId,
                                          List company,
                                          List marketingPackage,
                                          List groupProduct,
                                          List area,
                                          List businessContract)
    {
        pageBean.putValues("productStructureId",
                           (String[]) productStructureId.toArray(new String
                                   [productStructureId.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("company",
                           (String[]) company.toArray(new String[company.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("marketingPackage",
                           (String[]) marketingPackage.toArray(new String[marketingPackage.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("groupProduct",
                           (String[]) groupProduct.toArray(new String[groupProduct.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("area",
                           (String[]) area.toArray(new String[area.size()]),
                           new String[]{"toString"},
                           null);
        pageBean.putValues("businessContract",
                           (String[]) businessContract.toArray(new String[businessContract.size()]),
                           new String[]{"toString"},
                           null);
    }

    /**
     * Displays associated script for the rule selected.
     * @param appReqBlock
     */
    protected String showAssoicatedScriptForRule(AppReqBlock appReqBlock) throws Exception
    {
        String scriptPK = Util.initString(appReqBlock.getReqParm("scriptOrTablePK"), "0");

        appReqBlock.getHttpServletRequest().setAttribute("scriptPK", scriptPK);

        appReqBlock.getHttpServletRequest().setAttribute("main", SCRIPT_DIALOG);

        return TEMPLATE_MAIN;
    }
}
