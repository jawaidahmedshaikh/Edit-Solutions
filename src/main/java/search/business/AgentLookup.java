/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 20, 2003
 * Time: 3:18:41 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.business;

import edit.common.vo.AgentSearchResponseVO;
import edit.common.vo.SearchRequestVO;
import edit.services.component.ILookup;

public interface AgentLookup extends ILookup {

    public static final String FIND_AGENT_BY_CLIENT_NAME = "FindAgentByClientNameCmd";
    public static final String FIND_AGENT_BY_CLIENT_NAME_DOB = "FindAgentByClientNameDOBCmd";
    public static final String FIND_AGENT_BY_TAX_ID = "FindAgentByTaxIdCmd";
    public static final String FIND_BY_AGENT_ID = "FindByAgentIdCmd";

    public AgentSearchResponseVO executeSearch(String searchCommand, SearchRequestVO searchRequestVO) throws Exception;
}
