/*
 * User: sprasad
 * Date: Jan 10, 2005
 * Time: 5:03:39 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.common.vo.VOObject;
import edit.common.vo.FeeCorrespondenceVO;
import engine.dm.dao.FeeCorrespondenceDAO;

public class FeeCorrespondence implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private FeeCorrespondenceVO feeCorrespondenceVO;

    /**
     * Instantiates a FeeCorrespondence entity with a default FeeCorrespondenceVO.
     */
    public FeeCorrespondence()
    {
        init();
    }

    /**
     * Instantiates a FeeCorrespondence entity with a supplied FeeCorrespondenceVO.
     *
     * @param feeCorrespondenceVO
     */
    public FeeCorrespondence(FeeCorrespondenceVO feeCorrespondenceVO)
    {
        init();

        this.feeCorrespondenceVO = feeCorrespondenceVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (feeCorrespondenceVO == null)
        {
            feeCorrespondenceVO = new FeeCorrespondenceVO();
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
        // statusCT takes two values 'P' or 'H'
        // 'P' -- pending
        // 'H' -- history
        crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.ENGINE_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return feeCorrespondenceVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return feeCorrespondenceVO.getFeeCorrespondencePK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.feeCorrespondenceVO = (FeeCorrespondenceVO) voObject;
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

    public void associateFee(Fee fee)
    {
        feeCorrespondenceVO.setFeeFK(fee.getPK());
    }

    /************************************** Static Methods **************************************/

    /**
     * Finder.
     *
     * @param feeCorrespondencePK
     */
    public static final FeeCorrespondence findByPK(long feeCorrespondencePK)
    {
        FeeCorrespondence feeCorrespondence = null;

        FeeCorrespondenceVO[] feeCorrespondenceVOs = new FeeCorrespondenceDAO().findByPK(feeCorrespondencePK);

        if (feeCorrespondenceVOs != null)
        {
            feeCorrespondence = new FeeCorrespondence(feeCorrespondenceVOs[0]);
        }

        return feeCorrespondence;
    }

    /**
     * Finder method by feeFk.
     * @param feePK
     * @return
     */
    public static final FeeCorrespondence findByFeePK(long feePK)
    {
        FeeCorrespondence feeCorrespondence = null;

        FeeCorrespondenceVO[] feeCorrespondenceVOs = new FeeCorrespondenceDAO().findByFeePK(feePK);

        if (feeCorrespondenceVOs != null)
        {
            // there should be only one feeCorrespondence for fee.
            feeCorrespondence = new FeeCorrespondence(feeCorrespondenceVOs[0]);
        }

        return feeCorrespondence;
    }
}
