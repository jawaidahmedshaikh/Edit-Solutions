/**

 * User: dlataill

 * Date: Oct 15, 2004

 * Time: 3:43:12 PM

 *

 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved

 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is

 * subject to the license agreement. 

 */
package engine.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.*;

import java.util.List;
import java.util.ArrayList;

/**
 * User: dlataill
 * Date: Oct 15, 2004
 * Time: 3:43:12 PM
 * <p/>
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
public class ProductFilteredFundStructureComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public ProductFilteredFundStructureComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public void compose(ProductFilteredFundStructureVO productFilteredFundStructureVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            if (voInclusionList.contains(FilteredFundVO.class)) associateFilteredFundVO(productFilteredFundStructureVO);
            if (voInclusionList.contains(ProductStructureVO.class)) associateProductStructureVO(productFilteredFundStructureVO);
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

    private void associateFilteredFundVO(ProductFilteredFundStructureVO productFilteredFundStructureVO)
    {
        if (productFilteredFundStructureVO.getFilteredFundFK() != 0)
        {
            voInclusionList.remove(ProductFilteredFundStructureVO.class);

            FilteredFundVO filteredFundVO = new FilteredFundComposer(voInclusionList).compose(productFilteredFundStructureVO.getFilteredFundFK());

            productFilteredFundStructureVO.setParentVO(FilteredFundVO.class, filteredFundVO);

            filteredFundVO.addProductFilteredFundStructureVO(productFilteredFundStructureVO);

            voInclusionList.add(ProductFilteredFundStructureVO.class);
        }
    }

    private void associateProductStructureVO(ProductFilteredFundStructureVO productFilteredFundStructureVO)
    {
        if (productFilteredFundStructureVO.getProductStructureFK() != 0)
        {
            voInclusionList.remove(ProductFilteredFundStructureVO.class);

            ProductStructureVO[] productStructureVO = engine.dm.dao.DAOFactory.getProductStructureDAO().findByPK(productFilteredFundStructureVO.getProductStructureFK(), false, new ArrayList());

            productFilteredFundStructureVO.setParentVO(ProductStructureVO.class, productStructureVO[0]);

            productStructureVO[0].addProductFilteredFundStructureVO(productFilteredFundStructureVO);

            voInclusionList.add(ProductFilteredFundStructureVO.class);
        }
    }
}
