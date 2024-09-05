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
import contract.Segment;
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

import java.util.*;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import role.ClientRole;


/**
 * The ClientDocument is built with the following entities:
 * ClientDetail
 * ClientDetail.ClientAddress (active and based on ClientRole.RoleTypeCT)
 * ClientDetail.Preference
 * ClientDetail.TaxInformation
 * ClientDetail.TaxInformation.TaxProfile
 * 
 * ContractClient
 * ContractClient.Allocation (substituted with the ClientSetup.AllocationOverride if exists)
 * ContractClient.Withholding (substituted with the ClientSetup.WithholdingOverride if exists)
 * 
 * The following rules apply:
 * 
 * 1. ContractClient.SegmentFK and ClientRole.RoleTypeCT are NOT sufficient to uniquely
 * define a ContractClient (e.g. change of ownership for beneficiary). For this reason there 
 * is the default constraint of  ContractClient.TerminationDate = EDITDate.DEFAULT_MAX_DATE. 
 * This should produce a unique ContractClient.
 *
 * 2. The ClientAddress is optional, but if it exists, it is constrained by 
 * ClientAddress.AddressTypeCT = "PrimaryAddress" and ClientAddress.TerminationDate = EDITDate.DEFAULT_MAX_DATE.
 * 
 * 3. ContractClient.ContractClientAllocation (0 or 1) and ContractClient.Withhodling (0 or 1) may be overridden at
 * the ClientSetup level. Either the original OR the overridden entity must be used (if exists).
 * 
 * 4. It is possible that the Script Writer wants to active a "SECONDARY" client. For this reason, another
 * WS parameter is introduced called CLIENTDOCDRIVER = [PRIMARY|SECONDARY]. When it is specifed as PRIMARY, then the
 * above building rules apply. If it is specified as SECONDARY, then the above rules also apply with 
 * the exception of # 3 since the driving PK will be a SegmentPK and not an ContractClientPK (e.g. Segment.ContractClient.ClientRole for
 * a SegmentPK) vs (ContractClient.ClientRole for ContractClientPK).
 */
public class ClientDocument extends PRASEDocBuilder
{
    /**
   * The driving entityPK. Could be either an ContractClientPK (CLIENTDOCDRIVER = PRIMARY)
     * or SegmentPK (CLIENTDOCDRIVER = SECONDARY).
     */
    private Long entityPK;

    /**
     * The ContractClient.ClientRole.RoleTypeCT that, along with the assumed
     * ContractClient.TerminationDate of EDITDate.DEFAULT_MAX_DATE will define
     * a unique ContractClient.
     */
    private String roleTypeCT;

    /**
   * Dictates whether the building process is driven by SegmentPK or ContractClientPK.
     */
    private String clientDocDriver;
    
    private String contractNumber;

  /**
   * The EDITTrx Identifier if CLIENTDOCDRIVER is EDITTrx mode.
   */
  private Long editTrxPK;
  
    /**
     * The key name of the RoleTypeCT building parameter.
     */
    public static final String BUILDING_PARAMETER_NAME_ROLETYPECT = "RoleTypeCT";

    /**
   * The key name of the ClientDocDriver building parameter.
   */
  public static final String BUILDING_PARAMETER_CLIENTDOCDRIVER = "ClientDocDriver";

  /**
   * The key name of the CONTRACTCLIENTPK building parameter.
   */
  public static final String BUILDING_PARAMETER_CONTRACTCLIENTPK = "ContractClientPK";

  public static final String BUILDING_PARAMETER_ENTITYPK = "EntityPK";

  public static final String BUILDING_PARAMETER_EDITTRXPK = "EDITTrxPK";

  public static final String BUILDING_PARAMETER_PREFERENCETYPE = "PreferenceType";

  /**
   * If specified from the CLIENTDOCDRIVER building parameter, the building process
   * will be based on the specified ContractClientPK.
   */
  public static final String BUILDING_PARAMETER_VALUE_EDITTRX = "EDITTrx";
  
  public static final String BUILDING_PARAMETER_VALUE_SEGMENT = "Segment";

    /**
   * The key name of the SegmentPK building parameter.
   */
  public static final String BUILDING_PARAMETER_SEGMENTPK = "SegmentPK";

    /**
     * The key name of the ClientDocDriver building parameter.
     */
    public static final String BUILDING_PARAMETER_NAME_CLIENTDOCDRIVER = "ClientDocDriver";

    /**
     * The key name of the EDITTRXPK building parameter.
     */
    public static final String BUILDING_PARAMETER_NAME_EDITTRXPK = "EDITTrxPK";


    public static final String BUILDING_PARAMETER_NAME_ENTITYPK = "EntityPK";

    /**
     * The key name of the CONTRACTCLIENTPK building parameter.
     */
    public static final String BUILDING_PARAMETER_NAME_CONTRACTCLIENTPK = "ContractClientPK";

    public static final String ROOT_ELEMENT_NAME = "ClientDocVO";

    /**
     * The key name of the SegmentPK building parameter.
     */
    public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK";
    
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_CLIENTDOCDRIVER,
                                                BUILDING_PARAMETER_NAME_EDITTRXPK,
                                                BUILDING_PARAMETER_NAME_ENTITYPK,
                                                BUILDING_PARAMETER_NAME_ROLETYPECT,
                                                BUILDING_PARAMETER_NAME_SEGMENTPK,
                                                BUILDING_PARAMETER_VALUE_EDITTRX,
                                                BUILDING_PARAMETER_VALUE_SEGMENT,
                                                BUILDING_PARAMETER_PREFERENCETYPE};

    public ClientDocument(){}

    public ClientDocument(Long entityPK, String roleTypeCT, String clientDocDriver)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_ENTITYPK, entityPK.toString()).put(BUILDING_PARAMETER_NAME_ROLETYPECT, roleTypeCT).put(BUILDING_PARAMETER_NAME_CLIENTDOCDRIVER, clientDocDriver));

        this.entityPK = entityPK;

        this.roleTypeCT = roleTypeCT;

        this.clientDocDriver = clientDocDriver;
    }

    /**
     * Constuctor. The specified building params is expected to 
     * contain the EDITTrxPK and RoleTypeCT.
     * @see #BUILDING_PARAMETER_NAME_ROLETYPECT
     * @see #BUILDING_PARAMETER_NAME_EDITTRXPK
     * @param buildingParams
     */
    public ClientDocument(Map<String, String> buildingParams)
    {
        super(buildingParams);

        this.clientDocDriver = buildingParams.get(BUILDING_PARAMETER_NAME_CLIENTDOCDRIVER);

        if (getClientDocDriver().equalsIgnoreCase(BUILDING_PARAMETER_VALUE_EDITTRX))
        {
            this.entityPK = new Long(buildingParams.get(BUILDING_PARAMETER_NAME_EDITTRXPK));
        }
        else if (getClientDocDriver().equalsIgnoreCase(BUILDING_PARAMETER_VALUE_SEGMENT))
        {
            this.entityPK = new Long(buildingParams.get(BUILDING_PARAMETER_NAME_SEGMENTPK));
        }

        this.roleTypeCT = buildingParams.get(BUILDING_PARAMETER_NAME_ROLETYPECT);
    }
    
    /**
     * Constructor. The specified clientDocument represents the fully 
     * constructed Document. The building parameters are assumed to be a
     * SegmentPK of 0 and the RoleTypeCT. It would not make sense to ever use
     * the EDITTrxPK as the building parameter since we could build and
     * ClientDocument from the database with such information.
     * @param clientVOElement
     */
    public ClientDocument(Element clientVOElement)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, "0").put(BUILDING_PARAMETER_NAME_CLIENTDOCDRIVER, BUILDING_PARAMETER_VALUE_SEGMENT).put(BUILDING_PARAMETER_NAME_ROLETYPECT, clientVOElement.element("RoleTypeCT").getText()));
    
        Element rootElement = new DefaultElement(getRootElementName());
        
        rootElement.add(clientVOElement);
    
        setRootElement(rootElement);
        
        setDocumentBuilt(true);
    }

    public ClientDocument(Element clientVOElement, String segmentPK, String contractClientPK)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_EDITTRXPK, "0").put(BUILDING_PARAMETER_NAME_CLIENTDOCDRIVER, BUILDING_PARAMETER_VALUE_EDITTRX).put(BUILDING_PARAMETER_NAME_ENTITYPK, null).put(BUILDING_PARAMETER_NAME_SEGMENTPK, segmentPK).put(BUILDING_PARAMETER_NAME_CONTRACTCLIENTPK, contractClientPK));

        Element rootElement = new DefaultElement(getRootElementName());

        rootElement.add(clientVOElement);

        setRootElement(rootElement);

        setDocumentBuilt(true);
    }

    /**
     * Builds the DOM4J Element equivalent of the ClientDocVO used for processing.
     * 
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element clientDocVOElement = new DefaultElement(getRootElementName());

            //SessionHelper.clearSessions(); NO NO NO NO NO!!!! You can't just blindly empty the entire Hibernate Session in the middle of no where.
            
            // The driving data structure.
            ContractClient[] contractClients = null;
            
            if (getClientDocDriver().equalsIgnoreCase(BUILDING_PARAMETER_VALUE_EDITTRX))
            {
                contractClients = new ContractClient[1];
                
                    contractClients[0] = ContractClient.findSeparateBy_EDITTrxPK_V1(getEntityPK());
                }
            else if (getClientDocDriver().equalsIgnoreCase(BUILDING_PARAMETER_VALUE_SEGMENT))
            {
                Segment segment = Segment.findByPK(getEntityPK()); 
                this.contractNumber = segment.getContractNumber();

            	if (this.contractNumber != null && !this.contractNumber.equals("")) {
            		contractClients = ContractClient.findSeparateBy_ContractNumber_RoleType_V1(this.contractNumber, getRoleTypeCT());
            	} else {
                    contractClients = ContractClient.findSeparateBy_SegmentPK_RoleType_V1(getEntityPK(), getRoleTypeCT());
            	}
            }

            for (ContractClient contractClient: contractClients)
            {
                buildClientVOElement(contractClient, clientDocVOElement);
            }

            setRootElement(clientDocVOElement);

            setDocumentBuilt(true);
        }
    }

    /**
     * The driving ClientRole.RoleTypeCT with the associated MAX
     * ContractClient.TerminationDate.
     * @return
     */
    public String getRoleTypeCT()
    {
        return roleTypeCT;
    }

    /**
     * The major container of this ClientDocument. It contains
     * the ClientRole.RoleTypeCT, ClientRole.NewIssuesEligibilityStatusCT, and
     * a "fake" ClientPK.
     * Building continues with ClientDetail and ContractClient.
     * @param contractClient
     * @return
     */
    private void buildClientVOElement(ContractClient contractClient, Element clientDocVOElement)
    {
        Element clientVOElement = new DefaultElement("ClientVO");

        ClientRole clientRole = contractClient.getClientRole();

        // RoleTypeCT
        Element roleTypeCTElement = new DefaultElement("RoleTypeCT");

        roleTypeCTElement.setText(clientRole.getRoleTypeCT());

        // NewIssuesEligibilityStatusCT
        Element newIssuesEligibilityStatusCTElement = null;

        if (clientRole.getNewIssuesEligibilityStatusCT() != null)
        {
            newIssuesEligibilityStatusCTElement = new DefaultElement("NewIssuesEligibilityStatusCT");

            newIssuesEligibilityStatusCTElement.setText(clientRole.getNewIssuesEligibilityStatusCT());
        }

        // ClientPK
        Element clientPKElement = new DefaultElement("ClientPK");

        clientPKElement.setText(CRUD.getNextAvailableKey() + "");

        // Add them together.
        clientVOElement.add(roleTypeCTElement);

        clientVOElement.add(clientPKElement);

        if (newIssuesEligibilityStatusCTElement != null)
        {
            clientVOElement.add(newIssuesEligibilityStatusCTElement);
        }

        clientDocVOElement.add(clientVOElement);

        // Continue building.
        buildClientDetailElement(contractClient.getClientDetail(), clientRole, clientVOElement);

        buildContractClientElement(contractClient, clientVOElement);
    }

    /**
     * The associated ClientDetail as dictated by the ClientRole. Building continues
     * with ClientAddress, Preference, and TaxInformation.
     * @param clientDetail
     * @param clientRole
     * @param clientDocumentElement
     */
    private void buildClientDetailElement(ClientDetail clientDetail, ClientRole clientRole, Element clientDocumentElement)
    {
        Element clientDetailElement = clientDetail.getAsElement();

        buildClientAddressElement(clientDetail.getClientAddresses(), clientDetailElement);

        buildPreferenceElement(clientDetail, clientRole, clientDetailElement);

        buildTaxInformationElement(clientDetail, clientRole, clientDetailElement);

        clientDocumentElement.add(clientDetailElement);
    }

    /**
     * The associated ClientAddress that was filtered by ClientAddress.AddressTypeCT = "PrimaryAddress"
     * and ClientAddress.TerminationDate = EDITDate.DEFAULT_MAX_DATE. The ClientAddress is 
     * an optional entity.
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
        Set<TaxInformation> taxInformationSet = clientDetail.getTaxInformations();
        Iterator it = taxInformationSet.iterator();

        while (it.hasNext())
        {
            TaxInformation taxInformation = (TaxInformation) it.next();

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
     * 
     *    1.  If ClientRole.PreferenceFK NOT = null
     *        Get corresponding Preference record based on PreferenceFK
     *
     *    2. If ws:PreferenceType not set and ClientRole.PreferenceFK = null
     *        Get Preference record [if one exists]
     *            PreferenceType = Disbursement AND OverrideStatus = P
     *
     *     3. If ws:PreferenceType is set and ClientRole.PreferenceFK = null
     *        Get Preference record indicated by ws:PreferenceType [if one exists]
     *            If ws:PreferenceType = Billing
     *                  PreferenceType = Billing AND OverrideStatus = P
     *            If ws:PreferenceType = Disbursment
     *                  PreferenceType = Disbursement AND OverrideStatus = P
     * @param clientDetail
     * @param clientRole to get overridden preference.
     * @param clientDetailElement the containing Element
     */
    private void buildPreferenceElement(ClientDetail clientDetail, ClientRole clientRole, Element clientDetailElement)
    {
        Preference preference = null;
        
        String wsPreferenceType = super.getBuildingParameters().get("PreferenceType");

        // Overridden Preference
        if (clientRole.getPreference() != null)
        {
            preference = clientRole.getPreference();
        }
        else
        {
            for (Preference currentPreference:clientDetail.getPreferences())
            {
                String currentPreferenceType = currentPreference.getPreferenceTypeCT();
                
                String currentOverrideStatus = currentPreference.getOverrideStatus();
                
                if ((currentPreferenceType != null) && (currentOverrideStatus != null)) // apparently these values aren't guaranteed to be there
                {
                    if ((wsPreferenceType) == null && currentPreferenceType.equals(Preference.TYPE_BILLING) && currentOverrideStatus.equals(Preference.PRIMARY))
                    {
                        preference = currentPreference;
                        
                        break;
                    }
                    else if ((wsPreferenceType != null) && wsPreferenceType.equals(Preference.TYPE_BILLING) && currentPreferenceType.equals(Preference.TYPE_BILLING) && currentOverrideStatus.equals(Preference.PRIMARY))
                    {
                        preference = currentPreference;
                        
                        break;                    
                    }
                    else if ((wsPreferenceType != null) && wsPreferenceType.equals(Preference.TYPE_DISBURSEMENT)&& currentPreferenceType.equals(Preference.TYPE_DISBURSEMENT) && currentOverrideStatus.equals(Preference.PRIMARY))
                    {
                        preference = currentPreference;
                        
                        break;                    
                    }                    
                }
            }
        }

        if (preference != null)
        {
            Element preferenceElement = preference.getAsElement();

            clientDetailElement.add(preferenceElement);
        }
    }

    /**
     * The driving entity of this Document. It is complicated in that its associated
     * ContractClientAllocation and Withholding entities may have been overridden at
     * the ClientSetup level. In any case, it is important that the Withholding
     * and ContractClientAllocation Elements be properly established either with
     * the originals, OR with the overrides via ClientSetup.
     * @param contractClient
     * @param clientDocumentElement
     */
    private void buildContractClientElement(ContractClient contractClient, Element clientDocumentElement)
    {
        Element contractClientElement = contractClient.getAsElement();

        Set<ContractClientAllocation> contractClientAllocations = contractClient.getContractClientAllocations();

        Set<ContractClientAllocationOvrd> contractClientAllocationOvrds = null;

        Set<WithholdingOverride> withholdingOverrides = null;

        if (getClientDocDriver().equals(BUILDING_PARAMETER_VALUE_SEGMENT)) // There will never be overrides for this ClientDocDriver
        {
            contractClientAllocationOvrds = new HashSet<ContractClientAllocationOvrd>(); // dummy Set      

            withholdingOverrides = new HashSet<WithholdingOverride>(); // dummy Set      
        }
        else if (getClientDocDriver().equals(BUILDING_PARAMETER_VALUE_EDITTRX))
        {
            ClientSetup clientSetup = contractClient.getClientSetups().iterator().next();

            contractClientAllocationOvrds = clientSetup.getContractClientAllocationOvrds();

            withholdingOverrides = clientSetup.getWithholdingOverrides();
        }

        buildContractClientAllocationElement(contractClientAllocations, contractClientAllocationOvrds, contractClientElement);

        buildWithholdingElement(contractClient.getWithholdings(), withholdingOverrides, contractClientElement);

        clientDocumentElement.add(contractClientElement);
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

    /**
     * @see #clientDocDriver
     * @return
     */
    public String getClientDocDriver()
    {
        return clientDocDriver;
    }

    /**
     * 
     * @return
     */
    public Long getEntityPK()
    {
        return entityPK;
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }

  /**
     * Builds object model from the GroupSetup document.
    */
    // Always expects to have one and only one GroupSetup, ContractSetup, ClientSetup and edittrx.
    // Always must have GroupSetup, ContractSetup, ClientSetup and EDITTrx
    private ClientSetup buildEntitiesAndGetClientSetupFromGroupSetupDocument()
  {
        PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder) getSPParams().getDocumentByName("GroupSetupDocVO");

        Element groupSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO", groupSetupDocument).get(0);
    
        GroupSetup groupSetup = (GroupSetup) SessionHelper.mapToHibernateEntity(GroupSetup.class, groupSetupElement, SessionHelper.EDITSOLUTIONS);
        
        Element contractSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO", groupSetupDocument).get(0);

        ContractSetup contractSetup = (ContractSetup) SessionHelper.mapToHibernateEntity(ContractSetup.class, contractSetupElement, SessionHelper.EDITSOLUTIONS);

        Element clientSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO", groupSetupDocument).get(0);

        ClientSetup clientSetup = (ClientSetup) SessionHelper.mapToHibernateEntity(ClientSetup.class, clientSetupElement, SessionHelper.EDITSOLUTIONS);

        Element editTrxElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO", groupSetupDocument).get(0);

        EDITTrx editTrx = (EDITTrx) SessionHelper.mapToHibernateEntity(EDITTrx.class, editTrxElement, SessionHelper.EDITSOLUTIONS);

        groupSetup.addContractSetup(contractSetup);
        contractSetup.addClientSetup(clientSetup);
        clientSetup.addEDITTrx(editTrx);

        List<Element> contractClientAllocationOvrdElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.ContractClientAllocationOvrdVO", groupSetupDocument);
                            
        for (Element contractClientAllocationOvrdElement : contractClientAllocationOvrdElements)
                            {
            ContractClientAllocationOvrd contractClientAllocationOvrd = (ContractClientAllocationOvrd) SessionHelper.mapToHibernateEntity(ContractClientAllocationOvrd.class, contractClientAllocationOvrdElement, SessionHelper.EDITSOLUTIONS);
                        
            Long contractClientAllocationFK = contractClientAllocationOvrd.getContractClientAllocationFK();
                        
            // The document just holds FKs. By this time the ContractClientAllocation and ContractClientAllocationOvrd are saved to tbe database.
            // Get the entity from database and build object structure.
            ContractClientAllocation contractClientAllocation = ContractClientAllocation.findSeperateByPK(contractClientAllocationFK);
                        
            contractClientAllocationOvrd.setContractClientAllocation(contractClientAllocation);

            clientSetup.addContractClientAllocationOvrd(contractClientAllocationOvrd);
                                    }
        
        List<Element> withholdingOverrideElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.WithholdingOverrideVO", groupSetupDocument);

        for (Element withholdingOverrideElement : withholdingOverrideElements)
        {
            WithholdingOverride withholdingOverride = (WithholdingOverride) SessionHelper.mapToHibernateEntity(WithholdingOverride.class, withholdingOverrideElement, SessionHelper.EDITSOLUTIONS);

            // The document just holds FKs. By this time the Withholding and WithholdingOverride are saved to database.
            // Get the entity from database and build the object structure.
            Long withholdingFK = withholdingOverride.getWithholdingFK();

            Withholding withholding = Withholding.findSeperateByPK(withholdingFK);
                
            withholdingOverride.setWithholding(withholding);

            clientSetup.addWithholdingOverride(withholdingOverride);
            }

        return clientSetup;
    }
    
    /**
     * Returns true if the driving transaction is new.
     * @return
     */
    private boolean isNewTransaction()
        {
        return this.editTrxPK == 0 ? true : false;
                        }
                        
  /**
    * 
    * @return
    */
  public Long getEDITTrxPK()
    {
      return this.editTrxPK;
            }
}
