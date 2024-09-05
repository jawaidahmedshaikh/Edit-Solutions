/*
 * Lookup.java      Version 1.00  09/24/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package client.business;

import edit.common.vo.*;
import edit.services.component.ILookup;

import java.util.List;

public interface Lookup extends ILookup {

	public abstract ClientDetailVO[] getClientByClientPK(long clientPK) throws Exception;

    public abstract ClientDetailVO[] getClientByClientId(String clientId) throws Exception;

    public abstract ClientDetailVO[] getClientDetailByClientId(String clientId,
                                                                boolean includeChildVOs,
                                                                 List voExclusionList) throws Exception;

    public abstract ClientDetailVO[] getClientByTaxId(String taxId,
                                                       boolean includeChildVOs,
                                                        List voExclusionList) throws Exception;

    public ClientDetailVO[] findClientDetailByClientPK(long clientPK,
                                                        boolean includeChildVOs,
                                                         List voExclusionList);

    public ClientDetailVO[] findClientDetailByLastName(String lastName, boolean includeChildVOs, List voExclusionList);

    public ClientDetailVO[] findClientDetailByLastNamePartialFirstName(String lastName, String partialFirstName, boolean includeChildVOs, List voExclusionList);

    public ClientDetailVO[] findClientDetailByPartialLastName(String paritalLastName, boolean includeChildVOs, List voExclusionList);

    public ClientDetailVO[] findClientDetailByLastNameDOB(String lastName,
                                                           String dob,
                                                           boolean includeChildVOs,
                                                            List voExclusionList);

    public ClientDetailVO[] findClientDetailByLastNamePartialFirstNameDOB(String lastName,
                                                                           String partialFirstName,
                                                                            String dob,
                                                                             boolean includeChildVOs,
                                                                              List voExclusionList);

    public ClientDetailVO[] findClientDetailByPartialLastNameDOB(String lastName,
                                                                  String dob,
                                                                   boolean includeChildVOs,
                                                                    List voExclusionList);

    public ClientDetailVO[] findClientDetailByTaxId(String taxId, boolean includeChildVOs, List voExclusionList);

    public ClientDetailVO[] findClientDetailByAgentId(String agentId, boolean includeChildVOs, List voExclusionList) throws Exception;

    public ClientDetailVO[] findByClientPK(long clientPK, boolean includeChildVOs, List voExclusionList);

    public ClientDetailVO composeClientDetailVO(long clientDetailPK, List voInclusionList ) throws Exception;

    public PreferenceVO composePrimaryPreference(long clientDetailPK, List voInclusionList) throws Exception;

    public TaxProfileVO composePrimaryTaxProfile(long taxInformationPK, List voInclusionList) throws Exception;

    /**
     * Finder.
     * @param clientDetailPK
     * @param addressTypeCT
     * @return
     */
    public ClientAddressVO findClientAddessBy_AddressType(long clientDetailPK, String addressTypeCT);

    /**
     * Finder.
     * @param partialCorporateName
     * @return
     */
    public ClientDetailVO[] findClientDetailsBy_PartialCorporateName(String partialCorporateName);

    /**
     * Finds the set of ClientDetails for a given Segment by its roleTypeCT.
     * @param segmentPK
     * @param roleTypeCT
     * @return
     */
    public ClientDetailVO[] findClientDetailsBy_SegmentPK_RoleType(long segmentPK, String roleTypeCT);

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public ClientDetailVO findClientDetailBy_ReinsurerPK(long reinsurerPK);

    /**
     * Finder method for ClientDetail
     * @param taxId
     * @return
     */
    public ClientDetailVO[] findClientDetailBy_TaxId(String taxId);

    /**
     * Finder method for ClientDetail
     * @param name
     * @return
     */
    public ClientDetailVO[] findClientDetailBy_Name(String name);

    /**
     * Finder metjod for ClientDetail
     * @param contractClientPK
     * @return
     */
    public ClientDetailVO findByClientRolePK(long contractRolePK);

    public ClientDetailVO[] findClientDetailBy_NameForRefund(String name);
}