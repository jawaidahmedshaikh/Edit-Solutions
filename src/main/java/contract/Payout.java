/*
 * User: cgleason
 * Date: Jan 11, 2005
 * Time: 8:48:51 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;


import contract.dm.dao.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import java.math.*;

import java.util.*;


public class Payout extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private PayoutVO payoutVO;
    private Segment segment;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a Payout entity with a default PayoutVO.
     */
    public Payout()
    {
        init();
    }

    /**
     * Instantiates a Payout entity with a supplied PayoutVO.
     *
     * @param payoutVO
     */
    public Payout(PayoutVO payoutVO)
    {
        init();

        this.payoutVO = payoutVO;
    }

    /**
     * Instantiates a Payout entity with a supplied PayoutVO.
     *
     * @param payoutVO
     */
    public Payout(PayoutVO payoutVO, Segment segment)
    {
        init();

        this.payoutVO = payoutVO;
        this.segment = segment;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (payoutVO == null)
        {
            payoutVO = new PayoutVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * Getter.
     * @return
     */
    public int getCertainDuration()
    {
        return payoutVO.getCertainDuration();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getCertainPeriodEndDate()
    {
        return ((payoutVO.getCertainPeriodEndDate() != null) ? new EDITDate(payoutVO.getCertainPeriodEndDate()) : null);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getExclusionAmount()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getExclusionAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getExclusionRatio()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getExclusionRatio());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFinalDistributionAmount()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getFinalDistributionAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getFinalPaymentDate()
    {
        return ((payoutVO.getFinalPaymentDate() != null) ? new EDITDate(payoutVO.getFinalPaymentDate()) : null);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastCheckDate()
    {
        return ((payoutVO.getLastCheckDate() != null) ? new EDITDate(payoutVO.getLastCheckDate()) : null);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getNextPaymentDate()
    {
        return ((payoutVO.getNextPaymentDate() != null) ? new EDITDate(payoutVO.getNextPaymentDate()) : null);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPaymentAmount()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getPaymentAmount());
    }

    /**
     * Getter.
     * @return
     */
    public String getPaymentFrequencyCT()
    {
        return payoutVO.getPaymentFrequencyCT();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getPaymentStartDate()
    {
        return ((payoutVO.getPaymentStartDate() != null) ? new EDITDate(payoutVO.getPaymentStartDate()) : null);
    }

    /**
     * Getter.
     * @return
     */
    public Long getPayoutPK()
    {
        return SessionHelper.getPKValue(payoutVO.getPayoutPK());
    }

    /**
     * Getter.
     * @return
     */
    public String getPostJune1986Investment()
    {
        return payoutVO.getPostJune1986Investment();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getReducePercent1()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getReducePercent1());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getReducePercent2()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getReducePercent2());
    }

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalExpectedReturnAmount()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getTotalExpectedReturnAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYearlyTaxableBenefit()
    {
        return SessionHelper.getEDITBigDecimal(payoutVO.getYearlyTaxableBenefit());
    }

    /**
     * Setter.
     * @param certainDuration
     */
    public void setCertainDuration(int certainDuration)
    {
        this.payoutVO.setCertainDuration(certainDuration);
    }

    /**
     * Setter.
     * @param certainPeriodEndDate
     */
    public void setCertainPeriodEndDate(EDITDate certainPeriodEndDate)
    {
        this.payoutVO.setCertainPeriodEndDate((certainPeriodEndDate != null) ? certainPeriodEndDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     * @param exclusionAmount
     */
    public void setExclusionAmount(EDITBigDecimal exclusionAmount)
    {
        this.payoutVO.setExclusionAmount(SessionHelper.getEDITBigDecimal(exclusionAmount));
    }

    /**
     * Setter.
     * @param exclusionRatio
     */
    public void setExclusionRatio(EDITBigDecimal exclusionRatio)
    {
        this.payoutVO.setExclusionRatio(SessionHelper.getEDITBigDecimal(exclusionRatio));
    }

    /**
     * Setter.
     * @param finalDistributionAmount
     */
    public void setFinalDistributionAmount(EDITBigDecimal finalDistributionAmount)
    {
        this.payoutVO.setFinalDistributionAmount(SessionHelper.getEDITBigDecimal(finalDistributionAmount));
    }

    /**
     * Setter.
     * @param finalPaymentDate
     */
    public void setFinalPaymentDate(EDITDate finalPaymentDate)
    {
        this.payoutVO.setFinalPaymentDate((finalPaymentDate != null) ? finalPaymentDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     * @param lastCheckDate
     */
    public void setLastCheckDate(EDITDate lastCheckDate)
    {
        this.payoutVO.setLastCheckDate((lastCheckDate != null) ? lastCheckDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     * @param nextPaymentDate
     */
    public void setNextPaymentDate(EDITDate nextPaymentDate)
    {
        this.payoutVO.setNextPaymentDate((nextPaymentDate != null) ? nextPaymentDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     * @param paymentAmount
     */
    public void setPaymentAmount(EDITBigDecimal paymentAmount)
    {
        this.payoutVO.setPaymentAmount(SessionHelper.getEDITBigDecimal(paymentAmount));
    }

    /**
     * Setter.
     * @param paymentFrequencyCT
     */
    public void setPaymentFrequencyCT(String paymentFrequencyCT)
    {
        this.payoutVO.setPaymentFrequencyCT(paymentFrequencyCT);
    }

    /**
     * Setter.
     * @param paymentStartDate
     */
    public void setPaymentStartDate(EDITDate paymentStartDate)
    {
        this.payoutVO.setPaymentStartDate((paymentStartDate != null) ? paymentStartDate.getFormattedDate() : null);
    }

    /**
     * Setter.
     * @param payoutPK
     */
    public void setPayoutPK(Long payoutPK)
    {
        this.payoutVO.setPayoutPK(SessionHelper.getPKValue(payoutPK));
    }

    /**
     * Setter.
     * @param postJune1986Investment
     */
    public void setPostJune1986Investment(String postJune1986Investment)
    {
        this.payoutVO.setPostJune1986Investment(postJune1986Investment);
    }

    /**
     * Setter.
     * @param reducePercent1
     */
    public void setReducePercent1(EDITBigDecimal reducePercent1)
    {
        this.payoutVO.setReducePercent1(SessionHelper.getEDITBigDecimal(reducePercent1));
    }

    /**
     * Setter.
     * @param reducePercent2
     */
    public void setReducePercent2(EDITBigDecimal reducePercent2)
    {
        this.payoutVO.setReducePercent2(SessionHelper.getEDITBigDecimal(reducePercent2));
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
     * Setter.
     * @param totalExpectedReturnAmount
     */
    public void setTotalExpectedReturnAmount(EDITBigDecimal totalExpectedReturnAmount)
    {
        this.payoutVO.setTotalExpectedReturnAmount(SessionHelper.getEDITBigDecimal(totalExpectedReturnAmount));
    }

    /**
     * Setter.
     * @param yearlyTaxableBenefit
     */
    public void setYearlyTaxableBenefit(EDITBigDecimal yearlyTaxableBenefit)
    {
        this.payoutVO.setYearlyTaxableBenefit(SessionHelper.getEDITBigDecimal(yearlyTaxableBenefit));
    }

	/**
     * Getter.
     * @return
     */
    public String getLastDayOfMonthInd()
    {
        return payoutVO.getLastDayOfMonthInd();
    }

	/**
     * Setter.
     * @param lastDayOfMonthInd
     */
    public void setLastDayOfMonthInd(String lastDayOfMonthInd)
    {
        this.payoutVO.setLastDayOfMonthInd(lastDayOfMonthInd);
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        try
        {
            //CONVERT TO HIBERNATE - 09/13/07
            hSave();
        }
        catch (Throwable t)
        {
            System.out.println(t);

            t.printStackTrace();
            throw new RuntimeException(t);
        }
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
        return payoutVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return payoutVO.getPayoutPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.payoutVO = (PayoutVO) voObject;
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

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Payout.DATABASE;
    }

    /**
     * Finder.
     *
     * @param payoutPK
     */
    public static final Payout findByPK(long payoutPK)
    {
        Payout payout = null;

        PayoutVO[] payoutVOs = new PayoutDAO().findByPK(payoutPK);

        if (payoutVOs != null)
        {
            payout = new Payout(payoutVOs[0]);
        }

        return payout;
    }

    /**
     * Finder.
     * @param segment
     * @return
     */
    public static final Payout findBy_Segment(Segment segment)
    {
        Payout payout = null;

        String hql = "select po from Payout po where po.Segment = :segment";

        Map params = new HashMap();

        params.put("segment", segment);

        List results = SessionHelper.executeHQL(hql, params, Payout.DATABASE);

        if (!results.isEmpty())
        {
            payout = (Payout) results.get(0);
        }

        return payout;
    }

    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(payoutVO.getSegmentFK());
    }
     //-- long getSegmentFK() 

    public void setSegmentFK(Long segmentFK)
    {
        payoutVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Payout.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Payout.DATABASE);
    }
}
