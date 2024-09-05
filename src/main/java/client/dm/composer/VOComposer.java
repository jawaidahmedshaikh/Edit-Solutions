package client.dm.composer;

import client.dm.dao.DAOFactory;
import edit.common.vo.*;
import fission.utility.Util;

import java.util.List;




/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 15, 2003
 * Time: 9:19:59 AM
 * To change this template use Options | File Templates.
 */
public class VOComposer
{
    public ClientDetailVO composeClientDetailVO(long clientDetailPK, long preferencePK, long taxProfilePK, List voInclusionList) throws Exception
    {
        ClientDetailVO[] clientDetailVO = DAOFactory.getClientDetailDAO().findByClientDetailPK(clientDetailPK, false, null);

        ClientDetailComposer composer = new ClientDetailComposer(voInclusionList);

        PreferenceVO[] preferenceVO = null;

        if (voInclusionList.contains(PreferenceVO.class))
        {
            preferenceVO = DAOFactory.getPreferenceDAO().findByPreferencePK(preferencePK, false, null);

            composer.substitutePreferenceVO(preferenceVO);
        }

        TaxInformationVO[] taxInformationVO = null;

        if (voInclusionList.contains(TaxInformationVO.class))
        {
            taxInformationVO = DAOFactory.getTaxInformationDAO().findByTaxProfilePK(taxProfilePK, false, null);

            composer.substituteTaxInformationVO(taxInformationVO);
        }

        TaxProfileVO[] taxProfileVO = null;

        if (voInclusionList.contains(TaxProfileVO.class))
        {
            taxProfileVO = DAOFactory.getTaxProfileDAO().findByTaxProfilePK(taxProfilePK, false, null);

            composer.substituteTaxProfileVO(taxProfileVO);
        }

        composer.compose(clientDetailVO[0]);

        return clientDetailVO[0];
    }

    public ClientDetailVO[] composeClientDetailVO(String taxId, String name, String dateOfBirth, String agentId, String addressTypeCT, List voInclusionList) throws Exception
    {
        ClientDetailVO[] clientDetailVO = null;

        ClientDetailComposer composer = new ClientDetailComposer(voInclusionList);

        // Four cases:

        // Case 1: TaxId only
        if (taxId != null && name == null && dateOfBirth == null && agentId == null)
        {
            clientDetailVO = DAOFactory.getClientDetailDAO().findByTaxId(taxId, false, null);
        }

        // Case 2: Name Only
        else if (taxId == null && name != null && dateOfBirth == null && agentId == null)
        {
            String[] nameTokens = Util.fastTokenizer(name, ",");

            if (nameTokens.length == 1) // Partial last name only
            {
                clientDetailVO = DAOFactory.getClientDetailDAO().findByPartialLastName(nameTokens[0], false, null);
            }
            if (nameTokens.length == 2 && nameTokens[1].length() == 0) // Last name exact
            {
                clientDetailVO = DAOFactory.getClientDetailDAO().findByLastName(nameTokens[0], false, null);
            }
            if (nameTokens.length == 2 && nameTokens[1].length() > 0) // Last name and partial first
            {
                clientDetailVO = DAOFactory.getClientDetailDAO().findByLastNamePartialFirstName(nameTokens[0], nameTokens[1], false, null);
            }
        }

        // Case 3: Name and Date of Birth
        else if (taxId == null && name != null && dateOfBirth != null && agentId == null)
        {
            String[] nameTokens = Util.fastTokenizer(name, ",");

            if (nameTokens.length == 1) // Partial last name only
            {
                clientDetailVO = DAOFactory.getClientDetailDAO().findByPartialLastName_AND_BirthDate(nameTokens[0], dateOfBirth, false, null);
            }
            if (nameTokens.length == 2 && nameTokens[1].length() == 0) // Last name exact
            {
                clientDetailVO = DAOFactory.getClientDetailDAO().findByLastName_AND_BirthDate(nameTokens[0], dateOfBirth, false, null);
            }
            if (nameTokens.length == 2 && nameTokens[1].length() > 0) // Last name and partial first
            {
                clientDetailVO = DAOFactory.getClientDetailDAO().findByLastNamePartialFirstName_AND_BirthDate(nameTokens[0], nameTokens[1], dateOfBirth, false, null);
            }
        }

        // Case 4: ClientId
        else if (taxId == null && name == null && dateOfBirth == null && agentId != null)
        {
            clientDetailVO = DAOFactory.getClientDetailDAO().findByClientIdentification(agentId, false, null);
        }

        else
        {
            throw new Exception("Invalid Agent Search Criteria");
        }

        // Get the Addresses
        if (clientDetailVO != null)
        {
            for (int i = 0; i < clientDetailVO.length; i++)
            {
                ClientAddressVO[] clientAddressVO = DAOFactory.getClientAddressDAO().findByClientDetailPK_AND_AddressTypeCT(clientDetailVO[i].getClientDetailPK(), "PrimaryAddress", false, null);

                composer.substituteClientAddressVO(clientAddressVO);

                composer.compose(clientDetailVO[i]);
            }
        }

        return clientDetailVO;
    }
}
