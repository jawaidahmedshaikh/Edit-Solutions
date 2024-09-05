/*
 * User: gfrosti
 * Date: June 11, 2008
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

import edit.services.config.ServicesConfig;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import role.ClientRole;


/**
 * The original motivation for this builder is to react to a non-financial (NF)
 * change within ClientAddress. Such a NF change requires potential trx changes
 * for all ContractClients that could be associated with the working storage (WS) specified
 * ClientAddressPK. 
 * 
 * We also added the ClientDetailPK as a potential WS parameter. So, if WS contains
 * a ClientAddressPK, then we included the ClientAddress in the building of the 
 * document. If the WS contains a ClientDetailPK, then we don't include any
 * ClientAddress. WS containing [both] PKs is considered ambiguous.
 * 
 * The ContractClientDocument is built with the following entities:
 * 
 * ClientDetail
 * ClientDetail.ClientAddress (if specified in WS)
 * ClientDetail.ClientRole
 * ClientDetail.ClientRole.ContractClient
 * 
 */
public class ContractClientDocument extends PRASEDocBuilder
{
    /**
     * The driving clientAddressPK. 
     */
    private Long clientAddressPK;
    
    /**
     * The driving clientDetailPK.
     */
    private Long clientDetailPK;

    /**
     * The key name of the ClientAddressPK building parameter.
     */
    public static final String BUILDING_PARAMETER_CLIENTADDRESSPK = "ClientAddressPK";

    /**
     * The key name of the ClientAddressPK building parameter.
     */
    public static final String BUILDING_PARAMETER_CLIENTDETAILPK = "ClientDetailPK";

    /**
     * The name of the root Element/Container.
     */
    public static final String ROOT_ELEMENT_NAME = "ContractClientDocVO";

    private static final String[] buildingParameterNames = { BUILDING_PARAMETER_CLIENTADDRESSPK , BUILDING_PARAMETER_CLIENTDETAILPK};

    public ContractClientDocument()
    {
    }

    /**
     * Constuctor. The specified building params is expected to 
     * contain the ClientAddressPK.
     * @see #BUILDING_PARAMETER_CLIENTADDRESSPK
     * @param buildingParams
     */
    public ContractClientDocument(Map<String, String> buildingParams)
    {
        super(buildingParams);

        if (buildingParams.get(BUILDING_PARAMETER_CLIENTADDRESSPK) != null)
        {
            this.clientAddressPK = new Long(buildingParams.get(BUILDING_PARAMETER_CLIENTADDRESSPK));
        }
        else if (buildingParams.get(BUILDING_PARAMETER_CLIENTDETAILPK) != null)
        {
            this.clientDetailPK = new Long(buildingParams.get(BUILDING_PARAMETER_CLIENTDETAILPK));             
        }
    }

    /**
     * Builds the DOM4J Element equivalent of the ContractClientDocVO used for processing.
     * 
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element contractClientDocVOElement = new DefaultElement(getRootElementName());

            ClientDetail drivingClientDetail = null;
            
            if (getClientAddressPK() != null)
            {
                drivingClientDetail = ClientDetail.findSeparateBy_ClientAddressPK_V1(getClientAddressPK());
            }
            else if (getClientDetailPK() != null)
            {
                drivingClientDetail = ClientDetail.findSeparateBy_ClientDetailPK_V1(getClientDetailPK());
            }

            buildClientDetailElement(drivingClientDetail, contractClientDocVOElement);

            setRootElement(contractClientDocVOElement);

            setDocumentBuilt(true);
        }
    }

    /**
     * The associated ClientDetail as dictated by the ClientAddress. Building continues
     * with the ClientAddress, and ClientRoles.
     * @param clientDetail
     * @param contractClientDocVOElement
     */
    private void buildClientDetailElement(ClientDetail clientDetail, Element contractClientDocVOElement)
    {
        Element clientDetailElement = clientDetail.getAsElement();
        
        contractClientDocVOElement.add(clientDetailElement);

        if (getClientAddressPK() != null)
        {
            // there should only be one ClientAddress that was picked-up in the original hql.
            buildClientAddressElement(clientDetail.getClientAddresses().iterator().next(), clientDetailElement);
        }

        Set<ClientRole> clientRoles = clientDetail.getClientRoles();
        
        for (ClientRole clientRole:clientRoles)
        {
            buildClientRoleElement(clientRole, clientDetailElement);
        }
    }

    /**
     * Uses the original driving ClientAddress and adds its Element to the
     * specified ClientdDetail Element.
     * manually.
     * @param clientAddress the original driving ClientAddress
     * @param clientDetailElement the containing Element
     */
    private void buildClientAddressElement(ClientAddress clientAddress, Element clientDetailElement)
    {
        Element clientAddressElement = clientAddress.getAsElement();

        clientDetailElement.add(clientAddressElement);
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
     * @see #clientAddressPK
     */
    private Long getClientAddressPK()
    {
        return this.clientAddressPK;
    }
    
    /**
     * @see #clientAddressPK
     */
    private Long getClientDetailPK()
    {
        return this.clientDetailPK;
    }    

    /**
     * Builds the ClientRole Element from the specified clientDetailElement and adds itself 
     * as a child to the ClientDetail Element. Building continues with the ClientRole's associated
     * ContractClient.
     * @param clientRole
     * @param clientDetailElement
     */
    private void buildClientRoleElement(ClientRole clientRole, Element clientDetailElement)
    {
        Element clientRoleElement = clientRole.getAsElement();
        
        clientDetailElement.add(clientRoleElement);
        
        ContractClient contractClient = clientRole.getContractClients().iterator().next(); // there should be one and only one
        
        buildContractClientElement(contractClient, clientRoleElement);
    }

    /**
     * Builds a ContractClient Element from the specified contractClient and adds itself as a child
     * of the specified ClientRole Element.
     * @param contractClient
     * @param clientRoleElement
     */
    private void buildContractClientElement(ContractClient contractClient, Element clientRoleElement)
    {
        Element contractClientElement = contractClient.getAsElement();
        
        clientRoleElement.add(contractClientElement);
    }

    public static void main(String[] args)
    {
        ServicesConfig.setEditServicesConfig("C:\\Projects\\JDeveloper\\EDITSolutions\\VisionDevelopment\\webapps\\WEB-INF\\EDITServicesConfig.xml");
        
        Map params = new HashMap();
        
        params.put("ClientDetailPK", "1209480870585");
        
        ContractClientDocument document = new ContractClientDocument(params);
        
        document.build();
    }

}
