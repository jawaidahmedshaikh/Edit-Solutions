package codetable.ui.servlet;

import edit.common.CodeTableWrapper;
import edit.common.exceptions.EDITCreateUpdateException;
import edit.common.vo.*;
import edit.portal.common.transactions.Transaction;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;

import codetable.business.CodeTable;
import codetable.component.CodeTableComponent;

import edit.common.exceptions.EDITDeleteException;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Feb 5, 2004
 * Time: 3:00:55 PM
 * To change this template use Options | File Templates.
 */
public class CodeTableTran extends Transaction {

    private static final String ACTION = "action";
    private static final String SHOW_CODETABLE_DEF_SUMMARY = "showCodeTableDefSummary";
    private static final String ADD_CODETABLE = "addCodeTable";
    private static final String SAVE_CODETABLE = "saveCodeTable";
    private static final String CANCEL_CODETABLE = "cancelCodeTable";
    private static final String DELETE_CODETABLE = "deleteCodeTable";
    private static final String SHOW_CODETABLE_RELATIONS = "showCodeTableRelations";
    private static final String SHOW_ATTACHED_CODETABLES = "showAttachedCodeTables";
    private static final String ATTACH_COMPANY_AND_CODETABLE = "attachCompanyAndCodeTable";
    private static final String CANCEL_CODETABLE_RELATIONS = "cancelCodeTableRelations";
    private static final String DETACH_COMPANY_AND_CODETABLE = "detachCompanyAndCodeTable";
    private static final String SHOW_BUILD_CODETABLE_DIALOG = "showBuildCodeTableDialog";
    private static final String CLONE_FILTERED_CODETABLE = "cloneFilteredCodeTable";
    private static final String SHOW_FILTERED_CODETABLE_DIALOG = "showFilteredCodeTableDialog";
    private static final String SAVE_FILTERED_CODETABLE = "saveFilteredCodeTable";
    private static final String CANCEL_FILTERED_CODETABLE = "cancelFilteredCodeTable";

    private static final String SHOW_ONLINE_REPORT_SUMMARY = "showOnlineReportSummary";
    private static final String ADD_ONLINE_REPORT = "addOnlineReport";
    private static final String SAVE_ONLINE_REPORT = "saveOnlineReport";
    private static final String CANCEL_ONLINE_REPORT = "cancelOnlineReport";
    private static final String DELETE_ONLINE_REPORT = "deleteOnlineReport";
    private static final String SHOW_ONLINE_REPORTRELATIONS = "showOnlineReportRelations";
    private static final String SHOW_ATTACHED_ONLINE_REPORT = "showAttachedOnlineReport";
    private static final String ATTACH_COMPANY_AND_ONLINE_REPORT = "attachCompanyAndOnlineReport";
    private static final String CANCEL_ONLINE_REPORT_RELATIONS = "cancelOnlineReportRelations";
    private static final String DETACH_COMPANY_AND_ONLINE_REPORT = "detachCompanyAndOnlineReport";
    private static final String SHOW_CLONE_FILTERED_ONLINE_REPORT_DIALOG = "showCloneFilteredOnlineReportDialog";
    private static final String CLONE_FILTERED_ONLINE_REPORT = "cloneFilteredOnlineReport";

    private static final String CODETABLE_SUMMARY = "/codetable/jsp/codeTableSummary.jsp";
    private static final String CODETABLE_RELATION = "/codetable/jsp/codeTableRelation.jsp";
    private static final String BUILD_CODETABLE_DIALOG = "/codetable/jsp/buildCodeTableDialog.jsp";
    private static final String FILTERED_CODETABLE_DIALOG = "/codetable/jsp/filteredCodeTableDialog.jsp";

    private static final String ONLINE_REPORT_SUMMARY = "/codetable/jsp/onlineReportSummary.jsp";
    private static final String ONLINE_REPORT_RELATION = "/codetable/jsp/onlineReportRelation.jsp";
    private static final String CLONE_FILTERED_ONLINE_REPORT_DIALOG = "/codetable/jsp/cloneFilteredOnlineReportDialog.jsp";

    /**
     * NOTE: CompanyStructure and ProductStructure are used interchangeably
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getReqParm(ACTION);

        if (action.equals(SHOW_CODETABLE_DEF_SUMMARY))
        {
            return showCodeTableDefSummary(appReqBlock);
        }
        else if (action.equals(ADD_CODETABLE))
        {
            return addCodeTable(appReqBlock);
        }
        else if (action.equals(SAVE_CODETABLE))
        {
            return saveCodeTable(appReqBlock);
        }
        else if (action.equals(CANCEL_CODETABLE))
        {
            return cancelCodeTable(appReqBlock);
        }
        else if (action.equals(DELETE_CODETABLE))
        {
            return deleteCodeTable(appReqBlock);
        }
        else if (action.equals(SHOW_CODETABLE_RELATIONS))
        {
            return showCodeTableRelations(appReqBlock);
        }
        else if (action.equals(SHOW_ATTACHED_CODETABLES))
        {
            return showAttachedCodeTables(appReqBlock);
        }
        else if (action.equals(ATTACH_COMPANY_AND_CODETABLE))
        {
            return attachCompanyAndCodeTable(appReqBlock);
        }
        else if (action.equals(CANCEL_CODETABLE_RELATIONS))
        {
            return cancelCodeTableRelations(appReqBlock);
        }
        else if (action.equals(DETACH_COMPANY_AND_CODETABLE))
        {
            return detachCompanyAndCodeTable(appReqBlock);
        }
        else if (action.equals(SHOW_BUILD_CODETABLE_DIALOG))
        {
            return showBuildCodeTableDialog(appReqBlock);
        }
        else if (action.equals(CLONE_FILTERED_CODETABLE))
        {
            return cloneFilteredCodeTable(appReqBlock);
        }
        else if (action.equals(SHOW_FILTERED_CODETABLE_DIALOG))
        {
            return showFilteredCodeTableDialog(appReqBlock);
        }
        else if (action.equals(SAVE_FILTERED_CODETABLE))
        {
            return saveFilteredCodeTable(appReqBlock);
        }
        else if (action.equals(CANCEL_FILTERED_CODETABLE))
        {
            return cancelFilteredCodeTable(appReqBlock);
        }
        else if (action.equals(SHOW_ONLINE_REPORT_SUMMARY))
        {
            return showOnlineReportSummary(appReqBlock);
        }
        else if (action.equals(ADD_ONLINE_REPORT))
        {
            return addOnlineReport(appReqBlock);
        }
        else if (action.equals(SAVE_ONLINE_REPORT))
        {
            return saveOnlineReport(appReqBlock);
        }
        else if (action.equals(CANCEL_ONLINE_REPORT))
        {
            return cancelOnlineReport(appReqBlock);
        }
        else if (action.equals(DELETE_ONLINE_REPORT))
        {
            return deleteOnlineReport(appReqBlock);
        }
        else if (action.equals(SHOW_ONLINE_REPORTRELATIONS))
        {
            return showOnlineReportRelations(appReqBlock);
        }
//        else if (action.equals(SHOW_ATTACHED_ONLINE_REPORT))
//        {
//            return showAttachedOnlineReport(appReqBlock);
//        }
        else if (action.equals(ATTACH_COMPANY_AND_ONLINE_REPORT))
        {
            return attachCompanyAndOnlineReport(appReqBlock);
        }
        else if (action.equals(CANCEL_ONLINE_REPORT_RELATIONS))
        {
            return cancelOnlinelReportRelations(appReqBlock);
        }
        else if (action.equals(DETACH_COMPANY_AND_ONLINE_REPORT))
        {
            return detachCompanyAndOnlineReport(appReqBlock);
        }
        else if (action.equals(SHOW_CLONE_FILTERED_ONLINE_REPORT_DIALOG))
        {
            return showCloneFilteredOnlineReportDialog(appReqBlock);
        }
        else if (action.equals(CLONE_FILTERED_ONLINE_REPORT))
        {
            return cloneFilteredOnlineReport(appReqBlock);
        }
        else
        {
            throw new Exception("CodeTableTran: Invalid action " + action);
        }
    }

    protected String cancelFilteredCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        return showFilteredCodeTableDialog(appReqBlock);
    }

    protected String saveFilteredCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        FilteredCodeTableVO filteredCodeTableVO = getFilteredCodeTableVO(appReqBlock);

        String selectedCodeTableName = Util.initString(appReqBlock.getReqParm("selectedCodeTableName"), null);

        String message = "Filtered Code Table Successfully Updated";

        try
        {
            CodeTable codeTableComponent = new CodeTableComponent();

            codeTableComponent.saveFilteredCodeTable(filteredCodeTableVO);

            CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
            codeTableWrapper.reloadCodeTables();

            appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredCodeTablePK", filteredCodeTableVO.getFilteredCodeTablePK() + "");
            appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTablePK", null);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableName", selectedCodeTableName);
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }

        catch (EDITCreateUpdateException e)
        {
            message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

        }

        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }

        return showFilteredCodeTableDialog(appReqBlock);
    }

    private FilteredCodeTableVO getFilteredCodeTableVO(AppReqBlock appReqBlock)  throws Exception
    {
        String filteredCodeTablePK = Util.initString(appReqBlock.getReqParm("selectedFilteredCodeTablePK"), "0");

        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        FilteredCodeTableVO filteredCodeTableVO = codeTableComponent.getFilteredCodeTableEntry(Long.parseLong(filteredCodeTablePK));

        String codeDesc = Util.initString(appReqBlock.getReqParm("codeDesc"), null);

        if (!codeDesc.equals(null))
        {
            filteredCodeTableVO.setCodeDesc(codeDesc);
        }

        return filteredCodeTableVO;
    }

    protected String showFilteredCodeTableDialog(AppReqBlock appReqBlock) throws Exception
    {
//        String selectedCodeTablePK = Util.initString(appReqBlock.getReqParm("selectedCodeTablePK"), null);
        String selectedFilteredCodeTablePK = Util.initString(appReqBlock.getReqParm("selectedFilteredCodeTablePK"), null);
        String selectedCodeTableDefPK = Util.initString(appReqBlock.getReqParm("relationCodeTableDefPK"), null);
        String message = (String)appReqBlock.getHttpServletRequest().getAttribute("message");

        codetable.business.CodeTable codetableComponent = new codetable.component.CodeTableComponent();

        FilteredCodeTableVO filteredCodeTableVO = codetableComponent.getFilteredCodeTableEntry(Long.parseLong(selectedFilteredCodeTablePK));

        CodeTableDefVO codeTableDefVO = null;
        String codeTableName = null;

        if (selectedCodeTableDefPK != null)
        {
            codeTableDefVO = codetableComponent.getCodeTableDef(Long.parseLong(selectedCodeTableDefPK));
            codeTableName = codeTableDefVO.getCodeTableName();
        }
        else
        {
            codeTableName = Util.initString(appReqBlock.getReqParm("selectedCodeTableName"), null);
        }

        CodeTableVO codeTableVO = codetableComponent.getSpecificCodeTableVO(filteredCodeTableVO.getCodeTableFK());

        appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredCodeTablePK", selectedFilteredCodeTablePK);
        appReqBlock.getHttpServletRequest().setAttribute("selectedFilteredCodeTableVO", filteredCodeTableVO);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableName", codeTableName);
        appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableVO", codeTableVO);
        appReqBlock.getHttpServletRequest().setAttribute("relationCodeTableDefPK", selectedCodeTableDefPK);
        appReqBlock.getHttpServletRequest().setAttribute("message", message);


        return FILTERED_CODETABLE_DIALOG;
    }

    protected String cloneFilteredCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        String cloneFromCompanyStructurePK = Util.initString(appReqBlock.getReqParm("activeCompanyStructurePK"), null);

        String cloneToCompanyStructurePK = Util.initString(appReqBlock.getReqParm("cloneToCompanyStructurePK"), null);

        try
        {
            codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

            codeTableComponent.cloneFilteredCodeTables(Long.parseLong(cloneFromCompanyStructurePK), Long.parseLong(cloneToCompanyStructurePK));

            String message = "Company Structure Successfully Cloned";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            appReqBlock.setReqParm("activeCompanyStructurePK", cloneToCompanyStructurePK);
        }
        catch (EDITCreateUpdateException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            appReqBlock.setReqParm("activeCompanyStructurePK", cloneFromCompanyStructurePK);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }

        return showBuildCodeTableDialog(appReqBlock);
    }

    protected String showBuildCodeTableDialog(AppReqBlock appReqBlock) throws Exception
    {
        codetable.business.CodeTable codetableComponent = new codetable.component.CodeTableComponent();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String activeCompanyStructurePK = Util.initString(appReqBlock.getReqParm("activeCompanyStructurePK"), null);
        String selectedCodeTableDefPK = Util.initString(appReqBlock.getReqParm("relationCodeTableDefPK"), null);

        ProductStructureVO cloneFromCompanyStructureVO = engineLookup.findProductStructureVOByPK(Long.parseLong(activeCompanyStructurePK), false, null)[0];
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVO", cloneFromCompanyStructureVO);

        if (selectedCodeTableDefPK != null && activeCompanyStructurePK != null)
        {
            BIZCodeTableVO[] bizCodeTableVOs = codetableComponent.getCodeTableEntries(Long.parseLong(selectedCodeTableDefPK), Long.parseLong(activeCompanyStructurePK));
            appReqBlock.getHttpServletRequest().setAttribute("bizCodeTableVOs", bizCodeTableVOs);
            appReqBlock.getHttpServletRequest().setAttribute("relationCodeTableDefPK", selectedCodeTableDefPK);
        }

        //Get all ProductStructures
        ProductStructureVO[] productStructureVOs = engineLookup.findByTypeCode("Product", false, null);
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);

        return BUILD_CODETABLE_DIALOG;
    }

    protected String addCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "addMode");

        return showCodeTableDefSummary(appReqBlock);
    }

    protected String cancelCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.setReqParm("selectedCodeTablePK", null);

        return showCodeTableDefSummary(appReqBlock);
    }

    protected String saveCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        CodeTableVO codeTableVO = buildCodeTableVO(appReqBlock);

        String message = null;
        boolean dataErrorFound = false;

        if (codeTableVO.getCodeTableDefFK() == 0)
        {
            message = "No CodeTableDef Name Selected";
            dataErrorFound = true;
        }

        String codeValue = codeTableVO.getCode();
        if (codeValue == null || codeValue.equals(""))
        {
            message = "No Code value entered";
            dataErrorFound = true;
        }

        String descValue = codeTableVO.getCodeDesc();
        if (descValue == null || descValue.equals(""))
        {
            message = "No CodeDesc value entered";
            dataErrorFound = true;
        }

        if (dataErrorFound)
        {
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableVO", codeTableVO);
            appReqBlock.getHttpServletRequest().setAttribute("pageMode", "editMode");
        }
        else
        {

            try
            {
                codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();
                codeTableComponent.saveCodeTable(codeTableVO);
                appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableDefPK", null);
                appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTablePK", null);
                appReqBlock.getHttpServletRequest().setAttribute("message", "CodeTable Entry Saved Successfully");
            }

            catch (EDITCreateUpdateException e)
            {
                message = e.getMessage();

                appReqBlock.getHttpServletRequest().setAttribute("message", message);

            }

            catch (Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
                throw e;
            }
        }

        return showCodeTableDefSummary(appReqBlock);
    }

    protected CodeTableVO buildCodeTableVO(AppReqBlock appReqBlock)
    {

        String codeTablePK = Util.initString(appReqBlock.getReqParm("selectedCodeTablePK"), "0");
        String codeTableDefFK = Util.initString(appReqBlock.getReqParm("selectedCodeTableDefPK"), "0");

        String code = Util.initString(appReqBlock.getReqParm("code"), null);
        String codeDesc = Util.initString(appReqBlock.getReqParm("codeDesc"), null);

        CodeTableVO codeTableVO = new CodeTableVO();
        codeTableVO.setCodeTablePK(Long.parseLong(codeTablePK));
        codeTableVO.setCodeTableDefFK(Long.parseLong(codeTableDefFK));
        codeTableVO.setCode(code);
        codeTableVO.setCodeDesc(codeDesc);

        return codeTableVO;
    }


    protected String deleteCodeTable(AppReqBlock appReqBlock) throws Exception
    {

        String selectedCodeTablePK = Util.initString(appReqBlock.getReqParm("selectedCodeTablePK"), null); // Should fail without a PK

        try
        {
            codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

            codeTableComponent.deleteCodeTable(Long.parseLong(selectedCodeTablePK));

            appReqBlock.setReqParm("selectedCodeTablePK", null);

            appReqBlock.setReqParm("pageMode", "defaultMode");
        }
        catch (EDITDeleteException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            appReqBlock.setReqParm("selectedCodeTablePK", selectedCodeTablePK);

            appReqBlock.setReqParm("pageMode", "editMode");
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }

        return showCodeTableDefSummary(appReqBlock);
    }

    protected String cancelCodeTableRelations(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.setReqParm("activeCompanyStructurePK", null);
        appReqBlock.setReqParm("relationCodeTableDefPK", null);

        return showCodeTableRelations(appReqBlock);
    }

    protected String detachCompanyAndCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        String activeCompanyStructurePK = Util.initString(appReqBlock.getReqParm("activeCompanyStructurePK"), null);
        String selectedCodeTablePKs = Util.initString(appReqBlock.getReqParm("selectedCodeTablePKs"), null);
        String[] codeTableToDetachTokens = Util.fastTokenizer(selectedCodeTablePKs, ",");

        List codeTablePKsToDetach = new ArrayList();
        for (int i = 0; i < codeTableToDetachTokens.length; i++)
        {
            if (Util.isANumber(codeTableToDetachTokens[i]))
            {
                codeTablePKsToDetach.add(new Long(codeTableToDetachTokens[i]));
            }
        }

        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        for (int i = 0; i < codeTablePKsToDetach.size(); i++)
        {
            long codeTablePK = ((Long) codeTablePKsToDetach.get(i)).longValue();

            FilteredCodeTableVO filteredCodeTableVO = new FilteredCodeTableVO();
            filteredCodeTableVO.setProductStructureFK(Long.parseLong(activeCompanyStructurePK));
            filteredCodeTableVO.setCodeTableFK(codeTablePK);
            codeTableComponent.detachCodeTableFromProductStructure(filteredCodeTableVO);
        }

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        codeTableWrapper.reloadCodeTables();

        String message = "Detachment(s) Successful";
        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showBuildCodeTableDialog(appReqBlock);
    }

    protected String attachCompanyAndCodeTable(AppReqBlock appReqBlock) throws Exception
    {
        String activeCompanyStructurePK = Util.initString(appReqBlock.getReqParm("activeCompanyStructurePK"), null);
        String selectedCodeTablePKs = Util.initString(appReqBlock.getReqParm("selectedCodeTablePKs"), null);
        String[] codeTableToAttachTokens = Util.fastTokenizer(selectedCodeTablePKs, ",");

        List codeTablePKsToAttach = new ArrayList();
        for (int i = 0; i < codeTableToAttachTokens.length; i++)
        {
            if (Util.isANumber(codeTableToAttachTokens[i]))
            {
                codeTablePKsToAttach.add(new Long(codeTableToAttachTokens[i]));

            }
        }

        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        for (int i = 0; i < codeTablePKsToAttach.size(); i++)
        {
            long codeTablePK = ((Long) codeTablePKsToAttach.get(i)).longValue();
            FilteredCodeTableVO filteredCodeTableVO = new FilteredCodeTableVO();
            filteredCodeTableVO.setFilteredCodeTablePK(0);
            filteredCodeTableVO.setProductStructureFK(Long.parseLong(activeCompanyStructurePK));
            filteredCodeTableVO.setCodeTableFK(codeTablePK);
            codeTableComponent.saveFilteredCodeTable(filteredCodeTableVO);
        }

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        codeTableWrapper.reloadCodeTables();

        String message = "Attachment(s) Successful";
        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showBuildCodeTableDialog(appReqBlock);
    }

    protected String showAttachedCodeTables(AppReqBlock appReqBlock) throws Exception
    {
        return showCodeTableRelations(appReqBlock);
    }

   protected String showCodeTableDefSummary(AppReqBlock appReqBlock) throws Exception
   {
        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        String selectedCodeTableDefPK = Util.initString(appReqBlock.getReqParm("selectedCodeTableDefPK"), null);
        String selectedCodeTablePK = Util.initString(appReqBlock.getReqParm("selectedCodeTablePK"), null);


        String pageMode =  Util.initString((String)appReqBlock.getHttpServletRequest().getAttribute("pageMode"),null);

        if (pageMode == null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("pageMode", "defaultMode");
        }
        else if (pageMode.equalsIgnoreCase("addMode"))
        {
           selectedCodeTablePK = null;
           appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTablePK", null);
        }

        if (selectedCodeTableDefPK != null)
        {
            CodeTableVO[] codeTableVOs = codeTableComponent.getSelectedCodeTableEntries(Long.parseLong(selectedCodeTableDefPK));
            appReqBlock.getHttpServletRequest().setAttribute("codeTableVOs", codeTableVOs);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableDefPK", selectedCodeTableDefPK);
        }

        if (selectedCodeTablePK != null  && !selectedCodeTablePK.equals("0"))
        {
            CodeTableVO codeTableVO = codeTableComponent.getSpecificCodeTableVO(Long.parseLong(selectedCodeTablePK));

            appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableVO", codeTableVO);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTablePK", selectedCodeTablePK);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCodeTableDefPK", selectedCodeTableDefPK);
            appReqBlock.getHttpServletRequest().setAttribute("pageMode", "editMode");
        }

        CodeTableDefVO[] codeTableDefVOs = codeTableComponent.getAllCodeTableDefVOs();
        appReqBlock.getHttpServletRequest().setAttribute("codeTableDefVOs", codeTableDefVOs);

        return CODETABLE_SUMMARY;
    }

    /**
     * Get the data needed for the codeTable relation jsp, company structures and code tables and their attachments.
     * @param appReqBlock
     * @return codeTableRelations.jsp
     * @throws Exception
     */
    protected String showCodeTableRelations(AppReqBlock appReqBlock) throws Exception
    {
        codetable.business.CodeTable codetableComponent = new codetable.component.CodeTableComponent();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String activeCompanyStructurePK = Util.initString(appReqBlock.getReqParm("activeCompanyStructurePK"), null);
        String selectedCodeTableDefPK = Util.initString(appReqBlock.getReqParm("relationCodeTableDefPK"), null);

        if (selectedCodeTableDefPK != null && activeCompanyStructurePK != null)
        {
            BIZCodeTableVO[] bizCodeTableVOs = codetableComponent.getCodeTableEntries(Long.parseLong(selectedCodeTableDefPK), Long.parseLong(activeCompanyStructurePK));
            appReqBlock.getHttpServletRequest().setAttribute("bizCodeTableVOs", bizCodeTableVOs);
            appReqBlock.getHttpServletRequest().setAttribute("relationCodeTableDefPK", selectedCodeTableDefPK);
            appReqBlock.getHttpServletRequest().setAttribute("activeCompanyStructurePK", activeCompanyStructurePK);
        }

        //Get all ProductStructures
        ProductStructureVO[] productStructureVOs = engineLookup.findByTypeCode("Product", false, null);
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);


        //get all codeTableDefVOs with code table entries
        CodeTableDefVO[] codeTableDefVOs = codetableComponent.getAllCodeTableDefVOs();
        appReqBlock.getHttpServletRequest().setAttribute("codeTableDefVOs", codeTableDefVOs);

        return CODETABLE_RELATION;
    }

    /**
     * Get all OnlineReport records and place them in the request scope for the page to render
     * @param appReqBlock
     * @return String - jsp page
     * @throws Exception
     */
   protected String showOnlineReportSummary(AppReqBlock appReqBlock) throws Exception
   {
       codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

       OnlineReportVO[] onlineReportVOs = codeTableComponent.getAllOnlineReportVOs();

       String selectedOnlineReportPK = Util.initString(appReqBlock.getReqParm("selectedOnlineReportPK"), null);

       String pageMode =  Util.initString((String)appReqBlock.getHttpServletRequest().getAttribute("pageMode"),null);

       if (pageMode == null)
       {
           appReqBlock.getHttpServletRequest().setAttribute("pageMode", "defaultMode");
       }
       else if (pageMode.equalsIgnoreCase("addMode"))
       {
          selectedOnlineReportPK = null;
          appReqBlock.getHttpServletRequest().setAttribute("selectedOnlineReportPK", null);
       }

       if (selectedOnlineReportPK != null && !selectedOnlineReportPK.equals("0"))
       {
          OnlineReportVO onlineReportVO = codeTableComponent.getSpecificOnlineReportVO(Long.parseLong(selectedOnlineReportPK));
          appReqBlock.getHttpServletRequest().setAttribute("selectedOnlineReportVO", onlineReportVO);
          appReqBlock.getHttpServletRequest().setAttribute("selectedOnlineReportPK", selectedOnlineReportPK);
          appReqBlock.getHttpServletRequest().setAttribute("pageMode", "editMode");
       }

       appReqBlock.getHttpServletRequest().setAttribute("onlineReportVOs", onlineReportVOs);
       appReqBlock.getHttpServletRequest().setAttribute("selectedOnlineReportPK", selectedOnlineReportPK);


       //***  for future hardcoded for now - get filename from edit service config
//       EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
//       EDITReport[] editReport = editServicesConfig.getEDITReport();
//       String directoryName = null;
//
//       for (int i = 0; i < editReport.length; i++)
//       {
//             String reportName = editReport[i].getReportName();
//             if (reportName.equalsIgnoreCase("OnlineReport"))
//             {
//                directoryName = editReport[i].getWebDirectory();
//                break;
//             }
//       }
//
//       appReqBlock.getHttpServletRequest().setAttribute("directoryName", directoryName);
//
//       Vector fileNames = new Vector();
//       String fileName = null;
//       //get the filenames from the directory??????????????
//       fileNames.add(fileName);
//       String[] files = (String[])fileNames.toArray(new String[fileNames.size()]);
//
//       appReqBlock.getHttpServletRequest().setAttribute("fileNames", files);

       return ONLINE_REPORT_SUMMARY;
    }

    /**
     * Set the page mode for the request session
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String addOnlineReport(AppReqBlock appReqBlock) throws Exception
    {

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "addMode");

        return showOnlineReportSummary(appReqBlock);
    }

    /**
     * Clear the page of the current selection
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String cancelOnlineReport(AppReqBlock appReqBlock) throws Exception
    {

        appReqBlock.setReqParm("selectedOnlineReportPK", null);

        return showOnlineReportSummary(appReqBlock);
    }

    /**
     * Save the data in the request session as an OnlineReport table record.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String saveOnlineReport(AppReqBlock appReqBlock) throws Exception
    {
        OnlineReportVO onlineReportVO = buildOnlineReportVO(appReqBlock);

        try
        {
            codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();
            codeTableComponent.saveOnlineReport(onlineReportVO);
            appReqBlock.getHttpServletRequest().setAttribute("selectedOnlineReportPK", null);
            appReqBlock.getHttpServletRequest().setAttribute("message", "OnlineReport Entry Saved Successfully");
        }

        catch (EDITCreateUpdateException e)
        {
            appReqBlock.getHttpServletRequest().setAttribute("message", e.getMessage());
        }

        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }

        return showOnlineReportSummary(appReqBlock);
    }

    /**
     * Get the online Report table fields from the AppReqBlock and build the vo for the table update
     * @param appReqBlock
     * @return  OnlineReportVO
     */
    private OnlineReportVO buildOnlineReportVO(AppReqBlock appReqBlock)
    {
        String onlineReportPK = Util.initString(appReqBlock.getReqParm("selectedOnlineReportPK"), "0");

        String fileName = Util.initString(appReqBlock.getReqParm("fileName"), null);
        String description = Util.initString(appReqBlock.getReqParm("description"), null);
        String reportCategory = Util.initString(appReqBlock.getReqParm("reportCategoryCT"), null);

        OnlineReportVO onlineReportVO = new OnlineReportVO();
        onlineReportVO.setOnlineReportPK(Long.parseLong(onlineReportPK));
        onlineReportVO.setFileName(fileName);
        onlineReportVO.setDescription(description);
        onlineReportVO.setReportCategoryCT(reportCategory);

        return onlineReportVO;
    }

    /**
     * The onlineReportPK from the appReqBlock will be the key to the record to be deleted
     * @param appReqBlock
     * @return  onlineReportSummary.jsp
     * @throws Exception
     */
    protected String deleteOnlineReport(AppReqBlock appReqBlock) throws Exception
    {
        String selectedOnlineReportPK = Util.initString(appReqBlock.getReqParm("selectedOnlineReportPK"), null); // Should fail without a PK

         try
         {
             codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

             codeTableComponent.deleteOnlineReport(Long.parseLong(selectedOnlineReportPK));

             appReqBlock.setReqParm("selectedOnlineReportPK", null);

             appReqBlock.setReqParm("pageMode", "defaultMode");
         }
         catch (EDITDeleteException e)
         {
             String message = e.getMessage();

             appReqBlock.getHttpServletRequest().setAttribute("message", message);

             appReqBlock.setReqParm("selectedOnlineReportPK", selectedOnlineReportPK);

             appReqBlock.setReqParm("pageMode", "editMode");
         }
         catch (Exception e)
         {
             System.out.println(e);
             e.printStackTrace();
             throw e;
         }


        return showOnlineReportSummary(appReqBlock);
    }

    /**
     * Get the company structure and online onreport data to render the page and process it.
     * @param appReqBlock
     * @return  onlineReportRelations.jsp
     * @throws Exception
     */
    protected String showOnlineReportRelations(AppReqBlock appReqBlock) throws Exception
    {
        codetable.business.CodeTable codetableComponent = new codetable.component.CodeTableComponent();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String selectedCompanyStructurePK = Util.initString(appReqBlock.getReqParm("selectedCompanyStructurePK"), null);

        if (selectedCompanyStructurePK != null)
        {
            BIZOnlineReportVO[] bizOnlineReportVOs = codetableComponent.getOnlineReportEntries(Long.parseLong(selectedCompanyStructurePK));
            appReqBlock.getHttpServletRequest().setAttribute("bizOnlineReportVOs", bizOnlineReportVOs);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCompanyStructurePK", selectedCompanyStructurePK);
        }

        //Get all ProductStructures
        ProductStructureVO[] productStructureVOs = engineLookup.findByTypeCode("Product", false, null);
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);

        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();
        OnlineReportVO[] onlineReportVOs = codeTableComponent.getAllOnlineReportVOs();
        appReqBlock.getHttpServletRequest().setAttribute("onlineReportVOs", onlineReportVOs);

        return ONLINE_REPORT_RELATION;
    }

//    protected String showAttachedOnlineReport(AppReqBlock appReqBlock) throws Exception
//    {
//        return showOnlineReportRelations(appReqBlock);
//    }

    /**
     * The attach button on the onlineReportRelations page will invoke this method.  From the page selections, Filtered
     * Online Report records will be built and saved to the dataabase.
     * @param appReqBlock
     * @return  onlineReportRelations.jsp
     * @throws Exception
     */
    protected String attachCompanyAndOnlineReport(AppReqBlock appReqBlock) throws Exception
    {
        String selectedCompanyStructurePK = Util.initString(appReqBlock.getReqParm("selectedCompanyStructurePK"), null);
        String selectedOnlineReportPKs = Util.initString(appReqBlock.getReqParm("selectedOnlineReportPKs"), null);
        String[] onlineReportToAttachTokens = Util.fastTokenizer(selectedOnlineReportPKs, ",");

        List onlineReportToAttach = new ArrayList();
        for (int i = 0; i < onlineReportToAttachTokens.length; i++)
        {

            if (Util.isANumber(onlineReportToAttachTokens[i]))
            {
                onlineReportToAttach.add(new Long(onlineReportToAttachTokens[i]));
            }
        }

        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        for (int i = 0; i < onlineReportToAttach.size(); i++)
        {
            long onlineReportPK = ((Long) onlineReportToAttach.get(i)).longValue();
            FilteredOnlineReportVO filteredOnlineReportVO = new FilteredOnlineReportVO();
            filteredOnlineReportVO.setFilteredOnlineReportPK(0);
            filteredOnlineReportVO.setProductStructureFK(Long.parseLong(selectedCompanyStructurePK));
            filteredOnlineReportVO.setOnlineReportFK(onlineReportPK);
            codeTableComponent.saveFilteredOnlineReport(filteredOnlineReportVO);
        }

        return showOnlineReportRelations(appReqBlock);
    }

    /**
     * When the cancel button is hit, this method is invoked.  The page is rebuilt as if the first time entered.
     * @param appReqBlock
     * @return onlineReportRelations.jsp
     * @throws Exception
     */
    protected String cancelOnlinelReportRelations(AppReqBlock appReqBlock) throws Exception
    {
        return showOnlineReportRelations(appReqBlock);
    }

    /**
     * For the selected company and onlineReport keys, delete the FilteredOnlineReport entry that associates these two tables.
     * @param appReqBlock
     * @return  onlineReportRelations.jsp
     * @throws Exception
     */
    protected String detachCompanyAndOnlineReport(AppReqBlock appReqBlock) throws Exception
    {
        String selectedCompanyStructurePK = Util.initString(appReqBlock.getReqParm("selectedCompanyStructurePK"), null);
        String selectedOnlineReportPKs = Util.initString(appReqBlock.getReqParm("selectedOnlineReportPKs"), null);
        String[] onlineReportToDetachTokens = Util.fastTokenizer(selectedOnlineReportPKs, ",");

        List onlineReportToDetach = new ArrayList();
        for (int i = 0; i < onlineReportToDetachTokens.length; i++)
        {

            if (Util.isANumber(onlineReportToDetachTokens[i]))
            {
                onlineReportToDetach.add(new Long(onlineReportToDetachTokens[i]));

            }
        }

       codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        for (int i = 0; i < onlineReportToDetach.size(); i++)
        {
            long onlineReportPK = ((Long) onlineReportToDetach.get(i)).longValue();

            FilteredOnlineReportVO filteredOnlineReportVO = new FilteredOnlineReportVO();
            filteredOnlineReportVO.setProductStructureFK(Long.parseLong(selectedCompanyStructurePK));
            filteredOnlineReportVO.setOnlineReportFK(onlineReportPK);
            codeTableComponent.detachOnlineReportFromProductStructure(filteredOnlineReportVO);
        }

        String message = "Detachment(s) Successful";
        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showOnlineReportRelations(appReqBlock);
    }

    /**
     * The clone button on the relations page will invoke this method.  A new page is displayed for copying the Filtered
     * Online Report records of one company to another.
     * @param appReqBlock
     * @return cloneFilteredOnineReportDialog.jsp
     * @throws Exception
     */
    protected String showCloneFilteredOnlineReportDialog(AppReqBlock appReqBlock) throws Exception
    {

        codetable.business.CodeTable codetableComponent = new codetable.component.CodeTableComponent();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String selectedCompanyStructurePK = Util.initString(appReqBlock.getReqParm("selectedCompanyStructurePK"), null);

        ProductStructureVO cloneFromCompanyStructureVO = engineLookup.findProductStructureVOByPK(Long.parseLong(selectedCompanyStructurePK), false, null)[0];
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVO", cloneFromCompanyStructureVO);

        if (selectedCompanyStructurePK != null)
        {
            BIZOnlineReportVO[] bizOnlineReportVOs = codetableComponent.getOnlineReportEntriesForProductStructure(Long.parseLong(selectedCompanyStructurePK));
            appReqBlock.getHttpServletRequest().setAttribute("bizOnlineReportVOs", bizOnlineReportVOs);
            appReqBlock.getHttpServletRequest().setAttribute("selectedCompanyStructurePK", selectedCompanyStructurePK);
        }

        //Get all ProductStructures
        ProductStructureVO[] productStructureVOs = engineLookup.findByTypeCode("Product", false, null);
        appReqBlock.getHttpServletRequest().setAttribute("companyStructureVOs", productStructureVOs);

        return CLONE_FILTERED_ONLINE_REPORT_DIALOG;
    }

    /**
     * This method perform the copying of FilteredOnlineReport records for the from and to companyies selected.
     * @param appReqBlock
     * @return cloneFilteredOnineReportDialog.jsp
     * @throws Exception
     */
    protected String cloneFilteredOnlineReport(AppReqBlock appReqBlock) throws Exception
    {
        String cloneFromCompanyStructurePK = Util.initString(appReqBlock.getReqParm("selectedCompanyStructurePK"), null);

        String cloneToCompanyStructurePK = Util.initString(appReqBlock.getReqParm("cloneToCompanyStructurePK"), null);

        if (cloneToCompanyStructurePK == null)
        {
            String message = "Clone TO Company Structure Not Entered";
            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        else
        {
              try
              {
                  codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

                  codeTableComponent.cloneFilteredOnlineReport(Long.parseLong(cloneFromCompanyStructurePK), Long.parseLong(cloneToCompanyStructurePK));

                  String message = "Company Structure Successfully Cloned";

                  appReqBlock.getHttpServletRequest().setAttribute("message", message);
              }

              catch (EDITCreateUpdateException e)
              {
                  String message = e.getMessage();

                  appReqBlock.getHttpServletRequest().setAttribute("message", message);
              }
              catch (Exception e)
              {
                  System.out.println(e);
                  e.printStackTrace();

                  throw e;
              }
        }

        appReqBlock.setReqParm("selectedCompanyStructurePK", cloneFromCompanyStructurePK);


        return showCloneFilteredOnlineReportDialog(appReqBlock);
    }
}
