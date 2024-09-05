/*
 * User: cgleason
 * Date: Dec 13, 2004
 * Time: 3:04:00 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.util;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.*;

import fission.utility.Util;
import event.Suspense;
import event.CashBatchContract;
import engine.Area;
import engine.AreaValue;


public class DateCalcsForPremium
{
    private String dateResult;
    private String incomeMaturityDate;

    public DateCalcsForPremium()
    {

    }
    /**
     * From QuoteDetailTran on issue, for non Life contracts, this method is invoked to calculate the
     * contract effective date from the deposits, as defined by the EDITServicesConfig file.
     * @param segmentVO
     * @param productStructureVO
     * @return  error message
     */
    public String calcEffectiveDateForIssue(SegmentVO segmentVO,  ProductStructureVO productStructureVO)
    {
        String initialPremiumDate = getDateCalculationValues(segmentVO, productStructureVO);

        String message = null;

        Suspense[] suspense = Suspense.findByUserDefNumberForIssue(segmentVO.getContractNumber());

        if (suspense != null && suspense.length > 0)
        {
            message = calcUsingSuspense(initialPremiumDate, suspense);
        }

        return message;
    }

    /***
     * From ContractEvent on issue for Life contracts, this method is invoked to calculate the effecitve date
     * of the Premium transaction.
     * @param segmentVO
     */
     public void calcPremiumDateForIssue(SegmentVO segmentVO, Suspense[] suspense)
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(segmentVO.getProductStructureFK(), false, null);

        String initialPremiumDate = getDateCalculationValues(segmentVO, productStructureVO[0]);

        if (suspense != null)
        {
            calcUsingSuspense(initialPremiumDate, suspense);
        }
    }

    /**
     * Effective date calculation using deposits.  This date will be used for the effective date of contracts and transactions
     * for Non-Life contracts.  For Life contracts this date is the initial Premium Effective date.
     * @param depositsVO
     * @param initialPremiumDate
     * @return
     */
    private String calcUsingSuspense(String initialPremiumDate, Suspense[] suspense)
    {
        String message = null;

        Suspense[] sortedSuspense = (Suspense[]) Util.sortObjects(suspense, new String[] {"getEffectiveDate"});

        EDITBigDecimal weightedDepositAmt = new EDITBigDecimal();

        int j = 1;
        EDITDate receivedDate = null;
        EDITDate startingDate = null;
        EDITDate nextDate = null;
        EDITDate latestDate = null;
        int nbrDays = 0;
        boolean moneyReceived = false;
        EDITBigDecimal policyAmount = new EDITBigDecimal();
        String dateResult = null;

        for (int i = 0; i < sortedSuspense.length; i++)
        {
            CashBatchContract cashBatchContract = sortedSuspense[i].getCashBatchContract();

            if (cashBatchContract == null || cashBatchContract.getReleaseIndicator().equalsIgnoreCase("R"))
            {
                moneyReceived = true;
                receivedDate = sortedSuspense[i].getEffectiveDate();
                if (j == 1)
                {
                    startingDate = receivedDate;
                }

                nextDate = receivedDate;
                latestDate = receivedDate;

                policyAmount = policyAmount.addEditBigDecimal(sortedSuspense[i].getSuspenseAmount());
                nbrDays = nextDate.getElapsedDays(startingDate);
                weightedDepositAmt = weightedDepositAmt.addEditBigDecimal(
                                     sortedSuspense[i].getSuspenseAmount().multiplyEditBigDecimal(nbrDays + ""));
                j = 0;
            }
        }

        if (moneyReceived)
        {
            String result = weightedDepositAmt.divideEditBigDecimal(policyAmount).toString() + "";
            result = result.substring(0, result.indexOf("."));
            int numberOfDaysToAdd = Integer.parseInt(result);
            if (initialPremiumDate.equalsIgnoreCase("WeightedAverageDate"))
            {
                dateResult = startingDate.addDays(numberOfDaysToAdd).getFormattedDate();
            }
            else if (initialPremiumDate.equalsIgnoreCase("LatestEffectiveDate"))
            {
                dateResult = latestDate.getFormattedDate();
            }
        }
        else
        {
            message = "Deposit Money Not Yet Received.  Cannot Issue.";

        }

        this.dateResult = dateResult;

        return message;
    }

    public String getDateCalculated()
    {
        return this.dateResult;
    }

    public String getIncomeMaturityDate()
    {
        return this.incomeMaturityDate;
    }

    /**
     * Get the DateCalulation parameters from the EDITServiceConfig.
     * @param productStructureVO
     * @return
     */
    private String getDateCalculationValues(SegmentVO segmentVO, ProductStructureVO productStructureVO)
    {
//        DateCalculations dateCalculations = ServicesConfig.getDateCalculations(productStructureVO.getBusinessContractName());
        long productStructurePK = productStructureVO.getProductStructurePK();
        String qualifierCT = Util.initString(segmentVO.getQualNonQualCT(), "*");
        String areaCT = segmentVO.getIssueStateCT();
        String grouping = "AUTOISSUE";

        EDITDate effectiveDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);

        if (segmentVO.getEffectiveDate() != null)
        {
            effectiveDate = new EDITDate(segmentVO.getEffectiveDate());
        }

        String field = "INITIALPREMIUMDATE";

        Area area1 = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue1 = area1.getAreaValue(field);

        String initialPremiumDate = null;

        if (areaValue1 != null)
        {
            AreaValueVO areaValueVO = (AreaValueVO) areaValue1.getVO();
            initialPremiumDate = areaValueVO.getAreaValue();
        }

        field = "INCOMEMATURITYDATE";

        Area area2 = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue2 = area2.getAreaValue(field);

        if (areaValue2 != null)
        {
            AreaValueVO areaValueVO = (AreaValueVO) areaValue2.getVO();
            this.incomeMaturityDate = areaValueVO.getAreaValue();
        }
        else
        {
            this.incomeMaturityDate = null;
        }

        return initialPremiumDate;
    }
}
