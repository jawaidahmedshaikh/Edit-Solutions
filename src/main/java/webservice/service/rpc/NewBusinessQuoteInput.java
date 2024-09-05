/*
 * User: sprasad
 * Date: Nov 20, 2006
 * Time: 5:01:37 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice.service.rpc;

import java.util.Calendar;

/**
 * NewBusinessQuoteInput is a value object that represents the required input data to run the
 * New Business Quote. It is a compliment to the NewBusinessQuoteOutput.
 * Even though all of the input parameters are required, not all of them will be used
 * by the service since some of the parameters are superfluous and will be
 * ignored. This presents various input scenarios. Currently, those scenarios are:
 * <br><br>
 * SCENARIO ONE: Payment Amount and Issue Age are ignored.
 * <br>SCENARIO TWO: Payment Amount and Birh Date are ignored.
 * <br>SCENARIO THREE: Purchase Amount and Issue Age are ignored.
 * <br>SCENARIO FOUR: Purchase Amount and Birth Date are ignored.
 * <br><br>
 * DETAILED EXPLANATION:
 * <br><br>
 * The user of the New Quote Service should ALWAYS supply legal values
 * for every field defined in this NewBusinessQuoteInput. However, the 
 * service will not use all of the supplied values. The parameter values
 * that are ignored are based on the specified SCENARIO.
 * <br><br>
 * TO RUN THE SERVICE:
 * <br><br>
 * a) Supply legitimate values for all of the parameters.
 * <br>quoteDate = '01-02-2003'
 * <br>productStructureId = 1
 * <br>annuityOption = 'LOA'
 * <br>issueState = 'CT'
 * <br>frequency = 'Monthly'
 * <br>costBasis = 50000.00
 * <br>certainDuration = 0
 * <br>effectiveDate = '01-02-2003'
 * <br>startDate = '01-02-2003'
 * <br>postJune1986InvestmentInd = 'N'
 * <br>gender = 'M'
 * <br>purchaseAmount = 50000.00
 * <br>paymentAmount = 0
 * <br>issueAge = 33
 * <br>birthDate = '01-01-1977'
 * <br><br>
 * b) Specify the scenario to run
 * <br><br>
 * if SCENARIO ONE (use int = 1) then paymentAmount and issueAge will be ignored
 * <br>if SCENARIO TWO (use int = 2) then paymentAmount and birthDate will be ignored
 * <br>if SCENARIO THREE (use int = 3) then purchaseAmount and issueAge will be ignored
 * <br>if SCENARIO FOUR (use int = 4) then purchaseAmount and birthDate will be ignored.
 * <br><br>
 * c) Run 
 * @see NewBusinessQuote
 */
public class NewBusinessQuoteInput
{

  /**
   * To use NewBusinessQuote service there are four different scenarios to consider.
   * <br><br>Scenario One: Payment Amount and Issue Age are ignored.
   */
  public static final int SCENARIO_ONE = 1;
  
  /**
   * To use NewBusinessQuote service there are four different scenarios to consider.
   * <br><br>Scenario Two: Payment Amount and Birh Date are ignored.
   */  
  public static final int SCENARIO_TWO = 2;
  
  /**
   * To use NewBusinessQuote service there are four different scenarios to consider.
   * <br><br>Scenario Three: Purchase Amount and Issue Age are ignored.
   */  
  public static final int SCENARIO_THREE = 3;
  
  /**
   * To use NewBusinessQuote service there are four different scenarios to consider.
   * <br><br>Scenario Four: Payment Amount and Birth Date are ignored.
   */  
  public static final int SCENARIO_FOUR = 4; 
  
  
  /**
   * The New Business Quote scenario to invoke in the 
   * list of possible scenarios.
   * @see #SCENARIO_ONE
   * @see #SCENARIO_TWO
   * @see #SCENARIO_THREE
   * @see #SCENARIO_FOUR.
   */
  protected int scenario;

  /**
   * Date the Payout Quote is performed.
   */
  protected java.util.Calendar quoteDate;

  /**
   * The product key.
   */
  protected String productStructureId;

  /**
   * Payout option defined for the contract.
   */
  protected String annuityOption;

  /**
   * State of issue for contract.
   */
  protected String issueState;

  /**
   * Modal Payout Frequency.
   */
  protected String frequency;

  /**
   * Amount of previously taxed contributions.
   */
  protected double costBasis;

  /**
   * Guaranteed duration for the Modal Payout.
   */
  protected int certainDuration;

  /**
   * Amount of money contributed to contract.
   */
  protected double purchaseAmount;

  /**
   * Modal payout amount.
   */
  protected double paymentAmount;

  /**
   * Effective Date of the contract.
   */
  protected java.util.Calendar effectiveDate;

  /**
   * Start Date of the Modal Payout for the contract.
   */
  protected java.util.Calendar startDate;

  /**
   * Indicator for Payout contract re:Post86 Investment.
   */
  protected String postJune1986InvestmentInd;

  /**
   * Date of Birth of client.
   */
  protected java.util.Calendar birthDate;

  /**
   * Age at issue of Client associated with contract.
   */
  protected int issueAge;

  /**
   * Gender of Client.
   */
  protected String gender;

  /**
   * Getter.
   * @see #scenario
   * @return
   */
  public int getScenario()
  {
    return scenario;
  }

  /**
   * Setter.
   * @see #scenario
   * @param scenario
   */
  public void setScenario(int scenario)
  {
    this.scenario = scenario;
  }

  /**
   * Getter.
   * @see #quoteDate
   * @return
   */
  public Calendar getQuoteDate()
  {
    return quoteDate;
  }

  /**
   * Setter.
   * @see #quoteDate
   * @param quoteDate
   */
  public void setQuoteDate(Calendar quoteDate)
  {
    this.quoteDate = quoteDate;
  }

  /**
   * Getter.
   * @see #productStructureId
   * @return
   */
  public String getProductStructureId()
  {
    return productStructureId;
  }

  /**
   * Setter.
   * @see #productStructureId
   * @param productStructureId
   */
  public void setProductStructureId(String productStructureId)
  {
    this.productStructureId = productStructureId;
  }

  /**
   * Getter.
   * @see #annuityOption
   * @return
   */
  public String getAnnuityOption()
  {
    return annuityOption;
  }

  /**
   * Setter.
   * @see #annuityOption
   * @param annuityOption
   */
  public void setAnnuityOption(String annuityOption)
  {
    this.annuityOption = annuityOption;
  }

  /**
   * Getter.
   * @see #issueState
   * @return
   */
  public String getIssueState()
  {
    return issueState;
  }

  /**
   * Setter.
   * @see #issueState
   * @param issueState
   */
  public void setIssueState(String issueState)
  {
    this.issueState = issueState;
  }

  /**
   * Getter.
   * @see #frequency
   * @return
   */
  public String getFrequency()
  {
    return frequency;
  }

  /**
   * Setter.
   * @see #frequency
   * @param frequency
   */
  public void setFrequency(String frequency)
  {
    this.frequency = frequency;
  }

  /**
   * Getter.
   * @see #costBasis
   * @return
   */
  public double getCostBasis()
  {
    return costBasis;
  }

  /**
   * Setter.
   * @see #costBasis
   * @param costBasis
   */
  public void setCostBasis(double costBasis)
  {
    this.costBasis = costBasis;
  }

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

  /**
   * Getter.
   * @see #effectiveDate
   * @return
   */
  public Calendar getEffectiveDate()
  {
    return effectiveDate;
  }

  /**
   * Setter.
   * @see #effectiveDate
   * @param effectiveDate
   */
  public void setEffectiveDate(Calendar effectiveDate)
  {
    this.effectiveDate = effectiveDate;
  }

  /**
   * Getter.
   * @see #startDate
   * @return
   */
  public Calendar getStartDate()
  {
    return startDate;
  }

  /**
   * Setter.
   * @see #startDate
   * @param startDate
   */
  public void setStartDate(Calendar startDate)
  {
    this.startDate = startDate;
  }

  /**
   * Getter.
   * @see #postJune1986InvestmentInd
   * @return
   */
  public String getPostJune1986InvestmentInd()
  {
    return postJune1986InvestmentInd;
  }

  /**
   * Setter.
   * @see #postJune1986InvestmentInd
   * @param postJune1986InvestmentInd
   */
  public void setPostJune1986InvestmentInd(String postJune1986InvestmentInd)
  {
    this.postJune1986InvestmentInd = postJune1986InvestmentInd;
  }

  /**
   * Getter.
   * @see #birthDate
   * @return
   */
  public Calendar getBirthDate()
  {
    return birthDate;
  }

  /**
   * Setter.
   * @see #birthDate
   * @param birthDate
   */
  public void setBirthDate(Calendar birthDate)
  {
    this.birthDate = birthDate;
  }

  /**
   * Getter.
   * @see #issueAge
   * @return
   */
  public int getIssueAge()
  {
    return issueAge;
  }

  /**
   * Setter.
   * @see #issueAge
   * @param issueAge
   */
  public void setIssueAge(int issueAge)
  {
    this.issueAge = issueAge;
  }

  /**
   * Getter.
   * @see #gender
   * @return
   */
  public String getGender()
  {
    return gender;
  }

  /**
   * Getter.
   * @see #gender
   * @param gender
   */
  public void setGender(String gender)
  {
    this.gender = gender;
  }
}
