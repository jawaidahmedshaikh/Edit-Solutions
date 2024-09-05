/*
 * User: gfrosti
 * Date: Feb 12, 2005
 * Time: 4:45:14 PM
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



public class WithholdingHistory extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private WithholdingHistoryVO withholdingHistoryVO;
    private EDITTrxHistory editTrxHistory;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a WithholdingHistory entity with a default WithholdingHistoryVO.
     */
    public WithholdingHistory()
    {
        init();
    }

    /**
     * Instantiates a WithholdingHistory entity with a supplied WithholdingHistoryVO.
     *
     * @param withholdingHistoryVO
     */
    public WithholdingHistory(WithholdingHistoryVO withholdingHistoryVO)
    {
        init();

        this.withholdingHistoryVO = withholdingHistoryVO;
    }

    public EDITBigDecimal getCityWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingHistoryVO.getCityWithholdingAmount());
    }
     //-- java.math.BigDecimal getCityWithholdingAmount()

    public EDITBigDecimal getCountyWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingHistoryVO.getCountyWithholdingAmount());
    }
     //-- java.math.BigDecimal getCountyWithholdingAmount()

    public EDITBigDecimal getFICA()
    {
        return SessionHelper.getEDITBigDecimal(withholdingHistoryVO.getFICA());
    }
     //-- java.math.BigDecimal getFICA()

    public EDITBigDecimal getFederalWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingHistoryVO.getFederalWithholdingAmount());
    }
     //-- java.math.BigDecimal getFederalWithholdingAmount()

    public EDITBigDecimal getStateWithholdingAmount()
    {
        return SessionHelper.getEDITBigDecimal(withholdingHistoryVO.getStateWithholdingAmount());
    }
     //-- java.math.BigDecimal getStateWithholdingAmount()

    public long getWithholdingHistoryPK()
    {
        return withholdingHistoryVO.getWithholdingHistoryPK();
    }
     //-- long getWithholdingHistoryPK()

    public void setCityWithholdingAmount(EDITBigDecimal cityWithholdingAmount)
    {
        withholdingHistoryVO.setCityWithholdingAmount(SessionHelper.getEDITBigDecimal(cityWithholdingAmount));
    }
     //-- void setCityWithholdingAmount(java.math.BigDecimal)

    public void setCountyWithholdingAmount(EDITBigDecimal countyWithholdingAmount)
    {
        withholdingHistoryVO.setCountyWithholdingAmount(SessionHelper.getEDITBigDecimal(countyWithholdingAmount));
    }
     //-- void setCountyWithholdingAmount(java.math.BigDecimal)

    public void setFICA(EDITBigDecimal FICA)
    {
        withholdingHistoryVO.setFICA(SessionHelper.getEDITBigDecimal(FICA));
    }
     //-- void setFICA(java.math.BigDecimal)

    public void setFederalWithholdingAmount(EDITBigDecimal federalWithholdingAmount)
    {
        withholdingHistoryVO.setFederalWithholdingAmount(SessionHelper.getEDITBigDecimal(federalWithholdingAmount));
    }
     //-- void setFederalWithholdingAmount(java.math.BigDecimal)

    public void setStateWithholdingAmount(EDITBigDecimal stateWithholdingAmount)
    {
        withholdingHistoryVO.setStateWithholdingAmount(SessionHelper.getEDITBigDecimal(stateWithholdingAmount));
    }
     //-- void setStateWithholdingAmount(java.math.BigDecimal)

    public void setWithholdingHistoryPK(long withholdingHistoryPK)
    {
        withholdingHistoryVO.setWithholdingHistoryPK(withholdingHistoryPK);
    }
     //-- void setWithholdingHistoryPK(long)

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
        if (withholdingHistoryVO == null)
        {
            withholdingHistoryVO = new WithholdingHistoryVO();
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
        return withholdingHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return withholdingHistoryVO.getWithholdingHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.withholdingHistoryVO = (WithholdingHistoryVO) voObject;
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

    public WithholdingHistoryVO getAsVO()
    {
        return withholdingHistoryVO;
    }

    /**
     * Finder.
     *
     * @param withholdingHistoryPK
     */
    public static final WithholdingHistory findByPK(long withholdingHistoryPK)
    {
        WithholdingHistory withholdingHistory = null;

        WithholdingHistoryVO[] withholdingHistoryVOs = new WithholdingHistoryDAO().findByPK(withholdingHistoryPK);

        if (withholdingHistoryVOs != null)
        {
            withholdingHistory = new WithholdingHistory(withholdingHistoryVOs[0]);
        }

        return withholdingHistory;
    }

    /**
     * Finder.
     *
     * @param editTrxHistoryPK
     */
    public static final WithholdingHistory findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        WithholdingHistory withholdingHistory = null;

        WithholdingHistoryVO[] withholdingHistoryVOs = new WithholdingHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryPK);

        if (withholdingHistoryVOs != null)
        {
            withholdingHistory = new WithholdingHistory(withholdingHistoryVOs[0]);
        }

        return withholdingHistory;
    }

    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(withholdingHistoryVO.getEDITTrxHistoryFK());
    }
     //-- long getEDITTrxHistoryFK() 

    public void setEDITTrxHistoryFK(Long EDITTrxHistoryFK)
    {
        withholdingHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(EDITTrxHistoryFK));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, WithholdingHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, WithholdingHistory.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return WithholdingHistory.DATABASE;
    }
}
