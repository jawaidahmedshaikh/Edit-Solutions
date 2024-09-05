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
import edit.common.vo.CodeTableVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.FundVO;
import edit.common.vo.RulesVO;
import edit.portal.common.transactions.Transaction;
import engine.business.Analyzer;
import engine.business.Calculator;
import engine.business.Lookup;
import engine.component.LookupComponent;
import fission.beans.PageBean;
import fission.beans.SessionBean;
import fission.dm.valueobject.CallChainVO;
import fission.global.AppReqBlock;
import fission.utility.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ParamTran is used to handle parameter requests for Debugging and for
 * Projections
 */

public class ParamTran extends Transaction {



    public String execute(AppReqBlock aAppReqBlock) throws Exception  {

		String action = aAppReqBlock.getReqParm("action");

		if (action.equals("showDefaultBaseParametersScreen"))  {

			return showDefaultBaseParametersScreen(aAppReqBlock);
		}
		else if(action.equals("showBaseParametersScreen")){

			return showBaseParametersScreen(aAppReqBlock);
		}
		else if (action.equals("showRiderSelectionScreen"))  {

		   return showRiderSelectionScreen(aAppReqBlock);
		}
		else if (action.equals("addRider")) {

			return addRider(aAppReqBlock);
		}
		else if (action.equals("removeRider"))  {

			return removeRider(aAppReqBlock);
		}
		else if (action.equals("applyBaseParameters")) {

			return applyBaseParameters(aAppReqBlock);
		}
		else if (action.equals("clearBaseParameters")) {

			return clearBaseParameters(aAppReqBlock);
		}
		else if (action.equals("doProjection")) {

			return doProjection(aAppReqBlock);
		}
		else if (action.equals("showDebugParametersScreen")) {

			return showDebugParametersScreen(aAppReqBlock);
		}
		else if (action.equals("applyDebugParameters")) {

			return applyDebugParameters(aAppReqBlock);
		}
		else if (action.equals("saveDebugParameters")) {

			return saveDebugParameters(aAppReqBlock);
		}
		else if (action.equals("clearDebugParameters")) {

			return clearDebugParameters(aAppReqBlock);
		}
		else if (action.equals("cancelDebugParameters")) {

			return cancelDebugParameters(aAppReqBlock);
		}
		else if (action.equals("saveRiderParameters")) {

			return saveRiderParameters(aAppReqBlock);
		}
		else if (action.equals("clearRiderParameters")) {

			return clearRiderParameters(aAppReqBlock);
		}
		else if (action.equals("cancelRiderParameters")) {

			return cancelRiderParameters(aAppReqBlock);
		}
		else if (action.equals("setKey")) {

 			return setKey(aAppReqBlock);
		}
        if (action.equals("showKeySelectionDialog"))  {

        	return showKeySelectionDialog(aAppReqBlock);
       	}
		else if (action.equals("showAttachedRules")) {

			return showAttachedRules(aAppReqBlock);
		}
		else if (action.equals("editRider")) {

			return editRider(aAppReqBlock);
		}
		else if (action.equals("showDefaultDebugBaseParametersScreen")) {

			return showDefaultDebugBaseParametersScreen(aAppReqBlock);
		}
		else if (action.equals("callScriptChain")){

			return getScriptCallChain(aAppReqBlock);
		}
		else if (action.equals("showPayoutParametersScreen")){

			return showPayoutDefaultParametersScreen(aAppReqBlock);
		}
		else if (action.equals("savePayoutParameters")){

			return savePayoutParameters(aAppReqBlock);
		}
		else if (action.equals("applyPayoutParameters")){

			return applyPayoutParameters(aAppReqBlock);
		}
		else if (action.equals("clearPayoutParameters")) {

			return clearPayoutParameters(aAppReqBlock);
		}
		else if (action.equals("cancelPayoutParameters")) {

			return cancelPayoutParameters(aAppReqBlock);
		}
		else if(action.equals("showPayoutBaseParametersScreen"))  {

			return showPayoutBaseParametersScreen(aAppReqBlock);
		}
		else if (action.equals("applyPayoutBaseParameters")){

			return applyPayoutBaseParameters(aAppReqBlock);
		}
		else if (action.equals("clearPayoutBaseParameters")){

			return clearPayoutBaseParameters(aAppReqBlock);
		}
		else if (action.equals("showTransactionParametersScreen")){

			 return showTransactionParametersScreen(aAppReqBlock);
		}
		else if (action.equals("saveTransactionParameters")){

			return saveTransactionParameters(aAppReqBlock);
		}
		else if (action.equals("applyTransactionParameters")){

			return applyTransactionParameters(aAppReqBlock);
		}
        else {
            throw new Exception("Invalid Action: " + action);
        }
    }

	/*
	* This method is called when Projection for Life Contract is selected
	* and at that time we have to display the default parameters
	*/
	protected String showDefaultBaseParametersScreen(AppReqBlock aAppReqBlock) throws Exception  {

		// This starts a fresh session for the base parameter screen.
		// paramBean will store all parameters from base, rider, and debug,
		// as well as store the driving key data.
		//
		// A mode is always supplied to this method, and it is stored
		// throughout the remaing debug screens. It should only be set in
		// in this method.

		PageBean baseParamBean = new PageBean();

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");
        paramBean.clearState();

		paramBean.putValue("projectionMode", "Base Parameters");

		//to distinguish between debug-- payout params and life contract params
		paramBean.putValue("debugContractMode", "LifeContract");

		paramBean.putValue("enableRidersLink", "false");

		// The paramBean stores pageBeans. Each pageBean stores
		// the parameters for a particular screen such as rider, debug, etc.
		paramBean.putPageBean("baseParamBean", baseParamBean);

		// JSP expects the paramBean and codeTableBean (in session)
		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", baseParamBean);

		return "/engine/jsp/params.jsp";
	}

	protected String showBaseParametersScreen(AppReqBlock aAppReqBlock) throws Exception {

        PageBean pageBean;

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		String projectionMode = paramBean.getValue("projectionMode");

		if (projectionMode.equalsIgnoreCase("Base Parameters"))  {

				pageBean = aAppReqBlock.
							 getSessionBean("paramBean").
							  getPageBean("baseParamBean");
		} else  {

				pageBean = aAppReqBlock.
							 getSessionBean("paramBean").
							  getPageBean("debugParamBean");
		}
        //SRAMAM 09/2004 DOUBLE2DECIMAL
        //double interestRate = Double.parseDouble(pageBean.getValue("interestRate"));
        EDITBigDecimal interestRate = new EDITBigDecimal(pageBean.getValue("interestRate"));
        pageBean.putValue("interestRate", Util.formatDecimal("###0.00#", interestRate));

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		return "/engine/jsp/params.jsp";
	}

	protected String showRiderSelectionScreen(AppReqBlock aAppReqBlock) throws Exception  {

		return "/engine/jsp/riderSelection.jsp";
	}

	protected String addRider(AppReqBlock aAppReqBlock) throws Exception {

		/* Adding Rider is not just addding Rider to the selected
		rider list and show it on the screen, It also includes adding
		default parameters for selected rider to the session as well as script
		processor. The next two steps we have to do onClick() of add button
		bcos while removing the rider from selected rider list we are clearing/
		removing values from session as well as script processor. */

		// The list of selected riders will be held within the paramBean.

		// Adding selected rider to selected rider list.

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");
        SessionBean availableRidersBean = aAppReqBlock.getSessionBean("availableRidersBean");

		String riderId = aAppReqBlock.getReqParm("financialType");
		String rider   = aAppReqBlock.getReqParm("rider");

		String[] availableRiders = availableRidersBean.getValues("riders");

		if ((availableRiders != null) && (availableRiders.length > 0))  {

			for(int i=0; i<availableRiders.length; i++)  {

				if (isSelected(aAppReqBlock, rider))  {

					if ((riderId.startsWith("TERM")) ||
						(riderId.startsWith("INCREASE")))  {

						paramBean.addToValues("selectedRiderIds", riderId);
						paramBean.addToValues("selectedRiders", rider);
						break;

					} else  {

						throw new Exception(
                        "Only one selection is allowed for this rider - " + rider);
					}

				} else  {

					paramBean.addToValues("selectedRiderIds", riderId);
					paramBean.addToValues("selectedRiders", rider);
					break;
				}
			}
		}

        else {

            paramBean.addToValues("selectedRiderIds", riderId);
            paramBean.addToValues("selectedRiders", rider);
        }

		// Adding default parameters to the session.
		// This is just for sequence. bcos while removing rider, we remove
		// parameter values from session i.e page bean.

		PageBean riderBean = paramBean.getPageBean(rider);

		// add page bean to session bean

		paramBean.putPageBean(rider, riderBean);

		// Add default values to script processor.
		// to add default values we need rider name and selected index
		// but you can not get selected index before adding rider to selected riders
		// but obviously selected index would be size of selected riders length.

		int index = (paramBean.getValues("selectedRiders")).length;

        Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");
		// add 1 bcos always the index of base parameters is zero.
		//index += 1;
		// we need not add 1 bcos actually we are getting size of array
		// which is already one more than the selected index.

		// Map riderParams =
					calcComponent.getFinancialTypeData(riderId, index);


		/* You need not put the page bean in the request because available rider
		   list is stored in the codeTableBean which is a session bean and
		   selected riders are stored in the paramBean which is also a session bean */

		return "/engine/jsp/riderSelection.jsp";
	}

	private boolean isSelected(AppReqBlock aAppReqBlock, String riderEntry)  {

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		String[] selectedRiders = paramBean.getValues("selectedRiders");

		if (selectedRiders != null)  {

			for(int i=0; i<selectedRiders.length; i++)  {

				if(selectedRiders[i].equalsIgnoreCase(riderEntry))
					return true;
			}

			return false;
		}

		return false;
	}

	protected String applyBaseParameters(AppReqBlock aAppReqBlock) throws Exception  {

		// The productstructurekey information has been set and placed in the
		// paramBean. We just need to save the params to the dB with the
		// associated key, and return to the previous page with the selected values.

        Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");

		SessionBean paramBean  = aAppReqBlock.getSessionBean("paramBean");

		PageBean baseParamBean = paramBean.getPageBean("baseParamBean");

		setParameterValues(aAppReqBlock, baseParamBean);
        //SRAMAM 09/2004 DOUBLE2DECIMAL
		//double interestRate = Double.parseDouble(baseParamBean.getValue("interestRate"));
        EDITBigDecimal interestRate = new EDITBigDecimal(baseParamBean.getValue("interestRate"));
		baseParamBean.putValue("interestRate", Util.formatDecimal("###0.00#", interestRate));

		paramBean.putValue("enableRidersLink", "true");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", paramBean.getPageBean("baseParamBean"));

		return "/engine/jsp/params.jsp";
	}

	protected String clearBaseParameters(AppReqBlock aAppReqBlock) throws Exception  {

		return showDefaultBaseParametersScreen(aAppReqBlock);
	}

	protected String doProjection(AppReqBlock aAppReqBlock) throws Exception  {

		// All the necessary values are in paramBean (for the csId and rsId)
		// and the PageBeans stored in the paramBean.


		// ??? Is this the right return page?
		return "/engine/jsp/project.jsp";
	}


	protected String removeRider(AppReqBlock aAppReqBlock) throws Exception  {

		// Need to remove the pageBean from the paramBean that is storing
		// the rider parameters.
		// Additionally, remove the rider from the list of selected riders.

		//PageBean pageBean = new PageBean();

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");


		//String selectedRiderId = aAppReqBlock.getReqParm("selectedRiderId");
		String selectedRider   = aAppReqBlock.getReqParm("rider");

		int index = Integer.parseInt(aAppReqBlock.getReqParm("selectedIndex"));

        Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");

		// Add 1 to selected index bcos the index of
		// base parameters is always zero.
		index += 1;

		calcComponent.removeRider(index);

		// remove rider parameters from session
		paramBean.removePageBean(selectedRider);

		// substract 1 from index bcos array index starts from zero.
		index -= 1;

		// remove rider from selected rider list.
		paramBean.removeFromValues("selectedRiderIds", index);
		paramBean.removeFromValues("selectedRiders", index);

		/* You need not put the page bean in the request because available rider
		   list is stored in the codeTableBean which is a session bean and
		   selected riders are stored in the paramBean which is also a session bean */

		return "/engine/jsp/riderSelection.jsp";
	}

	/*
	* This method is called when param button on debugmain page is hit
	* and at that time we have to display the parameters that are already added
	*/
	protected String showDebugParametersScreen(AppReqBlock aAppReqBlock) {

		PageBean pageBean = aAppReqBlock.
							 getSessionBean("paramBean").
							  getPageBean("debugParamBean");

		String debugContractMode = aAppReqBlock.getSessionBean("paramBean").getValue("debugContractMode");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		if (debugContractMode.equalsIgnoreCase("LifeContract"))
		{
			return "/engine/jsp/params.jsp";
		}
		else if(debugContractMode.equalsIgnoreCase("PayoutContract"))
		{
			return "/engine/jsp/payoutParams.jsp";
		}
		else if(debugContractMode.equalsIgnoreCase("Transaction"))
		{
			return "/engine/jsp/transactionParams.jsp";
		}
		else
		{
			return "/engine/jsp/params.jsp";
		}
	}

	protected String applyDebugParameters(AppReqBlock aAppReqBlock) throws Exception  {

		// syam lingamallu 09/25/01

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		PageBean debugParamBean = paramBean.getPageBean("debugParamBean");

		setParameterValues(aAppReqBlock, debugParamBean);

		paramBean.putValue("enableRidersLink", "true");

		paramBean.putValue("enableCancelButton", "true");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugParamBean);

		return "/engine/jsp/params.jsp";
	}

	protected String saveDebugParameters(AppReqBlock aAppReqBlock) throws Exception  {

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		PageBean debugParamBean = paramBean.getPageBean("debugParamBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

		// For Base Parameters always financical type is BASEPARM.

		analyzerComponent.setFinancialType("BASEPARM");

		analyzerComponent.setSelectedIndex(0);

		setParameterValues(aAppReqBlock, debugParamBean);

        analyzerComponent.setAnalyzerMode(true);

		paramBean.putValue("enableRidersLink", "true");

		paramBean.putValue("enableCancelButton", "true");

		// syam lingamallu 10/02/01

		/* If you keep the script lines in same page bean, the problem occurs
			 while clearing	debug parameters beacause you loose script lines also. */

		PageBean debugScriptBean = aAppReqBlock.
									getSessionBean("paramBean").
									  getPageBean("debugScriptBean");
        debugScriptBean.clearState();

		String[] scriptLines = analyzerComponent.getScriptLines();

		debugScriptBean.putValues("scriptLines", scriptLines, new String[] {"toString"}, null);

		String scriptName = analyzerComponent.getScriptName();

		debugScriptBean.putValue("scriptName" , scriptName);

		// Actually this is to take care of, when the user comes from debug screen
		// and resaves the parameters. I have to just show the empty data stack,
		// working storage etc.. and reset the script processor.

		analyzerComponent.resetScriptProcessor();

		String[] stringArray;

		stringArray = analyzerComponent.getDataStack();

		debugScriptBean.putValues("dataStackValues", stringArray, new String[] {"toString"}, null);

//		stringArray = analyzerComponent.getWS();
//
//		debugScriptBean.putValues("workingStorageValues", stringArray, new String[] {"toString"}, null);

        aAppReqBlock.getHttpSession().setAttribute("workingStorage", analyzerComponent.getWS());

		stringArray = analyzerComponent.getFunctions();

		debugScriptBean.putValues("functionTables", stringArray, new String[] {"toString"}, null);

		// Here I am passing empty string bcos to clear function entries.

		stringArray = analyzerComponent.getFunctionEntry("");

		debugScriptBean.putValues("functionEntries", stringArray, new String[] {"toString"}, null);

		stringArray = analyzerComponent.getOutput();

		debugScriptBean.putValues("outputData", stringArray, new String[] {"toString"}, null);

		stringArray = debugScriptBean.getValues("breakPoints");

		debugScriptBean.putValues("breakPoints", stringArray, new String[] {"toString"}, null);

		debugScriptBean.putValue("instPtr",analyzerComponent.getInstPtr());

		debugScriptBean.putValue("lastInstPtr",analyzerComponent.getLastInstPtr());

		debugScriptBean.putValue("currentRow",analyzerComponent.getCurrentRow());

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugScriptBean);

		return "/engine/jsp/dbugmain.jsp";
	}

	protected String clearDebugParameters(AppReqBlock aAppReqBlock) throws Exception  {

		String debugContractMode = aAppReqBlock.getSessionBean("paramBean").getValue("debugContractMode");

		// JSP will automatically instantiate a dummy PageBean
		if (debugContractMode.equalsIgnoreCase("LifeContract"))
		{
			return showDefaultDebugBaseParametersScreen(aAppReqBlock);
		}
		else if (debugContractMode.equalsIgnoreCase("PayoutContract"))
		{
			return showPayoutDefaultParametersScreen(aAppReqBlock);
		}
		else if (debugContractMode.equalsIgnoreCase("Transaction"))
		{
			return showTransactionParametersScreen(aAppReqBlock);
		}
		else
		{
			return showDefaultDebugBaseParametersScreen(aAppReqBlock);
		}
	}

	protected String cancelDebugParameters(AppReqBlock aAppReqBlock) throws Exception  {

		// I hope cancel will just cancel the current entries and return to debug screen

		PageBean pageBean = aAppReqBlock.
								getSessionBean("paramBean").
									getPageBean("debugParamBean");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		return "/engine/jsp/dbugmain.jsp";
	}

	protected String saveRiderParameters(AppReqBlock aAppReqBlock) throws Exception  {

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		PageBean riderBean = paramBean.getPageBean(aAppReqBlock.getReqParm("rider"));

        Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");

		// Financial type is nothing but the rider description

		String financialType = aAppReqBlock.getReqParm("financialType");

		int selectedIndex = Integer.parseInt(aAppReqBlock.getReqParm("selectedIndex"));

		/* we have to increment the selectedIndex of rider because the base
		parameters are stored at index 0 in script processor.*/

		selectedIndex += 1;

		// System.out.println("Selected Index : " + selectedIndex);

		calcComponent.setFinancialType(financialType);

		calcComponent.setSelectedIndex(selectedIndex);

		setParameterValues(aAppReqBlock, riderBean);

		// calculatorComponent.printParams();

		// Here we need not put anything in the request bcos available riders
		// and selected riders are stored in param bean which is a session bean.

		return "/engine/jsp/riderSelection.jsp";
	}

	protected String clearRiderParameters(AppReqBlock aAppReqBlock) throws Exception  {

        Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		PageBean riderBean = new PageBean();

		String rider = aAppReqBlock.getReqParm("rider");

		String selectedIndex = aAppReqBlock.getReqParm("selectedIndex");

		paramBean.removePageBean(rider);

		int index = Integer.parseInt(selectedIndex);

		// increase the index bcos base parameters index is zero.
		index += 1;

		calcComponent.clearRiderParameters(rider, index);

		riderBean.putValue("editMode", "Rider");

		// I need to pass this information bcos once the user
		// clears and resaves, it would not give any problem.
		riderBean.putValue("rider", rider);

		riderBean.putValue("selectedIndex", selectedIndex);

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", riderBean);

		// JSP will automatically instantiate a dummy PageBean if it can't find one.

		return "/engine/jsp/params.jsp";
	}

	protected String cancelRiderParameters(AppReqBlock aAppReqBlock) throws Exception  {

		//According to production system cancel just goes to rider selection screen

		return "/engine/jsp/riderSelection.jsp";
	}

	protected String setKey(AppReqBlock aAppReqBlock) throws Exception {

		// The productStructure was selected and stored in the paramBean
		// in a previous method. We need to get the ruleStructure info.

        SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

        String productStructurePK = initParam(aAppReqBlock.getReqParm("productStructurePK"), "0");
        String rulesPK = initParam(aAppReqBlock.getReqParm("rulesPK"), "0");

		paramBean.putValue("productStructurePK", productStructurePK);
		paramBean.putValue("rulesPK", rulesPK);

        Lookup lookupComponent = (engine.business.Lookup) aAppReqBlock.getWebService("engine-lookup");

		paramBean.putValue("keyIsSet", "true");

 		String debugContractMode = paramBean.getValue("debugContractMode");

		RulesVO[] rulesVO = lookupComponent.getRuleByRuleId(Long.parseLong(rulesPK));

		//getting the ruleStructureVO over here bec need to set the process, rule etc in RuleKey
	   //calcComponent.setDrivingCRStructure(productStructureId,ruleStructureId);

		//setting the key only for Life Contract
		//over here and for PayoutContract and Transaction
		// setting the key in save and apply methods bec we have to change
		//event and eventType. We get event and eventType values from parameters
		//added from page.

        if (debugContractMode.equalsIgnoreCase("LifeContract")) {

            setKeyValues(aAppReqBlock
                         ,Long.parseLong(productStructurePK)
                         ,Long.parseLong(rulesPK)
                         ,rulesVO[0].getProcessName()
                         ,rulesVO[0].getEventName()
                         ,rulesVO[0].getEventTypeName()
                         ,rulesVO[0].getEffectiveDate()
                         ,rulesVO[0].getRuleName());
        }

		// The backend will want the two ids to attain the productRuleStructureId

		if(debugContractMode.equalsIgnoreCase("PayoutContract")){

			return "/engine/jsp/payoutParams.jsp";

		}
		else if(debugContractMode.equalsIgnoreCase("LifeContract"))
		{
			return "/engine/jsp/params.jsp";
		}
		else if(debugContractMode.equalsIgnoreCase("Transaction"))
		{

            FundVO[] fundVOs = lookupComponent.getFundsBYCSId(Long.parseLong(productStructurePK));

            paramBean.putValues("fundNamesCodes",
                                 fundVOs,
                                  new String[] {"getFundPK"},
                                   ",");

            paramBean.putValues("fundNamesCodeDescriptions",
                                 fundVOs,
                                  new String[] {"getName"},
                                   null);
            //String[] data = paramBean.getValues("fundNamesCodes");

            aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", paramBean);

			return "/engine/jsp/transactionParams.jsp";
		}
		else
		{
			return "/engine/jsp/params.jsp";
		}

	}

	private void setKeyValues(AppReqBlock aAppReqBlock
	                          ,long productStructurePK
	                           ,long rulesPK
	                            ,String processName
								 ,String eventName
								  ,String eventTypeName
								   ,String effectiveDate
								    ,String ruleName)throws Exception
	{

        //Having the two components here is a temporary fix until the xml of prase is implemented
        //Also temporary is the setting of analyzerMode in calcComponent

        SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");
        String projectionMode = paramBean.getValue("projectionMode");

        if (projectionMode.equalsIgnoreCase("Debug")) {
            Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

            analyzerComponent.setAnalyzerMode(true);

            analyzerComponent.setDrivingCRStructure(productStructurePK
                                                     ,rulesPK
                                                     ,processName
                                                     ,eventName
                                                     ,eventTypeName
                                                     ,effectiveDate
                                                     ,ruleName);
        }
        else {
            Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");

            calcComponent.setAnalyzerMode(true);

		    calcComponent.setDrivingCRStructure(productStructurePK
												 ,rulesPK
												 ,processName
												 ,eventName
												 ,eventTypeName
												 ,effectiveDate
												 ,ruleName);
        }

	}

	protected String showKeySelectionDialog(AppReqBlock aAppReqBlock) throws Exception{

        Lookup calcLookup = new LookupComponent();

        ProductStructureVO[] attachedProductStructureVOs = calcLookup.findProductStructureVOsAttachedToRulesVOs();

	    aAppReqBlock.getHttpServletRequest().setAttribute("attachedProductStructureVOs", attachedProductStructureVOs);

		return "/engine/jsp/keySelectionDialog.jsp";
	}

	protected String showAttachedRules(AppReqBlock appReqBlock) throws Exception {

        String productStructurePK = initParam(appReqBlock.getReqParm("productStructurePK"), "0");

        if (productStructurePK.equals("0"))
        {
            String message = "Product Structure Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            return showKeySelectionDialog(appReqBlock);
        }

        Lookup calcLookup = new LookupComponent();

        RulesVO[] attachedRulesVOs = calcLookup.findAttachedRulesVOsByProductStructurePK(Long.parseLong(productStructurePK), false, null);

        appReqBlock.getHttpServletRequest().setAttribute("attachedRulesVOs", attachedRulesVOs);
        appReqBlock.getHttpServletRequest().setAttribute("productStructurePK", productStructurePK);

		return showKeySelectionDialog(appReqBlock);
	}

	protected String editRider(AppReqBlock aAppReqBlock) throws Exception {

		//SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

        Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");

		String selectedRider = aAppReqBlock.getReqParm("rider");

        String financialType = aAppReqBlock.getReqParm("financialType");

		String index = aAppReqBlock.getReqParm("selectedIndex");

		int idx = Integer.parseInt(index);

		idx += 1;

		Map RiderData = calcComponent.
										getParamOfSelectedRider(selectedRider, idx);

		// If there is no riderBean yet, an empty PageBean is returned automatically.
		//PageBean riderBean = paramBean.getPageBean(aAppReqBlock.getReqParm("rider"));
		// since I have trouble in storing and getting rider parameter values with in
		// session every time I will be bringing in from script processor.

		// Just do this every time since the riderBean may be new.

		PageBean riderBean = new PageBean();



		riderBean.putValue("rider", selectedRider);

        riderBean.putValue("financialType", financialType);

		riderBean.putValue("selectedIndex", index);

//		ddNumber = (DDNumber) RiderData.get("interestrate");
//
//		if(ddNumber != null)
//			riderBean.putValue("interestRate", Util.formatDecimal("###0.00#", ddNumber.getDouble() * 100));
//
//		ddString = (DDString) RiderData.get("residentstate");
//
//		if(ddString != null)
//			riderBean.putValue("areaId", ddString.getString());
//
//		ddString = (DDString) RiderData.get("prempayterm");
//
//		if(ddString != null)
//			riderBean.putValue("premPayTerm", ddString.getString());
//
//		ddString = (DDString) RiderData.get("option7702");
//
//		if(ddString != null)
//			riderBean.putValue("option7702Id", ddString.getString());
//
//		ddString = (DDString) RiderData.get("deathbenefitopt");
//
//		if(ddString != null)
//			riderBean.putValue("deathBenOptId", ddString.getString());
//
//		ddNumber = (DDNumber) RiderData.get("coverageamount");
//
//		if(ddNumber != null)
//			riderBean.putValue("coverageAmount", Double.toString(ddNumber.getDouble()));
//
//		ddNumber = (DDNumber) RiderData.get("issueage");
//
//		if(ddNumber != null)
//			riderBean.putValue("issueAge", Integer.toString(ddNumber.getInt()));
//
//		ddString = (DDString) RiderData.get("sex");
//
//		if(ddString != null)
//			riderBean.putValue("genderId", ddString.getString());
//
//		ddString = (DDString) RiderData.get("class");
//
//		if(ddString != null)
//			riderBean.putValue("classId", ddString.getString());
//
//		ddString = (DDString) RiderData.get("tablerating");
//
//		if(ddString != null)
//			riderBean.putValue("tableRating", ddString.getString());
//
//		ddNumber = (DDNumber) RiderData.get("flatextra");
//
//		if(ddNumber != null)
//			riderBean.putValue("flatExtra", Integer.toString(ddNumber.getInt()));
//
//		ddNumber = (DDNumber) RiderData.get("flatage");
//		if(ddNumber != null)
//			riderBean.putValue("flatAge", Integer.toString(ddNumber.getInt()));
//
//		ddNumber = (DDNumber) RiderData.get("flatdur");
//
//		if(ddNumber != null)
//			riderBean.putValue("flatDuration", Integer.toString(ddNumber.getInt()));
//
//		ddNumber = (DDNumber) RiderData.get("percentextra");
//
//		if(ddNumber != null)
//			riderBean.putValue("percentExtra", Double.toString(ddNumber.getInt()));
//
//		ddNumber = (DDNumber) RiderData.get("percentage");
//
//		if(ddNumber != null)
//			riderBean.putValue("percentAge", Integer.toString(ddNumber.getInt()));
//
//		ddNumber = (DDNumber) RiderData.get("percentdur");
//
//		if(ddNumber != null)
//			riderBean.putValue("percentDuration", Integer.toString(ddNumber.getInt()));
//
//		ddDate = (DDDate) RiderData.get("effectivedate");
//
//		if (ddDate != null)  {
//			riderBean.putValue("effectiveDateMonth", Integer.toString(ddDate.getMonth()));
//			riderBean.putValue("effectiveDateDay", Integer.toString(ddDate.getDay()));
//			riderBean.putValue("effectiveDateYear", Integer.toString(ddDate.getYear()));
//		}
//
//		ddDate = (DDDate) RiderData.get("terminationdate");
//
//		if (ddDate != null)  {
//			riderBean.putValue("terminationDateMonth", Integer.toString(ddDate.getMonth()));
//			riderBean.putValue("terminationDateDay", Integer.toString(ddDate.getDay()));
//			riderBean.putValue("terminationDateYear", Integer.toString(ddDate.getYear()));
//		}
//
//		riderBean.putValue("editMode", "Rider");
//
//		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", riderBean);

		return "/engine/jsp/params.jsp";
	}

	protected String getScriptCallChain(AppReqBlock aAppReqBlock)throws Exception
	{
        SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		// The productStructure was selected and stored in the paramBean
		// in a previous method. We need to get the ruleStructure info.

        Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

        String debugContractMode = paramBean.getValue("debugContractMode");

 		ScriptChainNodeWrapper scriptChain = analyzerComponent.getScriptCallChain(null, false);

        if (scriptChain == null) {

            if (debugContractMode.equalsIgnoreCase("LifeContract")) {

                return "/engine/jsp/params.jsp";
            }

            else if (debugContractMode.equalsIgnoreCase("PayoutContract")) {

                String message = "Must select Key and Payout Parameters before viewing call chain.";
                aAppReqBlock.getHttpServletRequest().setAttribute("callChainMessage", message);
                return "/engine/jsp/callChainMessage.jsp";
            }

            else if (debugContractMode.equalsIgnoreCase("Transaction")) {

                String message = "Must select Key and Transaction Parameters before viewing call chain.";
                aAppReqBlock.getHttpServletRequest().setAttribute("callChainMessage", message);
                return "/engine/jsp/callChainMessage.jsp";
            }

            else {

                return "/engine/jsp/params.jsp";
            }
        }

        else {

            if (!debugContractMode.equalsIgnoreCase("Transaction") &&
                !debugContractMode.equalsIgnoreCase("PayoutContract") &&
                !debugContractMode.equalsIgnoreCase("LifeContract")) {

                String message = "Must be in Analyzer to display call chain.";
                aAppReqBlock.getHttpServletRequest().setAttribute("callChainMessage", message);
                return "/engine/jsp/callChainMessage.jsp";
            }

            else {

                CallChainVO callChainVO = new CallChainVO(scriptChain);

                aAppReqBlock.getHttpServletRequest().setAttribute("callChainVO", callChainVO);

                return "/engine/jsp/callChain.jsp";
            }
        }
	}

	private static int row = -1;
	private static int col = -1;
	private static Map nextCol = new HashMap();

	public static void printNode(ScriptChainNodeWrapper node) {

		row++;

		Integer colObj = (Integer) nextCol.get("nextCol:" + row);

		if (colObj == null) {

			nextCol.put("nextCol:" + row, new Integer(1));

			col = 0;
		}
		else {

			col = colObj.intValue();

			nextCol.put("nextCol:" + row, new Integer(col + 1));
		}

		String space = "";

		for (int i = 0; i < row; i++) {

			space += "  ";
		}

		ScriptChainNodeWrapper parent = node.getParent();
		String parentName = "";
		if (parent ==  null)
		{
			parentName = "RootNode";
		}
		else
		{
			parentName = parent.getNodeDescription();
		}
		System.out.println(space + node.getNodeId()+" "+node.getNodeDescription() + "Parent is = "+parentName +" \t\t" + row + "," + col);
//		ScriptLineVO[] scriptLines = node.getScriptLines();
//		System.out.println("ScriptLines are:  ");
//		for(int i=0; i<scriptLines.length; ++i)
//		{
//			System.out.println(i+"  "+scriptLines[i].getScriptLine());
//		}
		if (node.hasChildren()) {

			List children = node.getChildren();

			for (int i = 0; i < children.size(); i++) {

				printNode((ScriptChainNodeWrapper) children.get(i));
			}
		}

		row--;
	}

	/*
	* This method is called when Debug for Life Contract is selected
	* and at that time we have to display the default parameters
	*/
	protected String showDefaultDebugBaseParametersScreen(AppReqBlock aAppReqBlock) throws Exception {


		// This starts a fresh session for the debug parameter screen.
		// paramBean will store all parameters from base, rider, and debug,
		// as well as store the driving key data.
		//
		// A mode is always supplied to this method, and it is stored
		// throughout the remaing debug screens. It should only be set in
		// in this method.

		PageBean debugParamBean = new PageBean();

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		paramBean.clearState();

		//to distinguish between debug and projection
		paramBean.putValue("projectionMode", "Debug");

		//to distinguish between debug-- payout params and life contract params
		paramBean.putValue("debugContractMode", "LifeContract");

		// The paramBean stores pageBeans. Each pageBean stores
		// the parameters for a particular screen such as rider, debug, etc.

		paramBean.putPageBean("debugParamBean", debugParamBean);

		paramBean.putValue("enableRidersLink", "false");

		paramBean.putValue("enableCancelButton", "false");

		// JSP expects the paramBean and codeTableBean (in session)
		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugParamBean);

		return "/engine/jsp/params.jsp";
	}

	/*
	* This method is called when Debug for Payout Contract is selected
	* and at that time we have to display the default parameters
	*/
	protected String showPayoutDefaultParametersScreen(AppReqBlock aAppReqBlock)throws Exception
	{

		// This starts a fresh session for the debug parameter screen.
		// paramBean will store all parameters from base, rider, and debug,
		// as well as store the driving key data.
		//
		// A mode is always supplied to this method, and it is stored
		// throughout the remaing debug screens. It should only be set in
		// in this method.

		PageBean debugParamBean = new PageBean();

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		paramBean.removeAllValues();

		//to distinguish between debug and projection
		paramBean.putValue("projectionMode", "Debug");

		//to distinguish between debug-- payout params and life contract params
		paramBean.putValue("debugContractMode", "PayoutContract");

		// The paramBean stores pageBeans. Each pageBean stores
		// the parameters for a particular screen such as rider, debug, etc.

		paramBean.putPageBean("debugParamBean", debugParamBean);

		paramBean.putValue("enableRidersLink", "false");

		paramBean.putValue("enableCancelButton", "false");

		// JSP expects the paramBean and codeTableBean (in session)
		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugParamBean);

		return "/engine/jsp/payoutParams.jsp";
	}

	protected String savePayoutParameters(AppReqBlock aAppReqBlock) throws Exception
	{
		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		//set that param bean has Payout parameters
		paramBean.putValue("payoutParameters","payoutParameters");

		PageBean debugParamBean = paramBean.getPageBean("debugParamBean");

        Calculator calcComponent = (engine.business.Calculator) aAppReqBlock.getWebService("engine-service");
        Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.setFinancialType("BASEPARM");

		analyzerComponent.setSelectedIndex(0);

		setParameterValuesForPayout(aAppReqBlock, debugParamBean);

		paramBean.putValue("enableRidersLink", "true");

		paramBean.putValue("enableCancelButton", "true");

		/* If you keep the script lines in same page bean, the problem occurs
			 while clearing	debug parameters beacause you loose script lines also. */

		PageBean debugScriptBean = aAppReqBlock.
									getSessionBean("paramBean").
									  getPageBean("debugScriptBean");

		//set the key
		long productStructurePK = Long.parseLong(paramBean.getValue("productStructurePK"));

		//setting the rule Id to zero bec we have to change the process,event,eventType and ruleName
		//so it will get a new ruleId in Calculator component
 		int rulesPK = 0;//Integer.parseInt(paramBean.getValue("ruleStructureId"));

		String processName = "Quote";
		String eventName = "";
		double param = Double.parseDouble(aAppReqBlock.getReqParm("purchaseAmount"));
		if (param == 0)
		{
			eventName = "PurchAmt";
		}
		else
		{
			eventName = "ModalAmt";
		}

		String paramString = aAppReqBlock.getReqParm("areaId");
		String eventTypeName = paramString;

        EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("effectiveDate"));

		String ruleName  = "Driver";

		setKeyValues(aAppReqBlock
	                  ,productStructurePK
	                   ,rulesPK
	                    ,processName
						 ,eventName
						  ,eventTypeName
						   ,effectiveDate.getFormattedDate()
							,ruleName);

        analyzerComponent.setAnalyzerMode(true);

		String[] scriptLines = analyzerComponent.getScriptLines();

		debugScriptBean.putValues("scriptLines", scriptLines, new String[] {"toString"}, null);

		String scriptName = analyzerComponent.getScriptName();

		debugScriptBean.putValue("scriptName" , scriptName);

		// Actually this is to take care of, when the user comes from debug screen
		// and resaves the parameters. I have to just show the empty data stack,
		// working storage etc.. and reset the script processor.

		String[] stringArray;

		stringArray = analyzerComponent.getDataStack();

		debugScriptBean.putValues("dataStackValues", stringArray, new String[] {"toString"}, null);

//		stringArray = analyzerComponent.getWS();

//		debugScriptBean.putValues("workingStorageValues", stringArray, new String[] {"toString"}, null);

        aAppReqBlock.getHttpSession().setAttribute("workingStorage", analyzerComponent.getWS());

		stringArray = analyzerComponent.getFunctions();

		debugScriptBean.putValues("functionTables", stringArray, new String[] {"toString"}, null);

		// Here I am passing empty string bcos to clear function entries.

		stringArray = analyzerComponent.getFunctionEntry("");

		debugScriptBean.putValues("functionEntries", stringArray, new String[] {"toString"}, null);

		stringArray = analyzerComponent.getOutput();

		debugScriptBean.putValues("outputData", stringArray, new String[] {"toString"}, null);

		stringArray = debugScriptBean.getValues("breakPoints");

		debugScriptBean.putValues("breakPoints", stringArray, new String[] {"toString"}, null);

		debugScriptBean.putValue("instPtr",analyzerComponent.getInstPtr());

		debugScriptBean.putValue("lastInstPtr",analyzerComponent.getLastInstPtr());

		debugScriptBean.putValue("currentRow",analyzerComponent.getCurrentRow());

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugScriptBean);

		return "/engine/jsp/dbugmain.jsp";

	}

	protected String applyPayoutParameters(AppReqBlock aAppReqBlock) throws Exception  {

		// syam lingamallu 09/25/01

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		PageBean debugParamBean = paramBean.getPageBean("debugParamBean");

		setParameterValuesForPayout(aAppReqBlock, debugParamBean);

		paramBean.putValue("enableRidersLink", "true");

		paramBean.putValue("enableCancelButton", "true");

		//String sex = debugParamBean.getValue("genderId");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugParamBean);

		return "/engine/jsp/payoutParams.jsp";
	}

	protected String clearPayoutParameters(AppReqBlock aAppReqBlock) throws Exception  {

		aAppReqBlock.getSessionBean("paramBean").removePageBean("debugParamBean");

		aAppReqBlock.getSessionBean("paramBean").putValue("enableCancelButton", "false");

		// JSP will automatically instantiate a dummy PageBean

		return "/engine/jsp/payoutParams.jsp";
	}

	protected String cancelPayoutParameters(AppReqBlock aAppReqBlock) throws Exception  {

	 	// syam lingamallu 09/25/01

		// I hope cancel will just cancel the current entries and return to debug screen

		PageBean pageBean = aAppReqBlock.
								getSessionBean("paramBean").
									getPageBean("debugParamBean");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", pageBean);

		return "/engine/jsp/dbugmain.jsp";
	}

	/*
	* This method is called when Projection for Payout Contract is selected
	* and at that time we have to display the default parameters
	*/
	protected String showPayoutBaseParametersScreen(AppReqBlock aAppReqBlock) throws Exception  {

		// This starts a fresh session for the base parameter screen.
		// paramBean will store all parameters from base, rider, and debug,
		// as well as store the driving key data.
		//
		// A mode is always supplied to this method, and it is stored
		// throughout the remaing debug screens. It should only be set in
		// in this method.

		PageBean baseParamBean = new PageBean();

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");
		paramBean.removeAllValues();

		//to distinguish between debug and projection
		paramBean.putValue("projectionMode", "Base Parameters");

		//to distinguish between Projection's-- payout params and life contract params
		paramBean.putValue("debugContractMode", "PayoutContract");

		paramBean.putValue("enableRidersLink", "false");

		// The paramBean stores pageBeans. Each pageBean stores
		// the parameters for a particular screen such as rider, debug, etc.
		paramBean.putPageBean("baseParamBean", baseParamBean);

		// JSP expects the paramBean and codeTableBean (in session)
		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", baseParamBean);

		return "/engine/jsp/payoutParams.jsp";
	}

	protected String applyPayoutBaseParameters(AppReqBlock aAppReqBlock) throws Exception  {

		// The productstructurekey information has been set and placed in the
		// paramBean. We just need to save the params to the dB with the
		// associated key, and return to the previous page with the selected values.

		SessionBean paramBean  = aAppReqBlock.getSessionBean("paramBean");

		PageBean baseParamBean = paramBean.getPageBean("baseParamBean");

		setParameterValuesForPayout(aAppReqBlock, baseParamBean);

		paramBean.putValue("enableRidersLink", "true");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", paramBean.getPageBean("baseParamBean"));

		return "/engine/jsp/payoutParams.jsp";
	}

	protected String clearPayoutBaseParameters(AppReqBlock aAppReqBlock) throws Exception  {

		return showPayoutBaseParametersScreen(aAppReqBlock);
	}

	protected String showTransactionParametersScreen(AppReqBlock aAppReqBlock)throws Exception
	{
		// This starts a fresh session for the debug parameter screen.
		// paramBean will store all parameters from base, rider, and debug,
		// as well as store the driving key data.
		//
		// A mode is always supplied to this method, and it is stored
		// throughout the remaing debug screens. It should only be set in
		// in this method.

		PageBean debugParamBean = new PageBean();

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

        //Lookup lookup = (engine.business.Lookup) aAppReqBlock.getWebService("engine-lookup");

		paramBean.removeAllValues();

		//to distinguish between debug and projection
		paramBean.putValue("projectionMode", "Debug");

		//to distinguish between debug-- payout params and life contract params
		paramBean.putValue("debugContractMode", "Transaction");


		// The paramBean stores pageBeans. Each pageBean stores
		// the parameters for a particular screen such as rider, debug, etc.

		paramBean.putPageBean("debugParamBean", debugParamBean);

		paramBean.putValue("enableRidersLink", "false");

		paramBean.putValue("enableCancelButton", "false");

		// JSP expects the paramBean and codeTableBean (in session)
		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugParamBean);

		return "/engine/jsp/transactionParams.jsp";
	}

	protected String saveTransactionParameters(AppReqBlock aAppReqBlock)throws Exception {

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		PageBean debugParamBean = paramBean.getPageBean("debugParamBean");

        Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

        Lookup lookupComponent = (engine.business.Lookup) aAppReqBlock.getWebService("engine-lookup");

		// For Base Parameters always financical type is BASEPARM.

		analyzerComponent.setFinancialType("BASEPARM");

        String productKey = paramBean.getValue("productStructurePK");
		long productStructurePK = Long.parseLong(paramBean.getValue("productStructurePK"));

		long rulesPK = Long.parseLong(paramBean.getValue("rulesPK"));

        analyzerComponent.addParamEntry("productkey", productKey);

		setParameterValuesForTransaction(aAppReqBlock, debugParamBean, productStructurePK);

        analyzerComponent.setAnalyzerMode(true);

		paramBean.putValue("enableRidersLink", "true");

		paramBean.putValue("enableCancelButton", "true");

		/* If you keep the script lines in same page bean, the problem occurs
			 while clearing	debug parameters beacause you loose script lines also. */

		PageBean debugScriptBean = aAppReqBlock.
									getSessionBean("paramBean").
									  getPageBean("debugScriptBean");


		RulesVO[] rulesVOs = lookupComponent.getRuleByRuleId(rulesPK);

//		RuleStructureVO[] ruleStructureVO = (RuleStructureVO[]) Util.unmarshalVOs(RuleStructureVO.class,
//                                                lookupComponent.getByRuleStructureId(rulesVO[0].getRuleStructureFK()));

		String processName = rulesVOs[0].getProcessName();
		String eventName = "";
		double paramDouble = Double.parseDouble(aAppReqBlock.getReqParm("paymentAmount"));
		int param = (new Double(paramDouble)).intValue();
		if (param == 0)
		{
			eventName = "ModalAmt";
		}
		else
		{
			eventName = "PurchAmt";
		}

		String paramString = aAppReqBlock.getReqParm("areaId");
        if (Util.isANumber(paramString))
        {
            paramString = codeTableWrapper.getCodeTableEntry(Long.parseLong(paramString)).getCode();
        }

		String eventTypeName = paramString;

        EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("effectiveDate"));

		String ruleName  = rulesVOs[0].getRuleName();

//        calcComponent.clearScriptProcessor();
//
		setKeyValues(aAppReqBlock
	                  ,productStructurePK
	                   ,rulesPK
	                    ,processName
						 ,eventName
						  ,eventTypeName
						   ,effectiveDate.getFormattedDate()
							,ruleName);

		String[] scriptLines = analyzerComponent.getScriptLines();

		debugScriptBean.putValues("scriptLines", scriptLines, new String[] {"toString"}, null);

		String scriptName = analyzerComponent.getScriptName();

		debugScriptBean.putValue("scriptName" , scriptName);

		// Actually this is to take care of, when the user comes from debug screen
		// and resaves the parameters. I have to just show the empty data stack,
		// working storage etc.. and reset the script processor.

		analyzerComponent.resetScriptProcessor();

		String[] stringArray;

		stringArray = analyzerComponent.getDataStack();

		debugScriptBean.putValues("dataStackValues", stringArray, new String[] {"toString"}, null);

//		stringArray = analyzerComponent.getWS();

//		debugScriptBean.putValues("workingStorageValues", stringArray, new String[] {"toString"}, null);

        aAppReqBlock.getHttpSession().setAttribute("workingStorage", analyzerComponent.getWS());

		stringArray = analyzerComponent.getFunctions();

		debugScriptBean.putValues("functionTables", stringArray, new String[] {"toString"}, null);

		// Here I am passing empty string bcos to clear function entries.

		stringArray = analyzerComponent.getFunctionEntry("");

		debugScriptBean.putValues("functionEntries", stringArray, new String[] {"toString"}, null);

		stringArray = analyzerComponent.getOutput();

		debugScriptBean.putValues("outputData", stringArray, new String[] {"toString"}, null);

		stringArray = debugScriptBean.getValues("breakPoints");

		debugScriptBean.putValues("breakPoints", stringArray, new String[] {"toString"}, null);

		debugScriptBean.putValue("instPtr",analyzerComponent.getInstPtr());

		debugScriptBean.putValue("lastInstPtr",analyzerComponent.getLastInstPtr());

		debugScriptBean.putValue("currentRow",analyzerComponent.getCurrentRow());

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugScriptBean);

		return "/engine/jsp/dbugmain.jsp";
	}

	protected String applyTransactionParameters(AppReqBlock aAppReqBlock) throws Exception  {

		SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		PageBean debugParamBean = paramBean.getPageBean("debugParamBean");

        long productStructurePK = Long.parseLong(paramBean.getValue("productStructurePK"));

		setParameterValuesForTransaction(aAppReqBlock, debugParamBean, productStructurePK);

		paramBean.putValue("enableRidersLink", "true");

		paramBean.putValue("enableCancelButton", "true");

		aAppReqBlock.getHttpServletRequest().setAttribute("pageBean", debugParamBean);

		return "/engine/jsp/transactionParams.jsp";
	}


//*******************************
//  	  Helper Methods
//*******************************

	private void setParameterValues(AppReqBlock aAppReqBlock, PageBean pageBean) throws Exception{


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

        EDITDate effectiveDate  = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("effectiveDate"));
        EDITDate terminationDate  = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("terminationDate"));

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

	private void setParameterValuesForPayout(AppReqBlock aAppReqBlock,
                                              PageBean pageBean)
                                            throws Exception {

        SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

		Map clientInformation = new HashMap();

		List owners          = new ArrayList();
        List annuitants      = new ArrayList();
        List payees          = new ArrayList();
        List beneficiaries   = new ArrayList();
        List trustees        = new ArrayList();
        List checkBankPayors = new ArrayList();
        List eftBankPayors   = new ArrayList();

        Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.addParamEntry("transactionridertype", "BASE");

		EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("effectiveDate"));

		pageBean.putValue("effectiveDateMonth", effectiveDate.getFormattedMonth());
		pageBean.putValue("effectiveDateDay",   effectiveDate.getFormattedDay());
		pageBean.putValue("effectiveDateYear",  effectiveDate.getFormattedYear());

        analyzerComponent.addParamEntry("effectivedate", effectiveDate.getFormattedDate());

		clientInformation.put("effectivedate", effectiveDate.getFormattedDate());

		EDITDate startDate  = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("startDate"));

		pageBean.putValue("startDateMonth", startDate.getFormattedMonth());
		pageBean.putValue("startDateDay",   startDate.getFormattedDay());
		pageBean.putValue("startDateYear",  startDate.getFormattedYear());

		analyzerComponent.addParamEntry("startdate", startDate.getFormattedDate());

		String param = aAppReqBlock.getReqParm("areaId");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("areaId", aAppReqBlock.getReqParm("areaId"));
			analyzerComponent.addParamEntry("issuestate", param);
		}

		param = aAppReqBlock.getReqParm("nonQualInd");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("nonQualInd", aAppReqBlock.getReqParm("nonQualInd"));
			analyzerComponent.addParamEntry("qualnonqualind", param);
		}

		param = aAppReqBlock.getReqParm("exchangeInd");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("exchangeInd", aAppReqBlock.getReqParm("exchangeInd"));
			analyzerComponent.addParamEntry("exchangeind", param);
		}

		param = aAppReqBlock.getReqParm("annuityOption");

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

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("annuityOptionId", aAppReqBlock.getReqParm("annuityOption"));
			analyzerComponent.addParamEntry("payoutoption", payoutOption);
		}

		param = aAppReqBlock.getReqParm("certainPeriod");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("certainPeriod", aAppReqBlock.getReqParm("certainPeriod"));
            analyzerComponent.addParamEntry("certainperiod", param);
		}

		param = aAppReqBlock.getReqParm("frequency");

        String paymentFrequency = null;

        if (param.equalsIgnoreCase("AN") || param.equalsIgnoreCase("Annual")) {

            paymentFrequency = "Annual";
        }

        else if (param.equalsIgnoreCase("SA") || param.equalsIgnoreCase("Semi")) {

            paymentFrequency = "Semi";
        }

        else if (param.equalsIgnoreCase("QU") || param.equalsIgnoreCase("v")) {

            paymentFrequency = "Quarterly";
        }

        else if (param.equalsIgnoreCase("BM") || param.equalsIgnoreCase("BiMonthly")) {

            paymentFrequency = "BiMonthly";
        }

        else if (param.equalsIgnoreCase("MO") || param.equalsIgnoreCase("Monthly")) {

            paymentFrequency = "Monthly";
        }

		else if (param.equalsIgnoreCase("BW") || param.equalsIgnoreCase("BiWeekly")) {

            paymentFrequency = "BiWeekly";
        }

        else if (param.equalsIgnoreCase("WE") || param.equalsIgnoreCase("Weekly")) {

            paymentFrequency = "Weekly";
        }

		if ((param != null) && (param.trim().length() > 0))  {
				pageBean.putValue("frequencyId", aAppReqBlock.getReqParm("frequency"));
				analyzerComponent.addParamEntry("frequency", paymentFrequency);
		}

		param = aAppReqBlock.getReqParm("payoutBasis");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("payoutBasisId", aAppReqBlock.getReqParm("payoutBasis"));
			analyzerComponent.addParamEntry("payoutbasis", param);
		}

		param = aAppReqBlock.getReqParm("purchaseAmount");
        String event = "";

        double paramDouble = Double.parseDouble(param);
		if (paramDouble == 0) {

			event = "PurchAmt";
            param = aAppReqBlock.getReqParm("paymentAmount");
            pageBean.putValue("paymentAmount", param);
			analyzerComponent.addParamEntry("modalamount",param);
            analyzerComponent.addParamEntry("amount", "0");
		}

		else {

			event = "ModalAmt";
            pageBean.putValue("purchaseAmount", aAppReqBlock.getReqParm("purchaseAmount"));
			analyzerComponent.addParamEntry("amount", param);
            analyzerComponent.addParamEntry("modalamount", "0");
		}

		param = aAppReqBlock.getReqParm("post86Investment");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("post86Investment", aAppReqBlock.getReqParm("post86Investment"));
			analyzerComponent.addParamEntry("post86investment", param);
		}

		param = aAppReqBlock.getReqParm("costBasis");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("costBasis", aAppReqBlock.getReqParm("costBasis"));
			analyzerComponent.addParamEntry("originvestment", param);
		}

		param = aAppReqBlock.getReqParm("issueAge");

		if ((param != null) && (param.trim().length() > 0))  {

			pageBean.putValue("issueAge", aAppReqBlock.getReqParm("issueAge"));
			analyzerComponent.addParamEntry("issueage", param);
		}

		clientInformation.put("issueage",param );

		param = aAppReqBlock.getReqParm("sex");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("genderId", aAppReqBlock.getReqParm("sex"));
			analyzerComponent.addParamEntry("sex", param);
		}

		clientInformation.put("sex",param);

		param = aAppReqBlock.getReqParm("classIds");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("classId", aAppReqBlock.getReqParm("classIds"));
			analyzerComponent.addParamEntry("class", param);
		}

		param = aAppReqBlock.getReqParm("relationshipInd");

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("relationshipId", aAppReqBlock.getReqParm("relationshipInd"));//"ANN"
			analyzerComponent.addParamEntry("relationshipind", param);
		}
        clientInformation.put("relationshipind", param);

		param = aAppReqBlock.getReqParm("disbursementSource");
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

		if ((param != null) && (param.trim().length() > 0))  {
			pageBean.putValue("disburseSourceId", aAppReqBlock.getReqParm("disbursementSource"));
			analyzerComponent.addParamEntry("disbursementsource", disbursementSource);
		}
		clientInformation.put("disbursementsource", disbursementSource);

		//set the AnnuitantCounter parameter to 1 according to specs
		analyzerComponent.addParamEntry("annuitantcounter", "1");

		//set the PayeeCounter parameter to 1 according to specs
		analyzerComponent.addParamEntry("payeecounter", "1");

        analyzerComponent.addParamEntry("statuscode", "Q");

		//add client information
		owners.add(clientInformation);
		annuitants.add(clientInformation);
        payees.add(clientInformation);
        beneficiaries.add(clientInformation);
        trustees.add(clientInformation);
        checkBankPayors.add(clientInformation);
        eftBankPayors.add(clientInformation);

		analyzerComponent.addParamEntry("annuitants", annuitants);

		analyzerComponent.addParamEntry("payees", payees);

		analyzerComponent.addParamEntry("owners", owners);

		analyzerComponent.addParamEntry("beneficiaries", beneficiaries);

		analyzerComponent.addParamEntry("trustees", trustees);

		analyzerComponent.addParamEntry("checkBankPayors", checkBankPayors);

		analyzerComponent.addParamEntry("eftBankPayors", eftBankPayors);

		analyzerComponent.addParamEntry("selectedindex", "1");

        Lookup lookupComponent = (engine.business.Lookup) aAppReqBlock.getWebService("engine-lookup");

		paramBean.putValue("keyIsSet", "true");

		paramBean.putValue("rulesPK", aAppReqBlock.getReqParm("rulesPK"));

		long productStructurePK = Long.parseLong(paramBean.getValue("productStructurePK"));

		long rulesPK = Long.parseLong(paramBean.getValue("rulesPK"));

		RulesVO[] rulesVOs = lookupComponent.getRuleByRuleId(rulesPK);

//		RuleStructureVO[] ruleStructureVO = (RuleStructureVO[]) Util.unmarshalVOs(RuleStructureVO.class,
//                                              lookupComponent.getByRuleStructureId(rulesVO[0].getRuleStructureFK()));

        setKeyValues(aAppReqBlock
                     ,productStructurePK
                     ,rulesPK
                     ,rulesVOs[0].getProcessName()
                     ,event
                     ,payoutOption
                     ,effectiveDate.getFormattedDate()
                     ,rulesVOs[0].getRuleName());
	}

	private void setParameterValuesForTransaction(AppReqBlock aAppReqBlock,
                                                   PageBean pageBean,
                                                    long productStructurePK) throws Exception {

        SessionBean paramBean = aAppReqBlock.getSessionBean("paramBean");

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

		Map clientInformation = new HashMap();

		List owners          = new ArrayList();
        List annuitants      = new ArrayList();
        List payees          = new ArrayList();
        List beneficiaries   = new ArrayList();
        List trustees        = new ArrayList();
        List checkBankPayors = new ArrayList();
        List eftBankPayors   = new ArrayList();
        String payeeStatus     = "";

        Analyzer analyzerComponent = (engine.business.Analyzer) aAppReqBlock.getHttpSession().getAttribute("analyzerComponent");

		analyzerComponent.addParamEntry("transactionridertype", "BASE");

		String param = aAppReqBlock.getReqParm("transactionType");
        CodeTableVO trxTypeCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));

		if ((param!= null) && (param.trim().length() > 0)) {

			pageBean.putValue("trxTypeId", param);
			analyzerComponent.addParamEntry("transactiontype", (trxTypeCTVO.getCodeDesc()));
		}

        //default statuscode
        analyzerComponent.addParamEntry("statuscode", "");


		EDITDate effectiveDate  = DateTimeUtil.getEDITDateFromMMDDYYYY(aAppReqBlock.getReqParm("effectiveDate"));

		pageBean.putValue("effectiveDateMonth", effectiveDate.getFormattedMonth());
		pageBean.putValue("effectiveDateDay",   effectiveDate.getFormattedDay());
		pageBean.putValue("effectiveDateYear",  effectiveDate.getFormattedYear());

		analyzerComponent.addParamEntry("effectivedate", effectiveDate.getFormattedDate());

		clientInformation.put("effectivedate", effectiveDate.getFormattedDate());

//        edit.common.EDITDate ceDate = new edit.common.EDITDate("9999/99/99");

        //default the payeeeffdate from the effective date params entered - needed for geterror
        clientInformation.put("payeeeffdate", EDITDate.DEFAULT_MAX_DATE);

        //default the payeetermdate for geterror
        clientInformation.put("payeetermdate", EDITDate.DEFAULT_MAX_DATE);
        clientInformation.put("payeestatus", payeeStatus);


        //test code for script
        analyzerComponent.addParamEntry("trxeffectivedate", effectiveDate.getFormattedDate());

		param = aAppReqBlock.getReqParm("grossAmount");
		if ((param!= null) && (param.trim().length() > 0)) {

			pageBean.putValue("grossAmount",aAppReqBlock.getReqParm("grossAmount"));
			analyzerComponent.addParamEntry("amount", param);
		}

		param = aAppReqBlock.getReqParm("netGross");
        String netGross = "";
        if (param.equalsIgnoreCase("Please Select")) {

            netGross = "Gross";
        }

        else {

            CodeTableVO netGrossCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));
            netGross = netGrossCTVO.getCodeDesc();
        }

        analyzerComponent.addParamEntry("trxamount", aAppReqBlock.getReqParm("grossAmount"));

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("netGrossId", param);
			analyzerComponent.addParamEntry("trxnetamountind", netGross);
		}

		param = aAppReqBlock.getReqParm("areaId");
        CodeTableVO stateCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("areaId", param);
			analyzerComponent.addParamEntry("issuestate", stateCTVO.getCode());
		}

		param = aAppReqBlock.getReqParm("annuityOption");
        CodeTableVO optionCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));

		String payoutOption = null;
        if ((optionCTVO.getCode()).equalsIgnoreCase("LOA")) {

            payoutOption = "Life";
        }

        else if ((optionCTVO.getCode()).equalsIgnoreCase("PCA")) {

            payoutOption = "PerCert";
        }

        else if ((optionCTVO.getCode()).equalsIgnoreCase("LPC")) {

            payoutOption = "LifePerCert";
        }

        else if ((optionCTVO.getCode()).equalsIgnoreCase("JSA")) {

            payoutOption = "JTLife";
        }

		else if ((optionCTVO.getCode()).equalsIgnoreCase("JPC")) {

            payoutOption = "JTPerCert";
        }

        else if ((optionCTVO.getCode()).equalsIgnoreCase("LCR")) {

            payoutOption = "LifeCashRefund";
        }

        else if((optionCTVO.getCode()).equalsIgnoreCase("AMC")) {

            payoutOption = "AmtCert";
        }

        else if((optionCTVO.getCode()).equalsIgnoreCase("INR")) {

            payoutOption = "InstRef";
        }

        else if((optionCTVO.getCode()).equalsIgnoreCase("INT")) {

            payoutOption = "IntOnly";
        }

        else if ((optionCTVO.getCode()).equalsIgnoreCase("TML")) {

            payoutOption = "TmpLife";
        }

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("annuityOptionId", param);
			analyzerComponent.addParamEntry("payoutoption", payoutOption);
		}

		param = aAppReqBlock.getReqParm("frequency");

        if (!param.equalsIgnoreCase("Please Select") &&
            !param.equals("")) {

            CodeTableVO frequencyCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));
            String frequencyCode = frequencyCTVO.getCode();

            String paymentFrequency = null;

            if (frequencyCode.equalsIgnoreCase("AN") || frequencyCode.equalsIgnoreCase("Annual")) {

                paymentFrequency = "Annual";
            }

            else if (frequencyCode.equalsIgnoreCase("SA") || frequencyCode.equalsIgnoreCase("Semi")) {

                paymentFrequency = "Semi";
            }

            else if (frequencyCode.equalsIgnoreCase("QU") || frequencyCode.equalsIgnoreCase("Quarterly")) {

                paymentFrequency = "Quarterly";
            }

            else if (frequencyCode.equalsIgnoreCase("BM") || frequencyCode.equalsIgnoreCase("BiMonthly")) {

                paymentFrequency = "BiMonthly";
            }

            else if (frequencyCode.equalsIgnoreCase("MO") || frequencyCode.equalsIgnoreCase("Monthly")) {

                paymentFrequency = "Monthly";
            }

            else if (frequencyCode.equalsIgnoreCase("BW") || frequencyCode.equalsIgnoreCase("BiWeekly")) {

                paymentFrequency = "BiWeekly";
            }

            else if (frequencyCode.equalsIgnoreCase("WE") || frequencyCode.equalsIgnoreCase("Weekly")) {

                paymentFrequency = "Weekly";
            }

            if ((param != null) && (param.trim().length() > 0)) {

                pageBean.putValue("frequencyId", param);
                analyzerComponent.addParamEntry("frequency", paymentFrequency);
            }
        }

		param = aAppReqBlock.getReqParm("paymentAmount");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("paymentAmount", aAppReqBlock.getReqParm("paymentAmount"));
			analyzerComponent.addParamEntry("modalamount", param);
		}

		param = aAppReqBlock.getReqParm("yearlyTaxableBenefit");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("yearlyTaxableBenefit", aAppReqBlock.getReqParm("yearlyTaxableBenefit"));
			analyzerComponent.addParamEntry("yearlytaxablebenefit", param);
		}

		param = aAppReqBlock.getReqParm("exclusionRatio");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("exclusionRatio", aAppReqBlock.getReqParm("exclusionRatio"));
			analyzerComponent.addParamEntry("exclusionratio", param);
		}

		param = aAppReqBlock.getReqParm("costBasis");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("costBasis", aAppReqBlock.getReqParm("costBasis"));
			analyzerComponent.addParamEntry("originvestment", param);
		}

		param = aAppReqBlock.getReqParm("recoveredCostBasis");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("recoveredCostBasis", aAppReqBlock.getReqParm("recoveredCostBasis"));
			analyzerComponent.addParamEntry("recoveredcostbasis" , param);
		}

		param = aAppReqBlock.getReqParm("issueAge");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("issueAge", aAppReqBlock.getReqParm("issueAge"));
			analyzerComponent.addParamEntry("issueage", param);
		}

		clientInformation.put("issueage", param);

		param = aAppReqBlock.getReqParm("sex");
        CodeTableVO genderCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("genderId", param);
			analyzerComponent.addParamEntry("sex", genderCTVO.getCode());
		}

		clientInformation.put("sex", genderCTVO.getCode());

		param = aAppReqBlock.getReqParm("classIds");
        if (!param.equalsIgnoreCase("Please Select") &&
            !param.equals("")) {

            CodeTableVO classCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));
            if ((param != null) && (param.trim().length() > 0)) {

                pageBean.putValue("classId", param);
                analyzerComponent.addParamEntry("class", classCTVO.getCode());
            }

            clientInformation.put("class", classCTVO.getCode());
		}

		param = aAppReqBlock.getReqParm("relationshipInd");
        CodeTableVO relationshipCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));
        String relationship = relationshipCTVO.getCode();

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("relationshipId", param);
			analyzerComponent.addParamEntry("relationshipind", relationship);
		}

        clientInformation.put("relationshipind", relationship);

		//set client counter according to relationShip
		if (relationship.equals("ANN"))
		{
			analyzerComponent.addParamEntry("annuitantcounter", "1");
		}

		else if (relationship.equals("OWN")) {

			analyzerComponent.addParamEntry("ownercounter", "1");
		}
		else if (relationship.equals("PAY")) {

			analyzerComponent.addParamEntry("payeecounter", "1");
		}

		param = aAppReqBlock.getReqParm("disbursementSource");
        if (!param.equalsIgnoreCase("Please Select") &&
            !param.equals("")) {

            CodeTableVO disbursementCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));
            String disbursementCode = disbursementCTVO.getCode();
            String disbursementSource = null;

            if (disbursementCode.equalsIgnoreCase("C")) {

                disbursementSource = "Check";
            }
            else if (disbursementCode.equalsIgnoreCase("E")) {

                disbursementSource = "EFT";
            }
            else {

                disbursementSource = "Hold";
            }

            if ((param != null) && (param.trim().length() > 0)) {

                pageBean.putValue("disburseSourceId", param);
                analyzerComponent.addParamEntry("disbursementsource", disbursementSource);
            }

            clientInformation.put("disbursementsource", disbursementSource);
        }

		param = aAppReqBlock.getReqParm("taxFilingStatus");
        CodeTableVO taxFilingStatusCTVO = codeTableWrapper.getCodeTableEntry(Long.parseLong(param));
		String taxFilingStatus = taxFilingStatusCTVO.getCode();

        Lookup lookupComponent = (engine.business.Lookup) aAppReqBlock.getWebService("engine-lookup");

	    FundVO[] fundVOs = lookupComponent.getFundsBYCSId(productStructurePK);

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("taxFilingStatusId", param);
			analyzerComponent.addParamEntry("taxfilingstatus", taxFilingStatus);
		}

		clientInformation.put("taxfilingstatus", taxFilingStatus);

		param = aAppReqBlock.getReqParm("payeeAllocation");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("payeeAllocation", aAppReqBlock.getReqParm("payeeAllocation"));
			analyzerComponent.addParamEntry("payeeallocation", param);
		}

		clientInformation.put("payeeallocation", param);

		param = aAppReqBlock.getReqParm("fedWithholdingOvrd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("fedWithholdingOvrd", aAppReqBlock.getReqParm("fedWithholdingOvrd"));
			analyzerComponent.addParamEntry("fedwithholdingovrd", param);
		}

		param = aAppReqBlock.getReqParm("fedwithholdingInd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("fedwithholdingInd", aAppReqBlock.getReqParm("fedwithholdingInd"));
			analyzerComponent.addParamEntry("fedwithholdingind", param);
		}

		param = aAppReqBlock.getReqParm("stateWithholdingOvrd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("stateWithholdingOvrd", aAppReqBlock.getReqParm("stateWithholdingOvrd"));
			analyzerComponent.addParamEntry("statewithholdingovrd", param);
		}

		param = aAppReqBlock.getReqParm("statewithholdingInd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("statewithholdingInd", aAppReqBlock.getReqParm("statewithholdingInd"));
			analyzerComponent.addParamEntry("statewithholdingind", param);
		}

		param = aAppReqBlock.getReqParm("cityWithholdingOvrd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("cityWithholdingOvrd", aAppReqBlock.getReqParm("cityWithholdingOvrd"));
			analyzerComponent.addParamEntry("citywithholdingovrd", param);
		}

		param = aAppReqBlock.getReqParm("citywithholdingInd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("citywithholdingInd", aAppReqBlock.getReqParm("citywithholdingInd"));
			analyzerComponent.addParamEntry("citywithholdingind", param);
		}

		param = aAppReqBlock.getReqParm("countyWithholdingOvrd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("countyWithholdingOvrd", aAppReqBlock.getReqParm("countyWithholdingOvrd"));
			analyzerComponent.addParamEntry("countywithholdingovrd", param);
		}

		param = aAppReqBlock.getReqParm("countywithholdingInd");

		if ((param != null) && (param.trim().length() > 0)) {

			pageBean.putValue("countywithholdingInd", aAppReqBlock.getReqParm("countywithholdingInd"));
			analyzerComponent.addParamEntry("countywithholdingind", param);
		}

        List funds = new ArrayList();
        List fundValues = new ArrayList();

		param = aAppReqBlock.getReqParm("fund1");
		if (!(param.equalsIgnoreCase("Please Select"))) {

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("fund1", aAppReqBlock.getReqParm("fund1"));
                long numericFundId = Long.parseLong(param);
                fundValues.add(param);
                String fundType = getFundTypeForFund(fundVOs, numericFundId);
                fundValues.add(fundType);
			}

			param = aAppReqBlock.getReqParm("allocationPct1");

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("allocationPct1", aAppReqBlock.getReqParm("allocationPct1"));
                fundValues.add(param);
			}
		}

		else {

			pageBean.putValue("fund1", aAppReqBlock.getReqParm("fund1"));
			pageBean.putValue("allocationPct1", aAppReqBlock.getReqParm("allocationPct1"));
		}

		param = aAppReqBlock.getReqParm("fund2");

		if (!(param.equalsIgnoreCase("Please Select"))) {

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("fund2", aAppReqBlock.getReqParm("fund2"));
                long numericFundId = Long.parseLong(param);
                fundValues.add(param);
                String fundType = getFundTypeForFund(fundVOs,numericFundId);
                fundValues.add(fundType);
 			}

			param = aAppReqBlock.getReqParm("allocationPct2");

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("allocationPct2", aAppReqBlock.getReqParm("allocationPct2"));
                fundValues.add(param);
			}
		}

		else {

			pageBean.putValue("fund2", aAppReqBlock.getReqParm("fund2"));
			pageBean.putValue("allocationPct2", aAppReqBlock.getReqParm("allocationPct2"));
		}

		param = aAppReqBlock.getReqParm("fund3");

		if (!(param.equalsIgnoreCase("Please Select"))) {

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("fund3", aAppReqBlock.getReqParm("fund3"));
                long numericFundId = Long.parseLong(param);
                fundValues.add(param);
                String fundType = getFundTypeForFund(fundVOs,numericFundId);
                fundValues.add(fundType);
			}

			param = aAppReqBlock.getReqParm("allocationPct3");

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("allocationPct3", aAppReqBlock.getReqParm("allocationPct3"));
                fundValues.add(param);
			}
		}

		else {

			pageBean.putValue("fund3", aAppReqBlock.getReqParm("fund3"));
			pageBean.putValue("allocationPct3", aAppReqBlock.getReqParm("allocationPct3"));
		}

		param = aAppReqBlock.getReqParm("fund4");

		if (!(param.equalsIgnoreCase("Please Select"))) {

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("fund4", aAppReqBlock.getReqParm("fund4"));
                long numericFundId = Long.parseLong(param);
                fundValues.add(param);
                String fundType = getFundTypeForFund(fundVOs,numericFundId);
                fundValues.add(fundType);
			}

			param = aAppReqBlock.getReqParm("allocationPct4");

			if ((param != null) && (param.trim().length() > 0)) {

				pageBean.putValue("allocationPct4", aAppReqBlock.getReqParm("allocationPct4"));
                fundValues.add(param);
			}
		}

		else {

			pageBean.putValue("fund4", aAppReqBlock.getReqParm("fund4"));
			pageBean.putValue("allocationPct4", aAppReqBlock.getReqParm("allocationPct4"));
		}

        // get values into hashtable in a vector
        int vectorSize = fundValues.size();
        int fundSub = 0;
        for (int i = 0; i < vectorSize; i++) {

            Map fundInformation = new HashMap();
            String parama = (String) fundValues.get(fundSub);
            Long paramLong = new Long(parama);
            i++;
            String paramb = (String) fundValues.get(fundSub += 1);  i++;
            String paramc = (String) fundValues.get(fundSub += 1);
            fundSub += 1;

            fundInformation.put("fund", paramLong);
            fundInformation.put("fundtype", paramb);
            fundInformation.put("allocationpct", paramc);

            fundInformation.put("dollars", new Double(0));
            fundInformation.put("units", new Double(0));
            analyzerComponent.addParamEntry("allocoverride", "Y");
            fundInformation.put("cumdollars", new Double(1000));
            fundInformation.put("cumunits", new Double(100));
            fundInformation.put("pounits", new Double(50));
            fundInformation.put("podollars", new Double(1200));
            fundInformation.put("air", new Double(0));

            String lastValDate = "2002/01/01";
            fundInformation.put("lastvaldate", lastValDate);

            //default other fund fields
            fundInformation.put("depositdate", null);
            fundInformation.put("excessintcalcdate", null);
            fundInformation.put("excessintpymtdate", null);
            fundInformation.put("excessinterest", "0");
            fundInformation.put("excessintmethod", "");
            fundInformation.put("excessintstartdate", null);

            funds.add(fundInformation);
        }

        analyzerComponent.addParamEntry("funds",funds);

        analyzerComponent.addParamEntry("fundcounter", (funds.size() + ""));

		//add client information
		owners.add(clientInformation);
		annuitants.add(clientInformation);
        payees.add(clientInformation);
        beneficiaries.add(clientInformation);
        trustees.add(clientInformation);
        checkBankPayors.add(clientInformation);
        eftBankPayors.add(clientInformation);

		analyzerComponent.addParamEntry("annuitants", annuitants);

		analyzerComponent.addParamEntry("payees", payees);

		analyzerComponent.addParamEntry("owners", owners);

		analyzerComponent.addParamEntry("selectedindex", "1");

        //parameters needed for geterror
        long trxid = 173;
        long longZero = 0;
        analyzerComponent.addParamEntry("segmentname", trxid + "");
        analyzerComponent.addParamEntry("amount", "0");
        analyzerComponent.addParamEntry("reducepercent1", "0");
        analyzerComponent.addParamEntry("expectedreturn", "0");
        analyzerComponent.addParamEntry("exclusionamount", "0");
        analyzerComponent.addParamEntry("trxid", longZero + "");
        analyzerComponent.addParamEntry("contractnumber", "debug");
        analyzerComponent.addParamEntry("trxsequence", "0");
        analyzerComponent.addParamEntry("trxstatus", "");

		paramBean.putValue("keyIsSet", "true");

		paramBean.putValue("rulesPK", aAppReqBlock.getReqParm("rulesPK"));

		long rulesPK = Long.parseLong(paramBean.getValue("rulesPK"));

		RulesVO[] rulesVO = lookupComponent.getRuleByRuleId(rulesPK);

//		RuleStructureVO[] ruleStructureVO = (RuleStructureVO[]) Util.unmarshalVOs(RuleStructureVO.class,
//                                              lookupComponent.getByRuleStructureId(rulesVO[0].getRuleStructureFK()));

        String event = "N";

        setKeyValues(aAppReqBlock
                     ,productStructurePK
                     ,rulesPK
                     ,rulesVO[0].getProcessName()
                     ,event
                     ,payoutOption
                     ,effectiveDate.getFormattedDate()
                     ,rulesVO[0].getRuleName());
	}

    private String getFundTypeForFund(FundVO[] fundVOs, long fundId) throws Exception {

        String fundType = null;

        for (int i = 0; i < fundVOs.length; i++) {

            long fund = fundVOs[i].getFundPK();
            if (fundId == fund) {

                fundType =  fundVOs[i].getFundType();
                break;
            }
        }

        return fundType;
    }
}