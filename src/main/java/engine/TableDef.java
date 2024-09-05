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


public class TableDef extends HibernateEntity
{
    private Long tableDefPK;
    private String tableName;
    private String accessType;
    private EDITDateTime lockDateTime;
    private EDITDateTime maintDateTime;
    private String operator;
    private TableKeys tableKey;

    private Set tableKeys = new HashSet();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;
    

    public TableDef()
    {
        this.maintDateTime = new EDITDateTime();
        this.lockDateTime = new EDITDateTime();
    }

    public Long getTableDefPK()
    {
        return tableDefPK;
    }

    public void setTableDefPK(Long tableDefPK)
    {
        this.tableDefPK = tableDefPK;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    public EDITDateTime getLockDateTime()
    {
        return lockDateTime;
    }

    public void setLockDateTime(EDITDateTime lockDateTime)
    {
        this.lockDateTime = lockDateTime;
    }

    public String getAccessType()
    {
        return accessType;
    }

    public void setAccessType(String accessType)
    {
        this.accessType = accessType;
    }


    /**
     * setter
     */
    public void setTableKey(TableKeys tableKey)
    {
        this.tableKey = tableKey;
    }

   /**
     * Getter.
     * @return
     */
    public Set getTableKeys()
    {
        return tableKeys;
    }

    /**
     * Setter.
     * @param tableKeys
     */
    public void setTableKeys(Set tableKeys)
    {
        this.tableKeys = tableKeys;
    }

    /**
     * Adder.
     * @param contributingProduct
     */
    public void add(TableKeys tableKey)
    {
        getTableKeys().add(tableKey);

        tableKey.setTableDef(this);
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
        return TableDef.DATABASE;
    }

    /**
     * Finder by PK.
     * @param scriptPK
     * @return
     */
    public static final TableDef findByPK(Long scriptPK)
    {
        return (TableDef) SessionHelper.get(TableDef.class, scriptPK, TableDef.DATABASE);
    }
}

