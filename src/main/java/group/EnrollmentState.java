/*
 * User: sdorman
 * Date: Jun 19, 2007
 * Time: 12:45:46 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package group;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;

import org.dom4j.*;
import fission.utility.DateTimeUtil;
import staging.IStaging;
import staging.StagingContext;
import staging.Case;
import client.ClientDetail;
import client.ClientAddress;

public class EnrollmentState extends HibernateEntity implements IStaging
{
    /**
     * Primary key
     */
    private Long enrollmentStatePK;

    private String stateCT;

    //  Parents
    private Enrollment enrollment;
    private Long enrollmentFK;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



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

    public String getStateCT()
    {
        return stateCT;
    }

    public void setStateCT(String stateCT)
    {
        this.stateCT = stateCT;
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

    /**
     * Finds the EnrollmentState with the given primary key
     *
     * @param enrollmentPK
     *
     * @return
     */
    public static EnrollmentState findByPK(Long enrollmentStatePK)
    {
        return (EnrollmentState) SessionHelper.get(EnrollmentState.class, enrollmentStatePK, EnrollmentState.DATABASE);

    }

    /**
     * Finds the EnrollmentState for the Enrollment forgein key
     *
     * @param enrollmentFK
     *
     * @return
     */
    public static EnrollmentState[] findByEnrollemntFK(Long enrollmentFK)
    {
        String hql = " select enrollmentState from EnrollmentState enrollmentState" +
                     " where enrollmentState.EnrollmentFK = :enrollmentFK";

        EDITMap params = new EDITMap();

        params.put("enrollmentFK", enrollmentFK);

        List<EnrollmentState> results = SessionHelper.executeHQL(hql, params, EnrollmentState.DATABASE);

        if (results.size() > 0)
        {
            return (EnrollmentState[]) results.toArray(new EnrollmentState[results.size()]);
        }
        else
        {
            return null;
        }
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.EnrollmentState stagingEnrollmentState = new staging.EnrollmentState();

        stagingEnrollmentState.setState(this.stateCT);

        stagingContext.getCurrentEnrollment().addEnrollmentState(stagingEnrollmentState);
        stagingEnrollmentState.setEnrollment(stagingContext.getCurrentEnrollment());

        SessionHelper.saveOrUpdate(stagingEnrollmentState, database);

        return stagingContext;
    }
}
