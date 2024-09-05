/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 15, 2002
 * Time: 3:11:33 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.component;

import edit.common.vo.SearchRequestVO;
import edit.common.vo.SearchResponseVO;
import edit.services.command.Command;
import edit.services.component.AbstractLookupComponent;
import search.business.Lookup;
import search.command.lookup.AbstractSearchCmd;
import fission.global.AppReqBlock;
import fission.utility.Util;

public class LookupComponent extends AbstractLookupComponent implements Lookup {

    private static final String lookupCmdPackage = "search.command.lookup";

    public SearchResponseVO[] searchForClients(AppReqBlock appReqBlock) throws Exception
    {
        String searchCommand = appReqBlock.getReqParm("searchType");

        SearchRequestVO searchRequestVO = new SearchRequestVO();

        String productStructurePK = appReqBlock.getReqParm("companyStructurePK");
        if (productStructurePK == null || !Util.isANumber(productStructurePK)) {

            productStructurePK = "0";
        }
        searchRequestVO.setProductStructurePK(Long.parseLong(productStructurePK));
        searchRequestVO.setContractNumber(appReqBlock.getReqParm("contractNumber"));
        String clientName = appReqBlock.getReqParm("clientName");
        clientName = Util.substitute(clientName, "'", "''");
        searchRequestVO.setName(clientName);
        searchRequestVO.setTaxId(appReqBlock.getReqParm("taxId"));
        searchRequestVO.setAgentId(appReqBlock.getReqParm("agentId"));

        SearchResponseVO[] searchResponseVOs = executeSearch(searchCommand, searchRequestVO);
        
        return searchResponseVOs;
    }

    public SearchResponseVO[] executeSearch(String lookupCmd, SearchRequestVO searchRequestVO)
    {
        SearchResponseVO[] result = null;

        try
        {
            Command command = (Command) Class.forName(lookupCmdPackage + "." + lookupCmd).newInstance();

            ((AbstractSearchCmd) command).setSearchRequestVO(searchRequestVO);

            result = (SearchResponseVO[]) command.exec();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            RuntimeException e2 = new RuntimeException(e);

            e2.setStackTrace(e.getStackTrace());

            throw e2;
        }

        return result;
    }
}
