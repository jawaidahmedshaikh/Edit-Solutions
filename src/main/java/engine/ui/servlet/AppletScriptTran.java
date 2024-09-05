package engine.ui.servlet;

import edit.common.vo.*;
import edit.portal.common.transactions.Transaction;
import engine.business.Calculator;
import engine.business.Lookup;
import fission.beans.DataResultBean;
import fission.global.AppReqBlock;

import java.util.ArrayList;
import java.util.List;

public class AppletScriptTran extends Transaction {

    private static final String ACTION        = "action";
    //private static final String A_SCRIPT_BEAN = "aScriptBean";
    private static final String SCRIPT_LC     = "script";
    //private static final String SCRIPT_ID_UC  = "ScriptId";
    private static final String SCRIPT_ID_LC  = "scriptId";
    private static final String SCRIPT_NAME   = "scriptName";
    private static final String SCRIPT_NAME_NEEDS_TO_BE_ENTERED = "Script Name needs to be entered";
    //private static final String SCRIPT_BEAN_INSTRUCTIONS = "ScriptBeanInstructions";
    //private static final String SCRIPT_BEAN_SCRIPTS = "ScriptBeanScripts";
    //private static final String TABLE_BEAN_TABLES = "TableBeanTables";
    //private static final String SCRIPT_NAME_ALREADY_EXISTS  = "Script Name Already Exists";
    public static final String ACTION_SHOWENHANCEDNEW = "showEnhancedScriptNew";
	public static final String ACTION_SHOWENHANCEDMOD = "showEnhancedScriptMod";
	public static final String ACTION_SHOW_ENHANCED_DEFAULT = "showEnhancedScriptDefault";
	public static final String ACTION_CANCEL = "actionCancel";
	public static final String ACTION_APPLY = "actionApply";
	public static final String ACTION_SAVE = "actionSave";
    public static final String ACTION_DELETE = "actionDelete";
	DataResultBean dataBean = new DataResultBean();
	// -------------------------------------------------------
	//						public methods
	// -------------------------------------------------------


    public String execute(AppReqBlock aAppReqBlock) throws Exception  {

       	String action = aAppReqBlock.getReqParm(ACTION);

//		ScriptBean aScriptBean = new ScriptBean();

		if (ACTION_SHOWENHANCEDNEW.equalsIgnoreCase(action))  {
           actionShowEnhancedNew(aAppReqBlock);
           return "appletData";
		}
		else if (ACTION_SHOW_ENHANCED_DEFAULT.equals(action)) {

			actionShowEnhancedScriptDefault(aAppReqBlock);
			return "appletData";
		}
		else if (ACTION_SHOWENHANCEDMOD.equalsIgnoreCase(action)) {
           	actionShowEnhancedMod(aAppReqBlock);
            return "appletData";
        }
		else if (ACTION_CANCEL.equalsIgnoreCase(action)) {

			//unlockScript(aAppReqBlock);
			return "appletData";
        }
		else if (ACTION_SAVE.equalsIgnoreCase(action))				 {

			String scriptText = aAppReqBlock.getReqParm("script");

			saveScript(scriptText,aAppReqBlock,false);
			return "appletData";
		}
        else if (ACTION_DELETE.equalsIgnoreCase(action))				 {

			deleteScript(aAppReqBlock);
			return "appletData";
		}
		else  {
            throw new Exception("AppletScriptTran :: invalid action " + action);
        }
    }

	// -------------------------------------------------------
	//						private methods
	// -------------------------------------------------------

    // Returns the script in a ScriptBean
//    private ScriptBean getScriptByName(String scriptName)
//            throws Exception {
//
//        ScriptBean sb = new ScriptBean();
//        DDGroupItem ddScript = ed.getDDScriptByName(scriptName);
//        sb.setId(((DDNumber)ddScript.find(SCRIPT_ID_UC)).getInt());
//        sb.setName(scriptName);
//        sb.setScript(ed.getScriptLines(sb.getId()));
//        return sb;

//		return null;

//    }

    /*
	private void unlockScript(AppReqBlock aAppReqBlock)
            throws Exception {

       // doScriptEdits(aAppReqBlock);
//        ed.releaseScript(aAppReqBlock.getReqParm(SCRIPT_NAME));
//        ScriptBean aScriptBean = new ScriptBean();
//        aScriptBean.setScriptTree(ed.getScriptTree());
       // ((HttpSession)aAppReqBlock.getHttpSession()).setAttribute(A_SCRIPT_BEAN,aScriptBean);

    }
    */

	private void doScriptEdits(AppReqBlock aAppReqBlock) throws Exception  {

		String sname = null;
        sname = aAppReqBlock.getReqParm(SCRIPT_NAME);

		//if scriptName is null check that whether its
		//stored in scriptId parameter
		if ( (sname == null) || ((sname.trim()).length() == 0 )) {
			sname =aAppReqBlock.getReqParm(SCRIPT_ID_LC);
		}

        if ((sname == null) || ((sname.trim()).length() == 0))  {
            throw new Exception(SCRIPT_NAME_NEEDS_TO_BE_ENTERED);
        }
    }

    private void saveScript(String scriptText, AppReqBlock appReqBlock,
            boolean refreshScript) throws Exception {

        doScriptEdits(appReqBlock);
        String scriptName = appReqBlock.getReqParm(SCRIPT_NAME);
        isValidScriptName(scriptName);

	    long scriptId = Long.parseLong((appReqBlock.getReqParm(SCRIPT_ID_LC)).trim());

		//get Calculator component
        Calculator calcComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

		//long newScriptId = calcComponent.saveScript(scriptId, scriptName, scriptText);

        //dataBean.setObjectBy(SCRIPT_ID_LC, newScriptId + "");
        appReqBlock.getHttpServletRequest().setAttribute("dataBean", dataBean);

	}

    private DataResultBean deleteScript(AppReqBlock appReqBlock) throws Exception {

	    long scriptId = Long.parseLong((appReqBlock.getReqParm(SCRIPT_ID_LC)).trim());

		//get Calculator component
        Calculator calcComponent = (engine.business.Calculator) appReqBlock.getWebService("engine-service");

        String message = "";

		try {

            calcComponent.deleteScript(scriptId);

            dataBean.setObjectBy("message", "Script Deleted Successfully");

            appReqBlock.getHttpServletRequest().setAttribute("dataBean", dataBean);
        }

        catch (Exception e) {

          System.out.println(e);

            e.printStackTrace();

            message = e.getMessage();

            dataBean.setObjectBy("message", message);

            appReqBlock.getHttpServletRequest().setAttribute("dataBean", dataBean);
        }

        return dataBean;
	}

    private void actionShowEnhancedMod(AppReqBlock appReqBlock) throws Exception {

		String scriptName = appReqBlock.getReqParm(SCRIPT_NAME);

		//get lookup component from request
        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

        ScriptVO[] scriptVO = lookup.getScriptByName(scriptName);

        String scriptId = scriptVO[0].getScriptPK() + "";
		ScriptLineVO[] scriptLinesVO = scriptVO[0].getScriptLineVO();

		List scriptLines = new ArrayList();
		for(int i=0; i<scriptLinesVO.length; ++i) {

			String scriptLine = scriptLinesVO[i].getScriptLine();
			scriptLines.add(scriptLine);
		}

		dataBean.setObjectBy(SCRIPT_LC, scriptLines);
        dataBean.setObjectBy(SCRIPT_ID_LC, scriptId);
		appReqBlock.getHttpServletRequest().setAttribute("dataBean", dataBean);
	}

	protected void actionShowEnhancedScriptDefault(AppReqBlock appReqBlock)
														    throws Exception  {
		//if duplicate scriptName then throw an error
		boolean duplicate = checkForDuplicateScriptName(appReqBlock);

		// build dataBean and populate it with default scriptLines

		List scriptLines = new ArrayList();

		scriptLines.add("exit");

		dataBean.setObjectBy(SCRIPT_LC, scriptLines);
        dataBean.setObjectBy(SCRIPT_ID_LC, "0");

		appReqBlock.getHttpServletRequest().setAttribute("dataBean", dataBean);
	}

  	private void actionShowEnhancedNew(AppReqBlock appReqBlock) throws Exception {

        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

		ScriptVO[] scriptVO = lookup.getAllScriptNames();

		ScriptInstructionVO[] scriptInstructionVO = lookup.getAllScriptInstructions();

		TableDefVO[] tableDefVO = lookup.getAllTableNames();

        RulesVO[] rulesVO = lookup.getAllRules();

		List scriptNames = new ArrayList();
		for(int i=0; i<scriptVO.length; ++i){
			scriptNames.add((scriptVO[i].getScriptName()).trim());
		}

		List scriptInstruction = new ArrayList();
		for(int i=0; i<scriptInstructionVO.length; ++i){
			scriptInstruction.add((scriptInstructionVO[i].getInstruction()).trim());
		}

		List scriptId = new ArrayList();
		for(int i=0; i<scriptVO.length; ++i){
			scriptId.add((scriptVO[i].getScriptPK())+"");
		}

		List tableNames = new ArrayList();
		for(int i=0; i<tableDefVO.length; ++i){
			tableNames.add((tableDefVO[i].getTableName()).trim());
		}

        List scriptRules = new ArrayList();
        List tableRules  = new ArrayList();
        for (int i = 0; i < rulesVO.length; ++i) {

            String ruleName = rulesVO[i].getRuleName();
            if (ruleName.startsWith("TB")) {

                boolean nameFound = false;
                for (int j = 0; j < tableRules.size(); j++) {

                    String vectorName = (String) tableRules.get(j);

                    if (vectorName.equals(ruleName)) {

                        nameFound = true;
                        j = tableRules.size();
                    }
                }

                if (!nameFound) {

                    tableRules.add(ruleName.trim());
                }
            }

            else {

                boolean nameFound = false;
                for (int j = 0; j < scriptRules.size(); j++) {

                    String vectorName = (String) scriptRules.get(j);

                    if (vectorName.equals(ruleName)) {

                        nameFound = true;
                        j = scriptRules.size();
                    }
                }

                if (!nameFound) {

                    scriptRules.add(ruleName.trim());
                }
            }
        }

		dataBean.setObjectBy("ScriptBeanInstuctions",scriptInstruction);
        dataBean.setObjectBy("ScriptBeanScripts",scriptNames);
        dataBean.setObjectBy("TableBeanTables",tableNames);
        dataBean.setObjectBy("RuleBeanTableRules", tableRules);
        dataBean.setObjectBy("RuleBeanScriptRules", scriptRules);
		appReqBlock.getHttpServletRequest().setAttribute("dataBean", dataBean);

	 }

	public boolean checkForDuplicateScriptName(AppReqBlock appReqBlock) throws Exception {

		String scriptName = appReqBlock.getReqParm(SCRIPT_NAME);

		//get all script names
		//Lookup lookup = (Lookup) appReqBlock.getLocalBusinessService("lookupComponent");
        Lookup lookup = (engine.business.Lookup) appReqBlock.getWebService("engine-lookup");

		ScriptVO[] scriptVO = lookup.getAllScriptNames();
		for(int i=0; i<scriptVO.length; ++i)
		{
			String name = scriptVO[i].getScriptName();
			if (name.equalsIgnoreCase(scriptName))
			{
				return true;
			}
			else
			{
				continue;
			}
		}
		return false;
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
     * @return boolean set to TRUE if script name passed in
     *         the constructor is valid (begins with a letter
     *         and contains only letters and numbers - no
     *         special characters allowed)
     */
    public static boolean isValidScriptName(String name) throws Exception {

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
        return true;

    }

 }