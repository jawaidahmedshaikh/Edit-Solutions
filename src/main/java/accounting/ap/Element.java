package accounting.ap;

import edit.common.vo.*;
import edit.common.EDITBigDecimal;
import edit.common.CodeTableWrapper;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;

public class Element
{
    public static final String GROSS_AMOUNT = "GrossAmount";
    public static final String NET_AMOUNT = "NetAmount";
    public static final String STATE_WITHHOLDING = "StateWithhldng";
    public static final String FEDERAL_WITHHOLDING = "FedWithhldng";
    public static final String CITY_WITHHOLDING = "CityWithhldng";
    public static final String COUNTY_WITHHOLDING = "CntyWithholding";
    public static final String CHECK_AMOUNT = "CheckAmount";
    public static final String FREE_AMOUNT = "FreeAmount";
    public static final String SUSPENSE = "Suspense";
    public static final String FUND_DOLLARS = "FundDollars";
    public static final String GAIN = "Gain";
    public static final String LOSS = "Loss";
    public static final String ORIGINAL_AMOUNT = "OrigAmount";
    public static final String COMMISSION_AMOUNT = "CommissionAmt";
    public static final String ADA_AMOUNT = "ADAAmount";
    public static final String EXPENSE_AMOUNT = "ExpenseAmount";
    public static final String BONUS_COMMISSION_AMOUNT = "BonusCommAmt";
    public static final String EXCESS_BONUS_COMMISSION_AMOUNT = "ExcBonusCommAmt";
    public static final String BONUS_AMOUNT = "BonusAmount";
    public static final String BUCKET_LIAB_NEG = "BucketLiabNeg";
    public static final String BUCKET_LIAB_POS = "BucketLiabPos";
    public static final String ACCUM_GUAR_INTEREST = "AccumGuarInt";
    public static final String ACCUM_BONUS_INTEREST = "AccumBonusInt";
    public static final String ACCUM_EXCESS_INTEREST = "AccumExcessInt";
    public static final String M_AND_E_FEE = "MEFee";
    public static final String ADVISORY_FEE = "AdvisoryFee";
    public static final String MANAGEMENT_FEE = "ManagementFee";
    public static final String RVP_FEE = "RVPFee";
    public static final String MODAL_PREMIUM_AMOUNT = "ModalPremiumAmt";
    public static final String INTEREST_PROCEEDS = "IntrstProceeds";
    public static final String LOAN_PRINCIPAL_DOLLARS = "LnPrncplDollars";
    public static final String LOAN_INTEREST_DOLLARS = "LnIntrstDollars";
    public static final String LOAN_INTEREST_LIABILITY = "LoanIntrstLiab";
    public static final String PREM_DUE_UNPAID = "PremDueUnpaid";
    public static final String UNEARNED_INTEREST_CREDIT = "UnearnedIntCred";
    public static final String OVER_SHORT_AMOUNT = "OverShortAmount";
    public static final String FROM_EVENT_TYPE = "F";
    public static final String TO_EVENT_TYPE = "T";
    public static List chargeTypes = new ArrayList();

    private String elementName;

    private FilteredFundVO filteredFundVO;

    static
    {
        CodeTableVO[] chargeTypeCTVOs = CodeTableWrapper.getSingleton().getCodeTableEntries("CHARGETYPE");

        for (int i = 0; i < chargeTypeCTVOs.length; i++)
        {
            chargeTypes.add(chargeTypeCTVOs[i].getCode());
        }
    }

    public Element(String name)
    {
        this.elementName = name;
    }

    public void setFilteredFundVO(FilteredFundVO filteredFundVO)
    {
        this.filteredFundVO = filteredFundVO;
    }

    public FilteredFundVO getFilteredFundVO()
    {
        return filteredFundVO;
    }

    public String getName()
    {
        return elementName;
    }

    public boolean isFundElement()
    {
        return elementName.equals(FUND_DOLLARS) || elementName.equals(GAIN) ||
            elementName.equals(LOSS) || elementName.equals(BONUS_AMOUNT) ||
            elementName.equals(BUCKET_LIAB_NEG) || elementName.equals(BUCKET_LIAB_POS) ||
            elementName.equals(ACCUM_BONUS_INTEREST) || elementName.equals(ACCUM_EXCESS_INTEREST) ||
            elementName.equals(ACCUM_GUAR_INTEREST)  || elementName.equals(LOAN_PRINCIPAL_DOLLARS) ||
            elementName.equals(LOAN_INTEREST_DOLLARS) || elementName.equals(LOAN_INTEREST_LIABILITY) ||
                elementName.equals(UNEARNED_INTEREST_CREDIT) || elementName.equals(OVER_SHORT_AMOUNT);
    }

    public boolean isGainLoss()
    {
        return elementName.equals(GAIN) || elementName.equals(LOSS);
    }


    public boolean isSuspenseElement()
    {
        return (elementName.equals(ORIGINAL_AMOUNT));
    }

    public EDITBigDecimal getAmount(EDITTrxHistoryVO editTrxHistoryVO)
    {   //SRAMAM 09/2004 DOUBLE2DECIMAL
        //double elementAmount = 0.0;
        EDITBigDecimal elementAmount = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

        if (elementName.equals(GROSS_AMOUNT))
        {
            elementAmount = new EDITBigDecimal(editTrxHistoryVO.getFinancialHistoryVO(0).getGrossAmount()
                                               .toString());
        }

        else if (chargeTypes.contains(elementName))
        {
            elementAmount = getChargeAmount(elementName, editTrxHistoryVO.getChargeHistoryVO());
        }

        else if (elementName.equals(NET_AMOUNT))
        {
            //elementAmount = editTrxHistoryVO.getFinancialHistoryVO(0).getNetAmount();
            elementAmount = new EDITBigDecimal(editTrxHistoryVO.getFinancialHistoryVO(0).getNetAmount().toString());
        }

        else if (elementName.equals(STATE_WITHHOLDING))
        {
            WithholdingHistoryVO[] withholdingHistoryVOs = editTrxHistoryVO.getWithholdingHistoryVO();
            if (withholdingHistoryVOs != null && withholdingHistoryVOs.length > 0) {

             //elementAmount = editTrxHistoryVO.getWithholdingHistoryVO(0).getStateWithholdingAmount();
            elementAmount = new EDITBigDecimal(editTrxHistoryVO.getWithholdingHistoryVO(0).getStateWithholdingAmount().toString());
         }
            else {
                //elementAmount = 0;
                elementAmount = elementAmount.resetValue();
            }
        }

        else if (elementName.equals(FEDERAL_WITHHOLDING))
        {
            WithholdingHistoryVO[] withholdingHistoryVOs = editTrxHistoryVO.getWithholdingHistoryVO();
            if (withholdingHistoryVOs != null && withholdingHistoryVOs.length > 0) {

                //elementAmount = editTrxHistoryVO.getWithholdingHistoryVO(0).getFederalWithholdingAmount();
                elementAmount = new EDITBigDecimal(editTrxHistoryVO.getWithholdingHistoryVO(0).getFederalWithholdingAmount().toString());
            }
            else {
                //elementAmount = 0;
                elementAmount = elementAmount.resetValue();
            }
        }

        else if (elementName.equals(CITY_WITHHOLDING))
        {
            WithholdingHistoryVO[] withholdingHistoryVOs = editTrxHistoryVO.getWithholdingHistoryVO();
            if (withholdingHistoryVOs != null && withholdingHistoryVOs.length > 0) {
               // elementAmount = editTrxHistoryVO.getWithholdingHistoryVO(0).getCityWithholdingAmount();
                elementAmount =  new EDITBigDecimal(editTrxHistoryVO.getWithholdingHistoryVO(0).getCityWithholdingAmount().toString());
            }
            else {
                //elementAmount = 0;
                elementAmount = elementAmount.resetValue();
            }
        }

        else if (elementName.equals(COUNTY_WITHHOLDING))
        {
            WithholdingHistoryVO[] withholdingHistoryVOs = editTrxHistoryVO.getWithholdingHistoryVO();
            if (withholdingHistoryVOs != null && withholdingHistoryVOs.length > 0) {
               // elementAmount = editTrxHistoryVO.getWithholdingHistoryVO(0).getCountyWithholdingAmount();
                elementAmount = new EDITBigDecimal(editTrxHistoryVO.getWithholdingHistoryVO(0).getCountyWithholdingAmount().toString());
            }
            else {
                elementAmount = elementAmount.resetValue();
            }
        }

        else if (elementName.equals(CHECK_AMOUNT))
        {
           // elementAmount = editTrxHistoryVO.getFinancialHistoryVO(0).getCheckAmount();
            elementAmount = new EDITBigDecimal(editTrxHistoryVO.getFinancialHistoryVO(0).getCheckAmount().toString());
        }

        else if (elementName.equals(FREE_AMOUNT))
        {
            //elementAmount = editTrxHistoryVO.getFinancialHistoryVO(0).getFreeAmount();
            elementAmount = new EDITBigDecimal(editTrxHistoryVO.getFinancialHistoryVO(0).getFreeAmount().toString());
        }

        else if (elementName.equals(INTEREST_PROCEEDS))
        {
            elementAmount = new EDITBigDecimal(editTrxHistoryVO.getFinancialHistoryVO(0).getInterestProceeds().toString());
        }

        else if (elementName.equals(SUSPENSE))
        {
            //elementAmount = editTrxHistoryVO.getInSuspenseVO(0).getAmount();
            elementAmount = new EDITBigDecimal(editTrxHistoryVO.getInSuspenseVO(0).getAmount().toString());
        }

        else if (elementName.equals(FUND_DOLLARS))
        {
            //elementAmount = 0;
            elementAmount = elementAmount.resetValue();
        }

        else if (elementName.equals(GAIN))
        {
            //elementAmount = 0;
            elementAmount = elementAmount.resetValue();
        }

        else if (elementName.equals(LOSS))
        {
            //elementAmount = 0;
            elementAmount = elementAmount.resetValue();
        }

        return elementAmount;
    }

    public EDITBigDecimal getCommissionAmount(CommissionHistoryVO commissionHistoryVO, EDITTrxVO editTrxVO, FinancialHistoryVO financialHistoryVO)
    {
        EDITBigDecimal elementAmount = new EDITBigDecimal();

        switch (elementName) {
            case COMMISSION_AMOUNT:
            elementAmount = new EDITBigDecimal(commissionHistoryVO.getCommissionAmount().toString());
                break;
            case ADA_AMOUNT:
            elementAmount = new EDITBigDecimal(commissionHistoryVO.getADAAmount().toString());
                break;
            case EXPENSE_AMOUNT:
            elementAmount = new EDITBigDecimal(commissionHistoryVO.getExpenseAmount().toString());
                break;
            case BONUS_COMMISSION_AMOUNT:
            elementAmount = new EDITBigDecimal(editTrxVO.getBonusCommissionAmount());
                break;
            case EXCESS_BONUS_COMMISSION_AMOUNT:
            elementAmount = new EDITBigDecimal(editTrxVO.getExcessBonusCommissionAmount());
                break;
            case CHECK_AMOUNT:
            if (financialHistoryVO != null)
            {
                elementAmount = new EDITBigDecimal(financialHistoryVO.getCheckAmount());
            }
            else
            {
                elementAmount = new EDITBigDecimal();
            }
                break;
        }

        return elementAmount;
    }

    public EDITBigDecimal getReinsuranceAmount(ReinsuranceHistoryVO reinsuranceHistoryVO)
    {
        EDITBigDecimal elementAmount = new EDITBigDecimal();

        if (elementName.equals(MODAL_PREMIUM_AMOUNT))
        {
            elementAmount = new EDITBigDecimal(reinsuranceHistoryVO.getModalPremiumAmount().toString());
        }

        return elementAmount;
    }

    public EDITBigDecimal getHistoryAmount(BucketHistoryVO bucketHistoryVO, InvestmentHistoryVO investmentHistoryVO)
    {
        EDITBigDecimal elementAmount = new EDITBigDecimal();

        switch (elementName) {
            case BONUS_AMOUNT:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getBonusAmount().toString());
                break;
            case GAIN:
            case LOSS:
            elementAmount = new EDITBigDecimal(investmentHistoryVO.getGainLoss().toString());
                break;
            case BUCKET_LIAB_NEG:
            case BUCKET_LIAB_POS:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getBucketLiability().toString());
                break;
            case LOAN_PRINCIPAL_DOLLARS:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getLoanPrincipalDollars());
                break;
            case LOAN_INTEREST_DOLLARS:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getLoanInterestDollars());
                break;
            case LOAN_INTEREST_LIABILITY:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getLoanInterestLiability());
                break;
            case UNEARNED_INTEREST_CREDIT:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getUnearnedInterestCredit());
                break;
            case OVER_SHORT_AMOUNT:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getOverShortAmount());
                break;
            default:
            elementAmount = new EDITBigDecimal(bucketHistoryVO.getDollars().toString());
                break;
        }

        return elementAmount;
    }

    private EDITBigDecimal getChargeAmount(String elementName, ChargeHistoryVO[] chargeHistoryVO)
    {
        //SRAMAM 09/2004 DOUBLE2DECIMAL
        //double elementAmount = 0.0;
        EDITBigDecimal elementAmount = new EDITBigDecimal();
        for (int i = 0; i < chargeHistoryVO.length; i++)
        {
            if (chargeHistoryVO[i].getChargeTypeCT().equals(elementName))
            {
                elementAmount = new EDITBigDecimal(chargeHistoryVO[i].getChargeAmount().toString());
                break;
            }
        }
        return elementAmount;
    }

    public static EDITBigDecimal getAccumGuarInterestAmount(BucketHistoryVO[] bucketHistoryVOs, String investmentFK)
    {
        EDITBigDecimal accumGuarInterest = new EDITBigDecimal();

        for (int i = 0; i < bucketHistoryVOs.length; i++)
        {
            BucketVO bucketVO = (BucketVO) bucketHistoryVOs[i].getParentVO(BucketVO.class);
            if (Long.parseLong(investmentFK) == bucketVO.getInvestmentFK())
            {
                accumGuarInterest = accumGuarInterest.addEditBigDecimal(bucketHistoryVOs[i].getInterestEarnedGuaranteed());
            }
        }

        accumGuarInterest = Util.roundToNearestCent(accumGuarInterest);

        return accumGuarInterest;
    }

    public static EDITBigDecimal getAccumBonusInterestAmount(BucketHistoryVO[] bucketHistoryVOs, String investmentFK)
    {
        EDITBigDecimal accumBonusInterest = new EDITBigDecimal();

        for (int i = 0; i < bucketHistoryVOs.length; i++)
        {
            BucketVO bucketVO = (BucketVO) bucketHistoryVOs[i].getParentVO(BucketVO.class);
            if (Long.parseLong(investmentFK) == bucketVO.getInvestmentFK())
            {
                accumBonusInterest = accumBonusInterest.addEditBigDecimal(bucketHistoryVOs[i].getBonusInterestEarned());
            }
        }

        accumBonusInterest = Util.roundToNearestCent(accumBonusInterest);

        return accumBonusInterest;
    }

    public static EDITBigDecimal getAccumExcessInterestAmount(BucketHistoryVO[] bucketHistoryVOs, String investmentFK, EDITBigDecimal accumGuarInt)
    {
        EDITBigDecimal accumExcessInterest = new EDITBigDecimal();

        for (int i = 0; i < bucketHistoryVOs.length; i++)
        {
            BucketVO bucketVO = (BucketVO) bucketHistoryVOs[i].getParentVO(BucketVO.class);
            if (Long.parseLong(investmentFK) == bucketVO.getInvestmentFK())
            {
                accumExcessInterest = accumExcessInterest.addEditBigDecimal(bucketHistoryVOs[i].getInterestEarnedCurrent());
            }
        }

        accumExcessInterest = Util.roundToNearestCent(accumExcessInterest.subtractEditBigDecimal(accumGuarInt));

        return accumExcessInterest;
    }
}
