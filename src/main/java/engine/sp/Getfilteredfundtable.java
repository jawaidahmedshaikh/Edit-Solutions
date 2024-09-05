/*
 * Getfilteredfundtable.java      10/23/2003
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.vo.FilteredFundVO;
import edit.common.vo.ChargeCodeVO;
import edit.common.EDITBigDecimal;
import engine.dm.dao.DAOFactory;
import engine.ChargeCode;

import java.math.BigDecimal;

import fission.utility.Util;

/**
 * This is the implementation for the Getfilteredfundtable instruction.
 * It will access the filterdFundId in working storage and access the table.
 * All the fields will be put on the working storage table for access by
 * the scripts.
 */
public final class Getfilteredfundtable extends InstOneOperand {
	

    /**
     * Getfilteredfundtable constructor
     * <p> 
     * @exception SPException  
     */ 
     public Getfilteredfundtable() throws SPException {
    
        super(); 
     }
     
    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException{
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Note: No compiling is required for this instruction
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {

		sp = execSP;

        long filteredFundId = Long.parseLong((String)sp.getWSEntry("FilteredFundId"));

        FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(filteredFundId);

        if (filteredFundVO != null)
        {
            FilteredFundVO ffundVO = filteredFundVO;
            sp.addWSEntry("FundFK", new String(ffundVO.getFundFK() + ""));
            String number = new String(ffundVO.getFundNumber());
            if (number != null)
            {
                sp.addWSEntry("Number",number);
            }

            String effectiveDate = new String(ffundVO.getEffectiveDate());
            if (effectiveDate != null)
            {
                sp.addWSEntry("EffectiveDate", effectiveDate);
            }

            String terminationDate = new String(ffundVO.getTerminationDate());
            if (terminationDate != null)
            {
                sp.addWSEntry("TerminationDate", terminationDate);
            }

            String pricingDirection =  new String(ffundVO.getPricingDirection());
            if (pricingDirection != null)
            {
                sp.addWSEntry("PricingDirection", pricingDirection);
            }

            String indexingMethodCT = ffundVO.getIndexingMethodCT();
            if (indexingMethodCT != null)
            {
                sp.addWSEntry("IndexingMethod", indexingMethodCT);
            }

            String fundAdjustmentCT = ffundVO.getFundAdjustmentCT();
            if (fundAdjustmentCT != null)
            {
                sp.addWSEntry("FundAdjustment", fundAdjustmentCT);
            }

            String annualSubBucketCT =  ffundVO.getAnnualSubBucketCT();
            if (annualSubBucketCT != null)
            {
                sp.addWSEntry("AnnualSubBucketCT", annualSubBucketCT);
            }

            String fundNewClientCloseDate = ffundVO.getFundNewClientCloseDate();
            if (fundNewClientCloseDate != null)
            {
                sp.addWSEntry("FundNewClientCloseDate", fundNewClientCloseDate);
            }

            String fundNewDepositCloseDate = ffundVO.getFundNewDepositCloseDate();
            if (fundNewDepositCloseDate != null)
            {
                sp.addWSEntry("FundNewDepositCloseDate", fundNewDepositCloseDate);
            }

            String divisionLockupEndDate = ffundVO.getDivisionLockUpEndDate();
            if (divisionLockupEndDate != null)
            {
                sp.addWSEntry("DivisionLockupEndDate", divisionLockupEndDate);
            }

            String divFeesLiquationModeCT = ffundVO.getDivisionFeesLiquidationModeCT();
            if (divFeesLiquationModeCT != null)
            {
                sp.addWSEntry("DivisionFeesLiquidationModeCT", divFeesLiquationModeCT);
            }

            String postLockWithdrawalDateCT = ffundVO.getPostLockWithdrawalDateCT();
            if (postLockWithdrawalDateCT != null)
            {
                sp.addWSEntry("PostLockWithdrawalDateCT", postLockWithdrawalDateCT);
            }
            //The following fields are not returned as null they are returned as zero, always set them
            String guaranteedDuration = new String(ffundVO.getGuaranteedDuration() + "");
            sp.addWSEntry("GuaranteedDuration", guaranteedDuration);

            String indexCapRateGuarPeriod = new String(ffundVO.getIndexCapRateGuarPeriod() + "");
            sp.addWSEntry("IndexCapRateGuarPeriod", indexCapRateGuarPeriod);

            String premiumBonusDuration = new String(ffundVO.getPremiumBonusDuration() + "");
            sp.addWSEntry("PremiumBonusDuration", premiumBonusDuration);

            String minimumTransferAmount = new String(ffundVO.getMinimumTransferAmount() + "");
            sp.addWSEntry("MinimumTransferAmount", minimumTransferAmount);

            String mvaStartingIndexGuarPeriod = new String(ffundVO.getMVAStartingIndexGuarPeriod() + "");
            sp.addWSEntry("MVAStartingIndexGuarPeriod", mvaStartingIndexGuarPeriod);

            String contributionLockUpDuration = new String(ffundVO.getContributionLockUpDuration() + "");
            sp.addWSEntry("ContributionLockUpDuration", contributionLockUpDuration);

            String divisionLevelLockUpDuration = new String(ffundVO.getDivisionLevelLockUpDuration() + "");
            sp.addWSEntry("DivisionLevelLockUpDuration", divisionLevelLockUpDuration);

            String seriesToSeriesEligibilityInd = Util.initString(ffundVO.getSeriesToSeriesEligibilityInd(), "N");
            sp.addWSEntry("SeriesToSeriesEligibilityInd", seriesToSeriesEligibilityInd);

            if ("Y".equalsIgnoreCase(ffundVO.getChargeCodeIndicator()))
            {
//                EDITBigDecimal accumulatedPremium = new EDITBigDecimal((String) sp.getWSEntry("AccumPrem"));
                String newIssuesEligibilityStatus = (String)sp.getWSEntry("NewIssuesEligibilityStatus");
                if (newIssuesEligibilityStatus.equalsIgnoreCase("#NULL"))
                {
                    newIssuesEligibilityStatus = null;
                }

                ChargeCode chargeCode =
                        ChargeCode.findByFilteredFund(filteredFundId, newIssuesEligibilityStatus);

                if (chargeCode != null)
                {
                    ChargeCodeVO chargeCodeVO = (ChargeCodeVO) chargeCode.getVO();
                    if (chargeCodeVO != null)
                    {
                        String chargeCodeNum = chargeCodeVO.getChargeCode();
                        long chargeCodePK = chargeCodeVO.getChargeCodePK();
//                        BigDecimal chargeCodeAccumPremium = chargeCodeVO.getAccumulatedPremium();

                        // lets the script know to get the three fields
                        sp.addWSEntry("ChargeCodeIndicator", "Y");

                        sp.addWSEntry("ChargeCodePK", chargeCodePK + "");
                        sp.addWSEntry("ChargeCode", chargeCodeNum);
//                        sp.addWSEntry("AccumulatedPremium", chargeCodeAccumPremium + "");
                    }
                    else
                    {
                        sp.addWSEntry("ChargeCodeIndicator", "N");
                    }
                }
                else
                {
                    // let the script know that it need not check the 3 fields
                    sp.addWSEntry("ChargeCodeIndicator", "N");
                }
            }
            else
            {
                // let the script know that it need not check the 3 fields
                sp.addWSEntry("ChargeCodeIndicator", "N");
            }

        }

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}