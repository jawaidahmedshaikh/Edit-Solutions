/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 21, 2003
 * Time: 8:13:54 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.*;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;

import client.dm.dao.*;

public class FindAgentByClientNameCmd extends AbstractSearchCmd {

    public FindAgentByClientNameCmd() {
    }

    public Object exec()
    {
        AgentSearchResponseVO agentSearchResponseVO = null;

        ClientDetailVO[] clientDetailVOs = null;

        String[] nameElements = Util.fastTokenizer(super.getSearchRequestVO().getName(), ",");

        if (nameElements.length == 2){

            if (nameElements[1].equals("")){

//                clientDetailVOs = super.getClientLookup().findClientDetailByLastName(nameElements[0].trim(), false, new ArrayList());
                clientDetailVOs = DAOFactory.getClientDetailDAO().findByLastNameWithoutOvrds(nameElements[0].trim(), false, new ArrayList());

            }
            else {

//                clientDetailVOs = super.getClientLookup().findClientDetailByLastNamePartialFirstName(nameElements[0].trim(), nameElements[1].trim(), false, new ArrayList());
                clientDetailVOs = DAOFactory.getClientDetailDAO().findByLastNamePartialFirstNameWithoutOvrds(nameElements[0].trim(), nameElements[1].trim(), false, new ArrayList());
            }
        }
        else if (nameElements.length == 1){

            String partialLastName = nameElements[0].trim();
            if(partialLastName.length()>0)
            {
//                clientDetailVOs = super.getClientLookup().findClientDetailByPartialLastName(partialLastName, false, new ArrayList());
                clientDetailVOs = DAOFactory.getClientDetailDAO().findByPartialLastNameWithoutOvrds(partialLastName, false, new ArrayList());
            }
        }

        agentSearchResponseVO = new AgentSearchResponseVO();

        if (clientDetailVOs != null) {

            // Sort the clients first
            clientDetailVOs = (ClientDetailVO[]) Util.sortObjects(clientDetailVOs, new String[]{"getLastName", "getFirstName", "getClientDetailPK"});

            for (int i = 0; i < clientDetailVOs.length; i++){

                ClientAgentVO clientAgentVO = new ClientAgentVO();
//                clientAgentVO.setClientDetailVO(clientDetailVOs[i]);

                long clientDetailPK = clientDetailVOs[i].getClientDetailPK();
                ClientRoleVO[] clientRoleVOs = super.getRoleLookup().getRolesByClientDetailFK(clientDetailPK);

                if (clientRoleVOs != null) {

                    for (int r = 0; r < clientRoleVOs.length; r++) {

                        String roleType = clientRoleVOs[r].getRoleTypeCT();

                        if (roleType.equals("Agent"))
                        {
                            List agentVOList = new ArrayList();
                            try
                            {
                                AgentVO agentVO = super.getAgent().composeAgentVO(clientRoleVOs[r].getAgentFK(), new ArrayList());
                                if (agentVO != null)
                                {
                                    agentVOList.add(agentVO);
                                }
                            }
                            catch (Exception e)
                            {

                            }

                            if (agentVOList.size() > 0)
                            {
                                clientAgentVO.setClientDetailVO(clientDetailVOs[i]);
                                clientAgentVO.setAgentVO((AgentVO[]) agentVOList.toArray(new AgentVO[agentVOList.size()]));
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
