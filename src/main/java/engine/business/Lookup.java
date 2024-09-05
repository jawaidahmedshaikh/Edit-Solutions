  /*
 * User: unknown
 * Date: Jun 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.business;

import edit.common.vo.*;
import edit.common.*;
import edit.services.component.ILookup;

import java.util.List;

import engine.dm.dao.DAOFactory;


/**
 * The Engine request controller
 */
public interface Lookup extends ILookup {

	public abstract ProductStructureVO[] getAllProductStructureNames() throws Exception;

	public abstract ProductStructureVO[] getAllProductStructures();

    public abstract ProductStructureVO[] getAllProductStructuresByIds(long[] ids) throws Exception;

    public abstract ProductRuleStructureVO[] getAllProductRuleStructuresByRuleId(long ruleId) throws Exception;

	public abstract ProductStructureVO[] getAllProductStructuresAttachedToRules()
														 throws Exception;

    public abstract ProductStructureVO[] getAllProductStructuresByCoName(String companyName);

	public abstract ProductRuleStructureVO[] getAllRuleIdsByCSId(long csId) throws Exception;

	public abstract ScriptLineVO[] getAllScriptLines(long scriptId) throws Exception;

    public ProductStructureVO[] getByProductStructureId(long productId);

    public abstract ScriptVO[] getAllScriptNames() throws Exception;

	public abstract ScriptInstructionVO[] getAllScriptInstructions() throws Exception;

	public abstract TableDefVO[] getAllTableNames() throws Exception;

	public abstract TableKeysVO[] getAllTableKeysById(long tableId) throws Exception;

	public abstract TableKeysVO[] getTableKeyByTableKeyId(long tableKeyId) throws Exception;

	public abstract RateTableVO[] getAllRatesById(long tableId) throws Exception;

	public abstract RateTableVO[] getAllRatesByIdAndOrderByAccessType(long tableId,String orderBy) throws Exception;

	public abstract InterestRateParametersVO[] getAllInterestRateParamsAndInterestRates() throws Exception;

	public abstract InterestRateVO[] getInterestRateById(long interestRateParameterId) throws Exception;

	public abstract RulesVO[] getAllRules() throws Exception;

    public abstract RulesVO[] getAllRulesByCSId(long csId) throws Exception;

	public abstract RulesVO[] getRuleByRuleId(long ruleId) throws Exception;

	public abstract TableDefVO[] getTableNameByTableDefId(long tableDefId) throws Exception;

    public abstract FilteredFundVO[] getUnitValuesByCSIdFundId(long csId, long fundId) throws Exception;

    public abstract FilteredFundVO[] getByFundId(long fundId) throws Exception;

    public abstract FundVO[] getFundsBYCSId(long productStructureId) throws Exception;

    public abstract FundVO[] getFundsByMarketingPackage(String marketingPackageName);

    public abstract FundVO[] getFundByFundPK(long fundPK) throws Exception;

    public abstract FundVO[] getFundByFilteredFundFK(long filteredFundFK,
                                                      boolean includeChildVOs,
                                                       List voClassExclusionList)
                                                    throws Exception;

    public abstract FundVO[] getFundByFundPK(long fundPK,
                                              boolean includeChildVOs,
                                               String[] voClassExclusionList)
                                            throws Exception;

    public abstract FundVO[] getAllFunds();

    /**
     * Retrieves all Funds where the ExcludeFromActivityFileInd = "N"
     * @return
     */
    public abstract FundVO[] composeAllFundsByActivityFileInd(List voInclusionList, String activityFileInd);

    public abstract FundVO[] getAllFundsNonRecursively() throws Exception;

    public abstract FilteredFundVO[] getAllFilteredFunds() throws Exception;

    public abstract FilteredFundVO[] getAllFilteredFundVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception;

    public abstract FundVO[] getAllFixedFunds() throws Exception;

    /**
     * Retrieves all FilteredFundVOs whose parent fund's FundTypeCT is 'Hedge'
     * @return
     * @throws Exception
     */
    public abstract FilteredFundVO[] getAllHedgeFunds() throws Exception;

    public abstract ScriptVO[] getScriptByName(String scriptName) throws Exception;

    public abstract TableDefVO[] getTableIdByName(String tableName) throws Exception;

    public abstract UnitValuesVO[] getUnitValuesByFilteredFundId(long filteredFundId) throws Exception;

    public abstract FilteredFundVO[] getByCSIdFundId(long productStructureId, long fundId) throws Exception;

    public abstract FilteredFundVO[] getByMarketingPackageFundId(String marketingPackageName, long fundId) throws Exception;

//    public AreaStructureVO[] findAllAreaStructureVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception;

//    public AreaStructureVO[] findAreaStructureVOByPK(long primaryKey, boolean includeChildVOs, String[] voClassExclusionList) throws Exception;

    public FundVO[] findAllFundVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception;

    public ProductStructureVO[] findAllProductStructureVOs(boolean includeChildVOs, String[] voClassExclusionList);

    /**
     * Returns all ProductStructureVOs whose TypeCodeCT = 'Product'
     * @param includeChildeVOs
     * @param voClassExclusionList
     * @return
     */
    public ProductStructureVO[] findAllProductTypeStructureVOs(boolean includeChildeVOs, List voClassExclusionList);

    public abstract ProductStructureVO[] getAllProductStructureVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception;

//    public AreaStructureVO[] findAttachedAreaStructureVOs(long companyStructurePK,
//                                                  boolean includeChildVOs,
//                                                   String[] voClassExclusionList)
//                                                throws Exception;

    public ProductStructureVO[] findProductStructureVOsAttachedToRulesVOs()
                                                throws Exception;

    public FilteredFundVO[] findFilteredFundByPK(long filteredFundPK);

    public FilteredFundVO[] findFilteredFundByPK(long filteredFundPK,
                                                  boolean includeChildVos,
                                                   List voClassExclusionList);

    public FilteredFundVO[] findFilteredFundVOsByPK(long filteredFundPK,
                                          boolean includeChildVos,
                                           List voClassExclusionList)
                                        throws Exception;

    public abstract FilteredFundVO[] getByFundNumber(String fundNumber);

    public FilteredFundVO[] findAttachedFilteredFundVOs(long productStructurePK,
                                                  boolean includeChildVOs,
                                                   String[] voClassExclusionList)
                                                throws Exception;

    public abstract ProductFilteredFundStructureVO[] getByPK(long productFilteredFundStructureId) throws Exception;

    public abstract RulesVO[] findRulesVOsByProductStructurePK(long productStructurePK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract ScriptVO[] findScriptVOByPK(long scriptPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract TableDefVO[] findTableDefVOByPK(long tableDefPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract RulesVO[] findAllRulesVOs(boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract ProductStructureVO[] findProductStructureVOByPK(long productStructurePK, boolean includeChildVOs, List voExclusionList);

    public abstract TableDefVO[] findAllTableDefVOs(boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract ScriptVO[] findAllScriptVOs(boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract ProductRuleStructureVO[] findAllProductRuleStructureVOs(boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract RulesVO[] findRulesVOByPK(long rulesPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public RuleNameVO findRuleNameVO() throws Exception;

    public ProductStructureVO[] findAttachedProductStructureVOsByRulesPK(long rulesPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public RulesVO[] findAttachedRulesVOsByProductStructurePK(long productStructurePK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public CompanyStructureNameVO findCompanyStructureNameVO() throws Exception;

    public abstract RulesVO[] findRulesVOsByTableDefPK(long tableDefPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract RulesVO[] findRulesVOsByScriptPK(long scriptPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract long[] findRulesPKsByProductStructurePK(long productStructurePK) throws Exception;

    public abstract long[] findAttachedProductStructurePKs() throws Exception;

    public RateTableVO[] findRateTableVOsByTableKeysPK(long tableKeysPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract TableKeysVO[] findTableKeysVOByPK(long tableKeysPK, boolean includeChildVOs, List voExclusionList) throws Exception;

    public abstract long[] findAttachedRulesPKs() throws Exception;

    public abstract long[] findAttachedTableDefPKs() throws Exception;

    public abstract long[] findAttachedScriptPKs() throws Exception;

    public abstract FilteredFundVO[] findFilteredFundByProductStructure(long productStructurePK,
                                                                         boolean includeChildVOs,
                                                                          List voExclusionList) throws Exception;

    public abstract FilteredFundVO[] findFilteredFundByMarketingPackage(String marketingPackage,
                                                                        boolean includeChildVOs,
                                                                        List voExclusionList) throws Exception;

    public abstract long[] getProductStructurePKsByCompanyName(String companyName);

    public abstract long[] getAllProductStructurePKs();

    public abstract FilteredFundVO[] findFilteredFundbyPKAndFundType(long filteredFundPK,
                                                                         boolean includeChildVOs,
                                                                          List voExclusionList);

    public AreaVO composeAreaVO(long areaPK, List voInclusionList) throws Exception;

    public AreaVO[] composeAreaVOByOverrideStatus(String overrideStatus, List voInclusionList) throws Exception;

    public FilteredAreaVO[] composeFilteredAreaVOByProductStructurePK(long productStructurePK, List voInclusionList) throws Exception;

    public FilteredAreaVO composeFilteredAreaVOByFilteredAreaPK(long filteredAreaPK, List voInclusionList) throws Exception;

    public FilteredFundVO[] composeFilteredFundVOByProductStructurePK(long productStructurePK, List voInclusionList) throws Exception;

    public FilteredFundVO[] composeFilteredFundVOByProductStructurePK_AND_FundType(long productStructurePK, List voInclusionList, String fundType) throws Exception;

    /**
     * Composes FilteredFundVO, retrieving filtered funds by the give ProductStructureFK and FundFK
     * @param productStructurePK
     * @param fundPK
     * @param voInclusionLIst
     * @return
     * @throws Exception
     */ 
    public FilteredFundVO[] composeFilteredFundVOByCoStructurePK_And_FundPK(long productStructurePK, long fundPK, List voInclusionLIst) throws Exception;

    public FilteredFundVO[] composeFilteredFundVOByFilteredFundPK(long filteredFundPK, List voInclusionList) throws Exception;

    /**
     * Composes FilteredFundVOs, retrieving filtered funds show FundFK is equal to the fundFK parameter
     * @param fundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] composeFilteredFundVOByFundFK(long fundFK, List voInclusionList) throws Exception;

    /**
     * Composes all FilteredFundVOs for the given fundNumber
     * @param fundNumber
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] composeFilteredFundVOByFundNumber(String fundNumber, List voInclusionList) throws Exception;

    /**
     * Composes ProductFilteredFundStructureVOs, retrieving those records whose ProductStructureFK and FilteredFundFK
     * match the productStructureFK and filteredFundFK parameters
     * @param productStructureFK
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public ProductFilteredFundStructureVO composeProductFilteredFundStructureByCoStructFKAndFltrdFundFK(long productStructureFK, long filteredFundFK, List voInclusionList);

    public ProductFilteredFundStructureVO composeProductFilteredFundStructureByPK(long productFilteredFundStructurePK, List voInclusionList);

    public FundVO[] composeAllFundVOs(List voInclusionList);

    public FundVO[] composeAllFixedFundVOs(List voInclusionList) throws Exception;

    public FundVO[] composeAllVariableFundVOs(List voInclusionList) throws Exception;

    public FundVO composeFundVOByFilteredFundPK(long filteredFundPK, List voInclusionList) throws Exception;

    public ProductStructureVO[] findProductStructureByNames(String companyName,
                                                            String marketingPackageName,
                                                            String groupProductName,
                                                            String areaName,
                                                            String businessContractName);

    public FilteredAreaVO[] findByProductStructurePK_AND_AreaCT(long productStructurePK, String area) throws Exception;

    public TableDefVO[] getTableDefByName(String tableName) throws Exception;

    public FilteredFundVO[] getSpecificUnitValue(long productStructurePK, long fundPK, String cycleDate) throws Exception;

    public UnitValuesVO[] getUnitValuesByFilteredFundIdDate(long filteredFundPK, String cycleDate, String pricingDirection) throws Exception;

    /**
     * Used by ActivityFileInterfaceProcessor - filtering additionally by charge code
     * @param filteredFundFK
     * @param cycleDate
     * @param pricingDirection
     * @param chargeCodeFK
     * @return
     */
    public UnitValuesVO[] getUnitValuesByFilteredFundIdDateChargeCode(
                    long filteredFundFK, String cycleDate, String pricingDirection, long chargeCodeFK);
    /**
     * Composes UnitValue VOs for the given filtered fund and to/from dates
     * @param filteredFundPK
     * @param fromDate
     * @param toDate
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public UnitValuesVO[] getUnitValuesByFilteredFundIdAndDates(long filteredFundPK,
                                                                long chargeCodeFK,
                                                                String fromDate,
                                                                String toDate,
                                                                List voInclusionList) throws Exception;


    /**
     *  Get Unit Value VOs that match a filtered fund id, the charge code, and effective date.
     * @param filteredFundId  filtered fund PK
     * @param chargeCodeFK  charge code FK
     * @param effDate effective date
     * @return array of UnitValue
     * @throws Exception
     */
    public UnitValuesVO[] getUnitValuesByFilteredFundChargeCodeEffDate(long filteredFundId,
                                                                       long chargeCodeFK,
                                                                       String effDate) throws Exception;
    

    public UnitValuesVO[] findUnitValuesByFilteredFundAndChargeCode(long filteredFundId, long chargeCodeFK);
 

   public TableKeysVO[] findTableKeysByAllColumns(long tableDefPK,
                                                    String effectiveDate,
                                                     String bandAmountStart,
                                                      String bandAmountEnd,
                                                      String userKey,
                                                       String genderId,
                                                        String classId,
                                                         String area,
                                                          String tableType) throws Exception;

    public RateTableVO[] findIssueRates(long tableKeyId, int age) throws Exception;

    /**
     * The entire list of available AreaKeys.
     * @return
     */
    public AreaKeyVO[] findAllAreaKeys();

    /**
     * The list of AreaValues associated with the supplied AreaKeyPK.
     * @param areaKeyPK
     * @return
     */
    public AreaValueVO[] findAreaValuesBy_AreaKeyPK(long areaKeyPK);

    /**
     * Finds the AreaValue by areaValuePK.
     * @param areaValuePK
     * @return
     */
    public AreaValueVO findAreaValueBy_AreaValuePK(long areaValuePK);

    /**
     * Finder.
     * @see engine.ProductStructure#findBy_ProductStructurePK_AreaValuePK(long, long)
     * @param productStructurePK
     * @param areaValuePK
     * @return
     */
    public ProductStructureVO findProductStructureBy_ProductStructurePK_AreaValuePK(long productStructurePK, long areaValuePK);

    /**
     * Finder.
     * @see engine.AreaValue#findBy_ProductStructurePK(long)
     * @param productStructurePK
     * @return
     */
    public AreaValueVO[] findAreaValuesBy_ProductStructurePK(long productStructurePK);

   /**
    * Finder.
    * @param areaKeyPK
    * @return
    */
    public AreaKeyVO findAreaKeyBy_AreaKeyPK(long areaKeyPK);

    /**
     * Retrieves all Unit Values whose UpdateStatus is equal to the updateStatus parameter
     * @param updateStatus
     * @return
     * @throws Exception
     */
    public UnitValuesVO[] getUnitValuesByUpdateStatus(String updateStatus) throws Exception;

    /**
     * Retrieves all ProductStructureVOs for the given marketing package name
     * @param marketingPackageName
     * @return
     */
    public ProductStructureVO[] getAllProductStructuresByMarketingPackage(String marketingPackageName);
    
    /**
    *
    * @param productStructurePK
    * @param areaCT
    * @param grouping
    * @param effectiveDate
    * @param field
    * @param qualifierCT
    * @return
    */
    public AreaValueVO getAreaValue(long productStructurePK,
                                String areaCT, 
                                String grouping, 
                                EDITDate effectiveDate,
                                String field,
                                String qualifierCT); 
    
    /**
    *
    * @param productStructurePK
    * @param areaCT
    * @param grouping
    * @param effectiveDate
    * @param field
    * @param qualifierCT
    * @return
    */
    public AreaValueVO[] getMultipleAreaValues(long productStructurePK,
                                String areaCT, 
                                String grouping, 
                                EDITDate effectiveDate,
                                String field,
                                String qualifierCT); 

    /**
     * Finder.
     * @param productStructurePK
     * @param treatyGroupPK
     * @return
     */
    public ProductStructureVO findProductStructureBy_ProductStructurePK_TreatyGroupPK(long productStructurePK, long treatyGroupPK);

    /**
     * Finder method for feeDescription by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public FeeDescriptionVO[] findFeeDescriptionBy_FilteredFundPK(long filteredFundPK);

    /**
     * Finder mehtod for feeDescription by PK.
     * @param feeDescriptionPK
     * @return
     */
    public FeeDescriptionVO findFeeDescriptionBy_FeeDescriptionPK(long feeDescriptionPK);

    /**
     * Finder method for Fee by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public FeeVO[] findFeeBy_FilteredFundPK(long filteredFundPK);

    /**
     * Finder method for Fee by PK. 
     * @param feePK
     * @return
     */
    public FeeVO findFeeBy_FeePK(long feePK);

    /**
     * Finder method for Fee by filteredFundPK and pricingTypeCT.
     * @param filteredFundPK
     * @param pricingTypeCT
     * @param feeRedemption
     * @return
     */
    public FeeVO[] findFeeBy_FilteredFundPK_And_PricingTypeCT_And_FeeRedemption_And_TrxTypeCT
                                                                                (long filteredFundPK,
                                                                                String pricingTypeCT,
                                                                                String feeRedemption,
                                                                                String transactionTypeCT);

    /**
     * Finder method for FeeDescription by filteredFundPK and feeTypeCT and feeRedemption.
     * @param filteredFundPK
     * @param feeTypeCT
     * @return
     */
    public FeeDescriptionVO findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(long filteredFundPK, String feeTypeCT);

    /**
     * Finder method for FeeCorrespondence by feePK.
     * @param feePK
     * @return
     */
    public FeeCorrespondenceVO findFeeCorrespondenceBy_FeePK(long feePK);

    /**
     * Returns the latest ControlBalance record for the given productFilteredFundStructure
     * @param productFilteredFundStructureFK
     * @return
     */
    public ControlBalanceVO findLastControlBalanceVO(long productFilteredFundStructureFK);


    /**
     * Returns the latest ControlBalance record for the given productFilteredFundStructure
     * and charge code FK.  If ChargeCodeFK is 0, then checks for IS NULL.
     * @param productFilteredFundStructureFK
     * @return
     */
    public ControlBalanceVO findLastControlBalanceVO(
            long productFilteredFundStructureFK,
            long chargeCodeFK);
    
    /**
     * Returns all Fee records whose accounting pending status is 'Y' and the effectiveDate < given date
     * @param voInclusionList
     * @param accountingProcessDate
     * @return
     */
    public FeeVO[] composeAllFeesByAccountingPendingStatus_Date(List voInclusionList, String accountingProcessDate);

    /**
     * Returns the unit values for the specified fund, charge code, date a pricing direction.
     * @param filteredFundPK
     * @param chargeCodeFK
     * @param cycleDate
     * @param pricingDirection
     * @return
     * @throws Exception
     */
    public UnitValuesVO[] getUnitValuesByFund_ChargeCode_Date(long filteredFundPK,
                                                              long chargeCodeFK,
                                                              String cycleDate,
                                                              String pricingDirection);

    /**
     * Returns the ControlBalance record for the given ProductFilteredFundStructureFK whose EndingBalanceCycleDate
     * is equal to (or if not found it will return the record just previous to) the given processDate
     * @param productFilteredFundStructureFK
     * @param processDate
     * @return
     * @throws Exception
     */
    public ControlBalanceVO[] findControlBalanceByCoFilteredFundStructure_DateClosest(long productFilteredFundStructureFK,
                                                                                      String processDate) throws Exception;

    /**
     * Returns the ControlBalance record for the given ProductFilteredFundStructureFK and ChargeCodeFK whose EndingBalanceCycleDate
     * is equal to (or if not found it will return the record just previous to) the given processDate
     * @param productFilteredFundStructureFK
     * @param processDate
     * @param chargeCodeFK
     * @return
     * @throws Exception
     */
    public ControlBalanceVO[] findControlBalanceByCoFilteredFundStructure_DateClosest(long productFilteredFundStructureFK,
                                                                                      long chargeCodeFK,
                                                                                      String processDate) throws Exception;


	public ProductStructureVO[] findByTypeCode(String typeCodeCT, boolean includeChildVOs, List voExclusionList);

   	public ProductStructureVO[] getProductStructuresByTypeCode(String typeCode);

    /**
     * Returns the FeeCorrespondenceVOs that have a Correspondence Date less than or equal to the parameter date.
     * @param notifyCorrDate
     * @return
     * @throws Exception
     */
    public FeeCorrespondenceVO[] composeFeeCorrespondenceVOByDate(String notifyCorrDate) throws Exception;

    /**
     * Finder by ControlBalanceFK
     * Returns the latest ControlBalanceDetail for given ControlBalanceFK
     * Latest = Max(AccountingPeriod) and Max(EffectiveDate) and Max(ValuationDate)
     * @param controlBalanceFK
     * @return
     */
    public ControlBalanceDetailVO findLatestControlBalanceDetailByControlBalanceFK(long controlBalanceFK);
}
