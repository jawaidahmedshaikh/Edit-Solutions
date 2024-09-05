/*
 * User: mcassidy
 * Date: Mar 25, 2005
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;
import contract.*;

import java.util.*;

import engine.component.*;
import engine.business.*;

/**
 * Entity class for UnitValues.
 */
public class UnitValues extends HibernateEntity
{
    public static final String UPDATESTATUS_HEDGE = "Hedge";

    private Long unitValuesPK;
    private Long filteredFundFK;
    private EDITDate effectiveDate;
    private EDITBigDecimal unitValue;
    private EDITBigDecimal annuityUnitValue;
    private String updateStatus;
    private EDITBigDecimal netAssetValue1;
    private EDITBigDecimal netAssetValue2;
    private EDITBigDecimal mutualFundShares;
    private EDITBigDecimal participantUnits;
    private EDITBigDecimal mutualFundAssets;
    private EDITBigDecimal dividendRate;
    private Long chargeCodeFK;

    private FilteredFund filteredFund;  // parent
    private ChargeCode chargeCode;      // parent

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;
    

    public Long getUnitValuesPK()
    {
        return unitValuesPK;
    }

    public void setUnitValuesPK(Long unitValuesPK)
    {
        this.unitValuesPK = unitValuesPK;
    }

    public Long getFilteredFundFK()
    {
        return filteredFundFK;
    }

    public void setFilteredFundFK(Long filteredFundFK)
    {
        this.filteredFundFK = filteredFundFK;
    }

    public Long getChargeCodeFK()
    {
        return chargeCodeFK;
    }

    public void setChargeCodeFK(Long chargeCodeFK)
    {
        this.chargeCodeFK = chargeCodeFK;
    }

    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public EDITBigDecimal getUnitValue()
    {
        return unitValue;
    }

    public void setUnitValue(EDITBigDecimal unitValue)
    {
        this.unitValue = unitValue;
    }

    public EDITBigDecimal getAnnuityUnitValue()
    {
        return annuityUnitValue;
    }

    public void setAnnuityUnitValue(EDITBigDecimal annuityUnitValue)
    {
        this.annuityUnitValue = annuityUnitValue;
    }

    public String getUpdateStatus()
    {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus)
    {
        this.updateStatus = updateStatus;
    }

    public EDITBigDecimal getNetAssetValue1()
    {
        return netAssetValue1;
    }

    public void setNetAssetValue1(EDITBigDecimal netAssetValue1)
    {
        this.netAssetValue1 = netAssetValue1;
    }

    public EDITBigDecimal getNetAssetValue2()
    {
        return netAssetValue2;
    }

    public void setNetAssetValue2(EDITBigDecimal netAssetValue2)
    {
        this.netAssetValue2 = netAssetValue2;
    }

    public EDITBigDecimal getMutualFundShares()
    {
        return mutualFundShares;
    }

    public void setMutualFundShares(EDITBigDecimal mutualFundShares)
    {
        this.mutualFundShares = mutualFundShares;
    }

    public EDITBigDecimal getParticipantUnits()
    {
        return participantUnits;
    }

    public void setParticipantUnits(EDITBigDecimal participantUnits)
    {
        this.participantUnits = participantUnits;
    }

    public EDITBigDecimal getMutualFundAssets()
    {
        return mutualFundAssets;
    }

    public void setMutualFundAssets(EDITBigDecimal mutualFundAssets)
    {
        this.mutualFundAssets = mutualFundAssets;
    }

    public EDITBigDecimal getDividendRate()
    {
        return dividendRate;
    }

    public void setDividendRate(EDITBigDecimal dividendRate)
    {
        this.dividendRate = dividendRate;
    }

    public FilteredFund getFilteredFund()
    {
        return filteredFund;
    }

    public void setFilteredFund(FilteredFund filteredFund)
    {
        this.filteredFund = filteredFund;
    }

    public ChargeCode getChargeCode()
    {
        return chargeCode;
    }

    public void setChargeCode(ChargeCode chargeCode)
    {
        this.chargeCode = chargeCode;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, UnitValues.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, UnitValues.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return UnitValues.DATABASE;
    }

    public UnitValues findByPK(Long unitValuesPK)
    {
        return (UnitValues) SessionHelper.get(UnitValues.class, unitValuesPK, UnitValues.DATABASE);
    }

    /**
     *  Get UnitValues that match a filtered fund id, the charge code, and effective date.
     *
     * NOTE: Originally in engine.LookupComponent.getUnitValuesByFilteredFundChargeCodeEffDate
     *
     * @param filteredFundId        filtered fund PK
     * @param chargeCodeFK          charge code FK
     * @param effectiveDate         effective date
     *
     * @return array of UnitValues
     */
    public static UnitValues[] findBy_FilteredFund_ChargeCode_EffectiveDate(Long filteredFundFK, Long chargeCodeFK, EDITDate effectiveDate)
    {
        // first get the unit values for filteredfund id, effective date for the "Forward" direction
        UnitValues[] unitValues = UnitValues.findBy_FilteredFundPK_EffectiveDate_PricingDirection(filteredFundFK, effectiveDate, "Forward");

        if (unitValues == null)
        {
            return null;
        }

        List unitValuesList = new ArrayList();

        for (int i = 0; i < unitValues.length; i++)
        {
            UnitValues values = unitValues[i];

            Long tempChargeCodeFK = values.getChargeCode().getChargeCodePK();

            if (chargeCodeFK == tempChargeCodeFK)
            {
                unitValuesList.add(values);
            }
        }

        return (UnitValues[]) unitValuesList.toArray(new UnitValues[unitValuesList.size()]);
    }

    public boolean hasUnitValueForPriorMonth(EDITDate date)
    {
        boolean isExists = false;

        // This may return null if there are no UnitValues at all.
        UnitValues latestUnitValue = findLatestUnitValueBy_FilteredFund_ChargeCode(getFilteredFundFK(), getChargeCodeFK());

        if (latestUnitValue != null)
        {
            EDITDate latestUnitValueEffectiveDate = latestUnitValue.getEffectiveDate();

            // If UnitValue exists for the month of given date or prior month the following condition should be true.
            if (date.getMonth() - latestUnitValueEffectiveDate.getMonth() <= 1)
            {
                isExists = true;
            }
        }
        else
        // If there are no UnitValues at all then nothing to compare and return true.
        {
            isExists = true;
        }

        return isExists;
    }

    /**
     * Finder to find latest UnitValue entry for a given Filtered Fund.
     * This can be used when charge codes are not defined for filtered fund.
     * @param filteredFundFK
     * @return
     */
    public static UnitValues findLatestUnitValueBy_FilteredFund(Long filteredFundFK)
    {
        UnitValues unitValues = null;

        String hql = " select unitValues" +
                     " from UnitValues unitValues" +
                     " where unitValues.FilteredFundFK = :filteredFundFK" +
                     " and unitValues.EffectiveDate in (select max(unitValues2.EffectiveDate)" +
                             " from UnitValues unitValues2" +
                             " where unitValues2.FilteredFundFK = :filteredFundFK)";

        Map params = new HashMap();

        params.put("filteredFundFK", filteredFundFK);

        List results = SessionHelper.executeHQL(hql, params, UnitValues.DATABASE);

        if (!results.isEmpty())
        {
            unitValues = (UnitValues) results.get(0);
        }

        return unitValues;
    }

    /**
     * Finder to find latest UnitValue entry for a given Filtered Fund and Charge Code.
     * @param filteredFundFK
     * @param chargeCodeFK
     * @return
     */
    public static UnitValues findLatestUnitValueBy_FilteredFund_ChargeCode(Long filteredFundFK, Long chargeCodeFK)
    {
        UnitValues unitValues = null;

        String hql = " select unitValues" +
                     " from UnitValues unitValues" +
                     " where unitValues.FilteredFundFK = :filteredFundFK" +
                     " and unitValues.ChargeCodeFK = :chargeCodeFK" +
                     " and unitValues.EffectiveDate in (select max(unitValues2.EffectiveDate)" +
                             " from UnitValues unitValues2" +
                             " where unitValues2.FilteredFundFK = :filteredFundFK" +
                             " and unitValues2.ChargeCodeFK = :chargeCodeFK)";

        Map params = new HashMap();

        params.put("filteredFundFK", filteredFundFK);
        params.put("chargeCodeFK", chargeCodeFK);

        List results = SessionHelper.executeHQL(hql, params, UnitValues.DATABASE);

        if (!results.isEmpty())
        {
            unitValues = (UnitValues) results.get(0);
        }

        return unitValues;
    }

    /**
     * Finder By UnitValues.UpdateStatus and Fund.FundType.
     * @param updateStatus
     * @param fundType
     * @return an array of UnitValues.
     */
    public static UnitValues[] findByUpdateStatus_And_FundType(String updateStatus, String fundType)
    {
        String hql = "select unitValues from UnitValues unitValues " +
                     "left join fetch unitValues.ChargeCode chargeCode " +
                     "join fetch unitValues.FilteredFund filteredFund " +
                     "join fetch filteredFund.Fund fund " +
                     "where unitValues.UpdateStatus = :updateStatus " +
                     "and fund.FundType = :fundType " +
                     "order by filteredFund.FundNumber asc, chargeCode.ChargeCode asc";

        Map params = new HashMap();

        params.put("updateStatus", updateStatus);
        params.put("fundType", fundType);

        List results = SessionHelper.executeHQL(hql, params, UnitValues.DATABASE);

        return (UnitValues[]) results.toArray(new UnitValues[results.size()]);
    }

    /**
     * Finder By Fund Type and whose Fund UnitValues are not indicated as 'Hedge'.
     * It also eliminates all funds whose UnitValues (atleast one) are indicated as 'Hedge'.
     * @param fundType
     * @return an array of UnitValues for the past 12 months.
     */
    public static UnitValues[] findByFundType_And_RollingPrevious12Month(String fundType)
    {
        String hql = "select unitValues from UnitValues unitValues " +
                     "left join fetch unitValues.ChargeCode chargeCode " +
                     "join fetch unitValues.FilteredFund filteredFund " +
                     "join fetch filteredFund.Fund fund " +
                     "where unitValues.UpdateStatus is null " +
                     "and fund.FundType = :fundType " +
                     "and unitValues.EffectiveDate between :pastYearDate and :sysDate " +
                     "and filteredFund.FilteredFundPK not in (" +
                     "select filteredFund2.FilteredFundPK from UnitValues unitValues2 " +
                     "left join unitValues2.ChargeCode chargeCode2 " +
                     "join unitValues2.FilteredFund filteredFund2 " +
                     "join filteredFund2.Fund fund2 " +
                     "where unitValues2.UpdateStatus = 'Hedge' " +
                     "and fund2.FundType = :fundType) " +
                     "order by filteredFund.FundNumber asc, chargeCode.ChargeCode asc, unitValues.EffectiveDate asc";

        Map params = new HashMap();
        
        EDITDate pastYearDate = new EDITDate().subtractMonths(12);
        params.put("pastYearDate", pastYearDate);
        params.put("fundType", fundType);
        params.put("sysDate", new EDITDate());

        List results = SessionHelper.executeHQL(hql, params, UnitValues.DATABASE);

        return (UnitValues[]) results.toArray(new UnitValues[results.size()]);
    }

    /**
     * Originally in UnitValuesDAO(). findUnitValuesByFilteredFundIdDate
     * @param filteredFundId
     * @param effDate
     * @param pricingDirection
     * @return
     */
    public static UnitValues[] findBy_FilteredFundPK_EffectiveDate_PricingDirection(Long filteredFundFK, EDITDate effectiveDate, String pricingDirection)
    {
        if (pricingDirection.equalsIgnoreCase("Hedge"))
        {
            //"Hedge" searches for a forward price, and if none is found, searches for a backward price
            UnitValues[] unitValuesHedge = UnitValues.findBy_FilteredFundFK_EffectiveDate_ForwardPricing(filteredFundFK, effectiveDate);

            if (unitValuesHedge == null || unitValuesHedge.length == 0)
            {
                // Backwards
                unitValuesHedge = UnitValues.findBy_FilteredFundFK_EffectiveDate_BackwardPricing(filteredFundFK, effectiveDate);

            }

            return unitValuesHedge;
        }
        else
        {
            UnitValues[] unitValues = null;

            if (pricingDirection.equals("Backward"))
            {
                unitValues = UnitValues.findBy_FilteredFundFK_EffectiveDate_BackwardPricing(filteredFundFK, effectiveDate);
            }
            else
            {
                unitValues = UnitValues.findBy_FilteredFundFK_EffectiveDate_ForwardPricing(filteredFundFK, effectiveDate);
            }

            return unitValues;
        }
    }

    public static UnitValues[] findBy_FilteredFundFK_EffectiveDate_BackwardPricing(Long filteredFundFK, EDITDate effectiveDate)
    {
        String hql = "select unitValues from UnitValues unitValues " +
                     " where unitValues.FilteredFundFK = :filteredFundFK" +
                     " and unitValues.EffectiveDate = " +
                            " (select max(unitValues.EffectiveDate) from unitValues" +
                            " where unitValues.EffectiveDate <= :effectiveDate" +
                            " and unitValues.FilteredFundFK = :filteredFundFK)";

        Map params = new HashMap();

        params.put("filteredFundFK", filteredFundFK);
        params.put("effectiveDate", effectiveDate);

        List results = SessionHelper.executeHQL(hql, params, UnitValues.DATABASE);

        return (UnitValues[]) results.toArray(new UnitValues[results.size()]);
    }

    public static UnitValues[] findBy_FilteredFundFK_EffectiveDate_ForwardPricing(Long filteredFundFK, EDITDate effectiveDate)
    {
        String hql = "select unitValues from UnitValues unitValues " +
                     " where unitValues.FilteredFundFK = :filteredFundFK" +
                     " and unitValues.EffectiveDate = " +
                            " (select min(unitValues.EffectiveDate) from unitValues" +
                            " where unitValues.EffectiveDate >= :effectiveDate" +
                            " and unitValues.FilteredFundFK = :filteredFundFK)";

        Map params = new HashMap();

        params.put("filteredFundFK", filteredFundFK);
        params.put("effectiveDate", effectiveDate);

        List results = SessionHelper.executeHQL(hql, params, UnitValues.DATABASE);

        return (UnitValues[]) results.toArray(new UnitValues[results.size()]);
    }

    //   HIBERNATE METHOD, SEE NEXT METHOD FOR CRUD VERSION (USES VOs)
    /**
     * Return back if the unit values are missing for investments that are using
     * charge codes. For each of the investments, check if there is a unit value
     * for its charge code.  If all of the investments objects have a unit value
     * that also have a filtered fund, then the status is false.  Otherwise
     * return true.  This uses the effective date to look at only forward values.
     * @param allInvestmentVOsToCheck
     * @param effDate
     * @return
     */
     public static boolean areUnitValuesMissingForInvestmentsWithChargeCodes(Investment[] allInvestmentsToCheck,
                                                                EDITDate effectiveDate) throws Exception
     {
         boolean unitValuesMissing = false;
         boolean investmentHasValue = false;

         for (int i = 0; i < allInvestmentsToCheck.length; i++)
         {
             investmentHasValue = false;

             Investment investment = allInvestmentsToCheck[i];

             Set buckets = investment.getBuckets();

             for (Iterator iterator = buckets.iterator(); iterator.hasNext(); )
             {
                 Bucket bucket = (Bucket) iterator.next();

                 if (bucket.getCumUnits().isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                 {
                     investmentHasValue = true;
                     break;
                 }
             }

             if (investmentHasValue)
             {
                 Long chargeCodeFK = investment.getChargeCodeFK();
                 Long filteredFundId = investment.getFilteredFundFK();

                 if (chargeCodeFK.longValue() != 0L && filteredFundId.longValue() != 0L)
                 {
//                     UnitValues[] unitValues = lookup.getUnitValuesByFilteredFundChargeCodeEffDate(
//                                     filteredFundId, chargeCodeFK, effDate);
                     UnitValues[] unitValues = UnitValues.findBy_FilteredFund_ChargeCode_EffectiveDate(filteredFundId, chargeCodeFK, effectiveDate);

                     if (unitValues == null || unitValues.length == 0)
                     {
                         unitValuesMissing = true;
                     }
                 }
             }
         }

         return unitValuesMissing;
     }

    /**
     * Return back if the unit values are missing for investments that are using
     * charge codes. For each of the investments, check if there is a unit value
     * for its charge code.  If all of the investments objects have a unit value
     * that also have a filtered fund, then the status is false.  Otherwise
     * return true.  This uses the effective date to look at only forward values.
     * @param allInvestmentVOsToCheck
     * @param effDate
     * @return
     */
     public static boolean areUnitValuesMissingForInvestmentsWithChargeCodes(
                InvestmentVO[] allInvestmentVOsToCheck,
                String effDate)
                throws Exception
     {
         boolean unitValuesMissing = false;
         boolean investmentHasValue = false;

         for (int i = 0; i < allInvestmentVOsToCheck.length; i++)
         {
             investmentHasValue = false;

             InvestmentVO investmentVO = allInvestmentVOsToCheck[i];

             //Only nonFixed fundTypes need to be checked for units
             boolean fundIsFixed = Fund.checkForFixedFund(investmentVO.getFilteredFundFK());

             if (!fundIsFixed)
             {
                 BucketVO[] bucketVO = investmentVO.getBucketVO();
                 for (int j = 0; j < bucketVO.length; j++)
                 {
                     if (new EDITBigDecimal(bucketVO[j].getCumUnits()).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                     {
                         investmentHasValue = true;
                         break;
                     }
                 }

                 if (investmentHasValue)
                 {
                     long chargeCodeFK = investmentVO.getChargeCodeFK();
                     long filteredFundId = investmentVO.getFilteredFundFK();

                 if (filteredFundId != 0L)
                 {
                     Lookup lookup = new LookupComponent();

                         UnitValuesVO[] unitValuesVOs =
                                 lookup.getUnitValuesByFilteredFundChargeCodeEffDate(
                                         filteredFundId,
                                         chargeCodeFK,
                                         effDate);

                         if (unitValuesVOs == null || unitValuesVOs.length == 0)
                         {
                             unitValuesMissing = true;
                         }
                     }
                 }
             }
         }

         return unitValuesMissing;
     }
}



