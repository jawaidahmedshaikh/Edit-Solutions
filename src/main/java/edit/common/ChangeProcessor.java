/*
 * User: unknown
 * Date: Sep 24, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package edit.common;

import contract.Segment;
import edit.common.vo.*;
import edit.services.db.*;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * The Contract History  processor
 */
public class ChangeProcessor
{

    public ChangeProcessor()
    {

    }

    /**
     * This method compares the VO passed in to the current data on the data base of the VO entity.
     * If any field is changed, changeVOs are generated.
     */
    public Change[] checkForChanges(Object targetVO, boolean VOShouldBeDeleted, String poolName, String effectiveDate)
    {
        CRUD crud = CRUDFactory.getSingleton().getCRUD(poolName);

        Change[] changes = null;

        try
        {
            long targetVOPK = crud.getPKValue(targetVO);

            if (targetVOPK == 0)
            {
                changes = addToChangeHistory(null, targetVO, effectiveDate);
            }
            else if (VOShouldBeDeleted)
            {
                changes = addToChangeHistory(targetVO, null, effectiveDate);
            }
            else
            {
                changes = addToChangeHistory(crud.retrieveVOFromDB(targetVO.getClass(), targetVOPK), targetVO, effectiveDate);
            }
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            return changes;
        }
    }

    /**
     * Set status based on inputs and the rest of the Change data recording specific before and after
     * data fields and vos.
     *
     * @param beforeVO
     * @param afterVO
     */
    private Change[] addToChangeHistory(Object beforeVO, Object afterVO, String effectiveDate)
    {
        List changes = new ArrayList();

        int status = 0;

        if (beforeVO == null && afterVO != null) // Added
        {
            status = Change.ADDED;
        }
        else if (beforeVO != null && afterVO == null) // Deleted
        {
            status = Change.DELETED;
        }
        else if (beforeVO != null && afterVO != null) // Changed
        {
            status = Change.CHANGED;
        }

        String tableName = (beforeVO != null) ? VOClass.getTableName(beforeVO.getClass()) : VOClass.getTableName(afterVO.getClass());
        DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);
        DBTable dbTable = dbDatabase.getDBTable(tableName);
        DBColumn[] dbColumns = dbTable.getDBColumns();

        try
        {
            for (int i = 0; i < dbColumns.length; i++)
            {
                String fieldName = dbColumns[i].getColumnName();
                Class fieldType = null;

                Object beforeValAsObj = null;
                Object afterValAsObj = null;

                String beforeValAsStr = null;
                String afterValAsStr = null;

                if ((status == Change.ADDED) || (status == Change.CHANGED))
                {
                    fieldType = afterVO.getClass().getMethod("get" + fieldName, null).getReturnType();

                    afterValAsObj = afterVO.getClass().getMethod("get" + fieldName, null).invoke(afterVO, null);
                    afterValAsStr = (afterValAsObj != null) ? afterValAsObj.toString() : null;
                }

                if ((status == Change.DELETED) || (status == Change.CHANGED))
                {
                    fieldType = beforeVO.getClass().getMethod("get" + fieldName, null).getReturnType();

                    beforeValAsObj = beforeVO.getClass().getMethod("get" + fieldName, null).invoke(beforeVO, null);
                    beforeValAsStr = (beforeValAsObj != null) ? beforeValAsObj.toString() : null;
                }

                Change change = new Change();

                change.setFieldName(fieldName);
                change.setFieldType(fieldType);
                change.setBeforeValue(beforeValAsStr);
                change.setAfterValue(afterValAsStr);
                change.setBeforeVO(beforeVO);
                change.setAfterVO(afterVO);
                change.setStatus(status);
                if (effectiveDate != null)
                {
                    change.setEffectiveDate(effectiveDate);
                }

                //todo - common rtn for deletes and adds vs changes, eliminate multiple records on add and delete
                if (status == Change.CHANGED) // Something needs to have changed to add this entry
                {
                    if (beforeValAsObj instanceof BigDecimal)
                    {
                        if (beforeValAsObj != null && afterValAsObj != null)
                        {
                            BigDecimal before = (BigDecimal)beforeValAsObj;
                            BigDecimal after  = (BigDecimal)afterValAsObj;
                            int result = before.compareTo(after);
                            if (result != 0)
                            {
                                changes.add(change);
                            }
                        }
                    }
                    else if ((change.getBeforeValue() == null) && (change.getAfterValue() != null && !change.getAfterValue().equals("0.00")) ||
                            (change.getBeforeValue() != null && !change.getBeforeValue().equals("0.00")) && (change.getAfterValue() == null))
                    {
                        changes.add(change);
                    }

                    else if ((change.getBeforeValue() != null) && (change.getAfterValue() != null))
                    {
                        if (!change.getBeforeValue().equalsIgnoreCase(change.getAfterValue()))
                        {
                            changes.add(change);
                        }
                    }
                }
                else // Adds add Deletes are always added for every field value
                {
                    changes.add(change);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return (Change[]) changes.toArray(new Change[changes.size()]);
    }

    /**
     * Process changes for between the compared tables
     *
     * @param change
     * @param crudEntity
     * @param operator
     */
    public ChangeHistory processForChanges(Change change, CRUDEntity crudEntity, String operator, long parentKey)
    {
        String fieldName = change.getFieldName();

        ChangeHistory changeHistory = null;

        boolean validChangeFound = checkForValidChange(change);

        if ((!fieldName.equalsIgnoreCase("MaintDateTime") &&
                !fieldName.equalsIgnoreCase("Operator"))  &&
                validChangeFound)
        {
            changeHistory = new ChangeHistory(change, crudEntity, operator, parentKey);
            changeHistory.createHistoryRecordForChange();
        }

        return changeHistory;
    }

    public void processForChanges(Change change, CRUDEntity crudEntity, String operator, String roleType, long parentKey)
    {
        String fieldName = change.getFieldName();

        boolean validChangeFound = checkForValidChange(change);

        if ((!fieldName.equalsIgnoreCase("MaintDateTime") &&
                !fieldName.equalsIgnoreCase("Operator"))  &&
                validChangeFound)
        {
            ChangeHistory changeHistory = new ChangeHistory(change, crudEntity, operator, roleType, parentKey);
            changeHistory.createHistoryRecordForChange();
        }
    }

   /**
     * Create changeHistory for the Add event the change fieldname is defined by the
     * property String name.
     *
     * @param change
     * @param crudEntity
     * @param operator
     * @param properties
     */
    public ChangeHistory processForAdd(Change change, CRUDEntity crudEntity, String operator, String property, long parentKey)
    {
        ChangeHistory changeHistory = null;
        String value = getPropertyValue(crudEntity, property);

        if (property.equalsIgnoreCase(change.getFieldName()))
        {
            changeHistory = new ChangeHistory(change, crudEntity, operator, parentKey);
            changeHistory.createHistoryRecordForAdd(property, value);
        }

        return changeHistory;
    }

    /**
     * Create changeHistory for Add with role type
     *
     * @param change
     * @param crudEntity
     * @param operator
     * @param properties
     */
    public void processForAdd(Change change, CRUDEntity crudEntity, String operator, String property, String roleType, long parentKey)
    {
        String value = getPropertyValue(crudEntity, property);

        if (property.equalsIgnoreCase(change.getFieldName()))
        {
            ChangeHistory changeHistory = new ChangeHistory(change, crudEntity, operator, roleType, parentKey);
            changeHistory.createHistoryRecordForAdd(property, value);
        }
    }

    /**
     * Create history for delete activity
     * @param change
     * @param crudEntity
     * @param operator
     * @param property
     */
    public ChangeHistory processForDelete(Change change, CRUDEntity crudEntity, String operator, String property, long parentKey)
    {
        ChangeHistory changeHistory = null;

        String value = getPropertyValue(crudEntity, property);

        if (property.equalsIgnoreCase(change.getFieldName()))
        {
            changeHistory = new ChangeHistory(change, crudEntity, operator, parentKey);
            changeHistory.createHistoryRecordForDelete(property, value);
        }

        return changeHistory;
    }

    /**
     * Create change history for Delete Active with Role Type
     * @param change
     * @param crudEntity
     * @param operator
     * @param property
     * @param roleType
     */
    public void processForDelete(Change change, CRUDEntity crudEntity, String operator, String property, String roleType, long parentKey)
    {
        String value = getPropertyValue(crudEntity, property);

        ChangeHistory changeHistory = new ChangeHistory(change, crudEntity, operator, roleType, parentKey);
        changeHistory.createHistoryRecordForDelete(property, value);
    }

    private String getPropertyValue(CRUDEntity crudEntity, String property)
    {
        String value = null;

        try
        {
            String getterName = "get" + property;

            VOObject voObject = crudEntity.getVO();

            Method getterMethod = voObject.getClass().getMethod(getterName, null);

            Object getterValue = getterMethod.invoke(voObject, null);

            if (getterValue != null)
            {
                value = getterValue.toString();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return value;
    }

    /**
     * Generates Change History record for Segment Status change.
     * @param segmentVO
     * @param operator
     */
    public void generateChangeHistoryForSegmentStatusChange(SegmentVO segmentVO, String operator, String effectiveDate)
    {
        Change[] changes = checkForChanges(segmentVO, false, ConnectionFactory.EDITSOLUTIONS_POOL, effectiveDate);

        for (int i = 0; i < changes.length; i++)
        {
            Change change = changes[i];

            if ("SegmentStatusCT".equals(change.getFieldName()))
            {
                Segment segment = new Segment(segmentVO);

                processForChanges(change, segment, operator, segmentVO.getSegmentPK());
            }
        }
    }

    private boolean checkForValidChange(Change change)
    {
        boolean validChangeFound = true;
        String beforeValue = change.getBeforeValue();
        String afterValue = change.getAfterValue();
        String tableName  = null;
        if (beforeValue != null)
        {
            tableName = change.getBeforeTableName();
        }
        else
        {
            tableName= change.getAfterTableName();
        }

        if (beforeValue == null && afterValue.equals("0"))
        {
            validChangeFound = false;
        }

        if (beforeValue != null && beforeValue.equalsIgnoreCase("please select") && afterValue == null)
        {
            validChangeFound = false;
        }

        if ((beforeValue == null || beforeValue.equals("")) &&
            (afterValue == null || afterValue.equals("")  || afterValue.equals(" ")))
        {
            validChangeFound = false;
        }

         else if ((beforeValue == null) && (afterValue == null))
        {
            validChangeFound = false;
        }

        else if (tableName.equalsIgnoreCase("segment") || tableName.equalsIgnoreCase("contractClient") ||
            tableName.equalsIgnoreCase("clientRole"))
        {
            if (change.getFieldName().endsWith("FK"))
            {
                validChangeFound = false;
            }
        }

        return validChangeFound;
    }
}