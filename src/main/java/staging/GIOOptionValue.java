/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package staging;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.HibernateEntity;

/**
 *
 * @author sprasad
 */
public class GIOOptionValue extends HibernateEntity
{
    private Long gioOptionValuePK;
    private Long segmentBaseFK;
    private EDITDate optionDate;
    private EDITBigDecimal dbIncreaseAmount;
    private EDITBigDecimal dbIncreaseAmountGuaranteed;
    private EDITBigDecimal annualPremium;

    private SegmentBase segmentBase;

    public EDITBigDecimal getAnnualPremium()
    {
        return annualPremium;
    }

    public void setAnnualPremium(EDITBigDecimal annualPremium)
    {
        this.annualPremium = annualPremium;
    }

    public EDITBigDecimal getDBIncreaseAmount()
    {
        return dbIncreaseAmount;
    }

    public void setDBIncreaseAmount(EDITBigDecimal dbIncreaseAmount)
    {
        this.dbIncreaseAmount = dbIncreaseAmount;
    }

    public EDITBigDecimal getDBIncreaseAmountGuaranteed()
    {
        return dbIncreaseAmountGuaranteed;
    }

    public void setDBIncreaseAmountGuaranteed(EDITBigDecimal dbIncreaseAmountGuaranteed)
    {
        this.dbIncreaseAmountGuaranteed = dbIncreaseAmountGuaranteed;
    }

    public Long getGIOOptionValuePK()
    {
        return gioOptionValuePK;
    }

    public void setGIOOptionValuePK(Long gioOptionValuePK)
    {
        this.gioOptionValuePK = gioOptionValuePK;
    }

    public EDITDate getOptionDate()
    {
        return optionDate;
    }

    public void setOptionDate(EDITDate optionDate)
    {
        this.optionDate = optionDate;
    }

    public Long getSegmentBaseFK()
    {
        return segmentBaseFK;
    }

    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
    }

    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }
}
