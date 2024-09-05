/*
 * LookupComponent.java      Version 1.0  09/24/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package client.component;

import client.business.Lookup;
import client.dm.composer.*;
import client.dm.dao.DAOFactory;
import client.*;
import edit.common.vo.*;
import edit.services.component.AbstractLookupComponent;
import edit.services.db.*;

import java.util.List;

/**
 * The Client Engine request controller
 */
public class LookupComponent extends AbstractLookupComponent implements Lookup
{

    public ClientDetailVO[] getClientByClientPK(long clientPK) throws Exception
    {
        return DAOFactory.getClientDetailDAO().findByClientPK(clientPK);
    }

    public ClientDetailVO[] getClientByClientId(String clientId) throws Exception
    {
        return DAOFactory.getClientDetailDAO().findByClientId(clientId);
    }

    public ClientDetailVO[] getClientDetailByClientId(String clientId,
                                                       boolean includeChildVOs,
                                                        List voExclusionList) throws Exception {

        return DAOFactory.getClientDetailDAO().findByClientIdentification(clientId,
                                                                           includeChildVOs,
                                                                            voExclusionList);
    }

    public ClientDetailVO[] getClientByTaxId(String taxId,
                                              boolean includeChildVOs,
                                               List voExclusionList) throws Exception {

        return DAOFactory.getClientDetailDAO().findByTaxId(taxId, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByClientPK(long clientPK,
                                                        boolean includeChildVOs,
                                                         List voExclusionList) {

        return DAOFactory.getClientDetailDAO().findByClientPK(clientPK, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByLastName(String lastName, boolean includeChildVOs, List voExclusionList)
    {
        return DAOFactory.getClientDetailDAO().findByLastName(lastName, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByLastNamePartialFirstName(String lastName, String partialFirstName, boolean includeChildVOs, List voExclusionList)
    {
        return DAOFactory.getClientDetailDAO().findByLastNamePartialFirstName(lastName, partialFirstName, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByPartialLastName(String paritalLastName, boolean includeChildVOs, List voExclusionList)
    {
        return DAOFactory.getClientDetailDAO().findByPartialLastName(paritalLastName, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByLastNameDOB(String lastName,
                                                           String dob,
                                                            boolean includeChildVOs,
                                                             List voExclusionList){

        return DAOFactory.getClientDetailDAO().findByLastName_AND_BirthDate(lastName,
                                                                             dob,
                                                                              includeChildVOs,
                                                                               voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByLastNamePartialFirstNameDOB(String lastName,
                                                                           String partialFirstName,
                                                                            String dob,
                                                                             boolean includeChildVOs,
                                                                              List voExclusionList){

        return DAOFactory.getClientDetailDAO().findByLastNamePartialFirstName_AND_BirthDate(lastName,
                                                                                             partialFirstName,
                                                                                              dob,
                                                                                               includeChildVOs,
                                                                                                voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByPartialLastNameDOB(String lastName,
                                                                  String dob,
                                                                   boolean includeChildVOs,
                                                                    List voExclusionList){

        return DAOFactory.getClientDetailDAO().findByLastName_AND_BirthDate(lastName,
                                                                             dob,
                                                                              includeChildVOs,
                                                                               voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByTaxId(String taxId, boolean includeChildVOs, List voExclusionList)
    {
        return DAOFactory.getClientDetailDAO().findByTaxId(taxId, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO[] findClientDetailByAgentId(String agentId, boolean includeChildVOs, List voExclusionList) throws Exception
    {
        return DAOFactory.getClientDetailDAO().findByAgentId(agentId, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO[] findByClientPK(long clientPK, boolean includeChildVOs, List voExclusionList)
    {
        return DAOFactory.getClientDetailDAO().findByClientPK(clientPK, includeChildVOs, voExclusionList);
    }

    public ClientDetailVO composeClientDetailVO(long clientDetailPK, List voInclusionList ) throws Exception
    {
        return new ClientDetailComposer(voInclusionList).compose(clientDetailPK);
    }

    public PreferenceVO composePrimaryPreference(long clientDetailPK, List voInclusionList) throws Exception
    {
        return new PreferenceComposer(voInclusionList).composePrimaryPreference(clientDetailPK);
    }

    public TaxProfileVO composePrimaryTaxProfile(long taxInformationPK, List voInclusionList) throws Exception
    {
        return new TaxProfileComposer(voInclusionList).composePrimaryTaxProfile(taxInformationPK);
    }

    /**
     * @see Lookup#findClientAddessBy_AddressType(long, String)
     * @param clientDetailPK
     * @param addressTypeCT
     * @return
     */
    public ClientAddressVO findClientAddessBy_AddressType(long clientDetailPK, String addressTypeCT)
    {
        ClientAddressVO primaryAddressVO = null;

        ClientAddress clientAddress = ClientAddress.findByClientDetailPK_And_AddressTypeCT(clientDetailPK,
                                                                                           addressTypeCT);

        if (clientAddress != null)
        {
            primaryAddressVO = (ClientAddressVO) clientAddress.getVO();
        }

        return primaryAddressVO;
    }

    /**
     * Finder.
     * @param partialCorporateName
     * @return
     */
    public ClientDetailVO[] findClientDetailsBy_PartialCorporateName(String partialCorporateName)
    {
        return (ClientDetailVO[]) CRUDEntityImpl.mapEntityToVO(ClientDetail.findBy_PartialCorporateName(partialCorporateName), ClientDetailVO.class);
    }

    /**
     * Finder.
     * @see
     * @param segmentPK
     * @param roleTypeCT
     * @return
     */
    public ClientDetailVO[] findClientDetailsBy_SegmentPK_RoleType(long segmentPK, String roleTypeCT)
    {
        ClientDetailVO[] clientDetailVOs = null;

        ClientDetail[] clientDetails = ClientDetail.findBy_SegmentPK_RoleType(segmentPK, roleTypeCT);

        return (ClientDetailVO[]) CRUDEntityImpl.mapEntityToVO(clientDetails, ClientDetailVO.class);
    }

    /**
     * Finder.
     * @param reinsurerPK
     * @return
     */
    public ClientDetailVO findClientDetailBy_ReinsurerPK(long reinsurerPK)
    {
        ClientDetailVO clientDetailVO = null;

        ClientDetail clientDetail = ClientDetail.findBy_ReinsurerPK(reinsurerPK);

        if (clientDetail != null)
        {
            clientDetailVO = (ClientDetailVO) clientDetail.getVO();
        }

        return clientDetailVO;
    }

    /**
     * Finder method for ClientDetail
     * @param taxId
     * @return
     */
    public ClientDetailVO[] findClientDetailBy_TaxId(String taxId)
    {
        ClientDetailVO[] clientDetailVOs = null;

        ClientDetail[] clientDetails = ClientDetail.findBy_TaxId(taxId);

        if(clientDetails != null)
        {
            clientDetailVOs = (ClientDetailVO[]) CRUDEntityImpl.mapEntityToVO(clientDetails, ClientDetailVO.class);
        }

        return clientDetailVOs;
    }

    /**
     * Finder method for ClientDetail
     * @param name
     * @return
     */
    public ClientDetailVO[] findClientDetailBy_Name(String name)
    {
        ClientDetailVO[] clientDetailVOs = null;

        ClientDetail[] clientDetails = ClientDetail.findBy_Name(name);

        if(clientDetails != null)
        {
            clientDetailVOs = (ClientDetailVO[]) CRUDEntityImpl.mapEntityToVO(clientDetails, ClientDetailVO.class);
        }

        return clientDetailVOs;
    }

    public ClientDetailVO findByClientRolePK(long contractRolePK)
    {
        ClientRoleVO[] clientRoleVOs = role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(contractRolePK, false, null);
        ClientRoleVO clientRoleVO = null;
        ClientDetailVO clientDetailVO = null;
        if (clientRoleVOs != null)
        {
            clientRoleVO = clientRoleVOs[0];

            ClientDetailVO[] clientDetailVOs = DAOFactory.getClientDetailDAO().findByClientDetailPK(clientRoleVO.getClientDetailFK(), false, null);
            if (clientDetailVOs != null)
            {
                clientDetailVO = clientDetailVOs[0];
                clientDetailVO.addClientRoleVO(clientRoleVO);
            }
        }

        return  clientDetailVO;
    }

    public ClientDetailVO[] findClientDetailBy_NameForRefund(String name)
    {
        ClientDetailVO[] clientDetailVOs = null;

        ClientDetail[] clientDetails = ClientDetail.findBy_NameForRefund(name);

        if(clientDetails != null)
        {
            clientDetailVOs = (ClientDetailVO[]) CRUDEntityImpl.mapEntityToVO(clientDetails, ClientDetailVO.class);
        }

        return clientDetailVOs;
    }
}