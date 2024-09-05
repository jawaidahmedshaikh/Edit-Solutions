/*
 * LookupComponent.java      Version 1.0  09/24/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package role.component;

import edit.common.vo.ClientRoleVO;
import edit.services.component.AbstractLookupComponent;
import role.business.Lookup;
import role.dm.composer.ClientRoleComposer;
import role.dm.dao.DAOFactory;
import role.dm.dao.FastDAO;

import java.util.List;

/**
 * The Role request controller
 */
public class LookupComponent extends AbstractLookupComponent implements Lookup
{

    public ClientRoleVO[] getRolesByClientDetailFK(long clientDetailFK)
    {

        return DAOFactory.getClientRoleDAO().findByClientDetailFK(clientDetailFK, false, null);
    }

    public ClientRoleVO[] getRoleByClientRolePK(long clientRolePK) throws Exception
    {

        return DAOFactory.getClientRoleDAO().findByClientRolePK(clientRolePK, false, null);
    }

    public ClientRoleVO[] getRoleByRoleTypeClientDetailFKStatusReferenceID(String roleType,
                                                                           long clientDetailFK,
                                                                           String overrideStatus,
                                                                           String referenceID) throws Exception
    {

        return DAOFactory.getClientRoleDAO().findByRoleTypeClientDetailFKStatus(roleType,
                                                                                clientDetailFK,
                                                                                overrideStatus);
    }

    public ClientRoleVO[] getRoleByAllKeysAndRoleType(long clientDetailFK,
                                                       long bankAcctInfoFK,
                                                        long preferenceFK,
                                                         long taxProfileFK,
                                                          String roleType,
                                                           boolean includeChildVOs,
                                                            List voExclusionVector)
                                                     throws Exception {

        return DAOFactory.getClientRoleDAO().findByAllKeysAndRoleType(clientDetailFK,
                                                                       bankAcctInfoFK,
                                                                        preferenceFK,
                                                                         taxProfileFK,
                                                                          roleType,
                                                                           includeChildVOs,
                                                                            voExclusionVector);
    }

    public long[] findClientRolePKsByClientRolePKAndRoleType(long clientRolePK, String roleTypeCT) throws Exception
    {
        return new FastDAO().findClientRolePKsByClientRolePKAndRoleType(clientRolePK, roleTypeCT);
    }

    public ClientRoleVO[] findClientRoleVOByClientRolePK(long clientRolePK, boolean includeChildVOs, List voInclusionList) throws Exception
    {
        return DAOFactory.getClientRoleDAO().findByClientRolePK(clientRolePK, false, null);
    }

    public ClientRoleVO composeClientRoleVO(long clientRolePK, List voInclusionList) throws Exception
    {
        return new ClientRoleComposer(voInclusionList).compose(clientRolePK);
    }

    public ClientRoleVO composerClientRoleVO(ClientRoleVO clientRoleVO, List voInclusionList) throws Exception
    {
        new ClientRoleComposer(voInclusionList).compose(clientRoleVO);

        return clientRoleVO;
    }

    public ClientRoleVO[] composeRoleByAgentFK(long agentFK, List voInclusionList) throws Exception
    {
        ClientRoleVO[] clientRoleVOs = DAOFactory.getClientRoleDAO().findByAgentFK(agentFK, false, null);
        for (int i = 0; i < clientRoleVOs.length; i++)
        {
            new ClientRoleComposer(voInclusionList).compose(clientRoleVOs[i]);
        }

        return clientRoleVOs;
    }

    public ClientRoleVO findClientRoleVOByClientDetailPKANDRoleType(long clientDetailPK, String roleType) throws Exception
    {
        ClientRoleVO clientRoleVO = null;

        ClientRoleVO[] clientRoleVOs = DAOFactory.getClientRoleDAO().findByClientRolePKAndRoleType(clientDetailPK, roleType, false, null);

        if (clientRoleVOs != null)
        {
            clientRoleVO = clientRoleVOs[0];
        }

        return clientRoleVO;
    }
}