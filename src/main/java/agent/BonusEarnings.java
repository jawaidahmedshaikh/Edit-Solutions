/*
 * User: gfrosti
 * Date: May 2, 2005
 * Time: 3:47:31 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.*;

import edit.common.vo.*;

import event.*;

import java.math.*;



public class BonusEarnings
{
    private ParticipatingAgent participatingAgent;
    private EDITBigDecimal negativePolicyEarnings;
    private EDITBigDecimal netPolicyEarnings;
    private EDITBigDecimal positivePolicyEarnings;
    private EDITBigDecimal taxableIncome;
    private EDITBigDecimal taxableIncomeYTD;

    public BonusEarnings(ParticipatingAgent participatingAgent)
    {
        this.participatingAgent = participatingAgent;

        this.negativePolicyEarnings = new EDITBigDecimal("0.00");
    }

    /**
     * The relative earnings information.
     * @param participatingAgent
     */
    public void generateBonusEarnings(ParticipatingAgent participatingAgent, BonusCommissionHistory[] bonusCommissionHistories)
    {
        generateEarnings(bonusCommissionHistories);

        generateTaxableIncomeYTD();
    }

    /**
     *
     * @param bonusCommissionHistories
     */
    private void generateEarnings(BonusCommissionHistory[] bonusCommissionHistories)
    {
        EDITBigDecimal earnings = new EDITBigDecimal("0", 2);

        for (int i = 0; i < bonusCommissionHistories.length; i++)
        {
            BonusCommissionHistory bonusCommissionHistory = bonusCommissionHistories[i];

            EDITBigDecimal checkAmount = bonusCommissionHistory.getAmount();

            earnings = earnings.addEditBigDecimal(checkAmount);
        }

        this.netPolicyEarnings = earnings;

        this.positivePolicyEarnings = earnings;

        this.taxableIncome = earnings;
    }

    /**
     * The YTD Taxable Income for the associated PlacedAgent of the specified ParticipatingAgent.
     */
    private void generateTaxableIncomeYTD()
    {
        PlacedAgent placedAgent = participatingAgent.getPlacedAgent();

        EDITDate currentDate = new EDITDate();

        EDITDate startDate = new EDITDate(currentDate.getFormattedYear(), EDITDate.DEFAULT_MIN_MONTH, EDITDate.DEFAULT_MIN_DAY);

        EDITDate stopDate = new EDITDate(currentDate.getFormattedYear(), EDITDate.DEFAULT_MAX_MONTH, EDITDate.DEFAULT_MAX_DAY);

        String[] trxTypes = { "BCK" };

        taxableIncomeYTD = new TaxableIncomeYTD().calculateTaxableIncomeYTD(new PlacedAgent[]
                {
                    placedAgent
                }, startDate.getFormattedDate(), stopDate.getFormattedDate(), trxTypes);
    }

    /**
     * The VO (read-only) represenation of this entity.
     * @return
     */
    public BonusEarningsVO getBonusEarningsVO()
    {
        BonusEarningsVO bonusEarningsVO = new BonusEarningsVO();

        bonusEarningsVO.setNegativePolicyEarnings(new BigDecimal("0.00"));
        bonusEarningsVO.setNetPolicyEarnings(netPolicyEarnings.getBigDecimal());
        bonusEarningsVO.setPositivePolicyEarnings(positivePolicyEarnings.getBigDecimal());
        bonusEarningsVO.setTaxableIncome(taxableIncome.getBigDecimal());
        bonusEarningsVO.setTaxableIncomeYTD(taxableIncomeYTD.getBigDecimal());

        return bonusEarningsVO;
    }
}
