package agent;

import edit.common.*;
import edit.common.vo.*;
import event.dm.composer.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 3, 2005
 * Time: 11:35:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class TaxableIncomeYTD
{
    /**
     * Finds all CommissionHistory records for the specified PlacedAgent within the specified date range for trxTypes
     * of 'CK', 'BCK' and 'CA', and generates the YTD total.
     * a taxableIncomeYTD.
     * @param placedAgent
     * @param startingDate
     * @param endingDate
     * @return
     * @throws Exception
     */
    public EDITBigDecimal calculateTaxableIncomeYTD(PlacedAgent[] placedAgent, String startingDate, String endingDate, String[] trxTypes)
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);

        EDITBigDecimal taxableIncomeYTD = new EDITBigDecimal();

        for (int i = 0; i < placedAgent.length; i++)
        {
            CommissionHistoryVO[] ytdCheckCommHistVOs = new CommissionHistoryComposer(voInclusionList).composeForYTDTotals(placedAgent[i].getPK(), startingDate, endingDate, trxTypes);

            if (ytdCheckCommHistVOs != null)
            {
                for (int j = 0; j < ytdCheckCommHistVOs.length; j++)
                {
                    EDITTrxVO editTrxVO = (EDITTrxVO) ytdCheckCommHistVOs[j].getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);

                    if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("CA"))
                    {
                        if (ytdCheckCommHistVOs[j].getReduceTaxable().equalsIgnoreCase("Y"))
                        {
                            EDITBigDecimal commAmt = new EDITBigDecimal( ytdCheckCommHistVOs[j].getCommissionAmount() );

                            if (commAmt.doubleValue() < 0) {

                                commAmt = commAmt.negate();
                            }

                            taxableIncomeYTD = taxableIncomeYTD.subtractEditBigDecimal(commAmt);
                        }
                    }
                    else
                    {
                        EDITBigDecimal taxableIncome = getTaxableIncome(ytdCheckCommHistVOs[j]);

                        taxableIncomeYTD = taxableIncomeYTD.addEditBigDecimal(taxableIncome);
                    }
                }
            }
        }

        return taxableIncomeYTD;
    }

    /**
     * The gross amount of the associated FinancialHistory.
     * @param commissionHistoryVO
     * @return
     */
    private EDITBigDecimal getTaxableIncome(CommissionHistoryVO commissionHistoryVO)
    {
        // double commissionTaxable = 0;
        EDITBigDecimal commissionTaxable = new EDITBigDecimal();

        FinancialHistoryVO[] financialHistoryVO = ((EDITTrxHistoryVO) commissionHistoryVO.getParentVO(EDITTrxHistoryVO.class)).getFinancialHistoryVO();

        if (financialHistoryVO != null && financialHistoryVO.length > 0) {

            commissionTaxable = new EDITBigDecimal( financialHistoryVO[0].getGrossAmount() );
        }

        return commissionTaxable;
    }
}
