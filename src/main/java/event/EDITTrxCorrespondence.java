/*
 * User: gfrosti
 * Date: Nov 7, 2003
 * Time: 8:57:11 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;


import edit.common.vo.*;
import edit.common.*;
import edit.services.db.hibernate.*;
import edit.services.db.*;
import event.financial.client.trx.ClientTrx;
import event.dm.dao.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import fission.utility.*;


public class EDITTrxCorrespondence extends HibernateEntity implements CRUDEntity
{
    private EDITTrxCorrespondenceVO editTrxCorrespondenceVO;

    private EDITTrxCorrespondenceImpl editTrxCorrespondenceImpl;
    private EDITTrx editTrx;
    private TransactionCorrespondence transactionCorrespondence;

    public static final String STATUS_PENDING = "P";
    public static final String STATUS_HISTORY = "H";
    public static final String STATUS_TERMINATED = "T";

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public EDITTrxCorrespondence()
    {
        this.editTrxCorrespondenceImpl = new EDITTrxCorrespondenceImpl();
        this.editTrxCorrespondenceVO = new EDITTrxCorrespondenceVO();
    }

    public EDITTrxCorrespondence(long editTrxCorrespondencePK) throws Exception
    {
        this();
        this.editTrxCorrespondenceImpl.load(this, editTrxCorrespondencePK);
    }

    public EDITTrxCorrespondence(EDITTrxCorrespondenceVO editTrxCorrespondenceVO)
    {
        this();
        this.editTrxCorrespondenceVO = editTrxCorrespondenceVO;
    }

    public void save(CRUD crud) throws Exception
    {
        editTrxCorrespondenceImpl.save(this, crud);
    }

    public void save(EDITTrx editTrx, CRUD crud) throws Exception
     {
         editTrxCorrespondenceImpl.save(this, editTrx, crud);
     }



    /**
     * Saves the EditTrxCorrespondence correspondence, which will use the notification days parameter to
     * calculate the correspondence effective date if the transaction is 'HFTA' or 'HFTP'
     * @param notificationDays
     * @throws Exception
     */
    public void save(int notificationDays, String notificationDaysType, EDITTrx editTrx) throws Exception
    {
        editTrxCorrespondenceImpl.save(this, notificationDays, notificationDaysType, editTrx);
    }

    /**
     * Saves the EditTrxCorrespondence correspondence, which will use the notification days parameter to
     * calculate the correspondence effective date if the transaction is 'HFTA' or 'HFTP'
     * @param notificationDays
     * @throws Exception
     */
    public void save(int notificationDays, String notificationDaysType, EDITTrxVO editTrxVO, CRUD crud) throws Exception
    {
        editTrxCorrespondenceImpl.save(this, notificationDays, notificationDaysType, editTrxVO, crud);
    }

    public void save() throws Exception
    {
        editTrxCorrespondenceImpl.save(this, 0, null);
    }

    public VOObject getVO()
    {
        return editTrxCorrespondenceVO;
    }

    public void delete() throws Exception
    {
        editTrxCorrespondenceImpl.delete(this);
    }

    public long getPK()
    {
        return editTrxCorrespondenceVO.getEDITTrxCorrespondencePK();
    }

    public void setVO(VOObject voObject)
    {
        this.editTrxCorrespondenceVO = (EDITTrxCorrespondenceVO) voObject;
    }

    public boolean isNew()
    {
        return editTrxCorrespondenceImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return editTrxCorrespondenceImpl.cloneCRUDEntity(this);
    }

    public TransactionCorrespondence getTransactionCorrespondence()
    {
        return transactionCorrespondence;
    }

    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }

    public void setEDITTrx(ClientTrx clientTrx) throws Exception
    {
        editTrxCorrespondenceVO.setEDITTrxFK(clientTrx.getEDITTrxVO().getEDITTrxPK());
    }

    public void set_EDITTrx(EDITTrxVO editTrxVO) throws Exception
    {
        editTrxCorrespondenceVO.setEDITTrxFK(editTrxVO.getEDITTrxPK());
    }

    public void set_TransactionCorrespondence(TransactionCorrespondence transactionCorrespondence) throws Exception
    {
        editTrxCorrespondenceVO.setTransactionCorrespondenceFK(transactionCorrespondence.getPK());
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    public void setTransactionCorrespondence(TransactionCorrespondence transactionCorrespondence)
    {
        this.transactionCorrespondence = transactionCorrespondence;
    }

    public void updateStatus(String status) throws Exception
    {
        editTrxCorrespondenceVO.setStatus(status);

        save();
    }

    /**
     * Sets the NotificationAmount field on the EditTrxCorrespondence record using the notificationAmount parameter
     * @param notificationAmount
     */
    public void setNotificationAmount(EDITBigDecimal notificationAmount)
    {
        editTrxCorrespondenceVO.setNotificationAmount(SessionHelper.getEDITBigDecimal(notificationAmount));
    }

    public String getAddressTypeCT()
        {
            return editTrxCorrespondenceVO.getAddressTypeCT();
        }

    public EDITDate getCorrespondenceDate()
    {
        return SessionHelper.getEDITDate(editTrxCorrespondenceVO.getCorrespondenceDate());
    }

    public Long getEDITTrxCorrespondencePK()
    {
        return SessionHelper.getPKValue(editTrxCorrespondenceVO.getEDITTrxCorrespondencePK());
    }

    public Long getEDITTrxFK()
    {
        return SessionHelper.getPKValue(editTrxCorrespondenceVO.getEDITTrxFK());
    }

    public EDITBigDecimal getNotificationAmount()
    {
        return SessionHelper.getEDITBigDecimal(editTrxCorrespondenceVO.getNotificationAmount());
    }

    public Long getTransactionCorrespondenceFK()
    {
        return SessionHelper.getPKValue(editTrxCorrespondenceVO.getTransactionCorrespondenceFK());
    }

    public String getStatus()
    {
        return editTrxCorrespondenceVO.getStatus();
    }

    public void setAddressTypeCT(String addressTypeCT)
    {
        editTrxCorrespondenceVO.setAddressTypeCT(addressTypeCT);
    }

    public void setCorrespondenceDate(EDITDate correspondenceDate)
    {
        editTrxCorrespondenceVO.setCorrespondenceDate(SessionHelper.getEDITDate(correspondenceDate));
    }

    public void setEDITTrxCorrespondencePK(long EDITTrxCorrespondencePK)
    {
        editTrxCorrespondenceVO.setEDITTrxCorrespondencePK(EDITTrxCorrespondencePK);
    }

    public void setEDITTrxFK(long EDITTrxFK)
    {
        editTrxCorrespondenceVO.setEDITTrxFK(EDITTrxFK);
    }

    public void setNotificationAmount(BigDecimal notificationAmount)
    {
        editTrxCorrespondenceVO.setNotificationAmount(notificationAmount);
    }

    public void setStatus(String status)
    {
        editTrxCorrespondenceVO.setStatus(status);
    }

    public void setTransactionCorrespondenceFK(long transactionCorrespondenceFK)
    {
        editTrxCorrespondenceVO.setTransactionCorrespondenceFK(transactionCorrespondenceFK);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return EDITTrxCorrespondence.DATABASE;
    }

    public static EDITTrxCorrespondence findByTrxCorrFK(Long editTrxPK)
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = new EDITTrxCorrespondenceDAO().findByTrxCorrFK(editTrxPK.longValue());

        EDITTrxCorrespondence editTrxCorrespondence = null;
        if (editTrxCorrespondenceVOs != null)
        {
            editTrxCorrespondence = new EDITTrxCorrespondence(editTrxCorrespondenceVOs[0]);
        }

        return editTrxCorrespondence;
    }

    public EDITDate calcCorrespondenceDate(TransactionCorrespondence transactionCorrespondence, EDITDate effectiveDate)
    {
        int numberOfDays = transactionCorrespondence.getNumberOfDays();

        String priorPostCT = transactionCorrespondence.getPriorPostCT();

        EDITDate correspondenceDate = effectiveDate;

        if (priorPostCT.equals("Prior"))
        {
            correspondenceDate = correspondenceDate.subtractDays(numberOfDays);
        }

        else if (priorPostCT.equals("Post"))
        {
            correspondenceDate = correspondenceDate.addDays(numberOfDays);
        }

        return correspondenceDate;
    }

    /**
     * Finder by PK.
     * @param editTrxCorrespondencePK
     * @return EDITTrx Correspondence object
     */
    public static final EDITTrxCorrespondence findByPK(Long editTrxCorrespondencePK)
    {
        return (EDITTrxCorrespondence) SessionHelper.get(EDITTrxCorrespondence.class, editTrxCorrespondencePK, EDITTrxCorrespondence.DATABASE);
    }

    /**
     * Retrieves EDITTrxCorrespondence records by given status and prior to given correspondence date.
     * @param status
     * @param correspondenceDate
     * @return array of long (EDITTrxcorrespondencePKs) values
     */
    public static final long[] findEDITTrxCorrespondenceByStatus_And_PriorToCorrespondenceDate(String status, EDITDate correspondenceDate)
    {
    	/*
        String hql = " select editTrxCorrespondence.EDITTrxCorrespondencePK" +
                     " from EDITTrxCorrespondence editTrxCorrespondence" +
                     " where editTrxCorrespondence.Status = :status" +
                     " and editTrxCorrespondence.CorrespondenceDate <= :correspondenceDate";
                     */
        String hql =       " select a.EDITTrxCorrespondencePK" + 
                           " from EDITTrxCorrespondence a, ClientSetup b, ContractClient c, Segment s, EDITTrx e " +
                           " where e.EDITTrxPK = a.EDITTrxFK " +
                           " and b.ClientSetupPK = e.ClientSetupFK " +
                           " and c.ContractClientPK = b.ContractClientFK " +
                           " and s.SegmentPK = c.SegmentFK " +
                           " and a.Status = :status" +
                           " and a.CorrespondenceDate <= :correspondenceDate" +
                           " and ( " +
                           " 	(e.TransactionTypeCT = 'FI' and e.PendingStatus not in ('T')) or " +
                           " 	(e.TransactionTypeCT != 'FI' and e.PendingStatus not in ('P', 'T')) " +
                           " ) " +
                           " and s.SegmentStatusCT != 'Frozen'";

        Map params = new HashMap();
        params.put("status", status);
        params.put("correspondenceDate", correspondenceDate);

        List results = SessionHelper.executeHQL(hql, params, EDITTrxCorrespondence.DATABASE);

        return Util.convertLongToPrim((Long[]) results.toArray(new Long[results.size()]));
    }

    /**
     * Unterminates this EDITTrxCorrespondence.  Does this simply by setting the status to pending
     */
    public void unterminate()
    {
        editTrxCorrespondenceVO.setStatus(EDITTrxCorrespondence.STATUS_PENDING);
	}
}
