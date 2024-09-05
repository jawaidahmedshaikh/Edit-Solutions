/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 15, 2002
 * Time: 3:11:25 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.business;

import edit.common.vo.SearchRequestVO;
import edit.common.vo.SearchResponseVO;
import edit.services.component.ILookup;
import fission.global.AppReqBlock;

public interface Lookup extends ILookup {

    public static final String FIND_BY_CLIENT_NAME = "FindByClientNameCmd";
    public static final String FIND_BY_PRODUCT_STRUCTURE_CONTRACT_NUMBER = "FindByProductStructureContractNumberCmd";
    public static final String FIND_BY_TAX_ID = "FindByTaxIdCmd";
    public static final String FIND_BY_AGENT_ID = "FindByAgentIdCmd";
    public static final String FIND_BY_CONTRACT_NUMBER_ONLY = "FindByContractNumberOnlyCmd";

    public SearchResponseVO[] searchForClients(AppReqBlock appReqBlock) throws Exception;

    public SearchResponseVO[] executeSearch(String searchCommand, SearchRequestVO searchRequestVO);
}
