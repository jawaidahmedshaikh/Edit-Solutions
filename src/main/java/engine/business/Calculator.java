/*
 * User: unknown
 * Date: Jun 1, 2000
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.business;

import edit.common.EDITDate;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITEngineException;
import edit.common.exceptions.EDITLockException;
import edit.common.exceptions.EDITException;
import edit.common.vo.AreaVO;
import edit.common.vo.AreaValueVO;
import edit.common.vo.ChargeCodeVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.ElementLockVO;
import edit.common.vo.FeeDescriptionVO;
import edit.common.vo.FeeVO;
import edit.common.vo.FilteredAreaVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.QuoteVO;
import edit.common.vo.RateTableVO;
import edit.common.vo.RulesVO;
import edit.common.vo.TableDefVO;
import edit.common.vo.TableKeysVO;
import edit.common.vo.VOObject;

import edit.services.component.ICRUD;

import engine.dm.StorageManager;

import engine.sp.*;

import engine.sp.custom.document.PRASEDocBuilder;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;


/**
 * The Engine request controller
 */
public interface Calculator extends ICRUD {

    public void clearScriptProcessor();
    public void setScriptName(String scriptName);
    public String getScriptName();
//    public void loadScript(String loadScript) throws Exception;
    public void setScriptLoaded(boolean scriptLoaded);
    public List getSCParams();
    public void resetScriptProcessor();
//    public void execScriptProcessor() throws Exception;



    public long saveProductStructure(String newProductStructure) throws Exception;

//    public long saveRuleStructure(String newRuleStructure,String newRuleIdStructure, String username) throws Exception;

    public void loadScript(long scriptId) throws Exception;

    public String[] getScriptLines();

//    public ScriptChainNodeWrapper getScriptCallChain(ScriptChainNodeWrapper parent,
//                                                      boolean calledFromAnalyzer)throws Exception;

    public RulesVO checkForBestMatch(String ruleName) throws Exception;

//    public String processBatch(String batchVO) throws Exception;

//    public BatchVO processBatch(BatchVO batchVO) throws Exception;

    public QuoteVO processQuote(QuoteVO quoteVO) throws Exception;

    public void setProcessName(String processName) throws Exception;

    public void setEvent(String event) throws Exception;

    public void setEventType(String eventType) throws Exception;

    public void setEffectiveDate(String effectiveDate) throws Exception;

    public void setDrivingCSId(long drivingCSId) throws Exception;

//    public String processReserves(ReservesBatchVO reservesBatchVO) throws Exception;

    public void deleteRule(long ruleId) throws Exception;

    public void deleteProductStructure(long productStructureId) throws Exception, EDITDeleteException;

    /**
     * Exports parameters to XML File
     * <p>
     * @param xmlPath  XML File Path
     * @param xmlFileName XML File Name
     * @param params  Parameters in DDGroupItem format
     */
//    public void exportToXML(String xmlPath, String xmlFileName, DDGroupItem ddGroupItem) throws Exception ;

    /**
     * Exports parameters to XML File
     * <p>  * @param xmlPath  XML File Path
     * @param xmlFileName XML File Name
     * @param params  Parameters in DDGroupItem format
     */
    public void exportToXML(String xmlPath, String xmlFileName, List ddGroupItem) throws Exception ;

    /**
     * Returns reference to ScriptProcessor
     * @return ScriptProcessor
     */
    public ScriptProcessor getScriptProcessor() ;

    /**
     * Returns reference to StorageManager
     * <p>
     * @return StorageManager
     */
    public StorageManager getStorageManager() ;

    /**
     * Import a table from a comma delimitted file
     */
//    public void importTable(File file) throws Exception ;

    /**
     * Import a table from a comma delimitted file
     * into Table key
     */
    public void importTableInTableKey(long tableDefId ,File file) throws Exception ;

    /**
     * Copies ans saves the TableKey Row in TableKey table
     * and also its associated RateTable
     */
    /*public void copyTableKeyRow(String tableId    ,String tableName ,String sex  ,String class_cd
    ,String bandAmount ,String month     ,String day  ,String year
    ,String userKey    ,String area      ,String type ,String tableKeyId) throws Exception;*/

    /**
     * Saves the script
     * <p>
     * @param scriptId Script Id
     * @param script Script text
     */
    public long saveScript(long scriptId, String scriptName , String script, String scriptType, String scriptStatus, String operator) throws Exception ;

    public void setSPFinancialType(String newValue);

    public void setSPSelectedIndex(int newValue);

//    public void addParamEntry(DD data);

//    public void addParamEntry(String name, Map hashtable);

//    public void addParamEntry(String name, List vector);

    public void removeRider(int index);

    public Map getParamOfSelectedRider(String selectedRider ,int index) throws Exception;

//    public void clearParams();

    public void setFinancialType(String value);

    public void setSelectedIndex(int value);

    public Map getFinancialTypeData(String  financialType, int selectedIndex);

    public void clearRiderParameters(String selectedRider, int selectedIndex);

    public VOObject[] getOutputVector() throws Exception;

    public String getOutputEntry(String name);

    public boolean containsOutputKey(String name);

    public void copyTable(String tableId,String newTableName,String accessType)throws Exception;

    public void copyScript(String newScriptName,  long oldScriptId)throws Exception;

    public void setDrivingCRStructure(long productStructureId,
                                      long ruleId,
                                      String processName,
                                      String event,
                                      String eventType,
                                      String effectiveDate,
                                      String ruleName) throws Exception;

//    public void addParamEntry(String name, String data) throws Exception;

    public long saveVO (Object valueObject) throws Exception;

    public boolean hasFilteredFundHasUnitValueForPriorOrCurrentMonth(Long filteredFundFK, Long chargeCodeFK, EDITDate date);

    public long createOrUpdateVONonRecursive(Object valueObject) throws Exception;

    public int deleteVO(Class voName, long primaryKey, boolean recursively) throws Exception;

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception;

//    public double getCalculatedValue(String calcValueVOAsXML) throws Exception;
//    public double getCalculatedValue(CalcValueVO calcValueVO) throws Exception;

//    public long createAreaStructureVO(String areaStructureVOAsXML) throws Exception;
//    public long createAreaStructureVO(AreaStructureVO areaStructureVO) throws Exception;
//
//    public int deleteAreaStructure(long areaStructurePK) throws DeletionException, Exception;
//
//    public long attachAreaStructureToProduct(long productStructurePK, long areaStructurePK) throws Exception;
//
//    public long detachAreaStructureFromProduct(long productStructurePK, long areaStructurePK) throws Exception;

    public String updateUnitValuesFromImport(String[] fileContents) throws Exception;

    public String updateRateTablesFromImport(String[] fileContents, String fileName, String operator) throws Exception;

    public void attachFilteredFundToProduct(long productStructurePK, long[] filteredFundPK) throws Exception;

    public void detachFilteredFundFromProduct(long productStructurePK, long[] filteredFundPK) throws Exception;

    public void attachRulesToProductStructure(long productStructurePK, long[] rulesPKs) throws Exception;

    public void detachRulesFromProductStructure(long productStructurePK, long[] rulesPKs) throws Exception;

    public void cloneProductStructure(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws Exception;

    public void saveRule(RulesVO rulesVO) throws Exception;

    public long saveProductStructure(ProductStructureVO productStructureVO) throws Exception;

    //This is temporary until prase has xml
    public void setAnalyzerMode(boolean analyzerMode) throws Exception;

    public int deleteTable(long tableDefPK, long tableKeysPK) throws Exception, EDITDeleteException;

    public int deleteScript(long scriptPK) throws Exception, EDITDeleteException;

    public long saveTable(TableDefVO tableDefVO, TableKeysVO tableKeysVO, RateTableVO[] rateTableVOs) throws Exception;

    public SPOutput processScript(String rootElementName, VOObject voDocument, String processName, String event,
                                          String eventType, String effectiveDate,
                                          long productKey, boolean abortOnHardValidationError) throws SPException, RuntimeException;

    public SPOutput processScriptWithDocument(String rootElementName, Document voDocument, String processName, String event,
                                          String eventType, String effectiveDate,
                                          long productKey, boolean abortOnHardValidationError) throws SPException, RuntimeException;
                                          

    /**
     * Under normal running, the documents that are going to be built during script processing are built
     * realtime while the script is running. The documents that are built are constructed off of the database.
     * Othertimes, however, the data that makes up the documents does not yet exist in the database. In this
     * situation, the caller supplies the documents themselves.
     * @param rootElementName
     * @param segmentVOElement
     * @param clientVOElements
     * @param riderVOElements
     * @param contractGroupPK
     * @param process
     * @param event
     * @param eventType
     * @param effectiveDate
     * @param productStructurePK
     * @param abortOnHardValidationError
     * @return
     */
    public SPOutput processScript(String rootElementName, 
                                Element segmentVOElement, 
                                List<Element> clientVOElements, 
                                List<Element> riderVOElements, 
                                Long contractGroupPK, 
                                String process, 
                                String event, 
                                String eventType, 
                                EDITDate effectiveDate, 
                                Long productStructurePK,
                                boolean abortOnHardValidationError) throws SPException;                                      

  /**
   * PRASE allows for activation of individual Documents instead of having to load in the entire
   * Document at one time. To accomplish this, it is necessary to specify the [driving] document so that
   * the initial document can be identified. In the case of this method, such a root document is specified
   * with the rootPRASEDocKey. The Document will not be built twice, but placed and then retrieved from 
   * the document cache.
   * @param praseDocBuilder
   * @param processName
   * @param event
   * @param eventType
   * @param effectiveDate
   * @param productKey
   * @param abortOnHardValidationError
   * @return
   * @throws SPException
   */
  public SPOutput processScriptWithDocument(PRASEDocBuilder praseDocBuilder, String processName, String event,
                                        String eventType, String effectiveDate,
                                        long productKey, boolean abortOnHardValidationError) throws SPException;

    public void attachAreaValueToProductStructure(long areaValuePK, long productStructurePK) throws Exception;

    public AreaVO savePrimaryArea(AreaVO areaVO) throws Exception;

    public void deleteArea(long areaPK) throws Exception;

    public void cloneFilteredAreas(long fromProductStructurePK, long toProductStructurePK) throws Exception;

    public void detachAreaFromProductStructure(long filteredAreaPK) throws Exception;

    public FilteredAreaVO updateFilteredArea(long filteredAreaPK, long filteredFundPK, long renewalToFundPK, AreaVO areaVO) throws Exception;

    public AreaVO getBestMatchAreaVO(long productStructurePK, String areaCT) throws Exception;

//    public List parseSegmentVOAsTextLines(SegmentVO segmentVO) throws Exception;

    public void saveVONonRecursive(Object valueObject) throws Exception;

    /**
     * Adds a new AreaKey.
     * @see engine.AreaKey
     * @param grouping
     * @param field
     * @throws EDITEngineException
     */
    public void addAreaKey(String grouping, String field) throws EDITEngineException;

    /**
     * Deletes the AreaKey and its associated AreaValues.
     * @param areaKeyPK
     * @throws EDITEngineException
     * @see engine.AreaKey#delete()
     */
    public void deleteAreaKey(long areaKeyPK) throws EDITEngineException;

    /**
     * Adds a new AreaValue to the specified AreaKey.      @param areaValueVO

     */
    public void saveAreaValue(AreaValueVO areaValueVO) throws EDITEngineException;

    /**
     * Deletes the AreaValue.
     * @param areaValuePK
     * @see engine.AreaValue#delete()
     */
    public void deleteAreaValue(long areaValuePK) throws EDITEngineException;

    /**
     * Removes the association between the supplied AreaValue and ProductStructure.
     * @param areaValuePK
     * @param productStructurePK
     */
    public void detachAreaValueFromProductStructure(long areaValuePK, long productStructurePK) throws EDITEngineException;

    /**
     * Clones the set of AreaValues from the clone-from ProductStructure to the clone-to ProductStructure.
     * @see engine.ProductStructure#cloneAreaValues(engine.ProductStructure)
     * @param cloneFromProductStructurePK
     * @param cloneToProductStructurePK
     */
    public void cloneAreaValues(long cloneFromProductStructurePK, long cloneToProductStructurePK) throws EDITEngineException;

    public ElementLockVO lockElement(long elementPK, String username) throws EDITLockException;

    public int unlockElement(long lockedElementPK);

    /**
     * saves feeDescrtption.
     * @param feeDescriptionVO
     */
    public void saveFeeDescription(FeeDescriptionVO feeDescriptionVO) throws EDITEngineException;

    /**
     * deletes feeDescription by feeDescriptionPK.
     * @param feeDescriptionPK
     */
    public void deleteFeeDescription(long feeDescriptionPK) throws EDITEngineException;

    /**
     * saves fee.
     * @param feeVO
     */
    public void saveFee(FeeVO feeVO) throws EDITEngineException;

    /**
     * deletes fee by feePK.
     * @param feePK
     */
    public void deleteFee(long feePK) throws EDITEngineException; 

    /**
     * reverses fee
     * @param feePK
     */
    public void reverseFee(long feePK) throws EDITEngineException;

    /**
     * Creates DFCASH transactions and updates corresponding Fees and Suspense entries.
     * @param feeVOs
     * @param feeAmountsMapByFeePK
     * @param filteredFundFK
     * @param suspensePK
     * @throws EDITEngineException
     */
    public void createDFCASHFeeTransactionAndUpdateFees(FeeVO[] feeVOs, Map feeAmountsMapByFeePK,
                                                long filteredFundFK, long suspensePK)
                                                throws EDITEngineException;

    public ChargeCodeVO findChargeCodeBy_ChargeCodePK(long chargeCodePK);

    public ChargeCodeVO[] findChargeCodeBy_FilteredFundPK(long filteredFundPK);

    public void saveChargeCode(ChargeCodeVO chargeCodeVO) throws EDITEngineException;

    public void deleteChargeCode(long chargeCodePK) throws EDITEngineException;

    public void saveChangeProductStructure(ProductStructureVO productStructureVO);
    
    /**
     * Finds the InterestRates from the specified parameters.
     * The InterestRates 
     */
    public List getInterestRates(String companyName, String marketingPackageName, String groupProductName, String areaName,
                                 String businessContractName, String fundNumber, String interestRateDate,
                                 String lastValuationDate, String trxEffectiveDate, String optionCT);

 	/**
   	 * Saves/Updates the specified FilteredFundVO and clears the CSCache since it
   	 * is now stale.
   	 * @param filteredFundVO
   	 * @throws edit.common.exceptions.VOEditException
   	 * @return the primary key of the FilteredFund
   	 */
    public long saveFilteredFund(FilteredFundVO filteredFundVO) throws Exception;

    public void copySelectedScripts(String[] keys, String operator, String fileName) throws Exception;

    public long saveCompany(String companyName, String policyNumberPrefix, String policyNumberSuffix, 
                            int policyNumberSequenceNumber, int policySequenceLength,
                            String billingCompanyNumber);

    /**
     * Validation of NewBusiness Save data will execute this method in order to build the Commission
     * Document from the data passed in and not the datbase
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
                                          String eventType, String effectiveDate,
                                          long productKey, boolean abortOnHardValidationError) throws SPException, RuntimeException;


    /**
     * Runs the specified PRASETest.
     * @param praseTestPK
     */
    public void runPRASETest(Long praseTestPK) throws SPException;

    /**
     * Gets the results recorded when processing scripts
     *
     * @return  SPRecordedResults object
     */
    public SPRecordedResults getSPRecordedResults();

    /**
     * Exports the script processor recorded results for a specific run to an xml file in the config file's export
     * directory.
     *
     * @param operator                  operator whose run data will be exported
     * @param runInformation            runInformation portion of the SPRecordedRun object.  Defines the single run
     *                                  that will be exported
     *
     * @throws Exception if there is a problem writing the file
     */
    public void exportSPRecordedRunData(String operator, String runInformation) throws Exception;

    /**
     * Clears the recorded results for a specific operator
     *
     * @param operator                  operator to be removed
     *
     * @return  SPRecordedOperator object that was removed
     */
    public SPRecordedOperator clearSPRecordedOperator(String operator);

    /**
     * Exports the scripts specified by the scriptPKs to XML files in the Exports directory
     *
     * @param scriptPKs                  array of scriptPKs of scripts to be exported
     *
     * @throws EDITException        if the export directory does not exist or if the script xml files already exist in the export directory
     * @throws IOException          for any errors produced while writing the files
     */
    public void exportScriptsToXML(String[] scriptPKs) throws EDITException, IOException;
    
}
