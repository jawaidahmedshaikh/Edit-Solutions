/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 16, 2003
 * Time: 1:36:55 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.*;

import java.util.ArrayList;
import java.util.List;

import role.ClientRole;
import client.ClientDetail;

public class FindByAgentIdCmd extends AbstractSearchCmd {

    public FindByAgentIdCmd() {

    }

    public Object exec()
    {
        AgentSearchResponseVO agentSearchResponseVO = null;

        String agentId = super.getSearchRequestVO().getAgentId();

        List voInclusionList = new ArrayList();

        AgentVO agentVO = super.getAgent().composeAgentByAgentNumber(agentId, voInclusionList);
        ClientDetailVO clientDetailVO = null;
        if (agentVO != null)
        {
            ClientRole[] clientRoles = ClientRole.findByAgentFK(new Long(agentVO.getAgentPK()));
            clientDetailVO = (ClientDetailVO) clientRoles[0].getClientDetail().getVO();
        }

        if (clientDetailVO != null)
        {
            agentSearchResponseVO = new AgentSearchResponseVO();

            ClientAgentVO clientAgentVO = new ClientAgentVO();
            clientAgentVO.addAgentVO(agentVO);
            clientAgentVO.setClientDetailVO(clientDetailVO);

            agentSearchResponseVO.addClientAgentVO(clientAgentVO);
        }

        return agentSearchResponseVO;
    }
}
