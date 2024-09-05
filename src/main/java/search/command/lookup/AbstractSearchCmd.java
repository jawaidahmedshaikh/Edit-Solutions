/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 15, 2002
 * Time: 3:55:51 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.SearchRequestVO;
import edit.services.command.Command;

public abstract class AbstractSearchCmd implements Command {

    private client.business.Lookup  clientLookup = null;
    private contract.business.Lookup contractLookup = null;
    private role.business.Lookup roleLookup = null;
    private agent.business.Agent agent = null;
    private SearchRequestVO searchRequestVO;

    public AbstractSearchCmd(){

        clientLookup = new client.component.LookupComponent();
        contractLookup = new contract.component.LookupComponent();
        roleLookup = new role.component.LookupComponent();
        agent = new agent.component.AgentComponent();
    }

    public client.business.Lookup getClientLookup(){

        return clientLookup;
    }

    public contract.business.Lookup getContractLookup(){

        return contractLookup;
    }

    public role.business.Lookup getRoleLookup(){

        return roleLookup;
    }

    public agent.business.Agent getAgent(){

        return agent;
    }

    public void setSearchRequestVO(SearchRequestVO searchRequestVO){

        this.searchRequestVO = searchRequestVO;
    }

    public SearchRequestVO getSearchRequestVO(){

        return searchRequestVO;
    }
}
