/*
 * User: gfrosti
 * Date: Feb 12, 2005
 * Time: 4:16:19 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.dm.dao.SegmentHistoryDAO;
import contract.*;
import billing.BillSchedule;
import java.util.*;
import staging.IStaging;
import staging.StagingContext;

public class SegmentHistory extends HibernateEntity implements CRUDEntity, IStaging
{
    private CRUDEntityImpl crudEntityImpl;
    private SegmentHistoryVO segmentHistoryVO;
    private EDITTrxHistory editTrxHistory;
    private BillSchedule billSchedule;

    /**
     * The parent Segment.
     */
    private Segment segment;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a SegmentHistory entity with a default SegmentHistoryVO.
     */
    public SegmentHistory()
    {
        init();
    }

    public SegmentHistory(EDITTrxHistory editTrxHistory)
    {
        init();

        this.segmentHistoryVO.setEDITTrxHistoryFK(editTrxHistory.getPK());
    }

    /**
     * Instantiates a FinancialHistory entity with a supplied FinancialHistoryVO.
     *
     * @param financialHistoryVO
     */
    public SegmentHistory(SegmentHistoryVO segmentHistoryVO)                
    {
        init();

        this.segmentHistoryVO = segmentHistoryVO;
    }

    public Long getSegmentHistoryPK()
    {
        return SessionHelper.getPKValue(segmentHistoryVO.getSegmentHistoryPK());
    }

    //-- long getSegmentHistoryPK()
    public void setSegmentHistoryPK(Long segmentHistoryPK)
    {
        segmentHistoryVO.setSegmentHistoryPK(SessionHelper.getPKValue(segmentHistoryPK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(segmentHistoryVO.getEDITTrxHistoryFK());
    }

    /**
     * Setter.
     * @param billScheduleFK
     */
    public void setBillScheduleFK(Long billScheduleFK)
    {
        segmentHistoryVO.setBillScheduleFK(SessionHelper.getPKValue(billScheduleFK));
    }
    
    /**
    * Getter.
    * @return
    */
   public Long getBillScheduleFK()
   {
       return SessionHelper.getPKValue(segmentHistoryVO.getBillScheduleFK());
   }

   /**
    * Setter.
    * @param editTrxHistoryFK
    */
   public void setEDITTrxHistoryFK(Long editTrxHistoryFK)
   {
       segmentHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(editTrxHistoryFK));
   }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(segmentHistoryVO.getSegmentFK());
    }

    /**
     * Setter.
     * @param segmentFK
     */
    public void setSegmentFK(Long segmentFK)
    {
        segmentHistoryVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPrevFaceAmount()
    {
        return SessionHelper.getEDITBigDecimal(segmentHistoryVO.getPrevFaceAmount());
    }

    /**
     * Setter.
     * @param prevFaceAmount
     */
    public void setPrevFaceAmount(EDITBigDecimal prevFaceAmount)
    {
        segmentHistoryVO.setPrevFaceAmount(SessionHelper.getEDITBigDecimal(prevFaceAmount));
    }

    public EDITBigDecimal getPrevAnnualPremium()
    {
        return SessionHelper.getEDITBigDecimal(segmentHistoryVO.getPrevAnnualPremium());
    }

    /**
     * Setter.
     * @param prevFaceAmount
     */
    public void setPrevAnnualPremium(EDITBigDecimal prevAnnualPremium)
    {
        segmentHistoryVO.setPrevFaceAmount(SessionHelper.getEDITBigDecimal(prevAnnualPremium));
    }


    /**
     * Getter.
     * @return
     */
    public String getPrevSegmentStatus()
    {
        return segmentHistoryVO.getPrevSegmentStatus();
    }

    public String getPriorRateClass()
    {
        return segmentHistoryVO.getPriorRateClass();
    }

    /**
     * Setter.
     * @param prevSegmentStatus
     */
    public void setPrevSegmentStatus(String prevSegmentStatus)
    {
        segmentHistoryVO.setPrevSegmentStatus(prevSegmentStatus);
    }

    public void setPriorRateClass(String priorRateClass)
    {
        segmentHistoryVO.setPriorRateClass(priorRateClass);
    }

    /**
     * Getter
     * @return
     */
    public EDITDate getPrevLastAnniversaryDate()
    {
        return SessionHelper.getEDITDate(segmentHistoryVO.getPrevLastAnniversaryDate());
    } //-- java.lang.String getPrevLastAnniversaryDate()

    /**
     * Setter
     * @param prevLastAnniversaryDate
     */
    public void setPrevLastAnniversaryDate(EDITDate prevLastAnniversaryDate)
    {
        segmentHistoryVO.setPrevLastAnniversaryDate(SessionHelper.getEDITDate(prevLastAnniversaryDate));
    } //-- void setPrevLastAnniversaryDate(java.lang.String)

    /**
     * Getter
     * @return
     */
    public EDITDate getPriorPaidToDate()
    {
        return SessionHelper.getEDITDate(segmentHistoryVO.getPriorPaidToDate());
    }

    /**
     * Setter
     * @param priorPaidToDate
     */
    public void setPriorPaidToDate(EDITDate priorPaidToDate)
    {
        segmentHistoryVO.setPriorPaidToDate(SessionHelper.getEDITDate(priorPaidToDate));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarPaidUpTerm()
    {
        return SessionHelper.getEDITBigDecimal(segmentHistoryVO.getGuarPaidUpTerm());
    }

    /**
     * Setter.
     * @param guarPaidUpTerm
     */
    public void setGuarPaidUpTerm(EDITBigDecimal guarPaidUpTerm)
    {
        segmentHistoryVO.setGuarPaidUpTerm(SessionHelper.getEDITBigDecimal(guarPaidUpTerm));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNonGuarPaidUpTerm()
    {
        return SessionHelper.getEDITBigDecimal(segmentHistoryVO.getNonGuarPaidUpTerm());
    }

    /**
     * Setter.
     * @param nonGuarPaidUpTerm
     */
    public void setNonGuarPaidUpTerm(EDITBigDecimal nonGuarPaidUpTerm)
    {
        segmentHistoryVO.setNonGuarPaidUpTerm(SessionHelper.getEDITBigDecimal(nonGuarPaidUpTerm));
    }

    /**
     * Getter
     * @return
     */
    public String getStatusCT()
    {
        return segmentHistoryVO.getStatusCT();
    }

    /**
     * Setter
     * @param statusCT
     */
    public void setStatusCT(String statusCT)
    {
        segmentHistoryVO.setStatusCT(statusCT);
    }
    
    /**
     * Getter.
     * @return
     */
    public EDITDate getPriorTerminationDate()
    {
        return SessionHelper.getEDITDate(segmentHistoryVO.getPriorTerminationDate());
    }

    /**
     * Setter.
     * @param priorTerminationDate
     */
    public void setPriorTerminationDate(EDITDate priorTerminationDate)
    {
        segmentHistoryVO.setPriorTerminationDate(SessionHelper.getEDITDate(priorTerminationDate));
    }
    
    
    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (segmentHistoryVO == null)
        {
            segmentHistoryVO = new SegmentHistoryVO();
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
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return segmentHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return segmentHistoryVO.getSegmentHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.segmentHistoryVO = (SegmentHistoryVO) voObject;
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
     * Getter.
     * @return
     */
    public EDITTrxHistory getEDITTrxHistory()
    {
        return editTrxHistory;
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
    public BillSchedule getBillSchedule()
    {
        return billSchedule;
    }

    /**
     * Setter.
     * @param editTrxHistory
     */
    public void setBillSchedule(BillSchedule billSchedule)
    {
        this.billSchedule = billSchedule;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return SegmentHistory.DATABASE;
    }

    public SegmentHistoryVO getAsVO()
    {
        return segmentHistoryVO;
    }

    public static SegmentHistory[] findBy_EDITTrxHistoryFK(long editTrxHistoryFK)
    {
        return (SegmentHistory[]) CRUDEntityImpl.mapVOToEntity(new SegmentHistoryDAO().findByEditTrxHistoryFK(editTrxHistoryFK), SegmentHistory.class);
    }
    
    public static Double getPrevFaceAmount(Long segmentPK, long editTrxHistoryPK)
    {
        Double prevFaceAmount = new Double(0.00);

        String hql = "select sh.PrevFaceAmount from SegmentHistory sh where sh.SegmentFK = :segmentFK and sh.EDITTrxHistoryFK = :editTrxHistoryFK ";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("editTrxHistoryFK", editTrxHistoryPK);

        List results = SessionHelper.executeHQL(hql, params, SegmentHistory.DATABASE);

        if (!results.isEmpty())
        {
            prevFaceAmount = ((EDITBigDecimal) results.get(0)).doubleValue();
        }
        return prevFaceAmount;
    }

    public static SegmentHistory[] findBySegmentPK(Long segmentPK)
    {
        SegmentHistory[] segmentHistories = null;

        String hql = "from SegmentHistory sh where sh.SegmentFK = :segmentFK order by sh.SegmentHistoryPK";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, SegmentHistory.DATABASE);

        if (!results.isEmpty())
        {
            segmentHistories = (SegmentHistory[]) results.toArray(new SegmentHistory[results.size()]);
        }
        return segmentHistories;
    }
    
    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.ContractStatusHistory contractStatusHistory = new staging.ContractStatusHistory();
        contractStatusHistory.setSegmentBase(stagingContext.getCurrentSegmentBase());
        contractStatusHistory.setProcessDate(this.getEDITTrxHistory().getProcessDateTime());
        contractStatusHistory.setEffectiveDate(this.getEDITTrxHistory().getEDITTrx().getEffectiveDate());
        contractStatusHistory.setStatus(this.getStatusCT());
        contractStatusHistory.setPriorStatus(this.getPrevSegmentStatus());

        stagingContext.getCurrentSegmentBase().addContractStatusHistory(contractStatusHistory);

        SessionHelper.saveOrUpdate(contractStatusHistory, database);

        return stagingContext;
    }

    /**
     * @see #segment
     * @return the segment
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * @see #segment
     * @param segment the segment to set
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }
}
