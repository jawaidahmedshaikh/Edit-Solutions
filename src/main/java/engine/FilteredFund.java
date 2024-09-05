/*
 * User: gfrosti
 * Date: Nov 17, 2003
 * Time: 4:16:24 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.common.*;

import edit.common.exceptions.*;

import edit.common.vo.*;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITEngineException;
import edit.services.db.*;
import edit.services.db.hibernate.*;

import engine.dm.dao.*;

import java.util.*;
import org.hibernate.Session;



public class FilteredFund extends HibernateEntity implements CRUDEntity
{
    public static String DATABASE = SessionHelper.ENGINE;

    public static final String BUSINESS_NOTIFICATION_TYPE_CT = "Business";
    public static final String CALENDAR_NOTIFICATION_TYPE_CT = "Calendar";
    public static final String MONTHLY_NOTIFICATION_MODE_CT = "Monthly";
    public static final String QUARTERLY_NOTIFICATION_MODE_CT = "Quarterly";
    public static final String SEMI_ANNUAL_NOTIFICATION_MODE_CT = "SemiAnnual";
    public static final String ANNUAL_NOTIFICATION_MODE_CT = "Annual";
    private FilteredFundVO filteredFundVO;
    private FilteredFundImpl filteredFundImpl;
    private Fund fund;
    private Set productStructures = new HashSet();
    private Set chargeCodes = new HashSet();
    private Set unitValues = new HashSet();
    
    /**
     * The set of FilteredFunds that are associated with General Account Funds
     * (i.e. with Fund.FundType = System and Fund.LoanQualifierCT != null).
     * They are cached after their first retrieval.
     */ 
    private static FilteredFund[] generalAccountFilteredFunds;
    

    public static final String CORRESPONDENCE_INDICATOR_YES = "Y";
    public static final String CORRESPONDENCE_INDICATOR_NO = "N";

    private Set fees;


    public FilteredFund()
    {
        this.filteredFundVO = new FilteredFundVO();
        this.filteredFundImpl = new FilteredFundImpl();
    }

    public FilteredFund(long filteredFundPK) throws Exception
    {
        this();
        this.filteredFundImpl.load(this, filteredFundPK);
    }

    public FilteredFund(FilteredFundVO filteredFundVO)
    {
        this();
        this.filteredFundVO = filteredFundVO;
    }

    /**
     * Getter.
     * @return
     */
    public String getAnnualSubBucketCT()
    {
        return filteredFundVO.getAnnualSubBucketCT();
    }
     //-- java.lang.String getAnnualSubBucketCT()

    /**
     * Getter.
     * @return
     */
    public int getCOIReplenishmentDays()
    {
        return filteredFundVO.getCOIReplenishmentDays();
    }
     //-- int getCOIReplenishmentDays()

    /**
     * Getter.
     * @return
     */
    public String getCOIReplenishmentDaysTypeCT()
    {
        return filteredFundVO.getCOIReplenishmentDaysTypeCT();
    }
     //-- java.lang.String getCOIReplenishmentDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getCOIReplenishmentModeCT()
    {
        return filteredFundVO.getCOIReplenishmentModeCT();
    }
     //-- java.lang.String getCOIReplenishmentModeCT()

    /**
     * Getter.
     * @return
     */
    public String getChargeCodeIndicator()
    {
        return filteredFundVO.getChargeCodeIndicator();
    }
     //-- java.lang.String getChargeCodeIndicator()

    /**
     * Getter.
     * @return
     */
    public int getContributionLockUpDuration()
    {
        return filteredFundVO.getContributionLockUpDuration();
    }
     //-- int getContributionLockUpDuration()

    /**
     * Getter.
     * @return
     */
    public int getDeathDays()
    {
        return filteredFundVO.getDeathDays();
    }
     //-- int getDeathDays()

    /**
     * Getter.
     * @return
     */
    public String getDeathDaysTypeCT()
    {
        return filteredFundVO.getDeathDaysTypeCT();
    }
     //-- java.lang.String getDeathDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getDeathModeCT()
    {
        return filteredFundVO.getDeathModeCT();
    }
     //-- java.lang.String getDeathModeCT()

    /**
     * Getter.
     * @return
     */
    public String getDivFeesLiquidatnDaysTypeCT()
    {
        return filteredFundVO.getDivFeesLiquidatnDaysTypeCT();
    }
     //-- java.lang.String getDivFeesLiquidatnDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public int getDivisionFeesLiquidationDays()
    {
        return filteredFundVO.getDivisionFeesLiquidationDays();
    }
     //-- int getDivisionFeesLiquidationDays()

    /**
     * Getter.
     * @return
     */
    public String getDivisionFeesLiquidationModeCT()
    {
        return filteredFundVO.getDivisionFeesLiquidationModeCT();
    }
     //-- java.lang.String getDivisionFeesLiquidationModeCT()

    /**
     * Getter.
     * @return
     */
    public int getDivisionLevelLockUpDuration()
    {
        return filteredFundVO.getDivisionLevelLockUpDuration();
    }
     //-- int getDivisionLevelLockUpDuration()

    /**
     * Getter.
     * @return
     */
    public EDITDate getDivisionLockUpEndDate()
    {
        return SessionHelper.getEDITDate(filteredFundVO.getDivisionLockUpEndDate());
    }
     //-- java.lang.String getDivisionLockUpEndDate()

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(filteredFundVO.getEffectiveDate());
    }
     //-- java.lang.String getEffectiveDate()

    /**
     * Getter.
     * @return
     */
    public Long getFilteredFundPK()
    {
        return SessionHelper.getPKValue(filteredFundVO.getFilteredFundPK());
    }
     //-- long getFilteredFundPK()

    /**
     * Getter.
     * @return
     */
    public int getFullSurrenderDays()
    {
        return filteredFundVO.getFullSurrenderDays();
    }
     //-- int getFullSurrenderDays()

    /**
     * Getter.
     * @return
     */
    public String getFullSurrenderDaysTypeCT()
    {
        return filteredFundVO.getFullSurrenderDaysTypeCT();
    }
     //-- java.lang.String getFullSurrenderDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getFullSurrenderModeCT()
    {
        return filteredFundVO.getFullSurrenderModeCT();
    }
     //-- java.lang.String getFullSurrenderModeCT()

    /**
     * Getter.
     * @return
     */
    public String getFundAdjustmentCT()
    {
        return filteredFundVO.getFundAdjustmentCT();
    }
     //-- java.lang.String getFundAdjustmentCT()

    /**
     * Getter.
     * @return
     */
    public Long getFundFK()
    {
        return SessionHelper.getPKValue(filteredFundVO.getFundFK());
    } //-- long getFundFK()


    /**
     * Getter.
     * @return
     */
    public EDITDate getFundNewClientCloseDate()
    {
        return SessionHelper.getEDITDate(filteredFundVO.getFundNewClientCloseDate());
    }
     //-- java.lang.String getFundNewClientCloseDate()

    /**
     * Getter.
     * @return
     */
    public EDITDate getFundNewDepositCloseDate()
    {
        return SessionHelper.getEDITDate(filteredFundVO.getFundNewDepositCloseDate());
    }
     //-- java.lang.String getFundNewDepositCloseDate()

    /**
     * Getter.
     * @return
     */
    public String getFundNumber()
    {
        return filteredFundVO.getFundNumber();
    }
     //-- java.lang.String getFundNumber()

    /**
     * Getter.
     * @return
     */
    public int getGuaranteedDuration()
    {
        return filteredFundVO.getGuaranteedDuration();
    }
     //-- int getGuaranteedDuration()

    /**
     * Getter.
     * @return
     */
    public Long getHoldingAccountFK()
    {
        return (filteredFundVO.getHoldingAccountFK() == 0) ? null : new Long(filteredFundVO.getHoldingAccountFK());
    }
     //-- long getHoldingAccountFK()


    /**
     * Getter.
     * @return
     */
    public String getIncludeInCorrespondenceInd()
    {
        return filteredFundVO.getIncludeInCorrespondenceInd();
    } //-- java.lang.String getIncludeInCorrespondenceInd()

    /**
     * Getter.
     * @return
     */
    public int getIndexCapRateGuarPeriod()
    {
        return filteredFundVO.getIndexCapRateGuarPeriod();
    }
     //-- int getIndexCapRateGuarPeriod()

    /**
     * Getter.
     * @return
     */
    public String getIndexingMethodCT()
    {
        return filteredFundVO.getIndexingMethodCT();
    }
     //-- java.lang.String getIndexingMethodCT()

    /**
     * Getter.
     * @return
     */
    public int getLoanDays()
    {
        return filteredFundVO.getLoanDays();
    }
     //-- int getLoanDays()

    /**
     * Getter.
     * @return
     */
    public String getLoanDaysTypeCT()
    {
        return filteredFundVO.getLoanDaysTypeCT();
    }
     //-- java.lang.String getLoanDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getLoanModeCT()
    {
        return filteredFundVO.getLoanModeCT();
    }
     //-- java.lang.String getLoanModeCT()

    /**
     * Getter.
     * @return
     */
    public int getMVAStartingIndexGuarPeriod()
    {
        return filteredFundVO.getMVAStartingIndexGuarPeriod();
    }
     //-- int getMVAStartingIndexGuarPeriod()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getMinimumTransferAmount()
    {
        return SessionHelper.getEDITBigDecimal(filteredFundVO.getMinimumTransferAmount());
    }
     //-- java.math.BigDecimal getMinimumTransferAmount()

    /**
     * Getter.
     * @return
     */
    public String getPostLockWithdrawalDateCT()
    {
        return filteredFundVO.getPostLockWithdrawalDateCT();
    }
     //-- java.lang.String getPostLockWithdrawalDateCT()

    /**
     * Getter.
     * @return
     */
    public int getPremiumBonusDuration()
    {
        return filteredFundVO.getPremiumBonusDuration();
    }
     //-- int getPremiumBonusDuration()

    /**
     * Getter.
     * @return
     */
    public String getPricingDirection()
    {
        return filteredFundVO.getPricingDirection();
    }
     //-- java.lang.String getPricingDirection()

    /**
     * Getter.
     * @return
     */
    public String getSeparateAccountName()
    {
        return filteredFundVO.getSeparateAccountName();
    }
     //-- java.lang.String getSeparateAccountName()

    /**
     * Getter.
     * @return
     */
    public String getSubscriptionDaysTypeCT()
    {
        return filteredFundVO.getSubscriptionDaysTypeCT();
    }
     //-- java.lang.String getSubscriptionDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getSubscriptionModeCT()
    {
        return filteredFundVO.getSubscriptionModeCT();
    }
     //-- java.lang.String getSubscriptionModeCT()

    /**
     * Getter.
     * @return
     */
    public int getSubscriptionNotificationDays()
    {
        return filteredFundVO.getSubscriptionNotificationDays();
    }
     //-- int getSubscriptionNotificationDays()

    /**
     * Getter.
     * @return
     */
    public EDITDate getTerminationDate()
    {
        return SessionHelper.getEDITDate(filteredFundVO.getTerminationDate());
    }
     //-- java.lang.String getTerminationDate()

    /**
     * Getter.
     * @return
     */
    public int getTransferDays()
    {
        return filteredFundVO.getTransferDays();
    }
     //-- int getTransferDays()

    /**
     * Getter.
     * @return
     */
    public String getTransferDaysTypeCT()
    {
        return filteredFundVO.getTransferDaysTypeCT();
    }
     //-- java.lang.String getTransferDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getTransferModeCT()
    {
        return filteredFundVO.getTransferModeCT();
    }
     //-- java.lang.String getTransferModeCT()

    /**
     * Getter.
     * @return
     */
    public int getWithdrawalDays()
    {
        return filteredFundVO.getWithdrawalDays();
    }
     //-- int getWithdrawalDays()

    /**
     * Getter.
     * @return
     */
    public String getWithdrawalDaysTypeCT()
    {
        return filteredFundVO.getWithdrawalDaysTypeCT();
    }
     //-- java.lang.String getWithdrawalDaysTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getWithdrawalModeCT()
    {
        return filteredFundVO.getWithdrawalModeCT();
    }
     //-- java.lang.String getWithdrawalModeCT()

    /**
     * Getter.
     * @return
     */
    public String getSeriesToSeriesEligibilityInd()
    {
        return filteredFundVO.getSeriesToSeriesEligibilityInd();
    }

    /**
     * Getter.
     * @return
     */
    public int getSeriesTransferDays()
    {
        return filteredFundVO.getSeriesTransferDays();
    }

    /**
     * Getter.
     * @return
     */
    public String getSeriesTransferDaysTypeCT()
    {
        return filteredFundVO.getSeriesTransferDaysTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public String getSeriesTransferModeCT()
    {
        return filteredFundVO.getSeriesTransferModeCT();
    }

    /**
     * Setter.
     * @param annualSubBucketCT
     */
    public void setAnnualSubBucketCT(String annualSubBucketCT)
    {
        filteredFundVO.setAnnualSubBucketCT(annualSubBucketCT);
    }
     //-- void setAnnualSubBucketCT(java.lang.String)

    /**
     * Setter.
     * @param COIReplenishmentDays
     */
    public void setCOIReplenishmentDays(int COIReplenishmentDays)
    {
        filteredFundVO.setCOIReplenishmentDays(COIReplenishmentDays);
    }
     //-- void setCOIReplenishmentDays(int)

    /**
     * Setter.
     * @param COIReplenishmentDaysTypeCT
     */
    public void setCOIReplenishmentDaysTypeCT(String COIReplenishmentDaysTypeCT)
    {
        filteredFundVO.setCOIReplenishmentDaysTypeCT(COIReplenishmentDaysTypeCT);
    }
     //-- void setCOIReplenishmentDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param COIReplenishmentModeCT
     */
    public void setCOIReplenishmentModeCT(String COIReplenishmentModeCT)
    {
        filteredFundVO.setCOIReplenishmentModeCT(COIReplenishmentModeCT);
    }
     //-- void setCOIReplenishmentModeCT(java.lang.String)

    /**
     * Setter.
     * @param chargeCodeIndicator
     */
    public void setChargeCodeIndicator(String chargeCodeIndicator)
    {
        filteredFundVO.setChargeCodeIndicator(chargeCodeIndicator);
    }
     //-- void setChargeCodeIndicator(java.lang.String)

    /**
     * Setter.
     * @param contributionLockUpDuration
     */
    public void setContributionLockUpDuration(int contributionLockUpDuration)
    {
        filteredFundVO.setContributionLockUpDuration(contributionLockUpDuration);
    }
     //-- void setContributionLockUpDuration(int)

    /**
     * Setter.
     * @param deathDays
     */
    public void setDeathDays(int deathDays)
    {
        filteredFundVO.setDeathDays(deathDays);
    }
     //-- void setDeathDays(int)

    /**
     * Setter.
     * @param deathDaysTypeCT
     */
    public void setDeathDaysTypeCT(String deathDaysTypeCT)
    {
        filteredFundVO.setDeathDaysTypeCT(deathDaysTypeCT);
    }
     //-- void setDeathDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param deathModeCT
     */
    public void setDeathModeCT(String deathModeCT)
    {
        filteredFundVO.setDeathModeCT(deathModeCT);
    }
     //-- void setDeathModeCT(java.lang.String)

    /**
     * Setter.
     * @param divFeesLiquidatnDaysTypeCT
     */
    public void setDivFeesLiquidatnDaysTypeCT(String divFeesLiquidatnDaysTypeCT)
    {
        filteredFundVO.setDivFeesLiquidatnDaysTypeCT(divFeesLiquidatnDaysTypeCT);
    }
     //-- void setDivFeesLiquidatnDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param divisionFeesLiquidationDays
     */
    public void setDivisionFeesLiquidationDays(int divisionFeesLiquidationDays)
    {
        filteredFundVO.setDivisionFeesLiquidationDays(divisionFeesLiquidationDays);
    }
     //-- void setDivisionFeesLiquidationDays(int)

    /**
     * Setter.
     * @param divisionFeesLiquidationModeCT
     */
    public void setDivisionFeesLiquidationModeCT(String divisionFeesLiquidationModeCT)
    {
        filteredFundVO.setDivisionFeesLiquidationModeCT(divisionFeesLiquidationModeCT);
    }
     //-- void setDivisionFeesLiquidationModeCT(java.lang.String)

    /**
     * Setter.
     * @param divisionLevelLockUpDuration
     */
    public void setDivisionLevelLockUpDuration(int divisionLevelLockUpDuration)
    {
        filteredFundVO.setDivisionLevelLockUpDuration(divisionLevelLockUpDuration);
    }
     //-- void setDivisionLevelLockUpDuration(int)

    /**
     * Setter.
     * @param divisionLockUpEndDate
     */
    public void setDivisionLockUpEndDate(EDITDate divisionLockUpEndDate)
    {
        filteredFundVO.setDivisionLockUpEndDate(SessionHelper.getEDITDate(divisionLockUpEndDate));
    }
     //-- void setDivisionLockUpEndDate(java.lang.String)

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        filteredFundVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }
     //-- void setEffectiveDate(java.lang.String)

    /**
     * Setter.
     * @param filteredFundPK
     */
    public void setFilteredFundPK(Long filteredFundPK)
    {
        filteredFundVO.setFilteredFundPK(SessionHelper.getPKValue(filteredFundPK));
    }
     //-- void setFilteredFundPK(long)

    /**
     * Setter.
     * @param fullSurrenderDays
     */
    public void setFullSurrenderDays(int fullSurrenderDays)
    {
        filteredFundVO.setFullSurrenderDays(fullSurrenderDays);
    }
     //-- void setFullSurrenderDays(int)

    /**
     * Setter.
     * @param fullSurrenderDaysTypeCT
     */
    public void setFullSurrenderDaysTypeCT(String fullSurrenderDaysTypeCT)
    {
        filteredFundVO.setFullSurrenderDaysTypeCT(fullSurrenderDaysTypeCT);
    }
     //-- void setFullSurrenderDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param fullSurrenderModeCT
     */
    public void setFullSurrenderModeCT(String fullSurrenderModeCT)
    {
        filteredFundVO.setFullSurrenderModeCT(fullSurrenderModeCT);
    }
     //-- void setFullSurrenderModeCT(java.lang.String)

    /**
     * Setter.
     * @param fundAdjustmentCT
     */
    public void setFundAdjustmentCT(String fundAdjustmentCT)
    {
        filteredFundVO.setFundAdjustmentCT(fundAdjustmentCT);
    }
     //-- void setFundAdjustmentCT(java.lang.String)

    /**
     * Setter.
     * @param fundFK
     */
    public void setFundFK(Long fundFK)
    {
        filteredFundVO.setFundFK(SessionHelper.getPKValue(fundFK));
    } //-- void setFundFK(long)


    /**
     * Setter.
     * @param fundNewClientCloseDate
     */
    public void setFundNewClientCloseDate(EDITDate fundNewClientCloseDate)
    {
        filteredFundVO.setFundNewClientCloseDate(SessionHelper.getEDITDate(fundNewClientCloseDate));
    }
     //-- void setFundNewClientCloseDate(java.lang.String)

    /**
     * Setter.
     * @param fundNewDepositCloseDate
     */
    public void setFundNewDepositCloseDate(EDITDate fundNewDepositCloseDate)
    {
        filteredFundVO.setFundNewDepositCloseDate(SessionHelper.getEDITDate(fundNewDepositCloseDate));
    }
     //-- void setFundNewDepositCloseDate(java.lang.String)

    /**
     * Setter.
     * @param fundNumber
     */
    public void setFundNumber(String fundNumber)
    {
        filteredFundVO.setFundNumber(fundNumber);
    }
     //-- void setFundNumber(java.lang.String)

    /**
     * Setter.
     * @param guaranteedDuration
     */
    public void setGuaranteedDuration(int guaranteedDuration)
    {
        filteredFundVO.setGuaranteedDuration(guaranteedDuration);
    }
     //-- void setGuaranteedDuration(int)

    /**
     * Setter.
     * @param holdingAccountFK
     */
    public void setHoldingAccountFK(Long holdingAccountFK)
    {
        filteredFundVO.setHoldingAccountFK((holdingAccountFK == null) ? 0 : holdingAccountFK.longValue());
    }
     //-- void setHoldingAccountFK(long)

    /**
     * Setter
     * @param includeInCorrespondenceInd
     */
    public void setIncludeInCorrespondenceInd(String includeInCorrespondenceInd)
    {
        filteredFundVO.setIncludeInCorrespondenceInd(includeInCorrespondenceInd);
    } //-- void setIncludeInCorrespondenceInd(java.lang.String)

    /**
     * Setter.
     * @param indexCapRateGuarPeriod
     */
    public void setIndexCapRateGuarPeriod(int indexCapRateGuarPeriod)
    {
        filteredFundVO.setIndexCapRateGuarPeriod(indexCapRateGuarPeriod);
    }
     //-- void setIndexCapRateGuarPeriod(int)

    /**
     * Setter.
     * @param indexingMethodCT
     */
    public void setIndexingMethodCT(String indexingMethodCT)
    {
        filteredFundVO.setIndexingMethodCT(indexingMethodCT);
    }
     //-- void setIndexingMethodCT(java.lang.String)

    /**
     * Setter.
     * @param loanDays
     */
    public void setLoanDays(int loanDays)
    {
        filteredFundVO.setLoanDays(loanDays);
    }
     //-- void setLoanDays(int)

    /**
     * Setter.
     * @param loanDaysTypeCT
     */
    public void setLoanDaysTypeCT(String loanDaysTypeCT)
    {
        filteredFundVO.setLoanDaysTypeCT(loanDaysTypeCT);
    }
     //-- void setLoanDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param loanModeCT
     */
    public void setLoanModeCT(String loanModeCT)
    {
        filteredFundVO.setLoanModeCT(loanModeCT);
    }
     //-- void setLoanModeCT(java.lang.String)

    /**
     * Setter.
     * @param MVAStartingIndexGuarPeriod
     */
    public void setMVAStartingIndexGuarPeriod(int MVAStartingIndexGuarPeriod)
    {
        filteredFundVO.setMVAStartingIndexGuarPeriod(MVAStartingIndexGuarPeriod);
    }
     //-- void setMVAStartingIndexGuarPeriod(int)

    /**
     * Setter.
     * @param minimumTransferAmount
     */
    public void setMinimumTransferAmount(EDITBigDecimal minimumTransferAmount)
    {
        filteredFundVO.setMinimumTransferAmount(SessionHelper.getEDITBigDecimal(minimumTransferAmount));
    }
     //-- void setMinimumTransferAmount(java.math.BigDecimal)

    /**
     * Setter.
     * @param postLockWithdrawalDateCT
     */
    public void setPostLockWithdrawalDateCT(String postLockWithdrawalDateCT)
    {
        filteredFundVO.setPostLockWithdrawalDateCT(postLockWithdrawalDateCT);
    }
     //-- void setPostLockWithdrawalDateCT(java.lang.String)

    /**
     * Setter.
     * @param premiumBonusDuration
     */
    public void setPremiumBonusDuration(int premiumBonusDuration)
    {
        filteredFundVO.setPremiumBonusDuration(premiumBonusDuration);
    }
     //-- void setPremiumBonusDuration(int)

    /**
     * Setter.
     * @param pricingDirection
     */
    public void setPricingDirection(String pricingDirection)
    {
        filteredFundVO.setPricingDirection(pricingDirection);
    }
     //-- void setPricingDirection(java.lang.String)

    /**
     * Setter.
     * @param separateAccountName
     */
    public void setSeparateAccountName(String separateAccountName)
    {
        filteredFundVO.setSeparateAccountName(separateAccountName);
    }
     //-- void setSeparateAccountName(java.lang.String)

    /**
     * Setter.
     * @param subscriptionDaysTypeCT
     */
    public void setSubscriptionDaysTypeCT(String subscriptionDaysTypeCT)
    {
        filteredFundVO.setSubscriptionDaysTypeCT(subscriptionDaysTypeCT);
    }
     //-- void setSubscriptionDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param subscriptionModeCT
     */
    public void setSubscriptionModeCT(String subscriptionModeCT)
    {
        filteredFundVO.setSubscriptionModeCT(subscriptionModeCT);
    }
     //-- void setSubscriptionModeCT(java.lang.String)

    /**
     * Setter.
     * @param subscriptionNotificationDays
     */
    public void setSubscriptionNotificationDays(int subscriptionNotificationDays)
    {
        filteredFundVO.setSubscriptionNotificationDays(subscriptionNotificationDays);
    }
     //-- void setSubscriptionNotificationDays(int)

    /**
     * Setter.
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        filteredFundVO.setTerminationDate(SessionHelper.getEDITDate(terminationDate));
    }
     //-- void setTerminationDate(java.lang.String)

    /**
     * Setter.
     * @param transferDays
     */
    public void setTransferDays(int transferDays)
    {
        filteredFundVO.setTransferDays(transferDays);
    }
     //-- void setTransferDays(int)

    /**
     * Setter.
     * @param transferDaysTypeCT
     */
    public void setTransferDaysTypeCT(String transferDaysTypeCT)
    {
        filteredFundVO.setTransferDaysTypeCT(transferDaysTypeCT);
    }
     //-- void setTransferDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param transferModeCT
     */
    public void setTransferModeCT(String transferModeCT)
    {
        filteredFundVO.setTransferModeCT(transferModeCT);
    }
     //-- void setTransferModeCT(java.lang.String)

    /**
     * Setter.
     * @param withdrawalDays
     */
    public void setWithdrawalDays(int withdrawalDays)
    {
        filteredFundVO.setWithdrawalDays(withdrawalDays);
    }
     //-- void setWithdrawalDays(int)

    /**
     * Setter.
     * @param withdrawalDaysTypeCT
     */
    public void setWithdrawalDaysTypeCT(String withdrawalDaysTypeCT)
    {
        filteredFundVO.setWithdrawalDaysTypeCT(withdrawalDaysTypeCT);
    }
     //-- void setWithdrawalDaysTypeCT(java.lang.String)

    /**
     * Setter.
     * @param withdrawalModeCT
     */
    public void setWithdrawalModeCT(String withdrawalModeCT)
    {
        filteredFundVO.setWithdrawalModeCT(withdrawalModeCT);
    }
     //-- void setWithdrawalModeCT(java.lang.String)

    /**
     * getter
     * @return
     */
    public String getCategoryCT()
    {
        return filteredFundVO.getCategoryCT();
    } //-- java.lang.String getCategoryCT()

    /**
     * setter
     * @param categoryCT
     */
    public void setCategoryCT(String categoryCT)
    {
        filteredFundVO.setCategoryCT(categoryCT);
    } //-- void setCategoryCT(java.lang.String)

    /**
     * Setter.
     * @param seriesToSeriesEligibilityInd
     */
    public void setSeriesToSeriesEligibilityInd(String seriesToSeriesEligibilityInd)
    {
        filteredFundVO.setSeriesToSeriesEligibilityInd(seriesToSeriesEligibilityInd);
    }

    /**
     * Setter.
     * @param seriesTransferDays
     */
    public void setSeriesTransferDays(int seriesTransferDays)
    {
        filteredFundVO.setSeriesTransferDays(seriesTransferDays);
    }

    /**
     * Setter.
     * @param seriesTransferDaysTypeCT
     */
    public void setSeriesTransferDaysTypeCT(String seriesTransferDaysTypeCT)
    {
        filteredFundVO.setSeriesTransferDaysTypeCT(seriesTransferDaysTypeCT);
    }

    /**
     * Setter.
     * @param seriesTransferModeCT
     */
    public void setSeriesTransferModeCT(String seriesTransferModeCT)
    {
        filteredFundVO.setSeriesTransferModeCT(seriesTransferModeCT);
    }

    /**
     * Setter.
     * @param fund
     */
    public void setFund(Fund fund)
    {
        this.fund = fund;
    }

    /**
     * Getter.
     * @return
     */
    public Fund getFund()
    {
        return fund;
    }

    /**
     * CRUD version.
     * @return
     */
    public FundVO get_FundVO()
    {
        return new FundDAO().findFundByPK(filteredFundVO.getFundFK(), false, null)[0];
    }

    /**
     * Getter
     * @return  set of productStructures
     */
    public Set getProductStructures()
    {
        return productStructures;
    }

    /**
     * Setter
     * @param productStructures      set of productStructures
     */
    public void setProductStructures(Set productStructures)
    {
        this.productStructures = productStructures;
    }

    /**
     * Adds a ProductStructure to the set of children
     * @param productStructure
     */
    public void addProductStructure(ProductStructure productStructure)
    {
        this.getProductStructures().add(productStructure);

        productStructure.addFilteredFund(this);

        SessionHelper.saveOrUpdate(productStructure, FilteredFund.DATABASE);
    }

    /**
     * Removes a ProductStructure from the set of children
     * @param productStructure
     */
    public void removeProductStructure(ProductStructure productStructure)
    {
        this.getProductStructures().remove(productStructure);

        productStructure.removeFilteredFund(null);

        SessionHelper.saveOrUpdate(productStructure, FilteredFund.DATABASE);
    }

    /**
     * Getter
     * @return  set of chargeCodes
     */
    public Set getChargeCodes()
    {
        return chargeCodes;
    }

    /**
     * Setter
     * @param chargeCodes      set of chargeCodes
     */
    public void setChargeCodes(Set chargeCodes)
    {
        this.chargeCodes = chargeCodes;
    }

    /**
     * Adds a ChargeCode to the set of children
     * @param chargeCode
     */
    public void addChargeCode(ChargeCode chargeCode)
    {
        this.getChargeCodes().add(chargeCode);

        chargeCode.setFilteredFund(this);

        SessionHelper.saveOrUpdate(chargeCode, FilteredFund.DATABASE);
    }

    /**
     * Removes a ChargeCode from the set of children
     * @param chargeCode
     */
    public void removeChargeCode(ChargeCode chargeCode)
    {
        this.getChargeCodes().remove(chargeCode);

        chargeCode.setFilteredFund(null);

        SessionHelper.saveOrUpdate(chargeCode, FilteredFund.DATABASE);
    }

    /**
     * Getter
     * @return  set of unitValues
     */
    public Set getUnitValues()
    {
        return unitValues;
    }

    /**
     * Setter
     * @param unitValues      set of unitValues
     */
    public void setUnitValues(Set unitValues)
    {
        this.unitValues = unitValues;
    }

    /**
     * Adds a UnitValues to the set of children
     * @param unitValues
     */
    public void addUnitValues(UnitValues unitValues)
    {
        this.getUnitValues().add(unitValues);

        unitValues.setFilteredFund(this);

//        SessionHelper.saveOrUpdate(unitValues, FilteredFund.DATABASE);
    }

    /**
     * Removes a UnitValues from the set of children
     * @param unitValues
     */
    public void removeUnitValues(UnitValues unitValues)
    {
        this.getUnitValues().remove(unitValues);

        unitValues.setFilteredFund(null);

        SessionHelper.saveOrUpdate(unitValues, FilteredFund.DATABASE);
    }

    /**
     * Setter.
     * @param fees
     */
    public void setFees(Set fees)
    {
        this.fees = fees;
    }

    /**
     * Getter.
     * @return
     */
    public Set getFees()
    {
        return this.fees;
    }

    public void save() throws Exception
    {
        filteredFundImpl.save(this, ConnectionFactory.ENGINE_POOL, false);
    }

    public void delete() throws Exception
    {

    }

    public VOObject getVO()
    {
        return filteredFundVO;
    }

    public long getPK()
    {
        return filteredFundVO.getFilteredFundPK();
    }

    public void setVO(VOObject voObject)
    {
        this.filteredFundVO = (FilteredFundVO) voObject;
    }

    public boolean isNew()
    {
        return (filteredFundVO.getFilteredFundPK() == 0);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return filteredFundImpl.cloneCRUDEntity(this);
    }

    /**
     * This method returns true if there exists an Unit Value entry for the given month or prior month.
     * @param chargeCodeFK
     * @param date
     * @return
     */
    // At this point of time, this check is required for only hedge funds.
    public boolean hasUnitValueForPriorOrCurrentMonth(Long chargeCodeFK, EDITDate date)
    {
        boolean isExists = false;

        if (this.getFund().isHedgeFund())
        {
            // This may return null if there are no UnitValues at all.
            UnitValues latestUnitValue = null;

            if (chargeCodeFK.longValue() == 0L)
            {
                latestUnitValue = UnitValues.findLatestUnitValueBy_FilteredFund(getFilteredFundPK());
            }
            else
            {
                latestUnitValue = UnitValues.findLatestUnitValueBy_FilteredFund_ChargeCode(getFilteredFundPK(), chargeCodeFK);
            }

            if (latestUnitValue != null)
            {
                EDITDate latestUnitValueEffectiveDate = latestUnitValue.getEffectiveDate();

                // If UnitValue exists for the month of given date or prior month the following condition should be true.
                if ((date.getElapsedMonths(latestUnitValueEffectiveDate)) <= 1)
                {
                    isExists = true;
                }
            }
            // If there are no UnitValues at all then nothing to compare then return true.
            else
            {
                isExists = true;
            }
        }
        // If this is not hedge fund no need to compare
        else
        {
            isExists = true;
        }

        return isExists;
    }

    /**
     * Creates set of DFCASH transactions
     * @param fees
     * @throws EDITEngineException
     */
    public void createDFCASHFeeTransaction(Fee[] fees) throws EDITEngineException
    {
        FeeVO feeVO = null;

        if (fees != null)
        {
            for (int i = 0; i < fees.length; i++)
            {
                feeVO = (FeeVO) fees[i].getVO();

                feeVO.setFeePK(0);
                feeVO.setFilteredFundFK(getPK());
                feeVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());

                // by default the DCASH are released
                feeVO.setReleaseInd("Y");
                feeVO.setStatusCT("N");

                // when the fee is released the 'AccountingPendingStatus' is 'Y'
                feeVO.setAccountingPendingStatus("Y");
                feeVO.setTransactionTypeCT(Fee.DIVISION_FEE_CASH_TRX_TYPE);
                feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
                feeVO.setOperator("System");

                Fee fee = new Fee(feeVO);
                fee.save();
            }
        }
    }

    /**************************************************** Static Methods **************************************************/
    /**
     * Finder by PK.
     * @param filteredFundPK
     * @return
     */
    public static final FilteredFund findByPK(long filteredFundPK)
    {
        FilteredFund filteredFund = null;

        FilteredFundVO filteredFundVO = new FilteredFundDAO().findByFilteredFundPK(filteredFundPK)[0];

        if (filteredFundVO != null)
        {
            filteredFund = new FilteredFund(filteredFundVO);
        }

        return filteredFund;
    }

    /**
     * Finder by Fund Number.
     * @param fundNumber
     * @return
     */
    public static final FilteredFund findByFundNumber(String fundNumber)
    {
        FilteredFundVO[] filteredFundVOs = new FilteredFundDAO().findByFundNumber(fundNumber);

        FilteredFund filteredFund = null;

        if (filteredFundVOs != null)
        {
            filteredFund = new FilteredFund(filteredFundVOs[0]);
        }

        return filteredFund;
    }

    /**
     * Finder by PK.
     * @param filteredFundPK
     * @return
     */
    public static final FilteredFund findByPK(Long filteredFundPK)
    {
        return (FilteredFund) SessionHelper.get(FilteredFund.class, filteredFundPK, FilteredFund.DATABASE);
    }

    /**
     * Originally rom FilteredFundDAO.findFilteredFundbyPKAndFundType()
     *
     * @param filteredFundPK
     *
     * @return  array of FilteredFunds
     */
    public static FilteredFund[] findByPK_FundType(Long filteredFundPK, String fundType)
    {
        String hql = "select filteredFund from FilteredFund filteredFund" +
                     " join filteredFund.Fund fund" +
                     " where filteredFund.FilteredFundPK = :filteredFundPK" +
                     " and fund.FundType = :fundType";

        Map params = new HashMap();

        params.put("filteredFundPK", filteredFundPK);
        params.put("fundType", fundType);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        return (FilteredFund[]) results.toArray(new FilteredFund[results.size()]);
    }

    /**
     * Find by PK and Include the Fund.
     *
     * Originally from engineLookup.composeFilteredFundVOByFilteredFundPK
     * @param filteredFundPK
     *
     * @return  array of FilteredFunds
     */
    public static FilteredFund findByPK_IncludeFund(Long filteredFundPK)
    {
        FilteredFund filteredFund = null;

        String hql = "select filteredFund from FilteredFund filteredFund" +
                     " join fetch filteredFund.Fund fund" +
                     " where filteredFund.FilteredFundPK = :filteredFundPK";

        Map params = new HashMap();

        params.put("filteredFundPK", filteredFundPK);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        if (!results.isEmpty())
        {
            filteredFund = (FilteredFund) results.get(0);
        }

        return filteredFund;
    }

    /**
     * Find all FilteredFunds by ProductStructurePK
     * @param productStructurePK
     * @return  array of FilteredFunds
     */
    public static FilteredFund[] findBy_ProductStructurePK(Long productStructurePK)
    {
        String hql = "select filteredFund from FilteredFund filteredFund" +
                     " join filteredFund.ProductStructures productStructure" +
                     " where productStructure.ProductStructurePK = :productStructurePK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        return (FilteredFund[]) results.toArray(new FilteredFund[results.size()]);
    }


    /**
     * Originally in FilteredFundDAO.findByCSIdFundId
     * @param productStructurePK
     * @param fundPK
     * @return
     */
    public static FilteredFund[] findBy_ProductStructurePK_FundFK(Long productStructurePK, Long fundFK)
    {
        String hql = "select filteredFund from FilteredFund filteredFund" +
                     " join filteredFund.ProductFilteredFundStructure productFilteredFundStructure" +
                     " where productFilteredFundStructure.ProductStructureFK = :productStructurePK" +
                     " and filteredFund.FundFK = :fundFK";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("fundFK", fundFK);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        return (FilteredFund[]) results.toArray(new FilteredFund[results.size()]);
	}

    /**
     * Originally in FastDAO.findFilteredFundPKByBucketPK
     * @param bucketPK
     * @return
     * @throws Exception
     */
    public static FilteredFund findBy_BucketPK(Long bucketPK)
    {
        FilteredFund filteredFund = null;

        String hql = "select filteredFund from FilteredFund filteredFund" +
                     " join filteredFund.Investment investment" +
                     " join investment.Bucket bucket" +
                     " where bucket.BucketPK = :bucketPK";

        Map params = new HashMap();

        params.put("bucketPK", bucketPK);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        if (! results.isEmpty())
        {
            filteredFund = (FilteredFund) results.get(0);
        }

        return filteredFund;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, FilteredFund.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, FilteredFund.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return FilteredFund.DATABASE;
    }

    /**
     * Finder by Product Structure and Correspondence Indicator
     * @param productStructurePK
     * @param correspondenceIndicator
     * @return Array of Filtered Funds
     */
    public static final FilteredFund[] findByProductStructure_And_CorrespondenceIndicator(Long productStructurePK,
                                                                                          String correspondenceIndicator)
    {
        String hql = " select filteredFund" +
                     " from FilteredFund filteredFund" +
                     " where filteredFund.ProductStructures.ProductStructurePK = :productStructurePK" +
                     " and filteredFund.IncludeInCorrespondenceInd = :correspondenceIndicator";

        Map params = new HashMap();
        params.put("productStructurePK", productStructurePK);
        params.put("correspondenceIndicator", correspondenceIndicator);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        return (FilteredFund[]) results.toArray(new FilteredFund[results.size()]);
    }

    /**
     * Finder by Product Structure and Correspondence Indicator
     * @param productStructurePK
     * @param correspondenceIndicator
     * @return Array of Filtered Funds
     */
    public static final FilteredFund findByFundNumber_And_ProductStructureNames(String fundNumber, String companyName,
                    String marketingPackageName, String groupProductName, String areaName, String businessContractName)
    {
        FilteredFund filteredFund = null;

        String hql = " select filteredFund" +
                     " from FilteredFund filteredFund" +
//                     " join filteredFund.CompanyFilteredFundStructure companyFilteredFundStructure" +
//                     " join companyFilteredFundStructure.CompanyStructure companyStructure" +
                     " join filteredFund.ProductStructures productStructure" +
                     " join productStructure.Company company" +
                     " where filteredFund.FundNumber = :fundNumber" +
                     " and company.CompanyName = :companyName" +
                     " and productStructure.MarketingPackageName = :marketingPackageName" +
                     " and productStructure.GroupProductName = :groupProductName" +
                     " and productStructure.AreaName = :areaName" +
                     " and productStructure.BusinessContractName = :businessContractName";

        Map params = new HashMap();

        params.put("fundNumber", fundNumber);
        params.put("companyName", companyName);
        params.put("marketingPackageName", marketingPackageName);
        params.put("groupProductName", groupProductName);
        params.put("areaName", areaName);
        params.put("businessContractName", businessContractName);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        if (!results.isEmpty())
        {
          filteredFund = (FilteredFund) results.get(0);
        }

        return filteredFund;
    }

    /**
     * Finds the filteredFund by the productStructurePK and the fundType
     *
     * @param productStructurePK
     * @param fundType
     *
     * @return
     */
    public static FilteredFund[] findBy_ProductStructurePK_FundType(Long productStructurePK, String fundType)
    {
        String hql = "select filteredFund from FilteredFund filteredFund" +
                     " join filteredFund.ProductStructures productStructures" +
                     " join filteredFund.Fund fund" +
                     " where productStructures.ProductStructurePK = :productStructurePK" +
                     " and fund.FundType = :fundType";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("fundType", fundType);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        return (FilteredFund[]) results.toArray(new FilteredFund[results.size()]);
    }

    /**
     * Finds the filteredFund by the productStructurePK and the loanQualifierCT
     *
     * @param productStructurePK
     * @param loanQualifierCT
     *
     * @return
     */
    public static FilteredFund[] findBy_ProductStructurePK_LoanQualifierCT(Long productStructurePK, String loanQualifierCT)
    {
        String hql = "select filteredFund from FilteredFund filteredFund" +
                     " join filteredFund.ProductStructures productStructures" +
                     " join filteredFund.Fund fund" +
                     " where productStructures.ProductStructurePK = :productStructurePK" +
                     " and fund.LoanQualifierCT = :loanQualifierCT";

        Map params = new HashMap();

        params.put("productStructurePK", productStructurePK);
        params.put("loanQualifierCT", loanQualifierCT);

        List results = SessionHelper.executeHQL(hql, params, FilteredFund.DATABASE);

        return (FilteredFund[]) results.toArray(new FilteredFund[results.size()]);
    }
    
    /**
     * A convenience method to get all FilteredFunds (cached) that are associated
     * with a Fund.FundType = "System" and a Fund.LoanQualifierCT != null (i.e.
     * a GeneralAccount fund).
     *
     * The result(s) do are in a separate Hibernate session.
     */
    public static FilteredFund[] getSeparateGeneralAccountFilteredFunds()
    {
        if (generalAccountFilteredFunds == null)
        {
            String hql = " select filteredFund" +
                    " from FilteredFund filteredFund" +
                    " join filteredFund.Fund fund" +
                    " where fund.TypeCodeCT = '" + Fund.FUNDTYPE_SYSTEM + "'" +
                    " and fund.LoanQualifierCT is not null";
            
            Session session = null;
            
            List<FilteredFund> results = null;
            
            try
            {
                session = SessionHelper.getSeparateSession(FilteredFund.DATABASE);
                
                results = SessionHelper.executeHQL(session, hql, null, 0);
            }
            finally
            {
                if (session != null) session.close();
            }
            
            generalAccountFilteredFunds = results.toArray(new FilteredFund[results.size()]);
        }
        
        return generalAccountFilteredFunds;
    }
}
