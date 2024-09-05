/*
 * User: gfrosti
 * Date: Dec 1, 2004
 * Time: 11:16:32 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package reinsurance;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.common.vo.VOObject;
import edit.common.vo.ReinsuranceHistoryVO;
import edit.common.*;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import event.EDITTrxHistory;
import reinsurance.dm.dao.*;

public class ReinsuranceHistory extends HibernateEntity implements CRUDEntity
{
    public static final String HISTORY_STATUS = "H";
    
    public static final String UPDATE_STATUS = "U";

    private CRUDEntityImpl crudEntityImpl;

    private ReinsuranceHistoryVO reinsuranceHistoryVO;

    private EDITTrxHistory editTrxHistory;

    /**
     * Instantiates a ReinsuranceHistory entity with a default ReinsuranceHistoryVO.
     */
    public ReinsuranceHistory()
    {
        init();
    }

    /**
     * Instantiates a ReinsuranceHistory entity with a ReinsuranceHistoryVO retrieved from persistence.
     * @param reinsuranceHistoryPK
     */
    public ReinsuranceHistory(long reinsuranceHistoryPK)
    {
        init();

        crudEntityImpl.load(this, reinsuranceHistoryPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a ReinsuranceHistory entity with a supplied ReinsuranceHistoryVO.
     * @param reinsuranceHistoryVO
     */
    public ReinsuranceHistory(ReinsuranceHistoryVO reinsuranceHistoryVO)
    {
        init();

        this.reinsuranceHistoryVO = reinsuranceHistoryVO;
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
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Getter.
     * @return
     */
    public Long getReinsuranceHistoryPK()
    {
        return reinsuranceHistoryVO.getReinsuranceHistoryPK();
    }

    /**
     * Setter.
     * @param reinsuranceHistoryPK
     */
    public void setReinsuranceHistoryPK(Long reinsuranceHistoryPK)
    {
        reinsuranceHistoryVO.setReinsuranceHistoryPK(reinsuranceHistoryPK);

    }
    
    /**
     * Getter.
     * @return
     */ 
    public long getContractTreatyFK()
    {
        return reinsuranceHistoryVO.getContractTreatyFK();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getModalPremiumAmount()
    {
        return new EDITBigDecimal(reinsuranceHistoryVO.getModalPremiumAmount());
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return reinsuranceHistoryVO.getReinsuranceHistoryPK();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return reinsuranceHistoryVO;
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
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * Setter.
     * @param modalPremiumAmount
     */
    public void setModalPremiumAmount(EDITBigDecimal modalPremiumAmount)
    {
        reinsuranceHistoryVO.setModalPremiumAmount(SessionHelper.getEDITBigDecimal(modalPremiumAmount));
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getUpdateDateTime()
    {
        return reinsuranceHistoryVO.getUpdateDateTime();
    }

    /**
     * Setter.
     * @param updateDateTime
     */
    public void setUpdateDateTime(String updateDateTime)
    {
        reinsuranceHistoryVO.setUpdateDateTime(updateDateTime);
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getUpdateStatus()
    {
        return reinsuranceHistoryVO.getUpdateStatus();
    }

    /**
     * Setter.
     * @param status
     */
    public void setUpdateStatus(String status)
    {
        reinsuranceHistoryVO.setUpdateStatus(status);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getReinsuranceNAR()
    {
        return SessionHelper.getEDITBigDecimal(reinsuranceHistoryVO.getReinsuranceNAR());
    }

    /**
     * Setter.
     * @param reinsuranceNAR
     */
    public void setReinsuranceNAR(EDITBigDecimal reinsuranceNAR)
    {
        reinsuranceHistoryVO.setReinsuranceNAR(SessionHelper.getEDITBigDecimal(reinsuranceNAR));
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getReinsuranceContractPeriodCT()
    {
        return reinsuranceHistoryVO.getReinsuranceContractPeriodCT();
    }

    /**
     * Setter.
     * @param reinsuranceContractPeriodCT
     */
    public void setReinsuranceContractPeriodCT(java.lang.String reinsuranceContractPeriodCT)
    {
        reinsuranceHistoryVO.setReinsuranceContractPeriodCT(reinsuranceContractPeriodCT);
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getMaintDateTime()
    {
        return reinsuranceHistoryVO.getMaintDateTime();
    }

    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        reinsuranceHistoryVO.setMaintDateTime(maintDateTime);
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getOperator()
    {
        return reinsuranceHistoryVO.getOperator();
    }

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(java.lang.String operator)
    {
        reinsuranceHistoryVO.setOperator(operator);
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getAccountingPendingStatus()
    {
        return reinsuranceHistoryVO.getAccountingPendingStatus();
    }

    /**
     * Setter.
     * @param accountingPendingStatus
     */
    public void setAccountingPendingStatus(java.lang.String accountingPendingStatus)
    {
        reinsuranceHistoryVO.setAccountingPendingStatus(accountingPendingStatus);
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getReinsuranceTypeCT()
    {
        return reinsuranceHistoryVO.getReinsuranceTypeCT();
    }

    /**
     * Setter.
     * @param reinsuranceTypeCT
     */
    public void setReinsuranceTypeCT(java.lang.String reinsuranceTypeCT)
    {
        reinsuranceHistoryVO.setReinsuranceTypeCT(reinsuranceTypeCT);
    }

    /**
     * Getter.
     * @return
     */
    public java.lang.String getUndoRedoStatus()
    {
        return reinsuranceHistoryVO.getUndoRedoStatus();
    }

    /**
     * Setter.
     * @param undoRedoStatus
     */
    public void setUndoRedoStatus(java.lang.String undoRedoStatus)
    {
        reinsuranceHistoryVO.setUndoRedoStatus(undoRedoStatus);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCededDeathBenefit()
    {
        return SessionHelper.getEDITBigDecimal(reinsuranceHistoryVO.getCededDeathBenefit());
    }

    /**
     * Setter.
     * @param cededDeathBenefit
     */
    public void setCededDeathBenefit(EDITBigDecimal cededDeathBenefit)
    {
        reinsuranceHistoryVO.setCededDeathBenefit(SessionHelper.getEDITBigDecimal(cededDeathBenefit));
    }

    /**
     * Getter.
     * @return
     */
    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(reinsuranceHistoryVO.getEDITTrxHistoryFK());
    }

    /**
     * Setter.
     * @param EDITTrxHistoryFK
     */
    public void setEDITTrxHistoryFK(Long EDITTrxHistoryFK)
    {
        reinsuranceHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(EDITTrxHistoryFK));
    }

    /**
     * Setter.
     * @param ContractTreatyFK
     */
    public void setContractTreatyFK(Long ContractTreatyFK)
    {
        reinsuranceHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(ContractTreatyFK));
    }

    /**
     * Setter.
     * @param editTrxHistory
     */
    public void setEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        this.editTrxHistory = editTrxHistory;
    }

    /**
     * Getter.
     * @return
     */
    public EDITTrxHistory getEDITTrxHistory()
    {
        return editTrxHistory;
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.reinsuranceHistoryVO = (ReinsuranceHistoryVO) voObject;
    }

    /**
     * Maps the ModalPremiumAmount as an update to its associated Treaty.
     */
    public void updateBalance()
    {
        Treaty treaty = Treaty.findBy_ContractTreatyPK(getContractTreatyFK());
        
        treaty.updateReinsurerBalance(getModalPremiumAmount(), this.reinsuranceHistoryVO.getReinsuranceTypeCT());
        
        treaty.save();
        
        setUpdateDateTime(new EDITDateTime().getFormattedDateTime());

        setUpdateStatus(ReinsuranceHistory.HISTORY_STATUS);

        save();
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (reinsuranceHistoryVO == null)
        {
            reinsuranceHistoryVO = new ReinsuranceHistoryVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    public ReinsuranceHistoryVO getAsVO()
    {
        return reinsuranceHistoryVO;
    }

    /**
     * Finder.
     * @param updateStatus
     * @return
     */
    public static final ReinsuranceHistory[] findBy_UpdateStatus(String updateStatus)
    {
        return (ReinsuranceHistory[]) CRUDEntityImpl.mapVOToEntity(new ReinsuranceHistoryDAO().findBy_UpdateStatus(updateStatus), ReinsuranceHistory.class);
    }

    /**
     * Finder.
     * @param updateStatus
     * @return
     */
    public static final ReinsuranceHistory[] findBy_UpdateStatus_ProductStructurePK(String updateStatus, long productStructurePK)
    {
        return (ReinsuranceHistory[]) CRUDEntityImpl.mapVOToEntity(new ReinsuranceHistoryDAO().findBy_UpdateStatus_ProductStructurePK(updateStatus, productStructurePK), ReinsuranceHistory.class);
    }

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public static final ReinsuranceHistory[] findReinsuranceHistoryBy_ReinsurerPK(long reinsurerPK)
    {
        return (ReinsuranceHistory[]) CRUDEntityImpl.mapVOToEntity(new ReinsuranceHistoryDAO().findReinsuranceHistoryBy_ReinsurerPK(reinsurerPK), ReinsuranceHistory.class);
    }

    /**
     * Finder.
     * @param contractTreatyPK
     * @return
     */
    public static ReinsuranceHistory[] findBy_ContractTreatyPK(long contractTreatyPK)
    {
        return (ReinsuranceHistory[]) CRUDEntityImpl.mapVOToEntity(new ReinsuranceHistoryDAO().findBy_ContractTreatyPK(contractTreatyPK), ReinsuranceHistory.class);
    }

    public static ReinsuranceHistory[] findBy_EDITTrxHistoryFK(long editTrxHistoryFK)
    {
        return (ReinsuranceHistory[]) CRUDEntityImpl.mapVOToEntity(new ReinsuranceHistoryDAO().findBy_EDITTrxHistoryFK(editTrxHistoryFK), ReinsuranceHistory.class);
    }
}