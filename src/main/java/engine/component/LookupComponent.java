/*
 * User: unknown
 * Date: Aug 3, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.component;

import edit.common.*;
import edit.common.vo.*;
import edit.services.component.AbstractLookupComponent;
import edit.services.db.CRUDEntityImpl;
import engine.*;
import engine.sp.*;
import engine.business.Lookup;
import engine.dm.composer.AreaComposer;
import engine.dm.composer.FilteredAreaComposer;
import engine.dm.composer.FilteredFundComposer;
import engine.dm.composer.VOComposer;
import engine.dm.dao.DAOFactory;
import engine.dm.dao.FastDAO;
import engine.dm.dao.ControlBalanceDetailDAO;

import java.util.ArrayList;
import java.util.List;


/**
 * The Calculation Engine request controller
 */
public class LookupComponent extends AbstractLookupComponent implements Lookup
{

    public RulesVO[] findAttachedRulesVOsByProductStructurePK(long productStructurePK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getRulesDAO().findByProductStructurePK(productStructurePK, includeChildVOs, voExclusionList);
    }

    public ProductStructureVO[] findProductStructureVOsAttachedToRulesVOs(
            )
            throws Exception
    {
        return DAOFactory.getProductStructureDAO().findAllProductStructuresAttachedToRules();
    }

    public ScriptVO[] findAllScriptVOs(boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getScriptDAO().findAll(includeChildVOs, voExclusionList);
    }

    public RuleNameVO findRuleNameVO() throws Exception
    {
        return new FastDAO().findRuleNameVO();
    }

    public RulesVO[] findRulesVOByPK(long rulesPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getRulesDAO().findByPK(rulesPK, includeChildVOs, voExclusionList);
    }

    public ProductRuleStructureVO[] findAllProductRuleStructureVOs(boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getProductRuleStructureDAO().findAll(includeChildVOs, voExclusionList);
    }

    public TableDefVO[] findAllTableDefVOs(boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getTableDefDAO().findAll(includeChildVOs, voExclusionList);
    }

    public ProductStructureVO[] getAllProductStructureNames() throws Exception
    {

        ProductStructureVO[] ProductStructureVO = DAOFactory.getProductStructureDAO().
                findAllProductStructureNames();

        return ProductStructureVO;
    }

    public ProductStructureVO[] getAllProductStructures()
    {

        ProductStructureVO[] productStructureVO = DAOFactory.getProductStructureDAO().
                findAllProductStructures();

        return productStructureVO;
    }

    public ProductStructureVO[] getAllProductStructuresByIds(long[] ids) throws Exception
    {

        ProductStructureVO[] productStructureVOs = DAOFactory.getProductStructureDAO().
                findByProductStructureIds(ids);

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
            CompanyVO companyVO = company.createCompanyVO();
            productStructureVOs[i].setParentVO(CompanyVO.class, companyVO);
        }
        return productStructureVOs;
    }

    public ProductRuleStructureVO[] getAllProductRuleStructuresByRuleId(long ruleId) throws Exception
    {

        ProductRuleStructureVO[] productRuleStructureVO =
                DAOFactory.getProductRuleStructureDAO().findAllByRulePK(ruleId);

        return productRuleStructureVO;
    }

    public ProductStructureVO[] getAllProductStructuresAttachedToRules() throws Exception
    {

        ProductStructureVO[] productStructureVO = DAOFactory.getProductStructureDAO().
                findAllProductStructuresAttachedToRules();

        return productStructureVO;
    }

    public ProductStructureVO[] getAllProductStructuresByCoName(String companyName)
    {
        ProductStructure[] productStructures = ProductStructure.findBy_CompanyName(companyName);

        ProductStructureVO[] productStructureVOs = null;
        List productStructureList = new ArrayList();

        if (productStructures != null)
        {
            for (int i = 0; i < productStructures.length; i++)
            {
                Company company = productStructures[i].getCompany();
                CompanyVO companyVO = company.createCompanyVO();
                ProductStructureVO productStructureVO = (ProductStructureVO)productStructures[i].getVO();
                productStructureVO.setParentVO(CompanyVO.class, companyVO);
                productStructureList.add(productStructureVO);
            }

            productStructureVOs = (ProductStructureVO[])productStructureList.toArray(new ProductStructureVO[productStructureList.size()]);
        }

        return productStructureVOs;
    }

    public ProductRuleStructureVO[] getAllRuleIdsByCSId(long csId) throws Exception
    {

        ProductRuleStructureVO[] productRuleStructureVO = DAOFactory.getProductRuleStructureDAO().
                findAllRulePKsByProductStructurePK(csId);

        return productRuleStructureVO;
    }

    public ProductStructureVO[] getByProductStructureId(long productId)
    {

        ProductStructureVO[] productStructureVO = DAOFactory.getProductStructureDAO().
                findByProductStructureId(productId);

        return productStructureVO;
    }

    public ScriptVO[] getAllScriptNames() throws Exception
    {

        ScriptVO[] scriptVO = DAOFactory.getScriptDAO().findAllScriptNames();

        return scriptVO;
    }

    public ScriptLineVO[] getAllScriptLines(long scriptId) throws Exception
    {

        ScriptLineVO[] scriptLineVO = DAOFactory.getScriptLineDAO().findScriptLinesById(scriptId);

        return scriptLineVO;
    }

    public ScriptInstructionVO[] getAllScriptInstructions() throws Exception
    {

        ScriptInstructionVO[] scriptInstructionVO = DAOFactory.getScriptInstructionDAO().
                findAllScriptInstructions();

        return scriptInstructionVO;
    }

    public TableDefVO[] getAllTableNames() throws Exception
    {

        TableDefVO[] tableDefVO = DAOFactory.getTableDefDAO().findAllTableNames();

        return tableDefVO;
    }

    public TableKeysVO[] getAllTableKeysById(long tableId) throws Exception
    {

        TableKeysVO[] tableKeysVO = DAOFactory.getTableKeysDAO().findTableKeysById(tableId);

        return tableKeysVO;
    }

    public TableKeysVO[] getTableKeyByTableKeyId(long tableKeyId) throws Exception
    {

        TableKeysVO[] tableKeysVO = DAOFactory.getTableKeysDAO().findFirstTableKeysByTableKeyId(tableKeyId);

        return tableKeysVO;
    }

    public RateTableVO[] getAllRatesById(long tableId) throws Exception
    {

        RateTableVO[] rateTableVO = DAOFactory.getRateTableDAO().findAllRatesById(tableId);

        return rateTableVO;
    }


    public InterestRateParametersVO[] getAllInterestRateParamsAndInterestRates() throws Exception
    {
        InterestRateParametersVO[] interestRateParametersVO = DAOFactory.getInterestRateParametersDAO().findAllInterestRateParamsAndInterestRates();

        return interestRateParametersVO;
    }

    public InterestRateVO[] getInterestRateById(long interestRateParametersId) throws Exception
    {
        InterestRateVO[] interestRateVO = DAOFactory.getInterestRateDAO().findInterestRateById(interestRateParametersId);

        return interestRateVO;
    }

    public RateTableVO[] getAllRatesByIdAndOrderByAccessType(long tableId, String orderBy) throws Exception
    {

        RateTableVO[] rateTableVO = DAOFactory.getRateTableDAO().findAllRatesByIdAndOrderByAccessType(tableId, orderBy);

        return rateTableVO;

    }

    public RulesVO[] getAllRules() throws Exception
    {

        RulesVO[] rulesVO = DAOFactory.getRulesDAO().findAllRules();

        return rulesVO;
    }

    public RulesVO[] findRulesVOsByProductStructurePK(long productStructurePK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        RulesVO[] rulesVOs = DAOFactory.getRulesDAO().findByProductStructurePK(productStructurePK, includeChildVOs, voExclusionList);

        return rulesVOs;
    }

//    public RuleStructureVO[] findRuleStructureVOsByPK(long ruleStructurePK, boolean includeChildVOs, List voExclusionList) throws Exception
//    {
//        RuleStructureVO[] ruleStructureVOs = DAOFactory.getRuleStructureDAO().findByRuleStructurePK(ruleStructurePK, includeChildVOs, voExclusionList);
//
//        return ruleStructureVOs;
//    }

    public ScriptVO[] findScriptVOByPK(long scriptPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        ScriptVO[] scriptVOs = DAOFactory.getScriptDAO().findByScriptPK(scriptPK, includeChildVOs, voExclusionList);

        return scriptVOs;
    }

    public RulesVO[] findAllRulesVOs(boolean includeChildVOs, List voExclusionList) throws Exception
    {
        RulesVO[] rulesVOs = DAOFactory.getRulesDAO().findAll(includeChildVOs, voExclusionList);

        return rulesVOs;
    }

    public CompanyStructureNameVO findCompanyStructureNameVO() throws Exception
    {
        return new FastDAO().findCompanyStructureNameVO();
    }

    public ProductStructureVO[] findProductStructureVOByPK(long productStructurePK, boolean includeChildVOs, List voExclusionList)
    {
        return DAOFactory.getProductStructureDAO().findByPK(productStructurePK, includeChildVOs, voExclusionList);
    }

    public TableDefVO[] findTableDefVOByPK(long tableDefPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        TableDefVO[] tableDefVOs = DAOFactory.getTableDefDAO().findByTableDefPK(tableDefPK, includeChildVOs, voExclusionList);

        return tableDefVOs;
    }

    public RulesVO[] getAllRulesByCSId(long csId) throws Exception
    {

        RulesVO[] rulesVO = DAOFactory.getRulesDAO().findAllRulesByCSId(csId);

        return rulesVO;
    }

    public RulesVO[] getRuleByRuleId(long ruleId) throws Exception
    {

        RulesVO[] rulesVO = DAOFactory.getRulesDAO().findByRuleId(ruleId);

        return rulesVO;
    }

    public TableDefVO[] getTableNameByTableDefId(long tableDefId) throws Exception
    {

        TableDefVO[] tableDefVO = DAOFactory.getTableDefDAO().findTableNameById(tableDefId);

        return tableDefVO;
    }

    public FilteredFundVO[] getUnitValuesByCSIdFundId(long csId, long fundId) throws Exception
    {

        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findUnitValuesByCSIdFundId(csId, fundId);

        return filteredFundVO;
    }

    public UnitValuesVO[] getUnitValuesByFilteredFundId(long filteredFundId) throws Exception
    {

        UnitValuesVO[] unitValuesVO = DAOFactory.getUnitValuesDAO().
                findUnitValuesByFilteredFundId(filteredFundId);

        return unitValuesVO;
    }

    /**
     * Get the unit values for a given filtered fund id and charge code FK
     * @param filteredFundId
     * @param chargeCodeFK
     * @return
     */
    public UnitValuesVO[] findUnitValuesByFilteredFundAndChargeCode(long filteredFundId, long chargeCodeFK)
    {

         UnitValuesVO[] unitValuesVO = DAOFactory.getUnitValuesDAO().
                        findUnitValuesByFilteredFundAndChargeCode(filteredFundId, chargeCodeFK);

        return unitValuesVO;
    }


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
                                                                       String effDate) throws Exception
    {
        // first get the unit values for filteredfund id, effective date for the "Forward" direction
        UnitValuesVO[] unitValuesVO = DAOFactory.getUnitValuesDAO().
                findUnitValuesByFilteredFundIdDate(filteredFundId, effDate, "Forward");

        if (unitValuesVO == null)
            return null;

        List unitValues = new ArrayList();

        for (int i = 0; i < unitValuesVO.length; i++)
        {
            UnitValuesVO valuesVO = unitValuesVO[i];
            long tempChargeCodeFK = valuesVO.getChargeCodeFK();
            if (chargeCodeFK == tempChargeCodeFK)
            {
                unitValues.add(valuesVO);
            }
        }

        return (UnitValuesVO[]) unitValues.toArray(new UnitValuesVO[unitValues.size()]);
    }


    public FilteredFundVO[] getByFundId(long fundId) throws Exception
    {

        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().
                findByFundId(fundId);

        return filteredFundVO;
    }

    public FundVO[] getFundsBYCSId(long productStructureId) throws Exception
    {

        FundVO[] fundVO = DAOFactory.getFundDAO().findFundsByCSId(productStructureId);

        return fundVO;
    }

    /**
     * Find all funds for the given marketing package
     * @param marketingPackageName
     * @return
     */
    public FundVO[] getFundsByMarketingPackage(String marketingPackageName)
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findFundsByMarketingPackage(marketingPackageName);

        return fundVO;
    }

    public FundVO[] getFundByFilteredFundFK(long filteredFundFK,
                                            boolean includeChildVOs,
                                            List voClassExclusionList) throws Exception
    {

        return DAOFactory.getFundDAO().
                findFundByFilteredFundFK(filteredFundFK,
                                         includeChildVOs,
                                         voClassExclusionList);
    }

    public FundVO[] getFundByFundPK(long fundPK) throws Exception
    {

        FundVO[] fundVO = DAOFactory.getFundDAO().findFundByPK(fundPK);

        return fundVO;
    }

    public FundVO[] getFundByFundPK(long fundPK,
                                    boolean includeChildVOs,
                                    String[] voClassExclusionList) throws Exception
    {

        FundVO[] fundVOs = DAOFactory.getFundDAO().
                findFundByPK(fundPK,
                             includeChildVOs,
                             super.convertVOClassExclusionList(voClassExclusionList));

        return fundVOs;
    }

    public FundVO[] getAllFunds()
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findAllFundsNonRecursively();

        return fundVO;
    }

    public FundVO[] composeAllFundsByActivityFileInd(List voInclusionList, String activityFileInd)
    {
        return new VOComposer().composeFundVOsByActivityFileInd(voInclusionList, activityFileInd);
    }

    public FundVO[] getAllFundsNonRecursively() throws Exception
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findAllFundsNonRecursively();

        return fundVO;
    }

    public FilteredFundVO[] getAllFilteredFunds() throws Exception
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findAllFilteredFunds();

        return filteredFundVO;
    }

    public FilteredFundVO[] getAllFilteredFundVOs(boolean includeChildVOs, String[] voExclusionList) throws Exception
    {

        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findAllFilteredFunds(includeChildVOs, super.convertVOClassExclusionList(voExclusionList));

        return filteredFundVO;
    }

    public FundVO[] getAllFixedFunds() throws Exception
    {
        FundVO[] fundVO = DAOFactory.getFundDAO().findAllFixedFunds();

        return fundVO;
    }

    /**
     * Retrieves all FilteredFundVOs whose parent fund's FundTypeCT is 'Hedge'
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] getAllHedgeFunds() throws Exception
    {
        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().findAllHedgeFunds();

        return filteredFundVO;
    }

    public ScriptVO[] getScriptByName(String scriptName) throws Exception
    {

        ScriptVO[] scriptVO = DAOFactory.getScriptDAO().findScriptByName(scriptName);

        return scriptVO;
    }

    public TableDefVO[] getTableIdByName(String tableName) throws Exception
    {

        TableDefVO[] tableDefVO = DAOFactory.getTableDefDAO().findTableIdByName(tableName);

        return tableDefVO;
    }

    public FilteredFundVO[] getByCSIdFundId(long productStructureId, long fundId) throws Exception
    {

        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().
                findByCSIdFundId(productStructureId, fundId);

        return filteredFundVO;
    }

    public FilteredFundVO[] getByMarketingPackageFundId(String marketingPackageName, long fundId) throws Exception
    {

        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().
                findByMarketingPackageFundId(marketingPackageName, fundId);

        return filteredFundVO;
    }

//    public AreaStructureVO[] findAllAreaStructureVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception
//    {
//
//        AreaStructureVO[] areaStructureVOs = DAOFactory.getAreaStructureDAO().findAll(includeChildVOs, super.convertVOClassExclusionList(voClassExclusionList));
//
//        return areaStructureVOs;
//    }
//
//    public AreaStructureVO[] findAreaStructureVOByPK(long primaryKey, boolean includeChildVOs, String[] voClassExclusionList) throws Exception
//    {
//
//        AreaStructureVO[] areaStructureVOs = DAOFactory.getAreaStructureDAO().findByPK(primaryKey, includeChildVOs, super.convertVOClassExclusionList(voClassExclusionList));
//
//        return areaStructureVOs;
//    }

    public FundVO[] findAllFundVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception
    {

        FundVO[] fundVOs = DAOFactory.getFundDAO().findAll(false, null);

        return fundVOs;
    }

    public ProductStructureVO[] findAllProductStructureVOs(boolean includeChildVOs, String[] voClassExclusionList)
    {

        ProductStructureVO[] productStructureVOs = DAOFactory.getProductStructureDAO().findAll(includeChildVOs, super.convertVOClassExclusionList(voClassExclusionList));

        return productStructureVOs;
    }

    /**
     * Returns all ProductStructureVOs whose TypeCodeCT = 'Product'
     * @param includeChildeVOs
     * @param voClassExclusionList
     * @return
     */
    public ProductStructureVO[] findAllProductTypeStructureVOs(boolean includeChildeVOs, List voClassExclusionList)
    {
        return DAOFactory.getProductStructureDAO().findAllProductTypeStructures(includeChildeVOs, voClassExclusionList);
    }

//    public AreaStructureVO[] findAttachedAreaStructureVOs(long productStructurePK, boolean includeChildVOs, String[] voClassExclusionList) throws Exception
//    {
//
//        AreaStructureVO[] areaStructureVOs = DAOFactory.getAreaStructureDAO().findAttached(productStructurePK, includeChildVOs, super.convertVOClassExclusionList(voClassExclusionList));
//
//        return areaStructureVOs;
//    }

    public FilteredFundVO[] findFilteredFundByPK(long filteredFundPK)
    {

        return DAOFactory.getFilteredFundDAO().findByFilteredFundPK(filteredFundPK);
    }

    public FilteredFundVO[] findFilteredFundByPK(long filteredFundPK,
                                                 boolean includeChildVOs,
                                                 List voClassExclusionList)
    {

        return DAOFactory.getFilteredFundDAO().findByFilteredFundPK(filteredFundPK,
                                                                    includeChildVOs,
                                                                    voClassExclusionList);
    }

    public FilteredFundVO[] findFilteredFundVOsByPK(long filteredFundPK, boolean includeChildVOs, List voClassExclusionList) throws Exception
    {

        FilteredFundVO[] filteredFundVOs = DAOFactory.getFilteredFundDAO().
                findByFilteredFundPK(filteredFundPK,
                                     includeChildVOs,
                                     voClassExclusionList);

        return filteredFundVOs;
    }

    public FilteredFundVO[] getByFundNumber(String fundNumber)
    {

        FilteredFundVO[] filteredFundVO = DAOFactory.getFilteredFundDAO().
                findByFundNumber(fundNumber);

        return filteredFundVO;
    }

    public FilteredFundVO[] findAttachedFilteredFundVOs(long productStructurePK, boolean includeChildVOs, String[] voClassExclusionList) throws Exception
    {

        FilteredFundVO[] filteredFundVOs = DAOFactory.getFilteredFundDAO().
                findAttached(productStructurePK,
                             includeChildVOs,
                             super.convertVOClassExclusionList(voClassExclusionList));

        return filteredFundVOs;
    }

    public ProductStructureVO[] getAllProductStructureVOs(boolean includeChildVOs, String[] voClassExclusionList) throws Exception
    {

        ProductStructureVO[] productStructureVOs = DAOFactory.getProductStructureDAO().findAllProductStructures();

        return productStructureVOs;
    }

    public ProductFilteredFundStructureVO[] getByPK(long productFilteredFundStructureId) throws Exception
    {

        ProductFilteredFundStructureVO[] productFilteredFundStructureVOs = DAOFactory.getProductFilteredFundStructureDAO().
                findByPK(productFilteredFundStructureId);

        return productFilteredFundStructureVOs;
    }

    public RulesVO[] findAttachedRuleVOsByProductStructurePK(long rulesPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getRulesDAO().findByProductStructurePK(rulesPK, includeChildVOs, voExclusionList);
    }

    public ProductStructureVO[] findAttachedProductStructureVOsByRulesPK(long rulesPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getProductStructureDAO().findByRulesPK(rulesPK, includeChildVOs, voExclusionList);
    }

    public long[] findAttachedProductStructurePKs() throws Exception
    {
        return new FastDAO().findAttachedProductStructurePKs();
    }

    public RulesVO[] findRulesVOsByTableDefPK(long tableDefPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getRulesDAO().findByTableDefPK(tableDefPK, includeChildVOs, voExclusionList);
    }

    public RulesVO[] findRulesVOsByScriptPK(long scriptPK,
                                            boolean includeChildVOs,
                                            List voExclusionList) throws Exception
    {

        return DAOFactory.getRulesDAO().findByScriptPK(scriptPK, includeChildVOs, voExclusionList);
    }

    public long[] findAttachedRulesPKs() throws Exception
    {
        return new FastDAO().findAttachedRulesPKs();
    }

    public long[] findAttachedTableDefPKs() throws Exception
    {
        return new FastDAO().findAttachedTableDefPKs();
    }

    public long[] findAttachedScriptPKs() throws Exception
    {

        return new FastDAO().findAttachedScriptPKs();
    }

    public long[] findRulesPKsByProductStructurePK(long productStructurePK) throws Exception
    {
        return new FastDAO().findRulesPKsByProductStructurePK(productStructurePK);
    }

    public RateTableVO[] findRateTableVOsByTableKeysPK(long tableKeysPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getRateTableDAO().findByTableKeysPK(tableKeysPK, includeChildVOs, voExclusionList);
    }

    public TableKeysVO[] findTableKeysVOByPK(long tableKeysPK, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getTableKeysDAO().findByPK(tableKeysPK, includeChildVOs, voExclusionList);
    }

    public FilteredFundVO[] findFilteredFundByProductStructure(long productStructurePK,
                                                               boolean includeChildVOs,
                                                               List voExclusionList)
            throws Exception
    {

        return DAOFactory.getFilteredFundDAO().findByProductStructureId(productStructurePK,
                                                                        includeChildVOs,
                                                                        voExclusionList);
    }

    public FilteredFundVO[] findFilteredFundByMarketingPackage(String marketingPackageName,
                                                               boolean includeChildVOs,
                                                               List voExclusionList)
            throws Exception
    {

        return DAOFactory.getFilteredFundDAO().findByMarketingPackage(marketingPackageName,
                                                                      includeChildVOs,
                                                                      voExclusionList);
    }

    public FilteredFundVO composeFilteredFundVO(long filteredFundPK, List voInclusionList) throws Exception
    {
        return new FilteredFundComposer(voInclusionList).compose(filteredFundPK);
    }

    public long[] getProductStructurePKsByCompanyName(String companyName)
    {
        return new FastDAO().findProductStructurePKsByCompanyName(companyName);
    }

    public long[] getAllProductStructurePKs()
    {
        return new FastDAO().findAllProductStructurePKs();
    }

    public FilteredFundVO[] findFilteredFundbyPKAndFundType(long filteredFundPK, boolean includeChildVOs,
                                                             List voExclusionList)
    {
        return DAOFactory.getFilteredFundDAO().findFilteredFundbyPKAndFundType(filteredFundPK, includeChildVOs, voExclusionList);
    }

    public AreaVO composeAreaVO(long areaPK, List voInclusionList) throws Exception
    {
        return new AreaComposer(voInclusionList).compose(areaPK);
    }

    public AreaVO[] composeAreaVOByOverrideStatus(String overrideStatus, List voInclusionList) throws Exception
    {
        return new VOComposer().composeAreaVOByOverrideStatus(voInclusionList, overrideStatus);
    }

    public FilteredAreaVO[] composeFilteredAreaVOByProductStructurePK(long productStructurePK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFilteredAreaByProductStructurePK(productStructurePK, voInclusionList);
    }

    public FilteredAreaVO composeFilteredAreaVOByFilteredAreaPK(long filteredAreaPK, List voInclusionList) throws Exception
    {
        return new FilteredAreaComposer(voInclusionList).compose(filteredAreaPK);
    }

    public FilteredFundVO[] composeFilteredFundVOByProductStructurePK(long productStructurePK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFilteredFundVOByProductStructurePK(productStructurePK, voInclusionList);
    }

    public FilteredFundVO[] composeFilteredFundVOByProductStructurePK_AND_FundType(long productStructurePK, List voInclusionList, String fundType) throws Exception
    {
        return new VOComposer().composeFilteredFundVOByProductStructurePK_AND_FundType(productStructurePK, voInclusionList, fundType);
    }

    /**
     * Composes FilteredFundVO, retrieving filtered funds by the give ProductStructureFK and FundFK
     * @param productStructurePK
     * @param voInclusionLIst
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] composeFilteredFundVOByCoStructurePK_And_FundPK(long productStructurePK, long fundPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFilteredFundVOByProductStructurePK_And_FundPK(productStructurePK, fundPK, voInclusionList);
    }

    public FilteredFundVO[] composeFilteredFundVOByFilteredFundPK(long filteredFundPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFilteredFundVOByFilteredFundPK(filteredFundPK, voInclusionList);
    }

    /**
     * Composes FilteredFundVOs, retrieving filtered funds show FundFK is equal to the fundFK parameter
     * @param fundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] composeFilteredFundVOByFundFK(long fundFK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFilteredFundVOByFundFK(fundFK, voInclusionList);
    }

    public FilteredFundVO[] composeFilteredFundVOByFundNumber(String fundNumber, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFilteredFundVOByFundNumber(fundNumber, voInclusionList);
    }

    /**
     * Composes ProductFilteredFundStructureVOs, retrieving those records whose ProductStructureFK and FilteredFundFK
     * match the productStructureFK and filteredFundFK parameters
     * @param productStructureFK
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public ProductFilteredFundStructureVO composeProductFilteredFundStructureByCoStructFKAndFltrdFundFK(long productStructureFK, long filteredFundFK, List voInclusionList)
    {
        return new VOComposer().composeProductFilteredFundStructureByProdStructFKAndFltrdFundFK(productStructureFK, filteredFundFK, voInclusionList);
    }

    public ProductFilteredFundStructureVO composeProductFilteredFundStructureByPK(long productFilteredFundStructurePK, List voInclusionList)
    {
        return new VOComposer().composeProductFilteredFundStructureByPK(productFilteredFundStructurePK, voInclusionList);
    }

    public FundVO[] composeAllFundVOs(List voInclusionList)
    {
        return new VOComposer().composeAllFundVOs(voInclusionList);
    }

    public FundVO[] composeAllFixedFundVOs(List voInclusionList) throws Exception
    {
        return new VOComposer().composeAllFixedFundVOs(voInclusionList);
    }

    public FundVO[] composeAllVariableFundVOs(List voInclusionList) throws Exception
    {
        return new VOComposer().composeAllVariableFundVOs(voInclusionList);
    }

    public FundVO composeFundVOByFilteredFundPK(long filteredFundPK, List voInclusionList) throws Exception
    {
        return new VOComposer().composeFundVOByFilteredFundPK(filteredFundPK, voInclusionList);
    }

    public ProductStructureVO[] findProductStructureByNames(String companyName,
                                                            String marketingPackageName,
                                                            String groupProductName,
                                                            String areaName,
                                                            String businessContractName)
    {
        return DAOFactory.getProductStructureDAO().findProductStructureByNames(companyName, marketingPackageName,
                                                                               groupProductName, areaName, businessContractName);
    }

    public FilteredAreaVO[] findByProductStructurePK_AND_AreaCT(long productStructurePK, String area) throws Exception
    {
        return null;
    }

    public TableDefVO[] getTableDefByName(String tableName) throws Exception
    {
        return DAOFactory.getTableDefDAO().findTableIdByName(tableName);
    }

    public FilteredFundVO[] getSpecificUnitValue(long productStructurePK, long fundPK, String cycleDate) throws Exception
    {
        return DAOFactory.getFilteredFundDAO().findSpecificUnitValue(productStructurePK, fundPK, cycleDate);
    }

    public UnitValuesVO[] getUnitValuesByFilteredFundIdDate(long filteredFundPK, String cycleDate, String pricingDirection) throws Exception
    {
        return DAOFactory.getUnitValuesDAO().findUnitValuesByFilteredFundIdDate(filteredFundPK, cycleDate, pricingDirection);
    }

    /**
     * Used by ActivityFileInterfaceProcessor - filtering additionally by charge code
     * @param filteredFundFK
     * @param effDate
     * @param pricingDirection
     * @param chargeCodeFK
     * @return
     */
    public UnitValuesVO[] getUnitValuesByFilteredFundIdDateChargeCode(
          long filteredFundFK, String effDate, String pricingDirection, long chargeCodeFK)
    {
         return DAOFactory.getUnitValuesDAO().findUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK, effDate, pricingDirection, chargeCodeFK);
    }

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
                                                                List voInclusionList) throws Exception
    {
        return new VOComposer().composeUnitValuesByFilteredFundAndDates(filteredFundPK, chargeCodeFK, fromDate, toDate, voInclusionList);
    }

   public TableKeysVO[] findTableKeysByAllColumns(long tableDefPK,
                                                    String effectiveDate,
                                                     String bandAmountStart,
                                                      String bandAmountEnd,
                                                      String userKey,
                                                       String genderId,
                                                        String classId,
                                                         String area,
                                                          String tableType) throws Exception
    {
        return new FastDAO().findTableKeysByAllColumns(tableDefPK, effectiveDate, bandAmountStart,
                                                         bandAmountEnd, userKey, genderId, classId,
                                                          area, tableType);
    }

    public RateTableVO[] findIssueRates(long tableKeyId, int age) throws Exception
    {
        return CSCache.getCSCache().getIssueRatesBy_TableKeyPK_Age(tableKeyId, age);
    }

    /**
     * @see engine.business.Lookup#findAllAreaKeys()
     * @return
     */
    public AreaKeyVO[] findAllAreaKeys()
    {
        AreaKey[] areaKeys = AreaKey.findAllAreaKeys();

        return (AreaKeyVO[]) CRUDEntityImpl.mapEntityToVO(areaKeys, AreaKeyVO.class);
    }

    /**
     * @see engine.business.Lookup#findAreaValuesBy_AreaKeyPK(long)
     * @param areaKeyPK
     * @return
     */
    public AreaValueVO[] findAreaValuesBy_AreaKeyPK(long areaKeyPK)
    {
        AreaValue[] areaValues = AreaValue.findBy_AreaKeyPK(areaKeyPK);

        return (AreaValueVO[]) CRUDEntityImpl.mapEntityToVO(areaValues, AreaValueVO.class);
    }
    /**
     * @see Lookup#findAreaValueBy_AreaValuePK(long)
     * @param areaValuePK
     * @return
     */
    public AreaValueVO findAreaValueBy_AreaValuePK(long areaValuePK)
    {
        AreaValue areaValue = new AreaValue(areaValuePK);

        return (AreaValueVO) areaValue.getVO();
    }

    /**
     * Finder.
     * @see engine.business.Lookup#findByProductStructurePK_AND_AreaCT(long, String)
     * @param productStructurePK
     * @param areaValuePK
     * @return
     */
    public ProductStructureVO findProductStructureBy_ProductStructurePK_AreaValuePK(long productStructurePK, long areaValuePK)
    {
        ProductStructureVO productStructureVO = null;

        ProductStructure productStructure = ProductStructure.findBy_ProductStructurePK_AreaValuePK(productStructurePK, areaValuePK);

        if (productStructure != null)
        {
            productStructureVO = (ProductStructureVO) productStructure.getVO();
        }

        return productStructureVO;
    }

    /**
     * Finder.
     * @see engine.business.Lookup#findAreaValuesBy_ProductStructurePK(long)
     * @param productStructurePK
     * @return
     */
    public AreaValueVO[] findAreaValuesBy_ProductStructurePK(long productStructurePK)
    {
        AreaValue[] areaValues = AreaValue.findBy_ProductStructurePK(productStructurePK);

        return (AreaValueVO[]) CRUDEntityImpl.mapEntityToVO(areaValues, AreaValueVO.class);
    }

    /**
     * Finder.
     * @param areaKeyPK
     * @return
     */
    public AreaKeyVO findAreaKeyBy_AreaKeyPK(long areaKeyPK)
    {
        AreaKey areaKey = new AreaKey(areaKeyPK);

        return (AreaKeyVO) areaKey.getVO();

    }

    /**
     * Retrieves all Unit Values whose UpdateStatus is equal to the updateStatus parameter
     * @param updateStatus
     * @return
     * @throws Exception
     */
    public UnitValuesVO[] getUnitValuesByUpdateStatus(String updateStatus) throws Exception
    {
        return DAOFactory.getUnitValuesDAO().findUnitValuesByUpdateStatus(updateStatus);
    }

    public AreaValueVO getAreaValue(long productStructurePK, String areaCT, String grouping, EDITDate effectiveDate, String field, String qualifierCT)
    {
        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue     = area.getAreaValue(field);

        AreaValueVO areaValueVO = null;
        if (areaValue != null)
        {
            areaValueVO = (AreaValueVO) areaValue.getVO();
        }

        return areaValueVO; 
    }
    
    public AreaValueVO[] getMultipleAreaValues(long productStructurePK, String areaCT, String grouping, EDITDate effectiveDate, String field, String qualifierCT)
    {
        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue[] areaValues = area.getMultipleAreaValues(field);
        
        if (areaValues != null) {
        	return (AreaValueVO[]) CRUDEntityImpl.mapEntityToVO(areaValues, AreaValueVO.class);
        } else {
        	return null;
        }
    }

    /**
     * Retrieves all ProductStructureVOs for the given marketing package name
     * @param marketingPackageName
     * @return
     */
    public ProductStructureVO[] getAllProductStructuresByMarketingPackage(String marketingPackageName)
    {
        return DAOFactory.getProductStructureDAO().findProductStructuresByMarketingPackage(marketingPackageName);
    }

    /**
     * @see engine.business.Lookup#findProductStructureBy_ProductStructurePK_TreatyGroupPK(long, long)
     * @param productStructurePK
     * @param treatyGroupPK
     * @return
     */
    public ProductStructureVO findProductStructureBy_ProductStructurePK_TreatyGroupPK(long productStructurePK, long treatyGroupPK)
    {
        ProductStructureVO productStructureVO = null;

        ProductStructure productStructure = ProductStructure.findBy_ProductStructurePK_TreatyGroupPK(productStructurePK, treatyGroupPK);

        if (productStructure != null)
        {
            productStructureVO = (ProductStructureVO) productStructure.getVO();
        }

        return productStructureVO;
    }

    /**
     * Finder method for feeDescription by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public FeeDescriptionVO[] findFeeDescriptionBy_FilteredFundPK(long filteredFundPK)
    {
        FeeDescription[] feeDescriptions = FeeDescription.findByFilteredFundPK(filteredFundPK);

        return (FeeDescriptionVO[]) CRUDEntityImpl.mapEntityToVO(feeDescriptions, FeeDescriptionVO.class);
    }

    /**
     * Finder method for feeDescription by PK.
     * @param feeDescriptionPK
     * @return
     */
    public FeeDescriptionVO findFeeDescriptionBy_FeeDescriptionPK(long feeDescriptionPK)
    {
        FeeDescriptionVO feeDescriptionVO = null;

        FeeDescription feeDescription = FeeDescription.findByPK(feeDescriptionPK);

        if (feeDescription != null)
        {
            feeDescriptionVO = (FeeDescriptionVO) feeDescription.getVO();
        }

        return feeDescriptionVO ;
    }

    /**
     * Finder method for Fee by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public FeeVO[] findFeeBy_FilteredFundPK(long filteredFundPK)
    {
        Fee[] fees = Fee.findByFilteredFundPK(filteredFundPK);

        return (FeeVO[]) CRUDEntityImpl.mapEntityToVO(fees, FeeVO.class);
    }

    /**
     * Finder method for Fee by PK.
     * @param feePK
     * @return
     */
    public FeeVO findFeeBy_FeePK(long feePK)
    {
        FeeVO feeVO = null;

        Fee fee = Fee.findByPK(feePK);

        if (fee != null)
        {
            feeVO = (FeeVO) fee.getVO();
        }

        return feeVO;
    }

    /**
     * Finder method for Fee by filteredFundPK and pricingTypeCT and feeRedemption.
     * @param filteredFundPK
     * @param pricingTypeCT
     * @param feeRedemption
     * @return
     */
    public FeeVO[] findFeeBy_FilteredFundPK_And_PricingTypeCT_And_FeeRedemption_And_TrxTypeCT(long filteredFundPK,
                                                                                String pricingTypeCT,
                                                                                String feeRedemption,
                                                                                String transactionTypeCT)
    {
        Fee[] fees = Fee.findByFilteredFundPK_And_PricingTypeCT_And_FeeRedemption(filteredFundPK,
                                                                                  pricingTypeCT,
                                                                                  feeRedemption,
                                                                                  transactionTypeCT);

        return (FeeVO[] ) CRUDEntityImpl.mapEntityToVO(fees, FeeVO.class);
    }

    /**
     * Finder method for FeeDescription by filteredFundPK and feeTypeCT.
     * @param filteredFundPK
     * @param feeTypeCT
     * @return
     */
    public FeeDescriptionVO findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(long filteredFundPK, String feeTypeCT)
    {
        FeeDescriptionVO feeDescriptionVO = null;

        FeeDescription feeDescription = FeeDescription.findByFilteredFundPK_And_FeeTypeCT(filteredFundPK, feeTypeCT);

        if (feeDescription != null)
        {
            feeDescriptionVO = (FeeDescriptionVO) feeDescription.getVO();
        }

        return feeDescriptionVO;
    }

    /**
     * Finder method for FeeCorrespondence by feePK.
     * @param feePK
     * @return
     */
    public FeeCorrespondenceVO findFeeCorrespondenceBy_FeePK(long feePK)
    {
        FeeCorrespondenceVO feeCorrespondenceVO = null;

        FeeCorrespondence feeCorrespondence = FeeCorrespondence.findByFeePK(feePK);

        if (feeCorrespondence != null)
        {
            feeCorrespondenceVO = (FeeCorrespondenceVO) feeCorrespondence.getVO();
        }

        return feeCorrespondenceVO;
    }


    /**
     * Returns the latest ControlBalance record for the given productFilteredFundStructure
     * @param productFilteredFundStructureFK
     * @return
     */
    public ControlBalanceVO findLastControlBalanceVO(long productFilteredFundStructureFK)
    {
        ControlBalanceVO controlBalanceVOToReturn = null;

        ControlBalanceVO[] controlBalanceVO = DAOFactory.getControlBalanceDAO().
                getControlBalanceVOByProductFilteredFundStructureFK(productFilteredFundStructureFK);

        if (controlBalanceVO != null)
        {
            controlBalanceVOToReturn = controlBalanceVO[0];
        }

        return controlBalanceVOToReturn;
    }

     /**
     * Returns the latest ControlBalance record for the 
     * given productFilteredFundStructure
     * @param productFilteredFundStructureFK
     * @return
     */
    public ControlBalanceVO findLastControlBalanceVO(
            long productFilteredFundStructureFK,
            long chargeCodeFK)
    {
        ControlBalanceVO controlBalanceVOToReturn = null;

        ControlBalanceVO[] controlBalanceVO =
                new DAOFactory().getControlBalanceDAO().
                getControlBalanceByFilteredFundStrucAndChargeCode(
                        productFilteredFundStructureFK, chargeCodeFK);

        if (controlBalanceVO != null)
        {
            controlBalanceVOToReturn = controlBalanceVO[0];
        }

        return controlBalanceVOToReturn;
    }

    /**
     * Returns all Fee records whose accounting pending status is 'Y' and the effectiveDate < given date
     * @param voInclusionList
     * @param accountingProcessDate
     * @return
     */
    public FeeVO[] composeAllFeesByAccountingPendingStatus_Date(List voInclusionList, String accountingProcessDate)
    {
        return new VOComposer().composeAllFeesByAccountingPendingStatus_Date(voInclusionList, accountingProcessDate);
    }

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
                                                              String pricingDirection)
    {
        return DAOFactory.getUnitValuesDAO().findUnitValuesByFund_ChargeCode_Date(filteredFundPK,
                                                                                  chargeCodeFK,
                                                                                  cycleDate,
                                                                                  pricingDirection);
    }

    /**
     * Returns the ControlBalance record for the given ProductFilteredFundStructureFK whose EndingBalanceCycleDate
     * is equal to (or if not found it will return the record just previous to) the given processDate
     * @param productFilteredFundStructureFK
     * @param processDate
     * @return
     * @throws Exception
     */
    public ControlBalanceVO[] findControlBalanceByCoFilteredFundStructure_DateClosest(long productFilteredFundStructureFK,
                                                                                    String processDate) throws Exception
    {
        return DAOFactory.getControlBalanceDAO().getControlBalanceVOByCoFilteredFundStruct_DateClosest(productFilteredFundStructureFK,
                                                                                                       processDate);
    }

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
                                                                                      String processDate) throws Exception
    {
        return DAOFactory.getControlBalanceDAO().getControlBalanceVOByCoFilteredFundStruct_DateClosest(productFilteredFundStructureFK,
                                                                                                       chargeCodeFK,
                                                                                                       processDate);
    }

	/*
	 * Returns productStructureVO for the typeCodeCT passed in.
     * @param typeCodeCT
     * @param includeChildVOs
     * @param voExclusionList
     * @return
     */
    public ProductStructureVO[] findByTypeCode(String typeCodeCT, boolean includeChildVOs, List voExclusionList)
    {
        ProductStructureVO[] productStructureVOs = DAOFactory.getProductStructureDAO().findByTypeCode(typeCodeCT, includeChildVOs, voExclusionList);

        return productStructureVOs;
	}

	public ProductStructureVO[] getProductStructuresByTypeCode(String typeCode)
    {

        ProductStructureVO[] productStructureVO = DAOFactory.getProductStructureDAO().
                findByTypeCode(typeCode, false, null);

        return productStructureVO;
	}

    /**
     * Returns the FeeCorrespondenceVOs that have a Correspondence Date less than or equal to the parameter date.
     * @param notifyCorrDate
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public FeeCorrespondenceVO[] composeFeeCorrespondenceVOByDate(String notifyCorrDate) throws Exception
    {
        return DAOFactory.getFeeCorrespondenceDAO().findByDateLTE(notifyCorrDate);
    }

    /**
     * Finder by ControlBalanceFK
     * Returns the latest ControlBalanceDetail for given ControlBalanceFK
     * Latest = Max(AccountingPeriod) and Max(EffectiveDate) and Max(ValuationDate)
     * @param controlBalanceFK
     * @return
     */
    public ControlBalanceDetailVO findLatestControlBalanceDetailByControlBalanceFK(long controlBalanceFK)
    {
        ControlBalanceDetailVO controlBalanceDetailVO = null;

        ControlBalanceDetailVO[] controlBalanceDetailVOs =  new ControlBalanceDetailDAO().findLatestByControlBalanceFK(controlBalanceFK);

        if (controlBalanceDetailVOs != null)
        {
            controlBalanceDetailVO = controlBalanceDetailVOs[0];
        }

        return controlBalanceDetailVO;
    }

    /**
     * All ProductStructureVOs if companyName = 'All', or those associated with the specified companyName.
     * @param companyName
     * @return
     */
    public ProductStructureVO[] getProductStructureVOs(String companyName)
    {
        ProductStructureVO[] productStructureVOs = null;

        if (companyName.equalsIgnoreCase("All"))
        {
            productStructureVOs = getAllProductStructures();
        }
        else
        {
            productStructureVOs = getAllProductStructuresByCoName(companyName);
        }

        return productStructureVOs;
    }


    /**
     * All ProductStructureVOs if companyName = 'All', or those associated with the specified companyName.
     * @param companyName
     * @return
     */
    public ProductStructureVO[] getProductStructureVOsForTypeProduct(String companyName)
    {
        ProductStructureVO[] productStructureVOs = null;

        if (companyName.equalsIgnoreCase("All"))
        {
            productStructureVOs = findByTypeCode("Product", false, null);
        }
        else
        {
            productStructureVOs = getAllProductStructuresByCoName(companyName);
        }

        return productStructureVOs;
    }

}
