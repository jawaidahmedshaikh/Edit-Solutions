/*
 * User: sdorman
 * Date: Dec 16, 2008
 * Time: 1:24:25 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package staging;

import edit.common.*;

/**
 * Contains values that are calculated at time of issue.  These calculated values are good for the life of the contract.
 *
 * NOTE:  At this time, this class is only valid for riders, not base segments.  But that may change in the future.
 */
public class ValueAtIssue
{
    private Long valueAtIssuePK;

    private EDITDate effectiveDate;
    private EDITBigDecimal guaranteedPremium;
    private EDITBigDecimal increaseAmount;

    private Long segmentRiderFK;

    private SegmentRider segmentRider;


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

    public Long getSegmentRiderFK()
    {
        return segmentRiderFK;
    }

    public void setSegmentRiderFK(Long segmentRiderFK)
    {
        this.segmentRiderFK = segmentRiderFK;
    }

    public SegmentRider getSegmentRider()
    {
        return segmentRider;
    }

    public void setSegmentRider(SegmentRider segmentRider)
    {
        this.segmentRider = segmentRider;
    }
}