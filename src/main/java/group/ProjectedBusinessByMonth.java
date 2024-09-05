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
import staging.StagingContext;
import staging.IStaging;

public class ProjectedBusinessByMonth extends HibernateEntity implements IStaging
{
    /**
     * Primary key
     */
    private Long projectedBusinessByMonthPK;

    private EDITDate date;
    private EDITBigDecimal percentExpected;
    private int numberOfEligibles;
    private String enrollmentStatusCT;
    private EDITDate closedDate;
    private EDITBigDecimal closedUnpaidPercent;

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
    public ProjectedBusinessByMonth()
    {
        // Set defaults
        closedDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }

    public Long getProjectedBusinessByMonthPK()
    {
        return projectedBusinessByMonthPK;
    }

    public void setProjectedBusinessByMonthPK(Long projectedBusinessByMonthPK)
    {
        this.projectedBusinessByMonthPK = projectedBusinessByMonthPK;
    }

    public EDITDate getDate()
    {
        return date;
    }

    public void setDate(EDITDate date)
    {
        this.date = date;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIDate()
    {
        String date = null;

        if (getDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIDate(String uiDate)
    {
        if (uiDate != null)
        {
            setDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiDate));
        }
    }

    public EDITBigDecimal getPercentExpected()
    {
        return percentExpected;
    }

    public void setPercentExpected(EDITBigDecimal percentExpected)
    {
        this.percentExpected = percentExpected;
    }

    public int getNumberOfEligibles()
    {
        return numberOfEligibles;
    }

    public void setNumberOfEligibles(int numberOfEligibles)
    {
        this.numberOfEligibles = numberOfEligibles;
    }

    /**
     * Convenience (bean) method to support UI needed for number Of Eligibles (two fields with same name on same page)
     * @return
     */
    public int getProjBusNumberOfEligibles()
    {
        return numberOfEligibles;
    }

    /**
     * Convenience method to support UI needed for number of Eligibles (two fields with same name on same page)
     */
    public void setProjBusNumberOfEligibles(int numberOfEligibles)
    {
        setNumberOfEligibles(numberOfEligibles);
    }

    public String getEnrollmentStatusCT()
    {
        return enrollmentStatusCT;
    }

    public void setEnrollmentStatusCT(String enrollmentStatusCT)
    {
        this.enrollmentStatusCT = enrollmentStatusCT;
    }

    public EDITDate getClosedDate()
    {
        return closedDate;
    }

    public void setClosedDate(EDITDate closedDate)
    {
        this.closedDate = closedDate;
    }

    public EDITBigDecimal getClosedUnpaidPercent()
    {
        return closedUnpaidPercent;
    }

    public void setClosedUnpaidPercent(EDITBigDecimal closedUnpaidPercent)
    {
        this.closedUnpaidPercent = closedUnpaidPercent;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIClosedDate()
    {
        String date = null;

        if (getClosedDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getClosedDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIClosedDate(String uiClosedDate)
    {
        if (uiClosedDate != null)
        {
            setClosedDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiClosedDate));
        }
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

    public String getDatabase()
    {
        return ProjectedBusinessByMonth.DATABASE;
    }

    /**
     * Finds the ProjectedBusinessByMonth with the given primary key
     *
     * @param projectedBusinessByMonthPK
     *
     * @return
     */
    public static ProjectedBusinessByMonth findByPK(Long projectedBusinessByMonthPK)
    {
        String hql = " select projectedBusinessByMonth from ProjectedBusinessByMonth projectedBusinessByMonth" +
                     " where projectedBusinessByMonth.ProjectedBusinessByMonthPK = :projectedBusinessByMonthPK";

        EDITMap params = new EDITMap();

        params.put("projectedBusinessByMonthPK", projectedBusinessByMonthPK);

        List<ProjectedBusinessByMonth> results = SessionHelper.executeHQL(hql, params, ProjectedBusinessByMonth.DATABASE);

        if (results.size() > 0)
        {
            return (ProjectedBusinessByMonth) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all ProjectedBusinessByMonths
     *
     * @return array of ProjectedBusinessByMonth objects      ;
     */
    public static ProjectedBusinessByMonth[] findAll()
    {
        String hql = " from ProjectedBusinessByMonth projectedBusinessByMonth";

        EDITMap params = new EDITMap();

        List<ProjectedBusinessByMonth> results = SessionHelper.executeHQL(hql, params, ProjectedBusinessByMonth.DATABASE);

        return results.toArray(new ProjectedBusinessByMonth[results.size()]);
    }

    /**
     * Finder.
     * @param enrollment
     * @return
     */
    public static ProjectedBusinessByMonth[] findByEnrollment(Long enrollmentFK)
    {
        String hql = " from ProjectedBusinessByMonth projectedBusinessByMonth" +
                    " where projectedBusinessByMonth.EnrollmentFK = :enrollmentFK";

        EDITMap params = new EDITMap("enrollmentFK", enrollmentFK);

        List<ProjectedBusinessByMonth> results = SessionHelper.executeHQL(hql, params, ProjectedBusinessByMonth.DATABASE);

        return (ProjectedBusinessByMonth[]) results.toArray(new ProjectedBusinessByMonth[results.size()]);
    }

    /**
     * Finder.
     * @param enrollment
     * @return
     */
    public static ProjectedBusinessByMonth[] findByEnrollment_Date(Long enrollmentFK, EDITDate date)
    {
        String hql = " from ProjectedBusinessByMonth projectedBusinessByMonth" +
                     " where projectedBusinessByMonth.EnrollmentFK = :enrollmentFK" +
                     " and projectedBusinessByMonth.Date = :date";

        EDITMap params = new EDITMap();
        params.put("enrollmentFK", enrollmentFK);
        params.put("date", date);

        List<ProjectedBusinessByMonth> results = SessionHelper.executeHQL(hql, params, ProjectedBusinessByMonth.DATABASE);

        if (results.isEmpty())
        {
            return null;
        }

        return (ProjectedBusinessByMonth[]) results.toArray(new ProjectedBusinessByMonth[results.size()]);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.ProjectedBusinessByMonth stagingProjBusByMonth = new staging.ProjectedBusinessByMonth();

        stagingProjBusByMonth.setEnrollment(stagingContext.getCurrentEnrollment());
        stagingProjBusByMonth.setDate(this.getDate());
        stagingProjBusByMonth.setPercentExpected(this.percentExpected);
        stagingProjBusByMonth.setNumberOfEligibles(this.numberOfEligibles);
        stagingProjBusByMonth.setEnrollmentStatus(this.enrollmentStatusCT);
        stagingProjBusByMonth.setClosedDate(this.closedDate);
        stagingProjBusByMonth.setClosedUnpaidPercent(this.closedUnpaidPercent);

        stagingContext.getCurrentEnrollment().addProjectedBusinessByMonth(stagingProjBusByMonth);

        SessionHelper.saveOrUpdate(stagingProjBusByMonth, database);

        return stagingContext;
    }
}
