/*
 * User: gfrosti
 * Date: May 3, 2004
 * Time: 2:10:12 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package codetable;

import edit.common.vo.VOObject;
import edit.common.vo.*;
import edit.common.EDITDate;
import edit.common.exceptions.EDITContractException;

import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

import contract.*;
import contract.component.ContractComponent;
import contract.business.Contract;
import reinsurance.*;
import client.ClientDetail;
import client.ClientAddress;
import agent.Agent;


public class IssueDocument extends PRASEDocument
{
    private IssueDocumentVO issueDocumentVO;

    private boolean includeInvestments;

    private long segmentPK;

    private String issueDate;

    private static final String ANNUITANT = "ANN";
    private static final String SECONDARY_ANNUITANT = "SAN";
    private static final String OWNER = "OWN";
    private static final String SECONDARY_OWNER = "SOW";
    private static final String INSURED = "Insured";
    private static final String PRIMARY_BENEFICIARY = "PBE";
    private static final String CONTINGENT_BENEFICIARY = "CBE";

    /**
     * Contructor - set the segmentPK that is passed in
     * @param segmentPK
     */
    public IssueDocument(long segmentPK, String issueDate)
    {
        this.segmentPK = segmentPK;

        this.issueDate = issueDate;
    }

    /**
     * Build IssueDocumentVO for the segmentPK set in this entity. Contract, client, agent and investment data will be used.
     */
    public void buildDocument()
    {
        try
        {
            Vector voInclusionList = new Vector();
            voInclusionList.add(LifeVO.class);
            voInclusionList.add(PayoutVO.class);
            voInclusionList.add(ContractClientVO.class);
            voInclusionList.add(ContractClientAllocationVO.class);
            voInclusionList.add(InvestmentVO.class);
            voInclusionList.add(InvestmentAllocationVO.class);
            voInclusionList.add(BucketVO.class);
            voInclusionList.add(SegmentVO.class);
            voInclusionList.add(AgentHierarchyVO.class);
            voInclusionList.add(AgentHierarchyAllocationVO.class);
            voInclusionList.add(AgentSnapshotVO.class);
            voInclusionList.add(DepositsVO.class);
            voInclusionList.add(NoteReminderVO.class);

            SegmentVO segmentVO = super.getSegmentVO(segmentPK, voInclusionList);

            ContractClientVO[] contractClientVOs = setIssueAge(segmentVO);
            segmentVO.removeAllContractClientVO();
            segmentVO.setContractClientVO(contractClientVOs);
            checkSegmentVODates(segmentVO);
            IssueClientVO[] issueClientVOs = null;
            IssueInvestmentVO[] issueInvestmentVOs = null;
            ProductStructureVO productStructureVO = null;

            if (segmentVO != null)
            {
                issueClientVOs = determineClients(segmentVO);

                issueInvestmentVOs = getInvestmentVO(segmentVO);

                if (segmentVO.getInvestmentVOCount() > 0)
                {
                    segmentVO.removeAllInvestmentVO();
                }

                productStructureVO = super.getProductStructureVO(segmentVO.getProductStructureFK());
            }

            issueDocumentVO = new IssueDocumentVO();
            issueDocumentVO.setSegmentVO(segmentVO);
            issueDocumentVO.setProductStructureVO(productStructureVO);
            issueDocumentVO.setIssueClientVO(issueClientVOs);
            issueDocumentVO.setIssueInvestmentVO(issueInvestmentVOs);

            setOwnersPremiumTaxState();

            setInsuredFields();

            associateReinsuranceInformation();
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * Set the effective and issue dates for script processing.
     * @param segmentVO
     */
    private void checkSegmentVODates(SegmentVO segmentVO)
    {
//        String effectiveDate = segmentVO.getEffectiveDate();
//        String currentDate = EDITDate.getCurrentDate();
        String issueDate = segmentVO.getIssueDate();

//        if (effectiveDate.equals("0000/00/00"))
//        {
//            segmentVO.setEffectiveDate(currentDate);
//        }

        if (issueDate == null)
        {
            segmentVO.setIssueDate(this.issueDate);
        }
    }

    /**
     * For the selected contract, get the client information for each of its contract client and agents.  This data build the
     * IssueClientVO array.
     * @param segmentVO
     * @return IssueClientVO array
     * @throws Exception
     */
    private IssueClientVO[] determineClients(SegmentVO segmentVO)  throws Exception
    {
        IssueClientVO issueClientVO = null;
        ContractClientVO[] contractClientVOs = null;
        Vector issueClientVector = new Vector();

        if (segmentVO.getContractClientVOCount() > 0)
        {
            contractClientVOs = segmentVO.getContractClientVO();

            for (int i = 0; i < contractClientVOs.length; i++)
            {
                ClientRoleVO clientRoleVO = super.getClientRoleVO(contractClientVOs[i].getClientRoleFK());

                String roleType = clientRoleVO.getRoleTypeCT();

                ClientDetailVO clientDetailVO = null;
                issueClientVO = new IssueClientVO();
                issueClientVO.setContractClientVO(contractClientVOs[i]);
                issueClientVO.setClientPK(1);
                issueClientVO.setRoleTypeCT(clientRoleVO.getRoleTypeCT());

                if (roleType.equalsIgnoreCase(ANNUITANT) || roleType.equalsIgnoreCase(OWNER) ||
                    roleType.equalsIgnoreCase(SECONDARY_OWNER) || roleType.equalsIgnoreCase(SECONDARY_ANNUITANT) ||
                    roleType.equalsIgnoreCase(INSURED) || roleType.equalsIgnoreCase(PRIMARY_BENEFICIARY) ||
                    roleType.equalsIgnoreCase(CONTINGENT_BENEFICIARY))
                {
                    clientDetailVO = getClientDetails(clientRoleVO.getClientDetailFK());
                    issueClientVO.setClientDetailVO(clientDetailVO);

                    issueClientVector.add(issueClientVO);
                }
            }
        }

        //get the agent client information
        AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
        if (agentHierarchyVOs != null && agentHierarchyVOs.length > 0)
        {
            for (int j = 0; j < agentHierarchyVOs.length; j++)
            {
                IssueClientVO issueAgentClientVO = getAgentClientInfo(agentHierarchyVOs[j], segmentVO);

                issueClientVector.add(issueAgentClientVO);
            }
        }

        return (IssueClientVO[]) issueClientVector.toArray(new IssueClientVO[issueClientVector.size()]);
    }

    /**
     * Using the parent class get the ClientDetailVO for the key provided.
     * @param clientDetailPK
     * @return ClientDetailVO
     * @throws Exception
     */
    private ClientDetailVO getClientDetails(long clientDetailPK) throws Exception
    {
        ClientDetailVO clientDetailVO = null;

        clientDetailVO = super.getClientDetailVO(clientDetailPK);

        return clientDetailVO;
    }

    /**
     * Get the ClientDetailVO for the agent provided and the role type to create an IssueClientVO
     * @param agentHierarchyVO
     * @return  IssueClientVO
     * @throws Exception
     */
    private IssueClientVO getAgentClientInfo(AgentHierarchyVO agentHierarchyVO, SegmentVO segmentVO) throws Exception
    {
        Vector voInclusionList = new Vector();
        voInclusionList.add(ClientRoleVO.class);
        ClientRoleVO agentClientRoleVO = null;
        long clientDetailPK = 0;
        ClientDetailVO clientDetailVO = null;
        Agent agent = null;
        IssueClientVO issueClientVO = new IssueClientVO();

        agentClientRoleVO = super.getAgentClientInfo(agentHierarchyVO.getAgentFK(), voInclusionList);

        if (agentClientRoleVO != null)
        {
            String roleType = agentClientRoleVO.getRoleTypeCT();
            clientDetailPK = agentClientRoleVO.getClientDetailFK();

            clientDetailVO = getClientDetails(clientDetailPK);
            issueClientVO.setRoleTypeCT(roleType);
            issueClientVO.setClientDetailVO(clientDetailVO);

            agent = Agent.findByPK(agentHierarchyVO.getAgentFK());
            issueClientVO.setAgentTypeCT(agent.getAgentTypeCT());

            // The following code is erroring while generating issue report because the code is expecting
            // Segment.EffectiveDate but the issue report does not supply one. Discussed the probem with Kathy D
            // and decided would populate AgentHierarchAllocations only when Segment.EffectiveDate is suppied.
            // Syam Lingamallu
            if (segmentVO.getEffectiveDate() != null) 
            {
                AgentHierarchyAllocation agentHierarchyAllocation = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(
                        new Long(agentHierarchyVO.getAgentHierarchyPK()), new EDITDate(segmentVO.getEffectiveDate()));
    
                issueClientVO.setAgentAllocation(agentHierarchyAllocation.getAllocationPercent().getBigDecimal());
            }
        }

        return issueClientVO;
    }

    /**
     * Build an IssueInvestmentVO for each investment contained in the contract.
     * @param segmentVO
     * @return IssueInvestmentVO array
     */
    private IssueInvestmentVO[] getInvestmentVO(SegmentVO segmentVO)
     {
         InvestmentVO[] investmentVOs = null;
         Vector issueInvestments = new Vector();
         IssueInvestmentVO issueInvestmentVO = null;


         if (segmentVO.getInvestmentVOCount() > 0)
         {
             investmentVOs = segmentVO.getInvestmentVO();

             for (int i = 0; i <investmentVOs.length; i++)
             {
                 issueInvestmentVO = new IssueInvestmentVO();
                 issueInvestmentVO.setInvestmentVO(investmentVOs[i]);
                 issueInvestments.add(issueInvestmentVO);
             }
         }

         return (IssueInvestmentVO[]) issueInvestments.toArray(new IssueInvestmentVO[issueInvestments.size()]);
     }

    /**
     * Transactions that are reinsurable have [possibly] Treaties mapped to the Segment as ContractTreaties. The
     * Reinsurance information is driven by these ContractTreaties.
     */
    public void associateReinsuranceInformation()
    {
        // This is assumed to be the ReinsuranceDocument at the base Segment level only. Riders would have to be added
        // in the future.
        SegmentVO segmentVO = issueDocumentVO.getSegmentVO();

        Segment segment = new Segment(segmentVO);

        ContractTreaty[] contractTreaties = ContractTreaty.findBy_SegmentPK_V1(segment.getSegmentPK());

        if (contractTreaties != null)
        {
            for (int i = 0; i < contractTreaties.length; i++)
            {
                ContractTreaty contractTreaty = contractTreaties[i];

                if (contractTreaty.getStatus() == null)
                {
                    ReinsuranceDocument reinsuranceDocument = PRASEDocumentFactory.getSingleton().getReinsuranceDocument(contractTreaty);
                    Treaty treaty = new Treaty(contractTreaty.getTreatyFK().longValue());
                    contractTreaty.setTreaty(treaty);
                    treaty.setTreatyGroup(new TreatyGroup(treaty.getTreatyGroupFK()));
                    treaty.setReinsurer(new Reinsurer(treaty.getReinsurerFK()));

                    reinsuranceDocument.buildDocument();

                    ReinsuranceVO reinsuranceVO = (ReinsuranceVO) reinsuranceDocument.getDocumentAsVO();

                    issueDocumentVO.addReinsuranceVO(reinsuranceVO);
                }
            }
        }
    }

    /**
     * Calculates the Issue Age for all contractClients.
     * @param segmentVO
     * @return
     * @throws Exception
     */
    private ContractClientVO[] setIssueAge(SegmentVO segmentVO) throws Exception
    {
        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        int annuitantIssueAge = 0;
        boolean ownerIssueAgeMissing = false;
        int ownerIndex = 0;

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            int issueAge = 0;

            if (contractClientVOs[i].getEffectiveDate() == (null))
            {
                contractClientVOs[i].setEffectiveDate(segmentVO.getEffectiveDate());
            }

            role.business.Lookup roleLookup = new role.component.LookupComponent();

            List voInclusionList = new ArrayList();
            voInclusionList.add(ClientDetailVO.class);

            ClientRoleVO clientRoleVO = roleLookup.composeClientRoleVO(contractClientVOs[i].getClientRoleFK(), voInclusionList);

            ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);

            String effectiveDate = contractClientVOs[i].getEffectiveDate();

            String birthDate = clientDetailVO.getBirthDate();

            if ((birthDate != null) && (effectiveDate != null))
            {
                if (! birthDate.equals(EDITDate.DEFAULT_MAX_DATE))
                {
                    try
                    {
                        Contract contractComponent = new ContractComponent();

                        if (segmentVO.getSegmentNameCT().equalsIgnoreCase("Life"))
                        {
                            issueAge = contractComponent.calculateIssueAgeForLifeContracts(birthDate, effectiveDate);
                        }
                        else
                        {
                            issueAge = contractComponent.calculateIssueAge(birthDate, effectiveDate);
                        }
                    }
                    catch (EDITContractException e)
                    {
                        issueAge = 0;
                    }
                }
            }

            contractClientVOs[i].setIssueAge(issueAge);

            if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("ANN"))
            {
                annuitantIssueAge = issueAge;
            }

            if (issueAge == 0 && clientRoleVO.getRoleTypeCT().equalsIgnoreCase("OWN"))
            {
                ownerIndex = i;
                ownerIssueAgeMissing = true;
            }
        }

        if (ownerIssueAgeMissing)
        {
            contractClientVOs[ownerIndex].setIssueAge(annuitantIssueAge);
        }

        return contractClientVOs;
    }

    /**
     * Populates OwnersPremiumTaxState in NatauralDocVO.
     * Is set only when the transactionTypeCT is 'IS'
     */
    private void setOwnersPremiumTaxState()
    {
        Segment segment = new Segment(issueDocumentVO.getSegmentVO());

        ClientDetail ownerClientDetail = segment.getOwner();

        ClientAddress ownerPrimaryAddress = ownerClientDetail.getPrimaryAddress();
        if (ownerPrimaryAddress != null)
        {
            issueDocumentVO.setOwnersPremiumTaxState(ownerPrimaryAddress.getStateCT());
        }
        else
        {
            ClientAddress ownerBusinessAddress = ownerClientDetail.getBusinessAddress();
            if (ownerBusinessAddress != null)
            {
                issueDocumentVO.setOwnersPremiumTaxState(ownerBusinessAddress.getStateCT());
            }
        }
    }

    /**
     * Populates Insured fields.
     */
    private void setInsuredFields()
    {
        Segment segment = new Segment(issueDocumentVO.getSegmentVO());

        // only life policies will have insured role.
        if (segment.isLifeProduct()) 
        {
            ClientDetail insured = segment.getInsured();
    
            if (insured != null)
            {
                issueDocumentVO.setInsuredGender(insured.getGenderCT());
                issueDocumentVO.setInsuredDateOfBirth(insured.getBirthDate() == null ? null : insured.getBirthDate().getFormattedDate());
    
                ClientAddress insuredAddress = insured.getPrimaryAddress();
    
                if (insuredAddress != null)
                {
                    issueDocumentVO.setInsuredResidenceState(insuredAddress.getStateCT());
                }
            }
    
            ContractClient insuredContractClient = segment.getInsuredContractClient();
    
            ContractClientVO[] contractClientVOs = issueDocumentVO.getSegmentVO().getContractClientVO();
    
            for (int i = 0; i < contractClientVOs.length; i++)
            {
                if (contractClientVOs[i].getContractClientPK() == insuredContractClient.getPK())
                {
                    insuredContractClient = new ContractClient(contractClientVOs[i]);
                }
            }
            
            if (insuredContractClient != null)
            {
                issueDocumentVO.setInsuredIssueAge(insuredContractClient.getIssueAge());
                issueDocumentVO.setInsuredClass(insuredContractClient.getClassCT());
                issueDocumentVO.setInsuredUnderwritingClass(insuredContractClient.getUnderwritingClassCT());
            }
        }
    }

    public boolean getIncludeInvestments()
    {
        return includeInvestments;
    }

    public void setIncludeInvestments(boolean includeInvestments)
    {
        this.includeInvestments = includeInvestments;
    }

    public VOObject getDocumentAsVO()
    {
        return issueDocumentVO;
    }
}
