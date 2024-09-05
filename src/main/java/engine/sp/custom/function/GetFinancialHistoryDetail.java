package engine.sp.custom.function;

import contract.*;

import edit.services.db.hibernate.SessionHelper;
import edit.common.*;
import edit.common.vo.FinancialHistoryVO;
import engine.sp.*;

import java.lang.reflect.Array;

import java.util.*;
import java.util.Set;

import org.hibernate.Session;
import fission.utility.*;

/**
 * Custom function to create FinancialHistory detail information and place the data in working storage.
 */
public class GetFinancialHistoryDetail implements FunctionCommand
{    
    private ScriptProcessor scriptProcessor;

    /**
     * Constructor.
     * @param scriptProcessor ScriptProcessor
     */
    public GetFinancialHistoryDetail(ScriptProcessor scriptProcessor)
    {
        this.scriptProcessor = scriptProcessor;
    }

    /**
     * Getter.
     * @return
     */
    private ScriptProcessor getScriptProcessor()
    {
        return scriptProcessor;
    }
    
    public void exec()
    {
        long segmentPK = Long.parseLong(Util.initString(getScriptProcessor().getWSEntry("SegmentPK"), "0"));
        EDITDate date = new EDITDate((String) getScriptProcessor().getWSEntry("FHEffDate"));

        FinancialHistoryVO[] financialHistoryVOs = event.dm.dao.DAOFactory.getFinancialHistoryDAO().findPreviousActiveTrxFinancialHistory(segmentPK, date);
        
        if (financialHistoryVOs != null && financialHistoryVOs.length > 0) {
	        mapFinancialHistoryVector(financialHistoryVOs[0]);
        }
        
        // Activatefunction will Increment Instruction Pointer
    }

    private void mapFinancialHistoryVector(FinancialHistoryVO financialHistoryVO)
    {
        getScriptProcessor().addWSEntry("FHFinancialHistoryPK", String.valueOf(financialHistoryVO.getFinancialHistoryPK()));
        getScriptProcessor().addWSEntry("FHEDITTrxHistoryFK", String.valueOf(financialHistoryVO.getEDITTrxHistoryFK()));
        getScriptProcessor().addWSEntry("FHGrossAmount", financialHistoryVO.getGrossAmount().toString());
        getScriptProcessor().addWSEntry("FHNetAmount", financialHistoryVO.getNetAmount().toString());
        getScriptProcessor().addWSEntry("FHCheckAmount", financialHistoryVO.getCheckAmount().toString());
        getScriptProcessor().addWSEntry("FHFreeAmount", financialHistoryVO.getFreeAmount().toString());
        getScriptProcessor().addWSEntry("FHTaxableBenefit", financialHistoryVO.getTaxableBenefit().toString());
        getScriptProcessor().addWSEntry("FHDisbursementSourceCT", financialHistoryVO.getDisbursementSourceCT());
        getScriptProcessor().addWSEntry("FHLiability", financialHistoryVO.getLiability().toString());
        getScriptProcessor().addWSEntry("FHCommissionableAmount", financialHistoryVO.getCommissionableAmount().toString());
        getScriptProcessor().addWSEntry("FHMaxCommissionAmount", financialHistoryVO.getMaxCommissionAmount().toString());
        getScriptProcessor().addWSEntry("FHCostBasis", financialHistoryVO.getCostBasis().toString());
        getScriptProcessor().addWSEntry("FHAccumulatedValue", financialHistoryVO.getAccumulatedValue().toString());
        getScriptProcessor().addWSEntry("FHSurrenderValue", financialHistoryVO.getSurrenderValue().toString());
        getScriptProcessor().addWSEntry("FHPriorDueDate", financialHistoryVO.getPriorDueDate());
        getScriptProcessor().addWSEntry("FHPriorExtractDate", financialHistoryVO.getPriorExtractDate());
        getScriptProcessor().addWSEntry("FHPriorLapsePendingDate", financialHistoryVO.getPriorLapsePendingDate());
        getScriptProcessor().addWSEntry("FHPriorLapseDate", financialHistoryVO.getPriorLapseDate());
        getScriptProcessor().addWSEntry("FHPriorFixedAmount", financialHistoryVO.getPriorFixedAmount().toString());
        getScriptProcessor().addWSEntry("FHGuarAccumulatedValue", financialHistoryVO.getGuarAccumulatedValue().toString());
        getScriptProcessor().addWSEntry("FHPrevGuidelineSinglePremium", financialHistoryVO.getPrevGuidelineSinglePremium().toString());
        getScriptProcessor().addWSEntry("FHPrevGuidelineLevelPremium", financialHistoryVO.getPrevGuidelineLevelPremium().toString());
        getScriptProcessor().addWSEntry("FHPrevTamra", financialHistoryVO.getPrevTamra().toString());
        getScriptProcessor().addWSEntry("FHPrevTamraStartDate", financialHistoryVO.getPrevTamraStartDate());
        getScriptProcessor().addWSEntry("FHPrevComplexChangeValue", financialHistoryVO.getPrevComplexChangeValue());
        getScriptProcessor().addWSEntry("FHPrevMECGuidelineSinglePrem", financialHistoryVO.getPrevMECGuidelineSinglePrem().toString());
        getScriptProcessor().addWSEntry("FHPrevMECGuidelineLevelPrem", financialHistoryVO.getPrevMECGuidelineLevelPrem().toString());
        getScriptProcessor().addWSEntry("FHPrevMECStatusCT", financialHistoryVO.getPrevMECStatusCT());
        getScriptProcessor().addWSEntry("FHPrevMECDate", financialHistoryVO.getPrevMECDate());
        getScriptProcessor().addWSEntry("FHTaxableIndicator", financialHistoryVO.getTaxableIndicator());
        getScriptProcessor().addWSEntry("FHNetAmountAtRisk", financialHistoryVO.getNetAmountAtRisk().toString());
        getScriptProcessor().addWSEntry("FHPriorCostBasis", financialHistoryVO.getPriorCostBasis().toString());
        getScriptProcessor().addWSEntry("FHPriorRecoveredCostBasis", financialHistoryVO.getPriorRecoveredCostBasis().toString());
        getScriptProcessor().addWSEntry("FHPrevChargeDeductAmount", financialHistoryVO.getPrevChargeDeductAmount().toString());
        getScriptProcessor().addWSEntry("FHPriorTotalActiveBeneficiaries", String.valueOf(financialHistoryVO.getPriorTotalActiveBeneficiaries()));
        getScriptProcessor().addWSEntry("FHPriorRemainingBeneficiaries", String.valueOf(financialHistoryVO.getPriorRemainingBeneficiaries()));
        getScriptProcessor().addWSEntry("FHPriorSettlementAmount", financialHistoryVO.getPriorSettlementAmount().toString());
        getScriptProcessor().addWSEntry("FHPriorLastSettlementValDate", financialHistoryVO.getPriorLastSettlementValDate());
        getScriptProcessor().addWSEntry("FHPrevCumGLP", financialHistoryVO.getPrevCumGLP().toString());
        getScriptProcessor().addWSEntry("FHPrevTotalFaceAmount", financialHistoryVO.getPrevTotalFaceAmount().toString());
        getScriptProcessor().addWSEntry("FHInsuranceInforce", financialHistoryVO.getInsuranceInforce().toString());
        getScriptProcessor().addWSEntry("FHDistributionCodeCT", financialHistoryVO.getDistributionCodeCT());
        getScriptProcessor().addWSEntry("FHNetIncomeAttributable", financialHistoryVO.getNetIncomeAttributable().toString());
        getScriptProcessor().addWSEntry("FHInterestProceeds", financialHistoryVO.getInterestProceeds().toString());
        getScriptProcessor().addWSEntry("FHPriorInitialCYAccumValue", financialHistoryVO.getPriorInitialCYAccumValue().toString());
        getScriptProcessor().addWSEntry("FHSevenPayRate", financialHistoryVO.getSevenPayRate().toString());
        getScriptProcessor().addWSEntry("FHDuration", String.valueOf(financialHistoryVO.getDuration()));
        getScriptProcessor().addWSEntry("FHPriorDeathBenefitOption", String.valueOf(financialHistoryVO.getPriorDeathBenefitOption()));
        getScriptProcessor().addWSEntry("FHUnnecessaryPremiumInd", String.valueOf(financialHistoryVO.getUnnecessaryPremiumInd()));
        getScriptProcessor().addWSEntry("FHPrevMAPEndDate", String.valueOf(financialHistoryVO.getPrevMAPEndDate()));
        getScriptProcessor().addWSEntry("FHPrevTamraInitAdjValue", String.valueOf(financialHistoryVO.getPrevTamraInitAdjValue()));
        getScriptProcessor().addWSEntry("FHCurrentDeathBenefit", String.valueOf(financialHistoryVO.getCurrentDeathBenefit()));
        getScriptProcessor().addWSEntry("FHCurrentCorridorPercent", String.valueOf(financialHistoryVO.getCurrentCorridorPercent()));
        getScriptProcessor().addWSEntry("FHSurrenderCharge", String.valueOf(financialHistoryVO.getSurrenderCharge()));
        getScriptProcessor().addWSEntry("FHCurrIntRate", String.valueOf(financialHistoryVO.getCurrIntRate()));
    }

}
