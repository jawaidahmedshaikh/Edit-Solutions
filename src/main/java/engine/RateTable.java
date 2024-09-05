/*
 * User: cgleason
 * Date: Feb 7, 2007
 * Time: 3:47:13 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.services.db.hibernate.*;
import edit.common.*;


public class RateTable extends HibernateEntity
{
    private Long tableKeysFK;
    private Long rateTablePK;
    private int age;
    private int duration;
    private EDITBigDecimal rate;

    private TableKeys tableKey;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;


    public RateTable()
    {
    }

    public Long getTableKeysFK()
    {
        return tableKeysFK;
    }

    public void setTableKeysFK(Long tableKeysFK)
    {
        this.tableKeysFK = tableKeysFK;
    }

    public Long getRateTablePK()
    {
        return rateTablePK;
    }

    public void setRateTablePK(Long rateTablePK)
    {
        this.rateTablePK = rateTablePK;
    }


    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }


    public EDITBigDecimal getRate()
    {
        return rate;
    }

    public void setRate(EDITBigDecimal rate)
    {
        this.rate = rate;
    }

   /**
     * Getter
     * @return
     */
    public TableKeys getTableKeys()
    {
        return tableKey;
    }

    /**
     * setter
     */
    public void setTableKeys(TableKeys tableKey)
    {
        this.tableKey = tableKey;
    }




   /**
     * @see edit.services.db.hibernate.HibernateEntity#hSave()
     */
    public void hSave()
    {
    }

   /**
     * @see edit.services.db.hibernate.HibernateEntity#hDelete()
     */
    public void hDelete()
    {
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return RateTable.DATABASE;
    }


    /**
     * Finder by PK.
     * @param rateTablePK
     * @return
     */
    public static final RateTable findByPK(Long scriptPK)
    {
        return (RateTable) SessionHelper.get(RateTable.class, scriptPK, RateTable.DATABASE);
    }
}

