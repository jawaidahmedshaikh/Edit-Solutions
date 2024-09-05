/*
 * User: sprasad
 * Date: Nov 20, 2006
 * Time: 5:04:01 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice.service.rpc;

/**
 * The result of performing a New Business Quote is a NewBusinessQuoteOutput.
 * This compliments the NewBusinessQuoteInput and is a value object that
 * represents the resultant values.
 */
public class NewBusinessQuoteOutput
{
  /**
   * Guaranteed duration for Modal Payouts.
   */
  private int certainDuration;
  
  /**
   * Final Lump Sum Payout amount for Life w/Refund Payout option.
   */
  private double finalDistributionAmount;
  
  /**
   * Not Used ' sum of Premium Tax Charges.
   */
  private double premiumTaxes;
  
  /**
   * Not Used ' sum of Front End Load Charges.
   */
  private double frontEndLoads;
  
  /**
   * Not Used ' sum of fees.
   */
  private double fees;
  
  /**
   * The projected total payout for a contract.
   */
  private double totalProjectedAnnuity;
  
  /**
   * Percentage of amount of Modal Payout not subject to income tax.
   */
  private double exclusionRatio;
  
  /**
   * Taxable Benefit of year's worth of Modal Payouts.
   */
  private double yearlyTaxableBenefit;
  
  /**
   * Estimated amount of money in current dollars of all modal payments for life of contract.
   */
  private double commutedValue;
  
  /**
   * Amount of money contributed to Payout Contract.
   */
  private double purchaseAmount;
  
  /**
   * Modal Payout amount.
   */
  private double paymentAmount;

  /**
   * Getter.
   * @see #certainDuration
   * @return
   */
  public int getCertainDuration()
  {
    return certainDuration;
  }

  /**
   * Setter.
   * @see #certainDuration
   * @param certainDuration
   */
  public void setCertainDuration(int certainDuration)
  {
    this.certainDuration = certainDuration;
  }

  /**
   * Getter.
   * @see #finalDistributionAmount
   * @return
   */
  public double getFinalDistributionAmount()
  {
    return finalDistributionAmount;
  }

  /**
   * Setter.
   * @see #finalDistributionAmount
   * @param finalDistributionAmount
   */
  public void setFinalDistributionAmount(double finalDistributionAmount)
  {
    this.finalDistributionAmount = finalDistributionAmount;
  }

  /**
   * Getter.
   * @see #premiumTaxes
   * @return
   */
  public double getPremiumTaxes()
  {
    return premiumTaxes;
  }

  /**
   * Setter.
   * @see #premiumTaxes
   * @param premiumTaxes
   */
  public void setPremiumTaxes(double premiumTaxes)
  {
    this.premiumTaxes = premiumTaxes;
  }

  /**
   * Getter.
   * @see #frontEndLoads
   * @return
   */
  public double getFrontEndLoads()
  {
    return frontEndLoads;
  }

  /**
   * Setter.
   * @see #frontEndLoads
   * @param frontEndLoads
   */
  public void setFrontEndLoads(double frontEndLoads)
  {
    this.frontEndLoads = frontEndLoads;
  }

  /**
   * Getter.
   * @see #fees
   * @return
   */
  public double getFees()
  {
    return fees;
  }

  /**
   * Setter.
   * @see #fees
   * @param fees
   */
  public void setFees(double fees)
  {
    this.fees = fees;
  }

  /**
   * Getter.
   * @see #totalProjectedAnnuity
   * @return
   */
  public double getTotalProjectedAnnuity()
  {
    return totalProjectedAnnuity;
  }

  /**
   * Setter.
   * @see #totalProjectedAnnuity
   * @param totalProjectedAnnuity
   */
  public void setTotalProjectedAnnuity(double totalProjectedAnnuity)
  {
    this.totalProjectedAnnuity = totalProjectedAnnuity;
  }

  /**
   * Getter.
   * @see #exclusionRatio
   * @return
   */
  public double getExclusionRatio()
  {
    return exclusionRatio;
  }

  /**
   * Setter.
   * @see #exclusionRatio
   * @param exclusionRatio
   */
  public void setExclusionRatio(double exclusionRatio)
  {
    this.exclusionRatio = exclusionRatio;
  }

  /**
   * Getter.
   * @see #yearlyTaxableBenefit
   * @return
   */
  public double getYearlyTaxableBenefit()
  {
    return yearlyTaxableBenefit;
  }

  /**
   * Setter.
   * @see #yearlyTaxableBenefit
   * @param yearlyTaxableBenefit
   */
  public void setYearlyTaxableBenefit(double yearlyTaxableBenefit)
  {
    this.yearlyTaxableBenefit = yearlyTaxableBenefit;
  }

  /**
   * Getter.
   * @see #commutedValue
   * @return
   */
  public double getCommutedValue()
  {
    return commutedValue;
  }

  /**
   * Setter.
   * @see #commutedValue
   * @param commutedValue
   */
  public void setCommutedValue(double commutedValue)
  {
    this.commutedValue = commutedValue;
  }

  /**
   * Getter.
   * @see #purchaseAmount
   * @return
   */
  public double getPurchaseAmount()
  {
    return purchaseAmount;
  }

  /**
   * Setter.
   * @see #purchaseAmount
   * @param purchaseAmount
   */
  public void setPurchaseAmount(double purchaseAmount)
  {
    this.purchaseAmount = purchaseAmount;
  }

  /**
   * Getter.
   * @see #paymentAmount
   * @return
   */
  public double getPaymentAmount()
  {
    return paymentAmount;
  }

  /**
   * Setter.
   * @see #paymentAmount
   * @param paymentAmount
   */
  public void setPaymentAmount(double paymentAmount)
  {
    this.paymentAmount = paymentAmount;
  }
}
