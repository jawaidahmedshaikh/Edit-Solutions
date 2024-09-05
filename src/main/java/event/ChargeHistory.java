/*
 * User: gfrosti
 * Date: Feb 12, 2005
 * Time: 4:34:44 PM
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

import event.dm.dao.*;

import java.util.*;



public class ChargeHistory extends HibernateEntity implements CRUDEntity
{
    public static final String CHARGETYPECT_ADMINFEE = "AdminFee";
    private CRUDEntityImpl crudEntityImpl;
    private ChargeHistoryVO chargeHistoryVO;
    private EDITTrxHistory editTrxHistory;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a ChargeHistory entity with a default ChargeHistoryVO.
     */
    public ChargeHistory()
    {
        init();
    }

    /**
     * Instantiates a ChargeHistory entity with a supplied ChargeHistoryVO.
     *
     * @param chargeHistoryVO
     */
    public ChargeHistory(ChargeHistoryVO chargeHistoryVO)
    {
        init();

        this.chargeHistoryVO = chargeHistoryVO;
    }

    public EDITBigDecimal getAdjustmentNonTaxable()
    {
        return SessionHelper.getEDITBigDecimal(chargeHistoryVO.getAdjustmentNonTaxable());
    }

    //-- java.math.BigDecimal getAdjustmentNonTaxable() 
    public EDITBigDecimal getAdjustmentTaxable()
    {
        return SessionHelper.getEDITBigDecimal(chargeHistoryVO.getAdjustmentTaxable());
    }

    //-- java.math.BigDecimal getAdjustmentTaxable() 
    public Long getChargeHistoryPK()
    {
        return SessionHelper.getPKValue(chargeHistoryVO.getChargeHistoryPK());
    }

    //-- long getChargeHistoryPK() 
    public void setAdjustmentNonTaxable(EDITBigDecimal adjustmentNonTaxable)
    {
        chargeHistoryVO.setAdjustmentNonTaxable(SessionHelper.getEDITBigDecimal(adjustmentNonTaxable));
    }

    //-- void setAdjustmentNonTaxable(java.math.BigDecimal) 
    public void setAdjustmentTaxable(EDITBigDecimal adjustmentTaxable)
    {
        chargeHistoryVO.setAdjustmentTaxable(SessionHelper.getEDITBigDecimal(adjustmentTaxable));
    }

    //-- void setAdjustmentTaxable(java.math.BigDecimal) 
    public void setChargeAmount(EDITBigDecimal chargeAmount)
    {
        chargeHistoryVO.setChargeAmount(SessionHelper.getEDITBigDecimal(chargeAmount));
    }

    //-- void setChargeAmount(java.math.BigDecimal) 
    public void setChargeHistoryPK(Long chargeHistoryPK)
    {
        chargeHistoryVO.setChargeHistoryPK(SessionHelper.getPKValue(chargeHistoryPK));
    }

    //-- void setChargeHistoryPK(long) 
    public void setChargeTypeCT(String chargeTypeCT)
    {
        chargeHistoryVO.setChargeTypeCT(chargeTypeCT);
    }

    //-- void setChargeTypeCT(java.lang.String) 

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
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (chargeHistoryVO == null)
        {
            chargeHistoryVO = new ChargeHistoryVO();
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
        return chargeHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return chargeHistoryVO.getChargeHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.chargeHistoryVO = (ChargeHistoryVO) voObject;
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

    public ChargeHistoryVO getAsVO()
    {
        return chargeHistoryVO;
    }

    /**
     * Finder.
     *
     * @param chargeHistoryPK
     */
    public static final ChargeHistory findByPK(long chargeHistoryPK)
    {
        ChargeHistory chargeHistory = null;

        ChargeHistoryVO[] chargeHistoryVOs = new ChargeHistoryDAO().findByPK(chargeHistoryPK);

        if (chargeHistoryVOs != null)
        {
            chargeHistory = new ChargeHistory(chargeHistoryVOs[0]);
        }

        return chargeHistory;
    }

    /**
     * Getter.
     * @return
     */
    public String getChargeTypeCT()
    {
        return chargeHistoryVO.getChargeTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getChargeAmount()
    {
        return SessionHelper.getEDITBigDecimal(chargeHistoryVO.getChargeAmount());
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public static ChargeHistory findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        ChargeHistory chargeHistory = null;

        ChargeHistoryVO[] chargeHistoryVOs = new ChargeHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryPK);

        if (chargeHistoryVOs != null)
        {
            chargeHistory = new ChargeHistory(chargeHistoryVOs[0]);
        }

        return chargeHistory;
    }

    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(chargeHistoryVO.getEDITTrxHistoryFK());
    }
     //-- long getEDITTrxHistoryFK() 

    public void setEDITTrxHistoryFK(Long EDITTrxHistoryFK)
    {
        chargeHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(EDITTrxHistoryFK));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ChargeHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ChargeHistory.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ChargeHistory.DATABASE;
    }

    /**
     * Originally in ChargeHistory.findByEditTrxHistoryPK
     * @param editTrxHistoryPK
     * @return
     */
    public static ChargeHistory[] findBy_EDITTrxHistoryPK(Long editTrxHistoryPK)
    {
        String hql = " select chargeHistory from ChargeHistory chargeHistory" +
                    " where chargeHistory.EDITTrxHistoryFK = :editTrxHistoryPK";

        Map params = new HashMap();

        params.put("editTrxHistoryPK", editTrxHistoryPK);

        List results = SessionHelper.executeHQL(hql, params, ChargeHistory.DATABASE);

        return (ChargeHistory[]) results.toArray(new ChargeHistory[results.size()]);
    }

    public static ChargeHistory[] findBySegmentFK(Long segmentFK)
    {
        String hql = "select ch from ChargeHistory ch join ch.EDITTrxHistory eth" +
                     " join eth.EDITTrx et join et.ClientSetup cls" +
                     " join cls.ContractSetup cs where cs.SegmentFK = :segmentFK" +
                     " and (et.Status = :naturalStatus or et.Status = :reapplyStatus)";

        Map params = new TreeMap();

        params.put("segmentFK", segmentFK);
        params.put("naturalStatus", "N");
        params.put("reapplyStatus", "A");

        List results = SessionHelper.executeHQL(hql, params, ChargeHistory.DATABASE);

        return (ChargeHistory[]) results.toArray(new ChargeHistory[results.size()]);
    }
}
