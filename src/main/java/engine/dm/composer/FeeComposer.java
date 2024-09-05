/**
 * User: dlataill
 * Date: Mar 18, 2005
 * Time: 1:14:37 PM
 * 
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.CRUD;
import edit.common.vo.*;

import java.util.List;

import engine.dm.dao.FeeDescriptionDAO;

public class FeeComposer  extends Composer
{
    private CRUD crud;

    List voInclusionList;

    public FeeComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public FeeVO compose(long feePK)
    {
        FeeVO feeVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            feeVO = (FeeVO) crud.retrieveVOFromDB(FeeVO.class, feePK);

            compose(feeVO);
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return feeVO;
    }

    public void compose(FeeVO feeVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            if (voInclusionList.contains(FilteredFundVO.class)) associateFilteredFundVO(feeVO);
            if (voInclusionList.contains(FeeDescriptionVO.class)) associateFeeDescriptionVO(feeVO);
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void associateFilteredFundVO(FeeVO feeVO)
    {
        voInclusionList.remove(FeeVO.class);

        FilteredFundVO filteredFundVO = new FilteredFundComposer(voInclusionList).compose(feeVO.getFilteredFundFK());

        feeVO.setParentVO(FilteredFundVO.class, filteredFundVO);

        filteredFundVO.addFeeVO(feeVO);

        voInclusionList.add(FeeVO.class);
    }

    private void associateFeeDescriptionVO(FeeVO feeVO)
    {
        voInclusionList.remove(FeeVO.class);

        FeeDescriptionVO[] feeDescriptionVO = new FeeDescriptionDAO().findByPK(feeVO.getFeeDescriptionFK());

        if (feeDescriptionVO != null)
        {
            feeVO.setParentVO(FeeDescriptionVO.class, feeDescriptionVO[0]);

            feeDescriptionVO[0].addFeeVO(feeVO);
        }

        voInclusionList.add(FeeVO.class);
    }
}
