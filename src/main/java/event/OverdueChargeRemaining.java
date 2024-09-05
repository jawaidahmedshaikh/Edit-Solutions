package event;

import edit.common.EDITBigDecimal;

import java.util.HashSet;
import java.util.Set;

/**
 * A helper class to store the results of the OverdueCharge/OverdueChargeSettled calculations.
 * This helper class can be used in two contexts:
 * 
 * Context 1: It is necessary to find the remaining amounts for a single OverdueCharge/OverdueChargeSettled
 * relationship. This is likely done for PRASE which needs that level of granularity. In this case, the
 * user is expected to use the Constructor that takes a single OverdueCharge.
 * 
 * Context 2: It is necessary to sum ALL of the remaining amounts for ALL of the OverdueCharges. This is likely
 * done for online requests for a specific transaction. In this case, the user is expected to use the Constructor
 * that takes a single EDITTrx.
 */
public class OverdueChargeRemaining
{
  /**
   * The driving EDITTrx whether calculations are at the Segment or EDITTrx level.
   */
  private EDITTrx editTrx;

  /**
   * The scrope of the calculations. In this case, all EDITTrxs will be considered
   * that are associated with the specified EDITTrx.
   */
  public static final int SCOPE_SEGMENT_LEVEL = 0;

  /**
   * The scope of the calculations. In this case, the only EDITTrx considered is the
   * specified EDITTrx.
   */
  public static final int SCOPE_EDITTRX_LEVEL = 1;

  /**
   * The scope of which to calculate the Remaining Amounts.
   */
  private int calculationScopeLevel;
  
  private Set<OverdueChargeRemainingAmount> overdueChargeRemainingAmounts = new HashSet<OverdueChargeRemainingAmount>();

  /**
   * Constructor and starting point when the remaining amounts are to be calculated at
   * the EDITTrx level for [all] the OverdueCharge(s).
   * @param editTrx
   */
  public OverdueChargeRemaining(EDITTrx editTrx, int calculationScopeLevel)
  {
    this.editTrx = editTrx;

    this.calculationScopeLevel = calculationScopeLevel;
  }

  /**
   * If calculating at the Segment scope, [each] OverdueCharge is evaluated singularly and
   * a [separate] OverdueChargeRemainingAmount entry is made for every OverdueCharge and
   * its associated OverdueChargeSettled child.
   * 
   * If calculating at the EDITTrx scope, [all] OverdueCharge(s) and their child OverdueChargeSettled(s) are
   * evaluated resulting in a [single] OverdueChargeRemaining entry for all OverdueCharge(s).
   */
  public void calculate()
  {
    OverdueCharge[] overdueCharges = getOverdueCharges();

    OverdueChargeRemainingAmount overdueChargeRemainingAmount = null;

    for (OverdueCharge overdueCharge: overdueCharges)
    {
      overdueChargeRemainingAmount = getOverdueChargeRemainingAmount(overdueCharge, overdueChargeRemainingAmount);
      
      overdueChargeRemainingAmount.add(overdueCharge);

      for (OverdueChargeSettled overdueChargeSettled: overdueCharge.getOverdueChargesSettled())
      {
        overdueChargeRemainingAmount.subtract(overdueChargeSettled);
      }
      
      overdueChargeRemainingAmounts.add(overdueChargeRemainingAmount);
    }
  }

  /**
   * Calculating remaining unsettled amounts differs in scope depending on calculating
   * at the Segment level versus the EDITTrx level. At the Segment level, a [new]
   * OverdueChargeRemainingAmount is generated for each OverdueCharge. At the EDITTrx level, 
   * the [same] OverdueChargeRemainingAmount is used.
   * @param overdueCharge
   * @param overdueChargeRemainingAmount
   * @return
   */
  private OverdueChargeRemainingAmount getOverdueChargeRemainingAmount(OverdueCharge overdueCharge, OverdueChargeRemainingAmount overdueChargeRemainingAmount)
  {
    if (getCalculationScopeLevel() == SCOPE_SEGMENT_LEVEL) 
    {
      overdueChargeRemainingAmount = new OverdueChargeRemainingAmount(overdueCharge);
    }
    else if (getCalculationScopeLevel() == SCOPE_EDITTRX_LEVEL)
    {
      if (overdueChargeRemainingAmount == null)
      {
        overdueChargeRemainingAmount = new OverdueChargeRemainingAmount();
      }
    }
    
    return overdueChargeRemainingAmount;
  }

  /**
   * Helper method to get the set of OverdueCharges for either the driving EDITTrx
   * or the driving OverdueCharge.
   * @return
   */
  private OverdueCharge[] getOverdueCharges()
  {
    OverdueCharge[] overdueCharges = null;

    if (getCalculationScopeLevel() == SCOPE_SEGMENT_LEVEL)
    {
      overdueCharges = OverdueCharge.findBy_EDITTrxPK_V1(getEditTrx(), true);
    }
    else if (getCalculationScopeLevel() == SCOPE_EDITTRX_LEVEL)
    {
      overdueCharges = OverdueCharge.findBy_EDITTrxPK_V1(getEditTrx(), false);
    }

    return overdueCharges;
  }

  /**
   * Getter.
   * @see #editTrx
   * @return
   */
  public EDITTrx getEditTrx()
  {
    return editTrx;
  }

  /**
   * Evaluates the remaining amounts (
   * @return true if [any] of the remaining amount are > 0.00
   */
  public boolean remainingAmountsExist()
  {
    return !getOverdueChargeRemainingAmounts().isEmpty();
  }

  /**
   * Getter.
   * @see #calculationScopeLevel
   * @return
   */
  public int getCalculationScopeLevel()
  {
    return calculationScopeLevel;
  }

  /**
   * The result(s) of the calculation. For Segment Level Scope, expect 0 or more entries (one for
   * each OverdueCharge. For EDITTrx Level, expect 0 or 1 (suummed over all OverdueCharge(s)).
   * @return
   */
  public Set<OverdueChargeRemaining.OverdueChargeRemainingAmount> getOverdueChargeRemainingAmounts()
  {
    return overdueChargeRemainingAmounts;
  }
  
  /**
   * A convenience method to return the first OverdueChargeRemainingAmount (if any). EDITTrx Scoped
   * calculations will have (at most) one OverdueChargeRemainingAmount.
   * @return the first OverdueChargeRemainingAmount or null
   */
  public OverdueChargeRemaining.OverdueChargeRemainingAmount getOverdueChargeRemainingAmount()
  {
    if (!getOverdueChargeRemainingAmounts().isEmpty())
    {
      return getOverdueChargeRemainingAmounts().iterator().next();
    }
    else
    {
      return null;
    }
  }

  public class OverdueChargeRemainingAmount
  {
    /**
     * The driving OverdueCharge when the remaining amounts are to be calculated at the [single]
     * OverdueCharge level.
     */
    private OverdueCharge overdueCharge;

    /**
     * The remaining Coi amount after subtracting OverdueChargeSettled.SettledCoi(s) from 
     * OverdueCharge.OverdueCoi.
     */
    private EDITBigDecimal remainingCoi;

    /**
     * The remaining Admin amount after subtracting OverdueChargeSettled.SettledAdmin(s) from
     * OverdueCharge.OverdueAdmin.
     */
    private EDITBigDecimal remainingAdmin;

    /**
     * The remaining Expense amount after subtracting OverdueChargeSettled.SettledExpense(s) from
     * OverdueCharge.OverdueExpense.
     */
    private EDITBigDecimal remainingExpense;

    public OverdueChargeRemainingAmount(OverdueCharge overdueCharge)
    {
      this();
    
      this.overdueCharge = overdueCharge;
    }
    
    public OverdueChargeRemainingAmount()
    {
      this.remainingCoi = new EDITBigDecimal("0.00");

      this.remainingAdmin = new EDITBigDecimal("0.00");

      this.remainingExpense = new EDITBigDecimal("0.00");
    }

    /**
     * Subtracts the settled amounts of the specified OverdueChargeSettled from the
     * remaining amounts of this OverdueChargeRemaining.
     * @param overdueChargeSettled
     */
    private void subtract(OverdueChargeSettled overdueChargeSettled)
    {
      this.remainingAdmin = remainingAdmin.subtractEditBigDecimal(overdueChargeSettled.getSettledAdmin());
      
      this.remainingCoi = remainingCoi.subtractEditBigDecimal(overdueChargeSettled.getSettledCoi());
      
      this.remainingExpense = remainingExpense.subtractEditBigDecimal(overdueChargeSettled.getSettledExpense());
    }
    
    /**
     * Adds the overdue amounts of the specified OverdueCharge to the
     * remaining amounts of this OverdueChargeRemaining.
     * @param overdueCharge
     */
    private void add(OverdueCharge overdueCharge)
    {
      this.remainingAdmin = remainingAdmin.addEditBigDecimal(overdueCharge.getOverdueAdmin());
      
      this.remainingCoi = remainingCoi.addEditBigDecimal(overdueCharge.getOverdueCoi());
      
      this.remainingExpense = remainingExpense.addEditBigDecimal(overdueCharge.getOverdueExpense());
    }    
    
    /**
     * Getter.
     * @see #remainingCoi
     * @return
     */
    public EDITBigDecimal getRemainingCoi()
    {
      return remainingCoi;
    }
    
    /**
     * Getter.
     * @see #remainingAdmin
     * @return
     */
    public EDITBigDecimal getRemainingAdmin()
    {
      return remainingAdmin;
    }
    
    /**
     * Getter.
     * @see #remainingExpense
     * @return
     */
    public EDITBigDecimal getRemainingExpense()
    {
      return remainingExpense;
    }
    
    /**
     * The driving OverdueCharge for Segment Level Scoped calculations.
     * @return the driving OverdueCharge or null if not for Segment Level Scoped calculations
     */
    public OverdueCharge getOverdueCharge()
    {
      return overdueCharge;
    }
  }
}
