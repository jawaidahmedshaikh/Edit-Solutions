/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 21, 2003
 * Time: 8:49:24 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package search.command.lookup;

import edit.common.vo.*;
import fission.utility.Util;

import java.util.ArrayList;

import client.dm.dao.*;

public class FindAgentByClientNameDOBCmd extends AbstractSearchCmd {

    public FindAgentByClientNameDOBCmd() {
    }

    public Object exec()
    {
        AgentSearchResponseVO agentSearchResponseVO = null;

        ClientDetailVO[] clientDetailVOs = null;

        String[] nameElements = Util.fastTokenizer(super.getSearchRequestVO().getName(), ",");
        String dateOfBirth = super.getSearchRequestVO().getDateOfBirth();

        if (nameElements.length == 2){

            if (nameElements[1].equals("")){

//                clientDetailVOs = super.getClientLookup().findClientDetailByLastNameDOB(nameElements[0].trim(),
//                                                                                         dateOfBirth,
//                                                                                          false,
//                                                                                           new ArrayList());
                clientDetailVOs = DAOFactory.getClientDetailDAO().findByLastName_AND_BirthDateWithoutOvrds(nameElements[0].trim(),
                        dateOfBirth, false, new ArrayList());
            }
            else {

//                clientDetailVOs = super.getClientLookup().findClientDetailByLastNamePartialFirstNameDOB(nameElements[0].trim(),
//                                                                                                         nameElements[1].trim(),
//                                                                                                          dateOfBirth,
//                                                                                                           false,
//                                                                                                            new ArrayList());
                clientDetailVOs = DAOFactory.getClientDetailDAO().findByLastNamePartialFirstName_AND_BirthDateWithoutOvrds(nameElements[0].trim(),
                        nameElements[1].trim(), dateOfBirth, false, new ArrayList());
            }
        }
        else if (nameElements.length == 1){

//            clientDetailVOs = super.getClientLookup().findClientDetailByPartialLastNameDOB(nameElements[0].trim(),
//                                                                                            dateOfBirth,
//                                                                                             false, new ArrayList());
            clientDetailVOs = DAOFactory.getClientDetailDAO().findByPartialLastName_AND_BirthDateWithoutOvrds(nameElements[0].trim(),
                    dateOfBirth, false, new ArrayList());
        }

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
