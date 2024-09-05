/*
 * User: unknown
 * Date: Feb 24, 2002
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.ui.servlet;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.exceptions.EDITEngineException;
import edit.common.exceptions.VOEditException;
import edit.common.vo.*;
import edit.portal.common.transactions.Transaction;
import edit.portal.widget.FeeSummaryTableModel;
import engine.business.Calculator;
import engine.business.Lookup;
import engine.component.CalculatorComponent;
import engine.component.LookupComponent;
import engine.ChargeCode;
import engine.FeeFilterRow;
import engine.Fee;
import fission.global.AppReqBlock;
import fission.utility.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import edit.common.EDITDate;

import java.math.BigDecimal;

public class FundTran extends Transaction
{
    // Screen Names
    private static final String FILTERED_FUND_SUMMARY       = "/engine/jsp/filteredFundSummary.jsp";
    private static final String FUND_SUMMARY                = "/engine/jsp/fundSummary.jsp";
    private static final String FILTERED_FUND_RELATIONS     = "/engine/jsp/filteredFundRelations.jsp";
    private static final String VO_EDIT_EXCEPTION_DIALOG    = "/common/jsp/VOEditExceptionDialog.jsp";
    private static final String SUBSCRIPTION_REDEMPTION_INFO_DIALOG = "/engine/jsp/subscriptionRedemptionInfoDialog.jsp";
    private static final String FEE_DESCRIPTION_DIALOG      = "/engine/jsp/feeDescriptionDialog.jsp";
    private static final String FEE_DIALOG                  = "/engine/jsp/feeDialog.jsp";
    private static final String CLIENT_ADD_DIALOG           = "/engine/jsp/clientSelectionDialog.jsp";
    private static final String CORRESPONDENCE_DIALOG       = "/engine/jsp/feeCorrespondenceDialog.jsp";
    private static final String CHARGE_CODE_DIALOG          = "/engine/jsp/chargeCodeDialog.jsp";
    private static final String FEE_FILTER_DIALOG           = "/engine/jsp/feeFilterDialog.jsp";

    // Actions
    private static final String SHOW_FILTERED_FUND_SUMMARY          = "showFilteredFundSummary";
    private static final String SAVE_FILTERED_FUND                  = "saveFilteredFund";
    private static final String SHOW_FILTERED_FUND_DETAILS          = "showFilteredFundDetails";
    private static final String ADD_NEW_FILTERED_FUND               = "addNewFilteredFund";
    private static final String CANCEL_FILTERED_FUND_CHANGES        = "cancelFilteredFundChanges";
    private static final String SHOW_FUND_SUMMARY                   = "showFundSummary";
    private static final String SAVE_FUND                           = "saveFund";
    private static final String SHOW_FUND_DETAILS                   = "showFundDetails";
    private static final String ADD_NEW_FUND                        = "addNewFund";
    private static final String CANCEL_FUND_CHANGES                 = "cancelFundChanges";
    private static final String SHOW_FILTERED_FUND_RELATIONS        = "showFilteredFundRelations";
    private static final String SHOW_ATTACHED_FILTERED_FUNDS        = "showAttachedFilteredFunds";
    private static final String ATTACH_FILTERED_FUND_STRUCTURE      = "attachFilteredFund";
    private static final String DETACH_FILTERED_FUND_STRUCTURE      = "detachFilteredFund";
    private static final String SHOW_VO_EDIT_EXCEPTION_DIALOG       = "showVOEditExceptionDialog";
    private static final String SHOW_SUBSCRIPTION_REDEMPTION_INFO_DIAL0G = "showSubscriptionRedemptionInfoDialog";
    private static final String SAVE_SUBSCRIPTION_REDEMPTION_INFO   = "saveSubscriptionRedemptionInfo";
    private static final String CANCEL_SUBSCRIPTION_REDEMPTION_INFO = "cancelSubscriptionRedemptionInfo";
    private static final String SHOW_FEE_DESCRIPTION_DIALOG         = "showFeeDescriptionDialog";
    private static final String ADD_FEE_DESCRIPTION                 = "addFeeDescription";
    private static final String SAVE_FEE_DESCRIPTION                = "saveFeeDescription";
    private static final String CANCEL_FEE_DESCRIPTION              = "cancelFeeDescription";
    private static final String DELETE_FEE_DESCRIPTION              = "deleteFeeDescription";
    private static final String SHOW_FEE_DIALOG                     = "showFeeDialog";
    private static final String ADD_FEE                             = "addFee";
    private static final String SAVE_FEE                            = "saveFee";
    private static final String CANCEL_FEE                          = "cancelFee";
    private static final String DELETE_FEE                          = "deleteFee";
    private static final String SHOW_CLIENT_ADD_DIALOG              = "showClientAddDialog";
    private static final String SEARCH_CLIENTS_BY_NAME              = "searchClientsByName";
    private static final String SEARCH_CLIENTS_BY_TAXID             = "searchClientsByTaxId";
    private static final String GET_CLIENT_DETAIL_PK                = "getClientDetailPK";
    private static final String SHOW_CORRESPONDENCE_DIALOG          = "showCorrespondenceDialog";
    private static final String SHOW_CHARGE_CODE_DIALOG             = "showChargeCodeDialog";
    private static final String ADD_CHARGE_CODE                     = "addChargeCode";
    private static final String SAVE_CHARGE_CODE                    = "saveChargeCode";
    private static final String CANCEL_CHARGE_CODE                  = "cancelChargeCode";
    private static final String DELETE_CHARGE_CODE                  = "deleteChargeCode";
    private static final String SHOW_FEE_FILTER_DIALOG              = "showFeeFilterDialog";
    private static final String FILTER_FEES                         = "filterFees";

    /**
     * Used to execute transaction
     */
    public String execute(AppReqBlock appReqBlock) throws Exception
    {
        String action = appReqBlock.getReqParm("action");

        if (action.equalsIgnoreCase(SAVE_FILTERED_FUND))
        {
            return saveFilteredFund(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SHOW_FILTERED_FUND_SUMMARY))
        {
            return showFilteredFundSummary(appReqBlock);
        }
        else if (action.equals(SHOW_FILTERED_FUND_DETAILS))
        {
            return showFilteredFundDetails(appReqBlock);
        }
        else if (action.equals(ADD_NEW_FILTERED_FUND))
        {
            return addNewFilteredFund(appReqBlock);
        }
        else if (action.equals(CANCEL_FILTERED_FUND_CHANGES))
        {
            return cancelFilteredFundChanges(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SAVE_FUND))
        {
            return saveFund(appReqBlock);
        }
        else if (action.equalsIgnoreCase(SHOW_FUND_SUMMARY))
        {
            return showFundSummary(appReqBlock);
        }
        else if (action.equals(SHOW_FUND_DETAILS))
        {
            return showFundDetails(appReqBlock);
        }
        else if (action.equals(ADD_NEW_FUND))
        {
            return addNewFund(appReqBlock);
        }
        else if (action.equals(CANCEL_FUND_CHANGES))
        {
            return cancelFundChanges(appReqBlock);
        }
        else if (action.equals(SHOW_FILTERED_FUND_RELATIONS))
        {
            return showFilteredFundRelations(appReqBlock);
        }
        else if (action.equals(SHOW_ATTACHED_FILTERED_FUNDS))
        {
            return showAttachedFilteredFunds(appReqBlock);
        }
        else if (action.equals(ATTACH_FILTERED_FUND_STRUCTURE))
        {
            return attachFilteredFund(appReqBlock);
        }
        else if (action.equals(DETACH_FILTERED_FUND_STRUCTURE))
        {
            return detachFilteredFund(appReqBlock);
        }
        else if (action.equals(SHOW_VO_EDIT_EXCEPTION_DIALOG))
        {
            return showVOEditExceptionDialog(appReqBlock);
        }
        else if (action.equals(SHOW_SUBSCRIPTION_REDEMPTION_INFO_DIAL0G))
        {
            return showSubscriptionRedemptionInfoDialog(appReqBlock);
        }
        else if (action.equals(SAVE_SUBSCRIPTION_REDEMPTION_INFO))
        {
            return saveSubscriptionRedemptionInfo(appReqBlock);
        }
        else if (action.equals(CANCEL_SUBSCRIPTION_REDEMPTION_INFO))
        {
            return cancelSubscriptionRedemptionInfo(appReqBlock);
        }
        else if (action.equals(SHOW_FEE_DESCRIPTION_DIALOG))
        {
            return showFeeDescriptionDialog(appReqBlock);
        }
        else if (action.equals(ADD_FEE_DESCRIPTION))
        {
            return addFeeDescription(appReqBlock);
        }
        else if (action.equals(SAVE_FEE_DESCRIPTION))
        {
            return saveFeeDescription(appReqBlock);
        }
        else if (action.equals(CANCEL_FEE_DESCRIPTION))
        {
            return cancelFeeDescription(appReqBlock);
        }
        else if (action.equals(DELETE_FEE_DESCRIPTION))
        {
            return deleteFeeDescription(appReqBlock);
        }
        else if (action.equals(SHOW_FEE_DIALOG))
        {
            return showFeeDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CLIENT_ADD_DIALOG))
        {
            return showClientAddDialog(appReqBlock);
        }
        else if (action.equals(SEARCH_CLIENTS_BY_NAME))
        {
            return searchClientsByName(appReqBlock);
        }
        else if (action.equals(SEARCH_CLIENTS_BY_TAXID))
        {
            return searchClientsByTaxId(appReqBlock);
        }
        else if (action.equals(GET_CLIENT_DETAIL_PK))
        {
            return getClientDetailPK(appReqBlock);
        }
        else if (action.equals(ADD_FEE))
        {
            return addFee(appReqBlock);
        }
        else if (action.equals(SAVE_FEE))
        {
            return saveFee(appReqBlock);
        }
        else if (action.equals(CANCEL_FEE))
        {
            return cancelFee(appReqBlock);
        }
        else if (action.equals(DELETE_FEE))
        {
            return deleteFee(appReqBlock);
        }
        else if (action.equals(SHOW_CORRESPONDENCE_DIALOG))
        {
            return showCorrespondenceDialog(appReqBlock);
        }
        else if (action.equals(SHOW_CHARGE_CODE_DIALOG))
        {
            return showChargeCodeDialog(appReqBlock);
        }
        else if (action.equals(ADD_CHARGE_CODE))
        {
            return addChargeCode(appReqBlock);
        }
        else if (action.equals(SAVE_CHARGE_CODE))
        {
            return saveChargeCode(appReqBlock);
        }
        else if (action.equals(CANCEL_CHARGE_CODE))
        {
            return cancelChargeCode(appReqBlock);
        }
        else if (action.equals(DELETE_CHARGE_CODE))
        {
            return deleteChargeCode(appReqBlock);
        }
        else if (action.equals(SHOW_FEE_FILTER_DIALOG))
        {
            return showFeeFilterDialog(appReqBlock);
        }
        else if (action.equals(FILTER_FEES))
        {
            return filterFees(appReqBlock);
        }
        else
        {
            throw new Exception("FilteredFundTran: Invalid action " + action);
        }
    }

    protected String saveFilteredFund(AppReqBlock appReqBlock) throws Exception
    {
        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        Calculator calculatorComp = (CalculatorComponent) appReqBlock.getWebService("engine-service");
    
        FilteredFundVO filteredFundVO = saveMainPageFilteredFund(appReqBlock);

        try
        {
            long newFilteredFundPK = calculatorComp.createOrUpdateVONonRecursive(filteredFundVO);
            filteredFundVO.setFilteredFundPK(newFilteredFundPK);
        }
        catch (VOEditException e)
        {
            e.setReturnPage(FILTERED_FUND_SUMMARY);
            throw e;
        }
        finally
        {
            FundVO[] fundVOs = calculatorLookup.getAllFundsNonRecursively();

            FilteredFundVO[] allFilteredFunds = calculatorLookup.getAllFilteredFunds();

            appReqBlock.getHttpSession().setAttribute("selectedFilteredFundVO", filteredFundVO);
            appReqBlock.getHttpSession().setAttribute("filteredFundVOs", allFilteredFunds);
            appReqBlock.getHttpSession().setAttribute("fundVOs", fundVOs);
        }

        return FILTERED_FUND_SUMMARY;
    }

    /**
     * Save the details of the filtered fund
     * @param appReqBlock
     * @return
     */
    private FilteredFundVO saveMainPageFilteredFund(AppReqBlock appReqBlock)
    {
        FilteredFundVO filteredFundVO = (FilteredFundVO) appReqBlock.getHttpSession().getAttribute("selectedFilteredFundVO");
        if (filteredFundVO == null)
    {
            filteredFundVO = new FilteredFundVO();
        }

        String filteredFundPK = Util.initString(appReqBlock.getReqParm("filteredFundPK"), "0");
        String fundFK = appReqBlock.getReqParm("fundFK");

        String effectiveMonth = appReqBlock.getReqParm("effectiveMonth");
        String effectiveDay   = appReqBlock.getReqParm("effectiveDay");
        String effectiveYear  = appReqBlock.getReqParm("effectiveYear");
        String effectiveDate  = DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear, null);

        String terminationMonth = appReqBlock.getReqParm("terminationMonth");
        String terminationDay   = appReqBlock.getReqParm("terminationDay");
        String terminationYear  = appReqBlock.getReqParm("terminationYear");
        String terminationDate  = DateTimeUtil.initDate(terminationMonth, terminationDay, terminationYear, EDITDate.DEFAULT_MAX_DATE);

        String pricingDirection = Util.initString(appReqBlock.getReqParm("pricingDirection"), null);
        String guaranteedDuration = Util.initString(appReqBlock.getReqParm("guaranteedDuration"), "0");
        String indexingMethodCT = Util.initString(appReqBlock.getReqParm("indexingMethodCT"), null);
        String fundAdjustmentCT = Util.initString(appReqBlock.getReqParm("fundAdjustmentCT"), null);
        String indexCapRateGuarPeriod = appReqBlock.getReqParm(("indexCapRateGuarPeriod"));
        String premiumBonusDuration =  appReqBlock.getReqParm("premiumBonusDuration");
        String minimumTransferAmount = appReqBlock.getReqParm("minimumTransferAmount");
        String mvaStartingIndexGuarPeriod = appReqBlock.getReqParm("mvaStartingIndexGuarPeriod");
        String fundNewClientCloseMonth = appReqBlock.getReqParm("fundNewClientCloseMonth");
        String fundNewClientCloseDay   = appReqBlock.getReqParm("fundNewClientCloseDay");
        String fundNewClientCloseYear  = appReqBlock.getReqParm("fundNewClientCloseYear");
        String fundNewClientCloseDate = null;
        if (!fundNewClientCloseMonth.equals(""))
        {
            fundNewClientCloseDate = new EDITDate(fundNewClientCloseYear, fundNewClientCloseMonth, fundNewClientCloseDay).getFormattedDate();
        }
        String annualizedSubBucket = Util.initString(appReqBlock.getReqParm("annualizedSubBucket"), null);
        String category = Util.initString(appReqBlock.getReqParm("category"), null);
        String fundNewDepositCloseMonth = appReqBlock.getReqParm("fundNewDepositCloseMonth");
        String fundNewDepositCloseDay   = appReqBlock.getReqParm("fundNewDepositCloseDay");
        String fundNewDepositCloseYear  = appReqBlock.getReqParm("fundNewDepositCloseYear");
        String fundNewDepositCloseDate = null;
        if (!fundNewDepositCloseMonth.equals(""))
        {
            fundNewDepositCloseDate = new EDITDate(fundNewDepositCloseYear, fundNewDepositCloseMonth, fundNewDepositCloseDay).getFormattedDate();
        }
        String contributionLockUpDuration = Util.initString(appReqBlock.getReqParm("contributionLockUpDuration"), null);
        String divisionLevelLockUpDuration = Util.initString(appReqBlock.getReqParm("divisionLevelLockUpDuration"), null);
        String divisionLockUpEndMonth = appReqBlock.getReqParm("divisionLockUpEndMonth");
        String divisionLockUpEndDay   = appReqBlock.getReqParm("divisionLockUpEndDay");
        String divisionLockUpEndYear  = appReqBlock.getReqParm("divisionLockUpEndYear");
        String divisionLockUpEndDate = null;
        if (!divisionLockUpEndMonth.equals(""))
        {
            divisionLockUpEndDate = new EDITDate(divisionLockUpEndYear, divisionLockUpEndMonth, divisionLockUpEndDay).getFormattedDate();
        }

        String seriesToSeriesEligibilityInd = appReqBlock.getReqParm("seriesToSeriesEligibilityInd");
        String separateAccountName = Util.initString(appReqBlock.getReqParm("separateAccountName"), null);

        String includeInCorrespondenceInd = appReqBlock.getReqParm("includeInCorrespondenceInd");
        if (includeInCorrespondenceInd != null)
        {
            includeInCorrespondenceInd = "Y";
        }

        if (indexingMethodCT.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setIndexingMethodCT(null);
        }
        else
        {
            filteredFundVO.setIndexingMethodCT(indexingMethodCT);
        }

        if (fundAdjustmentCT.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setFundAdjustmentCT(null);
        }
        else
        {
            filteredFundVO.setFundAdjustmentCT(fundAdjustmentCT);
        }

        if (!fundFK.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setFundFK(Long.parseLong(fundFK));
        }

        filteredFundVO.setFilteredFundPK(Long.parseLong(filteredFundPK));
        filteredFundVO.setFundNumber(appReqBlock.getReqParm("fundNumber"));
        filteredFundVO.setEffectiveDate(effectiveDate);
        filteredFundVO.setTerminationDate(terminationDate);

        if (pricingDirection.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setPricingDirection(null);
        }
        else
        {
            filteredFundVO.setPricingDirection(pricingDirection);
        }

        if (annualizedSubBucket.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setAnnualSubBucketCT(null);
        }
        else
        {
            filteredFundVO.setAnnualSubBucketCT(annualizedSubBucket);
        }

        if (category.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setCategoryCT(null);
        }
        else
        {
            filteredFundVO.setCategoryCT(category);
        }

        filteredFundVO.setGuaranteedDuration(Integer.parseInt(guaranteedDuration));

        if (Util.isANumber(indexCapRateGuarPeriod))
        {
            filteredFundVO.setIndexCapRateGuarPeriod(Integer.parseInt(indexCapRateGuarPeriod));
        }

        if (Util.isANumber(premiumBonusDuration))
        {
            filteredFundVO.setPremiumBonusDuration(Integer.parseInt(premiumBonusDuration));
        }

        if (Util.isANumber(minimumTransferAmount))
        {
            filteredFundVO.setMinimumTransferAmount(new EDITBigDecimal(minimumTransferAmount).getBigDecimal());
        }

        if (Util.isANumber(mvaStartingIndexGuarPeriod))
        {
            filteredFundVO.setMVAStartingIndexGuarPeriod(Integer.parseInt(mvaStartingIndexGuarPeriod));
        }

        if (fundNewClientCloseDate != null)
        {
            filteredFundVO.setFundNewClientCloseDate(fundNewClientCloseDate);
        }
        if (fundNewDepositCloseDate != null)
        {
            filteredFundVO.setFundNewDepositCloseDate(fundNewDepositCloseDate);
        }
        if (divisionLockUpEndDate != null)
        {
            filteredFundVO.setDivisionLockUpEndDate(divisionLockUpEndDate);
        }
        if (contributionLockUpDuration != null)
        {
            filteredFundVO.setContributionLockUpDuration(Integer.parseInt(contributionLockUpDuration));
        }
        if (divisionLevelLockUpDuration != null)
        {
            filteredFundVO.setDivisionLevelLockUpDuration(Integer.parseInt(divisionLevelLockUpDuration));
        }

        filteredFundVO.setSeparateAccountName(separateAccountName);
        filteredFundVO.setIncludeInCorrespondenceInd(includeInCorrespondenceInd);
        filteredFundVO.setPostLockWithdrawalDateCT(Util.initString(appReqBlock.getReqParm("postLockWithdrawalDateCT"), null));

        filteredFundVO.setSeriesToSeriesEligibilityInd(seriesToSeriesEligibilityInd);

        return filteredFundVO;
    }


    protected String showFilteredFundSummary(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("filteredFundVOs");
        appReqBlock.getHttpSession().removeAttribute("fundVOs");
        appReqBlock.getHttpSession().removeAttribute("selectedFilteredFundVO");

        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        FilteredFundVO[] filteredFundVOs = calculatorLookup.getAllFilteredFunds();

        FundVO[] fundVOs = calculatorLookup.getAllFundsNonRecursively();

        appReqBlock.getHttpSession().setAttribute("filteredFundVOs", filteredFundVOs);
        appReqBlock.getHttpSession().setAttribute("fundVOs", fundVOs);

        return FILTERED_FUND_SUMMARY;
    }

    protected String showFilteredFundDetails(AppReqBlock appReqBlock) throws Exception
    {
        long filteredFundPK = Long.parseLong(appReqBlock.getReqParm("filteredFundPK"));

        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        List voExclusionList = new ArrayList();
        voExclusionList.add(UnitValuesVO.class);
        voExclusionList.add(InterestRateParametersVO.class);
        voExclusionList.add(ProductFilteredFundStructureVO.class);
        voExclusionList.add(FeeVO.class);
        voExclusionList.add(FeeDescriptionVO.class);
        voExclusionList.add(ChargeCodeVO.class);


        FilteredFundVO[] filteredFundVO =  calculatorLookup.findFilteredFundVOsByPK(filteredFundPK,
                                                                         true,
                                                                          voExclusionList);

        appReqBlock.getHttpSession().setAttribute("selectedFilteredFundVO", filteredFundVO[0]);

        return FILTERED_FUND_SUMMARY;
    }

    protected String addNewFilteredFund(AppReqBlock appReqBlock) throws Exception
    {
        return showFilteredFundSummary(appReqBlock);
    }

    protected String cancelFilteredFundChanges(AppReqBlock appReqBlock) throws Exception
    {
        return showFilteredFundSummary(appReqBlock);
    }

    protected String saveFund(AppReqBlock appReqBlock) throws Exception
    {
        Calculator calculatorComp = (CalculatorComponent) appReqBlock.getWebService("engine-service");

        FundVO fundVO = new FundVO();

        String fundPK = appReqBlock.getReqParm("fundPK");

        if (fundPK.equals(""))
        {
            fundVO.setFundPK(0);
        }
        else
        {
            fundVO.setFundPK(Long.parseLong(fundPK));
        }

        String fundType = appReqBlock.getReqParm("fundType");
        CodeTableVO codeTableVO = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(fundType));

        String portfolio = Util.initString(appReqBlock.getReqParm("portfolio"), null);
        if (portfolio != null && portfolio.equalsIgnoreCase("Please Select"))
        {
            portfolio = null;
        }

        fundVO.setName(appReqBlock.getReqParm("fundName"));
        fundVO.setFundType(codeTableVO.getCode());
        fundVO.setPortfolioNewMoneyStatusCT(portfolio);
        fundVO.setShortName(appReqBlock.getReqParm("shortName"));
        fundVO.setExcludeFromActivityFileInd(appReqBlock.getReqParm("excludeFromActivityFileInd"));
        fundVO.setTypeCodeCT(appReqBlock.getReqParm("typeCodeSelect"));
        if (!appReqBlock.getReqParm("reportingFundName").equals(""))
        {
            fundVO.setReportingFundName(appReqBlock.getReqParm("reportingFundName"));
        }

        String loanQualifier = Util.initString(appReqBlock.getReqParm("loanQualifierCT"), null);
        if (loanQualifier != null && loanQualifier.equalsIgnoreCase("Please Select"))
        {
            loanQualifier = null;
        }
        fundVO.setLoanQualifierCT(loanQualifier);

        calculatorComp.createOrUpdateVONonRecursive(fundVO);

        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        FundVO[] allFundVOs = calculatorLookup.findAllFundVOs(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("selectedFundVO", fundVO);
        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", allFundVOs);

        return FUND_SUMMARY;
    }

    protected String showFundSummary(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("filteredFundVOs");
        appReqBlock.getHttpSession().removeAttribute("fundVOs");
        appReqBlock.getHttpSession().removeAttribute("selectedFilteredFundVO");

        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        FundVO[] allFundVOs = calculatorLookup.findAllFundVOs(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", allFundVOs);

        return FUND_SUMMARY;
    }

    protected String showFundDetails(AppReqBlock appReqBlock) throws Exception
    {
        long fundPK = Long.parseLong(appReqBlock.getReqParm("fundPK"));

        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        FundVO[] fundVO = calculatorLookup.getFundByFundPK(fundPK, false, null);

        FundVO[] allFundVOs = calculatorLookup.findAllFundVOs(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("selectedFundVO", fundVO[0]);
        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", allFundVOs);

        return FUND_SUMMARY;
    }

    protected String addNewFund(AppReqBlock appReqBlock) throws Exception
    {
        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        FundVO[] allFundVOs = calculatorLookup.findAllFundVOs(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", allFundVOs);

        return FUND_SUMMARY;
    }

    protected String cancelFundChanges(AppReqBlock appReqBlock) throws Exception
    {
        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        FundVO[] allFundVOs = calculatorLookup.findAllFundVOs(false, null);

        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", allFundVOs);

        return FUND_SUMMARY;
    }

    protected String showFilteredFundRelations(AppReqBlock appReqBlock) throws Exception
    {
        appReqBlock.getHttpSession().removeAttribute("filteredFundVOs");
        appReqBlock.getHttpSession().removeAttribute("fundVOs");

        populateRequestWithProductFilteredFundVOs(appReqBlock);

        return FILTERED_FUND_RELATIONS;
    }

   protected String showAttachedFilteredFunds(AppReqBlock appReqBlock) throws Exception
   {
        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        long selectedProductStructurePK = Long.parseLong(appReqBlock.getReqParm("selectedProductStructurePK"));

        FilteredFundVO[] attachedFilteredFundVOs = calculatorLookup.findAttachedFilteredFundVOs(selectedProductStructurePK, false, null);

        appReqBlock.getHttpServletRequest().setAttribute("attachedFilteredFundVOs", attachedFilteredFundVOs);
        appReqBlock.getHttpServletRequest().setAttribute("selectedProductStructurePK", selectedProductStructurePK + "");

        populateRequestWithProductFilteredFundVOs(appReqBlock);

        return FILTERED_FUND_RELATIONS;
    }

    protected String attachFilteredFund(AppReqBlock appReqBlock) throws Exception
    {
        String productStructurePK = initParam(appReqBlock.getReqParm("selectedProductStructurePK"), "0");

        if (productStructurePK.equals("0"))
        {
            String message = "Product Structure Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            populateRequestWithProductFilteredFundVOs(appReqBlock);

            return FILTERED_FUND_RELATIONS;
        }

        String selectedFilteredFundPKs = initParam(appReqBlock.getReqParm("selectedFilteredFundPKs"), null);

        if (selectedFilteredFundPKs == null)
        {
            String message = "Filtered Funds Reqired";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            populateRequestWithProductFilteredFundVOs(appReqBlock);

            return FILTERED_FUND_RELATIONS;
        }

        String[] filteredFundsToAttachTokens = Util.fastTokenizer(selectedFilteredFundPKs, ",");

        List filteredFundsToAttach = new ArrayList();

        for (int i = 0; i < filteredFundsToAttachTokens.length; i++)
        {
            if (Util.isANumber(filteredFundsToAttachTokens[i]))
            {
                filteredFundsToAttach.add(new Long(filteredFundsToAttachTokens[i]));
            }
        }

        Calculator calculatorComp = (CalculatorComponent) appReqBlock.getWebService("engine-service");
        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        calculatorComp.attachFilteredFundToProduct(Long.parseLong(productStructurePK), Util.convertLongToPrim((Long[]) filteredFundsToAttach.toArray(new Long[filteredFundsToAttach.size()])));

        FilteredFundVO[] attachedFilteredFundVOs = calculatorLookup.findAttachedFilteredFundVOs(Long.parseLong(productStructurePK), false, null);

        appReqBlock.getHttpServletRequest().setAttribute("attachedFilteredFundVOs", attachedFilteredFundVOs);
        appReqBlock.getHttpServletRequest().setAttribute("selectedProductStructurePK", productStructurePK + "");

        populateRequestWithProductFilteredFundVOs(appReqBlock);

        return FILTERED_FUND_RELATIONS;
    }

  protected String detachFilteredFund(AppReqBlock appReqBlock) throws Exception
  {
        String productStructurePK = initParam(appReqBlock.getReqParm("selectedProductStructurePK"), "0");

        if (productStructurePK.equals("0"))
        {
            String message = "Product Structure Required";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            populateRequestWithProductFilteredFundVOs(appReqBlock);

            return FILTERED_FUND_RELATIONS;
        }

        String selectedFilteredFundPKs = initParam(appReqBlock.getReqParm("selectedFilteredFundPKs"), null);

        if (selectedFilteredFundPKs == null)
        {
            String message = "Filtered Funds Reqired";

            appReqBlock.getHttpServletRequest().setAttribute("message", message);

            populateRequestWithProductFilteredFundVOs(appReqBlock);

            return FILTERED_FUND_RELATIONS;
        }

        String[] filteredFundsToDetachTokens = Util.fastTokenizer(selectedFilteredFundPKs, ",");

        List filteredFundsToAttach = new ArrayList();

        for (int i = 0; i < filteredFundsToDetachTokens.length; i++)
        {
            if (Util.isANumber(filteredFundsToDetachTokens[i]))
            {
                filteredFundsToAttach.add(new Long(filteredFundsToDetachTokens[i]));
            }
        }

        Calculator calculatorComp = (CalculatorComponent) appReqBlock.getWebService("engine-service");
        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        calculatorComp.detachFilteredFundFromProduct(Long.parseLong(productStructurePK), Util.convertLongToPrim((Long[]) filteredFundsToAttach.toArray(new Long[filteredFundsToAttach.size()])));

        FilteredFundVO[] attachedFilteredFundVOs = calculatorLookup.findAttachedFilteredFundVOs(Long.parseLong(productStructurePK), false, null);

        appReqBlock.getHttpServletRequest().setAttribute("attachedFilteredFundVOs", attachedFilteredFundVOs);
        appReqBlock.getHttpServletRequest().setAttribute("selectedProductStructurePK", productStructurePK + "");

        populateRequestWithProductFilteredFundVOs(appReqBlock);

        return FILTERED_FUND_RELATIONS;
    }

    private void populateRequestWithProductFilteredFundVOs(AppReqBlock appReqBlock) throws Exception
    {
        Lookup calculatorLookup = (Lookup) appReqBlock.getWebService("engine-lookup");

        FilteredFundVO[] filteredFundVOs = calculatorLookup.getAllFilteredFundVOs(false, null);

        FundVO[] fundVOs = calculatorLookup.getAllFundsNonRecursively();

        ProductStructureVO[] productStructureVOs = calculatorLookup.findByTypeCode("Product", false, null);

        appReqBlock.getHttpServletRequest().setAttribute("productStructureVOs", productStructureVOs);
        appReqBlock.getHttpServletRequest().setAttribute("filteredFundVOs", filteredFundVOs );
        appReqBlock.getHttpServletRequest().setAttribute("fundVOs", fundVOs);
    }

    protected String showVOEditExceptionDialog(AppReqBlock appReqBlock) throws Exception
    {
//        VOEditException voEditException = (VOEditException) appReqBlock.getHttpSession().getAttribute("VOEditException");
//
//        // Remove voEditException from Session (to clear it), and move it to request scope.
//        appReqBlock.getHttpSession().removeAttribute("VOEditException");
//
//        appReqBlock.getHttpServletRequest().setAttribute("VOEditException", voEditException);

        return VO_EDIT_EXCEPTION_DIALOG;
    }

    /**
     * This method will display the new Subscription/Redemption Information dialog
     * @param appReqBlock
     * @return /engine/jsp/subscriptionRedemptionInfoDialog.jsp
     * @throws Exception
     */
    protected String showSubscriptionRedemptionInfoDialog(AppReqBlock appReqBlock) throws Exception
    {
        FilteredFundVO filteredFundVO = saveMainPageFilteredFund(appReqBlock);

        appReqBlock.getHttpSession().setAttribute("selectedFilteredFundVO", filteredFundVO);

        return SUBSCRIPTION_REDEMPTION_INFO_DIALOG;
    }


    /**
     * Saves the modified/new subscription/redemtpion information and closes the subscription/redemption dialog
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String saveSubscriptionRedemptionInfo(AppReqBlock appReqBlock) throws Exception
    {
        FilteredFundVO filteredFundVO = (FilteredFundVO) appReqBlock.getHttpSession().getAttribute("selectedFilteredFundVO");
        if (filteredFundVO == null)
        {
            filteredFundVO = new FilteredFundVO();
        }

        int subscriptionNotificationDays = Integer.parseInt(appReqBlock.getReqParm("subscriptionNotificationDays"));
        int coiReplenishmentDays = Integer.parseInt(appReqBlock.getReqParm("coiReplenishmentDays"));
        int deathDays = Integer.parseInt(appReqBlock.getReqParm("deathDays"));
        int fullSurrenderDays = Integer.parseInt(appReqBlock.getReqParm("fullSurrenderDays"));
        int withdrawalDays = Integer.parseInt(appReqBlock.getReqParm("withdrawalDays"));
        int transferDays = Integer.parseInt(appReqBlock.getReqParm("transferDays"));
        int loanDays = Integer.parseInt(appReqBlock.getReqParm("loanDays"));
        int seriesTransferDays = Integer.parseInt(appReqBlock.getReqParm("seriesTransferDays"));
        int divFeesLiquidationDays = Integer.parseInt(appReqBlock.getReqParm("divFeesLiquidationDays"));
        String subscriptionModeCT = appReqBlock.getReqParm("subscriptionModeCT");
        String subscriptionDaysTypeCT = appReqBlock.getReqParm("subscriptionDaysTypeCT");
        String coiReplenishmentModeCT = appReqBlock.getReqParm("coiReplenishmentModeCT");
        String coiReplenishmentDaysTypeCT = appReqBlock.getReqParm("coiReplenishmentDaysTypeCT");
        String deathModeCT = appReqBlock.getReqParm("deathModeCT");
        String deathDaysTypeCT = appReqBlock.getReqParm("deathDaysTypeCT");
        String fullSurrenderModeCT = appReqBlock.getReqParm("fullSurrenderModeCT");
        String fullSurrenderDaysTypeCT = appReqBlock.getReqParm("fullSurrenderDaysTypeCT");
        String withdrawalModeCT = appReqBlock.getReqParm("withdrawalModeCT");
        String withdrawalDaysTypeCT = appReqBlock.getReqParm("withdrawalDaysTypeCT");
        String transferModeCT = appReqBlock.getReqParm("transferModeCT");
        String transferDaysTypeCT = appReqBlock.getReqParm("transferDaysTypeCT");
        String loanModeCT = appReqBlock.getReqParm("loanModeCT");
        String loanDaysTypeCT = appReqBlock.getReqParm("loanDaysTypeCT");
        String seriesTransferModeCT = appReqBlock.getReqParm("seriesTransferModeCT");
        String seriesTransferDaysTypeCT = appReqBlock.getReqParm("seriesTransferDaysTypeCT");
        String divisionLiquidationModeCT = Util.initString(appReqBlock.getReqParm("divisionFeesLiquidationModeCT"), "");
        String divFeesLiquidationDaysTypeCT = Util.initString(appReqBlock.getReqParm("divFeesLiquidationDaysTypeCT"), "");
//        String specialMonth = appReqBlock.getReqParm("specialMonth");
//        String specialDay = appReqBlock.getReqParm("specialDay");
        String holdingAccountFK = appReqBlock.getReqParm("holdingAccountFK");

        filteredFundVO.setSubscriptionNotificationDays(subscriptionNotificationDays);
        filteredFundVO.setCOIReplenishmentDays(coiReplenishmentDays);
        filteredFundVO.setDeathDays(deathDays);
        filteredFundVO.setFullSurrenderDays(fullSurrenderDays);
        filteredFundVO.setWithdrawalDays(withdrawalDays);
        filteredFundVO.setTransferDays(transferDays);
        filteredFundVO.setLoanDays(loanDays);
        filteredFundVO.setSeriesTransferDays(seriesTransferDays);
        filteredFundVO.setDivisionFeesLiquidationDays(divFeesLiquidationDays);
        if (!subscriptionModeCT.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setSubscriptionModeCT(subscriptionModeCT);
        }

        if (!subscriptionDaysTypeCT.equalsIgnoreCase("Please Select"))
        {
            filteredFundVO.setSubscriptionDaysTypeCT(subscriptionDaysTypeCT);
        }

        if (!coiReplenishmentModeCT.equals("Please Select"))
        {
            filteredFundVO.setCOIReplenishmentModeCT(coiReplenishmentModeCT);
        }

        if (!coiReplenishmentDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setCOIReplenishmentDaysTypeCT(coiReplenishmentDaysTypeCT);
        }

        if (!deathModeCT.equals("Please Select"))
        {
            filteredFundVO.setDeathModeCT(deathModeCT);
        }

        if (!deathDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setDeathDaysTypeCT(deathDaysTypeCT);
        }

        if (!fullSurrenderModeCT.equals("Please Select"))
        {
            filteredFundVO.setFullSurrenderModeCT(fullSurrenderModeCT);
        }

        if (!fullSurrenderDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setFullSurrenderDaysTypeCT(fullSurrenderDaysTypeCT);
        }

        if (!withdrawalModeCT.equals("Please Select"))
        {
            filteredFundVO.setWithdrawalModeCT(withdrawalModeCT);
        }

        if (!withdrawalDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setWithdrawalDaysTypeCT(withdrawalDaysTypeCT);
        }

        if (!transferModeCT.equals("Please Select"))
        {
            filteredFundVO.setTransferModeCT(transferModeCT);
        }

        if (!transferDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setTransferDaysTypeCT(transferDaysTypeCT);
        }

        if (!loanModeCT.equals("Please Select"))
        {
            filteredFundVO.setLoanModeCT(loanModeCT);
        }

        if (!loanDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setLoanDaysTypeCT(loanDaysTypeCT);
        }

        if (!seriesTransferModeCT.equals("Please Select"))
        {
            filteredFundVO.setSeriesTransferModeCT(seriesTransferModeCT);
        }

        if (!seriesTransferDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setSeriesTransferDaysTypeCT(seriesTransferDaysTypeCT);
        }

        if (!divisionLiquidationModeCT.equals("Please Select"))
        {
            filteredFundVO.setDivisionFeesLiquidationModeCT(divisionLiquidationModeCT);
        }

        if (!divFeesLiquidationDaysTypeCT.equals("Please Select"))
        {
            filteredFundVO.setDivFeesLiquidatnDaysTypeCT(divFeesLiquidationDaysTypeCT);
        }

        if (holdingAccountFK != null && !holdingAccountFK.equals("Please Select"))
        {
            filteredFundVO.setHoldingAccountFK(Long.parseLong(holdingAccountFK));
        }

        appReqBlock.getHttpSession().setAttribute("selectedFilteredFundVO", filteredFundVO);

        return FILTERED_FUND_SUMMARY;
    }

    /**
     * Closes the subscription/redemption dialog without saving any changes.
     * @param appReqBlock
     * @return
     * @throws Exception
     */
    protected String cancelSubscriptionRedemptionInfo(AppReqBlock appReqBlock) throws Exception
    {
        return FILTERED_FUND_SUMMARY;
    }

    protected String showFeeDescriptionDialog(AppReqBlock appReqBlock)
    {
        FilteredFundVO filteredFundVO = saveMainPageFilteredFund(appReqBlock);

        String filteredFundPK = appReqBlock.getReqParm("filteredFundPK");
        String feeDescriptionPK = appReqBlock.getReqParm("feeDescriptionPK");

        Lookup calculatorLookup = new LookupComponent();

        FeeDescriptionVO feeDescriptionVO = null;

        if (feeDescriptionPK != null && Long.parseLong(feeDescriptionPK) != 0)
        {
            feeDescriptionVO = calculatorLookup.findFeeDescriptionBy_FeeDescriptionPK(Long.parseLong(feeDescriptionPK));
        }

        FeeDescriptionVO[] feeDescriptionVOs =
                calculatorLookup.findFeeDescriptionBy_FilteredFundPK(Long.parseLong(filteredFundPK));

        appReqBlock.getHttpSession().setAttribute("selectedFilteredFundVO", filteredFundVO);

        appReqBlock.getHttpServletRequest().setAttribute("feeDescriptionVO", feeDescriptionVO);
        appReqBlock.getHttpServletRequest().setAttribute("feeDescriptionVOs", feeDescriptionVOs);

        appReqBlock.getHttpServletRequest().setAttribute("filteredFundPK", filteredFundPK);
        appReqBlock.getHttpServletRequest().setAttribute("fundNumber", appReqBlock.getReqParm("fundNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("fundName", appReqBlock.getReqParm("fundName"));

        return FEE_DESCRIPTION_DIALOG;
    }

    protected String showFeeDescriptionDetail(AppReqBlock appReqBlock)
    {
        String filteredFundPK = appReqBlock.getReqParm("filteredFundPK");
        String feeDescriptionPK = appReqBlock.getReqParm("feeDescriptionPK");

        Lookup calculatorLookup = new LookupComponent();

        FeeDescriptionVO feeDescriptionVO = null;

        if (feeDescriptionPK != null && Long.parseLong(feeDescriptionPK) != 0)
        {
            feeDescriptionVO = calculatorLookup.findFeeDescriptionBy_FeeDescriptionPK(Long.parseLong(feeDescriptionPK));
        }

        FeeDescriptionVO[] feeDescriptionVOs =
                calculatorLookup.findFeeDescriptionBy_FilteredFundPK(Long.parseLong(filteredFundPK));

        appReqBlock.getHttpServletRequest().setAttribute("feeDescriptionVO", feeDescriptionVO);
        appReqBlock.getHttpServletRequest().setAttribute("feeDescriptionVOs", feeDescriptionVOs);

        appReqBlock.getHttpServletRequest().setAttribute("filteredFundPK", filteredFundPK);
        appReqBlock.getHttpServletRequest().setAttribute("fundNumber", appReqBlock.getReqParm("fundNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("fundName", appReqBlock.getReqParm("fundName"));

        return FEE_DESCRIPTION_DIALOG;
    }

    protected String addFeeDescription(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("feeDescriptionPK", null);

        return showFeeDescriptionDialog(appReqBlock);
    }

    protected String saveFeeDescription(AppReqBlock appReqBlock)
    {
        String responseMessage = null;

        String feeDescriptionPK = Util.initString(appReqBlock.getReqParm("feeDescriptionPK"),"0");
        String filteredFundFK   = appReqBlock.getReqParm("filteredFundPK");
        String feeTypeCT        = appReqBlock.getReqParm("feeTypeCT");
        String pricingTypeCT    = appReqBlock.getReqParm("pricingTypeCT");
        String feeRedemption    = appReqBlock.getReqParm("feeRedemptionStatus");
        String clientDetailPK   = appReqBlock.getReqParm("clientDetailPK");

        FeeDescriptionVO feeDescriptionVO = new FeeDescriptionVO();
        feeDescriptionVO.setFeeDescriptionPK(Long.parseLong(feeDescriptionPK));
        feeDescriptionVO.setFilteredFundFK(Long.parseLong(filteredFundFK));
        feeDescriptionVO.setFeeTypeCT(feeTypeCT);
        feeDescriptionVO.setFeeRedemption(feeRedemption);
        feeDescriptionVO.setPricingTypeCT(pricingTypeCT);
        feeDescriptionVO.setClientDetailFK(Long.parseLong(clientDetailPK));

        Calculator calculatorComponent = new CalculatorComponent();

        try
        {
            calculatorComponent.saveFeeDescription(feeDescriptionVO);

            appReqBlock.setReqParm("feeDescriptionPK", null);
        }
        catch (EDITEngineException e)
        {
            responseMessage = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

            appReqBlock.setReqParm("feeDescriptionPK", feeDescriptionPK);
        }

        return showFeeDescriptionDialog(appReqBlock);
    }

    protected String cancelFeeDescription(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("feeDescriptionPK", null);

        return showFeeDescriptionDialog(appReqBlock);
    }

    protected String deleteFeeDescription(AppReqBlock appReqBlock)
    {
        String responseMessage = null;

        String feeDescriptionPK = appReqBlock.getReqParm("feeDescriptionPK");

        Calculator calculatorComponent = new CalculatorComponent();

        try
        {
            calculatorComponent.deleteFeeDescription(Long.parseLong(feeDescriptionPK));

            appReqBlock.setReqParm("feeDescriptionPK", null);
        }
        catch (EDITEngineException e)
        {
            responseMessage = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

            appReqBlock.setReqParm("feeDescriptionPK", feeDescriptionPK);
        }

        return showFeeDescriptionDialog(appReqBlock);
    }

    protected String showClientAddDialog(AppReqBlock appReqBlock)
    {
        String clientDetailPK = appReqBlock.getReqParm("clientDetailPK");

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs =
                clientLookup.findClientDetailByClientPK(Long.parseLong(clientDetailPK), false, null);

        ClientDetailVO clientDetailVO = null;

        if (clientDetailVOs != null)
        {
            clientDetailVO = clientDetailVOs[0];
        }

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailVO", clientDetailVO);

        setRequestParameters(appReqBlock);

        return CLIENT_ADD_DIALOG;
    }

    protected String searchClientsByName(AppReqBlock appReqBlock)
    {
        String name = appReqBlock.getReqParm("name");

        ClientDetailVO[] clientDetailVOs = null;

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        clientDetailVOs = clientLookup.findClientDetailBy_Name(name);

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailVOs", clientDetailVOs);

        return showClientAddDialog(appReqBlock);
    }

    protected String searchClientsByTaxId(AppReqBlock appReqBlock)
    {
        String taxId = appReqBlock.getReqParm("taxId");

        ClientDetailVO[] clientDetailVOs = null;

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        clientDetailVOs = clientLookup.findClientDetailBy_TaxId(taxId);

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailVOs", clientDetailVOs);

        return showClientAddDialog(appReqBlock);
    }

    protected String getClientDetailPK(AppReqBlock appReqBlock)
    {
        setRequestParameters(appReqBlock);

        appReqBlock.getHttpServletRequest().setAttribute("clientDetailPK", appReqBlock.getReqParm("clientDetailPK"));

        appReqBlock.getHttpServletRequest().setAttribute("isFromClientPage", "true");

        return showFeeDescriptionDialog(appReqBlock);
    }

    private void setRequestParameters(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpServletRequest().setAttribute("filteredFundPK", appReqBlock.getReqParm("filteredFundPK"));
        appReqBlock.getHttpServletRequest().setAttribute("fundNumber", appReqBlock.getReqParm("fundNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("fundName", appReqBlock.getReqParm("fundName"));
        appReqBlock.getHttpServletRequest().setAttribute("feeDescriptionPK", appReqBlock.getReqParm("feeDescriptionPK"));
        appReqBlock.getHttpServletRequest().setAttribute("feeTypeCT", appReqBlock.getReqParm("feeTypeCT"));
        appReqBlock.getHttpServletRequest().setAttribute("pricingTypeCT", appReqBlock.getReqParm("pricingTypeCT"));
        appReqBlock.getHttpServletRequest().setAttribute("feeRedemptionStatus", appReqBlock.getReqParm("feeRedemptionStatus"));
    }

    protected String showFeeDialog(AppReqBlock appReqBlock)
    {
        FilteredFundVO filteredFundVO = saveMainPageFilteredFund(appReqBlock);

        String filteredFundPKStr = appReqBlock.getReqParm("filteredFundPK");
        long filteredFundPK = Long.parseLong(filteredFundPKStr);

        getFeeDescriptions(appReqBlock, Long.parseLong(filteredFundPKStr));

        String key = appReqBlock.getReqParm("selectedRowIds_FeeSummaryTableModel");
        if (key != null && !key.equals(""))
        {
            Long feePK = FeeFilterRow.getPKFromKey(key);
            Fee fee = Fee.findByPK(feePK.longValue());
            appReqBlock.getHttpServletRequest().setAttribute("feeVO", (FeeVO) fee.getVO());
        }

        String filterPeriod = Util.initString(appReqBlock.getReqParm("filterPeriod"), null);
        String filterDateType = Util.initString(appReqBlock.getReqParm("filterDateType"), "EffectiveDate");
        String fromDate = Util.initString(appReqBlock.getReqParm("fromDate"), null);
        String toDate = Util.initString(appReqBlock.getReqParm("toDate"), null);
        String fromAmount = Util.initString(appReqBlock.getReqParm("fromAmount"), "0");
        String toAmount = Util.initString(appReqBlock.getReqParm("toAmount"), "0");
        String filterTransaction = Util.initString(appReqBlock.getReqParm("filterTransaction"), null);
        String sortByDateType = Util.initString(appReqBlock.getReqParm("sortByDateType"), "EffectiveDate");
        String sortOrder = Util.initString(appReqBlock.getReqParm("sortOrder"), "Ascending");

        appReqBlock.getHttpServletRequest().setAttribute("filterPeriod", filterPeriod);
        appReqBlock.getHttpServletRequest().setAttribute("filterDateType", filterDateType);
        appReqBlock.getHttpServletRequest().setAttribute("fromDate", fromDate);
        appReqBlock.getHttpServletRequest().setAttribute("toDate", toDate);
        appReqBlock.getHttpServletRequest().setAttribute("fromAmount", fromAmount);
        appReqBlock.getHttpServletRequest().setAttribute("toAmount", toAmount);
        appReqBlock.getHttpServletRequest().setAttribute("filterTransaction", filterTransaction);
        appReqBlock.getHttpServletRequest().setAttribute("sortByDateType", sortByDateType);
        appReqBlock.getHttpServletRequest().setAttribute("sortOrder", sortOrder);

        ChargeCodeVO[] chargeCodeVOs = getSortedChargeCodeVOs(filteredFundPK);

        appReqBlock.getHttpSession().setAttribute("selectedFilteredFundVO", filteredFundVO);

        appReqBlock.getHttpServletRequest().setAttribute("filteredFundPK", filteredFundPKStr);
        appReqBlock.getHttpServletRequest().setAttribute("fundNumber", appReqBlock.getReqParm("fundNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("fundName", appReqBlock.getReqParm("fundName"));
        appReqBlock.getHttpServletRequest().setAttribute("chargeCodeVOs", chargeCodeVOs);

        new FeeSummaryTableModel(appReqBlock);

        return FEE_DIALOG;
    }

    private String showFeeFilterDialog(AppReqBlock appReqBlock)
    {
        appReqBlock.getHttpServletRequest().setAttribute("filteredFundPK", appReqBlock.getReqParm("filteredFundPK"));
        appReqBlock.getHttpServletRequest().setAttribute("fundNumber", appReqBlock.getReqParm("fundNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("fundName", appReqBlock.getReqParm("fundName"));

        return FEE_FILTER_DIALOG;
    }

    private String filterFees(AppReqBlock appReqBlock)
    {
        String filteredFundPKStr = appReqBlock.getReqParm("filteredFundPK");
        getFeeDescriptions(appReqBlock, Long.parseLong(filteredFundPKStr));

        String filterPeriod = Util.initString(appReqBlock.getReqParm("filterPeriod"), null);
        String filterDateType = Util.initString(appReqBlock.getReqParm("filterDateType"), "EffectiveDate");
        String fromDate = Util.initString(appReqBlock.getReqParm("fromDate"), null);
        String toDate = Util.initString(appReqBlock.getReqParm("toDate"), null);
        String fromAmount = Util.initString(appReqBlock.getReqParm("fromAmount"), "0");
        String toAmount = Util.initString(appReqBlock.getReqParm("toAmount"), "0");
        String filterTransaction = Util.initString(appReqBlock.getReqParm("filterTransaction"), null);
        String sortByDateType = Util.initString(appReqBlock.getReqParm("sortByDateType"), "EffectiveDate");
        String sortOrder = Util.initString(appReqBlock.getReqParm("sortOrder"), "Ascending");

        appReqBlock.getHttpServletRequest().setAttribute("filterPeriod", filterPeriod);
        appReqBlock.getHttpServletRequest().setAttribute("filterDateType", filterDateType);
        appReqBlock.getHttpServletRequest().setAttribute("fromDate", fromDate);
        appReqBlock.getHttpServletRequest().setAttribute("toDate", toDate);
        appReqBlock.getHttpServletRequest().setAttribute("fromAmount", fromAmount);
        appReqBlock.getHttpServletRequest().setAttribute("toAmount", toAmount);
        appReqBlock.getHttpServletRequest().setAttribute("filterTransaction", filterTransaction);
        appReqBlock.getHttpServletRequest().setAttribute("sortByDateType", sortByDateType);
        appReqBlock.getHttpServletRequest().setAttribute("sortOrder", sortOrder);

        new FeeSummaryTableModel(appReqBlock);
        
        appReqBlock.getHttpServletRequest().setAttribute("filteredFundPK", appReqBlock.getReqParm("filteredFundPK"));
        appReqBlock.getHttpServletRequest().setAttribute("fundNumber", appReqBlock.getReqParm("fundNumber"));
        appReqBlock.getHttpServletRequest().setAttribute("fundName", appReqBlock.getReqParm("fundName"));

        new FeeSummaryTableModel(appReqBlock);

        return FEE_DIALOG;
    }

    /**
     * Get the ChargeCodeVOs for a filtered fund in an array sorted
     * on the ChargeCode string.
     * @param filteredFundPK
     * @return
     */
    private ChargeCodeVO[] getSortedChargeCodeVOs(long filteredFundPK)
    {
        ChargeCode[] chargeCodes = ChargeCode.findByFilteredFundPK(filteredFundPK);
        if (chargeCodes == null)
        {
            return new ChargeCodeVO[0];
        }
        ChargeCodeVO[] chargeCodeVOs = new ChargeCodeVO[chargeCodes.length];
        for (int i = 0; i < chargeCodes.length; i++)
        {
            ChargeCode chargeCode = chargeCodes[i];
            ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
            chargeCodeVOs[i] = chargeCodeVO;
        }
        // sort the Array on charge code string
        Arrays.sort(chargeCodeVOs,
                new Comparator()
                {
                    public int compare(Object a, Object b)
                    {
                        ChargeCodeVO aVO = (ChargeCodeVO) a;
                        ChargeCodeVO bVO = (ChargeCodeVO) b;
                        String aChargeCode = aVO.getChargeCode();
                        String bChargeCode = bVO.getChargeCode();
                        return (aChargeCode.compareTo(bChargeCode));
                    }
                });
        return chargeCodeVOs;
    }

    protected String addFee(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("feePK", null);

        return showFeeDialog(appReqBlock);
    }

    protected String saveFee(AppReqBlock appReqBlock)
    {
        String responseMessage = null;

        String feePK = Util.initString(appReqBlock.getReqParm("feePK"), "0");
        String filteredFundPK = appReqBlock.getReqParm("filteredFundPK");
        String feeDescriptionPK = appReqBlock.getReqParm("feeDescriptionPK");
        String effectiveMonth = Util.initString(appReqBlock.getReqParm("effectiveMonth"), null);
        String effectiveDay = Util.initString(appReqBlock.getReqParm("effectiveDay"), null);
        String effectiveYear = Util.initString(appReqBlock.getReqParm("effectiveYear"), null);
        String processDateTime = Util.initString(appReqBlock.getReqParm("processDate"), null);
        String redemptionDate = Util.initString(appReqBlock.getReqParm("redemptionDate"), null);
        String originalProcDate = Util.initString(appReqBlock.getReqParm("originalProcDate"), null);
        String releaseDate = Util.initString(appReqBlock.getReqParm("releaseDate"), null);
        String releaseInd = Util.initString(appReqBlock.getReqParm("releaseIndStatus"), null);
        String feeAmount = Util.initString(appReqBlock.getReqParm("feeAmount"), null);
        String transactionTypeCT = Util.initString(appReqBlock.getReqParm("transactionTypeCT"), null);
        String toFromInd = Util.initString(appReqBlock.getReqParm("toFromInd"), null);
        String contractNumber = Util.initString(appReqBlock.getReqParm("policyNumber"), null);
        String accountingPeriod = Util.initString(appReqBlock.getReqParm("accountingPeriod"), null);
        String accountingPendingStatus = Util.initString(appReqBlock.getReqParm("accountingPendingStatus"), null);
        String statusCT = Util.initString(appReqBlock.getReqParm("statusCT"), null);
        String reversalEntry = Util.initString(appReqBlock.getReqParm("reversal"), null);
        String chargeCodeFKStr = Util.initString(appReqBlock.getReqParm("chargeCodeFK"), "0");
        long chargeCodeFK = Long.parseLong(chargeCodeFKStr);

        String effectiveDate = DateTimeUtil.initDate(effectiveMonth, effectiveDay, effectiveYear, null);

        FeeVO feeVO = new FeeVO();
        feeVO.setFeePK(Long.parseLong(feePK));
        feeVO.setFilteredFundFK(Long.parseLong(filteredFundPK));
        feeVO.setFeeDescriptionFK(Long.parseLong(feeDescriptionPK));
        feeVO.setEffectiveDate(effectiveDate);
        feeVO.setProcessDateTime(processDateTime);
        feeVO.setOriginalProcessDate(originalProcDate);
        feeVO.setRedemptionDate(redemptionDate);
        feeVO.setReleaseDate(releaseDate);
        feeVO.setReleaseInd(releaseInd);
        feeVO.setStatusCT(statusCT);
        feeVO.setAccountingPendingStatus(accountingPendingStatus);
        feeVO.setTrxAmount(new EDITBigDecimal(feeAmount).getBigDecimal());
        feeVO.setTransactionTypeCT(transactionTypeCT);
        feeVO.setToFromInd(toFromInd);
        feeVO.setContractNumber(contractNumber);
        feeVO.setOperator(appReqBlock.getUserSession().getUsername());
        feeVO.setChargeCodeFK(chargeCodeFK);
        feeVO.setAccountingPeriod(accountingPeriod);

        Calculator calculatorComponent = new CalculatorComponent();

        try
        {
            if (reversalEntry == null)
            {
                calculatorComponent.saveFee(feeVO);
            }
            else if (reversalEntry.equalsIgnoreCase("R"))
            {
                calculatorComponent.reverseFee(Long.parseLong(feePK));
            }

            appReqBlock.setReqParm("feePK", null);
        }
        catch (EDITEngineException e)
        {
            responseMessage = e.getMessage();

            appReqBlock.setReqParm("feePK", feePK);
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showFeeDialog(appReqBlock);
    }

    protected String cancelFee(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("feePK", null);

        return showFeeDialog(appReqBlock);
    }

    protected String deleteFee(AppReqBlock appReqBlock)
    {
        String feePK = appReqBlock.getReqParm("feePK");
        String responseMessage = null;

        Calculator calculatorComponent = new CalculatorComponent();

        try
        {
            calculatorComponent.deleteFee(Long.parseLong(feePK));

            appReqBlock.setReqParm("feePK", null);
        }
        catch (EDITEngineException e)
        {
            responseMessage = e.getMessage();

            appReqBlock.setReqParm("feePK", feePK);
        }

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showFeeDialog(appReqBlock);
    }

    private void getFeeDescriptions(AppReqBlock appReqBlock, long filteredFundPK)
    {
        Lookup engineLookup = new LookupComponent();

        FeeDescriptionVO[] feeDescriptionVOs =
                engineLookup.findFeeDescriptionBy_FilteredFundPK(filteredFundPK);

        /*if (feeDescriptionVOs == null)
        {
            String message = "There were no Fee Descriptions for the selected Filtered Fund";

            appReqBlock.getHttpServletRequest().setAttribute("feeDescriptionMessage", message);
        }*/

        appReqBlock.getHttpServletRequest().setAttribute("feeDescriptionVOs", feeDescriptionVOs);
    }

    protected String showCorrespondenceDialog(AppReqBlock appReqBlock)
    {
        String feePK = appReqBlock.getReqParm("feePK");

        Lookup engineLookup = new LookupComponent();

        FeeCorrespondenceVO feeCorrespondenceVO =
                engineLookup.findFeeCorrespondenceBy_FeePK(Long.parseLong(feePK));

        appReqBlock.getHttpServletRequest().setAttribute("feeCorrespondenceVO", feeCorrespondenceVO);

        return CORRESPONDENCE_DIALOG;
    }

    protected String showChargeCodeDialog(AppReqBlock appReqBlock)
    {
        FilteredFundVO filteredFundVO = saveMainPageFilteredFund(appReqBlock);

        String filteredFundPK   = appReqBlock.getReqParm("filteredFundPK");
        String chargeCodePK     = appReqBlock.getReqParm("chargeCodePK");

        ChargeCodeVO chargeCodeVO = null;

        Calculator calculatorComponent = new CalculatorComponent();

        if(chargeCodePK != null && Long.parseLong(chargeCodePK) != 0)
        {
            chargeCodeVO = calculatorComponent.findChargeCodeBy_ChargeCodePK(Long.parseLong(chargeCodePK));
        }


        ChargeCodeVO[] chargeCodeVOs =
                calculatorComponent.findChargeCodeBy_FilteredFundPK(Long.parseLong(filteredFundPK));


        ChargeCodeVO[] sortedChargeCodeVOs =
                (ChargeCodeVO[]) Util.sortObjects(
                        (Object[])chargeCodeVOs, new String[]{"getChargeCode"});


        appReqBlock.getHttpSession().setAttribute("selectedFilteredFundVO", filteredFundVO);
        
        appReqBlock.getHttpServletRequest().setAttribute("chargeCodeVO", chargeCodeVO);

        appReqBlock.getHttpServletRequest().setAttribute("chargeCodeVOs", sortedChargeCodeVOs);

        appReqBlock.getHttpServletRequest().setAttribute("filteredFundFK", filteredFundPK);

        appReqBlock.getHttpServletRequest().setAttribute("fundNumber", appReqBlock.getReqParm("fundNumber"));

        appReqBlock.getHttpServletRequest().setAttribute("fundName", appReqBlock.getReqParm("fundName"));

        return CHARGE_CODE_DIALOG;
    }

    protected String addChargeCode(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("chargeCodePK", null);

        return showChargeCodeDialog(appReqBlock);
    }



    protected String saveChargeCode(AppReqBlock appReqBlock)
    {
        //ChargeCodeVO chargeCodeVO = (ChargeCodeVO)
        //        Util.mapFormDataToVO(appReqBlock.getHttpServletRequest(), ChargeCodeVO.class);

        HttpServletRequest req = appReqBlock.getHttpServletRequest();
        ChargeCodeVO chargeCodeVO  = createChargeCodeVO(req);

        try
        {
            new CalculatorComponent().saveChargeCode(chargeCodeVO);

            String responseMessage = chargeCodeVO.getChargeCode() + " successfully saved.";

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }
        catch (EDITEngineException e)
        {
            String responseMessage = e.getMessage();

            appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);
        }

        return showChargeCodeDialog(appReqBlock);
    }

    private ChargeCodeVO createChargeCodeVO(HttpServletRequest req)
    {

        ChargeCodeVO chargeCodeVO = new ChargeCodeVO();
        String ccPKStr = req.getParameter("chargeCodePK");
        String ffFKStr = req.getParameter("filteredFundFK");
        String chargeCode = Util.initString(req.getParameter("chargeCode"), "");
        String accumulatedPremiumStr = Util.initString(req.getParameter("accumulatedPremium"), "0");

        String clientFundNumber = Util.initString(req.getParameter("clientFundNumber"), "");
        clientFundNumber = clientFundNumber.trim();
        // if they are resetting it to 'nothing', make sure that it is empty string

        if (ccPKStr == null || ccPKStr.trim().length() == 0)
            ccPKStr = "0";

        if (ffFKStr == null || ffFKStr.trim().length() == 0)
            ffFKStr = "0";

        long chargeCodePK = Long.parseLong(ccPKStr);
        long filteredFundFK = Long.parseLong(ffFKStr);
        BigDecimal accumulatedPremium = new BigDecimal(accumulatedPremiumStr);

        String newIssueIndicator = Util.initString(req.getParameter("newIssuesInd"), "N");
        if (newIssueIndicator.equalsIgnoreCase("Please Select"))
        {
            chargeCodeVO.setNewIssuesIndicatorCT(null);
        }
        else
        {
            chargeCodeVO.setNewIssuesIndicatorCT(newIssueIndicator);
        }

        chargeCodeVO.setChargeCode(chargeCode);
        chargeCodeVO.setChargeCodePK(chargeCodePK);
        chargeCodeVO.setFilteredFundFK(filteredFundFK);
        chargeCodeVO.setClientFundNumber(clientFundNumber);
        return chargeCodeVO;
    }

    protected String cancelChargeCode(AppReqBlock appReqBlock)
    {
        appReqBlock.setReqParm("chargeCodePK", null);

        return showChargeCodeDialog(appReqBlock);
    }

    protected String deleteChargeCode(AppReqBlock appReqBlock) throws EDITEngineException
    {
        String chargeCodePK = appReqBlock.getReqParm("chargeCodePK");

        String chargeCode = appReqBlock.getReqParm("chargeCode");

        new CalculatorComponent().deleteChargeCode(Long.parseLong(chargeCodePK));

        String responseMessage = chargeCode + " was deleted.";

        appReqBlock.getHttpServletRequest().setAttribute("responseMessage", responseMessage);

        return showChargeCodeDialog(appReqBlock);
    }

}
