/**
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jun 3, 2003
 * Time: 2:30:24 PM
 * To change this template use Options | File Templates.
 */

package engine.business;

import edit.common.ScriptChainNodeWrapper;
import edit.common.vo.NaturalDocVO;
import edit.common.vo.QuoteVO;
import edit.common.vo.SPOutputVO;
import edit.common.vo.VOObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;


//import engine.dm.DD;


public interface Analyzer  {

    public void clearScriptProcessor();
    public void setScriptName(String scriptName);
    public String getScriptName();
    public void setScriptLoaded(boolean scriptLoaded);
    public void resetScriptProcessor();
    public SPOutputVO execScriptProcessor() throws Exception;
    public SPOutputVO execSingleInstScriptProcessor() throws Exception;
    public boolean containsBreakPointKeySP(String name);
    public void removeBreakPointEntrySP(String name);
    public void addBreakPointEntrySP(String name, Boolean data);
    public void setViewerModeSP(String newValue);
    public HashMap<String, String> getDocumentXML();
    public String getViewerMode();
    public String[] getDataStack();
    public Map getWS();
    public String[] getFunctions();
    public String[] getVectorTable();
    public String[] getOutput();
    public String[] getFunctionEntry(String name);
    public String[] getVectorTableEntry(String name);
    public String   getInstPtr();
    public String   getLastInstPtr();
    public String   getCurrentRow();
    public String[] getBreakPoints();
    public void loadScript(long scriptId) throws Exception;

    public String[] getScriptLines();

    public void addParamEntry(String name, String data);

    public void addParamEntry(String name, List vector);

    public void removeRider(int index);

    public Map getParamOfSelectedRider(String selectedRider ,int index) throws Exception;


    public void setFinancialType(String value);

    public void setSelectedIndex(int value);

    public Map getFinancialTypeData(String  financialType, int selectedIndex);

    public void clearRiderParameters(String selectedRider, int selectedIndex);

    public VOObject[] getOutputVector() throws Exception;

//    public boolean containsOutputKey(String name);

    public void initQuoteSP(QuoteVO quoteVO)  throws Exception;

//    public void initContractSP(BatchVO batchVO)  throws Exception;

    public ScriptChainNodeWrapper getScriptCallChain(ScriptChainNodeWrapper parent,
                                                       boolean calledFromAnalyzer)throws Exception;

    public void setDrivingCRStructure(long productStructureId,
                                        long ruleId,
                                        String processName,
                                        String event,
                                        String eventType,
                                        String effectiveDate,
                                        String ruleName) throws Exception;

    public void setAnalyzerMode(boolean analyzerMode) throws Exception;

    public void loadScriptAndParameters(String rootElementName, VOObject voDocument, String processName, String event,
                                         String eventType, String effectiveDate,
                                          long productKey, boolean isAnalyzeTransaction) throws Exception;

    public void loadScriptAndParameters(NaturalDocVO naturalDocVO, String processName, String event,
                                         String eventType, String effectiveDate,
                                          long productKey, boolean isAnalyzeTransaction) throws Exception;

    public void loadScriptAndParametersWithDocument(String rootElementName, Document voDocument, String processName, String event,
                                          String eventType, String effectiveDate,
                                          long productKey) throws Exception;
    
    /**
     * A specialized method that expects [only] and SegmentPK and will default the following:
     * 1. The SegmentDocVO will be the Root Document / Root Element Name.
     * 2. The Process Name will be "NBQuote".
     * 3. The Event and EventType will be "*".
     * 4. The EffectiveDate will default to the System's date.
     */
    public void loadScriptAndParameters(Long segmentPK) throws Exception;  
}
