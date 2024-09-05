/*
 * User: cgleason
 * Date: Jan 13, 2005
 * Time: 12:40:56 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package contract;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import java.util.*;



public class InherentRider extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private InherentRiderVO inherentRiderVO;
    private Segment segment;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a InherentRider entity with a default InherentRiderVO.
     */
    public InherentRider()
    {
        init();
    }

    /**
     * Instantiates a InherentRider entity with a supplied InherentRiderVO.
     *
     * @param inherentRiderVO
     */
    public InherentRider(InherentRiderVO inherentRiderVO)
    {
        init();

        this.inherentRiderVO = inherentRiderVO;
    }

    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(inherentRiderVO.getSegmentFK());
    }
     //-- long getSegmentFK() 

    public void setSegmentFK(Long segmentFK)
    {
        inherentRiderVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }
     //-- void setSegmentFK(long) 

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    public String getInherentRiderTypeCT()
    {
        return inherentRiderVO.getInherentRiderTypeCT();
    }

    //-- java.lang.String getInherentRiderTypeCT() 
    public Long getInherentRiderPK()
    {
        return SessionHelper.getPKValue(inherentRiderVO.getInherentRiderPK());
    }

    //-- long getInherentRiderPK() 
    public EDITDate getRiderEffectiveDate()
    {
        return SessionHelper.getEDITDate(inherentRiderVO.getRiderEffectiveDate());
    }

    //-- java.lang.String getRiderEffectiveDate() 
    public EDITBigDecimal getRiderPercent()
    {
        return SessionHelper.getEDITBigDecimal(inherentRiderVO.getRiderPercent());
    }

    //-- java.math.BigDecimal getRiderPercent() 
    public String getRiderStatusCT()
    {
        return inherentRiderVO.getRiderStatusCT();
    }

    public EDITBigDecimal getAmount()
    {
        return SessionHelper.getEDITBigDecimal(inherentRiderVO.getAmount());
    }

    public void setInherentRiderPK(Long inherentRiderPK)
    {
        inherentRiderVO.setInherentRiderPK(SessionHelper.getPKValue(inherentRiderPK));
    }

    //-- void setInherentRiderPK(long) 
    public void setInherentRiderTypeCT(String inherentRiderTypeCT)
    {
        inherentRiderVO.setInherentRiderTypeCT(inherentRiderTypeCT);
    }

    //-- void setInherentRiderTypeCT(java.lang.String) 
    public void setRiderEffectiveDate(EDITDate riderEffectiveDate)
    {
        inherentRiderVO.setRiderEffectiveDate(SessionHelper.getEDITDate(riderEffectiveDate));
    }

    //-- void setRiderEffectiveDate(java.lang.String) 
    public void setRiderPercent(EDITBigDecimal riderPercent)
    {
        inherentRiderVO.setRiderPercent(SessionHelper.getEDITBigDecimal(riderPercent));
    }

    //-- void setRiderPercent(java.math.BigDecimal) 
    public void setRiderStatusCT(String riderStatusCT)
    {
        inherentRiderVO.setRiderStatusCT(riderStatusCT);
    }

    public void setAmount(EDITBigDecimal amount)
    {
        inherentRiderVO.setAmount(SessionHelper.getEDITBigDecimal(amount));
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (inherentRiderVO == null)
        {
            inherentRiderVO = new InherentRiderVO();
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
        return inherentRiderVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return inherentRiderVO.getInherentRiderPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.inherentRiderVO = (InherentRiderVO) voObject;
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
     * Finder.
     *
     * @param inherentRiderPK
     */
    public static final InherentRider findByPK(long inherentRiderPK)
    {
        //        InherentRider inherentRider = null;
        //
        //        InherentRiderVO[] inherentRiderVOs = new InherentRiderDAO().findByPK(inherentRiderPK);
        //
        //        if (inherentRiderVOs != null)
        //        {
        //            inherentRider = new InherentRider(inherentRiderVOs[0]);
        //        }
        //
        //        return inherentRider;
        return null;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, InherentRider.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, InherentRider.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return InherentRider.DATABASE;
    }

    public static InherentRider[] findBySegmentFK(Long segmentFK)
    {
        String hql = " select inherentRider from InherentRider inherentRider" +
                     " where inherentRider.SegmentFK = :segmentFK";

        EDITMap params = new EDITMap("segmentFK", segmentFK);

        List<InherentRider> results = SessionHelper.executeHQL(hql, params, InherentRider.DATABASE);

        return (InherentRider[]) results.toArray(new InherentRider[results.size()]);
    }
}
