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

import edit.common.*;
import edit.common.vo.*;
import edit.common.vo.user.UIRulesVO;
import edit.portal.common.transactions.Transaction;
import edit.portal.common.session.UserSession;
import edit.portal.exceptions.PortalEditingException;
import engine.business.Analyzer;
import engine.business.Calculator;
import engine.business.Lookup;
import engine.component.CalculatorComponent;
import engine.component.LookupComponent;
import engine.sp.*;
import engine.*;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.dm.SMException;
import fission.dm.valueobject.CallChainVO;
import fission.global.AppReqBlock;
import fission.utility.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * ScriptTran is used to handle Script related requests
 */
public class ScriptTran extends Transaction {

    public static final String SHOW_SCRIPT_DEFAULT          = "showScriptDefault";
    public static final String SHOW_SCRIPT                  = "showScript";
    public static final String SCRIPT_DEFAULT_PAGE          = "/engine/jsp/scripts.jsp";
    public static final String ADD_NEW_SCRIPT               = "addNewScript";
    public static final String NEW_SCRIPT_NAME_DIALOG       = "/engine/jsp/newScriptNameDialog.jsp";
    public static final String SETUP_FOR_NEW_SCRIPT         = "setupForNewScript";
    public static final String REFRESH_DEBUG_MAIN           = "refreshDebugMain";
    public static final String DEBUGMAIN_SCREEN             = "/engine/jsp/dbugmain.jsp";
    public static final String SHOW_DEBUG_MAIN_SCRIPT       = "showDebugMainScript";
    public static final String UPDATE_DEBUG_PARAM           = "updateDebugParam";
    public static final String EXPAND_SCRIPT                = "expandScript";
    public static final String SHOW_ENHANCED_EDIT_APPLET    = "showEnhancedEditApplet";
    public static final String EXPANDSCRIPT_SCREEN          = "/engine/jsp/expandscrp.jsp";
    public static final String ENHANCEDEDIT_SCREEN          = "/engine/jsp/enhancedapplet.jsp";
    public static final String LOCK_SCRIPT                  = "lockScript";
    public static final String SAVE_SCRIPT                  = "saveScript";
    public static final String UNLOCK_SCRIPT                = "unlockScript";
    public static final String UNLOCK_SCRIPT_2              = "unlockScript2";
    public static final String DELETE_SCRIPT                = "deleteScript";
    public static final String IMPORT_SCRIPT                = "importScript";
    public static final String EXPORT_TO_XML                = "exportToXML";
    public static final String SCRIPT_COPY_DIALOG           = "/engine/jsp/copyselectiondialog.jsp";
    public static final String GET_SCRIPT_CALL_CHAIN        = "getScriptCallChain";
    public static final String SCRIPT_LC                    = "script";
    public static final String SCRIPT_NAME                  = "scriptName";
    public static final String SCRIPT_TEXT                  = "scriptText";
    public static final String SCRIPT_ID                    = "scriptId";
    public static final String SCRIPT_TYPE                  = "scriptType";
    public static final String SCRIPT_STATUS                = "scriptStatus";
    public static final String A_USER_BEAN                  = "aUserBean";
    public static final String A_PARAM_BEAN                 = "aParamBean";
    public static final String INVALID_DRAG_DROP_REQUESTS   = "Invalid drag drop request";
    public static final String INVALID_DRAG_DROP_SOURCE_REQUEST = "Invalid drag drop source request";
    public static final String DEBUGVIEW_SCREEN                 = "/engine/jsp/dbugview.jsp";
    private static final String EDITING_EXCEPTION_DIALOG        = "/common/jsp/editingExceptionDialog.jsp";
    private static final String SHOW_EDITING_EXCEPTION_DIALOG   = "showEditingExceptionDialog";
    private static final String SHOW_ASSOCIATED_RULES_FOR_SCRIPT = "showAssociatedRulesForScript";
    private static final String ASSOCIATED_RULES_FOR_SCRIPT_DIALOG = "/engine/jsp/associatedRulesForScriptDialog.jsp";
    private static final String SHOW_JUMP_TO_DIALOG = "showJumpToDialog";
    private static final String JUMP_TO_DIALOG = "/common/jsp/jumpToDialog.jsp";
    private static final String SHOW_CANCEL_SCRIPT_CHANGES_DIALOG = "showCancelScriptChangesDialog";
    private static final String CANCEL_SCRIPT_CHANGES_DIALOG = "/engine/jsp/cancelScriptChangesDialog.jsp";
    private static final String CANCEL_SCRIPT_CHANGES = "cancelScriptChanges";
    private static final String GET_SELECTED_SCRIPT = "getSelectedScript";
    

    /**
     * Used to execute transaction
     */
    public String execute(AppReqBlock appReqBlock) throws Exception  {

        String action = appReqBlock.getReqParm("action");

        // Determine request and provide appropriate action
        if (action.equalsIgnoreCase(SHOW_SCRIPT_DEFAULT)) {

            return showScriptDefault(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SHOW_SCRIPT)) {

            return showScript(appReqBlock);
        }
        else if (action.equalsIgnoreCase(ADD_NEW_SCRIPT)) {

            return showNewScriptNameDialog(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SETUP_FOR_NEW_SCRIPT)) {

            return setupForNewScript(appReqBlock);
        }
        else if (action.equalsIgnoreCase(REFRESH_DEBUG_MAIN)) {

        	refreshDebugMain(appReqBlock);
			return DEBUGMAIN_SCREEN;
        }
        else if (action.equalsIgnoreCase(SHOW_DEBUG_MAIN_SCRIPT)) {

            showDebugMainScript(appReqBlock);
		    return DEBUGMAIN_SCREEN;
        }
        else if (action.equalsIgnoreCase(UPDATE_DEBUG_PARAM)) {

            updateDebugParam(appReqBlock);
			return DEBUGMAIN_SCREEN;
        }
        else if (action.equalsIgnoreCase("debugStep")) {

           	return debugStep(appReqBlock);
        }
        else if (action.equalsIgnoreCase("debugRun")) {

            return debugRun(appReqBlock);
        }
        else if (action.equalsIgnoreCase("debugReset")) {

            return debugReset(appReqBlock);
        }
        else if (action.equalsIgnoreCase("debugClear")) {

            return debugClear(appReqBlock);
		}
        else if (action.equalsIgnoreCase("doProjection")) {

            return doProjection(appReqBlock);
        }
        else if(action.equalsIgnoreCase(EXPAND_SCRIPT)) {

			expandScript(appReqBlock);

			//pass this expanded Script DDGI to be expanded in jsp page
			return EXPANDSCRIPT_SCREEN;

        }
        else if (action.equalsIgnoreCase("processBreakPoint")) {

             return processBreakPoint(appReqBlock);
        }
        else if (action.equalsIgnoreCase("showStackViewer")) {

            return showStackViewer(appReqBlock);
        }
        else if (action.equalsIgnoreCase("showFunctionViewer")) {

            return showFunctionViewer(appReqBlock);
        }
        else if (action.equalsIgnoreCase("showVectorViewer")) {
            return showVectorViewer(appReqBlock);
        }
        else if (action.equalsIgnoreCase("showDocumentViewer")) {
            return showDocumentViewer(appReqBlock);
        }
        else if (action.equalsIgnoreCase("showOutputViewer")) {

            return showOutputViewer (appReqBlock);
        // Enhanced Script Mode
		}
        else if (action.equalsIgnoreCase(SHOW_ENHANCED_EDIT_APPLET)) {

			showEnhancedEditApplet(appReqBlock);
			return ENHANCEDEDIT_SCREEN;
        }
        else if (action.equalsIgnoreCase(LOCK_SCRIPT)) {

    		return lockScript(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SAVE_SCRIPT)) {

			return saveScript(appReqBlock);
        }
        else if (action.equalsIgnoreCase(UNLOCK_SCRIPT)) {

            return unlockScript(appReqBlock);
        }
        else if (action.equalsIgnoreCase(UNLOCK_SCRIPT_2)) {

            return unlockScript(appReqBlock);
        }
        else if (action.equalsIgnoreCase(DELETE_SCRIPT)) {

            return deleteScript(appReqBlock);
	    }
        else if (action.equalsIgnoreCase(IMPORT_SCRIPT)) {

          	importScript(appReqBlock);
		  	return SCRIPT_DEFAULT_PAGE;
		}
        else if (action.equalsIgnoreCase(EXPORT_TO_XML)) {

			exportToXML(appReqBlock);
            return DOWNLOADFILE_REQUEST;
	    }
        else if(action.equals("doPayoutProjection")) {

			return doPayoutProjection(appReqBlock);
        }
        else if (action.equals(GET_SCRIPT_CALL_CHAIN)) {

            return getScriptCallChain(appReqBlock);
        }
        else if (action.equals(SHOW_EDITING_EXCEPTION_DIALOG)) {

            return showEditingExceptionDialog(appReqBlock);
        }
        else if (action.equals(SHOW_ASSOCIATED_RULES_FOR_SCRIPT)) {

            return showAssociatedRulesForScript(appReqBlock);
        }
        else if (action.equals(SHOW_JUMP_TO_DIALOG)) {

            return showJumpToDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CANCEL_SCRIPT_CHANGES_DIALOG)) {

            return showCancelScriptChangesDialog(appReqBlock);
        }
        else if (action.equals(CANCEL_SCRIPT_CHANGES)) {

            return cancelScriptChanges(appReqBlock);
        }
        else if (action.equals(GET_SELECTED_SCRIPT))
        {
            return getSelectedScript(appReqBlock);
        }
        else
        {

            throw new Exception("ScriptTran: Invalid action " + action);
        }
    }


//***********************************************************
//*Script and Enhanced Script action Methods
//***********************************************************

    protected String showScriptDefault (AppReqBlock appReqBlock) throws Exception {

        Lookup calcLookup = new LookupComponent();

        ScriptVO[] scriptVOs = calcLookup.findAllScriptVOs(false, null);

        long[] attachedPKs = calcLookup.findAttachedScriptPKs();

        List attachedScriptPKs = new ArrayList();

        if (attachedPKs != null) {

            for (int i = 0; i < attachedPKs.length; i++) {

                attachedScriptPKs.add(new Long(attachedPKs[i]));
            }
        }

        ScriptInstructionVO[] scriptInstructionVOs = calcLookup.getAllScriptInstructions();

        appReqBlock.getHttpServletRequest().setAttribute("scriptInstructionVOs", scriptInstructionVOs);
        appReqBlock.getHttpServletRequest().setAttribute("scriptVOs", scriptVOs);
        appReqBlock.getHttpServletRequest().setAttribute("attachedScriptPKs", attachedScriptPKs);
        String action = (String) appReqBlock.getReqParm("action");
        appReqBlock.getHttpServletRequest().setAttribute("action", action);

        return SCRIPT_DEFAULT_PAGE;
	}

    protected String showScript (AppReqBlock appReqBlock) throws Exception {

        String scriptPK = initParam(appReqBlock.getReqParm("scriptId"), "0");
        if (!scriptPK.equals("0")) {

            Lookup calcLookup = new LookupComponent();

            ScriptVO[] scriptVOs = calcLookup.findScriptVOByPK(Long.parseLong(scriptPK), false, null);
            if (scriptVOs != null && scriptVOs.length > 0)
            {
                ScriptLineVO[] scriptLineVOs = calcLookup.getAllScriptLines(Long.parseLong(scriptPK));

                appReqBlock.getHttpServletRequest().setAttribute("scriptVO", scriptVOs[0]);
                appReqBlock.getHttpServletRequest().setAttribute("scriptLineVOs", scriptLineVOs);
            }

            UserSession userSession = appReqBlock.getUserSession();

            userSession.setScriptPK( Long.parseLong(scriptPK) );
        }

        return showScriptDefault(appReqBlock);
	}

    protected String showNewScriptNameDialog (AppReqBlock appReqBlock) throws Exception {

        return NEW_SCRIPT_NAME_DIALOG;
	}

    protected String setupForNewScript (AppReqBlock appReqBlock) throws Exception {

        String scriptName = appReqBlock.getFormBean().getValue("scriptName");

        appReqBlock.getHttpServletRequest().setAttribute("scriptName", scriptName);

        UserSession userSession = appReqBlock.getUserSession();

        userSession.lockScript(0);

        return showScriptDefault(appReqBlock);
	}

	protected void expandScript(AppReqBlock appReqBlock) throws Exception {

		// TO BE COMPLETED LATER
	}

    protected String saveScript(AppReqBlock appReqBlock) throws Exception {

		CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String scriptName   = appReqBlock.getReqParm(SCRIPT_NAME).trim();
        String scriptId     = appReqBlock.getReqParm(SCRIPT_ID).trim();
        String scriptText   = appReqBlock.getReqParm(SCRIPT_TEXT);
        String scriptType   = appReqBlock.getReqParm(SCRIPT_TYPE);
        String scriptStatus = appReqBlock.getReqParm(SCRIPT_STATUS);
        String operator     = appReqBlock.getUserSession().getUsername();
        long scriptPK = 0;
        if (!scriptId.equals("")) {

            scriptPK = Long.parseLong(scriptId);
        }

        if (Util.isANumber(scriptType))
        {
            scriptType = codeTableWrapper.getCodeTableEntry(Long.parseLong(scriptType)).getCode();
        }
        else
        {
            scriptType = null;
        }

        if (Util.isANumber(scriptStatus))
        {
            scriptStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(scriptStatus)).getCode();
        }
        else
        {
            scriptStatus = null;
        }

		checkForValidScriptName(scriptName);

        Calculator calcComp = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        long  newScriptPK = calcComp.saveScript(scriptPK, scriptName, scriptText, scriptType, scriptStatus, operator);

        if (newScriptPK > 0) {

            UserSession userSession = appReqBlock.getUserSession();

            userSession.unlockScript();

            String message = "Script Saved Successfully";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }

        return showScriptDefault(appReqBlock);
    }

    protected String lockScript(AppReqBlock appReqBlock) throws Exception {

        UserSession userSession = appReqBlock.getUserSession();

        userSession.lockScript( userSession.getScriptPK() );

        return showScript(appReqBlock);
    }

    /*protected String changeScript(AppReqBlock appReqBlock) throws Exception {

		CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String scriptId     = appReqBlock.getReqParm(SCRIPT_ID).trim();
        String scriptName   = appReqBlock.getReqParm(SCRIPT_NAME).trim();
        String scriptText   = appReqBlock.getReqParm(SCRIPT_TEXT);
        String scriptType   = appReqBlock.getReqParm(SCRIPT_TYPE);
        String scriptStatus = appReqBlock.getReqParm(SCRIPT_STATUS);
        String operator     = appReqBlock.getUserSession().getUsername();

        long scriptPK = 0;
        if (!scriptId.equals("")) {

            scriptPK = Long.parseLong(scriptId);
        }

        if (Util.isANumber(scriptType))
        {
            scriptType = codeTableWrapper.getCodeTableEntry(Long.parseLong(scriptType)).getCode();
        }
        else
        {
            scriptType = null;
        }

        if (Util.isANumber(scriptStatus))
        {
            scriptStatus = codeTableWrapper.getCodeTableEntry(Long.parseLong(scriptStatus)).getCode();
        }
        else
        {
            scriptStatus = null;
        }

		checkForValidScriptName(scriptName);

        Calculator calcComp = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        long  newScriptPK = calcComp.saveScript(scriptPK, scriptName, scriptText, scriptType, scriptStatus, operator);

		//get the updated script

        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");
        ScriptVO[] scriptVO = null;

        if (scriptPK == 0) {

            scriptVO = (ScriptVO[]) lookup.findScriptVOByPK(newScriptPK, true, null);
        }

        else {

            scriptVO = (ScriptVO[]) lookup.findScriptVOByPK(scriptPK, true, null);
        }

        ScriptLineVO[] scriptLineVOs = scriptVO[0].getScriptLineVO();

        appReqBlock.getHttpServletRequest().setAttribute("scriptVO", scriptVO[0]);
        appReqBlock.getHttpServletRequest().setAttribute("scriptLineVOs", scriptLineVOs);

        return showScriptDefault(appReqBlock);
	}*/

	protected String deleteScript(AppReqBlock appReqBlock) throws Exception {

		String scriptId = initParam(appReqBlock.getReqParm("scriptId"), "0");

        if (scriptId.equals("0")) {

            String message = "Script Selection Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showScriptDefault(appReqBlock);
        }

        else {

            Calculator calcComponent = new CalculatorComponent();;

            try {

                long scriptID = Long.parseLong(scriptId.trim());

                calcComponent.deleteScript(scriptID);
                String message = "Script Successfully Deleted";

                appReqBlock.getHttpServletRequest().setAttribute("message", message);

                return showScriptDefault(appReqBlock);
            }

            catch (Exception e) {

              System.out.println(e);

                e.printStackTrace();

                String message = e.getMessage();

                appReqBlock.getHttpServletRequest().setAttribute("message", message);
                appReqBlock.getHttpServletRequest().setAttribute("scriptId", scriptId);

                return showScript(appReqBlock);
            }
        }
	}

	protected void importScript(AppReqBlock appReqBlock) throws Exception {

		/* aScriptBean =
	    (ScriptBean)((HttpSession)aAppReqBlock.getHttpSession()).getAttribute(Reserved.A_SCRIPT_BEAN);
		uploadFile(aAppReqBlock,aScriptBean);
		((HttpSession)aAppReqBlock.getHttpSession()).setAttribute(Reserved.A_SCRIPT_BEAN,aScriptBean); */
	}

	protected void exportToXML(AppReqBlock appReqBlock) throws Exception  {
		/*

            String xmlFilePath = new String(aAppReqBlock.gobjGetCommonGlobal().getXMLWorkPath());
            String xmlFileName = Reserved.SCRIPTS +  ((HttpSession)aAppReqBlock.getHttpSession()).getId() + Reserved.XML;

			String xmlNewFileName2 = aAppReqBlock.getReqParm(Reserved.SCRIPT_NAME);
			String xmlNewFileName = xmlNewFileName2 + Reserved.XML;

            ((HttpSession)aAppReqBlock.getHttpSession()).setAttribute(Reserved.FILE_NAME,xmlFileName);
            ((HttpSession)aAppReqBlock.getHttpSession()).setAttribute(Reserved.FILE_PATH,xmlFilePath);
            ((HttpSession)aAppReqBlock.getHttpSession()).setAttribute(Reserved.NEW_FILE_NAME,xmlNewFileName);

			String scriptName = xmlNewFileName2;
			ScriptBean sb = getScriptByName(scriptName);
			int scriptId = sb.getId();

			DDGroupItem groupItem = ed.getScriptLinesDD(scriptId);
			ed.exportToXML(xmlFilePath,xmlFileName,groupItem);		*/
	}

    protected void showEnhancedEditApplet(AppReqBlock appReqBlock) throws Exception  {

	/*		aScriptBean =
			     (ScriptBean)((HttpSession)appReqBlock.getHttpSession()).getAttribute("aScriptBean");

			//If scriptBean is null
			//create a ScriptBean instance and load the scriptTree
			if(aScriptBean == null) {
			    aScriptBean = new ScriptBean();
			    aScriptBean.setScriptTree(ed.getScriptTree());
			}

	        ((HttpSession)appReqBlock.getHttpSession()).setAttribute("aScriptBean",aScriptBean);

			aScriptBean.addScriptElementAt(0, " ");
	        aScriptBean.setScriptInstructions(ed.getScriptInstructions());
	        TableBean aTableBean = new TableBean();
			aTableBean.setTableNames(ed.getTableNames());
	        aCodeTableBean =  new CodeTableBean();
	        ed.loadAllCodeTables();
	        aCodeTableBean.setCodeTables(ed.getCodeTables());
	        ((HttpSession)appReqBlock.getHttpSession()).setAttribute("aScriptBean",aScriptBean);
	        ((HttpSession)appReqBlock.getHttpSession()).setAttribute("aTableBean",aTableBean);
	        ((HttpSession)appReqBlock.getHttpSession()).setAttribute("aCodeTableBean",aCodeTableBean);*/
	}

//****End*Script*Action*Methods******************************


//**********************************************************
//*                    Debug Section
//********************************************************
    protected void updateDebugParam(AppReqBlock appReqBlock)
                           throws SMException,Exception
    {

    }

	protected void refreshDebugMain(AppReqBlock appReqBlock)
								throws Exception {

		// ((HttpServletRequest)aAppReqBlock.getHttpServletRequest()).setAttribute(Reserved.A_SCRIPT_PROC_BEAN,ed.getScriptProc());

	}

	protected void showDebugMainScript(AppReqBlock appReqBlock) throws Exception {

		String sname = null;
            // sname = ed.getScriptName();
            if (sname == null)
            	throw new Exception("Please select a projection key");

            // ed.setScriptName(sname);
            // ed.loadScript(sname);

			//set that script is loaded
	        // ed.setScriptLoaded(true);
            appReqBlock.getHttpSession().removeAttribute(A_PARAM_BEAN);
            //((HttpSession)aAppReqBlock.getHttpSession()).setAttribute(Reserved.A_SCRIPT_PROC_BEAN,ed.getScriptProc());
	}

	protected String debugStep(AppReqBlock appReqBlock) throws Exception {

		//PageBean debugScriptBean = aAppReqBlock.getSessionBean("paramBean").getPageBean("debugScriptBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");
        //Calculator calculatorComponent = (engine.business.Calculator) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null)?"":ignoreEditWarnings;

        SPOutputVO spOutputVO = null;
        ValidationVO[] validationVOs = null;
        PortalEditingException editingException = null;

        try
        {
            spOutputVO = analyzerComponent.execSingleInstScriptProcessor();

            setValues(analyzerComponent, appReqBlock);
        }
        catch (SPException e)
        {
            setValues(analyzerComponent, appReqBlock);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw e;
        }

        if (!ignoreEditWarnings.equals("true"))
        {
            validationVOs = spOutputVO.getValidationVO();

            if (spOutputVO.getValidationVOCount() > 0)
            {
                editingException = new PortalEditingException();

                editingException.setValidationVOs(validationVOs);

                editingException.setReturnPage(DEBUGVIEW_SCREEN);

                editingException.setTransaction("ScriptTran");

                editingException.setAction("debugStep");

                throw editingException;
            }
        }

        return DEBUGVIEW_SCREEN;
    }

	protected String debugRun(AppReqBlock appReqBlock) throws Exception, PortalEditingException {

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

        String ignoreEditWarnings = appReqBlock.getReqParm("ignoreEditWarnings");
        ignoreEditWarnings = (ignoreEditWarnings == null)?"":ignoreEditWarnings;

        SPOutputVO spOutputVO = null;
        ValidationVO[] validationVOs = null;
        PortalEditingException editingException = null;

        try {

            spOutputVO = analyzerComponent.execScriptProcessor();

            setValues(analyzerComponent, appReqBlock);
        }
        catch (SPException e)
        {
            setValues(analyzerComponent, appReqBlock);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw e;
        }

        if (!ignoreEditWarnings.equals("true"))
        {
            validationVOs = spOutputVO.getValidationVO();

            if (spOutputVO.getValidationVOCount() > 0)
            {
                editingException = new PortalEditingException();

                editingException.setValidationVOs(validationVOs);

                editingException.setReturnPage(DEBUGMAIN_SCREEN);

                editingException.setTransaction("ScriptTran");

                editingException.setAction("debugRun");

                throw editingException;
            }
        }

        return DEBUGMAIN_SCREEN;
	}

	protected String debugReset(AppReqBlock appReqBlock) throws Exception {

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.resetScriptProcessor();

		/**************************************************************

			Actually the below code is to clear the contents of string arrays
			like dataStackValues, workingStorageValues etc..

			I know pretty well that after resetting the script processor
			all stacks, hashtables will be empty.

		***************************************************************/

		PageBean debugScriptBean = appReqBlock.
									 getSessionBean("paramBean").
									   getPageBean("debugScriptBean");

		String[] stringArray;

		stringArray = analyzerComponent.getDataStack();

		debugScriptBean.putValues("dataStackValues", stringArray, new String[] {"toString"}, null);

//		stringArray = analyzerComponent.getWS();
//
//		debugScriptBean.putValues("workingStorageValues", stringArray, new String[] {"toString"}, null);

        appReqBlock.getHttpSession().setAttribute("workingStorage", analyzerComponent.getWS());

		stringArray = analyzerComponent.getFunctions();

		debugScriptBean.putValues("functionTables", stringArray, new String[] {"toString"}, null);

		// Here I am passing empty string because to clear function entries.

		stringArray = analyzerComponent.getFunctionEntry("");

		debugScriptBean.putValues("functionEntries", stringArray, new String[] {"toString"}, null);

//		stringArray = analyzerComponent.getOutput();
//
//		debugScriptBean.putValues("outputData", stringArray, new String[] {"toString"}, null);

        stringArray = analyzerComponent.getVectorTable();

        debugScriptBean.putValues("vectorTable", stringArray);

        // Here I am passing empty string because to clear function entries.

        stringArray = analyzerComponent.getVectorTableEntry("");

		debugScriptBean.putValues("vectorEntries", stringArray);

		stringArray = debugScriptBean.getValues("breakPoints");

		debugScriptBean.putValues("breakPoints", stringArray, new String[] {"toString"}, null);

		getInstructionPointers(appReqBlock);

		return "/engine/jsp/dbugmain.jsp";
    }

	protected void getInstructionPointers(AppReqBlock appReqBlock) {

		PageBean debugScriptBean = appReqBlock.
									 getSessionBean("paramBean").
									   getPageBean("debugScriptBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		debugScriptBean.putValue("instPtr", analyzerComponent.getInstPtr());

		debugScriptBean.putValue("lastInstPtr", analyzerComponent.getLastInstPtr());

		debugScriptBean.putValue("currentRow", analyzerComponent.getCurrentRow());
	}

	protected String debugClear(AppReqBlock appReqBlock) throws Exception {

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.clearScriptProcessor();

		analyzerComponent.setScriptLoaded(false);

        analyzerComponent.setScriptName(null);

		String debugContractMode = appReqBlock.getSessionBean("paramBean").getValue("debugContractMode");
        String projectionMode = appReqBlock.getSessionBean("paramBean").getValue("projectionMode");
        String editMode = appReqBlock.getSessionBean("paramBean").getValue("editMode");

		appReqBlock.getSessionBean("paramBean").clearState();

        appReqBlock.getSessionBean("paramBean").putValue("debugContractMode", debugContractMode);
        appReqBlock.getSessionBean("paramBean").putValue("projectionMode", projectionMode);
        appReqBlock.getSessionBean("paramBean").putValue("editMode", editMode);
		if(debugContractMode.equalsIgnoreCase("LifeContract")) {
			return "/engine/jsp/params.jsp";
		}
		else if(debugContractMode.equalsIgnoreCase("PayoutContract")) {
			return "/engine/jsp/payoutParams.jsp";
		}
		else if(debugContractMode.equalsIgnoreCase("Transaction")) {
			return "/engine/jsp/transactionParams.jsp";
		}
		else {
			return "/engine/jsp/params.jsp";
		}
	}

	protected String processBreakPoint(AppReqBlock appReqBlock) throws Exception {

		PageBean debugScriptBean = appReqBlock.
									 getSessionBean("paramBean").
									   getPageBean("debugScriptBean");

		String bpt = appReqBlock.getReqParm("breakpoint");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		if (analyzerComponent.containsBreakPointKeySP(bpt)) {

			analyzerComponent.removeBreakPointEntrySP(bpt);

		}
        else {

			analyzerComponent.addBreakPointEntrySP(bpt, new Boolean(false));
		}

		String[] breakPoints = analyzerComponent.getBreakPoints();

		debugScriptBean.putValues("breakPoints", breakPoints, new String[] {"toString"}, null);

		getInstructionPointers(appReqBlock);

		return DEBUGMAIN_SCREEN;
	}

//******End*Of*The*Debug*************************************

// Projection

	protected String doProjection(AppReqBlock appReqBlock)throws Exception {

        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

		int j = 0;

		List duration 			= new ArrayList();
		List premium 				= new ArrayList();
		List deathBenefit 		= new ArrayList();
		List cashValue 			= new ArrayList();
		List cashSurrenderValue 	= new ArrayList();

		PageBean pageBean = appReqBlock.
			 					getSessionBean("paramBean").
									getPageBean("baseParamBean");

        calculatorComponent.setFinancialType("BASEPARM");
        calculatorComponent.setSelectedIndex(0);

		setParameterValues(appReqBlock, pageBean);

		String projScriptName = null;
		projScriptName = calculatorComponent.getScriptName();
		if (projScriptName == null)
			throw new Exception("Please select a projection key");

		/* I need not do this bcos loading the script into
		script processor is done while setting the key. */

        calculatorComponent.resetScriptProcessor();
//        calculatorComponent.execScriptProcessor();// Projections will have to be reconsidered. We want STATELESS interactions with the calc component

		// Get all the values from script processor to display.



		pageBean.putValue("GSP", calculatorComponent.getOutputEntry("GSP"));
		pageBean.putValue("GLP", calculatorComponent.getOutputEntry("GLP"));
		pageBean.putValue("TAMRA", calculatorComponent.getOutputEntry("TAMRA"));

		for (int i=0; i<100; i++) {

			j = i + 1;

			if ((! calculatorComponent.containsOutputKey("DurationGuaranteed" + j + ".0"))) {
			         break;
			}

			duration.add(calculatorComponent.getOutputEntry("DurationGuaranteed" + j + ".0"));

			premium.add(calculatorComponent.getOutputEntry("PremiumGuaranteed" + j + ".0"));

			deathBenefit.add(calculatorComponent.getOutputEntry("DeathBenefitGuaranteed" + j + ".0"));

			cashValue.add(calculatorComponent.getOutputEntry("CashValueGuaranteed" + j + ".0"));

			cashSurrenderValue.add(calculatorComponent.getOutputEntry("CashSurrenderValueGuaranteed" + j + ".0"));
		}

		pageBean.putValues("durationGuar", (String[]) duration.toArray(new String[duration.size()]));
		pageBean.putValues("premiumGuar", (String[]) premium.toArray(new String[premium.size()]));
		pageBean.putValues("deathBenefitGuar", (String[]) deathBenefit.toArray(new String[deathBenefit.size()]));
		pageBean.putValues("cashValueGuar", (String[]) cashValue.toArray(new String[cashValue.size()]));
		pageBean.putValues("cashSurrenderValueGuar", (String[]) cashSurrenderValue.toArray(new String[cashSurrenderValue.size()]));


		// Just reset the values and clear the contents
		// of vectors so that you can keep curr values.
		j = 0;

		duration.clear();
		premium.clear();
		deathBenefit.clear();
		cashValue.clear();
		cashSurrenderValue.clear();

		for (int i=0; i<100; i++) {

			j = i + 1;

			if (! calculatorComponent.containsOutputKey("DurationCurrent" + j + ".0")) {
			         break;
			}

			duration.add(calculatorComponent.getOutputEntry("DurationCurrent" + j + ".0"));

			premium.add(calculatorComponent.getOutputEntry("PremiumCurrent" + j + ".0"));

			deathBenefit.add(calculatorComponent.getOutputEntry("DeathBenefitCurrent" + j + ".0"));

			cashValue.add(calculatorComponent.getOutputEntry("CashValueCurrent" + j + ".0"));

			cashSurrenderValue.add(calculatorComponent.getOutputEntry("CashSurrenderValueCurrent" + j + ".0"));
		}

		pageBean.putValues("durationCurr", (String[]) duration.toArray(new String[duration.size()]));
		pageBean.putValues("premiumCurr", (String[]) premium.toArray(new String[premium.size()]));
		pageBean.putValues("deathBenefitCurr", (String[]) deathBenefit.toArray(new String[deathBenefit.size()]));
		pageBean.putValues("cashValueCurr", (String[]) cashValue.toArray(new String[cashValue.size()]));
		pageBean.putValues("cashSurrenderValueCurr", (String[]) cashSurrenderValue.toArray(new String[cashSurrenderValue.size()]));

		appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		return "/engine/jsp/project.jsp";
    }

//	protected void showScriptCopyDialog(AppReqBlock aAppReqBlock) throws Exception {

//		PageBean aPageBean = new PageBean();

    	/* Iterator scriptNameEnum = ed.getScriptNames();
		Iterator scriptIdsEnum = ed.getScriptIds();

    	List scriptNames = new ArrayList();
    	List scriptIds   = new ArrayList();

    	while (scriptNameEnum.hasNext()) {

    		scriptNames.add((String) scriptNameEnum.next());
    		scriptIds.add(((Integer) scriptIdsEnum.next()).toString());
    	}

    	// What you would have got back from CalcComp/LookupComp
    	ScriptVO[] scriptVO = new ScriptVO[scriptIds.size()];

    	for (int i = 0; i < scriptVO.length; i++) {

    		scriptVO[i] = new ScriptVO(Integer.parseInt((String) scriptIds.get(i)),
    				                    (String) scriptNames.get(i),
    									 0,
    									  new Date(),
    									   new Date());
    	}

		// add list of scriptNames
		aPageBean.putValues("names",
						     scriptVO,
							  new String[] {"getScriptName"},
							   null);

		// add list of scriptIds
		aPageBean.putValues("ids",
						     scriptVO,
							  new String[] {"getScriptId"},
							   null);	*/

//		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", aPageBean);

//	}

	protected String doPayoutProjection(AppReqBlock appReqBlock) throws Exception {

        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

		PageBean pageBean = appReqBlock.
								getSessionBean("paramBean").
									getPageBean("baseParamBean");

		calculatorComponent.clearScriptProcessor();
		setParameterValuesForPayout(appReqBlock, pageBean);

		//set the key
		SessionBean paramBean = appReqBlock.getSessionBean("paramBean");

		int productStructureId = Integer.parseInt(paramBean.getValue("productStructureId"));

		//setting the rule Id to zero bec we have to change the process,event,eventType and ruleName
		//so it will get a new ruleId in Calculator component
 		int ruleId = 0;//Integer.parseInt(paramBean.getValue("ruleStructureId"));

		String processName = "Quote";
		String eventName = "";
		int param = Integer.parseInt(appReqBlock.getReqParm("purchaseAmount"));
		if (param == 0) {

			eventName = "PurchAmt";
		}
		else {

			eventName = "ModalAmt";
		}

		String paramString = appReqBlock.getReqParm("areaId");

		String eventTypeName = paramString;

		EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("effectiveDate"));

		String ruleName  = "Driver";

		setKeyValues(appReqBlock
	                  ,productStructureId
	                   ,ruleId
	                    ,processName
						 ,eventName
						  ,eventTypeName
						   ,effectiveDate.getFormattedDate()
							,ruleName);

		String projScriptName = null;
		projScriptName = calculatorComponent.getScriptName();
		if (projScriptName == null)
			throw new Exception("Please select a projection key");

     	calculatorComponent.resetScriptProcessor();
//        calculatorComponent.execScriptProcessor(); // Projections will have to be reconsidered. We want STATELESS interactions with the calc component
       //SRAMAM 09/2004 DOUBLE2DECIMAL
/*		pageBean.putValue("CertainDuration", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("CertainDuration"))));
		pageBean.putValue("ExclusionRatio", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("ExclusionRatio"))));
		pageBean.putValue("ExpectedReturn", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("ExpectedReturn"))));
		pageBean.putValue("Fees", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("Fees"))));
		pageBean.putValue("Loads", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("Loads"))));
		pageBean.putValue("PremiumTaxes", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("PremiumTaxes"))));
*/
        pageBean.putValue("CertainDuration", Util.formatDecimal("#####000.000####",new EDITBigDecimal(calculatorComponent.getOutputEntry("CertainDuration"))));
        pageBean.putValue("ExclusionRatio", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("ExclusionRatio"))));
        pageBean.putValue("ExpectedReturn", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("ExpectedReturn"))));
        pageBean.putValue("Fees", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("Fees"))));
        pageBean.putValue("Loads", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("Loads"))));
        pageBean.putValue("PremiumTaxes", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("PremiumTaxes"))));


		if (eventName.equals("PurchAmt")) {
            //SRAMAM 09/2004 DOUBLE2DECIMAL
			//pageBean.putValue("PurchaseAmount", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("PurchaseAmount"))));
            pageBean.putValue("PurchaseAmount", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("PurchaseAmount"))));
		}
		else {
            //SRAMAM 09/2004 DOUBLE2DECIMAL
			//pageBean.putValue("ModalAmount", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("ModalAmount"))));
            pageBean.putValue("ModalAmount", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("ModalAmount"))));
		}

		//pageBean.putValue("YearlyTaxableBenefit", Util.formatDecimal("#####000.000####", Double.parseDouble(calculatorComponent.getOutputEntry("YearlyTaxableBenefit"))));
        pageBean.putValue("YearlyTaxableBenefit", Util.formatDecimal("#####000.000####", new EDITBigDecimal(calculatorComponent.getOutputEntry("YearlyTaxableBenefit"))));

		appReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		return "/engine/jsp/payoutProjection.jsp";

	}

//***********************************************************
//*                 Helper Methods
//***********************************************************

    // Returns the script in a ScriptBean
    /* private ScriptBean getScriptByName(String scriptName)
            throws Exception {

        ScriptBean sb = new ScriptBean();
        DDGroupItem ddScript = ed.getDDScriptByName(scriptName);
        sb.setId(((DDNumber)ddScript.find(Reserved.SCRIPT_ID_UC)).getInt());
        sb.setName(scriptName);
        sb.setScript(ed.getScriptLines(sb.getId()));

        return sb;
    } */

	private String unlockScript(AppReqBlock appReqBlock) throws Exception {

        UserSession userSession = appReqBlock.getUserSession();

        userSession.unlockScript();

        return showScriptDefault(appReqBlock);
    }

    private String formatAddScriptLine(String dataIn) throws Exception  {

        int sepIndex = dataIn.indexOf(":");
        String source = dataIn.substring(0, sepIndex);
        String data =dataIn.substring(sepIndex + 1, dataIn.length());

        if (source == null)  {
            throw new Exception(INVALID_DRAG_DROP_REQUESTS);
        }

        if (source.equalsIgnoreCase("blank")) {
            return " ";

        }else if (source.equalsIgnoreCase("Instruction")) {
            return data;

        } else if (source.equalsIgnoreCase("Script")) {
            return "Call " + data + ":";

        } else if (source.equalsIgnoreCase("Table"))  {
            return "Activate  func:" + data;

        } else  {
            throw new Exception(INVALID_DRAG_DROP_SOURCE_REQUEST);
        }
    }

//	private java.lang.String getExpandedTree(engine.dm.DDGroupItem dd
//                                           ,java.lang.String s) {
//		// TO BE COMPLETE LATER
//
//		return null;
//	}

	/* private void uploadFile(AppReqBlock aAppReqBlock, ScriptBean aScriptBean)
            throws Exception {

	    MultipartRequest multi =
            new MultipartRequest(aAppReqBlock.getHttpServletRequest(), ".");

        Iterator files = multi.getFileNames();
        while (files.hasNext()) {
            String name = (String)files.next();
            String fileName = multi.getFilesystemName(name);
            int mark = fileName.indexOf(".");
			String fileName2 = fileName.substring(0, mark);

			String type = multi.getContentType(name);
            if (fileName == null)  {
                throw new Exception(Reserved.PLEASE_SELECT_OR_ENTER_FILE_NAME);
            }

            List dataGroups = null;
            DDGroupItem aGroup = null;
	       	StringBuilder stringBuffer = new StringBuilder();
			XMLReader r = new XMLReader(multi.getFile(name));
            dataGroups = r.readXML();

        	if(dataGroups.size() == 0) {
				throw new Exception(Reserved.NO_SCRIPT_FOUND);
			}


		DDGroupItem aScriptGroup = (DDGroupItem) dataGroups.get(0);
		StringBuilder scriptGroup = new StringBuilder();
		Iterator enumeration = (aScriptGroup).elements();
		while (enumeration.hasNext()){
			DDString ddScript = (DDString)enumeration.next();
			String script = ddScript.getString();
			scriptGroup.append(script);
			scriptGroup.append("\n");
		}
		String newScript = scriptGroup.toString();
		ed.saveScript(0, fileName2, newScript);
	    aScriptBean.setScriptTree(ed.getScriptTree());
        }
    } */

	private void checkForDuplicateScriptName(String scriptName) throws Exception {

		/* Iterator enum = ed.getScriptNames();
		while (enum.hasNext()) {
			String tempScriptName = enum.next().toString();
			if (tempScriptName.equalsIgnoreCase(scriptName)) {
				throw new Exception(Reserved.SCRIPT_NAME_ALREADY_EXISTS);
			}
		} */
	}



    /**
     * Returns a String containing a script line
     * <p>
     * @param appReqBlock A reference to AppReqBlock
     * @return String containing a script line created from
     *         the operator, operand type, and operand name
     *         extracted from the AppReqBlock with a new line
     *         character appended to the end of it
     */
    private String convertScriptToText(AppReqBlock appReqBlock)  {

        StringBuilder scriptText = new StringBuilder();
        boolean done=false;
        for (int i=1; !done; i++)  {

            // Get script line data
            String operator    = appReqBlock.getReqParm("operator" +i);
            String operandType = appReqBlock.getReqParm("operandType" + i);
            String operandName = appReqBlock.getReqParm("operandName" + i);
            if (operator == null)  {
                done = true;
                continue;
            }

            // Convert Script Line
            scriptText.append(convertScriptLine(operator,operandType,operandName));

            // Append a Linefeed
            scriptText.append("\n");
        }

        return scriptText.toString();
    }

    /**
     * Returns a String containing a script line
     * <p>
     * @param operator A String containing the value of the
     *        operator in a given script line.
     * @param operandType A String containing the value of
     *        the operand type in a given script line.
     * @param operandName A String containing the value of
     *        the operand name in a given script line.
     * @return String containing the operator, operand type
     *         and operand name converted into a script line
     *         containing the special characters denoting
     *         comments and labels
     */
    private String convertScriptLine(String operator,
                                      String operandType,
                                       String operandName)  {

        StringBuilder scriptLine = new StringBuilder();

        // Process Operator
        if (operator == null)  {
            return null;
        }
        if (operator.equalsIgnoreCase("COMMENT")) {
            scriptLine.append("//");

        } else if (operator.equalsIgnoreCase("LABEL"))  {
            // do not include operator
        } else  {
            scriptLine.append(operator + " ");
        }

        // Process Operand Type
        if ((operandType != null) && (operandType.trim().length() > 0)) {

            scriptLine.append(operandType + ":");
        }

        // Process Operand Name
        if ((operandName != null) && (operandName.trim().length() > 0)) {

             scriptLine.append(operandName);
        }

        return scriptLine.toString();
    }
    /**
     * Returns a boolean value reflecting if the selected
     * script has a valid script name
     * <p>
     * @param name A String containing a script name
     * @throws Exception Script name is required (if the
     *         String passed in its constructor is an empty
     *         String)
     * @throws Exception Script name must begin with a letter
     *         (if the String passed in its constructor begins
     *         with a character other than a letter)
     * @throws Exception Script name can only contain letters
     *         and digits (if the String passed in its
     *         constructor contains characters other than
     *         letters and numbers
     */


    private void checkForValidScriptName(String name) throws Exception {

        if (name.trim().length() == 0)  {
            throw new Exception("Script name is required");
        }
        if (! Character.isLetter(name.charAt(0)))  {
            throw new Exception("Script name must begin with a letter");
        }

        for (int i = 1; i < name.length(); i++) {
            if (! Character.isLetterOrDigit(name.charAt(i)))  {
                throw new Exception(
                    "Script name can only contain letters and digits");
            }
        }
    }

	private void setKeyValues(AppReqBlock appReqBlock
	                          ,int productStructureId
	                           ,int ruleId
	                            ,String processName
								 ,String eventName
								  ,String eventTypeName
								   ,String effectiveDate
								    ,String ruleName)
                             throws Exception {

        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

		calculatorComponent.setDrivingCRStructure(productStructureId
												 ,ruleId
												 ,processName
												 ,eventName
												 ,eventTypeName
												 ,effectiveDate
												 ,ruleName);

	}

//**End*Helper*Methods**************************************

//***********************************************************
//*         Viewer Methods For Right Side of the Frame
//***********************************************************

	protected String showStackViewer(AppReqBlock appReqBlock) {

		SessionBean paramBean  = appReqBlock.getSessionBean("paramBean");

		PageBean debugScriptBean = paramBean.getPageBean("debugScriptBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.setViewerModeSP("0");

		String viewerMode = analyzerComponent.getViewerMode();

		debugScriptBean.putValue("viewerMode", viewerMode);

		String[] dataStackValues = analyzerComponent.getDataStack();

		debugScriptBean.putValues("dataStackValues", dataStackValues, new String[] {"toString"}, null);

//		String[] workingStorageValues = analyzerComponent.getWS();
//
//		debugScriptBean.putValues("workingStorageValues", workingStorageValues, new String[] {"toString"}, null);

        appReqBlock.getHttpSession().setAttribute("workingStorage", analyzerComponent.getWS());

		return DEBUGVIEW_SCREEN;
	}

	protected String showFunctionViewer(AppReqBlock appReqBlock) {

		SessionBean paramBean  = appReqBlock.getSessionBean("paramBean");

		PageBean debugScriptBean = paramBean.getPageBean("debugScriptBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.setViewerModeSP("1");

		String viewerMode = analyzerComponent.getViewerMode();

		debugScriptBean.putValue("viewerMode", viewerMode);

		String[] functionTables = analyzerComponent.getFunctions();

		debugScriptBean.putValues("functionTables", functionTables, new String[] {"toString"}, null);

		String selectedFunction = appReqBlock.getReqParm("function");

		if ((selectedFunction != null) && (selectedFunction.length() > 0)) {

			String[] functionEntries = analyzerComponent.getFunctionEntry(selectedFunction);

			debugScriptBean.putValues("functionEntries", functionEntries, new String[] {"toString"}, null);
		}

		return DEBUGVIEW_SCREEN;
	}

    protected String showVectorViewer(AppReqBlock appReqBlock) {

		SessionBean paramBean  = appReqBlock.getSessionBean("paramBean");

		PageBean debugScriptBean = paramBean.getPageBean("debugScriptBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.setViewerModeSP("2");

		String viewerMode = analyzerComponent.getViewerMode();

		debugScriptBean.putValue("viewerMode", viewerMode);

		String[] vectorTables = analyzerComponent.getVectorTable();

		debugScriptBean.putValues("vectorTable", vectorTables);

		String selectedVector = appReqBlock.getReqParm("vector");

		if ((selectedVector != null) && (selectedVector.length() > 0)) {

			String[] vectorEntries = analyzerComponent.getVectorTableEntry(selectedVector);

			debugScriptBean.putValues("vectorEntries", vectorEntries);
		}

		return DEBUGVIEW_SCREEN;
	}
    
    protected String showDocumentViewer(AppReqBlock appReqBlock) {

		SessionBean paramBean  = appReqBlock.getSessionBean("paramBean");
		PageBean debugScriptBean = paramBean.getPageBean("debugScriptBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");
		analyzerComponent.setViewerModeSP("4");

		String viewerMode = analyzerComponent.getViewerMode();

		debugScriptBean.putValue("viewerMode", viewerMode);

		HashMap<String, String> documents = analyzerComponent.getDocumentXML();

		HttpServletRequest req = appReqBlock.getHttpServletRequest();
		req.getSession().setAttribute("documents", documents);
		
		return DEBUGVIEW_SCREEN;
	}    

	protected String showOutputViewer(AppReqBlock appReqBlock) {

		SessionBean paramBean  = appReqBlock.getSessionBean("paramBean");

		PageBean debugScriptBean = paramBean.getPageBean("debugScriptBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.setViewerModeSP("3");

		String viewerMode = analyzerComponent.getViewerMode();

		debugScriptBean.putValue("viewerMode", viewerMode);

		String[] outputData = analyzerComponent.getOutput();

		debugScriptBean.putValues("outputData", outputData, new String[] {"toString"}, null);

		return DEBUGVIEW_SCREEN;
	}

	private void setParameterValues(AppReqBlock aAppReqBlock, PageBean pageBean) throws Exception {

        Map clientInformation = new HashMap();

        List owners          = new ArrayList();
        List annuitants      = new ArrayList();
        List payees          = new ArrayList();

        // Make the baseParamBean represents current parameter selections

        Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

        String param = aAppReqBlock.getReqParm("interestRate");
        double rate = Double.parseDouble(param) / 100;
        param = rate + "";

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("interestRate", aAppReqBlock.getReqParm("interestRate"));
            analyzerComponent.addParamEntry("interestrate", param);
        }

        param = aAppReqBlock.getReqParm("areaId");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("areaId", aAppReqBlock.getReqParm("areaId"));
            analyzerComponent.addParamEntry("residentstate", param);
        }

        param = aAppReqBlock.getReqParm("premPayTerm");

        if ((param != null) && (param.trim().length() > 0))  {

            pageBean.putValue("premPayTerm", aAppReqBlock.getReqParm("premPayTerm"));
            analyzerComponent.addParamEntry("prempayterm", param);

        } else  {

            pageBean.putValue("premPayTerm", "NA");
            analyzerComponent.addParamEntry("prempayterm", "NA");
        }

        param = aAppReqBlock.getReqParm("option7702Id");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("option7702Id", aAppReqBlock.getReqParm("option7702Id"));
            analyzerComponent.addParamEntry("option7702", param);
        }

        param = aAppReqBlock.getReqParm("deathBenOptId");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("deathBenOptId", aAppReqBlock.getReqParm("deathBenOptId"));
            analyzerComponent.addParamEntry("deathbenefitopt", param);
        }

        param = aAppReqBlock.getReqParm("coverageAmount");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("coverageAmount", aAppReqBlock.getReqParm("coverageAmount"));
            analyzerComponent.addParamEntry("coverageamount", param);
        }

        analyzerComponent.addParamEntry("statuscode", "Q");

        param = aAppReqBlock.getReqParm("issueAge");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("issueAge", aAppReqBlock.getReqParm("issueAge"));
            analyzerComponent.addParamEntry("issueage", param);
        }
        clientInformation.put("issueage",param);

        param = aAppReqBlock.getReqParm("genderId");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("genderId", aAppReqBlock.getReqParm("genderId"));
            analyzerComponent.addParamEntry("sex", param);
        }

        clientInformation.put("sex",param);

        param = aAppReqBlock.getReqParm("classId");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("classId", aAppReqBlock.getReqParm("classId"));
            analyzerComponent.addParamEntry("class", param);
        }

        clientInformation.put("class",param);

        param = aAppReqBlock.getReqParm("relationshipInd");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("relationshipId", aAppReqBlock.getReqParm("relationshipInd"));//"ANN"
            analyzerComponent.addParamEntry("relationshipind", param);
        }

        clientInformation.put("relationshipind",param);

        //set client counter according to relationShip
        if (param.equals("ANN"))
        {
            analyzerComponent.addParamEntry("annuitantcounter","1");
            analyzerComponent.addParamEntry("ownercounter", "0");
            analyzerComponent.addParamEntry("payeecounter", "0");
        }
        else if (param.equals("OWN"))
        {
            analyzerComponent.addParamEntry("ownercounter", "1");
            analyzerComponent.addParamEntry("annuitantcounter", "0");
            analyzerComponent.addParamEntry("payeecounter", "0");
        }
        else if (param.equals("PAY"))
        {
            analyzerComponent.addParamEntry("payeecounter", "1");
            analyzerComponent.addParamEntry("ownercounter", "0");
            analyzerComponent.addParamEntry("annuitantcounter", "0");
        }

        clientInformation.put("relationshipind",param);

        param = aAppReqBlock.getReqParm("tableRating");

        if ((param != null) && (param.trim().length() > 0))  {

            pageBean.putValue("tableRating", aAppReqBlock.getReqParm("tableRating"));
            analyzerComponent.addParamEntry("tablerating", param);
            clientInformation.put("tablerating", param);

        } else  {

            pageBean.putValue("tableRating", "NA");
            analyzerComponent.addParamEntry("tablerating", "NA");
            clientInformation.put("tablerating", "NA");
        }

        param = aAppReqBlock.getReqParm("flatExtra");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("flatExtra", aAppReqBlock.getReqParm("flatExtra"));
            analyzerComponent.addParamEntry("flatextra", param);
        }
        clientInformation.put("flatextra", param);

        param = aAppReqBlock.getReqParm("flatAge");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("flatAge", aAppReqBlock.getReqParm("flatAge"));
            analyzerComponent.addParamEntry("flatage", param);
        }
        clientInformation.put("flatage",param);

        param = aAppReqBlock.getReqParm("flatDuration");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("flatDuration", aAppReqBlock.getReqParm("flatDuration"));
            analyzerComponent.addParamEntry("flatdur", param);
        }
        clientInformation.put("flatdur",param);

        param = aAppReqBlock.getReqParm("percentExtra");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("percentExtra", aAppReqBlock.getReqParm("percentExtra"));
            analyzerComponent.addParamEntry("percentextra", param);
        }
        clientInformation.put("percentextra", param);

        param = aAppReqBlock.getReqParm("percentAge");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("percentAge", aAppReqBlock.getReqParm("percentAge"));
            analyzerComponent.addParamEntry("percentage", param);
        }
        clientInformation.put("percentage", param);

        param = aAppReqBlock.getReqParm("percentDuration");

        if ((param != null) && (param.trim().length() > 0))  {
            pageBean.putValue("percentDuration", aAppReqBlock.getReqParm("percentDuration"));
            analyzerComponent.addParamEntry("percentdur", param);
        }
        clientInformation.put("percentdur",param);

        EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("effectiveDate"));
        EDITDate terminationDate = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("terminationDate"));

        pageBean.putValue("effectiveDateMonth", effectiveDate.getFormattedMonth());
        pageBean.putValue("effectiveDateDay",   effectiveDate.getFormattedDay());
        pageBean.putValue("effectiveDateYear",  effectiveDate.getFormattedYear());

        pageBean.putValue("terminationDateMonth", terminationDate.getFormattedMonth());
        pageBean.putValue("terminationDateDay",   terminationDate.getFormattedDay());
        pageBean.putValue("terminationDateYear",  terminationDate.getFormattedYear());

        analyzerComponent.addParamEntry("effectivedate", effectiveDate.getFormattedDate());

        analyzerComponent.addParamEntry("terminationdate", terminationDate.getFormattedDate());


        //add client information
        owners.add(clientInformation);
        annuitants.add(clientInformation);
        payees.add(clientInformation);

        analyzerComponent.addParamEntry("annuitants", annuitants);

        analyzerComponent.addParamEntry("payees", payees);

        analyzerComponent.addParamEntry("owners", owners);

	}


	private void setParameterValuesForPayout(AppReqBlock appReqBlock,
                                              PageBean pageBean) throws Exception {

		Map clientInformation = new HashMap();
		List annuitant = new ArrayList();
		List payee = new ArrayList();

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");
//        Calculator calculatorComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

		analyzerComponent.addParamEntry("transactionridertype", "BASE");

		EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("effectiveDate"));

		pageBean.putValue("effectiveDateMonth", effectiveDate.getFormattedMonth());
		pageBean.putValue("effectiveDateDay",   effectiveDate.getFormattedDay());
		pageBean.putValue("effectiveDateYear",  effectiveDate.getFormattedYear());

        analyzerComponent.addParamEntry("effectivedate", effectiveDate.getFormattedDate());

		clientInformation.put("effectivedate", effectiveDate.getFormattedDate());

		EDITDate startDate = DateTimeUtil.getEDITDateFromMMDDYYYY(appReqBlock.getReqParm("startDate"));

		pageBean.putValue("startDateMonth", startDate.getFormattedMonth());
		pageBean.putValue("startDateDay",   startDate.getFormattedDay());
		pageBean.putValue("startDateYear",  startDate.getFormattedYear());

        analyzerComponent.addParamEntry("startdate", startDate.getFormattedDate());

		String param = appReqBlock.getReqParm("areaId");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("areaId", appReqBlock.getReqParm("areaId"));
			analyzerComponent.addParamEntry("issuestate", param);
		}

		param = appReqBlock.getReqParm("nonQualInd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("nonQualInd", appReqBlock.getReqParm("nonQualInd"));
			analyzerComponent.addParamEntry("nonqualind", param);
		}

		param = appReqBlock.getReqParm("exchangeInd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("exchangeInd", appReqBlock.getReqParm("exchangeInd"));
			analyzerComponent.addParamEntry("exchangeind", param);
		}

		param = appReqBlock.getReqParm("annuityOption");

		String payoutOption = null;
        if (param.equalsIgnoreCase("LOA")) {

            payoutOption = "Life";
        }

        else if (param.equalsIgnoreCase("PCA")) {

            payoutOption = "PerCert";
        }

        else if (param.equalsIgnoreCase("LPC")) {

            payoutOption = "LifePerCert";
        }

        else if (param.equalsIgnoreCase("JSA")) {

            payoutOption = "JTLife";
        }

		else if (param.equalsIgnoreCase("JPC")) {

            payoutOption = "JTPerCert";
        }

        else if (param.equalsIgnoreCase("LCR")) {

            payoutOption = "LifeCashRefund";
        }

        else if (param.equalsIgnoreCase("AMC")) {

            payoutOption = "AmtCert";
        }

        else if (param.equalsIgnoreCase("INR")) {

            payoutOption = "InstRef";
        }

        else if (param.equalsIgnoreCase("INT")) {

            payoutOption = "IntOnly";
        }

        else if (param.equalsIgnoreCase("TML")) {

            payoutOption = "TmpLife";
        }

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("annuityOptionId", appReqBlock.getReqParm("annuityOption"));
			analyzerComponent.addParamEntry("payoutoption", payoutOption);
		}

		param = appReqBlock.getReqParm("certainPeriod");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("certainPeriod", appReqBlock.getReqParm("certainPeriod"));
			analyzerComponent.addParamEntry("certainperiod", param);
		}

		param = appReqBlock.getReqParm("frequency");

        String paymentFrequency = null;

        if (param.equalsIgnoreCase("AN")) {

            paymentFrequency = "Annual";
        }

        else if (param.equalsIgnoreCase("SA")) {

            paymentFrequency = "Semi";
        }

        else if (param.equalsIgnoreCase("QU")) {

            paymentFrequency = "Quarterly";
        }

        else if (param.equalsIgnoreCase("BM")) {

            paymentFrequency = "BiMonthly";
        }

        else if (param.equalsIgnoreCase("MO")) {

            paymentFrequency = "Monthly";
        }

		else if (param.equalsIgnoreCase("BW")) {

            paymentFrequency = "BiWeekly";
        }

        else if (param.equalsIgnoreCase("WE")) {

            paymentFrequency = "Weekly";
        }

		if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("frequencyId", appReqBlock.getReqParm("frequency"));
				analyzerComponent.addParamEntry("frequency", paymentFrequency);
		}

		param = appReqBlock.getReqParm("payoutBasis");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("payoutBasisId", appReqBlock.getReqParm("payoutBasis"));
			analyzerComponent.addParamEntry("payoutbasis", param);
		}

		param = appReqBlock.getReqParm("purchaseAmount");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("purchaseAmount", appReqBlock.getReqParm("purchaseAmount"));
			analyzerComponent.addParamEntry("amount", param);
		}

		param = appReqBlock.getReqParm("paymentAmount");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("paymentAmount", appReqBlock.getReqParm("paymentAmount"));
			analyzerComponent.addParamEntry("modalamount", param);
		}

		param = appReqBlock.getReqParm("post86Investment");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("post86Investment", appReqBlock.getReqParm("post86Investment"));
			analyzerComponent.addParamEntry("post86investment", param);
		}

		param = appReqBlock.getReqParm("costBasis");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("costBasis", appReqBlock.getReqParm("costBasis"));
			analyzerComponent.addParamEntry("originvestment", param);
		}

		param = appReqBlock.getReqParm("issueAge");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("issueAge", appReqBlock.getReqParm("issueAge"));
			analyzerComponent.addParamEntry("issueage", param);
		}

		clientInformation.put("issueage",param);

		param = appReqBlock.getReqParm("sex");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("genderId", appReqBlock.getReqParm("sex"));
			analyzerComponent.addParamEntry("sex", param);
		}
		clientInformation.put("sex",param);

		param = appReqBlock.getReqParm("classIds");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("classId", appReqBlock.getReqParm("classIds"));
			analyzerComponent.addParamEntry("class", param);
		}

		param = appReqBlock.getReqParm("relationshipInd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("relationshipInd", appReqBlock.getReqParm("relationshipInd"));//"ANN"
			analyzerComponent.addParamEntry("relationshipind", param);
		}

		param = appReqBlock.getReqParm("disbursementSource");
		String disbursementSource = null;
		if (param.equalsIgnoreCase("C")) {

        	disbursementSource = "Check";
        }
        else if (param.equalsIgnoreCase("E")) {

        	disbursementSource = "EFT";
        }
		else {

            disbursementSource = "Hold";
        }

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("disburseSourceId", appReqBlock.getReqParm("disbursementSource"));
			analyzerComponent.addParamEntry("disbursementsource", disbursementSource);
		}
		clientInformation.put("disbursementsource",disbursementSource);

		//set the AnnuitantCounter parameter to 1 according to specs
		analyzerComponent.addParamEntry("annuitantcounter", "1");

		//set the PayeeCounter parameter to 1 according to specs
		analyzerComponent.addParamEntry("payeecounter", "1");

		annuitant.add(clientInformation);
		payee.add(clientInformation);

		analyzerComponent.addParamEntry("annuitants", annuitant);

		analyzerComponent.addParamEntry("payees", payee);

		analyzerComponent.addParamEntry("selectedindex", "1");
	}

    protected String showEditingExceptionDialog(AppReqBlock appReqBlock) throws Exception{

        PortalEditingException editingException = (PortalEditingException) appReqBlock.getHttpSession().getAttribute("portalEditingException");

        // Remove editingException from Session (to clear it), and move it to request scope.
        appReqBlock.getHttpSession().removeAttribute("portalEditingException");

        appReqBlock.getHttpServletRequest().setAttribute("portalEditingException", editingException);

        return EDITING_EXCEPTION_DIALOG;
    }

    protected String showAssociatedRulesForScript(AppReqBlock appReqBlock) throws Exception {

        String scriptPK = initParam(appReqBlock.getReqParm("scriptId"), "0");

        Lookup calcLookup = new LookupComponent();
        ScriptVO[] scriptVOs = calcLookup.findScriptVOByPK(Long.parseLong(scriptPK), false, null);
        RulesVO[] rulesVOs = calcLookup.findRulesVOsByScriptPK(Long.parseLong(scriptPK), false, null);
        UIRulesVO[] uiRulesVOs = new ProductStructureTran().buildUIRulesVOs(rulesVOs);

        appReqBlock.getHttpServletRequest().setAttribute("uiRulesVOs", uiRulesVOs);
        appReqBlock.getHttpServletRequest().setAttribute("scriptVOs", scriptVOs);

        return ASSOCIATED_RULES_FOR_SCRIPT_DIALOG;
    }

    public void setValues(Analyzer analyzerComponent, AppReqBlock appReqBlock) throws Exception {

        PageBean debugScriptBean = appReqBlock.
									 getSessionBean("paramBean").
									   getPageBean("debugScriptBean");


        String[] dataStackValues = analyzerComponent.getDataStack();

        //debugScriptBean.putValues("dataStackValues", dataStackValues, new String []	{"toString"}, null);
        debugScriptBean.putValues("dataStackValues", dataStackValues);

//        String[] workingStorageValues = analyzerComponent.getWS();
//
//        debugScriptBean.putValues("workingStorageValues", workingStorageValues, new String [] {"toString"}, null);

        appReqBlock.getHttpSession().setAttribute("workingStorage", analyzerComponent.getWS());

        String[] functionTables = analyzerComponent.getFunctions();

        //debugScriptBean.putValues("functionTables", functionTables, new String [] {"toString"}, null);
        debugScriptBean.putValues("functionTables", functionTables);

        String selectedFunction = appReqBlock.getReqParm("function");

        if ((selectedFunction != null) && (selectedFunction.length() > 0))  {

            String[] functionEntries = analyzerComponent.getFunctionEntry(selectedFunction);

            //debugScriptBean.putValues("functionEntries", functionEntries, new String [] {"toString"}, null);
            debugScriptBean.putValues("functionEntries", functionEntries);
        }

        String[] vectorTables = analyzerComponent.getVectorTable();

		debugScriptBean.putValues("vectorTable", vectorTables);

		String selectedVector = appReqBlock.getReqParm("vector");

		if ((selectedVector != null) && (selectedVector.length() > 0)) {

			String[] vectorEntries = analyzerComponent.getVectorTableEntry(selectedVector);

			debugScriptBean.putValues("vectorEntries", vectorEntries);
		}

//        String[] outputData = analyzerComponent.getOutput();
//
//        debugScriptBean.putValues("outputData", outputData,new String [] {"toString"}, null);
//
        getInstructionPointers(appReqBlock);
    }

    protected String getScriptCallChain(AppReqBlock appReqBlock) throws Exception {

        SessionBean paramBean = appReqBlock.getSessionBean("paramBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) appReqBlock.getHttpSession().getAttribute("analyzerComponent");

 		ScriptChainNodeWrapper scriptChain = analyzerComponent.getScriptCallChain(null, true);

        if (scriptChain == null) {

           String message = "Must select Payout Parameters before viewing call chain.";
           appReqBlock.getHttpServletRequest().setAttribute("callChainMessage", message);
           return "/engine/jsp/callChainMessage.jsp";
        }

        else {

            CallChainVO callChainVO = new CallChainVO(scriptChain);

            appReqBlock.getHttpServletRequest().setAttribute("callChainVO", callChainVO);

            return "/engine/jsp/callChain.jsp";
        }
	}

    protected String showJumpToDialog(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpServletRequest().setAttribute("jumpToTarget", appReqBlock.getReqParm("jumpToTarget"));

        return JUMP_TO_DIALOG;
    }

    protected String showCancelScriptChangesDialog(AppReqBlock appReqBlock)
    {
        return CANCEL_SCRIPT_CHANGES_DIALOG;
    }

    protected String cancelScriptChanges(AppReqBlock appReqBlock) throws Exception
    {
        UserSession userSession = appReqBlock.getUserSession();

        userSession.unlockScript();

        return showScriptDefault(appReqBlock);
	}

    /**
     * Search on the filtere entered to set the summary area to that script
     * @param appReqBlock
     * @return
     */
    private String getSelectedScript(AppReqBlock appReqBlock) throws Exception
    {
        String scriptFilter = Util.initString(appReqBlock.getReqParm("scriptFilter"), null);
        String message = null;

        if (scriptFilter != null)
        {
            ScriptVO scriptVO = Script.findByPartialScriptName(scriptFilter);

            if (scriptVO == null)
            {
                message = "Script Selected Not Found";
            }

            else
            {
                appReqBlock.getHttpServletRequest().setAttribute("scriptVO", scriptVO);
                appReqBlock.setReqParm("scriptId", scriptVO.getScriptPK()+ "");
            }

            appReqBlock.getHttpServletRequest().setAttribute("message", message);
        }

        return showScript(appReqBlock);
    }
}