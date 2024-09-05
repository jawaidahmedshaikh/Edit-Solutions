/*
 * User: gfrosti
 * Date: Oct 15, 2004
 * Time: 4:45:16 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package contract;

import edit.services.db.*;
import edit.services.db.hibernate.*;
import edit.common.vo.*;
import edit.common.*;
import contract.dm.dao.*;

import java.util.*;

import org.hibernate.Session;


public class ChangeHistory extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityI crudEntityImpl;

    private ChangeHistoryVO changeHistoryVO;
    
    public static final String PENDING_STATUS_PENDING = "P";
    
    public static final String PENDING_STATUS_HISTORY = "H";

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


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
     * Getter.
     * @return
     */
    public Long getChangeHistoryPK()
    {
        return SessionHelper.getPKValue(changeHistoryVO.getChangeHistoryPK());
    }

    /**
     * Setter.
     * @param changeHistoryPK
     */
    public void setChangeHistoryPK(Long changeHistoryPK)
    {
        this.changeHistoryVO.setChangeHistoryPK(SessionHelper.getPKValue(changeHistoryPK));
    }

    public String getAfterValue()
    {
        return changeHistoryVO.getAfterValue();
    } //-- java.lang.String getAfterValue()

    public String getBeforeValue()
    {
        return changeHistoryVO.getBeforeValue();
    } //-- java.lang.String getBeforeValue()

    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(changeHistoryVO.getEffectiveDate());
    } //-- java.lang.String getEffectiveDate()

    public String getFieldName()
    {
        return changeHistoryVO.getFieldName();
    } //-- java.lang.String getFieldName()

    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(changeHistoryVO.getMaintDateTime());
    } //-- java.lang.String getMaintDateTime()

    public Long getModifiedRecordFK()
    {
        return SessionHelper.getPKValue(changeHistoryVO.getModifiedRecordFK());
    } //-- long getModifiedRecordFK()

    public String getNonFinancialTypeCT()
    {
        return changeHistoryVO.getNonFinancialTypeCT();
    } //-- java.lang.String getNonFinancialTypeCT()

    public String getOperator()
    {
        return changeHistoryVO.getOperator();
    } //-- java.lang.String getOperator()

    public Long getParentFK()
    {
        return SessionHelper.getPKValue(changeHistoryVO.getParentFK());
    } //-- long getParentFK()

    public EDITDate getProcessDate()
    {
        return SessionHelper.getEDITDate(changeHistoryVO.getProcessDate());
    } //-- java.lang.String getProcessDate()

    public String getRoleCT()
    {
        return changeHistoryVO.getRoleCT();
    } //-- java.lang.String getRoleCT()

    public String getTableName()
    {
        return changeHistoryVO.getTableName();
    } //-- java.lang.String getTableName()

    public void setAfterValue(String afterValue)
    {
        changeHistoryVO.setAfterValue(afterValue);
    } //-- void setAfterValue(java.lang.String)

    public void setBeforeValue(String beforeValue)
    {
        changeHistoryVO.setBeforeValue(beforeValue);
    } //-- void setBeforeValue(java.lang.String)

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        changeHistoryVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    } //-- void setEffectiveDate(java.lang.String)

    public void setFieldName(String fieldName)
    {
        changeHistoryVO.setFieldName(fieldName);
    } //-- void setFieldName(java.lang.String)

    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        changeHistoryVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    } //-- void setMaintDateTime(java.lang.String)

    public void setModifiedRecordFK(Long modifiedRecordFK)
    {
        changeHistoryVO.setModifiedRecordFK(SessionHelper.getPKValue(modifiedRecordFK));
    } //-- void setModifiedRecordFK(long)

    public void setNonFinancialTypeCT(String nonFinancialTypeCT)
    {
        changeHistoryVO.setNonFinancialTypeCT(nonFinancialTypeCT);
    } //-- void setNonFinancialTypeCT(java.lang.String)

    public void setOperator(String operator)
    {
        changeHistoryVO.setOperator(operator);
    } //-- void setOperator(java.lang.String)

    public void setParentFK(Long parentFK)
    {
        changeHistoryVO.setParentFK(SessionHelper.getPKValue(parentFK));
    } //-- void setParentFK(long)

    public void setProcessDate(EDITDate processDate)
    {
        changeHistoryVO.setProcessDate(SessionHelper.getEDITDate(processDate));
    } //-- void setProcessDate(java.lang.String)

    public void setRoleCT(String roleCT)
    {
        changeHistoryVO.setRoleCT(roleCT);
    } //-- void setRoleCT(java.lang.String)

    public void setTableName(String tableName)
    {
        changeHistoryVO.setTableName(tableName);
    } //-- void setTableName(java.lang.String)
    
    public void setPendingStatus(String pendingStatus)
    {
        this.changeHistoryVO.setPendingStatus(pendingStatus);
    }
    
    public String getPendingStatus()
    {
        return this.changeHistoryVO.getPendingStatus();
    }

    public static ChangeHistory[] findByModifiedKey_AfterValue(Long modifiedRecordFK)
    {
        String hql = "select ch from ChangeHistory ch where ModifiedRecordFK = :modifiedRecordFK and ch.AfterValue = :afterValue" ;
//        String hql = "select ch from ChangeHistory ch where ModifiedRecordFK = :modifiedRecordFK";

        Map params = new HashMap();

        params.put("modifiedRecordFK", modifiedRecordFK);
        params.put("afterValue", "DeathPending");

        List results = SessionHelper.executeHQL(hql, params, ChangeHistory.DATABASE);

        return (ChangeHistory[]) results.toArray(new ChangeHistory[results.size()]);

    }
    
    /**
     * Find a ChangeHistory record by ModifiedRecordFK and AfterValue fields
     */
    public static ChangeHistory[] findByModifiedRecordKey_AfterValue(Long modifiedRecordFK, String afterValue)
    {
        String hql = "select ch from ChangeHistory ch where ModifiedRecordFK = :modifiedRecordFK and ch.AfterValue = :afterValue" ;

        Map params = new HashMap();

        params.put("modifiedRecordFK", modifiedRecordFK);
        params.put("afterValue", afterValue);

        List results = SessionHelper.executeHQL(hql, params, ChangeHistory.DATABASE);

        return (ChangeHistory[]) results.toArray(new ChangeHistory[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ChangeHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ChangeHistory.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ChangeHistory.DATABASE;
    }

   /**
     * Finder by PK.
     * @param changeHistoryPK
     * @return
     */
    public static final ChangeHistory findByPK(Long changeHistoryPK)
    {
        return (ChangeHistory) SessionHelper.get(ChangeHistory.class, changeHistoryPK, ChangeHistory.DATABASE);
	}

    public static ChangeHistory[] getByModifiedKey(Long modifiedRecordFK)
    {
        String hql = "select ch from ChangeHistory ch where ModifiedRecordFK = :modifiedRecordFK";

        Map params = new HashMap();

        params.put("modifiedRecordFK", modifiedRecordFK);

        List results = SessionHelper.executeHQL(hql, params, ChangeHistory.DATABASE);

        ChangeHistory[] changeHistories = null;

        if (!results.isEmpty())
        {
            changeHistories = (ChangeHistory[]) results.toArray(new ChangeHistory[results.size()]);
        }
        return changeHistories;
    }

    /**
     * Finder. Uses its own session. Orders by EffectiveDate, ModifiedRecordFK asc.
     * @param pendingStatus
     * @param effectiveDate
     * @return
     */
    public static ChangeHistory[] findSeparateBy_PendingStatus_EffectiveDateLTE(String pendingStatus, EDITDate effectiveDate)
    {
        String hql =    " from ChangeHistory changeHistory " +
                        " where changeHistory.PendingStatus = :pendingStatus" +
                        " and changeHistory.EffectiveDate <= :effectiveDate" +
                        " order by changeHistory.EffectiveDate, changeHistory.ModifiedRecordFK asc";
        
        Session session = null;
        
        List<ChangeHistory> results = null;
        
        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
            
            Map params = new EDITMap("pendingStatus", pendingStatus)
                        .put("effectiveDate", effectiveDate);
            
            results = SessionHelper.executeHQL(session, hql,params, 0);
        }
        finally
        {
            if (session != null) session.close();
        }
        
        return results.toArray(new ChangeHistory[results.size()]);
    }

    public static ChangeHistory[] findForClient(Long clientDetailFK)
    {
        String hql = " from ChangeHistory changeHistory" +
                     " where (changeHistory.ModifiedRecordFK = :clientDetailFK" +
                     " and changeHistory.TableName = :clientDetailTableName)" +
        //Now add/union the ChangeHistory for the ClientAddress
                     " or (changeHistory.TableName = :clientAddressTableName" +
                     " and changeHistory.ModifiedRecordFK in (select clientAddress.ClientAddressPK" +
                     " from ClientAddress clientAddress" +
                     " where clientAddress.ClientDetailFK = :clientDetailFK))" +
        //Now add/union the ChangeHistory for the TaxInformation
                     " or (changeHistory.TableName = :clientTaxInformationTableName" +
                     " and changeHistory.ModifiedRecordFK in (select taxInformation.TaxInformationPK" +
                     " from TaxInformation taxInformation" +
                     " where taxInformation.ClientDetailFK = :clientDetailFK))" +
        //Now add/union the ChangeHistory for the TaxProfile
                     " or (changeHistory.TableName = :clientTaxProfileTableName" +
                     " and changeHistory.ModifiedRecordFK in (select taxProfile.TaxProfilePK" +
                     " from TaxProfile taxProfile" +
                     " where taxProfile.TaxInformationFK in (select taxInformation2.TaxInformationPK" +
                     " from TaxInformation taxInformation2" +
                     " where taxInformation2.ClientDetailFK = :clientDetailFK)))" +
        //Now add/union the ChangeHistory for the Preference table
                     " or (changeHistory.TableName = :clientPreferenceTableName" +
                     " and changeHistory.ModifiedRecordFK in (select preference.PreferencePK" +
                     " from Preference preference" +
                     " where preference.ClientDetailFK = :clientDetailFK))" +
                     " order by changeHistory.EffectiveDate desc";

        Map params = new HashMap();

        params.put("clientDetailFK", clientDetailFK);
        params.put("clientDetailTableName", "client.ClientDetail");
        params.put("clientAddressTableName", "client.ClientAddress");
        params.put("clientTaxInformationTableName", "client.TaxInformation");
        params.put("clientTaxProfileTableName", "client.TaxProfile");
        params.put("clientPreferenceTableName", "client.Preference");

        List results = SessionHelper.executeHQL(hql, params, ChangeHistory.DATABASE);

        return (ChangeHistory[]) results.toArray(new ChangeHistory[results.size()]);
    }
    
    /**
     * Returns the specified number of PKs in asc order. This
     * finder is most likely to be used for performance tests.
     * @return
     * @param maxResults the max number of pks to return
     */
    public static List<Long> findSeparate_PKs(int maxResults)
    {
        List<Long> pks = null;
        
        String hql = "select changeHistory.ChangeHistoryPK " +
                    " from ChangeHistory changeHistory " +
                    " order by changeHistory.ChangeHistoryPK asc";
        
        Session separateSession = null;
        
        try
        {
            separateSession = SessionHelper.getSeparateSession(DATABASE);
            
            pks = SessionHelper.executeHQL(separateSession, hql, null, maxResults);
        }
        finally
        {
            if (separateSession != null)
            {
                separateSession.close();
            }
        }
        
        
        return pks;
    }    
}
