/*
 * User: gfrosti
 * Date: Jan 9, 2004
 * Time: 10:54:06 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;


public class SecurityLog extends HibernateEntity
{
    private Long securityLogPK;
    private String operatorName;
    private EDITDateTime maintDateTime;
    private String message;
    private String type;

    public static final String DATABASE = SessionHelper.SECURITY;


    public SecurityLog()
    {
    }

    /**
     * Returns the value of field 'maintDateTime'.
     *
     * @return the value of field 'maintDateTime'.
     */
    public EDITDateTime getMaintDateTime()
    {
        return this.maintDateTime;
    }

    /**
     * Returns the value of field 'message'.
     *
     * @return the value of field 'message'.
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Returns the value of field 'operatorName'.
     *
     * @return the value of field 'operatorName'.
     */
    public String getOperatorName()
    {
        return this.operatorName;
    }

    /**
     * Returns the value of field 'securityLogPK'.
     *
     * @return the value of field 'securityLogPK'.
     */
    public Long getSecurityLogPK()
    {
        return this.securityLogPK;
    }

    /**
     * Returns the value of field 'type'.
     *
     * @return the value of field 'type'.
     */
    public String getType()
    {
        return this.type;
    }

    /**
     * Sets the value of field 'maintDateTime'.
     *
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    /**
     * Sets the value of field 'message'.
     *
     * @param message the value of field 'message'.
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Sets the value of field 'operatorName'.
     *
     * @param operatorName the value of field 'operatorName'.
     */
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    /**
     * Sets the value of field 'securityLogPK'.
     *
     * @param securityLogPK the value of field 'securityLogPK'.
     */
    public void setSecurityLogPK(Long securityLogPK)
    {
        this.securityLogPK = securityLogPK;
    }

    /**
     * Sets the value of field 'type'.
     *
     * @param type the value of field 'type'.
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, SecurityLog.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, SecurityLog.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return SecurityLog.DATABASE;
    }

    public static SecurityLog[] findAll()
    {
        SecurityLog[] securityLogs = null;

        String hql = "select securityLog from SecurityLog securityLog";

        Map params = new HashMap();

        List results = SessionHelper.executeHQL(hql, params, SecurityLog.DATABASE);

        if (! results.isEmpty())
        {
            securityLogs = (SecurityLog[]) results.toArray(new SecurityLog[results.size()]);
        }

        return securityLogs;
    }
}

