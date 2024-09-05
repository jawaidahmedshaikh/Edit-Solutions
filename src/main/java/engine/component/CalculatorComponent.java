/*
 * User: unknown
 * Date: Jul 31, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.ScriptChainNodeWrapper;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITEngineException;
import edit.common.exceptions.EDITException;
import edit.common.exceptions.EDITLockException;
import edit.common.vo.AreaVO;
import edit.common.vo.AreaValueVO;
import edit.common.vo.ChargeCodeVO;
import edit.common.vo.EDITExport;
import edit.common.vo.ElementLockVO;
import edit.common.vo.FeeDescriptionVO;
import edit.common.vo.FeeVO;
import edit.common.vo.FilteredAreaVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.QuoteVO;
import edit.common.vo.RateTableVO;
import edit.common.vo.RulesVO;
import edit.common.vo.ScriptVO;
import edit.common.vo.TableDefVO;
import edit.common.vo.TableKeysVO;
import edit.common.vo.VOObject;
import edit.common.vo.ValidationVO;
import edit.services.component.AbstractComponent;
import edit.services.config.ServicesConfig;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import engine.AreaKey;
import engine.AreaValue;
import engine.ChargeCode;
import engine.Company;
import engine.Fee;
import engine.FeeDescription;
import engine.FilteredAreaValue;
import engine.FilteredFund;
import engine.ProductStructure;
import engine.Script;
import engine.ScriptLine;
import engine.business.Calculator;
import engine.dm.StorageManager;
import engine.dm.dao.DAOFactory;
import engine.sp.CSCache;
import engine.sp.Getinterestrate;
import engine.sp.PRASETest;
import engine.sp.SPException;
import engine.sp.SPOutput;
import engine.sp.SPRecordedOperator;
import engine.sp.SPRecordedResults;
import engine.sp.ScriptProcessor;
import engine.sp.ScriptProcessorFactory;
import engine.sp.ScriptProcessorImpl;
import engine.sp.custom.document.ClientDocument;
import engine.sp.custom.document.CommissionDocument;
import engine.sp.custom.document.GroupDocument;
import engine.sp.custom.document.PRASEDocBuilder;
import engine.sp.custom.document.ProductStructureDocument;
import engine.sp.custom.document.RiderDocument;
import engine.sp.custom.document.SegmentDocument;
import fission.global.AppReqBlock;
import fission.utility.UtilFile;
import logging.Log;
import logging.LogEvent;


/**
 * The Calculation Engine request controller
 */
public class CalculatorComponent extends AbstractComponent implements Calculator
{

    // Constants
    private static final String A_SCRIPT_PROC_BEAN = "aScriptProcBean";

    // Member variables
//    private StorageManager sm;       // Used to reference StorageManager
//    private ScriptProcessor sp;       // Used to reference ScriptProcessor
//    private ProductRuleProcessor pr; // Used to  reference Product Rule Processor

    /**
     * CalcEngController constructor.
     */

    public CalculatorComponent()
    {

        init();
    }

    private final void init()
    {

//        sm = new StorageManager();
//        pr = new ProductRuleProcessor();
//        sp = new ScriptProcessor(pr, sm);
    }

    public void clearScriptProcessor()
    {

//        sp.clear();
    }

    public void setScriptName(String scriptName)
    {

//        sp.setScriptName(scriptName);
    }

    public String getScriptName()
    {

//        return sp.getScriptName();

        return null;
    }


//	public void loadScript(String loadScript) throws Exception {
//
//		sp.loadScript(loadScript);
//	}

    public void loadScript(long scriptId) throws Exception
    {

//        sp.loadScript(scriptId);
    }

    public void setScriptLoaded(boolean scriptLoaded)
    {

//        sp.setScriptLoaded(scriptLoaded);
    }

    public List getSCParams()
    {

//        return sp.getParams();

        return null;
    }

    public void resetScriptProcessor()
    {

//        sp.reset();
    }

    public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception
    {
        StorageManager sm = new StorageManager();

        long primaryKey = sm.saveVO(valueObject);

        return primaryKey;
    }

    /**
     * Verifies if UnitValue exists for prior or current month for given date.
     * @param filteredFundPK
     * @param chargeCodeFK
     * @param date
     * @return
     */
    public boolean hasFilteredFundHasUnitValueForPriorOrCurrentMonth(Long filteredFundPK, Long chargeCodeFK, EDITDate date)
    {
        FilteredFund filteredFund = FilteredFund.findByPK(filteredFundPK);

        return filteredFund.hasUnitValueForPriorOrCurrentMonth(chargeCodeFK, date);
    }

    public long createOrUpdateVONonRecursive(Object valueObject) throws Exception
    {
        StorageManager sm = new StorageManager();

        long primaryKey = sm.saveVONonRecursive(valueObject);

        return primaryKey;
    }

    public String[] findVOs()
    {
        return null;
    };

    public long saveVO(Object vo)
    {
        return 0;
    };

    public int deleteVO(Class voName, long primaryKey, boolean recursively) throws Exception
    {

        //int key = sm.deleteVORecursively(voName, primaryKey);
        return super.deleteVO(voName, primaryKey, ConnectionFactory.ENGINE_POOL, false);
    }

    public long saveProductStructure(String newProductStructure) throws Exception
    {
        StorageManager sm = new StorageManager();

        return sm.saveProductStructure(newProductStructure);
    }

//	public long saveRuleStructure(String newRuleStructure,
//                                   String newRuleIdStructure,
//                                    String username) throws Exception {
//
//		return sm.saveRuleStructure(newRuleStructure,newRuleIdStructure, username);
//	}


//    public String[] getFunctionEntry(String name) {
//
//        DDFunction functionEntry = sp.getFunctionEntry(name);
//
//		List tableDataVector = null;
//
//		if(functionEntry != null)
//			tableDataVector = functionEntry.getTableData();
//
//		List td = new ArrayList();
//
//		if ((tableDataVector != null) && (tableDataVector.size() > 0)) {
//
//			for(int i=0; i<tableDataVector.size(); i++)  {
//
//				td.add(Double.toString(((DDNumber) tableDataVector.get(i)).getDouble()));
//			}
//		}
//
//        return ((String[]) td.toArray(new String[td.size()]));
//    }

    public String[] getScriptLines()
    {

//        return sp.getScriptLines();

        return null;
    }


    public void execScriptProcessor() throws Exception
    {

//        sp.exec();
    }

//	public void sendSPOutputToCSV(String outputPathName, String outputFileName)	throws Exception {
//
//		sp.sendOutputToCSV(outputPathName, outputFileName);
//	}
//
//	public void sendSPOutputToXML(String outputPathName, String outputFileName)	throws Exception {
//
//		sp.sendOutputToXML(outputPathName, outputFileName);
//	}


//	public String getSPFinancialType() {
//
//		return sp.getFinancialType();
//	}

    public List getParams()
    {

//        return sp.getParams();

        return null;
    }

//	public int getIndexForParams(int selectedIndex) {
//
//		return sp.getIndexForParams(selectedIndex);
//	}

//	public void setParams(DDGroupItem inParams)  {
//
//	   sp.setParams(inParams);
//	}

    public void setAttributeSP(AppReqBlock aAppReqBlock)
    {

//        aAppReqBlock.getHttpSession().setAttribute(A_SCRIPT_PROC_BEAN, sp);
    }

    public void setSPFinancialType(String newValue)
    {

//        sp.setFinancialType(newValue);
    }

    public void setSPSelectedIndex(int newValue)
    {

//        sp.setSelectedIndex(newValue);
    }

//   	public void addParamEntry(DD data)  {
//
//   		sp.addParamEntry(data);
//	}

//    public void addParamEntry(String name, Map hashtable) {
//
//        sp.addParamEntry(name, hashtable);
//    }

//    public void addParamEntry(String name, List vector) {
//
//        sp.addParamEntry(name, vector);
//    }

    public void removeRider(int index)
    {

//        sp.removeRider(index);
    }

    public Map getParamOfSelectedRider(String selectedRider,
                                             int index) throws Exception
    {

//        return sp.getParamOfSelectedRider(selectedRider, index);

        return null;
    }


    public void clearRiderParameters(String selectedRider, int selectedIndex)
    {

//        sp.clearRiderParameters(selectedRider, selectedIndex);

    }

    public void deleteRule(long rulesPK) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.deleteRule(rulesPK);
    }

    public void deleteProductStructure(long productStructureId) throws Exception, EDITDeleteException
    {
        StorageManager sm = new StorageManager();

        sm.deleteProductStructure(productStructureId);
    }

    /**
     * Exports parameters to XML File
     * <p>
     * @param xmlPath  XML File Path
     * @param xmlFileName XML File Name
     * @param params  Parameters in DDGroupItem format
     */
//    public void exportToXML(String xmlPath, String xmlFileName, DDGroupItem ddGroupItem) throws Exception {

    //sm.exportToXML(xmlPath,xmlFileName, ddGroupItem);
//    }

    /**
     * Exports parameters to XML File
     * <p>  * @param xmlPath  XML File Path
     * @param xmlFileName XML File Name
     * @param params  Parameters in DDGroupItem format */

    public void exportToXML(String xmlPath, String xmlFileName, List ddGroupItem) throws Exception
    {

//sm.exportToXML(xmlPath,xmlFileName, ddGroupItem);
    }

    /**
     * Returns reference to ScriptProcessor
     * @return ScriptProcessor
     */
    public ScriptProcessor getScriptProcessor()
    {

//        return sp;

        return  null;
    }

    /**
     * Returns reference to StorageManager
     * <p>
     * @return StorageManager
     */
    public StorageManager getStorageManager()
    {

//        return sm;
        return null;
    }

    /**
     * Import a table from a comma delimitted file
     */
//    public void importTable(File file) throws Exception {
//
//        sm.importTable(file);
//    }

    /**
     * Import a table from a comma delimitted file
     * into Table key
     */
    public void importTableInTableKey(long tableDefId, File file)
            throws Exception
    {

        //sm.importTableInTableKey(tableDefId ,file);
    }

    /**
     * Saves the script
     * <p>
     * @param scriptId Script Id
     * @param script Script text
     */
    public long saveScript(long scriptId, 
                           String scriptName,
                           String scriptText,
                           String scriptType,
                           String scriptStatus,
                           String operator) throws Exception
    {
        StorageManager sm = new StorageManager();

        return sm.saveScript(scriptId, scriptName, scriptText, scriptType, scriptStatus, operator);
    }

    public void setDrivingCRStructure(long productStructurePK,
                                      long ruleId,
                                      String processName,
                                      String event,
                                      String eventType,
                                      String effectiveDate,
                                      String ruleName)
            throws Exception
    {

        RulesVO rulesVO;
//        pr.setProcessName(processName);
//        pr.setEvent(event);
//        pr.setEventType(eventType);
//        pr.setEffectiveDate(effectiveDate);
//        pr.setProductStructurePK(productStructurePK);

        if (ruleId == 0)
        {

//            rulesVO = pr.getBestMatchScriptId(ruleName);
        }
        else
        {

            rulesVO = DAOFactory.getRulesDAO().findByRuleId(ruleId)[0];
        }

//        long scriptId = rulesVO.getScriptFK();

//        sp.loadScript(scriptId);

//        ScriptVO scriptVO = DAOFactory.getScriptDAO().findScriptById(scriptId)[0];

//        String scriptName = scriptVO.getScriptName();

//        setScriptName(scriptName);
    }

//	public void clearParams() {
//
//    	sp.clearParams();
//	}

//    public double getCalculatedValue(String calcValueVOAsXML) throws Exception {
//    public double getCalculatedValue(CalcValueVO calcValueVO) throws Exception
//    {
//
//
//        return Math.random() * 10;

//        // ** Currently, this is just to test getting Unit Values.
//
//        // Initialize the state of sp
//        sp.reset();
//
//        CalcValueVO calcValueVO = (CalcValueVO) Util.unmarshalVO(CalcValueVO.class, calcValueVOAsXML);
//
//        // Get the best match rule
//        pr.setProductStructurePK(calcValueVO.getSegmentVO().getProductStructureFK());
//        pr.setEffectiveDate(calcValueVO.getEffectiveDate());
//        pr.setEvent(calcValueVO.getEventName());
//        pr.setEventType(calcValueVO.getEventTypeName());
//        pr.setProcessName(calcValueVO.getProcessName());
//        RulesVO rulesVO = pr.getBestMatchScriptId(calcValueVO.getRuleName());
//
//        // Set up the fund information for the fund counter
//        List funds = new ArrayList();
//        Map fundInfo = new HashMap();
//        fundInfo.put("fund", new DDNumber(new Long(calcValueVO.getSegmentVO().getAllocationVO(0).getFundFK())));
//        funds.add(fundInfo);
//
//        // Load ScriptProcessor
//        sp.setFinancialType("BASEPARM");
//        sp.addParamEntry("selectedindex", new DDNumber(1));
//        sp.addParamEntry(new DDNumber("productkey", calcValueVO.getSegmentVO().getProductStructureFK()));
//        sp.addParamEntry("funds",funds);
//        sp.addParamEntry(new DDDate("trxeffectivedate", new EDITDate(calcValueVO.getSegmentVO().getSegmentActivityHistoryVO(0).getEffectiveDate())));
//        sp.addParamEntry("fundcounter", new DDNumber(1));
//        sp.addParamEntry("transactionridertype", new DDString(calcValueVO.getSegmentVO().getOptionCode()));
//
////        ScriptVO scriptVO = DAOFactory.getScriptDAO().findByScriptPK(rulesVO.getScriptFK(), false, null)[0];
//
//        sp.loadScript(calcValueVO.getProcessName(), calcValueVO.getEventName(), calcValueVO.getEventTypeName(), calcValueVO.getSegmentVO().getProductStructureFK(), calcValueVO.getSegmentVO().getSegmentActivityHistoryVO(0).getEffectiveDate());
//        sp.setSelectedIndex(0);
//
//        sp.exec();
//
//        DDNumber ddNum = (DDNumber) sp.getOutputEntry("UnitValue1.0");
//
//        return ddNum.getDouble();
//    }

    public void setFinancialType(String value)
    {

//        sp.setFinancialType(value);
    }

    public Map getFinancialTypeData(String financialType, int selectedIndex)
    {

//        return sp.getFinancialTypeData(financialType, selectedIndex);

        return null;
    }

    public void setSelectedIndex(int value)
    {

//        sp.setSelectedIndex(value);
    }

    public VOObject[] getOutputVector() throws Exception
    {

//        return sp.getScriptOutput();

        return null;
    }

    public String getOutputEntry(String name)
    {

//        return sp.getOutputEntry(name);
        return null;
    }


    public boolean containsOutputKey(String name)
    {

//        return sp.containsOutputKey(name);

        return false;
    }

    public void copyTable(String tableId,
                          String newTableName,
                          String accessType) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.copyTable(tableId, newTableName, accessType);
    }

    public void copyScript(String newScriptName,
                           long oldScriptId) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.copyScript(newScriptName, oldScriptId);
    }

    public ScriptChainNodeWrapper getScriptCallChain(ScriptChainNodeWrapper parent,
                                                     boolean calledFromAnalyzer) throws Exception
    {

//		ScriptProcessor scriptProcessor = new ScriptProcessor(pr,sm);

        ScriptChainNodeWrapper wrapper = null;

        String scriptName = getScriptName();

        if (scriptName != null)
        {
            ScriptProcessor sp = null;

            try
            {
                sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();

                ScriptVO scriptVO = DAOFactory.getScriptDAO().findScriptByName(scriptName)[0];

                long scriptId = scriptVO.getScriptPK();

                wrapper = sp.loadScript(scriptId, parent, calledFromAnalyzer);
            }
            finally
            {
                if (sp != null) sp.close();
            }
        }

        return wrapper;
    }

    public RulesVO checkForBestMatch(String ruleName) throws Exception
    {

//        return pr.getBestMatchScriptId(ruleName);

        return null;
    }

//    public String processBatch(String batchVOAsXML) throws Exception {
//
//        BatchVO batchVO = (BatchVO) Util.unmarshalVO(BatchVO.class, batchVOAsXML);
//
//        return Util.marshalVO(new BatchProcessor().processBatch(batchVO));
//    }

//    public BatchVO processBatch(BatchVO batchVO) throws Exception{
//
//        return new BatchProcessor().processBatch(batchVO);
//    }

    public QuoteVO processQuote(QuoteVO quoteVO) throws Exception
    {

//        QuoteParameters parameterProcessor = new QuoteParameters();
//
//        parameterProcessor.loadParamters(quoteVO, sp);
//
//        QuoteVO resultsQuoteVO = new QuoteProcessor().processQuote(quoteVO, sp);
//
//        return resultsQuoteVO;
        return null;
    }

    public void setProcessName(String processName)
    {

//        pr.setProcessName(processName);
    }

    public void setEvent(String event)
    {

//        pr.setEvent(event);
    }

    public void setEventType(String eventType)
    {

//        pr.setEventType(eventType);
    }

    public void setEffectiveDate(String effectiveDate)
    {

//        pr.setEffectiveDate(effectiveDate);
    }

    public void setDrivingCSId(long drivingCSId)
    {

//        pr.setProductStructurePK(drivingCSId);
    }

//    public String processReserves(ReservesBatchVO reservesBatchVO) throws Exception {
//
//        return new ReservesProcessor().processReserves(reservesBatchVO);
//    }

//	public void addParamEntry(String name, String data) throws Exception {
//
//		sp.addParamEntry(name,data);
//	}

//    public long createAreaStructureVO(String areaStructureVOAsXML) throws Exception {
//
//        AreaStructureVO areaStructureVO = (AreaStructureVO)
//                Util.unmarshalVO(AreaStructureVO.class, areaStructureVOAsXML);
//
//        return sm.createAreaStructureVO(areaStructureVO);
//    }

//    public long createAreaStructureVO(AreaStructureVO areaStructureVO) throws Exception
//    {
//
//        return sm.createAreaStructureVO(areaStructureVO);
//    }
//
//
//    public int deleteAreaStructure(long areaStructurePK) throws DeletionException, Exception
//    {
//
//        return sm.deleteAreaStructure(areaStructurePK);
//    }
//
//    public long attachAreaStructureToProduct(long productStructurePK,
//                                             long areaStructurePK) throws Exception
//    {
//
//        return sm.attachAreaStructureToProduct(productStructurePK, areaStructurePK);
//    }
//
//    public long detachAreaStructureFromProduct(long productStructurePK,
//                                               long areaStructurePK) throws Exception
//    {
//
//        return sm.detachAreaStructureFromProduct(productStructurePK, areaStructurePK);
//    }

    public String updateUnitValuesFromImport(String[] fileContents) throws Exception
    {
        StorageManager sm = new StorageManager();

        return sm.updateUnitValuesFromImport(fileContents);
    }

    public String updateRateTablesFromImport(String[] fileContents, String fileName, String operator) throws Exception
    {
        StorageManager sm = new StorageManager();

        return sm.updateRateTablesFromImport(fileContents, fileName, operator);
    }

    public void attachFilteredFundToProduct(long productStructurePK,
                                            long[] filteredFundPK) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.attachFilteredFundToProduct(productStructurePK, filteredFundPK);
    }

    public void cloneProductStructure(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.cloneProductStructure(cloneFromProductStructurePK, cloneToProductStructurePK);
    }

    public void saveRule(RulesVO rulesVO) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.saveRule(rulesVO);
    }

    public long saveProductStructure(ProductStructureVO productStructureVO) throws Exception
    {
        StorageManager sm = new StorageManager();

        return sm.saveProductStructure(productStructureVO);
    }

    public void detachFilteredFundFromProduct(long productStructurePK,
                                              long[] filteredFundPK) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.detachFilteredFundFromProduct(productStructurePK, filteredFundPK);
    }

    public void detachRulesFromProductStructure(long productStructurePK, long[] rulesPKs) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.detachRulesFromProductStructure(productStructurePK, rulesPKs);
    }

    public void attachRulesToProductStructure(long productStructurePK, long[] rulesPKs) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.attachRulesToProductStructure(productStructurePK, rulesPKs);
    }

    //This is temporary until prase has xml
    public void setAnalyzerMode(boolean analyzerMode) throws Exception
    {

//        sp.setAnalyzerMode(analyzerMode);
    }

    public int deleteTable(long tableDefPK, long tableKeysPK) throws Exception, EDITDeleteException
    {
        StorageManager sm = new StorageManager();

        return sm.deleteTable(tableDefPK, tableKeysPK);
    }

    public int deleteScript(long scriptPK) throws Exception, EDITDeleteException
    {
        StorageManager sm = new StorageManager();

        return sm.deleteScript(scriptPK);
    }

    public long saveTable(TableDefVO tableDefVO, TableKeysVO tableKeysVO, RateTableVO[] rateTableVOs) throws Exception
    {
        StorageManager sm = new StorageManager();

        return sm.saveTable(tableDefVO, tableKeysVO, rateTableVOs);
    }

    /**
     * Runs appropriate scripts agains an objectified XML Document. The set if applicable scripts is determined by the
     * supplied business parameters.
     * @param voDocument
     * @param processName
     * @param event
     * @param eventType
     * @param effectiveDate
     * @param productKey
     * @param abortOnHardValidationError
     * @return
     * @throws SPException
     */
    public SPOutput processScript(String rootElementName, VOObject voDocument, String processName, String event,
                                String eventType, String effectiveDate,
                                long productKey, boolean abortOnHardValidationError) throws SPException, RuntimeException
    {
        ScriptProcessor sp = null;

        SPOutput spOutput = null;

        try
        {
            sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();

            sp.loadDocument(rootElementName, voDocument);

            sp.loadScript(processName, event, eventType, productKey, effectiveDate);

            sp.setNoEditingException(false);

            sp.setAbortOnHardValidationError(abortOnHardValidationError);

            sp.exec();
        }
        catch (SPException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            spOutput = sp.getSPOutput();

            ValidationVO[] validationVOs = spOutput.getSPOutputVO().getValidationVO();

            e.setValidationVO(validationVOs);

            throw e;
        }
        catch (RuntimeException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            throw e;
        }
        finally
        {
            spOutput = sp.getSPOutput();

            if (sp != null) sp.close();
        }

        return spOutput;
    }

    public SPOutput processScriptWithDocument(String rootElementName, Document voDocument, String processName, String event,
                                String eventType, String effectiveDate,
                                long productKey, boolean abortOnHardValidationError) throws SPException, RuntimeException
    {
        ScriptProcessor sp = null;

        SPOutput spOutput = null;

        boolean errored = false;

        try
        {
            sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();

            sp.loadDocument(rootElementName, voDocument);

            sp.loadScript(processName, event, eventType, productKey, effectiveDate);

            sp.setNoEditingException(false);

            sp.setAbortOnHardValidationError(abortOnHardValidationError);

            sp.exec();
        }
        catch (SPException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            spOutput = sp.getSPOutput();

            ValidationVO[] validationVOs = spOutput.getSPOutputVO().getValidationVO();

            e.setValidationVO(validationVOs);

            errored = true;

            throw e;
        }
        catch (RuntimeException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            errored = true;

            throw e;
        }
        finally
        {
            spOutput = sp.getSPOutput();

//            spOutputVO.setErrored(errored);

            if (sp != null) sp.close();
        }

        return spOutput;
    }


    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.ENGINE_POOL, false, null);
    }

    public void attachAreaValueToProductStructure(long areaValuePK, long productStructurePK) throws EDITEngineException
    {
        ProductStructure productStructure = new ProductStructure(productStructurePK);

        AreaValue areaValue = new AreaValue(areaValuePK);

        FilteredAreaValue filteredAreaValue = new FilteredAreaValue(productStructure, areaValue);

        filteredAreaValue.save();
    }

    public AreaVO savePrimaryArea(AreaVO areaVO) throws Exception
    {
//        Area area = new Area(areaVO);
//
//        area.saveAsPrimary();
//
//        return (AreaVO) area.getVO();

        return null;
    }

    public FilteredAreaVO updateFilteredArea(long filteredAreaPK, long filteredFundPK, long renewalToFundPK, AreaVO areaVO) throws Exception
    {
//        AreaController areaController = new AreaController();
//
//        FilteredArea filteredArea = new FilteredArea(filteredAreaPK);
//
//        FilteredFund filteredFund = (filteredFundPK != 0)?new FilteredFund(filteredFundPK):null;
//
//        FilteredFund renewalToFund = (renewalToFundPK != 0)?new FilteredFund(renewalToFundPK):null;
//
//        Area area = new Area(areaVO);
//
//        areaController.updateFilteredArea(filteredArea, filteredFund, renewalToFund, area);
//
//        return (FilteredAreaVO) filteredArea.getVO();

        return null;
    }

    public void deleteArea(long areaPK) throws Exception
    {
//        new Area(areaPK).delete();
    }

    public void cloneFilteredAreas(long fromProductStructurePK, long toProductStructurePK) throws Exception
    {
//        ProductStructure fromProductStructure = new ProductStructure(fromProductStructurePK);
//
//        ProductStructure toProductStructure = new ProductStructure(toProductStructurePK);
//
//        AreaController areaController = new AreaController();
//
//        areaController.cloneFilteredAreas(fromProductStructure, toProductStructure);
    }

    public void detachAreaFromProductStructure(long filteredAreaPK) throws Exception
    {
//        FilteredArea filteredArea = new FilteredArea(filteredAreaPK);
//
//        filteredArea.delete();
    }

    public AreaVO getBestMatchAreaVO(long productStructurePK, String areaCT) throws Exception
    {
//        Area area = Area.findBestMatch(productStructurePK, areaCT);
//
//        return (AreaVO) area.getVO();

        return null;
    }

//    public List parseSegmentVOAsTextLines(SegmentVO segmentVO) throws Exception
//    {
//        ScriptProcessor sp = null;
//
//        List textLines = null;
//
//        try
//        {
//            sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();
//
//            sp.loadParameters(segmentVO);
//
//            textLines = sp.parseXML();
//        }
//        finally
//        {
//            if (sp != null) sp.close();
//        }
//
//        return textLines;
//    }

    public void saveVONonRecursive(Object valueObject) throws Exception
    {
        StorageManager sm = new StorageManager();

        sm.saveVONonRecursive(valueObject);
    }

    /**
     * @see Calculator#saveAreaValue(edit.common.vo.AreaValueVO)     @param areaValueVO

     */
    public void saveAreaValue(AreaValueVO areaValueVO) throws EDITEngineException
    {
        AreaValue newAreaValue = new AreaValue(areaValueVO);

        newAreaValue.save();
    }

    /**
     * @see Calculator#deleteAreaValue(long)
     * @param areaValuePK
     */
    public void deleteAreaValue(long areaValuePK) throws EDITEngineException
    {
        AreaValue areaValue = new AreaValue(areaValuePK);

        areaValue.delete();
    }

    /**
     * @see Calculator#detachAreaValueFromProductStructure(long, long)
     * @param areaValuePK
     * @param productStructurePK
     */
    public void detachAreaValueFromProductStructure(long areaValuePK, long productStructurePK) throws EDITEngineException
    {
        FilteredAreaValue filteredAreaValue = FilteredAreaValue.findBy_ProductStructurePK_AreaValuePK(productStructurePK, areaValuePK);

        if (filteredAreaValue == null)
        {
            throw new EDITEngineException("Association Does Not Exist For Specified Product Structure And Area Value");
        }

        filteredAreaValue.delete();
    }

    /**
     *
     * @param cloneFromProductStructurePK
     * @param cloneToProductStructurePK
     */
    public void cloneAreaValues(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws EDITEngineException
    {
        ProductStructure cloneFromProductStructure = new ProductStructure(cloneFromProductStructurePK);

        ProductStructure cloneToProductStructure = new ProductStructure(cloneToProductStructurePK);

        cloneFromProductStructure.cloneAreaValues(cloneToProductStructure);
    }

    /**
     * @see Calculator#addAreaKey(String, String)
     * @param grouping
     * @param field
     * @throws EDITEngineException
     */
    public void addAreaKey(String grouping, String field) throws EDITEngineException
    {
        AreaKey areaKey = new AreaKey(grouping, field);

        areaKey.save();
    }

    /**
     * @see Calculator#deleteAreaKey(long)
     * @param areaKeyPK
     * @throws EDITEngineException
     */
    public void deleteAreaKey(long areaKeyPK) throws EDITEngineException
    {
        AreaKey areaKey = new AreaKey(areaKeyPK);

        areaKey.delete();
    }

    public ElementLockVO lockElement(long elementPK, String username) throws EDITLockException
    {
        return new StorageManager().lockElement(elementPK, username);
    }

    public int unlockElement(long lockedElementPK)
    {
        return new StorageManager().unlockElement(lockedElementPK);
    }

    /**
     * @see engine.business.Calculator#saveFeeDescription(edit.common.vo.FeeDescriptionVO)
     */
    public void saveFeeDescription(FeeDescriptionVO feeDescriptionVO) throws EDITEngineException
    {
        FeeDescription feeDescription = new FeeDescription(feeDescriptionVO);

        feeDescription.save();
    }

    /**
     * @see engine.business.Calculator#deleteFeeDescription(long)
     */
    public void deleteFeeDescription(long feeDescriptionPK) throws EDITEngineException
    {
        FeeDescription feeDescription = FeeDescription.findByPK(feeDescriptionPK);

        feeDescription.delete();
    }

    /**
     * @see engine.business.Calculator#saveFee(edit.common.vo.FeeVO)
     */
    public void saveFee(FeeVO feeVO) throws EDITEngineException
    {
        Fee fee = new Fee(feeVO);

        fee.save();
    }

    /**
     * @see engine.business.Calculator#deleteFee(long)
     */
    public void deleteFee(long feePK) throws EDITEngineException
    {
        Fee fee = Fee.findByPK(feePK);

        fee.delete();
    }

    /**
     * @see engine.business.Calculator#reverseFee(long)
     */
    public void reverseFee(long feePK) throws EDITEngineException
    {
        Fee fee = Fee.findByPK(feePK);

        fee.reverse();
    }

    /**
     * @see engine.business.Calculator#createDFCASHFeeTransactionAndUpdateFees(edit.common.vo.FeeVO[], java.util.Map, long, long)
     */
    public void createDFCASHFeeTransactionAndUpdateFees(FeeVO[] feeVOs, Map feeAmountsMapByFeePK,
                                                        long filteredFundFK, long suspensePK)
                                                        throws EDITEngineException
    {
        FilteredFund filteredFund = FilteredFund.findByPK(filteredFundFK);

        Fee[] fees = (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOs, Fee.class);

        filteredFund.createDFCASHFeeTransaction(fees);

        Fee.updateFeesAndSuspense(feeAmountsMapByFeePK, suspensePK);
    }

    public ChargeCodeVO findChargeCodeBy_ChargeCodePK(long chargeCodePK)
    {
        ChargeCodeVO chargeCodeVO = null;

        ChargeCode chargeCode = ChargeCode.findByPK(chargeCodePK);

        if (chargeCode != null)
        {
            chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
        }

        return chargeCodeVO;
    }

    public ChargeCodeVO[] findChargeCodeBy_FilteredFundPK(long filteredFundPK)
    {
        ChargeCode[] chargeCodes = ChargeCode.findByFilteredFundPK(filteredFundPK);

        return (ChargeCodeVO[]) CRUDEntityImpl.mapEntityToVO(chargeCodes, ChargeCodeVO.class);
    }

    /**
     * @see engine.business.Calculator#saveChargeCode(edit.common.vo.ChargeCodeVO)
     */
    public void saveChargeCode(ChargeCodeVO chargeCodeVO) throws EDITEngineException
    {
        ChargeCode chargeCode = new ChargeCode(chargeCodeVO);

        chargeCode.save();
    }

    /**
     * @see engine.business.Calculator#deleteChargeCode(long)
     */
    public void deleteChargeCode(long chargeCodePK) throws EDITEngineException
    {
        ChargeCode chargeCode = ChargeCode.findByPK(chargeCodePK);

        chargeCode.delete();
    }

    public void saveChangeProductStructure(ProductStructureVO productStructureVO)
    {
        ProductStructure productStructure = new ProductStructure(productStructureVO);

        try
        {
            productStructure.save();
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  
        }
    }

    public void copySelectedScripts(String[] keys, String operator, String fileName) throws Exception
    {
        getAndSetExportFile(fileName);

        SessionHelper.beginTransaction(SessionHelper.MISCELLANEOUS);

        for (int i = 0; i < keys.length; i++)
        {
            Long scriptPK = new Long(keys[i]);
            Script script = Script.findByPKWithScriptLine(scriptPK);

            Script newScript = (Script)SessionHelper.newInstance(Script.class, SessionHelper.MISCELLANEOUS);
            newScript.setOperator(operator);
            newScript.setScriptName(script.getScriptName());
            newScript.setScriptStatusCT(script.getScriptStatusCT());
            newScript.setScriptTypeCT(script.getScriptTypeCT());
            newScript.setMaintDateTime(new EDITDateTime());
            Set scriptLines = script.getScriptLines();

            for (Iterator iterator = scriptLines.iterator(); iterator.hasNext();)
            {
                ScriptLine scriptLine = (ScriptLine) iterator.next();

                ScriptLine newScriptLine = new ScriptLine();
                newScriptLine.setLineNumber(scriptLine.getLineNumber());
                newScriptLine.setScriptLine(scriptLine.getScriptLine());
                newScript.add(newScriptLine);

            }

            SessionHelper.saveOrUpdate(newScript, SessionHelper.MISCELLANEOUS);
        }

        SessionHelper.commitTransaction(SessionHelper.MISCELLANEOUS);
        SessionHelper.closeSession(SessionHelper.MISCELLANEOUS);
        SessionHelper.clearSessions();
    }

    public long saveCompany(String companyName, String policyNumberPrefix, String policyNumberSuffix, int policyNumberSequenceNumber, 
    		int policySequenceLength, String billingCompanyNumber)
    {
        Company company = Company.findBy_CompanyName(companyName);

        if (company == null)
        {
            company = (Company) SessionHelper.newInstance(Company.class, SessionHelper.ENGINE);
            company.setCompanyName(companyName);
        }

        company.setPolicyNumberPrefix(policyNumberPrefix);
        company.setPolicyNumberSuffix(policyNumberSuffix);
        company.setPolicyNumberSequenceNumber(policyNumberSequenceNumber);
        company.setPolicySequenceLength(policySequenceLength);
        company.setBillingCompanyNumber(billingCompanyNumber);

        SessionHelper.beginTransaction(SessionHelper.ENGINE);
        company.hSave();
        SessionHelper.commitTransaction(SessionHelper.ENGINE);

        return company.getCompanyPK().longValue();
    }



    /**
     *
     * @param fileName
     * @throws Exception
     */
    private void getAndSetExportFile(String fileName) throws Exception
    {
    	/** It looks like SEG was obtaining the PRASE hibernate instance and 
    	renaming it to Miscellaneous, so that it would be run in a separate 
    	ThreadLocal instance. It's possible that this flow is process 
    	intensive. But it doesn't look like it's called anywhere. I've moved 
    	the EditServicesConfig.JDBC elements to the container so for now I'll
    	keep this here until I know there is no flow that uses this. */
//        EDITService editService = null;
//
//        ServicesConfig.setEditServicesConfig(fileName);
//
//        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
//
//        EDITService[] editServices = editServicesConfig.getEDITService();
//
//        List editServiceList = new ArrayList();
//
//        for (int i = 0; i < editServices.length; i++)
//        {
//            editService = editServices[i];
//            if (editService.getServiceName().equalsIgnoreCase("PRASE"))
//            {
//                editService.setServiceName("Miscellaneous");
//                editServiceList.add(editService);
//                break;
//            }
//        }
//
//
//        DBSessionFactory.getInstance((EDITService[]) editServiceList.toArray(new EDITService[editServiceList.size()]));
    }

    /**
     * Finds the InterestRates from the specified parameters.
     * The InterestRates
     */
    public List getInterestRates(String companyName, String marketingPackageName, String groupProductName, String areaName,
                                 String businessContractName, String fundNumber, String interestRateDate,
                                 String lastValuationDate, String trxEffectiveDate, String optionCT)
    {
        List results = null;

        Map wsEntries = null;
        List wsVector = null;

        try
        {
            ScriptProcessor sp = new ScriptProcessorImpl();

            String filteredFundPK = getFilteredFundPK(fundNumber, companyName, marketingPackageName, groupProductName, areaName, businessContractName);

            // these are defined in the Getinterest instruction.
            sp.addWSEntry("FilteredFundId", filteredFundPK);
            sp.addWSEntry("IntRateDate", interestRateDate);
            sp.addWSEntry("LastValuationDate", lastValuationDate);
            sp.addWSEntry("TrxEffectiveDate", trxEffectiveDate);
            sp.addWSEntry("Option", optionCT);

            Getinterestrate getInterestRate = new Getinterestrate();

            getInterestRate.exec(sp);

            wsEntries = sp.getWS();

            wsVector =  (List) sp.getWSVector(Getinterestrate.RATE_DATE_DATA);

            results = new ArrayList();

            results.add(wsEntries);

            results.addAll(wsVector);

        }
        catch (SPException ex)
        {
          System.out.println(ex);

            ex.printStackTrace();
        }

        return results;
    }

    /**
     * Finds the filteredFundPK by the fundNumber and the product structure names
     *
     * @param fundNumber
     * @param companyName
     * @param marketingPackageName
     * @param groupProductName
     * @param areaName
     * @param businessContractName
     * @return
     */
    private String getFilteredFundPK(String fundNumber, String companyName, String marketingPackageName,
                                     String groupProductName, String areaName, String businessContractName)
    {
        FilteredFund filteredFund = FilteredFund.findByFundNumber_And_ProductStructureNames(
                fundNumber, companyName, marketingPackageName, groupProductName, areaName, businessContractName);

        return filteredFund.getFilteredFundPK() + "";
    }

	/**
   	 * @see Calculator#saveFilteredFund(FilteredFundVO)
   	 * @param filteredFundVO
   	 */
  	public long saveFilteredFund(FilteredFundVO filteredFundVO) throws Exception
  	{
    	CSCache.getCSCache().clearCSCache();
  
	    return createOrUpdateVONonRecursive(filteredFundVO);
	}

  /**
   * Prepares PRASE by placing the specified root document into the root document
   * cache to be identified with the specified RootDocumentPK.
   * Processing it then continues with the expectation that the script writer
   * will activate the root document.
   * @see engine.business.Calculator#processScriptWithDocument(..)
   * @param rootPraseDocBuilder
   * @param rootDocumentPK
   * @param processName
   * @param event
   * @param eventType
   * @param effectiveDate
   * @param productKey
   * @param abortOnHardValidationError
   * @return
   */
  public SPOutput processScriptWithDocument(PRASEDocBuilder praseDocBuilder, String processName, String event, String eventType, String effectiveDate, long productKey, boolean abortOnHardValidationError) throws SPException
  {
    ScriptProcessor sp = null;

    SPOutput spOutput = null;

    boolean errored = false;

    try
    {
        sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();

        sp.loadRootDocument(praseDocBuilder);

        sp.loadScript(processName, event, eventType, productKey, effectiveDate);

        sp.setNoEditingException(false);

        sp.setAbortOnHardValidationError(abortOnHardValidationError);

        sp.exec();
    }
    catch (SPException e)
    {
    	if (!e.isLogged()) 
    	{
	        Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
	
	        Log.logGeneralExceptionToDatabase(null, e);     
	        
	        e.setLogged(true);
    	}

        spOutput = sp.getSPOutput();

        ValidationVO[] validationVOs = spOutput.getSPOutputVO().getValidationVO();

        e.setValidationVO(validationVOs);

        errored = true;

        throw e;
    }
    catch (RuntimeException e)
    {
        Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

        Log.logGeneralExceptionToDatabase(null, e);

        errored = true;

        throw e;
    }
    finally
    {
        spOutput = sp.getSPOutput();

    //            spOutputVO.setErrored(errored);

        if (sp != null) sp.close();
    }

    return spOutput;
  }

    /**
     * @see super#processScript(String, Document, List<Document>, Document, Document, String, String, String, EDITDate, Long, boolean)
     * @param rootElementName
     * @param segmentDocument
     * @param clientDocuments
     * @param riderDocument
     * @param contractGroupPK
     * @param process
     * @param event
     * @param eventType
     * @param effectiveDate
     * @param productStructurePK
     * @param abortOnHardValidationError
     * @return
     */
    public SPOutput processScript(String rootElementName, Element segmentVOElement, List<Element> clientVOElements, List<Element> riderVOElements, Long contractGroupPK, String process, String event, String eventType, EDITDate effectiveDate, Long productStructurePK, boolean abortOnHardValidationError) throws SPException
    {
        ScriptProcessor sp = null;
        
        SPOutput spOutput = null;
        
        try
        {
            sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();
        
            // SegmentDocument
            SegmentDocument segmentDocumentBuilder = new SegmentDocument(segmentVOElement);
            
            sp.loadRootDocument(segmentDocumentBuilder);
            
            // ClientDocuments
            for (Element clientVOElement:clientVOElements)
            {
                ClientDocument clientDocumentBuilder = new ClientDocument(clientVOElement);

                sp.loadDocument(clientDocumentBuilder);
            }
            
            // RiderDocument
            RiderDocument riderDocumentBuilder = new RiderDocument(riderVOElements);
            
            sp.loadDocument(riderDocumentBuilder);
            
            // GroupDocument
            GroupDocument groupDocumentBuilder = new GroupDocument(contractGroupPK);
            
            sp.loadDocument(groupDocumentBuilder);
            
            // Execute
            sp.loadScript(process, event, eventType, productStructurePK, effectiveDate.getFormattedDate());

            sp.setNoEditingException(false);

            sp.setAbortOnHardValidationError(abortOnHardValidationError);

            sp.exec();            
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase("New Business Quote failed to process.", e);

            throw new SPException("New Business Quote failed to process.", SPException.SCRIPT_LOADING_ERROR, e);
        }
        finally
        {
            spOutput = sp.getSPOutput();

            if (sp != null) sp.close();
        }
    
        return spOutput;
    }

    /**
     *
     * @param rootElementName
     * @param quoteVO
     * @param processName
     * @param event
     * @param eventType
     * @param effectiveDate
     * @param productKey
     * @param abortOnHardValidationError
     * @return
     * @throws SPException
     * @throws RuntimeException
     */
    public SPOutput processScriptForNBValidation(String rootElementName, QuoteVO quoteVO, String processName, String event,
                                                  String eventType, String effectiveDate, long productKey, boolean abortOnHardValidationError)
                                                    throws SPException, RuntimeException
    {
        ScriptProcessor sp = null;

        SPOutput spOutput = null;

        try
        {
            sp = ScriptProcessorFactory.getSingleton().getScriptProcessor();

            // CommissionDocument
            CommissionDocument commissionDocumentBuilder = new CommissionDocument(quoteVO.getSegmentVO()[0]);

            sp.loadDocument(commissionDocumentBuilder);

            Map<String, String> productMap = new HashMap<>();
            productMap.put("ProductStructurePK", productKey + "");
            ProductStructureDocument psd = new ProductStructureDocument(productMap);
            
            sp.loadDocument(psd);
            sp.loadDocument(rootElementName, quoteVO);

            // Execute
            sp.loadScript(processName, event, eventType, productKey, effectiveDate);

            sp.setNoEditingException(false);

            sp.setAbortOnHardValidationError(abortOnHardValidationError);

            sp.exec();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase("New Business Quote failed to process.", e);

            throw new SPException("New Business Quote failed to process.", SPException.SCRIPT_LOADING_ERROR, e);
        }
        finally
        {
            spOutput = sp.getSPOutput();

            if (sp != null) sp.close();
        }

        return spOutput;
    }

    public void runPRASETest(Long praseTestPK) throws SPException
    {
        PRASETest praseTest = (PRASETest) SessionHelper.get(PRASETest.class, praseTestPK, SessionHelper.ENGINE);            
        
        praseTest.run();
    }

    /**
     * @see engine.business.Calculator#getSPRecordedResults()
     */
    public SPRecordedResults getSPRecordedResults()
    {
        return SPRecordedResults.getSingleton();
    }

    /**
     * @see engine.business.Calculator#exportSPRecordedRunData(String, String)
     */
    public void exportSPRecordedRunData(String operator, String runInformation) throws Exception
    {
        SPRecordedResults spRecordedResults = SPRecordedResults.getSingleton();

        spRecordedResults.exportRunData(operator, runInformation);
    }

    /**
     * @see engine.business.Calculator#clearSPRecordedOperator(String)
     */
    public SPRecordedOperator clearSPRecordedOperator(String operator)
    {
        SPRecordedResults spRecordedResults = SPRecordedResults.getSingleton();

        return spRecordedResults.clearSPRecordedOperator(operator);
    }

    /**
     * @see Calculator#exportScriptsToXML(String[])
     */
    public void exportScriptsToXML(String[] scriptPKs) throws EDITException, IOException
    {
        String editExportName = "ExportDirectory1";

        EDITExport editExport = ServicesConfig.getEDITExport(editExportName);

        String exportDirectoryName = editExport.getDirectory();

        if (!UtilFile.directoryExists(exportDirectoryName))
        {
            throw new EDITException("Export Directory " + exportDirectoryName + " for Config Name " + editExportName + " Does Not Exist");
        }

        for (int i = 0; i < scriptPKs.length; i++)
        {
            Long scriptPK = new Long(scriptPKs[i]);

            Script script = Script.findByPKWithScriptLine(scriptPK);

            String fileName = exportDirectoryName + "\\" + script.getScriptName() + ".xml";

            if (UtilFile.fileExists(fileName))
            {
                throw new EDITException("A Filename of " + fileName + " Already Exists");
            }

            Element scriptElement = script.getAsElement();

            Set scriptLines = script.getScriptLines();

            for (Iterator iterator = scriptLines.iterator(); iterator.hasNext();)
            {
                ScriptLine scriptLine = (ScriptLine) iterator.next();

                Element scriptLineElement = scriptLine.getAsElement();

                scriptElement.add(scriptLineElement);
            }

            Document document = new DefaultDocument(script.getScriptName());

            document.setRootElement(scriptElement);

            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

            out.write(document.asXML());
            out.close();
        }
    }
}
