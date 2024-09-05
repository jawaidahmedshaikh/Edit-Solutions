package contract;

import billing.BillSchedule;
import edit.common.EDITBigDecimal;
import edit.common.vo.SegmentSecondaryVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

public class SegmentSecondary extends HibernateEntity implements CRUDEntity {

	private CRUDEntityImpl crudEntityImpl;
	private Segment segmentParent;
	private Segment segmentChild;
	private SegmentSecondaryVO segmentSecondaryVO;
	
	/**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
	
    public SegmentSecondary() {
    	init();
    }
    
    public SegmentSecondary(SegmentSecondaryVO segmentSecondaryVO)                
    {
        init();

        this.segmentSecondaryVO = segmentSecondaryVO;
    }
    
    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (segmentSecondaryVO == null)
        {
        	segmentSecondaryVO = new SegmentSecondaryVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }
    
    public Long getSegmentSecondaryPK()
    {
        return SessionHelper.getPKValue(segmentSecondaryVO.getSegmentSecondaryPK());
    }

    public void setSegmentSecondaryPK(Long segmentSecondaryPK)
    {
    	segmentSecondaryVO.setSegmentSecondaryPK(SessionHelper.getPKValue(segmentSecondaryPK));
    }
    
    public Long getSegmentParentFK()
    {
        return SessionHelper.getPKValue(segmentSecondaryVO.getSegmentParentFK());
    }

    public void setSegmentParentFK(Long segmentParentFK)
    {
    	segmentSecondaryVO.setSegmentParentFK(SessionHelper.getPKValue(segmentParentFK));
    }
    
    public Long getSegmentChildFK()
    {
        return SessionHelper.getPKValue(segmentSecondaryVO.getSegmentChildFK());
    }

    public void setSegmentChildFK(Long segmentChildFK)
    {
    	segmentSecondaryVO.setSegmentChildFK(SessionHelper.getPKValue(segmentChildFK));
    }
    
    public EDITBigDecimal getAnnualPremium()
    {
        return SessionHelper.getEDITBigDecimal(segmentSecondaryVO.getAnnualPremium());
    }

    public void setAnnualPremium(EDITBigDecimal annualPremium)
    {
    	segmentSecondaryVO.setAnnualPremium(SessionHelper.getEDITBigDecimal(annualPremium));
    }
    
    public String getLocation()
    {
        return segmentSecondaryVO.getLocation();
    }
    
    public void setLocation(String location)
    {
    	segmentSecondaryVO.setLocation(location);
    }
    
    public String getSequence()
    {
        return segmentSecondaryVO.getSequence();
    }
    
    public void setSequence(String sequence)
    {
    	segmentSecondaryVO.setSequence(sequence);
    }
    
    public void setSegmentParent(Segment segmentParent)
    {
        this.segmentParent = segmentParent;

        if (segmentParent != null)
        {
            this.setSegmentParentFK(segmentParent.getSegmentPK());
        }
    }
    
    public Segment getSegmentParent()
    {
        return this.segmentParent;
    }
    
    public void setSegmentChild(Segment segmentChild)
    {
        this.segmentChild = segmentChild;

        if (segmentChild != null)
        {
            this.setSegmentChildFK(segmentChild.getSegmentPK());
        }
    }
    
    public Segment getSegmentChild()
    {
        return this.segmentChild;
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
        return segmentSecondaryVO;
    }
    
    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.segmentSecondaryVO = (SegmentSecondaryVO) voObject;
    }
    
    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return segmentSecondaryVO.getSegmentSecondaryPK();
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
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return SegmentSecondary.DATABASE;
    }

    public SegmentSecondaryVO getAsVO()
    {
        return segmentSecondaryVO;
    }
}
