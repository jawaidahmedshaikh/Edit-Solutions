/*
 * 
 * User: cgleason
 * Date: Jun 15, 2007
 * Time: 12:34:30 PM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package productbuild.ui.servlet;

import fission.global.*;
import fission.utility.*;
import edit.portal.widget.*;
import edit.portal.widgettoolkit.*;
import edit.portal.common.transactions.*;
import edit.services.db.hibernate.*;
import edit.common.exceptions.*;
import edit.common.CodeTableWrapper;
import productbuild.*;
import productbuild.component.*;
import productbuild.business.*;
import engine.*;

public class ProductBuildTran  extends Transaction
{

    private static final String SHOW_QUESTIONNAIRE          = "showQuestionnaire";
    private static final String SHOW_QUESTIONNAIRE_DETAIL   = "showQuestionnaireDetail";
    private static final String SHOW_SELECTED_QUESTIONNAIRE = "showSelectedQuestionnaire";
    private static final String SHOW_QUESTIONNAIRE_RELATION = "showQuestionnaireRelation";
    private static final String ADD_QUESTIONNAIRE           = "addQuestionnaire";
    private static final String SAVE_QUESTIONNAIRE          = "saveQuestionnaire";
    private static final String CANCEL_QUESTIONNAIRE        = "cancelQuestionnaire";
    private static final String DELETE_QUESTIONNAIRE        = "deleteQuestionnaire";
    private static final String CLONE_QUESTIONNAIRES        = "cloneQuestionnaires";
    private static final String SHOW_BUILD_QUESTIONNAIRE_DIALOG = "showBuildQuestionnaireDialog";
    private static final String CANCEL_QUESTIONNAIRE_RELATION   = "cancelQuestionnaireRelation";
    private static final String SHOW_ASSOCIATED_QUESTIONNAIRES  = "showAssociatedQuestionaires";
    private static final String ATTACH_QUESTIONNAIRE_PRODUCTSTRUCTURE  = "attachQuestionnaireToProductStructure";
    private static final String DETACH_QUESTIONNAIRE_PRODUCTSTRUCTURE  = "detachQuestionnaireFromProductStructure";


    private String QUESTIONNAIRE_SUMMARY      = "/productbuild/jsp/questionnaireSummary.jsp";
    private String QUESTIONNAIRE_RELATION     = "/productbuild/jsp/questionnaireRelation.jsp";
    private String BUILD_QUESTIONNAIRE_DIALOG = "/productbuild/jsp/buildQuestionnaireDialog.jsp";
    private String TOOL_BAR                   = "/engine/jsp/toolkitselection.jsp";
    private static final String TEMPLATE_MAIN = "/common/jsp/template/template-main.jsp";


    public ProductBuildTran()
    {

    }
    public String execute(AppReqBlock appReqBlock) throws Throwable
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(SHOW_QUESTIONNAIRE))
        {
            returnPage = showQuestionnaire(appReqBlock);
        }
        else if (action.equals(SHOW_QUESTIONNAIRE_DETAIL))
        {
            returnPage = showQuestionnaireDetail(appReqBlock);
        }
        else if (action.equals(SHOW_SELECTED_QUESTIONNAIRE))
        {
            returnPage = showSelectedQuestionnaire(appReqBlock);
        }
        else if (action.equals(SHOW_QUESTIONNAIRE_RELATION))
        {
            returnPage = showQuestionnaireRelation(appReqBlock);
        }
        else if (action.equals(ADD_QUESTIONNAIRE))
        {
            returnPage = addOrCancelQuestionnaire(appReqBlock);
        }
        else if (action.equals(SAVE_QUESTIONNAIRE))
        {
            returnPage = saveQuestionnaire(appReqBlock);
        }
        else if (action.equals(CANCEL_QUESTIONNAIRE))
        {
            returnPage = addOrCancelQuestionnaire(appReqBlock);
        }
        else if (action.equals(DELETE_QUESTIONNAIRE))
        {
            returnPage = deleteQuestionnaire(appReqBlock);
        }
        else if (action.equals(CANCEL_QUESTIONNAIRE_RELATION))
        {
            returnPage = cancelQuestionnaireRelation(appReqBlock);
        }
        else if (action.equals(SHOW_ASSOCIATED_QUESTIONNAIRES))
        {
            returnPage = showAssociatedQuestionaires(appReqBlock);
        }
        else if (action.equals(SHOW_BUILD_QUESTIONNAIRE_DIALOG))
        {
            returnPage = showBuildQuestionnaireDialog(appReqBlock);
        }
        else if (action.equals(ATTACH_QUESTIONNAIRE_PRODUCTSTRUCTURE))
        {
            returnPage = attachQuestionnaireToProductStructure(appReqBlock);
        }
        else if (action.equals(DETACH_QUESTIONNAIRE_PRODUCTSTRUCTURE))
        {
            returnPage = detachQuestionnaireFromProductStructure(appReqBlock);
        }
        else if (action.equals(CLONE_QUESTIONNAIRES))
        {
            returnPage = cloneQuestionnaires(appReqBlock);
        }
        else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }


    private String showQuestionnaire(AppReqBlock appReqBlock)
    {

        new QuestionnaireTableModel(appReqBlock);

        return QUESTIONNAIRE_SUMMARY;

//        TableModel.get(appReqBlock, QuestionnaireTableModel.class);
//
//        appReqBlock.putInRequestScope("toolbar",TOOL_BAR);
//
//        appReqBlock.putInRequestScope("main", QUESTIONNAIRE_SUMMARY);
//
//        return TEMPLATE_MAIN;
    }


    private String showQuestionnaireDetail(AppReqBlock appReqBlock)
    {
        String questionairePK = new QuestionnaireTableModel(appReqBlock).getSelectedRowId();

        Questionnaire questionnaire = Questionnaire.findByPK(new Long(questionairePK));

        appReqBlock.getHttpServletRequest().setAttribute("activeQuestionnaire", questionnaire);

        return showQuestionnaire(appReqBlock);
    }

    private String showQuestionnaireRelation(AppReqBlock appReqBlock)
    {
        String productStructurePK = new ProductStructureTableModel(appReqBlock).getSelectedRowId();
        if (productStructurePK == null)
        {
            productStructurePK = (String)appReqBlock.getReqParm("selectedRowIds_ProductStructureTableModel");
        }

        new ProductStructureTableModel(appReqBlock);

        if (productStructurePK == null)
        {
            new QuestionnaireTableModel(appReqBlock);
        }
        else
        {
            new QuestionnaireTableModel(appReqBlock, new Long(productStructurePK));
        }

        return QUESTIONNAIRE_RELATION;
    }

    private String showAssociatedQuestionaires(AppReqBlock appReqBlock)
    {
        String productStructurePK = new ProductStructureTableModel(appReqBlock).getSelectedRowId();

        new QuestionnaireTableModel(appReqBlock, new Long(productStructurePK));

        appReqBlock.getHttpServletRequest().setAttribute("selectedRowIds_ProductStructureTableModel", productStructurePK);

        return QUESTIONNAIRE_RELATION;
    }

    private String showBuildQuestionnaireDialog(AppReqBlock appReqBlock)
    {
        String productStructurePK = new ProductStructureTableModel(appReqBlock).getSelectedRowId();
        if (productStructurePK == null)
        {
            productStructurePK = (String)appReqBlock.getReqParm("selectedRowIds_ProductStructureTableModel");
        }

        ProductStructure productStructure = ProductStructure.findByPK(new Long(productStructurePK));
        String companyName = productStructure.getCompanyName();
        String marketingPage = productStructure.getMarketingPackageName();
        String groupProduct = productStructure.getGroupProductName();
        String areaName = productStructure.getAreaName();
        String businessContract = productStructure.getBusinessContractName();
        String companyStructureString = companyName + "," + marketingPage + "," + groupProduct + "," + areaName + "," + businessContract;

        appReqBlock.getHttpServletRequest().setAttribute("companyStructure", companyStructureString);
        appReqBlock.getHttpServletRequest().setAttribute("selectedRowIds_ProductStructureTableModel", productStructurePK);

        new CloneQuestionnaireTableModel(appReqBlock, new Long(productStructurePK));

        return BUILD_QUESTIONNAIRE_DIALOG;
    }

    private String showSelectedQuestionnaire(AppReqBlock appReqBlock)
    {
        String productStructurePK = (String)appReqBlock.getReqParm("selectedRowIds_ProductStructureTableModel");

        String questionairePK = new CloneQuestionnaireTableModel(appReqBlock).getSelectedRowId();

        Questionnaire questionnaire = Questionnaire.findByPK(new Long(questionairePK));

        appReqBlock.getHttpServletRequest().setAttribute("activeQuestionnaire", questionnaire);
        appReqBlock.getHttpServletRequest().setAttribute("selectedRowIds_ProductStructureTableModel", productStructurePK);
        appReqBlock.getHttpServletRequest().setAttribute("activeQuestionnairePK", questionairePK);

        return showBuildQuestionnaireDialog(appReqBlock);
    }

    private String addOrCancelQuestionnaire(AppReqBlock appReqBlock)
    {
        new QuestionnaireTableModel(appReqBlock).resetAllRows();

        return showQuestionnaire(appReqBlock);
    }

    private String saveQuestionnaire(AppReqBlock appReqBlock) throws EDITContractException
    {
        Questionnaire questionnaire = (Questionnaire) Util.mapFormDataToHibernateEntity(appReqBlock.getHttpServletRequest(), Questionnaire.class, SessionHelper.EDITSOLUTIONS, false);

        if (Util.isANumber(questionnaire.getAreaCT()))
        {
            questionnaire.setAreaCT(CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(questionnaire.getAreaCT())).getCode());
        }
        else
        {
            questionnaire.setAreaCT(null);
        }

        String manualIndStatus = Util.initString(appReqBlock.getReqParm("manualIndStatus"), null);

        if (manualIndStatus != null&& manualIndStatus.equalsIgnoreCase("checked"))
        {
            manualIndStatus = "Y";
        }
        else
        {
            manualIndStatus = "N";
        }

        questionnaire.setManualInd(manualIndStatus);

        ProductBuildUseCase productBuildComponent = new ProductBuildUseCaseComponent();
        String responseMessage = null;

        try
        {
            productBuildComponent.saveQuestionnaire(questionnaire);

            responseMessage = "Questionnaire Successfully Saved";
        }
        catch (EDITContractException e)
        {
            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showQuestionnaire(appReqBlock);
    }

    private String deleteQuestionnaire(AppReqBlock appReqBlock)   throws EDITContractException
    {
        String questionnairePK = appReqBlock.getReqParm("questionnairePK");

        ProductBuildUseCase productBuildComponent = new ProductBuildUseCaseComponent();
        String responseMessage = null;

        try
        {
            productBuildComponent.deleteQuestionnaire(new Long(questionnairePK));

            responseMessage = "Questionnaire Successfully Deleted";
        }
        catch (EDITContractException e)
        {
            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showQuestionnaire(appReqBlock);
    }


    private String cancelQuestionnaireRelation(AppReqBlock appReqBlock)
    {
        return showQuestionnaireRelation(appReqBlock);
    }

    private String attachQuestionnaireToProductStructure(AppReqBlock appReqBlock) throws EDITContractException
    {
        String productStructurePK = new ProductStructureTableModel(appReqBlock).getSelectedRowId();

        String questionnairePK = appReqBlock.getReqParm("activeQuestionnairePK");

        int displayOrder = (Integer) new Integer(appReqBlock.getReqParm("displayOrder"));

        ProductBuildUseCase productBuildComponent = new ProductBuildUseCaseComponent();
        String responseMessage = null;

        try
        {
            productBuildComponent.saveFilteredQuestionnaire(new Long(productStructurePK), new Long(questionnairePK), displayOrder);

            responseMessage = "Questionnaire Successfully Attached";
        }
        catch (EDITContractException e)
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
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showBuildQuestionnaireDialog(appReqBlock);
    }

    private String detachQuestionnaireFromProductStructure(AppReqBlock appReqBlock) throws EDITContractException
    {
        String questionnairePK = appReqBlock.getReqParm("activeQuestionnairePK");

        String productStructurePK = new ProductStructureTableModel(appReqBlock).getSelectedRowId();

        ProductBuildUseCase productBuildComponent = new ProductBuildUseCaseComponent();
        String responseMessage = null;

        try
        {
            productBuildComponent.deleteFilteredQuestionnaire(new Long(productStructurePK), new Long(questionnairePK));

            responseMessage = "Questionnaire Successfully Detached";
        }
        catch (EDITContractException e)
        {
            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showBuildQuestionnaireDialog(appReqBlock);
    }

    private String cloneQuestionnaires(AppReqBlock appReqBlock) throws EDITContractException
    {
        String productStructurePK = Util.initString(appReqBlock.getReqParm("selectedRowIds_ProductStructureTableModel"), "0");
        String cloneToProductStructurePK = Util.initString(appReqBlock.getReqParm("cloneToProductStructurePK"), "0");

        ProductBuildUseCase productBuildComponent = new ProductBuildUseCaseComponent();
        String responseMessage = null;

        try
        {
            productBuildComponent.cloneQuestionnaires(new Long(productStructurePK), new Long(cloneToProductStructurePK));

            responseMessage = "Questionnaire Successfully Cloned";
        }
        catch (EDITContractException e)
        {
            responseMessage = e.getMessage();
        }
        finally
        {
            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showBuildQuestionnaireDialog(appReqBlock);
    }
}
