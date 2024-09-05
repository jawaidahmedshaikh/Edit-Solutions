/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 20, 2003
 * Time: 1:28:14 PM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package edit.common;

import edit.services.db.*;


public class Change
{
    private Object beforeVO;
    private Object afterVO;

    private String fieldName;

    private Class fieldType;
    private String beforeValue;
    private String afterValue;
    private String effectiveDate;

    private int status;

    public static final int DELETED = 0;
    public static final int ADDED   = 1;
    public static final int CHANGED = 2;

    public Class getFieldType()
    {
        return this.fieldType;
    }

    public void setFieldType(Class fieldType)
    {
        this.fieldType = fieldType;
    }

    public void setBeforeVO(Object beforeVO)
    {
        this.beforeVO = beforeVO;
    }

    public Object getBeforeVO()
    {
        return this.beforeVO;
    }

    public Object getAfterVO()
    {
        return this.afterVO;
    }

    public void setAfterVO(Object afterVO)
    {
        this.afterVO = afterVO;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldName()
    {
        return this.fieldName;
    }

    public void setBeforeValue(String beforeValue)
    {
        this.beforeValue = beforeValue;
    }

    public String getBeforeValue()
    {
        return this.beforeValue;
    }

    public void setAfterValue(String afterValue)
    {
        this.afterValue = afterValue;
    }

    public String getAfterValue()
    {
        return this.afterValue;
    }

    public String getBeforeTableName()
    {
        return VOClass.getTableName(beforeVO.getClass());
    }

    public String getAfterTableName()
    {
        return VOClass.getTableName(afterVO.getClass());
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getStatus()
    {
        return this.status;
    }
    public String getEffectiveDate()
    {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }
}