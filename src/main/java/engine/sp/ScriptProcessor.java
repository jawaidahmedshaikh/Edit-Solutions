/*
 * User: gfrosti
 * Date: Aug 24, 2004
 * Time: 2:19:12 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp;

import edit.common.ScriptChainNodeWrapper;
import edit.common.vo.VOObject;
import engine.dm.StorageManager;
import engine.sp.custom.document.PRASEDocBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.Session;



public interface ScriptProcessor extends Serializable
{
    /**
     * Used to determine which stack to push or pop from
     */
    int DATA_STACK = 0;
    int CALLRETURN_STACK = 1;
    int LOOP_STACK = 2;
    int IF_STACK = 3;

    void setProductRuleProcessor(ProductRuleProcessor pr);

    void setStorageManager(StorageManager sm);

    void addFunctionEntry(String tableName, List rateTable);

    void addCalculationOutput(Element element);

    void addParamEntry(String name, String data);

    void addParamEntry(String name, List vector);

    void addWSVector(String name, List vector);

    void addVectorEntry(String name, Map vector);

    Map getFinancialTypeData(String financialType
                                          , int selectedIndex);

    Map getParamOfSelectedRider(String selectedRider
                                             , int index);

    void addWSEntry(String name, String data);

    void addBreakPointEntry(String name, Boolean data);

    String[] getBreakPoints();

    boolean containsBreakPointKey(String name);

    void exec() throws SPException;

    void execSingleInst() throws SPException;

    String getAliasFullyQualifiedName(String aliasName) throws SPException;

    String[] getDataStack();

    int getInstPtr();

    int getLastInstPtr();

    int getCurrentRow();

    int getLabelEntry(String name);

    List getFunctionEntry(String name);

    Map getVectorEntry(String name);

    String[] getFunctions();

    String[] getVectorTable();

    String getScriptElementAt(int i);

    int getScriptSize();

    String getScriptName();

    String getWSEntry(String name);

    Object getWSVector(String name);

    Map getWS();

    String getViewerMode();

    void setViewerMode(String newValue);

    ScriptChainNodeWrapper loadScript(long scriptId,
                                             ScriptChainNodeWrapper parent,
                                             boolean calledFromAnalyzer) throws SPException;

    void loadScript(String process, String event,
                           String eventType, long productKey,
                           String effectiveDate) throws SPException, RuntimeException;

    void loadScript(long scriptId) throws SPException;

    String popFromStack() throws SPException;

    Object pop(int stackType);

    String peekFromStack();

    Object peek(int stackType);

    void push(String anElement);

    void push(int stackType, Object anElement);

    void addAliasEntry(String aliasName, String dataName);

    void putLabelEntry(String name, int i);

    void removeBreakPointEntry(String name);

    void reset();

    void removeRider(int index);

    void clear();

    void setInstPtr(int i);

    void setScriptName(String newValue);

    void setScriptLoaded(boolean newValue);

    void setSelectedIndex(int newValue);

    void setFinancialType(String newValue);

    void setStopRun(boolean sr);

    ProductRuleProcessor getProductRule();

    String[] getScriptLines();

    void clearRiderParameters(String selectedRider, int selectedIndex);

    void setNoEditingException(boolean noEditingExceptionErrors);

    void setAnalyzerMode(boolean analyzerModeValue);

    Object removeForeachEntry(String name);

    void setConditionSwitch(boolean value);

    boolean getConditionSwitch();

    void addIdentifierEntry(String fieldPath, String id);

    SPOutput getSPOutput();

    void addNewElement(String childPath);

    void addNewElementValue(String fieldPath, String fieldName, String data);

    String getElementValue(String fieldPath, String fieldName);

    boolean verifyPathExistence(String fieldPath);

    /*   Use by the foreach instruction
     */
    boolean setLastActiveElement(String fieldPath);

    void removeLastActiveElement(String fieldPath);

    void close();

    void incrementInstPtr();

    boolean ifStackIsEmpty();

    boolean loopStackIsEmpty();

    boolean stackEmpty(int stackType);

    void incrementInstPtr(int lineforExit);
    
    void setDataMapValue(String mapName, String key, String value);
    
    String getDataMapValue(String mapName, String key);

    /**
     * True if the foreach Map caontains the supplied key.
     * @param key
     * @return
     */
    boolean forEachHTContainsKey(String key);

    /**
     * Returns the SPParams in its current state.
     * @return
     */
    public SPParams getSPParams();

    /**
     * Validation errors will not terminate the current script execution.
     */
    public int CONTINUE_ON_VALIDATION_ERROR = 0;

    /**
     * Validation errors will terminate the current script execution.
     */
    public int ABORT_ON_VALIDATION_ERROR = 1;

    /**
     * Sets whether the current script should stop executing for a validation instruction that fails. The default
     * behavior is to abort.
     * @param abortOnHardValidationError
     */     
    void setAbortOnHardValidationError(boolean abortOnHardValidationError);

    /**
     * Stores header/value pairs for the intention of supplying contextual information under the possibility of
     * an error while running a script. An example might be a header/value of "Contract Number"/"1234321". It
     * is the Context instruction that sets these values.
     * @see Context
     * @param header
     * @param value
     */
    public void addToContext(String header, String value);
    
  /**
   * Adds a PRASEDocBuilder to the Map of builders currently activated.
   * If the specified builder has been added before, then the request is
   * ignored. If the specified builder has a shared DocumentType of a currently
   * activated builder, then the specified builder is added to the root 
   * document structure of the existing document. 
   * 
   * For example:
   * 
   * If a ClientDocument of RoleTypeCT OWN is currently activated, but now
   * another ClientDocument is to be added of RoleTypeCT ANN, then the ANN
   * document's contents is added to the root structure of the existing
   * document. The goal is to share the same document root for the same DocumentType.
   * @param praseDocBuilder
   */
    public void loadDocument(PRASEDocBuilder praseDocBuilder) throws SPException;
    
  /**
   * Typically, a document would be loaded within the scripts themselves after
   * the script writer has placed the necessary building parameters in Working
   * Storage (WS). However, due to the "chicken-before-the-egg" scenario, the script writer
   * may not have access to the initial WS params. In this initial case, the parameters
   * need to be placed in WS on behalf of the script writer. Processing is then delegated
   * to the normal loadDocument method.
   * @see #loadDocument(PRASEDocKey)
   * @param praseDocBuilder
   */
    public void loadRootDocument(PRASEDocBuilder praseDocBuilder) throws SPException;
    
    /**
     * Used for documents that are [not] participating as a PRASEDocBuilder. Nonetheless,
     * it is still necessary to "identify" the Document with its DocumentType.
     * @param rootElementName the name of the Document's root Element
     * @param document
     * @see PRASEDocBuilder.DocumentType
     */
    public void loadDocument(String rootElementName, Document document);    
    
    /**
     * Used for documents that are [not] participating as a PRASEDocBuilder. Nonetheless,
     * it is still necessary to "identify" the Document with its DocumentType.
     * @param rootElementName the the name of the Document's root Element
     * @param xmlDocument
     * @see PRASEDocBuilder.DocumentType
     */    
    public void loadDocument(String rootElementName, String xmlDocument);

    /**
     * Used for documents that are [not] participating as a PRASEDocBuilder. Nonetheless,
     * it is still necessary to "identify" the Document with its DocumentType.
     * @param rootElementName the name of the Document's root Element
     * @param voDocument
     * @see PRASEDocBuilder.DocumentType
     */
    public void loadDocument(String rootElementName, VOObject voDocument);
    
    /**
     * The set of keys that identify PRASEDocBuilders that have been activated.
     * PRASEDocKeys identify a specific PRASEDocBuilder which is different than
     * the SPParams class which identifies Documents by DocumentType (a more 
     * general classification).
     * @return
     */
    public List<PRASEDocBuilder> getPraseDocBuilders();  
    
    /**
     * Returns the unique Hibernate Session established for the currently
     * running ScriptProcessor. The Session is separate from any other Session
     * currenlty running and is dedicated to the EDITSOLUTIONS database as it
     * is assumed that no other DB need ever be targeted as of this writing.
     * @return
     */
    public Session getHibernateSession();
    
    /**
     * Queries dynamically executed with ScriptProcessor have their results placed
     * in the ResultDocVO for access by the scripter.
     * @return
     */
    public Document getResultDocVO();
    
    /**
     * Clears the contents of ResultDocVO with the exception of the Root Element.
     */    
    public void clearResultDocVO();

    /**
     * Adds or updates the specified Attribute for the specified Element using the specified
     * value.
     * @param elementPath
     * @param attributeName
     * @param attributeValue
     */
    public void addNewElementAttribute(String elementPath, String attributeName, String attributeValue);
}
