/*
 * User: gfrosti
 * Date: Nov 28, 2003
 * Time: 1:20:09 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.*;
import edit.common.vo.*;
import event.CommissionHistory;
import event.EDITTrx;
import event.FinancialHistory;
import role.*;


public class AgentEarningsImpl
{

    public void generateEarnings(AgentEarnings agentEarnings, AgentContract agentContract)
    {
        AgentEarningsVO agentEarningsVO = agentEarnings.getVO();

        EDITBigDecimal taxableIncomeYTD =generateTaxableIncomeYTD(agentContract);

        agentEarningsVO.setPositivePolicyEarnings(new EDITBigDecimal().getBigDecimal());
        agentEarningsVO.setNegativePolicyEarnings(new EDITBigDecimal().getBigDecimal());
        agentEarningsVO.setNetPolicyEarnings(new EDITBigDecimal().getBigDecimal());
        agentEarningsVO.setTaxableIncome(new EDITBigDecimal().getBigDecimal());
        agentEarningsVO.setTaxableIncomeYTD(taxableIncomeYTD.getBigDecimal());
    }

    public void generateEarnings(AgentEarnings agentEarnings, AgentContract agentContract, CommissionHistory[] commissionHistoryRecords) throws Exception
    {
        AgentEarningsVO agentEarningsVO = agentEarnings.getVO();

        // double totalPositivePolicyEarnings = 0;
        EDITBigDecimal totalPositivePolicyEarnings = new EDITBigDecimal();
        // double totalNegativePolicyEarnings = 0;
        EDITBigDecimal totalNegativePolicyEarnings = new EDITBigDecimal();

        // double totalTaxableIncome = 0;
        EDITBigDecimal totalTaxableIncome = new EDITBigDecimal();
        // double taxableIncomeYTD = 0;

        EDITBigDecimal totalNetPolicyEarnings = new EDITBigDecimal();

        for (int i = 0; i < commissionHistoryRecords.length; i++)
        {
            EDITTrx editTrx = commissionHistoryRecords[i].getEDITTrxHistory().getEDITTrx();

            if (!editTrx.getTransactionTypeCT().equalsIgnoreCase("CK"))
            {
                // double policyEarning = getPolicyEarning(commissionHistoryVO[i]);

                // commented above line for double to BigDecimal conversion
                // sprasad 9/22/2004

                EDITBigDecimal policyEarning =  getPolicyEarning(commissionHistoryRecords[i]);

                if (policyEarning.doubleValue() < 0)
                {
                    totalNegativePolicyEarnings = totalNegativePolicyEarnings.addEditBigDecimal(policyEarning);
                }
                else if (policyEarning.doubleValue() > 0)
                {
                    totalPositivePolicyEarnings = totalPositivePolicyEarnings.addEditBigDecimal(policyEarning);
                }

                if (editTrx.getTransactionTypeCT().equalsIgnoreCase("CA"))
                {
                    if (commissionHistoryRecords[i].getReduceTaxable().equalsIgnoreCase("Y"))
                    {
                        /*  double commAmt = commissionHistoryVO[i].getCommissionAmount();
                        if (commAmt < 0)
                        {
                            commAmt *= -1;
                        }

                        totalTaxableIncome -= commAmt;  */

                        // commented above lines for double to BigDecimal conversion
                        // sprasad 9/21/2004

                        EDITBigDecimal commAmt = commissionHistoryRecords[i].getCommissionAmount();

                        if ( commAmt.doubleValue() < 0 ) {
                            commAmt = commAmt.negate();
                        }

                        totalTaxableIncome = totalTaxableIncome.subtractEditBigDecimal(commAmt);
                    }
                }
            }
            else
            {
                // double taxableIncome = getTaxableIncome(commissionHistoryVO[i]);
                // totalTaxableIncome += taxableIncome;
                EDITBigDecimal taxableIncome = getTaxableIncome(commissionHistoryRecords[i]);
                totalTaxableIncome = totalTaxableIncome.addEditBigDecimal(taxableIncome);
            }
        }

        EDITBigDecimal taxableIncomeYTD = generateTaxableIncomeYTD(agentContract);

        agentEarningsVO.setPositivePolicyEarnings(totalPositivePolicyEarnings.getBigDecimal());
        agentEarningsVO.setNegativePolicyEarnings(totalNegativePolicyEarnings.getBigDecimal());
        totalNetPolicyEarnings = totalPositivePolicyEarnings.addEditBigDecimal(totalNegativePolicyEarnings);
        agentEarningsVO.setNetPolicyEarnings(totalNetPolicyEarnings.getBigDecimal());
        agentEarningsVO.setTaxableIncome(totalTaxableIncome.getBigDecimal());
        agentEarningsVO.setTaxableIncomeYTD(taxableIncomeYTD.getBigDecimal());
    }

    private EDITBigDecimal generateTaxableIncomeYTD(AgentContract agentContract)
    {
        String startingDate = getStartingDate();
        String endingDate = getEndingDate();

        PlacedAgent[] placedAgent = agentContract.getPlacedAgent();

        EDITBigDecimal taxableIncomeYTD = new EDITBigDecimal();
        if (placedAgent != null)
        {
            String[] trxTypes = {"CK", "CA"};

            taxableIncomeYTD = new TaxableIncomeYTD().calculateTaxableIncomeYTD(placedAgent, startingDate, endingDate, trxTypes);
        }

        return taxableIncomeYTD;
    }

    private String getEndingDate()
    {
        EDITDate currentDate = new EDITDate();

        EDITDate endingDate = new EDITDate(currentDate.getFormattedYear(), EDITDate.DEFAULT_MAX_MONTH, EDITDate.DEFAULT_MAX_DAY);

        return endingDate.getFormattedDate();
    }

    private String getStartingDate()
    {
        EDITDate currentDate = new EDITDate();

        EDITDate startingDate = new EDITDate(currentDate.getFormattedYear(), EDITDate.DEFAULT_MIN_MONTH, EDITDate.DEFAULT_MIN_DAY);

        return startingDate.getFormattedDate();
    }

    private EDITBigDecimal getPolicyEarning(CommissionHistory commissionHistory)
    {
        // double policyEarning = 0.0;

        // commented above line for double to BigDecimal conversion
        // sprasad 9/22/2004

        EDITBigDecimal policyEarning = new EDITBigDecimal();

//        EDITTrxVO editTrxVO = (EDITTrxVO) commissionHistoryVO.getParentVO(EDITTrxHistoryVO.class).getParentVO(EDITTrxVO.class);
//
//        String status = editTrxVO.getStatus();

//        if (status.equals("R"))
        if (commissionHistory.getCommissionTypeCT().equals("ChargeBack")) {
            // policyEarning = -commissionHistoryVO.getCommissionAmount();

            // commented above line for double to BigDecimal conversion
            // sprasad 9/22/2004

            policyEarning = new EDITBigDecimal(commissionHistory.getCommissionAmount().getBigDecimal().negate());
        }
        else {
            policyEarning = new EDITBigDecimal( commissionHistory.getCommissionAmount().getBigDecimal() );
        }

        return policyEarning;
    }

    private EDITBigDecimal getTaxableIncome(CommissionHistory commissionHistory)
    {
        // double commissionTaxable = 0;
        EDITBigDecimal commissionTaxable = new EDITBigDecimal();

        java.util.Set<FinancialHistory> financialHistory = commissionHistory.getEDITTrxHistory().getFinancialHistories();

        if (financialHistory != null && !financialHistory.isEmpty()) {

            commissionTaxable = new EDITBigDecimal( financialHistory.iterator().next().getGrossAmount().getBigDecimal() );
        }

        return commissionTaxable;
    }

    private EDITBigDecimal getTaxableIncomeYTD(PlacedAgent placedAgent) throws Exception
    {
        ClientRoleFinancial clientRoleFinancial = placedAgent.getClientRole().getClientRoleFinancial();

        // double taxableIncomeYTD = ((ClientRoleFinancialVO) clientRoleFinancial.getVO()).getAmountTaxableYTD();

        // commented above line for double to BigDecimal conversion
        // sprasad 9/22/2004
        EDITBigDecimal taxableIncomeYTD = new EDITBigDecimal( ( (ClientRoleFinancialVO) clientRoleFinancial.getVO() ).getAmountTaxableYTD() );

        return taxableIncomeYTD;
    }
}
