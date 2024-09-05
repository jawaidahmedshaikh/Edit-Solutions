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
import edit.common.exceptions.EDITCaseException;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import event.EDITTrx;
import group.PayrollDeductionSchedule;
import staging.IStaging;
import staging.StagingContext;

/**
 * Tracks the year end values calculated for UL policies
 *
 */
public class YearEndValues extends HibernateEntity
{
    /**
     * The PK.
     */
    private Long yearEndValuesPK;

    /**
     * The associated Segment for which history is tracked.  (parent)
     */
    private Long segmentFK;
    private Segment segment;

    private EDITDate yearEndDate;

    private String policyAdminStatus;
    private String paymentMethod;
    private String paymentMode;
    private String deductionFrequency;
    private EDITBigDecimal ulFaceAmount;
    private int termRiderCount;
    private EDITBigDecimal termRiderFace;
    private EDITBigDecimal annualPremium;
    private String residentState;
    private EDITBigDecimal accumulatedValue;
    private EDITBigDecimal interimInterest;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    /**
     * Creates a new instance of YearEndValues
     */
    public YearEndValues()
    {
        init();
    }

    /**
     * Initialize necessary objects
     */
    private void init(){}

    /**
     * @return Long
     *
     * @see #yearEndValuesPK
     */
    public Long getYearEndValuesPK()
    {
        return yearEndValuesPK;
    }

    /**
     * @param yearEndValuesPK
     *
     * @see #yearEndValuesPK
     */
    public void setYearEndValuesPK(Long yearEndValuesPK)
    {
        this.yearEndValuesPK = yearEndValuesPK;
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
     * @return EDITBigDecimal
     *
     * @see #termRiderFace
     */
    public EDITBigDecimal getTermRiderFace()
    {
        return termRiderFace;
    }

    /**
     * @param termRiderFace
     *
     * @see #termRiderFace
     */
    public void setTermRiderFace(EDITBigDecimal termRiderFace)
    {
        this.termRiderFace = termRiderFace;
    }

    /**
     * @return int
     *
     * @see #termRiderCount
     */
    public int getTermRiderCount()
    {
        return termRiderCount;
    }

    /**
     * @param termRiderCount
     *
     * @see #termRiderCount
     */
    public void setTermRiderCount(int termRiderCount)
    {
        this.termRiderCount = termRiderCount;
    }

    public EDITDate getYearEndDate()
    {
        return this.yearEndDate;
    }

    public void setYearEndDate(EDITDate yearEndDate)
    {
        this.yearEndDate = yearEndDate;
    }

    public void setPolicyAdminStatus(String policyAdminStatus)
    {
        this.policyAdminStatus = policyAdminStatus;
    }
    
    public String getPolicyAdminStatus()
    {
        return policyAdminStatus;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMode(String paymentMode)
    {
        this.paymentMode = paymentMode;
    }
    
    public String getPaymentMode()
    {
        return paymentMode;
    }

    public void setDeductionFrequency(String deductionFrequency)
    {
        this.deductionFrequency = deductionFrequency;
    }
    
    public String getDeductionFrequency()
    {
        return deductionFrequency;
    }

    public void setResidentState(String residentState)
    {
        this.residentState = residentState;
    }
    
    public String getResidentState()
    {
        return residentState;
    }

    /**
     * @return EDITBigDecimal
     *
     * @see #annualPremium
     */
    public EDITBigDecimal getAnnualPremium()
    {
        return annualPremium;
    }

    /**
     * @param modalPremium
     *
     * @see #annualPremium
     */
    public void setAnnualPremium(EDITBigDecimal annualPremium)
    {
        this.annualPremium = annualPremium;
    }

    /**
     * @return EDITBigDecimal
     *
     * @see #accumulatedValue
     */
    public EDITBigDecimal getAccumulatedValue()
    {
        return accumulatedValue;
    }

    /**
     * @param accumulatedValue
     *
     * @see #accumulatedValue
     */
    public void setAccumulatedValue(EDITBigDecimal accumulatedValue)
    {
        this.accumulatedValue = accumulatedValue;
    }

    public EDITBigDecimal getInterimInterest()
    {
        return interimInterest;
    }

    /**
     * Setter.
     * @param interimInterest
     */
    public void setInterimInterest(EDITBigDecimal interimInterest)
    {
        this.interimInterest = interimInterest;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getULFaceAmount()
    {
        return ulFaceAmount;
    }

    /**
     * Setter.
     * @param ulFaceAmount
     */
    public void setULFaceAmount(EDITBigDecimal ulFaceAmount)
    {
        this.ulFaceAmount = ulFaceAmount;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return YearEndValues.DATABASE;
    }


    /**
     * Finds the YearEndValues with the latest yearEndDate for a given segmentPK.  Used by the billing pages
     * @param segmentPK
     * @param maxYearEndDate
     * 				- the date the YearEndValues.yearEndDate cannot be later than
     * @return single YearEndValues
     */
    public static YearEndValues findBySegmentPK_maxYearEndDate(Long segmentPK)
    {
        YearEndValues yearEndValues = null;

        String hql = null;

        EDITMap params = new EDITMap();

         hql = " select yearEndValues from YearEndValues yearEndValues" +
               " where yearEndValues.SegmentFK = :segmentPK" +
               " and yearEndValues.YearEndDate = " +
               "	(select max(yearEndValues2.YearEndDate) " +
               "	from YearEndValues yearEndValues2, Segment segment" +
               "	where segment.SegmentPK = yearEndValues2.SegmentFK" +
               " 	and yearEndValues2.SegmentFK = :segmentPK) " +
               " order by yearEndValues.YearEndValuesPK desc";

         params.put("segmentPK", segmentPK);

        List<YearEndValues> results = SessionHelper.executeHQL(hql, params, YearEndValues.DATABASE);

        if (!results.isEmpty())
        {
            yearEndValues = (YearEndValues) results.get(0);
        }

        return yearEndValues;
    }

    /**
     * Finds all YearEndValuess for supplied SegmentPK
     * @param segmentPK
     * @return YearEndValues Array
     */
    public static YearEndValues[] findBySegmentPK(Long segmentPK)
    {
        String hql = " select yearEndValues from YearEndValues yearEndValues" +
                     " where yearEndValues.SegmentFK = :segmentPK";

        EDITMap params = new EDITMap();

        params.put("segmentPK", segmentPK);

        List<YearEndValues> results = SessionHelper.executeHQL(hql, params, YearEndValues.DATABASE);

        return results.toArray(new YearEndValues[results.size()]);
    }
    
    /**
     * Finds all YearEndValuess for supplied SegmentPK, sorted by PK
     * @param segmentPK
     * @return YearEndValues Array
     */
    public static YearEndValues[] findBySegmentPK_OrderByPK(Long segmentPK)
    {
        String hql = " select yearEndValues from YearEndValues yearEndValues" +
                     " where yearEndValues.SegmentFK = :segmentPK" +
                     " order by yearEndValues.YearEndValuesPK desc";

        EDITMap params = new EDITMap();

        params.put("segmentPK", segmentPK);

        List<YearEndValues> results = SessionHelper.executeHQL(hql, params, YearEndValues.DATABASE);

        return results.toArray(new YearEndValues[results.size()]);
    }

    public static YearEndValues findByPK(Long yearEndValuesPK)
    {
        YearEndValues yearEndValues = null;

        String hql = " select yearEndValues from YearEndValues yearEndValues" +
                     " where yearEndValues.YearEndValuesPK = :yearEndValuesPK";

        EDITMap params = new EDITMap();
        params.put("yearEndValuesPK", yearEndValuesPK);

        List results = SessionHelper.executeHQL(hql, params, YearEndValues.DATABASE);

        if (!results.isEmpty())
        {
            yearEndValues = (YearEndValues) results.get(0);
        }

        return yearEndValues;
    }
    
	public void save() throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.saveOrUpdate(this, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, YearEndValues.DATABASE);
    }
 
}
