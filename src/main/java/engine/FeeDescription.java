/*
 * User: sprasad
 * Date: Jan 10, 2005
 * Time: 5:01:54 PM
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
import edit.common.vo.FeeDescriptionVO;
import edit.common.exceptions.EDITEngineException;
import engine.dm.dao.FeeDescriptionDAO;

public class FeeDescription implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private FeeDescriptionVO feeDescriptionVO;

    /**
     * Instantiates a FeeDescription entity with a default FeeDescriptionVO.
     */
    public FeeDescription()
    {
        init();
    }

    /**
     * Instantiates a FeeDescription entity with a supplied FeeDescriptionVO.
     *
     * @param feeDescriptionVO
     */
    public FeeDescription(FeeDescriptionVO feeDescriptionVO)
    {
        init();

        this.feeDescriptionVO = feeDescriptionVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (feeDescriptionVO == null)
        {
            feeDescriptionVO = new FeeDescriptionVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save() throws EDITEngineException
    {
        if (feeDescriptionVO.getPricingTypeCT().equalsIgnoreCase("HedgeFund"))
        {
            if (feeDescriptionVO.getClientDetailFK() == 0)
            {
                throw new EDITEngineException("Client needs to be associated for [PricingType = 'HedgeFund']");
            }
        }

        crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITEngineException
    {
        Fee[] fees = Fee.findByFeeDescriptionPK(feeDescriptionVO.getFeeDescriptionPK());

        if (fees != null)
        {
            throw new EDITEngineException("Can not Delete Fee Description - Fee Assocociations Exist");
        }

        crudEntityImpl.delete(this, ConnectionFactory.ENGINE_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return feeDescriptionVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return feeDescriptionVO.getFeeDescriptionPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.feeDescriptionVO = (FeeDescriptionVO) voObject;
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

    /************************************** Static Methods **************************************/

    /**
     * Finder method by PK.
     *
     * @param feeDescriptionPK
     */
    public static final FeeDescription findByPK(long feeDescriptionPK)
    {
        FeeDescription feeDescription = null;

        FeeDescriptionVO[] feeDescriptionVOs = new FeeDescriptionDAO().findByPK(feeDescriptionPK);

        if (feeDescriptionVOs != null)
        {
            feeDescription = new FeeDescription(feeDescriptionVOs[0]);
        }

        return feeDescription;
    }

    /**
     * Finder method by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public static final FeeDescription[] findByFilteredFundPK(long filteredFundPK)
    {
        FeeDescriptionVO[] feeDescriptionVOs = new FeeDescriptionDAO().findByFilteredFundPK(filteredFundPK);

        return (FeeDescription[]) CRUDEntityImpl.mapVOToEntity(feeDescriptionVOs, FeeDescription.class);
    }

    /**
     * Finder method by filteredFundPK and feeTypeCT
     * @param filteredFundPK
     * @param feeTypeCT
     * @return
     */
    public static final FeeDescription findByFilteredFundPK_And_FeeTypeCT(long filteredFundPK, String feeTypeCT)
    {
        FeeDescription feeDescription = null;

        FeeDescriptionVO[] feeDescriptionVOs = new FeeDescriptionDAO().findByFilteredFundPK_And_FeeTypeCT(filteredFundPK, feeTypeCT);

        if (feeDescriptionVOs != null)
        {
            feeDescription = new FeeDescription(feeDescriptionVOs[0]);
        }

        return feeDescription;
    }
}
