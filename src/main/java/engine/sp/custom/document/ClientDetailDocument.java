/*
 * User: gfrosti
 * Date: Jan 1, 2007
 * Time: 10:31:22 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp.custom.document;

import client.ClientAddress;
import client.ClientDetail;
import client.Preference;
import client.TaxInformation;
import client.TaxProfile;

import contract.ContractClient;
import contract.ContractClientAllocation;
import contract.Withholding;

import edit.common.EDITDate;
import edit.common.EDITMap;

import edit.services.db.CRUD;

import edit.services.db.hibernate.SessionHelper;

import event.ClientSetup;
import event.ContractClientAllocationOvrd;
import event.ContractSetup;
import event.EDITTrx;
import event.GroupSetup;
import event.InvestmentAllocationOverride;
import event.WithholdingOverride;

import fission.utility.DOMUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import role.ClientRole;


/**
 * The ClientDocument is built with the following entities:
 * ClientDetail
 * ClientDetail.ClientAddress (active and based on ClientRole.RoleTypeCT)
 * ClientDetail.Preference (may be an override off of ClientRole)
 * ClientDetail.TaxInformation (may be an override off of ClientRole)
 * ClientDetail.TaxInformation.TaxProfile
 * 
 * The following rules apply:
 *
 * 1. The ClientAddress is optional, but if it exists, it is constrained by 
 * ClientAddress.AddressTypeCT = "PrimaryAddress" and ClientAddress.TerminationDate = EDITDate.DEFAULT_MAX_DATE.
 * 
 */
public class ClientDetailDocument extends PRASEDocBuilder
{
  /**
   * The driving clientRolePK. 
   */
  private Long clientRolePK;

  /**
   * The key name of the CONTRACTCLIENTPK building parameter.
   */  
  public static final String BUILDING_PARAMETER_CLIENTROLEPK = "ClientRolePK";

  
    public static final String ROOT_ELEMENT_NAME = "ClientDetailDocVO";

    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_CLIENTROLEPK};

    public ClientDetailDocument(){}

    /**
     * Constuctor. The specified building params is expected to 
     * contain the ClientRolePK.
     * @see #BUILDING_PARAMETER_CLIENTROLEPK
     * @param buildingParams
     */
    public ClientDetailDocument(Map<String, String> buildingParams)
    {
        super(buildingParams);
        
        this.clientRolePK = new Long(buildingParams.get(BUILDING_PARAMETER_CLIENTROLEPK));
    }

    /**
     * Builds the DOM4J Element equivalent of the ClientDocVO used for processing.
     * 
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element clientDetailDocVOElement = new DefaultElement(getRootElementName());

            // Purposely over-grabbing information here since the use of overrides and primary ClientAddress
            // could result in several calls.
            ClientRole drivingClientRole = ClientRole.findSeparateBy_ClientRolePK_V1(getClientRolePK());
            
            buildClientDetailElement(drivingClientRole, clientDetailDocVOElement);

            setRootElement(clientDetailDocVOElement);

            setDocumentBuilt(true);
        }
    }

    /**
     * The associated ClientDetail as dictated by the ClientRole. Building continues
     * with ClientAddress, Preference, and TaxInformation.
     * @param clientRole
     * @param clientDocumentElement
     */
    private void buildClientDetailElement(ClientRole clientRole, Element clientDocumentElement)
    {   
        ClientDetail clientDetail = clientRole.getClientDetail();
    
        Element clientDetailElement = clientDetail.getAsElement();

        buildClientAddressElement(clientDetail.getClientAddresses(), clientDetailElement);

        buildPreferenceElement(clientDetail, clientRole, clientDetailElement);

        buildTaxInformationElement(clientDetail, clientRole, clientDetailElement);

        clientDocumentElement.add(clientDetailElement);
    }

    /**
     * The associated ClientAddress needs to be filtered by ClientAddress.AddressTypeCT = "PrimaryAddress"
     * and ClientAddress.TerminationDate = EDITDate.DEFAULT_MAX_DATE. The ClientAddress is 
     * an optional entity. Since we over-grabbed the information, we need to do this filter
     * manually.
     * @param clientAddresses the associated ClientAddress by AddressTypeCT and TerminationDate
     * @param clientDetailElement the containing Element
     */
    private void buildClientAddressElement(Set<ClientAddress> clientAddresses, Element clientDetailElement)
    {
        if (!clientAddresses.isEmpty())
        {
            for (ClientAddress clientAddress: clientAddresses)
            {
                if (clientAddress.getAddressTypeCT().equals(ClientAddress.CLIENT_PRIMARY_ADDRESS))
                {
                    if (clientAddress.getTerminationDate() == null || clientAddress.getTerminationDate().equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
                    {
                        Element clientAddressElement = clientAddress.getAsElement();

                        clientDetailElement.add(clientAddressElement);

                        break;
                    }
                }
            }
        }
    }

    /**
     * The associated TaxInformation for the ClientDetail. This entity is supposed to be there at all times.
     * The building process continues with TaxProfile.
     * @param clientDetail
     * @param clientRole to get overridden preference.
     * @param clientDetailElement the containing Element
     */
    private void buildTaxInformationElement(ClientDetail clientDetail, ClientRole clientRole, Element clientDetailElement)
    {
        TaxInformation taxInformation = clientDetail.getTaxInformations().iterator().next();

        Element taxInformationElement = taxInformation.getAsElement();

        Set<TaxProfile> taxProfiles = new HashSet<TaxProfile>();

        // Overridden TaxProfile
        if (clientRole.getTaxProfile() != null)
        {
            taxProfiles.add(clientRole.getTaxProfile());
        }
        else
        {
            taxProfiles = taxInformation.getTaxProfiles();
        }

        buildTaxProfileElement(taxProfiles, taxInformationElement);

        clientDetailElement.add(taxInformationElement);
    }

    /**
     * The associated TaxProfile for the TaxInformation. The TaxProfile is an optional entity.
     *
     * @param taxProfiles the associated TaxProfile
     * @param taxInformationElement the containing Element
     */
    private void buildTaxProfileElement(Set<TaxProfile> taxProfiles, Element taxInformationElement)
    {
        if (!taxProfiles.isEmpty())
        {
            for (TaxProfile taxProfile: taxProfiles)
            {
                Element taxProfileElement = taxProfile.getAsElement();

                taxInformationElement.add(taxProfileElement);
            }
        }
    }

    /**
     * The associated Preference of the ClientDetail. This is an optional entity.
     * @param clientDetail
     * @param clientRole to get overridden preference.
     * @param clientDetailElement the containing Element
     */
    private void buildPreferenceElement(ClientDetail clientDetail, ClientRole clientRole, Element clientDetailElement)
    {
        Set<Preference> preferences = new HashSet<Preference>();

        // Overridden Preference
        if (clientRole.getPreference() != null)
        {
            preferences.add(clientRole.getPreference());
        }
        else
        {
            preferences = clientDetail.getPreferences();
        }

        if (!preferences.isEmpty())
        {
            Element preferenceElement = preferences.iterator().next().getAsElement();

            clientDetailElement.add(preferenceElement);
        }
    }

    /**
     * If there is a ContractClientAllocationOvrd use it, otherwise use the ContractClientAllocation (if it exists).
     * @param contractClientAllocations the original ContractClientAllocation (if any)
     * @param contractClientAllocationOvrds the overridden ContractClientAllocation (if any)
     * @param contractClientElement the containing Element
     */
    private void buildContractClientAllocationElement(Set<ContractClientAllocation> contractClientAllocations, Set<ContractClientAllocationOvrd> contractClientAllocationOvrds, Element contractClientElement)
    {
        ContractClientAllocation contractClientAllocation = null;

        if (!contractClientAllocationOvrds.isEmpty()) // Check the overrides first.     
        {
            contractClientAllocation = contractClientAllocationOvrds.iterator().next().getContractClientAllocation();
        }
        else if (!contractClientAllocations.isEmpty()) // If no overrides, then check for the original.
        {
            contractClientAllocation = contractClientAllocations.iterator().next();
        }

        if (contractClientAllocation != null)
        {
            Element contractClientAllocationElement = contractClientAllocation.getAsElement();

            contractClientElement.add(contractClientAllocationElement);
        }
    }

    /**
     * If there is a WithholdingOverride use it, otherwise use the Withholding (if it exists).
     * @param withholdings the original Withholding (if any)
     * @param withholdingOverrides the overridden Withholding (if any)
     * @param contractClientElement the containing Element
     */
    private void buildWithholdingElement(Set<Withholding> withholdings, Set<WithholdingOverride> withholdingOverrides, Element contractClientElement)
    {
        Withholding withholding = null;

        if (!withholdingOverrides.isEmpty()) // Check the overrides first.     
        {
            withholding = withholdingOverrides.iterator().next().getWithholding();
        }
        else if (!withholdings.isEmpty()) // If no overrides, then check for the original.
        {
            withholding = withholdings.iterator().next();
        }

        if (withholding != null)
        {
            Element withholdingElement = withholding.getAsElement();

            contractClientElement.add(withholdingElement);
        }
    }

    /**
     * 
     * @return
     */
    public String getRootElementName()
    {
        return ROOT_ELEMENT_NAME;
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
  
    /**
     * @see #clientRolePK
     */
    private Long getClientRolePK() 
    {
        return this.clientRolePK;
    }
}
