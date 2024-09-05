/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

import logging.Log;
import logging.LogEvent;

import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.Session;

import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.ScriptChainNodeWrapper;
import edit.common.vo.EDITExport;
import edit.common.vo.RulesVO;
import edit.common.vo.ScriptLineVO;
import edit.common.vo.ScriptVO;
import edit.common.vo.VOObject;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import engine.dm.StorageManager;
import engine.dm.dao.DAOFactory;
import engine.sp.custom.document.PRASEDocBuilder;

/**
 * The ScriptProcessor functions as an insurance calculator engine. A script is
 * a calculation made up of very basic instructions such as Add, Mult, Div,
 * Etc... A script can be loaded and processed in the ScriptProcessor. A data
 * stack (stack) will be used to calculate the result of the script.
 */
public class ScriptProcessorImpl implements ScriptProcessor {
	// Turns on/off debug writes to the screen
	private boolean debugConsole = false;
	private boolean debugOutfile = Boolean.parseBoolean(System.getProperty("debugScripts"));

	private boolean createConversionErrorFile = false;
	private String debugFile;
	private String conversionErrorFile;

	private static final String BASE_PARM = "BASEPARM";
	private static final String FINANCIAL_SEGMENT_COUNTER = "financialsegcounter";
	private static final String INTEREST_RATE = "interestrate";
	private static final String OPTION7702 = "option7702";
	private static final String PREM_PAY_TERM = "prempayterm";
	private static final String RESIDENT_STATE = "residentstate";
	private static final String TABLE_RATING = "tablerating";
	int TRANSACTION_READ_COMMITTED = 2;
	private boolean abortOnHardValidationError;

	// Used to store aliases to DDs
	private Map aliases = new HashMap();
	private boolean analyzerMode = true;

	// Used to store break points for debugging
	private Map breakPoints = new HashMap();

	// Used to Store instrPtr during a subroutine call
	private Stack<Integer> callReturn = new Stack<Integer>();

	// Used to direct the execution of if/else/endif
	private boolean condition = false;

	// Used to position script row in jsp
	// (current instruction or when setting break point)
	private int currentRow = 0;

	// Used to store errors that have occurred during script processing
	private List errorLog = new ArrayList();

	// Used to trigger an error occurred during script processing
	private boolean errorOccurred = false;

	// Used to identify between different FinancialTypes
	private String financialType = new String("BASEPARM");

	// Used to hold the current index of a mulptiple occurring node
	protected Map foreachHT = new HashMap();

	// Used to store functions (rate table data)
	private Map functions = new HashMap();

	// Used to hold the key field for outputted data items
	private Map identifier = new HashMap();

	// Use to track if/else and endif
	protected Stack<Boolean> ifStack = new Stack<Boolean>();
	private Map innerScriptLines = new HashMap();

	// Used as instruction pointer while processing script
	private int instPtr = 0;

	// Used to store labels with an instruction pointer
	// pointing to it's location
	private Map labels = new HashMap();
	private int lastInstPtr = 0;

	// Used to store instrPtr during looping process
	protected Stack<Integer> loopStack = new Stack<Integer>();
	private Map newNodes = new HashMap();
	private boolean noEditingException = false;

	// private static int nodeCount = 0;
	private int nodeCount = 0;
	private Map outputData = new HashMap();

	// Used to store the selected Rider parameters
	Map param = new HashMap();

	// Used to store parameters for debugging
	private List params = new ArrayList();
	private ProductRuleProcessor pr;
	private boolean processedWithErrors;

	// Name values pairs supplied by the Context instruction.
	private Map context = new HashMap();

	/**
	 * Documents that are built are to placed within the document space of this
	 * ScriptProcessor made accessible be a key that every PRASEDocBuilder
	 * self-generates.
	 */
	private List<PRASEDocBuilder> praseDocBuilders = new ArrayList<PRASEDocBuilder>();

	/**
	 * Manages the collection and storing of test data should the user want to
	 * capture the state(s) of running of this ScriptProcessor to store for
	 * future testing.
	 */
	private SPTest spTest = new SPTest();

	/**
	 * Records the steps executed while processing the script
	 */
	private SPRecorder spRecorder = new SPRecorder();

	/**
	 * CompiledScripts (those which have been cached and now borrowed) need to
	 * be returned to the pool of CompiledScripts. We will likely do this in
	 */
	private List<CompiledScript> borrowedCompiledScripts = new ArrayList<CompiledScript>();

	/**
	 * A separate Hibernate Session dedicated to this ScriptProcessor dedicated
	 * to the EDITSOLUTIONS DB as it is believed this will be the only DB
	 * targeted.
	 */
	private Session hibernateSession;

	/**
	 * Check script syntax
	 * <p>
	 * 
	 * @param scriptIn
	 *            The script requiring syntax checking
	 * @return Returns message stating the status of the syntax check
	 * @exception Exception
	 *                is thrown if there is a syntax error
	 */

	// public static String checkScript(List scriptIn) throws Exception {
	//
	// ScriptProcessor sp = new ScriptProcessor();
	// String message = "No errors...";
	//
	// try {
	// Iterator script = scriptIn.elements();
	// while (script.hasNext()) {
	// String aScriptLine = (String) script.next();
	//
	// // Bypass blank lines
	// if ((aScriptLine.trim()).length() == 0) {
	// continue;
	// }
	//
	// Inst inst = sp.makeInstObject(aScriptLine);
	// inst.compile(sp);
	// }
	//
	// } catch (Exception e) {
	// message = "Syntax Error: " + e.getMessage() + "\n";
	// e.printStackTrace();
	// }
	//
	// return message;
	//
	// }
	//

	/**
	 * Retrieves the script from the DataManager, builds instructions, and
	 * places the instructions in the script holder (List).
	 * <p>
	 * 
	 * @param scriptName
	 *            Name of script to be loaded
	 * @exception SMException
	 *                If there is an error in the DataManager
	 */

	// public void loadScript(String scriptNameIn) throws Exception {
	//
	//
	//
	// try {
	// long scriptId = 0;
	// ScriptVO[] scriptVO =
	// DAOFactory.getScriptDAO().findScriptByName(scriptName);
	// scriptId = scriptVO[0].getScriptPK();
	// loadScript(scriptId);
	//
	// }
	// catch (Exception e) {
	// System.out.println(e.getMessage());
	// e.printStackTrace();
	// throw e;
	// }
	// }

	/**
	 * This method of loadScript takes in the scriptid, then gets the scriptline
	 * requested
	 */
	private ScriptChainNodeWrapper root = null;
	long saveScriptId = 0;

	/**
	 * Stores error-output of instructions that can accomdate.
	 */

	// private List errorOutput = new ArrayList();
	// Used to store the script to be processed
	private List script = new ArrayList();

	// Used to check that script is loaded or not
	private boolean scriptLoaded = false;

	// Member Variables:
	private String scriptName;

	// Used to identify the selected Rider
	private int selectedIndex = 0;

	// Probably was meant to be used on script debugging screen?
	private boolean singleStep = false;
	private StorageManager sm;
	private SPOutput spOutput;
	private SPParams spParams = null;

	// Used as Data Stack
	private Stack<String> stack = new Stack<String>();

	// Used to force the script to stop running
	private boolean stopRun = false;

	// Used to store vectors (calc data)
	private Map vectorTable = new HashMap();

	// Used to determine which viewer to display
	private String viewerMode = new String();

	// Working Storage Area
	private Map<String, String> ws = new HashMap<String, String>();

	// Used to store RateDateTable created by getinterestrate
	private Map wsHashtable = new HashMap();
	
	// Stores custom script maps
	private Map<String, Map> dataMapStorage = new HashMap<>();

	/************************************** Constructor Methods **************************************/

	/**
	 * ScriptProcessor constructor
	 */
	public ScriptProcessorImpl() {
		pr = new ProductRuleProcessor();
		spParams = new SPParams();
		spOutput = new SPOutput();
		this.abortOnHardValidationError = true; // Default
		
		if (debugOutfile) {
			debugFile = getExportFile();
		}
		
		if (createConversionErrorFile) {
			conversionErrorFile = this.getConversionErrorFile();
		}
	}

	/************************************** Public Methods **************************************/

	/**
	 * Places the passed in information onto the alias Map
	 * <p/>
	 * 
	 * @param aliasName
	 *            Name of alias
	 * @param dataName
	 *            Data name associated with alias
	 */
	public void addAliasEntry(String aliasName, String dataName) {
		aliases.put(aliasName, dataName);
	}

	/**
	 * Places the passed in information onto the the break point Map
	 * <p/>
	 * 
	 * @param name
	 *            instruction line number
	 * @param data
	 *            DDBoolean set to true if breakpoint has been processed
	 */
	public void addBreakPointEntry(String name, Boolean data) {
		breakPoints.put(name, data);

		setCurrentRow(Integer.parseInt(name));
	}

	/**
	 * DOM Elements that come in from the source XML are placed on this stack
	 * when the Element changes, or is created as new.
	 * 
	 * @param element
	 */
	public void addCalculationOutput(Element element) {
		spOutput.addCalculationOutput(element);
	}

	public void addForeachEntry(String name, Object data) {
		foreachHT.put(name, data);
	}

	/**
	 * Places the passed in information onto the function Map
	 * <p/>
	 */
	public void addFunctionEntry(String tableName, List rateTable) {
		functions.put(tableName, rateTable);
	}

	public void addIdentifierEntry(String fieldPath, String id) {
		identifier.put(fieldPath, id);
	}

	public void addNewElement(String childPath) {
		Element newElement = spParams.addNewElement(childPath);

		addCalculationOutput(newElement);
	}

	public void addNewElementValue(String fieldPath, String fieldName,
			String data) {
		Element newElement = spParams.setNewElementValue(fieldPath, fieldName,
				data);

		addCalculationOutput(newElement);
	}

	/**
	 * For the last-active Element defined by the specified elementPath, the
	 * specified attribute will be created/updated with the specified attribute
	 * value. Since the basic DOMs are Element driven (not Attribute driven), we
	 * deliberately don't add attribute-level changes to the calculation
	 * outputs.
	 * 
	 * @param elementPath
	 * @param attributeName
	 * @param attributeValue
	 */
	public void addNewElementAttribute(String elementPath,
			String attributeName, String attributeValue) {
		spParams.setNewAttributeValue(elementPath, attributeName,
				attributeValue);
	}

	/**
	 * Places the passed in information into the parameter area Map
	 * <p/>
	 * 
	 * @param name
	 *            The name of the parameter
	 * @param data
	 *            The value of the parameter
	 */
	public void addParamEntry(String name, String data) {
		Map financialTypeData = getFinancialTypeData(getFinancialType(),
				getSelectedIndex());

		financialTypeData.put(name, data);
	}

	public void addParamEntry(String name, List vector) {
		Map financialTypeData = getFinancialTypeData(getFinancialType(),
				getSelectedIndex());

		financialTypeData.put(name, vector);
	}

	/**
	 * Instructions which error [can] place error information on this stack for
	 * client viewing.
	 * 
	 * @param element
	 */
	public void addValidationOutput(Element element) {
		spOutput.addValidationOutput(element);
	}

	/**
	 * Places the passed in information onto the vector Map
	 * <p/>
	 * 
	 * @param data
	 *            The vector data
	 */
	public void addVectorEntry(String name, Map vector) {
		vectorTable.put(name, vector);
	}

	/**
	 * Places the passed in information onto the working storage (ws) Map
	 * <p/>
	 * 
	 * @param name
	 *            The name of the Object
	 * @param data
	 *            The value of the object
	 */
	public void addWSEntry(String name, String data) {
		// System.out.println("         [ws entry: " + name + "=" + data + "]");
		ws.put(name, data);
	}

	public void addWSVector(String name, List vector) {
		wsHashtable.put(name, vector);
	}

	/**
	 * Used in debug process to clear all structures in the ScriptProcessor. It
	 * also invoked when the ScriptProcessor is returned to the pool (we
	 * actually pool ScriptProcessors via the ScriptProcessorFactory). If you
	 * look at the ScriptProcessorPoolableObjectFactory, you will see that this
	 * clear() method is called upon passivation of the ScriptProcessor.
	 */
	public void clear() {
		stack.removeAllElements();
		callReturn.removeAllElements();
		loopStack.removeAllElements();
		ifStack.removeAllElements();
		ws.clear();
		functions.clear();
		breakPoints.clear();
		labels.clear();
		aliases.clear();
		newNodes.clear();
		script.clear();
		spOutput = new SPOutput();
		outputData.clear();
		foreachHT.clear();
		identifier.clear();
		instPtr = 0;
		lastInstPtr = 0;
		currentRow = 0;
		singleStep = false;
		stopRun = false;
		pr.clearProductRuleProcessorValues();
		spParams.clear();
		abortOnHardValidationError = false;
		context.clear();
		vectorTable.clear();
		praseDocBuilders.clear();

		// Return all borrowed CompiledScript(s) back to the pool
		for (CompiledScript borrowedCompiledScript : borrowedCompiledScripts) {
			CSCache.getCSCache().returnCompiledScript(borrowedCompiledScript);
		}

		borrowedCompiledScripts.clear();

		clearHibernateSession();
	}

	/**
	 * Clears-out and nulls the Hibernate Session associated with this
	 * ScriptProcessor.
	 */
	private void clearHibernateSession() {
		if (hibernateSession != null) {
			hibernateSession.clear();

			hibernateSession.close();

			hibernateSession = null;
		}
	}

	// syam lingamallu 10/25/2001

	/**
	 * ******************************************************************** this
	 * method is different from remove rider bcos removeRider() method removes
	 * entire element from params vector where as this just removes all
	 * parameters but will not remove the element.
	 * *********************************************************************
	 */
	public void clearRiderParameters(String selectedRider, int selectedIndex) {
		Map defaultRiderData = setDefaultData(selectedRider);

		int size = params.size();

		if ((size != 0) && (size > selectedIndex)) {
			params.add(selectedIndex, defaultRiderData);
		}
	}

	/**
	 * Returns this instance to its pool
	 */
	public void close() {
		ScriptProcessorFactory.getSingleton().returnScriptProcessor(this);
	}

	/**
	 * Clears all Break points
	 */

	// public void clearBreakPoints() {
	//
	// breakPoints.clear();
	// }

	/**
	 * Returns true if the passed in parameter name exists
	 */

	// public boolean containsParamKey(String name) {
	//
	// //get the index
	// // int index = ((Integer)popFromStack()).intValue();
	// int index = Integer.parseInt(popFromStack());
	// // int index = ((DDNumber)pop()).getInt();
	//
	// int size = params.size();
	// Map baseParam = new HashMap();
	// //int segmentCounter = 0;
	// int segmentCount = 0;
	//
	// if (size != 0) {
	// baseParam = (Map)params.get(0);
	// if (baseParam.containsKey(FINANCIAL_SEGMENT_COUNTER)) {
	//
	// //FINANCIAL_SEGMENT_COUNTER value is the total number
	// //of elements in param vector
	// segmentCount =
	// ((DDNumber)baseParam.get(FINANCIAL_SEGMENT_COUNTER)).getInt();
	//
	// }
	// }
	// if (index <= segmentCount) {
	//
	// //if suppose the index given by user is 1
	// //then it means the user want to use the parameters of first
	// //element of params vector and in order to use
	// //the first element parameters we have to decrease the index by 1
	// //bec first element of vector is at 0th index of params vector
	// Map paramData = (Map)params.get(index - 1);
	//
	// param = paramData;
	// //boolean b = paramData.containsKey(name);
	// if(paramData.containsKey(name)) {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * Returns true if the passed in parameter name exists
	 */
	public boolean containsBreakPointKey(String name) {
		return breakPoints.containsKey(name);
	}

	public boolean containsLabelKey(String name) {
		return labels.containsKey(name);
	}

	/**
	 * This method will execute the script until the stopRun variable is set to
	 * true.
	 * <p/>
	 * 
	 * @throws SPException
	 *             If instruction pointer is pointing outside script (illegal
	 *             script address)
	 */
	public void exec() throws SPException {
		EDITDateTime scriptProcessDateTime = new EDITDateTime();

		boolean shouldAbortCurrentScript = false;

		processedWithErrors = false;

		boolean processedWithValidationErrors = false;

		if (spRecorder.shouldRecord()) {
			spRecorder.loadRunInformation(scriptProcessDateTime, this.pr);
		}

		if (!isBreakPointEmpty()) {
			execWithBreakPoints();

			return;
		}

		Inst inst = null;

		setStopRun(false);

		try {
			while (!isStopRun()) {
				inst = (Inst) script.get(instPtr);

				if (debugConsole) {
					System.out.println(inst.getInstAsEntered());
				}

				if (debugOutfile) {
					try (PrintWriter out = new PrintWriter(new BufferedWriter(
							new FileWriter(debugFile, true)))) {
						out.println(inst.getInstAsEntered());
					} catch (IOException e) {
						System.out.println("IOException: " + e.getMessage());
					}
				}

				if (spRecorder.shouldRecord()) {
					spRecorder.recordInstruction(inst);
				}

				try {
					try {
					    inst.exec(this);
					} catch (Exception e) {
						throw e;
					}

					inst.sp = null;
				} catch (SPException e) {
					if (createConversionErrorFile) {
						try (PrintWriter out = new PrintWriter(
								new BufferedWriter(new FileWriter(
										conversionErrorFile, true)))) {
							out.println(e.getMessage());

							try {
								out.println(e.getNestedException().getMessage()
										.toString());
							} catch (Exception ex) { }

							try {
								out.println(inst.sp.getWS().toString());
							} catch (Exception ex) { }

							try {
								out.println("Scriptname: "
										+ inst.sp.getScriptName());
							} catch (Exception ex) { }

							try {
								out.println(inst.sp.getDataStack().toString());
							} catch (Exception ex) { }

							try {
								out.println(inst.sp.getScriptLines()[inst.sp
								                                     .getInstPtr()]);
							} catch (Exception ex) { }

							out.println("");

						} catch (IOException ex) {
							System.out.println("IOException: "
									+ ex.getMessage());
						}
				        if (debugOutfile) {
						    try (PrintWriter out2 = new PrintWriter(new BufferedWriter(
								new FileWriter(debugFile, true)))) {
							    out2.println(e.getMessage());

							    try {
								    out2.println(e.getNestedException().getMessage()
										.toString());
							    } catch (Exception ex) { }

							    try {
								    out2.println(inst.sp.getWS().toString());
							    } catch (Exception ex) { }

							    try {
								    out2.println("Scriptname: "
										+ inst.sp.getScriptName());
							     } catch (Exception ex) { }

							    try {
								    out2.println(inst.sp.getDataStack().toString());
							    } catch (Exception ex) { }

							    try {
								    out2.println(inst.sp.getScriptLines()[inst.sp
								                                      .getInstPtr()]);
							    } catch (Exception ex) { }

							    out2.println("");
						    } catch (IOException e2) {
							    System.out.println("IOException: " + e2.getMessage());
						    }
				        }
					}
					System.out.println("exec: " + e);
					e.printStackTrace();

					if (!e.isLogged()) // the error may have already been logged
						// in the instruction - don't log twice.
					{
						Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
								new LogEvent(e));

						Log.logGeneralExceptionToDatabase(null, e);

						processedWithErrors = true;

						e.setLogged(true);

						// logErroredScript(inst, e);

						if (e.getErrorCode() == SPException.VALIDATION_ERROR) {
							processedWithValidationErrors = true;
						}

						if (shouldOutputValidationError(e, inst)) {
							addValidationOutput(inst.getErrorOutput());
						}

						if (shouldLogValidateInstruction(inst)) {
							logErroredValidateInstruction(
									((ValidateInst) inst).getMessage(),
									((ValidateInst) inst).getSeverity(), e,
									scriptProcessDateTime, false);
						}

						if (shouldAbortCurrentScript = shouldAbortCurrentScript(
								e, inst)) {
							throw (e);
						}
					}
				} catch (Exception e) {
					System.out.println("exec2: " + e);

					e.printStackTrace();

					shouldAbortCurrentScript = true;

					processedWithErrors = true;

					// logErroredScript(inst, e);

					throw new RuntimeException(
							"Unexpected Error In ScriptProcessor for Script Line ["
									+ inst.getInstAsEntered() + "]", e);
				} finally {
					inst.sp = null; // just in case the last instruction
					// executed errored.
				}
			}
		} finally {
			if (!processedWithValidationErrors) {
				logSuccessfulValidation(scriptProcessDateTime);
			}

			// Only capture results for tests that ran to completion.
			if (!shouldAbortCurrentScript && spTest.shouldCaptureTestData()) {
				spTest.storeTestResults(this);

				spTest.clearTestResults();
			}

			if (spRecorder.shouldRecord()) {
				spRecorder.close(this.getSPOutput());
			}

			// The compiled script can be safely returned
			// to the cache and the instance variable nulled.
			// Before, it was doing this in the finally clause
			// only for the very first instruction execution
			// which is correct but this coding is more straightforward.
			// returnCompiledScriptToCache();
		}
	}

	/**
	 * Executes the current instruction in the script. The member variable
	 * instrPtr points to the current instruction.
	 * <p/>
	 * 
	 * @throws SPException
	 *             If instrPtr points outside of script
	 */
	public void execSingleInst() throws SPException {
		boolean shouldAbortCurrentScript = false;

		EDITDateTime scriptProcessDateTime = new EDITDateTime();

		processedWithErrors = false;

		String idx = Integer.toString(getInstPtr());

		if (containsBreakPointKey(idx)) {
			if ((getBreakPointEntry(idx)).booleanValue()) {
				// Breakpoint was already processed (need to reset)
				addBreakPointEntry(idx, new Boolean(false));
			} else {
				// Breakpoint was not processed (need to stop)
				addBreakPointEntry(idx, new Boolean(true));
				setStopRun(true);

				return;
			}
		}

		Inst inst = (Inst) script.get(instPtr);

		try {
			inst.exec(this);
		} catch (SPException e) {
			System.out.println("execSingleInst: " + e);

			if (!e.isLogged()) {
				e.printStackTrace();

				Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
						new LogEvent(e));

				Log.logGeneralExceptionToDatabase(null, e);

				e.setLogged(true);
			}

			processedWithErrors = true;

			logErroredScript(inst, e);

			if (shouldOutputValidationError(e, inst)) {
				addValidationOutput(inst.getErrorOutput());
			}

			if (shouldLogValidateInstruction(inst)) {
				logErroredValidateInstruction(
						((ValidateInst) inst).getMessage(),
						((ValidateInst) inst).getSeverity(), e,
						scriptProcessDateTime, false);
			}

			if (shouldAbortCurrentScript = shouldAbortCurrentScript(e, inst)) {
				throw (e);
			}
		} catch (RuntimeException e) {
			System.out.println("execSingleInst2: " + e);

			e.printStackTrace();

			shouldAbortCurrentScript = true;

			processedWithErrors = true;

			logErroredScript(inst, e);

			throw (e);
		} finally {
			inst.sp = null; // just in case the last inst errored.
		}
	}

	public void execWithBreakPoints() throws SPException {
		setStopRun(false);

		while (!isStopRun()) {
			execSingleInst();
		}
	}

	public boolean forEachHTContainsKey(String key) {
		return foreachHT.containsKey(key);
	}

	/**
	 * Returns the fully qualified name associated with an alias
	 * <p/>
	 * 
	 * @param aliasName
	 *            The alias name
	 * 
	 * @return Returns the data name associated with an alias
	 * 
	 * @throws SPException
	 *             If alias is not found
	 */
	public synchronized String getAliasFullyQualifiedName(String aliasName)
			throws SPException {
		String dataName = (String) aliases.get(aliasName);

		if (dataName == null) {
			throw new SPException(
					"ScriptProcessor.getAliasFullyQualifiedName(String): "
							+ aliasName + " alias not in table",
							SPException.INSTRUCTION_PROCESSING_ERROR);
		}

		return dataName;
	}

	/**
	 * Returns break point information
	 * <p/>
	 * 
	 * @param Instruction
	 *            line number
	 * 
	 * @return DDBoolean that is true if the break point has been processed
	 */
	public Boolean getBreakPointEntry(String name) {
		return (Boolean) breakPoints.get(name);
	}

	public String[] getBreakPoints() {
		Iterator breakPointKeys = breakPoints.keySet().iterator();

		List breakPointVector = new ArrayList();

		while (breakPointKeys.hasNext()) {
			// DDBoolean dDBoolean;
			String key = (String) breakPointKeys.next();

			// System.out.println("The rows are " + key);
			// dDBoolean = (DDBoolean) breakPoints.get(key);
			// boolean value = dDBoolean.getBoolean();
			// System.out.println(key + " :::: " + value);
			// if (value) {
			breakPointVector.add(key);

			// }
		}

		return ((String[]) breakPointVector.toArray(new String[breakPointVector
		                                                       .size()]));
	}

	public boolean getConditionSwitch() {
		return condition;
	}

	/**
	 * Returns the current Row of the script while processing the script or when
	 * setting break points
	 */
	public int getCurrentRow() {
		return currentRow;
	}

	/**
	 * Returns the data stack Modified : syam lingamallu : 10/08/2001 to return
	 * string array
	 */
	public String[] getDataStack() {
		List lines = new ArrayList();

		for (int i = 0; i < stack.size(); i++) {
			// System.out.println("class name is :: " +
			// stack.get(i).getClass().getName());
			String value = (String) stack.get(i);

			lines.add(value);
		}

		return (String[]) lines.toArray(new String[lines.size()]);
	}

	/**
	 * This method specific to the geterror instruction - it needs database data
	 * for editing
	 */
	// public Object getElementAsVO() throws Exception
	// {
	// String segmentAsXML = spParams.getVOAsXML("SegmentVO");
	//
	// SegmentVO segmentVO = (SegmentVO) Util.unmarshalVO(SegmentVO.class,
	// segmentAsXML);
	//
	// return segmentVO;
	// }

	public String getElementValue(String fieldPath, String fieldName) {
		String value = spParams.getLastActiveElementValue(fieldPath, fieldName);

		return value;
	}

	/**
	 * Returns the financial Type
	 */
	public String getFinancialType() {
		return financialType;
	}

	/**
	 * This method returns the hashtable of financialType from vector params
	 */
	public Map getFinancialTypeData(String financialType, int selectedIndex) {
		Map paramOfFinancialType = new HashMap();
		int size = params.size();

		// if param vector has data of financialType then take the
		// Map of financialType and modify the Map
		if (size != 0) {
			if (size > selectedIndex) {
				paramOfFinancialType = (Map) params.get(selectedIndex);

				String fType = ((String) paramOfFinancialType
						.get("financialtype"));

				if (fType.equalsIgnoreCase(financialType)) {
					return paramOfFinancialType;
				}
			} else {
				// String financialTypeAsDDString =
				// getAsDDString(financialType);
				paramOfFinancialType = getParamOfSelectedRider(financialType,
						selectedIndex);

				paramOfFinancialType.put("financialtype", financialType);

				// get the BaseTerm parameters and increment its
				// financialSegmentCounter
				Map baseParam = (Map) params.get(0);

				// take value of FINANCIAL_SEGMENT_COUNTER from Base parameters
				int segmentCounter = ((Integer) baseParam
						.get(FINANCIAL_SEGMENT_COUNTER)).intValue();

				// increment the FINANCIAL_SEGMENT_COUNTER value
				// by 1 as a new Rider's parameters are added
				Integer num = new Integer(segmentCounter + 1);

				baseParam.put("financiasegmentcounter", num);
				params.add(paramOfFinancialType);

				return paramOfFinancialType;
			}
		} else {
			paramOfFinancialType = getParamOfSelectedRider(financialType,
					selectedIndex);

			paramOfFinancialType.put("financialtype", financialType);

			Integer num = new Integer(1);

			// put FINANCIAL_SEGMENT_COUNTER value as 1(bec we are putting
			// 1 for BaseParameters and incrementing its value as other
			// rider parameters are added)
			paramOfFinancialType.put("finacialsegmentcounter", num);
			params.add(paramOfFinancialType);

			return paramOfFinancialType;
		}

		return new HashMap();
	}

	public Object getForeachEntry(String name) {
		return foreachHT.get(name);
	}

	/**
	 * * syam lingamallu Actual function is commented out because we need
	 * strings or string arrays
	 * <p/>
	 * **
	 */
	public List getFunctionEntry(String name) {
		return (List) functions.get(name);
	}

	/**
	 * Returns all of the activated functions modified : syam lingamallu :
	 * 10/09/2001
	 */
	public String[] getFunctions() {
		Iterator functionEnum = functions.keySet().iterator();

		List functionVector = new ArrayList();

		while (functionEnum.hasNext()) {
			String name = (String) functionEnum.next();

			functionVector.add(name);
		}

		return ((String[]) functionVector.toArray(new String[functionVector
		                                                     .size()]));
	}

	public String getIdentifier(String fieldPath) {
		String id = null;

		id = (String) identifier.get(fieldPath);

		return id;
	}

	/**
	 * Returns the current instruction pointer
	 * <p/>
	 * 
	 * @return Returns the current instruction pointer
	 */
	public int getInstPtr() {
		return instPtr;
	}

	/**
	 * Returns integer value associated with label name
	 * <p/>
	 * 
	 * @param name
	 *            The Label name
	 * 
	 * @return Returns and integer associated with label
	 */
	public synchronized int getLabelEntry(String name) {
		Integer n = (Integer) labels.get(name);

		return n.intValue();
	}

	/**
	 * Returns the last instruction pointer
	 * <p/>
	 * 
	 * @return Returns the current instruction pointer
	 */
	public int getLastInstPtr() {
		return lastInstPtr;
	}

	/**
	 * Returns the output data sorted by name
	 * <p/>
	 * 
	 * @return Returns the output data Modified : syam lingamallu : 10/08/2001
	 *         to return string array
	 */

	// public String[] getOutput()
	// {
	// String[] outputStrings = new String[output.size()];
	//
	// for (int i = 0; i < output.size(); i++)
	// {
	//
	// SPOutput spOutput = (SPOutput) output.get(i);
	// outputStrings[i] = spOutput.getName() + "," + spOutput.getValue();
	// }
	// return null;
	// return outputStrings;
	// TreeMap od1 = new TreeMap(outputData);
	//
	// List od2 = new List(od1.values());
	//
	// List od3 = new ArrayList();
	//
	// for(int i=0; i<od2.size(); i++) {
	//
	// String dd = (String) od2.get(i);
	//
	// od3.add(dd);
	// }
	//
	// return ((String[]) od3.toArray(new String[od3.size()]));
	// }

	/**
	 * Return the output entry using the passed in name
	 */
	public String getOutputEntry(String name) {
		return (String) outputData.get(name);
	}

	/**
	 * Retrieves the entry off of the Parameter Map.
	 * <p/>
	 * 
	 * @param name
	 *            The name of the DD
	 * 
	 * @return The DD data
	 */
	public String getParamEntry(String name) {
		return (String) param.get(name);
	}

	/**
	 * This method returns the data of selected rider
	 */
	public Map getParamOfSelectedRider(String selectedRider, int index) {
		int size = params.size();
		Map riderData = new HashMap();

		if (size != 0) {
			boolean flag = false;
			Map BaseParameterData = new HashMap();

			if (size > index) {
				riderData = (Map) params.get(index);

				// later check for riderType also
				flag = true;

				return riderData;
			} else {
				Iterator enumer = params.iterator();

				while (enumer.hasNext()) {
					riderData = (Map) enumer.next();

					String fType =
							// ((String)riderData.get("financialtype")).toString();
							((String) riderData.get("financialtype"));

					if (fType.equalsIgnoreCase(BASE_PARM)) {
						BaseParameterData = riderData;
					} else {
						continue;
					}
				}
			}

			if (!flag) {
				// put the Defaults Values of Option7702,
				// Resident State and interestRate in all
				// riders parameters after taking from
				// Basepolicy holder parameters values
				Map defaultRiderData = new HashMap();

				if (BaseParameterData.containsKey(OPTION7702)) {
					defaultRiderData.put(OPTION7702,
							BaseParameterData.get(OPTION7702));
				}

				if (BaseParameterData.containsKey(RESIDENT_STATE)) {
					defaultRiderData.put(RESIDENT_STATE,
							BaseParameterData.get(RESIDENT_STATE));
				}

				if (BaseParameterData.containsKey(INTEREST_RATE)) {
					defaultRiderData.put(INTEREST_RATE,
							BaseParameterData.get(INTEREST_RATE));
				}

				// always put TableRating and PremPayTerm default value as "NA"
				// even when
				// BaseParameters has some TableRating value
				// as per reuqirements
				defaultRiderData.put(TABLE_RATING, "NA");
				defaultRiderData.put(PREM_PAY_TERM, "NA");

				return defaultRiderData;
			}
		}

		return new HashMap();
	}

	/**
	 * Returns the parameters
	 * <p/>
	 * 
	 * @return params
	 */
	public List getParams() {
		return params;
	}

	public ProductRuleProcessor getProductRule() {
		return pr;
	}

	/**
	 * Returns the objectified XML of any element that was created or modified
	 * in the DOM
	 * 
	 * @return
	 */
	public SPOutput getSPOutput() {
		spOutput.setProcessedWithErrors(processedWithErrors);

		spOutput.setDocuments(spParams.getDocuments());

		return spOutput;
	}

	/**
	 * Getter.
	 * 
	 * @return spParams
	 */
	public SPParams getSPParams() {
		return spParams; // To change body of implemented methods use File |
		// Settings | File Templates.
	}

	/*
	 * Returns vector containing all script Lines* No more we need this method
	 * because it is not really script Lines.*
	 */

	// public List getScriptLines() {
	// return script;
	// }

	/**
	 * Returns Script Instruction using the passed in index
	 * <p/>
	 * 
	 * @param i
	 *            Index of script instruction
	 * 
	 * @return Returns Script instruction
	 */
	public String getScriptElementAt(int i) {
		return ((Inst) script.get(i)).getInstAsEntered();
	}

	// syam lingamallu 10/05/2001

	/**
	 * ********************************************************************
	 * Found that List script really does not contain script lines as strings.
	 * List script contains objects of instructions. To get the original script
	 * lines this method can be used
	 * *********************************************************************
	 */
	public String[] getScriptLines() {
		String[] scriptLines = new String[script.size()];

		for (int i = 0; i < script.size(); i++) {
			Inst inst = (Inst) script.get(i);

			scriptLines[i] = inst.getInstAsEntered();
		}

		return scriptLines;
	}

	/**
	 * Returns the scriptName that is currently being debugged or run.
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * Returns the number of instructions in the script
	 * <p/>
	 * 
	 * @return Number of instructions
	 */
	public int getScriptSize() {
		return script.size();
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * /** Returns the number of objects on the data stack
	 * <p/>
	 * 
	 * @return Number of objects on data stack
	 */
	public int getStackSize() {
		return stack.size();
	}

	/**
	 * Returns reference to instance of StorageManager
	 * <p/>
	 * 
	 * @return Returns reference to instance of StorageManager
	 */
	public StorageManager getStorageManager() {
		return sm;
	}

	/**
	 * Uses the passed in information to get the vector Map
	 * <p/>
	 * 
	 * @param data
	 *            name of the vector data
	 */
	public Map getVectorEntry(String name) {
		return (Map) vectorTable.get(name);
	}

	/**
	 * Returns all the entries in the vectorTable
	 * 
	 * @return
	 */
	public String[] getVectorTable() {
		Iterator vectorTableItr = vectorTable.keySet().iterator();

		List vectorTables = new ArrayList();

		while (vectorTableItr.hasNext()) {
			String name = (String) vectorTableItr.next();

			vectorTables.add(name);
		}

		return ((String[]) vectorTables
				.toArray(new String[vectorTables.size()]));
	}

	/**
	 * Returns a String containing the viewer mode for the debug screen
	 */
	public String getViewerMode() {
		return viewerMode;
	}

	/**
	 * Returns Working Storage Modified : syam lingamallu : 10/08/2001 to return
	 * string array.
	 */
	public Map getWS() {
		return ws;

		// TreeMap ws1 = new TreeMap(ws);
		//
		// List ws2 = new List(ws1.values());
		//
		// List ws3 = new ArrayList();
		//
		// for(int i=0; i<ws2.size(); i++) {
		//
		// String dd = (String) ws2.get(i);
		//
		// ws3.add(dd);
		// }
		//
		// return ((String[]) ws3.toArray(new String[ws3.size()]));
	}

	/**
	 * Returns a DD from Working Storage using the passed in index. The index
	 * represents a positional index (Working Storage is stored using the name
	 * as the index)
	 * <p>
	 * 
	 * @param i
	 *            Index of the DD in Working Storage
	 * @return Returns a DD from Working Storage
	 */

	// public Object getWSElementAt(int index) {
	//
	// Iterator enum = ws.elements();
	// int i = 0;
	// while (enum.hasNext()) {
	// Object wsItem = enum.next();
	// if (index == i) {
	// return wsItem;
	// }
	// i++;
	//
	// }
	//
	// return null; //Invalid index
	// }

	/**
	 * Retrieves the entry off of the Working Storage Map.
	 * <p/>
	 * 
	 * @param name
	 *            The name of the DD
	 * 
	 * @return The DD data
	 */
	public String getWSEntry(String name) {
		return ws.get(name);
	}

	/**
	 * Returns the number of objects in Working Storage (ws)
	 * <p/>
	 * 
	 * @return Number of objects in Working Storage
	 */
	public int getWSSize() {
		return ws.size();
	}

	/**
	 * Retrieves the entry off of the Working Storage Map that store a vector.
	 * <p/>
	 * 
	 * @param name
	 *            The name of the vector
	 * 
	 * @return The object data
	 */
	public Object getWSVector(String name) {
		return wsHashtable.get(name);
	}

	/**
	 * Returns Working Storage
	 */
	public Map getWSdata() {
		return ws;
	}

	/**
	 * Returns true if an error Occurred
	 * <p/>
	 * 
	 * @return Returns true if an error Occurred
	 */
	public boolean hasErrorOccurred() {
		return errorOccurred;
	}

	public boolean ifStackIsEmpty() {
		return ifStack.isEmpty();
	}

	/**
	 * Increments the Instruction Pointer by 1
	 */
	public void incrementInstPtr() {
		setInstPtr(instPtr + 1);
	}

	public void incrementInstPtr(int lineforExit) {
		setInstPtr(instPtr + 1, lineforExit);
	}

	/**
	 * Used to check that script is selected or not
	 */
	public String isScriptLoaded() {
		return (scriptLoaded ? "" : "DISABLED");
	}

	/**
	 * Returns true if script processer is in single step mode. This is usually
	 * used for debugging.
	 * <p/>
	 * 
	 * @return Returns true if in singleStep mode
	 */
	public boolean isSingleStep() {
		return singleStep;
	}

	/**
	 * Returns true if the script was stopped
	 * <p/>
	 * 
	 * @return Returns true if script was stopped
	 */
	public boolean isStopRun() {
		return stopRun;
	}

	public ScriptChainNodeWrapper loadScript(long scriptId,
			ScriptChainNodeWrapper parent, boolean calledFromAnalyzer)
					throws SPException {
		String scriptName = "Default script";
		ScriptLineVO[] scriptLineVO = DAOFactory.getScriptLineDAO()
				.findScriptLinesById(scriptId);

		if ((scriptId == 0) && (parent != null)) {
			scriptLineVO = parent.getScriptLines();
		}

		if (parent == null) {
			ScriptVO[] scriptVO = DAOFactory.getScriptDAO().findScriptById(
					scriptId);

			scriptName = (scriptVO[0].getScriptName()).trim();
		}

		else {
			// add scriptLines to a node
			parent.setScriptLines(scriptLineVO);
		}

		String aScriptLine = null;
		String operator = null;
		String operand = null;
		List calledScripts = new ArrayList();

		ScriptVO[] scriptVO = null;
		long subScriptId = 0;

		List vectorLines = new ArrayList();

		// Map to capture Buildvector instructions
		Map vectorParams = new HashMap();

		for (int i = 0; i < scriptLineVO.length; i++) {
			aScriptLine = scriptLineVO[i].getScriptLine();

			// Store all references to labels
			if ((aScriptLine.endsWith(":")) && (!aScriptLine.startsWith("//"))) {
				operator = Inst.extractOperator(aScriptLine);

				if (!operator.equalsIgnoreCase("Label")) {
					operand = Inst.extractOperand(aScriptLine);
				}

				if (operator.equalsIgnoreCase("Label")) {
					operand = aScriptLine;

					// take the lines of inner script
					int lineNumber = 0;
					List lines = new ArrayList();

					for (int j = i; j < scriptLineVO.length; j++, i++) {
						String scriptLine = scriptLineVO[i].getScriptLine();

						if (lineNumber == 0) {
							scriptLine = "//" + scriptLine;
						}

						ScriptLineVO newScriptLineVO = new ScriptLineVO();

						newScriptLineVO.setScriptFK(0);
						newScriptLineVO.setLineNumber(lineNumber);
						newScriptLineVO.setScriptLine(scriptLine);
						lines.add(newScriptLineVO);

						++lineNumber;

						// when the script ends
						if (scriptLine.equalsIgnoreCase("exit")) {
							Object scriptArray = lines
									.toArray(new ScriptLineVO[lines.size()]);

							innerScriptLines.put(aScriptLine,
									(ScriptLineVO[]) scriptArray);

							break;
						}
					}
				}

				if (operator.equalsIgnoreCase("Gettablename")
						|| operator.equalsIgnoreCase("Create")
						|| operator.equalsIgnoreCase("Foreach")
						|| operator.equalsIgnoreCase("Round")) {
					;
				}

				else {
					String[] scriptCalled = { operand, operator };

					calledScripts.add(scriptCalled);
				}
			}

			// Store script vector parameters in a new hashtable
			else if (aScriptLine.equalsIgnoreCase("Vector:Start")) {
				for (int j = i;; i++) {
					aScriptLine = scriptLineVO[i].getScriptLine();

					// Buildvector instructions btween start and end put in
					// hashtable
					if (aScriptLine.equalsIgnoreCase("Vector:end")) {
						vectorLines.add(aScriptLine);
						vectorParams = buildVectorParams(vectorLines);
						aScriptLine = ("Buildvector "
								+ vectorParams.get("Name") + ":");

						break;
					}

					else {
						vectorLines.add(aScriptLine);
					}
				}
			}

			if (!calledFromAnalyzer) {
				// Make an instruction
				Inst inst = makeInstObject(aScriptLine);

				inst.setInstLineNr(getScriptSize());

				// Compile instruction
				inst.compile(this);

				// Add instruction to list
				script.add(inst);
			}
		}

		// end for
		// Add Called scripts
		Iterator enumer = calledScripts.iterator();

		if (!calledScripts.isEmpty()) {
			while (enumer.hasNext()) {
				String[] CalledScript = (String[]) enumer.next();
				String aCalledScript = CalledScript[0];

				String calledScript = aCalledScript.substring(0,
						aCalledScript.length() - 1);

				// Create label and load script only if the label
				// is not found
				if (!containsLabelKey(calledScript) || calledFromAnalyzer) {
					// Load the called Script
					String anOperator = CalledScript[1];
					String oName = calledScript;

					if (anOperator.equals("getscript")) {
						RulesVO rulesVO = pr.getBestMatchScriptId(oName);

						subScriptId = rulesVO.getScriptFK();

						if (parent == null) {
							root = new ScriptChainNodeWrapper();
							root.setNodeId(++nodeCount);
							root.setScriptId(scriptId);
							root.setNodeDescription(scriptName);
							root.setParent(null);
							root.setScriptLines(scriptLineVO);

							ScriptChainNodeWrapper childNode = new ScriptChainNodeWrapper();

							childNode.setNodeId(++nodeCount);
							childNode.setScriptId(subScriptId);
							childNode.setNodeDescription(anOperator + " "
									+ calledScript);

							root.addChild(childNode);
							parent = root;

							loadScript(subScriptId, childNode,
									calledFromAnalyzer);
						}

						else {
							ScriptChainNodeWrapper childNode = new ScriptChainNodeWrapper();

							childNode.setNodeId(++nodeCount);
							childNode.setScriptId(subScriptId);
							childNode.setNodeDescription(anOperator + " "
									+ calledScript);
							parent.addChild(childNode);

							loadScript(subScriptId, childNode,
									calledFromAnalyzer);
						}
					}

					else {
						scriptVO = DAOFactory.getScriptDAO().findScriptByName(
								oName);

						// scriptVO =
						// DAOFactory.getScriptDAO().findScriptById(Long.parseLong(oName));
						if (scriptVO != null) {
							subScriptId = scriptVO[0].getScriptPK();

							if (anOperator.startsWith("if")) {
								if (subScriptId != 0) {
									if (parent == null) {
										root = new ScriptChainNodeWrapper();
										root.setNodeId(++nodeCount);
										root.setScriptId(scriptId);
										root.setNodeDescription(scriptName);
										root.setParent(null);
										root.setScriptLines(scriptLineVO);

										ScriptChainNodeWrapper childNode = new ScriptChainNodeWrapper();

										childNode.setNodeId(++nodeCount);
										childNode.setScriptId(subScriptId);
										childNode.setNodeDescription(anOperator
												+ " " + calledScript);

										root.addChild(childNode);

										parent = root;

										loadScript(subScriptId, childNode,
												calledFromAnalyzer);
									}

									else {
										ScriptChainNodeWrapper childNode = new ScriptChainNodeWrapper();

										childNode.setNodeId(++nodeCount);
										childNode.setScriptId(subScriptId);
										childNode.setNodeDescription(anOperator
												+ " " + calledScript);
										parent.addChild(childNode);

										loadScript(subScriptId, childNode,
												calledFromAnalyzer);
									}
								}
							}

							else {
								if (parent == null) {
									root = new ScriptChainNodeWrapper();
									root.setNodeId(++nodeCount);
									root.setScriptId(scriptId);
									root.setNodeDescription(scriptName);
									root.setParent(null);
									root.setScriptLines(scriptLineVO);

									ScriptChainNodeWrapper childNode = new ScriptChainNodeWrapper();

									childNode.setNodeId(++nodeCount);
									childNode.setScriptId(subScriptId);
									childNode.setNodeDescription(anOperator
											+ " " + calledScript);

									root.addChild(childNode);
									parent = root;

									loadScript(subScriptId, childNode,
											calledFromAnalyzer);
								}

								else {
									ScriptChainNodeWrapper childNode = new ScriptChainNodeWrapper();

									childNode.setNodeId(++nodeCount);
									childNode.setScriptId(subScriptId);
									childNode.setNodeDescription(anOperator
											+ " " + calledScript);
									parent.addChild(childNode);

									loadScript(subScriptId, childNode,
											calledFromAnalyzer);
								}
							}

							// line 892
						}

						// line 850
					}

					// line 846
				}

				// line 805
				else {
					/*
					 * String anOperator = CalledScript[1];
					 * ScriptChainNodeWrapper childNode = new
					 * ScriptChainNodeWrapper();
					 * childNode.setNodeId(++nodeCount);
					 * childNode.setScriptId(subScriptId);
					 * childNode.setNodeDescription
					 * (anOperator+" "+aCalledScript);
					 * parent.addChild(childNode);
					 */
					String anOperator = CalledScript[1];

					if (!anOperator.equals("Label")) {
						ScriptChainNodeWrapper childNode = new ScriptChainNodeWrapper();

						childNode.setNodeId(++nodeCount);
						childNode.setScriptId(0);
						childNode.setNodeDescription(anOperator + " "
								+ calledScript);
						childNode
						.setScriptLines((ScriptLineVO[]) innerScriptLines
								.get(aCalledScript));

						parent.addChild(childNode);
						loadScript(0, childNode, calledFromAnalyzer);
					}
				}
			}

			// line 794
		}

		// 792
		return parent;
	}

	public void loadScript(String process, String event, String eventType,
			long productKey, String effectiveDate) throws SPException,
			RuntimeException {
		try {
			pr.setProcessName(process);
			pr.setEvent(event);
			pr.setEventType(eventType);
			pr.setProductStructurePK(productKey);
			pr.setEffectiveDate(effectiveDate);

			Long driverScriptPK = CSCache.getCSCache()
					.getScriptPKBy_BestMatchElements("Driver", pr);

			if (analyzerMode) {
				ScriptVO[] scriptVO = DAOFactory.getScriptDAO().findScriptById(
						driverScriptPK);

				setScriptName(scriptVO[0].getScriptName());
			}

			loadScript(driverScriptPK);
		} catch (SPException e) {
			System.out.println("loadScript: " + e);

			e.printStackTrace();

			String message = getDriverLoadingMessage();

			if (message != null) {
				Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
						new LogEvent(e + message));

				Log.logGeneralExceptionToDatabase(message, e);
			} else {
				Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
						new LogEvent(e));

				Log.logGeneralExceptionToDatabase(null, e);
			}

			logErroredScript(e.getInst(), e);

			throw (e);
		} catch (RuntimeException e) {
			System.out.println("loadScript2: " + e);

			e.printStackTrace();

			String message = getDriverLoadingMessage();

			if (message != null) {
				Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
						new LogEvent(e + message));

				Log.logGeneralExceptionToDatabase(message, e);
			} else {
				Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
						new LogEvent(e));

				Log.logGeneralExceptionToDatabase(null, e);
			}

			throw (e);
		}
	}

	private String getDriverLoadingMessage() {
		String message = " Rule Was Not Found For ProductStructure ["
				+ pr.getProductStructurePK() + "] Rule [" + "Driver"
				+ "] Process [" + pr.getProcessName() + "] Event ["
				+ pr.getEventName() + "] EventType [" + pr.getEventTypeName()
				+ "]";

		return message;
	}

	/**
	 * This method of loadScript takes in the scriptid, then gets the scriptline
	 * requested
	 */
	public void loadScript(long scriptId) throws SPException, RuntimeException {
		Inst inst = null;

		// Get a vector containing the script and compile the entries
		try {
			TreeMap calledScripts = null;

			List elementalScript = null;

			CompiledScript compiledScript = CSCache.getCSCache()
					.borrowCompiledScript(scriptId, pr);

			if ((compiledScript != null)) {
				// We need to flag this borrowed script so that we can return it
				// to the
				// pool at the end of the script run (done via the clear()
				// method).
				borrowedCompiledScripts.add(compiledScript);

				elementalScript = compiledScript.getScript();

				script.addAll(elementalScript);

				calledScripts = compiledScript.getLabels();
			} else {
				elementalScript = new ArrayList();
				calledScripts = new TreeMap();

				ScriptLineVO[] scriptLineVOs = CSCache.getCSCache()
						.getScriptLinesBy_ScriptPK(scriptId);

				String aScriptLine = null;
				String operator = null;
				String operand = null;

				// Map to capture Buildvector instructions
				Map vectorParams = new HashMap();

				for (int i = 0; i < scriptLineVOs.length; i++) {
					aScriptLine = scriptLineVOs[i].getScriptLine();

					// Bypass blank lines
					if ((aScriptLine.trim()).length() == 0) {
						continue;
					}

					// Store all references to labels
					if ((aScriptLine.endsWith(":"))
							&& (!aScriptLine.startsWith("//"))) {
						operator = Inst.extractOperator(aScriptLine);

						if (!operator.equalsIgnoreCase("Label")) {
							operand = Inst.extractOperand(aScriptLine);

							if (operator.equalsIgnoreCase("Gettablename")
									|| operator.equalsIgnoreCase("Create")
									|| operator.equalsIgnoreCase("Getvector")
									|| operator.equalsIgnoreCase("Foreach")
									|| operator.equalsIgnoreCase("Round")
									|| operator
									.equalsIgnoreCase("ClearLastActiveElement")) {
							} else {
								if (operator.startsWith("If")
										|| operator.startsWith("if")) {
									separateIf(operand, operator, calledScripts);
								} else if (operator
										.equalsIgnoreCase("Gettable")) {
									setSourceScript(operand, calledScripts,
											elementalScript);
								} else {
									calledScripts.put(operand, operator);
								}
							}
						}
					}

					// Store script vector parameters in a new hashtable
					else if (aScriptLine.equalsIgnoreCase("Vector:Start")) {
						List vectorLines = new ArrayList();

						for (int j = i;; i++) {
							aScriptLine = scriptLineVOs[i].getScriptLine();

							// Buildvector instructions btween start and end put
							// in hashtable
							if (aScriptLine.equalsIgnoreCase("Vector:end")) {
								vectorLines.add(aScriptLine);

								vectorParams = buildVectorParams(vectorLines);

								aScriptLine = ("Buildvector "
										+ vectorParams.get("Name") + ":");

								break;
							} else {
								vectorLines.add(aScriptLine);
							}
						}
					}

					// Make an instruction
					inst = makeInstObject(aScriptLine);

					inst.setInstLineNr(getScriptSize() + elementalScript.size());

					// Compile instruction
					inst.compile(this);

					// Add instruction to list
					if (inst instanceof Buildvector) {
						// Store each set of vector params in a hashtable for
						// build vector to use
						((Buildvector) inst).setVectorParams(vectorParams);

						// buildVector Expense:
						inst.setOperator("Buildvector");
					}

					elementalScript.add(inst);
				}

				// end for
				// end else

				// Add the new CompiledScript. It will never be added again
				// since
				// the (above) code would have found the CompiledScript in
				// cache.
				CSCache.getCSCache().addCompiledScript(scriptId, pr,
						elementalScript, calledScripts);

				script.addAll(elementalScript);
			}

			// Add Called scripts
			Iterator enumer = calledScripts.keySet().iterator();

			while (enumer.hasNext()) {
				String aCalledScript = (String) enumer.next();

				// Create label and load script only if the label
				// is not found
				if (!containsLabelKey(aCalledScript.substring(0,
						aCalledScript.length() - 1))) {
					// Load the called Script
					String anOperator = (String) calledScripts
							.get(aCalledScript);
					String oName = aCalledScript.substring(0,
							aCalledScript.length() - 1);

					Long compiledScriptPK = null;

					if (anOperator.equals("getscript")) {
						// getscript is "rules-based" and needs to use a best
						// match algorithm.
						compiledScriptPK = CSCache.getCSCache()
								.getScriptPKBy_BestMatchElements(oName, pr);

						// If a Script Rule match fails (found no matching Rule,
						// this is OK - we simply ignore it).
						if (compiledScriptPK != null) {
							// Create a Label instruction dynamically
							inst = makeInstObject(aCalledScript);

							inst.setInstLineNr(getScriptSize());
							inst.compile(this);
							script.add(inst);

							loadScript(compiledScriptPK);
						}
					} else {
						// a direct call to get the script
						compiledScriptPK = CSCache.getCSCache()
								.getScriptPKBy_ScriptName(oName).longValue();

						// Create a Label instruction dynamically
						inst = makeInstObject(aCalledScript);

						inst.setInstLineNr(getScriptSize());
						inst.compile(this);
						script.add(inst);

						loadScript(compiledScriptPK);
					}
				}
			}
		} catch (SPException e) {
			System.out.println("loadScript: " + e);

			if (!e.isLogged()) {
				e.printStackTrace();

				Logging.getLogger(Logging.GENERAL_EXCEPTION).error(
						new LogEvent(e));

				Log.logGeneralExceptionToDatabase(null, e);

				e.setLogged(true);
			}

			e.setInst(inst);

			throw e;
		} catch (RuntimeException e) {
			System.out.println("loadScript2: " + e);

			e.printStackTrace();

			throw e;
		}

	}

	public boolean loopStackIsEmpty() {
		return loopStack.isEmpty();
	}

	/**
	 * Return an Instruction from the passed in script string. Actually converts
	 * the script line string into the specific instruction object (Ex. Push)
	 * and calls the compile method.
	 * <p/>
	 * 
	 * @param aScriptLine
	 *            Instruction in String format
	 * 
	 * @return Returns Instruction Object (Inst)
	 */
	public Inst makeInstObject(String aScriptLine) {
		char[] firstChar = new char[1];
		String operator = Inst.extractOperator(aScriptLine).toLowerCase();
		char[] restOfChars = new char[operator.length() - 1];
		StringBuffer sb = new StringBuffer();
		String className;
		Inst anInst = null;

		// Make sure that the first character is upper case and
		// everything else is lower case and that the package name
		// is supplied.
		// String op = operator.toLowerCase();
		operator.getChars(0, 1, firstChar, 0);
		operator.getChars(1, operator.length(), restOfChars, 0);

		String first = (new String(firstChar)).toUpperCase();

		sb.append("engine.sp.");

		// sb.append(getClass().getPackage().getName() + ".");
		sb.append(first);
		sb.append(restOfChars);
		className = sb.toString();

		// Now make an object and initialize it
		try {
			anInst = (Inst) Class.forName(className).newInstance();
		} catch (Exception e) {
			System.out.println("makeInstObject: " + e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		anInst.setInstAsEntered(aScriptLine);
		anInst.setOperator(operator);

		// anInst.setInstLineNr(script.size());
		// anInst.compile(this);
		return anInst;
	}

	/**
	 * Returns the top item from the appropriate stack without removing it.
	 * <p/>
	 * 
	 * @return
	 */
	public Object peek(int stackType) {
		switch (stackType) {
		case CALLRETURN_STACK:
			return callReturn.peek();

		case LOOP_STACK:
			return loopStack.peek();

		case IF_STACK:
			return ifStack.peek();

		default:
			throw new RuntimeException(
					"ScriptProcessor.peek(int): Error in Peek - stack type invalid: "
							+ stackType);
		}
	}

	/**
	 * Returns the top item from the data stack without removing it.
	 * <p/>
	 * 
	 * @return
	 */
	public String peekFromStack() {
		return (String) stack.peek();
	}

	/**
	 * Removes the top item from the appropriate stack
	 * <p/>
	 * 
	 * @return Returns top item from the appropriate stack
	 */
	public Object pop(int stackType) {
		// after an error occurs the loop stack is empty on the last whiltelt of
		// the withdrawal script
		// this code prevents the empty stack exception - can find the source of
		// the extra execution
		if (stackType == LOOP_STACK) {
			if (loopStack.isEmpty()) {
				Object anElement = new Object();

				return anElement;
			} else {
				return loopStack.pop();
			}
		}

		else if (stackType == IF_STACK) {
			if (ifStack.isEmpty()) {
				Object anElement = new Object();

				return anElement;
			} else {
				return ifStack.pop();
			}
		} else if (stackType == CALLRETURN_STACK) {
			if (callReturn.isEmpty()) {
				Object anElement = new Object();

				return anElement;
			} else {
				return callReturn.pop();
			}
		} else {
			throw new RuntimeException(
					"ScriptProcessor.pop(int): Error in Pop - StackType invalid: "
							+ stackType);
		}
	}

	/**
	 * Removes the top item from the data stack
	 * <p/>
	 * 
	 * @return Returns top item from the data stack
	 */
	public String popFromStack() throws SPException {
		try {
			String anElement = (String) stack.pop();

			if (spRecorder.shouldRecord()) {
				spRecorder.recordPop(anElement);
			}

			return anElement;
		} catch (EmptyStackException ese) {
			System.out.println("popFromStack: " + ese);

			ese.printStackTrace();

			throw new SPException(
					"Empty Stack in ScriptProcessorImpl popFromStack",
					SPException.INSTRUCTION_PROCESSING_ERROR, ese);
		}
	}

	/**
	 * Places the passed in object onto the data stack
	 * <p/>
	 * 
	 * @param anElement
	 *            DD object that is placed onto the data stack
	 */
	public void push(String anElement) {
		if (debugConsole) {
			if (anElement == null) {
				System.out.println("PushSP : " + anElement);
			} else {
				System.out.println("PushSP : " + anElement.toString()
						+ " Class Name :" + anElement.getClass().getName());
			}
		}

		if (debugOutfile) {
			if (anElement == null) {
				try (PrintWriter pwOut = new PrintWriter(new BufferedWriter(
						new FileWriter(debugFile, true)))) {
					pwOut.println("\t Pushed Value: " + anElement);
				} catch (IOException e) {
					System.out.println("IOException: " + e.getMessage());
				}
			} else {
				try (PrintWriter pwOut = new PrintWriter(new BufferedWriter(
						new FileWriter(debugFile, true)))) {
					pwOut.println("\t Pushed Value: " + anElement.toString());
				} catch (IOException e) {
					System.out.println("IOException: " + e.getMessage());
				}
			}
		}

		if (spRecorder.shouldRecord()) {
			spRecorder.recordPush(anElement);
		}

		stack.push(anElement);
	}

	/**
	 * Places the passed in object onto the appropriate stack
	 * <p/>
	 * 
	 * @param anElement
	 *            Object that is placed onto the appropriate stack
	 */
	public void push(int stackType, Object anElement) {
		switch (stackType) {
		case CALLRETURN_STACK:
			callReturn.push((Integer) anElement);

			break;

		case LOOP_STACK:
			loopStack.push((Integer) anElement);

			break;

		case IF_STACK:
			ifStack.push((Boolean) anElement);

			break;

		default:
			throw new RuntimeException(
					"ScriptProcess.push(int, Object): Error in Push - stack type invalid: "
							+ stackType);
		}
	}

	/**
	 * Places the passed in information onto the label Map
	 * <p/>
	 * 
	 * @param name
	 *            The label name
	 * @param i
	 *            Integer associated with label
	 */
	public void putLabelEntry(String name, int i) {
		// System.out.println("Label Entry : " + i + " : " + name);
		labels.put(name, new Integer(i));
	}

	public void removeBreakPointEntry(String name) {
		breakPoints.remove(name);

		setCurrentRow(Integer.parseInt(name));
	}

	public Object removeForeachEntry(String name) {
		return foreachHT.remove(name);
	}

	/**
	 * Method to remove the rider form params List
	 */
	public void removeRider(int index) {
		int size = params.size();
		Map baseParam = (Map) params.get(0);

		if ((size != 0) && (index < size)) {
			params.remove(index);
			baseParam.put(FINANCIAL_SEGMENT_COUNTER, new Integer(size - 1));
		}
	}

	/**
	 * Used by debugging process to Reset the Script so it can be run again
	 */
	public void reset() {
		stack.removeAllElements();
		loopStack.removeAllElements();
		ifStack.removeAllElements();
		ws.clear();
		functions.clear();
		vectorTable.clear();
		aliases.clear();
		newNodes.clear();
		spOutput = new SPOutput();
		outputData.clear();
		foreachHT.clear();
		identifier.clear();
		instPtr = 0;
		lastInstPtr = 0;
		currentRow = 0;
		singleStep = false;
		stopRun = false;
		context.clear();
		vectorTable.clear();
		praseDocBuilders.clear();

		clearHibernateSession();

		// Reset the breakpoints as not being processed
		Iterator enumer = breakPoints.keySet().iterator();

		while (enumer.hasNext()) {
			String bp = (String) enumer.next();

			// addBreakPointEntry(bp, new DDBoolean(false));
			/*
			 * Modified syam lingmallu 11/01/01 I have to modify this bcos if
			 * the user resets the script, when he has breakpoints in his script
			 * the script scrolls to one of the line which has breakpoints.
			 * Actually it should show the first line of script. This is
			 * happening bcos addBreakPointEntry function is calling the
			 * function called setCurretRow
			 */
			breakPoints.put(bp, new Boolean(false));
		}
	}

	/**
	 * @param abortOnHardValidationError
	 */
	public void setAbortOnHardValidationError(boolean abortOnHardValidationError) {
		this.abortOnHardValidationError = abortOnHardValidationError;
	}

	/**
	 * Store the passed in information analyzerMode
	 * <p/>
	 * 
	 * @param data
	 *            analyzerMode
	 */
	public void setAnalyzerMode(boolean analyzerModeValue) {
		analyzerMode = analyzerModeValue;
	}

	public void setConditionSwitch(boolean value) {
		condition = value;
	}

	public Map setDefaultData(String financialType) {
		/*
		 * This code is from above method i.e getParamofSelectedRider method.
		 * after we clear the rider parameters once again we have to keep the
		 * default data i.e from base parameters data.
		 */
		Map defaultRiderData = new HashMap();

		Map BaseParameterData = new HashMap();

		Iterator enumer = params.iterator();

		Map riderData = null;

		while (enumer.hasNext()) {
			riderData = (Map) enumer.next();

			String fType = ((String) riderData.get("financialtype"));

			// ((DDString)riderData.get("financialtype")).getString();
			if (fType.equalsIgnoreCase(BASE_PARM)) {
				BaseParameterData = riderData;
			} else {
				continue;
			}
		}

		// DDString ddString = getAsDDString(financialType);
		// defaultRiderData.put(ddString.getExternalName()
		// ,ddString);
		if (BaseParameterData.containsKey(OPTION7702)) {
			defaultRiderData.put(OPTION7702, BaseParameterData.get(OPTION7702));
		}

		if (BaseParameterData.containsKey(RESIDENT_STATE)) {
			defaultRiderData.put(RESIDENT_STATE,
					BaseParameterData.get(RESIDENT_STATE));
		}

		if (BaseParameterData.containsKey(INTEREST_RATE)) {
			defaultRiderData.put(INTEREST_RATE,
					BaseParameterData.get(INTEREST_RATE));
		}

		// always put TableRating and PremPayTerm default value as "NA" even
		// when
		// BaseParameters has some TableRating valueas per reuqirements
		defaultRiderData.put(TABLE_RATING, "NA");
		defaultRiderData.put(PREM_PAY_TERM, "NA");

		return defaultRiderData;
	}

	/**
	 * Used to set errorOccurred member variable
	 * <p/>
	 * 
	 * @param eo
	 *            New value of errorOccurred
	 */
	public void setErrorOccurred(boolean eo) {
		errorOccurred = eo;
	}

	/**
	 * Sets the financial Type
	 */
	public void setFinancialType(String newValue) {
		financialType = newValue;
	}

	/**
	 * Used to set instruction pointer (instPtr member variable)
	 * <p/>
	 * 
	 * @param i
	 *            New value of instrPtr
	 */
	public void setInstPtr(int i) {
		lastInstPtr = instPtr;
		instPtr = i;
		currentRow = i;
	}

	public void setInstPtr(int i, int lineForExit) {
		lastInstPtr = lineForExit;
		instPtr = i;
		currentRow = i;
	}

	/*
	 * Use by the foreach instruction
	 */

	public boolean setLastActiveElement(String fieldPath) {
		int foreachRequestCount = 0;

		if (foreachHT.containsKey(fieldPath)) {
			foreachRequestCount = ((Integer) foreachHT.get(fieldPath))
					.intValue();
		}

		boolean lastActiveSet = spParams.setLastActiveElement(fieldPath,
				foreachRequestCount);

		if (lastActiveSet) {
			foreachRequestCount++;

			addForeachEntry(fieldPath, new Integer(foreachRequestCount));
		}

		return lastActiveSet;
	}

	/*
	 * Use by the foreach instruction
	 */

	public void removeLastActiveElement(String fieldPath) {
		spParams.removeLastActiveElement(fieldPath);
	}

	/**
	 * Places the passed in information onto the vector Map
	 * <p>
	 * 
	 * @param data
	 *            The vector data
	 */

	// public void addErrorLog(ErrorLogVO errorLogVO)
	// {
	//
	// errorLog.add(errorLogVO);
	// }
	// private EditErrorVO[] buildEditErrors(EditErrorVO[] editErrorVOs)
	// {
	//
	// for (int i = 0; i < errorLog.size(); i++)
	// {
	//
	// ErrorLogVO errorLogVO = (ErrorLogVO) errorLog.get(i);
	// editErrorVOs[i] = new EditErrorVO();
	// editErrorVOs[i].setBaseParameter(errorLogVO.getBaseParamStructure());
	// editErrorVOs[i].setBaseParameterValue(errorLogVO.getBaseParamValue());
	// editErrorVOs[i].setCrossParameter(errorLogVO.getCrossParamStructure());
	// editErrorVOs[i].setCrossParameterValue(errorLogVO.getCrossParamValue());
	// MessageVO messageVO = new MessageVO();
	// messageVO.setMessage(errorLogVO.getMessage());
	// CodeTableVO[] codeTableVO =
	// codeTableWrapper.getCodeTableEntries("SEVERITY");
	// for (int j = 0; j < codeTableVO.length; j++)
	// {
	//
	// String severity = codeTableVO[j].getCodeDesc();
	// if (severity.equalsIgnoreCase(errorLogVO.getSeverity()))
	// {
	//
	// messageVO.setSeverityCT(codeTableVO[j].getCodeTablePK());
	// break;
	// }
	// }
	//
	// editErrorVOs[i].setMessageVO(messageVO);
	// }
	// return editErrorVOs;
	// }

	/**
	 * Store the passed in information noEditingExceptionErrors
	 * <p/>
	 * 
	 * @param data
	 *            noEditingExceptionErrors
	 */
	public void setNoEditingException(boolean noEditingExceptionErrors) {
		noEditingException = noEditingExceptionErrors;
	}

	public void setProductRuleProcessor(ProductRuleProcessor pr) {
		this.pr = pr;
	}

	public void setScriptLoaded(boolean newValue) {
		scriptLoaded = newValue;
	}

	public void setScriptName(String newValue) {
		scriptName = newValue;
	}

	public void setSelectedIndex(int newValue) {
		selectedIndex = newValue;
	}

	/**
	 * Used to set singleStep member variable
	 * <p/>
	 * 
	 * @param ss
	 *            New value of singleStep
	 */
	public void setSingleStep(boolean ss) {
		singleStep = ss;
	}

	/**
	 * Used to set stopRun member variable
	 * <p/>
	 * 
	 * @param sr
	 *            New value of stopRun
	 */
	public void setStopRun(boolean sr) {
		stopRun = sr;
	}

	public void setStorageManager(StorageManager sm) {
		this.sm = sm;
	}

	/**
	 * Sets viewerMode to be equal to the value passed in its constructor
	 * <p/>
	 * 
	 * @param newValue
	 *            A String containing the viewer mode for the debug page
	 */
	public void setViewerMode(String newValue) {
		viewerMode = newValue;
	}

	/**
	 * Returns true if the appropriate stack is empty
	 * <p/>
	 * 
	 * @return Returns true if the appropriate stack is empty
	 */
	public boolean stackEmpty(int stackType) {
		switch (stackType) {
		case CALLRETURN_STACK:
			return callReturn.empty();

		case LOOP_STACK:
			return loopStack.empty();

		case IF_STACK:
			return ifStack.empty();

		default:
			throw new RuntimeException("Invalid Stack Type");
		}
	}

	/**
	 * Used to position the instruction pointer at the passed in label name.
	 * <p/>
	 * 
	 * @param startingPoint
	 *            Label Name
	 */
	public void startAt(String startingPoint) throws SPException {
		int i = getLabelEntry(startingPoint);

		setInstPtr(i);
	}

	public boolean verifyPathExistence(String fieldPath) {
		boolean pathExists = spParams.verifyPathExistence(fieldPath);

		return pathExists;
	}

	/**
	 * Returns true if the callReturn stack is empty
	 * <p/>
	 * 
	 * @return Returns true if the callReturn stack is empty
	 */
	protected boolean callReturnStackEmpty() {
		return callReturn.empty();
	}

	protected boolean isBreakPointEmpty() {
		return breakPoints.isEmpty();
	}

	/**
	 * *********************************** Private Methods
	 * *************************************
	 */
	private String buildCacheKey(long productKey, String process, String event,
			String eventType) {
		String key = (productKey + "") + process + event + eventType;

		return key;
	}

	private Map buildVectorParams(List vectorLines) {
		Map ht = new HashMap();
		List ruleValues = new ArrayList();
		List paramValues = new ArrayList();
		String ruleName = null;
		String name = null;
		String type = null;
		String tableType = null;
		String mode = null;
		String variable = null;
		String calc = null;
		String paramName = null;

		ht.put("Rules", new ArrayList());
		ht.put("ParamNames", new ArrayList());

		int ind = 0;

		Iterator e = vectorLines.iterator();

		while (e.hasNext()) {
			String line = (String) e.next();

			if (line.startsWith("Name:")) {
				ind = line.indexOf(":");
				name = line.substring(ind + 1);
				ht.put("Name", name);
			} else if (line.startsWith("Type:")) {
				ind = line.indexOf(":");
				type = line.substring(ind + 1);
				ht.put("Type", type);
			} else if (line.startsWith("PRE:")) {
				ind = line.indexOf(":");
				ruleName = line.substring(ind + 1);
				ruleValues = (List) ht.get("Rules");
				ruleValues.add(ruleName);
			} else if (line.startsWith("Inst:Type")) {
				ind = line.indexOf("=");
				tableType = line.substring(ind + 1);
				ruleValues = (List) ht.get("Rules");
				ruleValues.add(tableType);
			} else if (line.startsWith("Inst:Mode")) {
				ind = line.indexOf("=");
				mode = line.substring(ind + 1);
				ruleValues = (List) ht.get("Rules");
				ruleValues.add(mode);
			} else if (line.startsWith("Variable:")) {
				ind = line.indexOf(":");
				variable = line.substring(ind + 1);
				ht.put("Variable", variable);
			} else if (line.startsWith("Calc=")) {
				ind = line.indexOf("=");
				calc = line.substring(ind + 1);
				ht.put("Calc", calc);
			} else if (line.startsWith("Inst:Param")) {
				ind = line.indexOf("=");
				paramName = line.substring(ind + 1);
				paramValues = (List) ht.get("ParamNames");
				paramValues.add(paramName);
			}
		}

		// end while loop
		return ht;
	}

	/**
	 * Returns map of param 'mapName' from this.dataMapStorage.
	 * If map doesn't yet exist, it is created.
	 * 
	 * @param mapName
	 */
	private Map getDataMap(String mapName) {
				
		Map<String, String> dataMap = this.dataMapStorage.get(mapName);
		
		if(dataMap == null) {
			
			dataMap = new HashMap();
			
			this.dataMapStorage.put(mapName, dataMap);
		}
		
		return dataMap;
	}
	
	/**
	 * Pulls requested map from this.dataMapStorage and returns
	 * value stored at key provided in params.
	 * 
	 * @param mapName
	 * @param key
	 */
	public String getDataMapValue(String mapName, String key) {
				
		String value = (String) getDataMap(mapName).get(key);
		
		return value;
	}
	

	/**
	 * Pulls requested map from this.dataMapStorage and sets provided value
	 * at key provided in params
	 * 
	 * @param mapName
	 * @param key
	 * @param value
	 */
	public void setDataMapValue(String mapName, String key, String value) {
				
		getDataMap(mapName).put(key, value);
		
	}

	/**
	 * Logs, in context, the current instruction that failed.
	 * 
	 * @param inst
	 */
	private void logErroredScript(Inst inst, Exception e) {
		Logger logger = Logging.getLogger(Logging.EXECUTE_SCRIPT);

		int erroredLineNumber = (inst != null) ? inst.getInstLineNr() : (-1);

		String operator = "NA";

		String message = null;

		LogEvent logEvent = null;

		logEvent = new LogEvent(e.toString());

		logEvent.addToContext("Instruction", inst.getInstAsEntered());
		logEvent.addToContext("LineNumber", String.valueOf(erroredLineNumber));
		logEvent.addToContext("Operator", operator);

		StringBuffer scriptLines = new StringBuffer();

		// Get a snapshot of the current script.
		int scriptLength = script.size();

		for (int i = 0; i < scriptLength; i++) {
			Inst currentInst = (Inst) script.get(i);

			int currentLineNumber = currentInst.getInstLineNr();

			boolean errored = false;

			if (currentLineNumber == erroredLineNumber) {
				errored = true;
			} else {
				errored = false;
			}

			String scriptLine = currentInst.getInstAsEntered();

			scriptLines.append(currentLineNumber + ",," + scriptLine + ",,"
					+ errored);

			if (i != (scriptLength - 1)) {
				scriptLines.append("::");
			}
		}

		logEvent.addToContext("Script", scriptLines.toString());

		logEvent.setContextName(Logging.EXECUTE_SCRIPT);

		logger.error(logEvent);
	}

	/**
	 * Logs validate instructions that are tagged as reporting = y.
	 * 
	 * @param inst
	 * @param e
	 * @param dbOnly
	 *            if true, then only log to the DB, don't log to Log4J
	 */
	private void logErroredValidateInstruction(String message, String severity,
			SPException e, EDITDateTime scriptProcessDateTime, boolean dbOnly) {
		LogEvent logEvent = new LogEvent(message, e);

		String contextName = (String) context.get(Context.CONTEXT_NAME);

		String logName = Log.VALIDATE_UNSPECIFIED;

		EDITMap columnInfo = new EDITMap();

		if (contextName != null) {
			logName = contextName;

			logEvent.setContextName(contextName);

			java.util.Set keys = context.keySet();

			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();

				if (!key.equals(Context.CONTEXT_NAME)) {
					String value = (String) context.get(key);

					logEvent.addToContext(key, value);

					columnInfo.put(key, value);
				}
			}

			logEvent.addToContext("Severity", severity);

			columnInfo.put("Severity", severity);
			columnInfo.put("ProcessDate",
					scriptProcessDateTime.getFormattedDateTime());
			columnInfo.put("Error", message);
		}

		Log.logToDatabase(logName, message, columnInfo);

		if (!dbOnly) {
			Logger logger = Logging.getLogger(logName);

			logger.error(logEvent);
		}
	}

	private void separateIf(String operand, String operator, Map calledScripts) {
		int ind = operand.indexOf(";");
		String var1 = null;
		String var2 = null;

		if (ind == -1) {
			var1 = operand.substring(0, operand.length());
			calledScripts.put(var1, operator);
		} else {
			var1 = operand.substring(0, ind);
			var2 = operand.substring(ind + 1);
			calledScripts.put(var1, operator);
			calledScripts.put(var2, operator);
		}
	}

	/**
	 * Sets current row
	 */
	private void setCurrentRow(int i) {
		currentRow = i;
	}

	/**
	 * Tables are:
	 * 
	 * 1. Always obtained via a rule. 2. Always have an associated z-script (a
	 * script that defines HOW to access the table).
	 * 
	 * If the Rule is not found, we don't do anything in this method.
	 * 
	 * @param operand
	 * @param calledScripts
	 * @param miniScript
	 * @return
	 * @throws SPException
	 */
	private void setSourceScript(String operand, Map calledScripts,
			List miniScript) throws SPException {
		String name = operand.substring(0, operand.length() - 1);
		RulesVO rulesVO = pr.getBestMatchTableId(name);

		if (rulesVO != null) {
			String ruleName = rulesVO.getRuleName();

			// Artificially cache this so that the called scripts (soon to be
			// evaluated) can readily find it.
			CSCache.getCSCache().setScriptPKBy_BestMatchElements(ruleName, pr);

			calledScripts.put(ruleName + ":", "getscript");

			// Create a Label instruction dynamically : insert a call into
			// calledScripts
			Inst inst = makeInstObject("getscript " + ruleName + ":");

			inst.setInstLineNr(getScriptSize());
			inst.compile(this);
			miniScript.add(inst);
		}
	}

	/**
	 * Determines if the current SPException merits the aborting of the current
	 * script.
	 * 
	 * @param e
	 * @param inst
	 * 
	 * @return
	 */
	private boolean shouldAbortCurrentScript(SPException e, Inst inst) {
		boolean shouldAbortCurrentScript = false;

		if (inst instanceof ValidateInst) {
			String severity = ((ValidateInst) inst).getSeverity();

			if (severity.equals(ValidateInst.SEVERITY_HARD)) {
				if (abortOnHardValidationError) {
					shouldAbortCurrentScript = true;
				} else {
					shouldAbortCurrentScript = false;
				}
			}
		} else {
			shouldAbortCurrentScript = true;
		}

		return shouldAbortCurrentScript;
	}

	/**
	 * Determines if the errored validate instruction is to be logged.
	 * 
	 * @param inst
	 * 
	 * @return
	 */
	private boolean shouldLogValidateInstruction(Inst inst) {
		boolean shouldLogErroredValidateInstruction = false;

		if (inst instanceof ValidateInst) {
			ValidateInst validateInst = (ValidateInst) inst;

			if (validateInst.getReporting().equalsIgnoreCase(
					ValidateInst.REPORTING_YES)) {
				shouldLogErroredValidateInstruction = true;
			}
		}

		return shouldLogErroredValidateInstruction;
	}

	/**
	 * Determines if a validation should be added to the validation output stack
	 * based on the type of SPException.
	 * 
	 * @param e
	 * 
	 * @return
	 */
	private boolean shouldOutputValidationError(SPException e, Inst inst) {
		boolean shouldOutputValidationError = false;

		if (inst instanceof ValidateInst) {
			shouldOutputValidationError = (e.getErrorCode() == SPException.VALIDATION_ERROR);
		}

		return shouldOutputValidationError;
	}

	/**
	 * Return the compiled script to the cache. This can be done once the
	 * compiled script has been cloned into the <CODE>script</CODE> variable.
	 * Also checks for analyzer mode and not null before it does it.
	 */

	// private void returnCompiledScriptToCache()
	// {
	// if (!this.analyzerMode)
	// {
	// if (this.compiledScript != null)
	// {
	// CSCache.getCSCache().returnCompiledScript(this.compiledScript);
	// this.compiledScript = null;
	// }
	// }
	// }

	/**
	 * @param header
	 * @param value
	 * 
	 * @see ScriptProcessor#addToContext(String, String)
	 */
	public void addToContext(String header, String value) {
		context.put(header, value);
	}

	/**
	 * Return the compiled script to the cache. This can be done once the
	 * compiled script has been cloned into the <CODE>script</CODE> variable.
	 * Also checks for analyzer mode and not null before it does it.
	 */

	// private void returnCompiledScriptToCache()
	// {
	// if (!this.analyzerMode)
	// {
	// if (this.compiledScript != null)
	// {
	// CSCache.getCSCache().returnCompiledScript(this.compiledScript);
	// this.compiledScript = null;
	// }
	// }
	// }

	/**
	 * @param praseDocBuilder
	 */
	public void loadDocument(PRASEDocBuilder praseDocBuilder)
			throws SPException {
		if (!getPraseDocBuilders().contains(praseDocBuilder)) {
			if (!praseDocBuilder.isDocumentBuilt()) {
				praseDocBuilder.build();
			}

			// Even though the Document may not have been built, add the key so
			// that
			// we don't keep repeatedly trying to build a dead Document.
			getPraseDocBuilders().add(praseDocBuilder);

			if (praseDocBuilder.isDocumentBuilt()) // It's possible that the
				// Document failed to build.
			{
				loadDocument(praseDocBuilder.getRootElementName(),
						praseDocBuilder);

				if (spTest.shouldCaptureTestData()) {
					spTest.storePRASEDocument(praseDocBuilder);
				}

				if (spRecorder.shouldRecord()) {
					spRecorder.recordInputDocument(praseDocBuilder);
				}
			}
		}
	}

	/**
	 * @param rootElementName
	 * @param document
	 * 
	 * @see ScriptProcessor
	 */
	public void loadDocument(String rootElementName, Document document) {
		getSPParams().addDocument(rootElementName, document);

		if (spRecorder.shouldRecord()) {
			spRecorder.recordInputDocument(document);
		}
	}

	/**
	 * @param rootElementName
	 * @param xmlDocument
	 */
	public void loadDocument(String rootElementName, String xmlDocument) {
		getSPParams().addDocument(rootElementName, xmlDocument);

		if (spRecorder.shouldRecord()) {
			spRecorder.recordInputDocument(xmlDocument);
		}
	}

	/**
	 * @param rootElementName
	 * @param voDocument
	 */
	public void loadDocument(String rootElementName, VOObject voDocument) {
		getSPParams().addDocument(rootElementName, voDocument);

		if (spRecorder.shouldRecord()) {
			spRecorder.recordInputDocument(voDocument);
		}
	}

	/**
	 * @param praseDocBuilder
	 */
	public void loadRootDocument(PRASEDocBuilder praseDocBuilder)
			throws SPException {
		getWS().putAll(praseDocBuilder.getBuildingParameters());

		loadDocument(praseDocBuilder);
	}

	/**
	 * @return
	 */
	public List<PRASEDocBuilder> getPraseDocBuilders() {
		return praseDocBuilders;
	}

	/**
	 * @return
	 * 
	 * @see #getHibernateSession()
	 * @see #hibernateSession
	 */
	public Session getHibernateSession() {
		if (hibernateSession == null) {
			hibernateSession = SessionHelper
					.getSeparateSession(SessionHelper.EDITSOLUTIONS);
		}

		return hibernateSession;
	}

	/**
	 * @see super#clearResultDocVO()
	 */
	public void clearResultDocVO() {
		getSPParams().getDocumentByName(SPParams.RESULTDOCVO).getRootElement()
		.clearContent();
	}

	/**
	 * If a script runs without any validation errors/warning we log this as
	 * well. There is a potential that this is "too" global and some how needs
	 * to be narrowed to specific cases (identified via the script writer - NOT
	 * to be hard coded).
	 * <p/>
	 * We fake the logging mechanism to log a non-error/non-warning.
	 * 
	 * @param scriptProcessDateTime
	 */

	private void logSuccessfulValidation(EDITDateTime scriptProcessDateTime) {
		String message = "No Validation Errors/Warnings Encountered";

		SPException spException = new SPException(message,
				SPException.GENERAL_MESSAGE);

		logErroredValidateInstruction(message, ValidateInst.SEVERITY_SUCCESS,
				spException, scriptProcessDateTime, true);
	}

	/**
	 * @return
	 * 
	 * @see ScriptProcessorImpl#spRecorder
	 */
	public Document getResultDocVO() {
		return getSPParams().getDocumentByName(SPParams.RESULTDOCVO);
	}

	/**
	 * @return exportFile Path to write debugging file
	 * 
	 *         Currently set to write "ExecPRASE" file to EDITExports/EAS folder
	 */
	private String getExportFile() {
		EDITDateTime runDate = new EDITDateTime();
		String dateString = runDate.getFormattedDateTime();
		String fileName = "ExecPRASE";

		EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

		String exportFile = export1.getDirectory() + fileName + ".txt";

		return exportFile;
	}

	private String getConversionErrorFile() {
		EDITDateTime runDate = new EDITDateTime();
		String dateString = runDate.getFormattedDateTime();
		String fileName = "ConversionErrors";

		EDITExport error1 = ServicesConfig.getEDITExport("ExportDirectory1");

		String errorFile = error1.getDirectory() + fileName + ".txt";

		return errorFile;
	}

}
