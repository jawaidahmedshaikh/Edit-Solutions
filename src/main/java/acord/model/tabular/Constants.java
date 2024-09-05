/*
 * User: gfrosti
 * Date: Jun 8, 2006
 * Time: 9:14:00 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package acord.model.tabular;

/**
 *
 * @author gfrosti
 */
public interface Constants
{
    /**
     * Maintains the set of constants for the required ContentType element of the ContentClassification element.
     * @author gfrosti
     */
    public interface ContentType
    {
        //7702 Corridor (DEFRA)
        public int CONTENTTYPE_7702CORR = 63 ;
        
        //Age Table
        public int CONTENTTYPE_AGE = 56;
        
        //Annuity Factors
        public int CONTENTTYPE_ANNUITYFACTOR = 21;
        
        //Benefit Maximum IRC Section 415
        public int CONTENTTYPE_BENEFITMAXIRC415 = 26;
        
        //Benefit Maximum Revenue Canada Max
        public int CONTENTTYPE_BENMAXREVCANMAX = 27;
        
        //Bond Default Rates
        public int CONTENTTYPE_BONDDEFAULTRATE = 34 ;
        
        //Cash Value Rates
        public int CONTENTTYPE_CASHVALUE = 45 ;
        
        //Claim Cost (in Disability)
        public int CONTENTTYPE_CLAIMCOST = 50 ;
        
        //Commission rates
        public int CONTENTTYPE_COMMISSIONRATE = 53 ;
        
        //Commutation Function
        public int CONTENTTYPE_COMMUTE = 65 ;
        
        //Contribution limits
        public int CONTENTTYPE_CONTRIBUTIONLIMIT = 38 ;
        
        //Conversion Factors (Non-Term)
        public int CONTENTTYPE_CONVERSIONFACTORS = 20 ;
        
        //Cost of Insurance Rates
        public int CONTENTTYPE_COSTOFINSURANCE = 23 ;
        
        //Cost of Living Increase
        public int CONTENTTYPE_COSTOFLIVINGINC = 36 ;
        
        //Crediting Rate
        public int CONTENTTYPE_CREDITINGRATE = 35 ;
        
        //Death Benefit scale (e.g. for decreasing term policies)
        public int CONTENTTYPE_DEATHBENEFITSCALE = 44 ;
        
        //Disability Continuance
        public int CONTENTTYPE_DISABILITYCONT = 9 ;
        
        //Disability Recovery
        public int CONTENTTYPE_DISABILITYRECOVERY = 8 ;
        
        //Disabled Lives Mortality
        public int CONTENTTYPE_MORTALITYDISABLED = 2 ;
        
        //Dividend Rates
        public int CONTENTTYPE_DIVIDEND = 46 ;
        
        //Expenses
        public int CONTENTTYPE_EXPENSE = 49 ;
        
        //Extended Term rates
        public int CONTENTTYPE_EXTENDEDTERMRATE = 55 ;
        
        //Fee
        public int CONTENTTYPE_FEE = 66 ;
        
        //Fertility Rates
        public int CONTENTTYPE_FERTILITYRATE = 19 ;
        
        //Generational Mortality
        public int CONTENTTYPE_MORTGENERATIONAL = 3;
        
        //Growth Rate
        public int CONTENTTYPE_GROWTHRATE = 37 ;
        
        //Healthy Lives Mortality
        public int CONTENTTYPE_MORTALITYHEALTHY = 1 ;
        
        //Historical Unit Values
        public int CONTENTTYPE_HISTORICALUNITVAL = 58;
        
        //Inflation Rate
        public int CONTENTTYPE_INFLATIONRATE = 32 ;
        
        //Insured Lives Mortality
        public int CONTENTTYPE_MORTALITYINSURED = 4;
        
        //Interest Rates
        public int CONTENTTYPE_INTERESTRATE = 29 ;
        
        //Life Expectancy
        public int CONTENTTYPE_LIFEEXP = 64 ;
        
        //Life Table
        public int CONTENTTYPE_LIVES = 57 ;
        
        //Load Rates
        public int CONTENTTYPE_LOADRATE = 48 ;
        
        //Marriage
        public int CONTENTTYPE_MARRIAGE = 13 ;
        
        //Medical Cost
        public int CONTENTTYPE_MEDICALCOST = 39 ;
        
        //Modal Factor
        public int CONTENTTYPE_MODALFACTOR = 59 ;
        
        //Net Premium Rates
        public int CONTENTTYPE_NETPREMIUMS = 54 ;
        
        //Net Single Premium
        public int CONTENTTYPE_NETSGLPREMIUMRATE = 47 ;
        
        //Other
        public int OLI_OTHER = 2147483647 ;
        
        //Partial Withdrawal
        public int CONTENTTYPE_PARTIALWITHDRAWAL = 17 ;
        
        //Pay Cap Increase
        public int CONTENTTYPE_PAYCAPINCREASE = 41 ;
        
        //Pay Limit IRC Section 401(a)(17)
        public int CONTENTTYPE_PAYLIMITIRC401 = 28 ;
        
        //Percent Married
        public int CONTENTTYPE_PERCENTMARRIED = 15 ;
        
        //Policy Withdrawal
        public int CONTENTTYPE_POLICYWITHDRAWAL = 16;
        
        //Premium
        public int CONTENTTYPE_PREMIUM = 43 ;
        
        //Premium Persistency
        public int CONTENTTYPE_PREMIUMPERSISTENCY = 18 ;
        
        //Projection Scale
        public int CONTENTTYPE_PROJECTIONSCALE = 22 ;
        
        //Real Growth
        public int CONTENTTYPE_REALGROWTH = 33 ;
        
        //Remarriage
        public int CONTENTTYPE_REMARRIAGE = 14 ;
        
        //Retirement Delayed
        public int CONTENTTYPE_RETIREMENTDELAYED = 12 ;
        
        //Retirement Early
        public int CONTENTTYPE_RETIREMENTEARLY = 11 ;
        
        //Retirement Normal
        public int CONTENTTYPE_RETIREMENTNORMAL = 10 ;
        
        //Salary Scale Percent
        public int CONTENTTYPE_SALARYSCALEPERCENT = 30;
        
        //Salary Scale Ratio
        public int CONTENTTYPE_SALARYSCALERATIO = 31 ;
        
        //Section 415 Limit Increase
        public int CONTENTTYPE_SECTION415LIMITINC = 42;
        
        //Social Security CPI
        public int CONTENTTYPE_SOCIALSECURITYCPI = 25 ;
        
        //Social Security Wage Bases
        public int CONTENTTYPE_SOCSECWAGEBASE = 24 ;
        
        //Surrender Charge
        public int CONTENTTYPE_SURRENDERCHARGE = 51 ;
        
        //Term Conversion rates
        public int CONTENTTYPE_TERMCONVERSIONRATE = 52 ;
        
        //Termination Disability
        public int CONTENTTYPE_TERMDISABILITY = 7 ;
        
        //Termination Involuntary
        public int CONTENTTYPE_TERMINVOLUNTARY = 6 ;
        
        //Termination Voluntary
        public int CONTENTTYPE_TERMVOLUNTARY = 5 ;
        
        //Trend rate
        public int CONTENTTYPE_TRENDRATE = 40 ;
        
        //Unknown
        public int OLI_UNKNOWN = 0 ;
    }

    /**
     * Maintains the set of constants for the required ContentSubType element of the ContentClassification element.
     * The sub type further identifies the type of value information in the table. ContentType provides the general
     * classification.
     *
     * @author gfrosti
     */
    public interface ContentSubType
    {
        // Charge Free Percent
        public int CONTENTSUBTYPE_CHARGEFREEPCT = 3;

        // Charge Rate To Anniversary
        public int CONTENTSUBTYPE_RATETOANNIV = 2;

        // Excess Charge Percent
        public int CONTENTSUBTYPE_EXCESSCHRGPCT = 5;

        // Fee
        public int CONTENTSUBTYPE_FEE = 1;

        // Full Surrender
        public int CONTENTSUBTYPE_FULLSURRENDER = 13;

        // Guaranteed Charge
        public int CONTENTSUBTYPE_GUARCHARGE = 10;

        // High Value
        public int CONTENTSUBTYPE_HIGH = 7;

        // Low Value
        public int CONTENTSUBTYPE_LOW = 6;

        // Maximum Charge
         public int CONTENTSUBTYPE_MAXCHARGE = 9;

        // Maximum Guaranteed Charge
        public int CONTENTSUBTYPE_MAXGUARCHARGE = 11;

        // Mid-Year Value
        public int CONTENTSUBTYPE_MIDYR = 8;

        // Other
        public int OLI_OTHER = 2147483647;

        // Partial Surrender
        public int CONTENTSUBTYPE_PARTIALSURRENDER = 12;

        // Target Percent
        public int CONTENTSUBTYPE_TARGETPCT = 4;

        // Unknown
        public int OLI_UNKNOWN = 0;
    }


    /**
     *  Semantic concept represented by the axis.
     */
    public interface ScaleType
    {
        // Age
        public int SCALETYPE_AGE = 3;

        // Currency
        public int SCALETYPE_CURRENCY = 5;

        // Dates - Fully qualified (YYYY-MM-DD) or reduced precision (YYYY-MM or YYYY).
        public int SCALETYPE_DATE = 1;
        
        // Ordinal Date Dates - relative units of time; I.e., Years, Months, Weeks
        public int SCALETYPE_ORDINALDATE = 2;

        // Other
        public int OLI_OTHER = 2147483647;

        // Unknown
        public int OLI_UNKNOWN = 0;

        // Years of Service
        public int SCALETYPE_SERVICE = 4;
    }
}
