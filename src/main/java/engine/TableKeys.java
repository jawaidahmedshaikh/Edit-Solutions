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

import java.util.*;


public class TableKeys extends HibernateEntity
{
    private Long tableKeysPK;
    private Long tableDefFK;
    private EDITDate effectiveDate;
    private EDITBigDecimal bandAmount;
    private String userKey;
    private String gender;
    private String classType;
    private String state;
    private String tableType;
    private TableKeys tableKey;
    private RateTable rateTable;
    private TableDef tableDef;

    private Set rateTables = new HashSet();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;
    

    public TableKeys()
    {
    }

    public Long getTableKeysPK()
    {
        return tableKeysPK;
    }

    public void setTableKeysPK(Long tableKeysPK)
    {
        this.tableKeysPK = tableKeysPK;
    }

    public Long getTableDefFK()
    {
        return tableDefFK;
    }

    public void setTableDefFK(Long tableDefFK)
    {
        this.tableDefFK = tableDefFK;
    }

    public String getUserKey()
    {
        return userKey;
    }

    public void setUserKey(String userKey)
    {
        this.userKey = userKey;
    }


    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }


    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getClassType()
    {
        return classType;
    }

    public void setClassType(String classType)
    {
        this.classType = classType;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getTableType()
    {
        return tableType;
    }

    public void setTableType(String tableType)
    {
        this.tableType = tableType;
    }


    public EDITBigDecimal getBandAmount()
    {
        return bandAmount;
    }

    public void setBandAmount(EDITBigDecimal bandAmount)
    {
        this.bandAmount = bandAmount;
    }

   /**
     * Getter
     * @return
     */
    public TableDef getTableDef()
    {
        return tableDef;
    }

    /**
     * setter
     */
    public void setTableDef(TableDef tableDef)
    {
        this.tableDef = tableDef;
    }


   /**
     * Getter.
     * @return
     */
    public Set getRateTables()
    {
        return rateTables;
    }

    /**
     * Setter.
     * @param
     */
    public void setRateTables(Set rateTables)
    {
        this.rateTables = rateTables;
    }

    /**
     * Adder.
     * @param contributingProduct
     */
    public void add(RateTable rateTable)
    {
        getRateTables().add(rateTable);

        rateTable.setTableKeys(this);
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
        return TableKeys.DATABASE;
    }

    /**
     * Finder by PK.
     * @param scriptPK
     * @return
     */
    public static final TableKeys findByPK(Long scriptPK)
    {
        return (TableKeys) SessionHelper.get(TableKeys.class, scriptPK, TableKeys.DATABASE);
    }
}

