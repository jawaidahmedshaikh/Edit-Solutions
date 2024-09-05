/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 15, 2002
 * Time: 3:49:31 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.SearchResponseVO;
import fission.utility.Util;
import search.dm.dao.FastDAO;

public class FindByTaxIdCmd extends AbstractSearchCmd {

    public FindByTaxIdCmd() {
    }

    public Object exec()
    {
        String taxId = super.getSearchRequestVO().getTaxId();

        FastDAO f = new FastDAO();
        SearchResponseVO[] searchResponseVOs = f.findClientsAndSegmentsByTaxID(taxId);

        if (searchResponseVOs != null) {

            // Sort by clients and contract number
            searchResponseVOs = (SearchResponseVO[]) Util.sortObjects(searchResponseVOs, new String[]{"getClientName", "getClientDetailFK"});
        }

        return searchResponseVOs;
    }
}
