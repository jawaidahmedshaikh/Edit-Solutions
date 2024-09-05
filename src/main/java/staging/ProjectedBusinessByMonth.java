/*
 * User: dlataill
 * Date: Oct 9, 2007
 * Time: 10:20:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

public class ProjectedBusinessByMonth extends HibernateEntity
{
    /**
     * Primary key
     */
    private Long projectedBusinessByMonthPK;
    private Long enrollmentFK;
    private EDITDate date;
    private EDITBigDecimal percentExpected;
    private int numberOfEligibles;
    private String enrollmentStatus;
    private EDITDate closedDate;
    private EDITBigDecimal closedUnpaidPercent;

    //  Parents
    private Enrollment enrollment;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.STAGING;


    /**
     * Constructor
     */
    public ProjectedBusinessByMonth()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getProjectedBusinessByMonthPK()
    {
        return projectedBusinessByMonthPK;
    }

    /**
     * Setter.
     * @param projectedBusinessByMonthPK
     */
    public void setProjectedBusinessByMonthPK(Long projectedBusinessByMonthPK)
    {
        this.projectedBusinessByMonthPK = projectedBusinessByMonthPK;
    }

    public Long getEnrollmentFK()
    {
        return enrollmentFK;
    }

    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }

    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getDate()
    {
        return date;
    }

    /**
     * Setter.
     * @param date
     */
    public void setDate(EDITDate date)
    {
        this.date = date;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPercentExpected()
    {
        return percentExpected;
    }

    /**
     * Setter.
     * @param percentExpected
     */
    public void setPercentExpected(EDITBigDecimal percentExpected)
    {
        this.percentExpected = percentExpected;
    }

    /**
     * Getter.
     * @return
     */
    public int getNumberOfEligibles()
    {
        return numberOfEligibles;
    }

    /**
     * Setter.
     * @param numberOfEligibles
     */
    public void setNumberOfEligibles(int numberOfEligibles)
    {
        this.numberOfEligibles = numberOfEligibles;
    }

    /**
     * Getter.
     * @return
     */
    public String getEnrollmentStatus()
    {
        return enrollmentStatus;
    }

    /**
     * Setter.
     * @param enrollmentStatus
     */
    public void setEnrollmentStatus(String enrollmentStatus)
    {
        this.enrollmentStatus = enrollmentStatus;
    }

    public EDITDate getClosedDate()
    {
        return closedDate;
    }

    public void setClosedDate(EDITDate closedDate)
    {
        this.closedDate = closedDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getClosedUnpaidPercent()
    {
        return closedUnpaidPercent;
    }

    /**
     * Setter.
     * @param closedUnpaidPercent
     */
    public void setClosedUnpaidPercent(EDITBigDecimal closedUnpaidPercent)
    {
        this.closedUnpaidPercent = closedUnpaidPercent;
    }

    public String getDatabase()
    {
        return ProjectedBusinessByMonth.DATABASE;
    }
}
