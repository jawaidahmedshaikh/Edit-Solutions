/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import contract.dm.dao.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.*;



public class AnnualizedSubBucket extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private AnnualizedSubBucketVO annualizedSubBucketVO;
    private Bucket bucket;
    private EDITTrx editTrx;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a AnnualizedSubBucket entity with a default AnnualizedSubBucketVO.
     */
    public AnnualizedSubBucket()
    {
        init();
    }

    /**
     * Instantiates a AnnualizedSubBucket entity with a AnnualizedSubBucketVO retrieved from persistence.
     *
     * @param annualizedSubBucketPK
     */
    public AnnualizedSubBucket(long annualizedSubBucketPK)
    {
        init();

        crudEntityImpl.load(this, annualizedSubBucketPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a AnnualizedSubBucket entity with a supplied AnnualizedSubBucketVO.
     *
     * @param annualizedSubBucketVO
     */
    public AnnualizedSubBucket(AnnualizedSubBucketVO annualizedSubBucketVO)
    {
        init();

        this.annualizedSubBucketVO = annualizedSubBucketVO;
    }

    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    public Long getAnnualizedSubBucketPK()
    {
        return SessionHelper.getPKValue(annualizedSubBucketVO.getAnnualizedSubBucketPK());
    }

    //-- long getAnnualizedSubBucketPK()
    public Long getEDITTrxFK()
    {
        return SessionHelper.getPKValue(annualizedSubBucketVO.getEDITTrxFK());
    }

    //-- long getEDITTrxFK()
    public EDITDate getSBBaseEndDate()
    {
        return SessionHelper.getEDITDate(annualizedSubBucketVO.getSBBaseEndDate());
    }

    //-- java.lang.String getSBBaseEndDate()
    public EDITBigDecimal getSBBaseRate()
    {
        return SessionHelper.getEDITBigDecimal(annualizedSubBucketVO.getSBBaseRate());
    }

    //-- java.math.BigDecimal getSBBaseRate()
    public EDITDate getSBCurrentEndDate()
    {
        return SessionHelper.getEDITDate(annualizedSubBucketVO.getSBCurrentEndDate());
    }

    //-- java.lang.String getSBCurrentEndDate()
    public EDITBigDecimal getSBCurrentRate()
    {
        return SessionHelper.getEDITBigDecimal(annualizedSubBucketVO.getSBCurrentRate());
    }

    //-- java.math.BigDecimal getSBCurrentRate()
    public EDITDate getSBEffDate()
    {
        return SessionHelper.getEDITDate(annualizedSubBucketVO.getSBEffDate());
    }

    //-- java.lang.String getSBEffDate()
    public EDITBigDecimal getSBFundValue()
    {
        return SessionHelper.getEDITBigDecimal(annualizedSubBucketVO.getSBFundValue());
    }

    //-- java.math.BigDecimal getSBFundValue()
    public EDITDate getSBGuarMinEndDate1()
    {
        return SessionHelper.getEDITDate(annualizedSubBucketVO.getSBGuarMinEndDate1());
    }

    //-- java.lang.String getSBGuarMinEndDate1()
    public EDITDate getSBGuarMinEndDate2()
    {
        return SessionHelper.getEDITDate(annualizedSubBucketVO.getSBGuarMinEndDate2());
    }

    //-- java.lang.String getSBGuarMinEndDate2()
    public EDITBigDecimal getSBGuarMinRate1()
    {
        return SessionHelper.getEDITBigDecimal(annualizedSubBucketVO.getSBGuarMinRate1());
    }

    //-- java.math.BigDecimal getSBGuarMinRate1()
    public EDITBigDecimal getSBGuarMinRate2()
    {
        return SessionHelper.getEDITBigDecimal(annualizedSubBucketVO.getSBGuarMinRate2());
    }

    //-- java.math.BigDecimal getSBGuarMinRate2()
    public int getSBNumberBuckets()
    {
        return annualizedSubBucketVO.getSBNumberBuckets();
    }

    //-- int getSBNumberBuckets()
    public void setEDITTrxFK(Long EDITTrxFK)
    {
        annualizedSubBucketVO.setEDITTrxFK(SessionHelper.getPKValue(EDITTrxFK));
    }

    //-- void setEDITTrxFK(long)
    public void setAnnualizedSubBucketPK(Long annualizedSubBucketPK)
    {
        annualizedSubBucketVO.setAnnualizedSubBucketPK(SessionHelper.getPKValue(annualizedSubBucketPK));
    }

    //-- void setAnnualizedSubBucketPK(long)
    public void setSBBaseEndDate(EDITDate SBBaseEndDate)
    {
        annualizedSubBucketVO.setSBBaseEndDate(SessionHelper.getEDITDate(SBBaseEndDate));
    }

    //-- void setSBBaseEndDate(java.lang.String)
    public void setSBBaseRate(EDITBigDecimal SBBaseRate)
    {
        annualizedSubBucketVO.setSBBaseRate(SessionHelper.getEDITBigDecimal(SBBaseRate));
    }

    //-- void setSBBaseRate(java.math.BigDecimal)
    public void setSBCurrentEndDate(EDITDate SBCurrentEndDate)
    {
        annualizedSubBucketVO.setSBCurrentEndDate(SessionHelper.getEDITDate(SBCurrentEndDate));
    }

    //-- void setSBCurrentEndDate(java.lang.String)
    public void setSBCurrentRate(EDITBigDecimal SBCurrentRate)
    {
        annualizedSubBucketVO.setSBCurrentRate(SessionHelper.getEDITBigDecimal(SBCurrentRate));
    }

    //-- void setSBCurrentRate(java.math.BigDecimal)
    public void setSBEffDate(EDITDate SBEffDate)
    {
        annualizedSubBucketVO.setSBEffDate(SessionHelper.getEDITDate(SBEffDate));
    }

    //-- void setSBEffDate(java.lang.String)
    public void setSBFundValue(EDITBigDecimal SBFundValue)
    {
        annualizedSubBucketVO.setSBFundValue(SessionHelper.getEDITBigDecimal(SBFundValue));
    }

    //-- void setSBFundValue(java.math.BigDecimal)
    public void setSBGuarMinEndDate1(EDITDate SBGuarMinEndDate1)
    {
        annualizedSubBucketVO.setSBGuarMinEndDate1(SessionHelper.getEDITDate(SBGuarMinEndDate1));
    }

    //-- void setSBGuarMinEndDate1(java.lang.String)
    public void setSBGuarMinEndDate2(EDITDate SBGuarMinEndDate2)
    {
        annualizedSubBucketVO.setSBGuarMinEndDate2(SessionHelper.getEDITDate(SBGuarMinEndDate2));
    }

    //-- void setSBGuarMinEndDate2(java.lang.String)
    public void setSBGuarMinRate1(EDITBigDecimal SBGuarMinRate1)
    {
        annualizedSubBucketVO.setSBGuarMinRate1(SessionHelper.getEDITBigDecimal(SBGuarMinRate1));
    }

    //-- void setSBGuarMinRate1(java.math.BigDecimal)
    public void setSBGuarMinRate2(EDITBigDecimal SBGuarMinRate2)
    {
        annualizedSubBucketVO.setSBGuarMinRate2(SessionHelper.getEDITBigDecimal(SBGuarMinRate2));
    }

    //-- void setSBGuarMinRate2(java.math.BigDecimal)
    public void setSBNumberBuckets(int SBNumberBuckets)
    {
        annualizedSubBucketVO.setSBNumberBuckets(SBNumberBuckets);
    }

    //-- void setSBNumberBuckets(int)
    public Bucket getBucket()
    {
        return bucket;
    }

    public void setBucket(Bucket bucket)
    {
        this.bucket = bucket;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (annualizedSubBucketVO == null)
        {
            annualizedSubBucketVO = new AnnualizedSubBucketVO();
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
        return annualizedSubBucketVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return annualizedSubBucketVO.getAnnualizedSubBucketPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.annualizedSubBucketVO = (AnnualizedSubBucketVO) voObject;
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

    public static AnnualizedSubBucket[] findByEDITTrxFK(long editTrxFK) throws Exception
    {
        AnnualizedSubBucketVO[] bucketVO = DAOFactory.getBucketDAO().findSubBucketByEDITTrxFK(editTrxFK);

        return mapVOToEntity(bucketVO);
    }

    public static AnnualizedSubBucket[] mapVOToEntity(AnnualizedSubBucketVO[] annualizedSubBucketvos)
    {
        AnnualizedSubBucket[] annualizedSubBucket = null;

        if (annualizedSubBucketvos != null)
        {
            annualizedSubBucket = new AnnualizedSubBucket[annualizedSubBucketvos.length];

            for (int i = 0; i < annualizedSubBucketvos.length; i++)
            {
                annualizedSubBucket[i] = new AnnualizedSubBucket(annualizedSubBucketvos[i]);
            }
        }

        return annualizedSubBucket;
    }

    public static long getNextAvailableKey()
    {
        return CRUD.getNextAvailableKey();
    }

    public Long getBucketFK()
    {
        return SessionHelper.getPKValue(annualizedSubBucketVO.getBucketFK());
    }

    //-- long getBucketFK()
    public void setBucketFK(Long bucketFK)
    {
        annualizedSubBucketVO.setBucketFK(SessionHelper.getPKValue(bucketFK));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, AnnualizedSubBucket.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, AnnualizedSubBucket.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AnnualizedSubBucket.DATABASE;
    }
}
