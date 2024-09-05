/*
 * User: gfrosti
 * Date: May 18, 2007
 * Time: 1:46:32 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.plexus.util.StringUtils;
import org.hibernate.Session;

import billing.BillSchedule;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import event.EDITTrx;
import group.PayrollDeductionSchedule;
import staging.IStaging;
import staging.StagingContext;

/**
 * Tracks the financial changes of the associated Billing entity over time as
 * the associated EDITTrxs are processed.
 *
 * @author gfrosti
 */
public class PremiumDue extends HibernateEntity implements IStaging
{
    /**
     * The PK.
     */
    private Long premiumDuePK;

    /**
     * The associated Segment for which history is tracked.  (parent)
     */
    private Long segmentFK;
    private Segment segment;

    /**
     * The transaction for which the history is tracked.
     */
    private Long editTrxFK;
    private EDITTrx editTrx;

    /**
     * Bill amount.
     */
    private EDITBigDecimal billAmount;

    /**
     * Deduction amount.
     */
    private EDITBigDecimal deductionAmount;

    /**
     * @todo define
     */
    private int numberOfDeductions;

    /**
     * @todo define
     */
    private EDITDate effectiveDate;

    /**
     * @todo define
     */
    private String pendingExtractIndicator;

    private EDITBigDecimal priorBillAmount;

    private EDITBigDecimal priorDeductionAmount;

    private EDITBigDecimal adjustmentAmount;

    private EDITBigDecimal deductionAmountOverride;

    private EDITBigDecimal billAmountOverride;

    //  Children
    private Set<CommissionPhase> commissionPhases;


    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    public static final String PENDING_EXTRACT_R = "R";
    public static final String PENDING_EXTRACT_D = "D";
    public static final String PENDING_EXTRACT_P = "P";
    public static final String PENDING_EXTRACT_E = "E";
    public static final String PENDING_EXTRACT_A = "A";
    public static final String PENDING_EXTRACT_M = "M";
    public static final String PENDING_EXTRACT_T = "T";
    public static final String PENDING_EXTRACT_U = "U";
    public static final String PENDING_EXTRACT_B = "B";


    /**
     * Creates a new instance of PremiumDue
     */
    public PremiumDue()
    {
        init();
    }

    /**
     * Initialize necessary objects
     */
    private void init()
    {
        if (commissionPhases == null)
        {
            commissionPhases = new HashSet();
        }
    }

    /**
     * @return Long
     *
     * @see #premiumDuePK
     */
    public Long getPremiumDuePK()
    {
        return premiumDuePK;
    }

    /**
     * @param premiumDuePK
     *
     * @see #premiumDuePK
     */
    public void setPremiumDuePK(Long premiumDuePK)
    {
        this.premiumDuePK = premiumDuePK;
    }

    /**
     * @return Long
     *
     * @see #segmentFK
     */
    public Long getSegmentFK()
    {
        return segmentFK;
    }

    /**
     * @param segmentFK
     *
     * @see #segmentFK
     */
    public void setSegmentFK(Long segmentFK)
    {
        this.segmentFK = segmentFK;
    }

    public Segment getSegment()
    {
        return this.segment;
    }

    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
     * @return Long
     *
     * @see #editTrxPK
     */
    public Long getEDITTrxFK()
    {
        return editTrxFK;
    }

    /**
     * @param editTrxFK
     *
     * @see #editTrxFK
     */
    public void setEDITTrxFK(Long editTrxFK)
    {
        this.editTrxFK = editTrxFK;
    }

    /**
     * @return EDITBigDecimal
     *
     * @see #billAmount
     */
    public EDITBigDecimal getBillAmount()
    {
        return billAmount;
    }

    /**
     * @param billAmount
     *
     * @see #billAmount
     */
    public void setBillAmount(EDITBigDecimal billAmount)
    {
        this.billAmount = billAmount;
    }

    /**
     * @return EDITBigDecimal
     *
     * @see #deductionAmount
     */
    public EDITBigDecimal getDeductionAmount()
    {
        return deductionAmount;
    }

    /**
     * @param deductionAmount
     *
     * @see #deductionAmount
     */
    public void setDeductionAmount(EDITBigDecimal deductionAmount)
    {
        this.deductionAmount = deductionAmount;
    }

    /**
     * @return int
     *
     * @see #numberOfDeductions
     */
    public int getNumberOfDeductions()
    {
        return numberOfDeductions;
    }

    /**
     * @param numberOfDeductions
     *
     * @see #numberOfDeductions
     */
    public void setNumberOfDeductions(int numberOfDeductions)
    {
        this.numberOfDeductions = numberOfDeductions;
    }

    public EDITDate getEffectiveDate()
    {
        return this.effectiveDate;
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public String getPendingExtractIndicator()
    {
        return pendingExtractIndicator;
    }

    public void setPendingExtractIndicator(String pendingExtractIndicator)
    {
        this.pendingExtractIndicator = pendingExtractIndicator;
    }

    /**
     * @return EDITBigDecimal
     *
     * @see #priorBillAmount
     */
    public EDITBigDecimal getPriorBillAmount()
    {
        return priorBillAmount;
    }

    /**
     * @param priorBillAmount
     *
     * @see #priorBillAmount
     */
    public void setPriorBillAmount(EDITBigDecimal priorBillAmount)
    {
        this.priorBillAmount = priorBillAmount;
    }

    /**
     * @return EDITBigDecimal
     *
     * @see #priorDeductionAmount
     */
    public EDITBigDecimal getPriorDeductionAmount()
    {
        return priorDeductionAmount;
    }

    /**
     * @param priorDeductionAmount
     *
     * @see #priorDeductionAmount
     */
    public void setPriorDeductionAmount(EDITBigDecimal priorDeductionAmount)
    {
        this.priorDeductionAmount = priorDeductionAmount;
    }

    public EDITBigDecimal getAdjustmentAmount()
    {
        return adjustmentAmount;
    }

    /**
     * Setter.
     * @param adjustmentAmount
     */
    public void setAdjustmentAmount(EDITBigDecimal adjustmentAmount)
    {
        this.adjustmentAmount = adjustmentAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBillAmountOverride()
    {
        return billAmountOverride;
    }

    /**
     * Setter.
     * @param billAmountOverride
     */
    public void setBillAmountOverride(EDITBigDecimal billAmountOverride)
    {
        this.billAmountOverride = billAmountOverride;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDeductionAmountOverride()
    {
        return deductionAmountOverride;
    }

    /**
     * Setter.
     * @param deductionAmountOverride
     */
    public void setDeductionAmountOverride(EDITBigDecimal deductionAmountOverride)
    {
        this.deductionAmountOverride = deductionAmountOverride;
    }


    public Set<CommissionPhase> getCommissionPhases()
    {
        return this.commissionPhases;
    }

    public void setCommissionPhases(Set<CommissionPhase> commissionPhases)
    {
        this.commissionPhases = commissionPhases;
    }

    public void addCommissionPhase(CommissionPhase commissionPhase)
    {
        this.commissionPhases.add(commissionPhase);
    }

    public void removeCommissonPhase(CommissionPhase commissionPhase)
    {
        this.commissionPhases.remove(commissionPhase);
    }

    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return PremiumDue.DATABASE;
    }

    /**
     * Finds the PremiumDue with the latest effectiveDate for a given Segment
     * @param segment
     * @return single PremiumDue
     */
    public static PremiumDue findBySegment_LatestEffectiveDate(Segment segment)
    {
        PremiumDue premiumDue = null;

        String hql = null;

        EDITMap params = new EDITMap();

        hql = " select premiumDue from PremiumDue premiumDue" +
              " where premiumDue.Segment = :segment" +
              " and premiumDue.PendingExtractIndicator <> :pendingExtractIndicator" +
              " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
              " where premiumDue2.Segment = :segment" +
              " and premiumDue2.PendingExtractIndicator <> :pendingExtractIndicator)";

        params.put("segment", segment);
        params.put("pendingExtractIndicator", "R");

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        if (!results.isEmpty())
        {
            premiumDue = (PremiumDue) results.get(0);
        }

        return premiumDue;
    }

    /**
     * Finds the PremiumDue with the latest effectiveDate for a given segmentPK.  Used by the billing pages
     * @param segmentPK
     * @return single PremiumDue
     */
    public static PremiumDue findBySegmentPK_LatestEffectiveDate(Long segmentPK)
    {
        PremiumDue premiumDue = null;
        EDITDate systemDate = new EDITDate();

        String hql = null;

        EDITMap params = new EDITMap();


         hql = " select premiumDue from PremiumDue premiumDue" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and premiumDue.PendingExtractIndicator not in ('R')" +
               " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
               " where premiumDue2.SegmentFK = :segmentPK" +
               " and premiumDue2.PendingExtractIndicator not in ('R')" +
               " and premiumDue2.EffectiveDate <= :systemDate)" +
               " order by premiumDue.PremiumDuePK desc";

         params.put("segmentPK", segmentPK);
         params.put("systemDate", systemDate);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        if (!results.isEmpty())
        {
            premiumDue = (PremiumDue) results.get(0);

        }

        return premiumDue;
    }

    /**
     * Finds the PremiumDue with the latest effectiveDate for a given segmentPK.  Used by the billing pages
     * @param segmentPK
     * @param maxEffectiveDate
     * 				- the date the PremiumDue.effectiveDate cannot be later than
     * @return single PremiumDue
     */
    public static PremiumDue findBySegmentPK_maxEffectiveDate(Long segmentPK, EDITDate maxEffectiveDate)
    {
        PremiumDue premiumDue = null;

        String hql = null;

        EDITMap params = new EDITMap();

         hql = " select premiumDue from PremiumDue premiumDue" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and premiumDue.PendingExtractIndicator not in ('R')" +
               " and premiumDue.EffectiveDate = " +
               "	(select max(premiumDue2.EffectiveDate) " +
               "	from PremiumDue premiumDue2, Segment segment" +
               "	where segment.SegmentPK = premiumDue2.SegmentFK" +
               " 	and premiumDue2.SegmentFK = :segmentPK" +
               " 	and premiumDue2.PendingExtractIndicator not in ('R')" +
               " 	and (premiumDue2.EffectiveDate <= :maxEffectiveDate	or premiumDue2.EffectiveDate <= segment.EffectiveDate))" +
               " order by premiumDue.PremiumDuePK desc";

         params.put("segmentPK", segmentPK);
         params.put("maxEffectiveDate", maxEffectiveDate);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        if (!results.isEmpty())
        {
            premiumDue = (PremiumDue) results.get(0);
        }

        return premiumDue;
    }

    /**
     * Finds the PremiumDue with the latest effectiveDate for a given segmentPK.  Used by the billing pages
     * @param segmentPK
     * @return single PremiumDue
     */
    public static PremiumDue findBySegmentPK_NextBillDueDate(Long segmentPK, EDITDate nextBillDueDate)
    {
        PremiumDue premiumDue = null;

        String hql = null;

        EDITMap params = new EDITMap();

         hql = " select premiumDue from PremiumDue premiumDue" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and premiumDue.PendingExtractIndicator not in ('R')" +
               " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
               " where premiumDue2.SegmentFK = :segmentPK" +
               " and premiumDue2.PendingExtractIndicator not in ('R')" +
               " and premiumDue2.EffectiveDate <= :nextBillDueDate)" +
               " order by premiumDue.PremiumDuePK desc";

         params.put("segmentPK", segmentPK);
         params.put("nextBillDueDate", nextBillDueDate);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        if (!results.isEmpty())
        {
            premiumDue = (PremiumDue) results.get(0);

        }

        return premiumDue;
    }

    /**
     * Finds the PremiumDue with the latest effectiveDate for a given segmentPK.  Used by Reversal process.
     * @param segmentPK
     * @return single PremiumDue
     */
    public static PremiumDue findBySegmentPK_PremiumDueToReset(Long segmentPK, List<Long> premiumDuePKs, EDITDate trxDate)
    {
        PremiumDue premiumDue = null;

        String hql = null;

        EDITMap params = new EDITMap();

        hql = " select premiumDue from PremiumDue premiumDue" +
              " where premiumDue.SegmentFK = :segmentPK" +
              " and premiumDue.PremiumDuePK not in (:premiumDuePKs)" +
              " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
              " where premiumDue2.SegmentFK = :segmentPK" +
              " and premiumDue2.PremiumDuePK not in (:premiumDuePKs)" +
              " and premiumDue2.EffectiveDate <= :trxDate)" +
              " order by premiumDue.PremiumDuePK desc";

        params.put("segmentPK", segmentPK);
        params.put("premiumDuePKs", premiumDuePKs);
        params.put("trxDate", trxDate);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        if (!results.isEmpty())
        {
            premiumDue = (PremiumDue) results.get(0);
        }

        return premiumDue;
    }

    /**
     * Finds all PremiumDues for supplied SegmentPK
     * @param segmentPK
     * @return PremiumDue Array
     */
    public static PremiumDue[] findBySegmentPK(Long segmentPK)
    {
        String hql = " select premiumDue from PremiumDue premiumDue" +
                     " join fetch premiumDue.CommissionPhases" +
                     " where premiumDue.SegmentFK = :segmentPK";

        EDITMap params = new EDITMap();

        params.put("segmentPK", segmentPK);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        return results.toArray(new PremiumDue[results.size()]);
    }
    
    /**
     * Finds all PremiumDues for supplied SegmentPK, sorted by PK
     * @param segmentPK
     * @return PremiumDue Array
     */
    public static PremiumDue[] findBySegmentPK_OrderByPK(Long segmentPK)
    {
        String hql = " select premiumDue from PremiumDue premiumDue" +
                     " join fetch premiumDue.CommissionPhases" +
                     " where premiumDue.SegmentFK = :segmentPK" +
                     " order by premiumDue.PremiumDuePK";

        EDITMap params = new EDITMap();

        params.put("segmentPK", segmentPK);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        return results.toArray(new PremiumDue[results.size()]);
    }

    /**
     * Find all PremiumDues associated with the specified SegmentFK
     * @param segmentFK
     * @return list PremiumDue
     */
    public static List<PremiumDue> findAllBySegmentFK(Long segmentFK)
    {
        String hql = null;

        EDITMap params = new EDITMap();

        hql = " select premiumDue from PremiumDue premiumDue" +
              " where premiumDue.SegmentFK = :segmentFK";

        params.put("segmentFK", segmentFK);

        return SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);
    }
    
    /**
     * Find all PremiumDues associated with the specified EDITTrx
     * @param editTrxFK
     * @return list PremiumDue
     */
    public static List<PremiumDue> findAllByEDITTrxFK(Long editTrxFK)
    {
        String hql = null;

        EDITMap params = new EDITMap();

        hql = " select premiumDue from PremiumDue premiumDue" +
              " where premiumDue.EDITTrxFK = :editTrxFK";

        params.put("editTrxFK", editTrxFK);

        return SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);
    }
    

    /**
     * Finds the PremiumDue associated with the specified EDITTrx
     * @param editTrxFK
     * @return single PremiumDue
     */
    public static PremiumDue findByEDITTrxFK(Long editTrxFK)
    {
        PremiumDue premiumDue = null;

        String hql = null;

        EDITMap params = new EDITMap();

        hql = " select premiumDue from PremiumDue premiumDue" +
              " where premiumDue.EDITTrxFK = :editTrxFK";

        params.put("editTrxFK", editTrxFK);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        if (!results.isEmpty())
        {
            premiumDue = (PremiumDue) results.get(0);
        }

        return premiumDue;
    }

    /**
     * Finds premium Due to be used for the PayrollDeductionSchedule processing
     * @param segment
     * @param pds
     * @return
     */
    public static PremiumDue[] getPremiumDueForExtract(Segment segment, PayrollDeductionSchedule pds)
    {
        String hql = null;
        String reportType = pds.getReportTypeCT();

        EDITMap params = new EDITMap();

        EDITDate currentDateThru = pds.getCurrentDateThru();

        if (reportType != null && reportType.equalsIgnoreCase(PayrollDeductionSchedule.REPORT_CHANGES_ONLY))
        {
            hql = " select premiumDue from PremiumDue premiumDue" +
                  " where premiumDue.Segment = :segment" +
                  " and premiumDue.PendingExtractIndicator in ('P', 'U', 'B')" +
                  " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
                  " where premiumDue2.Segment = :segment" +
//                  " and premiumDue2.PendingExtractIndicator in ('P', 'U', 'B')" +
                  " and premiumDue2.EffectiveDate <= :currentDateThru)" +
                  " order by premiumDue.PremiumDuePK desc";

            params.put("segment", segment);
            params.put("currentDateThru", currentDateThru);
        }
        else
        {
            EDITDate nextExtractDate = pds.getNextPRDExtractDate();

            hql = " select premiumDue from PremiumDue premiumDue" +
                   " where premiumDue.Segment = :segment" +
                   " and premiumDue.PendingExtractIndicator in ('A', 'D', 'P', 'M', 'U', 'B', 'T')" +
                   " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
                   " where premiumDue2.Segment = :segment" +
                   " and premiumDue2.PendingExtractIndicator != 'R' " +
//                   " and premiumDue2.PendingExtractIndicator in ('A', 'D', 'P', 'M', 'U', 'B')" +
                   " and (premiumDue2.EffectiveDate <= :nextExtractDate" +
                   " or (premiumDue2.EffectiveDate > :nextExtractDate" +
                   " and premiumDue2.EffectiveDate <= :currentDateThru)))" +
                   " order by premiumDue.PremiumDuePK desc";

            params.put("segment", segment);
            params.put("nextExtractDate", nextExtractDate);
            params.put("currentDateThru", currentDateThru);
        }

        List results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        return (PremiumDue[]) results.toArray(new PremiumDue[results.size()]);
    }

    public static PremiumDue[] getPremiumDueForPRDStaging(Segment segment, PayrollDeductionSchedule pds)
    {
        String hql = null;

        EDITMap params = new EDITMap();

        EDITDate lastExtractDate = pds.getLastPRDExtractDate();

        hql = " select premiumDue from PremiumDue premiumDue" +
              " where premiumDue.Segment = :segment" +
              " and premiumDue.PendingExtractIndicator <> :pendingExtractIndicator" +
              " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
              " where premiumDue2.Segment = :segment" +
              " and premiumDue2.EffectiveDate <= :lastExtractDate" +
              " and premiumDue2.PendingExtractIndicator <> :pendingExtractIndicator)";

        params.put("segment", segment);
        params.put("lastExtractDate", lastExtractDate);
        params.put("pendingExtractIndicator", "R");

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        return (PremiumDue[]) results.toArray(new PremiumDue[results.size()]);
    }

    public static PremiumDue[] getPremiumDueForBill(Segment segment, EDITDate extractDate)
    {
        String hql = " select premiumDue from PremiumDue premiumDue" +
                     " where premiumDue.Segment = :segment" +
                     " and premiumDue.PendingExtractIndicator <> :pendingExtractIndicator" +
                     " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
                     " where premiumDue2.Segment = :segment" +
                     " and premiumDue2.EffectiveDate <= :extractDate" +
                     " and premiumDue2.PendingExtractIndicator <> :pendingExtractIndicator)" +
                     " order by premiumDue.PremiumDuePK desc";

        EDITMap params = new EDITMap();

        params.put("segment", segment);
        params.put("extractDate", extractDate);
        params.put("pendingExtractIndicator", "R");

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        return (PremiumDue[]) results.toArray(new PremiumDue[results.size()]);
    }

    public static PremiumDue[] getPremiumDueForBillStaging(Segment segment, BillSchedule billSched)
    {
        EDITDate lastExtractDate = billSched.getLastBillDueDate();

        String hql = " select premiumDue from PremiumDue premiumDue" +
                  " where premiumDue.Segment = :segment" +
                  " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
                  " where premiumDue2.Segment = :segment" +
                  " and premiumDue2.EffectiveDate <= :extractDate" +
                  " and premiumDue2.PendingExtractIndicator <> :pendingExtractIndicator) " +
                  " order by premiumDue.PremiumDuePK desc";

        EDITMap params = new EDITMap();

        params.put("segment", segment);
        params.put("extractDate", lastExtractDate);
        params.put("pendingExtractIndicator", "R");

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        return (PremiumDue[]) results.toArray(new PremiumDue[results.size()]);
    }

    public static PremiumDue[] getPremiumDueForDataWarehouse(Segment segment, EDITDate datawarehouseDate)
    {
        String hql = "select premiumDue from PremiumDue premiumDue" +
                  " where premiumDue.Segment = :segment" +
                  " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
                  " where premiumDue2.Segment = :segment" +
                  " and premiumDue2.EffectiveDate <= :datawarehouseDate)";

        EDITMap params = new EDITMap();

        params.put("segment", segment);
        params.put("datawarehouseDate", datawarehouseDate);

        List<PremiumDue> results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        return (PremiumDue[]) results.toArray(new PremiumDue[results.size()]);
    }


    /**
     * Same query used in GetcommissionPhasedetail instruction.
     * Get the active PremiumDue with CommissionPhases.
     * @param segmentPK
     * @param paidToDate
     * @return
     */
    public static PremiumDue[] findActiveSeparateBySegmentPK(Long segmentPK, EDITDate paidToDate)
    {
        PremiumDue[] premiumDues = null;

        String hql = " select premiumDue from PremiumDue premiumDue" +
               " left join fetch premiumDue.CommissionPhases commissionPhases" +
               " left join fetch commissionPhases.CommissionablePremiumHistories" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and premiumDue.PendingExtractIndicator not in ('R')" +
               " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
               " left join premiumDue2.CommissionPhases commissionPhases2" +
               " left join commissionPhases2.CommissionablePremiumHistories" +
               " where premiumDue2.SegmentFK = :segmentPK" +
               " and premiumDue2.PendingExtractIndicator not in ('R')" +
               " and premiumDue2.EffectiveDate <= :paidToDate)" +
               " order by premiumDue.PremiumDuePK desc";


        Map params = new HashMap();
        params.put("segmentPK", segmentPK);
        params.put("paidToDate", paidToDate);

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                List premiumDueList = new ArrayList();
                premiumDueList.add(results.get(0));
                premiumDues = (PremiumDue[])premiumDueList.toArray(new PremiumDue[1]);
            }
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return premiumDues;

    }

    /**
      * Same query used in GetcommissionPhasedetail instruction.
      * Get any future dated premium due with CommissionPhases
      * @param segmentPK
      * @param paidToDate
      * @return
      */
    public static PremiumDue[] findFutureSeparateBySegmentPK(Long segmentPK, EDITDate paidToDate)
     {
         PremiumDue[] premiumDues = null;

         String hql = " select premiumDue from PremiumDue premiumDue" +
                 " left join fetch premiumDue.CommissionPhases commissionPhases" +
                 " left join fetch commissionPhases.CommissionablePremiumHistories" +
                 " where premiumDue.SegmentFK = :segmentPK" +
                 " and premiumDue.PendingExtractIndicator not in ('R')" +
                 " and premiumDue.EffectiveDate > :paidToDate";

          Map params = new HashMap();
          params.put("segmentPK", segmentPK);
          params.put("paidToDate", paidToDate);

         Session session = null;

         try
         {
             session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

             List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

             if (!results.isEmpty())
             {
                 premiumDues = (PremiumDue[])results.toArray(new PremiumDue[results.size()]);
             }
         }
         finally
         {
             if (session != null)
             {
                 session.close();
             }
         }

         return premiumDues;

     }

    public static PremiumDue findByPK(Long premiumDuePK)
    {
        PremiumDue premiumDue = null;

        String hql = " select premiumDue from PremiumDue premiumDue" +
                     " where premiumDue.PremiumDuePK = :premiumDuePK";

        EDITMap params = new EDITMap();
        params.put("premiumDuePK", premiumDuePK);

        List results = SessionHelper.executeHQL(hql, params, PremiumDue.DATABASE);

        if (!results.isEmpty())
        {
            premiumDue = (PremiumDue) results.get(0);
        }

        return premiumDue;
    }
    
    /**
     * Utility method for determining the scheduled premium amount
     * 
     * @param billTypeCT
     * 				- used to determine the amount to return
     * @return String - represents the scheduledPremiumAmount
     */
    public String getScheduledPremiumAmount(String billTypeCT)
    {
        String scheduledPremiumAmount = "";
    	if (!StringUtils.isEmpty(billTypeCT))
    	{
    		if (billTypeCT.equalsIgnoreCase("GRP")) 
    		{
    			scheduledPremiumAmount = (this.deductionAmount == null ? "" : this.deductionAmount.toString());
    		} 
    		else if (billTypeCT.equalsIgnoreCase("INDIV"))
    		{
    			scheduledPremiumAmount = (this.billAmount == null ? "" : this.billAmount.toString());
    		}
    	}
    	return scheduledPremiumAmount;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.PremiumDue stagingPremiumDue = new staging.PremiumDue();
        stagingPremiumDue.setSegmentBase(stagingContext.getCurrentSegmentBase());
        stagingPremiumDue.setBillAmount(this.billAmount);
        stagingPremiumDue.setDeductionAmount(this.deductionAmount);
        stagingPremiumDue.setNumberOfDeductions(this.numberOfDeductions);
        stagingPremiumDue.setEffectiveDate(this.effectiveDate);
        stagingPremiumDue.setPendingExtractIndicator(this.pendingExtractIndicator);
        stagingPremiumDue.setPriorDeductionAmount(this.priorDeductionAmount);
        stagingPremiumDue.setPriorBillAmount(this.priorBillAmount);
        stagingPremiumDue.setBillAmountOverride(this.getBillAmountOverride());
        stagingPremiumDue.setDeductionAmountOverride(this.getDeductionAmountOverride());

        stagingContext.getCurrentSegmentBase().addPremiumDue(stagingPremiumDue);

        stagingContext.setCurrentPremiumDue(stagingPremiumDue);

        SessionHelper.saveOrUpdate(stagingPremiumDue, database);

        return stagingContext;
    }
}
