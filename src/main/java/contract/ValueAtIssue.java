/*
 * User: sdorman
 * Date: Dec 16, 2008
 * Time: 1:24:25 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package contract;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;

/**
 * Contains values that are calculated at time of issue.  These calculated values are good for the life of the contract.
 *
 * NOTE:  At this time, this class is only valid for riders, not base segments.  But that may change in the future.
 */
public class ValueAtIssue extends HibernateEntity
{
    private Long valueAtIssuePK;

    private EDITDate effectiveDate;
    private EDITBigDecimal guaranteedPremium;
    private EDITBigDecimal increaseAmount;

    private Long segmentFK;

    private Segment segment;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public ValueAtIssue()
    {

    }

    public Long getValueAtIssuePK()
    {
        return valueAtIssuePK;
    }

    public void setValueAtIssuePK(Long valueAtIssuePK)
    {
        this.valueAtIssuePK = valueAtIssuePK;
    }

    public EDITDate getEffectiveDate()
    {
        return this.effectiveDate;
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public EDITBigDecimal getGuaranteedPremium()
    {
        return this.guaranteedPremium;
    }

    public void setGuaranteedPremium(EDITBigDecimal guaranteedPremium)
    {
        this.guaranteedPremium = guaranteedPremium;
    }

    public EDITBigDecimal getIncreaseAmount()
    {
        return increaseAmount;
    }

    public void setIncreaseAmount(EDITBigDecimal increaseAmount)
    {
        this.increaseAmount = increaseAmount;
    }

    public Long getSegmentFK()
    {
        return segmentFK;
    }

    public void setSegmentFK(Long segmentFK)
    {
        this.segmentFK = segmentFK;
    }

    public Segment getSegment()
    {
        return segment;
    }

    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    public String getDatabase()
    {
        return ValueAtIssue.DATABASE;
    }

   /**
     * Finder by PK.
     * @param valueAtIssuePK
     * @return
     */
    public static final ValueAtIssue findByPK(Long valueAtIssuePK)
    {
        return (ValueAtIssue) SessionHelper.get(Segment.class, valueAtIssuePK, ValueAtIssue.DATABASE);
    }

   /**
     * Finder by PK.
     * @param valueAtIssuePK
     * @return
     */
    public static final ValueAtIssue[] findBySegmentFK(Long segmentFK)
    {
        ValueAtIssue[] valueAtIssues = null;

        String hql = "select valueAtIssue from ValueAtIssue valueAtIssue " +
                     " where valueAtIssue.SegmentFK = :segmentFK";

        Map params = new HashMap();
        params.put("segmentFK", segmentFK);

        List results = SessionHelper.executeHQL(hql, params, ValueAtIssue.DATABASE);

        if (!results.isEmpty())
        {
            valueAtIssues = (ValueAtIssue[]) results.toArray(new ValueAtIssue[results.size()]);
        }

        return valueAtIssues;
    }
}
