/*
 * Lookup.java      Version 1.00  09/24/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package role.business;

import edit.common.vo.ClientRoleVO;
import edit.services.component.ILookup;

import java.util.List;

public interface Lookup extends ILookup
{
    public abstract ClientRoleVO[] getRolesByClientDetailFK(long clientDetailFK);

    public abstract ClientRoleVO[] getRoleByClientRolePK(long clientRolePK) throws Exception;

    public abstract ClientRoleVO[] getRoleByRoleTypeClientDetailFKStatusReferenceID(String roleType,
                                                                                    long clientDetailFK,
                                                                                    String overrideStatus,
                                                                                    String referenceID) throws Exception;

    public abstract ClientRoleVO[] getRoleByAllKeysAndRoleType(long clientDetailFK,
                                                                long bankAcctInfoFK,
                                                                 long preferenceFK,
                                                                  long taxProfileFK,
                                                                   String roleType,
                                                                    boolean includeChildVOs,
                                                                     List voExclusionVector)
                                                              throws Exception;

    public long[] findClientRolePKsByClientRolePKAndRoleType(long clientRolePK, String roleTypeCT) throws Exception;

    public ClientRoleVO composeClientRoleVO(long clientRolePK, List voInclusionList) throws Exception;

    public ClientRoleVO composerClientRoleVO(ClientRoleVO clientRoleVO, List voInclusionList) throws Exception;

    public ClientRoleVO[] composeRoleByAgentFK(long agentFK, List voInclusionList) throws Exception;

    public ClientRoleVO[] findClientRoleVOByClientRolePK(long clientRolePK, boolean includeChildVOs, List voInclusionList) throws Exception;

    /**
     * Returns the unqique ClientRoleVO identified by the clientDetailPK and roleType, or null if none found.
     * @param clientDetailPK
     * @param roleType
     * @return
     */
    public ClientRoleVO findClientRoleVOByClientDetailPKANDRoleType(long clientDetailPK, String roleType) throws Exception;
}