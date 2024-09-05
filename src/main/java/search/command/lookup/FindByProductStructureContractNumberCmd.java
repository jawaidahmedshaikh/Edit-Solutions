/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 16, 2002
 * Time: 10:19:07 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.SearchResponseVO;
import search.dm.dao.FastDAO;

public class FindByProductStructureContractNumberCmd extends AbstractSearchCmd  {

    public Object exec()
    {
        String contractNumber = super.getSearchRequestVO().getContractNumber();
        long productStructurePK = super.getSearchRequestVO().getProductStructurePK();

        FastDAO fastLookup = new FastDAO();
        SearchResponseVO[]   searchResponseVOs = fastLookup.findByProductStructureContractNumber(productStructurePK, contractNumber);

//       //On Contract Only Search only return one entry regardless of roleType values
//        SearchResponseVO[] searchVO = new SearchResponseVO[1];
//        searchVO[0] = searchResponseVOs[0];
//        searchVO[0].setRoleType(null);

        return searchResponseVOs;
    }
}
