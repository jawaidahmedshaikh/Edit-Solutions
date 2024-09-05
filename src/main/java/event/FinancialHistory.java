/*
 * User: gfrosti
 * Date: Feb 12, 2005
 * Time: 4:16:19 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;
import engine.common.Constants;
import event.dm.dao.*;

import java.util.*;

import org.hibernate.Session;



public class FinancialHistory extends HibernateEntity implements CRUDEntity
{


    public static final String DISBURSEMENTSOURCECT_CHECK = "Check";
    private CRUDEntityImpl crudEntityImpl;
    private FinancialHistoryVO financialHistoryVO;
    private EDITTrxHistory editTrxHistory;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a FinancialHistory entity with a default FinancialHistoryVO.
     */
    public FinancialHistory()
    {
        init();
    }

    public FinancialHistory(EDITTrxHistory editTrxHistory)
    {
        init();

        this.financialHistoryVO.setEDITTrxHistoryFK(editTrxHistory.getPK());
    }

    /**
     * Instantiates a FinancialHistory entity with a supplied FinancialHistoryVO.
     *
     * @param financialHistoryVO
     */
    public FinancialHistory(FinancialHistoryVO financialHistoryVO)
    {
        init();

        this.financialHistoryVO = financialHistoryVO;
    }

    public Long getFinancialHistoryPK()
    {
        return SessionHelper.getPKValue(financialHistoryVO.getFinancialHistoryPK());
    }

    //-- long getFinancialHistoryPK()
    public void setFinancialHistoryPK(Long financialHistoryPK)
    {
        financialHistoryVO.setFinancialHistoryPK(SessionHelper.getPKValue(financialHistoryPK));
    }
    
    
    //- DE - Added 2022-02-28
    public EDITBigDecimal getPrevCumGLP() {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevCumGLP());
    }

    //-- void setFinancialHistoryPK(long)
    public EDITBigDecimal getAccumulatedValue()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getAccumulatedValue());
    }

    //-- java.math.BigDecimal getAccumulatedValue()
    public EDITBigDecimal getCommissionableAmount()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getCommissionableAmount());
    }

    //-- java.math.BigDecimal getCommissionableAmount()
    public EDITBigDecimal getCostBasis()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getCostBasis());
    }

    //-- java.math.BigDecimal getCostBasis()
    public EDITBigDecimal getFreeAmount()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getFreeAmount());
    }

    //-- java.math.BigDecimal getFreeAmount()
    public EDITBigDecimal getGuarAccumulatedValue()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getGuarAccumulatedValue());
    }

    //-- java.math.BigDecimal getGuarAccumulatedValue()
    public EDITBigDecimal getInterestProceeds()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getInterestProceeds());
    }

    //-- java.math.BigDecimal getInterestProceeds()
    public EDITBigDecimal getLiability()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getLiability());
    }

    //-- java.math.BigDecimal getLiability()
    public EDITBigDecimal getMaxCommissionAmount()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getMaxCommissionAmount());
    }

    //-- java.math.BigDecimal getMaxCommissionAmount()
    public EDITBigDecimal getNetAmountAtRisk()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getNetAmountAtRisk());
    }

    //-- java.math.BigDecimal getNetAmountAtRisk()
    public String getPrevComplexChangeValue()
    {
        return financialHistoryVO.getPrevComplexChangeValue();
    }

    //-- java.math.BigDecimal getPrevFaceAmount()
    public EDITBigDecimal getPrevGuidelineLevelPremium()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevGuidelineLevelPremium());
    }

    //-- java.math.BigDecimal getPrevGuidelineLevelPremium()
    public EDITBigDecimal getPrevGuidelineSinglePremium()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevGuidelineSinglePremium());
    }

    //-- java.math.BigDecimal getPrevGuidelineSinglePremium()
    public EDITDate getPrevMECDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPrevMECDate());
    }

    public EDITDate getPrevMAPEndDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPrevMAPEndDate());
    }

    //-- java.lang.String getPrevMECDate()
    public EDITBigDecimal getPrevMECGuidelineLevelPrem()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevMECGuidelineLevelPrem());
    }

    //-- java.math.BigDecimal getPrevMECGuidelineLevelPrem()
    public EDITBigDecimal getPrevMECGuidelineSinglePrem()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevMECGuidelineSinglePrem());
    }

    //-- java.math.BigDecimal getPrevMECGuidelineSinglePrem()
    public String getPrevMECStatusCT()
    {
        return financialHistoryVO.getPrevMECStatusCT();
    }

    //-- java.lang.String getPrevMECStatusCT()
    public EDITBigDecimal getPrevTamra()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevTamra());
    }

    public EDITBigDecimal getPrevTamraInitAdjValue()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevTamraInitAdjValue());
    }

    //-- java.math.BigDecimal getPrevTamra()
    public EDITDate getPrevTamraStartDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPrevTamraStartDate());
    }

    //-- java.lang.String getPrevTamraStartDate()
    public EDITBigDecimal getPriorCostBasis()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPriorCostBasis());
    }

    //-- java.math.BigDecimal getPriorCostBasis()
    public EDITDate getPriorDueDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPriorDueDate());
    }

    //-- java.lang.String getPriorDueDate()
    public EDITDate getPriorExtractDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPriorExtractDate());
    }

    //-- java.lang.String getPriorExtractDate()
    public EDITBigDecimal getPriorFixedAmount()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPriorFixedAmount());
    }

    //-- java.math.BigDecimal getPriorFixedAmount()
    public EDITDate getPriorLapseDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPriorLapseDate());
    }

    public EDITBigDecimal getPriorInitialCYAccumValue()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPriorInitialCYAccumValue());
    }

    //-- java.lang.String getPriorLapseDate()
    public EDITDate getPriorLapsePendingDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPriorLapsePendingDate());
    }

    //-- java.lang.String getPriorLapsePendingDate()
    public EDITBigDecimal getPriorRecoveredCostBasis()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPriorRecoveredCostBasis());
    }

    //-- java.math.BigDecimal getPriorRecoveredCostBasis()
    public EDITBigDecimal getSurrenderValue()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getSurrenderValue());
    }

    //-- java.math.BigDecimal getSurrenderValue()
    public EDITBigDecimal getTaxableBenefit()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getTaxableBenefit());
    }

    //-- java.math.BigDecimal getTaxableBenefit()
    public String getTaxableIndicator()
    {
        return financialHistoryVO.getTaxableIndicator();
    }

    /**
      * Getter
      * @return
      */
     public EDITBigDecimal getPrevTotalFaceAmount()
     {
         return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevTotalFaceAmount());
     }

    /**
      * Getter
      * @return
      */
     public EDITBigDecimal getInsuranceInforce()
     {
         return SessionHelper.getEDITBigDecimal(financialHistoryVO.getInsuranceInforce());
     }

    /**
     * getter
     * @return
     */
    public EDITDate getPriorLastSettlementValDate()
    {
        return SessionHelper.getEDITDate(financialHistoryVO.getPriorLastSettlementValDate());
    } //-- java.lang.String getPriorLastSettlementValDate()

    /**
     * getter
     * @return
     */
    public int getPriorRemainingBeneficiaries()
    {
        return financialHistoryVO.getPriorRemainingBeneficiaries();
    } //-- int getPriorRemainingBeneficiaries()

    /**
     * getter
     * @return
     */
    public EDITBigDecimal getPriorSettlementAmount()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPriorSettlementAmount());
    } //-- java.math.BigDecimal getPriorSettlementAmount()

    /**
     * getter
     * @return
     */
    public int getPriorTotalActiveBeneficiaries()
    {
        return financialHistoryVO.getPriorTotalActiveBeneficiaries();
    }

    public void setAccumulatedValue(EDITBigDecimal accumulatedValue)
    {
        financialHistoryVO.setAccumulatedValue(SessionHelper.getEDITBigDecimal(accumulatedValue));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSevenPayRate()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getSevenPayRate());
    }

    public EDITBigDecimal getCurrentDeathBenefit()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getCurrentDeathBenefit());
    }

    public EDITBigDecimal getCurrIntRate()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getCurrIntRate());
    }

    public EDITBigDecimal getCurrentCorridorPercent()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getCurrentCorridorPercent());
    }
    
    public EDITBigDecimal getSurrenderCharge()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getSurrenderCharge());
    }

    public void setPrevCumGLP(EDITBigDecimal prevCumGLP) {
        financialHistoryVO.setPrevCumGLP(SessionHelper.getEDITBigDecimal(prevCumGLP));
    }

    //-- void setAccumulatedValue(java.math.BigDecimal)
    public void setCheckAmount(EDITBigDecimal checkAmount)
    {
        financialHistoryVO.setCheckAmount(SessionHelper.getEDITBigDecimal(checkAmount));
    }

    //-- void setCheckAmount(java.math.BigDecimal)
    public void setCommissionableAmount(EDITBigDecimal commissionableAmount)
    {
        financialHistoryVO.setCommissionableAmount(SessionHelper.getEDITBigDecimal(commissionableAmount));
    }

    //-- void setCommissionableAmount(java.math.BigDecimal)
    public void setCostBasis(EDITBigDecimal costBasis)
    {
        financialHistoryVO.setCostBasis(SessionHelper.getEDITBigDecimal(costBasis));
    }

    //-- void setCostBasis(java.math.BigDecimal)
    public void setFreeAmount(EDITBigDecimal freeAmount)
    {
        financialHistoryVO.setFreeAmount(SessionHelper.getEDITBigDecimal(freeAmount));
    }

    //-- void setGrossAmount(java.math.BigDecimal)
    public void setGuarAccumulatedValue(EDITBigDecimal guarAccumulatedValue)
    {
        financialHistoryVO.setGuarAccumulatedValue(SessionHelper.getEDITBigDecimal(guarAccumulatedValue));
    }

    //-- void setGuarAccumulatedValue(java.math.BigDecimal)
    public void setInterestProceeds(EDITBigDecimal interestProceeds)
    {
        financialHistoryVO.setInterestProceeds(SessionHelper.getEDITBigDecimal(interestProceeds));
    }

    //-- void setInterestProceeds(java.math.BigDecimal)
    public void setLiability(EDITBigDecimal liability)
    {
        financialHistoryVO.setLiability(SessionHelper.getEDITBigDecimal(liability));
    }

    //-- void setLiability(java.math.BigDecimal)
    public void setMaxCommissionAmount(EDITBigDecimal maxCommissionAmount)
    {
        financialHistoryVO.setMaxCommissionAmount(SessionHelper.getEDITBigDecimal(maxCommissionAmount));
    }

    //-- void setNetAmount(java.math.BigDecimal)
    public void setNetAmountAtRisk(EDITBigDecimal netAmountAtRisk)
    {
        financialHistoryVO.setNetAmountAtRisk(SessionHelper.getEDITBigDecimal(netAmountAtRisk));
    }

    //-- void setNetAmountAtRisk(java.math.BigDecimal)
    public void setPrevComplexChangeValue(String prevComplexChangeValue)
    {
        financialHistoryVO.setPrevComplexChangeValue(prevComplexChangeValue);
    }


    //-- void setPrevFaceAmount(java.math.BigDecimal)
    public void setPrevGuidelineLevelPremium(EDITBigDecimal prevGuidelineLevelPremium)
    {
        financialHistoryVO.setPrevGuidelineLevelPremium(SessionHelper.getEDITBigDecimal(prevGuidelineLevelPremium));
    }

    //-- void setPrevGuidelineLevelPremium(java.math.BigDecimal)
    public void setPrevGuidelineSinglePremium(EDITBigDecimal prevGuidelineSinglePremium)
    {
        financialHistoryVO.setPrevGuidelineSinglePremium(SessionHelper.getEDITBigDecimal(prevGuidelineSinglePremium));
    }

    //-- void setPrevGuidelineSinglePremium(java.math.BigDecimal)
    public void setPrevMECDate(EDITDate prevMECDate)
    {
        financialHistoryVO.setPrevMECDate(SessionHelper.getEDITDate(prevMECDate));
    }

    public void setPrevMAPEndDate(EDITDate prevMAPEndDate)
    {
        financialHistoryVO.setPrevMAPEndDate(SessionHelper.getEDITDate(prevMAPEndDate));
    }

    //-- void setPrevMECDate(java.lang.String)
    public void setPrevMECGuidelineLevelPrem(EDITBigDecimal prevMECGuidelineLevelPrem)
    {
        financialHistoryVO.setPrevMECGuidelineLevelPrem(SessionHelper.getEDITBigDecimal(prevMECGuidelineLevelPrem));
    }

    //-- void setPrevMECGuidelineLevelPrem(java.math.BigDecimal)
    public void setPrevMECGuidelineSinglePrem(EDITBigDecimal prevMECGuidelineSinglePrem)
    {
        financialHistoryVO.setPrevMECGuidelineSinglePrem(SessionHelper.getEDITBigDecimal(prevMECGuidelineSinglePrem));
    }

    //-- void setPrevMECGuidelineSinglePrem(java.math.BigDecimal)
    public void setPrevMECStatusCT(String prevMECStatusCT)
    {
        financialHistoryVO.setPrevMECStatusCT(prevMECStatusCT);
    }

    //-- void setPrevMECStatusCT(java.lang.String)
    public void setPrevTamra(EDITBigDecimal prevTamra)
    {
        financialHistoryVO.setPrevTamra(SessionHelper.getEDITBigDecimal(prevTamra));
    }

    public void setPrevTamraInitAdjValue(EDITBigDecimal prevTamraInitAdjValue)
    {
        financialHistoryVO.setPrevTamraInitAdjValue(SessionHelper.getEDITBigDecimal(prevTamraInitAdjValue));
    }

    //-- void setPrevTamra(java.math.BigDecimal)
    public void setPrevTamraStartDate(EDITDate prevTamraStartDate)
    {
        financialHistoryVO.setPrevTamraStartDate(SessionHelper.getEDITDate(prevTamraStartDate));
    }

    //-- void setPrevTamraStartDate(java.lang.String)
    public void setPriorCostBasis(EDITBigDecimal priorCostBasis)
    {
        financialHistoryVO.setPriorCostBasis(SessionHelper.getEDITBigDecimal(priorCostBasis));
    }

    //-- void setPriorCostBasis(java.math.BigDecimal)
    public void setPriorDueDate(EDITDate priorDueDate)
    {
        financialHistoryVO.setPriorDueDate(SessionHelper.getEDITDate(priorDueDate));
    }

    //-- void setPriorDueDate(java.lang.String)
    public void setPriorExtractDate(EDITDate priorExtractDate)
    {
        financialHistoryVO.setPriorExtractDate(SessionHelper.getEDITDate(priorExtractDate));
    }

    //-- void setPriorExtractDate(java.lang.String)
    public void setPriorFixedAmount(EDITBigDecimal priorFixedAmount)
    {
        financialHistoryVO.setPriorFixedAmount(SessionHelper.getEDITBigDecimal(priorFixedAmount));
    }

    public void setPriorInitialCYAccumValue(EDITBigDecimal priorInitialCYAccumValue)
    {
        financialHistoryVO.setPriorInitialCYAccumValue(SessionHelper.getEDITBigDecimal(priorInitialCYAccumValue));
    }

    //-- void setPriorFixedAmount(java.math.BigDecimal)
    public void setPriorLapseDate(EDITDate priorLapseDate)
    {
        financialHistoryVO.setPriorLapseDate(SessionHelper.getEDITDate(priorLapseDate));
    }

    //-- void setPriorLapseDate(java.lang.String)
    public void setPriorLapsePendingDate(EDITDate priorLapsePendingDate)
    {
        financialHistoryVO.setPriorLapsePendingDate(SessionHelper.getEDITDate(priorLapsePendingDate));
    }

    //-- void setPriorLapsePendingDate(java.lang.String)
    public void setPriorRecoveredCostBasis(EDITBigDecimal priorRecoveredCostBasis)
    {
        financialHistoryVO.setPriorRecoveredCostBasis(SessionHelper.getEDITBigDecimal(priorRecoveredCostBasis));
    }

    //-- void setPriorRecoveredCostBasis(java.math.BigDecimal)
    public void setSurrenderValue(EDITBigDecimal surrenderValue)
    {
        financialHistoryVO.setSurrenderValue(SessionHelper.getEDITBigDecimal(surrenderValue));
    }

    //-- void setSurrenderValue(java.math.BigDecimal)
    public void setTaxableBenefit(EDITBigDecimal taxableBenefit)
    {
        financialHistoryVO.setTaxableBenefit(SessionHelper.getEDITBigDecimal(taxableBenefit));
    }

    //-- void setTaxableBenefit(java.math.BigDecimal)
    public void setTaxableIndicator(String taxableIndicator)
    {
        financialHistoryVO.setTaxableIndicator(taxableIndicator);
    }

     //-- void setPrevSegmentStatus(java.lang.String) 
    public String getDisbursementSourceCT()
    {
        return financialHistoryVO.getDisbursementSourceCT();
    }

    public String getPriorDeathBenefitOption()
    {
        return financialHistoryVO.getPriorDeathBenefitOption();
    }

    public String getUnnecessaryPremiumInd()
    {
        return financialHistoryVO.getUnnecessaryPremiumInd();
    }


    //-- java.lang.String getDisbursementSourceCT()
    public void setDisbursementSourceCT(String disbursementSourceCT)
    {
        financialHistoryVO.setDisbursementSourceCT(disbursementSourceCT);
    }

    /**
     * setter
     * @param priorLastSettlementValDate
     */
    public void setPriorLastSettlementValDate(EDITDate priorLastSettlementValDate)
    {
        financialHistoryVO.setPriorLastSettlementValDate(SessionHelper.getEDITDate(priorLastSettlementValDate));
    } //-- void setPriorLastSettlementValDate(java.lang.String)

    /**
     * setter
     * @param priorRemainingBeneficiaries
     */
    public void setPriorRemainingBeneficiaries(int priorRemainingBeneficiaries)
    {
        financialHistoryVO.setPriorRemainingBeneficiaries(priorRemainingBeneficiaries);
    } //-- void setPriorRemainingBeneficiaries(int)

    /**
     * setter
     * @param priorSettlementAmount
     */
    public void setPriorSettlementAmount(EDITBigDecimal priorSettlementAmount)
    {
        financialHistoryVO.setPriorSettlementAmount(SessionHelper.getEDITBigDecimal(priorSettlementAmount));
    } //-- void setPriorSettlementAmount(java.math.BigDecimal)

    /**
     * setter
     * @param priorTotalActiveBenficiaries
     */
    public void setPriorTotalActiveBeneficiaries(int priorTotalActiveBenficiaries)
    {
        financialHistoryVO.setPriorTotalActiveBeneficiaries(priorTotalActiveBenficiaries);
    } //-- void setPriorTotalActiveBenficiaries(int)

    /**
     * Setter.
     * @param sevenPayRate
     */
    public void setSevenPayRate(EDITBigDecimal sevenPayRate)
    {
        financialHistoryVO.setSevenPayRate(SessionHelper.getEDITBigDecimal(sevenPayRate));
    }
    
    public void setCurrentDeathBenefit(EDITBigDecimal currentDeathBenefit)
    {
        financialHistoryVO.setCurrentDeathBenefit(SessionHelper.getEDITBigDecimal(currentDeathBenefit));
    }

    public void setCurrIntRate(EDITBigDecimal currIntRate)
    {
        financialHistoryVO.setCurrIntRate(SessionHelper.getEDITBigDecimal(currIntRate));
    }

    public void setCurrentCorridorPercent(EDITBigDecimal currentCorridorPercent)
    {
        financialHistoryVO.setCurrentCorridorPercent(SessionHelper.getEDITBigDecimal(currentCorridorPercent));
    }
    
    public void setSurrenderCharge(EDITBigDecimal surrenderCharge)
    {
        financialHistoryVO.setSurrenderCharge(SessionHelper.getEDITBigDecimal(surrenderCharge));
    }

    public EDITTrxHistory getEDITTrxHistory()
    {
        return editTrxHistory;
    }

    public void setEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        this.editTrxHistory = editTrxHistory;
    }

    /**
     * getter
     * @return
     */
    public EDITBigDecimal getPrevChargeDeductAmount()
    {
          return SessionHelper.getEDITBigDecimal(financialHistoryVO.getPrevChargeDeductAmount());
    }

    /**
     * setter
     * @param prevChargeDeductAmount
     */
    public void setPrevChargeDeductAmount(EDITBigDecimal prevChargeDeductAmount)
    {
        financialHistoryVO.setPrevChargeDeductAmount(SessionHelper.getEDITBigDecimal(prevChargeDeductAmount));
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (financialHistoryVO == null)
        {
            financialHistoryVO = new FinancialHistoryVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return financialHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return financialHistoryVO.getFinancialHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.financialHistoryVO = (FinancialHistoryVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    public FinancialHistoryVO getAsVO()
    {
        return financialHistoryVO;
    }

    /**
     * Finder.
     *
     * @param financialHistoryPK
     */
    public static final FinancialHistory findByPK(long financialHistoryPK)
    {
        FinancialHistory financialHistory = null;

        FinancialHistoryVO[] financialHistoryVOs = new FinancialHistoryDAO().findByPK(financialHistoryPK);

        if (financialHistoryVOs != null)
        {
            financialHistory = new FinancialHistory(financialHistoryVOs[0]);
        }

        return financialHistory;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGrossAmount()
    {
        return new EDITBigDecimal(financialHistoryVO.getGrossAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNetAmount()
    {
        return new EDITBigDecimal(financialHistoryVO.getNetAmount());
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public static FinancialHistory findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        FinancialHistory financialHistory = null;

        FinancialHistoryVO[] financialHistoryVOs = new FinancialHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryPK);

        if (financialHistoryVOs != null)
        {
            financialHistory = new FinancialHistory(financialHistoryVOs[0]);
        }

        return financialHistory;
    }

    /**
     * Setter.
     * @param grossAmount
     */
    public void setGrossAmount(EDITBigDecimal grossAmount)
    {
        this.financialHistoryVO.setGrossAmount(SessionHelper.getEDITBigDecimal(grossAmount));
    }

    /**
     * Setter.
     * @param netAmount
     */
    public void setNetAmount(EDITBigDecimal netAmount)
    {
        this.financialHistoryVO.setNetAmount(netAmount.getBigDecimal());
    }

    /**
     * Setter.
     * @param disbursementSourceCT
     */
    public void setDisbursementCT(String disbursementSourceCT)
    {
        this.financialHistoryVO.setDisbursementSourceCT(disbursementSourceCT);
    }

    public void setPriorDeathBenefitOption(String priorDeathBenefitOption)
    {
        this.financialHistoryVO.setPriorDeathBenefitOption(priorDeathBenefitOption);
    }

    public void setUnnecessaryPremiumInd(String unnecessaryPremiumInd)
    {
        this.financialHistoryVO.setUnnecessaryPremiumInd(unnecessaryPremiumInd);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCheckAmount()
    {
        return new EDITBigDecimal(this.financialHistoryVO.getCheckAmount());
    }

    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(financialHistoryVO.getEDITTrxHistoryFK());
    }
     //-- long getEDITTrxHistoryFK()

    public void setEDITTrxHistoryFK(Long EDITTrxHistoryFK)
    {
        financialHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(EDITTrxHistoryFK));
    }
     //-- void setEDITTrxHistoryFK(long)

    /**
     * Getter
     * @return
     */
    public String getDistributionCodeCT()
    {
        return financialHistoryVO.getDistributionCodeCT();
    } //-- java.lang.String getDistributionCodeCT()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getNetIncomeAttributable()
    {
        return SessionHelper.getEDITBigDecimal(financialHistoryVO.getNetIncomeAttributable());

    } //-- java.math.BigDecimal getNetIncomeAttributable()

    /**
     * Setter
     * @param distributionCodeCT
     */
    public void setDistributionCodeCT(String distributionCodeCT)
    {
        financialHistoryVO.setDistributionCodeCT(distributionCodeCT);
    } //-- void setDistributionCodeCT(java.lang.String)

    /**
     * Setter
     * @param netIncomeAttributable
     */
    public void setNetIncomeAttributable(EDITBigDecimal netIncomeAttributable)
    {
        financialHistoryVO.setNetIncomeAttributable(SessionHelper.getEDITBigDecimal(netIncomeAttributable));
    } //-- void setNetIncomeAttributable(java.math.BigDecimal)

    /**
     * Setter
     * @param prevTotalFaceAmount
     */
    public void setPrevTotalFaceAmount(EDITBigDecimal prevTotalFaceAmount)
    {
        financialHistoryVO.setPrevTotalFaceAmount(SessionHelper.getEDITBigDecimal(prevTotalFaceAmount));
    }

    /**
     * Setter
     * @param insuranceInforce
     */
    public void setInsuranceInforce(EDITBigDecimal insuranceInforce)
    {
        financialHistoryVO.setInsuranceInforce(SessionHelper.getEDITBigDecimal(insuranceInforce));
    }

    /**
     * Getter
     * @return
     */
    public int getDuration()
    {
        return financialHistoryVO.getDuration();
    }

    /**
     * Setter
     * @param duration
     */
    public void setDuration(int duration)
    {
        financialHistoryVO.setDuration(duration);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, FinancialHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, FinancialHistory.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return FinancialHistory.DATABASE;
    }

    /**
     * Originally in FinancialHistoryDAO.findByEditTrxHistoryPK
     * @param editTrxHistoryPK
     * @return
     */
    public static FinancialHistory[] findBy_EDITTrxHistoryPK(Long editTrxHistoryPK)
    {
        String hql = " select financialHistory from FinancialHistory financialHistory" +
                    " where financialHistory.EDITTrxHistoryFK = :editTrxHistoryPK";

        Map params = new HashMap();

        params.put("editTrxHistoryPK", editTrxHistoryPK);

        List results = SessionHelper.executeHQL(hql, params, FinancialHistory.DATABASE);

        return (FinancialHistory[]) results.toArray(new FinancialHistory[results.size()]);
    }

    public static FinancialHistory[] findBySegment_Date_CheckAmount(Long segmentPK, EDITDate processDate, EDITBigDecimal checkAmount)
    {
          String hql = "select financialHistory from FinancialHistory financialHistory" +
                       " join financialHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and (editTrxHistory.ProcessDateTime >= :minProcessDateTime" +
                       " and editTrxHistory.ProcessDateTime <= :maxProcessDateTime)" +
                       " and financialHistory.CheckAmount = :checkAmount";

          Map params = new EDITMap("segmentPK", segmentPK)
                        .put("minProcessDateTime", new EDITDateTime(processDate + " " + EDITDateTime.DEFAULT_MIN_TIME))
                        .put("maxProcessDateTime", new EDITDateTime(processDate + " " + EDITDateTime.DEFAULT_MAX_TIME))
                        .put("checkAmount", checkAmount);

          Session session = null;

          List<FinancialHistory> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(FinancialHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          }
          finally
          {
            if (session != null) session.close();
          }

          return results.toArray(new FinancialHistory[results.size()]);
    }
    
    public static FinancialHistory[] findActiveByContractNumber(String contractNumber)
    {
          String hql = "select financialHistory from FinancialHistory financialHistory" +
                       " join financialHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " join contractSetup.Segment segment " +
                       " where segment.ContractNumber = :contractNumber" +
                       " and editTrx.Status in ('N', 'A') and editTrx.PendingStatus = 'H' " +
                       " order by editTrx.EffectiveDate desc, editTrx.EDITTrxPK desc)";

          Map params = new EDITMap("contractNumber", contractNumber);

          Session session = null;

          List<FinancialHistory> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(FinancialHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (results.isEmpty()) {
              return null;
          } else {
        	  return results.toArray(new FinancialHistory[results.size()]);
          }
    }
    
    public static FinancialHistory[] findAllByContractNumber(String contractNumber)
    {
          String hql = "select financialHistory from FinancialHistory financialHistory" +
                       " join financialHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " join contractSetup.Segment segment " +
                       " where segment.ContractNumber = :contractNumber" +
                       " and editTrx.PendingStatus = 'H' " +
                       " order by editTrx.EffectiveDate desc, editTrx.EDITTrxPK desc)";

          Map params = new EDITMap("contractNumber", contractNumber);

          Session session = null;

          List<FinancialHistory> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(FinancialHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (results.isEmpty()) {
              return null;
          } else {
        	  return results.toArray(new FinancialHistory[results.size()]);
          }
    }
    

    public static FinancialHistory[] findActiveByContractNumberAndTransactionType(String contractNumber, String transactionType)
    {
          String hql = "select financialHistory from FinancialHistory financialHistory" +
                       " join financialHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " join contractSetup.Segment segment " +
                       " where segment.ContractNumber = :contractNumber" +
                       " and editTrx.Status in ('N', 'A') and editTrx.PendingStatus = 'H' " +
                       " and editTrx.TransactionTypeCT = :transactionType " +
                       " order by editTrx.EffectiveDate desc, editTrx.EDITTrxPK desc)";

          Map params = new EDITMap("contractNumber", contractNumber)
          	.put("transactionType", transactionType);


          Session session = null;

          List<FinancialHistory> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(FinancialHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (results.isEmpty()) {
              return null;
          } else {
        	  return results.toArray(new FinancialHistory[results.size()]);
          }
    }
    
    public static FinancialHistory[] findAllByContractNumberAndTransactionType(String contractNumber, String transactionType)
    {
          String hql = "select financialHistory from FinancialHistory financialHistory" +
                       " join financialHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " join contractSetup.Segment segment " +
                       " where segment.ContractNumber = :contractNumber" +
                       " and editTrx.PendingStatus = 'H' " +
                       " and editTrx.TransactionTypeCT = :transactionType " +
                       " order by editTrx.EffectiveDate desc, editTrx.EDITTrxPK desc)";

          Map params = new EDITMap("contractNumber", contractNumber)
                	.put("transactionType", transactionType);

          Session session = null;

          List<FinancialHistory> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(FinancialHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (results.isEmpty()) {
              return null;
          } else {
        	  return results.toArray(new FinancialHistory[results.size()]);
          }
    }
    
    public static FinancialHistory[] findBySegment_LatestMVISTrx(Long segmentPK)
    {
          String hql = "select financialHistory from FinancialHistory financialHistory" +
                       " join financialHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and editTrx.Status in ('N', 'A') and editTrx.PendingStatus = 'H' " +
	                   " and editTrx.TransactionTypeCT in ('MV', 'IS')" +
                       " order by editTrx.EffectiveDate desc)";

          Map params = new EDITMap("segmentPK", segmentPK);

          Session session = null;

          List<FinancialHistory> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(FinancialHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (results.isEmpty()) {
              return null;
          } else {
        	  return results.toArray(new FinancialHistory[results.size()]);
          }
    }

    /**
     * Finder ComplexChange and its Premium Tamra Retest transactions for a given segment and effectiveDate
     * @param segmentPK
     * @param effectiveDate
     * @return
     */
    public static FinancialHistory findBy_EDITTrxPK_UsingCRUD(long editTrxPK)
    {
        FinancialHistory[] financialHistories = (FinancialHistory[]) CRUDEntityImpl.mapVOToEntity(new FinancialHistoryDAO().findBy_EDITTrxPK(editTrxPK), FinancialHistory.class);
        return financialHistories[0];
    }

    public static EDITBigDecimal sumPremiumsPaidTD(Long segmentPK, EDITDate fromDate, EDITDate toDate, String targetField)
    {
        Object summedAmount = null;
        EDITBigDecimal totalPremium = new EDITBigDecimal();

        String hql = setSumTransactionHQL(targetField);

        Map params = new EDITMap("segmentPK", segmentPK)
                    .put("transactionTypeCT", "PY")
                    .put("fromDate", fromDate)
                    .put("toDate", toDate);

        List results = SessionHelper.executeHQL(hql, params, FinancialHistory.DATABASE);
        if (!results.isEmpty())
        {
            summedAmount = results.get(0);
        }

        if (summedAmount != null)
        {
            totalPremium = new EDITBigDecimal(summedAmount.toString());
        }

        params.clear();
        results.clear();
        summedAmount = null;
        params = new EDITMap("segmentPK", segmentPK)
                    .put("transactionTypeCT", "WP")
                    .put("fromDate", fromDate)
                    .put("toDate", toDate);

        results = SessionHelper.executeHQL(hql, params, FinancialHistory.DATABASE);
        if (!results.isEmpty())
        {
            summedAmount = results.get(0);
        }

        if (summedAmount != null)
        {
            totalPremium = totalPremium.addEditBigDecimal(new EDITBigDecimal(summedAmount.toString()));
        }

        return totalPremium;
    }

    public static EDITBigDecimal sumLTCPayments(Long segmentPK, EDITDate fromDate, EDITDate toDate, String targetField)
    {
        Object summedAmount = null;
        EDITBigDecimal totalPremium = new EDITBigDecimal();

        String hql = setSumTransactionHQL(targetField);

        Map params = new EDITMap("segmentPK", segmentPK)
                    .put("transactionTypeCT", "CPO")
                    .put("fromDate", fromDate)
                    .put("toDate", toDate);

        List results = SessionHelper.executeHQL(hql, params, FinancialHistory.DATABASE);
        if (!results.isEmpty())
        {
            summedAmount = results.get(0);
        }

        if (summedAmount != null)
        {
            totalPremium = new EDITBigDecimal(summedAmount.toString());
        }

        return totalPremium;
    }

    public static String setSumTransactionHQL(String targetFieldName)
    {
       String hql = " select sum(" + targetFieldName + ")" +
                    " from FinancialHistory financialHistory" +
                    " join financialHistory.EDITTrxHistory editTrxHistory" +
                    " join editTrxHistory.EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentPK" +
                    " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                    " and editTrx.EffectiveDate >= :fromDate" +
                    " and editTrx.EffectiveDate <= :toDate" +
                    " and editTrx.Status in ('N', 'A')";

        return hql;
    }
    
    public static FinancialHistory[] findFinancialHistory_ByTrxType(Long segmentPK, EDITDate effectiveDate, String transationTypeCT)
    {
          String hql = "select financialHistory from FinancialHistory financialHistory" +
                       " join financialHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and editTrx.EffectiveDate = :effectiveDate " +
	                   " and editTrx.TransactionTypeCT = :transationTypeCT " + 
                       " and editTrx.Status in ('N', 'A') " +
                       " and editTrx.PendingStatus = 'H' " +
                       " order by editTrx.EffectiveDate desc";

          Map params = new EDITMap("segmentPK", segmentPK)
                  .put("effectiveDate", effectiveDate)
          		  .put("transationTypeCT", transationTypeCT);

          Session session = null;

          List<FinancialHistory> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(FinancialHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (results.isEmpty()) {
              return null;
          } else {
        	  return results.toArray(new FinancialHistory[results.size()]);
          }
    }
    
    public static EDITBigDecimal sumTransaction_MultipleTrxTypes(Long segmentPK, EDITDate fromDate, EDITDate toDate, String targetFieldName, List<String> transactionTypes)
    {
        Object summedAmount = null;
        EDITBigDecimal total = new EDITBigDecimal();

        String hql = "select sum(financialHistory." + targetFieldName + ")" +
                " from FinancialHistory financialHistory" +
                " join financialHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +
                " join editTrx.ClientSetup clientSetup" +
                " join clientSetup.ContractSetup contractSetup" +
                " where contractSetup.SegmentFK = :segmentPK" +
                " and editTrx.TransactionTypeCT in (:transactionTypes)" +
                " and editTrx.EffectiveDate >= :fromDate" +
                " and editTrx.EffectiveDate <= :toDate" +
                " and editTrx.PendingStatus = 'H' " +
                " and editTrx.Status in ('N', 'A')";

        Map params = new EDITMap("segmentPK", segmentPK)
                    .put("transactionTypes", transactionTypes)
                    .put("fromDate", fromDate)
                    .put("toDate", toDate);

        List results = SessionHelper.executeHQL(hql, params, FinancialHistory.DATABASE);
        if (!results.isEmpty())
        {
            summedAmount = results.get(0);
        }

        if (summedAmount != null)
        {
            total = new EDITBigDecimal(summedAmount.toString());
        }

        return total;
    }
    
    public static EDITBigDecimal sumCharge_ByChargeType(Long segmentPK, EDITDate fromDate, EDITDate toDate, String chargeTypeCT)
    {
    	Object summedAmount = null;
        EDITBigDecimal total = new EDITBigDecimal();
        
    	String hql = "select sum(chargeHistory.ChargeAmount)" +
                " from ChargeHistory chargeHistory" +
                " join chargeHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +
                " join editTrx.ClientSetup clientSetup" +
                " join clientSetup.ContractSetup contractSetup" +
                " where contractSetup.SegmentFK = :segmentPK" +
                " and chargeHistory.ChargeTypeCT = :chargeTypeCT" +
                " and editTrx.EffectiveDate >= :fromDate" +
                " and editTrx.EffectiveDate <= :toDate" +
                " and editTrx.PendingStatus = 'H' " +
                " and editTrx.Status in ('N', 'A')";
                
	    Map params = new EDITMap("segmentPK", segmentPK)
	                    .put("chargeTypeCT", chargeTypeCT)
	                    .put("fromDate", fromDate)
	                    .put("toDate", toDate);
	                
	    Session session = null;                
	                 
	    
        List results = SessionHelper.executeHQL(hql, params, FinancialHistory.DATABASE);
        if (!results.isEmpty())
        {
            summedAmount = results.get(0);
        }

        if (summedAmount != null)
        {
            total = new EDITBigDecimal(summedAmount.toString());
        }

        return total;
    }
}
