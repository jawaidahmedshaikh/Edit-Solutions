package engine.component;

import contract.Segment;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.ScriptChainNodeWrapper;
import edit.common.vo.ClientVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.GroupSetupVO;
import edit.common.vo.NaturalDocVO;
import edit.common.vo.QuoteVO;
import edit.common.vo.RedoDocVO;
import edit.common.vo.RulesVO;
import edit.common.vo.SPOutputVO;
import edit.common.vo.ScriptVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.VOObject;
import edit.services.component.AbstractComponent;
import edit.services.logging.Logging;
import engine.business.Analyzer;
import engine.dm.StorageManager;
import engine.dm.dao.DAOFactory;
import engine.sp.CSCache;
import engine.sp.ProductRuleProcessor;
import engine.sp.SPException;
import engine.sp.SPOutput;
import engine.sp.ScriptProcessor;
import engine.sp.ScriptProcessorImpl;
import engine.sp.custom.document.ClientDocument;
import engine.sp.custom.document.GroupSetupDocument;
import engine.sp.custom.document.InvestmentAllocationOverrideDocument;
import engine.sp.custom.document.InvestmentDocument;
import engine.sp.custom.document.SegmentDocument;
import fission.utility.Util;
import fission.utility.XMLUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import logging.Log;
import logging.LogEvent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;


/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jun 4, 2003
 * Time: 10:19:39 AM
 * To change this template use Options | File Templates.
 */
public class AnalyzerComponent extends AbstractComponent implements Analyzer  {

    private StorageManager  sm;       // Used to reference StorageManager
    private ScriptProcessor	sp;       // Used to reference ScriptProcessor
    private ProductRuleProcessor  pr; // Used to reference Product Rule Processor


       public AnalyzerComponent() {

        init();
    }

	private final void init() {

	    sm = new StorageManager();
        pr = new ProductRuleProcessor();
        sp = new ScriptProcessorImpl();
        sp.setProductRuleProcessor(pr);
        sp.setStorageManager(sm);
        sp.setAnalyzerMode(true);
	}

    //debug clear and do payout projection
    public void clearScriptProcessor() {

        sp.clear();
    }

    //debug clear
    public void setScriptName(String scriptName) {

        sp.setScriptName(scriptName);
    }

   //save parameter methods
    public String getScriptName() {

        return sp.getScriptName();
    }

    public void loadScript(long scriptId) throws Exception{

        sp.loadScript(scriptId);
    }

    //debugClear
    public void setScriptLoaded(boolean scriptLoaded) {

        sp.setScriptLoaded(scriptLoaded);
    }

    //debug and projection
    public void resetScriptProcessor() {

        sp.reset();
    }

    //for debug views
    public String getViewerMode() {

        return sp.getViewerMode();
    }

    //save  all parameters
    public String[] getDataStack() {

        return sp.getDataStack();
    }

    //save parameters and debug processing
    public Map getWS() {

        return sp.getWS();
    }

    //save parameters and debug processing
    public String[] getFunctions() {

        return sp.getFunctions();
    }

    public String[] getVectorTable() {

        return sp.getVectorTable();
    }

    //save parameters and debug processing
    public String[] getOutput() {

        SPOutput spOutput = sp.getSPOutput();

        SPOutputVO spOutputVO = spOutput.getSPOutputVO();

        String[] outputAsXML = null;

        try
        {
            outputAsXML = Util.marshalVOs(spOutputVO.getVOObject());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return outputAsXML;
    }

    //save parameters and debug processing
    public String[] getFunctionEntry(String name) {

         List tableDataVector = sp.getFunctionEntry(name);

//         List tableDataVector = null;
//
//         if(functionEntry != null)
//             tableDataVector = functionEntry.getTableData();

         List td = new ArrayList();

         if ((tableDataVector != null) && (tableDataVector.size() > 0)) {

             for (int i = 0; i < tableDataVector.size(); i++)  {

//                 td.add(Double.toString(((Double) tableDataVector.get(i)).doubleValue()));
                 td.add(tableDataVector.get(i).toString());
             }
         }

         return ((String[]) td.toArray(new String[td.size()]));
     }

    public String[] getVectorTableEntry(String name) {

        Map vectorTableEntry = sp.getVectorEntry(name);

        List vectorTableEntryDataList = new ArrayList();
        if (vectorTableEntry != null)
        {
            Iterator vectorTableEntryKeysItr = vectorTableEntry.keySet().iterator();
            List vectorTableEntryKeysList = new ArrayList();

            String key = null;
            while ( vectorTableEntryKeysItr.hasNext() ) {

                key = (String) vectorTableEntryKeysItr.next();
                vectorTableEntryKeysList.add( new Integer(key) );
            }

            Collections.sort(vectorTableEntryKeysList);

            String keyValue = null;
            for ( int i = 0; i < vectorTableEntryKeysList.size(); i++ ) {

//          vectorTableDataList.add(Double.toString(((Double) vectorTableDataItr.next()).doubleValue()));
                keyValue = vectorTableEntryKeysList.get(i).toString();
                vectorTableEntryDataList.add( vectorTableEntry.get(keyValue).toString() );
            }
        }
        return ( (String[]) vectorTableEntryDataList.toArray( new String[vectorTableEntryDataList.size()]) );
    }

    //save parameters and debug processing
     public String[] getScriptLines() {

         return sp.getScriptLines();
     }


    //save parameters and debug processing
     public String getInstPtr() {

         Integer pointer = new Integer(sp.getInstPtr());

         return pointer.toString();
     }

    //save parameters and debug processing
     public String getLastInstPtr() {

         Integer lastPointer = new Integer(sp.getLastInstPtr());

         return lastPointer.toString();
     }

    //save parameters and debug processing
     public String getCurrentRow() {

         Integer row = new Integer(sp.getCurrentRow());

         return row.toString();
     }

    //debug
    public String[] getBreakPoints() {

         return sp.getBreakPoints();
     }


    //debug and projection
    public SPOutputVO execScriptProcessor() throws Exception {

        SPOutputVO spOutputVO;

        try
        {
            sp.exec();
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();

            //throw e; // Don't rethrow
        }
        finally
        {
            spOutputVO = sp.getSPOutput().getSPOutputVO();
        }

        return spOutputVO;
    }

     //debug
     public SPOutputVO execSingleInstScriptProcessor() throws SPException {

         SPOutputVO spOutputVO;

         try
         {
             sp.execSingleInst();
         }
         catch (SPException e)
         {
            System.out.println(e);

            e.printStackTrace();

            throw e;
         }
         finally
         {
            spOutputVO = sp.getSPOutput().getSPOutputVO();
         }

         return spOutputVO;
     }

    //debug
     public boolean containsBreakPointKeySP(String name) {

         return sp.containsBreakPointKey(name);
     }

    //debug
     public void removeBreakPointEntrySP(String name)  {

         sp.removeBreakPointEntry(name);
     }

    //debug
     public void addBreakPointEntrySP(String name, Boolean data)  {

         sp.addBreakPointEntry(name, data);
     }
     
     public HashMap<String, String> getDocumentXML() 
     {
    	 HashMap<String, String> docXml = new HashMap<String, String>();
    	 Map<String, Document> docs = sp.getSPParams().getDocuments();
    	 for(String docName : docs.keySet()) {
    		 StringWriter writer = new StringWriter();
    		 org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(writer, org.dom4j.io.OutputFormat.createPrettyPrint());
    		 try {
    			 xmlWriter.write(docs.get(docName));
    			 xmlWriter.close();
    		 } catch(IOException ex) {
    			 throw new RuntimeException("Could not write the XML document", ex);
    		 }
    		 
    		 docXml.put(docName, writer.toString());
    	 }
    	 
    	 return docXml;
     }

    //debug
     public void setViewerModeSP(String newValue)  {

         sp.setViewerMode(newValue);
     }



     //set projection parametes
     public void addParamEntry(String name, String data)  {

            sp.addParamEntry(name, data);
     }

     //set projection parameters
     public void addParamEntry(String name, List vector) {

         sp.addParamEntry(name, vector);
     }

    //projection parameters
     public void removeRider(int index) {

         sp.removeRider(index);
     }

     //projection parameters
     public Map getParamOfSelectedRider(String selectedRider,
                                               int index) throws Exception {

         return sp.getParamOfSelectedRider(selectedRider,index);
     }

    //projection parameters
     public void clearRiderParameters(String selectedRider, int selectedIndex) {

         sp.clearRiderParameters(selectedRider, selectedIndex);
     }



    //projection and debug
    public void setFinancialType(String value)  {

		sp.setFinancialType(value);
	}

    //projection and debug
    public Map getFinancialTypeData(String financialType, int selectedIndex)  {

		return sp.getFinancialTypeData(financialType, selectedIndex);
	}

        //projection and debug
	public void setSelectedIndex(int value) {

		sp.setSelectedIndex(value);
	}

    //projection
	public VOObject[] getOutputVector() throws Exception
    {
        SPOutput spOutput = sp.getSPOutput();

        VOObject[] calculationOutput = spOutput.getSPOutputVO().getVOObject();

		return calculationOutput;
	}

//    //projection
//	public boolean containsOutputKey(String name) {
//
////		return sp.containsOutputKey(name);
//
//        return
//	}



    public void initQuoteSP(QuoteVO quoteVO)  throws Exception {

//        QuoteParameters quoteParameters = new QuoteParameters();
//
//        sp.setAnalyzerMode(true);
//
//        quoteParameters.loadParamters(quoteVO, sp);
    }
//   public void initContractSP(BatchVO batchVO)  throws Exception {
//
////       ContractParameters contractParameters = new ContractParameters();
////
////       BatchProcessor bp = new BatchProcessor();
////
////       Map clientCache = new HashMap();
////
////       sp.setAnalyzerMode(true);
////
////       SegmentVO segmentVO = batchVO.getSegmentVO();
////
////       TransactionsVO[] transactionsVOs = batchVO.getTransactionsVO();
////
////       clientCache = bp.populateClientCache(segmentVO);
////
////       contractParameters.loadParamtersForAnalyzer(segmentVO, transactionsVOs[0], clientCache, sp);
//    }

    public ScriptChainNodeWrapper getScriptCallChain(ScriptChainNodeWrapper parent,
                                                      boolean calledFromAnalyzer) throws Exception {

//		ScriptProcessor scriptProcessor = new ScriptProcessor(pr,sm);

        ScriptChainNodeWrapper scriptChainNodeWrapper = null;

        String scriptName = getScriptName();
        if (scriptName == null) {

            return null;
        }
        else {

            ScriptVO scriptVO = DAOFactory.getScriptDAO().findScriptByName(scriptName)[0];

            long scriptId = scriptVO.getScriptPK();

            scriptChainNodeWrapper = sp.loadScript(scriptId, parent, calledFromAnalyzer);

            return scriptChainNodeWrapper;
        }
    }

    public void setDrivingCRStructure(long productStructurePK,
                                       long ruleId,
                                        String processName,
                                         String event,
                                          String eventType,
                                           String effectiveDate,
                                            String ruleName)
                                     throws Exception {

        Long driverScriptPK = null;
        pr.setProcessName(processName);
        pr.setEvent(event);
        pr.setEventType(eventType);
        pr.setEffectiveDate(effectiveDate);
        pr.setProductStructurePK(productStructurePK);

        if (ruleId == 0) {

            driverScriptPK = CSCache.getCSCache().getScriptPKBy_BestMatchElements("Driver", pr);
        }
        else {

            RulesVO rulesVO = DAOFactory.getRulesDAO().findByRuleId(ruleId)[0];
            
            driverScriptPK = rulesVO.getScriptFK();
        }

        sp.loadScript(driverScriptPK);

        ScriptVO scriptVO = DAOFactory.getScriptDAO().findScriptById(driverScriptPK)[0];

        String scriptName = scriptVO.getScriptName();

        setScriptName(scriptName);
    }

    public void setAnalyzerMode(boolean analyzerMode) throws Exception {

        sp.setAnalyzerMode(analyzerMode);
    }

    private Element getElementFromVO(VOObject vo) throws Exception
    {
        Document document = null;

        try
        {
            document = XMLUtil.parse(Util.marshalVO(vo));
        }
        catch (DocumentException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        Element voElement = document.getRootElement();
        return voElement;
    }

    public void loadScriptAndParameters(String rootElementName, VOObject voDocument, String processName, String event,
                                         String eventType, String effectiveDate,
                                          long productKey, boolean isAnalyzeTransaction) throws Exception {
        setAnalyzerMode(true);
  
         // When analyzing transaction the EDITTrx may be built from cloudland hence
        // build the GroupSetupDocuemnt from VO instead of hitting the database.
        if (isAnalyzeTransaction  && rootElementName.equalsIgnoreCase("NaturalDocVO"))
        {
            loadGroupSetupDocument(voDocument);
        }
        else
        {
            sp.loadDocument(rootElementName, voDocument);
        }

        try
        {
            sp.loadScript(processName, event, eventType, productKey, effectiveDate);
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();
            
            throw e;
        }
    
        sp.setNoEditingException(false);

//        sp.exec();
//
//        List output = sp.getScriptOutput();
//
//        return output;
    }

    public void loadScriptAndParameters(NaturalDocVO naturalDocVO, String processName, String event,
                                        String eventType, String effectiveDate,
                                        long companyKey, boolean isAnalyzeTransaction) throws Exception
    {
        setAnalyzerMode(true);

        // When analyzing transaction the EDITTrx may be built from cloudland hence
        // build the GroupSetupDocuemnt from VO instead of hitting the database.
        if (isAnalyzeTransaction)
        {
            loadGroupSetupDocument(naturalDocVO);

            /*build & load client doc in advance****************
             */
            buildClientDocumentFromNaturalVO(naturalDocVO, sp);

            /*build investmentAllocationOverrideDoc from cloudLand**************
             * groupSetupVO has everything it needs to build the doc
             */
            GroupSetupVO groupSetupVO = naturalDocVO.getGroupSetupVO(0);

            InvestmentAllocationOverrideDocument.buildInvestmentAllocationOverrideDocFromCloudland(groupSetupVO, sp);

            /* build investmentDocument from cloudLand ************************
             * groupSetupDocument has everything it needs to build the investment doc
             * investmentPk is retrieved from groupsetupDoc
             */
            SegmentVO segmentVO = naturalDocVO.getBaseSegmentVO().getSegmentVO();
            EDITTrxVO editTrxVO = groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0);

            EDITMap editMap2 = new EDITMap(InvestmentDocument.BUILDING_PARAMETER_EDITTRXPK, editTrxVO.getEDITTrxPK() + "");
            editMap2.put(InvestmentDocument.BUILDING_PARAMETER_SEGMENTPK, segmentVO.getSegmentPK() + "");

            InvestmentDocument investmentDocument = new InvestmentDocument(editMap2);
            investmentDocument.buildInvestmentDocFromCloudland(sp);
            sp.loadRootDocument(investmentDocument);
        }

        try
        {
            sp.loadScript(processName, event, eventType, companyKey, effectiveDate);
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        sp.setNoEditingException(false);

        //        sp.exec();
        //
        //        List output = sp.getScriptOutput();
        //
        //        return output;
    }

    public void loadScriptAndParametersWithDocument(String rootElementName, Document voDocument, String processName, String event,
                                          String eventType, String effectiveDate,
                                          long productKey) throws Exception
    {
        sp.loadDocument(rootElementName, voDocument);

        setAnalyzerMode(true);
        try
        {
            sp.loadScript(processName, event, eventType, productKey, effectiveDate);
        }
        catch (SPException e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }


        sp.setNoEditingException(false);

    }

    private void buildClientDocumentFromNaturalVO(VOObject voDocument,ScriptProcessor sp)
    {

        try
        {
            // build ClientDocument
             Element clientVOElement = new DefaultElement("ClientVO");
             long segmentPK = ((NaturalDocVO)voDocument).getBaseSegmentVO().getSegmentVO().getSegmentPK();
             long contractClientFK = ((NaturalDocVO)voDocument).getBaseSegmentVO().getClientVO()[0].getContractClientVO()[0].getContractClientPK();

            ClientVO[] clientDetailVO = ((NaturalDocVO)voDocument).getBaseSegmentVO().getClientVO();
            Element clientDetailVOElement = null;
            for(int i=0;i<clientDetailVO.length;i++)
            {
                clientDetailVOElement = getElementFromVO(clientDetailVO[i]);
                //clientVOElement.add(clientDetailVOElement);
            }

            //there will be only one clientDoc in a naturalDoc for the transaction, so there will be only one clientDetailVOElement
            ClientDocument clientDocumentBuilder =
                new ClientDocument(clientDetailVOElement, segmentPK + "", contractClientFK + "");

            //System.out.println("clientDocumentBuilder-" + clientDocumentBuilder.asXML()); //??remove

            sp.loadRootDocument(clientDocumentBuilder);

        }
        catch (Exception e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }
    }
    
    /**
     * Builds GroupSetup document manually and loads it in to the ScriptProcessor for future use.
     * @param voDocument
     * @throws Exception
     */
    private void loadGroupSetupDocument(VOObject voDocument) throws Exception
    {   
        GroupSetupVO groupSetupVO = null;

        if (voDocument instanceof RedoDocVO)
        {
            groupSetupVO = ((RedoDocVO) voDocument).getGroupSetupVO(0);
        }
        else if (voDocument instanceof NaturalDocVO)
        {
            groupSetupVO = ((NaturalDocVO) voDocument).getGroupSetupVO(0);
        }

        EDITTrxVO editTrxVO = groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0);

        EDITMap editMap = new EDITMap(GroupSetupDocument.BUILDING_PARAMETER_NAME_EDITTRXPK, editTrxVO.getEDITTrxPK()+"");

        GroupSetupDocument groupSetupDocument = new GroupSetupDocument(editMap);

                groupSetupDocument.buildGroupSetupElementFromVO(groupSetupVO);

        groupSetupDocument.setDocumentBuilt(true);

                sp.loadRootDocument(groupSetupDocument);
        }
    
    /**
     * @see #loadScriptAndParameters(Long)
     * @param segmentPK
     * @throws Exception
     */
    public void loadScriptAndParameters(Long segmentPK) throws Exception
    {
        sp.setAnalyzerMode(true);
        
        sp.setNoEditingException(false);
        
        Segment segment = Segment.findByPK(segmentPK);
        
        Long productStructurePK = segment.getProductStructureFK();
        
        sp.addWSEntry(SegmentDocument.BUILDING_PARAMETER_NAME_SEGMENTPK, segmentPK.toString());
        
        sp.loadScript("NBQuote", "*", "*", productStructurePK.longValue(), new EDITDate().getFormattedDate());
	}
}
