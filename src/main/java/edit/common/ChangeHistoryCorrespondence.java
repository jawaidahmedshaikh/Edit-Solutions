/**
 * 
 * User: cgleason
 * Date: Jan 18, 2005
 * Time: 4:51:24 PM
 * 
 * (c) 2000 - 2005 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 */

package edit.common;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.common.vo.VOObject;
import edit.common.vo.ChangeHistoryCorrespondenceVO;

public class ChangeHistoryCorrespondence implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private ChangeHistoryCorrespondenceVO changeHistoryCorrespondenceVO;

    /**
     * Instantiates a ChangeHistoryCorrespondence entity with a default ChangeHistoryCorrespondenceVO.
     */
    public ChangeHistoryCorrespondence()
    {
        init();
    }

    /**
     * Instantiates a ChangeHistoryCorrespondence entity with a supplied ChangeHistoryCorrespondenceVO.
     *
     * @param changeHistoryCorrespondenceVO
     */
    public ChangeHistoryCorrespondence(ChangeHistoryCorrespondenceVO changeHistoryCorrespondenceVO)
    {
        init();

        this.changeHistoryCorrespondenceVO = changeHistoryCorrespondenceVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (changeHistoryCorrespondenceVO == null)
        {
            changeHistoryCorrespondenceVO = new ChangeHistoryCorrespondenceVO();
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
        return changeHistoryCorrespondenceVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return changeHistoryCorrespondenceVO.getChangeHistoryCorrespondencePK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.changeHistoryCorrespondenceVO = (ChangeHistoryCorrespondenceVO) voObject;
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
     * @param changeHistoryCorrespondencePK
     */
    public static final ChangeHistoryCorrespondence findByPK(long changeHistoryCorrespondencePK)
    {
//        ChangeHistoryCorrespondence changeHistoryCorrespondence = null;
//
//        ChangeHistoryCorrespondenceVO[] changeHistoryCorrespondenceVOs = new ChangeHistoryCorrespondenceDAO().findByPK(changeHistoryCorrespondencePK);
//
//        if (changeHistoryCorrespondenceVOs != null)
//        {
//            changeHistoryCorrespondence = new ChangeHistoryCorrespondence(changeHistoryCorrespondenceVOs[0]);
//        }
//
//        return changeHistoryCorrespondence;
        return null;
    }
}
