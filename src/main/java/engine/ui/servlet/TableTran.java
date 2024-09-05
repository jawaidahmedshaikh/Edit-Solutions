/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.ui.servlet;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.exceptions.EDITCreateUpdateException;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.VOEditException;
import edit.common.vo.*;
import edit.common.vo.user.UIRulesVO;
import edit.portal.common.transactions.Transaction;
import engine.business.Calculator;
import engine.business.Lookup;
import engine.common.Constants;
import engine.component.CalculatorComponent;
import engine.component.LookupComponent;
import engine.FilteredFund;
import engine.ChargeCode;
import event.dm.dao.DAOFactory;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.dm.SMException;
import fission.global.AppReqBlock;
import fission.utility.*;

import java.util.*;

/**
 * TableTran is used to handle Table related requests
 */
public class TableTran extends Transaction
{

    // Used to store instance of controller
    //private static final String CREATE_NEW_TABLE 	           = "createNewTable";
//    private static final String TABLE_ID = "tableId";
    private static final String TABLE_NAME = "tableName";
//    private static final String ACCESS_TYPE = "accessType";
//    private static final String USER_KEY = "userKey";
//    private static final String SEX = "sex";
//    private static final String CLASS_TYPE = "classType";
//    private static final String BAND_AMOUNT = "bandAmount";

    private static final String ACTION = "action";

    //Unit Value
    private static final String SHOW_UNIT_VALUE_TABLE = "showUnitValueTable";
    private static final String SHOW_UNIT_VALUE_DETAIL_SUMMARY = "showUnitValueDetailSummary";
    private static final String SHOW_UNIT_VALUES = "showUnitValues";
    private static final String UPDATE_UNIT_VALUES = "updateUnitValues";
    private static final String DELETE_UNIT_VALUES = "deleteUnitValues";
    private static final String CANCEL_UNIT_VALUES = "cancelUnitValues";
    private static final String UNIT_VALUE_TABLE = "/engine/jsp/unitValueTable.jsp";
    private static final String INTEREST_RATE_TABLE = "/engine/jsp/interestRateTable.jsp";
    private static final String IMPORT_UNIT_VALUES = "importUnitValues";
    private static final String IMPORT_RATE_TABLES = "importRateTables";
    private static final String VO_EDIT_EXCEPTION_DIALOG = "/common/jsp/VOEditExceptionDialog.jsp";
    private static final String UPDATE_INTEREST_RATES = "updateInterestRates";
    private static final String DELETE_INTEREST_RATES = "deleteInterestRates";
    private static final String CANCEL_INTEREST_RATES = "cancelInterestRates";
    private static final String SHOW_INTEREST_RATE_TABLE = "showInterestRateTable";
    private static final String SHOW_INTEREST_RATE_DETAIL_SUMMARY = "showInterestRateDetailSummary";
    private static final String SHOW_INTEREST_RATES = "showInterestRates";
    private static final String SHOW_VO_EDIT_EXCEPTION_DIALOG = "showVOEditExceptionDialog";
    private static final String SHOW_TABLE_RATES = "showTableRates";
    private static final String ADD_TABLE = "addTable";
    private static final String CANCEL_TABLE_EDITS = "cancelTableEdits";
    private static final String SHOW_ASSOCIATED_RULES_FOR_TABLE = "showAssociatedRulesForTable";
    private static final String SAVE_TABLE = "saveTable";
    private static final String SHOW_FILTERED_TRANSACTION_SUMMARY = "showFilteredTransactionSummary";
    private static final String SHOW_FILTERED_TRANSACTION_CORRESPONDENCE_DIALOG = "showFilteredTransactionCorrespondenceDialog";
    private static final String FILTERED_TRANSACTION_SUMMARY = "/engine/jsp/filteredTransactionSummary.jsp";
    private static final String FILTERED_TRANSACTION_CORRESPONDENCE_DIALOG = "/engine/jsp/filteredTransactionCorrespondenceDialog.jsp";
//    private static final String CANCEL_CORRESPONDENCE_DIALOG   = "cancelCorrespondenceDialog";

    private static final String SHOW_AREA_SUMMARY = "showAreaSummary";
    private static final String ADD_AREA = "addArea";
    private static final String SAVE_AREA = "saveArea";
    private static final String CANCEL_AREA_EDITS = "cancelAreaEdits";
    private static final String SHOW_AREA_DETAIL = "showAreaDetail";
    private static final String DELETE_AREA = "deleteArea";
    private static final String SHOW_AREA_RELATIONS = "showAreaRelations";
    private static final String SHOW_ATTACHED_AREAS = "showAttachedAreas";
    private static final String ATTACH_PRODUCT_AND_AREA = "attachProductAndArea";
    private static final String CANCEL_AREA_RELATIONS = "cancelAreaRelations";
    private static final String DETACH_PRODUCT_AND_AREA = "detachProductAndArea";
    private static final String SHOW_CLONE_FILTERED_AREA_DIALOG = "showCloneFilteredAreaDialog";
    private static final String CLONE_FILTERED_AREA = "cloneFilteredArea";
    private static final String SHOW_FILTERED_AREA_DIALOG = "showFilteredAreaDialog";
    private static final String SAVE_FILTERED_AREA = "saveFilteredArea";
    private static final String CANCEL_FILTERED_AREA_EDITS = "cancelFilteredAreaEdits";

    private static final String AREA_SUMMARY = "/engine/jsp/areaSummary.jsp";
    private static final String AREA_RELATION = "/engine/jsp/areaRelation.jsp";
    private static final String CLONE_FILTERED_AREA_DIALOG = "/engine/jsp/cloneFilteredAreaDialog.jsp";
    private static final String FILTERED_AREA_DIALOG = "/engine/jsp/filteredAreaDialog.jsp";

    //ends Unit Value

    // Used to store or retrieve bean information
//    private PageBean pageBean;
//    String fileName = null;

    /**
     * Used to execute transaction
     */
    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getReqParm(ACTION);

        if (action.equalsIgnoreCase("showTableDefault"))
        {
            return showTableDefault(appReqBlock);
        }
        else if (action.equals("showTableSelectionDialog"))
        {
            showTableSelectionDialog(appReqBlock);

            return "/engine/jsp/tableselectiondialog.jsp";
        }
        else if (action.equals("showTableKeyCols"))
        {
            showTableKeyCols(appReqBlock);

            return "/engine/jsp/tabkeycols.jsp";
        }
        else if (action.equals("deleteTable"))
        {
            return deleteTable(appReqBlock);
        }
        else if (action.equals(SHOW_UNIT_VALUE_TABLE))
        {
            return showUnitValueTable(appReqBlock);
        }
        else if (action.equals(SHOW_UNIT_VALUE_DETAIL_SUMMARY))
        {
            return showUnitValueDetailSummary(appReqBlock);
        }
        else if (action.equals(SHOW_UNIT_VALUES))
        {
            return showUnitValues(appReqBlock);
        }
        else if (action.equals(UPDATE_UNIT_VALUES))
        {
            return updateUnitValues(appReqBlock);
        }
        else if (action.equals(DELETE_UNIT_VALUES))
        {
            return deleteUnitValues(appReqBlock);
        }
        else if (action.equals(CANCEL_UNIT_VALUES))
        {
            return cancelUnitValues(appReqBlock);
        }
        else if (action.equals(IMPORT_UNIT_VALUES))
        {
            return importUnitValues(appReqBlock);
        }
        else if (action.equals(SHOW_INTEREST_RATE_TABLE))
        {
            return showInterestRateTable(appReqBlock);
        }
        else if (action.equals(SHOW_INTEREST_RATE_DETAIL_SUMMARY))
        {
            return showInterestRateDetailSummary(appReqBlock);
        }
        else if (action.equals(SHOW_INTEREST_RATES))
        {
            return showInterestRates(appReqBlock);
        }
        else if (action.equals(UPDATE_INTEREST_RATES))
        {
            return updateInterestRates(appReqBlock);
        }
        else if (action.equals(DELETE_INTEREST_RATES))
        {
            return deleteInterestRates(appReqBlock);
        }
        else if (action.equals(CANCEL_INTEREST_RATES))
        {
            return cancelInterestRates(appReqBlock);
        }
        else if (action.equals(SHOW_VO_EDIT_EXCEPTION_DIALOG))
        {
            return showVOEditExceptionDialog(appReqBlock);
        }
        else if (action.equals(IMPORT_RATE_TABLES))
        {
            return importRateTables(appReqBlock);
        }
        else if (action.equals(SHOW_TABLE_RATES))
        {
            return showTableRates(appReqBlock);
        }
        else if (action.equals(ADD_TABLE))
        {
            return addTable(appReqBlock);
        }
        else if (action.equals(CANCEL_TABLE_EDITS))
        {
            return cancelTableEdits(appReqBlock);
        }
        else if (action.equals(SHOW_ASSOCIATED_RULES_FOR_TABLE))
        {
            return showAssociatedRulesForTable(appReqBlock);
        }
        else if (action.equals(SAVE_TABLE))
        {
            return saveTable(appReqBlock);
        }
        else if (action.equals(SHOW_FILTERED_TRANSACTION_SUMMARY))
        {
            return showFilteredTransactionSummary(appReqBlock);
        }
        else if (action.equals(SHOW_FILTERED_TRANSACTION_CORRESPONDENCE_DIALOG))
        {
            return showFilteredTransactionCorrespondenceDialog(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_SUMMARY))
        {
            return showAreaSummary(appReqBlock);
        }
        else if (action.equals(ADD_AREA))
        {
            return addArea(appReqBlock);
        }
        else if (action.equals(SAVE_AREA))
        {
            return saveArea(appReqBlock);
        }
        else if (action.equals(CANCEL_AREA_EDITS))
        {
            return cancelAreaEdits(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_DETAIL))
        {
            return showAreaDetail(appReqBlock);
        }
        else if (action.equals(DELETE_AREA))
        {
            return deleteArea(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_RELATIONS))
        {
            return showAreaRelations(appReqBlock);
        }
        else if (action.equals(SHOW_ATTACHED_AREAS))
        {
            return showAttachedAreas(appReqBlock);
        }
        else if (action.equals(ATTACH_PRODUCT_AND_AREA))
        {
            return attachProductAndArea(appReqBlock);
        }
        else if (action.equals(CANCEL_AREA_RELATIONS))
        {
            return cancelAreaRelations(appReqBlock);
        }
        else if (action.equals(DETACH_PRODUCT_AND_AREA))
        {
            return detachProductAndArea(appReqBlock);
        }
//        else if (action.equals(SHOW_CLONE_FILTERED_AREA_DIALOG))
//        {
//            return showCloneFilteredAreaDialog(appReqBlock);
//        }
//        else if (action.equals(CLONE_FILTERED_AREA))
//        {
//            return cloneFilteredArea(appReqBlock);
//        }
//        else if (action.equals(SHOW_FILTERED_AREA_DIALOG))
//        {
//            return showFilteredAreaDialog(appReqBlock);
//        }
//        else if (action.equals(SAVE_FILTERED_AREA))
//        {
//            return saveFilteredArea(appReqBlock);
//        }
//        else if (action.equals(CANCEL_FILTERED_AREA_EDITS))
//        {
//            return cancelFilteredAreaEdits(appReqBlock);
//        }
        else
        {
            throw new Exception("TableTran: Invalid action " + action);
        }
    }

//    protected String cancelFilteredAreaEdits(AppReqBlock appReqBlock) throws Exception
//    {
//        return showFilteredAreaDialog(appReqBlock);
//    }

//    protected String saveFilteredArea(AppReqBlock appReqBlock) throws Exception
//    {
//        AreaVO areaVO = buildAreaVO(appReqBlock);
//
//        String filteredAreaPK = Util.initString(appReqBlock.getReqParm("filteredAreaPK"), null);
//
//        String filteredFundPK = Util.initString(appReqBlock.getReqParm("filteredFundPK"), "0");
//
//        String renewalToFundPK = Util.initString(appReqBlock.getReqParm("renewalFundToPK"), "0");
//
//        Calculator calculatorComponent = new CalculatorComponent();
//
//        calculatorComponent.updateFilteredArea(Long.parseLong(filteredAreaPK), Long.parseLong(filteredFundPK), Long.parseLong(renewalToFundPK), areaVO);
//
//        String message = "Filtered Area Successfully Updated";
//
//        appReqBlock.getHttpServletRequest().setAttribute("message", message);
//
//        return showFilteredAreaDialog(appReqBlock);
//    }
//
//    protected String showFilteredAreaDialog(AppReqBlock appReqBlock) throws Exception
//    {
//        String filteredAreaPK = Util.initString(appReqBlock.getReqParm("filteredAreaPK"), null);
//
//        Lookup calculatorLookup = new LookupComponent();
//
//        List voInclusionList = new ArrayList();
//        voInclusionList.add(AreaVO.class);
//
//        FilteredAreaVO filteredAreaVO = calculatorLookup.composeFilteredAreaVOByFilteredAreaPK(Long.parseLong(filteredAreaPK), voInclusionList);
//
//        appReqBlock.getHttpServletRequest().setAttribute("filteredAreaVO", filteredAreaVO);
//
//        voInclusionList.clear();
//        voInclusionList.add(FundVO.class);
//
//        FilteredFundVO[] filteredFundVOs = calculatorLookup.composeFilteredFundVOByProductStructurePK(filteredAreaVO.getProductStructureFK(), voInclusionList);
//
//        appReqBlock.getHttpServletRequest().setAttribute("filteredFundVOs", filteredFundVOs);
//
//        return FILTERED_AREA_DIALOG;
//    }
//
//    protected String cloneFilteredArea(AppReqBlock appReqBlock) throws Exception
//    {
//        String cloneFromProductStructurePK = Util.initString(appReqBlock.getReqParm("cloneFromProductStructurePK"), null);
//
//        String cloneToProductStructurePK = Util.initString(appReqBlock.getReqParm("cloneToProductStructurePK"), null);
//
//        try
//        {
//            Calculator calculatorComponent = new CalculatorComponent();
//
//            calculatorComponent.cloneFilteredAreas(Long.parseLong(cloneFromProductStructurePK), Long.parseLong(cloneToProductStructurePK));
//
//            String message = "Product Structure Successfully Cloned";
//
//            appReqBlock.getHttpServletRequest().setAttribute("message", message);
//        }
//        catch (EDITCreateUpdateException e)
//        {
//            String message = e.getMessage();
//
//            appReqBlock.getHttpServletRequest().setAttribute("message", message);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
//
//            throw e;
//        }
//
//        appReqBlock.setReqParm("activeProductStructurePK", cloneFromProductStructurePK);
//
//        return showCloneFilteredAreaDialog(appReqBlock);
//    }
//
//    protected String showCloneFilteredAreaDialog(AppReqBlock appReqBlock) throws Exception
//    {
//        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);
//
//        Lookup calculatorLookup = new LookupComponent();
//
//        ProductStructureVO cloneFromProductStructureVO = calculatorLookup.findProductStructureVOByPK(Long.parseLong(activeProductStructurePK), false, null)[0];
//        appReqBlock.getHttpServletRequest().setAttribute("cloneFromProductStructureVO", cloneFromProductStructureVO);
//
//        List voInclusionList = new ArrayList();
//        voInclusionList.add(AreaVO.class);
//
//        FilteredAreaVO[] filteredAreaVOs = calculatorLookup.composeFilteredAreaVOByProductStructurePK(Long.parseLong(activeProductStructurePK), voInclusionList);
//        appReqBlock.getHttpServletRequest().setAttribute("filteredAreaVOs", filteredAreaVOs);
//
//        ProductStructureVO[] productStructureVOs = calculatorLookup.findAllProductStructureVOs(false, null);
//        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);
//
//        return CLONE_FILTERED_AREA_DIALOG;
//    }

    protected String detachProductAndArea(AppReqBlock appReqBlock) throws Exception
    {
        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        String selectedFilteredAreaPKs = Util.initString(appReqBlock.getReqParm("selectedFilteredAreaPKs"), null);

        String[] filteredAreasToDetachTokens = Util.fastTokenizer(selectedFilteredAreaPKs, ",");

        List filteredAreasToDetach = new ArrayList();

        for (int i = 0; i < filteredAreasToDetachTokens.length; i++)
        {
            if (Util.isANumber(filteredAreasToDetachTokens[i]))
            {
                filteredAreasToDetach.add(new Long(filteredAreasToDetachTokens[i]));
            }
        }

        Calculator calculatorComponent = new CalculatorComponent();

        for (int i = 0; i < filteredAreasToDetach.size(); i++)
        {
            long filteredAreaPK = ((Long) filteredAreasToDetach.get(i)).longValue();

            calculatorComponent.detachAreaFromProductStructure(filteredAreaPK);
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", activeProductStructurePK);

        return showAreaRelations(appReqBlock);
    }

    protected String cancelAreaRelations(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.setReqParm("activeProductStructurePK", null);
        ;

        return showAreaRelations(appReqBlock);
    }

    protected String attachProductAndArea(AppReqBlock appReqBlock) throws Exception
    {
        long activeProductStructurePK = Long.parseLong(Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null));

        String selectedAreaPKs = Util.initString(appReqBlock.getReqParm("selectedAreaPKs"), null);

        String[] areasToAttachTokens = Util.fastTokenizer(selectedAreaPKs, ",");

        List areasToAttach = new ArrayList();

        for (int i = 0; i < areasToAttachTokens.length; i++)
        {
            if (Util.isANumber(areasToAttachTokens[i]))
            {
                areasToAttach.add(new Long(areasToAttachTokens[i]));
            }
        }

        Calculator calculatorComponent = new CalculatorComponent();

        try
        {
            for (int i = 0; i < areasToAttach.size(); i++)
            {
                long areaPK = ((Long) areasToAttach.get(i)).longValue();

                calculatorComponent.attachAreaValueToProductStructure(areaPK, activeProductStructurePK);
            }
        }
        catch (EDITCreateUpdateException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", activeProductStructurePK + "");

        return showAreaRelations(appReqBlock);
    }

    protected String showAttachedAreas(AppReqBlock appReqBlock) throws Exception
    {
        return showAreaRelations(appReqBlock);
    }

    protected String showAreaRelations(AppReqBlock appReqBlock) throws Exception
    {
        Lookup calculatorLookup = new LookupComponent();

        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        if (activeProductStructurePK != null)
        {
            ProductStructureVO activeProductStructureVO = calculatorLookup.findProductStructureVOByPK(Long.parseLong(activeProductStructurePK), false, null)[0];

            appReqBlock.getHttpServletRequest().setAttribute("activeProductStructureVO", activeProductStructureVO);

            List voInclusionList = new ArrayList();
            voInclusionList.add(AreaVO.class);

            FilteredAreaVO[] filteredAreaVOs = calculatorLookup.composeFilteredAreaVOByProductStructurePK(Long.parseLong(activeProductStructurePK), voInclusionList);

            appReqBlock.getHttpServletRequest().setAttribute("filteredAreaVOs", filteredAreaVOs);
        }

        // Get All ProductStructureVOs
        ProductStructureVO[] productStructureVOs = calculatorLookup.findAllProductTypeStructureVOs(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        // Get All Areas
        AreaVO[] areaVOs = calculatorLookup.composeAreaVOByOverrideStatus("P", new ArrayList()); // Only primaries

        appReqBlock.getHttpServletRequest().setAttribute("areaVOs", areaVOs);

        return AREA_RELATION;
    }

    protected String deleteArea(AppReqBlock appReqBlock) throws Exception
    {
        String activeAreaPK = Util.initString(appReqBlock.getReqParm("activeAreaPK"), null); // Should fail without a PK

        try
        {
            Calculator calculatorComponent = new CalculatorComponent();

            calculatorComponent.deleteArea(Long.parseLong(activeAreaPK));
        }
        catch (EDITDeleteException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            appReqBlock.getHttpServletRequest().setAttribute("activeAreaPK", activeAreaPK);

            appReqBlock.getHttpServletRequest().setAttribute("pageMode", "editMode");
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }

        return showAreaSummary(appReqBlock);
    }

    protected String showAreaDetail(AppReqBlock appReqBlock) throws Exception
    {
        String activeAreaPK = Util.initString(appReqBlock.getReqParm("activeAreaPK"), null); // Should fail without a PK

        Lookup calculatorLookup = new LookupComponent();

        List voInclusionList = new ArrayList();

        AreaVO areaVO = calculatorLookup.composeAreaVO(Long.parseLong(activeAreaPK), voInclusionList);

        appReqBlock.getHttpServletRequest().setAttribute("activeAreaVO", areaVO);

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "editMode");

        return showAreaSummary(appReqBlock);
    }

    protected String cancelAreaEdits(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.setReqParm("activeAreaPK", null);

        return showAreaSummary(appReqBlock);
    }

    protected String saveArea(AppReqBlock appReqBlock) throws Exception
    {
        AreaVO areaVO = buildAreaVO(appReqBlock);

        try
        {
            Calculator calculatorComponent = new CalculatorComponent();

            calculatorComponent.savePrimaryArea(areaVO);

            appReqBlock.setReqParm("activeAreaPK", null);

            appReqBlock.getHttpServletRequest().setAttribute("message", Constants.SuccessMsg.AREA_SUCCESSFULLY_SAVED);
        }
        catch (EDITCreateUpdateException e)
        {
            String message = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }

        return showAreaSummary(appReqBlock);
    }

    private AreaVO buildAreaVO(AppReqBlock appReqBlock)
    {
        String areaPK = Util.initString(appReqBlock.getReqParm("activeAreaPK"), "0");

        String areaCT = Util.initString(appReqBlock.getReqParm("areaCT"), null);
        String statementModeCT = Util.initString(appReqBlock.getReqParm("statementModeCT"), null);
        String freeLookTypeCT = Util.initString(appReqBlock.getReqParm("freeLookTypeCT"), null);

        String payoutLeadDaysCheck = Util.initString(appReqBlock.getReqParm("payoutLeadDaysCheck"), "0");
        String payoutLeadDaysEFT = Util.initString(appReqBlock.getReqParm("payoutLeadDaysEFT"), "0");
        String lookBackDays = Util.initString(appReqBlock.getReqParm("lookBackDays"), "0");

        String freeLookDaysNB = Util.initString(appReqBlock.getReqParm("freeLookDaysNB"), "0");
        String freeLookAgeNB = Util.initString(appReqBlock.getReqParm("freeLookAgeNB"), "0");
        String freeLookAgeBasedNB = Util.initString(appReqBlock.getReqParm("freeLookAgeBasedNB"), "0");

        String freeLookDaysInternal = Util.initString(appReqBlock.getReqParm("freeLookDaysInternal"), "0");
        String freeLookAgeInternal = Util.initString(appReqBlock.getReqParm("freeLookAgeInternal"), "0");
        String freeLookAgeBasedInternal = Util.initString(appReqBlock.getReqParm("freeLookAgeBasedInternal"), "0");

        String freeLookDaysExternal = Util.initString(appReqBlock.getReqParm("freeLookDaysExternal"), "0");
        String freeLookAgeExternal = Util.initString(appReqBlock.getReqParm("freeLookAgeExternal"), "0");
        String freeLookAgeBasedExternal = Util.initString(appReqBlock.getReqParm("freeLookAgeBasedExternal"), "0");

        String licenseSolicitGuideline = Util.initString(appReqBlock.getReqParm("licenseSolicitGuideline"), "0");

        String totalPremPercentAmount = appReqBlock.getReqParm("totalPremPercentAllowed");


        AreaVO areaVO = new AreaVO();

        areaVO.setAreaPK(Long.parseLong(areaPK));

        areaVO.setStatementModeCT(statementModeCT);
        areaVO.setFreeLookTypeCT(freeLookTypeCT);
        areaVO.setAreaCT(areaCT);

        areaVO.setPayoutLeadDaysCheck(Integer.parseInt(payoutLeadDaysCheck));
        areaVO.setPayoutLeadDaysEFT(Integer.parseInt(payoutLeadDaysEFT));
        areaVO.setLookBackDays(Integer.parseInt(lookBackDays));

        areaVO.setFreeLookDaysNB(Integer.parseInt(freeLookDaysNB));
        areaVO.setFreeLookAgeNB(Integer.parseInt(freeLookAgeNB));
        areaVO.setFreeLookAgeBasedNB(Integer.parseInt(freeLookAgeBasedNB));

        areaVO.setFreeLookDaysInternal(Integer.parseInt(freeLookDaysInternal));
        areaVO.setFreeLookAgeInternal(Integer.parseInt(freeLookAgeInternal));
        areaVO.setFreeLookAgeBasedInternal(Integer.parseInt(freeLookAgeBasedInternal));

        areaVO.setFreeLookDaysExternal(Integer.parseInt(freeLookDaysExternal));
        areaVO.setFreeLookAgeExternal(Integer.parseInt(freeLookAgeExternal));
        areaVO.setFreeLookAgeBasedExternal(Integer.parseInt(freeLookAgeBasedExternal));
        areaVO.setLicenseSolicitGuideline(Integer.parseInt(licenseSolicitGuideline));

        if (Util.isANumber(totalPremPercentAmount))
        {
            areaVO.setTotalPremPercentAllowed(new EDITBigDecimal(totalPremPercentAmount).getBigDecimal());
        }
        return areaVO;
    }

    protected String addArea(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.setReqParm("activeAreaPK", null);

        appReqBlock.getHttpServletRequest().setAttribute("pageMode", "addMode");

        return showAreaSummary(appReqBlock);
    }

    protected String showAreaSummary(AppReqBlock appReqBlock) throws Exception
    {
        AreaVO activeAreaVO = null;

        String activeAreaPK = Util.initString(appReqBlock.getReqParm("activeAreaPK"), null);

        String pageMode = (String) appReqBlock.getHttpServletRequest().getAttribute("pageMode");

        if (pageMode == null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("pageMode", "defaultMode");
        }

        Lookup engineLookup = new LookupComponent();

        if (activeAreaPK != null)
        {
            List voInclusionList = new ArrayList();
            activeAreaVO = engineLookup.composeAreaVO(Long.parseLong(activeAreaPK), voInclusionList);
            appReqBlock.getHttpServletRequest().setAttribute("activeAreaVO", activeAreaVO);
        }

        List voInclusionList = new ArrayList();
        AreaVO[] areaVOs = engineLookup.composeAreaVOByOverrideStatus("P", voInclusionList);
        appReqBlock.getHttpServletRequest().setAttribute("areaVOs", areaVOs);

        return AREA_SUMMARY;
    }

    protected String saveTable(AppReqBlock appReqBlock) throws Exception
    {
        String operator = appReqBlock.getUserSession().getUsername();

        String rateTableCount = initParam(appReqBlock.getReqParm("rateTableCount"), "0");

        String tableDefPK = initParam(appReqBlock.getReqParm("selectTableDefPK"), "0");
        String tableName = initParam(appReqBlock.getReqParm("tableName"), null);
        String accessType = initParam(appReqBlock.getReqParm("selectAccessType"), null);

        if (tableName == null)
        {
            String message = "Table Information Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showTableDefault(appReqBlock);
        }

        String tableKeysPK = initParam(appReqBlock.getReqParm("tableKeysPK"), "0");
        String genderCode = initParam(appReqBlock.getReqParm("selectGenderCode"), null);
        String classTypeCode = initParam(appReqBlock.getReqParm("selectClassTypeCode"), null);
        String bandAmount = initParam(appReqBlock.getReqParm("bandAmount"), "0");
        String tableTypeCode = initParam(appReqBlock.getReqParm("selectTableTypeCode"), null);
        String stateCode = initParam(appReqBlock.getReqParm("selectStateCode"), null);
        String effectiveDay = initParam(appReqBlock.getReqParm("effectiveDay"), null);
        String effectiveMonth = initParam(appReqBlock.getReqParm("effectiveMonth"), null);
        String effectiveYear = initParam(appReqBlock.getReqParm("effectiveYear"), null);
        String effectiveDate = DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear, null);
        String userKey = initParam(appReqBlock.getReqParm("userKey"), "-");

        TableDefVO tableDefVO = new TableDefVO();
        tableDefVO.setTableName(tableName);
        tableDefVO.setAccessType(accessType);
        tableDefVO.setTableDefPK(Long.parseLong(tableDefPK));
        tableDefVO.setOperator(operator);

        TableKeysVO tableKeysVO = new TableKeysVO();
        tableKeysVO.setTableKeysPK(Long.parseLong(tableKeysPK));
        tableKeysVO.setGender(genderCode);
        tableKeysVO.setClassType(classTypeCode);
        tableKeysVO.setBandAmount(new EDITBigDecimal(bandAmount).getBigDecimal());
        tableKeysVO.setTableType(tableTypeCode);
        tableKeysVO.setState(stateCode);
        tableKeysVO.setEffectiveDate(effectiveDate);
        tableKeysVO.setUserKey(userKey);

        List rateTableVOs = new ArrayList();

        for (int i = 0; i < Integer.parseInt(rateTableCount); i++)
        {
            String rateTablePK = initParam(appReqBlock.getReqParm("rateTablePK" + i), "0");

            String age = initParam(appReqBlock.getReqParm("age" + i), null);
            String duration = initParam(appReqBlock.getReqParm("duration" + i), null);
            String rate = initParam(appReqBlock.getReqParm("rate" + i), null);

            if (age == null || duration == null || rate == null)
            {
                continue;
            }

            RateTableVO rateTableVO = new RateTableVO();
            rateTableVO.setRateTablePK(Long.parseLong(rateTablePK));
            rateTableVO.setAge(Integer.parseInt(age));
            rateTableVO.setDuration(Integer.parseInt(duration));
            rateTableVO.setRate(new EDITBigDecimal(rate).getBigDecimal());

            rateTableVOs.add(rateTableVO);
        }

        Calculator calcComponent = new CalculatorComponent();
        calcComponent.saveTable(tableDefVO, tableKeysVO, (RateTableVO[]) (rateTableVOs.toArray(new RateTableVO[rateTableVOs.size()])));

        String message = "Table Successfully Saved";
        appReqBlock.getHttpServletRequest().setAttribute("message", message);

        return showTableDefault(appReqBlock);
    }

    private String showFilteredTransactionSummary(AppReqBlock appReqBlock)
    {
        TransactionPriorityVO[] transactionPriorityVOs = null;

        try
        {
            transactionPriorityVOs = DAOFactory.getTransactionPriorityDAO().findAll();
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        appReqBlock.getHttpServletRequest().setAttribute("transactionPriorityVOs", transactionPriorityVOs);

        return FILTERED_TRANSACTION_SUMMARY;
    }

    private String showFilteredTransactionCorrespondenceDialog(AppReqBlock appReqBlock)
    {
        TransactionCorrespondenceVO[] transactionCorrespondenceVOs = null;

        try
        {
            transactionCorrespondenceVOs = DAOFactory.getTransactionCorrespondenceDAO().findAll();
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        appReqBlock.getHttpServletRequest().setAttribute("transactionCorrespondenceVOs", transactionCorrespondenceVOs);

        return FILTERED_TRANSACTION_CORRESPONDENCE_DIALOG;
    }

//    private String cancelCorrespondenceDialog(AppReqBlock appReqBlock)
//    {
//        return ;
//    }

    protected String showAssociatedRulesForTable(AppReqBlock appReqBlock) throws Exception
    {
        String tableDefPK = initParam(appReqBlock.getReqParm("tableDefPK"), "0");
        String tableKeysPK = initParam(appReqBlock.getReqParm("tableKeysPK"), "0");

        Lookup calcLookup = new LookupComponent();

        TableDefVO[] tableDefVOs = calcLookup.findTableDefVOByPK(Long.parseLong(tableDefPK), false, null);
        TableKeysVO[] tableKeysVOs = calcLookup.findTableKeysVOByPK(Long.parseLong(tableKeysPK), false, null);
        tableDefVOs[0].setTableKeysVO(tableKeysVOs);

        RulesVO[] rulesVOs = calcLookup.findRulesVOsByTableDefPK(Long.parseLong(tableDefPK), false, null);

        UIRulesVO[] uiRulesVOs = new ProductStructureTran().buildUIRulesVOs(rulesVOs);

        appReqBlock.getHttpServletRequest().setAttribute("uiRulesVOs", uiRulesVOs);
        appReqBlock.getHttpServletRequest().setAttribute("tableDefVOs", tableDefVOs);
        appReqBlock.getHttpServletRequest().setAttribute("tableKeysVOs", tableKeysVOs);

        return "/engine/jsp/associatedRulesForTableDialog.jsp";
    }

    protected String cancelTableEdits(AppReqBlock appReqBlock) throws Exception
    {
        return this.showTableRates(appReqBlock);
    }

    protected String addTable(AppReqBlock appReqBlock) throws Exception
    {
        return showTableDefault(appReqBlock);
    }

    protected String showTableRates(AppReqBlock appReqBlock) throws Exception
    {
        String tableKeysPK = initParam(appReqBlock.getReqParm("tableKeysPK"), "0");
        String tableDefPK = initParam(appReqBlock.getReqParm("tableDefPK"), "0");

        if (!tableKeysPK.equals("0") && !tableDefPK.equals("0"))
        {
            Lookup calcLookup = new LookupComponent();

            TableDefVO tableDefVO = calcLookup.findTableDefVOByPK(Long.parseLong(tableDefPK), false, null)[0];
            TableKeysVO tableKeysVO = calcLookup.findTableKeysVOByPK(Long.parseLong(tableKeysPK), false, null)[0];
            RateTableVO[] rateTableVOs = calcLookup.findRateTableVOsByTableKeysPK(Long.parseLong(tableKeysPK), false, null);

            appReqBlock.getHttpServletRequest().setAttribute("tableDefVO", tableDefVO);
            appReqBlock.getHttpServletRequest().setAttribute("tableKeysVO", tableKeysVO);
            appReqBlock.getHttpServletRequest().setAttribute("rateTableVOs", rateTableVOs);
        }
        appReqBlock.getHttpServletRequest().setAttribute("tableKeysPK", tableKeysPK);
        appReqBlock.getHttpServletRequest().setAttribute("tableDefPK", tableDefPK);

        return showTableDefault(appReqBlock);
    }

    private void doTableEdits(AppReqBlock appReqBlock) throws Exception
    {

        String tname = appReqBlock.getReqParm(TABLE_NAME);
        if ((tname == null) || ((tname.trim()).length() == 0))
        {
            throw new Exception("Table Name needs to be entered");
        }
    }

    // Returns the selected Row information in tableBean
    // and RateTable Rows
//    protected void getRateTable(AppReqBlock aAppReqBlock
//                                , int newTableKeyId) throws Exception
//    {
//        String tid = aAppReqBlock.getReqParm(TABLE_ID);
//        int tableId = Integer.parseInt(tid.trim());
//        //String tkid = aAppReqBlock.getReqParm(TABLE_KEY_ID);
//        //int tableKeyId = Integer.parseInt(tkid.trim());
//        PageBean pageBean = null;
//
//        pageBean.putValue("tableId", tableId + "");
//        pageBean.putValue("tableName", aAppReqBlock.getReqParm(TABLE_NAME));
//        pageBean.putValue("accessType", aAppReqBlock.getReqParm(ACCESS_TYPE));
//        pageBean.putValue("userKey", aAppReqBlock.getReqParm(USER_KEY));
//        pageBean.putValue("sex", aAppReqBlock.getReqParm(SEX));
//        pageBean.putValue("classType", aAppReqBlock.getReqParm(CLASS_TYPE));
//        pageBean.putValue("bandAmount", Double.parseDouble(aAppReqBlock.getReqParm(BAND_AMOUNT)) + "");
//        pageBean.putValue("area", aAppReqBlock.getReqParm("area"));
//        pageBean.putValue("type", aAppReqBlock.getReqParm("type"));
//    }

    protected void showTableKeyCols(AppReqBlock aAppReqBlock) throws Exception
    {
        PageBean pageBean = new PageBean();

        SessionBean tableBean = aAppReqBlock.getSessionBean("tableBean");

        // TableDef
        String tableId = tableBean.getValue("tableId");
        //String tableKeyId = tableBean.getValue("tableKeyPK");

        //Iterator tableKeys = null;

        if ((tableId != null) && (tableId.length() > 0))
        {

            //Lookup lookup = (Lookup) aAppReqBlock.getLocalBusinessService("lookupComponent");
            Lookup lookup = (engine.business.Lookup) aAppReqBlock.getWebService("engine-lookup");
            CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

            TableKeysVO[] tableKeyVOs = lookup.getAllTableKeysById(Long.parseLong(tableId.trim()));

            //tableKeys = ed.getTableKeys(tableId);
            List vId = new ArrayList();
            List vDate = new ArrayList();
            List vSex = new ArrayList();
            List vClass = new ArrayList();
            List vBand = new ArrayList();
            List vArea = new ArrayList();
            List vTable = new ArrayList();
            List vUser = new ArrayList();
            long codeTableId = 0;

            if (tableKeyVOs != null)
            {
                for (int i = 0; i < tableKeyVOs.length; ++i)
                {
                    vId.add(new Long(tableKeyVOs[i].getTableKeysPK()));
                    vDate.add(tableKeyVOs[i].getEffectiveDate());

                    String gender = (tableKeyVOs[i].getGender());
                    if (gender == null || gender.equals(""))
                    {
                        vSex.add("-");
                    }
                    else
                    {
                        vSex.add(gender);
                    }

                    String classType = tableKeyVOs[i].getClassType();
                    if (classType == null || classType.equals(""))
                    {
                        vClass.add("-");
                    }
                    else
                    {
                        vClass.add(classType);
                    }
//                    vBand.add(new Double(tableKeyVOs[i].getBandAmount()));
                    vBand.add(tableKeyVOs[i].getBandAmount());

                    String state = tableKeyVOs[i].getState();
                    if (state == null || state.equals(""))
                    {
                        vArea.add("-");
                    }
                    else
                    {
                        vArea.add(state);
                    }

                    vTable.add(tableKeyVOs[i].getTableType());
                    vUser.add(tableKeyVOs[i].getUserKey());
                }
            }

            pageBean.putValues("tableKeyIds",
                    (Long[]) vId.toArray(new Long[vId.size()]),
                    new String[]{"toString"},
                    null);

            pageBean.putValues("effectiveDates",
                    (String[]) vDate.toArray(new String[vDate.size()]),
                    new String[]{"toString"},
                    null);

            pageBean.putValues("genders",
                    (String[]) vSex.toArray(new String[vSex.size()]),
                    new String[]{"toString"},
                    null);

            pageBean.putValues("classTypes",
                    (String[]) vClass.toArray(new String[vClass.size()]),
                    new String[]{"toString"},
                    null);

            pageBean.putValues("bandAmounts",
//                    (Double[]) vBand.toArray(new Double[vBand.size()]),
                    (String[]) vBand.toArray(new String[vBand.size()]),
                    new String[]{"toString"},
                    null);

            pageBean.putValues("areas",
                    (String[]) vArea.toArray(new String[vArea.size()]),
                    new String[]{"toString"},
                    null);

            pageBean.putValues("tableTypes",
                    (String[]) vTable.toArray(new String[vTable.size()]),
                    new String[]{"toString"},
                    null);

            pageBean.putValues("userKeys",
                    (String[]) vUser.toArray(new String[vUser.size()]),
                    new String[]{"toString"},
                    null);
        }

        aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);
    }

    protected String showTableDefault(AppReqBlock appReqBlock) throws Exception
    {

        Lookup calcLookup = new LookupComponent();

        List voExclusionList = new ArrayList();
        voExclusionList.add(RateTableVO.class);
        voExclusionList.add(RulesVO.class);
        TableDefVO[] tableDefVOs = calcLookup.findAllTableDefVOs(true, voExclusionList);

        long[] attachedPKs = calcLookup.findAttachedTableDefPKs();

        List attachedTableDefPKs = new ArrayList();

        if (attachedPKs != null)
        {
            for (int i = 0; i < attachedPKs.length; i++)
            {
                attachedTableDefPKs.add(new Long(attachedPKs[i]));
            }
        }

        appReqBlock.getHttpServletRequest().setAttribute("tableDefVOs", tableDefVOs);
        appReqBlock.getHttpServletRequest().setAttribute("attachedTableDefPKs", attachedTableDefPKs);

        return "/engine/jsp/tables.jsp";
    }

    protected void showTableSelectionDialog(AppReqBlock aAppReqBlock) throws Exception
    {

        PageBean pageBean = new PageBean();

        //Lookup lookup = (Lookup) aAppReqBlock.getLocalBusinessService("lookupComponent");
        Lookup lookup = (engine.business.Lookup) aAppReqBlock.getWebService("engine-lookup");

        TableDefVO[] tableNamesList = lookup.getAllTableNames();
        List tableNames = new ArrayList();
        List tableIds = new ArrayList();

        if (tableNamesList != null)
        {
            for (int i = 0; i < tableNamesList.length; ++i)
            {
                tableNames.add(tableNamesList[i].getTableName());
                tableIds.add(new Long(tableNamesList[i].getTableDefPK()));
            }
        }

        String[] tableNames2 = (String[]) tableNames.toArray(new String[tableNames.size()]);

        Long[] tableIds2 = (Long[]) tableIds.toArray(new Long[tableIds.size()]);

        pageBean.putValues("tableNames",
                tableNames2,
                new String[]{"toString"},
                null);

        pageBean.putValues("tableIds",
                tableIds2,
                new String[]{"toString"},
                null);

        aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

    }

    protected List getRateData(AppReqBlock aAppReqBlock) throws SMException, Exception
    {
        int numberOfRows = 0;
        List rateData = new ArrayList();
//looping for 100 rows bec in tabcols.jsp,there are 100 rows
        while (numberOfRows < 100)
        {

            String ageParameter =
                    aAppReqBlock.getReqParm("age" + numberOfRows);
            String durationParameter =
                    aAppReqBlock.getReqParm("duration" + numberOfRows);
            String rateParameter =
                    aAppReqBlock.getReqParm("rate" + numberOfRows);
            if (((ageParameter.length()) != 0) &&
                    ((durationParameter.length()) != 0) &&
                    ((rateParameter.length()) != 0))
            {
                Map data = new HashMap();
                data.put("age", ageParameter);
                data.put("duration", durationParameter);
                data.put("rate", rateParameter);
                rateData.add(data);
            }
            ++numberOfRows;
        }
        return rateData;
    }

    protected String deleteTable(AppReqBlock appReqBlock) throws Exception
    {

        String tableDefPK = initParam(appReqBlock.getReqParm("tableDefPK"), "0");
        String tableKeysPK = initParam(appReqBlock.getReqParm("tableKeysPK"), "0");

        if (tableDefPK.equals("0"))
        {
            String message = "Table Selection Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showTableDefault(appReqBlock);
        }
        else
        {
            Calculator calcComponent = new CalculatorComponent();

            try
            {
                calcComponent.deleteTable(Long.parseLong(tableDefPK), Long.parseLong(tableKeysPK));

                String message = "Table Successfully Deleted";

                appReqBlock.getHttpServletRequest().setAttribute("message", message);

                return showTableDefault(appReqBlock);
            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                String message = e.getMessage();

                appReqBlock.getHttpServletRequest().setAttribute("message", message);
                appReqBlock.getHttpServletRequest().setAttribute("tableDefPK", tableDefPK);
                appReqBlock.getHttpServletRequest().setAttribute("tableKeysPK", tableKeysPK);

                return showTableRates(appReqBlock);
            }
        }
    }

    protected String showUnitValueTable(AppReqBlock appReqBlock) throws Exception
    {

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        SessionBean fundSummaries = appReqBlock.getSessionBean("fundSummaries");
        PageBean fundDescriptions = null;

        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        String key = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);

        FundVO[] fundVOs = lookup.composeAllVariableFundVOs(voInclusionList);

        if (fundVOs != null)
        {

            for (int i = 0; i < fundVOs.length; i++)
            {

                String fundId = fundVOs[i].getFundPK() + "";
                String fundName = fundVOs[i].getName();
                FilteredFundVO[] filteredFundVOs = fundVOs[i].getFilteredFundVO();

                for (int j = 0; j < filteredFundVOs.length; j++)
                {
                    String fundNumber = filteredFundVOs[j].getFundNumber();
                    String filteredFundId = filteredFundVOs[j].getFilteredFundPK() + "";
                    fundDescriptions = new PageBean();

                    fundDescriptions.putValue("fundId", fundId);
                    fundDescriptions.putValue("fundNumber", fundNumber);
                    fundDescriptions.putValue("fundName", fundName);


                    key = fundId + "_" + filteredFundId + "+";

                    fundDescriptions.putValue("key", key);

                    fundSummaries.putPageBean(key, fundDescriptions);
                }
            }
        }
        return UNIT_VALUE_TABLE;
    }


    /**
     * For the filtered fund, get the array of charge codes that applies to them
     * and also the array of their FKs and return the two String arrays
     * bundled inside of an Object array.
     * @param filteredFundPK
     * @return Object[] with first the array of chargecode numbers and the second the array of FKs
     */
    private Object[] getChargeCodesAndKeysForFilteredFund(long filteredFundPK)
    {

        ChargeCode[] chargeCodes = ChargeCode.findByFilteredFundPK(filteredFundPK);

        if (chargeCodes == null)
        {
            chargeCodes = new ChargeCode[0];
        }
    
        String[] chargeCodeNumbers = new String[chargeCodes.length];

        String[] chargeCodeFKs = new String[chargeCodes.length];

        for (int i = 0; i < chargeCodes.length; i++)
        {
            ChargeCode chargeCode = chargeCodes[i];
            ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
            String chargeCodeForFFund = chargeCodeVO.getChargeCode();
            long chargeCodePK = chargeCodeVO.getChargeCodePK();

            chargeCodeNumbers[i] = chargeCodeForFFund;
            chargeCodeFKs[i] = String.valueOf(chargeCodePK);
        }

        return new Object[] {chargeCodeNumbers, chargeCodeFKs};

    }

    protected String showUnitValueDetailSummary(AppReqBlock appReqBlock) throws Exception
    {

        String key = appReqBlock.getFormBean().getValue("key");

        PageBean formBean = new PageBean();

        PageBean fundDescription = appReqBlock.getSessionBean("fundSummaries").getPageBean(key);

        String fundNumber = fundDescription.getValue("fundNumber");
        String fundName = fundDescription.getValue("fundName");

        String filteredFundId = key.substring(key.indexOf("_") + 1, key.indexOf("+"));

        formBean.putValue("fundNumber", fundNumber);
        formBean.putValue("fundName", fundName);
        formBean.putValue("key", key);

        long filteredFundPK = Long.parseLong(filteredFundId);
        Object[] arrays = getChargeCodesAndKeysForFilteredFund(filteredFundPK);
        String[] chargeCodeNumbers = (String[]) arrays[0];
        String[] chargeCodeFKs = (String[]) arrays[1];

        formBean.putValues("chargeCodes", chargeCodeNumbers);
        formBean.putValues("chargeCodeFKs", chargeCodeFKs);

        appReqBlock.getSessionBean("unitValueSessionBean").putPageBean("formBean", formBean);

        SessionBean unitValues = appReqBlock.getSessionBean("unitValues");

        unitValues.clearState();

        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        UnitValuesVO[] unitValuesVOs = lookup.getUnitValuesByFilteredFundId(Long.parseLong(filteredFundId));

        TreeMap sortedUnitValues = new TreeMap();

        if (unitValuesVOs != null)
        {

            for (int i = 0; i < unitValuesVOs.length; i++)
            {

                UnitValuesVO unitValueVO = unitValuesVOs[i];

                String unitValuesPK = unitValueVO.getUnitValuesPK() + "";

                sortedUnitValues.put(unitValueVO.getEffectiveDate() + unitValuesPK, unitValueVO);
            }

            boolean firstUV = true;

            Iterator it2 = sortedUnitValues.values().iterator();

            while (it2.hasNext())
            {

                UnitValuesVO unitValueVO = (UnitValuesVO) it2.next();

                String unitValueId = unitValueVO.getUnitValuesPK() + "";
                String unitValue = (new EDITBigDecimal(unitValueVO.getUnitValue())).toString();
                EDITDate effectiveDate = new EDITDate(unitValueVO.getEffectiveDate());
                String annuityUnitValue = (new EDITBigDecimal(unitValueVO.getAnnuityUnitValue())).toString();
                String updateStatus = unitValueVO.getUpdateStatus();
                String netAssetValue1 = Util.initString(new EDITBigDecimal(unitValueVO.getNetAssetValue1()).toString(), "0");
                String netAssetValue2 = Util.initString(new EDITBigDecimal(unitValueVO.getNetAssetValue2()).toString(), "0");
                String nav1Assets = Util.initString(new EDITBigDecimal(unitValueVO.getNAV1Assets()).toString(), "0");
                String nav2Assets = Util.initString(new EDITBigDecimal(unitValueVO.getNAV2Assets()).toString(), "0");
                String uvalAssets = Util.initString(new EDITBigDecimal(unitValueVO.getUVALAssets()).toString(), "0");

                long chargeCodeFK = unitValueVO.getChargeCodeFK();
                String chargeCodeFKStr = Util.initString(String.valueOf(chargeCodeFK), "0");
                String chargeCodeNumber = "";
                if (chargeCodeFK != 0L)
                {
                    chargeCodeNumber = getChargeCodeNumber(chargeCodeFK);
                }

                PageBean units = new PageBean();

                if (firstUV)
                {

                    firstUV = false;
                    formBean.putValue("unitValueId", unitValueId);
                    formBean.putValue("unitValue", unitValue);
                    formBean.putValue("effectiveDate", effectiveDate.getFormattedDate());
                    formBean.putValue("effectiveYear", effectiveDate.getFormattedYear());
                    formBean.putValue("effectiveMonth", effectiveDate.getFormattedMonth());
                    formBean.putValue("effectiveDay", effectiveDate.getFormattedDay());
                    formBean.putValue("annuityUnitValue", annuityUnitValue);
                    formBean.putValue("updateStatus", updateStatus);
                    formBean.putValue("netAssetValue1", netAssetValue1);
                    formBean.putValue("netAssetValue2", netAssetValue2);
                    formBean.putValue("nav1Assets", nav1Assets);
                    formBean.putValue("nav2Assets", nav2Assets);
                    formBean.putValue("uvalAssets", uvalAssets);
                    formBean.putValue("chargeCodeFK", String.valueOf(chargeCodeFK));
                    formBean.putValue("chargeCode", chargeCodeNumber);

                }

                units.putValue("unitValueId", unitValueId);
                units.putValue("unitValue", unitValue);
                units.putValue("effectiveDate", effectiveDate.getFormattedDate());
                units.putValue("annuityUnitValue", annuityUnitValue);
                units.putValue("updateStatus", updateStatus);
                units.putValue("netAssetValue1", netAssetValue1);
                units.putValue("netAssetValue2", netAssetValue2);
                units.putValue("nav1Assets", nav1Assets);
                units.putValue("nav2Assets", nav2Assets);
                units.putValue("uvalAssets", uvalAssets);
                units.putValue("chargeCodeFK", String.valueOf(chargeCodeFK));
                units.putValue("chargeCode", chargeCodeNumber);

                unitValues.putPageBean(unitValueId, units);
            }
        }

        return UNIT_VALUE_TABLE;
    }

    protected String showUnitValues(AppReqBlock appReqBlock) throws Exception
    {

        PageBean formBean = appReqBlock.getFormBean();

        String unitValueId = formBean.getValue("unitValueId");
        String fundNumber = formBean.getValue("fundNumber");
        String fundName = formBean.getValue("fundName");

        PageBean unitValueDescription = appReqBlock.getSessionBean("unitValues").getPageBean(unitValueId);

        String unitValue = unitValueDescription.getValue("unitValue");
        String effectiveDate = unitValueDescription.getValue("effectiveDate");
        String annuityUnitValue = unitValueDescription.getValue("annuityUnitValue");
        String updateStatus = unitValueDescription.getValue("updateStatus");
        String netAssetValue1 = unitValueDescription.getValue("netAssetValue1");
        String netAssetValue2 = unitValueDescription.getValue("netAssetValue2");
        String nav1Assets = unitValueDescription.getValue("nav1Assets");
        String nav2Assets = unitValueDescription.getValue("nav2Assets");
        String uvalAssets = unitValueDescription.getValue("uvalAssets");
        String chargeCodeFK = unitValueDescription.getValue("chargeCodeFK");
        String chargeCode = unitValueDescription.getValue("chargeCode");

        long filteredFundPK = FilteredFund.findByFundNumber(fundNumber).getPK();
        Object[] arrays = getChargeCodesAndKeysForFilteredFund(filteredFundPK);
        String[] chargeCodeNumbers = (String[]) arrays[0];
        String[] chargeCodeFKs = (String[]) arrays[1];

        String effectiveMonth = null;
        String effectiveDay = null;
        String effectiveYear = null;

        if (!effectiveDate.equals("") && effectiveDate != null)
        {

            EDITDate editEffectiveDate = new EDITDate(effectiveDate);

            effectiveMonth = editEffectiveDate.getFormattedMonth();
            effectiveDay = editEffectiveDate.getFormattedDay();
            effectiveYear = editEffectiveDate.getFormattedYear();
        }

        formBean.putValue("fundNumber", fundNumber);
        formBean.putValue("fundName", fundName);
        formBean.putValue("unitValue", unitValue);
        formBean.putValue("annuityUnitValue", annuityUnitValue);
        formBean.putValue("effectiveMonth", effectiveMonth);
        formBean.putValue("effectiveDay", effectiveDay);
        formBean.putValue("effectiveYear", effectiveYear);
        formBean.putValue("unitValueId", unitValueId);
        formBean.putValue("updateStatus", updateStatus);
        formBean.putValue("netAssetValue1", netAssetValue1);
        formBean.putValue("netAssetValue2", netAssetValue2);
        formBean.putValue("nav1Assets", nav1Assets);
        formBean.putValue("nav2Assets", nav2Assets);
        formBean.putValue("uvalAssets", uvalAssets);
        formBean.putValue("chargeCode", chargeCode);
        formBean.putValue("chargeCodeFK", chargeCodeFK);

        // allowable chargecodes and their FKs for this
        formBean.putValues("chargeCodes", chargeCodeNumbers);
        formBean.putValues("chargeCodeFKs", chargeCodeFKs);

        appReqBlock.getSessionBean("unitValueSessionBean").putPageBean("formBean", formBean);

        return UNIT_VALUE_TABLE;
    }

    protected String updateUnitValues(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        SessionBean unitValueSessionBean = appReqBlock.getSessionBean("unitValueSessionBean");

        String unitValueId = formBean.getValue("unitValueId");
        if (unitValueId.equals("") || unitValueId.equals("-1"))
        {
            unitValueId = "0";
        }
        //get the rest of values
        String unitValue = Util.initString(formBean.getValue("unitValue"), "0");
        String annuityUnitValue = Util.initString(formBean.getValue("annuityUnitValue"), "0");

        String effectiveYear = formBean.getValue("effectiveYear");
        String effectiveMonth = formBean.getValue("effectiveMonth");
        String effectiveDay = formBean.getValue("effectiveDay");
        EDITDate effectiveDate = null;
        String netAssetValue1 = Util.initString(formBean.getValue("netAssetValue1"), "0");
        String netAssetValue2 = Util.initString(formBean.getValue("netAssetValue2"), "0");

        String nav1Assets = Util.initString(formBean.getValue("nav1Assets"), "0");
        String nav2Assets = Util.initString(formBean.getValue("nav2Assets"), "0");
        String uvalAssets = Util.initString(formBean.getValue("uvalAssets"), "0");

        if (!effectiveYear.equals("") && (!effectiveMonth.equals("")) && (!effectiveDay.equals("")))
        {
            effectiveDate = new EDITDate(effectiveYear, effectiveMonth, effectiveDay);
        }

        String updateStatus = Util.initString(formBean.getValue("updateStatus"), "");
        String key = formBean.getValue("key");

        String filteredFundId = key.substring(key.indexOf("_") + 1, key.indexOf("+"));
        long filteredFundPK = Long.parseLong(filteredFundId);

        String chargeCodeFKStr = formBean.getValue("chargeCodeFK");
        long chargeCodeFK = 0;
        if (Util.isANumber(chargeCodeFKStr))
        {
           chargeCodeFK = Long.parseLong(chargeCodeFKStr);
        }

        UnitValuesVO unitValueVO = new UnitValuesVO();
        unitValueVO.setUnitValuesPK(Long.parseLong(unitValueId));
        unitValueVO.setUnitValue(new EDITBigDecimal(unitValue).getBigDecimal());

        if (effectiveDate != null)
        {
            unitValueVO.setEffectiveDate(effectiveDate.getFormattedDate());
        }

        unitValueVO.setFilteredFundFK(Long.parseLong(filteredFundId));

        unitValueVO.setAnnuityUnitValue(new EDITBigDecimal(annuityUnitValue).getBigDecimal());
        unitValueVO.setUpdateStatus(updateStatus);
        unitValueVO.setNetAssetValue1(new EDITBigDecimal(netAssetValue1).getBigDecimal());
        unitValueVO.setNetAssetValue2(new EDITBigDecimal(netAssetValue2).getBigDecimal());
        unitValueVO.setNAV1Assets(new EDITBigDecimal(nav1Assets).getBigDecimal());
        unitValueVO.setNAV2Assets(new EDITBigDecimal(nav2Assets).getBigDecimal());
        unitValueVO.setUVALAssets(new EDITBigDecimal(uvalAssets).getBigDecimal());

        if (chargeCodeFK != 0L)
        {
            unitValueVO.setChargeCodeFK(chargeCodeFK);
        }

        SessionBean unitValues = appReqBlock.getSessionBean("unitValues");
//        validateVO(unitValueVO, appReqBlock);

        // save it to the data base
        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        String verifyPriorOrCurrenMonthUnitValueExistence = formBean.getValue("verifyPriorOrCurrenMonthUnitValueExistence");

        boolean hasPriorOrCurrentMonthUnitValue = true;

        if ("true".equals(verifyPriorOrCurrenMonthUnitValueExistence))
        {
            hasPriorOrCurrentMonthUnitValue = calculatorComponent.hasFilteredFundHasUnitValueForPriorOrCurrentMonth
                    (new Long(filteredFundPK), new Long(chargeCodeFK), new EDITDate(effectiveDate));
        }

        // This will be verified for only hedge funds.
        if (hasPriorOrCurrentMonthUnitValue)
        {
            long newUnitValueId = calculatorComponent.createOrUpdateVONonRecursive(unitValueVO);

            //load formbean w/ new id
            formBean.putValue("unitValueId", newUnitValueId + "");

            formBean.putValue("hasPriorOrCurrentMonthUnitValue", "true");

            unitValues = appReqBlock.getSessionBean("unitValues");

            unitValues.clearState();

            Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

            UnitValuesVO[] unitValuesVOs = lookup.getUnitValuesByFilteredFundId(Long.parseLong(filteredFundId));

            if (unitValuesVOs != null && unitValuesVOs.length != 0)
            {
                for (int i = 0; i < unitValuesVOs.length; i++)
                {
                    unitValueId = unitValuesVOs[i].getUnitValuesPK() + "";
                    unitValue = unitValuesVOs[i].getUnitValue() + "";
                    effectiveDate = new EDITDate(unitValuesVOs[i].getEffectiveDate());
                    annuityUnitValue = unitValuesVOs[i].getAnnuityUnitValue() + "";
                    updateStatus = unitValuesVOs[i].getUpdateStatus();
                    netAssetValue1 = unitValuesVOs[i].getNetAssetValue1() + "";
                    netAssetValue2 = unitValuesVOs[i].getNetAssetValue2() + "";
                    nav1Assets = unitValuesVOs[i].getNAV1Assets() + "";
                    nav2Assets = unitValuesVOs[i].getNAV2Assets() + "";
                    uvalAssets = unitValuesVOs[i].getUVALAssets() + "";
                    chargeCodeFK = unitValuesVOs[i].getChargeCodeFK();

                    PageBean units = new PageBean();

                    units.putValue("unitValueId", unitValueId);
                    units.putValue("unitValue", unitValue);
                    units.putValue("effectiveDate", effectiveDate.getFormattedDate());
                    units.putValue("annuityUnitValue", annuityUnitValue);
                    units.putValue("updateStatus", updateStatus);
                    units.putValue("netAssetValue1", netAssetValue1);
                    units.putValue("netAssetValue2", netAssetValue2);
                    units.putValue("nav1Assets", nav1Assets);
                    units.putValue("nav2Assets", nav2Assets);
                    units.putValue("uvalAssets", uvalAssets);
                    units.putValue("chargeCodeFK", String.valueOf(chargeCodeFK));
                    String chargeCode = getChargeCodeNumber(chargeCodeFK);
                    units.putValue("chargeCode", chargeCode);

                    unitValues.putPageBean(unitValueId, units);
                }
            }
        }
        else
        {
            formBean.putValue("hasPriorOrCurrentMonthUnitValue", "false");
        }

        // re-load formbean values for select tag
        Object[] arrays = getChargeCodesAndKeysForFilteredFund(filteredFundPK);
        String[] chargeCodeNumbers = (String[]) arrays[0];
        String[] chargeCodeFKs = (String[]) arrays[1];

        formBean.putValues("chargeCodes", chargeCodeNumbers);
        formBean.putValues("chargeCodeFKs", chargeCodeFKs);

        unitValueSessionBean.putPageBean("formBean", formBean);

        return UNIT_VALUE_TABLE;
    }

    private String getChargeCodeNumber(long chargeCodePK)
    {
        if (chargeCodePK == 0L)
        {
            return null;
        }
        ChargeCode chargeCode = ChargeCode.findByPK(chargeCodePK);
        ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
        return chargeCodeVO.getChargeCode();
    }


    private long getChargeCodeFK(String chargeCodeNum, long filteredFundId)
    {
        ChargeCode chargeCode = ChargeCode.findByFilteredFundPK(filteredFundId, chargeCodeNum);
        if (chargeCode == null) {
            return 0L;
        }
        return chargeCode.getPK();
    }

    protected String deleteUnitValues(AppReqBlock appReqBlock) throws Exception
    {

        PageBean formBean = appReqBlock.getFormBean();
        SessionBean unitValueSessionBean = appReqBlock.getSessionBean("unitValueSessionBean");

        String unitValueId = formBean.getValue("unitValueId");

        String unitValue = formBean.getValue("unitValue");
        String annuityUnitValue = formBean.getValue("annuiyUnitValue");
        String netAssetValue1 = formBean.getValue("netAssetValue1");
        String netAssetValue2 = formBean.getValue("netAssetValue2");

        EDITDate effectiveDate = new EDITDate(formBean.getValue("effectiveYear"), formBean.getValue("effectiveMonth"), formBean.getValue("effectiveDay"));

        String key = formBean.getValue("key");
        String filteredFundId = key.substring(key.indexOf("_") + 1, key.indexOf("+"));

        long filteredFundPK = Long.parseLong(filteredFundId);
        Object[] arrays = getChargeCodesAndKeysForFilteredFund(filteredFundPK);
        String[] chargeCodeNumbers = (String[]) arrays[0];
        String[] chargeCodeFKs = (String[]) arrays[1];

        formBean.putValues("chargeCodes", chargeCodeNumbers);
        formBean.putValues("chargeCodeFKs", chargeCodeFKs);

// delete it fromo the data base
        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        calculatorComponent.deleteVO(UnitValuesVO.class, Long.parseLong(unitValueId), false);

        formBean.putValue("unitValueId", "-1");
        formBean.putValue("unitValue", "");
        formBean.putValue("effectiveYear", "");
        formBean.putValue("effectiveMonth", "");
        formBean.putValue("effectiveDay", "");
//        formBean.putValue("annuityUnitValue", annuityUnitValue);    // ?
//        formBean.putValue("netAssetValue1", netAssetValue1);        // ?
//        formBean.putValue("netAssetValue2", netAssetValue2);        // ?
        formBean.putValue("annuityUnitValue", "");
        formBean.putValue("netAssetValue1", "");
        formBean.putValue("netAssetValue2", "");
        formBean.putValue("chargeCodeFK", "");
        formBean.putValue("chargeCode", "");
        formBean.putValue("updateStatus", "");

        unitValueSessionBean.putPageBean("formBean", formBean);

        SessionBean unitValues = appReqBlock.getSessionBean("unitValues");

        unitValues.clearState();

        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        UnitValuesVO[] unitValuesVOs = lookup.getUnitValuesByFilteredFundId(Long.parseLong(filteredFundId));

        if (unitValuesVOs != null && unitValuesVOs.length != 0)
        {

            for (int i = 0; i < unitValuesVOs.length; i++)
            {

                unitValueId = unitValuesVOs[i].getUnitValuesPK() + "";
                unitValue = unitValuesVOs[i].getUnitValue() + "";
                effectiveDate = new EDITDate(unitValuesVOs[i].getEffectiveDate());
                annuityUnitValue = unitValuesVOs[i].getAnnuityUnitValue() + "";
                netAssetValue1 = unitValuesVOs[i].getNetAssetValue1() + "";
                netAssetValue2 = unitValuesVOs[i].getNetAssetValue2() + "";
                long chargeCodeFK = unitValuesVOs[i].getChargeCodeFK();

                String chargeCodeFKStr = Util.initString(String.valueOf(chargeCodeFK), "0");
                String chargeCodeNumber = "";
                if (chargeCodeFK != 0L)
                {
                    chargeCodeNumber = getChargeCodeNumber(chargeCodeFK);
                }

                PageBean units = new PageBean();

                units.putValue("unitValueId", unitValueId);
                units.putValue("unitValue", unitValue);
                units.putValue("effectiveDate", effectiveDate.getFormattedDate());
                units.putValue("annuityUnitValue", annuityUnitValue);
                units.putValue("netAssetValue1", netAssetValue1);
                units.putValue("netAssetValue2", netAssetValue2);
                units.putValue("chargeCodeFK", chargeCodeFK + "");
                units.putValue("chargeCode", chargeCodeNumber);

                unitValues.putPageBean(unitValueId, units);
            }

            appReqBlock.getSessionBean("unitValueSessionBean").putPageBean("formBean", formBean);

        }

        return UNIT_VALUE_TABLE;
    }

    protected String cancelUnitValues(AppReqBlock appReqBlock) throws Exception
    {
        PageBean formBean = appReqBlock.getFormBean();
        SessionBean unitValueSessionBean = appReqBlock.getSessionBean("unitValueSessionBean");

        String key = formBean.getValue("key");
        String filteredFundId = key.substring(key.indexOf("_") + 1, key.indexOf("+"));
        long filteredFundPK = Long.parseLong(filteredFundId);

        formBean.putValue("unitValueId", "-1");
        formBean.putValue("unitValue", "");
        formBean.putValue("effectiveYear", "");
        formBean.putValue("effectiveMonth", "");
        formBean.putValue("effectiveDay", "");
        formBean.putValue("annuityUnitValue", "");
        formBean.putValue("netAssetValue1", "");
        formBean.putValue("netAssetValue2", "");
        formBean.putValue("nav1Assets", "");
        formBean.putValue("nav2Assets", "");
        formBean.putValue("uvalAssets", "");
        formBean.putValue("chargeCodeFK", "");
        formBean.putValue("chargeCode", "");
        formBean.putValue("updateStatus", "");

        // re-load formbean values for select tag
        Object[] arrays = getChargeCodesAndKeysForFilteredFund(filteredFundPK);
        String[] chargeCodeNumbers = (String[]) arrays[0];
        String[] chargeCodeFKs = (String[]) arrays[1];

        formBean.putValues("chargeCodes", chargeCodeNumbers);
        formBean.putValues("chargeCodeFKs", chargeCodeFKs);

        unitValueSessionBean.putPageBean("formBean", formBean);

        //SessionBean unitValues = appReqBlock.getSessionBean("unitValues");

        return UNIT_VALUE_TABLE;
    }

    protected String showInterestRateTable(AppReqBlock appReqBlock) throws Exception
    {

        SessionBean fundInterestSummaries = appReqBlock.getSessionBean("fundInterestSummaries");
        fundInterestSummaries.clearState();

        PageBean interestRateDescriptions = null;
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        String key = null;
        String fundNumber = null;

        List voInclusionList = new ArrayList();
        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(InterestRateParametersVO.class);

        FundVO[] fundVOs = lookup.composeAllFixedFundVOs(voInclusionList);

        if (fundVOs != null)
        {

            for (int i = 0; i < fundVOs.length; i++)
            {

                FilteredFundVO[] filteredFundVOs = fundVOs[i].getFilteredFundVO();

                for (int j = 0; j < filteredFundVOs.length; j++)
                {
                    String fundId = fundVOs[i].getFundPK() + "";
                    String fundName = fundVOs[i].getName();
                    fundNumber = filteredFundVOs[j].getFundNumber();
                    String filteredFundId = filteredFundVOs[j].getFilteredFundPK() + "";
                    String interestRateParamId = "";

                    InterestRateParametersVO[] interestRateParamVO = filteredFundVOs[j].getInterestRateParametersVO();

                    if (interestRateParamVO.length > 0)
                    {
                        for (int k = 0; k < interestRateParamVO.length; k++)
                        {

                            interestRateDescriptions = new PageBean();

                            EDITDate originalDate = new EDITDate(interestRateParamVO[k].getOriginalDate());

                            String originalMonth = originalDate.getFormattedMonth();
                            String originalDay = originalDate.getFormattedDay();
                            String originalYear = originalDate.getFormattedYear();

                            String stopDate = interestRateParamVO[k].getStopDate();
                            String stopMonth = "";
                            String stopDay = "";
                            String stopYear = "";

                            if (stopDate != null && !stopDate.equals(""))
                            {
                                EDITDate editStopDate = new EDITDate(stopDate);
                                stopMonth = editStopDate.getFormattedMonth();
                                stopDay = editStopDate.getFormattedDay();
                                stopYear = editStopDate.getFormattedYear();
                            }

                            String optionCode = interestRateParamVO[k].getOptionCT();
                            interestRateParamId = interestRateParamVO[k].getInterestRateParametersPK() + "";
                            interestRateDescriptions.putValue("originalDate", originalDate.getFormattedDate());
                            interestRateDescriptions.putValue("originalMonth", originalMonth);
                            interestRateDescriptions.putValue("originalDay", originalDay);
                            interestRateDescriptions.putValue("originalYear", originalYear);
                            interestRateDescriptions.putValue("stopDate", stopDate);
                            interestRateDescriptions.putValue("stopMonth", stopMonth);
                            interestRateDescriptions.putValue("stopDay", stopDay);
                            interestRateDescriptions.putValue("stopYear", stopYear);
                            interestRateDescriptions.putValue("optionCode", optionCode);
                            interestRateDescriptions.putValue("interestRateParamId", interestRateParamId);
                            interestRateDescriptions.putValue("fundId", fundId);
                            interestRateDescriptions.putValue("fundNumber", fundNumber);
                            interestRateDescriptions.putValue("fundName", fundName);
                            interestRateDescriptions.putValue("filteredFundId", filteredFundId);

                            key = fundId + "_" + filteredFundId + "+" + interestRateParamId;
                            interestRateDescriptions.putValue("key", key);
                            fundInterestSummaries.putPageBean(key, interestRateDescriptions);
                        }
                    }
                    else
                    {
                        interestRateDescriptions = new PageBean();
                        interestRateDescriptions.putValue("fundId", fundId);
                        interestRateDescriptions.putValue("fundNumber", fundNumber);
                        interestRateDescriptions.putValue("fundName", fundName);
                        interestRateDescriptions.putValue("filteredFundId", filteredFundId);
                        key = fundId + "_" + filteredFundId + "+" + "0";
                        interestRateDescriptions.putValue("key", key);
                        fundInterestSummaries.putPageBean(key, interestRateDescriptions);
                    }
                }
            }
        }

//	    appReqBlock.getHttpServletRequest().getSession().setAttribute("fundNumbers", (String[])fundNumbers.toArray(new String[fundNumbers.size()]));

        return INTEREST_RATE_TABLE;
    }


    protected String showInterestRateDetailSummary(AppReqBlock appReqBlock) throws Exception
    {

        String key = appReqBlock.getFormBean().getValue("key");

        PageBean formBean = new PageBean();

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        PageBean interestRateDescription = appReqBlock.getSessionBean("fundInterestSummaries").getPageBean(key);

        String fundNumber = interestRateDescription.getValue("fundNumber");
        String fundName = interestRateDescription.getValue("fundName");
        String originalDate = interestRateDescription.getValue("originalDate");
        String stopDate = interestRateDescription.getValue("stopDate");
        String optionCode = interestRateDescription.getValue("optionCode");
        long codeTablePK = 0;

        CodeTableVO[] codeTableVOs = codeTableWrapper.getCodeTableEntries("INTERESTPARAMOPTION");
        for (int i = 0; i < codeTableVOs.length; i++)
        {
            if (optionCode.equalsIgnoreCase(codeTableVOs[i].getCodeDesc()))
            {
                codeTablePK = codeTableVOs[i].getCodeTablePK();
            }
        }
        optionCode = codeTablePK + "";

        String interestRateParamId = key.substring(key.indexOf("+") + 1, key.length());

        SessionBean interestRates = appReqBlock.getSessionBean("interestRates");

        interestRates.clearState();

        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        InterestRateVO[] interestRateVOs = null;
        if (!interestRateParamId.equals(""))
        {
            interestRateVOs = lookup.getInterestRateById(Long.parseLong(interestRateParamId));
        }


        if (interestRateVOs != null && interestRateVOs.length != 0)
        {

            for (int i = 0; i < interestRateVOs.length; i++)
            {

                String interestRateId = interestRateVOs[i].getInterestRatePK() + "";
                String guaranteeDuration = interestRateVOs[i].getGuaranteeDuration();
                String effectiveDate = interestRateVOs[i].getEffectiveDate();
                String interestRate = (new EDITBigDecimal(interestRateVOs[i].getRate())).toString();


                if (i == 0)
                {
                    formBean.putValue("interestRateId", interestRateId);
                    formBean.putValue("guaranteeDuration", guaranteeDuration);
                    formBean.putValue("interestRate", interestRate);
                    EDITDate editEffectiveDate = new EDITDate(effectiveDate);
                    formBean.putValue("effectiveMonth", editEffectiveDate.getFormattedMonth());
                    formBean.putValue("effectiveDay", editEffectiveDate.getFormattedDay());
                    formBean.putValue("effectiveYear", editEffectiveDate.getFormattedYear());
                    formBean.putValue("optionCode", optionCode);
                }

                PageBean rates = new PageBean();

                rates.putValue("interestRateId", interestRateId);
                rates.putValue("guaranteeDuration", guaranteeDuration);
                rates.putValue("effectiveDate", effectiveDate);
                rates.putValue("interestRate", interestRate);

                interestRates.putPageBean(interestRateId, rates);
            }
        }

        if (!interestRateParamId.equals(""))
        {

            formBean.putValue("originalDate", originalDate);
            if (!originalDate.equals(""))
            {
                EDITDate editOriginalDate = new EDITDate(originalDate);
                formBean.putValue("originalMonth", editOriginalDate.getFormattedMonth());
                formBean.putValue("originalDay", editOriginalDate.getFormattedDay());
                formBean.putValue("originalYear", editOriginalDate.getFormattedYear());
            }
            formBean.putValue("stopDate", stopDate);
            if (!stopDate.equals(""))
            {
                EDITDate editStopDate = new EDITDate(stopDate);
                formBean.putValue("stopMonth", editStopDate.getFormattedMonth());
                formBean.putValue("stopDay", editStopDate.getFormattedDay());
                formBean.putValue("stopYear", editStopDate.getFormattedYear());
            }
            formBean.putValue("optionCode", optionCode);
        }
        else
        {
            formBean.putValue("originalDate", "");
            formBean.putValue("originalMonth", "");
            formBean.putValue("originalDay", "");
            formBean.putValue("originalYear", "");
            formBean.putValue("stopDate", "");
            formBean.putValue("stopMonth", "");
            formBean.putValue("stopDay", "");
            formBean.putValue("stopYear", "");
            formBean.putValue("optionCode", "");
        }

        formBean.putValue("fundNumber", fundNumber);
        formBean.putValue("fundName", fundName);
        formBean.putValue("key", key);
        formBean.putValue("interestRateParamId", interestRateParamId);
        appReqBlock.getSessionBean("interestRateSessionBean").putPageBean("formBean", formBean);

        return INTEREST_RATE_TABLE;
    }

    protected String showInterestRates(AppReqBlock appReqBlock) throws Exception
    {

        PageBean formBean = appReqBlock.getFormBean();

        String interestRateId = formBean.getValue("interestRateId");

        PageBean interestRateDescription = appReqBlock.getSessionBean("interestRates").getPageBean(interestRateId);

        String interestRate = interestRateDescription.getValue("interestRate");
        String effectiveDate = interestRateDescription.getValue("effectiveDate");
        String guaranteeDuration = interestRateDescription.getValue("guaranteeDuration");

        //effectiveDate in yyyymmdd format
        EDITDate editEffectiveDate = new EDITDate(effectiveDate);
//        EDITDate editEffectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(effectiveDate);

        String effectiveMonth = editEffectiveDate.getFormattedMonth();
        String effectiveDay = editEffectiveDate.getFormattedDay();
        String effectiveYear = editEffectiveDate.getFormattedYear();

        formBean.putValue("interestRate", interestRate);
        formBean.putValue("guaranteeDuration", guaranteeDuration);
        formBean.putValue("effectiveMonth", effectiveMonth);
        formBean.putValue("effectiveDay", effectiveDay);
        formBean.putValue("effectiveYear", effectiveYear);
        formBean.putValue("interestRateId", interestRateId);

        appReqBlock.getSessionBean("interestRateSessionBean").putPageBean("formBean", formBean);

        return INTEREST_RATE_TABLE;
    }

    protected String updateInterestRates(AppReqBlock appReqBlock) throws Exception
    {

        PageBean formBean = appReqBlock.getFormBean();
        SessionBean interestRateSessionBean = appReqBlock.getSessionBean("interestRateSessionBean");

        String interestRateId = formBean.getValue("interestRateId");
        if (interestRateId.equals(""))
        {

            interestRateId = "0";
        }
//get the rest of values
        String interestRate = formBean.getValue("interestRate");
        String guaranteeDuration = formBean.getValue("guaranteeDuration");
        if (guaranteeDuration.equals(""))
        {
            guaranteeDuration = "0";
        }

        EDITDate effectiveDate = new EDITDate(formBean.getValue("effectiveYear"), formBean.getValue("effectiveMonth"), formBean.getValue("effectiveDay"));

//get the entered Interest RateParams
        String origYear = formBean.getValue("originalYear");
        String origMonth = formBean.getValue("originalMonth");
        String origDay = formBean.getValue("originalDay");
        String originalDateIn = DateTimeUtil.initDate(origMonth, origDay, origYear, null);

        String stopYear = formBean.getValue("stopYear");
        String stopMonth = formBean.getValue("stopMonth");
        String stopDay = formBean.getValue("stopDay");

        String stopDateIn = DateTimeUtil.initDate(stopMonth, stopDay, stopYear, EDITDate.DEFAULT_MAX_DATE);

        String optionIn = formBean.getValue("optionCode");
        if (!optionIn.equals(""))
        {
            CodeTableVO codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionIn));
            optionIn = codeTableVO.getCodeDesc();
        }

// get original interest rate param fields
        String key = appReqBlock.getFormBean().getValue("key");
        String filteredFundId = key.substring(key.indexOf("_") + 1, key.indexOf("+"));
        String interestRateParamId = key.substring(key.indexOf("+") + 1, key.length());
        String originalDateEx = "";
        String stopDateEx = "";
        String optionEx = "";

//        if (interestRateId == "0")
//        {
        PageBean interestRateDescription = appReqBlock.getSessionBean("fundInterestSummaries").getPageBean(key);
        originalDateEx = interestRateDescription.getValue("originalDate");
        stopDateEx = interestRateDescription.getValue("stopDate");
        optionEx = interestRateDescription.getValue("optionCode");
//            if (!optionEx.equals(""))
//            {
//                CodeTableVO codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(optionEx));
//                optionEx = codeTableVO.getCodeDesc();
//            }
//        }

        InterestRateVO interestRateVO = new InterestRateVO();
        interestRateVO.setInterestRatePK(Long.parseLong(interestRateId));
        interestRateVO.setRate(new EDITBigDecimal(interestRate).getBigDecimal());
        interestRateVO.setEffectiveDate(effectiveDate.getFormattedDate());
        interestRateVO.setGuaranteeDuration(guaranteeDuration);



// save it to the data base
        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        InterestRateParametersVO interestRateParamVO = null;
        if (originalDateIn.equals(originalDateEx) && stopDateIn.equals(stopDateEx) && optionIn.equals(optionEx))
        {

            interestRateVO.setInterestRateParametersFK(Long.parseLong(interestRateParamId));
            long newInterestRateId = calculatorComponent.createOrUpdateVONonRecursive(interestRateVO);
            formBean.putValue("interestRateId", (newInterestRateId + ""));
            formBean.putValue("optionCode", optionIn);

        }
        else
        {
            interestRateParamVO = new InterestRateParametersVO();
            interestRateParamVO.setFilteredFundFK(Long.parseLong(filteredFundId));
            interestRateParamVO.setInterestRateParametersPK(0);
            interestRateParamVO.setOptionCT(optionIn);
            interestRateParamVO.setOriginalDate(originalDateIn);
            interestRateParamVO.setStopDate(stopDateIn);
            long newInterestRateParamId = calculatorComponent.createOrUpdateVONonRecursive(interestRateParamVO);

            formBean.putValue("interestRateParamId", (newInterestRateParamId + ""));

            interestRateVO.setInterestRateParametersFK(newInterestRateParamId);
            long newInterestRateId = calculatorComponent.createOrUpdateVONonRecursive(interestRateVO);
        }

        SessionBean interestRates = appReqBlock.getSessionBean("interestRates");
        interestRates.clearState();

        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        InterestRateVO[] interestRateVOs = lookup.getInterestRateById(Long.parseLong(interestRateParamId));

        if (interestRateVOs != null && interestRateVOs.length != 0)
        {

            for (int i = 0; i < interestRateVOs.length; i++)
            {

                interestRateId = interestRateVOs[i].getInterestRatePK() + "";
                interestRate = interestRateVOs[i].getRate() + "";
                effectiveDate = new EDITDate(interestRateVOs[i].getEffectiveDate());
                guaranteeDuration = interestRateVOs[i].getGuaranteeDuration() + "";

                PageBean rates = new PageBean();

                rates.putValue("interestRateId", interestRateId);
                rates.putValue("interestRate", interestRate);
                rates.putValue("effectiveDate", effectiveDate.getFormattedDate());
                rates.putValue("guaranteeDuration", guaranteeDuration);
                interestRates.putPageBean(interestRateId, rates);
            }
        }
        showInterestRateTable(appReqBlock);

        return INTEREST_RATE_TABLE;

    }

//    protected String setupInterestRateParamSummary() throws Exception
//    {
//
//    }


    protected String deleteInterestRates(AppReqBlock appReqBlock) throws Exception
    {

        PageBean formBean = appReqBlock.getFormBean();
        SessionBean interestRateSessionBean = appReqBlock.getSessionBean("interestRateSessionBean");

        String interestRateId = Util.initString(formBean.getValue("interestRateId"), null);

        String interestRate = formBean.getValue("interestRate");
        String guaranteeDuration = formBean.getValue("guaranteeDuration");

        String effectiveYear = Util.initString(formBean.getValue("effectiveYear"), null);
        String effectiveMonth = Util.initString(formBean.getValue("effectiveMonth"), null);
        String effectiveDay = Util.initString(formBean.getValue("effectiveDay"), null);
        EDITDate effectiveDate = null;
        if (effectiveYear != null && effectiveMonth != null && effectiveDay != null)
        {
            effectiveDate = new EDITDate(effectiveYear, effectiveMonth, effectiveDay);
        }

        String key = formBean.getValue("key");
//String filteredFundId     = key.substring(key.indexOf("_")+1, key.indexOf("+"));
        String interestRateParamId = key.substring(key.indexOf("+") + 1, key.length());


// delete it from the data base
        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        if (interestRateId != null)
        {
        calculatorComponent.deleteVO(InterestRateVO.class, Long.parseLong(interestRateId), false);

        formBean.putValue("interestRateId", "");
        formBean.putValue("interestRate", "");
        formBean.putValue("effectiveYear", "");
        formBean.putValue("effectiveMonth", "");
        formBean.putValue("effectiveDay", "");
        formBean.putValue("guaranteeDuration", guaranteeDuration);
        }
        else
        {
            calculatorComponent.deleteVO(InterestRateParametersVO.class, Long.parseLong(interestRateParamId), false);
        }

        interestRateSessionBean.putPageBean("formBean", formBean);

        SessionBean interestRates = appReqBlock.getSessionBean("interestRates");

        interestRates.clearState();

        //Lookup lookup = (Lookup)appReqBlock.getLocalBusinessService("lookupComponent");
        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        InterestRateVO[] interestRateVOs = lookup.getInterestRateById(Long.parseLong(interestRateParamId));

        if (interestRateVOs != null && interestRateVOs.length != 0)
        {

            for (int i = 0; i < interestRateVOs.length; i++)
            {

                interestRateId = interestRateVOs[i].getInterestRatePK() + "";
                interestRate = interestRateVOs[i].getRate() + "";
                effectiveDate = new EDITDate(interestRateVOs[i].getEffectiveDate());
                guaranteeDuration = interestRateVOs[i].getGuaranteeDuration() + "";

                PageBean rates = new PageBean();

                rates.putValue("interestRateId", interestRateId);
                rates.putValue("interestRate", interestRate);
                rates.putValue("effectiveDate", effectiveDate.getFormattedDate());
                rates.putValue("guaranteeDuration", guaranteeDuration);
                interestRates.putPageBean(interestRateId, rates);
            }
        }
        else
        {

            calculatorComponent.deleteVO(InterestRateParametersVO.class, Long.parseLong(interestRateParamId), false);
            interestRateSessionBean.clearState();
        }

        showInterestRateTable(appReqBlock);
        return INTEREST_RATE_TABLE;
    }

    protected String cancelInterestRates(AppReqBlock appReqBlock) throws Exception
    {

        PageBean formBean = appReqBlock.getFormBean();
        SessionBean interestRateSessionBean = appReqBlock.getSessionBean("interestRateSessionBean");

        formBean.putValue("fundNumber", "");
        formBean.putValue("fundName", "");
        formBean.putValue("fundId", "");
        formBean.putValue("optionCode", "");
        formBean.putValue("originalDate", "");
        formBean.putValue("originalMonth", "");
        formBean.putValue("originalDay", "");
        formBean.putValue("originalYear", "");

        formBean.putValue("interestRateId", "");
        formBean.putValue("interestRate", "");
        formBean.putValue("effectiveYear", "");
        formBean.putValue("effectiveMonth", "");
        formBean.putValue("effectiveDay", "");
        formBean.putValue("guaranteeDuration", "");

        interestRateSessionBean.putPageBean("formBean", formBean);

        //SessionBean interestRate = appReqBlock.getSessionBean("interestRate");

        return INTEREST_RATE_TABLE;
    }

    protected String importUnitValues(AppReqBlock appReqBlock) throws Exception
    {

        PageBean formBean = appReqBlock.getFormBean();
        SessionBean unitValueSessionBean = appReqBlock.getSessionBean("unitValueSessionBean");

        String[] fileContents = Util.uploadTable(appReqBlock, true);

        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        String message = calculatorComponent.updateUnitValuesFromImport(fileContents);

        unitValueSessionBean.putValue("message", message);

        return UNIT_VALUE_TABLE;

    }


    protected String showVOEditExceptionDialog(AppReqBlock appReqBlock) throws Exception
    {

        VOEditException voEditException = (VOEditException) appReqBlock.getHttpSession().getAttribute("VOEditException");

        // Remove voEditException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("VOEditException");

        appReqBlock.getHttpServletRequest().setAttribute("VOEditException", voEditException);

        return VO_EDIT_EXCEPTION_DIALOG;
    }


    protected String importRateTables(AppReqBlock appReqBlock) throws Exception
    {

        String operator = appReqBlock.getUserSession().getUsername();

        String[] fileContents = Util.uploadTable(appReqBlock, false);

        String fileName = Util.initString((String)appReqBlock.getHttpServletRequest().getAttribute("fileName"), "");

        Calculator calculatorComponent =  new engine.component.CalculatorComponent();

        String message = null;
        if (!fileName.equals(""))
        {
            message = calculatorComponent.updateRateTablesFromImport(fileContents, fileName, operator);
        }
        else
        {
            message = "ImportRateTables needs fileName of Table to Update";
        }

        appReqBlock.getHttpServletRequest().setAttribute("message", message);
        return showTableDefault(appReqBlock);
    }
}
