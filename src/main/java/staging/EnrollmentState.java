/*
 * User: dlataill
 * Date: Sep 12, 2010
 * Time: 9:20:46 AM
 *
 * (c) 2000-2010 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package staging;

import edit.services.db.hibernate.*;

public class EnrollmentState extends HibernateEntity
{
    private Long enrollmentStatePK;
    private Long enrollmentFK;
    private String state;

    //  Parents
    private Enrollment enrollment;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.STAGING;


    /**
     * Constructor
     */
    public EnrollmentState()
    {
        init();
    }

    private void init()
    {

    }

    public Long getEnrollmentStatePK()
    {
        return enrollmentStatePK;
    }

    public void setEnrollmentStatePK(Long enrollmentStatePK)
    {
        this.enrollmentStatePK = enrollmentStatePK;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
    }

    public Long getEnrollmentFK()
    {
        return this.enrollmentFK;
    }

    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }


    public String getDatabase()
    {
        return EnrollmentState.DATABASE;
    }
}
