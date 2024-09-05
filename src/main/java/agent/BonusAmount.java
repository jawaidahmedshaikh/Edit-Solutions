package agent;

import edit.common.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 4, 2005
 * Time: 9:27:52 AM
 * To change this template use File | Settings | File Templates.
 */
public final class BonusAmount
{
    private EDITBigDecimal totalBonusCommissionAmount;

    private EDITBigDecimal totalExcessBonusCommissionAmount;

    public BonusAmount()
    {
        totalBonusCommissionAmount = new EDITBigDecimal("0.00");

        totalExcessBonusCommissionAmount = new EDITBigDecimal("0.00");
    }

    /**
     * Adds the specified Commission Amount to the Total Bonus Commission Amount.
     * @param commissionAmount
     */
    public final void addToTotalBonusCommissionAmount(EDITBigDecimal bonusCommissionAmount)
    {
        totalBonusCommissionAmount = totalBonusCommissionAmount.addEditBigDecimal(bonusCommissionAmount);
    }

    /**
     * Adds the specified Bonus Commission Amount to the Total Excess Commission Amount.
     * @param excessCommissionAmount
     */
    public final void addToTotalExcessBonusCommissionAmount(EDITBigDecimal excessCommissionAmount)
    {
        totalExcessBonusCommissionAmount = totalExcessBonusCommissionAmount.addEditBigDecimal(excessCommissionAmount);
    }

    /**
     * Getter.
     * @return
     */
    public final EDITBigDecimal getTotalBonusCommissionAmount()
    {
        return totalBonusCommissionAmount;
    }

    /**
     * Getter.
     * @return
     */
    public final EDITBigDecimal getTotalExcessBonusCommissionAmount()
    {
        return totalExcessBonusCommissionAmount;
    }

    /**
     * The sum total of the Bonus Commission Amount + Excess Bonus Commission Amount.
     * @return
     */
    public final EDITBigDecimal getTotalBonusAmount()
    {
        return getTotalBonusCommissionAmount().addEditBigDecimal(getTotalExcessBonusCommissionAmount());
    }
}
