/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 21, 2003
 * Time: 9:00:06 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.*;
import fission.utility.Util;

import java.util.ArrayList;

import client.dm.dao.*;

public class FindAgentByTaxIdCmd extends AbstractSearchCmd {

    public FindAgentByTaxIdCmd() {
    }

    public Object exec()
    {
        AgentSearchResponseVO agentSearchResponseVO = null;

        ClientDetailVO[] clientDetailVOs = null;

        String taxId = super.getSearchRequestVO().getTaxId();

//        clientDetailVOs = super.getClientLookup().findClientDetailByTaxId(taxId,
//                                                                           false,
//                                                                            new ArrayList());
        clientDetailVOs = DAOFactory.getClientDetailDAO().findByTaxIdWithoutOvrds(taxId, false, new ArrayList());

        if (clientDetailVOs != null) {

            // Sort the clients first
            clientDetailVOs = (ClientDetailVO[]) Util.sortObjects(clientDetailVOs, new String[]{"getLastName", "getFirstName", "getClientDetailPK"});

            agentSearchResponseVO = new AgentSearchResponseVO();

            for (int i = 0; i < clientDetailVOs.length; i++){

                ClientAgentVO clientAgentVO = new ClientAgentVO();
                clientAgentVO.setClientDetailVO(clientDetailVOs[i]);

                long clientDetailPK = clientDetailVOs[i].getClientDetailPK();
                ClientRoleVO[] clientRoleVOs = super.getRoleLookup().getRolesByClientDetailFK(clientDetailPK);

                if (clientRoleVOs != null) {

                    for (int r = 0; r < clientRoleVOs.length; r++) {

                        String roleType = clientRoleVOs[r].getRoleTypeCT();

                        if (roleType.equals("Agent")) {

                            AgentVO[] agentVOs = super.getAgent().composeAgentVOByRolePK(clientRoleVOs[r].getClientRolePK(), new ArrayList());
                            if (agentVOs != null) {

                                clientAgentVO.setAgentVO(agentVOs);
                            }
                        }
                    }

                    agentSearchResponseVO.addClientAgentVO(clientAgentVO);
                }

            }
        }

        return agentSearchResponseVO;
    }
}
