/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 15, 2002
 * Time: 3:49:31 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.*;

import fission.utility.*;

import search.dm.dao.*;


public class FindByClientNameCmd extends AbstractSearchCmd
{
    public FindByClientNameCmd()
    {
    }

    public Object exec()
    {
        String[] nameElements = Util.fastTokenizer(super.getSearchRequestVO().getName(), ",");

        FastDAO fastLookup = new FastDAO();
        SearchResponseVO[] searchResponseVOs = null;

        if ((nameElements.length == 2) && (!nameElements[1].equals("")))
        {
            searchResponseVOs = fastLookup.findByLastNamePartialFirstName(nameElements[0].trim(), nameElements[1].trim());
        }
        else
        {
            searchResponseVOs = fastLookup.findByPartialLastName(nameElements[0].trim());
        }

        if (searchResponseVOs != null)
        {
            // Sort the clients first
            searchResponseVOs = (SearchResponseVO[]) Util.sortObjects(searchResponseVOs,
                                 new String[] {"getClientName", "getClientDetailFK"});

            //Sort the contracts (if there are any)
            for (int i = 0; i < searchResponseVOs.length; i++)
            {
                if (searchResponseVOs[i].getSearchResponseContractInfoCount() > 0)
                {
                    SearchResponseContractInfo[] searchRespContractInfo = searchResponseVOs[i].getSearchResponseContractInfo();

                    searchRespContractInfo = (SearchResponseContractInfo[]) Util.sortObjects(searchRespContractInfo,
                                         new String[] {"getContractNumber"});

                    searchResponseVOs[i].removeAllSearchResponseContractInfo();
                    searchResponseVOs[i].setSearchResponseContractInfo(searchRespContractInfo);
                }
            }
        }

        return searchResponseVOs;
    }
}
