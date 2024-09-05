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


public class FindByContractNumberOnlyCmd extends AbstractSearchCmd
{

    public Object exec()
    {
        String contractNumber = super.getSearchRequestVO().getContractNumber();

        FastDAO fastLookup = new FastDAO();
        SearchResponseVO[]   searchResponseVOs = fastLookup.findByContractNumber(contractNumber);

//        //On Contract Only Search only return one entry regardless of roleType values
//        SearchResponseVO searchVO = null;
//        searchVO = searchResponseVOs[0];
//        searchVO.setRoleType(null);

        return searchResponseVOs;
    }
}
