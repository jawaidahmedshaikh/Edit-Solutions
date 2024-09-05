/*
 * User: gfrosti
 * Date: Dec 13, 2004
 * Time: 11:30:39 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package businesscalendar;

import businesscalendar.dm.dao.BusinessDayDAO;

import edit.common.*;

import edit.common.vo.BusinessDayVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import org.apache.commons.validator.GenericValidator;


public class BusinessDay implements CRUDEntity, Comparable
{
    private BusinessDayVO businessDayVO;
    private CRUDEntityImpl crudEntityImpl;

    public BusinessDay()
    {
        init();

        setDefaultValues();
    }

    /**
     * Sets default values for the newly instantiated instance.
     */
    private void setDefaultValues()
    {
        businessDayVO.setActiveInd("Y");
    }

    /**
     * Instantiates a BusinessDay entity with a default BizDayVO.
     * @throws IllegalArgumentException if the specified date is invalid.
     */
    public BusinessDay(EDITDate editDate)
    {
        init();

        setDefaultValues();

        this.businessDayVO.setBusinessDate(editDate.getFormattedDate());
    }

    /**
     * Instantiates a BusinessDay entity with a BizDayVO retrieved from persistence.
     *
     * @param businessDayPK
     */
    public BusinessDay(long businessDayPK)
    {
        init();

        crudEntityImpl.load(this, businessDayPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a BusinessDay entity with a supplied BizDayVO.
     *
     * @param businessDayVO
     * @throws IllegalArgumentException if the BusinessDate of the VO is invalid.
     */
    public BusinessDay(BusinessDayVO businessDayVO)
    {
        init();

        this.businessDayVO = businessDayVO;
    }

    /**
     * Temporary method to allow call with EDITDate until BusinessCalendar is converted to use the new EDITDate
     * @return
     */ 
    public EDITDate getBusinessDateNew()
    {
        return new EDITDate(businessDayVO.getBusinessDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getBusinessDate()
    {
        return new EDITDate(businessDayVO.getBusinessDate());
    }

    /**
     * Gets the day of this object's business date.
     * @return
     */
    public int getDay()
    {
        return new EDITDate(businessDayVO.getBusinessDate()).getDay();
    }

    /**
     * Gets the month of this object's business date.
     * @return
     */
    public int getMonth()
    {
        return new EDITDate(businessDayVO.getBusinessDate()).getMonth();
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
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return businessDayVO.getBusinessDayPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.businessDayVO = (BusinessDayVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return businessDayVO;
    }

    /**
     * Gets the year of this object's business date.
     * @return
     */
    public int getYear()
    {
        return new EDITDate(businessDayVO.getBusinessDate()).getYear();
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
     * @see Comparable#compareTo(Object)
     * @param o
     * @return
     */
    public int compareTo(Object o)
    {
        int compareValue = 0;

        if (!(o instanceof BusinessDay))
        {
            throw new RuntimeException("The Specified Object Must Be Of Type [BusinessDay] To Do A Comparison");
        }

        BusinessDay specifiedBusinessDay = (BusinessDay) o;

        EDITDate specifiedBusinessDate = specifiedBusinessDay.getBusinessDate();

        EDITDate thisBusinessDate = getBusinessDate();

        if (thisBusinessDate.before(specifiedBusinessDate))
        {
            compareValue = -1;
        }
        else if (thisBusinessDate.equals(specifiedBusinessDate))
        {
            compareValue = 0;
        }
        else if (thisBusinessDate.after(specifiedBusinessDate))
        {
            compareValue = 1;
        }

        return compareValue;
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    public boolean equals(Object o)
    {
        boolean equals = false;

        if (this == o)
        {
            equals = true;
        }
        else if (!(o instanceof BusinessDay))
        {
            equals = false;
        }
        else
        {
            final BusinessDay specBusinessDay = (BusinessDay) o;

            int specifiedBusinessYear = specBusinessDay.getYear();

            int specifiedBusinessMonth = specBusinessDay.getMonth();

            int specifiedBusinessDay = specBusinessDay.getDay();

            if (specifiedBusinessYear != getYear())
            {
                equals = false;
            }
            else if (specifiedBusinessMonth != getMonth())
            {
                equals = false;
            }
            else if (specifiedBusinessDay != getDay())
            {
                equals = false;
            }
            else
            {
                equals = true;
            }
        }

        return equals;
    }

    /**
     * @see Object#hashCode()
     * @return
     */
    public int hashCode()
    {
        int result;
        result = getYear();
        result = (29 * result) + getMonth();
        result = (29 * result) + getDay();

        return result;
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        EDITDate editDate = new EDITDate(businessDayVO.getBusinessDate());

        checkForValidDate(editDate);

        checkForDuplicates(editDate);

        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (businessDayVO == null)
        {
            businessDayVO = new BusinessDayVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * Finder.
     * @return
     */
    public static final BusinessDay[] findAll()
    {
        return (BusinessDay[]) CRUDEntityImpl.mapVOToEntity(new BusinessDayDAO().findAll(), BusinessDay.class);
    }

    /**
     * Finder.
     * @param businessDateYYYYMMDD
     * @return
     */
    public static final BusinessDay findBy_BusinessDate_ActiveInd(String businessDateYYYYMMDD)
    {
        BusinessDay businessDay = null;

        BusinessDayVO[] businessDayVOs = new BusinessDayDAO().findBy_BusinessDate_ActiveInd(businessDateYYYYMMDD, "Y");

        if (businessDayVOs != null)
        {
            businessDay = new BusinessDay(businessDayVOs[0]);
        }

        return businessDay;
    }

    /**
     * Finds the biggest BusinessDay that is greater than BusinessDay1 but less than BusinessDay2.
     * BusinessDate1 is assumed <= BusinessDate2.
     * @param businessDate1YYYYMMDD
     * @param businessDate2YYYYMMDD
     * @return
     */
    public static final BusinessDay findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(String businessDate1YYYYMMDD, String businessDate2YYYYMMDD)
    {
        BusinessDay businessDay = null;

        BusinessDayVO[] businessDayVOs = new BusinessDayDAO().findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(businessDate1YYYYMMDD, businessDate2YYYYMMDD, "Y");

        if (businessDayVOs != null)
        {
            businessDay = new BusinessDay(businessDayVOs[0]);
        }

        return businessDay;
    }

    /**
     * Finds the biggest BusinessDay that is greater than BusinessDay1 but less than BusinessDay2.
     * BusinessDate1 is assumed <= BusinessDate2.
     * @param businessDate1YYYYMMDD
     * @param businessDate2YYYYMMDD
     * @return
     */
    public static final BusinessDay findBy_BusinessDate1_MIN_GT_BusinessDate2(String businessDate1YYYYMMDD, String businessDate2YYYYMMDD)
    {
        BusinessDay businessDay = null;

        BusinessDayVO[] businessDayVOs = new BusinessDayDAO().findBy_BusinessDate1_MAX_LT_BusinessDate2_ActiveInd(businessDate1YYYYMMDD, businessDate2YYYYMMDD, "Y");

        if (businessDayVOs != null)
        {
            businessDay = new BusinessDay(businessDayVOs[0]);
        }

        return businessDay;
    }

    /**
     * Finds the set of BusinessDays greater than the specified date ordered by BusinessDate Ascending.
     * @param businessDate
     * @param noBusinessDays
     * @return
     */
    public static final BusinessDay[] findBy_MaxRows_BusinessDate_GT(EDITDate businessDate, int noBusinessDays)
    {
        BusinessDay[] businessDays = (BusinessDay[]) CRUDEntityImpl.mapVOToEntity(new BusinessDayDAO().findBy_MaxRows_BusinessDate_GT(businessDate.getFormattedDate(), noBusinessDays), BusinessDay.class);

        return businessDays;
    }

    /**
     * Finds the set of BusinessDays less than the specified date ordered by BusinessDate Descending.
     * @param businessDate
     * @param noBusinessDays
     * @return
     */
    public static BusinessDay[] findBy_MaxRows_BusinessDate_LT_ActiveInd(EDITDate businessDate, int noBusinessDays, String activeInd)
    {

        BusinessDay[] businessDays = (BusinessDay[]) CRUDEntityImpl.mapVOToEntity(new BusinessDayDAO().findBy_MaxRows_BusinessDate_LT_ActiveInd(businessDate.getFormattedDate(), noBusinessDays, activeInd), BusinessDay.class);
        return businessDays;
    }

    /**
     * Finds the set of BusinessDays less than the specified date ordered by BusinessDate Descending.
     * @param businessDate
     * @param noBusinessDays
     * @return
     */
    public static BusinessDay[] findBy_MaxRows_BusinessDate_LT(EDITDate businessDate, int noBusinessDays)
    {
        BusinessDay[] businessDays = (BusinessDay[]) CRUDEntityImpl.mapVOToEntity(new BusinessDayDAO().findBy_MaxRows_BusinessDate_LT(businessDate.getFormattedDate(), noBusinessDays), BusinessDay.class);

        return businessDays;
    }

    /**
     * Finds the set of BusinessDays within the specified range inclusive.
     * @param beginDate
     * @param endDate
     * @return
     */
    public static BusinessDay[] findBy_Range_Inclusive(EDITDate beginDate, EDITDate endDate)
    {
        return (BusinessDay[]) CRUDEntityImpl.mapVOToEntity(new BusinessDayDAO().findBy_Range_Inclusive(beginDate.getFormattedDate(), endDate.getFormattedDate()), BusinessDay.class);
    }

    /**
     * A BusinessDay is a unique day in time and can not have duplicates.
     */
    private void checkForDuplicates(EDITDate date)
    {
        if (isNew())
        {
            BusinessDay businessDay = BusinessDay.findBy_BusinessDate_ActiveInd(date.getFormattedDate());

            if (businessDay != null)
            {
                throw new IllegalArgumentException("Duplicate Business Days Not Allowed");
            }
        }
    }

    /**
     * Verifies that this is a legal date.
     * @param editDate
     */
    private void checkForValidDate(EDITDate editDate)
    {
        boolean validDate = GenericValidator.isDate(editDate.getFormattedDate(), EDITDate.DATE_FORMAT, true);

        if (!validDate)
        {
            throw new IllegalArgumentException("Invalid Date - Business Day Could Not Be Added");
        }
    }

    /**
     * Finds all the BusinessDays within the specified year.
     * @param year
     * @return
     */
    public static final BusinessDay[] findBy_Year(int year)
    {
        EDITDate beginDate = new EDITDate(year + "", EDITDate.DEFAULT_MIN_MONTH, EDITDate.DEFAULT_MIN_DAY);

        EDITDate endDate = new EDITDate(year + "", EDITDate.DEFAULT_MAX_MONTH, EDITDate.DEFAULT_MAX_DAY);

        return findBy_Range_Inclusive(beginDate, endDate);
    }

    /**
     * Marks this instance as active.
     */
    public void setAsActive()
    {
        businessDayVO.setActiveInd("Y");
    }

    /**
     * Marks this instance as inactive.
     */
    public void setAsInactive()
    {
        businessDayVO.setActiveInd("N");
    }
}
