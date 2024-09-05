/*
 * User: gfrosti
 * Date: Oct 15, 2004
 * Time: 4:45:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.common;

import edit.services.db.*;
import edit.common.vo.*;
import contract.dm.dao.*;


public class ChangeHistory implements CRUDEntity
{
    private CRUDEntityI crudEntityImpl;

    private ChangeHistoryVO changeHistoryVO;

    private Change change;
    private CRUDEntity crudEntityImplForChanges;
    private String operator;
    private String roleType;
    private long parentKey;

    /**
     * Instantiates a ChangeHistory entity with a default ChangeHistoryVO.
     */
    public ChangeHistory()
    {
        init();
    }

    /**
     * Instantiates a ChangeHistory entity with a ChangeHistoryVO retrieved from persistence.
     * @param changeHistoryPK
     */
    public ChangeHistory(long changeHistoryPK)
    {
        init();

        crudEntityImpl.load(this, changeHistoryPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a ChangeHistory entity with a supplied ChangeHistoryVO.
     * @param changeHistoryVO
     */
    public ChangeHistory(ChangeHistoryVO changeHistoryVO)
    {
        init();

        this.changeHistoryVO = changeHistoryVO;
    }

   /**
     * Instantiates a ChangeHistory entity with a supplied Change.
     * @param changeHistoryVO
     */
    public ChangeHistory(Change change, CRUDEntity crudEntity, String operator, long parentKey)
    {
        init();

        this.change = change;
        this.crudEntityImplForChanges = crudEntity;
        this.operator = operator;
        this.parentKey = parentKey;
    }

   /**
     * Instantiates a ChangeHistory entity with a supplied Change.
     * @param changeHistoryVO
     */
    public ChangeHistory(Change change, CRUDEntity crudEntity, String operator, String roleType, long parentKey)
    {
        init();

        this.change = change;
        this.crudEntityImplForChanges = crudEntity;
        this.operator = operator;
        this.roleType = roleType;
        this.parentKey = parentKey;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (changeHistoryVO == null)
        {
            changeHistoryVO = new ChangeHistoryVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return changeHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return changeHistoryVO.getChangeHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.changeHistoryVO = (ChangeHistoryVO) voObject;
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
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Finds the associated set of ChangeHistories by SegmentPK.
     * @param segmentPK
     * @return
     */
    public static ChangeHistory[] findBySegmentPK(long segmentPK)
    {
        ChangeHistoryVO[] changeHistoryVOs = null;

        changeHistoryVOs = DAOFactory.getChangeHistoryDAO().findBySegmentPK(segmentPK);

        return (ChangeHistory[]) CRUDEntityImpl.mapVOToEntity(changeHistoryVOs, ChangeHistory.class);
    }

    /**
     * ChangeHistory setup for Changes, ContractClient will have set the role type
     */
    public void createHistoryRecordForChange()
    {
        String tableName = change.getAfterTableName();
        if (tableName.equalsIgnoreCase("ContractClient"))
        {
            changeHistoryVO.setNonFinancialTypeCT("RoleChange");

            changeHistoryVO.setRoleCT(roleType);
        }
        else if (tableName.equalsIgnoreCase("ClientRole"))
        {
            changeHistoryVO.setNonFinancialTypeCT("RoleChange");
            changeHistoryVO.setRoleCT(roleType);
        }
        else if (tableName.equalsIgnoreCase("Segment"))
        {
            changeHistoryVO.setNonFinancialTypeCT("SegmentChange");
        }
        else if (tableName.equalsIgnoreCase("Investment") ||
                (tableName.equalsIgnoreCase("InvestmentAllocation")))
        {
            changeHistoryVO.setNonFinancialTypeCT("InvestmentChange");
        }
        else if (tableName.equalsIgnoreCase("ClientAddress"))
        {
            changeHistoryVO.setNonFinancialTypeCT("AddressChange");
        }
        else if (tableName.equalsIgnoreCase("AgentLicense"))
        {
            changeHistoryVO.setNonFinancialTypeCT("AgentLicenseChange");
        }
        else if (tableName.equalsIgnoreCase("ClientDetail"))
        {
            changeHistoryVO.setNonFinancialTypeCT("ClientDetailChange");
        }
        String beforeValue = change.getBeforeValue();
        String afterValue = change.getAfterValue();


        saveChangeHistoryVO(tableName, change.getFieldName(), beforeValue, afterValue);
    }

    /**
     * ChangeHistory setup for Add, ContractClient will have set the role type
     */
    public void createHistoryRecordForAdd(String property, String value)
    {
        String tableName = change.getAfterTableName();
        if (tableName.equalsIgnoreCase("ContractClient") || tableName.equalsIgnoreCase("ClientRole"))
        {
            changeHistoryVO.setNonFinancialTypeCT("RoleAdd");

            changeHistoryVO.setRoleCT(roleType);
        }

        else if (tableName.equalsIgnoreCase("Investment"))
        {
            changeHistoryVO.setNonFinancialTypeCT("InvestmentAdd");
        }
        else if (tableName.equalsIgnoreCase("ClientAddress"))
        {
            changeHistoryVO.setNonFinancialTypeCT("AddressAdd");
        }
        else if (tableName.equalsIgnoreCase("AgentLicense"))
        {
            changeHistoryVO.setNonFinancialTypeCT("AgentLicenseAdd");
        }

        String beforeValue = null;
        String afterValue = value;

        saveChangeHistoryVO(tableName, property, beforeValue, afterValue);
    }

    /**
     * ChangeHistory setup for Deletes, ContractClient will have set the role type
     *
     */
    public void createHistoryRecordForDelete(String property, String value)
    {
        String tableName = change.getBeforeTableName();
        if (tableName.equalsIgnoreCase("ContractClient"))
        {
            changeHistoryVO.setNonFinancialTypeCT("RoleDelete");

            changeHistoryVO.setRoleCT(roleType);
        }

        else if (tableName.equalsIgnoreCase("Investment"))
        {
            changeHistoryVO.setNonFinancialTypeCT("InvestmentDelete");
        }
        else if (tableName.equalsIgnoreCase("ClientAddress"))
        {
            changeHistoryVO.setNonFinancialTypeCT("AddressDelete");
        }
        else if (tableName.equalsIgnoreCase("AgentLicense"))
        {
            changeHistoryVO.setNonFinancialTypeCT("AgentLicenseDelete");
        }
        String beforeValue = value;
        String afterValue = null;

        saveChangeHistoryVO(tableName, property, beforeValue, afterValue);
    }

    /**
     * If the change entity contains an effectiveDate use it else use process date for the effective date.
     * The ChangeHistoryVO is created with the before and after value for speicif tables.  The table name,
     * field name, record key, and parent key make up this record. 
     * @param tableName
     * @param property
     * @param beforeValue
     * @param afterValue
     */
    private void saveChangeHistoryVO(String tableName, String property, String beforeValue, String afterValue)
    {
        EDITDate processDate = new EDITDate();
        String effectiveDate = change.getEffectiveDate();

        if (effectiveDate == null || effectiveDate.equals(""))
        {
            effectiveDate = processDate.getFormattedDate();
        }

        changeHistoryVO.setTableName(tableName);
        changeHistoryVO.setFieldName(property);
        changeHistoryVO.setEffectiveDate(effectiveDate);
        changeHistoryVO.setProcessDate(processDate.getFormattedDate());
        changeHistoryVO.setBeforeValue(beforeValue);
        changeHistoryVO.setAfterValue(afterValue);
        changeHistoryVO.setOperator(operator);
        changeHistoryVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        changeHistoryVO.setChangeHistoryPK(0);
        changeHistoryVO.setModifiedRecordFK(crudEntityImplForChanges.getPK());
        changeHistoryVO.setParentFK(parentKey);

        save();
    }

    /**
     * Look up for change histories affting the contract
     * @param segmentVO
     * @return
     */
    public ChangeHistoryVO[] findAllChangeHistoryForSegment(SegmentVO segmentVO)
    {
        ChangeHistoryVO[] changeHistoryVOs = DAOFactory.getChangeHistoryDAO().findByModifiedKey(segmentVO.getSegmentPK());

        return changeHistoryVOs;
    }
}
