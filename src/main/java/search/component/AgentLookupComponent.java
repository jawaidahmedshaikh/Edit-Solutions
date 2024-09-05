/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 20, 2003
 * Time: 3:20:31 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.component;

import edit.common.vo.AgentSearchResponseVO;
import edit.common.vo.SearchRequestVO;
import edit.services.command.Command;
import edit.services.component.AbstractLookupComponent;
import search.business.AgentLookup;
import search.command.lookup.AbstractSearchCmd;

public class AgentLookupComponent extends AbstractLookupComponent implements AgentLookup {

    private static final String lookupCmdPackage = "search.command.lookup";

    public AgentSearchResponseVO executeSearch(String lookupCmd, SearchRequestVO searchRequestVO) throws Exception {

        Command command = (Command) Class.forName(lookupCmdPackage + "." + lookupCmd).newInstance();

        ((AbstractSearchCmd) command).setSearchRequestVO(searchRequestVO);

        AgentSearchResponseVO result = (AgentSearchResponseVO) command.exec();

        return result;
    }
}
