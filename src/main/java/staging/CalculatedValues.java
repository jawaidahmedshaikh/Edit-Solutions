 /*
  * User: dlataill
  * Date: Oct 9, 2007
  * Time: 9:48:44 AM

  * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
  * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
  * subject to the license agreement.
  */

 package staging;

 import edit.services.db.hibernate.HibernateEntity;
 import edit.services.db.hibernate.SessionHelper;
 import edit.common.EDITBigDecimal;
 import edit.common.EDITDate;
 import edit.common.vo.CalculatedValuesVO;

 public class CalculatedValues extends HibernateEntity implements IStaging
 {
     private Long calculatedValuesPK;
     private Long segmentBaseFK;
     private EDITBigDecimal cashValue;
     private EDITBigDecimal cashSurrenderValue;
     private EDITBigDecimal loanPrincipal;
     private EDITBigDecimal unitValues;
     private EDITBigDecimal reserveAmount;
     private EDITBigDecimal paidUpTermVested;
     private EDITBigDecimal loanBalance;
     private EDITDate lastLoanActivityDate;
     private EDITDate lastLoanDate;
     private int policyDuration;
     private EDITBigDecimal accumLTCClaims;
     private EDITBigDecimal accumHomeCareClaims;
     private int ltcNumberOfMonthsPaid;
     private int homeCareNumberOfMonthsPaid;
     private EDITBigDecimal accumWithdrawalAmount;
     private EDITBigDecimal accumWithdrawalUnits;
     private EDITBigDecimal accumCriticalCareAmount;
     private EDITDate accountingDate;
     private String exitCode;
     private EDITDate paidUpTermDate;
     private int reducedDBAge;
     private EDITBigDecimal ltcConfinementAmount;
     private EDITBigDecimal ltcHHCAmount;
     private int ltcWaitingPeriod;
     private int ltcEliminationPeriod;
     private EDITBigDecimal guarPaidUpPurchasePremium;
     private EDITBigDecimal currentInterestRate;
     private EDITBigDecimal contractSurrValGuar;
     private EDITBigDecimal currentMVACharge;
     private EDITBigDecimal guaranteedPremiumPercent;
     private EDITBigDecimal priorYearEndAccountValue;
     private EDITBigDecimal priorYearEndCashSurrenderValue;
     private EDITBigDecimal priorYearEndMinAcctValue;
     private EDITBigDecimal priorYearSurrenderValue;
     private EDITBigDecimal totalMinAccountValue;
     private EDITBigDecimal withdrawalSinceInception;
     private EDITBigDecimal coverageFee;
     private EDITBigDecimal premiumsPaid;

     private SegmentBase segmentBase;

     private CalculatedValuesVO calculatedValuesVO;

     /**
      * Target database to be used for lookups, etc.
      */
     public static String DATABASE = SessionHelper.STAGING;

     public CalculatedValues()
     {
     }

     public CalculatedValues(CalculatedValuesVO calculatedValuesVO)
     {
         this.calculatedValuesVO = calculatedValuesVO;
     }

     /**
      * Getter.
      * @return
      */
     public Long getCalculatedValuesPK()
     {
         return calculatedValuesPK;
     }

     /**
      * Setter.
      * @param calculatedValuesPK
      */
     public void setCalculatedValuesPK(Long calculatedValuesPK)
     {
         this.calculatedValuesPK = calculatedValuesPK;
     }

     /**
      * Getter.
      * @return
      */
     public Long getSegmentBaseFK()
     {
         return segmentBaseFK;
     }

     /**
      * Setter.
      * @param segmentBaseFK
      */
     public void setSegmentBaseFK(Long segmentBaseFK)
     {
         this.segmentBaseFK = segmentBaseFK;
     }

     public SegmentBase getSegmentBase()
     {
         return segmentBase;
     }

     public void setSegmentBase(SegmentBase segmentBase)
     {
         this.segmentBase = segmentBase;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getCashValue()
     {
         return cashValue;
     }

     /**
      * Setter.
      * @param cashValue
      */
     public void setCashValue(EDITBigDecimal cashValue)
     {
         this.cashValue = cashValue;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getCashSurrenderValue()
     {
         return cashSurrenderValue;
     }

     /**
      * Setter.
      * @param cashSurrenderValue
      */
     public void setCashSurrenderValue(EDITBigDecimal cashSurrenderValue)
     {
         this.cashSurrenderValue = cashSurrenderValue;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getLoanPrincipal()
     {
         return loanPrincipal;
     }

     /**
      * Setter.
      * @param loanPrincipal
      */
     public void setLoanPrincipal(EDITBigDecimal loanPrincipal)
     {
         this.loanPrincipal = loanPrincipal;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getUnitValues()
     {
         return unitValues;
     }

     /**
      * Setter.
      * @param unitValues
      */
     public void setUnitValues(EDITBigDecimal unitValues)
     {
         this.unitValues = unitValues;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getReserveAmount()
     {
         return reserveAmount;
     }

     /**
      * Setter.
      * @param reserveAmount
      */
     public void setReserveAmount(EDITBigDecimal reserveAmount)
     {
         this.reserveAmount = reserveAmount;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getPaidUpTermVested()
     {
         return paidUpTermVested;
     }

     /**
      * Setter.
      * @param paidUpTermVested
      */
     public void setPaidUpTermVested(EDITBigDecimal paidUpTermVested)
     {
         this.paidUpTermVested = paidUpTermVested;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getLoanBalance()
     {
         return loanBalance;
     }

     /**
      * Setter.
      * @param loanBalance
      */
     public void setLoanBalance(EDITBigDecimal loanBalance)
     {
         this.loanBalance = loanBalance;
     }

     /**
      * Getter.
      * @return
      */
     public EDITDate getLastLoanActivityDate()
     {
         return lastLoanActivityDate;
     }

     /**
      * Setter.
      * @param lastLoanActivityDate
      */
     public void setLastLoanActivityDate(EDITDate lastLoanActivityDate)
     {
         this.lastLoanActivityDate = lastLoanActivityDate;
     }

     /**
      * Getter.
      * @return
      */
     public EDITDate getLastLoanDate()
     {
         return lastLoanDate;
     }

     /**
      * Setter.
      * @param lastLoanDate
      */
     public void setLastLoanDate(EDITDate lastLoanDate)
     {
         this.lastLoanDate = lastLoanDate;
     }

     /**
      * Getter.
      * @return
      */
     public int getPolicyDuration()
     {
         return policyDuration;
     }

     /**
      * Setter.
      * @param policyDuration
      */
     public void setPolicyDuration(int policyDuration)
     {
         this.policyDuration = policyDuration;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getAccumLTCClaims()
     {
         return accumLTCClaims;
     }

     /**
      * Setter.
      * @param accumLTCClaims
      */
     public void setAccumLTCClaims(EDITBigDecimal accumLTCClaims)
     {
         this.accumLTCClaims = accumLTCClaims;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getAccumHomeCareClaims()
     {
         return accumHomeCareClaims;
     }

     /**
      * Setter.
      * @param accumHomeCareClaims
      */
     public void setAccumHomeCareClaims(EDITBigDecimal accumHomeCareClaims)
     {
         this.accumHomeCareClaims = accumHomeCareClaims;
     }

     /**
      * Getter.
      * @return
      */
     public int getLTCNumberOfMonthsPaid()
     {
         return ltcNumberOfMonthsPaid;
     }

     /**
      * Setter.
      * @param ltcNumberOfMonthsPaid
      */
     public void setLTCNumberOfMonthsPaid(int ltcNumberOfMonthsPaid)
     {
         this.ltcNumberOfMonthsPaid = ltcNumberOfMonthsPaid;
     }

     /**
      * Getter.
      * @return
      */
     public int getHomeCareNumberOfMonthsPaid()
     {
         return homeCareNumberOfMonthsPaid;
     }

     /**
      * Setter.
      * @param homeCareNumberOfMonthsPaid
      */
     public void setHomeCareNumberOfMonthsPaid(int homeCareNumberOfMonthsPaid)
     {
         this.homeCareNumberOfMonthsPaid = homeCareNumberOfMonthsPaid;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getAccumWithdrawalAmount()
     {
         return accumWithdrawalAmount;
     }

     /**
      * Setter.
      * @param accumWithdrawalAmount
      */
     public void setAccumWithdrawalAmount(EDITBigDecimal accumWithdrawalAmount)
     {
         this.accumWithdrawalAmount = accumWithdrawalAmount;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getAccumWithdrawalUnits()
     {
         return accumWithdrawalUnits;
     }

     /**
      * Setter.
      * @param accumWithdrawalUnits
      */
     public void setAccumWithdrawalUnits(EDITBigDecimal accumWithdrawalUnits)
     {
         this.accumWithdrawalUnits = accumWithdrawalUnits;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getAccumCriticalCareAmount()
     {
         return accumCriticalCareAmount;
     }

     /**
      * Setter.
      * @param accumCriticalCareAmount
      */
     public void setAccumCriticalCareAmount(EDITBigDecimal accumCriticalCareAmount)
     {
         this.accumCriticalCareAmount = accumCriticalCareAmount;
     }

     /**
      * Getter.
      * @return
      */
     public String getExitCode()
     {
         return exitCode;
     }

     /**
      * Setter.
      * @param exitCode
      */
     public void setExitCode(String exitCode)
     {
         this.exitCode = exitCode;
     }

     /**
      * Getter.
      * @return
      */
     public EDITDate getAccountingDate()
     {
         return accountingDate;
     }

     /**
      * Setter.
      * @param accountingDate
      */
     public void setAccountingDate(EDITDate accountingDate)
     {
         this.accountingDate = accountingDate;
     }

     /**
      * Getter.
      * @return
      */
     public EDITDate getPaidUpTermDate()
     {
         return paidUpTermDate;
     }

     /**
      * Setter.
      * @param paidUpTermDate
      */
     public void setPaidUpTermDate(EDITDate paidUpTermDate)
     {
         this.paidUpTermDate = paidUpTermDate;
     }

     /**
      * Getter.
      * @return
      */
     public int getReducedDBAge()
     {
         return reducedDBAge;
     }

     /**
      * Setter.
      * @param reducedDBAge
      */
     public void setReducedDBAge(int reducedDBAge)
     {
         this.reducedDBAge = reducedDBAge;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getLTCConfinementAmount()
     {
         return ltcConfinementAmount;
     }

     /**
      * Setter.
      * @param ltcConfinementAmount
      */
     public void setLTCConfinementAmount(EDITBigDecimal ltcConfinementAmount)
     {
         this.ltcConfinementAmount = ltcConfinementAmount;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getLTCHHCAmount()
     {
         return ltcHHCAmount;
     }

     /**
      * Setter.
      * @param ltcHHCAmount
      */
     public void setLTCHHCAmount(EDITBigDecimal ltcHHCAmount)
     {
         this.ltcHHCAmount = ltcHHCAmount;
     }

     /**
      * Getter.
      * @return
      */
     public int getLTCWaitingPeriod()
     {
         return ltcWaitingPeriod;
     }

     /**
      * Setter.
      * @param ltcWaitingPeriod
      */
     public void setLTCWaitingPeriod(int ltcWaitingPeriod)
     {
         this.ltcWaitingPeriod = ltcWaitingPeriod;
     }

     /**
      * Getter.
      * @return
      */
     public int getLTCEliminationPeriod()
     {
         return ltcEliminationPeriod;
     }

     /**
      * Setter.
      * @param ltcEliminationPeriod
      */
     public void setLTCEliminationPeriod(int ltcEliminationPeriod)
     {
         this.ltcEliminationPeriod = ltcEliminationPeriod;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getGuarPaidUpPurchasePremium()
     {
         return guarPaidUpPurchasePremium;
     }

     /**
      * Setter.
      * @param guarPaidUpPurchasePremium
      */
     public void setGuarPaidUpPurchasePremium(EDITBigDecimal guarPaidUpPurchasePremium)
     {
         this.guarPaidUpPurchasePremium = guarPaidUpPurchasePremium;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getCurrentInterestRate()
     {
         return currentInterestRate;
     }

     /**
      * Setter.
      * @param currentInterestRate
      */
     public void setCurrentInterestRate(EDITBigDecimal currentInterestRate)
     {
         this.currentInterestRate = currentInterestRate;
     }

     public String getDatabase()
     {
         return CalculatedValues.DATABASE;
     }
     
    /**
     * Setter.
     * @param contractSurrValGuar
     */
    public void setContractSurrValGuar(EDITBigDecimal contractSurrValGuar)
    {
        this.contractSurrValGuar = contractSurrValGuar;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getContractSurrValGuar()
    {
        return contractSurrValGuar;
    }

    /**
     * Setter.
     * @param currentMVACharge
     */
    public void setCurrentMVACharge(EDITBigDecimal currentMVACharge)
    {
        this.currentMVACharge = currentMVACharge;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCurrentMVACharge()
    {
        return currentMVACharge;
    }

    /**
     * Setter.
     * @param guarenteedPremiumPercent
     */
    public void setGuaranteedPremiumPercent(EDITBigDecimal guarenteedPremiumPercent)
    {
        this.guaranteedPremiumPercent = guarenteedPremiumPercent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuaranteedPremiumPercent()
    {
        return guaranteedPremiumPercent;
    }

    /**
     * Setter.
     * @param priorYearEndAccountValue
     */
    public void setPriorYearEndAccountValue(EDITBigDecimal priorYearEndAccountValue)
    {
        this.priorYearEndAccountValue = priorYearEndAccountValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorYearEndAccountValue()
    {
        return priorYearEndAccountValue;
    }

    /**
     * Setter.
     * @param priorYearEndCashSurrenderValue
     */
    public void setPriorYearEndCashSurrenderValue(EDITBigDecimal priorYearEndCashSurrenderValue)
    {
        this.priorYearEndCashSurrenderValue = priorYearEndCashSurrenderValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorYearEndCashSurrenderValue()
    {
        return priorYearEndCashSurrenderValue;
    }

    /**
     * Setter.
     * @param priorYearEndMinAcctValue
     */
    public void setPriorYearEndMinAcctValue(EDITBigDecimal priorYearEndMinAcctValue)
    {
        this.priorYearEndMinAcctValue = priorYearEndMinAcctValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorYearEndMinAcctValue()
    {
        return priorYearEndMinAcctValue;
    }

    /**
     * Setter.
     * @param priorYearSurrenderValue
     */
    public void setPriorYearSurrenderValue(EDITBigDecimal priorYearSurrenderValue)
    {
        this.priorYearSurrenderValue = priorYearSurrenderValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPriorYearSurrenderValue()
    {
        return priorYearSurrenderValue;
    }

    /**
     * Setter.
     * @param totalMinAccountValue
     */
    public void setTotalMinAccountValue(EDITBigDecimal totalMinAccountValue)
    {
        this.totalMinAccountValue = totalMinAccountValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalMinAccountValue()
    {
        return totalMinAccountValue;
    }

    /**
     * Setter.
     * @param withdrawalSinceInception
     */
    public void setWithdrawalSinceInception(EDITBigDecimal withdrawalSinceInception)
    {
        this.withdrawalSinceInception = withdrawalSinceInception;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getWithdrawalSinceInception()
    {
        return withdrawalSinceInception;
    }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getCoverageFee()
     {
         return coverageFee;
     }

     /**
      * Setter.
      * @param coverageFee
      */
     public void setCoverageFee(EDITBigDecimal coverageFee)
     {
         this.coverageFee = coverageFee;
     }

     /**
      * Getter.
      * @return
      */
     public EDITBigDecimal getPremiumsPaid()
     {
         return premiumsPaid;
     }

     /**
      * Setter.
      * @param premiumsPaid
      */
     public void setPremiumsPaid(EDITBigDecimal premiumsPaid)
     {
         this.premiumsPaid = premiumsPaid;
     }

     public StagingContext stage(StagingContext stagingContext, String database)
     {
         this.setSegmentBase(stagingContext.getCurrentSegmentBase());
         this.setCashValue(new EDITBigDecimal(calculatedValuesVO.getCashValue()));
         this.setCashSurrenderValue(new EDITBigDecimal(calculatedValuesVO.getCashSurrenderValue()));
         this.setLoanPrincipal(new EDITBigDecimal(calculatedValuesVO.getLoanPrincipal()));
         if (calculatedValuesVO.getLastLoanActivityDate() != null)
         {
             this.setLastLoanActivityDate(new EDITDate(calculatedValuesVO.getLastLoanActivityDate()));
         }
         if (calculatedValuesVO.getLastLoanDate() != null)
         {
             this.setLastLoanDate(new EDITDate(calculatedValuesVO.getLastLoanDate()));
         }
         this.setPolicyDuration(calculatedValuesVO.getPolicyDuration());
         this.setAccumLTCClaims(new EDITBigDecimal(calculatedValuesVO.getAccumLTCClaims()));
         this.setAccumHomeCareClaims(new EDITBigDecimal(calculatedValuesVO.getAccumHomeCareClaims()));
         this.setLTCNumberOfMonthsPaid(calculatedValuesVO.getLTCNumberOfMonthsPaid());
         this.setHomeCareNumberOfMonthsPaid(calculatedValuesVO.getHomeCareNumberOfMonthsPaid());
         this.setAccumWithdrawalAmount(new EDITBigDecimal(calculatedValuesVO.getAccumWithdrawalAmount()));
         this.setAccumWithdrawalUnits(new EDITBigDecimal(calculatedValuesVO.getAccumWithdrawalUnits()));
         this.setAccumCriticalCareAmount(new EDITBigDecimal(calculatedValuesVO.getAccumCriticalCareAmount()));
         if (calculatedValuesVO.getPaidUpTermDate() != null)
         {
             this.setPaidUpTermDate(new EDITDate(calculatedValuesVO.getPaidUpTermDate()));
         }
         this.setReducedDBAge(calculatedValuesVO.getReducedDBAge());
         this.setLTCConfinementAmount(new EDITBigDecimal(calculatedValuesVO.getLTCConfinementAmount()));
         this.setLTCHHCAmount(new EDITBigDecimal(calculatedValuesVO.getLTCHHCAmount()));
         this.setLTCWaitingPeriod(calculatedValuesVO.getLTCWaitingPeriod());
         this.setLTCEliminationPeriod(calculatedValuesVO.getLTCEliminationPeriod());
         this.setGuarPaidUpPurchasePremium(new EDITBigDecimal(calculatedValuesVO.getGuarPaidUpPurchasePremium()));
         this.setCurrentInterestRate(new EDITBigDecimal(calculatedValuesVO.getCurrentInterestRate()));
         this.setContractSurrValGuar(new EDITBigDecimal(calculatedValuesVO.getContractSurrValGuar()));
         this.setCurrentMVACharge(new EDITBigDecimal(calculatedValuesVO.getCurrentMVACharge()));
         this.setGuaranteedPremiumPercent(new EDITBigDecimal(calculatedValuesVO.getGuaranteedPremiumPercent()));
         this.setPriorYearEndAccountValue(new EDITBigDecimal(calculatedValuesVO.getPriorYearEndAccountValue()));
         this.setPriorYearEndCashSurrenderValue(new EDITBigDecimal(calculatedValuesVO.getPriorYearEndCashSurrenderValue()));
         this.setPriorYearEndMinAcctValue(new EDITBigDecimal(calculatedValuesVO.getPriorYearEndMinAcctValue()));
         this.setPriorYearSurrenderValue(new EDITBigDecimal(calculatedValuesVO.getPriorYearSurrenderValue()));
         this.setTotalMinAccountValue(new EDITBigDecimal(calculatedValuesVO.getTotalMinAccountValue()));
         this.setWithdrawalSinceInception(new EDITBigDecimal(calculatedValuesVO.getWithdrawalSinceInception()));
         this.setCoverageFee(new EDITBigDecimal(calculatedValuesVO.getCoverageFee()));
         this.setPremiumsPaid(new EDITBigDecimal(calculatedValuesVO.getPremiumsPaid()));

         stagingContext.getCurrentSegmentBase().addCalculatedValues(this);
    
         SessionHelper.saveOrUpdate(this, database);

         return stagingContext;
     }
}


