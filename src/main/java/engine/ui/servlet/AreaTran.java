/*
 * User: gfrosti
 * Date: Nov 4, 2004
 * Time: 1:59:48 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.ui.servlet;

import edit.portal.common.transactions.*;
import edit.portal.common.transactions.Transaction;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.common.*;
import fission.global.*;
import fission.utility.*;
import engine.business.*;
import engine.component.*;
import engine.*;

public class AreaTran extends Transaction
{
    // Actions
    private static final String ADD_AREA_KEY = "addAreaKey";
    private static final String SHOW_AREA_KEY_DIALOG = "showAreaKeyDialog";
    private static final String SHOW_AREA_SUMMARY = "showAreaSummary";
    private static final String SHOW_AREA_VALUES = "showAreaValues";
    private static final String DELETE_AREA_KEY = "deleteAreaKey";
    private static final String ADD_AREA_VALUE = "addAreaValue";
    private static final String SAVE_AREA_VALUE = "saveAreaValue";
    private static final String SHOW_AREA_VALUE_DETAILS = "showAreaValueDetails";
    private static final String CANCEL_AREA_VALUE = "cancelAreaValue";
    private static final String DELETE_AREA_VALUE = "deleteAreaValue";
    private static final String SHOW_AREA_RELATION = "showAreaRelation";
    private static final String SHOW_ASSOCIATED_AREA_VALUES = "showAssociatedAreaValues";
    private static final String ATTACH_AREA_VALUES_TO_PRODUCTSTRUCTURE = "attachAreaValuesToProductStructure";
    private static final String DETACH_AREA_VALUES_FROM_PRODUCTSTRUCTURE = "detachAreaValuesFromProductStructure";
    private static final String SHOW_BUILD_AREA_DIALOG = "showBuildAreaDialog";
    private static final String CLONE_AREA_VALUES = "cloneAreaValues";
    private static final String CANCEL_AREA_RELATIONS = "cancelAreaRelations";

    // Pages
    private static final String AREA_KEY_DIALOG = "/engine/jsp/areaKeyDialog.jsp";
    private static final String AREA_SUMMARY = "/engine/jsp/areaSummary.jsp";
    private static final String AREA_RELATION = "/engine/jsp/areaRelation.jsp";
    private static final String BUILD_AREA_DIALOG = "/engine/jsp/buildAreaDialog.jsp";

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

        if (action.equals(ADD_AREA_KEY))
        {
            returnPage = addAreaKey(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_KEY_DIALOG))
        {
            returnPage = showAreaKeyDialog(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_SUMMARY))
        {
            returnPage = showAreaSummary(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_VALUES))
        {
            returnPage = showAreaValues(appReqBlock);
        }
        else if (action.equals(DELETE_AREA_KEY))
        {
            returnPage = deleteAreaKey(appReqBlock);
        }
        else if (action.equals(ADD_AREA_VALUE))
        {
            returnPage = addAreaValue(appReqBlock);
        }
        else if (action.equals(SAVE_AREA_VALUE))
        {
            returnPage = saveAreaValue(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_VALUE_DETAILS))
        {
            returnPage = showAreaValueDetails(appReqBlock);
        }
        else if (action.equals(CANCEL_AREA_VALUE))
        {
            returnPage = cancelAreaValue(appReqBlock);
        }
        else if (action.equals(DELETE_AREA_VALUE))
        {
            returnPage = deleteAreaValue(appReqBlock);
        }
        else if (action.equals(SHOW_AREA_RELATION))
        {
            returnPage = showAreaRelation(appReqBlock);
        }
        else if (action.equals(SHOW_ASSOCIATED_AREA_VALUES))
        {
            returnPage = showAssociatedAreaValues(appReqBlock);
        }
        else if (action.equals(ATTACH_AREA_VALUES_TO_PRODUCTSTRUCTURE))
        {
            returnPage = attachAreaValuesToProductStructure(appReqBlock);
        }
        else if (action.equals(DETACH_AREA_VALUES_FROM_PRODUCTSTRUCTURE))
        {
            returnPage = detachAreaValuesFromProductStructure(appReqBlock);
        }
        else if (action.equals(SHOW_BUILD_AREA_DIALOG))
        {
            returnPage = showBuildAreaDialog(appReqBlock);
        }
        else if (action.equals(CLONE_AREA_VALUES))
        {
            returnPage = cloneAreaValues(appReqBlock);
        }
        else if (action.equals(CANCEL_AREA_RELATIONS))
        {
            returnPage = cancelAreaRelations(appReqBlock);
        }
        else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }

    /**
     * Cancels current area relations operations.
     * @param appReqBlock
     * @return
     */
    private String cancelAreaRelations(AppReqBlock appReqBlock)
    {
        return showAreaRelation(appReqBlock);
    }

    /**
     * Clones the set of AreaValues from one ProductStructure to another ProductStructure.
     * @param appReqBlock
     * @return
     */
    private String cloneAreaValues(AppReqBlock appReqBlock)
    {
        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        String cloneToProductStructurePK = Util.initString(appReqBlock.getReqParm("cloneToProductStructurePK"), null);

        Calculator calculator = new CalculatorComponent();

        String responseMessage = null;

        try
        {
            calculator.cloneAreaValues(Long.parseLong(activeProductStructurePK), Long.parseLong(cloneToProductStructurePK));

            appReqBlock.setReqParm("activeProductStructurePK", cloneToProductStructurePK);

            responseMessage = "Area Value(s) Successfully Cloned";
        }
        catch (EDITEngineException e)
        {
            appReqBlock.setReqParm("activeProductStructurePK", activeProductStructurePK);

            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showBuildAreaDialog(appReqBlock);
    }

    /**
     * Shows the cloneAreaValuesDialog.jsp
     * @param appReqBlock
     * @return
     */
    private String showBuildAreaDialog(AppReqBlock appReqBlock)
    {
        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        Lookup calculatorLookup = new LookupComponent();

        ProductStructureVO cloneFromProductStructureVO = calculatorLookup.findProductStructureVOByPK(Long.parseLong(activeProductStructurePK), false, null)[0];

        ProductStructureVO[] productStructureVOs = calculatorLookup.findByTypeCode("Product",false, null);

        AreaKeyVO[] areaKeyVOs = calculatorLookup.findAllAreaKeys();

        appReqBlock.getHttpServletRequest().setAttribute("cloneFromProductStructureVO", cloneFromProductStructureVO);

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);

        appReqBlock.getHttpServletRequest().setAttribute("areaKeyVOs", areaKeyVOs);

        return BUILD_AREA_DIALOG;
    }

    /**
     * Detaches the selected AreaValue(s) from the selected ProductStructure.
     * @param appReqBlock
     * @return
     */
    private String detachAreaValuesFromProductStructure(AppReqBlock appReqBlock)
    {
        String areaValuePKsList = Util.initString(appReqBlock.getReqParm("areaValuePKs"), null);

        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        Calculator calculator = new CalculatorComponent();

        String[] areaValuePKs = Util.fastTokenizer(areaValuePKsList, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < areaValuePKs.length; i++)
            {
                calculator.detachAreaValueFromProductStructure(Long.parseLong(areaValuePKs[i]), Long.parseLong(activeProductStructurePK));
            }

            responseMessage = "Area Value(s) Successfully Detached";
        }
        catch (EDITEngineException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showBuildAreaDialog(appReqBlock);
    }

    /**
     * Maps selected AreaValue(s) to the selected ProductStructure.
     * @param appReqBlock
     * @return
     */
    private String attachAreaValuesToProductStructure(AppReqBlock appReqBlock)
    {
        String areaValuePKsList = Util.initString(appReqBlock.getReqParm("areaValuePKs"), null);

        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        Calculator calculator = new CalculatorComponent();

        String[] areaValuePKs = Util.fastTokenizer(areaValuePKsList, ",");

        String responseMessage = null;

        try
        {
            for (int i = 0; i < areaValuePKs.length; i++)
            {
                calculator.attachAreaValueToProductStructure(Long.parseLong(areaValuePKs[i]), Long.parseLong(activeProductStructurePK));
            }

            responseMessage = "Area Value(s) Successfully Attached";
        }
        catch (EDITEngineException e)
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

        return showBuildAreaDialog(appReqBlock);
    }

    /**
     * Returns the arearelation.jsp page with a targeted ProductStructure and its associated AreaValues.
     * @param appReqBlock
     * @return
     */
    private String showAssociatedAreaValues(AppReqBlock appReqBlock)
    {
        String activeProductStructurePK = Util.initString(appReqBlock.getReqParm("activeProductStructurePK"), null);

        appReqBlock.getHttpServletRequest().setAttribute("activeProductStructurePK", activeProductStructurePK);

        return showAreaRelation(appReqBlock);
    }

    /**
     * Returns the areaRelation.jsp.
     * @param appReqBlock
     * @return
     */
    private String showAreaRelation(AppReqBlock appReqBlock)
    {
        Lookup calculatorLookup = new LookupComponent();

        ProductStructure[] productStructures = ProductStructure.findAll();

        AreaKeyVO[] areaKeyVOs = calculatorLookup.findAllAreaKeys();

        appReqBlock.getHttpServletRequest().setAttribute("productStructures", productStructures);

        appReqBlock.getHttpServletRequest().setAttribute("areaKeyVOs", areaKeyVOs);

        return AREA_RELATION;
    }

    /**
     * Deletes the selected AreaValue.
     * @param appReqBlock
     * @return
     */
    private String deleteAreaValue(AppReqBlock appReqBlock)
    {
        String activeAreaValuePK = Util.initString(appReqBlock.getReqParm("activeAreaValuePK"), null);

        Calculator calculator = new CalculatorComponent();

        String responseMessage = null;

        boolean areaValueSuccessfullyDeleted;

        try
        {
            calculator.deleteAreaValue(Long.parseLong(activeAreaValuePK));

            areaValueSuccessfullyDeleted = true;

            responseMessage = "Area Value Successfully Deleted";
        }
        catch (EDITEngineException e)
        {
            areaValueSuccessfullyDeleted = false;

            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        if (areaValueSuccessfullyDeleted)
        {
            return showAreaValues(appReqBlock);
        }
        else
        {
            return showAreaValueDetails(appReqBlock);
        }
    }

    /**
     * Cancels all state changes for the AreaValues summary.
     * @param appReqBlock
     * @return
     */
    private String cancelAreaValue(AppReqBlock appReqBlock)
    {
        String activeAreaValuePK = Util.initString(appReqBlock.getReqParm("activeAreaValuePK"), null);

        if (activeAreaValuePK != null)
        {
            return showAreaValues(appReqBlock);
        }
        else
        {
            return showAreaSummary(appReqBlock);
        }
    }

    /**
     * Displays the details of the selected AreaValue.
     * @param appReqBlock
     * @return
     */
    private String showAreaValueDetails(AppReqBlock appReqBlock)
    {
        String activeAreaValuePK = Util.initString(appReqBlock.getReqParm("activeAreaValuePK"), null);

        Lookup calculatorLookup = new LookupComponent();

        AreaValueVO areaValueVO = calculatorLookup.findAreaValueBy_AreaValuePK(Long.parseLong(activeAreaValuePK));

        appReqBlock.getHttpServletRequest().setAttribute("areaValueVO", areaValueVO);

        appReqBlock.getHttpServletRequest().setAttribute("activeAreaValuePK", activeAreaValuePK);

        return showAreaValues(appReqBlock);
    }

    /**
     * Saves a new AreaValue.
     * @param appReqBlock
     * @return
     */
    private String saveAreaValue(AppReqBlock appReqBlock)
    {
        String effectiveMonth = Util.initString(appReqBlock.getReqParm("effectiveMonth"), null);
        String effectiveDay = Util.initString(appReqBlock.getReqParm("effectiveDay"), null);
        String effectiveYear = Util.initString(appReqBlock.getReqParm("effectiveYear"), null);

        EDITDate effectiveDate = new EDITDate(effectiveYear, effectiveMonth, effectiveDay);

        String areaCT = Util.initString(appReqBlock.getReqParm("areaCT"), null);
        String areaValue = Util.initString(appReqBlock.getReqParm("areaValue"), null);
        String qualifierCT = Util.initString(appReqBlock.getReqParm("qualifierCT"), "*");

        String activeAreaKeyPK = Util.initString(appReqBlock.getReqParm("activeAreaKeyPK"), "0");

        String activeAreaValuePK = Util.initString(appReqBlock.getReqParm("activeAreaValuePK"), "0");

        AreaValueVO areaValueVO = new AreaValueVO();

        areaValueVO.setAreaCT(areaCT);
        areaValueVO.setAreaKeyFK(Long.parseLong(activeAreaKeyPK));
        areaValueVO.setAreaValue(areaValue);
        areaValueVO.setAreaValuePK(Long.parseLong(activeAreaValuePK));
        areaValueVO.setEffectiveDate(effectiveDate.getFormattedDate());
        areaValueVO.setQualifierCT(qualifierCT);

        String responseMessage = null;

        try
        {
            Calculator calculatorComponent = new CalculatorComponent();

            calculatorComponent.saveAreaValue(areaValueVO);

            responseMessage = "Area Value Successfully Saved";

        }
        catch (EDITEngineException e)
        {
            responseMessage = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("activeAreaValuePK", activeAreaValuePK);
        }

        appReqBlock.getHttpServletRequest().setAttribute("activeAreaKeyPK", activeAreaKeyPK);

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showAreaValues(appReqBlock);
    }

    /**
     * Prepares the areaSummary.jsp to add a new AreaValue.
     * @param appReqBlock
     * @return
     */
    private String addAreaValue(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("activeAreaValuePK", null);

        return showAreaValues(appReqBlock);
    }

    /**
     * Delete the AreaKey and its associated AreaValues.
     * @param appReqBlock
     * @return
     */
    private String deleteAreaKey(AppReqBlock appReqBlock)
    {
        String activeAreaKeyPK = Util.initString(appReqBlock.getReqParm("activeAreaKeyPK"), null);

        Calculator calculatorComponent = new CalculatorComponent();

        try
        {
            calculatorComponent.deleteAreaKey(Long.parseLong(activeAreaKeyPK));

            appReqBlock.setReqParm("activeAreaKeyPK", null);

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", "Area Key Successfully Deleted");

            return showAreaSummary(appReqBlock);
        }
        catch (EDITEngineException e)
        {
            String responseMessage = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

            return showAreaValues(appReqBlock);
        }
    }

    /**
     * Renders the areaSummary.jsp page with the AreaValue of the selected areaPK.
     * @param appReqBlock
     * @return
     */
    private String showAreaValues(AppReqBlock appReqBlock)
    {
        String activeAreaKeyPK = Util.initString(appReqBlock.getReqParm("activeAreaKeyPK"), null);

        Lookup calculatorLookup = new LookupComponent();

        AreaValueVO[] areaValueVOs = calculatorLookup.findAreaValuesBy_AreaKeyPK(Long.parseLong(activeAreaKeyPK));

        appReqBlock.getHttpServletRequest().setAttribute("areaValueVOs", areaValueVOs);

        appReqBlock.getHttpServletRequest().setAttribute("activeAreaKeyPK", activeAreaKeyPK);

        return showAreaSummary(appReqBlock);
    }

    /**
     * Renders the areaSummary.jsp page in its proper state.
     * @param appReqBlock
     * @return
     */
    private String showAreaSummary(AppReqBlock appReqBlock)
    {
        Lookup calculatorLookup = new LookupComponent();

        AreaKeyVO[] areaKeyVOs = calculatorLookup.findAllAreaKeys();

        appReqBlock.getHttpServletRequest().setAttribute("areaKeyVOs", areaKeyVOs);

        return AREA_SUMMARY;
    }

    /**
     * The dialog for adding a new AreaKey.
     * @param appReqBlock
     * @return
     */
    private String showAreaKeyDialog(AppReqBlock appReqBlock)
    {
        return AREA_KEY_DIALOG;
    }

    /**
     * Adds a new AreaKey via the supplied grouping and field name.
     * @param appReqBlock
     * @return
     */
    private String addAreaKey(AppReqBlock appReqBlock)
    {
        String grouping = Util.initString(appReqBlock.getReqParm("grouping"), null);
        String field = Util.initString(appReqBlock.getReqParm("field"), null);

        Calculator calculatorComponent = new CalculatorComponent();

        String responseMessage = null;

        try
        {
            calculatorComponent.addAreaKey(grouping, field);

            responseMessage = "Area Key Successfully Added";
        }
        catch (EDITEngineException e)
        {
            responseMessage = e.getMessage();
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showAreaSummary(appReqBlock);
    }
}
