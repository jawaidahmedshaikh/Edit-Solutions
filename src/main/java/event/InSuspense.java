/*
 * User: gfrosti
 * Date: May 12, 2005
 * Time: 1:17:29 PM
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



public class InSuspense extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private InSuspenseVO inSuspenseVO;
    private EDITTrxHistory editTrxHistory;
    private Suspense suspense;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Suspense getSuspense()
    {
        return suspense;
    }

    public void setSuspense(Suspense suspense)
    {
        this.suspense = suspense;
    }

    /**
     * Instantiates a InSuspense entity with a default InSuspenseVO.
     */
    public InSuspense()
    {
        init();
    }

    /**
     * Instantiates a InSuspense entity with a supplied InSuspenseVO.
     *
     * @param inSuspenseVO
     */
    public InSuspense(InSuspenseVO inSuspenseVO)
    {
        init();

        this.inSuspenseVO = inSuspenseVO;
    }

    public EDITBigDecimal getAmount()
    {
        return SessionHelper.getEDITBigDecimal(inSuspenseVO.getAmount());
    }

    //-- java.math.BigDecimal getAmount() 
    public long getInSuspensePK()
    {
        return inSuspenseVO.getInSuspensePK();
    }

    //-- long getInSuspensePK() 
    public void setInSuspensePK(long inSuspensePK)
    {
        inSuspenseVO.setInSuspensePK(inSuspensePK);
    }

    //-- void setInSuspensePK(long) 
    public Long getSuspenseFK()
    {
        return SessionHelper.getPKValue(inSuspenseVO.getSuspenseFK());
    }

    //-- long getSuspenseFK() 
    public void setSuspenseFK(Long suspenseFK)
    {
        inSuspenseVO.setSuspenseFK(SessionHelper.getPKValue(suspenseFK));
    }

    //-- void setSuspenseFK(long) 

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
        if (inSuspenseVO == null)
        {
            inSuspenseVO = new InSuspenseVO();
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
        return inSuspenseVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return inSuspenseVO.getInSuspensePK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.inSuspenseVO = (InSuspenseVO) voObject;
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

    public InSuspenseVO getAsVO()
    {
        return inSuspenseVO;
    }

    /**
     * Finder.
     *
     * @param inSuspensePK
     */
    public static final InSuspense findByPK(long inSuspensePK)
    {
        InSuspense inSuspense = null;

        InSuspenseVO[] inSuspenseVOs = new event.dm.dao.InSuspenseDAO().findByPK(inSuspensePK);

        if (inSuspenseVOs != null)
        {
            inSuspense = new InSuspense(inSuspenseVOs[0]);
        }

        return inSuspense;
    }

    /**
     * Setter.
     * @param checkAmount
     */
    public void setAmount(EDITBigDecimal checkAmount)
    {
        this.inSuspenseVO.setAmount(SessionHelper.getEDITBigDecimal(checkAmount));
    }

    public void associate(EDITTrxHistory editTrxHistory, Suspense suspense)
    {
        this.inSuspenseVO.setEDITTrxHistoryFK(editTrxHistory.getPK());

        this.inSuspenseVO.setSuspenseFK(suspense.getPK());
    }

    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(inSuspenseVO.getEDITTrxHistoryFK());
    }

    public void setEDITTrxHistoryFK(Long EDITTrxHistoryFK)
    {
        inSuspenseVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(EDITTrxHistoryFK));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, InSuspense.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, InSuspense.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return InSuspense.DATABASE;
    }
}
